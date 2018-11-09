<%--
/*
 * 程序名称：ck003-v.jsp
 * 功能说明：定期开立、通知开立复核匹配输入页面
 * 作　　者：刘琰
 * 完成日期：2004年02月06日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<%@page import="com.iss.itreasury.ebank.obquery.dataentity.OBQueryInfo"%>
<%@page import="com.iss.itreasury.settlement.util.NameRef"%>
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
	
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = new FinanceInfo();
	OBQueryInfo obQueryInfo = new OBQueryInfo();
	PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	long lTransType = -1;
	long rdoInterest = -1;
	long isautocontinue = 1;	

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
			isautocontinue = obQueryInfo.getIsAutoContinue();			
		}
		payeeInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo");
		if(request.getAttribute("lTransType") != null)
		{
			lTransType = Long.parseLong((String)request.getAttribute("lTransType"));
		}
	
		
				
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<jsp:include page="/ShowMessage.jsp"/>
<%@ include file="/common/common.jsp" %>
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
		    <table width="100%" cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span nowrap class="txt_til2" ><%=(lTransType==OBConstant.QueryInstrType.OPENFIXDEPOSIT)?"定期开立复核":"通知存款开立复核"%></span></td>
			       <td class=title_right ><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>
<!--	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">

      <tr>
        <td>
	         <table width="150" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	            <td width="130" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> <%=(lTransType==OBConstant.QueryInstrType.OPENFIXDEPOSIT)?"定期开立复核":"通知存款开立复核"%></td>
	            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	          </tr>
	        </table>
        </td>
      </tr>
    </table> -->  
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
  	<tr><td width="5" height="25" colspan="6"  class="MsoNormal"></td></tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>"/>
      <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>"/>
      <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>"/>
      <input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID%>"/>
      <input type="hidden" name="InstructionID" value="<%=financeInfo.getID()%>"/>
      <%Timestamp opendate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strOpenDate = sdf.format(opendate); %>
      <input type="hidden" name="hiddenOpendate" value="<%=strOpenDate%>"/>
<%
		//付款方账号放大镜
		String[] nextfocus = null;
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
		{
			nextfocus = new String[]{"nFixedDepositTime"};
		}
		else if (lTransType == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT)
		{
			nextfocus = new String[]{"nNoticeDay"};
		}
	/*	OBMagnifier.createPayerAccountNoCtrlByDate(out,
											sessionMng.m_lUserID,
											sessionMng.m_lCurrencyID,
											sessionMng.m_lOfficeID,
											financeInfo.getID(),
											sessionMng.m_lClientID,
											"nPayerAccountID",
											"dPayerCurrBalance",
											"dPayerUsableBalance",
											"tsExecute",
											"frm",
											NameRef.getAccountNoByID(obQueryInfo.getPayerAcctID()),
											"sPayerAccountNoZoom",
											"&nbsp;&nbsp;付款方账号",
											" width=\"300\" height=\"25\" class=\"MsoNormal\"",
											" width=\"300\" height=\"25\" ",nextfocus);*/
		%>
		<td width="130" height="25" align="left" nowrap>&nbsp;&nbsp;付款方账号：</td>
		<td width="430">
			<fs_c:dic id="sPayerAccountNoZoomCtrl" size="22" form="frm" title="付款方账号" sqlFunction="getPayerAccountNoSQLByDateDic"  sqlParams='frm.sPayerAccountNoZoomCtrl.value,frm.lUserID.value,frm.lClientID.value,frm.lCurrencyID.value,frm.InstructionID.value' value="<%=NameRef.getAccountNoByID(obQueryInfo.getPayerAcctID()) %>"  nextFocus="<%=nextfocus[0] %>" width="500">
				<fs_c:columns> 
					<fs_c:column display="账号"  name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="账户名称" name="sname" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="sPayerAccountNoZoomCtrl" name="saccountno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="nPayerAccountID" name="nAccountID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dPayerCurrBalance" name="dPayerCurrBalance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="dPayerUsableBalance" name="dPayerUsableBalance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="tsExecute" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="sName" name="sname" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="hiddenOpendate" name="dtopendate" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
		</td>
      <td width="5" class="MsoNormal"></td>
      <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>

