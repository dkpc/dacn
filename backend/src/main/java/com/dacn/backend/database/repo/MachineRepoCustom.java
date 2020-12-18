package com.dacn.backend.database.repo;

import java.util.List;

import com.dacn.backend.database.entity.Machine;

public interface MachineRepoCustom {
	
	public List<Machine> addMultipleMachine(List<Machine> lst);
	
	public void deleteAllMachine();
	
	public List<Machine> getAllMachine();
	
}
