package com.codeExecutor.ExecutorService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeExecutor.ExecutorService.Executors.CExecutor;
import com.codeExecutor.ExecutorService.Executors.JavaExecutor;
import com.codeExecutor.model.Output;
import com.codeExecutor.model.OutputType;

@Component
public class CodeExecutor {

	@Autowired
	private JavaExecutor javaExecutor;

	@Autowired
	private CExecutor cExecutor;

	private static String randomFileTimeStampName() {
		return RandomStringUtils.randomAlphabetic(16);
	}

	private static void createFile(String fileName, String code, String dir, String ext, String name)
			throws IOException {
		File tempDir = new File(dir + "/" + fileName + "/");
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		String filepath = dir + "/" + fileName + "/" + name + ext;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath)))) {
			writer.write(code);
		}
	}

	private static void cleanDirectory(String dir, String fileName) throws IOException {
		String dirPath = dir + "/" + fileName;
		FileUtils.deleteDirectory(new File(dirPath));
	}

	public Output runCode(String code, String name, String lang,String input) throws IOException, InterruptedException {
		String dirName = "samples";
		String fileName = randomFileTimeStampName();
		Output compileResult = null;
		Output executionResult = null;

		//compile
		switch (lang) {
		case "java":
			createFile(fileName, code, dirName, ".java", name);
			compileResult = javaExecutor.compile(name, fileName, dirName);
			break;
		case "c":
			createFile(fileName, code, dirName, ".c", name);
			compileResult = cExecutor.compile(name, fileName, dirName, lang, 3);
			break;
		case "cpp":
			createFile(fileName, code, dirName, ".cpp", name);
			compileResult = cExecutor.compile(name, fileName, dirName, lang, 3);
			break;
		}
		
		//execute
		if (compileResult.getType() == OutputType.SUCCESS) {
			createFile(fileName, input, dirName, "", "input");
			switch (lang) {
			case "java":
				executionResult = javaExecutor.execute(name, fileName, dirName, "input", 3);
				break;
			case "c":
				executionResult = cExecutor.execute(name, fileName, dirName, "input", 3);
				break;
			case "cpp":
				executionResult = cExecutor.execute(name, fileName, dirName, "input", 3);
				break;
			}
//			System.out.println(exeRes.getType() + "\n" + exeRes.getOutput() + "time " + exeRes.getTime());
			cleanDirectory(dirName, fileName);
			return executionResult;
		} else {
//			System.out.println(compileRes.getType() + "\n" + compileRes.getOutput());
			cleanDirectory(dirName, fileName);
			return compileResult;
		}
		
	}

}
