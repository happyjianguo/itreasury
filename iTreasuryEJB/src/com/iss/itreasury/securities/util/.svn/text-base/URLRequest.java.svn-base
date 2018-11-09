package com.iss.itreasury.securities.util ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/*
 * Created on 2004-7-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class URLRequest
{
	/**
	 *  
	 */
	public URLRequest ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
	}
	public static void main ( String[] args )
	{
		StringBuffer sb = new StringBuffer ( ) ;
		try
		{
			String url = "http://" + args[0] + "/NASApp/iTreasury-securities/EndProcess.jsp" ;
			System.out.println ( url ) ;
			URL reverseURL = new URL ( url ) ;
			URLConnection conn ;
			conn = reverseURL.openConnection ( ) ;
			//			conn.setDoOutput(true);
			//			PrintStream out = new PrintStream(conn.getOutputStream());
			//			out.println("String = Text to Reverse.");
			BufferedReader in = new BufferedReader ( new InputStreamReader ( conn.getInputStream ( ) ) ) ;
			int c ;
			while ((c = in.read ( )) != -1)
			{
				sb.append ( (char) c ) ;
			}
			System.out.println ( sb.toString ( ) ) ;
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace ( ) ;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace ( ) ;
		}
	}
}