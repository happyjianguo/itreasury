<%--
/*
 * �������ƣ�q005-v.jsp
 * ����˵�������������ѯ����ҳ������
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-11
 */
--%>

<%@ page contentType="application/msexcel;charset=gbk"%>

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
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    response.setHeader("Content-Disposition",";filename=\treport.xls");
%>
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
<HTML>
<HEAD><TITLE></TITLE>
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
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
       <tr>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>ָ�����</td>
        <%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT) {
        %>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>�����;</td>
        <%
            } else {
        %>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>��������</td>
        <%
            }
        %>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>��/��</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>���</td>
         <td align="center" colspan="2" class="td-rightbottom" nowrap>�Է�����</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>״̬</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>���ױ��</td>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>�����;</td>
        <%
            if (lTransType == -1) {
        %>
        
        <%
            }
        %>
         <td align="center" rowspan="2" class="td-rightbottom" nowrap>��ע</td>
       </tr>
       <tr>
        <%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT) {
        %>
         <td align="center" class="td-rightbottom" nowrap>���ڴ������</td>
        <%
            } else {
        %>
         <td align="center" class="td-rightbottom" nowrap>����</td>
        <%
            }
        %>
         <td align="center" class="td-rightbottom" nowrap>�˺�</td>
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
       <tr>
         <td align="left" class="td-topright">�ύ���ڣ�</td>
         <td align="left" colspan="3" class="td-topright"><%=sConfirmDate%></td>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="6" class="td-topright"><%="&nbsp;&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="6" class="td-topright"><%="&nbsp;&nbsp;"%></td>
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
       <tr>
        <%
            if (lTransType == -1) {
        %>
         <td colspan="1" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td colspan="1" class="td-topright">�˺ţ�</td>
         <td colspan="3" class="td-topright"><%=sPayerAcctNo==""?"&nbsp;&nbsp;":sPayerAcctNo+"&nbsp;"%></td>
         <td colspan="5" class="td-topright"><%="&nbsp;&nbsp;"%></td>
        <%
            } else {
        %>
         <td colspan="1" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td colspan="1" class="td-topright">���ڴ�</td>
         <td colspan="3" class="td-topright"><%=sPayerAcctNo==""?"&nbsp;&nbsp;":sPayerAcctNo%></td>
         <td colspan="5" class="td-topright"><%="&nbsp;&nbsp;"%></td>
        <%
            }
        %>
       </tr>
<%
                }
%>
       <tr>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;<%=String.valueOf(info.getID())%></td>
         <td align="center" class="td-topright" nowrap>
		 	<%if(info.getTransType()==2){
            		 if(info.getSBatchNo()!=null && info.getSBatchNo().trim().length() > 0) {
            		 %>
            		 ��������-���л��</td>
            		 <% 
            		 }
            		 else
            		 {%>            		           		
            		��ʸ���-���л��</td>
            		 <% 
            		 }
            	}else {           		
            		%>
            	     <%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
            	      <%
            	} %>	
         <td align="center" class="td-topright" nowrap>
<%
                out.print("��");
           
%>
         </td>
         <td align="right" class="td-topright" nowrap>
             <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%>
         </td>
        <%
            if (lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT) {
        %>
         <td align="center" class="td-topright" nowrap>
             <%=info.getFixedDepositTime()%>����</td>
        <%
            } else {
        %>
         <td align="center" class="td-topright" nowrap><%=info.getPayeeName()%></td>
        <%
            }
        %>
         <td align="center" class="td-topright" nowrap><%=info.getPayeeAcctNo()+"&nbsp;"%></td>
         <td align="center" class="td-topright" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%>
         </td>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;&nbsp;":info.getTransNo()%>
         </td>
         <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
             <%=info.getNote()== null||info.getNote()==""?"&nbsp;&nbsp;":info.getNote()%>
         </td>
        <%
            if (lTransType == -1) {
        %>
        
        <%
            }
        %>
         <td align="center" class="td-topright" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;&nbsp;":info.getReject()%>
         </td>
       </tr>
