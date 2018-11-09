<%--
/**
 页面名称 ：a404-c.jsp
 页面功能 : 定期续存打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-03-01
 特殊说明 ：实现操作说明：findByID取得所有信息
  定期续存
           定期存款开户证实书
           存款利息计付通知单（利息总额凭证）
           特种转账（借/贷方）凭证
 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO"%>
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
        /* 显示文件头 */
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		long lID = -1;
		long lOfficeID = sessionMng.m_lOfficeID;
		String strPrintPage = "";
      
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
		
		//TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();

		//查找续存信息
		TransFixedContinueInfo resultInfo = null;
		//resultInfo = depositDelegation.continueFindByID(lID);
		resultInfo = dao.findByID(lID);
		PrintInfo printInfo = new PrintInfo();
		//添加续存信息
		if(resultInfo!=null)
		{
				if (resultInfo.getOfficeID() > 0)
				{
					printInfo.setOfficeID(resultInfo.getOfficeID());
				}
				if (resultInfo.getCurrencyID() > 0)
				{
					printInfo.setCurrencyID(resultInfo.getCurrencyID());
				}
				if (resultInfo.getAbstractID() > 0)
				{
					printInfo.setAbstractID(resultInfo.getAbstractID());
				}
				if (resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0)
				{
					printInfo.setAbstract(resultInfo.getAbstract());
				}
				if (resultInfo.getInputUserID() > 0)
				{
					printInfo.setInputUserID(resultInfo.getInputUserID());
				}
				if (resultInfo.getCheckUserID() > 0)
				{
					printInfo.setCheckUserID(resultInfo.getCheckUserID());
				}
				//定期续存时开户日即开户日
				if (resultInfo.getExecuteDate() != null)
				{
					printInfo.setExecuteDate(resultInfo.getExecuteDate());
				}
				//起息日
				if (resultInfo.getInterestStartDate() != null)
				{
					printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
				}
				if (resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0)
				{
					printInfo.setTransNo(resultInfo.getTransNo());
				}
				//利息外部银行
				if (resultInfo.getInterestExtAcctNo() != null && resultInfo.getInterestExtAcctNo().length() > 0)
				{
					printInfo.setExtAccountNo(resultInfo.getInterestExtAcctNo());
				}
				if (resultInfo.getInterestExtClientName() != null && resultInfo.getInterestExtClientName().length() > 0)
				{
					printInfo.setExtClientName(resultInfo.getInterestExtClientName());
				}
				if (resultInfo.getInterestRemitInBank() != null && resultInfo.getInterestRemitInBank().length() > 0)
				{
					printInfo.setExtRemitInBank(resultInfo.getInterestRemitInBank());
				}
				if (resultInfo.getInterestRemitInProvince() != null && resultInfo.getInterestRemitInProvince().length() > 0)
				{
					printInfo.setExtRemitInProvince(resultInfo.getInterestRemitInProvince());
				}
				if (resultInfo.getInterestRemitInCity() != null && resultInfo.getInterestRemitInCity().length() > 0)
				{
					printInfo.setExtRemitInCity(resultInfo.getInterestRemitInCity());
				}
				System.out.println("settlement resultInfo.getAmount()="+resultInfo.getAmount());
				//金额
				if (resultInfo.getAmount() != 0.0)
				{
					printInfo.setAmount(resultInfo.getAmount());
				}
				//存单号
				if (resultInfo.getNewDepositNo() != null && resultInfo.getNewDepositNo().length() > 0)
				{
					printInfo.setFixedDepositNo(resultInfo.getNewDepositNo());
				}
				//定期账户ID,可取账户户名,账号(在定期续存中收方和付方都是定期账号)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setReceiveAccountID(resultInfo.getAccountID());
				}
				//期限
				if (resultInfo.getDepositTerm() > 0)
				{
					printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
				}
				//利率
				if (resultInfo.getNewRate() != 0.0)
				{
					printInfo.setRate(resultInfo.getNewRate());
				}
				//付款方账户ID ，可得付款账号(在定期续存中收方和付方都是定期账号)
				if (resultInfo.getAccountID() > 0)
				{
					printInfo.setPayAccountID(resultInfo.getAccountID());
				}
				//合计利息
				if (resultInfo.getWithDrawInterest() != 0.0)
				{
					printInfo.setRealInterest(resultInfo.getWithDrawInterest());
				}
				//计提利息
				if (resultInfo.getPreDrawInterest() > 0)
				{
					printInfo.setRealInterestReceivable(resultInfo.getPreDrawInterest());
				}
				//利息支付
				if (resultInfo.getPayableInterest() > 0)
				{
					printInfo.setPayableInterest(resultInfo.getPayableInterest());
				}
				//收息账号ID,据此可取收息账号
				if (resultInfo.getReceiveInterestAccountID() > 0)
				{
					printInfo.setReceiveInterestAccountID(resultInfo.getReceiveInterestAccountID());
				}
				//定期子账户，据此可得终息信息
				if (resultInfo.getSubAccountID() > 0)
				{
					printInfo.setSubAccountID(resultInfo.getSubAccountID());
				}
				//开始日期
				if (resultInfo.getStartDate() != null)
				{
					printInfo.setStartDate(resultInfo.getStartDate());
				}
				//结束日期
				if (resultInfo.getEndDate() != null)
				{
					printInfo.setEndDate(resultInfo.getEndDate());
				}
				//存款类型
				if (resultInfo.getTransactionTypeID() > 0)
				{
					printInfo.setTransTypeID(resultInfo.getTransactionTypeID());
				}		
		}  		
		
		double dTempAmount = printInfo.getAmount();
		
		if(resultInfo != null)
		{

			if ((strPrintPage.indexOf("5") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('5','a');
				lShow = 1;
				PrintVoucher.PrintFixedDepositOpen(printInfo,out);//打印定期存款开户证实书
				printInfo.setAmount(dTempAmount);
			}
			if ((strPrintPage.indexOf("7") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('7','a');
				lShow = 1;
				PrintVoucher.PrintDepositInterest(printInfo,out);//打印存款利息计付通知单
				printInfo.setAmount(dTempAmount);
			}

			if ((strPrintPage.indexOf("4") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('4','a');
				lShow = 1;
				PrintVoucher.PrintShowDebtor(printInfo,out);//打印特种转账借
				printInfo.setAmount(dTempAmount);
			}
			
			if ((strPrintPage.indexOf("2") >= 0) )
			{
				if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
				strPrintPage = strPrintPage.replace('2','a');
				lShow = 1;
				PrintVoucher.PrintShowCredit(printInfo,out);//打印特种转账贷
				printInfo.setAmount(dTempAmount);
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
	 	location.href='../a404-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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