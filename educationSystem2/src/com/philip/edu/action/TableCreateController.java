package com.philip.edu.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.FormStatus;
import com.philip.edu.basic.Group;
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
	private Textbox memo;
	@Wire
	private Input depend;
	@Wire
	private Combobox table_class;
	private int group_id;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		stat_time.setSelectedIndex(0);
		form_type.setSelectedIndex(0);
		is_null.setSelectedIndex(0);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		for(int i=0; i<groups.size(); i++){
			Group group = (Group)groups.get(i);
			Comboitem item = new Comboitem(group.getClass_name());
			item.setValue(group.getId());
			table_class.appendChild(item);
			if(group.getId()==1)table_class.setSelectedItem(item);
		}
		
		String sGroup = (String)Executions.getCurrent().getArg().get("group_id");
		group_id = Integer.parseInt(sGroup);
	}
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		
		return super.doBeforeCompose(page, parent, compInfo);
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
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        cWindow.detach();
    }
	
	@Listen("onClick = #createBtn")
    public void createTable(Event e) {
		Form form = new Form();
		FormStatus status = new FormStatus();
		
        //1.check:
		if(bus_name.getValue()==null || "".equals(bus_name.getValue()) || "必填项".equals(bus_name.getValue())){Messagebox.show("业务表名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(physic_name.getValue()==null || "".equals(physic_name.getValue()) || "必填项".equals(physic_name.getValue())){Messagebox.show("物理表名不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		else if(dbManager.isTableExsits("tbl_" + physic_name.getValue())){Messagebox.show("该物理表名已经存在，请重新输入！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		//2.save:
		form.setBus_name(bus_name.getValue());
		form.setPhsic_name("tbl_" + physic_name.getValue());
		form.setUser_id(Constants.USER_ID);
		form.setTbl_name(physic_name.getValue());
		form.setStats_time(stat_time.getSelectedItem().getValue().toString().charAt(0));
		form.setForm_type(form_type.getSelectedItem().getValue().toString().charAt(0));
		form.setIs_null(is_null.getSelectedItem().getValue().toString().charAt(0));
		form.setDisplay_method(Constants.V_DISPLAY_GENERAL_LIST);
		form.setDependency_form(depend.getValue().toString());
		String sGroup = table_class.getSelectedItem().getValue().toString();
		int group_id = Integer.parseInt(sGroup);
		form.setGroup_id(group_id);
		if(memo.getValue()!=null && !"".equals(memo.getValue()))form.setMemo(memo.getValue());
		form.setCreate_time(new Date());
		
		status.setTask_id(Constants.TASK_ID);
		status.setForm_status(Constants.STATUS_CREATED);
		status.setUser_id(Constants.USER_ID);
		status.setUpdate_time(new Date());
		
		form.setStatus(status);
		
		boolean b = dbManager.createTable(form);
		
		if(b){
			Messagebox.show("成功创建！","信息",Messagebox.OK,Messagebox.INFORMATION);
			Listbox pList = (Listbox)Path.getComponent("/dbWindow/formlist");
			List<Form> forms = formManager.getFormsByGroup(group_id);
			pList.setModel(new ListModelList<Form>(forms));
			cWindow.detach();
		}else{Messagebox.show("表创建过程中遇到问题！","错误",Messagebox.OK,Messagebox.ERROR);}
    }
}
