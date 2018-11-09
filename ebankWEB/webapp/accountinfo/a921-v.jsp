<%--
 ҳ������ ��v051_4.jsp
 ҳ�湦�� : ��ʴ����ջ�-����-����/ȡ������ҳ��
 ��    �� ��Barry
 ��    �� ��2003��12��5��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
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
/**
 * ����Ŵ󾵹��ñ��������´���š����ֱ�š���������
 */
long lOfficeID 				= sessionMng.m_lOfficeID;
long lCurrencyID 			= sessionMng.m_lCurrencyID;
String strFormName 			= "frmV051_4";

/**
 * ҳ����Ʊ���
 */
long lID = -1;	
Timestamp tsExecute = DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
long lInputUserID			= -1;													//¼����
long lCheckUserID			= sessionMng.m_lUserID;									//������
long lTransactionTypeID		= SETTConstant.TransactionType.MULTILOANRECEIVE;		//��������
long lStatusID				= -1;													//״̬,�������Ӳ��ҵ�Ĭ�ϲ�ѯ
long lRecordStatusID		= -1;													//��¼״̬
long lDesc 										= Constant.PageControl.CODE_ASCORDESC_DESC;				//����ʽ
long lOrderByCode								= -1;													//�����ֶ�
Timestamp tsInput			= null;													//¼��ʱ��
Timestamp tsModify 			= null;													//�޸�ʱ��
String strTempTransNO		= "";													//��ʱ���׺�

String strTransNo			= "";													//���׺�
/**
 * ҳ�����
 */
long lMultiLoanType 		= -1;													//��ʴ����ջ�����
long lClientID 				= -1;													//�ͻ�ID
long lDepositAccountID 		= -1;													//���ڴ���˺�
long lBankID 				= -1;													//����ID
String strDeclarationNo 	= "";													//������
double dAmount 				= 0.0;													//���
Timestamp tsInterestStart 	= tsExecute;											//��Ϣ��
long lAbstractID 			= -1;													//ժҪID
String strAbstract 			= "";													//ժҪ
String strCheckAbstract		= "";													//����ժҪ

String strRbValue 			= "1";													//radio button״̬

//ҳ�渨������
String strAction = null;															//��������
String strActionResult = Constant.ActionResult.FAIL;								//�������


