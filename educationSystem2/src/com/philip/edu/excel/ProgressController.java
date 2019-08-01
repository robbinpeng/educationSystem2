package com.philip.edu.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.lang.Threads;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.UploadInfo;
import com.philip.edu.database.DatabaseManager;
import com.philip.edu.rule.MessageInfo;
import com.philip.edu.rule.RuleManager;
import com.philip.edu.upload.UploadManager;

public class ProgressController extends GenericAutowireComposer<Component>{
	
	private static Logger logger = Logger.getLogger(ProgressController.class);
	private static FormManager formManager = new FormManager();
	private static RuleManager ruleManager = new RuleManager();
	private static UploadManager uploadManager = new UploadManager();
	private static DatabaseManager dbManager = new DatabaseManager();
	private static RapidExcelHelper rapidHelper = new RapidExcelHelper();
	private static RuleManager engine = new RuleManager();
	
	@Wire
	private Window progresswindow;
	@Wire
    private Progressmeter userprogress;
	@Wire
    private Label progresslabel;
	@Wire
	private Button close;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        // TODO Auto-generated method stub
        super.doAfterCompose(comp);
        
        // 启动进度条
        logger.info("start to do progress.1");
        desktop.enableServerPush(true);
        
        InputStream in = (InputStream)Executions.getCurrent().getArg().get("data");
        //logger.info("in length" + in);
        UploadInfo form = (UploadInfo)Executions.getCurrent().getArg().get("form");
        Integer iTask = (Integer)Executions.getCurrent().getArg().get("task_id");
        Integer iGroup = (Integer)Executions.getCurrent().getArg().get("group_id");
        
