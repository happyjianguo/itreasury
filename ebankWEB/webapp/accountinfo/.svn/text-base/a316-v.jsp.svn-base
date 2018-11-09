<%--
 页面名称 ：a316-v.jsp
 页面功能 : 定期支取复核页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
 修改历史 ：
 */
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
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.*" %>
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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a316-v.jsp******\n");
    //标题变量
    String strTitle = "";
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
		String strAbstract = "";
		long lAccountID = -1;
		String strAccountNo = "";
		double dAmount = 0.0;
		long lBankID = -1;
		String strBankName = "";
		long lCapitalAndInterestDealWay = -1;
		String strCashFlowDesc = "";
		long lCashFlowID = -1;
		long lCertificationBankID = -1;
		String strCertificationNo = "";
		String strCheckAbstract = "";
		long lCheckUserID = -1;
		String strCheckUserName = "";
		String strRemitInCity = "";
		long lClientID = -1;
		String strClientName = "";
		String strClientNo = "";
		long lCurrencyID = -1;
		String strCurrentAccountClientName = "";
		long lCurrentAccountID = -1;
		String strCurrentAccountNo = "";
		double dDepositBalance = 0.0;
		String strDepositNo = "";
		long lDepositTerm = -1;
		java.sql.Timestamp tsEndDate = null;
		java.sql.Timestamp tsExecuteDate = null;
		String strExtClientName = "";
		String strExtAcctNo = "";
		String strRemitInBank = "";
		long lID = -1;
		long lInputUserID = -1;
		String strInputUserName = "";
		String strInstructionNo = "";
		long lInterestPlanID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;
		long lNoticeDay = -1;
		long lOfficeID = -1;
		long lPayTypeID = -1;
		double dPreDrawInterest = 0.0;
		String strRemitInProvince = "";
		double dRate = 0.0;
		long lSealBankID = -1;
		String strSealNo = "";
		java.sql.Timestamp tsStartDate = null;
		long lStatusID = -1;
		long lSubAccountID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = "";
		long lAbstractID = -1;
		String strCapitalExtBankNo = "";
		long lConfirmOfficeID = -1;
		String strConfirmOfficeName = "";
		double dCurrentInterest = 0.0;
		double dDrawAmount = 0.0;
		double dDrawInterest = 0.0;
		long lInterestBankID = -1;
		String strInterestBankName = "";
		String strInterestCashFlowDesc = "";
		long lInterestCashFlowID = -1;
		String strInterestRemitInCity = "";
		String strInterestExtClientName = "";
		String strInterestExtAcctNo = "";
		String strInterestExtBankName = "";
		String strInterestExtBankNo = "";
		long lInterestPayTypeID = -1;
		String strInterestRemitInProvince = "";
		long lIsPreDraw = -1;
		long lIsTally = -1;
		double dOtherInterest = 0.0;
		double dPayableInterest = 0.0;
		String strReceiveInterestAccountClientName = "";
		long lReceiveInterestAccountID = -1;
		String strReceiveInterestAccountNo = "";
		double dStrikePreDrawInterest = 0.0;
		double dTotalUnpayInterest = 0.0;
		String strInterestRemitInBank = "";
		java.sql.Timestamp tsInputDate = null;
		//页面辅助变量
		String strAction = "";
		String strStatus = "";
		String strActionResult = Constant.ActionResult.FAIL;
		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);		
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strEndDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String inputDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = "";
		
		//利息合计
		double dTotalInterest  = 0.0;
		
		//从request取参数
		//网银财务指令接收
		String strOBInstr = (String)request.getAttribute("OBInstr");
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		//页面控制参数
		if (request.getAttribute("strActionResult") != null)
		{
			  strActionResult = (String)request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
			  strAction = (String)request.getAttribute("strAction");
		}				
		//业务数据
		String strTemp = null;
		strTemp = (String)request.getAttribute("strModifyTime");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strModifyTime = strTemp;
		}				
		String sReturn = (String) request.getAttribute("lReturn");
		long lReturn = -1;
		if (sReturn != null && sReturn.trim().length() > 0) {
			lReturn = Long.parseLong(sReturn);
		}
		Log.print("=============lReturn="+lReturn);
		String sTransactionTypeID = (String) request.getAttribute("lTransactionTypeID");
		if (sTransactionTypeID != null && sTransactionTypeID.trim().length() > 0) {
			lTransactionTypeID = Long.parseLong(sTransactionTypeID);
		}
		Log.print("=============lTransactionTypeID="+lTransactionTypeID);
		TransFixedDrawInfo info = null;
		info = (TransFixedDrawInfo)request.getAttribute("searchResults");

		if(info != null)
		{
			strAbstract = info.getAbstract();
			lAccountID = info.getAccountID();
			strAccountNo = info.getAccountNo();
			dAmount = info.getAmount();
			lBankID = info.getBankID();
			strBankName = info.getBankName();
			lCapitalAndInterestDealWay = info.getCapitalAndInterestDealWay();
			strCashFlowDesc = info.getCashFlowDesc();
			lCashFlowID = info.getCashFlowID();
			lCertificationBankID = info.getCertificationBankID();
			strCertificationNo = info.getCertificationNo();
			strCheckAbstract = info.getCheckAbstract();
			lCheckUserID = info.getCheckUserID();
			strCheckUserName = info.getCheckUserName();
			strRemitInCity = info.getRemitInCity();
			lClientID = info.getClientID();
			strClientName = info.getClientName();
			strClientNo = info.getClientNo();
			lCurrencyID = info.getCurrencyID();
			strCurrentAccountClientName = info.getCurrentAccountClientName();
			lCurrentAccountID = info.getCurrentAccountID();
			strCurrentAccountNo = info.getCurrentAccountNo();
			dDepositBalance = info.getDepositBalance();
			strDepositNo = info.getDepositNo();
			lDepositTerm = info.getDepositTerm();
			tsEndDate = info.getEndDate();
			tsExecuteDate = info.getExecuteDate();
			strExtClientName = info.getExtClientName();
			strExtAcctNo = info.getExtAcctNo();
			strRemitInBank = info.getRemitInBank();
			lID = info.getID();
			lInputUserID = info.getInputUserID();
			strInputUserName = info.getInputUserName();
			strInstructionNo = info.getInstructionNo();
			lInterestPlanID = info.getInterestPlanID();
			tsInterestStartDate = info.getInterestStartDate();
			tsModifyDate = info.getModifyDate();
			tsInputDate = info.getInputDate();
			lNoticeDay = info.getNoticeDay();
			lOfficeID = info.getOfficeID();
			lPayTypeID = info.getPayTypeID();
			dPreDrawInterest = info.getPreDrawInterest();
			strRemitInProvince = info.getRemitInProvince();
			dRate = info.getRate();
			lSealBankID = info.getSealBankID();
			strSealNo = info.getSealNo();
			tsStartDate = info.getStartDate();
			lStatusID = info.getStatusID();
			lSubAccountID = info.getSubAccountID();
			lTransactionTypeID = info.getTransactionTypeID();
			strTransNo = info.getTransNo();
			lAbstractID = info.getAbstractID();
			strCapitalExtBankNo = info.getCapitalExtBankNo();
			lConfirmOfficeID = info.getConfirmOfficeID();
			strConfirmOfficeName = info.getConfirmOfficeName();
			dCurrentInterest = info.getCurrentInterest();
			dDrawAmount = info.getDrawAmount();
			dDrawInterest = info.getDrawInterest();
			lInterestBankID = info.getInterestBankID();
			strInterestBankName = info.getInterestBankName();
			strInterestCashFlowDesc = info.getInterestCashFlowDesc();
			lInterestCashFlowID = info.getInterestCashFlowID();
			strInterestRemitInCity = info.getInterestRemitInCity();
			strInterestExtClientName = info.getInterestExtClientName();
			strInterestExtAcctNo = info.getInterestExtAcctNo();
			strInterestExtBankName = info.getInterestExtBankName();
			strInterestExtBankNo = info.getInterestExtBankNo();
			lInterestPayTypeID = info.getInterestPayTypeID();
			strInterestRemitInProvince = info.getInterestRemitInProvince();
			lIsPreDraw = info.getIsPreDraw();
			lIsTally = info.getIsTally();
			dOtherInterest = info.getOtherInterest();
			dPayableInterest = info.getPayableInterest();
			strReceiveInterestAccountClientName = info.getReceiveInterestAccountClientName();
			lReceiveInterestAccountID = info.getReceiveInterestAccountID();
			strReceiveInterestAccountNo = info.getReceiveInterestAccountNo();
			dStrikePreDrawInterest = info.getStrikePreDrawInterest();
			dTotalUnpayInterest = info.getTotalUnpayInterest();
			strInterestRemitInBank = info.getInterestRemitInBank();
			
			//自定义
			strStartDate = DataFormat.getDateString(tsStartDate);
			strEndDate = DataFormat.getDateString(tsEndDate);
			strExecuteDate = DataFormat.getDateString(tsExecuteDate);
			strInterestStartDate = DataFormat.getDateString(tsInterestStartDate);
			inputDate = DataFormat.getDateString(tsInputDate);
	}
	//格式化数据
		strAbstract = DataFormat.formatString(strAbstract);
		strAccountNo = DataFormat.formatString(strAccountNo);
		strBankName = DataFormat.formatString(strBankName);
		strCashFlowDesc = DataFormat.formatString(strCashFlowDesc);
		strCertificationNo = DataFormat.formatString(strCertificationNo);
		strCheckAbstract = DataFormat.formatString(strCheckAbstract);
		strCheckUserName = DataFormat.formatString(strCheckUserName);
		strRemitInCity = DataFormat.formatString(strRemitInCity);
		strClientName = DataFormat.formatString(strClientName);
		strClientNo = DataFormat.formatString(strClientNo);
		strCurrentAccountClientName = DataFormat.formatString(strCurrentAccountClientName);
		strCurrentAccountNo = DataFormat.formatString(strCurrentAccountNo);
		strDepositNo = DataFormat.formatString(strDepositNo);
		strExtClientName = DataFormat.formatString(strExtClientName);
		strExtAcctNo = DataFormat.formatString(strExtAcctNo);
		strRemitInBank = DataFormat.formatString(strRemitInBank);
		strInputUserName = DataFormat.formatString(strInputUserName);
		strInstructionNo = DataFormat.formatString(strInstructionNo);
		strRemitInProvince = DataFormat.formatString(strRemitInProvince);
		strSealNo = DataFormat.formatString(strSealNo);
		strTransNo = DataFormat.formatString(strTransNo);
		strCapitalExtBankNo = DataFormat.formatString(strCapitalExtBankNo);
		strConfirmOfficeName = DataFormat.formatString(strConfirmOfficeName);
		strInterestBankName = DataFormat.formatString(strInterestBankName);
		strInterestCashFlowDesc = DataFormat.formatString(strInterestCashFlowDesc);
		strInterestRemitInCity = DataFormat.formatString(strInterestRemitInCity);
		strInterestExtClientName = DataFormat.formatString(strInterestExtClientName);
		strInterestExtAcctNo = DataFormat.formatString(strInterestExtAcctNo);
		strInterestExtBankName = DataFormat.formatString(strInterestExtBankName);
		strInterestExtBankNo = DataFormat.formatString(strInterestExtBankNo);
		strInterestRemitInProvince = DataFormat.formatString(strInterestRemitInProvince);
		strReceiveInterestAccountClientName = DataFormat.formatString(strReceiveInterestAccountClientName);
		strReceiveInterestAccountNo = DataFormat.formatString(strReceiveInterestAccountNo);
		strInterestRemitInBank = DataFormat.formatString(strInterestRemitInBank);
		
		strCurrentAccountClientName = NameRef.getClientNameByAccountID(lCurrentAccountID);
		lClientID = NameRef.getClientIDByAccountID(lAccountID);
		
		if(strClientNo == null || strClientNo.equals(""))
		{			
			strClientNo = NameRef.getClientCodeByID(lClientID);
			strClientNo = DataFormat.formatString(strClientNo);
			strClientName = NameRef.getClientNameByID(lClientID);
			strClientName = DataFormat.formatString(strClientName);
		}		
		if(strAccountNo == null || strAccountNo.equals(""))
		{			
			strAccountNo = NameRef.getAccountNoByID(lAccountID);
			strAccountNo = DataFormat.formatString(strAccountNo);
		}

		if(strCurrentAccountNo == null || strCurrentAccountNo.equals(""))
		{			
			strCurrentAccountNo = NameRef.getAccountNoByID(lCurrentAccountID);
			strCurrentAccountNo = DataFormat.formatString(strCurrentAccountNo);
			strCurrentAccountClientName = NameRef.getClientNameByAccountID(lCurrentAccountID);			
		}
		if(strReceiveInterestAccountNo == null || strReceiveInterestAccountNo.equals(""))
		{			
			strReceiveInterestAccountNo = NameRef.getAccountNoByID(lReceiveInterestAccountID);
			strReceiveInterestAccountNo = DataFormat.formatString(strReceiveInterestAccountNo);
			strReceiveInterestAccountClientName = NameRef.getClientNameByAccountID(lReceiveInterestAccountID);			
		}
		if(strBankName == null || strBankName.equals(""))
		{
			strBankName = NameRef.getBankNameByID(lBankID);
			strBankName = DataFormat.formatString(strBankName);			
		}
		if(strInterestBankName == null || strInterestBankName.equals(""))
		{
			strInterestBankName = NameRef.getBankNameByID(lInterestBankID);
			strInterestBankName = DataFormat.formatString(strInterestBankName);			
		}
		
		strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));		
		strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));
		strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));
		
		dTotalInterest=dPayableInterest+dCurrentInterest+dOtherInterest+dPreDrawInterest;
    %>
	<form name="frmV015" action="" method="post">	
	<!--利用隐藏控件，来控制页面-->
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">
		<input name="strSuccessPageURL"  type="hidden" value="">
		<input name="strFailPageURL" type="hidden" value="">
		<input name="strContinueSave" type="hidden" value="false">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="lID" type="hidden" value="<%=lID%>">
		<input name="lInputUserID" type="hidden" value="<%=lInputUserID%>">
		<input name="lOfficeID" type="hidden" value="<%=lOfficeID%>">
		<input name="lCurrencyID" type="hidden" value="<%=lCurrencyID%>">
		<input name="lTransactionTypeID" type="hidden" value="<%=lTransactionTypeID%>">
		<input name="strModifyTime" type="hidden" value="<%=strModifyTime%>">
		<table width="774" border="0" align="center" cellspacing="0" cellpadding="0" class="txt_black">

		<tr>
		<td height="24"  background="/webob/graphics/infor_til_bgpic.jpg">
			<table width="760" border="0" align="center" cellpadding="0"
				cellspacing="0" class="txt_black">
				<tr>
					<td width="159" class="txt_til2">
						
					</td>
					<td width="615" align="right">
						<a href="login.htm" class="txt_black">首页</a> &gt; 帐户信息&gt; 账户历史明细
					</td>
				</tr>
			</table>
		</td>
		</tr>
		</table>
		<br/>
		<table width="774" border="0"  cellpadding="0" cellspacing="0">
  <tr>
    <td width="3" ><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif"  class="txt_til2"> 定期支取</td>
    <td width="554"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>
 </table>	
