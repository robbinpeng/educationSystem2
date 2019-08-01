package com.philip.edu.rule;

public class SearchInfo {
	private String field1Name;
	private String field2Name;
	private int field1Column;
	private int field2Column;
	private String andName = "";
	private String andValue = "";
	private String form_physic;
	private String physic_field;
	private String physic_field2;
	private boolean multiple=false;
	
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public String getPhysic_field2() {
		return physic_field2;
	}
	public void setPhysic_field2(String physic_field2) {
		this.physic_field2 = physic_field2;
	}
	public String getPhysic_field() {
		return physic_field;
	}
	public void setPhysic_field(String physic_field) {
		this.physic_field = physic_field;
	}
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
