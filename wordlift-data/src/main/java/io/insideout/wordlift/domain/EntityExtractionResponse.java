package io.insideout.wordlift.domain;

public class EntityExtractionResponse {

    private EntityImpl[] entities;

    public EntityImpl[] getEntities() {
	return entities;
    }

    public void setEntities(EntityImpl[] entities) {
	this.entities = entities;
    }

}
