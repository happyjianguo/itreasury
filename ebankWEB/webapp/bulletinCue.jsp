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
<title>������Ϣ</title>
</head>
<body class="body"> 
<%
long id = -1;
String clients = "";//����ͻ�
String header = "";//�������
String content = "";//��������
long statusID = -1;//����״̬
String statusName="";//����״̬
String releaseDate ="";//��������
long officeId=-1;
long userID = -1;//������
String userName="";//����������
//Ϊ�˲�ѯ���������
String fromReleaseDate ="";//����������
String endReleaseDate ="";//�������ڵ�
long modifyId=3;//��ѯ��ʶ
Collection collection=null;
BulletinInfo bulletinInfo=new BulletinInfo();
bulletinInfo.setModifyId(modifyId);
clients=""+sessionMng.m_lClientID;
bulletinInfo.setClients(clients);
Log.print("-------------ClientCode:"+sessionMng.m_strClientCode);
BulletinBiz bulletinBiz=new BulletinBiz();
collection=bulletinBiz.findByCondition(bulletinInfo);
if(collection!=null && collection.size()>0){
	//out.print("��ֵ");
	Iterator iterator = collection.iterator();


 %>
<br>
 <table id="tbl_gg" width="600" border="0" height="100" style="" class="shw-table-submenu"  align="center" cellpadding="5">

 <TR><TD  colSpan=2 height=21 align="center" height="21" bgcolor="#D6EAF3"><B><B>������Ϣ��</B></B></TD></TR>
              <tr class="tableHeader"> 
                <td height="18" valign="bottom"  width="30%" class="ItemTitle">
                  <p align="center">��������</p>
                </td>

                <td valign="bottom" height="18"  width="70%" class="ItemTitle"> 
                  <p align="center">��������</p>
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
  <%--<div align='center'><input type = 'button' onClick = 'window.close()' value='�ر�'></div>--%>
</body>
<script language='javascript'>

<%
//modify by wjliu --begin ��û��������Ϣʱ�����Զ��ر�
if(collection==null)
{
%>

	 window.close();
<%
}
%>
 </script>
</html>