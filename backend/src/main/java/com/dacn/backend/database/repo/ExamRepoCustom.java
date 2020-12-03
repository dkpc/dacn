package com.dacn.backend.database.repo;

import com.dacn.backend.database.entity.Exam;

public interface ExamRepoCustom {
	public Exam findExamById(int examId);
	public Exam addExam(String description, int oneWay);
}
