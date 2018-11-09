<%--
/*
 * 程序名称：batchck006-v.jsp
 * 功能说明：交易指令批量复核或取消复核输出页面
 * 作　　者：credream
 * 完成日期：2008年2月5日
 
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
		
		Vector rf = null;//显示信息
		rf = (Vector)request.getAttribute("return");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!"+rf.size());
%>
<script language="JavaScript" src="/webob/js/Control.js"></script>

<safety:resources />

<form method="post" name="form1">
    <table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
	    <br/>
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width=98% align="center">
				<TR>
			       <td class=title><span class="txt_til2">业务复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	<br/>
	<table width=98% border="0" cellspacing="0" cellpadding="0" align="center" class = normal>
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
      
      </table>
      
      <table width=100% align="">
      	<tr><td height="10"></td><td></td></tr>
        <tr class="MsoNormal">
          <td colspan=2 class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="60" class="MsoNormal">
            <div align="right">
			<!--img src="/webob/graphics/button_fanhui.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:doExit();"-->
			<input type="button" name="Submitv00204" value=" 返 回 " class="button1" onClick="javascript:doExit();">&nbsp;&nbsp;
			</div>
          </td>
        </tr>
      </table>
      <br/>
 	</td>
  </tr>
</table>
</form>

	  <script language="JavaScript">
	  function doExit()
	  {
			
			form1.action = "batchck001-v.jsp";
	showSending();
		document.form1.submit();
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