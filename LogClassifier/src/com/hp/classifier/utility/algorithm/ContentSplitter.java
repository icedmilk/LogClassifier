package com.hp.classifier.utility.algorithm;

import java.util.ArrayList;
import java.util.Stack;

import com.hp.classifier.utility.CharStack;

public class ContentSplitter
{

	public static int defaultCharactersPerLine = 50;

	public static int defaultLines = 3;

	public static String[] mysplit(String content)
	{
		return content.split("\\s+");
	}

	public static void pushStr(Stack<CharStack> s, int end, String[] tempRes,
			ArrayList<String> arrRes)
	{
		int start = s.pop().index;
		if (s.empty())
		{
			String str = new String();
			for (int j = start; j <= end; j++)
			{
				str += tempRes[j];
				if (j != end)
					str += " ";
			}
			arrRes.add(str);
		}
	}

	public static String[] mysplit2(String content)
	{
		content = ContentFilter.removeEnter(content);

		String[] tempRes = content.split("\\s+");

		Stack<CharStack> s = new Stack<CharStack>();

		ArrayList<String> arrRes = new ArrayList<String>();

		for (int i = 0; i < tempRes.length; i++)
		{
			boolean usedFlag = true;
			switch (tempRes[i].charAt(0))
			{
			case '"':
				s.push(new CharStack('"', i));
				break;

			case '\'':
				s.push(new CharStack('\'', i));
				break;

			case '[':
				s.push(new CharStack('[', i));
				break;

			case '<':
				s.push(new CharStack('<', i));
				break;

			default:
				usedFlag = false;
				break;
			}

			// Pop or add
			if (!s.empty())
				switch (tempRes[i].charAt(tempRes[i].length() - 1))
				{
				case '"':
					if ('"' == s.peek().c)
					{
						usedFlag = true;
						pushStr(s, i, tempRes, arrRes);
					}
					break;

				case '\'':
					if ('\'' == s.peek().c)
					{
						usedFlag = true;
						pushStr(s, i, tempRes, arrRes);
					}
					break;

				case ']':
					if ('[' == s.peek().c)
					{
						usedFlag = true;
						pushStr(s, i, tempRes, arrRes);
					}
					break;

				case '>':

					if ('<' == s.peek().c)
					{
						usedFlag = true;
						pushStr(s, i, tempRes, arrRes);
					}
					break;

				default:

					break;
				}
			if (!usedFlag && s.empty())
				arrRes.add(tempRes[i]);
		}

		String[] finalRes = null;
		return List2Arr.convertString(arrRes, finalRes);

	}


}