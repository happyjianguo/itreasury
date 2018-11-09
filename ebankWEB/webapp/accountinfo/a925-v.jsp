<%--
 ҳ������ ��v016.jsp
 ҳ�湦�� : ��Ӫ�����ջء�������/ȡ��������ʾҳ��
 ��    �� ��gqzhang	
 ��    �� ��2003��10��23��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

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

	OBHtml.showOBHomeHead(out,sessionMng,Env.getClientName(),Constant.YesOrNo.NO);
//ҳ�渨������
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	String strPreSaveResult = null;

//����ҵ�����
	 long lID = -1;
	 long lOfficeID = -1;
	 long lCurrencyID = -1;
	 String strTransNo = ""; 
	 long lTransactionTypeID = -1;
	 long lClientID = -1;
	 long lDepositAccountID = -1;
	 long lBankID = -1;
	 long lLoanAccountID = -1;
	 long lLoanContractID = -1;
	 long lLoanNoteID = -1;
	  String strStartDate = "";//��ʼ����
	 long lPreFormID = -1;
	 double dAmount = 0.0;
	 java.sql.Timestamp tsInterestStart = null;
	 java.sql.Timestamp tsExecute = null;
	 long lAbstractID = -1;
	 String strAbstract = "";
	 String strCheckAbstract = "";
	 long lPayInterestAccountID = -1;
	 long lInterestBankID = -1;
	 long lReceiveInterestAccountID = -1;
	 long lPaySuretyAccountID = -1;
	 long lSuretyBankID = -1;
	 long lReceiveSuretyAccountID = -1;
	 double dInterest = 0.0;
	 double dInterestReceiveAble = 0.0;
	 double dInterestIncome = 0.0;
	 double dInterestTax = 0.0;
	 double dCompoundInterest = 0.0;
	 double dOverDueInterest = 0.0;
	 double dSuretyFee = 0.0;
	 String strAdjustInterestReason = ""; 
	 double dAdjustInterest = 0;
	 double dAheadRepayInterest = 0;
	 long lIsRemitInterest = -1;
	 long lIsRemitCompoundInterest = -1;
	 long lIsRemitOverDueInterest = -1;
	 long lIsRemitSuretyFee = -1;
	 long lCapitalAndInterstDealway = -1;
	 double dRealInterest = 0.0;
	 double dRealInterestReceiveAble = 0.0;
	 double dRealInterestIncome = 0.0;
	 double dRealInterestTax = 0.0;
	 double dRealCompoundInterest = 0.0;
	 double dRealOverDueInterest = 0.0;
	 double dRealSuretyFee = 0.0;
	 java.sql.Timestamp tsInput = null;
	 long lInputUserID = -1;
	 long lCheckUserID = -1; 
	 long lStatusID = -1;
	 java.sql.Timestamp tsModify = null;
	long lOrderByCode = Sett_TransRepaymentLoanDAO.ORDER_TRANS_NO;
	boolean isDesc = true;

	 
	if (request.getAttribute("strActionResult") != null)
	{
		  strActionResult = (String)request.getAttribute("strActionResult");
	}
	if (request.getAttribute("strAction") != null)
	{
		  strAction = (String)request.getAttribute("strAction");
	}
	
	
	//��������ָ�����
		String strOBInstr = (String)request.getAttribute("OBInstr");
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	//��Request�л�ò���
	TransRepaymentLoanInfo transRepaymentLoanInfo = null;
	transRepaymentLoanInfo = (TransRepaymentLoanInfo)request.getAttribute("searchResults");
	
	if(transRepaymentLoanInfo != null)
	{
	  lID = transRepaymentLoanInfo.getID();
	  Log.print("lID ��"+lID);
	  lOfficeID = transRepaymentLoanInfo.getOfficeID();
	  Log.print("lOfficeID ��"+lOfficeID);
	  lCurrencyID = transRepaymentLoanInfo.getCurrencyID();
	  Log.print("lCurrencyID ��"+lCurrencyID);
	  strTransNo = transRepaymentLoanInfo.getTransNo();
	  Log.print("strTransNo ��"+strTransNo);
	  lTransactionTypeID = transRepaymentLoanInfo.getTransactionTypeID();
	  Log.print("lTransactionTypeID ��"+lTransactionTypeID);
	  lClientID = transRepaymentLoanInfo.getClientID();
	  Log.print("lClientID ��"+lClientID);
	  lDepositAccountID = transRepaymentLoanInfo.getDepositAccountID();
	  Log.print("lDepositAccountID ��"+lDepositAccountID);
	  lBankID = transRepaymentLoanInfo.getBankID();
	  Log.print("lBankID ��"+lBankID);
	  lLoanAccountID = transRepaymentLoanInfo.getLoanAccountID();
	  Log.print("lLoanAccountID ��"+lLoanAccountID);
	  lLoanContractID = transRepaymentLoanInfo.getLoanContractID();
	   Log.print("lLoanContractID ��"+lLoanContractID);
	  lLoanNoteID = transRepaymentLoanInfo.getLoanNoteID();
	  Log.print("lLoanNoteID ��"+lLoanNoteID);
	  lPreFormID = transRepaymentLoanInfo.getPreFormID();
	  Log.print("lPreFormID ��"+lPreFormID);
	  dAmount = transRepaymentLoanInfo.getAmount();
	  Log.print("dAmount ��"+dAmount);
	  tsInterestStart = transRepaymentLoanInfo.getInterestStart();
	  Log.print("tsInterestStart ��"+tsInterestStart);
	  tsExecute = transRepaymentLoanInfo.getExecute();
	  Log.print("tsExecute ��"+tsExecute);
	  lAbstractID = transRepaymentLoanInfo.getAbstractID();
	  Log.print("lAbstractID ��"+lAbstractID);
	  strAbstract = transRepaymentLoanInfo.getAbstract();
	  Log.print("strAbstract ��"+strAbstract);
	  strCheckAbstract = transRepaymentLoanInfo.getCheckAbstract();
	  Log.print("strCheckAbstract ��"+strCheckAbstract);
	  lPayInterestAccountID = transRepaymentLoanInfo.getPayInterestAccountID();
	  Log.print("lPayInterestAccountID ��"+lPayInterestAccountID);
	  lInterestBankID = transRepaymentLoanInfo.getInterestBankID();
	  Log.print("lInterestBankID ��"+lInterestBankID);
	  lReceiveInterestAccountID = transRepaymentLoanInfo.getReceiveInterestAccountID();
	  Log.print("lReceiveInterestAccountID ��"+lReceiveInterestAccountID);
	  lPaySuretyAccountID = transRepaymentLoanInfo.getPaySuretyAccountID();
	  Log.print("lPaySuretyAccountID ��"+lPaySuretyAccountID);
	  lSuretyBankID = transRepaymentLoanInfo.getSuretyBankID();
	  Log.print("lSuretyBankID ��"+lSuretyBankID);
	  lReceiveSuretyAccountID = transRepaymentLoanInfo.getReceiveSuretyAccountID();
	  Log.print("lReceiveSuretyAccountID ��"+lReceiveSuretyAccountID);
	  dInterest = transRepaymentLoanInfo.getInterest();
	  Log.print("dInterest ��"+dInterest);
	  dInterestReceiveAble = transRepaymentLoanInfo.getInterestReceiveAble();
	  Log.print("dInterestReceiveAble ��"+dInterestReceiveAble);
	  
	  dInterestIncome = transRepaymentLoanInfo.getInterestIncome();
	  Log.print("dInterestIncome ��"+dInterestIncome);
	  dInterestTax = transRepaymentLoanInfo.getInterestTax();
	  Log.print("dInterestTax ��"+dInterestTax);
	  dCompoundInterest = transRepaymentLoanInfo.getCompoundInterest();
	   Log.print("dCompoundInterest ��"+dCompoundInterest);
	  dOverDueInterest = transRepaymentLoanInfo.getOverDueInterest();
	  Log.print("dOverDueInterest ��"+dOverDueInterest);
	  dSuretyFee = transRepaymentLoanInfo.getSuretyFee();
	  Log.print("dSuretyFee ��"+dSuretyFee);
	  strAdjustInterestReason = transRepaymentLoanInfo.getAdjustInterestReason();
	  Log.print("strAdjustInterestReason ��"+strAdjustInterestReason);
	  dAdjustInterest = transRepaymentLoanInfo.getAdjustInterest();
	  Log.print("dAdjustInterest ��"+dAdjustInterest);
	  dAheadRepayInterest = transRepaymentLoanInfo.getAheadRepayInterest();
	  Log.print("dAheadRepayInterest ��"+dAheadRepayInterest);
	  lIsRemitInterest = transRepaymentLoanInfo.getIsRemitInterest();
	  Log.print("lIsRemitInterest ��"+lIsRemitInterest);
	  lIsRemitCompoundInterest = transRepaymentLoanInfo.getIsRemitCompoundInterest();
	  Log.print("lIsRemitCompoundInterest ��"+lIsRemitCompoundInterest);
	  lIsRemitOverDueInterest = transRepaymentLoanInfo.getIsRemitOverDueInterest();
	  Log.print("lIsRemitOverDueInterest ��"+lIsRemitOverDueInterest);
	  lIsRemitSuretyFee = transRepaymentLoanInfo.getIsRemitSuretyFee();
	  Log.print("lIsRemitSuretyFee ��"+lIsRemitSuretyFee);
	  lCapitalAndInterstDealway = transRepaymentLoanInfo.getCapitalAndInterstDealway();
	  Log.print("lCapitalAndInterstDealway ��"+lCapitalAndInterstDealway);
	  dRealInterestReceiveAble = transRepaymentLoanInfo.getRealInterestReceiveAble();
	   Log.print("RealInterestReceiveAble ��"+dRealInterestReceiveAble);
	  dRealInterestIncome = transRepaymentLoanInfo.getRealInterestIncome();
	  Log.print("dRealInterestIncome ��"+dRealInterestIncome);
	  dRealInterest = transRepaymentLoanInfo.getRealInterest();
	  Log.print("dRealInterest ��"+dRealInterest);
	  dRealInterestTax = transRepaymentLoanInfo.getRealInterestTax();
	  Log.print("dRealInterestTax ��"+dRealInterestTax);
	  dRealCompoundInterest = transRepaymentLoanInfo.getRealCompoundInterest();
	  Log.print("dRealCompoundInterest ��"+dRealCompoundInterest);
	  dRealOverDueInterest = transRepaymentLoanInfo.getRealOverDueInterest();
	  Log.print("dRealOverDueInterest ��"+dRealOverDueInterest);
	  dRealSuretyFee = transRepaymentLoanInfo.getRealSuretyFee();
	  Log.print("dRealSuretyFee ��"+dRealSuretyFee);
	  tsInput = transRepaymentLoanInfo.getInput();
	  Log.print("tsInput ��"+tsInput);
	  lInputUserID = transRepaymentLoanInfo.getInputUserID();
	  Log.print("lInputUserID ��"+lInputUserID);
	  lCheckUserID = transRepaymentLoanInfo.getCheckUserID();
	  Log.print("lCheckUserID ��"+lCheckUserID);
	  lStatusID = transRepaymentLoanInfo.getStatusID();
	  Log.print("lStatusID ��"+lStatusID);
	  tsModify = transRepaymentLoanInfo.getModify();
	  Log.print("tsModify ��"+tsModify);
	 }
	 
		
	   //���ݷſ�֪ͨ����ѯ��ʼ����
	    LoanPayFormDetailInfo tempLoanPayFormDetailInfo = new LoanPayFormDetailInfo();
		Sett_TransGrantLoanDAO tempSett_TransGrantLoanDAO = new Sett_TransGrantLoanDAO();
		
		tempLoanPayFormDetailInfo.setLoadNoteID(lLoanNoteID);
		java.sql.Timestamp tsTemp = null;
		tsTemp = (tempSett_TransGrantLoanDAO.findPayFormDetailByCondition(tempLoanPayFormDetailInfo)).getStart();
		strStartDate = DataFormat.formatDate(tsTemp);
		
		
  	//�������Ӳ���
	
			
			String strTemp = null;
			strTemp = (String)request.getAttribute("lOrderByCode");
			if(strTemp != null && strTemp.trim().length() > 0)
			{
				lOrderByCode = Long.valueOf(strTemp).longValue();
			}
			
	
			strTemp = (String)request.getAttribute("isDesc");
			if(strTemp != null && strTemp.length()>0)
			{
				isDesc = Boolean.valueOf(strTemp).booleanValue();
			}			
