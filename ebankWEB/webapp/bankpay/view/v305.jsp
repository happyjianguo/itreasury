<%--
/*
 * 程序名称：
 * 功能说明：信息查询统计
 * 作　　者：baihuili
 * 日期：2006年09月17日
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

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />
<%
    //标题变量
    String strTitle = "［银行汇款］";
    try {
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        long lTransType = -1;           //交易类型
        String sTemp = "";//临时量
        sTemp = (String) request.getAttribute("lTransType");
        if(sTemp != null && sTemp.trim().length() > 0) {
            lTransType = Long.parseLong(sTemp); // 网上银行交易类型
            if (lTransType == 0) {
                lTransType = -1;
            }
            Log.print("交易类型=" + lTransType);
        }

        //显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        // 系统时间
        long lSysDate = java.lang.System.currentTimeMillis();
        Timestamp dtSysDate = new Timestamp(lSysDate);
%>
<form method="post" name="frmjyzj">
<input type="hidden" name="lTransType" value="" style="display:none">
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
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr  class="FormTitle">
          <td  width="5" valign="top" align="left" height="22"></td>
          <td width="565"height="22" ><font size="3"><b>日期：<%=obCSI.getFormatConfirmDate().substring(0,10)%></b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="3"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext">
            <p>共有笔数：<%=obCSI.getTotalCount()%></p>
          </td>
        </tr>
      </table>

      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr>
          <td  height="25" width="1"></td>
          <td height="25" width="40"></td>
          <td height="25" width="90" class="graytext">未复核笔数：<%=obCSI.getUnCheckCount()%></td>
          <td height="25" width="90" class="graytext">已复核笔数：<%=obCSI.getCheckCount()%></td>
          <td height="25" width="90" class="graytext">已签认笔数：<%=obCSI.getSignCount()%></td>
          <td height="25" width="90" class="graytext">&nbsp;</td>
          </tr>
          
        <tr>
          <td  height="1" colspan="8"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext">
 
            <p>总交易金额：<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
            其中（贷）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getLoanAmount())%>
            （借）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getDebitAmount())%></p>
 
          </td>
        </tr>
      </table>
      <br>
<%  
        }
    } else {
%>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="FormTitle">
          <td  width="5" valign="top" align="left" height="22"></td>
          <td width="565"height="22" ><font size="3" ><b>日期：</b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="3"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext">
            <p>共有笔数：</p>
          </td>
        </tr>
      </table>
      <br>
<%
    }
    session.setAttribute("vctCap", vctCap);
%>
    <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="450">
          <div align="left"><span class="graytext">查询时间：<%=DataFormat.getDateString().substring(0,10)%></span></div>
          </td>
        </tr>
      </table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="376">
            <div align="right"></div>
          </td>
          <td width="134">
            <div align="right">
			<!--img src="\webob\graphics\button_dayin.gif" width="46" height="18" onClick="javascript:printMe()"-->
			<input type="button" name="Submitv00204" value=" 打  印 " class="button" onClick="printMe();">
			</div>
          </td>
          <td width="60">
            <div align="right">
			<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onClick="javascript:returnMe()"-->
			<input type="button" name="Submitv00204" value=" 返  回 " class="button" onClick="javascript:history.go(-1)">
			</div>
          </td>
        </tr>
      </table>
</form>

<script language="javascript">
    function printMe() {
        frmjyzj.action = "../view/v306.jsp";
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
    } catch (IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>