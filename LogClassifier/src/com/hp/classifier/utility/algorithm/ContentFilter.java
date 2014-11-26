package com.hp.classifier.utility.algorithm;

public class ContentFilter
{
	public static String removeEnter(String content)
	{
		return content.replaceAll("\\\\n", "\n");
	}
}
