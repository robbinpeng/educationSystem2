package com.philip.edu.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class FormDAO {
	private Logger logger = Logger.getLogger(FormDAO.class);
	
	public ArrayList getForms(int user_id){
		Session session = null;
		ArrayList forms = new ArrayList();
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		forms = (ArrayList) session.createQuery("FROM Form WHERE user_id="+user_id).list();
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return forms;
	}
	
	public FormField getFieldById(int field_id){
		FormField field = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			field = session.get(FormField.class, field_id);
			
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return field;
	}
	
	public Form getFormById(int form_id){
		Session session = null;
		Form form = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			form = (Form)session.get(Form.class, form_id);
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return form;
	}
	
	public ArrayList getFormFields(int form_id){
		Session session = null;
		ArrayList fields = new ArrayList();
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("FROM FormField WHERE form_id=" + form_id + " order by sequence");
		fields = (ArrayList) query.list();
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return fields;
	}
	
	public ArrayList getFormFieldsByFormName(int user_id, String table_name){
		Session session = null;
		ArrayList forms = new ArrayList();
		ArrayList fields = new ArrayList();
		Form form = null;
		
		try{
		session = HibernateUtil.getSession();
		session.beginTransaction();
		
		forms = (ArrayList)session.createQuery("FROM Form WHERE user_id=" + user_id + " AND tbl_name='" + table_name + "'").list();
		for(int i=0; i<forms.size(); i++){
			form = (Form)forms.get(i);
			break;
		}
		
		if(form!=null){
			fields = (ArrayList)session.createQuery("FROM FormField WHERE form_id=" + form.getId() + " order by sequence").list();
		}
		
		session.getTransaction().commit();
		
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return fields;
	}

	public void saveRule(Rule rule) {
		// TODO Auto-generated method stub
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(rule);
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	public void updateRule(Rule rule) {
		// TODO Auto-generated method stub
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.update(rule);
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}
	
	public Form getFormByName(int user_id, String name){
		Session session = null;
		Form form = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			List al = session.createQuery("From Form where user_id=" + user_id + " AND tbl_name='" + name + "'").list();
			for(int i=0; i<al.size(); i++){
				form = (Form)al.get(i);
				break;
			}
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return form;
	}
	
	public Form getFormByBusinessName(String name){
		Session session = null;
		Form form = null;
		
		try{
			session = HibernateUtil.getSession();
			List al = session.createQuery("From Form where bus_name='" + name + "'").list();
			if(al!=null)
				form = (Form)al.get(0);
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		return form;
	}
	
	public FormField getFormFieldByPhysicName(int form_id, String field_name){
		Session session = null;
		FormField field = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			List l = session.createQuery("from FormField where form_id=" + form_id + " and physic_name='" + field_name + "'").list();
			
			field = (FormField) l.get(0);
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return field;
	}
	
	public FormField getFormFieldByBusName(int form_id, String bus_name){
		Session session = null;
		FormField field = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			List l = session.createQuery("from FormField where form_id=" + form_id + " and bus_name='" + bus_name + "'").list();
			
			field = (FormField) l.get(0);
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return field;
	}
	
	public ArrayList getGroups(int user_id){
		ArrayList al = new ArrayList();
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from Group order by sequence");
			al = (ArrayList)query.list();
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return al;
	}
	
	public ArrayList getFormsByGroup(int group_id){
		ArrayList al = new ArrayList();
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			al = (ArrayList) session.createQuery("FROM Form WHERE group_id="+group_id + " order by bus_name").list();
			
			session.getTransaction().commit();
		}  catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return al;
	}
	
	public ArrayList getAllStatusTemp(){
		Session session = null;
		ArrayList template = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("From StatusTemp order by form_id");
			template = (ArrayList)query.list();
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return template;
	}
	
	public boolean createTask(Task task){
		Session session = null;
		boolean isSuccess = false;
		int sucTask = 0;
		ArrayList forms = null;
		ArrayList template = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			//create task:
			sucTask = ((Integer)session.save(task)).intValue();
			
			//create data collection:
			template = this.getAllStatusTemp();
			
			for(int i=0; i<template.size(); i++){
				StatusTemp temp = (StatusTemp)template.get(i);
				FormStatus status = new FormStatus();
				
				status.setForm_id(temp.getForm_id());
				status.setForm_status(temp.getStatus());
				status.setTask_id(sucTask);
				status.setUser_id(temp.getUser_id());
				status.setUpdate_time(new Date());
				
				session.save(status);
			}
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public ArrayList getTaskList(){
		ArrayList taskList = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("From Task order by id");
			taskList = (ArrayList)query.list();
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return taskList; 
	}
	
	public boolean deleteTask(int task_id){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Query query = session.createQuery("delete from FormStatus where task_id=" + task_id);
			query.executeUpdate();
			
			Task task = session.get(Task.class, task_id);
			session.delete(task);
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public Task getTaskById(int task_id){
		Session session = null;
		Task task = null;
		
		try{
			session = HibernateUtil.getSession();
			
			task = session.get(Task.class, task_id);
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return task;
	}
	
	public ArrayList getDataCollection(int task_id){
		Session session = null;
		ArrayList collection = new ArrayList();
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			ArrayList al = (ArrayList)session.createQuery("From Form order by id").list();
			for(int i=0; i<al.size(); i++){
				UploadInfo info = new UploadInfo();
				Form form = (Form)al.get(i);
				
				info.setBus_name(form.getBus_name());
				info.setDependency_form(form.getDependency_form());
				info.setId(form.getId());
				
				Query query = session.createQuery("From FormStatus where form_id=" + form.getId() + " and task_id=" + task_id);
				ArrayList sAl = (ArrayList)query.list();
				FormStatus status = (FormStatus)sAl.get(0);
				
				info.setStatus(status.getForm_status());
				collection.add(info);
			}
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return collection;
	}
	
	public ArrayList getDataCollectionByGroup(int task_id, int group_id){
		Session session = null;
		ArrayList collection = new ArrayList();
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			ArrayList al = (ArrayList)session.createQuery("From Form where group_id=" + group_id + " order by bus_name").list();
			for(int i=0; i<al.size(); i++){
				UploadInfo info = new UploadInfo();
				Form form = (Form)al.get(i);
				
				info.setBus_name(form.getBus_name());
				info.setDependency_form(form.getDependency_form());
				info.setId(form.getId());
				
				Query query = session.createQuery("From FormStatus where form_id=" + form.getId() + " and task_id=" + task_id);
				ArrayList sAl = (ArrayList)query.list();
				FormStatus status = (FormStatus)sAl.get(0);
				
				info.setStatus(status.getForm_status());
				collection.add(info);
			}
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return collection;
	}
	
	public School getSchoolInfo(){
		School s = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			ArrayList list = (ArrayList)session.createQuery("From School order by id").list();
			
			s = (School)list.get(0);
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return s;
	}
	
	public boolean updateSchoolInfo(School school){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			ArrayList list = (ArrayList)session.createQuery("From School order by id").list();
			
			School s = (School)list.get(0);
			school.setId(s.getId());
			
			session.clear();
			session.update(school);
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean addSchoolInfo(School school){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(school);
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public ArrayList getAllSchool(){
		Session session = null;
		ArrayList al = null;
		
		try{
			session = HibernateUtil.getSession();
			
			al = (ArrayList)session.createQuery("From School order by id").list();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return al;
	}
	
	public boolean createGroup(Group group){
		Session session = null;
		boolean bSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(group);
			session.getTransaction().commit();
			bSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return bSuccess;
	}
	
	public boolean updateGroup(Group group){
		Session session = null;
		boolean bSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.update(group);
			session.getTransaction().commit();
			bSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return bSuccess;
	}
	
	public boolean deleteGroup(int group_id){
		Session session = null;
		boolean bSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Group group = session.get(Group.class, group_id);
			session.delete(group);
			
			session.getTransaction().commit();
			bSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return bSuccess;
	}
	
	public Group getGroupById(int group_id){
		Session session = null;
		Group group = null;
		
		try{
			session = HibernateUtil.getSession();
			
			group = session.get(Group.class, group_id);
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return group;
	}
}
