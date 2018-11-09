<%--
/*
 * 程序名称：V12.jsp
 * 功能说明：资金划拨提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2003年09月19日
 */
--%>

<!--Header start-->
<%@ include file="/Header.jsp" %>
<%@ include file="/common/common.jsp" %>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
                 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*"
%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[资金划拨]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
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
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/OBZoom.js"></script>
<script language="JavaScript" src="/webob/OBCheck.js"></script>

<safety:resources />

<% 
		if (lSourceType != 1) 
		{
%>
	  <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">资金划拨确认</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="3" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td height="25" bgcolor="#C8D7EC">
            <p><font size="3">资金划拨-<%= financeInfo.getFormatRemitType() %>将于复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！</font><font size="3"><br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <br>
              该笔交易有待复核人复核！
			  <br>
              <br>
              <b>指令序号为：<%= financeInfo.getID() %></b></font><br>
              <br>
            </p>
            </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
<%
		}
%>
	  <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">付款方资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext">付款方名称：</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="graytext"><%= financeInfo.getPayerName() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
		<!--
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">银行账号：</td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayerBankNo() %></td>
          <td width="5"></td>
        </tr>
		-->
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">付款方账号：</td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayerAcctNo() %></td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">收款方资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25">
            <p><span class="graytext">汇款方式：</span></p>
          </td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getFormatRemitType() %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>
           
		<% long l = financeInfo.getRemitType(); %>
		<% if ((l == OBConstant.SettRemitType.SELF) || (l == OBConstant.SettRemitType.INTERNALVIREMENT)) { %>
      <!--
	  <table width="570" border="0" cellspacing="0" cellpadding="0">
       <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>收款方银行账号：</p>
          </td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayeeBankNo() %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>
	  -->
	  <% } %>

      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>收款方账号：</p>
          </td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayeeAcctNo() %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>
	  
	   <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
	  <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>收款方名称：</p>
          </td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayeeName() %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>

<% 
		if ((l == OBConstant.SettRemitType.BANKPAY) || (l == OBConstant.SettRemitType.FINCOMPANYPAY) || (l == OBConstant.SettRemitType.PAYSUBACCOUNT)) 
		{ 
%>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>汇入地：</p>
          </td>
          <td width="430" height="25" class="graytext"><%= ((financeInfo.getPayeeProv() == null) ? "　" : financeInfo.getPayeeProv()) + "省" + ((financeInfo.getPayeeCity() == null) ? "　" : financeInfo.getPayeeCity()) + "市" %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>
<%
		}
%>

<% 
		if (l != OBConstant.SettRemitType.INTERNALVIREMENT) 
		{ 
%>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>汇入行名称：</p>
          </td>
          <td width="430" height="25" class="graytext"><%= (financeInfo.getPayeeBankName() == null) ? "" : financeInfo.getPayeeBankName() %></td>
          <td width="1" height="25"></td>
        </tr>
      </table>
<%
		} 
%>

      <br>
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="560"height="25" bgcolor="#456795" colspan="3"><font size="3" color="#FFFFFF" class="whitetext">划款资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td height="25" bgcolor="#C8D7EC" class="graytext" width="110">金额：</td>
          <td width="20" height="25" bgcolor="#C8D7EC" class="graytext">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="graytext"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext" colspan="2">执行日：</td>
          <td width="430" height="25" bgcolor="#C8D7EC" class="graytext"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">汇款用途：</td>
          <td width="430" height="25" onkeypress="if (this.value.length>50) event.keyCode=0;" class="graytext"><%= financeInfo.getNote() %></td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
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
      <table width="570" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td>
          <td colspan="2"height="22" bgcolor="#0C3869"><font size="3" color="#FFFFFF" class="whitetext"><b>交易申请处理详情</b></font></td>
          <td width="5" height="22" bgcolor="#0C3869"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="570" border="0" cellspacing="0" cellpadding="0" bgcolor="#FF6600">
        <tr>
          <td width="50" bgcolor="#456795" height="19">
            <p align="center"><font size="2" class="whitetext">序列号</font></p>
          </td>
          <td bgcolor="#FFFFFF" height="19" valign="bottom" width="1"></td>
          <td bgcolor="#456795" height="19" valign="middle" class="whitetext" width="150">
            <div align="center">用户</div>
          </td>
          <td bgcolor="#FFFFFF" height="19" valign="middle" class="whitetext" width="1"></td>
          <td bgcolor="#456795" height="19" valign="middle" class="whitetext" width="150">
            <div align="center">工作描述</div>
          </td>
          <td bgcolor="#FFFFFF" height="19" valign="middle" class="whitetext" width="1"></td>
          <td bgcolor="#456795" height="19" valign="middle" class="whitetext" width="217">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        <tr>
          <td colspan="10" height="1" bgcolor="#FFFFFF"></td>
        </tr>
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="graytext" height="25">
            <div align="center">1</div>
          </td>
          <td bgcolor="#FFFFFF" width="1" class="graytext" height="25">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          <td bgcolor="#FFFFFF" class="graytext" width="1" height="25"></td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center">录入</div>
          </td>
          <td bgcolor="#FFFFFF" width="1" class="graytext" height="25">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="215" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        <tr>
          <td colspan="10" height="1" bgcolor="#FFFFFF"></td>
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
          <td width="50" align="left" bgcolor="#C8D7EC" class="graytext" height="25">
            <div align="center" class="graytext">2</div>
          </td>
          <td width="1" align="left" class="graytext" height="25" bgcolor="#FFFFFF">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          <td bgcolor="#FFFFFF" class="graytext" width="1" height="25"></td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center">复核</div>
          </td>
          <td bgcolor="#FFFFFF" width="1" class="graytext" height="25">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="217" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <tr>
          <td colspan="10" height="1" bgcolor="#FFFFFF"></td>
        </tr>
