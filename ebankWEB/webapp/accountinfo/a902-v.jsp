<%--
 页面名称 ：a902-v.jsp
 页面功能 : 业务处理-利息费用支付-复核/取消复核页面
 作    者 ：qqgd
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
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
%>

<%
//定义放大镜公用变量：办事处编号、币种编号、表单域名称

long lOfficeID 					= sessionMng.m_lOfficeID;		//办事处ID
long lCurrencyID 				= sessionMng.m_lCurrencyID;		//币种
String strFormName 				= "frmV047";					//表单名称
long lCheckUserID		= sessionMng.m_lUserID;						//录入人

Timestamp tsExecute 			= DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));	//开机时间,即录入时间
long lID						= -1;							//ID

//链接查找参数
long lStatusID					= -1;										//下拉框状态,记录上操作一次选了什么
long lDesc 						= Constant.PageControl.CODE_ASCORDESC_DESC;	//排序方式
int nOrderByCode				= -1;										//排序字段


Timestamp tsModify				= null;						//修改时间
//-----------------------页面一参数----------------------
long lClientID 					= -1;							//还款客户ID
long lLoanAccountID 			= -1;							//贷款账号
long lContractID 				= -1;							//合同号
long lContractType				= -1;							//合同类型	<<<
long lLoanType					= -1;							//贷款类型	<<<
long lLoanNoteID 				= -1;							//放款通知单
String tsStartDate 				= null;							//开始日期
String tsEndDate 				= null;							//到期日期
Timestamp tsInterestClear 		= null;							//起息日
double dAmount 					= 0.0;							//还款金额
//-----------------------页面一参数----------------------
//-----------------------页面二参数----------------------
long lPayInterestAccountID 			= -1;						//活期存款账号
long lInterestBankID 				= -1;						//收款银行
long lReceiveInterestAccountID 		= -1;						//收款方账号

double dInterest 				= 0.0;							//利息
double dInterestReceivable 		= 0.0;							//计提利息
double dInterestIncome 			= 0.0;							//本次利息
double dInterestTax				= 0.0;							//利息税费
double dCompoundInterest 		= 0.0;							//复利
double dOverDueInterest			= 1.0;							//逾期罚息
double dCommission 				= 0.0;							//手续费
double dSuretyFee 				= 0.0;							//担保费

double dRealInterest 			= 0.0;							//实际支付利息
double dRealInterestReceivable 	= 0.0;							//实际支付计提利息
double dRealInterestIncome 		= 0.0;							//实际支付本次利息
double dRealInterestTax			= 0.0;							//实际支付利息税费
double dRealCompoundInterest 	= 0.0;							//实际支付复利
double dRealOverDueInterest		= 0.0;							//实际支付逾期罚息
double dRealCommission 			= 0.0;							//实际支付手续费
double dRealSuretyFee 			= 0.0;							//实际支付担保费
//double dInterestTaxRate			= 0.0;						//利息税费率		<<<

long lIsRemitInterest 			= -1;							//是否免还剩余贷款利息
long lIsRemitCompoundInterest 	= -1;							//是否免还剩余复利
long lIsRemitOverDueInterest 	= -1;							//是否免还剩余逾期罚息
long lIsRemitSuretyFee 			= -1;							//是否免还剩余担保费
long lIsRemitCommission 		= -1;							//是否免还剩余手续费

String tsInterestStart 			= null;							//起息日
long lAbstractID 				= -1;							//摘要ID
String strAbstract = "";										//摘要

//-----------------------页面二参数----------------------
//-----------------------得到页面参数--------------------
String strTmp =null;
strTmp=(String)request.getAttribute("lContractType");			//合同类型
if (strTmp!=null && strTmp.trim().length()>0){
	lContractType = Long.valueOf(strTmp.trim()).longValue();
}

	TransRepaymentLoanInfo info=(TransRepaymentLoanInfo)request.getAttribute("RepaymentInfo");							//还款类
	
