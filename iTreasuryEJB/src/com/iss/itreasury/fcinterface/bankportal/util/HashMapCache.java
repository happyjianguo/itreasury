/**
 * Created on 2007-1-22
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.util.HashMap;

/**
 * @author chengli ϵͳ������Ϣ�������
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
