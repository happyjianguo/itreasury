/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ISSAppConfig.java

package com.iss.system;

import com.iss.system.action.ActionException;
import com.iss.system.dataentity.LicenceBean;
import com.iss.system.security.Licence;
import com.iss.system.security.SinatureUtil;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
/**
 * @author pliu
 * <iss-app>
 * <!--安全访问控制设置,主要针对一次提交的ACTION，一个ACTION可能需要多个权限。-->
 * <security-settings>
 * <!--安全访问设置，可以有多个。-->
 * <security-setting>
 * 		<name></name>
 * 		<resource-path></resource-path>
 * </security-setting>
 * </security-settings>
 * 
 * <class-factories>
 * <use-jndi></use-jndi>
 * <class-factory>
 * 		<name></name>
 * 		<resource-path></resource-path>
 * </class-factory>
 * </class-factories>
 * </iss-app>
 */
public class ISSAppConfig
{
    public ISSAppConfig()
    {
    }

    public static final LicenceBean[] getLicence(String strSystem, String strStoragePath, String strLicenceFileName)
    {
        if(m_propLicence.containsKey(strSystem))
            return (LicenceBean[])m_propLicence.get(strSystem);
        LicenceBean licences[] = (LicenceBean[])null;
        try
        {
            licences = Licence.verifySinature(strStoragePath + strLicenceFileName, SinatureUtil.LICENCEINFO_FILE);
            addLicence(strSystem, licences);
        }
        catch(ActionException e)
        {
            e.printStackTrace();
        }
        return licences;
    }

    public static final void addLicence(String strSystem, LicenceBean licences[])
    {
        if(licences != null)
            m_propLicence.put(strSystem, licences);
    }

    public static final void removeLicence(String strSystem)
    {
        m_propLicence.remove(strSystem);
    }

    /**
     * 
     */
    public static void load()
    {
        try
        {
            //取当前线程的CLASS LOADER。        	
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader == null)
                classLoader = java.lang.Class.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("iss-app-config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDocument = db.parse(is);
            is.close();
            //parse security settings            
            NodeList nl = null;
            Node node = null;
            String strName = null;
            String strValue = null;
            nl = xmlDocument.getElementsByTagName("security-setting");
            for(int i = 0; i < nl.getLength(); i++)
            {
                node = nl.item(i);
                for(node = node.getFirstChild(); node != null; node = node.getNextSibling())
                {
                    strName = node.getNodeName();
                    strValue = "";
                    for(Node nodeVal = node.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
                        strValue = strValue + nodeVal.getNodeValue();

                    if(!node.getNodeName().equals("name") && node.getNodeName().equals("resource-path"))
                    {
                        System.out.println("resource-path=" + strValue);
                        SecuritySettingConfig.load(strValue);
                    }
                }

            }

            String strUseJNDI = null;
            nl = xmlDocument.getElementsByTagName("use-jndi");
            node = nl.item(0);
            //test to see if class factory is to use JNDI            
            strUseJNDI = "";
            for(Node nodeVal = node.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
                strUseJNDI = strUseJNDI + nodeVal.getNodeValue();

            if(strUseJNDI.equalsIgnoreCase("true"))
            {
                System.out.println("No need to load class factory!");
                return;
            }
            // parse class factories            
            nl = xmlDocument.getElementsByTagName("class-factory");
            for(int i = 0; i < nl.getLength(); i++)
            {
                node = nl.item(i);
                for(node = node.getFirstChild(); node != null; node = node.getNextSibling())
                {
                    strName = node.getNodeName();
                    strValue = "";
                    for(Node nodeVal = node.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
                        strValue = strValue + nodeVal.getNodeValue();

                    if(!node.getNodeName().equals("name") && node.getNodeName().equals("resource-path"))
                        BaseObjectFactory.loadClassFactories(strValue);
                }

            }

        }
        catch(Exception e)
        {
            System.out.println("ISS APLLICATION CONFIGURATION FAILLED TO LOAD! PLEASE REFER FOLLOWING MESSAGE:\n");
            e.printStackTrace();
        }
        finally
        {
            System.out.println("ISS APLLICATION CONFIGURATION SUCCESSFULLY LOADED!\n");
        }
    }    
    private static final String ISS_APP_CONFIG = "iss-app-config.xml";
    public static final String SESSION_LOGON_USER_KEY = "__SESSION_LOGON_USER_KEY__";
    private static final Properties m_propLicence = new Properties();

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 32 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/