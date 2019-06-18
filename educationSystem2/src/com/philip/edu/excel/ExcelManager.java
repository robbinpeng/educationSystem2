package com.philip.edu.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.cf.PatternFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class ExcelManager {
	private static Logger logger = Logger.getLogger(ExcelManager.class);
	
	private static FormManager fManager = new FormManager();
	
	public static boolean generateTemplate(int form_id, String file_path){
		boolean isSuccess = false;
		
		Workbook wb = new HSSFWorkbook();
		
		Form form = fManager.getFormById(form_id);
		ArrayList fields = fManager.getFormFields(form_id);
		ArrayList al = new ArrayList();
		
		for(int i=0; i<fields.size(); i++){
			FormField field = (FormField) fields.get(i);
			if(field.getIs_report() == 'N')continue;
			
			al.add(field);
		}
		
		Sheet sheet = wb.createSheet(form.getBus_name());
		
		Row row = sheet.createRow(0);
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.index);
		style.setFont(font);
		
		for(int i=0; i<al.size(); i++){
			FormField field = (FormField) al.get(i);
			
			Cell cell = row.createCell(i);
			cell.setCellValue(field.getBus_name());
			cell.setCellStyle(style);
		}
		
		try {
			FileOutputStream output = new FileOutputStream(file_path);
			
			wb.write(output);
			
			output.close();
			wb.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e);
		}
		
		return isSuccess;
	}
}
