package io.insideout.wordlift.endpoint;

import io.insideout.wordlift.domain.EntityImpl;
import io.insideout.wordlift.endpoint.services.EntityService;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.ontologies.RDF;
import org.apache.stanbol.enhancer.contentitem.inmemory.InMemoryContentItem;
import org.apache.stanbol.enhancer.servicesapi.Chain;
import org.apache.stanbol.enhancer.servicesapi.ChainManager;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EnhancementException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementJobManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancerHelper {

	private ChainManager chainManager;
	private EnhancementJobManager enhancementJobManager;
	private EntityService entityService;

	private final UriRef STANBOL_ENTITY_ANNOTATION_URI = new UriRef(
			"http://fise.iks-project.eu/ontology/EntityAnnotation");

	public EnhancerHelper(final ChainManager chainManager,
			final EnhancementJobManager enhancementJobManager,
			final EntityService entityService) {
		this.chainManager = chainManager;
		this.enhancementJobManager = enhancementJobManager;
		this.entityService = entityService;
	}

	// public Collection<EntityImpl> extractEntities(final URL url) {
	// WebContentItem contentItem = new WebContentItem(url);
	// return extract(contentItem);
	// }

	public Collection<EntityImpl> extractEntities(final String content) {
		InMemoryContentItem contentItem;

		try {
			// TODO: must change this,
			// http://incubator.apache.org/stanbol/docs/trunk/enhancer/contentitemfactory.html
			contentItem = new InMemoryContentItem(content.getBytes("UTF-8"),
					MediaType.TEXT_PLAIN);
		} catch (UnsupportedEncodingException e) {
			logger.error("The provided contents could not be decoded using UTF-8.");
			throw new RuntimeException(
					"The provided contents could not be decoded using UTF-8.");
		}

		return extract(contentItem);
	}

	private Collection<EntityImpl> extract(ContentItem contentItem) {
		Chain chain = chainManager.getDefault();
		if (chain == null) {
			logger.error("The enhancement chain could not be found.");
			throw new RuntimeException(
					"The enhancement chain could not be found.");
		}

		try {
			enhancementJobManager.enhanceContent(contentItem, chain);
		} catch (EnhancementException e) {
			logger.error("The Enhancement Job Manager returned an error: {}.",
					e.getMessage(), e);
			throw new RuntimeException(
					"The Enhancement Job Manager returned an error.", e);
		}

		// the enhancement results are now available in the metadata.
		MGraph enhancementResults = contentItem.getMetadata();

		logger.info("Found [{}] triples.", enhancementResults.size());

		Iterator<Triple> it = enhancementResults.filter(null, RDF.type,
				STANBOL_ENTITY_ANNOTATION_URI);

		Map<String, EntityImpl> entities = new HashMap<String, EntityImpl>();

		while (it.hasNext()) {
			Triple t = it.next();

			EntityImpl entity = entityService.create(t, enhancementResults);

			// if we didn't get any entity, proceed to the next one.
			if (null == entity)
				continue;

			// if (0.5 > entity.getRelevance())
			// continue;

			if (true == entities.containsKey(entity.getReference())) {
				((EntityImpl) entities.get(entity.getReference())).addCount(1L);
			} else {
				entities.put(entity.getReference(), entity);
			}
		}

		logger.info("Extraction completed.");

		return entities.values();

	}

	// private EntityImpl createEntityFromGraph(Graph graph) {
	// EntityImpl entity = new EntityImpl();
	//
	// try {
	// entity.setReference(getReference(graph));
	// entity.setRelevance(getConfidence(graph));
	// entity.setText(getLabel(graph));
	// entity.setType(getSchemaType(graph));
	// entity.setCount(1L);
	// } catch (Exception e) {
	// logger.error("An error occurred: {}.", e.getMessage(), e);
	// }
	// return entity;
	// }
	//
	// private String getReference(Graph graph) {
	// UriRef uri = (UriRef) graph.filter(null, new
	// UriRef("http://fise.iks-project.eu/ontology/entity-reference"),
	// null).next().getObject();
	// return uri.getUnicodeString();
	// }
	//
	// private String getLabel(Graph graph) {
	// PlainLiteralImpl label = (PlainLiteralImpl) graph.filter(null, new
	// UriRef("http://fise.iks-project.eu/ontology/entity-label"),
	// null).next().getObject();
	// return label.getLexicalForm();
	// }
	//
	// private Double getConfidence(Graph graph) {
	// TypedLiteralImpl confidence = (TypedLiteralImpl) graph.filter(null, new
	// UriRef("http://fise.iks-project.eu/ontology/confidence"),
	// null).next().getObject();
	// return Double.parseDouble(confidence.getLexicalForm());
	// }
	//
	// private String getSchemaType(Graph graph) {
	//
	// Iterator<Triple> it = graph.filter(null, new
	// UriRef("http://fise.iks-project.eu/ontology/entity-type"), null);
	//
	// while (it.hasNext()) {
	// UriRef uri = (UriRef) it.next().getObject();
	// String type = convertDbpediaTypeToSchemaType(uri.getUnicodeString());
	//
	// if (null != type)
	// return type;
	// }
	//
	// return "unknown";
	// }
	//
	// private String convertDbpediaTypeToSchemaType(final String type) {
	//
	// // http://mappings.dbpedia.org/server/ontology/classes
	// if (true == type.equals("http://dbpedia.org/ontology/Place"))
	// return "Place";
	// if (true == type.equals("http://dbpedia.org/ontology/Organisation"))
	// return "Organization";
	// if (true == type.equals("http://dbpedia.org/ontology/Person"))
	// return "Person";
	// if (true == type.equals("http://dbpedia.org/ontology/Event"))
	// return "Event";
	// if (true == type.equals("http://dbpedia.org/ontology/Work"))
	// return "CreativeWork";
	//
	// return null;
	// }

	private Logger logger = LoggerFactory.getLogger(this.getClass());

}
