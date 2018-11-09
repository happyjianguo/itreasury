<%--
 ҳ������ ��v052_6.jsp
 ҳ�湦�� : ��ʴ����ջ�-ί�д����ջ�-����/ȡ������ҳ��
 ��    �� ��Barry
 ��    �� ��2003��12��5��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
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
/**
 * ���幫������
 */
long lOfficeID 									= sessionMng.m_lOfficeID;
long lCurrencyID 								= sessionMng.m_lCurrencyID;
String strFormName 								= "frmV052_6";

long lInputUserID								= -1;									//¼����
long lCheckUserID								= sessionMng.m_lUserID;					//������
Timestamp tsExecute  							= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
long lTransactionTypeID							= SETTConstant.TransactionType.MULTILOANRECEIVE;													//��������

String strTransNo								= "";									//���׺�
/**
 * ҳ�渨������
 */
String strAction 								= null;
String strActionResult 							= Constant.ActionResult.FAIL;

/**
 * ҳ�������
 */
long lID										= -1;									//ID
long lSubAccountID 								= -1;									//���˻�ID
long lConsignAccountID 							= -1;									//ί�д���˺ţ�ί��ר��
long lConsignDepositAccountID 					= -1;									//ί�зŻ��ڴ���˺ţ�ί��ר��
long lPayInterestAccountID 						= -1;									//����Ϣ�˺�
long lInterestBankID 							= -1;									//����Ϣ����ID
long lReceiveInterestAccountID 					= -1;									//��Ϣ�����˻�ID��ί��ר��
long lCommissionAccountID 						= -1;									//�������ջ��˺ţ�ί��ר��
long lCommissionBankID 							= -1;									//�������ջ����У�ί��ר��
long lStatusID									= -1;									//״̬
long lRecordStatusID							= -1;									//��¼״̬
String strCheckAbstract							= "";									//����ժҪ
Timestamp tsInput								= null;									//¼��ʱ��

/**
 * ���Ӳ���,�͸��˵Ĳ���
 */
long lDesc										= -1;									//����ʽ
long lOrderByCode								= -1;									//�����ֶ�
String strTempTransNO							= "";									//��ʱ�˺�
Timestamp tsModify								= null;									//�޸�ʱ��

/**
 * ��ϢӦ��֧��ֵ
 */
double dInterest 								= 0.0;    
double dInterestTax 							= 0.0;
double dCompoundInterest 						= 0.0;
double dOverDueInterest 						= 0.0;
double dCommission 								= 0.0;
 
/**
 * ʵ��֧����Ϣ
 */
double dRealInterest 							= 0.0;
double dRealInterestTax 						= 0.0;
double dRealCompoundInterest 					= 0.0;
double dRealOverDueInterest 					= 0.0;
double dRealCommission 							= 0.0;

/**
 * ˰��
 */

double dInterestTaxRate 						= 0.0;

/**
 * �Ƿ��⻹��Ϣ
 */
long lIsRemitInterest 							= -1;
long lIsRemitCompoundInterest 					= -1;
long lIsRemitOverDueInterest 					= -1;
long lIsRemitCommission 						= -1;

/**
 * �⻹ԭ��
 */
String strAdjustInterestReason 					= "";

/**
 * ������Ϣ����취
 */
long lCapitalAndInterstDealway 			= -1;

/**
 * �ϴν�Ϣ��,�����˻�������
 */
Timestamp tsLatestInterestClear					= null;										//�ϴν�Ϣ��

/**
 * Ϊ��ӡ��ӵ���Ϣ��Ϣ
 */
	   
Timestamp tsCompoundInterestStart 				= null;										//������Ϣ��
double dCompoundAmount 							= 0.0;										//��������
double dCompoundRate 							= 0.0;										//��������

Timestamp tsOverDueStart 						= null;										//���ڷ�Ϣ��Ϣ��
double dOverDueAmount 							= 0.0;										//���ڷ�Ϣ����
double dOverDueRate 							= 0.0;										//���ڷ�Ϣ����

/**
 * ҳ��һ����
 */

long lMultiLoanType								= -1;										//��ʴ����ջ�����
String strDeclarationNo 						= "";										//������
long lClientID									= -1;										//�ͻ�ID
long lLoanAccountID 							= -1;										//�����˺�
long lLoanContractID 							= -1;										//��ͬ��
long lLoanNoteID 								= -1;										//�ſ�֪ͨ����
Timestamp tsDateStart 							= null;										//�ſ�֪ͨ����ʼ����
double dBalance 								= 0.0;										//���˻����
long lFreeFormID 								= -1;										//�⻹֪ͨ��
long lPreFormID 								= -1;										//��ǰ����֪ͨ��
double dAmount 									= 0.0;										//���
Timestamp tsInterestStart 						= tsExecute;								//��Ϣ��
long lAbstractID 								= -1;										//ժҪID
String strAbstract 								= "";										//ժҪ

//-----------------------ҳ��һ����----------------------

 
String strTemp = null;
/**
 * ��Request�л�ó�ʼ��������Ϣ
 */

strTemp = (String)request.getAttribute("strActionResult");
if (strTemp != null && strTemp.trim().length()>0)
{
	  strActionResult = strTemp.trim();
}
strTemp = (String)request.getAttribute("strAction");
if (strTemp != null && strTemp.trim().length()>0)
{
	  strAction = strTemp.trim();
}

