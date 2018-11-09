<%--
 ҳ������ ��a902-v.jsp
 ҳ�湦�� : ҵ����-��Ϣ����֧��-����/ȡ������ҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant" %>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
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
%>

<%
//����Ŵ󾵹��ñ��������´���š����ֱ�š���������

long lOfficeID 					= sessionMng.m_lOfficeID;		//���´�ID
long lCurrencyID 				= sessionMng.m_lCurrencyID;		//����
String strFormName 				= "frmV047";					//������
long lCheckUserID		= sessionMng.m_lUserID;						//¼����

Timestamp tsExecute 			= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));	//����ʱ��,��¼��ʱ��
long lID						= -1;							//ID

//���Ӳ��Ҳ���
long lStatusID					= -1;										//������״̬,��¼�ϲ���һ��ѡ��ʲô
long lDesc 						= Constant.PageControl.CODE_ASCORDESC_DESC;	//����ʽ
int nOrderByCode				= -1;										//�����ֶ�


Timestamp tsModify				= null;						//�޸�ʱ��
//-----------------------ҳ��һ����----------------------
long lClientID 					= -1;							//����ͻ�ID
long lLoanAccountID 			= -1;							//�����˺�
long lContractID 				= -1;							//��ͬ��
long lContractType				= -1;							//��ͬ����	<<<
long lLoanType					= -1;							//��������	<<<
long lLoanNoteID 				= -1;							//�ſ�֪ͨ��
String tsStartDate 				= null;							//��ʼ����
String tsEndDate 				= null;							//��������
Timestamp tsInterestClear 		= null;							//��Ϣ��
double dAmount 					= 0.0;							//������
//-----------------------ҳ��һ����----------------------
//-----------------------ҳ�������----------------------
long lPayInterestAccountID 			= -1;						//���ڴ���˺�
long lInterestBankID 				= -1;						//�տ�����
long lReceiveInterestAccountID 		= -1;						//�տ�˺�

double dInterest 				= 0.0;							//��Ϣ
double dInterestReceivable 		= 0.0;							//������Ϣ
double dInterestIncome 			= 0.0;							//������Ϣ
double dInterestTax				= 0.0;							//��Ϣ˰��
double dCompoundInterest 		= 0.0;							//����
double dOverDueInterest			= 1.0;							//���ڷ�Ϣ
double dCommission 				= 0.0;							//������
double dSuretyFee 				= 0.0;							//������

double dRealInterest 			= 0.0;							//ʵ��֧����Ϣ
double dRealInterestReceivable 	= 0.0;							//ʵ��֧��������Ϣ
double dRealInterestIncome 		= 0.0;							//ʵ��֧��������Ϣ
double dRealInterestTax			= 0.0;							//ʵ��֧����Ϣ˰��
double dRealCompoundInterest 	= 0.0;							//ʵ��֧������
double dRealOverDueInterest		= 0.0;							//ʵ��֧�����ڷ�Ϣ
double dRealCommission 			= 0.0;							//ʵ��֧��������
double dRealSuretyFee 			= 0.0;							//ʵ��֧��������
//double dInterestTaxRate			= 0.0;						//��Ϣ˰����		<<<

long lIsRemitInterest 			= -1;							//�Ƿ��⻹ʣ�������Ϣ
long lIsRemitCompoundInterest 	= -1;							//�Ƿ��⻹ʣ�ิ��
long lIsRemitOverDueInterest 	= -1;							//�Ƿ��⻹ʣ�����ڷ�Ϣ
long lIsRemitSuretyFee 			= -1;							//�Ƿ��⻹ʣ�ൣ����
long lIsRemitCommission 		= -1;							//�Ƿ��⻹ʣ��������

String tsInterestStart 			= null;							//��Ϣ��
long lAbstractID 				= -1;							//ժҪID
String strAbstract = "";										//ժҪ

//-----------------------ҳ�������----------------------
//-----------------------�õ�ҳ�����--------------------
String strTmp =null;
strTmp=(String)request.getAttribute("lContractType");			//��ͬ����
if (strTmp!=null && strTmp.trim().length()>0){
	lContractType = Long.valueOf(strTmp.trim()).longValue();
}

	TransRepaymentLoanInfo info=(TransRepaymentLoanInfo)request.getAttribute("RepaymentInfo");							//������
	
