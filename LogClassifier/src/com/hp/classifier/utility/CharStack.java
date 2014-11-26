package com.hp.classifier.utility;

public class CharStack
{
	public char c;
	public int index;
	
	public CharStack()
	{
		c = ' ';
		index = 0;
	}
	
	public CharStack(char c, int index)
	{
		this.c = c;
		this.index = index;
	}
}
