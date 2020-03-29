package com.codeExecutor.model;

public class Input {

	private String code;
	private String input;
	private String lang;
	private long questionId;
	
	
	public Input(String code, String input, String lang, long questionId) {
		super();
		this.code = code;
		this.input = input;
		this.lang = lang;
		this.questionId = questionId;
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
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "Input [code=" + code + ", input=" + input + ", lang=" + lang + ", questionId=" + questionId + "]";
	}
	
	
	
	

}
