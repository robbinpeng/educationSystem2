package com.philip.edu.rule;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DictItem;
import com.philip.edu.basic.DictManager;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class DictionCheck {
	private static final Logger logger = Logger.getLogger(DictionCheck.class);
	
	private static FormManager formManager = new FormManager();
	private static ExcelHelper excelHelper = new ExcelHelper();
	private static DictManager dictManager = new DictManager();
	
	public MessageInfo dictionCheck(Workbook wb, int form_id){
		MessageInfo message = new MessageInfo();
		ArrayList messageList = new ArrayList();
		
		//Caption:
		ArrayList fields = formManager.getFormFields(form_id);
		ArrayList checkFields = new ArrayList();
		int columns = excelHelper.getExcelColumns(wb);
		int lines = excelHelper.getExcelLines(wb, form_id, columns);
		
		for(int i=0; i<fields.size(); i++){
			FormField caption = (FormField) fields.get(i);
			
			if("TJSJ".equals(caption.getPhysic_name()))continue;
			
			if(caption.getDictid()!=0){
				LineInfo info = new LineInfo();
				
				int column = excelHelper.getColumn2Check(wb, caption.getBus_name(), columns);
				if(column!=0)info.setColumn(column);
				else continue;
				
				info.setDis_method(caption.getDis_method());
				
				info.setDict_id(caption.getDictid());
				
				ArrayList dict_items = dictManager.getDictItemByDict(caption.getDictid());
				info.setDict_items(dict_items);
				
				checkFields.add(info);
			}
		}
		
		Sheet sheet = wb.getSheetAt(0);
		
		message.setMessage_type(Constants.RULECHECK_MESSAGE_SUCCESS);
		
		for(int j=1; j<lines; j++){
			Row row = sheet.getRow(j);
			
			for(int k=0; k<checkFields.size(); k++){
				LineInfo line = (LineInfo)checkFields.get(k);
				boolean isFound = false;
				boolean allFound = false;
				
				Cell cell = row.getCell(line.getColumn());
				if(cell==null){
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (j+1) + "行,第" + (line.getColumn()+1) +"的信息为空！");
					break;
				} else {
					String value = null;
					CellType cellType = cell.getCellTypeEnum();
					if(cellType==CellType.STRING){
						value = cell.getStringCellValue();
					} else if(cellType == CellType.NUMERIC){
						if(DateUtil.isCellDateFormatted(cell)){
							value = cell.getDateCellValue().toString();
						}else{
							if(cell.getNumericCellValue()%1==0)value = new Integer(new Double(cell.getNumericCellValue()).intValue()).toString();
							else value = String.valueOf(cell.getNumericCellValue());
						}
					}
					
					ArrayList dict_items = line.getDict_items();
					for(int l=0; l<dict_items.size(); l++){
						DictItem item = (DictItem)dict_items.get(l);
						if(value.equals(item.getItemname())){
							isFound = true;
							break;
						}
					}
					
					if(line.getDis_method()==Constants.V_DISPLAY_MULTIPLE_COMBOBOX){
						//String value1 = line.getValue();
						logger.info("column:" + line.getColumn());
						logger.info("value :" + line.getValue());
						if(value.charAt(0)=='[' && value.charAt(value.length()-1)==']'){
							allFound = true;
							String temp = value.substring(1, value.length()-1);
							String[] str = temp.split(",");
							for(int m=0; m<str.length; m++){
								String temp1 = str[m];
								isFound = false;
								for(int n=0; n<dict_items.size(); n++){
									DictItem item1 = (DictItem)dict_items.get(n);
									if(temp1.equals(item1.getItemname())){
										isFound = true;
										break;
									}
								}
								if(!isFound){
									allFound = false;
									break;
								}
							}
						}
					}
				}
				
				if(isFound){
					
				} else if(allFound){
					
				} else {
					message.setMessage_type(Constants.RULECHECK_MESSAGE_RULE_FAIL);
					messageList.add("第" + (j+1) + "行,第" + (line.getColumn()+1) + "列的信息不在数据字典里！");
				}
			}
		}
		message.setMessage_info(messageList);
		return message;
		
	}
}
