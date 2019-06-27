package com.philip.edu.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Input;
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
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.FormStatus;
import com.philip.edu.database.DatabaseManager;

public class TableUpdateController extends SelectorComposer<Component> {
	private static Logger logger = Logger.getLogger(TableUpdateController.class);
	private static DatabaseManager dbManager = new DatabaseManager();
	private static FormManager formManager = new FormManager();
	
	@Wire
	private Window cWindow;
	@Wire
	private Button closeBtn;
	@Wire
	private Button createBtn;
	@Wire
	private Textbox bus_name;
	@Wire
	private Textbox physic_name;
	@Wire
	private Combobox stat_time;
	@Wire
	private Combobox form_type;
	@Wire
	private Combobox is_null;
	@Wire
	private Combobox dis_method;
	@Wire
	private Textbox memo;
	@Wire
	private Input depend;
	@Wire
	private Button chooseDep;
	
	private Form storeForm;
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        cWindow.detach();
    }
	
	@Listen("onClick = #chooseDep")
	public void chooseDependency(Event e) {
		ArrayList al = new ArrayList();
		String name = "";
		String dp = depend.getValue();
		String[] str = null;
		if(dp!=null && !dp.equals("")){
			str = dp.split(",");
			for(int i=0; i<str.length; i++){
				String temp = str[i].substring(1, str[i].length()-1);
				int table_id = Integer.parseInt(temp);
				al.add("" + table_id);
			}
		}	
		HashMap map = new HashMap();
		map.put("dependency", al);
		
		Window window = (Window) Executions.createComponents("/dependency.zul", null, map);
		window.setPosition("center");
		
		window.doModal();
	}
	
	@Listen("onClick = #updateBtn")
    public void editTable(Event e) {
		Form form = storeForm;
		
        //1.check:
		if(bus_name.getValue()==null || "".equals(bus_name.getValue()) || "必填项".equals(bus_name.getValue())){Messagebox.show("业务表名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		//2.save:
		form.setBus_name(bus_name.getValue());
		form.setUser_id(Constants.USER_ID);
		form.setStats_time(stat_time.getSelectedItem().getValue().toString().charAt(0));
		form.setForm_type(form_type.getSelectedItem().getValue().toString().charAt(0));
		form.setIs_null(is_null.getSelectedItem().getValue().toString().charAt(0));
		form.setDisplay_method(dis_method.getSelectedItem().getValue().toString().charAt(0));
		form.setDependency_form(depend.getValue());
		if(memo.getValue()!=null && !"".equals(memo.getValue()))form.setMemo(memo.getValue());
				
		boolean b = dbManager.updateTable(form);
		
		if(b){
			Messagebox.show("成功修改！","信息",Messagebox.OK,Messagebox.INFORMATION);
			Listbox pList = (Listbox)Path.getComponent("/dbWindow/formlist");
			List<Form> forms = formManager.getForms(Constants.USER_ID);
			pList.setModel(new ListModelList<Form>(forms));
			cWindow.detach();
		}else{Messagebox.show("表修改过程中遇到问题！","错误",Messagebox.OK,Messagebox.ERROR);}
    }
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		Radiogroup rg = (Radiogroup)Path.getComponent("/dbWindow/tblChose");
		String sId = rg.getSelectedItem().getId();
		int id = Integer.parseInt(sId);
		
		Form form = formManager.getFormById(id);
		
		bus_name.setValue(form.getBus_name());
		physic_name.setValue(form.getTbl_name());
		physic_name.setDisabled(true);
		
		Comboitem item = new Comboitem(Constants.DIP_TYPE_STAT_TIME_POINT);
		item.setValue(Constants.V_TYPE_STAT_TIME_POINT);
		stat_time.appendChild(item);
		if(form.getStats_time()==Constants.V_TYPE_STAT_TIME_POINT)stat_time.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TYPE_STAT_TIME_STUDY_YEAR);
		item.setValue(Constants.V_TYPE_STAT_TIME_STUDY_YEAR);
		stat_time.appendChild(item);
		if(form.getStats_time()==Constants.V_TYPE_STAT_TIME_STUDY_YEAR)stat_time.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TYPE_STAT_TIME_NATURE_YEAR);
		item.setValue(Constants.V_TYPE_STAT_TIME_NATURE_YEAR);
		stat_time.appendChild(item);
		if(form.getStats_time()==Constants.V_TYPE_STAT_TIME_NATURE_YEAR)stat_time.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_TYPE_STAT_TIME_CURRENT_YEAR);
		item.setValue(Constants.V_TYPE_STAT_TIME_CURRENT_YEAR);
		stat_time.appendChild(item);
		if(form.getStats_time()==Constants.V_TYPE_STAT_TIME_CURRENT_YEAR)stat_time.setSelectedItem(item);
		
		item = new Comboitem(Constants.DIP_FORM_TYPE_PERSONALIZED);
		item.setValue(Constants.V_FORM_TYPE_PERSONALIZED);
		form_type.appendChild(item);
		if(form.getForm_type()==Constants.V_FORM_TYPE_PERSONALIZED)form_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FORM_TYPE_EDUCATION_STATUS);
		item.setValue(Constants.V_FORM_TYPE_EDUCATION_STATUS);
		form_type.appendChild(item);
		if(form.getForm_type()==Constants.V_FORM_TYPE_EDUCATION_STATUS)form_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FORM_TYPE_PROFESSIONAL_VALUE);
		item.setValue(Constants.V_FORM_TYPE_PROFESSIONAL_VALUE);
		form_type.appendChild(item);
		if(form.getForm_type()==Constants.V_FORM_TYPE_PROFESSIONAL_VALUE)form_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FORM_TYPE_CLASS_VALUE);
		item.setValue(Constants.V_FORM_TYPE_CLASS_VALUE);
		form_type.appendChild(item);
		if(form.getForm_type()==Constants.V_FORM_TYPE_CLASS_VALUE)form_type.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_FORM_TYPE_HIGHBASIC_REPORT);
		item.setValue(Constants.V_FORM_TYPE_HIGHBASIC_REPORT);
		form_type.appendChild(item);
		if(form.getForm_type()==Constants.V_FORM_TYPE_HIGHBASIC_REPORT)form_type.setSelectedItem(item);

		item = new Comboitem("否");
		item.setValue("N");
		is_null.appendChild(item);
		if(form.getIs_null()=='N')is_null.setSelectedItem(item);
		item = new Comboitem("是");
		item.setValue("Y");
		is_null.appendChild(item);
		if(form.getIs_null()=='Y')is_null.setSelectedItem(item);
		
		item = new Comboitem(Constants.DIP_DISPLAY_SINGLE_RECORD);
		item.setValue(Constants.V_DISPLAY_SINGLE_RECORD);
		dis_method.appendChild(item);
		if(form.getDisplay_method()==Constants.V_DISPLAY_SINGLE_RECORD)dis_method.setSelectedItem(item);
		item = new Comboitem(Constants.DIP_DISPLAY_GENERAL_LIST);
		item.setValue(Constants.V_DISPLAY_GENERAL_LIST);
		dis_method.appendChild(item);
		if(form.getDisplay_method()==Constants.V_DISPLAY_GENERAL_LIST)dis_method.setSelectedItem(item);
		
		depend.setValue("" + form.getDependency_form());
		
		String name = "";
		String dp = form.getDependency_form();
		String[] str = null;
		if(dp!=null && !dp.equals("")){
			str = dp.split(",");
			for(int i=0; i<str.length; i++){
				String temp = str[i].substring(1, str[i].length()-1);
				int table_id = Integer.parseInt(temp);
				Form formTemp = formManager.getFormById(table_id);
				name += formTemp.getBus_name() + ",";
			}
			chooseDep.setLabel(name);
		}
		
		memo.setValue(form.getMemo());		
		
		storeForm = form;
	}
}
