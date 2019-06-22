package com.philip.edu.rule;

public class BadRulesException extends Exception{
	private String message = null;
	
	public BadRulesException(String msg){
		message = msg;
	}
	
	public String getErrorMessage(){
		return message;
	}
}
