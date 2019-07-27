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
	
	public ArrayList getTableDataByPage(ArrayList fields, String tbl_name, int page){
		return dao.getTableDataByPage(fields, tbl_name, page);
	}
	
	public ArrayList searchDataByPage(ArrayList fields, String tbl_name, int page, String search){
		return dao.searchDataByPage(fields, tbl_name, page, search);
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

