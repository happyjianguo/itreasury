package com.iss.system;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * @author pliu
 * <p>逻辑对象实例化工厂类。</p>
 * <p>因为系统将设计为即支持SessionBean的发布，又支持普通类分布的方式，所以逻辑对象设计要求服从
 * SessionBean的对象设计。不可以在应用 系 统 中实例化对象，只能通过这个工厂类创建一个逻辑对象。</p>
 * <p>当系统发布为SessionBean时，该工厂类构造JNDI资源中的对象用于支持大型系统；当系统发布为普通
 * Class时该工厂类返回 单实例化的对象，用于支持小型系统。</p>
 * <p>系统类工厂配置文件结构说明：</p>
 * <ISS-class-factories>
 * <use-jndi></use-jndi>
 * <class-factory>
 * 		<name></name>
 * 		<class-factory-path></class-factory-path>
 * </class-factory>
 * </ISS-class-factories>
 * 
 * <p>应用类工厂配置文件。文档结构说明：</p>
 * <class-factory>
 * <class-map>
 * 		<name></name>
 * 		<full-name></full-name>
 * </class-map>
 * <class-path-map>
 * 		<source-path></source-path>
 * 		<destination-path></destination-path>
 * </class-path-map>
 * </class-factory>
 */
public final class BaseObjectFactory
{
    /**
     * 类、对应变换表。当系统支持小型系统时，通过这个HASH表，根据请求对象名创建对象实例。
     */
    private static Hashtable m_htClasses = null;
    private static int m_nCount = 0;
    private static boolean m_bDebug = false;
    private static final String NOT_CACHED_CLASS = "NOT-CACHED-CLASS";
    public BaseObjectFactory()
    {
    } 
    /**
     * 逻辑对象实现化方法，返回对象可能是SessionBean也可能是精通的BaseObject派生的类对象。无论是哪一种，
     * 这个方法返回的对象都是无状态的。
     * @param strClassName 请求创建的对象名，用点号分融路径，如：com.iss.iam.dao.Asset
     * @return Object 返回null，如果失败。
     */
    public static Object getBaseObject(String strClassName)
    {
        if(m_htClasses == null)
            synchronized(com.iss.system.BaseObjectFactory.class)
            {
                ISSAppConfig.load();
            }
        if(m_bDebug)
            synchronized(com.iss.system.BaseObjectFactory.class)
            {
                m_nCount++;
                System.out.println(MessageFormat.format("{0} getBaseObject:{1}", new Object[] {
                    new Integer(m_nCount), strClassName
                }));
            }
        Object objMapped = m_htClasses.get(strClassName);
        if((objMapped instanceof String) && ((String)objMapped).startsWith("NOT-CACHED-CLASS"))
            try
            {
                strClassName = ((String)objMapped).substring("NOT-CACHED-CLASS".length());
                Class cls = Class.forName(strClassName);
                objMapped = cls.newInstance();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        return objMapped;
    }
    /**
     * 释放由getBaseObject创建的对象，必须和getBaseObject成对使用。
     * @param obj
     */
    public static void releaseBaseObject(Object obj)
    {
        if(obj != null && m_bDebug)
            synchronized(com.iss.system.BaseObjectFactory.class)
            {
                m_nCount--;
                System.out.println(MessageFormat.format("{0} releaseBaseObject:{1}", new Object[] {
                    new Integer(m_nCount), obj.getClass().getName()
                }));
            }
    }
    /**
     * 初始化类工厂,从ISS-class-factories.xml文件中读取所有类工厂配置信息。ISS-class-factories.xml
     * 文档结构为
     * <ISS-class-factories>
     * <use-jndi></use-jndi>
     * <class-factory>
     * 		<name></name>
     * 		<resource-path></resource-path>
     * </class-factory>
     * </ISS-class-factories>
     * 
     * <p>应用类工厂配置文件。文档结构说明：</p>
     * <class-factory>
     * <class-map>
     * 		<name></name>
     * 		<full-name></full-name>
     * </class-map>
     * <class-path-map>
     * 		<source-path></source-path>
     * 		<destination-path></destination-path>
     * </class-path-map>
     * <class-factory>
     */
    public static void loadClassFactories(String strResourcePath)
    {
        try
        {
//        	取当前线程的classloader
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader == null)
                classLoader = java.lang.Class.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream(strResourcePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDocument = db.parse(is);
            is.close();
            if(m_htClasses == null)
                m_htClasses = new Hashtable(1024);
            NodeList nlClassMap = xmlDocument.getElementsByTagName("class-map");
            Node nodeClass = null;
            String strName = null;
            String strValue = null;
            String strClassName = null;
            Class cls = null;
            Object objCached = null;
            for(int j = 0; j < nlClassMap.getLength(); j++)
            {
                nodeClass = nlClassMap.item(j);
                for(nodeClass = nodeClass.getFirstChild(); nodeClass != null; nodeClass = nodeClass.getNextSibling())
                {
                    strValue = "";
                    for(Node nodeVal = nodeClass.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
                        strValue = strValue + nodeVal.getNodeValue();

                    if(nodeClass.getNodeName().equals("name"))
                        strName = strValue;
                    else
                    if(nodeClass.getNodeName().equals("full-name"))
                        strClassName = strValue;
                    else
                    if(nodeClass.getNodeName().equals("cached"))
                        if(strValue.equalsIgnoreCase("true"))
                        {
                            cls = Class.forName(strClassName);
                            objCached = cls.newInstance();
                            m_htClasses.put(strName, objCached);
                        } else
                        {
                            m_htClasses.put(strName, "NOT-CACHED-CLASS" + strClassName);
                        }
                }

            }

            System.out.println("\nISS CLASS FACTORY SUCCESSFULLY LOADED!\n");
        }
        catch(Exception e)
        {
            System.out.println("\nISS CLASS FACTORY FAILLED TO LOAD! PLEASE REFER FOLLOWING MESSAGE:\n");
            e.printStackTrace();
        }
    } 
    public static boolean getDebug()
    {
        return m_bDebug;
    }

    public static void setDebug(boolean b)
    {
        m_bDebug = b;
    }
}