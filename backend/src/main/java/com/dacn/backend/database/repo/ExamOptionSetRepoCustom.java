package com.dacn.backend.database.repo;

import java.util.List;

import com.dacn.backend.database.entity.ExamOptionSet;

public interface ExamOptionSetRepoCustom {
	public List<ExamOptionSet> getExamOptionSet(int examId);
	
	public List<String> getExpectedOutput(int examId);
	public List<String> getExpectedAnswer(int examId);
}
