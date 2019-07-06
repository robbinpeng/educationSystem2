package com.philip.edu.basic;

public class UploadInfo {
	private String bus_name;
	private int id;
	private char status;
	private String dependency_form;
	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getDependency_form() {
		return dependency_form;
	}
	public void setDependency_form(String dependency_form) {
		this.dependency_form = dependency_form;
	}
	
}
