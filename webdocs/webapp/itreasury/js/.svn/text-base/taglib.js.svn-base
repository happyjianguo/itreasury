var weekend = [0,6];
var ARRAYNUM=100;
var weekendColor = "#e0e0e0";
var fontface = "Verdana";
var fontsize = 2;
var gNow = new Date();
var ggWinCal;
var gFormName;//控件所在Form的名称
gFormName=new Array(ARRAYNUM);
gAmount = new Array(ARRAYNUM);
var gNoLinkID;
var CN = new Array(10);
	CN[0]="零";
	CN[1]="壹";
	CN[2]="贰";
	CN[3]="叁";
	CN[4]="肆";
	CN[5]="伍";
	CN[6]="陆";
	CN[7]="柒";
	CN[8]="捌";
	CN[9]="玖";
for(gNoLinkID=0;gNoLinkID<ARRAYNUM;gNoLinkID++)
{
	gAmount[gNoLinkID]=new Array(ARRAYNUM);
}
isNav = (navigator.appName.indexOf("Netscape") != -1) ? true : false;
isIE = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;

fs_Calendar.Months = ["一月", "二月", "三月", "四月", "五月", "六月",
"七月", "八月", "九月", "十月", "十一月", "十二月"];

// Non-Leap year Month days..
fs_Calendar.DOMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
// Leap year Month days..
fs_Calendar.lDOMonth = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

function fs_Calendar(p_item, p_WinCal, p_month, p_year, p_format) {
	if ((p_month == null) && (p_year == null))	return;

	if (p_WinCal == null)
		this.gWinCal = ggWinCal;
	else
		this.gWinCal = p_WinCal;
	
	if (p_month == null) {
		this.gMonthName = null;
		this.gMonth = null;
		this.gYearly = true;
	} else {
		this.gMonthName = fs_Calendar.get_month(p_month);
		this.gMonth = new Number(p_month);
		this.gYearly = false;
	}

	this.gYear = p_year;
	this.gFormat = p_format;
	this.gBGColor = "white";
	this.gFGColor = "black";
	this.gTextColor = "black";
	this.gHeaderColor = "black";
	this.gReturnItem = p_item;
}

fs_Calendar.get_month = fs_Calendar_get_month;
fs_Calendar.get_daysofmonth = fs_Calendar_get_daysofmonth;
fs_Calendar.calc_month_year = fs_Calendar_calc_month_year;
fs_Calendar.print = fs_Calendar_print;

function fs_Calendar_get_month(monthNo) {
	return fs_Calendar.Months[monthNo];
}

function fs_Calendar_get_daysofmonth(monthNo, p_year) {
	/* 
	Check for leap year ..
	1.Years evenly divisible by four are normally leap years, except for... 
	2.Years also evenly divisible by 100 are not leap years, except for... 
	3.Years also evenly divisible by 400 are leap years. 
	*/
	if ((p_year % 4) == 0) {
		if ((p_year % 100) == 0 && (p_year % 400) != 0)
			return fs_Calendar.DOMonth[monthNo];
	
		return fs_Calendar.lDOMonth[monthNo];
	} else
		return fs_Calendar.DOMonth[monthNo];
}

function fs_Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

function fs_Calendar_print() {
	ggWinCal.print();
}

function fs_Calendar_calc_month_year(p_Month, p_Year, incr) {
	/* 
	Will return an 1-D array with 1st element being the calculated month 
	and second being the calculated year 
	after applying the month increment/decrement as specified by 'incr' parameter.
	'incr' will normally have 1/-1 to navigate thru the months.
	*/
	var ret_arr = new Array();
	
	if (incr == -1) {
		// B A C K W A R D
		if (p_Month == 0) {
			ret_arr[0] = 11;
			ret_arr[1] = parseInt(p_Year) - 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) - 1;
			ret_arr[1] = parseInt(p_Year);
		}
	} else if (incr == 1) {
		// F O R W A R D
		if (p_Month == 11) {
			ret_arr[0] = 0;
			ret_arr[1] = parseInt(p_Year) + 1;
		}
		else {
			ret_arr[0] = parseInt(p_Month) + 1;
			ret_arr[1] = parseInt(p_Year);
		}
	}
	
	return ret_arr;
}

// This is for compatibility with Navigator 3, we have to create and discard one object before the prototype object exists.
new fs_Calendar();

