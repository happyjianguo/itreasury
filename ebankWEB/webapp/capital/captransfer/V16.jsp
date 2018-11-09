<%--
/*
 * 程序名称：V16.jsp
 * 功能说明：资金划拨修改输入页面
 * 作　　者：刘琰
 * 完成日期：2003年09月17日
 */
--%>

<!--Header start-->


<%@ page contentType = "text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%@ include file="/magnifier/MagnifierSQL.jsp" %>
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
	
%>
<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;
	
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
%>

<script language="JavaScript" src="/webob/Control.js"></script>
<script language="JavaScript" src="/webob/Check.js"></script>

<safety:resources />

<form method="post" name="frmzjhb">
     <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td>
          <td width="720"height="25" bgcolor="#456795" colspan="2"><font size="3" color="#FFFFFF" class="whitetext">付款方资料</font></td>
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
          <td width="590" height="25" bgcolor="#C8D7EC" class="box">
            <input type="text" class="rebox" name="sPayerName" size="30" value="<%=(financeInfo.getID() == -1) ? sessionMng.m_strClientName : financeInfo.getPayerName() %>" readonly>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>

		<!--a try for glass start-->
		<tr bgcolor="#C8D7EC">
		<td width="5" height="25"></td>
<%
		//付款方账号放大镜
		OBHtmlCom.createBankAccountCodeCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"dPayerCurrBalance","dPayerUsableBalance","frmzjhb",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","付款方账号"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>		
		<td width="5"></td>
		</tr>
		<tr bgcolor="#FFFFFF">
        <td colspan="4" height="1"></td>
        </tr>
		<!--a try for glass end-->
		<!--
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">付款方账号：</td>
          <td width="430" height="25" class="box">
		<input type="text" class="rebox" name="sPayerAcctNo" size="16" value="<%= financeInfo.getPayerAcctNo() %>" readonly>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
		-->
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>

          <td width="130" height="25" class="graytext">当前余额：</td>
          <td width="590" height="25" class="box">
		<input type="text" class="amountbox" name="dPayerCurrBalance" size="16" value="<%= sPayerCurrentBalance %>" readonly>
		<font class="graytext" >
		可用余额：
		</font>
		<input type="text" class="amountbox" name="dPayerUsableBalance" size="16" value="<%= sPayerUsableBalance %>" readonly>
		  </td>
          <td width="5" colspan="4"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="720"height="25" bgcolor="#456795"><font size="3" color="#FFFFFF" class="whitetext">收款方资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25">
            <p><span class="graytext">汇款方式：</span></p>
          </td>
          <td width="590" height="25">
           <input type="hidden" name="nRemitTypeHidden" value="<%= financeInfo.getRemitType() %>">
<%
			OBHtmlCom.showRemitTypeListControl(out,"nRemitType",financeInfo.getRemitType(),(financeInfo.getID() == -1)?"onchange=\"jump();\" onfocus=\"nextfield='getNextField';\" ":"readonly",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
		  </td>
          <td width="1" height="25"></td>
        </tr>
      </table>
		   
	  <!--汇款方式动态显示收款方资料-->

	  <table width="730" border="0" cellspacing="0" cellpadding="0">

		 <tr id="payeeName">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">收款方名称：</td>
          <td height="25" width="590" colspan="2" class="graytext">
            <input type="text" class="rebox" name="sPayeeName" value="<%=(financeInfo.getID() == -1)?sessionMng.m_strClientName : financeInfo.getPayeeName() %>" size="24" readonly>
              </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeBankNoZoom">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//收款方账号放大镜（本转）
		OBHtmlCom.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"sPayeeNameZoomBankCtrl","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeBankNoZoom","收款方账号"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");
%>	 
		 		 																																												
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNoZoomLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeNameZoomBank">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
		  <td height="25" width="130" class="graytext">收款方名称：</td>
		  <td height="25" colspan="2" class="graytext">
		  	<input type="Text" name="sPayeeNameZoomBankCtrl" value="<%= financeInfo.getPayeeName() %>" >
		  </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameZoomBankLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<tr id="payeeBankNoZoomInternal">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//收款方账号放大镜（内部转账）
		OBHtmlCom.createBankAccountCodeCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"","","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAccountInternal","收款方账号"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>			 
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNoZoomInternalLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		<!--
        <tr id="payeeAcctNo">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="128" class="graytext">收款方账号：</td>
          <td height="25" colspan="2" class="graytext">
            <input type="text" class="rebox" name="sPayeeAcctNo" value="<%= financeInfo.getPayeeAcctNo() %>" size="30" readonly>
          </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeAcctNoLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		-->
		 <tr id="payeeAcctNoZoom">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
<%
		//收款方账号放大镜（汇）
		OBHtmlCom.createPayeeAccountNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"sPayeeNameZoomAcctCtrl","sPayeeProv","sPayeeCity","sPayeeBankName","frmzjhb",financeInfo.getPayeeAcctNo(),"sPayeeAcctNoZoom","收款方账号"," width=\"130\" height=\"25\" class=\"graytext\""," width=\"590\" height=\"25\" class=\"box\"");	
