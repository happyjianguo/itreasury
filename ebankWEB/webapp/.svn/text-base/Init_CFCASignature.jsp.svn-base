<%@ page contentType="text/html;charset=gbk"%> 
<%  
	String strTmp = null;
	String strUserID = null;
	String strIssuer = null;
	String strLoginNo = null;
	strTmp = request.getParameter("loginno");
	if( strTmp != null && strTmp.length() > 0 )
		strLoginNo = strTmp;
%>
<SCRIPT language=JavaScript>
var selectFlag = "";
var subjectFilter = "";

function error_report(err_name,err_message){
	var err_info;
	err_info = "错误:" + err_name + "\n错误信息:" + err_message;
	alert("错误:" + err_name + "\n错误信息:" + err_message);
}
function signature(form1)
{
	try{
		var bInit = false;	
	    //指定license文件，初始化license	
	    bInit = CryptoAgency1.CFCA_Initialize("CFCALicense.dll");
		
	    subjectFilter = "";								
		//if(document.all("subject").value.length>0){
			//通过主题过滤
		//	subjectFilter = document.all("subject").value;
		//}
		
		//选择签名证书，使用证书主题过滤
		//false，表示选择一张证书，如果要一次选择多张证书用
		//用CryptoAgency1.CFCA_SelectSignCerts(subjectFilter,true);
		selectFlag = CryptoAgency1.CFCA_SelectSignCerts(subjectFilter,false);
		
		//选择证书成功
		if(selectFlag){
			//获得选择的签名证书数量
			var selectCount = CryptoAgency1.CFCA_GetUseCertsCount(true);
			//获得刚选择的签名证书的数据，索引从0开始
			var cert = CryptoAgency1.CFCA_GetCertContent(true,0);
			//获得证书信息
			form1.signature.value = cert;
			form1.certdn.value = CryptoAgency1.CFCA_GetCertDN(cert);
			form1.certsn.value = CryptoAgency1.CFCA_GetCertSN(cert);
			//使用后清除证书
			CryptoAgency1.CFCA_ClearUseCerts(true);
		}
		//选择证书失败
		else{
			//使用后清除证书
			CryptoAgency1.CFCA_ClearUseCerts(true);
		}
	}
	catch(e)
	{
		error_report(e.number,e.message);
		return;
	}
	if(selectFlag){
		form1.authentication.value="true";
		form1.submit();
	}
	return true;
}
</SCRIPT>
<HTML>
<BODY bgcolor="ffffff"> 
<OBJECT id=CryptoAgency1 style="VISIBILITY: hidden" codeBase="CFCACertKitAx.cab#version=2,2,8,8" classid=clsid:60931F6C-3ED3-4713-AEC6-6A59BF761B23 VIEWASTEXT>
</OBJECT>
<form  name="form1" method="post" action="Init.jsp">
<input type="hidden" name="signature" >
<input type="hidden" name="strUserID" value="<%=strUserID%>" >
<input type="hidden" name="certdn">
<input type="hidden" name="certsn">
<input type="hidden" name="strIssuer" value="<%=strIssuer%>">
<input type="hidden" name="authentication" value="false" >
<input type="hidden" name="loginno" value="<%=strLoginNo%>">

<SCRIPT LANGUAGE=javascript>
	signature(form1);
</SCRIPT>

</form>
</BODY>
</HTML>

