<%--
 页面名称 ：fi_v004.jsp
 页面功能 : 网上银行 - 逐笔付款  审批/取消审批页面
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
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.ExtSystemSettingBiz"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "逐笔付款";
String strTemp = "";

String strAction = "";
long osentryId = -1;
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
	
	strTemp = (String)request.getAttribute("strAction");
	if(strTemp != null && !strTemp.equals("")){
		strAction = strTemp;
	}
	
	strTemp = (String)request.getAttribute("osentryId");
	if (strTemp != null && strTemp.trim().length() > 0){
		osentryId = Long.parseLong(strTemp);
	}
	
	String strSourceType = "";

	ExtSystemSettingInfo extSystemSettingInfo = new ExtSystemSettingInfo();
	ExtSystemSettingBiz extSystemSettingBiz = new ExtSystemSettingBiz();
	if(financeInfo.getSource()!=-1)
	{
		extSystemSettingInfo = extSystemSettingBiz.findExtSystemSettingByID(financeInfo.getSource());
		strSourceType = extSystemSettingInfo.getSName();
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
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript">
<!--
//控制不同付款方式的显示
function disRemitType(form){
	var remitType = form.nRemitType.value;
	var oTdBankpay = document.getElementById("tdBankpay");
	var oTdInternalvirement = document.getElementById("tdInternalvirement");
	var oTrPayeeProv = document.getElementById("trPayeeProv");
	var oTrPayeeBankName = document.getElementById("trPayeeBankName");
	var oTrPayeeBankCNAPSNO = document.getElementById("trPayeeBankCNAPSNO");           
	var oTrPayeeBankOrgNO = document.getElementById("trPayeeBankOrgNO");           
	var oTrPayeeBankExchangeNO = document.getElementById("trPayeeBankExchangeNO");           
	
	if(remitType == "<%=OBConstant.SettRemitType.BANKPAY%>"){
		oTdBankpay.style.display = "block";
		oTdInternalvirement.style.display = "none";
		oTrPayeeProv.style.display = "block";
		oTrPayeeBankName.style.display = "block";
		oTrPayeeBankCNAPSNO.style.display = "block";
		oTrPayeeBankOrgNO.style.display = "block";
		oTrPayeeBankExchangeNO.style.display = "block";
	}
	
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
		oTdBankpay.style.display = "none";
		oTdInternalvirement.style.display = "block";
		oTrPayeeProv.style.display = "none";
		oTrPayeeBankName.style.display = "none";
		oTrPayeeBankCNAPSNO.style.display = "none";
		oTrPayeeBankOrgNO.style.display = "none";
		oTrPayeeBankExchangeNO.style.display = "none";
	}
}
//-->
</script>
<form name="form1" method="post" action="<%=strContext%>/capital/financeinstr/control/fi_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="<%=strContext%>/capital/financeinstr/view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="<%=strContext%>/capital/financeinstr/view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.QueryOperationType.QUERY%>">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="nRemitType" value="<%=financeInfo.getRemitType()%>">
<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>">
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="dAmount" value="<%=financeInfo.getAmount()%>">
<input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID()%>">
<input type="hidden" name="osentryId" value="<%=osentryId%>">
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
<table cellpadding="0" cellspacing="0" class="title_top">
<tbody>
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">付款方资料</span></td>
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
			<table  border="0" cellspacing="0" cellpadding="0" align="">
			
			    <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2"> 收款方资料</td>
				<td width="683"><a class=lab_title3></td>
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
		         <tr id="trPayeeBankCNAPSNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">汇入行CNAPS号：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getSPayeeBankCNAPSNO()==null?"":financeInfo.getSPayeeBankCNAPSNO() %>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		         <tr id="trPayeeBankOrgNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">汇入行机构号：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getSPayeeBankOrgNO()==null?"":financeInfo.getSPayeeBankOrgNO() %>
		          	</td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		         <tr id="trPayeeBankExchangeNO">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">汇入行联行号：</td>
		          	<td width="500" height="25">
		          		<%=financeInfo.getSPayeeBankExchangeNO()==null?"":financeInfo.getSPayeeBankExchangeNO() %>
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
			<table  border="0" cellspacing="0" cellpadding="0" align="">
			   <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2"> 划款资料</td>
				<td width="683"><a class=lab_title3></td>
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
		          	<td width="130" height="25" align="left">数据来源：</td>
		          	<td width="500" height="25">
		            	<%=financeInfo.getSource()==-1?"":strSourceType %>
		            </td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <%
		        	if(financeInfo.getSource()!=SETTConstant.ExtSystemSource.EBANK)
		        	{
		         %>
		         <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">外部系统指令序号：</td>
		          	<td width="500" height="25">
		            	<%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %>
		            </td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        <%
		        	}
		         %>
		        
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
	<%if(strAction.equals("doApproval")){ %>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
					<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">审批意见</td>
					<td width="16"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
					<td width="*%" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
  	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		    <%
				String strMagnifierNameRemark = "审批意见";
				String strFormNameRemark = "form1";
				String strMainPropertyRemark = "";
				String strPrefixRemark = "";
				String[] strMainNamesRemark = {com.iss.inut.workflow.constants.Constants.WF_ADVISE};
				String[] strMainFieldsRemark = {"Description"};
				String strReturnInitValuesRemark="";
				String[] strReturnNamesRemark = {"txtsOptionID"};
				String[] strReturnFieldsRemark = {"id"};
				String[] strReturnValuesRemark = {""};
				String[] strDisplayNamesRemark = {"审批意见编号","审批意见内容"};
				String[] strDisplayFieldsRemark = {"Code","Description"};
				int nIndexRemark = 1;
				String strSQLRemark = "select * from sys_approvalopinion where officeid="+sessionMng.m_lOfficeID+" and currencyid="+sessionMng.m_lCurrencyID+" and moduleid="+sessionMng.m_lModuleID+" and statusid="+Constant.RecordStatus.VALID;
				String strMatchValueRemark = "Description";
				String strNextControlsRemark = "";
				String strTitleRemark = "审批意见";
				String strFirstTDRemark="align='center'";
				String strSecondTDRemark="colspan='2'";	
				Magnifier.showTextAreaCtrlForEbank(out,strMagnifierNameRemark,strFormNameRemark,strPrefixRemark,strMainNamesRemark,strMainFieldsRemark,strReturnNamesRemark,strReturnFieldsRemark,strReturnInitValuesRemark,strReturnValuesRemark,strDisplayNamesRemark,strDisplayFieldsRemark,nIndexRemark,strMainPropertyRemark,strSQLRemark,strMatchValueRemark,strNextControlsRemark,strTitleRemark,strFirstTDRemark,strSecondTDRemark);
			%> 
			</table>
		</td>
	</tr>
	<tr>
		<td height="10">&nbsp;</td>
	</tr>
	<%} %>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
					<%
						if(strAction.equals("doApproval")){
					%>
						<isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval(form1, this)" />
					<%
						}
						else if(strAction.equals("cancelApproval")){
					%>
						<input type="button" value=" 取消审批 " class="button1" name="butCancelApproval" onclick="toCancelApproval(form1)"/>
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

function doApproval(form, oBut)
{
	form.action = "<%=strContext%>/capital/financeinstr/control/fi_c003.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/approval/view/v033.jsp";
	form.strFailPageURL.value = "<%=strContext%>/approval/view/v033.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
	
    //modify by xwhe 2008-11-11 去掉审批意见必须项
	/*if(form.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
	{
		alert("请输入审批意见");
		form.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
		return;
	}*/

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
		
	if(confirm("是否" + oBut.value + "？"))
	{
		showSending();
	    form.submit();
	}
}	

function toCancelApproval(form)
{
	form.action = "<%=strContext%>/capital/financeinstr/control/fi_c003.jsp";
	form.strSuccessPageURL.value = "<%=strContext%>/approval/view/v036.jsp";
	form.strFailPageURL.value = "<%=strContext%>/approval/view/v036.jsp";
	form.strAction.value = "<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";

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
		
	if(confirm("是否取消审批？"))
	{
		showSending();
	    form.submit();
	}
}
	
function toReturn(form){
	showSending();
	<%if(strAction.equals("finished")){%>
		window.location.href = "<%=strContext%>/approval/control/c034.jsp";
	<%}else if(strAction.equals("cancelApproval")){%>
		window.location.href = "<%=strContext%>/approval/view/v036.jsp";
	<%}else{%>
		window.location.href = "<%=strContext%>/approval/view/v033.jsp";
	<%}%>
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