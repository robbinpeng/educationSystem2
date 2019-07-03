package com.philip.edu.basic;

public class Group {
	private int id;
	private String class_name;
	private int sequence;
	private char group_type;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public char getGroup_type() {
		return group_type;
	}
	public void setGroup_type(char group_type) {
		this.group_type = group_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
