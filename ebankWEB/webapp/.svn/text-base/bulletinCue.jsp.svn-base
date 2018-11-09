<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@page import="java.util.*,
				com.iss.itreasury.system.bulletin.dataentity.*,
				com.iss.itreasury.system.bulletin.bizlogic.*,
				com.iss.itreasury.util.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<html>
<head>
<style>
body { background: url("/itreasury/graphics/mainbank.jpg") no-repeat; } 

TABLE { 
	border-top: 1px dashed blue;	
	border-left: 1px dashed blue; 	
	border-right: 1px dashed blue;	
	border-bottom: 1px dashed blue; 
	filter:alpha(opacity=70);
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>公告信息</title>
</head>
<body class="body"> 
<%
long id = -1;
String clients = "";//公告客户
String header = "";//公告标题
String content = "";//公告内容
long statusID = -1;//公告状态
String statusName="";//公告状态
String releaseDate ="";//发布日期
long officeId=-1;
long userID = -1;//发布人
String userName="";//发布人姓名
//为了查询而设的属性
String fromReleaseDate ="";//发布日期由
String endReleaseDate ="";//发布日期到
long modifyId=3;//查询标识
Collection collection=null;
BulletinInfo bulletinInfo=new BulletinInfo();
bulletinInfo.setModifyId(modifyId);
clients=""+sessionMng.m_lClientID;
bulletinInfo.setClients(clients);
Log.print("-------------ClientCode:"+sessionMng.m_strClientCode);
BulletinBiz bulletinBiz=new BulletinBiz();
collection=bulletinBiz.findByCondition(bulletinInfo);
if(collection!=null && collection.size()>0){
	//out.print("有值");
	Iterator iterator = collection.iterator();


 %>
<br>
 <table id="tbl_gg" width="600" border="0" height="100" style="" class="shw-table-submenu"  align="center" cellpadding="5">

 <TR><TD  colSpan=2 height=21 align="center" height="21" bgcolor="#D6EAF3"><B><B>公告信息栏</B></B></TD></TR>
              <tr class="tableHeader"> 
                <td height="18" valign="bottom"  width="30%" class="ItemTitle">
                  <p align="center">公告名称</p>
                </td>

                <td valign="bottom" height="18"  width="70%" class="ItemTitle"> 
                  <p align="center">公告内容</p>
                </td>
              </tr>
<%

    while ( iterator.hasNext())
    {
 	   bulletinInfo=(BulletinInfo)iterator.next();
	    	header=bulletinInfo.getHeader();
	    	content=bulletinInfo.getContent();
	    	clients=bulletinInfo.getClients();
	    	officeId=bulletinInfo.getOfficeId();
	    	if(!clients.equals("part")){
%>
<tr>
      <td  valign="top" align="left" height="18" class="ItemBody">&nbsp;<%=header%></td>
      <td  valign="top" align="left" height="18" class="ItemBody">&nbsp;<%=content%></td>
</tr>
<%	    	
	    	}
	    	else{
	    		if(officeId==sessionMng.m_lOfficeID){%>
				<tr>
	    			 <td  valign="top" align="left" height="18" class="ItemBody">&nbsp;<%=header%></td>
      				 <td  valign="top" align="left" height="18" class="ItemBody">&nbsp;<%=content%></td>
	    		</tr>
	    		<% }
	    	}
    }


%>

  </table>
<%} %>
  <%--<div align='center'><input type = 'button' onClick = 'window.close()' value='关闭'></div>--%>
</body>
<script language='javascript'>

<%
//modify by wjliu --begin 当没有提醒信息时窗口自动关闭
if(collection==null)
{
%>

	 window.close();
<%
}
%>
 </script>
</html>