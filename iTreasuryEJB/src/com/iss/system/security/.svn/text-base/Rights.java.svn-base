/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   Rights.java

package com.iss.system.security;

import com.iss.system.util.ResourceBoundleUtil;
import java.util.Hashtable;
import java.util.Properties;

public final class Rights
{

    public Rights()
    {
    }

    public static final long[] getRightsValue(String strConfig, String strKey[])
    {
        String strRight = null;
        long lRights[] = new long[strKey.length];
        for(int intIndex = 0; intIndex < strKey.length; intIndex++)
        {
            strRight = getMessage(strConfig, strKey[intIndex]);
            lRights[intIndex] = Long.parseLong(strRight);
        }

        return lRights;
    }

    public static final String[] getRightsName(String strConfig, String strKey[])
    {
        String strRights[] = new String[strKey.length];
        for(int intIndex = 0; intIndex < strKey.length; intIndex++)
            strRights[intIndex] = getMessage(strConfig, strKey[intIndex]);

        return strRights;
    }

    public static final long getRightValue(String strConfig, String strKey)
    {
        String strRight = getMessage(strConfig, strKey);
        return Long.parseLong(strRight);
    }

    public static final String getRightName(String strConfig, String strKey)
    {
        return getMessage(strConfig, strKey);
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro_zh。
     * @param strKey 读取资源的键值。
     * @return String
     */
    public static String getMessage(String strConfig, String strKey)
    {
        return ResourceBoundleUtil.getMessage(strConfig, strKey);
    }

    public static final long getSysAdminRight(String strConfig)
    {
        String strKey = strConfig + "com.iss.system.rights.sysadmin.value";
        long lRight;
        if(m_propAdminRights.containsKey(strKey))
        {
            lRight = ((Long)m_propAdminRights.get(strKey)).longValue();
        } else
        {
            lRight = getRightValue(strConfig, "com.iss.system.rights.sysadmin.value");
            m_propAdminRights.put(strKey, new Long(lRight));
        }
        return lRight;
    }

    public static final String getSysAdminRightName(String strConfig)
    {
        String strKey = strConfig + "com.iss.system.rights.sysadmin.value";
        String strRight = null;
        if(m_propAdminRights.containsKey(strKey))
        {
            strRight = (String)m_propAdminRights.get(strKey);
        } else
        {
            strRight = getRightName(strConfig, "com.iss.system.rights.sysadmin.name");
            m_propAdminRights.put(strKey, strRight);
        }
        return strRight;
    }

    public static final String SYSADMIN_RIGHTS_KEY = "com.iss.system.rights.sysadmin.value";
    public static final String SYSADMIN_RIGHTS_NAME_KEY = "com.iss.system.rights.sysadmin.name";
    private static final Properties m_propAdminRights = new Properties();

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/