/*
 * Created on 2005-6-8
 */
package com.iss.itreasury.fcinterface.bankportal.usermgt;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.Env;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

/**
 * �û�������<br>
 * �ṩ�û�ʵ����Ķ�̬����
 * @author mxzhou
 */
public class UserManager
{
	/**��־����*/
	private static Logger log = new Logger(DAOFactory.class);
	
	/**
	 * ��ȡ�û���ľ���ʵ��<br>
	 * ����ʵ����ͨ��Env�е����ö�̬����
	 * @return
	 * @throws SystemException
	 */
	public static User getUserImpl() throws SystemException
	{
		String userImplClassName = Env.getEnvConfigItem(Env.INTERFACE_USERIMPL);
		if(userImplClassName == null || userImplClassName.trim().length() <= 0)
		{
			throw new SystemException("��δ�����û��ӿھ���ʵ���࣬����ϵͳ�������ã�");
		}
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class userImplClass = null;
		User userImpl = null;

		try
		{
			userImplClass = classLoader.loadClass(userImplClassName);

			//����ָ�������ݿ�ʵ����	
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
