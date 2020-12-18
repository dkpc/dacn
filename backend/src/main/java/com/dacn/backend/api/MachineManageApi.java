package com.dacn.backend.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dacn.backend.business.MachineManageBusiness;
import com.dacn.backend.database.entity.Machine;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(value = "MachineManage APIs")
public class MachineManageApi {
	
	@Autowired
	private MachineManageBusiness machineBusiness;

	@ApiOperation(value = "Submit file for student submission and adding student list", response = ResponseEntity.class)
	@RequestMapping(value = "/machine/deleteAll", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAll() {
		machineBusiness.deleteAllMachine();
		return new ResponseEntity<Object>(HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Submit file for student submission and adding student list", response = ResponseEntity.class)
	@GetMapping(value = "/machine/createMachineWithClassname/{classname}")
	public ResponseEntity<Object> createMachineWithClassname(@PathVariable String classname) {
		return new ResponseEntity<Object>(machineBusiness.createMachineWithClassname(classname), HttpStatus.OK);	
	}
	
	@ApiOperation(value = "Submit file for student submission and adding student list", response = ResponseEntity.class)
	@GetMapping(value = "/machine/getAllMachine")
	public ResponseEntity<List<Machine>> getCount() {
		return machineBusiness.getAllMachine();	
	}
}
