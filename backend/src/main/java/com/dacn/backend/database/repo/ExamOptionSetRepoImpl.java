package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dacn.backend.database.entity.ExamOptionSet;

@SuppressWarnings("unchecked")
public class ExamOptionSetRepoImpl implements ExamOptionSetRepoCustom{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ExamOptionSet> getExamOptionSet(int examId) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM ExamOptionSet t ");
        sb.append("WHERE t.examId = :examId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("examId", examId);
            
		List<ExamOptionSet> lst = qr.getResultList();
        
        return lst;
	}

	@Override
	public List<String> getExpectedOutput(int examId) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t.expectedOutput FROM ExamOptionSet t ");
        sb.append("WHERE t.examId = :examId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("examId", examId);
            
		List<String> lst = qr.getResultList();
        
        return lst;
	}
	@Override
	public List<String> getExpectedAnswer(int examId) {
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t.expectedAnswer FROM ExamOptionSet t ");
        sb.append("WHERE t.examId = :examId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("examId", examId);
            
		List<String> lst = qr.getResultList();
        
        return lst;
	}
}
