package com.philip.edu.basic;

import java.util.Date;

public class Task {
	private int id;
	private String task_name;
	private char task_status;
	private String start_time;
	private String end_time;
	private String stat_time;
	private String internal_stat_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public char getTask_status() {
		return task_status;
	}
	public void setTask_status(char task_status) {
		this.task_status = task_status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStat_time() {
		return stat_time;
	}
	public void setStat_time(String stat_time) {
		this.stat_time = stat_time;
	}
	public String getInternal_stat_time() {
		return internal_stat_time;
	}
	public void setInternal_stat_time(String internal_stat_time) {
		this.internal_stat_time = internal_stat_time;
	}
	
}
