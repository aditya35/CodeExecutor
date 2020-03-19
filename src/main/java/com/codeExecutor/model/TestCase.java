package com.codeExecutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="name")
	private Question question;

	public TestCase() {
	}

	public long getId() {
		return id;
	}
	
	public TestCase(long id, String input, String output, Question question) {
		super();
		this.id = id;
		this.input = input;
		this.output = output;
		this.question = question;
	}

	public void setId(long id) {
		this.id = id;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "TestCase [id=" + id + ", input=" + input + ", output=" + output + ", question=" + question + "]";
	}

	

}
