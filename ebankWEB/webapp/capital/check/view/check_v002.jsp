<%--
 页面名称 ：check_v002.jsp
 页面功能 : 业务复核 - 逐笔付款 
 作    者 ：leiyang
 日    期 ：2008-12-19
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="java.sql.Timestamp"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "业务复核";
Timestamp systemDateTime = Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);

try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	
	FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
	if(financeInfo == null){
		financeInfo = new FinanceInfo();
		financeInfo.setID(-1);
		
		financeInfo.setPayerAcctID(-1);
		financeInfo.setPayerAcctNo("");
		
		financeInfo.setRemitType(OBConstant.SettRemitType.BANKPAY);
		financeInfo.setPayeeAcctID(-1);
		financeInfo.setPayeeAcctNo("");
		financeInfo.setPayeeName("");
		financeInfo.setPayeeBankName("");
		
		financeInfo.setAmount(0.0);
		financeInfo.setExecuteDate(systemDateTime);
	}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript">
<!--
//控制不同付款方式的显示
function disRemitType(form){
	var remitType = form.nRemitType.value;
	var oTrBankpay = document.getElementById("trBankpay");
	var oTrInternalvirement = document.getElementById("trInternalvirement");
	var oTrPayeeBankName = document.getElementById("trPayeeBankName");
	
	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
		oTrBankpay.style.display = "block";
		oTrInternalvirement.style.display = "none";
		oTrPayeeBankName.style.display = "block";
	}
	
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
		oTrBankpay.style.display = "none";
		oTrInternalvirement.style.display = "block";
		oTrPayeeBankName.style.display = "none";
	}
}

