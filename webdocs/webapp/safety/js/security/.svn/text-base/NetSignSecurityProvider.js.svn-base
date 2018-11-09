(function(){

	if(!window.security)
	{
		window.security = {};
	}
	
	window.security.isSign = function(){
		return true;
	}
	
	window.security.filter = function(obj)
	{
		return "";
	}
	
	window.security.sign = function(obj)
	{
		var signature = "";
		try{
			securityProvider.NSSetPlainText(obj.originalData);
		}catch(ex){
			alert("签名异常，异常原因："+e.description);
			return "";
		}	
		if(securityProvider.errorNum!=0)
		{
			alert("签名异常");
			return "";
		}	
		try{
			signature = securityProvider.NSAttachedSign("");
		}catch(e){
			alert("签名异常，异常原因："+e.description);
			return "";
		}	
		if(securityProvider.errorNum!=0)
		{
			alert("返回值〔"+securityProvider.errorNum+"〕\n"+securityProvider.errMsg+"\n底层返回值("+securityProvider.errorCode+")");
			return "";
		}	
		return signature;		
	}
	
	
	


})();