package com.philip.edu.rule;

import java.util.ArrayList;

public class LineInfo {
	private int type = 0;
	private String value;
	private int column = 0; 
	private int dict_id = 0;
	private ArrayList dict_items = null;
	private boolean is_required = false;
	private char dis_method = 'T';
	
	public char getDis_method() {
		return dis_method;
	}
	public void setDis_method(char dis_method) {
		this.dis_method = dis_method;
	}
	public boolean isIs_required() {
		return is_required;
	}
	public void setIs_required(boolean is_required) {
		this.is_required = is_required;
	}
	public ArrayList getDict_items() {
		return dict_items;
	}
	public void setDict_items(ArrayList dict_items) {
		this.dict_items = dict_items;
	}
	public int getDict_id() {
		return dict_id;
	}
	public void setDict_id(int dict_id) {
		this.dict_id = dict_id;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
