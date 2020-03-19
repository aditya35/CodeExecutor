package com.codeExecutor.ExecutorService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private Output compile(String code, String name, String lang, String dirName, String fileName)
			throws IOException, InterruptedException {
		Output compileResult = null;
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
		return compileResult;
	}

	private Output execute(String name, String lang, String input, String fileName, String dirName)
			throws IOException, InterruptedException {
		Output executionResult = null;
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
		return executionResult;
	}

	public Output runCode(String code, String name, String lang, String input)
			throws IOException, InterruptedException {
		String dirName = "samples";
		String fileName = randomFileTimeStampName();
		Output compileResult = null;
		Output executionResult = null;

		// compile
		compileResult = compile(code, name, lang, dirName, fileName);

		if (compileResult.getType() == OutputType.SUCCESS) {

			// execute
			executionResult = execute(name, lang, input, fileName, dirName);
//			System.out.println(exeRes.getType() + "\n" + exeRes.getOutput() + "time " + exeRes.getTime());
			cleanDirectory(dirName, fileName);
			return executionResult;
		} else {
//			System.out.println(compileRes.getType() + "\n" + compileRes.getOutput());
			cleanDirectory(dirName, fileName);
			return compileResult;
		}

	}

	private boolean comareOutputs(String actual, String expexted) {
		return actual.trim().compareTo(expexted.trim()) == 0;
	}

	public Output compileRunSampleTestCases(String code, String name, String lang, String input, String output)
			throws IOException, InterruptedException {
		String dirName = "samples";
		String fileName = randomFileTimeStampName();
		Output compileResult = null;
		Output executionResult = null;

		// compile
		compileResult = compile(code, name, lang, dirName, fileName);

		if (compileResult.getType() == OutputType.SUCCESS) {

			// execute
			executionResult = execute(name, lang, input, fileName, dirName);

			// check expected output and actual output
			if (executionResult.getType() == OutputType.SUCCESS) {

				if (!comareOutputs(executionResult.getOutput(), output)) {
					executionResult.setOutput("Sample Test Case Failed");
					executionResult.setType(OutputType.FAILURE);
				}
			}

			cleanDirectory(dirName, fileName);
			return executionResult;

		} else {

			// compilation errors
			cleanDirectory(dirName, fileName);
			return compileResult;
		}

	}

	public List<Output> compileRunTestCases(String code, String name, String lang, List<String> inputList,
			List<String> outputList) throws IOException, InterruptedException {

		String dirName = "samples";
		String fileName = randomFileTimeStampName();
		Output compileResult = null;
		Output executionResult = null;

		// compile
		compileResult = compile(code, name, lang, dirName, fileName);

		if (compileResult.getType() == OutputType.SUCCESS) {

			int nTests = Math.min(inputList.size(), outputList.size());
			List<Output> executionOutputs = new ArrayList<>();

			for (int i = 0; i < nTests; i++) {

				// execute
				executionResult = execute(name, lang, inputList.get(i), fileName, dirName);

				// check expected output and actual output
				if (executionResult.getType() == OutputType.SUCCESS) {

					if (!comareOutputs(executionResult.getOutput(), outputList.get(i))) {
						executionResult.setOutput("Sample Test Case Failed");
						executionResult.setType(OutputType.FAILURE);
					}
				}

				executionOutputs.add(executionResult);
			}

			cleanDirectory(dirName, fileName);
			return executionOutputs;

		} else {

			// compilation errors
			cleanDirectory(dirName, fileName);
			return Arrays.asList(new Output[] { executionResult });
		}

	}

}
