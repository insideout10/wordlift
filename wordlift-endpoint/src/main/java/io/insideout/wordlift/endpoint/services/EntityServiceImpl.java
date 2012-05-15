package io.insideout.wordlift.endpoint.services;

import io.insideout.wordlift.domain.EntityImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.clerezza.rdf.core.Graph;
import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.impl.PlainLiteralImpl;
import org.apache.clerezza.rdf.core.impl.TypedLiteralImpl;
import org.apache.clerezza.rdf.ontologies.RDF;
import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = "io.insideout.keywords.services.EntityService")
@Service
public class EntityServiceImpl implements EntityService {

    @Reference
    private SchemaService schemaService;

    private final UriRef STANBOL_ENTITY_REFERENCE_URI = new UriRef("http://fise.iks-project.eu/ontology/entity-reference");
    private final UriRef STANBOL_ENTITY_RANK_URI = new UriRef("http://www.iks-project.eu/ontology/rick/model/entityRank");
    private final UriRef RDF_SCHEMA_LABEL_URI = new UriRef("http://www.w3.org/2000/01/rdf-schema#label");
    private final UriRef STANBOL_ENTITY_SCORE_URI = new UriRef("http://www.iks-project.eu/ontology/rick/query/score");
    private final UriRef STANBOL_ENTITY_SUBJECT_URI = new UriRef("http://purl.org/dc/terms/subject");
    private final UriRef STANBOL_ENTITY_HOMEPAGE_URI = new UriRef("http://xmlns.com/foaf/0.1/homepage");
    private final UriRef STANBOL_ENTITY_THUMBNAIL_URI = new UriRef("http://dbpedia.org/ontology/thumbnail");
    private final UriRef STANBOL_ENTITY_LATITUDE_URI = new UriRef("http://www.w3.org/2003/01/geo/wgs84_pos#lat");
    private final UriRef STANBOL_ENTITY_LONGITUDE_URI = new UriRef("http://www.w3.org/2003/01/geo/wgs84_pos#long");
    private final UriRef RDF_SCHEMA_COMMENT_URI = new UriRef("http://www.w3.org/2000/01/rdf-schema#comment");
    private final UriRef DBPEDIA_WIKI_PAGE_REDIRECTS_URI = new UriRef("http://www.w3.org/2000/01/rdf-schema#seeAlso");

    private final String DEFAULT_LANGUAGE = "en";

    @Override
    public EntityImpl create(final Triple triple, final MGraph graph) {
	return createEntityFromTriple(triple, graph);
    }

    public void setSchemaService(final SchemaService schemaService) {
	this.schemaService = schemaService;
    }

