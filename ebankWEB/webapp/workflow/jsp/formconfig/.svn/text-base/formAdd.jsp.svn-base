<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message key="label.webmanage.elementList.head.addForm" /></title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/workflow/css/css.css">
		<script language="javascript"> 
													
			    function String.prototype.Trim() {
			   	    return this.replace(/(^\s*)|(\s*$)/g,"");
			    }
			   
				function myclose(){	
					var obj1=document.all["txtFormName"];					
					if(checkEmpty(obj1)){
						var obj2=document.all["txtFormDesc"];					
						if(checkEmpty(obj2)){
							top.window.close();	
				 			window.returnValue=setValue();
				 			}
			 			}
					alert('<bean:message key="label.webmanage.common.checkInput" />');	
				}
			
				function setValue(){
				
			 		paramter="operate=addForm";
			 		paramter=paramter+"&"+"selectedName="+document.all["txtFormName"].value;
			 		paramter=paramter+"&"+"selectedDescription="+document.all["txtFormDesc"].value;		 		
			 		return paramter;
			 	}		 	
		 							
				function checkInput(obj){					
	              	//var reg = /^\w+$/;
					//if(reg.test(obj.value)){
				  	//	return true;
				 	//   }				
				 	//obj.focus();  	
				 	//return false;
				}
				
				function checkEmpty(obj){
					if(obj.value.Trim()!="")
				 		return true;
				 	return false;
				}
		</script>
	</head>

	<body>
		<table align="center" width="100%" border="0" cellspacing="1" class="tblist">
			<tr>
				<td align="center">
					<bean:message key='label.webmanage.formList.opt.formName' />
				</td>
				<td align="center">
					<input type="text" name="txtFormName"><FONT color="red">*</FONT>
				</td>
			</tr>
			<tr>
				<td align="center">
					<bean:message key='label.webmanage.formList.opt.formDesc' />
				</td>
				<td align="center">
					<input type="text" name="txtFormDesc"><FONT color="red">*</FONT>
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
