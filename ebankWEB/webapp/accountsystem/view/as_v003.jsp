<%--
 ҳ������ ��as_v003.jsp
 ҳ�湦�� : �˻���ϵ��ϵ���� - �޸��˻���ϵ��ϵ 
 ��    �� ��jeff
 ��    �� ��2008-02-28
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
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
<!-- ������û���ǰ�ػ���Session -->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
	String strTitle = "�˻���ϵ��ϵ����";
	
	try{
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		
		//��¼���
    	if (!sessionMng.isLogin()){
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	OBHtml.showOBHomeEnd(out);
        	out.flush();
        	return;
    	}
    	//���Ȩ��
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
		<input type="hidden" name="strSuccessPageURL" value="../view/as_v003.jsp">		<!--�����ɹ�ת��ҳ��-->
		<input type="hidden" name="strFailPageURL" value="../view/as_v001.jsp">		<!--����ʧ��ת��ҳ��-->
		<input type="hidden" name="strAction" value="<%=AccountSystemConstant.Actions.MODIFY%>">			<!--��������-->
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
			  				<td width="98" class="MsoNormal">��ϵ��ţ�</td>
			  				<td width="5" class="MsoNormal"><font color="red">*</font></td>
							<td width="405" align="left" class="MsoNormal">
								<input type="hidden" name="systemCode" value="<%=assInfo.getSystemCode()%>" />
								<input type="text" name="systemCodeDis" size="20" maxlength="14" class="box" value="<%=assInfo.getSystemCode()%>" disabled />
							</td>
							<td width="100" class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">��ϵ���ƣ�</td>
			  				<td class="MsoNormal"><font color="red">*</font></td>
							<td align="left" class="MsoNormal">
								<input type="text" name="systemName" size="20" maxlength="50" class="box" value="<%=assInfo.getSystemName()%>" onFocus="this.select();nextfield='butSave'"  />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">�ϼ��ͻ���ţ�</td>
			  				<td class="MsoNormal">&nbsp;</td>
							<td align="left" class="MsoNormal">
								<input type="text" name="clientCodeDis" size="20" maxlength="14" class="box" value="<%=strClientCode%>" disabled />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">�ϼ��ͻ����ƣ�</td>
			  				<td class="MsoNormal">&nbsp;</td>
							<td align="left" class="MsoNormal">
								<input type="text" name="clientNameDis" size="20" maxlength="50" class="box" value="<%=strClientName%>" disabled />
							</td>
							<td class="MsoNormal">&nbsp;</td>
				        </tr>
					    <tr class="MsoNormal">
					    	<td class="MsoNormal">&nbsp;</td>
			  				<td class="MsoNormal">�˺ţ�</td>
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
				        		<input type="button" value=" �� �� " class="button1" name="butSave" onclick="toSave(form1)"/>
				        		<input type="button" value=" ɾ �� " class="button1" name="butDelete" onclick="toDelete(form1)"/>
								<input type="button" value=" ��һ�� " class="button1" name="butNext" onclick="toNext(form1)"/>
								<input type="button" value=" �� �� " class="button1" name="butReturn" onclick="toReturn()"/>
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
/* ҳ�潹�㼰�س����� */
firstFocus(form1.systemName);
//setSubmitFunction("toSave(form1)");
setFormName("form1");

function toSave(form){
	if (!validate(form)) return;
	
	if(confirm("��ȷ��Ҫ������")){
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
	
	if(confirm("��ȷ��Ҫɾ����")){
		showSending();
		form.submit();
	}
}

function toReturn(){
	window.location.href = "../view/as_v001.jsp";
}

function validate(form){
	if(form.systemName.value == ""){
		alert("��ϵ���Ʋ���Ϊ�գ�");
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