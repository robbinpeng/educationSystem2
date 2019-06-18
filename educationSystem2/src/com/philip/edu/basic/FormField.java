package com.philip.edu.basic;

public class FormField{
	private int id=0;
	private String bus_name;
	private String physic_name;
	private char is_required;
	private int sequence;
	private int data_type;
	private int length;
	private char dis_method;
	private char is_report;
	private char is_hidden;
	private char compute;
	private int form_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public String getPhysic_name() {
		return physic_name;
	}
	public void setPhysic_name(String physic_name) {
		this.physic_name = physic_name;
	}
	public char getIs_required() {
		return is_required;
	}
	public void setIs_required(char is_required) {
		this.is_required = is_required;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	public char getDis_method() {
		return dis_method;
	}
	public void setDis_method(char dis_method) {
		this.dis_method = dis_method;
	}
	public char getIs_report() {
		return is_report;
	}
	public void setIs_report(char is_report) {
		this.is_report = is_report;
	}
	public char getIs_hidden() {
		return is_hidden;
	}
	public void setIs_hidden(char is_hidden) {
		this.is_hidden = is_hidden;
	}
	public char getCompute() {
		return compute;
	}
	public void setCompute(char compute) {
		this.compute = compute;
	}
	public int getForm_id() {
		return form_id;
	}
	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}
	
}
