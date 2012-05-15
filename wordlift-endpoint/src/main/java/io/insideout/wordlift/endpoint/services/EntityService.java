package io.insideout.wordlift.endpoint.services;

import io.insideout.wordlift.domain.EntityImpl;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.Triple;

public interface EntityService {

    public EntityImpl create(final Triple triple, final MGraph graph);

}