fs_Calendar.prototype.getMonthlyCalendarCode = function() {
	var vCode = "";
	var vHeader_Code = "";
	var vData_Code = "";
	
	// Begin Table Drawing code here..
	vCode = vCode + "<TABLE BORDER=1 BGCOLOR=\"" + this.gBGColor + "\">";
	
	vHeader_Code = this.cal_header();
	vData_Code = this.cal_data();
	vCode = vCode + vHeader_Code + vData_Code;
	
	vCode = vCode + "</TABLE>";
	
	return vCode;
}

fs_Calendar.prototype.show = function() {
	var vCode = "";
	
	this.gWinCal.document.open();

	// Setup the page...
	this.wwrite("<html>");
	this.wwrite("<head><title>日历</title>");
	this.wwrite("</head>");

	this.wwrite("<body " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	this.wwriteA("<FONT FACE='" + fontface + "' SIZE=2><B>");
	this.wwriteA(this.gMonthName + " " + this.gYear);
	this.wwriteA("</B><BR>");

	// Show navigation buttons
	var prevMMYYYY = fs_Calendar.calc_month_year(this.gMonth, this.gYear, -1);
	var prevMM = prevMMYYYY[0];
	var prevYYYY = prevMMYYYY[1];

	var nextMMYYYY = fs_Calendar.calc_month_year(this.gMonth, this.gYear, 1);
	var nextMM = nextMMYYYY[0];
	var nextYYYY = nextMMYYYY[1];
	
	this.wwrite("<TABLE WIDTH='100%' BORDER=1 CELLSPACING=0 CELLPADDING=0 BGCOLOR='#e0e0e0'><TR><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)-1) + "', '" + this.gFormat + "'" +
		");" +
		"\"><<<\/A>]</TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', '" + prevMM + "', '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\"><<\/A>]</TD>");
	this.wwrite("<TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', '" + nextMM + "', '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">><\/A>]</TD><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', '" + this.gMonth + "', '" + (parseInt(this.gYear)+1) + "', '" + this.gFormat + "'" +
		");" +
		"\">>><\/A>]</TD></TR></TABLE><BR>");

	// Get the complete fs_calendar code for the month..
	vCode = this.getMonthlyCalendarCode();
	this.wwrite(vCode);

	this.wwrite("</font></body></html>");
	this.gWinCal.document.close();
}

fs_Calendar.prototype.showY = function() {
	var vCode = "";
	var i;
	var vr, vc, vx, vy;		// Row, Column, X-coord, Y-coord
	var vxf = 285;			// X-Factor
	var vyf = 200;			// Y-Factor
	var vxm = 10;			// X-margin
	var vym;				// Y-margin
	if (isIE)	vym = 75;
	else if (isNav)	vym = 25;
	
	this.gWinCal.document.open();

	this.wwrite("<html>");
	this.wwrite("<head><title>日历</title>");
	this.wwrite("<style type='text/css'>\n<!--");
	for (i=0; i<12; i++) {
		vc = i % 3;
		if (i>=0 && i<= 2)	vr = 0;
		if (i>=3 && i<= 5)	vr = 1;
		if (i>=6 && i<= 8)	vr = 2;
		if (i>=9 && i<= 11)	vr = 3;
		
		vx = parseInt(vxf * vc) + vxm;
		vy = parseInt(vyf * vr) + vym;

		this.wwrite(".lclass" + i + " {position:absolute;top:" + vy + ";left:" + vx + ";}");
	}
	this.wwrite("-->\n</style>");
	this.wwrite("</head>");

	this.wwrite("<body " + 
		"link=\"" + this.gLinkColor + "\" " + 
		"vlink=\"" + this.gLinkColor + "\" " +
		"alink=\"" + this.gLinkColor + "\" " +
		"text=\"" + this.gTextColor + "\">");
	this.wwrite("<FONT FACE='" + fontface + "' SIZE=2><B>");
	this.wwrite("Year : " + this.gYear);
	this.wwrite("</B><BR>");

	// Show navigation buttons
	var prevYYYY = parseInt(this.gYear) - 1;
	var nextYYYY = parseInt(this.gYear) + 1;
	
	this.wwrite("<TABLE WIDTH='100%' BORDER=1 CELLSPACING=0 CELLPADDING=0 BGCOLOR='#e0e0e0'><TR><TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', null, '" + prevYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\" alt='Prev Year'><<<\/A>]</TD>");
	this.wwrite("<TD ALIGN=center>");
	this.wwrite("[<A HREF=\"" +
		"javascript:window.opener.fs_Build(" + 
		"'" + this.gReturnItem + "', null, '" + nextYYYY + "', '" + this.gFormat + "'" +
		");" +
		"\">>><\/A>]</TD></TR></TABLE><BR>");

	// Get the complete fs_calendar code for each month..
	var j;
	for (i=11; i>=0; i--) {
		if (isIE)
			this.wwrite("<DIV ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");
		else if (isNav)
			this.wwrite("<LAYER ID=\"layer" + i + "\" CLASS=\"lclass" + i + "\">");

		this.gMonth = i;
		this.gMonthName = fs_Calendar.get_month(this.gMonth);
		vCode = this.getMonthlyCalendarCode();
		this.wwrite(this.gMonthName + "/" + this.gYear + "<BR>");
		this.wwrite(vCode);

		if (isIE)
			this.wwrite("</DIV>");
		else if (isNav)
			this.wwrite("</LAYER>");
	}

	this.wwrite("</font><BR></body></html>");
	this.gWinCal.document.close();
}

