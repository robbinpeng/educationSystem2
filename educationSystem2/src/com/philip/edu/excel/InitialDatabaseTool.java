package com.philip.edu.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.HibernateUtil;
import com.philip.edu.rule.ExcelHelper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class InitialDatabaseTool {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InitialDatabaseTool tool = new InitialDatabaseTool();
		tool.updateDict();

	}
	
	public void updateDict() {
		FileInputStream in = null;
		Workbook wb = null;
		Session session = null;
		ExcelHelper helper = new ExcelHelper();

		try {
			in = new FileInputStream("D:/Develop/education/tool.xls");
			wb = WorkbookFactory.create(in);

			session = HibernateUtil.getSession();
			session.beginTransaction();

			Sheet sheet = wb.getSheetAt(0);

			for(int i=1; i<1032; i++){
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(21);
				
				double form_id = row.getCell(2).getNumericCellValue();
				String physic_name = row.getCell(12).getStringCellValue();
				if(cell == null){
					
					String sql1 = "update FormField set dictid=0 where form_id=" +form_id+ " and physic_name='" + physic_name +"'";
					
					Query query = session.createQuery(sql1);
					query.executeUpdate();
				} else {
					double d = cell.getNumericCellValue();
					String sql2 = "update FormField set dictid="+ d +" where form_id=" +form_id+ " and physic_name='" + physic_name +"'";
					Query query = session.createQuery(sql2);
					query.executeUpdate();
				}
			}

			session.getTransaction().commit();
			System.out.println("success!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void updateFormat() {
		FileInputStream in = null;
		Workbook wb = null;
		Session session = null;
		ExcelHelper helper = new ExcelHelper();

		try {
			in = new FileInputStream("D:/Develop/education/tool.xls");
			wb = WorkbookFactory.create(in);

			session = HibernateUtil.getSession();
			session.beginTransaction();

			Sheet sheet = wb.getSheetAt(0);

			for (int j = 1; j < 1032; j++) {
				Row row = sheet.getRow(j);
				Cell cell = row.getCell(2);
				double dForm_id = cell.getNumericCellValue();
				int form_id = new Double(dForm_id).intValue();
				cell = row.getCell(12);
				String name = cell.getStringCellValue();

				String hql = "FROM FormField WHERE form_id=" + form_id + " AND physic_name='" + name + "'";
				ArrayList list = (ArrayList) session.createQuery(hql).list();
				System.out.println(list.size());
				FormField field = (FormField) list.get(0);

				// data type:
				cell = row.getCell(14);
				String data_type = cell.getStringCellValue();
				if ("string".equals(data_type)) {
					field.setData_type(Constants.V_DATA_TYPE_STRING);
				} else if ("int".equals(data_type)) {
					field.setData_type(Constants.V_DATA_TYPE_INTEGER);
				} else if ("data".equals(data_type)) {
					field.setData_type(Constants.V_DATA_TYPE_DATE);
				} else if ("double".equals(data_type)) {
					field.setData_type(Constants.V_DATA_TYPE_FLOAT);
				} else if ("clob".equals(data_type)) {
					field.setData_type(Constants.V_DATA_TYPE_BIG_STRING);
				}

				// format:
				cell = row.getCell(22);
				if (cell != null) {
					String text_format = cell.getStringCellValue();
					if (text_format != null && !text_format.equals("")) {
						if ("mobile".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_MOBILEPHONE);
						} else if ("email".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_EMAIL);
						} else if ("url".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_WEBSITE);
						} else if ("yyyy".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_DATE_YEAR);
						} else if ("yyyy-mm".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_DATE_MONTH);
						} else if ("yyyymm".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH);
						} else if ("yyyy-mm-dd".equals(text_format)) {
							field.setText_format(Constants.V_TEXT_FORMAT_DATE_DAY);
						}
					} else {
						field.setText_format(Constants.V_TEXT_FORMAT_NO);
					}
				} else {
					field.setText_format(Constants.V_TEXT_FORMAT_NO);
				}

				session.update(field);
			}

			session.getTransaction().commit();
			System.out.println("success!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void addDirectoryData() {
		FileInputStream in = null;
		Workbook wb = null;
		Session session = null;
		ExcelHelper helper = new ExcelHelper();

		try {
			in = new FileInputStream("D:/Develop/education/普通高等学校本科专业目录.xls");
			wb = WorkbookFactory.create(in);

			session = HibernateUtil.getSession();
			session.beginTransaction();

			Sheet sheet = wb.getSheetAt(0);

			for (int i = 1; i < 669; i++) {
				Row row = sheet.getRow(i);

				String sql = "insert into TBL_PTGDXXBKZYML( CREATOR, CREATE_TIME, LAST_OPERATOR, LAST_OPERATE_TIME, STATUS, ZYDM, ZYMC, DMBB, XKML) values (?,?,?,?,?,?,?,?,?)";

				Query query = session.createSQLQuery(sql);
				query.setParameter(0, 1);
				query.setParameter(1, new Date());
				query.setParameter(2, 1);
				query.setParameter(3, new Date());
				query.setParameter(4, 1);

				System.out.println(i);

				Cell cell = row.getCell(0);
				String value = (String) helper.getCellValue(cell);
				query.setParameter(5, value);

				cell = row.getCell(1);
				value = (String) helper.getCellValue(cell);
				query.setParameter(6, value);

				cell = row.getCell(2);
				value = (String) helper.getCellValue(cell);
				query.setParameter(7, value);

				cell = row.getCell(3);
				value = (String) helper.getCellValue(cell);
				query.setParameter(8, value);

				query.executeUpdate();
			}

			session.getTransaction().commit();
			System.out.println("success!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

	public void initialDatabaseSetup() {
		FileInputStream in = null;
		Workbook wb = null;
		Session session = null;
		ExcelHelper helper = new ExcelHelper();

		try {
			in = new FileInputStream("D:/Develop/education/tool.xls");
			wb = WorkbookFactory.create(in);

			session = HibernateUtil.getSession();
			session.beginTransaction();

			Sheet sheet = wb.getSheetAt(0);

			for (int j = 1; j < 1032; j++) {
				Row row = sheet.getRow(j);

				FormField field = new FormField();
				field.setBus_name(row.getCell(13).getStringCellValue());
				field.setPhysic_name(row.getCell(12).getStringCellValue());
				Cell cell = row.getCell(20);
				String temp = String.valueOf(cell.getNumericCellValue());
				if (temp.charAt(0) == '0')
					field.setIs_required('Y');
				else
					field.setIs_required('N');

				cell = row.getCell(16);
				double i = cell.getNumericCellValue();
				int sequence = new Double(i).intValue();
				field.setSequence(sequence);

				field.setData_type(1);

				cell = row.getCell(15);
				i = cell.getNumericCellValue();
				int length = new Double(i).intValue();
				field.setLength(length);

				cell = row.getCell(19);
				i = cell.getNumericCellValue();
				int show = new Double(i).intValue();
				if (show == 0)
					field.setIs_report('N');
				else
					field.setIs_report('Y');

				cell = row.getCell(23);
				Object temp1 = helper.getCellValue(cell);
				if (temp1 == null || "".equals(temp1.toString()))
					field.setIs_hidden('N');
				else {
					int iTemp = ((Integer) temp1).intValue();
					if (iTemp == 1)
						field.setIs_hidden('Y');
				}

				// field.setCompute(compute);

				cell = row.getCell(2);
				i = cell.getNumericCellValue();
				int form_id = new Double(i).intValue();
				field.setForm_id(form_id);

				session.save(field);

			}

			session.getTransaction().commit();

			System.out.println("successfully imported!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
	}

}
