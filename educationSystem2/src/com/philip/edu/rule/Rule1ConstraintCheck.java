package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Rule1ConstraintCheck {

	private Logger logger = Logger.getLogger(Rule1ConstraintCheck.class);
	private static FormManager fManager = new FormManager();
	private static ExcelHelper helper = new ExcelHelper();

	public MessageInfo getMessage(Workbook wb, JSONObject obj, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		ArrayList rules = null;
		int columns = 0;
		int lines = 0;
		int bit = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";

		columns = helper.getExcelColumns(wb);
		lines = helper.getExcelLines(wb);

		// Precondition:
		JSONArray preArray = (JSONArray) obj.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			String physic_name = preObj.getString("field");
			FormField field = fManager.getFieldByPhysicName(form_id, physic_name);
			conditionColumn = helper.getColumn2Check(wb, field.getBus_name(), columns);
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}

		rules = translateRulesSured(obj);

		Sheet sheet = wb.getSheetAt(0);
		// decide the columns:
		ArrayList arrayLeft = new ArrayList();
		ArrayList arrayRight = new ArrayList();

		JSONArray tempLeft = (JSONArray) rules.get(0);
		JSONArray tempRight = (JSONArray) rules.get(1);

		JSONObject objOP = (JSONObject) rules.get(2);
		String sOP = objOP.getString("operator");

		for (int j = 0; j < tempLeft.length(); j++) {
			LineInfo line = new LineInfo();
			JSONObject obj1 = (JSONObject) tempLeft.get(j);
			String type1 = obj1.getString("type");
			if (Constants.RULE_FORMFIELD.equals(type1)) {
				line.setType(Constants.LINE_TYPE_FIELD_NAME);
				String name1 = obj1.getString("field");
				FormField field1 = fManager.getFieldByPhysicName(form_id, name1);
				int column1 = helper.getColumn2Check(wb, field1.getBus_name(), columns);
				line.setColumn(column1);
				String value1 = obj1.getString("field");
				line.setValue(value1);
				arrayLeft.add(line);
			} else if (Constants.RULE_TEXTBOX.equals(type1)) {
				line.setType(Constants.LINE_TYPE_VALUE);
				String value2 = obj1.getString("value");
				line.setValue(value2);
				arrayLeft.add(line);
			} else if (Constants.RULE_OPERATOR.equals(type1)) {
				line.setType(Constants.LINE_TYPE_OPERATOR);
				String value3 = obj1.getString("operator");
				line.setValue(value3);
				arrayLeft.add(line);
			}
		}

		for (int j = 0; j < tempRight.length(); j++) {
			LineInfo line = new LineInfo();
			JSONObject obj1 = (JSONObject) tempRight.get(j);
			String type1 = obj1.getString("type");
			if (Constants.RULE_FORMFIELD.equals(type1)) {
				line.setType(Constants.LINE_TYPE_FIELD_NAME);
				String name1 = obj1.getString("field");
				FormField field1 = fManager.getFieldByPhysicName(form_id, name1);
				int column1 = helper.getColumn2Check(wb, field1.getBus_name(), columns);
				line.setColumn(column1);
				String value1 = obj1.getString("field");
				line.setValue(value1);
				arrayRight.add(line);
			} else if (Constants.RULE_TEXTBOX.equals(type1)) {
				line.setType(Constants.LINE_TYPE_VALUE);
				String value2 = obj1.getString("value");
				line.setValue(value2);
				arrayRight.add(line);
			} else if (Constants.RULE_OPERATOR.equals(type1)) {
				line.setType(Constants.LINE_TYPE_OPERATOR);
				String value3 = obj1.getString("operator");
				line.setValue(value3);
				arrayRight.add(line);
			}
		}

		// cycle everyLine:
		for (int i = 1; i < lines; i++) {

			Row row = sheet.getRow(i);
			// 0。precondition:
			if (isCondition) {
				Cell conCell = row.getCell(conditionColumn);
				if (conCell == null)
					continue;
				String testValue = "";
				Object value = helper.getCellValue(conCell);
				testValue = value.toString();

				// operator:
				if (Constants.V_EQUAL.equals(conOperator)) {
					// =
					if (!conValue.equals(testValue))
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
			int leftResult = 0;
			int rightResult = 0;
			String leftString = "";
			String rightString = "";
			LineInfo line = null;
			boolean isNum = true;
			// 1、 get left result:
			String currentOperator = Constants.V_ADD;
			for (int k = 0; k < arrayLeft.size(); k++) {
				line = (LineInfo) arrayLeft.get(k);
				try {
					switch (line.getType()) {
					case Constants.LINE_TYPE_FIELD_NAME:
						Cell cell = row.getCell(line.getColumn());
						if (cell == null)
							continue;
						String tempValue = "";
						Object value = helper.getCellValue(cell);
						tempValue = value.toString();
						if (arrayLeft.size() == 1) {
							leftString = tempValue;
							break;
						}

						int iValue = Integer.parseInt(tempValue);
						if (Constants.V_ADD.equals(currentOperator)) {
							leftResult += iValue;
						} else if (Constants.V_MINUS.equals(currentOperator)) {
							leftResult -= iValue;
						} else if (Constants.V_MUTIPL.equals(currentOperator)) {
							leftResult *= iValue;
						} else if (Constants.V_DIVIDE.equals(currentOperator)) {
							leftResult /= iValue;
						}
						break;
					case Constants.LINE_TYPE_OPERATOR:
						currentOperator = line.getValue();
						break;
					case Constants.LINE_TYPE_VALUE:
						String tempValue2 = line.getValue();
						if (arrayLeft.size() == 1) {
							leftString = tempValue2;
							break;
						}

						int iValue2 = Integer.parseInt(tempValue2);
						if (Constants.V_ADD.equals(currentOperator)) {
							leftResult += iValue2;
						} else if (Constants.V_MINUS.equals(currentOperator)) {
							leftResult -= iValue2;
						} else if (Constants.V_MUTIPL.equals(currentOperator)) {
							leftResult *= iValue2;
						} else if (Constants.V_DIVIDE.equals(currentOperator)) {
							leftResult /= iValue2;
						}
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的内容不是数字！");
					isNum = false;
					break;
				}
			}

			// 2、get right result:
			currentOperator = Constants.V_ADD;
			for (int k = 0; k < arrayRight.size(); k++) {
				line = (LineInfo) arrayRight.get(k);
				try {
					switch (line.getType()) {
					case Constants.LINE_TYPE_FIELD_NAME:
						Cell cell = row.getCell(line.getColumn());
						if (cell == null)
							continue;
						String tempValue = "";
						Object value = helper.getCellValue(cell);
						tempValue = value.toString();
						if (arrayRight.size() == 1) {
							rightString = tempValue;
							break;
						}

						int iValue = Integer.parseInt(tempValue);
						if (Constants.V_ADD.equals(currentOperator)) {
							rightResult += iValue;
						} else if (Constants.V_MINUS.equals(currentOperator)) {
							rightResult -= iValue;
						} else if (Constants.V_MUTIPL.equals(currentOperator)) {
							rightResult *= iValue;
						} else if (Constants.V_DIVIDE.equals(currentOperator)) {
							rightResult /= iValue;
						}
						break;
					case Constants.LINE_TYPE_OPERATOR:
						currentOperator = line.getValue();
						break;
					case Constants.LINE_TYPE_VALUE:
						String tempValue2 = line.getValue();
						if (arrayRight.size() == 1) {
							rightString = tempValue2;
							break;
						}
						int iValue2 = Integer.parseInt(tempValue2);

						
						if (Constants.V_ADD.equals(currentOperator)) {
							rightResult += iValue2;
						} else if (Constants.V_MINUS.equals(currentOperator)) {
							rightResult -= iValue2;
						} else if (Constants.V_MUTIPL.equals(currentOperator)) {
							rightResult *= iValue2;
						} else if (Constants.V_DIVIDE.equals(currentOperator)) {
							rightResult /= iValue2;
						}
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的内容不是数字！");
					isNum = false;
					break;
				}
			}
			// 3、compare:
			if (arrayLeft.size() == 1 && arrayRight.size() == 1) {
				if (Constants.V_EQUAL.equals(sOP)) {
					if (!leftString.equals(rightString)) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (i + 1) + "行的等式不成立！");
					}
				}
			}
			if (!isNum)
				continue;
			if (Constants.V_EQUAL.equals(sOP)) {
				if (!(leftResult == rightResult)) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的等式不成立！");
				}
			} else if (Constants.V_GREATT.equals(sOP)) {
				if (!(leftResult > rightResult)) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的不等式不成立！");
				}
			} else if (Constants.V_GREATTE.equals(sOP)) {
				if (!(leftResult >= rightResult)) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的不等式不成立！");
				}
			} else if (Constants.V_LESST.equals(sOP)) {
				if (!(leftResult < rightResult)) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的不等式不成立！");
				}
			} else if (Constants.V_LESSTE.equals(sOP)) {
				if (!(leftResult <= rightResult)) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (i + 1) + "行的不等式不成立！");
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
