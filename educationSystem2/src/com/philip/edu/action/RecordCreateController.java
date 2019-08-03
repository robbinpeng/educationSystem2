package com.philip.edu.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.util.Template;
//import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.DataManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.UploadInfo;
import com.philip.edu.rule.MessageInfo;
import com.philip.edu.rule.RuleManager;

public class RecordCreateController extends SelectorComposer<Component> {

	private static Logger logger = Logger.getLogger(RecordCreateController.class);
	private static DataManager dataManager = new DataManager();
	private static RuleManager engine = new RuleManager();

	@Wire
	private Listbox lbClient;
	@Wire
	private Button butSave;
	private static FormManager formManager = new FormManager();
	private ArrayList fields;
	private Form form;

	@Wire
	private Window bdlBody;
	@Wire
	private Button closeBtn;

	private ListModelList<FormField> lmlField;

	// 加载页面时方法
	@SuppressWarnings("null")
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);// 关联前端页面控件的必要方法

		Integer sForm = (Integer) Executions.getCurrent().getArg().get("form_id");
		int form_id = sForm.intValue();

		form = formManager.getFormById(form_id);
		fields = formManager.getFormFields(form_id);
		lmlField = new ListModelList(fields);
		lbClient.setTooltiptext("");// 提示功能属性

		lbClient.setModel(lmlField);
	}

	@Listen("onClick = #closeBtn")
	public void closeModal(Event e) {
		logger.info("to close.");
		bdlBody.detach();
	}

	@Listen("onClick = #lbClient")
	public void openSelect(Event event) {
		logger.info("entering open select");
		/*
		 * Button thisButton = null; Event se = event; Component temp =
		 * se.getTarget(); String id = temp.getId(); logger.info("type is:" +
		 * temp); logger.info("id is:" + id); if(temp instanceof Button)
		 * thisButton = (Button)temp; else return;
		 */
		FormField field = (FormField) event.getData();
		if (field == null)
			return;
		int dic_id = field.getDictid();

		String id = field.getDis_method() + "_" + field.getId() + "_" + field.getSequence();
		Button p = (Button) Path.getComponent("/bdlBody/" + id);

		HashMap map = new HashMap();
		map.put("dict_id", dic_id);
		map.put("button_id", id);
		if (p.getLabel().equals("==请选择=="))
			p.setLabel("");
		map.put("chosed", p.getLabel());

		Window window1 = (Window) Executions.createComponents("/multiple_chose.zul", null, map);

		window1.doModal();
		map = null;
	}

	// 保存数据按钮
	@Listen("onClick = #butSave")
	public void saveInfo(Event e) {
		int task_id;
		String sTask = (String) Sessions.getCurrent().getAttribute("task_id");
		if (sTask == null) {
			task_id = Constants.SYSTEM_TASK_ID;
		} else {
			task_id = Integer.parseInt(sTask);
		}

		List<Listitem> list = lbClient.getItems();
		ArrayList record = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			Listitem item = (Listitem) list.get(i);
			Listcell cell = (Listcell) item.getLastChild();
			FormField field = (FormField) fields.get(i);

			DataInfo data = new DataInfo();
			switch (field.getDis_method()) {
			case Constants.V_DISPLAY_SINGLE_TEXTBOX:
				Textbox text = (Textbox) cell.getLastChild();
				if ((text.getValue() == null || "".equals(text.getValue()))) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "字段不能为空", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						data.setKey(field.getPhysic_name());
						data.setValue("");
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(text.getValue());
				}
				break;
			case Constants.V_DISPLAY_MUTIPLE_TEXTBOX:
				Textbox textM = (Textbox) cell.getLastChild();
				if ((textM.getValue() == null || "".equals(textM.getValue()))) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "字段不能为空", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						data.setKey(field.getPhysic_name());
						data.setValue("");
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(textM.getValue());
				}
				break;
			case Constants.V_DISPLAY_DATE_CONTROL:
				Datebox date = (Datebox) cell.getLastChild();
				SimpleDateFormat sdf = null;
				switch (field.getText_format()) {
				case Constants.V_TEXT_FORMAT_DATE_YEAR:
					sdf = new SimpleDateFormat("yyyy");
					break;
				case Constants.V_TEXT_FORMAT_DATE_MONTH:
					sdf = new SimpleDateFormat("yyyy-MM");
					break;
				case Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH:
					sdf = new SimpleDateFormat("yyyyMM");
					break;
				case Constants.V_TEXT_FORMAT_DATE_DAY:
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					break;
				}
				if (date.getValue() == null) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "字段不能为空", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						// data.setKey(field.getPhysic_name());
						// data.setValue(null);
						continue;
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(sdf.format(date.getValue()));
				}
				break;
			case Constants.V_DISPLAY_SINGLE_COMBOBOX:
				Combobox box = (Combobox) cell.getLastChild();
				if ((box.getSelectedItem() == null || "".equals(box.getSelectedItem().getValue()))) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "字段不能为空", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						data.setKey(field.getPhysic_name());
						data.setValue("");
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(box.getSelectedItem().getLabel());
				}
				break;
			case Constants.V_DISPLAY_MULTIPLE_COMBOBOX:
				Button button1 = (Button) cell.getLastChild();
				if ((button1.getLabel() == null || "".equals(button1.getLabel()))) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "您没有选择必选项", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						data.setKey(field.getPhysic_name());
						data.setValue("");
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(button1.getLabel());
					//logger.info(data.getValue());
				}
				break;
			case Constants.V_DISPLAY_UPLOAD_CONTROL:
				Fileupload upload = (Fileupload) cell.getLastChild();
				String path = (String) upload.getAttribute("file_path");
				if (path == null) {
					if (field.getIs_required() == 'Y') {
						Messagebox.show("" + field.getBus_name() + "字段必须上传文件", "错误", Messagebox.OK, Messagebox.ERROR);
						return;
					} else {
						data.setKey(field.getPhysic_name());
						data.setValue("");
					}
				} else {
					data.setKey(field.getPhysic_name());
					data.setValue(path);
				}
				break;
			}
			record.add(data);
		}

		boolean checkpass = true;
		String sMessage = "";
		// 2.5 check text format:
		MessageInfo m = engine.textFormatSingleCheck(form.getId(), record);
		if (m.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {

		} else {
			sMessage += "添加数据中有数据类型、格式错误： \n";
			ArrayList al = m.getMessage_info();
			if (al.size() != 0) {
				checkpass = false;
				for (int i = 0; i < al.size(); i++) {
					sMessage += al.get(i).toString() + "\n";
				}
			}
		}

		// 2.6 check dictionary:
		m = engine.DictionCheckSingleLine(form.getId(), record);
		if (m.getMessage_type() == Constants.RULECHECK_MESSAGE_SUCCESS) {

		} else {
			sMessage += "\n 添加数据中有字段不在数据字典中：   \n";
			ArrayList al = m.getMessage_info();
			if (al.size() != 0) {
				checkpass = false;
				for (int j = 0; j < al.size(); j++) {
					sMessage += (String) al.get(j) + "\n";
				}
			}
		}
		// 3.check the rules:
		ArrayList list_m = engine.rulesCheckSingleLine(form.getId(), record, task_id);
		for (int j = 0; j < list_m.size(); j++) {
			MessageInfo message = (MessageInfo) list_m.get(j);
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
			boolean tempSuccess = dataManager.createRecord(form, record, task_id);
			if (tempSuccess) {
				Messagebox.show("添加记录成功！", "信息", Messagebox.OK, Messagebox.INFORMATION);

				Window pList = (Window) Path.getComponent("/window1");
				bdlBody.detach();
				Executions.getCurrent().sendRedirect("");
			} else {
				Messagebox.show("添加数据时出错，请联系管理员！", "错误", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			HashMap map = new HashMap();
			map.put("message", sMessage);
			Window window1 = (Window) Executions.createComponents("/rule_check.zul", null, map);

			window1.doModal();
		}
	}

	// 文件上传
	@Listen("onUpload = Fileupload")
	public void FileUpload(UploadEvent event) {

		Media media = event.getMedia();

		// 保存上传的Excel文件
		File fExcel = saveUploadedExcel(media, "fileUpload\\");
		if (fExcel == null) {
			Messagebox.show("没有内容！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		System.out.println("Excel saved to: " + fExcel.getAbsolutePath());

		Fileupload upload = (Fileupload) event.getTarget();
		String path = fExcel.getAbsolutePath().replaceAll(Pattern.quote(File.separator), "\\\\\\\\\\\\\\\\");
		upload.setAttribute("file_path", path);
		upload.setLabel(media.getName());
	}

	public File saveUploadedExcel(Media media, String sSavePath) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS_");// 设置日期格式作为文件名前缀
		File fExcel = new File(Sessions.getCurrent().getWebApp().getRealPath("") + sSavePath + df.format(new Date())
				+ "_" + media.getName());
		//logger.info(Sessions.getCurrent().getWebApp().getRealPath("") + sSavePath + df.format(new Date()) + "_" + media.getName());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fExcel);
			fos.write(media.getByteData());
			fos.close();
		} catch (Exception e) {
			System.out.println("路径或文件有误");
			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close(); // 关闭流
				} catch (Exception e) {
					System.out.println("上传失败");
					return null;
				}
			}
		}

		return fExcel;
	}
}
