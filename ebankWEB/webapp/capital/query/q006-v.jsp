<%--
/*
 * �������ƣ�q006-v.jsp
 * ����˵���������ܽ�������ҳ��
 * �������ߣ�kewen hu
 * ������ڣ�2004-02-14
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

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>


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

        //��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        // ϵͳʱ��
        
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<form method="post" name="frmjyzj">
<input type="hidden" name="lTransType" value="" style="display:none">
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
        %>
<table cellpadding="0" cellspacing="0" class="title_top" >
	  <tr>
	  <td height="24">
<%
        for (int i = 0; i < vctCapLen; i++) {
            obCSI = (OBCapSummarizeInfo)vctCap.get(i);
%>
  

		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2" >���ڣ�<%=obCSI.getFormatConfirmDate().toString().substring(0,10)%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			
		<br/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
			<tr><td>
		      <table width="766" border="0" cellspacing="0" align="center" cellpadding="0">
		        <tr >
		          <td width="5" height="25"></td>
		          <td height="25" class="graytext">
		            <p>���б�����<%=obCSI.getTotalCount()%></p>
		          </td>
		        </tr>
		      </table>
		     </td></tr>
		     <tr><td>
		      <table width="766" border="0" cellspacing="0" cellpadding="0" align="center" >
		        <tr>
		          <td  height="25" width="1"></td>
		          <td height="25" width="40"></td>
		          <td height="25" width="90" class="graytext">δ���˱�����<%=obCSI.getUnCheckCount()%></td>
		          <td height="25" width="90" class="graytext">�����б�����<%=obCSI.getLApprovalingCount()%></td>
		          <td height="25" width="90" class="graytext">������������<%=obCSI.getLApprovaledCount()%></td>
		          <td height="25" width="90" class="graytext">�Ѹ��˱�����<%=obCSI.getCheckCount()%></td>
		          </tr>
		          <tr>
		          <td  height="25" width="1"></td>
		          <td height="25" width="40"></td>
		          <td height="25" width="90" class="graytext">��ǩ�ϱ�����<%=obCSI.getSignCount()%></td>
		          <td height="25" width="90" class="graytext">�����б�����<%=obCSI.getOnGoingCount()%></td>
		          <td height="25" class="graytext" width="90">����ɱ�����<%=obCSI.getFinishedCount()%></td>
		          <td height="25" class="graytext" width="90">�Ѿܾ�������<%=obCSI.getRefusedCount()%></td>
		         
		        </tr>
		        <tr>
		          <td  height="1" colspan="8"></td>
		        </tr>
		      </table>
		      </td></tr>
		      <tr><td>
		      <table width="766" border="0" cellspacing="0" align="center" cellpadding="0">
		        <tr >
		          <td width="5" height="25"></td>
		          <td height="25" class="graytext">
		<%
		            if (isLoan) {
		%>
		            <p>�ܻ����<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%></p>
		<%
		            } else {
		%>
		            <p>�ܽ��׽�<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
		            ���У�����<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getLoanAmount())%>
		            ���裩<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getDebitAmount())%></p>
		<% 
		            }
		%>
		          </td>
		        </tr>
		      </table>
		      </td></tr>
		      </table>
		      <br>
		<%  
		        }
		    } else {
		%>
		      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
		        <tr>
		          <td  width="5" valign="top" align="left" height="22"></td>
		          <td width="565"height="22" ><font size="3" ><b>���ڣ�</b></font></td>
		        </tr>
		        <tr>
		          <td  height="1" colspan="3"></td>
		        </tr>
		      </table>
		      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal>
		        <tr >
		          <td width="5" height="25"></td>
		          <td height="25" class="graytext">
		            <p>���б�����</p>
		          </td>
		        </tr>
		      </table>
		      <br>
		<%
		    }
		    session.setAttribute("vctCap", vctCap);
		%>
   	  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="450">
          <div align="left"><span class="graytext">��ѯʱ�䣺<%=DataFormat.getDateString(dtSysDate)%></span></div>
          </td>
        </tr>
      </table>
      <br>
      <table width="98%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right">
			<input type="button" name="Submitv00204" value=" �� ӡ " class="button1" onClick="printMe();">&nbsp;&nbsp;
			<input type="button" name="Submitv00204" value=" �� �� " class="button1" onClick="doreturn();">&nbsp;&nbsp;

          </td>
        </tr>
      </table>
          </td>
    </tr>
  </table> 
</form>

<script language="javascript">
    function printMe() {
        frmjyzj.action = "q007-v.jsp";
        frmjyzj.lTransType.value = "<%=lTransType%>";
        frmjyzj.target = "NewWindow_S";
        frmjyzj.submit();
    }
    function doreturn() {
       history.go(-1);
    }
</script>
<%
	OBHtml.showOBHomeEnd(out);
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
<safety:resources />
<%@ include file="/common/SignValidate.inc" %>