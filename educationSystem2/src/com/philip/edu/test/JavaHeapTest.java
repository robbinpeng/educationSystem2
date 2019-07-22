package com.philip.edu.test;

import java.util.Vector;

public class JavaHeapTest {

	static Vector v = new Vector(10);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 100; i++) {
		    Object o = new Object();
		    v.add(o);
		    o = null;
		}
	}

}
