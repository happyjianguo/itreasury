<%--
/*
 * �������ƣ�q007-v.jsp
 * ����˵���������ܽ�������ҳ���ӡ
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-15
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.table2 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-leftright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px}
.td-leftbottomright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 1px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
body {
	font-family: "����";
	font-size: 14px;
}
td {
	font-family: "����";
	font-size: 12px;
}
.f16 {
	font-family: "����_GB2312";
	font-size: 16px;
}
.f14 {
	font-family: "����";
	font-size: 14px;
}
.f10 {
	font-family: "����";
	font-size: 10px;
	line-height:10px;
}
.f12 {
	font-family: "����";
	font-size: 12px;
}

.f22
{
  font-family:"����";
  font-size:22px;
}
.f15 {
	font-family: "����_GB2312";
	font-size: 16px;
}
.tnt {Writing-mode:tb-rl;Text-align:center;font-size:12px}
-->
</style>
<%
    //�������
    String strTitle = "�۽��������ѯ��";
    try {
        /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        long lTransType = -1;           //��������
        String sTemp = "";//��ʱ��
        sTemp = (String) request.getAttribute("lTransType");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lTransType = Long.parseLong(sTemp); // �������н�������
            if (lTransType == 0) {
                lTransType = -1;
            }
            Log.print("��������=" + lTransType);
        }

        // ϵͳʱ��
       
        Timestamp dtSysDate = Env.getSystemDateTime();
%>

<%IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);%>
<br>
<br>
<br>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px"><%=sessionMng.m_strClientName%></font></b></td>
      </tr>
    </table>
	<br>
<form method="post" name="frmjyzj">
<%
    Vector vctCap = (Vector) session.getAttribute("vctCap");
    Log.print("vctCap=" + vctCap);
    if (vctCap != null) {
        boolean isLoan = false;
        switch ((int)lTransType) {
            case (int) -1:
            case (int)OBConstant.QueryInstrType.CAPTRANSFER:
            case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
            case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
            case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
            case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
                isLoan = false;
            break;
            case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
            case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
            case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
                isLoan = true;
            break;
            default:
            break;
        }
        int vctCapLen = vctCap.size();
        OBCapSummarizeInfo obCSI = null;
        for (int i = 0; i < vctCapLen; i++) {
            obCSI = (OBCapSummarizeInfo)vctCap.get(i);
%>
<br>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td class="td-rightbottom">���ڣ�<%=obCSI.getFormatConfirmDate()%></td>
        </tr>
    </table>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td class="td-rightbottom">���б�����<%=obCSI.getTotalCount()%></td>
        </tr>
    </table>

    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td class="td-rightbottom">δ���˱�����<%=obCSI.getUnCheckCount()%></td>
          <td class="td-rightbottom">�����б�����<%=obCSI.getLApprovalingCount()%></td>
          <td class="td-rightbottom">������������<%=obCSI.getLApprovaledCount()%></td>
          <td class="td-rightbottom">�Ѹ��˱�����<%=obCSI.getCheckCount()%></td>
          <td class="td-rightbottom"><%="&nbsp;"%></td>
        </tr>
        <tr class="ItemBody">
          <td class="td-rightbottom">��ǩ�ϱ�����<%=obCSI.getSignCount()%></td>
          <td class="td-rightbottom">�����б�����<%=obCSI.getOnGoingCount()%></td>
          <td class="td-rightbottom">����ɱ�����<%=obCSI.getFinishedCount()%></td>
          <td class="td-rightbottom">�Ѿܾ�������<%=obCSI.getRefusedCount()%></td>
          <td class="td-rightbottom"><%="&nbsp;"%></td>
        </tr>
    </table>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td height="25" class="td-rightbottom">
<%
            if (isLoan) {
%>
            �ܻ����<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
<%
            } else {
%>
            �ܽ��׽�<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
            ���У�����<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getLoanAmount())%>
            ���裩<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getDebitAmount())%>
<% 
            }
%>
          </td>
        </tr>
      </table>
<%  
        }
    } else {
%>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td class="td-rightbottom"><font size="3">���ڣ�</font></td>
        </tr>
    </table>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td height="25" class="td-rightbottom">���б�����</td>
        </tr>
    </table>
<%
    }
%>
    <br>
	<table width="99%" border="0" cellspacing="0" cellpadding="3" class="table1">
        <tr class="ItemBody">
          <td align="left" class="td-rightbottom">��ѯʱ�䣺<%=dtSysDate%></td>
        </tr>
    </table>
	<br>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="right" >����Ա��<%=sessionMng.m_strUserName%></td>
      </tr>
    </table>
</form>
<%
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
<%@ include file="/common/SignValidate.inc" %>