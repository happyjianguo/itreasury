<%--
 页面名称 ：a919-p.jsp
 页面功能 : 贷款收回打印控制页面
 作    者 ：qqgd
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
<%@ page import="com.iss.itreasury.settlement.account.dao.Sett_AccountDAO"%>
<%@ page import="com.iss.itreasury.settlement.account.dataentity.AccountInfo"%>
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
        //定义变量
		long lOfficeID = sessionMng.m_lOfficeID;
		
		//页面辅助变量
		String strContinueSave = null;

		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

		//打印变量
		long lShow = -1;
        //读取数据
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strContinueSave = (String)request.getAttribute("strContinueSave");
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strPrintPage");
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
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
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
				//委托单位id
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignClientID(NameRef.getClientIDByAccountID(resultInfo.getConsignAccountID()));
				}
				//账户类型
				if (resultInfo.getLoanAccountID() > 0)
				{
					Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
					AccountInfo accountInfo = sett_AccountDAO.findByID(resultInfo.getLoanAccountID());
					if (accountInfo != null)
					{
						printInfo.setAccountTypeID(accountInfo.getAccountTypeID());
					}
				}
				//收款方
				if (resultInfo.getLoanAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getLoanAccountID());
				}
				//付款方客户	
				if (resultInfo.getClientID() > 0)
				{
					printInfo.setPayClientID(resultInfo.getClientID());
				}
				if (resultInfo.getDepositAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getDepositAccountID());
				}
				if (resultInfo.getBankID() > 0)
				{
					printInfo.setPayBankID(resultInfo.getBankID());
				}
				//当前余额
				if (resultInfo.getCurrentBalance() != 0.0)
				{
					printInfo.setCurrentBalance(resultInfo.getCurrentBalance());
				}
				if (resultInfo.getLoanContractID() > 0)
				{
					printInfo.setContractID(resultInfo.getLoanContractID());
				}
				if (resultInfo.getLoanNoteID() > 0)
				{
					printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
				}
				if (resultInfo.getPayInterestAccountID() > 0)
				{
					printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
				}
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				if (resultInfo.getPaySuretyAccountID() > 0)
				{
					printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyAccountID());
				}
				if (resultInfo.getReceiveSuretyAccountID() > 0)
				{
					printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyAccountID());
				}
				if (resultInfo.getCommissionAccountID() > 0)
				{
					printInfo.setPayCommissionAccountID(resultInfo.getCommissionAccountID());
				}
				if (resultInfo.getFreeFormID() > 0)
				{
					printInfo.setFreeFormID(resultInfo.getFreeFormID());
				}
				if (resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0)
				{
					printInfo.setBillNo(resultInfo.getBillNo());
				}
				if (resultInfo.getBillTypeID() > 0)
				{
					printInfo.setBillTypeID(resultInfo.getBillTypeID());
				}
				if (resultInfo.getBillBankID() > 0)
				{
					printInfo.setBillBankID(resultInfo.getBillBankID());
				}
				if (resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0)
				{
					printInfo.setExtBankNo(resultInfo.getExtBankNo());
				}
				if (resultInfo.getPreFormID() > 0)
				{
					printInfo.setPreFormID(resultInfo.getPreFormID());
				}
				if (resultInfo.getInterestBankID() > 0)
				{
					printInfo.setPayInterestBankID(resultInfo.getInterestBankID());
				}
				if (resultInfo.getSuretyBankID() > 0)
				{
					printInfo.setPaySuertyFeeBankID(resultInfo.getSuretyBankID());
				}
				if (resultInfo.getCommissionBankID() > 0)
				{
					printInfo.setPayCommissionBankID(resultInfo.getCommissionBankID());
				}
				if (resultInfo.getInterest() != 0.0)
				{
					printInfo.setInterest(resultInfo.getInterest());
				}
				if (resultInfo.getInterestReceiveAble() != 0.0)
				{
					printInfo.setInterestReceivable(resultInfo.getInterestReceiveAble());
				}
				if (resultInfo.getInterestIncome() != 0.0)
				{
					printInfo.setInterestIncome(resultInfo.getInterestIncome());
				}
				if (resultInfo.getInterestTax() != 0.0)
				{
					printInfo.setInterestTax(resultInfo.getInterestTax());
				}
				if (resultInfo.getRealInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getRealInterest());
				}
				if (resultInfo.getRealInterestReceiveAble() != 0.0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getRealInterestReceiveAble());
				}
				if (resultInfo.getRealInterestIncome() != 0.0)
				{
					printInfo.setRealInterestIncome(resultInfo.getRealInterestIncome());
				}
				if (resultInfo.getRealInterestTax() != 0.0)
				{
					printInfo.setRealInterestTax(resultInfo.getRealInterestTax());
				}
				if (resultInfo.getRealCompoundInterest() != 0.0)
				{
					printInfo.setRealCompoundInterest(resultInfo.getRealCompoundInterest());
				}
				if (resultInfo.getCompoundAmount() != 0.0)
				{
					printInfo.setCompoundAmount(resultInfo.getCompoundAmount());
				}
				if (resultInfo.getCompoundInterest() != 0.0)
				{
					printInfo.setCompoundInterest(resultInfo.getCompoundInterest());
				}
				if (resultInfo.getCompoundRate() != 0.0)
				{
					printInfo.setCompoundRate(resultInfo.getCompoundRate() * 100);
				}
				if (resultInfo.getCompoundInterestStart() != null)
				{
					printInfo.setCompoundInterestStart(resultInfo.getCompoundInterestStart());
				}
				if (resultInfo.getRealOverDueInterest() != 0.0)
				{
					printInfo.setRealOverDueInterest(resultInfo.getRealOverDueInterest());
				}
				if (resultInfo.getOverDueAmount() != 0.0)
				{
					printInfo.setOverDueAmount(resultInfo.getOverDueAmount());
				}
				if (resultInfo.getOverDueInterest() != 0.0)
				{
					printInfo.setOverDueInterest(resultInfo.getOverDueInterest());
				}
				if (resultInfo.getOverDueStart() != null)
				{
					printInfo.setOverDueStart(resultInfo.getOverDueStart());
				}
				if (resultInfo.getOverDueRate() != 0.0)
				{
					printInfo.setOverDueRate(resultInfo.getOverDueRate() * 100);
				}
				if (resultInfo.getRealSuretyFee() != 0.0)
				{
					printInfo.setRealSuretyFee(resultInfo.getRealSuretyFee());
				}
				if (resultInfo.getSuretyFee() != 0.0)
				{
					printInfo.setSuretyFee(resultInfo.getSuretyFee());
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
				if (resultInfo.getCommission() != 0.0)
				{
					printInfo.setCommission(resultInfo.getCommission());
				}
				if (resultInfo.getCommissionStart() != null)
				{
					printInfo.setCommissionStart(resultInfo.getCommissionStart());
				}
				if (resultInfo.getCommissionRate() != 0.0)
				{
					printInfo.setCommissionRate(resultInfo.getCommissionRate() * 100);
				}
				if (resultInfo.getAdjustInterest() != 0.0)
				{
					printInfo.setAdjustInterest(resultInfo.getAdjustInterest());
				}
				//业务类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}
				//贷款单位
				if (resultInfo.getConsignAccountID() > 0)
				{
					printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
				}

			//贷款单位
			if(resultInfo.getConsignAccountID() > 0)
			{
				printInfo.setConsignAccountID(resultInfo.getConsignAccountID());
			}
			//set打印参数End
			if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo,out);//存款支取凭证
			}
		
			if ((strPrintPage.indexOf("0") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('0','a');
				lShow = 1;
				PrintVoucher.PrintInterestNotice(printInfo,out);//利息通知单
			}
			
			if ((strPrintPage.indexOf("p") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('p','a');
				lShow = 1;
				PrintVoucher.PrintConsignRepaymentLoan(printInfo,out);//委托贷款收回凭证
						
			}
			
			if ((strPrintPage.indexOf("q") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('q','a');
				lShow = 1;
				PrintVoucher.PrintTrustRepaymentLoan(printInfo,out);//自营贷款收回凭证
						
			}
					
			if ((strPrintPage.indexOf("b") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(1,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("c") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(2,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("d") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(3,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("e") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(4,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("f") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("g") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(6,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("h") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(7,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("i") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("j") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("k") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(6,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("l") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(7,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("m") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("n") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				PrintVoucher.PrintTemplate(8,1,printInfo,out);//套打
			}
			
			 
			Log.print("aaaaaaaaaaadfdffdfd");
			Log.print(resultInfo.getConsignAccountID());
			 //为委托贷款特种转账
			if (printInfo.getTransTypeID() == SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			{//委托贷款
				//特种转账付方
				if(resultInfo.getConsignAccountID()>0)
				{
					printInfo.setPayAccountID(resultInfo.getConsignAccountID());
				}
				
				//特种转账收方方
				if(resultInfo.getConsignDepositAccountID()>0)
				{
					printInfo.setReceiveAccountID(resultInfo.getConsignDepositAccountID());
					printInfo.setSpecialAccountID(resultInfo.getConsignDepositAccountID());
				}
				if ((strPrintPage.indexOf("2") >= 0) )
				{
					if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
					strPrintPage = strPrintPage.replace('6','a');
					lShow = 1;
					PrintVoucher.PrintShowCredit(printInfo,out);//特种转账
				}
			}
			else//自营贷款
			{
				if ((strPrintPage.indexOf("2") >= 0) )
				{
					if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
					strPrintPage = strPrintPage.replace('6','a');
					lShow = 1;
					PrintVoucher.PrintShowCredit(printInfo,out);//特种转账
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
	 	location.href='../accountinfo/a919-p.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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