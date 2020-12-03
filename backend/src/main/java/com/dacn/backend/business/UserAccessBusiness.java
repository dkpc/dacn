package com.dacn.backend.business;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dacn.backend.BO.AssignMachineBO;
import com.dacn.backend.database.entity.User;

public interface UserAccessBusiness {
	
	public ResponseEntity<User> addUser(String username, String password, String name, String className, int assignedMachineId,
			String role ,int studentId) throws Exception;

	public ResponseEntity<User> findUserById(int studentId) throws Exception;
	
	public ResponseEntity<List<User>> insertStudentList(MultipartFile file, String className) throws Exception;
	
	public ResponseEntity<List<User>> assignMachine(List<AssignMachineBO> lst) throws Exception;
}
