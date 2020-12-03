package com.dacn.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateInstanceResponseSecurityGroup {
	@JsonProperty(value = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}