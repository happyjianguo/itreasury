<!--
/*
 * 程序名称：f007-v.jsp
 * 功能说明：定期转存修改输入页面
 * 作　　者：葛亮
 * 完成日期：2007年04月19日
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
				   com.iss.itreasury.settlement.account.dataentity.*,
				   com.iss.itreasury.settlement.util.SETTHTML,
				   com.iss.itreasury.settlement.util.SETTConstant,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   com.iss.itreasury.settlement.util.NameRef,
				     com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"
%>
<%@page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期转存]";
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
	FinanceInfo financeInfo = null;
	PayerOrPayeeInfo payeeInfo = null;
	String sPayerCurrentBalance="";
	String sPayerUsableBalance="";
	String dPayerStartDate = "";
	String nFixedDepositTime = "";
	long accountID = 0;
	double dMinSinglePayAmount = 0.0;
	//AccountDelegation accdel = new AccountDelegation();
	Sett_AccountDAO dao = new Sett_AccountDAO();
	AccountInfo accinfo = new AccountInfo();
   
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
		
		
		
		InutParameterInfo inutParameterInfo = new InutParameterInfo();
		inutParameterInfo.setOfficeID(sessionMng.m_lOfficeID);
		inutParameterInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		inutParameterInfo.setClientID(sessionMng.m_lClientID);
		inutParameterInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
		inutParameterInfo.setTransTypeID(OBConstant.SettInstrType.CHANGEFIXDEPOSIT);
		boolean isNeedApproval = OBFSWorkflowManager.isNeedApproval(inutParameterInfo);
		
		
		
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
		//accountID = (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayeeAcctID();
		//accinfo = accdel.findAccountByID(accountID);
		//accinfo = dao.findByID(accountID);
		//dMinSinglePayAmount = accinfo.getMinSinglePayAmount();
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<safety:resources />
<form method="post" name="frm">
	    <table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="" align="center">
		  <tr>
		    <td height="4"></td>
		  </tr>
		  <tr>
		    <td height="1" bgcolor="#47BBD2"></td>
		  </tr>
		  <tr>
		    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="1" bgcolor="#47BBD2"></td>
		        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">到期转存</span></td>
		        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		    <td height="5"></td>
		  </tr>
		</table>
	    <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">定期账户资料</td>
	            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	          </tr>
	        </table></td>
	      </tr>
	    </table>
      <table width="774" border="0" cellspacing="0" cellpadding="0" class =normal align="center">
      	    <tr  class="MsoNormal">
		          <td width="5" height="25"  class="MsoNormal"></td>
		          <td width="130" height="25"  class="MsoNormal"> <span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账户名称：</span></td>
		          <td width="590" height="25"  class="MsoNormal">
							<input type="text" name="sPayeeAccountName" size="24" value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountName():financeInfo.getPayerName() %>" readonly  class=box>					  		
			      </td>
		          <td width="5" height="25"  class="MsoNormal"></td>
           </tr>
           <tr  class="MsoNormal">
		          <td width="5" height="25"  class="MsoNormal"></td>
		          <td width="130" height="25"  class="MsoNormal"> <span class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期账号：</span></td>
		          <td width="590" height="25"  class="MsoNormal">
							<input type="text" name="sPayeeAccountNo" size="24" value="<%= (financeInfo.getID() == -1) ? NameRef.getNoLineAccountNo(payeeInfo.getAccountNo().toString()):NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %>" readonly  class=box>
					  		<input type="hidden" name="nPayeeAccountID"  value="<%= (financeInfo.getID() == -1) ? payeeInfo.getAccountID():financeInfo.getPayerAcctID() %>" >
			      </td>
		          <td width="5" height="25"  class="MsoNormal"></td>
           </tr>
           <tr  class="MsoNormal">
                 <td colspan="4" height="1"  class="MsoNormal"></td>
           </tr>
           <!--a try for glass start-->
		 		    <tr  class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <%
				 String [] strNextCtrl = {"nFixedDepositTime"};
		         String [] strReturnNames = {"sPayerAccountNoZoom","sPayerAccountNoZoomAccountID"};
		         String [] strReturnFields = {"SubAccountID","AccountID"};
		         String [] strReturnValues = {"",""};
			 	 //付款方账号放大镜
			 	 SETTMagnifier.createFixedDepositNoCtrlZJ(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","sPayerAccountNoZoom","<font color=\"red\">*&nbsp;</font>定期存款子账号",sessionMng.m_lUserID,(financeInfo.getID() == -1)?payeeInfo.getAccountID():financeInfo.getPayerAcctID(),Long.parseLong("0"),financeInfo.getSDepositBillNo(),Long.parseLong("0"),Long.parseLong("3"),Long.parseLong("0"),"nPayeeAccountID"," width=\"160\" height=\"25\" class=\"MsoNormal\""," width=\"560\" height=\"25\" ",strNextCtrl,"","","","","","","",strReturnNames,strReturnFields,strReturnValues);
				 //OBMagnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,financeInfo.getID(),sessionMng.m_lClientID,"nPayerAccountID","dPayerCurrBalance","dPayerUsableBalance","frm",financeInfo.getPayerAcctNo(),"sPayerAccountNoZoom","定期存款子账号"," width=\"130\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ",strNextCtrl);	
		         %>		
		         <td width="5" class="MsoNormal"></td>
		   </tr>
	        <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>定期存款起始日：</td>
		         <td width="590" height="25"  class="MsoNormal"><input type="text" class="box" name="dPayerStartDate" size="16" value="<%= (financeInfo.getID()==-1 && financeInfo.getDepositStart()==null)? "":financeInfo.getDepositStart().toString().substring(0,10) %>" readonly>
			     <font class="graytext" >&nbsp;&nbsp;&nbsp;定期存款结束日：<input type="text" class="box" name="dPayerEndDate" size="16" value="" readonly>			         
			     </td>
	             <td width="5" colspan="4"  class="MsoNormal"></td>
	        </tr>
	       <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>存单金额：</td>
		         <td width="590" height="25"  class="MsoNormal"><input type="text" class="box" name="dPayerCurrBalance" size="16" value="<%= financeInfo.getDepositAmount() %>" readonly style="text-align:right" onpropertychange="document.frm.dPayerCurrBalance.value=formatAmount(this.value)">
			     <font class="graytext" >&nbsp;&nbsp;&nbsp;期限：<input type="text" class="box" name="nFixedDepositTime" size="2" value="<%= (financeInfo.getID()==-1)?"":String.valueOf(financeInfo.getFixedDepositTime())%>" readonly>
			     </td>
	             <td width="5" colspan="4"  class="MsoNormal"></td>
	        </tr>
	        	       <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="130" height="25"  class="MsoNormal"><font color="red">&nbsp;&nbsp;</font>利率：</td>
		         <td width="590" height="25"  class="MsoNormal"><input type="text" class="box" name="dPayerCurrInterest" size="16" value="<%= financeInfo.getDepositRate()%>" readonly style="text-align:right">&nbsp;%
			     </td>
	             <td width="5" colspan="4"  class="MsoNormal"></td>
	        </tr>
      </table>
      <br>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
	        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">到期转存</td>
	            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	          </tr>
	        </table></td>
	      </tr>
	  </table>      				 
      <table width="774" border="0" cellspacing="0" cellpadding="0" class =normal align="center">
            <tr  class="MsoNormal">
                 <td colspan="4" height="1"  class="MsoNormal"></td>
            </tr>
            <tr  class="MsoNormal">
                 <td width="5" height="25"></td>
                 <td width="166" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>金额：</td>                      
                 <td height="25" width="591"  class="MsoNormal">￥
		         <input  class="box" type="text" name="dAmount" value="<%= financeInfo.getFormatAmount()%>"  size="20" style="text-align:right" readonly="readonly" onpropertychange="document.frm.dAmount.value=formatAmount(this.value)">
		            <script  language="JavaScript">
						//createAmountCtrl("frm","dAmount","<%= financeInfo.getFormatAmount() %>","","","<%=sessionMng.m_lCurrencyID%>");
					</script>
		         </td>
		         <td width="5" height="25"  class="MsoNormal"></td>
          </tr>
            <tr  class="MsoNormal">
                 <td colspan="4" height="1"  class="MsoNormal"></td>
            </tr>
	        <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="166" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>定期存款期限：</td>
		         <td width="591" height="25"  class="MsoNormal">
		         <%SETTHTML.showFixedDepositMonthListCtrl(out,"nFixedDepositTime1",SETTConstant.TransQueryType.FIXED,financeInfo.getSDepositBillPeriod(),false,"onfocus=\"nextfield='dAmount';\" onblur=\"PeriodOnblur()\"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);%>
			     </td>
	             <td colspan="4"  class="MsoNormal"></td>
	        </tr>
       <tr  class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td height="25"   class="MsoNormal"><font color="red">*&nbsp;</font>执行日：</td>
      <td height="25"   class="MsoNormal">
      	<fs_c:calendar 
          	    name="tsExecute"
	          	value="" 
	          	properties="nextfield ='sNote'" 
	          	size="20"/>
	          		          	  <script>
	          		$('#tsExecute').val('<%= (financeInfo.getID() == -1)?DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)) :financeInfo.getFormatExecuteDate() %>');
	          	</script>
	  </td>
      <td height="25"  class="MsoNormal"></td>
      <td width="7" height="25" class="MsoNormal"></td>
    </tr>
    	        <tr class="MsoNormal">
	             <td colspan="4" height="1" class="MsoNormal"></td>
	        </tr>
	       <tr class="MsoNormal">
		         <td width="5" height="25"  class="MsoNormal"></td>
		         <td width="166" height="25"  class="MsoNormal"><font color="red">*&nbsp;</font>新定期存款起始日：</td>
		         <td width="591" height="25"  class="MsoNormal"><input type="text" name="dPayerCurrStartDate" value="<%= (financeInfo.getID() == -1) ? "":String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10) %>" onfocus="nextfield ='dPayerCurrEndDate';" size="12" readonly class="box">
			     <font class="graytext" >&nbsp;&nbsp;&nbsp;<font color="red">*&nbsp;</font>新定期存款到期日：</font><input type="text" name="dPayerCurrEndDate" value="<%= (financeInfo.getID() == -1) ? "":String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10) %>" onfocus="nextfield ='nFixedDepositTime1';" size="12" readonly class="box" onblur="PeriodOnblur()">
			     </td>
	             <td colspan="4"  class="MsoNormal"></td>
	        </tr>
      </table>
	  <br>
	  <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr>
	        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">利息处理</td>
	            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	          </tr>
	        </table></td>
	      </tr>
	  </table>  	  						  				
      
  <table width="774" border="0" cellspacing="0" cellpadding="0" class =normal align="center">
    <tr  class="MsoNormal"> 
      <td colspan="5" height="1"  class="MsoNormal"></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="5" height="25"  class="MsoNormal"><input type="radio" name="interesttype" value="1" onclick="startRadio()"></td>
      <td width="150" height="25"  class="MsoNormal"><span class="MsoNormal">本利转存</span></td>
      <td width="260" height="25"  class="MsoNormal" colspan=3><span class="MsoNormal"></span></td>
    </tr>
    <tr  class="MsoNormal"> 
      <td width="5" height="25"  class="MsoNormal"><input type="radio" name="interesttype" value="2" onclick="startRadio()" checked="checked"></td>
        <%
		//收款方账号放大镜（本转）
		String sDepositInterestToAccountNO = "";
		if(financeInfo.getSDepositInterestToAccountID()>0)
		{
			sDepositInterestToAccountNO = eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID());
		}
		OBMagnifier.createPayeeBankNOCtrl(out,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"lInterestToAccountID","sPayeeNameZoomBankCtrl","frm",sDepositInterestToAccountNO,"lInterestToAccountID","利息转至活期账号"," width=\"170\" height=\"25\" class=\"MsoNormal\""," width=\"590\" height=\"25\" ");
		
		%>
    </tr>
    <input type="hidden" name="lInterestToAccountID" value="<%=financeInfo.getSDepositInterestToAccountID()%>">
    <input type="hidden" name="sPayeeNameZoomBankCtrl">
    <tr  class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
  </table>
   <br>
   <%--
       <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">链接附件</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
           
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "上传" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
      <%--     </td>
        </tr>
      </table>
      --%>
      <br>
      <table width="774" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan=5" align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class="button1" name="add" type="button" value=" 提交 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			&nbsp;
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
         	<input class=button1 name=reset type=button value=" 重置 " > 
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
	    <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
		<input type="hidden" name="dMinSinglePayAmount" value="<%=dMinSinglePayAmount%>">
		<input type="hidden" name="dClientNameaa" value="">
		
	  <input type="hidden" name="submitUserId" value="<%=sessionMng.m_lUserID %>">
	  <input type="hidden" name="isRefused" value="<%=financeInfo.isRefused() %>">
	  <input type="hidden" name="strAction" value="">
	  
	  <input type="hidden" name="isNeedApproval" value="<%=isNeedApproval %>">	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>"> 
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  
	  
</form>
<!--presentation end-->

<script language="Javascript">
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
			frm.lInterestToAccountIDCtrl.disabled = true;  		    		
	}else if(<%=financeInfo.getSDepositInterestDeal()%> == 2){
		frm.interesttype[1].checked = true;
	}
	
	function onChanged(obj){
		if(frm.dPayerStartDate.value!=""&&frm.nFixedDepositTime.value!="")
		{	
			var d = ft_dateConvertDate(frm.dPayerCurrStartDate.value) ;
			var arra = frm.dPayerCurrStartDate.value.split("-");
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
		    
		    frm.dPayerCurrStartDate.value = ft_dateConvertStr(d);
		}
	}
	/* 定期存款期限的onblur事件 */
	function PeriodOnblur()
	{
		if(frm.dPayerCurrStartDate.value!=""&&frm.nFixedDepositTime1.value!="")
		{	
			var d = ft_dateConvertDate(frm.dPayerCurrStartDate.value) ;
			var arra = frm.dPayerCurrStartDate.value.split("-");
			var yy = arra[0]; 
			var mm = arra[1]-1 ;
			var n = 1000000 ;
			
			n = parseInt(frm.nFixedDepositTime1.value);
			d.setMonth(d.getMonth()+n);
			if((d.getYear()*12+d.getMonth()) > (yy*12+mm + n)){
			d=new Date(d.getYear(),d.getMonth(),0);
		    }
		    
		    frm.dPayerCurrEndDate.value = ft_dateConvertStr(d);
		}
	}
	
	/*日期格式转换为日期字符串*/
	function ft_dateConvertStr(d){
		return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
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
    	    frm.lInterestToAccountID.value= -1 ;
    		frm.lInterestToAccountIDCtrl.disabled = true;		  		
    	}
    	else if(frm.interesttype[1].checked == true)
    	{
    	  frm.lInterestToAccountIDCtrl.disabled = false;
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
		if (frm.sPayerAccountNoZoomCtrl.value == "")
		{
			alert("请选择定期存款子账号");
			frm.sPayerAccountNoZoomCtrl.focus();
			return false;
		}
		/* 检验执行日*/
		if(document.all("tsExecute").value=="")
		{
			alert("执行日不能为空，请录入");
			document.all("tsExecute").focus();
			return false;
		}
		if (!checkInterestExecuteDate(frm.tsExecute,"<%=DataFormat.getDateString(Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID))%>"))
		{
			document.all("tsExecute").focus();
			return false;
		}
		/* 新定期存款起始日校验 */
		if (!checkDate(frm.dPayerCurrStartDate,1,"新定期存款起始日"))
		{
			return false;
		}
		
		/* 新定期存款到期日校验 */
		if (!checkDate(frm.dPayerCurrEndDate,1,"新定期存款到期日"))
		{
			return false;
		}
		/*
		if (frm.nFixedDepositTime1.value == "" || frm.nFixedDepositTime1.value<0)
        {
             alert("请选择定期存款期限！");
			 frm.nFixedDepositTime1.focus();
             return false;
        }
        
		if(parseFloat(document.frm.dMinSinglePayAmount.value)>reverseFormatAmount(frm.dAmount.value))
		{
			alert("金额小于该账户定期开立起存金额，请重新输入！");
			document.frm.dAmount.focus();
			return false;
		}*/
		/* 划款资料非空校验 */
		
		/* 金额校验 */
		if(!checkAmount(frm.dAmount, 1, "金额"))
		{
			return false;
		}

		/* 业务校验 */
		/* 可用余额－交易金额 */
		/*
		var dBalance = parseFloat(reverseFormatAmount(frm.dPayerUsableBalance.value)) -
							parseFloat(reverseFormatAmount(frm.dAmount.value)) 
		
		
		
		//可用余额－交易金额＜0，则提示“可用余额不足，请重新输入划拨金额”
		if (dBalance < 0) 
		{
			alert("可用余额不足，请重新输入划拨金额");
			frm.dAmount.focus();
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
    /* 修改提交处理函数 */
    function addme()
    {
        
		frm.action = "f009-c.jsp";
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		if (validate() == true)
        {
			
			<%
			if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToView(out);
	
				if(isNeedApproval){					
					if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
					&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
					&& !financeInfo.isRefused()){
			%>				
						//特殊处理				
						valueArray[9] = '-1';
				<%
					}			
				}else{
					if(financeInfo.getStatus() !=OBConstant.SettInstrStatus.CHECK 
					   && financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED){
				%>
						valueArray[9] = '-1';
				<%
					}
				}			
				%>			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				//签名不成功，不允许提交表单
				if(signatureValue == ""){
					alert("签名不成功，无法进行提交！");
					return false;
				}					
			<%}%>
			
			//确认提交 
			if (!confirm("是否提交？"))
			{
				return false;
			}
			/* 确认提交 */
			showSending();
			frm.clickCount.value = parseInt(frm.clickCount.value) +1 ;
            //alert(frm.lInterestToAccountIDCtrl.value);
            frm.submit();
        }
    }
    
     function doSaveAndApprovalInit()
    {
		frm.action = "f009-c.jsp?operate=saveAndApproval";
		document.frm.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		if (validate() == true)
        {
				<%
				if(Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus)){
					OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
					OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToView(out);
					
					if(financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELAPPROVALED
						&& financeInfo.getActionStatus() != OBConstant.SettActionStatus.CANCELCHECKED 
						&& !financeInfo.isRefused()){		
				%>
						//特殊处理
						valueArray[9] = '-1';	
					<%}%>
					
					var signatureValue = DoSign(frm,nameArray,valueArray);					
					//签名不成功，不允许提交表单
					if(signatureValue == ""){
						alert("签名不成功，无法进行提交！");
						return false;
					}	
						
				<%}%>
				
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
</script>

<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frm.sPayerAccountNoZoomCtrl);
	//setSubmitFunction("addme(frm)");
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