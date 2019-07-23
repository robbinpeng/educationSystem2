package com.philip.edu.upload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormStatus;
import com.philip.edu.basic.HibernateUtil;

public class UploadDAO {

	private static Logger logger = Logger.getLogger(UploadDAO.class);

	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		String[] str = null;
		String dependency = "(1),(3),(5)";
		str = dependency.split(",");

		for (int i = 0; i < str.length; i++) {
			String temp = str[i].substring(1, str[i].length() - 1);
			System.out.println("i:" + i + temp);
		}
	}

	public boolean uploadData(ArrayList list) {
		Session session = null;
		boolean b = false;

		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();

			for (int i = 0; i < list.size(); i++) {
				String sql = (String) list.get(i);

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				Query query = session.createSQLQuery(sql);
				query.setParameter(1, new Date());
				query.setParameter(2, new Date());
				//query.setParameter(2, format.format(new Date()));
				query.executeUpdate();
			}

			session.getTransaction().commit();
			b = true;

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}

		return b;
	}

	public boolean uploadUpdate(int form_id, int task_id) {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		Session session = null;

		try {
			// 1.update self status to success;
			session = HibernateUtil.getSession();
			session.beginTransaction();

			//FormStatus status = session.get(FormStatus.class, task);
			Query query = session.createQuery("From FormStatus where form_id=" + form_id + " and task_id=" + task_id);
			ArrayList al = (ArrayList) query.list();
			FormStatus status = (FormStatus)al.get(0);
			status.setForm_status(Constants.STATUS_SUCCESS);
			session.update(status);

			// 2.update depency table to check whether can be changed to
			// uploadable:
			query = session.createQuery("From Form where dependency_form like :dependency");
			query.setParameter("dependency", "%(" + form_id + ")%");
			ArrayList list = (ArrayList) query.list();
			
			for (int i = 0; i < list.size(); i++) {
				boolean isUploadable = true;

				Form form = (Form) list.get(i);
				ArrayList tables = getDependencies(form.getDependency_form());

				for (int j = 0; j < tables.size(); j++) {
					Integer table_id = (Integer) tables.get(j);
					//FormStatus status1 = session.get(FormStatus.class, table_id.intValue());
					Query qSql = session.createQuery("From FormStatus where form_id="+table_id+" and task_id=" + task_id);
					ArrayList al1 = (ArrayList) qSql.list();
					FormStatus status1 = (FormStatus)al1.get(0);

					if (status1.getForm_status() != Constants.STATUS_SUCCESS) {
						isUploadable = false;
						break;
					}
				}

				if (isUploadable) {
					//FormStatus status2 = session.get(FormStatus.class, form.getId());
					Query qSql = session.createQuery("From FormStatus where form_id=" + form.getId() + " and task_id=" + task_id);
					ArrayList al1 = (ArrayList) qSql.list();
					FormStatus status2 = (FormStatus)al1.get(0);
					
					status2.setForm_status(Constants.STATUS_UPLOADABLE);
					session.update(status2);
				}

			}

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

	private ArrayList getDependencies(String dependency) {
		ArrayList al = new ArrayList();
		String[] str = null;
		if (dependency.indexOf(",") == -1) {
			String temp = dependency.substring(1, dependency.length() - 1);
			logger.info(dependency);
			int table_id = Integer.parseInt(temp);
			al.add(table_id);
		} else {
			str = dependency.split(",");
			for (int i = 0; i < str.length; i++) {
				String temp = str[i].substring(1, str[i].length() - 1);
				logger.info("split:" + str[i]);
				// System.out.println(temp);
				int table_id = Integer.parseInt(temp);
				al.add(table_id);
			}
		}

		return al;
	}

	public int rollbackData(String table_name, int task_id) {
		int deleted = 0;
		Session session = null;
		String sql = null;

		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();

			sql = "delete from " + table_name + " where task_id=" + task_id;

			Query query = session.createSQLQuery(sql);
			deleted = query.executeUpdate();

			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(session);
		}

		return deleted;
	}

	public boolean updateRollback(int form_id, int task_id) {
		boolean isSuccess = false;
		Session session = null;

		try {
			// 1.update self status to uploadable;
			session = HibernateUtil.getSession();
			session.beginTransaction();

			Form form = session.get(Form.class, form_id);
			Query query = session.createQuery("From FormStatus where form_id=" + form_id + " and task_id=" + task_id);
			ArrayList al = (ArrayList)query.list();
			
			FormStatus status = (FormStatus)al.get(0);
			boolean isUploadable = true;

			if (form.getDependency_form() == null || form.getDependency_form().equals(""))
				isUploadable = true;
			else {
				ArrayList tables = getDependencies(form.getDependency_form());

				for (int j = 0; j < tables.size(); j++) {
					Integer table_id = (Integer) tables.get(j);
					query = session.createQuery("From FormStatus where form_id=" + form_id + " and task_id=" + task_id);
					ArrayList al1 = (ArrayList)query.list();
					
					FormStatus status1 = (FormStatus)al1.get(0);

					if (status1.getForm_status() != Constants.STATUS_SUCCESS) {
						isUploadable = false;
						break;
					}
				}
			}

			if (isUploadable) {
				status.setForm_status(Constants.STATUS_UPLOADABLE);

				session.update(status);
			} else {
				status.setForm_status(Constants.STATUS_CREATED);

				session.update(status);
			}

			// 2.update depency table to create;
			query = session.createQuery("From Form where dependency_form like :dependency");
			query.setParameter("dependency", "%(" + form_id + ")%");
			ArrayList list = (ArrayList) query.getResultList();

			for (int i = 0; i < list.size(); i++) {
				Form form1 = (Form) list.get(i);

				//FormStatus status1 = session.get(FormStatus.class, form1.getId());
				query = session.createQuery("From FormStatus where form_id=" + form1.getId() + " and task_id=" + task_id);
				ArrayList al1 = (ArrayList)query.list();
				
				FormStatus status1 = (FormStatus)al1.get(0);
				status1.setForm_status(Constants.STATUS_CREATED);
				session.update(status1);
			}

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
}
