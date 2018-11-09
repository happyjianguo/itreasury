<%--
 页面名称 ：as_v004.jsp
 页面功能 : 账户关系体系设置 - 设置下级账户
 作    者 ：jeff
 日    期 ：2008-02-28
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingViewInfo"%>
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
    	
    	AccountSystemSettingInfo assInfo = new AccountSystemSettingInfo();
		Collection coll = null;
		
    	if(request.getAttribute("findResultInfo") != null){
    		assInfo = (AccountSystemSettingInfo)request.getAttribute("findResultInfo");
    	}
    	
    	if(request.getAttribute("findResultColl") != null){
    		coll = (Collection)request.getAttribute("findResultColl");
    	}
    	
%>
	<jsp:include flush="true" page="/ShowMessage.jsp" />
	<safety:resources />
	<script language="JavaScript" src="/webob/js/Control.js"></script>
	<script language="JavaScript" src="/webob/js/Check.js"></script>
	<script language="JavaScript" src="/webob/js/date-picker.js"></script>
	<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
	<form name="form1" method="post" action="../control/as_c003.jsp">
		<input type="hidden" name="strSuccessPageURL" value="../view/as_v004.jsp">		<!--操作成功转向页面-->
		<input type="hidden" name="strFailPageURL" value="../view/as_v004.jsp">		<!--操作失败转向页面-->
		<input type="hidden" name="strAction" value="<%=AccountSystemConstant.Actions.SAVE%>">			<!--操作代码-->
		<input type="hidden" name="nId" value="<%=assInfo.getId()%>">
		<input type="hidden" name="nStatusId" value="<%=assInfo.getNStatusId()%>">
		<input type="hidden" name="nAccountId" value="">
		
  



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
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 上级账户信息</td>
	<td width="800"><a class=lab_title3></td>
</tr>
    		<tr>
    			<td colspan="3">
    				<table border="0" class="normal" width=100%>
					    <tr class="MsoNormal">
				        	<td colspan="6" height="10" class="MsoNormal"></td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td width="2" class="MsoNormal">&nbsp;</td>
			  				<td width="100" class="MsoNormal">体系编号：</td>
							<td width="248" align="left" class="MsoNormal"><%=assInfo.getSystemCode()%></td>
					    	<td width="2" class="MsoNormal">&nbsp;</td>
			  				<td width="100" class="MsoNormal">体系名称：</td>
							<td width="248" align="left" class="MsoNormal"><%=assInfo.getSystemName()%></td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">上级账号：</td>
							<td align="left" class="MsoNormal"><%=assInfo.getAccountCode()%></td>
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">上级账户名称：</td>
							<td align="left" class="MsoNormal"><%=assInfo.getAccountName()%></td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="6" height="10" class="MsoNormal"></td>
				        </tr>
    				</table>
    			</td>
    		</tr>
	  		<tr>
	  			<td colspan="3" height="5"></td>
			</tr>
			</table>
			
		<br/>
    		<TABLE border=0  width=100% align="" cellspacing="0" cellpadding="0"> 
    		
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 设置下级账户</td>
	<td width="800"><a class=lab_title3></td>
