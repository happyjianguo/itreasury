<%--
/*
 * 程序名称：ck010-v.jsp
 * 功能说明：到期续存修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
	               com.iss.itreasury.settlement.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.settlement.util.NameRef,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<%@page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%!
	/* 标题固定变量 */
	String strTitle = "[业务复核]";
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
%>
<%
	/* 实例化信息类 */
	String strContext = request.getContextPath();
	FinanceInfo financeInfo = new FinanceInfo();
	OBQueryInfo obQueryInfo = new OBQueryInfo();
	PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	String dPayerStartDate = "";
	String nFixedDepositTime = "";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();
	long lTransType = -1;
	long rdoInterest = -1;
	long isautocontinue = 1;
	long blInterest = -1;

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
		if(request.getAttribute("FormValue") != null)
		{
			obQueryInfo = (OBQueryInfo)request.getAttribute("FormValue");
			rdoInterest = obQueryInfo.getAutocontinuetype();
			blInterest = obQueryInfo.getFixedInterestDeal();
			isautocontinue = obQueryInfo.getIsAutoContinue();			
		}		
		if(request.getAttribute("payerOrPayeeInfo") != null)
		{
			payerInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo");
			Log.print("----------------payerid"+payerInfo.getAccountID());
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
		
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
		
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<jsp:include page="/ShowMessage.jsp"/>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frm">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title  nowrap ><span class="txt_til2" >到期续存业务复核</span></td>
			       <td class=title_right ><a class=img_title></td>
			     <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>"/>
		         <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>"/>
		         <input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID %>"/>
		         <%		         String sysdate=Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID );
 				 %>
 		        <input type="hidden" name="sysdate" value="<%=sysdate %>"/>
				</TR>
			</TABLE>
<br/>
<!-- 	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
  <tr>
    <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 到期续存</td>
    <td width="683"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>  
