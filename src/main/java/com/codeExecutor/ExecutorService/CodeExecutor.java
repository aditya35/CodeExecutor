package com.codeExecutor.ExecutorService;

import com.codeExecutor.ExecutorService.Executors.CExecutor;
import com.codeExecutor.ExecutorService.Executors.JavaExecutor;
import com.codeExecutor.model.Output;
import com.codeExecutor.model.OutputType;
import com.codeExecutor.model.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CodeExecutor {

    @Autowired
    private JavaExecutor javaExecutor;

    @Autowired
    private CExecutor cExecutor;

    private static String randomFileTimeStampName() {
        return RandomStringUtils.randomAlphabetic(16);
    }

    private static void createFile(String rootDirName, String dirName, String fileName, String ext, String code)
            throws IOException {
        File tempDir = new File(rootDirName + "/" + dirName + "/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        String filepath = rootDirName + "/" + dirName + "/" + fileName + ext;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath)))) {
            writer.write(code);
        }
    }

    private static void cleanDirectory(String rootDirName, String dirName) throws IOException {
        String dirPath = rootDirName + "/" + dirName;
        FileUtils.deleteDirectory(new File(dirPath));
    }

    private String extractClassName(String code) {
        String[] wordArray = code.split("\\s");
        List<String> words = Stream.of(wordArray).filter(s -> s != null && !s.isEmpty()).collect(Collectors.toList());
        for (int i = 1; i < words.size(); i++) {
            if (words.get(i - 1).equals("public") && words.get(i).equals("class") && words.size() > i + 1) {
                return words.get(i + 1).replace("{", "");
            }
        }
        return null;
    }


    private Output compile(String rootDirName, String testDirName, String code, String lang)
			throws IOException, InterruptedException {
        Output compileOutput = null;
        String name = "Solution";
        switch (lang) {
            case "java":
                String className = extractClassName(code);
                if (className == null) {
                    return new Output("Can't find class", OutputType.COMPILATION_ERROR);
                }
                createFile(rootDirName, testDirName, className, ".java", code);
                compileOutput = javaExecutor.compile(rootDirName, testDirName, className);
                break;
            case "c":
                createFile(rootDirName, testDirName, name, ".c", code);
                compileOutput = cExecutor.compile(rootDirName, testDirName, name, lang, 3);
                break;
            case "cpp":
                createFile(rootDirName, testDirName, name, ".cpp", code);
                compileOutput = cExecutor.compile(rootDirName, testDirName, name, lang, 3);
                break;
        }
        return compileOutput;
    }

    private Output execute(String rootDirName, String testDirName, String code, String input, String lang, int timeout)
            throws IOException, InterruptedException {
        Output executionResult = null;
        String name = "Solution";
        String inputFileName = null;
        if (input != null) {
            inputFileName = "input";
            createFile(rootDirName, testDirName, inputFileName, "", input);
        }
        switch (lang) {
            case "java":
                String className = extractClassName(code);
                executionResult = javaExecutor.execute(rootDirName, testDirName, className, inputFileName, timeout);
                break;
            case "c":
			case "cpp":
				executionResult = cExecutor.execute(rootDirName, testDirName, name, inputFileName, timeout);
                break;
		}
        return executionResult;
    }

    public Output runCode(String code, String lang, String input, int timeout)
            throws IOException, InterruptedException {
        String rootDirName = "Sample";
        String testDirName = randomFileTimeStampName();
        Output compileResult;
        Output executionResult;

        // compile
        compileResult = compile(rootDirName, testDirName, code, lang);

        if (compileResult.getType() == OutputType.SUCCESS) {

            // execute
            executionResult = execute(rootDirName, testDirName, code, input, lang, timeout);
//			System.out.println(exeRes.getType() + "\n" + exeRes.getOutput() + "time " + exeRes.getTime());
            cleanDirectory(rootDirName, testDirName);
            return executionResult;
        } else {
//			System.out.println(compileRes.getType() + "\n" + compileRes.getOutput());
            cleanDirectory(rootDirName, testDirName);
            return compileResult;
        }
    }

    private boolean comareOutputs(String actual, String expexted) {
        return actual.trim().compareTo(expexted.trim()) == 0;
    }

    public Output compileRunSampleTestCases(String code, String lang, String input, String output, int timeout)
            throws IOException, InterruptedException {
        String rootDirName = "samples";
        String testDirName = randomFileTimeStampName();
        Output compileResult;
        Output executionResult;

        // compile
        compileResult = compile(rootDirName, testDirName, code, lang);

        if (compileResult.getType() == OutputType.SUCCESS) {

            // execute
            executionResult = execute(rootDirName, testDirName, code, input, lang, timeout);

            // check expected output and actual output
            if (executionResult.getType() == OutputType.SUCCESS) {

                if (!comareOutputs(executionResult.getOutput(), output)) {
                    executionResult.setOutput("Sample Test Case Failed");
                    executionResult.setType(OutputType.FAILURE);
                }
            }

            cleanDirectory(rootDirName, testDirName);
            return executionResult;

        } else {

            // compilation errors
            cleanDirectory(rootDirName, testDirName);
            return compileResult;
        }

    }

    public List<Output> compileRunTestCases(String code, String lang, List<TestCase> testCases, int timeout) throws IOException, InterruptedException {

        String rootDirName = "samples";
        String testDirName = randomFileTimeStampName();
        Output compileResult;
        Output executionResult;

        // compile
        compileResult = compile(rootDirName, testDirName, code, lang);

        if (compileResult.getType() == OutputType.SUCCESS) {

            int nTests = testCases.size();
            List<Output> executionOutputs = new ArrayList<>();

            for (int i = 0; i < nTests; i++) {

                // execute
                executionResult = execute(rootDirName, testDirName, code, testCases.get(i).getInput(), lang, timeout);

                // check expected output and actual output
                if (executionResult.getType() == OutputType.SUCCESS) {
                    if (!comareOutputs(executionResult.getOutput(), testCases.get(i).getOutput())) {
                        executionResult.setOutput("Test Case " + i + " Failed");
                        executionResult.setType(OutputType.FAILURE);
                    }else {
						executionResult.setOutput("Test Case " + i + " Passed");
						executionResult.setType(OutputType.SUCCESS);
					}
                }
                executionOutputs.add(executionResult);
            }

            cleanDirectory(rootDirName, testDirName);
            return executionOutputs;

        } else {

            // compilation errors
            cleanDirectory(rootDirName, testDirName);
            return Collections.singletonList(compileResult);
        }
    }

}
