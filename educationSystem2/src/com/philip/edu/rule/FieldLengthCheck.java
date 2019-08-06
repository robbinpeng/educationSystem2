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

public class FieldLengthCheck {
	private static Logger logger = Logger.getLogger(FieldLengthCheck.class);
	private static FormManager formManager = new FormManager();
	private static ExcelHelper excelHelper = new ExcelHelper();

	public MessageInfo lengthCheck(String[][] data, int form_id) {
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		boolean isRight = false;
		ArrayList fields = formManager.getFormFields(form_id);
		ArrayList checkFields = new ArrayList();
		int columns = data[0].length;
		int lines = data.length;

		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		// SXSSFSheet sheet = wb.getSheetAt(0);

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
			// SXSSFRow row = sheet.getRow(i);

			for (int k = 0; k < checkFields.size(); k++) {
				FormatLine line1 = (FormatLine) checkFields.get(k);
				String value = "";
				String vFormat = "";
				// SXSSFCell cell = row.getCell(line1.getColumnCheck());
				String value_cell = data[i][line1.getColumnCheck()];

				if (value_cell != null) {
					if (!"".equals(value_cell)) {

						// length check:
						if (value_cell.length() > line1.getLength()) {
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (i + 1) + "行的[" + line1.getColumnName() + "]字段长度超出系统要求");
						}
					}
				}
			}

		}
		message.setMessage_info(messageList);

		return message;
	}

}
