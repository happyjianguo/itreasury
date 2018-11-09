<%--
 页面名称 ：c007.jsp
 页面功能 : 贴现收回打印控制页面
 作    者 ：rxie
 日    期 ：2003-11-20
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
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.TransRepaymentDiscountInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO"%>
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
		long lOfficeID = -1;
		
		
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
		
		//TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
		Sett_TransRepaymentDiscountDAO loanDelegation = new Sett_TransRepaymentDiscountDAO();
		
		TransRepaymentDiscountInfo resultInfo = null;
		
		//resultInfo = transDiscountDelegation.repaymentFindDetailByID(lID);
		resultInfo=loanDelegation.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		
		if(resultInfo != null)
		{
		Log.print("result not null");
			//set 打印参数
			   if(resultInfo.getNOfficeID() > 0 )
			{
				printInfo.setOfficeID(resultInfo.getNOfficeID());
				
			}	
			if(resultInfo.getNCurrencyID() > 0 )
			{
				printInfo.setCurrencyID(resultInfo.getNCurrencyID());
			}
			
			if (resultInfo.getNStatusID() > 0)
			{
				printInfo.setStatusID(resultInfo.getNStatusID());
			}
			if (resultInfo.getNTransactionTypeID() > 0)
			{
				printInfo.setTransTypeID(resultInfo.getNTransactionTypeID());
			}
			
			//  执行日，即贴现日
			if(resultInfo.getDtExecute() != null )
			{
				printInfo.setExecuteDate(resultInfo.getDtExecute());
			}
			
			//起息日，即银行到账日
			if(resultInfo.getDtInterestStart() != null )
			{
				printInfo.setInterestStartDate(resultInfo.getDtInterestStart());
			}
			if(resultInfo.getSTransNo() != null && resultInfo.getSTransNo().length() > 0)
			{
				printInfo.setTransNo(resultInfo.getSTransNo());
			}
			
			if(resultInfo.getSAbstract() != null && resultInfo.getSAbstract().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getSAbstract());
			}
			
			if(resultInfo.getNInputUserID() > 0 )
			{
				printInfo.setInputUserID(resultInfo.getNInputUserID());
			}
			
			if(resultInfo.getNCheckUserID() > 0 )
			{
				printInfo.setCheckUserID(resultInfo.getNCheckUserID());
			}
			
			
	     
				
		/*	
		//收款方
			if(resultInfo.getNClientID() > 0 )
			{
				printInfo.setReceiveClientID(resultInfo.getNClientID());
			}
			
			if(resultInfo.getNDiscountAccountID()>0)
			{
				printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
			}

			if(resultInfo.getNBankID()>0)
			{
				printInfo.setReceiveBankID(resultInfo.getNBankID());
			}
			*/
			
			//单据号
			if(resultInfo.getNDiscountNoteID() > 0)
			{
				printInfo.setFixedDepositNo(NameRef.getDiscountCredenceNoByID(resultInfo.getNDiscountNoteID()));
			}
			
			//贴现汇票ID(根据此ID取贴现汇票种类)
			if(resultInfo.getNDiscountNoteID() > 0)
			{
				printInfo.setDiscountNoteID(resultInfo.getNDiscountNoteID());
			}
			
			//汇票ID,(根据此ID可取汇票号码)
			if(resultInfo.getNDiscountBillID() > 0)
			{
				printInfo.setDiscountBillID(resultInfo.getNDiscountBillID());
			}
			
			//贴现单位账号，即收款方单位，根据此ID可以取贴现单位名称  
			if(resultInfo.getNDiscountAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getNDiscountAccountID());
			}
			
			//贴现收回金额
			if(resultInfo.getMAmount() != 0.0)
			{
				printInfo.setDiscountAmount(resultInfo.getMAmount());
				printInfo.setAmount(resultInfo.getMAmount());//为了打印 特种转账借贷凭证 的金额
			}
			Log.print("贴现收回金额:"+printInfo.getDiscountAmount());
			
			//当前贴现余额
			if(resultInfo.getMSumReceiveAmout()!= 0.0)
			{
				printInfo.setCurrentBalance(resultInfo.getMSumReceiveAmout());
			}
			Log.print("贴现收回余额:"+printInfo.getCurrentBalance());
			
			//付款银行ID,删除原因：在贴现收回交易中打印的特种转账借方凭证中付款方取退票处理中的活期账号，
			//如果没有退票，则付款方显示为空
			/*	if (resultInfo.getNBankID() > 0)
				{
					printInfo.setExtClientName(NameRef.getBankNameByID(resultInfo.getNBankID()));
					printInfo.setExtAccountNo(IPrintTemplate.getBankAccountCodeByID(resultInfo.getNBankID()));
				}
				*/
			//付款方
			if (resultInfo.getNDepositAccountID() > 0)
			{
				printInfo.setPayAccountID(resultInfo.getNDepositAccountID());
				Log.print("付款方账号：" + printInfo.getPayAccountID());
			}
			
			//退票金额
			if(resultInfo.getMReturnedAmount() != 0.0)
			{
				printInfo.setAmount(resultInfo.getMReturnedAmount());
			}
			
			if (resultInfo.getMOverDueInterest() > 0)
			{
				printInfo.setOverDueAmount(resultInfo.getMOverDueInterest());
			}
			
			Log.print("退票金额:"+resultInfo.getMReturnedAmount());
			//set打印参数End
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintRepaymentDiscount(printInfo,out);//打印贴现收回凭证
			}
		
			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//打印特转借
			}
			
		 /*  if ((strPrintPage.indexOf("3") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('3','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特转贷
			}
		*/	
		
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
	 	location.href='../accountinfo/a912-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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