</table>  --> 
     
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal" align="">
    <tr  class="MsoNormal"> 
      <td colspan="4" height="15"></td>
      <td colspan="3"  class="MsoNormal"></td>
    </tr>
    <!--a try for glass start-->
    <%
		if(lTransType == OBConstant.QueryInstrType.DRIVEFIXDEPOSIT)
		{
%>
	 <tr  class="MsoNormal"> 
        <td width="20" height="25"  class="MsoNormal" align="center"><font color="#FF0000">* </font></td>
		<td width=130 width="130" height="25" class="MsoNormal">定期账号</td>
		<td width="590" height="25" >
				<%SETTHTML.showFixedDepositAccountListCtrl(out,"saccountno1",SETTConstant.AccountGroupType.FIXED,financeInfo.getPayerAcctID() ,false,"onchange=saccountnochange();",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
		</td>
        <td  class="MsoNormal"></td>
	    <td width="263"  class="MsoNormal"></td>
	    <td width="199"  class="MsoNormal"></td>
	              			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
	    
    </tr>
    <tr  class="MsoNormal"> 
      <td width="20" height="25"  class="MsoNormal" align="center"><font color="#FF0000">* </font></td>
		  <%
				 String [] strNextCtrl = {"nFixedDepositTime"};
		         String [] strReturnNames = {"sPayerAccountNoZoom","sPayerAccountNoZoomAccountID"};
		         String [] strReturnFields = {"SubAccountID","AccountID"};
		         String [] strReturnValues = {"",""};
			 	 //付款方账号放大镜
		/*	 	 OBMagnifier.createFixedDepositNoCtrlZJ(out,
			 	 sessionMng.m_lOfficeID,
			 	 sessionMng.m_lCurrencyID,
			 	 "frm",
			 	 "sPayerAccountNoZoom",
			 	 "定期存款单据号",
			 	 sessionMng.m_lUserID,
			 	 (obQueryInfo.getPayeeAcctID() == -1) ? payerInfo.getAccountID():obQueryInfo.getPayeeAcctID(),
			 	 Long.parseLong("0"),
			 	 obQueryInfo.getSDepositBillNo() == null ? "":obQueryInfo.getSDepositBillNo(),
			 	 Long.parseLong("0"),
			 	 Long.parseLong("4"),
			 	 Long.parseLong("0"),
			 	 "saccountno1",
			 	 " width=\"130\" height=\"25\" class=\"MsoNormal\"",
			 	 " width=\"590\" height=\"25\" ",
			 	 strNextCtrl,
			 	 "",
			 	 "",
			 	 "",
			 	 "",
			 	 "",
			 	 "",
			 	 "",
			 	 strReturnNames,
			 	 strReturnFields,
			 	 strReturnValues);*/
				 //OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","定期存款子账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",strNextCtrl);	
		         %>			
		         
		        <td width="130" height="25" align="left">定期存单号：</td>
				<td>
						<input type="hidden" name="lClientID"	value="<%=sessionMng.m_lClientID %>"/> 
	
 						<fs_c:dic id="sPayerAccountNoZoomCtrl" size="22" form="frm" title="存款单据号" sqlFunction="getFixedDepositNoSQLOB"  sqlParams='frm.lOfficeID.value,frm.lCurrencyID.value,0,frm.nPayeeAccountID.value,frm.lUserID.value,frm.sPayerAccountNoZoomCtrl.value,4,frm.sysdate.value'  value='<%= obQueryInfo.getSDepositBillNo() == null ? "":obQueryInfo.getSDepositBillNo() %>'  nextFocus="nFixedDepositTime" width="500">
							<fs_c:columns> 
								<fs_c:column display="存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
								<fs_c:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
							</fs_c:columns>
							<fs_c:pageElements>
								<fs_c:pageElement elName="sPayerAccountNoZoomCtrl" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="lClientIDCtrl" name="ClientNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs_c:pageElement elName="strClientName" name="ClientName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs_c:pageElement elName="lClientID" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="lAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs_c:pageElement elName="lAccountIDClientID" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs_c:pageElement elName="dPayerStartDate" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="dPayerEndDate" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="nFixedDepositTime" name="Interval" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="dPayerCurrBalance" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="dPayerCurrInterest" name="Rate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="dPayerCurrStartDate" name="EndDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="s" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
								<fs_c:pageElement elName="sPayerAccountNoZoom" name="SubAccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
								<fs_c:pageElement elName="sPayerAccountNoZoomAccountID" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
							</fs_c:pageElements>							
						</fs_c:dic> 
				 </td>
      <td  class="MsoNormal"></td>
	   <td width="263"  class="MsoNormal"></td>
	    <td width="199"  class="MsoNormal"></td>
	              			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
	    
    </tr>
        <tr class="MsoNormal"> 
      <td width="20" height="25" class="MsoNormal"></td>
      <td width="280" height="25" class="MsoNormal" >续期新定期存款起始日：</td>
      <td height="25" colspan="2" class="MsoNormal"> 
	      <fs_c:calendar 
          	    name="strNewStartDate"
	          	value="" 
	          	properties="nextfield =''" 
	          	size="20"/>
	        	  <script>
	          		$('#strNewStartDate').val('<%=(obQueryInfo.getStrNewStartDate() == null) ? DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :obQueryInfo.getStrNewStartDate()%>');
	          	</script>
      </td>
      <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
    </tr>

    <tr class="MsoNormal"> 
      <td width="20" height="25" class="MsoNormal"></td>
      <td width="280" height="25" class="MsoNormal"> <p align="left"><span class="MsoNormal">新定期存款期限：</span></p></td>
      <td height="25" colspan="2" class="MsoNormal"> 
        <%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime1",SETTConstant.TransQueryType.FIXED,obQueryInfo.getLFIXEDNEXTPERIOD(),false,"onfocus=\"nextfield='dAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
      </td>
                			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
      
    </tr>

    <tr  class="MsoNormal"> 
      <td width="20" height="25"></td>
      <td width="280" height="25"  class="MsoNormal">金额：</td>
      <td height="25" colspan="2"  class="MsoNormal">  
        <fs:amount 
	       		form="frm"
       			name="dAmount"
       			value="<%=obQueryInfo.getAmount()%>"
       			nextFocus="tsExecute"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
      <td width="5" height="25"></td>
                			<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="20" height="25" class="MsoNormal"></td>
      <td width="280" height="25" class="MsoNormal" >执行日：</td>
      <td height="25" colspan="2" class="MsoNormal">
	      <fs_c:calendar 
          	    name="tsExecute"
	          	value="" 
	          	properties="nextfield =''" 
	          	size="20"/>
	        	<script>
	          		$('#tsExecute').val('<%=(obQueryInfo.getExecuteDate() == null) ? DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :obQueryInfo.getExecuteDate()%>');
	          	</script>
      </td>
                			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
    </tr>
<!-- 自动续存 --> 
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>   
			<TR >
			  <td width="5" height="25"  class="MsoNormal"></td>	
	          <TD height=20 width="20%">是否自动续存：</TD>
	          <TD height=20 width="30%"><input <%=isautocontinue==1?"checked":"" %> type="checkbox" name="isautocontinue"  value="1"  onclick="changeContinueType(this)"/></TD>					
			  <TD height="20" width="16%"></TD>
			            			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
			</TR>
			<TR  style="display:<%=isautocontinue==1?"":"none" %>" id="isautocontinuetr1">
			<td width="5" height="25"  class="MsoNormal"></td>
	          <TD height=20 width="15%">&nbsp;<INPUT <%=rdoInterest!=2?"checked":"" %>  name=rdoInterest type=radio onfocus="nextfield ='';" value="1"/>本利续存</TD>
	          <TD height=20 width="30%"></TD>					
			  <TD height="20" width="16%"></TD>
			            			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
			  
			</TR>
    <tr  class="MsoNormal" style="display:<%=isautocontinue==1?"":"none" %>" id="isautocontinuetr2">
    <td width="5" height="25"  class="MsoNormal"></td> 
        <%
		//收款方账号放大镜
		String sDepositInterestToAccountNO1 = "";
		if(obQueryInfo.getAutocontinueaccountid() > 0 && rdoInterest==2)
		{
		      sDepositInterestToAccountNO1 = NameRef.getAccountNoByID(obQueryInfo.getAutocontinueaccountid());
		}		
	/*   	OBMagnifier.createInterPayeeBankNOCtrl(
	   		out,
	   		sessionMng.m_lCurrencyID,
	   		sessionMng.m_lClientID,
	   		"lInterestToAccountID1",
	   		"strReceiveInterestAccountClientName1",
	   		"frm",
	   		sDepositInterestToAccountNO1,
	   		"lInterestToAccountID1",
	   		"&nbsp;<INPUT "+(rdoInterest==2?"checked":"")+" name=rdoInterest type=radio onfocus=\"nextfield ='';\" value=\"2\"/>利息转至活期账号",
	   		" style=\"width:20%\" height=\"25\" class=\"MsoNormal\"",
	   		" width=\"190\" height=\"25\" ");*/
		%>
		<td width="150" height="25" align="left">&nbsp;<INPUT <%=(rdoInterest==2?"checked":"")%> name=rdoInterest type=radio onfocus="nextfield ='';" value="2"/>利息转至活期账号：</td>
			<td>
				<fs_c:dic id="lInterestToAccountID1Ctrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyID.value,frm.lInterestToAccountID1Ctrl.value,frm.strReceiveInterestAccountClientName1.value' value="<%=sDepositInterestToAccountNO1 %>" nextFocus="dAmount" width="500">
					<fs_c:columns> 
						<fs_c:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs_c:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs_c:columns>
					<fs_c:pageElements>
						<fs_c:pageElement elName="lInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lInterestToAccountID1" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="strReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="hidlInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="hidstrReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					</fs_c:pageElements>							
				</fs_c:dic> 
			</td>
		<td  nowrap width="27%">&nbsp;&nbsp;
		活期客户名称：<input disabled class="box" type="text" name="strReceiveInterestAccountClientName1" value="<%=NameRef.getAccountNameByID(obQueryInfo.getAutocontinueaccountid())%>">
		</td>	
		          			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
		
    </tr>
<%} %>
    <input type="hidden" name="lInterestToAccountID1" value="<%=obQueryInfo.getAutocontinueaccountid()>0?obQueryInfo.getAutocontinueaccountid():-1 %>">  
<!-- End -->    

       <tr class="MsoNormal"> 
      <td width="20" height="25" class="MsoNormal"></td>
      <td width="280" height="25" class="MsoNormal" >利息处理方式：</td>
      <td height="25" colspan="2" class="MsoNormal"></td>
                			<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
      
    </tr>

    <tr  class="MsoNormal"> 
    <td width="5" height="25"  class="MsoNormal"></td>
      <td width="280" height="25"  class="MsoNormal"><span class="MsoNormal">&nbsp;<input <%=blInterest!=2?"checked":"" %> type="radio" name="interesttype" value="1" onClick="onRadio()">本利续存</span></td>
      <td height="25"  class="MsoNormal" colspan=3><span class="MsoNormal"></span></td>
      <td width="7"></td>
                			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
      
    </tr>
    <tr  class="MsoNormal"> 
	      <td width="20" height="25"  class="MsoNormal"> </td>
     <%
		          	    //long aa = -1000;
		               // String [] strNextCtrl1 = {"nFixedDepositTime"};
						//SETTMagnifier.createAccountCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lInterestToAccountNO","利息转至活期账号"
						//,sessionMng.m_lClientID,Long.parseLong("0"),"",Long.parseLong("1"),Long.parseLong("0"),aa,
						//""," width=\"150\" height=\"25\" class=\"MsoNormal\""," width=\"260\" height=\"25\"",strNextCtrl1,"","","dClientName");
				  
				  		//收款方账号放大镜（本转）
		String sDepositInterestToAccountNO = "";
		if(obQueryInfo.getLInterestToAccountID()>0)
		{
		// modify by xwhe
		//	sDepositInterestToAccountNO = eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID());
		    sDepositInterestToAccountNO = NameRef.getAccountNoByID(obQueryInfo.getLInterestToAccountID());
		}
	  //OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"lInterestToAccountID","sPayeeNameZoomBankCtrl","frm",sDepositInterestToAccountNO,"lInterestToAccountID","利息转至活期账号"," width=\"170\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
	/*  	OBMagnifier.createInterPayeeBankNOCtrl(out,
	  			sessionMng.m_lCurrencyID,
	  			sessionMng.m_lClientID,
	  			"lInterestToAccountID",
	  			"sPayeeNameZoomBankCtrl",
	  			"frm",
	  			sDepositInterestToAccountNO,
	  			"lInterestToAccountID",
	  			"&nbsp;<input "+(blInterest==2?"checked":"")+" type=\"radio\" name=\"interesttype\" value=\"2\" onClick=\"onRadio()\">利息转至活期账号",
	  			" style=\"width:170px\" height=\"25\" nowrap class=\"MsoNormal\"",
	  			" width=\"190\" height=\"25\" ");*/
	  %>
	  <td width="150" height="25" align="left">&nbsp;<input <%=blInterest==2?"checked":""%> type="radio" name="interesttype" value="2" onClick="onRadio()">利息转至活期账号：</td>
		<td width="">
				<fs_c:dic id="lInterestToAccountIDCtrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyID.value,frm.lInterestToAccountIDCtrl.value,frm.sPayeeNameZoomBankCtrl.value' value="<%=sDepositInterestToAccountNO %>" nextFocus="dAmount" width="500">
					<fs_c:columns> 
						<fs_c:column display="收款方账号" name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs_c:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs_c:columns>
					<fs_c:pageElements>
						<fs_c:pageElement elName="lInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="lInterestToAccountID" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="sPayeeNameZoomBankCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs_c:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="hidlInterestToAccountIDCtrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
						<fs_c:pageElement elName="hidsPayeeNameZoomBankCtrl" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					</fs_c:pageElements>							
				</fs_c:dic> 
			</td>
			          			<td width="5" height="25" colspan="4"  class="MsoNormal"></td>
			
    </tr>

    <%
		}