%>
<form name="frmV016" action="../control/c014.jsp" method="post">
<TABLE border=0 class=title_top height=290 width="99%">
  <TBODY>
  
  <input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
  <input name="strAction"  type="hidden">
   <input name="strSuccessPageURL"  type="hidden" value="<%
			if(lStatusID == SETTConstant.TransactionStatus.SAVE)
			{out.print("../view/v015.jsp");
			}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
			{out.print("../view/v017.jsp");}%>">
	<input name="strFailPageURL"  type="hidden" value="../view/v016.jsp">
	<input name="lID"  type="hidden" value="<%=lID%>">
	<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
	<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
	<input name="tsModify"  type="hidden" value="<%=tsModify%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
	<input name="isDesc" type="Hidden" value="<%=isDesc%>">

  
  
    <TR class="tableHeader"> 
      <TD class=FormTitle height=2 width="100%"><B>��Ӫ�����ջ�</B></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR > 
              <TD colSpan=2 height=20><U>���������ϸ����</U> </TD>
              <TD height=20 width="23%">&nbsp;</TD>
              <TD height=20 width="16%">&nbsp;</TD>
              <TD height=20 width="37%">&nbsp;</TD>
            </TR>
            <TR > 
              <TD colSpan=2 height=20>����ͻ���ţ�</TD>
              <TD height=20 width="23%"><INPUT class=box  
            maxLength=8 name="lClientNo" size=10 value="<%=NameRef.getClientCodeByID(lClientID)%>" disabled> </TD>
              <TD height=20 width="16%">����ͻ����� ��</TD>
              <TD height=20 width="37%"><textarea class=box  
            name="lClientName" rows=2 cols=30 disabled><%=NameRef.getClientNameByID(lClientID)%></textarea> 
              </TD>
            </TR>
            <TR > 
              <TD height=20 width="3%">
			  <%
			  if (lDepositAccountID < 0)
			  {
			  %>
			 <INPUT  name="Rb1" 
            type=radio value="1"  disabled> 
			 <%}
			 else
			{
			%>
			<INPUT  name="Rb1" 
            type=radio value="1" checked disabled> 
			<%}%>
			</TD>