fs_Calendar.prototype.wwrite = function(wtext) {
	this.gWinCal.document.writeln(wtext);
}

fs_Calendar.prototype.wwriteA = function(wtext) {
	this.gWinCal.document.write(wtext);
}

fs_Calendar.prototype.cal_header = function() {
	var vCode = "";
	
	vCode = vCode + "<TR>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;日&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;一&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;二&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;三&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;四&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='14%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;五&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "<TD WIDTH='16%'><FONT SIZE='2' FACE='" + fontface + "' COLOR='" + this.gHeaderColor + "'><B>&nbsp;六&nbsp;&nbsp;</B></FONT></TD>";
	vCode = vCode + "</TR>";
	
	return vCode;
}

fs_Calendar.prototype.cal_data = function() {
	var vDate = new Date();
	vDate.setDate(1);
	vDate.setMonth(this.gMonth);
	vDate.setFullYear(this.gYear);

	var vFirstDay=vDate.getDay();
	var vDay=1;
	var vLastDay=fs_Calendar.get_daysofmonth(this.gMonth, this.gYear);
	var vOnLastDay=0;
	var vCode = "";

	/*
	Get day for the 1st of the requested month/year..
	Place as many blank cells before the 1st day of the month as necessary. 
	*/

	vCode = vCode + "<TR>";
	for (i=0; i<vFirstDay; i++) {
		vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(i) + "><FONT SIZE='2' FACE='" + fontface + "'> </FONT></TD>";
	}

	// Write rest of the 1st week
	for (j=vFirstDay; j<7; j++) {
		vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j) + "><FONT SIZE='2' FACE='" + fontface + "'>" + 
			"<A HREF='#' " + 
				"onClick=\"self.opener." + this.gReturnItem + ".value='" + 
				this.format_data(vDay) + 
				"';window.close();"+"self.opener." + this.gReturnItem + ".focus();"+"\">" + 
				this.format_day(vDay) + 
			"</A>" + 
			"</FONT></TD>";
		vDay=vDay + 1;
	}
	vCode = vCode + "</TR>";

	// Write the rest of the weeks
	for (k=2; k<7; k++) {
		vCode = vCode + "<TR>";

		for (j=0; j<7; j++) {
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j) + "><FONT SIZE='2' FACE='" + fontface + "'>" + 
				"<A HREF='#' " + 
					"onClick=\"self.opener." + this.gReturnItem + ".value='" + 
					this.format_data(vDay) + 
					"';window.close();"+"self.opener." + this.gReturnItem + ".focus();"+"\">" + 
				this.format_day(vDay) + 
				"</A>" + 
				"</FONT></TD>";
			vDay=vDay + 1;

			if (vDay > vLastDay) {
				vOnLastDay = 1;
				break;
			}
		}

		if (j == 6)
			vCode = vCode + "</TR>";
		if (vOnLastDay == 1)
			break;
	}
	
	// Fill up the rest of last week with proper blanks, so that we get proper square blocks
	for (m=1; m<(7-j); m++) {
		if (this.gYearly)
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j+m) + 
			"><FONT SIZE='2' FACE='" + fontface + "' COLOR='gray'> </FONT></TD>";
		else
			vCode = vCode + "<TD WIDTH='14%'" + this.write_weekend_string(j+m) + 
			"><FONT SIZE='2' FACE='" + fontface + "' COLOR='gray'>" + m + "</FONT></TD>";
	}
	
	return vCode;
}