//-----------------------�õ�ҳ�����--------------------
if (info != null){
	lID							= info.getID();						//ID
	lClientID 					= info.getClientID();				//����ͻ�ID
	lLoanAccountID 				= info.getLoanAccountID();			//�����˺�
	lContractID 				= info.getLoanContractID();			//��ͬ��
	lLoanNoteID 				= info.getLoanNoteID();				//�ſ�֪ͨ��
	tsInterestClear 			= info.getInterestClear();			//��Ϣ��
	
	dInterest 					= info.getInterest();				//��Ϣ
	dInterestReceivable 		= info.getInterestReceiveAble();	//������Ϣ
	dInterestIncome 			= info.getInterestIncome();			//������Ϣ
	dInterestTax				= info.getInterestTax();			//��Ϣ˰��
	dCompoundInterest 			= info.getCompoundInterest();		//����
	dOverDueInterest			= info.getOverDueInterest();		//���ڷ�Ϣ
	dCommission 				= info.getCommission();				//������
	dSuretyFee 					= info.getSuretyFee();				//������
	
	/**
	 * �жϵ�ǰ�Ĵ�������,���������Ͷ�ȡ��Ӧ����Ϣ�˻���������Ϣ
	 * �������Ӫ����,��Ϣ������˻��ǵ������˻�,�����ǵ���������.ί���Ǹ�Ϣ�˻��͸�Ϣ����
	 
	if (lContractType==LOANConstant.LoanType.YT
		||lContractType==LOANConstant.LoanType.ZGXEDQ
		||lContractType==LOANConstant.LoanType.ZGXEZCQ
		||lContractType==LOANConstant.LoanType.ZYDQ
		||lContractType==LOANConstant.LoanType.ZYZCQ){//���������
		
		lLoanType = 1;
		lReceiveInterestAccountID 	= info.getReceiveSuretyAccountID();//�տ�˺�
	}
	else if(lContractType==LOANConstant.LoanType.WT 
		||lContractType==LOANConstant.LoanType.WTTJTH){//�����ί��
		lLoanType = 2;
		lReceiveInterestAccountID 	= info.getReceiveInterestAccountID();//�տ�˺�
	}*/
	if (info.getReceiveSuretyAccountID()>0) lReceiveInterestAccountID = info.getReceiveSuretyAccountID();
	else if (info.getReceiveInterestAccountID()>0) lReceiveInterestAccountID = info.getReceiveInterestAccountID();
	
	
	lPayInterestAccountID 		= info.getPayInterestAccountID();	//���ڴ���˺�
	lInterestBankID 			= info.getInterestBankID();			//�տ�����
	
	dRealInterest 				= info.getRealInterest();			//ʵ��֧����Ϣ
	dRealInterestReceivable 	= info.getRealInterestReceiveAble();//ʵ��֧��������Ϣ
	dRealInterestIncome 		= info.getRealInterestIncome();		//ʵ��֧��������Ϣ
	dRealInterestTax			= info.getRealInterestTax();		//ʵ��֧����Ϣ˰��
	dRealCompoundInterest 		= info.getRealCompoundInterest();	//ʵ��֧������
	dRealOverDueInterest		= info.getRealOverDueInterest();	//ʵ��֧�����ڷ�Ϣ
	dRealCommission 			= info.getRealCommission();			//ʵ��֧��������
	dRealSuretyFee 				= info.getRealSuretyFee();			//ʵ��֧��������
	
	lIsRemitInterest 			= info.getIsRemitInterest();		//�Ƿ��⻹ʣ�������Ϣ
	lIsRemitCompoundInterest	= info.getIsRemitCompoundInterest();//�Ƿ��⻹ʣ�ิ��
	lIsRemitOverDueInterest		= info.getIsRemitOverDueInterest();	//�Ƿ��⻹ʣ�����ڷ�Ϣ
	lIsRemitSuretyFee 			= info.getIsRemitSuretyFee();		//�Ƿ��⻹ʣ�ൣ����
	lIsRemitCommission 			= info.getIsRemitCommission();		//�Ƿ��⻹ʣ��������
	
	tsInterestStart 			= DataFormat.getDateString(info.getInterestStart());//��Ϣ��
	lAbstractID 				= info.getAbstractID();				//ժҪID
	strAbstract 				= info.getAbstract();				//ժҪ
	lStatusID					= info.getStatusID();				//�õ�״̬ID
	tsModify 					= info.getModify();
}

