package com.codeExecutor.dto;

public class Output {
	
	private String output;
	private OutputType type;
	private double time;

	
	public Output(String output, OutputType type ) {
		this.output = output;
		this.type = type;
	}
	
	public Output(String output, OutputType type ,double time) {
		this.output = output;
		this.type = type;
		this.time = time;
	}
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public OutputType getType() {
		return type;
	}

	public void setType(OutputType type) {
		this.type = type;
	}
	
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	
	
		
	

}
