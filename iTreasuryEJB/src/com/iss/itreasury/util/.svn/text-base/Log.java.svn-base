/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;

/**
 * @author yiwang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Log
{
	static Log4j log4j=new Log4j(null);
	public Log()
	{
	}
	public static void print(String s)
	{
			 log4j.debug( s ); 
	}
	public static void print(long l)
	{
			 log4j.debug(""+l );	 
	}
	public static void print(double d)
	{
			 log4j.debug(""+d );	 
	}
	public static void print(float f)
	{
			 log4j.debug(""+f );	 
	}
	public static void print(boolean b)
	{
			 log4j.debug(""+b );	 
	}
	public static void print(char c)
	{
			 log4j.debug(""+c );	 
	}
	public static void print(Object o)
	{
		try
		{			
			if (o!=null){				
					 log4j.debug(""+o.toString() );	 
			}
		}catch(Exception e){
			log4j.error( e.toString() );
		}
		
	}
	public static void print(java.sql.Timestamp ts)
	{
		try
		{
			if (ts!=null){
					 log4j.debug(""+ts.toString() );	 
			}
		}catch(Exception e){
			log4j.error( e.toString() );
		}
	}
	public static void print(java.sql.Date dt)
	{
		try
		{
			if (dt!=null){
					 log4j.debug(""+dt.toString() );	 
			} 
		}catch(Exception e){
			log4j.error( e.toString() );
		}

	}
}
