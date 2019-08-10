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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.DataManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.School;
import com.philip.edu.basic.Task;

public class PagingDataController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TableDataController.class);
	private DataManager manager = new DataManager();
	private FormManager formManager = new FormManager();

	@Wire
	private Window window1;
	@Wire
	private Listbox dataList;
	@Wire
	private Paging dataPage;
	private ArrayList data_page;
	private ListModelList<ArrayList> model;
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
	@Wire
	private Checkbox show_s;
	@Wire
	private Combobox choseSchool;
	@Wire
	private Combobox choseTJSJ;
	@Wire
	private Checkbox checkAll;
	
	private int number=0;

	private Form form;
	private int form_id;
	private File file;
	private ArrayList fields;
	private int fileColumn;

	private Listbox storedList;

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
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
		fields = formManager.getFormFields(form_id);
		// Executions.getCurrent().setAttribute("datas", datas);
		// Generate first Page:
		Listhead head = new Listhead();
		Listheader empty = new Listheader();
		empty.setWidth("3%");
		Checkbox temp = new Checkbox();
		temp.setId("check0");
		empty.appendChild(temp);
		head.appendChild(empty);
		int index = 0;
		Listheader header0 = new Listheader("配置学校名称");
		head.appendChild(header0);
		header0 = new Listheader("内部统计时间");
		head.appendChild(header0);

		for (int i = 0; i < fields.size(); i++) {
			FormField field = (FormField) fields.get(i);
			if (field.getIs_hidden() == 'N') {
				Listheader header = new Listheader(field.getBus_name());
				head.appendChild(header);
				if (field.getDis_method() == Constants.V_DISPLAY_UPLOAD_CONTROL)
					fileColumn = index + 1;
				index++;
			}
		}
		dataList.appendChild(head);

		data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), 1, true, "all", "all");
		model = new ListModelList<ArrayList>(data_page);
		dataList.setModel(model);
		dataList.setItemRenderer(new myItemRenderer());
		
		number = manager.getTableDataNumber(form.getPhsic_name(), true, "all", "all");
		dataPage.setTotalSize(number);

		dataPage.addEventListener("onPaging", new EventListener<Event>() {

			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				PagingEvent pe = (PagingEvent) event;

				redraw(pe.getActivePage() + 1, dataPage.getPageSize());
			}
		});

		Comboitem item = new Comboitem("全部学校");
		item.setValue("all");
		choseSchool.appendChild(item);
		choseSchool.setSelectedItem(item);
		ArrayList al = formManager.getAllSchool();
		for (int i = 0; i < al.size(); i++) {
			School school = (School) al.get(i);
			item = new Comboitem(school.getSchool_name());
			item.setValue(school.getSchool_name());
			choseSchool.appendChild(item);
		}

		Comboitem item1 = new Comboitem("全部时间");
		item1.setValue("all");
		choseTJSJ.appendChild(item1);
		choseTJSJ.setSelectedItem(item1);
		al = formManager.getTaskList();
		for (int i = 0; i < al.size(); i++) {
			Task task = (Task) al.get(i);
			item1 = new Comboitem(task.getInternal_stat_time());
			item1.setValue(task.getInternal_stat_time());
			choseTJSJ.appendChild(item1);
		}
	}
	
	@Listen("onCheck = Checkbox")
	public void checkAll(Event e){
		Checkbox ck = (Checkbox)e.getTarget();
		if("check0".equals(ck.getId())){
			for(int i=1; i<dataList.getItemCount()+1; i++){
				Listitem item = (Listitem)dataList.getChildren().get(i);
				Checkbox box = (Checkbox)item.getFirstChild().getFirstChild();
				box.setChecked(ck.isChecked());
			}
		}
	}

	@Listen("onChange = #choseTJSJ")
	public void chose_TJSJ(Event e) {
		// data:
		// 清空所有数据
		dataList.getItems().clear();
		data_page.clear();
		
		if (search.getValue() != null && !search.getValue().equals("")){
			number = manager.getSearchDataNumber(fields, form.getPhsic_name(), search.getValue(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			dataPage.setTotalSize(number);
			
			data_page = manager.searchDataByPage(fields, form.getPhsic_name(), 1, search.getValue(), show_s.isChecked(),
					choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}
		else {
			number = manager.getTableDataNumber(form.getPhsic_name(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			dataPage.setTotalSize(number);
			
			data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), 1, show_s.isChecked(),
					choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}

		model = new ListModelList<ArrayList>(data_page);
		dataList.setModel(model);
	}

	@Listen("onChange = #choseSchool")
	public void chose_school(Event e) {
		// Messagebox.show(choseSchool.getSelectedItem().getLabel());
		// data:
		// 清空所有数据
		dataList.getItems().clear();
		data_page.clear();

		// int total = activePage * pageSize;

		if (search.getValue() != null && !search.getValue().equals("")){
			number = manager.getSearchDataNumber(fields, form.getPhsic_name(), search.getValue(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			dataPage.setTotalSize(number);
			
			data_page = manager.searchDataByPage(fields, form.getPhsic_name(), 1, search.getValue(), true,
					choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}
		else {
			number = manager.getTableDataNumber(form.getPhsic_name(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			dataPage.setTotalSize(number);
			
			data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), 1, true,
					choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}
		
		model = new ListModelList<ArrayList>(data_page);
		dataList.setModel(model);
	}

	@Listen("onCheck = #show_s")
	public void show_school(Event e) {
		if (show_s.isChecked()) {
			// Messagebox.show("显示学校");
			// header:
			Listhead myHead = dataList.getListhead();
			List<Listheader> tempHeader = ((Listhead) myHead.clone()).getChildren();

			// myHead = new Listhead();myHead.insertBefore(arg0, arg1)
			Listheader header = new Listheader("配置学校名称");
			myHead.insertBefore(header, myHead.getChildren().get(1));
			// myHead.appendChild(header);

			/*
			 * for(int i=0; i<tempHeader.size(); i++){ Listheader header0 =
			 * tempHeader.get(i); myHead.appendChild(header0); }
			 */

			// data:
			// 清空所有数据
			dataList.getItems().clear();
			data_page.clear();

			// int total = activePage * pageSize;

			if (search.getValue() != null && !search.getValue().equals("")){
				number = manager.getSearchDataNumber(fields, form.getPhsic_name(), search.getValue(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
				dataPage.setTotalSize(number);
				
				data_page = manager.searchDataByPage(fields, form.getPhsic_name(), 1, search.getValue(), true,
						choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			}
			else{
				number = manager.getTableDataNumber(form.getPhsic_name(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
				dataPage.setTotalSize(number);
				
				data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), 1, true,
						choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			}

			model = new ListModelList<ArrayList>(data_page);
			dataList.setModel(model);

		} else {
			// Messagebox.show("关闭学校");
			// header:
			Listhead myHead = dataList.getListhead();
			List<Listheader> tempHeader = myHead.getChildren();

			for (int i = 0; i < tempHeader.size(); i++) {
				Listheader header = (Listheader) tempHeader.get(i);
				if ("配置学校名称".equals(header.getLabel())) {
					tempHeader.remove(header);
					break;
				}
			}

			// data:
			// 清空所有数据
			dataList.getItems().clear();
			data_page.clear();

			// int total = activePage * pageSize;

			if (search.getValue() != null && !search.getValue().equals("")){
				number = manager.getSearchDataNumber(fields, form.getPhsic_name(), search.getValue(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
				dataPage.setTotalSize(number);
				
				data_page = manager.searchDataByPage(fields, form.getPhsic_name(), 1, search.getValue(), false,
						choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			}
			else {
				number = manager.getTableDataNumber(form.getPhsic_name(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
				dataPage.setTotalSize(number);
				
				data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), 1, false,
						choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			}
			model = new ListModelList<ArrayList>(data_page);
			dataList.setModel(model);
		}
	}

	private void redraw(int activePage, int pageSize) {

		// 清空所有数据
		dataList.getItems().clear();
		data_page.clear();

		int total = activePage * pageSize;
		

		if (search.getValue() != null && !search.getValue().equals("")){
			number = manager.getSearchDataNumber(fields, form.getPhsic_name(), search.getValue(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			dataPage.setTotalSize(number);
			
			data_page = manager.searchDataByPage(fields, form.getPhsic_name(), activePage, search.getValue(),
					show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}
		else {
			number = manager.getTableDataNumber(form.getPhsic_name(), show_s.isChecked(), choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
			//logger.info("total size:" + number);
			dataPage.setTotalSize(number);
			
			data_page = manager.getTableDataByPage(fields, form.getPhsic_name(), activePage, show_s.isChecked(),
					choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		}

		model = new ListModelList<ArrayList>(data_page);
		dataList.setModel(model);
	}

	public class myItemRenderer implements ListitemRenderer {

		// @Override
		public void render(Listitem item, Object data, int index) throws Exception {
			// TODO Auto-generated method stub
			ArrayList line = (ArrayList) data;
			// add checkbox:
			Listcell cell1 = new Listcell();
			Checkbox box = new Checkbox();

			DataInfo dInfo = (DataInfo) line.get(0);
			box.setId(dInfo.getValue());
			// item.setId("item" + dInfo.getValue());

			cell1.appendChild(box);
			item.appendChild(cell1);

			for (int k = 1; k < line.size(); k++) {
				DataInfo data1 = (DataInfo) line.get(k);
				Listcell cell = null;
				if (data1.getKey() != null && "URL".equals(data1.getKey())) {
					String path = data1.getUrl();
					String name = data1.getValue();
					Button b = new Toolbarbutton(name);
					// logger.info(path);
					// file = new File(path);
					ZScript script = new ZScript("java",
							"File file = new File(\"" + path + "\");Filedownload.save(file,null)");
					EventHandler evthdl = new EventHandler(script);
					b.addEventHandler("onClick", evthdl);
					cell = new Listcell();
					cell.appendChild(b);
				} else
					cell = new Listcell(data1.getValue());
				// System.out.println(data1.getValue());
				item.appendChild(cell);
			}
		}
	}

	/*
	 * @Listen("onOK = #search") public void searchData(Event e){ ArrayList
	 * newData = new ArrayList(); dataList.getItems().clear(); DataInfo data =
	 * null;
	 * 
	 * ArrayList al = (ArrayList)datas.get(0); Listhead head = new Listhead();
	 * for(int j=0; j<al.size(); j++){ data = (DataInfo) al.get(j); Listheader
	 * header = new Listheader(data.getValue()); head.appendChild(header); }
	 * dataList.appendChild(head);
	 * 
	 * for(int i=1; i<datas.size(); i++){ ArrayList line =
	 * (ArrayList)datas.get(i); Listitem item = new Listitem(); boolean bFound =
	 * false;
	 * 
	 * //add checkbox: Listcell cell1 = new Listcell(); Checkbox box = new
	 * Checkbox();
	 * 
	 * DataInfo dInfo = (DataInfo)line.get(0); box.setId(dInfo.getValue());
	 * cell1.appendChild(box); item.appendChild(cell1);
	 * 
	 * for(int k=1; k<line.size(); k++){ data = (DataInfo)line.get(k); Listcell
	 * cell = new Listcell(data.getValue()); item.appendChild(cell);
	 * if(data.getValue().contains(search.getValue())){ bFound = true; } }
	 * 
	 * if(bFound)dataList.appendChild(item); }
	 * 
	 * 
	 * }
	 */

	@Listen("onOK = #search")
	public void searchData(Event e) {

		dataList.getItems().clear();
		data_page.clear();

		data_page = manager.searchDataByPage(fields, form.getPhsic_name(), 1, search.getValue(), show_s.isChecked(),
				choseSchool.getSelectedItem().getValue().toString(), choseTJSJ.getSelectedItem().getValue().toString());
		model = new ListModelList<ArrayList>(data_page);
		dataList.setModel(model);

	}

	@Listen("onOK = #search1")
	public void searchData1(Event e) {
		ArrayList newData = new ArrayList();
		// dataList.getItems().clear();
		DataInfo data = null;

		Listhead head = (Listhead) dataList.getFirstChild();
		Listitem item = (Listitem) head.getNextSibling();

		while (item != null) {
			boolean bFound = false;

			List<Listcell> dataAL = item.getChildren();
			for (int j = 0; j < dataAL.size(); j++) {
				Listcell cell = (Listcell) dataAL.get(j);
				// logger.info("cell info:" + j + ":" + cell.getLabel());
				if (cell.getLabel().contains(search.getValue())) {
					bFound = true;
					break;
				}
			}

			if (bFound) {
				if (!(item.getNextSibling() instanceof Listitem))
					item = (Listitem) item.getNextSibling().getNextSibling();
				else
					item = (Listitem) item.getNextSibling();
			} else {
				Listitem temp = item;
				Object obj = item.getNextSibling();
				if (obj instanceof Listitem) {
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

	@Listen("onClick = #create")
	public void createRecord(Event e) {
		HashMap map = new HashMap();
		map.put("form_id", form.getId());

		Window window2 = (Window) Executions.createComponents("/new_record.zul", null, map);

		window2.doModal();
		map = null;
	}

	@Listen("onClick = #update")
	public void updateRecord(Event e) {
		/*if (checked == null || checked.size() == 0) {
			Messagebox.show("没有选中任何记录！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (checked.size() > 1) {
			Messagebox.show("一次只能修改一条记录！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}*/
		int chosed = 0;
		String sid = "";
		for(int i=1; i<dataList.getItemCount(); i++){
			Listitem item = (Listitem)dataList.getItemAtIndex(i);
			Checkbox ck = (Checkbox)item.getFirstChild().getFirstChild();
			if(ck.isChecked()){
				if(chosed > 0){
					Messagebox.show("一次只能修改一条记录！", "错误", Messagebox.OK, Messagebox.ERROR);
					return;
				}
				sid = ck.getId();
				chosed++;
			}
		}
		
		if(chosed == 0){
			Messagebox.show("没有选中任何记录！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		int id = Integer.parseInt(sid);

		HashMap map = new HashMap();
		map.put("form_id", form.getId());
		map.put("id", id);

		Window window3 = (Window) Executions.createComponents("/update_record.zul", null, map);

		window3.doModal();
		map = null;
	}

	@Listen("onClick = #delete")
	public void deleteRecords(Event e){
		ArrayList checked = new ArrayList();
		
		for(int i=1; i<dataList.getItemCount(); i++){
			Listitem item = (Listitem)dataList.getItemAtIndex(i);
			Checkbox ck = (Checkbox)item.getFirstChild().getFirstChild();
			if(ck.isChecked()){
				checked.add(ck.getId());
			}
		}
		
		if(checked.size()==0)Messagebox.show("没有选中任何记录！","错误",Messagebox.OK,Messagebox.ERROR);
		else{
			Messagebox.show("你确定要删除这些记录吗？","确定删除",Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){
				 public void onEvent(Event e) throws InterruptedException {
					 if(e.getName().equals("onOK")){
						 //Messagebox.show("删除了");
						 /*for(int j=0; j<checked.size(); j++){
							 String id = (String) checked.get(j);
							 Messagebox.show(id);
						 }*/
						 manager.deleteRecords(form.getPhsic_name(), checked);
						 Executions.getCurrent().sendRedirect("");
					 }else{
						 
					 }
				 }
			});   
			
		}
		
	}

}
