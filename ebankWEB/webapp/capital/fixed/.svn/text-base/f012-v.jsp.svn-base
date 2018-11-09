<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/*
 * 程序名称：f012-v.jsp
 * 功能说明：定期支取修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				    com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				    java.text.SimpleDateFormat"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期支取]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;
	PayerOrPayeeInfo payerInfo = null;
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long lAbstractID = -1;
	long isautocontinue = -1;
	long rdoInterest = -1;	
	String strTemp = null;
	Timestamp opendate = null;
	String strOpenDate = "";

	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }

		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		payerInfo = (PayerOrPayeeInfo)request.getAttribute("payerInfo");
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		
		
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER);
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		
		
		/* 从信息类中获取格式化的当前金额和可用金额 */
        sPayerCurrentBalance = financeInfo.getFormatCurrentBalance();
        if (sPayerCurrentBalance==null || sPayerCurrentBalance.trim().length()==0) 
		{	
			sPayerCurrentBalance="0.00";
		}
        sPayerUsableBalance = financeInfo.getFormatUsableBalance();
        if (sPayerUsableBalance==null || sPayerUsableBalance.trim().length()==0) 
		{	
			sPayerUsableBalance="0.00";
		}
		strTemp = (String)request.getAttribute("rdoInterest");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			rdoInterest = Long.valueOf(strTemp).longValue();
		}		
		
		if(financeInfo.getOfficeID()>0&&financeInfo.getCurrencyID()>0)
		{
			opendate = Env.getSystemDate(financeInfo.getOfficeID(),financeInfo.getCurrencyID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strOpenDate = sdf.format(opendate);
		}
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        
	String msg = "";
	if(request.getAttribute("submitt")!=null && !request.getAttribute("submitt").equals("")){
	msg = (String)request.getAttribute("submitt");
%>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>
<script language="javascript">
	if("<%=msg%>" != "")
	 {
	 	alert("<%=msg%>");
	 }
</script>
<%}%>
<%       

        if(request.getAttribute("delete")!=null && request.getAttribute("delete").equals("delete")){
%><script language="javascript">
	alert("删除成功");
</script>
<%       
        }
%>
<safety:security/>
<form method="post" name="frm">
<safety:certInformation/>
  <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
  <input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">
  <input type="hidden" name="hiddenOpendate" value="<%=strOpenDate %>">
  <input type="hidden" name='sFixedDepositNo' value="<%=financeInfo.getSubAccountID()  %>" />
  <table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">定期支取</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>	
   
<br/>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">

      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
          <tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2"> 定期账户资料</td>
			<td width="17"><a class=lab_title3></td>
		</tr>
        </table></td>
      </tr>
    </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="17%" height="25" class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账户名称：</td>
      <td width="430" height="25" class="MsoNormal"> 
        <input type="text" class="box" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? payerInfo.getAccountName() : financeInfo.getPayerName() %>" readonly>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="*%" height="25" colspan="3" class="MsoNormal">&nbsp;</td>
    </tr>
    <tr class="MsoNormal"> 
      <td height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账号：</td>
      <td height="25" class="MsoNormal"> 
        <%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.FIXED,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
        <input type="hidden"  name="sPayerAccountNo"  value="">
        <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>" >
      </td>
      <td height="25" class="MsoNormal"></td>
      <td height="25" colspan="3" class="MsoNormal">&nbsp;</td>
    </tr>
    <!--a try for glass start-->
    <tr class="MsoNormal"> 
      <td height="25" class="MsoNormal"></td>
      
      <%
		//定期存款单据号
		boolean isModify = false;
		if(financeInfo.getSubAccountID()!=-1) isModify = true;
		long lTypeID = 21;
		long lDepositTypeIDFixedDepositNo = 1;
		long lAccountID = (financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID();
		String strAccountIDCtrlFixedDepositNo = "nPayerAccountID";		
		String[] strNextControlsFixedDepositNo = {"nRemitType"};
		String strRtnEndDateCtrlFixedDepositNo= "";
		String strRtnOpenDateCtrlFixedDepositNo = "";
		String strRtnCapitalCtrlFixedDepositNo = "dDepositAmount";
		String strRtnBalanceCtrlFixedDepositNo = "mbalance";
		String strRtnRateCtrl = "dDepositRate";
		String strRtnIntervalCtrl = "nDisFixedDepositTime";
		String strRtnStartDateCtrl = "tsFixedDepositStart";
		String strAmount = "amount";
		String strFirstTD = " width=\"130\" height=\"25\" class=\"MsoNormal\" ";
		String strSecondTD = " width=\"430\" height=\"25\" ";
		String sysdate= Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
		if(!isModify){%>

			<td height="25" align="left"><font color='#FF0000'>* </font>定期存单：</td>
			<td >
				<%	if(isCreateNewBook){%>
				<fs:dic id="depositNo" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL_CREATE"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.nPayerAccountID.value,frm.lUserID.value,frm.depositNo.value,frm.lTypeID.value,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>" nextFocus="nRemitType" width="600">
					<fs:columns> 
						<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
						<fs:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="depositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="mbalance" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.AMOUNT_6 %>" elType="text" />
						<fs:pageElement elName="nDisFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="tsFixedDepositStart" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="amount" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					</fs:pageElements>							
				</fs:dic> 
				<% }else{%>
					<fs:dic id="depositNo" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.nPayerAccountID.value,frm.lUserID.value,frm.depositNo.value,frm.lTypeID.value,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>" nextFocus="nRemitType" width="600">
					<fs:columns> 
						<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
						<fs:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="depositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="mbalance" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="nDisFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="tsFixedDepositStart" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="amount" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					</fs:pageElements>							
				</fs:dic> 
				<%} %>
			</td>
			<%if (strAmount != null && strAmount != "")
				{%>
				<script>
					var amount="<%=strAmount%>";
					var lCurrencyID = "<%=sessionMng.m_lCurrencyID%>";
					$('sPayerAccountNoZoomCtrl').blur(function(){
						adjustAmount('frm',amount,'1',sChineseAmount,lCurrencyID);
					});
				</script>
				<%}}
	else{
			%>
			<td height="25" align="left"><font color='#FF0000'>* </font>定期存单：
					<input name="lId" type="hidden" value="<%=financeInfo.getID() %>"></td>
					<input name="subAccountId" type="hidden" value="<%=financeInfo.getSubAccountID() %>"></td>
			<td >
				<%	if(isCreateNewBook){%>
				<fs:dic id="depositNo" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL_CREATE_MODIFY"  sqlParams='frm.subAccountId.value,frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.nPayerAccountID.value,frm.lUserID.value,frm.depositNo.value,frm.lTypeID.value,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>" nextFocus="nRemitType" width="600">
					<fs:columns> 
						<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
						<fs:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="depositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="mbalance" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="nDisFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="tsFixedDepositStart" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="amount" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					</fs:pageElements>							
				</fs:dic> 
				<% }else{%>
					<fs:dic id="depositNo" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL_MODIFY"  sqlParams='frm.lId.value,frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.nPayerAccountID.value,frm.lUserID.value,frm.depositNo.value,frm.lTypeID.value,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>" nextFocus="nRemitType" width="600">
					<fs:columns> 
						<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
						<fs:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="depositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="ItIsNotControl" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="mbalance" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="nDisFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="tsFixedDepositStart" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="amount" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					</fs:pageElements>							
				</fs:dic> 
				<%} %>
				<%if (strAmount != null && strAmount != "")
				{%>
				<script>
					var amount="<%=strAmount%>";
					var lCurrencyID = "<%=sessionMng.m_lCurrencyID%>";
					$('depositNo').blur(function(){
						adjustAmount('frm',amount,'1',sChineseAmount,lCurrencyID);
					});
				</script>
				<%}%>
			</td>
			<%
		}
%>
      <td height="25" class="MsoNormal"></td>
      <td >
      	<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
		<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">
		<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>">
		<input type="hidden" name="lTypeID" value="<%=lTypeID %>">
		<input type="hidden" name="sysdate" value="<%=sysdate %>">
		<input type="hidden" id="lInstructionID" name="lInstructionID" value="<%=financeInfo.getID() %>">
		<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
		<input type="hidden" name="strAccountIDCtrlFixedDepositNo" value="<%=strAccountIDCtrlFixedDepositNo %>">
		<input type="hidden" name="PayeeBankAccountNOInternal"/></td>
		<td width="*%" height="25" colspan="2" class="MsoNormal">&nbsp;</td>
    </tr>
    <tr class="MsoNormal"> 
      <td height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>存单余额：</td>
      <td height="25" class="MsoNormal"> 
      		<fs:amount 
	       		form="frm"
       			name="mbalance"
       			value="<%=financeInfo.getDepositBalance()%>"
       			readonly="true"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
      <td height="25" class="MsoNormal"></td>
      <td height="25" colspan="3" class="MsoNormal">&nbsp;</td>
    </tr>
    <tr class="MsoNormal"> 
      <td height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal" nowrap><font color="red">&nbsp;&nbsp;</font>定期存款起始日：</td>
      <td height="25" class="MsoNormal"> 
        <input type="text" class="box" name="tsFixedDepositStart" size="20" value="<%= (financeInfo.getID()==-1 && financeInfo.getDepositStart()==null)? "":financeInfo.getDepositStart().toString().substring(0,10) %>" readonly>
       &nbsp;  </td>
     </tr>
    <tr class="MsoNormal">
       <td height="25" class="MsoNormal"></td>
       <td height="25" class="MsoNormal" nowrap><font color="red">&nbsp;&nbsp;</font>期限：</td>
       <td>
		<%SETTHTML.showFixedDepositMonthListCtrl(out,"nDisFixedDepositTime",SETTConstant.TransQueryType.FIXED,financeInfo.getFixedDepositTime(),false," disabled=\"disabled\" onfocus=\"nextfield='dDepositAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
        <input type="hidden" name="nFixedDepositTime" value="<%= financeInfo.getFixedDepositTime() %>">
      </td>
      <td colspan="4" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td height="25"></td>
      <td height="25" class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>存款本金：</td>
      <td height="25" class="MsoNormal"> 
      	<fs:amount 
       			form="frm"
    			name="dDepositAmount"
    			value="<%=financeInfo.getDepositAmount()%>"
    			readonly="true"
    			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
       </td>
     </tr>
     <tr>
     	<td height="25"></td>
     	<td><font color="red">&nbsp;&nbsp;</font>利率：</td>
     	<td>
        <input type="text" class="box" name="dDepositRate" size="20" value="<%= DataFormat.formatRate(financeInfo.getDepositRate()) %>" style="text-align:right" readonly>
        % </td>
      <td colspan="4" class="MsoNormal"></td>
    </tr>
  
  </table>
  <br>
      	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
 <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 定期支取方式</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	 
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr class="MsoNormal">
          <td width="49%" class="MsoNormal">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal >
				<tr class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr class="MsoNormal">
          			<td width="5" height="25" class="MsoNormal"></td>
          			<td width="34%" height="25"><p><span class="MsoNormal">本金：</span></p></td>
          			<td width="*%" height="25" class="MsoNormal"></td>  			
        		</tr>
				<tr class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal"><p><span class="MsoNormal" nowrap><font color="red">&nbsp;&nbsp;</font>汇款方式：</span></p></td>
          			<td height="25" class="MsoNormal">
					<input type="hidden" name="nRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getRemitType() %>">
<%
				//	OBHtmlCom.showRemitTypeListControl1(out,"nRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" ":"readonly");
					OBHtmlCom.showRemitTypeListControl1(out,"nRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType(),"onchange=\"jump();\" ");
%>
					<input type="hidden" name="nRemitTypeHid" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType() %>">
					</td>  			
        		</tr>
				<tr id="payeeAcctLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeeAcct" class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          		
<%
					//收款方账号放大镜（内部转账）
					String[] nextfocus = {"nRemitType"};
					String val="";
					if( financeInfo.getRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)
					{
						val = NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo());
					//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID1","","sPayeeName","frm",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号"," width=\"120\" height=\"25\" class=\"MsoNormal\""," width=\"240\" height=\"25\" ",nextfocus);	
					//OBMagnifier.createPayerInterAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID1","","sPayeeName","frm",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号","  width=\"120\" height=\"25\"  class=\"MsoNormal\""," width=\"240\" height=\"25\" ",nextfocus);	
					}
%>	              
            		<td height="25" align="left" nowrap><font color='#FF0000'>* </font>收款方账号：</td>
					<td>
						<fs:dic id="sPayeeAccountInternalCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=val%>" nextFocus="nRemitType" width="400">
							<fs:columns> 
								<fs:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sPayeeAccountInternalCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nPayeeAccountID1" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankAccountNOInternal" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>		
        		</tr>
				<tr id="payeeAcctNameLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeeAcctName" class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal" nowrap><p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>收款方名称：</span></p></td>
          			<td height="25" class="MsoNormal">
          			<input type="hidden" name="nPayeeAccountID1" value="<%=  financeInfo.getPayeeAcctID() %>" >
						<input type="text" class="box" name="sPayeeName" value="<%= (financeInfo.getRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)?financeInfo.getPayeeName():"" %>" size="24" readonly>
					</td>  			
        		</tr>
				<tr id="payeeBankNoLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeeBankNo" class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          		<td height="25" align="left" nowrap><font color='#FF0000'>* </font>收款方账号：</td>
				<td >
				<%
					//收款方账号放大镜（汇）
					String sPayeeAcctNoZoomCtrl_value="";
					if( financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
						 sPayeeAcctNoZoomCtrl_value= financeInfo.getPayeeAcctNo();
				%>
				<fs:dic id="sPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sPayeeAcctNoZoomCtrl.value,frm.sPayeeNameZoomAcctCtrl.value' value="<%=sPayeeAcctNoZoomCtrl_value%>" nextFocus="amount" width="400">
					<fs:columns> 
						<fs:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="sPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="nPayeeAccountID2" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sPayeeNameZoomAcctCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="sPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					</fs:pageElements>							
				</fs:dic> 
				</td>	
        		</tr>
				<tr id="payeeBankNoNameLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeeBankNoName" class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal" nowrap>
              <p><span class="MsoNormal">&nbsp;&nbsp;收款方名称：</span></p>
            </td>
          			<td height="25" class="MsoNormal">
					<input size="32" type="text" class="box" name="sPayeeNameZoomAcctCtrl" value="<%= (financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeName():"" %>" onfocus="nextfield ='sPayeeProv';" maxlength="50" readonly>
					</td>  			
        		</tr>
				<tr id="payeePlaceLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeePlace"class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal" nowrap><p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>汇入地：</span></p></td>
          			<td height="25" nowrap class="MsoNormal">
						<input type="text" name="sPayeeProv" value="<%=  (financeInfo.getPayeeProv()!=null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeProv():"" %>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15" readonly class="box">
            			省
						<input type="text" name="sPayeeCity" value="<%= ( financeInfo.getPayeeCity() != null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeCity():"" %>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15" readonly class="box">
              市（县）</td>  			
        		</tr>
				<tr id="payeeBankNameLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="payeeBankName" class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal"><p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>汇入行名称：</span></p></td>
          			<td height="25" class="MsoNormal">
						<input type="text" name="sPayeeBankName" value="<%=  (financeInfo.getPayeeBankName()!=null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeBankName():"" %>" size="32" onfocus="nextfield ='nInterestRemitType';" maxlength="50" readonly class="box">
						<input type="hidden" name="nPayeeAccountID2" value="<%=  financeInfo.getPayeeAcctID() %>" >
					</td>  			
        		</tr>
				<tr id="line1" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="blank1"  class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal">
              <p>&nbsp;</p>
            </td>
          			<td height="25" class="MsoNormal"></td>  			
        		</tr>
				<tr id="line2" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="blank2"class="MsoNormal">
          			<td height="25" class="MsoNormal"></td>
          			<td height="25" class="MsoNormal">
              <p>&nbsp;</p>
            </td>
          			<td height="25" class="MsoNormal"></td>  			
        		</tr>
			</table>  
		  </td>
		  <td width="1" class="MsoNormal">
		  </td>
		  
		  <td width="49%" class="MsoNormal" >
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
				<tr class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal"><p><span class="MsoNormal">利息：</span></p></td>
          			<td width="240" height="25" class="MsoNormal"></td>  			
        		</tr>
				<tr class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal" nowrap><p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>汇款方式：</span></p></td>
          			<td width="240" height="25" class="MsoNormal">
					<input type="hidden" name="nInterestRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getInterestRemitType() %>">
<%
			//		OBHtmlCom.showRemitTypeListControl1(out,"nInterestRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getInterestRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" ":"readonly");
					OBHtmlCom.showRemitTypeListControl1(out,"nInterestRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType(),"onchange=\"jump();\" ");

%>
					<input type="hidden" name="nInterestRemitTypeHid" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType() %>">
					</td>  			
        		</tr>
				<tr id="InterestPayeeAcctLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="InterestPayeeAcct" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
<%
					String sInterestPayeeAccountInternal_value="";
					//利息收款方账号放大镜（内部转账）
					if( financeInfo.getInterestRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)
					{
						sInterestPayeeAccountInternal_value = financeInfo.getInterestPayeeAcctNo();
					//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nInterestPayeeAccountID1","","sInterestPayeeName","frm",financeInfo.getInterestPayeeAcctNo(),"sInterestPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号"," width=\"120\" height=\"25\" class=\"MsoNormal\""," width=\"240\" height=\"25\" ",nextfocus);	
			/*		OBMagnifier.createPayerInterAccountNoCtrl(out,
							sessionMng.m_lUserID,
							sessionMng.m_lCurrencyID,
							financeInfo.getID(),
							sessionMng.m_lClientID,
							"nInterestPayeeAccountID1",
							"",
							"sInterestPayeeName",
							"frm",
							financeInfo.getInterestPayeeAcctNo(),
							"sInterestPayeeAccountInternal",
							"<font color='#FF0000'>* </font>收款方账号",
							" nowrap width=\"120\" height=\"25\"  class=\"MsoNormal\"",
							" width=\"240\" height=\"25\" ",
							nextfocus);	*/
					}
					else
					{
					//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nInterestPayeeAccountID1","","sInterestPayeeName","frm","","sInterestPayeeAccountInternal","<font color='#FF0000'>* </font>收款方账号"," width=\"120\" height=\"25\" class=\"MsoNormal\""," width=\"240\" height=\"25\" ",nextfocus);	
					/*	OBMagnifier.createPayerInterAccountNoCtrl(out,
								sessionMng.m_lUserID,
								sessionMng.m_lCurrencyID,
								financeInfo.getID(),
								sessionMng.m_lClientID,
								"nInterestPayeeAccountID1",
								"",
								"sInterestPayeeName",
								"frm",
								"",
								"sInterestPayeeAccountInternal",
								"<font color='#FF0000'>* </font>收款方账号",
								" nowrap width=\"120\" height=\"25\"  class=\"MsoNormal\"",
								" width=\"240\" height=\"25\" ",
								nextfocus);	*/
					}
	%>	  			
					<td width="130" height="25" align="left" nowrap><font color='#FF0000'>* </font>收款方账号：</td>
					<td >
						<fs:dic id="sInterestPayeeAccountInternalCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sInterestPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=sInterestPayeeAccountInternal_value%>" nextFocus="nRemitType" width="400">
							<fs:columns> 
								<fs:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sInterestPayeeAccountInternalCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nInterestPayeeAccountID1" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankAccountNOInternal" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
        		</tr>
				<tr id="InterestPayeeAcctNameLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="InterestPayeeAcctName" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal" nowrap><p><span class="MsoNormal">&nbsp;&nbsp;收款方名称：</span></p></td>
          			<td width="240" height="25" class="MsoNormal">
          			<input type="hidden" name="nInterestPayeeAccountID1" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >	
						<input type="text" class="box" name="sInterestPayeeName" value="<%= (financeInfo.getInterestRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)?financeInfo.getInterestPayeeName():"" %>" size="24" readonly>
					</td>  			
        		</tr>
				<tr id="InterestPayeeBankNoLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeBankNo" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          		
<%
				String sInterestPayeeAcctNoZoomCtrl_value="";
				//收款方账号放大镜（汇）
				if( financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)
				{
					sInterestPayeeAcctNoZoomCtrl_value = financeInfo.getInterestPayeeAcctNo();
			/*	OBMagnifier.createPayeeAccountNOCtrl(
						out,
						sessionMng.m_lCurrencyID,
						sessionMng.m_lClientID,
						"nInterestPayeeAccountID2",
						"sInterestPayeeNameZoomAcctCtrl",
						"sInterestPayeeProv",
						"sInterestPayeeCity",
						"sInterestPayeeBankName",
						"frm",
						financeInfo.getInterestPayeeAcctNo(),
						"sInterestPayeeAcctNoZoom",
						"<font color='#FF0000'>* </font>收款方账号",
						"  nowrap width=\"120\" height=\"25\" class=\"MsoNormal\"",
						" width=\"240\" height=\"25\" ");	*/
				}
				else
				{
			//	OBMagnifier.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"nInterestPayeeAccountID2","sInterestPayeeNameZoomAcctCtrl","sInterestPayeeProv","sInterestPayeeCity","sInterestPayeeBankName","frm","","sInterestPayeeAcctNoZoom","<font color='#FF0000'>* </font>收款方账号"," nowrap width=\"120\" height=\"25\" class=\"MsoNormal\""," width=\"240\" height=\"25\" ");	
				}

%>			
					<td width="130" height="25" align="left" nowrap><font color='#FF0000'>* </font>收款方账号：</td>
					<td >
						<fs:dic id="sInterestPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sInterestPayeeAcctNoZoomCtrl.value,frm.sInterestPayeeNameZoomAcctCtrl.value' value="<%=sInterestPayeeAcctNoZoomCtrl_value%>" nextFocus="amount" width="400">
							<fs:columns> 
								<fs:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sInterestPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nInterestPayeeAccountID2" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeNameZoomAcctCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
        		</tr>
				<tr id="InterestPayeeBankNoNameLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="InterestPayeeBankNoName" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal" nowrap> 
              <p><span class="MsoNormal">&nbsp;&nbsp;收款方名称：</span></p>
            </td>
          			<td width="240" height="25" class="MsoNormal">
					<input size="32" type="text" class="box" name="sInterestPayeeNameZoomAcctCtrl" value="<%= (financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeName():"" %>" onfocus="nextfield ='sInterestPayeeProv';" maxlength="50" readonly>
					</td>  			
        		</tr>
				<tr id="InterestPayeePlaceLine" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="InterestPayeePlace" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25"class="MsoNormal"><p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>汇入地：</span></p></td>
          			<td width="240" height="25" nowrap class="MsoNormal">
						<input type="text" class="box" name="sInterestPayeeProv" value="<%=  (financeInfo.getInterestPayeeProv()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeProv():"" %>" size="10" onfocus="nextfield ='sInterestPayeeCity';" maxlength="15" readonly>
            			省
						<input type="text" class="box" name="sInterestPayeeCity" value="<%=  (financeInfo.getInterestPayeeCity()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeCity():"" %>" size="10" onfocus="nextfield ='sInterestPayeeBankName';" maxlength="15" readonly>
              市（县）</td>  			
        		</tr>
				<tr id="InterestPayeeBankNameLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeBankName" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal">
              <p><span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>汇入行名称：</span></p>
            </td>
          			<td width="240" height="25" class="MsoNormal">
						<input type="text" class="box" name="sInterestPayeeBankName" value="<%=  (financeInfo.getInterestPayeeBankName()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeBankName():"" %>" size="32" onfocus="nextfield ='amount';" maxlength="50" readonly>
						<input type="hidden" name="nInterestPayeeAccountID2" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >	
					</td>  			
        		</tr>
				<tr id="InterestLine1" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestBlank1" class="MsoNormal">
          			<td width="4" height="25" class="MsoNormal"></td>
          			<td width="120" height="25" class="MsoNormal">
              <p>&nbsp;</p>
            </td>
          			<td width="240" height="25" class="MsoNormal"></td>  			
        		</tr>
				<tr id="InterestLine2" class="MsoNormal">
          			<td colspan="3" height="1" class="MsoNormal"></td>
        		</tr>
        		<tr id="InterestBlank2" class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25">
              <p>&nbsp;</p>
            </td>
          			<td width="240" height="25" class="MsoNormal"></td>  			
        		</tr>
			</table>  
		  </td>
        </tr>
      </table>
	   <br>
      	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td>
        <table width="110" border="0" cellspacing="0" cellpadding="0">
           <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 划款资料</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        </table>
        </td>
      </tr>
    </table>
     
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="17%" height="25" class="MsoNormal"><font color="red">*&nbsp;</font>金额：&nbsp;&nbsp;</td>
          <td height="25" width="576" class="MsoNormal">
		  
            <input  type="hidden" name="mamOuntFortans" value="<%=financeInfo.getMamOuntForTrans()%>" >
		    	<fs:amount 
	       		form="frm"
       			name="amount"
       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount())) %>"
       			chineseCtrlName="sChineseAmount"
       			nextFocus="tsExecute"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="*%" height="25" colspan="1" class="MsoNormal">&nbsp;</td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal" nowrap><font color="red">&nbsp;&nbsp;</font>大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25"class="MsoNormal">
			<input type="text" class="box" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" onpropertychange="checkdAmount();" readonly>		
		  </td>		  
          <td width="5" height="25"></td>
          <td width="*%" height="25" colspan="1" class="MsoNormal">&nbsp;</td>
        </tr>  
        
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal" ><font color="red">*&nbsp;</font>执行日：</td>
          <td height="25" class="MsoNormal">
          <fs:calendar 
          	    name="tsExecute"
	          	value="<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate() %>" 
	          	properties="nextfield ='sNoteCtrl'" 
	          	size="20"/>
							          	<!-- 
			<input type="text" name="tsExecute" value="<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate() %>" onfocus="nextfield ='sNoteCtrl';" size="20" class="box" onblur="dateformat();">
			<a class=calendar href="javascript:show_calendar('frm.tsExecute');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				
			</a>
			 -->
		  </td>

          <td width="5" height="25"></td>
          <td width="*%" height="25" colspan="1" class="MsoNormal">&nbsp;</td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        
        <tr>
        	<td width="4" height="25">&nbsp;</td>
        	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇款用途：</td>	
        	<td width="500" height="25">
        	<%
        	String _getNote = financeInfo.getNote();
        	if(financeInfo.getNote() == null){
        		_getNote = "";	        		
        	}
        	%>
				<fs:dic id="sNoteCtrl" form="frm" title="汇款用途" sqlFunction="getAbstractSettingSQL" sqlParams="frm.lOfficeID.value,frm.lUserID.value,frm.sNoteCtrl.value" value="<%=_getNote%>" nextFocus="add" width="500" type="textarea" row="2" col="55" needRemind="true" maxlength="30" properties="" position="top">
					<fs:columns>
						<fs:column display="摘要代号" name="AbstractCode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="摘要描述" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="sNote" name="AbstractID" type="<%=PagerTypeConstant.STRING %>" elType="hidden"/>
						<fs:pageElement elName="sCode" name="AbstractCode" type="<%=PagerTypeConstant.STRING %>" elType="hidden"/>
						<fs:pageElement elName="sNoteCtrl" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" elType="text"/>
					</fs:pageElements>
				</fs:dic>
        	</td>	    
        	<td width="5" height="25"></td>
        	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>    	
        </tr>
          
<%
/*		long lOfficeIDAbstract = sessionMng.m_lOfficeID;
		long lClientIDAbstract = sessionMng.m_lUserID;
		long lCurrencyIDAbstract = sessionMng.m_lCurrencyID;
		String strFormNameAbstract = "frm";
		String strCtrlNameAbstract = "sNote";
		String strTitleAbstract = " &nbsp;&nbsp;汇款用途";
		long lAbstractIDAbstract = lAbstractID;
		String strAbstractDescAbstract = (financeInfo.getNote()==null)?"":financeInfo.getNote();
		String strFirstTDAbstract = "";
		String strSecondTDAbstract = "";
		long maxLength = 100;
		String[] strNextControlsAbstract = null;
		strNextControlsAbstract = new String[]{"add"};
		
	OBMagnifier.createAbstractSettingCtrl(
		out,
		lOfficeIDAbstract,
		lClientIDAbstract,
		lCurrencyIDAbstract,
		strFormNameAbstract,
		strCtrlNameAbstract,
		strTitleAbstract,
		lAbstractIDAbstract,
		strAbstractDescAbstract,
		strFirstTDAbstract,
		strSecondTDAbstract,
		maxLength,
		strNextControlsAbstract);*/
%> 

        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
       <br>
<%
if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
{
if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ 
%>
      	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="" id="mytable1">
      <tr>
        <td>
        <table width="110" border="0" cellspacing="0" cellpadding="0">
           <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 自动续存</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        </table>
        </td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="" id="mytable2">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
			<TR >
			  <td width="5" height="25"  class="MsoNormal"></td>	
	          <TD height=20 width="18%">&nbsp;&nbsp;是否自动续存：</TD>
	          <TD height=20 width="35%"><input <%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"checked":"" %> type="checkbox" name="isautocontinue"  value="1"  onclick="changeContinueType(this)" id="isautocontinuetr"/></TD>					
			  <TD height="20" width="16%"></TD>
			  <TD height="20" width="39%"></TD>
			</TR>
			<TR  style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr1">
			<td width="5" height="25"  class="MsoNormal"></td>
	          <TD height=20 width="15%"><INPUT <%=rdoInterest!=2?"checked":"" %>  name=rdoInterest type=radio onfocus="nextfield ='';" value="1"/>本利续存</TD>
	          <TD height=20 width="30%"></TD>					
			  <TD height="20" width="16%"></TD>
			  <TD height="20" width="39%"></TD>
			</TR>
    <tr  class="MsoNormal" style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr2">
    <td width="5" height="25"  class="MsoNormal"></td> 
        <%
		//收款方账号放大镜
		String sDepositInterestToAccountNO = "";
		if(financeInfo.getAutocontinueaccountid() > 0 && rdoInterest==2)
		{
		      sDepositInterestToAccountNO = NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid());
		}
	 /*  	OBMagnifier.createInterPayeeBankNOCtrl(
	   		out,
	   		sessionMng.m_lCurrencyID,
	   		sessionMng.m_lClientID,
	   		"lInterestToAccountID",
	   		"strReceiveInterestAccountClientName",
	   		"frm",
	   		sDepositInterestToAccountNO,
	   		"lInterestToAccountID",
	   		"<INPUT "+(rdoInterest==2?"checked":"")+" name=rdoInterest type=radio onfocus=\"nextfield ='';\" value=\"2\"/>利息转至活期账号",
	   		" style=\"width:20%\" height=\"25\" nowrap class=\"MsoNormal\"",
	   		" nowrap width=\"190\" height=\"25\" ");*/
		%>
		 <td width="130" height="25" align="left"><INPUT <%=rdoInterest==2?"checked":"" %> name=rdoInterest type=radio onfocus="nextfield ='';" value="2"/>利息转至活期账号:</td>
					<td>
						<fs:dic id="lInterestToAccountIDCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='false,frm.lClientID.value, frm.lCurrencyID.value,frm.lInterestToAccountIDCtrl.value,frm.strReceiveInterestAccountClientName.value' value="<%=sDepositInterestToAccountNO%>" nextFocus="amount" width="400">
							<fs:columns> 
								<fs:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="lInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="lInterestToAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="strReceiveInterestAccountClientName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="hidlInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="hidstrReceiveInterestAccountClientName" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>	
		<td width="27%" nowrap>
		账户名称：<input disabled class="box" type="text" name="strReceiveInterestAccountClientName" value="<%=(financeInfo.getAutocontinueaccountid()>0&&rdoInterest==2)?NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid()):""%>">
		</td>	
    </tr>
    <input type="hidden" name="lInterestToAccountID" value="<%=financeInfo.getAutocontinueaccountid()>0?financeInfo.getAutocontinueaccountid():-1 %>">      
  
      </table>
       <br>
<% 
	}
}	
%>       
        <%--
       <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">链接附件</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
	            <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	            <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " 上 传 " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
        <%--   </td>
        </tr>
      </table>
      --%>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
        <td colspan="2" width="60%">
        </td>
          <td colspan="3" align="right" nowrap>
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name="add" type="button" value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'"> 
			<fs:obApprovalinitbutton controlName="approvalInit"
		 							value="保存并提交审批" 
									classType="button1" 
									onClickMethod="doSaveAndApprovalInit();"
									officeID="<%=sessionMng.m_lOfficeID%>" 
									currencyID="<%=sessionMng.m_lCurrencyID%>"
									clientID="<%=sessionMng.m_lClientID%>"
									moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
									transTypeID="<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>"/>

			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class="button1" name=addreset type=reset value=" 重 置 " >&nbsp;&nbsp; 
			
          </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
       </td>
  </tr>
