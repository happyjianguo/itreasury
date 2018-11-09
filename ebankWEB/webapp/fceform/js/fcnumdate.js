/**
*取客户端的当前时间
*@return 17:45 格式
*@date 2005-04-05
**/
function curTime(){
	var s1="";
	var c = ":";
	var d = new Date();
	var iHours=d.getHours() ;
	var iMinutes=d.getMinutes() ;
	if(iHours<10){
		iHours="0"+iHours;
	}else{
		iHours=""+iHours;
	}
	if(iMinutes<10){
		iMinutes="0"+iMinutes;
	}else{
		iMinutes=""+iMinutes;
	}
    s1= iHours + c;
    s1+= iMinutes;
	return s1;
}
function Num(str1){return num(str1);}

/**
*求当前年的第一天
*@date 2004-05-02
**/
function YearFirstDay(){
	var dDate=new Date();
	var s1=""+dDate.getYear();
	s1+="-01-01";
	return s1;
}
/**
*求当前年的最后一天
*@date 2004-05-02
**/
function YearLastDay(){
	var dDate=new Date();
	var s1=""+dDate.getYear();
	s1+="-12-31";
	return s1;
}
/**
*字符型变实数，用于用户自定义函数用，如字符为空则为0
*@param str1 要转换的字符串
*@return 转换后的数值
*@date 2003-07-10
**/
function num(str1){
	var s1=trim(str1);
	if(isSpace(s1)) {return 0;}
	var f1=parseFloat(s1);
	if(isNaN(f1)) {return 0;}
	return f1;
}
/**
转换成整数
*@date 2004-08-17
**/
function ToInt(str1){
	var s1=trim(str1);
	if(isSpace(s1)) {return 0;}
	var f1=parseInt(s1);
	if(isNaN(f1)) {return 0;}
	return f1;
}
/**
*当前日期时间 2003-01-01 11:20:20
*@date 2003-12-03
**/
function curDateTime(){
	var s1=curDate()+" ";
	var c = ":";
	var d = new Date();
	var iHours=d.getHours() ;
	var iMinutes=d.getMinutes() ;
	var iSeconds=d.getSeconds() ;
	if(iHours<10){
		iHours="0"+iHours;
	}else{
		iHours=""+iHours;
	}
	if(iMinutes<10){
		iMinutes="0"+iMinutes;
	}else{
		iMinutes=""+iMinutes;
	}
	if(iSeconds<10){
		iSeconds="0"+iSeconds;
	}else{
		iSeconds=""+iSeconds;
	}
		
    s1+= iHours + c;
    s1+= iMinutes + c;
    s1+= iSeconds ;
	return s1;
}
//'*********************************************************
//'*********************************************************
// ' 2、Purpose: 判断输入是否为数值(包括小数点)
// ' Inputs:String
// ' Returns:True,False
//'*********************************************************
function IsFloat(str){ 
	if (str==""){
		return false;
	}
	if (parseFloat(str)==NaN || isNaN(str)){
		return false;
	}
	return true;
}

//'*********************************************************
//'*********************************************************
// ' 2、Purpose: 判断输入是否为整数值
// ' Inputs:String
// ' Returns:True,False
//'*********************************************************
function IsInt(str){ 
	if (str==""){
		return false;
	}
	if (parseInt(str)==NaN || isNaN(str)){
		return false;
	}
	return true;
}
/**
*取得服务器的当前时间
*@date 2003-12-15
**/
function GetTime(){
	var sql="select substring(convert(varchar(19),getdate(),20),12,20) ";
 	var s1=SqlToField(sql);
 	return s1;
}
/**
*取得服务器的当前日期时间
*@date 2003-12-15
**/
function GetDateTime(){
	var sql="select convert(varchar(19),getdate(),20) ";
 	var s1=SqlToField(sql);
 	return s1;
}
//-----------------------------------------------------------
function DateToInt(date) {
   var year = date.getFullYear();
   var month = date.getMonth() + 1;
   var day = date.getDate() + 1;

   var a = Math.floor((14-month)/12);
   year += 4800 - a;
   month += 12*a - 3;
   return day + Math.floor((153*month+2)/5) + year*365 + Math.floor(year/4) - Math.floor(year/100) + Math.floor(year/400) - 32045;
}

