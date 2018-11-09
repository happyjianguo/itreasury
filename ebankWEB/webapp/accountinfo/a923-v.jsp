<%--
 页面名称 ：v052_6.jsp
 页面功能 : 多笔贷款收回-委托贷款收回-复核/取消复核页面
 作    者 ：Barry
 日    期 ：2003年12月5日
 特殊说明 ：实现操作说明：
 修改历史 ：
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
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}

		OBHtml.showOBHomeHead(out,sessionMng,Env.getClientName(),Constant.YesOrNo.NO);
/**
 * 定义公共变量
 */
long lOfficeID 									= sessionMng.m_lOfficeID;
long lCurrencyID 								= sessionMng.m_lCurrencyID;
String strFormName 								= "frmV052_6";

long lInputUserID								= -1;									//录入人
long lCheckUserID								= sessionMng.m_lUserID;					//复核人
Timestamp tsExecute  							= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
long lTransactionTypeID							= SETTConstant.TransactionType.MULTILOANRECEIVE;													//交易类型

String strTransNo								= "";									//交易号
/**
 * 页面辅助变量
 */
String strAction 								= null;
String strActionResult 							= Constant.ActionResult.FAIL;

/**
 * 页面二参数
 */
long lID										= -1;									//ID
long lSubAccountID 								= -1;									//子账户ID
long lConsignAccountID 							= -1;									//委托存款账号，委托专用
long lConsignDepositAccountID 					= -1;									//委托放活期存款账号，委托专用
long lPayInterestAccountID 						= -1;									//付利息账号
long lInterestBankID 							= -1;									//付利息银行ID
long lReceiveInterestAccountID 					= -1;									//利息收入账户ID，委托专用
long lCommissionAccountID 						= -1;									//手续费收回账号，委托专用
long lCommissionBankID 							= -1;									//手续费收回银行，委托专用
long lStatusID									= -1;									//状态
long lRecordStatusID							= -1;									//记录状态
String strCheckAbstract							= "";									//复核摘要
Timestamp tsInput								= null;									//录入时间

/**
 * 链接查找,和复核的参数
 */
long lDesc										= -1;									//排序方式
long lOrderByCode								= -1;									//排序字段
String strTempTransNO							= "";									//临时账号
Timestamp tsModify								= null;									//修改时间

/**
 * 利息应当支付值
 */
double dInterest 								= 0.0;    
double dInterestTax 							= 0.0;
double dCompoundInterest 						= 0.0;
double dOverDueInterest 						= 0.0;
double dCommission 								= 0.0;
 
/**
 * 实际支付利息
 */
double dRealInterest 							= 0.0;
double dRealInterestTax 						= 0.0;
double dRealCompoundInterest 					= 0.0;
double dRealOverDueInterest 					= 0.0;
double dRealCommission 							= 0.0;

/**
 * 税率
 */

double dInterestTaxRate 						= 0.0;

/**
 * 是否免还利息
 */
long lIsRemitInterest 							= -1;
long lIsRemitCompoundInterest 					= -1;
long lIsRemitOverDueInterest 					= -1;
long lIsRemitCommission 						= -1;

/**
 * 免还原因
 */
String strAdjustInterestReason 					= "";

/**
 * 本金、利息处理办法
 */
long lCapitalAndInterstDealway 			= -1;

/**
 * 上次结息日,从子账户带出来
 */
Timestamp tsLatestInterestClear					= null;										//上次结息日

/**
 * 为打印添加的利息信息
 */
	   
Timestamp tsCompoundInterestStart 				= null;										//复利起息日
double dCompoundAmount 							= 0.0;										//复利本金
double dCompoundRate 							= 0.0;										//复利利率

Timestamp tsOverDueStart 						= null;										//逾期罚息起息日
double dOverDueAmount 							= 0.0;										//逾期罚息本金
double dOverDueRate 							= 0.0;										//逾期罚息利率

/**
 * 页面一参数
 */

long lMultiLoanType								= -1;										//多笔贷款收回类型
String strDeclarationNo 						= "";										//报单号
long lClientID									= -1;										//客户ID
long lLoanAccountID 							= -1;										//贷款账号
long lLoanContractID 							= -1;										//合同号
long lLoanNoteID 								= -1;										//放款通知单号
Timestamp tsDateStart 							= null;										//放款通知单开始日期
double dBalance 								= 0.0;										//子账户余额
long lFreeFormID 								= -1;										//免还通知单
long lPreFormID 								= -1;										//提前还款通知单
double dAmount 									= 0.0;										//金额
Timestamp tsInterestStart 						= tsExecute;								//起息日
long lAbstractID 								= -1;										//摘要ID
String strAbstract 								= "";										//摘要

