package com.philip.edu.test;

import java.io.File;
import java.util.regex.Pattern;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = new String("\\test\\getTest\\test_name.xml");
		String[] testA = test.split(Pattern.quote(File.separator));
		String out1 = testA[testA.length-1];
		System.out.println(out1);
		
		String test1 = new String("123456:12323");
		System.out.println(test1.substring(6));
		
		String test2 = new String("123\"fsd");
		System.out.println(test2.contains("\""));
	}

}
