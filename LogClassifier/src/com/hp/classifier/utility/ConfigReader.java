package com.hp.classifier.utility;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ConfigReader
{
	/**
	 * Description:	Calculate the configuration count
	 * @return		Configuration count
	 */
	public static int configCount()
	{
		try
		{
			File f = new File(System.getProperty("user.dir")
					+ "/src/config.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("value");
			return nl.getLength();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Description:	Read the configuration file
	 * @param args	configuration parameters 
	 */
	public static void read(String[] args)
	{
		try
		{
			File f = new File(System.getProperty("user.dir")
					+ "/src/config.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);

			args[0] = doc.getElementsByTagName("RecorderCount").item(0)
					.getFirstChild().getNodeValue();
			args[1] = doc.getElementsByTagName("Statistic").item(0)
					.getFirstChild().getNodeValue();
			args[2] = doc.getElementsByTagName("File").item(0).getFirstChild()
					.getNodeValue();
			args[3] = doc.getElementsByTagName("ResolvedResults").item(0)
					.getFirstChild().getNodeValue();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
