package com.dacn.backend.response;

import com.dacn.backend.database.entity.User;

public class LoginRes {
	private User user;
	private String console;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}
}
