<%--
/*
 * 程序名称：ck004-v.jsp
 * 功能说明：定期支取修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
--%>


<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	  OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long lTransType = -1;
	String strContext = request.getContextPath();
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
		if(request.getAttribute("financeInfo") != null)
		{
			financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		}
		if(request.getAttribute("payerOrPayeeInfo") != null)
		{
			payerInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo");
			Log.print("----------------payerid"+payerInfo.getAccountID());
		}
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
		
        /* 显示文件头 */
      long lTypeID = 21;
		long lDepositTypeIDFixedDepositNo = 1;
		long lAccountID = (financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID();
		String strAccountIDCtrlFixedDepositNo = "saccountno1";		
		String[] strNextControlsFixedDepositNo = {"nRemitType"};
		String strRtnEndDateCtrlFixedDepositNo= "";
		String strRtnOpenDateCtrlFixedDepositNo = "";
		String strRtnCapitalCtrlFixedDepositNo = "dDepositAmount";
		String strRtnBalanceCtrlFixedDepositNo = "";
		String strRtnRateCtrl = "dDepositRate";
		String strRtnIntervalCtrl = "nFixedDepositTime";
		String strRtnStartDateCtrl = "tsFixedDepositStart";
		String strFirstTD = " width=\"130\" height=\"25\" class=\"MsoNormal\" nowrap ";
		String strSecondTD = " width=\"590\" height=\"25\"  ";
		boolean isCreateNewBook = Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK, true);
		String sysdate= Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<form method="post" name="frm">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span nowrap class="txt_til2"  style="width:140px"><%=(lTransType==OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER)?"定期支取复核":"通知存款支取复核"%></span></td>
			       <td class=title_right ><a class=img_title></td>
			       <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>"> 
				   <input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>"> 
				   <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>"> 
				   <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>"> 
				   <input type="hidden" name="InstructionID" value="<%=lAccountID%>"> 
				   <input type="hidden" name="sysdate" value="<%=sysdate%>">
				   <input type="hidden" name="sFixedDepositNo" value="<%=-1 %>">
				   <input type="hidden" name="sFixedDepositNoAccountID" value="<%= String.valueOf(lAccountID) %>">
				</TR>
			</TABLE>
   
<br/>
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal" align="">
        <tr  class="MsoNormal">
          <td colspan="4" height="25" ></td>
          <td height="25" class="MsoNormal"></td>
        </tr>
		<!--a try for glass start-->
