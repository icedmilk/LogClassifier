package com.hp.main;

import com.hp.model.ResolveLog;
import com.hp.xml.ConfigReader;

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
