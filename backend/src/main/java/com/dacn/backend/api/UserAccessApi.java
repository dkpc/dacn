package com.dacn.backend.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacn.backend.business.UserAccessBusiness;
import com.dacn.backend.database.entity.User;
import com.dacn.backend.request.AssignMachineReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(value = "User APIs")
public class UserAccessApi {

	@Autowired
	private UserAccessBusiness userAccessBusiness;
	
	@ApiOperation(value = "Add user", response = ResponseEntity.class)
	@PostMapping("/user/add")
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		return userAccessBusiness.addUser(
				user.getUsername(),
				user.getPassword(),
				user.getName(),
				user.getClassName(),
				user.getAssignedMachineId(),
				user.getRole(),
				user.getStudentId()
				);
	}
	
	@ApiOperation(value = "Find user by id", response = ResponseEntity.class)
	@GetMapping("/user/findById/{studentId}")
	public ResponseEntity<User> findById(@PathVariable int studentId) throws Exception {
		return userAccessBusiness.findUserById(studentId);
	}
	
	@ApiOperation(value = "Assign user to machine", response = ResponseEntity.class)
	@PostMapping("/user/assignMachine")
	public ResponseEntity<List<User>> assignMachine(@RequestBody AssignMachineReq req) throws Exception{
		return userAccessBusiness.assignMachine(req.getAssignMachineList());
	}
	
	@ApiOperation(value = "Login", response = ResponseEntity.class)
	@PostMapping("/user/login")
	public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) throws Exception{
		return userAccessBusiness.login(username, password);
	}
}
