<%@ page contentType="text/html;charset=gbk"%> 
<%@page import="com.iss.itreasury.ebank.util.OBHtml"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="java.util.*"%>
<%@ page import="com.iss.itreasury.ebank.privilege.bizlogic.*"%>
<%@ page import="java.net.URLEncoder"%>
<%String strContext = request.getContextPath();%>
<%  
	String strURL = "";
	String strCertCN = null;
	String strSignature = null;
	String strCertDN = null;
	String strCertSN = null;
	String strLoginNo = null;
	boolean bAuthentication = false;
	UserBean bean = new UserBean();
	String strTmp = "";
	String strCertNo = null;
	
	try
	{ 
		strTmp = request.getParameter("signature");
		if( strTmp != null && strTmp.length() > 0 )
			strSignature = strTmp;
		strTmp = request.getParameter("loginno");
		if( strTmp != null && strTmp.length() > 0 )
			strLoginNo = strTmp;
		strTmp = request.getParameter("certdn");
		if( strTmp != null && strTmp.length() > 0 )
			strCertDN = strTmp;
		strTmp = request.getParameter("certsn");
		if( strTmp != null && strTmp.length() > 0 )
			strCertSN = strTmp;
		strTmp = request.getParameter("authentication");
		if( strTmp != null && strTmp.length() > 0 )
		{
			if( strTmp.equals("true") )
				bAuthentication = true;
			else
				bAuthentication = false;
		}
		
		System.out.println("signature is ...." + strSignature);
		System.out.println("certdn is ...." + strCertDN);
		System.out.println("authentication is ...." + strTmp);
		/*
		if(strCertDN == null || "".equals(strCertDN))
		{
			
		}
		*/
		//System.out.println(" Signature GetUsrID : " +  request.getParameter("strUserID"));
 		//System.out.println(" Signature GetCertIssuer : " +  request.getParameter("strIssure"));
		/*
		if( strSignature != null && bAuthentication == true )
 		{ 		
				
				// 验证数字签名 				
					
					System.out.println("abc-------");
					//	   	验证数字签名   	
					
					com.infosec.NetSignServer tmp = new com.infosec.NetSignServer();
					
					
					byte[] s64code = strSignature.getBytes("GBK");
					tmp.NSAttachedVerify(s64code);
					int ret=tmp.getLastErrnum(); 
					
				  	if (ret < 0)
 				  	{ 			
						System.out.println("ret is " + ret);	  		
						out.print("<h2>签名验证失败！</h2>");
 						if (ret == -17)
 				  			out.print("证书在CRL中!");
 						if (ret == -16)
 				  			out.print("Server未找到相应CA的CRL!");
 				  		out.print("ret = " + ret); 
						out.flush();
 						return;
 				  	}
 					else
 					{ 
						strCertNo = tmp.NSGetSignerCertInfo(1);
						System.out.println(" Signature NSGetPlainText : " +  tmp.NSGetPlainText() );
						System.out.println(" Signature 主题DN : " +  strCertNo);
 						System.out.println(" Signature 颁发者DN : " +  tmp.NSGetSignerCertInfo(2));
 						System.out.println(" Signature 证书有效期起始时间 : " +  tmp.NSGetSignerCertInfo(3));
 						System.out.println(" Signature 代表证书有效期终止时间 : " +  tmp.NSGetSignerCertInfo(4));
 						System.out.println(" Signature 证书序列号，格式：字符串 : " +  tmp.NSGetSignerCertInfo(5));
						
						if (bean.checkUserCertNo(strLoginNo,strCertNo) <0)
						{
							out.println("<h2>签名验证失败！</h2>");
							out.println("<h2>证书与登录用户不匹配</h2>");
							//out.println(strCertNo);
							out.flush();
	 						return;
						}
 					}
					
				
					//System.out.println(tmpissue+"\t"+strLoginNo);	//System.out.println(tmpissue+"\t"+bean.checkUserCertNo(strLoginNo,tmpissue)+"\t"+strLoginNo);
					
		}
 		else
 		{ 
			out.print("签名验证失败，请重新登录！");
			out.flush();
			return;
 		} 	
 		*/
 			
 		strCertNo = strCertDN;
		strCertCN = new UserBean().getCN(strCertDN);
		if (!sessionMng.checkUserCert(strCertCN,strCertSN))
		{
			OBHtml.showCommonMessage(out, sessionMng, "签名验证","/NASApp/iTreasury-ebank/Index.jsp",1,"OB_E101");
			sessionMng.logout();
			return;
		}
	}
	catch(Exception e)
	{
		OBHtml.showCommonMessage(out, sessionMng, "签名验证","/NASApp/iTreasury-ebank/Index.jsp",1,"OB_E100");
		sessionMng.logout();
		return;
	}
%>
<html>
<body>
<%-- modified by mzh_fu 2007/09/03
<form name="form1" action="<%=strContext%>/InitMenu.jsp" method="post">
--%>
<form name="form1" action="<%=strContext%>/PasswordValidate.jsp" method="post">
    <input name="isInit" type="hidden" value="pass">
	<input name="txtCertNo" type="hidden" value="<%=strCertNo%>">
</form>
</body>
</html>
<script language="Javascript">
	form1.submit();
</script>