package com.dacn.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacn.backend.database.entity.Exam;
import com.dacn.backend.database.repo.ExamRepo;

@Service
public class ExamManageBusinessImpl implements ExamManageBusiness{
	
	@Autowired
	private ExamRepo examRepo;

	@Override
	public Exam addExam(String description, int oneWay) throws Exception {
		Exam exam = examRepo.addExam(description, oneWay);
		if (exam == null) {
			throw new Exception();
		}
		return exam;
	}

	@Override
	public Exam findExamById(int examId) {
		return examRepo.findExamById(examId);
	}
	
}
