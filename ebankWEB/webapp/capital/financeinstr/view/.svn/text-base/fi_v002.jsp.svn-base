<%--
 页面名称 ：fi_v001.jsp
 页面功能 : 网上银行 - 逐笔付款 (批量付款时使用)
 作    者 ：leiyang
 日    期 ：2008-12-01
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
<%@ page import="java.sql.Timestamp"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
String strTitle = "逐笔付款";
String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
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
	var oTrBankpay = document.getElementById("trBankpay");
	var oTrInternalvirement = document.getElementById("trInternalvirement");
	var oTrPayeeProv = document.getElementById("trPayeeProv");
	var oTrPayeeBankName = document.getElementById("trPayeeBankName");
	var oTrSaveBankpay = document.getElementById("trSaveBankpay");
	var oTrSaveInternalvirement = document.getElementById("trSaveInternalvirement");

	oTrBankpay.style.display = "block";
	oTrInternalvirement.style.display = "none";
	oTrPayeeProv.style.display = "block";
	oTrPayeeBankName.style.display = "block";
	oTrSaveBankpay.style.display = "block";
	oTrSaveInternalvirement.style.display = "none";
	form.sPayeeName.readOnly = "";
}
//-->
</script>
<form name="form1" method="post" action="../control/fi_c002.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v003.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.SettInstrStatus.SAVE%>">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo()%>">
<input type="hidden" name="nRemitType" value="<%=financeInfo.getRemitType()%>">
<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>" />
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="submitUserId" value="<%=sessionMng.m_lUserID%>">
<input type="hidden" name="systemDateTime" value="<%=DataFormat.getDateString(systemDateTime)%>">
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
<table width="800" align="center"  border="0" cellspacing="0" cellpadding="0">
<tbody>
	<tr>
		<td height="1" bgcolor="#47BBD2"></td>
	</tr>
	<tr>
		<td height="24" valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1" bgcolor="#47BBD2"></td>
					<td width="124" background="/webob/graphics/new_til_bg.gif"><span class="txt_til2"><%=strTitle%></span></td>
					<td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 付款方资料</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 付款方名称：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sPayerName" size="32" value="<%=sessionMng.m_strClientName%>" readonly>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td width="4" height="25">&nbsp;</td>
					<%     
						//付款方账号放大镜
						OBMagnifier.createPayerAccountNoCtrl(out,
															sessionMng.m_lUserID,
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
															" width=\"130\" height=\"25\" align=\"left\"",
															" width=\"500\" height=\"25\"",
															new String[]{"sPayeeBankNoZoomCtrl"});	
					%>		
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">&nbsp;&nbsp;当前余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="25">
						<input type="text" class="tar" name="dPayerCurrBalance" size="20" value="<%=financeInfo.getFormatCurrentBalance()%>" readonly align="right"> 元
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">&nbsp;&nbsp;可用余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="25">
						<input type="text" class="tar" name="dPayerUsableBalance" size="20" value="<%=financeInfo.getFormatUsableBalance()%>" readonly align="right"> 元
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">收款方资料</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇款方式：</td>
		          	<td width="500" height="25">
				  	<%
				  		//汇款方式
				  		OBConstant.SettRemitType.showList(out,
				  										  "dnRemitType",
				  										  1,
				  										  financeInfo.getRemitType(),
				  										  false,
				  										  false,
				  										  " disabled ",
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
		            	<input type="text" class="box" name="sPayeeName" value="<%=financeInfo.getPayeeName()%>" size="32">
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeProv">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇入地：</td>
		          	<td width="500" height="25">
			            <input type="text" class="box" name="sPayeeProv" value="<%=financeInfo.getPayeeProv()%>" size="10" onfocus="nextfield ='sPayeeCity';" maxlength="15"> 省
			            <input type="text" class="box" name="sPayeeCity" value="<%=financeInfo.getPayeeCity()%>" size="10" onfocus="nextfield ='sPayeeBankName';" maxlength="15"> 市（县）
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeBankName">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 汇入行名称：</td>
		          	<td width="500" height="25">
			            <input class="box" type="text" name="sPayeeBankName" value="<%=financeInfo.getPayeeBankName()%>" size="32" onfocus="nextfield ='dAmount';" maxlength="50">
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">划款资料</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left"><font color="#FF0000">*</font> 金额：<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="500" height="25">
			            <script language="JavaScript">
							createAmountCtrl_standard("form1","dAmount",false,"<%=DataFormat.formatListAmount(financeInfo.getAmount())%>","tsExecute","sChineseAmount","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">&nbsp;&nbsp;大写金额：</td>
		          	<td width="500" height="25">
		            	<input type="text" class="box" name="sChineseAmount" size="32" value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>" readonly>
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
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" valign="top"><font color="#FF0000">*</font> 汇款用途：</td>
		          	<td width="500" height="25">
            			<textarea name="sNote" class="box" cols="30" rows="2" onkeypress="if (this.value.length>6) event.keyCode=0;" onchange="if(this.value.length>6) this.value=this.value.substr(0,6)" ><%=financeInfo.getNote()%></textarea>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr id="trSaveBankpay">
					<td width="*%" align="right">
						<input type="button" value=" 保 存 " class="button1" name="butBankpaySave" onclick="toSave(form1)"/>&nbsp;
						<input type="button" value=" 重 置 " class="button1" name="butBankpayReset" onclick="toReset(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr id="trSaveInternalvirement">
					<td width="*%" align="right">
						<input type="button" value=" 保 存 " class="button1" name="butInternalvirementSave" onclick="toSave(form1)"/>&nbsp;
						<input type="button" value=" 重 置 " class="button1" name="butInternalvirementReset" onclick="toReset(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</tbody>
</table>

</form>
	
<script language="javascript">
/* 页面焦点及回车控制 */
firstFocus(form1.sPayerName);
//setSubmitFunction("toNext(form1)");
setFormName("form1");
disRemitType(form1);

function toSave(form)
{
	form.action = "../control/fi_c002.jsp";
	form.strSuccessPageURL.value = "../view/fi_v003.jsp";
	form.strFailPageURL.value = "../view/fi_v001.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.SAVE%>";
	if (!validate(form)) return;
 
    //网银数字签名  modify by leiyang 20081202
	<%
		if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
			OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
			OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "form1");
   %>
			var signatureValue = DoSign(form,nameArray,valueArray);
			//签名不成功，不允许提交表单
			if(signatureValue == ""){
				alert("签名不成功，无法进行提交！");
				return false;
			}
	<%
		}
	%>

	if(confirm("是否保存？"))
	{
		showSending();
	    form.submit();
	}
}

