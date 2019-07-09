package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

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
	private Listbox dataList;
	private ArrayList datas;
	@Wire
	private Grid groupList;
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		// TODO Auto-generated method stub

		String sForm = Executions.getCurrent().getParameter("form_id");
		logger.info("form_id:" + sForm);

		int form_id = Integer.parseInt(sForm);
		
		datas = manager.getTableData(form_id);
		Executions.getCurrent().setAttribute("datas", datas);
		//generateList();
		
		return super.doBeforeCompose(page, parent, compInfo);
	}
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		groupList.setModel(new ListModelList<Group>(groups));
	
	}
	
	public void generateList(){
		logger.info("get in generate.");

		dataList = new Listbox();
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
		}
	}
}
