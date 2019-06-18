package com.philip.edu.rule;

public class NotImplementException extends Exception {
	private String message = null;
	
	public NotImplementException(String msg){
		message = msg;
	}
	
	public String getErrorMessage(){
		return message;
	}
}
