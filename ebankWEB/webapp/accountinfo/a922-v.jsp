<%--
 ҳ������ ��v053_6.jsp
 ҳ�湦�� : ��ʴ����ջ�-��Ӫ�����ջ�-����/ȡ������ҳ��
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
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%> 
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation"%>
<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo"%> 
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript">

function doDisPatch()
{
	var tempRealInterest = reverseFormatAmount(document.frmV053_6.dRealInterest.value);
	var tempInterest = reverseFormatAmount(document.frmV053_6.dInterestCtrl.value);
	var tempInterestReceiveAble = reverseFormatAmount(document.frmV053_6.dInterestReceiveAbleCtrl.value);
    var tempRealInterestReceiveAble = reverseFormatAmount(document.frmV053_6.dRealInterestReceiveAble.value);//����
	if (tempRealInterest == "")
	{
	 tempRealInterest = 0.0;
	}
	
	if (tempInterest == "")
	{
	 tempInterest = 0.0;
	}
	
	if (tempInterestReceiveAble == "")
	{
	   tempInterestReceiveAble = 0.0;
	}
	
	if (tempRealInterestReceiveAble == "")
	{
	   tempRealInterestReceiveAble = 0.0;
	}

	if (!isFloat(tempRealInterest))
	{
		alert("��������ȷ����ֵ");
		document.frmV053_6.dRealInterest.value = "0.00";
		document.frmV053_6.dRealInterest.focus();
		doDisPatch();
		return false;
	}
	
	
	if (parseFloat(tempRealInterest) > parseFloat(tempInterest))
	{
		alert("ʵ��֧��������Ϣ����Ӧ��֧����");
		document.frmV053_6.dRealInterest.value = "0.00";
		doDisPatch();
		document.frmV053_6.dRealInterest.focus();
		return false;
	}
	
	if (parseFloat(tempRealInterest) > parseFloat(tempInterestReceiveAble))
	{
		document.frmV053_6.dRealInterestReceiveAble.value = document.frmV053_6.dInterestReceiveAbleCtrl.value;
		document.frmV053_6.dRealInterestIncome.value = formatAmount1(round((parseFloat(tempRealInterest) - parseFloat(tempInterestReceiveAble)),2));
	}
	else
	{
		document.frmV053_6.dRealInterestReceiveAble.value = formatAmount1(tempRealInterest);
		document.frmV053_6.dRealInterestIncome.value = "0.00";
	}
	
}
function doChangeSum()
{
	var tempInterest = reverseFormatAmount(document.frmV053_6.dRealInterest.value);
	
	var tempCompoundInterest = reverseFormatAmount(document.frmV053_6.dRealCompoundInterest.value);
	var tempOverDueInterest = reverseFormatAmount(document.frmV053_6.dRealOverDueInterest.value);
	var tempSuretyFee = reverseFormatAmount(document.frmV053_6.dRealSuretyFee.value);
	
	if(tempInterest == "")
	{
		tempInterest = 0.0;
	}
	
	if(tempCompoundInterest == "")
	{
		tempCompoundInterest = 0.0;
	}
	
	if(tempOverDueInterest == "")
	{
		tempOverDueInterest = 0.0;
	}
	
	if(tempSuretyFee == "")
	{
		tempSuretyFee = 0.0;
	}
	
	if (!isFloat(tempInterest))
	{
		alert("��������ȷ����ֵ");
		document.frmV053_6.dRealInterest.value = "0.00";
		document.frmV053_6.dRealInterest.focus();
		return false;
	}
	
	
	
	if (!isFloat(tempCompoundInterest))
	{
		alert("��������ȷ����ֵ");
		document.frmV053_6.dRealCompoundInterest.value = "0.00";
		document.frmV053_6.dRealCompoundInterest.focus();
		return false;
	}
	
	if (!isFloat(tempOverDueInterest))
	{
		alert("��������ȷ����ֵ");
		document.frmV053_6.dRealOverDueInterest.value = "0.00";
		document.frmV053_6.dRealOverDueInterest.focus();
		return false;
	}
	
	if (!isFloat(tempSuretyFee))
	{
		alert("��������ȷ����ֵ");
		document.frmV053_6.dRealSuretyFee.value = "0.00";
		document.frmV053_6.dRealSuretyFee.focus();
		return false;
	}

	document.frmV053_6.dRealSumFee.value =formatAmount1( round((parseFloat(tempInterest) + parseFloat(tempCompoundInterest)+parseFloat(tempOverDueInterest)+parseFloat(tempSuretyFee)),2));
	
}


function doBoth()
{
	doDisPatch();
	doChangeSum();
	
}

</script>
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
String strFormName 								= "frmV053_6";

