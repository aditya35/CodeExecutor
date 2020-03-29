package com.codeExecutor.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeExecutor.dao.QuestionDAO;
import com.codeExecutor.dao.TestCaseDAO;
import com.codeExecutor.model.Question;
import com.codeExecutor.model.TestCase;

@RestController
public class QuestionController {

	@Autowired
	private QuestionDAO questionDAO;
	
	@Autowired
	private TestCaseDAO testCaseDAO;

	@GetMapping("/questions")
	public List<Question> getAllQuestions() {
		return questionDAO.getAllQuestions();
	}

	@GetMapping("/question/{id}")
	public ResponseEntity<Question> getQuestion(@PathVariable long id) {
		Question question = questionDAO.getQuestion(id);
		if (question == null) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<Question>(question,HttpStatus.OK);
	}

	@PostMapping("/question")
	public ResponseEntity<Object> addNewQuestion(@RequestBody Question question) {
		if(question == null) {
			return ResponseEntity.badRequest().build();
		}
		questionDAO.addQuestion(question);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/question")
	public ResponseEntity<Object> updateQuestion(@RequestBody Question question) {
		questionDAO.addQuestion(question);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/testcases")
	public ResponseEntity<Object> addTestCases(@RequestBody List<TestCase> testCases) {
		if(testCases.size()==0) {
			return ResponseEntity.badRequest().build();
		}
		for (TestCase test : testCases) {
			testCaseDAO.addTestCase(test);
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/question/{name}")
	public ResponseEntity<String> deleteQuestion(long qId) {
		try {
			testCaseDAO.deleteTestCases(questionDAO.getQuestion(qId).getqName());
			questionDAO.deleteQuestion(qId);
		}catch (Throwable e) {
			return new ResponseEntity<String>("Error deleting Question",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Deleted",HttpStatus.OK);
	}
}
