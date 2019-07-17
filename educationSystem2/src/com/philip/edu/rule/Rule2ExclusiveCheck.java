package com.philip.edu.rule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

import org.apache.log4j.Logger;

public class Rule2ExclusiveCheck {

	private Logger logger = Logger.getLogger(Rule2ExclusiveCheck.class);

	private static FormManager manager = new FormManager();
	private static ExcelHelper helper = new ExcelHelper();
	private static RuleDAO dao = new RuleDAO();

	public MessageInfo getMessage1(Workbook wb, JSONObject object, int form_id, int task_id) {
		logger.info("start to check the uploaded excel:");

		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		String ruleSQL = null;
		String bus_name = null;
		String relate_table = null;
		String relate_field = null;
		ArrayList al = null;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		// 1. fetch the sql:
		try {
			al = this.TranslateRuleSimple(object, form_id, task_id);

			ruleSQL = (String) al.get(0);
			bus_name = (String) al.get(1);
			relate_table = (String) al.get(2);
			relate_field = (String) al.get(3);
		} catch (NotImplementException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getErrorMessage());
			e1.printStackTrace();

			message.setMessage_type(Constants.RULECHECK_MESSAGE_NOT_IMPLEMENT);
			messageList.add("规则条件较为复杂，系统不能处理。");
			message.setMessage_info(messageList);
			return message;
		}

		// 2. make sure columns are the same:
		/*
		 * boolean format_right = helper.is_format_right(wb, form_id);
		 * 
		 * if (format_right == false) {
		 * message.setMessage_type(Constants.RULECHECK_MESSAGE_FORMAT_WRONG);
		 * messageList.add("上传的表格与模板不符，请校验！");
		 * message.setMessage_info(messageList); return message; }
		 */

		// 3. make sure total lines:
		int excelColumns = helper.getExcelColumns(wb);
		int lines = 0;
		lines = helper.getExcelLines(wb,form_id,excelColumns);

		// 4. get the correct column:

		int column = helper.getColumn2Check(wb, bus_name, excelColumns);

		// 5. cycle the column to end:
		message = checkRule(ruleSQL, wb, column, lines, bus_name, relate_table, relate_field);