%>
    
         <tr>
	          		<td colspan="5">

	          		</td>          
	          		<td width="162" align="right" nowrap>
	           
					<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
					<input type="button" name="Submitv00204" value=" 匹 配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
					<input type="button" name="Submitv00204" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
					</td>
					<td>
	   				</td>
		</tr>
		<tr><td height="15">&nbsp;</td></tr>
  </table>
<!--   <table width="100%">
   	<tr><td height="10"></td><td></td></tr>
   		 <tr class="MsoNormal">
          <td class="MsoNormal" colspan="4">
            <div align="right" ></div>
          </td>          
          <td width="162" align="right"> -->
			<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
<!--			<input type="button" name="Submitv00204" value=" 匹配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
          	<input type="button" name="Submitv00204" value=" 返回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
          </td>
        </tr>
   </table> -->
      <input type="hidden" name="sPayeeNameZoomBankCtrl">
      <input type="hidden" name="lInterestToAccountID" value="<%=  obQueryInfo.getLInterestToAccountID() %>" >
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=  obQueryInfo.getInterestPayeeAcctID() %>" >
	 <%--modify by  xwehe 2008-05-07 <input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >--%>
	  <input type="hidden" name="nPayeeAccountID"  value="<%= (obQueryInfo.getPayerAcctID() == -1) ? payerInfo.getAccountID():obQueryInfo.getPayerAcctID() %>" >	 
	  <input type="hidden" name="nPayerAccountID" value="<%=(obQueryInfo.getPayerAcctID() == -1) ? payerInfo.getAccountID() : obQueryInfo.getPayerAcctID() %>" >
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="SelectType" value="<%= lTransType %>">
	  <input type="hidden" name="nSubAccountID" value="<%=  obQueryInfo.getSubAccountID() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="hiddenOpendate" value="">
	  <input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">
    </td>
  </tr>
