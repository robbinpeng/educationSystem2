package com.philip.edu.rule;

import java.util.ArrayList;

public class MessageInfo {
	private int message_type;
	private ArrayList message_info;
	private String fail_information;
	public String getFail_information() {
		return fail_information;
	}
	public void setFail_information(String fail_information) {
		this.fail_information = fail_information;
	}
	public int getMessage_type() {
		return message_type;
	}
	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}
	public ArrayList getMessage_info() {
		return message_info;
	}
	public void setMessage_info(ArrayList message_info) {
		this.message_info = message_info;
	}
	
}
