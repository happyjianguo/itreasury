/**
 * created on Mar 18, 2008
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.util.HashMap;
import java.util.StringTokenizer;


/**
 * 字符串处理工具类
 * 
 * @author xintan
 *
 */
public class StringUtil {

    private static final String DELIMITER_MAIN =";"; //字符串分割符定义
    private static final String DELIMITER_DOT = ","; 

    public static HashMap split(String orignalStr)
    {
        HashMap mapResult = new HashMap();
        
        if(orignalStr==null) return mapResult;        
        
        orignalStr = orignalStr.trim();
        
        StringTokenizer mainTokenizer = new StringTokenizer(orignalStr, DELIMITER_MAIN, false);
        while(mainTokenizer.hasMoreTokens())
        {
            String aMainToken = mainTokenizer.nextToken();
            if(aMainToken==null) continue;
            StringTokenizer subTokenizer = new StringTokenizer(aMainToken, DELIMITER_DOT, false);
            if(subTokenizer.countTokens()==2)
            { 
                String strName = subTokenizer.nextToken();
                if(strName==null || strName.trim().length()<=0)
                {
                    continue;
                }
                String strValue = subTokenizer.nextToken();
                
                mapResult.put(strName.trim(), strValue);
            }
        }
        return mapResult;
    }
    
    public static boolean isNeedSplit(String originalStr)
    {
        StringTokenizer token = new StringTokenizer(originalStr, ",;", true);
        if(token.countTokens()>=3)
            return true;
        else return false;
    }
    
    public static boolean isBlank(String str)
    {
        if(str==null || str.trim().equals("")) return true;
        else return false;
    }
    
    public static boolean isEqual(String strFirst, String strSecond) {
        if(isBlank(strFirst)&& isBlank(strSecond))
        {
            return true;
        }
        if(strFirst!=null)
        {
            return strFirst.equals(strSecond);
        }
            
        return false;
    }    

    public static void main(String[] args)
    {
        String strTest = "abc3,ff;";
//        while(token.hasMoreTokens())
//        {
//            System.out.println(token.nextToken());
//        }
        System.out.println(isNeedSplit(strTest));
//        String strTest = "   ";
//        HashMap splitResults = split(strTest);
//        if(splitResults==null) System.out.println("结果为空");
//        else{
//            System.out.println("结果为:" + splitResults.size());
//            Set keySet = splitResults.keySet();
//            Iterator it = keySet.iterator();
//            while(it.hasNext())
//            {
//                String name = (String) it.next();
//                String value = (String) splitResults.get(name);
//                System.out.println("name:" + name + "  value:" + value);
//            }
//        }
//        System.out.println(isContainedDelimiter(strTest, ","));
    }

    
}

