package com.codeExecutor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	private double time;

//	@OneToOne(mappedBy = "testcase" , cascade = CascadeType.ALL)
//	private TestCase testCases;

	public Question() {
	}

	public Question(String name, String description, double time, TestCase testCases) {
		super();
		this.name = name;
		this.description = description;
		this.time = time;
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

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Question [name=" + name + ", description=" + description + ", time=" + time + ", testCases=" 
				+ "]";
	}
	
	
}
