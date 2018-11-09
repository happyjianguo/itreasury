<%--
/**
 * 程序名称：V853.jsp
 * 功能说明：系统管理-用户管理
 * 作　　者：刘琰
 * 完成日期：2003年9月5日
 */
--%>


<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="getData" class="com.iss.itreasury.system.privilege.util.GetData" scope="page"/>
<%@ page import="java.util.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.system.bizlogic.EBankbean,
				com.iss.itreasury.ebank.privilege.dataentity.*,
				com.iss.itreasury.ebank.privilege.bizlogic.*,
				com.iss.itreasury.ebank.privilege.dao.*,
				com.iss.itreasury.ebank.privilege.util.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
response.setHeader("Cache-Control","no-stored");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<!--Header end-->
<%
//固定变量
String strTitle = "[用户管理]";
String strContext = request.getContextPath();//http://xxxx/../cpob
try
{
	/**
	* isLogin start
	*/
	//登录检测
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}
	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    Collection c = (Collection) request.getAttribute("UsersOfClient");
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<form name="form1" method=post >

  <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用户管理</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>  
    
			<br/>
	
			<table width=100% border="1" align="" cellpadding="0" cellspacing="0" class=normal > 
			<thead>
			<TR align=middle >
				<TD width=20% height=17 >用户名称</TD>
				<TD width=15% height=17 >登录名称</TD>
				<TD width=10% height=17 >币种</TD>
				<TD width=15% height=17 >录入人</TD>
				<TD width=15% height=17 >录入日期</TD>
				<TD width=15% height=17 >修改密码</TD>
			</TR>
			</thead>
	<%
			if( c != null )
			{
				Iterator it = c.iterator();
	
				while( it.hasNext() )
				{
					OB_UserInfo pi = (OB_UserInfo) it.next();
	
	%>		
			<TR align="center" >
				<TD height=17 align="center" > <a href="javascript:view('<%=pi.getId()%>');"> <%= pi.getSName()%></a></TD>
				<TD height=17 ><%= pi.getSLoginNo() %></TD>
				<TD height=17 align="left"><%= pi.getNCurrencyId() > 0 ? Constant.CurrencyType.getName(pi.getNCurrencyId()) : "全部"  %></TD>
				<TD height=17 align="left"><%=  pi.getInputUserName()==null?"上级系统管理员":pi.getInputUserName()  %></TD>
				<TD height=17 align="center"><%=  DataFormat.getDateString(pi.getInput()) %></TD>
				<TD height=17 align="center"><input type="button" name="Submit8223" value="更改密码" class="button1" onClick="Javascript:window.open('V855.jsp?UserID=<%=pi.getId()%>','_blank','height=180,width=330');">
	</TD>
			</TR>
	<%
				}
			}
	%>		
			</TABLE>
			<br>
			<table width=100% border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="376">
	            <div align="right"></div>
	          </td>
	          <td width="305">
	            <div align="right"></div>
	          </td>
	          <td >
	            <div align="right" width="500">
				<!--img name="Query" src="/webob/graphics/button_xinzeng.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:add();"-->
				<input type="button" name="Query" value=" 新 增 " class="button1" onClick="javascript:add();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
	          </td>
	        </tr>
	      </table>
	     </td>
     </tr>
    </table>
</form>
<%
}
catch (Exception e)
{
	OBHtml.showExceptionMessage(out,sessionMng, (IException) e, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
 %>
 <script language="JavaScript">
function add()
{
	form1.action = "<%= strContext %>/system/C853.jsp?method=toAdd";
	form1.submit();
}
function view(UserID)
{
	
	form1.action = "<%= strContext %>/system/C853.jsp?method=toModify&UserID="+UserID;
	form1.submit();
}
function changepassword(UserID)
{
	form1.action = "<%= strContext %>/system/C853.jsp?method=toModify&UserID="+UserID;
	form1.submit();
}

</script>

<%@ include file="/common/SignValidate.inc" %>