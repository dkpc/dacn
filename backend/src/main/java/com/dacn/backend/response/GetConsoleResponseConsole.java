package com.dacn.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetConsoleResponseConsole {
	
	@JsonProperty(value = "url")
	private String url;
	@JsonProperty(value = "type")
	private String type;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
