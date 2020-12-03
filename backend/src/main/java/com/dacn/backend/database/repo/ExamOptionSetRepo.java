package com.dacn.backend.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacn.backend.database.entity.ExamOptionSet;

public interface ExamOptionSetRepo extends JpaRepository<ExamOptionSet, Integer>, ExamOptionSetRepoCustom{

}
