<%--
 页面名称 ：c006.jsp
 页面功能 : 贴现打印控制页面
 作    者 ：rxie
 日    期 ：2003-11-17
 特殊说明 ：实现操作说明：findByID取得所有信息
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO"%>
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
     //Log.print("进入C006.jsp");
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
	
		//TransDiscountDelegation transDiscountDelegation = new TransDiscountDelegation();
		Sett_TransGrantDiscountDAO transDiscountDelegation = new Sett_TransGrantDiscountDAO();
		TransGrantDiscountInfo resultInfo = null;
		
		//resultInfo = transDiscountDelegation.grantFindDetailByID(lID);
		resultInfo = transDiscountDelegation.findByID(lID);
  		
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

			
		  //收款方
			   if(resultInfo.getDepositAccountID() > 0)
			  {
				printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
				
			  }
			  
			 // Log.print("收款方："+resultInfo.getDepositAccountID());
			   if(resultInfo.getDepositAccountID() > 0)
			   {
				printInfo.setReceiveClientID(NameRef.getClientIDByAccountID(resultInfo.getDepositAccountID()));
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

       
			
			//付款方  
			   if(resultInfo.getDiscountAccountID() > 0)
			  {
				printInfo.setPayAccountID(resultInfo.getDiscountAccountID());
			  }
			  
			   if(resultInfo.getDiscountAccountID() > 0)
			  {
				printInfo.setPayClientID(NameRef.getClientIDByAccountID(resultInfo.getDiscountAccountID()));
				
			  }	
			
			//汇票金额
			if(resultInfo.getDiscountBillAmount() != 0.0)
			{
				printInfo.setDiscountBillAmount(resultInfo.getDiscountBillAmount());
			}
			//Log.print("汇票金额C006:"+printInfo.getDiscountBillAmount());
			
			//贴现汇票ID(贴现号码)
			if(resultInfo.getDiscountNoteID() > 0)
			{
				printInfo.setDiscountNoteID(resultInfo.getDiscountNoteID());
			}
			
			//持票人账号,进而获取持票人名称，持票人开户银行名称？？
			if(resultInfo.getDepositAccountID() > 0)
			{
				printInfo.setReceiveAccountID(resultInfo.getDepositAccountID());
			}
			
			//实际贴现金额
			if(resultInfo.getDiscountAmount()!= 0.0)
			{
				printInfo.setDiscountAmount(resultInfo.getDiscountAmount());
				printInfo.setAmount(resultInfo.getDiscountAmount());
			}
			
			//贴现利息
			if(resultInfo.getInterest()!= 0.0)
			{
				printInfo.setDiscountInterestAmount(resultInfo.getInterest());
			}
			//贴现率，实际贴现金额/汇票金额
			
			
			
			
/******************************
//一下参数需要在贷款中设置
//贴现模版中需要的参数
	public String DiscountType = ""; //贴现种类？   
	public String DateBillOut = "";//出票日？
	public String DateBillEnd = "";//到期日？
	public String BillOutName = "";//汇票出票人名称
	public String BillOutAccount = "";//汇票出票人账号
	public String BillOutBankName = "";//汇票出票开户行
**************************/
			
			//set打印参数End
			
			if ((strPrintPage.indexOf("1") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('1','a');
				lShow = 1;
				PrintVoucher.PrintShowIn(printInfo,out);//打印进账单
			}
			if ((strPrintPage.indexOf("r") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('r','a');
				lShow = 1;
				PrintVoucher.PrintGrantDiscount(printInfo,out);//打印贴现发放凭证
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
	 	location.href='../control/c006.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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