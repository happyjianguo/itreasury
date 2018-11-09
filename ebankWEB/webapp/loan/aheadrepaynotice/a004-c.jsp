<%@page contentType="text/html;charset=gbk"%>
<%
    //response.setHeader("Cache-Control","no-stored");
	//response.setHeader("Pragma","no-cache");
	//response.setDateHeader("Expires",0);
%>
<%@page import="
com.iss.itreasury.ebank.obaheadrepaynotice.bizlogic.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.AheadRepayNoticeInfo,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*
" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[提前还款通知单]";
%>
<%String strContext = request.getContextPath();%>
<%
	long ContractID = -1; //合同的ID
	long PayID = -1;//放款通知单ID
	double dAmount = 0;//提前还款金额
	long lID = -1;
	long lIsAhead=-1;

	try
	{
		/**
		* isLogin start
		*/
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		//取参数变量
		String strTemp = "";
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lID = -1;
			}
		}

		strTemp = (String)request.getAttribute("ContractID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				ContractID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				ContractID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("PayID");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				PayID = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				PayID = -1;
			}
		}
		
		strTemp = (String)request.getAttribute("dAheadRepayAmount");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				dAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp));
			}
			catch (Exception e)
			{
				dAmount = 0;
			}
		}
		strTemp = (String)request.getAttribute("nIsAhead");
		if (strTemp != null && !strTemp.equals(""))
		{
			try
			{
				lIsAhead = Long.parseLong(strTemp);
			}
			catch (Exception e)
			{
				lIsAhead = -1;
			}
		}
		
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		
		//info.setOfficeID(sessionMng.m_lOfficeID);
		//info.setCurrencyID(sessionMng.m_lCurrencyID);
		info.setContractID(ContractID);//合同ID
		info.setLetoutNoticeID(PayID);//放款通知单ID
		info.setAmount(dAmount);
		info.setInputUserID(sessionMng.m_lUserID);
		info.setIsAhead(lIsAhead);
		//表明需要新增或更新
		info.setID(lID);

		OBAheadRepayNoticeHome aheadRepayNoticeHome = (OBAheadRepayNoticeHome)EJBObject.getEJBHome("OBAheadRepayNoticeHome");
		OBAheadRepayNotice aheadRepayNotice = aheadRepayNoticeHome.create();
		
		lID = aheadRepayNotice.saveAheadRepayNotice(info);

		//info = aheadRepayNotice.findAheadRepayNoticeByID(lID);

		request.setAttribute("lID", String.valueOf(lID));  
		request.setAttribute("action", "view");  		
		long lAction = 1;
		request.setAttribute("actionControl", String.valueOf(lAction));  

		/* 获取上下文环境 */
		ServletContext sc = getServletContext();
	
		/* 设置返回地址 */

		
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "/loan/aheadrepaynotice/a002-c.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
		/* forward到结果页面 */ 
		rd.forward(request, response);
		//return;
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

			