<%--
 页面名称 ：fi_v001.jsp
 页面功能 : 网上银行 - 逐笔付款确认
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
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.ExtSystemSettingBiz"%>
<%@ page import="com.iss.itreasury.util.*"%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "逐笔付款确认";
String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
StringBuffer strMessage = null;


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
   	String strSourceType = "";
	FinanceInfo financeInfo = (FinanceInfo)request.getAttribute("financeInfo");
	
	ExtSystemSettingInfo extSystemSettingInfo = new ExtSystemSettingInfo();
	ExtSystemSettingBiz extSystemSettingBiz = new ExtSystemSettingBiz();
	if(financeInfo.getSource()!=-1)
	{
		extSystemSettingInfo = extSystemSettingBiz.findExtSystemSettingByID(financeInfo.getSource());
		strSourceType = extSystemSettingInfo.getSName();
	}
	
	
	
	if(financeInfo == null){
		response.sendRedirect(strContext + "/capital/financeinstr/view/fi_v001.jsp");
	}
	else{
		if(financeInfo.getSBatchNo() == null){
			financeInfo.setSBatchNo("");
		}
	}
	String isNeedApproval = "";
	String isflag = "";
	String temp = "";
	
	temp = request.getParameter("isNeedApproval");
	if(temp!=null&&temp.trim().length()>0)
	{
		isNeedApproval = temp;
	}
	
	temp =(String) request.getAttribute("flag");
	if(temp!=null&&temp.trim().length()>0)
	{
		isflag = temp;
	}
	
	
	
	String newOpen = "";
	String submitAgain = "";
	temp = (String)request.getAttribute("newOpen");
	if(temp!=null&&temp.trim().length()>0)
	{
		newOpen = temp;
	}



%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript">
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
		trPayeeBankCNAPSNO.style.display = "block";
		trPayeeBankOrgNO.style.display = "block";
		trPayeeBankExchangeNO.style.display = "block";
		
	}
	
	if(remitType == "<%=OBConstant.SettRemitType.INTERNALVIREMENT%>"){
		oTdBankpay.style.display = "none";
		oTdInternalvirement.style.display = "block";
		oTrPayeeProv.style.display = "none";
		oTrPayeeBankName.style.display = "none";
		trPayeeBankCNAPSNO.style.display = "none";
		trPayeeBankOrgNO.style.display = "none";
		trPayeeBankExchangeNO.style.display = "none";
	}
}

function displayApplyCode(form)
{
	var oTrApplyCode = document.getElementById("trApplyCode");
	var source = form.lSource.value;
	if(source=="<%=SETTConstant.ExtSystemSource.EBANK %>")
	{
		oTrApplyCode.style.display="none";
	}else
	{
		oTrApplyCode.style.display="block";
	}
}
</script>

<link rel="stylesheet" href="/webloan/css/approve.css" type="text/css">

