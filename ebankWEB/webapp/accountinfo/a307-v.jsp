<%--
/**
 页面名称 ：a307-v.jsp
 页面功能 : 交易费用业务处理－新增页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
 修改历史 ：
 */
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>	
<%@ page import="com.iss.itreasury.settlement.setting.dao.Sett_TransactionFeeTypeDAO"%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo"%>
<%@ page import="com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%!
public void showFeeSettingListCtrl(JspWriter out, String strControlName, String strProperty, long lSelectValue)
{
	Collection collSearchResult = null;
	try
	{
		Sett_TransactionFeeTypeDAO setDao = new Sett_TransactionFeeTypeDAO();

		TransFeeTypeSetInfo conditionInfo = new TransFeeTypeSetInfo();

		//设置查询条件
		conditionInfo.setStatusID(1);
	
		collSearchResult = setDao.findByConditions(conditionInfo, -1, false);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		collSearchResult = null;
	}

	try
	{
		out.println("<select class='box' name=\"" + strControlName + "\" " + strProperty + ">");
		
		if (lSelectValue == -1)
		{
			out.println("<option value='-1' selected>&nbsp;</option>");
		}
		else
		{
			out.println("<option value='-1'>&nbsp;</option>");
		}
		if(collSearchResult != null)
		{
			Iterator itTemp = collSearchResult.iterator();

			for (int i = 0; itTemp.hasNext(); i++)
			{			
				TransFeeTypeSetInfo tempInfo = (TransFeeTypeSetInfo)itTemp.next();
				if (tempInfo.getID() == lSelectValue)
				{
					out.println("<option value='" + tempInfo.getID() + "' selected >" + tempInfo.getName() + "</option>");
				}
				else
				{
					out.println("<option value='" + tempInfo.getID() + "'>" + tempInfo.getName() + "</option>");
				}
			}
		}
		out.println("</select>");

		//生成辅助的隐藏域
		if(collSearchResult != null)
		{
			Iterator itTemp = collSearchResult.iterator();
			while(itTemp.hasNext())
			{		
				TransFeeTypeSetInfo tempInfo = (TransFeeTypeSetInfo)itTemp.next();
				
				out.println("<INPUT type='hidden' name='FeeTypeIsHaveBank"+tempInfo.getID()+"' value='"+tempInfo.getIsHaveBank()+"'>");
			}
		}
	}
	catch (Exception ex)
	{
		Log.print("显示下拉列表出现异常：" + ex.toString());
	}
}
%>

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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a307-v.jsp******\n");
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
			long lBillBankID = -1;
			long lBillTypeID = -1;
			long lCashFlowID = -1;
			long lCheckUserID = -1;
			long lConfirmOfficeID = -1;
			long lConfirmUserID = -1;
			long lCurrencyID = -1;
			long lFeeBankID = -1;
			long lFeeTypeID = -1;
			long lID = -1;
			long lInputUserID = -1;
			long lOfficeID = -1;
			long lRelatedAccountID = -1;
			long lRelatedClientID = -1;
			long lRelatedContractID = -1;
			long lRelatedLoanNoteID = -1;
			long lRelatedSubAccountID = -1;
			long lRelatedTransNo = -1;
			long lRelatedTransTypeID = -1;
			long lRemitInBankID = -1;
			long lSignUserID = -1;
			long lStatusID = -1;
			long lTransactionTypeID = -1;
			String strAbstract = "";
			String strBillNo = "";
			String strCancleCheckAbstract = "";
			String strCheckAbstract = "";
			String strConfirmAbstract = "";
			String strExtAcctName = "";
			String strExtAcctNo = "";
			String strPayExtBankNo = "";
			String strRelatedFixedDepositNo = "";
			String strTransNo = "";
			java.sql.Timestamp tsExecuteDate = null;
			java.sql.Timestamp tsInputDate = null;
			java.sql.Timestamp tsInterestStartDate = null;
			java.sql.Timestamp tsModifyDate = null;

			//页面辅助变量
			String strAction = null;
			String strActionResult = null;
			String strPreSaveResult = null;
			
			//关联交易的回显信息
			String strRelatedClientName = null;
			String strRelatedClientCode = null;
			String strRelatedAccountNo = null;		
			//关联交易合同信息
			String strRelatedContractNo = null;
			//关联交易放款通知单信息
			String strRelatedLoanNoteNo = null;

			//费用收方开户行回显信息
			String strFeeBranchName = null;

			//支付费用账号回显信息
			String strAccountNo = null;	
			String strClientName = null;
			
			//汇入银行回显信息
			String strRemitInBranchName = null;

			String strExecuteDate = null;
			String strInputDate = null;
			String strInterestStartDate = null;
			String strModifyDate = null;

			//单据信息
			String strInputUserName = null;
			String strStatus = null;
			String strCheckUserName = null;
			
			//初始化变量
			strActionResult = Constant.ActionResult.FAIL;
			strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);

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
			String sReturn = (String) request.getAttribute("lReturn");
			long lReturn = -1;
			if (sReturn != null && sReturn.trim().length() > 0) {
				lReturn = Long.parseLong(sReturn);
			}
			Log.print("=============lReturn="+lReturn);
			//从Request中获得参数
			TransFeeInfo resultInfo = null;
			resultInfo = (TransFeeInfo)request.getAttribute("searchResults");

			if(resultInfo != null)
			{
				dAmount = resultInfo.getAmount();
				lAbstractID = resultInfo.getAbstractID();
				lAccountID = resultInfo.getAccountID();
				lBillBankID = resultInfo.getBillBankID();
				lBillTypeID = resultInfo.getBillTypeID();
				lCashFlowID = resultInfo.getCashFlowID();
				lCheckUserID = resultInfo.getCheckUserID();
				lConfirmOfficeID = resultInfo.getConfirmOfficeID();
				lConfirmUserID = resultInfo.getConfirmUserID();
				lCurrencyID = resultInfo.getCurrencyID();
				lFeeBankID = resultInfo.getFeeBankID();
				lFeeTypeID = resultInfo.getFeeTypeID();
				lID = resultInfo.getID();
				lInputUserID = resultInfo.getInputUserID();
				lOfficeID = resultInfo.getOfficeID();
				lRelatedAccountID = resultInfo.getRelatedAccountID();
				lRelatedClientID = resultInfo.getRelatedClientID();
				lRelatedContractID = resultInfo.getRelatedContractID();
				lRelatedLoanNoteID = resultInfo.getRelatedLoanNoteID();
				lRelatedSubAccountID = resultInfo.getRelatedSubAccountID();
				lRelatedTransNo = resultInfo.getRelatedTransNo();
				lRelatedTransTypeID = resultInfo.getRelatedTransTypeID();
				lRemitInBankID = resultInfo.getRemitInBankID();
				lSignUserID = resultInfo.getSignUserID();
				lStatusID = resultInfo.getStatusID();
				lTransactionTypeID = resultInfo.getTransactionTypeID();
				strAbstract = resultInfo.getAbstract();
				strBillNo = resultInfo.getBillNo();
				strCancleCheckAbstract = resultInfo.getCancleCheckAbstract();
				strCheckAbstract = resultInfo.getCheckAbstract();
				strConfirmAbstract = resultInfo.getConfirmAbstract();
				strExtAcctName = resultInfo.getExtAcctName();
				strExtAcctNo = resultInfo.getExtAcctNo();
				strPayExtBankNo = resultInfo.getPayExtBankNo();
				strRelatedFixedDepositNo = resultInfo.getRelatedFixedDepositNo();
				strTransNo = resultInfo.getTransNo();
				tsExecuteDate = resultInfo.getExecuteDate();
				tsInputDate = resultInfo.getInputDate();
				tsInterestStartDate = resultInfo.getInterestStartDate();
				tsModifyDate = resultInfo.getModifyDate();
			}
			
			//格式化数据
			strAbstract = DataFormat.formatString(strAbstract);
			strBillNo = DataFormat.formatString(strBillNo);
			strCancleCheckAbstract = DataFormat.formatString(strCancleCheckAbstract);
			strCheckAbstract = DataFormat.formatString(strCheckAbstract);
			strConfirmAbstract = DataFormat.formatString(strConfirmAbstract);
			strExtAcctName = DataFormat.formatString(strExtAcctName);
			strExtAcctNo = DataFormat.formatString(strExtAcctNo);
			strPayExtBankNo = DataFormat.formatString(strPayExtBankNo);
			strRelatedFixedDepositNo = DataFormat.formatString(strRelatedFixedDepositNo);
			strTransNo = DataFormat.formatString(strTransNo);
			
			if(strRelatedClientCode == null)
			{
				strRelatedClientCode = NameRef.getClientCodeByID(lRelatedClientID);
				
				strRelatedClientName = NameRef.getClientNameByID(lRelatedClientID);				
			}
			strRelatedClientCode = DataFormat.formatString(strRelatedClientCode);
			strRelatedClientName = DataFormat.formatString(strRelatedClientName);

			if(strRelatedAccountNo == null)
			{
				strRelatedAccountNo = NameRef.getAccountNoByID(lRelatedAccountID);				
			}
			strRelatedAccountNo = DataFormat.formatString(strRelatedAccountNo);

			if(strRelatedContractNo == null)
			{
				strRelatedContractNo = NameRef.getContractNoByID(lRelatedContractID);				
			}
			strRelatedContractNo = DataFormat.formatString(strRelatedContractNo);

			if(strRelatedLoanNoteNo == null)
			{
				strRelatedLoanNoteNo = NameRef.getPayFormNoByID(lRelatedLoanNoteID);				
			}
			strRelatedLoanNoteNo = DataFormat.formatString(strRelatedLoanNoteNo);

			if(strFeeBranchName == null)
			{
				strFeeBranchName = NameRef.getBankNameByID(lFeeBankID);				
			}
			strFeeBranchName = DataFormat.formatString(strFeeBranchName);

			if(strAccountNo == null)
			{
				strAccountNo = NameRef.getAccountNoByID(lAccountID);				
			}
			strAccountNo = DataFormat.formatString(strAccountNo);

			if(strClientName == null)
			{
				strClientName = NameRef.getClientNameByAccountID(lAccountID);				
			}
			strClientName = DataFormat.formatString(strClientName);

			if(strRemitInBranchName == null)
			{
				strRemitInBranchName = NameRef.getBankNameByID(lRemitInBankID);				
			}
			strRemitInBranchName = DataFormat.formatString(strRemitInBranchName);

			strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
			strInputDate = DataFormat.formatString(DataFormat.formatDate(tsInputDate));
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
		<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">
		<input name="lStatusID" type="hidden" value="<%=lStatusID%>">
		<input name="strModifyDate"  type="hidden" value="<%=strModifyDate%>">
		<TABLE width="89%" class="top" height="8">
			<TR class="tableHeader">
				<TD class="FormTitle" height="13"><B>交易费用</B>
				</TD>
			</TR>
			<TR>
				<TD height="215">
					<TABLE width="97%" height="129" align="center">
						<TR bordercolor="#FFFFFF">
							<TD width="15%" height="20">	对应业务类型：
							</TD>
							<TD colspan="3">
								<%SETTConstant.TransactionType.showList(out, "lRelatedTransTypeID", 0, lRelatedTransTypeID, false, true, " disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
							</TD>
							<TD colspan="2">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD height="20">	对应客户编号：
							</TD>
							<TD colspan="3">								
								<INPUT type="text" name="strRelatedClientCode" class = box value="<%=strRelatedClientCode%>" disabled>
							</TD>
							<TD colspan="2">	对应客户名称：
							</TD>
							<TD>
								<TEXTAREA name="strRelatedClientName" readonly bgcolor="#FF00" rows="2" cols="30" disabled><%=strRelatedClientName%></TEXTAREA>
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
<script language="javascript">
											showDisableAccountCtrl("strRelatedAccountNo","<%=strRelatedAccountNo%>","对应账号","width=\"15%\" height=\"20\""," colspan=3");
</script>
							<TD colspan="2">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD height="20">	对应合同号：
							</TD>
							<TD colspan="3">								
								<INPUT type="text" name="strRelatedContractNo" class = box value="<%=strRelatedContractNo%>" disabled>
							</TD>
							<TD colspan="2">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD height="20">	对应放款通知单号：
							</TD>
							<TD colspan="3">								
								<INPUT type="text" name="strRelatedLoanNoteNo" class = box value="<%=strRelatedLoanNoteNo%>" disabled>
							</TD>
							<TD colspan="2">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD height="20">	交易编号：
							</TD>
							<TD colspan="3">								
								<INPUT type="text" name="textfield232" class = box value="<%=(lRelatedTransNo!=-1)?String.valueOf(lRelatedTransNo):""%>" disabled>
							</TD>
							<TD colspan="2">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD colspan="7" height="20">
<HR>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="97%" height="9" align="center">
						<TR bordercolor="#FFFFFF">
							<TD width="17%" height="20">	交易费用类型：
							</TD>
							<TD>
								<%showFeeSettingListCtrl(out, "lFeeTypeID", "disabled", lFeeTypeID);%>
							</TD>
							<TD>	
							开户行：
							</TD>
							<TD>
								<TEXTAREA  name="textfield624225222" class="box" rows="2" cols="30" disabled><%=strFeeBranchName%></TEXTAREA>
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD colspan="7" height="2">
<HR>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="97%" height="129" align="center" border="0" bordercolor="#999999">
						<TR bordercolor="#E0DE89">
							<TD width="4%" height="32">
								<DIV align="center">									
									<INPUT type="radio" name="radiobutton" value="radiobutton" checked disabled>
								</DIV>
							</TD>
<script language="javascript">
											showDisableAccountCtrl("strAccountNo","<%=strAccountNo%>","支付费用账号"," width=16%"," colspan=3");
</script>
							<TD width="11%">&nbsp;	
							</TD>
							<TD width="6%">&nbsp;	
							</TD>
							<TD>&nbsp;	
							</TD>
						</TR>
						<TR bordercolor="#E0DE89">
							<TD height="20" width="4%">
								<DIV align="center">									
									<INPUT type="radio" name="radiobutton" value="radiobutton"  disabled>
								</DIV>
							</TD>
							<TD height="20" width="16%">	银行汇款账号:
							</TD>
							<TD height="20" colspan="3">								
								<INPUT type="text" name="textfield62422522" class="box" value="<%=strExtAcctNo%>" disabled>
							</TD>
							<TD height="20" align="left" colspan="2">	银行汇款方名称 ：
							</TD>
							<TD height="20" width="41%" align="left">
								<TEXTAREA  name="textfield6242252" class="box" rows="2" cols="30" disabled><%=strExtAcctName%></TEXTAREA>
							</TD>
						</TR>
						<TR bordercolor="#E0DE89">
							<TD height="20" width="4%">&nbsp;	
							</TD>
							<TD height="20" width="16%">	汇入银行名称：
							</TD>
							<TD height="20" colspan="3">
								<TEXTAREA  name="textfield6242242" class="box" rows="2" cols="30" disabled><%=strRemitInBranchName%></TEXTAREA>
							</TD>
							<TD height="20" align="left" colspan="2">&nbsp;	
							</TD>
							<TD height="20" width="41%" align="left">&nbsp;	
							</TD>
						</TR>
					</TABLE>
					<TABLE width="97%" height="129" align="center">
						<TR bordercolor="#FFFFFF">
							<TD colspan="7" height="20">
<HR>
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD width="14%" height="20">	金额：
							</TD>
							<TD height="21" colspan="6">	￥
								<INPUT type="text" name="je" size="17" disabled class="tar" value="<%=DataFormat.formatDisabledAmount(dAmount)%>">
							</TD>
						</TR>						
						<TR bordercolor="#FFFFFF">
							<TD height="20">	起息日：
							</TD>
							<TD colspan="3">								
								<INPUT type="text" name="qxr" size="20" disabled class="box" value="<%=strInterestStartDate%>">
							</TD>
							<TD align="left" colspan="2">	执行日：
							</TD>
							<TD align="left">								
								<INPUT type="text" name="zxr" size="20" disabled class="box" value="<%=strExecuteDate%>">
							</TD>
						</TR>
						<TR bordercolor="#FFFFFF">
							<TD height="20">	交易号：
							</TD>
							<TD colspan="3">								
								<input type="text" name="strTransNo" size="20" class="box" value="<%=strTransNo%>" disabled>
							</TD>
							<TD align="left" colspan="2">	摘 要：
							</TD>
							<TD align="left">								
								<INPUT type="text" name="textfield33" value="<%=strAbstract%>" class = box disabled>
							</TD>
						</TR>
					</TABLE>	
					<TABLE width="97%" height="37" align="center">
						<TR>
							<TD align=right>	&nbsp;
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
				<TD height="20">
<HR>
					<TABLE width="97%" height="20" align="center">
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
					</TABLE>
				</TD>
			</TR>
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
firstFocus(document.frmV005.strCancleCheckAbstract);
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
	document.location.href="<%=strContext%>/settlement/tran/fee/view/v004.jsp";
	
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
		window.open("<%=strContext%>/accountinfo/a408-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a408-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a307-v.jsp");
}

function doReturn()
{
	<%
	if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
	%>
		document.location.href="<%=strContext%>/settlement/tran/fee/control/c003.jsp?lStatusID=<%=SETTConstant.TransactionStatus.CHECK%>&lTransactionTypeID=<%=SETTConstant.TransactionType.TRANSFEE%>&strSuccessPageURL=../view/v006.jsp&strFailPageURL=../view/v006.jsp&strAction=<%=SETTConstant.Actions.LINKSEARCH%>";
	<%
	}else
	{%>
		document.location.href="<%=strContext%>/settlement/tran/fee/view/v004.jsp";
	<%}%>
}

function allFields()
{
	this.aa = new Array("strCancleCheckAbstract","取消复核备注","string",1);
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
    }
	catch( Exception exp )
	{
		exp.printStackTrace();
		Log.print(exp.getMessage());
	}
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
