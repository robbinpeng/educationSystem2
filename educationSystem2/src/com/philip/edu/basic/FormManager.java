package com.philip.edu.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FormManager {
	private Logger logger = Logger.getLogger(FormManager.class);
	
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
	
	public boolean createTask(Task task){
		return dao.createTask(task);
	}
	
	public ArrayList getDataCollection(int task_id){
		return dao.getDataCollection(task_id);
	}
	
	public ArrayList getDataCollectionByGroup(int task_id, int group_id){
		return dao.getDataCollectionByGroup(task_id, group_id);
	}
	
	public ArrayList getTaskList(){
		return dao.getTaskList();
	}
	
	public boolean deleteTask(int task_id){
		return dao.deleteTask(task_id);
	}
	
	public Task getTaskById(int task_id){
		return dao.getTaskById(task_id);
	}
	
	public School getSchoolInfo(){
		return dao.getSchoolInfo();
	}
	
	public boolean updateSchoolInfo(School school){
		return dao.updateSchoolInfo(school);
	}
	
	public boolean createGroup(Group group){
		return dao.createGroup(group);
	}
	
	public boolean updateGroup(Group group){
		return dao.updateGroup(group);
	}
	
	public boolean deleteGroup(int group_id){
		return dao.deleteGroup(group_id);
	}
	
	public Group getGroupById(int group_id){
		return dao.getGroupById(group_id);
	}
}