long lID 										= -1;										//Ϊ�޸ĺ�ɾ��
String strTempTransNO							= "";										//��ʱ���׺�
Timestamp tsModify								= null;										//�޸�ʱ��
long lInputUserID								= -1;						//¼����
long lCheckUserID								= sessionMng.m_lUserID;						//������
Timestamp tsExecute  							= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
long lTransactionTypeID							= SETTConstant.TransactionType.MULTILOANRECEIVE;													//��������
long lStatusID									= -1;										//״̬,�������Ӳ��ҵ�Ĭ�ϲ�ѯ
long lRecordStatusID							= -1;										//��¼״̬
long lDesc 										= Constant.PageControl.CODE_ASCORDESC_DESC;				//����ʽ
long lOrderByCode								= -1;													//�����ֶ�

Timestamp tsCheck								= null;													//����ʱ��
Timestamp tsInput								= null;													//¼��ʱ��

String strTransNo								= "";													//���׺�
/**
 * ҳ�渨������
 */
String strAction 								= null;
String strActionResult 							= Constant.ActionResult.FAIL;



/**
 * ҳ�������
 */
long lSubAccountID 								= -1;										//���˻�ID
long lPayInterestAccountID 						= -1;										//��Ϣ�˺�,��Ӫ/ί�й���
long lInterestBankID 							= -1;										//��Ϣ����ID,��Ӫ/ί�й���
long lPaySuretyAccountID 						= -1;										//���������˺�
long lSuretyBankID 								= -1;										//������������ID
long lReceiveSuretyAccountID 					= -1;										//�����������˺�


/**
 * ��ϢӦ��֧��ֵ
 */
double dInterest 								= 0.0;    
double dInterestReceiveAble 					= 0.0;										//������Ϣ
double dInterestIncome 							= 0.0;										//������Ϣ
double dCompoundInterest 						= 0.0;
double dOverDueInterest 						= 0.0;
double dSuretyFee 								= 0.0;										//������
 
/**
 * ʵ��֧����Ϣ
 */
double dRealInterest 							= 0.0;
double dRealInterestReceiveAble 					= 0.0;										//ʵ��֧��������Ϣ
double dRealInterestIncome 						= 0.0;										//ʵ��֧��������Ϣ
double dRealCompoundInterest 					= 0.0;
double dRealOverDueInterest 					= 0.0;
double dRealSuretyFee 							= 0.0;										//ʵ��֧��������

/**
 * �Ƿ��⻹��Ϣ
 */
long lIsRemitInterest 							= -1;
long lIsRemitCompoundInterest 					= -1;
long lIsRemitOverDueInterest 					= -1;
long lIsRemitSuretyFee 							= -1;

/**
 * �⻹ԭ��
 */
String strAdjustInterestReason 					= "";

/**
 * ������Ϣ����취
 */
long lCapitalAndInterstDealway 					= -1;

/**
 * �ϴν�Ϣ��,�����˻�������
 */
Timestamp tsLatestInterestClear					= null;											//�ϴν�Ϣ��

/**
 * Ϊ��ӡ��ӵ���Ϣ��Ϣ
 */
	   
Timestamp tsCompoundInterestStart 				= null;					//������Ϣ��
double dCompoundAmount 							= 0.0;					//��������
double dCompoundRate 							= 0.0;					//��������

Timestamp tsOverDueStart 						= null;					//���ڷ�Ϣ��Ϣ��
double dOverDueAmount 							= 0.0;					//���ڷ�Ϣ����
double dOverDueRate 							= 0.0;					//���ڷ�Ϣ����

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
double dSavedAmount								= 0.0;										//����Ľ��
Timestamp tsInterestStart 						= tsExecute;								//��Ϣ��
long lAbstractID 								= -1;										//ժҪID
String strAbstract 								= "";										//ժҪ
String strCheckAbstract							= "";										//����ժҪ

//-----------------------ҳ��һ����----------------------

 
String strTemp = null;
/**
 * ��Request�л�ó�ʼ��������Ϣ
 */
SubLoanAccountDetailInfo tempSubLoanAccountDetailInfo = null;
tempSubLoanAccountDetailInfo = (SubLoanAccountDetailInfo)request.getAttribute("SubLoanAccountDetailInfo");

