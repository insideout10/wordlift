package io.insideout.wordlift.endpoint.services;

import org.apache.clerezza.rdf.core.UriRef;

public interface SchemaService {

    public String getType(final UriRef[] refs);

}