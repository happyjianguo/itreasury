<%--
 ҳ������ ��a1012-v.jsp
 ҳ�湦�� : ��֤��������ҳ��
 ��    �� ��jiamiao
 ��    �� ��2006-05-07
 ����˵�� ����ʵ��˵����
				1��
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transmargindeposit.dataentity.*" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>

<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<safety:resources />

	<%
    try
    {
	/* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }

    	//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, "[�˻���ʷ��ϸ]", OBConstant.ShowMenu.YES);
		//�������		
		String strAbstract = null;
		long lAccountID = -1;
		double dAmount = 0.0;
		double dCommissionAmount = 0.0;//�����ѽ��
		long lBillBankID = -1;
		String strBillNo = null;
		long lBillTypeID = -1;		
		long lCertificationBankID = -1;
		String strCertificationNo = null;
		long lCheckUserID = -1;
		String strCheckUserName = null;
		String strRemitInCity = null;
		String strConsignPassword = null;
		String strConsignVoucherNo = null;
		long lCurrencyID = -1;		
		String strDepositNo = null;
		long lDepositTerm = -1;
		java.sql.Timestamp tsEndDate = null;
		java.sql.Timestamp tsExecuteDate = null;
		long lExtAcctID = -1;
		String strExtClientName = null;
		String strExtAcctNo = null;
		String strExtBankNo = null;
		long lID = -1;
		long lInputUserID = -1;
		String strInputUserName = null;
		String strInstructionNo = null;
		long lInterestPlanID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;
		java.sql.Timestamp tsInputDate = null;
		long lNoticeDay = -1;
		long lOfficeID = -1;
		String strRemitInProvince = null;
		double dRate = 0.0;
		long lSealBankID = -1;
		String strSealNo = null;
		java.sql.Timestamp tsStartDate = null;
		long lStatusID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = null;
		//long lAbstractID = -1;
		String strRemitInBank = null;
		
		
		//ҳ�渨������
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strEndDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = null;
		String inputDate = "";
		String strStatus = null;
		//��������ָ�����
		String strOBInstr = (String)request.getAttribute("OBInstr");
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		//��֤��ͻ�������Ϣ
		long lClientID = -1;
		String strClientNo = null;
		String strClientName = null;
		//��֤���˻�
		String strAccountNo = null;
		//�����˻�������Ϣ(��֤��)
		String strCurrentAccountClientName = null;
		long lCurrentAccountID = -1;
		String strCurrentAccountNo = null;
		
		//�����˻�������Ϣ(��֤��)
		long lCommissionCurrentAccountID = -1;
		
		//�����л�����Ϣ(��֤��)
		long lBankID = -1;
		String strBankName = null;
		//�����л�����Ϣ(������)
		long lCommissionBankID = -1;
		String strCommissionBankName = null;

		//�ֽ���ת������Ϣ(��֤��)
		String strCashFlowDesc = null;
		long lCashFlowID = -1;
		//�ֽ���ת������Ϣ(������)
		String strCommissionCashFlowDesc = null;
		long lCommissionCashFlowID = -1;
		
		//ժҪ������Ϣ
		String strCheckAbstract = null;
		long lAbstractID = -1;
		
		//��ͬ�źͷſ�֪ͨ��ID
		long lContractID = -1;
		long lLoanNoteID = -1;
		
		
		//��Ϣ�������˺�
		long interestAccountID = -1;
		long isInterest = -1;
		
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
		
		TransMarginOpenInfo info = null;
		info = (TransMarginOpenInfo)request.getAttribute("searchResults");

		if(info != null)
		{
			strAbstract = info.getAbstract();
			lAccountID = info.getAccountID();
			strAccountNo = info.getAccountNo();
			dAmount = info.getAmount();
			dCommissionAmount = info.getCommissionAmount();//�����ѽ��
			lBankID = info.getBankID();
			lCommissionBankID = info.getCommissionBankID();//�����ѿ�����
			strBankName = info.getBankName();
			lBillBankID = info.getBillBankID();
			strBillNo = info.getBillNo();
			lBillTypeID = info.getBillTypeID();
			strCashFlowDesc = info.getCashFlowDesc();
			lCashFlowID = info.getCashFlowID();
			lCommissionCashFlowID = info.getCommissionCashFlowID();//�������ֽ�����
			lCertificationBankID = info.getCertificationBankID();
			strCertificationNo = info.getCertificationNo();
			strCheckAbstract = info.getCheckAbstract();
			lCheckUserID = info.getCheckUserID();
			strCheckUserName = info.getCheckUserName();
			strRemitInCity = info.getRemitInCity();
			lClientID = info.getClientID();
			strClientName = info.getClientName();
			strClientNo = info.getClientNo();
			strConsignPassword = info.getConsignPassword();
			strConsignVoucherNo = info.getConsignVoucherNo();
			lCurrencyID = info.getCurrencyID();
			strCurrentAccountClientName = info.getCurrentAccountClientName();
			lCurrentAccountID = info.getCurrentAccountID();
			lCommissionCurrentAccountID = info.getCommissionCurrentAccountID();//�����ѻ����˻�
			strCurrentAccountNo = info.getCurrentAccountNo();
			strDepositNo = info.getDepositNo();
			lDepositTerm = info.getDepositTerm();
			tsEndDate = info.getEndDate();			
			tsExecuteDate = info.getExecuteDate();
			lExtAcctID = info.getExtAcctID();
			strExtClientName = info.getExtClientName();
			strExtAcctNo = info.getExtAcctNo();
			strExtBankNo = info.getExtBankNo();
			lID = info.getID();
			lInputUserID = info.getInputUserID();
			strInputUserName = info.getInputUserName();
			strInstructionNo = info.getInstructionNo();
			lInterestPlanID = info.getInterestPlanID();
			tsInterestStartDate = info.getInterestStartDate();
			tsModifyDate = info.getModifyDate();
			tsInputDate = info.getInputDate();
			lNoticeDay = info.getNoticeDay();
			lOfficeID = info.getOfficeID();
			strRemitInProvince = info.getRemitInProvince();
			dRate = info.getRate();
			lSealBankID = info.getSealBankID();
			strSealNo = info.getSealNo();
			tsStartDate = info.getStartDate();
			lStatusID = info.getStatusID();
			lTransactionTypeID = info.getTransactionTypeID();
			strTransNo = info.getTransNo();
			lAbstractID = info.getAbstractID();
			strRemitInBank = info.getRemitInBank();	
			
			lContractID = info.getContractID();
			lLoanNoteID = info.getLoanNoticeID();
			
			//�Զ���
			strStartDate = DataFormat.getDateString(tsStartDate);
			strEndDate = DataFormat.getDateString(tsEndDate);
			strExecuteDate = DataFormat.getDateString(tsExecuteDate);
			strInterestStartDate = DataFormat.getDateString(tsInterestStartDate);
			inputDate = DataFormat.getDateString(tsInputDate);
			
			//isInterest = info.getIsInterest();
			//interestAccountID = info.getInterestAccountID();
	}
	//��ʽ������
		strAbstract = DataFormat.formatString(strAbstract);
		strAccountNo = DataFormat.formatString(strAccountNo);
		strBankName = DataFormat.formatString(strBankName);
		strBillNo = DataFormat.formatString(strBillNo);
		strCashFlowDesc = DataFormat.formatString(strCashFlowDesc);
		strCertificationNo = DataFormat.formatString(strCertificationNo);
		strCheckAbstract = DataFormat.formatString(strCheckAbstract);
		strCheckUserName = DataFormat.formatString(strCheckUserName);
		strRemitInCity = DataFormat.formatString(strRemitInCity);
		strClientName = DataFormat.formatString(strClientName);
		strClientNo = DataFormat.formatString(strClientNo);
		strConsignPassword = DataFormat.formatString(strConsignPassword);
		strConsignVoucherNo = DataFormat.formatString(strConsignVoucherNo);
		strCurrentAccountClientName = DataFormat.formatString(strCurrentAccountClientName);
		strCurrentAccountNo = DataFormat.formatString(strCurrentAccountNo);
		strDepositNo = DataFormat.formatString(strDepositNo);
		strExtClientName = DataFormat.formatString(strExtClientName);
		strExtAcctNo = DataFormat.formatString(strExtAcctNo);
		strExtBankNo = DataFormat.formatString(strExtBankNo);
		strInputUserName = DataFormat.formatString(strInputUserName);
		strInstructionNo = DataFormat.formatString(strInstructionNo);
		strRemitInProvince = DataFormat.formatString(strRemitInProvince);
		strSealNo = DataFormat.formatString(strSealNo);
		strTransNo = DataFormat.formatString(strTransNo);	
		strRemitInBank = DataFormat.formatString(strRemitInBank);		
		
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
		if(strCurrentAccountNo == null || strCurrentAccountNo.equals(""))
		{
			strCurrentAccountNo = NameRef.getAccountNoByID(lCurrentAccountID);
			strCurrentAccountNo = DataFormat.formatString(strCurrentAccountNo);
			strCurrentAccountClientName = NameRef.getClientNameByAccountID(lCurrentAccountID);
		}
		if(strBankName == null || strBankName.equals(""))
		{
			strBankName = NameRef.getBankNameByID(lBankID);
			strBankName = DataFormat.formatString(strBankName);			
		}

		if(strCashFlowDesc == null || strCashFlowDesc.equals(""))
		{
			strCashFlowDesc = NameRef.getCashFlowNameByID(lCashFlowID);
			strCashFlowDesc = DataFormat.formatString(strCashFlowDesc);
		}
		strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
		strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));		
		strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));	
			
    %>
	<form name="frmV004" action="../control/c004.jsp" method="post">	<!--�������ؿؼ���������ҳ��-->
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">
		<!--
		<input name="strSuccessPageURL" type="hidden" value="../view/v003.jsp">
		-->
		<input name="strSuccessPageURL"  type="hidden" value="<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{out.print("../view/v004.jsp");
				}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
				{out.print("../view/v006.jsp");}%>">
		<input name="strFailPageURL" type="hidden" value="../view/v004.jsp">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="lID" type="hidden" value="<%=lID%>">
		<input name="lInputUserID" type="hidden" value="<%=lInputUserID%>">
		<input name="lOfficeID" type="hidden" value="<%=lOfficeID%>">
		<input name="lCurrencyID" type="hidden" value="<%=lCurrencyID%>">
		<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">
		<input name="strModifyTime" type="hidden" value="<%=strModifyTime%>">
		<TABLE border="0" class="top" height="290" width="99%">
			<TBODY>
				<TR>
					<TD class="FormTitle" height="2" width="100%"><B>��֤����</B>
					</TD>
				</TR>
				<TR>
					<TD height="120" vAlign="top" width="100%">
						<TABLE align="center" border="0" height="27" width="100%">
							<TBODY>
								<TR>
									<TD height="147" vAlign="top" width="95%">
										<TABLE align="center" border="0" borderColor="#999999" height="40" width="97%">
											<TBODY>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="15%">	��֤��ͻ���ţ�
													</TD>
													<TD height="20" width="30%">
														<INPUT class="box" disabled maxLength="6" name="strClientNo" size="10" value="<%=strClientNo%>">
													</TD>
													<TD height="20" width="16%">	��֤��ͻ����� ��
													</TD>
													<TD height="20" width="39%">
														<INPUT class="box" disabled name="strClientName" size="30" value="<%=strClientName%>">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">	<!--*****************************************************************************-->
<script language="javascript">showDisableAccountCtrl("strAccountNo","<%=strAccountNo%>","��֤���˺�","width=15%","width=30%");
</script><!--*****************************************************************************-->
													<TD borderColor="#E8E8E8" height="20" width="16%">	&nbsp;
													</TD>
													<TD borderColor="#E8E8E8" height="20" width="39%">	&nbsp;
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">
              <TD height=20 width="15%">��֤����ݺţ� </TD>	
													<TD height="20" width="30%">
														<INPUT class="box" disabled name="strDepositNo" value="<%=strDepositNo%>">
													</TD>
													<TD height="20" width="16%">
													</TD>
													<TD height="20" width="39%">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="15%">	��ͬ�ţ�
													</TD>
													<TD height="20" width="30%">
														<INPUT class="box" disabled name="strStartDate" size="18" value="<%=NameRef.getContractNoByID(lContractID)%>">
													</TD>
													<TD height="29" width="15%">	�ſ�֪ͨ���ݺţ�
													</TD>
													<TD height="29" width="30%">
														<input class="box" disabled name="dRate" value="<%=NameRef.getMarginPayFormNoByID(lLoanNoteID)%>">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="15%">	��ʼ���ڣ�
													</TD>
													<TD height="20" width="30%">
														<INPUT class="box" disabled name="strStartDate" size="18" value="<%=strStartDate%>">
													</TD>
													<TD height="29" width="15%">	���ʣ�
													</TD>
													<TD height="29" width="30%">
														<input class="box" disabled name="dRate" value="<%=(dRate>=0.00?DataFormat.formatRate(dRate):"0.00")%>">%
													</TD>
												</TR>
												
												
				
              </TABLE></TD></TR>
			  
			  
        <TR>
          <TD height=20 vAlign=top width="95%">
            <TABLE align=center border=1 borderColor=#999999 height=40 
            width="97%">
              <TBODY>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%"><U>��֤������Դ��ϸ����</U> </TD>
                <TD colSpan=3 height=20 vAlign=top>&nbsp;</TD></TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%">
<%	
	if(lBankID <0)	
	{
%>														
								<INPUT disabled name=radiobutton type=radio value=radiobutton checked >���ڴ��
<%
	}
	else
	{
%>	
								<INPUT disabled name=radiobutton type=radio value=radiobutton checked >���ڴ��
<%
	}
%>		
				
				  </TD>
                <TD borderColor=#E8E8E8 colSpan=3 height=20 vAlign=top></TD></TR>
              <TR borderColor=#E8E8E8>
<script language="javascript">			  
  	showDisableAccountCtrl("strCurrentAccountNo","<%=strCurrentAccountNo%>","�����˺�","width=15%","width=30%");
</script>
                <TD height=20 Align=center width="16%">���ڿͻ����� 
                  ��</TD>
                <TD height=20 vAlign=top width="39%"><INPUT 
                  class=box disabled name=strCurrentAccountClientName size=30 value="<%=strCurrentAccountClientName%>"> 
                  </TD></TR>
              <TR borderColor=#E8E8E8 Align=center>
		  

				</TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%">
<%	
	if(lBankID !=-1 || (strExtBankNo!=null && !strExtBankNo.equals("")))
	{	
%>					 
    			<INPUT disabled name=radiobutton type=radio value=radiobutton checked>�����տ�
				 
<%
	}
	else
	{
%>				
				<INPUT disabled name=radiobutton type=radio value=radiobutton >�����տ�
<%
	}
%>					
				
				  </TD>
                <TD colSpan=3 height=20 vAlign=top></TD></TR>
				
              <TR borderColor=#E8E8E8 Align=center>
                <TD height=20 width="15%">�����У�</TD>
                <TD height=20 width="30%"><INPUT class=box disabled   name=strBankName size=30 value="<%=strBankName%>"> </TD>
			  </TR>
				  
			<TR borderColor=#E8E8E8 Align=center>
                <TD height=20 width="15%">�� �</TD>
                <TD height=20 width="30%">			
				<%=sessionMng.m_strCurrencySymbol%> <INPUT class=tar disabled name=dAmount size=17 value="<%=DataFormat.formatDisabledAmount(dAmount)%>">
				</TD>
			</TR>
              </TBODY></TABLE></TD></TR>
			  
			  
			  
			  <TR>
          <TD height=20 vAlign=top width="95%">
            <TABLE align=center border=1 borderColor=#999999 height=40 
            width="97%">
              <TBODY>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%"><U>��������Դ��ϸ����</U> </TD>
                <TD colSpan=3 height=20 vAlign=top>&nbsp;</TD></TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%">
				<%	
	if( lCommissionBankID < 0 )	
	{
%>														
								<INPUT CHECKED disabled name=radiobutton type=radio value=radiobutton checked >���ڴ��
<%
	}
	else
	{
%>	
								<INPUT disabled name=radiobutton type=radio value=radiobutton checked >���ڴ��
<%
	}
%>		
				
				  </TD>
                <TD borderColor=#E8E8E8 colSpan=3 height=20 vAlign=top></TD></TR>
              <TR borderColor=#E8E8E8>
<script language="javascript">  
  	showDisableAccountCtrl("strCurrentAccountNo","<%=NameRef.getAccountNoByID(lCommissionCurrentAccountID)%>","�����˺�","width=15%","width=30%");
</script>
                <TD height=20 Align=center width="16%">���ڿͻ����� 
                  ��</TD>
                <TD height=20 vAlign=top width="39%"><INPUT 
                  class=box disabled name=strCommissionCurrentAccountClientName size=30 value="<%=NameRef.getClientNameByAccountID(lCommissionCurrentAccountID)%>"> 
                  </TD></TR>
              <TR borderColor=#E8E8E8 Align=center>
		  

				</TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%">
<%
	if( lCommissionBankID !=-1 )
	{
%>					 
    			<INPUT disabled name=radioCommissionbutton type=radio value=radiobutton checked>�����տ�
				 
<%
	}
	else
	{
%>				
				<INPUT disabled name=radioCommissionbutton type=radio value=radiobutton >�����տ�
<%
	}
%>					
				
				  </TD>
                <TD colSpan=3 height=20 vAlign=top></TD></TR>
				
              <TR borderColor=#E8E8E8 Align=center>
                <TD height=20 width="15%">�����У�</TD>
                <TD height=20 width="30%"><INPUT class=box disabled   name=strCommissionBankName size=30 value="<%=NameRef.getBankNameByID(lCommissionBankID)%>"> </TD>
			  </TR>
				  
			<TR borderColor=#E8E8E8 Align=center>
                <TD height=20 width="15%">�� �</TD>
                <TD height=20 width="30%">			
				<%=sessionMng.m_strCurrencySymbol%> <INPUT class=tar disabled name=dCommissionAmount size=17 value="<%=DataFormat.formatDisabledAmount(dCommissionAmount)%>">
				</TD>
			</TR>
              </TBODY></TABLE></TD></TR>
			  
			  
			  
			  <TR>
          <TD height=20 vAlign=top width="95%">
            <TABLE align=center border=1 borderColor=#999999 height=40 
            width="97%">
              <TBODY>
              			<TR borderColor="#E8E8E8">
							<TD height="20" Align="center" width="15%"><U>��Ϣ����</U>
							</TD>
							<TD height=21 width="27%">
				            <INPUT name="IsWithinterest" <%if(isInterest > 0){out.print("checked");}%>  disabled  type=checkbox value="1" >
				             ��Ϣ</TD>
							<TD colSpan="2" height="20" vAlign="top">&nbsp;	
							</TD>
						</TR>
						
						<TR borderColor="#E8E8E8">
							<TD height="20" Align="center" width="15%">��Ϣ�˺�
							</TD>
							<TD height=21 width="27%">
				            <INPUT name="lInterestAccountID" value="<%=DataFormat.formatString(NameRef.getAccountNoByID(interestAccountID))%>" disabled  type="text"  >
				             </TD>
							<TD colSpan="2" height="20" vAlign="top">&nbsp;	
							</TD>
						</TR>
              </TBODY></TABLE></TD></TR>
			  
			  
        <TR>
          <TD height=2 width="95%">
            <TABLE align=center height=2 width="97%">
              <TBODY>
              <TR>
                
                <TD align=left height=20 Align=center width="10%">��Ϣ�գ�</TD>
                <TD align=left height=20 Align=center width="25%"><INPUT class=box disabled name=strInterestStartDate 
                  value="<%=strInterestStartDate%>"> </TD>
                <TD align=left height=20 Align=center width="10%">ִ���գ� </TD>
                <TD align=left height=20 Align=center width="25%"><INPUT class=box disabled name=strExecuteDate 
                  value="<%=strExecuteDate%>"> </TD></TR>
              <TR>
                <TD align=left height=20 Align=center width="10%">���׺ţ� </TD>
                <TD align=left height=20 Align=center width="20%"><INPUT class=box disabled name=strTransNo 
                  value="<%=strTransNo%>"> </TD>
                <TD align=left height=20 Align=center width="10%">ժ Ҫ��</TD>
                <TD align=left height=20 Align=center width="25%"><INPUT class=box disabled maxLength=2000 
                  name=strAbstract size=40 value="<%=strAbstract%>"> </TD>
                <TD align=left height=20 Align=center width="10%">&nbsp;</TD>
				<TD align=left height=20 Align=center  width="10%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="20%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="25%">&nbsp;</TD></TR>
				
				
				<TR>
                <TD align=left height=20 Align=center width="10%">&nbsp; </TD>
                <TD align=left height=20 Align=center width="20%">&nbsp; </TD>
                <TD align=left height=20 Align=center width="10%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="25%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="10%">&nbsp;</TD>
				<TD align=left height=20 Align=center  width="10%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="20%">&nbsp;</TD>
                <TD align=left height=20 Align=center width="25%">&nbsp;</TD></TR>
             
              <TR>
                <TD align=right colSpan=8 height=20 vAlign=top>
                  <DIV align=right></DIV>
                  <DIV align=right>
<%
	if(strAction !=null && strAction.equals("FixedQuery"))
	{		
		if(strOBInstr!=null && strOBInstr.equals("obinstr"))
		{
%>	
		<!--
		<INPUT class=button name=Submit3 onclick="" type=button value=" ������ʷ��ϸ "> 
		-->
		<%//if(Config.getBoolean( ConfigConstant.SETT_HAS_REPAIR_PRINTVOUCHER,false)){%>
			<!-- <input  type="button"  name="otherprint"  value=" �� �� "  class="button" onClick = "addprint()" >-->
		<%//}else{%>
			<INPUT class="button" name="print" type="button" value=" �� ӡ " onClick="doPrint();">
		<%//}%>
		<input class="button" name="Submit32" type="button" value=" �� �� " onClick="doReturn();">	
<%	
		}
		else
		{
%>
		
		<%//if(Config.getBoolean( ConfigConstant.SETT_HAS_REPAIR_PRINTVOUCHER,false)){%>
			<!-- <input  type="button"  name="otherprint"  value=" �� �� "  class="button" onClick = "addprint()" > -->
		<%//}else{%>
			<INPUT class="button" name="print" type="button" value=" �� ӡ " onClick="doPrint();">
		<%//}%>
		<input class="button" name="Submit32" type="button" value=" �� �� " onClick="window.close();">	

<%
		}
	}
	else
	{
		if(lStatusID==SETTConstant.TransactionStatus.CHECK)
		{
	%>				  
					  <INPUT class=button name=Submit3 onclick="doCancelCheck();" type=button value=" ȡ������ "> 
	<%
		}
		else
		{
	%>				  
					  <INPUT class=button name=Submit333 onclick="doCheck();" type=button value=" ���� "> 	
	<%
	   }
	%>
					<INPUT class="button" name="print" type="button" value=" �� ӡ " onClick="doPrint();">
	<%	
		if(lStatusID==SETTConstant.TransactionStatus.CHECK)
		{
	%>					  
	                  <input class="button" name="Submit32" type="button" value=" �� �� " onClick="doLinkSearch();">	
	<%
		}
		else
		{
	%>			
					<input class="button" name="Submit32" type="button" value=" �� �� " onClick="location.href='../view/v003.jsp';">	
	<%
	   }
	 }  
	%>		  
                  </DIV></TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=2 width="95%">
            <HR>
            <TABLE align=center border=0 height=22 width="97%">
              <TBODY>
              <TR Align=center>
                <TD height=25 width="8%">���˱�ע:</TD>
                <TD height=25 vAlign=top width="19%"><INPUT 
                  class=box name="strCheckAbstract" value="<%=strCheckAbstract%>" maxlength=50 onfocus="nextfield ='submitfunction';"> </TD>
                <TD height=25 width="6%">¼����:</TD>
                <TD height=25 width="8%"><%=strInputUserName%></TD>
                <TD height=25 width="8%">¼������:</TD>
                <TD height=25 width="11%"><%=inputDate%></TD>
                <TD height=25 width="6%">������:</TD>
                <TD height=25 width="7%"><%=strCheckUserName%></TD>
                <TD height=25 width="8%">��������:</TD>
		<%	
		if(lStatusID==SETTConstant.TransactionStatus.CHECK)
		{
		%>			
                <TD height=25 width="9%"><%=strExecuteDate%></TD>
		<%
		}
		else
		{
		%>	
			<TD height=25 width="9%"></TD>
         <%
	   }  
		%>	       <TD height=25 width="5%">״̬:</TD>
                <TD height=25 width="5%"><%=strStatus%></TD>
			  </TR></TBODY>
			</TABLE>
		  </TD>
	    </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
				
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
firstFocus(document.frmV004.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV004");
</script>
<%
	}
	else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">firstFocus(document.frmV004.strCheckAbstract);
//setSubmitFunction("doCancelCheck()");
setFormName("frmV004");
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
		function addprint()
		{
		
			//alert("��ӡ");
			frmV004.strSuccessPageURL.value='../view/v004.jsp';
			frmV004.strFailPageURL.value='../view/v004.jsp';
			frmV004.action='../../../print/control/c100.jsp';
			frmV004.submit();
		}
		
		function checkSuccess()
		{			
			if (confirm("���˳ɹ����Ƿ��ӡ?"))
		    {
				window.open("<%=strContext%>/settlement/print/view/v002.jsp?lID=<%=lID%>&print=1&lTransTypeID=<%=lTransactionTypeID%>&strSuccessPageURL=/settlement/tran/margin/view/v004.jsp&strFailPageURL=/settlement/tran/margin/view/v004.jsp");
		        document.location.href="<%=strContext%>/settlement/tran/margin/view/v003.jsp";
		    }
			else
			{
				document.location.href="<%=strContext%>/settlement/tran/margin/view/v003.jsp";
			}
		}		
		function doPrint()
		{			
			window.open("a1012p-v.jsp?lID=<%=lID%>&lTransTypeID=<%=lTransactionTypeID%>&strSuccessPageURL=/settlement/tran/margin/view/v004.jsp&strFailPageURL=/settlement/tran/margin/view/v004.jsp");			
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
		  		document.frmV004.strAction.value="<%=SETTConstant.Actions.CHECK%>";
				showSending();
		 	    document.frmV004.submit();
			}	
		}
		function doCancelCheck()
		{
			if(!validateFields(frmV004)) return;
		    if(isSubmited == true)
		    {
		        alert("�������ύ����Ⱥ�");
		        return;
		    }		
			
		    if (confirm("�Ƿ�ȡ������?")) 
			{
				isSubmited = true;
		  		document.frmV004.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
				showSending();
		 	    document.frmV004.submit();
			}	
		}
		function doLinkSearch()
		{
			document.location.href="<%=strContext%>/settlement/tran/margin/control/c003.jsp?strAction=<%=SETTConstant.Actions.LINKSEARCH%>&lTransactionTypeID=<%=SETTConstant.TransactionType.OPENMARGIN%>&strSuccessPageURL=../view/v006.jsp&strFailPageURL=../view/v006.jsp";
		}
		function doReturn()
		{
			document.location.href="<%=strContext%>/settlement/obinstruction/control/c001.jsp?strSuccessPageURL=/settlement/obinstruction/view/v002.jsp&strFailPageURL=/settlement/obinstruction/view/v002.jsp&_pageLoaderKey=<%=strPageLoaderKey%>";
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
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}
		OBHtml.showOBHomeEnd(out);
%>


<%@ include file="/common/SignValidate.inc" %>