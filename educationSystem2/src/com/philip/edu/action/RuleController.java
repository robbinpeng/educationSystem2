package com.philip.edu.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.Rule;
import com.philip.edu.rule.RuleManager;

public class RuleController  extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(RuleController.class);
	private RuleManager ruleManager = new RuleManager();
	private FormManager formManager = new FormManager();
	
	@Wire
	private Listbox ruleList;
	private ListModelList<Rule> listModel;
	
	//@Wire
	//private Grid groupList;

	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		String sForm = Executions.getCurrent().getParameter("form_id");
		Session session = Sessions.getCurrent();
		session.setAttribute("form_id", sForm);
		int form_id = Integer.parseInt(sForm);
		List<Rule> rules = ruleManager.getRules(form_id);
		listModel = new ListModelList<Rule>(rules);
		ruleList.setModel(listModel);
		
		String form_name = formManager.getFormById(form_id).getBus_name();
		session.setAttribute("form_name", form_name);
		
		//List<Group> groups = formManager.getGroups(Constants.USER_ID);
		//groupList.setModel(new ListModelList<Group>(groups));
	}
	
	@Listen("onDelete = #ruleList")
	public void doDeleteRule(Event event){
		System.out.println("get in delete");
		
		System.out.print(event.getData());
		Rule rule = (Rule)event.getData();
		boolean is_success = ruleManager.deleteRule(rule.getId());
		if(is_success){
			listModel.remove(rule);
		} else {
			Messagebox.show("É¾³ý¹æÔòÊ§°Ü£¡","´íÎó",Messagebox.OK,Messagebox.ERROR);
		}
	}
	
	@Listen("onActive = #ruleList")
	public void doChangeActive(Event event){
		Rule rule = (Rule)event.getData();
		boolean is_success = ruleManager.changeActive(rule.getId());
				
		if(is_success){
			listModel.remove(rule);
			if(rule.getRule_active()=='Y')rule.setRule_active('N');
			else rule.setRule_active('Y');
			listModel.add(rule);
		} else {
			Messagebox.show("ÐÞ¸ÄÆô¶¯×´Ì¬Ê§°Ü£¡","´íÎó",Messagebox.OK,Messagebox.ERROR);
		}
	}
}
