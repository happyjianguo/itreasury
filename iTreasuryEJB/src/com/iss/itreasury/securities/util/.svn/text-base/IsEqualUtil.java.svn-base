/*
 * Created on 2004-5-18
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class IsEqualUtil 
{
	public static boolean isEqual(Object obj1,Object obj2)
	{
		boolean returnValue=false;
		
		Class cla=obj1.getClass() ;
		if(cla.equals( Long.class))
			if(((Long)obj1).longValue()==((Long)obj2).longValue())
				returnValue=true;
		if(cla.equals( Double.class))
			if(((Double)obj1).doubleValue() ==((Double)obj2).doubleValue())
				returnValue=true;
			
		if(cla.equals( String.class))
			if(((String)obj1).equals((String)obj2))
				returnValue=true;
			
		if(cla.equals( Timestamp.class))
			if(((Timestamp)obj1).equals(((Timestamp)obj2)))
				returnValue=true;

		return returnValue;
	}
	
	public static boolean isMatch(String[]args,Object obj1,Object obj2) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		boolean returnValue=false;
		if(args!=null&&args.length >0)
		{
			for(int i=0;i<args.length ;i++)
			{
				Class cla1=obj1.getClass() ;
				Class cla2=obj2.getClass() ;
				try
				{
					Method meth1=cla1.getMethod( args[i],new Class[]{});
					Method meth2=cla2.getMethod( args[i],new Class[]{});
					
					returnValue=isEqual(meth1.invoke(obj1,new Object[]{}),meth2.invoke( obj2,new Object[]{}));
					if(returnValue==false)
						return returnValue;
				}
				catch(NoSuchMethodException ex)
				{
					
				}
				catch(SecurityException ex1)
				{
					
				}
				
				
			}
		}
		
		return returnValue;
	}
	
	public static ArrayList deliver(String[] names,Object[] objs) throws Exception
	{
		ArrayList coll=new ArrayList();
		ArrayList temp=null;
		Object tempObj=new Object();
		
		if(names==null||objs==null)
			return null;
		
		if(objs.length >0)
		{   
			tempObj=objs[0];
			
			for(int i=0;i<objs.length ;i++)
			{
				if(isMatch(names,tempObj,objs[i]))
				{
					if (temp == null)
					{
						temp = new ArrayList();
					}
					temp.add( objs[i]);
					tempObj=objs[i];
					
					
				}
				else
				{
					if (temp != null)
					{
						coll.add( temp);
					}
					
					temp = new ArrayList();
					
					temp.add( objs[i]);
					tempObj=objs[i];
				}
				
				
				
			}
			coll.add( temp);
			
		}
		
		
		
		
		return coll;
	}
}
