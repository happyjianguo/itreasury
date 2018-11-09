<%--
 ҳ������ ��a914-v.jsp
 ҳ�湦�� : ��Ӫ����š�������/ȡ��������ʾҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

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
//�������
	TransGrantLoanInfo info = null;
	LoanPayFormDetailInfo patforminfo = null;
	
	patforminfo = (LoanPayFormDetailInfo)request.getAttribute("LoanPayFormDetailInfo");
	info = (TransGrantLoanInfo)request.getAttribute("TransGrantLoanInfo");
	//patforminfo = new LoanPayFormDetailInfo();
	//info = new TransGrantLoanInfo();
	
	//�������
	long lStatusID = -1;
	int  nOrderByCode = -1;
	long lDesc = -1;
	String strAction 				= null;								//��������
	strAction = (String)request.getAttribute("strAction");	
	
	//�������
	long lPayInterestAccountID = -1;//����-��Ϣ�˻�
	long lPaySuretyFeeAccountID = -1;//����-����˺�
	long lReceiveSuretyFeeAccountID = -1;//����-�������˺�
	
	long lDepositAccountID = -1;//������������˻�
	
	long lPayTypeID = -1;//���ʽ
	long lBankID = -1;//�����
	String strExtAcctNo = "";//�����տλ�˺�
	String strExtAcctName = "";//�����տλ����
	String strProvince = "";//�����(ʡ)
	String strCity = "";//�����(��)
	String strBankName = "";//������
	String strExtBankNo = ""; //������
	
	long lAbstractID = -1;//ժҪID
	String strAbstract = "";//ժҪ����
	String strCheckAbstract = "";//����ժҪ
	
	double dAmount = 0.0;//���
	java.sql.Timestamp tsExecute = null;//ִ����
	java.sql.Timestamp tsInterestStart = null;//��Ϣ��
	java.sql.Timestamp tsModify = null;//��������
	String strTransNo = "";
	
	//�Ӳ�ѯ���info�еõ�ֵ
	lPayInterestAccountID = info.getPayInterestAccountID();//����-��Ϣ�˻�
	lPaySuretyFeeAccountID = info.getPaySuretyFeeAccountID();//����-����˺�
	lReceiveSuretyFeeAccountID = info.getReceiveSuretyFeeAccountID();//����-�������˺�
		
	lDepositAccountID = info.getDepositAccountID();//������������˻�
		
	lPayTypeID = info.getPayTypeID();//���ʽ
	lBankID = info.getBankID();//�����
	tsModify = info.getModify();
	
	if(info.getExtAcctNo() != null)
	{
		strExtAcctNo = info.getExtAcctNo();//�����տλ�˺�
	}
	if(info.getExtAcctName() != null)
	{
		strExtAcctName = info.getExtAcctName();//�����տλ����
	}
	if(info.getProvince() != null)
	{
		strProvince = info.getProvince();//�����(ʡ)
	}
	if(info.getCity() != null)
	{
		strCity = info.getCity();//�����(��)
	}
	if(info.getBankName() != null)
	{
		strBankName = info.getBankName();//������
	}
	if(info.getExtBankNo() != null)
	{
		strExtBankNo = info.getExtBankNo();//������
	}
	
	if(info.getTransNo() != null)
	{
		strTransNo = info.getTransNo();//���׺�
	}
	lAbstractID = info.getAbstractID();//ժҪID
	if(info.getAbstract() != null)
	{
		strAbstract = info.getAbstract();//ժҪ����
	}
	if(info.getCheckAbstract() != null)
	{
		strCheckAbstract = info.getCheckAbstract();//����ժҪ����
	}	
	
	dAmount = info.getAmount();//���
	tsExecute = info.getExecute();//ִ����
	tsInterestStart = info.getInterestStart();//��Ϣ��
	tsModify = info.getModify();//��������
	
	//��ȡ����
	String strTemp = null;
	strTemp = (String)request.getAttribute("lStatusID");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		lStatusID = Long.valueOf(strTemp).longValue();
	}
	else{
		lStatusID=info.getStatusID();
	}
	strTemp = (String)request.getAttribute("nOrderByCode");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		nOrderByCode = Integer.valueOf(strTemp).intValue();
	}
	strTemp = (String)request.getAttribute("lDesc");
	if(strTemp != null && strTemp.length()>0)
	{
		lDesc = Long.valueOf(strTemp).longValue();
	}
