<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,	
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.system.bizlogic.*,
				com.iss.itreasury.system.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.ebank.obrepayplan.bizlogic.*,
				com.iss.itreasury.ebank.obrepayplan.dataentity.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,	
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.repayplan.dataentity.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.repayplan.bizlogic.*,
				com.iss.itreasury.ebank.approval.bizlogic.*,
				com.iss.itreasury.ebank.approval.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>

<%
		//log4j //log4j = new //log4j(Constant.ModuleType.LOAN);
		String strTitle  = "ί�д����⻹���롪�⻹֪ͨ����ϸ";
		String strOfficeName = sessionMng.m_strOfficeName;
		String strUserName = sessionMng.m_strUserName;
		long lUserID = sessionMng.m_lUserID;
		long lClientID = sessionMng.m_lClientID;
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
try
{

	        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		//�������
		

		String strTmp = "";
		String strFreeCode = "";
		String strClientName ="";
		double mPayInterest  = 0;
		long lID = -1;
		String[] strOpinion = {"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] strCheckName = {"&nbsp;","&nbsp;","&nbsp;","&nbsp;"};
		String[] sDate = {"&nbsp;&nbsp;�ꡡ�¡���","&nbsp;&nbsp;�ꡡ�¡���","&nbsp;&nbsp;�ꡡ�¡���","&nbsp;&nbsp;�ꡡ�¡���"};

		//��ȡ�������

///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
////////////////////////////////////////////////////////////////////*/
		//OBRepayPlanHome payplanhome = (OBRepayPlanHome) EJBObject.getEJBHome("OBRepayPlanHome");
		//OBRepayPlan payplanejb = payplanhome.create();
		OBContractQuery repayplan = new OBContractQuery();
		//RepayPlanInfo payplaninfo = new RepayPlanInfo();
////////////////////////////////////////////////////////////////////*/
		//�����������ȡ�������

		String strLoanClientName="";//������� ����/����
		strLoanClientName=Env.getClientName();
		//String strTmp = "";
		String strAction = "";
		String strControl = "";
		String strBackURL = "";
		int backflag = 0;
		long lFreeApplyID = -1;
		
		long lTypeID=-1;
		long lContractID=-1;		//��ͬ��ʾ
		String strContractCode="";	//��ͬ���
		double dAmount = 0;//������
		long nIntervalNum = -1;//��������
		double dBalance = 0;//�������
		double dLoanRate=0;//��������
		double dChargeRate = 0;//��������
		String strLoanPurpose="";//�����;
		String strBorrowClientName="";	//��λ����
		String strConsignClientName="";	//ί�е�λ����
		String strConsignClientAccount = "";//ί���˻�

		long lLoanPayID=-1;//�ſ�֪ͨ��ID
		String strLoanPayCode=null;	//�ſ�֪ͨ�����
		double dPayAmount = 0;//�ſ���
		double dPayBalance = 0;//�ſ����
		Timestamp dtEndDate = null;//������
		Timestamp dtStartDate = null;//������

		double mFreeAmount = 0;//�⻹���
		double mFreeRate = 0;//�⻹��Ϣ
		Timestamp dtFreeDate = null;//�⻹��
		String strFreeReason = "";//�⻹ԭ��
		long lStatusID = -1;//�⻹״̬
		long lInputUserID = -1;//¼����
		String strInputUserName="";//¼��������
		long lCheckUserID=-1;//�����
		String strCheckUserName="";//���������
		Timestamp dtInputDate=null;//¼������
		Timestamp dtToday = Env.getSystemDate();//��������

///////control/////////////////////////////////////////////

		freeinfo = (FreeApplyInfo)request.getAttribute("freeinfo");
		//System.out.println("stop here!!!");
///////////////////////////////////////view////////////////////////////////////////////////
				strFreeCode=freeinfo.getFreeCode();
				mFreeAmount=freeinfo.getFreeAmount();						//�⻹���
				mFreeRate=freeinfo.getFreeRate();							//�⻹��Ϣ
				dtFreeDate=freeinfo.getFreeDate();							//�⻹��
				strFreeReason=freeinfo.getFreeReason();						//�⻹ԭ��
				strConsignClientAccount=freeinfo.getConsignClientAccount();	//ί���˻�
				lStatusID=freeinfo.getStatusID();							//�⻹״̬
				lInputUserID=freeinfo.getInputUserID();//¼����ID
				lCheckUserID=freeinfo.getCheckUserID();//�����ID
				strInputUserName=freeinfo.getInputUserName();//¼��������
				strCheckUserName=freeinfo.getCheckUserName();//���������
				dtInputDate=freeinfo.getInputDate();//¼������
				//--------------------------------------------------------------------------//
				strContractCode=freeinfo.getContractCode();			//��ͬ���
				dAmount =freeinfo.getLoanAmount();				//������
				nIntervalNum =freeinfo.getIntervalNum();			//����
				dLoanRate=freeinfo.getInterestRate();				//����ִ������
				dBalance =freeinfo.getBalance();					//������δ�����
				strLoanPurpose=freeinfo.getLoanPurpose();			//�����;
				strClientName=freeinfo.getClientName();				//��λ����
				//dtEndDate = freeinfo.getEndDate();					//������
				//lPlanID = freeinfo.getPlanVersionID();
				//---------------------------------------------------------------------------//
				strConsignClientName=freeinfo.getConsignClientName();//ί�е�λ����
				strLoanPayCode=freeinfo.getLoanPayCode();			//�ſ�֪ͨ�����
				dPayAmount=freeinfo.getLoanPayAmount();				//�ſ���
				dPayBalance=freeinfo.getLoanPayBalance();			//�ſ����
				mPayInterest=freeinfo.getLoanPayInterest();					//�ſ���Ϣ
				dtEndDate = freeinfo.getPayLoanEndDate();						//������
				dtStartDate=freeinfo.getPayLoanStartDate();						//������
				dChargeRate=freeinfo.getChargeRate();//��������
				
				strBorrowClientName=freeinfo.getClientName();
				
	
		// ������Ӧ��������
//*****************************************************************************************
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
		//����
		long lModuleID = Constant.ModuleType.LOAN;
		//ģ������
		long lLoanTypeID = lTypeID;
		long lActionID = Constant.ApprovalAction.FREE_APPLY;
		//��ʷ���
		Collection ColappBiz = null;
		long lDesc1 = Constant.PageControl.CODE_ASCORDESC_DESC;//�������

		ColappBiz = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,lOfficeID,lCurrencyID,lFreeApplyID,lDesc1);
		if (ColappBiz != null) 
		{
			if(ColappBiz.size() >=4)
			{
				Iterator iter = ColappBiz.iterator();
				int nOpeinion = 3;
				while (iter.hasNext() && nOpeinion >= 0)
				{
					//log4j.info(">=4");
					tracinginfo = (ApprovalTracingInfo)iter.next();
					strOpinion[nOpeinion] = DataFormat.formatString(tracinginfo.getOpinion());
					strCheckName[nOpeinion] = DataFormat.formatString(tracinginfo.getUserName());
					sDate[nOpeinion] = DataFormat.getChineseDateString(tracinginfo.getApprovalDate());
					nOpeinion--;
				}
			}
			else
			{
				Iterator iter = ColappBiz.iterator();
				int nOpeinion = ColappBiz.size()-1;
				while (iter.hasNext() && nOpeinion >= 0)
				{
					//log4j.info("<4");
					tracinginfo = (ApprovalTracingInfo)iter.next();
					strOpinion[nOpeinion] = DataFormat.formatString(tracinginfo.getOpinion());
					strCheckName[nOpeinion] = DataFormat.formatString(tracinginfo.getUserName());
					sDate[nOpeinion] = DataFormat.getChineseDateString(tracinginfo.getApprovalDate());
					nOpeinion--;
				}
			}
		}
	//*****************************************************************************************
		//payplanejb
		Collection ColpayPlan = null;
		long lPageLineCount = Constant.PageControl.CODE_PAGELINECOUNT;//ÿҳ��ʾ��¼��
		long lPageNo=1;
		long lOrderParam = 1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;//
		
		OBPageInfo pageinfo = new OBPageInfo();
		OBSecurityInfo sinfo = new OBSecurityInfo();
		
		pageinfo.setPageLineCount(lPageLineCount);
		pageinfo.setPageNo(lPageNo);
		pageinfo.setOrderParam(lOrderParam);
		pageinfo.setDesc(lDesc);
			
		sinfo.setOfficeID(lOfficeID);
		sinfo.setCurrencyID(lCurrencyID);
		sinfo.setUserID(lUserID);
		sinfo.setClientID(lClientID);
		
		//ColpayPlan = payplanejb.findPlanByContract(lContractID,pageinfo,sinfo);
		Collection c = null;
		//c=repayplan.findPlanByVer(freeinfo.getPlanVersionID(),Constant.PageControl.CODE_PAGELINECOUNT,0,lOrderParam,lDesc,sessionMng.m_lUserID,sessionMng.m_lOfficeID);
		c=repayplan.findPlanByContract(freeinfo.getContractID(),1000,1,1,1);
		//c=repayplan.findPlanDetailByContractID(freeinfo.getContractID());
		int nPlan=0;
		RepayPlanInfo rp_info = new RepayPlanInfo();
		
		if (c != null)
		{
			nPlan = c.size();

			//log4j.info("nPlan:"+nPlan);
		}
		String[] strPayTime=new String[nPlan];
		String[] strRepayTime=new String[nPlan];
		String[] strPayAmount=new String[nPlan];
		String[] strRepayAmount=new String[nPlan];
		double dTotalPay = 0;
		double dTotalRepay = 0;
		int iPay=0;
		int iRepay=0;
		if (c != null)
		{
			Iterator iterPlan = c.iterator();
			while(iterPlan!=null && iterPlan.hasNext())
			{
				rp_info = (RepayPlanInfo)iterPlan.next();
				if(rp_info.nLoanOrRepay==LOANConstant.PlanType.PAY)//�ÿ�ſ
				{
					if(rp_info.tsPlanDate!=null)
					{
						strPayTime[iPay]=DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strPayTime[iPay]="&nbsp;";
					}
					if(rp_info.dAmount>=0)
					{
						strPayAmount[iPay]=DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strPayAmount[iPay]="&nbsp;";
					}
					dTotalPay = dTotalPay + rp_info.dAmount;
					iPay++;
				}
				else//����
				{
					if(rp_info.tsPlanDate!=null)
					{
						strRepayTime[iRepay]=DataFormat.getChineseDateString(rp_info.tsPlanDate);
					}
					else
					{
						strRepayTime[iRepay]="&nbsp;";
					}
					if(rp_info.dAmount>=0)
					{
						strRepayAmount[iRepay]=DataFormat.formatListAmount(rp_info.dAmount);
					}
					else
					{
						strRepayAmount[iRepay]="&nbsp;";
					}
					dTotalRepay = dTotalRepay + rp_info.dAmount;
					iRepay++;
				}
			}//end while(iter.hasNext())
		}
		nPlan=iRepay>iPay?iRepay:iPay;


//*****************************************************************************************
%>
<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gbk">
<title>ί�д����⻹֪ͨ��</title>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<style type="text/css">
<!--
.table1 {  border: 2px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
-->
</style>
</head>
	

<body bgcolor="#FFFFFF">
<table width="630" border="0" cellspacing="0" cellpadding="0" height="960">
<tr>
<td valign="top" align="center">
<table width="100%" border="0">

<tr>
<td align="center" width="25%"><font size="5"></font></td>
<td align="center" width="50%"><strong><font size="5"><b>ί�д����⻹֪ͨ��</b></font></strong></td>
<td align="center" width="25%">&nbsp;</td>
</tr>
<tr>
<td align="right" width="25%">&nbsp;</td>
<td align="right">&nbsp;</td>
<td align="right" width="25%">&nbsp;</td>
</tr>
</table>
<font size="4"></font>
<table width="100%" border="0" cellspacing="0" cellpadding="4" class="table1" height="850">
<tr>
<td class="td-rightbottom" align="center" height="36">ί�е�λ</td>
<td class="td-rightbottom" align="center" height="36" width="30%"><%=strConsignClientName==null?"&nbsp;":strConsignClientName%>&nbsp;</td>
<td class="td-rightbottom" width="20%" align="center">ί�д���˺�</td>
<td class="td-bottom" width="30%"><%=strConsignClientAccount==null?"&nbsp;":strConsignClientAccount%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36" width="12%">��λ</td>
<td class="td-rightbottom" align="center" height="36" colspan="1"><%=strBorrowClientName==null?"&nbsp;":strBorrowClientName%>&nbsp;</td>
<td class="td-rightbottom" align="center" height="36" width="12%">��ͬ��</td>
<td class="td-bottom"><%=strContractCode==null?"&nbsp;":strContractCode%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">�����</td>
<%String Amount = DataFormat.formatAmount(dAmount);%>
<td class="td-bottom" height="36"  colspan=3>�����(��д)
					<%=ChineseCurrency.showChinese(Amount)%>(<%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(dAmount,2)%>)</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">��������</td>
<td class="td-rightbottom" align="center" height="36"><%=dLoanRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dLoanRate))+"%"%>&nbsp;</td>
<td class="td-rightbottom" align="center">��������</td>
<td class="td-bottom" align="center"><%=dChargeRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dChargeRate))+"%"%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">�ڡ�����</td>
<td class="td-rightbottom" align="center" colspan=1><%if ((freeinfo!=null)&&(nIntervalNum>0)) {out.println(nIntervalNum);} else {out.println("&nbsp;");}%>����</td>
<td class="td-rightbottom" align="center">��������</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtStartDate)%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36">�������</td>
<td class="td-rightbottom" align="center" height="36"><%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(dBalance,2)%></td>
<td class="td-rightbottom" align="center">������</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtEndDate)%>&nbsp;</td>
</tr>
<tr>
<td class="td-rightbottom" align="center" height="36"> �⻹���</td>
<td class="td-rightbottom" align="center" height="36"><%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatNumber(mFreeAmount,2)%></td>
<td class="td-rightbottom" align="center">�⻹��</td>
<td class="td-bottom"><%=DataFormat.getDateString(dtFreeDate)%>&nbsp;</td>
</tr>





