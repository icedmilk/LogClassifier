package com.hp.classifier.utility.algorithm;

import java.util.ArrayList;

public class LCS
{
	public int len = 0;
	public String[] str = null;

	public String alg(String x, String y)
	{
		String result = new String();
		int M = x.length();
		int N = y.length();

		// opt[i][j] = length of LCS of x[i..M] and y[j..N]
		int[][] opt = new int[M + 1][N + 1];

		// compute length of LCS and all subproblems via dynamic programming
		for (int i = M - 1; i >= 0; i--)
		{
			for (int j = N - 1; j >= 0; j--)
			{
				if (x.charAt(i) == y.charAt(j))
					opt[i][j] = opt[i + 1][j + 1] + 1;
				else
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
			}
		}

		// recover LCS itself and print it to standard output
		int i = 0, j = 0;
		while (i < M && j < N)
		{
			if (x.charAt(i) == y.charAt(j))
			{
				result += x.charAt(i);
				i++;
				j++;
			}
			else if (opt[i + 1][j] >= opt[i][j + 1])
				i++;
			else
				j++;
		}
		return result;
	}

	public LCS alg_String(String[] x, String[] y)
	{
		ArrayList<String> result = new ArrayList<String>();
		int M = x.length;
		int N = y.length;

		// opt[i][j] = length of LCS of x[i..M] and y[j..N]
		int[][] opt = new int[M + 1][N + 1];

		// compute length of LCS and all subproblems via dynamic programming
		for (int i = M - 1; i >= 0; i--)
		{
			for (int j = N - 1; j >= 0; j--)
			{
				if (x[i].equals(y[j]))
					opt[i][j] = opt[i + 1][j + 1] + 1;
				else
					opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
			}
		}

		// recover LCS itself and print it to standard output
		int i = 0, j = 0;
		boolean addFlag = true;
		while (i < M && j < N)
		{
			if (x[i].equals(y[j]))
			{
				addFlag = true;
				result.add(x[i]);
				i++;
				j++;
			}
			else if (opt[i + 1][j] >= opt[i][j + 1])
			{
				if (addFlag)
				{
					result.add("*");
					addFlag = false;
				}
				i++;

			}
			else
			{
				if (addFlag)
				{
					result.add("*");
					addFlag = false;
				}
				result.add("*");
				j++;
			}
		}
		final int size = result.size();
		String[] res = new String[size];
		for (int k = 0; k < size; k++)
		{
			res[k] = result.get(k);
		}
		LCS lcs = new LCS();
		lcs.len = opt[0][0];
		lcs.str = res;
		return lcs;

	}

}
