<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
com.iss.itreasury.ebank.obcontent.bizlogic.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.ebank.obcontent.dao.OBContentDao,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
	long lContentID = -1; //合同文本的ID
	String PageName = "";
	String prePageName = "";
	long PageNo = 1;
	long lAction = 1;
	String sAction = "";
	
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
		strTemp = request.getParameter("lContentID");
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
		
		strTemp = request.getParameter("PageName");
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
		
		strTemp = request.getParameter("prePageName");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				prePageName = strTemp;
			}
			catch (Exception e)
			{
				prePageName = "";
			}
		}
		
		strTemp = request.getParameter("PageNo");
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
		
		strTemp = request.getParameter("lAction");
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
		
		strTemp = request.getParameter("SUBMIT");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				sAction = strTemp;
			}
			catch (Exception e)
			{
				sAction = "";
			}
		}
		
		long lThisNo = PageNo;
		boolean bIsSave = false;
		
		String strContent = "";
		String arrContent[] = null;
		if ( request.getParameterValues("textfield") != null )
		{
			arrContent = request.getParameterValues("textfield");
			int nLen = arrContent.length;
			
			if ( nLen > 1 )
			{		
				for (int i = 0; i < nLen; i++)
				{
					if (i < nLen - 1)
					{
						strContent = strContent + DataFormat.toChinese(arrContent[i]) + OBContentDao.CONTENT_SEPERATOR;
					}
					else
					{
						strContent = strContent + DataFormat.toChinese(arrContent[i]);
					}
				}
			}
			else
			{
				strContent = DataFormat.toChinese(arrContent[0]) + OBContentDao.CONTENT_SEPERATOR;
			}
		}
		else
		{
			bIsSave = false;
		}
		
		String sName = "";
		if (sAction.equals("presave"))
		{
			bIsSave = true;
			sName = prePageName;
			PageNo = PageNo - 1;
		}
		else if (sAction.equals("pre"))
		{
			bIsSave = false;
			sName = prePageName;
			PageNo = PageNo - 1;
		}
		else if (sAction.equals("close"))
		{
			bIsSave = true;
			sName = "";
		}
		else if (sAction.equals("save"))
		{
			bIsSave = true;
			sName = PageName;
			PageNo = PageNo + 1;
		}
		else if (sAction.equals("view"))
		{
			bIsSave = false;
			sName = PageName;
			PageNo = PageNo + 1;
		}
	
		if (bIsSave)
		{
			OBContentOperation operation = new OBContentOperation();
			operation.putOBContent(lContentID, lThisNo,strContent);    
		} 
		
		if (!sName.equals(""))
		{
			request.setAttribute("lContentID",String.valueOf(lContentID));
			request.setAttribute("PageNo",String.valueOf(PageNo) );
			request.setAttribute("lAction",String.valueOf(lAction) );
			request.setAttribute("PageName",sName );
			
			String sURL = "/content/c001-c.jsp";
			/* 获取上下文环境 */
			//ServletContext sc = getServletContext();
		
			/* 设置返回地址 */
			
		//构建页面分发时需要用到的实体
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext + sURL);
		//分发
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
			/* forward到结果页面 */
			rd.forward(request, response);
			//return;
		}
		else
		{
%>
<SCRIPT language=javascript>
	window.close();
</SCRIPT>
<%			
		}
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"客户选择","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			