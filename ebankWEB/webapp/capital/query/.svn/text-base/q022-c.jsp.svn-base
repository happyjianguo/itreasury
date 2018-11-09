<%--
  页面名称 ：c201.jsp
  页面功能 : 利息费用匡算 - 查询条件(v201)流转控制。
  作    者 ：xr li
  日    期 ：2003-11-24
  特殊说明 ：处理规则：
             1.
  修改历史 ： 
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML,
                 com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo"%>
				 
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.*"%>				 
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.SessionMng"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.settlement.interest.bizlogic.InterestEstimate"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QInterestEstimate"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QLoanNotice"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="InterestEstimate" class="com.iss.itreasury.settlement.interest.bizlogic.InterestEstimate" />
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	
	
    try
	{
		/* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
            out.flush();
            return;
        }
		
		 //定义变量
		String strAccountNoFrom = "";
		String strAccountNoTo = "";
		java.sql.Timestamp tsClearInterestDate = null;
		String strClientNoFrom = "";
		String strClientNoTo = "";
		long lConsignerIDFrom = -1;
		long lConsignerIDTo = -1;
		String strConsignerFrom = "";
		String strConsignerTo = "";
		long lConsignLoanSort = -1;
		String strContractNoFrom = "";
		String strContractNoTo = "";
		long lCurrencyID = -1;
		java.sql.Timestamp tsDateFrom = null;
		java.sql.Timestamp tsDateTo = null;
		long lFeeType = -1;
		long lIsSelectCircleLoan = -1;
		long lIsSelectClearInterestDate = -1;
		long lIsSelectClientNo = -1;
		long lIsSelectCompoundInterest = -1;
		long lIsSelectConsigner = -1;
		long lIsSelectConsignLoanSort = -1;
		long lIsSelectContractNo = -1;
		long lIsSelectForfeitInterest = -1;
		long lIsSelectInterest = -1;
		long lIsSelectPayFormNo = -1;
		long lIsSelectSelfLoanSort = -1;
		long lIsSelectSelfLoanTerm = -1;
		long lIsSelectShortLoan = -1;
		long lIsSelectUnClearInterest = -1;
		long lNoticeDays = -1;
		long lOfficeID = -1;
		String strPayFormNoFrom = "";
		String strPayFormNoTo = "";
		double dRate = 0.0;
		long lSelfLoanSort = -1;
		long lSelfLoanTermFrom = -1;
		long lSelfLoanTermTo = -1;
		//读取数据		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		//key
		String key = (String)request.getAttribute("_pageLoaderKey");
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("lAccountIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAccountNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lAccountIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAccountNoTo = strTemp;
		}
		strTemp = (String)request.getAttribute("strClearInterestDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsClearInterestDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lClientIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strClientNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lClientIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strClientNoTo = strTemp;
		}
		
		strTemp = (String)request.getAttribute("lConsignerIDFrom");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lConsignerIDFrom = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConsignerIDTo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		   lConsignerIDTo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConsignerIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strConsignerFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lConsignerIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strConsignerTo = strTemp;
		}
		strTemp = (String)request.getAttribute("lConsignLoanSort");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lConsignLoanSort = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lContractIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strContractNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lContractIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strContractNoTo = strTemp;
		}
		strTemp = (String)request.getAttribute("lCurrencyID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lCurrencyID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strNotifyStartDateFrom");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsDateFrom = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strNotifyStartDateTo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsDateTo = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lFeeType");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lFeeType = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectCircleLoan");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectCircleLoan = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectClearInterestDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectClearInterestDate = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectClientNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectClientNo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectCompoundInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectCompoundInterest = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectConsigner");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectConsigner = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectConsignLoanSort");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectConsignLoanSort = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectContractNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectContractNo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectForfeitInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectForfeitInterest = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectInterest = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectPayFormNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectPayFormNo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectSelfLoanSort");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectSelfLoanSort = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectSelfLoanTerm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectSelfLoanTerm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectShortLoan");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectShortLoan = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lIsSelectUnClearInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lIsSelectUnClearInterest = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNoticeDays");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNoticeDays = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayFormIDFromCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayFormNoFrom = strTemp;
		}
		strTemp = (String)request.getAttribute("lPayFormIDToCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strPayFormNoTo = strTemp;
		}
		strTemp = (String)request.getAttribute("dRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("lSelfLoanSort");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSelfLoanSort = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSelfLoanTermFrom");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSelfLoanTermFrom = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSelfLoanTermTo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSelfLoanTermTo = Long.valueOf(strTemp).longValue();
		}
		
		InterestEstimateQueryInfo info = new InterestEstimateQueryInfo();
		QInterestEstimate qobj = new QInterestEstimate();
		QLoanNotice qNotice = new QLoanNotice();
		PageLoader pageLoader = null;
		
		
		info.setAccountNoFrom(strAccountNoFrom);
		info.setAccountNoTo(strAccountNoTo);
		info.setClearInterestDate(tsClearInterestDate);
		
		info.setClientNoFrom(sessionMng.m_strClientCode);  //查询条件只限制为"成员单位"
		info.setClientNoTo(sessionMng.m_strClientCode);
		
		info.setConsignerFrom(strConsignerFrom);
		info.setConsignerTo(strConsignerTo);
		info.setConsignerIDFrom(lConsignerIDFrom);
		info.setConsignerIDTo(lConsignerIDTo);
		info.setConsignLoanSort(lConsignLoanSort);
		info.setContractNoFrom(strContractNoFrom);
		info.setContractNoTo(strContractNoTo);
		info.setCurrencyID(lCurrencyID);
		info.setDateFrom(tsDateFrom);
		info.setDateTo(tsDateTo);
		info.setFeeType(lFeeType);
		info.setIsSelectCircleLoan(lIsSelectCircleLoan);
		
		info.setIsSelectClearInterestDate(1);  //查询条件包含"结息日期"
		info.setIsSelectClientNo(1);  //查询条件只限制为"成员单位"
		
		info.setIsSelectCompoundInterest(lIsSelectCompoundInterest);
		info.setIsSelectConsigner(lIsSelectConsigner);
		info.setIsSelectConsignLoanSort(lIsSelectConsignLoanSort);
		info.setIsSelectContractNo(lIsSelectContractNo);
		info.setIsSelectForfeitInterest(lIsSelectForfeitInterest);
		info.setIsSelectInterest(lIsSelectInterest);
		info.setIsSelectPayFormNo(lIsSelectPayFormNo);
		info.setIsSelectSelfLoanSort(lIsSelectSelfLoanSort);
		info.setIsSelectSelfLoanTerm(lIsSelectSelfLoanTerm);
		info.setIsSelectShortLoan(lIsSelectShortLoan);
		info.setIsSelectUnClearInterest(lIsSelectUnClearInterest);
		info.setNoticeDays(lNoticeDays);
		info.setOfficeID(lOfficeID);
		info.setPayFormNoFrom(strPayFormNoFrom);
		info.setPayFormNoTo(strPayFormNoTo);
		info.setRate(dRate);
		info.setSelfLoanSort(lSelfLoanSort);
		info.setSelfLoanTermFrom(lSelfLoanTermFrom);
		info.setSelfLoanTermTo(lSelfLoanTermTo);
		info.setOfficeID(sessionMng.m_lOfficeID);
		info.setCurrencyID(sessionMng.m_lCurrencyID);
		
		/*if("searchAccount".equals(strAction))
		{		
			//查询数据
			pageLoader = qobj.queryInterestEstimateAccountInfo(info);
			key = sessionMng.setPageLoader(pageLoader);			
			sessionMng.setQueryCondition(key,info);
			//取合计值
			InterestEstimateQueryResultInfo interestSumInfo = qobj.queryInterestSum(info);
			sessionMng.setSumResult(key,interestSumInfo);
			request.setAttribute("_pageLoaderKey",key);
		}
		else if("searchClient".equals(strAction))
		{		
			//查询数据
			pageLoader = qobj.queryInterestEstimateClientInfo(info);
			key = sessionMng.setPageLoader(pageLoader);
			sessionMng.setQueryCondition(key,info);
			//取合计值
			InterestEstimateQueryResultInfo interestSumInfo = qobj.queryInterestSum(info);
			sessionMng.setSumResult(key,interestSumInfo);
			request.setAttribute("_pageLoaderKey",key);
		}
		else */if("printInterestEstimate".equals(strAction))
		{
			session.setAttribute("queryInfo",info);
			strActionResult = Constant.ActionResult.SUCCESS;
		
			//查询数据
			InterestEstimate interestE = new InterestEstimate();
			InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
			qInfo = (InterestEstimateQueryInfo)session.getAttribute("queryInfo");
			Collection resultColl=null;
			resultColl=interestE.findEstimateRecords(null,qInfo);			
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("searchResults",resultColl);
		}
		/*else if("printNoticeEstimate".equals(strAction))
		{
			session.setAttribute("queryInfo",info);
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else if("interestEstimate".equals(strAction))
		{
			//查询数据
			InterestEstimate interestE = new InterestEstimate();
			InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
			qInfo = (InterestEstimateQueryInfo)session.getAttribute("queryInfo");
			Collection resultColl=null;
			resultColl=interestE.findEstimateRecords(null,qInfo);			
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("searchResults",resultColl);
		}
		else if("noticeEstimate".equals(strAction))
		{		
			//查询数据
			InterestEstimate interestE = new InterestEstimate();
			InterestEstimateQueryInfo qInfo = new InterestEstimateQueryInfo();
			qInfo = (InterestEstimateQueryInfo)session.getAttribute("queryInfo");
			Collection resultColl=null;
			resultColl=interestE.findNoticeEstimateRecords(null,qInfo);
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("searchResults",resultColl);
		}
		else if("LoanMatureNotice".equals(strAction))
		{	
			info.setNoticetype(SETTConstant.LoanNoticeType.LoanMatureNotice);	
			//查询数据
			pageLoader = qNotice.queryLoanMatureNotice(info);
			key = sessionMng.setPageLoader(pageLoader);
			request.setAttribute("_pageLoaderKey",key);
			sessionMng.setQueryCondition(key,info);
			strActionResult = Constant.ActionResult.SUCCESS;
			
		}
		else if("LoanDunNotice".equals(strAction))
		{	
			info.setNoticetype(SETTConstant.LoanNoticeType.LoanDunNotice);		
			//查询数据
			pageLoader = qNotice.queryLoanDunNotice(info);
			key = sessionMng.setPageLoader(pageLoader);
			request.setAttribute("_pageLoaderKey",key);
			sessionMng.setQueryCondition(key,info);
			strActionResult = Constant.ActionResult.SUCCESS;
		}*/

	//业务处理和流程控制
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
	}
	
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~10");		
	request.setAttribute("strActionResult",strActionResult);	
	//根据处理结果设置下一步跳转的目标页面
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~11");		
	strNextPageURL = "/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;	
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~12");				
	Log.print("Next Page URL:"+strNextPageURL);
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~13");			

	
	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~14");		
	rd.forward( request,response);
	//Log.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~15");		
	
%>