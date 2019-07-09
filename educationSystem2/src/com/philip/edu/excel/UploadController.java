package com.philip.edu.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zel.ImportHandler;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.UploadInfo;
import com.philip.edu.database.DatabaseManager;
import com.philip.edu.rule.MessageInfo;
import com.philip.edu.rule.RuleManager;
import com.philip.edu.upload.UploadManager;

import org.apache.log4j.Logger;

public class UploadController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UploadController.class);
	private static FormManager formManager = new FormManager();
	private static RuleManager ruleManager = new RuleManager();
	private static UploadManager uploadManager = new UploadManager();
	private static DatabaseManager dbManager = new DatabaseManager();
	
	@Wire
	private Window dbWindow;

	@Wire
	private Listbox formlist;
	
	@Wire
	private Grid groupList;
	
	@Wire
	private Button create;
	
	@Wire
	private Button update;
	
	@Wire
	private Button delete;
	
	@Wire
	private Button design;
	
	@Wire
	private Radiogroup tblChose;
	
	private int group_id;
	
	private int task_id;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		groupList.setModel(new ListModelList<Group>(groups));
		
		String sGroup = Executions.getCurrent().getParameter("group_id");
		int groupid = Integer.parseInt(sGroup);
		group_id = groupid;
		
		String sTask = (String)Executions.getCurrent().getSession().getAttribute("task_id");
		task_id = Integer.parseInt(sTask);
		
		List<UploadInfo> forms = formManager.getDataCollectionByGroup(task_id, groupid);
		formlist.setModel(new ListModelList<UploadInfo>(forms));

		// formlist.getChildren();
	}
	
	@Listen("onClick = #create")
	public void createTable() {
		Window window2 = (Window) Executions.createComponents("/new_table.zul", null, null);
		
		window2.doModal();
	}
	
	@Listen("onClick = #update")
	public void editTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("没有数据表被选中！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		Window window3 = (Window) Executions.createComponents("/update_table.zul", null, null);
		
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
	
	/*@Listen("onClick = #design")
	public void designTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("没有数据表被选中！");return;}
		
		String sID = tblChose.getSelectedItem().getId();
		HashMap map = new HashMap();
		map.put("form_id", sID);
		
		Window window4 = (Window) Executions.createComponents("/data_design.zul", null, map);
		
		window4.doModal();
	}*/

	@Listen("onUpload = #formlist")
	public void uploadExcel(Event event) {
		UploadInfo form = (UploadInfo) event.getData();
		boolean isSuccess = false;

		UploadEvent ue = null;
		if (event instanceof UploadEvent) {
			ue = (UploadEvent) event;
		} else if (event instanceof ForwardEvent) {
			ue = (UploadEvent) ((ForwardEvent) event).getOrigin();
		}

		Workbook wb = null;
		ArrayList list = null;

		RuleManager engine = new RuleManager();
		MessageInfo message = null;
		String sMessage = "";
		// 1.check it's excel;
		Media media = ue.getMedia();
		if ("application/vnd.ms-excel".equals(media.getContentType())) {
			try {
				// 2.check format is right:
				wb = WorkbookFactory.create(media.getStreamData());
				boolean format_right = ruleManager.formatCheck(form.getId(), wb);
				if (format_right) {
					boolean checkpass = true;
					// 2.5 check text format:
					MessageInfo m = engine.textFormatCheck(form.getId(), wb);
					if(m.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS){
						
					} else {
						sMessage += "上传表格中有数据类型、格式错误： \n";
						ArrayList al = m.getMessage_info();
						if(al.size()!=0){
							checkpass = false;
							for (int i = 0; i<al.size(); i++){
								sMessage += al.get(i).toString() + "\n";
							}
						}
					}
					
					//2.6 check dictionary:
					m = engine.DictionCheck(form.getId(), wb);
					if(m.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){
						
					} else {
						sMessage += "上传表格中有字段不在数据字典中：   \n";
						ArrayList al = m.getMessage_info();
						if(al.size()!=0){
							checkpass = false;
							for (int j=0; j<al.size(); j++){
								sMessage += (String)al.get(j) + "\n";
							}
						}
					}
					// 3.check the rules:
					list = engine.rulesCheck(form.getId(), wb);
					for (int j = 0; j < list.size(); j++) {
						message = (MessageInfo) list.get(j);
						if (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
						} else {
							ArrayList al = message.getMessage_info();
							if (al.size() != 0) {
								checkpass = false;
								sMessage += message.getFail_information() + ":\n";
								for (int i = 0; i < al.size(); i++) {
									System.out.println(al.get(i).toString());
									sMessage += al.get(i).toString() + "\n";
								}

								sMessage += "\n";
							}
						}
					}
					// create a window programmatically and use it as a modal
					// dialog.
					if (checkpass) {
						// 3.save data into database;
						boolean tempSuccess = uploadManager.uploadData(wb, form.getId(), Constants.USER_ID, task_id);
						if (tempSuccess) {
							isSuccess = uploadManager.uploadUpdate(form.getId(), task_id);
							if (isSuccess) {
								Messagebox.show("上传成功！","信息",Messagebox.OK,Messagebox.INFORMATION);

								List<UploadInfo> forms = formManager.getDataCollectionByGroup(task_id, group_id);
								formlist.setModel(new ListModelList<UploadInfo>(forms));
							} else {
								Messagebox.show("更新上传数据时出错，请联系管理员！","错误",Messagebox.OK,Messagebox.ERROR);
							}
						} else {
							Messagebox.show("上传表格过程中出错!","错误",Messagebox.OK,Messagebox.ERROR);
						}
					} else {
						HashMap map = new HashMap();
						map.put("message", sMessage);
						Window window1 = (Window) Executions.createComponents("/rule_check.zul", null, map);

						window1.doModal();
					}
				} else {
					Messagebox.show("上传的表格与要求不符！","错误",Messagebox.OK,Messagebox.ERROR);
				}
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			}
		} else {
			Messagebox.show("您上传的不是excel表格！","错误",Messagebox.OK,Messagebox.ERROR);
		}
	}

	@Listen("onRollback = #formlist")
	public void rollback(Event event) {
		UploadInfo form = (UploadInfo) event.getData();
		Form form1 = formManager.getFormById(form.getId());
		int lines = uploadManager.rollbackData(form1, task_id);
		boolean success = false;
		if(lines!=0){
			success = uploadManager.updateRollback(form.getId(), task_id);
			if(success){
				List<UploadInfo> forms = formManager.getDataCollectionByGroup(task_id, group_id);
				formlist.setModel(new ListModelList<UploadInfo>(forms));
			}else{
				Messagebox.show("退回错误！","错误",Messagebox.OK,Messagebox.ERROR);
			}
		}else{
			Messagebox.show("退回错误！","错误",Messagebox.OK,Messagebox.ERROR);
		}
	}

}