%>	

          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeAcctNoZoomLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeNameZoomAcct">
          <td bgcolor="#C8D7EC" height="25" width="2"></td>
          <td height="25" width="5"></td>
		  <td height="25" width="130" class="graytext">收款方名称：</td>
		  <td height="25" colspan="2" class="graytext">
		  	<input type="text" name="sPayeeNameZoomAcctCtrl" value="<%= financeInfo.getPayeeName() %>" onfocus="nextfield ='sPayeeProv';">
		  </td>
          
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeNameZoomAcctLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<tr id="payeePlace">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">汇入地：</td>
          <td height="25"  class="graytext">
            <input type="text" name="sPayeeProv" value="<%=  financeInfo.getPayeeProv() %>" size="10" onfocus="nextfield ='sPayeeCity';">
            省
            <input type="text" name="sPayeeCity" value="<%=  financeInfo.getPayeeCity() %>" size="10" onfocus="nextfield ='sPayeeBankName';">
            市 </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeePlaceLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>

		<tr id="payeeBankNameRead">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">汇入行名称：</td>
          <td height="25" colspan="2" class="graytext">
			<input type="text" class="rebox" name="sPayeeBankNameRead" value = "<%= (financeInfo.getID() == -1)?Env.getClientName():financeInfo.getPayeeBankName() %>" size="30" readonly>
          </td>
		<td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeBankName">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="130" class="graytext">汇入行名称：</td>
          <td height="25" colspan="2" class="graytext">
            <input type="text" name="sPayeeBankName" value="<%=  financeInfo.getPayeeBankName() %>" size="30" onfocus="nextfield ='dAmount';">
          </td>
        <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
		<tr id="payeeBankNameLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		
		<!--
		<tr id="payeeBalance">
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
          <td height="25" width="5"></td>
          <td height="25" width="128" class="graytext">当前余额：</td>
          <td height="25"  class="graytext">
            
          </td>
          <td height="25"  class="graytext">
		可用余额：
            
          </td>
          <td bgcolor="#C8D7EC" height="25" width="1"></td>
        </tr>
        <tr id="payeeBalanceLine">
          <td bgcolor="#C8D7EC" height="1" colspan="6"></td>
        </tr>
		-->
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#456795">
          <td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\blue_top_left.gif" width="3" height="3"></td>
          <td width="720 "height="25" bgcolor="#456795" colspan="3"><font size="3" color="#FFFFFF" class="whitetext">划款资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="110" height="25" bgcolor="#C8D7EC" class="graytext">金额：</td>
          <td width="20" height="25" bgcolor="#C8D7EC" class="graytext">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td height="25" width="590" bgcolor="#C8D7EC" class="box">
            <script  language="JavaScript">
				createAmountCtrl("frmzjhb","dAmount","<%= financeInfo.getFormatAmount() %>","tsExecute","",1);
			</script>
          </td>
          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" bgcolor="#C8D7EC" class="graytext" colspan="2">执行日：</td>
          <td height="25" bgcolor="#C8D7EC" class="box">
           		<fs_c:calendar 
	          	    name="tsExecute"
		          	value="" 
		          	properties="nextfield ='sNote'" 
		          	size="12"/>
		        <script>
	          		$('#txtExecuteA').val('<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(1,1)) :financeInfo.getFormatExecuteDate() %>');
	          	</script>
		  </td>

          <td width="5" height="25"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">汇款用途：</td>
          <td height="25" class="box" nowrap>
		  <textarea name="sNote" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>200) this.value=this.value.substr(0,200)" onFocus="nextfield ='';"><%= financeInfo.getNote() %></textarea>
			<font color="red">(不超过50个字)</font>
          </td>
          <td width="5"></td>
        </tr>
        <tr bgcolor="#FFFFFF">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right"><img src="\webob\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"></div>
          </td>
          <td width="62">
            <div align="right"><img src="\webob\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"></div>
          </td>
        </tr>
      </table>

	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	  