<%
            }
        } else {
%>
      <tr>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
        <%
            if (lTransType == -1) {
        %>
        
        <%
            }
        %>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
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
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
      <tr>
        <td align="center" class="td-rightbottom" nowrap>ָ�����</td>
        <td align="center" class="td-rightbottom" nowrap>��������</td>
        <td align="center" class="td-rightbottom" nowrap>��ͬ��</td>
        <td align="center" class="td-rightbottom" nowrap>����ſ�����</td>
        <td align="center" class="td-rightbottom" nowrap>
            <%=lTransType==OBConstant.QueryInstrType.INTERESTFEEPAYMENT?"��Ϣ���û���":"���"%></td>
        <td align="center" class="td-rightbottom" nowrap>״̬</td>
        <td align="center" class="td-rightbottom" nowrap>���ױ��</td>
        <td align="center" class="td-rightbottom" nowrap>��ע</td>
      </tr>
<%
        if (listQuery != null) {
            //����������򣬰���ʽ��ʾ���м�¼
            while(listQuery != null && listQuery.hasNext()) {
                FinanceInfo info = (FinanceInfo)listQuery.next();//��ȡ��һ����¼��Ϣ
                sPreConfirmDate = sConfirmDate;
                sConfirmDate = DataFormat.getDateString(info.getConfirmDate());
                if ("".equals(sPreConfirmDate) || !sConfirmDate.equals(sPreConfirmDate)) {
%>
      <tr>
         <td align="left" class="td-topright">�ύ���ڣ�</td>
         <td align="left" colspan="3" class="td-topright">
            <%=sConfirmDate==""?"&nbsp;&nbsp;":sConfirmDate%></td>
         <td colspan="4" class="td-topright"><%="&nbsp;&nbsp;"%></td>
      </tr>
<%
                }
%>
      <tr>
        <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;<%=String.valueOf(info.getID())%></td>
        <td align="center" class="td-topright" nowrap>
			<%=OBConstant.SettInstrType.getName(info.getTransType())%></td>
        <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
            <%=info.getLoanContractNo()==""?"&nbsp;&nbsp;":info.getLoanContractNo()%></td>
        <td align="center" class="td-topright" nowrap>
            <%=DataFormat.getDateString(info.getPayDate())%></td>
        <%
            if (lTransType == OBConstant.QueryInstrType.INTERESTFEEPAYMENT) {
			double interest = info.getRealInterest() + info.getRealCompoundInterest() + 
				info.getRealOverdueInterest() + info.getRealSuretyFee() + info.getRealCommission();
        %>
        <td align="right" class="td-topright" nowrap>
            <%=sessionMng.m_strCurrencySymbol%>
			<%=interest==0.0?"&nbsp;&nbsp;":DataFormat.formatDisabledAmount(interest)%></td>
        <%
            } else {
        %>
        <td align="right" class="td-topright" nowrap>
            <%=sessionMng.m_strCurrencySymbol%><%=info.getFormatAmount()%></td>
        <%
            }
        %>
        <td align="center" class="td-topright" nowrap>
            <%=OBConstant.SettInstrStatus.getName(info.getStatus())%></td>
        <td align="center" class="td-topright" nowrap>&nbsp;&nbsp;
            <%=info.getTransNo()==null || info.getTransNo()==""?"&nbsp;&nbsp;":info.getTransNo()%></td>
        <td align="center" class="td-topright" nowrap>
            <%=info.getReject()==null || info.getReject()==""?"&nbsp;&nbsp;":info.getReject()%></td>
      </tr>
<%
            }
        } else {
%>
      <tr>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
         <td align="center" class="td-topright"><%="&nbsp;&nbsp;"%></td>
      </tr>
<%
        }
%>
    </table>
<%
        break;
    }
%>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
      <tr>
        <td align="left" class="td-topright">��ѯʱ�䣺<%=dtSysDate%></td>
      </tr>
    </table>
</BODY>
</HTML>
<%
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
