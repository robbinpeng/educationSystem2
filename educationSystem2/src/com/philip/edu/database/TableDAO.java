package com.philip.edu.database;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormStatus;
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
			
			session.update(field);
			
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
			
			//1¡¢create physic table£º
			sql = "create table " + form.getPhsic_name() + " (ID bigint not null auto_increment, CREATOR bigint, CREATE_TIME VARCHAR(50), LAST_OPERATOR bigint, LAST_OPERATE_TIME VARCHAR(50), STATUS int, TASK_ID bigint, TASK_VER_ int, primary key (ID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			
			Query query = session.createSQLQuery(sql);
			query.executeUpdate();
			
			//whether is uploadable:
			FormStatus status = form.getStatus();
			if(form.getDependency_form()==null || form.getDependency_form().equals("")){
				//uploadable:
				status.setStatus(Constants.STATUS_UPLOADABLE);
			} else {
				String dependency = form.getDependency_form();
				boolean canUpload = true;
				
				String[] str = null;
				if(dependency!=null && !dependency.equals("")){
					str = dependency.split(",");
					for(int i=0; i<str.length; i++){
						String temp = str[i].substring(1, str[i].length()-1);
						int table_id = Integer.parseInt(temp);
						
						FormStatus tempStatus = session.get(FormStatus.class, table_id);
						char stat = tempStatus.getStatus();
						if(stat!=Constants.STATUS_SUCCESS){canUpload=false; break;}
					}
				}
				
				if(canUpload)status.setStatus(Constants.STATUS_UPLOADABLE);
				else status.setStatus(Constants.STATUS_CREATED);
			}
			
			form.setStatus(status);
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
			
			FormStatus status = form.getStatus();
			if(form.getDependency_form()==null || form.getDependency_form().equals("")){
				//uploadable:
				status.setStatus(Constants.STATUS_UPLOADABLE);
			} else {
				String dependency = form.getDependency_form();
				boolean canUpload = true;
				
				String[] str = null;
				if(dependency!=null && !dependency.equals("")){
					str = dependency.split(",");
					for(int i=0; i<str.length; i++){
						String temp = str[i].substring(1, str[i].length()-1);
						int table_id = Integer.parseInt(temp);
						
						FormStatus tempStatus = session.get(FormStatus.class, table_id);
						char stat = tempStatus.getStatus();
						if(stat!=Constants.STATUS_SUCCESS){canUpload=false; break;}
					}
				}
				
				if(canUpload)status.setStatus(Constants.STATUS_UPLOADABLE);
				else status.setStatus(Constants.STATUS_CREATED);
			}
			
			form.setStatus(status);
			session.update(form);
			
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
