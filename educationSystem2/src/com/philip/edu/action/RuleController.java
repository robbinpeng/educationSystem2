package com.philip.edu.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;
import com.philip.edu.basic.Rule;
import com.philip.edu.rule.RuleManager;

public class RuleController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(RuleController.class);
	private RuleManager ruleManager = new RuleManager();
	private FormManager formManager = new FormManager();

	@Wire
	private Window mainWindow;

	@Wire
	private Listbox ruleList;
	private ListModelList<Rule> listModel;

	@Wire
	private Window window1;
	@Wire
	private Window window2;
	@Wire
	private Window window3;
	@Wire
	private Window window4;
	@Wire
	private Window window5;
	@Wire
	private Window window6;

	@Wire
	private Grid groupList;

	@Wire
	private Combobox rule_pattern;
	@Wire
	private Textbox rule_name;
	@Wire
	private Spinner rule_seq;
	@Wire
	private Textbox fail_information;
	boolean isCheck = false;
	
	@Wire
	private Tabbox tb;

	@Wire
	private Button addCon1;
	@Wire
	private Button addFormField1;
	@Wire
	private Button addOperator1;
	@Wire
	private Button addText1;
	@Wire
	private Button addFormField2;
	@Wire
	private Button addOp2;
	@Wire
	private Button addRelateForm2;
	@Wire
	private Button addCondition3;
	@Wire
	private Button addFormField3;
	@Wire
	private Button addOperator3;
	@Wire
	private Button addRelateForm3;
	@Wire
	private Button addRelateForm32;
	@Wire
	private Button addConditionR3;
	@Wire
	private Button addConditionNo3;
	@Wire
	private Button addCondition4;
	@Wire
	private Button addFormField4;
	@Wire
	private Button addOperator4;
	@Wire
	private Button addFormField5;
	@Wire
	private Button addForm5;
	@Wire
	private Button addRelateCondition;
	@Wire
	private Button addCondition6;
	@Wire
	private Button addFormField6;
	@Wire
	private Button addOperator6;
	@Wire
	private Button addRelaOp6;
	@Wire
	private Button addText6;
	@Wire
	private Button addYear6;
	@Wire
	private Button addMonth6;
	@Wire
	private Button addDay6;
	@Wire
	private Button btnSave;
	@Wire
	private Combobox dateFormat;
	@Wire
	private Checkbox multiple1;
	@Wire
	private Button cancelBtn;
	
	private createEventListener cel = new createEventListener();
	private Rule stored_rule;

	// @Wire
	// private Grid groupList;

	Div div = new Div();
	Combobox formCom = new Combobox();
	Combobox fieldCom = new Combobox();
	Combobox opCom = new Combobox();
	Combobox opCom2 = new Combobox();

	FormManager manager = new FormManager();
	Image image = new Image("/img/close.jpg");
	// forms:
	ArrayList forms = manager.getForms(Constants.USER_ID);

	// fields:
	String sForm_id = Executions.getCurrent().getParameter("form_id");
	int iForm_id = Integer.parseInt(sForm_id);
	ArrayList fields = manager.getFormFields(iForm_id);

	// operator:
	Comboitem item = new Comboitem(Constants.ADD);
	Comboitem itemOp = new Comboitem(Constants.AND);

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		List<Group> groups = formManager.getGroups(Constants.USER_ID);
		groupList.setModel(new ListModelList<Group>(groups));

		String sForm = Executions.getCurrent().getParameter("form_id");
		logger.info("sForm:" + sForm);
		Session session = Sessions.getCurrent();
		session.setAttribute("form_id", sForm);
		int form_id = Integer.parseInt(sForm);
		List<Rule> rules = ruleManager.getRules(form_id);
		listModel = new ListModelList<Rule>(rules);
		ruleList.setModel(listModel);

		String form_name = formManager.getFormById(form_id).getBus_name();
		session.setAttribute("form_name", form_name);

		// List<Group> groups = formManager.getGroups(Constants.USER_ID);
		// groupList.setModel(new ListModelList<Group>(groups));

		// generate the divs:
		for (int i = 0; i < forms.size(); i++) {
			com.philip.edu.basic.Form form = (com.philip.edu.basic.Form) forms.get(i);
			Comboitem item = new Comboitem(form.getBus_name());
			item.setValue(form.getTbl_name());
			formCom.appendChild(item);
			if (i == 0)
				formCom.setSelectedItem(item);
		}

		for (int i = 0; i < fields.size(); i++) {
			FormField field = (FormField) fields.get(i);
			Comboitem item = new Comboitem(field.getBus_name());
			item.setValue(field.getPhysic_name());
			fieldCom.appendChild(item);
			if (i == 0)
				fieldCom.setSelectedItem(item);
		}

		item.setValue(Constants.V_ADD);
		opCom.appendChild(item);
		item = new Comboitem(Constants.MINUS);
		item.setValue(Constants.V_MINUS);
		opCom.appendChild(item);
		item = new Comboitem(Constants.MULTIPL);
		item.setValue(Constants.V_MUTIPL);
		opCom.appendChild(item);
		item = new Comboitem(Constants.DIVIDE);
		item.setValue(Constants.V_DIVIDE);
		opCom.appendChild(item);
		item = new Comboitem(Constants.LESST);
		item.setValue(Constants.V_LESST);
		opCom.appendChild(item);
		item = new Comboitem(Constants.LESSTE);
		item.setValue(Constants.V_LESSTE);
		opCom.appendChild(item);
		item = new Comboitem(Constants.EQUAL);
		item.setValue(Constants.V_EQUAL);
		opCom.appendChild(item);
		opCom.setSelectedItem(item);
		item = new Comboitem(Constants.NOEQUAL);
		item.setValue(Constants.V_NOEQUAL);
		opCom.appendChild(item);
		item = new Comboitem(Constants.GREATT);
		item.setValue(Constants.V_GREATT);
		opCom.appendChild(item);
		item = new Comboitem(Constants.GREATTE);
		item.setValue(Constants.V_GREATTE);
		opCom.appendChild(item);

		itemOp.setValue(Constants.V_AND);
		opCom2.appendChild(itemOp);
		opCom2.setSelectedItem(itemOp);
		
		btnSave.addEventListener("onClick", (EventListener<? extends Event>) cel);
	}
	
	class createEventListener implements EventListener {
		public void onEvent(Event e){
			saveRules();
		}
	}

	@Listen("onDelete = #ruleList")
	public void doDeleteRule(Event event) {
		System.out.println("get in delete");

		System.out.print(event.getData());
		Rule rule = (Rule) event.getData();
		boolean is_success = ruleManager.deleteRule(rule.getId());
		if (is_success) {
			listModel.remove(rule);
		} else {
			Messagebox.show("删除规则失败！", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Listen("onActive = #ruleList")
	public void doChangeActive(Event event) {
		Rule rule = (Rule) event.getData();
		boolean is_success = ruleManager.changeActive(rule.getId());

		if (is_success) {
			listModel.remove(rule);
			if (rule.getRule_active() == 'Y')
				rule.setRule_active('N');
			else
				rule.setRule_active('Y');
			listModel.add(rule);
		} else {
			Messagebox.show("修改启动状态失败！", "错误", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// Session session = Sessions.getCurrent();
	// String sForm_id = session.getAttribute("form_id");
	// System.out.println("field size:" + fields.size());

	// operator 2:

	@Listen("onCheck = #multiple1")
	public void doCheck() {
		Messagebox.show("hello!");
		if (isCheck)
			isCheck = false;
		else
			isCheck = true;
	}

	@Listen("onUpdate = #ruleList")
	public void updateRule(Event e) {
		cancelDo();

		Rule rule = (Rule) e.getData();
		stored_rule = rule;

		for (int i = 0; i < rule_pattern.getItemCount(); i++) {
			Comboitem item = rule_pattern.getItemAtIndex(i);
			if (item.getValue().toString().charAt(0) == rule.getRule_pattern()) {
				rule_pattern.setSelectedItem(item);
			}
		}
		rule_name.setValue(rule.getRule_name());
		rule_seq.setValue(new Integer(rule.getRule_seq()));
		;
		fail_information.setValue(rule.getFail_information());

		int rule_class = rule.getRule_class();

		switch (rule_class) {
		case 1:
			tb.setSelectedIndex(0);
			translateRule1(rule.getRule_content());
			break;
		case 2:
			tb.setSelectedIndex(1);
			translateRule2(rule.getRule_content());
			break;
		case 3:
			tb.setSelectedIndex(2);
			translateRule3(rule.getRule_content());
			break;
		case 4:
			tb.setSelectedIndex(3);
			translateRule4(rule.getRule_content());
			break;
		case 5:
			tb.setSelectedIndex(4);
			translateRule5(rule.getRule_content());
			break;
		case 6:
			tb.setSelectedIndex(5);
			translateRule6(rule.getRule_content());
			break;
		default:
			break;
		}
		
		btnSave.setLabel("保存");
		btnSave.removeEventListener("onClick",  (EventListener<? extends Event>) cel );
		btnSave.addEventListener("onClick", (EventListener<? extends Event>) new saveEventListener());
	}
	
	class saveEventListener implements EventListener {
		public void onEvent(Event e){
			justSaveRule();
		}
	}
	
	private void justSaveRule() {
		boolean is_new = false;
		int number = 0;
		// window1:
		// window's rules:
		// Messagebox.show("winodw1");

		// basic check:
		if (rule_pattern.getSelectedItem() == null) {
			Messagebox.show("必须选择校验规则模式!", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (rule_name == null || "".equals(rule_name.getValue())) {
			Messagebox.show("必须输入校验规则名称！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (rule_seq == null || "".equals(rule_seq.getValue().toString())) {
			Messagebox.show("校验规则顺序必须输入！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (fail_information == null || "".equals(fail_information.getValue())) {
			Messagebox.show("必须输入检验失败信息！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// enter:
		Rule rule = stored_rule;
		//rule.setUser_id(Constants.USER_ID);
		Session session = Sessions.getCurrent();
		Integer sSeq = rule_seq.getValue();
		rule.setRule_seq(sSeq.intValue());
		rule.setFail_information(fail_information.getValue());
		rule.setRule_pattern(rule_pattern.getSelectedItem().getValue().toString().charAt(0));
		rule.setRule_name(rule_name.getValue());
		rule.setRule_active(Constants.RULE_ACTIVE);

		// window1:
		JSONObject rules = getWindowRules(window1);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule1(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(1);
			rule.setRule_content(rules.toString());

			/*
			 * System.out.println(rule.getUser_id());
			 * System.out.println(rule.getForm_id());
			 * System.out.println(rule.getRule_seq());
			 * System.out.println(rule.getFail_information());
			 * System.out.println(rule.getRule_pattern());
			 * System.out.println(rule.getRule_name());
			 * System.out.println(rule.getRule_active());
			 * System.out.println(rule.getRule_class());
			 * System.out.println(rule.getRule_content());
			 */

			manager.updateRule(rule);
			is_new = true;
		}
		// window2:
		// Messagebox.show("winodw2");
		rules = getWindowRules(window2);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时保存多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule2(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(2);
			rule.setRule_content(rules.toString());

			manager.updateRule(rule);
			is_new = true;
		}
		// window3:
		// Messagebox.show("winodw3");
		rules = getWindowRules(window3);
		// Messagebox.show("checked:" + isCheck);
		Checkbox ck = (Checkbox)window3.getChildren().get(0).getChildren().get(2);

		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时保存多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule3(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			logger.info("is checked:" + ck.isChecked());
			if (ck.isChecked()) {
				JSONObject mulObj = new JSONObject(true);
				mulObj.put("multiple", "yes").put("type", "multiple");
				JSONArray array = rules.getJSONArray("rules");
				array.put(mulObj);
				rules.put("rules", array);
			}

			number++;
			rule.setRule_class(3);
			rule.setRule_content(rules.toString());

			manager.updateRule(rule);
			is_new = true;
		}
		// window4:
		// Messagebox.show("winodw4");
		rules = getWindowRules(window4);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时保存多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule4(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(4);
			rule.setRule_content(rules.toString());

			manager.updateRule(rule);
			is_new = true;
		}
		// window5:
		// Messagebox.show("winodw5");
		rules = getWindowRules(window5);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时保存多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule5(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(5);
			rule.setRule_content(rules.toString());

			manager.updateRule(rule);
			is_new = true;
		}
		// window6:
		// Messagebox.show("winodw6");
		rules = getWindowRules(window6);
		System.out.println("get rules");
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时保存多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule6(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			number++;
			// System.out.println("rule checked.");
			// add format:
			List al = window6.getChildren();
			Div div = (Div) al.get(0);
			Combobox comDate = (Combobox) div.getLastChild();
			if (comDate.getSelectedItem() == null) {
				Messagebox.show("必须选择一种日期格式!", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			String format = comDate.getSelectedItem().getValue();
			JSONArray array = rules.getJSONArray("rules");
			JSONObject formatObj = new JSONObject();
			formatObj.put("format", format).put("type", Constants.RULE_DATE_FORMAT);
			array.put(formatObj);
			JSONObject newRule = new JSONObject();
			newRule.put("rules", array);

			//
			rule.setRule_class(6);
			rule.setRule_content(newRule.toString());

			manager.updateRule(rule);
			is_new = true;
		}

		if (is_new) {
			Messagebox.show("规则成功保存！", "信息", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} else
			Messagebox.show("没有规则被保存!", "错误", Messagebox.OK, Messagebox.ERROR);
	}

	private void translateRule1(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_CONDITION.equals(type)) {
				addCondition1();
				Div div = divCon1[indexCon1 - 1];
				// field:
				String field = obj.getString("field");
				Combobox fieldCom1 = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom1.getItemCount(); j++) {
					Comboitem item = fieldCom1.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom1.setSelectedItem(item);
				}
				// operator:
				String op = obj.getString("operator");
				Combobox opCom1 = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < opCom1.getItemCount(); j++) {
					Comboitem item = opCom1.getItemAtIndex(j);
					if (op.equals(item.getValue().toString()))
						opCom1.setSelectedItem(item);
				}
				// value:
				String value = obj.getString("value");
				Textbox tb = (Textbox) div.getChildren().get(3);
				tb.setValue(value);
			} else if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField1();
				String field = obj.getString("field");
				Div div = divField1[indexField1 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_OPERATOR.equals(type)) {
				addOperator1();
				String operator = obj.getString("operator");
				Div div = divOp1[indexOp1 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (operator.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_TEXTBOX.equals(type)) {
				addText1();
				String text = obj.getString("value");
				Div div = divText1[indexText1 - 1];
				Textbox textB = (Textbox) div.getChildren().get(1);
				textB.setValue(text);
			}
		}
	}

	private void translateRule2(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField2();
				String field = obj.getString("field");
				Div div = divField2[indexField2 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_OP_AND.equals(type)) {
				addOp2();
				String operator = obj.getString("operator1");
				Div div = divOp2[indexOp2 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (operator.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_RELATE_FORM.equals(type)) {
				addRelateForm2();
				String formName = obj.getString("relateForm");
				String fieldName = obj.getString("relateField");
				Div div = divRForm2[indexRForm2-1];
				Combobox formCom = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < formCom.getItemCount(); j++) {
					Comboitem item = formCom.getItemAtIndex(j);
					if (formName.equals(item.getValue().toString()))
						formCom.setSelectedItem(item);
				}
				loadFields(indexRForm2-1);
				Combobox fieldCom = (Combobox) div.getChildren().get(3);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (fieldName.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			}
		}
	}
	
	private void translateRule3(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_CONDITION.equals(type)) {
				addCondition3();
				Div div = divCon3[indexCon3 - 1];
				// field:
				String field = obj.getString("field");
				Combobox fieldCom1 = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom1.getItemCount(); j++) {
					Comboitem item = fieldCom1.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom1.setSelectedItem(item);
				}
				// operator:
				String op = obj.getString("operator");
				Combobox opCom1 = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < opCom1.getItemCount(); j++) {
					Comboitem item = opCom1.getItemAtIndex(j);
					if (op.equals(item.getValue().toString()))
						opCom1.setSelectedItem(item);
				}
				// value:
				String value = obj.getString("value");
				Textbox tb = (Textbox) div.getChildren().get(3);
				tb.setValue(value);
			} else if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField3();
				String field = obj.getString("field");
				Div div = divField3[indexField3 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_OR.equals(type)) {
				addOperator3();
				String operator = obj.getString("value");
				Div div = divOp3[indexOp3 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (operator.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_RELATE_FORM.equals(type)) {
				addRelateForm3();
				String formName = obj.getString("relateForm");
				String fieldName = obj.getString("relateField");
				Div div = divRForm3[indexRForm3-1];
				Combobox formCom = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < formCom.getItemCount(); j++) {
					Comboitem item = formCom.getItemAtIndex(j);
					if (formName.equals(item.getValue().toString()))
						formCom.setSelectedItem(item);
				}
				loadFields3(indexRForm3-1);
				Combobox fieldCom = (Combobox) div.getChildren().get(3);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (fieldName.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_RELATE_FORM_TWO.equals(type)) {
				addRelateForm32();
				String formName = obj.getString("relateForm");
				String field1Name = obj.getString("relateField1");
				String field2Name = obj.getString("relateField2");
				Div div = divRForm32[indexRForm32-1];
				Combobox formCom = (Combobox) div.getChildren().get(2);
				for (int j=0; j< formCom.getItemCount(); j++) {
					Comboitem item = formCom.getItemAtIndex(j);
					if(formName.equals(item.getValue().toString()))formCom.setSelectedItem(item);
				}
				loadFields32(indexRForm32 -1);
				Combobox fieldCom1 = (Combobox) div.getChildren().get(3);
				for (int j=0; j< fieldCom1.getItemCount(); j++){
					Comboitem item = fieldCom1.getItemAtIndex(j);
					if(field1Name.equals(item.getValue().toString()))fieldCom1.setSelectedItem(item);
				}
				Combobox fieldCom2 = (Combobox) div.getChildren().get(4);
				for(int j=0; j<fieldCom2.getItemCount(); j++) {
					Comboitem item = fieldCom2.getItemAtIndex(j);
					if(field2Name.equals(item.getValue().toString()))fieldCom2.setSelectedItem(item);
				}
			} else if (Constants.RULE_FIELD_EQUAL.equals(type)) {
				addConditionR3();
				String field = obj.getString("field");
				String value = obj.getString("value");
				Div div = divConR3[indexConR3 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
				Textbox tbValue = (Textbox) div.getChildren().get(4);
				tbValue.setValue(value);
			} else if (Constants.RULE_NO.equals(type)) {
				addConditionNo3();
			} else if (Constants.RULE_MULTIPLE.equals(type)){
				logger.info("multiple:" + multiple1);
				Checkbox multi = (Checkbox)window3.getChildren().get(0).getChildren().get(2);
				multi.setChecked(true);
			}
		}
	}
	
	private void translateRule4(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_CONDITION.equals(type)) {
				addCondition4();
				Div div = divCon4[indexCon4 - 1];
				// field:
				String field = obj.getString("field");
				Combobox fieldCom1 = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom1.getItemCount(); j++) {
					Comboitem item = fieldCom1.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom1.setSelectedItem(item);
				}
				// operator:
				String op = obj.getString("operator");
				Combobox opCom1 = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < opCom1.getItemCount(); j++) {
					Comboitem item = opCom1.getItemAtIndex(j);
					if (op.equals(item.getValue().toString()))
						opCom1.setSelectedItem(item);
				}
				// value:
				String value = obj.getString("value");
				Textbox tb = (Textbox) div.getChildren().get(3);
				tb.setValue(value);
			} else if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField4();
				String field = obj.getString("field");
				Div div = divField4[indexField4 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_SIMPLE_ADD.equals(type)) {
				addOperator4();
				//String operator = obj.getString("operator1");
				Div div = divOp4[indexOp4 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (Constants.V_ADD.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			}
		}
	}
	
	private void translateRule5(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField5();
				String field = obj.getString("field");
				Div div = divField5[indexField5 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_FIELD_SUM.equals(type)) {
				addFormFieldSum5();
			} else if (Constants.RULE_OPERATOR.equals(type)) {
				addOperator5();
				String operator = obj.getString("operator");
				Div div = divOp5[indexOp5 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (operator.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_RELATE_FORM.equals(type)) {
				addRelateForm5();
				String formName = obj.getString("relateForm");
				String fieldName = obj.getString("relateField");
				Div div = divRForm5[indexRForm5-1];
				Combobox formCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < formCom.getItemCount(); j++) {
					Comboitem item = formCom.getItemAtIndex(j);
					if (formName.equals(item.getValue().toString()))
						formCom.setSelectedItem(item);
				}
				loadFields5(indexRForm5-1);
				Combobox fieldCom = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (fieldName.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_FORM_SUM.equals(type)) {
				addForm5();
				String formName = obj.getString("form");
				Div div = divForm5[indexForm5-1];
				Combobox formCom = (Combobox) div.getChildren().get(1);
				for (int j=0; j< formCom.getItemCount(); j++) {
					Comboitem item = formCom.getItemAtIndex(j);
					if(formName.equals(item.getValue().toString()))formCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_FIELD_SUM.equals(type)) {
				addRelateCondition();
				String sumLine = obj.getString("sumLine");
				String sumTotal = obj.getString("sumTotal");
				Div div = divRForm56[indexRForm56 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (sumLine.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
				Combobox fieldCom2 = (Combobox) div.getChildren().get(3);
				for (int j=0; j<fieldCom2.getItemCount(); j++) {
					Comboitem item = fieldCom2.getItemAtIndex(j);
					if (sumTotal.equals(item.getValue().toString()))
						fieldCom2.setSelectedItem(item);
				}
			}
		}
	}
	
	private void translateRule6(String ruleContent) {
		JSONObject object = new JSONObject(ruleContent);
		JSONArray ruleArray = (JSONArray) object.get("rules");

		for (int i = 0; i < ruleArray.length(); i++) {
			JSONObject obj = ruleArray.getJSONObject(i);
			String type = obj.getString("type");

			if (Constants.RULE_CONDITION.equals(type)) {
				addCondition6();
				Div div = divCon6[indexCon6 - 1];
				// field:
				String field = obj.getString("field");
				Combobox fieldCom1 = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom1.getItemCount(); j++) {
					Comboitem item = fieldCom1.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom1.setSelectedItem(item);
				}
				// operator:
				String op = obj.getString("operator");
				Combobox opCom1 = (Combobox) div.getChildren().get(2);
				for (int j = 0; j < opCom1.getItemCount(); j++) {
					Comboitem item = opCom1.getItemAtIndex(j);
					if (op.equals(item.getValue().toString()))
						opCom1.setSelectedItem(item);
				}
				// value:
				String value = obj.getString("value");
				Textbox tb = (Textbox) div.getChildren().get(3);
				tb.setValue(value);
			} else if (Constants.RULE_FORMFIELD.equals(type)) {
				addFormField6();
				String field = obj.getString("field");
				Div div = divField6[indexField6 - 1];
				Combobox fieldCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < fieldCom.getItemCount(); j++) {
					Comboitem item = fieldCom.getItemAtIndex(j);
					if (field.equals(item.getValue().toString()))
						fieldCom.setSelectedItem(item);
				}
			}  else if (Constants.RULE_OPERATOR.equals(type)) {
				addOperator6();
				String operator = obj.getString("operator");
				Div div = divOp6[indexOp6 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (operator.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_OR.equals(type)) {
				addRelaOp6();
				Div div = divOpR6[indexOpR6 - 1];
				Combobox opCom = (Combobox) div.getChildren().get(1);
				for (int j = 0; j < opCom.getItemCount(); j++) {
					Comboitem item = opCom.getItemAtIndex(j);
					if (Constants.RULE_OR.equals(item.getValue().toString()))
						opCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_TEXTBOX.equals(type)) {
				addText6();
				String value = obj.getString("value");
				Div div = divText6[indexText6 - 1];
				Textbox text = (Textbox) div.getChildren().get(1);
				text.setValue(value);
			} else if (Constants.RULE_YEAR_ADD.equals(type)) {
				addYear6();
				String year = obj.getString("value");
				Div div = divYear6[indexYear6 - 1];
				Combobox yearCom = (Combobox) div.getChildren().get(2);
				for (int j = 0; j< yearCom.getItemCount(); j++) {
					Comboitem item = yearCom.getItemAtIndex(j);
					if (year.equals(item.getValue().toString()))
						yearCom.setSelectedItem(item);
				}
			} else if (Constants.RULE_MONTH.equals(type)) {
				addMonth6();
				String month = obj.getString("value");
				Div div = divMonth6[indexMonth6 - 1];
				Textbox text = (Textbox) div.getChildren().get(1);
				text.setValue(month);
			} else if (Constants.RULE_DAY.equals(type)) {
				addDay6();
				String day = obj.getString("value");
				Div div = divDay6[indexDay6 - 1];
				Textbox text = (Textbox) div.getChildren().get(1);
				text.setValue(day);
			} else if (Constants.RULE_DATE_FORMAT.equals(type)) {
				String format = obj.getString("format");
				Combobox dateF = (Combobox)window6.getChildren().get(0).getLastChild();
				for( int j=0; j<dateF.getItemCount(); j++){
					Comboitem item = dateF.getItemAtIndex(j);
					if(format.equals(item.getValue().toString()))
						dateF.setSelectedItem(item);
				}
			}
		}
	}

	// 1.1 Condition div:
	Div[] divCon1 = new Div[20];
	int indexCon1 = 0;
	int numCon1 = 0;

	@Listen("onClick = #addCon1")
	public void addCondition1() {
		logger.info("entering 1.");

		if (numCon1 >= 1) {
			Messagebox.show("前置条件最多只有一个！", "错误", Messagebox.OK, Messagebox.ERROR);
		} else {
			divCon1[indexCon1] = (Div) div.clone();
			// divCon1[indexCon1].setClass("mydiv");
			logger.info("entering 2");
			Input input = new Input();
			input.setType("hidden");
			input.setValue("a");
			divCon1[indexCon1].appendChild(input);
			divCon1[indexCon1].appendChild((Combobox) fieldCom.clone());
			divCon1[indexCon1].appendChild((Combobox) opCom.clone());
			divCon1[indexCon1].appendChild(new Textbox());
			divCon1[indexCon1].appendChild(new Label("时,有"));
			logger.info("entering 3");
			Image imageTemp = (Image) image.clone();
			// ZScript script = new
			// ZScript("java","onClick=delCondition1("+indexCon1+")");
			ZScript script = new ZScript("java", "delCondition1()");
			// String method = "window1.removeChild(divCon1["+ indexCon1
			// +"]);numCon1--;";
			// ZScript script = new ZScript("java", method);
			// EventHandler evthdl = new EventHandler(script);
			// imageTemp.addEventHandler("onClick", evthdl);
			imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
			imageTemp.setId("a" + getNum(indexCon1));
			divCon1[indexCon1].appendChild(imageTemp);
			window1.appendChild(divCon1[indexCon1]);

			indexCon1++;
			numCon1++;
		}
	}

	class myEventListener implements EventListener {
		public void onEvent(Event e) {
			Component c = e.getTarget();
			String name = c.getId();
			char type = name.charAt(0);
			String sNum = name.substring(1);
			int index = Integer.parseInt(sNum);

			switch (type) {
			case 'a':
				window1.removeChild(divCon1[index]);
				numCon1--;
				break;
			case 'b':
				window1.removeChild(divField1[index]);
				break;
			case 'c':
				window1.removeChild(divOp1[index]);
				break;
			case 'd':
				window1.removeChild(divText1[index]);
				break;
			case '1':
				window2.removeChild(divField2[index]);
				break;
			case 'e':
				window2.removeChild(divOp2[index]);
				break;
			case 'f':
				window2.removeChild(divRForm2[index]);
				break;
			case '2':
				window3.removeChild(divCon3[index]);
				break;
			case '3':
				window3.removeChild(divField3[index]);
				break;
			case 'k':
				window3.removeChild(divOp3[index]);
				break;
			case '4':
				window3.removeChild(divRForm3[index]);
				break;
			case 'g':
				window3.removeChild(divRForm32[index]);
				break;
			case 'o':
				window3.removeChild(divConR3[index]);
				break;
			case 'h':
				window3.removeChild(divConNo3[index]);
				break;
			case '5':
				window4.removeChild(divCon4[index]);
				numCon4--;
				break;
			case '6':
				window4.removeChild(divField4[index]);
				break;
			case 'q':
				window4.removeChild(divOp4[index]);
				break;
			case '7':
				window5.removeChild(divField5[index]);
				break;
			case 'i':
				window5.removeChild(divFieldSum5[index]);
				break;
			case '8':
				window5.removeChild(divOp5[index]);
				break;
			case 'y':
				window5.removeChild(divRForm5[index]);
				break;
			case 't':
				window5.removeChild(divForm5[index]);
				break;
			case 'x':
				window5.removeChild(divRForm56[index]);
				break;
			case '9':
				window6.removeChild(divCon6[index]);
				numCon6--;
				break;
			case 'Z':
				window6.removeChild(divField6[index]);
				break;
			case 'Y':
				window6.removeChild(divOp6[index]);
				break;
			case 'X':
				window6.removeChild(divOpR6[index]);
				break;
			case 's':
				window6.removeChild(divText6[index]);
				break;
			case 'l':
				window6.removeChild(divYear6[index]);
				break;
			case 'm':
				window6.removeChild(divMonth6[index]);
				break;
			case 'p':
				window6.removeChild(divDay6[index]);
				break;
			default:
				break;
			}
		}
	}

	private String getNum(int index) {
		if (index < 10) {
			return "0" + index;
		} else {
			return "" + index;
		}
	}

	// 1.2 Form Field:
	Div[] divField1 = new Div[20];
	int indexField1 = 0;

	@Listen("onClick = #addFormField1")
	public void addFormField1() {
		divField1[indexField1] = (Div) div.clone();
		// divField1[indexField1].setClass("mydiv");
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField1[indexField1].appendChild(input);
		divField1[indexField1].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField1("+indexField1+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("b" + getNum(indexField1));
		divField1[indexField1].appendChild(imageTemp);
		window1.appendChild(divField1[indexField1]);

		indexField1++;
	}

	public void delFormField1(int index) {
		window1.removeChild(divField1[index]);
	}

	// 1.3 Operator:
	Div[] divOp1 = new Div[20];
	int indexOp1 = 0;

	@Listen("onClick = #addOperator1")
	public void addOperator1() {
		divOp1[indexOp1] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("c");
		divOp1[indexOp1].appendChild(input);
		divOp1[indexOp1].appendChild((Combobox) opCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperator1("+indexOp1+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("c" + getNum(indexOp1));
		divOp1[indexOp1].appendChild(imageTemp);
		window1.appendChild(divOp1[indexOp1]);

		indexOp1++;
	}

	public void delOperator1(int index) {
		window1.removeChild(divOp1[index]);
	}

	// 1.4 textbox:
	Div[] divText1 = new Div[20];
	int indexText1 = 0;

	@Listen("onClick = #addText1")
	public void addText1() {
		divText1[indexText1] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("d");
		divText1[indexText1].appendChild(input);
		divText1[indexText1].appendChild(new Textbox());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delText1("+indexText1+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("d" + getNum(indexText1));
		divText1[indexText1].appendChild(imageTemp);
		window1.appendChild(divText1[indexText1]);

		indexText1++;
	}

	public void delText1(int index) {
		window1.removeChild(divText1[index]);
	}

	// 2.1 Form Field:
	Div[] divField2 = new Div[20];
	int indexField2 = 0;

	@Listen("onClick = #addFormField2")
	public void addFormField2() {
		divField2[indexField2] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField2[indexField2].appendChild(input);
		divField2[indexField2].appendChild((Combobox) fieldCom.clone());
		divField2[indexField2].appendChild(new Label("不重复"));

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField2("+indexField2+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("1" + getNum(indexField2));
		divField2[indexField2].appendChild(imageTemp);
		window2.appendChild(divField2[indexField2]);

		indexField2++;
	}

	public void delFormField2(int index) {
		window2.removeChild(divField2[index]);
	}

	// 2.2 And:
	Div[] divOp2 = new Div[20];
	int indexOp2 = 0;

	@Listen("onClick = #addOp2")
	public void addOp2() {
		divOp2[indexOp2] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("e");
		divOp2[indexOp2].appendChild(input);
		divOp2[indexOp2].appendChild((Combobox) opCom2.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOp2("+indexOp2+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("e" + getNum(indexOp2));
		divOp2[indexOp2].appendChild(imageTemp);
		window2.appendChild(divOp2[indexOp2]);

		indexOp2++;
	}

	public void delOp2(int index) {
		window2.removeChild(divOp2[index]);
	}

	// 2.3 related forms:
	Div[] divRForm2 = new Div[20];
	Combobox[] fieldsChangeCom = new Combobox[20];
	int indexRForm2 = 0;
	Combobox[] formCom2 = new Combobox[20];

	@Listen("onClick = #addRelateForm2")
	public void addRelateForm2() {
		divRForm2[indexRForm2] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("f");
		divRForm2[indexRForm2].appendChild(input);
		divRForm2[indexRForm2].appendChild(new Label("存在于"));
		formCom2[indexRForm2] = (Combobox) formCom.clone();
		// ZScript script = new ZScript("java","loadFields("+indexRForm2+")");
		// EventHandler evthdl1 = new EventHandler(script);
		formCom2[indexRForm2].addEventListener("onChange", (EventListener<? extends Event>) new loadEventListener());
		formCom2[indexRForm2].setId("A" + getNum(indexRForm2));
		divRForm2[indexRForm2].appendChild(formCom2[indexRForm2]);

		fieldsChangeCom[indexRForm2] = (Combobox) fieldCom.clone();
		divRForm2[indexRForm2].appendChild(fieldsChangeCom[indexRForm2]);

		Image imageTemp = (Image) image.clone();
		// ZScript script1 = new ZScript("java","delRForm2("+indexRForm2+")");
		// EventHandler evthdl = new EventHandler(script1);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("f" + getNum(indexRForm2));
		divRForm2[indexRForm2].appendChild(imageTemp);
		window2.appendChild(divRForm2[indexRForm2]);

		indexRForm2++;
	}

	class loadEventListener implements EventListener {
		public void onEvent(Event e) {
			Component c = e.getTarget();
			String name = c.getId();
			char type = name.charAt(0);
			String sNum = name.substring(1);
			int index = Integer.parseInt(sNum);

			switch (type) {
			case 'A':
				ArrayList fields2 = manager.getFormFieldsByFormName(Constants.USER_ID,
						formCom2[index].getSelectedItem().getValue());
				fieldsChangeCom[index].getItems().clear();

				for (int i = 0; i < fields2.size(); i++) {
					FormField field = (FormField) fields2.get(i);
					Comboitem item = new Comboitem(field.getBus_name());
					item.setValue(field.getPhysic_name());
					fieldsChangeCom[index].appendChild(item);
				}
				break;
			case 'B':
				ArrayList fields3 = manager.getFormFieldsByFormName(Constants.USER_ID,
						formCom3[index].getSelectedItem().getValue());
				fieldsChangeCom3[index].getItems().clear();
				fieldsChangeCom36[index].getItems().clear();

				for (int i = 0; i < fields3.size(); i++) {
					FormField field = (FormField) fields3.get(i);
					Comboitem item = new Comboitem(field.getBus_name());
					item.setValue(field.getPhysic_name());
					fieldsChangeCom3[index].appendChild(item);
					Comboitem item1 = new Comboitem(field.getBus_name());
					item1.setValue(field.getPhysic_name());
					fieldsChangeCom36[index].appendChild(item1);
				}
				break;
			case 'C':
				ArrayList fields32 = manager.getFormFieldsByFormName(Constants.USER_ID,
						formCom32[index].getSelectedItem().getValue());
				fieldsChangeCom32[index].getItems().clear();
				fieldsChangeCom33[index].getItems().clear();
				fieldsChangeCom36[index].getItems().clear();

				for (int i = 0; i < fields32.size(); i++) {
					FormField field = (FormField) fields32.get(i);
					Comboitem item1 = new Comboitem(field.getBus_name());
					item1.setValue(field.getPhysic_name());
					Comboitem item2 = new Comboitem(field.getBus_name());
					item2.setValue(field.getPhysic_name());
					Comboitem item3 = new Comboitem(field.getBus_name());
					item3.setValue(field.getPhysic_name());
					fieldsChangeCom32[index].appendChild(item1);
					fieldsChangeCom33[index].appendChild(item2);
					fieldsChangeCom36[index].appendChild(item3);
				}
				break;
			case 'D':
				ArrayList fields5 = manager.getFormFieldsByFormName(Constants.USER_ID,
						formCom5[index].getSelectedItem().getValue());
				fieldsChangeCom5[index].getItems().clear();

				for (int i = 0; i < fields5.size(); i++) {
					FormField field = (FormField) fields5.get(i);
					Comboitem item = new Comboitem(field.getBus_name());
					item.setValue(field.getPhysic_name());
					fieldsChangeCom5[index].appendChild(item);
				}
				break;
			case 'E':
				ArrayList fields56 = manager.getFormFieldsByFormName(Constants.USER_ID,
						formCom56[index].getSelectedItem().getValue());
				System.out.println("get here2");
				fieldsChangeCom56[index].getItems().clear();

				for (int i = 0; i < fields56.size(); i++) {
					FormField field = (FormField) fields56.get(i);
					Comboitem item = new Comboitem(field.getBus_name());
					item.setValue(field.getPhysic_name());
					fieldsChangeCom56[index].appendChild(item);
				}
				break;
			default:
				break;
			}
		}
	}

	public void loadFields(int index) {
		// Messagebox.show(formCom2.getSelectedItem().getValue());
		ArrayList fields2 = manager.getFormFieldsByFormName(Constants.USER_ID,
				formCom2[index].getSelectedItem().getValue());
		fieldsChangeCom[index].getItems().clear();

		for (int i = 0; i < fields2.size(); i++) {
			FormField field = (FormField) fields2.get(i);
			Comboitem item = new Comboitem(field.getBus_name());
			item.setValue(field.getPhysic_name());
			fieldsChangeCom[index].appendChild(item);
		}
	}

	public void delRForm2(int index) {
		window2.removeChild(divRForm2[index]);
	}

	// 3.1 Condition div:
	Div[] divCon3 = new Div[20];
	int indexCon3 = 0;
	int numCon3 = 0;

	@Listen("onClick = #addCondition3")
	public void addCondition3() {

		if (numCon3 >= 1) {
			Messagebox.show("前置条件最多只有一个！", "错误", Messagebox.OK, Messagebox.ERROR);
		} else {
			divCon3[indexCon3] = (Div) div.clone();
			Input input = new Input();
			input.setType("hidden");
			input.setValue("a");
			divCon3[indexCon3].appendChild(input);
			divCon3[indexCon3].appendChild((Combobox) fieldCom.clone());
			divCon3[indexCon3].appendChild((Combobox) opCom.clone());
			divCon3[indexCon3].appendChild(new Textbox());
			divCon3[indexCon3].appendChild(new Label("时,有"));
			Image imageTemp = (Image) image.clone();
			// ZScript script = new
			// ZScript("java","delCondition3("+indexCon3+")");
			// EventHandler evthdl = new EventHandler(script);
			imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
			imageTemp.setId("2" + getNum(indexCon3));
			divCon3[indexCon3].appendChild(imageTemp);
			window3.appendChild(divCon3[indexCon3]);

			indexCon3++;
			numCon3++;
		}
	}

	public void delCondition3(int index) {
		window3.removeChild(divCon3[index]);
		numCon3--;
	}

	// 3.2 Form Field:
	Div[] divField3 = new Div[20];
	int indexField3 = 0;

	@Listen("onClick = #addFormField3")
	public void addFormField3() {
		divField3[indexField3] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField3[indexField3].appendChild(input);
		divField3[indexField3].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField3("+indexField3+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("3" + getNum(indexField3));
		divField3[indexField3].appendChild(imageTemp);
		window3.appendChild(divField3[indexField3]);

		indexField3++;
	}

	public void delFormField3(int index) {
		window3.removeChild(divField3[index]);
	}

	// 3.3 Operator:
	Div[] divOp3 = new Div[20];
	int indexOp3 = 0;

	@Listen("onClick = #addOperator3")
	public void addOperator3() {
		divOp3[indexOp3] = (Div) div.clone();
		Combobox tempCom = new Combobox();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("k");
		divOp3[indexOp3].appendChild(input);
		Comboitem cItem = new Comboitem(Constants.OR);
		cItem.setValue(Constants.RULE_OR);
		tempCom.appendChild(cItem);
		tempCom.setSelectedItem(cItem);
		divOp3[indexOp3].appendChild(tempCom);

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperator3("+indexOp3+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("k" + getNum(indexOp3));
		divOp3[indexOp3].appendChild(imageTemp);
		window3.appendChild(divOp3[indexOp3]);

		indexOp3++;
	}

	public void delOperator3(int index) {
		window3.removeChild(divOp3[index]);
	}

	// 3.4 related forms one field:
	Div[] divRForm3 = new Div[20];
	Combobox[] fieldsChangeCom3 = new Combobox[20];
	int indexRForm3 = 0;
	Combobox[] formCom3 = new Combobox[20];
	int indexRForm36 = 0;
	Combobox[] fieldsChangeCom36 = new Combobox[20];

	@Listen("onClick = #addRelateForm3")
	public void addRelateForm3() {
		divRForm3[indexRForm3] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("f");
		divRForm3[indexRForm3].appendChild(input);
		divRForm3[indexRForm3].appendChild(new Label("存在于"));
		formCom3[indexRForm3] = (Combobox) formCom.clone();
		// ZScript script = new ZScript("java","loadFields3("+indexRForm3+")");
		// EventHandler evthdl1 = new EventHandler(script);
		formCom3[indexRForm3].addEventListener("onChange", (EventListener<? extends Event>) new loadEventListener());
		formCom3[indexRForm3].setId("B" + getNum(indexRForm3));
		divRForm3[indexRForm3].appendChild(formCom3[indexRForm3]);
		fieldsChangeCom36[indexRForm36] = (Combobox) fieldCom.clone();

		fieldsChangeCom3[indexRForm3] = (Combobox) fieldCom.clone();
		divRForm3[indexRForm3].appendChild(fieldsChangeCom3[indexRForm3]);

		Image imageTemp = (Image) image.clone();
		// ZScript script1 = new ZScript("java","delRForm3("+indexRForm3+")");
		// EventHandler evthdl = new EventHandler(script1);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("4" + indexRForm3);
		divRForm3[indexRForm3].appendChild(imageTemp);
		window3.appendChild(divRForm3[indexRForm3]);

		indexRForm3++;
		indexRForm36++;
	}

	// 3.5 related forms two field:
	Div[] divRForm32 = new Div[20];
	Combobox[] fieldsChangeCom32 = new Combobox[20];
	Combobox[] fieldsChangeCom33 = new Combobox[20];
	int indexRForm32 = 0;
	Combobox[] formCom32 = new Combobox[20];

	@Listen("onClick = #addRelateForm32")
	public void addRelateForm32() {
		divRForm32[indexRForm32] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("g");
		divRForm32[indexRForm32].appendChild(input);
		divRForm32[indexRForm32].appendChild(new Label("存在于"));
		formCom32[indexRForm32] = (Combobox) formCom.clone();
		// ZScript script = new
		// ZScript("java","loadFields32("+indexRForm32+")");
		// EventHandler evthdl1 = new EventHandler(script);
		formCom32[indexRForm32].addEventListener("onChange", (EventListener<? extends Event>) new loadEventListener());
		formCom32[indexRForm32].setId("C" + indexRForm32);
		divRForm32[indexRForm32].appendChild(formCom32[indexRForm32]);

		fieldsChangeCom32[indexRForm32] = (Combobox) fieldCom.clone();
		divRForm32[indexRForm32].appendChild(fieldsChangeCom32[indexRForm32]);

		fieldsChangeCom33[indexRForm32] = (Combobox) fieldCom.clone();
		divRForm32[indexRForm32].appendChild(fieldsChangeCom33[indexRForm32]);

		fieldsChangeCom36[indexRForm36] = (Combobox) fieldCom.clone();

		Image imageTemp = (Image) image.clone();
		// ZScript script1 = new ZScript("java","delRForm32("+indexRForm32+")");
		// EventHandler evthdl = new EventHandler(script1);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("g" + indexRForm32);
		divRForm32[indexRForm32].appendChild(imageTemp);
		window3.appendChild(divRForm32[indexRForm32]);

		indexRForm32++;
		indexRForm36++;
	}

	public void loadFields32(int index) {
		// Messagebox.show(formCom3.getSelectedItem().getValue());
		System.out.println("entering load fields");
		System.out.println("selected:" + formCom32[index].getSelectedItem().getValue());
		ArrayList fields32 = manager.getFormFieldsByFormName(Constants.USER_ID,
				formCom32[index].getSelectedItem().getValue());
		fieldsChangeCom32[index].getItems().clear();
		fieldsChangeCom33[index].getItems().clear();
		fieldsChangeCom36[index].getItems().clear();

		for (int i = 0; i < fields32.size(); i++) {
			FormField field = (FormField) fields32.get(i);
			Comboitem item1 = new Comboitem(field.getBus_name());
			item1.setValue(field.getPhysic_name());
			Comboitem item2 = new Comboitem(field.getBus_name());
			item2.setValue(field.getPhysic_name());
			Comboitem item3 = new Comboitem(field.getBus_name());
			item3.setValue(field.getPhysic_name());
			fieldsChangeCom32[index].appendChild(item1);
			fieldsChangeCom33[index].appendChild(item2);
			fieldsChangeCom36[index].appendChild(item3);
		}
	}

	void delRForm32(int index) {
		window3.removeChild(divRForm32[index]);
	}

	void loadFields3(int index) {
		// Messagebox.show(formCom3.getSelectedItem().getValue());
		// System.out.println("index:"+index);
		ArrayList fields3 = manager.getFormFieldsByFormName(Constants.USER_ID,
				formCom3[index].getSelectedItem().getValue());
		fieldsChangeCom3[index].getItems().clear();
		fieldsChangeCom36[index].getItems().clear();

		for (int i = 0; i < fields3.size(); i++) {
			FormField field = (FormField) fields3.get(i);
			Comboitem item = new Comboitem(field.getBus_name());
			item.setValue(field.getPhysic_name());
			fieldsChangeCom3[index].appendChild(item);
			Comboitem item1 = new Comboitem(field.getBus_name());
			item1.setValue(field.getPhysic_name());
			fieldsChangeCom36[index].appendChild(item1);
		}
	}

	public void delRForm3(int index) {
		window3.removeChild(divRForm3[index]);
	}

	// 3.6 relate form condition:
	Div[] divConR3 = new Div[20];
	int indexConR3 = 0;

	@Listen("onClick = #addConditionR3")
	public void addConditionR3() {
		divConR3[indexConR3] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("o");
		divConR3[indexConR3].appendChild(input);
		divConR3[indexConR3].appendChild(new Label("且当"));
		divConR3[indexConR3].appendChild(fieldsChangeCom36[--indexRForm36]);
		indexRForm36++;
		divConR3[indexConR3].appendChild(new Label("="));
		divConR3[indexConR3].appendChild(new Textbox());
		divConR3[indexConR3].appendChild(new Label("时"));

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delConditionR3("+indexConR3+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("o" + indexConR3);
		divConR3[indexConR3].appendChild(imageTemp);
		window3.appendChild(divConR3[indexConR3]);

		indexConR3++;
	}

	public void delConditionR3(int index) {
		window3.removeChild(divConR3[index]);
	}

	// 3.7 no:
	Div[] divConNo3 = new Div[20];
	int indexConNo3 = 0;

	@Listen("onClick = #addConditionNo3")
	public void addConditionNo3() {
		divConNo3[indexConNo3] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("h");
		divConNo3[indexConNo3].appendChild(input);
		divConNo3[indexConNo3].appendChild(new Label("不"));

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delConditionNo3("+indexConNo3+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("h" + indexConNo3);
		divConNo3[indexConNo3].appendChild(imageTemp);
		window3.appendChild(divConNo3[indexConNo3]);

		indexConNo3++;
	}

	void delConditionNo3(int index) {
		window3.removeChild(divConNo3[index]);
	}

	// 4.1 pre condition:
	Div[] divCon4 = new Div[20];
	int indexCon4 = 0;
	int numCon4 = 0;

	@Listen("onClick = #addCondition4")
	public void addCondition4() {

		if (numCon4 >= 1) {
			Messagebox.show("前置条件最多只有一个！", "错误", Messagebox.OK, Messagebox.ERROR);
		} else {
			divCon4[indexCon4] = (Div) div.clone();
			Input input = new Input();
			input.setType("hidden");
			input.setValue("a");
			divCon4[indexCon4].appendChild(input);
			divCon4[indexCon4].appendChild((Combobox) fieldCom.clone());
			divCon4[indexCon4].appendChild((Combobox) opCom.clone());
			divCon4[indexCon4].appendChild(new Textbox());
			divCon4[indexCon4].appendChild(new Label("时，有"));
			Image imageTemp = (Image) image.clone();
			// ZScript script = new
			// ZScript("java","delCondition4("+indexCon4+")");
			// EventHandler evthdl = new EventHandler(script);
			imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
			imageTemp.setId("5" + indexCon4);
			divCon4[indexCon4].appendChild(imageTemp);
			window4.appendChild(divCon4[indexCon4]);

			indexCon4++;
			numCon4++;
		}
	}

	public void delCondition4(int index) {
		window4.removeChild(divCon4[index]);
		numCon4--;
	}

	// 4.2 Form Field:
	Div[] divField4 = new Div[20];
	int indexField4 = 0;

	@Listen("onClick = #addFormField4")
	public void addFormField4() {
		divField4[indexField4] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField4[indexField4].appendChild(input);
		divField4[indexField4].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField4("+indexField4+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("6" + indexField4);
		divField4[indexField4].appendChild(imageTemp);
		window4.appendChild(divField4[indexField4]);

		indexField4++;
	}

	public void delFormField4(int index) {
		window4.removeChild(divField4[index]);
	}

	// 4.3 Operator:
	Div[] divOp4 = new Div[20];
	int indexOp4 = 0;

	@Listen("onClick = #addOperator4")
	public void addOperator4() {
		divOp4[indexOp4] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("q");
		divOp4[indexOp4].appendChild(input);
		Combobox op4Com = new Combobox();
		Comboitem op4Item = new Comboitem(Constants.ADD);
		op4Item.setValue(Constants.V_ADD);
		op4Com.appendChild(op4Item);
		op4Com.setSelectedItem(op4Item);
		divOp4[indexOp4].appendChild(op4Com);

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperator4("+indexOp4+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("q" + indexOp4);
		divOp4[indexOp4].appendChild(imageTemp);
		window4.appendChild(divOp4[indexOp4]);

		indexOp4++;
	}

	public void delOperator4(int index) {
		window4.removeChild(divOp4[index]);
	}

	// 5.1 form field:
	Div[] divField5 = new Div[20];
	int indexField5 = 0;

	@Listen("onClick = #addFormField5")
	public void addFormField5() {
		divField5[indexField5] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField5[indexField5].appendChild(input);
		divField5[indexField5].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField5("+indexField5+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("7" + indexField5);
		divField5[indexField5].appendChild(imageTemp);
		window5.appendChild(divField5[indexField5]);

		indexField5++;
	}

	public void delFormField5(int index) {
		window5.removeChild(divField5[index]);
	}

	// 5.2 form field sum:
	Div[] divFieldSum5 = new Div[20];
	int indexFieldSum5 = 0;

	@Listen("onClick = #addFormFieldSum5")
	public void addFormFieldSum5() {
		divFieldSum5[indexFieldSum5] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("i");
		divFieldSum5[indexFieldSum5].appendChild(input);
		divFieldSum5[indexFieldSum5].appendChild((Combobox) fieldCom.clone());
		divFieldSum5[indexFieldSum5].appendChild(new Label("之和"));

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormFieldSum5("+indexFieldSum5+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("i" + indexFieldSum5);
		divFieldSum5[indexFieldSum5].appendChild(imageTemp);
		window5.appendChild(divFieldSum5[indexFieldSum5]);

		indexFieldSum5++;
	}

	public void delFormFieldSum5(int index) {
		window5.removeChild(divFieldSum5[index]);
	}

	// 5.3 Operator:
	Div[] divOp5 = new Div[20];
	int indexOp5 = 0;

	@Listen("onClick = #addOperator5")
	public void addOperator5() {
		divOp5[indexOp5] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("c");
		divOp5[indexOp5].appendChild(input);
		divOp5[indexOp5].appendChild((Combobox) opCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperator5("+indexOp5+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("8" + indexOp5);
		divOp5[indexOp5].appendChild(imageTemp);
		window5.appendChild(divOp5[indexOp5]);

		indexOp5++;
	}

	public void delOperator5(int index) {
		window5.removeChild(divOp5[index]);
	}

	// 5.4 related forms:
	Div[] divRForm5 = new Div[20];
	Combobox[] fieldsChangeCom5 = new Combobox[20];
	int indexRForm5 = 0;
	Combobox[] formCom5 = new Combobox[20];

	@Listen("onClick = #addRelateForm5")
	public void addRelateForm5() {
		divRForm5[indexRForm5] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("y");
		divRForm5[indexRForm5].appendChild(input);
		// divRForm5[indexRForm5].appendChild(new Label("存在于"));
		formCom5[indexRForm5] = (Combobox) formCom.clone();
		// ZScript script = new ZScript("java","loadFields5("+indexRForm5+")");
		// EventHandler evthdl1 = new EventHandler(script);
		formCom5[indexRForm5].addEventListener("onChange", (EventListener<? extends Event>) new loadEventListener());
		formCom5[indexRForm5].setId("D" + indexRForm5);
		divRForm5[indexRForm5].appendChild(formCom5[indexRForm5]);

		fieldsChangeCom5[indexRForm5] = (Combobox) fieldCom.clone();
		divRForm5[indexRForm5].appendChild(fieldsChangeCom5[indexRForm5]);

		Image imageTemp = (Image) image.clone();
		// ZScript script1 = new ZScript("java","delRForm5("+indexRForm5+")");
		// EventHandler evthdl = new EventHandler(script1);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("y" + indexRForm5);
		divRForm5[indexRForm5].appendChild(imageTemp);
		window5.appendChild(divRForm5[indexRForm5]);

		indexRForm5++;
	}

	public void loadFields5(int index) {
		// Messagebox.show(formCom2.getSelectedItem().getValue());
		// System.out.println("get here");
		ArrayList fields5 = manager.getFormFieldsByFormName(Constants.USER_ID,
				formCom5[index].getSelectedItem().getValue());
		fieldsChangeCom5[index].getItems().clear();

		for (int i = 0; i < fields5.size(); i++) {
			FormField field = (FormField) fields5.get(i);
			Comboitem item = new Comboitem(field.getBus_name());
			item.setValue(field.getPhysic_name());
			fieldsChangeCom5[index].appendChild(item);
		}
	}

	public void delRForm5(int index) {
		window5.removeChild(divRForm5[index]);
	}

	// 5.5 add forms:
	Div[] divForm5 = new Div[20];
	int indexForm5 = 0;
	int indexRForm56 = 0;
	Combobox[] formCom56 = new Combobox[20];

	Combobox[] fieldsChangeCom56 = new Combobox[20];

	@Listen("onClick = #addForm5")
	public void addForm5() {
		divForm5[indexForm5] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("t");
		divForm5[indexForm5].appendChild(input);
		formCom56[indexRForm56] = (Combobox) formCom.clone();
		// ZScript script = new
		// ZScript("java","loadFields56("+indexRForm56+")");
		// EventHandler evthdl1 = new EventHandler(script);
		formCom56[indexRForm56].addEventListener("onChange", (EventListener<? extends Event>) new loadEventListener());
		formCom56[indexRForm56].setId("E" + indexRForm56);
		fieldsChangeCom56[indexRForm56] = (Combobox) fieldCom.clone();
		divForm5[indexForm5].appendChild(formCom56[indexRForm56]);

		Image imageTemp = (Image) image.clone();
		// ZScript script1 = new ZScript("java","delForm5("+indexForm5+")");
		// EventHandler evthdl = new EventHandler(script1);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("t" + indexForm5);
		divForm5[indexForm5].appendChild(imageTemp);
		window5.appendChild(divForm5[indexForm5]);

		indexForm5++;
	}

	public void delForm5(int index) {
		window5.removeChild(divForm5[index]);
	}

	// 5.6 relateForm conditions:
	Div[] divRForm56 = new Div[20];

	@Listen("onClick = #addRelateCondition")
	public void addRelateCondition() {
		divRForm56[indexRForm56] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("x");
		divRForm56[indexRForm56].appendChild(input);
		fieldsChangeCom56[indexRForm56] = (Combobox) fieldCom.clone();
		divRForm56[indexRForm56].appendChild(fieldsChangeCom56[indexRForm56]);
		divRForm56[indexRForm56].appendChild(new Label("="));

		divRForm56[indexRForm56].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delRForm56("+indexRForm56+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("x" + indexRForm56);
		divRForm56[indexRForm56].appendChild(imageTemp);
		window5.appendChild(divRForm56[indexRForm56]);

		indexRForm56++;
	}

	public void loadFields56(int index) {
		// Messagebox.show(formCom2.getSelectedItem().getValue());
		System.out.println("get here1");
		ArrayList fields56 = manager.getFormFieldsByFormName(Constants.USER_ID,
				formCom56[index].getSelectedItem().getValue());
		System.out.println("get here2");
		fieldsChangeCom56[index].getItems().clear();

		for (int i = 0; i < fields56.size(); i++) {
			FormField field = (FormField) fields56.get(i);
			Comboitem item = new Comboitem(field.getBus_name());
			item.setValue(field.getPhysic_name());
			fieldsChangeCom56[index].appendChild(item);
		}
	}

	public void delRForm56(int index) {
		window5.removeChild(divRForm56[index]);
	}

	// 6.1 pre condition:
	Div[] divCon6 = new Div[20];
	int indexCon6 = 0;
	int numCon6 = 0;

	@Listen("onClick = #addCondition6")
	public void addCondition6() {

		if (numCon6 >= 1) {
			Messagebox.show("前置条件最多只有一个！", "错误", Messagebox.OK, Messagebox.ERROR);
		} else {
			divCon6[indexCon6] = (Div) div.clone();
			Input input = new Input();
			input.setType("hidden");
			input.setValue("a");
			divCon6[indexCon6].appendChild(input);
			divCon6[indexCon6].appendChild((Combobox) fieldCom.clone());
			divCon6[indexCon6].appendChild((Combobox) opCom.clone());
			divCon6[indexCon6].appendChild(new Textbox());
			divCon6[indexCon6].appendChild(new Label("时，有"));
			Image imageTemp = (Image) image.clone();
			// ZScript script = new
			// ZScript("java","delCondition6("+indexCon6+")");
			// EventHandler evthdl = new EventHandler(script);
			imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
			imageTemp.setId("9" + indexCon6);
			divCon6[indexCon6].appendChild(imageTemp);
			window6.appendChild(divCon6[indexCon6]);

			indexCon6++;
			numCon6++;
		}
	}

	public void delCondition6(int index) {
		window6.removeChild(divCon6[index]);
		numCon6--;
	}

	// 6.2 Form Field:
	Div[] divField6 = new Div[20];
	int indexField6 = 0;

	@Listen("onClick = #addFormField6")
	public void addFormField6() {
		divField6[indexField6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("b");
		divField6[indexField6].appendChild(input);
		divField6[indexField6].appendChild((Combobox) fieldCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new
		// ZScript("java","delFormField6("+indexField6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("Z" + indexField6);
		divField6[indexField6].appendChild(imageTemp);
		window6.appendChild(divField6[indexField6]);

		indexField6++;
	}

	public void delFormField6(int index) {
		window6.removeChild(divField6[index]);
	}

	// 6.3 Operator:
	Div[] divOp6 = new Div[20];
	int indexOp6 = 0;

	@Listen("onClick = #addOperator6")
	public void addOperator6() {
		divOp6[indexOp6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("c");
		divOp6[indexOp6].appendChild(input);
		divOp6[indexOp6].appendChild((Combobox) opCom.clone());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperator6("+indexOp6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("Y" + indexOp6);
		divOp6[indexOp6].appendChild(imageTemp);
		window6.appendChild(divOp6[indexOp6]);

		indexOp6++;
	}

	public void delOperator6(int index) {
		window6.removeChild(divOp6[index]);
	}

	// 6.4 Relation Operator:
	Div[] divOpR6 = new Div[20];
	int indexOpR6 = 0;

	@Listen("onClick = #addRelaOp6")
	public void addRelaOp6() {
		divOpR6[indexOpR6] = (Div) div.clone();
		Combobox tempCom = new Combobox();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("k");
		divOpR6[indexOpR6].appendChild(input);
		Comboitem cItem = new Comboitem(Constants.OR);
		cItem.setValue(Constants.V_OR);
		tempCom.appendChild(cItem);
		tempCom.setSelectedItem(cItem);
		divOpR6[indexOpR6].appendChild(tempCom);

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delOperatorR6("+indexOpR6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("X" + indexOpR6);
		divOpR6[indexOpR6].appendChild(imageTemp);
		window6.appendChild(divOpR6[indexOpR6]);

		indexOpR6++;
	}

	public void delOperatorR6(int index) {
		window6.removeChild(divOpR6[index]);
	}

	// 6.5 textbox:
	Div[] divText6 = new Div[20];
	int indexText6 = 0;

	@Listen("onClick = #addText6")
	public void addText6() {
		divText6[indexText6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("s");
		divText6[indexText6].appendChild(input);
		divText6[indexText6].appendChild(new Textbox());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delText6("+indexText6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("s" + indexText6);
		divText6[indexText6].appendChild(imageTemp);
		window6.appendChild(divText6[indexText6]);

		indexText6++;
	}

	public void delText6(int index) {
		window6.removeChild(divText6[index]);
	}

	// 6.6 year:
	Div[] divYear6 = new Div[20];
	int indexYear6 = 0;

	@Listen("onClick = #addYear6")
	public void addYear6() {
		divYear6[indexYear6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("l");
		divYear6[indexYear6].appendChild(input);
		divYear6[indexYear6].appendChild(new Label("自然年"));

		Combobox yearCom = new Combobox();
		for (int i = 200; i > -201; i--) {
			Comboitem yearItem = new Comboitem("" + i);
			yearItem.setValue("" + i);
			yearCom.appendChild(yearItem);
			if (i == 0)
				yearCom.setSelectedItem(yearItem);
		}
		divYear6[indexYear6].appendChild(yearCom);

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delYear6("+indexYear6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("l" + indexYear6);
		divYear6[indexYear6].appendChild(imageTemp);
		window6.appendChild(divYear6[indexYear6]);

		indexYear6++;
	}

	public void delYear6(int index) {
		window6.removeChild(divYear6[index]);
	}

	// 6.7 month:
	Div[] divMonth6 = new Div[20];
	int indexMonth6 = 0;

	@Listen("onClick = #addMonth6")
	public void addMonth6() {
		divMonth6[indexMonth6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("m");
		divMonth6[indexMonth6].appendChild(input);
		divMonth6[indexMonth6].appendChild(new Textbox());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delMonth6("+indexMonth6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("m" + indexMonth6);
		divMonth6[indexMonth6].appendChild(imageTemp);
		window6.appendChild(divMonth6[indexMonth6]);

		indexMonth6++;
	}

	public void delMonth6(int index) {
		window6.removeChild(divMonth6[index]);
	}

	// 6.8 day:
	Div[] divDay6 = new Div[20];
	int indexDay6 = 0;

	@Listen("onClick = #addDay6")
	public void addDay6() {
		divDay6[indexDay6] = (Div) div.clone();
		Input input = new Input();
		input.setType("hidden");
		input.setValue("p");
		divDay6[indexDay6].appendChild(input);
		divDay6[indexDay6].appendChild(new Textbox());

		Image imageTemp = (Image) image.clone();
		// ZScript script = new ZScript("java","delDay6("+indexDay6+")");
		// EventHandler evthdl = new EventHandler(script);
		imageTemp.addEventListener("onClick", (EventListener<? extends Event>) new myEventListener());
		imageTemp.setId("p" + indexDay6);
		divDay6[indexDay6].appendChild(imageTemp);
		window6.appendChild(divDay6[indexDay6]);

		indexDay6++;
	}

	public void delDay6(int index) {
		window6.removeChild(divDay6[index]);
	}

	private JSONObject getWindowRules(Window window) {
		List rules = window.getChildren();
		JSONObject result = new JSONObject();
		JSONArray rulesArray = new JSONArray();
		JSONObject fieldObj = new JSONObject();
		Combobox fieldCom = new Combobox();
		String fieldValue = new String();
		Textbox textB = new Textbox();

		for (int i = 0; i < rules.size(); i++) {
			Div divRule = (Div) rules.get(i);
			List divChildren = divRule.getChildren();
			Input input = (Input) divChildren.get(0);
			char type = input.getValue().charAt(0);
			// System.out.println("type is " + type);
			switch (type) {
			case 'a':
				// Combobox,Combobox,Textbox.
				fieldObj = new JSONObject(true);
				fieldCom = (Combobox) divChildren.get(1);
				fieldValue = fieldCom.getSelectedItem().getValue();
				Combobox opCom = (Combobox) divChildren.get(2);
				String opValue = opCom.getSelectedItem().getValue();
				Textbox tbValue = (Textbox) divChildren.get(3);
				String textValue = tbValue.getValue();
				fieldObj.put("value", textValue).put("operator", opValue).put("field", fieldValue).put("type",
						Constants.RULE_CONDITION);
				rulesArray.put(fieldObj);
				break;
			case 'b':
				// Combobox:
				fieldObj = new JSONObject(true);
				fieldCom = (Combobox) divChildren.get(1);
				fieldValue = fieldCom.getSelectedItem().getValue();
				fieldObj.put("field", fieldValue).put("type", Constants.RULE_FORMFIELD);
				rulesArray.put(fieldObj);
				break;
			case 'c':
				// Combobox:
				fieldObj = new JSONObject(true);
				opCom = (Combobox) divChildren.get(1);
				opValue = opCom.getSelectedItem().getValue();
				fieldObj.put("operator", opValue).put("type", Constants.RULE_OPERATOR);
				rulesArray.put(fieldObj);
				break;
			case 'd':
				// Textbox:
				fieldObj = new JSONObject(true);
				textB = (Textbox) divChildren.get(1);
				textValue = textB.getValue();
				fieldObj.put("value", textValue).put("type", Constants.RULE_TEXTBOX);
				rulesArray.put(fieldObj);
				break;
			case 'e':
				// Combobox:
				fieldObj = new JSONObject(true);
				opCom = (Combobox) divChildren.get(1);
				opValue = opCom.getSelectedItem().getValue();
				fieldObj.put("operator1", opValue).put("type", Constants.RULE_OP_AND);
				rulesArray.put(fieldObj);
				break;
			case 'f':
				// Combobox,Combobox:
				fieldObj = new JSONObject(true);
				Combobox formCom = (Combobox) divChildren.get(2);
				String formValue = formCom.getSelectedItem().getValue();
				fieldCom = (Combobox) divChildren.get(3);
				fieldValue = fieldCom.getSelectedItem().getValue();
				fieldObj.put("relateField", fieldValue).put("relateForm", formValue).put("type",
						Constants.RULE_RELATE_FORM);
				rulesArray.put(fieldObj);
				break;
			case 'g':
				// Combobox,Combobox,Combobox:
				fieldObj = new JSONObject(true);
				formCom = (Combobox) divChildren.get(2);
				formValue = formCom.getSelectedItem().getValue();
				Combobox fieldCom1 = (Combobox) divChildren.get(3);
				String fieldValue1 = fieldCom1.getSelectedItem().getValue();
				Combobox fieldCom2 = (Combobox) divChildren.get(4);
				String fieldValue2 = fieldCom2.getSelectedItem().getValue();
				fieldObj.put("relateField2", fieldValue2).put("relateField1", fieldValue1).put("relateForm", formValue)
						.put("type", Constants.RULE_RELATE_FORM_TWO);
				rulesArray.put(fieldObj);
				break;
			case 'o':
				// Label,Combobox,Label,Textbox:
				fieldObj = new JSONObject(true);
				fieldCom = (Combobox) divChildren.get(2);
				fieldValue = fieldCom.getSelectedItem().getValue();
				Textbox fieldtx = (Textbox) divChildren.get(4);
				String txValue = fieldtx.getValue();
				fieldObj.put("value", fieldValue).put("field", txValue).put("type", Constants.RULE_FIELD_EQUAL);
				rulesArray.put(fieldObj);
				break;
			case 'h':
				// Label:
				fieldObj = new JSONObject(true);
				fieldObj.put("value", Constants.RULE_NO).put("type", Constants.RULE_NO);
				rulesArray.put(fieldObj);
				break;
			case 'i':
				break;
			case 'k':
				fieldObj = new JSONObject(true);
				fieldObj.put("value", Constants.RULE_OR).put("type", Constants.RULE_OR);
				rulesArray.put(fieldObj);
				break;
			case 'l':
				fieldObj = new JSONObject(true);
				Combobox yearCom = (Combobox) divChildren.get(2);
				String yearValue = yearCom.getSelectedItem().getValue();
				fieldObj.put("value", yearValue).put("type", Constants.RULE_YEAR_ADD);
				rulesArray.put(fieldObj);
				break;
			case 'm':
				// Textbox:
				fieldObj = new JSONObject(true);
				textB = (Textbox) divChildren.get(1);
				textValue = textB.getValue();
				fieldObj.put("value", textValue).put("type", Constants.RULE_MONTH);
				rulesArray.put(fieldObj);
				break;
			case 'p':
				// Textbox:
				fieldObj = new JSONObject(true);
				textB = (Textbox) divChildren.get(1);
				textValue = textB.getValue();
				fieldObj.put("value", textValue).put("type", Constants.RULE_DAY);
				rulesArray.put(fieldObj);
				break;
			case 'q':
				// Add:
				fieldObj = new JSONObject(true);
				fieldObj.put("value", Constants.V_ADD).put("type", Constants.RULE_SIMPLE_ADD);
				rulesArray.put(fieldObj);
				break;
			case 'r':
				// date format:
				Combobox format = (Combobox) divRule.getLastChild();
				break;
			case 's':
				// Textbox
				JSONObject textObj = new JSONObject(true);
				List al = window.getChildren();
				Div div = (Div) al.get(0);
				Combobox comDate = (Combobox) div.getLastChild();
				if (comDate.getSelectedItem() == null) {
					Messagebox.show("必须先选择日期格式!", "错误", Messagebox.OK, Messagebox.ERROR);
					break;
				}
				String format0 = comDate.getSelectedItem().getValue();
				Textbox textS = (Textbox) divChildren.get(1);
				if (format0.length() == textS.getValue().length()) {
					textObj = new JSONObject(true);
					textObj.put("format", format0).put("value", textS.getValue()).put("type",
							Constants.RULE_TEXTBOX_DATE);
					rulesArray.put(textObj);
				} else {
					Messagebox.show("文本框中输入的日期格式不正确！", "错误", Messagebox.OK, Messagebox.ERROR);
				}
				break;
			case 't':
				// 汇总表：
				fieldObj = new JSONObject(true);
				fieldCom = (Combobox) divChildren.get(1);
				fieldValue = fieldCom.getSelectedItem().getValue();
				fieldObj.put("form", fieldValue).put("type", Constants.RULE_FORM_SUM);
				rulesArray.put(fieldObj);
				break;
			case 'x':
				// 汇总条件:
				// Combobox,label,Combobox:
				JSONObject fieldSumObj = new JSONObject(true);
				Combobox fieldSumCom = (Combobox) divChildren.get(1);
				String fieldSumValue = fieldSumCom.getSelectedItem().getValue();
				fieldSumObj.put("sumLine", fieldSumValue).put("type", Constants.RULE_FIELD_SUM);
				Combobox fieldTotalCom = (Combobox) divChildren.get(3);
				String fieldTotalValue = fieldTotalCom.getSelectedItem().getValue();
				fieldSumObj.put("sumTotal", fieldTotalValue);
				rulesArray.put(fieldSumObj);
				break;
			case 'y':
				// Combobox,Combobox:
				fieldObj = new JSONObject(true);
				formCom = (Combobox) divChildren.get(1);
				formValue = formCom.getSelectedItem().getValue();
				fieldCom = (Combobox) divChildren.get(2);
				fieldValue = fieldCom.getSelectedItem().getValue();
				fieldObj.put("relateField", fieldValue).put("relateForm", formValue).put("type",
						Constants.RULE_RELATE_FORM);
				rulesArray.put(fieldObj);
				break;
			default:
				// do nothing.
			}
		}
		if (rulesArray.length() != 0)
			result.put("rules", rulesArray);
		else
			result = null;

		return result;
	}

	void saveRules() {
		// test:
		boolean is_new = false;
		int number = 0;
		// window1:
		// window's rules:
		// Messagebox.show("winodw1");

		// basic check:
		if (rule_pattern.getSelectedItem() == null) {
			Messagebox.show("必须选择校验规则模式!", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (rule_name == null || "".equals(rule_name.getValue())) {
			Messagebox.show("必须输入校验规则名称！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (rule_seq == null || "".equals(rule_seq.getValue().toString())) {
			Messagebox.show("校验规则顺序必须输入！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (fail_information == null || "".equals(fail_information.getValue())) {
			Messagebox.show("必须输入检验失败信息！", "错误", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// enter:
		Rule rule = new Rule();
		rule.setUser_id(Constants.USER_ID);
		Session session = Sessions.getCurrent();
		String sForm_id = (String) session.getAttribute("form_id");
		System.out.println("form_id:" + sForm_id);
		rule.setForm_id(Integer.parseInt(sForm_id));
		Integer sSeq = rule_seq.getValue();
		rule.setRule_seq(sSeq.intValue());
		rule.setFail_information(fail_information.getValue());
		rule.setRule_pattern(rule_pattern.getSelectedItem().getValue().toString().charAt(0));
		rule.setRule_name(rule_name.getValue());
		rule.setRule_active(Constants.RULE_ACTIVE);

		// window1:
		JSONObject rules = getWindowRules(window1);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule1(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(1);
			rule.setRule_content(rules.toString());

			/*
			 * System.out.println(rule.getUser_id());
			 * System.out.println(rule.getForm_id());
			 * System.out.println(rule.getRule_seq());
			 * System.out.println(rule.getFail_information());
			 * System.out.println(rule.getRule_pattern());
			 * System.out.println(rule.getRule_name());
			 * System.out.println(rule.getRule_active());
			 * System.out.println(rule.getRule_class());
			 * System.out.println(rule.getRule_content());
			 */

			manager.saveRule(rule);
			is_new = true;
		}
		// window2:
		// Messagebox.show("winodw2");
		rules = getWindowRules(window2);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule2(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(2);
			rule.setRule_content(rules.toString());

			manager.saveRule(rule);
			is_new = true;
		}
		// window3:
		// Messagebox.show("winodw3");
		rules = getWindowRules(window3);
		// Messagebox.show("checked:" + isCheck);
		boolean is_test = false;
		Checkbox ck = (Checkbox)window3.getChildren().get(0).getChildren().get(2);

		logger.info("rules:" + rules);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			} 
			boolean b = checkRule3(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			
			logger.info("is Checked:" + ck.isChecked());
			if (ck.isChecked()) {
				JSONObject mulObj = new JSONObject(true);
				mulObj.put("multiple", "yes").put("type", "multiple");
				JSONArray array = rules.getJSONArray("rules");
				array.put(mulObj);
				rules.put("rules", array);
			}

			number++;
			rule.setRule_class(3);
			rule.setRule_content(rules.toString());

			manager.saveRule(rule);
			is_new = true;
		}
		// window4:
		// Messagebox.show("winodw4");
		rules = getWindowRules(window4);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule4(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(4);
			rule.setRule_content(rules.toString());

			manager.saveRule(rule);
			is_new = true;
		}
		// window5:
		// Messagebox.show("winodw5");
		rules = getWindowRules(window5);
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule5(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}

			number++;
			rule.setRule_class(5);
			rule.setRule_content(rules.toString());

			manager.saveRule(rule);
			is_new = true;
		}
		// window6:
		// Messagebox.show("winodw6");
		rules = getWindowRules(window6);
		System.out.println("get rules");
		if (rules != null) {
			if (number > 0) {
				Messagebox.show("不允许同时建多个规则！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			boolean b = checkRule6(rules);
			if (!b) {
				Messagebox.show("您输入的规则不正确，请校验后再输入！", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			number++;
			// System.out.println("rule checked.");
			// add format:
			List al = window6.getChildren();
			Div div = (Div) al.get(0);
			Combobox comDate = (Combobox) div.getLastChild();
			if (comDate.getSelectedItem() == null) {
				Messagebox.show("必须选择一种日期格式!", "错误", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			String format = comDate.getSelectedItem().getValue();
			JSONArray array = rules.getJSONArray("rules");
			JSONObject formatObj = new JSONObject();
			formatObj.put("format", format).put("type", Constants.RULE_DATE_FORMAT);
			array.put(formatObj);
			JSONObject newRule = new JSONObject();
			newRule.put("rules", array);

			//
			rule.setRule_class(6);
			rule.setRule_content(newRule.toString());

			manager.saveRule(rule);
			is_new = true;
		}

		if (is_new) {
			Messagebox.show("规则成功保存！", "信息", Messagebox.OK, Messagebox.INFORMATION);
			Executions.getCurrent().sendRedirect("");
		} else
			Messagebox.show("没有规则被保存!", "错误", Messagebox.OK, Messagebox.ERROR);
	}

	boolean checkRule1(JSONObject rules) {
		boolean right = true;
		String typeLeft = "";
		String typeRight = "";

		JSONArray ruleList = (JSONArray) rules.get("rules");
		for (int i = 0; i < ruleList.length(); i++) {
			JSONObject obj = (JSONObject) ruleList.get(i);
			String type = obj.getString("type");
			if (type.equals(Constants.RULE_CONDITION) && i != 0)
				return false;
		}

		JSONObject obj1 = (JSONObject) ruleList.get(0);
		typeLeft = obj1.getString("type");
		for (int j = 1; j < ruleList.length(); j++) {
			JSONObject obj2 = (JSONObject) ruleList.get(j);
			typeRight = obj2.getString("type");
			if (typeLeft.equals(typeRight))
				return false;
			typeLeft = typeRight;
		}

		return true;
	}

	boolean checkRule2(JSONObject rules) {
		JSONArray ruleList = (JSONArray) rules.get("rules");
		System.out.println("length:" + ruleList.length());
		int index = 0;
		while (index < ruleList.length()) {
			JSONObject obj1 = ruleList.getJSONObject(index);
			if (obj1 == null)
				return false;
			String type = obj1.getString("type");
			if (!Constants.RULE_FORMFIELD.equals(type))
				return false;
			index++;
			if (index >= ruleList.length())
				return false;

			JSONObject obj2 = ruleList.getJSONObject(index);
			if (obj2 == null)
				return false;
			type = obj2.getString("type");
			if (!Constants.RULE_RELATE_FORM.equals(type))
				return false;
			index++;
			if (index >= ruleList.length())
				return true;

			JSONObject obj3 = ruleList.getJSONObject(index);
			if (obj3 == null)
				return true;
			type = obj3.getString("type");
			if (!Constants.RULE_OP_AND.equals(type))
				return false;
			index++;
			if (index >= ruleList.length())
				return false;
		}

		return true;
	}

	boolean checkRule3(JSONObject rules) {
		boolean right = true;
		boolean isCondition = false;
		int start = 0;
		int index = 0;
		int fieldNum = 0;
		boolean complete = false;

		JSONArray ruleList = (JSONArray) rules.get("rules");
		for (int i = 0; i < ruleList.length(); i++) {
			JSONObject obj = (JSONObject) ruleList.get(i);
			String type = obj.getString("type");
			if (type.equals(Constants.RULE_CONDITION)) {
				isCondition = true;
				if (i != 0)
					return false;
			}
		}

		if (isCondition)
			start = 1;
		else
			start = 0;
		index = start;

		if (index < ruleList.length()) {
			JSONObject obj = ruleList.getJSONObject(index);
			String type = obj.getString("type");
			if (Constants.RULE_FORMFIELD.equals(type)) {
				fieldNum++;
				index++;
			} else {
				return false;
			}
		} else if (complete) {
			return true;
		} else
			return false;

		if (index < ruleList.length()) {
			JSONObject obj = ruleList.getJSONObject(index);
			String type = obj.getString("type");
			if (Constants.RULE_FORMFIELD.equals(type)) {
				fieldNum++;
				index++;
			} else if (Constants.RULE_RELATE_FORM.equals("type")) {
				complete = true;
				index++;
			}
		} else {
			if (complete)
				return true;
			else
				return false;
		}

		if (index < ruleList.length()) {
			if (fieldNum == 1) {
				if (complete) {
					for (int j = index; j < ruleList.length(); j++) {
						JSONObject o = ruleList.getJSONObject(j);
						String t = o.getString("type");
						if (!(Constants.RULE_OR.equals(t) || Constants.RULE_RELATE_FORM.equals(t)
								|| Constants.RULE_FIELD_EQUAL.equals(t))) {
							return false;
						}
					}
				} else {
					// return false;
				}
			} else if (fieldNum == 2) {
				JSONObject obj = ruleList.getJSONObject(index);
				String type = obj.getString("type");
				if (Constants.RULE_RELATE_FORM_TWO.equals(type)) {
					index++;
					complete = true;
				} else {
					return false;
				}

				if (complete) {
					for (int j = index; j < ruleList.length(); j++) {
						JSONObject o = ruleList.getJSONObject(j);
						String t = o.getString("type");
						if (!(Constants.RULE_OR.equals(t) || Constants.RULE_RELATE_FORM_TWO.equals(t)
								|| Constants.RULE_FIELD_EQUAL.equals(t))) {
							return false;
						}
					}
				} else {
					// return false;
				}

			}
		} else {
			if (complete)
				return true;
			else
				return false;
		}

		return true;
	}

	boolean checkRule4(JSONObject rules) {
		String typeLeft = "";
		String typeRight = "";
		boolean isCondition = false;
		int start = 0;
		JSONArray ruleList = (JSONArray) rules.get("rules");
		for (int i = 0; i < ruleList.length(); i++) {
			JSONObject obj = (JSONObject) ruleList.get(i);
			String type = obj.getString("type");
			if (type.equals(Constants.RULE_CONDITION)) {
				isCondition = true;
				if (i != 0)
					return false;
			}
		}

		if (isCondition)
			start = 1;
		else
			start = 0;

		JSONObject obj1 = (JSONObject) ruleList.get(start);
		typeLeft = obj1.getString("type");
		if (Constants.RULE_SIMPLE_ADD.equals(typeLeft))
			return false;
		for (int j = start + 1; j < ruleList.length(); j++) {
			JSONObject obj2 = (JSONObject) ruleList.get(j);
			typeRight = obj2.getString("type");
			if (typeLeft.equals(typeRight))
				return false;
			typeLeft = typeRight;
		}

		if (Constants.RULE_SIMPLE_ADD.equals(typeLeft))
			return false;

		return true;
	}

	boolean checkRule5(JSONObject rules) {
		int compareNum = 0;
		int index = 0;
		String typeLeft = "";
		String typeRight = "";
		JSONArray ruleList = (JSONArray) rules.get("rules");
		for (int i = 0; i < ruleList.length(); i++) {
			JSONObject obj = ruleList.getJSONObject(i);
			String type = obj.getString("type");
			if (Constants.RULE_OPERATOR.equals(type)) {
				String op = obj.getString("operator");
				if (isCompareOperator(op)) {
					compareNum++;
					if (compareNum == 1)
						index = i;
				}
			}
		}
		if (compareNum != 1)
			return false;

		// left:
		JSONObject ob1 = ruleList.getJSONObject(0);
		typeLeft = ob1.getString("type");
		if (!Constants.RULE_FORMFIELD.equals(typeLeft))
			return false;
		int j = 1;
		for (j = 1; j < index; j++) {
			JSONObject ob2 = ruleList.getJSONObject(j);
			typeRight = ob2.getString("type");
			if (typeLeft.equals(typeRight))
				return false;
			typeLeft = typeRight;
		}
		// right:
		j++;
		if (j < ruleList.length()) {
			JSONObject obj = ruleList.getJSONObject(j);
			String type = obj.getString("type");
			if (Constants.RULE_RELATE_FORM.equals(type)) {
				j++;
				if (j < ruleList.length())
					return false;
			} else if (Constants.RULE_FORM_SUM.equals(type)) {
				j++;
				if (j < ruleList.length()) {
					JSONObject objT = ruleList.getJSONObject(j);
					String typeTemp = objT.getString("type");
					if (Constants.RULE_FIELD_SUM.equals(typeTemp)) {
						j++;
						if (j < ruleList.length())
							return false;
					} else
						return false;
				} else
					return false;
			} else
				return false;
		} else
			return false;

		return true;
	}

	boolean checkRule6(JSONObject rules) {
		boolean isCondition = false;
		int start = 0;
		int index = 0;
		JSONArray ruleList = (JSONArray) rules.get("rules");
		for (int i = 0; i < ruleList.length(); i++) {
			JSONObject obj = (JSONObject) ruleList.get(i);
			String type = obj.getString("type");
			if (Constants.RULE_CONDITION.equals(type)) {
				isCondition = true;
				if (i != 0)
					return false;
			}
		}

		if (isCondition)
			start = 1;
		else
			start = 0;
		index = start;
		String typeLeft = "";
		String typeRight = "";

		if (index < ruleList.length()) {
			JSONObject ob1 = (JSONObject) ruleList.get(index);
			String type1 = ob1.getString("type");
			if (!Constants.RULE_FORMFIELD.equals(type1))
				return false;
		} else
			return false;

		typeLeft = Constants.RULE_FORMFIELD;
		String opValue = "";
		for (int j = index + 1; j < ruleList.length(); j++) {
			JSONObject obj2 = (JSONObject) ruleList.get(j);
			typeRight = obj2.getString("type");

			if (typeLeft.equals(typeRight))
				return false;

			if (Constants.RULE_OPERATOR.equals(typeRight)) {
				opValue = obj2.getString("operator");
				if (isCompareOperator(opValue))
					break;
			}

			typeLeft = typeRight;
			index++;
		}

		if (!isCompareOperator(opValue))
			return false;

		index++;
		index++;

		if (index < ruleList.length()) {
			JSONObject obj3 = (JSONObject) ruleList.get(index);
			String type3 = obj3.getString("type");
			if (Constants.RULE_TEXTBOX_DATE.equals(type3)) {
				index++;
				if (index < ruleList.length()) {
					JSONObject obj6 = (JSONObject) ruleList.get(index);
					String type6 = obj6.getString("type");
					if (!Constants.RULE_OR.equals(type6))
						return false;
				} else
					return true;
			} else if (Constants.RULE_YEAR_ADD.equals(type3)) {
				index++;
				if (index < ruleList.length()) {
					System.out.println("entering here 1");
					JSONObject obj4 = (JSONObject) ruleList.get(index);
					String type4 = obj4.getString("type");
					if (Constants.RULE_MONTH.equals(type4)) {
						index++;
						if (index < ruleList.length()) {
							JSONObject obj5 = (JSONObject) ruleList.get(index);
							String type5 = obj5.getString("type");
							if (!(Constants.RULE_DAY.equals(type5) || Constants.RULE_OR.equals(type5)))
								return false;
							else
								return true;
						} else
							return true;
					} else if (Constants.RULE_OR.equals(type4)) {
						return true;
					}
				} else
					return true;
			}
			if (Constants.RULE_FORMFIELD.equals(type3)) {
				index++;
				if (index < ruleList.length()) {
					System.out.println("entering here 2");
					JSONObject ob6 = (JSONObject) ruleList.get(index);
					String type6 = ob6.getString("type");
					if (Constants.RULE_OR.equals(type6))
						return true;
					else
						return false;
				} else
					return true;
			} else
				return false;
		}

		return true;
	}

	boolean isCompareOperator(String operator) {
		boolean result = false;

		if (Constants.V_GREATT.equals(operator) || Constants.V_GREATTE.equals(operator)
				|| Constants.V_LESST.equals(operator) || Constants.V_LESSTE.equals(operator)
				|| Constants.V_EQUAL.equals(operator)) {
			result = true;
		}
		return result;
	}

	@Listen("onClick = #cancelBtn")
	public void cancelDo() {
		List listTemp = window1.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window1.removeChild(c);
		}

		listTemp = window2.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window2.removeChild(c);
		}

		listTemp = window3.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window3.removeChild(c);
		}

		listTemp = window4.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window4.removeChild(c);
		}

		listTemp = window5.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window5.removeChild(c);
		}

		listTemp = window6.getChildren();
		while (listTemp.size() > 1) {
			Component c = (Component) listTemp.get(1);
			window6.removeChild(c);
		}

		numCon1 = 0;
		numCon3 = 0;
		numCon4 = 0;
		numCon6 = 0;
	}
}
