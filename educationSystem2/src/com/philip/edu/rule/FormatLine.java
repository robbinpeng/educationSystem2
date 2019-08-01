package com.philip.edu.rule;

public class FormatLine {
	int columnCheck = 0;
	int dataType = 1;
	char textFormat = 'o';
	String columnName = "";
	boolean is_required = false;
	int length = 0;
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public boolean isIs_required() {
		return is_required;
	}
	public void setIs_required(boolean is_required) {
		this.is_required = is_required;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnCheck() {
		return columnCheck;
	}
	public void setColumnCheck(int columnCheck) {
		this.columnCheck = columnCheck;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public char getTextFormat() {
		return textFormat;
	}
	public void setTextFormat(char textFormat) {
		this.textFormat = textFormat;
	}
	
	
}