fs_Calendar.prototype.format_day = function(vday) {
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth();
	var vNowYear = gNow.getFullYear();

	if (vday == vNowDay && this.gMonth == vNowMonth && this.gYear == vNowYear)
		return ("<FONT COLOR=\"RED\"><B>" + vday + "</B></FONT>");
	else
		return (vday);
}

fs_Calendar.prototype.write_weekend_string = function(vday) {
	var i;

	// Return special formatting for the weekend day.
	for (i=0; i<weekend.length; i++) {
		if (vday == weekend[i])
			return (" BGCOLOR=\"" + weekendColor + "\"");
	}
	
	return "";
}

fs_Calendar.prototype.format_data = function(p_day) {
	var vData;
	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = fs_Calendar.get_month(this.gMonth).substr(0,3).toUpperCase();
	var vFMon = fs_Calendar.get_month(this.gMonth).toUpperCase();
	var vY4 = new String(this.gYear);
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	switch (this.gFormat) {
		case "YYYY-MM-DD" :
			vData = vY4 + "-" + vMonth + "-" + vDD;
			break;
		case "MM\/DD\/YYYY" :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
			break;
		case "MM\/DD\/YY" :
			vData = vMonth + "\/" + vDD + "\/" + vY2;
			break;
		case "MM-DD-YYYY" :
			vData = vMonth + "-" + vDD + "-" + vY4;
			break;
		case "MM-DD-YY" :
			vData = vMonth + "-" + vDD + "-" + vY2;
			break;

		case "DD\/MON\/YYYY" :
			vData = vDD + "\/" + vMon + "\/" + vY4;
			break;
		case "DD\/MON\/YY" :
			vData = vDD + "\/" + vMon + "\/" + vY2;
			break;
		case "DD-MON-YYYY" :
			vData = vDD + "-" + vMon + "-" + vY4;
			break;
		case "DD-MON-YY" :
			vData = vDD + "-" + vMon + "-" + vY2;
			break;

		case "DD\/MONTH\/YYYY" :
			vData = vDD + "\/" + vFMon + "\/" + vY4;
			break;
		case "DD\/MONTH\/YY" :
			vData = vDD + "\/" + vFMon + "\/" + vY2;
			break;
		case "DD-MONTH-YYYY" :
			vData = vDD + "-" + vFMon + "-" + vY4;
			break;
		case "DD-MONTH-YY" :
			vData = vDD + "-" + vFMon + "-" + vY2;
			break;

		case "DD\/MM\/YYYY" :
			vData = vDD + "\/" + vMonth + "\/" + vY4;
			break;
		case "DD\/MM\/YY" :
			vData = vDD + "\/" + vMonth + "\/" + vY2;
			break;
		case "DD-MM-YYYY" :
			vData = vDD + "-" + vMonth + "-" + vY4;
			break;
		case "DD-MM-YY" :
			vData = vDD + "-" + vMonth + "-" + vY2;
			break;

		default :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
	}

	return vData;
}

function fs_Build(p_item, p_month, p_year, p_format) {
	var p_WinCal = ggWinCal;
	gCal = new fs_Calendar(p_item, p_WinCal, p_month, p_year, p_format);

	// Customize your fs_Calendar here..
	gCal.gBGColor="white";
	gCal.gLinkColor="black";
	gCal.gTextColor="black";
	gCal.gHeaderColor="darkgreen";

	// Choose appropriate show function
	if (gCal.gYearly)	gCal.showY();
	else	gCal.show();
}

