package com.dacn.backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetConsoleResponse {
	@JsonProperty(value = "console")
	private GetConsoleResponseConsole console;

	public GetConsoleResponseConsole getConsole() {
		return console;
	}

	public void setConsole(GetConsoleResponseConsole console) {
		this.console = console;
	}
	
	
}