//��Request�л�ò���

		//ҵ������
		String strTemp = null;														//��ʱ����
		
		//ҳ����Ʋ���
		strTemp = (String)request.getAttribute("strActionResult");					//�������
		if (strTemp != null && strTemp.trim().length()>0){
			  strActionResult = (String)request.getAttribute("strActionResult");
		}
		strTemp = (String)request.getAttribute("strAction");						//��������
		if (strTemp != null && strTemp.trim().length()>0){
			  strAction = (String)request.getAttribute("strAction");
		}
	
		strTemp = (String)request.getAttribute("lID");								//ID
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			lID = Long.valueOf(strTemp.trim()).longValue();
		}

		strTemp = (String)request.getAttribute("lInputUserID");						//¼����
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			lInputUserID = Long.valueOf(strTemp.trim()).longValue();
		}
		
		strTemp = (String)request.getAttribute("tsExecute");						//ִ����
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			tsExecute = DataFormat.getDateTime(strTemp.trim());
		}
		Log.print("��������еĸ�����֮ǰ"+lCheckUserID);
		strTemp = (String)request.getAttribute("lCheckUserID");						//������
		Log.print("�����еĸ�����"+strTemp);
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			lCheckUserID = Long.valueOf(strTemp.trim()).longValue();
		}
		Log.print("��������еĸ�����֮��"+lCheckUserID);
		strTemp = (String)request.getAttribute("lStatusID");						//����״̬
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			lStatusID = Long.valueOf(strTemp.trim()).longValue();
		}
		/**
		 * Ĭ�ϼ�¼״̬�͵�ǰ״̬���
		 */
		lRecordStatusID = lStatusID;
		
		strTemp = (String)request.getAttribute("lRecordStatusID");					//����״̬,�����
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			lRecordStatusID = Long.valueOf(strTemp.trim()).longValue();
		}
		
		strTemp = (String)request.getAttribute("tsInput");						//¼��ʱ��
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			tsInput = DataFormat.getDateTime(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("tsModify");						//�޸�ʱ��
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			tsModify = DataFormat.getDateTime(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("strTempTransNO");				//��ʱ���׺�
		if (strTemp != null && strTemp.trim().trim().length() > 0){
			strTempTransNO = strTemp.trim();
		}
	
		strTemp = (String)request.getAttribute("lMultiLoanType");				//��ʴ����ջ�����
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lMultiLoanType = Long.valueOf(strTemp.trim()).longValue();
		}
	
		strTemp = (String)request.getAttribute("lClientID");					//�ͻ����
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lClientID = Long.valueOf(strTemp.trim()).longValue();
		}
		
		strTemp = (String)request.getAttribute("lDepositAccountID");			//�����˺�
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lDepositAccountID = Long.valueOf(strTemp.trim()).longValue();
		}
		
		
		strTemp = (String)request.getAttribute("lBankID");						//����ID
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lBankID = Long.valueOf(strTemp.trim()).longValue();
		}
		strTemp = (String)request.getAttribute("strDeclarationNo");				//������
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			strDeclarationNo = strTemp.trim();
		}
		
		strTemp = (String)request.getAttribute("dAmount");						//���
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			dAmount = DataFormat.parseNumber(strTemp.trim());
		}
		
			
		strTemp = (String)request.getAttribute("tsInterestStart");				//��Ϣ��
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			tsInterestStart =  DataFormat.getDateTime(strTemp.trim());
		}
			
		strTemp = (String)request.getAttribute("lAbstract");					//ժҪID
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lAbstractID = Long.valueOf(strTemp.trim()).longValue();
		}
		
		strTemp = (String)request.getAttribute("lAbstractCtrl");				//ժҪ�ı�
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			strAbstract = strTemp.trim() ;
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");				//����ժҪ�ı�
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			strCheckAbstract = strTemp.trim() ;
		}
		strTemp = (String)request.getAttribute("lDesc");						//����ʽ
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lDesc = Long.valueOf(strTemp.trim()).longValue();
		}
		strTemp = (String)request.getAttribute("lOrderByCode");					//�����ֶ�
		if (strTemp != null && strTemp.trim().trim().length() > 0)
		{
			lOrderByCode = Long.valueOf(strTemp.trim()).longValue();
		}				
		strTemp = (String)request.getAttribute("strTransNo");					//���׺�
		if (strTemp !=null && strTemp.trim().length()>0){
			strTransNo = strTemp.trim();
		}
			
%>
<safety:resources />
<form name="frmV051_4" method="post">
	<TABLE border="0" class="top" width="99%">
		<TBODY>
				<TR class="tableHeader">
					<TD class="FormTitle" height="2" width="100%">
