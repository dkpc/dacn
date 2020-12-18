package com.dacn.backend.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacn.backend.database.entity.Machine;

public interface MachineRepo extends JpaRepository<Machine, Integer>, MachineRepoCustom{
	
}