if (tempSubLoanAccountDetailInfo != null)
{ 	
	lSubAccountID				= tempSubLoanAccountDetailInfo.getSubAccountID();				//���˻�ID
	lPayInterestAccountID 		= tempSubLoanAccountDetailInfo.getPayInterestAccountID();		//��Ϣ�˺�
	lPaySuretyAccountID 		= tempSubLoanAccountDetailInfo.getPaySuretyFeeAccountID();		//���������˺�
	lSuretyBankID 		= tempSubLoanAccountDetailInfo.getPaySuretyFeeBankID();			//������������
	lReceiveSuretyAccountID 	= tempSubLoanAccountDetailInfo.getReceiveSuretyFeeAccountID();	//�յ������˺�
	tsLatestInterestClear 		= tempSubLoanAccountDetailInfo.getLatestClearInterest();		//�ϴν�Ϣ��
	
	dInterest 					= tempSubLoanAccountDetailInfo.getInterest();					//��Ϣ
	dInterestReceiveAble			= tempSubLoanAccountDetailInfo.getInterestReceiveAble();		//������Ϣ
	dInterestIncome				= tempSubLoanAccountDetailInfo.getInterestIncome();				//������Ϣ
	dCompoundInterest 			= tempSubLoanAccountDetailInfo.getCompoundInterest();			//����
	dOverDueInterest 			= tempSubLoanAccountDetailInfo.getOverDueInterest();			//��Ϣ
	dSuretyFee					= tempSubLoanAccountDetailInfo.getSuretyFee();
}
/**
 * ʵ��֧��Ĭ�Ϻ�Ӧ��֧�����
 */
dRealInterest 					= dInterest;
dRealInterestReceiveAble			= dInterestReceiveAble;
dRealInterestIncome				= dInterestIncome;
dRealCompoundInterest 			= dCompoundInterest;
dRealOverDueInterest 			= dOverDueInterest;
dRealSuretyFee					= dSuretyFee;

strTemp = (String)request.getAttribute("strActionResult");
if (strTemp != null && strTemp.trim().length()>0)								//�������
{
	  strActionResult = strTemp.trim();
}
strTemp = (String)request.getAttribute("strAction");
if (strTemp != null && strTemp.trim().length()>0)								//��������
{
	  strAction = strTemp.trim();
}

