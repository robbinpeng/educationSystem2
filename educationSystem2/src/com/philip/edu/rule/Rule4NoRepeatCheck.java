package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.HibernateUtil;

public class Rule4NoRepeatCheck {

	private Logger logger = Logger.getLogger(Rule4NoRepeatCheck.class);
	private static ExcelHelper helper = new ExcelHelper();
	private static FormManager manager = new FormManager();

	public MessageInfo getMessage(String[][] data, JSONObject object, int form_id) {
		int[] columns = new int[10];
		logger.info("rule 4: begin to check");
		int bit = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";

		MessageInfo message = new MessageInfo();
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		ArrayList messageList = new ArrayList();

		int excelColumns = data[0].length;
		int lines = data.length;

		// Precondition:
		JSONArray preArray = (JSONArray) object.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			String physic_name = preObj.getString("field");
			FormField field = manager.getFieldByPhysicName(form_id, physic_name);
			conditionColumn = helper.getColumn2Check(data, field.getBus_name());
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}

		String keyInfo = "(";

		ArrayList al = translateRules(object, form_id);
		bit = al.size();
		for (int i = 0; i < al.size(); i++) {
			FormField field = (FormField) al.get(i);
			keyInfo += field.getBus_name() + ",";
			int index = helper.getColumn2Check(data, field.getBus_name());
			columns[i] = index;
		}
		keyInfo += ")";

