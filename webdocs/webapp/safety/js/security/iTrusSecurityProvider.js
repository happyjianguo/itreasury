(function(){

	if(!window.security)
	{
		window.security = {};
	}
	
	window.security.Cert = null;
	
	window.security.isSign = function(){
		return true;
	}
	
	window.security.filter = function(obj)
	{
		var CertFilter = securityProvider.Filter; 
		CertFilter.Clear();
		CertFilter.Issuer = obj.issuerDN;
		CertFilter.SerialNumber = obj.CertSN;
		var Certs = securityProvider.MyCertificates;
		if(parseInt(Certs.Count) > 0){
			try{
				security.Cert = Certs(0);
			}catch(e){
				alert("���֤��ʱ��������"+ "\n��������key�Ƿ���ȷ����");
				security.Cert = null;
			}
		}		
		alert("û���ҵ���Ч��֤�飡");
		security.Cert = null;
	}
	
	window.security.sign = function(obj)
	{
		if(security.Cert == null)
		{
			alert("δ��ȡ�ͻ���֤��");
			return "";
		}
		try {			
				var signature = security.Cert.SignMessage(obj.originalData, OUTPUT_BASE64);
				return signature;
			} catch (e) {
				if(-2147483135 == e.number) {
					//�û�ȡ��ǩ��
					alert("��ȡ����PTAǩ������ǩ��������ʹ�ø�ϵͳ");
					return "";
				}else if(e.number == -2146885621) {
					alert("����ӵ��֤�顰" + CurCert.CommonName + "����˽Կ��ǩ��ʧ�ܡ�");
					return "";
				}else{
					alert("PTAǩ��ʱ��������\n����ţ� " + e.number + "\n���������� " + e.description);
					return "";
			}		
	}
	
	
	


})();