package com.dacn.backend.database.repo;

import com.dacn.backend.database.entity.Submission;

public interface SubmissionRepoCustom {
	public Submission addSubmission(int id, int studentId, int examId, String answer, int mark);
	
	public Submission addSubmission(int studentId, int examId, String answer, int mark);
	
	public Submission getSubByIds(int studentId, int examId);
}