function IntToDate(ndate) {
    var year, month, day;

    var a = ndate + 32044;
    var b = Math.floor(( (a*4) + 3) / 146097);
    var c = a - Math.floor((b * 146097)/4);

    var d = Math.floor(((c*4) + 3) / 1461);
    var e = c - Math.floor((1461 * d)/4);
    var m = Math.floor((5 * e + 2) / 153);

    day = e - Math.floor((153 * m + 2)/5) + 1;
    month = m + 3 - 12 * (Math.floor(m/10));
    year = b * 100 + d - 4800 + Math.floor(m/10);

    var retdate = new Date();
    retdate.setFullYear(year);
    retdate.setMonth(month-1);
    retdate.setDate(day-1);
    return retdate;
}

function DateToStr(date) {
  month = date.getMonth() + 1;
  day = date.getDate();

  return date.getFullYear() + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
}

function DateStrToDate(dateStr) {
  var newDateObj;
  var month, day;

  newDateObj = new Date( dateStr.replace("-", "/") );
  if ( newDateObj == "NaN" ){
    return DateStrToDate("1970-01-01");
  }else{
    return newDateObj;
  }
}
/**
计算一个日期加减某些天后得到另外一个日期,日期格式: 2000-09-09
*@param dateStr 起始日期
*@param nDateInterval 整型 加减的天数
*@return 计算后的日期 字符型 
**/
function DateAdd(dateStr, nDateInterval) {
 var ndate = DateToInt(DateStrToDate(dateStr)) + nDateInterval;
 return DateToStr(IntToDate(ndate));
}

//demo 
//alert( DateAdd('2004-03-04', 5) );
//-----------------------------------------------------------

/**
检查是否为合法的日期
*@param sDate 要检查的日期 字符型 格式:2000-09-09
*@return true 表示合法的日期,否则为false
*@date 2004-03-05
**/
function CheckDate(sDate){
	var s1;
	var s;
	var s2;
	var len;
	var new_year;
	var new_month;
	var new_date;
	s1=trim(sDate);
    if (s1.length>10) { return false;}
	if(isSpace(s1)){ return true; }
	s=s1.indexOf(".");
	s2=s1.lastIndexOf(".");
	len=s1.length;
      
	new_year=parseInt(s1.substring(0,4));
	new_month=s1.substring(5,7);
	new_date=s1.substring(8,10);
     
	if (new_month.substring(0,1)=="0"){
		new_month=new_month.substring(1,2);
	}
	if (new_date.substring(0,1)=="0"){
		new_date=new_date.substring(1,2);
        }
        
        if (new_month>12){ 
           bInturn=false;
           return bInturn;
        }
	
	var bInturn=true; //日期合法！
	 if(new_year<2100 && new_year>1900){
	 }else{
		bInturn=false;
		return bInturn;
	 }
	 if(new_month<=12 && new_month>=1){
	 }else{
		bInturn=false;
		return bInturn;
	 }
	 if(new_date>=1 && new_date<=31){
	 }else{
		bInturn=false;
		return bInturn;
	 }
	 if(new_month==4 || new_month==6 || new_month==9 || new_month==11){
		if(new_date==31){
			bInturn=false;
			return bInturn;
		}
	 }
	 if(new_month==2){
		var bln29=false; //日期不合法！
		if(Math.ceil(new_year/4)==(new_year/4)){
			bln29=true;
			if(Math.ceil(new_year/100)==(new_year/100)){
				bln29=false;
				if(Math.ceil(new_year/400)==(new_year/400)){
					bln29=true;
				}
			}
		}
		if(bln29){
			if(new_date>29){
				bInturn=false;
				return bInturn;
			}
				
		}else{
			if(new_date>28){
				bInturn=false;
				return bInturn;
			}
		}
	 }
	return bInturn;
		
}


