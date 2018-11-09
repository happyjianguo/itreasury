<!--
/*
 * 程序名称：ck007-c.jsp
 * 功能说明：复核匹配控制页面
 * 作　　者：刘琰
 * 完成日期：2004年02月10日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "java.sql.*,
				   java.util.*,
                   javax.servlet.*,
				   com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obquery.bizlogic.*,
				   com.iss.itreasury.ebank.obquery.dataentity.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*"
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
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
%>

<%
	/* 定义对表单的相应变量 */
	long lTransType = -1; //交易类型
	long lPayerAcctID = -1;//付款方账户ID
	long lRemitType = 0;// 汇款方式
	long lPayeeAcctID = -1;//收款方账户ID
	double dAmount = 0.0;// 金额
	String tsExecute = null;// 执行日
	String tsClearInterest = null; //结息日
	long lFixedDepositTime = -1; // 定期存款期限（月）
	long lNoticeDay = -1; //通知存款品种（天）
	long lInterestPayeeAcctID = -1;//利息收款方账户ID
	long lInterestRemitType = 0;// 利息汇款方式
	String strDepositNo = "";//定期（通知）存款单据号
	long lSubAccountID = -1; //子账户ID
	long lContractID = -1; //合同ID
	long lLoanNoteID = -1; //放款通知单ID
	double dRealInterest = 0.0; //实付贷款利息
	double dRealCompoundInterest = 0.0; //实付复利
	double dRealOverDueInterest = 0.0; //实付逾期罚息
	double dRealCommission = 0.0; //实付手续费
	double dRealSuretyFee = 0.0; //实付担保费dRealSuretyfee
	String strNewStartDate = null;// 续期新定期存款起始日
	long fixedInterestDeal = -1;//1为到期续存，2为到期转存
	long lInterestToAccountID = -1;//利息转至活期帐户
	String sDepsoitBillNo = "";
	long sDepositBillPeriod = -1; // 新定期续存和转存的期限（月）
	String strTemp = null;

	
	

	/* 用户登录检测与权限校验 */
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
%>

