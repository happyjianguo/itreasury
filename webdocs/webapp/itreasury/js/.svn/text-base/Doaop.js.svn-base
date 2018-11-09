/*
$(function(){
	
	$.aop.around( {target: window, method: 'open'}, function(invocation) {
		doOpen(invocation);
		
	 	return  invocation.proceed();                                        
     } );   
});  
*/

function doOpen(invocation){
	var Password; 
	var url ;
	if(invocation.arguments[0].indexOf("iTreasury-ebank")>0)
	{
		url = "/NASApp/iTreasury-ebank";
	}
	else
	{
		url ="/NASApp";
	}
	$.ajaxSetup({                                                      
		async : false                                                    
		});                                                                                                                                     
	$.post(url+'/GetPasswordServlet',{
			date : new Date()                                                         
	},function(data){                                                  
			Password = data;                                                  
	},'text');    
	if(invocation.arguments[0].indexOf('?') == -1)                                
	invocation.arguments[0] += '?Password=' + Password;                                
	else                                
	invocation.arguments[0] += '&Password=' + Password; 
}

function stepTo(url){
   var a = document.createElement("a");
   if(document.body == null){
      document.write("<table></table>");
   }
   a.setAttribute("href", url);
   a.style.display = "none";
   document.body.appendChild(a); 
   a.click();
}
