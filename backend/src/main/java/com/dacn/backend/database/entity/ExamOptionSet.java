package com.dacn.backend.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exam_option_set")
public class ExamOptionSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "exam_id")
	private int examId;
	
	@Column(name = "expected_output")
	private String expectedOutput;
	
	@Column(name = "expected_answer")
	private String expectedAnswer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public String getExpectedAnswer() {
		return expectedAnswer;
	}

	public void setExpectedAnswer(String expectedAnswer) {
		this.expectedAnswer = expectedAnswer;
	}
	
}
