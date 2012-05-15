package io.insideout.wordlift.domain;

public class JobResponse {

    private Integer status;
    private String message;
    private String id;

    public JobResponse() {
    }

    public JobResponse(Integer status, String message, String id) {
	this.status = status;
	this.message = message;
	this.id = id;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}