</table>
</form>
<!--presentation end-->

<script language="Javascript">
    /* 修改提交处理函数 */
    function matchme()
    {
        
		frm.action = "ck007-c.jsp";
		if (validate() == true)
        {
			/* 确认提交 */
			if (!confirm("是否匹配？"))
			{
				return false;
			}
			showSending();
            frm.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (frm.lInstructionID.value == -1)
		{	
			if (confirm("确定取消吗？"))
			{
				frm.action="";
				frm.submit();
			}
		}
		else
		{
			if (confirm("确定取消吗？"))
			{
        		history.go(-1);
			}
		}
    }

    /* 校验函数 */
    function validate()
    {
    	if(frm.saccountno1.value<=0)
    	{
    		alert("请选择定期帐号");
    		frm.saccountno1.focus();
    		return false;
    	}
		if(document.frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("定期存款子账号,请从放大镜中选择")
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		
		var strNewStartDate = frm.strNewStartDate.value;
		if(chkdate(strNewStartDate) == 0)
		{
			alert("续期新定期存款起始日格式不正确，请重新录入");
			frm.strNewStartDate.focus();
			return false;
		}
		
		if(document.frm.nFixedDepositTime1.value == "-1"){
			alert("新定期存款期限不能为空，请录入");
			document.frm.nFixedDepositTime1.focus();
			return false;
		}
		<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

		{ %>
		if (frm.lInterestToAccountID1Ctrl.value == "" )
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
				frm.lInterestToAccountID1Ctrl.focus();
				return false;
			}
		}
		<%}%>
		
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
		
        if(frm.interesttype[0].checked == false&&frm.interesttype[1].checked == false)
        {
        	alert("请选择利息处理类型");
        	frm.interesttype[0].focus();
        	return false;
        }
		
		if(frm.interesttype[1].checked == true)
        {
        	if(frm.lInterestToAccountIDCtrl.value != ""&& frm.lInterestToAccountID.value > 0)
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
    	function goback(){
		location.href='ck001-v.jsp';
	}
	
    function onRadio(){
    	if(frm.interesttype[0].checked == true)
    		{
	    		/*
	    		frm.lInterestToAccountID.disabled = true;
	    		frm.lInterestToAccountIDCtrlCtrl1.disabled = true;
	    		frm.lInterestToAccountIDCtrlCtrl2.disabled = true;
	    		frm.lInterestToAccountIDCtrlCtrl3.disabled = true;
	    		frm.lInterestToAccountIDCtrlCtrl4.disabled = true; 
	    		*/
	    		frm.lInterestToAccountIDCtrl.disabled = true;   		    		
    		}
    		else if(frm.interesttype[1].checked == true)
    		{
    			/*
	    		frm.lInterestToAccountIDCtrlCtrl1.disabled = false;
	    		frm.lInterestToAccountIDCtrlCtrl2.disabled = false;
	    		frm.lInterestToAccountIDCtrlCtrl3.disabled = false;
	    		frm.lInterestToAccountIDCtrlCtrl4.disabled = false;     		
	    		frm.lInterestToAccountID.disabled = false;
	    		*/
	    		frm.lInterestToAccountIDCtrl.disabled = false;   	
    		}
    }
   
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>     
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
<%
}
%>		    

function saccountnochange()
{
		frm.sPayerAccountNoZoom.value = -1;
		frm.sPayerAccountNoZoomCtrl.value = "";
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