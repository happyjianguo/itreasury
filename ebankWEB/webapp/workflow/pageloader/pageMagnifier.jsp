<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.iss.inut.base.PageResult"%>
<%
	PageResult pageResult=(PageResult)request.getAttribute("pageResult");
	
	String operate="";
	
	if(request.getAttribute("operate")!=null && !request.getAttribute("operate").toString().equals("")){
		operate=request.getAttribute("operate").toString().trim();
	}
	
	String selectedDefineID="";
	
	if(request.getAttribute("selectedDefineID")!=null && !request.getAttribute("selectedDefineID").toString().equals("")){
		selectedDefineID=request.getAttribute("selectedDefineID").toString().trim();
	}
%>
<script language="javascript">
	/**
	 *分页栏
	 */
	 function checkNumber(no,min,max)
	 {
	 	var tmp=/\d{1,20}/;
	 	if(no.match(tmp)==null)
	 		return false;
	 	if(no.match(tmp)!=null)
	 	{	
	 		if(no<=min)
	 			return false;
	 		if(no>=max)
	 			return false;
	 		if(no>min && no<max)
	 			return true;
	 	}
	 }
	 
	 function locatePage(action){
	        	
        	var iPageNo=<%=pageResult.getPageNo()%>
        	
        	if(action==1){
      			iPageNo=1;
      		}
      		else if(action==2){
      			if(iPageNo!=1)
      			iPageNo=parseInt(iPageNo)-1;
      			else
      			{
      				alert("已经是第一页！");
      				return;
      			}
      		}
      		else if(action==3){
      			if(iPageNo!=<%=pageResult.getPageTotal()%>)
      				iPageNo=parseInt(iPageNo)+1;
      			else
      			{	
      				alert("已经是最后一页！");
      				return;
      			}
      		}
      		else if(action==4){
      			iPageNo=<%=pageResult.getPageTotal()%>;
      		}
      		document.forms[0].elements('pageNo').value=iPageNo;
      		setParamter();
            document.forms[0].submit();
		}
		
	   function jump(){
		   if(!checkNumber(document.forms[0].elements('pageNo').value,0,1000)){
			  	alert("请输入数字！");
			  	return;
		   }
		   setParamter();
	       document.forms[0].submit();
	   }
	   
	   function changesize(){
	  
		    if(!checkNumber(document.forms[0].elements('pageSize').value,0,1000)){
			  	alert("请输入数字！");
			  	return;
			}
			setParamter();
	        document.forms[0].submit();	   		
	   }
	   
	   function setParamter(){
	   	  <% if(!operate.equals("")) {%>
	 	  		 document.all["operate"].value="<%=operate%>";
	 	  <% }%>
	 	  <% if(!selectedDefineID.equals("")) {%>
	 	  		document.all["selectedDefineID"].value="<%=selectedDefineID%>";
	 	  <% }%>	   	
	   }

</script>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="a">
	       <tr> 
	        <td colspan="2" align="center" class="more-bg">共<font color="#993333"><%=pageResult.getPageTotal()%></font>页<font color="#993333"><%=pageResult.getRecTotal()%></font>条&nbsp;<a onclick="javascript:locatePage(1)" style="cursor:hand">第一页</a>&nbsp;<a onclick="locatePage(2)" style="cursor:hand">上页</a>&nbsp;<a onclick="locatePage(3)" style="cursor:hand">下页</a>&nbsp;<a onclick="locatePage(4)" style="cursor:hand">末页</a>&nbsp;到第 
	          <input id="pageNo" name="pageResult.pageNo" type="text" value="<%=pageResult.getPageNo()%>" size="5"/>
	          页
				<input name="Submit" type="button" value="跳转" onClick="jump()">  
	          </td>
	      </tr>
	   </table>
