package com.dacn.backend.api;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dacn.backend.business.StudentSubmissionBusiness;
import com.dacn.backend.business.UserAccessBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(value = "Submission APIs")
public class SubmissionApi {
	
	@Autowired
	private StudentSubmissionBusiness studentSubmissionBusiness;
	
	@Autowired
	private UserAccessBusiness userAccessBusiness;
	
	@ApiOperation(value = "Submit file for student submission and adding student list", response = ResponseEntity.class)
	@RequestMapping(value = "/submit/submit", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> submitText(@RequestParam MultipartFile file, @RequestParam int studentId,
			@RequestParam int machineId, @RequestParam int examId, @RequestParam String classname) throws Exception {
		if (file.getOriginalFilename().endsWith(".txt")) {
			File converted = new File(System.getProperty("user.dir") + "/uploads");
			file.transferTo(converted);
			return new ResponseEntity<>(studentSubmissionBusiness.submit(converted, studentId, machineId, examId, classname), HttpStatus.OK);	
		}
		return new ResponseEntity<>(userAccessBusiness.insertStudentList(file, classname), HttpStatus.OK);	
	}
}
