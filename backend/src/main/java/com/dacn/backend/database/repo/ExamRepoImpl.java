package com.dacn.backend.database.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.dacn.backend.database.entity.Exam;

@SuppressWarnings("unchecked")
public class ExamRepoImpl implements ExamRepoCustom{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ExamRepo examRepo;

	@Override
	public Exam findExamById(int examId) {
		
		StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT t FROM Exam t ");
        sb.append("WHERE t.id = :examId");   
		
        Query qr = em.createQuery(sb.toString());
        
        qr.setParameter("examId", examId);
            
		List<Exam> lst = qr.getResultList();
        
        return lst.get(0);
	}

	@Override
	public Exam addExam(String description, int oneWay) {
		Exam exam = new Exam();
		exam.setDescription(description);
		exam.setOneWay(oneWay);
		examRepo.save(exam);
		
		return exam;
	}

}