strTemp = (String)request.getAttribute("lTransactionTypeID");
if (strTemp !=null && strTemp.trim().length()>0){
	lTransactionTypeID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("strTransNo");
if (strTemp !=null && strTemp.trim().length()>0){
	strTransNo = strTemp.trim();
}

/**
 * ��ȡҳ��һ��ҵ������
 */
strTemp = (String)request.getAttribute("lFreeFormID");
if (strTemp != null && strTemp.trim().length() > 0)
{
	lFreeFormID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lMultiLoanType");
if (strTemp != null && strTemp.trim().length() > 0)											//��ʴ����ջ�
{
	lMultiLoanType = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("strDeclarationNo");
if (strTemp != null && strTemp.trim().length() > 0)											//������
{
	strDeclarationNo = strTemp.trim();
}

strTemp = (String)request.getAttribute("lClientID");
if (strTemp != null && strTemp.trim().length() > 0)											//�ͻ�ID
{
	lClientID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//�����˺�
{
	lLoanAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanContractID");
if (strTemp != null && strTemp.trim().length() > 0)											//��ͬ��
{
	lLoanContractID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanNoteID");
if (strTemp != null && strTemp.trim().length() > 0)											//�ſ�֪ͨ��
{
	lLoanNoteID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("tsDateStart");
if (strTemp != null && strTemp.trim().length() > 0)											//��ʼʱ��
{
 	tsDateStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dBalance");
if (strTemp != null && strTemp.trim().length() > 0)											//���˻����
{
 	dBalance = DataFormat.parseNumber(strTemp.trim());
}

strTemp = (String)request.getAttribute("lFreeFormID");
if (strTemp != null && strTemp.trim().length() > 0)											//�⻹֪ͨ��
{
 	lFreeFormID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lPreFormID");
if (strTemp != null && strTemp.trim().length() > 0)											//��ǰ����֪ͨ��
{
 	lPreFormID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("dAmount");											//���
if (strTemp != null && strTemp.trim().length() > 0)
{
	dAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("tsInterestStart");
if (strTemp != null && strTemp.trim().length() > 0)											//��Ϣ��
{
	tsInterestStart = DataFormat.getDateTime(strTemp);
}

strTemp = (String)request.getAttribute("lAbstractID");
if (strTemp != null && strTemp.trim().length() > 0)											//ժҪID
{
	lAbstractID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lAbstractCtrl");
if (strTemp != null && strTemp.trim().length() > 0)											//ժҪ�ı�
{
	strAbstract = strTemp;
}
//��ȡҳ�����ҵ������

strTemp = (String)request.getAttribute("lConsignAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//ί�д���˺�
{
	lConsignAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lID");
if (strTemp != null && strTemp.trim().length() > 0)											//ID
{
	lID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lSubAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//���˻�ID
{
	lSubAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lConsignDepositAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//ί�л��ڴ���˺�
{
	lConsignDepositAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lPayInterestAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//��Ϣ�˺�
{
	lPayInterestAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lInterestBankID");
if (strTemp != null && strTemp.trim().length() > 0)											//����Ϣ����ID
{
	lInterestBankID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//��Ϣ�����˻�
{
	lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lCommissionAccountID");								//���������˻�ID
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCommissionAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lCommissionBankID");								//������������ID
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCommissionBankID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lStatusID");										//״̬
if (strTemp != null && strTemp.trim().length() > 0)
{
	lStatusID = Long.valueOf(strTemp).longValue();
}

lRecordStatusID = lStatusID;																//���û�иı��¼״̬�Ϳ���ļ�¼״̬���.

strTemp = (String)request.getAttribute("lRecordStatusID");									//��¼״̬
if (strTemp != null && strTemp.trim().length() > 0)
{
	lRecordStatusID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("strCheckAbstract");									//��¼״̬
if (strTemp != null && strTemp.trim().length() > 0)
{
	strCheckAbstract = strTemp.trim();
}

strTemp = (String)request.getAttribute("tsInput");											//¼��ʱ��
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsInput = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lDesc");											//����ʽ
if (strTemp != null && strTemp.trim().length() > 0)
{
	lDesc = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lOrderByCode");										//�����ֶ�
if (strTemp != null && strTemp.trim().length() > 0)
{
	lOrderByCode = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("strTempTransNO");									//��ʱ���׺�
if (strTemp != null && strTemp.trim().length() > 0)
{
	strTempTransNO = strTemp.trim();
}
strTemp = (String)request.getAttribute("tsModify");											//�޸�ʱ��
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsModify = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("tsExecute");										//ִ����
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsExecute = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lInputUserID");										//¼����
if (strTemp != null && strTemp.trim().length() > 0)
{
	lInputUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lCheckUserID");										//������
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCheckUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("dInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ����Ϣ
{
	dInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestTax");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ����Ϣ˰
{
	dInterestTax = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ������
{
	dCompoundInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ�����ڷ�Ϣ
{
	dOverDueInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ��������
{
	dCommission = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ����Ϣ
{
	dRealInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestTax");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��֧����Ϣ˰��
{
	dRealInterestTax = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ������
{
	dRealCompoundInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ�����ڷ�Ϣ
{
	dRealOverDueInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��֧��������
{
	dRealCommission = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestTaxRate");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��֧����Ϣ˰�� <<<
{
	dInterestTaxRate = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("lIsRemitInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//�Ƿ��⻹��Ϣ
{
	lIsRemitInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//�Ƿ��⻹����
{
	lIsRemitCompoundInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//�Ƿ��⻹���ڷ�Ϣ
{
	lIsRemitOverDueInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//�Ƿ��⻹������
{
	lIsRemitCommission = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("strAdjustInterestReason");
if (strTemp != null && strTemp.trim().length() > 0)											//�滹ԭ��
{
	strAdjustInterestReason = strTemp;
}
		
strTemp = (String)request.getAttribute("lCapitalAndInterstDealway");
if (strTemp != null && strTemp.trim().length() > 0)											//����/��Ϣ����ʽ
{
	lCapitalAndInterstDealway = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsLatestInterestClear");
if (strTemp != null && strTemp.trim().length() > 0)											//�ϴν�Ϣ��
{
	tsLatestInterestClear = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("tsCompoundInterestStart");
if (strTemp != null && strTemp.trim().length() > 0)											//������Ϣ��
{
	tsCompoundInterestStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dCompoundAmount");
if (strTemp != null && strTemp.trim().length() > 0)											//��������
{
	dCompoundAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCompoundRate");
if (strTemp != null && strTemp.trim().length() > 0)											//��������
{
	dCompoundRate = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("tsOverDueStart");
if (strTemp != null && strTemp.trim().length() > 0)											//���ڷ�Ϣ��Ϣ��
{
	tsOverDueStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dOverDueAmount");
if (strTemp != null && strTemp.trim().length() > 0)											//���ڷ�Ϣ����
{
	dOverDueAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dOverDueRate");
if (strTemp != null && strTemp.trim().length() > 0)											//���ڷ�Ϣ����
{
	dOverDueRate = DataFormat.parseNumber(strTemp);
}
	
%>
<form name="frmV052_6" method="post">
	<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="strAction"  type="hidden">
	<input name="strActionResult"  type="hidden" value="<%=strActionResult%>">
	<input name="strSuccessPageURL"  type="hidden" value="">
	<input name="strFailPageURL"  type="hidden" value="">
	<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">

<!--����,ȡ����Ҫ�Ĳ���-->
	<input name="lID" type="Hidden" value="<%=lID%>">
	<input name="strTempTransNO" type="Hidden" value="<%=strTempTransNO%>">
	<input name="tsModify" type="Hidden" value="<%=tsModify%>">
	<input name="lCheckUserID" type="Hidden" value="<%=lCheckUserID%>">
	<input name="tsExecute" type="Hidden" value="<%=tsExecute%>">
<!--����,ȡ����Ҫ�Ĳ���-->
<!--���Ӳ���-->
	<input name="lMultiLoanType" type="hidden" value="<%=lMultiLoanType%>">
	<input name="lStatusID" type="Hidden" value="<%=lRecordStatusID%>">
	<input name="lDesc" type="Hidden" value="<%=lDesc%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
<!--���Ӳ���-->
	<input name="tsLatestInterestClear" type="hidden" value="<%=DataFormat.getDateString(tsLatestInterestClear)%>"> <!--�ϴν�Ϣ��-->
	<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<!--Ϊ��ͬʱ�������˸��ĵ�ǰ��¼״̬��������-->
<input type="hidden" name="lRecordStatusID" value="<%=lRecordStatusID%>">
<!--Ϊ��ͬʱ�������˸��ĵ�ǰ��¼״̬��������-->
<TABLE border=0 class=top height=20 width="99%">
  <TBODY>  
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>ҵ�񸴺� ���� ��ʴ����ջ�</B></TD>
  </TR>
  <TR>
				<tr>
					<td align='center'>
						<table width="97%">
							<tr>
								<td width='80'>�ո�����:</td>
								<td align='left'>
									<%SETTConstant.MultiLoanType.showList(out,"lMultiLoanType",0,3,false,false,"onchange='changeMultiLoanType(frmV052_6)'disabled onchange='changeMultiLoanType(frmV052_6)' onfocus=nextfield='strDeclarationNo'",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;
								</td>
								<td align="right">
									���б�����:&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<input type="text" class="box" name="strDeclarationNo" value="<%=strDeclarationNo%>" disabled onfocus=nextfield='lClientIDCtrl'>
								</td>
							</tr>
						</table>
					</td>
				</tr>
  	<TR>
    <TD height=116 vAlign=bottom width="100%">
      <TABLE align=center border=1 borderColor=#999999 height=65 width="97%">
        <TBODY> 
        <TR borderColor=#E8E8E8> 
          <TD height=18 vAlign=middle width="17%" nowrap><U>ί�д�����ϸ����</U> </TD>
          <TD height=18 vAlign=top width="34%" nowrap>&nbsp;</TD>
          <TD height=18 width="17%" nowrap>&nbsp;</TD>
          <TD height=18 width="32%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8>
<%
//����ͻ���ŷŴ�
		String strCtrlNameC = "lClientID";
		String strTitleC = "ί�д���ͻ����";
		long lClientIDC = lClientID;
		String strClientNoC = NameRef.getClientCodeByID(lClientID);
		String strFirstTDC = "";
		String SecondTDC = "";
		String[] sNextControlsClientC = {"lLoanAccountIDCtrlCtrl3"};
		String strRtnClientNameCtrlC = "strLoanClientName";
		
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
        	<TD height=20 width="17%" nowrap>ί�д���ͻ�����: </TD>
          <TD height=20 width="32%" nowrap> 
                <textarea name="strLoanClientName" class="box" disabled  rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
          </TD>
        </TR>
        <TR borderColor=#E8E8E8> 
	<%
//ί�д����˺ŷŴ�   	
		String strCtrlNameTrustLoanAcct = "lLoanAccountID";
		String strTitleTrustLoanAcct = "ί�д����˺�";
		long lClientIDTrustLoanAcct = lClientID;
		long lAccountIDTrustLoanAcct = lLoanAccountID;
		String strAccountNoTrustLoanAcct = NameRef.getAccountNoByID(lLoanAccountID);
		long lAccountGroupTypeIDTrustLoanAcct = SETTConstant.AccountGroupType.CONSIGN;
		long lAccountTypeIDTrustLoanAcct = -1;
		long lReceiveOrPayTrustLoanAcct = -1;
		String strClientCtrlTrustLoanAcct = "lClientID";
		String strFirstTDTrustLoanAcct = "";
		String strSecondTDTrustLoanAcct = "";
		String[] strNextControlsTrustLoanAcct = {"lLoanContractIDCtrl"};
		String strRtnClientIDCtrlTrustLoanAcct = "lClientID";
		String strRtnClientNoCtrlTrustLoanAcct = "lClientIDCtrl";
		String strRtnClientNameCtrlTrustLoanAcct = "strLoanClientName";
		
	SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameTrustLoanAcct,
		strTitleTrustLoanAcct,
		lClientIDTrustLoanAcct,
		lAccountIDTrustLoanAcct,
		strAccountNoTrustLoanAcct,
		lAccountGroupTypeIDTrustLoanAcct,
		lAccountTypeIDTrustLoanAcct,
		lReceiveOrPayTrustLoanAcct,
		strClientCtrlTrustLoanAcct,
		strFirstTDTrustLoanAcct,
		strSecondTDTrustLoanAcct,
		strNextControlsTrustLoanAcct,
		strRtnClientIDCtrlTrustLoanAcct,
		strRtnClientNoCtrlTrustLoanAcct,
		strRtnClientNameCtrlTrustLoanAcct);%>
<script language="javascript">
document.all.lLoanAccountIDCtrlCtrl1.disabled=true;
document.all.lLoanAccountIDCtrlCtrl2.disabled=true;
document.all.lLoanAccountIDCtrlCtrl3.disabled=true;
</script>
      <TD height=20 width="17%" nowrap>&nbsp; </TD>
          <TD height=20 width="32%" nowrap>&nbsp; </TD>
        </TR>
        <TR borderColor=#E8E8E8> 			
<%
//��ͬ�ŷŴ�
	String strCtrlNameContract = "lLoanContractID";
	String strTitleContract = "��ͬ��";
	long lClientIDContract = NameRef.getClientIDByAccountID(lLoanAccountID);
	long lLoanContractIDContract = lLoanContractID;
	String strContractNo = NameRef.getContractNoByID(lLoanContractID);
	long lContractTransactionType = SETTConstant.TransactionType.CONSIGNLOANRECEIVE;
	long lContractMagnifierType = 2;
	String strClientCtrl = "lLoanAccountIDClientID";
	String strFirstTDContract = "";
	String strSecondTDContract = "";
	String[] strNextControlsContract = { "lLoanNoteIDCtrl" };

	SETTMagnifier.createContractCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameContract,
		strTitleContract,
		lClientIDContract,
		lLoanContractIDContract,
		strContractNo,
		lContractTransactionType,
		lContractMagnifierType,
		strClientCtrl,
		strFirstTDContract,
		strSecondTDContract,
		strNextControlsContract);
%>
<script language="javascript">
document.all.lLoanContractIDCtrl.disabled=true;
function getContractTypeSQL()
{
	return "select distinct nTypeID,'FromConstant_1_nTypeID' as sDesc from  loaninfo ";
}
</script>

          <TD height=20 width="17%" nowrap>&nbsp;</TD>
          <TD height=20 width="32%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8> 
	<%					
//�ſ�֪ͨ���ݺŷŴ�
	String strCtrlNamePayForm = "lLoanNoteID";
	String strTitlePayForm = "�ſ�֪ͨ���ݺ�";
	long lLoanContractIDPayForm = lLoanContractID;
	long lPayFormID = lLoanNoteID;
	String strPayFormNo = NameRef.getPayFormNoByID(lLoanNoteID);
	long lPayFormTypeID = 2;									//�ſ�֪ͨ������(��ѯ����:1,���У�2��ί��)
	long lPayFormStatusIDs = 3;										//�ſ�֪ͨ��״̬(�ڲ�״̬��0,ȫ�� 1,����ҵ���� 2,���Ÿ��� 3,�ջ�ҵ���� 4,�ջظ���
	String strContractCtrlPayForm = "lLoanContractID";
	String strFirstTDPayForm = "";
	String strSecondTDPayForm = "";
	String[] strNextControlsPayForm = { "dAmount" };
	String strRtnStartDateCtrl = "tsDateStart";
	String strBalance = "hidBalance";			//���

	SETTMagnifier.createPayFormCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNamePayForm,
		strTitlePayForm,
		lLoanContractIDPayForm,
		lPayFormID,
		strPayFormNo,
		lPayFormTypeID,
		lPayFormStatusIDs,
		strContractCtrlPayForm,
		strFirstTDPayForm,
		strSecondTDPayForm,
		strNextControlsPayForm,
		strRtnStartDateCtrl,
		"",
		"",
		strBalance);	
%>
<script language="javascript">
document.all.lLoanNoteIDCtrl.disabled=true;
</script>
									<TD height="20" width="15%">&nbsp;
									</TD>
									<TD height="20" width="35%">&nbsp;
										
									</TD>
        </TR>
        </TBODY> 
      </TABLE>
    </TD></TR>
 <!--page two-->   
        <TR>
      <TD height=20 vAlign=bottom><TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD height=20 vAlign=middle nowrap width="15%"><U>ί�з���ϸ����</U> </TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="35%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> 
			  <table>
                  <tr> 
				  <input type="Hidden" name="lConsignAccountID" value="<%=lConsignAccountID%>">
                    <script language=javascript>
					showDisableAccountCtrl("lConsignAccountID","<%=NameRef.getAccountNoByID(lConsignAccountID)%>","ί�д���˺�","width=20%","")
					</script>
                    <TD height="20" width="135">&nbsp;</TD>
                    <TD height=20 vAlign=middle>ί�д��ͻ����ƣ�</TD>
                    <TD height=20 vAlign=top > 
					<textarea name="lConsignClientName"  class="box" disabled   rows=2 cols=30 disabled><%=NameRef.getClientNameByAccountID(lConsignAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> 
			  <table>
                  <tr>
				   <%
		//ί�з����ڴ���˺�		
		String strCtrlNameAcct = "lConsignDepositAccountID";
		String strTitleAcct = "ί�з����ڴ���˺�";
		long lClientIDAcct = NameRef.getClientIDByAccountID(lConsignDepositAccountID);
		long lAccountIDAcct = lConsignDepositAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lConsignDepositAccountID);;
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;//�ո�����
		String strClientCtrlAcct = "";//���޸�
		String strFirstTDAcct = "";
		String strSecondTDAcct = "";
		String[] strNextControlsAcct = {"Rb1[0]"};
		String strRtnClientIDCtrlAcct = "";
		String strRtnClientNoCtrlAcct = "";
		String strRtnClientNameCtrlAcct = "strConsignDepositClientName";		
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
document.all.lConsignDepositAccountIDCtrlCtrl1.disabled=true;
document.all.lConsignDepositAccountIDCtrlCtrl2.disabled=true;
document.all.lConsignDepositAccountIDCtrlCtrl3.disabled=true;
document.all.lConsignDepositAccountIDCtrlCtrl4.disabled=true;
</script>
					 <TD width="100">&nbsp;</TD>
                    <TD height=20 vAlign=middle width="110">���ڿͻ����ƣ�</TD>
                    <TD height=20 vAlign=top > <textarea name="strConsignDepositClientName"  class="box" disabled bgcolor="#FF00"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(lConsignDepositAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height=20 vAlign=bottom width="100%"> <TABLE align=center border=1 borderColor=#999999 height=40 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD height=20 vAlign=middle nowrap width="15%"><U>��Ϣ������ϸ����</U> </TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="35%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"><input type="Radio" name="Rb1" disabled <%=lPayInterestAccountID>0?"checked":""%> value="1"  onfocus="nextfield='lPayInterestAccountIDCtrlCtrl3';" checked> 
                    </td>
                   
<%
		//��Ϣ�����˺ŷŴ�		
	
	String[] strNextControlsAcctPayInterest = {"lReceiveInterestAccountIDCtrlCtrl3"};//���޸�
SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		"lPayInterestAccountID",
		"��Ϣ�����˺�",
		 NameRef.getClientIDByAccountID(lPayInterestAccountID),
		lPayInterestAccountID,
		NameRef.getAccountNoByID(lPayInterestAccountID),
		SETTConstant.AccountGroupType.CURRENT,
		-1,
		-1,
		"",
		"",
		"",
		strNextControlsAcctPayInterest,
		"",
		"",
		"strPayInterestClientName");
%>
<script language="javascript">
document.all.lPayInterestAccountIDCtrlCtrl1.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl2.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl3.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl4.disabled=true;
</script>
                    <TD width="100">&nbsp;</TD>
                    <TD height=20 vAlign=middle>��Ϣ�����ͻ����ƣ�</TD>
                    <TD height=20 vAlign=top > <textarea name="strPayInterestClientName"  class="box" disabled bgcolor="#FF00"  rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPayInterestAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"><input type="Radio" disabled name="Rb1"<%=lInterestBankID>0?"checked":""%>  value="2" onfocus="nextfield='lInterestBankIDCtrl';" > 
</td>
                    <%
//�����зŴ�
		 String strCtrlNameBranch = "lInterestBankID";
		 String strTitleBranch = "������";
		 long lBranchIDBranch = lInterestBankID;
		 String strBranchNameBranch = NameRef.getBankNameByID(lInterestBankID);
		 long lIsSingleBankBranch = 0;
		 String strAccountCtrlBranch = "";
		 String strFirstTDBranch = "";
		 String strSecondTDBranch = "";
		String[] strNextControlsBranch = {"lReceiveInterestAccountIDCtrlCtrl3"};
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
document.all.lInterestBankIDCtrl.disabled=true;
</script>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>��Ϣ������ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">&nbsp; </td>
                 <%
String[] strNextControlsAcctReceiveInterest = {"Rb3[0]"};
SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		"lReceiveInterestAccountID",
		"��Ϣ�����˺�",
		 NameRef.getClientIDByAccountID(lReceiveInterestAccountID),
		lReceiveInterestAccountID,
		NameRef.getAccountNoByID(lReceiveInterestAccountID),
		SETTConstant.AccountGroupType.CURRENT,
		-1,
		-1,
		"",
		"",
		"",
		strNextControlsAcctReceiveInterest,
		"",
		"",
		"strReceiveInterestClientName");
					%>
<script language="javascript">
document.all.lReceiveInterestAccountIDCtrlCtrl1.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl2.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl3.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl4.disabled=true;
</script>
                    <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">��Ϣ����ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strReceiveInterestClientName" class="box" disabled bgcolor="#FF00" rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lReceiveInterestAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE> 
        
      </TD>
    </TR>
    <TR> 
      <TD height="20" vAlign="top"><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>�������ջ���ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> <input type="Radio" disabled name="Rb3" <%=lCommissionAccountID>0?"checked":""%> value="1" onfocus="nextfield='lCommissionAccountIDCtrlCtrl3';"  checked> 
                    </td>
        <%
	
		//�����Ѹ����˺ŷŴ�		
		String strCtrlNamePC = "lCommissionAccountID";
		String strTitlePC = "�����Ѹ����˺�";
		long lClientIDPC = NameRef.getClientIDByAccountID(lCommissionAccountID);
		long lAccountIDPC = lCommissionAccountID;
		String strAccountNoPC = NameRef.getAccountNoByID(lCommissionAccountID);;
		long lAccountGroupTypeIDPC = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDPC = -1;
		long lReceiveOrPayPC = -1;//�ո�����
		String strClientCtrlPC = "";//
		String strFirstTDPC = "";
		String strSecondTDPC = "";
		String[] strNextControlsPC = {"dRealInterest"};
		String strRtnClientIDCtrlPC = "";
		String strRtnClientNoCtrlPC = "";
		String strRtnClientNameCtrlPC = "strPayCommissionClientName";		
SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNamePC,
		strTitlePC,
		lClientIDPC,
		lAccountIDPC,
		strAccountNoPC,
		lAccountGroupTypeIDPC,
		lAccountTypeIDPC,
		lReceiveOrPayPC,
		strClientCtrlPC,
		strFirstTDPC,
		strSecondTDPC,
		strNextControlsPC,
		strRtnClientIDCtrlPC,
		strRtnClientNoCtrlPC,
		strRtnClientNameCtrlPC);
%>
<script language="javascript">
document.all.lCommissionAccountIDCtrlCtrl1.disabled=true;
document.all.lCommissionAccountIDCtrlCtrl2.disabled=true;
document.all.lCommissionAccountIDCtrlCtrl3.disabled=true;
document.all.lCommissionAccountIDCtrlCtrl4.disabled=true;
</script>
                 <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">�����Ѹ����ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strPayCommissionClientName" class="box" disabled bgcolor="#FF00" rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lCommissionAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> <input type="Radio" disabled name="Rb3" <%=lCommissionBankID>0?"checked":""%> value="2" onfocus="nextfield='lCommissionBankIDCtrl';"> 
</td>
                    <%
//�����зŴ�
        String strCtrlNameBranchPC = "lCommissionBankID";
		String strTitleBranchPC = "������";
		long lBranchIDBranchPC = lCommissionBankID;
		String strBranchNameBranchPC = NameRef.getBankNameByID(lCommissionBankID);
		long lIsSingleBankBranchPC = 0;
		String strAccountCtrlBranchPC = "";
		String strFirstTDBranchPC = "";
		String strSecondTDBranchPC = "";
		String[] strNextControlsBranchPC = {"dRealInterest"};
		String strRtnBankAccountNoCtrlBranchPC = "";


	SETTMagnifier.createBranchCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameBranchPC,
		strTitleBranchPC,
		lBranchIDBranchPC,
		strBranchNameBranchPC,
		lIsSingleBankBranchPC,
		strAccountCtrlBranchPC,
		strFirstTDBranchPC,
		strSecondTDBranchPC,
		strNextControlsBranchPC,
		strRtnBankAccountNoCtrlBranchPC);	
%>
<script language="javascript">
document.all.lCommissionBankIDCtrl.disabled=true;
</script>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height="20" vAlign="top"><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <td width="80%"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD  height="21"  valign="top"width="250">Ӧ��֧�� </TD>
                    <TD height="21" vAlign="top" width="250">ʵ��֧��</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
                  <tr> 
                    <td>������Ϣ��</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                      createAmountCtrl("frmV052_6","dInterestCtrl","<%=DataFormat.formatAmountUseZero(dInterest)%>","");
					  document.frmV052_6.dInterestCtrl.disabled=true;
					</script> </TD>
					<%Log.print("Ӧ����Ϣ:"+dInterest);%>
					<input type="hidden" name="dInterest" value="<%=dInterest%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
					 	//createAmountCtrl("frmV052_6","dRealInterest","<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest",null,'<%=lCurrencyID%>',"onChange=doChangeBoth();");
					 	//document.all.dRealInterest.disabled=true;
					 	//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV052_6","dRealInterest",false,"<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeBoth()");
					 </script> </TD>
                    <TD height="20" vAlign="top"><input type="checkbox" class="box" disabled name="lIsRemitInterest" value="1" > 
                      <script language="JavaScript">
					if('<%=lIsRemitInterest%>' == "1")
					{
					document.frmV052_6.lIsRemitInterest.checked = true;
					}
					</script>
                      �⻹ʣ����Ϣ</TD>
                  </tr>
                 
                 
                  <tr> 
                    <td>������</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                    createAmountCtrl("frmV052_6","dCompoundInterestCtrl","<%=DataFormat.formatAmountUseZero(dCompoundInterest)%>","");
					document.frmV052_6.dCompoundInterestCtrl.disabled=true;
					</script></TD>
					<%Log.print("Ӧ������:"+dInterest);%>
					<input type="hidden" name="dCompoundInterest" value="<%=dCompoundInterest%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
           		 		//createAmountCtrl("frmV052_6","dRealCompoundInterest","<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest",null,'<%=lCurrencyID%>',"onChange=doChangeBoth();");
						//document.frmV052_6.dRealCompoundInterest.disabled=true;
						//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV052_6","dRealCompoundInterest",false,"<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeBoth()");
					  </script> </TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled class="box" name="lIsRemitCompoundInterest" value="1" > 
                      <script language="JavaScript">
					if('<%=lIsRemitCompoundInterest%>' == "1")
					{
					document.frmV052_6.lIsRemitCompoundInterest.checked = true;
					}
					</script>
                      �⻹ʣ�ิ��</TD>
                  </tr>
                  <tr> 
                    <td>���ڷ�Ϣ:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                    createAmountCtrl("frmV052_6","dOverDueInterestCtrl","<%=DataFormat.formatAmountUseZero(dOverDueInterest)%>","");
					document.frmV052_6.dOverDueInterestCtrl.disabled=true;
					</script></TD>
					<input type="hidden" name="dOverDueInterest" value="<%=dOverDueInterest%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                   		//createAmountCtrl("frmV052_6","dRealOverDueInterest","<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","dRealCommission",null,'<%=lCurrencyID%>',"onChange=doChangeBoth();");
                   		//document.frmV052_6.dRealOverDueInterest.disabled=true;
						//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV052_6","dRealOverDueInterest",false,"<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","dRealCommission","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeBoth()");
					 </script> </TD>
                    <TD height="20" vAlign="top"><input type="checkbox"  disabled class="box" name="lIsRemitOverDueInterest" value="1" > 
                      <script language="JavaScript">
					if('<%=lIsRemitOverDueInterest%>' == "1")
					{
					document.frmV052_6.lIsRemitOverDueInterest.checked = true;
					}
					</script>
                      �⻹ʣ�෣Ϣ</TD>
                  </tr>
				   <tr> 
                    <td>��Ϣ˰�ѣ�</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                       createAmountCtrl("frmV052_6","dInterestTaxCtrl","<%=DataFormat.formatAmountUseZero(dInterestTax)%>","");
					   document.frmV052_6.dInterestTaxCtrl.disabled=true;
					</script></TD>
					<input type="hidden" name="dInterestTax" value="<%=dInterestTax%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                    	//createAmountCtrl("frmV052_6","dRealInterestTax","<%=dRealInterestTax%>","dRealCompoundInterest",null,'<%=lCurrencyID%>',"onChange=doChangeSumFee();");
                    	//document.frmV052_6.dRealInterestTax.disabled=true;
                    	//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV052_6","dRealInterestTax",false,"<%=dRealInterestTax%>","dRealCompoundInterest","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeSumFee()");
					</script> </TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>������:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                    createAmountCtrl("frmV052_6","dCommissionCtrl","<%=DataFormat.formatAmountUseZero(dCommission)%>","");
					document.frmV052_6.dCommissionCtrl.disabled=true;
					</script></TD>
					<input type="hidden" name="dCommission" value="<%=dCommission%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                      	//createAmountCtrl("frmV052_6","dRealCommission","<%=DataFormat.formatAmountUseZero(dRealCommission)%>","strAdjustInterestReason",null,'<%=lCurrencyID%>',"onChange=doChangeSumFee();");
                      	//document.all.dRealCommission.disabled=true;
                      	//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV052_6","dRealCommission",false,"<%=DataFormat.formatAmountUseZero(dRealCommission)%>","strAdjustInterestReason","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeSumFee()");
					  </script></TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled class="box" name="lIsRemitCommission" value="1" > 
                      <script language="JavaScript">
					if('<%=lIsRemitCommission%>' == "1")
					{
						document.frmV052_6.lIsRemitCommission.checked = true;
					}
					</script>
                      �⻹ʣ��������</TD>
                  </tr>
                  <tr> 
                    <td>��Ϣ���úϼƣ�</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                     createAmountCtrl("frmV052_6","dSumFee","<%=UtilOperation.Arith.round((dInterest+dCompoundInterest+dOverDueInterest+dCommission),2)%>","");
					 document.frmV052_6.dSumFee.disabled=true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                      createAmountCtrl("frmV052_6","dRealSumFee","<%=UtilOperation.Arith.round((dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealCommission),2)%>","");
					  document.frmV052_6.dRealSumFee.disabled=true;
					 </script></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>�⻹ԭ��</td>
                    <TD  height="20"  valign="top"><textarea name="strAdjustInterestReason" disabled class="box"  bgcolor="#FF00" rows="2" cols="30" onfocus="nextfield='lCapitalAndInterstDealway[0]'"><%=strAdjustInterestReason%></textarea></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD height="20" vAlign="top"><TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>����/��Ϣ����취</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table width="100%">
                  <tr> 
                    <td width="200">&nbsp; </td>
                    <TD width="250"><input type="radio" name="lCapitalAndInterstDealway" disabled value="1" onfocus="nextfield='lCapitalAndInterstDealway[1]'">
                      ���ܴ��� </TD>
                    <TD vAlign="middle">&nbsp;</TD>
                    <TD height="20" vAlign="middle" width="250"><input type="radio" disabled name="lCapitalAndInterstDealway" value="2" onfocus="nextfield='submitfunction'" checked> 
                      <script language="JavaScript">
					if('<%=lCapitalAndInterstDealway%>' == "1")
					{
					document.frmV052_6.lCapitalAndInterstDealway[0].checked = true;
					}
					else if('<%=lCapitalAndInterstDealway%>' == 2)
					{
					document.frmV052_6.lCapitalAndInterstDealway[1].checked = true;
					}	
					</script>
                      �ֱʴ���</TD>
                    <TD height="20" vAlign="top">&nbsp; </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    
 <!--page two-->
	<TR>
					<TD height="119" vAlign="top" width="100%">
						<TABLE align="center" height="8" width="97%">
							<TBODY>
								<TR vAlign="middle">
									<td colspan="6">
										<table width="350">
											<td width="10">
												<input type="Checkbox" name="cbPreFormID" disabled value="1" onFocus="nextfield='lPreFormIDCtrl'">
											</td>
	
<%
//��ǰ����֪ͨ���Ŵ�
	String strCtrlNameAheadPayForm = "lPreFormID";
	String strTitleAheadPayForm = "��ǰ����֪ͨ��";
	long lLoanContractIDAheadPayForm = lLoanContractID;
	long lPreFormIDF = lPreFormID;
	String strAheadPayFormNo = NameRef.getAheadPayFormNoByID(lPreFormID);
	long lAheadPayFormTypeID = 1;
	String strContractCtrlAheadPayForm = "lLoanContractID";
	long lAheadPayFormStatusIDs = 1;//Ӧ�ø�Ϊ1��Ϊҵ����ʱʹ��
	String strFirstTDAheadPayForm = "";
	String strSecondTDAheadPayForm = "";
	String[] strNextControlsAheadPayForm = { "submitfunction" };
	String strRtnAmountCtrl = "dAmount";
	String strRtnClientNoCtrl = "lClientIDCtrl";				//�ͻ���
	String strRtnContractNoCtrl = "lLoanContractIDCtrl";			//��ͬ��
	String strRtnPayFormNoCtrl = "lLoanNoteIDCtrl";			//�ſ�֪ͨ����

	SETTMagnifier.createAheadPayFormCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameAheadPayForm,
		strTitleAheadPayForm,
		lLoanContractIDAheadPayForm,
		lPreFormIDF,
		strAheadPayFormNo,
		lAheadPayFormTypeID,
		lAheadPayFormStatusIDs,
		strContractCtrlAheadPayForm,
		strFirstTDAheadPayForm,
		strSecondTDAheadPayForm,
		strNextControlsAheadPayForm,
		strRtnAmountCtrl,
		strRtnClientNoCtrl,
		strRtnContractNoCtrl,
		strRtnPayFormNoCtrl);
	%>
<script language="javascript">
document.all.lPreFormIDCtrl.disabled=true;
</script>
										</table>
									</TD>
									<td>
									<!--<input type="checkbox" name="cbFreeFormID" disabled value="1" onfocus=nextfield="lFreeFormIDCtrl">
<%/*
		//�⻹֪ͨ���Ŵ�		
	 String  strCtrlNameFF = "lFreeFormID";//�Ŵ����ؼ�����
	 String  strTitleFF = "�⻹֪ͨ��"; //�Ŵ�����
	 long   lFreeFormIDFF = lFreeFormID; //�⻹֪ͨ��ID(��ʶֵ)
	 String  strFreeFormNoFF = NameRef.getFreeFormNoByID(lFreeFormID); //�⻹֪ͨ����(��ʶֵ)
	 long lTypeID	= 2;		//����
	 String  strFirstTDFF = ""; //��һ��TD������
	 String  strSecondTDFF = ""; //�ڶ���TD������
	 String[] strNextControlsFF = {"submitfunction"}; //��һ������������ý���Ŀؼ�
	 String  strRtnFreeAmountCtrlFF = ""; //����ֵ���⻹����Ӧ�Ŀؼ���
	 String  strRtnFreeInterestCtrlFF = ""; //����ֵ���⻹��Ϣ����Ӧ�Ŀؼ���
	 String  strRtnClientIDCtrlFF = ""; //����ֵ������ͻ�ID����Ӧ�Ŀؼ���
	 String  strRtnClientNoCtrlFF = ""; //����ֵ������ͻ���ţ���Ӧ�Ŀؼ���
	 String  strRtnClientNameCtrlFF = ""; //����ֵ������ͻ����ƣ���Ӧ�Ŀؼ���
	 String  strRtnContractIDCtrlFF = ""; //����ֵ����ͬID����Ӧ�Ŀؼ���
	 String  strRtnContractNoCtrlFF = ""; //����ֵ����ͬ���ƣ���Ӧ�Ŀؼ���
	 String  strRtnPayFormIDCtrlFF = ""; //����ֵ���ſ�֪ͨ��ID����Ӧ�Ŀؼ���
	 String  strRtnPayFormNoCtrlFF = ""; //����ֵ���ſ�֪ͨ����ţ���Ӧ�Ŀؼ���
	 String  strRtnPFStartDateCtrlFF = ""; //����ֵ���ſ�֪ͨ����ʼ���ڣ���Ӧ�Ŀؼ���
	 
 SETTMagnifier.createFreeFormCtrl(
		 out,
		 lOfficeID,
		 lCurrencyID,
		 strFormName,
		 strCtrlNameFF,
		 strTitleFF,
		 lFreeFormIDFF,
		 strFreeFormNoFF,
		 lTypeID,
		 strFirstTDFF,
		 strSecondTDFF,
		 strNextControlsFF,
		 strRtnFreeAmountCtrlFF,
		 strRtnFreeInterestCtrlFF,
		 strRtnClientIDCtrlFF,
		 strRtnClientNoCtrlFF,
		 strRtnClientNameCtrlFF,
		 strRtnContractIDCtrlFF,
		 strRtnContractNoCtrlFF,
		 strRtnPayFormIDCtrlFF,
		 strRtnPayFormNoCtrlFF,
		 strRtnPFStartDateCtrlFF);
		
		*/%>
<script language="javascript">
document.all.lFreeFormIDCtrl.disabled=true;
</script> -->
		</td>
								</TR>

  <TR>
    <TD height=110 vAlign=top width="100%" colspan='9'>
      <TABLE align=center height=87 width="97%">
        <TBODY> 
        <TR vAlign=middle> 
          <TD borderColor=#E8E8E8 height=20 width="14%" nowrap>������:</TD>
          <TD borderColor=#E8E8E8 height=20 width="20%" nowrap><%=sessionMng.m_strCurrencySymbol%>  
<script language="javascript">
// �������ؼ�
	createAmountCtrl("frmV052_6","dAmount","<%=DataFormat.formatDisabledAmount(dAmount)%>","tsInterestStart");
	document.all.dAmount.disabled=true;
</script>
          </TD>
          <TD borderColor=#E8E8E8 height=20 width="13%" nowrap>&nbsp;</TD>
          <TD height=20 width="19%" nowrap>&nbsp;</TD>
          <TD height=20 width="6%" nowrap>&nbsp;</TD>
          <TD height=20 width="28%" nowrap>&nbsp;</TD>
        </TR>
        <TR vAlign=middle> 

          <TD height=20 width="14%" nowrap>��Ϣ��: </TD>
          <TD height=20 width="20%" nowrap> 
		  	<table border=0 cellspacing=2 cellpadding=2>
				<tr>
					<td>
						<INPUT type="Text" class="box" disabled name="tsInterestStart" value="<%=DataFormat.getDateString(tsInterestStart)%>" onFocus="nextfield='lAbstractIDCtrl';">
					</td>
					<td>&nbsp;
						
					</td>
				</tr>
			</table>
           </TD>
          <TD height=20 width="13%" nowrap>&nbsp;</TD>
          <TD height=20 width="19%" nowrap>&nbsp;</TD>
          <TD height=20 width="6%" nowrap>&nbsp;</TD>
          <TD height=20 width="28%" nowrap>&nbsp;</TD>
        </TR>
        <TR vAlign=middle> 
						<%
//ժҪ�Ŵ�
		String strCtrlNameAbstract = "lAbstractID";
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
document.all.lAbstractIDCtrl.disabled=true;
</script>
          <TD height=20 width="13%" nowrap>&nbsp;</TD>
          <TD height=20 width="19%" nowrap>&nbsp;</TD>
          <TD height=20 width="6%" nowrap>&nbsp;</TD>
          <TD height=20 width="28%" nowrap>&nbsp;</TD>
        </TR>
	        </TBODY>
      </TABLE>
    </TD>
  </TR>
  
        </TBODY>
      </TABLE>
    </TD>
  </TR>
      <TR> 
      <TD height="20" vAlign="top" width="100%"> <TABLE align="center" height="20" width="97%">
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
			<INPUT class=button name=Submit3 onclick="doCheck(frmV052_6);" type=button value=" �� �� "> 
			<%
			}
			else if (lRecordStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV052_6);" type=button value=" ȡ������ "> 			
			<%
			}
			%>
			<INPUT class=button name=Submit322 onclick="doBack(frmV052_6);" type=button value=" �� �� "> 
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
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(lInputUserID)%></TD>
          <TD height=25 width="8%">¼������:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(tsInput)%></TD>
          <TD height=25 width="6%">������:</TD>
          <TD height=25 width="7%"><%=lStatusID==SETTConstant.TransactionStatus.CHECK ? NameRef.getUserNameByID(lCheckUserID) : ""%></TD>
          <TD height=25 width="8%">��������:</TD>
          <TD height=25 width="9%"><%=(lCheckUserID > 0 && lStatusID==SETTConstant.TransactionStatus.CHECK ? DataFormat.formatDate(tsExecute) : "&nbsp;")%></TD>
          <TD height=25 width="5%">״̬:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR></TBODY>
            
			</table></form>

<script language="JavaScript">
firstFocus(document.frmV052_6.strCheckAbstract);
//setSubmitFunction("<%if (lRecordStatusID==SETTConstant.TransactionStatus.SAVE)out.print("doCheck"); else if (lRecordStatusID==SETTConstant.TransactionStatus.CHECK) out.print("doCancelCheck");%>(frmV052_6)");
setFormName("frmV052_6");
</script>
<script language="JavaScript">
var isSubmited = false;//��ʶ�Ƿ����ύ����

function doBack(form)
{

	if (confirm("�Ƿ񷵻أ�"))
	{
		<%
		if (lRecordStatusID == SETTConstant.TransactionStatus.SAVE)
		{
		%>
		form.strActionResult.value="<%=Constant.ActionResult.SUCCESS%>";
		form.action = "../view/v052_5.jsp";
		<%
		}
		else
		{
		%>
		form.lStatusID.value="<%=SETTConstant.TransactionStatus.CHECK%>";
		form.action = "../control/c052.jsp";
		form.strSuccessPageURL.value = '../view/v055.jsp';
		form.strFailPageURL.value = '../view/v052_6.jsp';
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
		window.open( "../accountinfo/a927-v.jsp?lID="+<%=lID%>+"&strTransNo=<%=strTransNo%>&lTransactionTypeID=<%=SETTConstant.TransactionType.MULTILOANRECEIVE%>&strSuccessPageURL='../../tran/loan/view/v052_6.jsp'&strFailPageURL='../../tran/loan/view/v052_6.jsp'&lReturn=<%=lOBReturn%>");
	}
}

function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v052_5.jsp';
	form.strFailPageURL.value = '../view/v052_6.jsp';
	
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
	form.strFailPageURL.value = '../view/v052_6.jsp';
	
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