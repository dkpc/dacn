package com.dacn.backend.business;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dacn.backend.common.TextUtil;
import com.dacn.backend.database.entity.Exam;
import com.dacn.backend.database.entity.Submission;
import com.dacn.backend.database.entity.User;
import com.dacn.backend.database.repo.SubmissionRepo;
import com.dacn.backend.database.repo.UserRepo;

@Service
public class StudentSubmissionBusinessImpl implements StudentSubmissionBusiness{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ExamManageBusiness examManageBusiness;
	
	@Autowired
	private SubmissionRepo submissionRepo;

	@Autowired
	private GradingBusiness gradingBusiness;
	
	@Override
	@Transactional
	public ResponseEntity<Submission> submit(File file, int studentId, int machineId, int examId, String className) throws Exception {

		User user = userRepo.findUserByStudentId(studentId);
		if (user == null) {
			throw new Exception("User not found");
		}
//		if (user.getAssignedMachineId() != machineId) {
//			throw new Exception();
//		}
		Exam exam = examManageBusiness.findExamById(examId);
		if (exam == null) {
			throw new Exception("Exam not found");
		}
		
		List<String> ans = TextUtil.dataToList(file);
		int mark = gradingBusiness.grading(examId, ans);
		Submission sub;
		Submission tempSub = submissionRepo.getSubByIds(studentId, examId);
		if (tempSub == null) {
			sub = submissionRepo.addSubmission(studentId, examId, TextUtil.getDataFromTxt(file), mark);
		} else {
			sub = submissionRepo.addSubmission(tempSub.getId(),studentId, examId, TextUtil.getDataFromTxt(file), mark);
		}
		
		if (sub == null) {
			
		}
		return new ResponseEntity<Submission>(sub ,HttpStatus.OK);
	}

}
