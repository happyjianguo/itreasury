package com.iss.sysframe.jasperPrint.util;

public class JasperPrintUtil {

	public static String replaceSomeOldWithNew(String oldString ,String newString,String switchString)
	{
		String returnString="";
		StringBuffer tempStringBuffer=new StringBuffer();
		tempStringBuffer.append(switchString);
		tempStringBuffer.replace(tempStringBuffer.indexOf(oldString), tempStringBuffer.indexOf(oldString)+oldString.length(), newString);	
		returnString=tempStringBuffer.toString();
		
		return returnString;
		
	}
	
	public static void main (String[] args ){
		
		StringBuffer a=new StringBuffer();
		a.append("shdgvfsaudgyfoisufh#piudgshfughuyfos");
		a.replace(a.indexOf("#"),a.indexOf("#")+1, "VVVVVVVVVVVV");
		System.out.print(a);
		
	}
}
