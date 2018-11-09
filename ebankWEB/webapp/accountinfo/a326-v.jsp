<%--
/**
 ҳ������ ��a326-v.jsp
 ҳ�湦�� : ��������ת�渴��ҳ��
 ��    �� ��kewen hu
 ��    �� ��2004-02-24
 ����˵�� ����ʵ��˵����
 �޸���ʷ ��
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.*" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
    Log.print("\n*******����ҳ��--ebank/capital/accountinfo/a326-v.jsp******\n");
    //�������
    String strTitle = "[�˻���ʷ��ϸ]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* ��ʾ�ļ�ͷ */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		//�������		
		String strAbstract = "";
		long lAccountID = -1;
		String strAccountNo = "";
		double dAmount = 0.0;
		long lCertificationBankID = -1;
		String strCertificationNo = "";
		String strCheckAbstract = "";
		long lCheckUserID = -1;
		String strCheckUserName = "";
		long lClientID = -1;
		String strClientName = "";
		String strClientNo = "";
		long lCurrencyID = -1;
		String strDepositNo = "";
		long lDepositTerm = -1;
		java.sql.Timestamp tsEndDate = null;
		java.sql.Timestamp tsExecuteDate = null;
		long lID = -1;
		long lInputUserID = -1;
		String strInputUserName = "";
		String strInstructionNo = "";
		long lInterestPlanID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;
		long lOfficeID = -1;
		double dPreDrawInterest = 0.0;
		double dRate = 0.0;
		long lSealBankID = -1;
		String strSealNo = "";
		java.sql.Timestamp tsStartDate = null;
		long lStatusID = -1;
		long lSubAccountID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = "";
		long lAbstractID = -1;
		long lInterestBankID = -1;
		String strInterestBankName = "";
		String strInterestCashFlowDesc = "";
		long lInterestCashFlowID = -1;
		String strInterestRemitInCity = "";
		String strInterestExtClientName = "";
		String strInterestExtAcctNo = "";
		String strInterestRemitInBank = "";
		String strInterestExtBankNo = "";
		long lInterestPayTypeID = -1;
		String strInterestRemitInProvince = "";
		long lIsCapitalAndInterestTransfer = -1;
		double dNewAmount = 0.0;
		long lNewCertificationBankID = -1;
		String strNewCertificationNo = "";
		String strNewDepositNo = "";
		long lNewDepositTerm = -1;
		java.sql.Timestamp tsNewEndDate = null;
		long lNewInterestPlanID = -1;
		double dNewRate = 0.0;
		java.sql.Timestamp tsNewStartDate = null;
		double dPayableInterest = 0.0;
		long lReceiveInterestAccountID = -1;
		String strReceiveInterestAccountNo = "";
		double dWithDrawInterest = 0.0;
		long lNewSealBankID = -1;
		String strNewSealNo = "";
		java.sql.Timestamp tsInputDate = null;
		String strReceiveInterestAccountClientName ="";
		//ҳ�渨������
		String strAction = "";
		String strStatus = "";
		String strActionResult = Constant.ActionResult.FAIL;
		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);		
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strEndDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strNewEndDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strNewStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String inputDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = "";
		
		//��Ϣ�ϼ�
		double dTotalInterest  = 0.0;
		
		dTotalInterest=dPayableInterest+dPreDrawInterest;
		
		//��requestȡ����
		
		//ҳ����Ʋ���
		if (request.getAttribute("strActionResult") != null)
		{
			  strActionResult = (String)request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
			  strAction = (String)request.getAttribute("strAction");
		}
		//ҵ������
		String strTemp = null;
		strTemp = (String)request.getAttribute("strModifyTime");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strModifyTime = strTemp;
		}
		String sReturn = (String) request.getAttribute("lReturn");
		long lReturn = -1;
		if (sReturn != null && sReturn.trim().length() > 0) {
			lReturn = Long.parseLong(sReturn);
		}
		Log.print("=============lReturn="+lReturn);
		TransFixedContinueInfo info = null;
		info = (TransFixedContinueInfo)request.getAttribute("searchResults");

		if(info != null)
		{
			strAbstract = info.getAbstract();
			lAccountID = info.getAccountID();
			strAccountNo = info.getAccountNo();
			dAmount = info.getAmount();
			lCertificationBankID = info.getCertificationBankID();
			strCertificationNo = info.getCertificationNo();
			strCheckAbstract = info.getCheckAbstract();
			lCheckUserID = info.getCheckUserID();
			strCheckUserName = info.getCheckUserName();
			lClientID = info.getClientID();
			strClientName = info.getClientName();
			strClientNo = info.getClientNo();
			lCurrencyID = info.getCurrencyID();
			strDepositNo = info.getDepositNo();
			lDepositTerm = info.getDepositTerm();
			tsEndDate = info.getEndDate();
			tsExecuteDate = info.getExecuteDate();
			lID = info.getID();
			lInputUserID = info.getInputUserID();
			strInputUserName = info.getInputUserName();
			strInstructionNo = info.getInstructionNo();
			lInterestPlanID = info.getInterestPlanID();
			tsInterestStartDate = info.getInterestStartDate();
			tsModifyDate = info.getModifyDate();
			lOfficeID = info.getOfficeID();
			dPreDrawInterest = info.getPreDrawInterest();
			dRate = info.getRate();
			lSealBankID = info.getSealBankID();
			strSealNo = info.getSealNo();
			tsStartDate = info.getStartDate();
			lStatusID = info.getStatusID();
			lSubAccountID = info.getSubAccountID();
			lTransactionTypeID = info.getTransactionTypeID();
			strTransNo = info.getTransNo();
			lAbstractID = info.getAbstractID();
			lInterestBankID = info.getInterestBankID();
			strInterestBankName = info.getInterestBankName();
			strInterestCashFlowDesc = info.getInterestCashFlowDesc();
			lInterestCashFlowID = info.getInterestCashFlowID();
			strInterestRemitInCity = info.getInterestRemitInCity();
			strInterestExtClientName = info.getInterestExtClientName();
			strInterestExtAcctNo = info.getInterestExtAcctNo();
			strInterestRemitInBank = info.getInterestRemitInBank();
			strInterestExtBankNo = info.getInterestExtBankNo();
			lInterestPayTypeID = info.getInterestPayTypeID();
			strInterestRemitInProvince = info.getInterestRemitInProvince();
			lIsCapitalAndInterestTransfer = info.getIsCapitalAndInterestTransfer();
			dNewAmount = info.getNewAmount();
			lNewCertificationBankID = info.getNewCertificationBankID();
			strNewCertificationNo = info.getNewCertificationNo();
			strNewDepositNo = info.getNewDepositNo();
			lNewDepositTerm = info.getNewDepositTerm();
			tsNewEndDate = info.getNewEndDate();
			lNewInterestPlanID = info.getNewInterestPlanID();
			dNewRate = info.getNewRate();
			tsNewStartDate = info.getNewStartDate();
			dPayableInterest = info.getPayableInterest();
			lReceiveInterestAccountID = info.getReceiveInterestAccountID();
			strReceiveInterestAccountNo = info.getReceiveInterestAccountNo();
			dWithDrawInterest = info.getWithDrawInterest();
			lNewSealBankID = info.getNewSealBankID();
			strNewSealNo = info.getNewSealNo();
			tsInputDate = info.getInputDate();
			
			//�Զ���
			strStartDate = DataFormat.getDateString(tsStartDate);
			strEndDate = DataFormat.getDateString(tsEndDate);
			strNewStartDate = DataFormat.getDateString(tsNewStartDate);
			strNewEndDate = DataFormat.getDateString(tsNewEndDate);
			strExecuteDate = DataFormat.getDateString(tsExecuteDate);
			strInterestStartDate = DataFormat.getDateString(tsInterestStartDate);
			inputDate = DataFormat.getDateString(tsInputDate);
	}
	//��ʽ������
		strAbstract = DataFormat.formatString(strAbstract);
		strAccountNo = DataFormat.formatString(strAccountNo);
		strCertificationNo = DataFormat.formatString(strCertificationNo);
		strCheckAbstract = DataFormat.formatString(strCheckAbstract);
		strCheckUserName = DataFormat.formatString(strCheckUserName);
		strClientName = DataFormat.formatString(strClientName);
		strClientNo = DataFormat.formatString(strClientNo);
		strDepositNo = DataFormat.formatString(strDepositNo);
		strInputUserName = DataFormat.formatString(strInputUserName);
		strInstructionNo = DataFormat.formatString(strInstructionNo);
		strSealNo = DataFormat.formatString(strSealNo);
		strTransNo = DataFormat.formatString(strTransNo);
		strInterestBankName = DataFormat.formatString(strInterestBankName);
		strInterestCashFlowDesc = DataFormat.formatString(strInterestCashFlowDesc);
		strInterestRemitInCity = DataFormat.formatString(strInterestRemitInCity);
		strInterestExtClientName = DataFormat.formatString(strInterestExtClientName);
		strInterestExtAcctNo = DataFormat.formatString(strInterestExtAcctNo);
		strInterestRemitInBank = DataFormat.formatString(strInterestRemitInBank);
		strInterestExtBankNo = DataFormat.formatString(strInterestExtBankNo);
		strInterestRemitInProvince = DataFormat.formatString(strInterestRemitInProvince);
		strNewCertificationNo = DataFormat.formatString(strNewCertificationNo);
		strNewDepositNo = DataFormat.formatString(strNewDepositNo);
		strReceiveInterestAccountNo = DataFormat.formatString(strReceiveInterestAccountNo);
		strNewSealNo = DataFormat.formatString(strNewSealNo);
		
		lClientID = NameRef.getClientIDByAccountID(lAccountID);
		
		if(strClientNo == null || strClientNo.equals(""))
		{			
			strClientNo = NameRef.getClientCodeByID(lClientID);
			strClientNo = DataFormat.formatString(strClientNo);
			strClientName = NameRef.getClientNameByID(lClientID);
			strClientName = DataFormat.formatString(strClientName);
		}		
		if(strAccountNo == null || strAccountNo.equals(""))
		{			
			strAccountNo = NameRef.getAccountNoByID(lAccountID);
			strAccountNo = DataFormat.formatString(strAccountNo);
		}

		if(strReceiveInterestAccountNo == null || strReceiveInterestAccountNo.equals(""))
		{			
			strReceiveInterestAccountNo = NameRef.getAccountNoByID(lReceiveInterestAccountID);
			strReceiveInterestAccountNo = DataFormat.formatString(strReceiveInterestAccountNo);
			strReceiveInterestAccountClientName = NameRef.getClientNameByAccountID(lReceiveInterestAccountID);			
		}		
		if(strInterestBankName == null || strInterestBankName.equals(""))
		{
			strInterestBankName = NameRef.getBankNameByID(lInterestBankID);
			strInterestBankName = DataFormat.formatString(strInterestBankName);			
		}
		
		strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));		
		strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));
		strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));
		
		dTotalInterest=dPayableInterest+dPreDrawInterest;
    %>
	<form name="frmv025" action="" method="post">	
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">		
		<input name="strSuccessPageURL"  type="hidden" value="">
		<input name="strFailPageURL" type="hidden" value="">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="lID" type="hidden" value="<%=lID%>">
		<input name="lInputUserID" type="hidden" value="<%=lInputUserID%>">
		<input name="lOfficeID" type="hidden" value="<%=lOfficeID%>">
		<input name="lCurrencyID" type="hidden" value="<%=lCurrencyID%>">
		<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">
		<input name="strModifyTime" type="hidden" value="<%=strModifyTime%>">
		
	<TABLE border=0 class=top height=200 width="99%">
  	<TBODY>
  	<TR class="tableHeader">
    	<TD class=FormTitle height=2 width="100%"><B>��������ת��</B></TD>
	</TR>
  	<TR>
    <TD height=20 vAlign=bottom width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">���ڿͻ���ţ�</TD>
          <td height="20" width="34%"> 
            <input type="text" name="ClientIDCtrl" disabled class="box" value="<%=strClientNo%>" size="7" maxlength="7" >
            </td>
          <TD height=20 width="18%">���ڿͻ����� ��</TD>
          <TD height=20 width="35%">
            <textarea class=box disabled name=ClientNameDisable rows=2 cols=30><%=strClientName%></textarea>			
             </TD>
		</TR>
        <TR borderColor=#E8E8E8>
         <SCRIPT language=JavaScript>
			showDisableAccountCtrl("FixedAccountDisabled","<%=strAccountNo%>","�����˺�"," width='18%' "," width='27%' ");
		</SCRIPT>			
          <TD borderColor=#E8E8E8 height=20 width="18%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="32%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">���ڶ��ڴ��ݺţ� </TD>
          <TD height=20 width="34%">
              <INPUT class=box disabled name=FixedDepositDisabled value="<%=strDepositNo%>">
               </TD>
          <TD height=20 width="18%">��������ת��ִ���գ�</TD>
          <TD height=20 width="34%">
              <INPUT class=box disabled name=executeDateDisabled size=18 value="<%=strExecuteDate%>">
               </TD>
        </TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">���ڶ��ڴ����ʼ�գ�</TD>
          <TD height=20 width="34%">
              <INPUT class=box disabled name=StartDateDisabled size=18 value="<%=strStartDate%>">
           </TD>
          <TD height=20 width="18%">���ڴ����գ�</TD>
          <TD height=20 width="35%">
		  <INPUT class=box disabled name=OverDate size=18 value="<%=strEndDate%>"> </TD>
		</TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">���ڴ������:</TD>
          <TD height=20 width="34%">
		 <%
		  SETTHTML.showFixedDepositMonthListCtrl(out,"lDepositTerm",lDepositTerm,false,"onchange=\"dateChange(frmV001)\" disabled onfocus=\"nextfield='strEndDate';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>			  
		</TD>
          <TD height=29 width="18%">���ڶ��ڴ������ :</TD>
          <TD height=29 width="35%">
		  <input class="box" disabled name="dRate" value="<%=dRate%>">%
		   </TD>
	</TR></TBODY></TABLE></TD></TR> 
  <TR>
    <TD height=138 vAlign=bottom width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%"><U>��������ת�洦��</U> </TD>
          <TD colSpan=3 height=20>&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="25%">�����¶��ڴ��ݺţ� </TD>
          <TD height=20 width="25%">
              <INPUT class=box disabled name=FixedNewDepositDisabled value="<%=strNewDepositNo%>">
               </TD>
          <TD height=20 width="25%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="25%">�����¶��ڴ����ʼ�գ�</TD>
          <TD height=20 width="25%">
              <INPUT class=box disabled name=NewStartDateDisabled size=18 value="<%=strNewStartDate%>">
               </TD>
          <TD height=20 width="25%">&nbsp;</TD>
          <TD height=20 width="25%"></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="25%">���ڴ������:</TD>
          <TD height=20 width="25%">
			<%
		  SETTHTML.showFixedDepositMonthListCtrl(out,"lNewDepositTerm",lNewDepositTerm,false,"onchange=\"dateChange(frmV001)\" disabled onfocus=\"nextfield='strEndDate';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>			  
		</TD>
          <TD height=20 width="25%">�����¶��ڴ����գ�</TD>
          <TD height=20 width="25%">
		  <INPUT class=box disabled name=NewOverDate size=18 value="<%=strNewEndDate%>"> 
		  </TD>
		  </TR>			
        <TR borderColor=#E8E8E8>
		  <TD height=20 width="25%">���ʣ�</TD>
          <TD height=29 width="25%">
		  <input class="box" disabled name="dNewRate" value="<%=dNewRate%>">%
		    </TD>
          <TD height=20 width="25%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD>
		  </TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="25%">��</TD>
          <TD height="32" width="27%">��			
    		<INPUT class=box name=dNewwAmountCtrl disabled value="<%=DataFormat.formatDisabledAmount(dNewAmount,2)%>" >
			</TD>
          <TD height=20 width="25%">&nbsp;</TD>
          <TD height=20 width="25%">&nbsp;</TD></TR></TBODY></TABLE></TD></TR>		  
  <TR>
    <TD height=20 vAlign=bottom width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>��Ϣ��ϸ����</U></TD></TR>
        <TR borderColor=#E8E8E8>           
		<TD borderColor=#E8E8E8 colSpan=2 height=20>������Ϣ��</TD>	
          <TD height=20 width="27%">��
              <INPUT class=box value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dPreDrawInterest,2),2)%>" name=EndAccountInterestDisabled size=17 disabled>
               </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>��Ϣ֧����</TD>
          <TD height=20 width="27%">��
              <INPUT class=box  value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dPayableInterest,2),2)%>" name=InterestPayDisabled size=17 disabled>
               </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>��Ϣ�ϼƣ�</TD>
          <TD height=20 width="27%">��
              <INPUT class=box disabled  value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dTotalInterest,2),2)%>" name=InterestTotalDisabled size=17>
           </TD>
          <TD height=20 width="15%"></TD>
          <TD height=20 width="37%">&nbsp;</TD>
		 </TR>
		<TR borderColor=#E8E8E8>		  
			<TD borderColor=#E8E8E8 height=20 width="3%">