//-----------------------得到页面参数--------------------
if (info != null){
	lID							= info.getID();						//ID
	lClientID 					= info.getClientID();				//还款客户ID
	lLoanAccountID 				= info.getLoanAccountID();			//贷款账号
	lContractID 				= info.getLoanContractID();			//合同号
	lLoanNoteID 				= info.getLoanNoteID();				//放款通知单
	tsInterestClear 			= info.getInterestClear();			//结息日
	
	dInterest 					= info.getInterest();				//利息
	dInterestReceivable 		= info.getInterestReceiveAble();	//计提利息
	dInterestIncome 			= info.getInterestIncome();			//本次利息
	dInterestTax				= info.getInterestTax();			//利息税费
	dCompoundInterest 			= info.getCompoundInterest();		//复利
	dOverDueInterest			= info.getOverDueInterest();		//逾期罚息
	dCommission 				= info.getCommission();				//手续费
	dSuretyFee 					= info.getSuretyFee();				//担保费
	
	/**
	 * 判断当前的贷款类型,并根据类型读取相应的利息账户和银行信息
	 * 如果是自营贷款,利息存入的账户是担保费账户,银行是担保费银行.委托是付息账户和付息银行
	 
	if (lContractType==LOANConstant.LoanType.YT
		||lContractType==LOANConstant.LoanType.ZGXEDQ
		||lContractType==LOANConstant.LoanType.ZGXEZCQ
		||lContractType==LOANConstant.LoanType.ZYDQ
		||lContractType==LOANConstant.LoanType.ZYZCQ){//如果是信托
		
		lLoanType = 1;
		lReceiveInterestAccountID 	= info.getReceiveSuretyAccountID();//收款方账号
	}
	else if(lContractType==LOANConstant.LoanType.WT 
		||lContractType==LOANConstant.LoanType.WTTJTH){//如果是委托
		lLoanType = 2;
		lReceiveInterestAccountID 	= info.getReceiveInterestAccountID();//收款方账号
	}*/
	if (info.getReceiveSuretyAccountID()>0) lReceiveInterestAccountID = info.getReceiveSuretyAccountID();
	else if (info.getReceiveInterestAccountID()>0) lReceiveInterestAccountID = info.getReceiveInterestAccountID();
	
	
	lPayInterestAccountID 		= info.getPayInterestAccountID();	//活期存款账号
	lInterestBankID 			= info.getInterestBankID();			//收款银行
	
	dRealInterest 				= info.getRealInterest();			//实际支付利息
	dRealInterestReceivable 	= info.getRealInterestReceiveAble();//实际支付计提利息
	dRealInterestIncome 		= info.getRealInterestIncome();		//实际支付本次利息
	dRealInterestTax			= info.getRealInterestTax();		//实际支付利息税费
	dRealCompoundInterest 		= info.getRealCompoundInterest();	//实际支付复利
	dRealOverDueInterest		= info.getRealOverDueInterest();	//实际支付逾期罚息
	dRealCommission 			= info.getRealCommission();			//实际支付手续费
	dRealSuretyFee 				= info.getRealSuretyFee();			//实际支付担保费
	
	lIsRemitInterest 			= info.getIsRemitInterest();		//是否免还剩余贷款利息
	lIsRemitCompoundInterest	= info.getIsRemitCompoundInterest();//是否免还剩余复利
	lIsRemitOverDueInterest		= info.getIsRemitOverDueInterest();	//是否免还剩余逾期罚息
	lIsRemitSuretyFee 			= info.getIsRemitSuretyFee();		//是否免还剩余担保费
	lIsRemitCommission 			= info.getIsRemitCommission();		//是否免还剩余手续费
	
	tsInterestStart 			= DataFormat.getDateString(info.getInterestStart());//起息日
	lAbstractID 				= info.getAbstractID();				//摘要ID
	strAbstract 				= info.getAbstract();				//摘要
	lStatusID					= info.getStatusID();				//得到状态ID
	tsModify 					= info.getModify();
}

strTmp=(String)request.getAttribute("tsStartDate");				//开始时间
if (strTmp!=null && strTmp.trim().length()>0){
	tsStartDate = strTmp.trim();
}
strTmp=(String)request.getAttribute("tsEndDate");				//到期时间
if (strTmp!=null && strTmp.trim().length()>0){
	tsEndDate = strTmp.trim();
}
strTmp=(String)request.getAttribute("dAmount");					//还款金额
if (strTmp!=null && strTmp.trim().length()>0){
	dAmount = DataFormat.parseNumber(strTmp.trim());
}
strTmp = (String)request.getAttribute("lCheckUserID");			//录入人
if(strTmp != null && strTmp.trim().length() > 0)
{
	lCheckUserID = Long.valueOf(strTmp).longValue();
}
strTmp = (String)request.getAttribute("lPayInterestAccountID");	//付息账号
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
Log.print("排序方式:"+lDesc);


//网银财务指令接收
String strOBInstr = (String)request.getAttribute("OBInstr");
String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");

//页面辅助变量
String strAction 				= null;						//操作码
String strActionResult 			= Constant.ActionResult.FAIL;//操作结果
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
<!--修改成功后链接查找回显参数-->
<input type="hidden" name="lStatusID" value="<%=lStatusID%>">
<input name="lTransactionTypeID" type="hidden" value="<%=SETTConstant.TransactionType.INTERESTFEEPAYMENT%>">
<input type="hidden" name="nOrderByCode" value="<%=nOrderByCode%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<!--修改成功后链接查找回显参数-->

