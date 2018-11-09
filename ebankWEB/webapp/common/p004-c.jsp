<%--
 页面名称 ：p004-c
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
		OBPrint biz=new OBPrint();
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(clientID);
		printInfo.setTransID(transID);
	
		/****金额1*****/
		if ((strPrintPage.indexOf("b") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("c") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("d") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("e") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);			
			biz.updatePrintTimes(printInfo);
		}
		
		/****金额2*****/
		if ((strPrintPage.indexOf("f") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("g") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("h") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("i") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);			
			biz.updatePrintTimes(printInfo);
		}
		
		/****金额3*****/
		if ((strPrintPage.indexOf("j") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("k") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("l") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("m") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);			
			biz.updatePrintTimes(printInfo);
		}
		
		/****金额4*****/
		if ((strPrintPage.indexOf("n") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("o") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("x") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("y") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);			
			biz.updatePrintTimes(printInfo);
		}		

		String nextURL=printPage;
		//转向下一页面
		
		
		
		
		
		
		//构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextURL);
	//分发
	RequestDispatcher  rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
		
		
		
		rd.forward( request,response );
}
catch( Exception exp )
{
	exp.printStackTrace();
}
%>