<%
	if(lIsCapitalAndInterestTransfer>0)
	{
%>					
				<INPUT checked name=rdoInterest type=radio disabled onfocus="nextfield ='lAbstractIDCtrl';" value="1"  > 
<%
	}
	else
	{
%>			
				<INPUT name=rdoInterest type=radio disabled onfocus="nextfield ='lAbstractIDCtrl';" value="1"  > 
<%
	}
%>					
			</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">��������</TD>
          <TD height=20 width="27%">&nbsp;</TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		  </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
<%
	if(lInterestBankID<=0 && lIsCapitalAndInterestTransfer<0)
	{
%>		  
              <INPUT checked name=rdoInterest type=radio value=1  disabled onFocus="nextfield ='lReceiveInterestAccountIDCtrlCtrl3';" >
<%
	}
	else
	{
%>	
			<INPUT name=rdoInterest type=radio value=1  disabled onFocus="nextfield ='lReceiveInterestAccountIDCtrlCtrl3';" >		  
<%
	}
%>				  
          </TD>
<SCRIPT language=JavaScript>	
	showDisableAccountCtrl("InterestToCurrentAccountIDDisabled","<%=strReceiveInterestAccountNo%>","��Ϣת�������˺�"," width='15%' "," width='26%' ");
</SCRIPT>
          <TD height=20 width="15%">���ڿͻ�����:</TD>
          <TD height=20 width="37%">
           <textarea class=box  name="InterestCurrentClientName" disabled rows=2 cols=30><%=strReceiveInterestAccountClientName%></textarea>

			
               </TD></TR>
		<TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
		<%
			if(lInterestBankID>0 && lIsCapitalAndInterestTransfer<0)
			{
		%>				  
		              <INPUT name=rdoInterest type=radio value=2  checked disabled onFocus="nextfield='lInterestPayTypeID';" >
		<%
			}
			else
			{
		%>
					  <INPUT name=rdoInterest type=radio value=2 disabled onFocus="nextfield='lInterestPayTypeID';" >
		<%
			}
		%>	
               </TD>	  
                <td height="20" width="18%">�����������:</td>
                <td height="20" width="27%"> 
                  <textarea name="InterestPayBankDisabled" disabled class="box" rows=2 cols=30><%=strInterestBankName%></textarea>
				  </td>

          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		 </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"></TD>	  
			<td height="20" bordercolor="#E8E8E8" width="18%">�տ�˺�:</td>
                <td height="20" width="27%">
                  <input type="text" name="InterestRepaymentAccountCtrl" disabled size="18" class="box" value="<%=strInterestExtAcctNo%>">				  
				  </td>

          <TD height=20 width="15%">�տ�ͻ�����:</TD>
          <TD height=20 width="37%">
              <textarea class=box disabled name=InterestRepaymentClientNameDisabled rows=2 cols=30><%=strInterestExtClientName%></textarea>
          </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">�����(ʡ)��</TD>
          <TD height=20 width="27%">
              <INPUT class=box disabled value="<%=strInterestRemitInProvince%>"  name=InterestRemitInProvinceDisabled size=24>
               </TD>
          <TD height=20 width="15%">�����(��)��</TD>
          <TD height=20 width="37%">
              <INPUT class=box disabled value="<%=strInterestRemitInCity%>"  name=InterestRemitInCityDisabled size=24>
           </TD></TR>

        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
      <td height="20" bordercolor="#E8E8E8" width="18%">����������:</td>
                <td height="20" width="27%"> 
              <textarea class=box  disabled  rows=2 cols=30 name=InterestRemitInBankDisabled ><%=strInterestRemitInBank%></textarea>
			   </td>
				<TD  height=20 width="15%">&nbsp;</TD>
				<TD  height=20 width="37%">&nbsp;</TD>
		 </TR>
        </TBODY></TABLE></TD></TR>
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center height=87 width="97%">
        <TBODY>
        <TR>
          <TD height=20 width="11%">��Ϣ�գ�</TD>
          <TD height=20 width="24%">
		  <INPUT class=box disabled name=InterestStart  value="<%=strInterestStartDate%>"> </TD>
           <TD height=20 width="10%">ִ����</TD>
          <TD height=20 width="20%">
		    <INPUT class=box disabled name=zExecuteDay value="<%=strExecuteDate%>"> 			
			</TD>
          <TD height=20 width="10%">&nbsp;</TD>
          <TD height=20 width="20%">&nbsp;</TD></TR>
        <TR>
          <TD height=20 width="10%">���׺�:</TD>
          <TD height=20 width="25%">
		  <input class=box name=zTransNo disabled value="<%=strTransNo%>">
		  </TD>		   
		  <td align="left" valign="middle" height="20" width="10%">ժ Ҫ:</td>
                <td align="left" valign="middle" height="20" width="25%"> 
                  <input type="text" name="zAbstract" maxlength="2000" size="40" disabled class="box" value="<%=strAbstract%>">
                  </td>		 
		  <TD height=20 width="10%">&nbsp;</TD>
          <TD height=20 width="20%">&nbsp;</TD>
		</TR>
        <TR Align=center>
          <TD colSpan=6 height=20>
            <DIV align=right>
		<INPUT class="button" name="print" type="button" value=" �� ӡ " onClick="doPrint();">		
		<input class="button" name="Submit32" type="button" value=" �� �� " onClick="window.close();">	
            </DIV></TD></TR></TBODY>
			</table>
    </td>
  </tr>
  


  <TR>
