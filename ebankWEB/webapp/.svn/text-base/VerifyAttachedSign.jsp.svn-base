<%@ page import="com.infosec.NetSignServer"%>
<%  

	 String signature = "";
	 String certdn = "";
	 String strContext = request.getContextPath();
	 try
	 {
	    com.infosec.NetSignServer tmp = new com.infosec.NetSignServer();
	        
	   	signature =request.getParameter("signature");
	   	certdn =request.getParameter("certdn");

//	   	验证数字签名   	
	   	byte[] s64code = signature.getBytes("GBK");
 	   			
		tmp.NSAttachedVerify(s64code);
	
	 	int ret=tmp.getLastErrnum();
		
		if (ret== 0)
		{
			System.out.println(signature);
			System.out.println("sign : " + tmp.NSGetPlainText());
			System.out.println("certdn : " + certdn);
			request.setAttribute("signature",signature);
			request.setAttribute("certdn",certdn);
/*			
			RequestDispatcher rd = request.getRequestDispatcher(strContext + "/Login.jsp" );
			rd.forward( request,response );
*/
%>
<form  name="form1" method="post" action="<%=strContext%>/Login.jsp">
<input type="hidden" name="certdn" value="<%=  certdn %>" >
<input type="hidden" name="signature"  value="<%= signature  %>">
<SCRIPT LANGUAGE=javascript>
	form1.submit();
</SCRIPT>
</form>
<%			
 
		}
		else
		{
			out.println("<h3><font color=red>Server端Attached方式验签失败！</font></h3><p>");
			out.println("错误代码是"+ret);
	
		}
	}
	catch(Exception e)
	{
		System.out.println(e.toString());
	}	
%>
