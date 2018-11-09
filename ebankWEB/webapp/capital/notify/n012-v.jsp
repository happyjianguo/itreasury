<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<!--
/*
 * 程序名称：n012-v.jsp
 * 功能说明：通知支取修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 *modify by wjliu 2007/3/27 修改 修改页面中收款方账号的显示方式,改为按汇款方式显示
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.settlement.util.NameRef,
				    com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				    java.text.SimpleDateFormat"
				   
%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[通知支取]";
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
	String strTemp=null;
	long dPayableInterest=-1;
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
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		
		
		
		financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		payerInfo = (PayerOrPayeeInfo)request.getAttribute("payerInfo");
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo = financeInstr.findByID(financeInfo.getID(), sessionMng.m_lUserID, sessionMng.m_lCurrencyID);
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
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
		strTemp = (String)request.getAttribute("lSubAccount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dPayableInterest = Long.parseLong(strTemp);
		}
		
		if(financeInfo.getOfficeID()>0&&financeInfo.getCurrencyID()>0)
		{
			opendate = Env.getSystemDate(financeInfo.getOfficeID(),financeInfo.getCurrencyID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strOpenDate = sdf.format(opendate);
		}
				
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:security/>
<form method="post" name="frm">
<safety:certInformation/>
<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">
<input type="hidden" name="hiddenOpendate" value="<%=strOpenDate %>">
<input type="hidden" name="sNotifyDepositNo" value="<%=financeInfo.getSubAccountID()  %>" >
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">通知支取</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	   
	<br/>
     <table align="" width="140" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 通知账户资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>	   
    </table> 
     <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr class="MsoNormal">
		  <td width="5" height="25"></td>
     	  <td width="17%" height="25" class="MsoNormal" >通知账户名称：</td>
          <td height="25" class="MsoNormal" >
            <input type="text" class="box" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? payerInfo.getAccountName() : financeInfo.getPayerName() %>" readonly>
          </td>
          <td height="25"></td>
          <td height="25" colspan="3"  class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
		  <td height="25"><font color='#FF0000'>* </font></td>      
     	  <td height="25" class="MsoNormal" >通知存款账号：</td>
          <td height="25" class="MsoNormal" >
	  	<%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.NOTIFY,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
            <input type="hidden"  name="sPayerAccountNo" value="" >
			<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>" >
          </td>
          <td height="25"></td>
          <td height="25" colspan="3"  class="MsoNormal"></td>
        </tr>
        <% 
        long lTypeID = 21;
		long lDepositTypeIDFixedDepositNo = 2;
		long lAccountID = (financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID();
		String strAccountIDCtrlFixedDepositNo = "nPayerAccountID";		
		String[] strNextControlsFixedDepositNo = {"nRemitType"};
		String strRtnEndDateCtrlFixedDepositNo= "";
		String strRtnOpenDateCtrlFixedDepositNo = "";
		String strRtnCapitalCtrlFixedDepositNo = "dDepositAmount";
		String strRtnBalanceCtrlFixedDepositNo = "dDepositBalance";
		String strRtnRateCtrl = "";
		String strRtnIntervalCtrl = "nDisNoticeDay";
		String strRtnStartDateCtrl = "tsNotifyDepositStart";
		String strFirstTD = " width=\"130\" height=\"25\" class=\"MsoNormal\" nowrap";
		String sysdate= Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strSecondTD = " width=\"430\" height=\"25\"  ";
		%>
        <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
		<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">
		<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>">
		<input type="hidden" name="lTypeID" value="<%=lTypeID %>">
		<input type="hidden" name="sysdate" value="<%=sysdate %>">
		<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>"> 
		<input  type="hidden" name="strAccountIDCtrlFixedDepositNo" value="<%=strAccountIDCtrlFixedDepositNo %>">
		<!--a try for glass start-->
		<tr class="MsoNormal">
		<td height="25"><font color='#FF0000'>* </font></td>
		<td height="25" align="left">通知存款单据号：</td>
				<td>
					<fs:dic id="depositNo" size="22" form="frm" title="通知存款单据号" sqlFunction="getFixedDepositNoSQL"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,2,frm.nPayerAccountID.value,frm.lUserID.value,frm.depositNo.value,frm.lTypeID.value,frm.sysdate.value' value="<%=financeInfo.getDepositNo()%>" nextFocus="nRemitType" width="600">
						<fs:columns> 
							<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							<fs:column display="开户日" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						</fs:columns>
						<fs:pageElements>
							<fs:pageElement elName="depositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs:pageElement elName="dDepositBalance" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs:pageElement elName="nDisNoticeDay" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs:pageElement elName="tsNotifyDepositStart" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs:pageElement elName="sNotifyDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							<fs:pageElement elName="sNotifyDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							
							
						</fs:pageElements>							
					</fs:dic> 
					<input type="hidden" name="SubAccountID" value="<%=String.valueOf(dPayableInterest) %>">
					<input type="hidden" name="AccountID" value="<%=String.valueOf(lAccountID) %>">
		</td>		
	
		
      <td ></td>
      <td ></td>
      		<td height="25" colspan="2"  class="MsoNormal"></td>
      
		</tr>
		
        <tr class="MsoNormal">
          	<td height="25"></td>
      		<td height="25" class="MsoNormal">通知存款起始日：</td>
      		<td height="25" class="MsoNormal"> 
        		<input type="text" class="box" name="tsNotifyDepositStart" size="30" value="<%= (financeInfo.getID()==-1 && financeInfo.getDepositStart()==null)? "":financeInfo.getDepositStart().toString().substring(0,10) %>" readonly>
        	</td>
        </tr>
        <tr>
        	<td height="25"></td>
        	<td><font class="MsoNormal" >品&nbsp;&nbsp;&nbsp;&nbsp;种： </font> </td>
        	<td>
<%
		SETTHTML.showFixedDepositMonthListCtrl(out,"nDisNoticeDay",SETTConstant.TransQueryType.NOTIFY,financeInfo.getNoticeDay(),true,"disabled",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
			<input type="hidden" name="nNoticeDay" value="<%= financeInfo.getNoticeDay() %>" ></td>
          	<td colspan="4"></td>
        </tr>
        <tr class="MsoNormal">
         	<td height="25"></td>
      		<td height="25" class="MsoNormal">存款本金：</td>
      		<td height="25" class="MsoNormal" nowrap> 
		      	<fs:amount 
			       		form="frm"
		       			name="dDepositAmount"
		       			value="<%=financeInfo.getDepositAmount() %>"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
   			</td>
		</tr>
		<tr>
			<td height="25"></td>
			<td>
		        <font class="MsoNormal" >存款余额： </font> 
			</td>
			<td>
		  		<fs:amount 
			       		form="frm"
		       			name="dDepositBalance"
		       			value="<%=financeInfo.getDepositBalance() %>"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
        	</td>
          	<td colspan="4"></td>
        </tr>
      </table>
      <br>
      <table align="" width="140" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 通知支取方式</td>
	<td width="17"><a class=lab_title3></td>
</tr>		   
     </table> 
     
	  <table align=""  width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr class="MsoNormal">
          <td width="49%">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
        		<tr class="MsoNormal">
          			<td width="5" height="25"></td>
          			<td width="35%" height="25"><p><span class="MsoNormal">本金：</span></p></td>
          			<td height="25"></td>  			
        		</tr>
        		<tr class="MsoNormal">
          			<td height="25"></td>
          			<td height="25"><p><span class="MsoNormal">汇款方式：</span></p></td>
          			<td height="25">
					<input type="hidden" name="nRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getRemitType() %>">
<%
				//	OBHtmlCom.showRemitTypeListControl1(out,"nRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly");
					OBHtmlCom.showRemitTypeListControl1(out,"nRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType(),"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ");

%>
					<input type="hidden" name="nRemitTypeHid" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType() %>">
					</td>  			
        		</tr>
				<tr id="payeeAcctLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeeAcct" class="MsoNormal">
          			<td height="25"><font color='#FF0000'>* </font></td>
          		
<%
					String sPayeeAccountInternal_value="";
					if( financeInfo.getRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)
					{
						sPayeeAccountInternal_value =NameRef.getNoLineAccountNo( financeInfo.getPayeeAcctNo());
					}%>	                
            		<td height="25" align="left">收款方账号：</td>
					<td >
						<fs:dic id="sPayeeAccountInternalCtrl" size="20" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=sPayeeAccountInternal_value%>" nextFocus="nRemitType" width="500">
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
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeeAcctName" class="MsoNormal">
          			<td height="25"><font color='#FF0000'>* </font></td>
          			<td height="25"><p><span class="MsoNormal">收款方名称：</span></p></td>
          			<td height="25">
          			<input type="hidden" name="nPayeeAccountID1" value="<%=  financeInfo.getPayeeAcctID() %>" >
						<input type="text" class="box" name="sPayeeName" value="<%= (financeInfo.getRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)?financeInfo.getPayeeName():"" %>" size="24" readonly>
					</td>  			
        		</tr>
				<tr id="payeeBankNoLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeeBankNo" class="MsoNormal">
          			<td height="25"><font color='#FF0000'>* </font></td>
<%
				String sPayeeAcctNoZoom_value = "";
				if( financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)
				{
					sPayeeAcctNoZoom_value = financeInfo.getPayeeAcctNo();

				}
				
				%>				
					<td height="25" align="left">收款方账号：</td>
					<td >
						<fs:dic id="sPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sPayeeAcctNoZoomCtrl.value,frm.sPayeeNameZoomAcctCtrl.value' value="<%=sPayeeAcctNoZoom_value%>" nextFocus="amount" width="500">
							<fs:columns> 
								<fs:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nPayeeAccountID2" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeNameZoomAcctCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
        		</tr>
				<tr id="payeeBankNoNameLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeeBankNoName" class="MsoNormal">
          			<td height="25"><font color='#FF0000'>* </font></td>
          			<td height="25">
              <p><span class="MsoNormal">收款方名称：</span></p>
            </td>
          			<td height="25">
					<input type="text" class="box" name="sPayeeNameZoomAcctCtrl" value="<%= (financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeName():"" %>" onfocus="nextfield ='sPayeeProv';" maxlength="50" readonly>
					</td>  			
        		</tr>
				<tr id="payeePlaceLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeePlace" class="MsoNormal">
          			<td height="25"></td>
          			<td height="25"><p><span class="MsoNormal">汇入地：</span></p></td>
          			<td height="25">
						<input type="text" class="box" name="sPayeeProv" value="<%= ( financeInfo.getPayeeProv()!=null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getPayeeProv():"" %>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15" readonly>
            			省
						<input type="text" class="box" name="sPayeeCity" value="<%=  (financeInfo.getPayeeCity()!=null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)? financeInfo.getPayeeCity():"" %>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15" readonly>
              市（县）</td>  			
        		</tr>
				<tr id="payeeBankNameLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="payeeBankName" class="MsoNormal">
          			<td height="25"></td>
          			<td height="25"><p><span class="MsoNormal">汇入行名称：</span></p></td>
          			<td height="25">
						<input type="text" class="box" name="sPayeeBankName" value="<%=  (financeInfo.getPayeeBankName()!=null && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY)? financeInfo.getPayeeBankName():"" %>" size="30" onfocus="nextfield ='nInterestRemitType';" maxlength="50" readonly>
						<input type="hidden" name="nPayeeAccountID2" value="<%=  financeInfo.getPayeeAcctID() %>" >
					</td>  			
        		</tr>
				<tr id="line1" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="blank1"  class="MsoNormal">
          			<td height="25"></td>
          			<td height="25">
              <p>&nbsp;</p>
            </td>
          			<td height="25"></td>  			
        		</tr>
				<tr id="line2" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="blank2" class="MsoNormal">
          			<td height="25"></td>
          			<td height="25">
             	    <p>&nbsp;</p>
           			</td>
          			<td height="25"></td>  			
        		</tr>
			</table>  
		  </td>
		  <td width="1"></td>
		  <td width="49%">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        		<tr class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25"><p><span class="MsoNormal">利息：</span></p></td>
          			<td width="240" height="25"></td>  			
        		</tr>
        		<tr class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25"><p><span class="MsoNormal">汇款方式：</span></p></td>
          			<td width="240" height="25">
					<input type="hidden" name="nInterestRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getInterestRemitType() %>">
<%
				//	OBHtmlCom.showRemitTypeListControl1(out,"nInterestRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.BANKPAY:financeInfo.getInterestRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly");
				
				
					OBHtmlCom.showRemitTypeListControl1(out,"nInterestRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType(),"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ");
%>
					<input type="hidden" name="nInterestRemitTypeHid" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType() %>">
					</td>  			
        		</tr>
				<tr id="InterestPayeeAcctLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeAcct" class="MsoNormal">
          			<td width="4" height="25"><font color='#FF0000'>* </font></td>
<%
	System.out.println("------------------------------------------------利息:"+ financeInfo.getInterestRemitType());
					String sInterestPayeeAccountInternalCtrl ="";
					if( financeInfo.getInterestRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)
					{
					   sInterestPayeeAccountInternalCtrl = financeInfo.getInterestPayeeAcctNo();

					}
%>	  			
					<td width="130" height="25" align="left">收款方账号：</td>
					<td >
						<fs:dic id="sInterestPayeeAccountInternalCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sInterestPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=sInterestPayeeAccountInternalCtrl%>" nextFocus="nRemitType" width="500">
							<fs:columns> 
								<fs:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sInterestPayeeAccountInternalCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="nInterestPayeeAccountID1" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="sInterestPayeeName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="PayeeBankAccountNOInternal" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
					</td>
        		</tr>
				<tr id="InterestPayeeAcctNameLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeAcctName" class="MsoNormal">
          			<td width="4" height="25"><font color='#FF0000'>* </font></td>
          			<td width="120" height="25"><p><span class="MsoNormal">收款方名称：</span></p></td>
          			<td width="240" height="25">
          			<input type="hidden" name="nInterestPayeeAccountID1" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >
						<input type="text" class="box" name="sInterestPayeeName" value="<%= (financeInfo.getInterestRemitType() == OBConstant.SettRemitType.INTERNALVIREMENT)?financeInfo.getInterestPayeeName():"" %>" size="24" readonly>
					</td>  			
        		</tr>
				<tr id="InterestPayeeBankNoLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeBankNo" class="MsoNormal">
          			<td width="4" height="25"><font color='#FF0000'>* </font></td>
<%
				String sInterestPayeeAcctNoZoomCtrl_value = "";
				//收款方账号放大镜（汇）
				if( financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)
				{
					sInterestPayeeAcctNoZoomCtrl_value = financeInfo.getInterestPayeeAcctNo();

				}
%>			
					<td width="130" height="25" align="left">收款方账号：</td>
					<td >
						<fs:dic id="sInterestPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sInterestPayeeAcctNoZoomCtrl.value,frm.sInterestPayeeNameZoomAcctCtrl.value' value="<%=sInterestPayeeAcctNoZoomCtrl_value%>" nextFocus="amount" width="500">
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
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeBankNoName" class="MsoNormal">
          			<td width="4" height="25"><font color='#FF0000'>* </font></td>
          			<td width="120" height="25">
              <p><span class="MsoNormal">收款方名称：</span></p>
            </td>
          			<td width="240" height="25">
					<input type="text" class="box" name="sInterestPayeeNameZoomAcctCtrl" value="<%= (financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeName():"" %>" onfocus="nextfield ='sInterestPayeeProv';" maxlength="50" readonly>
					</td>  			
        		</tr>
				<tr id="InterestPayeePlaceLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeePlace" class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25"><p><span class="MsoNormal">汇入地：</span></p></td>
          			<td width="240" height="25">
						<input type="text" class="box" name="sInterestPayeeProv" value="<%= ( financeInfo.getInterestPayeeProv()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeProv():"" %>" size="10" onfocus="nextfield ='sInterestPayeeCity';" maxlength="15" readonly>
            			省
						<input type="text" class="box" name="sInterestPayeeCity" value="<%=  (financeInfo.getInterestPayeeCity()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeCity():"" %>" size="10" onfocus="nextfield ='sInterestPayeeBankName';" maxlength="15" readonly>
              市（县）</td>  			
        		</tr>
				<tr id="InterestPayeeBankNameLine" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestPayeeBankName" class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25">
              <p><span class="MsoNormal">汇入行名称：</span></p>
            </td>
          			<td width="240" height="25">
						<input type="text" class="box" name="sInterestPayeeBankName" value="<%=  (financeInfo.getInterestPayeeBankName()!=null && financeInfo.getInterestRemitType() == OBConstant.SettRemitType.BANKPAY)?financeInfo.getInterestPayeeBankName():"" %>" size="30" onfocus="nextfield ='amount';" maxlength="50" readonly>
						<input type="hidden" name="nInterestPayeeAccountID2" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >	
					</td>  			
        		</tr>
				<tr id="InterestLine1" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestBlank1" class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25">
              <p>&nbsp;</p>
            </td>
          			<td width="240" height="25"></td>  			
        		</tr>
				<tr id="InterestLine2" class="MsoNormal">
          			<td colspan="3" height="1"></td>
        		</tr>
        		<tr id="InterestBlank2" class="MsoNormal">
          			<td width="4" height="25"></td>
          			<td width="120" height="25">
              <p>&nbsp;</p>
            </td>
          			<td width="240" height="25"></td>  			
        		</tr>
			</table>  
		  </td>
        </tr>
      </table>
	   <br>
      <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="800"><a class=lab_title3></td>
</tr>	   
     </table> 
      <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
       
        <tr class="MsoNormal">
          <td width="5" height="25"><font color='#FF0000'>* </font></td>
          <td width="110" height="25" class="MsoNormal">金额：</td>
          <td height="25" width="590" class="MsoNormal"  >
            <fs:amount 
	       		form="frm"
       			name="amount"
       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount())) %>"
       			nextFocus="tsExecute"
       			chineseCtrlName="sChineseAmount"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" >大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25" class="MsoNormal">
			<input type="text" class="box" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"><font color='#FF0000'>* </font></td>
          <td width="17%" height="25" class="MsoNormal" >执行日：</td>
          <td height="25" class="MsoNormal" >
          <fs_c:calendar 
         	    name="tsExecute"
	          	value="<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>" 
	          	properties="nextfield ='sNoteCtrl'" 
	          	size="20"/>
		  </td>

          <td width="5" height="25"></td>
        </tr>
        
        <tr class="MsoNormal">
        	<td width="4" height="25"><font color='#FF0000'>* </font></td>
        	<td width="130" height="25" align="left">汇款用途：</td>	
        	<td width="500" height="25">
				<fs:dic id="sNoteCtrl" form="frm" title="汇款用途" sqlFunction="getAbstractSettingSQL" sqlParams="frm.lOfficeID.value,frm.lUserID.value,frm.sNoteCtrl.value" nextFocus="add" width="500" value="<%=financeInfo.getNote() %>" type="textarea" row="2" col="55" needRemind="true" maxlength="30" properties="" position="top">
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
        	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>    	
        </tr>          

      </table>
      <br>
   			<% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " 上 传 " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
      
      <br>
      <table width="100%" align="" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="5" align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'">
			<fs:obApprovalinitbutton controlName="approvalInit" 
		 							value="保存并提交审批" 
									classType="button1" 
									onClickMethod="doSaveAndApprovalInit();" 
									officeID="<%=sessionMng.m_lOfficeID%>" 
									currencyID="<%=sessionMng.m_lCurrencyID%>" 
									clientID="<%=sessionMng.m_lClientID%>" 
									moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
									transTypeID="<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>"/>
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<%--<input class=button1 name=add type=button value=" 重置 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">--%>
			<input class=button1 name=addreset type=reset value=" 重 置 " >&nbsp;&nbsp;
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
	  	<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  	<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="confirmUserID" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>">
	  <input type="hidden" name="strAction" value="">
	  <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify() %>">
	  <input type="hidden" name="timestamp" value="<%=System.nanoTime() %>">
	  	  
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <input type="hidden" name="payeeAcctID" value="" >
	   <input type="hidden" name="interestPayeeAcctID" value="" >
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
    	frm.sNotifyDepositNo.value = -1;
    	frm.depositNo.value = "";
    	frm.tsNotifyDepositStart.value = "";
    	frm.nDisNoticeDay.value = -1;
    	frm.dDepositAmount.value = "";
    	frm.dDepositBalance.value = "";
    	$.ajax(
    		{
    			type:'post',
    			url:'<%=strContext%>/capital/fixed/returnOpendate.jsp',
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
	
	function getNextField()
	{
              //>>>>add by shiny 20030403
      	      iRemitType = frm.nRemitType.value;
			  if (iRemitType == -1)
			  {//没有选择
			  	  alert("请选择汇款方式");
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
	function change()
	{
		frm.nNoticeDay.value = frm.nDisNoticeDay.value;
	}
	
	function getNextField1()
	{
              //>>>>add by shiny 20030403
      	      iInterestRemitType = frm.nInterestRemitType.value;
			  if (iInterestRemitType == -1)
			  {//没有选择
			  	  alert("请选择汇款方式");
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

    /* 修改保存处理函数 */
    function addme()
    {
        checkValues();
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";

		if (validate() == true)
        {
			change();
			

		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new NotifyDepositDrawOperator();
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
 
			frm.action = "n013-c.jsp?operate=submit&sign=<%=sign%>";
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
    }
    
    function doSaveAndApprovalInit()
    {
    	checkValues();
		frm.action = "n013-c.jsp?operate=saveAndApproval&sign=<%=sign%>";
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";

		if (validate() == true)
        {
			change();

		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new NotifyDepositDrawOperator();
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
    
    /* 取消处理函数 */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			//if (confirm("确定重置吗？"))
			//{
				frm.action="n011-c.jsp";
				showSending();	
				frm.submit();
			//}
		}
		else
		{
			//if (confirm("确定重置吗？"))
			//{
        		history.go(-1);
			//}
		}
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
			alert("通知账户名称不能为空");
			frm.sPayerName.focus();
			return false;
		}
		if (frm.saccountno1.value == "" || frm.saccountno1.value == -1)
		{
			alert("通知存款账号不能为空");
			frm.saccountno1.focus();
			return false;
		}
		//>>>>add by fxzhang 2006-12-28	
		if (frm.depositNo.value == "")
		{
			alert("请选择存款单据号");
			frm.depositNo.focus();
			return false;
		}
		<%
		if(financeInfo.getID()==-1){
		%>	
			else if(frm.sNotifyDepositNo.value == -1)
			{
				alert("存款单据号,请从放大镜中选择" );
				frm.depositNo.focus();
				return false;
			}		
		<%
		}
		else{
		%>
			else if(frm.depositNo.value !="<%=financeInfo.getDepositNo()%>")
			{
				alert("存款单据号,请从放大镜中选择" );
				frm.depositNo.focus();
				return false;
			}
		<%
		}
		%>
		/*汇款方式*/
		if(frm.nRemitType.value == -1)
		{
			alert("请选择汇款方式");
			frm.nRemitType.focus();
			return false;
		}
		/*利息汇款方式*/
		if(frm.nInterestRemitType.value == -1)
		{
			alert("请利息选择汇款方式");
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
			
			if (frm.sPayeeNameZoomAcctCtrl.value == "")
			{
				alert("收款方名称或账号不能为空，请选择");
				frm.sPayeeNameZoomAcctCtrl.focus();
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
				alert("收款方名称不能为空");
				frm.sPayeeName.focus();
				return false;
			}
			if (frm.sPayeeAccountInternalCtrl.value == ""||frm.sPayeeBankAccountNOInternal.value<0)
			{
				alert("收款方账号，请从放大镜中选择");
				frm.sPayeeAccountInternalCtrl.focus();
				return false;
			}
			
		}
		
		/*利息汇款方式*/
		if(frm.nInterestRemitType.value == -1)
		{
			alert("请利息选择汇款方式");
			frm.nInterestRemitType.focus();
			return false;
		}
		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 汇款方式电汇
		{
			
			if (frm.sInterestPayeeNameZoomAcctCtrl.value == "")
			{
				alert("利息收款方名称或账号，请从放大镜中选择");
				frm.sInterestPayeeNameZoomAcctCtrl.focus();
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
				frm.sInterestPayeeName.focus();
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
		var dBalance = parseFloat(reverseFormatAmount(frm.dDepositBalance.value)) -
							parseFloat(reverseFormatAmount(frm.amount.value)) ;
		
		if (dBalance<0)
		{
			alert("支取金额必须小于存单余额");
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
</script>

<script language="javascript">	

	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frm)");
	firstFocus(frm.depositNo);
//	//setSubmitFunction("addme(frm)");
	setFormName("frm");
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
