package com.dacn.backend.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER", schema = "HDH")
public class User {
	
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "student_id")
	private int studentId;
	
	@Column(name = "class_name")
	private String className;
	
	@Column(name = "assigned_machine_id")
	private int assignedMachineId;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "machine_username")
	private String machineUsername;
	
	@Column(name = "machine_password")
	private String machinePassword;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getAssignedMachineId() {
		return assignedMachineId;
	}

	public void setAssignedMachineId(int assignedMachineId) {
		this.assignedMachineId = assignedMachineId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getMachineUsername() {
		return machineUsername;
	}

	public void setMachineUsername(String machineUsername) {
		this.machineUsername = machineUsername;
	}

	public String getMachinePassword() {
		return machinePassword;
	}

	public void setMachinePassword(String machinePassword) {
		this.machinePassword = machinePassword;
	}
	
}