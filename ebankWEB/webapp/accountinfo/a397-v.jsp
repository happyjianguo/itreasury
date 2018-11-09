<%--
/**
 页面名称 ：a397-v.jsp
 页面功能 : 一付多收业务复核-收款复核页面
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
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo"%>
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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a398-v.jsp******\n");
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
			long lAbstractID = -1;
			long lAccountID = -1;
			long lBankID = -1;
			long lCashFlowID = -1;
			long lCheckUserID = -1;
			long lConfirmOfficeID = -1;
			long lConfirmUserID = -1;
			long lCurrencyID = -1;
			long lId = -1;
			long lInputUserID = -1;
			long lIsBank = -1;
			long lIsGL = -1;
			long lIsInternalVirement = -1;
			long lOfficeID = -1;
			long lPayClientID = -1;
			long lPayGL = -1;
			long lReceiveClientID = -1;
			long lReceiveGL = -1;
			long lSerialNo = -1;
			long lSignUserID = -1;
			long lStatusID = -1;
			long lTransactionTypeID = -1;
			long lTypeID = -1;
			String strAbstract = "";
			String strCheckAbstract = "";
			String strConfirmAbstract = "";
			String strConsignPassword = "";
			String strConsignVoucherNo = "";
			String strDeclarationNo = "";
			String strEmptyTransNo = "";
			String strExtAccountNo = "";
			String strExtClientName = "";
			String strRemitInBank = "";
			String strRemitInCity = "";
			String strRemitInProvince = "";
			String strTransNo = "";
			java.sql.Timestamp tsExecuteDate = null;
			java.sql.Timestamp tsInputDate = null;
			java.sql.Timestamp tsInterestStartDate = null;
			java.sql.Timestamp tsModifyDate = null;

			//页面辅助变量
			String strAction = null;
			String strActionResult = Constant.ActionResult.FAIL;
			String strPreSaveResult = null;

			//付款方客户回显信息
			String strPayClientCode = null;
			String strPayClientName = null;

			//收款总账类回显信息
			String strReceiveGLName = null;
			
			//付款总账类回显信息
			String strPayGLName = null;

			//账户回显信息
			String strReceiveClientCode = null;
			String strReceiveClientName = null;
			String strReceiveAccountNo = null;

			//开户行回显信息
			String strBranchName = null;

			//单据信息
			String strInputUserName = null;
			String strExecuteDate = null;
			String strInterestStartDate = null;
			String strModifyDate = null;
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
			TransOnePayMultiReceiveInfo resultInfo = null;
			resultInfo = (TransOnePayMultiReceiveInfo)request.getAttribute("searchResults");

			if(resultInfo != null)
			{
				dAmount = resultInfo.getAmount();
				lAbstractID = resultInfo.getAbstractID();
				lAccountID = resultInfo.getAccountID();
				lBankID = resultInfo.getBankID();
				lCashFlowID = resultInfo.getCashFlowID();
				lCheckUserID = resultInfo.getCheckUserID();
				lConfirmOfficeID = resultInfo.getConfirmOfficeID();
				lConfirmUserID = resultInfo.getConfirmUserID();
				lCurrencyID = resultInfo.getCurrencyID();
				lId = resultInfo.getId();
				lInputUserID = resultInfo.getInputUserID();
				lIsBank = resultInfo.getIsBank();
				lIsGL = resultInfo.getIsGL();
				lIsInternalVirement = resultInfo.getIsInternalVirement();
				lOfficeID = resultInfo.getOfficeID();
				lPayClientID = resultInfo.getPayClientID();
				lPayGL = resultInfo.getPayGL();
				lReceiveClientID = resultInfo.getReceiveClientID();
				lReceiveGL = resultInfo.getReceiveGL();
				lSerialNo = resultInfo.getSerialNo();
				lSignUserID = resultInfo.getSignUserID();
				lStatusID = resultInfo.getStatusID();
				lTransactionTypeID = resultInfo.getTransactionTypeID();
				lTypeID = resultInfo.getTypeID();
				strAbstract = resultInfo.getAbstract();
				strCheckAbstract = resultInfo.getCheckAbstract();
				strConfirmAbstract = resultInfo.getConfirmAbstract();
				strConsignPassword = resultInfo.getConsignPassword();
				strConsignVoucherNo = resultInfo.getConsignVoucherNo();
				strDeclarationNo = resultInfo.getDeclarationNo();
				strEmptyTransNo = resultInfo.getEmptyTransNo();
				strExtAccountNo = resultInfo.getExtAccountNo();
				strExtClientName = resultInfo.getExtClientName();
				strRemitInBank = resultInfo.getRemitInBank();
				strRemitInCity = resultInfo.getRemitInCity();
				strRemitInProvince = resultInfo.getRemitInProvince();
				strTransNo = resultInfo.getTransNo();
				tsExecuteDate = resultInfo.getExecuteDate();
				tsInputDate = resultInfo.getInputDate();
				tsInterestStartDate = resultInfo.getInterestStartDate();
				tsModifyDate = resultInfo.getModifyDate();
			}
			//格式化数据
			strAbstract = DataFormat.formatString(strAbstract);
			strCheckAbstract = DataFormat.formatString(strCheckAbstract);
			strConfirmAbstract = DataFormat.formatString(strConfirmAbstract);
			strConsignPassword = DataFormat.formatString(strConsignPassword);
			strConsignVoucherNo = DataFormat.formatString(strConsignVoucherNo);
			strDeclarationNo = DataFormat.formatString(strDeclarationNo);
			strEmptyTransNo = DataFormat.formatString(strEmptyTransNo);
			strExtAccountNo = DataFormat.formatString(strExtAccountNo);
			strExtClientName = DataFormat.formatString(strExtClientName);
			strRemitInBank = DataFormat.formatString(strRemitInBank);
			strRemitInCity = DataFormat.formatString(strRemitInCity);
			strRemitInProvince = DataFormat.formatString(strRemitInProvince);
			strTransNo = DataFormat.formatString(strTransNo);
			
			if(strPayClientCode == null)
			{
				strPayClientCode = NameRef.getClientCodeByID(lPayClientID);	
				
				strPayClientName = NameRef.getClientNameByID(lReceiveClientID);
			}
			strPayClientCode = DataFormat.formatString(strPayClientCode);
			strPayClientName = DataFormat.formatString(strPayClientName);

			if(strReceiveClientCode == null)
			{
				strReceiveClientCode = NameRef.getClientCodeByID(lReceiveClientID);
				
				strReceiveClientName = NameRef.getClientNameByID(lReceiveClientID);
			}
			strReceiveClientCode = DataFormat.formatString(strReceiveClientCode);
			strReceiveClientName = DataFormat.formatString(strReceiveClientName);

			if(strReceiveAccountNo == null)
			{
				strReceiveAccountNo = NameRef.getAccountNoByID(lAccountID);
			}
			strReceiveAccountNo = DataFormat.formatString(strReceiveAccountNo);

			if(strReceiveGLName == null)
			{
				strReceiveGLName = NameRef.getGLTypeDescByID(lReceiveGL);
			}
			strReceiveGLName = DataFormat.formatString(strReceiveGLName);

			if(strPayGLName == null)
			{
				strPayGLName = NameRef.getGLTypeDescByID(lPayGL);
			}
			strPayGLName = DataFormat.formatString(strPayGLName);

			if(strBranchName == null)
			{
				strBranchName = NameRef.getBankNameByID(lBankID);
			}
			strBranchName = DataFormat.formatString(strBranchName);

			strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
			strInputDate = DataFormat.formatDate(tsInputDate);
			strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));

			strExecuteDate = DataFormat.formatDate(tsExecuteDate);
			strInterestStartDate = DataFormat.formatDate(tsInterestStartDate);
			strModifyDate = String.valueOf(tsModifyDate.getTime());

			strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));
%>
	<form name="frmV097" method="post" action="">
		<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
        <input name="strAction"  type="hidden">
		<input name="strSuccessPageURL"  type="hidden" value="<%
				if(lStatusID == SETTConstant.TransactionStatus.SAVE)
				{out.print("../a397-v.jsp");
				}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
				{out.print("../a313-c.jsp");}%>">
		<input name="strFailPageURL"  type="hidden" value="../a313-c.jsp">
		<input name="lId"  type="hidden" value="<%=lId%>">
		<input name="lTransactionTypeID"  type="hidden" value="<%=lTransactionTypeID%>">
		<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
		<input name="strModifyDate"  type="hidden" value="<%=strModifyDate%>">
		<TABLE class="top" height="200" width="760">
			<TBODY>
				<TR class="tableHeader">
					<TD class="FormTitle" height="13"><B>业务复核 —— 多借多贷</B>
					</TD>
				</TR>
				<TR>
					<TD height="100">
						<TABLE align="center" height="129" width="97%">
							<TBODY>
								<TR bordercolor="#ffffff">
									<TD height="2" width="15%" nowrap>	收付方向：
									</TD>
									<TD height="2">										
										<%SETTConstant.ReceiveOrPay.showList(out, "lTypeID",0, lTypeID, false, true, "disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
									</TD>
									<TD height="2" width="14%">&nbsp;	
									</TD>
									<TD height="2" width="35%">&nbsp;	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="2" width="15%" nowrap>	付款方客户编号：
									</TD>
									<TD>	
										<INPUT class=box disabled name="strPayClientCode" size=8 value="<%=strPayClientCode%>">
									</TD>
									<TD width="14%">&nbsp;	
									</TD>
									<TD width="35%">&nbsp;	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" nowrap>	银行报单号：
									</TD>
									<TD nowrap>	
										<input type="text" name="strDeclarationNo" class="box" size="20" maxlength="15" value="<%=strDeclarationNo%>" disabled>
									</TD>
									<TD nowrap>	总账类类型:										
									</TD>
									<TD nowrap>	
										<input type="text" name="lPayGL" class="box" size="20" maxlength="15" value="<%=strPayGLName%>" disabled>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="4">
<HR>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="4" height="2">										
										<INPUT type=radio disabled name=radiobutton value=radiobutton <%=(lAccountID != -1)?"checked":""%>>内部账户
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="2" width="15%" nowrap>	客户编号：
									</TD>
									<TD height="2">										
										<INPUT class=box disabled name="strReceiveClientCode" size=8 value="<%=strReceiveClientCode%>">		
									</TD>
									<TD height="2" width="14%" nowrap>	客户名称：
									</TD>
									<TD height="2" width="35%">
										<TEXTAREA class="box" disabled name="strReceiveClientName" rows="2" cols="30"><%=strReceiveClientName%></TEXTAREA>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
<script language="javascript">showDisableAccountCtrl("strAccountNo","<%=strReceiveAccountNo%>","账号","height='35' width='50%'","height='35' width='50%'");
</script>
									<TD height="2" width="14%">&nbsp;	
									</TD>
									<TD height="2" width="35%">&nbsp;	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="4" height="20">										
										<INPUT name=radiobutton disabled type=radio value=radiobutton <%=(lBankID != -1)?"checked":""%>>银行
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" width="15%" nowrap>	开户行
									</TD>
									<TD height="20">										
										<INPUT class=box disabled name="strBranchName" size=30 value="<%=strBranchName%>">
									</TD>
									<TD height="20" width="14%">&nbsp;	
									</TD>
									<TD height="20" width="35%">&nbsp;	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" width="15%" nowrap>	收款方账号：
									</TD>
									<TD height="20" nowrap>										
										<INPUT class=box disabled name="strExtAccountNo" size=24 value="<%=strExtAccountNo%>">										
									</TD>
									<TD height="20" width="14%" nowrap>	客户名称：
									</TD>
									<TD height="20" width="35%">
										<TEXTAREA class="box" disabled name="strExtClientName" rows="2" cols="30"><%=strExtClientName%></TEXTAREA>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="29" width="20%" nowrap>	汇入地(省)：
									</TD>
									<TD height="29" width="6%">			
										<INPUT class=box disabled name="strRemitInProvince" value="<%=strRemitInProvince%>">
									</TD>
									<TD height="29" width="10%" nowrap>	汇入地(市)：
									</TD>
									<TD height="29" width="17%">			
										<INPUT class=box disabled name="strRemitInCity" value="<%=strRemitInCity%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="29" width="13%" nowrap>	汇入行名称：
									</TD>
									<TD height="29" width="34%" colspan="3">							
										<TEXTAREA class="box" disabled name="：strRemitInBank" rows="2" cols="30"><%=strRemitInBank%></TEXTAREA>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="25" nowrap>										
										<INPUT name=radiobutton disabled type=radio value=radiobutton <%=(lReceiveGL != -1)?"checked":""%>>总账类类型
									</TD>
									<TD nowrap>	
										<INPUT class=box disabled name="strReceiveGLName" value="<%=strReceiveGLName%>">
									</TD>
									<TD nowrap>&nbsp;	
									</TD>
									<TD nowrap>&nbsp;	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="4" height="2">
<HR>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="2" width="15%" nowrap>	金 额：
									</TD>
									<TD height="2">	￥										
										<INPUT class=box disabled name="dAmount" size=17 value="<%=dAmount%>">
									</TD>
									<TD height="2" width="14%" nowrap>	摘 要：
									</TD>
									<TD height="2" width="35%">										
										<INPUT class=box disabled name="strAbstract1" size=40 value="<%=strAbstract%>">	
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="32" width="15%" nowrap>	起息日：
									</TD>
									<TD height="32">										
										<INPUT class=box disabled name="tsInterestStart1" value="<%=strInterestStartDate%>">
									</TD>
									<TD align="left" height="32" width="14%" nowrap>	执行日：
									</TD>
									<TD align="left" height="32" width="35%">										
										<INPUT class=box disabled name="tsExecute1" value="<%=strExecuteDate%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="4" align="right">	
											<div align="right">
												<input type="button" name="print" value=" 打 印 " class="button" onClick="doPrint();">
												<input type="button" name="close" value=" 关 闭 " class="button" onClick="window.close();">
											</div>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<tr>
					<td height="2" width="95%">
						<hr>
						<table width="97%" border="0" height="22" align="center">
							<tr valign="middle">
								<td width="9%" height="25">	<%=(lStatusID == SETTConstant.TransactionStatus.SAVE)?"复核备注":"取消复核备注"%>：
								</td>
								<td width="16%" height="25" valign="top">
									<input type="text" name="strCheckAbstract" class="box" value="<%=strCheckAbstract%>" onFocus="nextfield ='submitfunction';">
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
					</td>
				</tr>
			</TBODY>
		</TABLE>
	</form>
	
<script language="JavaScript">
//标识是否已提交请求
var isSubmited = false;

function checkSuccess()
{
	alert("复核成功!");
    //if (confirm("复核成功，是否打印?"))
    //{
        //code
    //}
	document.location.href="<%=strContext%>/settlement/tran/current/view/v096.jsp";
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
		document.frmV097.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		showSending();
		document.frmV097.submit();
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
		if(!validateFields(frmV097)) return;

		isSubmited = true;
		document.frmV097.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		showSending();
		document.frmV097.submit();
	}
}

function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a410-v.jsp?lID=<%=lId%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a410-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a397-v.jsp");
}

function doReturn()
{
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/current/control/c013.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.ONETOMULTI%>&strSuccessPageURL=../view/v099.jsp&strFailPageURL=../view/v099.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%
	}else 
	{%>
		document.location.href="<%=strContext%>/settlement/tran/current/view/v096.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strCheckAbstract","取消复核备注","string",1);
} 
</script>

<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV097.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV097");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV097.strCheckAbstract);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV097");     
</script>
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
