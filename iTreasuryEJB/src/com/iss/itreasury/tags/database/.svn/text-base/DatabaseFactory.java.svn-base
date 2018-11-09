/*
 * Created on 2005-8-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.tags.database;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;



/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DatabaseFactory {
    
    public static IDatabase getInstance()
	{
        IDatabase aDatabase = null;
        String className = readClassName();
        
		Class cls = null;
		try
		{
		    cls = Class.forName(className);
		    System.out.println(cls);
		    Object o = cls.newInstance();
		    System.out.println(o);
		    aDatabase = (IDatabase) o;
		    System.out.println(aDatabase);
		}
		catch (Exception e)
		{
			System.out.println(className + ":" + e.getMessage());
			e.printStackTrace();
		}
		return aDatabase;
	}
    /**
     * 取的类名
     * @return
     */
    private static String readClassName()
    {
        String s = "com.iss.itreasury.tags.database.Database";//默认取TAG自带的数据库类
        try {
             s = parse("issTag-default.xml");
        } catch (Exception e) {
            System.out.println("load issTag-default.xml error! read default");
            e.printStackTrace();
        }
        return s;
    }
    /**
    ** 解析指定的URL，返回指定的类名
    */
    private static String parse(String url) throws SAXException, IOException,FileNotFoundException 
    {
        String className = null;
        DOMParser parser = new DOMParser();

        parser.parse(url);
        Document document = parser.getDocument();
        NodeList nodes = document.getElementsByTagName("database");
        for (int i = 0; i < nodes.getLength(); i++)
        {
         	Element element =(Element)nodes.item(i);
         	NodeList names = element.getElementsByTagName("classname"); 
         	Element e = (Element) names.item(0); 
         	Text t = (Text) e.getFirstChild(); 
         	className = t.getNodeValue();
        }
        
        return className;
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(DatabaseFactory.getInstance().getTagConnection());
    }
}
