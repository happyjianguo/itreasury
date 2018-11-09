<%--
/*
 * 程序名称：C416.jsp
 * 功能说明：交易指令批量复核或取消复核输出页面
 * 作　　者：刘琰
 * 完成日期：2003年09月25日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<!--Header end-->

<%
	//标题变量
	String strTitle = null;
%>

<%
	/* 用户登录检测与权限校验 */
	try{ 
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
           
        /* 显示文件头 */
       	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		String sign ="";
        String sTemp = "";
		Vector rf = null;//显示信息
		rf = (Vector)request.getAttribute("return");
		QueryCapForm rsForm = (QueryCapForm)request.getAttribute("FormValue");//查询条件信息
		String strIsCheck = (String)request.getAttribute("isCheck");//1 是复核
		String str = (String)request.getAttribute("flag");
		if(str!=null&&str.equals("checked")){
			out.println("<script language='javascript'>");
			out.println("alert('复核成功')");
			out.println("</script>");
		}
		sTemp = (String) request.getParameter("sign");
        if (sTemp != null && sTemp.trim().length() > 0) {
            sign = sTemp;
        }
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>

<safety:resources />
 
  <form name="form1" method="post">   
    <table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
	    	<br/>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width=98% align="center">
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
   
<br/>
<table width=98% border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>

    <td height="" class="MsoNormal">
           
<%
		int iCount = rf.size();//数量
		for (int i=0;i<iCount;i++)//显示
		{
			String strTmp = (String)rf.elementAt(i);
			out.print("<br>"+strTmp);
		}
%>
           
			</td>
          <td width="1" height="25"></td>
        </tr>
       <tr >
         
          <td colspan="2">
            <div align="right">
			<!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit();"-->
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="javascript:doExit();">&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
          </td>
          <td>&nbsp;</td>
        </tr>
        <tr><td height="5"></td></tr>
      </table>
     <br/>
  	</td>
  </tr>
</table>

	 
	        <input type="hidden" name="SelectType" value="<%= rsForm.getTransType() %>">
            <input type="hidden" name="txtConfirmA" size="12" value="<%= rsForm .getStartSubmit() %>">
            <input type="hidden" name="txtConfirmB" size="12" value="<%= rsForm .getEndSubmit() %>">
            <input type="hidden" name="SelectStatus"  value="<%= rsForm .getStatus() %>">
            <input type="hidden" name="txtMinAmount" size="12" value="<%= rsForm .getMinAmount() %>">
            <input type="hidden" name="txtMaxAmount" size="12" value="<%= rsForm .getMaxAmount() %>">
            <input type="hidden" name="txtExecuteA" size="12" value="<%= rsForm .getStartExe() %>" >
            <input type="hidden" name="txtExecuteB" size="12" value="<%= rsForm .getEndExe() %>" >
            <input type="hidden" name="sign" size="12" value="<%=sign%>">
	  </form>

	  <script language="JavaScript">
	  function doExit()
	  {
			form1.action = "C412.jsp";
			showSending(); form1.submit();
	  }
	  </script>
<%
   }
   catch(IException ie)
   {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
   }
%>

<%
		OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>