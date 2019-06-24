package com.philip.edu.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;

import com.philip.edu.basic.FormField;
import com.philip.edu.basic.HibernateUtil;
import com.philip.edu.rule.ExcelHelper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class InitialDatabaseTool {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
				if(temp1==null || "".equals(temp1.toString()))field.setIs_hidden('N');				
				else{
					int iTemp = ((Integer)temp1).intValue();
					if(iTemp == 1) field.setIs_hidden('Y');
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