<tr>
<td class="td-bottom" colspan="4" height="150">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">

<tr valign="top">
<td colspan="2" height="30">�⻹ԭ�򣺣�ί�е�λ��д��</td>
</tr>
<tr>
<td colspan="2" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<%=DataFormat.formatString(strFreeReason)%></td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%">ί�е�λ���£�</td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%">�����ˣ�</td>
</tr>
<tr>
<td width="69%">&nbsp;</td>
<td width="31%" align="center" height="40"><br>
�ꡡ�¡���
</td>
</tr>
</table>
</td>
</tr>
<tr align="center">
<td class="td-bottom" colspan="4" height="30">�С����������ܡ��ơ����С��ޡ����Ρ�����˾</td>
</tr>
	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">�����������</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[0]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">ǩ��</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>������ǩ��:<%=strCheckName[0]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[0]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">�����������</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[1]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">ǩ��</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>������ǩ��:<%=strCheckName[1]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[1]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td colspan="4" class="td-rightbottom" height="100">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr valign="top">
				<td width="40%">���������</td>
				<td width="30%">&nbsp;</td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><%=strOpinion[2]%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">ǩ��</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>����ǩ��:<%=strCheckName[2]%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center"><%=sDate[2]%>
				</td>
			</tr>
		</table>
		</td>
	</tr>


