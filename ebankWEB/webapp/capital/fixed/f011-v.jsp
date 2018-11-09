<!--
/*
 * 程序名称：f011-v.jsp
 * 功能说明：定期转存提交控制页面
 * 作　　者：蔡俊
 * 完成日期：2007年04月18日
 */
-->

<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:directive.page import="java.text.DateFormat" />
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[到期续存]";
%>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();	
	com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
    String type = "";
    String _type = request.getParameter("type");
    if ((type != null) && (type.length()>0))
	{
    	type = _type;
	}
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
       	lTransType = (String)request.getParameter("txtTransType");
       	
       	   	
	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
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

		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
		
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		
		/*if(blnUseITrusCert){
			String[] nameArray = OBSignatureConstant.ChangeFixdeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.ChangeFixdeposit.getSignValueArrayFromInfo(financeInfo);	
			if(OBSignatureUtil.isIdHaveNotRealValue(financeInfo,blnIsNeedApproval)){
				//特殊处理			
			 	valueArray[OBSignatureConstant.ChangeFixdeposit -1 ] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}*/
		String strID = (String)request.getAttribute("lID");
	    long lID = -1;
	    if((strID != null) && (strID.length()>0)){
	    	lID = Long.parseLong(strID);
	    }
%>

<safety:resources />

<link rel="stylesheet" href="/webloan/css/approve.css" type="text/css"> 

<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
</script>
<%} %>