<script language=javascript>			  
		showDisableAccountCtrl("strDepositAccountNo","<%=NameRef.getAccountNoByID(lDepositAccountID)%>","���ڴ���˺�","width=21%","width=23%")
</script>
            
              <TD height=32 vAlign=center width="16%">&nbsp;</TD>
              <TD height=32 vAlign=center width="37%">&nbsp;</TD>
            </TR>
            <TR > 
              <TD height=20 width="3%"> 
			 <%
			  if (lBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb1" 
            type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb1" 
            type=radio value="2" checked disabled> 
			<%}%> </TD>
              <TD height=20 vAlign=center width="21%">�տ��������ƣ�</TD>
           
              <TD height=20 vAlign=center width="23%"><textarea 
            class=box  name="strBankName" rows=2 cols=30 disabled><%=NameRef.getBankNameByID(lBankID)%></textarea> 
              
              </TD>
              <TD height=20 vAlign=center width="16%">&nbsp;</TD>
              <TD height=20 vAlign=center width="37%">&nbsp;</TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR > 
              <TD height=20 width="17%"><U>��Ӫ������ϸ����</U> </TD>
              <TD height=20 width="33%">&nbsp;</TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%">&nbsp;</TD>
            </TR>
            <TR > 
            
 <script language=javascript>			  
		showDisableAccountCtrl("strLoanAccountNo","<%=NameRef.getAccountNoByID(lLoanAccountID)%>","��Ӫ�����˺�","width=17%","width=33%")