<TD height=20 vAlign=top width="100%">
	<TABLE><TBODY>
              <tr valign="middle"> 
                <td width="8%" height="25">���˱�ע: </td>
                <td width="19%" height="25" valign="top"> 
                  <input type="text" name="strCheckAbstract" class="box" value="<%=strCheckAbstract%>">
                  </td>
                <td width="6%" height="25" valign="middle">¼����: </td>
                <td width="8%" height="25" valign="middle"><%=strInputUserName%></td>
                <td width="8%" height="25">¼������:</td>
                <td width="11%" height="25"><%=inputDate%></td>
                <td width="6%" height="25">������:</td>
                <td width="7%" height="25"><%=strCheckUserName%></td>
                <td width="8%" height="25"> �������� :</td>
                <td width="9%" height="25"><%=strExecuteDate%></td>
                <td width="5%" height="25">״̬:</td>
                <td width="5%" height="25"> <%=strStatus%> 
                  </td>
              </tr>


</TBODY></TABLE>
		</TD>
		</TR>
		
		
</TBODY></TABLE>
</form>
<%
	if(strAction ==null || !strAction.equals("FixedQuery"))
	{
%>
<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
	<script language="JavaScript">
	firstFocus(document.frmv025.strCheckAbstract);
	//setSubmitFunction("doCheck()");
	setFormName("frmv025");
	</script>
<%
	}
	else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
	<script language="JavaScript">
	firstFocus(document.frmv025.strCheckAbstract);
	//setSubmitFunction("doCancelCheck()");
	setFormName("frmv025");
	</script>
