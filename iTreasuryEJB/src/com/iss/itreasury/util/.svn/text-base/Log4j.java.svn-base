/*
 * Created on 2003-8-27
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Log4j implements Serializable
{
	private static HashMap log_map=new HashMap(); 
	private Logger logger=null;
	private static final String defaultLogName="DefaultLog:";
	
	public static void main(String[] args)
	{
		Log4j log = new Log4j(2);
		log.error("hello!");
	}
	public Log4j(Object object)
	{
		String logName=defaultLogName;
		if (object!=null)
			logName=object.getClass().getName();
		
		logger=(Logger)log_map.get( logName );
		if (logger==null)
		{
			logger=LogManager.getLogger(logName);
			log_map.put( logName,logger);
		}
	}
	public Log4j(long lModuleID, Object object)
	{
		this(lModuleID);
	}
	public Log4j(long lModuleID)
	{
		
			try {
				String logName=getModuleName(lModuleID);
				if (logName==null||logName.length()<1) logName=defaultLogName;
				
				logger=(Logger)log_map.get( logName );
				if (logger==null)
				{
					logger=LogManager.getLogger(logName);
					log_map.put( logName,logger);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	private String getModuleName(long moduleID) throws Exception
	{
		return Constant.ModuleType.getName( moduleID);
	}
	public void debug(String message)
	{
			 logger.debug( message );			 
	}
	public void info(String message)
	{
			 logger.info(message);			 
	}
	public void warn(String message)
	{
			 logger.warn(message);			 
	}
	public void error(String message)
	{
			 logger.error(message);			 
	}
	public void fatal(String message)
	{
			 logger.fatal(message);			 
	}
	public void print(String message)
	{
			 logger.info( message );			 
	}	
}
