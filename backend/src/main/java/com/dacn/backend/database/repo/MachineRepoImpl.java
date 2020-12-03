package com.dacn.backend.database.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.Machine;

public class MachineRepoImpl {
	
	@Autowired
	private MachineRepo machineRepo;
	
	public List<Machine> addMultipleMachine(List<Machine> lst){
		return machineRepo.saveAll(lst);
	}
}
