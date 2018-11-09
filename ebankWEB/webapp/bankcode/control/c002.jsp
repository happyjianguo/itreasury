<%@ page contentType="text/html;charset=GBK"%>

<%@ page import="java.util.*,java.io.PrintWriter,com.iss.itreasury.fcinterface.bankportal.bankcode.bizlogic.*"%>
<% 
	response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires",0);
%>
<%
	String queryCondition = "";
	String strResult = "";
	String strTmp = "";
	Collection cityCol = null;
	try
	{
	 	strTmp = request.getParameter("query");
	 	strTmp = java.net.URLDecoder.decode(request.getParameter("query"),"utf-8");
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
	if(strTmp!=null && strTmp.trim().length()>0)
	{
		queryCondition = strTmp;
	}
			
	AreaCodeBiz areaCodeBiz = new AreaCodeBiz();
	cityCol=areaCodeBiz.findAreaCodeCityByProvince(queryCondition);
	String[] cityStr = (String[])cityCol.toArray(new String[0]);
	for(int i=0;i<cityStr.length;i++)
	{
		if(cityStr[i]!=null)
		{
			strResult+=cityStr[i].trim()+"::";
		}
	}
	response.setContentType("text/xml;charset=GBK");
	PrintWriter io = response.getWriter();
	if(strResult.trim().length()>0)
	{
		io.write(strResult.substring(0,strResult.length()));
		System.out.println("传送返回值完毕！"+strResult.substring(0,strResult.length()));
	}		
	io.close();
%>