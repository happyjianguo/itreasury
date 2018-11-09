<%--
 页面名称 ：c856.jsp
 页面功能 : 网银用户删除校验页面
 作    者 ：王振
 日    期 ：2011-11-29
 特殊说明 ：
 修改历史 ：
 修改说明 :
--%>
<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.system.bizdelegation.PrivilegeDelegation"%> 
<%
	try
	{

		
		long lUserID = -1;
		String temp = "";
		boolean jobAssion = false;//该用户是否分配有岗位。
		temp = request.getParameter("userID");
		System.out.print(temp);
		if(temp!=null&&temp.trim().length()>0)
		{
			lUserID = Long.parseLong(temp);
		}
		PrivilegeDelegation delegation = new PrivilegeDelegation();
		jobAssion = delegation.findDuty(lUserID);
		
		out.println("<div id='result'>"+jobAssion+"</div>");
	}catch(Exception e)
	{
		e.printStackTrace();
	
	}

%>