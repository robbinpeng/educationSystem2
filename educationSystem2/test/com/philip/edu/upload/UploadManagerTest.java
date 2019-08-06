package com.philip.edu.upload;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.excel.RapidExcelHelper;

public class UploadManagerTest {
	
	private static Logger logger = Logger.getLogger(UploadManagerTest.class);
	private FormManager formManager = new FormManager();
	RapidExcelHelper rapidHelper = new RapidExcelHelper();

	@Test
	public void testUploadData() {
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1/3-5-4.xlsx");
			rapidHelper.processFirstSheetStream(in);
			
			int excelColumns = rapidHelper.getColumns();
			int lines = rapidHelper.getLines();
			int excelLines = 0;
			
			logger.info("excelColumns:" + excelColumns);
			
			ArrayList all = rapidHelper.getAll();
			int x=0;
			for(x=0; x<lines; x++){
				ArrayList line = (ArrayList)all.get(x);
				logger.info("line.size()=" + line.size() + ", excelColumns=" + excelColumns);
				if(line.size()!=excelColumns)break;
			}
			excelLines = x;
			
			logger.info("excelLines:" + excelLines);
			
			String[][] data = new String[excelLines][excelColumns];

			for(int i=0; i<excelLines; i++){
				ArrayList line = (ArrayList)all.get(i);
				for(int j=0; j<excelColumns; j++){
					String cell = (String)line.get(j);
					data[i][j] = cell;
				}
			}
			
			rapidHelper.refresh();
			
			// check the excel is right:
			UploadManager manager = new UploadManager();
			manager.uploadData(data, 54, Constants.USER_ID, 18);
			
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
		} catch (Exception e) {
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
