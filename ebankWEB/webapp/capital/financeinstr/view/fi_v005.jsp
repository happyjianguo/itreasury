<%--
 页面名称 ：fi_v005.jsp
 页面功能 : 网上银行 - 逐笔付款  修改/删除
 作    者 ：leiyang
 日    期 ：2008-12-01
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.info.*"%>
<%@ page import="com.iss.itreasury.safety.signature.*"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "逐笔付款";
String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
String signatureOriginalValue = "";
boolean isValidateFromDB = false;
boolean isNeedApproval = false;

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
		response.sendRedirect(strContext + "/capital/financeinstr/view/fi_v001.jsp");
	}
	else{
		if(financeInfo.getSBatchNo() == null){
			financeInfo.setSBatchNo("");
		}
	}
	
	if(financeInfo.getSBatchNo().equals("")){
		isNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
	}
	
 	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
	if(queryCapForm == null){
		queryCapForm = new QueryCapForm();
		queryCapForm.setStartSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
		queryCapForm.setEndSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
	}
	
	//验证数据是否非法修改
	if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
		String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
		String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromInfo(financeInfo);
		
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		signatureOriginalValue = SignatureUtil.formatData(signatureInfo);

		try {
			isValidateFromDB = SignatureAuthentication.validateFromDB(signatureInfo);
			if(isValidateFromDB == false){
				valueArray[3] = "-1";
				signatureInfo.setValueArray(valueArray);
				signatureOriginalValue = SignatureUtil.formatData(signatureInfo);
				isValidateFromDB = SignatureAuthentication.validateFromDB(signatureInfo);
			}
			System.out.println("signatureValue：" + financeInfo.getSignatureValue());
			System.out.println("signatureOriginalValue：" + signatureOriginalValue.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new IException(e.getMessage());
		}
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
	var oTdBankpay = document.getElementById("tdBankpay");
	var oTdInternalvirement = document.getElementById("tdInternalvirement");
	var oTrPayeeProv = document.getElementById("trPayeeProv");
	var oTrPayeeBankName = document.getElementById("trPayeeBankName");
	
	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
		oTdBankpay.style.display = "block";
		oTdInternalvirement.style.display = "none";
		oTrPayeeProv.style.display = "block";
		oTrPayeeBankName.style.display = "block";
	}
	
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
		oTdBankpay.style.display = "none";
		oTdInternalvirement.style.display = "block";
		oTrPayeeProv.style.display = "none";
		oTrPayeeBankName.style.display = "none";
	}
}
//-->
</script>
<form name="form1" method="post" action="../control/fi_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.QueryOperationType.QUERY%>">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo()%>">
<input type="hidden" name="nRemitType" value="<%=financeInfo.getRemitType()%>">
<!-- 查询申请指令的隐藏域 -->
<input type="hidden" name="lTransType" value="<%=queryCapForm.getTransType()%>">
<input type="hidden" name="lDepositID" value="<%=queryCapForm.getDepositID()%>">
<input type="hidden" name="strDepositNo" value="<%=queryCapForm.getDepositNo()%>">
<input type="hidden" name="lStatus" value="<%=queryCapForm.getStatus()%>">
<input type="hidden" name="sStartExe" value="<%=queryCapForm.getStartExe()%>">
<input type="hidden" name="sEndExe" value="<%=queryCapForm.getEndExe()%>">
<input type="hidden" name="dMinAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMinAmount())%>">
<input type="hidden" name="dMaxAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>">
<input type="hidden" name="sStartSubmit" value="<%=queryCapForm.getStartSubmit()%>">
<input type="hidden" name="sEndSubmit" value="<%=queryCapForm.getEndSubmit()%>">
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
<table width="800" align="center"  border="0" cellspacing="0" cellpadding="0">
<tbody>
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
		          	<td width="130" height="25" align="left">指令序号：</td>
		          	<td width="500" height="25">
		            	<%=financeInfo.getID()%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">付款方名称：</td>
		          	<td width="500" height="25">
		            	<%=sessionMng.m_strClientName%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td width="4" height="25">&nbsp;</td>
					<td  width="130" height="25" align="left">付款方账号：</td>
					<td width="500" height="25">
						<%=financeInfo.getPayerAcctNo()%>
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
		          	<td width="130" height="25" align="left">汇款方式：</td>
		          	<td width="500" height="25">
				  		<%=OBConstant.SettRemitType.getName(financeInfo.getRemitType())%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
			  	<tr id="tdBankpay">
					<td width="4" height="25">&nbsp;</td>
					<td  width="130" height="25" align="left">
						收款方账号：
					</td>
					<td width="500" height="25">
						<%=financeInfo.getPayeeAcctNo()%>
					</td>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
			  	<tr id="tdInternalvirement">
					<td width="4" height="25">&nbsp;</td>
					<td  width="130" height="25" align="left">
						收款方账号：
					</td>
					<td width="500" height="25">
						<%=financeInfo.getPayeeAcctNo()%>
					</td>
					<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		 		</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">收款方名称：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getPayeeName()%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeProv">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">汇入地：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getPayeeProv()%> 省 <%=financeInfo.getPayeeCity()%> 市（县）
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr id="trPayeeBankName">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">汇入行名称：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getPayeeBankName()%>
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
		          	<td width="130" height="25" align="left">金额：<%=sessionMng.m_strCurrencySymbol%></td>
		          	<td width="500" height="25">
			            <%=DataFormat.formatListAmount(financeInfo.getAmount())%> 元
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">大写金额：</td>
		          	<td width="500" height="25">
		            	<%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">执行日：</td>
		          	<td width="500" height="25">
						<%=financeInfo.getFormatExecuteDate()%>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left" valign="top">汇款用途：</td>
		          	<td width="500" height="25">
            			<%=financeInfo.getNote()%>
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
<% 
	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
		(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
       	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
       	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
       	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) || //已拒绝
       	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)|| //已审批
       	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) //审批中		   
	{
%>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="150" background="/webob/graphics/lab_conner2.gif" class="txt_til2">交易申请处理详情</td>
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
			          	<td width="25%" height="25" align="center">序列号</td>
			          	<td width="25%" height="25" align="center">用  户</td>
			          	<td width="25%" height="25" align="center">工作描述</td>
			          	<td width="25%" height="25" align="center">时间和日期</td>
			        </tr>
			    </thead>
			    <tbody>
			        <tr>
			          	<td width="25%" height="25" align="center">1</td>
			          	<td width="25%" height="25" align="center"><%=financeInfo.getConfirmUserName()%></td>
			          	<td width="25%" height="25" align="center">录入</td>
			          	<td width="25%" height="25" align="center"><%=financeInfo.getFormatConfirmDate()%></td>
			        </tr>
<% 
	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
      	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
      	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
      	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
    {
%>
			        <tr>
			          	<td width="25%" height="25" align="center">2</td>
			          	<td width="25%" height="25" align="center"><%=financeInfo.getCheckUserName()%></td>
			          	<td width="25%" height="25" align="center">复核</td>
			          	<td width="25%" height="25" align="center"><%=financeInfo.getFormatCheckDate()%></td>
			        </tr>
<%
	}
%>
			    </tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
<%
	}
	if(isNeedApproval == true){
%>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">历史审批意见</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<!-- 历史审批意见 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
				<TR>
					<TD colspan="3">
					<% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
					<%
						if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY){
			        %>
						<fs:HistoryOpinionFrame
						  strTransNo='<%=strtransNo%>'
						  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
						  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>'
						  clientID='<%=sessionMng.m_lClientID%>'
						  currencyID='<%=sessionMng.m_lCurrencyID%>'
						  officeID='<%=sessionMng.m_lOfficeID%>'
						  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
					<%
						}
					    else if(financeInfo.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT){
					%>
						<fs:HistoryOpinionFrame
						  strTransNo='<%=strtransNo%>'
						  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
						  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>'
						  clientID='<%=sessionMng.m_lClientID%>'
						  currencyID='<%=sessionMng.m_lCurrencyID%>'
						  officeID='<%=sessionMng.m_lOfficeID%>'
						  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
					<%
						}
					%>
					</TD>
				</TR>
			</table>
			<!-- 历史审批意见 -->
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
<%
	}
