package com.philip.edu.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;

public class ChosenboxViewModel {
	private List<Form> forms = this.getFormList();
	private static FormManager formManager = new FormManager();
	private ListModelList<Form> formModel = new ListModelList<Form>(forms);
	private String filter="";
	
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter==null?"":filter.trim();
	}

	public ListModelList<Form> getFormModel(){
		return new ListModelList<Form>(forms);
	}

	private List<Form> getFormList(){
		return formManager.getForms(Constants.USER_ID);
	}
	
	 @Command
	 @NotifyChange("formModel")
	 public void changeFilter() {
	     forms = this.getFilterForms(filter);
	 }
	 
	 public List<Form> getFilterForms(String filter) {
		 List<Form> newList = new ArrayList<Form>();
		 
		 List<Form> allForms = getFormList();
		 
		 for(Iterator<Form> i=allForms.iterator(); i.hasNext();){
			 Form temp = i.next();
			 if(temp.getBus_name().contains(filter)){
				 newList.add(temp);
			 }
		 }
		 
		 return newList;
	 }
}
