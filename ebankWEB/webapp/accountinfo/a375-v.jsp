<%--
 页面名称 ：a375-v.jsp
 页面功能 : 委托存款业务复核-复核页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
				1、复核
				2、取消复核
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo" %>
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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a375-v.jsp******\n");
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

			//收款方账户回显信息
			String strReceiveClientCode = null;
			String strReceiveClientName = null;
			String strReceiveAccountNo = null;
			long lReceiveAccountClientID = -1;

			//开户行回显信息
			String strBranchName = null;
			String strBankAccountCode = null;

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

			if(strReceiveClientCode == null)
			{
				strReceiveClientCode = NameRef.getClientCodeByID(lReceiveClientID);
				
				strReceiveClientName = NameRef.getClientNameByID(lReceiveClientID);				
			}
			strReceiveClientCode = DataFormat.formatString(strReceiveClientCode);
			strReceiveClientName = DataFormat.formatString(strReceiveClientName);

			if(strReceiveAccountNo == null)
			{
				strReceiveAccountNo = NameRef.getAccountNoByID(lReceiveAccountID);				
			}			
			strReceiveAccountNo = DataFormat.formatString(strReceiveAccountNo);

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
	<form name="frmV075" method="post" action="../control/c004.jsp">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">
		<input name="strSuccessPageURL" type="hidden" value="<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{out.print("../view/v075.jsp");
				}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
				{out.print("../view/v076.jsp");}%>">
		<input name="strFailPageURL" type="hidden" value="../view/v075.jsp">
		<input name="lId" type="hidden" value="<%=lId%>">
		<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">
		<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
		<input name="strModifyTime"  type="hidden" value="<%=strModifyTime%>">
		<TABLE border="0" class="top" height="290" width="99%">
			<TBODY>
				<TR class="tableHeader">
					<TD class="FormTitle" height="2" width="100%"><B>委托存款</B>
					</TD>
				</TR>
				<TR>
					<TD height="120" vAlign="top" width="100%">
						<TABLE border="0" height="27" width="100%">
							<TBODY>
								<TR>
									<TD height="107" vAlign="top" width="95%">
										<TABLE align="center" height="106" width="97%">
											<TBODY>
												<TR>
													<TD height="106" vAlign="top" width="50%">
														<TABLE border="1" borderColor="#999999" height="130" width="100%">
															<TBODY>
																<TR borderColor="#E8E8E8">
																	<TD height="20" vAlign="middle" width="34%"><U>付款方详细资料 </U>
																	</TD>
																	<TD height="20" vAlign="top" width="66%">&nbsp;	
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
																	<TD height="20" vAlign="middle" width="29%">	付款方客户编号：
																	</TD>
																	<TD height="20" vAlign="top" width="71%">
																		<input type="text" name="kehh" size="25" class="box" disabled onFocus="nextfield ='zhh1';" value="<%=strPayClientCode%>">
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
																	<TD height="20" vAlign="middle" width="34%">	付款方客户名称：
																	</TD>
																	<TD height="20" vAlign="top" width="66%">
																		<textarea name="textfield624" class="box" disabled rows=2 cols="30"><%=strPayClientName%></textarea>
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
<%
	if(lPayAccountID > 0)
	{
																			%>
<script language="javascript">showDisableAccountCtrl("strPayAccountNo","<%=strPayAccountNo%>","付款方账号","height='20' width='50%'","height='20' width='50%'");
</script>
<%
	}
	else if(lBankID > 0 )
	{
%>
																	<TD valign="middle" height="20" width="18%">	开户行：
																	</TD>
																	<TD valign="top" height="20" width="82%" colspan="3">
																		<input type="text" name="kehh123" size="25" class="box" disabled  value="<%=strBranchName%>">
<%
	}
