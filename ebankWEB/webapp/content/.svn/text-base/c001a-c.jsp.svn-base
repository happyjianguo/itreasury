<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
com.iss.itreasury.loan.contractcontent.bizlogic.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	long lContentID = -1; //合同文本的ID
	String PageName = "";
	long PageNo = 1;
	long lAction = 1;
	
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
		strTemp = (String)request.getAttribute("lContentID");
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
		
		strTemp = (String)request.getAttribute("PageName");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PageName = strTemp;
			}
			catch (Exception e)
			{
				PageName = "";
			}
		}
		
		strTemp = (String)request.getAttribute("PageNo");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PageNo = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				PageNo = 1;
			}
		}
		
		strTemp = (String)request.getAttribute("lAction");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lAction = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lAction = 1;
			}
		}
		

		ContractContentOperation operation = new ContractContentOperation();
		String sContent = "";
		sContent = operation.getContractContent(lContentID, PageNo);     
		request.setAttribute("sContent", sContent);  
		request.setAttribute("lContentID", String.valueOf(lContentID));  
		request.setAttribute("lAction", String.valueOf(lAction));  
			 		

		/* 获取上下文环境 */
		//ServletContext sc = getServletContext();
	
		/* 设置返回地址 */		
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(PageName);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
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

			