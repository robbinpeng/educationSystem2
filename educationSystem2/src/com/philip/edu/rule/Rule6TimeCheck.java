package com.philip.edu.rule;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

import org.apache.log4j.Logger;

public class Rule6TimeCheck {

	private static Logger logger = Logger.getLogger(Rule6TimeCheck.class);

	private static ExcelHelper helper = new ExcelHelper();
	private static FormManager fManager = new FormManager();

	private ArrayList cutRulesByOr(JSONObject obj) {
		ArrayList compareSenten = new ArrayList();
		JSONArray line = new JSONArray();
		JSONObject ob = null;

		JSONArray array = (JSONArray) obj.get("rules");
		for (int i = 0; i < array.length(); i++) {
			ob = (JSONObject) array.get(i);
			String type = ob.getString("type");
			if (Constants.RULE_OR.equals(type)) {
				compareSenten.add(line);
				line = new JSONArray();
			} else {
				line.put(ob);
			}
		}
		compareSenten.add(line);

		return compareSenten;
	}

	public MessageInfo getMessage(Workbook wb, JSONObject object, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		ArrayList ruleList = null;
		int columns = 0;
		int lines = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		columns = helper.getExcelColumns(wb);
		lines = helper.getExcelLines(wb, form_id, columns);

		// Precondition:
		JSONArray preArray = (JSONArray) object.get("rules");
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

		ruleList = translateRulesSured(object, form_id, wb, columns);

		String format = (String) ruleList.get(0);

		Sheet sheet = wb.getSheetAt(0);

		// cycle everyLine:
		for (int i = 1; i < lines; i++) {

			Row row = sheet.getRow(i);
			// 0.precondition:
			if (isCondition) {
				Cell conCell = row.getCell(conditionColumn);
				String testValue = "";
				if (conCell != null) {
					Object value = helper.getCellValue(conCell);
					testValue = value.toString();
				}

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
						messageList.add("第" + (i + 1) + "行的条件不是数字格式！");
					}

				}
			}
			// 1. check every sentence:
			boolean isDate = true;