%>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
					<%
						/* 修改、删除 */
						if(financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE && sessionMng.m_lUserID == financeInfo.getConfirmUserID()){
					%>
						<input type="button" value=" 修 改 " class="button1" name="butUpdate" onclick="toUpdate(form1)"/>&nbsp;
						<input type="button" value=" 删 除 " class="button1" name="butDelete" onclick="toDelete(form1)"/>&nbsp;
					<%
						}
					%>
						<input type="button" value=" 返 回 " class="button1" name="butReturn" onclick="toReturn(form1)"/>
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
setFormName("form1");
disRemitType(form1);

function toUpdate(form)
{
	if(confirm("是否修改？"))
	{
		form.action = "<%=strContext%>/capital/financeinstr/control/fi_c001.jsp";
		if(form.sbatchNO.value == ""){
			form.strSuccessPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
		}
		else{
			form.strSuccessPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v002.jsp";
		}
		form.strFailPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
		form.strAction.value = "";
		showSending();
	    form.submit();
    }
}

function toDelete(form)
{
	if(confirm("是否删除？"))
	{
		form.action = "<%=strContext%>/capital/financeinstr/control/fi_c004.jsp";
		form.strSuccessPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
		form.strFailPageURL.value = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
		form.strAction.value = "<%=OBConstant.SettInstrStatus.DELETE%>";
		showSending();
	    form.submit();
    }
}

function toReturn(form){
	form.action = "<%=strContext%>/capital/query/control/query_c001.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/capital/query/view/query_v002.jsp";
	form.strFailPageURL.value = "<%=strContext%>/capital/query/view/query_v001.jsp";
	form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
	showSending();
    form.submit();
}

function validate(form){
	if(!checkAmount(form.dAmount, 1, "交易金额")){
		return false;
	}

	if(!checkDate(form.tsExecute, 1 ,"执行日")){
		return false;
	}
	
	if(!InputValid(form.sNote, 1, "textarea", 1, 0, 100,"汇款用途")){
		return false;
	}
	
	return true;
}

<%
	if(strTroyName.equals(Constant.GlobalTroyName.ITrus) && !isValidateFromDB){
%>
	showRecordBeFalsifiedPrompt(); 
<%
	}
%>
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