function fs_show_calendar() {
	/* 
		p_month : 0-11 for Jan-Dec; 12 for All Months.
		p_year	: 4-digit year
		p_format: Date format (mm/dd/yyyy, dd/mm/yy, ...)
		p_item	: Return Item.
	*/

	p_item = arguments[0];
	if (arguments[1] == null)
		p_month = new String(gNow.getMonth());
	else
		p_month = arguments[1];
	if (arguments[2] == "" || arguments[2] == null)
		p_year = new String(gNow.getFullYear().toString());
	else
		p_year = arguments[2];
	if (arguments[3] == null)
		p_format = "YYYY-MM-DD";
	else
		p_format = arguments[3];

	vWinCal = window.open("", "Calendar", 
		"width=280,height=250,status=no,resizable=no,top=200,left=400");
	vWinCal.opener = self;
	ggWinCal = vWinCal;

	fs_Build(p_item, p_month, p_year, p_format);
}
/*
Yearly Calendar Code Starts here
*/
function fs_show_yearly_calendar(p_item, p_year, p_format) {
	// Load the defaults..
	if (p_year == null || p_year == "")
		p_year = new String(gNow.getFullYear().toString());
	if (p_format == null || p_format == "")
		p_format = "YYYY-MM-DD";

	var vWinCal = window.open("", "Calendar", "scrollbars=yes");
	vWinCal.opener = self;
	ggWinCal = vWinCal;

	fs_Build(p_item, null, p_year, p_format);
}




function SourceToTarget(source, target, hiddenTarget) 
{
	if ( move(source, target) )
	{
		for (var i=0;i < target.options.length;i++)
		{
			if (target.options[i].value == -1)
				target.options[i] = null;
		}
		setHiddenValue(target,hiddenTarget);
	}
	return false;
}

function TargetToSource(source, target,hiddenTarget) 
{
	if ( move(target, source) )
	{
		if (target.options.length == 0)
		{
			
			target.options[0] = new Option("", "");
			target.options[0].text = "??";
			target.options[0].value = -1;
		}
		setHiddenValue(target,hiddenTarget);
	}
	return false;
}

function move(source, target) 
{
	var checkValue = false;
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options[i].selected) 
		{
			var len = target.options.length;
			var idValue = source.options[i].value;
			if (idValue == -1) continue;			

			var tmpNum = new Number(getOrder(idValue));
			for (var m = 0; m < target.options.length; m++) 
			{
				var tmpStr = target.options[m].value;
				var tmpNum2 = new Number(getOrder(tmpStr));
				
				if (tmpNum2 - tmpNum > 0)
				{
					break;
				}
			}
			
			
			for (var n = len; n > m; n--) {
				target.options[n] = new Option("", "");
				target.options[n].text = target.options[n-1].text;
				target.options[n].value = target.options[n-1].value;
			}
			
			target.options[m] = new Option("", "");
			target.options[m].text = source.options[i].text;
			target.options[m].value = source.options[i].value;
			
			source.options[i] = null;
			i -= 1;
			checkValue = true;
		}
	}

	if (!checkValue) 
	{
	   alert("???!");
	   source.focus();
	}
	return checkValue;
}

function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}

function getOrder(str)
{
	var tmpArray = str.split("$");
	return tmpArray[1];
}




function setHiddenValue(target,hiddenTarget)
{
	hiddenTarget.value="";
	var strTmp = "";
	for(var i=0;i<target.options.length;i++)
	{
		var tmpValue = target.options[i].value;
		if (tmpValue == -1) continue;
		strTmp = strTmp + getId(tmpValue) + ",";
	}
	
	if (strTmp.length > 0)
		strTmp = strTmp.substring(0,strTmp.length-1);
	hiddenTarget.value = strTmp;
}



function fs_createAmountCtrl(strFormName,strCtrlName,strData,strChineseCtrl,strtab,strsize)
 {
      if(strChineseCtrl == null)
 		{
 			strChineseCtrl="";
 		}
 	document.writeln(" <input  type='text'  size='"+strsize+"' tab="+strtab+"  class=tar name='"+ strCtrlName + "' value='" + fs_formatAmount( strData ) +"'  maxlength='30' onblur='fs_adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"\",1,\"" + strChineseCtrl +"\")'  onfocus='fs_adjustAmount(\"" + strFormName +"\",\"" + strCtrlName +"\",2,\"" + strChineseCtrl +"\")'> ");
 }