			boolean isRight = false;
			for (int j = 1; j < ruleList.size(); j++) {
				ArrayList rule = (ArrayList) ruleList.get(j);
				JSONArray arrayLeft = (JSONArray) rule.get(0);
				JSONArray arrayRight = (JSONArray) rule.get(1);
				JSONObject objOper = (JSONObject) rule.get(2);
				LocalDate leftLDate = LocalDate.now(ZoneId.systemDefault());
				LocalDate rightLDate = LocalDate.now(ZoneId.systemDefault());

				try {
					// left:
					for (int jL = 0; jL < arrayLeft.length(); jL++) {
						JSONObject objLeft = (JSONObject) arrayLeft.get(jL);
						String typeL = objLeft.getString("type");
						if (Constants.RULE_FORMFIELD.equals(typeL)) {
							int column = objLeft.getInt("column");
							Cell cell = row.getCell(column);
							if (cell == null)
								continue;
							String sDate = "";
							Object value = helper.getCellValue(cell);
							CellType cellType = cell.getCellTypeEnum();
							if (cellType == CellType.STRING) {
								sDate = value.toString();
							} else if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date temp = cell.getDateCellValue();
								sDate = sdf.format(cell.getDateCellValue());
							} else if (cellType == CellType.NUMERIC) {
								sDate = ((Integer) value).toString();
							}

							int year = 0;
							int month = 0;
							int day = 0;

							if ("".equals(sDate))
								throw new TimeErrorException("时间为空");
							switch (format.length()) {
							case 4: // "yyyy"
								year = Integer.parseInt(sDate.substring(0, 4));
								leftLDate = LocalDate.of(year, 1, 1);
								break;
							case 7: // "yyyy-mm"
								year = Integer.parseInt(sDate.substring(0, 4));
								logger.info("sDate:" + sDate);
								if (sDate.length() >= 7)
									month = Integer.parseInt(sDate.substring(5, 7));
								else
									month = 1;
								leftLDate = LocalDate.of(year, month, 1);
								break;
							case 10: // "yyyy-mm-dd"
								year = Integer.parseInt(sDate.substring(0, 4));
								logger.info("sDate:" + sDate);
								String sMonth = "";
								if (sDate.length() >= 7)
									sMonth = sDate.substring(5, 7);
								else
									sMonth = "1";
								logger.info("month:" + sMonth);
								month = Integer.parseInt(sMonth);
								if (sDate.length() >= 10)
									day = Integer.parseInt(sDate.substring(8, 10));
								else
									day = 1;
								leftLDate = LocalDate.of(year, month, day);
								break;
							}
						} else if (Constants.RULE_TEXTBOX_DATE.equals(typeL)) {
							String sDate = objLeft.getString("value");
							if ("".equals(sDate))
								throw new TimeErrorException("时间为空");

							int year = 0;
							int month = 0;
							int day = 0;
							switch (format.length()) {
							case 4: // "yyyy"
								year = Integer.parseInt(sDate.substring(0, 4));
								leftLDate = LocalDate.of(year, 1, 1);
								break;
							case 7: // "yyyy-mm"
								year = Integer.parseInt(sDate.substring(0, 4));
								month = Integer.parseInt(sDate.substring(5, 7));
								leftLDate = LocalDate.of(year, month, 1);
								break;
							case 10: // "yyyy-mm-dd"
								year = Integer.parseInt(sDate.substring(0, 4));
								month = Integer.parseInt(sDate.substring(5, 7));
								day = Integer.parseInt(sDate.substring(8, 10));
								leftLDate = LocalDate.of(year, month, day);
								break;
							}
						} else if (Constants.RULE_YEAR_ADD.equals(typeL)) {
							String sDate = objLeft.getString("value");
							int yearAdd = Integer.parseInt(sDate);
							leftLDate = LocalDate.now().plusYears(yearAdd - 1);
							leftLDate = leftLDate.withMonth(1);
							leftLDate = leftLDate.withDayOfMonth(1);
						} else if (Constants.RULE_MONTH.equals(typeL)) {
							String sDate = objLeft.getString("value");
							int month = Integer.parseInt(sDate);
							leftLDate = leftLDate.withMonth(month);
							leftLDate = leftLDate.withDayOfMonth(1);
						} else if (Constants.RULE_DAY.equals(typeL)) {
							String sDate = objLeft.getString("value");
							int day = Integer.parseInt(sDate);
							leftLDate = leftLDate.withDayOfMonth(day);
						}
					}
					// right:
					int year = 0;
					int month = 0;
					int day = 0;
					for (int jR = 0; jR < arrayRight.length(); jR++) {
						JSONObject objRight = (JSONObject) arrayRight.get(jR);
						String typeR = objRight.getString("type");

						if (Constants.RULE_FORMFIELD.equals(typeR)) {
							int column = objRight.getInt("column");
							Cell cell = row.getCell(column);
							if (cell == null)
								continue;
							String sDate = "";
							Object value = helper.getCellValue(cell);
							CellType cellType = cell.getCellTypeEnum();
							if (cellType == CellType.STRING) {
								sDate = value.toString();
							} else if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								sDate = sdf.format(value);
							} else if (cellType == CellType.NUMERIC) {
								sDate = ((Integer) value).toString();
							}

							if ("".equals(sDate))
								throw new TimeErrorException("时间为空");
							;
							switch (format.length()) {
							case 4: // "yyyy"
								year = Integer.parseInt(sDate.substring(0, 4));
								rightLDate = LocalDate.of(year, 1, 1);
								break;
							case 7: // "yyyy-mm"
								year = Integer.parseInt(sDate.substring(0, 4));
								if (sDate.length() >= 7)
									month = Integer.parseInt(sDate.substring(5, 7));
								else
									month = 1;
								rightLDate = LocalDate.of(year, month, 1);
								break;
							case 10: // "yyyy-mm-dd"
								year = Integer.parseInt(sDate.substring(0, 4));
								if (sDate.length() >= 7)
									month = Integer.parseInt(sDate.substring(5, 7));
								else
									month = 1;
								if (sDate.length() >= 10)
									day = Integer.parseInt(sDate.substring(8, 10));
								else
									day = 1;
								rightLDate = LocalDate.of(year, month, day);
								break;
							}
						} else if (Constants.RULE_TEXTBOX_DATE.equals(typeR)) {
							String sDate = objRight.getString("value");
							if ("".equals(sDate))
								throw new TimeErrorException("时间为空");
							;

							switch (format.length()) {
							case 4: // "yyyy"
								year = Integer.parseInt(sDate.substring(0, 4));
								rightLDate = LocalDate.of(year, 1, 1);
								break;
							case 7: // "yyyy-mm"
								year = Integer.parseInt(sDate.substring(0, 4));
								month = Integer.parseInt(sDate.substring(5, 7));
								rightLDate = LocalDate.of(year, month, 1);
								break;
							case 10: // "yyyy-mm-dd"
								year = Integer.parseInt(sDate.substring(0, 4));
								month = Integer.parseInt(sDate.substring(5, 7));
								day = Integer.parseInt(sDate.substring(8, 10));
								rightLDate = LocalDate.of(year, month, day);
								break;
							}
						} else if (Constants.RULE_YEAR_ADD.equals(typeR)) {
							String sDate = objRight.getString("value");
							int yearAdd = Integer.parseInt(sDate);
							rightLDate = LocalDate.now().plusYears(yearAdd - 1);
							rightLDate = rightLDate.withMonth(1);
							rightLDate = rightLDate.withDayOfMonth(1);
						} else if (Constants.RULE_MONTH.equals(typeR)) {
							String sDate = objRight.getString("value");
							month = Integer.parseInt(sDate);
							rightLDate = rightLDate.withMonth(month);
							rightLDate = rightLDate.withDayOfMonth(1);
						} else if (Constants.RULE_DAY.equals(typeR)) {
							String sDate = objRight.getString("value");
							day = Integer.parseInt(sDate);
							rightLDate = rightLDate.withDayOfMonth(day);
						}
					}

				} catch (NumberFormatException e) {
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i + 1) + "行的日期格式不正确！");
					isDate = false;
					isRight = false;
					break;
				} catch (TimeErrorException e) {
					isDate = false;
					isRight = true;
				}
				// operator:

				if (!isDate)
					continue;

				String sOP = objOper.getString("operator");
				if (Constants.V_EQUAL.equals(sOP)) {
					if (!(leftLDate.isEqual(rightLDate))) {
						logger.info("left:" + leftLDate + ", right:" + rightLDate);
						// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						// messageList.add("第" + (i+1) + "行的等式不成立！");
						isRight = false;
					} else {
						isRight = true;
						break;
					}
				} else if (Constants.V_GREATT.equals(sOP)) {
					if (!(leftLDate.isAfter(rightLDate))) {
						// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						// messageList.add("第" + (i+1) + "行的不等式不成立！");
						isRight = false;
					} else {
						isRight = true;
						break;
					}
				} else if (Constants.V_GREATTE.equals(sOP)) {
					if (!(leftLDate.isAfter(rightLDate)) || (leftLDate.isEqual(rightLDate))) {
						// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						// messageList.add("第" + (i+1) + "行的不等式不成立！");
						isRight = false;
					} else {
						isRight = true;
						break;
					}
				} else if (Constants.V_LESST.equals(sOP)) {
					logger.info("left Date:" + leftLDate.toString());
					logger.info("right date:" + rightLDate.toString());
					if (!(leftLDate.isBefore(rightLDate))) {
						// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						// messageList.add("第" + (i+1) + "行的不等式不成立！");
						isRight = false;
					} else {
						isRight = true;
						break;
					}
				} else if (Constants.V_LESSTE.equals(sOP)) {
					logger.info("leftLDate:" + leftLDate.toString() + ",rightLDate:" + rightLDate.toString());
					if (!(leftLDate.isBefore(rightLDate) || (leftLDate.isEqual(rightLDate)))) {
						// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						// messageList.add("第" + (i+1) + "行的不等式不成立！");
						isRight = false;
					} else {
						isRight = true;
						break;
					}
				}
			}
			if (!isRight) {
				message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				messageList.add("第" + (i + 1) + "行的时间条件不满足！");
			}
		}
		message.setMessage_info(messageList);

		return message;
	}

	public MessageInfo getMessageSingleLine(ArrayList record, JSONObject object, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		ArrayList ruleList = null;
		int columns = 0;
		int lines = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";
		String physic_name = "";

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		// columns = helper.getExcelColumns(wb);
		// lines = helper.getExcelLines(wb, form_id, columns);

		ArrayList fields = fManager.getFormFields(form_id);
		ArrayList displayFields = new ArrayList();

		for (int k = 0; k < fields.size(); k++) {
			FormField fieldTemp = (FormField) fields.get(k);
			if (fieldTemp.getIs_hidden() == 'N')
				displayFields.add(fieldTemp);
		}

		// Precondition:
		JSONArray preArray = (JSONArray) object.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if (Constants.RULE_CONDITION.equals(type)) {
			isCondition = true;
			physic_name = preObj.getString("field");
			FormField field = fManager.getFieldByPhysicName(form_id, physic_name);
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

		ruleList = translateRulesSuredSingle(object, form_id, columns, displayFields);

		String format = (String) ruleList.get(0);

		// 0.precondition:
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
					messageList.add("该记录前置条件不是数字格式！");
					message.setMessage_info(messageList);
					return message;
				}

			}
		}
		// 1. check every sentence:
		boolean isDate = true;

		boolean isRight = false;
		for (int j = 1; j < ruleList.size(); j++) {
			ArrayList rule = (ArrayList) ruleList.get(j);
			JSONArray arrayLeft = (JSONArray) rule.get(0);
			JSONArray arrayRight = (JSONArray) rule.get(1);
			JSONObject objOper = (JSONObject) rule.get(2);
			LocalDate leftLDate = LocalDate.now(ZoneId.systemDefault());
			LocalDate rightLDate = LocalDate.now(ZoneId.systemDefault());

			try {
				// left:
				for (int jL = 0; jL < arrayLeft.length(); jL++) {
					JSONObject objLeft = (JSONObject) arrayLeft.get(jL);
					String typeL = objLeft.getString("type");
					if (Constants.RULE_FORMFIELD.equals(typeL)) {
						int column = objLeft.getInt("column");
						DataInfo data = (DataInfo) record.get(column);
						if (data == null) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("该行记录为空！");
							message.setMessage_info(messageList);
							return message;
						}

						String sDate = data.getValue();

						int year = 0;
						int month = 0;
						int day = 0;

						if ("".equals(sDate))
							throw new TimeErrorException("时间为空");
						switch (format.length()) {
						case 4: // "yyyy"
							year = Integer.parseInt(sDate.substring(0, 4));
							leftLDate = LocalDate.of(year, 1, 1);
							break;
						case 7: // "yyyy-mm"
							year = Integer.parseInt(sDate.substring(0, 4));
							logger.info("sDate:" + sDate);
							if (sDate.length() >= 7)
								month = Integer.parseInt(sDate.substring(5, 7));
							else
								month = 1;
							leftLDate = LocalDate.of(year, month, 1);
							break;
						case 10: // "yyyy-mm-dd"
							year = Integer.parseInt(sDate.substring(0, 4));
							logger.info("sDate:" + sDate);
							String sMonth = "";
							if (sDate.length() >= 7)
								sMonth = sDate.substring(5, 7);
							else
								sMonth = "1";
							logger.info("month:" + sMonth);
							month = Integer.parseInt(sMonth);
							if (sDate.length() >= 10)
								day = Integer.parseInt(sDate.substring(8, 10));
							else
								day = 1;
							leftLDate = LocalDate.of(year, month, day);
							break;
						}
					} else if (Constants.RULE_TEXTBOX_DATE.equals(typeL)) {
						String sDate = objLeft.getString("value");
						if ("".equals(sDate))
							throw new TimeErrorException("时间为空");

						int year = 0;
						int month = 0;
						int day = 0;
						switch (format.length()) {
						case 4: // "yyyy"
							year = Integer.parseInt(sDate.substring(0, 4));
							leftLDate = LocalDate.of(year, 1, 1);
							break;
						case 7: // "yyyy-mm"
							year = Integer.parseInt(sDate.substring(0, 4));
							month = Integer.parseInt(sDate.substring(5, 7));
							leftLDate = LocalDate.of(year, month, 1);
							break;
						case 10: // "yyyy-mm-dd"
							year = Integer.parseInt(sDate.substring(0, 4));
							month = Integer.parseInt(sDate.substring(5, 7));
							day = Integer.parseInt(sDate.substring(8, 10));
							leftLDate = LocalDate.of(year, month, day);
							break;
						}
					} else if (Constants.RULE_YEAR_ADD.equals(typeL)) {
						String sDate = objLeft.getString("value");
						int yearAdd = Integer.parseInt(sDate);
						leftLDate = LocalDate.now().plusYears(yearAdd - 1);
						leftLDate = leftLDate.withMonth(1);
						leftLDate = leftLDate.withDayOfMonth(1);
					} else if (Constants.RULE_MONTH.equals(typeL)) {
						String sDate = objLeft.getString("value");
						int month = Integer.parseInt(sDate);
						leftLDate = leftLDate.withMonth(month);
						leftLDate = leftLDate.withDayOfMonth(1);
					} else if (Constants.RULE_DAY.equals(typeL)) {
						String sDate = objLeft.getString("value");
						int day = Integer.parseInt(sDate);
						leftLDate = leftLDate.withDayOfMonth(day);
					}
				}
				// right:
				int year = 0;
				int month = 0;
				int day = 0;
				for (int jR = 0; jR < arrayRight.length(); jR++) {
					JSONObject objRight = (JSONObject) arrayRight.get(jR);
					String typeR = objRight.getString("type");

					if (Constants.RULE_FORMFIELD.equals(typeR)) {
						int column = objRight.getInt("column");
						DataInfo data = (DataInfo) record.get(column);
						if (data == null) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("该行记录日期是空");
						}
						String sDate = data.getValue();

						if ("".equals(sDate))
							throw new TimeErrorException("时间为空");
						switch (format.length()) {
						case 4: // "yyyy"
							year = Integer.parseInt(sDate.substring(0, 4));
							rightLDate = LocalDate.of(year, 1, 1);
							break;
						case 7: // "yyyy-mm"
							year = Integer.parseInt(sDate.substring(0, 4));
							if (sDate.length() >= 7)
								month = Integer.parseInt(sDate.substring(5, 7));
							else
								month = 1;
							rightLDate = LocalDate.of(year, month, 1);
							break;
						case 10: // "yyyy-mm-dd"
							year = Integer.parseInt(sDate.substring(0, 4));
							if (sDate.length() >= 7)
								month = Integer.parseInt(sDate.substring(5, 7));
							else
								month = 1;
							if (sDate.length() >= 10)
								day = Integer.parseInt(sDate.substring(8, 10));
							else
								day = 1;
							rightLDate = LocalDate.of(year, month, day);
							break;
						}
					} else if (Constants.RULE_TEXTBOX_DATE.equals(typeR)) {
						String sDate = objRight.getString("value");
						if ("".equals(sDate))
							throw new TimeErrorException("时间为空");

						switch (format.length()) {
						case 4: // "yyyy"
							year = Integer.parseInt(sDate.substring(0, 4));
							rightLDate = LocalDate.of(year, 1, 1);
							break;
						case 7: // "yyyy-mm"
							year = Integer.parseInt(sDate.substring(0, 4));
							month = Integer.parseInt(sDate.substring(5, 7));
							rightLDate = LocalDate.of(year, month, 1);
							break;
						case 10: // "yyyy-mm-dd"
							year = Integer.parseInt(sDate.substring(0, 4));
							month = Integer.parseInt(sDate.substring(5, 7));
							day = Integer.parseInt(sDate.substring(8, 10));
							rightLDate = LocalDate.of(year, month, day);
							break;
						}
					} else if (Constants.RULE_YEAR_ADD.equals(typeR)) {
						String sDate = objRight.getString("value");
						int yearAdd = Integer.parseInt(sDate);
						rightLDate = LocalDate.now().plusYears(yearAdd - 1);
						rightLDate = rightLDate.withMonth(1);
						rightLDate = rightLDate.withDayOfMonth(1);
					} else if (Constants.RULE_MONTH.equals(typeR)) {
						String sDate = objRight.getString("value");
						month = Integer.parseInt(sDate);
						rightLDate = rightLDate.withMonth(month);
						rightLDate = rightLDate.withDayOfMonth(1);
					} else if (Constants.RULE_DAY.equals(typeR)) {
						String sDate = objRight.getString("value");
						day = Integer.parseInt(sDate);
						rightLDate = rightLDate.withDayOfMonth(day);
					}
				}

			} catch (NumberFormatException e) {
				// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				// messageList.add("第" + (i + 1) + "行的日期格式不正确！");
				isDate = false;
				isRight = false;
				break;
			} catch (TimeErrorException e) {
				isDate = false;
				isRight = true;
			}
			// operator:

			if (!isDate) {
				message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
				messageList.add("该记录的日期格式不正确");
				message.setMessage_info(messageList);
				return message;
			}

			String sOP = objOper.getString("operator");
			if (Constants.V_EQUAL.equals(sOP)) {
				if (!(leftLDate.isEqual(rightLDate))) {
					logger.info("left:" + leftLDate + ", right:" + rightLDate);
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i+1) + "行的等式不成立！");
					isRight = false;
				} else {
					isRight = true;
					break;
				}
			} else if (Constants.V_GREATT.equals(sOP)) {
				if (!(leftLDate.isAfter(rightLDate))) {
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i+1) + "行的不等式不成立！");
					isRight = false;
				} else {
					isRight = true;
					break;
				}
			} else if (Constants.V_GREATTE.equals(sOP)) {
				if (!(leftLDate.isAfter(rightLDate)) || (leftLDate.isEqual(rightLDate))) {
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i+1) + "行的不等式不成立！");
					isRight = false;
				} else {
					isRight = true;
					break;
				}
			} else if (Constants.V_LESST.equals(sOP)) {
				logger.info("left Date:" + leftLDate.toString());
				logger.info("right date:" + rightLDate.toString());
				if (!(leftLDate.isBefore(rightLDate))) {
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i+1) + "行的不等式不成立！");
					isRight = false;
				} else {
					isRight = true;
					break;
				}
			} else if (Constants.V_LESSTE.equals(sOP)) {
				logger.info("leftLDate:" + leftLDate.toString() + ",rightLDate:" + rightLDate.toString());
				if (!(leftLDate.isBefore(rightLDate) || (leftLDate.isEqual(rightLDate)))) {
					// message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					// messageList.add("第" + (i+1) + "行的不等式不成立！");
					isRight = false;
				} else {
					isRight = true;
					break;
				}
			}
		}
		if (!isRight) {
			message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
			messageList.add("该记录的时间条件不满足！");
		}
		
		message.setMessage_info(messageList);

		return message;

	}

	private ArrayList translateRulesSured(JSONObject obj, int form_id, Workbook wb, int columns) {
		ArrayList al = new ArrayList();
		JSONArray array = null;
		JSONArray arrayLeft = null;
		JSONArray arrayRight = null;
		ArrayList line = null;
		JSONObject compareOp = null;
		String format = "";

		boolean isFound = false;

		// format:
		format = getFormat(obj);
		al.add(format);

		ArrayList sentences = this.cutRulesByOr(obj);

		for (int j = 0; j < sentences.size(); j++) {
			isFound = false;
			array = (JSONArray) sentences.get(j);
			line = new ArrayList();
			arrayLeft = new JSONArray();
			arrayRight = new JSONArray();

			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = (JSONObject) array.get(i);
				String type = ob.getString("type");

				if (Constants.RULE_FORMFIELD.equals(type)) {
					String name1 = ob.getString("field");
					FormField field = fManager.getFieldByPhysicName(form_id, name1);
					int column1 = helper.getColumn2Check(wb, field.getBus_name(), columns);
					ob.put("column", column1);
				}
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

			line.add(arrayLeft);
			line.add(arrayRight);
			line.add(compareOp);

			al.add(line);
		}
		return al;
	}

	private ArrayList translateRulesSuredSingle(JSONObject obj, int form_id, int columns, ArrayList displayFields) {
		ArrayList al = new ArrayList();
		JSONArray array = null;
		JSONArray arrayLeft = null;
		JSONArray arrayRight = null;
		ArrayList line = null;
		JSONObject compareOp = null;
		String format = "";

		boolean isFound = false;

		// format:
		format = getFormat(obj);
		al.add(format);

		ArrayList sentences = this.cutRulesByOr(obj);

		for (int j = 0; j < sentences.size(); j++) {
			isFound = false;
			array = (JSONArray) sentences.get(j);
			line = new ArrayList();
			arrayLeft = new JSONArray();
			arrayRight = new JSONArray();

			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = (JSONObject) array.get(i);
				String type = ob.getString("type");

				if (Constants.RULE_FORMFIELD.equals(type)) {
					String name1 = ob.getString("field");
					FormField field = fManager.getFieldByPhysicName(form_id, name1);
					// int column1 = helper.getColumn2Check(wb,
					// field.getBus_name(), columns);
					int column = 0;
					for (int k = 0; k < displayFields.size(); k++) {
						FormField fieldTemp = (FormField) displayFields.get(k);
						if (fieldTemp.getPhysic_name().equals(field.getPhysic_name())) {
							column = k;
							break;
						}
					}
					ob.put("column", column);
				}
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

			line.add(arrayLeft);
			line.add(arrayRight);
			line.add(compareOp);

			al.add(line);
		}
		return al;
	}

	private String getFormat(JSONObject obj) {
		JSONArray array = obj.getJSONArray("rules");
		String format = null;

		JSONObject object = array.getJSONObject(array.length() - 1);
		format = object.getString("format");

		return format;
	}

	public boolean isCompareOperator(String operator) {
		boolean result = false;

		if (Constants.V_GREATT.equals(operator) || Constants.V_GREATTE.equals(operator)
				|| Constants.V_LESST.equals(operator) || Constants.V_LESSTE.equals(operator)
				|| Constants.V_EQUAL.equals(operator)) {
			result = true;
		}
		return result;
	}
}
