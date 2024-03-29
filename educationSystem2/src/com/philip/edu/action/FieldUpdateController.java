package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
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

public class FieldUpdateController extends SelectorComposer<Component>{
	private static Logger logger = Logger.getLogger(TableCreateController.class);
	private static DatabaseManager dbManager = new DatabaseManager();
	private static FormManager formManager = new FormManager();
	private static ExcelManager excelManager = new ExcelManager();
	private static DictManager dictManager = new DictManager();
	
	@Wire
	private Window ufWindow;
	@Wire
	private Button closeBtn;
	@Wire
	private Button updateBtn;
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
	private FormField store_field;
	
	@Listen("onClick = #closeBtn")
	public void closeModal(Event e) {
		ufWindow.detach();
	}

	
	@Listen("onClick = #updateBtn")
    public void editField(Event e) {
		FormField field = store_field; 
		String len = length.getValue();
		int iLen = 0;
		
        //1.check:
		if(bus_name.getValue()==null || "".equals(bus_name.getValue()) || "必填项".equals(bus_name.getValue())){Messagebox.show("字段业务名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if (sequence.getValue() == null || "".equals(sequence.getValue()) || "必填项".equals(sequence.getValue())) {
			Messagebox.show("字段顺序不能为空！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		} else if (!isNumeric(sequence.getValue())) {
			Messagebox.show("字段顺序必须是数字！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		if (length.getValue()==null || "".equals(length.getValue()) || "必填项".equals(length.getValue())){
			Messagebox.show("字段长度不能为空！","错误",Messagebox.OK,Messagebox.ERROR);
			return;
		} else if (!isNumeric(length.getValue())) {
			Messagebox.show("字段长度必须是数字！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			iLen = Integer.parseInt(len);
			if(iLen < store_field.getLength()){
				Messagebox.show("字段长度只能增加，不能减少！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		
		//2.update:
		iLen = Integer.parseInt(len);
		
		//field.setLength(iLen);
		field.setBus_name(bus_name.getValue());
		field.setSequence(Integer.parseInt(sequence.getValue()));
		field.setData_type(Integer.parseInt(data_type.getSelectedItem().getValue().toString()));
		field.setIs_required(is_required.getSelectedItem().getValue().toString().charAt(0));
		field.setIs_report(is_report.getSelectedItem().getValue().toString().charAt(0));
		field.setIs_hidden(is_hidden.getSelectedItem().getValue().toString().charAt(0));
		field.setDis_method(display_method.getSelectedItem().getValue().toString().charAt(0));
		field.setCompute(compute.getSelectedItem().getValue().toString().charAt(0));
		field.setText_format(text_format.getSelectedItem().getValue().toString().charAt(0));
		field.setDictid(Integer.parseInt(dictionary.getSelectedItem().getValue().toString()));
		field.setMemo(memo.getValue());
		
		boolean b = false;
		
		if(iLen != store_field.getLength()){
			Form form = formManager.getFormById(store_formid);
			b = dbManager.alterLength(form.getPhsic_name(), store_field.getId(), iLen);
		}
		
		if(b){
			field.setLength(iLen);
			b = dbManager.updateField(field);
		}
		
		if(b){
			Messagebox.show("成功修改！","信息",Messagebox.OK,Messagebox.INFORMATION);
			
			Listbox pList = (Listbox)Path.getComponent("/fWindow/fieldList");
		    logger.info("store_formid:" + store_formid);
			List<FormField> fields = formManager.getFormFields(store_formid);
			pList.setModel(new ListModelList<FormField>(fields));
			ufWindow.detach();
		}else{Messagebox.show("字段修改过程中遇到问题！","错误",Messagebox.OK,Messagebox.ERROR);}
    } 
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		Integer sForm = (Integer) Executions.getCurrent().getArg().get("form_id");
		int form_id = sForm.intValue();
		store_formid = form_id;
		
		Radiogroup rg = (Radiogroup)Path.getComponent("/fWindow/fieldChose");
		String sId = rg.getSelectedItem().getId();
		int id = Integer.parseInt(sId);
		
		FormField field = formManager.getFieldById(id);

		Comboitem item = new Comboitem(Constants.DIP_DATA_TYPE_STRING);
		item.setValue(Constants.V_DATA_TYPE_STRING);
		data_type.appendChild(item);
		if(field.getData_type()==Constants.V_DATA_TYPE_STRING)data_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DATA_TYPE_INTEGER);
		item.setValue(Constants.V_DATA_TYPE_INTEGER);
		data_type.appendChild(item);
		if(field.getData_type()==Constants.V_DATA_TYPE_INTEGER)data_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DATA_TYPE_FLOAT);
		item.setValue(Constants.V_DATA_TYPE_FLOAT);
		data_type.appendChild(item);
		if(field.getData_type()==Constants.V_DATA_TYPE_FLOAT)data_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DATA_TYPE_DATE);
		item.setValue(Constants.V_DATA_TYPE_DATE);
		data_type.appendChild(item);
		if(field.getData_type()==Constants.V_DATA_TYPE_DATE)data_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DATA_TYPE_BIG_STRING);
		item.setValue(Constants.V_DATA_TYPE_BIG_STRING);
		data_type.appendChild(item);
		if(field.getData_type()==Constants.V_DATA_TYPE_BIG_STRING)data_type.setSelectedItem(item);
		
		item = new Comboitem("可空");
		item.setValue("N");
		is_required.appendChild(item);
		if(field.getIs_required()=='N')is_required.setSelectedItem(item);
		item = new Comboitem("必填");
		item.setValue("Y");
		is_required.appendChild(item);
		if(field.getIs_required()=='Y')is_required.setSelectedItem(item);
		
		item = new Comboitem(Constants.DIP_DISPLAY_SINGLE_TEXTBOX);
		item.setValue(Constants.V_DISPLAY_SINGLE_TEXTBOX);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_SINGLE_TEXTBOX)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_MUTIPLE_TEXTBOX);
		item.setValue(Constants.V_DISPLAY_MUTIPLE_TEXTBOX);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_MUTIPLE_TEXTBOX)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_RICH_TEXT);
		item.setValue(Constants.V_DISPLAY_RICH_TEXT);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_RICH_TEXT)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_DATE_CONTROL);
		item.setValue(Constants.V_DISPLAY_DATE_CONTROL);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_DATE_CONTROL)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_SINGLE_COMBOBOX);
		item.setValue(Constants.V_DISPLAY_SINGLE_COMBOBOX);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_SINGLE_COMBOBOX)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_MUTLIPLE_COMBOBOX);
		item.setValue(Constants.V_DISPLAY_MULTIPLE_COMBOBOX);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_MULTIPLE_COMBOBOX)display_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_UPLOAD_CONTROL);
		item.setValue(Constants.V_DISPLAY_UPLOAD_CONTROL);
		display_method.appendChild(item);
		if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)display_method.setSelectedItem(item);
		
		item = new Comboitem(Constants.DIP_FIELD_TYPE_PHYSIC_COLUMN);
		item.setValue(Constants.V_FIELD_TYPE_PHYSIC_COLUMN);
		compute.appendChild(item);
		if(field.getCompute()==Constants.V_FIELD_TYPE_PHYSIC_COLUMN)compute.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FIELD_TYPE_VERTURAL_COLUMN);
		item.setValue(Constants.V_FIELD_TYPE_VIRTURAL_COLUMN);
		compute.appendChild(item);
		if(field.getCompute()==Constants.V_FIELD_TYPE_VIRTURAL_COLUMN)compute.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FIELD_TYPE_COMPUTE_COLUMN);
		item.setValue(Constants.V_FIELD_TYPE_COMPUTE_COLUMN);
		compute.appendChild(item);
		if(field.getCompute()==Constants.V_FIELD_TYPE_COMPUTE_COLUMN)compute.setSelectedItem(item);
		
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_NO);
		item.setValue(Constants.V_TEXT_FORMAT_NO);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_NO)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_WEBSITE);
		item.setValue(Constants.V_TEXT_FORMAT_WEBSITE);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_WEBSITE)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_MOBILEPHONE);
		item.setValue(Constants.V_TEXT_FORMAT_MOBILEPHONE);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_MOBILEPHONE)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_IDENTITY);
		item.setValue(Constants.V_TEXT_FORMAT_IDENTITY);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_IDENTITY)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_EMAIL);
		item.setValue(Constants.V_TEXT_FORMAT_EMAIL);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_EMAIL)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_DATE_YEAR);
		item.setValue(Constants.V_TEXT_FORMAT_DATE_YEAR);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_DATE_YEAR)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_DATE_MONTH);
		item.setValue(Constants.V_TEXT_FORMAT_DATE_MONTH);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_DATE_MONTH)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_DATE_MONTH_NOSLASH);
		item.setValue(Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH)text_format.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TEXT_FORMAT_DATE_DAY);
		item.setValue(Constants.V_TEXT_FORMAT_DATE_DAY);
		text_format.appendChild(item);
		if(field.getText_format()==Constants.V_TEXT_FORMAT_DATE_DAY)text_format.setSelectedItem(item);
		
		item = new Comboitem("是");
		item.setValue("Y");
		is_report.appendChild(item);
		if(field.getIs_report()=='Y')is_report.setSelectedItem(item);
		item = new Comboitem("否");
		item.setValue("N");
		is_report.appendChild(item);
		if(field.getIs_report()=='N')is_report.setSelectedItem(item);
		
		item = new Comboitem("否");
		item.setValue("N");
		is_hidden.appendChild(item);
		if(field.getIs_hidden()=='N')is_hidden.setSelectedItem(item);
		item = new Comboitem("是");
		item.setValue("Y");
		is_hidden.appendChild(item);
		if(field.getIs_hidden()=='Y')is_hidden.setSelectedItem(item);
		
		item = new Comboitem("");
		item.setValue("0");
		dictionary.appendChild(item);
		if(field.getDictid()==0)dictionary.setSelectedItem(item);
		ArrayList dicts = dictManager.getDictByGroup(Constants.DICT_GROUP_ID);
		for(int i=0; i<dicts.size(); i++){
			Dict dict = (Dict)dicts.get(i);
			item = new Comboitem(dict.getDictname());
			item.setValue("" + dict.getId());
			dictionary.appendChild(item);
			if(field.getDictid()==dict.getId())dictionary.setSelectedItem(item);
		}
		
		bus_name.setValue(field.getBus_name());
		physic_name.setValue(field.getPhysic_name());
		sequence.setValue("" + field.getSequence());
		length.setValue("" + field.getLength());
		
		memo.setValue(field.getMemo());
		
		store_field = field;
	}
	
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
