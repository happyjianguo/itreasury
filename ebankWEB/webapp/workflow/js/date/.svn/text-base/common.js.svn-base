//var g_bIsIE = (navigator.appVersion.indexOf("MSIE") > -1) ? true : false;
var g_ctrlSelected;
var g_myObject=new Object();
function Container_onclick( objMe,bTimeAvailable)
{
	var hidHeight;
	g_ctrlSelected=objMe;
	g_myObject.WindowRef=window;
	g_myObject.TimeAvailable=bTimeAvailable;
	if(bTimeAvailable == true)
	{
		hidHeight = 300;
	}
	else
	{
		hidHeight = 250;
	}
//window.alert(bTimeAvailable);
	var strFeatures = " center: Yes; help: No; resizable: No; status: No;dialogWidth=210px;dialogHeight="+hidHeight+"px" ;
	//????????????????
	window.showModelessDialog( "/NASApp/iTreasury-ebank/workflow/js/date/DateTimeDialog.htm", g_myObject,strFeatures);
}

function setDateFieldValue( NewValue )
{
	g_ctrlSelected.value = NewValue;
}

function getDateFieldValue( )
{
	return g_ctrlSelected.value;
}