function fs_formatAmount(strData)
 {
 	if(fs_isInnerFloat(strData))
 	{
 		if(strData!="")
 		{
			var i,strTemp;

			
			strTemp=new String(strData);
			strData="";
			for(i=0;i<strTemp.length;i++)
			{
				var cData;
				cData=strTemp.charAt(i);
				if (cData!=",")
				{
					strData=strData+cData;
				}
			}

			
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			
			strTemp=new String(strFront);
			var bHaveMinus=false;
			if(strFront.substring(0,1)=="-")
			{
				bHaveMinus=true;
				strTemp=strTemp.substring(1,strTemp.length);
			}
			strFront="";
			var nNum;
			nNum=0;
			for(i=strTemp.length-1;i>=0;i--)
			{
				if(nNum==3)
				{
					strFront=","+strFront ;
					nNum=0;
				}
				nNum++;
				var cData;
				cData=strTemp.charAt(i);
				strFront=cData+strFront;
			}
			if(bHaveMinus)
			{
				strFront="-" + strFront;
			}
			
		
	 		if(strEnd.length>2)
	 		{
	 			strEnd=strEnd.substring(0,2);
	 		}
	 		else
	 		{
	 			if(strEnd.length==1)
	 			{
	 				strEnd=strEnd+ "0";
	 			}
	 			else
	 			{
	 				if(strEnd.length==0)
	 				{
	 					strEnd="00";
	 				}
	 			}
	 		}
	 		strData=strFront+"." + strEnd;
 		}
	}
	return strData;
 }

 function fs_isInnerFloat( d_float)
{
	if(isNaN(parseFloat(d_float)))
	  return false;
	else
	  return true;
}

function fs_adjustAmount(strFormName,strCtrlName,nType,strChineseCtrl)
{
	if (window.event.keyCode!=13)//
	{
	 	var strOldData=eval(strFormName+"."+strCtrlName+".value");
	 	
	 	if(nType==1)
	 	{
	 		  //alert(ARRAYNUM);
			      	for(i=0;i<ARRAYNUM;i++)
			      	{
			      		if (gAmount[i][0]!=null)
			      		{
			      			if(gAmount[i][0]==strCtrlName)
			      			{
			      				for(j=1;j<ARRAYNUM;j++)
			      				{
			      					if (gAmount[i][j]!=null)
			      					{
			//	 		  alert(ARRAYNUM + "-----------" + gAmount[i][j]);
			      						eval(strFormName+ "." + gAmount[i][j]+".value=''");
			      					}
			      					else
			      						break;
			      				}
			      			}
			      		}
			      		else
			      			break;
			      	}
		}
	 	if (fs_isInnerFloat(strOldData))//是否是浮点数
	 	{
	 		var strNewData="";
	 		if(nType==2)//说明是离开
	 		{
 		  		strNewData=fs_reverseFormatAmount(strOldData);
			}
			else
			{
    	 			if (strOldData=="0.00" )
    	 			{
    	 		  		strNewData = "";
    	 			}
    	 			else
    	 			{
					strNewData=fs_formatAmount(strOldData);
				}
				if(strChineseCtrl!="")
				{
					eval(strFormName + "." + strChineseCtrl + ".value='" + fs_showChinese(strOldData) +"'");
				}
			}
			/*
			* 2004/4/23，Forest将注释去掉，原因是如果注释，那么在金额控件失去焦点后，‘0.00’会变为‘’
			*/
			if(strNewData=="")//
			{
				strNewData="0.00";
			}
			//alert(" ");
			eval(strFormName + "." +strCtrlName + ".value='"+strNewData+"'");
			if(nType==2)
			{
				eval(strFormName + "." +strCtrlName + ".select()");
				
			}
	 	}
	 	else
	 	{
			if(nType==2)
			{
				eval(strFormName + "." +strCtrlName + ".value='0.00'");
				eval(strFormName + "." +strCtrlName + ".select()");
				
			}
	 		if(strChineseCtrl!="")
	 		{
	 				eval(strFormName + "." + strChineseCtrl + ".value=''");
	 		}
	 	}
	 }
 }

