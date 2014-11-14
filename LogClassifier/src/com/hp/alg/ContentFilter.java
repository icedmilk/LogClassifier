package com.hp.alg;

public class ContentFilter
{
	public static String removeEnter(String content)
	{
		return content.replaceAll("\\\\n", "\n");
	}
}
