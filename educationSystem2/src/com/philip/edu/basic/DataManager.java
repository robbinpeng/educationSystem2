package com.philip.edu.basic;

import java.util.ArrayList;

public class DataManager {
	
	private static FormManager formManager = new FormManager();
	private static DataDAO dao = new DataDAO();
	
	public ArrayList getTableData(int form_id){
		Form form = formManager.getFormById(form_id);
		ArrayList fields = formManager.getFormFields(form_id);
		
		if(fields==null || fields.size()==0)return null;
		
		return dao.getTableData(fields, form.getPhsic_name());
	}
	
	public ArrayList getTableDataByPage(ArrayList fields, String tbl_name, int page, boolean show_school, String chose_school, String chose_TJSJ){
		return dao.getTableDataByPage(fields, tbl_name, page, show_school, chose_school, chose_TJSJ);
	}
	
	public int getTableDataNumber(String tbl_name, boolean show_school, String chose_school, String chose_TJSJ){
		return dao.getTableDataNumber(tbl_name, show_school, chose_school, chose_TJSJ);
	}
	
	public int getSearchDataNumber(ArrayList fields, String tbl_name, String search, boolean show_school, String chose_school, String chose_TJSJ){
		return dao.getSearchDataNumber(fields, tbl_name, search, show_school, chose_school, chose_TJSJ);
	}
	
	public ArrayList searchDataByPage(ArrayList fields, String tbl_name, int page, String search, boolean show_school, String chose_school, String chose_TJSJ){
		return dao.searchDataByPage(fields, tbl_name, page, search, show_school, chose_school, chose_TJSJ);
	}
	
	public boolean deleteRecords(String table_name, ArrayList ids){
		return dao.deleteRecords(table_name, ids);
	}
	
	public boolean createRecord(Form form, ArrayList record, int task_id){
		return dao.createRecord(form, record, task_id);
	}
	
	public ArrayList getTableDataById(ArrayList fields, String tbl_name, int id){
		return dao.getTableDataById(fields, tbl_name, id);
	}
	
	public boolean updateRecord(Form form, ArrayList record, int task_id, int id){
		return dao.updateRecord(form, record, task_id,id);
	}
}