//控制不同付款方式的文本显示
function changeRemitType(form){
	disRemitType(form);
	var remitType = form.nRemitType.value;
	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
		form.sPayeeBankNoZoomCtrl.value = "";
		form.sPayeeName.value = "";
		form.sPayeeBankName.value = "";
	}
	
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
		form.sPayeeAcctNoZoomCtrl.value = "";
		form.sPayeeName.value = "";
		form.sPayeeBankName.value = "";
	}
}
//-->
</script>
<form name="form1" method="post" action="">
<input type="hidden" name="strSuccessPageURL" value="">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>" />
<input type="hidden" name="dPayerCurrBalance" value="">
<input type="hidden" name="dPayerUsableBalance" value="">
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="systemDateTime" value="<%=DataFormat.getDateString(systemDateTime)%>">
<table cellpadding="0" cellspacing="0" class="title_top">
<tbody>
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%=strTitle %></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
		</td>
	</tr>

	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
				<tr>
					<td width="4" height="25">&nbsp;</td>
					<%     
						//付款方账号放大镜
						OBMagnifier.createPayerAccountCheckCtrl(out,
															sessionMng.m_lSAID,
															sessionMng.m_lCurrencyID,
															financeInfo.getID(),
															sessionMng.m_lClientID,
															"nPayerAccountID",
															"dPayerCurrBalance",
															"dPayerUsableBalance",
															"form1",
															financeInfo.getPayerAcctNo(),
															"sPayerAccountNoZoom",
															"<font color='#FF0000'>* </font>付款方账号",
															" nowrap width=\"130\" height=\"25\" align=\"left\"",
															" width=\"500\" height=\"25\"",
															new String[]{"nRemitType"});	
					%>		
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇款方式：</td>
		          	<td width="500" height="25">
				  	<%
				  		//汇款方式
				  		OBConstant.SettRemitType.showList(out,
				  										  "nRemitType",
				  										  1,
				  										  financeInfo.getRemitType(),
				  										  false,
				  										  false,
				  										  "onchange='changeRemitType(form)'",
				  										  sessionMng.m_lOfficeID,
			  											  sessionMng.m_lCurrencyID);
				  	%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
			  	<tr id="trBankpay">
					<td width="4" height="25">&nbsp;</td>
					<%
						//收款方账号放大镜（银行汇款）
						OBMagnifier.createPayeeAccountNOCtrl(out,
															sessionMng.m_lCurrencyID,
															sessionMng.m_lClientID,
															"nPayeeAccountID",
															"sPayeeName",
															"sPayeeProv",
															"sPayeeCity",
															"sPayeeBankName",
															"form1",
															financeInfo.getPayeeAcctNo(),
															"sPayeeBankNoZoom",
															"<font color='#FF0000'>* </font> 收款方账号",
															" width=\"130\" height=\"25\" align=\"left\"",
															" width=\"500\" height=\"25\"");
					%>	
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
			  	<tr id="trInternalvirement">
					<td width="4" height="25">&nbsp;</td>
					<%
						//收款方账号放大镜（内部转账）
						OBMagnifier.createPayeeBankNOCtrl1(out,
														   sessionMng.m_lCurrencyID,
														   sessionMng.m_lOfficeID,
														   "nPayeeAccountID",
														   "sPayeeName",
														   "form1",
														   financeInfo.getPayeeAcctNo(),
														   "sPayeeAcctNoZoom",
														   "<font color='#FF0000'>* </font>收款方账号",
														   " width=\"130\" height=\"25\" align=\"left\"",
														   " width=\"500\" height=\"25\"");
					%>	
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 收款方名称：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sPayeeName" value="<%=financeInfo.getPayeeName()%>" size="32" readonly>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeBankName">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇入行名称：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" name="sPayeeBankName" value="<%=financeInfo.getPayeeBankName()%>" size="32" onfocus="nextfield ='dAmount';" maxlength="50" readonly>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 金额：<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="500" height="25">
			            <script language="JavaScript">
							createAmountCtrl_standard("form1","dAmount",false,"<%=DataFormat.formatListAmount(financeInfo.getAmount())%>","tsExecute","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 执行日：</td>
		          	<td width="500" height="25">
		          		<fs_c:calendar 
			          	    name="tsExecute"
				          	value="" 
				          	properties="nextfield ='sNote'" 
				          	size="20"/>
				          	  <script>
	          		$('#tsExecute').val('<%=financeInfo.getFormatExecuteDate()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
		<tr>
	          		<td colspan="7">

	          		</td>          
	          		<td width="162" align="right">
	           
					<!--img src="\webob\graphics\button_pipei.gif" width="46" height="18" onclick="Javascript:matchme();"-->
		<input type="button" value=" 匹 配 " class="button1" name="butSearch" onclick="doQuery()"/>&nbsp;
					</td>
	          		<td>
					<input type="button" name="Submitv00204" value=" 返 回 " class="button1"  onClick="Javascript:goback()" >&nbsp;&nbsp;
				
	          		</td>
	          		<td>&nbsp;</td>
		</tr>
		<tr><td></td></tr>				
			</table>
		</td>
	</tr>
</tbody>
</table>

</form>
	
<script language="javascript">
/* 页面焦点及回车控制 */
firstFocus(form1.sPayerAccountNoZoomCtrl);
setFormName("form1");
disRemitType(form1);

function doQuery()
{
	form1.action = "<%=strContext%>/capital/check/control/check_c001.jsp";
	form1.strSuccessPageURL.value = "/capital/financeinstr/view/fi_v006.jsp";
	form1.strFailPageURL.value = "/capital/check/view/check_v002.jsp";
	form1.strAction.value = "<%=OBConstant.QueryOperationType.CHECK%>";
	if (!validate(form1)) return;
	showSending();
    form1.submit();
}

function toReset(form){
	form.reset();
	disRemitType(form);
}
function goback(){	
		history.go(-1)
	}
	
function validate(form){

	if(!InputValid(form.sPayerAccountNoZoomCtrl, 1, "string", 1, 1, 50,"付款方账号")){
		return false;
	}

	if(form.nPayerAccountID.value == "-1"){
		alert("付款方账号请用放大镜选择！");
		form.sPayerAccountNoZoomCtrl.focus();
		return false;
	}
	
	var remitType = form.nRemitType.value;
	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){

		if(!InputValid(form.sPayeeBankNoZoomCtrl, 1, "string", 1, 1, 50,"收款方账号")){
			return false;
		}

		if(form.nPayeeAccountID.value == "-1"){
			alert("收款方账号请用放大镜选择！");
			form.sPayeeBankNoZoomCtrl.focus();
			return false;
		}
	}
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
	
		if(!InputValid(form.sPayeeAcctNoZoomCtrl, 1, "string", 1, 1, 50,"收款方账号")){
			return false;
		}
	
		if(form.nPayeeAccountID.value == "-1"){
			alert("收款方账号请用放大镜选择！");
			form.sPayeeAcctNoZoomCtrl.focus();
			return false;
		}
	}
	
	if(!checkAmount(form.dAmount, 1, "交易金额")){
		return false;
	}

	if(!checkDate(form.tsExecute, 1 ,"执行日")){
		return false;
	}

	
	
	return true;
}
</script>
<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}
OBHtml.showOBHomeEnd(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
%>
<%@ include file="/common/SignValidate.inc"%>