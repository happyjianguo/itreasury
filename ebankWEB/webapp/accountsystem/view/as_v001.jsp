<%--
 页面名称 ：as_v001.jsp
 页面功能 : 账户关系体系设置 - 显示查询窗口
 作    者 ：jeff
 日    期 ：2008-02-27
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>

<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
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
    	
    	Collection coll = null;
    	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
    	
    	if(request.getAttribute("findResultColl") != null){
    		coll = (Collection)request.getAttribute("findResultColl");
    	}
    	assInfo.convertRequestToDataEntity(request);
    	
    	
%>
	<jsp:include flush="true" page="/ShowMessage.jsp" />
	<safety:resources />
	<script language="JavaScript" src="/webob/js/Control.js"></script>
	<script language="JavaScript" src="/webob/js/Check.js"></script>
	<script language="JavaScript" src="/webob/js/date-picker.js"></script>
	<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
	<form name="form1" method="post" action="../control/as_c001.jsp">
		<input type="hidden" name="strSuccessPageURL" value="../view/as_v001.jsp">		<!--操作成功转向页面-->
		<input type="hidden" name="strFailPageURL" value="../view/as_v001.jsp">		<!--操作失败转向页面-->
		<input type="hidden" name="strAction" value="<%=AccountSystemConstant.Actions.QUERY%>">			<!--操作代码-->
		<input type="hidden" name="nId" value="">

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td style="width:140px" class=title  nowrap><span class="txt_til2"><%=strTitle%></span></td>
			       <td class=title_right  width="17"><a class=img_title></td>
				</TR>
			</TABLE>
    		
    		<br/>
    		
<TABLE border=0  width=100% align="" cellspacing="0" cellpadding="0"> 
   
    		<tr>
    			<td colspan="3">
    				<table border="0" class="normal" width=100%>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"></td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td width="2" class="MsoNormal">&nbsp;</td>
			  				<td width="88" class="MsoNormal">体系名称：</td>
							<td width="420" align="left" class="MsoNormal">
								<table><tr>
										<script language="javascript">
											function getSystemNameSQL(){
												var strSQL = "";
												strSQL = "select t.id id, t.systemcode systemcode, t.systemname systemname ";
												strSQL += "from sett_accountsystemsetting t ";
												strSQL += "where t.nofficeid = <%=sessionMng.m_lOfficeID%> ";
												strSQL += "and t.ncurrencyid = <%=sessionMng.m_lCurrencyID%> ";
												strSQL += "and t.nclientid = <%=sessionMng.m_lClientID%> ";
												strSQL += "and t.nstatusid != <%=AccountSystemConstant.SettingStatus.DELETED%> ";
												strSQL += "order by t.systemcode";
												return strSQL;
											}
										</script>
										<%
							              String strMagnifierName = URLEncoder.encode("体系名称");
							              String strFormName = "form1";
							              String strPrefix = "system";
							              String[] strMainNames = {"Name", "Code"};
							              String[] strMainFields = {"systemName", "systemCode"};
							              String[] strReturnNames = {"id"};
							              String[] strReturnFields = {"id"};
							              String strReturnInitValues = "";
							              String[] strReturnValues = {"-1"};
							              String[] strDisplayNames = {URLEncoder.encode("体系编号"),URLEncoder.encode("体系名称")};
							              String[] strDisplayFields = {"systemCode","systemName"};
							              int nIndex = 1;
							              String strMainProperty = "";
							              String strSQL = "getSystemNameSQL()";
							              String strMatchValue = "systemName";
							              String strNextControls = "butQuery";
							              String strTitle1 = "";
							              String strFirstTD = "";
							              String strSecondTD = "";
							
							              OBMagnifier.showNewZoomCtrl(out
							              ,strMagnifierName
							              ,strFormName
							              ,strPrefix
							              ,strMainNames
							              ,strMainFields
							              ,strReturnNames
							              ,strReturnFields
							              ,strReturnInitValues
							              ,strReturnValues
							              ,strDisplayNames
							              ,strDisplayFields
							              ,nIndex
							              ,strMainProperty
							              ,strSQL
							              ,strMatchValue
							              ,strNextControls
							              ,strTitle1
							              ,strFirstTD
							              ,strSecondTD
							              ,false
							              ,false);	
										%>
								</tr></table>
							</td>
							<td width="100" class="MsoNormal">&nbsp;</td> 	
				        </tr>
						<tr class="MsoNormal">
							<td colspan="4"  align="right" class="MsoNormal">
								<input type="button" value=" 查 找 " class="button1" name="butQuery" onclick="toQuery(form1)"/>
								<input type="button" value=" 新 增 " class="button1" name="butAdd" onclick="toAdd()"/>
								&nbsp;
							</td>
						</tr>
						<tr class="MsoNormal">
							<td height="25" colspan="4" class="MsoNormal"><hr></td>
						</tr>
						<tr class="MsoNormal">
							<td colspan="4" class="MsoNormal">
								<table align="left" border="1" cellpadding="0" cellspacing="0" class="normal" width="100%">
									<thead>
			       						<tr>
			        						<TD width="30%" height="24" align="center">账户体系编号</TD>
			        						<TD width="30%" align="center">账户体系名称</TD>
									        <TD width="40%" align="center">状态</TD>
										</tr>
									</thead>
			       					<tbody>
			       					<%if(coll == null){%>
						         		<tr bgcolor="#EBEBEB">
											<td height="24" align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
								 		</tr>
			       					<%}else{
			       						Iterator iter = coll.iterator();
			       						AccountSystemSettingInfo tempInfo = null;
			       						while(iter.hasNext()){
			       							tempInfo = (AccountSystemSettingInfo)iter.next();
			       					%>
						         		<tr>
											<td height="24" align="center">
											<%if(tempInfo.getNStatusId() == AccountSystemConstant.SettingStatus.DELETING){
												out.print(tempInfo.getSystemCode());
											}else{ %>
												<A href="javascript:toDetails(form1,<%=tempInfo.getId()%>)"><%=tempInfo.getSystemCode()%></A>
											<%} %>
											</td>
											<td align="center"><%=tempInfo.getSystemName()%></td>
											<td align="center"><%=AccountSystemConstant.SettingStatus.getName(tempInfo.getNStatusId())%></td>
								 		</tr>
			       					<%}}%>				
									</tbody>
								</table>
							</td>
						</tr>
    				</table>
    			</td>
    		</tr>
		</tbody>
		</table>
	</form>
	
<script language="javascript">
	function toAdd(){
		window.location.href = "../view/as_v002.jsp";
	}
	
	function toQuery(form){
		if(form.systemid.value > 0){
			form.nId.value = form.systemid.value;
		}
		showSending();
		form.submit();
	}
	
	function toDetails(form, id){
		form.strSuccessPageURL.value = "../view/as_v003.jsp"
		form.strFailPageURL.value = "../view/as_v001.jsp"
		form.strAction.value = "<%=AccountSystemConstant.Actions.DETAILS%>";
		form.nId.value = id;
		showSending();
		form.submit();
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