</table>

<table width="100%" border="0">
<tr>
<td>��֪ͨ����ί�е�λ��д��һʽ�ķݣ���ί�е�λǩ�ָ��º���<%=Env.getClientName()%>��</td>
</tr>
</table>
</td>
</tr>
</table>


<!-- ------------------------------------------------------------------------------------------------Page2 -->
<p>&nbsp;</p>
<font size="4"><b>������ </b></font>
<table width="630" border="0" cellspacing="0" cellpadding="0" height="800">
<tr>
<td valign="top" align="center">
<font size="4"><b>�á��������ơ���</b></font>
<table width="100%" border="1" cellspacing="0" class="table1" >
<tr>
<td colspan='2' ALIGN="center" width="50%"  class="td-rightbottom">�ÿ�ƻ�</td>
<td colspan='2' ALIGN="center" width="50%" class="td-rightbottom">����ƻ�</td>
</tr>
<tr align="center">
<td width="25%"  class="td-rightbottom">�ƻ�ʱ��</td>
<td width="25%"  class="td-rightbottom">���</td>
<td width="25%"  class="td-rightbottom">�ƻ�ʱ��</td>
<td width="25%"  class="td-rightbottom">���</td>
</tr>
<%
	for(int i=0;i<nPlan;i++)
	{

%>
<TR>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strPayTime[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strPayAmount[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strRepayTime[i])%></TD>
	<TD class="td-rightbottom" align=center>&nbsp;<%=DataFormat.formatString(strRepayAmount[i])%></TD>
</TR>
<%
	}
%>
<TR>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
	<TD class="td-rightbottom" align=center></TD>
</TR>
<tr align="center">
<td width="25%"  class="td-rightbottom">�ܼ�:</td>
<td width="25%"  class="td-rightbottom">&nbsp;<%=DataFormat.formatListAmount(dTotalPay)%></td>
<td width="25%"  class="td-rightbottom">�ܼ�:</td>
<td width="25%"  class="td-rightbottom">&nbsp;<%=DataFormat.formatListAmount(dTotalRepay)%></td>
</tr>
</table>
<p>&nbsp;</p>
</td>
</tr>
</table>
<!-- ------------------------------------------------------------------------------------------------Page2 -->
</body>

</html>
<script language="JavaScript">
<!--
window.print();
//-->
</script>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<%	
   //��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>