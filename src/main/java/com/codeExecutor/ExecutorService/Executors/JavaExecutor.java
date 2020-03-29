package com.codeExecutor.ExecutorService.Executors;

import com.codeExecutor.model.Output;
import com.codeExecutor.model.OutputType;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class JavaExecutor {


    // testDirName - random 16 digit name
    public Output compile(String rootDirName, String testDirName, String className) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result;
        ByteArrayOutputStream streamErr = new ByteArrayOutputStream();
        String filepath = rootDirName + "/" + testDirName + "/" + className + ".java";

        result = compiler.run(null, null, streamErr, filepath);
        if (result == 0) {
            return new Output("COMPILATION SUCCESS", OutputType.SUCCESS);
        }
        String error = new String(streamErr.toByteArray());
        return new Output(error, OutputType.COMPILATION_ERROR);

    }

    public Output execute(String rootDirName, String testDirName, String className, String inputFileName, int timeout)
            throws InterruptedException, IOException {
        String runScript;
        if (inputFileName == null) {
            runScript = "java -cp " + rootDirName + "/" + testDirName + "/ " + className;
        } else {
            runScript = "java -cp " + rootDirName + "/" + testDirName + "/ " + className + " < " + rootDirName + "/" + testDirName + "/"
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
            double exTime = (double) (endTime - startTime) / 1000;

            //check Runtime Errors
            Scanner error = new Scanner(p.getErrorStream());
            StringBuilder errorBuilder = new StringBuilder();
            if (error.hasNext()) {
                while (error.hasNext()) {
                    errorBuilder.append(error.nextLine()).append("\n");
                }
                error.close();
                return new Output(errorBuilder.toString(), OutputType.RUNTIME_ERROR);
            }


            // get output
            StringBuilder outputBuilder = new StringBuilder();
            Scanner output = new Scanner(p.getInputStream());
            while (output.hasNext()) {
                outputBuilder.append(output.nextLine()).append("\n");
            }
            error.close();
            output.close();

            return new Output(outputBuilder.toString(), OutputType.SUCCESS, exTime);

        }

    }

}
