package com.philip.edu.basic;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class DictDAO {
	private Logger logger = Logger.getLogger(DictDAO.class);
	
	public DictGroup getDictGroup(int groupid){
		Session session = null;
		DictGroup group = null;
		
		try{
			session = HibernateUtil.getSession();
			group = session.get(DictGroup.class, groupid);
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return group;
	}
	
	public ArrayList getDictByGroup(int groupid){
		ArrayList dictList = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("From Dict where dictgroupid=" + groupid + " order by id");
			dictList = (ArrayList)query.list();
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return dictList; 
	}
	
	public Dict getDictById(int dictid){
		Session session = null;
		Dict dict = null;
		
		try{
			session = HibernateUtil.getSession();
			dict = session.get(Dict.class, dictid);
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return dict;
	}
	
	public ArrayList getDictItemByDict(int dictid){
		ArrayList itemList = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("From DictItem where dictid=" + dictid + " order by id");
			itemList = (ArrayList)query.list();
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return itemList; 
		
	}
}
