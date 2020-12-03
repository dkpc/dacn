package com.dacn.backend.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacn.backend.database.entity.Exam;

public interface ExamRepo extends JpaRepository<Exam, Integer>, ExamRepoCustom{

}
