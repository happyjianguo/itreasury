<!--
/*
 * 程序名称：ck006-c.jsp
 * 功能说明：复核匹配业务选择控制页面
 * 作　　者：刘琰
 * 完成日期：2004年02月10日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   javax.servlet.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
%>

<%
	/* 用户登录检测与权限校验 */
	long lTransType = -1; //交易类型
	long lInstructionID = -1; //交易ID
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
        System.out.println("-------------------------ck 006----------------------------------");
%>

<!--Access DB start-->
<%
		lInstructionID = GetNumParam(request,"lInstructionID");
		
		/* 实例化信息类 */
		PayerOrPayeeInfo payerOrPayeeInfo = null;
		FinanceInfo financeInfo = new FinanceInfo();
		
		/* 初始化EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();		
		if(lInstructionID>0)
		{
			financeInfo = financeInstr.findByID(lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
			
		}
		
		if( (request.getParameter("SelectType") != null) && (request.getParameter("SelectType").length()>0) )
		{			
			lTransType = GetNumParam(request,"SelectType");
		}

		else if (lInstructionID>0)
		{
			if ( financeInfo.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_OTHER )
			{
				lTransType = 1;
			}
			else
			lTransType = OBConstant.getQueryTypeByTransType( financeInfo.getTransType() );
		}
		
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT || lTransType == OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
		if(lTransType == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT || lTransType == OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW)

		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
		}
		if(lTransType == OBConstant.QueryInstrType.TRUSTLOANRECEIVE || lTransType == OBConstant.QueryInstrType.YTLOANRECEIVE)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.TRUST);
		}
		if(lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.CONSIGN);
		}
		if(lTransType == OBConstant.QueryInstrType.DRIVEFIXDEPOSIT)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
		if(lTransType == OBConstant.QueryInstrType.CHANGEFIXDEPOSIT)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
%>
<!--Access DB end-->

<!--Forward start-->
<%	
		/* 在请求中保存结果对象 */
	    request.setAttribute("lTransType", Long.toString(lTransType) );
		request.setAttribute("payerOrPayeeInfo", payerOrPayeeInfo );
		/*
		//违反录入匹配需求
		if(lInstructionID>0)
		{
			request.setAttribute("financeInfo", financeInfo );	
		}
		*/
		
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		/* 设置返回地址 */
	   	RequestDispatcher rd = null;
		Log.print("---*******lTransType="+lTransType);
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		
		switch((int)lTransType)
		{
			case (int)OBConstant.QueryInstrType.CAPTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck002-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck002-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				
				request.setAttribute("child", "1" );
				break;
			case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck004-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck004-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck008-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT://定期续存
				pageControllerInfo.setUrl(strContext + "/capital/check/ck010-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT://定期转存
				pageControllerInfo.setUrl(strContext + "/capital/check/ck011-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;		
		}
		
		Log.print("-----rd="+rd.toString());		
		/* forward到结果页面 */
	    rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("---------"+ie.toString());
		return;
	}	
	
%>

