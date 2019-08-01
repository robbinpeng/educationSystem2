package com.philip.edu.rule;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.philip.edu.basic.Constants;
import com.philip.edu.excel.RapidExcelHelper;

public class ExcelHelperTest {

	@Test
	public void testIs_format_right() {
		boolean test = true;
		ExcelHelper helper = new ExcelHelper();
		FileInputStream in = null;
		SXSSFWorkbook wb = null;
		RapidExcelHelper rapidHelper = new RapidExcelHelper();
		
		try {
			in = new FileInputStream("D:/Develop/education/test/1/5-2-1.分专业毕业综合训练情况.xlsx");
			rapidHelper.processFirstSheetStream(in);
			
			int excelColumns = rapidHelper.getColumns();
			int excelLines = rapidHelper.getLines();
			
			String[][] data = new String[excelLines][excelColumns];
			ArrayList all = rapidHelper.getAll();
			for(int i=0; i<excelLines; i++){
				ArrayList line = (ArrayList)all.get(i);
				for(int j=0; j<excelColumns; j++){
					String cell = (String)line.get(j);
					data[i][j] = cell;
				}
			}
			
			rapidHelper.refresh();
			
			test = helper.is_format_right(data, 73);
			assertEquals(test, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
