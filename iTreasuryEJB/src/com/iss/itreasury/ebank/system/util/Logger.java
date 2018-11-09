package com.iss.itreasury.ebank.system.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 日志工具类<br>
 * 提供基本的日志记录功能。当前采用apache common logging实现
 * @author mxzhou
 */
public class Logger
{
	private Log logger = null;

	/**
	 * Constructor for Logger.
	 * @param arg0
	 */
	public Logger(String name)
	{
		super();
		this.logger = LogFactory.getLog(name);
	}

	public Logger(Class clazz)
	{
		super();
		this.logger = LogFactory.getLog(clazz);
	}


	/**
	 * @see org.apache.log4j.Category#debug(java.lang.Object, java.lang.Throwable)
	 */
	public void debug(Object arg0, Throwable arg1)
	{
		if (logger.isDebugEnabled())
			logger.debug(arg0, arg1);

	}

	/**
	 * @see org.apache.log4j.Category#debug(java.lang.Object)
	 */
	public void debug(Object arg0)
	{
		if (logger.isDebugEnabled())
			logger.debug(arg0);
	}

	/**
	 * @see org.apache.log4j.Category#error(java.lang.Object, java.lang.Throwable)
	 */
	public void error(Object arg0, Throwable arg1)
	{
		if (logger.isErrorEnabled())
			logger.error(arg0, arg1);
	}

	/**
	 * @see org.apache.log4j.Category#error(java.lang.Object)
	 */
	public void error(Object arg0)
	{
		if (logger.isErrorEnabled())
			logger.error(arg0);
	}

	/**
	 * @see org.apache.log4j.Category#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public void fatal(Object arg0, Throwable arg1)
	{
		if (logger.isFatalEnabled())
			logger.fatal(arg0, arg1);
	}

	/**
	 * @see org.apache.log4j.Category#fatal(java.lang.Object)
	 */
	public void fatal(Object arg0)
	{
		if (logger.isFatalEnabled())
			logger.fatal(arg0);
	}

	/**
	 * @see org.apache.log4j.Category#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object arg0, Throwable arg1)
	{
		if (logger.isInfoEnabled())
			logger.info(arg0, arg1);
	}

	/**
	 * @see org.apache.log4j.Category#info(java.lang.Object)
	 */
	public void info(Object arg0)
	{
		if (logger.isInfoEnabled())
			logger.info(arg0);
	}

	/**
	 * @see org.apache.log4j.Category#log(org.apache.log4j.Priority, java.lang.Object, java.lang.Throwable)
	 */
	public void trace(Object arg0, Throwable arg1)
	{
		if (logger.isTraceEnabled())
			logger.trace(arg0, arg1);
	}

	/**
	 * @see org.apache.log4j.Category#log(org.apache.log4j.Priority, java.lang.Object)
	 */
	public void trace(Object arg0)
	{
		if (logger.isTraceEnabled())
			logger.trace(arg0);
	}

	/**
	 * @see org.apache.log4j.Category#warn(java.lang.Object, java.lang.Throwable)
	 */
	public void warn(Object arg0, Throwable arg1)
	{
		if (logger.isWarnEnabled())
			logger.warn(arg0, arg1);
	}

	/**
	 * @see org.apache.log4j.Category#warn(java.lang.Object)
	 */
	public void warn(Object arg0)
	{
		if (logger.isWarnEnabled())
			logger.warn(arg0);
	}

	public boolean isDebugEnabled()
	{
		return this.logger.isDebugEnabled();
	}

	public boolean isErrorEnabled()
	{
		return this.logger.isErrorEnabled();
	}

	public boolean isFatalEnabled()
	{
		return this.logger.isFatalEnabled();
	}

	public boolean isInfoEnabled()
	{
		return this.logger.isInfoEnabled();
	}

	public boolean isTraceEnabled()
	{
		return this.logger.isTraceEnabled();
	}
	
	public boolean isWarnEnabled()
	{
		return this.logger.isWarnEnabled();
	}

}
