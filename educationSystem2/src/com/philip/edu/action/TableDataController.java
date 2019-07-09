package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.DataManager;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;

public class TableDataController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TableDataController.class);
	private DataManager manager = new DataManager();
	private FormManager formManager = new FormManager();
	
	@Wire
	private Window window1;
	@Wire
	private Listbox dataList;
	private ArrayList datas;
	@Wire
	private Grid groupList;
	@Wire
	private Textbox search;
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		// TODO Auto-generated method stub
		
		return super.doBeforeCompose(page, parent, compInfo);
	}
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		groupList.setModel(new ListModelList<Group>(groups));
		
		String sForm = Executions.getCurrent().getParameter("form_id");
		logger.info("form_id:" + sForm);

		int form_id = Integer.parseInt(sForm);
		
		datas = manager.getTableData(form_id);
		//Executions.getCurrent().setAttribute("datas", datas);
		generateList();
		dataList.invalidate();
	}
	
	public void generateList(){
		logger.info("get in generate.");

		//dataList = new Listbox();
		if(datas == null) return;
		
		ArrayList caption = (ArrayList)datas.get(0);
		Listhead head = new Listhead();
		for(int i=0; i<caption.size(); i++){
			DataInfo data = (DataInfo)caption.get(i);
			Listheader header = new Listheader(data.getValue());
			head.appendChild(header);
		}
		dataList.appendChild(head);
		logger.info("Caption load correct");
		
		for(int j=1; j<datas.size(); j++){
			ArrayList line = (ArrayList)datas.get(j);
			Listitem item = new Listitem();
			for(int k=0; k<line.size(); k++){
				DataInfo data = (DataInfo)line.get(k);
				Listcell cell = new Listcell(data.getValue());
				item.appendChild(cell);
			}
			dataList.appendChild(item);
			logger.info("load one item data");
		}
		
		logger.info("data all loaded");
	}
	
	@Listen("onOK = #search")
	public void searchData(Event e){
		ArrayList newData = new ArrayList();
		dataList.getItems().clear();
		DataInfo data = null;
		
		/*ArrayList al = (ArrayList)datas.get(0);
		Listhead head = new Listhead();
		for(int j=0; j<al.size(); j++){
			data = (DataInfo) al.get(j);
			Listheader header = new Listheader(data.getValue());
			head.appendChild(header);
		}
		dataList.appendChild(head);*/
		
		for(int i=1; i<datas.size(); i++){
			ArrayList line = (ArrayList)datas.get(i);
			Listitem item = new Listitem();
			boolean bFound = false;
			
			for(int k=0; k<line.size(); k++){
				data = (DataInfo)line.get(k);
				Listcell cell = new Listcell(data.getValue());
				item.appendChild(cell);
				if(data.getValue().contains(search.getValue())){
					bFound = true;
				}
			}
			
			if(bFound)dataList.appendChild(item);
		}
			
	}
}
