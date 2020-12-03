package com.dacn.backend.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacn.backend.database.entity.Submission;

public interface SubmissionRepo extends JpaRepository<Submission, Integer>, SubmissionRepoCustom{

}
