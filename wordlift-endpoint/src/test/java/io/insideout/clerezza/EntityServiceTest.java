package io.insideout.clerezza;

import io.insideout.wordlift.domain.EntityImpl;
import io.insideout.wordlift.endpoint.services.EntityServiceImpl;
import io.insideout.wordlift.endpoint.services.SchemaServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.clerezza.rdf.core.Graph;
import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.access.TcManager;
import org.apache.clerezza.rdf.core.serializedform.Parser;
import org.apache.clerezza.rdf.ontologies.RDF;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityServiceTest {

    private final UriRef STANBOL_ENTITY_ANNOTATION_URI = new UriRef("http://fise.iks-project.eu/ontology/EntityAnnotation");

    @Test
    public void test() throws FileNotFoundException {

	final FileInputStream fileInputStream = new FileInputStream("/Users/david/Desktop/sample.rdf");

	final Parser parser = Parser.getInstance();

	Graph graph = parser.parse(fileInputStream, "application/rdf+xml");

	logger.debug("Size of graph [{}].", graph.size());

	// get all the entities.

	Iterator<Triple> entityAnnotations = graph.filter(null, RDF.type, STANBOL_ENTITY_ANNOTATION_URI);

	EntityServiceImpl entityService = new EntityServiceImpl();
	entityService.setSchemaService(new SchemaServiceImpl());

	final TcManager tcManager = TcManager.getInstance();
	MGraph mGraph = tcManager.createMGraph(new UriRef("http://example.org/testGraph"));
	mGraph.addAll(graph);

	while (entityAnnotations.hasNext()) {
	    EntityImpl entity = entityService.create(entityAnnotations.next(), mGraph);
	    logger.debug(entity.toString());
	}

    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
