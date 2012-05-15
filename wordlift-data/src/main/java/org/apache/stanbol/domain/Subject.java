package org.apache.stanbol.domain;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject {

    @JsonProperty("@subject")
    private String subject;

    @JsonProperty("@type")
    private Set<String> types;

    @JsonProperty("enhancer:confidence")
    private Double confidence;

    @JsonProperty("enhancer:entity-label")
    private EntityLabel entityLabel;

    @JsonProperty("enhancer:entity-type")
    private Set<String> entityTypes;

    @JsonProperty("enhancer:entity-reference")
    private String entityReference;

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public Set<String> getTypes() {
	return types;
    }

    public void setTypes(Set<String> types) {
	this.types = types;
    }

    public Double getConfidence() {
	return confidence;
    }

    public void setConfidence(Double confidence) {
	this.confidence = confidence;
    }

    public EntityLabel getEntityLabel() {
	return entityLabel;
    }

    public void setEntityLabel(EntityLabel entityLabel) {
	this.entityLabel = entityLabel;
    }

    public Set<String> getEntityTypes() {
	return entityTypes;
    }

    public void setEntityTypes(Set<String> entityTypes) {
	this.entityTypes = entityTypes;
    }

    public String getEntityReference() {
	return entityReference;
    }

    public void setEntityReference(String entityReference) {
	this.entityReference = entityReference;
    }

    @Override
    public String toString() {
	return "Subject [subject=" + subject + ", types=" + types + ", confidence=" + confidence + ", entityLabel=" + entityLabel + ", entityTypes=" + entityTypes + ", entityReference=" + entityReference + "]";
    }

}