<table width=774 border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
  <TBODY>
  
  <TR>
    <TD height=20 vAlign=bottom width="100%">
      <TABLE  border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1" width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>

          <td height="20" width="16%">定期客户编号：</td>
          <td height="20" width="34%"> 
            <input type="text" name="ClientIDCtrl" disabled class="box" value="<%=strClientNo%>" size="7" maxlength="7" >
            </td>
			
          <TD height=20 width="15%">定期客户名称 ：</TD>
          <TD height=20 width="35%">
            <textarea class=box disabled name=ClientNameDisable rows=2 cols=30><%=strClientName%></textarea>			
             </TD></TR>
        <TR borderColor=#E8E8E8>
		
		<SCRIPT language=JavaScript>
			showDisableAccountCtrl("FixedAccountDisabled","<%=strAccountNo%>","定期账号"," width='18%' "," width='27%' ");
		</SCRIPT>
		
          <TD borderColor=#E8E8E8 height=20 width="15%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="35%">		  	
			  <input type="hidden" name="ClientID" value="<%=lClientID%>">
			  <input type="hidden" name="ExecuteDate" value="<%=strExecuteDate%>">
			  
          </TD>
        </TR>
		
        <TR borderColor=#E8E8E8>
          <TD height=20 width="16%">定期存款单据号： </TD>
          <TD height=20 width="34%">
              <INPUT class=box disabled name=FixedDepositDisabled value="<%=strDepositNo%>">
               </TD>
          <TD height=20 width="15%">定期支取起息日：</TD>
          <TD height=20 width="35%">
		  <INPUT class=box disabled name=ExecuteDateDisabled size=18 value="<%=strInterestStartDate%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="16%">起始日：</TD>
          <TD height=20 width="34%">
              <INPUT class=box disabled name=StartDateDisabled size=18 value="<%=strStartDate%>">
               </TD>
          <TD height=20 width="15%">到期日：</TD>
          <TD height=20 width="35%">
		  <INPUT class=box disabled name=OverDate size=18 value="<%=strEndDate%>"> </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD height=20 width="16%">定期存款期限:</TD>
          <TD height=20 width="34%">
			<%
		  SETTHTML.showFixedDepositMonthListCtrl(out,"lDepositTerm",lDepositTerm,false,"onchange=\"dateChange(frmV001)\" disabled onfocus=\"nextfield='strEndDate';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %>			  
		</TD>
          <TD height=29 width="15%">利率 ：</TD>
          <TD height=29 width="35%">
		  <input class="box" disabled name="dRate" value="<%=dRate%>">%
    </TD></TR></TBODY></TABLE></TD></TR>
  <TR>
    <TD height=20 vAlign=bottom width="100%">
      <TABLE border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1" width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>定期到期存款详细资料</U></TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>本金金额：</TD>
          <TD height=20 width="27%">￥
			   <INPUT class=box name=PrincipalDisabled disabled value="<%=DataFormat.formatDisabledAmount(dAmount,2)%>" >			   
		  </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		  </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"><input type=hidden name=PrincipalToCurrentAccountID value="2" >
<%
	if(lBankID<=0)
	{
%>			
              <INPUT checked onFocus="nextfield ='lCurrentAccountIDCtrlCtrl3';" name="rdoSource" type=radio value="1" disabled>
<%
	}
	else
	{
%>			  
			<INPUT onFocus="nextfield ='lCurrentAccountIDCtrlCtrl3';" name="rdoSource" type=radio value="1" disabled>
<%
	}
%>	            
            </TD>

<SCRIPT language=JavaScript>	
	showDisableAccountCtrl("PrincipalToCurrentAccountIDDiabled","<%=strCurrentAccountNo%>","本金转至活期账号"," width='15%' "," width='26%' ");
</SCRIPT>
	
	
          <TD height=20 width="15%">活期客户名称:</TD>
          <TD height=20 width="37%">
              <textarea class=box rows=2 cols=30 disabled name="PrincipalCurrentClientNameDiabled"><%=strCurrentAccountClientName%></textarea>			  
			  <input type=hidden name=PrincipalCurrentClientName value="<%=strCurrentAccountClientName%>" >			  
               </TD></TR>

		<TR borderColor=#E8E8E8>
		<TD borderColor=#E8E8E8 height=20 width="3%">
			<%
				if(lBankID>0)
				{
			%>	
						<INPUT checked onFocus="nextfield ='lBankIDCtrl';" name=rdoSource type=radio value="2">
			<%
				}
				else
				{
			%>		  
			              <INPUT  onFocus="nextfield ='lBankIDCtrl';" name=rdoSource type=radio value="2">
			<%
				}
			%>			  
               </TD>	
                <td height="20" width="18%">付款开户行名称:</td>
                <td height="20" width="27%"> 
                  <textarea  name="PrincipalPayBankDisabled" disabled class="box" rows=2 cols=30><%=strBankName%></textarea>                 
				  </td>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>

        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"></TD>


<td height="20" bordercolor="#E8E8E8" width="18%">收款方账号:</td>
                <td height="20" width="27%"> 
                  <input type="text" name="PrincipalRepaymentAccountDiabled" disabled size="18" class="box" value="<%=strExtAcctNo%>">                 
				  </td>
				  
          <TD height=20 width="15%">收款方客户名称:</TD>
          <TD height=20 width="37%">
              <textarea class=box  disabled  name=PrincipalRepaymentClientNameDisabled rows=2 cols=30><%=strExtClientName%></textarea>						
               </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入地(省)：</TD>
            <TD height=20 width="27%"> 
              <input class=box size=24  disabled  value="<%=strRemitInProvince%>" 
            name=PrincipalRemitInProvince>			
              </TD>
          <TD height=20 width="15%">汇入地(市)：</TD>
          <TD height=20 width="37%">
              <INPUT class=box  disabled  value="<%=strRemitInCity%>" name=PrincipalRemitInCity size=24>			
			
               </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>		  
                <td height="20" bordercolor="#E8E8E8" width="18%">汇入行名称:</td>
                <td height="20" width="27%"> 
              <textarea class=box  disabled name=PrincipalRemitInBankDisabled rows=2 cols=30><%=strRemitInBank%></textarea>
			  	   </td>			   
				<TD  height=20 width="15%">&nbsp;</TD>
				<TD  height=20 width="37%">&nbsp;</TD>
		  </TR>
        </TBODY></TABLE></TD></TR>
  <TR>
    <TD height=130 vAlign=bottom width="100%">
      <TABLE border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1" width="97%">
        <TBODY>
        <TR borderColor=#E8E8E8>
          <TD colSpan=5 height=30><U>利息详细资料</U></TD></TR>

	<TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>计提利息：</TD>
          <TD height=20 width="27%">￥
              <INPUT class=box value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dPreDrawInterest,2),2)%>" name=EndAccountInterestDisabled size=17 disabled>
               </TD>
          <TD height=20 width="15%">冲销计提利息：</TD>
          <TD height=20 width="37%">￥
	   		<INPUT class=box name=dStrikePreDrawInterestCtrl disabled value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dStrikePreDrawInterest,2),2)%>" > 		
		  </TD>
		  </TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>利息支出：</TD>
          <TD height=20 width="27%">￥
              <INPUT class=box  value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dPayableInterest,2),2)%>" name=InterestPayDisabled size=17 disabled>
               </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>活期利息：</TD>
          <TD height=20 width="27%">￥
              <INPUT class=box  value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dCurrentInterest,2),2)%>" name=CurrentInterestDisabled size=17 disabled>
               </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		  </TR>
		  <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>其它利息：</TD>
          <TD height=20 width="27%">￥
		  <INPUT class=box  value="<%=DataFormat.formatDisabledAmount(dOtherInterest,2)%>" name=dOtherInterest size=17 disabled>				  
          </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD>
		</TR>	  
