<%--
/**
 页面名称 ：a403-c.jsp
 页面功能 : 定期(通知)支取打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：实现操作说明：findByID取得所有信息
 定期支取:(通知支取)
 			进账单（本金）
			特种转账（借/贷方）凭证
			存款利息计付通知单
			存款支取凭证
 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO"%>
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

    //标题变量
    String strTitle = "[账户历史明细]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }
        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

		long lID = -1;
		long lOfficeID = sessionMng.m_lOfficeID;
		String strPrintPage = "";
      
	  //页面辅助变量
		String strContinueSave = null;
		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;
		long lTransactionTypeID = -1;

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
		
		strTemp = (String)request.getParameter("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		
		//TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();

		//查找支取信息
		TransFixedDrawInfo resultInfo = null;
		resultInfo = dao.findByID(lID);
		PrintInfo printInfo = new PrintInfo();
		//添加支取信息
		if(resultInfo!=null)
		{
			if(resultInfo.getOfficeID() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getOfficeID());
			}
			if(resultInfo.getCurrencyID() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getCurrencyID());
			}
			if(resultInfo.getAbstractID() > 0 )
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
		
			if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstract());
			}
			if(resultInfo.getInputUserID() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getInputUserID());
			}
			if(resultInfo.getCheckUserID() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getCheckUserID());
			}
			if(resultInfo.getExecuteDate() != null )
			{
				printInfo.setExecuteDate(resultInfo.getExecuteDate());
			}
			if(resultInfo.getInterestStartDate() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
			}
			if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getTransNo());
			}		

			//本金
		if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER)	
		{
			if(resultInfo.getAmount() != 0.0 )
			{
				printInfo.setAmount(resultInfo.getAmount());
			}
		}
		if (resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)	
		{
			if(resultInfo.getDrawAmount() != 0.0 )
			{
				printInfo.setAmount(resultInfo.getDrawAmount());
			}
		}  	
			if(resultInfo.getCapitalExtBankNo() != null && resultInfo.getCapitalExtBankNo().length() > 0)
			{
				request.setAttribute("strExtBankNo",resultInfo.getCapitalExtBankNo());
			}
			if(resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
			{
				request.setAttribute("strInstructionNo",resultInfo.getInstructionNo());
			}			
			

		   if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
				printInfo.setExtAccountNo(resultInfo.getExtAcctNo());
			}
			if(resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setExtClientName(resultInfo.getExtClientName());
			}
			
			//收款人汇入行名称
			if(resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			//收款人汇入省
			if(resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			//收款人汇入市
			if(resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}

			if(resultInfo.getClientID() > 0 )
			{
				printInfo.setReceiveClientID(resultInfo.getClientID());
			}

			//付款人账户id,付款方账号,付款人全称
			if(resultInfo.getAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			
			
			//收款方人账号Id，可得收款人账号，收款人全称
			if(resultInfo.getCurrentAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());
			}

			//收款人开户行id，可得PayBankCode,PayBankName，ReceiveBankCode
			if(resultInfo.getBankID() >0 )
			{
			  printInfo.setReceiveBankID(resultInfo.getBankID());
			}

			//子定期账户id
			if(resultInfo.getSubAccountID() > 0)
			{
				printInfo.setSubAccountID(resultInfo.getSubAccountID());
			}

			//账号码，客户名称，据上面得付款人账户id取
			
			//利息
			if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				if(resultInfo.getDrawInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getDrawInterest());
				}
			}
			else
			{
				if(resultInfo.getTotalInterest() != 0.0)
				{
					printInfo.setPayableInterest(resultInfo.getTotalInterest());
				}
			}
			
			//活期利息
			if(resultInfo.getCurrentInterest() != 0.0)
			{
				printInfo.setCurrentInterest(resultInfo.getCurrentInterest());
			}
			Log.print("活期利息:"+printInfo.getCurrentInterest());
			
			//其它利息
			if(resultInfo.getOtherInterest() != 0.0)
			{
				printInfo.setOtherInterest(resultInfo.getOtherInterest());
			}
			Log.print("其它利息:"+printInfo.getOtherInterest());
		
			//收息账号
			if (resultInfo.getReceiveInterestAccountID() > 0)
			{
				printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				
			}
			
			//利率
			if(resultInfo.getRate() != 0.0)
			{
				printInfo.setRate(resultInfo.getRate());
			}
			
			//存单号码
			if(resultInfo.getDepositNo()!= null || resultInfo.getDepositNo().length() > 0)
			{
				printInfo.setFixedDepositNo(resultInfo.getDepositNo());
			}
			
			//存款类型
			if (resultInfo.getTransactionTypeID() > 0 )
			{
				printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
			}
			
			//开始日期
			if(resultInfo.getStartDate() != null)
			{
				printInfo.setStartDate(resultInfo.getStartDate());
			}
			
			//结束日期
			if(resultInfo.getEndDate() != null)
			{
				printInfo.setEndDate(resultInfo.getEndDate());
			}
			
			//如果是通知存款，结束日期等于起息日
			if(resultInfo.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			{
				printInfo.setEndDate(resultInfo.getInterestStartDate());
				Log.print("通知结束日:"+printInfo.getEndDate());
			}

		}  		
		
		if(resultInfo != null)
		{
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//打印进账单
			}
			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('4','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//打印特转借
			}
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷
			}
			
			if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowWithDraw(printInfo,out);//存款支付凭证
			}
			
			if ((strPrintPage.indexOf("7") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('7','a');
				lShow = 1;
				PrintVoucher.PrintDepositInterest(printInfo,out);//存款利息计付通知单
			}
			
			
			/*
			特别说明：为不影响已经完成并通过测试的凭证，在此处针对套打凭证进行printInfo的设置
			此处调用的是IPrintVourcher中的PrintTemplate方法
			*/
			
			if(resultInfo.getAccountID() > 0 )
			{
			   printInfo.setPayAccountID(resultInfo.getAccountID());
			}
			
			if(resultInfo.getCurrentAccountID() > 0)
			{
			   printInfo.setReceiveAccountID(resultInfo.getCurrentAccountID());	
			}
			
			
			if(resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setReceiveExtClientName(resultInfo.getExtClientName());
			}
			
			if(resultInfo.getExtAcctNo() != null && resultInfo.getExtAcctNo().length() > 0)
			{
			   printInfo.setReceiveExtAccountNo(resultInfo.getExtAcctNo());
			}
			
			if(resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setReceiveExtRemitInBank(resultInfo.getRemitInBank());
			}
			
			if(resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() >0)
			{
			   printInfo.setReceiveExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			
			if(resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() >0)
			{
			   printInfo.setReceiveExtRemitInCity(resultInfo.getRemitInCity());
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
	 	location.href='../a403-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
	 }
}

document.onkeydown = keyDown; // work together to analyze keystrokes
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
</SCRIPT>

<%

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
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>