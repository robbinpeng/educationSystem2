package com.philip.edu.rule;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.zk.ui.Executions;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class ExcelHelper {
	private Logger logger = Logger.getLogger(ExcelHelper.class);

	private static FormManager manager = new FormManager();

	public boolean is_format_right(Workbook wb, int form_id) {
		logger.info("begin to check whether excel format is right.");
		List al2 = new ArrayList();

		boolean is_right = true;

		Sheet sheet = wb.getSheetAt(0);

		// 0. make sure columns are the same:
		int excelColumns = 0;
		int tableColumns = 0;

		excelColumns = getExcelColumns(wb);
		List al1 = manager.getFormFields(form_id);

		Row row = sheet.getRow(0);

		for (int i = 0; i < al1.size(); i++) {
			FormField temp = (FormField) al1.get(i);
			if (temp.getPhysic_name().equals("TJSJ")) {
				al2.add(temp);
				continue;
			}
			if (temp.getIs_hidden() == 'N') {
				Cell cell = row.getCell(i - 1);
				String content = cell.getStringCellValue();
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

	public int getExcelColumns(Workbook wb) {
		logger.info("to count the excel columns");

		int excelColumns = 0;

		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		int i = 1;
		while (i > 0) {
			Cell cell = row.getCell(i - 1);

			if (cell == null) {
				break;
			}

			Object cellValue = this.getCellValue(cell);
			if (cellValue == null || "".equals(cellValue.toString()))
				break;
			i++;
		}
		excelColumns = i - 1;

		logger.info("the excel has " + excelColumns + " columns!");

		return excelColumns;
	}

	public int getExcelLines(Workbook wb, int form_id, int columns) {
		logger.info("to count the excel lines");
		int lines = 0;
		int index = 0;

		Sheet sheet = wb.getSheetAt(0);
		Row row0 = sheet.getRow(0);
		boolean isEnd = false;

		while (index + 1 > 0) {
			Row row1 = sheet.getRow(index);
			if (row1 == null)
				break;

			for (int i = 0; i < columns; i++) {
				Cell cell1 = row1.getCell(i);
				if (cell1 == null) {
					String title = row0.getCell(i).getStringCellValue();
					FormField field = manager.getFieldByBusName(form_id, title);
					if (field.getIs_required() == 'N') {
						continue;
					} else {
						isEnd = true;
						break;
					}
				}
			}
			if (isEnd)
				break;
			index++;
		}
		lines = index;

		logger.info("the excel has " + lines + " lines");

		return lines;

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

	public int getColumn2Check(Workbook wb, String bus_name, int columnTotal) {
		logger.info("to decide which column to check");
		int column = 0;

		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);

		for (int i = 0; i < columnTotal; i++) {
			Cell cell = row.getCell(i);

			if (cell == null) {
				column = i;
				break;
			}
			Object value = this.getCellValue(cell);
			if (cell != null && bus_name.trim().equals(value.toString())) {
				column = i;
				break;
			}
		}

		return column;
	}
}
