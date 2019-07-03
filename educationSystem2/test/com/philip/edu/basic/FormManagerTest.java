package com.philip.edu.basic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.zkoss.zk.ui.Executions;

public class FormManagerTest {
	private FormManager manager = new FormManager();

	@Test
	public void testGetGroups() {
		ArrayList groups = manager.getGroups(Constants.USER_ID);
		
		assertEquals(groups.size(), 9);
	}
	
	@Test
	public void testGetFormsByGroup() {
		ArrayList forms = manager.getFormsByGroup(1);
		
		assertNotEquals(forms.size(), 0);
	}
	
	@Test
	public void testGetForms() {
		ArrayList forms = manager.getForms(Constants.USER_ID);
		
		assertNotEquals(forms.size(), 0);		
		//print some:
		for(int i=0; i<forms.size(); i++){
			Form form = (Form)forms.get(i);
			System.out.println("form_id:" + form.getId());
			System.out.println("form name:" + form.getTbl_name());
			System.out.println("form bussiness name:" + form.getBus_name());
			System.out.println("form status:" + form.getStatus().getStatus());
		}	
	}
	
	@Test
	public void testGetFields() {
		ArrayList fields = manager.getFormFields(Constants.FORM_ID);
		
		assertNotEquals(fields.size(), 0);
		//print some:
		for(int i=0; i<fields.size(); i++){
			FormField field = (FormField)fields.get(i);
			System.out.println("sequence: " + field.getSequence());
			System.out.println("field name: " + field.getBus_name());
			System.out.println("field physic name: " + field.getPhysic_name());
		}
	}

	@Test
	public void testGetFormFieldsByTableName(){
		ArrayList fields = manager.getFormFieldsByFormName(Constants.USER_ID, Constants.FORM_NAME);
		
		assertNotEquals(fields.size(),0);
		//print some:
				for(int i=0; i<fields.size(); i++){
					FormField field = (FormField)fields.get(i);
					System.out.println("sequence: " + field.getSequence());
					System.out.println("field name: " + field.getBus_name());
					System.out.println("field physic name: " + field.getPhysic_name());
				}
	}
	
	@Test
	public void testSaveRule(){
		Rule rule = new Rule();
		rule.setUser_id(Constants.USER_ID);
		rule.setForm_id(Constants.FORM_ID);
		rule.setRule_seq(1);
		rule.setRule_class(Constants.RULE_CLASS);
		rule.setRule_active(Constants.RULE_ACTIVE);
		rule.setRule_content("test");
		rule.setRule_name("test rule");
		rule.setRule_pattern('T');
		
		//manager.saveRule(rule);
	}
	
	@Test
	public void testGetFormByName(){
		String name = Constants.FORM_NAME;
		
		Form form = manager.getFormByName(Constants.USER_ID, name);
		
		assertNotEquals(form, null);
		
		System.out.println(form.getBus_name());
		System.out.println(form.getTbl_name());
		System.out.println(form.getPhsic_name());
		System.out.println("form by name status:"+ form.getStatus().getStatus());
			
	}
	
	@Test
	public void testGetFieldByPhysicName(){
		String field_name = Constants.FIELD_NAME;
		FormField field = manager.getFieldByPhysicName(Constants.FORM_ID_TEST, field_name);
		
		assertNotEquals(field, null);
		
		System.out.println(field.getBus_name());
		System.out.println(field.getPhysic_name());
		System.out.println(field.getData_type());
	}
	
	@Test 
	public void testGetFormById(){
		int id = Constants.FORM_ID;
		
		Form form = manager.getFormById(id);
		
		assertNotEquals(form, null);
		
		System.out.println(form.getBus_name());
		System.out.println(form.getTbl_name());
		System.out.println(form.getPhsic_name());
		System.out.println(form.getStatus().getStatus());
	}
}
