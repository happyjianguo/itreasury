package com.iss.system;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.iss.system.security.Rights;
/**
 * @author pliu
 * <!--安全访问控制设置,主要针对一次提交的ACTION，一个ACTION可能需要多个权限。-->
 * <security-setting>
 * <!--当一个URL有多个Action时，用于进行判断的附加参数名。-->
 * <parameter>operate</parameter>
 * <!--一个安全访问控制项.
 * 安全访问控制项对应的URL，包含扩展名。
 * parameter为访问控制项的附加参数，可以没有。
 * rights为该访问项需要的权限，如果是多个权限要以逗号分隔。多个权限之间的关系是逻辑"或"关系-->
 * <access-control-item url="" parameter="" rights="">
 * </access-control-item>
 * </security-setting>
 */
public class SecuritySettingConfig
{
    static private Hashtable m_htACIs = null;
    public static String DISPATCH_PARAMETER = null;
    public static String CONFIG = null;
    /**
     * 
     * @param url
     * @param strPara
     * @param rights
     */
    public static boolean checkPermission(String strUrl, String strPara, long[] rights)
    {
        boolean bMatch = true;
        String strURI = null;
        if (strPara == null)
        {
            strURI = strUrl;
        }
        else if (strUrl.endsWith(".do"))
        {
            strURI = MessageFormat.format("{0}?{1}={2}", new Object[] { strUrl, DISPATCH_PARAMETER, strPara });
        }
        else
        { //*.jsp
            strURI = MessageFormat.format("{0}?{1}", new Object[] { strUrl, strPara });
        }
        if (SecuritySettingConfig.m_htACIs.containsKey(strURI))
        {
            RightItem ri = (RightItem) SecuritySettingConfig.m_htACIs.get(strURI);
            if (ri.m_lRights != null)
            {
                bMatch = false;
                for (int i = 0; rights != null  && i < ri.m_lRights.length; i++)
                {
                    for (int j = 0; j < rights.length; j++)
                    {
                        if (ri.m_lRights[i] == rights[j])
                        {
                            bMatch = true;
                            break;
                        }
                    }
                }
            }
        }
        return bMatch;
    }
    /**
     * 
     * @param strUrl
     * @param strPara
     * @return String
     */
    public static String getRightsString(String strUrl, String strPara)
    {
        String strURI = null;
        if (strPara == null)
        {
            strURI = strUrl;
        }
        else if (strUrl.endsWith(".do"))
        {
            strURI = MessageFormat.format("{0}?{1}={2}", new Object[] { strUrl, DISPATCH_PARAMETER, strPara });
        }
        else
        { //*.jsp
            strURI = MessageFormat.format("{0}?{1}", new Object[] { strUrl, strPara });
        }
        String strRights = null;
        if (SecuritySettingConfig.m_htACIs.containsKey(strURI))
        {
            RightItem ri = (RightItem) SecuritySettingConfig.m_htACIs.get(strURI);
            strRights = ri.m_strRithtString;
        }
        return strRights;
    }
    /**
     * 
     * @param strUrl
     * @param strPara
     * @return long[]
     */
    public static long[] getRightsAssign(String strUrl, String strPara)
    {
        String strURI = null;
        String[] strRights = null;
        if (strPara == null)
            strURI = MessageFormat.format("{0}", new Object[] { strUrl });
        else
            strURI = MessageFormat.format("{0}?{1}={2}", new Object[] { strUrl, DISPATCH_PARAMETER, strPara });
        long lRights[] = null;
        if (SecuritySettingConfig.m_htACIs.containsKey(strURI))
        {
            lRights = (long[]) SecuritySettingConfig.m_htACIs.get(strURI);
        }
        return lRights;
    }
    /**
     * 
     * @param strResourcePath
     */
    public static void load(String strResourcePath)
    {
        try
        {
            //取当前线程的CLASS LOADER。
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null)
            {
                classLoader = Class.class.getClassLoader();
            }
            InputStream is = classLoader.getResourceAsStream(strResourcePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document xmlDocument = db.parse(is);
            is.close();
            //load class factory configuration
            if (m_htACIs == null)
                m_htACIs = new Hashtable(1024);
            //parse parameter name
            NodeList nl = xmlDocument.getElementsByTagName("parameter");
            Node node = nl.item(0);
            DISPATCH_PARAMETER = "";
            for (Node nodeVal = node.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
            {
                DISPATCH_PARAMETER += nodeVal.getNodeValue();
            }
            //parser config resource name
            nl = xmlDocument.getElementsByTagName("config");
            node = nl.item(0);
            CONFIG = "";
            for (Node nodeVal = node.getFirstChild(); nodeVal != null; nodeVal = nodeVal.getNextSibling())
            {
                CONFIG += nodeVal.getNodeValue();
            }
            //
            nl = xmlDocument.getElementsByTagName("access-control-item");
            String strURL = null, strPara = null, strRight = null;
            Element element = null;
            RightItem ri = null;
            String strRightName;
            for (int j = 0; j < nl.getLength(); j++)
            {
                element = (Element) nl.item(j);
                ri = new RightItem();
                strURL = element.getAttribute("url");
                strPara = element.getAttribute("parameter");
                strRight = element.getAttribute("rights");
                ri.m_strRights = strRight.split(",");
                ri.m_lRights = new long[ri.m_strRights.length];
                for (int intIndex = 0; intIndex < ri.m_lRights.length; intIndex++)
                {
                    ri.m_lRights[intIndex] = Rights.getRightValue(CONFIG, ri.m_strRights[intIndex]);
                    strRightName =
                        ri.m_strRights[intIndex].substring(0, ri.m_strRights[intIndex].lastIndexOf(".value")) + ".name";
                    if (ri.m_strRithtString == null)
                        ri.m_strRithtString = strRightName;
                    else
                        ri.m_strRithtString += "," + strRightName;
                }
                if (strPara == null || strPara.equals(""))
                {
                    m_htACIs.put(strURL, ri);
                }
                else
                {
                    m_htACIs.put(
                        MessageFormat.format("{0}?{1}={2}", new Object[] { strURL, DISPATCH_PARAMETER, strPara }),
                        ri);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("iss classes factory loaded finished.\n");
        }
    }
}
/**
 * 
 * @author pliu
 */
class RightItem
{
    public RightItem()
    {
    }
    public long[] m_lRights = null;
    public String[] m_strRights = null;
    public String m_strRithtString = null;
}