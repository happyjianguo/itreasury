<%--
/***** isoftstone Reserved *****
//Declaration: common.jsp
//Function: ���幫�ú���������
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
//�����ҳҳ��ÿҳ��ʾ�ļ�¼��
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
		Log.print("===checkClick()===" + "����Ĳ��������������ַ�����");
	}
		return false;
}

//��ȡ�ַ����Ͳ�������Ϊnull��ת��""
public static String GetParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return sTemp==null?"":sTemp;
}

//��ȡ�ַ����Ͳ�������Ϊnull��ת��Ĭ��ֵ��strDefaultValue��
public static String GetParam(HttpServletRequest rqt, String strParamName, String strDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return sTemp==null?strDefaultValue:sTemp;
}

//��ȡ�����Ͳ�������Ϊnull��""��תΪ-1
public static long GetNumParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?-1:Long.parseLong(sTemp);
}

//��ȡ�����Ͳ�������λnull��""��תΪĬ��ֵ��lDefaultValue��
public static long GetNumParam(HttpServletRequest rqt, String strParamName, long lDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?lDefaultValue:Long.parseLong(sTemp);
}

//��ȡ���Ͳ�������Ϊnull��""��תΪ0
public static int GetIntParam(HttpServletRequest rqt, String strParamName)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?0:Integer.parseInt(sTemp);
}

//��ȡ���Ͳ�������λnull��""��תΪĬ��ֵ��lDefaultValue��
public static int GetIntParam(HttpServletRequest rqt, String strParamName, int lDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?lDefaultValue:Integer.parseInt(sTemp);
}

//��ȡ�����Ͳ�������λnull��""��תΪĬ��ֵ��fDefaultValue��
public static float GetFloatParam(HttpServletRequest rqt, String strParamName, float fDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?fDefaultValue:Float.parseFloat(sTemp);
}

//��ȡ�����Ͳ�������λnull��""��תΪĬ��ֵ��dDefaultValue��
public static double GetDoubleParam(HttpServletRequest rqt, String strParamName, double dDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);
	return (sTemp==null||sTemp.equals(""))?dDefaultValue:Double.parseDouble(DataFormat.reverseFormatAmount(sTemp));
}

//��ȡ�����Ͳ�������λnull��""��תΪĬ��ֵ��fDefaultValue��
public static Timestamp GetTSParam(HttpServletRequest rqt, String strParamName, String sDefaultValue)
{
	String sTemp = (String)rqt.getAttribute(strParamName);   
	return (sTemp==null||sTemp.equals(""))?
	null:java.sql.Timestamp.valueOf(sTemp+ " 00:00:00");
}


//�����루sTemp��Ϊnull��յ�ֵתΪ�ո�&nbsp;��������htmlҳ���������
public static String NoNullString(String sTemp)
{
	return (sTemp==null||sTemp.equals(""))?"&nbsp;":sTemp;
}

//�����루sTemp��Ϊnull��ֵתΪָ�����ַ�����sReplace��������htmlҳ���������
public static String NoNullString(String sTemp, String sReplace)
{
	return (sTemp==null||sTemp.equals(""))?sReplace:sTemp;
}

//���ַ�����sSource��ת��Ϊָ�����ȣ�nLength���ģ�
//���㲿�����ַ���sPadString�����루nLeftRight: 0-���룬1-�Ҳ��룩
public static String getLimitString(String sSource, int nLength, String sPadString, int nLeftRight)
{
	String sTarget = "";
    int nSourceLength = sSource.length();
        
    //ԭ�ַ������ȱ�ָ�����ȳ��������κδ���
    if (nSourceLength > nLength) sTarget = sSource;

	//��ָ���������ɲ����ַ���
    for(int i=0; i<nLength-nSourceLength; i++)
    	{sTarget += sPadString;}

	if (nLeftRight == 0)
    {   //����
    	sTarget = sTarget + sSource;
	}else
    {   //�Ҳ���
    	sTarget = sSource + sTarget;
	}
    return sTarget;
}

//�����ݿ��е��ַ���תΪHTML��ʶ��ĸ�ʽ
public static String HTMLEncode(String sSourceText)
{
    StringBuffer newString = new StringBuffer();
	//����մ�
	if(sSourceText==null||sSourceText.equals("")) return "&nbsp";
    
	char charItem;
	for(int i = 0; i < sSourceText.length(); i++){
		charItem = sSourceText.charAt(i);
		if(charItem == '>')			newString.append("&gt;");
		else if(charItem == '<')	newString.append("&lt;");
		else if(charItem == '&')	newString.append("&amp;");
		else if(charItem == '"')	newString.append("&quot;");
		else if(charItem == 39)		newString.append("&rsquo;");	// ������
		else if(charItem == 13 && sSourceText.charAt(i+1)==10)		// �س�
		{
			newString.append("<BR>");
			i++; 
		}
		else	newString.append(charItem);		//��ͨ�ַ���������
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