        Thread t1 = new Thread(new UserHandleThread(userprogress, progresswindow, progresslabel, in, form, iTask.intValue(), iGroup.intValue(), close));
        t1.start();
    }
    
    class UserHandleThread implements Runnable {
        
        private Desktop dt;
        private Progressmeter pg;
        private Window win;
        private Label prglb;
        private InputStream in0;
        private UploadInfo form0;
        private int task_id0;
        private int group_id0;
        private Button button;

        public UserHandleThread(Progressmeter watchpg, Window window, Label lb, InputStream in, UploadInfo form, int task_id, int group_id, Button close) {
        	pg = watchpg;
            dt = watchpg.getDesktop();
            win = window;
            prglb = lb;
            in0 = in;
            form0 = form;
            task_id0 = task_id;
            group_id0 = group_id;
            button = close;
        }
        
        public void run(){
        	int p = 10000;
        	int iStep = 0;
        	String sStep = "0";
			String sMessage = "";
        	MessageInfo message = new MessageInfo();
        	boolean isSuccess = false;
        	//logger.info("star run. 1");
        	
        	try {
        		
				// 2.check format is right:
        		//logger.info("rapidHelper:" + rapidHelper);
        		//logger.info("InputStream:" + in);
				rapidHelper.processFirstSheetStream(in0);
				int excelColumns = rapidHelper.getColumns();
				int lines = rapidHelper.getLines();
				int excelLines = 0;
				
				//logger.info("excelColumns:" + excelColumns);
				
				ArrayList all = rapidHelper.getAll();
				int x=0;
				for(x=0; x<lines; x++){
					ArrayList line = (ArrayList)all.get(x);
					//logger.info("line.size()=" + line.size() + ", excelColumns=" + excelColumns);
					if(line.size()!=excelColumns)break;
				}
				excelLines = x;
				//logger.info("get 2.");
				
				String[][] data = new String[excelLines][excelColumns];
				for(int i=0; i<excelLines; i++){
					ArrayList line = (ArrayList)all.get(i);
					for(int j=0; j<excelColumns; j++){
						String cell = (String)line.get(j);
						data[i][j] = cell;
					}
				}	
				
				//logger.info("get 3.");
				
				rapidHelper.refresh();
				
				Executions.activate(dt);
                pg.setValue(5);
                prglb.setValue("已完成5%……");
                Executions.deactivate(dt);
                Thread.sleep(5);
				
				boolean format_right = ruleManager.formatCheck(form0.getId(), data);
				if (format_right) {
					boolean checkpass = true;
					// 2.5 check text format:
					MessageInfo m = engine.textFormatCheck(form0.getId(), data);
					
					//logger.info("get 4.");
					
					Executions.activate(dt);
	                pg.setValue(10);
	                prglb.setValue("已完成10%……");
	                Executions.deactivate(dt);
	                Thread.sleep(5);
	                
	                //logger.info("get 5.");
	                
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
					
					//logger.info("get 6.");
					
					//2.6 check dictionary:
					m = engine.DictionCheck(form0.getId(), data);
					
					Executions.activate(dt);
	                pg.setValue(20);
	                prglb.setValue("已完成20%……");
	                Executions.deactivate(dt);
	                Thread.sleep(5);
	                
	                logger.info("get 7.");
	                
					if(m.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){
						
					} else {
						sMessage += "\n 上传表格中有字段不在数据字典中：   \n";
						ArrayList al = m.getMessage_info();
						if(al.size()!=0){
							checkpass = false;
							for (int j=0; j<al.size(); j++){
								sMessage += (String)al.get(j) + "\n";
							}
						}
					}
					//logger.info("get 7.");
					// 3.check the rules:
					ArrayList list = engine.rulesCheck(form0.getId(), data, task_id0);
					
					Executions.activate(dt);
	                pg.setValue(80);
	                prglb.setValue("已完成80%……");
	                Executions.deactivate(dt);
	                Thread.sleep(5);
	                
	                //logger.info("get 8.");
	                
					for (int j = 0; j < list.size(); j++) {
						message = (MessageInfo) list.get(j);
						if (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
						} else {
							sMessage += "\n 规则校验不通过： \n";
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
						boolean tempSuccess = uploadManager.uploadData(data, form0.getId(), Constants.USER_ID, task_id0);
						
						Executions.activate(dt);
		                pg.setValue(90);
		                prglb.setValue("已完成90%……");
		                Executions.deactivate(dt);
		                Thread.sleep(5);
						
						if (tempSuccess) {
							isSuccess = uploadManager.uploadUpdate(form0.getId(), task_id0);
							if (isSuccess) {
								
								Executions.activate(dt);
				                pg.setValue(100);
				                prglb.setValue("上传成功！");
				                Executions.deactivate(dt);
				                Thread.sleep(5);
				                
								//Messagebox.show("上传成功！","信息",Messagebox.OK,Messagebox.INFORMATION);
				                Executions.activate(dt);
				                win.detach();
				                
								List<UploadInfo> forms = formManager.getDataCollectionByGroup(task_id0, group_id0);
								Listbox formlist = (Listbox) Path.getComponent("/dbWindow/formlist");
								formlist.setModel(new ListModelList<UploadInfo>(forms));
								
				                Executions.deactivate(dt);
				                //Events.postEvent(new Event("onClose", progresswindow));
								

							} else {
								Executions.activate(dt);
				                win.detach();
				                Messagebox.show("更新上传数据时出错，请联系管理员！","错误",Messagebox.OK,Messagebox.ERROR);
				                Executions.deactivate(dt);
							}
						} else {
							Executions.activate(dt);
			                win.detach();
			                Messagebox.show("上传表格过程中出错,请联系管理员！","错误",Messagebox.OK,Messagebox.ERROR);
			                Executions.deactivate(dt);
						
						}
					} else {
						Executions.activate(dt);
		                win.detach();
						HashMap map = new HashMap();
						map.put("message", sMessage);
						Window window1 = (Window) Executions.createComponents("/rule_check.zul", null, map);

						window1.doModal();
		                Executions.deactivate(dt);
					}
				} else {
					Executions.activate(dt);
	                win.detach();
	                Messagebox.show("上传的表格与要求不符！请检查列数和列标题是否正确！","错误",Messagebox.OK,Messagebox.ERROR);
	                Executions.deactivate(dt);
				}
				
			} catch (EncryptedDocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			} finally {
				desktop.enableServerPush(false);
			}

        }
        
    }
   
}