<%
		if(lTransType == OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER)
		{
%>	
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="20%" height="25" class="MsoNormal" nowrap ><font color="red">*</font>&nbsp;定期账号</td>
			<td width="590" height="25">
					<%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.FIXED,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
			</td>
			<td width="*%" height="25" colspan="2"  class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td height="25"></td>
<%
		//定期存款单据号
		
	/*		long lTypeID = 21;
		long lDepositTypeIDFixedDepositNo = 1;
		long lAccountID = (financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID();
		String strAccountIDCtrlFixedDepositNo = "saccountno1";		
		String[] strNextControlsFixedDepositNo = {"nRemitType"};
		String strRtnEndDateCtrlFixedDepositNo= "";
		String strRtnOpenDateCtrlFixedDepositNo = "";
		String strRtnCapitalCtrlFixedDepositNo = "dDepositAmount";
		String strRtnBalanceCtrlFixedDepositNo = "";
		String strRtnRateCtrl = "dDepositRate";
		String strRtnIntervalCtrl = "nFixedDepositTime";
		String strRtnStartDateCtrl = "tsFixedDepositStart";
		String strFirstTD = " width=\"130\" height=\"25\" class=\"MsoNormal\" nowrap ";
		String strSecondTD = " width=\"590\" height=\"25\"  ";
	OBMagnifier.createFixedDepositNoCtrl(out,
			sessionMng.m_lOfficeID,
			sessionMng.m_lCurrencyID,
			"frm",
			"sFixedDepositNo",
			"<font color='red'>* </font>定期存款单据号",
			sessionMng.m_lUserID,
			lAccountID,-1,
			financeInfo.getDepositNo(),
			lDepositTypeIDFixedDepositNo,
			lTypeID,
			strAccountIDCtrlFixedDepositNo,
			strFirstTD,
			strSecondTD,
			strNextControlsFixedDepositNo,
			strRtnEndDateCtrlFixedDepositNo,
			strRtnOpenDateCtrlFixedDepositNo,
			strRtnCapitalCtrlFixedDepositNo,
			strRtnBalanceCtrlFixedDepositNo,
			strRtnRateCtrl,
			strRtnIntervalCtrl,
			strRtnStartDateCtrl);	*/
			
%>		
			
		<td height="25" align="left"><font color='red'>* </font>定期存款单据号：</td>
		<td >
		<%if(isCreateNewBook) {%>
			<fs_c:dic id="sFixedDepositNoCtrl" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL_CREATE"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.saccountno1.value,frm.lUserID.value,frm.sFixedDepositNoCtrl.value,21,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>"  nextFocus="nRemitType" width="650">
				<fs_c:columns> 
					<fs_c:column display="存款单据号"  name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sFixedDepositNoCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="nFixedDepositTime" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="tsFixedDepositStart" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
			<%}else{ %>
			<fs_c:dic id="sFixedDepositNoCtrl" size="22" form="frm" title="定期存款单据号" sqlFunction="getFixedDepositNoSQL"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,1,frm.saccountno1.value,frm.lUserID.value,frm.sFixedDepositNoCtrl.value,21,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>"  nextFocus="nRemitType" width="650">
				<fs_c:columns> 
					<fs_c:column display="存款单据号"  name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="到期日" name="EndDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sFixedDepositNoCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dDepositAmount" name="Capital" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dDepositRate" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="nFixedDepositTime" name="StartDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="tsFixedDepositStart" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
			<%} %>
			<td height="25" class="MsoNormal"></td>
			<td ></td>
		</tr>  

<%
		}
		else if (lTransType == OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW)
		{
%>
		<tr class="MsoNormal">
			<td width="5"  height="25"></td>
			<td width="20%" height="25" class="MsoNormal" nowrap ><font color="red">*</font>&nbsp;通知账号</td>
			<td width="590" height="25">
	  			<%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.NOTIFY,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
			</td>
			<td ></td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<tr  class="MsoNormal">
		<td height="25"></td>
<%
		//通知存款单据号
	/*
		long lTypeID = 21;
		long lDepositTypeID = 2;
		long lAccountID = (financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID();
		String strAccountIDCtrlFixedDepositNo = "saccountno1";		
		String[] strNextControlsFixedDepositNo = {"nRemitType"};
		String strRtnEndDateCtrlFixedDepositNo= "";
		String strRtnOpenDateCtrlFixedDepositNo = "";
		String strRtnCapitalCtrlFixedDepositNo = "dDepositAmount";
		String strRtnBalanceCtrlFixedDepositNo = "dDepositBalance";
		String strRtnRateCtrl = "";
		String strRtnIntervalCtrl = "nNoticeDay";
		String strRtnStartDateCtrl = "tsFixedDepositStart";
		String strFirstTD = " width=\"130\" height=\"25\" class=\"MsoNormal\" nowrap ";
		String strSecondTD = " width=\"590\" height=\"25\" ";		
OBMagnifier.createFixedDepositNoCtrl(out,
			sessionMng.m_lOfficeID,
			sessionMng.m_lCurrencyID,
			"frm",
			"sFixedDepositNo",
			"<font color='red'>* </font>通知存款单据号",
			sessionMng.m_lUserID,
			lAccountID,
			-1,
			financeInfo.getDepositNo(),
			lDepositTypeID,
			lTypeID,
			strAccountIDCtrlFixedDepositNo,
			strFirstTD,
			strSecondTD,
			strNextControlsFixedDepositNo,
			strRtnEndDateCtrlFixedDepositNo,
			strRtnOpenDateCtrlFixedDepositNo,
			strRtnCapitalCtrlFixedDepositNo,
			strRtnBalanceCtrlFixedDepositNo,
			strRtnRateCtrl,
			strRtnIntervalCtrl,
			strRtnStartDateCtrl);	*/
%>				
		
		<td height="25" align="left"><font color='red'>* </font>通知存款单据号：
		<input type="hidden" name="sFixedDepositNo" value="<%=-1 %>">
		<input type="hidden" name="sFixedDepositNoAccountID" value="<%=String.valueOf(lAccountID) %>"></td>
		<td >
			<fs_c:dic id="sFixedDepositNoCtrl" size="22" form="frm" title="通知存款单据号" sqlFunction="getFixedDepositNoSQL"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,2,frm.saccountno1.value,frm.lUserID.value,frm.sFixedDepositNoCtrl.value,21,frm.sysdate.value' value="<%=financeInfo.getDepositNo() %>"  nextFocus="nRemitType" width="650">
				<fs_c:columns> 
					<fs_c:column display="存款单据号"  name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="开户日" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sFixedDepositNoCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="sFixedDepositNo" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="sFixedDepositNoAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
		</td>
      <td ></td>
      <td height="25" colspan="1"  class="MsoNormal"></td>
	</tr>

<%
		}
