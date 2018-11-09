<%--
/*
 * 程序名称：f004-v.jsp
 * 功能说明：定期开立提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
--%>

<!--Header start-->
<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.ConfigConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obquery.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>

<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType"%>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
String strTitle = "定期开立";
//String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
//String signatureOriginalValue = "";
//boolean isValidateFromDB = false;
//boolean isNeedApproval = false;

try {
	
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


	long lSourceType = 0;
	String strSource = request.getParameter("src");
	if ((strSource != null) && (strSource.length() > 0)) {
		lSourceType = Long.parseLong(strSource);
	}
	String type = "";
	String _type = request.getParameter("type");
	if ((type != null) && (type.length() > 0)) {
		type = _type;
	}
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
	if ((strCheckType != null) && (strCheckType.length() > 0)) {
		lCheckType = Long.parseLong(strCheckType);
	}
	String lTransType = "";
	lTransType = (String) request.getParameter("lTransType");
	if (lTransType == null)
		lTransType = (String) request.getParameter("txtTransType");

	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if (isNeedApproval == null)
		isNeedApproval = "";

	
	FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
	if(financeInfo == null){
		response.sendRedirect(strContext + "/capital/financeinstr/view/fi_v001.jsp");
	}
	else{
		if(financeInfo.getSBatchNo() == null){
			financeInfo.setSBatchNo("");
		}
	}
	

	boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng, financeInfo);
		
		
 	QueryCapForm queryCapForm = (QueryCapForm)request.getAttribute("queryCapForm");
	if(queryCapForm == null){
		queryCapForm = new QueryCapForm();
		queryCapForm.setStartSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
		queryCapForm.setEndSubmit(DataFormat.getDateString(Env.getSystemDateTime()));
	}

		System.out
		.println("financeInfo.getNDepositBillStatusId()的值是================"
				+ financeInfo.getNDepositBillStatusId());
		boolean isbill = false;
		if (financeInfo.getNDepositBillInputuserId() > 0)
			isbill = true;
		if (isbill)
			strTitle = "[换开定期存单]";
		else
			strTitle = "[定期开立]";

		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

		String strTransNo = financeInfo.getID() + "";
		boolean blnNotBeFalsified = true;
		boolean blnUseITrusCert = Config.getProperty(
		ConfigConstant.GLOBAL_TROY_NAME,
		Constant.GlobalTroyName.NotUseCertificate).equals(
		Constant.GlobalTroyName.ITrus);


/*		if (blnUseITrusCert) {
			//add by mingfang 2007-05-24 
			String[] nameArray = OBSignatureConstant.OpenFixDeposit
			.getSignNameArray();
			String[] valueArray = OBSignatureConstant.OpenFixDeposit
			.getSignValueArrayFromInfo(financeInfo);
			if (OBSignatureUtil.isIdHaveNotRealValue(financeInfo,blnIsNeedApproval)) {
				//特殊处理	
				valueArray[5] = "-1";
			}
			
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(	nameArray, valueArray, financeInfo.getSignatureValue());
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}
*/
		double dMinSinglePayAmount = 0.0;
		long lIsSoft = 0;
		String strTemp = (String) request
		.getAttribute("dMinSinglePayAmount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dMinSinglePayAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("lIsSoft");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lIsSoft = Long.valueOf(strTemp).longValue();
		}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%
if (blnUseITrusCert && !blnNotBeFalsified) {
%>
<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%
}
%>