strTmp=(String)request.getAttribute("tsStartDate");				//��ʼʱ��
if (strTmp!=null && strTmp.trim().length()>0){
	tsStartDate = strTmp.trim();
}
strTmp=(String)request.getAttribute("tsEndDate");				//����ʱ��
if (strTmp!=null && strTmp.trim().length()>0){
	tsEndDate = strTmp.trim();
}
strTmp=(String)request.getAttribute("dAmount");					//������
if (strTmp!=null && strTmp.trim().length()>0){
	dAmount = DataFormat.parseNumber(strTmp.trim());
}
strTmp = (String)request.getAttribute("lCheckUserID");			//¼����
if(strTmp != null && strTmp.trim().length() > 0)
{
	lCheckUserID = Long.valueOf(strTmp).longValue();
}
strTmp = (String)request.getAttribute("lPayInterestAccountID");	//��Ϣ�˺�
if(strTmp != null && strTmp.trim().length() > 0)
{
	lPayInterestAccountID = Long.valueOf(strTmp).longValue();
}

strTmp = (String)request.getAttribute("nOrderByCode");
if(strTmp != null && strTmp.length()>0)
{
	nOrderByCode = Integer.valueOf(strTmp.trim()).intValue();
}

strTmp = (String)request.getAttribute("lStatusID");
if(strTmp != null && strTmp.trim().length() > 0)
{
	lStatusID = Long.valueOf(strTmp).longValue();
}

strTmp = (String)request.getAttribute("lDesc");
if(strTmp != null && strTmp.length()>0)
{
	lDesc = Long.valueOf(strTmp.trim()).longValue();
}
Log.print("����ʽ:"+lDesc);


//��������ָ�����
String strOBInstr = (String)request.getAttribute("OBInstr");
String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");

//ҳ�渨������
String strAction 				= null;						//������
String strActionResult 			= Constant.ActionResult.FAIL;//�������
if (request.getAttribute("strAction") != null)
{
	strAction = (String)request.getAttribute("strAction");
}
%>
<safety:resources />
<form name="frmV047" method="post"> 

<input name="lID" type="hidden" value="<%=lID%>">
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="strActionResult" type="hidden" value="<%=strActionResult%>">
<input name="strAction"  type="hidden">
<input name="strSuccessPageURL"  type="hidden" >
<input name="strFailPageURL"  type="hidden" >

<input name="tsModify" type="hidden" value="<%=DataFormat.getDateString(tsModify)%>">
<input name="lCheckUserID" type="hidden" value="<%=lCheckUserID%>">
<!--�޸ĳɹ������Ӳ��һ��Բ���-->
<input type="hidden" name="lStatusID" value="<%=lStatusID%>">
<input name="lTransactionTypeID" type="hidden" value="<%=SETTConstant.TransactionType.INTERESTFEEPAYMENT%>">
<input type="hidden" name="nOrderByCode" value="<%=nOrderByCode%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<!--�޸ĳɹ������Ӳ��һ��Բ���-->