%>	
<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td></tr>
</table>
	<br/>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr class="MsoNormal">
          <td width="49%">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal" >
			<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td></tr>
        		<tr class="MsoNormal">
          			<td width="5" height="25"></td>
          			<td width="40%" height="25"><p><span  class="MsoNormal">&nbsp;&nbsp;本金：</span></p></td>
          			<td width="240" height="25"></td>  			
          			<td width="*%" height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>

        		<tr  class="MsoNormal">
          			<td height="25"></td>
          			<td height="25"><p><span class="graytext">&nbsp;&nbsp;汇款方式：</span></p></td>
          			<td height="25">
					<input type="hidden" name="nRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType() %>">
<%
					OBHtmlCom.showRemitTypeListControl1(out,"nRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly");
%>
					<input type="hidden" name="sPayeeNameZoomAcctCtrl" value="<%= financeInfo.getPayeeName() %>" onfocus="nextfield ='sPayeeProv';">					
					</td>  			
					<td height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>

        		<tr id="payeeAcct"  class="MsoNormal">
          			<td height="25"></td>
          		
<%
					//收款方账号放大镜（内部转账）
					//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","sPayeeName","frm",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='red'>* </font>收款方账号"," width=\"120\" height=\"25\"  class=\"MsoNormal\""," width=\"240\" height=\"25\" ");	
					//modify by xwhe
			/*		OBMagnifier.createPayerInterAccountNoCtrl(out,
							sessionMng.m_lUserID,
							sessionMng.m_lCurrencyID,
							financeInfo.getID(),
							sessionMng.m_lClientID,
							"nPayeeAccountID",
							"",
							"sPayeeName",
							"frm",
							financeInfo.getPayeeAcctNo(),
							"sPayeeAccountInternal",
							"<font color='red'>* </font>收款方账号",
							" width=\"120\" height=\"25\" nowrap class=\"MsoNormal\"",
							" width=\"240\" height=\"25\" ");	*/
					%>	              
					<td height="25" align="left"><font color='#FF0000'>* </font>收款方账号：</td>
					<td>
						<fs_c:dic id="sPayeeAccountInternalCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=financeInfo.getPayeeAcctNo()%>" nextFocus="nRemitType" width="450">
							<fs_c:columns> 
								<fs_c:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs_c:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs_c:columns>
							<fs_c:pageElements>
								<fs_c:pageElement elName="sPayeeAccountInternalCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="nPayeeAccountID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sPayeeName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sPayeeBankAccountNOInternal" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs_c:pageElements>							
						</fs_c:dic> 
					</td>		
					<td height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>

        		<tr id="payeeBankNo" class="MsoNormal">
          			<td height="25"></td>
<%
				//收款方账号放大镜（汇）
				/*OBMagnifier.createPayeeAccountNOCtrl(out,
						sessionMng.m_lCurrencyID,
						sessionMng.m_lClientID,
						"nPayeeAccountID",
						"sPayeeNameZoomAcctCtrl",
						"sPayeeProv",
						"sPayeeCity",
						"sPayeeBankName",
						"frm",
						financeInfo.getPayeeAcctNo(),
						"sPayeeAcctNoZoom",
						"<font color='red'>* </font>收款方账号",
						" width=\"120\" height=\"25\" nowrap class=\"MsoNormal\"",
						" width=\"240\" height=\"25\" ",
						false);	*/