<%
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
		{
%>	

	<tr class="MsoNormal">
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal" nowrap>
            <p><span class="MsoNormal">&nbsp;&nbsp;定期账号：</span></p>
      </td>
      <td width="590" height="25" class="MsoNormal">
      	 
		  <%SETTHTML.showFixedDepositAccountListCtrl(out,"fixAccountId",SETTConstant.AccountGroupType.FIXED,-1 ,false,"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal">
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal" nowrap>
            <p><span class="MsoNormal">&nbsp;&nbsp;定期存款期限：</span></p>
      </td>
      <td width="590" height="25" class="MsoNormal">
		  <%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime",SETTConstant.TransQueryType.FIXED,obQueryInfo.getFixedDepositTime(),false,"onfocus=\"nextfield='dAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>

<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>
			<TR >
			  <td width="5" height="25"  class="MsoNormal"></td>	
	          <TD height=20 width="20%" nowrap>&nbsp;&nbsp;是否自动续存：</TD>
	          <TD height=20 width="30%"><input <%=isautocontinue==1?"checked":"" %> type="checkbox" name="isautocontinue"  value="1"  onclick="changeContinueType(this)" /></TD>					
			  <TD height="20" width="16%"></TD>
			  <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
			</TR>
			<TR  style="display:<%=isautocontinue==1?"":"none" %>" id="isautocontinuetr1">
			<td width="5" height="25"  class="MsoNormal"></td>
	          <TD height=20 width="15%" nowrap>&nbsp;<INPUT <%=rdoInterest!=2?"checked":"" %>  name=rdoInterest type=radio onfocus="nextfield ='';" value="1"/>本利续存</TD>
	          <TD height=20 width="30%"></TD>					
			  <TD height="20" width="16%"></TD>
			  <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
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
	   		" style=\"width:20%\" height=\"25\" nowrap class=\"MsoNormal\"",
	   		" width=\"190\" height=\"25\" ");*/
		%>
		<td width="130" height="25" align="left" nowrap>&nbsp;<INPUT <%=rdoInterest==2?"checked":"" %> name=rdoInterest type=radio onfocus="nextfield ='';" value="2"/>利息转至活期账号：</td>
		<td width="430">
			<fs_c:dic id="lInterestToAccountID1Ctrl" size="22" form="frm" title="收款方账号" sqlFunction="getInterPayeeBankNOSQL"  sqlParams='true,frm.lClientID.value,frm.lCurrencyID.value,frm.lInterestToAccountID1Ctrl.value,frm.strReceiveInterestAccountClientName1.value' value="<%=sDepositInterestToAccountNO1%>"  nextFocus="dAmount" width="500">
				<fs_c:columns> 
					<fs_c:column display="收款方账号"  name="displayAccountNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs_c:column display="账户名称" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs_c:columns>
				<fs_c:pageElements>
					<fs_c:pageElement elName="lInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="lInterestToAccountID1" name="ID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="strReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					
					<fs_c:pageElement elName="sPayeeBankAccountNO" name="accountBankNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs_c:pageElement elName="hidlInterestToAccountID1Ctrl" name="spayeeacctno" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
					<fs_c:pageElement elName="hidstrReceiveInterestAccountClientName1" name="sPayeeName" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs_c:pageElements>							
			</fs_c:dic> 
		</td>
		<td nowrap width="27%" nowrap>&nbsp;&nbsp;
		活期客户名称：<input disabled class="box" type="text" name="strReceiveInterestAccountClientName1" value="<%=NameRef.getAccountNameByID(obQueryInfo.getAutocontinueaccountid())%>">
		</td>	
		<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>
 <%} %>
    <input type="hidden" name="lInterestToAccountID1" value="<%=obQueryInfo.getAutocontinueaccountid()>0?obQueryInfo.getAutocontinueaccountid():-1 %>">    
