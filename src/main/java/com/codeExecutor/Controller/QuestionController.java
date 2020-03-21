package com.codeExecutor.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/question/{name}")
	public ResponseEntity<Question> getQuestion(@PathVariable String name) {
		Question question = questionDAO.getQuestion(name);
		if (question == null) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<Question>(question,HttpStatus.OK);
	}

	@PostMapping("/question")
	public ResponseEntity<String> addNewQuestion(@RequestBody Question question) {
		if(question == null) {
			return ResponseEntity.badRequest().build();
		}
		String returnMsg = questionDAO.addQuestion(question);
		if(returnMsg.equalsIgnoreCase("check file Name")) {
			return new ResponseEntity<String>("Invalid Question Name", HttpStatus.BAD_REQUEST);
		} else if (returnMsg.equalsIgnoreCase("Question already present")) {
			return new ResponseEntity<String>("Question already Present",HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Question Added",HttpStatus.CREATED);
	}
	
	@PostMapping("/testcases")
	public ResponseEntity addTestCases(@RequestBody List<TestCase> testCases) {
		if(testCases.size()==0) {
			return ResponseEntity.badRequest().build();
		}
		for (TestCase test : testCases) {
			testCaseDAO.addTestCase(test);
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/question/{name}")
	public ResponseEntity<String> deleteQuestion(String name) {
		try {
			questionDAO.deleteQuestion(name);
			testCaseDAO.deleteTestCases(name);
		}catch (Throwable e) {
			return new ResponseEntity<String>("Error deleting Question",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Deleted",HttpStatus.OK);
	}
}
