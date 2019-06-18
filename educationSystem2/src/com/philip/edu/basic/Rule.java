package com.philip.edu.basic;

public class Rule {
	private int id;
	private int user_id;
	private int form_id;
	private String rule_name;
	private int rule_class;
	private String rule_content;
	private String fail_information;
	private char rule_pattern;
	private int rule_seq;
	private char rule_active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getForm_id() {
		return form_id;
	}
	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public int getRule_class() {
		return rule_class;
	}
	public void setRule_class(int rule_class) {
		this.rule_class = rule_class;
	}
	public String getRule_content() {
		return rule_content;
	}
	public void setRule_content(String rule_content) {
		this.rule_content = rule_content;
	}
	public String getFail_information() {
		return fail_information;
	}
	public void setFail_information(String fail_information) {
		this.fail_information = fail_information;
	}
	public char getRule_pattern() {
		return rule_pattern;
	}
	public void setRule_pattern(char rule_pattern) {
		this.rule_pattern = rule_pattern;
	}
	public int getRule_seq() {
		return rule_seq;
	}
	public void setRule_seq(int rule_seq) {
		this.rule_seq = rule_seq;
	}
	public char getRule_active() {
		return rule_active;
	}
	public void setRule_active(char rule_active) {
		this.rule_active = rule_active;
	}
	
	
}
