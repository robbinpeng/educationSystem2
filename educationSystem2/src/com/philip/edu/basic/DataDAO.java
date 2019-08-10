package com.philip.edu.basic;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;


public class DataDAO {
	
	public static void main(String[] args){
		FormManager formManager = new FormManager();
		Form form = formManager.getFormById(22);
		ArrayList fields = formManager.getFormFields(22);
		DataDAO dao = new DataDAO();
		ArrayList result = dao.getTableData(fields, form.getPhsic_name());
		int a = 1;
	}
	
	private Logger logger = Logger.getLogger(DataDAO.class);
	private FormManager formManager = new FormManager();
	
	public ArrayList getTableData(ArrayList fields, String tbl_name){
		Session session = null;
		DataInfo data = null;
		ArrayList result = new ArrayList();
		ArrayList line = new ArrayList();
		StringBuffer sb = new StringBuffer("select id, ");
		Query query = null;
		//logger.info("get into getTableData() method"); 
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			//create sql:
			ArrayList al0 = new ArrayList();
			for(int i1=0; i1<fields.size(); i1++){
				FormField field = (FormField) fields.get(i1);
				if(field.getIs_hidden()=='Y'){
					
				} else {
					al0.add(field);
				}
			}
			
			fields = (ArrayList)al0.clone();
				
			for(int i=0; i<fields.size(); i++){
				FormField field = (FormField) fields.get(i);
				//if(field.getIs_report()=='N'||field.getIs_hidden()=='Y')continue;
				//Caption:
				data = new DataInfo();
				data.setId(i+1);
				data.setKey(field.getPhysic_name());
				data.setValue(field.getBus_name());
				if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)data.setUrl("URL");
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
			//logger.info("the caption size:" + caption.size());
			
			sb.append(" from " + tbl_name + " order by id");
			
			//logger.info("sql:" + sb.toString());
			
			query = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			
			//query = session.createSQLQuery().
			ArrayList al = (ArrayList)query.list();
			
			for(int i=0; i<al.size(); i++){
				line = new ArrayList();
				HashMap map = (HashMap)al.get(i);
				DataInfo data1 = new DataInfo();
				
				//logger.info("fields.size:" + fields.size());
				
				Object oId = map.get("id");
				if(oId!=null){
					data1.setValue(oId.toString());
				} else {
					data1.setValue("");
				}
				line.add(data1);
				
				for(int j=0; j<fields.size(); j++){
					data = new DataInfo();
					//logger.info("j:" + j);
					DataInfo captionD = (DataInfo)caption.get(j);
					//logger.info("caption key is:" + captionD.getKey());
					
					data.setId(j);
					
					Object o = map.get(captionD.getKey());
					if(o!=null){
						data.setValue(o.toString());
						//logger.info("captionD url:" + captionD.getUrl());
						if("URL".equals(captionD.getUrl())){
							String path = o.toString();
							String[] sPath = path.split(Pattern.quote(File.separator));;
							String name = sPath[sPath.length-1];
							//logger.info("path:" + sPath[sPath.length-1]);
							data.setUrl(path);
							data.setValue(name);
							data.setKey("URL");
						}
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
	
	public ArrayList getTableDataByPage(ArrayList fields, String tbl_name, int page, boolean show_school, String chose_school, String chose_TJSJ){
		Session session = null;
		DataInfo data = null;
		ArrayList result = new ArrayList();
		ArrayList line = new ArrayList();
		StringBuffer sb = new StringBuffer("select id, ");
		Query query = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			//create sql:
			ArrayList al0 = new ArrayList();
			
			FormField fieldTemp = new FormField();
			if(show_school){
				fieldTemp.setPhysic_name("school_name");
				al0.add(fieldTemp);
			}
			
			fieldTemp = new FormField();
			fieldTemp.setPhysic_name("internal_tjsj");
			al0.add(fieldTemp);
			
			for(int i1=0; i1<fields.size(); i1++){
				FormField field = (FormField) fields.get(i1);
				if(field.getIs_hidden()=='Y'){
					
				} else {
					al0.add(field);
				}
			}
			
			fields = (ArrayList)al0.clone();
				
			for(int i=0; i<fields.size(); i++){
				FormField field = (FormField) fields.get(i);
				//if(field.getIs_report()=='N'||field.getIs_hidden()=='Y')continue;
				//Caption:
				if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)data.setUrl("URL");
				
				//sql:
				if(i != fields.size()-1){
					sb.append(field.getPhysic_name() + ", ");
				} else {
					sb.append(field.getPhysic_name());
				}
			}
			
			int index = (page-1) * Constants.DATA_PAGE_SIZE;
			
			if("all".equals(chose_school) && "all".equals(chose_TJSJ)){
				sb.append(" from " + tbl_name + " order by id limit " + index + "," + Constants.DATA_PAGE_SIZE);
			} else {
				sb.append(" from " + tbl_name + " where ");
				if(!"all".equals(chose_school))
					{
						sb.append(" school_name='" + chose_school + "' ");
						if(!"all".equals(chose_TJSJ))sb.append(" and internal_tjsj='" + chose_TJSJ + "'");
					}
				else{
					if(!"all".equals(chose_TJSJ))sb.append(" internal_tjsj='" + chose_TJSJ + "' ");
				}
				
				sb.append(" order by id limit " + index + "," + Constants.DATA_PAGE_SIZE);
			}
			
			
			logger.info("sql:" + sb.toString());
			
			query = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			
			//query = session.createSQLQuery().
			ArrayList al = (ArrayList)query.list();
			
			for(int i=0; i<al.size(); i++){
				line = new ArrayList();
				HashMap map = (HashMap)al.get(i);
				DataInfo data1 = new DataInfo();
				
				//logger.info("fields.size:" + fields.size());
				
				Object oId = map.get("id");
				if(oId!=null){
					data1.setValue(oId.toString());
				} else {
					data1.setValue("");
				}
				line.add(data1);
				
				for(int j=0; j<fields.size(); j++){
					data = new DataInfo();
					//logger.info("j:" + j);
					FormField field = (FormField)fields.get(j);
					//logger.info("caption key is:" + captionD.getKey());
					
					data.setId(j);
					
					Object o = map.get(field.getPhysic_name());
					if(o!=null){
						data.setValue(o.toString());
						//logger.info("captionD url:" + captionD.getUrl());
						if(field.getDis_method() == Constants.V_DISPLAY_UPLOAD_CONTROL){
							String path = o.toString();
							String[] sPath = path.split(Pattern.quote(File.separator));;
							String name = sPath[sPath.length-1];
							//logger.info("path:" + sPath[sPath.length-1]);
							data.setUrl(path);
							data.setValue(name);
							data.setKey("URL");
						}
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
	
	public ArrayList searchDataByPage(ArrayList fields, String tbl_name, int page, String search, boolean show_school, String chose_school, String chose_TJSJ){
		Session session = null;
		DataInfo data = null;
		ArrayList result = new ArrayList();
		ArrayList line = new ArrayList();
		StringBuffer sb = new StringBuffer("select id, ");
		StringBuffer sb1 = new StringBuffer(" from " + tbl_name + " where ");
		Query query = null;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();

			ArrayList al0 = new ArrayList();
			FormField fieldTemp = new FormField();
			if(show_school){
				fieldTemp.setPhysic_name("school_name");
				al0.add(fieldTemp);
			}
			
			fieldTemp = new FormField();
			fieldTemp.setPhysic_name("internal_tjsj");
			al0.add(fieldTemp);
			
			//create sql:

			for(int i1=0; i1<fields.size(); i1++){
				FormField field = (FormField) fields.get(i1);
				if(field.getIs_hidden()=='Y'){
					
				} else {
					al0.add(field);
				}
			}
			
			fields = (ArrayList)al0.clone();
				
			for(int i=0; i<fields.size(); i++){
				FormField field = (FormField) fields.get(i);
				//if(field.getIs_report()=='N'||field.getIs_hidden()=='Y')continue;
				//Caption:
				if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)data.setUrl("URL");
				
				int index = (page-1) * Constants.DATA_PAGE_SIZE;
				
				//sql:
				if(i != fields.size()-1){
					sb.append(field.getPhysic_name() + ", ");
					sb1.append(field.getPhysic_name() + " like '%" + search + "%' or ");
				} else {
					sb.append(field.getPhysic_name());
					sb1.append(field.getPhysic_name() + " like '%" + search + "%' ");
					if("all".equals(chose_school) && "all".equals(chose_TJSJ)){
						
					} else {
						if(!"all".equals(chose_school)){
							sb1.append(" and school_name='" + chose_school + "'");
						} 
						if(!"all".equals(chose_TJSJ)){
							sb1.append(" and internal_tjsj='" + chose_TJSJ + "'");
						} 
					}
					sb1.append(" order by id limit " + index + "," + Constants.DATA_PAGE_SIZE);
				}
			}
			
			sb.append(sb1);
			
			logger.info("sql:" + sb.toString());
			
			
			query = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			
			//query = session.createSQLQuery().
			ArrayList al = (ArrayList)query.list();
			
			for(int i=0; i<al.size(); i++){
				line = new ArrayList();
				HashMap map = (HashMap)al.get(i);
				DataInfo data1 = new DataInfo();
				
				//logger.info("fields.size:" + fields.size());
				
				Object oId = map.get("id");
				if(oId!=null){
					data1.setValue(oId.toString());
				} else {
					data1.setValue("");
				}
				line.add(data1);
				
				for(int j=0; j<fields.size(); j++){
					data = new DataInfo();
					//logger.info("j:" + j);
					FormField field = (FormField)fields.get(j);
					//logger.info("caption key is:" + captionD.getKey());
					
					data.setId(j);
					
					Object o = map.get(field.getPhysic_name());
					if(o!=null){
						data.setValue(o.toString());
						//logger.info("captionD url:" + captionD.getUrl());
						if(field.getDis_method() == Constants.V_DISPLAY_UPLOAD_CONTROL){
							String path = o.toString();
							String[] sPath = path.split(Pattern.quote(File.separator));;
							String name = sPath[sPath.length-1];
							//logger.info("path:" + sPath[sPath.length-1]);
							data.setUrl(path);
							data.setValue(name);
							data.setKey("URL");
						}
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
	
	public ArrayList getTableDataById(ArrayList fields, String tbl_name, int id){
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
			
			ArrayList al0 = new ArrayList();
			for(int i1=0; i1<fields.size(); i1++){
				FormField field = (FormField) fields.get(i1);
				if(field.getIs_hidden()=='Y'){
					
				} else {
					al0.add(field);
				}
			}
			
			fields = al0;
				
			for(int i=0; i<fields.size(); i++){
				FormField field = (FormField) fields.get(i);
				//if(field.getIs_report()=='N'||field.getIs_hidden()=='Y')continue;
				//Caption:
				data = new DataInfo();
				data.setId(i+1);
				data.setKey(field.getPhysic_name());
				data.setValue(field.getBus_name());
				if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)data.setUrl("URL");
				line.add(data);
				
				//sql:
				if(i != fields.size()-1){
					sb.append(field.getPhysic_name() + ", ");
				} else {
					sb.append(field.getPhysic_name());
				}
			}
			//result.add(line);
			ArrayList caption = line;
			//logger.info("the caption size:" + caption.size());
			
			sb.append(" from " + tbl_name + " where id=" + id +" order by id");
			
			//logger.info("sql:" + sb.toString());
			
			//Query query = session.createQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			Query query = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			ArrayList al = (ArrayList)query.list();
			
			HashMap map = (HashMap)al.get(0);
			
			DataInfo data1 = new DataInfo();
				
			//logger.info("fields.size:" + fields.size());
				
			Object oId = map.get("id");
			if(oId!=null){
				data1.setValue(oId.toString());
			} else {
				data1.setValue("");
			}
			result.add(data1);
				
			for(int j=0; j<fields.size(); j++){
				data = new DataInfo();
				//logger.info("j:" + j);
				DataInfo captionD = (DataInfo)caption.get(j);
				//logger.info("caption key is:" + captionD.getKey());
					
				data.setId(j);
					
				Object o = map.get(captionD.getKey());
				if(o!=null){
					data.setValue(o.toString());
					//logger.info("captionD url:" + captionD.getUrl());
					if("URL".equals(captionD.getUrl())){
						String path = o.toString();
						String[] sPath = path.split(Pattern.quote(File.separator));;
						String name = sPath[sPath.length-1];
						//logger.info("path:" + sPath[sPath.length-1]);
						data.setUrl(path);
						data.setValue(name);
						data.setKey("URL");
					}
				} else {
					data.setValue("");
				}
					
					result.add(data);
			}
			//session.getTransaction().commit();
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
			
		return result;
	}
	
	public boolean deleteRecords(String table_name, ArrayList ids){
		Session session = null;
		boolean b = false;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			String sql = "delete from " + table_name + " where id=";
			
			for(int i=0; i<ids.size(); i++){
				String sqlF = sql + (String)ids.get(i);
				session.createSQLQuery(sqlF).executeUpdate();
			}
			session.getTransaction().commit();
			b = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return b;
	}
	
	public boolean createRecord(Form form, ArrayList record, int task_id){
		Session session = null;
		boolean b = false;
		StringBuffer sql1 = new StringBuffer("insert into " + form.getPhsic_name() + "(");
		StringBuffer sql2 = new StringBuffer(" values(");
		
		School school = formManager.getSchoolInfo();
		Task task = formManager.getTaskById(task_id);
		String tjsj = null;
		switch(form.getStats_time()){
		case Constants.V_TYPE_STAT_TIME_POINT:
			tjsj = task.getStat_time();
			break;
		case Constants.V_TYPE_STAT_TIME_STUDY_YEAR:
			tjsj = task.getStudy_year();
			break;
		case Constants.V_TYPE_STAT_TIME_NATURE_YEAR:
			tjsj = task.getNatural_year();
			break;
		default:
			break;
		}
		
		//default data:
		sql1.append("CREATOR, ");
		sql2.append("" + Constants.USER_ID + ", ");
		sql1.append("CREATE_TIME, ");
		sql2.append("?, ");
		sql1.append("LAST_OPERATOR, ");
		sql2.append("" + Constants.USER_ID + ", ");
		sql1.append("LAST_OPERATE_TIME, ");
		sql2.append("?, ");
		sql1.append("STATUS, ");
		sql2.append("1, ");
		sql1.append("TASK_ID, ");
		sql2.append(""+task_id+", ");
		sql1.append("INTERNAL_TJSJ, ");
		sql2.append("'" + task.getInternal_stat_time() + "', ");
		sql1.append("SCHOOL_NUMBER, ");
		sql2.append("'" + school.getSchool_number() + "', ");
		sql1.append("SCHOOL_NAME, ");
		sql2.append("'" + school.getSchool_name() + "', ");
		
		for(int i = 0; i<record.size(); i++){
			DataInfo data = (DataInfo)record.get(i);
			if(i != record.size()-1){
				sql1.append("" + data.getKey() + ", ");
				sql2.append("'" + data.getValue() + "', ");
			} else {
				sql1.append("" + data.getKey() + ")");
				sql2.append("'" + data.getValue() + "')"); 
			}
		}
		
		StringBuffer sql = sql1.append(sql2);
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Query query = session.createSQLQuery(sql.toString());
			query.setParameter(1, new Date());
			query.setParameter(2, new Date());
			query.executeUpdate();
			
			session.getTransaction().commit();
			b = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return b;
	}
	
	public boolean updateRecord(Form form, ArrayList record, int task_id, int id){
		Session session = null;
		boolean b = false;
		StringBuffer sql1 = new StringBuffer("update " + form.getPhsic_name() + " set ");
		
		School school = formManager.getSchoolInfo();
		Task task = formManager.getTaskById(task_id);
		String tjsj = null;
		switch(form.getStats_time()){
		case Constants.V_TYPE_STAT_TIME_POINT:
			tjsj = task.getStat_time();
			break;
		case Constants.V_TYPE_STAT_TIME_STUDY_YEAR:
			tjsj = task.getStudy_year();
			break;
		case Constants.V_TYPE_STAT_TIME_NATURE_YEAR:
			tjsj = task.getNatural_year();
			break;
		default:
			break;
		}
		
		//default data:
		sql1.append("LAST_OPERATOR=" + Constants.USER_ID + ","); 
		sql1.append("LAST_OPERATE_TIME=?, ");
		sql1.append("TASK_ID=" + task_id + ", ");
		sql1.append("INTERNAL_TJSJ='" + task.getInternal_stat_time() + "', ");

		
		for(int i = 0; i<record.size(); i++){
			DataInfo data = (DataInfo)record.get(i);
			if(i != record.size()-1){
				sql1.append("" + data.getKey() + "='" + data.getValue() + "',");

			} else {
				sql1.append("" + data.getKey() + "='" + data.getValue() + "' where id=" + id);
			}
		}
		
		StringBuffer sql = sql1;
		
		try{
			session = HibernateUtil.getSession();
			session.beginTransaction();
			
			Query query = session.createSQLQuery(sql.toString());
			query.setParameter(1, new Date());
			query.executeUpdate();
			
			session.getTransaction().commit();
			b = true;
		} catch(HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		return b;
	}
}
