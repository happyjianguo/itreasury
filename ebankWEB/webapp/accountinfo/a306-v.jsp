<%--
/**
 页面名称 ：a306-v.jsp
 页面功能 : 总账类业务复核－复核页面
 作    者 ：kewen hu
 日    期 ：2003-11-14
 特殊说明 ：简单实现说明：
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
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo"%>
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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a306-v.jsp******\n");
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
			double dAmountOne = 0.0;
			double dAmountThree = 0.0;
			double dAmountTwo = 0.0;
			double dSumForSearch = 0.0;
			long lAbstractID = -1;
			long lAccountID = -1;
			long lBillBankID = -1;
			long lBillTypeID = -1;
			long lCheckUserID = -1;
			long lClientID = -1;
			long lConfirmOfficeID = -1;
			long lConfirmUserID = -1;
			long lCurrencyID = -1;
			long lDirOne = -1;
			long lDirThree = -1;
			long lDirTwo = -1;
			long lDirection = -1;
			long lGeneralLedgerOne = -1;
			long lGeneralLedgerThree = -1;
			long lGeneralLedgerTwo = -1;
			long lID = -1;
			long lInputUserID = -1;
			long lOfficeID = -1;
			long lSignUserID = -1;
			long lStatusID = -1;
			long lTransTypeID = -1;
			String strAbstract = "";
			String strBillNo = "";
			String strCancelCheckAbstract = "";
			String strCheckAbstract = "";
			String strConfirmAbstract = "";
			String strPayExtBankNo = "";
			String strReceiveExtBankNo = "";
			String strTransNo = "";
			String strVoucherNo = "";
			String strVoucherPWD = "";
			java.sql.Timestamp tsExecuteDate = null;
			java.sql.Timestamp tsInputDate = null;
			java.sql.Timestamp tsInterestStartDate = null;
			java.sql.Timestamp tsModifyDate = null;

			//页面辅助变量
			String strAction = null;
			String strActionResult = null;
			String strPreSaveResult = null;

			String strExecuteDate = null;
			String strInterestStartDate = null;
			String strModifyDate = null;

			//客户回显信息
			String strClientCode = null;
			String strClientName = null;

			//账户回显信息			
			String strAccountNo = null;

			//总账类回显信息
			String strGeneralLedgerOneName = null;
			String strGeneralLedgerTwoName = null;
			String strGeneralLedgerThreeName = null;

			//单据信息
			String strInputUserName = null;
			String strInputDate = null;
			String strStatus = null;
			String strCheckUserName = null;

			double dDebitAmount = 0.0;
			double dCreditAmount = 0.0;
			
			//从Request中获得参数
			//页面控制参数
			if (request.getAttribute("strActionResult") != null)
			{
				  strActionResult = (String)request.getAttribute("strActionResult");
			}
			if (request.getAttribute("strAction") != null)
			{
				  strAction = (String)request.getAttribute("strAction");
			}
			if (request.getAttribute("strPreSaveResult") != null)
			{
				  strPreSaveResult = (String)request.getAttribute("strPreSaveResult");
			}
			String sReturn = (String) request.getAttribute("lReturn");
			long lReturn = -1;
			if (sReturn != null && sReturn.trim().length() > 0) {
				lReturn = Long.parseLong(sReturn);
			}
			Log.print("=============lReturn="+lReturn);
			//从Request中获得参数
			TransGeneralLedgerInfo resultInfo = null;
			resultInfo = (TransGeneralLedgerInfo)request.getAttribute("searchResults");

			if(resultInfo != null)
			{
				dAmount = resultInfo.getAmount();
				dAmountOne = resultInfo.getAmountOne();
				dAmountThree = resultInfo.getAmountThree();
				dAmountTwo = resultInfo.getAmountTwo();
				dSumForSearch = resultInfo.getSumForSearch();
				lAbstractID = resultInfo.getAbstractID();
				lAccountID = resultInfo.getAccountID();
				lBillBankID = resultInfo.getBillBankID();
				lBillTypeID = resultInfo.getBillTypeID();
				lCheckUserID = resultInfo.getCheckUserID();
				lClientID = resultInfo.getClientID();
				lConfirmOfficeID = resultInfo.getConfirmOfficeID();
				lConfirmUserID = resultInfo.getConfirmUserID();
				lCurrencyID = resultInfo.getCurrencyID();
				lDirOne = resultInfo.getDirOne();
				lDirThree = resultInfo.getDirThree();
				lDirTwo = resultInfo.getDirTwo();
				lDirection = resultInfo.getDirection();
				lGeneralLedgerOne = resultInfo.getGeneralLedgerOne();
				lGeneralLedgerThree = resultInfo.getGeneralLedgerThree();
				lGeneralLedgerTwo = resultInfo.getGeneralLedgerTwo();
				lID = resultInfo.getID();
				lInputUserID = resultInfo.getInputUserID();
				lOfficeID = resultInfo.getOfficeID();
				lSignUserID = resultInfo.getSignUserID();
				lStatusID = resultInfo.getStatusID();
				lTransTypeID = resultInfo.getTransActionTypeID();
				strAbstract = resultInfo.getAbstract();
				strBillNo = resultInfo.getBillNo();
				strCancelCheckAbstract = resultInfo.getCancelCheckAbstract();
				strCheckAbstract = resultInfo.getCheckAbstract();
				strConfirmAbstract = resultInfo.getConfirmAbstract();
				strPayExtBankNo = resultInfo.getPayExtBankNo();
				strReceiveExtBankNo = resultInfo.getReceiveExtBankNo();
				strTransNo = resultInfo.getTransNo();
				strVoucherNo = resultInfo.getVoucherNo();
				strVoucherPWD = resultInfo.getVoucherPWD();
				tsExecuteDate = resultInfo.getExecuteDate();
				tsInputDate = resultInfo.getInputDate();
				tsInterestStartDate = resultInfo.getInterestStartDate();
				tsModifyDate = resultInfo.getModifyDate();
				
				if(dAmount != 0.0)
				{
					if(lDirection == SETTConstant.DebitOrCredit.DEBIT)
					{
						dDebitAmount += dAmount;
					}
					else if(lDirection == SETTConstant.DebitOrCredit.CREDIT)
					{
						dCreditAmount += dAmount;
					}
				}
				if(dAmountOne != 0.0)
				{
					if(lDirOne == SETTConstant.DebitOrCredit.DEBIT)
					{
						dDebitAmount += dAmountOne;
					}
					else if(lDirOne == SETTConstant.DebitOrCredit.CREDIT)
					{
						dCreditAmount += dAmountOne;
					}
				}
				if(dAmountTwo != 0.0)
				{
					if(lDirTwo == SETTConstant.DebitOrCredit.DEBIT)
					{
						dDebitAmount += dAmountTwo;
					}
					else if(lDirTwo == SETTConstant.DebitOrCredit.CREDIT)
					{
						dCreditAmount += dAmountTwo;
					}
				}
				if(dAmountThree != 0.0)
				{
					if(lDirThree == SETTConstant.DebitOrCredit.DEBIT)
					{
						dDebitAmount += dAmountThree;
					}
					else if(lDirThree == SETTConstant.DebitOrCredit.CREDIT)
					{
						dCreditAmount += dAmountThree;
					}
				}
			}
			
			//格式化数据
			strAbstract = DataFormat.formatString(strAbstract);
			strBillNo = DataFormat.formatString(strBillNo);
			strCancelCheckAbstract = DataFormat.formatString(strCancelCheckAbstract);
			strCheckAbstract = DataFormat.formatString(strCheckAbstract);
			strConfirmAbstract = DataFormat.formatString(strConfirmAbstract);
			strPayExtBankNo = DataFormat.formatString(strPayExtBankNo);
			strReceiveExtBankNo = DataFormat.formatString(strReceiveExtBankNo);
			strTransNo = DataFormat.formatString(strTransNo);
			strVoucherNo = DataFormat.formatString(strVoucherNo);
			strVoucherPWD = DataFormat.formatString(strVoucherPWD);

			if(strClientCode == null)
			{
				strClientCode = NameRef.getClientCodeByID(lClientID);
				
				strClientName = NameRef.getClientNameByID(lClientID);				
			}
			strClientCode = DataFormat.formatString(strClientCode);
			strClientName = DataFormat.formatString(strClientName);

			if(strAccountNo == null)
			{
				strAccountNo = NameRef.getAccountNoByID(lAccountID);				
			}
			strAccountNo = DataFormat.formatString(strAccountNo);

			if(strGeneralLedgerOneName == null)
			{
				strGeneralLedgerOneName = NameRef.getGLTypeDescByID(lGeneralLedgerOne);
			}
			strGeneralLedgerOneName = DataFormat.formatString(strGeneralLedgerOneName);

			if(strGeneralLedgerTwoName == null)
			{
				strGeneralLedgerTwoName = NameRef.getGLTypeDescByID(lGeneralLedgerTwo);
			}
			strGeneralLedgerTwoName = DataFormat.formatString(strGeneralLedgerTwoName);

			if(strGeneralLedgerThreeName == null)
			{
				strGeneralLedgerThreeName = NameRef.getGLTypeDescByID(lGeneralLedgerThree);
			}
			strGeneralLedgerThreeName = DataFormat.formatString(strGeneralLedgerThreeName);

			strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
			strInputDate = DataFormat.formatDate(tsInputDate);
			strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));
			
			strExecuteDate = DataFormat.formatDate(tsExecuteDate);
			strInterestStartDate = DataFormat.formatDate(tsInterestStartDate);
			strModifyDate = String.valueOf(tsModifyDate.getTime());

			strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));
%>
	<FORM name="frmV005" method="post" action="">		
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">
		<input name="strSuccessPageURL" type="hidden" value="">
		<input name="strFailPageURL" type="hidden" value="">
		<input name="lID" type="hidden" value="<%=lID%>">
		<input name="lTransTypeID" type="hidden" value="<%=lTransTypeID%>">
		<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
		<input name="strModifyDate"  type="hidden" value="<%=strModifyDate%>">
		<TABLE class="top" height="8" width="760">
			<TBODY>
				<TR class="tableHeader">
					<TD class="FormTitle" height="2"><B>总账类</B>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE align="center" height="129" width="99%">
							<TBODY>
								<TR bordercolor="#ffffff">
									<TD height=20>
									</TD>
									<td>客户编号：</td>
									<td>
										<input type="text" name="lClientIDCtrl" class="box"  maxlength='30' value='<%=strClientCode%>' disabled>
									</td>
									<TD colspan="2" align="right">	客户名称：
									</TD>
									<TD>
										<TEXTAREA name="strClientName" readonly bgcolor="#FF00" rows="2" cols="30" disabled><%=strClientName%></TEXTAREA>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20">
									</TD>
<script language="javascript">
											showDisableAccountCtrl("strAccountNo","<%=strAccountNo%>","账号",""," colspan=4");
</script>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20">
									</TD>
									<TD nowrap>	借贷方向：
									</TD>
									<TD>
										<%SETTConstant.DebitOrCredit.showList(out, "lDirection",0, lDirection, false, false, "disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
									</TD>
									<TD colspan="2" nowrap align="right">	金额：
									</TD>
									<TD>￥
										<INPUT type="text" name="je" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmount)%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="6" height="20">
<HR>
									</TD>	<!--划一行横线-->
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" width="5%" nowrap>										
										<INPUT name="GLCheckBox" type="checkbox" onfocus="nextfield ='lGeneralLedgerOneCtrl';" value="1" disabled <%=(lGeneralLedgerOne!=-1)? "checked" : ""%>>
									</TD>
									<td width="10%">总账类类型：
									</td>
									<td width="20%">
										<input type="text" name="lGeneralLedgerOneCtrl" class="box"  maxlength='30' value='<%=strGeneralLedgerOneName%>' disabled>
									</td>
									<TD width="20%" align="center">
										<%SETTConstant.DebitOrCredit.showList(out, "lDirOne",0, lDirOne, false, false, "disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
									</TD>
									<TD  width="10%" nowrap align="right">	金额：
									</TD>
									<TD>￥
										<INPUT type="text" name="je" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmountOne)%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" nowrap>										
										<INPUT name="GLCheckBox" type="checkbox" value="2" onfocus="nextfield ='lGeneralLedgerTwoCtrl';" <%=(lGeneralLedgerTwo!=-1)? "checked" : ""%> disabled>
									</TD>
									<td>总账类类型：
									</td>
									<td>
										<input type="text" name="lGeneralLedgerTwoCtrl" class="box"  maxlength='30' value='<%=strGeneralLedgerTwoName%>' disabled>
									</td>
									<TD align="center">
										<%SETTConstant.DebitOrCredit.showList(out, "lDirTwo",0, lDirTwo, false, false, "disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
									</TD>
									<TD nowrap align="right">	金额：
									</TD>
									<TD>￥
										<INPUT type="text" name="je" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmountTwo)%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD nowrap>										
										<INPUT name="GLCheckBox" type="checkbox" value="3" onfocus="nextfield ='lGeneralLedgerThreeCtrl';" <%=(lGeneralLedgerThree!=-1)? "checked" : ""%> disabled>
									</TD>
									<td>总账类类型：
									</td>
									<td>
										<input type="text" name="lGeneralLedgerThreeCtrl" class="box"  maxlength='30' value='<%=strGeneralLedgerThreeName%>' disabled>
									</td>
									<TD align="center">
										<%SETTConstant.DebitOrCredit.showList(out, "lDirThree",0, lDirThree, false, false, "disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
									</TD>
									<TD nowrap align="right">	金额：
									</TD>
									<TD>￥
										<INPUT type="text" name="je" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmountThree)%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20">&nbsp;	
									</TD>
									<TD colspan="4" nowrap align="right">	借方金额总计：
									</TD>
									<TD>￥										
										<INPUT type="text" name="dDebitAmount" value="<%=DataFormat.formatDisabledAmount(dDebitAmount)%>" class="tar" size="18"  disabled>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20" >&nbsp;	
									</TD>
									<TD colspan="4" nowrap align="right">	贷方金额总计：
									</TD>
									<TD>￥										
										<INPUT type="text" name="dCreditAmount" value="<%=DataFormat.formatDisabledAmount(dCreditAmount)%>" class="tar" size="18" disabled>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD colspan="6" height="20">
<HR>
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height="20">
									</TD>
									<TD nowrap>	起息日：
									</TD>
									<TD colspan="2" nowrap>										
										<INPUT type="text" name="qxr" size="20" disabled class="box" value="<%=strInterestStartDate%>">
									</TD>
									<TD align="left" nowrap>	执行日：
									</TD>
									<TD align="left" nowrap>										
										<INPUT type="text" name="zxr" size="20" disabled class="box" value="<%=strExecuteDate%>">
									</TD>
								</TR>
								<TR bordercolor="#ffffff">
									<TD height=20>
									</TD>
									<td>	交易号：
									</td>
									<td  colspan="2">
										<input type="text" name="strTransNo" size="20" class="box" value="<%=strTransNo%>" disabled>
									</td>
									<TD>	摘要：
									</TD>
									<TD>											
										<INPUT type="text" name="zy" maxlength="2000" disabled size="20" class="box" value="<%=strAbstract%>">
									</TD>
								</TR>								
								<TR bordercolor="#ffffff">
									<TD align="right" colspan="6">										
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
					<td height="26" valign="top" width="100%">
						<hr>
						<table width="97%" border="0" align="center">
							<tr>
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
	</FORM>
<%
	if(!"Query".equalsIgnoreCase(strAction))
	{
%>
<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV005.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV005");     
</script>
<%
	}else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV005.strCancelCheckAbstract);
//setSubmitFunction("doUndoCheck()");
setFormName("frmV005");     
</script>
<%
	}
%>
<%
	}
%>


	
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
	document.location.href="<%=strContext%>/settlement/tran/generalledger/view/v004.jsp";
	
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
		document.frmV005.strAction.value="<%=SETTConstant.Actions.CHECK%>";
		document.frmV005.submit();
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
		if(!validateFields(frmV005)) return;

		isSubmited = true;
		document.frmV005.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
		document.frmV005.submit();
	}
}

function doPrint()
{
	window.open("<%=strContext%>/accountinfo/a408-v.jsp?lID=<%=lID%>&lTransTypeID=<%=lTransTypeID%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a408-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a306-v.jsp");
}

function doReturn()
{
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/generalledger/control/c003.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransTypeID=<%=SETTConstant.TransactionType.GENERALLEDGER%>&strSuccessPageURL=../view/v006.jsp&strFailPageURL=../view/v006.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%
	}else
	{%>
		document.location.href="<%=strContext%>/settlement/tran/generalledger/view/v004.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strCancelCheckAbstract","取消复核备注","string",1);
} 
</script>
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