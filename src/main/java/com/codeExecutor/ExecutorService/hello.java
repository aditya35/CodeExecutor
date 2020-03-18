package com.codeExecutor.ExecutorService;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/")
public class hello {
	
	@Autowired
	private CodeExecutor executor;
	
    public static String JavaHelloWorld(String name) {
        return "public class " + name + " {\n"
                + "public static void main (String args[])throws InterruptedException{\n"
                + "System.out.println(\"Hello World from \"+new java.util.Scanner(System.in).nextLine());\n"
                + "System.out.println(\"new Number \"+ new java.util.Random().nextInt(10));\n"
//                + "Thread.sleep(1000);\n"
                + "}}\n";
    }
    
    public static String cHelloWorld() {
    	return "#include <stdio.h>\n"
    			+ "#include <stdlib.h>\n"
    			+ "void main() {\n"
    			+ "int a = 0;\n"
    			+ "int b = 0;\n"
    			+ "int c = a/b;\n"
//    			+ "scanf(\"%d\",&c);\n"
    			+ "printf(\"Hello world from %d\",c);\n"
    			+ "}";
    }
    
    public static String cppHelloWorld() {
    	return "#include<iostream>\n"
    			+ "using namespace std;\n"
    			+"int main(){\n"
//    			+"int a = 0;\n"
//    			+"int b =0;\n"
//    			+"int c = a/b;"
//    			+"cout<<c<<endl;\n"
//				+"int a;\n"
//				+"cin<<a;\n"
//				+"cout<<a\n"
    			+"cout<<\"Hello world\"<<endl;\n"
    			+"return 0;\n"
    			+"}";
    }

	
    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/hello")
	public ResponseEntity printHello() {
		String id = "hello";
		String usrDir = System.getProperty("user.dir");
		try {
//			executor.runCode(JavaHelloWorld(id),id,"java","111");
//			executor.runCode(cHelloWorld(), id,"c","111");
			executor.runCode(cppHelloWorld(), id, "cpp","111");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity("hello",HttpStatus.OK);
	}

}
