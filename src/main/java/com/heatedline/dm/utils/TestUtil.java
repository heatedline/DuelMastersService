package com.heatedline.dm.utils;

import java.nio.charset.Charset;

public class TestUtil {
	
	public static void main(String[] args) {
		
		String s = "光輪|";
		byte[] bytes = s.getBytes(Charset.forName("UTF-8"));
		String str = new String(bytes, Charset.forName("UTF-8"));
		System.out.println(str);
	}

}