function toReset(form){
	form.reset();
	disRemitType(form);
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
	
	if(!InputValid(form.sPayeeBankNoZoomCtrl, 1, "string", 1, 1, 50,"收款方账号")){
		return false;
	}

	/////特殊校验收款方名称 ----- start -----
	
	//if(!InputValid(form.sPayeeName, 1, "string", 1, 1, 15, "收款方名称")){
	//	return false;
	//}
   	if(form.sPayeeName.value.length==0) 
    {
       alert("请输入收款方名称");
       form.sPayeeName.focus();
       return false;
    }
	if(form.sPayeeName.value.indexOf("'")>0)
	{
	   alert("收款方名称中不能包含非法字符！");
       form.sPayeeName.focus();
       return false;
	}
	if(!(1<=form.sPayeeName.value.length && form.sPayeeName.value.length<=15))
	{
	   form.sPayeeName.value = form.sPayeeName.value.substring(0,15);
	   alert(form.sPayeeName.value);
	}
	/////特殊校验收款方名称 ----- end -----
    
	
	if(!InputValid(form.sPayeeProv, 1, "string", 1, 1, 10,"汇入地省份")){
		return false;
	}
	
	if(!InputValid(form.sPayeeCity, 1, "string", 1, 1, 10,"汇入地城市")){
		return false;
	}
	
	if(!InputValid(form.sPayeeBankName, 1, "string", 1, 1, 50,"汇入行名称")){
		return false;
	}
	
	if(!checkAmount(form.dAmount, 1, "交易金额")){
		return false;
	}

	if(!checkDate(form.tsExecute, 1 ,"执行日")){
		return false;
	}

	if(!CompareDateString(form.systemDateTime.value,form.tsExecute.value))
	{
		alert("执行日不能小于系统开机日！");
		form.tsExecute.focus();
		return false;
	}
	
	if(!InputValid(form.sNote, 1, "string", 1, 1, 100,"汇款用途")){
		return false;
	}
	
	// 基本业务校验
	var dBalance = parseFloat(reverseFormatAmount(form.dPayerUsableBalance.value)) - parseFloat(reverseFormatAmount(form.dAmount.value));
	// 可用余额 - 交易金额不能小于0
	if(dBalance < 0){
		alert("可用余额不足，请重新输入金额");
		form.dAmount.focus();
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