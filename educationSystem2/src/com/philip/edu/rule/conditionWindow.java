package com.philip.edu.rule;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Layout;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.philip.edu.basic.Constants;



public class conditionWindow extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	@Wire
	private Window window6;
	
	@Listen("onClick=#frontConButton")
	public void addFrontCondition(){
		Div myDiv = new Div();
		Combobox combox = new Combobox();
		combox.appendItem("ѧУ");
		Image image = new Image("/test");
		ZScript script = new ZScript("java","delItem()");
		image.addEventHandler("onClick", new EventHandler(script));
		Messagebox.show(Messagebox.ERROR, Messagebox.ON_OK, Messagebox.OK, null);
		
		combox.getItems().clear();
		String message = new String();
		List test = window6.getChildren();
		for(int i=0; i<test.size(); i++){
			XulElement el = (XulElement)test.get(i);
			message += (String)el.getAttribute("value");
		}
		
		Input input = new Input();
		Window window = new Window();
		
		char testA = 'a';
		switch(testA){
			case 'a':
				System.out.println(testA);
				break;
			default:
				//do nothing.
		}
		
		Integer.parseInt("");
		
		window.getFirstChild().setAttribute("value", "test");
		//out.print(Executions.getCurrent().getArg().get("message"));
	}
}
