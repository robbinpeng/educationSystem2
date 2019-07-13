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
			in = new FileInputStream("D:/Develop/education/test/表7-3-2 教学成果奖（近一届）.xls");
			wb = WorkbookFactory.create(in);
			 
			list = engine.rulesCheck(105, wb, 10);
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
			in = new FileInputStream("D:/Develop/education/test/表1-1 学校概况.xls");
			wb = WorkbookFactory.create(in);
			
			test = manager.formatCheck(21, wb);
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
	
	@Test
	public void testDictionCheck(){
		boolean test = true;
		RuleManager manager = new RuleManager();
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream("D:/Develop/education/test/表1-1 学校概况.xls");
			wb = WorkbookFactory.create(in);
			
			MessageInfo message = manager.DictionCheck(22, wb);
			if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){
				
			} else {
				ArrayList al = message.getMessage_info();
				for(int i=0; i<al.size(); i++){
					String error = (String)al.get(i);
					System.out.println(error);
				}
			}
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
	public void testGetRules(){
		RuleManager manager = new RuleManager();
		ArrayList al = null;
		
		al = manager.getRules(26);
		//assertEquals(al.size(), 4);
		
		al = manager.getActiveRules(26);
		//assertEquals(al.size(), 3);
	}
	
	@Test
	public void testTextFormat(){
		RuleManager manager = new RuleManager();
		FileInputStream in = null;
		Workbook wb = null;
		
		try {
			in = new FileInputStream("D:/Develop/education/test/表1-1 学校概况.xls");
			wb = WorkbookFactory.create(in);
			
			MessageInfo message = manager.textFormatCheck(22, wb);
			if(message.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){
				
			} else {
				ArrayList al = message.getMessage_info();
				for(int i=0; i<al.size(); i++){
					String error = (String)al.get(i);
					System.out.println(error);
				}
			}
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
