<%--
 页面名称 ：check_v003.jsp
 页面功能 : 信息查询 － 取消复核查询
 作    者 ：leiyang
 日    期 ：2008-12-05
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "取消复核查询";
String strTemp = "";

long lFixTransferID = -1;       //定期支取账户ID
long lNotifyTransferID = -1;    //通知支取账户ID

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
   	
   	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
	if(queryCapForm == null){
		queryCapForm = new QueryCapForm();
		queryCapForm.setStartSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
		queryCapForm.setEndSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
	}
	
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	lFixTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
	lNotifyTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
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
function disTransType(form){
	var lTransType = form.lTransType.value;
	var oTrFixedDeposit = document.getElementById("trFixedDeposit");
	var oTrNotifyDeposit = document.getElementById("trNotifyDeposit");
	
	form.lFixedDepositIDCtrl.value = "";
	oTrFixedDeposit.style.display = "none";
	form.lNotifyDepositIDCtrl.value = "";
	oTrNotifyDeposit.style.display = "none";

	if(lTransType == "<%=OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER%>"){
		oTrFixedDeposit.style.display = "block";
		oTrNotifyDeposit.style.display = "none";
	}
	
	if(lTransType == "<%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW%>"){
		oTrFixedDeposit.style.display = "none";
		oTrNotifyDeposit.style.display = "block";
	}
}
//-->
</script>

