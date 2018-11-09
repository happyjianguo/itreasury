package com.iss.itreasury.system.logger.dataentity;

import java.util.Iterator;
import java.util.Vector;

public class LoggerResults {

	private Vector			vResult		= new  Vector();
	
	/**
	 * 私有静态对象，加载时候不做初始化
	 */
	private static LoggerResults instance = null;
	
	/**
	 * 私有构造方法，避免外部创建实例
	 *
	 */
	private LoggerResults()
	{
		
	}
	
	/**
	 * 静态工厂方法，返回此类的唯一实例
	 * 当发现实例没有初始化的时候，才初始化
	 * @return
	 */
	synchronized public static LoggerResults getInstance()
	{
		if( instance == null)
		{
			instance = new LoggerResults();
		}
		return instance;		
	}

	public Vector getResult() {
		return vResult;
	}

	public void setResult(Vector result) {
		vResult = result;
	}
	
	public static void main(String[] args)
	{
		Vector v = new Vector();
		v.add("a");
		v.add("b");
		v.add("c");
		if(v != null && v.size() > 0)
		{
			Iterator it = v.iterator();
			while(v.size() > 0)
			{
				 System.out.println(v.get(0)); 
				 v.remove(0);
			}

		}
	}

	
}
