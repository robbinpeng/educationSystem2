package com.philip.edu.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
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
import com.philip.edu.rule.MessageInfo;

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
	public void doDeleteTask(Event event){
		
		Messagebox.show("你确定要删除这项任务吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
			 public void onEvent(Event e) throws InterruptedException {
				 if(e.getName().equals("onOK")){
					 Task task = (Task)event.getData();
						boolean is_success = formManager.deleteTask(task.getId());
						if(is_success){
							listModel.remove(task);
							tasklist.setModel(listModel);
						} else {
							Messagebox.show("删除任务失败！","错误",Messagebox.OK,Messagebox.ERROR);
						}
				 }else{
					 
				 }
			 }
		});
		
		
	}
	
	@Listen("onClick = #open")
	public void openTask() {
		if(taskChose.getSelectedItem()==null){Messagebox.show("没有任务被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		Executions.getCurrent().getSession().setAttribute("task_id", taskChose.getSelectedItem().getId());
		
		Executions.sendRedirect("/collection/1");
	}
	
	@Listen("onBatchUpload = #tasklist")
	public void batchUpload(Event event){
		//UploadInfo form = (UploadInfo) event.getData();
		logger.info("entering batch upload 1.");
		Task task = (Task) event.getData();
		boolean isSuccess = false;

		UploadEvent ue = null;
		if (event instanceof UploadEvent) {
			ue = (UploadEvent) event;
		} else if (event instanceof ForwardEvent) {
			ue = (UploadEvent) ((ForwardEvent) event).getOrigin();
		}
		
		logger.info("entering batch upload 2.");

		ArrayList list = null;

		MessageInfo message = null;
		String sMessage = "";
		// 1.check it's excel;
		Media media = ue.getMedia();
		if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(media.getContentType())) {
			logger.info("entering batch upload 3.");
			InputStream in = media.getStreamData();
			HashMap data = new HashMap();
			data.put("data", in);
			data.put("task_id", task.getId());
			
			Window progressWindow = (Window)Executions.createComponents("/progress1.zul", null, data);
			progressWindow.doModal();
		} else {
			Messagebox.show("您上传的不是更新的Excel表格！","错误",Messagebox.OK,Messagebox.ERROR);
		}
	}
}
