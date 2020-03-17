package com.codeExecutor.ExecutorService.Executors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.stereotype.Component;

import com.codeExecutor.dto.Output;
import com.codeExecutor.dto.OutputType;

@Component
public class JavaExecutor {


	public Output compile(String fileName, String name, String dir) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = -1;
		ByteArrayOutputStream streamErr = new ByteArrayOutputStream();
		String filepath = dir + "/" + fileName + "/" + name + ".java";

		result = compiler.run(null, null, streamErr, filepath);
		if (result == 0) {
			return new Output("COMPILATION SUCCESS", OutputType.SUCCESS);
		}

		String error = new String(streamErr.toByteArray());
		return new Output(error, OutputType.COMPILATION_ERROR);

	}

	public Output excute(String fileName, String name, String dir, String inputFileName, int timeout)
			throws InterruptedException, IOException {
		String runScript;
		if (inputFileName == null) {
			runScript = "java -cp " + dir + "/" + fileName + "/ " + name;
		} else {
			runScript = "java -cp " + dir + "/" + fileName + "/ " + name + " < " + dir + "/" + fileName + "/"
					+ inputFileName;
		}
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c", runScript);
		long startTime = System.currentTimeMillis();
		// start the process
		Process p = builder.start();
		// wait for execution to complete until timeout
		p.waitFor(timeout, TimeUnit.SECONDS);
		long endTime = System.currentTimeMillis();

		// check timeout
		if ((endTime - startTime) > timeout * 1000) {
			p.destroy();
			if (p.isAlive()) {
				p.destroyForcibly();
			}
			return new Output("TIMEOUT", OutputType.TIMEOUT);
		} else {
			double  exTime = (double) (endTime - startTime) / 1000 ;

			Scanner error = new Scanner(p.getErrorStream());
			StringBuilder errorBuilder  = new StringBuilder();
			//check Runtime Errors
			if (error.hasNext()) {
				while (error.hasNext()) {
					errorBuilder.append(error.nextLine()+"\n");
				}
				error.close();
				return new Output(errorBuilder.toString(), OutputType.RUNTIME_ERROR);
			}
			

			// get output
			StringBuilder outputBuilder = new StringBuilder();
			Scanner output = new Scanner(p.getInputStream());
			while (output.hasNext()) {
				outputBuilder.append(output.nextLine() + "\n");
			}
			error.close();
			output.close();
			
			return new Output(outputBuilder.toString(), OutputType.SUCCESS,exTime);

		}

	}

}