%>
<form name="frmV006" method="post">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="strAction"  type="hidden">
<input name="strSuccessPageURL"  type="hidden">
<input name="strFailPageURL"  type="hidden">

<input type="hidden" name="lTransactionTypeID" value="<%=SETTConstant.TransactionType.TRUSTLOANGRANT%>">
<input type="hidden" name="lID" value="<%=info.getID()%>">
<input type="hidden" name="tsModify" value="<%=tsModify.toString()%>">
<input type="hidden" name="lStatusID" value="<%=lStatusID%>"> 

<input type="hidden" name="nOrderByCode" value="<%=nOrderByCode%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="lCheckUserID" value="<%=sessionMng.m_lUserID%>">
<TABLE border=0 class=top height=290 width="99%">
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=2 width="100%"><B>��Ӫ�����</B></TD></TR>
  <TR>
    <TD colSpan=2 height=20 vAlign=top width="100%">
      <TABLE align=center borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD height=20 vAlign=middle width="17%">��Ӫ����ͻ���ţ�</TD>
          <TD height=20 vAlign=top width="33%"><INPUT class=box 
            disabled maxLength=10 name="txtTrustLoanClientNoCtrl" size=10 value="<%=NameRef.getClientCodeByAccountID(info.getLoanAccountID())%>"> 
            </TD>
          <TD height=20 width="16%">��Ӫ����ͻ����� ��</TD>
          <TD height=20 width="34%"><textarea class=box disabled 
            name="txtTrustLoanClientNameCtrl" rows=2 cols=30><%=NameRef.getClientNameByAccountID(info.getLoanAccountID())%></textarea> </TD></TR>
        <TR borderColor=#E8E8E8>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtTrustLoanAccountNoCtrl","<%=NameRef.getAccountNoByID(info.getLoanAccountID())%>","��Ӫ�����˺�","width=17%","width=33%")
</script>				  
<!--*********************************************************************************-->			
          <TD height=20 width="16%">&nbsp;</TD>
          <TD height=20 width="34%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="17%">��ͬ�ţ�</TD>
          <TD height=20 width="33%"><INPUT class=box disabled 
            name="txtContractNoCtrl" value="<%=NameRef.getContractNoByID(info.getLoanContractID())%>"> </TD>
          <TD height=20 width="16%">�ſ�֪ͨ����</TD>
          <TD height=20 width="34%"><INPUT class=box 
            name="txtLetoutRequisitionNoCtrl" value="<%=NameRef.getPayFormNoByID(info.getLoanNoteID())%>" disabled></TD></TR></TABLE></TD></TR>
  <TR>
    <TD height=20 rowSpan=2 vAlign=bottom width="35%">
      <TABLE align=right border=1 borderColor=#999999 height="100%" 
        width="96%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=23 vAlign=middle><U>��Ӫ������ϸ���� 
            </U></TD>
          <TD borderColor=#E8E8E8 height=23 vAlign=top width="60%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>��ʼ���ڣ�</TD>
          <TD height=20 vAlign=top width="60%"><INPUT class=box 
            disabled name="txtDateStartCtrl" value="<%=DataFormat.formatDate(patforminfo.getStart())%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=27 vAlign=middle>�������ڣ�</TD>
          <TD height=27 vAlign=top width="60%"><INPUT class=box 
            disabled name="txtDateEndCtrl" value="<%=DataFormat.formatDate(patforminfo.getEnd())%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=27 vAlign=middle>��������:</TD>
          <TD height=27 vAlign=top width="60%">
				<%
				long[] lLoanTypeArray = LOANConstant.LoanType.getAllCode();
				%>
				<select class=box name="slcLoanType" disabled>
					<option value="-1" <%=(patforminfo.getLoanType() == -1 ? "selected" : "")%>></option>
					<%
					for (int i=0; i < lLoanTypeArray.length; i++)
					{
					%>
					<option value="<%=lLoanTypeArray[i]%>" <%=(patforminfo.getLoanType() == lLoanTypeArray[i] ? "selected" : "")%>><%=LOANConstant.LoanType.getName(lLoanTypeArray[i])%></option>
					<%
					}
					%>
				</select>
 		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>�������ޣ�</TD>
          <TD height=20 vAlign=top width="60%">
		  <%
					SETTHTML.showLoanTermListCtrl(out,"slcLoanInterval",patforminfo.getLoanTerm(),true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
				%>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD colSpan=2 height=20 vAlign=middle>&nbsp;</TD>
          <TD height=20 vAlign=top width="60%">&nbsp;</TD></TR></TABLE></TD>
    <TD height=9 vAlign=bottom width="65%">
      <TABLE align=left border=1 borderColor=#999999 height="100%" 
        width="98%">
        <TR borderColor=#E8E8E8>
          <TD height=23 width="20%"><U>��Ϣ��ϸ���� </U></TD>
          <TD borderColor=#E8E8E8 height=23 width="39%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=23 width="13%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=23 width="28%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=25 width="20%">���ʣ�</TD>
          <TD height=25 width="39%"><INPUT class=tar disabled 
            name="txtRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getInterestRate(),'0',6)%>%"> </TD>
          <TD height=25 width="13%">&nbsp;</TD>
          <TD height=25 width="28%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtInterestAccountIDNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","��Ϣ�˻�","width=20%","width=39%")
