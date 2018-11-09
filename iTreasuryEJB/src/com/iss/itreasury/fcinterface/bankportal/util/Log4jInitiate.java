package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Log4jInitiate
{
	private static Log4jInitiate m_instance = new Log4jInitiate();
	
	private static Properties props = null;

	private Log4jInitiate()
	{
		super();
	}

	/**
	 * 初始化logger
	 */
	public static void initialize()
	{
		getLogConfig();

		// 配置logger
		if (props == null)
		{
			//按照默认进行配置
			BasicConfigurator.configure();
		}
		else
		{
			try
			{
				// 按照属性文件进行配置
				PropertyConfigurator.configure(props);
			}
			catch (Exception e)
			{
				System.out.println("config log4j by default.");
				// 按照默认进行配置
				BasicConfigurator.configure();
			}
		}
	}
	public static Properties getLogConfig()
	{
		if(props == null)
		{
			InputStream in =
				getResourceAsStream(
					Env.getEnvConfigItem(Env.LOG4J_CONFIGFILE_NAME));
					
			if (in != null)
			{
				try
				{
					props = new Properties();
					props.load(in);
					in.close();
				}
				catch (java.io.IOException e)
				{
					// ignored
				}
			}
		}
		return props;
	}

	private static InputStream getResourceAsStream(final String name)
	{
		return (
			InputStream) AccessController.doPrivileged(new PrivilegedAction()
		{
			public Object run()
			{
				ClassLoader threadCL = getContextClassLoader();

				if (threadCL != null)
				{
					return threadCL.getResourceAsStream(name);
				}
				else
				{
					return ClassLoader.getSystemResourceAsStream(name);
				}
			}
		});
	}

	/**
	 * Return the thread context class loader if available.
	 * Otherwise return null.
	 * 
	 * The thread context class loader is available for JDK 1.2
	 * or later, if certain security conditions are met.
	 *
	 * @exception LogConfigurationException if a suitable class loader
	 * cannot be identified.
	 */
	private static ClassLoader getContextClassLoader()
	{
		ClassLoader classLoader = null;

		try
		{
			// Are we running on a JDK 1.2 or later system?
			Method method =
				Thread.class.getMethod("getContextClassLoader", new Class[]{});

			// Get the thread context class loader (if there is one)
			try
			{
				classLoader =
					(ClassLoader) method.invoke(Thread.currentThread(), new Object[]{});
			}
			catch (IllegalAccessException e)
			{
				; // ignore
			}
			catch (InvocationTargetException e)
			{
				/**
				 * InvocationTargetException is thrown by 'invoke' when
				 * the method being invoked (getContextClassLoader) throws
				 * an exception.
				 * 
				 * getContextClassLoader() throws SecurityException when
				 * the context class loader isn't an ancestor of the
				 * calling class's class loader, or if security
				 * permissions are restricted.
				 * 
				 * In the first case (not related), we want to ignore and
				 * keep going.  We cannot help but also ignore the second
				 * with the logic below, but other calls elsewhere (to
				 * obtain a class loader) will trigger this exception where
				 * we can make a distinction.
				 */
				if (e.getTargetException() instanceof SecurityException)
				{
					; // ignore
				}
				else
				{
					// Capture 'e.getTargetException()' exception for details
					// alternate: log 'e.getTargetException()', and pass back 'e'.
					//throw new LogConfigurationException("Unexpected InvocationTargetException", e.getTargetException());
				}
			}
		}
		catch (NoSuchMethodException e)
		{
			// Assume we are running on JDK 1.1
			; // ignore
		}

		if (classLoader == null)
		{
			classLoader = Log4jInitiate.class.getClassLoader();
		}

		// Return the selected class loader
		return classLoader;
	}
}
