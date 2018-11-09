/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.lang.reflect.Constructor;
import java.sql.Connection;

import com.iss.itreasury.fcinterface.bankportal.constant.DatabaseType;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * DAO工厂对象<br>
 * 提供DAO多数据库支持，现象多数据库实现对象的动态加载<br>
 * 当前采用缓存机制，对成功加载的数据库实现对象进行了缓存，便于后继的再次加载
 * @author mxzhou
 */
public class DAOFactory
{
	/**日志对象*/
	private static Logger log = new Logger(DAOFactory.class);
	/**当前数据库的类型*/
	private static String curDBType = null;
	/**数据库实现对象缓存*/
	private static CacheContainer daoImplCache = new CacheContainer(16);
	
	/**
	 * 根据接口获取DAO对应的具体实现对象<br>
	 * 连接作为可选参数，可以为null，此时有DAO实现对象自己进行维护
	 * @param DAOInterface
	 * @param conn
	 * @return
	 * @throws SystemException
	 */
	public static Object getDAOImpl(
			Class DAOInterface,
			Connection conn)
		throws SystemException
	{		
		getCurDBType();
		
		String daoImplClassName = DAOInterface.getName()+"_"+curDBType;

		CacheDAOImplKey key = new CacheDAOImplKey(daoImplClassName);
		Object daoImpl = daoImplCache.getCacheObject(key);
		
		if(daoImpl == null)
		{
			ClassLoader classLoader = DAOFactory.class.getClassLoader();
				//Thread.currentThread().getContextClassLoader();
			Class daoImplClass = null;

			try
			{
				daoImplClass = classLoader.loadClass(daoImplClassName);

				//加载指定的数据库实现类	
				Constructor constructor = daoImplClass.getConstructor(
						new Class[]{Connection.class});
				daoImpl = constructor.newInstance(
						new Object[]{conn});
			}
			catch (Exception e)
			{
				log.info("Cannot load database implement class by ClassLoader, try to get class by Class.forName().");
				try
				{
					daoImplClass = Class.forName(daoImplClassName);
					daoImpl = daoImplClass.newInstance();
					if(daoImpl == null)
					{
						throw new Exception("the database implement is null.");
					}
				}
				catch (Exception e1)
				{
					log.info("Cannot load database implement class ["+daoImplClassName+"] by parameter.");
					throw new SystemException("Cannot load database implement class ["+daoImplClassName+"] by parameter.", e);
				}
			}

			/**由于dao的连接可能是外部维护，如果是通过缓存获得的dao实现对象，可能会对该连接产生影响
			 * 所以当前对dao不做缓存，每次都创建新的实例
			 * Edit by mxzhou
			 */
			//缓存工厂类对象
//			try
//			{
//				daoImplCache.addCacheObject(new CacheDAOImplKey(daoImplClassName), daoImpl);
//			}
//			catch (Exception e)
//			{
//				throw new SystemException("Cannot cache database implement object.", e);
//			}
		}
		return daoImpl;
	}
	public static String getCurDBType() throws SystemException
	{
		if(curDBType == null)
		{
			//校验当前环境配置的数据库类型是否有效
			String dbType = Env.getEnvConfigItem(Env.DB_TYPE);
			long[] dbTypeIDs = DatabaseType.getAllCode();
			int curID = -1;
			for(int i = 0; i < dbTypeIDs.length; i++)
			{
				if(DatabaseType.getName(dbTypeIDs[i]).equalsIgnoreCase(dbType))
				{
					curID = i;
					break;
				}
			}
			if(curID == -1)
			{
				throw new SystemException("当前系统暂不支持" + dbType +"数据库");
			}
			curDBType = DatabaseType.getName(dbTypeIDs[curID]);
		}
		return curDBType;
	}
}
