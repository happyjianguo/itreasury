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
	err_info = "����:" + err_name + "\n������Ϣ:" + err_message;
	alert("����:" + err_name + "\n������Ϣ:" + err_message);
}
function signature(form1)
{
	try{
		var bInit = false;	
	    //ָ��license�ļ�����ʼ��license	
	    bInit = CryptoAgency1.CFCA_Initialize("CFCALicense.dll");
		
	    subjectFilter = "";								
		//if(document.all("subject").value.length>0){
			//ͨ���������
		//	subjectFilter = document.all("subject").value;
		//}
		
		//ѡ��ǩ��֤�飬ʹ��֤���������
		//false����ʾѡ��һ��֤�飬���Ҫһ��ѡ�����֤����
		//��CryptoAgency1.CFCA_SelectSignCerts(subjectFilter,true);
		selectFlag = CryptoAgency1.CFCA_SelectSignCerts(subjectFilter,false);
		
		//ѡ��֤��ɹ�
		if(selectFlag){
			//���ѡ���ǩ��֤������
			var selectCount = CryptoAgency1.CFCA_GetUseCertsCount(true);
			//��ø�ѡ���ǩ��֤������ݣ�������0��ʼ
			var cert = CryptoAgency1.CFCA_GetCertContent(true,0);
			//���֤����Ϣ
			form1.signature.value = cert;
			form1.certdn.value = CryptoAgency1.CFCA_GetCertDN(cert);
			form1.certsn.value = CryptoAgency1.CFCA_GetCertSN(cert);
			//ʹ�ú����֤��
			CryptoAgency1.CFCA_ClearUseCerts(true);
		}
		//ѡ��֤��ʧ��
		else{
			//ʹ�ú����֤��
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

