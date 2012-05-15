package io.insideout.wordlift.endpoint.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.clerezza.rdf.core.UriRef;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component(name = "io.insideout.keywords.osgi.SchemaService")
@Service
public class SchemaServiceImpl implements SchemaService {

    private String UNKNOWN_TYPE = "Other";

    private Map<UriRef, String> typeRefs;

    public SchemaServiceImpl() {
	typeRefs = new HashMap<UriRef, String>();

	typeRefs.put(new UriRef("http://dbpedia.org/ontology/Place"), "Place");
	typeRefs.put(new UriRef("http://dbpedia.org/ontology/Organisation"), "Organization");
	typeRefs.put(new UriRef("http://dbpedia.org/ontology/Person"), "Person");
	typeRefs.put(new UriRef("http://dbpedia.org/ontology/Event"), "Event");
	typeRefs.put(new UriRef("http://dbpedia.org/ontology/Work"), "CreativeWork");
    }

    @Override
    public String getType(final UriRef[] refs) {
	for (final UriRef ref : refs)
	    if (true == typeRefs.containsKey(ref))
		return typeRefs.get(ref);

	return UNKNOWN_TYPE;
    }
}