</tr>
    		<tr>
    			<td colspan="3">
    				<table border="0" class="normal" width=100%>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"></td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td width="2" class="MsoNormal">&nbsp;</td>
			  				<td width="120" class="MsoNormal">下级账号：</td>
							<td width="400" align="left" class="MsoNormal">
								<table><tr>
										<script language="javascript">
											function getAccountIdSQL(){
												var strSQL = "";
												strSQL = "select b.id id, b.saccountno accountCode, b.sname accountName ";
												strSQL += "from client a, sett_account b ";
												strSQL += "where a.id("+ encodeURIComponent("+") +") = b.nclientid ";
												strSQL += "and b.ncurrencyid = <%=sessionMng.m_lCurrencyID%> ";
												strSQL += "and b.nofficeid = <%=sessionMng.m_lOfficeID%> ";
												strSQL += "and b.nstatusid in (<%=SETTConstant.AccountStatus.NORMAL%>, <%=SETTConstant.AccountStatus.PARTFREEZE%>) ";
												strSQL += "and b.ncheckstatusid = <%=SETTConstant.AccountCheckStatus.CHECK%> ";
												strSQL += "and b.nclientid in (select t.id from client t where t.nlevelcode like (select t.nlevelcode "+ encodeURIComponent("|| '_%'") +" from  client t where t.id = <%=sessionMng.m_lClientID%>)) ";
												//strSQL += "and b.id in (select accountid from ob_acctprvgbyclient t where t.clientid = <%=sessionMng.m_lClientID%>) ";
												<%
													//找查帐户类型组
													out.print("strSQL += \"and b.naccounttypeid in (");
													long[] accountTypeColl = SETTConstant.AccountType.getCurrentAccountTypeCode(sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID);
													for(int i=0; i<accountTypeColl.length-1; i++){
														out.print(accountTypeColl[i] + ", ");
													}
													if(accountTypeColl.length>=1){
														out.print(accountTypeColl[accountTypeColl.length-1]);
													}
													out.print(") \";");
												%>
												strSQL += "order by b.naccounttypeid, b.id";
												return strSQL;
											}
										</script>
										<%
							              String strMagnifierName = URLEncoder.encode("账号");
							              String strFormName = "form1";
							              String strPrefix = "account";
							              String[] strMainNames = {"Code", "Name"};
							              String[] strMainFields = {"accountCode", "accountName"};
							              String[] strReturnNames = {"id"};
							              String[] strReturnFields = {"id"};
							              String strReturnInitValues = "";
							              String[] strReturnValues = {"-1"};
							              String[] strDisplayNames = {URLEncoder.encode("账号"),URLEncoder.encode("账户名称")};
							              String[] strDisplayFields = {"accountCode","accountName"};
							              int nIndex = 0;
							              String strMainProperty = "";
							              String strSQL = "getAccountIdSQL()";
							              String strMatchValue = "accountCode";
							              String strNextControls = "butSave";
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
					    	<td width="278" class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">下级客户名称：</td>
							<td align="left" class="MsoNormal">
								&nbsp;<input type="text"  style="width:127px"name="accountName" size="20" maxlength="50" class="box" value="" disabled>
							</td>
					    	<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"></td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" align="right" class="MsoNormal">
								<input type="button" value=" 新 增 " class="button1" onclick="toSave(form1)"/>
								<input type="button" value=" 删 除 " class="button1" onclick="toDelete(form1)"/>
								&nbsp;
				        	</td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"><hr/></td>
				        </tr>
						<tr class="MsoNormal">
							<td colspan="4" class="MsoNormal">
								<table align="left" border="1" cellpadding="0" cellspacing="0" class="normal" width="100%">
									<thead>
			       						<tr>
			        						<TD width="10%" height="24" align="center">
			        							<input type="checkbox" name="cbIndex" value="0" onclick="changeCheck(form1,this)">
			        						</TD>
			        						<TD width="18%" align="center">下级客户编号</TD>
									        <TD width="18%" align="center">下级客户名称</TD>
									        <TD width="18%" align="center">下级账号</TD>
									        <TD width="18%" align="center">下级账户名称</TD>
									        <TD width="18%" align="center">状态</TD>
										</tr>
									</thead>
			       					<tbody>
			       					<%if(coll == null){%>
						         		<tr>
											<td height="24" align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
											<td align="center">&nbsp;</td>
								 		</tr>
			       					<%}else{
			       						Iterator iter = coll.iterator();
			       						AccountRelationSettingViewInfo tempInfo = null;
			       						while(iter.hasNext()){
			       							tempInfo = (AccountRelationSettingViewInfo)iter.next();
			       					%>
						         		<tr>
											<td height="24" align="center">
												<input type="checkbox" name="cbIndexId" value="<%=tempInfo.getId()%>" onclick="changeCheck(form1,this)">
											</td>
											<td align="center"><%=tempInfo.getClientCode()%></td>
											<td align="center"><%=tempInfo.getClientName()%></td>
											<td align="center"><%=tempInfo.getAccountCode()%></td>
											<td align="center"><%=tempInfo.getAccountName()%></td>
											<td align="center"><%=AccountSystemConstant.RelationStatus.getName(tempInfo.getNStatusId())%></td>
								 		</tr>
			       					<%}}%>			
									</tbody>
								</table>
							</td>
						</tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"><hr/></td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" align="right" class="MsoNormal">
				        		<input type="button" value=" 上一步 " class="button1" onclick="toPrevious(form1)"/>
								<input type="button" value=" 完 成 " class="button1" onclick="toEnd(form1)"/>
								&nbsp;
				        	</td>
				        </tr>
					    <tr class="MsoNormal">
				        	<td colspan="4" height="10" class="MsoNormal"></td>
				        </tr>
    				</table>
    			</td>
    		</tr>
		</tbody>
		</table>
	</form>
	