<!--ҳ�����-->
			<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
			<input name="strAction" type="hidden">
			<input name="strActionResult" type="hidden" value="<%=strActionResult%>">
			<input name="strSuccessPageURL" type="hidden" value="">
			<input name="strFailPageURL" type="hidden" value="">
 			<!--ҳ�����-->
 			<!--�޸�-->
 			<input name="lID" type="hidden" value="<%=lID%>">
 			<input name="lStatusID" type="hidden" value="<%=lRecordStatusID%>">
 			<input name="strTempTransNO" type="Hidden" value="<%=strTempTransNO%>">
			<input name="tsModify" type="Hidden" value="<%=tsModify%>">
			<input name="lCheckUserID" type="Hidden" value="<%=lCheckUserID%>">
			<input name="tsExecute" type="Hidden" value="<%=tsExecute%>">
 			<!--�޸�-->
 			<!--�������Ӳ���-->
 			<input name="lMultiLoanType" type="hidden" value="<%=lMultiLoanType%>">
 			<input name="lTransactionTypeID" type="Hidden" value="<%=lTransactionTypeID%>">
			<input name="lStatusID" type="Hidden" value="<%=lStatusID%>">
			<input name="lDesc" type="Hidden" value="<%=lDesc%>">
			<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
					</TD>
				</TR>
			
			<!--�������Ӳ���-->

				<TR class="tableHeader">
					<TD class="FormTitle" height="2" width="100%"><B>ҵ��������ʴ����ջ� 
      </B>
					</TD>
				</TR>
				<tr>
					<td align='center'>
						<table width="97%">
							<tr>
								<td width='80'>�ո�����:</td>
								<td align='left'>
									<%SETTConstant.MultiLoanType.showList(out,"lMultiLoanType",0,0,false,false,"onchange='changeMultiLoanType(frmV051_4)'disabled onfocus=nextfield='lClientIDCtrl'",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<TR>
					<TD height="130" vAlign="bottom" width="100%">
						<TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
							<TBODY>
								<TR borderColor="#E8E8E8">
									<TD colSpan="2" height="20"><U>���������ϸ����</U>
									</TD>
									<TD height="20" width="28%">&nbsp;	
									</TD>
									<TD height="20" width="13%">&nbsp;	
									</TD>
									<TD height="20" width="34%">&nbsp;	
									</TD>
								</TR>
								<TR borderColor="#E8E8E8">
									<TD height="20" width="3%">
									</TD>
									<%
//����ͻ���ŷŴ�
		String strCtrlNameC = "lClientID";
		String strTitleC = "����ͻ����";
		long lClientIDC = lClientID;
		String strClientNoC = NameRef.getClientCodeByID(lClientID);
		String strFirstTDC = "";
		String SecondTDC = "";
		String[] sNextControlsClientC = {"RbPay[0]"};
		String strRtnClientNameCtrlC = "strPayClientName";
		
	SETTMagnifier.createClientCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameC,
		strTitleC,
		lClientIDC,
		strClientNoC,
		strFirstTDC,
		SecondTDC,
		sNextControlsClientC,
		strRtnClientNameCtrlC);
%>
<script language="javascript">
document.all.lClientIDCtrl.disabled=true;
</script>
									<td width="119" height="28">
										<div align="left">	����ͻ�����:
										</div>
									</td>
									<td width="243" height="28">
                  <textarea name="strPayClientName" class="box" disabled  rows="2" cols="30"><%=NameRef.getClientNameByID(lClientID)%></textarea> 
                </td>
								</tr>
								<TR borderColor="#E8E8E8">
									<TD height="20" width="3%">
										<INPUT name="RbPay" disabled <%=lDepositAccountID>0?"checked":""%> type="radio" value="1" checked onFocus="nextfield='lDepositAccountIDCtrlCtrl3';" checked>
									</TD>
									<%	
//���ڴ���˺ŷŴ�
		String strCtrlNameAcct = "lDepositAccountID";
		String strTitleAcct = "���ڴ���˺�";
		long lClientIDAcct = lClientID;
		long lAccountIDAcct = lDepositAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lDepositAccountID);;
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;//�ո�����
		String strClientCtrlAcct = "lClientID";
		String strFirstTDAcct = "";
		String strSecondTDAcct = "";
		String[] strNextControlsAcct = {"dAmount"};
		String strRtnClientIDCtrlAcct = "lClientID";
		String strRtnClientNoCtrlAcct = "lClientIDCtrl";
		String strRtnClientNameCtrlAcct = "strPayClientName";
		
	SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameAcct,
		strTitleAcct,
		lClientIDAcct,
		lAccountIDAcct,
		strAccountNoAcct,
		lAccountGroupTypeIDAcct,
		lAccountTypeIDAcct,
		lReceiveOrPayAcct,
		strClientCtrlAcct,
		strFirstTDAcct,
		strSecondTDAcct,
		strNextControlsAcct,
		strRtnClientIDCtrlAcct,
		strRtnClientNoCtrlAcct,
		strRtnClientNameCtrlAcct);
%>
<script language="javascript">
document.all.lDepositAccountIDCtrlCtrl1.disabled=true;
document.all.lDepositAccountIDCtrlCtrl2.disabled=true;
document.all.lDepositAccountIDCtrlCtrl3.disabled=true;
document.all.lDepositAccountIDCtrlCtrl4.disabled=true;
</script>
									<TD height="32" vAlign="middle" width="10%">&nbsp;	
									</TD>
									<TD height="32" vAlign="middle" width="25%">&nbsp;	
									</TD>
								</TR>
								<TR borderColor="#E8E8E8">
									<TD height="20" width="3%">
										<INPUT name="RbPay" disabled <%=lBankID>0?"checked":""%> type="radio" value="2" onFocus="nextfield='lBankIDCtrl';">
									</TD>
									
									<%
