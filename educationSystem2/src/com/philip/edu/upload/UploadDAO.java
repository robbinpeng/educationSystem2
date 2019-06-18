package com.philip.edu.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormStatus;
import com.philip.edu.basic.HibernateUtil;

public class UploadDAO {
	public boolean uploadData(ArrayList list){
		Session session = null;
		boolean b = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			for(int i=0; i<list.size(); i++){
				String sql = (String)list.get(i);
				
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, new Date());
				query.setParameter(1, new Date());
				query.setParameter(2, new Date());
				query.executeUpdate();
			}

			session.getTransaction().commit();
			b=true;
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return b;
	}

	public boolean uploadUpdate(int form_id) {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		Session session = null;
		
		try{
			//1.update self status to success;
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			FormStatus status = session.get(FormStatus.class, form_id);
			
			status.setStatus(Constants.STATUS_SUCCESS);
			session.save(status);
			
			//2.update depency table to uploadable;
			Query query = session.createQuery("From Form where dependency_form="+form_id);
			ArrayList list = (ArrayList)query.getResultList();
			for(int i=0; i<list.size(); i++){
				Form form = (Form)list.get(i);
				
				FormStatus status1 = session.get(FormStatus.class, form.getId());
				
				status1.setStatus(Constants.STATUS_UPLOADABLE);
				session.save(status1);
			}
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}

	public int rollbackData(String table_name){
		int deleted = 0;
		Session session = null;
		String sql = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			sql = "delete from " + table_name;
			
			Query query = session.createSQLQuery(sql);
			deleted = query.executeUpdate();
			
			session.getTransaction().commit();
			
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return deleted;
	}
	
	public boolean updateRollback(int form_id){
		boolean isSuccess = false;
		Session session = null;
		
		try{
			//1.update self status to uploadable;
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			FormStatus status = session.get(FormStatus.class, form_id);
			
			status.setStatus(Constants.STATUS_UPLOADABLE);
			session.save(status);
			
			//2.update depency table to uploadable;
			Query query = session.createQuery("From Form where dependency_form="+form_id);
			ArrayList list = (ArrayList)query.getResultList();
			for(int i=0; i<list.size(); i++){
				Form form = (Form)list.get(i);
				
				FormStatus status1 = session.get(FormStatus.class, form.getId());
				
				status1.setStatus(Constants.STATUS_CREATED);
				session.save(status1);
			}
			
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
}
