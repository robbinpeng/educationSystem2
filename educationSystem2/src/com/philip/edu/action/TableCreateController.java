package com.philip.edu.action;

import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.FormStatus;
import com.philip.edu.database.DatabaseManager;

public class TableCreateController extends SelectorComposer<Component>{
	
	private static Logger logger = Logger.getLogger(TableCreateController.class);
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
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		stat_time.setSelectedIndex(0);
		form_type.setSelectedIndex(0);
		is_null.setSelectedIndex(0);
		dis_method.setSelectedIndex(0);
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        cWindow.detach();
    }
	
	@Listen("onClick = #createBtn")
    public void createTable(Event e) {
		Form form = new Form();
		FormStatus status = new FormStatus();
		
        //1.check:
		if(bus_name.getValue()==null || "".equals(bus_name.getValue()) || "������".equals(bus_name.getValue())){Messagebox.show("ҵ���������Ϊ�գ�");return;}
		if(physic_name.getValue()==null || "".equals(physic_name.getValue()) || "������".equals(physic_name.getValue())){Messagebox.show("������������Ϊ�գ�");return;}
		else if(dbManager.isTableExsits("tbl_" + physic_name.getValue())){Messagebox.show("�����������Ѿ����ڣ����������룡");return;}
		//2.save:
		form.setBus_name(bus_name.getValue());
		form.setPhsic_name("tbl_" + physic_name.getValue());
		form.setUser_id(Constants.USER_ID);
		form.setTbl_name(physic_name.getValue());
		form.setStats_time(stat_time.getSelectedItem().getValue().toString().charAt(0));
		form.setForm_type(form_type.getSelectedItem().getValue().toString().charAt(0));
		form.setIs_null(is_null.getSelectedItem().getValue().toString().charAt(0));
		form.setDisplay_method(dis_method.getSelectedItem().getValue().toString().charAt(0));
		if(memo.getValue()!=null && !"".equals(memo.getValue()))form.setMemo(memo.getValue());
		form.setCreate_time(new Date());
		
		status.setForm(form);
		status.setStatus(Constants.STATUS_CREATED);
		status.setUser_id(Constants.USER_ID);
		status.setUpdate_time(new Date());
		
		form.setStatus(status);
		
		boolean b = dbManager.createTable(form);
		
		if(b){
			Messagebox.show("�ɹ�������");
			Listbox pList = (Listbox)Path.getComponent("/dbWindow/formlist");
			List<Form> forms = formManager.getForms(Constants.USER_ID);
			pList.setModel(new ListModelList<Form>(forms));
			cWindow.detach();
		}else{Messagebox.show("�������������������⣡");}
    }
}