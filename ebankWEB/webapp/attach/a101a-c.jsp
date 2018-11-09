<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.attach.bizlogic.*,
	com.iss.itreasury.util.*
" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%
	long ParentID = -1; //所属对象的ID
	long ParentIDType = -1;//所属对象的类型
	
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

		AttachOperation attachOperation = new AttachOperation();
		Collection c = null;
		c = attachOperation.query(ParentIDType, ParentID);     
		request.setAttribute("AttachInfo", c);   
		request.setAttribute("ParentID", String.valueOf(ParentID));   
		request.setAttribute("ParentIDType", String.valueOf(ParentIDType));   
		String strURL = strContext + "/attach/a102a-v.jsp";

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

			