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
import com.codeExecutor.dto.Output;
import com.codeExecutor.dto.OutputType;

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

	public void runJavaCode(String code, String name) throws IOException, InterruptedException {
		String dirName = "samples";
		String input = "Aditya";
		String fileName = randomFileTimeStampName();
		createFile(fileName, code, dirName, ".java", name);
		Output compileRes = javaExecutor.compile(fileName, name, dirName);
		if (compileRes.getType()==OutputType.SUCCESS) {
			createFile(fileName, input, dirName, "", "input");
			Output exeRes = javaExecutor.excute(fileName, name, dirName, "input", 3);
			System.out.println(exeRes.getType()+"\n"+exeRes.getOutput()+"time "+exeRes.getTime());
		}else {
			System.out.println(compileRes.getType()+"\n"+compileRes.getOutput());
		}

		cleanDirectory(dirName, fileName);
	}

	public void runCppCode(String code, String name, String lang) throws IOException, InterruptedException {
		String dirName = "samples";
		String input = "111";
		String fileName = randomFileTimeStampName();
		Output compileRes = null;
		Output exeRes = null;
		if (lang.equalsIgnoreCase("c")) {
			createFile(fileName, code, dirName, ".c", name);
		} else {
			createFile(fileName, code, dirName, ".cpp", name);
		}
		compileRes= cExecutor.compile(name, fileName, dirName, lang, 3);
		if(compileRes.getType() == OutputType.SUCCESS) {
			createFile(fileName, input, dirName, "", "input");
			exeRes = cExecutor.execute(fileName, name, dirName, "input", 3);
			System.out.println(exeRes.getType()+"\n"+exeRes.getOutput()+"time "+exeRes.getTime());
		}else {
			System.out.println(compileRes.getType()+"\n"+compileRes.getOutput());
		}
		cleanDirectory(dirName, fileName);
	}

	public void runCode(String code, String name, String lang) {
		try {
			if (lang.equalsIgnoreCase("java")) {
				runJavaCode(code, name);
			} else if (lang.equalsIgnoreCase("cpp") || lang.equalsIgnoreCase("c")) {
				runCppCode(code, name, lang);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
