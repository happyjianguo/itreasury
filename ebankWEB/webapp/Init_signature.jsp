<%@ page contentType="text/html;charset=gbk"%> 
<%String strContext = request.getContextPath();%>
<%  
	String strURL = "";
	String strTmp = null;
	String strSignature = null;
	String strUserID = null;
	String strIssuer = null;
	String strLoginNo = null;
	boolean bAuthentication = false;
	//
	strTmp = request.getParameter("loginno");
	if( strTmp != null && strTmp.length() > 0 )
		strLoginNo = strTmp;
	strTmp = request.getParameter("signature");
	if( strTmp != null && strTmp.length() > 0 )
		strSignature = strTmp;
	strTmp = request.getParameter("authentication");
	if( strTmp != null && strTmp.length() > 0 )
	{
		if( strTmp.equals("true") )
			bAuthentication = true;
		else
			bAuthentication = false;
	}

	try 	
	{  		
		if( strSignature != null && bAuthentication == true )
 		{ 			
				/*
				// 验证数字签名 				
				try 
				{
 					com.infosec.NetSign4UNIX tmp = new com.infosec.NetSign4UNIX();
 				  	int ret = tmp.NetSignSignVerify(strSignature);
 				  	if (ret < 0)
 				  	{ 				  		
						out.println("<h2>签名验证失败！</h2>");
 						if (ret == -17)
 				  			out.println("证书在CRL中!");
 						if (ret == -16)
 				  			out.println("Server未找到相应CA的CRL!");
 				  		out.println("ret = " + ret); 
						out.flush();
 						return;
 				  	}
 					else
 					{
						strUserID = tmp.GetUsrID();
						strIssuer = tmp.GetCertIssuer();
 						System.out.println(" Signature GetUsrID : " +  tmp.GetCertIssuer());
 						System.out.println(" Signature GetCertIssuer : " +  tmp.GetUsrID());
 						System.out.println(" Signature GetCertIssuer : " +  tmp.GetSignature());
 					}
	 			} 
				catch(Exception e)
 				{ 
					out.println("签名验证失败！==");
 					out.flush();
 					return;
 				}
				*/
 
 		}
 		else
 		{ 
//			out.println("invalid user. please login again");
 		} 	
}
 	catch (Exception e) 	
{ 		System.out.println("Init session failed. " + e.toString()); 	}


	
%>
<SCRIPT language=JavaScript>
function signature(form1)
{
	InfoSecNetSign1.NSSetPlainText("please sign.");

	if(InfoSecNetSign1.errorNum!=0)
	{
		alert("返回值〔"+InfoSecNetSign1.errorNum+"〕\n"+InfoSecNetSign1.errMsg);
		return false;
	}
	else	
	{	
		form1.signature.value =InfoSecNetSign1.NSAttachedSign("");
		//
		form1.certdn.value = InfoSecNetSign1.NSGetSignerCertInfo(1);
		if(InfoSecNetSign1.errorNum!=0)
		{
			alert("返回值〔"+InfoSecNetSign1.errorNum+"〕\n"+InfoSecNetSign1.errMsg);
			form1.authentication.value="false";
			return false;
		}
		else
		{
			if(InfoSecNetSign1.errorNum!=0)
			{
				alert("返回值〔"+InfoSecNetSign1.errorNum+"〕\n"+InfoSecNetSign1.errMsg);
				form1.authentication.value="false";
				return false;
			}
			form1.authentication.value="true";
			form1.submit();
			return true;
		}

	}  

/*
	for(c=0; c < form1.elements.length; c++)
	{
	    if( form1.elements[c].value != null && form1.elements[c].value.length>0 )
			InfoSecNetSign1.addFormItem(form1.elements[c].value);
	}
	InfoSecNetSign1.makeAttachedSign();
	form1.signature.value = InfoSecNetSign1.attachedSign;
	form1.certdn.value = InfoSecNetSign1.DetachedSign;
	//form1.certdn.value = InfoSecNetSign1.plainTextForDetachedSign;
	//form1.certdn.value = InfoSecNetSign1.signedMsg;
	if (form1.signature.value)
	{
		form1.authentication.value="true";
		form1.submit();
		return true;
	}
	if (!form1.signature.value)
	{
		alert("签名验证没有通过！");
		form1.authentication.value="false";
		return false;
	}
	return true;
form1.submit();
*/
}
</SCRIPT>
<HTML>
<BODY bgcolor="ffffff"> 
<OBJECT id=InfoSecNetSign1 codeBase=NetSign.dll#version=1,8,23,8 data=DATA:application/x-oleobject;BASE64,xDi5YpBBN0+M8KkrCpHMdwADAACgRwAA/wIAAA== classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 VIEWASTEXT width="693" height="29"></OBJECT> 

<!--
     <object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
codebase=/netsign.dll data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="33" height="32">
        <embed width="33" height="32" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==">
        </embed> 
      </object> 
-->	  
<form  name="form1" method="post" action="<%=strContext%>/Init.jsp">
<input type="hidden" name="signature" >
<input type="hidden" name="strUserID" value="<%=strUserID%>" >
<input type="hidden" name="certdn">
<input type="hidden" name="strIssuer" value="<%=strIssuer%>">
<input type="hidden" name="authentication" value="false" >
<input type="hidden" name="loginno" value="<%=strLoginNo%>">

<SCRIPT LANGUAGE=javascript>
	signature(form1);
</SCRIPT>

</form>
</BODY>
</HTML>