<TABLE border=0 class=top height=8 width="99%">
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>��Ϣ����֧��</B></TD></TR>
  <TR borderColor=#E8E8E8>
    <TD vAlign=bottom width="100%">
      <TABLE align=center border=1 cellPadding=2 cellSpacing=2 height=60 
      width="97%" borderColor=#999999>
      <tr borderColor=#E8E8E8>
      	<td colspan='7'>
      		<strong>��Ϣ���û�����ϸ����</strong>
      	</td>
      </tr>
        <TR borderColor=#E8E8E8>
          <td height=20 width="5%">&nbsp;</td>
          <TD height=20 width="17%">�������ͻ���ţ�</TD>
          <TD height=20 width="33%">
           <input type='hidden' name='lLoanAccountID' value='<%=lLoanAccountID%>'>
           <INPUT class=box maxLength=6 
            name="lLoanAccountIDCtrl" size=10 value="<%=NameRef.getClientCodeByAccountID(lLoanAccountID)%>" disabled> </TD>
          <td>&nbsp;</td>
          <TD height=20 width="16%">�������ͻ����� ��</TD>
		  <TD height=20 width="34%">
                <textarea name="txtTrustLoanClientNameCtrl"  class="box" disabled   rows=2 cols=30><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
            </TD>
          <td>&nbsp;</td>
		</TR>
        <TR borderColor=#E8E8E8>
		  <td>
		  	<input type='radio' name='radio1' disabled value='1' <%=lPayInterestAccountID>0?"checked":""%> onfocus=nextfield='"lPayInterestAccountIDCtrlCtrl3"';>
		  </td>
		  <%
	//���ڴ���˺ŷŴ�
		String strCtrlNameAcct = "lPayInterestAccountID";
		String strTitleAcct = "���ڴ���˺�";
		long lClientIDAcct = lClientID;						//????
		long lAccountIDAcct = lPayInterestAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lPayInterestAccountID);
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;
		String strClientCtrlAcct = "";
		String strFirstTDAcct = "";
		String strSecondTDAcct = "";
		String[] strNextControlsAcct = {"lReceiveInterestAccountIDCtrlCtrl3"};
		String strRtnClientIDCtrlAcct = "";
		String strRtnClientNoCtrlAcct = "";
		String strRtnClientNameCtrlAcct = "";
		
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
<script language='javascript'>
document.all.lPayInterestAccountIDCtrlCtrl1.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl2.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl3.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl4.disabled=true;
</script>
		  <TD>&nbsp;</TD>
		  <TD>&nbsp;</TD>
		  <TD>&nbsp;</TD>
		  <TD>&nbsp;</TD>
           </TR>
           <TR borderColor=#E8E8E8>
	  	  <td>
		  	<input type='radio' disabled name='radio1' <%=lInterestBankID>0?"checked":""%> value='2' onfocus=nextfield='lInterestBankIDCtrl'>
		  </td>
<%
//�տ��������ƷŴ�
		String strCtrlNameBranch = "lInterestBankID";
		String strTitleBranch = "�տ���������";
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
<script language='javascript'>
document.all.lInterestBankIDCtrl.disabled=true;
</script>
<TD height=20 >&nbsp;</TD>
<TD height=20 >&nbsp;</TD>
<TD height=20 >&nbsp;</TD>
<TD height=20 >&nbsp;</TD>
</TR>  
    </TABLE>
	</td>
	</tr>
<tr>
	<td>
		<TABLE align=center border=1 cellPadding=2 cellSpacing=2 height=60 width="97%" borderColor=#999999>
			<tr borderColor=#E8E8E8>
				<td>
					<strong>��Ϣ����������ϸ����</strong>
				</td>
			</tr>
      		<tr borderColor=#E8E8E8>
      			<td height=20>
      				<table border=0 cellpadding=0 cellspacing=0 width='100%'>
      					<tr borderColor=#E8E8E8>
        <td>&nbsp;</td>
        	<%
//�տ�˺ŷŴ�
		strCtrlNameAcct = "lReceiveInterestAccountID";
		strTitleAcct = "�տ�˺�";
		lClientIDAcct = -1;
		lAccountIDAcct = lReceiveInterestAccountID;
		strAccountNoAcct = NameRef.getAccountNoByID(lReceiveInterestAccountID);
		lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		lAccountTypeIDAcct = -1;
		lReceiveOrPayAcct = -1;
		strClientCtrlAcct = "";
		strFirstTDAcct = "";
		strSecondTDAcct = "";
		strNextControlsAcct = new String[]{"dRealInterest"};
		strRtnClientIDCtrlAcct = "";
		strRtnClientNoCtrlAcct = "";
		strRtnClientNameCtrlAcct = "lReceiveInterestClientName";
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
<script language='javascript'>
document.all.lReceiveInterestAccountIDCtrlCtrl1.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl2.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl3.disabled=true;
document.all.lReceiveInterestAccountIDCtrlCtrl4.disabled=true;
</script>
<TD height=20 width="14%">�ͻ�����:</TD>
          <TD height=20 width="40%">
                <textarea class="box" name="lReceiveInterestClientName" disabled   rows=2 cols=30><%=NameRef.getClientNameByAccountID(lReceiveInterestAccountID)%></textarea>        
            </TD>
      					</tr>
      				</table>
      			</td>
      		</tr>
      	</table>
	</td>
