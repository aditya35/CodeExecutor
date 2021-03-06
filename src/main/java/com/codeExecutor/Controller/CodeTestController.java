package com.codeExecutor.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Output> run(@RequestBody Input input) throws IOException, InterruptedException {
        if (input == null) {
            return ResponseEntity.badRequest().build();
        }
        Question question = questionDAO.getQuestion(input.getQuestionId());
        int timeoutInSec = 5;
        Output runOutput = executor.compileRunSampleTestCases(input.getCode(), input.getLang(),
                input.getInput(), question.getsOutput(), timeoutInSec);
        return new ResponseEntity<Output>(runOutput, HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<List<Output>> submit(@RequestBody Input input) throws IOException, InterruptedException {
        if (input == null) {
            return ResponseEntity.badRequest().build();
        }
        int timeoutInSec = 5;
        List<TestCase> tesCases = testCaseDAO.getQuestionTestCases(input.getQuestionId());
        List<Output> outputList = executor.compileRunTestCases(input.getCode(), input.getLang(), tesCases, timeoutInSec);
        return new ResponseEntity<>(outputList, HttpStatus.OK);
    }

}
