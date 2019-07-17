package com.philip.edu.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.DataManager;
import com.philip.edu.basic.Form;
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
	@Wire
	private Button create;
	@Wire
	private Button update;
	@Wire
	private Button delete;
	private ArrayList checked = new ArrayList();
	private Form form;
	private int form_id;
	private File file;
	
	private Listbox storedList;
	
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

		form_id = Integer.parseInt(sForm);
		
		form = formManager.getFormById(form_id);
		datas = manager.getTableData(form_id);
		//Executions.getCurrent().setAttribute("datas", datas);
		generateList();
		dataList.invalidate();
		
		checked = new ArrayList();
	}
	
	public void generateList(){
		logger.info("get in generate.");

		int fileColumn=0;
		//dataList = new Listbox();
		if(datas == null) return;
		
		dataList.setSizedByContent(true);
		
		ArrayList caption = (ArrayList)datas.get(0);
		Listhead head = new Listhead();
		head.setSizable(true);
		Listheader empty = new Listheader();
		empty.setWidth("3%");
		head.appendChild(empty);
		for(int i=0; i<caption.size(); i++){
			DataInfo data = (DataInfo)caption.get(i);
			FormField field = formManager.getFieldByPhysicName(form_id, data.getKey());
			if(field.getDis_method()==Constants.V_DISPLAY_UPLOAD_CONTROL)fileColumn = i+1;
			Listheader header = new Listheader(data.getValue());
			head.appendChild(header);
		}
		dataList.appendChild(head);
		logger.info("Caption load correct");
		
		logger.info("fileColumn: " + fileColumn);
		for(int j=1; j<datas.size(); j++){
			ArrayList line = (ArrayList)datas.get(j);
			Listitem item = new Listitem();
			//add checkbox:
			Listcell cell1 = new Listcell();
			Checkbox box = new Checkbox();
			
			DataInfo dInfo = (DataInfo)line.get(0);
			box.setId(dInfo.getValue());
			//item.setId("item" + dInfo.getValue());
								
			cell1.appendChild(box);
			item.appendChild(cell1);
			
			for(int k=1; k<line.size(); k++){
				DataInfo data = (DataInfo)line.get(k);
				Listcell cell = null;
				if(data.getKey()!=null&& "URL".equals(data.getKey())){
					String path = data.getUrl();
					String name = data.getValue();
					Button b = new Toolbarbutton(name);
					//logger.info(path);
					//file = new File(path);
					ZScript script = new ZScript("java","File file = new File(\"" +path+ "\");Filedownload.save(file,null)");
			        EventHandler evthdl = new EventHandler(script);
			        b.addEventHandler("onClick", evthdl);
			        cell = new Listcell();
			        cell.appendChild(b);
				}
				else cell = new Listcell(data.getValue());
				item.appendChild(cell);
			}
			dataList.appendChild(item);
			logger.info("load one item data");
		}
		
		logger.info("data all loaded");
	}
	
	public void generateListData() {
		if(datas == null) return;
		
		dataList.getItems().clear();
		
		for(int j=1; j<datas.size(); j++){
			ArrayList line = (ArrayList)datas.get(j);
			Listitem item = new Listitem();
			//add checkbox:
			Listcell cell1 = new Listcell();
			Checkbox box = new Checkbox();
			
			DataInfo dInfo = (DataInfo)line.get(0);
			box.setId(dInfo.getValue());
								
			cell1.appendChild(box);
			item.appendChild(cell1);
			
			for(int k=1; k<line.size(); k++){
				DataInfo data = (DataInfo)line.get(k);
				Listcell cell = null;
				if(data.getKey()!=null&& "URL".equals(data.getKey())){
					String path = data.getUrl();
					String name = data.getValue();
					Button b = new Toolbarbutton(name);
					//logger.info(path);
					//file = new File(path);
					ZScript script = new ZScript("java","File file = new File(\"" +path+ "\");Filedownload.save(file,null)");
			        EventHandler evthdl = new EventHandler(script);
			        b.addEventHandler("onClick", evthdl);
			        cell = new Listcell();
			        cell.appendChild(b);
				}
				else cell = new Listcell(data.getValue());
				item.appendChild(cell);
			}
			dataList.appendChild(item);
			logger.info("load one item data");
		}
	}
	
