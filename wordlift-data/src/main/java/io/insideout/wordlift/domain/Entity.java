package io.insideout.wordlift.domain;

public interface Entity {

    public Long getCount();

    public Double getRelevance();

    public String getText();

    public String getType();

    public String getSourceUrl();
}
