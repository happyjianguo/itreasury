//********************************************************
//	一些对cookie的操作函数。
//	author:xintan
//	date:2005-12-29
//	包含的函数有：
//	1. getCookie	取得名为name的cookie内容
//	2. setCookie	把字符串设到指定的cookie里去
//	3. setArgsValuesToCookie 将在函数FieldSetttoCookie中指定的控件的值设置到cookie中去
//	4. getArgsValueFromCookie 从cookie中取得指定参数名的值
//  5. initFormData 使用cookie中的值初始化指定的控件
//********************************************************	
	
	var strCookie="";	//指定的cookie内容
	var isInit=0;			//是否初始化strCookie,1：是;2：否
	
	//取得名称为name的cookie值	
	//name: cookie名
	//@return:cookie的内容字符串。如果不存在，null
	function getCookie(name)
	{
	//	alert(document.cookie);
		var start = document.cookie.indexOf( name+"=");
		var len = start + name.length+1;
		if((!start)&&(name!=document.cookie.substring(0, name.length)))
		{
			return null;	
		}
		if(start==-1) return null;
		var end = document.cookie.indexOf(";", len);
		if(end==-1) end = document.cookie.length;
		return unescape(document.cookie.substring(len, end));
	}
	
	//设置cookie
	//name:cookie名
	//value:要保存到cookie的名值对
	//expires:cookie的生存天数。单位：天
	//path:
	//domain:
	//secure:
	//@return:无
	function setCookie(name, value, expires, path,domain,secure)
	{
		var today = new Date();
		today.setTime(today.getTime());	
		if(expires)
		{
			//set expires time for X number of days
			expires = expires*1000*60*60*24;	
		}
		var expires_date = new Date(today.getTime() + expires);
		document.cookie = name + "=" + escape(value) +
			((expires)?";expires="+expires_date.toGMTString():"") + 
			((path)?";path=" + path:"") +
			((domain)?";domain="+domain:"")+
			((secure)?";secure":"");
	}
	//设置cookie，不指定生命周期，即类似session,ie关闭时对应cookie消失
	function setCookie1(name, value, path,domain,secure)
	{
		document.cookie = name + "=" + escape(value) +			
			((path)?";path=" + path:"") +
			((domain)?";domain="+domain:"")+
			((secure)?";secure":"");
	}
	
	//从指定名的cookie中取出指定参数名的参数值
	//cooikeName:cookie名
	//argsName:要取值的参数名	
	//@return:参数值，如果指定的cookie或参数不存在，返回null
	function getArgsValueFromCookie(cookieName, argsName)
	{
		if(isInit==0)	strCookie = getCookie(cookieName);	
	//	alert("cookie content:" + strCookie);
		if(strCookie!=null && argsName!=null)
		{
			var start= strCookie.indexOf("&"+argsName+"=");
	//		alert("start = " + start);
			if(start<0) return null;
			start = start+2+argsName.length;
			var end = strCookie.indexOf("&",start);	
			return strCookie.substring(start, end);
		}
		else return null;
	}

	function setArgsValuesToCookie(cookieName,path)
	{
			var fields = new FieldSetttoCookie();
			var fname;
			var fvalue;
			var cookieValue = "";
			if(fields!=null)
			{
				for(x in fields)
				{
						fname = fields[x][0];
						fvalue = document.all[fname].value;
						if(fvalue==null) fvalue="";
						cookieValue+="&"+fname+"="+fvalue;
				}
				cookieValue+="&";
				setCookie(cookieName, cookieValue,2,path,'','');
			}
	}
	
	//将cookie中的值载入到指定的控件中，控件在
	function initFormData(frm, cookieName)
	{
		var fields = new FieldSetttoCookie();
		var fname;
		var fvalue;
		if(fields!=null)
		{
			for(x in fields)	
			{
					fname = fields[x][0];
					fvalue = getArgsValueFromCookie(cookieName, fname);
			//		alert(fname + "=" + fvalue);
					if(fvalue==null) fvalue='';
					eval(frm + "." + fname + ".value = '" + fvalue + "'");
			}
		}
	}
