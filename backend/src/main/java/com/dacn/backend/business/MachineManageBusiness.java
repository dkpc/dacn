package com.dacn.backend.business;

import java.util.List;

import com.dacn.backend.database.entity.Machine;

public interface MachineManageBusiness {
	/**
	 * 
	 * @param count
	 * @return
	 */
	public List<Machine> getMachine(int count);

}