<%
	//	}
%>			  
        
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 colSpan=2 height=20>利息合计：</TD>
          <TD height=20 width="27%">￥
              <INPUT class=box disabled  value="<%=DataFormat.formatDisabledAmount(DataFormat.formatDouble(dTotalInterest,2),2)%>" name=InterestTotalDisabled size=17>
               </TD>
          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">
<%
	if(lInterestBankID<=0)
	{
%>		  
              <INPUT checked name=rdoInterest type=radio value=1  disabled onFocus="nextfield ='lReceiveInterestAccountIDCtrlCtrl3';" >
<%
	}
	else
	{
%>	
			<INPUT name=rdoInterest type=radio value=1  disabled onFocus="nextfield ='lReceiveInterestAccountIDCtrlCtrl3';" >		  
<%
	}
%>				  
          </TD>
<SCRIPT language=JavaScript>	
	showDisableAccountCtrl("InterestToCurrentAccountIDDisabled","<%=strReceiveInterestAccountNo%>","利息转至活期账号"," width='15%' "," width='26%' ");
</SCRIPT>
          <TD height=20 width="15%">活期客户名称:</TD>
          <TD height=20 width="37%">
           <textarea class=box  name="InterestCurrentClientName" disabled rows=2 cols=30><%=strReceiveInterestAccountClientName%></textarea>

			
               </TD></TR>
		<TR borderColor=#E8E8E8>
         <TD borderColor=#E8E8E8 height=20 width="3%">
		<%
			if(lInterestBankID>0)
			{
		%>				  
		              <INPUT name=rdoInterest type=radio value=2  checked  onFocus="nextfield='lInterestBankIDCtrl';" >
		<%
			}
			else
			{
		%>
					  <INPUT name=rdoInterest type=radio value=2  onFocus="nextfield='lInterestBankIDCtrl';" >
		<%
			}
		%>			  
               </TD>	  
                <td height="20" width="18%">付款开户行名称:</td>
                <td height="20" width="27%"> 
                  <textarea name="InterestPayBankDisabled" disabled class="box" rows=2 cols=30><%=strInterestBankName%></textarea>
				  </td>

          <TD height=20 width="15%">&nbsp;</TD>
          <TD height=20 width="37%">&nbsp;</TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%"></TD>	  
