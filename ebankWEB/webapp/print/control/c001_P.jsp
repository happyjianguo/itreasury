<%--
/**
 * 页面名称 ：c001_p.jsp
 * 页面功能 : 交易记录查询——控制页面
 * 作    者 ：Boxu
 * 日    期 ：2006-8-16
 * 特殊说明 ：
 *			
 * 修改历史 ：
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QTransaction"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.print.dao.EbankQTransaction"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page session="true"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%String strContext = request.getContextPath();%>
<%
	/* 标题固定变量 */
	String strTitle = "[电子回单打印]";
%>
<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	String strContinueSave = "";

    try
	{
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
		
        //定义变量
		long lOfficeID = -1;//办事处
		long lCurrencyID = sessionMng.m_lCurrencyID;//币种
		long lTransactionTypeID = -1;//业务类型
		long lAccountTransTypeID = -1;//帐户交易类型
		long lPayClientIDStart = -1;//付款方客户ID（由）
		String strPayClientNoStart = "";//付款方客户编号（由）
		long lPayClientIDEnd = -1;//付款方客户ID（至）
		String strPayClientNoEnd = "";//付款方客户编号（至）
		long lPayAccountIDStart = -1;//付款方帐户（由）
		String strPayAccountNoStart = "";//付款方帐户号（由）
		long lPayAccountIDEnd = -1;//付款方帐户（至）
		String strPayAccountNoEnd = "";//付款方帐户号（至）
		double dPayAmountStart = 0.0;//付款方金额（由）
		double dPayAmountEnd = 0.0;//付款方金额（至）
		long lReceiveClientIDStart = -1;//收款方客户ID（由）
		String strReceiveClientNoStart = "";//收款方客户编号（由）
		long lReceiveClientIDEnd = -1;//收款方客户ID（至）
		String strReceiveClientNoEnd = "";//收款方客户编号（至）
		long lReceiveAccountIDStart = -1;//收款方帐户（由）
		String strReceiveAccountNoStart = "";//收款方帐户号（由）
		long lReceiveAccountIDEnd = -1;//收款方帐户（至）
		String strReceiveAccountNoEnd = "";//收款方帐户号（至）
		double dReceiveAmountStart = 0.0;//收款方金额（由）
		double dReceiveAmountEnd = 0.0;//收款方金额（至）
		long lBankID = -1;//开户行
		String strTransNoStart = "";//交易号（由）
		String strTransNoEnd = "";//交易号（至）
		Timestamp tsInterestStartStart = null;//起息日（由）
		Timestamp tsInterestStartEnd = null;//起息日（至）
		Timestamp tsExecuteStart = null;//执行日（由）
		Timestamp tsExecuteEnd = null;//执行日（至）
		long lStatusID = -1;//交易纪录状态
		long lInputuserID = -1;//录入人
		long lCheckuserID = -1;//复核人
		long lQueryType = -1;//查询类型
		long lOrderID = -1;//排序类型ID
		long lDESC = -1;//升降序ID
		long lPageCount = -1;//每页纪录条数
		long lEyeCheckQuery = -1;//查询类型,区别目视复合查询与其他查询
		String str200="";	//定期，通知开立
		String strTransactionType = "";//业务类型字符串
		String isEyeCheck="0";	//是否是目视复核 1 -- 是，0 --不是
		Timestamp systemDate = Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID); //系统开机日
		if(request.getParameter("isEyeCheck")!=null && request.getParameter("isEyeCheck").equals("1"))
		{
			isEyeCheck = request.getParameter("isEyeCheck");
		}
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		
		//读取数据
		String strTemp = null;
		strTemp = (String)request.getAttribute("lAccountTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountTransTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBranchID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lCheckuserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("tsExecuteEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteEnd = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsExecuteStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lInputuserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("tsInterestEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestStartEnd = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("tsInterestStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestStartStart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lPayAccountIDEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayAccountIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayAccountIDStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayAccountIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayAccountIDEndCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			if(strTemp.trim().length() > 8)
			{
		    	strPayAccountNoEnd = strTemp;
			}
		}
		strTemp = (String)request.getAttribute("lPayAccountIDStartCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			if(strTemp.trim().length() > 8)
			{
		    	strPayAccountNoStart = strTemp;
			}
		}
		strTemp = (String)request.getAttribute("dPayMoneyEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dPayAmountEnd = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("dPayMoneyStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dPayAmountStart = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("lPayClientIDEndCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayClientNoEnd = strTemp;
		}
		strTemp = (String)request.getAttribute("lPayClientIDEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayClientIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayClientIDStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lPayClientIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayClientIDStartCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayClientNoStart = strTemp;
		}
		strTemp = (String)request.getAttribute("lReceiveAccountIDEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveAccountIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveAccountIDStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveAccountIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveAccountIDEndCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			if(strTemp.trim().length() > 8)
			{
		    	strReceiveAccountNoEnd = strTemp;
			}
		}
		strTemp = (String)request.getAttribute("lReceiveAccountIDStartCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			if(strTemp.trim().length() > 8)
			{
		    	strReceiveAccountNoStart = strTemp;
			}
		}
		strTemp = (String)request.getAttribute("dReceiveMoneyEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dReceiveAmountEnd = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("dReceiveMoneyStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dReceiveAmountStart = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
		}
		strTemp = (String)request.getAttribute("lReceiveClientIDEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveClientIDEnd = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveClientIDStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lReceiveClientIDStart = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveClientIDEndCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strReceiveClientNoEnd = strTemp;
		}
		strTemp = (String)request.getAttribute("lReceiveClientIDStartCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strReceiveClientNoStart = strTemp;
		}
		strTemp = (String)request.getAttribute("lTransactionStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strTransactionNoEnd");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strTransNoEnd = strTemp;
		}
		strTemp = (String)request.getAttribute("strTransactionNoStart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strTransNoStart = strTemp;
		}

		strTemp = (String)request.getAttribute("lQueryType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lQueryType = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("str200");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    str200 = strTemp;
		}
		strTemp = (String)request.getAttribute("strTransactionType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strTransactionType = strTemp;
		}
		strTemp = (String)request.getAttribute("lEyeCheckQuery");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lEyeCheckQuery = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("officeList");
		if(strTemp !=null && strTemp.trim().length()>0)
		{
			lOfficeID = Long.parseLong(strTemp);
		}

        QueryTransactionConditionInfo conditionInfo = null;
		QTransaction qobj = new QTransaction();
		
		//add Boxu 2006-12-12
		EbankQTransaction ebankaction = new EbankQTransaction();
		
		PageLoader pageLoader = null;
		
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");

	    if( true )
		{
			conditionInfo = new QueryTransactionConditionInfo();
			conditionInfo.setQueryType(lQueryType);

			if(str200.equals("true"))  //如果 定期，通知开立为真
			{	
				conditionInfo.setQueryType(200);
			}
			
			conditionInfo.setAccountTransTypeID(lAccountTransTypeID);
			conditionInfo.setBankID(lBankID);
			conditionInfo.setCheckuserID(lCheckuserID);
			conditionInfo.setCurrencyID(lCurrencyID);
			conditionInfo.setExecuteEnd(tsExecuteEnd);
			conditionInfo.setExecuteStart(tsExecuteStart);
			conditionInfo.setInputuserID(lInputuserID);
			conditionInfo.setInterestStartEnd(tsInterestStartEnd);
			conditionInfo.setInterestStartStart(tsInterestStartStart);
			conditionInfo.setOfficeID(lOfficeID);
			conditionInfo.setPayAccountIDEnd(lPayAccountIDEnd);
			conditionInfo.setPayAccountIDStart(lPayAccountIDStart);
			conditionInfo.setPayAccountNoEnd(strPayAccountNoEnd);
			conditionInfo.setPayAccountNoStart(strPayAccountNoStart);
			conditionInfo.setPayAmountEnd(dPayAmountEnd);
			conditionInfo.setPayAmountStart(dPayAmountStart);
			conditionInfo.setPayClientNoEnd(strPayClientNoEnd);
			conditionInfo.setPayClientIDEnd(lPayClientIDEnd);
			
			conditionInfo.setPayClientIDStart(sessionMng.m_lClientID);  //付款方客户ID（由）
			
			conditionInfo.setPayClientNoStart(strPayClientNoStart);
			conditionInfo.setReceiveAccountIDEnd(lReceiveAccountIDEnd);
			conditionInfo.setReceiveAccountIDStart(lReceiveAccountIDStart);
			conditionInfo.setReceiveAccountNoEnd(strReceiveAccountNoEnd);
			conditionInfo.setReceiveAccountNoStart(strReceiveAccountNoStart);
			conditionInfo.setReceiveAmountEnd(dReceiveAmountEnd);
			conditionInfo.setReceiveAmountStart(dReceiveAmountStart);
			conditionInfo.setReceiveClientIDEnd(lReceiveClientIDEnd);
			
			conditionInfo.setReceiveClientIDStart(sessionMng.m_lClientID);  //收款方客户ID（由）
			
			conditionInfo.setReceiveClientNoEnd(strReceiveClientNoEnd);
			conditionInfo.setReceiveClientNoStart(strReceiveClientNoStart);
			conditionInfo.setStatusID(lStatusID);
			conditionInfo.setTransactionTypeID(lTransactionTypeID);
			conditionInfo.setTransNoEnd(strTransNoEnd);
			conditionInfo.setTransNoStart(strTransNoStart);
			conditionInfo.setDESC(lDESC);
			conditionInfo.setOrderID(lOrderID);
			conditionInfo.setPageCount(lPageCount);
			conditionInfo.setTransactionTypeIDs(strTransactionType);
			conditionInfo.setSystemDate(systemDate);
			conditionInfo.setClientId(sessionMng.m_lClientID);
			//conditionInfo.setEyeCheckQuery(lEyeCheckQuery);
			//conditionInfo.setUserID(sessionMng.m_lUserID);

			//从 session 得到 查询条件
			//if(session.getAttribute("eyeCheckQueryCondition") != null && isEyeCheck.equals("0"))
			//{
			//	conditionInfo = (QueryTransactionConditionInfo)session.getAttribute("eyeCheckQueryCondition");
			//}
			//把查询条件放到session中
			//session.setAttribute("eyeCheckQueryCondition",conditionInfo);

			pageLoader = ebankaction.queryTransactionInfo(conditionInfo,sessionMng.m_lUserID);
			
			if( strPageLoaderKey == null )
			{
				strPageLoaderKey = sessionMng.setPageLoader(pageLoader);
			}
			
			//Log.print("SSSSSSSSSSSSS");
			//double dPaySum = qobj.getPayAmountSum(conditionInfo);
			//double dReceiveSum = qobj.getReceiveAmountSum(conditionInfo);
			//long lCount = qobj.getCount(conditionInfo);
			//Log.print("EEEEEEEEEEEEEEEE");
			
			//QueryTransactionSumInfo sumInfo = new QueryTransactionSumInfo();
			//sumInfo.setPaySum(dPaySum);
			//sumInfo.setReceiveSum(dReceiveSum);
			//sumInfo.setCount(lCount);
			//sessionMng.setSumResult(strPageLoaderKey,sumInfo);
		}
		
		sessionMng.setQueryCondition(strPageLoaderKey,conditionInfo);
		request.setAttribute("_pageLoaderKey",strPageLoaderKey);
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = strContext + "/pagecontrol.jsp";
	Log.print("strNextPageURL :  " + strNextPageURL);

	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>