%>				
				<td height="25" align="left"><font color='#FF0000'>* </font>收款方账号：</td>
				<td >
					<fs_c:dic id="sPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sPayeeAcctNoZoomCtrl.value,frm.sPayeeNameZoomAcctCtrl.value' value="<%=financeInfo.getPayeeAcctNo()%>" nextFocus="dAmount" width="450">
						<fs_c:columns> 
							<fs_c:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							<fs_c:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						</fs_c:columns>
						<fs_c:pageElements>
							<fs_c:pageElement elName="sPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="nPayeeAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="sPayeeNameZoomAcctCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="sPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="sPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="sPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
							<fs_c:pageElement elName="sPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						</fs_c:pageElements>							
					</fs_c:dic> 
				</td>
				<td colspan=2></td>
       		</tr>
			<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td></tr>
		</table>  
		  </td>
		  <td>&nbsp;</td>
		  <td width="49%">
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="normal" align="center">
			<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td></tr>
        		<tr  class="MsoNormal">
          			<td width="5" height="25"></td>
          			<td width="40%" height="25"><p><span  class="MsoNormal">&nbsp;&nbsp;利息：</span></p></td>
          			<td width="240" height="25"></td>  			
          			<td height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>

        		<tr  class="MsoNormal">
          			<td height="25"></td>
          			<td height="25"><p><span  class="MsoNormal">&nbsp;&nbsp;汇款方式：</span></p></td>
          			<td height="25">
					<input type="hidden" name="nInterestRemitTypeHidden" value="<%= (financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType() %>">
