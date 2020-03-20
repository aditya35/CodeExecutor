package com.codeExecutor.Controller;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public Question getQuestion(@PathVariable String name) {
		Question question = questionDAO.getQuestion(name);
		if (question == null) {
			throw new RuntimeException("Question not Found");
		}
		return question;
	}

	@PostMapping("/question")
	public String addNewQuestion(@RequestBody Question question) {
		return questionDAO.addQuestion(question);
//		if(returnMsg.equalsIgnoreCase("check file Name")) {
//			return ResponseEntity.status("check fileName",HttpStatus.BAD_REQUEST).build();
//		}
	}
	
	@PostMapping("/testcases")
	public void addTestCases(@RequestBody List<TestCase> testCases) {
		/*TODO  add batch processing support*/
		for (TestCase test : testCases) {
			testCaseDAO.addTestCase(test);
		}
	}

	@DeleteMapping("/question/{name}")
	public void deleteQuestion(String name) {
		try {
			questionDAO.deleteQuestion(name);
			testCaseDAO.deleteTestCases(name);
		}catch (Throwable e) {
			throw e;
		}
	}
}
