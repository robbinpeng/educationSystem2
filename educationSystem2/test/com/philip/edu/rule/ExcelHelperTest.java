package com.philip.edu.rule;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import com.philip.edu.basic.Constants;

public class ExcelHelperTest {

	@Test
	public void testIs_format_right() {
		boolean test = true;
		ExcelHelper helper = new ExcelHelper();
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream("D:/Develop/education/test/1-11.xls");
			wb = WorkbookFactory.create(in);
			
			test = helper.is_format_right(wb, Constants.FORM_ID);
			assertEquals(test, true);
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