</script>				  
<!--*********************************************************************************-->			
          <TD height=25 width="13%">�ͻ����� :</TD>
		  <TD height=25 width="28%"><textarea class=box disabled 
            name="txtInterestAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPayInterestAccountID )%></textarea> 
			<!--<img src="/graphics/but-all.GIF"  border=0 align=absmiddle  alt="���ܲ���">-->
      </TD></TR></TABLE></TD></TR>
  <TR>
    <TD height=9 vAlign=bottom width="65%">
      <TABLE align=left border=1 borderColor=#999999 height="100%" 
        width="98%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=23 vAlign=middle><U>������ϸ����</U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">
		  <INPUT  disabled name=checkbox type=checkbox <%=(patforminfo.getAssureRate() > 0 ? "checked" : "" )%> value=checkbox> </TD>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="20%">��������:</TD>
          <TD height=20 vAlign=top width="34%">
		    <INPUT class=tar disabled name="txtSuretyRateCtrl" value="<%=DataFormat.formatTax(patforminfo.getAssureRate(),'0',6)%>%"> </TD>
          <TD height=20 vAlign=top width="13%">&nbsp;</TD>
          <TD height=20 vAlign=top width="28%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">&nbsp;</TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtDebtorAccountIDCtrl","<%=NameRef.getAccountNoByID(lPaySuretyFeeAccountID)%>","����˺�","width=20%","width=34%")
</script>				  
<!--*********************************************************************************-->			  
          <TD height=25 width="13%">�ͻ����� :</TD>
          <TD height=25 width="28%">
		    <textarea class=box disabled name="txtDebtorAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPaySuretyFeeAccountID )%></textarea>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 vAlign=middle width="5%">&nbsp;</TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtSuretyAccountIDCtrl","<%=NameRef.getAccountNoByID(lReceiveSuretyFeeAccountID)%>","�������˺�","width=20%","width=34%")
</script>				  
<!--*********************************************************************************-->			  
          <TD height=25 width="13%">�ͻ����� :</TD>
          <TD height=25 width="28%">
		    <textarea class=box disabled name="txtSuretyAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lReceiveSuretyFeeAccountID)%></textarea>
      </TD></TR></TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=262 vAlign=top width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>��Ӫ����ſ��˻���ϸ���� 
        </U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
		    <INPUT disabled name="radio1" type=radio value="1" <%=((lDepositAccountID > 0 || lPayTypeID < 0) ? "checked" : "")%>> 
            </TD>
