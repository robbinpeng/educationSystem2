package com.philip.edu.rule;

public class TimeErrorException extends Exception{
	private String message = null;
	
	public TimeErrorException(String msg){
		message = msg;
	}
	
	public String getErrorMessage(){
		return message;
	}
}