<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
<form name="frm" method="post">
	<% 
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) 
		{
%>


	<table cellpadding="0" cellspacing="0" class="title_top">
		<tr>
			<td height="24">
				<table style="width:150px" cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
						<td class=title>
							<span class="txt_til2">到期续存</span>
						</td>
						<td class=title_right>
							<a class=img_title>
						</td>
					</TR>
				</TABLE>
			</td>
		</tr>


	</table>
	<br />
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							到期续存
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr>
			<td colspan="3" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal">

				<p>
					<br>
					到期续存将<%
	        if(isNeedApproval.equals("true"))
	        {
	        	%>于审批后<%
	        	if(!OBFSWorkflowManager.isAutoCheck())
	        	{
	        		%>等待复核人复核！<%
	        	}
	        	else
	        	{
	        		%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        	}
	        }
	        else if(!OBFSWorkflowManager.isAutoCheck())
	        {
	        	%>于审批并复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
	        else
	        {
	        	%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
        %>
					<br>
				
					<%if(isNeedApproval.equals("true"))
			  	{
			  		%><br>
					该笔交易有待审批人审批！
					<br>
					<%
			  	}else if(!OBFSWorkflowManager.isAutoCheck())
			  	{
			  		%><br>
					该笔交易有待复核人复核！
					<br>
					<%
			 	}%>
					<br>
					<b>指令序号为：<%= financeInfo.getID() %></b>
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
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td style="width: 90px" class="lab_title2">
							到期存款账户
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>

				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				定期账户名称：
			</td>
			<td width="430" height="25" class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
			<td width="5" height="25"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				定期账号：
			</td>
			<td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
			<td width="5"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="5" height="25"></td>
			<td width="130" height="25" class="MsoNormal">
				定期存款子账号：
			</td>
			<td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getSDepositBillNo().toString()) %></td>
			<td width="5"></td>
		</tr>
		<tr class="MsoNormal">
			<td colspan="4" height="1"></td>
		</tr>
	</table>
	<br>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">


					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							到期续存
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>


	<table width=100% border="0" cellspacing="0" cellpadding="0"
		class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					金额：
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal">
				<%= financeInfo.getFormatAmount() %>
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					定期存款期限：
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"天":"个月"%></td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<tr class="MsoNormal">
			<td width="6" height="25" class="MsoNormal"></td>
			<td height="25" colspan="2" class="MsoNormal">
				<p>
					新定期存款起始日：
				</p>
			</td>
			<td width="547" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;新定期存款到期日：<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
	</table>

	<br>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="110" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="90" class="lab_title2">
							利息处理
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100% border="0" cellspacing="0" cellpadding="0"
		class=normal align="">
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
		<%if(financeInfo.getSDepositInterestDeal() == 1 ) {%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				本利续存
			</td>
			<td width="20" height="25" class="MsoNormal">
				<div align="right"></div>
			</td>
			<td width="430" height="25" class="MsoNormal"></td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<%}else if(financeInfo.getSDepositInterestDeal() == 2 ){ %>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" class="MsoNormal" width="110">
				利息转至活期账户:
			</td>
			<td width="20" height="25" class="MsoNormal">
				<div align="right"></div>
			</td>
			<td width="430" height="25" class="MsoNormal">
				利息转至活期账号：<%=eBankNameRef.getAccountNOByIDFromSett(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;
			</td>
			<td width="5" height="25" class="MsoNormal"></td>
		</tr>
		<%} %>
		<tr class="MsoNormal">
			<td colspan="5" height="1" class="MsoNormal"></td>
		</tr>
	</table>
	<br>
		 <% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
		{ 
%>
	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td>
				<table width="180" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td width="3">
							<a class=lab_title1>
						</td>
						<td width="160" class="lab_title2">
							交易申请处理详情
						</td>
						<td width="17">
							<a class=lab_title3>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=774 border="1" align="" cellpadding="0" cellspacing="0" class=list_table>
			<tr class="itemtitle">
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
				<div align="center"><%= financeInfo.getConfirmUserName() %></div>
			</td>

			<td width="198" height="25">
				<div align="center">
					录入
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
			</td>
		</tr>

		<% }
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getCheckUserName() %>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					复核
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatCheckDate() %></div>
			</td>
		</tr>
		<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{  
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center">
					2
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getCheckUserName() %>
				</div>
			</td>

			<td width="198" height="25">
				<div align="center">
					复核
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatCheckDate() %></div>
			</td>
		</tr>

		<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
		<tr valign="middle">
			<td width="63" align="left" height="25">
				<div align="center" class="graytext">
					3
				</div>
			</td>

			<td width="190" height="25">
				<div align="center"><%= financeInfo.getSignUserName() %></div>
			</td>

			<td width="198" height="25">
				<div align="center">
					签认
				</div>
			</td>

			<td width="269" height="25">
				<div align="center"><%= financeInfo.getFormatSignDate() %></div>
			</td>
		</tr>
		<% 
				}
%>
	</table>
	
	<br />
	<table border="0" width=100% cellspacing="0" cellpadding="0"
		align="center">
		<!-- 历史审批意见 -->
		<TR>
			<TD colspan="3">
				<iframe
					src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>"
					width="100%" height="100" scrolling="Auto" frameborder="0"
					name="currentSignID"></iframe>

			</TD>
		</TR>
		<!-- 历史审批意见 -->
		<tr>
			<td width="774" align="right">

				<%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

				<input class=button1 name=add type=button value=" 修 改 "
					onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">
				&nbsp;&nbsp;

				<input class=button1 name=add type=button value=" 删 除 "
					onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
				&nbsp;&nbsp;

				<!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->

				<% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
				<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
				<input class=button1 name=add type=button value=" 返 回 "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<%}%>

				<% }
		
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// 未、已确认、登录人＝确认人 
		if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) 
		{%>
				<input class=button1 name=add type=button value=" 返 回 "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;

				<%}
		}
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复 核 "
					onClick="Javascript:checkmatchme();"
					onKeyDown="Javascript:checkmatchme();">
				<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE|| financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复 核 "
					onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
				<input class=button1 name=add type=button value=" 返 回 "
					onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
				&nbsp;&nbsp;
				<% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// 已复核、登录人＝复核人 %>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 "
					onClick="Javascript:cancelcheckme();"
					onKeyDown="Javascript:cancelcheckme();">

				<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// 已复核、需要被登录人签认=true %>

				<!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				<input class=button1 name=add type=button value=" 签 认 "
					onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

				<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已签认、登录人＝签认人 %>

				<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				<input class=button1 name=add type=button value=" 取消签认 "
					onClick="Javascript:cancelsignme();"
					onKeyDown="Javascript:cancelsignme();">

				<%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>

				<!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
				<%
						///add by liuguang 2007-10-19  设置业务是否需要打印的提示
						if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
				%>
				<input type="Button" class="button1" value=" 打 印 " width="46"
					height="18" onclick="javascript:PrintConsignVoucher()"
					<%if(a!=null && !a.contains(strStatus))
               { 
			%>
					disabled <%
				}
			%>>
				<%
				}
				%>
				<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
				<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
				<input type="Button" class="button1" value=" 关 闭 " width="46"
					height="18" onclick="window.close();">
				<%}%>
			</td>
		</tr>
	</table>

	<input type="hidden" name="RemitType"
		value="<%= financeInfo.getRemitType() %>">
	<input type="hidden" name="Amount"
		value="<%= financeInfo.getAmount() %>">
	<input type="hidden" name="ExecuteDate"
		value="<%= financeInfo.getExecuteDate() %>">
	<input type="hidden" name="ConfirmUserID"
		value="<%= financeInfo.getConfirmUserID() %>">
	<input type="hidden" name="ConfirmDate"
		value="<%= financeInfo.getConfirmDate() %>">
	<input type="hidden" name="PayerAcctID"
		value="<%= financeInfo.getPayerAcctID() %>">
	<input type="hidden" name="PayeeAcctID"
		value="<%= financeInfo.getPayeeAcctID() %>">
	<input type="hidden" name="lInstructionID"
		value="<%= financeInfo.getID() %>">
	<input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	<input type="hidden" name="billstatusid"
		value="<%=financeInfo.getNDepositBillStatusId()%>">
	<input type="hidden" name="txtisCheck" value="">
	<input type="hidden" name="txtTransType" value="<%=lTransType %>">
	<input type="hidden" name="menu"
		value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">


	<!-- add by mingfang 2007-05-24 -->
	<!-- 签名用字段 -->

	<input type="hidden" name="nPayeeAccountID"
		value="<%=financeInfo.getPayeeAcctID()%>">
	<input type="hidden" name="sPayerAccountNoZoomCtrl"
		value="<%=financeInfo.getSDepositBillNo()%>">
	<input type="hidden" name="dAmount"
		value="<%=financeInfo.getAmount()%>">
	<input type="hidden" name="nFixedDepositTime1"
		value="<%=financeInfo.getFixedDepositTime()%>">
	<input type="hidden" name="tsExecute"
		value="<%=financeInfo.getExecuteDate()%>">
	<input type="hidden" name="dPayerStartDate"
		value="<%=financeInfo.getSDepositBillStartDate()%>">
	<input type="hidden" name="interesttype"
		value="<%=financeInfo.getSDepositInterestDeal()%>">
	<input type="hidden" name="lInterestToAccountID"
		value="<%=financeInfo.getSDepositInterestToAccountID()%>">
	<input type="hidden" name="submitUserId"
		value="<%=financeInfo.getConfirmUserID()%>">

	<!-- 供c415.jsp判断业务类型用-->
	<input type="hidden" name="SelectType"
		value="<%=financeInfo.getTransType() %>">