<td height="20" bordercolor="#E8E8E8" width="18%">收款方账号:</td>
                <td height="20" width="27%">
                  <input type="text" name="InterestRepaymentAccountCtrl" disabled size="18" class="box" value="<%=strInterestExtAcctNo%>">				  
				  </td>

          <TD height=20 width="15%">收款方客户名称:</TD>
          <TD height=20 width="37%">
              <textarea class=box disabled name=InterestRepaymentClientNameDisabled rows=2 cols=30><%=strInterestExtClientName%></textarea>
               </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
          <TD borderColor=#E8E8E8 height=20 width="18%">汇入地(省)：</TD>
          <TD height=20 width="27%">
              <INPUT class=box disabled value="<%=strInterestRemitInProvince%>"  name=InterestRemitInProvinceDisabled size=24>
               </TD>
          <TD height=20 width="15%">汇入地(市)：</TD>
          <TD height=20 width="37%">
              <INPUT class=box disabled value="<%=strInterestRemitInCity%>"  name=InterestRemitInCityDisabled size=24>
               </TD></TR>
        <TR borderColor=#E8E8E8>
          <TD borderColor=#E8E8E8 height=20 width="3%">&nbsp;</TD>
                <td height="20" bordercolor="#E8E8E8" width="18%">汇入行名称:</td>
                <td height="20" width="27%"> 
              <textarea class=box  disabled  rows=2 cols=30 name=InterestRemitInBankDisabled ><%=strInterestRemitInBank%></textarea>

			   </td>
				<TD  height=20 width="15%">&nbsp;</TD>
				<TD  height=20 width="37%">&nbsp;</TD>
		 </TR>
        </TBODY></TABLE></TD></TR>
	<TR>
    <TD height=30 vAlign=bottom width="100%">
      <TABLE border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1" width="97%">
        <TBODY>	
		<TR borderColor=#E8E8E8>
          <TD height=30><U>本金/利息处理方法</U></TD>
		  <TD height="20" Align="center" width="15%">
		<%	
			if(lCapitalAndInterestDealWay!=2)	
			{
		%>														
				<INPUT onFocus="nextfield ='lAbstractIDCtrl';"  name="lCapitalAndInterestDealWay" type="radio" value="1" disabled checked>汇总处理
		<%
			}
			else
			{
		%>	
				<INPUT onFocus="nextfield ='lAbstractIDCtrl';" name="lCapitalAndInterestDealWay" type="radio" value="1" disabled>汇总处理
		<%
			}
		%>	
		</TD>
		<TD height="20" Align="center" width="15%">
		<%	
			if(lCapitalAndInterestDealWay==2)	
			{
		%>														
				<INPUT onFocus="nextfield ='lAbstractIDCtrl';" name="lCapitalAndInterestDealWay" type="radio" value="2" disabled checked>分笔处理
		<%
			}
			else
			{
		%>	
				<INPUT onFocus="nextfield ='lAbstractIDCtrl';" name="lCapitalAndInterestDealWay" type="radio" value="2" disabled>分笔处理
		<%
			}
		%>	
		</TD>
		<TD  height=20 width="15%">&nbsp;</TD>
		<TD  height=20 width="37%">&nbsp;</TD>		
		 </TR>  
		</TBODY></TABLE></TD></TR>		
  <TR>
    <TD height=20 vAlign=top width="100%">
      <TABLE align=center height=20 width="97%">
        <TBODY>        
        <TR>
		<TD height=30 width="10%">交易号：</TD>
          <TD height=30 width="25%">
		  <INPUT class=box  disabled  name=TransactionNoDisabled value="<%=strTransNo%>"> 
		</TD>
		<td align="left" valign="middle" height="20" width="10%">摘 要:
				</td>
                <td align="left" valign="middle" height="20" width="25%"> 
                 <input type="text" name="AbstractDisabled" maxlength="2000" size="40" disabled class="box" value="<%=strAbstract%>">               
				  </td>
				  
          <TD height=20 width="11%">&nbsp;</TD>
          <TD height=20 width="25%"></TD>
          <TD height=20 width="9%">&nbsp;</TD>
          <TD height=20 width="20%">&nbsp;</TD></TR>
