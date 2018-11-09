<%--
/*
 * 程序名称：
 * 功能说明：信息查询统计
 * 作　　者：baihuili
 * 日期：2006年09月17日
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
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
        eBankPrint.showPrintReport(out,sessionMng,"A4",2,true);

        // 系统时间
        long lSysDate = java.lang.System.currentTimeMillis();
        Timestamp dtSysDate = new Timestamp(lSysDate);
%>
<form method="post" name="frmjyzj">
<input type="hidden" name="lTransType" value="" style="display:none">
<table width="920" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td align="center" ><b><font style="font-size:22px">银行汇款交易总结</font></b></td>
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
          <td width="565"height="22" colspan="3" ><font size="3"><b>日期：<%=obCSI.getFormatConfirmDate().substring(0,10)%></b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="4"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" width="500" colspan="3">
            <p>共有笔数：<%=obCSI.getTotalCount()%></p>
          </td>
        </tr>
        <tr>
          <td height="25" width="40"></td>
          <td height="25" width="120" >未复核笔数：<%=obCSI.getUnCheckCount()%></td>
          <td height="25" width="120" >已复核笔数：<%=obCSI.getCheckCount()%></td>
          <td height="25" width="120" >已签认笔数：<%=obCSI.getSignCount()%></td>
         
          </tr>
          
        
        <tr >
          <td width="5" height="25"></td>
          <td height="25" colspan="3" >
 
            <p>总交易金额：<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getTotalAmount())%>
            其中（贷）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount(obCSI.getLoanAmount())%>
            （借）<%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatListAmount((obCSI.getDebitAmount())%></p>
 
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
          <td width="565"height="22" ><font size="3" ><b>日期：</b></font></td>
        </tr>
        <tr>
          <td  height="1" colspan="3"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" >
            <p>共有笔数：0</p>
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
          <div align="left"><span >查询时间：<%=DataFormat.getDateString().substring(0,10)%></span></div>
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