package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Rule4NoRepeatCheck {

	private Logger logger = Logger.getLogger(Rule4NoRepeatCheck.class);
	private static ExcelHelper helper = new ExcelHelper();
	private static FormManager manager = new FormManager();

	public MessageInfo getMessage(Workbook wb, JSONObject object, int form_id){
		int[] columns = new int[10];
		logger.info("rule 4: begin to check");
		int bit = 0;
		boolean isCondition = false;
		int conditionColumn = 0;
		String conOperator = "";
		String conValue = "";
		
		MessageInfo message = new MessageInfo();
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		ArrayList messageList = new ArrayList();
		
		int excelColumns = helper.getExcelColumns(wb);
		int lines = helper.getExcelLines(wb);
		
		//Precondition:
		JSONArray preArray = (JSONArray) object.get("rules");
		JSONObject preObj = (JSONObject) preArray.get(0);
		String type = preObj.getString("type");
		if(Constants.RULE_CONDITION.equals(type)){
			isCondition = true;
			String physic_name = preObj.getString("field");
			FormField field = manager.getFieldByPhysicName(form_id, physic_name);
			conditionColumn = helper.getColumn2Check(wb, field.getBus_name(), excelColumns);
			conOperator = preObj.getString("operator");
			conValue = preObj.getString("value");
		}
		
		String keyInfo = "(";
		
		ArrayList al = translateRules(object, form_id);
		bit = al.size();
		for(int i=0; i<al.size(); i++){
			FormField field = (FormField)al.get(i);
			keyInfo += field.getBus_name() + ",";
			int index = helper.getColumn2Check(wb, field.getBus_name(), excelColumns);
			columns[i] = index;
		}
		keyInfo += ")";
		
		Sheet sheet = wb.getSheetAt(0);
		for(int j=1; j<lines; j++){
			
			Row row = sheet.getRow(j);
			//1. check the precondition:
			if(isCondition){
				Cell conCell = row.getCell(conditionColumn);
				String testValue="";
				if(conCell==null)continue;
				Object value = helper.getCellValue(conCell);
				testValue = value.toString();
				
				//operator:
				if(Constants.V_EQUAL.equals(conOperator)){
					// = 
					if(!conValue.equals(testValue))continue;
				} else if(Constants.V_NOEQUAL.equals(conOperator)) {
					if (conValue.equals(testValue))
						continue;
				} else {
					int left = 0;
					int right = 0;
					try{
						left = Integer.parseInt(testValue);	
						right = Integer.parseInt(conValue);
						if(Constants.V_GREATT.equals(conOperator)){
							if(!(left>right))continue;
						} else if(Constants.V_GREATTE.equals(conOperator)){
							if(!(left>=right))continue;
						} else if(Constants.V_LESST.equals(conOperator)){
							if(!(left<right))continue;
						} else if(Constants.V_LESSTE.equals(conOperator)){
							if(!(left<=right))continue;
						}
					} catch (NumberFormatException e) {
						message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
						messageList.add("第" + (j+1) + "行的条件不是数字！");
					}
					
				}
			}
			
			//2. check the rule:
			String key = null;

			StringBuffer result = new StringBuffer("");
			
			for(int k=0; k<bit; k++){
				Cell cell = row.getCell(columns[k]);
				String testValue="";
				if(cell != null){
					Object value = helper.getCellValue(cell);
					
					testValue = value.toString();
				    result.append(testValue);
				}			
			}
			
			for(int l=0; l<j; l++){
				Row row1 = sheet.getRow(l);
				StringBuffer compare = new StringBuffer("");
				
				for(int m=0; m<bit; m++){
					Cell cell1 = row1.getCell(columns[m]);
					String testValue="";
					if(cell1 != null){
						Object value = helper.getCellValue(cell1);
						testValue = value.toString();
					    compare.append(testValue);
					}
				}
				
				logger.info("result:"+result + ", compare:" + compare);
				if(result.toString().equals(compare.toString())){
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (j+1) + "行的主键" + keyInfo + "与第" + (l+1) +"行记录重复！");
					break;
				}
			}
		}
		
		message.setMessage_info(messageList);;
		
		return message;
	}
	
	private ArrayList translateRules(JSONObject object, int form_id){
		logger.info("begin to translate the rule");
		ArrayList al = new ArrayList();
		JSONArray array = (JSONArray) object.get("rules");
		
		for(int i=0; i<array.length(); i++){
			JSONObject ob = array.getJSONObject(i);
			if("field".equals(ob.get("type").toString())){
				//al.add(ob.get("field").toString());
				String field_name = ob.get("field").toString();
				FormField field = manager.getFieldByPhysicName(form_id, field_name);
				al.add(field);
			}
		}
		
		return al;
	}
}
