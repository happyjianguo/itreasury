<!--
/*
 * 程序名称：ck011-v.jsp
 * 功能说明：到期续存修改输入页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
	               com.iss.itreasury.settlement.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.bizdelegation.*,
				   com.iss.itreasury.settlement.account.dao.*,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

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
	FinanceInfo financeInfo = new FinanceInfo();
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
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>

</table>
<br/>
<!--	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="">
   <tr>
    <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 到期转存</td>
    <td width="683"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>   
</table> -->  
     
  <table width="774" border="0" cellspacing="0" cellpadding="0" class="normal" align="">
    <tr  class="MsoNormal"> 
      <td colspan="4" height="25"></td>
    </tr>
    <!--a try for glass start-->
    <%
		if(lTransType == OBConstant.QueryInstrType.CHANGEFIXDEPOSIT)
		{
%>
    <tr  class="MsoNormal"> 
      <td width="20" height="25"  class="MsoNormal" align="center"><font color="#FF0000">* </font></td>
		  <%
				 String [] strNextCtrl = {"nFixedDepositTime"};
		         String [] strReturnNames = {"sPayerAccountNoZoom","sPayerAccountNoZoomAccountID"};
		         String [] strReturnFields = {"SubAccountID","AccountID"};
		         String [] strReturnValues = {"",""};
			 	 //付款方账号放大镜
			 	 SETTMagnifier.createFixedDepositNoCtrlZJ(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","sPayerAccountNoZoom","定期存款子账号",sessionMng.m_lUserID,(financeInfo.getID() == -1) ? payerInfo.getAccountID():financeInfo.getPayeeAcctID(),Long.parseLong("0"),"",Long.parseLong("0"),Long.parseLong("3"),Long.parseLong("0"),"nPayeeAccountID"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",strNextCtrl,"","","","","","","",strReturnNames,strReturnFields,strReturnValues);
				 //OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","定期存款子账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",strNextCtrl);	
		         %>			
      <td width="280" class="MsoNormal"></td>
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
	          		$('#strNewStartDate').val('<%=(financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>');
	          	</script>
      </td>
    </tr>
    <tr  class="MsoNormal"> 
      <td colspan="4" height="1"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="20" height="25" class="MsoNormal"></td>
      <td width="280" height="25" class="MsoNormal">新定期存款期限：</td>
      <td height="25" colspan="2" class="MsoNormal"> 
        <%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime1",SETTConstant.TransQueryType.FIXED,financeInfo.getFixedDepositTime(),false,"onfocus=\"nextfield='dAmount';\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
      </td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="20" height="25"></td>
      <td width="280" height="25"  class="MsoNormal">金额：<%= sessionMng.m_strCurrencySymbol %> </td>
      <td height="25" colspan="2"  class="MsoNormal"> 
        <script  language="JavaScript">
				createAmountCtrl("frm","dAmount","<%= financeInfo.getFormatAmount() %>","tsExecute","",1);
			</script> </td>
      <td width="5" height="25"></td>
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
	          		$('#tsExecute').val('<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate()%>');
	          	</script>
	  </td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="20" height="25"  class="MsoNormal"><input type="radio" name="interesttype" value="1" onClick="onRadio"></td>
      <td width="280" height="25"  class="MsoNormal">本利续存：</td>
      <td height="25"  class="MsoNormal" colspan=3><span class="MsoNormal"></span></td>
      <td width="7"></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="20" height="25"  class="MsoNormal"><input type="radio" name="interesttype" value="2" onClick="onRadio"></td>
     <%
		          	    //long aa = -1000;
		               // String [] strNextCtrl1 = {"nFixedDepositTime"};
						//SETTMagnifier.createAccountCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lInterestToAccountNO","利息转至活期账号"
						//,sessionMng.m_lClientID,Long.parseLong("0"),"",Long.parseLong("1"),Long.parseLong("0"),aa,
						//""," width=\"150\" height=\"25\" class=\"MsoNormal\""," width=\"260\" height=\"25\"",strNextCtrl1,"","","dClientName");
				  
				  		//收款方账号放大镜（本转）
		String sDepositInterestToAccountNO = "";
		if(financeInfo.getSDepositInterestToAccountID()>0)
		{
			sDepositInterestToAccountNO = eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID());
		}
	  OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"lInterestToAccountID","sPayeeNameZoomBankCtrl","frm",sDepositInterestToAccountNO,"lInterestToAccountID","利息转至活期账号"," width=\"170\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
	  %>
    </tr>
    <tr  class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <%
		}
%>
    <tr class="MsoNormal" > 
      <td height="15" colspan ="5" >&nbsp;</td>
    </tr>

         <tr>
	          		<td colspan="5">

	          		</td>          
	          		<td width="162" align="right">
	           
					<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
					<input type="button" name="Submitv00204" value=" 匹 配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
					</td>
					<td>
					<input type="button" name="Submitv00204" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
				
	          		</td>
		</tr>
		<tr><td></td></tr>    
  </table>
<!--   <table width="774" align="center">
   	<tr><td height="10"></td><td></td></tr>
   		 <tr class="MsoNormal">
          <td class="MsoNormal" colspan="4">
            <div align="right" ></div>
          </td>          
          <td width="162" align="right">
			<input type="button" name="Submitv00204" value=" 匹配 " class="button1" onClick="Javascript:matchme();">&nbsp;&nbsp;
          <input type="button" name="Submitv00204" value=" 返回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
          </td>
        </tr>
   </table> -->
    <input type="hidden" name="sPayeeNameZoomBankCtrl">
      <input type="hidden" name="lInterestToAccountID" value="<%=  financeInfo.getSDepositInterestToAccountID() %>" >
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=  financeInfo.getInterestPayeeAcctID() %>" >
	  <input type="hidden" name="nPayeeAccountID" value="<%=  financeInfo.getPayeeAcctID() %>" >
	  <input type="hidden" name="nPayerAccountID" value="<%=(financeInfo.getID() == -1) ? payerInfo.getAccountID() : financeInfo.getPayerAcctID() %>" >
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="SelectType" value="<%=lTransType %>">
	  <input type="hidden" name="nSubAccountID" value="<%=  financeInfo.getSubAccountID() %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">

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
<%--			if (!confirm("是否匹配？"))--%>
<%--			{--%>
<%--				return false;--%>
<%--			}--%>
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
        	if(frm.lInterestToAccountIDCtrl.value != ""&& frm.lInterestToAccountID.value != "0")
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
        	function goback(){
		history.go(-1);
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