// �տ��������ƷŴ�
		String strCtrlNameBranch = "lBankID";
		String strTitleBranch = "�տ���������";
		long lBranchIDBranch = lBankID;
		String strBranchNameBranch = NameRef.getBankNameByID(lBankID);
		long lIsSingleBankBranch = 0;//�Ƿ񵥱�����
		String strAccountCtrlBranch = "";
		String strFirstTDBranch = "";
		String strSecondTDBranch = "";
		String[] strNextControlsBranch = {"dAmount"};
		String strRtnBankAccountNoCtrlBranch = "";
			
	SETTMagnifier.createBranchCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameBranch,
		strTitleBranch,
		lBranchIDBranch,
		strBranchNameBranch,
		lIsSingleBankBranch,
		strAccountCtrlBranch,
		strFirstTDBranch,
		strSecondTDBranch,
		strNextControlsBranch,
		strRtnBankAccountNoCtrlBranch);
%>
<script language="javascript">
document.all.lBankIDCtrl.disabled=true;
</script>
									<td width="124" height="28">������:	
									</td>
									<td width="124" height="28"><input type="text" disabled class="box" name="strDeclarationNo" value="<%=strDeclarationNo%>">
									</td>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				
				<TR>
					<TD height="119" vAlign="top" width="100%">
						<TABLE align="center" height="8" width="97%">
							<TBODY>

								<TR vAlign="middle">
									<TD height="20" width="15%">	������:
									</TD>
									<TD height="20" width="20%"><%=sessionMng.m_strCurrencySymbol%> 
	<!--*****************************************************************************-->
<script language="javascript">// �������ؼ�
	createAmountCtrl("frmV051_4","dAmount","<%=DataFormat.formatDisabledAmount(dAmount)%>","tsInterestStart");
	document.all.dAmount.disabled=true;
</script><!--*****************************************************************************-->
									</TD>
									<TD height="32" width="10%">&nbsp;	
									</TD>
									<TD height="32" width="25%">&nbsp;	
									</TD>
									<TD height="32" width="10%">&nbsp;	
									</TD>
									<TD height="32" width="20%">&nbsp;	
									</TD>
								</TR>
								<TR vAlign="middle">
									<TD height="32" width="15%">	������Ϣ��:
									</TD>
									<TD height="32" width="20%">
										<INPUT type="Text" disabled class="box" name="tsInterestStart" value="<%=tsInterestStart!=null?DataFormat.getDateString(tsInterestStart):DataFormat.getDateString(tsExecute)%>" onFocus="nextfield='tsExecute';">&nbsp;
									</TD>
									<TD height="32" width="10%">&nbsp;	
									</TD>
									<TD height="32" width="25%">ִ����:
									</TD>
									<TD height="32" width="20%">
										<INPUT type="Text" disabled class="box" name="tsExecute" value="<%=tsExecute!=null?DataFormat.getDateString(tsExecute):""%>" onFocus="nextfield='lAbstractCtrl';">&nbsp;
									</TD>
									<TD height="32" width="20%">&nbsp;	
									</TD>
								</TR>
								<TR vAlign="middle">
									<%
//ժҪ�Ŵ�
		String strCtrlNameAbstract = "lAbstract";
		String strTitleAbstract = "ժҪ";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = strAbstract;//NameRef.getAbstractDetailDescByID(lAbstractID);
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		String[] strNextControlsAbstract = {"submitfunction"};
		
	SETTMagnifier.createAbstractCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameAbstract,
		strTitleAbstract,
		lAbstractIDAbstract,
		strAbstractDescAbstract,
		strFirstTDAbstract,
		strSecondTDAbstract,
		strNextControlsAbstract);
