package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Rule3ExistsCheck {
	private Logger logger = Logger.getLogger(Rule1ConstraintCheck.class);
	private static FormManager fManager = new FormManager();
	private static ExcelHelper helper = new ExcelHelper();

	public MessageInfo getMessage(Workbook wb, JSONObject obj, int form_id) {
		MessageInfo message = null;
		int columns = 0;
		int lines = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = null;
		String conValue = null;
		ArrayList messageList = new ArrayList();

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

		Sheet sheet = wb.getSheetAt(0);
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
		}

		return message;
	}
}
