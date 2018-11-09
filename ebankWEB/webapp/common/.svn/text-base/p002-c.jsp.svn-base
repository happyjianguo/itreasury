<%--
 页面名称 ：p002-c
 页面功能 : 记录打印日至
 作    者 ：qqgd
 日    期 ：
 修改历史 ：
--%>
	<%@ page contentType = "text/html;charset=gbk" %>
	<%@ page import="com.iss.itreasury.util.Env"%>
	<%@ page import="com.iss.itreasury.ebank.util.*"%>
	<%@ page import="com.iss.itreasury.util.Log"%>
	<%@ page import="com.iss.itreasury.util.Constant"%>
	<%@ page import="com.iss.itreasury.ebank.obprint.bizlogic.OBPrint"%>
	<%@ page import="com.iss.itreasury.ebank.obprint.dataentity.OBPrintLogInfo"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>  
<%
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
	      //定义变量		
		long clientID = sessionMng.m_lClientID;
		long transID=GetNumParam(request,"lID",-1);
		String strPrintPage = GetParam(request,"strPrintPage","");
		String printPage=GetParam(request,"printPage","");
		long hit[]={0,0,0,0};
		OBPrint biz=new OBPrint();
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(clientID);
		printInfo.setTransID(transID);
		printInfo.setAmountTypeID(-1);

		if ((strPrintPage.indexOf("0") >= 0) )//打印利息通知单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.INTEREST_LXTZD );
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("1") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("2") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("3") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("4") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("5") >= 0) )//打印定期开户证实书
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.FIXED_KHZS );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("6") >= 0) )//打印通知开户证实书
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.NOTIFY_KHZS );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("7") >= 0) )//打印存款利息计付通知
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DEPOSIT_CKLXJFTZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("8") >= 0) )//打印自营贷款发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("9") >= 0) )//打印委托贷款发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("p") >= 0) )//打印自营贷款收回凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("q") >= 0) )//打印委托贷款收回凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("r") >= 0) )//打印贴现发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DISCOUNT_TXFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("s") >= 0) )//打印贷款收回凭证（多笔贷款收回）
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.LOAN_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}

		String nextURL=printPage;
		//转向下一页面
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
		rd.forward( request,response );
}
catch( Exception exp )
{
	exp.printStackTrace();
}
%>