</script>
       
              <TD height=20 width="15%">����ͻ����� ��</TD>
              <TD height=20 width="35%"><textarea class=box disabled 
            name="strLoanClientName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea> 
              </TD>
            </TR>
            <TR > 
              <TD height=20 width="17%">��ͬ�ţ�</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="strLoanContract" value="<%=NameRef.getContractNoByID(lLoanContractID)%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%"></TD>
            </TR>
            <TR > 
              <TD height=20 width="17%">�ſ�֪ͨ���ţ�</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="lLoanNoteNo" value="<%=NameRef.getPayFormNoByID(lLoanNoteID)%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%">&nbsp;</TD>
            </TR>
			  <TR > 
              <TD height=20 width="17%">��ʼ���ڣ�</TD>
              <TD height=20 width="33%"><INPUT class=box disabled 
            name="strStartDate" value="<%=strStartDate%>"> </TD>
              <TD height=20 width="15%">&nbsp;</TD>
              <TD height=20 width="35%"></TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR > 
              <TD height=20 vAlign=center nowrap width="15%"><U>��Ϣ�ջ���ϸ����</U> </TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="35%">&nbsp;</TD>
            </TR>
            <TR > 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> 
				<%
			  if (lPayInterestAccountID < 0)
			  {
			  %>
			  <INPUT  name="Rb2" 
            	type=radio value="1"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb2" 
            type=radio value="1" checked disabled> 
			<%}%> </td>
                    <script language=javascript>			  
	showDisableAccountCtrl("lPayInterestAccountNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","��Ϣ���ڴ���˺�","width=20%","")
