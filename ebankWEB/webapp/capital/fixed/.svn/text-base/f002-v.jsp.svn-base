<%--
/*
 * 程序名称：f002-v.jsp
 * 功能说明：定期开立修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				   com.iss.itreasury.settlement.util.SETTConstant"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期开立]";
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
<%@ include file="/common/common.jsp" %>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;
	PayerOrPayeeInfo payeeInfo = null;
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
	String lIsSoft = "";
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();
	long lAbstractID = -1;
	String strReceiveInterestAccountClientName = "";
	long lReceiveInterestAccountID = -1;
	String strReceiveInterestAccountNo = "";
	long isautocontinue = -1;
	long rdoInterest = -1;	
	String strTemp = null;

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
		payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payeeInfo");
		
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
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.OPENFIXDEPOSIT);
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		strTemp = (String)request.getAttribute("rdoInterest");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			rdoInterest = Long.valueOf(strTemp).longValue();
		}
		
		
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
		/*获取账户起存金额信息*/
		accountID = (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID();
		//accinfo = accdel.findAccountByID(accountID);
		accinfo = dao.findByID(accountID);
		dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
		
							if(dMinSinglePayAmount > 0.0) {
						request.setAttribute("dMinSinglePayAmount",String.valueOf(dMinSinglePayAmount));	
					}
					if(accinfo.getAccountExtendInfo() == null) {
						lIsSoft = "0";	
					}
					else {
						if(accinfo.getAccountExtendInfo().getIsSoft() > 0) {
							lIsSoft=String.valueOf(accinfo.getAccountExtendInfo().getIsSoft());	
						}
					}
		
		
        /* 显示文件头 */
 %>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
 <%       
        if(request.getAttribute("delete")!=null && request.getAttribute("delete").equals("delete")){
%><script language="javascript">
	alert("删除成功");
</script>
<%       
        }
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>



<safety:security/>
<form method="post" name="frm">
	<safety:certInformation/>
	<input type="hidden" name="lClientID"	value="<%=sessionMng.m_lClientID %>"/> 
	<input type="hidden" name="lUserID"	value="<%=sessionMng.m_lUserID %>"/> 
	<input type="hidden" name="lCurrencyId"	value="<%=sessionMng.m_lCurrencyID %>"/> 
	<input type="hidden" name="InstructionID"	value="<%=financeInfo.getID()%>"/> 
	<input type="hidden" name="lOfficeId" value="<%=sessionMng.m_lOfficeID %>">
	<input type="hidden" name="dtmodify" value="<%=financeInfo!=null?financeInfo.getDtModify()+"":"" %>">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">定期开立</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">

      <tr>
        <td>
	        <table width="110" border="0" cellspacing="0" cellpadding="0">
			 <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 活期账户</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
	        </table>
        </td>
      </tr>
    </table>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
        <tr class="MsoNormal">
		  <td width="5" height="25" class="MsoNormal"></td>
      	  <td height="25" bclass="MsoNormal" width="145"><font color="red">&nbsp;&nbsp;</font>活期账户名称：</td>
          <td width="430" height="25"  class="MsoNormal">
            <input type="text" class="box" name="sPayerName" size="32" value="<%=(financeInfo.getID() == -1) ? NameRef.getClientNameByID(sessionMng.m_lClientID) : financeInfo.getPayerName() %>" readonly>
          </td>
          <td width="5" height="25"  class="MsoNormal"></td>
          <td width="*%" height="25" colspan="3" class="MsoNormal">&nbsp;</td>
        </tr>

		<!--a try for glass start-->
		<tr  class="MsoNormal">
		<td width="5" height="25"  class="MsoNormal"></td>
		<td width="145" height="25" align="left"><font color='#FF0000'>* </font>付款方账号：</td>
		<td width="430">
			<fs:dic id="sPayerAccountNoZoomCtrl" size="22" form="frm" title="付款方账号" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams='frm.sPayerAccountNoZoomCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyId.value,frm.InstructionID.value' value="<%=NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo()) %>" nextFocus="fixedDepositTime" width="500">
				<fs:columns> 
					<fs:column display="账号"  name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs:columns>
				<fs:pageElements>
					<fs:pageElement elName="sPayerAccountNoZoomCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="payerAcctID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="dPayerCurrBalance" name="dPayerCurrBalance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
					<fs:pageElement elName="dPayerUsableBalance" name="dPayerUsableBalance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
					<fs:pageElement elName="tsExecute" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="sName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="hiddenOpendate" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs:pageElements>							
			</fs:dic> 
			<input type="hidden" name="hiddenOpendate" value='<%=request.getParameter("oldtsExecute") %>'>
		</td>
		<%Timestamp opendate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strOpenDate = sdf.format(opendate); %>
        <td width="105" class="MsoNormal"></td>
		<td width="5" class="MsoNormal"></td>
		<td width="*%" colspan="2" height="25" class="MsoNormal">&nbsp;</td>
		</tr>
		
        <tr class="MsoNormal">
       		<td width="5" height="25"  class="MsoNormal"><input type="hidden" name="dtopendate" value="<%=strOpenDate %>"></td>
          	<td width="145" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>当前余额：</td>
          	<td width="430" height="25"  class="MsoNormal">
				<input type="text" class="box" name="dPayerCurrBalance" size="20" value="<%= sPayerCurrentBalance %>" style="text-align:right" readonly>
			</td>
		</tr>
		<tr class="MsoNormal">
			<td></td>
			<td width="145" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>可用余额：</td>
			<td width="430" height="25"  class="MsoNormal">
				<input type="text" class="box" name="dPayerUsableBalance" size="20" value="<%= sPayerUsableBalance %>" style="text-align:right" readonly>
				<input type="hidden" name="payerAcctID" size="16" value="<%= financeInfo.getPayerAcctID() %>" >
		  	</td>
          	<td width="5" colspan="4"  class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
                                 <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 定期账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
    <tr  class="MsoNormal"> 
      <td width="5" height="25"  class="MsoNormal"></td>
      <td height="25" width="145"  class="MsoNormal"> <span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账号：</span></td>
      <td width="593" height="25"  class="MsoNormal"> 
      <!--
        <input type="text" name="sPayeeAccountNo" size="20" value="<%= (financeInfo.getID() == -1) ? NameRef.getNoLineAccountNo(payeeInfo.getAccountNo()):NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo()) %>" readonly  class="box"> 
        <input type="hidden" name="payeeAcctID"  value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID() %>" > 
      -->
      <%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.FIXED,financeInfo.getPayeeAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
      	<input type="hidden" name="sPayeeAccountNo" size="20" value="<%=financeInfo!=null?financeInfo.getPayeeAcctNo():"" %>" class="box"> 
        <input type="hidden" name="payeeAcctID"  value="<%=financeInfo!=null?financeInfo.getPayeeAcctID():-1 %>" > 
      </td>
      <td width="5" height="25"  class="MsoNormal"></td>
      <td width="*%" height="25" class="MsoNormal">&nbsp;</td>
      
    </tr>
    <!-- 
    <tr  class="MsoNormal"> 
      <td width="5" height="25"></td>
      <td width="131" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期留存金额：</td>
      <td width="21"  class="MsoNormal"><div align="right"><%//= sessionMng.m_strCurrencySymbol %></div></td>
      <td height="25" class="MsoNormal">
       <script  language="JavaScript">
				createAmountCtrl("frm","mamOuntForTrans","<%//= financeInfo.getMamOuntForTrans() %>","tsExecute","sChineseAmount","<%//=sessionMng.m_lCurrencyID%>");
				frm.mamOuntForTrans.style.textAlign = "right";
	   </script> 
      </td>
    </tr>
    -->
    <tr  class="MsoNormal"> 
      <td width="5" height="25"  class="MsoNormal"></td>
      <td height="25" width="145" class="MsoNormal"> <p><span  class="MsoNormal"><font color="red">*&nbsp;</font>定期存款期限：</span></p></td>
      <td width="593" height="25"  class="MsoNormal"> 
        <%SETTHTML.showFixedDepositMonthListCtrl(out,"fixedDepositTime",SETTConstant.TransQueryType.FIXED,financeInfo.getFixedDepositTime(),false,"onfocus=\"nextfield='amount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
      		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
      
    </tr>
    
<%
if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
{ 
%>
			<TR >
			  <td width="5" height="25"  class="MsoNormal"></td>	
	          <TD height=20 width="145">&nbsp;&nbsp;是否自动续存：</TD>
	          <TD height=20 width="30%"><input <%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"checked":"" %> type="checkbox" name="isautocontinue"  value="1"  onclick="changeContinueType(this)" /></TD>					
			  <TD height="20" ></TD>
			  <TD height="20" ></TD>
			</TR>
			<TR  style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr1">
			  <td width="5" height="25"  class="MsoNormal"></td>
	          <TD height=20 width="145"><INPUT <%=rdoInterest!=2?"checked":"" %>  name=rdoInterest type=radio onfocus="nextfield ='';" value="1"/>本利续存</TD>
	          <TD height=20 width="30%"></TD>					
			  <TD height="20" ></TD>
			  <TD height="20" ></TD>
			</TR>
    <tr  class="MsoNormal" style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr2">
	    <td width="5" height="25"  class="MsoNormal"></td> 
	    <%	String sDepositInterestToAccountNO = "";
		if(financeInfo.getAutocontinueaccountid() > 0 && rdoInterest==2)
		{
		      sDepositInterestToAccountNO = NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid());
		} %>
	    <td width="145" height="25" align="left"><INPUT  name=rdoInterest type=radio onfocus="nextfield ='';" value="2"/>利息转至活期账号：</td>
		<td >
			<fs:dic id="lInterestToAccountIDCtrl" size="18" form="frm" title="利息转至活期账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyId.value,frm.lInterestToAccountIDCtrl.value,frm.strReceiveInterestAccountClientName.value' value="<%=sDepositInterestToAccountNO%>" nextFocus="amount" width="500">
				<fs:columns> 
					<fs:column display="收款方账号"  name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
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
        <%/*
		//收款方账号放大镜
		String sDepositInterestToAccountNO = "";
		if(financeInfo.getAutocontinueaccountid() > 0 && rdoInterest==2)
		{
		      sDepositInterestToAccountNO = NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid());
		}
	   	OBMagnifier.createInterPayeeBankNOCtrl(
	   		out,
	   		sessionMng.m_lCurrencyID,
	   		sessionMng.m_lClientID,
	   		"lInterestToAccountID",
	   		"strReceiveInterestAccountClientName",
	   		"frm",
	   		sDepositInterestToAccountNO,
	   		"lInterestToAccountID",
	   		"<INPUT "+(rdoInterest==2?"checked":"")+" name=rdoInterest type=radio onfocus=\"nextfield ='';\" value=\"2\"/>利息转至活期账号",
	   		" style=\"width:20%\" height=\"25\" class=\"MsoNormal\"",
	   		" width=\"190\" height=\"25\" ");*/
		%>
		<td width="35%">
			户名：<input disabled class="box" type="text" name="strReceiveInterestAccountClientName" value="<%=(financeInfo.getAutocontinueaccountid()>0&&rdoInterest==2)?NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid()):""%>">
		    <input type="hidden" name="lInterestToAccountID" value="<%=financeInfo.getAutocontinueaccountid()>0?financeInfo.getAutocontinueaccountid():-1 %>">    
		</td>	
		<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
    </tr>
<%}%>
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
        <tr  class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="145" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>金额：&nbsp;</td>
          <td height="25" width="350"  class="MsoNormal">
          	<fs:amount 
	       		form="frm"
       			name="amount"
       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount())) %>"
       			chineseCtrlName="sChineseAmount"
       			nextFocus="tsExecute"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td  height="25"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="5" height="25"  class="MsoNormal"></td>
          <td height="25"   class="MsoNormal" ><font color="red">&nbsp;&nbsp;</font>大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
          <td height="25"   class="MsoNormal">
			<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>		
		  </td>		  
          <td height="25"  class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25"   class="MsoNormal" ><font color="red">*&nbsp;</font>执行日：</td>
          <td height="25"  class="MsoNormal">
		 	<% OBConstant.Calendar.show(out,"tsExecute",(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate(),"nextfield ='sNoteCtrl'","20");%>
		  </td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
        <tr>
        	<td width="4" height="25">&nbsp;</td>
        	<td width="145" height="25" align="left"><font color="#FF0000">*</font> 汇款用途：</td>	
        	<td width="350" height="25">
				<fs:dic id="sNoteCtrl" form="frm" title="汇款用途" sqlFunction="getAbstractSettingSQL" sqlParams="frm.lOfficeId.value,frm.lUserID.value,frm.sNoteCtrl.value" nextFocus="add" width="500" value="<%=financeInfo.getNote() %>" type="textarea" row="2" col="55" needRemind="true" maxlength="30" properties="" position="top">
					<fs:columns>
						<fs:column display="摘要代号" name="AbstractCode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="摘要描述" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="sNote" name="AbstractID" type="<%=PagerTypeConstant.STRING %>" elType="hidden"/>
						<fs:pageElement elName="sNoteCtrl" name="AbstractDesc" type="<%=PagerTypeConstant.STRING %>" elType="text"/>
					</fs:pageElements>
				</fs:dic>
        	</td>	    
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

      </table>
    <br>
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
           		<% String transcode = financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=transcode%>' 
		        	caption = " 上 传 " 
		        	lid = '-1'
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       <%--     </td>
        </tr>
    </table>
    --%>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr>
          <td align="right" nowrap>
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 id="submitt" name="add" type=button value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'">
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>"/>						
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name=addreset type="reset" value=" 重 置 " >&nbsp;&nbsp; 

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
	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
		<input type="hidden" name="clickCount" value="<%=strClickCount%>">
	    <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
		<input type="hidden" name="dMinSinglePayAmount" value="<%=dMinSinglePayAmount%>">
		<input type="hidden" name="lIsSoft" value="<%=lIsSoft%>">
	  <input type="hidden" name="confirmUserID" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="timestamp" value="<%=System.nanoTime() %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>">
	  <input type="hidden" name="strAction" value="">
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 

	  
</form>
<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frm.sPayerAccountNoZoomCtrl);
	////setSubmitFunction("Javascript:addme();");
	setFormName("frm");