</tr>
	<tr>
		<td>
			<TABLE align=center border=1 cellPadding=0 cellSpacing=0 height=60 width="97%" borderColor=#999999>
	<tr> 
		<td>
			<TABLE align=center border=0 cellPadding=2 cellSpacing=2 height=60 width="100%">
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
                      createAmountCtrl("frmV047","dInterest","<%=DataFormat.formatAmountUseZero(dInterest)%>","");
					  document.frmV047.dInterest.disabled = true;
					</script>
					</TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                     	//Modify by leiyang date 2007/07/18
						//createAmountCtrl("frmV047","dRealInterest","<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest",null,'<%=lCurrencyID%>',"onChange='disassemble(),sum()'");
						//document.frmV047.dRealInterest.disabled = true;
						createAmountCtrl_standard("frmV047","dRealInterest",false,"<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest","",<%=lCurrencyID%>,"disabled=\"true\"","disassemble();sum()");
						
					 </script>
					 </TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled class="box" name="lIsRemitInterest" <%=lIsRemitInterest>0?"checked":""%> value="1" >
					  �⻹ʣ����Ϣ</TD>
                  </tr>
                  	
                  <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;���� ������Ϣ��</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
					<script language="JavaScript">
                       createAmountCtrl("frmV047","dInterestReceivable","<%=DataFormat.formatAmountUseZero(dInterestReceivable)%>","");
					   document.frmV047.dInterestReceivable.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                    createAmountCtrl("frmV047","dRealInterestReceivable","<%=dRealInterestReceivable%>","",null,'<%=lCurrencyID%>',"");
                    document.frmV047.dRealInterestReceivable.disabled = true;
					</script>
					 </TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  
                  <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ϣ��</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                    createAmountCtrl("frmV047","dInterestIncome","<%=DataFormat.formatAmountUseZero(dInterestIncome)%>","");
					document.frmV047.dInterestIncome.disabled = true;
					
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                   	createAmountCtrl("frmV047","dRealInterestIncome","<%=DataFormat.formatAmountUseZero(dRealInterestIncome)%>","dRealInterestTax",null,'<%=lCurrencyID%>',"");
                   	document.frmV047.dRealInterestIncome.disabled = true;
					</script>
					</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>


                  
                  <tr> 
                    <td>������</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                    createAmountCtrl("frmV047","dCompoundInterest","<%=DataFormat.formatAmountUseZero(dCompoundInterest)%>","");
					document.frmV047.dCompoundInterest.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
           		 		//createAmountCtrl("frmV047","dRealCompoundInterest","<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest",null,'<%=lCurrencyID%>',"onChange='sum()'");
           		 		//document.frmV047.dRealCompoundInterest.disabled = true;
	           		 	//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV047","dRealCompoundInterest",false,"<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest","",<%=lCurrencyID%>,"disabled=\"true\"","sum()");
           		 		
					  </script>
					 	</TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled  class="box" name="lIsRemitCompoundInterest" <%=lIsRemitCompoundInterest>0?"checked":""%> value="1" >
                      �⻹ʣ�ิ��</TD>
                  </tr>
                  <tr> 
                    <td>���ڷ�Ϣ:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                    createAmountCtrl("frmV047","dOverDueInterest","<%=DataFormat.formatAmountUseZero(dOverDueInterest)%>","");
					document.frmV047.dOverDueInterest.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
	                   	//createAmountCtrl("frmV047","dRealOverDueInterest","<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","",null,'<%=lCurrencyID%>',"onChange='sum()'");
	                   	//document.frmV047.dRealOverDueInterest.disabled = true;
						//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV047","dRealOverDueInterest",false,"<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","","",<%=lCurrencyID%>,"disabled=\"true\"","sum()");
					 </script>
					 
					 </TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled  class="box" name="lIsRemitOverDueInterest" <%=lIsRemitOverDueInterest>0?"checked":""%> value="1" >
                      �⻹ʣ�����ڷ�Ϣ</TD>
                  </tr>
                  <tr> 
                    <td>��Ϣ˰�ѣ�</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                    createAmountCtrl("frmV047","dInterestTax","<%=DataFormat.formatAmountUseZero(dInterestTax)%>","");
					document.frmV047.dInterestTax.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                   	createAmountCtrl("frmV047","dRealInterestTax","<%=DataFormat.formatAmountUseZero(dRealInterestTax)%>","dRealCommission",null,'<%=lCurrencyID%>',"");
                   	document.frmV047.dRealInterestTax.disabled = true;
					</script>
					</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>������:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                    createAmountCtrl("frmV047","dCommission","<%=DataFormat.formatAmountUseZero(dCommission)%>","");
					document.frmV047.dCommission.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
	                      //createAmountCtrl("frmV047","dRealCommission","<%=DataFormat.formatAmountUseZero(dRealCommission)%>","",null,'<%=lCurrencyID%>',"onChange='sum()'");
	                      //document.frmV047.dRealCommission.disabled = true;
	                      //Modify by leiyang date 2007/07/18
						  createAmountCtrl_standard("frmV047","dRealCommission",false,"<%=DataFormat.formatAmountUseZero(dRealCommission)%>","","",<%=lCurrencyID%>,"disabled=\"true\"","sum()");
					  </script></TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled  class="box" name="lIsRemitCommission" <%=lIsRemitCommission>0?"checked":""%> value="1" >
                      �⻹ʣ��������</TD>
                  </tr>

                  <tr> 
                    <td>������:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                    createAmountCtrl("frmV047","dSuretyFee","<%=DataFormat.formatAmountUseZero(dSuretyFee)%>","");
					document.frmV047.dSuretyFee.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
	                      //createAmountCtrl("frmV047","dRealSuretyFee","<%=DataFormat.formatAmountUseZero(dRealSuretyFee)%>","tsInterestStart",null,'<%=lCurrencyID%>',"onChange='sum()'");
	                      //document.frmV047.dRealSuretyFee.disabled = true;
						  //Modify by leiyang date 2007/07/18
						  createAmountCtrl_standard("frmV047","dRealSuretyFee",false,"<%=DataFormat.formatAmountUseZero(dRealSuretyFee)%>","tsInterestStart","",<%=lCurrencyID%>,"disabled=\"true\"","sum()");
					  </script></TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled class="box" name="lIsRemitSuretyFee" <%=lIsRemitSuretyFee>0?"checked":""%> value="1" >
                      �⻹ʣ�ൣ����</TD>
                  </tr>

                  <tr> 
                    <td>��Ϣ���úϼƣ�</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                     createAmountCtrl("frmV047","dSumFee","<%=DataFormat.formatAmountUseZero(dInterest+dCompoundInterest+dOverDueInterest+dCommission+dSuretyFee)%>","");
					 document.frmV047.dSumFee.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                      createAmountCtrl("frmV047","dRealSumFee","<%=DataFormat.formatAmountUseZero((dRealInterest*100+dRealCompoundInterest*100+dRealOverDueInterest*100+dRealCommission*100+dRealSuretyFee*100)/100)%>","");
					  document.frmV047.dRealSumFee.disabled = true;
					 </script></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
      		</table>
      		</td>
      		</tr>
      		</table>
		</td>
	</tr>
	<tr borderColor=#E8E8E8>
		<td align=center>
			<table border=0 cellpadding=0 cellspacing=0 width="97%">
				<tr>
      				<TD height=25 width="17%">
      					��Ϣ�� :
      				</TD>
	        		<TD height=25 width="25%">
	        		 <fs_c:calendar 
			          	    name="tsInterestStart"
				          	value="" 
				          	properties="nextfield ='lAbstractIDCtrl'" 
				          	size="20"/>
				      <script>
	          		$('#tsInterestStart').val('<%=tsInterestStart !=null?tsInterestStart:""%>');
	          	</script>
					</TD>
	        		<td>&nbsp;</td>
