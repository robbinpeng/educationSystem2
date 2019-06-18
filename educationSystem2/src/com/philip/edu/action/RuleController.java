package com.philip.edu.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Rule;
import com.philip.edu.rule.RuleManager;

public class RuleController  extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(RuleController.class);
	private RuleManager ruleManager = new RuleManager();
	
	@Wire
	private Listbox ruleList;
	private ListModelList<Rule> listModel;
	
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
			Messagebox.show("…æ≥˝πÊ‘Ú ß∞‹£°");
		}
	}
}