/**
计算两个日期的差值天数
*@param date1 日期1 字符型 格式:2000-09-09
*@param date2 日期2 字符型 格式:2000-09-09
*@return 两个日期的差值天数,整型
**/
function DiffDate(date1,date2){
		var new_year1=parseInt(date1.substring(0,4));
		var month1=date1.substring(5,7);
		if(month1.substring(0,1)=="0"){
			month1=month1.substring(1,2);
		}
		var new_month1=parseInt(month1);
		var day1=date1.substring(8,10);
		if(day1.substring(0,1)=="0"){
			day1=day1.substring(1,2);
		}
		var new_day1=parseInt(1,2);
		var new_year2=parseInt(date2.substring(0,4));
		var month2=date2.substring(5,7);
		if(month2.substring(0,1)=="0"){
			month2=month2.substring(1,2);
		}
		var new_month2=parseInt(month2);
		var day2=date2.substring(8,10);
		if(day2.substring(0,1)=="0"){
			day2=day2.substring(1,2);
		}
		var new_day2=parseInt(day2);
		
	if(new_year1==new_year2){
		return sameyear(date1,date2);
	}

	if(new_year1 > new_year2){
			var sum=0;
			var sum1;
			var disdate1=parseInt(sameyear(date1,new_year1+".01.00"));
			var disdate2=parseInt(sameyear(new_year2+".12.31",date2));
			var disdate3=0;
            var bln365=true;
                        
		for(var i=new_year2+1;i<new_year1;i++){
			bln365=true;
			if(Math.ceil(i/4)==(i/4)){
				bln365=false;
				if(Math.ceil(i/100)==(i/100)){
					bln365=true;
					if(Math.ceil(i/400)==(i/400)){
						bln365=false;
					}	
				}
			}
			if(bln365){
				disdate3=disdate3+365;
			}else{
				disdate3=disdate3+366;
			}		
		}
		//alert(disdate1+"  "+disdate2+"  "+disdate3)
		sum=disdate1+disdate2+disdate3;
		return  sum;
	} else {
		return (-1)*DiffDate(date2,date1);
	}
}
function getdays(new_year,new_month){
		if(new_month==1 || new_month==3 || new_month==5 || new_month==7 || new_month==8 || new_month==10 || new_month==12){
			strFH=31;
		}
		if(new_month==4 || new_month==6 || new_month==9 || new_month==11){
			strFH=30;
		}
		if(new_month==2){
		var bln29=false;
			if(Math.ceil(new_year/4)==(new_year)/4){
				bln29=true;
				if(Math.ceil(new_year/100)==(new_year)/100){

					bln29=false;
					if(Math.ceil(new_year/400)==(new_year)/400){
						bln29=true;
						}
					}
				}
			if(bln29){
				strFH=29;
				
			}else{
				strFH=28;
			}
		}
		return strFH;
}
			
function sameyear(date1,date2){
	
		var new_year=parseInt(date1.substring(0,4));
		var month1=date1.substring(5,7);
		if(month1.substring(0,1)=="0"){
			month1=month1.substring(1,2);
		}
		var new_month1=parseInt(month1);
		var day1=date1.substring(8,10);
		if(day1.substring(0,1)=="0"){
			day1=day1.substring(1,2);
		}
		var new_day1=parseInt(day1);
		var month2=date2.substring(5,7);
		if(month2.substring(0,1)=="0"){
			month2=month2.substring(1,2);
		}
		var new_month2=parseInt(month2);
		var day2=date2.substring(8,10);
		if(day2.substring(0,1)=="0"){
			day2=day2.substring(1,2);
		}
		var new_day2=parseInt(day2);
                
		var disdate;//相差天数
		var disdate1;
		var disdate2;
		var disdate3=0;
		var sum=0;//相差总天数
		if(new_month1==new_month2){
			disdate=new_day1-new_day2;
			return disdate;
		}
		//alert(disdate)
       if(new_month1>new_month2){
		    disdate1=new_day1;
		    
		    disdate2=parseInt(getdays(new_year,new_month2))-new_day2;
     	   //
     	    for(var i=new_month2+1;i<new_month1;i++){
				disdate3=disdate3+parseInt(getdays(new_year,i));
			}
             
            sum=parseInt(disdate1)+parseInt(disdate2)+parseInt(disdate3);
                       // sum=parseFloat(sum);
         //  alert("sum="+sum)
         	return sum;
		}else {
			return (-1)*sameyear(date2,date1);
		}
}

