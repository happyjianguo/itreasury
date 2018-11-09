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
 * <p>�߼�����ʵ���������ࡣ</p>
 * <p>��Ϊϵͳ�����Ϊ��֧��SessionBean�ķ�������֧����ͨ��ֲ��ķ�ʽ�������߼��������Ҫ�����
 * SessionBean�Ķ�����ơ���������Ӧ�� ϵ ͳ ��ʵ��������ֻ��ͨ����������ഴ��һ���߼�����</p>
 * <p>��ϵͳ����ΪSessionBeanʱ���ù����๹��JNDI��Դ�еĶ�������֧�ִ���ϵͳ����ϵͳ����Ϊ��ͨ
 * Classʱ�ù����෵�� ��ʵ�����Ķ�������֧��С��ϵͳ��</p>
 * <p>ϵͳ�๤�������ļ��ṹ˵����</p>
 * <ISS-class-factories>
 * <use-jndi></use-jndi>
 * <class-factory>
 * 		<name></name>
 * 		<class-factory-path></class-factory-path>
 * </class-factory>
 * </ISS-class-factories>
 * 
 * <p>Ӧ���๤�������ļ����ĵ��ṹ˵����</p>
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
     * �ࡢ��Ӧ�任����ϵͳ֧��С��ϵͳʱ��ͨ�����HASH�����������������������ʵ����
     */
    private static Hashtable m_htClasses = null;
    private static int m_nCount = 0;
    private static boolean m_bDebug = false;
    private static final String NOT_CACHED_CLASS = "NOT-CACHED-CLASS";
    public BaseObjectFactory()
    {
    } 
    /**
     * �߼�����ʵ�ֻ����������ض��������SessionBeanҲ�����Ǿ�ͨ��BaseObject�������������������һ�֣�
     * ����������صĶ�������״̬�ġ�
     * @param strClassName ���󴴽��Ķ��������õ�ŷ���·�����磺com.iss.iam.dao.Asset
     * @return Object ����null�����ʧ�ܡ�
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
     * �ͷ���getBaseObject�����Ķ��󣬱����getBaseObject�ɶ�ʹ�á�
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
     * ��ʼ���๤��,��ISS-class-factories.xml�ļ��ж�ȡ�����๤��������Ϣ��ISS-class-factories.xml
     * �ĵ��ṹΪ
     * <ISS-class-factories>
     * <use-jndi></use-jndi>
     * <class-factory>
     * 		<name></name>
     * 		<resource-path></resource-path>
     * </class-factory>
     * </ISS-class-factories>
     * 
     * <p>Ӧ���๤�������ļ����ĵ��ṹ˵����</p>
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
//        	ȡ��ǰ�̵߳�classloader
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