</script>

<!--presentation end-->

<script language="Javascript">

var remark = document.getElementById("remark");
<%--
if(document.getElementsByName("isContinut")[0].checked){
	document.getElementById("insert").innerHTML="<font color=\"red\">*&nbsp;</font>";
}else{
	document.getElementById("insert").innerHTML="<font color=\"red\">&nbsp;&nbsp;</font>";
}
--%>	
	function onClickRed(obj)
	{
		if(obj.checked){
			document.getElementById("insert").innerHTML="<font color=\"red\">*&nbsp;</font>";
		}else{
			document.getElementById("insert").innerHTML="<font color=\"red\">&nbsp;&nbsp;</font>";
			remark.value = "";
		}
	}
	
	
    /* 修改保存处理函数 */
    function addme()
    {
        //var submitt = document.getElementById("submitt").value;
		var signatureValue;
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		if (validate() == true)
        {
			//网银签名
		    if(security.isSign())
		    {
		    	var format = new OpenFixDepositOperator();
		    	sign(format,frm);
		    }
			
			//确认保存 
			if (!confirm("是否保存？"))
			{
				return false;
			}
			showSending();
			frm.action = "f003-c.jsp?operate=submitt&sign=<%=sign%>"+"&oldtsExecute="+frm.hiddenOpendate.value;
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
    }
    /*保存并提交审批*/
    function doSaveAndApprovalInit()
	{
		frm.action = "f003-c.jsp?operate=saveAndApproval&sign=<%=sign%>"+"&oldtsExecute="+frm.hiddenOpendate.value;
		var signatureValue;
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
						
		if(validate() == true){

			//网银签名
		    if(security.isSign())
		    {
		    	var format = new OpenFixDepositOperator();
		    	sign(format,frm);
		    }		
		
			//确认保存并提交审批
			if (!confirm("是否保存并提交审批？"))
			{
				return false;
			}
			
			showSending();

			document.frm.submit();
        }
	}
    
    /* 取消处理函数 */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			frm.action="";
			frm.submit();
		}
		else
		{
        	history.go(-1);
		}
    }

    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */

		/* 付款方资料非空校验 */
		if (frm.sPayerName.value == "")
		{
			alert("付款方名称不能为空，请录入");
			frm.sPayerName.focus();
			return false;
		}
		
		if (frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("付款方账号，请从放大镜中选择");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

		{ %>
		if (frm.lInterestToAccountIDCtrl.value == "" )
		{
		var temp =-1;
			var radios = document.getElementsByName("rdoInterest");
			for ( var i = 0; i < radios.length; i++) {
				 if (radios[i].checked==true) {
				 temp= radios[i].value;
				   	break;
				 }
			}
			if( temp == 2 )
			{
				alert("利息转至活期账号不能为空，请从放大镜中选择");
				frm.lInterestToAccountIDCtrl.focus();
				return false;
			}
		}
		<%}%>
		
		/* 收款方资料非空校验 */
		if (frm.sPayeeAccountNo.value == "" || frm.payeeAcctID.value == -1)
       {
             alert("定期账号不存在，不能做此项交易");
			 frm.saccountno1.focus();
             return false;
       }
		
		if (frm.fixedDepositTime.value == "" || frm.fixedDepositTime.value<0)
       {
             alert("定期存款期限不能为空，请选择");
			 frm.fixedDepositTime.focus();
             return false;
       }
		//if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.amount.value))
		//{
			//alert("金额小于该账户定期开立起存金额，请重新输入");
			//document.frm.amount.focus();
			//return false;
		//}
		
		if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.amount.value))
			{
				alert("存款金额小于存款起点金额，起点金额为：<%=sessionMng.m_strCurrencySymbol%>" + formatAmount(document.frm.dMinSinglePayAmount.value));
				//alert(document.frm.lIsSoft.value);
				if(parseInt(document.frm.lIsSoft.value) == 1) {
					return false;
				}
			}
		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frm.amount, 1, "交易金额"))
		{
			return false;
		}

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

		/* 业务校验 */
		/* 可用余额－交易金额 */
		var dBalance = parseFloat(reverseFormatAmount(frm.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frm.amount.value)) ;
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入交易金额");
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
		if (frm.sNoteCtrl.value == "")
		{
			alert("汇款用途不能为空，请从放大镜中选择");
			frm.sNoteCtrl.focus();
			return false;
		}
		/* BUG #13770 END */
    	return true;
    }
    
    //add by dwj 20111107
    function saccountnochange()
    {
    	var a = frm.saccountno1.options[frm.saccountno1.selectedIndex].text;
    	frm.sPayeeAccountNo.value = a;
    	frm.payeeAcctID.value = frm.saccountno1.value;
    }
    //end add by dwj 
    
		function changeContinueType(obj)
		{
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
</script>

<script language="javascript">

 <%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
{ %>
 	changeContinueType(frm.isautocontinue);
 <%}%>
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
