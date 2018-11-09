<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
	com.iss.itreasury.settlement.util.*,
	com.iss.itreasury.common.attach.bizlogic.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.common.attach.dataentity.*,
	com.iss.itreasury.util.*
" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<fs:header showMenu="false"/>
<% 
//删除记录
	try{
		
		long ParentID = -1;
		long ParentIDType = -1;
		long lFileID = -1;
		long ModuleID = -1;
		long TransTypeID = -1;
		long TransSubTypeID = -1;
		long CurrencyID = -1;
		long OfficeID = -1;
		String transCode = "";
		//取参数变量
		String strTmp = "";
		strTmp = (String)request.getAttribute("ParentID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ParentID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("ParentIDType");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ParentIDType = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("fileID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     lFileID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("ModuleID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     ModuleID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("TransTypeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     TransTypeID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("TransSubTypeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     TransSubTypeID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("CurrencyID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     CurrencyID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("OfficeID");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     OfficeID = Long.parseLong(strTmp.trim());
		}
		transCode = (String)request.getAttribute("transCode");
		
		AttachOperation attachOperation = new AttachOperation();

		attachOperation.deleteOBDoc(lFileID);
		
		String strURL = "/common/c101-c.jsp?transCode="+transCode+"&ParentID="+ParentID + "&ParentIDType=" +ParentIDType + "&ModuleID=" +ModuleID + "&TransTypeID=" +TransTypeID + "&TransSubTypeID=" +TransSubTypeID + "&CurrencyID=" +CurrencyID + "&OfficeID=" +OfficeID;

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
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
	catch (Exception ie)
	{
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			