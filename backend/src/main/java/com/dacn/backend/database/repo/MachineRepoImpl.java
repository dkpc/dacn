package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.Machine;

@SuppressWarnings("unchecked")
public class MachineRepoImpl implements MachineRepoCustom{
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private MachineRepo machineRepo;
	
	@Transactional
	public List<Machine> addMultipleMachine(List<Machine> lst){
		return machineRepo.saveAll(lst);
	}

	@Override
	@Transactional
	public void deleteAllMachine() {
		machineRepo.deleteAll();
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE Machine AUTO_INCREMENT = 1");
		
		Query qr = em.createNativeQuery(sb.toString());
		qr.executeUpdate();
	}

	
	@Override
	public List<Machine> getAllMachine() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m FROM Machine m");
		
		Query qr = em.createQuery(sb.toString());
		
		List<Machine> lst = qr.getResultList();
		
		return lst;
	}
}
