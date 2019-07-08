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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.Rule;
import com.philip.edu.basic.Task;

public class TaskListController extends SelectorComposer<Component>{
	
	private static Logger logger = Logger.getLogger(DatabaseListController.class);
	private FormManager formManager = new FormManager();
	
	@Wire
	private Window tWindow;
	
	@Wire
	private Listbox tasklist;
	
	@Wire
	private Button create;
	
	@Wire
	private Button open;
	
	@Wire
	private Radiogroup taskChose;
	
	private ListModelList<Task> listModel;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Task> tasks = formManager.getTaskList();
		listModel = new ListModelList<Task>(tasks);
		tasklist.setModel(listModel);

	}
	
	@Listen("onClick = #create")
	public void createTask() {		
		Window window2 = (Window) Executions.createComponents("/new_task.zul", null, null);
		
		window2.doModal();
	}
	
	@Listen("onDelete = #tasklist")
	public void doDeleteRule(Event event){
		
		Task task = (Task)event.getData();
		boolean is_success = formManager.deleteTask(task.getId());
		if(is_success){
			listModel.remove(task);
			tasklist.setModel(listModel);
		} else {
			Messagebox.show("删除任务失败！","错误",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	@Listen("onClick = #open")
	public void openTask() {
		if(taskChose.getSelectedItem()==null){Messagebox.show("没有任务被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		Executions.getCurrent().getSession().setAttribute("task_id", taskChose.getSelectedItem().getId());
		
		Executions.sendRedirect("/collection/1");
	}
}
