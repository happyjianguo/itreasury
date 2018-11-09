<%--
 页面名称 ：v053_6.jsp
 页面功能 : 多笔贷款收回-自营贷款收回-复核/取消复核页面
 作    者 ：Barry
 日    期 ：2003年12月5日
 特殊说明 ：实现操作说明：
 修改历史 ：
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
    var tempRealInterestReceiveAble = reverseFormatAmount(document.frmV053_6.dRealInterestReceiveAble.value);//计提
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
		alert("请输入正确的数值");
		document.frmV053_6.dRealInterest.value = "0.00";
		document.frmV053_6.dRealInterest.focus();
		doDisPatch();
		return false;
	}
	
	
	if (parseFloat(tempRealInterest) > parseFloat(tempInterest))
	{
		alert("实际支付贷款利息大于应当支付！");
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
		alert("请输入正确的数值");
		document.frmV053_6.dRealInterest.value = "0.00";
		document.frmV053_6.dRealInterest.focus();
		return false;
	}
	
	
	
	if (!isFloat(tempCompoundInterest))
	{
		alert("请输入正确的数值");
		document.frmV053_6.dRealCompoundInterest.value = "0.00";
		document.frmV053_6.dRealCompoundInterest.focus();
		return false;
	}
	
	if (!isFloat(tempOverDueInterest))
	{
		alert("请输入正确的数值");
		document.frmV053_6.dRealOverDueInterest.value = "0.00";
		document.frmV053_6.dRealOverDueInterest.focus();
		return false;
	}
	
	if (!isFloat(tempSuretyFee))
	{
		alert("请输入正确的数值");
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
String strFormName 								= "frmV053_6";

long lID 										= -1;										//为修改和删除
String strTempTransNO							= "";										//临时交易号
Timestamp tsModify								= null;										//修改时间
long lInputUserID								= -1;						//录入人
long lCheckUserID								= sessionMng.m_lUserID;						//复合人
Timestamp tsExecute  							= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));
long lTransactionTypeID							= SETTConstant.TransactionType.MULTILOANRECEIVE;													//交易类型
long lStatusID									= -1;										//状态,用于链接查找的默认查询
long lRecordStatusID							= -1;										//记录状态
long lDesc 										= Constant.PageControl.CODE_ASCORDESC_DESC;				//排序方式
long lOrderByCode								= -1;													//排序字段

Timestamp tsCheck								= null;													//复核时间
Timestamp tsInput								= null;													//录入时间

String strTransNo								= "";													//交易号
/**
 * 页面辅助变量
 */
String strAction 								= null;
String strActionResult 							= Constant.ActionResult.FAIL;



/**
 * 页面二参数
 */
long lSubAccountID 								= -1;										//子账户ID
long lPayInterestAccountID 						= -1;										//付息账号,自营/委托公用
long lInterestBankID 							= -1;										//付息银行ID,自营/委托公用
long lPaySuretyAccountID 						= -1;										//负担保费账号
long lSuretyBankID 								= -1;										//负担保费银行ID
long lReceiveSuretyAccountID 					= -1;										//担保费收入账号


/**
 * 利息应当支付值
 */
double dInterest 								= 0.0;    
double dInterestReceiveAble 					= 0.0;										//计提利息
double dInterestIncome 							= 0.0;										//本次利息
double dCompoundInterest 						= 0.0;
double dOverDueInterest 						= 0.0;
double dSuretyFee 								= 0.0;										//担保费
 
/**
 * 实际支付利息
 */
double dRealInterest 							= 0.0;
double dRealInterestReceiveAble 					= 0.0;										//实际支付计提利息
double dRealInterestIncome 						= 0.0;										//实际支付本次利息
double dRealCompoundInterest 					= 0.0;
double dRealOverDueInterest 					= 0.0;
double dRealSuretyFee 							= 0.0;										//实际支付担保费

/**
 * 是否免还利息
 */
long lIsRemitInterest 							= -1;
long lIsRemitCompoundInterest 					= -1;
long lIsRemitOverDueInterest 					= -1;
long lIsRemitSuretyFee 							= -1;

/**
 * 免还原因
 */
String strAdjustInterestReason 					= "";

/**
 * 本金、利息处理办法
 */
long lCapitalAndInterstDealway 					= -1;

/**
 * 上次结息日,从子账户带出来
 */
Timestamp tsLatestInterestClear					= null;											//上次结息日

/**
 * 为打印添加的利息信息
 */
	   
