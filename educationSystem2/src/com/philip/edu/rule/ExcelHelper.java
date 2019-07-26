package com.philip.edu.rule;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.zkoss.zk.ui.Executions;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.excel.RapidExcelHelper;

public class ExcelHelper {
	private Logger logger = Logger.getLogger(ExcelHelper.class);

	private static FormManager manager = new FormManager();

	public boolean is_format_right(String[][] data, int form_id) {
		logger.info("begin to check whether excel format is right.");
		List al2 = new ArrayList();

		boolean is_right = true;

		// 0. make sure columns are the same:
		int excelColumns = 0;
		int tableColumns = 0;
		int excelLines = 0;

		excelColumns = data[0].length;
		excelLines = data.length;
		List al1 = manager.getFormFields(form_id);	

		int index = 0;
		for (int i = 0; i < al1.size(); i++) {
			FormField temp = (FormField) al1.get(i);
			if (temp.getPhysic_name().equals("TJSJ")) {
				al2.add(temp);
				continue;
			}
			if (temp.getIs_hidden() == 'N') {
				String content = data[0][index++];
				if (!content.equals(temp.getBus_name())) {
					logger.info("" + content + "不符" + temp.getBus_name());
					return false;
				}
				al2.add(temp);
			}
		}

		tableColumns = al2.size() - 1;
		logger.info("tableColumns=" + tableColumns + ", excelColumns=" + excelColumns);
		if (excelColumns != tableColumns) {
			logger.info("----------ERROR:导入数据表格式不正确！---------");
			is_right = false;
		}

		return is_right;
	}

	public int getColumn2Check(String[][] data, String bus_name) {
		logger.info("to decide which column to check");
		int column = 0;

		for (int i = 0; i < data[0].length; i++) {
			String cell = data[0][i];

			if (cell == null) {
				column = i;
				break;
			}
			
			if (cell != null && bus_name.trim().equals(cell)) {
				column = i;
				break;
			}
		}

		return column;
	}
	
	public Object getCellValue(Cell cell) {
		Object cellValue = null;

		CellType cellType = cell.getCellTypeEnum();// CellType.forInt(cell.getCellType());
		if (cellType == CellType.STRING) {
			cellValue = cell.getStringCellValue();
		} else if (cellType == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = cell.getDateCellValue();
			} else {
				if (cell.getNumericCellValue() % 1 == 0)
					cellValue = new Integer(new Double(cell.getNumericCellValue()).intValue());
				else
					cellValue = cell.getNumericCellValue();
			}
		} else if (cellType == CellType.BOOLEAN) {
			cellValue = cell.getBooleanCellValue();
		} else if (cellType == CellType.FORMULA) {
			cellValue = cell.getCellFormula();
		} else if (cellType == CellType.BLANK) {
			cellValue = "";
		}
		return cellValue;
	}
}
