<%--
 程序名称 ：Home.jsp
 功能说明 : 主页
 作    者 ：刘琰
 日    期 ：2003年11月26日
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*" %>
<%
   	response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
        String strTitle = "";
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false)
		{
			//OBHtml.showCommonMessage(out,Notes.CODE_COMMONMESSAGE_LOGIN,sessionMng,	Notes.CODE_OB_MODULE_TYPE_XT,"中油财务有限责任公司",Notes.CODE_SHOWMENU_TYPE_SHOW,"");
			out.flush();
			return;
		}
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.ShowMenu.YES);

%>

<table width="770" border="0" cellspacing="0" cellpadding="0" align="center" height="400">
  <tr> 
    <td width="20" rowspan="2" align="left" valign="top"></td>
    <td width="65" rowspan="2" align="left" valign="top">&nbsp;
  
    <p>&nbsp;</p></td>
    <td width="5" rowspan="2"></td>
    <td width="680" height="130" align="left" valign="top"><img src="/itreasury/graphics/mainbank.jpg" ></td>
  </tr>
  <tr> 
    <td width="580" align="left" valign="top">&nbsp;

    </td>
  </tr>
</table>
<%
	//显示文件尾
	OBHtml.showOBHomeEnd(out);
%>