function PowerDate()
{
this.date = null;
this.isFmtZero = false;
this.weekArr =
[
["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
["SUN", "MON", "TUR", "WED", "THU", "FRI", "SAT"]
];
this.monthArr = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
switch (arguments.length)
{
case 0:
this.date = new Date();
break;
case 1:
var reg = /^(\d{2,4})\D+(\d{1,2})\D+(\d{1,2})$/;
var str = arguments[0].replace(/\s/g, "");
str = str.replace(reg, "$1/$2/$3");
var o = new Date(str);
if(isNaN(o)){
var str1 = arguments[0];
if(str1.length == 8){
o = new Date(str1.substring(0,4)+"/"+str1.substring(4,6)+"/"+str1.substring(6,8));
}else if(str1.length > 8){
o = new Date(str1.substring(0,4),ToInt(str1.substring(4,6))-1,ToInt(str1.substring(6,8)),ToInt(str1.substring(8,10)),ToInt(str1.substring(10,12)),ToInt(str1.substring(12,14)));
}
}
this.date = o ;
break;
case 3:
this.date = new Date(arguments[0], arguments[1] - 1, arguments[2]);
break;
case 6:
this.date = new Date(arguments[0], arguments[1]
- 1, arguments[2], arguments[3], arguments[4], arguments[5]);
break;
case 7:
this.date = new Date(arguments[0], arguments[1]
- 1, arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]);
break;
default:
this.date = new Date("1970/1/1");
break;
}
if (typeof (this.date) != "object" || !(/Date/.test(this.date.constructor)))
throw( new Error(-1, '构造PowerDate方法失败,检查输入参数!'));
function ToInt(str1){
var s1=trim(str1);
if(s1.charAt(0)=="0") s1 = s1.substring(1,s1.length);
var f1=parseInt(s1);
if(isNaN(f1)) {return 0;}
return f1;
}
this.getDate = function()
{
return this.date;
}
this.getFullYear = function()
{
return this.date.getFullYear();
};
this.getYear = function()
{
return this.date.getYear();
};
this.getMonth = function()
{
return this.frmWithZero(this.date.getMonth() + 1);
};
this.getDay = function()
{
return this.frmWithZero(this.date.getDate());
};
this.getHour = function()
{
return this.frmWithZero(this.date.getHours());
};
this.getMinute = function()
{
return this.frmWithZero(this.date.getMinutes());
};
this.getSecond = function()
{
return this.frmWithZero(this.date.getSeconds());
};
this.getMillisecond = function()
{
var ss = this.date.getMilliseconds();
if (this.isFmtZero == true && ss < 10)
return "00" + ss;
else if (this.isFmtZero == true && ss < 100)
return "0" + ss;
else
return ss;
};
this.getWeek = function()
{
return this.date.getDay();
};
this.setIsFmtZero = function(val)
{
this.isFmtZero = val;
};
this.frmWithZero = function(num)
{
if (this.isFmtZero == true && num < 10)
return "0" + num;
else
return num;
}
this.getString = function(dateFmt)
{
if (typeof (dateFmt) != "string")
throw( new Error(-1, 'getString()方法需要字符串类型参数!'));
var str = dateFmt;
str = str.replace(/yy/g, this.getFullYear());
str = str.replace(/YY/g, this.getYear());
str = str.replace(/mm/g, this.getMonth());
str = str.replace(/MM/g, this.monthArr[this.getMonth() - 1]);
str = str.replace(/dd/g, this.getDay());
var h = this.getHour();
var sam = "am";
if(dateFmt.indexOf("am")>=0){
if(h>12){
sam = "pm";
h = h -12 ;
}
str = str.replace(/am/g,sam );
}
str = str.replace(/hh/g,h );
str = str.replace(/mi/g, this.getMinute());
str = str.replace(/ss/g, this.getSecond());
str = str.replace(/ms/g, this.getMillisecond());
str = str.replace(/we/g, this.weekArr[0][this.getWeek()]);
str = str.replace(/WE/g, this.weekArr[1][this.getWeek()]);
return str;
};
this.dateAfterDays = function(num)
{
if (typeof (num) != "number")
throw new Error(-1, "dateAfterDays(num)参数为数值类型.");
var dd = this.date.valueOf();
dd += num * 24 * 3600 * 1000;
this.date = new Date(dd);
return this;
};
this.dateAfterSeconds = function(num)
{
if (typeof (num) != "number")
throw new Error(-1, "dateAfterDays(num)参数为数值类型.");
var dd = this.date.valueOf();
dd += num * 1000;
this.date = new Date(dd);
return this;
};
this.isLeapYear = function()
{
var year = this.getFullYear();
return (0 == year % 4 && ((year % 100 != 0) || (year % 400 == 0)));
};
this.getDaysOfMonth = function()
{
return ( new Date(this.getFullYear(), this.getMonth(), 0)).getDate();
};
this.getChinaDate = function()
{
var year = this.getFullYear();
var month = this.getMonth();
var day = this.getDay();
var arrNum = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"];
var strTmp = "";
for (var i = 0, j = year.length; i < j; i++)
{
strTmp += arrNum[year.charAt(i)];
}
strTmp += "年";
strTmp += arrNum[month] + "月";
if (day < 10)
strTmp += arrNum[day];
else if (day < 20)
strTmp += "十" + arrNum[day - 10];
else if (day < 30)
strTmp += "二十" + arrNum[day - 20];
else
strTmp += "三十" + arrNum[day - 30];
strTmp += "日";
return strTmp;
};
this.dateCompare = function(dat)
{
if (typeof (dat) == "string")
{
if (dat != "")
dat = new Date(timeString);
else
dat = new Date();
}
if (typeof (dat) != "object" || !(/Date/.test(this.date.constructor)))
{
throw new Error(-2, "dateCompare的参数为日期类型或者可直接转化为日期类型的字符串!");
}
var d = this.date.getTime() - dat.getTime();
return d > 0 ? 1 : (d == 0 ? 0 : -1);
}
this.calDateDistance = function(pd, type, intOrFloat)
{
var miSecMain = this.date.valueOf();
var miSecSub = pd.getDate().valueOf();
var num = 0;
switch (type)
{
case "yy":
num = this.getFullYear() - pd.getFullYear();
break;
case "mm":
num = (this.getFullYear() - pd.getFullYear()) * 12 + this.getMonth() - pd.getMonth();
break;
case "dd":
num = this.fmtRtnVal((miSecMain - miSecSub) / 86400000, intOrFloat);
break;
case "hh":
num = this.fmtRtnVal((miSecMain - miSecSub) / 3600000, intOrFloat);
break;
case "mi":
num = this.fmtRtnVal((miSecMain - miSecSub) / 60000, intOrFloat);
break;
case "ss":
num = this.fmtRtnVal((miSecMain - miSecSub) / 1000, intOrFloat);
break;
case "ms":
num = (miSecMain - miSecSub);
break;
default:
throw new Error(-1, "没有您要求返回的类型,请检查输入参数!");
break;
}
return num;
};
this.fmtRtnVal = function(val, intOrFloat)
{
return (intOrFloat == 0 ? Math.floor(val) : parseInt(val * 100) / 100);
};
}