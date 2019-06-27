package com.philip.edu.action;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.database.DatabaseManager;
import com.philip.edu.excel.UploadController;

public class FormTableController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(FormTableController.class);
	private FormManager formManager = new FormManager();
	private static DatabaseManager dbManager = new DatabaseManager();
	
	@Wire
	private Window fWindow;
	@Wire
	private Listbox fieldList;
	@Wire
	private Button create;
	@Wire
	private Button update;
	@Wire
	private Button delete;
	@Wire
	private Radiogroup fieldChose;
	private int store_id;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		String sForm = Executions.getCurrent().getParameter("form_id");
		int form_id = Integer.parseInt(sForm);
		store_id = form_id;
		List<FormField> fields = formManager.getFormFields(form_id);
		fieldList.setModel(new ListModelList<FormField>(fields));
	}
	
	@Listen("onClick = #create")
	public void createTable() {
		HashMap map = new HashMap();
		map.put("form_id", store_id);
		
		Window window2 = (Window) Executions.createComponents("/new_field.zul", null, map);
		
		window2.doModal();
	}
	
	@Listen("onClick = #update")
	public void editTable() {
		if(fieldChose.getSelectedItem()==null){Messagebox.show("没有字段被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		HashMap map = new HashMap();
		map.put("form_id", store_id);
		
		Window window3 = (Window) Executions.createComponents("/update_field.zul", null, map);
		
		window3.doModal();
	}
	
	@Listen("onClick = #delete")
	public void deleteField() {
		if(fieldChose.getSelectedItem()==null){Messagebox.show("没有字段被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		String sId = fieldChose.getSelectedItem().getId();
		int field_id = Integer.parseInt(sId);
		
		Messagebox.show("你确定要删除这个字段吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
			 public void onEvent(Event e) throws InterruptedException {
				 if(e.getName().equals("onOK")){
					 //Messagebox.show("删除了");
					 Form form = formManager.getFormById(store_id);
					 dbManager.deleteField(form.getPhsic_name(), field_id);
					 List<FormField> fields = formManager.getFormFields(store_id);
					 fieldList.setModel(new ListModelList<FormField>(fields));
				 }else{
					 
				 }
			 }
		});
	}
}