<!--*********************************************************************************-->	
<script language=javascript>			  
		showDisableAccountCtrl("txtGrantCurrentAccountIDCtrl","<%=NameRef.getAccountNoByID(lDepositAccountID)%>","������������˺�","width=18%","width=28%")
</script>				  
<!--*********************************************************************************-->				
          <TD height=20 width="15%">���ڿͻ�����:</TD>
          <TD height=20 width="36%"><textarea class=box disabled 
            name="txtGrantCurrentAccountName" rows=2 cols=30><%=NameRef.getClientNameByAccountID(lDepositAccountID)%></textarea>
		  </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"><INPUT 
            disabled name="radio1" <%=(lPayTypeID > 0 ? "checked" : "")%> type=radio value="2" > </TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">�ſʽ��</TD>
          <TD height=20 width="28%">
		  <%
		  	SETTConstant.PayType.showList(out,"lPayTypeID",1,-1,false,true,"disabled onfocus=\"nextfield='lRemitOutBranchIDCtrl';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>

		  </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">��������ƣ�</TD>
          <TD height=20 width="28%"><textarea class=box disabled 
            name="txtRemitOutBankIDCtrl" rows=2 cols=30><%=NameRef.getBankNameByID(lBankID)%></textarea> 
			<!--<img src="/graphics/but-all.GIF"  border=0 align=absmiddle  alt=""> --></TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=18 width="3%"></TD>
		  <td height="18" bordercolor="#E8E8E8" width="18%">�����տλ�˺ţ�</td>
		  <td height="18" width="28%"> 
            <input type="text" name="txtRemitInAccountNoCtrl" value="<%=strExtAcctNo%>" disabled size="18" class="box">
            </td>
          <TD height=18 width="15%">�����տλ����:</TD>
          <TD height=18 width="36%"><textarea class=box disabled 
            name="txtRemitInAccountNameCtrl" rows=2 cols=30><%=strExtAcctName%></textarea> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD height=20 width="18%">�����(ʡ)��</TD>
          <TD height=20 width="28%"><INPUT class=box disabled 
            name="txtInProvinceCtrl" value="<%=strProvince%>" size=24> </TD>
          <TD height=20 width="15%">�����(��)��</TD>
          <TD height=20 width="36%"><INPUT class=box disabled 
            name="txtInCityCtrl" value="<%=strCity%>"  size=24> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">���������ƣ�</TD>
          <TD height=20 width="28%"><textarea class=box disabled rows=2 cols=30
            name="txtRemitInBankCtrl"><%=strBankName%></textarea> </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="36%">&nbsp;</TD>
</TR>
         </TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=66 vAlign=top width="100%">
      <TABLE align=center height=2 width="97%">
        <TR vAlign=middle>
          <TD height=32 width="10%">�� �</TD>
          <TD height=32 width="20%"><%=sessionMng.m_strCurrencySymbol%> <INPUT class=tar disabled 
            name="txtMoneyCtrl" size=17 value="<%=DataFormat.formatDisabledAmount(dAmount)%>"> </TD>
          <TD height=32 width="10%">��Ϣ�գ�</TD>
          <TD height=32 width="25%"><INPUT class=box disabled 
            name="txtDateInterestStartCtrl" value="<%=DataFormat.formatDate(tsInterestStart)%>"> </TD>
          <TD height=32 width="10%">ִ���գ�</TD>
          <TD height=32 width="25%"><INPUT type="text" disabled class="box"
            name="txtDateExecuteCtrl" value="<%=DataFormat.formatDate(tsExecute)%>"> </TD></TR>
        <TR>
          <TD align=left height=20 vAlign=middle width="10%">���׺ţ� </TD>
          <TD align=left height=20 vAlign=middle width="20%"><INPUT class=box disabled name="txtTransactionNoCtrl" 
            value="<%=strTransNo%>"> </TD>
          <TD align=left height=20 vAlign=middle width="10%">ժ Ҫ��</TD>
          <TD align=left height=20 vAlign=middle width="25%"><INPUT class=box disabled maxLength=2000 name="txtNoteCtrl" 
            size=25 value="<%=strAbstract%>"> </TD>
          <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
          <TD align=left height=20 vAlign=middle width="25%">&nbsp;</TD></TR>
        <TR>
          <TD align=left colSpan=6 height=20 vAlign=top>
            <DIV align=right>
<%
	if("Query".equalsIgnoreCase(strAction))
	{
%>
	            <INPUT class=button name=Submit32 onclick="print()" type=button value=" �� ӡ "> 
				<input type="button" name="close" value=" �� �� " class="button" onClick="window.close();">
<%
	}
	else
	{
%>
			<%
			if (lStatusID == SETTConstant.TransactionStatus.SAVE)
			{
			%>
			<INPUT class=button name=Submit3 onclick="doCheck(frmV006);" type=button value=" �� �� "> 
			<%
			}
			else if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV006);" type=button value=" ȡ������ "> 			
			<%
			}
			%>
            <INPUT class=button name=Submit32 onclick="print()" type=button value=" �� ӡ "> 
			<INPUT class=button name=Submit322 onclick="doBack(frmV006);" type=button value=" �� �� "> 
