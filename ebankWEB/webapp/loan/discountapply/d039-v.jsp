<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				com.iss.itreasury.system.approval.bizlogic.*,
				com.iss.itreasury.system.approval.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%
	/* ����̶����� */
	String strTitle = "[����ƾ֤]";
%>	
<%!
	public static String getAmountByOrder(String strAmount, int iOrderID) throws Exception
	{
		String strReturn = "&nbsp;";
		int nAmountLength = 11;
		try
		{
			if (iOrderID < strAmount.length())
			{
				if (iOrderID <= 2)
				{
					return (strAmount.substring(strAmount.length() - iOrderID, strAmount.length() - iOrderID + 1));
				}
				else
				{
					return (strAmount.substring(strAmount.length() - iOrderID - 1, strAmount.length() - iOrderID));
				}
			}
			if((iOrderID == strAmount.length()) && (iOrderID <= nAmountLength))
			{
					return "��";//sessionMng.m_strCurrencySymbol;
			}
		}
		catch (Exception e)
		{
		}
		return strReturn;
	}
%>
<%
/////////////////////////////////////////////////////////////////////////////////
	System.out.println("hrere!");
	try{    
		
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		//�����������ȡ�������
		
		String strTmp = "";
		String strControl = "";
		String strBackURL = "S120-1";
		String strDisabled = " disabled";
		String strAction = "";
			
		long txtContract = -1;			//���ֱ�ʾ
		String txtContractCode = "";	//����������
		long txtClient = -1;			//��λ��ʾ
		String txtClientCtrl = "";		//��λ����

		long lContractID = -1;			//���ֱ�ʾ
		long lCredenceID = -1;			//����ƾ֤��ʾ
		Timestamp tsFillDate = null;
		long lDraftTypeID = -1;
		String strDraftTypeName = "";
		String strDraftCode = "";
		Timestamp tsPublicDate = null;
		Timestamp tsAtTerm = null;
		String strApplyClient = "";
		String strApplyAccount = "";
		String strApplyBank = "";
		String strAcceptClient = "";
		String strAcceptAccount = "";
		String strAcceptBank = "";
		double dAmount = 0;
		double dRate = 0;
		double dInterest = 0;
		double dCheckAmount = 0;
		double dBillAmount = 0;
		double dBillInterest = 0;
		double dBillCheckAmount = 0;
		double[] dResult = new double[3];
		
		//////////////////////////////
		String strContractCode = "";
		String strCredenceCode = "";
		long lInputUserID = -1;
		String strInputUserName = "";
		Timestamp tsInputDate = null;
		long lStatusID = -1;
		long lCount = 0;
		//////////////////////////////
		int nTmp = 0;
		int TRACINGNUM = 4;
		String[] strApprovalTracing = new String[TRACINGNUM];
		String[] strApprovalDate = new String[TRACINGNUM];
		String[] strApprovalUserName = new String[TRACINGNUM];
		for (int i=0; i<TRACINGNUM; i++)
		{
			strApprovalUserName[i] = "";
			strApprovalTracing[i] = "";
			strApprovalDate[i] = DataFormat.getDateString(Env.getSystemDate());
		}
		//ģ������
		long lModuleID = Constant.ModuleType.LOAN;
		//ҵ������
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//��������
		long lActionID = Constant.ApprovalAction.DISCOUNT_CREDENCE;
		
		Collection temp = null;
		//////////////////////////////
		
		//����EJB
        
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();

///////control///////////////////////////////////////////////////////////////////
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
	
		strTmp = (String)request.getAttribute("backurl");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     strBackURL = strTmp.trim();
		}
		
		strTmp = (String)request.getAttribute("txtContract");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContract = Long.parseLong(strTmp.trim());
		}
			 
		strTmp = (String)request.getAttribute("txtContractCode");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtContractCode = strTmp.trim();
		}	 
		
		strTmp = (String)request.getAttribute("txtClient");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClient = Long.parseLong(strTmp.trim());
		}	 

		strTmp = (String)request.getAttribute("txtClientCtrl");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 txtClientCtrl = DataFormat.toChinese(strTmp.trim());
		}
		
//adding
		DiscountCredenceInfo info = new DiscountCredenceInfo();
       	if (request.getAttribute("info") != null)
       	{
           	info = (DiscountCredenceInfo)request.getAttribute("info");
       	}

