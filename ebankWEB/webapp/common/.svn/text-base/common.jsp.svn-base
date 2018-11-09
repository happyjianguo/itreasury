<%--
/***** isoftstone Reserved *****
//Declaration: common.jsp
//Function: 定义公用函数及常数
//Parameter: None
//Author: zyren
//Date: 2002-10-25
//Copyright (c) 2002 by isoftstone, Inc. All Rights Reserved.
//***** isoftstone Reserved ******/
--%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%!
//定义分页页面每页显示的记录数
public final int ROWS_IN_EACH_PAGE = 15;
%>
 
<%!
boolean checkClick(String strClickCount, HttpSession session) 
{
	long lngClickCount = 0;
	Log.print("Common.jsp.checkClick:parameter strClickCount="+strClickCount);
	if (strClickCount != null && !strClickCount.equals("")) 
	{
		lngClickCount = Long.parseLong(strClickCount);			
		String strOldClickCount = (String) session.getAttribute("clickCount");
                       Log.print("===common===18===strOldClickCount===" + strOldClickCount);
			if (strOldClickCount != null && !strOldClickCount.equals("")) 
			{
				if (lngClickCount == (Long.parseLong(strOldClickCount) + 1)) 
				{
					session.setAttribute("clickCount", strClickCount);
					return true;
				} 
			}
	} 
	else 
	{
		Log.print("===checkClick()===" + "传入的参数不是数字型字符串！");
	}
		return false;
}

//读取字符串型参数，若为null则转成""
public static String GetParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return sTemp==null?"":sTemp;
}

//读取字符串型参数，若为null则转成默认值（strDefaultValue）
public static String GetParam(HttpServletRequest rqt, String strParamName, String strDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return sTemp==null?strDefaultValue:sTemp;
}

//读取长整型参数，若为null或""则转为-1
public static long GetNumParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?-1:Long.parseLong(sTemp);
}

//读取长整型参数，如位null或""则转为默认值（lDefaultValue）
public static long GetNumParam(HttpServletRequest rqt, String strParamName, long lDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?lDefaultValue:Long.parseLong(sTemp);
}

//读取整型参数，若为null或""则转为0
public static int GetIntParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?0:Integer.parseInt(sTemp);
}

//读取整型参数，如位null或""则转为默认值（lDefaultValue）
public static int GetIntParam(HttpServletRequest rqt, String strParamName, int lDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?lDefaultValue:Integer.parseInt(sTemp);
}

//读取浮点型参数，如位null或""则转为默认值（fDefaultValue）
public static float GetFloatParam(HttpServletRequest rqt, String strParamName, float fDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?fDefaultValue:Float.parseFloat(sTemp);
}

//读取浮点型参数，如位null或""则转为默认值（dDefaultValue）
public static double GetDoubleParam(HttpServletRequest rqt, String strParamName, double dDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?dDefaultValue:Double.parseDouble(DataFormat.reverseFormatAmount(sTemp));
}

//读取日期型参数，如位null或""则转为默认值（fDefaultValue）
public static Timestamp GetTSParam(HttpServletRequest rqt, String strParamName, String sDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);   
	return (sTemp==null||sTemp.equals(""))?
	null:java.sql.Timestamp.valueOf(sTemp+ " 00:00:00");
}


//将输入（sTemp）为null或空的值转为空格（&nbsp;），用于html页面表格项输出
public static String NoNullString(String sTemp)
{
	return (sTemp==null||sTemp.equals(""))?"&nbsp;":sTemp;
}

//将输入（sTemp）为null的值转为指定的字符串（sReplace），用于html页面表格项输出
public static String NoNullString(String sTemp, String sReplace)
{
	return (sTemp==null||sTemp.equals(""))?sReplace:sTemp;
}

//将字符串（sSource）转换为指定长度（nLength）的，
//不足部分以字符（sPadString）补齐（nLeftRight: 0-左补齐，1-右补齐）
public static String getLimitString(String sSource, int nLength, String sPadString, int nLeftRight)
{
	String sTarget = "";
    int nSourceLength = sSource.length();
        
    //原字符串长度比指定长度长，则不做任何处理
    if (nSourceLength > nLength) sTarget = sSource;

	//按指定长度生成补齐字符串
    for(int i=0; i<nLength-nSourceLength; i++)
    	{sTarget += sPadString;}

	if (nLeftRight == 0)
    {   //左补齐
    	sTarget = sTarget + sSource;
	}else
    {   //右补齐
    	sTarget = sSource + sTarget;
	}
    return sTarget;
}

//将数据库中的字符串转为HTML可识别的格式
public static String HTMLEncode(String sSourceText)
{
    StringBuffer newString = new StringBuffer();
	//处理空串
	if(sSourceText==null||sSourceText.equals("")) return "&nbsp";
    
	char charItem;
	for(int i = 0; i < sSourceText.length(); i++){
		charItem = sSourceText.charAt(i);
		if(charItem == '>')			newString.append("&gt;");
		else if(charItem == '<')	newString.append("&lt;");
		else if(charItem == '&')	newString.append("&amp;");
		else if(charItem == '"')	newString.append("&quot;");
		else if(charItem == 39)		newString.append("&rsquo;");	// 单引号
		else if(charItem == 13 && sSourceText.charAt(i+1)==10)		// 回车
		{
			newString.append("<BR>");
			i++; 
		}
		else	newString.append(charItem);		//普通字符不作处理
	}
	return newString.toString();  
} 
%> 

<script language="javascript">

var http_request = false;


function send_request(method,url,content,responseType,callback) {
	http_request = false;

	if(window.XMLHttpRequest) { //Mozilla
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
			http_request.overrideMimeType("text/xml");
		}
	}
	else if (window.ActiveXObject) { // IE
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	if (!http_request) { // 
		window.alert("can not create XMLHttpRequest object.");
		return false;
	}
	if(responseType.toLowerCase()=="text") {
		http_request.onreadystatechange = callback;
	}
	else if(responseType.toLowerCase()=="xml") {
		http_request.onreadystatechange = callback;
	}
	else {
		window.alert("response type error");
		return false;
	}

	if(method.toLowerCase()=="get") {
		http_request.open(method, url, true);
	}
	else if(method.toLowerCase()=="post") {
		http_request.open(method, url, true);
		http_request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	}
	else {
		window.alert("http type error.");
		return false;
	}
	http_request.send(content);
}

</script>

