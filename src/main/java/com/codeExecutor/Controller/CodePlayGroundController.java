package com.codeExecutor.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codeExecutor.ExecutorService.CodeExecutor;
import com.codeExecutor.model.Input;
import com.codeExecutor.model.Output;

@RestController
public class CodePlayGroundController {
	
	@Autowired
	private CodeExecutor codeExecutor;

	@PostMapping("/playground")
	public ResponseEntity<Output> playGround( @RequestBody Input input) throws IOException, InterruptedException {
			Output playGroundOutput = codeExecutor.runCode(input.getCode(), "Sample", input.getLang(), input.getInput());
			return new ResponseEntity<Output>(playGroundOutput, HttpStatus.OK);
	}
	
	
	
	
}
