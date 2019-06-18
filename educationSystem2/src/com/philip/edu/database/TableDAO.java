package com.philip.edu.database;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.HibernateUtil;

public class TableDAO {
	
	private static Logger logger = Logger.getLogger(TableDAO.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		//Query query = session.createSQLQuery("create table test (ID bigint not null auto_increment, CREATOR bigint, primary key (ID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		Query query = session.createSQLQuery("alter table tbl_zyjbqk add TJSJ varchar(100)");
		int i = query.executeUpdate();
		
		System.out.println("" + i);
		session.getTransaction().commit();
	}
	
	public boolean isFieldExsits(String table_name, String field_name){
		Session session = null;
		boolean isExsits = false;
		ArrayList result = new ArrayList();
		logger.info("get into isFieldExsits() method"); 
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			Query query = session.createSQLQuery("select * from information_schema.columns where table_name='" + table_name +"' and column_name='" + field_name +"'");
			result = (ArrayList) query.list();
			
			if(result!=null && result.size()!=0) isExsits = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isExsits;
	}
	
	public boolean addField(String table_name, FormField field){
		boolean isSuccess = false;
		Session session = null;
		String sql = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			sql = "alter table " + table_name + " add " + field.getPhysic_name() + " varchar(" + field.getLength() + ")";
			
			Query query = session.createSQLQuery(sql);
			query.executeUpdate();
			
			session.save(field);
			
			session.getTransaction().commit();
			isSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean updateField(FormField field){
		boolean isSuccess = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(field);
			
			session.getTransaction().commit();
			isSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean deleteField(String table_name, int field_id){
		boolean isSuccess = false;
		Session session = null;
		String sql = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			FormField temp = session.get(FormField.class, field_id);
			
			if(temp!=null){
				
				sql = "alter table " + table_name + " drop column " + temp.getPhysic_name();
				
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				
				session.delete(temp);
				
				session.getTransaction().commit();
				isSuccess = true;
			}
			
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean isTableExsits(String physic_name){
		Session session = null;
		boolean isExsits = false;
		ArrayList result = new ArrayList();
		logger.info("get into isTableExsits() method"); 
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			Query query = session.createSQLQuery("SELECT table_name FROM information_schema.TABLES WHERE table_name ='" + physic_name + "';");
			result = (ArrayList) query.list();
			
			if(result!=null && result.size()!=0) isExsits = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isExsits;
	}	
	
	public boolean createTable(Form form){
		boolean isSuccess = false;
		Session session = null;
		String sql = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			sql = "create table " + form.getPhsic_name() + " (ID bigint not null auto_increment, CREATOR bigint, CREATE_TIME VARCHAR(50), LAST_OPERATOR bigint, LAST_OPERATE_TIME VARCHAR(50), STATUS int, TASK_ID bigint, TASK_VER_ int, primary key (ID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			
			Query query = session.createSQLQuery(sql);
			query.executeUpdate();
			
			session.save(form);
			
			session.getTransaction().commit();
			isSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean updateTable(Form form){
		boolean isSuccess = false;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(form);
			
			session.getTransaction().commit();
			isSuccess = true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean deleteTable(int form_id){
		boolean isSuccess = false;
		Session session = null;
		String sql = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Form form = session.get(Form.class, form_id);
			if(form!=null){
				sql = "drop table " + form.getPhsic_name();
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				
				session.delete(form);
				
				session.getTransaction().commit();
				isSuccess = true;
			}
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
}