		//SXSSFSheet sheet = wb.getSheetAt(0);
		for (int j = 1; j < lines; j++) {

			//SXSSFRow row = sheet.getRow(j);
			// 1. check the precondition:
			if (isCondition) {
				//SXSSFCell conCell = row.getCell(conditionColumn);
				String testValue = data[j][conditionColumn];
				if (testValue == null)
					continue;
				//Object value = helper.getCellValue(conCell);
				//testValue = value.toString();

				// operator:
				if (Constants.V_EQUAL.equals(conOperator)) {
					// =
					if (!conValue.equals(testValue))
						continue;
				} else if (Constants.V_NOEQUAL.equals(conOperator)) {
					if (conValue.equals(testValue))
						continue;
				} else {
					int left = 0;
					int right = 0;
					try {
						left = Integer.parseInt(testValue);
						right = Integer.parseInt(conValue);
						if (Constants.V_GREATT.equals(conOperator)) {
							if (!(left > right))
								continue;
						} else if (Constants.V_GREATTE.equals(conOperator)) {
							if (!(left >= right))
								continue;
						} else if (Constants.V_LESST.equals(conOperator)) {
							if (!(left < right))
								continue;
						} else if (Constants.V_LESSTE.equals(conOperator)) {
							if (!(left <= right))
								continue;
						}
					} catch (NumberFormatException e) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j + 1) + "行的条件不是数字！");
					}

				}
			}

			// 2. check the rule:
			String key = null;

			StringBuffer result = new StringBuffer("");

			for (int k = 0; k < bit; k++) {
				//SXSSFCell cell = row.getCell(columns[k]);
				String testValue = data[j][columns[k]];
				if (testValue != null) {
					result.append(testValue);
				}
			}

			for (int l = 0; l < j; l++) {
				//SXSSFRow row1 = sheet.getRow(l);
				StringBuffer compare = new StringBuffer("");

				for (int m = 0; m < bit; m++) {
					//SXSSFCell cell1 = row1.getCell(columns[m]);
					String testValue = data[l][columns[m]];
					if (testValue != null) {
						//Object value = helper.getCellValue(cell1);
						//testValue = value.toString();
						compare.append(testValue);
					}
				}

				//logger.info("result:" + result + ", compare:" + compare);
				if (result.toString().equals(compare.toString())) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (j + 1) + "行的主键" + keyInfo + "与第" + (l + 1) + "行记录重复！");
					break;
				}
			}
		}

		message.setMessage_info(messageList);
		;

		return message;
	}

	public MessageInfo getMessageSingleLine(ArrayList record, JSONObject object, int form_id, int task_id) {
		int[] columns = new int[10];
		logger.info("rule 4: begin to check");
		int bit = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";

		MessageInfo message = new MessageInfo();
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		ArrayList messageList = new ArrayList();

		// int excelColumns = helper.getExcelColumns(wb);
		// int lines = helper.getExcelLines(wb, form_id, excelColumns);

		// Precondition:
		JSONArray preArray = (JSONArray) object.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		String physic_name = "";

		Form form = manager.getFormById(form_id);
		ArrayList fields = manager.getFormFields(form_id);

		ArrayList displayFields = new ArrayList();
		for (int i = 0; i < fields.size(); i++) {
			FormField fieldTemp = (FormField) fields.get(i);
			if (fieldTemp.getIs_hidden() == 'N')
				displayFields.add(fieldTemp);
		}

		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			physic_name = preObj.getString("field");
			FormField field = manager.getFieldByPhysicName(form_id, physic_name);
			for (int i = 0; i < displayFields.size(); i++) {
				FormField fieldTemp = (FormField) fields.get(i);
				if (fieldTemp.getPhysic_name().equals(physic_name))
					conditionColumn = i;
			}
			// conditionColumn = helper.getColumn2Check(wb, field.getBus_name(),
			// excelColumns);
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}

		String keyInfo = "(";

		ArrayList al = translateRules(object, form_id);
		bit = al.size();
		for (int i = 0; i < al.size(); i++) {
			FormField field = (FormField) al.get(i);
			keyInfo += field.getBus_name() + ",";
			// int index = helper.getColumn2Check(wb, field.getBus_name(),
			// excelColumns);
			int j = 0;
			for (j = 0; j < displayFields.size(); j++) {
				FormField fieldTemp = (FormField) displayFields.get(j);
				if (fieldTemp.getPhysic_name().equals(field.getPhysic_name()))
					break;
			}
			columns[i] = j;
		}
		keyInfo += ")";

		// 1. check the precondition:
		if (isCondition) {
			DataInfo data = (DataInfo) record.get(conditionColumn);

			if (data == null)
				return message;

			String testValue = "";

			testValue = data.getValue();

			// operator:
			if (Constants.V_EQUAL.equals(conOperator)) {
				// =
				if (!conValue.equals(testValue))
					return message;
			} else if (Constants.V_NOEQUAL.equals(conOperator)) {
				if (conValue.equals(testValue))
					return message;
			} else {
				int left = 0;
				int right = 0;
				try {
					left = Integer.parseInt(testValue);
					right = Integer.parseInt(conValue);
					if (Constants.V_GREATT.equals(conOperator)) {
						if (!(left > right))
							return message;
					} else if (Constants.V_GREATTE.equals(conOperator)) {
						if (!(left >= right))
							return message;
					} else if (Constants.V_LESST.equals(conOperator)) {
						if (!(left < right))
							return message;
					} else if (Constants.V_LESSTE.equals(conOperator)) {
						if (!(left <= right))
							return message;
					}
				} catch (NumberFormatException e) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("条件中不是数字，不能进行比较！");
					message.setMessage_info(messageList);
					return message;
				}

			}
		}

		// 2. check the rule:
		String key = null;

		StringBuffer result = new StringBuffer("");
		
		StringBuffer sb = new StringBuffer("select * from " + form.getPhsic_name() + " where ");
		
		for (int k = 0; k < bit; k++) {
			DataInfo data = (DataInfo) record.get(columns[k]);
			
			String testValue = "";
			if (data != null) {
				if(k!=bit-1)
					sb.append("" + data.getKey() + "='" + data.getValue() + "' and ");
				else 
					sb.append("" + data.getKey() + "='" + data.getValue() + "' and task_id=" + task_id);
			}
		}

		String sql = sb.toString();
		//logger.info("sql:" + sql);
		
		Session session = null;
		ArrayList al1 = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createSQLQuery(sql);
			al1 = (ArrayList)query.list();

			if(al1 != null && al1.size()>0){
				message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				messageList.add("上传记录与数据库中有重复！");
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession(session);
		}
		
		message.setMessage_info(messageList);

		return message;
	}

	private ArrayList translateRules(JSONObject object, int form_id) {
		logger.info("begin to translate the rule");
		ArrayList al = new ArrayList();
		JSONArray array = (JSONArray) object.get("rules");

		for (int i = 0; i < array.length(); i++) {
			JSONObject ob = array.getJSONObject(i);
			if ("field".equals(ob.get("type").toString())) {
				// al.add(ob.get("field").toString());
				String field_name = ob.get("field").toString();
				FormField field = manager.getFieldByPhysicName(form_id, field_name);
				al.add(field);
			}
		}

		return al;
	}
}
