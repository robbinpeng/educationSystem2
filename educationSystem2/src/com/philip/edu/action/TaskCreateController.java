package com.philip.edu.action;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.Task;
import com.philip.edu.database.DatabaseManager;

public class TaskCreateController extends SelectorComposer<Component>{
	
	private static Logger logger = Logger.getLogger(TableCreateController.class);
	private static FormManager formManager = new FormManager();
	
	@Wire
	private Window window;
	
	@Wire
	private Button closeBtn;
	
	@Wire
	private Button createBtn;
	
	@Wire
	private Textbox task_name;
	
	@Wire
	private Datebox start_time;
	
	@Wire
	private Datebox end_time;
	
	@Wire
	private Datebox stat_time;
	
	@Wire
	private Combobox study_year;
	private ListModelList listYear;
	private ArrayList al;
	
	@Wire
	private Textbox natural_year;
	
	@Wire
	private Datebox internal_stat_time;
	
	@Wire
	private Combobox task_status;
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
        window.detach();
    }
	
	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		
		task_status.setSelectedIndex(0);
		
		LocalDate now = LocalDate.now(ZoneId.systemDefault());	
		int currentYear = now.getYear();
		al = new ArrayList();
		for(int i=currentYear-10; i<currentYear+10; i++){
			String year = "" + (i) + "-" + (i+1);
			al.add(year);
		}	
		listYear = new ListModelList(al);
		study_year.setModel(listYear);
		listYear.addToSelection(al.get(10));
		
		/*for(int i=currentYear-10; i<currentYear+10; i++){
			Comboitem item = new Comboitem(""+i+"-"+(i+1));
			study_year.appendChild(item);
			if(i==currentYear)study_year.setSelectedItem(item);
		}*/
	}
	
	@Listen("onChange = #stat_time")
	public void selectTime() {
		Date st_time = stat_time.getValue();
		LocalDate local = st_time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = local.getYear() - 1;
		natural_year.setValue("" + year);
		
		for(int i=0; i<study_year.getItemCount(); i++){
			Comboitem item = study_year.getItemAtIndex(i);
			String chose_year = ((String)al.get(i)).substring(0, 4);
			//logger.info("year=" + chose_year);
			if(chose_year.equals("" +year)){listYear.addToSelection(al.get(i));logger.info("entered");}
		}
	}
	
	@Listen("onClick = #createBtn")
	public void createTask() {
		//check all filled:
		if(task_name.getValue()==null || "".equals(task_name.getValue()) || "必填项".equals(task_name.getValue())){Messagebox.show("任务名称不能为空！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(start_time.getValue()==null){Messagebox.show("必须选定开始时间！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(end_time.getValue()==null){Messagebox.show("必须选定结束时间！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(stat_time.getValue()==null){Messagebox.show("必须选择统计时间！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(natural_year.getValue()==null || "".equals(natural_year.getValue()) || "请输入(yyyy)".equals(natural_year.getValue())){Messagebox.show("必须输入自然年！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		if(internal_stat_time.getValue()==null){Messagebox.show("必须选择内部统计时间！","错误",Messagebox.OK,Messagebox.ERROR);return;}
		
		//save to database:
		Task task = new Task();
		task.setTask_name(task_name.getValue());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		task.setStart_time(sdf.format((Date)start_time.getValue()));
		task.setEnd_time(sdf.format((Date)end_time.getValue()));
		task.setStat_time(sdf.format((Date)stat_time.getValue()));
		task.setStudy_year(study_year.getSelectedItem().getLabel());
		task.setNatural_year(natural_year.getValue());
		task.setInternal_stat_time(sdf.format((Date)internal_stat_time.getValue()));
		task.setTask_status(task_status.getSelectedItem().getValue().toString().charAt(0));
		
		boolean b = formManager.createTask(task);
		if(b){
			Messagebox.show("任务成功创建！","信息",Messagebox.OK, Messagebox.INFORMATION);
			
			Listbox pList = (Listbox)Path.getComponent("/tWindow/tasklist");
			List<Task> tasks = formManager.getTaskList();
			pList.setModel(new ListModelList<Task>(tasks));
			window.detach();
		} else {
			Messagebox.show("后台发生错误，任务创建失败！","错误", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
