<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.common.attach.bizlogic.*,
	com.iss.itreasury.dataentity.AutoFileInfo,
	com.iss.itreasury.common.attach.dataentity.*,
	com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%

	try
	{	
		System.out.println("add record in c106.jsp");
		AutoFileInfo fInfo = null;
		fInfo = (AutoFileInfo)request.getAttribute("fileInfo");
		AttachInfo  info = new AttachInfo();
		info.setFileID(fInfo.getID());//
		info.setType(fInfo.getParentIDType());//文件类型
		info.setParentType(fInfo.getParentIDType());//所属指令类型
		info.setParentID(fInfo.getParentID());//所属指令ID
		info.setModuleID(fInfo.getModuleID());
		info.setCurrencyID(fInfo.getCurrencyID());
		info.setOfficeID(fInfo.getOfficeID());
		info.setNClientID(fInfo.getClientID());
		info.setTransTypeID(fInfo.getTransTypeID());
		info.setTransSubTypeID(fInfo.getTransSubTypeID());
		info.setStatus(Constant.RecordStatus.VALID);
		info.setTransCode(fInfo.getTransCode());
		
		AttachOperation attachOperation = new AttachOperation();
		attachOperation.insertOBDoc(info);
		
		String strURL = "/common/c101-c.jsp?transCode="+fInfo.getTransCode()+"&ParentID="+fInfo.getParentID()+"&ParentIDType="+fInfo.getParentIDType() + "&ModuleID=" +fInfo.getModuleID() + "&TransTypeID=" +fInfo.getTransTypeID() + "&TransSubTypeID=" +fInfo.getTransSubTypeID() + "&CurrencyID=" +fInfo.getCurrencyID() + "&OfficeID=" +fInfo.getOfficeID()+"&ClientID="+fInfo.getClientID();

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strURL);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out, sessionMng, ie, "客户管理","",1);
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			