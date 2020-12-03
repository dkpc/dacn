package com.dacn.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateInstanceResponseLinks {
	@JsonProperty(value = "href")
	private String href;
	@JsonProperty(value = "rel")
	private String rel;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
}