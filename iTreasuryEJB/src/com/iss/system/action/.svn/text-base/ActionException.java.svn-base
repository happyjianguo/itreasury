/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ActionException.java

package com.iss.system.action;

import java.util.Enumeration;
import java.util.Properties;
/**
 * 这个类可以一次返回多条异常信息，异常信息本身不存储在这个类中，这个类只存储异常信息的资源KEY值及
 * 其参数。
 * 很多情况下，当系统运行过程中出现异常状态时不直接返回，还要继续运行，直接出现严重错误为止，当系统
 * 最后抛出异常时又需要知道这个过程中的全部异常，这种情况下这个Exception类就是适合的选择。<br>
 * 另外，用于需要多语言的支持，请不要将异常信息直接Hard Code到程序中，最好是在程序中只引用一个异常
 * 错误信息的标识，这个错误信息存储在外部的资源文件中。
 * @author pliu
 */
public class ActionException extends Exception
{
    public static String SYSTEM_MESSAGE = "com.iss.system.iSSMessage";
    public static String SYSTEM_DEFAULT_CONFIG = "compatible with appache struts";
    /**
     * 存储所有信息键值及参数，参数类型为Object[]，注意可能为null.
     */
    private Properties m_propMessages = new Properties();
    /**
     * ActionException默认构造方法。
     * @see java.lang.Object#Object()
     */
    public ActionException()
    {
        super();
    }

    public void AddException(ActionException actionException)
    {
        String strConfig = null;
        String strKey = null;
        Object objArgs[] = (Object[])null;
        Properties propMessage = null;
        Enumeration enuKey = null;
        for(Enumeration enuConfig = actionException.getMessageConfigs(); enuConfig.hasMoreElements();)
        {
            strConfig = (String)enuConfig.nextElement();
            propMessage = actionException.getConfigActionException(strConfig);
            for(enuKey = propMessage.keys(); enuKey.hasMoreElements(); AddException(strConfig, strKey, objArgs))
            {
                strKey = (String)enuKey.nextElement();
                objArgs = (Object[])propMessage.get(strKey);
            }

        }

    }

    public boolean isEmpty()
    {
        return m_propMessages.isEmpty();
    }
    /**
     * 构造一个没有参数的错误信息ActionException。
     * @param messageKey 错误信息资源键值。
     * @see java.lang.Throwable#Throwable(String)
     */ 
    public ActionException(String strConfig, String messageKey)
    {
        super(messageKey);
        m_propMessages = new Properties();
        AddException(strConfig, messageKey, new Object[] {
            ""
        });
    }

    public ActionException(String strConfig, String messageKey, Object objArg)
    {
        super(messageKey);
        m_propMessages = new Properties();
        AddException(strConfig, messageKey, new Object[] {
            objArg
        });
    }

    public ActionException(String strConfig, String messageKey, Object objArg0, Object objArg1)
    {
        super(messageKey);
        m_propMessages = new Properties();
        AddException(strConfig, messageKey, new Object[] {
            objArg0, objArg1
        });
    }

    public ActionException(String strConfig, String messageKey, Object objArg0, Object objArg1, Object objArg2)
    {
        super(messageKey);
        m_propMessages = new Properties();
        AddException(strConfig, messageKey, new Object[] {
            objArg0, objArg1, objArg2
        });
    }
    /**
     * 构造一个有一个以数组为参数的错误信息ActionException，这个构造方法可以支持多于3个参数。
     * @param messageKey
     * @param objArgs
     */
    public ActionException(String strConfig, String messageKey, Object objArgs[])
    {
        super(messageKey);
        m_propMessages = new Properties();
        AddException(strConfig, messageKey, objArgs);
    }

    public void AddException(String strConfig, String messageKey)
    {
        AddException(strConfig, messageKey, new Object[] {
            ""
        });
    }

    public void AddException(String strConfig, String messageKey, Object objArg)
    {
        AddException(strConfig, messageKey, new Object[] {
            objArg
        });
    }

    public void AddException(String strConfig, String messageKey, Object objArg0, Object objArg1)
    {
        AddException(strConfig, messageKey, new Object[] {
            objArg0, objArg1
        });
    }

    public void AddException(String strConfig, String messageKey, Object objArg0, Object objArg1, Object objArg2)
    {
        AddException(strConfig, messageKey, new Object[] {
            objArg0, objArg1, objArg2
        });
    }

    public void AddException(String strConfig, String messageKey, Object objArgs[])
    {
        if(strConfig == null)
            strConfig = SYSTEM_DEFAULT_CONFIG;
        Properties propMessage = null;
        if(m_propMessages.containsKey(strConfig))
        {
            propMessage = (Properties)m_propMessages.get(strConfig);
        } else
        {
            propMessage = new Properties();
            m_propMessages.put(strConfig, propMessage);
        }
        propMessage.put(messageKey, ((Object) (objArgs)));
    }

    public void AddException(String strConfig, ActionException actionException)
    {
        if(strConfig == null)
            strConfig = SYSTEM_DEFAULT_CONFIG;
        Properties propMessage = actionException.getConfigActionException(strConfig);
        if(propMessage != null)
        {
            String strKey = null;
            Object objArgs[] = (Object[])null;
            for(Enumeration enuKey = propMessage.keys(); enuKey.hasMoreElements(); AddException(strConfig, strKey, objArgs))
            {
                strKey = (String)enuKey.nextElement();
                objArgs = (Object[])propMessage.get(strKey);
            }

        }
    }

    public Enumeration getMessageConfigs()
    {
        return m_propMessages.keys();
    }

    public Enumeration getMessageKeys(String strConfig)
    {
        if(strConfig == null)
            strConfig = SYSTEM_DEFAULT_CONFIG;
        if(m_propMessages.containsKey(strConfig))
        {
            Properties propMessage = null;
            propMessage = (Properties)m_propMessages.get(strConfig);
            return propMessage.keys();
        } else
        {
            return null;
        }
    }

    public Object[] getMessageArg(String strConfig, String messageKey)
    {
        if(strConfig == null)
            strConfig = SYSTEM_DEFAULT_CONFIG;
        Object objArgs[] = (Object[])null;
        if(m_propMessages.containsKey(strConfig))
        {
            Properties propMessage = null;
            propMessage = (Properties)m_propMessages.get(strConfig);
            objArgs = (Object[])propMessage.get(messageKey);
        }
        return objArgs;
    }

    public Properties getConfigActionException(String strConfig)
    {
        if(strConfig == null)
            strConfig = SYSTEM_DEFAULT_CONFIG;
        if(m_propMessages.containsKey(strConfig))
            return (Properties)m_propMessages.get(strConfig);
        else
            return null;
    }

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/