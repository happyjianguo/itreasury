/**
 * 一些处理本模块jsp文件的常用方法
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
