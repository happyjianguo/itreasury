<%--
/*
 * 程序名称：c002-v.jsp
 * 功能说明：资金划拨修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>
 
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
					java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%!
	/* 标题固定变量 */
	String strTitle = "[资金申请]";
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
	ClientAccountInfo accountInfo=null;
	
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";


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
		accountInfo=(ClientAccountInfo)request.getAttribute("accountInfo");
		
		long lChild = GetNumParam(request,"child");
		
		
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
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		if (accountInfo==null) 
	{
		accountInfo=new ClientAccountInfo();
%>
	<script language="javascript">
		alert("客户的账户信息设置有误，请先检查账户设置信息");
		window.history.back();
	</script>
<%
	}
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">
     <table width="80%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr bgcolor="#456795" class="tableHeader">
          <!--td bgcolor="#456795" width="4" valign="top" align="left" height="25">
		  <img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td height="25" bgcolor="#456795" colspan="4" class=FormTitle>
		  <font size="3" color="#FFFFFF" >申领方资料</font>
		  </td>
          <!--td width="129" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="MsoNormal">
		  <td width="4" height="25" class="MsoNormal"></td>
          <td width="140" height="25" class="MsoNormal">客户名称：</td>
          <td width="457" height="25" class="MsoNormal">
            <input type="text" class="box" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? sessionMng.m_strClientName : financeInfo.getPayerName() %>" readonly>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>		
        <tr  class="MsoNormal">
          <td width="4" height="25" class="MsoNormal"></td>
          <td width="160" height="25" class="MsoNormal">申领方账户：</td>
          <td width="457" height="25" class="MsoNormal">
		<input type="hidden" name="nPayerAccountID" value="<%=accountInfo.getPayAccountID()%>" >
		<input type="text" class="" name="sPayerAccountNoZoomCtrl" size="16" 
		value="<%= accountInfo.getPayAccountNo() %>" readonly>
		  </td>
          <td width="129" colspan="4" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr  class="MsoNormal">
		  <td width="4" height="25" class="MsoNormal"></td>
          <td width="140" height="25" class="MsoNormal">银行账户：</td>
          <td width="457" height="25" class="MsoNormal">
            <input type="text" class="" name="sPayeeBankAcctNo" size="30" 
			value="<%=accountInfo.getReceiveAccountNo()%>" readonly>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr  class="MsoNormal">
		  <td width="4" height="25" class="MsoNormal"></td>
          <td width="140" height="25" class="MsoNormal">可申领余额：</td>
          <td width="457" height="25" class="MsoNormal">
            <input type="text" class="amountbox" size="30"  name="balance"
			value="<%=DataFormat.formatDisabledAmount(accountInfo.getCanUseBalance())%>" readonly>
          </td>
          <td width="129" height="25" class="MsoNormal"></td>
        </tr>
        <tr  class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
  <br>
  <table width="80%" border="0" cellspacing="0" cellpadding="0" class=top>
    <tr class="tableHeader"> 
      <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
      <td width="720 "height="25" bgcolor="#456795" colspan="5"class="FormTitle"><font size="3" color="#FFFFFF" >资金申领资料</font></td>
      <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="110" height="25"class="MsoNormal">申领金额：</td>
      <td width="20" height="25" class="MsoNormal"> <div align="right"><%= sessionMng.m_strCurrencySymbol %></div></td>
      <td height="25" width="590" class="MsoNormal"> 
	  <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%= financeInfo.getFormatAmount() %>","sNote","sChineseAmount","<%=sessionMng.m_lCurrencyID%>");
	</script> </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal" colspan="2">
	  大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
      <td height="25" class="MsoNormal"> 
	  <input type="text" class="rebox" name="sChineseAmount" size="30" 
	  value="<%=ChineseCurrency.showChinese(String.valueOf(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" 
	  readonly>	
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">备注：</td>
          <td height="25" class="MsoNormal" nowrap>
		 
            <textarea name="sNote" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onFocus="nextfield ='';"><%=(financeInfo.getNote()==null)?"":financeInfo.getNote() %></textarea>
			
			
        <font color="red">&nbsp;</font>
          </td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
  </table>
      <br>
      <table width="80%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button name=addButton type=button value=" 提 交 " onClick="Javascript:addme();" > 
			</div>
          </td>
		  <td width="9" height="17"></td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button name=add type=button value=" 取 消 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
			</div>
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
%>
	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
		<input type="hidden" name="clickCount" value="<%=strClickCount%>">
	  	<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
</form>
<script language="Javascript">
		
	function getNextField()
	{
              //>>>>add by shiny 20030403
      	      var iRemitType = frmzjhb.nRemitType.value;
			  if (iRemitType == -1)
			  {//没有选择
			  	  alert("请选择汇款方式");
				  frmzjhb.nRemitType.focus();  	
			  }
              else if (iRemitType== <%=OBConstant.SettRemitType.INTERNALVIREMENT%> )
			  {//内部转账
                  frmzjhb.sPayeeAccountInternalCtrl.focus();
              }else if(iRemitType== <%=OBConstant.SettRemitType.SELF%>) 
			  {//本转
                  frmzjhb.sPayeeBankNoZoomCtrl.focus();
              }
			  else if(iRemitType== <%=OBConstant.SettRemitType.OTHER%>) 
			  {//其他
                  frmzjhb.dAmount.focus();
              }
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* 修改提交处理函数 */
    function addme()
    {
        
		frmzjhb.action = "c013-c.jsp?child=<%=lChild%>";
		if (validate() == true)
        {
			/* 确认提交 */
<%--			if (!confirm("是否提交？"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
			showSending();
			frmzjhb.clickCount.value = parseInt(frmzjhb.clickCount.value) +1 ;
            frmzjhb.submit();
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		if (frmzjhb.lInstructionID.value == -1)
		{	
			if (confirm("确定取消吗？"))
			{
				frmzjhb.action="c011-c.jsp";
				frmzjhb.submit();
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
		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.dAmount, 1, "申领金额"))
		{
			return false;
		}

		/* 汇款用途 
		if (!InputValid(frmzjhb.sNote, 0, "textarea", 1, 0, 100,"汇款用途"))
		{
			return false;
		}*/
		/* 业务校验 */
		/* 可用余额－交易金额 */
		<% if (financeInfo.getID()> 0)
		{%>
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.balance.value)) + <%=financeInfo.getAmount()%>-
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		<%}
		else{
		%>
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.balance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		<%}%>	
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("可申领余额不足，请重新输入申领金额");
			frmzjhb.dAmount.focus();
			return false;
		}
		
    	return true;
    }
	
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	firstFocus(frmzjhb.dAmount);
	//setSubmitFunction("addme(frmzjhb)");
	setFormName("frmzjhb");
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