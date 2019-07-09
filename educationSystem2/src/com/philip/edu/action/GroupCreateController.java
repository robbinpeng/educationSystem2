package com.philip.edu.action;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Textarea;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.StatusTemp;

public class GroupCreateController extends SelectorComposer<Component>{
	private static Logger logger = Logger.getLogger(GroupCreateController.class);
	private static FormManager formManager = new FormManager();
	
	@Wire
	private Window gWindow;
	@Wire
	private Textbox class_name;
	@Wire
	private Textbox sequence;
	@Wire
	private Textbox description;
	@Wire
	private Combobox group_type;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		group_type.setSelectedIndex(0);		
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        gWindow.detach();
    }
	
	@Listen("onClick = #createBtn")
    public void createGroup(Event e) {
		
        //1.check:
		if(class_name.getValue()==null || "".equals(class_name.getValue()) || "必填项".equals(class_name.getValue())){Messagebox.show("分类名称不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(sequence.getValue()==null || "".equals(sequence.getValue()) || "必填项".equals(sequence.getValue()) || !isNumeric(sequence.getValue())){Messagebox.show("顺序必须填写数字！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		//2.Save:
		Group group = new Group();
		group.setClass_name(class_name.getValue());
		group.setSequence(Integer.parseInt(sequence.getValue()));
		group.setGroup_type(group_type.getSelectedItem().getValue().toString().charAt(0));
		group.setDescription(description.getValue());
		
		boolean b = formManager.createGroup(group);
		if(b){
			Messagebox.show("分类创建成功！","信息",Messagebox.OK,Messagebox.INFORMATION);
			Listbox pList = (Listbox)Path.getComponent("/window/grouplist");
			List<Group> groups = formManager.getGroups(Constants.USER_ID);
			pList.setModel(new ListModelList<Group>(groups));
			gWindow.detach();
		} else {
			Messagebox.show("创建分类出错！","错误",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
