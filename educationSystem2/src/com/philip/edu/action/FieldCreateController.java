package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Dict;
import com.philip.edu.basic.DictManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.database.DatabaseManager;
import com.philip.edu.excel.ExcelManager;

public class FieldCreateController extends SelectorComposer<Component> {
	private static Logger logger = Logger.getLogger(TableCreateController.class);
	private static DatabaseManager dbManager = new DatabaseManager();
	private static FormManager formManager = new FormManager();
	private static ExcelManager excelManager = new ExcelManager();
	private static DictManager dictManager = new DictManager();

	@Wire
	private Window cfWindow;
	@Wire
	private Button closeBtn;
	@Wire
	private Button createBtn;
	@Wire
	private Textbox bus_name;
	@Wire
	private Textbox physic_name;
	@Wire
	private Textbox sequence;
	@Wire
	private Combobox data_type;
	@Wire
	private Textbox length;
	@Wire
	private Combobox is_required;
	@Wire
	private Combobox display_method;
	@Wire
	private Combobox compute;
	@Wire
	private Textbox memo;
	@Wire
	private Combobox is_report;
	@Wire
	private Combobox is_hidden;
	@Wire
	private Combobox text_format;
	@Wire
	private Combobox dictionary;

	private int store_formid;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		//Label test = new Label();
		
		Integer sForm = (Integer) Executions.getCurrent().getArg().get("form_id");
		int form_id = sForm.intValue();
		store_formid = form_id;

		data_type.setSelectedIndex(0);
		is_required.setSelectedIndex(0);
		display_method.setSelectedIndex(0);
		compute.setSelectedIndex(0);
		is_report.setSelectedIndex(0);
		is_hidden.setSelectedIndex(0);
		text_format.setSelectedIndex(0);
		
		Comboitem item = new Comboitem("");
		item.setValue("0");
		dictionary.appendChild(item);
		dictionary.setSelectedItem(item);
		ArrayList dicts = dictManager.getDictByGroup(Constants.DICT_GROUP_ID);
		for(int i=0; i<dicts.size(); i++){
			Dict dict = (Dict)dicts.get(i);
			item = new Comboitem(dict.getDictname());
			item.setValue(dict.getId());
			dictionary.appendChild(item);
		}
	}

	@Listen("onClick = #closeBtn")
	public void closeModal(Event e) {
		cfWindow.detach();
	}

	@Listen("onClick = #createBtn")
	public void createTable(Event e) {
		FormField field = new FormField();
		Form form = formManager.getFormById(store_formid);

		// 1.check:
		if (bus_name.getValue() == null || "".equals(bus_name.getValue()) || "必填项".equals(bus_name.getValue())) {
			Messagebox.show("字段业务名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		if (physic_name.getValue() == null || "".equals(physic_name.getValue())
				|| "必填项".equals(physic_name.getValue())) {
			Messagebox.show("字段物理名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		} else if (dbManager.isFieldExsits(form.getPhsic_name(), physic_name.getValue())) {
			Messagebox.show("该字段已经存在，请重新输入！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		if (sequence.getValue() == null || "".equals(sequence.getValue()) || "必填项".equals(sequence.getValue())) {
			Messagebox.show("字段顺序不能为空！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		} else if (!isNumeric(sequence.getValue())) {
			Messagebox.show("字段顺序必须是数字！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		if (length.getValue() == null || "".equals(length.getValue()) || "必填项".equals(length.getValue())) {
			Messagebox.show("字段长度不能为空!","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		} else if (!isNumeric(length.getValue())) {
			Messagebox.show("字段长度必须是数字！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		}

		// 2.save:
		field.setBus_name(bus_name.getValue());
		field.setPhysic_name(physic_name.getValue());
		field.setSequence(Integer.parseInt(sequence.getValue()));
		field.setData_type(Integer.parseInt(data_type.getSelectedItem().getValue().toString()));
		field.setLength(Integer.parseInt(length.getValue()));
		field.setIs_required(is_required.getSelectedItem().getValue().toString().charAt(0));
		field.setDis_method(display_method.getSelectedItem().getValue().toString().charAt(0));
		field.setCompute(compute.getSelectedItem().getValue().toString().charAt(0));
		field.setIs_report(is_report.getSelectedItem().getValue().toString().charAt(0));
		field.setIs_hidden(is_hidden.getSelectedItem().getValue().toString().charAt(0));
		field.setText_format(text_format.getSelectedItem().getValue().toString().charAt(0));
		field.setForm_id(store_formid);
		field.setMemo(memo.getValue());
		String sDict = dictionary.getSelectedItem().getValue().toString();
		int dictid = Integer.parseInt(sDict);
		field.setDictid(dictid);

		boolean b = dbManager.addField(form.getPhsic_name(), field);

		if (b) {
			// 3.generate template:
			/*String rootPath = Sessions.getCurrent().getWebApp().getRealPath("");
			boolean b1 = excelManager.generateTemplate(store_formid,
					rootPath + "/template/" + form.getBus_name() + ".xls");

			if (b1) {*/
				Messagebox.show("成功创建字段！","信息",Messagebox.OK,Messagebox.INFORMATION);
				
				Listbox pList = (Listbox) Path.getComponent("/fWindow/fieldList");
				List<FormField> fields = formManager.getFormFields(store_formid);
				pList.setModel(new ListModelList<FormField>(fields));
				cfWindow.detach();
			/*} else {
				Messagebox.show("在生成Excel模板时出错！");
			}*/
		} else {
			Messagebox.show("添加字段时出错！","错误",Messagebox.OK,Messagebox.ERROR);
		}

	}

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