Timestamp tsCompoundInterestStart 				= null;					//复利起息日
double dCompoundAmount 							= 0.0;					//复利本金
double dCompoundRate 							= 0.0;					//复利利率

Timestamp tsOverDueStart 						= null;					//逾期罚息起息日
double dOverDueAmount 							= 0.0;					//逾期罚息本金
double dOverDueRate 							= 0.0;					//逾期罚息利率

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
double dSavedAmount								= 0.0;										//保存的金额
Timestamp tsInterestStart 						= tsExecute;								//起息日
long lAbstractID 								= -1;										//摘要ID
String strAbstract 								= "";										//摘要
String strCheckAbstract							= "";										//复核摘要

//-----------------------页面一参数----------------------

 
String strTemp = null;
/**
 * 从Request中获得初始化对象信息
 */
SubLoanAccountDetailInfo tempSubLoanAccountDetailInfo = null;
tempSubLoanAccountDetailInfo = (SubLoanAccountDetailInfo)request.getAttribute("SubLoanAccountDetailInfo");

if (tempSubLoanAccountDetailInfo != null)
{ 	
	lSubAccountID				= tempSubLoanAccountDetailInfo.getSubAccountID();				//子账户ID
	lPayInterestAccountID 		= tempSubLoanAccountDetailInfo.getPayInterestAccountID();		//付息账号
	lPaySuretyAccountID 		= tempSubLoanAccountDetailInfo.getPaySuretyFeeAccountID();		//付担保费账号
	lSuretyBankID 		= tempSubLoanAccountDetailInfo.getPaySuretyFeeBankID();			//付担保费银行
	lReceiveSuretyAccountID 	= tempSubLoanAccountDetailInfo.getReceiveSuretyFeeAccountID();	//收担保费账号
	tsLatestInterestClear 		= tempSubLoanAccountDetailInfo.getLatestClearInterest();		//上次结息日
	
	dInterest 					= tempSubLoanAccountDetailInfo.getInterest();					//利息
	dInterestReceiveAble			= tempSubLoanAccountDetailInfo.getInterestReceiveAble();		//计提利息
	dInterestIncome				= tempSubLoanAccountDetailInfo.getInterestIncome();				//本次利息
	dCompoundInterest 			= tempSubLoanAccountDetailInfo.getCompoundInterest();			//复利
	dOverDueInterest 			= tempSubLoanAccountDetailInfo.getOverDueInterest();			//罚息
	dSuretyFee					= tempSubLoanAccountDetailInfo.getSuretyFee();
}
/**
 * 实际支付默认和应当支付相等
 */
dRealInterest 					= dInterest;
dRealInterestReceiveAble			= dInterestReceiveAble;
dRealInterestIncome				= dInterestIncome;
dRealCompoundInterest 			= dCompoundInterest;
dRealOverDueInterest 			= dOverDueInterest;
dRealSuretyFee					= dSuretyFee;

strTemp = (String)request.getAttribute("strActionResult");
if (strTemp != null && strTemp.trim().length()>0)								//结果代码
{
	  strActionResult = strTemp.trim();
}
strTemp = (String)request.getAttribute("strAction");
if (strTemp != null && strTemp.trim().length()>0)								//操作代码
{
	  strAction = strTemp.trim();
}

