<%@page contentType="text/html;charset=gbk"%>
<%
    /*response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);*/
%>
<%@page import="java.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.attach.bizlogic.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.loan.attach.dataentity.*,
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
		
		AutoFileInfo fInfo = null;
		fInfo = (AutoFileInfo)request.getAttribute("fileInfo");
		AttachInfo  info = new AttachInfo();
		info.setFileID(fInfo.getID());
		info.setType(Constant.DocType.EBANKUPLOAD);
		info.setParentType(fInfo.getParentIDType());
		info.setParentID(fInfo.getParentID());
		info.setStatus(Constant.RecordStatus.VALID);
		AttachOperation attachOperation = new AttachOperation();
		attachOperation.add(info);
		String strURL = strContext + "/attach/a101a-c.jsp?ParentID="+fInfo.getParentID()+"&ParentIDType="+fInfo.getParentIDType();

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

			