<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>
			<logic:equal name="FLAG" value="ADD">
				<bean:message key="label.webmanage.elementList.head.addElement" />
			</logic:equal> 
			<logic:equal name="FLAG" value="EDIT">
				<bean:message key="label.webmanage.elementList.head.editElement" />
			</logic:equal>
		</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">

		<script language="javascript">
										
			   function String.prototype.Trim() {
			   		return this.replace(/(^\s*)|(\s*$)/g,"");
			   }
				
			   function myclose(){	
					var obj1=document.all["txtEleEle"];					
					if(checkInput(obj1)){
						var obj2=document.all["txtEleName"];					
						if(checkEmpty(obj2)){
			 				top.window.close();	 		
		 					window.returnValue=setValue();
				 		}
			 		}
					alert('<bean:message key="label.webmanage.common.checkInput" />');	
		 		}	
		 		
		 		function setValue(){	 		
		 			
		 			if(document.all["FLAG"].value=="ADD"){
			 			paramter="operate=addElement";
		 			}
		 			else{
			 			paramter="operate=editElement";
			 			paramter=paramter+"&"+"selectedElementID="+document.all["selectedElementID"].value;
		 			}
		 			paramter=paramter+"&"+"selectedElementName="+document.all["txtEleName"].value;
		 			paramter=paramter+"&"+"selectedElementElement="+document.all["txtEleEle"].value;
		 			return paramter;
		 		}
	 								
				function checkInput(obj){
					if(!checkEmpty(obj))
						return false;
					var str;				
					if(obj.value.indexOf(".",[0])>=0)
						str=dropdot(obj.value);
	              	var reg = /^\w+$/;
					if(reg.test(str))
				  		return true;			
				 	obj.focus();  	
				 	return false;
				}
				
				function dropdot(objvalue){
				
					var i=objvalue.indexOf(".",[0]);
					var str1=objvalue.substring(0,i-1);
					var str2=objvalue.substring(i+1,objvalue.length);	
					var str=str1+str2;
					if(str.indexOf(".",[0])>=0)
						str=dropdot(str);
					return str;			
				}
				
				function checkEmpty(obj){
					if(obj.value.Trim()!="")
				 		return true;
				 	return false;
				}

				
		</script> 
	</head>

	<body>
		<input type="hidden" id="selectedElementID" value="<bean:write name='selectedElementID' />">
		<input type="hidden" id="FLAG" value="<bean:write name='FLAG'/>">
		<table align="center" width="100%" border="0" cellspacing="1" class="tblist">

			<tr>
				<td align="center">
					<bean:message key='label.webmanage.elementList.opt.elementElement' />
				</td>
				<td align="center">
					<input type="text" name="txtEleEle" value="<bean:write name='selectedElementElement'/>"><FONT color="red">*</FONT>
				</td>
			</tr>

			<tr>
				<td align="center">
					<bean:message key='label.webmanage.elementList.opt.elementName' />
				</td>
				<td align="center">
					<input type="text" name="txtEleName" value="<bean:write name='selectedElementName'/>"><FONT color="red">*</FONT>
				</td>
			</tr>

			<tr>
				<td align="center">
				</td>
				<td align="center">
					<input type="button" class="button1" value="<bean:message key='label.webmanage.common.ok' />" onClick="myclose();">
					<input type="button" class="button1" value="<bean:message key='label.webmanage.common.close' />" onclick="javascript:window.close();">
				</td>
			</tr>
		</table>
	</body>
</html>