<form name="frm" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="<%=OBConstant.QueryOperationType.QUERY%>">  <!--操作代码-->
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

	<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="" align="center">
		<tr>
			<td height="4"></td>
		</tr>
		<tr>
			<td height="1" bgcolor="#47BBD2"></td>
		</tr>
		<tr>
			<td height="24" valign="top">
				<table width="150" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="1" bgcolor="#47BBD2"></td>
						<td width="124" background="/webob/graphics/new_til_bg.gif">
							<span class="txt_til2">定期开立</span>
						</td>
						<td width="25" align="right">
							<img src="/webob/graphics/newconner.gif" width="30" height="22">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="5"></td>
		</tr>
	</table>
	<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3">
							<img src="/webob/graphics/lab_conner1.gif" width="3" height="23">
						</td>
						<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">
							活期账户
						</td>
						<td width="17">
							<img src="/webob/graphics/lab_conner3.gif" width="16" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>

			<td width="130" height="25" class="MsoNormal">
				活期账户名称：
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getPayerName() == null
						|| financeInfo.getPayerName() == "" ? "&nbsp;"
						: financeInfo.getPayerName()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<!--
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">银行账号：</td>
          <td width="430" height="25" class="graytext"><%=financeInfo.getPayerBankNo()%></td>
          <td width="5"></td>
        </tr>
		-->
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>

			<td width="130" height="25" class="MsoNormal">
				活期账号：
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayerAcctNo())%>
			</td>
			<td width="5" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3">
							<img src="/webob/graphics/lab_conner1.gif" width="3" height="23">
						</td>
						<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">
							定期账户
						</td>
						<td width="17">
							<img src="/webob/graphics/lab_conner3.gif" width="16" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>


	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					定期账号：
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayeeAcctNo())%>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<!-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal" width="141">定期留存金额：</td>
      <td class="MsoNormal" width="26">
      <div align="right"><%//= sessionMng.m_strCurrencySymbol %>&nbsp;</div>
      </td>
      <td width="547" height="25" class="MsoNormal"><%//= financeInfo.getMamOuntForTrans() %></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
     -->
		<tr class="MsoNormal">
			<td colspan="6" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					定期存款期限：
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal">
				<%=financeInfo.getFixedDepositTime()>10000?financeInfo.getFixedDepositTime()-10000:financeInfo.getFixedDepositTime()%>
				<%=(financeInfo.getFixedDepositTime() > 10000) ? "天"
								: "个月"%>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<%-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>到期是否续存：</p></td>
      <td width="547" height="25" class="MsoNormal">
        <% long isFixContinue =financeInfo.getIsFixContinue();System.out.print("MMMMMMMMMMMMMM"+financeInfo.getIsFixContinue()); if(isFixContinue > 1){%>
        到期不续存
        <%}else{  %>
        到期续存
        <% }%>
      </td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal" >备注：</td>
      <td width="547" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getFixEdremark()) %></td>
      <td width="10" class="MsoNormal"></td>
    </tr>
    --%>
	</table>

	<br>
	<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3">
							<img src="/webob/graphics/lab_conner1.gif" width="3" height="23">
						</td>
						<td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2">
							划款资料
						</td>
						<td width="17">
							<img src="/webob/graphics/lab_conner3.gif" width="16" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				金额：
			</td>
			<td width="20" height="25" class="MsoNormal">
				<div align="right">
					<%=sessionMng.m_strCurrencySymbol%>
				</div>
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getFormatAmount()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td width="130" height="25" class="MsoNormal" colspan="2">
				执行日：
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getFormatExecuteDate()%>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td width="130" height="25" class="MsoNormal" colspan="2">
				汇款用途：
			</td>
			<td width="430" height="25" class="MsoNormal">
				<%=DataFormat.formatString(financeInfo.getNote())%>
			</td>
			<td width="5" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<%
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)
			|| // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
			|| // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
			|| // 已签认
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
			|| //处理中
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
			|| //已完成
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)
			|| //已拒绝 
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE)
			|| // 已确认,未复核(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
			|| // 已复核(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
			|| // 已签认(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
			|| //处理中(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)
			|| //已完成(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝(换开)  		   
			{
	%>
	<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
				<table width="180" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3">
							<img src="/webob/graphics/lab_conner1.gif" width="3" height="23">
						</td>
						<td width="160" background="/webob/graphics/lab_conner2.gif" class="txt_til2">
							交易申请处理详情
						</td>
						<td width="17">
							<img src="/webob/graphics/lab_conner3.gif" width="16" height="23">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal>
		<thead>
			<tr>
				<td width="63" height="19">
					<p align="center">
						序列号
					</p>
				</td>

				<td height="19" valign="middle" width="190">
					<div align="center">
						用户
					</div>
				</td>

				<td height="19" valign="middle" width="198">
					<div align="center">
						工作描述
					</div>
				</td>

				<td height="19" valign="middle" width="269">
					<div align="center">
						时间和日期
					</div>
				</td>
			</tr>
		</thead>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					1
				</div>
			</td>

			<td width="190" height="25">

				<div align="center">
					<%
					if (isbill) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillInputuserId())%>
					<%
					} else {
					%>
					<%=financeInfo.getConfirmUserName()%>
					<%
					}
					%>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					录入
				</div>
			</td>

			<td width="269" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=financeInfo.getDtDepositBillInputdate()
										.toString().substring(0, 19)%>
					<%
					} else {
					%>
					<%=financeInfo.getFormatConfirmDate()%>
					<%
					}
					%>
				</div>
			</td>
		</tr>

		<%
				}
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
				|| // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) || //已完成
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝 
				&& (financeInfo.getNDepositBillInputuserId() == 0)) {
		%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center">
					<%=financeInfo.getCheckUserName() == null ? "机核"
									: financeInfo.getCheckUserName()%>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					复核
				</div>
			</td>

			<td width="269" height="25">
				<div align="center">
					<%=financeInfo.getFormatCheckDate()%>
				</div>
			</td>
		</tr>
		<%
				}
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
				|| // 已复核(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //已完成(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
				&& //已拒绝(换开)
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
				&& (financeInfo.getDtDepositBillCheckdate() != null)) {
		%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillCheckuserId())%>
					<%
					}
					%>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					复核
				</div>
			</td>

			<td width="269" height="25">
				<div align="center">
					<%
					if (isbill) {
					%>
					<%=financeInfo.getDtDepositBillCheckdate()
										.toString().substring(0, 19)%>
					<%
					}
					%>
				</div>
			</td>
		</tr>

		<%
				}
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) || //已完成
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE))
				&& //已拒绝
				(financeInfo.getSignUserName() != null && !"".equals(financeInfo.getSignUserName()))
				&& (financeInfo.getNDepositBillInputuserId() == 0)) {
		%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="190" height="25">
				<div align="center">
					<%=financeInfo.getSignUserName()%>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					签认
				</div>
			</td>

			<td width="269" height="25">
				<div align="center">
					<%=financeInfo.getFormatSignDate()%>
				</div>
			</td>
		</tr>
		<%
		}
		%>
		<%
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH) || //已完成(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))
				&& //已拒绝(换开)
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
				&& (financeInfo.getDtDepositBillSignDate() != null)) {
		%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="190" height="25">
				<div align="center">
					<%
					if ((isbill)) {
					%>
					<%=NameRef.getUserNameByID(financeInfo
										.getNDepositBillSignUserID())%>
					<%
					}
					%>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					签认
				</div>
			</td>

			<td width="269" height="25">
				<div align="center">
					<%
					if ((isbill)) {
					%>
					<%=financeInfo.getDtDepositBillSignDate()
										.toString().substring(0, 19)%>
					<%
					}
					%>
				</div>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<br />
	<br>
	<%
	if (isbill) {
	%>
	<table width="774" border="0" align="center" cellspacing="0" cellpadding="0" class=normal>
		<tr class="tableHeader">
			<!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
			<td colspan="2" height="22" class=FormTitle>
				<font size="3"><b>换开定期存单详情</b>
				</font>
			</td>
			<!--td width="5" height="22" bgcolor="#0C3869"></td-->
		</tr>

		<tr>
			<td width="23%" height="25" class="MsoNormal">
				&nbsp;换开定期存单录入摘要：
			</td>
			<td width="77%">
				<%=financeInfo.getSDepositBillAbstract() == null ? ""
									: financeInfo.getSDepositBillAbstract()%>
			</td>
		</tr>
		<%
					if (financeInfo.getNDepositBillStatusId() != OBConstant.SettInstrStatus.SAVE
					|| lCheckType > -1) {
		%>
		<tr>
			<td width="23%" height="25" class="MsoNormal">
				&nbsp;换开定期存单复核摘要：
			</td>
			<td width="77%">
				<span class="MsoNormal"> <%
 				if (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK
 				|| financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) {
 %>
					<%=financeInfo
													.getSDepositBillCheckAbstract() == null ? ""
											: financeInfo
													.getSDepositBillCheckAbstract()%> <%
 } else {
 %>
					<textarea name="lAbstractID" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;"
						onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield ='';" type="_moz">
						<%=financeInfo
													.getSDepositBillCheckAbstract() == null ? ""
											: financeInfo
													.getSDepositBillCheckAbstract()%>
					</textarea> <%
 }
 %> </span>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	%>
	<%--
    <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	<fs:uploadFile strContext='<%=strContext%>' showOnly='true'
		transCode='<%=strtransNo%>' caption="上传" lid='-1'
		moduleID='<%=Constant.ModuleType.SETTLEMENT%>' transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
		transSubTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' currencyID='<%=sessionMng.m_lCurrencyID%>'
		officeID='<%=sessionMng.m_lOfficeID%>' clientID='<%=sessionMng.m_lClientID%>'
		islowerunit = '<%=OBConstant.IsLowerun.ISNO%>' />
	<%--     </td>
        </tr>
    </table>
 --%>
	<br>
	<table border="0" width="774" cellspacing="0" cellpadding="0" align="center">
		<!-- 历史审批意见 -->
		<TR>
			<TD colspan="3">
				<%--<iframe
					src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>"
					width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
			</TD>
		</TR>
		<!-- 历史审批意见 -->
		<tr>
			<td width="774" align="right">

				<%
						/* 从指令查询页面过来的 */
						String search = "";
						if (request.getAttribute("search") != null) {
							search = (String) request.getAttribute("search");
						}
						String isSign="";
						if(request.getParameter("sign")!=null){
						isSign=request.getParameter("sign");
						}
						/* 确认、修改、删除 */
						if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo
						.getStatus() == OBConstant.SettInstrStatus.SAVE))
						&& (sessionMng.m_lUserID == financeInfo
								.getConfirmUserID())) {// 未、已确认、登录人＝确认人
				%>

				<input class=button1 name=add type=button value=" 修改 " onClick="Javascript:updateme();"
					onKeyDown="Javascript:updateme();">
				&nbsp;&nbsp;

				<input class=button1 name=add type=button value=" 删除 " onClick="Javascript:deleteme();"
					onKeyDown="Javascript:deleteme();">
				&nbsp;&nbsp;
				<%
						}
						/* 复核匹配*/
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)
						&& (sessionMng.m_lUserID != financeInfo
								.getConfirmUserID())
						&& (lCheckType != 1)
						&& !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();"
					onKeyDown="Javascript:checkmatchme();">
				&nbsp;&nbsp;
				<%
						}
						/* 复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
						.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
						&& (sessionMng.m_lUserID != financeInfo
								.getConfirmUserID())
						&& (lCheckType == 1)
						& !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();"
					onKeyDown="Javascript:checkme();">
				&nbsp;&nbsp;
				<%
						}
						/* 换开复核 */
						System.out.println("sessionMng.m_lUserID="
						+ sessionMng.m_lUserID);
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (sessionMng.m_lUserID != financeInfo
								.getNDepositBillInputuserId())
						&& (lCheckType == 1) && isbill & !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();"
					onKeyDown="Javascript:checkme();">
				&nbsp;&nbsp;
				<%
						}
						/* 取消复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
						&& (sessionMng.m_lUserID == financeInfo
								.getCheckUserID()) & !search.equals("1") && !isSign.equals("1")) {// 已复核、登录人＝复核人
				%>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();"
					onKeyDown="Javascript:cancelcheckme();">
				&nbsp;&nbsp;
				<%
						}
						/* 换开取消复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (sessionMng.m_lUserID == financeInfo
								.getNDepositBillCheckuserId())
						&& isbill
						& !search.equals("1") && !isSign.equals("1")) {// 已完成、登录人＝复核人
				%>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();"
					onKeyDown="Javascript:cancelcheckme();">
				&nbsp;&nbsp;
				<%
						}
						/* 签认及提交 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
						&& (financeInfo.getIsNeedSign() == true) && isSign.equals("1")) {// 已复核、需要被登录人签认=true
				%>

				<!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				<input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();"
					onKeyDown="Javascript:signme();">
				&nbsp;&nbsp;

				<%
						}
						/* 换开签认及提交 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK)
						&& isbill && (financeInfo.getIsNeedSign() == true) && isSign.equals("1")) {// 已完成、需要被登录人签认=true
				%>

				<!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				<input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();"
					onKeyDown="Javascript:signme();">
				&nbsp;&nbsp;

				<%
						}
						/* 取消签认 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN)
						&& (sessionMng.m_lUserID == financeInfo.getSignUserID()) && isSign.equals("1")) {// 已签认、登录人＝签认人
				%>

				<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				<input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();"
					onKeyDown="Javascript:cancelsignme();">
				&nbsp;&nbsp;

				<%
						}
						/* 换开取消签认 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (sessionMng.m_lUserID == financeInfo
								.getNDepositBillSignUserID())
						&& (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN)
						&& isbill && isSign.equals("1")) {// 已完成、登录人＝签认人
				%>

				<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->

				<input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();"
					onKeyDown="Javascript:cancelsignme();">
				&nbsp;&nbsp;

				<%
						}
						ArrayList a = Config.getArray(
						ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT, null);
						String strStatus = "" + financeInfo.getStatus();
				%>

				<input class=button1 name=add type=button value=" 返回 " onClick="javascript:returnme()"
					onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				
				<%
						///add by liuguang 2007-10-19  设置业务是否需要打印的提示
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
				<input type="Button" class="button1" value=" 打印 " width="46" height="18" onclick="javascript:PrintConsignVoucher()"
				<%  
					if(a!=null && !a.contains(strStatus))
					{
				%>
				disabled 
				<%
				}
				%>
				>
				&nbsp;&nbsp;
				<%
				}
				%>
			</td>
		</tr>
	</table>

	<input type="hidden" name="RemitType" value="<%=financeInfo.getRemitType()%>">
	<input type="hidden" name="Amount" value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="ExecuteDate" value="<%=financeInfo.getExecuteDate()%>">
	<input type="hidden" name="ConfirmUserID" value="<%=financeInfo.getConfirmUserID()%>">
	<input type="hidden" name="ConfirmDate" value="<%=financeInfo.getConfirmDate()%>">
	<input type="hidden" name="PayerAcctID" value="<%=financeInfo.getPayerAcctID()%>">
	<input type="hidden" name="PayeeAcctID" value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
	<input type="hidden" name="txtID" value="<%=financeInfo.getID()%>">
	<input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	<input type="hidden" name="txtisCheck" value="">
	<input type="hidden" name="txtTransType" value="<%=lTransType%>">

	<!-- 签名用字段  add by mingfang -->
	<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>">
	<input type="hidden" name="nFixedDepositTime" value="<%=financeInfo.getFixedDepositTime()%>">
	<input type="hidden" name="dAmount" value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate()%>">

	<input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID()%>">
	<!-- 供c415.jsp判断业务类型用-->
	<input type="hidden" name="SelectType" value="<%=financeInfo.getTransType()%>">


</form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("<%=strContext%>/common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}


	/*返回处理函数 */
	function returnme()
	{
		frm.action = "<%=strContext%>/capital/query/control/query_c001.jsp";
		frm.strSuccessPageURL.value = "<%=strContext%>/capital/query/view/query_v002.jsp";
		frm.strFailPageURL.value = "<%=strContext%>/capital/query/view/query_v001.jsp";
		frm.strAction.value = "<%=OBConstant.QueryOperationType.QUERY%>";
		showSending();
	    frm.submit();
	}

	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/f001-c.jsp?isupdate=isupdate";
		frm.submit();
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		//showMenu();
		frm.action="<%=strContext%>/capital/fixed/f005-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}%>
		
	    if(!<%=dMinSinglePayAmount%> == 0.0 ){
			if(parseFloat(<%=dMinSinglePayAmount%>)>reverseFormatAmount(frm.dAmount.value))
			{
				alert("存款金额小于存款起点金额，起点金额为：<%=sessionMng.m_strCurrencySymbol%>" + <%=dMinSinglePayAmount%>);
				if(parseInt(<%=lIsSoft%>) == 1) {
					return false;
				}
			}
		} 
		frm.action="<%=strContext%>/capital/check/C415.jsp?fuhe=fuhe";
		frm.txtisCheck.value = "1";
		frm.txtTransType.value = "2";
		frm.submit();
	}
	
	/* 复核处理函数 */
	function checkmatchme()
	{
		//showMenu();
		
		frm.action="<%=strContext%>/capital/check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("是否取消复核？"))
		{
			//showMenu();
			
			<%
				if(blnUseITrusCert){
					OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
					OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
			%>
					var signatureValue = DoSign(frm,nameArray,valueArray);
					if(signatureValue == "") return;//签名不成功，不允许提交表单	
			<%}%>
			
			
			frm.action="<%=strContext%>/capital/check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("是否取消签认？"))
		{
			//showMenu();
			frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frm.action="<%=strContext%>/capital/captransfer/S00-Ipt.jsp";
		frm.target="new_window";
		frm.submit();
		frm.target="";
	}

</script>

<%
	OBHtml.showOBHomeEnd(out);
	} catch (Exception e) {
		e.printStackTrace();
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e, strTitle, "", 1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>