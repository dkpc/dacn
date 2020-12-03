package com.dacn.backend.BO;

public class AssignMachineBO {
	private int studentId;
	private int machineId;
	private String machineUsername;
	private String machinePassword;
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getMachineId() {
		return machineId;
	}
	public void setMachineId(int machineId) {
		this.machineId = machineId;
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
