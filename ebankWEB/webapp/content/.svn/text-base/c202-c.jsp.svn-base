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
com.iss.itreasury.loan.contractcontent.bizlogic.*,
com.iss.itreasury.loan.contractcontent.dataentity.*,
com.iss.itreasury.ebank.obcontent.dataentity.OBContentInfo,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%String strContext = request.getContextPath();%>

<%
	long lParentID = -1;
	long lContentID = -1; //合同文本的ID
	String PageName = "";
	String prePageName = "";
	long PageNo = 1;
	String strYearNo = "";
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
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lParentID = Long.parseLong(strTemp.trim());
			}
			catch (Exception e)
			{
				lParentID = -1;
			}
		}
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
		
		strTemp = (String)request.getAttribute("prePageName");
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
		strTemp = (String)request.getAttribute("yearNo");
		if (strTemp != null && !strTemp.equals(""))
		{
			strYearNo = strTemp;
		}
		
		strTemp = (String)request.getAttribute("SUBMIT");
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
		
		String strContent = ""+lParentID+OBContentDao.CONTENT_SEPERATOR;
		String arrContent[] = null;
		if ( request.getParameterValues("textfield") != null )
		{
			arrContent = request.getParameterValues("textfield");
			int nLen = arrContent.length;
					
			for (int i = 0; i < nLen; i++)
			{
				if (i < nLen - 1)
				{
					strContent = strContent + DataFormat.toChinese(arrContent[i]) + OBContentDao.CONTENT_SEPERATOR;
				}
				else
				{
					strContent = strContent + DataFormat.toChinese(arrContent[i])+ OBContentDao.CONTENT_SEPERATOR+lParentID;
				}
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
		
		if (bIsSave)
		{
			ContractContentOperation operation = new ContractContentOperation();
			if(lContentID > 0)//修改
			{
				operation.putContractContent(lContentID,lThisNo,strContent);
			}
			else if(lParentID > 0 && lContentID <= 0)//新增
			{
				//ContractContentDao dao = new ContractContentDao();
				String ss = operation.addClientContent(lParentID);
				ContractContentInfo info = new ContractContentInfo();
				info.setParentID(lParentID);
				info.setContractID(-1);
				info.setContractTypeID(8);
				info.setDocName(ss);
				info.setCode(strYearNo);

				//lContentID=dao.saveContractContent(info);
				lContentID=operation.saveContractContent(info);

				operation.putContractContent(lContentID,1,strContent);
			}
		}
		
		if (!sName.equals(""))
		{//*
			if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				try
				{
					sContent = operation.getContractContent(lContentID,PageNo);
				}
				catch(IException e)
				{
					//LOANHTML.showExceptionMessage(out, sessionMng, e, request, response, "财务情况统计表", Constant.RecordStatus.VALID);
					OBHtml.showExceptionMessage(out,sessionMng,e,"资产负债表","", Constant.RecordStatus.VALID); 
					//e.printStackTrace();
					out.flush();
					return;
				}
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));

				/* 获取上下文环境 */
				//ServletContext sc = getServletContext();
	
				/* 设置返回地址 */
				sName="c211-v.jsp";
				String sURL = "/content/c201-c.jsp?lClientID="+lParentID+"&PageName="+sName+"&PageNo=1";
				
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + sURL);
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
				/* forward到结果页面 */
				rd.forward(request, response);
				//return;
			}
			else
			{//*/
				sName="c211-v.jsp";
				String sURL = "/content/c201-c.jsp?lClientID="+lParentID+"&PageName="+sName+"&PageNo=1";
				/* 获取上下文环境 */
				//ServletContext sc = getServletContext();
		
				//构建页面分发时需要用到的实体
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + sURL);
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		
				/* forward到结果页面 */
				rd.forward(request, response);
				//return;
			}
		}
		else
		{
			if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				sContent = operation.getContractContent(lContentID,PageNo);
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));
				request.setAttribute("yearNo",strYearNo);

				/* 获取上下文环境 */
				//ServletContext sc = getServletContext();
	
				/* 设置返回地址 */
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl(strContext + PageName);
				//分发
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
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
<%			}
		}
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"资产负债表","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return;
	}
%>

			