package com.hp.alg;

import java.util.ArrayList;

public class List2Arr
{
	public static String[] convertString(ArrayList<String> arrRes, String[] arr)
	{
		int size = arrRes.size();
		arr = new String[size];
		
		for(int i = 0; i < size; i ++)
		{
			arr[i] = arrRes.get(i);
		}
		return arr;
	}
}
