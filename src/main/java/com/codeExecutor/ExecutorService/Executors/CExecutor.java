package com.codeExecutor.ExecutorService.Executors;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.codeExecutor.dto.Output;
import com.codeExecutor.dto.OutputType;

@Component
public class CExecutor {

	public Output compile(String name, String fileName, String dir, String lang, int timeout)
			throws IOException, InterruptedException {
		String runScript = null;
		ProcessBuilder builder = new ProcessBuilder();
		switch (lang) {
		case "c":
			runScript = "gcc " + dir + "/" + fileName + "/" + name + ".c" + " -o " + dir + "/" + fileName + "/" + name;
			break;
		case "cpp":
			runScript = "g++ " + dir + "/" + fileName + "/" + name + ".cpp" + " -o " + dir + "/" + fileName + "/"
					+ name;
			break;
		}

		// compile
		builder.command("bash", "-c", runScript);
		Process p = builder.start();

		StringBuilder errorBuiler = new StringBuilder();
		Scanner error = new Scanner(p.getErrorStream());
		if (error.hasNext()) {
			while (error.hasNext()) {
				errorBuiler.append(error.nextLine() + "\n");
			}
			p.destroy();
			error.close();
			return new Output(errorBuiler.toString(), OutputType.COMPILATION_ERROR);
		}
		error.close();
		return new Output("COMPILATION SUCCESS", OutputType.SUCCESS);

	}

	public Output execute(String fileName, String name, String dir, String inputFileName, int timeout)
			throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		String runScript;
		if (inputFileName == null) {
			runScript = dir + "/" + fileName + "/" + name;
		} else {
			runScript = dir + "/" + fileName + "/" + name + " < " + dir + "/" + fileName + "/" + inputFileName;
		}

		// execute
		builder.command("bash", "-c", runScript);
		long startTime = System.currentTimeMillis();
		Process p = builder.start();
		p.waitFor(timeout, TimeUnit.SECONDS);
		long endTime = System.currentTimeMillis();

		//TimeOut
		if (endTime - startTime > timeout*1000) {
			p.destroy();
			if (p.isAlive()) {
				p.destroyForcibly();
			}
			return new Output("TIMEOUT", OutputType.TIMEOUT);
			
		} else {
			double  exTime = (double) (endTime - startTime) / 1000 ;
			
			// Runtime Error
			StringBuilder errorBuilder = new StringBuilder();
			Scanner error = new Scanner(p.getErrorStream());
			if (error.hasNext()) {
				while (error.hasNext()) {
					errorBuilder.append(error.nextLine() + "\n");
				}
				error.close();
				return new Output(errorBuilder.toString(), OutputType.RUNTIME_ERROR);
			}

			// Output
			StringBuilder outputBuilder = new StringBuilder();
			Scanner output = new Scanner(p.getInputStream());
			System.out.println("Output");
			while (output.hasNext()) {
				outputBuilder.append(output.nextLine() + "\n");
			}
			error.close();
			output.close();
			return new Output(outputBuilder.toString(), OutputType.SUCCESS,exTime);
		}
	}

}
