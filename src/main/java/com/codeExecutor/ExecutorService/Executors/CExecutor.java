package com.codeExecutor.ExecutorService.Executors;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.codeExecutor.model.Output;
import com.codeExecutor.model.OutputType;

@Component
public class CExecutor {

    public Output compile(String rootDirName, String testDirName, String name, String lang, int timeout)
			throws IOException, InterruptedException {
        String runScript = null;
        ProcessBuilder builder = new ProcessBuilder();
        switch (lang) {
            case "c":
                runScript = "gcc " + rootDirName + "/" + testDirName + "/" + name + ".c" + " -o " + rootDirName + "/" + testDirName + "/" + name;
                break;
            case "cpp":
                runScript = "g++ " + rootDirName + "/" + testDirName + "/" + name + ".cpp" + " -o " + rootDirName + "/" + testDirName + "/"
                        + name;
                break;
        }

        // compile
        builder.command("bash", "-c", runScript);
        long startTime = System.currentTimeMillis();
        Process p = builder.start();
        p.waitFor(timeout,TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        if (endTime - startTime > timeout * 1000) {
            p.destroy();
            if (p.isAlive()) {
                p.destroyForcibly();
            }
            return new Output("TIMEOUT", OutputType.TIMEOUT);
        }

        StringBuilder errorBuiler = new StringBuilder();
        Scanner error = new Scanner(p.getErrorStream());
        if (error.hasNext()) {
            while (error.hasNext()) {
                errorBuiler.append(error.nextLine()).append("\n");
            }
            p.destroy();
            error.close();
            return new Output(errorBuiler.toString(), OutputType.COMPILATION_ERROR);
        }
        error.close();
        return new Output("COMPILATION SUCCESS", OutputType.SUCCESS);

    }

    public Output execute(String rootDirName, String testDirName, String name, String inputFileName, int timeoutInSec)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        String runScript;
        if (inputFileName == null) {
            runScript = rootDirName + "/" + testDirName + "/" + name;
        } else {
            runScript = rootDirName + "/" + testDirName + "/" + name + " < " + rootDirName + "/" + testDirName + "/" + inputFileName;
        }

        // execute
        builder.command("bash", "-c", runScript);
        long startTime = System.currentTimeMillis();
        Process p = builder.start();
        p.waitFor(timeoutInSec, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        //TimeOut
        if (endTime - startTime > timeoutInSec * 1000) {
            p.destroy();
            if (p.isAlive()) {
                p.destroyForcibly();
            }
            return new Output("TIMEOUT", OutputType.TIMEOUT);

        } else {
            double exTime = (double) (endTime - startTime) / 1000;

            // Runtime Error
            StringBuilder errorBuilder = new StringBuilder();
            Scanner error = new Scanner(p.getErrorStream());
            if (error.hasNext()) {
                while (error.hasNext()) {
                    errorBuilder.append(error.nextLine()).append("\n");
                }
                error.close();
                return new Output(errorBuilder.toString(), OutputType.RUNTIME_ERROR);
            }

            // Output
            StringBuilder outputBuilder = new StringBuilder();
            Scanner output = new Scanner(p.getInputStream());
            System.out.println("Output");
            while (output.hasNext()) {
                outputBuilder.append(output.nextLine()).append("\n");
            }
            error.close();
            output.close();
            return new Output(outputBuilder.toString(), OutputType.SUCCESS, exTime);
        }
    }

}
