package com.philip.edu.basic;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class DictManager {
	private Logger logger = Logger.getLogger(DictManager.class);
	
	private DictDAO dao = new DictDAO();
	
	public DictGroup getDictGroupById(int groupid){
		return dao.getDictGroup(groupid);
	}
	
	public ArrayList getDictByGroup(int groupid){
		return dao.getDictByGroup(groupid);
	}
	
	public Dict getDictById(int dictid){
		return dao.getDictById(dictid);
	}
	
	public ArrayList getDictItemByDict(int dictid){
		return dao.getDictItemByDict(dictid);
	}
}
