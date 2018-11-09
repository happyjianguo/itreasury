package com.iss.system.util;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author pliu
 *
 * ResourceBoundleUtil是一个读取properties文件的工具类，这个类会对读取过的properties文件进行缓存。
 */
public class ResourceBoundleUtil
{
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro。
     * @param strKey 读取资源的键值。
     * @return String
     */
    public static String getMessage(String strConfig, String strKey)
    {
        return getMessage(strConfig, strKey, null);
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro。
     * @param strKey 读取资源的键值。
     * @param objArg
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArg)
    {
        return getMessage(strConfig, strKey, new Object[] { objArg });
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro。
     * @param strKey 读取资源的键值。
     * @param objArg0
     * @param objArg1
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArg0, Object objArg1)
    {
        return getMessage(strConfig, strKey, new Object[] { objArg0, objArg1 });
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro。
     * @param strKey 读取资源的键值。
     * @param objArg0
     * @param objArg1
     * @param objArg2
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArg0, Object objArg1, Object objArg2)
    {
        return getMessage(strConfig, strKey, new Object[] { objArg0, objArg1, objArg2 });
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro,但是
     * 不要加locale后缀，我们会使用系统locale去转换字符串。
     * @param strKey 读取资源的键值。
     * @param objArgs
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArgs[])
    {
        return ResourceBoundleUtil.getMessage(strConfig, Locale.getDefault(), strKey, objArgs);
    }
    /**
     * 读对某一特定properties文件中的字串信息。
     * @param strConfig 资源配置路径，即message-resources中的name,如:com.iss.iam.iAMPro,但是
     * 不要加locale后缀。
     * @param lacale 这个方法会根据locale转换读取的字符串为相应的语言。
     * @param strKey 读取资源的键值。
     * @param objArgs
     * @return String
     */
    public static String getMessage(String strConfig, Locale lacale, String strKey, Object objArgs[])
    {
        String strMsg = null;
        ResourceBundle resourceBundle = ResourceBundle.getBundle(strConfig, lacale);
        strMsg = resourceBundle.getString(strKey);
        if (strMsg != null)
        {
            try
            {
                strMsg = new String(strMsg.getBytes("iso-8859-1"), "gb2312");
                if (objArgs != null)
                {
                    strMsg = MessageFormat.format(strMsg, objArgs);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return strMsg;
    }
}
