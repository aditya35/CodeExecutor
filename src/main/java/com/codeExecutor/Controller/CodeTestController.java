package com.codeExecutor.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeExecutor.ExecutorService.CodeExecutor;
import com.codeExecutor.dao.QuestionDAO;
import com.codeExecutor.dao.TestCaseDAO;
import com.codeExecutor.model.Output;
import com.codeExecutor.model.Question;
import com.codeExecutor.model.TestCase;
import com.codeExecutor.model.Input;

@RestController
public class CodeTestController {

	@Autowired
	private CodeExecutor executor;

	@Autowired
	private QuestionDAO questionDAO;

	@Autowired
	private TestCaseDAO testCaseDAO;

	@PostMapping("/run")
	public Output run(@RequestBody Input input) throws IOException, InterruptedException {
		Question question = questionDAO.getQuestion(input.getqName());
		return executor.compileRunSampleTestCases(input.getCode(), input.getqName(), input.getLang(),
				question.getsInput(), question.getsOutput());
	}
	
	@PostMapping("/submit")
	public List<Output> submit(@RequestBody Input input) throws IOException, InterruptedException{
		List<TestCase> tesCases = testCaseDAO.getQuestionTestCases(input.getqName());
		return executor.compileRunTestCases(input.getCode(), input.getqName(), input.getLang(), tesCases);
	}

}
