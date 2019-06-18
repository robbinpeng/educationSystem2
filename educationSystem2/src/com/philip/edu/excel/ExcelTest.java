package com.philip.edu.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/workspace/testZK/WebContent/template/test.xls");
			wb = WorkbookFactory.create(in);
			
			//wb.setSheetName(0, "test sheet");
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(0);
			Cell cell = row.getCell(2);
			System.out.println(cell.getStringCellValue());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
