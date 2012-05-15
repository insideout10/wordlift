package org.apache.stanbol.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityLabel {

    @JsonProperty("@literal")
    private String literal;

    @JsonProperty("@language")
    private String language;

    public String getLiteral() {
	return literal;
    }

    public void setLiteral(String literal) {
	this.literal = literal;
    }

    public String getLanguage() {
	return language;
    }

    public void setLanguage(String language) {
	this.language = language;
    }

}
