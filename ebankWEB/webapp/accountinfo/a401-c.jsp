<%--
 页面名称 ：a401-c.jsp
 页面功能 : 活期打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
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
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.print.PrintInfo"%>
<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.PrintVoucher"%>
<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO"%>
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
		strTemp = (String)request.getAttribute("lID");//交易ID
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strPrintPage");//打印的单据类型
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPrintPage = strTemp;
		}

		//TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();
		Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();

		TransCurrentDepositInfo resultInfo = null;
		
		//resultInfo = depositDelegation.findBySett_TransCurrentDepositID(lID);
		resultInfo = dao.findByID(lID);
  		
		PrintInfo printInfo = new PrintInfo();
		
		if(resultInfo != null)
		{
		Log.print("result not null");
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

			//在银行付款中-bankid 为付款银行
			if(resultInfo.getBankID() > 0 )
			{
				printInfo.setPayBankID(resultInfo.getBankID());
			}
			
			//在银行收款中-bankid 为收款银行
			if(resultInfo.getBankID() > 0 )
			{
				printInfo.setReceiveBankID(resultInfo.getBankID());
			}
			
			/*
			if(resultInfo.getBillBankID() > 0 )
			{
				request.setAttribute("lBillBankID",String.valueOf(resultInfo.getBillBankID()));
			}
			if(resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0)
			{
				request.setAttribute("lBillBankIDCtrl",resultInfo.getBillNo());
			}
			if(resultInfo.getBillTypeID() > 0 )
			{
				request.setAttribute("lBillTypeID",String.valueOf(resultInfo.getBillTypeID()));
			}
			if(resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0)
			{
				request.setAttribute("strExtBankNo",resultInfo.getExtBankNo());
			}
			if(resultInfo.getInstructionNo() != null && resultInfo.getInstructionNo().length() > 0)
			{
				request.setAttribute("strInstructionNo",resultInfo.getInstructionNo());
			}
*/			
			if(resultInfo.getPayAccountID() > 0 )
			{
				printInfo.setPayAccountID(resultInfo.getPayAccountID());
			}
			if(resultInfo.getReceiveAccountID() > 0 )
			{
				printInfo.setReceiveAccountID(resultInfo.getReceiveAccountID());
			}
			
			if(resultInfo.getReceiveClientID() > 0 )
			{
				printInfo.setReceiveClientID(resultInfo.getReceiveClientID());
			}
			if(resultInfo.getPayClientID() > 0 )
			{
				printInfo.setPayClientID(resultInfo.getPayClientID());
			}
			if(resultInfo.getAbstractID() > 0 )
			{
				printInfo.setAbstractID(resultInfo.getAbstractID());
			}
			if(resultInfo.getExtAccountNo() != null && resultInfo.getExtAccountNo().length() > 0)
			{
				printInfo.setExtAccountNo(resultInfo.getExtAccountNo());
			}
			Log.print("外部账号:"+printInfo.getExtAccountNo());
			
			if(resultInfo.getExtClientName() != null && resultInfo.getExtClientName().length() > 0)
			{
				printInfo.setExtClientName(resultInfo.getExtClientName());
			}
			Log.print("外部客户名称:"+printInfo.getExtClientName());
			
			if(resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0)
			{
				printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
			}
			Log.print("外部银行:"+printInfo.getExtRemitInBank());
			
			if(resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0)
			{
				printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
			}
			
			Log.print("外部银行省:"+printInfo.getExtRemitInProvince());
			
			if(resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0)
			{
				printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
			}
			Log.print("外部银行市:"+printInfo.getExtRemitInCity());
			
			if(resultInfo.getAbstractStr() != null && resultInfo.getAbstractStr().length() > 0)
			{
				printInfo.setAbstract(resultInfo.getAbstractStr());
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
				PrintVoucher.PrintShowWithDraw(printInfo,out);//打印存款支取凭证
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
	 	location.href='../a401-c?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
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
