<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
        java.util.*,
        java.lang.*,
        java.net.URLEncoder,
        com.iss.itreasury.loan.extendapply.bizlogic.*,
        com.iss.itreasury.loan.extendapply.dataentity.*,
        com.iss.itreasury.loan.contract.bizlogic.*,
        com.iss.itreasury.loan.contract.dataentity.*,
        com.iss.itreasury.loan.repayplan.bizlogic.*,
        com.iss.itreasury.loan.repayplan.dataentity.*,
       	com.iss.itreasury.ebank.util.*,			
		com.iss.itreasury.ebank.obdataentity.*,	
		com.iss.itreasury.ebank.obquery.bizlogic.*,
		com.iss.itreasury.ebank.obquery.dataentity.*,
        com.iss.itreasury.system.bizlogic.*,
        com.iss.itreasury.system.dataentity.*,
        com.iss.itreasury.loan.util.*,
		com.iss.itreasury.ebank.approval.bizlogic.*,
		com.iss.itreasury.ebank.approval.dataentity.*,
        com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<%
        try
        {
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
                long lContractID = -1;                   //��ͬID                
                long lExtendApplyID = -1;                //չ������ID
                String sTitle = "";                      //ҳͷ
                
                //���չ�������ID
                if( (String)request.getAttribute("lExtendApplyID")!=null )
                {
                    lExtendApplyID = Long.parseLong((String)request.getAttribute("lExtendApplyID"));
                }
                else
                {
                    lExtendApplyID = -1;
                }
                
                //��ú�ͬ��ID
                if( (String)request.getAttribute("lContractID")!=null )
                {
                    lContractID = Long.parseLong((String)request.getAttribute("lContractID"));
                }
                else
                {
                    lContractID = -1;
                }
                
                OBExtendQuery e_ejb = new OBExtendQuery();
                ExtendApplyInfo e_info = new ExtendApplyInfo();
                
                //�õ�չ����������
                e_info = e_ejb.findExtendByID(lExtendApplyID);
                
                //��ͬ��EJB
                OBContractQuery c_ejb = new OBContractQuery();
                ContractInfo c_info = new ContractInfo();
                
                //�õ���ͬ����
                c_info = c_ejb.findByID(lContractID);
                
                //�ж�ҳͷ��ʾ
                if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
                {
                    sTitle = "ί�д���";
                }
                else
                {
                    sTitle = "��Ӫ����";
                }
                
                RepayPlanInfo r_info = new RepayPlanInfo();  //  ҳ����ʾ��
				
				String clientName= DataFormat.formatString(c_info.getBorrowClientName());
				String applyCode = DataFormat.formatString(c_info.getApplyCode());
				String sType = LOANConstant.LoanType.getName(c_info.getLoanTypeID());
				double loanAmount=c_info.getExamineAmount();
				long   intervalNum=c_info.getIntervalNum();
				double interestRate=c_info.getInterestRate()*(1+c_info.getAdjustRate()/100);
				double adjustRate=c_info.getAdjustRate();
				double ChargeRate=c_info.getChargeRate();
				String loanPurpose=c_info.getLoanPurpose();
				String loanType="";//���ʽ
				String tmp="";
				double dTempAssure = 0;
				dTempAssure = c_info.getAssureAmount();

				if (intervalNum<0) intervalNum=0;
				if (interestRate<0) interestRate=0;
				if (loanPurpose==null) loanPurpose="";

				String[] sOpinion = {"","","","","","","",""};
				String[] sCheckUser = {"","","","","","","",""};
				String[] sCheckDate = {"","","","","","","",""};
				for (int i=0; i<8; i++)
				{
					sCheckDate[i] = DataFormat.getDateString(Env.getSystemDate());
				}

		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
		
		long lModuleID = Constant.ModuleType.LOAN;
		//ģ������
		long lLoanTypeID = c_info.getLoanTypeID();
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		Collection c = null;
		c = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lActionID,lExtendApplyID,-1);
		if (c != null) 
		{
			Iterator iter = c.iterator();
			int i=0;
			if(c.size() <= 8)
			{
				i=0;
				while (iter.hasNext() && i < 8) 
				{
					tracinginfo = (ApprovalTracingInfo)iter.next();
					sOpinion[i]=DataFormat.formatString(tracinginfo.getOpinion());
					sCheckUser[i]=DataFormat.formatString(tracinginfo.getUserName());
					sCheckDate[i]=DataFormat.getDateString(tracinginfo.getApprovalDate());
					i++;
				}
				while(i < 8)
				{
					//sCheckDate[i]="��������������������������������";
					sCheckDate[i]="����  �� ��  ��   ��   ��   ��   ��      ";
					i++;
				}
			}
			else
			{
				i=7;
				while (iter.hasNext() && i>=0) 
				{
					tracinginfo = (ApprovalTracingInfo)iter.next();
					sOpinion[i]=DataFormat.formatString(tracinginfo.getOpinion());
					sCheckUser[i]=DataFormat.formatString(tracinginfo.getUserName());
					sCheckDate[i]=DataFormat.getDateString(tracinginfo.getApprovalDate());
					i--;
				}
			}
		}
