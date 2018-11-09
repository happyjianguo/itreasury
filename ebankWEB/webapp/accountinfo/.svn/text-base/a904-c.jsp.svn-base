<%--
 页面名称 ：c011.jsp
 页面功能 : 利息费用支付打印控制页面
 作    者 ：rxie
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
			存款支取凭证
			存款利息通知单
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>



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

		long lID = -1;
		String strPrintPage = "";
		long lShow = -1;//是否是打印套打的凭证
        //定义变量
		double dAmount = 0.0;
		String strBankChequeNo = "";
		long lBankID = -1;
		long lBillBankID = -1;
		String strBillNo = "";
		long lBillTypeID = -1;
		long lCashFlowID = -1;
		String strCheckAbstractStr = "";
		String strConfirmAbstractStr = "";
		long lConfirmOfficeID = -1;
		long lConfirmUserID = -1;
		String strConsignPassword = "";
		long lConsignReceiveTypeID = -1;
		String strConsignVoucherNo = "";
		String strDeclarationNo = "";
		String strExtBankNo = "";
		String strInstructionNo = "";
		java.sql.Timestamp tsModifyTime = null;
		long lPayAccountID = -1;
		long lReceiveAccountID = -1;
		long lSignUserID = -1;
		long lSingleBankAccountTypeID = -1;
		long lStatusID = -1;
		long lReceiveClientID = -1;
		long lPayClientID = -1;
		long lAbstractID = -1;
		String strExtAccountNo = "";
		String strExtClientName = "";
		String strRemitInBank = "";
		String strRemitInCity = "";
		String strRemitInProvince = "";
		String strAbstractStr = "";
		long lCheckUserID = -1;
		long lCurrencyID = -1;
		java.sql.Timestamp tsExecuteDate = null;
		long lInputUserID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lTransactionTypeID = -1;
		String strTransNo = "";
		java.sql.Timestamp tsInputDate = null;
		
		//页面辅助变量
		String strContinueSave = null;

		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

        //读取数据
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");
		
		String strTemp = null;
		strTemp = (String)request.getParameter("lID");//交易ID
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getParameter("strPrintPage");//打印的单据类型
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		
		//TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo resultInfo = null;
		//resultInfo = transLoanDelegation.repaymentFindDetailByID(lID);
		resultInfo=transLoanDelegation.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		
		if(resultInfo != null)
		{
		   
				
				Log.print("result not null");
				//set 打印参数
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getExecute() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecute());
				}
				if(resultInfo.getStatusID() >0)
				{
					printInfo.setStatusID(resultInfo.getStatusID());
				}
				//上次结息日
				if (resultInfo.getLatestInterestClear() != null)
				{
					printInfo.setLatestInterestClearDate(resultInfo.getLatestInterestClear());
				}
				//结息日
				if (resultInfo.getInterestClear() != null)
				{
					printInfo.setInterestClearDate(resultInfo.getInterestClear());
				}
				//起息日
				if (resultInfo.getInterestStart() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStart());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//在利息费用支付中付方账户,也是付息账号,付手续费，担保费账号
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getPayInterestAccountID());
				}
				//付方银行，当付款方为银行时，开户行显示为空
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getInterestBankID()));
					printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getInterestBankID()));
				}
				//收方账户
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//交易类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//在利息费用支付中，根据账号设置业务类型
				long lAccountTypeID = -1;
				if (resultInfo.getLoanAccountID() > 0)
					lAccountTypeID = NameRef.getAccountTypeByID(resultInfo.getLoanAccountID());
				
				if (SETTConstant.AccountType.isTrustAccountType(lAccountTypeID)) //自营
				{
					//交易类型
					printInfo.setTransTypeID(SETTConstant.TransactionType.TRUSTLOANRECEIVE);
					//账户类型
					printInfo.setAccountTypeID(lAccountTypeID);
				}
				if (SETTConstant.AccountType.isConsignAccountType(lAccountTypeID)) //委贷
				{
					//交易类型
					printInfo.setTransTypeID(SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
					//账户类型
					printInfo.setAccountTypeID(lAccountTypeID);
				}
				//借据号
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				//合同号
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getRealInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getRealInterest());
				}
				if (resultInfo.getRealCompoundInterest() != 0.0)
				{
					printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
				}
				if (resultInfo.getCompoundInterestStart() != null)
				{
					printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
				}
				if (resultInfo.getCompoundAmount() != 0.0)
				{
					printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
				}
				if (resultInfo.getCompoundRate() != 0.0)
				{
					printInfo.setCompoundRate(resultInfo.getCompoundRate() * 100);
				}
				if (resultInfo.getRealOverDueInterest() != 0.0)
				{
					printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
				}
				if (resultInfo.getOverDueStart() != null)
				{
					printInfo.setOverDueStart(resultInfo.getOverDueStart());
				}
				if (resultInfo.getOverDueAmount() != 0.0)
				{
					printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
				}
				if (resultInfo.getOverDueRate() != 0.0)
				{
					printInfo.setOverDueRate(resultInfo.getOverDueRate() * 100);
				}
				if (resultInfo.getRealSuretyFee() != 0.0)
				{
					printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
				}
				if (resultInfo.getSuretyFeeStart() != null)
				{
					printInfo.setSuretyFeeStart(resultInfo.getSuretyFeeStart());
				}
				if (resultInfo.getSuretyFeeRate() != 0.0)
				{
					printInfo.setSuretyFeeRate(resultInfo.getSuretyFeeRate() * 100);
				}
				if (resultInfo.getRealCommission() != 0.0)
				{
					printInfo.setRealCommission(resultInfo.getRealCommission());
				}
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				if (resultInfo.getCommissionRate() != 0.0)
				{
					Log.print("====手续费利率1：" + resultInfo.getCommissionRate());
					Log.print("====手续费利率2：" + resultInfo.getCommissionRate() * 100);
					printInfo.setCommissionRate(DataFormat.formatDouble(resultInfo.getCommissionRate() * 100, 6));
					Log.print("====手续费利率3：" + printInfo.getCommissionRate());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				//利息总额（贷款利息通知单中设置为本金）
				printInfo.setAmount(
					DataFormat.formatDouble(
						DataFormat.formatDouble(resultInfo.getRealInterest())
							+ DataFormat.formatDouble(resultInfo.getRealCompoundInterest())
							+ DataFormat.formatDouble(resultInfo.getRealOverDueInterest())));
				//借款单位
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setBorrowClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				}
				//账户
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getLoanAccountID());
				}
				//利息本金
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setLoanBalance(resultInfo.getCurrentBalance());
				}
				//付息账号,也是付手续费和担保费的账号
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPayInterestAccountID());
					printInfo.setPayCommissionAccountID(resultInfo.getPayInterestAccountID());
				}
			
				
				double dShowWithDrawAmount = DataFormat.formatDouble(
							DataFormat.formatDouble(printInfo.getRealInterest())
								+ DataFormat.formatDouble(printInfo.getRealCompoundInterest())
								+ DataFormat.formatDouble(printInfo.getRealOverDueInterest())
								+ DataFormat.formatDouble(printInfo.getRealSuretyFee())
								+ DataFormat.formatDouble(printInfo.getRealCommission()));
		
		 dAmount = DataFormat.formatDouble(printInfo.getAmount());
		 Log.print("======dShowWithDrawAmount:"+dShowWithDrawAmount);
		 Log.print("======dAmount:"+dAmount);
		 Log.print("======手续费利率:"+ printInfo.getCommissionRate());
			
		   
		   if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				//更改原因，在自营贷款和委托贷款中存款支取凭证本金金额等于走活期账户的本金，利息，以及费用之和
				printInfo.setAmount(0);
				PrintVoucher.PrintShowWithDraw(printInfo,out);//打印存款支取凭证
				printInfo.setAmount(dAmount);
			}
			if ((strPrintPage.indexOf("0") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('0','a');
				lShow = 1;
				
				PrintVoucher.IPrintInterestNotice(printInfo,out);//贷款利息通知单
			}
			
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;

				if(printInfo.getTransTypeID() == SETTConstant.TransactionType.TRUSTLOANRECEIVE)
				{
					PrintVoucher.PrintShowCredit(printInfo,out);//特种转账贷方凭证
				}
				else if(printInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
				{
					printInfo.setAmount(0.0);
					PrintVoucher.PrintShowCredit(printInfo,out);//特种转账贷方凭证
					printInfo.setAmount(dAmount);
				}
			}
						
			if (lShow < 0)
			{
%>	
			<SCRIPT language=JavaScript>
				alert("打印完毕！");
				window.close();
			</script>
<%
				return;
			}
			
			
			IPrintTemplate.noShowPrintHeadAndFooter(out,0,lOfficeID);
		}
		%>
<Script Language="JavaScript">
netscape = "";
function keyDown(DnEvents)
{
	k = (netscape) ? DnEvents.which : window.event.keyCode;
	
	if (k == 13)
	 { 
	 	location.href='../accountinfo/a904-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
	 }
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>

		<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;
	}

	request.setAttribute("strActionResult",strActionResult);
/*
	//根据处理结果设置下一步跳转的目标页面
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//转向下一页面
	Log.print("Next Page URL:"+strNextPageURL);	
	//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
*/
%>
