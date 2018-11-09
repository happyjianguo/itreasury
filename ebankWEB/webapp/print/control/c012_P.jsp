<%
/**
 * 页面名称 ：c012_p.jsp
 * 页面功能 : 交易记录打印明细查询——控制页面
 * 作    者 ：Boxu
 * 日    期 ：2006-12-15
 * 特殊说明 ：
 *			
 * 修改历史 ：
 */
%>

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
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.ebank.print.dataentity.PrintrecordInfo"%>
<%@ page import="com.iss.itreasury.ebank.print.bizlogic.EbankPrintApplyBiz"%>
<%@ page import="com.iss.itreasury.ebank.print.dao.EbankQTransaction"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page session="true"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% String strContext = request.getContextPath();%>
<%
	/* 标题固定变量 */
	String strTitle = "[回单打印申请]";
%>
<%
	//页面控制变量
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	
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
		
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		//读取数据
		String strTemp = "";
		long lID = -1;
		long TransactionTypeID = -1;
		String TransNo = "";
		String payaccountno = "";
		Collection coll = null;
		
		strTemp = (String)request.getAttribute("strAction");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			strAction = strTemp;
		}
		System.out.println("strAction===="+strAction);
		strTemp = (String)request.getAttribute("lID");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			lID = Long.parseLong(strTemp);
		}
		System.out.println("lID===="+lID);
		strTemp = (String)request.getAttribute("TransactionTypeID");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			TransactionTypeID = Long.parseLong(strTemp);
		}
		System.out.println("TransactionTypeID===="+TransactionTypeID);
		strTemp = (String)request.getAttribute("TransNo");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			TransNo = strTemp;;
		}
		System.out.println("TransNo===="+TransNo);
		strTemp = (String)request.getAttribute("payaccountno");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			payaccountno = strTemp;
		}
		System.out.println("payaccountno===="+payaccountno);
		
		//回显分页
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		if ( strPageLoaderKey != null )
		{
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		}
		
		//查询打印记录信息开始
		if ( strAction.equals("toModify") )
		{
			EbankPrintApplyBiz papplybiz = new EbankPrintApplyBiz();
			QueryTransactionInfo queryinfo = new QueryTransactionInfo();
			queryinfo.setID(lID);
			queryinfo.setTransactionTypeID(TransactionTypeID);
			queryinfo.setTransNo(TransNo);
			queryinfo.setPayAccountNo(payaccountno);
			request.setAttribute("QueryTransaction",queryinfo);
			
			//查询打印信息
			PrintrecordInfo prinfo = new PrintrecordInfo();
			prinfo.setNclientid(sessionMng.m_lClientID);
			prinfo.setNofficeid(sessionMng.m_lOfficeID);
			prinfo.setNcurrency(sessionMng.m_lCurrencyID);
			prinfo.setNtransactiontypeid(TransactionTypeID);
			prinfo.setNprintcontentid(lID);
			prinfo.setNdeptid(VOUConstant.PrintSection.EBANKCUSTOMER);
			
			//boxu update 查询可以申请打印的数据需要传递"办事处"和"币种"
			coll = papplybiz.getPrintDetailByTransID(lID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			
			request.setAttribute("printresult",coll);
			
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
		{
			strActionResult = Constant.ActionResult.FAIL;
		}
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = strActionResult.equals(Constant.ActionResult.SUCCESS) ? strSuccessPageURL:strFailPageURL;
	System.out.println("strNextPageURL :  " + strNextPageURL);

	//转向下一页面
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>