</script>
                   <TD width="100">&nbsp;</TD>
                    <TD height=20 vAlign=center>��Ϣ�ͻ����ƣ�</TD>
                    <TD height=20 vAlign=top ><textarea name="strPayInterestClientName"  class="box"    rows=2 cols=30 disabled><%=NameRef.getClientNameByAccountID(lPayInterestAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR > 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">
				<%
			  if (lInterestBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb2" 
            	type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb2" 
            type=radio value="2" checked disabled> 
			<%}%></td>
					<td>�����У�</td>
					<td width="100">&nbsp;</td>
					<td><textarea name="strInterestBankName" class="box"   rows="2" cols="30" disabled><%=NameRef.getBankNameByID(lInterestBankID)%></textarea></td>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR > 
              <TD height="20" vAlign="center" nowrap width="15%"><U>�����Ѹ�����ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR > 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">
			<%
			  if (lPaySuretyAccountID < 0)
			  {
			  %>
			  <INPUT  name="Rb3" 
            	type=radio value="1"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb3" 
            type=radio value="1" checked disabled> 
			<%}%> </td>
                    <script language=javascript>showDisableAccountCtrl("strPaySuretyAccountNo","<%=NameRef.getAccountNoByID(lPaySuretyAccountID)%>","�����Ѹ�������˺�","width=20%","")
</script>
                    <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="center">����ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strPaySuretyClientName" class="box"   rows="2" cols="30" disabled><%=NameRef.getClientNameByAccountID(lPaySuretyAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR > 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> 
			<%
			  if (lSuretyBankID < 0)
			  {
			  %>
			  <INPUT  name="Rb3" 
            	type=radio value="2"  disabled> 
			 <%}
			else
			{
			%>
			<INPUT  name="Rb3" 
            type=radio value="2" checked disabled> 
			<%}%> </td>
                    <td>�����У�</td>
					<td width="100">&nbsp;</td>
					<td><textarea name="strSuretyBankName" class="box"   rows="2" cols="30" disabled><%=NameRef.getBankNameByID(lSuretyBankID)%></textarea></td>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR > 
              <TD height="20" vAlign="center" nowrap width="15%"><U>������������ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR > 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">&nbsp; </td>
                    <script language=javascript>showDisableAccountCtrl("sreReceiveSuretyAccountNo","<%=NameRef.getAccountNoByID(lReceiveSuretyAccountID)%>","�����������˺�","width=20%","")
</script>           <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="center">�տ�ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strReceiveSuretyAccountName" class="box"   rows="2" cols="30" disabled><%=NameRef.getClientNameByAccountID(lReceiveSuretyAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR>
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR > 
              <td width="80%"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD  height="21"  valign="top"width="250">Ӧ������ </TD>
                    <TD height="21" vAlign="top" width="250">ʵ�ʸ���</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
				  <tr> 
                    <td>������Ϣ��</td>
                    <TD  height="20"  valign="top">  
                      <fs:amount 
				       		form="frmV016"
			       			name="dInterest"
			       			value="<%=dInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
	       			</TD>
                    <TD height="20" vAlign="top">  
                      <fs:amount 
				       		form="frmV016"
			       			name="dRealInterest"
			       			value="<%=dRealInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top" nowrap>
					<%
					if  (lIsRemitInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitInterest"  disabled>
					<%
					}
					%>
                      �⻹ʣ����Ϣ</TD>
                  </tr>
                  <tr> 
                    <td>���� ������Ϣ��</td>
                    <TD  height="20"  valign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dInterestReceiveAble"
			       			value="<%=dInterestReceiveAble%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealInterestReceiveAble"
			       			value="<%=dRealInterestReceiveAble%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>&nbsp; &nbsp;&nbsp;&nbsp;������Ϣ��</td>
                    <TD  height="20"  valign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dInterestIncome"
			       			value="<%=dInterestIncome%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealInterestIncome"
			       			value="<%=dRealInterestIncome%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
				 <tr> 
                    <td>������</td>
                    <TD  height="20"  valign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dCompoundInterest"
			       			value="<%=dCompoundInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealCompoundInterest"
			       			value="<%=dRealCompoundInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitCompoundInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitCompoundInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitCompoundInterest"  disabled>
					<%
					}
					%>
                      �⻹ʣ�ิϢ</TD>
                  </tr>
                  <tr> 
                    <td>���ڷ�Ϣ��</td>
                    <TD  height="20"  valign="top"> 
                    	<fs:amount 
				       		form="frmV016"
			       			name="dOverDueInterest"
			       			value="<%=dOverDueInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealOverDueInterest"
			       			value="<%=dRealOverDueInterest%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitOverDueInterest == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitOverDueInterest" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitOverDueInterest"  disabled>
					<%
					}
					%>
                      �⻹ʣ�෣Ϣ</TD>
                  </tr>
                  <tr> 
                    <td>�����ѣ�</td>
                    <TD  height="20"  valign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dSuretyFee"
			       			value="<%=dSuretyFee%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealSuretyFee"
			       			value="<%=dRealSuretyFee%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">
					<%
					if  (lIsRemitSuretyFee == Constant.RecordStatus.VALID)
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitSuretyFee" checked disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox"  class="box" name="lIsRemitSuretyFee"  disabled>
					<%
					}
					%>
                      �⻹ʣ�ൣ����</TD>
                  </tr>
				  <tr> 
                    <td>��Ϣ�Ѻϼƣ�</td>
                    <TD  height="20"  valign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dSum"
			       			value="<%=(dInterest+dCompoundInterest+dOverDueInterest+dSuretyFee)%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">  
                    	<fs:amount 
				       		form="frmV016"
			       			name="dRealSum"
			       			value="<%=(dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealSuretyFee)%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>�⻹ԭ��</td>
                    <TD  height="20"  valign="top"><textarea name="strAdjustInterestReason" class="box"   rows="2" cols="30" disabled><%=DataFormat.formatString(strAdjustInterestReason)%></textarea></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR > 
              <TD height="20" vAlign="center" nowrap width="15%"><U>����/��Ϣ�����취</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR > 
              <td colspan="4"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD width="250">
					<%
					if (lCapitalAndInterstDealway == 1)
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  checked disabled>
					<%
					}
					else
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  disabled>
					<%
					}
					%>
                      ���ܴ��� </TD>
                    <TD vAlign="center">&nbsp;</TD>
                    <TD height="20" vAlign="center" width="250"><%
					if (lCapitalAndInterstDealway == 2)
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  checked disabled>
					<%
					}
					else
					{
					%>
					<input type="radio" name="lCapitalAndInterstDealway"  disabled>
					<%
					}
					%>
                      �ֱʴ���</TD>
                    <TD height="20" vAlign="top">&nbsp; </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <TABLE align=center height=116 width="97%">
          <TBODY>
            <TR vAlign=center> 
              <TD height=32 colspan="6" valign="top"><table width="100%" border="0" >
                  <tr> 
                    <td width="10">
					<%
					if (lPreFormID < 0)
					{
					%>
					<input type="checkbox" name="lLoanNoteID"  disabled>
					<%
					}
					else
					{
					%>
					<input type="checkbox" name="lLoanNoteID" checked  disabled>
					<%
					}
					%>
					</td>
                    <td width="100">��ǰ����֪ͨ��</td>
                    <td>
					<input type="text" name="strLoanNoteNo"  class="box" value="<%=NameRef.getAheadPayFormNoByID(lPreFormID)%>" disabled></td>
                  </tr>
                </table></TD>
            </TR>
            <TR vAlign=center> 
              <TD height=20 width="10%">�����</TD>
			  <TD height=20 width="23%">  
			  		<fs:amount 
				       		form="frmV016"
			       			name="dAmount"
			       			value="<%=dAmount%>"
			       			disabled="true"
			       			currencyID="<%=sessionMng.m_lCurrencyID%>"/> 
              <TD height=32 width="14%">��Ϣ�գ�</TD>
              <TD height=32 width="14%"><INPUT class=box name="tsInterestStart" value="<%=DataFormat.formatDate(tsInterestStart)%>" disabled> </TD>
              <TD height=32 width="8%">&nbsp;</TD>
              <TD height=32 width="31%"></TD>
            </TR>
            <TR> 
              <TD align=left height=20 vAlign=center width="10%">���׺ţ�</TD>
              <TD align=left height=20 vAlign=center width="23%">
			  <INPUT class=box  name="strTransNo" value="<%=DataFormat.formatString(strTransNo)%>" disabled> </TD>
              <TD align=left height=20 vAlign=center width="14%">ժ Ҫ��</TD>
			  <TD align=left height=20 vAlign=center width="14%">
			  <INPUT class=box  maxLength=200 name="strAbstract"  size=25 value="<%=DataFormat.formatString(strAbstract)%>" disabled> </TD>
              <TD align=left height=20 vAlign=center width="8%">&nbsp;</TD>
              <TD align=left height=20 vAlign=center width="31%">&nbsp;</TD>
            </TR>
            <TR> 
              <TD align=left colSpan=6 height=20 vAlign=top> 
                <DIV align=right> 