%>
<script language="javascript">
document.all.lAbstractCtrl.disabled=true;
</script>
									<TD height="30" width="10%">&nbsp;	
									</TD>
									<TD height="30" width="25%">
									</TD>
									<TD height="30" width="10%">&nbsp;	
									</TD>
									<TD height="30" width="20%">&nbsp;	
									</TD>
									</tr>
    <TR> 
      <TD height="20" vAlign="top" colspan=6 width="100%"> <TABLE align="center" height="20" width="97%">
          <TBODY>
        <TR vAlign=middle>
          <TD colSpan=6 height=20>
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
			if (lRecordStatusID == SETTConstant.TransactionStatus.SAVE)
			{
			%>
			<INPUT class=button name=Submit3 onclick="doCheck(frmV051_4);" type=button value=" �� �� "> 
			<%
			}
			else if (lRecordStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV051_4);" type=button value=" ȡ������ "> 			
			<%
			}
			%>
			<INPUT class=button name=Submit322 onclick="doBack(frmV051_4);" type=button value=" �� �� "> 
<%
	}
%>
            </DIV></TD></TR></TABLE></TD></TR>
            
                        	  <TR>
    <TD colSpan=6 height=20 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TR vAlign=middle>
          <TD height=25 width="8%">���˱�ע:</TD>
          <TD height=25 vAlign=top width="19%"><INPUT class=box 
            name="strCheckAbstract" value="<%=strCheckAbstract%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">¼����:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(lInputUserID)%></TD>
          <TD height=25 width="8%">¼������:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(tsInput)%></TD>
          <TD height=25 width="6%">������:</TD>
          <TD height=25 width="7%"><%=lStatusID==SETTConstant.TransactionStatus.CHECK?NameRef.getUserNameByID(lCheckUserID):""%></TD>
          <TD height=25 width="8%">��������:</TD>
          <TD height=25 width="9%"><%=(lCheckUserID > 0 && lStatusID==SETTConstant.TransactionStatus.CHECK ? DataFormat.formatDate(tsExecute) : "&nbsp;")%></TD>
          <TD height=25 width="5%">״̬:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR>
            
			</table>
		</td>
	</tr>
			</TBODY>
		
	</TABLE></form>
<script language="JavaScript">firstFocus(document.frmV051_4.strCheckAbstract);
//setSubmitFunction("<%if (lRecordStatusID==SETTConstant.TransactionStatus.SAVE)out.print("doCheck"); else if (lRecordStatusID==SETTConstant.TransactionStatus.CHECK) out.print("doCancelCheck");%>(frmV051_4)");
setFormName("frmV051_4");
</script>
<script language="JavaScript">
function doBack(form)
{
	if (confirm("�Ƿ񷵻أ�"))
	{
		<%
		if (lRecordStatusID == SETTConstant.TransactionStatus.SAVE)
		{
		%>
		form.strActionResult.value="<%=Constant.ActionResult.SUCCESS%>";
		form.action = "../view/v051_3.jsp";
		<%
		}
		else
		{
		%>
		form.lStatusID.value="<%=SETTConstant.TransactionStatus.CHECK%>";
		form.action = "../control/c052.jsp";
		form.strSuccessPageURL.value = '../view/v055.jsp';
		form.strFailPageURL.value = '../view/v051_4.jsp';
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
	strTemp=(String)request.getAttribute("lReturn");
	if ( (strTemp!=null)&&(strTemp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTemp).longValue();
	}
%>	
	if (confirm("�Ƿ��ӡ?")){
		window.open( "../accountinfo/a927-v.jsp?lID="+<%=lID%>+"&strTransNo=<%=strTransNo%>&lTransactionTypeID=<%=SETTConstant.TransactionType.MULTILOANRECEIVE%>&strSuccessPageURL='../../tran/loan/view/v051_4.jsp'&strFailPageURL='../../tran/loan/view/v051_4.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v051_3.jsp';
	form.strFailPageURL.value = '../view/v051_4.jsp';
	
	if (confirm("�Ƿ񸴺ˣ�"))
	{
		form.action = "../control/c054.jsp";
		showSending();
		form.submit();
	}
}
function doCancelCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CANCELCHECK%>';	
	form.strSuccessPageURL.value = '../view/v055.jsp';
	form.strFailPageURL.value = '../view/v051_4.jsp';
	
	if (!validateFields(form))
	{
		return false;
	}
	if (confirm("�Ƿ�ȡ�����ˣ�"))
	{
		form.action = "../control/c054.jsp";
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
OBHtml.showOBHomeEnd(out);		
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}

%>

<%@ include file="/common/SignValidate.inc" %>