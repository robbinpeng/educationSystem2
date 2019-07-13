package com.philip.edu.basic;

public class FormFieldData {
	private int id;
	private String bus_name;
	private String physic_name;
	private int sequence;
	private char dis_method;
	private char text_format;
	private int dictid;
	private String value;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public char getDis_method() {
		return dis_method;
	}
	public void setDis_method(char dis_method) {
		this.dis_method = dis_method;
	}
	public char getText_format() {
		return text_format;
	}
	public void setText_format(char text_format) {
		this.text_format = text_format;
	}
	public int getDictid() {
		return dictid;
	}
	public void setDictid(int dictid) {
		this.dictid = dictid;
	}
}