%>

<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gbk">
<title><%=Env.getClientName()%><%=sTitle%>չ��������</title>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<style type="text/css">
<!--
.table1 {  border: 1px solid #000000}
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
<body bgcolor="#FFFFFF"   onload="printPage()">
<div align=center>
<table width="630" border="0" cellspacing="0" cellpadding="0" height="960">
<tr>
<td valign="top" align="center">
<table width="100%" border="0">

<tr>
        <td height="30" align="center">&nbsp;</td>
</tr>
<tr>
<td width="50%" height="30" align="center"><font size="4"><b><%=Env.getClientName()%><%=sTitle%>չ��������</b></font></td>
</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="table1">
        <%if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
          {%>
        <tr>
                <td colspan="2" rowspan="2" align="center" class="td-right" width="13%">ί�е�λ</td>
                <td width="43%" height="25" colspan="3" class="td-right">���ƣ�<%=DataFormat.formatString(c_info.getConsignName())%></td>
          <td width="43%" height="25">��ַ��<%=DataFormat.formatString(c_info.getConsignAddress()).length() <= 0? "": "("+DataFormat.formatString(c_info.getConsignZip())+")"+DataFormat.formatString(c_info.getConsignAddress())%></td>
        </tr>
        <tr>
                <td height="25" colspan="3" class="td-topright">ί�д�����У�<%=DataFormat.formatString(c_info.getConsignAccount()).length() <= 0?"":DataFormat.formatString(Env.getClientName())%></td>
                <td height="25" class="td-top">�˺ţ�<%=DataFormat.formatString(c_info.getConsignAccount())%></td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">������</td>
                <td height="25" colspan="4" class="td-top"><%=Env.getClientName()%>&nbsp;</td>
        </tr>
        <%}else{%>
        <tr>
                <td colspan="2" align="center" class="td-right">������</td>
                <td height="25" colspan="4" class=""><%=Env.getClientName()%>&nbsp;</td>
        </tr>
        <%}%>
        <tr>
                <td colspan="2" rowspan="2" align="center" class="td-topright">�����</td>
                <td height="25" colspan="3" class="td-topright">���ƣ�<%=DataFormat.formatString(c_info.getBorrowName())%></td>
                <td height="25" class="td-top">��ַ��<%=c_info.getBorrowAddress().length() <= 0? "": "("+DataFormat.formatString(c_info.getBorrowZip())+")"+DataFormat.formatString(c_info.getBorrowAddress())%></td>
        </tr>
        <tr>
                <td height="25" colspan="3" class="td-topright">�����У�<%=DataFormat.formatString(c_info.getBorrowAccount()).length() <= 0?"":DataFormat.formatString(Env.getClientName())%></td>
                <td height="25" class="td-top">�˺ţ�<%=DataFormat.formatString(c_info.getBorrowAccount())%></td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">ԭ��ͬ��</td>
                <td height="25" colspan="3" class="td-topright"><%=c_info.getContractCode()%></td>
                <td colspan="1" class="td-top">��������:<%=LOANConstant.LoanType.getName(c_info.getLoanTypeID())%></td>
        </tr>
        <tr><%String Amount = DataFormat.formatAmount(c_info.getExamineAmount());%>
                <td colspan="2" align="center" class="td-topright">ԭ������</td>
                <td height="25" colspan="4" class="td-top">�����(��д)
					<%=ChineseCurrency.showChinese(Amount)%>(��<%=DataFormat.formatNumber(c_info.getExamineAmount(),2)%>)</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">ԭ��������</td>
                <td height="25" colspan="4" class="td-top"><%=c_info.getIntervalNum()%>����(�� <%=DataFormat.getChineseDateString(c_info.getLoanStart())%> �� <%=DataFormat.getChineseDateString(c_info.getLoanEnd())%>)</td>
        </tr>
<%
		if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
		{
%>
			<tr>
				<td colspan="2" align="center" class="td-topright">ԭ��������</td>
				<td colspan="3" height="25" class="td-topright" >
				<%=c_info.getInterestRate()<=0?"0":DataFormat.formatRate(DataFormat.formatRate(c_info.getInterestRate()))+"%"%>&nbsp;</td>
				<td height="25" colspan="1" class="td-top">�������ʣ�<%=ChargeRate <=0?"0": DataFormat.formatRate(DataFormat.formatRate(ChargeRate))+"%"%></td>
			</tr>
<%
		}
		else
		{
%>
			<tr>
				<td colspan="2" align="center" class="td-topright">ԭ��������</td>
				<td colspan="4" height="25" class="td-top" ><%=c_info.getInterestRate()<=0?"0":DataFormat.formatRate(DataFormat.formatRate(c_info.getInterestRate()))+"%"%>&nbsp;</td>
			</tr>
<%
		}
%>
        <tr>
                <td colspan="2" align="center" class="td-topright">ԭ������;</td>
                <td colspan="4" height="25" class="td-top" ><%=DataFormat.formatString(c_info.getLoanPurpose())%>&nbsp;</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">ԭ���ʽ</td>
                <td height="25" colspan="4" class="td-top"><%if(c_info.getIsCredit() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>���á�����<%if(c_info.getIsAssure() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��֤������<%if(c_info.getIsImpawn() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��Ѻ������<%if(c_info.getIsPledge() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��Ѻ</td>
        </tr>
        <%if(c_info.getIsAssure() == 1)
        {%>
        <tr>
                <td colspan="2" rowspan="2" align="center" class="td-topright">ԭ������</td>
                <td height="25" colspan="3" class="td-topright">���ƣ�<%=DataFormat.formatString(c_info.getAssureName())%></td>
                <td height="25" class="td-top">��ַ��<%=c_info.getAssureAddress().length() <= 0? "": "("+DataFormat.formatString(c_info.getAssureZip())+")"+DataFormat.formatString(c_info.getAssureAddress())%></td>
        </tr>
        <tr>
                <td height="25" colspan="3" class="td-topright">�����У�<%=DataFormat.formatString(c_info.getAssureAccount()).length() <= 0?"":DataFormat.formatString(Env.getClientName())%></td>
                <td height="25" class="td-top">�˺ţ�<%=DataFormat.formatString(c_info.getAssureAccount())%></td>
        </tr>
        <%}else{%>
        <tr>
                <td colspan="2" rowspan="2" align="center" class="td-topright">ԭ������</td>
                <td height="25" colspan="3" class="td-topright">���ƣ�</td>
                <td height="25" class="td-top">ס����</td>
        </tr>
        <tr>
                <td height="25" colspan="3" class="td-topright">�����У�</td>
                <td height="25" class="td-top">�˺ţ�</td>
        </tr>
        <%}%>
        <tr>
                <td colspan="2" align="center" class="td-topright">��(��)Ѻ��</td>
                <td height="25" colspan="4" class="td-top">&nbsp;</td>
        </tr>
<%
            Collection c2 = e_info.cExtendList;
	    Iterator iter2 = c2.iterator();
	    double dZQMoney = 0.0;
	    long lZQMonth = -1;
	    Timestamp start = null;
	    Timestamp end = null;
	    while (iter2.hasNext())
	    {
		r_info = (RepayPlanInfo)iter2.next();
                dZQMoney = dZQMoney + r_info.dAmount;
                
                if( r_info.lExtendPeriod > lZQMonth)
                {
                    lZQMonth = r_info.lExtendPeriod;
                    start = r_info.tsExtendStartDate;
                    end = r_info.tsExtendEndDate;
                }
            }

%>
        <tr><%String Amount1 = DataFormat.formatAmount(dZQMoney);%>
                <td colspan="2" align="center" class="td-topright">չ�ڽ��</td>
                <td height="25" colspan="4" class="td-top">�����(��д)
					<%=ChineseCurrency.showChinese(Amount1)%>(��<%=DataFormat.formatNumber(dZQMoney,2)%>)</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">չ������</td>
                <td height="25" colspan="4" class="td-top"><%if(lZQMonth != -1){%><%=lZQMonth%><%}%>����(�� <%=DataFormat.getChineseDateString(start)%> �� <%=DataFormat.getChineseDateString(end)%>)</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">չ������</td>
                <td colspan="4" height="25" class="td-top" ><%=e_info.m_dInterestRate<=0?"0":DataFormat.formatRate(DataFormat.formatRate(e_info.m_dInterestRate))+"%"%>&nbsp;</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">չ�ڴ��ʽ</td>
                <td height="25" colspan="4" class="td-top"><%if(c_info.getIsCredit() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>���á�����<%if(c_info.getIsAssure() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��֤������<%if(c_info.getIsImpawn() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��Ѻ������<%if(c_info.getIsPledge() == 1){%><input type="checkbox" checked disabled ><%}else{%><input type="checkbox" disabled ><%}%>��Ѻ</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">չ��ԭ��</td>
                <td height="25" colspan="4" class="td-top"><%=DataFormat.formatString(e_info.m_strExtendReason)%>&nbsp;</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">�黹���ڻ��Ϣ�ʽ���Դ</td>
                <td height="25" colspan="4" class="td-top"><%=DataFormat.formatString(e_info.m_strReturnPostPend)%>&nbsp;</td>
        </tr>
        <tr>
                <td colspan="2" align="center" class="td-topright">��������</td>
                <td height="25" colspan="4" class="td-top"><%=DataFormat.formatString(e_info.m_strOtherContent)%>&nbsp;</td>
        </tr>

</table></td>
</tr>
</table>
</div>
<div align=center>
<p align="center"><strong><font size="+2"><%=Env.getClientName()%><br><%=sTitle%>������</font></strong></p>
 <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
  <tr> 
    <td>
	<table width="100%"   cellpadding="0" cellspacing="0">
        <tr> 
          <td colspan="2"><br>�����λ��<%=clientName%></td>
          <td colspan="2"><br>�������ţ�<%=DataFormat.formatString(applyCode)%></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">�������<%=DataFormat.formatNumber(loanAmount,2)%></td>
          <td colspan="1">�������ޣ�<%=DataFormat.formatString(intervalNum+"&nbsp;����")%></td>
          <td colspan="2">��������(��Ϣ)��<%=interestRate <=0?"0": DataFormat.formatRate(DataFormat.formatRate(interestRate))+"%"%></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">���ʽ��<%=DataFormat.formatString(loanType)%></td>
          <td colspan="2">�������ࣺ<%=DataFormat.formatString(sType)%></td>
<%
		if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
		{
%>
			<td colspan="1">�������ʣ�<%=ChargeRate <=0?"0": DataFormat.formatRate(DataFormat.formatRate(ChargeRate))+"%"%></td>
<%
		}
		else
		{
%>
			<td colspan="1">&nbsp;</td>
<%
		}
%>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">������;��<%=DataFormat.formatString(loanPurpose)%>&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">���������ܶ��<%=DataFormat.formatNumber(dTempAssure,2)%></td>
          <td colspan="2">���ô����ܶ��<%=DataFormat.formatNumber((loanAmount-dTempAssure)<=0?0:(loanAmount-dTempAssure),2)%></td>
          <td colspan="1"></td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="1">���Ŷ�ȣ�</td>
          <td colspan="2">�������Ž�</td>
          <td colspan="1">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="4">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=200>
        <tr> 
          <td colspan="2"><p><br>
              ����������<br><%=sOpinion[0]%><br>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">�����ǩ�֣�<%=sCheckUser[0]%><br>
			<%=sCheckDate[0].substring(0,4)%>�� <%=sCheckDate[0].substring(5,7)%>�� <%=sCheckDate[0].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=200>
        <tr> 
          <td colspan="2"><p><br>
              ���������<br><%=sOpinion[1]%><br>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">����ǩ�֣�<%=sCheckUser[1]%><br>
			<%=sCheckDate[1].substring(0,4)%>�� <%=sCheckDate[1].substring(5,7)%>�� <%=sCheckDate[1].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>

<%
		if( c_info.getLoanTypeID() == LOANConstant.LoanType.WT )
		{
%>
</table>
</div>
<br clear=all style='page-break-before:always'>
 <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              ���ž��������<br><%=sOpinion[2]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">���ž���ǩ�֣�<%=sCheckUser[2]%><br>
			<%=sCheckDate[2].substring(0,4)%>�� <%=sCheckDate[2].substring(5,7)%>�� <%=sCheckDate[2].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              ����˾�ʽ�ͷ���Ƿ���԰��Ŵ˱ʴ��<br><%=sOpinion[3]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">�ƻ�������ǩ�֣�<%=sCheckUser[3]%><br>
			<%=sCheckDate[3].substring(0,4)%>�� <%=sCheckDate[3].substring(5,7)%>�� <%=sCheckDate[3].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p><br>
              ������/�����������<br><%=sOpinion[4]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">������/���ܾ���ǩ�֣�<%=sCheckUser[4]%><br>
			<%=sCheckDate[4].substring(0,4)%>�� <%=sCheckDate[4].substring(5,7)%>�� <%=sCheckDate[4].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>

<%
		}
		else
		{
%>
  <tr> 
    <td><table width="100%" height=200>
        <tr> 
          <td colspan="2"><p><br>
              ���ž��������<br><%=sOpinion[2]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">���ž���ǩ�֣�<%=sCheckUser[2]%><br>
			<%=sCheckDate[2].substring(0,4)%>�� <%=sCheckDate[2].substring(5,7)%>�� <%=sCheckDate[2].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
</table>
<br clear=all style='page-break-before:always'>
 <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000" >
  <tr> 
    <td><table width="100%" height=160>
        <tr> 
          <td colspan="2"><p><br>
              ����˾�ʽ�ͷ���Ƿ���԰��Ŵ˱ʴ��<br><%=sOpinion[3]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">�ƻ�������ǩ�֣�<%=sCheckUser[3]%><br>
			<%=sCheckDate[3].substring(0,4)%>�� <%=sCheckDate[3].substring(5,7)%>�� <%=sCheckDate[3].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=160>
        <tr> 
          <td colspan="2"><p><br>
              ������/�����������<br><%=sOpinion[4]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">������/���ܾ���ǩ�֣�<%=sCheckUser[4]%><br>
			<%=sCheckDate[4].substring(0,4)%>�� <%=sCheckDate[4].substring(5,7)%>�� <%=sCheckDate[4].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>

  <tr> 
    <td><table width="100%" height=300>
        <tr> 
          <td colspan="2"><p>
              ���տ���ίԱ�������<br><%=sOpinion[5]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">���տ���ίԱ��ǩ�֣�<%=sCheckUser[5]%><br>
			<%=sCheckDate[5].substring(0,4)%>�� <%=sCheckDate[5].substring(5,7)%>�� <%=sCheckDate[5].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=160>
        <tr> 
          <td colspan="2"><p><br>
              �ܾ��������<br><%=sOpinion[6]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">�ܾ���ǩ�֣�<%=sCheckUser[6]%><br>
			<%=sCheckDate[6].substring(0,4)%>�� <%=sCheckDate[6].substring(5,7)%>�� <%=sCheckDate[6].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="100%" height=160>
        <tr> 
          <td colspan="2"><p><br>
              ���³���<br><%=sOpinion[7]%>
              </p>
            <p>&nbsp;</p></td>
        </tr>
        <tr> 
          <td align="right">&nbsp;</td>
          <td width="35%" align="left">���³�ǩ�֣�<%=sCheckUser[7]%><br>
			<%=sCheckDate[7].substring(0,4)%>�� <%=sCheckDate[7].substring(5,7)%>�� <%=sCheckDate[7].substring(8,10)%>��</td>
        </tr>
      </table></td>
  </tr>
<%
		}
%>
</table>
</body>

</html>

<script language="JavaScript">
    
    function printPage()
    {
        window.print();
    }
    
</script>

<%		
	} 
	catch (IException e)
	{
	//LOANHTML.showExceptionMessage(out,sessionMng,e,request,response,"չ�ں�ͬ��ӡ",Constant.RecordStatus.VALID);
	e.printStackTrace();
	out.flush();
	return; 
	}
%>