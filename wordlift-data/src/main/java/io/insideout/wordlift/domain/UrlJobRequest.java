package io.insideout.wordlift.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UrlJobRequest implements JobRequest {

    // - url to analyze
    // - callback url
    // - callback when finished
    // - callback with progress
    // - chain name

    private String url;
    private String onCompleteUrl;
    private String onProgressUrl;
    private String chainName;

    public UrlJobRequest() {
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    @Override
    public String getOnCompleteUrl() {
	return onCompleteUrl;
    }

    public void setOnCompleteUrl(String onCompleteUrl) {
	this.onCompleteUrl = onCompleteUrl;
    }

    @Override
    public String getOnProgressUrl() {
	return onProgressUrl;
    }

    public void setOnProgressUrl(String onProgressUrl) {
	this.onProgressUrl = onProgressUrl;
    }

    @Override
    public String getChainName() {
	return chainName;
    }

    public void setChainName(String chainName) {
	this.chainName = chainName;
    }

    @Override
    public String toString() {
	return "UrlJobRequest [url=" + url + ", onCompleteUrl=" + onCompleteUrl + ", onProgressUrl=" + onProgressUrl + ", chainName=" + chainName + "]";
    }

}
