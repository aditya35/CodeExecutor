package com.codeExecutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {

	@Id
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "time")
	private int time;
	
	@Column(name = "sample_input")
	private String sInput;
	
	@Column(name = "sample_output")
	private String sOutput;
	
	public Question(){}

	public Question(String name, String description, int time, String sInput, String sOutput) {
		super();
		this.name = name;
		this.description = description;
		this.time = time;
		this.sInput = sInput;
		this.sOutput = sOutput;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getsInput() {
		return sInput;
	}

	public void setsInput(String sInput) {
		this.sInput = sInput;
	}

	public String getsOutput() {
		return sOutput;
	}

	public void setsOutput(String sOutput) {
		this.sOutput = sOutput;
	}

}