////////view/////////////////////////////////////////////////////////////////////
			if (lCredenceID > 0)
			{
				//dci = Discount.findDiscountCredenceByID (lCredenceID);

				if (info.getCode()!=null && info.getCode().length()>0)
				{
					strCredenceCode = info.getCode();
				}
				else
				{
					strCredenceCode = "&nbsp;";
				}
				lContractID = info.getDiscountContractID();
				if (info.getDiscountContractCode()!=null && info.getDiscountContractCode().length()>0)
				{
					strContractCode = info.getDiscountContractCode();
				}
				else
				{
					strContractCode = "&nbsp;";
				}
				lStatusID = info.getStatusID();
				tsFillDate = info.getFillDate();
				tsInputDate = info.getInputDate();
				lInputUserID = info.getInputUserID();
				if (info.getInputUserName()!=null && info.getInputUserName().length()>0)
				{
					strInputUserName = info.getInputUserName();
				}
				else
				{
					strInputUserName = "&nbsp;";
				}
				lDraftTypeID = info.getDraftTypeID();
				if (info.getDraftCode()!=null && info.getDraftCode().length()>0)
				{
					strDraftCode = info.getDraftCode();
				}
				else
				{
					strDraftCode = "&nbsp;";
				}
				tsPublicDate = info.getPublicDate();
				tsAtTerm = info.getAtTerm();

				if (info.getApplyClientName()!=null && info.getApplyClientName().length()>0)
				{
					strApplyClient = info.getApplyClientName();
				}
				else
				{
					strApplyClient = "&nbsp;";
				}
				if (info.getApplyAccount()!=null && info.getApplyAccount().length()>0)
				{
					strApplyAccount = info.getApplyAccount();
				}
				else
				{
					strApplyAccount = "&nbsp;";
				}
				if (info.getApplyBank()!=null && info.getApplyBank().length()>0)
				{
					strApplyBank = info.getApplyBank();
				}
				else
				{
					strApplyBank = "&nbsp;";
				}

				strAcceptClient = info.getAcceptClientName();
				strAcceptAccount = info.getAcceptAccount();
				strAcceptBank = info.getAcceptBank();
				if (info.getAcceptClientName()!=null && info.getAcceptClientName().length()>0)
				{
					strAcceptClient = info.getAcceptClientName();
				}
				else
				{
					strAcceptClient = "&nbsp;";
				}
				if (info.getAcceptAccount()!=null && info.getAcceptAccount().length()>0)
				{
					strAcceptAccount = info.getAcceptAccount();
				}
				else
				{
					strAcceptAccount = "&nbsp;";
				}
				if (info.getAcceptBank()!=null && info.getAcceptBank().length()>0)
				{
					strAcceptBank = info.getAcceptBank();
				}
				else
				{
					strAcceptBank = "&nbsp;";
				}

				dAmount = info.getExamineAmount();
				dRate = info.getDiscountRate();
				dCheckAmount = info.getCheckAmount();

				dBillAmount = info.getBillAmount();
				dBillInterest = info.getBillInterest();
				dBillCheckAmount = info.getBillCheckAmount();

				temp = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lCredenceID,Constant.PageControl.CODE_ASCORDESC_DESC);

				if( (temp != null) && (temp.size() > 0) )
				{
					nTmp = TRACINGNUM-1;
					Iterator it = temp.iterator();
					while (it.hasNext() )
					{
						tracinginfo = (ApprovalTracingInfo) it.next();
						strApprovalUserName[nTmp] = DataFormat.formatString(tracinginfo.getUserName());
						strApprovalTracing[nTmp] = DataFormat.formatString(tracinginfo.getOpinion());
						if (tracinginfo.getApprovalDate() != null)
						{
							strApprovalDate[nTmp] = DataFormat.getDateString(tracinginfo.getApprovalDate());
						} 
						else 
						{
							strApprovalDate[nTmp] = DataFormat.getDateString(Env.getSystemDate());
						}
						Log.print(nTmp);
						Log.print(strApprovalTracing[nTmp]);
						Log.print(strApprovalDate[nTmp]);
						nTmp--;
						if (nTmp < 0)
						{
							break;
						}
					}
				}
			}

/////////////////////////////////////////////////////////////////////////////////

%>

<html>
<head>
<title>����ƾ֤</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
body {
	font-family: "����";
	font-size: 14px;
}
td {
	font-family: "����_GB2312";
	font-size: 12px;
}
.f16 {
	font-family: "����";
	font-size: 16px;
}
.f12 {
	font-family: "����_GB2312";
	font-size: 12px;
}
-->
</style>
</head>

