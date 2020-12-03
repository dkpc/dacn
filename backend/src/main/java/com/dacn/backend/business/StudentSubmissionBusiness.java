package com.dacn.backend.business;

import java.io.File;

import org.springframework.http.ResponseEntity;

import com.dacn.backend.database.entity.Submission;

public interface StudentSubmissionBusiness {
	public ResponseEntity<Submission> submit(File file, int studentId, int machineId, int examId, String className) throws Exception;
}
