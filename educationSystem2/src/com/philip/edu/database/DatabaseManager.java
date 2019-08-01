package com.philip.edu.database;

import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;

public class DatabaseManager {
	private static TableDAO dao = new TableDAO();
	
	public boolean isTableExsits(String physic_name){
		return dao.isTableExsits(physic_name);
	}
	
	public boolean createTable(Form form){
		return dao.createTable(form);
	}
	
	public boolean updateTable(Form form){
		return dao.updateTable(form);
	}
	
	public boolean deleteTable(int form_id){
		return dao.deleteTable(form_id);
	}
	
	public boolean isFieldExsits(String table_name, String field_name){
		return dao.isFieldExsits(table_name, field_name);
	}
	
	public boolean addField(String table_name, FormField field){
		return dao.addField(table_name, field);
	}
	
	public boolean deleteField(String table_name, int field_id){
		return dao.deleteField(table_name, field_id);
	}
	
	public boolean updateField(FormField field){
		return dao.updateField(field);
	}
	
	public boolean alterLength(String table_name, int field_id, int length){
		return dao.alterLength(table_name, field_id, length);
	}
}
