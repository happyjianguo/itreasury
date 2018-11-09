<%--
 页面名称 ：check_v004.jsp
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
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
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
		response.sendRedirect(strContext + "/capital/query/view/query_v001.jsp");
	}
	
	Collection coll = (Collection)request.getAttribute("queryCapColl");
	
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
<input type="hidden" name="lInstructionID" value="">
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
		          		<fs:calendar 
			          	    name="sStartSubmit"
				          	value="<%=queryCapForm.getStartSubmit()%>" 
				          	properties="nextfield ='sEndSubmit'" 
				          	size="20"/>
		          	</td>
		          	<td width="280" height="25">
						至&nbsp;
						<fs:calendar 
			          	    name="sEndSubmit"
				          	value="<%=queryCapForm.getEndSubmit()%>" 
				          	properties="nextfield ='butSearch'" 
				          	size="20"/>
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
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 查询结果</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal">
				<thead>
			        <tr>
			        	<td width="3%" height="25" align="center"><input type="checkbox" id="checkMain" onClick="checkCheckBox(this)" /></td>
			        	<td width="12%" height="25" align="center">指令序号</td>
						<td width="12%" height="25" align="center">账 号</td>
						<td width="5%" height="25" align="center">借/贷</td>
						<td width="12%" height="25" align="center">金 额</td>
						<td width="19%" height="25" align="center">对方名称</td>
						<td width="14%" height="25" align="center">对方账号</td>
						<td width="9%" height="25" align="center">汇款用途</td>
						<td width="10%" height="25" align="center">执行日期</td>
			        </tr>
			    </thead>
				<tbody>
				<%
				if(coll != null){
					Iterator listQuery = coll.iterator();
					String strFormatConfirmDate = "";
		            while(listQuery.hasNext()) {
		                FinanceInfo info = (FinanceInfo)listQuery.next(); // 获取下一条记录信息
		                if(info.getTransNo() == null){
		                	info.setTransNo("");
		                }
		                if(info.getNote() == null){
		                	info.setNote("");
		                }
		                
		                if(strFormatConfirmDate.equals("") || !strFormatConfirmDate.equals(DataFormat.getDateString(info.getConfirmDate()))){
		                	strFormatConfirmDate = DataFormat.getDateString(info.getConfirmDate());
		        %>
			        <tr>
						<td height="25" align="left" colspan="2">提交日期：</td>
						<td height="25" align="left" colspan="9"><%=strFormatConfirmDate%></td>
			        </tr>
			    <%
		                }
			    %>
			        <tr>
			        	<td height="25" align="center">
			        		<input type="checkbox" name="lnstructionList" value="<%=info.getID()%>" onClick="checkCheckBox(this)" />
			        	</td>
			        	<td height="25" align="center">
			        		<a href="javascript:toHref(form1, '<%=info.getID()%>', <%=info.getTransType()%>)"><%=info.getID()%></a>
			        	</td>
						<td height="25" align="center"><%=info.getPayerAcctNo()%></td>
						<td height="25" align="center"><%="借"%></td>
						<td height="25" align="right"><%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
						<td height="25" align="left"><%=info.getPayeeName()==null?"":info.getPayeeName()%></td>
						<td height="25" align="center"><%=info.getPayeeAcctNo()==null?"":info.getPayeeAcctNo()%></td>
						<td height="25" align="left"><%=info.getNote()%></td>
						<td height="25" align="center"><%=info.getFormatExecuteDate()%></td>
			        </tr>
		        <%
		       
		            }
				}
				else{
				%>
			        <tr>
			        	<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
						<td height="25" align="center">&nbsp;</td>
			        </tr>
			    <%}%>
			    </tbody>
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
					<td width="*%" align="right">
						<input type="button" value=" 取消复核 " class="button1" name="butBatchUnCheck" onclick="toBatchUnCheck(form1)"/>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
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

function toHref(form, lnstructionID, transTypeId)
{
	form.action = "<%=strContext%>/capital/query/control/query_c002.jsp";
	switch(transTypeId){
		case <%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>:
			//资金划拨-银行付款
			form.strSuccessPageURL.value = "/capital/financeinstr/view/fi_v006.jsp";
			break;
		case <%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>:
			//资金划拨-内部转账
			form.strSuccessPageURL.value = "/capital/financeinstr/view/fi_v006.jsp";
			break;
		case <%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>:
			//定期开立
			form.strSuccessPageURL.value = "/capital/fixed/f004-v.jsp?src=1";
			break;
		case <%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>:
			//定期支取
			form.strSuccessPageURL.value = "/capital/fixed/f014-v.jsp?src=1";
			break;
		case <%=OBConstant.SettInstrType.OPENNOTIFYACCOUNT%>:
			//通知开立
			form.strSuccessPageURL.value = "/capital/notify/n004-v.jsp?src=1";
			break;
		case <%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>:
			//通知支取
			form.strSuccessPageURL.value = "/capital/notify/n014-v.jsp?src=1";
			break;
		case <%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>:
			//定期续存
        	form.strSuccessPageURL.value = "/capital/fixed/f008-v.jsp?src=1";
        	break;
	}
	form.strFailPageURL.value = "/capital/query/view/query_v001.jsp";
	form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	form.lInstructionID.value = lnstructionID;
	showSending();
    form.submit();
}

function toBatchUnCheck(form){
	var oCheckColl = window.document.getElementsByName("lnstructionList");
	var bool = 0;

	for(var i=0; i<oCheckColl.length; i++){
		if(oCheckColl[i].checked == true){
			bool++;
		}
	}
	
	if(bool == 0){
		alert("您没有选择指令");
		return false;
	}

	form.action = "<%=strContext%>/capital/check/control/check_c003.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/capital/check/view/check_v004.jsp";
	form.strFailPageURL.value = "<%=strContext%>/capital/check/view/check_v003.jsp";
	form.strAction.value = "BATCHUNCHECK";
	
	if(confirm("是否取消复核？")){
		showSending();
	    form.submit();
    }
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
		if(CompareDateString(frm.sEndExe.value,frm.sStartExe.value))
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

function checkCheckBox(arg0){
	var oCheckThis = arg0;
	var oCheckMain = window.document.getElementById("checkMain");
	var oCheckColl = window.document.getElementsByName("lnstructionList");
	var bool = true;
	
	if(oCheckThis.id == "checkMain"){
		if(oCheckThis.checked == true){
			for(var i=0; i<oCheckColl.length; i++){
				oCheckColl[i].checked = true;
			}
		}
		else {
			for(var i=0; i<oCheckColl.length; i++){
				oCheckColl[i].checked = false;
			}
		}
	}
	else {
		for(var i=0; i<oCheckColl.length; i++){
			if(oCheckColl[i].checked == false){
				bool = false;
			}
		}
		if(bool == true){
			oCheckMain.checked = true;
		}
		else {
			oCheckMain.checked = false;
		}
	}
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