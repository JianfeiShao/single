package com.zht.train;

import org.junit.Test;

public class utilTool {
	@Test
	public void subString(){
		String s = "asdfxfasd:'fasdffs:'adee:'eeeeee';www';fsdfs';";
		int st = s.indexOf(":'");
		int end = s.indexOf("';");
		System.out.println(st);
		System.out.println(end);
		System.out.println(s.substring(st,end));
	}
}
