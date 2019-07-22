package com.philip.edu.rule;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.HibernateUtil;

public class Rule5OutsideConstraintCheck {

	private Logger logger = Logger.getLogger(Rule5OutsideConstraintCheck.class);

	private static ExcelHelper helper = new ExcelHelper();
	private static FormManager fManager = new FormManager();
	private static RuleDAO dao = new RuleDAO();

	public MessageInfo getMessage(Workbook wb, JSONObject obj, int form_id, int task_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		ArrayList rules = null;
		int columns = 0;
		int lines = 0;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		columns = helper.getExcelColumns(wb);
		lines = helper.getExcelLines(wb, form_id, columns);

		rules = this.translateRulesSured(obj);

		JSONArray tempLeft = (JSONArray) rules.get(0);
		JSONArray tempRight = (JSONArray) rules.get(1);
		JSONObject objOP = (JSONObject) rules.get(2);
		String sOP = objOP.getString("operator");

		Sheet sheet = wb.getSheetAt(0);

		// left sum:
		double leftValue = 0;
		double rightValue = 0;

		for (int j = 1; j < lines; j++) {
			if (tempRight.length() == 1) {
				Row row = sheet.getRow(j);
				for (int i = 0; i < tempLeft.length(); i++) {
					JSONObject obj1 = (JSONObject) tempLeft.get(i);
					String type1 = obj1.getString("type");
					if (Constants.RULE_FORMFIELD.equals(type1)) {
						String field_name = obj1.getString("field");
						FormField field1 = fManager.getFieldByPhysicName(form_id, field_name);
						int column1 = helper.getColumn2Check(wb, field1.getBus_name(), columns);
						Cell cell = row.getCell(column1);
						Object value = helper.getCellValue(cell);
						if (CellType.NUMERIC == cell.getCellType()) {
							try {
								double temp = (double) value;
								leftValue += temp;
							} catch (ClassCastException e) {
								Integer temp = (Integer) value;
								leftValue += temp.doubleValue();
							}
						} else {
							String sTemp = value.toString();
							double temp = 0;
							if(sTemp!=null&&!"".equals(sTemp))temp = new Double(sTemp).doubleValue();
							leftValue += temp;
						}
					} else if (Constants.RULE_OPERATOR.equals(type1)) {
						// do nothing.
					}
				}

				// right:
				JSONObject obj2 = (JSONObject) tempRight.get(0);
				// get table:
				String table_name = obj2.get("relateForm").toString();
				Form form = fManager.getFormByName(Constants.USER_ID, table_name);
				String table = form.getPhsic_name();
				String relate_table = form.getBus_name();
				// get field:
				String field = obj2.get("relateField").toString();
				FormField formField = fManager.getFieldByPhysicName(form.getId(), field);
				String relate_field = formField.getBus_name();
				String ruleSQL = "select * from " + table;

				Session session = null;
				ArrayList al = null;

				try {
					session = HibernateUtil.getSession();

					Query query = session.createSQLQuery(ruleSQL)
							.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

					al = (ArrayList) query.list();
				} catch (HibernateException e) {
					e.printStackTrace();
				} finally {
					HibernateUtil.closeSession(session);
				}

				String temp;
				HashMap map = (HashMap) al.get(0);
				Object value = map.get(field);
				try {
					temp = (String) value;
					rightValue = Integer.parseInt(temp);
				} catch (ClassCastException e) {
					rightValue = ((BigInteger) value).intValue();
				}
				// rightValue = Integer.parseInt(value);

				// compare:
				if (Constants.V_EQUAL.equals(sOP)) {
					if (!(leftValue == rightValue)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("汇总数据的比较不成立！");
					}
				} else if (Constants.V_GREATT.equals(sOP)) {
					if (!(leftValue > rightValue)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("汇总数据的比较不成立！");
					}
				} else if (Constants.V_GREATTE.equals(sOP)) {
					if (!(leftValue >= rightValue)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("汇总数据的比较不成立！");
					}
				} else if (Constants.V_LESST.equals(sOP)) {
					if (!(leftValue < rightValue)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("汇总数据的比较不成立！");
					}
				} else if (Constants.V_LESSTE.equals(sOP)) {
					if (!(leftValue <= rightValue)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("汇总数据的比较不成立！");
					}
				}

				// left everyline stats:
			} else {
				double sum = 0;
				double sumRight = 0;
				Row row = sheet.getRow(j);

				JSONObject objLeft = (JSONObject) tempLeft.get(0);
				String field_name = objLeft.getString("field");
				FormField field = fManager.getFieldByPhysicName(form_id, field_name);
				int column = helper.getColumn2Check(wb, field.getBus_name(), columns);
				Cell cell = row.getCell(column);
				Object value = helper.getCellValue(cell);
				if (CellType.NUMERIC == cell.getCellType())
					try {
						sum = ((Double) value).doubleValue();
					} catch (ClassCastException e) {
						sum = ((Integer) value).doubleValue();
					}
				else {
					String sSum = value.toString();
					sum = Double.parseDouble(sSum);
				}
				logger.info("left sum:" + sum);

				// right:
				JSONObject objForm = (JSONObject) tempRight.get(0);
				JSONObject condition = (JSONObject) tempRight.get(1);
				String form_name = objForm.getString("form");
				Form form = fManager.getFormByName(Constants.USER_ID, form_name);
				String table_name = form.getPhsic_name();
				String lineName = condition.getString("sumLine");

				String lineTotal = condition.getString("sumTotal");
				FormField field1 = fManager.getFieldByPhysicName(form_id, lineTotal);
				int column1 = helper.getColumn2Check(wb, field1.getBus_name(), columns);
				cell = row.getCell(column1);
				Object value1 = helper.getCellValue(cell);
				String sTotal = value1.toString();

				String sql = "select * from " + table_name + " where " + lineName + "=" + "'" + sTotal + "' and task_id=" + task_id;
				logger.info("sql: " + sql);
				Session session = null;
				ArrayList al = null;

				try {
					session = HibernateUtil.getSession();

					Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

					al = (ArrayList) query.list();
				} catch (HibernateException e) {
					e.printStackTrace();
				} finally {
					HibernateUtil.closeSession(session);
				}

				sumRight = al.size();
				logger.info("right sume:" + sumRight);
				if (Constants.V_EQUAL.equals(sOP)) {
					if (!(sum == sumRight)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的数据与汇总表关系不满足！");
					}
				} else if (Constants.V_GREATT.equals(sOP)) {
					if (!(sum > sumRight)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的数据与汇总表关系不满足！");
					}
				} else if (Constants.V_GREATTE.equals(sOP)) {
					if (!(sum >= sumRight)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的数据与汇总表关系不满足！");
					}
				} else if (Constants.V_LESST.equals(sOP)) {
					if (!(sum < sumRight)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的数据与汇总表关系不满足！");
					}
				} else if (Constants.V_LESSTE.equals(sOP)) {
					if (!(sum <= sumRight)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的数据与汇总表关系不满足！");
					}
				}

			}

		}
		message.setMessage_info(messageList);

		return message;
	}

