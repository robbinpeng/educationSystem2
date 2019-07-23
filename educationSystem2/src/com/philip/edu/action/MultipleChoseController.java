package com.philip.edu.action;

import java.util.ArrayList;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DictItem;
import com.philip.edu.basic.DictManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;

public class MultipleChoseController extends SelectorComposer<Component>{
	
	private static final Logger logger = Logger.getLogger(MultipleChoseController.class);
	
	@Wire
	private Grid dicList;
	private FormManager formManager = new FormManager();
	private DictManager dictManager = new DictManager();
	private ArrayList checked = new ArrayList();
	private String button_id = "";
	
	@Wire
	private Window mWindow;
	
	@Wire
	private Button closeBtn;
	
	@Wire
	private Button create;
	
	@Listen("onClick = #create")
	public void createDict(Event e) {
		//String sDependency = "";
		String sName = "";
		
		for(int i=0; i<checked.size(); i++){
			String sName1 = (String)checked.get(i);
			//Form form = formManager.getFormById(Integer.parseInt(sID));
			if(i==checked.size()-1)
				sName += sName1;
			else sName += sName1 + ",";
		}
		
		Button c = (Button)Path.getComponent("/bdlBody/" + button_id);
		c.setLabel(sName);
		//Input i = (Input)Path.getComponent("/cWindow/depend");
		//i.setValue(sDependency);
		
		mWindow.detach();
	}
	
	@Listen("onCheck = #dicList")
	public void checkSelected(Event e) {
		Checkbox ck = (Checkbox)e.getData();
		boolean Exists = false;
		
		int i=0;
		for(i=0; i<checked.size(); i++){
			String name = (String)checked.get(i);
			if(ck.getLabel().equals(name)){
				Exists = true;
				break;
			}
		}
		if(Exists){
			if(ck.isChecked()){}
			else{
				checked.remove(i);
			}
		}else{
			if(ck.isChecked()){checked.add(ck.getLabel());}
			else{}
		}
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        mWindow.detach();
    }
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		logger.info("into multiple chose");
		Integer iDict = (Integer) Executions.getCurrent().getArg().get("dict_id");
		List<DictItem> items = dictManager.getDictItemByDict(iDict.intValue());
		dicList.setModel(new ListModelList<DictItem>(items));
		
		button_id = (String)Executions.getCurrent().getArg().get("button_id");
		
		String chosed = (String)Executions.getCurrent().getArg().get("chosed");
		ArrayList al = new ArrayList();
		if (chosed != null && chosed.length() > 0) {
			String[] str = chosed.split(",");
			for (int i = 0; i < str.length; i++) {
				String temp = str[i];
				al.add(temp);
			}
		}
		checked = al;
	}
}