//本文介绍了用查表法实现的公历到农历日期转换的方法，给出了实用的JScript脚本。可接受的公历日期范围是2001-1-1至2050-12-31。

// 数组LunarDaysOfMonth存入农历2001年到2050年每年中的月天数信息
// 农历每月只能是29或30天，一年用12(或13)个二进制位表示，从高到低，对应位为1表示30天，否则29天 
var LunarDaysOfMonth = new Array
(
    0xd4a8, 0xd4a0, 0xda50, 0x5aa8, 0x56a0, 0xaad8, 0x25d0, 0x92d0, 0xc958, 0xa950, // 2001-2010 
    0xb4a0, 0xb550, 0xb550, 0x55a8, 0x4ba0, 0xa5b0, 0x52b8, 0x52b0, 0xa930, 0x74a8, // 2011-2020 
    0x6aa0, 0xad50, 0x4da8, 0x4b60, 0x9570, 0xa4e0, 0xd260, 0xe930, 0xd530, 0x5aa0, // 2021-2030 
    0x6b50, 0x96d0, 0x4ae8, 0x4ad0, 0xa4d0, 0xd258, 0xd250, 0xd520, 0xdaa0, 0xb5a0, // 2031-2040 
    0x56d0, 0x4ad8, 0x49b0, 0xa4b8, 0xa4b0, 0xaa50, 0xb528, 0x6d20, 0xada0, 0x55b0  // 2041-2050 
);
 
// 数组LunarLeapYear存放农历2001年到2050年闰月的月份，如没有则为0，从高到低，每字节存两年 
var LunarLeapYear = new Array
(
    0x40, 0x02, 0x07, 0x00, 0x50, // 2001-2010 
    0x04, 0x09, 0x00, 0x60, 0x04, // 2011-2020 
    0x00, 0x20, 0x60, 0x05, 0x00, // 2021-2030 
    0x30, 0xb0, 0x06, 0x00, 0x50, // 2031-2040 
    0x02, 0x07, 0x00, 0x50, 0x03  // 2041-2050 
);
 
 
// 返回农历iLunarYear年的闰月月份，如没有则返回0 
function GetLeapMonth(iLunarYear)
{ 
    var Leap = LunarLeapYear[(iLunarYear - 2001) >> 1];
    return (((iLunarYear - 2001) & 1) == 0) ? (Leap >> 4) : (Leap & 0x0f);
} 
 
// 返回农历iLunarYer年iLunarMonth月的天数，结果是一个长整数
// 如果iLunarMonth不是闰月， 高字为0，低字为该月的天数
// 如果iLunarMonth是闰月， 高字为后一个月的天数，低字为前一个月的天数
function LunarMonthDays(iLunarYear, iLunarMonth)
{ 
    var High;
    var Low;
    var Bit;
 
    High = 0;
    Low = 29;
    Bit = 16 - iLunarMonth;
    if ((iLunarMonth > GetLeapMonth(iLunarYear)) && (GetLeapMonth(iLunarYear) > 0)) { Bit--;}
    if ((LunarDaysOfMonth[iLunarYear - 2001] & (1 << Bit)) > 0) { Low++;}
    if (iLunarMonth == GetLeapMonth(iLunarYear))
    {
        High = ((LunarDaysOfMonth[iLunarYear - 2001] & (1 << (Bit-1))) > 0) ?  30 : 29;
    }
 
    return Low + (High << 16);
} 
 
// 返回农历iLunarYear年的总天数
function LunarYearDays(iLunarYear)
{ 
    var Days;
    var tmp;
 
    Days = 0;
    for (var i=1; i <= 12; i++)
    {
        tmp = LunarMonthDays(iLunarYear, i);
        Days = Days + ((tmp >> 16) & 0xffff); //取高位 
        Days = Days + (tmp & 0xffff); //取低位 
    }
 
    return Days;
} 
 
// 将农历iLunarYear年格式化成天干地支记年法表示的字符串 
function FormatLunarYear(iLunarYear)
{ 
    var szText1 = new String("甲乙丙丁戊己庚辛壬癸");
    var szText2 = new String("子丑寅卯辰巳午未申酉戌亥");
    var strYear;
  
    strYear = szText1.substr((iLunarYear - 4) % 10, 1);
    strYear = strYear + szText2.substr((iLunarYear - 4) % 12, 1);
 
    return strYear + "年";
}
 