//-----------------------页面一参数----------------------

 
String strTemp = null;
/**
 * 从Request中获得初始化对象信息
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
 * 获取页面一的业务数据
 */
strTemp = (String)request.getAttribute("lFreeFormID");
if (strTemp != null && strTemp.trim().length() > 0)
{
	lFreeFormID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lMultiLoanType");
if (strTemp != null && strTemp.trim().length() > 0)											//多笔贷款收回
{
	lMultiLoanType = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("strDeclarationNo");
if (strTemp != null && strTemp.trim().length() > 0)											//报单号
{
	strDeclarationNo = strTemp.trim();
}

strTemp = (String)request.getAttribute("lClientID");
if (strTemp != null && strTemp.trim().length() > 0)											//客户ID
{
	lClientID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//贷款账号
{
	lLoanAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanContractID");
if (strTemp != null && strTemp.trim().length() > 0)											//合同号
{
	lLoanContractID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lLoanNoteID");
if (strTemp != null && strTemp.trim().length() > 0)											//放款通知单
{
	lLoanNoteID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("tsDateStart");
if (strTemp != null && strTemp.trim().length() > 0)											//开始时间
{
 	tsDateStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dBalance");
if (strTemp != null && strTemp.trim().length() > 0)											//子账户余额
{
 	dBalance = DataFormat.parseNumber(strTemp.trim());
}

strTemp = (String)request.getAttribute("lFreeFormID");
if (strTemp != null && strTemp.trim().length() > 0)											//免还通知单
{
 	lFreeFormID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lPreFormID");
if (strTemp != null && strTemp.trim().length() > 0)											//提前还款通知单
{
 	lPreFormID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("dAmount");											//金额
if (strTemp != null && strTemp.trim().length() > 0)
{
	dAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("tsInterestStart");
if (strTemp != null && strTemp.trim().length() > 0)											//起息日
{
	tsInterestStart = DataFormat.getDateTime(strTemp);
}

strTemp = (String)request.getAttribute("lAbstractID");
if (strTemp != null && strTemp.trim().length() > 0)											//摘要ID
{
	lAbstractID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lAbstractCtrl");
if (strTemp != null && strTemp.trim().length() > 0)											//摘要文本
{
	strAbstract = strTemp;
}
//获取页面二的业务数据

strTemp = (String)request.getAttribute("lConsignAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//委托存款账号
{
	lConsignAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lID");
if (strTemp != null && strTemp.trim().length() > 0)											//ID
{
	lID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lSubAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//子账户ID
{
	lSubAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lConsignDepositAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//委托活期存款账号
{
	lConsignDepositAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lPayInterestAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//付息账号
{
	lPayInterestAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lInterestBankID");
if (strTemp != null && strTemp.trim().length() > 0)											//付利息银行ID
{
	lInterestBankID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//利息收入账户
{
	lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lCommissionAccountID");								//付手续费账户ID
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCommissionAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lCommissionBankID");								//付手续费银行ID
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCommissionBankID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("lStatusID");										//状态
if (strTemp != null && strTemp.trim().length() > 0)
{
	lStatusID = Long.valueOf(strTemp).longValue();
}

lRecordStatusID = lStatusID;																//如果没有改变记录状态和库里的记录状态相等.

strTemp = (String)request.getAttribute("lRecordStatusID");									//记录状态
if (strTemp != null && strTemp.trim().length() > 0)
{
	lRecordStatusID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("strCheckAbstract");									//记录状态
if (strTemp != null && strTemp.trim().length() > 0)
{
	strCheckAbstract = strTemp.trim();
}

strTemp = (String)request.getAttribute("tsInput");											//录入时间
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsInput = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lDesc");											//排序方式
if (strTemp != null && strTemp.trim().length() > 0)
{
	lDesc = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lOrderByCode");										//排序字段
if (strTemp != null && strTemp.trim().length() > 0)
{
	lOrderByCode = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("strTempTransNO");									//临时交易号
if (strTemp != null && strTemp.trim().length() > 0)
{
	strTempTransNO = strTemp.trim();
}
strTemp = (String)request.getAttribute("tsModify");											//修改时间
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsModify = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("tsExecute");										//执行日
if (strTemp != null && strTemp.trim().length() > 0)
{
	tsExecute = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lInputUserID");										//录入人
if (strTemp != null && strTemp.trim().length() > 0)
{
	lInputUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lCheckUserID");										//复核人
if (strTemp != null && strTemp.trim().length() > 0)
{
	lCheckUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("dInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//应付利息
{
	dInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestTax");
if (strTemp != null && strTemp.trim().length() > 0)											//应付利息税
{
	dInterestTax = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//应付复利
{
	dCompoundInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//应付逾期罚息
{
	dOverDueInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//应付手续费
{
	dCommission = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//实付利息
{
	dRealInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestTax");
if (strTemp != null && strTemp.trim().length() > 0)											//实际支付利息税费
{
	dRealInterestTax = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//实付复利
{
	dRealCompoundInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//实付逾期罚息
{
	dRealOverDueInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//实际支付手续费
{
	dRealCommission = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestTaxRate");
if (strTemp != null && strTemp.trim().length() > 0)											//实际支付利息税率 <<<
{
	dInterestTaxRate = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("lIsRemitInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//是否免还利息
{
	lIsRemitInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitCompoundInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//是否免还复利
{
	lIsRemitCompoundInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitOverDueInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//是否免还逾期罚息
{
	lIsRemitOverDueInterest = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lIsRemitCommission");
if (strTemp != null && strTemp.trim().length() > 0)											//是否免还手续费
{
	lIsRemitCommission = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("strAdjustInterestReason");
if (strTemp != null && strTemp.trim().length() > 0)											//面还原因
{
	strAdjustInterestReason = strTemp;
}
		
strTemp = (String)request.getAttribute("lCapitalAndInterstDealway");
if (strTemp != null && strTemp.trim().length() > 0)											//本金/利息处理方式
{
	lCapitalAndInterstDealway = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsLatestInterestClear");
if (strTemp != null && strTemp.trim().length() > 0)											//上次结息日
{
	tsLatestInterestClear = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("tsCompoundInterestStart");
if (strTemp != null && strTemp.trim().length() > 0)											//复利起息日
{
	tsCompoundInterestStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dCompoundAmount");
if (strTemp != null && strTemp.trim().length() > 0)											//复利本金
{
	dCompoundAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dCompoundRate");
if (strTemp != null && strTemp.trim().length() > 0)											//复利利率
{
	dCompoundRate = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("tsOverDueStart");
if (strTemp != null && strTemp.trim().length() > 0)											//逾期罚息起息日
{
	tsOverDueStart = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("dOverDueAmount");
if (strTemp != null && strTemp.trim().length() > 0)											//逾期罚息本金
{
	dOverDueAmount = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dOverDueRate");
if (strTemp != null && strTemp.trim().length() > 0)											//逾期罚息利率
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

<!--复核,取消需要的参数-->
	<input name="lID" type="Hidden" value="<%=lID%>">
	<input name="strTempTransNO" type="Hidden" value="<%=strTempTransNO%>">
	<input name="tsModify" type="Hidden" value="<%=tsModify%>">
	<input name="lCheckUserID" type="Hidden" value="<%=lCheckUserID%>">
	<input name="tsExecute" type="Hidden" value="<%=tsExecute%>">
<!--复核,取消需要的参数-->
<!--链接查找-->
	<input name="lMultiLoanType" type="hidden" value="<%=lMultiLoanType%>">
	<input name="lStatusID" type="Hidden" value="<%=lRecordStatusID%>">
	<input name="lDesc" type="Hidden" value="<%=lDesc%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
<!--链接查找-->
	<input name="tsLatestInterestClear" type="hidden" value="<%=DataFormat.getDateString(tsLatestInterestClear)%>"> <!--上次结息日-->
	<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<!--为了同时有其他人更改当前记录状态所保留的-->
<input type="hidden" name="lRecordStatusID" value="<%=lRecordStatusID%>">
<!--为了同时有其他人更改当前记录状态所保留的-->
<TABLE border=0 class=top height=20 width="99%">
  <TBODY>  
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>业务复核 —— 多笔贷款收回</B></TD>
  </TR>
  <TR>
				<tr>
					<td align='center'>
						<table width="97%">
							<tr>
								<td width='80'>收付方向:</td>
								<td align='left'>
									<%SETTConstant.MultiLoanType.showList(out,"lMultiLoanType",0,3,false,false,"onchange='changeMultiLoanType(frmV052_6)'disabled onchange='changeMultiLoanType(frmV052_6)' onfocus=nextfield='strDeclarationNo'",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;
								</td>
								<td align="right">
									银行报单号:&nbsp;&nbsp;&nbsp;
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
          <TD height=18 vAlign=middle width="17%" nowrap><U>委托贷款详细资料</U> </TD>
          <TD height=18 vAlign=top width="34%" nowrap>&nbsp;</TD>
          <TD height=18 width="17%" nowrap>&nbsp;</TD>
          <TD height=18 width="32%" nowrap>&nbsp;</TD>
        </TR>
        <TR borderColor=#E8E8E8>
<%
//还款客户编号放大镜
		String strCtrlNameC = "lClientID";
		String strTitleC = "委托贷款客户编号";
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
        	<TD height=20 width="17%" nowrap>委托贷款客户名称: </TD>
          <TD height=20 width="32%" nowrap> 
                <textarea name="strLoanClientName" class="box" disabled  rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
          </TD>
        </TR>
        <TR borderColor=#E8E8E8> 
	<%
//委托贷款账号放大镜   	
		String strCtrlNameTrustLoanAcct = "lLoanAccountID";
		String strTitleTrustLoanAcct = "委托贷款账号";
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
//合同号放大镜
	String strCtrlNameContract = "lLoanContractID";
	String strTitleContract = "合同号";
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
//放款通知单据号放大镜
	String strCtrlNamePayForm = "lLoanNoteID";
	String strTitlePayForm = "放款通知单据号";
	long lLoanContractIDPayForm = lLoanContractID;
	long lPayFormID = lLoanNoteID;
	String strPayFormNo = NameRef.getPayFormNoByID(lLoanNoteID);
	long lPayFormTypeID = 2;									//放款通知单类型(查询条件:1,信托；2，委托)
	long lPayFormStatusIDs = 3;										//放款通知单状态(内部状态：0,全部 1,发放业务处理 2,发放复核 3,收回业务处理 4,收回复核
	String strContractCtrlPayForm = "lLoanContractID";
	String strFirstTDPayForm = "";
	String strSecondTDPayForm = "";
	String[] strNextControlsPayForm = { "dAmount" };
	String strRtnStartDateCtrl = "tsDateStart";
	String strBalance = "hidBalance";			//余额

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
              <TD height=20 vAlign=middle nowrap width="15%"><U>委托方详细资料</U> </TD>
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
					showDisableAccountCtrl("lConsignAccountID","<%=NameRef.getAccountNoByID(lConsignAccountID)%>","委托存款账号","width=20%","")
					</script>
                    <TD height="20" width="135">&nbsp;</TD>
                    <TD height=20 vAlign=middle>委托存款客户名称：</TD>
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
		//委托方活期存款账号		
		String strCtrlNameAcct = "lConsignDepositAccountID";
		String strTitleAcct = "委托方活期存款账号";
		long lClientIDAcct = NameRef.getClientIDByAccountID(lConsignDepositAccountID);
		long lAccountIDAcct = lConsignDepositAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lConsignDepositAccountID);;
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;//收付类型
		String strClientCtrlAcct = "";//待修改
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
                    <TD height=20 vAlign=middle width="110">活期客户名称：</TD>
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
              <TD height=20 vAlign=middle nowrap width="15%"><U>利息还款详细资料</U> </TD>
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
		//利息付出账号放大镜		
	
	String[] strNextControlsAcctPayInterest = {"lReceiveInterestAccountIDCtrlCtrl3"};//待修改
SETTMagnifier.createAccountCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		strFormName,
		"lPayInterestAccountID",
		"利息付出账号",
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
                    <TD height=20 vAlign=middle>利息付出客户名称：</TD>
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
//开户行放大镜
		 String strCtrlNameBranch = "lInterestBankID";
		 String strTitleBranch = "开户行";
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>利息收入详细资料</U> 
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
		"利息收入账号",
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
                    <TD height="20" vAlign="middle">利息收入客户名称： </TD>
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>手续费收回详细资料</U> 
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
	
		//手续费付出账号放大镜		
		String strCtrlNamePC = "lCommissionAccountID";
		String strTitlePC = "手续费付出账号";
		long lClientIDPC = NameRef.getClientIDByAccountID(lCommissionAccountID);
		long lAccountIDPC = lCommissionAccountID;
		String strAccountNoPC = NameRef.getAccountNoByID(lCommissionAccountID);;
		long lAccountGroupTypeIDPC = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDPC = -1;
		long lReceiveOrPayPC = -1;//收付类型
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
                    <TD height="20" vAlign="middle">手续费付出客户名称： </TD>
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
//开户行放大镜
        String strCtrlNameBranchPC = "lCommissionBankID";
		String strTitleBranchPC = "开户行";
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
                    <TD  height="21"  valign="top"width="250">应当支付 </TD>
                    <TD height="21" vAlign="top" width="250">实际支付</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
                  <tr> 
                    <td>贷款利息：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                      createAmountCtrl("frmV052_6","dInterestCtrl","<%=DataFormat.formatAmountUseZero(dInterest)%>","");
					  document.frmV052_6.dInterestCtrl.disabled=true;
					</script> </TD>
					<%Log.print("应付利息:"+dInterest);%>
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
                      免还剩余利息</TD>
                  </tr>
                 
                 
                  <tr> 
                    <td>复利：</td>
                    <TD  height="20"  valign="top"><%=sessionMng.m_strCurrencySymbol%> 
                      <script language="JavaScript">
                    createAmountCtrl("frmV052_6","dCompoundInterestCtrl","<%=DataFormat.formatAmountUseZero(dCompoundInterest)%>","");
					document.frmV052_6.dCompoundInterestCtrl.disabled=true;
					</script></TD>
					<%Log.print("应付复利:"+dInterest);%>
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
                      免还剩余复利</TD>
                  </tr>
                  <tr> 
                    <td>逾期罚息:</td>
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
                      免还剩余罚息</TD>
                  </tr>
				   <tr> 
                    <td>利息税费：</td>
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
                    <td>手续费:</td>
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
                      免还剩余手续费</TD>
                  </tr>
                  <tr> 
                    <td>利息费用合计：</td>
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
                    <td>免还原因：</td>
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>本金/利息处理办法</U> 
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
                      汇总处理 </TD>
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
                      分笔处理</TD>
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
//提前还款通知单放大镜
	String strCtrlNameAheadPayForm = "lPreFormID";
	String strTitleAheadPayForm = "提前还款通知单";
	long lLoanContractIDAheadPayForm = lLoanContractID;
	long lPreFormIDF = lPreFormID;
	String strAheadPayFormNo = NameRef.getAheadPayFormNoByID(lPreFormID);
	long lAheadPayFormTypeID = 1;
	String strContractCtrlAheadPayForm = "lLoanContractID";
	long lAheadPayFormStatusIDs = 1;//应该改为1：为业务处理时使用
	String strFirstTDAheadPayForm = "";
	String strSecondTDAheadPayForm = "";
	String[] strNextControlsAheadPayForm = { "submitfunction" };
	String strRtnAmountCtrl = "dAmount";
	String strRtnClientNoCtrl = "lClientIDCtrl";				//客户号
	String strRtnContractNoCtrl = "lLoanContractIDCtrl";			//合同号
	String strRtnPayFormNoCtrl = "lLoanNoteIDCtrl";			//放款通知单号

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
		//免还通知单放大镜		
	 String  strCtrlNameFF = "lFreeFormID";//放大镜主控件名称
	 String  strTitleFF = "免还通知单"; //放大镜描述
	 long   lFreeFormIDFF = lFreeFormID; //免还通知单ID(初识值)
	 String  strFreeFormNoFF = NameRef.getFreeFormNoByID(lFreeFormID); //免还通知单号(初识值)
	 long lTypeID	= 2;		//复核
	 String  strFirstTDFF = ""; //第一个TD的属性
	 String  strSecondTDFF = ""; //第二个TD的属性
	 String[] strNextControlsFF = {"submitfunction"}; //下一个（或多个）获得焦点的控件
	 String  strRtnFreeAmountCtrlFF = ""; //返回值（免还金额）对应的控件名
	 String  strRtnFreeInterestCtrlFF = ""; //返回值（免还利息）对应的控件名
	 String  strRtnClientIDCtrlFF = ""; //返回值（贷款客户ID）对应的控件名
	 String  strRtnClientNoCtrlFF = ""; //返回值（贷款客户编号）对应的控件名
	 String  strRtnClientNameCtrlFF = ""; //返回值（贷款客户名称）对应的控件名
	 String  strRtnContractIDCtrlFF = ""; //返回值（合同ID）对应的控件名
	 String  strRtnContractNoCtrlFF = ""; //返回值（合同名称）对应的控件名
	 String  strRtnPayFormIDCtrlFF = ""; //返回值（放款通知单ID）对应的控件名
	 String  strRtnPayFormNoCtrlFF = ""; //返回值（放款通知单编号）对应的控件名
	 String  strRtnPFStartDateCtrlFF = ""; //返回值（放款通知单起始日期）对应的控件名
	 
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
          <TD borderColor=#E8E8E8 height=20 width="14%" nowrap>还款金额:</TD>
          <TD borderColor=#E8E8E8 height=20 width="20%" nowrap><%=sessionMng.m_strCurrencySymbol%>  
<script language="javascript">
// 创建金额控件
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

          <TD height=20 width="14%" nowrap>起息日: </TD>
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
//摘要放大镜
		String strCtrlNameAbstract = "lAbstractID";
		String strTitleAbstract = "摘要";
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
	            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
<%
	}
	else
	{
%>
			<%
			if (lRecordStatusID == SETTConstant.TransactionStatus.SAVE)
			{
			%>
			<INPUT class=button name=Submit3 onclick="doCheck(frmV052_6);" type=button value=" 复 核 "> 
			<%
			}
			else if (lRecordStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV052_6);" type=button value=" 取消复核 "> 			
			<%
			}
			%>
			<INPUT class=button name=Submit322 onclick="doBack(frmV052_6);" type=button value=" 返 回 "> 
<%
	}
%>
            </DIV></TD></TR></TABLE></TD></TR>
            
                        	  <TR>
    <TD colSpan=2 height=20 vAlign=top width="100%">
      <HR>
      <TABLE align=center border=0 height=22 width="97%">
        <TR vAlign=middle>
          <TD height=25 width="8%">复核备注:</TD>
          <TD height=25 vAlign=top width="19%"><INPUT class=box 
            name="strCheckAbstract" value="<%=strCheckAbstract%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">录入人:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(lInputUserID)%></TD>
          <TD height=25 width="8%">录入日期:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(tsInput)%></TD>
          <TD height=25 width="6%">复核人:</TD>
          <TD height=25 width="7%"><%=lStatusID==SETTConstant.TransactionStatus.CHECK ? NameRef.getUserNameByID(lCheckUserID) : ""%></TD>
          <TD height=25 width="8%">复核日期:</TD>
          <TD height=25 width="9%"><%=(lCheckUserID > 0 && lStatusID==SETTConstant.TransactionStatus.CHECK ? DataFormat.formatDate(tsExecute) : "&nbsp;")%></TD>
          <TD height=25 width="5%">状态:</TD>
          <TD height=25 width="5%"><%=SETTConstant.TransactionStatus.getName(lStatusID)%></TD></TR></TABLE></TD></TR></TBODY>
            
			</table></form>

<script language="JavaScript">
firstFocus(document.frmV052_6.strCheckAbstract);
//setSubmitFunction("<%if (lRecordStatusID==SETTConstant.TransactionStatus.SAVE)out.print("doCheck"); else if (lRecordStatusID==SETTConstant.TransactionStatus.CHECK) out.print("doCancelCheck");%>(frmV052_6)");
setFormName("frmV052_6");
</script>
<script language="JavaScript">
var isSubmited = false;//标识是否已提交请求

function doBack(form)
{

	if (confirm("是否返回？"))
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
	if (confirm("是否打印?")){
		window.open( "../accountinfo/a927-v.jsp?lID="+<%=lID%>+"&strTransNo=<%=strTransNo%>&lTransactionTypeID=<%=SETTConstant.TransactionType.MULTILOANRECEIVE%>&strSuccessPageURL='../../tran/loan/view/v052_6.jsp'&strFailPageURL='../../tran/loan/view/v052_6.jsp'&lReturn=<%=lOBReturn%>");
	}
}

function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v052_5.jsp';
	form.strFailPageURL.value = '../view/v052_6.jsp';
	
	if (confirm("是否复核？"))
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
	if (confirm("是否取消复核？"))
	{
		form.action = "../control/c054.jsp";
		showSending();
		form.submit();
	}
}
function allFields()
{	
	this.aa = new Array("strCheckAbstract","复核备注","string",1);
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