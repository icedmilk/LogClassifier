package com.hp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hp.alg.ContentSplitter;
import com.hp.alg.LCS;

public class ResolveLog
{
	public static HashMap<String, Integer> hm = new HashMap<String, Integer>();

	public final static double initialSimilarityRate = 0.7;
	public final static double finalSimilarityRate = 0.9;

	public static double currentSimilarityRate = initialSimilarityRate;

	public static double increasingSpeed = (finalSimilarityRate - initialSimilarityRate) / 100000.0;
	
	public final static int logNo = 7;
	
	public static void increasmentFunc()
	{
		if (currentSimilarityRate < finalSimilarityRate)
			currentSimilarityRate += increasingSpeed;
		if (currentSimilarityRate > finalSimilarityRate)
			currentSimilarityRate = finalSimilarityRate;
	}
	
	public static void resolveLine_LCS(String logByLine, String resolvedResult)
	{
		String[] str = logByLine.split("\\|");

		if (resolvedResult.equals("true"))
		{
			for (String temp : str)
				System.out.print(temp + "\t");
			System.out.println();
		}

		// statistic
		int typeCount = 1;

		Set<String> keySet = hm.keySet();
		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext())
		{
			String key = iterator.next();
			String [] splitStr = ContentSplitter.mysplit2(str[logNo]);
			String [] splitKey = ContentSplitter.mysplit2(key);
			String [] sequence = LCS.alg_String(splitKey, splitStr);

			double similarity = (double) sequence.length
					/ (double) ((splitStr.length + splitKey.length)/2.0);

			if (similarity > currentSimilarityRate)
			{
				increasmentFunc();
//				System.out.println(currentSimilarityRate);
				Integer count = hm.get(key);
				hm.remove(key);
				
				String seq = new String();
				for(int i = 0; i < sequence.length; i++)
				{
					seq += sequence[i];
					seq += " ";
				}
				
				hm.put(seq, count + 1);
				return;
			}

		}
		hm.put(str[logNo], typeCount);

	}

	public static void readFileByLines(String fileName, String resolvedResult,
			int recorderCount)
	{
		
		File file = new File(fileName);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			if (resolvedResult.equals("true"))
			{
				System.out
						.println("user\t\t\t\tport\tfile\t\t\t\tdate\t\t\t\ttype\t\tport\tip\t\t\tinfo\t");
			}
			recorderCount = recorderCount == 0 ? 0x7fffffff : recorderCount;
			
			increasingSpeed = (finalSimilarityRate - initialSimilarityRate) / (double) recorderCount;
//			System.out.println(increasingSpeed);
			int i = 0;

			while (i++ < recorderCount
					&& ((tempString = reader.readLine()) != null))
			{
				resolveLine_LCS(tempString, resolvedResult);

			}

			reader.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Description: Summarize the statistical results
	 */
	public static void summary()
	{
		System.out.println();
		Set<String> keySet = hm.keySet();
		Iterator<String> iterator = keySet.iterator();

		Integer total_Count = 0;

		while (iterator.hasNext())
		{
			String key = iterator.next();
			total_Count += hm.get(key);
		}

		iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			Integer count = hm.get(key);
			double ratio = (double) hm.get(key) / (double) total_Count * 100;

			System.out.print(key + ": " + count + "\t");
			System.out.printf("%.2f", ratio);
			System.out.println("%");
		}
	}

}
