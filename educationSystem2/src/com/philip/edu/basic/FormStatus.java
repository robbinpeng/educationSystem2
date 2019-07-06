package com.philip.edu.basic;

import java.util.Date;

public class FormStatus {
	private int id=0;
	private int user_id=0;
	private int form_id=0;
	private int task_id=0;
	private char form_status='C';
	private Date update_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public char getForm_status() {
		return form_status;
	}
	public void setForm_status(char form_status) {
		this.form_status = form_status;
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
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