strTemp = (String)request.getAttribute("lTransactionTypeID");
if (strTemp !=null && strTemp.trim().length()>0){								//交易类型
	lTransactionTypeID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lID");
if (strTemp !=null && strTemp.trim().length()>0){								//ID
	lID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("strTempTransNO");
if (strTemp !=null && strTemp.trim().length()>0){								//临时交易号
	strTempTransNO = strTemp.trim();
}

strTemp = (String)request.getAttribute("tsModify");
if (strTemp !=null && strTemp.trim().length()>0){								//修改时间
	tsModify = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lInputUserID");
if (strTemp !=null && strTemp.trim().length()>0){								//录入人
	lInputUserID = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("lCheckUserID");
if (strTemp !=null && strTemp.trim().length()>0){								//复合人
	lCheckUserID = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsExecute");
if (strTemp !=null && strTemp.trim().length()>0){								//执行日
	tsExecute = DataFormat.getDateTime(strTemp.trim());
}

strTemp = (String)request.getAttribute("lStatusID");
if (strTemp !=null && strTemp.trim().length()>0){								//记录状态
	lStatusID = Long.valueOf(strTemp.trim()).longValue();
}
/**
 * 默认保存的记录状态和库中提出来的一致
 */
lRecordStatusID = lStatusID;
strTemp = (String)request.getAttribute("lRecordStatusID");
if (strTemp !=null && strTemp.trim().length()>0){								//保存的记录状态
	lRecordStatusID = Long.valueOf(strTemp.trim()).longValue();
}
strTemp = (String)request.getAttribute("lDesc");
if (strTemp !=null && strTemp.trim().length()>0){								//排序方式
	lDesc = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("lOrderByCode");
if (strTemp !=null && strTemp.trim().length()>0){								//排序字段
	lOrderByCode = Long.valueOf(strTemp.trim()).longValue();
}

strTemp = (String)request.getAttribute("tsInput");
if (strTemp !=null && strTemp.trim().length()>0){								//排序字段
	tsInput = DataFormat.getDateTime(strTemp.trim());
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

strTemp = (String)request.getAttribute("dSavedAmount");										//保存的金额
if (strTemp != null && strTemp.trim().length() > 0)
{
	dSavedAmount = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("lAbstractIDCtrl");
if (strTemp != null && strTemp.trim().length() > 0)											//摘要文本
{
	strAbstract = strTemp;
}

strTemp = (String)request.getAttribute("strCheckAbstract");
if (strTemp != null && strTemp.trim().length() > 0)											//摘要文本
{
	strCheckAbstract = strTemp;
}


//获取页面二的业务数据

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

strTemp = (String)request.getAttribute("lPaySuretyAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//负担保费账号
{
	lPaySuretyAccountID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lSuretyBankID");
if (strTemp != null && strTemp.trim().length() > 0)											//负担保费银行
{
	lSuretyBankID = Long.valueOf(strTemp).longValue();
}
strTemp = (String)request.getAttribute("lReceiveSuretyAccountID");
if (strTemp != null && strTemp.trim().length() > 0)											//收担保费账号
{
	lReceiveSuretyAccountID = Long.valueOf(strTemp).longValue();
}

strTemp = (String)request.getAttribute("dInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//应付利息
{
	dInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestReceiveAble");
if (strTemp != null && strTemp.trim().length() > 0)											//应付计提利息
{
	dInterestReceiveAble = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dInterestIncome");
if (strTemp != null && strTemp.trim().length() > 0)											//应付本次利息
{
	dInterestIncome = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("dSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//应付担保费
{
	dSuretyFee = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterest");
if (strTemp != null && strTemp.trim().length() > 0)											//实付利息
{
	dRealInterest = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestReceiveAble");
if (strTemp != null && strTemp.trim().length() > 0)											//实付计提利息
{
	dRealInterestReceiveAble = DataFormat.parseNumber(strTemp);
}

strTemp = (String)request.getAttribute("dRealInterestIncome");
if (strTemp != null && strTemp.trim().length() > 0)											//实付本次利息
{
	dRealInterestIncome = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("dRealSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//实付担保费
{
	dRealSuretyFee = DataFormat.parseNumber(strTemp);
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

strTemp = (String)request.getAttribute("lIsRemitSuretyFee");
if (strTemp != null && strTemp.trim().length() > 0)											//是否免还手续费
{
	lIsRemitSuretyFee = Long.valueOf(strTemp.trim()).longValue();
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

strTemp = (String)request.getAttribute("strTransNo");										//交易号
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

<!--复核,取消需要的参数-->
	<input name="lID" type="Hidden" value="<%=lID%>">
	<input name="strTempTransNO" type="Hidden" value="<%=strTempTransNO%>">
	<input name="tsModify" type="Hidden" value="<%=tsModify%>">
	<input name="lCheckUserID" type="Hidden" value="<%=lCheckUserID%>">
	<input name="tsExecute" type="Hidden" value="<%=tsExecute%>">
	<input name="lMultiLoanType" type="Hidden" value="<%=lMultiLoanType%>">
<!--复核,取消需要的参数-->
<!--链接查找-->
	<input name="lStatusID" type="Hidden" value="<%=lRecordStatusID%>">
	<input name="lDesc" type="Hidden" value="<%=lDesc%>">
	<input name="lOrderByCode" type="Hidden" value="<%=lOrderByCode%>">
<!--链接查找-->
	<input name="tsLatestInterestClear" type="hidden" value="<%=DataFormat.getDateString(tsLatestInterestClear)%>"> <!--上次结息日-->
	<input type="Hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
<TABLE border=0 class=top height=60 width="99%">
  <TBODY>   
 <TR class="tableHeader"> 
      <TD class=FormTitle height=2 width="100%"><B>业务复核 —— 多笔贷款收回 —— 自营贷款收回 </B></TD>
    </TR>
    		<tr>
					<td align='center'>
						<table width="97%">
							<tr>
								<td width='80'>收付方向:</td>
								<td align='left'>
									<%SETTConstant.MultiLoanType.showList(out,"lMultiLoanType",0,2,false,false,"onchange='changeMultiLoanType(frmV053_1)'disabled onchange='changeMultiLoanType(frmV053_1)' onfocus=nextfield='strDeclarationNo'",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;
								</td>
								<td align="right">
									银行报单号:&nbsp;&nbsp;&nbsp;
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
									<TD height="20" width="17%"><U>自营贷款详细资料</U>
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
									<TD height="20" width="15%">	贷款客户名称:
									</TD>
									<TD height="20" width="35%">
										<textarea name="strLoanClientName" class="box" disabled  rows="2" cols="30"><%=NameRef.getClientNameByAccountID(lLoanAccountID)%></textarea>
									</TD>

</TR>
								<TR borderColor="#E8E8E8">
									<%
//自营贷款账号放大镜   	
		String strCtrlNameTrustLoanAcct = "lLoanAccountID";
		String strTitleTrustLoanAcct = "自营贷款账号";
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
//合同号放大镜
	String strCtrlNameContract = "lLoanContractID";
	String strTitleContract = "合同号";
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
//放款通知单据号放大镜
	String strCtrlNamePayForm = "lLoanNoteID";
	String strTitlePayForm = "放款通知单据号";
	long lLoanContractIDPayForm = lLoanContractID;
	long lPayFormID = lLoanNoteID;
	String strPayFormNo = NameRef.getPayFormNoByID(lLoanNoteID);
	long lPayFormTypeID = 1;									//放款通知单类型(查询条件:1,信托；2，委托)
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
              <TD height=20 vAlign=middle nowrap width="15%"><U>利息收回详细资料</U> </TD>
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
	showDisableAccountCtrl("strPayInterestAccountNo","<%=NameRef.getAccountNoByID(lPayInterestAccountID)%>","付息活期存款账号","width=20%","")
</script>-->
<%	
//付息活期存款账号
		String strCtrlNameAcct = "lPayInterestAccountID";
		String strTitleAcct = "付息活期存款账号";
		long lClientIDAcct = -1;
		long lAccountIDAcct = lPayInterestAccountID;
		String strAccountNoAcct = NameRef.getAccountNoByID(lPayInterestAccountID);
		long lAccountGroupTypeIDAcct = SETTConstant.AccountGroupType.CURRENT;
		long lAccountTypeIDAcct = -1;
		long lReceiveOrPayAcct = -1;//收付类型
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
                    <TD height=20 vAlign=middle>付息客户名称：</TD>
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
//开户行放大镜
		String strCtrlNameBranch = "lInterestBankID";
		String strTitleBranch = "开户行";
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>担保费付出详细资料</U> 
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
					<script language=javascript>showDisableAccountCtrl("strPaySuretyFeeAccountNo","<%=NameRef.getAccountNoByID(lPaySuretyAccountID)%>","担保费付出存款账号","width=20%","")
					</script>
			        <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">付款客户名称： </TD>
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
//开户行放大镜
        String strCtrlNameBranchSF = "lSuretyBankID";
		String strTitleBranchSF = "开户行";
		long lBranchIDBranchSF = lSuretyBankID;
		String strBranchNameBranchSF = NameRef.getBankNameByID(lSuretyBankID);
		long lIsSingleBankBranchSF = 0;
		String strAccountCtrlBranchSF = "";
		String strFirstTDBranchSF = "";
		String strSecondTDBranchSF = "";
		String[] strNextControlsBranchSF = {"dRealInterest"};//next 焦点贷款利息的实际支付文本域
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
              <TD height="20" vAlign="middle" nowrap width="15%"><U>担保费收入详细资料</U> 
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
                    	showDisableAccountCtrl("strReceiveSuretyfeeAccountNo","<%=NameRef.getAccountNoByID(lReceiveSuretyAccountID)%>","担保费收入账号","width=20%","")
					</script>
                    <TD width="100">&nbsp; </TD>
                    <TD height="20" vAlign="middle">收款客户名称： </TD>
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
                    <TD  height="21"  valign="top"width="250">应当支付 </TD>
                    <TD height="21" vAlign="top" width="250">实际支付</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
                  <tr> 
                    <td>贷款利息：</td>
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
					  免还剩余利息</TD>
                  </tr>
                  <tr> 
                    <td>其中 计提利息：</td>
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
                    <td>&nbsp; &nbsp;&nbsp;&nbsp;本次利息：</td>
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
                    <td>复利：</td>
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
                      免还剩余复利</TD>
                  </tr>
                  <tr> 
                    <td>逾期罚息:</td>
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
                 
                      免还剩余罚息</TD>
                  </tr>
                  <tr> 
                    <td>担保费:</td>
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
                      免还剩余担保费</TD>
                  </tr>
                  <tr> 
                    <td>利息费用合计：</td>
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
                    <td>免还原因：</td>
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
            
                    <TD width="250"><input type="radio" disabled name="lCapitalAndInterstDealway" value="1" onfocus="nextfield='lCapitalAndInterstDealway[1]'">
					
                      汇总处理 </TD>
                    <TD vAlign="middle">&nbsp;</TD>
                    <TD height="20" vAlign="middle" width="250"><input type="radio" disabled name="lCapitalAndInterstDealway" value="2" checked onfocus="nextfield='submitfunction'">
                      分笔处理</TD>
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
//提前还款通知单放大镜
	String strCtrlNameAheadPayForm = "lPreFormID";
	String strTitleAheadPayForm = "提前还款通知单";
	long lLoanContractIDAheadPayForm = lLoanContractID;
	long lPreFormIDF = lPreFormID;
	String strAheadPayFormNo = NameRef.getAheadPayFormNoByID(lPreFormID);
	long lAheadPayFormTypeID = 1;
	String strContractCtrlAheadPayForm = "lLoanContractID";
	long lAheadPayFormStatusIDs = 0;//应该改为1：为业务处理时使用
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
								</TR>
								<TR vAlign="middle">
									<TD height="20" width="15%">	还款金额:
									</TD>
									<TD height="20" width="20%"><%=sessionMng.m_strCurrencySymbol%> 
	<!--*****************************************************************************-->
<script language="javascript">// 创建金额控件
	createAmountCtrl("frmV053_1","dAmount","<%=DataFormat.formatDisabledAmount(dAmount)%>","tsInterestStart");
	document.all.dAmount.disabled=true;
</script><!--*****************************************************************************-->
									</TD>
									<TD height="32" width="10%">&nbsp;	
									</TD>
									<TD height="32" width="15%">	起息日:
									</TD>
									<TD height="32" width="20%">
										<INPUT type="Text" class="box" disabled name="tsInterestStart" value="<%=DataFormat.getDateString(tsInterestStart)%>" onFocus="nextfield='lAbstractIDCtrl';">&nbsp;
									</TD>
									<TD height="32" width="20%">&nbsp;	
									</TD>
								</TR>
								<TR vAlign="middle">
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
			<INPUT class=button name=Submit3 onclick="doCheck(frmV053_6);" type=button value=" 复 核 "> 
			<%
			}
			else if (lRecordStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV053_6);" type=button value=" 取消复核 "> 			
			<%
			}
			%>
			<INPUT class=button name=Submit322 onclick="doBack(frmV053_6);" type=button value=" 返 回 "> 
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
          <TD height=25 width="9%"><%=(lCheckUserID > 0 && lStatusID == SETTConstant.TransactionStatus.CHECK ? DataFormat.formatDate(tsExecute) : "&nbsp;")%></TD>
          <TD height=25 width="5%">状态:</TD>
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
	if (confirm("是否返回？"))
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
	if (confirm("是否打印?")){
		window.open( "../accountinfo/a927-v.jsp?lID="+<%=lID%>+"&strTransNo=<%=strTransNo%>&lTransactionTypeID=<%=SETTConstant.TransactionType.MULTILOANRECEIVE%>&strSuccessPageURL='../../tran/loan/view/v053_6.jsp'&strFailPageURL='../../tran/loan/view/v053_6.jsp'&lReturn=<%=lOBReturn%>");
	}
}
function doCheck(form)
{
	form.strAction.value = '<%=SETTConstant.Actions.CHECK%>';	
	form.strSuccessPageURL.value = '../view/v053_5.jsp';
	form.strFailPageURL.value = '../view/v053_6.jsp';
	
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
	form.strFailPageURL.value = '../view/v053_6.jsp';
	
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