<% 
				if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝
         		{ 
%>
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="graytext" height="25">
            <div align="center" class="graytext">3</div>
          </td>
          <td width="1" align="left" class="graytext" height="25" bgcolor="#FFFFFF">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          <td bgcolor="#FFFFFF" class="graytext" width="1" height="25"></td>
          <td bgcolor="#C8D7EC" class="graytext" width="150" height="25">
            <div align="center">签认</div>
          </td>
          <td bgcolor="#FFFFFF" width="1" class="graytext" height="25">
            <div align="center"></div>
          </td>
          <td bgcolor="#C8D7EC" class="graytext" width="217" height="25">
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
	  <table border="0" width="570" cellspacing="0" cellpadding="0">
        <tr>
		<td width="570" align="right">

<%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

<!--
         <img src="\webob\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->

		   <!--打印委托付款凭证-->
		   <img src="\webob\button_dywtfkpz.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()">
		   
           <img src="\webob\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">


            <img src="\webob\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();">
            
            <% if ( lSourceType != 1) {%>
            <img src="\webob\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()">
            <%}%>

		<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID())) {// 已确认、登录人<>确认人 %>

           		<img src="\webob\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();">

		  <% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// 已复核、登录人＝复核人 %>

           		<img src="\webob\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();">

		<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已复核、登录人＝签认人 %>

            	 <img src="\webob\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();">

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已签认、登录人＝签认人 %>

            	 <img src="\webob\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();">

	      <%
	       } 
              if(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
               { 
               %>
		   <img src="\webob\button_dywtfkpz.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()">
               <!--
            <img src="\webob\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
            <%}%>
			<img src="\webob\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();">
         </td>
        </tr>
      </table>

	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="menu">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">

	  </form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("../finance/SConsignVoucherPrintPage.jsp?control=view&&popxr=asdfl&lID=<%=financeInfo.getID()%>&strPrintPage=4");
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
		frmzjhb.action="../finance/C15.jsp";
		frmzjhb.submit();
	}

	/* 确认处理函数 */
	function confirmme()
	{
		showMenu();
		frmzjhb.action="../finance/C13.jsp";
		frmzjhb.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		showMenu();
		frmzjhb.action="../finance/C15.jsp";
		frmzjhb.submit();
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		showMenu();
		frmzjhb.action="../finance/C17.jsp";
		frmzjhb.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		showMenu();
		frmzjhb.action="../query/C415.jsp";
		frmzjhb.txtisCheck.value = "1";
		frmzjhb.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消吗？"))
		{
			showMenu();
			frmzjhb.action="../query/C415.jsp";
			frmzjhb.txtisCheck.value = "0";
			frmzjhb.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		showMenu();
		frmzjhb.action="../query/C615.jsp";
		frmzjhb.txtisCheck.value = "1";
		frmzjhb.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			showMenu();
			frmzjhb.action="../query/C615.jsp";
			frmzjhb.txtisCheck.value = "0";
			frmzjhb.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/finance/S00-Ipt.jsp";
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