package com.philip.edu.basic;

import java.util.ArrayList;
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
	
}