<body text="#000000" bgcolor="#FFFFFF">

<% 
	for (int i = 0; i < 5; i++) 
	{
%>

<table width="650" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="100">&nbsp;</td>
    <td width="450" height="50" align="center"><b><span class="f16"><%=Env.getClientName()%><br><u>����ƾ֤��һʽ������</u></span></b></td>
    <td width="100">&nbsp;</td>
  </tr>
</table>
<table width="650" border="0" align="center">
<tr> 
    <td width="100">&nbsp;</td>
    <td width="450" height="30" align="center"><% if (tsFillDate != null) { %><%=DataFormat.getDateString(tsFillDate).substring(0,4)%>��&nbsp;<%=DataFormat.getDateString(tsFillDate).substring(5,7)%>��&nbsp;<%=DataFormat.getDateString(tsFillDate).substring(8,10)%>��<% } else { out.print("��&nbsp;&nbsp;��&nbsp;&nbsp;��");}%></td>
    <td width="100">&nbsp;</td>
</tr>
<tr>
	<td height="30" align="left" colspan=3>���ֺ�ͬ�ţ�&nbsp;<%=strContractCode%></td>
</tr>
</table>
<table width="650" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td width="630">
	  <table width="630" border="0" cellpadding="0" cellspacing="0" class="table1">
		<tr> 
          <td rowspan="3" align="center" class="td-rightbottom">��<br>
         	��<br>
         	��<br>
         	Ʊ</td>
          <td align="center" class="td-rightbottom" height="25">�֡���</td>
          <td align="center" colspan="2" class="td-rightbottom"><%if(lDraftTypeID>0) out.print(OBConstant.DraftType.getName(lDraftTypeID)); else out.print("�����ϸ��");%></td>
          <td align="center" class="td-rightbottom">����</td>
          <td align="center" colspan="4" class="td-rightbottom"><%=DataFormat.formatString(strDraftCode)%></td>
          <td rowspan="3" align="center" class="td-rightbottom">��<br>
         	Ʊ<br>
         	��</td>
          <td align="center" class="td-rightbottom">������</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyClient)%></td>
		</tr>
        <tr> 
          <td align="center" class="td-rightbottom" height="25">��Ʊ��</td>
          <td align="center" colspan="7" class="td-rightbottom"><% if (tsPublicDate != null) { %><%=DataFormat.getDateString(tsPublicDate).substring(0,4)%>��&nbsp;<%=DataFormat.getDateString(tsPublicDate).substring(5,7)%>��&nbsp;<%=DataFormat.getDateString(tsPublicDate).substring(8,10) %>��<% } else { out.print("��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��");}%></td>
          <td align="center" class="td-rightbottom">�ˡ���</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyAccount)%></td>
        </tr>
        <tr> 
          <td align="center" class="td-rightbottom" height="25">������</td>
          <td align="center" colspan="7" class="td-rightbottom"><% if (tsAtTerm != null) { %><%=DataFormat.getDateString(tsAtTerm).substring(0,4)%>��&nbsp;<%=DataFormat.getDateString(tsAtTerm).substring(5,7)%>��&nbsp;<%=DataFormat.getDateString(tsAtTerm).substring(8,10)%>��<% } else { out.print("��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��");}%></td>
          <td align="center" class="td-rightbottom">��������</td>
          <td align="center" colspan="3" class="td-bottom"><%=DataFormat.formatString(strApplyBank)%></td>
        </tr>
        <tr> 
          <td align="center" colspan="2" class="td-rightbottom" height="25">��Ʊ��Ʊ��</td>
          <td align="center" class="td-rightbottom">����</td>
          <td align="center" colspan="5" width="80" class="td-rightbottom"><%=DataFormat.formatString(strAcceptClient)%></td>
          <td align="center" class="td-rightbottom">�˺�</td>
          <td align="center" colspan="2" class="td-rightbottom"><%=DataFormat.formatString(strAcceptAccount)%></td>
          <td align="center" class="td-rightbottom">��������</td>
          <td align="center" colspan="2" class="td-bottom"><%=DataFormat.formatString(strAcceptBank)%></td>
        </tr>
        <tr> 
          <td align="center" colspan="2" class="td-rightbottom" height="40">��Ʊ���</td>
          <td colspan="10" class="td-rightbottom">&nbsp;�����<br>
         	����д��<%=ChineseCurrency.showChinese( DataFormat.formatAmount(dBillAmount))%></td>
          <td colspan="2" class="td-bottom">
		    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	  <tr>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">��</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">��</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">��</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">��</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">Ԫ</span></td>
          		<td width="8" align="center" class="td-rightbottom"><span class="f12">��</span></td>
          		<td width="8" align="center" class="td-bottom"><span class="f12">��</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillAmount),1)%></span></td>
       		</tr>
          	</table></td>
        </tr>
        <tr> 
          <td align="center" nowrap class="td-rightbottom" height="40">������</td>
          <td align="right" class="td-rightbottom"><%=dRate <= 0?"0":DataFormat.formatRate(DataFormat.formatRate(dRate))+"%"%>&nbsp;</td>
