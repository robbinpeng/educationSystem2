package com.philip.edu.rule;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.philip.edu.basic.Constants;

public class ExcelHelperTest {

	@Test
	public void testIs_format_right() {
		boolean test = true;
		ExcelHelper helper = new ExcelHelper();
		FileInputStream in = null;
		SXSSFWorkbook wb = null;
		try {
			in = new FileInputStream("D:/Develop/education/test/1-11.xls");
			OPCPackage pkg = OPCPackage.open(in);
			XSSFWorkbook xssfwb = new XSSFWorkbook(pkg);
			wb = new SXSSFWorkbook(xssfwb, 100);
			
			//test = helper.is_format_right(wb, Constants.FORM_ID);
			//assertEquals(test, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
