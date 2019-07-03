package com.philip.edu.basic;

import java.util.ArrayList;
import java.util.List;

public class FormManager {
	//private Logger logger = Logger.getLogger(FormManager.class);
	
	private FormDAO dao = new FormDAO();
	
	public ArrayList getForms(int user_id){
		return dao.getForms(user_id);
	}
	
	public ArrayList getFormFields(int form_id){
		return dao.getFormFields(form_id);
	}
	
	public ArrayList getFormFieldsByFormName(int user_id, String table_name){
		return dao.getFormFieldsByFormName(user_id, table_name);
	}
	
	public void saveRule(Rule rule){
		dao.saveRule(rule);
	}
	
	public Form getFormByName(int user_id, String name){
		return dao.getFormByName(user_id, name);
	}
	
	public FormField getFieldByPhysicName(int form_id, String field_name){
		return dao.getFormFieldByPhysicName(form_id, field_name);
	}
	
	public Form getFormById(int form_id){
		return dao.getFormById(form_id);
	}
	
	public FormField getFieldById(int field_id){
		return dao.getFieldById(field_id);
	}
	
	public ArrayList getGroups(int user_id){
		return dao.getGroups(user_id);
	}
	
	public ArrayList getFormsByGroup(int group_id){
		return dao.getFormsByGroup(group_id);
	}
}
