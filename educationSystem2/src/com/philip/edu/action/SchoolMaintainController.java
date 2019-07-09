package com.philip.edu.action;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.School;
import com.philip.edu.database.DatabaseManager;

public class SchoolMaintainController extends SelectorComposer<Component>{
	private static Logger logger = Logger.getLogger(SchoolMaintainController.class);
	private static FormManager formManager = new FormManager();
	
	@Wire
	private Window sWindow;
	@Wire
	private Textbox school_num;
	@Wire
	private Textbox school_name;
	@Wire
	private Button modify;
	@Wire
	private Button save;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		School school = formManager.getSchoolInfo();
		school_num.setValue(school.getSchool_number());
		school_num.setReadonly(true);
		school_name.setValue(school.getSchool_name());
		school_name.setReadonly(true);
	}
	
	@Listen("onClick = #modify")
	public void modifyText(Event e){
		school_num.setReadonly(false);
		school_name.setReadonly(false);
	}
	
	@Listen("onClick = #save")
	public void saveSchool(Event e){
		School school = new School();
		if(school_num.isReadonly()){Messagebox.show("没有修改不作存储！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		//check:
		if(school_num.getValue()==null || "".equals(school_num.getValue())){Messagebox.show("学校代码不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(school_name.getValue()==null || "".equals(school_name.getValue())){Messagebox.show("学校名称不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		school.setSchool_number(school_num.getValue());
		school.setSchool_name(school_name.getValue());
		
		boolean b = formManager.updateSchoolInfo(school);
		if(b){
			Messagebox.show("学校信息已存储！","信息",Messagebox.OK,Messagebox.INFORMATION);
			school_num.setReadonly(true);
			school_name.setReadonly(true);
		} else {
			Messagebox.show("学校信息存储错误！","错误",Messagebox.OK,Messagebox.ERROR);
		}
		
	}
}
