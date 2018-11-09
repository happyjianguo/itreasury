package com.iss.itreasury.fcinterface.bankportal.util;

import java.util.Enumeration;
import java.util.Hashtable;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.IllegalParameterException;

/**
 * 对象缓存容器<br>
 * 按照名值对（CacheObjectKey key, Object object）缓存对象<br>
 * 其中CacheObjectKey是唯一标识缓存对象的关键字
 * @author mxzhou
 */
public class CacheContainer
{
	private static final int DEFAULT_CACHE_SIZE = 10;

	private static final int MAX_CACHE_SIZE = 1000;

	private Hashtable container = null;

	/**
	 * Constructor for CacheContainer.
	 */
	public CacheContainer()
	{
		this.container = new Hashtable(DEFAULT_CACHE_SIZE);
	}

	public CacheContainer(int cacheSize)
	{
		if (cacheSize <= 0)
		{
			cacheSize = DEFAULT_CACHE_SIZE;
		}
		if (cacheSize > MAX_CACHE_SIZE)
		{
			cacheSize = MAX_CACHE_SIZE;
		}

		this.container = new Hashtable(cacheSize);
	}

	public void addCacheObject(CacheObjectKey key, Object object) throws IllegalParameterException
	{
		if (key == null)
			throw new IllegalParameterException("key is null.");

		if (object == null)
			throw new IllegalParameterException("cache object is null.");

		if (this.containsKey(key))
			throw new IllegalParameterException("key is already used.");

		this.container.put(key, object);
	}

	public Object getCacheObject(CacheObjectKey key)
	{
		Object result = null;

		Enumeration keys = this.container.keys();

		if (keys != null)
		{
			Object keyTemp = null;

			while (keys.hasMoreElements())
			{
				keyTemp = keys.nextElement();

				if (key.equals(keyTemp))
				{
					result = this.container.get(keyTemp);

					break;
				}
			}
		}

		return result;
	}

	public Object removeCacheObject(CacheObjectKey key) throws IllegalParameterException
	{
		return this.container.remove(key);
	}

	public boolean containsKey(CacheObjectKey key) throws IllegalParameterException
	{
		boolean result = false;

		Enumeration keys = this.container.keys();

		if (keys != null)
		{
			Object keyTemp = null;

			while (keys.hasMoreElements())
			{
				keyTemp = keys.nextElement();

				if (key.equals(keyTemp))
				{
					result = true;

					break;
				}
			}
		}

		return result;
	}

	public boolean isEmpty()
	{
		return this.container.isEmpty();
	}

	public void clear()
	{
		this.container.clear();
	}

}
