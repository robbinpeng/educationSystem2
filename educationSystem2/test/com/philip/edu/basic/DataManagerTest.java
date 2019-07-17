package com.philip.edu.basic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DataManagerTest {

	@Test
	public void testGetTableData() {
		DataManager manager = new DataManager();
		ArrayList al = manager.getTableData(35);
		assertNotEquals(al.size(),0);
	}

}
