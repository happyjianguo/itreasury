<%--
/**
 页面名称 ：c002.jsp
 页面功能 : 定期开立打印控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-20
 特殊说明 ：实现操作说明：findByID取得所有信息
            定期开立    存款支取凭证
                        定期存款开户证实书
           通知存款开立    存款支取凭证
                        通知存款开户证实书
 * 修改历史 ：
 */
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
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO"%>
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
		long lTransactionTypeID = 0;
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
		Log.print("===========lID="+lID);
        strTemp = (String)request.getAttribute("strPrintPage");
        if (strTemp != null && strTemp.trim().length() > 0)
        {
            strPrintPage = strTemp;
        }
		Log.print("===========strPrintPage="+strPrintPage);
        strTemp = (String)request.getAttribute("lTransTypeID");
        if (strTemp != null && strTemp.trim().length() > 0)
        {
            lTransactionTypeID = Long.parseLong(strTemp);
        }
		Log.print("===========lTransactionTypeID="+lTransactionTypeID);
        //TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
        //查找开立信息
        TransFixedOpenInfo resultInfo = null;
        //resultInfo = depositDelegation.openFindByID(lID);
		resultInfo = dao.findByID(lID);
        PrintInfo printInfo = new PrintInfo();
        //添加开立信息
        if(resultInfo!=null) {
            if(resultInfo.getOfficeID() > 0) {
                printInfo.setOfficeID(resultInfo.getOfficeID());
            }
            if(resultInfo.getCurrencyID() > 0) {
                printInfo.setCurrencyID(resultInfo.getCurrencyID());
            }
            if(resultInfo.getBankID() > 0) {
                printInfo.setPayBankID(resultInfo.getBankID());
            }
            if(resultInfo.getBillBankID() > 0) {
                request.setAttribute("lBillBankID",String.valueOf(resultInfo.getBillBankID()));
            }
            if(resultInfo.getBillNo() != null && resultInfo.getBillNo().length() > 0) {
                request.setAttribute("lBillBankIDCtrl",resultInfo.getBillNo());
            }
            if(resultInfo.getBillTypeID() > 0) {
                request.setAttribute("lBillTypeID",String.valueOf(resultInfo.getBillTypeID()));
            }
            if(resultInfo.getExtBankNo() != null && resultInfo.getExtBankNo().length() > 0) {
                request.setAttribute("strExtBankNo",resultInfo.getExtBankNo());
            }
            if(resultInfo.getAccountID() > 0) {
                printInfo.setReceiveAccountID(resultInfo.getAccountID());
            }
            if(resultInfo.getAbstractID() > 0) {
                printInfo.setAbstractID(resultInfo.getAbstractID());
            }
            if(resultInfo.getAbstract() != null && resultInfo.getAbstract().length() > 0) {
                printInfo.setAbstract(resultInfo.getAbstract());
            }
            if(resultInfo.getInputUserID() > 0) {
                printInfo.setInputUserID(resultInfo.getInputUserID());
            }
            if(resultInfo.getCheckUserID() > 0) {
                printInfo.setCheckUserID(resultInfo.getCheckUserID());
            }
            if(resultInfo.getTransNo() != null && resultInfo.getTransNo().length() > 0) {
                printInfo.setTransNo(resultInfo.getTransNo());
            }
            //开户日
            if(resultInfo.getExecuteDate() != null) {
                printInfo.setExecuteDate(resultInfo.getExecuteDate());
            }
            //起息日
            if(resultInfo.getInterestStartDate() != null) {
                printInfo.setInterestStartDate(resultInfo.getInterestStartDate());
            }
            //开立客户ID，可据此得开户名称
            if(resultInfo.getClientID() > 0) {
                printInfo.setReceiveClientID(resultInfo.getClientID());
            }
            //账号，收款方账号
            if(resultInfo.getAccountID() > 0) {
                printInfo.setReceiveAccountID(resultInfo.getAccountID());
            }
            //收款方银行可根据officeid取?
            //收款人汇入地址?
            //收款人汇入行名称?
            //存单号
            if(resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0) {
                printInfo.setFixedDepositNo(resultInfo.getDepositNo());
            }
            //金额
            if(resultInfo.getAmount() != 0.0) {
                printInfo.setAmount(resultInfo.getAmount());
            }
            //付款方账号ID,据此可得付款方全称，付款方账号
            if(resultInfo.getCurrentAccountID() > 0) {
                printInfo.setPayAccountID(resultInfo.getCurrentAccountID());
            }
            //付款方银行，据此可得银行名称
            if(resultInfo.getBankID() > 0) {
                printInfo.setPayBankID(resultInfo.getBankID());
            }
            //付款方
            if(resultInfo.getDepositNo() != null && resultInfo.getDepositNo().length() > 0) {
                printInfo.setFixedDepositNo(resultInfo.getDepositNo());
            }
            //付款人汇入行名称
            if (resultInfo.getRemitInBank() != null && resultInfo.getRemitInBank().length() > 0) {
                printInfo.setExtRemitInBank(resultInfo.getRemitInBank());
            }
            //省
            if (resultInfo.getRemitInProvince() != null && resultInfo.getRemitInProvince().length() > 0) {
                printInfo.setExtRemitInProvince(resultInfo.getRemitInProvince());
            }
            //市
            if (resultInfo.getRemitInCity() != null && resultInfo.getRemitInCity().length() > 0) {
                printInfo.setExtRemitInCity(resultInfo.getRemitInCity());
            }
            //期限
            if (resultInfo.getDepositTerm() > 0) {
                printInfo.setFixedDepositTerm(resultInfo.getDepositTerm());
            }
            //利率
            if (resultInfo.getRate() != 0.0) {
                printInfo.setRate(resultInfo.getRate());
            }
            //品种
            if (resultInfo.getNoticeDay() > 0) {
                printInfo.setNoticeDay(resultInfo.getNoticeDay());
            }
        }
        if(resultInfo != null) {
            if ((strPrintPage.indexOf("3") >= 0)) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('3','a');
                lShow = 1;
                PrintVoucher.PrintShowWithDraw(printInfo,out);//打印存款支取凭证
            }
			
            if ((strPrintPage.indexOf("5") >= 0)) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('5','a');
                lShow = 1;
                PrintVoucher.PrintFixedDepositOpen(printInfo,out);//打印定期存款开户证实书
            }
			
            if ((strPrintPage.indexOf("6") >= 0)) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('6','a');
                lShow = 1;
                PrintVoucher.PrintNoticeOpen(printInfo,out);//打印通知存款开户证实书
            }
			
            if ((strPrintPage.indexOf("b") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('b','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(1,1,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("c") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('c','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(2,1,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("d") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('d','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(3,1,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("e") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('e','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(4,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("f") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('f','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("g") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('g','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(6,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("h") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('h','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(7,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("i") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('i','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(8,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("j") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('j','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(5,2,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("k") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('k','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(6,3,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("l") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('l','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(7,3,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("m") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('m','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(8,3,printInfo,out);//套打
            }
            if ((strPrintPage.indexOf("n") >= 0) && lShow < 0) {
                if (lShow ==1) out.println("<br clear=all style='page-break-before:always'>");
                strPrintPage = strPrintPage.replace('n','a');
                lShow = 1;
                PrintVoucher.PrintTemplate(8,1,printInfo,out);//套打
            }
            if (lShow < 0) {
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
function keyDown(DnEvents) {
    k = (netscape)?DnEvents.which:window.event.keyCode;
    if (k == 13) {
        location.href='../a402-c.jsp?lID=<%=lID%>&strPrintPage=<%=strPrintPage%>';
    }
}

document.onkeydown = keyDown;// work together to analyze keystrokes
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