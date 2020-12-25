package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.User;

@SuppressWarnings("unchecked")
public class UserRepoImpl implements UserRepoCustom{
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepo userRepo;
	
	@Transactional
	@Override
	public User addUser(String username, String password, String name, String className, int assignedMachineId,
			String role, int studentId, String machineUsername, String machinePassword) {
		
		User user = new User();
		user.setAssignedMachineId(assignedMachineId);
		user.setClassName(className);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		user.setRole(role);
		user.setStudentId(studentId);
		
		return userRepo.save(user);
	}

	
	@Override
	public User findUserByStudentId(int studentId) {
		
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.studentId LIKE :studentId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("studentId", studentId);
        
        List<User> lst = qr.getResultList();
        
		return lst.get(0);
	}


	@Override
	public User getLoginInfo(String username) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.username LIKE :username");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("username", username);
        
        List<User> lst = qr.getResultList();
        
		return lst.get(0);
	}


	@Override
	public int getMachineInfo(String classname) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.classname = :classname ");  
        sb.append("ORDER BY t.assignedMachineId DESC");
		
        Query qr = em.createQuery(sb.toString());
        qr.setMaxResults(1);
        
        qr.setParameter("classname", classname);
        
        List<User> lst = qr.getResultList();
		return lst.get(0).getAssignedMachineId();
	}


	@Override
	@Transactional
	public void deleteAll(String classname) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("DELETE FROM user WHERE class_name = :classname");
		
        Query qr = em.createNativeQuery(sb.toString());
        qr.setParameter("classname", classname);
        
        qr.executeUpdate();
        
        update(getMax(classname));
	}
	
	public void update(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE User AUTO_INCREMENT = :id");
		
		Query qr = em.createNativeQuery(sb.toString());
        qr.setParameter("id", id);
        qr.executeUpdate();
	}
	
	public int getMax(String classname) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.id = (SELECT max(tt.id) FROM User tt WHERE tt.classname = :classname)");  
		
        Query qr = em.createQuery(sb.toString());
        qr.setParameter("classname", classname);
        List<User> lst = qr.getResultList();
        if (lst.isEmpty()) {
        	return 1;
        }
        return lst.get(0).getId();
        
	}
}
