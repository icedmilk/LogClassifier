package com.hp.classifier.main;

import com.hp.classifier.utility.ConfigReader;
import com.hp.classifier.utility.ResolveLog;

public class StartClassifier
{

	static String[] arguments;

	/**
	 * Description: Read the config.xml file
	 */
	public static void readConfig()
	{
		final int count = ConfigReader.configCount();

		arguments = new String[count];

		for (int i = 0; i < arguments.length; i++)
		{
			arguments[i] = new String();
		}
		ConfigReader.read(arguments);

	}

	public static void main(String[] args)
	{
		readConfig();
		ResolveLog.readFileByLines(arguments[2], arguments[3],
				Integer.parseInt(arguments[0]));
		if (arguments[1].equals("true"))
			ResolveLog.summary();
	}

}
