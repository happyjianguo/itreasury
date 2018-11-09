<%@ page contentType="text/html; charset=GBK" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%String ROOT_PATH = request.getContextPath();%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">

<html>
	<head>
	<title><bean:message key="label.webmanage.transferadd.title"/></title>
	<script language="javascript">
	//modify by kevin(刘连凯) date 2012-07-03
	//跳转到该页面后由于以下代码，导致单击其它菜单都变成了window.open()打开页面
	//modify start
	//window.name="addwindow";
	//modify end
	function add()
	{
		if(document.forms[0].elements["righttransferEntity.srcname"].value=="")
		{
			alert('<bean:message key="label.webmanage.transferadd.alertsid"/>');
			return false;
		}
		else if(document.forms[0].elements["righttransferEntity.destname"].value=="")
		{
			alert('<bean:message key="label.webmanage.transferadd.alertdid"/>');
			return false;
		}
		else if(document.forms[0].elements["righttransferEntity.strbegindate"].value=="")
		{
			alert('<bean:message key="label.webmanage.transferadd.alertstartdate"/>');
			return false;
		}
		else if(document.forms[0].elements["righttransferEntity.strenddate"].value=="")
		{
			alert('<bean:message key="label.webmanage.transferadd.alertenddate"/>');
			return false;
		}
		if(!check())
			{
				alert('<bean:message key="label.webmanage.currentstepList.alerttime"/>');
				return false;
			}
		document.forms[0].operate.value="addTransForm";
		document.forms[0].submit();
	}
	function check()
		{
			var startdate=document.forms[0].elements['righttransferEntity.strbegindate'].value;
			var enddate=document.forms[0].elements['righttransferEntity.strenddate'].value;
			if(checkEmpty(startdate) && checkEmpty(enddate)){
				if(!checkDate(startdate,enddate))
					return false;
			}			
			return true;
		}
		function checkEmpty(values){
			if(values=="")				
				return false
			return true;			
		}
		function checkDate(startDate,endDate){			
			if(getNum(startDate)<=getNum(endDate))
				return true;
			return false;
		}	
		function getNum(str)
		{			
			var temps=new Array();
			temps=str.split("-");
			var returnVal="";
			for(i=0;i<temps.length;i++){
				returnVal=returnVal+temps[i];
			}
			return returnVal;
		}
	</script>
	
	
	</head>
	<body bgcolor="#e9f4fc" leftmargin="0" topmargin="0" style=" border-width:0px;">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid rgb(173,195,239);">
			<tr>
				<td>
					<jsp:include page="/workflow/common/wf_header.jsp"/>
				</td>
			</tr>
		</table>
		
		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<%-- 去掉左边链接菜单，用网自定义菜单
			<td>					
					<jsp:include page="/workflow/common/wf_menu.jsp"/>					
				</td>			
			--%>
				<td valign="top">
					<table width="100%" border="0" cellspacing="3" cellpadding="2">
						<tr>
							<td colspan="22" height="22" bgcolor="#C4DAFA" style="font-size:12px; font-weight:bold; border-bottom:1px solid #fff;">　
								<bean:message key="label.webmanage.transferadd.head"/>
							</td>
						</tr>
					</table>
					<html:form action="/transRight">
					<input name="operate" type="hidden" value="updateTransForm"/>
					
							
					<table width="100%" border="0" cellpadding="3" cellspacing="2" class="tblist" id="DataTable">
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transferadd.sid"/></td>
					  <td nowrap>
                    	<!-- html:text property="righttransferEntity.srcid" readonly="true"/-->
                    	<html:text property="righttransferEntity.srcname" readonly="true"/>
                    	<input name="righttransferEntity.srcid" type="hidden" value="righttransferEntity.srcid"/>
                    	
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/icon.gif" border="0" align="absmiddle" onClick="newwin('/transRight.do?operate=selectAllUserLNList&user=src','userwindow')"></a>
                      </td>
                    </tr>
                    <tr class="thead">
                      <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transferadd.did"/></td>
					  <td nowrap>
                    	<!-- html:text property="righttransferEntity.destid" readonly="true"/-->
                    	<html:text property="righttransferEntity.destname" readonly="true"/>   
                    	<input name="righttransferEntity.destid" type="hidden" value="righttransferEntity.destid"/>                 	
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/icon.gif" border="0" align="absmiddle" onClick="newwin('/transRight.do?operate=selectAllUserLNList&user=dest','userwindow')"></a>
                    	
                      </td>
					</tr> 
					
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transferadd.startdate"/></td>
					  <td nowrap>
                    	<html:text property="righttransferEntity.strbegindate" readonly="true"/>
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['righttransferEntity.strbegindate'],false)"></a>
                      </td>
                    </tr>
                    <tr class="thead">  
                    
                      <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transferadd.enddate"/></td>
                        <td nowrap>
                    	<html:text property="righttransferEntity.strenddate" readonly="true"/>
                    	<a href="#"><img src="<%=request.getContextPath()%>/workflow/images/calendar.gif" border="0" align="absmiddle" onclick="Container_onclick(document.forms[0].elements['righttransferEntity.strenddate'],false)"></a>
                      </td>
                      
					</tr>
					<tr class="thead">
					  <td align="center" bgcolor="#ECE9D8"><bean:message key="label.webmanage.transferadd.status"/></td>
					  <td nowrap>
					  	<html:select property="righttransferEntity.valid">
					  		<html:option  value="1"><bean:message key="label.webmanage.transferadd.statusyes"/></html:option>
					  		<html:option  value="0"><bean:message key="label.webmanage.transferadd.statusno"/></html:option>
					  	</html:select>
                    	
                      </td>
					</tr>  
					 
					
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
					<tr>
					<td align="center">
					<input type="button" class="button1" name="addTransfer" value="<bean:message key="label.webmanage.common.save"/>" onclick="add()">
					<input type="button" class="button1" name="back" value="<bean:message key="label.webmanage.common.back"/>" onclick="javascript:history.go(-1);">
					</td>
					<td align="center">&nbsp;</td>
					</tr>
					</table>
					
					</html:form>
				</td>
			</tr>
		</table>
	<script language="JavaScript" src="<%=request.getContextPath()%>/workflow/js/date/common.js"></script>
	<script language="javascript">
	function newwin(url,windowname)
	{
		var lx = document.body.scrollLeft + event.clientX;	//鼠标当前x坐标
		var ly = document.body.scrollTop + event.clientY;	//鼠标当前y坐标
		parawin=window.open( '<%=ROOT_PATH%>'+url,'dd','width=380,height=450,left='+lx+',top='+ly+',alwaysRaised=yes,depended=yes,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
	}
	</script>
	</body>
</html>