</table>
<%
		String strClickCount = (String) session.getAttribute("clickCount");
		Log.print("strClickCount=" + strClickCount);
		if (strClickCount == null) 
		{
			strClickCount = "0";
		}
		if(sign.equals("again"))
		{
			financeInfo.setID(-1);
		}
%>

	  <input type="hidden" name="clickCount" value="<%=strClickCount%>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="lUpdateResult" value="">
	  <input type="hidden" name="confirmUserID" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>">
	  <input type="hidden" name="strAction" value="">
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <input type="hidden" name="payeeAcctID" value="" >
	  <input type="hidden" name="interestPayeeAcctID" value="" >	
	  <input type="hidden" name="timestamp" value="<%=System.nanoTime() %>" >	
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * 数据校验、表单显示控制及FORM提交
	 * javascript
	 */
	/* 汇款方式 */
	var iRemitType = frm.nRemitType.value;
	var iInterestRemitType = frm.nInterestRemitType.value;

	jump();
	change();

	/* 实现功能：动态显示根据汇款方式确定的收款方资料录入表单
	 * 实现方法：通过对TR中的ID属性控制实现
	 * 命名规则：不带放大镜的按一般命名规则(xxx)
	 * 			 通过放大镜实现的以Zoom结束(xxxZoom)
	 *			 同一放大镜对不同对象影响时通过加相应后缀区分(xxxZoomxxx)
	 */

	/* 收款方名称 */
	
    function saccountnochange()
    {
    	var a = frm.saccountno1.options[frm.saccountno1.selectedIndex].text;
    	frm.sPayerAccountNo.value = a;
    	frm.nPayerAccountID.value = frm.saccountno1.value;
    	//清空放大镜值
    	frm.sFixedDepositNo.value = -1;
    	frm.depositNo.value = "";
    	frm.mbalance.value = "";
    	frm.tsFixedDepositStart.value = "";
    	frm.nDisFixedDepositTime.value = "";
    	frm.dDepositAmount.value = "";
    	frm.dDepositRate.value = "";
		$.ajax(
		{
			type:"post",
			url:"<%=strContext%>/capital/fixed/returnOpendate.jsp",
			data:"accountId="+frm.saccountno1.value+"&currenctId="+frm.currencyId.value,
			async:false,
			success:function(result){
				var date = $(result).filter("div#result").text();
				frm.hiddenOpendate.value = date;
				frm.tsExecute.value = date;
			}
		}
		);
    	
    }
    	
	function jump()
	{	
		iRemitType = frm.nRemitType.value;
		iInterestRemitType = frm.nInterestRemitType.value;
		/* 本金 */
		payeeAcctLine.style.display = "none";
		payeeAcct.style.display = "none";
		payeeAcctNameLine.style.display = "none";
		payeeAcctName.style.display = "none";
		payeeBankNoLine.style.display = "none";
		payeeBankNo.style.display = "none";
		payeeBankNoNameLine.style.display = "none";
		payeeBankNoName.style.display = "none";
		payeePlaceLine.style.display = "none";
		payeePlace.style.display = "none";
		payeeBankNameLine.style.display = "none";
		payeeBankName.style.display = "none";
		line1.style.display = "none";
		blank1.style.display = "none";
		line2.style.display = "none";
		blank2.style.display = "none";
		/* 利息 */
		InterestPayeeAcctLine.style.display = "none";
		InterestPayeeAcct.style.display = "none";
		InterestPayeeAcctNameLine.style.display = "none";
		InterestPayeeAcctName.style.display = "none";
		InterestPayeeBankNoLine.style.display = "none";
		InterestPayeeBankNo.style.display = "none";
		InterestPayeeBankNoNameLine.style.display = "none";
		InterestPayeeBankNoName.style.display = "none";
		InterestPayeePlaceLine.style.display = "none";
		InterestPayeePlace.style.display = "none";
		InterestPayeeBankNameLine.style.display = "none";
		InterestPayeeBankName.style.display = "none";
		InterestLine1.style.display = "none";
		InterestBlank1.style.display = "none";
		InterestLine2.style.display = "none";
		InterestBlank2.style.display = "none";
		

		/* 根据汇款方式确定所显示的TR */
		/*本金*/
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  // 汇款方式银行付款		
		{
			payeeBankNoLine.style.display = "";
			payeeBankNo.style.display = "";
			payeeBankNoNameLine.style.display = "";
			payeeBankNoName.style.display = "";
			payeePlaceLine.style.display = "";
			payeePlace.style.display = "";
			payeeBankNameLine.style.display = "";
			payeeBankName.style.display = "";	
						
		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			payeeAcctLine.style.display = "";
			payeeAcct.style.display = "";
			payeeAcctNameLine.style.display = "";
			payeeAcctName.style.display = "";
			if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>) // 汇款方式内部转账
			{
				line1.style.display = "";
				blank1.style.display = "";
				line2.style.display = "";
				blank2.style.display = "";
			}			
		}

		/*利息*/
		if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  // 汇款方式银行付款		
		{
			InterestPayeeBankNoLine.style.display = "";
			InterestPayeeBankNo.style.display = "";
			InterestPayeeBankNoNameLine.style.display = "";
			InterestPayeeBankNoName.style.display = "";
			InterestPayeePlaceLine.style.display = "";
			InterestPayeePlace.style.display = "";
			InterestPayeeBankNameLine.style.display = "";
			InterestPayeeBankName.style.display = "";		
			
		}

		if(iInterestRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			InterestPayeeAcctLine.style.display = "";
			InterestPayeeAcct.style.display = "";
			InterestPayeeAcctNameLine.style.display = "";
			InterestPayeeAcctName.style.display = "";
			if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>) // 汇款方式内部转账
			{
				InterestLine1.style.display = "";
				InterestBlank1.style.display = "";
				InterestLine2.style.display = "";
				InterestBlank2.style.display = "";
			}
		}
	}
	//add by zcwang 2007-9-27 为签名所用
	function checkValues()
	{
		//本金
		frm.nRemitTypeHidden.value=frm.nRemitType.value;
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			frm.payeeAcctID.value=frm.nPayeeAccountID1.value;
		}
		else 	if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>) //  汇款方式银行付款
		{
			frm.payeeAcctID.value=frm.nPayeeAccountID2.value;
		}
		//利息
		frm.nInterestRemitTypeHidden.value=frm.nInterestRemitType.value;
		if(iInterestRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			frm.interestPayeeAcctID.value=frm.nInterestPayeeAccountID1.value;
		}
		else if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>) // 汇款方式银行付款
		{
			frm.interestPayeeAcctID.value=frm.nInterestPayeeAccountID2.value;
		}
	}
	//
	function change()
	{
		frm.nFixedDepositTime.value = frm.nDisFixedDepositTime.value;
	}
	
	function getNextField()
	{
              //>>>>add by shiny 20030403
      	      iRemitType = frm.nRemitType.value;
			  if (iRemitType == -1)
			  {//没有选择
			  	  alert("汇款方式不能为空，请选择");
				  frm.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frm.sPayeeAccountInternalCtrl.focus();
			  }
			  else
			  {
                  frm.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

	function format()
	{
		frm.amount.value = frm.amount.value;
	}
	
	function getNextField1()
	{
              //>>>>add by shiny 20030403
      	      iInterestRemitType = frm.nInterestRemitType.value;
			  if (iInterestRemitType == -1)
			  {//没有选择
			  	  alert("汇款方式不能为空，请选择");
				  frm.nInterestRemitRemitType.focus();  	
			  }
              else if (iInterestRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frm.sInterestPayeeAccountInternalCtrl.focus();
			  }
			  else
			  {
                  frm.sInterestPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }
    
        /*保存并提交审批*/
    function doSaveAndApprovalInit()
	{
		checkValues();
		frm.action = "f013-c.jsp?operate=saveAndApproval&sign=<%=sign%>";
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		var signatureValue;
		if (validate() == true)
        {
			change();
			
		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new FixedToCurrentTransferOperator();
		    	sign(format,frm);
		    }
			
			//确认保存并提交审批
			if (!confirm("是否保存并提交审批？"))
			{
				return false;
			}
			
            showSending();
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
	}

    /* 修改保存处理函数 */
    function addme()
    {
		checkValues();
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		var signatureValue;
		if (validate() == true)
        {
			change();
			
		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new FixedToCurrentTransferOperator();
		    	sign(format,frm);
		    }
		     
			//确认保存 
			if (!confirm("是否保存？"))
			{
				return false;
			}
            showSending();
			frm.nRemitTypeHid.value=frm.nRemitType.value;
			frm.nInterestRemitTypeHid.value=frm.nInterestRemitType.value;

            frm.action = "f013-c.jsp?sign=<%=sign%>";
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
    	//新增取消
		if (frm.lInstructionID.value == -1)
		{	
			frm.action="f011-c.jsp";
			frm.submit();
		}
		//修改取消
		else
		{
       		//history.go(-1);
       		frm.lUpdateResult.value="";
       		frm.action="f013-c.jsp";
       		frm.submit();
		}
    }
    
    /*日期转换*/
    function dateformat(){
		var date=frm.tsExecute.value.split("-");
		for(i=0;i<=2;i++)
		if(date[i]!=null&&date[i].length==1)
		date[i]="0"+date[i];
		date=date.join("-");
		frm.tsExecute.value = date ;
    }

    /* 校验函数 */
    function validate()
    {
       
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		iRemitType = frm.nRemitType.value;
		iInterestRemitType = frm.nInterestRemitType.value;
		/* 付款方资料非空校验 */
		if (frm.sPayerName.value == "")
		{
			alert("付款方名称不能为空，请录入");
			frm.sPayerName.focus();
			return false;
		}
		
		if (frm.nPayerAccountID.value == ""||frm.nPayerAccountID.value==-1)
		{
			alert("付款方账号，请从放大镜中选择");
			frm.saccountno1.focus();
			return false;
		}
		if (frm.depositNo.value == ""||frm.depositNo.value==-1)
		{
			alert("付款方定期存单不能为空，请从放大镜中选择");
			frm.depositNo.focus();
			return false;
		}
		
		/*汇款方式*/
		if(frm.nRemitType.value == -1)
		{
			alert("汇款方式不能为空，请选择");
			frm.nRemitType.focus();
			return false;
		}
		/*利息汇款方式*/
		if(frm.nInterestRemitType.value == -1)
		{
			alert("利息汇款方式不能为空，请选择");
			frm.nInterestRemitType.focus();
			return false;
		}
		/*定期支取的汇款方式必须一样*/
		/*
		if(iRemitType != iInterestRemitType){
		  alert("请选择同一种汇款方式");
		  frm.nRemitType.focus();
			return false;
		}
		*/
		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 汇款方式电汇
		{
			
			if (frm.sPayeeNameZoomAcctCtrl.value == "" )
			{
				alert("收款方名称或账号，请从放大镜中选择");
				frm.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}
		
			if (frm.sPayeeAcctNoZoomCtrl.value == ""||frm.sPayeeAcctNoZoomhiddenValue.value<0)
			{
			    
				alert("收款方账号，请从放大镜中选择");
				frm.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{
			if (frm.sPayeeName.value == "")
			{
				alert("收款方名称不能为空，请录入");
				frm.sPayeeAccountInternalCtrl.focus();
				return false;
			}
			if (frm.sPayeeAccountInternalCtrl.value == ""||frm.sPayeeBankAccountNOInternal.value<0)
			{                                                  
			
				alert("收款方账号，请从放大镜中选择");
				frm.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		}
		
		
		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 汇款方式电汇
		{
			
			if (frm.sInterestPayeeNameZoomAcctCtrl.value == "")
			{
				alert("利息收款方名称或账号，请从放大镜中选择");
				frm.sInterestPayeeAcctNoZoomCtrl.focus();
				return false;
			}
		
			if (frm.sInterestPayeeAcctNoZoomCtrl.value == ""||frm.sInterestPayeeAcctNoZoomhiddenValue.value<0)
			{
				alert("利息收款方账号，请从放大镜中选择");
				frm.sInterestPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iInterestRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{
			if (frm.sInterestPayeeName.value == "")
			{
				alert("利息收款方名称不能为空，请录入");
				frm.sInterestPayeeAccountInternalCtrl.focus();
				return false;
			}
			if (frm.sInterestPayeeAccountInternalCtrl.value == ""||frm.PayeeBankAccountNOInternal.value<0)
			{
				alert("利息收款方账号，请从放大镜中选择");
				frm.sInterestPayeeAccountInternalCtrl.focus();
				return false;
			}
		}

		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frm.amount, 1, "交易金额"))
		{
			return false;
		}
		
		/*if(frm.amount.value != frm.dDepositAmount.value)
		{
			alert("支取金额必须等与存单金额");
			return false;
		}*/

		/* 执行日校验 */
		if(document.all("tsExecute").value=="")
		{
			alert("执行日不能为空，请录入");
			document.all("tsExecute").focus();
			return false;
		}
		if(!CompareDateString(frm.hiddenOpendate.value,frm.tsExecute.value))
		{
			alert("执行日不能小于系统开机日！");
			frm.tsExecute.focus();
			return false;
		}
				/* 汇款用途 */
		if (!InputValid(frm.sNoteCtrl, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}	
		
		var dBalance = parseFloat(reverseFormatAmount(frm.mbalance.value)) -
							parseFloat(reverseFormatAmount(frm.amount.value));
		if (dBalance < 0)
		{
			alert("支取金额不能大于存单余额");
			frm.amount.focus();
			return false;
		}
		if(Trim(frm.amount.value)=="")
		{
			alert("支取金额不能为空");
			frm.amount.focus();
			return false;
		}
		if(parseFloat(reverseFormatAmount(frm.amount.value))<0.01){
			alert("支取金额不能小于0.01");
			frm.amount.focus();
			return false;
		}
		/*解决 BUG #13770::网银端，定期开立、定期支取、到期续存、通知开立、通知支取，没有汇款用途时，保存报白页。qushuang 2012-08-23*/
		if (frm.sNoteCtrl.value == ""||frm.sNoteCtrl.value<0)
		{
			alert("汇款用途不能为空，请从放大镜中选择");
			frm.sNoteCtrl.focus();
			return false;
		}
		/* BUG #13770 END */
    	return true;
    }
	//日期或期限改变时自动改变
	function dateChange(form,nData1,nData2)
	{
		var nData3;
		if (nData2 != -1&& nData1 !="")
		{
			nData3 = addDate(nData1,nData2,form);
			if(CompareDateString(nData3,'<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>'))
			{
				document.frm.amount.readOnly=true;
			}
			
		}
	}
	function addDate(strInputDate,lMonth,form)
	{
		var temp,s;
			temp=new String(strInputDate);
			s=new String("");
		
			for(var i=0;i<=temp.length-1;i++)
			{
	 			if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
			{
	  				s=s+"/";
			}	
	  		else
	 			{
	  				if(isNaN(Number(temp.charAt(i))))
	  				{
	   				///alert("请输入正确的开始日期");
	    			///form.strStartDate.focus();
	  					return false;
				}    
	  				else
				{
					s=s+temp.charAt(i);
				}
			}	
	 		}

		dtDate = new Date(s);
		var strDate;
		var dtDay;
		var dtMonth;
		var dtYear;
		var temp;
		
		if (parseInt(lMonth) < 10000)
		{						     
			dtMonth = parseInt(lMonth) + dtDate.getMonth();	
			var dtMonthTemp = parseInt(lMonth) + dtDate.getMonth()+1;	
			
			dtDay = dtDate.getDate();			
			if(dtMonthTemp == 4||dtMonthTemp ==6||dtMonthTemp ==9||dtMonthTemp==11)
			{
			   if(dtDay == 31)
			     dtDate.setDate(30);
			}
			else if(dtMonthTemp == 2)
			{
			  if(dtDay == 30||dtDay == 31 || dtDay == 29)
			  {
			     if(dtDate.getYear()%4 ==0)
				    dtDate.setDate(29);
				 else
				    dtDate.setDate(28);
			  }
			}	
			dtDate.setMonth(dtMonth);
		}
		else
		{		   
			lMonth = parseInt(lMonth) - 10000;
			dtDay = parseInt(lMonth) + dtDate.getDate();
			dtDate.setDate(dtDay);
			
		//	dtMonth = dtDate.getMonth() + 1;
			//	dtDate.setMonth(dtMonth);
			//	dtMonth = dtDate.getMonth();
			//	dtDay = dtDate.getDate();			
			//	if(dtMonth == 4||dtMonth ==6||dtMonth ==9||dtMonth==11)
			//	{
			//	   if(dtDay == 31)
		//		     dtDate.setDate(30);
			//	}
		//		if(dtMonth == 2)
			//	{
			//	  if(dtDay == 30||dtDay == 31 || dtDay ==29)
			//	  {
			//	     if(dtDate.getYear()%4 ==0)
			//		    dtDate.setDate(29);
			//		 else
			//		    dtDate.setDate(28);
			//	  }
			//}			
		}
		
		dtDay = dtDate.getDate();
		dtMonth = dtDate.getMonth()+1;
		dtYear = dtDate.getYear();
		dtYearTest = new String(dtYear);
		if(dtYearTest.length == 2)
		{
		 dtYearTest = "19"+dtYearTest;
		 dtYear=dtYearTest;
		}
		if (dtMonth == 0)
		{
			dtYear--;
			dtMonth = 12;
		}
		
		eval("strDate='"+ dtYear + "-" +dtMonth + "-" + dtDay + "'");
		//return strDate;
	    return formatDateString(strDate);
	}
	/**
	 * 格式化日期(xxxx-xx-xx)
	 * @param strDate 需要格式化的日期
	 * @return 返回格式化的日期
	 */ 
	function formatDateString(strDate){
		
		var splitDate,strYear,strMonth,strDay;
		
		splitDate = strDate.split(" ");
		
		strDate = splitDate[0];

		splitDate = strDate.split("-");
		
		strYear = splitDate[0];
		if (strYear.length < 2 )
		{
			strYear = "0" + strYear;
		}
		strMonth = splitDate[1];
		if (strMonth.length < 2 )
		{
			strMonth = "0" + strMonth;
		}
		strDay = splitDate[2];
			
		return strYear + "-" + strMonth + "-" + strDay;
	}

<%
if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
{
if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ 
%>	
		function changeContinueType(obj)
		{
			if((parseFloat(reverseFormatAmount(frm.amount.value)) == parseFloat(reverseFormatAmount(frm.mbalance.value))))
			{
				alert("划款金额与存单余额相同，不用续存！");
				document.getElementById("isautocontinuetr").disabled=true;
				document.getElementById("isautocontinuetr").checked=false;
			}else{
				document.getElementById("isautocontinuetr").disabled=false;
			}
			var isautocontinuetr1 = document.getElementById("isautocontinuetr1");
			var isautocontinuetr2 = document.getElementById("isautocontinuetr2");
			var rdoInterest = document.getElementsByName("rdoInterest");
			if(obj.checked==true)
			{
				isautocontinuetr1.style.display="";
				isautocontinuetr2.style.display="";
				for(var i = 0;i<rdoInterest.length;i++)
				{
					rdoInterest.disabled=false;
				}
			}
			else
			{
				isautocontinuetr1.style.display="none";
				isautocontinuetr2.style.display="none";
				for(var i = 0;i<rdoInterest.length;i++)
				{
					rdoInterest.disabled=true;
				}
			}
		}
<%
	}
}	
%>	
	
		function checkdAmount()
		{
<%
if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
{
if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ 
%>			
		
			//alert(parseFloat(reverseFormatAmount(frm.amount.value)) + "|--|" + parseFloat(reverseFormatAmount(frm.mbalance.value)));
			var isautocontinuetr = document.getElementById("isautocontinuetr");
			if((parseFloat(reverseFormatAmount(frm.amount.value)) == parseFloat(reverseFormatAmount(frm.mbalance.value))))
			{
				
//				isautocontinuetr.disabled=false;
//				$("#mytable1").show();
//				$("#mytable2").show();
//			}else {
				isautocontinuetr.disabled=true;
				$("#mytable1").hide();
				$("#mytable2").hide();
			}else {
				isautocontinuetr.disabled=false;
				$("#mytable1").show();
				$("#mytable2").show();				
			}
<%
	}
}	
%>			
		}
	 	
	
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frm)");
	firstFocus(frm.depositNo);
//	//setSubmitFunction("addme(frm)");
	setFormName("frm");
	var s1 = document.getElementsByName("tsFixedDepositStart");
	var s2 = document.getElementsByName("nDisFixedDepositTime");
	s1[0].onpropertychange=function(){dateChange(frm,frm.tsFixedDepositStart.value,frm.nDisFixedDepositTime.value)};
	s2[0].onpropertychange=function(){dateChange(frm,frm.tsFixedDepositStart.value,frm.nDisFixedDepositTime.value)};
	
</script>

<%
		/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);	
    }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />