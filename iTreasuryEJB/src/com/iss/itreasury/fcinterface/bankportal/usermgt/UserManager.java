/*
 * Created on 2005-6-8
 */
package com.iss.itreasury.fcinterface.bankportal.usermgt;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.Env;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

/**
 * 用户管理类<br>
 * 提供用户实现类的动态加载
 * @author mxzhou
 */
public class UserManager
{
	/**日志对象*/
	private static Logger log = new Logger(DAOFactory.class);
	
	/**
	 * 获取用户类的具体实现<br>
	 * 具体实现类通过Env中的配置动态加载
	 * @return
	 * @throws SystemException
	 */
	public static User getUserImpl() throws SystemException
	{
		String userImplClassName = Env.getEnvConfigItem(Env.INTERFACE_USERIMPL);
		if(userImplClassName == null || userImplClassName.trim().length() <= 0)
		{
			throw new SystemException("尚未配置用户接口具体实现类，请检查系统环境配置！");
		}
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class userImplClass = null;
		User userImpl = null;

		try
		{
			userImplClass = classLoader.loadClass(userImplClassName);

			//加载指定的数据库实现类	
			userImpl = (User)userImplClass.newInstance();
		}
		catch (Exception e)
		{
			log.info("Cannot load user implement class by ClassLoader, try to get class by Class.forName().");
			try
			{
				userImplClass = Class.forName(userImplClassName);
				userImpl = (User)userImplClass.newInstance();
				if(userImpl == null)
				{
					throw new Exception("the user implement is null.");
				}
			}
			catch (Exception e1)
			{
				log.info("Cannot load user implement class ["+userImplClassName+"] by parameter.");
				throw new SystemException("Cannot load user implement class ["+userImplClassName+"] by parameter.", e);
			}
		}
		
		return userImpl;
	}
}
