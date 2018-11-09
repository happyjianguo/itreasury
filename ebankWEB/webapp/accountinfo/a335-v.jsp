<%--
/**
 页面名称 ：a335-v.jsp
 页面功能 : 现金付款业务复核-复核页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
				1、复核
				2、取消复核
 修改历史 ：
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>	
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<% Log.print("==============strContext="+strContext);%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />


<%
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a335-v.jsp******\n");
    //标题变量
    String strTitle = "[账户历史明细]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

			//定义变量
			double dAmount = 0.0;
			String strBankChequeNo = "";
			long lBankID = -1;
			long lBillBankID = -1;
			String strBillNo = "";
			long lBillTypeID = -1;
			long lCashFlowID = -1;
			String strCheckAbstractStr = "";
			String strConfirmAbstractStr = "";
			long lConfirmOfficeID = -1;
			long lConfirmUserID = -1;
			String strConsignPassword = "";
			long lConsignReceiveTypeID = -1;
			String strConsignVoucherNo = "";
			String strDeclarationNo = "";
			String strExtBankNo = "";
			String strInstructionNo = "";
			java.sql.Timestamp tsModifyTime = null;
			long lPayAccountID = -1;
			long lReceiveAccountID = -1;
			long lSignUserID = -1;
			long lSingleBankAccountTypeID = -1;
			long lStatusID = -1;
			long lReceiveClientID = -1;
			long lPayClientID = -1;
			long lAbstractID = -1;
			String strExtAccountNo = "";
			String strExtClientName = "";
			String strRemitInBank = "";
			String strRemitInCity = "";
			String strRemitInProvince = "";
			String strAbstractStr = "";
			long lCheckUserID = -1;
			long lCurrencyID = -1;
			java.sql.Timestamp tsExecuteDate = null;
			long lId = -1;
			long lInputUserID = -1;
			java.sql.Timestamp tsInterestStartDate = null;
			long lOfficeID = -1;
			long lTransactionTypeID = -1;
			String strTransNo = "";
			java.sql.Timestamp tsInputDate = null;

			//页面辅助变量
			String strAction = null;
			String strActionResult = Constant.ActionResult.FAIL;
			String strPreSaveResult = null;

			String strExecuteDate = null;
			String strInterestStartDate = null;
			String strModifyTime = null;

			//付款方账户回显信息
			String strPayClientCode = null;
			String strPayClientName = null;
			String strPayAccountNo = null;
			long lPayAccountClientID = -1;

			//开户行回显信息
			String strBranchName = null;
			String strBankAccountCode = null;

			//现金流转回显信息
			String strCashFlowDesc = null;

			//单据信息
			String strInputUserName = null;
			String strInputDate = null;
			String strStatus = null;
			String strCheckUserName = null;

			if (request.getAttribute("strActionResult") != null)
			{
				  strActionResult = (String)request.getAttribute("strActionResult");
			}
			if (request.getAttribute("strAction") != null)
			{
				  strAction = (String)request.getAttribute("strAction");
			}
			String sReturn = (String) request.getAttribute("lReturn");
			long lReturn = -1;
			if (sReturn != null && sReturn.trim().length() > 0) {
				lReturn = Long.parseLong(sReturn);
			}
			Log.print("=============lReturn="+lReturn);
			//从Request中获得参数
			TransCurrentDepositInfo depositInfo = null;
			depositInfo = (TransCurrentDepositInfo)request.getAttribute("searchResults");

			if(depositInfo != null)
			{
				lAbstractID = depositInfo.getAbstractID();
				strBankChequeNo = depositInfo.getBankChequeNo();
				lBankID = depositInfo.getBankID();
				lCashFlowID = depositInfo.getCashFlowID();
				strCheckAbstractStr = depositInfo.getCheckAbstractStr();
				strConfirmAbstractStr = depositInfo.getConfirmAbstractStr();
				lConfirmOfficeID = depositInfo.getConfirmOfficeID();
				lConfirmUserID = depositInfo.getConfirmUserID();
				strConsignPassword = depositInfo.getConsignPassword();
				lConsignReceiveTypeID = depositInfo.getConsignReceiveTypeID();
				strConsignVoucherNo = depositInfo.getConsignVoucherNo();
				strDeclarationNo = depositInfo.getDeclarationNo();
				strExtBankNo = depositInfo.getExtBankNo();
				strInstructionNo = depositInfo.getInstructionNo();
				tsModifyTime = depositInfo.getModifyTime();
				lReceiveAccountID = depositInfo.getReceiveAccountID();
				lSignUserID = depositInfo.getSignUserID();
				lSingleBankAccountTypeID = depositInfo.getSingleBankAccountTypeID();
				lStatusID = depositInfo.getStatusID();
				lReceiveClientID = depositInfo.getReceiveClientID();
				lPayClientID = depositInfo.getPayClientID();
				strExtAccountNo = depositInfo.getExtAccountNo();
				strExtClientName = depositInfo.getExtClientName();
				strRemitInBank = depositInfo.getRemitInBank();
				strRemitInCity = depositInfo.getRemitInCity();
				strRemitInProvince = depositInfo.getRemitInProvince();
				lPayAccountID = depositInfo.getPayAccountID();
				dAmount = depositInfo.getAmount();
				lOfficeID = depositInfo.getOfficeID();
				strAbstractStr = depositInfo.getAbstractStr();
				lBillBankID = depositInfo.getBillBankID();
				strBillNo = depositInfo.getBillNo();
				lBillTypeID = depositInfo.getBillTypeID();
				lCheckUserID = depositInfo.getCheckUserID();
				lCurrencyID = depositInfo.getCurrencyID();
				tsExecuteDate = depositInfo.getExecuteDate();
				lId = depositInfo.getId();
				lInputUserID = depositInfo.getInputUserID();
				tsInterestStartDate = depositInfo.getInterestStartDate();
				lTransactionTypeID = depositInfo.getTransactionTypeID();
				strTransNo = depositInfo.getTransNo();	
				tsInputDate = depositInfo.getInputDate();
			}
	
			//格式化数据
			strBankChequeNo = DataFormat.formatString(strBankChequeNo);
			strBillNo = DataFormat.formatString(strBillNo);
			strCheckAbstractStr = DataFormat.formatString(strCheckAbstractStr);
			strConfirmAbstractStr = DataFormat.formatString(strConfirmAbstractStr);
			strConsignPassword = DataFormat.formatString(strConsignPassword);
			strConsignVoucherNo = DataFormat.formatString(strConsignVoucherNo);
			strDeclarationNo = DataFormat.formatString(strDeclarationNo);
			strExtBankNo = DataFormat.formatString(strExtBankNo);
			strInstructionNo = DataFormat.formatString(strInstructionNo);
			strExtAccountNo = DataFormat.formatString(strExtAccountNo);
			strExtClientName = DataFormat.formatString(strExtClientName);
			strRemitInBank = DataFormat.formatString(strRemitInBank);
			strRemitInCity = DataFormat.formatString(strRemitInCity);
			strRemitInProvince = DataFormat.formatString(strRemitInProvince);
			strAbstractStr = DataFormat.formatString(strAbstractStr);
			strTransNo = DataFormat.formatString(strTransNo);
			
			if(strPayClientCode == null)
			{
				strPayClientCode = NameRef.getClientCodeByID(lPayClientID);
				
				strPayClientName = NameRef.getClientNameByID(lPayClientID);				
			}
			strPayClientCode = DataFormat.formatString(strPayClientCode);
			strPayClientName = DataFormat.formatString(strPayClientName);

			if(strPayAccountNo == null)
			{
				strPayAccountNo = NameRef.getAccountNoByID(lPayAccountID);				
			}
			strPayAccountNo = DataFormat.formatString(strPayAccountNo);

			if(strBranchName == null)
			{
				strBranchName = NameRef.getBankNameByID(lBankID);
				
				strBankAccountCode = NameRef.getBankAccountCodeByID(lPayAccountID,lBankID);				
			}
			strBranchName = DataFormat.formatString(strBranchName);
			strBankAccountCode = DataFormat.formatString(strBankAccountCode);

			strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
			strInputDate = DataFormat.formatDate(tsInputDate);
			strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));

			strExecuteDate = DataFormat.formatDate(tsExecuteDate);
			strInterestStartDate = DataFormat.formatDate(tsInterestStartDate);
			strModifyTime = String.valueOf(tsModifyTime.getTime());

			strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));
%>
	<form name="frmV035" method="post" action="../control/c004.jsp">
		<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
        <input name="strAction"  type="hidden">
		<input name="strSuccessPageURL"  type="hidden" value="<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{out.print("../a335-v.jsp");
				}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
				{out.print("../a301-c.jsp");}%>">
		<input name="strFailPageURL"  type="hidden" value="../a301-c.jsp">
		<input name="lId"  type="hidden" value="<%=lId%>">
		<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
		<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
		<input name="strModifyTime"  type="hidden" value="<%=strModifyTime%>">
		<TABLE width="99%" border="0" class="top" height="328">
			<TR class="tableHeader">
				<TD height="2" class="FormTitle" width="100%"><B>现金付款</B>
				</TD>
			</TR>
			<TR>
				<TD height="120" valign="top" width="100%">
					<TABLE width="100%" border="0" height="27">
						<TR>
							<TD height="2" width="95%" valign="top">
								<TABLE width="97%" height="80" align="center">
									<TR>
										<TD width="34%" height="80" valign="top">
											<TABLE width="100%" border="1" height="40" align="center" bordercolor="#999999">
												<TR bordercolor="#E8E8E8">
													<TD valign="middle" height="20" colspan="4"><U>付款方详细资料 
                        </U>
													</TD>
												</TR>
												<TR bordercolor="#E8E8E8" valign="middle">
													<TD height="20" width="18%">	付款方客户编号：
													</TD>
													<TD height="20" width="41%">														
														<INPUT type="text" name="textfield6242" disabled class="box" maxlength="6" value="<%=strPayClientCode%>" >
													</TD>
													<TD height="20" width="18%">	付款方客户名称 
	                        ：
													</TD>
													<TD height="20" width="41%">
														<TEXTAREA name="textfield62422" disabled class="box" rows="2" cols="30"><%=strPayClientName%></TEXTAREA>
													</TD>
												</TR>
												<TR bordercolor="#E8E8E8">
<script language="javascript">showDisableAccountCtrl("strAccountNo","<%=strPayAccountNo%>","付款方账号","valign='middle' height='20' width='18%'","valign='top' height='20' width='82%' colspan='3'");
</script>
												</TR>
												<TR bordercolor="#E8E8E8">
													<TD valign="middle" height="20" width="18%">	开户行：
													</TD>
													<TD valign="top" height="20" width="82%" colspan="3">
														<TEXTAREA name="textfield62432" disabled class="box" rows="2" cols="30"><%=strBranchName%></TEXTAREA>
													</TD>
												</TR>
												<TR bordercolor="#E8E8E8">
													<TD valign="middle" height="20" width="18%">	银行账号：
													</TD>
													<TD valign="top" height="20" width="82%" colspan="3">												
														<INPUT class="box" name="txtAccountBankCode" size="30" maxlength="100" value="<%=strBankAccountCode%>" disabled>
													</TD>
												</TR>
											</TABLE>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD height="2" width="95%">
								<TABLE width="97%" height="2" align="center">
									<TR>
										<TD align="left" valign="middle" height="20" width="12%">	金              额：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">	￥											
											<input type="text" name="textfield82" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmount)%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">	起息日：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">											
											<input type="text" name="textfield92" size="20" disabled class="box" value="<%=strInterestStartDate%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">	执行日：
										</TD>
										<TD align="left" valign="middle" height="20" width="24%">											
											<input type="text" name="textfield102" size="20" disabled class="box" value="<%=strExecuteDate%>">
										</TD>
									</TR>
									<TR>
										<TD align="left" valign="middle" height="20" width="12%">	现金支票号：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">											
											<input type="text" name="strBillNo" maxlength="2000" disabled size="20" class="box" value="<%=strBillNo%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">	提入行号：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">											
											<input type="text" name="strExtBankNo" maxlength="2000" disabled size="20" class="box" value="<%=strExtBankNo%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">&nbsp;	
										</TD>
										<TD align="left" valign="middle" height="20" width="24%">&nbsp;	
										</TD>
									</TR>
									<TR>
										<TD align="left" valign="middle" height="20" width="12%">	交易号：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">											
											<input type="text" name="textfield2352" size="20" disabled class="box" value="<%=strTransNo%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">	摘 
	                  要：
										</TD>
										<TD align="left" valign="middle" height="20" width="18%">											
											<input type="text" name="textfield2322" maxlength="2000" disabled size="40" class="box" value="<%=strAbstractStr%>">
										</TD>
										<TD align="left" valign="middle" height="20" width="14%">&nbsp;	
										</TD>
										<TD align="left" valign="middle" height="20" width="24%">&nbsp;	
										</TD>
									</TR>									
									<TR>
										<TD align="left" valign="top" height="20" colspan="6">
											<div align="right">
												<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
												<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
											</div>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD height="2" width="95%">
								<HR>
								<table width="97%" border="0" height="22" align="center">
									<tr valign="middle">
										<td width="9%" height="25">	<%=(lStatusID == SETTConstant.TransactionStatus.SAVE)?"复核备注":"取消复核备注"%>：
										</td>
										<td width="16%" height="25" valign="top">
											<input type="text" name="strCheckAbstractStr" class="box" value="<%=strCheckAbstractStr%>" onFocus="nextfield ='submitfunction';">
										</td>
										<td width="13%" height="25" valign="middle">	录入人：<%=strInputUserName%>
										</td>
										<td width="16%" height="25">	录入日期：<%=strInputDate%>
										</td>
										<td width="12%" height="25">	
										<%
											if(lStatusID != SETTConstant.TransactionStatus.SAVE)
											{
												out.print("复核人：");
												out.print(strCheckUserName);
											}
										%>
										</td>
										<td width="16%" height="25">
										<%
											if(lStatusID != SETTConstant.TransactionStatus.SAVE)
											{
												out.print("复核日期：");
												out.print(strExecuteDate);
											}
										%>
										</td>
										<td width="18%" height="25">	状态：<%=strStatus%>
										</td>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</FORM>
	
<script language="JavaScript">
//标识是否已提交请求
var isSubmited = false;

function checkSuccess()
{
    if (confirm("复核成功，是否打印?"))
    {
        //code
		doPrint();
    }
	document.location.href="<%=strContext%>/settlement/tran/current/view/v034.jsp";
	
}

function doCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if (confirm("是否复核此笔业务数据?")) 
	{
		isSubmited = true;
		document.frmV035.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		document.frmV035.submit();
	}
}
function doUndoCheck()
{
    if(isSubmited == true)
    {
        alert("请求已提交，请等候！");
        return;
    }

	if (confirm("是否取消复核此笔业务数据?")) 
	{
		if(!validateFields(frmV035)) return;

		isSubmited = true;
		document.frmV035.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		document.frmV035.submit();
	}
}

function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a401-v.jsp?lID=<%=lId%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a401-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a335-v.jsp.jsp");
}

function doReturn()
{
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{%>
		document.location.href="<%=strContext%>/settlement/tran/current/control/c003.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.CASHPAY%>&strSuccessPageURL=../view/v036.jsp&strFailPageURL=../view/v036.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%
	}else
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/current/view/v034.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strCheckAbstractStr","取消复核备注","string",1);
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
firstFocus(document.frmV035.strCheckAbstractStr);
//setSubmitFunction("doCheck()");
setFormName("frmV035");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV035.strCheckAbstractStr);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV035");     
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
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>