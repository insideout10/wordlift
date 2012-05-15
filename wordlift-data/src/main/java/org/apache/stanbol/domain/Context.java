package org.apache.stanbol.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Context {

    @JsonProperty("dbp-ont")
    private String dbpOnt;

    @JsonProperty("dc")
    private String dc;

    @JsonProperty("enhancer")
    private String enhancer;

    @JsonProperty("foaf")
    private String foaf;

    @JsonProperty("geo")
    private String geo;

    @JsonProperty("owl")
    private String owl;

    @JsonProperty("rickModel")
    private String rickModel;

    @JsonProperty("rickQuery")
    private String rickQuery;

    @JsonProperty("xsd")
    private String xsd;

    public String getDbpOnt() {
	return dbpOnt;
    }

    public void setDbpOnt(String dbpOnt) {
	this.dbpOnt = dbpOnt;
    }

    public String getDc() {
	return dc;
    }

    public void setDc(String dc) {
	this.dc = dc;
    }

    public String getEnhancer() {
	return enhancer;
    }

    public void setEnhancer(String enhancer) {
	this.enhancer = enhancer;
    }

    public String getFoaf() {
	return foaf;
    }

    public void setFoaf(String foaf) {
	this.foaf = foaf;
    }

    public String getGeo() {
	return geo;
    }

    public void setGeo(String geo) {
	this.geo = geo;
    }

    public String getOwl() {
	return owl;
    }

    public void setOwl(String owl) {
	this.owl = owl;
    }

    public String getRickModel() {
	return rickModel;
    }

    public void setRickModel(String rickModel) {
	this.rickModel = rickModel;
    }

    public String getRickQuery() {
	return rickQuery;
    }

    public void setRickQuery(String rickQuery) {
	this.rickQuery = rickQuery;
    }

    public String getXsd() {
	return xsd;
    }

    public void setXsd(String xsd) {
	this.xsd = xsd;
    }

    @Override
    public String toString() {
	return "Context [dbpOnt=" + dbpOnt + ", dc=" + dc + ", enhancer=" + enhancer + ", foaf=" + foaf + ", geo=" + geo + ", owl=" + owl + ", rickModel=" + rickModel + ", rickQuery=" + rickQuery + ", xsd=" + xsd + "]";
    }

}
