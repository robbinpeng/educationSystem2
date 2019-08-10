package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Dict;
import com.philip.edu.basic.DictGroup;
import com.philip.edu.basic.DictI;
import com.philip.edu.basic.DictManager;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;

public class DictUpdateController extends SelectorComposer<Component> {
	private static Logger logger = Logger.getLogger(DictUpdateController.class);
	private static DictManager dictManager = new DictManager();
	
	@Wire
	private Window uWindow;
	@Wire
	private Textbox dict_code;
	@Wire
	private Textbox dict_name;
	@Wire
	private Combobox dict_group;
	private int id;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		Integer iId = (Integer)Executions.getCurrent().getArg().get("dictId");
		id = iId.intValue();
		Dict dict = dictManager.getDictById(id);
		
		dict_code.setValue(dict.getDictcode());
		dict_name.setValue(dict.getDictname());
		
		List<DictGroup> groups = dictManager.getDictGroups();
		Comboitem item = new Comboitem("选择类型");
		item.setValue("0");
		dict_group.appendChild(item);
		for(int i=0; i<groups.size(); i++){
			DictGroup dg = (DictGroup)groups.get(i);
			item = new Comboitem(dg.getGroupname());
			item.setValue(dg.getId());
			dict_group.appendChild(item);
			if(dict.getDictgroupid()==dg.getId())dict_group.setSelectedItem(item);
		}
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        uWindow.detach();
    }
	
	@Listen("onClick = #updateBtn")
    public void createGroup(Event e) {
		
		if(dict_code.getValue()==null || "".equals(dict_code.getValue()) || "必填项".equals(dict_code.getValue())){Messagebox.show("编码不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(dict_name.getValue()==null || "".equals(dict_name.getValue()) || "必填项".equals(dict_name.getValue())){Messagebox.show("名称不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if("0".equals(dict_group.getSelectedItem().getValue().toString())){Messagebox.show("必须选择分组！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		Dict dict = new Dict();
		dict.setId(id);
		dict.setDictcode(dict_code.getValue().toString());
		String sGroup = dict_group.getSelectedItem().getValue().toString();
		int group_id = Integer.parseInt(sGroup);
		dict.setDictgroupid(group_id);
		dict.setDictname(dict_name.getValue().toString());
		dict.setStatus('A');
		
		boolean b = dictManager.updateDict(dict);
		if(b){
			Messagebox.show("数据字典项修改成功！", "信息", Messagebox.OK, Messagebox.INFORMATION);
			Listbox pList = (Listbox)Path.getComponent("/window/dictionlist");
			List<Dict> diction = dictManager.getDictionary();
			List<DictI> dictionI = new ArrayList();
			for(int i=0; i<diction.size(); i++){
				dict = diction.get(i);
				DictI dictI = new DictI();
				dictI.setDictcode(dict.getDictcode());
				dictI.setDictgroupid(dict.getDictgroupid());
				dictI.setDictname(dict.getDictname());
				dictI.setId(dict.getId());
				dictI.setRemark(dict.getRemark());
				dictI.setStatus(dict.getStatus());
				//logger.info(dictI.getDictname());
				
				DictGroup dictGroup = dictManager.getDictGroupById(dict.getDictgroupid());
				dictI.setDictgroupname(dictGroup.getGroupname());
				dictionI.add(dictI);
			}
			pList.setModel(new ListModelList<DictI>(dictionI));
			uWindow.detach();
		} else {
			Messagebox.show("修改数据字典项出错！","错误",Messagebox.OK,Messagebox.ERROR);
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