<script language="javascript">
firstFocus(form1.accountCode);
//setSubmitFunction("toSave(form1)");
setFormName("form1");

	function toSave(form){
		if (!validate(form)) return;
		
		form.nAccountId.value = form.accountid.value;
		
		if(confirm("您确定要保存吗？")){
			showSending();
			form.submit();
		}
	}
	
	function toDelete(form){
		if(form.cbIndexId == null){
			alert("您没有选择删除项！");
			return false;
		}
		
		var cbIndexIdColl = window.document.getElementsByName("cbIndexId");
		var num = 0;
		for(var i=0; i<cbIndexIdColl.length; i++){
			if(cbIndexIdColl[i].checked == true){
				num++;
			}
		}
		if(num == 0){
			alert("您没有选择删除项！");
			return false;
		}
		
		form.strAction.value = "<%=AccountSystemConstant.Actions.DELETE%>";
	
		if(confirm("您确定要删除吗？")){
			showSending();
			form.submit();
		}
	}

	function toPrevious(form){
		form.action = "../control/as_c001.jsp";
		form.strSuccessPageURL.value = "../view/as_v003.jsp"
		form.strFailPageURL.value = "../view/as_v001.jsp"
		form.strAction.value = "<%=AccountSystemConstant.Actions.DETAILS%>";
		showSending();
		form.submit();
	}
	
	function toEnd(form){
		window.location.href = "../view/as_v001.jsp";
	}
	
	function validate(form){
		if(form.accountCode.value == ""){
			alert("下级账号不能为空！");
			form.accountCode.focus();
			return false;
		}
		
		if(form.accountid.value == "-1"){
			alert("下级账号请用放大镜选择！");
			form.accountCode.focus();
			return false;
		}
		return true;
	}
	
	//控制CheckBox
	function changeCheck(form, cbObject){
		if(form.cbIndexId != null){
			var cbIndexIdColl = window.document.getElementsByName("cbIndexId");
			if(cbObject.value == 0){
				if(cbObject.checked == true){
					for(var i=0; i<cbIndexIdColl.length; i++){
						cbIndexIdColl[i].checked = true;
					}
				}
				else {
					for(var i=0; i<cbIndexIdColl.length; i++){
						cbIndexIdColl[i].checked = false;
					}
				}
			}
			else {
				var num = 0;
				for(var i=0; i<cbIndexIdColl.length; i++){
					if(cbIndexIdColl[i].checked == true){
						num++;
					}
				}
				if(num == cbIndexIdColl.length){
					form.cbIndex.checked = true;
				}
				else {
					form.cbIndex.checked = false;
				}
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

<%@ include file="/common/SignValidate.inc" %>
