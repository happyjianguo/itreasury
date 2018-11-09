<%--
/*
 * 程序名称：f004-v.jsp
 * 功能说明：定期开立提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月08日
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="java.util.ArrayList,com.iss.itreasury.util.ConfigConstant,com.iss.itreasury.ebank.util.*,com.iss.itreasury.util.*,com.iss.itreasury.ebank.obquery.dataentity.*,com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,com.iss.itreasury.util.OBFSWorkflowManager,com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
String strContext = request.getContextPath();
%>
<%!/* 标题固定变量 */
	String strTitle = "[定期开立]";%>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
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
	
	//是否为申请指令查询	
	String lOperate = null;
    String operate="";
	lOperate = (String)request.getParameter("operate");
	if (lOperate != null)
	{
	    operate = lOperate;
	}
 	String strID = (String)request.getAttribute("lID");
    //long lID = -1;
    //if((strID != null) && (strID.length()>0)){
    //	lID = Long.parseLong(strID);
    //}
	
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;

	/* 用户登录检测与权限校验及文件头显示 */
	try {
		/* 用户登录检测 
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
		 }*/
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		
	  	//String strID = (String)request.getAttribute("lInstructionID");
	    long lID = financeInfo.getID();
	    //if((strID != null) && (strID.length()>0)){
	    //	lID = Long.parseLong(strID);
	    //}
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(
		sessionMng, financeInfo);
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		boolean isbill = false;
		if (financeInfo.getNDepositBillInputuserId() > 0)
			isbill = true;
		if (isbill)
			strTitle = "[换开定期存单]";
		else
			strTitle = "[定期开立]";
		/**
		 * presentation start
		 */
		/* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);

		String strTransNo = financeInfo.getID() + "";

		boolean blnUseITrusCert = Config.getProperty(
		ConfigConstant.GLOBAL_TROY_NAME,
		Constant.GlobalTroyName.NotUseCertificate).equals(
		Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		//boolean isUseCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
		//boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //是否使用新安世纪证书验签
    	String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
		
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);

		if(isUseCertification)
		{
			String temp = null;	
			temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}			
		}
%>


<%
		double dMinSinglePayAmount = 0.0;
		long lIsSoft = 0;
		long rdoInterest = -1;
		String strTemp = (String) request
		.getAttribute("dMinSinglePayAmount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			dMinSinglePayAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("lIsSoft");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lIsSoft = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("rdoInterest");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			rdoInterest = Long.valueOf(strTemp).longValue();
		}
%>

<%
if (isUseCertification && !blnNotBeFalsified) {
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
<input type="hidden" name="dMinSinglePayAmount" value="<%=dMinSinglePayAmount%>">
<input type="hidden" name="rdoInterest" value="<%=rdoInterest %>">
<table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">定期开立</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		
	<br/>
	<% 
			if (lSourceType != 1) {
	%>


	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">

		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
				<!-- 	<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td class="lab_title2">
					//		<%
					//		if (isbill) {
					//		%>
					//		换开定期存单确认
					//		<%
					//		} else {
					//		%>
					//		定期开立确认
					//		<%
					//		}
					//		%>
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr> -->
					<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"><%
							if (isbill) {
							%>
							换开定期存单确认
							<%
							} else {
							%>
							定期开立
							<%
							}
							%></td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
		 
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr>
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="16" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal">

				<p>
					<br>
					<%
					if (isbill) {
					%>换开定期存单确认<%
					} else {
					%>定期开立<%
					}
					%>将<%
					if (isNeedApproval.equals("true")) {
					%>于审批后<%
					if (!OBFSWorkflowManager.isAutoCheck()) {
					%>等待复核人复核！<%
					} else {
					%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
								}
								} else if (!OBFSWorkflowManager.isAutoCheck()) {
					%>于复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
					} else {
					%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
					}
					%>
					<br>
					<!--br>
              已通知复核人复核！
			  <br-->
					<!--update by hyzeng 2003-4-10-->
					<%
					if (isNeedApproval.equals("true")) {
					%>
					<br>该笔交易有待审批人审批！<br>
					<%
					} else if (!OBFSWorkflowManager.isAutoCheck()) {
					%>
					<br>该笔交易有待复核人复核！<br>
					<%
					}
					%>
					<br>
					<b>指令序号为：<%=financeInfo.getID()%>
					</b>
					<br>
					<br>
				</p>
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<%
	}
	%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
					<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2">活期账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>

			<td width="20%" height="25" class="MsoNormal" nowrap>
				活期账户名称：
			</td>
			<td width="50%" height="25" class="MsoNormal">
				<%=financeInfo.getPayerName() == null
						|| financeInfo.getPayerName() == "" ? "&nbsp;"
						: financeInfo.getPayerName()%>
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>

			<td height="25" class="MsoNormal" nowrap>
				活期账号：
			</td>
			<td height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayerAcctNo())%>
			</td>
			<td class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
										<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 定期账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>


	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="20%" class="MsoNormal" nowrap>
				<p>
					定期账号：
				</p>
			</td>
			<td width="50%" height="25" class="MsoNormal">
				<%=NameRef.getNoLineAccountNo(financeInfo
								.getPayeeAcctNo())%>
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" nowrap>
				<p>
					定期存款期限：
				</p>
			</td>
			<td height="25" class="MsoNormal">
				<%=financeInfo.getFixedDepositTime()>10000?financeInfo.getFixedDepositTime()-10000:financeInfo.getFixedDepositTime()%>
				<%=(financeInfo.getFixedDepositTime() > 10000) ? "天"
								: "个月"%>
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>
		<%
			if(financeInfo.getIsAutoContinue() == 1)
			{
		%>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" nowrap>
				<p>
					是否自动续存：
				</p>
			</td>
			<td height="25" class="MsoNormal">是
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<%
				if(financeInfo.getAutocontinuetype() == 1)
				{
		%>
				<tr class="MsoNormal">
					<td height="25" class="MsoNormal"></td>
					<td height="25" class="MsoNormal" nowrap>
						<p>
							自动续存类型：
						</p>
					</td>
					<td height="25" class="MsoNormal" nowrap>本息续存
					</td>
					<td height="25" class="MsoNormal"></td>
				</tr>		
		<%			
				}else if(financeInfo.getAutocontinuetype() == 2) {
		%>
				<tr class="MsoNormal">
					<td height="25" class="MsoNormal"></td>
					<td height="25" class="MsoNormal" nowrap>
						<p>
							自动续存类型：
						</p>
					</td>
					<td height="25" class="MsoNormal" nowrap>本金续存
					</td>
					<td height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td height="25" class="MsoNormal"></td>
					<td height="25" class="MsoNormal" nowrap>
						<p>
							利息转至活期账户：
						</p>
					</td>
					<td height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid()) %>
					</td>
					<td height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td height="25" class="MsoNormal"></td>
					<td height="25" class="MsoNormal" nowrap>
						<p>
							活期客户名称：
						</p>
					</td>
					<td height="25" class="MsoNormal"><%=NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid())%>
					</td>
					<td height="25" class="MsoNormal"></td>
				</tr>										
		<%			
				}		
			}else if(financeInfo.getIsAutoContinue() == 2) {
		%>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" nowrap>
				<p>
					是否自动续存：
				</p>
			</td>
			<td height="25" class="MsoNormal">否
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>			
		<%		
			} 
		%>
<%} %>
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="20%" nowrap>
				金额：
			</td>
			<td width="1%" height="25" class="MsoNormal">
				<div align="right">
					<%=sessionMng.m_strCurrencySymbol%>
				</div>
			</td>
			<td width="49%" height="25" class="MsoNormal">
				<%=financeInfo.getFormatAmount()%>
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" nowrap>
				执行日：
			</td>
			<td height="25" class="MsoNormal" colspan="2" >
				<%=financeInfo.getFormatExecuteDate()%>
			</td>
			<td height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" nowrap>
				汇款用途：
			</td>
			<td height="25" class="MsoNormal" colspan="2" >
				<%=DataFormat.formatString(financeInfo.getNote())%>
			</td>
			<td class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
	
<!-- <form id="frmbizapp" name="frmbizapp" method="post"> -->
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="180" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="3"><a class=lab_title1></td>
						<td class="lab_title2" style="width:140px"> 交易申请处理详情</td>
						<td width="17"><a class=lab_title3></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" align="" cellpadding="0" cellspacing="1" class=list_table>
			<tr class=itemtitle>
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
<!-- </form> -->	

	
	<br/>
	<%
	if (isbill) {
	%>
	<table width="100%" border="0" align="" cellspacing="0" cellpadding="0" class=normal>
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
	<br/>
	<%
	}
	%>
	<%--
    <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
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
 <br/>
 <% if(isNeedApproval.equals("true")){ %>
	   <table  border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">历史审批意见</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
		</table>
		<tr>
			<td align="left" >
			  <%--<iframe
				src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&lID=<%=lID %>&transType=<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>"
				width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		   </td>
		</tr>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>--%>
					<%} %>
		<br />
		
		<tr>
			<td width="100%" align="right">
				<%
					if(financeInfo.getStatus()==OBConstant.SettInstrStatus.REFUSE)
					{
						if(financeInfo.getConfirmUserID()==sessionMng.m_lUserID&&biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
						{
				%>
							<input class=button1 name=add type=button value=" 重新提交 " onClick="Javascript:submitagain();"onKeyDown="Javascript:submitagain();">&nbsp;&nbsp;
				<% 		
						}
					}
				 %>

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
								
						if(biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
							{
				%>

				<input class=button1 name=add type=button value=" 修 改 " onClick="Javascript:updateme();"
					onKeyDown="Javascript:updateme();">
				&nbsp;&nbsp;

				<input class=button1 name=add type=button value=" 删 除 " onClick="Javascript:deleteme();"
					onKeyDown="Javascript:deleteme();">
				&nbsp;&nbsp;

				<!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
							
				<%
							}
				if (lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {
				%>
				<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
				<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()"
					onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<%
				}
				%>

				<%
						}
						if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))
						&& (sessionMng.m_lUserID == financeInfo.getConfirmUserID())) {// 未、已确认、登录人＝确认人 
							if (lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {
				%>
				<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()"
					onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;

				<%
						}
						}
						/* 复核匹配*/
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)
						&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
						&& (lCheckType != 1)&& !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkmatchme();"
					onKeyDown="Javascript:checkmatchme();">
				&nbsp;&nbsp;
				<%
						}
						/* 复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
						.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
						&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
						&& (lCheckType == 1)&& !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkme();"
					onKeyDown="Javascript:checkme();">
				&nbsp;&nbsp;
				<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()"
					onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<%
						}
						/* 换开复核 */
						System.out.println("sessionMng.m_lUserID="
						+ sessionMng.m_lUserID);
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (sessionMng.m_lUserID != financeInfo.getNDepositBillInputuserId())
						&& (lCheckType == 1) && isbill && !search.equals("1")) {// 已确认、登录人<>确认人
				%>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkme();"
					onKeyDown="Javascript:checkme();">
				&nbsp;&nbsp;
				<%
						}
						/* 取消复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK)
						&& (sessionMng.m_lUserID == financeInfo.getCheckUserID()) 
						&& !search.equals("1") && !isSign.equals("1")) {// 已复核、登录人＝复核人
				%>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();"
					onKeyDown="Javascript:cancelcheckme();">
				&nbsp;&nbsp;
				<%
						}
						/* 换开取消复核 */
						if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)
						&& (sessionMng.m_lUserID == financeInfo.getNDepositBillCheckuserId())
						&& isbill&& !search.equals("1") && !isSign.equals("1")) {// 已完成、登录人＝复核人
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
				<input class=button1 name=add type=button value=" 签 认 " onClick="Javascript:signme();"
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
				<input class=button1 name=add type=button value=" 签 认 " onClick="Javascript:signme();"
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
				<!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
				<%
						///add by liuguang 2007-10-19  设置业务是否需要打印的提示
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
				<input type="Button" class="button1" value=" 打 印 " width="46" height="18" onclick="javascript:PrintConsignVoucher()"
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

				<!--
            <img src="\webob\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
		<% if ( lShowMenu == OBConstant.ShowMenu.NO) 
        	{
        		
        		if(operate.equals("query"))
        		{
        	%>
        			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
					<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        	<%
        		}
        		else
        		{
        	%>
        			<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();window.opener.queryme();"> &nbsp;&nbsp;
        	<% 		
        		}
        		
        	}
        	%>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
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
	<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO ? "hidden"
						: ""%>">
	<input type="hidden" name="dtmodify" value="<%=(financeInfo.getDtModify()==null?"":financeInfo.getDtModify().toString()) %>">
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
</td>
</tr>
</table>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("<%=strContext%>/common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}


	/* 菜单控制处理函数 */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frm.menu.value="hidden";
		<%  }   %>
	}
	/*返回处理函数 */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
	<%
		/* 复核 */
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
		.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
		&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
		&& (lCheckType == 1)&& !search.equals("1")) {// 已确认、登录人<>确认人
	%>
		frm.action="<%=strContext%>/capital/check/ck001-v.jsp";
		
	<% }else{%>	
	
		frm.action="<%=strContext%>/capital/fixed/f001-c.jsp";
	<% }%>
	
		frm.submit();
	}

	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "该数据已被篡改，是否修改？"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
				{
		%>
					msg = "该数据已被篡改!"
					alert(msg);
					return false;
		<%
				}
			}
		%>		
		var oldtsExecute = '<%=request.getParameter("oldtsExecute")==null?"":request.getParameter("oldtsExecute")%>';
		if(oldtsExecute==''){
			oldtsExecute = frm.tsExecute.value;
		}
		frm.action="<%=strContext%>/capital/fixed/f001-c.jsp?isupdate=isupdate"+"&oldtsExecute="+oldtsExecute;
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
				alert("存款金额小于存款起点金额，起点金额为：<%=sessionMng.m_strCurrencySymbol%>" + formatAmount(frm.dMinSinglePayAmount.value));
				if(parseInt(<%=lIsSoft%>) == 1) {
					return false;
				}
			}
		} 
		//确认复核 
		if (!confirm("是否复核？"))
			{
				return false;
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
		var msg = "是否取消复核？";
		if(!confirm(msg))
		{
			return false;
		}				
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}
		%>
			
		
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "0";
		showSending();
		frm.submit();
		
	}
	/* 签认处理函数 */
	function signme()
	{
	
		var msg = "是否签认？";
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "该数据已被篡改，是否签认？"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
				{
		%>
					msg = "该数据已被篡改!"
					alert(msg);
					return false;
		<%
				}
			}else
			{
		%>		
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>		
		//showMenu();
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
		
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
	
		var msg = "是否取消签认？";
		if (confirm(msg))
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
	
	function submitagain()
	{
		var oldtsExecute = '<%=request.getParameter("oldtsExecute")==null?"":request.getParameter("oldtsExecute")%>';
		if(oldtsExecute==''){
			oldtsExecute = frm.tsExecute.value;
		}
		frm.action="<%=strContext%>/capital/fixed/f001-c.jsp?sign=again"+"&oldtsExecute="+oldtsExecute;
		frm.submit();
	}
	/*
	$(document).ready(function() {
	 	$(".FormTitle").click(function(){
	      	$("#iTable").toggle();
	    });
	    $("#flexlist").flexigridenc({
			colModel : [
				{display: '序列号',  name : 'ncurrencyid', width : 220, sortable : false, align: 'center'},
				{display: '用户',  name : 'payeracctno', width : 220, sortable : false, align: 'center'},
				{display: '工作描述',  name : 'payername', width : 220, sortable : false, align: 'center'},
				{display: '时间和日期',  name : 'ntranstype', width : 220, sortable : false, align: 'center'}
			],//列参数
			//title:'交易申请处理详情',
			classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			usepager : false,
			userFunc : getFormData,
			height : 140
		});
		
	});
	
	function getFormData() 
	{
		return $.addFormData("frm","flexlist");
	}
*/
</script>

<%
			if (lShowMenu == OBConstant.ShowMenu.YES) { /* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	} catch (Exception e) {
		e.printStackTrace();
		OBHtml.showExceptionMessage(out, sessionMng, (IException) e,
		strTitle, "", 1);
		return;
	}
	
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp"%>
