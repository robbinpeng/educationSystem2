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
	
	public ArrayList getDictGroups(){
		Session session = null;
		ArrayList al = new ArrayList();
		
		try{
			session = HibernateUtil.getSession();
			al = (ArrayList)session.createQuery("from DictGroup order by id").list();
			
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return al;
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
	
	public ArrayList getDictionary(){
		ArrayList dictList = null;
		Session session = null;
		
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("From Dict order by id");
			
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
	
	public boolean createDict(Dict dict){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.save(dict);
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean updateDict(Dict dict){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			session.update(dict);
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
	}
	
	public boolean deleteDict(int dict_id){
		Session session = null;
		boolean isSuccess = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Dict dict = session.get(Dict.class, dict_id);
			session.delete(dict);
			session.getTransaction().commit();
			isSuccess = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return isSuccess;
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