<%
					OBHtmlCom.showRemitTypeListControl1(out,"nInterestRemitType",(financeInfo.getID()==-1)?OBConstant.SettRemitType.INTERNALVIREMENT:financeInfo.getInterestRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly");
%>
					<input type="hidden" name="sInterestPayeeNameZoomAcctCtrl" value="<%= financeInfo.getPayeeName() %>" onfocus="nextfield ='sInterestPayeeProv';">					
					</td>  			
					<td height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>

        		<tr id="InterestPayeeAcct" class="MsoNormal">
          			<td height="25"></td>
<%
					//利息收款方账号放大镜（内部转账）
					//OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nInterestPayeeAccountID","","sPayeeName","frm",financeInfo.getInterestPayeeAcctNo(),"sInterestPayeeAccountInternal","<font color='red'>* </font>收款方账号"," width=\"120\" height=\"25\"  class=\"MsoNormal\""," width=\"240\" height=\"25\" ");	

                    //OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayeeAccountID","","sPayeeName","frm",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","<font color='red'>* </font>收款方账号"," width=\"120\" height=\"25\"  class=\"MsoNormal\""," width=\"240\" height=\"25\" ");	
                   //modify by xwhe
          /*       /*   OBMagnifier.createPayerInterAccountNoCtrl(out,
                    		sessionMng.m_lUserID,
                    		sessionMng.m_lCurrencyID,
                    		financeInfo.getID(),
                    		sessionMng.m_lClientID,
                    		"nInterestPayeeAccountID",
                    		"",
                    		"sPayeeName",
                    		"frm",
                    		financeInfo.getInterestPayeeAcctNo(),
                    		"sInterestPayeeAccountInternal",
                    		"<font color='red'>* </font>收款方账号",
                    		" width=\"120\" height=\"25\" nowrap class=\"MsoNormal\"",
                    		" width=\"240\" height=\"25\" ");	 */

%>	  			
					<td height="25" align="left"><font color='#FF0000'>* </font>收款方账号：</td>
					<td >
						<fs_c:dic id="sInterestPayeeAccountInternalCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeAccountNoSQL"  sqlParams='frm.sInterestPayeeAccountInternalCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.lInstructionID.value' value="<%=financeInfo.getInterestPayeeAcctNo()%>" nextFocus="nRemitType" width="450">
							<fs_c:columns> 
								<fs_c:column display="账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs_c:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs_c:columns>
							<fs_c:pageElements>
								<fs_c:pageElement elName="sInterestPayeeAccountInternalCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="nInterestPayeeAccountID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sPayeeName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sPayeeBankAccountNOInternal" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs_c:pageElements>							
						</fs_c:dic> 
					</td>
					<td height="25" colspan="2"  class="MsoNormal"></td>
        		</tr>
				<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td></tr>
        		<tr id="InterestPayeeBankNo"  class="MsoNormal">
          			<td height="25"></td>
<%
				//收款方账号放大镜（汇）
		/*		OBMagnifier.createPayeeAccountNOCtrl(out,
						sessionMng.m_lCurrencyID,
						sessionMng.m_lClientID,
						"nInterestPayeeAccountID",
						"sInterestPayeeNameZoomAcctCtrl",
						"sInterestPayeeProv",
						"sInterestPayeeCity",
						"sInterestPayeeBankName",
						"frm",
						financeInfo.getInterestPayeeAcctNo(),
						"sInterestPayeeAcctNoZoom",
						"<font color='red'>* </font>收款方账号",
						" width=\"120\" height=\"25\" nowrap class=\"MsoNormal\"",
						" width=\"240\" height=\"25\" ",false);	*/
%>			
					<td height="25" align="left"><font color='#FF0000'>* </font>收款方账号：</td>
					<td >
						<fs_c:dic id="sInterestPayeeAcctNoZoomCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getPayeeAccountNOSQL"  sqlParams='false,frm.lClientID.value,frm.lCurrencyID.value,frm.sInterestPayeeAcctNoZoomCtrl.value,frm.sInterestPayeeNameZoomAcctCtrl.value' value="<%=financeInfo.getInterestPayeeAcctNo()%>" nextFocus="dAmount" width="450">
							<fs_c:columns> 
								<fs_c:column display="收款方账号" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs_c:column display="收款方名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs_c:columns>
							<fs_c:pageElements>
								<fs_c:pageElement elName="sInterestPayeeAcctNoZoomCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="nInterestPayeeAccountID2" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sInterestPayeeNameZoomAcctCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sInterestPayeeProv" name="SPAYEEPROV" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sInterestPayeeCity" name="SPAYEECITY" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sInterestPayeeBankName" name="sPayeeBankName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sInterestPayeeAcctNoZoomhiddenValue" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs_c:pageElements>							
						</fs_c:dic> 
					</td>
        		</tr>
			</table>  
		  </td>
        </tr>
      </table>	
      <br/>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="normal" align=""> 
		<tr class="MsoNormal" ><td height="15" colspan ="5" >&nbsp;</td><td height="25" colspan="2"  class="MsoNormal"></td></tr>
	  
        <tr  class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="20%" height="25"  class="MsoNormal">&nbsp;&nbsp;支取金额：</td>
          <td height="25" class="MsoNormal">
            <fs:amount 
	       		form="frm"
       			name="dAmount"
       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount()))%>"
       			nextFocus="tsExecute"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td height="25"></td>
          <td height="25" colspan="2"  class="MsoNormal"></td>
        </tr>

        <tr  class="MsoNormal">
          <td height="25"></td>
          <td height="25"  class="MsoNormal" >&nbsp;&nbsp;执行日：</td>
          <td height="25"  class="MsoNormal">
          	<fs_c:calendar 
			          	    name="tsExecute"
				          	value="" 
				          	properties="nextfield =''" 
				          	size="20"/>
			    <script>
	          		$('#tsExecute').val('<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate() %>');
	          	</script>
		  </td>
          <td height="25"></td>
        </tr>
      
        <tr>
          <td colspan = "4">
            <div align="right"></div>
          </td>
          <td ></td>
          <td >
            <div align="right">
			</div>
          </td>
          <td height="25" colspan="1"  class="MsoNormal"></td>
        </tr>
      </table>
	  <table width="100%">
   	<tr><td height="10"></td><td></td></tr>
   		 <tr class="MsoNormal">
          <td class="MsoNormal" colspan="3">
            <div align="right" ></div>
          </td>          
          <td width="162" align="right" colspan="2">
			<input type="button" name="Submitv00204" value=" 匹 配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
          </td>
        </tr>
   </table>
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >
	  <input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >
	  <input type="hidden" name="nPayerAccountID" value="" >
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="SelectType" value="<%= lTransType %>">
	  <input type="hidden" name="nSubAccountID" value="<%=  financeInfo.getSubAccountID() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">
	  <input type="hidden" name="hiddenOpendate" value="">
 	</td>
  </tr>