<%
		}
		else if (lTransType == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT)
		{
%>
    <tr class="MsoNormal">
       <td width="5" height="25" class="MsoNormal"></td>
       <td width="130" height="25" class="MsoNormal" nowrap>           
          <p><span class="MsoNormal">&nbsp;&nbsp;通知存款账号：</span></p>
       </td>
       <td width="300" height="25" class="MsoNormal">
       		
			<%SETTHTML.showFixedDepositAccountListCtrl(out,"notifyAccountId",SETTConstant.AccountGroupType.NOTIFY,-1 ,false,"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID);%>
       </td>
       <td width="5" height="25" class="MsoNormal"></td>
       <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>

    <tr class="MsoNormal">
       <td width="5" height="25" class="MsoNormal"></td>
       <td width="130" height="25" class="MsoNormal" nowrap>           
          <p><span class="MsoNormal">&nbsp;&nbsp;通知存款品种：</span></p>
       </td>
       <td width="300" height="25" class="MsoNormal">
		  <% SETTHTML.showFixedDepositMonthListCtrl(out,"nNoticeDay",SETTConstant.TransQueryType.NOTIFY,obQueryInfo.getNoticeDay(),false,"onfocus=\"nextfield='dAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
       </td>
       <td width="5" height="25" class="MsoNormal"></td>
       <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
    </tr>

<%
		}


%>
        <tr >
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="220" height="25" class="MsoNormal" nowrap>&nbsp;&nbsp;金额：</td>          
          <td height="25" width="300" class="MsoNormal">
            <fs:amount 
	       		form="frm"
       			name="dAmount"
       			value="<%=obQueryInfo.getAmount()%>"
       			nextFocus="tsExecute"
       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
          </td>
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="220" height="25" class="MsoNormal" nowrap>&nbsp;&nbsp;执行日：</td>
		  <td height="25" class="MsoNormal">
		  		<fs_c:calendar 
			          	    name="tsExecute"
				          	value="" 
				          	properties="nextfield =''" 
				          	size="20"/>
				  	  <script>
	          		$('#tsExecute').val('<%= (obQueryInfo.getExecuteDate() == null)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :obQueryInfo.getExecuteDate() %>');
	          	</script>
		  </td>
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="5" height="25" colspan="2"  class="MsoNormal"></td>
        </tr>
        <tr>
       		<td colspan="3"></td>          
       		<td align="right" colspan="3" nowrap>
				<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
				<input type="button" name="Submitv00204" value=" 匹 配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
				<input type="button" name="Submitv00204" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
			</td>
		</tr>
      </table>
	  
	  <input type="hidden" name="nPayerAccountID" size="16" value="<%= obQueryInfo.getPayerAcctID() %>" >
	   <% 
	   if(payeeInfo==null)
	   {
		   payeeInfo = new PayerOrPayeeInfo();
	   }
	   %>
	  <input type="hidden" name="nPayeeAccountID" value="<%=  payeeInfo.getAccountID() %>" >
	   
	  <input type="hidden" name="SelectType" value="<%= lTransType %>">
	
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	   
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
 </td>
  </tr>

</table>
</form>
 
<!--presentation end-->

<script language="Javascript">
    /* 匹配函数 */
    function matchme()
    {
		frm.action = "ck007-c.jsp";
		if (validate() == true)
        {
			/* 确认提交 */
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

	function goback(){
		window.location.href("ck001-v.jsp");
	}
    /* 校验函数 */
    function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */
		
		if (frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		
		if((frm.SelectType.value == <%=OBConstant.QueryInstrType.OPENFIXDEPOSIT%>) && (frm.nFixedDepositTime.value<0))
		{
			alert("请选择定期存款期限");
			frm.nFixedDepositTime.focus();
			return false;
		}
		
		if((frm.SelectType.value == <%=OBConstant.QueryInstrType.OPENNOTIFYACCOUNT%>) && (frm.nNoticeDay.value<0))
		{
			alert("请选择通知存款品种");
			frm.nNoticeDay.focus();
			return false;
		}

<%
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
		{
			if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
			{ 
%> 
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
<%
			}
		}
%>
		
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
		if(!CompareDateString(frm.hiddenOpendate.value,frm.tsExecute.value))
		{
		alert("执行日不能小于系统开机日！");
		form.tsExecute.focus();
		return false;
		}
		
		return true;
    }

<%
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
		{
			if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
			{ 
%> 
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
		}
%>		    
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frm.sPayerAccountNoZoomCtrl);
	//setSubmitFunction("matchme(frm)");
	setFormName("frm");

<%
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
		{
			if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	
			{ 
%>
 	changeContinueType(frm.isautocontinue);
 <%
 			}
 		}
 %>

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