<!--Get Data start-->
<%
		long isautocontinue = -1;
		long autocontinuetype = -1;
		long autocontinueaccountid = -1;
		if(request.getAttribute("isautocontinue")!=null)
		{
			isautocontinue = 1;
			strTemp = (String)request.getAttribute("rdoInterest");
			if(strTemp !=null && strTemp.equals("1"))
			{
				autocontinuetype =  Long.parseLong((String)request.getAttribute("rdoInterest"));
			}
			else if(strTemp !=null && strTemp.equals("2"))
			{
				autocontinuetype =  Long.parseLong((String)request.getAttribute("rdoInterest"));
				strTemp = (String)request.getAttribute("lInterestToAccountID1");
				autocontinueaccountid =Long.parseLong(strTemp);
			}
		}
		else
		{
			isautocontinue = 2;
		}


		/* 从FORM表单中获取相应数据 */
		lTransType = GetNumParam(request,"SelectType"); //交易类型
		
		lPayerAcctID = GetNumParam(request,"nPayerAccountID");//付款方账户ID
		Log.print("付款方账户ID=" + lPayerAcctID);
		
		lRemitType = GetNumParam(request,"nRemitType");// 汇款方式
		Log.print("汇款方式=" + lRemitType);
		
		lPayeeAcctID = GetNumParam(request,"nPayeeAccountID");//收款方账户ID
		Log.print("收款方账户ID=" + lPayeeAcctID);
		
		if(request.getParameter("dAmount") != null && request.getParameter("dAmount").length()>0)
		{
			dAmount = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dAmount"))).doubleValue();// 金额
			Log.print("金额=" + dAmount);
		}
		if(request.getParameter("tsExecute") != null)
		{
			tsExecute =request.getParameter("tsExecute");// 执行日
			Log.print("执行日=" + tsExecute);
		}
		
		if(request.getParameter("tsClearInterest") != null)
		{
			tsClearInterest = request.getParameter("tsClearInterest");// 日
			Log.print("执行日=" + tsExecute);
		}
		
		lFixedDepositTime = GetNumParam(request,"nFixedDepositTime");//定期存款期限
		Log.print("定期存款期限=" + lFixedDepositTime);
		
		lNoticeDay =  GetNumParam(request,"nNoticeDay");//通知存款品种
		Log.print("通知存款品种=" + lNoticeDay);
		
		lInterestPayeeAcctID = GetNumParam(request,"nInterestPayeeAccountID");//利息收款方账户ID
		Log.print("利息收款方账户ID=" + lInterestPayeeAcctID);
		
		lInterestRemitType = GetNumParam(request,"nInterestRemitType");// 利息汇款方式
		lInterestRemitType = GetNumParam(request,"nInterestRemitTypeHidden");
		Log.print("利息汇款方式=" + lInterestRemitType);
		
		if( GetNumParam(request,"sFixedDepositNo") > 0 )
		{
			lSubAccountID =  GetNumParam(request,"sFixedDepositNo"); //子账户ID
			Log.print("子账户=" + lSubAccountID);
		}
		else
		{
			lSubAccountID =  GetNumParam(request,"nSubAccountID"); //子账户ID
			Log.print("子账户=" + lSubAccountID);		
		}
		
		if(request.getParameter("dRealInterest") != null && request.getParameter("dRealInterest").length()>0)
		{
			dRealInterest = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dRealInterest"))).doubleValue(); //实付贷款利息
	 	}
		if(request.getParameter("dRealCompoundInterest") != null && request.getParameter("dRealCompoundInterest").length()>0)
		{	
			dRealCompoundInterest = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dRealCompoundInterest"))).doubleValue(); //实付复利
		}
		if(request.getParameter("dRealOverDueInterest") != null && request.getParameter("dRealOverDueInterest").length()>0)
		{
			dRealOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dRealOverDueInterest"))).doubleValue(); //实付逾期罚息
		}
		if(request.getParameter("dRealCommission") != null && request.getParameter("dRealCommission").length()>0)
		{
			dRealCommission = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dRealCommission"))).doubleValue(); //实付手续费
		}
		if(request.getParameter("dRealSuretyFee") != null && request.getParameter("dRealSuretyFee").length()>0)
		{
			dRealSuretyFee = Double.valueOf(DataFormat.reverseFormatAmount((String)request.getParameter("dRealSuretyFee"))).doubleValue(); //实付担保费
		}
		
		lContractID = GetNumParam(request,"lContractID"); //合同ID
		Log.print("利息汇款方式=" + lInterestRemitType);
		
		lLoanNoteID = GetNumParam(request,"lLoanNoteID"); //放款通知单ID
		Log.print("利息汇款方式=" + lLoanNoteID);
		
		long lChildClientID = GetNumParam(request,"lClientID");
		sDepositBillPeriod = GetNumParam(request,"nFixedDepositTime1"); // 新定期续存和转存的期限（月）
		if(request.getParameter("strNewStartDate") != null)
		{
			strNewStartDate = request.getParameter("strNewStartDate");// 日

		}
		strTemp = (String)request.getAttribute("interesttype");//1为到期续存，2为到期转存
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			fixedInterestDeal = Long.valueOf(strTemp).longValue() ;
		}

		lInterestToAccountID = GetNumParam(request,"lInterestToAccountID");//利息转至活期帐户

		sDepsoitBillNo = GetParam(request,"sPayerAccountNoZoomCtrl");

		strTemp = (String)request.getAttribute("fixAccountId");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			lPayeeAcctID = Long.parseLong(strTemp);
		}
		
		strTemp = (String)request.getAttribute("notifyAccountId");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			lPayeeAcctID = Long.parseLong(strTemp);
		}
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->