// 将农历iLunarMonth月格式化成农历表示的字符串
function FormatLunarMonth(iLunarMonth)
{ 
    var szText = new String("正二三四五六七八九十");
    var strMonth;
 
    if (iLunarMonth <= 10)
    {
        strMonth = szText.substr(iLunarMonth - 1, 1);
    }
    else if (iLunarMonth == 11) {strMonth = "十一";}
    else {strMonth = "十二";}
 
    return strMonth + "月";
} 
 
// 将农历iLunarDay日格式化成农历表示的字符串
function FormatLunarDay(iLunarDay)
{ 
    var szText1 = new String("初十廿三");
    var szText2 = new String("一二三四五六七八九十");
    var strDay;
    if ((iLunarDay != 20) && (iLunarDay != 30))
    {
        strDay = szText1.substr((iLunarDay - 1) / 10, 1) + szText2.substr((iLunarDay - 1) % 10, 1);
    }
    else if (iLunarDay != 20)
    {
        strDay = szText1.substr(iLunarDay / 10, 1) + "十";
    }
    else
    {
        strDay = "二十";
    }
  
    return strDay;
} 

/** 
调用方法举例如下：

var today= new Date();   // 今天是2004-3-5
var str = GetLunarDateString(today);

结果是 “甲申年二月十五”。

再举两个例子：

var date1 = new Date(2008, 9, 1);     // 2008-10-1
var date2 = new Date(2050, 4, 18);    // 2050-5-18
var str1 = GetLunarDateString(date1);
var str2 = GetLunarDateString(date2);

结果分别是 “戊子年九月初三” 和 “庚午年闰三月廿八”。

注意在Date中，月的范围是0-11。
 
// 将公历日期转换为农历日期，返回农历表示的字符串
**/
function GetLunarDateString(SolarDate)
{
    var tmp;
    var iLunarYear;
    var iLunarMonth;
    var iLunarDay;
    var Leap = false;
    var MinMilli = 1000 * 60;
    var HrMilli = MinMilli * 60;
    var DyMilli = HrMilli * 24;
  
    // 从2001年1月1日算起，给定的公历日期已经过去的天数
    // 11323是1970年1月1日到2001年1月1日之间的天数，因为Date是从1970年1月1日作为起点的
    var iSpanDays = Math.round(SolarDate.getTime() / DyMilli) - 11323;
 
    // 公历2001年1月24日为农历2001年正月初一，差23天
    if (iSpanDays < 23)
    {
        iYear = 2000;
        iLunarMonth = 12;
        iLunarDay = iSpanDays + 7;
    }
    else
    {
        // 从农历2001年正月初一算起 
        iSpanDays = iSpanDays - 23;
        iLunarYear = 2001;
        iLunarMonth = 1;
        iLunarDay = 1;
  
        // 计算农历年 
        tmp = LunarYearDays(iLunarYear);
        while (iSpanDays >= tmp)
        {
            iSpanDays -= tmp;
            iLunarYear++;
            tmp = LunarYearDays(iLunarYear);
        }
 
        // 计算农历月 
        tmp = LunarMonthDays(iLunarYear, iLunarMonth) & 0xffff; //取低字
        while (iSpanDays >= tmp)
        {
            iSpanDays -= tmp;
            if (iLunarMonth == GetLeapMonth(iLunarYear))  // 该年该月闰月
            {
                tmp = LunarMonthDays(iLunarYear, iLunarMonth) >> 16; //取高字
                if (iSpanDays < tmp)
                {
                    Leap = (tmp > 0) ? true : false;  // 闰月的后个月？
                    break;
                }
                iSpanDays = iSpanDays - tmp;
            }
  
            iLunarMonth++;
            tmp = LunarMonthDays(iLunarYear,iLunarMonth) & 0xffff; //取低字
        }
  
        // 计算农历日 
        iLunarDay += iSpanDays;
    }
  
    return FormatLunarYear(iLunarYear) + (Leap ? "闰" : "") + FormatLunarMonth(iLunarMonth) + FormatLunarDay(iLunarDay);
}

