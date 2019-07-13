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
	}

}