<TR Align=center>
          <TD colSpan=6 height=2>

            <DIV align=right>
		<INPUT class="button1" name="print" type="button" value=" 打 印 " onClick="doPrint();">
		<input class="button1" name="Submit32" type="button" value=" 关 闭 " onClick="window.close();">		
            </DIV>
			<HR>
			</TD></TR></TBODY></TABLE></TD></TR>
			
<TR>
<TD height=20 vAlign=top width="100%">
	<TABLE><TBODY>
              <tr valign="middle"> 
                <td width="8%" height="25">复核备注: </td>
                <td width="19%" height="25" > 
                  <input type="text" name="strCheckAbstract" class="box" value="<%=strCheckAbstract%>">
                  </td>
                <td width="7%" height="25" valign="middle">录入人: </td>
                <td width="6%" height="25" valign="middle"><%=strInputUserName%></td>
                <td width="8%" height="25">录入日期:</td>
                <td width="11%" height="25"><%=inputDate%></td>
                <td width="7%" height="25">复核人:</td>
                <td width="7%" height="25"><%=strCheckUserName%></td>
                <td width="9%" height="25"> 复核日期 :</td>
                <td width="9%" height="25"><%=strExecuteDate%></td>
                <td width="5%" height="25">状态:</td>
                <td width="5%" height="25"> <%=strStatus%> 
                  </td>
              </tr>


