package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;

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
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;

public class DependencyController extends SelectorComposer<Component>{
	@Wire
	private Grid formList;
	private FormManager formManager = new FormManager();
	private ArrayList checked = new ArrayList();
	
	@Wire
	private Window dpWindow;
	
	@Wire
	private Button closeBtn;
	
	@Listen("onClick = #create")
	public void createDependency(Event e) {
		String sDependency = "";
		String sName = "";
		
		for(int i=0; i<checked.size(); i++){
			String sID = (String)checked.get(i);
			Form form = formManager.getFormById(Integer.parseInt(sID));
			
			sDependency += "(" + sID + "),";
			sName += form.getBus_name() + ",";
		}
		
		Button c = (Button)Path.getComponent("/cWindow/chooseDep");
		c.setLabel(sName);
		Input i = (Input)Path.getComponent("/cWindow/depend");
		i.setValue(sDependency);
		
		dpWindow.detach();
	}
	
	@Listen("onCheck = #formList")
	public void checkSelected(Event e) {
		Checkbox ck = (Checkbox)e.getData();
		boolean Exists = false;
		
		int i=0;
		for(i=0; i<checked.size(); i++){
			String id = (String)checked.get(i);
			if(ck.getId().equals(id)){
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
			if(ck.isChecked()){checked.add(ck.getId());}
			else{}
		}
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        dpWindow.detach();
    }
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		List<Form> forms = formManager.getForms(Constants.USER_ID);
		formList.setModel(new ListModelList<Form>(forms));
		
		ArrayList al = (ArrayList)Executions.getCurrent().getArg().get("dependency");
		checked = al;
	}
}
