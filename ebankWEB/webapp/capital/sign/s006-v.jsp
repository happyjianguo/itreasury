<%--
/*
 * 程序名称：s006-v.jsp
 * 功能说明：交易指令单条签认或取消签认输出页面
 * 作　　者：kewen hu
 * 完成日期：2004-02-07
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<%
    Log.print("\n*******进入页面--ebank/capital/sign/s006-v.jsp******\n");
    //标题变量
    String strTitle = "[业务签认]";
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
        long lShowMenu = OBConstant.ShowMenu.YES;
        String strMenu = (String)request.getParameter("menu");
        if (strMenu != null && strMenu.equals("hidden")) {
            lShowMenu = OBConstant.ShowMenu.NO;
        }
        Log.print("=================strMenu="+strMenu);
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
        Vector rf = null;//显示
        rf = (Vector)request.getAttribute("return");
        QueryCapForm rsForm = (QueryCapForm)request.getAttribute("FormValue");//查询条件信息
        String strIsCheck = (String)request.getAttribute("isCheck");//1 是复核
%>
<form name="form1" method="post">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <BR/>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">
					<%
				    if (strIsCheck.equalsIgnoreCase("1")) {
				        out.print("业务签认确认");
				    } else {
				        out.print("业务取消签认确认");
				    }
					%>
					</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

<br/>
 
     <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
        <tr >
          <td colspan="3" height="1"></td>
        </tr>
        <tr>
          <td width="5" height="25"></td>
    <td height="" >
            
<%
        int iCount = rf.size();//显示数据的个数
        for (int i=0; i<iCount; i++) {
            String strTmp = (String)rf.elementAt(i);
            out.print("<br>"+strTmp);
        }
%>
            
            </td>
          <td width="1" height="25"></td>
        </tr>
        <tr>
          <td colspan="5">
          </td>
          <td width="60">
            <div align="right">
			<input type="Button" class="button1" name="button" value=" 关 闭 " width="46" height="18" border="0" style="cursor:hand" onClick="javascript:window.close();"></div>
          </td>
          <td></td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
      <br/>
 	</td>
  </tr>
</table>

<input type="hidden" name="SelectType" size="12" value="<%=rsForm.getTransType()%>">
<input type="hidden" name="txtConfirmA" size="12" value="<%=rsForm.getStartSubmit()%>">
<input type="hidden" name="txtConfirmB" size="12" value="<%=rsForm.getEndSubmit()%>">
<input type="hidden" name="SelectStatus" size="12" value="<%=rsForm.getStatus()%>">
<input type="hidden" name="txtMinAmount" size="12" value="<%=rsForm.getMinAmount()%>">
<input type="hidden" name="txtMaxAmount" size="12" value="<%=rsForm.getMaxAmount()%>">
<input type="hidden" name="txtExecuteA" size="12" value="<%=rsForm.getStartExe()%>">
<input type="hidden" name="txtExecuteB" size="12" value="<%=rsForm.getEndExe()%>">
</form>

<script language="JavaScript">
    function window.onunload() {        
        window.opener.document.form1.ActionID.value="<%=new java.util.Date().getTime()%>";
        window.opener.doQuery();
    }
</script>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
    OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>