package com.philip.edu.database;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormStatus;

public class TableDAOTest {

	private static TableDAO dao = new TableDAO();
	@Test
	public void testIsTableExsits() {
		boolean test = false;
		
		test = dao.isTableExsits("tbl_form");
		assertEquals(test, true);
		
		test = dao.isTableExsits("1_tbl_XXGK");
		assertEquals(test, false);
		
	}
	
	@Test
	public void testCreateTable() {
		Form form = new Form();
		
		form.setUser_id(Constants.USER_ID);
		form.setTbl_name("ZYJBQK");
		form.setBus_name("表1-5-1 专业基本情况");
		form.setPhsic_name("TBL_ZYJBQK");
		form.setStats_time(Constants.V_TYPE_STAT_TIME_POINT);
		form.setDisplay_method(Constants.V_DISPLAY_MUTIPLE_TEXTBOX);
		form.setForm_type(Constants.V_FORM_TYPE_HIGHBASIC_REPORT);
		form.setIs_null('N');
		form.setCreate_time(new Date());
		form.setMemo("do a test");
		
		FormStatus status = new FormStatus();
		status.setForm(form);
		status.setStatus(Constants.STATUS_CREATED);
		status.setUser_id(Constants.USER_ID);
		status.setUpdate_time(new Date());
		
		form.setStatus(status);
		
		//boolean test = dao.createTable(form);
		//assertEquals(test, true);
	}

	@Test
	public void testDeleteTable() {
		
		boolean test = dao.deleteTable(10);
		assertEquals(test, true);
	}
	
	@Test
	public void testIsFieldExists() {
		boolean test = false;
		
		test = dao.isFieldExsits("tbl_zyjbqk", "TJSJ");
		assertEquals(test, true);
		
		test = dao.isFieldExsits("tbl_zyjbqk", "XXGK");
		assertEquals(test, false);
	}
	
	@Test
	public void testAddField(){
		boolean test = false;
		
		FormField field = new FormField();
		field.setPhysic_name("XNZYDM");
		field.setBus_name("校内专业代码");
		field.setIs_required('Y');
		field.setSequence(1);
		field.setData_type(Constants.V_DATA_TYPE_STRING);
		field.setLength(50);
		field.setDis_method(Constants.V_DISPLAY_RICH_TEXT);
		field.setIs_report('Y');
		field.setIs_hidden('N');
		field.setCompute(Constants.V_FIELD_TYPE_COMPUTE_COLUMN);
		field.setForm_id(9);
		
		//test = dao.addField("tbl_zyjbqk", field);
		//assertEquals(test, true);
	}
	
	@Test
	public void testDeleteField(){
		//boolean test = dao.deleteField("tbl_zyjbqk", 32);
		//assertEquals(test, true);
	}
}