<%
		if(i==1)
		{
%>
			<td colspan="3" align="center" class="td-topleftright2bottom2">������Ϣ</td>
			<td colspan="5" class="td-bottom">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ǧ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ʮ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ǧ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ʮ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">Ԫ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-topright2bottom"><span class="f12">��</span></td>
				</tr>
				<tr>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),11)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),10)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),9)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),8)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),7)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),6)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),5)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),4)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),3)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),2)%></span></td>
					<td align="center" class="td-right2bottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),1)%></span></td>
				</tr>
			</table>
		</td>
<%
		}
		else
		{
%>
          <td colspan="3" align="center" class="td-rightbottom">������Ϣ</td>
          <td colspan="5" class="td-rightbottom">
		    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	<tr>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">Ԫ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-bottom"><span class="f12">��</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillInterest),1)%></span></td>
       		</tr>
          	</table></td>
<%
		}
%>
<%
		if(i==2)
		{
%>
			<td colspan="2" align="center" class="td-topleftright2bottom2">ʵ�����ֽ��</td>
			<td colspan="2" class="td-bottom">
			<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ǧ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ʮ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ǧ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">ʮ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">Ԫ</span></td>
					<td align="center" width="8" class="td-toprightbottom"><span class="f12">��</span></td>
					<td align="center" width="8" class="td-topbottom"><span class="f12">��</span></td>
				</tr>
				<tr>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),11)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),10)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),9)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),8)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),7)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),6)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),5)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),4)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),3)%></span></td>
					<td align="center" class="td-rightbottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),2)%></span></td>
					<td align="center" class="td-bottom"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),1)%></span></td>
				</tr>
			</table>
			</td>
<%
		}
		else
		{
%>
          <td colspan="2" align="center" class="td-rightbottom">ʵ�����ֽ��</td>
          <td colspan="2" class="td-bottom">
		  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          	<tr>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ǧ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">ʮ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">Ԫ</span></td>
          		<td align="center" width="8" class="td-rightbottom"><span class="f12">��</span></td>
          		<td align="center" width="8" class="td-bottom"><span class="f12">��</span></td>
       		</tr>
          	<tr>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),11)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),10)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),9)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),8)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),7)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),6)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),5)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),4)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),3)%></span></td>
          		<td align="center" class="td-right"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),2)%></span></td>
          		<td align="center"><span class="f12"><%=getAmountByOrder(DataFormat.formatAmount(dBillCheckAmount),1)%></span></td>
       		</tr>
          	</table></td>
<%
		}
%>
        </tr>
        <tr valign="top">
        	<td height="50" colspan="14" valign="middle">&nbsp;ժҪ��</td>
   		</tr>
  </table></td>
  <td width="20" align="center">��<%	
									switch (i)
									{
										case 0 :
											out.print("һ");
											break;
										case 1 :
											out.print("��");
											break;
										case 2 :
											out.print("��");
											break;
										case 3 :
											out.print("��");
											break;
										case 4 :
											out.print("��");
											break;
									}
								  %>��<br>���˸���</td>
  </tr>
</table>
<table width="650" border="0" align="center">
	<tr valign="bottom">
		<td width="25%">���ܾ���<%=strApprovalUserName[0]%></td>
		<td width="25%">���ž���<%=strApprovalUserName[1]%></td>
		<td width="25%">���ˣ�<%=strApprovalUserName[2]%></td>
		<td width="25%">���죺<%=strApprovalUserName[3]%></td>
	</tr>
</table>
<% if (i < 4) { %>
<br clear=all style='page-break-before:always'>
<% } %>
<% } %>

<SCRIPT language="JavaScript">
	window.print();
</SCRIPT>

</body>
</html>

<%	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"����ƾ֤", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"����ƾ֤",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>