<form name="form1" method="post" action="">
<input type="hidden" name="strSuccessPageURL" value="">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">  <!--操作代码-->
<input type="hidden" name="lDepositID" value="">
<input type="hidden" name="strDepositNo" value="">
<input type="hidden" name="lFixTransferID" value="<%=lFixTransferID%>">
<input type="hidden" name="lNotifyTransferID" value="<%=lNotifyTransferID%>">
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
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">交易类型：</td>
		          	<td width="500" height="25" colspan="2">
	            	<%
	            		OBHtmlCom.showQueryCheckTypeListControl(out,"lTransType",queryCapForm.getTransType()," onChange=\"disTransType(form1);\"  onfocus=\"nextfield ='sStartExe';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,true);
	            	%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr id="trFixedDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //定期存款单据号
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lFixedDepositID",
			                "定期存款单据号",
			                sessionMng.m_lUserID,
			                lFixTransferID,
			                -1,
			                "",
			                1,
			                21,
			                "lFixTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
				<tr id="trNotifyDeposit" style="display:none;">
					<td width="4" height="25">&nbsp;</td>
					<%
			            //通知存款单据号
			            OBMagnifier.createFixedDepositNoCtrl(
			                out,
			                sessionMng.m_lOfficeID,
			                sessionMng.m_lCurrencyID,
			                "form1",
			                "lNotifyDepositID",
			                "通知存款单据号",
			                sessionMng.m_lUserID,
			                lNotifyTransferID,
			                -1,
			                "",
			                2,
			                21,
			                "lNotifyTransferID",
							" width=\"130\" height=\"25\" align=\"left\"",
							" width=\"500\" height=\"25\" colspan=\"2\"",
			                new String[]{"lStatus"},
			                ""
			            );
					%>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">执行日：</td>
		          	<td width="220" height="25">
		          		由&nbsp;
		          		<fs_c:calendar 
			          	    name="sStartExe"
				          	value="" 
				          	properties="nextfield ='sEndExe'" 
				          	size="20"/>
				<script>
	          		$('#sStartExe').val('<%=queryCapForm.getStartExe()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						至&nbsp;
						<fs_c:calendar 
			          	    name="sEndExe"
				          	value="" 
				          	properties="nextfield ='dMinAmount'" 
				          	size="20"/>
				        <script>
	          		$('#sEndExe').val('<%=queryCapForm.getEndExe()%>');
	          	</script>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">金额：<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="220" height="25">
		          		由<script language="JavaScript">
							createAmountCtrl_standard("form1","dMinAmount",false,"<%=DataFormat.formatListAmount(queryCapForm.getMinAmount())%>","dMaxAmount","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元
		          	</td>
		          	<td width="280" height="25">
						至<script language="JavaScript">
							createAmountCtrl_standard("form1","dMaxAmount",false,"<%=DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>","sStartSubmit","","<%=sessionMng.m_lCurrencyID%>","","");
						</script> 元
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">提交日期：</td>
		          	<td width="220" height="25">
		          		由&nbsp;
		          		<fs_c:calendar 
			          	    name="sStartSubmit"
				          	value="" 
				          	properties="nextfield ='sEndSubmit'" 
				          	size="20"/>
				          	  	  <script>
	          		$('#sStartSubmit').val('<%=queryCapForm.getStartSubmit()%>');
	          	</script>
		          	</td>
		          	<td width="280" height="25">
						至&nbsp;
						<fs_c:calendar 
			          	    name="sEndSubmit"
				          	value="" 
				          	properties="nextfield ='nextfield'" 
				          	size="20"/>
				          	 <script>
	          		$('#sEndSubmit').val('<%=queryCapForm.getEndSubmit()%>');
	          	</script>
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
				<tr id="trSaveInternalvirement">
					<td width="*%" align="right">
						<input type="button" value=" 查 找 " class="button1" name="butSearch" onclick="doQuery()"/>&nbsp;
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
firstFocus(form1.lTransType);
//setSubmitFunction("doQuery()");
setFormName("form1");
disTransType(form1);

function doQuery()
{
	form1.action = "../control/check_c002.jsp";
	form1.strSuccessPageURL.value = "../view/check_v004.jsp";
	form1.strFailPageURL.value = "../view/check_v003.jsp";
	form1.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	if (!validate(form1)) return;
	
	if(form1.lFixedDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lFixTransferID.value;
		form1.strDepositNo.value = form1.lFixedDepositIDCtrl.value;
	}
	if(form1.lNotifyDepositIDCtrl.value != ""){
		form1.lDepositID.value = form1.lNotifyTransferID.value;
		form1.strDepositNo.value = form1.lNotifyDepositIDCtrl.value;
	}

	showSending();
    form1.submit();
}

function validate(form){

	if(!checkDate(form.sStartExe,0,"执行日由"))
	{
		form.sStartExe.value="";
		form.sStartExe.focus();
		return false;
	}
	
	if(!checkDate(form.sEndExe,0,"执行日至"))
	{
		form.sEndExe.value="";
		form.sEndExe.focus();
		return false;
	}
	
	if(form.sStartExe.value!="" && form.sEndExe.value!="")
	{
		if(CompareDateString(form.sEndExe.value,form.sStartExe.value))
		{
			alert("执行日至不能晚于执行日由");
			return false;
		}
	}
	
	if(!checkAmount(form.dMinAmount, 2, "金额由")){
		return false;
	}
	
	if(!checkAmount(form.dMaxAmount, 2, "金额至")){
		return false;
	}
	
	if(form.dMinAmount.value!="" && form.dMaxAmount.value!="")
	{
		if((parseFloat(reverseFormatAmount(form.dMinAmount.value))) > (parseFloat(reverseFormatAmount(form.dMaxAmount.value)))) {
        	alert("最小金额不能大于最大金额");
            return false;
        }
	}
	
	if(!checkDate(form.sStartSubmit,1,"提交日期由"))
	{
		form.sStartSubmit.value="";
		form.sStartSubmit.focus();
		return false;
	}
	
	if(!checkDate(form.sEndSubmit,1,"提交日期至"))
	{
		form.sEndSubmit.value="";
		form.sEndSubmit.focus();
		return false;
	}
	
	if(form.sStartSubmit.value!="" && form.sEndSubmit.value!="")
	{
		if(!CompareDateString(form.sStartSubmit.value,form.sEndSubmit.value))
		{
			alert("提交日期至不能晚于提交日期由");
			return false;
		}
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