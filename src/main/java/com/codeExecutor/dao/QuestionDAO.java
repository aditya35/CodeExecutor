package com.codeExecutor.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codeExecutor.model.Question;

@Repository
public class QuestionDAO {
	
	@Autowired
	private EntityManager entitiyManager;
	
	private Session getCurrSession() {
		return entitiyManager.unwrap(Session.class);
	}
	
	@Transactional
	public List<Question> getAllQuestions(){
		Session currentSession = getCurrSession();
		Query<Question> query  = currentSession.createQuery("From Question",Question.class);
		List <Question> questions = query.getResultList();
		return questions;
	}
	
	@Transactional
	public Question getQuestion(long id) {
		Session currentSession =  getCurrSession();
		Question question = currentSession.get(Question.class, id);
		return question;
	}
	
	@Transactional
	public void addQuestion(Question question) {
		Session currSession = getCurrSession();
		currSession.saveOrUpdate(question);
	}
	
	@Transactional
	public boolean deleteQuestion(long id) {
		Session currentSession= getCurrSession();
		Question question = currentSession.get(Question.class,id);
		if(question==null) {
			return false;
		}
		currentSession.delete(question);
		return true;
	}

}
