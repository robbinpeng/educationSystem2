package com.philip.edu.basic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory = null;
	static{
		try{
			Configuration configuration = new Configuration();
			configuration.configure();
			factory = configuration.buildSessionFactory();
		} catch(HibernateException e){
			e.printStackTrace();
		}
	}
	
	public static Session getSession(){
		Session session = (factory != null)?factory.openSession():null;
		return session;
	}
	
	public static void closeSession(Session session){
		if(session != null){
			if(session.isOpen()){
				session.close();
			}
		}
	}
}
