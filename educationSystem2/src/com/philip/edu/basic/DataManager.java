package com.philip.edu.basic;

import java.util.ArrayList;

public class DataManager {
	
	private static FormManager formManager = new FormManager();
	private static DataDAO dao = new DataDAO();
	
	public ArrayList getTableData(int form_id){
		Form form = formManager.getFormById(form_id);
		ArrayList fields = formManager.getFormFields(form_id);
		
		return dao.getTableData(fields, form.getPhsic_name());
	}
}
