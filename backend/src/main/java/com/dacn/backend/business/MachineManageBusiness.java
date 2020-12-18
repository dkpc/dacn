package com.dacn.backend.business;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacn.backend.database.entity.Machine;

public interface MachineManageBusiness {
	/**
	 * 
	 * @param count
	 * @return
	 * @throws InterruptedException 
	 */
	public List<String> createMachine(int count);
	
	/**
	 * 
	 * @param machineId
	 * @param token
	 * @return
	 */
	public String getConsoleLink(String machineId);
	
	public void deleteAllMachine();
	
	public List<String> createMachineWithClassname(String classname);

	public ResponseEntity<List<Machine>> getAllMachine();
}
