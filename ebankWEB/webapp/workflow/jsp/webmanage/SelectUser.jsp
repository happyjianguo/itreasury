<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">

<script language="javascript">

function sel()
{
	//window.opener.document.forms[0].elements["righttransferEntity.srcname"].value='1'; //在子窗口设置父窗口表单元素的值. 
	//格式："登录名,真实姓名"
	 var  aa  =  document.getElementsByName("radioVal");  
	 var str=new String(",");
  	 for  (var  i=0;  i<aa.length;  i++)  
  	 {  
   	      if(aa[i].checked)
   	      {
   	      	var index= aa[i].value.indexOf(",");
   	      	if(index!=-1)
   	      	{
   	      		var id = aa[i].value.substring(0,index);
   	      		var name= aa[i].value.substring(index+1,aa[i].value.length);
   	      		//alert("id"+id);
   	      		//alert("name"+name);
   	      		var user=document.forms[0].flag.value;
   	      		if (user=="src")
	   	      	{
	   	      		
	   	      		window.opener.document.forms[0].elements["righttransferEntity.srcid"].value=id;
	   	      		window.opener.document.forms[0].elements["righttransferEntity.srcname"].value=name;
  	      		}
   	      		if (user=="dest")
   	      		{
   	      			
   	      			window.opener.document.forms[0].elements["righttransferEntity.destid"].value=id;
   	      			window.opener.document.forms[0].elements["righttransferEntity.destname"].value=name;
   	      		}
   	      		
   	      	}
   	      }
   	 }  
	window.close() //关闭子窗口.
}
</script>
<head>
<title><bean:message key="label.webmanage.selectUser.head"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
<style type="text/css">
</style>
</head>
<html:form action="/transRight">
			<input name="flag" type="hidden" value=<%=(String)request.getAttribute("flag")%>>
			<input name="operate" type="hidden" value="selectAllUserLNList"/>
			<input name="user" type="hidden" value="<%=(String)request.getAttribute("flag")%>"/>
			<table width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist" id="DataTable">
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.selectUser.radio"/></td>
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.selectUser.user"/></td>
					</tr>
				<logic:iterate id = "result" name = "pageResult" property="list" indexId="ind">
				<tr>
					<td align="center" bgcolor="#F8F8F8" height="25">
					<input type="radio" name="radioVal" value="<bean:write name="result" property="id"/>,<bean:write name="result" property="cnName"/>" >
					</td>
					<td align="center" bgcolor="#F8F8F8" height="25"><bean:write name="result" property="cnName"/></td>
				<tr>
				</logic:iterate>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
					  <tr>
					  	<td>
					  	  <jsp:include page="/workflow/pageloader/pageMagnifier.jsp"/>
					  	</td>
					  </tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
				<tr>
					<td align="center"><input type="button" class="button1" name="selUrser" value="<bean:message key="label.webmanage.common.ok"/>" onclick="sel()"></td>
				</tr>
			</table>
</html:form>
					