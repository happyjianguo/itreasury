<%--
/**
 * 程序名称：V851.jsp
 * 功能说明：系统管理-用户组管理
 * 作　　者：刘琰
 * 完成日期：2003年9月4日
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
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
response.setHeader("Cache-Control","no-stored");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />
<!--Header end-->
<%
//固定变量
String strTitle = "[用户组管理]";
String strContext = request.getContextPath();//http://xxxx/../cpob
try
{
	/**
	* isLogin start
	*/
	//登录检测
	/*if( sessionMng.isLogin() == false)
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
	}*/

	/**
	* isLogin end
	*/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    Collection c = (Collection) request.getAttribute("GroupsOfClient");
%><%if(request.getAttribute("deletee") != null && !request.getAttribute("deletee").equals("")){ %>
<script language="javascript">
alert("删除成功");
</script>
<%} %>
<%if(request.getAttribute("aadd") != null && !request.getAttribute("aadd").equals("")){ %>
<script language="javascript">
alert("保存成功");
</script>
<%} %>

<form method="post" name="form1">

			
			
			<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用户组设置</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE> 
   
<br/>
	
		 <table width=100% border="1" align="" cellpadding="0" cellspacing="0" class=normal > 
		<thead>
		<TR >
			<TD width=30% height=17  align="center">用户组名称</TD>
			<TD width=25% height=17  align="center">录入日期</TD>
			<TD width=10% height=17  align="center">删除</TD>
		</TR>
		</thead>
<%  
		if( c != null )
		{
			Iterator it = c.iterator();
			int i = 0;
			while( it.hasNext() )
			{
				i++;
				OB_GroupInfo gi = (OB_GroupInfo) it.next();
%>
		<TR align="center"  borderColor=#999999>
		
			<TD height=17 align="left"  > <a href="javascript:view('<%= gi.getId() %>');"><%= gi.getName() %></a></TD>
			<TD height=17 align="center"  ><%= DataFormat.getDateString(gi.getInputDate()) %></TD>
			<TD height=17 >
			<!--img src="/webob/graphics/button_shanchu.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:deleteGroup(<%=  gi.getId() %>);"-->
			<input type="button" name="Submitv00204" value=" 删 除 " class="button1" onClick="javascript:deleteGroup(<%=  gi.getId() %>);">
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
          <td width="285">
            <div align=""></div>
          </td>
          <td>
            <div align="right" width="400">
			<!--img name="Query" src="/webob/graphics/button_xinzeng.gif" width="46" height="18" border="0" style="cursor:hand" onClick="javascript:add();"-->
			<input type="button" name="Submitv00204" value=" 新 增 " class="button1" onClick="javascript:add();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
}
	OBHtml.showOBHomeEnd(out);
%>
<script language="JavaScript">
function add()
{
	form1.action = "<%= strContext %>/system/C851.jsp?method=toAdd";
	form1.submit();
}
function view(lGroupID)
{
	form1.action = "<%= strContext %>/system/C851.jsp?method=toModify&GroupID="+lGroupID;
	form1.submit();
}
function deleteGroup(GroupID)
{
	if (confirm("是否删除?")) 
	{
	  form1.action = "<%= strContext %>/system/C851.jsp?method=Delete&GroupID="+GroupID;
	  form1.submit();
	}
}

</script>

<%@ include file="/common/SignValidate.inc" %>