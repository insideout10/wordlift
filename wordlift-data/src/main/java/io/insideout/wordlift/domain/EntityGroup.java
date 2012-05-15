package io.insideout.wordlift.domain;

import java.util.HashSet;
import java.util.Set;

public class EntityGroup {

    private String text;
    private String type;
    private Long count;
    private Set<String> references;
    private Set<String> sourceUrls = new HashSet<String>();

    public EntityGroup(final String text, final Long count) {
	this.text = text;
	this.count = count;
    }

    public EntityGroup(final String text, final Long count, final String type) {
	this.text = text;
	this.count = count;
	this.type = type;
    }

    public void addSourceUrl(String sourceUrl) {
	if (false == sourceUrls.contains(sourceUrl))
	    sourceUrls.add(sourceUrl);
    }

    public void addCount(final Long value) {
	count += value;
    }

    public String getText() {
	return text;
    }

    public void setText(final String text) {
	this.text = text;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Long getCount() {
	return count;
    }

    public void setCount(final Long count) {
	this.count = count;
    }

    public Set<String> getReferences() {
	return references;
    }

    public void setReferences(Set<String> references) {
	this.references = references;
    }

    public Set<String> getSourceUrls() {
	return sourceUrls;
    }

    public void setSourceUrls(Set<String> sourceUrls) {
	this.sourceUrls = sourceUrls;
    }

    @Override
    public String toString() {
	return "EntityGroup [text=" + text + ", count=" + count + "]";
    }

}