<form name="form1" method="post" action="../control/fi_c001.jsp">
<input type="hidden" name="strSuccessPageURL" value="../view/fi_v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/fi_v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">  <!--操作代码-->
<input type="hidden" name="lInstructionID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="sbatchNO" value="<%=financeInfo.getSBatchNo()%>">
<input type="hidden" name="nRemitType" value="<%=financeInfo.getRemitType()%>">
<input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID()%>">
<input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID()%>">
<input type="hidden" name="lSource" value="<%=financeInfo.getSource() %>">
<input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
<input type="hidden" name="newOpen" value="<%=newOpen %>">
<table cellpadding="0" cellspacing="0" class="title_top" border="0" width="">
	  <tr>
	    <td height="22" colspan=4 >
		    <table cellspacing="0" cellpadding="0" class=title_Top1 border="0">
				<TR>
			       <td class=title><span class="txt_til2"><%=strTitle %></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
		</td>
	</tr>
	<tr>
	  <td colspan=4>
		<table border="0" cellspacing="0" cellpadding="0" class="normal" width="100%">
	        <tr class="MsoNormal">
	          <td height="1" class="MsoNormal"></td>
        	</tr>
       
        <tr class="MsoNormal">
          	<td colspan="2" height="25" class="MsoNormal" nowrap>
      <p>
      <br>
      	<%
      		if(financeInfo.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY) //银行汇款
      		{
      	 %>
      	逐笔付款-银行汇款<%
      		}
      		else if(financeInfo.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT) //内部转账
      		{
      		%>逐笔付款-内部转账<%
      		}
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
	        	%>于复核后提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
	        else if(isflag.equals("save")&&isNeedApproval.equals("false"))
	        {
	        	%>于复核后提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
	        else
	        {
	        	%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
        %>
        <br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			    <%
			    if(isNeedApproval.equals("true")&& isflag.equals("save"))
			  	{
			  	%>
			  	    <br>该笔交易已保存还未提交！<br>
			  	<%
			  	}
			    else if(isNeedApproval.equals("true"))
			  	{
			  		%><br>该笔交易有待审批人审批！<br><%
			  	}%>
 
             <br>
              <b>指令序号为：<%= financeInfo.getID() %></b><br>
              <br>
            </p>
           
            </td>
        </tr>
        </table>
			</td>
		</tr>
		<tr>
			<td height="10" colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td valign="top">
				<table  border="0" cellspacing="0" cellpadding="0" align="">
				<tr>
					<td width="4" >&nbsp;</td>
					<td class="lab_title2" nowrap > 付款方资料</td>
					<td width="683"><a class=lab_title3></td>
				</tr>
				</table>
			</td>
		</tr>
	<tr>
		<td valign="top" colspan="2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="20">&nbsp;</td>
		          	<td width="130" height="20" align="left" nowrap>付款方名称：</td>
		          	<td width="500" height="20">
		            	<%--=sessionMng.m_strClientName--%>
		            	<%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%>
		          	</td>
		          	<td width="*%" height="20" class="MsoNormal">&nbsp;</td>
		        </tr>
				<tr>
					<td width="4" height="20">&nbsp;</td>
					<td  width="130" height="20" align="left">付款方账号：</td>
					<td width="500" height="20">
						<%=financeInfo.getPayerAcctNo()%>
					</td>
					<td width="*%" height="20" class="MsoNormal">&nbsp;</td>
		 		</tr>
        		<tr style="display: none">
          			<td width="4" height="20">&nbsp;</td>
          			<td width="130" height="20"align="left">当前余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="20">
						<%=financeInfo.getFormatCurrentBalance()%> 元
		  			</td>
          			<td width="*%" height="20" class="MsoNormal">&nbsp;</td>
        		</tr>
        		<tr style="display: none">
          			<td width="4" height="20">&nbsp;</td>
          			<td width="130" height="20"align="left">可用余额：<%=sessionMng.m_strCurrencySymbol%></td>
          			<td width="500" height="20">
          				<%=financeInfo.getFormatUsableBalance()%> 元
		  			</td>
          			<td width="*%" height="20" class="MsoNormal">&nbsp;</td>
        		</tr>
			</table>
		</td>
	</tr>
	<tr class="MsoNormal">
         <td colspan="3" height="2" class="MsoNormal"><br/></td>
    </tr>
		<tr>
			<td valign="top" colspan="2">
				<table  border="0" cellspacing="0" cellpadding="0" align="">
					<tr>
						<td width="1">&nbsp;</td>
						<td class="lab_title2" nowrap="nowrap"> 收款方资料</td>
						<td ><a class=lab_title3></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
		<td valign="top" colspan="2">
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
				
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="4" height="1" class="MsoNormal"><br/></td>
	</tr>
	<tr>
			<td valign="top" colspan="2">
				<table  border="0" cellspacing="0" cellpadding="0" align="">
				   <tr>
						<td width="1"><a class=lab_title1></td>
						<td class="lab_title2" nowrap="nowrap">&nbsp;划款资料</td>
						<td><a class=lab_title3></td>
				 </tr>
			   </table>
			</td>
		</tr>
		<tr>
		<td valign="top" colspan="2">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">金额：</td>
		          	<td width="500" height="25">
			           <%=sessionMng.m_strCurrencySymbol%>&nbsp;<%=DataFormat.formatListAmount(financeInfo.getAmount())%> 元
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
		            	<%=strSourceType %>		          	
		            </td>
		          	<td width="*%" height="25" class="MsoNormal">&nbsp;</td>
		        </tr>
		        
		        <tr id="trApplyCode">
					<td width="4" height="25">&nbsp;</td>
		          	<td width="130" height="25" align="left">外部系统指令序号：</td>
		          	<td width="500" height="25">
		            	<%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %>		          	
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
	            </tr>
			</table>
		</td>
		</tr>
		<tr>
			<td colspan="4" height="1" class="MsoNormal">&nbsp;</td>
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
		<td valign="top" colspan="3">
      	    <table   border="0" cellspacing="0" cellpadding="0">
		      <tr>
					<td width="3"><a class=lab_title1></td>
					<td class="lab_title2" > 交易申请处理详情</td>
					<td width="17"><a class=lab_title3></td>
			  </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top" colspan="3" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="list_table">
			        <tr class="itemtitle">
			          	<td width="25%" height="25" align="center">序列号</td>
			          	<td width="25%" height="25" align="center">用  户</td>
			          	<td width="25%" height="25" align="center">工作描述</td>
			          	<td width="25%" height="25" align="center">时间和日期</td>
			        </tr>
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
		<td height="10" colspan="3">&nbsp;</td>
	</tr>
<%
	}
	if(isNeedApproval.equals("true")){
%>
	<tr>
		<td valign="top" colspan="3">
			<table  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 历史审批意见</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
			
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top" colspan="3">
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
		<td height="10" colspan="3">&nbsp;</td>
	</tr>
<%
	}
%>
	<tr>
		<td valign="top" colspan="3">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="*%" align="right">
					<%
						if(financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE){
					%>
						<input type="button" value=" 修 改 " class="button1" name="butUpdate" onclick="toUpdate(form1)"/>&nbsp;
						<input type="button" value=" 删 除 " class="button1" name="butDelete" onclick="toDelete(form1)"/>&nbsp;
					<%
						}
						if(newOpen.equals("newOpen"))
						{
					%>
					
						<input type="button" value=" 关 闭 " class="button1" name="butCloseNew" onclick="toCloseNew(form1)"/>
					
					<%
						}
						else
						{
					%>
					
						<input type="button" value=" 返 回 " class="button1" name="butClose" onclick="toClose(form1)"/>
						<%
						}
						 %>
					</td>
					<td width="20" class="MsoNormal">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" height="1" class="MsoNormal">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
	
<script language="javascript">

/* 页面焦点及回车控制 */
setFormName("form1");
disRemitType(form1);
displayApplyCode(form1);

function toUpdate(form)
{
	if(confirm("是否修改？"))
	{
		form.action = "../control/fi_c001.jsp?report=report&update=update";
		form.strSuccessPageURL.value = "../view/fi_v001.jsp?oldOpenDate="+'${hiddenOpendate}';
		form.strFailPageURL.value = "../view/fi_v001.jsp?oldOpenDate="+'${hiddenOpendate}';
		form.strAction.value = "";
		showSending();
	    form.submit();
    }
}

function toDelete(form)
{
	if(confirm("是否删除？"))
	{
		form.action = "../control/fi_c004.jsp";
		form.strSuccessPageURL.value = "../view/fi_v001.jsp";
		form.strFailPageURL.value = "../view/fi_v001.jsp";
		form.strAction.value = "<%=OBConstant.SettInstrStatus.DELETE%>";
		showSending();
	    form.submit();
    }
}

function toClose(form){
	window.location.href = "<%=strContext%>/capital/financeinstr/view/fi_v001.jsp";
}

function toCloseNew(form)
{
	window.close();
	window.opener.queryme();
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
<jsp:include flush="true" page="/ShowMessage.jsp" />