</form>
<!--presentation end-->

<script language="Javascript">

	/*
	 * 数据校验、表单显示控制及FORM提交
	 * javascript
	 */

	/* 汇款方式 */
	var iRemitType = frmzjhb.nRemitType.value;
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
		iRemitType = frmzjhb.nRemitType.value;
		
		payeeName.style.display = "none";
		payeeNameLine.style.display = "none";
		payeeNameZoomBank.style.display = "none";
		payeeNameZoomBankLine.style.display = "none";
		payeeNameZoomAcct.style.display = "none";
		payeeNameZoomAcctLine.style.display = "none";
		/* 收款方银行账号 */
		payeeBankNoZoom.style.display = "none";
		payeeBankNoZoomLine.style.display = "none";
		payeeBankNoZoomInternal.style.display = "none";
		payeeBankNoZoomInternalLine.style.display = "none";
		/* 收款方账号 */
		//payeeAcctNo.style.display = "none";
		//payeeAcctNoLine.style.display = "none";
		payeeAcctNoZoom.style.display = "none";
		payeeAcctNoZoomLine.style.display = "none";
		/*  汇入行名称 */
		payeeBankName.style.display = "none";
		payeeBankNameRead.style.display = "none";
		payeeBankNameLine.style.display = "none";
		/* 汇入地 */
		payeePlace.style.display = "none";
		payeePlaceLine.style.display = "none";
		/* 当前余额 */
		//payeeBalance.style.display = "none";
		//payeeBalanceLine.style.display = "none";
		/*汇款用途*/
		frmzjhb.sNote.value = "";
		
		if( frmzjhb.lInstructionID.value > 0)
		{
			frmzjhb.sNote.value = "<%=financeInfo.getNote()%>";
		}
		else
		{
			frmzjhb.sNote.value = "<%=sessionMng.m_strClientShortName%>";
		}

		/* 根据汇款方式确定所显示的TR */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>) // 汇款方式本转
		{
			/* 带放大镜、和收款方银行账号有关联的收款方名称 */
			payeeNameZoomBank.style.display = "";
			payeeNameZoomBankLine.style.display = "";
			/* 带放大镜的收款方银行账号 */
			payeeBankNoZoom.style.display = "";
			payeeBankNoZoomLine.style.display = "";
			/* 不带放大镜的收款方账号 */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/*  汇入行名称 */
			payeeBankNameRead.style.display = "";
			payeeBankNameLine.style.display = "";
			
			/* 根据汇款方式改变汇款用途 */
			//frmzjhb.sNote.value = "本转";
		}
		if((iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  || // 汇款方式电汇
			(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>) || // 汇款方式信汇
			(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)) // 汇款方式票汇
		{
			/* 带放大镜、和收款方账号有关联的收款方名称 */
			payeeNameZoomAcct.style.display = "";
			payeeNameZoomAcctLine.style.display = "";
			/* 带放大镜的收款方账号 */
			payeeAcctNoZoom.style.display = "";
			payeeAcctNoZoomLine.style.display = "";
			/* 汇入地 */
			payeePlace.style.display = "";
			payeePlaceLine.style.display = "";
			/*  汇入行名称 */
			payeeBankName.style.display = "";
			payeeBankNameLine.style.display = "";
			
			/* 根据汇款方式改变汇款用途 */
			if(iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)
			{
				// frmzjhb.sNote.value="电汇";
			}
			if(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>)
			{
				//frmzjhb.sNote.value="信汇";
			}
			if(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>)
			{
				//frmzjhb.sNote.value="票汇";
			}
		}

		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>) // 汇款方式内部转账
		{
			/* 不带放大镜的收款方名称 */
			payeeName.style.display = "";
			payeeNameLine.style.display = "";
			/* 带放大镜的收款方银行账号 */
			payeeBankNoZoomInternal.style.display = "";
			payeeBankNoZoomInternalLine.style.display = "";
			 
			/* 不带放大镜的收款方账号 */
			//payeeAcctNo.style.display = "";
			//payeeAcctNoLine.style.display = "";
			/* 当前余额 */
			//payeeBalance.style.display = "";
			//payeeBalanceLine.style.display = "";
			
			/* 根据汇款方式改变汇款用途 */
			//frmzjhb.sNote.value="内部转账";
		}
	}
	
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
			  else
			  {
                  frmzjhb.sPayeeAcctNoZoomCtrl.focus();
              }   
              //<<<<add by shiny 20030403
    }

    /* 修改提交处理函数 */
    function addme()
    {
        
		frmzjhb.action = "C11.jsp";
		if (validate() == true)
        {
			/* 确认提交 */
<%--			if (!confirm("是否提交？"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
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
				frmzjhb.action="";
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
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        */

		/* 付款方资料非空校验 */
		if (frmzjhb.sPayerName.value == "")
		{
			alert("付款方名称不能为空");
			frmzjhb.sPayerName.focus();
			return false;
		}
		
		if (frmzjhb.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择付款方账号");
			frmzjhb.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		

		/* 根据汇款方式对收款方资料进行非空校验 */
		if(iRemitType == <%= OBConstant.SettRemitType.SELF %>)// 汇款方式本转
		{
			if (frmzjhb.sPayeeNameZoomBankCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeNameZoomBankCtrl.focus();
				return false;
			}
			if (frmzjhb.sPayeeBankNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeBankNoZoomCtrl.focus();
				return false;
			}
			
	

		}
		if((iRemitType == <%= OBConstant.SettRemitType.BANKPAY %>)  || // 汇款方式电汇
			(iRemitType == <%= OBConstant.SettRemitType.FINCOMPANYPAY %>) || // 汇款方式信汇
			(iRemitType == <%= OBConstant.SettRemitType.PAYSUBACCOUNT %>))// 汇款方式票汇
		{
			
			if (frmzjhb.sPayeeNameZoomAcctCtrl.value == "")
			{
				alert("请选择收款方名称或账号");
				frmzjhb.sPayeeNameZoomAcctCtrl.focus();
				return false;
			}
		
			if (frmzjhb.sPayeeAcctNoZoomCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeAcctNoZoomCtrl.focus();
				return false;
			}

		}
		if(iRemitType == <%= OBConstant.SettRemitType.INTERNALVIREMENT %>)// 汇款方式内部转账
		{
			if (frmzjhb.sPayeeName.value == "")
			{
				alert("收款方名称不能为空");
				frmzjhb.sPayeeName.focus();
				return false;
			}
			if (frmzjhb.sPayeeAccountInternalCtrl.value == "")
			{
				alert("请选择收款方账号");
				frmzjhb.sPayeeAccountInternalCtrl.focus();
				return false;
			}
		}

		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(frmzjhb.dAmount, 1, "交易金额"))
		{
			return false;
		}

		/* 执行日校验 */
		if (!checkInterestExecuteDate(frmzjhb.tsExecute,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
		{
			return false;
		}

		/* 业务校验 */
		/* 可用余额－交易金额 */
		var dBalance = parseFloat(reverseFormatAmount(frmzjhb.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frmzjhb.dAmount.value)) ;
		//add by sun for test
		/*comment by shiny 2003-03-11
		//alert("以下是调试信息");
		//alert("透支限额=" + frmzjhb.sPayerBankNoZoom_OVAmount.value);
		//alert("可用余额－交易金额＋透支限额=" + dBalance)
		
				alert("可用余额=" + frmzjhb.dPayerUsableBalance.value);
				alert("交易金额=" + frmzjhb.dAmount.value);
				alert("透支限额=" + frmzjhb.sPayerBankNoZoom_OVAmount.value);
				alert("最大可发放金额=" + frmzjhb.sPayerBankNoZoom_MaxAmount.value);
				*/
				//alert("可用余额－交易金额＋透支限额＋最大可发放金额=" + dFinalBalance);
		//add end
		
		
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入划拨金额");
			frmzjhb.dAmount.focus();
			return false;
		}

    	return true;
    }
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	setGetNextFieldFunction("getNextField(frmzjhb)");
	firstFocus(frmzjhb.sPayerAccountNoZoomCtrl);
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