(function(){

	if(!window.security)
	{
		window.security = {};
	}
	
	window.security.isSign = function(){
		return false;
	}
	
	window.security.filter = function(CertCN,CertSN)
	{
		return "";
	}
	
	window.security.sign = function(originalValue)
	{
		return "";
	}
	
	
	


})();