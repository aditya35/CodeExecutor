package com.codeExecutor.model;

public class Input {
	
	private String code;
	private String input;
	private String lang;
	
	public Input(String code, String input, String lang) {
		super();
		this.code = code;
		this.input = input;
		this.lang = lang;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

}
