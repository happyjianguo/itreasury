/*
 * Created on 2005-6-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;

import com.iss.itreasury.configtool.constantmanage.bizlogic.ConstantManager;

/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractConstantManager { 
	 
	private static AbstractConstantManager instance=null;
	abstract public long[] getFieldsByClassName(String name,long officeID,long currencyID);
	private final static String m_manage_class="com.iss.itreasury.configtool.constantmanage.bizlogic.ConstantManager";
	
	public long[] getAllFields(String name)
	{
		long[] fields = null;

		try {
			Class cc = Class.forName(name);
			Field field[] = cc.getDeclaredFields();
			fields = new long[field.length];
			
			for (int i = 0; i < field.length; i++) {
				fields[i] = field[i].getLong(cc);
			}
			
		} catch (Exception e) {
			Log.print(e.toString());
		}

		return fields;
	}

	public static AbstractConstantManager createConstantManager()
	{
		try {
			if (instance==null)
				instance=(AbstractConstantManager)Class.forName( m_manage_class ).newInstance() ;
		} catch (Exception e) {
			instance=new DefaultConstantManager();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
}
