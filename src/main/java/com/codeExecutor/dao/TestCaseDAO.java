package com.codeExecutor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codeExecutor.model.Question;
import com.codeExecutor.model.TestCase;

@Repository
public class TestCaseDAO {

	@Autowired
	private EntityManager entityManager;
	
	private Session getCurrSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Transactional
	public List<TestCase> getQuestionTestCases(long qId) {
		Session currSession = getCurrSession();
		Query<TestCase> query = currSession.createQuery("from TestCase t where t.name = :name",TestCase.class);
		query.setParameter("name", qId);
		List<TestCase> testCases = query.getResultList();
		return testCases;
	}
	
	@Transactional
	public void addTestCase(TestCase test) {
		Session currSession  = getCurrSession();
		currSession.saveOrUpdate(test);
	}
	
	@Transactional
	public void deleteTestCases(String qName) {
		Session cuSession = getCurrSession();
		String deleteQuestionTestCases = "delete from TestCase where name = :name";
		Query query = cuSession.createQuery(deleteQuestionTestCases);
		query.setParameter("name", qName);
		query.executeUpdate();
	}
}
