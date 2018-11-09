/*
 * Created on 2004-7-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.system.util;

/** jsp中使用的静态Logger,不必初始化
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JSPLogger
{
	private static Logger logger = new Logger("<<JSP>>");

	private JSPLogger()
	{

	}

	/**
	 * debug
	 * @param info
	 */
	public static void debug(Object o)
	{
		logger.debug(o);
	}
	public static void debug(Object o, Throwable exp)
	{
		logger.debug(o, exp);
	}
	/**
	 * info
	 * @param o
	 */
	public static void info(Object o)
	{
		logger.info(o);
	}
	public static void info(Object o, Throwable exp)
	{
		logger.info(o, exp);
	}
	/**
	 * warn
	 * @param o
	 */
	public static void warn(Object o)
	{
		logger.warn(o);
	}
	public static void warn(Object o, Throwable exp)
	{
		logger.warn(o, exp);
	}
	/**
	 * error
	 * @param o
	 */
	public static void error(Object o)
	{
		logger.error(o);
	}
	public static void error(Object o, Throwable exp)
	{
		logger.error(o, exp);
	}
	/**
	 * fatal
	 * @param o
	 */
	public static void fatal(Object o)
	{
		logger.fatal(o);
	}
	public static void fatal(Object o, Throwable exp)
	{
		logger.fatal(o, exp);
	}
}