</TBODY></TABLE>
		</TD>
		</TR>
		
</TBODY></TABLE>

</form>
<%
	if(strAction ==null || !strAction.equals("FixedQuery"))
	{
%>
<%
	if(lStatusID == SETTConstant.TransactionStatus.SAVE)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV015.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV015");
</script>
<%
	}
	else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">
firstFocus(document.frmV015.strCheckAbstract);
//setSubmitFunction("doCancelCheck()");
setFormName("frmV015");
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
				doPrint();	
		        document.location.href="<%=strContext%>/settlement/tran/fixed/view/v014.jsp";
		    }
			else
			{
				document.location.href="<%=strContext%>/settlement/tran/fixed/view/v014.jsp";
			}
		}		
		
		function doCheck()
		{
			if(isSubmited == true)
		   	{
    	    	alert("请求已提交，请等候！");
	    	   	return;
	    	}			
			if (confirm("是否复核?")) 
			{
				isSubmited = true;
		  		document.frmV015.strAction.value="<%=SETTConstant.Actions.CHECK%>";
				showSending();
		 	    document.frmV015.submit();
			}	
		}
		function doPrint()
		{			
			window.open("<%=strContext%>/accountinfo/a403-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a334-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a304-c.jsp");
		}
		function doCancelCheck()
		{
		    if(isSubmited == true)
		    {
		        alert("请求已提交，请等候！");
		        return;
		    }		
			if(!validateFields(frmV015)) return;
		    if (confirm("是否取消复核?")) 
			{
				isSubmited = true;
		  		document.frmV015.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
				showSending();
		 	    document.frmV015.submit();
			}	
		}
		function doLinkSearch()
		{
			document.location.href="<%=strContext%>/settlement/tran/fixed/control/c013.jsp?strAction=<%=SETTConstant.Actions.LINKSEARCH%>&lTransactionTypeID=<%=SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER%>&strSuccessPageURL=../view/v017.jsp&strFailPageURL=../view/v017.jsp";
		}	
		function doReturn()
		{
			document.location.href="<%=strContext%>/settlement/obinstruction/control/c001.jsp?strSuccessPageURL=/settlement/obinstruction/view/v002.jsp&strFailPageURL=/settlement/obinstruction/view/v002.jsp&_pageLoaderKey=<%=strPageLoaderKey%>";
		}	
	function allFields()
	{
		this.aa = new Array("strCheckAbstract","复核备注","string",1);
		
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