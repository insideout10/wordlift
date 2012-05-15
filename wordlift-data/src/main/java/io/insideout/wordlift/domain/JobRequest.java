package io.insideout.wordlift.domain;

public interface JobRequest {

    public String getOnCompleteUrl();

    public String getOnProgressUrl();

    public String getChainName();

}