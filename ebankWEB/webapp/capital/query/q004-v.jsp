<%--
/*
 * �������ƣ�q004-v.jsp
 * ����˵�������������ѯ����ҳ���ӡ
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-11
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.*"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.util.eBankPrint"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<%//@ page import="com.iss.itreasury.settlement.print.IPrintTemplate"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

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

        long lTransType = -1;       // �������н�������
        String sTemp = null;    //��ʱ��
        sTemp = (String) request.getParameter("lTransType");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lTransType = Long.parseLong(sTemp); // �������н�������
            if (lTransType == 0) {
                lTransType = -1;
            }
            Log.print("��������=" + lTransType);
        }
        //�������л�ȡ��ѯ�����Ϣ
        Collection lstQuery = (Collection) request.getAttribute("cltQcf");
        Iterator listQuery = null;
        if (lstQuery != null) {
            listQuery = lstQuery.iterator();
        }
        // ϵͳʱ��
        
        Timestamp dtSysDate = Env.getSystemDateTime();
%>

<%
	//IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	eBankPrint.showPrintReport(out,sessionMng,"A4",2,false);
%>


<%
    String sPreConfirmDate = "";    //ǰһ��ȷ������
    String sConfirmDate = "";       //ȷ������
    String sPrePayerAcctNo = "";    //ǰһ�����ڴ��
    String sPayerAcctNo = "";       //���ڴ��

    switch((int) lTransType) {
        case (int)-1:
        case (int)OBConstant.QueryInstrType.CAPTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
        case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
        case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
        case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
%>
<html>
<body text="#000000" bgcolor="#FFFFFF">
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
<br>
<br>
<br>
<div align="center">
	<table width="920" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px"><%=sessionMng.m_strClientName%></font></b></td>
      </tr>
    </table>
	<br>
    <table width="920" border="0" cellspacing="0" cellpadding="3" class="table1">
       <tr class="ItemBody">
         <td width="80" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>ָ�����</div>
         </td>

         <td width="110" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>��������</div>
         </td>
       
         <td width="40" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>��/��</div>
         </td>
         <td width="120" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>���</div>
         </td>
         <td width="220" align="center" height="18" colspan="2" class="td-rightbottom" nowrap>
           <div>�Է�����</div>
         </td>
         <td width="40" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>״̬</div>
         </td>
         <td width="80" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>���ױ��</div>
         </td>
        <%
            if (lTransType == -1) {
        %>
         <td width="80" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>��ͬ��</div>
         </td>
        <%
            }
        %>
         <td width="150" align="center" height="18" rowspan="2" class="td-rightbottom" nowrap>
           <div>��ע</div>
         </td>
       </tr>
       <tr class="ItemBody">
        <%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT) {
        %>
         <td width="100" align="center" height="18" rowspan="1" class="td-rightbottom" nowrap>
           <div>���ڴ������</div>
         </td>
        <%
            } else {
        %>
         <td width="100" align="center" height="18" rowspan="1" class="td-rightbottom" nowrap>
           <div>����</div>
         </td>
        <%
            }
        %>
         <td width="120" align="center" height="18" rowspan="1" class="td-rightbottom" nowrap>
           <div>�˺�</div>
         </td>
       </tr>
<%
        if (listQuery != null) {
            //����������򣬰���ʽ��ʾ���м�¼
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
%>
       <tr class="ItemBody">
         <td class="td-rightbottom" height="19">�ύ���ڣ�</td>
         <td colspan="3" class="td-rightbottom" height="19"><%=sConfirmDate%></td>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="6"class="td-rightbottom" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="5"class="td-rightbottom" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
                sPrePayerAcctNo = sPayerAcctNo;
                sPayerAcctNo = info.getPayerAcctNo();
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate) ||
                    (sConfirmDate.equals(sPreConfirmDate) && !sPayerAcctNo.equals(sPrePayerAcctNo))) {
%>
       <tr class="ItemBody">
        <%
            if (lTransType == -1) {
        %>
         <td colspan="1" class="td-rightbottom" height="19"><%="&nbsp;"%></td>
         <td colspan="1" class="td-rightbottom" height="19">�˺ţ�</td>
         <td colspan="3" class="td-rightbottom" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":sPayerAcctNo%></td>
         <td colspan="5" class="td-rightbottom" height="19"><%="&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="1" class="td-rightbottom" height="19"><%="&nbsp;"%></td>
         <td colspan="1" class="td-rightbottom" height="19">���ڴ�</td>
         <td colspan="3" class="td-rightbottom" height="19">
            <%=sPayerAcctNo==null || sPayerAcctNo==""?"&nbsp;":NameRef.getNoLineAccountNo(sPayerAcctNo)%></td>
         <td colspan="4" class="td-rightbottom" height="19"><%="&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
%>
       <tr class="ItemBody">
         <td width="80" align="center" class="td-rightbottom" nowrap><%=info.getID()%></td>
         <td width="110" align="left" class="td-rightbottom" height="18" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%>
         </td>
         <td width="40" align="center" class="td-rightbottom" height="18" nowrap>
<%
            
                out.print("��");
           
%>
         </td>
         <td width="120" align="right" class="td-rightbottom" nowrap>
             <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%>
         </td>
        <%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT) {
        %>
         <td width="100" align="center" class="td-rightbottom" nowrap>
             <%=info.getFixedDepositTime()%>����</td>
        <%
            } else {
        %>
         <td width="100" align="left" class="td-rightbottom" nowrap>
            <%=info.getPayeeName()==null || info.getPayeeName()==""?"&nbsp;":info.getPayeeName()%></td>
        <%
            }
        %>
         <td width="120" align="center" class="td-rightbottom" nowrap>
            <%=info.getPayeeAcctNo()==null || info.getPayeeAcctNo()==""?"&nbsp;":NameRef.getNoLineAccountNo(info.getPayeeAcctNo())%></td>
         <td width="40" align="left" class="td-rightbottom" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
         <td width="80" align="center" class="td-rightbottom" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <%
            if (lTransType == -1) {
        %>
         <td width="80" align="center" class="td-rightbottom" nowrap>
             <%=info.getLoanContractNo()==""?"&nbsp;":info.getLoanContractNo()%></td>
        <%
            }
        %>
         <td width="150"  align="center" class="td-rightbottom" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
       </tr>
<%
            }
        } else {
%>
      <tr class="ItemBody">
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
        <%
            if (lTransType == -1) {
        %>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
        <%
            }
        %>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
        case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
        case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
		case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
%>
    <table width="920" border="0" cellspacing="0" cellpadding="3" class="table1">
      <tr class="ItemBody">
        <td align="center" colspan="2" height="22" class="td-rightbottom">
            <font size="3">�����ʽ�����˺�</font></td>
      </tr>
    </table>
    <table width="920" border="0" cellspacing="0" cellpadding="3" class="table1">
      <tr align="center" class="ItemBody">
        <td width="80" class="td-rightbottom" height="13" nowrap>ָ�����</td>
        <td width="80" class="td-rightbottom" height="13" nowrap>��������</td>
        <td width="80" class="td-rightbottom" height="13" nowrap>��ͬ��</td>
        <td width="80" class="td-rightbottom" height="13" nowrap>����ſ�����</td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
        %>
        <td width="140" class="td-rightbottom" height="13" nowrap>��Ϣ���û���</td>
        <%
            } else {
        %>
        <td width="140" class="td-rightbottom" height="13" nowrap>���</td>
        <%
            }
        %>
        <td  width="60" class="td-rightbottom" height="13" nowrap>״̬</td>
        <td  width="80" class="td-rightbottom" height="13" nowrap>���ױ��</td>
        <td  width="220" class="td-rightbottom" height="13" nowrap>��ע</td>
      </tr>
<%
        if (listQuery != null) {
            //����������򣬰���ʽ��ʾ���м�¼
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next(); // ��ȡ��һ����¼��Ϣ
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
%>
      <tr class="ItemBody">
         <td class="td-rightbottom" height="19">�ύ���ڣ�</td>
         <td colspan="3" class="td-rightbottom" height="19">
            <%=sConfirmDate==""?"&nbsp;":sConfirmDate%></td>
         <td colspan="4" class="td-rightbottom" height="19"><%="&nbsp;"%></td>
      </tr>
<%
                }
%>
      <tr align="center" class="ItemBody">
        <td  width="80" class="td-rightbottom" nowrap><%=info.getID()%></td>
        <td width="80" class="td-rightbottom" height="13" nowrap>
            <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
        <td width="80" class="td-rightbottom" height="13" nowrap>
            <%=info.getLoanContractNo()==null || info.getLoanContractNo()==""?"&nbsp;":info.getLoanContractNo()%></td>
        <td width="80" class="td-rightbottom" height="13" nowrap>
            <%=info.getPayDate() == null?"&nbsp;":DataFormat.getDateString(info.getPayDate())%></td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
			double interest = info.getRealInterest() + info.getRealCompoundInterest() + 
				info.getRealOverdueInterest() + info.getRealSuretyFee() + info.getRealCommission();
        %>
        <td width="140" class="td-rightbottom" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%>
			<%=interest==0.0?"&nbsp;&nbsp;":DataFormat.formatDisabledAmount(interest)%></td>
        <%
            } else {
        %>
        <td width="140" class="td-rightbottom" height="13" align="right" nowrap>
            <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
        <%
            }
        %>
        <td width="60" class="td-rightbottom" height="13" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
        <td width="80" class="td-rightbottom" height="13" nowrap>
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;":info.getTransNo()%></td>
        <td width="220" class="td-rightbottom" height="13" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;":info.getReject()%></td>
      </tr>
<%
            }
        } else {
%>
      <tr class="ItemBody">
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
         <td align="center" class="td-rightbottom"><%="&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
    }
%>
<br>
    <table width="920" border="0" cellspacing="0" cellpadding="0">
      <tr class="ItemBody">
        <td align="left" >��ѯʱ�䣺<%=dtSysDate%></td>
		<td align="right" >����Ա��<%=sessionMng.m_strUserName%></td>
      </tr>
    </table>
	
</div>

</body>
</html>
<%
		//SETTHTML.showHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
<%@ include file="/common/SignValidate.inc" %>