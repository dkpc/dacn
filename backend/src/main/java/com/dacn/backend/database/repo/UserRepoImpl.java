package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.User;

@SuppressWarnings("unchecked")
public class UserRepoImpl implements UserRepoCustom{
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public User addUser(String username, String password, String name, String className, int assignedMachineId,
			String role, int studentId) {
		
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
	public User findUserById(int studentId) {
		
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.id = :studentId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("studentId", studentId);
        
        List<User> lst = qr.getResultList();
        
		return lst.get(0);
	}


	@Override
	public User getLoginInfo(String username) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM User t ");
        sb.append("WHERE t.username = :username");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("username", username);
        
        List<User> lst = qr.getResultList();
        
		return lst.get(0);
	}
}
