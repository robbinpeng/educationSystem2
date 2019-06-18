package com.philip.edu.upload;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;

public class UploadManagerTest {
	
	private static Logger logger = Logger.getLogger(UploadManagerTest.class);
	private FormManager formManager = new FormManager();

	@Test
	public void testUploadData() {
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1-4.xls");
			wb = WorkbookFactory.create(in);
			
			// check the excel is right:
			UploadManager manager = new UploadManager();
			
			boolean isSuccess = manager.uploadData(wb, 5, Constants.USER_ID);
			if(isSuccess)logger.info("�ɹ��ϴ����ݣ�");
			else logger.info("�ϴ�����ʧ�ܡ�");
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

	@Test
	public void testRollbackData() {
		Form form = formManager.getFormById(Constants.FORM_ID);
		UploadManager uploadManager = new UploadManager();
		int lines = uploadManager.rollbackData(form);
		assertNotEquals(lines, 0);
		
		boolean b = uploadManager.updateRollback(form.getId());
		assertEquals(b, true);
	}
}