		return message;
	}

	public MessageInfo getMessage(Workbook wb, JSONObject object, int form_id, int task_id) {
		logger.info("start to check the uploaded excel:");

		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		String ruleSQL = null;
		String bus_name = null;
		String relate_table = null;
		String relate_field = null;
		ArrayList al = null;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		// 3. make sure total lines:
		int excelColumns = helper.getExcelColumns(wb);
		int lines = 0;
		lines = helper.getExcelLines(wb, form_id, excelColumns);

		// 4. get the correct column:

		Sheet sheet = wb.getSheetAt(0);
		
		al = this.TranslateRuleComplex(object, form_id, task_id);
		
		for (int i = 1; i <= lines - 1; i++) {
			Row row = sheet.getRow(i);
			
			for(int j = 0; j < al.size(); j++){

				// 1. fetch the sql:
				ArrayList result = (ArrayList)al.get(j);
				
				ruleSQL = (String) result.get(0);
				bus_name = (String) result.get(1);
				relate_table = (String) result.get(2);
				relate_field = (String) result.get(3);
				
				int column = helper.getColumn2Check(wb, bus_name, excelColumns);
				
				Cell cell = row.getCell(column);
				if (cell == null)
					continue;
				String fieldValue = "";
				Object value = helper.getCellValue(cell);

				fieldValue = value.toString();
				ArrayList al1 = dao.getRelateField(ruleSQL, fieldValue);

				if (al1 != null && al1.size() != 0) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);;
					messageList.add("第" + (i+1) + "行的：'" + bus_name + "'中的'" + fieldValue + "'在关联表'" + relate_table + "'的'" + relate_field
							+ "'中有重复！");
				}
			}
						
		}
		message.setMessage_info(messageList);

		return message;
	}

	private ArrayList TranslateRuleSimple(JSONObject rule, int form_id, int task_id) throws NotImplementException {

		logger.info("begin to translate rule simple.");

		String ruleSQL = new String();
		JSONArray array = (JSONArray) rule.get("rules");
		ArrayList result = new ArrayList();

		if (array.length() > 2)
			throw new NotImplementException("Translate many conditions using and has not been implemented!");

		// get table:
		JSONObject obj = (JSONObject) array.get(1);
		String table_name = obj.get("relateForm").toString();
		Form form = manager.getFormByName(Constants.USER_ID, table_name);
		String table = form.getPhsic_name();
		String relate_table = form.getBus_name();
		// get field:
		String field = obj.get("relateField").toString();
		FormField formField = manager.getFieldByPhysicName(form_id, field);
		String relate_field = formField.getBus_name();
		ruleSQL = "select * from " + table + " where " + field + "=? and task_id=" + task_id;
		logger.info("the sql is:" + ruleSQL);

		// get check fieldName:
		obj = (JSONObject) array.get(0);
		String field_name = obj.get("field").toString();
		formField = manager.getFieldByPhysicName(form_id, field_name);
		String bus_name = formField.getBus_name();

		result.add(ruleSQL);
		result.add(bus_name);
		result.add(relate_table);
		result.add(relate_field);

		return result;
	}

	private ArrayList TranslateRuleComplex(JSONObject rule, int form_id, int task_id) {

		logger.info("begin to translate rule simple.");

		String ruleSQL = new String();
		JSONArray array = (JSONArray) rule.get("rules");
		ArrayList result = new ArrayList();
		ArrayList sentence = new ArrayList();

		for (int i = 0; i < array.length(); i++) {
			result = new ArrayList();
			// get check fieldName:
			JSONObject obj = (JSONObject) array.get(i);
			String type = obj.getString("type");
			if (!"field".equals(type))
				continue;

			String field_name = obj.get("field").toString();
			FormField formField = manager.getFieldByPhysicName(form_id, field_name);
			String bus_name = formField.getBus_name();

			// get table:
			obj = (JSONObject) array.get(++i);
			String table_name = obj.get("relateForm").toString();
			Form form = manager.getFormByName(Constants.USER_ID, table_name);
			String table = form.getPhsic_name();
			String relate_table = form.getBus_name();
			// get field:
			String field = obj.get("relateField").toString();
			logger.info("the form id:" + table);
			logger.info("the field is:" + field);
			formField = manager.getFieldByPhysicName(form.getId(), field);
			String relate_field = formField.getBus_name();
			ruleSQL = "select * from " + table + " where " + field + "=? and task_id=" + task_id;
			logger.info("the sql is:" + ruleSQL);

			result.add(ruleSQL);
			result.add(bus_name);
			result.add(relate_table);
			result.add(relate_field);

			sentence.add(result);
		}

		return sentence;
	}

	private String TranslateRule(JSONObject rule) {
		return null;
	}

	private MessageInfo checkRule(String sql, Workbook wb, int column, int lines, String bus_name, String relate_table,
			String relate_field) {
		Sheet sheet = wb.getSheetAt(0);
		PreparedStatement ps = null;
		ResultSet rs = null;
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		for (int i = 1; i <= lines - 1; i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(column);
			if (cell == null)
				continue;
			String fieldValue = "";
			Object value = helper.getCellValue(cell);

			fieldValue = value.toString();
			ArrayList al = dao.getRelateField(sql, fieldValue);

			if (al != null && al.size() != 0) {
				messageList.add("'" + bus_name + "'中的'" + fieldValue + "'在关联表'" + relate_table + "'的'" + relate_field
						+ "'中有重复！");
			}
		}

		if (messageList.size() != 0) {
			message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
			message.setMessage_info(messageList);
		}

		message.setMessage_info(messageList);

		return message;

	}
}
