package com.iss.itreasury.fcinterface.bankportal.util;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.IllegalParameterException;

/**
 * DAO缓存键值<br>
 * 继承CacheObjectKey，提供对DAO实例对象的缓存标识
 * @author mxzhou
 */
public class CacheDAOImplKey implements CacheObjectKey
{
	private String className = null;
	/**
	 * Constructor for CacheServiceFactoryKey.
	 */
	public CacheDAOImplKey(String className) throws IllegalParameterException
	{
		if (className == null || "".equals(className.trim()))
		{
			throw new IllegalParameterException();
		}

		this.className = className;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;

		if (!(obj instanceof CacheDAOImplKey))
			return false;

		CacheDAOImplKey key = (CacheDAOImplKey) obj;

		if (this.className.equals(key.className))
			return true;
		else 
			return false;
	}

}
