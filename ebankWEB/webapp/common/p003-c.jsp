<%--
 页面名称 ：p003-c
 页面功能 : 判断该单据是否已经被打印过，如果打印过让用户选择是否打印
 作    者 ：qqgd
 日    期 ：
 修改历史 ：
--%>
	<%@ page contentType = "text/html;charset=gbk" %>
	<%@ page import="com.iss.itreasury.util.Env"%>
	<%@ page import="com.iss.itreasury.util.Log"%>
	<%@ page import="com.iss.itreasury.ebank.util.*"%>
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
		long transactionTypeID=GetNumParam(request,"lTransactionTypeID",-1);
		String printPage=GetParam(request,"printPage","");
		long hit[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		OBPrint biz=new OBPrint();
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(clientID);
		printInfo.setTransID(transID);
	
		/****金额1*****/
		if ((strPrintPage.indexOf("b") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[0]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("c") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[1]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("d") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[2]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("e") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);			
			hit[3]=biz.getPrintTimes(printInfo);
		}
		
		/****金额2*****/
		if ((strPrintPage.indexOf("f") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[4]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("g") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[5]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("h") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[6]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("i") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);			
			hit[7]=biz.getPrintTimes(printInfo);
		}
		
		/****金额3*****/
		if ((strPrintPage.indexOf("j") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[8]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("k") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[9]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("l") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[10]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("m") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);			
			hit[11]=biz.getPrintTimes(printInfo);
		}
		
		/****金额4*****/
		if ((strPrintPage.indexOf("n") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[12]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("o") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[13]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("x") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[14]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("y") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);			
			hit[15]=biz.getPrintTimes(printInfo);
		}		
		
		String strMsg="您所选择的票据：";
		if (hit[0]>0)	strMsg+="[金额1-进账单]";
		if (hit[1]>0)   strMsg+="[金额1-特种转账贷方凭证]";
		if (hit[2]>0)	strMsg+="[金额1-存款支取凭证]";
		if (hit[3]>0)	strMsg+="[金额1-特种转账借方凭证]";
		if (hit[4]>0)	strMsg+="[金额2-进账单]";
		if (hit[5]>0)   strMsg+="[金额2-特种转账贷方凭证]";
		if (hit[6]>0)	strMsg+="[金额2-存款支取凭证]";
		if (hit[7]>0)	strMsg+="[金额2-特种转账借方凭证]";
		if (hit[8]>0)	strMsg+="[金额3-进账单]";
		if (hit[9]>0)   strMsg+="[金额3-特种转账贷方凭证]";
		if (hit[10]>0)	strMsg+="[金额3-存款支取凭证]";
		if (hit[11]>0)	strMsg+="[金额3-特种转账借方凭证]";
		if (hit[12]>0)	strMsg+="[金额4-进账单]";
		if (hit[13]>0)  strMsg+="[金额4-特种转账贷方凭证]";
		if (hit[14]>0)	strMsg+="[金额4-存款支取凭证]";
		if (hit[15]>0)	strMsg+="[金额4-特种转账借方凭证]";
		
		strMsg+=" 已经打印过了，是否继续打印？";
		long hitLong = 0;
		for (int i = 0; i < 16; i++) {
			hitLong += hit[i];
		}
		if (hitLong > 0)
		{
%>
<html>
<body>
	<script language="javascript">
		if ( confirm("<%=strMsg%>") )
		{
			window.location="<%=Env.EBANK_URL%>common/p004-c.jsp?lID=<%=transID%>&strPrintPage=<%=strPrintPage%>&lTransactionTypeID=<%=transactionTypeID%>&printPage=<%=printPage%>";
		}
		else
		{
			window.close();
		}	
	</script>
</body>
</html>	
<%		
		}
		else
		{
			String nextURL="/common/p004-c.jsp";
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
}
catch( Exception exp )
{
	exp.printStackTrace();
}
%>