<%--
 页面名称 ：c004.jsp
 页面功能 : 贷款发放打印控制页面
 作    者 ：rxie
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
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
		//请求检测
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
  		long lTransactionTypeID = -1;

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
		
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("strPrintPage");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}
		
		//TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
		Sett_TransGrantLoanDAO loanDelegation=new Sett_TransGrantLoanDAO();
		
		
		TransGrantLoanInfo resultInfo = null;
		
		//resultInfo = transLoanDelegation.grantFindDetailByID(lID);
		resultInfo=loanDelegation.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		
		if(resultInfo != null)
		{
		   Log.print("result not null");
			//set 打印参数
		   if(resultInfo.getOfficeID() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getOfficeID());
			}
				
			if(resultInfo.getCurrencyID() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getCurrencyID());
			}
			
			if(resultInfo.getAmount() != 0.0 )
			{
				printInfo.setAmount(resultInfo.getAmount());
			}
			
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}
			
			if(resultInfo.getExecute() != null )
			{
				printInfo.setExecuteDate(resultInfo.getExecute());
			}
			
			if(resultInfo.getInterestStart() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStart());
			}
			
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}
		    
			if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstract());
			}
			
			if(resultInfo.getAbstractID() > 0 )
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
			
			if(resultInfo.getInputUserID() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getInputUserID());
			}
			
			if(resultInfo.getCheckUserID() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getCheckUserID());
			}
		    
			if(resultInfo.getLoanContractID() > 0)
			{
				printInfo.setContractID(resultInfo.getLoanContractID());
			}
			
			if(resultInfo.getLoanNoteID() > 0)
			{
				printInfo.setLoanNoteID(resultInfo.getLoanNoteID());
			}
			
			//放款类型
	       
			 //借款账户,即自营或者委托贷款账号
			 if(resultInfo.getLoanAccountID() > 0)
			 {
			 	printInfo.setLoanAccountID(resultInfo.getLoanAccountID());
			 }
			 
			// Log.print("委托贷款账号："+NameRef.getAccountNoByID(printInfo.getLoanAccountID()));
			  //收款方
			   if(resultInfo.getDepositAccountID() > 0)
			  {
				printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
			  }
			  
			  
			   if(resultInfo.getDepositAccountID() > 0)
			  {
				printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
			  }
			
			//付款方  
			   if(resultInfo.getLoanAccountID() > 0)
			  {
				printInfo.setPayAccountID(resultInfo.getLoanAccountID());
			  }
			  
			   if(resultInfo.getLoanAccountID() > 0)
			  {
				printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getLoanAccountID()));
				
			  }
			    
			if(resultInfo.getExtendFormID() > 0)
			{
				printInfo.setExtendFormID(resultInfo.getExtendFormID());
			}
			
			if(resultInfo.getPayTypeID() > 0)
			{
				printInfo.setPayTypeID(resultInfo.getPayTypeID());
			}
			
			 //******收款方--从银行--added by gqzhang 2003.11.29	
			 if(resultInfo.getBankID() > 0)
			{
				printInfo.setReceiveBankID(resultInfo.getBankID());
			}
				
			//外部账号		
			if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
				printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
			}
			
			//	外部客户名称
			if(resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
			{
					printInfo.setExtClientName(resultInfo.getExtAcctName());
			}
			
			//外部汇入行
			if(resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
			{
					printInfo.setExtRemitInBank(resultInfo.getBankName());
			}
     		  //******收款方--从银行--added by gqzhang 2003.11.29
			
			if(resultInfo.getInterestTaxRateVauleDate() != null )
			{
				printInfo.setInterestTaxRateVauleDate(resultInfo.getInterestTaxRateVauleDate());
			}
			
			if(resultInfo.getInterestTaxRate() != 0.0 )
			{
				printInfo.setInterestTaxRate(resultInfo.getInterestTaxRate());
			}
			
			if(resultInfo.getPayCommisionAccountID() > 0)
			{
				printInfo.setPayCommissionAccountID(resultInfo.getPayCommisionAccountID());
			}
			
			if(resultInfo.getReceiveSuretyFeeAccountID() > 0)
			{
				printInfo.setReceiveSuretyFeeAccountID(resultInfo.getReceiveSuretyFeeAccountID());
			}
			
			if(resultInfo.getPaySuretyFeeAccountID() > 0)
			{
				printInfo.setPaySuretyFeeAccountID(resultInfo.getPaySuretyFeeAccountID());
			}
			
			if(resultInfo.getReceiveInterestAccountID() > 0)
			{
				printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
			}
			
			if(resultInfo.getPayInterestAccountID() > 0)
			{
				printInfo.setPayInterestAccountID(resultInfo.getPayInterestAccountID());
			}
			
			if(resultInfo.getTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}

			
			//set打印参数End
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//打印进账单
			}
			
			if ((strPrintPage.indexOf("8") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('8','a');
				lShow = 1;
				PrintVoucher.PrintTrustGrantLoan(printInfo,out);//打印自营贷款发放凭证
				
			}
			
			if ((strPrintPage.indexOf("9") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('9','a');
				lShow = 1;
				PrintVoucher.PrintConsignGrantLoan(printInfo,out);//打印委托贷款发放凭证
				Log.print("打印委托贷款发放凭证");
			}
			
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特种转账凭证
			}
			
			/*
			特别说明：为不影响已经完成并通过测试的凭证，在此处针对套打凭证进行printInfo的设置
			此处调用的是IPrintVoucher中的PrintTemplate方法
			*/
			/*			
			if (pi.getPayAccountID() > 0)
			{
				pi.getPayAccountID()
				pi.getPayBankID()
			}
			else
			{
				PayExtClientName()
				PayExtAccountNo()
				PayExtRemitInBank()
				PayExtRemitInProvince()
				PayExtRemitInCity()
			}
			if (pi.getReceiveAccountID() > 0)
			{
			    pi.getReceiveAccountID()
				pi.getReceiveBankID()
				
			}
			else
			{
				pi.getReceiveExtClientName()
				pi.getReceiveExtAccountNo()
				pi.getReceiveExtRemitInBank()
				pi.getReceiveExtRemitInProvince()
				pi.getReceiveExtRemitInCity()
			}
			*/
			
			if(resultInfo.getLoanAccountID() > 0 )
			{
			   printInfo.setPayAccountID(resultInfo.getLoanAccountID());
			}
			
			if(resultInfo.getDepositAccountID() > 0)
			{
			   printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());	
			}
			
			
			if(resultInfo.getExtAcctName() != null && resultInfo.getExtAcctName().length() > 0)
			{
				printInfo.setReceiveExtClientName(resultInfo.getExtAcctName());
			}
			
			if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
			   printInfo.setReceiveExtAccountNo(resultInfo.getExtAcctNo());
			}
			
			if(resultInfo.getBankName() != null && resultInfo.getBankName().length() > 0)
			{
				printInfo.setReceiveExtRemitInBank(resultInfo.getBankName());
			}
			
			if(resultInfo.getProvince() != null && resultInfo.getProvince().length() >0)
			{
			   printInfo.setReceiveExtRemitInProvince(resultInfo.getProvince());
			}
			
			if(resultInfo.getCity() != null && resultInfo.getCity().length() >0)
			{
			   printInfo.setReceiveExtRemitInCity(resultInfo.getCity());
			}
			
			if ((strPrintPage.indexOf("b") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('b','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(1,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("c") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('c','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(2,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("d") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('d','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(3,1,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("e") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('e','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(4,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("f") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('f','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("g") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('g','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("h") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('h','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("i") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('i','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("j") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('j','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("k") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('k','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(6,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("l") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('l','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(7,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("m") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('m','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,3,printInfo,out);//套打
			}
			if ((strPrintPage.indexOf("n") >= 0) && lShow < 0 )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('n','a');
				lShow = 1;
				IPrintVoucher.PrintTemplate(8,1,printInfo,out);//套打
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
	 	location.href='../accountinfo/a916-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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