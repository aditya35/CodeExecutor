package com.codeExecutor.ExecutorService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeExecutor.dao.QuestionDAO;
import com.codeExecutor.dao.TestCaseDAO;
import com.codeExecutor.model.Question;
import com.codeExecutor.model.TestCase;


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
    
    @Autowired
    private QuestionDAO questionDAO;
    
    @GetMapping("/test")
    public void testHiberante() {
//    	List<TestCase> testList = new ArrayList<>();
//    	TestCase test1 = new TestCase("1 2 3 4","4 3 2 1");
//    	Question testQues = new Question("arraySwap", "program to swap array", 1.23, test1);
//    	questionDAO.addQuestion(testQues);
//    	
    	
    }
    
    
//    @Autowired
//    private TestCaseDAO testCaseDAO;
    
//    @GetMapping("/testCases")
//    public String  testTestCaes() {
////    	System.out.println(testCaseDAO.getQuestionTestCases("array1"));
////    	testCaseDAO.deleteTestCases("array1");
////    	testCaseDAO.addTestCase(new TestCase("1 2 3 4", "4 3 2 1", "array2"));
////    	System.out.println(testCaseDAO.getQuestionTestCases("array2"));
//    	testCaseDAO.deleteTestCases("array2");
//    	return  "done";
////    	System.out.println("hello");
//    }

}