</form>
<!--presentation end-->

<script language="javascript">

	/*返回处理函数 */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f006-c.jsp";
		frm.submit();
	}

	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}

	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f007-c.jsp";
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
		frm.action="../fixed/f009-2-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
   
   function checkme()
	{
		//showMenu();
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.ChangeFixdeposit.outSignNameArrayToView(out);
				OBSignatureConstant.ChangeFixdeposit.outSignValueArrayToViewForView(out);
			%>
			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	
		/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frm.action="../sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	$(document).ready(function() {
	 	$(".FormTitle").click(function(){
	      	$("#iTable").toggle();
	    });
	    $("#flexlist").flexigridenc({
			colModel : [
				{display: '序列号',  name : 'ncurrencyid', width : 200, sortable : false, align: 'center'},
				{display: '用户',  name : 'payeracctno', width : 230, sortable : false, align: 'center'},
				{display: '工作描述',  name : 'payername', width : 230, sortable : false, align: 'center'},
				{display: '时间和日期',  name : 'ntranstype', width : 230, sortable : false, align: 'center'}
			],//列参数
			//title:'交易申请处理详情',
			classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			usepager : false,
			userFunc : getFormData,
			height : 100
		});
		
	});
	
	function getFormData() 
	{
		return $.addFormData("frmbizapp","flexlist");
	}
</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/common.jsp"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc"%>