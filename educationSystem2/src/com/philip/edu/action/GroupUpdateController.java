package com.philip.edu.action;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;

public class GroupUpdateController extends SelectorComposer<Component>{
	private static Logger logger = Logger.getLogger(GroupUpdateController.class);
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
	private Group group;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		Radiogroup rg = (Radiogroup)Path.getComponent("/window/groupChose");
		String sId = rg.getSelectedItem().getId();
		int id = Integer.parseInt(sId);	
		
		group = formManager.getGroupById(id);
		
		class_name.setValue(group.getClass_name());
		sequence.setValue("" + group.getSequence());
		description.setValue(group.getDescription());
		
		for(int i=0; i<group_type.getItemCount(); i++){
			Comboitem item = group_type.getItemAtIndex(i);
			if(item.getValue().toString().charAt(0)==group.getGroup_type())group_type.setSelectedItem(item);
		}
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        gWindow.detach();
    }
	
	@Listen("onClick = #createBtn")
    public void updateGroup(Event e) {
		
        //1.check:
		if(class_name.getValue()==null || "".equals(class_name.getValue()) || "必填项".equals(class_name.getValue())){Messagebox.show("分类名称不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(sequence.getValue()==null || "".equals(sequence.getValue()) || "必填项".equals(sequence.getValue()) || !isNumeric(sequence.getValue())){Messagebox.show("顺序必须填写数字！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		//2.Save:
		group.setClass_name(class_name.getValue());
		group.setSequence(Integer.parseInt(sequence.getValue()));
		group.setGroup_type(group_type.getSelectedItem().getValue().toString().charAt(0));
		group.setDescription(description.getValue());
		
		boolean b = formManager.updateGroup(group);
		if(b){
			Messagebox.show("分类修改成功！","信息",Messagebox.OK,Messagebox.INFORMATION);
			Listbox pList = (Listbox)Path.getComponent("/window/grouplist");
			List<Group> groups = formManager.getGroups(Constants.USER_ID);
			pList.setModel(new ListModelList<Group>(groups));
			gWindow.detach();
		} else {
			Messagebox.show("修改分类出错！","错误",Messagebox.OK,Messagebox.ERROR);
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
