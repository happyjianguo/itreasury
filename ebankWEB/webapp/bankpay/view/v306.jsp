<%--
/*
 * �������ƣ�
 * ����˵������Ϣ��ѯͳ��
 * �������ߣ�baihuili
 * ���ڣ�2006��09��17��
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
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
    String strTitle = "�����л���";
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

        //��ʾ�ļ�ͷ
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
        eBankPrint.showPrintReport(out,sessionMng,"A4",2,true);

        // ϵͳʱ��
        long lSysDate = java.lang.System.currentTimeMillis();
        Timestamp dtSysDate = new Timestamp(lSysDate);
%>
<form method="post" name="frmjyzj">
<input type="hidden" name="lTransType" value="" style="display:none">
<table width="920" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px">���л����ܽ�</font></b></td>
      </tr>
    </table>
      <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0" class="table1">
<%
    Vector vctCap = (Vector) session.getAttribute("vctCap");
    Log.print("vctCap=" + vctCap);
    if (vctCap != null) {
        boolean isLoan = false;
       
        int vctCapLen = vctCap.size();
        OBCapSummarizeInfo obCSI = null;
        for (int i = 0; i < vctCapLen; i++) {
            obCSI = (OBCapSummarizeInfo)vctCap.get(i);
%>
     
        <tr >
          <td  width="5" valign="top" align="left" height="22"></td>
          <td width="565"height="22" colspan="3" ><font size="3"><b>���ڣ�<%=obCSI.getFormatConfirmDate().substring(0,10)%></b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="4"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" width="500" colspan="3">
            <p>���б�����<%=obCSI.getTotalCount()%></p>
          </td>
        </tr>
        <tr>
          <td height="25" width="40"></td>
          <td height="25" width="120" >δ���˱�����<%=obCSI.getUnCheckCount()%></td>
          <td height="25" width="120" >�Ѹ��˱�����<%=obCSI.getCheckCount()%></td>
          <td height="25" width="120" >��ǩ�ϱ�����<%=obCSI.getSignCount()%></td>
         
          </tr>
          
        
        <tr >
          <td width="5" height="25"></td>
          <td height="25" colspan="3" >
 
            <p>�ܽ��׽�<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
            ���У�����<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getLoanAmount())%>
            ���裩<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount((obCSI.getDebitAmount())%></p>
 
          </td>
        </tr>
      
      <br>
<%  
        }
    } else {
%>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="FormTitle">
          <td  width="5" valign="top" align="left" height="22"></td>
          <td width="565"height="22" ><font size="3" ><b>���ڣ�</b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="3"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" >
            <p>���б�����0</p>
          </td>
        </tr>
     
      <br>
<%
    }
%>
 </table>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="450">
          <div align="left"><span >��ѯʱ�䣺<%=DataFormat.getDateString().substring(0,10)%></span></div>
          </td>
        </tr>
      </table>
      <br>
   
</form>

<script language="javascript">
    function printMe() {
        frmjyzj.action = "q007-v.jsp";
        frmjyzj.lTransType.value = "<%=lTransType%>";
        frmjyzj.target = "NewWindow_S";
        frmjyzj.submit();
    }

    function returnMe() {
        frmjyzj.action = "../view/v301.jsp";
        frmjyzj.target = "";
        frmjyzj.submit();
    }
</script>
<%
	OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
 
%>
<%@ include file="/common/SignValidate.inc" %>