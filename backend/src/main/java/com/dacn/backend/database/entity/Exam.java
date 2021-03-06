package com.dacn.backend.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "EXAM", schema = "HDH")
public class Exam implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_id")
	private int examId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "one_way")
	private int oneWay;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOneWay() {
		return oneWay;
	}

	public void setOneWay(int oneWay) {
		this.oneWay = oneWay;
	}
	
	
}
