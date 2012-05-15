package io.insideout.wordlift.domain;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class EntityImpl implements Entity {

    private Long count = 1L;
    private Double relevance = 0.0;
    private String type;
    private String reference;
    private Double score = 0.0;
    private Double rank = 0.0;
    private Map<String, List<Object>> properties = new HashMap<String, List<Object>>();

    @JsonIgnore
    private String sourceUrl;

    public EntityImpl() {
    }

    public EntityImpl(final String type, final Long count, final Double relevance) {
	this.type = type;
	this.count = count;
	this.relevance = relevance;
    }

    public void addProperty(final String key, final Object value) {
	if (false == properties.containsKey(key)) {
	    List<Object> objects = new ArrayList<Object>();
	    objects.add(value);
	    properties.put(key, objects);
	    return;
	}

	properties.get(key).add(value);
    }

    public void addCount(Long value) {
	count += value;
    }

    public void setCount(Long count) {
	this.count = count;
    }

    public void setRelevance(Double relevance) {
	this.relevance = relevance;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Long getCount() {
	return count;
    }

    public Double getRelevance() {
	return relevance;
    }

    public String getText() {
	List<Object> labels = properties.get("label");
	if (0 == labels.size())
	    return "";

	return labels.get(0).toString();
    }

    public String getType() {
	return type;
    }

    public String getReference() {
	return reference;
    }

    public void setReference(String reference) {
	this.reference = reference;
    }

    public String getSourceUrl() {
	return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
	this.sourceUrl = sourceUrl;
    }

    public Double getScore() {
	return score;
    }

    public void setScore(Double score) {
	this.score = score;
    }

    public Double getRank() {
	return rank;
    }

    public void setRank(Double rank) {
	this.rank = rank;
    }

    public Map<String, List<Object>> getProperties() {
	return properties;
    }

    public void setProperties(Map<String, List<Object>> properties) {
	this.properties = properties;
    }

    @Override
    public String toString() {
	return "EntityImpl [count=" + count + ", relevance=" + relevance + ", text=" + getText() + ", type=" + type + ", reference=" + reference + ", score=" + score + ", rank=" + rank + ", properties=" + properties + ", sourceUrl="
		+ sourceUrl + "]";
    }

}
