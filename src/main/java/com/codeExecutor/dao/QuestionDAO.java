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
	public Question getQuestion(String name) {
		Session currentSession =  getCurrSession();
		Question question = currentSession.get(Question.class, name);
		return question;
	}
	
	public boolean checkName(String name) {
		if(name.indexOf(" ")==-1) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public String addQuestion(Question question) {
		if(!checkName(question.getName())) {
			return "check file Name";
		}
		Session currentSession = getCurrSession();
		if(currentSession.contains(question)) {
			return "Question already present";
		}
		currentSession.save(question);
		return "Question added";
	}
	
	@Transactional
	public boolean deleteQuestion(String name) {
		Session currentSession= getCurrSession();
		Question question = currentSession.get(Question.class, name);
		if(question==null) {
			return false;
		}
		currentSession.delete(question);
		return true;
	}

}
