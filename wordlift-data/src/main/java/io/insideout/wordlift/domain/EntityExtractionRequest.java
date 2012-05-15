package io.insideout.wordlift.domain;

public class EntityExtractionRequest {

    private String url;
    private Double minRelevance = 0.1;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public Double getMinRelevance() {
	return minRelevance;
    }

    public void setMinRelevance(Double minRelevance) {
	this.minRelevance = minRelevance;
    }

}