/*	@Listen("onOK = #search")
	public void searchData(Event e){
		ArrayList newData = new ArrayList();
		dataList.getItems().clear();
		DataInfo data = null;
		
		ArrayList al = (ArrayList)datas.get(0);
		Listhead head = new Listhead();
		for(int j=0; j<al.size(); j++){
			data = (DataInfo) al.get(j);
			Listheader header = new Listheader(data.getValue());
			head.appendChild(header);
		}
		dataList.appendChild(head);
		
		for(int i=1; i<datas.size(); i++){
			ArrayList line = (ArrayList)datas.get(i);
			Listitem item = new Listitem();
			boolean bFound = false;
			
			//add checkbox:
			Listcell cell1 = new Listcell();
			Checkbox box = new Checkbox();
			
			DataInfo dInfo = (DataInfo)line.get(0);
			box.setId(dInfo.getValue());
			cell1.appendChild(box);
			item.appendChild(cell1);
			
			for(int k=1; k<line.size(); k++){
				data = (DataInfo)line.get(k);
				Listcell cell = new Listcell(data.getValue());
				item.appendChild(cell);
				if(data.getValue().contains(search.getValue())){
					bFound = true;
				}
			}
			
			if(bFound)dataList.appendChild(item);
		}
		
			
	}*/
	
	@Listen("onOK = #search")
	public void searchData(Event e){
		generateListData();
		
		List<Listitem> listItemList = dataList.getItems();
		List<Listcell> listCellList = null;
		boolean bFound = false;
		
		for(int i =0; i<listItemList.size(); i++){
			listCellList = listItemList.get(i).getChildren();
			bFound = false;
			
			for(int j=0; j<listCellList.size(); j++){
				Listcell cell = (Listcell)listCellList.get(j);
				if(cell.getLabel().contains(search.getValue())){
					bFound = true;
					break;
				}
			}
			
			if(bFound){
				
			} else {
				listItemList.remove(i);
				i--;
			}

		}
		
	}
	
	@Listen("onOK = #search1")
	public void searchData1(Event e){
		ArrayList newData = new ArrayList();
		//dataList.getItems().clear();
		DataInfo data = null;
	
		Listhead head = (Listhead)dataList.getFirstChild();
		Listitem item = (Listitem)head.getNextSibling();
		
		while(item!=null){
			boolean bFound = false;
			
			List<Listcell> dataAL = item.getChildren();
			for(int j=0; j<dataAL.size(); j++){
				Listcell cell = (Listcell)dataAL.get(j);
				logger.info("cell info:" + j + ":" + cell.getLabel());
				if(cell.getLabel().contains(search.getValue())){
					bFound = true;
					break;
				}
			}
			
			if(bFound){
				if(!(item.getNextSibling() instanceof Listitem))item = (Listitem)item.getNextSibling().getNextSibling();
				else item = (Listitem) item.getNextSibling();
			} else {
				Listitem temp = item;
				Object obj = item.getNextSibling();
				if(obj instanceof Listitem){
					Listitem temp1 = (Listitem) item.getNextSibling();
					dataList.removeChild(temp);
					item = temp1;
				} else {
					dataList.removeChild(temp);
					break;
				}
			}
		}
	}
	
	@Listen("onCheck = Checkbox")
	public void checkSelected(Event e) {
		//Messagebox.show("checked");
		logger.info("event:" + e);
		Checkbox ck = (Checkbox)e.getTarget();
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
			if(ck.isChecked()){logger.info("checked id:" + ck.getId());checked.add(ck.getId());}
			else{}
		}
	}
	
	@Listen("onClick = #create")
	public void createRecord(Event e){
		HashMap map = new HashMap();
		map.put("form_id", form.getId());
		
		Window window2 = (Window) Executions.createComponents("/new_record.zul", null, map);
		
		window2.doModal();
	}
	
	@Listen("onClick = #update")
	public void updateRecord(Event e){
		if(checked==null || checked.size()==0){Messagebox.show("没有选中任何记录！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(checked.size()>1){Messagebox.show("一次只能修改一条记录！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		String sid = (String)checked.get(0);
		int id = Integer.parseInt(sid);
		
		HashMap map = new HashMap();
		map.put("form_id", form.getId());
		map.put("id", id);
		
		Window window3 = (Window) Executions.createComponents("/update_record.zul", null, map);
		
		window3.doModal();
	}
	
	@Listen("onClick = #delete")
	public void deleteRecords(Event e){
		if(checked.size()==0)Messagebox.show("没有选中任何记录！","错误",Messagebox.OK,Messagebox.ERROR);
		else{
			Messagebox.show("你确定要删除这些记录吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
				 public void onEvent(Event e) throws InterruptedException {
					 if(e.getName().equals("onOK")){
						 //Messagebox.show("删除了");
						 manager.deleteRecords(form.getPhsic_name(), checked);
						 Executions.getCurrent().sendRedirect("");
					 }else{
						 
					 }
				 }
			});
			
		}
		
	}
	
}