function fs_adjustInterestRate(strFormName,strCtrlName,nType,strChineseCtrl)
 {
	if (window.event.keyCode!=13)
	
	{
		//document.forms[strFormName].elements[strCtrlName].value
	    //var strOldData=eval(strFormName+"."+strCtrlName+".value");
	 	var strOldData="document.forms["+strFormName+"].elements["+strCtrlName+"].value";
	 	if(nType==1)
	 	{
	 		  //alert(ARRAYNUM);
			      	for(i=0;i<ARRAYNUM;i++)
			      	{
			      		if (gAmount[i][0]!=null)
			      		{
			      			if(gAmount[i][0]==strCtrlName)
			      			{
			      				for(j=1;j<ARRAYNUM;j++)
			      				{
			      					if (gAmount[i][j]!=null)
			      					{
			//	 		  alert(ARRAYNUM + "-----------" + gAmount[i][j]);
			      						//eval(strFormName+ "." + gAmount[i][j]+".value=''");
			      						eval("document.forms["+strFormName+"]." + gAmount[i][j]+".value=''");
			      					}
			      					else
			      						break;
			      				}
			      			}
			      		}
			      		else
			      			break;
			      	 }
		  }
	 	if (fs_isInnerFloat(strOldData))//是否是浮点数
	 	{
	 		var strNewData="";
	 		if(nType==2)//说明是离开
	 		{
 		  		strNewData=fs_reverseFormatAmount(strOldData);
			}
			else
			{
    	 			if (strOldData=="0.000000" )
    	 			{
    	 		  		strNewData = "";
    	 			}
    	 			else
    	 			{
						strNewData=fs_formatInterestRate(strOldData);
					}
				if(strChineseCtrl!="")
				{
					eval(strFormName + "." + strChineseCtrl + ".value='" + fs_showChinese(strOldData) +"'");
				}
			}
			/*
			if((nType==2) && (strNewData==""))//
			{
				strNewData="0.00";
			}
			*/
			eval(strFormName + "." +strCtrlName + ".value='"+strNewData+"'");
			if(nType==2)
			{
				eval(strFormName + "." +strCtrlName + ".select()");
				
			}
	 	}
	 	else
	 	{
			if(nType==2)
			{
				eval(strFormName + "." +strCtrlName + ".value='0.000000'");
				eval(strFormName + "." +strCtrlName + ".select()");
				
			}
	 		if(strChineseCtrl!="")
	 		{
	 				eval(strFormName + "." + strChineseCtrl + ".value=''");
	 		}
	 	}
	 }
 }


function fs_showChinese(sAmount)
	{
		var sResult = "";
		if (sAmount == null || sAmount=="") 
		{
				sResult = "零元";
		} 
		else 
		{
			var nPoint = sAmount.indexOf(".");
			var sLeft="";
			var sRight="";
			if (nPoint != -1) 
			{
				sLeft = sAmount.substring(0,nPoint);
				sRight = sAmount.substring(nPoint+1,sAmount.length);
			}
			else
			{
				sLeft=sAmount;
			}
			sResult = fs_getLeftOfPoint(sLeft);
			
  		}
			sResult=sResult + "整";
			return sResult;
	}
	