<TABLE border=0 class=top height=8 width="99%">
  <TR class="tableHeader">
    <TD class=FormTitle height=2 width="100%"><B>利息费用支付</B></TD></TR>
  <TR borderColor=#E8E8E8>
    <TD vAlign=bottom width="100%">
      <TABLE align=center border=1 cellPadding=2 cellSpacing=2 height=60 
      width="97%" borderColor=#999999>
      <tr borderColor=#E8E8E8>
      	<td colspan='7'>
      		<strong>利息费用还款详细资料</strong>
      	</td>
      </tr>
        <TR borderColor=#E8E8E8>
          <td height=20 width="5%">&nbsp;</td>
          <TD height=20 width="17%">还款贷款客户编号：</TD>
          <TD height=20 width="33%">
           <input type='hidden' name='lLoanAccountID' value='<%=lLoanAccountID%>'>
           <INPUT class=box maxLength=6 
            name="lLoanAccountIDCtrl" size=10 value="<%=NameRef.getClientCodeByAccountID(lLoanAccountID)%>" disabled> </TD>
          <td>&nbsp;</td>
          <TD height=20 width="16%">还款贷款客户名称 ：</TD>
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
	//活期存款账号放大镜
		String strCtrlNameAcct = "lPayInterestAccountID";
		String strTitleAcct = "活期存款账号";
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
//收款银行名称放大镜
		String strCtrlNameBranch = "lInterestBankID";
		String strTitleBranch = "收款银行名称";
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
					<strong>利息费用收入详细资料</strong>
				</td>
			</tr>
      		<tr borderColor=#E8E8E8>
      			<td height=20>
      				<table border=0 cellpadding=0 cellspacing=0 width='100%'>
      					<tr borderColor=#E8E8E8>
        <td>&nbsp;</td>
        	<%
//收款方账号放大镜
		strCtrlNameAcct = "lReceiveInterestAccountID";
		strTitleAcct = "收款方账号";
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
<TD height=20 width="14%">客户名称:</TD>
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
                    <TD  height="21"  valign="top"width="250">应当支付 </TD>
                    <TD height="21" vAlign="top" width="250">实际支付</TD>
                    <TD height="21" vAlign="top" width="200">&nbsp; </TD>
                  </tr>
                  
                  <tr> 
                    <td>贷款利息：</td>
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
					  免还剩余利息</TD>
                  </tr>
                  	
                  <tr> 
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;其中 计提利息：</td>
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
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次利息：</td>
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
                    <td>复利：</td>
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
                      免还剩余复利</TD>
                  </tr>
                  <tr> 
                    <td>逾期罚息:</td>
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
                      免还剩余逾期罚息</TD>
                  </tr>
                  <tr> 
                    <td>利息税费：</td>
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
                    <td>手续费:</td>
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
                      免还剩余手续费</TD>
                  </tr>

                  <tr> 
                    <td>担保费:</td>
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
                      免还剩余担保费</TD>
                  </tr>

                  <tr> 
                    <td>利息费用合计：</td>
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
      					起息日 :
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
//摘要放大镜
		String strCtrlNameAbstract = "lAbstractID";
		String strTitleAbstract = "摘要";
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
			<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
			<input class="button" name="Submit32" type="button" value=" 返 回 " onClick="doReturn();">	
<%	
		}
		else
		{
%>

	            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
				<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
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
			<INPUT class=button name=Submit3 onclick="doCheck(frmV047);" type=button value=" 复 核 "> 
			<%
			}
			else if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			{
			%>
			<INPUT class=button name=Submit31 onclick="doCancelCheck(frmV047);" type=button value=" 取消复核 "> 			
			<%
			}
			%>
            <INPUT class=button name=Submit32 onclick="print()" type=button value=" 打 印 "> 
			<INPUT class=button name=Submit322 onclick="doBack(frmV047);" type=button value=" 返 回 "> 
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
            name="strCheckAbstract" value="<%=info.getCheckAbstract()!=null?info.getCheckAbstract():""%>" size="40" onfocus="nextfield='submitfunction';" maxlength="100"> </TD>
          <TD height=25 vAlign=middle width="6%">录入人:</TD>
          <TD height=25 vAlign=middle width="8%"><%=NameRef.getUserNameByID(info.getInputUserID())%></TD>
          <TD height=25 width="8%">录入日期:</TD>
          <TD height=25 width="11%"><%=DataFormat.formatDate(info.getInput())%></TD>
          <TD height=25 width="6%">复核人:</TD>
          <TD height=25 width="7%"><%=NameRef.getUserNameByID(info.getCheckUserID())%></TD>
          <TD height=25 width="8%">复核日期:</TD>
          <TD height=25 width="9%"><%=(info.getCheckUserID() > 0 ? DataFormat.formatDate(info.getExecute()) : "&nbsp;")%></TD>
          <TD height=25 width="5%">状态:</TD>
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
	if (confirm("是否返回？"))
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
	if (confirm("是否打印?")){
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
	
	if (confirm("是否复核？"))
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
	if (confirm("是否取消复核？"))
	{
		form.action = "../control/c044.jsp";
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
}
catch( Exception exp )
{
	exp.printStackTrace();
	Log.print(exp.getMessage());
}
OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>