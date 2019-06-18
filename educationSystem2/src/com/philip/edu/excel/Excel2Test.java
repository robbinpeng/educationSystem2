package com.philip.edu.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class Excel2Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fileOut = new FileOutputStream("/WebContent/template/test.xls");
			
			Workbook wb = new HSSFWorkbook();
			
			wb.createSheet("≤‚ ‘“≥");
			
			wb.write(fileOut);
			
			System.out.println("success!");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean saveExcel(String path){
		boolean isSuccess = false;
		
		try {
			FileOutputStream fileOut = new FileOutputStream(path + "/template/test.xls");
			
			Workbook wb = new HSSFWorkbook();
			
			wb.createSheet("≤‚ ‘“≥");
			
			wb.write(fileOut);
			
			System.out.println("success!");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}

}