<%
	}
%>
<%
	}
%>
	<script language="JavaScript">
	//��ʶ�Ƿ����ύ����
		var isSubmited = false;
		
		function checkSuccess()
		{			
			if (confirm("���˳ɹ����Ƿ��ӡ?"))
		    {
				doPrint();		
		        document.location.href="<%=strContext%>/settlement/tran/fixed/view/v024.jsp";
		    }
			else
			{
				document.location.href="<%=strContext%>/settlement/tran/fixed/view/v024.jsp";
			}
		}		
		function doPrint()
		{			
			window.open("<%=strContext%>/accountinfo/a404-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&lTransTypeID=<%=lTransactionTypeID%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a326-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a306-c.jsp");
		}
		function doCheck()
		{
			if(isSubmited == true)
		   	{
    	    	alert("�������ύ����Ⱥ�");
	    	   	return;
	    	}			
			if (confirm("�Ƿ񸴺�?")) 
			{
				isSubmited = true;
		  		document.frmv025.strAction.value="<%=SETTConstant.Actions.CHECK%>";
				showSending();
		 	    document.frmv025.submit();
			}	
		}
		function doCancelCheck()
		{
		    if(isSubmited == true)
		    {
		        alert("�������ύ����Ⱥ�");
		        return;
		    }		
			if(!validateFields(frmv025)) return;
		    if (confirm("�Ƿ�ȡ������?")) 
			{
				isSubmited = true;
		  		document.frmv025.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
				showSending();
		 	    document.frmv025.submit();
			}	
		}
		function doLinkSearch()
		{
			document.location.href="<%=strContext%>/settlement/tran/fixed/control/c023.jsp?strAction=<%=SETTConstant.Actions.LINKSEARCH%>&lTransactionTypeID=<%=SETTConstant.TransactionType.FIXEDCONTINUETRANSFER%>&strSuccessPageURL=../view/v027.jsp&strFailPageURL=../view/v027.jsp";
		}		
	function allFields()
	{
		this.aa = new Array("strCheckAbstract","���˱�ע","string",1);
		
	}
</script>
<%
		if(Constant.ActionResult.SUCCESS.equals(strActionResult) && String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
			{
%>
		<script language="JavaScript">
			checkSuccess();
		</script>
<%
			}			
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>