function fs_reverseFormatAmount(strData)
 {
		var i,strTemp;

		//去掉所有的","
		strTemp=new String(strData);
		strData="";
		for(i=0;i<strTemp.length;i++)
		{
			var cData;
			cData=strTemp.charAt(i);
			if (cData!=",")
			{
				strData=strData+cData;
			}
		}
		return strData;
 }
 
 function fs_getLeftOfPoint(nData)
	{
		var nYiNum=0;//亿位数
		var nWanNum=0;//万位数
		var nRemainNum=0;//剩余的数字
		var nThousandBelowNum=0;//千以下的数据
		var strReturn="";
		
		nRemainNum=nData;
		nYiNum=parseInt(nRemainNum)/100000000;
		if(nYiNum<1)
		{
			nYiNum=0;
		}
		nRemainNum=parseInt(nRemainNum) - parseInt(nYiNum)*100000000;
		nWanNum=parseInt(nRemainNum)/10000;
		if(nWanNum<1)
		{
			nWanNum=0;
		}
		nRemainNum=parseInt(nRemainNum)-parseInt(nWanNum)*10000;
		if(nYiNum>0)//如果亿位数>0，则处理
		{
			if(nYiNum>=10000)//如果大于1万，递归
			{
				strReturn=strReturn + getAmountLeft(nData);
			}
			else//如果小于一万，得出千以下数据
			{
				strReturn=strReturn+getThousandChineseData(nYiNum);
			}
			strReturn=strReturn + "亿";
			if(nWanNum>0 || nRemainNum >0)//说明有可能补0
			{
				if((parseInt(parseInt(nYiNum)/10))==parseInt(nYiNum)/10)//说明亿位为0
				{
					strReturn=strReturn + "零";
				}
				else//亿位不为0
				{
					if(parseInt(nWanNum)<1000)//小于一千，需要补0
					{
						strReturn=strReturn + "零";
					}
				}
			}
		}
		if(nWanNum>0)//有万
		{
			strReturn=strReturn + fs_getThousandChineseData(nWanNum);
			strReturn=strReturn + "万";
			if(nRemainNum>0)//说明有可能补0
			{
				if((parseInt(parseInt(nWanNum)/10))==parseInt(nWanNum)/10)//说明亿位为0
				{
					strReturn=strReturn + "零";
				}
				else//亿位不为0
				{
					if(parseInt(nRemainNum)<1000)//小于一千，需要补0
					{
						strReturn=strReturn + "零";
					}
				}
			}				
		}
		if(nRemainNum>0)
		{
			strReturn=strReturn + fs_getThousandChineseData(nRemainNum);
		}
		return strReturn;
	}
	
	function fs_getThousandChineseData(nData)
	{
		var strReturn="";
		if(parseInt(nData)>9999)
		{
			return "错误的千";
		}
		else
		{
			var nRemainNum=nData;//剩余的数
			var nThousandNum=0;//千位数
			var nHundredNum=0;//百位数
			var nTenNum=0;//十位数
			var nNum=0;//个位数
			nThousandNum=parseInt(parseInt(nRemainNum)/1000);//千位数
			nRemainNum=parseInt(nRemainNum)-parseInt(nThousandNum)*1000;
			nHundredNum=parseInt(parseInt(nRemainNum)/100);
			nRemainNum=parseInt(nRemainNum)-parseInt(nHundredNum)*100;
			nTenNum=parseInt(parseInt(nRemainNum)/10);
			nNum=parseInt(nRemainNum)-parseInt(nTenNum)*10;
			if(parseInt(nThousandNum)>0) 
			{
				strReturn=strReturn + CN[nThousandNum]+ "仟";
			}
			if(parseInt(nHundredNum)>0)
			{
				strReturn=strReturn + CN[nHundredNum] + "佰";
			}
			if(parseInt(nTenNum)>0)
			{
				if(nThousandNum>0 && nHundredNum==0)
				{
					strReturn=strReturn + "零";
				}
				strReturn=strReturn + CN[nTenNum] + "拾";
			}
			if(parseInt(nNum)>0)
			{
				if(nTenNum==0)
				{
					if(nThousandNum>0 || nHundredNum>0)
					{
						strReturn=strReturn + "零";
					}
				}
				strReturn=strReturn+CN[nNum];
			}
		}
		return strReturn;
	}
	
function fs_formatInterestRate(strData)
 {
 	if(fs_isInnerFloat(strData))
 	{
 		if(strData!="")
 		{
			var i,strTemp;

			//去掉所有的","
			strTemp=new String(strData);
			strData="";
			for(i=0;i<strTemp.length;i++)
			{
				var cData;
				cData=strTemp.charAt(i);
				if (cData!=",")
				{
					strData=strData+cData;
				}
			}

			//将小数点前和后的数据分别取出来
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//小数点前面的数据加","
			strTemp=new String(strFront);
			var bHaveMinus=false;
			if(strFront.substring(0,1)=="-")
			{
				bHaveMinus=true;
				strTemp=strTemp.substring(1,strTemp.length);
			}
			strFront="";
			var nNum;
			nNum=0;
			for(i=strTemp.length-1;i>=0;i--)
			{
				if(nNum==3)
				{
					strFront=","+strFront ;
					nNum=0;
				}
				nNum++;
				var cData;
				cData=strTemp.charAt(i);
				strFront=cData+strFront;
			}
			if(bHaveMinus)
			{
				strFront="-" + strFront;
			}
			
			//alert(strEnd);
			//补或者截小数点后面的值，保持6位
	 		if(strEnd.length>6)
	 		{
	 			strEnd=strEnd.substring(0,6);
	 		}
	 		else
	 		{
	 			if(strEnd.length==5)
	 			{
	 				strEnd=strEnd+ "0";
	 			}
	 			else if(strEnd.length==4)
	 			{
 					strEnd=strEnd+"00";
	 			}
	 			else if(strEnd.length==3)
	 			{
 					strEnd=strEnd+"000";
	 			}
	 			else if(strEnd.length==2)
	 			{
 					strEnd=strEnd+"0000";
	 			}
	 			else if(strEnd.length==1)
	 			{
 					strEnd=strEnd+"00000";
	 			}
	 			else if(strEnd.length==0)
	 			{
 					strEnd="000000";
	 			}																
	 		}
	 		strData=strFront+"." + strEnd;
 		}
	}
 	return strData;
 }
	
		
	
	
	
	 

 


