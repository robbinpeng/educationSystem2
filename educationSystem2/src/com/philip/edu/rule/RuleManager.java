package com.philip.edu.rule;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Rule;

public class RuleManager {
	private Logger logger = Logger.getLogger(RuleManager.class);
	
	private static RuleDAO dao = new RuleDAO(); 
	private static ExcelHelper excelHelper = new ExcelHelper();
	
	public boolean formatCheck(int form_id, String[][] data){
		// check format is right:
		return excelHelper.is_format_right(data,form_id); 
	}
	
	public boolean isColumnsRight(int form_id, String[][] data){
		return excelHelper.is_columns_right(data, form_id);
	}
	
	public MessageInfo isLengthRight(int form_id, String[][] data){
		FieldLengthCheck checker = new FieldLengthCheck();
		return checker.lengthCheck(data, form_id);
	}
	
	public MessageInfo textFormatCheck(int form_id, String[][] data){
		FieldFormatCheck checker = new FieldFormatCheck();
		return checker.formatCheck(data, form_id);
	}
	
	public MessageInfo textFormatSingleCheck(int form_id, ArrayList record){
		FieldFormatCheck checker = new FieldFormatCheck();
		return checker.formatCheckSingleLine(record, form_id);
	}
	
	public MessageInfo DictionCheck(int form_id, String[][] data){
		DictionCheck checker = new DictionCheck();
		return checker.dictionCheck(data, form_id);
	}
	
	public MessageInfo DictionCheckSingleLine(int form_id, ArrayList record){
		DictionCheck checker = new DictionCheck();
		return checker.dictionCheckSingleLine(record, form_id);
	}
	
	public boolean changeActive(int rule_id){
		return dao.changeActive(rule_id);
	}
	
	public ArrayList rulesCheck(int form_id, String[][] data, int task_id){
		MessageInfo message = null;
		ArrayList returnMessage = new ArrayList();
		
		ArrayList rules = getActiveRules(form_id);
		
		for(int i=0; i<rules.size(); i++){
			Rule rule = (Rule)rules.get(i);
			int type = rule.getRule_class();
			String rule_content = rule.getRule_content();
			JSONObject object = new JSONObject(rule_content);
			
			switch(type){
				case 1:
					//
					logger.info("处理第一类规则");
					Rule1ConstraintCheck engine1 = new Rule1ConstraintCheck();
					message = engine1.getMessage(data, object, form_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 2:
					//Exclusive Check:
					logger.info("处理第二类规则");
					Rule2ExclusiveCheck engine2 = new Rule2ExclusiveCheck();
					message = engine2.getMessage(data, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 3:
					//
					logger.info("处理第三类规则");
					//logger.info(rule.getRule_name());
					Rule3ExistsCheck engine3 = new Rule3ExistsCheck();
					message = engine3.getMessage(data, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 4:
					//Only Check:
					logger.info("处理第四类规则");
					Rule4NoRepeatCheck engine4 = new Rule4NoRepeatCheck();
					message = engine4.getMessage(data, object, form_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 5:
					//
					logger.info("处理第五类规则");
					Rule5OutsideConstraintCheck engine5 = new Rule5OutsideConstraintCheck();
					message = engine5.getMessage(data, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 6:
					//
					logger.info("处理第六类规则");
					Rule6TimeCheck engine6 = new Rule6TimeCheck();
					message = engine6.getMessage(data, object, form_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				default:
					break;
			}
		}
		
		return returnMessage;
	}
	
	public ArrayList rulesCheckSingleLine(int form_id, ArrayList record, int task_id){
		MessageInfo message = null;
		ArrayList returnMessage = new ArrayList();
		
		ArrayList rules = getActiveRules(form_id);
		
		for(int i=0; i<rules.size(); i++){
			Rule rule = (Rule)rules.get(i);
			int type = rule.getRule_class();
			String rule_content = rule.getRule_content();
			JSONObject object = new JSONObject(rule_content);
			
			switch(type){
				case 1:
					//
					logger.info("处理第一类规则");
					Rule1ConstraintCheck engine1 = new Rule1ConstraintCheck();
					message = engine1.getMessageSingleLine(record, object, form_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 2:
					//Exclusive Check:
					logger.info("处理第二类规则");
					Rule2ExclusiveCheck engine2 = new Rule2ExclusiveCheck();
					message = engine2.getMessageSingleLine(record, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 3:
					//
					logger.info("处理第三类规则");
					Rule3ExistsCheck engine3 = new Rule3ExistsCheck();
					message = engine3.getMessageSingleLine(record, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 4:
					//Only Check:
					logger.info("处理第四类规则");
					Rule4NoRepeatCheck engine4 = new Rule4NoRepeatCheck();
					message = engine4.getMessageSingleLine(record, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 5:
					//
					logger.info("处理第五类规则");
					Rule5OutsideConstraintCheck engine5 = new Rule5OutsideConstraintCheck();
					message = engine5.getMessageSingleLine(record, object, form_id, task_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				case 6:
					//
					logger.info("处理第六类规则");
					Rule6TimeCheck engine6 = new Rule6TimeCheck();
					message = engine6.getMessageSingleLine(record, object, form_id);
					message.setFail_information(rule.getFail_information());
					returnMessage.add(message);
					break;
				default:
					break;
			}
		}
		
		return returnMessage;
	}
	
	public ArrayList getRules(int form_id){
		return dao.getRules(form_id);
	}
	
	public ArrayList getActiveRules(int form_id){
		ArrayList al = new ArrayList();
		ArrayList allRules = this.getRules(form_id);
		
		for(int i=0; i<allRules.size(); i++){
			Rule rule = (Rule)allRules.get(i);
			if(rule.getRule_active()=='Y')al.add(rule);
		}
		
		return al;
	}
	
	public boolean deleteRule(int rule_id){
		return dao.deleteRule(rule_id);
	}
	
}
