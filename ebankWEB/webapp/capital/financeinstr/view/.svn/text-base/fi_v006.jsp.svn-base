<%--
 页面名称 ：fi_v006.jsp
 页面功能 : 网上银行 - 逐笔付款  复核/取消复核页面
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
<%@ page import="com.iss.itreasury.safety.util.*"%>

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "逐笔付款";
String strTemp = "";

String strAction = "";
boolean isNeedApproval = false;
String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
String signatureOriginalValue = "";
boolean isValidateFromDB = false;

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
	
	strTemp = (String)request.getAttribute("isNeedApproval");
	if(strTemp != null && !strTemp.equals("")){
		if(strTemp.equals("true")){
			isNeedApproval = true;
		}
	}
	
	//验证数据是否非法修改
/*	if(strTroyName.equals(Constant.GlobalTroyName.ITrus)){
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
	}*/
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
<form name="form1" method="post" action="">
<input type="hidden" name="strSuccessPageURL" value="">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="nRemitType" value="<%=financeInfo.getRemitType()%>">
<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>">
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="dAmount" value="<%=financeInfo.getAmount()%>">
<input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID()%>">
<!-- 取消复核查询的隐藏域 -->
<input type="hidden" name="lTransType" value="<%=queryCapForm.getTransType()%>">
<input type="hidden" name="lDepositID" value="<%=queryCapForm.getDepositID()%>">
<input type="hidden" name="strDepositNo" value="<%=queryCapForm.getDepositNo()%>">
<input type="hidden" name="sStartExe" value="<%=queryCapForm.getStartExe()%>">
<input type="hidden" name="sEndExe" value="<%=queryCapForm.getEndExe()%>">
<input type="hidden" name="dMinAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMinAmount())%>">
<input type="hidden" name="dMaxAmount" value="<%=DataFormat.formatListAmount(queryCapForm.getMaxAmount())%>">
<input type="hidden" name="sStartSubmit" value="<%=queryCapForm.getStartSubmit()%>">
<input type="hidden" name="sEndSubmit" value="<%=queryCapForm.getEndSubmit()%>">
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
<%------------------
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">当前余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="25">
						<%=financeInfo.getFormatCurrentBalance()%> 元
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
        		<tr>
          			<td width="4" height="25">&nbsp;</td>
          			<td width="130" height="25"align="left">可用余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="25">
          				<%=financeInfo.getFormatUsableBalance()%> 元
		  			</td>
          			<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
        		</tr>
-------------------%>
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
		        
		        <!-- Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"，使用手续费的配置文件 -->
		        <%
		        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)
		        	  && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY )
		        	{
		        %>
		        	<tr>
						<td width="4" height="25">&nbsp;</td>
			          	<td width="130" height="25">汇款区域/速度：</td>
			          	<td width="500" height="25">
			            	<%=Constant.remitAreaType.getName(financeInfo.getRemitArea()) %>&nbsp;
			            	<%=Constant.remitSpeedType.getName(financeInfo.getRemitSpeed()) %>
			          	</td>
			          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        	</tr>
		        <%
		        	}
		        %>
		        
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
						/* 复核 */
						if(isNeedApproval == true){
							if(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED && sessionMng.m_lUserID != financeInfo.getConfirmUserID()){
					%>
						<input type="button" value=" 复 核 " class="button1" name="butCheck" onclick="doCheck(form1)"/>&nbsp;
					<%
							}
						}
						else{
							if(financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE && sessionMng.m_lUserID != financeInfo.getConfirmUserID()){
					%>
						<input type="button" value=" 复 核 " class="button1" name="butCheck" onclick="doCheck(form1)"/>&nbsp;
					<%	
							}
						}
						/* 取消复核 */
						if(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK && sessionMng.m_lUserID != financeInfo.getConfirmUserID()){
					%>
						<input type="button" value=" 取消复核 " class="button1" name="butUnCheck" onclick="doUnCheck(form1)"/>&nbsp;
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

function doCheck(form){
	form.action = "<%=strContext%>/capital/financeinstr/control/fi_c005.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/capital/check/view/check_v013.jsp";
	form.strFailPageURL.value = "<%=strContext%>/capital/check/view/check_v001.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.CHECK%>";

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
		
	if(confirm("是否复核？"))
	{
		showSending();
	    form.submit();
	}
}

function doUnCheck(form){
	form.action = "<%=strContext%>/capital/financeinstr/control/fi_c005.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/capital/check/view/check_v003.jsp";
	form.strFailPageURL.value = "<%=strContext%>/capital/check/view/check_v001.jsp";
	form.strAction.value = "UNCHECK";

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
		
	if(confirm("是否取消复核？"))
	{
		showSending();
	    form.submit();
	}
}

function toReturn(form){
	if(form.lTransType.value == -1){
		showSending();
		window.location.href = "<%=strContext%>/capital/check/view/check_v002.jsp";
	}
	else {
		form.action = "<%=strContext%>/capital/check/control/check_c002.jsp";
		form.strSuccessPageURL.value = "<%=strContext%>/capital/check/view/check_v004.jsp";
		form.strFailPageURL.value = "<%=strContext%>/capital/check/view/check_v003.jsp";
		form.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
		showSending();
	    form.submit();
	}
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