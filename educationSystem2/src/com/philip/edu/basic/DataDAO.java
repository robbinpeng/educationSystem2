package com.philip.edu.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;


public class DataDAO {
	
	private Logger logger = Logger.getLogger(DataDAO.class);
	
	public ArrayList getTableData(ArrayList fields, String tbl_name){
		Session session = null;
		DataInfo data = null;
		ArrayList result = new ArrayList();
		ArrayList line = new ArrayList();
		StringBuffer sb = new StringBuffer("select id, ");
		logger.info("get into getTableData() method"); 
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			//create sql:
			for(int i=0; i<fields.size(); i++){
				FormField field = (FormField) fields.get(i);
				//Caption:
				data = new DataInfo();
				data.setId(i+1);
				data.setKey(field.getPhysic_name());
				data.setValue(field.getBus_name());
				line.add(data);
				
				//sql:
				if(i != fields.size()-1){
					sb.append(field.getPhysic_name() + ", ");
				} else {
					sb.append(field.getPhysic_name());
				}
			}
			result.add(line);
			ArrayList caption = line;
			logger.info("the caption size:" + caption.size());
			
			sb.append(" from " + tbl_name + " order by id");
			
			logger.info("sql:" + sb.toString());
			
			Query query = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			ArrayList al = (ArrayList)query.list();
			
			for(int i=0; i<al.size(); i++){
				line = new ArrayList();
				HashMap map = (HashMap)al.get(i);
				
				logger.info("fields.size:" + fields.size());
				for(int j=0; j<fields.size(); j++){
					data = new DataInfo();
					logger.info("j:" + j);
					DataInfo captionD = (DataInfo)caption.get(j);
					logger.info("caption key is:" + captionD.getKey());
					
					data.setId(j);
					
					Object o = map.get(captionD.getKey());
					if(o!=null){
						data.setValue(o.toString());
					} else {
						data.setValue("");
					}
					
					line.add(data);
				}
				
				result.add(line);
			}
		
			//session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
			
		return result;
	}
}
