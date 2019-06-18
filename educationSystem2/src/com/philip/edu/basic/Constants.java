package com.philip.edu.basic;

public class Constants {
	public static final int USER_ID = 1;
	public static final int FORM_ID = 2;
	public static final int FORM_ID_TEST = 3;
	public static final String FORM_NAME = "XXGK";
	public static final String FIELD_NAME = "XQMC";
	public static final int RULE_CLASS = 3;
	public static final char RULE_ACTIVE = 'Y';
	public static final int LAP_YEAR = 1900;
	
	//Operator:
	public static final String LESST = "<";
	public static final String LESSTE = "<=";
	public static final String EQUAL = "=";
	public static final String NOEQUAL = "!=";
	public static final String GREATT = ">";
	public static final String GREATTE = ">=";
	
	public static final String V_LESST = "op1";
	public static final String V_LESSTE = "op2";
	public static final String V_EQUAL = "op3";
	public static final String V_NOEQUAL = "op4";
	public static final String V_GREATT = "op5";
	public static final String V_GREATTE = "op6";
	
	public static final String ADD = "+";
	public static final String MINUS = "-";
	public static final String MULTIPL = "*";
	public static final String DIVIDE = "/";
	
	public static final String V_ADD = "op7";
	public static final String V_MINUS = "op8";
	public static final String V_MUTIPL = "op9";
	public static final String V_DIVIDE = "op10";
	
	public static final String AND = "并且";
	public static final String V_AND = "op11";
	
	public static final String OR = "或者";
	public static final String V_OR = "op12";
	
	public static final String NOT = "不";
	public static final String V_NOT = "op13";
	
	//rule Type:
	public static final String RULE_CONDITION = "condition";
	public static final String RULE_FORMFIELD = "field";
	public static final String RULE_OPERATOR = "operator";
	public static final String RULE_TEXTBOX = "textbox";
	public static final String RULE_OP_AND = "operator1";
	public static final String RULE_RELATE_FORM = "relateForm";
	public static final String RULE_RELATE_FORM_TWO = "relateForm2";
	public static final String RULE_FIELD_EQUAL = "fieldEqual";
	public static final String RULE_NO = "no";
	public static final String RULE_OR = "or";
	public static final String RULE_YEAR_ADD = "addYear";
	public static final String RULE_MONTH = "month";
	public static final String RULE_DAY = "day";
	public static final String RULE_SIMPLE_ADD = "sAdd";
	public static final String RULE_TEXTBOX_DATE ="textDate";
	public static final String RULE_DATE_FORMAT = "format";
	public static final String RULE_FIELD_SUM = "fieldSum";
	public static final String RULE_FORM_SUM = "form";
	
	public static final int RULECHECK_MESSAGE_NOT_IMPLEMENT = 0;
	public static final int RULECHECK_MESSAGE_SUCCESS = 1;
	public static final int RULECHECK_MESSAGE_FORMAT_WRONG = 2;
	public static final int RULECHECK_MESSAGE_RULE_FAIL = 3;
	
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "1982214";
	
	public static final int LINE_TYPE_FIELD_NAME = 1;
	public static final int LINE_TYPE_OPERATOR = 2;
	public static final int LINE_TYPE_VALUE = 3; 
	
	//text displayed:
	public static final String DISPLAY_RULE_PATTERN_LINE = "行级校验";
	public static final String DISPLAY_RULE_PATTERN_TABLE = "表级校验";
	public static final char VALUE_RULE_PATTERN_LINE = '1';
	public static final char VALUE_RULE_PATTERN_TABLE = '2';
	
	public static final String DISPLAY_RULE_CLASS_1 = "1.字段内关系校验";
	public static final String DISPLAY_RULE_CLASS_2 = "2.排他性约束校验";
	public static final String DISPLAY_RULE_CLASS_3 = "3.存在性校验";
	public static final String DISPLAY_RULE_CLASS_4 = "4.唯一性校验";
	public static final String DISPLAY_RULE_CLASS_5 = "5.外表约束校验";
	public static final String DISPLAY_RULE_CLASS_6 = "6.时间日期校验";
	
	public static final String DISPLAY_RULE_ACTIVE = "已启用";
	
	//data collection status:
	public static final char STATUS_CREATED = 'C';
	public static final char STATUS_UPLOADABLE = 'U';
	public static final char STATUS_SUCCESS = 'S';
}
