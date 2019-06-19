package com.philip.edu.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.excel.UploadController;

public class FormTableController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(FormTableController.class);
	private FormManager formManager = new FormManager();
	
	@Wire
	private Listbox fieldList;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		String sForm = Executions.getCurrent().getParameter("form_id");
		int form_id = Integer.parseInt(sForm);
		List<FormField> fields = formManager.getFormFields(form_id);
		fieldList.setModel(new ListModelList<FormField>(fields));
	}
}
