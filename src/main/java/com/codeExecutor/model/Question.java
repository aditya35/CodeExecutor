package com.codeExecutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "time")
	private double time;

	@Column(name = "sample_input")
	private String sInput;

	@Column(name = "sample_output")
	private String sOutput;

	public Question() {
	}

	public Question(String qName, String description, double time, String sInput, String sOutput) {
		super();
		this.name = qName;
		this.description = description;
		this.time = time;
		this.sInput = sInput;
		this.sOutput = sOutput;
	}

	public String getqName() {
		return name;
	}

	public void setqName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
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

	public Long getId() {
		return id;
	}
}
