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
			in = new FileInputStream("D:/Develop/education/test/表1-6-1 教职工基本信息.xls");
			wb = WorkbookFactory.create(in);
			
			// check the excel is right:
			UploadManager manager = new UploadManager();
			
			//boolean isSuccess = manager.uploadData(wb, 28, Constants.USER_ID, 3);
			//if(isSuccess)logger.info("成功上传数据！");
			//else logger.info("上传数据失败。");
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
		Form form = formManager.getFormById(28);
		UploadManager uploadManager = new UploadManager();
		int lines = uploadManager.rollbackData(form, Constants.TASK_ID);
		assertNotEquals(lines, 0);
		
		boolean b = uploadManager.updateRollback(form.getId(), Constants.TASK_ID);
		assertEquals(b, true);
	}
	
	@Test
	public void testUploadUpdate(){
		UploadManager uploadManager = new UploadManager();
		//boolean test = uploadManager.uploadUpdate(28, Constants.TASK_ID);
		//assertEquals(test, true);
	}
	
	@Test
	public void testRollbackUpdate(){
		UploadManager uploadManager = new UploadManager();
		boolean test = uploadManager.updateRollback(28, 3);
		assertEquals(test, true);
	}
}
