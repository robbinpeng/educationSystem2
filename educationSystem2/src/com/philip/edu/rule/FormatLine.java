package com.philip.edu.rule;

public class FormatLine {
	int columnCheck = 0;
	int dataType = 1;
	char textFormat = 'o';
	String columnName = "";
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
