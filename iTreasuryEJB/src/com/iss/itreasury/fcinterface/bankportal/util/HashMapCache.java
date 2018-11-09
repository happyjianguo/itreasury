/**
 * Created on 2007-1-22
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.util.HashMap;

/**
 * @author chengli 系统基础信息缓存对象
 */
public class HashMapCache {

	private  HashMap hmCache = new HashMap();
	
	public void remove(String key)
	{
		hmCache.remove(key);
	}
	
	public void put(String key ,Object object)
	{
		if(Env.getEnvConfigItem(Env.isUseCache).trim().equalsIgnoreCase("true"))
		{
			hmCache.put(key,object);
		}
	}
	
	public Object get(String key)
	{
		return hmCache.get(key);
	}
}
