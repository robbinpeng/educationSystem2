package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.HibernateUtil;
import com.philip.edu.basic.Rule;

public class RuleDAO {
	
	public boolean changeActive(int rule_id){
		Session session = null;
		boolean isSuccess = false;
		
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Rule rule = session.get(Rule.class, rule_id);
			
			if(rule.getRule_active()=='Y')rule.setRule_active('N');
			else rule.setRule_active('Y');
			
			session.update(rule);
			session.getTransaction().commit();
			
			isSuccess = true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}

	public ArrayList getRules(int form_id) {
		Session session = null;
		ArrayList rules = new ArrayList();

		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();

			rules = (ArrayList) session.createQuery("FROM Rule WHERE form_id=" + form_id + " order by rule_seq").list();

			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}

		return rules;
	}
	
	public ArrayList getRelateField(String sql,String field_value){
		Session session  = null;
		ArrayList al = null;
		
		try{
			session = HibernateUtil.getSession();
			
			Query query = session.createSQLQuery(sql);
			query.setParameter(0, field_value);
			
			al = (ArrayList) query.list();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return al;
	}

	public boolean deleteRule(int rule_id) {
		boolean is_success = false;
		Session session = null;
		Rule rule = null;

		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();

			ArrayList al = (ArrayList) session.createQuery("FROM Rule WHERE id=" + rule_id).list();

			if (al != null && al.size() != 0) {
				rule = (Rule) al.get(0);
			}

			session.delete(rule);

			session.getTransaction().commit();

			is_success = true;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}

		return is_success;
	}
}
