<%--
 页面名称 ：v101_P.jsp
 页面功能 : 电子回单打印控件安装
 作    者 ：xubo
 日    期 ：2007-9-6
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 java.net.URLEncoder,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* 标题固定变量 */
	String strTitle = "[控件安装]";
%>	
<% String strContext = request.getContextPath();%>
<%
  try
  {
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

	  //显示文件头
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>		
<jsp:include flush="true" page="/ShowMessage.jsp" />
<script src="../../fceform/eprint/eprint.js"></script>
<script src="../../fceform/eprint/printer.js"></script>
<script src="../../fceform/js/fcpub.js"></script>
<script src="../../fceform/js/fcopendj.js"></script>
<safety:resources />

<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<form name="frm" method="post">
<input name="strSuccessPageURL" type="hidden" >
<input name="strFailPageURL" type="hidden" >
<input type="hidden" name="strAction" >
<input type="hidden" name="path" >

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">控件安装</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
        	<table width=100% class=normal border="0" cellspacing="0" cellpadding="0">
		      <tr><td>&nbsp;</td></tr>
		      	<TR>
					<TD nowrap height=20 colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;<b>安装打印控件之前请您先将正确的访问路径添加至"受信任站点"中</b></TD>
				</TR>
				
				<TR>
					<TD nowrap height=20 colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;例如：</TD>
				</TR>
				
				<TR>
					<TD nowrap height=20 colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://127.0.0.1:8081/NASApp/iTreasuryMain.jsp?currencyID=1&nbsp;&nbsp;添加为&nbsp;&nbsp;<b>http://127.0.0.1</b></TD>
				</TR>
				<TR>
					<TD colSpan=5 height=20>&nbsp;</TD>
				</TR>
		      	<tr>
					<td align="right" colSpan=5>
						<input class=button1 name="search" type=button value=" 安装控件 " onclick="doSetup();">&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr><td height="5"></td></tr>
    		</table>
    		<br/>
    	</td>
   	</tr>
   </table>
</form>

<script language="javascript">
	function doSetup()
	{
		frm.strAction.value="setup";
		frm.path.value = "<%=Config.getProperty(ConfigConstant.GLOBAL_EVOUCHER_FILEPATH)%>";
		frm.action = "<%=strContext%>/print/control/c101_P.jsp";
		frm.submit();
	}
</script>			

<%	
    //显示文件尾
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

<%@ include file="/common/SignValidate.inc" %>
