package com.dacn.backend.request;

import java.util.List;

import com.dacn.backend.BO.AssignMachineBO;

public class AssignMachineReq {
	private List<AssignMachineBO> assignMachineList;

	public List<AssignMachineBO> getAssignMachineList() {
		return assignMachineList;
	}

	public void setAssignMachineList(List<AssignMachineBO> assignMachineList) {
		this.assignMachineList = assignMachineList;
	}
}