<%
//ժҪ�Ŵ�
		String strCtrlNameAbstract = "lAbstractID";
		String strTitleAbstract = "ժҪ";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = strAbstract==null?"":strAbstract;
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
<script language='javascript'>
document.all.lAbstractIDCtrl.disabled=true;
</script>
				</tr>
				
				<tr>
					<td height=10 colspan=6>
					<hr>
					</td>
				</tr>
        <TR vAlign=middle>
          <TD colSpan=6 height=20>
            <DIV align=right>
<%
	if("Query".equalsIgnoreCase(strAction))
	{
		if(strOBInstr!=null && strOBInstr.equals("obinstr"))
		{
%>
			<input type="button" name="print" value=" �� ӡ " class="button" onClick="doPrint();">
			<input class="button" name="Submit32" type="button" value=" �� �� " onClick="doReturn();">	
<%	
		}
		else
		{
%>

	            <INPUT class=button name=Submit32 onclick="print()" type=button value=" �� ӡ "> 
				<input type="button" name="close" value=" �� �� " class="button" onClick="window.close();">
<%
		}
	}
	else
	{
%>
			<%
			if (lStatusID == SETTConstant.TransactionStatus.SAVE)
			{
			%>
			<INPUT class=button name=Submit3 onclick="doCheck(frmV047);" type=button value=" �� �� "> 
			<%
			}
			else if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV047);" type=button value=" ȡ������ "> 			
			<%
			}
			%>
            <INPUT class=button name=Submit32 onclick="print()" type=button value=" �� ӡ "> 
			<INPUT class=button name=Submit322 onclick="doBack(frmV047);" type=button value=" �� �� "> 
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
            name="strCheckAbstract" value="<%=info.getCheckAbstract()!=null?info.getCheckAbstract():""%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">¼����:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(info.getInputUserID())%></TD>
          <TD height=25 width="8%">¼������:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(info.getInput())%></TD>
          <TD height=25 width="6%">������:</TD>
          <TD height=25 width="7%"><%=NameRef.getUserNameByID(info.getCheckUserID())%></TD>
          <TD height=25 width="8%">��������:</TD>
          <TD height=25 width="9%"><%=(info.getCheckUserID() > 0 ? DataFormat.formatDate(info.getExecute()) : "&nbsp;")%></TD>
          <TD height=25 width="5%">״̬:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR>
            
			</table>
	
