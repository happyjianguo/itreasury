<%--
 页面名称 ：p001-c
 页面功能 : 判断该单据是否已经被打印过，如果打印过让用户选择是否打印
 作    者 ：qqgd
 日    期 ：
 修改历史 ：
--%>
	<%@ page contentType = "text/html;charset=gbk" %>
	<%@ page import="com.iss.itreasury.util.Env"%>
	<%@ page import="com.iss.itreasury.util.Log"%>
	<%@ page import="com.iss.itreasury.ebank.util.*"%>
	<%@ page import="com.iss.itreasury.util.Constant,javax.servlet.*"%>
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
		String strTransNo=GetParam(request,"strTransNo","");
		String printPage=GetParam(request,"printPage","");
		long hit[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		OBPrint biz=new OBPrint();
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(clientID);
		printInfo.setTransID(transID);
		printInfo.setAmountTypeID(-1);

		if ((strPrintPage.indexOf("0") >= 0) )//打印利息通知单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.INTEREST_LXTZD );
			hit[0]=biz.getPrintTimes(printInfo);
		}	
		if ((strPrintPage.indexOf("1") >= 0) )//打印进账单
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			hit[1]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("2") >= 0) )//打印特转贷
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			hit[2]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("3") >= 0) )//打印存款支取凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			hit[3]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("4") >= 0) )//打印特转借
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );			
			hit[4]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("5") >= 0) )//打印定期开户证实书
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.FIXED_KHZS );			
			hit[5]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("6") >= 0) )//打印通知开户证实书
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.NOTIFY_KHZS );			
			hit[6]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("7") >= 0) )//打印存款利息计付通知
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DEPOSIT_CKLXJFTZ );			
			hit[7]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("8") >= 0) )//打印自营贷款发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKFFPZ );			
			hit[8]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("9") >= 0) )//打印委托贷款发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKFFPZ );			
			hit[9]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("p") >= 0) )//打印自营贷款收回凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKSHPZ );			
			hit[10]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("q") >= 0) )//打印委托贷款收回凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKSHPZ );			
			hit[11]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("r") >= 0) )//打印贴现发放凭证
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DISCOUNT_TXFFPZ );			
			hit[12]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("s") >= 0) )//打印贷款收回凭证（多笔贷款收回）
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.LOAN_DKSHPZ );			
			hit[13]=biz.getPrintTimes(printInfo);
		}

		String strMsg="您所选择的票据：";
		if (hit[0]>0)	strMsg+="[利息通知单]";
		if (hit[1]>0)	strMsg+="[进账单]";
		if (hit[2]>0)   strMsg+="[特种转账贷方凭证]";
		if (hit[3]>0)	strMsg+="[存款支取凭证]";
		if (hit[4]>0)	strMsg+="[特种转账借方凭证]";
		if (hit[5]>0)	strMsg+="[定期存款开户证实书]";
		if (hit[6]>0)	strMsg+="[通知存款开户证实书]";
		if (hit[7]>0)	strMsg+="[存款利息计付通知单]";
		if (hit[8]>0)	strMsg+="[自营贷款发放凭证]";
		if (hit[9]>0)	strMsg+="[委托贷款发放凭证]";
		if (hit[10]>0)	strMsg+="[自营贷款收回凭证]";
		if (hit[11]>0)	strMsg+="[委托贷款收回凭证]";
		if (hit[12]>0)	strMsg+="[贴现发放凭证]";
		if (hit[13]>0)	strMsg+="[贷款收回凭证]";
		strMsg+=" 已经打印过了，是否继续打印？";
		
		if ( (hit[0]+hit[1]+hit[2]+hit[3]+hit[4]+hit[5]+hit[6]+hit[7]+hit[8]+hit[9]+hit[10]+hit[11]+hit[12]+hit[13])>0 )
		{
%>
<html>
<body>
	<script language="javascript">
		if ( confirm("<%=strMsg%>") )
		{
			window.location="<%=Env.EBANK_URL%>common/p002-c.jsp?lID=<%=transID%>&strPrintPage=<%=strPrintPage%>&lTransactionTypeID=<%=transactionTypeID%>&printPage=<%=printPage%>&strTransNo=<%=strTransNo%>";
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
			String nextURL="/common/p002-c.jsp";
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