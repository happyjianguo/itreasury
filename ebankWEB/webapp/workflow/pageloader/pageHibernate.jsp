<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.iss.inut.base.PageResult"%>
<%
	PageResult pageResult=(PageResult)request.getAttribute("pageResult");
	
	String operate="";
	
	if(request.getParameter("operate")!=null && !request.getParameter("operate").equals("")){
		operate=request.getParameter("operate").trim();
	}
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
	 *��ҳ��
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
      				alert("�Ѿ��ǵ�һҳ��");
      				return;
      			}
      		}
      		else if(action==3){
      			if(iPageNo!=<%=pageResult.getPageTotal()%>)
      				iPageNo=parseInt(iPageNo)+1;
      			else
      			{	
      				alert("�Ѿ������һҳ��");
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
			  	alert("���������֣�");
			  	return;
		   }
		   setParamter();
	       document.forms[0].submit();
	   }
	   
	   function changesize(){
	  
		    if(!checkNumber(document.forms[0].elements('pageSize').value,0,1000)){
			  	alert("���������֣�");
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
	        <td colspan="2" align="right" class="more-bg">ÿҳ��ʾ
	           <input id="pageSize" size="5" name="pageResult.pageSize" type="text"  value="<%=pageResult.getPageSize()%>"/>
	          ��
	          <input name="Submit7" type="button" class="button1" value="ȷ��" onClick="changesize()">&nbsp;&nbsp;��<font color="#993333"><%=pageResult.getPageTotal()%></font>ҳ<font color="#993333"><%=pageResult.getRecTotal()%></font>��&nbsp;&nbsp;<a onclick="javascript:locatePage(1)" style="cursor:hand">��һҳ</a>&nbsp;&nbsp;&nbsp;<a onclick="locatePage(2)" style="cursor:hand">��ҳ</a>&nbsp;&nbsp;&nbsp;<a onclick="locatePage(3)" style="cursor:hand">��ҳ</a>&nbsp;&nbsp;&nbsp;<a onclick="locatePage(4)" style="cursor:hand">ĩҳ</a>&nbsp;&nbsp;&nbsp;���� 
	          <input id="pageNo" name="pageResult.pageNo" type="text" value="<%=pageResult.getPageNo()%>" size="5"/>
	          ҳ
				<input name="Submit" type="button" class="button1" value="��ת" onClick="jump()">  
	          &nbsp;&nbsp;</td>
	      </tr>
	   </table>
