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
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
		long ParentID = -1;
		long ParentIDType = -1;
		long lFileID = -1;
		
		//ȡ��������
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

		/* ��ȡ�����Ļ��� */
		//ServletContext sc = getServletContext();
	

		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	
		/* forward�����ҳ�� */
		rd.forward(request, response);
		//return;
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"�ͻ�ѡ��","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			