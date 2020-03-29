package com.codeExecutor.ExecutorService;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codeExecutor.ExecutorService.Executors.JavaExecutor;
import com.codeExecutor.model.Output;
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
public class test {
	
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
//    			+ "int a = 0;\n"
//    			+ "int b = 0;\n"
//    			+ "int c = a/b;\n"
//    			+ "scanf(\"%d\",&c);\n"
    			+ "printf(\"Hello world\");\n"
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
//		try {
//			executor.runCode(JavaHelloWorld(id),id,"java","111");
//			executor.runCode(cHelloWorld(), id,"c","111");
//			executor.runCode(cppHelloWorld(), id, "cpp","111");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new ResponseEntity("hello",HttpStatus.OK);
	}
    
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private CodeExecutor codeExecutor;
    
    @GetMapping("/test")
    public void testHiberante() throws IOException, InterruptedException {
//    	List<TestCase> testList = new ArrayList<>();
//    	TestCase test1 = new TestCase("1 2 3 4","4 3 2 1");
//    	Question testQues = new Question("arraySwap", "program to swap array", 1.23, test1);
//    	questionDAO.addQuestion(testQues);
//

    	//test java code
//		String sampleJavaCode = JavaHelloWorld("Solution");
//		Output output = codeExecutor.runCode(sampleJavaCode,"java","Aditya",5);

    	
    	//test c code
//    	String sampleCcode = cHelloWorld();
//    	Output output = codeExecutor.runCode(sampleCcode, "c", "12", 3);
    	
    	//test cpp code
    	String samplecppCode = cppHelloWorld();
    	Output output = codeExecutor.runCode(samplecppCode, "cpp", "112", 3);
    	
    	
    	
		System.out.println(output.getOutput()+" "+output.getType());

    	
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
