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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.database.DatabaseManager;
import com.philip.edu.excel.UploadController;
import com.philip.edu.rule.RuleManager;
import com.philip.edu.upload.UploadManager;

public class DatabaseListController extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(DatabaseListController.class);
	private static FormManager formManager = new FormManager();
	private static RuleManager ruleManager = new RuleManager();
	private static UploadManager uploadManager = new UploadManager();
	private static DatabaseManager dbManager = new DatabaseManager();

	@Wire
	private Window dbWindow;
	
	@Wire
	private Listbox formlist;
	
	@Wire
	private Button create;
	
	@Wire
	private Button update;
	
	@Wire
	private Button delete;
	
	@Wire
	private Button design;
	
	@Wire
	private Button open;
	
	@Wire
	private Radiogroup tblChose;
	
	@Wire
	private Grid groupList;
	
	private int group_id;
	
	@Listen("onClick = #create")
	public void createTable() {
		HashMap map = new HashMap();
		map.put("group_id", ""+group_id);
		
		Window window2 = (Window) Executions.createComponents("/new_table.zul", null, map);
		
		window2.doModal();
	}
	
	@Listen("onClick = #update")
	public void editTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("没有数据表被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		HashMap map = new HashMap();
		map.put("group_id", ""+group_id);
		
		Window window3 = (Window) Executions.createComponents("/update_table.zul", null, map);
		
		window3.doModal();
	}
	
	@Listen("onClick = #delete")
	public void deleteTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("没有数据表被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		String sId = tblChose.getSelectedItem().getId();
		int form_id = Integer.parseInt(sId);
		
		Messagebox.show("你确定要删除这张表吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
			 public void onEvent(Event e) throws InterruptedException {
				 if(e.getName().equals("onOK")){
					 //Messagebox.show("删除了");
					 dbManager.deleteTable(form_id);
					 List<Form> forms = formManager.getFormsByGroup(group_id);
					 formlist.setModel(new ListModelList<Form>(forms));
				 }else{
					 
				 }
			 }
		});
	}
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		groupList.setModel(new ListModelList<Group>(groups));
		
		String sGroup = Executions.getCurrent().getParameter("group_id");
		if(sGroup==null)sGroup = "1";
		group_id = Integer.parseInt(sGroup);
		
		List<Form> forms = formManager.getFormsByGroup(group_id);
		formlist.setModel(new ListModelList<Form>(forms));

	}
}
