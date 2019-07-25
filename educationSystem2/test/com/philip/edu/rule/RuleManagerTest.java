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
import com.philip.edu.basic.DataInfo;

public class RuleManagerTest {

	private static Logger logger = Logger.getLogger(RuleManagerTest.class);

	@Test
	public void testRulesCheck() {
		// do check process:
		FileInputStream in = null;
		Workbook wb = null;
		RuleManager engine = new RuleManager();
		MessageInfo message = null;
		ArrayList list = null;

		/*
		 * try { in = new FileInputStream("D:/Develop/education/test/2-7.xls");
		 * wb = WorkbookFactory.create(in);
		 * 
		 * list = engine.rulesCheck(40, wb, 10); for (int j = 0; j <
		 * list.size(); j++) { message = (MessageInfo) list.get(j); if
		 * (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
		 * } else { ArrayList al = message.getMessage_info(); for (int i = 0; i
		 * < al.size(); i++) { // System.out.println(al.get(i).toString());
		 * logger.info(al.get(i).toString()); } } }
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (EncryptedDocumentException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	@Test
	public void testFormatCheckSingleLine() {
		boolean test = true;
		RuleManager manager = new RuleManager();
		ArrayList al = new ArrayList();

		DataInfo data = new DataInfo();
		data.setKey("TJSJ");
		data.setValue("2019-07-30");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXMC");
		data.setValue("测试学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXDM");
		data.setValue("CSXX");
		al.add(data);
		data = new DataInfo();
		data.setKey("YWMC");
		data.setValue("english school");
		al.add(data);
		data = new DataInfo();
		data.setKey("BXLX");
		data.setValue("私立学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXXZ");
		data.setValue("民营学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("JBZ");
		data.setValue("教育部");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZGBM");
		data.setValue("主管部门");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXWZ");
		data.setValue("fdsafsa");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZSPC");
		data.setValue("diyipi");
		al.add(data);
		data = new DataInfo();
		data.setKey("KBBKJYNF");
		data.setValue("2019-08");
		al.add(data);
		data = new DataInfo();
		data.setKey("XM");
		data.setValue("中国");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDH");
		data.setValue("13454");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDZYX");
		data.setValue("robbin");
		al.add(data);

		MessageInfo info = manager.textFormatSingleCheck(21, al);

		if (info.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
		} else {
			ArrayList al1 = info.getMessage_info();
			for (int i = 0; i < al1.size(); i++) {
				// System.out.println(al.get(i).toString());
				logger.info(al1.get(i).toString());
			}
		}

		assertNotEquals(info.getMessage_info().size(), 0);
	}

	@Test
	public void testDictionCheckSingleLine() {
		RuleManager manager = new RuleManager();
		ArrayList al = new ArrayList();

		DataInfo data = new DataInfo();
		data.setKey("TJSJ");
		data.setValue("2019-07-30");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXMC");
		data.setValue("测试学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXDM");
		data.setValue("CSXX");
		al.add(data);
		data = new DataInfo();
		data.setKey("YWMC");
		data.setValue("english school");
		al.add(data);
		data = new DataInfo();
		data.setKey("BXLX");
		data.setValue("私立学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXXZ");
		data.setValue("民营学校");
		al.add(data);
		data = new DataInfo();
		data.setKey("JBZ");
		data.setValue("教育部");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZGBM");
		data.setValue("主管部门");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXWZ");
		data.setValue("fdsafsa");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZSPC");
		data.setValue("[提前批招生,第一批次招生]");
		al.add(data);
		data = new DataInfo();
		data.setKey("KBBKJYNF");
		data.setValue("2019-08");
		al.add(data);
		data = new DataInfo();
		data.setKey("XM");
		data.setValue("中国");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDH");
		data.setValue("13454");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDZYX");
		data.setValue("robbin");
		al.add(data);

		MessageInfo info = manager.DictionCheckSingleLine(21, al);

		if (info.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
		} else {
			ArrayList al1 = info.getMessage_info();
			for (int i = 0; i < al1.size(); i++) {
				// System.out.println(al.get(i).toString());
				logger.info(al1.get(i).toString());
			}
		}

		assertNotEquals(info.getMessage_info().size(), 0);
	}

	@Test
	public void testRuleCheckSingleLine() {
		RuleManager manager = new RuleManager();
		ArrayList al = new ArrayList();

		DataInfo data = new DataInfo();
		data.setKey("TJSJ");
		data.setValue("2019-07-30");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXMC");
		data.setValue("科研处");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXDM");
		data.setValue("7");
		al.add(data);
		data = new DataInfo();
		data.setKey("YWMC");
		data.setValue("吴志武");
		al.add(data);
		data = new DataInfo();
		data.setKey("BXLX");
		data.setValue("其他");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXXZ");
		data.setValue("3.0");
		al.add(data);
		data = new DataInfo();
		data.setKey("JBZ");
		data.setValue("2.0");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZGBM");
		data.setValue("主管部门");
		al.add(data);
		data = new DataInfo();
		data.setKey("XXWZ");
		data.setValue("fdsafsa");
		al.add(data);
		data = new DataInfo();
		data.setKey("ZSPC");
		data.setValue("[提前批招生,第一批次招生]");
		al.add(data);
		data = new DataInfo();
		data.setKey("KBBKJYNF");
		data.setValue("2019-08");
		al.add(data);
		data = new DataInfo();
		data.setKey("XM");
		data.setValue("中国");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDH");
		data.setValue("13454");
		al.add(data);
		data = new DataInfo();
		data.setKey("LXDZYX");
		data.setValue("robbin");
		al.add(data);

		ArrayList al2 = manager.rulesCheckSingleLine(21, al, 12);

		for (int j = 0; j < al2.size(); j++) {
			MessageInfo info = (MessageInfo) al2.get(j);

			if (info.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
			} else {
				ArrayList al1 = info.getMessage_info();
				for (int i = 0; i < al1.size(); i++) {
					// System.out.println(al.get(i).toString());
					logger.info(al1.get(i).toString());
				}
			}
		}

		assertNotEquals(al2.size(), 0);
	}

	@Test
	public void testDictionCheck() {
		boolean test = true;
		RuleManager manager = new RuleManager();
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream("D:/Develop/education/test/表1-1 学校概况.xls");
			wb = WorkbookFactory.create(in);

			MessageInfo message = manager.DictionCheck(22, wb);
			if (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {

			} else {
				ArrayList al = message.getMessage_info();
				for (int i = 0; i < al.size(); i++) {
					String error = (String) al.get(i);
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
	public void testGetRules() {
		RuleManager manager = new RuleManager();
		ArrayList al = null;

		al = manager.getRules(26);
		// assertEquals(al.size(), 4);

		al = manager.getActiveRules(26);
		// assertEquals(al.size(), 3);
	}

	@Test
	public void testTextFormat() {
		RuleManager manager = new RuleManager();
		FileInputStream in = null;
		Workbook wb = null;

		try {
			in = new FileInputStream("D:/Develop/education/test/2-7.xls");
			wb = WorkbookFactory.create(in);

			MessageInfo message = manager.textFormatCheck(40, wb);
			if (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {

			} else {
				ArrayList al = message.getMessage_info();
				for (int i = 0; i < al.size(); i++) {
					String error = (String) al.get(i);
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
