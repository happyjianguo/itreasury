//********************************************************
//	һЩ��cookie�Ĳ���������
//	author:xintan
//	date:2005-12-29
//	�����ĺ����У�
//	1. getCookie	ȡ����Ϊname��cookie����
//	2. setCookie	���ַ����赽ָ����cookie��ȥ
//	3. setArgsValuesToCookie ���ں���FieldSetttoCookie��ָ���Ŀؼ���ֵ���õ�cookie��ȥ
//	4. getArgsValueFromCookie ��cookie��ȡ��ָ����������ֵ
//  5. initFormData ʹ��cookie�е�ֵ��ʼ��ָ���Ŀؼ�
//********************************************************	
	
	var strCookie="";	//ָ����cookie����
	var isInit=0;			//�Ƿ��ʼ��strCookie,1����;2����
	
	//ȡ������Ϊname��cookieֵ	
	//name: cookie��
	//@return:cookie�������ַ�������������ڣ�null
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
	
	//����cookie
	//name:cookie��
	//value:Ҫ���浽cookie����ֵ��
	//expires:cookie��������������λ����
	//path:
	//domain:
	//secure:
	//@return:��
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
	//����cookie����ָ���������ڣ�������session,ie�ر�ʱ��Ӧcookie��ʧ
	function setCookie1(name, value, path,domain,secure)
	{
		document.cookie = name + "=" + escape(value) +			
			((path)?";path=" + path:"") +
			((domain)?";domain="+domain:"")+
			((secure)?";secure":"");
	}
	
	//��ָ������cookie��ȡ��ָ���������Ĳ���ֵ
	//cooikeName:cookie��
	//argsName:Ҫȡֵ�Ĳ�����	
	//@return:����ֵ�����ָ����cookie����������ڣ�����null
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
	
	//��cookie�е�ֵ���뵽ָ���Ŀؼ��У��ؼ���
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
