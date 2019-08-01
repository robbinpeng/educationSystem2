package com.philip.edu.rule;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class FieldFormatCheck {
	private static Logger logger = Logger.getLogger(FieldFormatCheck.class);
	private static FormManager formManager = new FormManager();
	private static ExcelHelper excelHelper = new ExcelHelper();

	public MessageInfo formatCheck(String[][] data, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		boolean isRight = false;
		ArrayList fields = formManager.getFormFields(form_id);
		ArrayList checkFields = new ArrayList();
		int columns = data[0].length;
		int lines = data.length;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		//SXSSFSheet sheet = wb.getSheetAt(0);

		// Caption:
		for (int j = 0; j < fields.size(); j++) {
			boolean set = false;
			FormatLine line = new FormatLine();
			FormField caption = (FormField) fields.get(j);

			if ("TJSJ".equals(caption.getPhysic_name()))
				continue;

			int columnCh = excelHelper.getColumn2Check(data, caption.getBus_name());
			line.setColumnCheck(columnCh);
			line.setColumnName(caption.getBus_name());
			if (caption.getIs_required() == 'Y')
				line.setIs_required(true);
			else
				line.setIs_required(false);
			if (caption.getIs_hidden() == 'Y')
				continue;
			if (caption.getData_type() != 0) {
				line.setDataType(caption.getData_type());
				set = true;
			}
			line.setLength(caption.getLength());
			if (caption.getText_format() != Constants.V_TEXT_FORMAT_NO) {
				line.setTextFormat(caption.getText_format());
				set = true;
			}

			if (set)
				checkFields.add(line);
		}

		// every line to check:
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		for (int i = 1; i < lines; i++) {
			//SXSSFRow row = sheet.getRow(i);

			for (int k = 0; k < checkFields.size(); k++) {
				FormatLine line1 = (FormatLine) checkFields.get(k);
				String value = "";
				String vFormat = "";
				//SXSSFCell cell = row.getCell(line1.getColumnCheck());
				String value_cell = data[i][line1.getColumnCheck()];
				
				if (value_cell != null) {
					if (!"".equals(value_cell)) {

						//length check:
						if(value_cell.length() > line1.getLength()) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]字段长度超出系统要求"); 
						}
						
						//format check:
						switch (line1.getDataType()) {
						case Constants.V_DATA_TYPE_INTEGER:
							if (!isNumeric(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是整数！");
							}
							break;
						case Constants.V_DATA_TYPE_FLOAT:
							if (!(isFloat(value_cell) || isNumeric(value_cell))) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是小数！");
							}
							break;
						case Constants.V_DATA_TYPE_DATE:
							/*
							 * switch (line1.getTextFormat()) { case
							 * Constants.V_TEXT_FORMAT_DATE_YEAR: if
							 * (value.length() != 4 || !isNumeric(value)) {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add("第"
							 * + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY'格式"); } break; case
							 * Constants.V_TEXT_FORMAT_DATE_MONTH: if
							 * (value.length() != 7 || value.charAt(4) != '-') {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList
							 * .add("第" + (i + 1) + "行的[" +
							 * line1.getColumnName() + "]日期不符合'YYYY-MM'格式"); }
							 * else { String year = value.substring(0, 4);
							 * String month = value.substring(5, 7); if
							 * (!isNumeric(year) || !isNumeric(month)) {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add(
							 * "第" + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY-MM'格式"); } else { int iMonth =
							 * Integer.parseInt(month); if (iMonth < 1 || iMonth
							 * > 12) { message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add("第"
							 * + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY-MM'格式"); } } } break; case
							 * Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH: if
							 * (value.length() != 6 || !isNumeric(value)) {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add("第"
							 * + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYYMM'格式"); } else { String month =
							 * value.substring(4, 6); int iMonth =
							 * Integer.parseInt(month); if (iMonth < 1 || iMonth
							 * > 12) { message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add(
							 * "第" + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYYMM'格式"); } } break; case
							 * Constants.V_TEXT_FORMAT_DATE_DAY:
							 * logger.info("date is:" + value); if
							 * (value.length() != 10 || value.charAt(4) != '-'
							 * || value.charAt(7) != '-') {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add(
							 * "第" + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY-MM-DD'格式"); } else { String year =
							 * value.substring(0, 4); String month =
							 * value.substring(5, 7); String day =
							 * value.substring(8, 10); if (!isNumeric(year) ||
							 * !isNumeric(month) || !isNumeric(day)) {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add(
							 * "第" + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY-MM-DD'格式"); } else { int iMonth =
							 * Integer.parseInt(month); int iDay =
							 * Integer.parseInt(day); if (iMonth < 1 || iMonth >
							 * 12 || iDay < 1 || iDay > 31) {
							 * message.setMessage_type(Constants.
							 * RULECHECK_MESSAGE_RULE_FAIL); messageList.add("第"
							 * + (i + 1) + "行的[" + line1.getColumnName() +
							 * "]日期不符合'YYYY-MM-DD'格式"); } } } break; default:
							 * break; }
							 */
							break;
						default:
							break;
						}

						switch (line1.getTextFormat()) {
						case Constants.V_TEXT_FORMAT_MOBILEPHONE:
							//logger.info("value is: " + value);
							if (!isMobile(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是电话号码！");
							}
							break;
						case Constants.V_TEXT_FORMAT_EMAIL:
							if (!isEmail(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是email！");
							}
							break;
						case Constants.V_TEXT_FORMAT_IDENTITY:
							if (!isIdentity(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是身份证号！");
							}
							break;
						case Constants.V_TEXT_FORMAT_WEBSITE:
							if (!isWebsite(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是网址！");
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_YEAR:
							if (value_cell.length() != 4 || !isNumeric(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY'格式");
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_MONTH:
							//logger.info("date is:" + value_cell);
							if (value_cell.length() != 7 || value_cell.charAt(4) != '-') {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
							} else {
								String year = value_cell.substring(0, 4);
								String month = value_cell.substring(5, 7);
								if (!isNumeric(year) || !isNumeric(month)) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList
											.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
								} else {
									int iMonth = Integer.parseInt(month);
									if (iMonth < 1 || iMonth > 12) {
										message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
										messageList.add(
												"第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
									}
								}
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH:
							if (value_cell.length() != 6 || !isNumeric(value_cell)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
							} else {
								String month = value_cell.substring(4, 6);
								int iMonth = Integer.parseInt(month);
								if (iMonth < 1 || iMonth > 12) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
								}
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_DAY:
							//logger.info("date is:" + value);
							if (value_cell.length() != 10 || value_cell.charAt(4) != '-' || value_cell.charAt(7) != '-') {
								//logger.info("entering 1.");
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
							} else {
								String year = value_cell.substring(0, 4);
								String month = value_cell.substring(5, 7);
								String day = value_cell.substring(8, 10);
								//logger.info("year:" + year);
								//logger.info("month" + month);
								//logger.info("day:" + day);
								if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
									//logger.info("entering 2.");
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList.add(
											"第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
								} else {
									int iMonth = Integer.parseInt(month);
									int iDay = Integer.parseInt(day);
									if (iMonth < 1 || iMonth > 12 || iDay < 1 || iDay > 31) {
										//logger.info("entering 3.");
										message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
										messageList.add(
												"第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
									}
								}
							}
						case Constants.V_TEXT_FORMAT_NO:
							break;
						default:
							break;
						}
					} else {
						if (line1.isIs_required()) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]值为空！");
						}
					}
				} else {
					if (line1.isIs_required()) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]值为空！");
					}
				}
			}
		}
		message.setMessage_info(messageList);

		return message;
	}

	public MessageInfo formatCheckSingleLine(ArrayList record, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		boolean isRight = false;
		ArrayList fields = formManager.getFormFields(form_id);
		ArrayList checkFields = new ArrayList();
		// int columns = excelHelper.getExcelColumns(wb);
		// int lines = excelHelper.getExcelLines(wb, form_id, columns);

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		// Caption:
		for (int j = 0; j < fields.size(); j++) {
			boolean set = false;
			FormatLine line = new FormatLine();
			FormField caption = (FormField) fields.get(j);

			if ("TJSJ".equals(caption.getPhysic_name()))
				continue;

			// int columnCh = excelHelper.getColumn2Check(wb,
			// caption.getBus_name(), columns);
			//line.setColumnCheck(columnCh);
			line.setColumnName(caption.getBus_name());
			if (caption.getIs_required() == 'Y')
				line.setIs_required(true);
			else
				line.setIs_required(false);
			if (caption.getIs_hidden() == 'Y')
				continue;
			if (caption.getData_type() != 0) {
				line.setDataType(caption.getData_type());
				set = true;
			}
			if (caption.getText_format() != Constants.V_TEXT_FORMAT_NO) {
				line.setTextFormat(caption.getText_format());
				set = true;
			}

			if (set)
				checkFields.add(line);
		}

		// every line to check:
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);

		for (int k = 0; k < checkFields.size(); k++) {
			FormatLine line1 = (FormatLine) checkFields.get(k);
			String value = "";
			String vFormat = "";
			DataInfo data = (DataInfo)record.get(k+1);

			if (data != null) {
				
				if (!"".equals(data.getValue())) {

					switch (line1.getDataType()) {
					case Constants.V_DATA_TYPE_INTEGER:
						if (!isNumeric(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是整数！");
							messageList.add("[" + line1.getColumnName() + "]字段不是整数");
						}
						break;
					case Constants.V_DATA_TYPE_FLOAT:
						if (!(isFloat(data.getValue()) || isNumeric(data.getValue()))) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是小数！");
							messageList.add("[" + line1.getColumnName() + "]字段不是小数");
						}
						break;
					case Constants.V_DATA_TYPE_DATE:
						break;
					default:
						break;
					}

					switch (line1.getTextFormat()) {
					case Constants.V_TEXT_FORMAT_MOBILEPHONE:
						//logger.info("value is: " + data.getValue());
						if (!isMobile(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是电话号码！");
							messageList.add("[" + line1.getColumnName() + "]字段不是电话号码");
						}
						break;
					case Constants.V_TEXT_FORMAT_EMAIL:
						if (!isEmail(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是email！");
							messageList.add("[" + line1.getColumnName() + "]字段不是电子邮件");
						}
						break;
					case Constants.V_TEXT_FORMAT_IDENTITY:
						if (!isIdentity(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是身份证号！");
							messageList.add("[" + line1.getColumnName() + "]字段不是身份证");
						}
						break;
					case Constants.V_TEXT_FORMAT_WEBSITE:
						if (!isWebsite(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是网址！");
							messageList.add("[" + line1.getColumnName() + "]字段不是网址");
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_YEAR:
						if (data.getValue().length() != 4 || !isNumeric(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY'格式");
							messageList.add("[" + line1.getColumnName() + "]字段不符合'YYYY'的格式");
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_MONTH:
						//logger.info("date is:" + value);
						if (data.getValue().length() != 7 || data.getValue().charAt(4) != '-') {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
							messageList.add("[" + line1.getColumnName() + "]字段不符合'YYYY-MM'的格式");
						} else {
							String year = data.getValue().substring(0, 4);
							String month = data.getValue().substring(5, 7);
							if (!isNumeric(year) || !isNumeric(month)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
								messageList.add("[" + line1.getColumnName() + "]字段不符合'YYYY-MM'的格式");
							} else {
								int iMonth = Integer.parseInt(month);
								if (iMonth < 1 || iMonth > 12) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
									messageList.add("[" + line1.getColumnName() + "]字段日期不符合'YYYY-MM'格式");
								}
							}
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH:
						if (data.getValue().length() != 6 || !isNumeric(data.getValue())) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
							messageList.add("[" + line1.getColumnName() + "]字段日期不符合'YYYYMM'的格式");
						} else {
							String month = data.getValue().substring(4, 6);
							int iMonth = Integer.parseInt(month);
							if (iMonth < 1 || iMonth > 12) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
								messageList.add("[" + line1.getColumnName() + "]字符日期不符合'YYYYMM'的格式");
							}
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_DAY:
						//logger.info("date is:" + data.getValue());
						if (data.getValue().length() != 10 || data.getValue().charAt(4) != '-' || value.charAt(7) != '-') {
							//logger.info("entering 1.");
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
							messageList.add("[" + line1.getColumnName() + "]字符日期不符合'YYYY-MM-DD'的格式");
						} else {
							String year = data.getValue().substring(0, 4);
							String month = data.getValue().substring(5, 7);
							String day = data.getValue().substring(8, 10);
							//logger.info("year:" + year);
							//logger.info("month" + month);
							//logger.info("day:" + day);
							if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
								//logger.info("entering 2.");
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
								messageList.add("[" + line1.getColumnName() + "]字符日期不符合'YYYY-MM-DD'的格式");
							} else {
								int iMonth = Integer.parseInt(month);
								int iDay = Integer.parseInt(day);
								if (iMonth < 1 || iMonth > 12 || iDay < 1 || iDay > 31) {
									//logger.info("entering 3.");
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									//messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
									messageList.add("[" + line1.getColumnName() + "]字符日期不符合'YYYY-MM-DD'的格式");
								}
							}
						}
					case Constants.V_TEXT_FORMAT_NO:
						break;
					default:
						break;
					}
				} else {
					if (line1.isIs_required()) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("[" + line1.getColumnName() + "]值为空！");
					}
				}
			} else {
				if (line1.isIs_required()) {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("[" + line1.getColumnName() + "]值为空！");
				}
			}
		}

		message.setMessage_info(messageList);

		return message;
	}

	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	private boolean isFloat(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	private boolean isEmail(String str) {
		Pattern pattern = Pattern.compile(
				"^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
		Matcher isEmail = pattern.matcher(str);
		if (!isEmail.matches()) {
			return false;
		}
		return true;
	}

	private boolean isMobile(String str) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
		Matcher isMobile = pattern.matcher(str);
		if (!isMobile.matches()) {
			return false;
		}
		return true;
	}

	private boolean isIdentity(String str) {
		Pattern pattern = Pattern.compile("(^\\d{18}$)|(^\\d{15}$)");
		Matcher isId = pattern.matcher(str);
		if (!isId.matches()) {
			return false;
		}
		return true;
	}

	private boolean isWebsite(String str) {
		Pattern pattern = Pattern.compile("(((https|http)?://)?([a-z0-9]+[.])|(www.))"
				+ "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)");
		Matcher isWeb = pattern.matcher(str);
		if (!isWeb.matches()) {
			return false;
		}
		return true;
	}
}
