<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=GBK"%>
<!-- miodify by kevin(������) 2012-07-03
<!-- ͷ�ļ����и�Session���жϣ���Session�ж���������ϵͳ����Ч�������ɵ���˾�к�ͳ������⣬���˾���ĵ�Session��һ�£��ָ�ҳ��Ĵ���ע�͵���-->
<!--< %String strContext = request.getContextPath();
			response.setHeader("Pragma", "No-cache");//HTTP 1.1 
			response.setHeader("Cache-Control", "no-cache");//HTTP 1.0 
			response.setHeader("Expires", "0");//��ֹ��proxy 
			//�ж�session�Ƿ�ʧЧ
			if(request.getSession()==null||request.getSession().getAttribute("login_Name")==null){
				response.sendRedirect(strContext + "/login.jsp");
				return ;
			}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>���������� 2.0</title>
		<link rel="stylesheet" type="text/css" href="< %=strContext%>/workflow/css/css.css">
		<style type="text/css">
<!--
.collapsedFolder {	font-family:"����";
	font-size: 10px;
	background-image: url(<  %=strContext%>/workflow/images/plusTop.gif);
	background-repeat: no-repeat;
	background-position: left;
	padding-left: 10px;
	cursor: hand;
}
.folder {	font-family: "����";
	background-image: url(<  %=strContext%>/workflow/images/plusTop.gif);
	background-repeat: no-repeat;
	background-position: left;
	font-size: 12px;
	padding-left: 20px;
	cursor: hand;
}
.folder1 {	font-family: "����";
	background-image: url(< %=strContext%>/workflow/images/minusTop.gif);
	background-repeat: no-repeat;
	background-position: left;
	font-size: 12px;
	padding-left: 20px;
	cursor: hand;
}
.iefile {
	background-image: url(<  %=strContext%>/workflow/images/join.gif);
	background-repeat: no-repeat;
	background-position: left;	
	padding-left: 20px;
	font-family: "����";
	font-size: 12px;
	border:none;
}

.iefile1 {
	background-image: url(< %=strContext%>/workflow/images/join1.gif);
	background-repeat: no-repeat;
	background-position: left;	
	padding-left: 20px;
	font-family: "����";
	font-size: 12px;
	border:none;
}
.submenu {	padding-left: 18px;
}
-->
<!--  </style>
		<script>// Example: obj = findObj("image1");
var ROOT_PATH = "< %=strContext%>";
function findObj(theObj, theDoc)
{
  var p, i, foundObj;
  
  if(!theDoc) theDoc = document;
  if( (p = theObj.indexOf("?")) > 0 && parent.frames.length)
  {
    theDoc = parent.frames[theObj.substring(p+1)].document;
    theObj = theObj.substring(0,p);
  }
  if(!(foundObj = theDoc[theObj]) && theDoc.all) foundObj = theDoc.all[theObj];
  for (i=0; !foundObj && i < theDoc.forms.length; i++) 
    foundObj = theDoc.forms[i][theObj];
  for(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++) 
    foundObj = findObj(theObj,theDoc.layers[i].document);
  if(!foundObj && document.getElementById) foundObj = document.getElementById(theObj);
  
  return foundObj;
}
</script>
	</head>
	<body bgcolor="#e9f4fc" leftmargin="0" topmargin="0" style=" border-width:0px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid rgb(173,195,239);">
			<tr>
				<td width="200" align="left">
					<img src="< %=strContext%>/workflow/images/w_logo.gif" width="201" height="65">
				</td>
				<td align="right" valign="bottom">
					<table border="0" cellpadding="2" cellspacing="4">
    			    <tr>
            			<td align="center" class="sys"><a href="< %=strContext%>/login.do?operate=logoff" class="syslink">�˳���¼</a></td>
        		    </tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="5" bgcolor="#FFFFFF">-</td>
			</tr>
		</table>-->