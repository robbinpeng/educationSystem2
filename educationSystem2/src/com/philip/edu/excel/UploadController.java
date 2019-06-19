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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
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
	private Button create;
	
	@Wire
	private Button update;
	
	@Wire
	private Button delete;
	
	@Wire
	private Radiogroup tblChose;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		List<Form> forms = formManager.getForms(Constants.USER_ID);
		formlist.setModel(new ListModelList<Form>(forms));

		// formlist.getChildren();
	}
	
	@Listen("onClick = #create")
	public void createTable() {
		Window window2 = (Window) Executions.createComponents("/new_table.zul", null, null);
		
		window2.doModal();
	}
	
	@Listen("onClick = #update")
	public void editTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("û�����ݱ���ѡ�У�");return;}
		
		Window window3 = (Window) Executions.createComponents("/update_table.zul", null, null);
		
		window3.doModal();
	}
	
	@Listen("onClick = #delete")
	public void deleteTable() {
		if(tblChose.getSelectedItem()==null){Messagebox.show("û�����ݱ���ѡ�У�");return;}
		
		String sId = tblChose.getSelectedItem().getId();
		int form_id = Integer.parseInt(sId);
		
		Messagebox.show("��ȷ��Ҫɾ�����ű���","ȷ��ɾ��",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
			 public void onEvent(Event e) throws InterruptedException {
				 if(e.getName().equals("onOK")){
					 //Messagebox.show("ɾ����");
					 dbManager.deleteTable(form_id);
					 List<Form> forms = formManager.getForms(Constants.USER_ID);
					 formlist.setModel(new ListModelList<Form>(forms));
				 }else{
					 
				 }
			 }
		});
	}

	@Listen("onUpload = #formlist")
	public void uploadExcel(Event event) {
		Form form = (Form) event.getData();
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
					// 3.check the rules:
					boolean checkpass = true;
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
						boolean tempSuccess = uploadManager.uploadData(wb, form.getId(), Constants.USER_ID);
						if (tempSuccess) {
							isSuccess = uploadManager.uploadUpdate(form.getId());
							if (isSuccess) {
								Messagebox.show("�ϴ��ɹ���");

								List<Form> forms = formManager.getForms(Constants.USER_ID);
								formlist.setModel(new ListModelList<Form>(forms));
							} else {
								Messagebox.show("�����ϴ�����ʱ����������ϵ����Ա��");
							}
						} else {
							Messagebox.show("�ϴ���������г���!");
						}
					} else {
						HashMap map = new HashMap();
						map.put("message", sMessage);
						Window window1 = (Window) Executions.createComponents("/rule_check.zul", null, map);

						window1.doModal();
					}
				} else {
					Messagebox.show("�ϴ��ı�����Ҫ�󲻷���");
				}
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			}
		} else {
			Messagebox.show("���ϴ��Ĳ���excel����");
		}
	}

	@Listen("onRollback = #formlist")
	public void rollback(Event event) {
		Form form = (Form) event.getData();
		int lines = uploadManager.rollbackData(form);
		boolean success = false;
		if(lines!=0){
			success = uploadManager.updateRollback(form.getId());
			if(success){
				List<Form> forms = formManager.getForms(Constants.USER_ID);
				formlist.setModel(new ListModelList<Form>(forms));
			}else{
				Messagebox.show("�˻ش���");
			}
		}else{
			Messagebox.show("�˻ش���");
		}
	}

}