strTemp = (String)request.getAttribute("lTransactionTypeID");
if (strTemp !=null && strTemp.trim().length()>0){								//��������
	lTransactionTypeID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lID");
if (strTemp !=null && strTemp.trim().length()>0){								//ID
	lID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("strTempTransNO");
if (strTemp !=null && strTemp.trim().length()>0){								//��ʱ���׺�
	strTempTransNO = strTemp.trim();
}

strTemp = (String)request.getAttribute("tsModify");
if (strTemp !=null && strTemp.trim().length()>0){								//�޸�ʱ��
	tsModify = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lInputUserID");
if (strTemp !=null && strTemp.trim().length()>0){								//¼����
	lInputUserID = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("lCheckUserID");
if (strTemp !=null && strTemp.trim().length()>0){								//������
	lCheckUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsExecute");
if (strTemp !=null && strTemp.trim().length()>0){								//ִ����
	tsExecute = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lStatusID");
if (strTemp !=null && strTemp.trim().length()>0){								//��¼״̬
	lStatusID = Long.valueOf(strTemp.trim()).longValue();
}
/**
 * Ĭ�ϱ���ļ�¼״̬�Ϳ����������һ��
 */
lRecordStatusID = lStatusID;
strTemp = (String)request.getAttribute("lRecordStatusID");
if (strTemp !=null && strTemp.trim().length()>0){								//����ļ�¼״̬
	lRecordStatusID = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("lDesc");
if (strTemp !=null && strTemp.trim().length()>0){								//����ʽ
	lDesc = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lOrderByCode");
if (strTemp !=null && strTemp.trim().length()>0){								//�����ֶ�
	lOrderByCode = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsInput");
if (strTemp !=null && strTemp.trim().length()>0){								//�����ֶ�
	tsInput = DataFormat.getDateTime(strTemp.trim());
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

strTemp = (String)request.getAttribute("dSavedAmount");										//����Ľ��
if (strTemp != null && strTemp.trim().length() > 0)
{
	dSavedAmount = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("lAbstractIDCtrl");
if (strTemp != null && strTemp.trim().length() > 0)											//ժҪ�ı�
{
	strAbstract = strTemp;
}

strTemp = (String)request.getAttribute("strCheckAbstract");
if (strTemp != null && strTemp.trim().length() > 0)											//ժҪ�ı�
{
	strCheckAbstract = strTemp;
}


//��ȡҳ�����ҵ������

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

strTemp = (String)request.getAttribute("lPaySuretyAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//���������˺�
{
	lPaySuretyAccountID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lSuretyBankID");
if (strTemp != null && strTemp.trim().length() > 0)											//������������
{
	lSuretyBankID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lReceiveSuretyAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//�յ������˺�
{
	lReceiveSuretyAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("dInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ����Ϣ
{
	dInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestReceiveAble");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ��������Ϣ
{
	dInterestReceiveAble = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestIncome");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ��������Ϣ
{
	dInterestIncome = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("dSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//Ӧ��������
{
	dSuretyFee = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ����Ϣ
{
	dRealInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestReceiveAble");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��������Ϣ
{
	dRealInterestReceiveAble = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestIncome");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��������Ϣ
{
	dRealInterestIncome = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("dRealSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//ʵ��������
{
	dRealSuretyFee = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("lIsRemitSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//�Ƿ��⻹������
{
	lIsRemitSuretyFee = Long.valueOf(strTemp.trim()).longValue();
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

strTemp = (String)request.getAttribute("strTransNo");										//���׺�
if (strTemp !=null && strTemp.trim().length()>0){
	strTransNo = strTemp.trim();
}
%>
<safety:resources />
 <form name="frmV053_6" action="" method="post">

 
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
	<input name="lMultiLoanType" type="Hidden" value="<%=lMultiLoanType%>">
<!--����,ȡ����Ҫ�Ĳ���-->
<!--���Ӳ���-->
	<input name="lStatusID" type="Hidden" value="<%=lRecordStatusID%>">
	<input name="lDesc" type="Hidden" value="<%=lDesc%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
<!--���Ӳ���-->
	<input name="tsLatestInterestClear" type="hidden" value="<%=DataFormat.getDateString(tsLatestInterestClear)%>"> <!--�ϴν�Ϣ��-->
	<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<TABLE border=0 class=top height=60 width="99%">
  <TBODY>   
 <TR class="tableHeader"> 
      <TD class=FormTitle height=2 width="100%"><B>ҵ�񸴺� ���� ��ʴ����ջ� ���� ��Ӫ�����ջ� </B></TD>
    </TR>
    		<tr>
					<td align='center'>
						<table width="97%">
							<tr>
								<td width='80'>�ո�����:</td>
								<td align='left'>
									<%SETTConstant.MultiLoanType.showList(out,"lMultiLoanType",0,2,false,false,"onchange='changeMultiLoanType(frmV053_1)'disabled onchange='changeMultiLoanType(frmV053_1)' onfocus=nextfield='strDeclarationNo'",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;
								</td>
								<td align="right">
									���б�����:&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<input type="text" class="box" disabled name="strDeclarationNo" value="<%=strDeclarationNo%>" onfocus=nextfield='lLoanAccountIDCtrlCtrl3'>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<TR>
					<TD height="130" vAlign="bottom" width="100%">
						<TABLE align="center" border="0" borderColor="#999999" height="40">
							<TBODY>
				<TR>
					<TD height="65" vAlign="bottom" width="100%">
						<TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
							<TBODY>
								<TR borderColor="#E8E8E8">
									<TD height="20" width="17%"><U>��Ӫ������ϸ����</U>
									</TD>
									<TD height="20" width="33%">&nbsp;	
									</TD>
									<TD height="20" width="15%">&nbsp;	
									</TD>
									<TD height="20" width="35%">&nbsp;	
									</TD>
								</TR>

<TR borderColor="#E8E8E8">								
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
									<TD height="20" width="15%">	����ͻ�����:
									</TD>
									<TD height="20" width="35%">
										<textarea name="strLoanClientName" class="box" disabled  rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
									</TD>

</TR>
								<TR borderColor="#E8E8E8">
									<%
//��Ӫ�����˺ŷŴ�   	
		String strCtrlNameTrustLoanAcct = "lLoanAccountID";
		String strTitleTrustLoanAcct = "��Ӫ�����˺�";
		long lClientIDTrustLoanAcct = NameRef.getClientIDByAccountID(lLoanAccountID);
		long lAccountIDTrustLoanAcct = lLoanAccountID;
		String strAccountNoTrustLoanAcct = NameRef.getAccountNoByID(lLoanAccountID);
		long lAccountGroupTypeIDTrustLoanAcct = SETTConstant.AccountGroupType.TRUST;
		long lAccountTypeIDTrustLoanAcct = -1;
		long lReceiveOrPayTrustLoanAcct = -1;
		String strClientCtrlTrustLoanAcct = "";
		String strFirstTDTrustLoanAcct = "";
		String strSecondTDTrustLoanAcct = "";
		String[] strNextControlsTrustLoanAcct = {"lLoanContractIDCtrl"};
		String strRtnClientIDCtrlTrustLoanAcct = "";
		String strRtnClientNoCtrlTrustLoanAcct = "";
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
									<TD height="20" width="15%">&nbsp;
									</TD>
									<TD height="20" width="35%">&nbsp;</TD>
								</TR>
								<TR borderColor="#E8E8E8">
									<%
//��ͬ�ŷŴ�
	String strCtrlNameContract = "lLoanContractID";
	String strTitleContract = "��ͬ��";
	long lClientIDContract = NameRef.getClientIDByAccountID(lLoanAccountID);
	long lLoanContractIDContract = lLoanContractID;
	String strContractNo = NameRef.getContractNoByID(lLoanContractID);
	long lContractTransactionType = SETTConstant.TransactionType.TRUSTLOANRECEIVE;
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
</script>

<script language="javascript">function getContractTypeSQL()
{
	return "select distinct nTypeID,'FromConstant_1_nTypeID' as sDesc from  loaninfo ";
}
</script>
									<td width="15%">&nbsp;
									</td>
									<td width="35%">&nbsp;
									</td>
								</TR>
								<TR borderColor="#E8E8E8">
									<%					
//�ſ�֪ͨ���ݺŷŴ�
	String strCtrlNamePayForm = "lLoanNoteID";
	String strTitlePayForm = "�ſ�֪ͨ���ݺ�";
	long lLoanContractIDPayForm = lLoanContractID;
	long lPayFormID = lLoanNoteID;
	String strPayFormNo = NameRef.getPayFormNoByID(lLoanNoteID);
	long lPayFormTypeID = 1;									//�ſ�֪ͨ������(��ѯ����:1,���У�2��ί��)
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
									<TD height="20" width="35%">&nbsp;</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
    <TR> 
      <TD height=20 vAlign=bottom width="100%"> <TABLE align=center border=1 borderColor=#999999 width="97%">
          <TBODY>
            <TR borderColor=#E8E8E8> 
              <TD height=20 vAlign=middle nowrap width="15%"><U>��Ϣ�ջ���ϸ����</U> </TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="15%">&nbsp;</TD>
              <TD height=20 vAlign=top width="35%">&nbsp;</TD>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"><input type="Radio" disabled name="rbPayInterest" <%=lPayInterestAccountID>0?"checked":""%> value="1"  onfocus="nextfield='lPayInterestAccountIDCtrlCtrl3';" checked>
					
					</td>
<!--					<input type="Hidden" name="lPayInterestAccountID" value="<%=lPayInterestAccountID%>">
<script language=javascript>			  
	showDisableAccountCtrl("strPayInterestAccountNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","��Ϣ���ڴ���˺�","width=20%","")
</script>-->
<%	
//��Ϣ���ڴ���˺�
		String strCtrlNameAcct = "lPayInterestAccountID";
		String strTitleAcct = "��Ϣ���ڴ���˺�";
		long lClientIDAcct = -1;
		long lAccountIDAcct = lPayInterestAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lPayInterestAccountID);
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;//�ո�����
		String strClientCtrlAcct = "";
		String strFirstTDAcct = "";
		String strSecondTDAcct = "";
		String[] strNextControlsAcct = {"rbPaySuretyFee[0]"};
		String strRtnClientIDCtrlAcct = "";
		String strRtnClientNoCtrlAcct = "";
		String strRtnClientNameCtrlAcct = "strPayInterestAccountName";
		
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
document.all.lPayInterestAccountIDCtrlCtrl1.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl2.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl3.disabled=true;
document.all.lPayInterestAccountIDCtrlCtrl4.disabled=true;
</script>
                    <TD width="100">&nbsp;</TD>
                    <TD height=20 vAlign=middle>��Ϣ�ͻ����ƣ�</TD>
                    <TD height=20 vAlign=top > <textarea name="strPayInterestAccountName"  class="box" disabled   rows=2 cols=30><%=NameRef.getClientNameByAccountID(lPayInterestAccountID)%></textarea> 
                    </TD>
                    
                  </tr>
                </table></td>
            </TR>
            <TR borderColor=#E8E8E8> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"><input type="Radio" disabled <%=lInterestBankID>0?"checked":""%> name="rbPayInterest" value="2" onfocus="nextfield='lInterestBankIDCtrl';">
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
		String[] strNextControlsBranch = {"rbPaySuretyFee[0]"};
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
document.all.lInterestBankIDCtrl.disabled = true;
</script>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    <TR> 
      <TD> <TABLE align="center" border="1" borderColor="#999999" height="40" width="97%">
          <TBODY>
            <TR borderColor="#E8E8E8"> 
              <TD height="20" vAlign="middle" nowrap width="15%"><U>�����Ѹ�����ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> <input type="Radio" disabled name="rbPaySuretyFee" <%=lPaySuretyAccountID>0?"checked":""%> value="1" onfocus="nextfield='dRealInterest';"  checked> </td>
					<input type="Hidden" name="lPaySuretyAccountID" value="<%=lPaySuretyAccountID%>">
					<script language=javascript>showDisableAccountCtrl("strPaySuretyFeeAccountNo","<%=NameRef.getAccountNoByID(lPaySuretyAccountID)%>","�����Ѹ�������˺�","width=20%","")
					</script>
			        <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">����ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strPaySuretyFeeAccountName" class="box" disabled bgcolor="#FF00" rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lPaySuretyAccountID)%></textarea> 
                    </TD>
                  </tr>
                </table></td>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10"> <input type="Radio" disabled <%=lSuretyBankID>0?"checked":""%> name="rbPaySuretyFee" value="2" onfocus="nextfield='lSuretyBankIDCtrl';"> 
					</td>
<%
//�����зŴ�
        String strCtrlNameBranchSF = "lSuretyBankID";
		String strTitleBranchSF = "������";
		long lBranchIDBranchSF = lSuretyBankID;
		String strBranchNameBranchSF = NameRef.getBankNameByID(lSuretyBankID);
		long lIsSingleBankBranchSF = 0;
		String strAccountCtrlBranchSF = "";
		String strFirstTDBranchSF = "";
		String strSecondTDBranchSF = "";
		String[] strNextControlsBranchSF = {"dRealInterest"};//next ���������Ϣ��ʵ��֧���ı���
		String strRtnBankAccountNoCtrlBranchSF = "";


	SETTMagnifier.createBranchCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		strCtrlNameBranchSF,
		strTitleBranchSF,
		lBranchIDBranchSF,
		strBranchNameBranchSF,
		lIsSingleBankBranchSF,
		strAccountCtrlBranchSF,
		strFirstTDBranchSF,
		strSecondTDBranchSF,
		strNextControlsBranchSF,
		strRtnBankAccountNoCtrlBranchSF);	
%>
<script language="javascript">
	document.all.lSuretyBankIDCtrl.disabled=true;
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>������������ϸ����</U> 
              </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="15%">&nbsp; </TD>
              <TD height="20" vAlign="top" width="35%">&nbsp; </TD>
            </TR>
            <TR borderColor="#E8E8E8"> 
              <td colspan="4"> <table>
                  <tr> 
                    <td width="10">&nbsp; </td>
					<input type="Hidden" name="lReceiveSuretyAccountID" value="<%=lReceiveSuretyAccountID%>">
                    <script language=javascript>
                    	showDisableAccountCtrl("strReceiveSuretyfeeAccountNo","<%=NameRef.getAccountNoByID(lReceiveSuretyAccountID)%>","�����������˺�","width=20%","")
					</script>
                    <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">�տ�ͻ����ƣ� </TD>
                    <TD height="20" vAlign="top"> <textarea name="strReceiveSuretyfeeAccountName" class="box" disabled bgcolor="#FF00" rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lReceiveSuretyAccountID)%></textarea> 
                    </TD>
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
                      createAmountCtrl("frmV053_6","dInterestCtrl","<%=DataFormat.formatAmountUseZero(dInterest)%>","");
					  document.frmV053_6.dInterestCtrl.disabled = true;
					</script>
					<input type="hidden" name="dInterest" value="<%=dInterest%>">
					</TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
						//createAmountCtrl("frmV053_6","dRealInterest","<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest",null,'<%=lCurrencyID%>',"onChange='doBoth();'");
						//document.all.dRealInterest.disabled = true;
						//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV053_6","dRealInterest",false,"<%=DataFormat.formatAmountUseZero(dRealInterest)%>","dRealCompoundInterest","",<%=lCurrencyID%>,"disabled=\"true\"","doBoth()");
					 </script>
					 </TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled class="box" name="lIsRemitInterest" value="1" <%=lIsRemitInterest>0?"checked":""%>>
					  �⻹ʣ����Ϣ</TD>
                  </tr>
                  <tr> 
                    <td>���� ������Ϣ��</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
					<script language="JavaScript">
                       createAmountCtrl("frmV053_6","dInterestReceiveAbleCtrl","<%=DataFormat.formatAmountUseZero(dInterestReceiveAble)%>","");
					   document.frmV053_6.dInterestReceiveAbleCtrl.disabled = true;
					</script></TD>
					<input type="hidden" name="dInterestReceiveAble" value="<%=dInterestReceiveAble%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                    createAmountCtrl("frmV053_6","dRealInterestReceiveAble","<%=dRealInterestReceiveAble%>","",null,'<%=lCurrencyID%>',"");
					  document.frmV053_6.dRealInterestReceiveAble.disabled = true;
					</script>
					 </TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>&nbsp; &nbsp;&nbsp;&nbsp;������Ϣ��</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                    createAmountCtrl("frmV053_6","dInterestIncomeCtrl","<%=DataFormat.formatAmountUseZero(dInterestIncome)%>","");
					document.frmV053_6.dInterestIncomeCtrl.disabled = true;
					</script></TD>
					<input type="hidden" name="dInterestIncome" value="<%=dInterestIncome%>">
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                    <script language="JavaScript">
                   	createAmountCtrl("frmV053_6","dRealInterestIncome","<%=DataFormat.formatAmountUseZero(dRealInterestIncome)%>","",null,'<%=lCurrencyID%>',"");
					document.frmV053_6.dRealInterestIncome.disabled = true;
					</script>
					</TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>������</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                    createAmountCtrl("frmV053_6","dCompoundInterestCtrl","<%=DataFormat.formatAmountUseZero(dCompoundInterest)%>","");
					document.frmV053_6.dCompoundInterestCtrl.disabled = true;
					</script></TD>
					
                    <TD height="20" vAlign="top"><input type="hidden" name="dCompoundInterest" value="<%=dCompoundInterest%>"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
           		 		//createAmountCtrl("frmV053_6","dRealCompoundInterest","<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest",null,'<%=lCurrencyID%>',"onChange=doChangeSum()");
           		 		//document.all.dRealCompoundInterest.disabled=true;
						//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV053_6","dRealCompoundInterest",false,"<%=DataFormat.formatAmountUseZero(dRealCompoundInterest)%>","dRealOverDueInterest","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeSum()");
					  </script>
					 	</TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled  class="box" name="lIsRemitCompoundInterest" value="1" <%=lIsRemitCompoundInterest>0?"checked":""%>>
                      �⻹ʣ�ิ��</TD>
                  </tr>
                  <tr> 
                    <td>���ڷ�Ϣ:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                    createAmountCtrl("frmV053_6","dOverDueInterestCtrl","<%=DataFormat.formatAmountUseZero(dOverDueInterest)%>","");
					document.frmV053_6.dOverDueInterestCtrl.disabled = true;
					</script></TD>
					
                    <TD height="20" vAlign="top"><input type="hidden" name="dOverDueInterest" value="<%=dOverDueInterest%>"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                   		//createAmountCtrl("frmV053_6","dRealOverDueInterest","<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","dRealSuretyFee",null,'<%=lCurrencyID%>',"onChange=doChangeSum()");
                   		//document.all.dRealOverDueInterest.disabled=true;
                   		//Modify by leiyang date 2007/07/18
						createAmountCtrl_standard("frmV053_6","dRealOverDueInterest",false,"<%=DataFormat.formatAmountUseZero(dRealOverDueInterest)%>","dRealSuretyFee","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeSum()");
					 </script>
					 
					 </TD>
                    <TD height="20" vAlign="top"><input type="checkbox"  disabled class="box" name="lIsRemitOverDueInterest" value="1" <%=lIsRemitOverDueInterest>0?"checked":""%>>
                 
                      �⻹ʣ�෣Ϣ</TD>
                  </tr>
                  <tr> 
                    <td>������:</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                    createAmountCtrl("frmV053_6","dSuretyFeeCtrl","<%=DataFormat.formatAmountUseZero(dSuretyFee)%>","");
					document.frmV053_6.dSuretyFeeCtrl.disabled = true;
					</script><input type="hidden" name="dSuretyFee" value="<%=dSuretyFee%>"></TD>
					
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                      //createAmountCtrl("frmV053_6","dRealSuretyFee","<%=DataFormat.formatAmountUseZero(dRealSuretyFee)%>","strAdjustInterestReason",null,'<%=lCurrencyID%>',"onChange=doChangeSum()");
                      //document.all.dRealSuretyFee.disabled=true;
                      //Modify by leiyang date 2007/07/18
					  createAmountCtrl_standard("frmV053_6","dRealSuretyFee",false,"<%=DataFormat.formatAmountUseZero(dRealSuretyFee)%>","strAdjustInterestReason","",<%=lCurrencyID%>,"disabled=\"true\"","doChangeSum()");
					  </script></TD>
                    <TD height="20" vAlign="top"><input type="checkbox" disabled  class="box" name="lIsRemitSuretyFee" value="1" <%=lIsRemitSuretyFee>0?"checked":""%>>
                      �⻹ʣ�ൣ����</TD>
                  </tr>
                  <tr> 
                    <td>��Ϣ���úϼƣ�</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%>  
                      <script language="JavaScript">
                     createAmountCtrl("frmV053_6","dSumFee","<%=UtilOperation.Arith.round((dInterest+dCompoundInterest+dOverDueInterest+dSuretyFee),2)%>","");
					 document.frmV053_6.dSumFee.disabled = true;
					</script></TD>
                    <TD height="20" vAlign="top"><%=sessionMng.m_strCurrencySymbol%>  
                     <script language="JavaScript">
                      createAmountCtrl("frmV053_6","dRealSumFee","<%=UtilOperation.Arith.round((dRealInterest+dRealCompoundInterest+dRealOverDueInterest+dRealSuretyFee),2)%>","");
					  document.frmV053_6.dRealSumFee.disabled = true;
					 </script></TD>
                    <TD height="20" vAlign="top">&nbsp;</TD>
                  </tr>
                  <tr> 
                    <td>�⻹ԭ��</td>
                    <TD  height="20"  valign="top"><textarea name="strAdjustInterestReason" disabled class="box"   rows="2" cols="30" onfocus="nextfield='lCapitalAndInterstDealway[0]'"><%=strAdjustInterestReason%></textarea></TD>
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
            
                    <TD width="250"><input type="radio" disabled name="lCapitalAndInterstDealway" value="1" onfocus="nextfield='lCapitalAndInterstDealway[1]'">
					
                      ���ܴ��� </TD>
                    <TD vAlign="middle">&nbsp;</TD>
                    <TD height="20" vAlign="middle" width="250"><input type="radio" disabled name="lCapitalAndInterstDealway" value="2" checked onfocus="nextfield='submitfunction'">
                      �ֱʴ���</TD>
                    <TD height="20" vAlign="top">&nbsp; </TD>
                  </tr>
                </table></td>
            </TR>
          </TBODY>
        </TABLE></TD>
    </TR>
    				<TR>
					<TD height="119" vAlign="top" width="100%">
						<TABLE align="center" height="8" width="97%">
							<TBODY>
								<TR vAlign="middle">
									<td colspan="6">
										<table width="350">
											<td width="10">
												<input type="Checkbox" disabled name="cbPreFormID" value="1" onFocus="nextfield='lPreFormIDCtrl'" <%=lPreFormID>0?"checked":""%>>
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
	long lAheadPayFormStatusIDs = 0;//Ӧ�ø�Ϊ1��Ϊҵ����ʱʹ��
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
								</TR>
								<TR vAlign="middle">
									<TD height="20" width="15%">	������:
									</TD>
									<TD height="20" width="20%"><%=sessionMng.m_strCurrencySymbol%> 
	<!--*****************************************************************************-->
<script language="javascript">// �������ؼ�
	createAmountCtrl("frmV053_1","dAmount","<%=DataFormat.formatDisabledAmount(dAmount)%>","tsInterestStart");
	document.all.dAmount.disabled=true;
</script><!--*****************************************************************************-->
									</TD>
									<TD height="32" width="10%">&nbsp;	
									</TD>
									<TD height="32" width="15%">	��Ϣ��:
									</TD>
									<TD height="32" width="20%">
										<INPUT type="Text" class="box" disabled name="tsInterestStart" value="<%=DataFormat.getDateString(tsInterestStart)%>" onFocus="nextfield='lAbstractIDCtrl';">&nbsp;
									</TD>
									<TD height="32" width="20%">&nbsp;	
									</TD>
								</TR>
								<TR vAlign="middle">
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
									<TD height="30" width="10%">&nbsp;	
									</TD>
									<TD height="30" width="25%">&nbsp;
										
									</TD>
									<TD height="30" width="10%">&nbsp;	
									</TD>
									<TD height="30" width="20%">&nbsp;	
									</TD>
								</TR>
							</TABLE>
						</td>
					</tr>
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
			<INPUT class=button name=Submit3 onclick="doCheck(frmV053_6);" type=button value=" �� �� "> 
			<%
			}
			else if (lRecordStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV053_6);" type=button value=" ȡ������ "> 			
			<%
			}
			%>
			<INPUT class=button name=Submit322 onclick="doBack(frmV053_6);" type=button value=" �� �� "> 
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
          <TD height=25 width="9%"><%=(lCheckUserID > 0 && lStatusID == SETTConstant.TransactionStatus.CHECK ? DataFormat.formatDate(tsExecute) : "&nbsp;")%></TD>
          <TD height=25 width="5%">״̬:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR>
			
          </TBODY>
        </TABLE></TD></TR></TBODY></TABLE></form>
<script language="JavaScript">
firstFocus(document.frmV053_6.strCheckAbstract);
//setSubmitFunction("<%if (lRecordStatusID==SETTConstant.TransactionStatus.SAVE)out.print("doCheck"); else if (lRecordStatusID==SETTConstant.TransactionStatus.CHECK) out.print("doCancelCheck");%>(frmV053_6)");
setFormName("frmV053_6"); 
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
		form.action = "../view/v053_5.jsp";
		<%
		}
		else
		{
		%>
		form.lStatusID.value="<%=SETTConstant.TransactionStatus.CHECK%>";
		form.action = "../control/c052.jsp";
		form.strSuccessPageURL.value = '../view/v055.jsp';
		form.strFailPageURL.value = '../view/v053_6.jsp';
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
		window.open( "../accountinfo/a927-v.jsp?lID="+<%=lID%>+"&strTransNo=<%=strTransNo%>&lTransactionTypeID=<%=SETTConstant.TransactionType.MULTILOANRECEIVE%>&strSuccessPageURL='../../tran/loan/view/v053_6.jsp'&strFailPageURL='../../tran/loan/view/v053_6.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v053_5.jsp';
	form.strFailPageURL.value = '../view/v053_6.jsp';
	
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
	form.strFailPageURL.value = '../view/v053_6.jsp';
	
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
