package com.philip.edu.basic;

public class Constants {
	public static final int USER_ID = 1;
	public static final int FORM_ID = 2;
	public static final int FORM_ID_TEST = 22;
	public static final int TASK_ID = 3;
	public static final String FORM_NAME = "XXGK";
	public static final String FIELD_NAME = "XQMC";
	public static final int RULE_CLASS = 3;
	public static final char RULE_ACTIVE = 'Y';
	public static final int LAP_YEAR = 1900;
	public static final int SYSTEM_TASK_ID = 11;
	public static final int DATA_PAGE_SIZE = 10;
	public static final int DATA_PAGE_SHOW = 5000;
	
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
	public static final String RULE_MULTIPLE = "multiple";
	
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
	
	public static final char V_TYPE_STAT_TIME_POINT = 'P';
	public static final String DIP_TYPE_STAT_TIME_POINT = "时点";
	public static final char V_TYPE_STAT_TIME_STUDY_YEAR = 'S';
	public static final String DIP_TYPE_STAT_TIME_STUDY_YEAR = "学年";
	public static final char V_TYPE_STAT_TIME_NATURE_YEAR = 'N';
	public static final String DIP_TYPE_STAT_TIME_NATURE_YEAR = "自然年";
	public static final char V_TYPE_STAT_TIME_CURRENT_YEAR = 'C';
	public static final String DIP_TYPE_STAT_TIME_CURRENT_YEAR = "当前年";
	
	public static final char V_FORM_TYPE_PERSONALIZED = 'P';
	public static final String DIP_FORM_TYPE_PERSONALIZED = "自定义表";
	public static final char V_FORM_TYPE_EDUCATION_STATUS = 'S';
	public static final String DIP_FORM_TYPE_EDUCATION_STATUS = "教学基本状态库";
	public static final char V_FORM_TYPE_PROFESSIONAL_VALUE = 'V';
	public static final String DIP_FORM_TYPE_PROFESSIONAL_VALUE = "专业评估";
	public static final char V_FORM_TYPE_CLASS_VALUE = 'C';
	public static final String DIP_FORM_TYPE_CLASS_VALUE = "课程评估";
	public static final char V_FORM_TYPE_HIGHBASIC_REPORT = 'H';
	public static final String DIP_FORM_TYPE_HIGHBASIC_REPORT = "高基报表";
	
	public static final char V_DISPLAY_SINGLE_RECORD = 'S';
	public static final String DIP_DISPLAY_SINGLE_RECORD = "单条记录";
	public static final char V_DISPLAY_GENERAL_LIST = 'G';
	public static final String DIP_DISPLAY_GENERAL_LIST = "通用列表";
	
	public static final char V_DISPLAY_SINGLE_TEXTBOX = 'T';
	public static final String DIP_DISPLAY_SINGLE_TEXTBOX = "单行输入框";
	public static final char V_DISPLAY_MUTIPLE_TEXTBOX = 'M';
	public static final String DIP_DISPLAY_MUTIPLE_TEXTBOX = "多行输入框";
	public static final char V_DISPLAY_RICH_TEXT = 'R';
	public static final String DIP_DISPLAY_RICH_TEXT = "富文本";
	public static final char V_DISPLAY_DATE_CONTROL = 'D';
	public static final String DIP_DISPLAY_DATE_CONTROL = "日期控件";
	public static final char V_DISPLAY_SINGLE_COMBOBOX = 'C';
	public static final String DIP_DISPLAY_SINGLE_COMBOBOX = "下拉单选框";
	public static final char V_DISPLAY_MULTIPLE_COMBOBOX = 'P';
	public static final String DIP_DISPLAY_MUTLIPLE_COMBOBOX = "下拉多选框";
	public static final char V_DISPLAY_UPLOAD_CONTROL = 'U';
	public static final String DIP_DISPLAY_UPLOAD_CONTROL = "文件上传控件";
	
	public static final char V_FIELD_TYPE_PHYSIC_COLUMN = 'P';
	public static final String DIP_FIELD_TYPE_PHYSIC_COLUMN = "实体列";
	public static final char V_FIELD_TYPE_VIRTURAL_COLUMN = 'V';
	public static final String DIP_FIELD_TYPE_VERTURAL_COLUMN = "虚拟列";
	public static final char V_FIELD_TYPE_COMPUTE_COLUMN = 'C';
	public static final String DIP_FIELD_TYPE_COMPUTE_COLUMN = "计算列";
	
	public static final int V_DATA_TYPE_STRING = 1;
	public static final String DIP_DATA_TYPE_STRING = "字符型（字数小于2000）";
	public static final int V_DATA_TYPE_INTEGER = 2;
	public static final String DIP_DATA_TYPE_INTEGER ="整数型";
	public static final int V_DATA_TYPE_FLOAT = 3;
	public static final String DIP_DATA_TYPE_FLOAT = "小数型";
	public static final int V_DATA_TYPE_DATE = 4;
	public static final String DIP_DATA_TYPE_DATE = "日期型";
	public static final int V_DATA_TYPE_BIG_STRING = 5;
	public static final String DIP_DATA_TYPE_BIG_STRING = "字符型（字数大于2000）";
	
	public static final char V_TEXT_FORMAT_WEBSITE = 'w';
	public static final String DIP_TEXT_FORMAT_WEBSITE = "网址";
	public static final char V_TEXT_FORMAT_MOBILEPHONE = 'p';
	public static final String DIP_TEXT_FORMAT_MOBILEPHONE = "手机号码";
	public static final char V_TEXT_FORMAT_EMAIL = 'e';
	public static final String DIP_TEXT_FORMAT_EMAIL = "Email";
	public static final char V_TEXT_FORMAT_IDENTITY = 'i';
	public static final String DIP_TEXT_FORMAT_IDENTITY = "身份证";
	public static final char V_TEXT_FORMAT_DATE_YEAR = 'y';
	public static final String DIP_TEXT_FORMAT_DATE_YEAR = "YYYY";
	public static final char V_TEXT_FORMAT_DATE_MONTH = 'm';
	public static final String DIP_TEXT_FORMAT_DATE_MONTH = "YYYY-MM";
	public static final char V_TEXT_FORMAT_DATE_MONTH_NOSLASH = 'n';
	public static final String DIP_TEXT_FORMAT_DATE_MONTH_NOSLASH = "YYYYMM";
	public static final char V_TEXT_FORMAT_DATE_DAY = 'd';
	public static final String DIP_TEXT_FORMAT_DATE_DAY = "YYYY-MM-DD";
	public static final char V_TEXT_FORMAT_NO = 'o';
	public static final String DIP_TEXT_FORMAT_NO = " ";
	
	public static final char TASK_STATUS_ACTIVE = 'A';
	public static final char TASK_STATUS_CLOSE = 'C';
	
	public static final int DICT_GROUP_ID = 4;
}
