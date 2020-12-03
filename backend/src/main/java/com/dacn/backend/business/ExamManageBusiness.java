package com.dacn.backend.business;

import com.dacn.backend.database.entity.Exam;

public interface ExamManageBusiness {
	public Exam addExam(String description, int oneWay) throws Exception;
	public Exam findExamById(int id);
}