<%
	if("Query".equalsIgnoreCase(strAction))
	{
		if(strOBInstr!=null && strOBInstr.equals("obinstr"))
		{
%>
			<input type="button" name="print" value=" �� ӡ " class="button1" onClick="doPrint();">
			<input class="button1" name="Submit32" type="button" value=" �� �� " onClick="doReturn();">	
<%	
		}
		else
		{
%>
				<input type="button" name="print" value=" �� ӡ " class="button1" onClick="doPrint();">
				<input type="button" name="close" value=" �� �� " class="button1" onClick="window.close();">
			
<%
		}
	}
	else
	{
%>
          		<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
				%>
					<input type="button" name="check" value=" �� �� " class="button1" onClick="doCheck();">
				<%
				}
				else
				{
				%>
					<input type="button" name="check" value=" ȡ������ " class="button1" onClick="doUndoCheck();">
				<%
				}
				%>
					<input type="button" name="print" value=" �� ӡ " class="button1" onClick="doPrint();">
					<input type="button" name="return" value=" �� �� " class="button1" onClick="doReturnFun();"> 
<%
	}
%>
			  </DIV>
			 </TD>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=top width="100%"> <HR> <TABLE align=center border=0 height=22 width="97%">
          <TBODY>
            <TR vAlign=center> 
              <TD height=25 width="10%">	<%=(lStatusID == SETTConstant.TransactionStatus.SAVE)?"���˱�ע":"ȡ�����˱�ע��"%></TD>
              <TD height=25 vAlign=top width="16%"><INPUT class=box name="strCheckAbstract" size="40" value="<%=DataFormat.formatString(strCheckAbstract)%>" onfocus="nextfield='submitfunction';" maxlength="100"> 
              </TD>
              <TD height=25 vAlign=center width="7%">¼���ˣ�</TD>
              <TD height=25 vAlign=center width="8%"><%=NameRef.getUserNameByID(lInputUserID)%></TD>
              <TD height=25 width="8%">¼�����ڣ�</TD>
              <TD height=25 width="8%"><%=DataFormat.formatDate(tsInput)%></TD>
			  <%
			 if(lStatusID != SETTConstant.TransactionStatus.SAVE)
			 {
			  %>
              <TD height=25 width="7%">�����ˣ�</TD>
              <TD height=25 width="8%"><%=NameRef.getUserNameByID(lCheckUserID)%></TD>
              <TD height=25 width="8%">�������ڣ�</TD>
              <TD height=25 width="8%"><%=DataFormat.formatDate(tsExecute)%></TD>
			  <%
			  }
			  else
			  {
			  %>
			  <TD height=25 width="7%">�����ˣ�</TD>
              <TD height=25 width="8%">&nbsp;</TD>
              <TD height=25 width="8%">�������ڣ�</TD>
              <TD height=25 width="8%">&nbsp;</TD>
			  <%
			  }
			  %>
              <TD height=25 width="6%">״̬��</TD>
              <TD height=25 width="6%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%> </TD>
            </TR>
          </TBODY></TABLE></TD></TR></TBODY></TABLE></form>