<!--Set obQueryInfo Attribute start-->
<%		
		/* 实例化信息类 */
		OBQueryInfo obQueryInfo = new OBQueryInfo();
		FinanceInfo financeInfo = new FinanceInfo();
		obQueryInfo.setChildClientID(lChildClientID);
		obQueryInfo.setPayerAcctID( lPayerAcctID );
		obQueryInfo.setPayeeAcctID( lPayeeAcctID );
		obQueryInfo.setAmount( dAmount );
		obQueryInfo.setRemitType( lRemitType );
		obQueryInfo.setTransType( lTransType );
		obQueryInfo.setExecuteDate( tsExecute );
		obQueryInfo.setFixedDepositTime( lFixedDepositTime );
		obQueryInfo.setNoticeDay( lNoticeDay );
		obQueryInfo.setInterestPayeeAcctID( lInterestPayeeAcctID );
		obQueryInfo.setInterestRemitType( lInterestRemitType );
		obQueryInfo.setSubAccountID( lSubAccountID );
		obQueryInfo.setContractID( lContractID );
		obQueryInfo.setLoanNoteID( lLoanNoteID );
		obQueryInfo.setClearInterest( tsClearInterest );
		obQueryInfo.setRealInterest( dRealInterest );
		obQueryInfo.setRealCompoundInterest( dRealCompoundInterest );
		obQueryInfo.setRealOverdueInterest( dRealOverDueInterest );
		obQueryInfo.setRealCommission( dRealCommission );
		obQueryInfo.setRealSuretyFee( dRealSuretyFee );
		obQueryInfo.setLofficeid(sessionMng.m_lOfficeID);
		obQueryInfo.setClientID ( sessionMng.m_lClientID );
		obQueryInfo.setCurrencyID ( sessionMng.m_lCurrencyID );
		obQueryInfo.setOperationTypeID ( OBConstant.QueryOperationType.CHECK );
		obQueryInfo.setUserID ( sessionMng.m_lUserID );
		obQueryInfo.setSDepositBillNo(sDepsoitBillNo);
		obQueryInfo.setStrNewStartDate(strNewStartDate);
		obQueryInfo.setFixedInterestDeal(fixedInterestDeal);
		obQueryInfo.setLInterestToAccountID(lInterestToAccountID);
		obQueryInfo.setLFIXEDNEXTPERIOD(sDepositBillPeriod);
		obQueryInfo.setIsAutoContinue(isautocontinue);//是否自动转续存
		obQueryInfo.setAutocontinuetype(autocontinuetype);//自动转续存类型（本金or本息）
		obQueryInfo.setAutocontinueaccountid(autocontinueaccountid);//利息转至活期账户号	
		
		
%>
<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%		
		/* 初始化查询类 */		
		OBCapitalTransQuery obBCapitalTransQuery = new OBCapitalTransQuery();
		
		/*调用方法查询结果集*/
		Collection rs = null;
		rs = obBCapitalTransQuery.query(obQueryInfo);
		Iterator iterator = null;
		if (rs != null)
		{
			iterator = rs.iterator();
			financeInfo = (FinanceInfo)iterator.next();			
		}

%>
<!--Access DB end-->

