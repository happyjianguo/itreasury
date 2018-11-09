<%--
 ҳ������ ��check_v001.jsp
 ҳ�湦�� : ҵ�񸴺�
 ��    �� ��leiyang
 ��    �� ��2008-12-19
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "ҵ�񸴺�";
String strTemp = "";
long lTransType = -1;

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
   	
   	strTemp = request.getParameter("lTransType");
   	if(strTemp != null && strTemp.length() > 0){
   		lTransType = Long.parseLong(strTemp);
   	}
   	else {
   		lTransType = OBConstant.QueryInstrType.CAPTRANSFER;
   	}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<form name="form1" method="post" action="">
<input type="hidden" name="strSuccessPageURL" value="">		<!--�����ɹ�ת��ҳ��-->
<input type="hidden" name="strFailPageURL" value="">		<!--����ʧ��ת��ҳ��-->
<input type="hidden" name="strAction" value="">  <!--��������-->
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2"><%=strTitle %></span></td>
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
					<td colspan="4" height="5" class="MsoNormal"></td>
				</tr>
		        <tr>
					<td width="4" height="25">&nbsp;</td>
		          	<td width="80" height="25" align="left">�������ͣ�</td>
		          	<td width="100" height="25">
	            	<%
	            		OBHtmlCom.showQueryCheckTypeListControl(out,"lTransType",lTransType," onfocus=\"nextfield ='butNext';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,false);
	            	%>
		          	</td>
		          	<td width="*%" height="25" align="left">
		          		<input type="button" value=" ȷ �� " class="button1" name="butNext" onclick="toNext(form1)"/>
		          	</td>
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
</tbody>
</table>
</form>
	
<script language="javascript">
/* ҳ�潹�㼰�س����� */
firstFocus(form1.lTransType);
//setSubmitFunction("toNext(form1)");
setFormName("form1");

function toNext(form)
{
	var transTypeId = form1.lTransType.value;
	if(transTypeId == "<%=OBConstant.QueryInstrType.CAPTRANSFER%>"){
		form.action = "check_v002.jsp";
		showSending();
	    form.submit();
	}
	else {
		form.action = "../ck006-c.jsp?SelectType=" + transTypeId;
		//form.strSuccessPageURL.value = "../view/query_v002.jsp";
		//form.strFailPageURL.value = "../view/check_v001.jsp";
		//form.strAction.value = "<%=OBConstant.QueryOperationType.CHECK%>";
		showSending();
	    form.submit();
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
<%@ include file="/common/SignValidate.inc"%>