<%
	}
%>
            </DIV></TD></TR></TABLE></TD></TR>
  <TR>
    <TD colSpan=2 height=20 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TR vAlign=middle>
          <TD height=25 width="8%">���˱�ע:</TD>
          <TD height=25 vAlign=top width="19%"><INPUT class=box 
            name="strCheckAbstract" value="<%=strCheckAbstract%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">¼����:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(info.getInputUserID())%></TD>
          <TD height=25 width="8%">¼������:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(info.getInputDate())%></TD>
          <TD height=25 width="6%">������:</TD>
          <TD height=25 width="7%"><%=NameRef.getUserNameByID(info.getCheckUserID())%></TD>
          <TD height=25 width="8%">��������:</TD>
          <TD height=25 width="9%"><%=(info.getCheckUserID() > 0 ? DataFormat.formatDate(info.getExecute()) : "&nbsp;")%></TD>
          <TD height=25 width="5%">״̬:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR>
  </TABLE>
</form>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<script language="JavaScript">
firstFocus(document.frmV006.strCheckAbstract);
//setSubmitFunction("<%=(lStatusID == SETTConstant.TransactionStatus.SAVE ? "doCheck" : "doCancelCheck")%>(frmV006);");
setFormName("frmV006"); 
</script>
<%
	}
%>
<script language="JavaScript">
function doBack(form)
{
	if (confirm("�Ƿ񷵻أ�"))
	{
		<%
		if (lStatusID == SETTConstant.TransactionStatus.SAVE)
		{
		%>
		form.action = "../view/v005.jsp";
		<%
		}
		else
		{
		%>
		form.action = "../control/c002.jsp";
		form.strSuccessPageURL.value = '../view/v007.jsp';
		form.strFailPageURL.value = '../view/v006.jsp';
		<%
		}
		%>
		showSending();
		form.submit();
	}	
}
function print(){
<%	
	long lOBReturn=-1;
	String strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>	
	if (confirm("�Ƿ��ӡ?")){
		window.open( "../accountinfo/a915-v.jsp?lID=<%=info.getID()%>&lTransactionTypeID=<%=SETTConstant.TransactionType.TRUSTLOANGRANT%>&strSuccessPageURL='../accountinfo/a914-v.jsp'&strFailPageURL='../accountinfo/a914-v.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v005.jsp';
	form.strFailPageURL.value = '../view/v006.jsp';
	
	if (confirm("�Ƿ񸴺ˣ�"))
	{
		form.action = "../control/c004.jsp";
		showSending();
		form.submit();
	}
}
function doCancelCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CANCELCHECK%>';	
	form.strSuccessPageURL.value = '../view/v007.jsp';
	form.strFailPageURL.value = '../view/v006.jsp';
	
	if (!validateFields(form))
	{
		return false;
	}
	if (confirm("�Ƿ�ȡ�����ˣ�"))
	{
		form.action = "../control/c004.jsp";
		showSending();
		form.submit();
	}
}
function allFields()
{
	this.aa = new Array("strCheckAbstract","���˱�ע","string",1);
}
</script>
<%	
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>