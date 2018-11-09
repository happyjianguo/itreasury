<%@page contentType="text/html;charset=gbk"%>

<%@page import="
com.iss.itreasury.ebank.obcontent.bizlogic.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	long lContentID = -1; //合同文本的ID
	String control="";
	String PageName = "";
	long PageNo = 1;
	long clientID=-1;
	
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
				lContentID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lContentID = -1;
			}
		}
		strTemp = request.getParameter("ClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				clientID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				clientID = -1;
			}
		}
		strTemp = request.getParameter("control");
		if (strTemp != null && !strTemp.equals(""))
		{
			control=strTemp;
		}
		
		if (clientID<=0 && lContentID<=0 )
		{
%>
<html>
<body>
	<script language="javascript">
		alert("参数有误");
		window.close();
	</script>
</body>
</html>	

<%
			return;
		}		
		OBContentOperation operation = new OBContentOperation();
		long ret=-1;
		if ( clientID>0 )
		{
			//ret=operation.findLoanQuestionaryByClient(clientID);			
			if (ret==-1)
			{
%>
<html>
<body>
	<script language="javascript">
		alert("没有填写过贷款调查表");
		window.close();
	</script>
</body>
</html>	
<%		
				return;
			}	
		}
		else
		{
			ret=operation.findLoanQuestionaryID(lContentID);
			if ( ret==-1 )
			{
				ret=operation.addLoanQuestionary(lContentID);		
				System.out.println("========="+ret);
			}	
			ret=operation.findLoanQuestionaryID(lContentID);
		}
		
		request.setAttribute("lContentID",String.valueOf(ret));
		request.setAttribute("PageName","/content/c101-v.jsp");
		request.setAttribute("PageNo","1");
		request.setAttribute("control",control);
		request.setAttribute("ClientID",String.valueOf(clientID));
		
		String strURL="/content/c001-c.jsp";
		/* 获取上下文环境 */
		//ServletContext sc = getServletContext();
	
		/* 设置返回地址 */
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strURL);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */
		rd.forward(request, response);
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

