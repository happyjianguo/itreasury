<%--
 ҳ������ ��query_v002_download.jsp
 ҳ�湦�� : ��Ϣ��ѯ �� ����ָ���ѯ������� 
 ��    �� ��leiyang
 ��    �� ��2008-12-05
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="application/msexcel;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.OBFinanceInstrBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>

<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
String strTitle = "����ָ���ѯ";
String strTemp = "";

long lFixTransferID = -1;       //����֧ȡ�˻�ID
long lNotifyTransferID = -1;    //֪֧ͨȡ�˻�ID

try{
	response.setHeader("Content-Disposition",";filename=\treport.xls");
	
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
   	
	Collection coll = (Collection)request.getAttribute("queryCapColl");
	
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	lFixTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
	lNotifyTransferID = obFinanceInstrBiz.getLoanAccountID(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<HTML>
<HEAD>
<style type="text/css">
<!--
.table1 {  border: 1px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
-->
</style>
</HEAD>
<BODY text="#000000">
<table width="100%" border="1" cellspacing="0" cellpadding="0" class="table1">
	<tr>
		<td width="12%" height="25" align="center" class="td-rightbottom" nowrap>ָ�����</td>
		<td width="14%" height="25" align="center" class="td-rightbottom" nowrap>��������</td>
		<td width="5%" height="25" align="center" class="td-rightbottom" nowrap>��/��</td>
		<td width="10%" height="25" align="center" class="td-rightbottom" nowrap>�� ��</td>
		<td width="15%" height="25" align="center" class="td-rightbottom" nowrap>�Է�����</td>
		<td width="12%" height="25" align="center" class="td-rightbottom" nowrap>�Է��˺�</td>
		<td width="6%" height="25" align="center" class="td-rightbottom" nowrap>״ ̬</td>
		<td width="9%" height="25" align="center" class="td-rightbottom" nowrap>���ױ��</td>
		<td width="10%" height="25" align="center" class="td-rightbottom" nowrap>�����;</td>
		<td width="7%" height="25" align="center" class="td-rightbottom" nowrap>�� ע</td>
	</tr>
	<%
	if(coll != null){
		Iterator listQuery = coll.iterator();
		String strFormatConfirmDate = "";
		long nPayerAcctID = -1;
           while(listQuery.hasNext()) {
               FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
               if(info.getTransNo() == null){
               	info.setTransNo("");
               }
               if(info.getNote() == null){
               	info.setNote("");
               }
               if(info.getReject() == null){
               	info.setReject("");
               }
               
               if(strFormatConfirmDate.equals("") || !strFormatConfirmDate.equals(DataFormat.getDateString(info.getConfirmDate()))){
               	strFormatConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                nPayerAcctID = info.getPayerAcctID();
       %>
        <tr>
			<td height="25" align="left" class="td-topright">�ύ���ڣ�</td>
			<td height="25" align="left" colspan="9" class="td-topright"><%=strFormatConfirmDate%></td>
        </tr>
        <tr>
			<td height="25" align="left" class="td-topright">�ʺţ�</td>
			<td height="25" align="left" colspan="9"  class="td-topright"><%=info.getPayerAcctNo()%></td>
        </tr>
    <%
               }
               else if(nPayerAcctID != info.getPayerAcctID()) {
               	nPayerAcctID = info.getPayerAcctID();
       %>
        <tr>
			<td height="25" align="left" class="td-topright">�ʺţ�</td>
			<td height="25" align="left" colspan="9" class="td-topright"><%=info.getPayerAcctNo()%></td>
        </tr>
       <%
               }
    %>
        <tr>
        	<td height="25" align="center" class="td-topright" nowrap>&nbsp;<%=info.getID()%>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>
			<%
				if(info.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_BANKPAY){
					 if(info.getSBatchNo()!=null && info.getSBatchNo().length()>0) {
						 out.println("��������-���л��");
					 }
					 else {
						 out.println(OBConstant.SettInstrType.getName(info.getTransType()));
					 }
				}
				else{
					out.println(OBConstant.SettInstrType.getName(info.getTransType()));
				}
			%>
			</td>
			<td height="25" align="center" class="td-topright" nowrap><%="��"%></td>
			<td height="25" align="right" class="td-topright" nowrap><%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
			<td height="25" align="left" class="td-topright" nowrap><%=info.getPayeeName()%></td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;<%=info.getPayeeAcctNo()%>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap><%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;<%=info.getTransNo()%>&nbsp;</td>
			<td height="25" align="left" class="td-topright" nowrap><%=info.getNote()%></td>
			<td height="25" align="left" class="td-topright" nowrap><%=info.getReject()%></td>
        </tr>
       <%
      
           }
	}
	else{
	%>
        <tr>
        	<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
			<td height="25" align="center" class="td-topright" nowrap>&nbsp;</td>
        </tr>
    <%}%>
</table>
</BODY>
</HTML>
<%
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>