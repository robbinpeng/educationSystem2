package com.philip.edu.excel;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class RuleCheckDialogController extends SelectorComposer<Component>{
	@Wire
    private Window modalDialog;
	@Wire
	private Textbox checkResult;
	
	private String message;
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		message = (String) Executions.getCurrent().getArg().get("message");
		
		checkResult.setValue(message);
	}
	
	@Listen("onClick = #closeBtn")
    public void showModal(Event e) {
        modalDialog.detach();
    }
}
