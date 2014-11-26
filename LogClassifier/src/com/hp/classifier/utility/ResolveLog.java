package com.hp.classifier.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hp.classifier.utility.algorithm.ContentSplitter;
import com.hp.classifier.utility.algorithm.LCS;

class LogSave
{
	public int count;
	public ArrayList<String> savedLogs;

	public LogSave(String log)
	{
		this.count = 1;
		savedLogs = new ArrayList<String>();
		savedLogs.add(log);
	}

	public LogSave(ArrayList<String> al, int count)
	{
		this.count = count;
		savedLogs = new ArrayList<String>();
		for (String string : al)
		{
			savedLogs.add(string);
		}

	}

	public LogSave merge(LogSave ls)
	{
		this.count += ls.count;
		for (String string : ls.savedLogs)
		{
			this.savedLogs.add(string);
		}
		return new LogSave(this.savedLogs, this.count);
	}

	public LogSave update(String log)
	{
		this.count++;
		savedLogs.add(log);
		return new LogSave(this.savedLogs, this.count);
	}
}

public class ResolveLog
{
	public static HashMap<String, LogSave> hm = new HashMap<String, LogSave>();

	public final static double initialSimilarityRate = 0.83;
	public final static double finalSimilarityRate = 0.83;

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
		// split algorithm
		/*
		 * String[] str = logByLine.split("\\|");
		 * 
		 * if (resolvedResult.equals("true")) { for (String temp : str)
		 * System.out.print(temp + "\t"); System.out.println(); }
		 */

		// start statistic
		Set<String> keySet = hm.keySet();
		Iterator<String> iterator = keySet.iterator();

		// traverse the hash table
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String[] splitStr = ContentSplitter.mysplit(logByLine);
			String[] splitKey = ContentSplitter.mysplit(key);

			LCS lcs = new LCS();
			String[] sequence = lcs.alg_String(splitKey, splitStr).str;
			int similar = lcs.alg_String(splitKey, splitStr).len;

			// calculate the similarity
			double similarity = (double) similar
					/ (double) ((splitStr.length + splitKey.length) / 2.0);

			// replace
			if (similarity > currentSimilarityRate)
			{
				increasmentFunc();

				LogSave origin = hm.get(key);
				LogSave s = origin.update(logByLine);

				String seq = new String();
				for (int i = 0; i < sequence.length; i++)
				{
					seq += sequence[i];
					seq += " ";
				}

				hm.remove(key);

				LogSave originsave = hm.get(seq);
				if (originsave != null)
					hm.put(seq, originsave.merge(s));
				else
					hm.put(seq, s);

				return;
			}

		}

		LogSave originsave = hm.get(logByLine);
		if (originsave != null)
			hm.put(logByLine, originsave.merge(new LogSave(logByLine)));
		else
			hm.put(logByLine, new LogSave(logByLine));

	}

	/**
	 * read the file by line and resolve the line
	 * 
	 * @param fileName
	 * @param resolvedResult
	 * @param recorderCount
	 */
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

			increasingSpeed = (finalSimilarityRate - initialSimilarityRate)
					/ (double) recorderCount;

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
			total_Count += hm.get(key).count;
		}

		iterator = hm.keySet().iterator();

		File f = new File("C:\\123.txt");
		BufferedWriter output = null;
		try
		{
			output = new BufferedWriter(new FileWriter(f));
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		while (iterator.hasNext())
		{
			String key = iterator.next();
			Integer count = hm.get(key).count;
			double ratio = (double) hm.get(key).count / (double) total_Count
					* 100;
			ArrayList<String> savelog = hm.get(key).savedLogs;

			for (String string : savelog)
			{
				String res = string + "\t" + key + "\t" + count + "\t" + ratio
						+ "%";
				System.out.println(res);
				try
				{
					output.write(res);
					output.newLine();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			// another way to write data 
			/*
			for (String string : savelog)
			{
				originData += string + ";";
			}

			String res = originData + "\t" + key + "\t" + count + "\t" + ratio
					+ "%";
			System.out.println(res);
			try
			{
				output.write(res);
				output.newLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			System.out.print(key + ": " + count + "\t");
			System.out.printf("%.2f", ratio);
			System.out.println("%");
			*/
		}
		try
		{
			output.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
