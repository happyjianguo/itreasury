<%--
 页面名称 ：as_v003.jsp
 页面功能 : 账户关系体系设置 - 修改账户关系体系 
 作    者 ：jeff
 日    期 ：2008-02-28
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.clientmanage.dataentity.ClientInfo"%>
<%@ page import="com.iss.itreasury.clientmanage.client.bizlogic.ClientCmd"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
	String strTitle = "账户关系体系设置";
	
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
    	
    	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
	    ClientCmd ccBiz = new ClientCmd();
    	ClientInfo clietInfo = null;
    	
    	if(request.getAttribute("findResultInfo") != null){
    		assInfo = (AccountSystemSettingInfo)request.getAttribute("findResultInfo");
    	}
    	else {
    		assInfo.convertRequestToDataEntity(request);
    	}
    	clietInfo = (ClientInfo)ccBiz.findbyid(sessionMng.m_lClientID);
    	
    	String strClientCode = clietInfo.getCode();
    	String strClientName = clietInfo.getName();
    	
    	
%>
	<jsp:include flush="true" page="/ShowMessage.jsp" />
	<safety:resources />
	<script language="JavaScript" src="/webob/js/Control.js"></script>
	<script language="JavaScript" src="/webob/js/Check.js"></script>
	<script language="JavaScript" src="/webob/js/date-picker.js"></script>
	<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
	<form name="form1" method="post" action="../control/as_c002.jsp">
		<input type="hidden" name="strSuccessPageURL" value="../view/as_v003.jsp">		<!--操作成功转向页面-->
		<input type="hidden" name="strFailPageURL" value="../view/as_v001.jsp">		<!--操作失败转向页面-->
		<input type="hidden" name="strAction" value="<%=AccountSystemConstant.Actions.MODIFY%>">			<!--操作代码-->
		<input type="hidden" name="nId" value="<%=assInfo.getId()%>">
		<input type="hidden" name="nStatusId" value="<%=assInfo.getNStatusId()%>">
  	
			
			


<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%=strTitle%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

<br/>
			<TABLE border=0  width=100% align="" cellspacing="0" cellpadding="0">
  
   
    		<tr>
    			<td colspan="3">
    				<table border="0" class="normal" width=100%>
					    <tr class="MsoNormal">
				        	<td colspan="5" height="10" class="MsoNormal"></td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td width="2" class="MsoNormal">&nbsp;</td>
			  				<td width="98" class="MsoNormal">体系编号：</td>
			  				<td width="5" class="MsoNormal"><font color="red">*</font></td>
							<td width="405" align="left" class="MsoNormal">
								<input type="hidden" name="systemCode" value="<%=assInfo.getSystemCode()%>" />
								<input type="text" name="systemCodeDis" size="20" maxlength="14" class="box" value="<%=assInfo.getSystemCode()%>" disabled />
							</td>
							<td width="100" class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">体系名称：</td>
			  				<td class="MsoNormal"><font color="red">*</font></td>
							<td align="left" class="MsoNormal">
								<input type="text" name="systemName" size="20" maxlength="50" class="box" value="<%=assInfo.getSystemName()%>" onFocus="this.select();nextfield='butSave'"  />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">上级客户编号：</td>
			  				<td class="MsoNormal">&nbsp;</td>
							<td align="left" class="MsoNormal">
								<input type="text" name="clientCodeDis" size="20" maxlength="14" class="box" value="<%=strClientCode%>" disabled />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">上级客户名称：</td>
			  				<td class="MsoNormal">&nbsp;</td>
							<td align="left" class="MsoNormal">
								<input type="text" name="clientNameDis" size="20" maxlength="50" class="box" value="<%=strClientName%>" disabled />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">账号：</td>
			  				<td class="MsoNormal"><font color="red">*</font></td>
							<td align="left" class="MsoNormal">
								<input type="text" name="AccountCodeDis" size="20" maxlength="50" class="box" value="<%=assInfo.getAccountCode()%>" disabled />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="5" height="10" class="MsoNormal"><hr/></td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="5" height="10" align="right" class="MsoNormal">
				        		<input type="button" value=" 保 存 " class="button1" name="butSave" onclick="toSave(form1)"/>
				        		<input type="button" value=" 删 除 " class="button1" name="butDelete" onclick="toDelete(form1)"/>
								<input type="button" value=" 下一步 " class="button1" name="butNext" onclick="toNext(form1)"/>
								<input type="button" value=" 返 回 " class="button1" name="butReturn" onclick="toReturn()"/>
								&nbsp;
				        	</td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="5" height="10" class="MsoNormal"></td>
				        </tr>
    				</table>
    			</td>
    		</tr>
		</tbody>
		</table>
	</form>
	
<script language="javascript">
/* 页面焦点及回车控制 */
firstFocus(form1.systemName);
//setSubmitFunction("toSave(form1)");
setFormName("form1");

function toSave(form){
	if (!validate(form)) return;
	
	if(confirm("您确定要保存吗？")){
		showSending();
		form.submit();
	}
}

function toNext(form){
	form.action = "../control/as_c003.jsp";
	form.strSuccessPageURL.value = "../view/as_v004.jsp"
	form.strFailPageURL.value = "../view/as_v003.jsp"
	form.strAction.value = "<%=AccountSystemConstant.Actions.NEXT%>";
	showSending();
	form.submit();
}

function toDelete(form){
	form.strAction.value = "<%=AccountSystemConstant.Actions.DELETE%>";
	form.strSuccessPageURL.value = "../view/as_v001.jsp"
	
	if(confirm("您确定要删除吗？")){
		showSending();
		form.submit();
	}
}

function toReturn(){
	window.location.href = "../view/as_v001.jsp";
}

function validate(form){
	if(form.systemName.value == ""){
		alert("体系名称不能为空！");
		form.systemName.focus();
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

<%@ include file="/common/SignValidate.inc" %>