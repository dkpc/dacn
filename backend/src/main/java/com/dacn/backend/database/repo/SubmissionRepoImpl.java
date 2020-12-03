package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.Submission;

@SuppressWarnings("unchecked")
public class SubmissionRepoImpl implements SubmissionRepoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private SubmissionRepo submissionRepo;

	@Override
	public Submission addSubmission(int studentId, int examId, String answer, int mark) {
		Submission sub = new Submission();
		sub.setStudentId(studentId);
		sub.setExamId(examId);
		sub.setAnswer(answer);
		sub.setMark(mark);
		
		return submissionRepo.save(sub);
	}

	
	@Override
	public Submission getSubByIds(int studentId, int examId) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM Submission t ");
        sb.append("WHERE t.examId = :examId ");   
        sb.append("AND t.studentId = :studentId");
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("examId", examId);
        qr.setParameter("studentId", studentId);
            
		List<Submission> lst = qr.getResultList();
        
        return lst.get(0);
	}


	@Override
	public Submission addSubmission(int id, int studentId, int examId, String answer, int mark) {
		Submission sub = new Submission();
		sub.setId(examId);
		sub.setStudentId(studentId);
		sub.setExamId(examId);
		sub.setAnswer(answer);
		sub.setMark(mark);
		
		return submissionRepo.save(sub);
	}

}
