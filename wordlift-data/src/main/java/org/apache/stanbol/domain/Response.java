package org.apache.stanbol.domain;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    @JsonProperty("@subject")
    private Set<Subject> subject;

    public Set<Subject> getSubject() {
	return subject;
    }

    public void setSubject(Set<Subject> subject) {
	this.subject = subject;
    }

}
