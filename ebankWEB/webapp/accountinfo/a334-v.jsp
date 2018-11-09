<%--
/**
 页面名称 ：a334-v.jsp
 页面功能 : 通知开立复核页面
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
    Log.print("\n*******进入页面--ebank/capital/accountinfo/a334-v.jsp******\n");
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
		String strAbstract = null;
		long lAccountID = -1;
		double dAmount = 0.0;
		long lBillBankID = -1;
		String strBillNo = null;
		long lBillTypeID = -1;
		long lCertificationBankID = -1;
		String strCertificationNo = null;
		long lCheckUserID = -1;
		String strCheckUserName = null;
		String strRemitInCity = null;
		String strConsignPassword = null;
		String strConsignVoucherNo = null;
		long lCurrencyID = -1;
		String strDepositNo = null;
		long lDepositTerm = -1;
		java.sql.Timestamp tsEndDate = null;
		java.sql.Timestamp tsExecuteDate = null;
		long lExtAcctID = -1;
		String strExtClientName = null;
		String strExtAcctNo = null;
		String strExtBankNo = null;
		long lID = -1;
		long lInputUserID = -1;
		String strInputUserName = null;
		String strInstructionNo = null;
		long lInterestPlanID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;
		java.sql.Timestamp tsInputDate = null;
		long lNoticeDay = -1;
		long lOfficeID = -1;
		String strRemitInProvince = null;
		double dRate = 0.0;
		long lSealBankID = -1;
		String strSealNo = null;
		java.sql.Timestamp tsStartDate = null;
		long lStatusID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = null;
		//long lAbstractID = -1;
		String strRemitInBank = null;
		//页面辅助变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strEndDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = null;
		String inputDate = "";
		String strStatus = null;

		//通知客户回显信息
		long lClientID = -1;
		String strClientNo = null;
		String strClientName = null;
		//通知账户
		String strAccountNo = null;
		//活期账户回显信息		
		String strCurrentAccountClientName = null;
		long lCurrentAccountID = -1;
		String strCurrentAccountNo = null;
		//开户行回显信息
		long lBankID = -1;
		String strBankName = null;

		//现金流转回显信息
		String strCashFlowDesc = null;
		long lCashFlowID = -1;
		
		//摘要回显信息
		String strCheckAbstract = null;
		long lAbstractID = -1;
		
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
		TransFixedOpenInfo info = null;
		info = (TransFixedOpenInfo)request.getAttribute("searchResults");

		if(info != null)
		{
			strAbstract = info.getAbstract();
			lAccountID = info.getAccountID();
			strAccountNo = info.getAccountNo();
			dAmount = info.getAmount();
			lBankID = info.getBankID();
			strBankName = info.getBankName();
			lBillBankID = info.getBillBankID();
			strBillNo = info.getBillNo();
			lBillTypeID = info.getBillTypeID();
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
			strConsignPassword = info.getConsignPassword();
			strConsignVoucherNo = info.getConsignVoucherNo();
			lCurrencyID = info.getCurrencyID();
			strCurrentAccountClientName = info.getCurrentAccountClientName();
			lCurrentAccountID = info.getCurrentAccountID();
			strCurrentAccountNo = info.getCurrentAccountNo();
			strDepositNo = info.getDepositNo();
			lDepositTerm = info.getDepositTerm();
			tsEndDate = info.getEndDate();			
			tsExecuteDate = info.getExecuteDate();
			lExtAcctID = info.getExtAcctID();
			strExtClientName = info.getExtClientName();
			strExtAcctNo = info.getExtAcctNo();
			strExtBankNo = info.getExtBankNo();
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
			strRemitInProvince = info.getRemitInProvince();
			dRate = info.getRate();
			lSealBankID = info.getSealBankID();
			strSealNo = info.getSealNo();
			tsStartDate = info.getStartDate();
			lStatusID = info.getStatusID();
			lTransactionTypeID = info.getTransactionTypeID();
			strTransNo = info.getTransNo();
			lAbstractID = info.getAbstractID();
			strRemitInBank = info.getRemitInBank();	
			
			//自定义
			strStartDate = DataFormat.getDateString(tsStartDate);
			strEndDate = DataFormat.getDateString(tsEndDate);
			strExecuteDate = DataFormat.getDateString(tsExecuteDate);
			strInterestStartDate = DataFormat.getDateString(tsInterestStartDate);
	}
	//格式化数据
		strAbstract = DataFormat.formatString(strAbstract);
		strAccountNo = DataFormat.formatString(strAccountNo);
		strBankName = DataFormat.formatString(strBankName);
		strBillNo = DataFormat.formatString(strBillNo);
		strCashFlowDesc = DataFormat.formatString(strCashFlowDesc);
		strCertificationNo = DataFormat.formatString(strCertificationNo);
		strCheckAbstract = DataFormat.formatString(strCheckAbstract);
		strCheckUserName = DataFormat.formatString(strCheckUserName);
		strRemitInCity = DataFormat.formatString(strRemitInCity);
		strClientName = DataFormat.formatString(strClientName);
		strClientNo = DataFormat.formatString(strClientNo);
		strConsignPassword = DataFormat.formatString(strConsignPassword);
		strConsignVoucherNo = DataFormat.formatString(strConsignVoucherNo);
		strCurrentAccountClientName = DataFormat.formatString(strCurrentAccountClientName);
		strCurrentAccountNo = DataFormat.formatString(strCurrentAccountNo);
		strDepositNo = DataFormat.formatString(strDepositNo);
		strExtClientName = DataFormat.formatString(strExtClientName);
		strExtAcctNo = DataFormat.formatString(strExtAcctNo);
		strExtBankNo = DataFormat.formatString(strExtBankNo);
		strInputUserName = DataFormat.formatString(strInputUserName);
		strInstructionNo = DataFormat.formatString(strInstructionNo);
		strRemitInProvince = DataFormat.formatString(strRemitInProvince);
		strSealNo = DataFormat.formatString(strSealNo);
		strTransNo = DataFormat.formatString(strTransNo);	
		strRemitInBank = DataFormat.formatString(strRemitInBank);		
		
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
		if(strBankName == null || strBankName.equals(""))
		{
			strBankName = NameRef.getBankNameByID(lBankID);
			strBankName = DataFormat.formatString(strBankName);			
		}

		if(strCashFlowDesc == null || strCashFlowDesc.equals(""))
		{
			strCashFlowDesc = NameRef.getCashFlowNameByID(lCashFlowID);
			strCashFlowDesc = DataFormat.formatString(strCashFlowDesc);
		}
		strInputUserName = DataFormat.formatString(NameRef.getUserNameByID(lInputUserID));
		strCheckUserName = DataFormat.formatString(NameRef.getUserNameByID(lCheckUserID));		
		inputDate = DataFormat.getDateString(tsInputDate);
		strStatus = DataFormat.formatString(SETTConstant.TransactionStatus.getName(lStatusID));	
			
    %>
	<form name="frmV004" action="" method="post">	<!--利用隐藏控件，来控制页面-->
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
    <td width="90" background="/webob/graphics/lab_conner2.gif"  class="txt_til2"> 通知存款开立</td>
    <td width="554"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>
 </table>	
		<table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
			<TBODY>
				
				<TR>
					<TD height="120" vAlign="top" width="100%">
						<TABLE align="center" border="0" height="27" width="100%">
							<TBODY>
								<TR>
									<TD height="147" vAlign="top" width="95%">
										<TABLE align="center" border="0" borderColor="#999999" height="40" width="97%" cellpadding="0" cellspacing="0" class=normal id="table1">
											<TBODY>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="16%">	通知存款客户编号：
													</TD>
													<TD height="20" width="29%">
														<INPUT class="box" disabled maxLength="6" name="strClientNo" size="10" value="<%=strClientNo%>">
													</TD>
													<TD height="20" width="17%">	通知存款客户名称 ：
													</TD>
													<TD height="20" width="38%">
														<INPUT class="box" disabled name="strClientName" size="30" value="<%=strClientName%>">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">	
												
										<script language="javascript">
										showDisableAccountCtrl("strAccountNo","<%=strAccountNo%>","通知存款账号","width=15%","width=30%");
										</script>
													<TD borderColor="#E8E8E8" height="20" width="16%">&nbsp;	
													</TD>
													<TD borderColor="#E8E8E8" height="20" width="39%">&nbsp;	
													</TD>
													
												</TR>
												<TR borderColor="#E8E8E8">
													 
              <TD height=20 width="15%">通知存款单据号： </TD>	
													<TD height="20" width="30%">
														<INPUT class="box" disabled name="strDepositNo" value="<%=strDepositNo%>">
													</TD>
													<TD height="20" width="16%">
													</TD>
													<TD height="20" width="39%">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="15%">	起始日期：
													</TD>
													<TD height="20" width="30%">
														<INPUT class="box" disabled name="strStartDate" size="18" value="<%=strStartDate%>">
													</TD>
													<TD height="20" width="16%">&nbsp;	
													</TD>
													<TD height="20" width="39%">
													</TD>
												</TR>
												<TR borderColor="#E8E8E8">
													<TD height="20" width="15%">	通知存款品种:
													</TD>
													<TD height="20" width="30%">
														<%
														//SETTConstant.NotifyDepositType.showList(out, "lNoticeDay",0,lNoticeDay,false,false,"disabled");
														SETTHTML.showFixedDepositMonthListCtrl(out,"lNoticeDay",SETTConstant.TransQueryType.NOTIFY,lNoticeDay,false,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			  											%>
													</TD>													
												</TR>
												</TBODY></TABLE></TD></TR>
        <TR>
          <TD height=20  width="95%">
            <TABLE align=center border=0 borderColor=#999999 height=40 
            width="97%" cellpadding="0" cellspacing="0" class=normal id="table1">
            
              <TBODY>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%"><U>存款来源详细资料</U> </TD>
                <TD colSpan=3 height=20 vAlign=top>&nbsp;</TD></TR>
              <TR borderColor=#E8E8E8>
                <TD height=20 Align=center width="15%">
				<%	
	if(lBankID <0 && (strExtBankNo==null || strExtBankNo.equals("")))	
	{
%>														
								<INPUT CHECKED disabled name=radiobutton type=radio value=radiobutton checked >活期存款
<%
	}
	else
	{
%>	
								<INPUT disabled name=radiobutton type=radio value=radiobutton checked >活期存款
<%
	}
%>		
				
				  </TD>
                <TD borderColor=#E8E8E8 colSpan=3 height=20 vAlign=top></TD></TR>
              <TR borderColor=#E8E8E8>
<script language="javascript">			  
  	showDisableAccountCtrl("strCurrentAccountNo","<%=strCurrentAccountNo%>","活期账号","width=16%","width=29%");
</script>
                <TD height=20 vAlign=middle width="17%">活期账户客户名称 
                  ：</TD>
                <TD height=20 vAlign=top width="38%"><INPUT 
                  class=box disabled name=strCurrentAccountClientName size=30 value="<%=strCurrentAccountClientName%>"> 
                  </TD></TR>
            
              <TR borderColor=#E8E8E8>
                 <TD height=20 Align=center width="15%">
<%	
	if(lBankID !=-1 || (strExtBankNo!=null && !strExtBankNo.equals("")))
	{	
%>					 
    			<INPUT disabled name=radiobutton type=radio value=radiobutton checked>银行收款
				 
<%
	}
	else
	{
%>				
				<INPUT disabled name=radiobutton type=radio value=radiobutton >银行收款
<%
	}
%>					
				
				  </TD>
                <TD colSpan=3 height=20 vAlign=top></TD></TR>
              <TR borderColor=#E8E8E8 vAlign=middle>
                <TD height=20 width="15%">开户行：</TD>
                <TD height=20 width="30%"><INPUT class=box disabled 
                  name=strBankName size=30 value="<%=strBankName%>"> </TD>
				  </TR>
              </TBODY></TABLE></TD></TR>
        <TR>
          <TD height=2 width="95%">
            <TABLE align=center height=2 width="97%">
              <TBODY>
              <TR>
                <TD align=left height=20 vAlign=middle  width="10%">金 额：</TD>
                <TD align=left height=20 vAlign=middle width="20%">				
				￥ <INPUT class=tar disabled name=dAmount size=17 value="<%=DataFormat.formatDisabledAmount(dAmount)%>"> </TD>
                <TD align=left height=20 vAlign=middle width="10%">起息日：</TD>
                <TD align=left height=20 vAlign=middle width="25%"><INPUT class=box disabled name=strInterestStartDate 
                  value="<%=strInterestStartDate%>"> </TD>
                <TD align=left height=20 vAlign=middle width="10%">执行日： </TD>
                <TD align=left height=20 vAlign=middle width="25%"><INPUT class=box disabled name=strExecuteDate 
                  value="<%=strExecuteDate%>"> </TD></TR>
              <TR>
                <TD align=left height=20 vAlign=middle width="10%">交易号： </TD>
                <TD align=left height=20 vAlign=middle width="20%"><INPUT class=box disabled name=strTransNo 
                  value="<%=strTransNo%>"> </TD>
                <TD align=left height=20 vAlign=middle width="10%">摘 要：</TD>
                <TD align=left height=20 vAlign=middle width="25%"><INPUT class=box disabled maxLength=2000 
                  name=strAbstract size=40 value="<%=strAbstract%>"> </TD>
                <TD align=left height=20 vAlign=middle width="10%">&nbsp;</TD>
                <TD align=left height=20 vAlign=middle 
width="25%">&nbsp;</TD></TR>
             
              <TR>
                <TD align=left colSpan=6 height=20 vAlign=top>
                  <DIV align=right></DIV>
                  <DIV align=right>
		<INPUT class="button1" name="print" type="button" value=" 打 印 " onClick="doPrint();">	
		<input class="button1" name="Submit32" type="button" value=" 关 闭 " onClick="window.close();">		
                  </DIV></TD></TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD height=2 width="95%">
            <HR class=normal>
            <TABLE align=center border=0 height=22 width="97%">
              <TBODY>
              <TR vAlign=middle>
                <TD height=25 width="9%">复核备注:</TD>
                <TD height=25 vAlign=top width="19%"><INPUT 
                  class=box name="strCheckAbstract" value="<%=strCheckAbstract%>" maxlength=50 onfocus="nextfield ='submitfunction';"> </TD>
                <TD height=25 width="7%">录入人:</TD>
                <TD height=25 width="6%"><%=strInputUserName%></TD>
                <TD height=25 width="9%">录入日期:</TD>
                <TD height=25 width="9%"><%=inputDate%></TD>
                <TD height=25 width="7%">复核人:</TD>
                <TD height=25 width="7%"><%=strCheckUserName%></TD>
                <TD height=25 width="9%">复核日期:</TD>
                <TD height=25 width="9%"><%=strExecuteDate%></TD>
                <TD height=25 width="5%">状态:</TD>
                <TD height=25 width="6%"><%=strStatus%></TD>
			  </TR></TBODY>
			</TABLE>
		  </TD>
	    </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
				
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
firstFocus(document.frmV004.strCheckAbstract);
//setSubmitFunction("doCheck()");
setFormName("frmV004");
</script>
<%
	}
	else if(lStatusID == SETTConstant.TransactionStatus.CHECK)
	{
%>
<script language="JavaScript">firstFocus(document.frmV004.strCheckAbstract);
//setSubmitFunction("doCancelCheck()");
setFormName("frmV004");
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
				window.open("<%=strContext%>/settlement/print/view/v002.jsp?lID=<%=lID%>&lTransTypeID=<%=lTransactionTypeID%>&strSuccessPageURL=/settlement/tran/fixed/view/v034.jsp&strFailPageURL=/settlement/tran/fixed/view/v034.jsp");				
		        document.location.href="<%=strContext%>/settlement/tran/fixed/view/v033.jsp";
		    }
			else
			{
				document.location.href="<%=strContext%>/settlement/tran/fixed/view/v033.jsp";
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
		  		document.frmV004.strAction.value="<%=SETTConstant.Actions.CHECK%>";
				showSending();
		 	    document.frmV004.submit();
			}	
		}
		function doPrint()
		{			
			window.open("<%=strContext%>/accountinfo/a402-v.jsp?lID=<%=lID%>&lTransactionTypeID=<%=lTransactionTypeID%>&lReturn=<%=lReturn%>&lTransTypeID=<%=lTransactionTypeID%>&strSuccessPageURL=/iTreasury-ebank/accountinfo/a334-v.jsp&strFailPageURL=/iTreasury-ebank/accountinfo/a304-c.jsp");
		}
		function doCancelCheck()
		{
		    if(isSubmited == true)
		    {
		        alert("请求已提交，请等候！");
		        return;
		    }		
			if(!validateFields(frmV004)) return;
		    if (confirm("是否取消复核?")) 
			{
				isSubmited = true;
		  		document.frmV004.strAction.value="<%=SETTConstant.Actions.CANCELCHECK%>";
				showSending();
		 	    document.frmV004.submit();
			}	
		}
		function doLinkSearch()
		{
			document.location.href="<%=strContext%>/settlement/tran/fixed/control/c003.jsp?strAction=<%=SETTConstant.Actions.LINKSEARCH%>&lTransactionTypeID=<%=SETTConstant.TransactionType.OPENNOTIFYACCOUNT%>&strSuccessPageURL=../view/v036.jsp&strFailPageURL=../view/v036.jsp";
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