package com.philip.edu.basic;

import java.util.Date;

public class Form {
	private int id=0;
	private int user_id=0;
	private String tbl_name;
	private String bus_name;
	private String phsic_name;
	private char stats_time;
	private char display_method;
	private char form_type;
	private char is_null;
	private String dependency_form;
	private String template_loc;
	private Date create_time;
	private String memo;
	private FormStatus status;
	
	public char getStats_time() {
		return stats_time;
	}
	public void setStats_time(char stats_time) {
		this.stats_time = stats_time;
	}
	public char getDisplay_method() {
		return display_method;
	}
	public void setDisplay_method(char display_method) {
		this.display_method = display_method;
	}
	public char getForm_type() {
		return form_type;
	}
	public void setForm_type(char form_type) {
		this.form_type = form_type;
	}
	public char getIs_null() {
		return is_null;
	}
	public void setIs_null(char is_null) {
		this.is_null = is_null;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public FormStatus getStatus() {
		return status;
	}
	public void setStatus(FormStatus status) {
		this.status = status;
	}
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
	public String getTbl_name() {
		return tbl_name;
	}
	public void setTbl_name(String tbl_name) {
		this.tbl_name = tbl_name;
	}
	public String getBus_name() {
		return bus_name;
	}
	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}
	public String getPhsic_name() {
		return phsic_name;
	}
	public void setPhsic_name(String phsic_name) {
		this.phsic_name = phsic_name;
	}

	public String getDependency_form() {
		return dependency_form;
	}
	public void setDependency_form(String dependency_form) {
		this.dependency_form = dependency_form;
	}
	public String getTemplate_loc() {
		return template_loc;
	}
	public void setTemplate_loc(String template_loc) {
		this.template_loc = template_loc;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
