package io.insideout.wordlift.domain;

public class TextJobRequest implements JobRequest {

    private String text;
    private String onCompleteUrl;
    private String onProgressUrl;
    private String chainName;

    public TextJobRequest() {
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
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
	return "TextJobRequest [text=" + text + ", onCompleteUrl=" + onCompleteUrl + ", onProgressUrl=" + onProgressUrl + ", chainName=" + chainName + "]";
    }

}
