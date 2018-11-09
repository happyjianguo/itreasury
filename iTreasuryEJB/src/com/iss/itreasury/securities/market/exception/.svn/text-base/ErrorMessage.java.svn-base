/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.exception;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ErrorMessage {
    private static int intKeyCount = 0;
    private static String[] strErrorMessageList = null;
    private static String[] strKeyList = null;
    static boolean blnHasInit = false;

    public ErrorMessage() {
    }

    static {
        if (!blnHasInit) {
            blnHasInit = true;
            try {
                Properties p = new Properties();
                FileInputStream is = new FileInputStream("ErrorMessage.properties");
                p.load(is);
                is.close();
                Enumeration enuKey = p.propertyNames();
                Vector vctKey = new Vector();
                while (enuKey.hasMoreElements()) {
                    vctKey.add(enuKey.nextElement());
                }
                intKeyCount = vctKey.size();
                strKeyList = new String[intKeyCount];
                strErrorMessageList = new String[intKeyCount];
                for (int i = 0; i < intKeyCount; i++) {
                    String strKey = (String) vctKey.elementAt(i);
                    strKeyList[i] = strKey;
                    strErrorMessageList[i] = p.getProperty(strKey);

                    int index = strErrorMessageList[i].indexOf("#");
                    if (index != -1) {
                        strErrorMessageList[i] = strErrorMessageList[i].substring(0, index).trim();
                    }
                }
            } catch (Exception e) {
                blnHasInit = false;
                e.printStackTrace();
            }
        }
    }

    public static String getErrorMessage(String strErrorCode) {
        String strErrorMessage = null;
        if (intKeyCount > 0) {
            for (int i = 0; i < intKeyCount; i++) {
                if (strErrorCode.equals(strKeyList[i])) {
                    strErrorMessage = UnicodeToGBK(strErrorMessageList[i]);
                    break;
                }
            }
        }
        return strErrorMessage;
    }

    /**
     * Add by eagle
     * @param strErrorCode
     * @param sParams
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String[] sParams) {
        String strErrorMessage = getErrorMessage(strErrorCode);
        StringBuffer sbErrorMsg = new StringBuffer();
        if (strErrorMessage == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(strErrorMessage, "?");
        if (sParams == null || st.countTokens() - 1 != sParams.length) {
            return strErrorMessage;
        }

        //将其中的?号替换对应的sParams
        for (int i = 0; i < sParams.length; i++) {
            sbErrorMsg.append(st.nextToken()).append(sParams[i]);
        }
        sbErrorMsg.append(st.nextToken());

        return sbErrorMsg.toString();
    }

    /**
     * Add by eagle
     * @param strErrorCode
     * @param sParams
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String sParam) {
        String[] sParams = new String[1];
        sParams[0] = sParam;

        return getErrorMessage(strErrorCode, sParams);
    }

    /**
     * Add by eagle
     * @param strErrorCode
     * @param sParams1、sParam2
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String sParam1, String sParam2) {
        String[] sParams = new String[2];
        sParams[0] = sParam1;
        sParams[1] = sParam2;

        return getErrorMessage(strErrorCode, sParams);
    }
    /**
     * Add by eagle
     * @param s
     * @return String
     */
    private static String UnicodeToGBK(String s) {
        try {
            if (s == null || s.equals("")) {
                return "";
            }
            String newstring = null;
            newstring = new String(s.getBytes("ISO8859_1"), "GBK");
            return newstring;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    public static void main(String[] args) throws MarketException {
        String strErrorMessage = "[?]在证券模块与财务模块中的[?]或[?]不符，请进行调查！";
        String[] sParams = {"123", "345", "4565"};
        StringBuffer sbErrorMsg = new StringBuffer();

        //将其中的?号替换对应的sParams
        StringTokenizer st = new StringTokenizer(strErrorMessage, "?");
        System.out.println("count = " + st.countTokens());
        if (sParams == null || st.countTokens() - 1 != sParams.length) {
            throw new MarketException("参数错误！");
        }

        for (int i = 0; i < sParams.length; i++) {
            sbErrorMsg.append(st.nextToken()).append(sParams[i]);
        }
        sbErrorMsg.append(st.nextToken());
    }
}