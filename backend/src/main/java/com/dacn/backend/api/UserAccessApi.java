package com.dacn.backend.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
		return new ResponseEntity<>(userAccessBusiness.addUser(
				user.getUsername(),
				user.getPassword(),	
				user.getName(),
				user.getClassName(),
				user.getAssignedMachineId(),
				user.getRole(),
				user.getStudentId()
				), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find user by id", response = ResponseEntity.class)
	@GetMapping("/user/findById/{studentId}")
	public ResponseEntity<User> findById(@PathVariable int studentId) throws Exception {
		return new ResponseEntity<>(userAccessBusiness.findUserByStudentId(studentId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Assign user to machine", response = ResponseEntity.class)
	@PostMapping("/user/assignMachine")
	public ResponseEntity<List<User>> assignMachine(@RequestBody AssignMachineReq req) throws Exception{
		return new ResponseEntity<>(userAccessBusiness.assignMachine(req.getAssignMachineList()), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Login", response = ResponseEntity.class)
	@PostMapping("/user/login/")
	public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) throws Exception{
		return new ResponseEntity<>(userAccessBusiness.login(username, password), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete by classname", response = ResponseEntity.class)
	@DeleteMapping("/user/deleteByClassname/{classname}")
	public ResponseEntity<Object> login(@PathVariable String classname) throws Exception{
		return new ResponseEntity<>(userAccessBusiness.deleteByClassname(classname), HttpStatus.OK);
	}
}
