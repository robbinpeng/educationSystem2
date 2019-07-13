package com.philip.edu.rule;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class FieldFormatCheck {
	private static Logger logger = Logger.getLogger(FieldFormatCheck.class);
	private static FormManager formManager = new FormManager();
	private static ExcelHelper excelHelper = new ExcelHelper();

	public MessageInfo formatCheck(Workbook wb, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		boolean isRight = false;
		ArrayList fields = formManager.getFormFields(form_id);
		ArrayList checkFields = new ArrayList();
		int lines = excelHelper.getExcelLines(wb);
		int columns = excelHelper.getExcelColumns(wb);

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		Sheet sheet = wb.getSheetAt(0);

		// Caption:
		for (int j = 0; j < fields.size(); j++) {
			boolean set = false;
			FormatLine line = new FormatLine();
			FormField caption = (FormField) fields.get(j);
			
			if("TJSJ".equals(caption.getPhysic_name()))continue;

			int columnCh = excelHelper.getColumn2Check(wb, caption.getBus_name(), columns);
			line.setColumnCheck(columnCh);
			line.setColumnName(caption.getBus_name());
			if (caption.getIs_hidden() == 'Y') continue;
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
		for (int i = 1; i < lines; i++) {
			Row row = sheet.getRow(i);

			for (int k = 0; k < checkFields.size(); k++) {
				FormatLine line1 = (FormatLine) checkFields.get(k);
				String value = "";
				String vFormat = "";
				Cell cell = row.getCell(line1.getColumnCheck());

				if (cell != null) {
					CellType cellType = cell.getCellTypeEnum();
					if (cellType == CellType.STRING) {
						value = cell.getStringCellValue();
					} else if (cellType == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(cell)) {
							Date vDate = cell.getDateCellValue();
							CellStyle style = cell.getCellStyle();
							
							vFormat = style.getDataFormatString();
							
							SimpleDateFormat sdf = new SimpleDateFormat(vFormat);
							value = sdf.format(vDate);
							logger.info(vFormat);
							logger.info(value);
						} else {
							DecimalFormat df = new DecimalFormat("0");    
							value = df.format(cell.getNumericCellValue()); 
							//if(cell.getNumericCellValue()%1==0)value = new Integer(new Double(cell.getNumericCellValue()).intValue()).toString();
							//else value = String.valueOf(cell.getNumericCellValue());
						}
					}

					switch (line1.getDataType()) {
					case Constants.V_DATA_TYPE_INTEGER:
						if (!isNumeric(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是整数！");
						}
						break;
					case Constants.V_DATA_TYPE_FLOAT:
						if (!(isFloat(value)||isNumeric(value))) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是小数！");
						}
						break;
					case Constants.V_DATA_TYPE_DATE:
						switch (line1.getTextFormat()) {
						case Constants.V_TEXT_FORMAT_DATE_YEAR:
							if (value.length() != 4 || !isNumeric(value)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY'格式");
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_MONTH:
							if (value.length() != 7 || value.charAt(4) != '-') {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
							} else {
								String year = value.substring(0, 4);
								String month = value.substring(5, 7);
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
							if (value.length() != 6 || !isNumeric(value)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
							} else {
								String month = value.substring(4, 6);
								int iMonth = Integer.parseInt(month);
								if (iMonth < 1 || iMonth > 12) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
								}
							}
							break;
						case Constants.V_TEXT_FORMAT_DATE_DAY:
							logger.info("date is:" + value);
							if (value.length() != 10 || value.charAt(4) != '-' || value.charAt(7) != '-') {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
							} else {
								String year = value.substring(0, 4);
								String month = value.substring(5, 7);
								String day = value.substring(8, 10);
								if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList.add(
											"第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
								} else {
									int iMonth = Integer.parseInt(month);
									int iDay = Integer.parseInt(day);
									if (iMonth < 1 || iMonth > 12 || iDay < 1 || iDay > 31) {
										message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
										messageList.add(
												"第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
									}
								}
							}
							break;
						default:
							break;
						}
						break;
					default:
						break;
					}

					switch (line1.getTextFormat()) {
					case Constants.V_TEXT_FORMAT_MOBILEPHONE:
						logger.info("value is: " + value);
						if (!isMobile(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是电话号码！");
						}
						break;
					case Constants.V_TEXT_FORMAT_EMAIL:
						if (!isEmail(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是email！");
						}
						break;
					case Constants.V_TEXT_FORMAT_IDENTITY:
						if (!isIdentity(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是身份证号！");
						}
						break;
					case Constants.V_TEXT_FORMAT_WEBSITE:
						if (!isWebsite(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]不是网址！");
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_YEAR:
						if (value.length() != 4 || !isNumeric(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY'格式");
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_MONTH:
						logger.info("date is:" + value);
						if (value.length() != 7 || value.charAt(4) != '-') {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
						} else {
							String year = value.substring(0, 4);
							String month = value.substring(5, 7);
							if (!isNumeric(year) || !isNumeric(month)) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
							} else {
								int iMonth = Integer.parseInt(month);
								if (iMonth < 1 || iMonth > 12) {
									message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
									messageList
											.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM'格式");
								}
							}
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH:
						if (value.length() != 6 || !isNumeric(value)) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
						} else {
							String month = value.substring(4, 6);
							int iMonth = Integer.parseInt(month);
							if (iMonth < 1 || iMonth > 12) {
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYYMM'格式");
							}
						}
						break;
					case Constants.V_TEXT_FORMAT_DATE_DAY:
						logger.info("date is:" + value);
						if (value.length() != 10 || value.charAt(4) != '-' || value.charAt(7) != '-') {
							logger.info("entering 1.");
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
						} else {
							String year = value.substring(0, 4);
							String month = value.substring(5, 7);
							String day = value.substring(8, 10);
							logger.info("year:" + year);
							logger.info("month" + month);
							logger.info("day:" + day);
							if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
								logger.info("entering 2.");
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]日期不符合'YYYY-MM-DD'格式");
							} else {
								int iMonth = Integer.parseInt(month);
								int iDay = Integer.parseInt(day);
								if (iMonth < 1 || iMonth > 12 || iDay < 1 || iDay > 31) {
									logger.info("entering 3.");
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