%>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD height="106" vAlign="top" width="50%">
														<TABLE align="center" border="1" borderColor="#999999" height="120" width="100%">
															<TBODY>
																<TR borderColor="#E8E8E8">
																	<TD height="22" vAlign="middle" width="28%"><U>收款方详细资料</U>
																	</TD>
																	<TD height="22" vAlign="top" width="72%">&nbsp;	
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
																	<TD height="26" vAlign="middle" width="29%">	收款方客户编号：
																	</TD>
																	<TD height="26" vAlign="top" width="71%">
																		<input type="text" name="kehh1" size="25" class="box" disabled onFocus="nextfield ='zhh1';" value="<%=strReceiveClientCode%>">
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
																	<TD height="38" vAlign="middle" width="28%">	收款方客户名称：
																	</TD>
																	<TD height="38" vAlign="top" width="72%">
																		<textarea name="textfield623" class="box" disabled rows=2 cols="30"><%=strReceiveClientName%></textarea>
																	</TD>
																</TR>
																<TR borderColor="#E8E8E8">
<script language="javascript">showDisableAccountCtrl("strReceiveAccountNo","<%=strReceiveAccountNo%>","收款方账号","height='27' width='50%'","height='27' width='50%'");
</script>
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
									<TD height="2" width="95%">
										<TABLE align="center" height="2" width="97%">
											<TBODY>
												<TR>
													<TD align="left" height="20" vAlign="middle" width="8%">	金 额：
													</TD>
													<TD align="left" height="20" vAlign="middle" width="22%">	￥
														<input type="text" name="textfield82" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmount)%>">
													</TD>
													<TD align="left" height="20" vAlign="middle" width="13%">	起息日：
													</TD>
													<TD align="left" height="20" vAlign="middle" width="19%">
														<input type="text" name="textfield92" size="20" disabled class="box" value="<%=strInterestStartDate%>">
													</TD>
													<TD align="left" height="20" vAlign="middle" width="14%">	执行日：
													</TD>
													<TD align="left" height="20" vAlign="middle" width="24%">
														<input type="text" name="textfield102" size="20" disabled class="box" value="<%=strExecuteDate%>">
													</TD>
												</TR>
												<TR>
													<TD align="left" height="20" vAlign="middle" width="8%">	交易号：
													</TD>
													<TD align="left" height="20" vAlign="middle" width="22%">
														<input type="text" name="textfield2352" size="20" disabled class="box" value="<%=strTransNo%>">
													</TD>
													<TD align="left" height="20" vAlign="middle" width="13%">	摘 要：
													</TD>
													<TD align="left" height="20" vAlign="middle" width="19%">
														<input type="text" name="textfield2322" maxlength="2000" disabled size="40" class="box" value="<%=strAbstractStr%>">
													</TD>
													<TD align="left" height="20" vAlign="middle" width="14%">&nbsp;	
													</TD>
													<TD align="left" height="20" vAlign="middle" width="24%">&nbsp;
														
													</TD>
												</TR>
												<TR>
													<td align="left" valign="top" height="20" colspan="6">
											<div align="right">
												<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
												<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
											</div>
													</td>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD height="2" width="95%">
<HR>
										<TABLE align="center" border="0" height="22" width="97%">
											<TBODY>
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
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
	
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
	
	document.location.href="<%=strContext%>/settlement/tran/current/view/v074.jsp";
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
		document.frmV075.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		document.frmV075.submit();
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
		if(!validateFields(frmV075)) return;

		isSubmited = true;
		document.frmV075.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		document.frmV075.submit();
	}
}

function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a401-v.jsp?lID=<%=lId%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a401-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a375-v.jsp");
}

function doReturn()
{
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{%>
		document.location.href="<%=strContext%>/settlement/tran/current/control/c003.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.CONSIGNSAVE%>&strSuccessPageURL=../view/v076.jsp&strFailPageURL=../view/v076.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";		
	<%
	}else
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/current/view/v074.jsp";
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
firstFocus(document.frmV075.strCheckAbstractStr);
//setSubmitFunction("doCheck()");
setFormName("frmV075");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV075.strCheckAbstractStr);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV075");     
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