</form>		
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<script language="JavaScript">
firstFocus(document.frmV047.strCheckAbstract);
//setSubmitFunction("<%=(lStatusID == SETTConstant.TransactionStatus.SAVE ? "doCheck" : "doCancelCheck")%>(frmV047)");
setFormName("frmV047"); 
</script>
<%
	}
%>
<script language='javascript'>
function doBack(form)
{
	if (confirm("�Ƿ񷵻أ�"))
	{
		<%
		if (lStatusID == SETTConstant.TransactionStatus.SAVE)
		{
		%>
		form.strActionResult.value="<%=Constant.ActionResult.SUCCESS%>";
		form.action = "../view/v046.jsp";
		<%
		}
		else
		{
		%>
		form.lStatusID.value="<%=SETTConstant.TransactionStatus.CHECK%>";
		form.action = "../control/c042.jsp";
		form.strSuccessPageURL.value = '../view/v048.jsp';
		form.strFailPageURL.value = '../view/v047.jsp';
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
	strTmp=(String)request.getAttribute("lReturn");
	if ( (strTmp!=null)&&(strTmp.length()>0) )
	{
		lOBReturn=Long.valueOf(strTmp).longValue();
	}
%>	
	if (confirm("�Ƿ��ӡ?")){
		window.open( "../accountinfo/a903-v.jsp?lID=<%=info.getID()%>&lTransactionTypeID=<%=SETTConstant.TransactionType.INTERESTFEEPAYMENT%>&strSuccessPageURL='../accountinfo/a902-v.jsp'&strFailPageURL='../accountinfo/a902-v.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doReturn()
{
	document.location.href="<%=strContext%>/settlement/obinstruction/control/c001.jsp?strSuccessPageURL=/settlement/obinstruction/view/v002.jsp&strFailPageURL=/settlement/obinstruction/view/v002.jsp&_pageLoaderKey=<%=strPageLoaderKey%>";
}	
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v046.jsp';
	form.strFailPageURL.value = '../view/v047.jsp';
	
	if (confirm("�Ƿ񸴺ˣ�"))
	{
		form.action = "../control/c044.jsp";
		showSending();
		form.submit();
	}
}
function doCancelCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CANCELCHECK%>';	
	form.strSuccessPageURL.value = '../view/v048.jsp';
	form.strFailPageURL.value = '../view/v047.jsp';
	
	if (!validateFields(form))
	{
		return false;
	}
	if (confirm("�Ƿ�ȡ�����ˣ�"))
	{
		form.action = "../control/c044.jsp";
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