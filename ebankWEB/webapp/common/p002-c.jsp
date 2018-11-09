<%--
 ҳ������ ��p002-c
 ҳ�湦�� : ��¼��ӡ����
 ��    �� ��qqgd
 ��    �� ��
 �޸���ʷ ��
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
		String printPage=GetParam(request,"printPage","");
		long hit[]={0,0,0,0};
		OBPrint biz=new OBPrint();
		OBPrintLogInfo printInfo=new OBPrintLogInfo();
		printInfo.setClientID(clientID);
		printInfo.setTransID(transID);
		printInfo.setAmountTypeID(-1);

		if ((strPrintPage.indexOf("0") >= 0) )//��ӡ��Ϣ֪ͨ��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.INTEREST_LXTZD );
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("1") >= 0) )//��ӡ���˵�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH1 );
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("2") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.PAY_VOUCH2 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("3") >= 0) )//��ӡ���֧ȡƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH1 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("4") >= 0) )//��ӡ��ת��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.REPAY_VOUCH2 );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("5") >= 0) )//��ӡ���ڿ���֤ʵ��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.FIXED_KHZS );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("6") >= 0) )//��ӡ֪ͨ����֤ʵ��
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.NOTIFY_KHZS );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("7") >= 0) )//��ӡ�����Ϣ�Ƹ�֪ͨ
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DEPOSIT_CKLXJFTZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("8") >= 0) )//��ӡ��Ӫ�����ƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("9") >= 0) )//��ӡί�д����ƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("p") >= 0) )//��ӡ��Ӫ�����ջ�ƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.TRUST_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("q") >= 0) )//��ӡί�д����ջ�ƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.CONSIGN_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("r") >= 0) )//��ӡ���ַ���ƾ֤
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.DISCOUNT_TXFFPZ );			
			biz.updatePrintTimes(printInfo);
		}
		if ((strPrintPage.indexOf("s") >= 0) )//��ӡ�����ջ�ƾ֤����ʴ����ջأ�
		{
			printInfo.setVouchTypeID( OBConstant.VouchType.LOAN_DKSHPZ );			
			biz.updatePrintTimes(printInfo);
		}

		String nextURL=printPage;
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
catch( Exception exp )
{
	exp.printStackTrace();
}
%>