<script language="JavaScript">
//��ʶ�Ƿ����ύ����
var isSubmited = false;

function checkSuccess()
{
    if (confirm("���˳ɹ����Ƿ��ӡ?"))
    {
        //code
    }
	else
	{
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v015.jsp";
	}
}

function doCheck()
{
    if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }

	if (confirm("�Ƿ񸴺˴˱�ҵ������?")) 
	{
		isSubmited = true;
		document.frmV016.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.frmV016.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }

	if(!validateFields(frmV016)) return;
	
	if (confirm("�Ƿ�ȡ�����˴˱�ҵ������?")) 
	{
		isSubmited = true;
		document.frmV016.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.frmV016.submit();
	}
}

function doPrint()
{
<%	
	long lOBReturn=-1;
	String strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>	
	window.open( "../accountinfo/a919-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=SETTConstant.TransactionType.TRUSTLOANRECEIVE%>&strSuccessPageURL='../accountinfo/a925-v.jsp'&strFailPageURL='../accountinfo/a925-v.jsp'&lReturn=<%=lOBReturn%>");
}
function doReturn()
		{
			document.location.href="<%=strContext%>/settlement/obinstruction/control/c001.jsp?strSuccessPageURL=/settlement/obinstruction/view/v002.jsp&strFailPageURL=/settlement/obinstruction/view/v002.jsp&_pageLoaderKey=<%=strPageLoaderKey%>";
		}	
function doReturnFun()
{
	if(isSubmited == true)
    {
        alert("�������ύ����Ⱥ�");
        return;
    }
	
	if (confirm("�Ƿ񷵻�?")) 
	{
		isSubmited = true;
		//alert(1);
		showSending();
		//alert(2);
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/loan/control/c013.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.TRUSTLOANRECEIVE%>&strSuccessPageURL=../view/v017.jsp&strFailPageURL=../view/v017.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";//���˳ɹ�����ʧ�ܾ��������Ӳ���ҳ��
	<%
	}else 
	{%>
		document.location.href="<%=strContext%>/settlement/tran/loan/view/v015.jsp";//ƥ��ҳ��
	<%}%>
	
	}
	
}

function allFields()
{
	
	this.aa = new Array("strCheckAbstract","ȡ�����˱�ע","string",1);
	
} 
</script>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV016.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV016");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV016.strCheckAbstract);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV016");     
</script>
<%
	}
%>
<%
	}
%>

<%	
		if(Constant.ActionResult.SUCCESS.equals(strActionResult) && String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
		{
			%>
				<script language="JavaScript">
					checkSuccess();
				</script>
			<%
		}

OBHtml.showOBHomeEnd(out);
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<%@ include file="/common/SignValidate.inc" %>