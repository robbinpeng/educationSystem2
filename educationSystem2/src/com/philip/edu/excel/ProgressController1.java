package com.philip.edu.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
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

public class ProgressController1 extends GenericAutowireComposer<Component> {

	private static Logger logger = Logger.getLogger(ProgressController1.class);
	private static FormManager formManager = new FormManager();
	private static RuleManager ruleManager = new RuleManager();
	private static UploadManager uploadManager = new UploadManager();
	private static DatabaseManager dbManager = new DatabaseManager();
	private static RapidExcelSheetsHelper rapidHelper = new RapidExcelSheetsHelper();
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

		InputStream in = (InputStream) Executions.getCurrent().getArg().get("data");
		// logger.info("in length" + in);
		Integer iTask = (Integer) Executions.getCurrent().getArg().get("task_id");

		Thread t1 = new Thread(
				new UserHandleThread(userprogress, progresswindow, progresslabel, in, iTask.intValue(), close));
		t1.start();
		// in.close();
	}

	class UserHandleThread implements Runnable {

		private Desktop dt;
		private Progressmeter pg;
		private Window win;
		private Label prglb;
		private InputStream in0;
		private int task_id0;
		private Button button;

		public UserHandleThread(Progressmeter watchpg, Window window, Label lb, InputStream in, int task_id,
				Button close) {
			pg = watchpg;
			dt = watchpg.getDesktop();
			win = window;
			prglb = lb;
			in0 = in;
			task_id0 = task_id;
			button = close;
		}

		public void run() {
			int p = 10000;
			int iStep = 0;
			String sStep = "0";
			String sMessage = "";
			MessageInfo message = new MessageInfo();
			ArrayList messageList = new ArrayList();
			ArrayList file = null;
			boolean isSuccess = false;
			boolean isAllSuccess = true;
			int sheet_num = 0;
			double percent = 0;
			int excelLines = 0;
			int excelColumns = 0;
			String name = "";
			int iPro;

			// logger.info("star run. 1");

			try {
				rapidHelper.processAllSheets(in0);
				file = rapidHelper.getFile();

				sheet_num = file.size();

				logger.info("start to do process");
				percent = new Double(95) / new Double(sheet_num);

				Executions.activate(dt);
				pg.setValue(5);
				prglb.setValue("已完成" + 5 + "%……");
				Executions.deactivate(dt);
				Thread.sleep(2);

				for (int k = 0; k < sheet_num; k++) {
					// Sheet sheet = wb.getSheetAt(k);
					try {
						ArrayList sheet = (ArrayList) file.get(k);
						name = (String) sheet.get(0);
						Form form = formManager.getFormByBusinessName(name);

						Integer iLines = (Integer) sheet.get(1);
						Integer iColumns = (Integer) sheet.get(2);

						if (form == null) {
							isAllSuccess = false;
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (k + 1) + "页的表名[" + name + "]不正确，无法导入相关的数据！");
							Executions.activate(dt);
							iPro = 5 + (int) (percent * (k + 1));
							pg.setValue(iPro);
							prglb.setValue("已完成" + iPro + "%……");
							Executions.deactivate(dt);
							Thread.sleep(2);
							continue;
						}

						//logger.info("process 1.");

						ArrayList data_list = (ArrayList) sheet.get(3);
						// logger.info("excelColumns:" + excelColumns);
						int x = 0;
						for (x = 0; x < iLines.intValue(); x++) {
							ArrayList line = (ArrayList) data_list.get(x);
							// logger.info("line.size()=" + line.size() + ",
							// excelColumns=" + excelColumns);
							if (line.size() != iColumns)
								break;
						}
						excelLines = x;
						excelColumns = iColumns.intValue();
						// logger.info("get 2.");

						String[][] data = new String[excelLines][excelColumns];
						for (int i = 0; i < excelLines; i++) {
							ArrayList line = (ArrayList) data_list.get(i);
							for (int j = 0; j < excelColumns; j++) {
								String cell = (String) line.get(j);
								data[i][j] = cell;
							}
						}

						// 1.5. Check filed length:
						MessageInfo tempMessage = ruleManager.isLengthRight(form.getId(), data);
						if(tempMessage.getMessage_type()==Constants.RULECHECK_MESSAGE_SUCCESS){}
						else{
							isAllSuccess = false;
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							ArrayList mlistTemp = tempMessage.getMessage_info();
							messageList.add("第" + (k + 1) + "页["+ name +"]的字段长度超出系统要求：");
							for(int y=0; y<mlistTemp.size(); y++){
								messageList.add(mlistTemp.get(y));
							}
							messageList.add("\n");
							Executions.activate(dt);
							iPro = 5 + (int) (percent * (k + 1));
							pg.setValue(iPro);
							prglb.setValue("已完成" + iPro + "%……");
							Executions.deactivate(dt);
							Thread.sleep(2);
							continue;
						}
						
						// 2.check columns the same:
						boolean is_right = ruleManager.formatCheck(form.getId(), data);

						if (!is_right) {
							isAllSuccess = false;
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (k + 1) + "页["+ name +"]的列数或列标题与系统不一致，无法导入相关的数据！");
							Executions.activate(dt);
							iPro = 5 + (int) (percent * (k + 1)); 
							pg.setValue(iPro);
							prglb.setValue("已完成" + iPro + "%……");
							Executions.deactivate(dt);
							Thread.sleep(2);
							continue;
						}

						// 3.save data into database;
						boolean tempSuccess = uploadManager.uploadData(data, form.getId(), Constants.USER_ID, task_id0);
						//logger.info("form_id:" + form.getId() + "task_id:" + task_id0);

						//logger.info("get here.");
						Executions.activate(dt);
						iPro = 5 + (int) (percent * (k + 1));
						pg.setValue(iPro);
						prglb.setValue("已完成" + iPro + "%……");
						Executions.deactivate(dt);
						Thread.sleep(2);

						if (tempSuccess) {
							isSuccess = uploadManager.uploadUpdate(form.getId(), task_id0);
							if (isSuccess) {

							} else {
								// Executions.activate(dt);
								// win.detach();
								// Messagebox.show("更新上传数据时出错，请联系管理员！", "错误",
								// Messagebox.OK, Messagebox.ERROR);
								// Executions.deactivate(dt);
								isAllSuccess = false;
								message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
								messageList.add("第" + (k + 1) + "张表[" + name + "]导入时系统故障，没有导入成功");
								Executions.activate(dt);
								iPro = 5 + (int) (percent * (k + 1));
								pg.setValue(iPro);
								prglb.setValue("已完成" + iPro + "%……");
								Executions.deactivate(dt);
								Thread.sleep(2);
							}
						} else {
							isAllSuccess = false;
							message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
							messageList.add("第" + (k + 1) + "张表[" + name + "]导入时有错，没有导入成功");
							Executions.activate(dt);
							iPro = 5 + (int) (percent * (k + 1));
							pg.setValue(iPro);
							prglb.setValue("已完成" + iPro + "%……");
							Executions.deactivate(dt);
							Thread.sleep(2);
						}
					} catch (Exception e) {
						logger.info("导入["+name+"]表时出错，出错内容:" );
						logger.info(e.getMessage());
						//logger.info(e.getStackTrace());
						isAllSuccess = false;
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (k + 1) + "张表[" + name + "]导入时有错，没有导入成功");
						Executions.activate(dt);
						iPro = 5 + (int) (percent * (k + 1));
						pg.setValue(iPro);
						prglb.setValue("已完成" + iPro + "%……");
						Executions.deactivate(dt);
						Thread.sleep(2);
					}
				}

				if (isAllSuccess) {
					Executions.activate(dt);
					pg.setValue(100);
					prglb.setValue("全部表格都上传成功！");
					Executions.deactivate(dt);
					Thread.sleep(2);

					// Messagebox.show("上传成功！","信息",Messagebox.OK,Messagebox.INFORMATION);
					Executions.activate(dt);
					win.detach();
					Executions.deactivate(dt);
					// Events.postEvent(new Event("onClose",
					// progresswindow));
				} else {
					// show error message:
					message.setMessage_info(messageList);
					if (message.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {
					} else {
						sMessage += "以下表格在导入的过程中发生错误：\n";
						ArrayList al = message.getMessage_info();
						if (al.size() != 0) {
							for (int i = 0; i < al.size(); i++) {
								// System.out.println(al.get(i).toString());
								sMessage += al.get(i).toString() + "\n";
							}
							sMessage += "\n";
						}
					}

					Executions.activate(dt);
					win.detach();
					HashMap map = new HashMap();
					map.put("message", sMessage);
					Window window1 = (Window) Executions.createComponents("/upload_log.zul", null, map);

					window1.doModal();
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
