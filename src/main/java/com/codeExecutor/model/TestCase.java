package com.codeExecutor.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testcase")
public class TestCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_id")
	private long id;

	@Column(name = "input")
	private String input;

	@Column(name = "output")
	private String output;
	
	
	@Column(name = "question_name")
	private String qName;
	
	public TestCase() {
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public long getId() {
		return id;
	}

	public TestCase(String input, String output, String qName) {
		super();
		this.input = input;
		this.output = output;
		this.qName = qName;
	}

	@Override
	public String toString() {
		return "TestCase [id=" + id + ", input=" + input + ", output=" + output + ", qName=" + qName + "]";
	}

	

}
