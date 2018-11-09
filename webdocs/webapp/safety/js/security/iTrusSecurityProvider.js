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
				alert("获得证书时发生错误，"+ "\n请检查您的key是否正确插入");
				security.Cert = null;
			}
		}		
		alert("没有找到有效的证书！");
		security.Cert = null;
	}
	
	window.security.sign = function(obj)
	{
		if(security.Cert == null)
		{
			alert("未获取客户端证书");
			return "";
		}
		try {			
				var signature = security.Cert.SignMessage(obj.originalData, OUTPUT_BASE64);
				return signature;
			} catch (e) {
				if(-2147483135 == e.number) {
					//用户取消签名
					alert("您取消了PTA签名，不签名将不能使用该系统");
					return "";
				}else if(e.number == -2146885621) {
					alert("您不拥有证书“" + CurCert.CommonName + "”的私钥，签名失败。");
					return "";
				}else{
					alert("PTA签名时发生错误\n错误号： " + e.number + "\n错误描述： " + e.description);
					return "";
			}		
	}
	
	
	


})();