</table>
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

	/* 实现功能：动态显示根据汇款方式确定的收款方资料录入表单
	 * 实现方法：通过对TR中的ID属性控制实现
	 * 命名规则：不带放大镜的按一般命名规则(xxx)
	 * 			 通过放大镜实现的以Zoom结束(xxxZoom)
	 *			 同一放大镜对不同对象影响时通过加相应后缀区分(xxxZoomxxx)
	 */

	/* 收款方名称 */
	function jump()
	{	
		iRemitType = frm.nRemitType.value;
		iInterestRemitType = frm.nInterestRemitType.value;
		/* 本金 */

		payeeAcct.style.display = "none";
		

		payeeBankNo.style.display = "none";
		
		/* 利息 */

		InterestPayeeAcct.style.display = "none";
	

		InterestPayeeBankNo.style.display = "none";

		/* 根据汇款方式确定所显示的TR */
		/*本金*/
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  // 汇款方式银行付款		
		{

			payeeBankNo.style.display = "";			
		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{

			payeeAcct.style.display = "";
		}
		
		/*利息*/
		if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  // 汇款方式银行付款		
		{

			InterestPayeeBankNo.style.display = "";			
		}

		if(iInterestRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{

			InterestPayeeAcct.style.display = "";
		}
	}
	
	function goback(){
		location.href='ck001-v.jsp';
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

    /* 修改提交处理函数 */
    function matchme()
    {
        
		frm.action = "ck007-c.jsp";
		if (validate() == true)
        {
			/* 确认提交 */
<%--			if (!confirm("是否匹配？"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
			frm.nInterestRemitTypeHidden.value = frm.nInterestRemitType.value;
			showSending();
            frm.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			if (confirm("是否取消？"))
			{
				frm.action="";
				frm.submit();
			}
		}
		else
		{
			if (confirm("是否取消？"))
			{
        		history.go(-1);
			}
		}
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		/**/
		if(frm.saccountno1.value<=0)
		{
			alert("请选择定期帐号");
			frm.saccountno1.focus();
			return false;
		}
		if (frm.sFixedDepositNoCtrl.value == "")
		{
			alert("存款子账号不能为空，请从放大镜中选择");
			frm.sFixedDepositNoCtrl.focus();
			return false;
		}	
		// add by fxzhang 2006-12-28
		else if(frm.sFixedDepositNo.value == -1)
		{
			alert("存款子账号不能为空，请从放大镜中选择" );
			frm.sFixedDepositNoCtrl.focus();
			return false;
		}	

		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 汇款方式电汇
		{
			if (frm.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("收款方账号不能为空，请选择");
				frm.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{
			if (frm.sPayeeAccountInternalCtrl.value == "")
			{
				alert("收款方账号不能为空，请选择");
				frm.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		}
		
		if(iInterestRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)   // 汇款方式电汇
		{
			if (frm.sInterestPayeeAcctNoZoomCtrl.value == "")
			{
				alert("收款方账号不能为空，请选择");
				frm.sInterestPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iInterestRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{
			if (frm.sInterestPayeeAccountInternalCtrl.value == "")
			{
				alert("收款方账号不能为空，请选择");
				frm.sInterestPayeeAccountInternalCtrl.focus();
				return false;
			}

		}

		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frm.dAmount, 1, "交易金额"))
		{
			return false;
		}

		/* 执行日校验 */
		var tsExecute = frm.tsExecute.value;
		if(tsExecute=="")
		{
			alert("执行日不能为空，请录入");
			frm.tsExecute.focus();
			return false;
		}
		if(chkdate(tsExecute) == 0)
		{
			alert("执行日格式不正确，请重新录入");
			frm.tsExecute.focus();
			return false;
		}
		
    	return true;
    }
    
    function saccountnochange()
    {
    	frm.sFixedDepositNo.value = -1;
    	frm.sFixedDepositNoCtrl.value = "";
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
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frm)");
	//firstFocus(frm.sFixedDepositNo);
	//setSubmitFunction("matchme(frm)");
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
<%@ include file="/common/SignValidate.inc" %>