<!--Forward start-->
<%	
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
		/* 设置返回地址 */
	    RequestDispatcher rd = null;
	    
	    PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		/* 在请求中保存结果对象 */
	    request.setAttribute("financeInfo", financeInfo);
		request.setAttribute("resultInfo", financeInfo);
		request.setAttribute("FormValue",obQueryInfo);
		request.setAttribute("lTransType",new Long(lTransType).toString());

		if((financeInfo != null) && (financeInfo.getID() >0))
		{System.out.println("lTransTypelTransType##############===-------------"+lTransType);
			switch((int) lTransType)
			{
				case (int) OBConstant.QueryInstrType.CAPTRANSFER :
					pageControllerInfo.setUrl(strContext + "/capital/captransfer/c004-v.jsp?checktype=1&signForCheck=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.CHILDCAPTRANSFER :
					pageControllerInfo.setUrl(strContext + "/capital/captransfer/c004-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					request.setAttribute("child", "1");
					break;
				case (int) OBConstant.QueryInstrType.OPENFIXDEPOSIT :
				
				
				
				
					if(lPayeeAcctID >0) {
					Sett_AccountDAO dao = new Sett_AccountDAO();
					AccountInfo accinfo = dao.findByID(lPayeeAcctID);
		            double dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
		            

					if(dMinSinglePayAmount > 0.0) {
						request.setAttribute("dMinSinglePayAmount",String.valueOf(dMinSinglePayAmount));	
					}
					if(accinfo.getAccountExtendInfo() == null) {
						request.setAttribute("lIsSoft","0");	
					}
					else {
						if(accinfo.getAccountExtendInfo().getIsSoft() > 0) {
							request.setAttribute("lIsSoft",String.valueOf(accinfo.getAccountExtendInfo().getIsSoft()));	
						}
					}
				}
				
					
					
					
					pageControllerInfo.setUrl(strContext + "/capital/fixed/f004-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER :	
					pageControllerInfo.setUrl(strContext + "/capital/fixed/f014-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.OPENNOTIFYACCOUNT :
				
				
				
									if(lPayeeAcctID >0) {
					Sett_AccountDAO dao = new Sett_AccountDAO();
					AccountInfo accinfo = dao.findByID(lPayeeAcctID);
		            double dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
		            

					if(dMinSinglePayAmount > 0.0) {
						request.setAttribute("dMinSinglePayAmount",String.valueOf(dMinSinglePayAmount));	
					}
					if(accinfo.getAccountExtendInfo() == null) {
						request.setAttribute("lIsSoft","0");	
					}
					else {
						if(accinfo.getAccountExtendInfo().getIsSoft() > 0) {
							request.setAttribute("lIsSoft",String.valueOf(accinfo.getAccountExtendInfo().getIsSoft()));	
						}
					}
				}
				
				
				
					pageControllerInfo.setUrl(strContext + "/capital/notify/n004-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW :
					pageControllerInfo.setUrl(strContext + "/capital/notify/n014-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.TRUSTLOANRECEIVE :
					pageControllerInfo.setUrl(strContext + "/capital/loanrepayment/l006-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.YTLOANRECEIVE :
					pageControllerInfo.setUrl(strContext + "/capital/loanrepayment/l006-v.jsp?checktype=1&isYT=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.CONSIGNLOANRECEIVE :
					pageControllerInfo.setUrl(strContext + "/capital/loanrepayment/l016-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.INTERESTFEEPAYMENT :
					pageControllerInfo.setUrl(strContext + "/capital/loanrepayment/l026-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.DRIVEFIXDEPOSIT :
					pageControllerInfo.setUrl(strContext + "/capital/fixed/f008-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				case (int) OBConstant.QueryInstrType.CHANGEFIXDEPOSIT :
					pageControllerInfo.setUrl(strContext + "/capital/fixed/f011-v.jsp?checktype=1");
					//分发
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					break;
				default :
					OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
					return;
			}
			/* forward到结果页面 */
	    	rd.forward(request, response);
		}
		else
		{	//没有匹配记录，返回上一页面。
			//对逐笔付款，定期/通知开立/支取和到期续存的返回方式和数据带回方式做了修改。
			//本次仅对ck003-v.jsp、ck004-v.jsp、ck010-v.jsp、ck007-c.jsp做了修改。
			//2010-01-10
 			switch((int) lTransType)
				{
				case (int)OBConstant.QueryInstrType.CAPTRANSFER:
%>
			<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%
					break;
				case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
%>
			<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%
					break;
				case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
%>
			<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%
					break;
				case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
%>
			<script language="JavaScript">
			alert("没有相匹配的交易申请，请重新录入");
			history.go(-1);
			</script>
<%
					break;
				case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck008-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT://定期续存
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck010-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT://定期转存
					sessionMng.getActionMessages().addMessage("没有相匹配的交易申请，请重新录入");
					pageControllerInfo.setUrl(strContext + "/capital/check/ck011-v.jsp");
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					/* forward到结果页面 */
	    			rd.forward(request, response);
					break;
				default :
					OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
					/* forward到结果页面 */
	    			rd.forward(request, response);
					return;		
			}
			
		
		}
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->
