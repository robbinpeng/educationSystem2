package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
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

public class Rule3ExistsCheck {
	private Logger logger = Logger.getLogger(Rule1ConstraintCheck.class);
	private static FormManager fManager = new FormManager();
	private static ExcelHelper helper = new ExcelHelper();

	public MessageInfo getMessage(String[][] data, JSONObject obj, int form_id, int task_id) {
		MessageInfo message = new MessageInfo();
		int columns = 0;
		int lines = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = null;
		String conValue = null;
		ArrayList messageList = new ArrayList();

		columns = data[0].length;
		lines = data.length;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		// Precondition:
		JSONArray preArray = (JSONArray) obj.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			String physic_name = preObj.getString("field");
			FormField field = fManager.getFieldByPhysicName(form_id, physic_name);
			conditionColumn = helper.getColumn2Check(data, field.getBus_name());
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}

		// how many fields:
		ArrayList checkList = null;
		try {
			checkList = this.translateRules(obj, form_id, isCondition, data);
		} catch (BadRulesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info(e1);
			messageList.add("规则的设置有问题！");
			message.setMessage_type(Constants.RULECHECK_MESSAGE_NOT_IMPLEMENT);
		}

		//SXSSFSheet sheet = wb.getSheetAt(0);
		// cycle everyLine:
		for (int i = 1; i < lines; i++) {

			//SXSSFRow row = sheet.getRow(i);
			// 0。precondition:
			if (isCondition) {
				//SXSSFCell conCell = row.getCell(conditionColumn);
				String cValue = data[i][conditionColumn];
				if (cValue == null)
					continue;
				String testValue = "";
				//Object value = helper.getCellValue(conCell);
				testValue = cValue;

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
						messageList.add("第" + (i + 1) + "行的条件不是数字！");
					}

				}
			}

			// 2. do the rule check:
			Integer iFields = (Integer) checkList.get(0);
			ArrayList al = (ArrayList) checkList.get(1);
			Session session = null;

			if (iFields.intValue() == 1) {
				String leftValue = "";
				boolean isExsits = false;
				// one field:
				for (int j = 0; j < al.size(); j++) {
					SearchInfo info = (SearchInfo) al.get(j);

					//SXSSFCell cell = row.getCell(info.getField1Column());
					String cellValue = data[i][info.getField1Column()];
					//Object o = helper.getCellValue(cell);
					leftValue = cellValue;
					Query query = null;

					try {
						session = HibernateUtil.getSession();
						String sql = "";
						if (!(info.getAndName() == null || info.getAndName().equals(""))) {
							sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
									+ leftValue + "' and " + info.getAndName() + "='" + info.getAndValue()
									+ "' and task_id=" + task_id;
						} else {
							sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
									+ leftValue + "' and task_id=" + task_id;
						}
						logger.info(sql);

						query = session.createSQLQuery(sql);
						ArrayList resultL = (ArrayList) query.list();

						if (resultL.size() != 0) {
							isExsits = true;
							break;
						} else {
							isExsits = false;
							continue;
						}

					} catch (HibernateException e) {
						e.printStackTrace();
					} finally {
						HibernateUtil.closeSession(session);
					}

				}
				if (!isExsits) {
					messageList.add("第" + (i + 1) + "行的记录在关联表中不存在！");
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				}
			} else if (iFields.intValue() == 2) {
				String leftValue1 = "";
				String leftValue2 = "";
				boolean isExsits = false;
				// one field:
				for (int j = 0; j < al.size(); j++) {
					SearchInfo info = (SearchInfo) al.get(j);

					//SXSSFCell cell1 = row.getCell(info.getField1Column());
					//Object o1 = helper.getCellValue(cell1);
					String cellValue1 = data[i][info.getField1Column()];
					leftValue1 = cellValue1;
					//SXSSFCell cell2 = row.getCell(info.getField2Column());
					//Object o2 = helper.getCellValue(cell2);
					String cellValue2 = data[i][info.getField2Column()];
					leftValue2 = cellValue2;

					try {
						session = HibernateUtil.getSession();
						String sql = "";
						if (!(info.getAndName().equals("") || info.getAndValue().equals(""))) {
							sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
									+ leftValue1 + "' and " + info.getField2Name() + "='" + leftValue2 + "' and "
									+ info.getAndName() + "='" + info.getAndValue() + "' and task_id=" + task_id;
						} else {
							sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
									+ leftValue1 + "' and " + info.getField2Name() + "='" + leftValue2
									+ "' and task_id=" + task_id;
						}

						logger.info(sql);

						Query query = session.createSQLQuery(sql);
						ArrayList resultL = (ArrayList) query.list();

						if (resultL.size() != 0) {
							isExsits = true;
							break;
						} else {
							isExsits = false;
							continue;
						}

					} catch (HibernateException e) {
						e.printStackTrace();
					} finally {
						HibernateUtil.closeSession(session);
					}

				}
				if (!isExsits) {
					messageList.add("第" + (i + 1) + "行的记录在关联表中不存在！");
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				}
			}
		}
		message.setMessage_info(messageList);

		return message;
	}

	public MessageInfo getMessageSingleLine(ArrayList record, JSONObject obj, int form_id, int task_id) {
		MessageInfo message = new MessageInfo();
		int columns = 0;
		int lines = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conName = null;
		String conOperator = null;
		String conValue = null;
		ArrayList messageList = new ArrayList();

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		ArrayList fields = fManager.getFormFields(form_id);
		ArrayList displayFields = new ArrayList();
		for (int i = 0; i < fields.size(); i++) {
			FormField fieldTemp = (FormField) fields.get(i);
			if (fieldTemp.getIs_hidden() == 'N')
				displayFields.add(fieldTemp);
		}

		// Precondition:
		JSONArray preArray = (JSONArray) obj.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			String physic_name = preObj.getString("field");
			FormField field = fManager.getFieldByPhysicName(form_id, physic_name);
			conName = physic_name;

			for (int i = 0; i < displayFields.size(); i++) {
				FormField fieldTemp = (FormField) displayFields.get(i);
				if (fieldTemp.getPhysic_name().equals(physic_name)) {
					conditionColumn = i;
					break;
				}
			}
			// conditionColumn = helper.getColumn2Check(wb, field.getBus_name(),
			// columns);
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}

		// how many fields:
		ArrayList checkList = null;
		try {
			checkList = this.translateRulesSingleLine(obj, form_id, isCondition);
		} catch (BadRulesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info(e1);
			messageList.add("规则的设置有问题！");
			message.setMessage_type(Constants.RULECHECK_MESSAGE_NOT_IMPLEMENT);
		}

		// 0。precondition:
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
					messageList.add("规则的条件不是数字，无法进行判定！");
				}

			}

		}

		// 2. do the rule check:
		Integer iFields = (Integer) checkList.get(0);
		ArrayList al = (ArrayList) checkList.get(1);
		Session session = null;

		if (iFields.intValue() == 1) {
			String leftValue = "";
			boolean isExsits = false;
			// one field:
			for (int j = 0; j < al.size(); j++) {
				SearchInfo info = (SearchInfo) al.get(j);

				DataInfo data_1 = null;
				for (int k = 0; k < record.size(); k++) {
					data_1 = (DataInfo) record.get(k);
					if (data_1.getKey().equals(info.getPhysic_field())) {
						break;
					}
				}

				leftValue = data_1.getValue();
				Query query = null;

				try {
					session = HibernateUtil.getSession();
					String sql = "";
					if (!(info.getAndName() == null || info.getAndName().equals(""))) {
						sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
								+ leftValue + "' and " + info.getAndName() + "='" + info.getAndValue()
								+ "' and task_id=" + task_id;
					} else {
						sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
								+ leftValue + "' and task_id=" + task_id;
					}
					logger.info(sql);

					query = session.createSQLQuery(sql);
					ArrayList resultL = (ArrayList) query.list();

					if (resultL.size() != 0) {
						isExsits = true;
						break;
					} else {
						isExsits = false;
						continue;
					}

				} catch (HibernateException e) {
					e.printStackTrace();
				} finally {
					HibernateUtil.closeSession(session);
				}

			}
			if (!isExsits) {
				messageList.add("该记录在关联表中不存在！");
				message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
			}
		} else if (iFields.intValue() == 2) {
			String leftValue1 = "";
			String leftValue2 = "";
			boolean isExsits = false;
			// one field:
			for (int j = 0; j < al.size(); j++) {
				SearchInfo info = (SearchInfo) al.get(j);

				DataInfo data_1 = null;
				for (int k = 0; k < record.size(); k++) {
					data_1 = (DataInfo) record.get(k);
					//logger.info("data_1:" + data_1.getKey() + "info.getField1Name:" +);
					if (data_1.getKey().equals(info.getPhysic_field())) {
						break;
					}
				}
				leftValue1 = data_1.getValue();

				DataInfo data_2 = null;
				for (int k = 0; k < record.size(); k++) {
					data_2 = (DataInfo) record.get(k);
					if (data_2.getKey().equals(info.getPhysic_field2())) {
						break;
					}
				}
				leftValue2 = data_2.getValue();

				try {
					session = HibernateUtil.getSession();
					String sql = "";
					if (!(info.getAndName().equals("") || info.getAndValue().equals(""))) {
						sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
								+ leftValue1 + "' and " + info.getField2Name() + "='" + leftValue2 + "' and "
								+ info.getAndName() + "='" + info.getAndValue() + "' and task_id=" + task_id;
					} else {
						sql = "select * from " + info.getForm_physic() + " where " + info.getField1Name() + "='"
								+ leftValue1 + "' and " + info.getField2Name() + "='" + leftValue2 + "' and task_id="
								+ task_id;
					}

					logger.info(sql);

					Query query = session.createSQLQuery(sql);
					ArrayList resultL = (ArrayList) query.list();

					if (resultL.size() != 0) {
						isExsits = true;
						break;
					} else {
						isExsits = false;
						continue;
					}

				} catch (HibernateException e) {
					e.printStackTrace();
				} finally {
					HibernateUtil.closeSession(session);
				}

			}
			if (!isExsits) {
				messageList.add("该记录在关联表中不存在！");
				message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
			}
		}

		message.setMessage_info(messageList);

		return message;
	}

	public ArrayList translateRulesSingleLine(JSONObject obj, int form_id, boolean isCondition)
			throws BadRulesException {
		ArrayList al = new ArrayList();
		int start = 0;
		if (isCondition)
			start = 1;
		else
			start = 0;

		JSONArray array = (JSONArray) obj.get("rules");
		int fieldNum = 0;

		JSONObject field1 = (JSONObject) array.get(start);
		String type = field1.getString("type");
		if (!type.equals(Constants.RULE_FORMFIELD))
			throw new BadRulesException("第一个字段不是表字段");
		fieldNum++;

		JSONObject field2 = (JSONObject) array.get(start + 1);
		type = field2.getString("type");
		if (type.equals(Constants.RULE_FORMFIELD))
			fieldNum++;

		al.add(fieldNum);

		FormField fieldOne = fManager.getFieldByPhysicName(form_id, field1.getString("field"));
		// int column1 = helper.getColumn2Check(wb, fieldOne.getBus_name(),
		// columnTotal);

		ArrayList arrayR = new ArrayList();
		JSONArray arrayRight = new JSONArray();
		if (fieldNum == 1) {
			// one field:
			SearchInfo info1 = new SearchInfo();
			info1.setPhysic_field(fieldOne.getPhysic_name());

			for (int i = start + 1; i < array.length(); i++) {
				JSONObject ob1 = (JSONObject) array.get(i);
				String type1 = ob1.getString("type");
				if (Constants.RULE_RELATE_FORM.equals(type1)) {
					String form1 = ob1.getString("relateForm");
					Form relateForm = fManager.getFormByName(Constants.USER_ID, form1);
					info1.setForm_physic(relateForm.getPhsic_name());
					String field11 = ob1.getString("relateField");
					info1.setField1Name(field11);
				} else if (Constants.RULE_OR.equals(type1)) {
					arrayR.add(info1);
					info1 = new SearchInfo();
					info1.setPhysic_field(fieldOne.getPhysic_name());
				} else if (Constants.RULE_FIELD_EQUAL.equals(type1)) {
					String andField = ob1.getString("value");
					info1.setAndName(andField);
					String andValue = ob1.getString("field");
					info1.setAndValue(andValue);
				}
			}
			arrayR.add(info1);
		} else if (fieldNum == 2) {
			// two field:
			SearchInfo info2 = new SearchInfo();
			info2.setPhysic_field(fieldOne.getPhysic_name());

			FormField fieldTwo = fManager.getFieldByPhysicName(form_id, field2.getString("field"));
			// int column2 = helper.getColumn2Check(wb, fieldTwo.getBus_name(),
			// columnTotal);
			info2.setPhysic_field2(fieldTwo.getPhysic_name());

			for (int i = start + 2; i < array.length(); i++) {
				JSONObject ob2 = (JSONObject) array.get(i);
				String type2 = ob2.getString("type");
				if (Constants.RULE_RELATE_FORM_TWO.equals(type2)) {
					String form2 = ob2.getString("relateForm");
					Form relateForm = fManager.getFormByName(Constants.USER_ID, form2);
					info2.setForm_physic(relateForm.getPhsic_name());
					String field21 = ob2.getString("relateField1");
					info2.setField1Name(field21);
					String field22 = ob2.getString("relateField2");
					info2.setField2Name(field22);
				} else if (Constants.RULE_OR.equals(type2)) {
					arrayR.add(info2);
					info2 = new SearchInfo();
					info2.setPhysic_field(fieldOne.getPhysic_name());
					info2.setPhysic_field2(fieldTwo.getPhysic_name());
				} else if (Constants.RULE_FIELD_EQUAL.equals(type2)) {
					String andField = ob2.getString("value");
					info2.setAndName(andField);
					String andValue = ob2.getString("field");
					info2.setAndValue(andValue);
				}
			}
			arrayR.add(info2);
		}
		al.add(arrayR);

		return al;
	}

	public ArrayList translateRules(JSONObject obj, int form_id, boolean isCondition, String[][] data)
			throws BadRulesException {
		ArrayList al = new ArrayList();
		int start = 0;
		if (isCondition)
			start = 1;
		else
			start = 0;

		JSONArray array = (JSONArray) obj.get("rules");
		int fieldNum = 0;

		int columnTotal = data[0].length;

		JSONObject field1 = (JSONObject) array.get(start);
		String type = field1.getString("type");
		if (!type.equals(Constants.RULE_FORMFIELD))
			throw new BadRulesException("第一个字段不是表字段");
		fieldNum++;

		JSONObject field2 = (JSONObject) array.get(start + 1);
		type = field2.getString("type");
		if (type.equals(Constants.RULE_FORMFIELD))
			fieldNum++;

		al.add(fieldNum);

		FormField fieldOne = fManager.getFieldByPhysicName(form_id, field1.getString("field"));
		int column1 = helper.getColumn2Check(data, fieldOne.getBus_name());

		ArrayList arrayR = new ArrayList();
		JSONArray arrayRight = new JSONArray();
		if (fieldNum == 1) {
			// one field:
			SearchInfo info1 = new SearchInfo();
			info1.setField1Column(column1);

			for (int i = start + 1; i < array.length(); i++) {
				JSONObject ob1 = (JSONObject) array.get(i);
				String type1 = ob1.getString("type");
				if (Constants.RULE_RELATE_FORM.equals(type1)) {
					String form1 = ob1.getString("relateForm");
					Form relateForm = fManager.getFormByName(Constants.USER_ID, form1);
					info1.setForm_physic(relateForm.getPhsic_name());
					String field11 = ob1.getString("relateField");
					info1.setField1Name(field11);
				} else if (Constants.RULE_OR.equals(type1)) {
					arrayR.add(info1);
					info1 = new SearchInfo();
					info1.setField1Column(column1);
				} else if (Constants.RULE_FIELD_EQUAL.equals(type1)) {
					String andField = ob1.getString("value");
					info1.setAndName(andField);
					String andValue = ob1.getString("field");
					info1.setAndValue(andValue);
				}
			}
			arrayR.add(info1);
		} else if (fieldNum == 2) {
			// two field:
			SearchInfo info2 = new SearchInfo();
			info2.setField1Column(column1);

			FormField fieldTwo = fManager.getFieldByPhysicName(form_id, field2.getString("field"));
			int column2 = helper.getColumn2Check(data, fieldTwo.getBus_name());
			info2.setField2Column(column2);

			for (int i = start + 2; i < array.length(); i++) {
				JSONObject ob2 = (JSONObject) array.get(i);
				String type2 = ob2.getString("type");
				if (Constants.RULE_RELATE_FORM_TWO.equals(type2)) {
					String form2 = ob2.getString("relateForm");
					Form relateForm = fManager.getFormByName(Constants.USER_ID, form2);
					info2.setForm_physic(relateForm.getPhsic_name());
					String field21 = ob2.getString("relateField1");
					info2.setField1Name(field21);
					String field22 = ob2.getString("relateField2");
					info2.setField2Name(field22);
				} else if (Constants.RULE_OR.equals(type2)) {
					arrayR.add(info2);
					info2 = new SearchInfo();
					info2.setField1Column(column1);
					info2.setField2Column(column2);
				} else if (Constants.RULE_FIELD_EQUAL.equals(type2)) {
					String andField = ob2.getString("value");
					info2.setAndName(andField);
					String andValue = ob2.getString("field");
					info2.setAndValue(andValue);
				}
			}
			arrayR.add(info2);
		}
		al.add(arrayR);

		return al;
	}
}
