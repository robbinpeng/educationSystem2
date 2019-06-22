package com.philip.edu.rule;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jfree.util.Log;
import org.junit.Test;

import com.philip.edu.basic.Constants;

public class RuleManagerTest {

	private static Logger logger = Logger.getLogger(RuleManagerTest.class);
	@Test
	public void testRulesCheck() {
		//do check process:
		FileInputStream in = null;
		Workbook wb = null;
		RuleManager engine = new RuleManager();
		MessageInfo message = null;
		ArrayList list = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1-111.xls");
			wb = WorkbookFactory.create(in);
			
			list = engine.rulesCheck(Constants.FORM_ID, wb);
			for(int j=0; j<list.size(); j++){
				message = (MessageInfo)list.get(j);
				if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){}
				else {
					ArrayList al = message.getMessage_info();
					for(int i=0; i<al.size(); i++){ 
						System.out.println(al.get(i).toString());
						logger.info(al.get(i).toString());
					}
				}
			}

			/*in = new FileInputStream("D:/Develop/education/test/1-11.xls");
			wb = WorkbookFactory.create(in);
			
			list = engine.rulesCheck(Constants.FORM_ID, wb);
			for(int j=0; j<list.size(); j++){
				message = (MessageInfo)list.get(j);
				if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){}
				else {
					ArrayList al = message.getMessage_info();
					for(int i=0; i<al.size(); i++){ 
						System.out.println(al.get(i).toString());
					}
				}
			}*/
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
	public void testFormatCheck(){
		boolean test = true;
		RuleManager manager = new RuleManager();
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream("D:/Develop/education/test/1-11.xls");
			wb = WorkbookFactory.create(in);
			
			test = manager.formatCheck(Constants.FORM_ID, wb);
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
