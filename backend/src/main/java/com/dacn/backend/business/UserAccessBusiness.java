package com.dacn.backend.business;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dacn.backend.BO.AssignMachineBO;
import com.dacn.backend.database.entity.User;

public interface UserAccessBusiness {
	
	public User addUser(String username, String password, String name, String className, int assignedMachineId,
			String role ,int studentId) throws Exception;

	public User findUserByStudentId(int studentId) throws Exception;
	
	public List<User> insertStudentList(MultipartFile file, String className) throws Exception;
	
	public List<User> assignMachine(List<AssignMachineBO> lst) throws Exception;
	
	public Object login(String username, String password) throws Exception;
	
	public Object deleteByClassname(String classname);
}
