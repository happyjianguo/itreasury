/**
 * һЩ����ģ��jsp�ļ��ĳ��÷���
 */
 
package com.iss.itreasury.ebank.privilege.util;


public class MyUtil
{

    public MyUtil()
    {
    }

    public static int getLevel(String strNo)
    {
        String tmpArray[] = strNo.split("-");
        return tmpArray.length - 1;
    }

    public static void main(String args1[])
    {
    }
}
