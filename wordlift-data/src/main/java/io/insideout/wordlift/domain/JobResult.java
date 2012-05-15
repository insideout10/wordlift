package io.insideout.wordlift.domain;

import java.util.Collection;

public class JobResult {

    private String id;
    private Collection<EntityImpl> entities;

    public JobResult() {
    }

    public JobResult(final String id, final Collection<EntityImpl> entities) {
	this.id = id;
	this.entities = entities;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public Collection<EntityImpl> getEntities() {
	return entities;
    }

    public void setEntities(Collection<EntityImpl> entities) {
	this.entities = entities;
    }

}
