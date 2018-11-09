package com.iss.system.util;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author pliu
 *
 * ResourceBoundleUtil��һ����ȡproperties�ļ��Ĺ����࣬������Զ�ȡ����properties�ļ����л��档
 */
public class ResourceBoundleUtil
{
    /**
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro��
     * @param strKey ��ȡ��Դ�ļ�ֵ��
     * @return String
     */
    public static String getMessage(String strConfig, String strKey)
    {
        return getMessage(strConfig, strKey, null);
    }
    /**
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro��
     * @param strKey ��ȡ��Դ�ļ�ֵ��
     * @param objArg
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArg)
    {
        return getMessage(strConfig, strKey, new Object[] { objArg });
    }
    /**
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro��
     * @param strKey ��ȡ��Դ�ļ�ֵ��
     * @param objArg0
     * @param objArg1
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArg0, Object objArg1)
    {
        return getMessage(strConfig, strKey, new Object[] { objArg0, objArg1 });
    }
    /**
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro��
     * @param strKey ��ȡ��Դ�ļ�ֵ��
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
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro,����
     * ��Ҫ��locale��׺�����ǻ�ʹ��ϵͳlocaleȥת���ַ�����
     * @param strKey ��ȡ��Դ�ļ�ֵ��
     * @param objArgs
     * @return String
     */
    public static String getMessage(String strConfig, String strKey, Object objArgs[])
    {
        return ResourceBoundleUtil.getMessage(strConfig, Locale.getDefault(), strKey, objArgs);
    }
    /**
     * ����ĳһ�ض�properties�ļ��е��ִ���Ϣ��
     * @param strConfig ��Դ����·������message-resources�е�name,��:com.iss.iam.iAMPro,����
     * ��Ҫ��locale��׺��
     * @param lacale ������������localeת����ȡ���ַ���Ϊ��Ӧ�����ԡ�
     * @param strKey ��ȡ��Դ�ļ�ֵ��
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
