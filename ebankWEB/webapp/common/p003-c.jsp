<%--
 ҳ������ ��p003-c
 ҳ�湦�� : �жϸõ����Ƿ��Ѿ�����ӡ���������ӡ�����û�ѡ���Ƿ��ӡ
 ��    �� ��qqgd
 ��    �� ��
 �޸���ʷ ��
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
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	      //�������		
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
	
		/****���1*****/
		if ((strPrintPage.indexOf("b") >= 0) )//��ӡ���˵�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[0]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("c") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[1]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("d") >= 0) )//��ӡ���֧ȡƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);
			hit[2]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("e") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE0);			
			hit[3]=biz.getPrintTimes(printInfo);
		}
		
		/****���2*****/
		if ((strPrintPage.indexOf("f") >= 0) )//��ӡ���˵�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[4]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("g") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[5]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("h") >= 0) )//��ӡ���֧ȡƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);
			hit[6]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("i") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE1);			
			hit[7]=biz.getPrintTimes(printInfo);
		}
		
		/****���3*****/
		if ((strPrintPage.indexOf("j") >= 0) )//��ӡ���˵�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[8]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("k") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[9]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("l") >= 0) )//��ӡ���֧ȡƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);
			hit[10]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("m") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE2);			
			hit[11]=biz.getPrintTimes(printInfo);
		}
		
		/****���4*****/
		if ((strPrintPage.indexOf("n") >= 0) )//��ӡ���˵�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[12]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("o") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[13]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("x") >= 0) )//��ӡ���֧ȡƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);
			hit[14]=biz.getPrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("y") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );
			printInfo.setAmountTypeID(OBConstant.AmountType.AMOUNT_TYPE3);			
			hit[15]=biz.getPrintTimes(printInfo);
		}		
		
		String strMsg="����ѡ���Ʊ�ݣ�";
		if (hit[0]>0)	strMsg+="[���1-���˵�]";
		if (hit[1]>0)   strMsg+="[���1-����ת�˴���ƾ֤]";
		if (hit[2]>0)	strMsg+="[���1-���֧ȡƾ֤]";
		if (hit[3]>0)	strMsg+="[���1-����ת�˽跽ƾ֤]";
		if (hit[4]>0)	strMsg+="[���2-���˵�]";
		if (hit[5]>0)   strMsg+="[���2-����ת�˴���ƾ֤]";
		if (hit[6]>0)	strMsg+="[���2-���֧ȡƾ֤]";
		if (hit[7]>0)	strMsg+="[���2-����ת�˽跽ƾ֤]";
		if (hit[8]>0)	strMsg+="[���3-���˵�]";
		if (hit[9]>0)   strMsg+="[���3-����ת�˴���ƾ֤]";
		if (hit[10]>0)	strMsg+="[���3-���֧ȡƾ֤]";
		if (hit[11]>0)	strMsg+="[���3-����ת�˽跽ƾ֤]";
		if (hit[12]>0)	strMsg+="[���4-���˵�]";
		if (hit[13]>0)  strMsg+="[���4-����ת�˴���ƾ֤]";
		if (hit[14]>0)	strMsg+="[���4-���֧ȡƾ֤]";
		if (hit[15]>0)	strMsg+="[���4-����ת�˽跽ƾ֤]";
		
		strMsg+=" �Ѿ���ӡ���ˣ��Ƿ������ӡ��";
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
			//ת����һҳ��
			
			//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(nextURL);
	//�ַ�
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