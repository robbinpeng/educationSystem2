package com.philip.edu.rule;

public class SearchInfo {
	private String field1Name;
	private String field2Name;
	private int field1Column;
	private int field2Column;
	private String andName = "";
	private String andValue = "";
	private String form_physic;
	
	public String getField1Name() {
		return field1Name;
	}
	public void setField1Name(String field1Name) {
		this.field1Name = field1Name;
	}
	public String getField2Name() {
		return field2Name;
	}
	public void setField2Name(String field2Name) {
		this.field2Name = field2Name;
	}
	public int getField1Column() {
		return field1Column;
	}
	public void setField1Column(int field1Column) {
		this.field1Column = field1Column;
	}
	public int getField2Column() {
		return field2Column;
	}
	public void setField2Column(int field2Column) {
		this.field2Column = field2Column;
	}
	
	public String getAndName() {
		return andName;
	}
	public void setAndName(String andName) {
		this.andName = andName;
	}
	public String getAndValue() {
		return andValue;
	}
	public void setAndValue(String andValue) {
		this.andValue = andValue;
	}
	public String getForm_physic() {
		return form_physic;
	}
	public void setForm_physic(String form_physic) {
		this.form_physic = form_physic;
	}
	
	
}
