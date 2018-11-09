<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.common.attach.bizlogic.*,
	com.iss.itreasury.common.attach.dataentity.*,
	com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
	long ParentID = -1; //所属对象的ID
	long ParentIDType = -1;//所属对象的类型
	long ModuleID = -1;//所属模块ID
	long TransTypeID = -1;//所属交易类型ID
	long TransSubTypeID = -1;//所属交易子类型ID
	long CurrencyID = -1;
	long OfficeID = -1;
	long clientID = -1;   //客户ID
	String transCode = "";
	try
	{
		request.setCharacterEncoding("gb2312");
		//取参数变量
		String strTmp = "";
		strTmp =request.getParameter("ParentID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ParentID = Long.parseLong(strTmp.trim());
		}

		strTmp =request.getParameter("ParentIDType");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ParentIDType = Long.parseLong(strTmp.trim());
		}
		
		strTmp =request.getParameter("ModuleID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ModuleID = Long.parseLong(strTmp.trim());
		}
		
		strTmp =request.getParameter("TransTypeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     TransTypeID = Long.parseLong(strTmp.trim());
		}
		
		strTmp =request.getParameter("TransSubTypeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     TransSubTypeID = Long.parseLong(strTmp.trim());
		}

		strTmp =request.getParameter("CurrencyID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     CurrencyID = Long.parseLong(strTmp.trim());
		}
		strTmp =request.getParameter("OfficeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     OfficeID = Long.parseLong(strTmp.trim());
		}
		strTmp =request.getParameter("ClientID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     clientID = Long.parseLong(strTmp.trim());
		}
		transCode =request.getParameter("transCode");
		
		AttachInfo info = new AttachInfo();
		info.setParentID(ParentID);
		info.setParentType(ParentIDType);
		info.setModuleID(ModuleID);
		info.setTransTypeID(TransTypeID);
		info.setTransSubTypeID(TransSubTypeID);
		info.setCurrencyID(CurrencyID);
		info.setOfficeID(OfficeID);
		info.setTransCode(transCode);
		info.setNClientID(clientID);
		
		AttachOperation attachOperation = new AttachOperation();
		Collection c = null;
		c = attachOperation.findByOBCondition(info);  
		request.setAttribute("AttachInfo", c);   
		request.setAttribute("ParentID", String.valueOf(ParentID)); 
		request.setAttribute("ParentIDType", String.valueOf(ParentIDType));
		request.setAttribute("ModuleID", String.valueOf(ModuleID)); 
		request.setAttribute("TransTypeID", String.valueOf(TransTypeID));
		request.setAttribute("TransSubTypeID", String.valueOf(TransSubTypeID));  
		request.setAttribute("CurrencyID", String.valueOf(CurrencyID));
		request.setAttribute("OfficeID", String.valueOf(OfficeID));
		request.setAttribute("ClientID", String.valueOf(clientID));   
		String strURL = "/common/c102-v.jsp";

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + strURL);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out, sessionMng, ie,"客户管理", "",1);
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			