    private EntityImpl createEntityFromTriple(final Triple triple, final MGraph graph) {
	EntityImpl entity = new EntityImpl();

	// for every entity, get the entity reference.
	// http://fise.iks-project.eu/ontology/entity-reference
	Graph tripleGraph = new GraphNode(triple.getSubject(), graph).getNodeContext();
	UriRef reference = getReference(tripleGraph);
	entity.setReference(reference.getUnicodeString());
	logger.debug("[reference:{}]", reference.getUnicodeString());

	// get the referenced entity.
	Graph referenceGraph = new GraphNode(reference, graph).getNodeContext();

	// check if it's a redirection.
	boolean isRedirection = isRedirection(referenceGraph);
	if (true == isRedirection) {
	    logger.debug("The entity is a redirection and will be ignored.");
	    return null;
	}

	// get the type.
	List<UriRef> typeRefs = getTypes(referenceGraph);
	String type = schemaService.getType(typeRefs.toArray(new UriRef[] {}));
	entity.setType(type);
	logger.debug("[type:{}].", type);

	// get the rank.
	Double rank = getDoubleValue(STANBOL_ENTITY_RANK_URI, referenceGraph);
	entity.setRank(rank);
	logger.debug("[rank:{}].", rank);

	// get the score.
	Double score = getDoubleValue(STANBOL_ENTITY_SCORE_URI, referenceGraph);
	entity.setScore(score);
	logger.debug("[score:{}].", score);

	// get the _first_ (en) label (rdfs:label xml:lang="en").
	List<String> labels = getStringsByLanguage(RDF_SCHEMA_LABEL_URI, DEFAULT_LANGUAGE, referenceGraph);
	for (String label : labels) {
	    addPropertyToEntityIfNotNull(entity, "label", label);
	    logger.debug("[label:{}].", label);
	}

	// image.
	List<UriRef> thumbnailRefs = getReferences(STANBOL_ENTITY_THUMBNAIL_URI, referenceGraph);
	for (UriRef ref : thumbnailRefs) {
	    addPropertyToEntityIfNotNull(entity, "thumbnail", ref.getUnicodeString());
	    logger.debug("[thumbnail:{}]", ref.getUnicodeString());
	}
	
	// depiction.
	// http://xmlns.com/foaf/0.1/depiction UriRef
	
	

	// description. ( <rdfs:comment xml:lang="en">)
	List<String> descriptions = getStringsByLanguage(RDF_SCHEMA_COMMENT_URI, DEFAULT_LANGUAGE, referenceGraph);
	for (String description : descriptions) {
	    addPropertyToEntityIfNotNull(entity, "description", description);
	    logger.debug("[description:{}].", description);
	}

	
	// get an array of the categories [STANBOL_ENTITY_SUBJECT_URI].
	List<UriRef> subjectRefs = getReferences(STANBOL_ENTITY_SUBJECT_URI, referenceGraph);
	for (UriRef ref : subjectRefs) {
	    addPropertyToEntityIfNotNull(entity, "subject", ref.getUnicodeString());
	    logger.debug("[subject:{}]", ref.getUnicodeString());
	}

	// get the homepage.
	List<UriRef> homepageRefs = getReferences(STANBOL_ENTITY_HOMEPAGE_URI, referenceGraph);
	for (UriRef ref : homepageRefs) {
	    addPropertyToEntityIfNotNull(entity, "homepage", ref.getUnicodeString());
	    logger.debug("[homepage:{}]", ref.getUnicodeString());
	}

	// get the latitude/longitude
	Double latitude = getDoubleValue(STANBOL_ENTITY_LATITUDE_URI, referenceGraph);
	Double longitude = getDoubleValue(STANBOL_ENTITY_LONGITUDE_URI, referenceGraph);
	addPropertyToEntityIfNotNull(entity, "latitude", latitude);
	addPropertyToEntityIfNotNull(entity, "longitude", longitude);
	logger.debug("[lat/lon:{}/{}].", latitude, longitude);


	return entity;
    }

    private void addPropertyToEntityIfNotNull(final EntityImpl entity, final String key, final Object value) {
	if (null != value)
	    entity.addProperty(key, value);
    }

    private UriRef getReference(final Graph graph) {
	return (UriRef) graph.filter(null, STANBOL_ENTITY_REFERENCE_URI, null).next().getObject();
    }

    private List<UriRef> getTypes(final Graph graph) {
	Iterator<Triple> it = graph.filter(null, RDF.type, null);

	List<UriRef> refs = new ArrayList<UriRef>();
	while (it.hasNext())
	    refs.add((UriRef) it.next().getObject());

	return refs;
    }

    private List<UriRef> getReferences(final UriRef predicate, final Graph graph) {
	Iterator<Triple> it = graph.filter(null, predicate, null);

	List<UriRef> refs = new ArrayList<UriRef>();
	while (it.hasNext())
	    refs.add((UriRef) it.next().getObject());

	return refs;
    }

    private List<String> getStringsByLanguage(final UriRef predicate, final String language, final Graph graph) {
	Iterator<Triple> it = graph.filter(null, predicate, null);

	List<String> strings = new ArrayList<String>();
	while (it.hasNext()) {
	    PlainLiteralImpl plainLiteralImpl = ((PlainLiteralImpl) it.next().getObject());
	    if (true == "".equals(language) || true == language.equals(plainLiteralImpl.getLanguage().toString()))
		strings.add(plainLiteralImpl.getLexicalForm());
	}

	return strings;
    }

    private Double getDoubleValue(final UriRef predicate, final Graph graph) {
	Iterator<Triple> it = graph.filter(null, predicate, null);

	if (false == it.hasNext())
	    return null;

	TypedLiteralImpl value = (TypedLiteralImpl) it.next().getObject();
	return Double.parseDouble(value.getLexicalForm());

    }

    private boolean isRedirection(final Graph graph) {
	List<UriRef> refs = getReferences(DBPEDIA_WIKI_PAGE_REDIRECTS_URI, graph);
	return (0 < refs.size());
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
}
