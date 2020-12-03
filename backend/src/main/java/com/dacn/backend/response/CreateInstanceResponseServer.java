package com.dacn.backend.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateInstanceResponseServer{
	@JsonProperty(value = "security_groups")
	private List<CreateInstanceResponseSecurityGroup> securityGroup;
	@JsonProperty(value = "OS-DCF:diskConfig")
	private String diskConfig;
	@JsonProperty(value = "id")
	private String id;
	@JsonProperty(value = "links")
	private List<CreateInstanceResponseLinks> links;
	@JsonProperty(value = "adminPass")
	private String adminPass;
	public List<CreateInstanceResponseSecurityGroup> getSecurityGroup() {
		return securityGroup;
	}
	public void setSecurityGroup(List<CreateInstanceResponseSecurityGroup> securityGroup) {
		this.securityGroup = securityGroup;
	}
	public String getDiskConfig() {
		return diskConfig;
	}
	public void setDiskConfig(String diskConfig) {
		this.diskConfig = diskConfig;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CreateInstanceResponseLinks> getLinks() {
		return links;
	}
	public void setLinks(List<CreateInstanceResponseLinks> links) {
		this.links = links;
	}
	public String getAdminPass() {
		return adminPass;
	}
	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}
	
}