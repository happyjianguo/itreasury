<%--
/*
 * 程序名称：c004-v.jsp
 * 功能说明：资金划拨提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
                 com.iss.itreasury.util.*,
                 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"
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
	String strTitle = "[资金申请确认]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    
	long lSourceType = 0;//头信息显示
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
	
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
	
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
        /* 用户登录检测 
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
        }*/

		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		long l = financeInfo.getRemitType();
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
	ClientAccountInfo accountInfo=null;	
	accountInfo=(ClientAccountInfo)request.getAttribute("accountInfo");		
		
	if (accountInfo==null) accountInfo=new ClientAccountInfo();
	
	String strPayerAccountNo = 	financeInfo.getPayerAcctNo();
	String strPayeeAccountNo = 	financeInfo.getPayeeAcctNo();
	//SEFC新增
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<% 
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) 
		{
%>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="560"height="25" colspan="3" class="FormTitle"><font size="3" color="#FFFFFF" >资金划拨确认</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        资金申领将于复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <br>
              该笔交易有待复核人复核！
			  <br>
              <br>
              <b>指令序号为：<%= financeInfo.getID() %></b><br>
              <br>
            </p>
            </td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
<%
		}
%>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr  class="tableHeader">
          
          <td width="560"height="25" class="FormTitle" colspan="4">
		  <font size="3" color="#FFFFFF" >申领方资料</font>
		  </td>
      
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">客户名称：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getPayerName() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">申领方账户：</td>
          <td width="430" height="25" class="MsoNormal"><%= strPayerAccountNo %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">银行账户：</td>
          <td width="430" height="25" class="MsoNormal"><%= accountInfo.getReceiveAccountNo() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">可申领余额：</td>
          <td width="430" height="25" class="MsoNormal">
		  <%= DataFormat.formatDisabledAmount(accountInfo.getCanUseBalance()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="80%" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr  class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="560"height="25" class="FormTitle" colspan="5"><font size="3" color="#FFFFFF" class="whitetext">资金申领资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">申领金额：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
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
			<%=ChineseCurrency.showChinese(String.valueOf(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>	
      		</td>
	      <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">备注：</td>
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getNote()==null)?"":financeInfo.getNote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <% if (financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE){%>
	<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">失败原因：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getReturnMsg() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%}%>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝      		   
		{ 
%>
      <table width="80%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="tableHeader">
          
    <!--td bgcolor="#0C3869" width="4" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <td height="22" colspan="4" class="FormTitle" ><font size="3" ><b>交易申请处理详情</b></font></td>
    	 </tr>        
      </table>
      <table width="80%" border="0" class="ItemList">
        <tr class="tableHeader">
          <td height="19" width="10%"  align="center" class="ItemTitle">
            <div align="center"><font size="2" >序列号</font></div>
          </td>
          
          <td height="19" class="ItemTitle" width="30%" align="center">
           用户
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">时间和日期</div>
          </td>
        </tr>
       
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">1</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% 
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
        <tr valign="middle">
          <td width="10%" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="graytext">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">复核</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        
<% 
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="10%" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="graytext">3</div>
          </td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">签认</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				} 
%>
<%
 			} 
%>
 </table>
 <br>
<%
 		} 
%>

	  <br>
      <form name="frmzjhb" method="post">
	  <table border="0" width="80%" cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="80%" align="right"> 
        <%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>
        <!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
        <!--打印委托付款凭证-->
        <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();"> 
        <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"--> 
		<input class=button name=add type=button value=" 修 改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">
		<input class=button name=add type=button value=" 删 除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
        <% if ( lSourceType != 1 &&  lShowMenu != OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class=button name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
        <%}%>
        <% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"--> 
		<input class=button name=add type=button value=" 复 核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
        <% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"--> 
		<input class=button name=add type=button value=" 复 核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
        <% }%>
        <% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
		<input type="Button" class="button" value="关 闭" width="46" height="18"   onclick="window.close();"> 
        <%}%>
      </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>

	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	  </form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("../../common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}


	/* 菜单控制处理函数 */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frmzjhb.menu.value="hidden";
		<%  }   %>
	}
	/*返回处理函数 */
	function returnme()
	{
		frmzjhb.lInstructionID.value = "";
		frmzjhb.action="../captransfer/c011-c.jsp";
		frmzjhb.submit();
	}

	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frmzjhb.action="../captransfer/C13.jsp";
		frmzjhb.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frmzjhb.action="../captransfer/c011-c.jsp";
		frmzjhb.submit();
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		//showMenu();
		frmzjhb.action="../captransfer/c015-c.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frmzjhb.action="../check/ck012-v.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}	
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		frmzjhb.action="../check/C015.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frmzjhb.action="../check/C415.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frmzjhb.action="../sign/s004-c.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
	}

</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>