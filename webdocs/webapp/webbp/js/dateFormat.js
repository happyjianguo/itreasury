/**
*Ŀ����Ҫ�Ƕԣ����뵱�������Զ���������ڸ�ʽ����Ƶ�����ֽ�����֤
*/

/**
*����Ƿ��ǺϷ���ʱ��ʱ��
*/
function isTime(objBegin,objEnd,beginTime,endTime)
{
    
	var begin;
	var end;	
	var differ;
	for (i=0;i<2;i++)
	{
		if(i==0)
			{
			begin=getFormatTime(objBegin,beginTime);
			//alert("begin="+begin);
			}
		if(begin==null)
		break;
		else if(i==1)
			{
			end=getFormatTime(objEnd,endTime);
			//alert("end="+end);
			}
	}
	differ=end-begin;
	//alert("differ="+differ)
	if((begin<end) && (differ>=30*60))
	return differ;
	else if(begin!=null && end!=null)
	{
	if(differ<0)
	{
	alert("��ʼ���ڱ���С�ڽ������ڣ�����������!");
	objBegin.focus();
	return 0;
	}
	else
	{
	alert("��ʼ���ںͽ������ڼ��ʱ���С��30����!");
	objEnd.focus();
	return 0;
	}
	}
	return 0;
}
/**
*����Ƿ��ǺϷ���ʱ���ʽ
*/
function getFormatTime(obj,sDate)
{
    var tmph="";	
	var statusTmph;
	statusTmph=0;
	var tmpm="";	
	var statusTmpm;
	statusTmpm=0;
	var tmps="";	
	var statusTmps;
	statusTmps=0;
	var lthdatestr;
	var status;
	status=0;
	var seconds;
	if (sDate != "")
		lthdatestr= sDate.length ;
	else
		{
		alert("������������ֵΪ�գ�������!");	
		obj.focus();	
		return ;
		}
	for (j=0;j<lthdatestr;j++)
	{	if (sDate.charAt(j)== ':')
		{
			status++;
		}
		if (status>2)
		{			
		    alert("�����������ڸ�ʽ���ԣ�����������!");		
			obj.focus();	
			return ;
		}
		if ((status==0) && (sDate.charAt(j)!=':') )
		{		
			tmph=tmph+sDate.charAt(j)	
			statusTmph++;		
		}
		if ((status==1) && (sDate.charAt(j)!=':'))
		{		
			tmpm=tmpm+sDate.charAt(j)	
			statusTmpm++;		
		}
		if ((status==2) && (sDate.charAt(j)!=':'))
		{		
			tmps=tmps+sDate.charAt(j)		
			statusTmps++;
		}		
	}
	if ((statusTmph!=1 && statusTmph!=2) || (statusTmpm!=1 && statusTmpm!=2) || (statusTmps!=1 && statusTmps!=2))
	{			
	    alert("�����������ڸ�ʽ���ԣ�����������!");	
		obj.focus();			
		return ;
	}
	if(tmph=="" )
	{
	alert("�����������ڸ�ʽ���ԣ�����������!"); 	
	obj.focus();	
	return ;
	}else if(tmph>=24)
	{
	alert("��������Сʱ�����ԣ�����������!"); 	
	obj.focus();	
	return ;
	}
	if(tmpm=="" ) 
	{
	alert("�����������ڸ�ʽ���ԣ�����������!"); 	
	obj.focus();	
	return ;
	}else if(tmpm>=60)
	{
	alert("����������������ԣ�����������!"); 	
	obj.focus();	
	return ;
	}
	if(tmps=="") 
	{
	alert("�����������ڸ�ʽ���ԣ�����������!"); 
	obj.focus();	
	return ;
	}else if(tmps>=60)
	{
	alert("���������������ԣ�����������!"); 	
	obj.focus();	
	return ;
	}		
	if(tmph!="")
	seconds=parseFloat(tmph)*60*60;		
	if(tmpm!="" )
	seconds=seconds+parseFloat(tmpm)*60;
	if(tmps!="")
	seconds=seconds+parseFloat(tmps);	
	//alert("seconds="+seconds);
	return seconds;   
}
/**
*��Сִ��Ƶ�ʴ���30���ӣ�С�ڹ涨��ʱ���
*/
function differValue(obj,valTime,beginTime,endTime)
{
	var interval=valTime;
	var begin=beginTime;
	var end=endTime;
	if(interval<30)
		{
		alert("�����Ƶ��ʱ�䲻��С��30����!");
		obj.focus();
		return false;
		}
	if(interval*60>isTime(null,null,begin,end))
		{
		alert("�����Ƶ��ʱ����ڹ涨��ʱ���!");
		obj.focus();
		return false;
		}
	return true;
}
/**
*��ʽ�����ݣ�obj��Ϊ�����֣�����ֵ����λ�ַ���""���硰0����ʽ��Ϊ��00��."-1"����Ϊ"".
*/
function formatData(obj)
{
//	alert("format:"+obj);
	if(obj==null) return "";
	if(obj==-1) return "";
	obj=""+obj;
	if(obj=="") return "";
	if(obj.length==1) return "0"+obj;
	else return obj;
}
function ajustData(obj)
{
	if(obj==null) return "00";
	obj=""+obj;
	if(obj.length<2) return "0"+obj;
	else return obj;
}
function adjustTime(strFormName,strCtrlName,strNextCtrlName,nType)
{
	if(window.event.keyCode!=13)
	{
		var obj = eval(strFormName+"."+strCtrlName);
		var strOldData = obj.value;
		var strPatten;
		switch(nType)
		{
		case 1:
			strPatten=/^(([0-1]?\d)|([2][0-3])|(\s*))$/;	
			if(!strPatten.test(strOldData)) 
			{
				alert("Сʱ����������");
				obj.value="";
			  obj.select();
			}else{
				obj.value=formatData(strOldData);	
			}
			break;
		case 2:
		  strPatten=/^(([0-5]?\d)|(\s*))$/;	
			if(!strPatten.test(strOldData)) 
			{
				alert("���ӵ���������");
				obj.value="";
				obj.select();
			//	obj.focus();
			}else{
				obj.value=formatData(strOldData);	
			}
			break;
		case 3:
		  break;	
		}
	}
}
function getFormatTime1(strFormName, strCtrlName)
{
	var strTime="";
	var strH = eval(strFormName+"."+"H"+strCtrlName+".value");
	var strM = eval(strFormName+"."+"M"+strCtrlName+".value");
	if(strH=="" && strM=="") return "";
	//if(strH=="00" && strM=="00") return "";
	if(strH=="" || strM=="")
	{
		throw "ʱ���ʽ��������������";
	}
	strTime=strH+":"+strM;
	return strTime;
}
/**
*ʱ���࣬Сʱ�����ӣ��룬����24Сʱ��
*/
function bs_time()
{
	var hour=-1;
	var minute=-1;
	var second=-1;	
}
function getFormatBSTime(strTime)
{
	var result = null;
	if(strTime==null || strTime=="")
	{
		return 	null;
	}
	else
	{
		var ltime = strTime.length;
		if(ltime>8) return null;
		var strHour="";
		var strMinute="";
		var strSecond="";
		var status=0;
		var isNum=0;
		result = new bs_time();
		try{
			for(var i=0;i<ltime;i++)
			{
					if(strTime.charAt(i)==":")
					{
							status++;
							isNum=0;
					}else isNum++;
					if(status>2)
					{
						 throw "ʱ���ʽ����";
					}
					if(status==0)
					{
						if(isNum==2 && strHour=='0') strHour="";
						strHour+=strTime.charAt(i);	
					}
					if(status==1 && isNum!=0)
					{
						if(isNum==2 && strMinute=='0') strMinute="";
						strMinute += strTime.charAt(i);	
					}
					if(status==2 && isNum!=0)
					{
						if(isNum==2 && strSecond=='0') strSecond="";
						strSecond += strTime.charAt(i);	
					}
			}
			result.hour = parseInt(strHour);
			result.minute = parseInt(strMinute);
			result.second = parseInt(strSecond);
		}catch(ex){
		}		
	}
	return result;	
}
function getHour()
{
	return formatData(this.hour);	
}
function getMinute()
{
	return formatData(this.minute);	
}
bs_time.prototype.getHour = getHour;
bs_time.prototype.getMinute = getMinute;
/**
*����һ��ʱ��ؼ�(Сʱ:����)���ؼ�������ʼֵ����һ��ȡ�ý���Ŀؼ�
*/
function createTimeCtrl(strFormName, strCtrlName, strInitTime, strNextCtrlName,strProperty)
{
	var initTime = getFormatBSTime(strInitTime);
	var hCtrlName = "H"+strCtrlName;
	var mCtrlName = "M"+strCtrlName;
	var hStr = "";
	var mStr = "";
	if(initTime!=null)
	{
//		alert("initTime:"+initTime);
		hStr = initTime.getHour();
		mStr = initTime.getMinute();	
	}
	
	//Сʱ
document.write(" <input class=timer type='text' size=2 maxlength=2 name='"+ hCtrlName + "' value='" + hStr+"' onblur=adjustTime(\""+strFormName+"\",\""+hCtrlName+"\",\""+mCtrlName+"\",1,1) onFocus=\"nextfield='"+mCtrlName+"';\""+strProperty+"><B>:</B>");
	//����
document.writeln(" <input class=timer type='text' size=2 maxlength=2 name='"+ mCtrlName + "' value='" + mStr+"' onblur=adjustTime(\""+strFormName+"\",\""+mCtrlName+"\",\""+strNextCtrlName+"\",2,1) onFocus=\"nextfield='"+strNextCtrlName+"';\" "+strProperty+">");
}