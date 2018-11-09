/*
 * EJBObject.java
 *
 * Created on 2001年12月3日, 上午11:36
 */

package com.iss.itreasury.util;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

/**
 * 
 * @author yiwang
 * @version
 */
public class EJBObject extends java.lang.Object
{
    /** Creates new CommonObject */
    public EJBObject()
    {
    }

    public static EJBHome getEJBHome(String strHome) throws Exception
    {

        return lookupEjbReference(strHome);
    }

    public static EJBHome lookupEjbReference(String reference)
    {

        InitialContext initContext = null;
        EJBHome home = null;
        try
        {
            initContext = new javax.naming.InitialContext();
        }
        catch (Exception e)
        {
            Log.print("Exception creating InitialContext: " + e.toString());
        }
        try
        {
                reference = reference.replaceAll("REF_","");
                home = (EJBHome) initContext.lookup("java:comp/env/" + reference);
        }
        catch (Exception e)
        {
            Log.print(reference + " home not found - " + "maybe without the reference declaration " + e.toString());
        }
        return home;
    }

    public static EJBHome lookupEjbJNDI(String jndiName, Class classNarrowTo)
    {
        InitialContext initContext = null;
        EJBHome home = null;
        try
        {
            initContext = new javax.naming.InitialContext();
        }
        catch (Exception e)  
        {
            Log.print("Exception creating InitialContext: " + e.toString());
        }
        try
        {
            home = (EJBHome) PortableRemoteObject.narrow(initContext.lookup(jndiName), classNarrowTo);
        }
        catch (Exception e)
        {

        }
        return home;

    }
    /**
     * 远程EJB接口调用
     * add by zhouxiang 2011
     * @param jndiName
     * @param classNarrowTo
     * @return
     */
    public static EJBHome getRemoteEjbHome(String jndiName, Class classNarrowTo, String contextURL, String contextPort)
    {
    	Object obj = null;
    	EJBHome home = null;
    	InitialContext initContext;
    	Properties p = new Properties();
    	p.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");
    	p.put(Context.PROVIDER_URL,"iiop://"+contextURL+":"+contextPort+"/");
    	try
    	{
    	    initContext = new InitialContext(p);
    	    obj = initContext.lookup(jndiName);
    	}
    	catch (Exception e)  
    	{
    		Log.print("Exception creating InitialContext: " + e.toString());
    	}
    	try
    	{
    		home = (EJBHome) PortableRemoteObject.narrow(obj, classNarrowTo);
    	}
    	catch (Exception e)
    	{
    		Log.print(jndiName + " home not found - " + "maybe without the reference declaration " + e.toString());
    	}
    	return home;
    	
    }
}