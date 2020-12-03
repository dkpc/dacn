package com.dacn.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateInstanceResponse {
	@JsonProperty(value = "server")
	private CreateInstanceResponseServer server;

	public CreateInstanceResponseServer getServer() {
		return server;
	}

	public void setServer(CreateInstanceResponseServer server) {
		this.server = server;
	}
}



