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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;

public class GroupMaintainController extends SelectorComposer<Component>{
	private static Logger logger = Logger.getLogger(GroupMaintainController.class);
	private static FormManager formManager = new FormManager();
	
	@Wire
	private Window window;
	@Wire
	private Listbox grouplist;
	@Wire
	private Radiogroup groupChose;
	@Wire
	private Button create;
	@Wire
	private Button update;
	@Wire
	private Button delete;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		grouplist.setModel(new ListModelList<Group>(groups));
		
	}
	
	@Listen("onClick = #create")
	public void createGroup() {
		Window window1 = (Window) Executions.createComponents("/new_group.zul", null, null);
		
		window1.doModal();
	}
	
	@Listen("onClick = #update")
	public void editGroup() {
		if(groupChose.getSelectedItem()==null){Messagebox.show("没有分类被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		Window window2 = (Window) Executions.createComponents("/update_group.zul", null, null);
		
		window2.doModal();
	}
	
	@Listen("onClick = #delete")
	public void deleteGroup() {
		if(groupChose.getSelectedItem()==null){Messagebox.show("没有分类被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		String sId = groupChose.getSelectedItem().getId();
		int group_id = Integer.parseInt(sId);
		
		Messagebox.show("你确定要删除这个分类吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
			 public void onEvent(Event e) throws InterruptedException {
				 if(e.getName().equals("onOK")){
					 //Messagebox.show("删除了");
					 formManager.deleteGroup(group_id);
					 List<Group> groups = formManager.getGroups(Constants.USER_ID);
					 grouplist.setModel(new ListModelList<Group>(groups));
				 }else{
					 
				 }
			 }
		});
	}
}
