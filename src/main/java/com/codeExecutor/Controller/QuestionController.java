package com.codeExecutor.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeExecutor.dao.QuestionDAO;
import com.codeExecutor.model.Question;

@RestController
public class QuestionController {

	@Autowired
	private QuestionDAO questionDAO;

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
		System.out.println(question);
		return questionDAO.addQuestion(question);
	}

	@DeleteMapping("/question/{name}")
	public void deleteQuestion(String name) {
		if (!questionDAO.deleteQuestion(name)) {
			throw new RuntimeException("Can't delete Question");
		}
	}
}
