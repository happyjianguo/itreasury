(function(){

	window.security = {
		isSign:function(){
			return true;
		},
		filter:function(obj){
			securityProvider.SelectSignCertificate(obj.CertCN,'');
		},
		sign:function(obj){
			try
			{
			 	var signatureData = securityProvider.SignMessageDetached(obj.originalData,'SHA-1');
			 	return signatureData;
		 	}catch(e)
		 	{
		 		alert(e.number+":"+e.name);
		 		return null;
		 	}			
		}
	}
})();