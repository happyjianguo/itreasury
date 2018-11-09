<%@page contentType="text/html;charset=gbk"%>
<%
    /*response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.ebank.obattach.bizlogic.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.ebank.obattach.dataentity.*,
	com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%
	try
	{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		long ParentID = -1;
		long ParentIDType = -1;
		long lFileID = -1;
		
		//取参数变量
		String strTemp = "";
		strTemp = request.getParameter("ParentID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ParentID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ParentID = -1;
			}
		}
		
		strTemp = request.getParameter("ParentIDType");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ParentIDType = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ParentIDType = -1;
			}
		}
		
		strTemp = request.getParameter("fileID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lFileID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lFileID = -1;
			}
		}
		
		OBAttachOperation attachOperation = new OBAttachOperation();
		attachOperation.delete(lFileID);
		String strURL = strContext + "/attach/a101-c.jsp?ParentID="+ParentID + "&ParentIDType=" +ParentIDType;

		/* 获取上下文环境 */
		//ServletContext sc = getServletContext();
	

		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			