	public ArrayList translateRulesSured(JSONObject obj) {
		ArrayList al = new ArrayList();
		JSONArray array = (JSONArray) obj.get("rules");
		JSONArray arrayLeft = new JSONArray();
		JSONArray arrayRight = new JSONArray();
		JSONObject compareOp = null;

		boolean isFound = false;

		for (int i = 0; i < array.length(); i++) {
			JSONObject ob = (JSONObject) array.get(i);
			String type = ob.getString("type");
			if (!isFound) {
				if (Constants.RULE_OPERATOR.equals(type)) {
					String operator = ob.getString(Constants.RULE_OPERATOR);
					if (isCompareOperator(operator)) {
						isFound = true;
						compareOp = ob;
						continue;
					}
				}
				arrayLeft.put(ob);
			} else {
				arrayRight.put(ob);
			}
		}

		al.add(arrayLeft);
		al.add(arrayRight);
		al.add(compareOp);

		return al;
	}

	private boolean isCompareOperator(String operator) {
		boolean result = false;

		if (Constants.V_GREATT.equals(operator) || Constants.V_GREATTE.equals(operator)
				|| Constants.V_LESST.equals(operator) || Constants.V_LESSTE.equals(operator)
				|| Constants.V_EQUAL.equals(operator)) {
			result = true;
		}
		return result;
	}
}
