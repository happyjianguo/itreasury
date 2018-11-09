package com.iss.itreasury.system.logger.dataentity;

import java.util.Iterator;
import java.util.Vector;

public class LoggerResults {

	private Vector			vResult		= new  Vector();
	
	/**
	 * ˽�о�̬���󣬼���ʱ������ʼ��
	 */
	private static LoggerResults instance = null;
	
	/**
	 * ˽�й��췽���������ⲿ����ʵ��
	 *
	 */
	private LoggerResults()
	{
		
	}
	
	/**
	 * ��̬�������������ش����Ψһʵ��
	 * ������ʵ��û�г�ʼ����ʱ�򣬲ų�ʼ��
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
