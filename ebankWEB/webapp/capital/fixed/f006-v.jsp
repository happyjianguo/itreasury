<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
	               com.iss.itreasury.settlement.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				   com.iss.itreasury.settlement.util.SETTConstant,
				    java.text.SimpleDateFormat"
%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[到期续存]";
%>

<%
com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
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
	String dPayerStartDate = "";
	String nFixedDepositTime = "";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
	long isautocontinue = -1;
	long rdoInterest = -1;	
	String strTemp = null;
	String lSubaccountid="";
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();
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
		String dPayerEndDate="";
		String dPayerEndDate1="";
		if(request.getAttribute("dPayerEndDate")!=null)
		{
			dPayerEndDate1 =(String) request.getAttribute("dPayerEndDate");   //定期存款结束日
		}
		
		String dPayerEndDate2="";
		if(request.getAttribute("endDate")!=null)
		{
			dPayerEndDate2 =(String) request.getAttribute("endDate");   //定期存款结束日
		}
		strTemp = (String)request.getAttribute("lSubAccount");
		System.out.print(strTemp);
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSubaccountid = strTemp;
		}
		
		dPayerEndDate = dPayerEndDate1.equals("")?dPayerEndDate2:dPayerEndDate1;
		
		financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
		payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payeeInfo");
		        //信息重新从数据库读取，主要用于更新dtmodify字段   add by zhanglei  2010.06.01
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo=financeInstr.findByID(financeInfo.getID(), sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.DRIVEFIXDEPOSIT);
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		strTemp = (String)request.getAttribute("rdoInterest");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			rdoInterest = Long.valueOf(strTemp).longValue();
		}
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
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
		
		if(financeInfo.getOfficeID()>0&&financeInfo.getCurrencyID()>0)
		{
			
			opendate = Env.getSystemDate(financeInfo.getOfficeID(),financeInfo.getCurrencyID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			strOpenDate = sdf.format(opendate);
			
		}		

		/*获取账户起存金额信息*/
		//accountID = (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID();
		//accinfo = accdel.findAccountByID(accountID);
		//accinfo = dao.findByID(accountID);
		//dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
		
        /* 显示文件头 */
       
%>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
<safety:security/>
<form method="post" name="frm">
<safety:certInformation/>
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
<input type="hidden" name="endDate" value="">
<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">
<input type="hidden" name="hiddenOpendate" value="<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>">
<input type="hidden" name="sPayerAccountNoZoom" value="<%=financeInfo.getSubAccountID()  %>" />
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">到期续存</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		 
		<br/>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">

	      <tr>
	        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
	      <!--     <tr>
	            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">定期账户资料</td>
	            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	          </tr> -->
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 定期账户资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>	          
	        </table></td>
	      </tr>
	    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class =normal align="">
      	    <tr  class="MsoNormal">
		          <td width="5" height="25"  class="MsoNormal"></td>
		          <td width="130" height="25"  class="MsoNormal"> <span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账户名称：</span></td>
		          <td width="250" height="25"  class="MsoNormal">
							<input type="text" name="sPayeeAccountName" size="24" value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountName():financeInfo.getPayerName() %>" readonly  class=box>					  		
		      </td>
		          <td width="5" height="25"  class="MsoNormal"></td>
		          <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
           </tr>
           <tr  class="MsoNormal">
		          <td width="5" height="25"  class="MsoNormal"></td>
		          <td width="130" height="25"  class="MsoNormal"> <span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账号：</span></td>
		          <td width="250" height="25"  class="MsoNormal">
						 <%SETTHTML.showFixedDepositAccountListCtrl(out,"sPayeeAccountNo",SETTConstant.AccountGroupType.FIXED,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
      				  <input type="hidden" name="payeeAcctID" value="<%=financeInfo.getPayerAcctID() %>" >
	         	  </td>
		          <td width="5" height="25"  class="MsoNormal"></td>
		 			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
	      			<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
      
           </tr>

           <!--a try for glass start-->
		 		 <tr  class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal">
			         <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>"/>
			         <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>"/>
			         <input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>"/>
		     	 </td>
		         <%
				 String [] strNextCtrl = {"amount"};
		         String [] strReturnNames = {"sPayerAccountNoZoom","sPayerAccountNoZoomAccountID"};
		         String [] strReturnFields = {"SubAccountID","AccountID"};
		         String [] strReturnValues = {lSubaccountid,""};
		         String sysdate=Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID );
		         %>	
		        <td width="130" height="25" align="left"><font color="red">*&nbsp;</font>定期存单号：
		        <input type="hidden" name="sysdate" value="<%=sysdate %>"/></td>
				<td>
						<input type="hidden" name="lClientID"	value="<%=sessionMng.m_lClientID %>"/> 
	
 						<fs:dic id="sDepositBillNo" size="22" form="frm" title="存款单据号" sqlFunction="getFixedDepositNoSQLOB"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,0,frm.payeeAcctID.value,frm.lUserID.value,frm.sDepositBillNo.value,3,frm.sysdate.value' value="<%=financeInfo.getSDepositBillNo() %>" nextFocus="amount" width="250">
							<fs:columns> 
								<fs:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" width="200" sort="true" align="center"/>
							</fs:columns>
							<fs:pageElements>
								<fs:pageElement elName="sDepositBillNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs:pageElement elName="lClientIDCtrl" name="ClientNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="strClientName" name="ClientName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="lClientID" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="lAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="lAccountIDClientID" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="dPayerStartDate" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="dPayerEndDate" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="nFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="dPayerCurrBalance" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="hidden" />
								<fs:pageElement elName="dPayerCurrInterest" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sDepositBillStartDate" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="amount" name="Balance" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="hidden" />
								<fs:pageElement elName="sPayerAccountNoZoom" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs:pageElement elName="sPayerAccountNoZoomAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs:pageElements>							
						</fs:dic> 
				 </td>
		         <td width="130" class="MsoNormal"></td>
				 <td width="5" class="MsoNormal"></td>
     	         <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
		   </tr>
	        <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25" nowrap class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期存款起始日：</td>
		         <td width="250" height="25"  class="MsoNormal" ><input type="text" class="box" name="dPayerStartDate" size="16" value="<%= (financeInfo.getID()==-1 && financeInfo.getDepositStart()==null)? "":financeInfo.getDepositStart().toString().substring(0,10) %>" readonly></td>
		  		 <td width="110" height="25"  nowrap class="MsoNormal" ><font color="red">&nbsp;&nbsp;</font>定期存款结束日：</td>
		  		 <td>  <input type="text" class="box" name="dPayerEndDate" size="16" value="<%= (financeInfo.getID()==-1)?"":dPayerEndDate%>" readonly></td>
		   		<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
	        </tr>
	       <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>存单金额：</td>
		         <td width="250" height="25"  class="MsoNormal">
		         	<fs:amount 
			       		form="frm"
		       			name="dPayerCurrBalance"
		       			value="<%=financeInfo.getDepositAmount() %>"
		       			nextFocus="nFixedDepositTime"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		       	</td>
			     <td width="110" height="25"  nowrap class="MsoNormal" ><font color="red">&nbsp;&nbsp;</font>期限：</td><td><input type="text" class="box" name="nFixedDepositTime" size="2" value="<%= (financeInfo.getID()==-1)?"":String.valueOf(financeInfo.getFixedDepositTime())%>" readonly></td>
	             <td width="5" colspan="4"  class="MsoNormal"></td>
          </tr>
	        	       <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>利率：</td>
		         <td width="250" height="25"  class="MsoNormal"><input type="text" class="box" name="dPayerCurrInterest" size="16" value="<%= DataFormat.formatRate(financeInfo.getDepositRate())%>" readonly style="text-align:right">&nbsp;%
			     </td>
	             <td width="5" height="25"  class="MsoNormal"></td>
	             <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
	        </tr>  
      </table>
      <br>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
	      <tr>
	        <td>
	        	<table width="110" border="0" cellspacing="0" cellpadding="0">
					<td width="3"><a class=lab_title1></td>
					<td class="lab_title2"> 到期续存</td>
					<td width="17"><a class=lab_title3></td>		          
	        	</table>
	        </td>
	      </tr>
	  </table>      				 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class =normal align="">
            <tr  class="MsoNormal">
                 <td width="5" height="25"></td>
                 <td width="130" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>金额：&nbsp;</td>                      
                 <td height="25" width="250"  class="MsoNormal">
		         	<fs:amount 
			       		form="frm"
		       			name="amount"
		       			value="<%=Double.parseDouble(DataFormat.reverseFormatAmount(financeInfo.getFormatAmount())) %>"
		       			nextFocus="sDepositBillPeriod"
		       			readonly="true"
		       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
		         </td>
		         <td height="25"  class="MsoNormal" colspan="2"></td>
          </tr>
	        <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>定期存款期限：</td>
		         <td width="250" height="25"  class="MsoNormal">
		         <%SETTHTML.showFixedDepositMonthListCtrl(out,"sDepositBillPeriod",SETTConstant.TransQueryType.FIXED,financeInfo.getSDepositBillPeriod(),false,"onfocus=\"nextfield='tsExecute';\" onchange=\"dateChange(frm)\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
			     </td>
	             <td class="MsoNormal" colspan="2"></td>
	        </tr>
       <tr class="MsoNormal"> 
	      <td width="5" height="25" class="MsoNormal"></td>
	      <td height="25" width="130"  class="MsoNormal"><font color="red">*&nbsp;</font>执行日：</td>
	      <td height="25" width="250"  class="MsoNormal">
	      <% OBConstant.Calendar.show(out,"tsExecute",(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate(),"nextfield ='add1'","20");%>
	      </td>
	      <td height="25" class="MsoNormal" colspan="2"></td>
      </tr>
       <tr class="MsoNormal">
	         <td width="5" height="25"  class="MsoNormal"></td>
	         <td width="130" height="25" nowrap class="MsoNormal"><font color="red">*&nbsp;</font>新定期存款起始日：</td>
	         <td width="250" height="25"  class="MsoNormal"><input type="text" name="sDepositBillStartDate" value="<%= (financeInfo.getID() == -1) ? "":String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10) %>"  size="12" readonly class="box"></td>
	         <td width="130"><font color="red">*&nbsp;</font>新定期存款到期日：</td>
	         <td><input type="text" name="dPayerCurrEndDate" value="<%= (financeInfo.getID() == -1) ? "":String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10) %>" size="12" readonly class="box" onblur="PeriodOnblur()"></td>
        </tr>
      </table>
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>      
      <br/>
	<table width="110" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2"> 自动续存</td>
			<td width="17"><a class=lab_title3></td>
		</tr>		          
	</table>      
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class =normal align="">      
<!-- 到期自动续存 -->

			<TR>
				<td width="5" height="25"  class="MsoNormal"></td>
	          	<TD height=20 width="110">是否自动续存：</TD>
	          	<TD height=20 width="250"><input <%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"checked":"" %> type="checkbox" name="isautocontinue"  value="1"  onclick="changeContinueType(this)" checked/></TD>					
			  	<TD height="20" ></TD>
			</TR>
			<TR style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr1">
	          <td width="5" height="25"  class="MsoNormal">&nbsp;<INPUT <%=rdoInterest!=2?"checked":"" %>  name=rdoInterest type=radio onfocus="nextfield ='';" value="1"/></td>
	          <TD height=20 width="110">本利续存</TD>
	          <TD height=20 width="250"></TD>					
			  <TD height="20" ></TD>
			</TR>
    <tr  class="MsoNormal" style="display:<%=(financeInfo.getIsAutoContinue()== 2 ? 2 :financeInfo.getIsAutoContinue())==1?"":"none" %>" id="isautocontinuetr2">
        <td width="5" height="25"  class="MsoNormal">&nbsp;<INPUT <%=(rdoInterest==2?"checked":"")%> name=rdoInterest type=radio onfocus="nextfield ='';" value="2"/></td>
        <%
		//收款方账号放大镜
		String sDepositInterestToAccountNO1 = "";
		if(financeInfo.getAutocontinueaccountid() > 0 && rdoInterest==2)
		{
		      sDepositInterestToAccountNO1 = NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid());
		}

		%>
		<td width="115" height="25" align="left">利息转至活期账号：</td>
		<td width="250">
			<fs:dic id="lInterestToAccountID1Ctrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyID.value,frm.lInterestToAccountID1Ctrl.value,frm.strReceiveInterestAccountClientName1.value' value="<%=sDepositInterestToAccountNO1 %>" nextFocus="amount" width="500">
				<fs:columns> 
					<fs:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs:columns>
				<fs:pageElements>
					<fs:pageElement elName="lInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="lInterestToAccountID1" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="strReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="hidlInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="hidstrReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs:pageElements>							
			</fs:dic> 
		</td>
	    <td width="130">户名：</td>
		<td><input disabled class="box" type="text" name="strReceiveInterestAccountClientName1" value="<%=(financeInfo.getAutocontinueaccountid()>0&&rdoInterest==2)?NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid()):""%>"></td>	
    </tr>

    <input type="hidden" name="lInterestToAccountID1" value="<%=financeInfo.getAutocontinueaccountid()>0?financeInfo.getAutocontinueaccountid():-1 %>"> 
<!-- End -->
   </table>
<%} %>     
      
	  <br/>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
	      <tr>
	        <td>
	        	<table width="110" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3"><a class=lab_title1></td>
						<td class="lab_title2"> 利息处理</td>
						<td width="17"><a class=lab_title3></td>
					</tr>		          
	        	</table>
	        </td>
	      </tr>
	  </table>  	  						  				
      
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class =normal align="">
    <tr  class="MsoNormal"> 
      	<td width="5" height="25"  class="MsoNormal">&nbsp;<input type="radio" name="interesttype" value="1" onclick="startRadio()" checked></td>
      	<td width="110" height="25"  class="MsoNormal"><span class="MsoNormal">本利续存</span></td>
   		<td height="25" colspan="4"  class="MsoNormal"></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="5" height="25"  class="MsoNormal">&nbsp;<input type="radio" name="interesttype" value="2" onclick="startRadio()" ></td>
        <%
		//收款方账号放大镜（本转）
		String sDepositInterestToAccountNO = "";
		if(financeInfo.getSDepositInterestToAccountID()>0 &&financeInfo.getSDepositInterestDeal() == 2)
		{
			//sDepositInterestToAccountNO = eBankNameRef.getRecAccountNOByID(financeInfo.getSDepositInterestToAccountID());
		      sDepositInterestToAccountNO = NameRef.getAccountNoByID(financeInfo.getSDepositInterestToAccountID());
		}

		%>
		<td width="115" height="25" align="left">利息转至活期账号：</td>
		<td width="250">
			<fs:dic id="lInterestToAccountIDCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyID.value,frm.lInterestToAccountIDCtrl.value,frm.sPayeeNameZoomBankCtrl.value' value="<%=sDepositInterestToAccountNO %>" nextFocus="amount" width="500">
				<fs:columns> 
					<fs:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs:columns>
				<fs:pageElements>
					<fs:pageElement elName="lInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="sDepositInterestToAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="sPayeeNameZoomBankCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="hidlInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs:pageElement elName="hidsPayeeNameZoomBankCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs:pageElements>							
			</fs:dic> 
		</td>
	<%-- modify by xwhe	账户名称：<input type="text" name="sPayeeNameZoomBankCtrl" value="<%=eBankNameRef.getRecAccountNameByID(financeInfo.getSDepositInterestToAccountID()) %>">--%>	
		<td width="130" >户名：</td>
		<td width="250" ><input class="box" type="text" name="sPayeeNameZoomBankCtrl" value="<%=NameRef.getAccountNameByID(financeInfo.getSDepositInterestToAccountID())%>"></td>
	    <td height="25" class="MsoNormal"></td>
    </tr>  
     <input type="hidden" name="sDepositInterestToAccountID" value="-1">
     <input type="hidden" name="nInterestToAccountID" value="">
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
	           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>'  
		        	caption = " 上 传 " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
      <%--     </td>
        </tr>
      </table>
      --%>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name="add1" type="button" value=" 保 存 " onClick="Javascript:addme();" onfocus="nextfield='submitfunction'"> 
			
			<fs:obApprovalinitbutton controlName="approvalInit" 
		 							value="保存并提交审批" 
									classType="button1" 
									onClickMethod="doSaveAndApprovalInit();" 
									officeID="<%=sessionMng.m_lOfficeID%>" 
									currencyID="<%=sessionMng.m_lCurrencyID%>" 
									clientID="<%=sessionMng.m_lClientID%>" 
									moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
									transTypeID="<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>"/>

			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<%--<input class=button1 name=add type=button value=" 重置 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();">--%> 
          	<input class=button1 name=add type=reset value=" 重 置 "> 
          </td>
          <td>&nbsp;</td>
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
	  <input type="hidden" name="timestamp" value="<%=System.nanoTime() %>">
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
</form>
<!--presentation end-->

<script language="Javascript">
        frm.sDepositBillNo.onblur= function(){
         var mm ;
         eval("mm=frm.nFixedDepositTime.value");
         if(mm > 10000){
          mm = mm-10000+'天';  
         }
         else if(mm > 0){
         mm =  mm+'月';
         }
         else{
         mm=mm;
         }
          eval("frm.nFixedDepositTime.value=mm");
        }
		if(frm.dPayerStartDate.value!=""&&frm.nFixedDepositTime.value!="")
		{	
		
			var d = ft_dateConvertDate(frm.dPayerStartDate.value) ;
			var arra = frm.dPayerStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(frm.nFixedDepositTime.value);
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    
		    frm.dPayerEndDate.value = ft_dateConvertStr(d);
		   
		    
		    
		    
		}


	if(<%=financeInfo.getSDepositInterestDeal()%> == 1){
			frm.interesttype[0].checked = true;
			//frm.lInterestToAccountIDCtrl.disabled = true;
			//frm.sPayeeNameZoomBankCtrl.value = "";	    		
	}else if(<%=financeInfo.getSDepositInterestDeal()%> == 2){
		frm.interesttype[1].checked = true;
	}
	
	function onChanged(obj){
		if(frm.dPayerStartDate.value!=""&&frm.nFixedDepositTime.value!="")
		{	
			var d = ft_dateConvertDate(frm.sDepositBillStartDate.value) ;
			var arra = frm.sDepositBillStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(obj.value);
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    
		    frm.dPayerCurrEndDate.value = ft_dateConvertStr(d);
		}
	}
	
	/* 新定期存款起始日 */
	function TimeOnblur()
	{
		if(frm.dPayerStartDate.value!=""&&frm.nFixedDepositTime.value!="")
		{	
			var d = ft_dateConvertDate(frm.dPayerStartDate.value) ;
			var arra = frm.dPayerStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(frm.nFixedDepositTime.value);
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    
		    frm.sDepositBillStartDate.value = ft_dateConvertStr(d);
		}
	}
	/* 定期存款期限的onblur事件 */
	function PeriodOnblur()
	{
		if(frm.sDepositBillStartDate.value!=""&&frm.sDepositBillPeriod.value!="")
		{	
			var d = ft_dateConvertDate(frm.sDepositBillStartDate.value) ;
			var arra = frm.sDepositBillStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(frm.sDepositBillPeriod.value);
			if(n<0){
				n = 0;
			}
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    frm.dPayerCurrEndDate.value = ft_dateConvertStr(d);
		}
	}
	
	/*日期格式转换为日期字符串*/
	function ft_dateConvertStr(d){
		var mon = d.getMonth()+1;
		var date = d.getDate();
		
		if(mon<=9&&mon>0)
		{
			mon = "0"+mon;
		}
		if(date<=9&&date>0)
		{
			date = "0"+date;
		}
		
		
		
		return d.getFullYear()+"-"+mon+"-"+date;
	}
	
	/* 日期字符串转换为日期格式 */
	function ft_dateConvertDate(dateStr){
		var arra = dateStr.split("-");
		return new Date(arra[0],arra[1]-1,arra[2]) ;
	}
	
    /* 点击单选按钮处理函数 */
    function startRadio()
    {
    	if(frm.interesttype[0].checked == true)
    	{
    	    frm.lInterestToAccountIDCtrl.value = "";
    	    frm.sDepositInterestToAccountID.value= -1 ;
    		frm.lInterestToAccountIDCtrl.disabled = true;
    		frm.sPayeeNameZoomBankCtrl.value = "";		  		
    	}
    	else if(frm.interesttype[1].checked == true)
    	{
    	  frm.lInterestToAccountIDCtrl.disabled = false;
    	}
     }

    /* 修改保存处理函数 */
    function addme()
    {
 
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		if (validate() == true)
        {
        	
		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new DriveFixDepositOperator();
		    	sign(format,frm);
		    }   
		         
			//确认保存 
			if (!confirm("是否保存？"))
			{
				return false;
			}
			showSending();
			calculateEndDate();
			frm.action = "f008-1-c.jsp?sign=<%=sign%>&oldhiddenOpendate="+frm.hiddenOpendate.value;
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            frm.submit();
        }
    }
    
     function doSaveAndApprovalInit()
    {
   
		frm.action = "f008-1-c.jsp?operate=saveAndApproval&sign=<%=sign%>";
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";

		if (validate() == true)
        {

		    //网银数字签名 
		    if(security.isSign())
		    {
		    	var format = new DriveFixDepositOperator();
		    	sign(format,frm);
		    }   
		    				
			//确认保存并提交审批
			if (!confirm("是否保存并提交审批？"))
			{
				return false;
			}
			
			showSending();

			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            //alert(frm.lInterestToAccountIDCtrl.value);
            frm.submit();
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

		/* 定期存款子账号不能为空 */
		if (frm.sDepositBillNo.value == "")
		{
			alert("请选择定期存款子账号");
			frm.sDepositBillNo.focus();
			return false;
		}
		/* 定期账号不能为空 */
		if (frm.sPayeeAccountNo.value == -1)
		{
			alert("请选择定期账号");
			frm.sPayeeAccountNo.focus();
			return false;
		}
		/* 检验执行日*/
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
		/* 新定期存款起始日校验 */
		if (!checkDate(frm.sDepositBillStartDate,1,"新定期存款起始日"))
		{
			return false; 
		}
		
		/* 新定期存款到期日校验 */
		if (!checkDate(frm.dPayerCurrEndDate,1,"新定期存款到期日"))
		{
			return false;
		}
		
		if (frm.sDepositBillPeriod.value == "" || frm.sDepositBillPeriod.value<0)
        {
             alert("请选择定期存款期限！");
			 frm.sDepositBillPeriod.focus();
             return false;
        }
        /*
		if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.amount.value))
		{
			alert("金额小于该账户定期开立起存金额，请重新输入！");
			document.frm.amount.focus();
			return false;
		}*/
		/* 划款资料非空校验 */
		
		/* 金额校验 */
		if(!checkAmount(frm.amount, 1, "金额"))
		{
			return false;
		}

		/* 业务校验 */
		/* 可用余额－交易金额 */
		/*
		var dBalance = parseFloat(reverseFormatAmount(frm.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frm.amount.value)) 
		
		
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入划拨金额");
			frm.amount.focus();
			return false;
		}
        */
        
        if(frm.interesttype[0].checked == false&&frm.interesttype[1].checked == false)
        {
        	alert("请选择利息处理类型");
        	frm.interesttype[0].focus();
        	return false;
        }
        
        if(frm.interesttype[1].checked == true)
        {
        	if(frm.lInterestToAccountIDCtrl.value != ""&& frm.lInterestToAccountIDCtrl.value != "0")
        	{
        		return true;
        	}
        	else
        	{
        		alert("请选择利息转至活期账号");
        		frm.lInterestToAccountIDCtrl.focus();
        		return false;
        	}
        }
    	return true;
    }
    	//日期或期限改变时自动改变
	function dateChange(form)
	{
		var nData1;
		var nData2;
		var nData3;
		
		eval("nData1=frm.sDepositBillStartDate.value");
		eval("nData2=frm.sDepositBillPeriod.value");
		//eval("frmV001.strInterestStartDateCtrl.value = nData1");
		//eval("form.txtDateInterestExecute.value = nData1");
		//eval("frm.strInterestStartDate.value = nData1");//隐藏控件
	
		if (nData2 != -1&& nData1 !="")
		{
			nData3 = addDate(nData1,nData2,form);
			eval("frm.dPayerCurrEndDate.value = nData3");
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
	 //有到期日预算开始日期
		function dateChangeEnd(form)
		{
			var nData1;
			var nData2;
			var nData3;
				
			eval("nData1=frmV001.strEndDate.value");
			eval("nData2=frmV001.lDepositTerm.value");
			
			if (nData2 != -1)
			{
				nData3 = addDateEnd(nData1,nData2,form);
				eval("frmV001.strStartDate.value = nData3");
			}
				
			//eval("frmV001.strInterestStartDateCtrl.value = frmV001.strStartDate.value");
			//eval("form.txtDateInterestExecute.value = nData1");
			eval("frmV001.strInterestStartDate.value = frmV001.strStartDate.value");//隐藏控件
		}
			function addDateEnd(strInputDate,lMonth,form)
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
		    				alert("请输入正确的到期日期");
			    			form.strEndDate.focus();
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
					dtMonth = dtDate.getMonth()+1 - parseInt(lMonth);					
					dtDate.setMonth(dtMonth);
					dtMonth = dtDate.getMonth();
					dtDay = dtDate.getDate();			
					if(dtMonth == 4||dtMonth ==6||dtMonth ==9||dtMonth==11)
					{
					   if(dtDay == 31)
					     dtDate.setDate(30);
					}
					if(dtMonth == 2)
					{
					  if(dtDay == 30||dtDay == 31|| dtDay ==29)
					  {			   
					     if(dtDate.getYear()%4 ==0)
						    dtDate.setDate(29);
						 else
						    dtDate.setDate(28);
					  }
					}			
				}
				else
				{
					lMonth = parseInt(lMonth) - 10000;			
					dtDay =  dtDate.getDate() - parseInt(lMonth);			
					dtDate.setDate(dtDay);			
					
					dtMonth = dtDate.getMonth()+1;
					dtDate.setMonth(dtMonth);
					dtMonth = dtDate.getMonth();
					dtDay = dtDate.getDate();			
					if(dtMonth == 4||dtMonth ==6||dtMonth ==9||dtMonth==11)
					{
					   if(dtDay == 31)
					     dtDate.setDate(30);
					}
					if(dtMonth == 2)
					{
					  if(dtDay == 30||dtDay == 31 || dtDay ==29)
					  {
					     if(dtDate.getYear()%4 ==0)
						    dtDate.setDate(29);
						 else
						   dtDate.setDate(28);
					  }
					}			
				}
				
				dtDay = dtDate.getDate();
				dtMonth = dtDate.getMonth();
				dtYear = dtDate.getYear();
				if (dtMonth == 0)
				{
					dtYear--;
					dtMonth = 12;
				}
				
				eval("strDate='"+ dtYear + "-" +dtMonth + "-" + dtDay + "'");
				return strDate;
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
		function calculateEndDate()
		{
			var d = ft_dateConvertDate(frm.dPayerStartDate.value) ;
			var arra = frm.dPayerStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(frm.nFixedDepositTime.value);
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    
		    frm.endDate.value = ft_dateConvertStr(d);
		    
		}
	function saccountnochange()
    {
    	frm.payeeAcctID.value = frm.sPayeeAccountNo.value;
    	frm.sPayerAccountNoZoom.value = -1;
    	frm.sDepositBillNo.value = "";
    	frm.dPayerStartDate.value = "";
    	frm.dPayerEndDate.value = "";
    	frm.dPayerCurrBalance.value = "";
    	frm.nFixedDepositTime.value = "";
    	frm.dPayerCurrInterest.value = "0.000000";
    	frm.amount.value = "";
    	$.ajax(
    		{
    			type:'post',
    			url:'<%=strContext%>/capital/fixed/returnOpendate.jsp',
    			data:"accountId="+frm.payeeAcctID.value+"&currenctId="+frm.currencyId.value,
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
	firstFocus(frm.sDepositBillNo);
	////setSubmitFunction("addme(frm)");
	setFormName("frm");
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
