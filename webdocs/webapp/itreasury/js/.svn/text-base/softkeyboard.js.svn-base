//软键盘js minzhao20090512
//使用说明
//在需要使用软键盘的页面上应用本js
//在需要使用软键盘输入的文本框中加入 onfocus="chooseposition()"方法；
//在页面上增加按钮使用onclick="see(form1.loginno,submitform)"方法
//form1.loginno为默认输入框，submitform为form提交的方法


window.onload=

function()
{
password1=null;		
initCalc();
}
var inputposition;
var password1;
var CapsLockValue=0;
var checkSoftKey;
function setVariables() {
tablewidth=630;
tableheight=20;
if (navigator.appName == "Netscape") {
horz=".left";
vert=".top";
docStyle="document.";
styleDoc="";
innerW="window.innerWidth";
innerH="window.innerHeight";
offsetX="window.pageXOffset";
offsetY="window.pageYOffset";
}
else {
horz=".pixelLeft";
vert=".pixelTop";
docStyle="";
styleDoc=".style";
innerW="document.body.clientWidth";
innerH="document.body.clientHeight";
offsetX="document.body.scrollLeft";
offsetY="document.body.scrollTop";
}
}
function checkLocation() {
if (checkSoftKey) {
objectXY="softkeyboard";
var availableX=eval(innerW);
var availableY=eval(innerH);
var currentX=eval(offsetX);
var currentY=eval(offsetY);
x=availableX-tablewidth+currentX;
y=currentY;
evalMove();
}
setTimeout("checkLocation()",0);
}
function evalMove() {
eval(docStyle + objectXY + styleDoc + vert + "=" + y);
}
self.onError=null;
currentX = currentY = 0;  
whichIt = null;           
lastScrollX = 0; lastScrollY = 0;
NS = (document.layers) ? 1 : 0;
IE = (document.all) ? 1: 0;
function heartBeat() {
if(IE) { diffY = document.body.scrollTop; diffX = document.body.scrollLeft; }
if(NS) { diffY = self.pageYOffset; diffX = self.pageXOffset; }
if(diffY != lastScrollY) {
percent = .1 * (diffY - lastScrollY);
if(percent > 0) percent = Math.ceil(percent);
else percent = Math.floor(percent);
if(IE) document.all.softkeyboard.style.pixelTop += percent;
if(NS) document.softkeyboard.top += percent; 
lastScrollY = lastScrollY + percent;}
if(diffX != lastScrollX) {
percent = .1 * (diffX - lastScrollX);
if(percent > 0) percent = Math.ceil(percent);
else percent = Math.floor(percent);
if(IE) document.all.softkeyboard.style.pixelLeft += percent;
if(NS) document.softkeyboard.left += percent;
lastScrollX = lastScrollX + percent;	}		}
function checkFocus(x,y) { 
stalkerx = document.softkeyboard.pageX;
stalkery = document.softkeyboard.pageY;
stalkerwidth = document.softkeyboard.clip.width;
stalkerheight = document.softkeyboard.clip.height;
if( (x > stalkerx && x < (stalkerx+stalkerwidth)) && (y > stalkery && y < (stalkery+stalkerheight))) return true;
else return false;}
function grabIt(e) {
checkSoftKey = false;
if(IE) {
whichIt = event.srcElement;
while (whichIt.id!=null&&whichIt.id.indexOf("softkeyboard") == -1) {
whichIt = whichIt.parentElement;
if (whichIt == null) { return true; } }
if(whichIt.style!=null){
whichIt.style.pixelLeft = whichIt.offsetLeft;
whichIt.style.pixelTop = whichIt.offsetTop;
}
currentX = (event.clientX + document.body.scrollLeft);
currentY = (event.clientY + document.body.scrollTop); 	
} else { 
window.captureEvents(Event.MOUSEMOVE);
if(checkFocus (e.pageX,e.pageY)) { 
whichIt = document.softkeyboard;
StalkerTouchedX = e.pageX-document.softkeyboard.pageX;
StalkerTouchedY = e.pageY-document.softkeyboard.pageY;} }
return true;	}
function moveIt(e) {
if (whichIt == null) { return false; }
if(IE) {
if(whichIt.style!=null){
newX = (event.clientX + document.body.scrollLeft);
newY = (event.clientY + document.body.scrollTop);
distanceX = (newX - currentX);    distanceY = (newY - currentY);
currentX = newX;    currentY = newY;
whichIt.style.pixelLeft += distanceX;
whichIt.style.pixelTop += distanceY;
if(whichIt.style.pixelTop < document.body.scrollTop) whichIt.style.pixelTop = document.body.scrollTop;
if(whichIt.style.pixelLeft < document.body.scrollLeft) whichIt.style.pixelLeft = document.body.scrollLeft;
if(whichIt.style.pixelLeft > document.body.offsetWidth - document.body.scrollLeft - whichIt.style.pixelWidth - 20) whichIt.style.pixelLeft = document.body.offsetWidth - whichIt.style.pixelWidth - 20;
if(whichIt.style.pixelTop > document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5) whichIt.style.pixelTop = document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5;
event.returnValue = false;
}
} else { 
whichIt.moveTo(e.pageX-StalkerTouchedX,e.pageY-StalkerTouchedY);
if(whichIt.left < 0+self.pageXOffset) whichIt.left = 0+self.pageXOffset;
if(whichIt.top < 0+self.pageYOffset) whichIt.top = 0+self.pageYOffset;
if( (whichIt.left + whichIt.clip.width) >= (window.innerWidth+self.pageXOffset-17)) whichIt.left = ((window.innerWidth+self.pageXOffset)-whichIt.clip.width)-17;
if( (whichIt.top + whichIt.clip.height) >= (window.innerHeight+self.pageYOffset-17)) whichIt.top = ((window.innerHeight+self.pageYOffset)-whichIt.clip.height)-17;
return false;}
return false;	}
function dropIt() {whichIt = null;
if(NS) window.releaseEvents (Event.MOUSEMOVE);
return true;	}
if(NS) {window.captureEvents(Event.MOUSEUP|Event.MOUSEDOWN);
window.onmousedown = grabIt;
window.onmousemove = moveIt;
window.onmouseup = dropIt;	}
if(IE) {
document.onmousedown = grabIt;
document.onmousemove = moveIt;
document.onmouseup = dropIt;	}
var style1="<style>";
style1+=".btn_letter {BORDER-RIGHT: 1px solid; PADDING-RIGHT: 1px; BORDER-TOP: 1px solid; PADDING-LEFT: 2px;FONT-SIZE: 14px; BORDER-LEFT: 1px solid; CURSOR: hand;PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; width:25px; height:20px;}";
style1+=".btn_num {width:25px;BORDER-RIGHT:1px solid; PADDING-RIGHT: 1px; BORDER-TOP: 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; BORDER-LEFT: 1px solid; CURSOR: hand; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid;height:20px;}";
style1+=".table_title {FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,StartColorStr=#B2DEF7, EndColorStr=#7AB7DA); height:26px;padding-top: 3px;}";
style1+=".btn_input {BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid}";
style1+=".btn_input1 {BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 13px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid; width:25px;}";
style1+=".btn_num1 {BORDER-RIGHT: #7ebf4f 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7ebf4f 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 13px;FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#B3D997); BORDER-LEFT: #7ebf4f 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7ebf4f 1px solid; width:25px;}";
style1+="</style>";	
document.write(style1);																																																			
document.write("<DIV align=center id=\"softkeyboard\" name=\"softkeyboard\" style=\"position:absolute; left:0px; top:0px; width:400px; z-index:180;display:none\"><table id=\"CalcTable\" width=\"\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"\"><FORM id=Calc name=Calc action=\"\" method=post autocomplete=\"off\"><tr><td name=\"mytd\" class=\"table_title\"  align=\"right\" valign=\"middle\" bgcolor=\"\" style=\"cursor: default;height:19\"><INPUT type=hidden value=\"\" name=password><INPUT type=hidden value=ok name=action2>&nbsp<font style=\"font-weight:bold; font-size:15px; color:#075BC3\">软键盘</font>&nbsp&nbsp&nbsp&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;<INPUT id=useKey class=\"btn_input\" type=button value=\"使用键盘输入\" bgtype=\"1\" onclick=\"password1.readOnly=0;password1.focus();closekeyboard();\"><span style=\"width:2px;\"></span></td></tr><tr align=\"center\"><td align=\"center\" bgcolor=\"#FFFFFF\"><table align=\"center\" width=\"%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\n<tr align=\"left\" valign=\"middle\"> \n<td> <input name=\"randombutton\"  type=button value=\" ~ \"></td>\n<td> <input name=\"randombutton\" type=button value=\" ! \"></td>\n<td> <input name=\"randombutton\" type=button value=\" @ \"></td>\n<td> <input name=\"randombutton\" type=button value=\" # \"></td>\n<td> <input name=\"randombutton\" type=button value=\" $ \"></td>\n<td><input name=\"randombutton\" type=button value=\" % \"></td>\n<td><input name=\"randombutton\" type=button value=\" ^ \"></td>\n<td> <input name=\"randombutton\" type=button value=\" & \"></td>\n<td><input name=\"randombutton\" type=button value=\" * \"></td>\n<td><input name=\"randombutton\" type=button value=\" ( \"></td>\n<td><input name=\"randombutton\" type=button value=\" ) \"></td>\n<td><input name=\"randombutton\" type=button value=\" _ \"></td>\n<td> <input name=\"randombutton\" type=button value=\" + \"></td>\n<td><input name=\"randombutton\" type=button value=\" | \"></td>\n<td colspan=\"1\" rowspan=\"2\"> <input name=\"button10\" type=button value=\" 退格\" onclick=\"setpassvalue();\"  onDblClick=\"setpassvalue();\" style=\"width:100px;height:45px\"> \n</td>\n</tr>\n<tr align=\"left\" valign=\"middle\"> \n<td><input name=\"randombutton\" type=button value=\" ` \"></td>\n<td><input type=button bgtype=\"2\" name=\"button_number1\" value=\" 1 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number2\" value=\" 2 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number3\" value=\" 3 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number4\" value=\" 4 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number5\" value=\" 5 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number6\" value=\" 6 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number7\" value=\" 7 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number8\" value=\" 8 \"></td>\n<td> <input type=button bgtype=\"2\" name=\"button_number9\" value=\" 9 \"></td>\n<td> <input bgtype=\"2\"  name=\"button_number0\" type=button value=\" 0 \"></td>\n<td> <input name=\"randombutton\" type=button value=\" - \"></td>\n<td> <input name=\"randombutton\" type=button value=\" = \"></td>\n<td> <input name=\"randombutton\" type=button value=\" \\ \"></td>\n<td> </td>\n</tr>\n<tr align=\"left\" valign=\"middle\"> \n<td> <input name=\"randombutton\" type=button value=\" q \"></td>\n<td> <input name=\"randombutton\" type=button value=\" w \"></td>\n<td> <input name=\"randombutton\" type=button value=\" e \"></td>\n<td> <input name=\"randombutton\" type=button value=\" r \"></td>\n<td> <input name=\"randombutton\" type=button value=\" t \"></td>\n<td> <input name=\"randombutton\" type=button value=\" y \"></td>\n<td> <input name=\"randombutton\" type=button value=\" u \"></td>\n<td> <input name=\"randombutton\" type=button value=\" i \"></td>\n<td> <input name=\"randombutton\" type=button value=\" o \"></td>\n<td> <input name=\"randombutton\" type=button value=\" p \"></td>\n<td> <input name=\"randombutton\"  type=button value=\" { \"></td>\n<td> <input name=\"randombutton\" type=button value=\" } \"></td>\n<td> <input name=\"randombutton\" type=button value=\" [ \"></td>\n<td> <input name=\"randombutton\" type=button value=\" ] \"></td>\n<td><input name=\"button9\" type=button onClick=\"capsLockText();setCapsLock();\"  onDblClick=\"capsLockText();setCapsLock();\" value=\"切换大/小写\" style=\"width:100px;\"></td>\n</tr>\n<tr align=\"left\" valign=\"middle\"> \n<td> <input name=\"randombutton\" type=button value=\" a \"></td>\n<td> <input name=\"randombutton\" type=button value=\" s \"></td>\n<td> <input name=\"randombutton\" type=button value=\" d \"></td>\n<td> <input name=\"randombutton\" type=button value=\" f \"></td>\n<td> <input name=\"randombutton\" type=button value=\" g \"></td>\n<td> <input name=\"randombutton\" type=button value=\" h \"></td>\n<td> <input name=\"randombutton\" type=button value=\" j \"></td>\n<td> <input name=\"randombutton\" type=button value=\" k \"></td>\n<td> <input name=\"randombutton\" type=button value=\" l \"></td>\n<td> <input name=\"randombutton\" type=button value=\" : \"></td>\n<td> <input name=\"randombutton\"  type=button value=\" &quot; \"></td>\n<td> <input name=\"randombutton\" type=button value=\" ; \"></td>\n<td> <input name=\"randombutton\" type=button value=\" ' \"></td>\n<td rowspan=\"2\" colspan=\"2\"> <input name=\"button12\" type=button onclick=\"OverInput();\" value=\"   确定  \" style=\"width:126px;height:45px;\"></td>\n</tr>\n<tr align=\"left\" valign=\"middle\"> \n<td><input name=\"randombutton\"  type=button value=\" z \"></td>\n<td> <input name=\"randombutton\" type=button value=\" x \"></td>\n<td> <input name=\"randombutton\" type=button value=\" c \"></td>\n<td> <input name=\"randombutton\" type=button value=\" v \"></td>\n<td> <input name=\"randombutton\" type=button value=\" b \"></td>\n<td> <input name=\"randombutton\" type=button value=\" n \"></td>\n<td> <input name=\"randombutton\" type=button value=\" m \"></td>\n<td> <input name=\"randombutton\" type=button value=\" &lt; \"></td>\n<td> <input name=\"randombutton\" type=button value=\" &gt; \"></td>\n<td> <input name=\"randombutton\" type=button value=\" ? \"></td>\n<td> <input name=\"randombutton\" type=button value=\" , \"></td>\n <td> <input name=\"randombutton\" type=button value=\" . \"></td>\n <td> <input name=\"randombutton\" type=button value=\" / \"></td>\n</tr>\n</table></td></FORM></tr></table></DIV>");

function addValue(newValue)
{
if (CapsLockValue==0)
{
var str=Calc.password.value;
if(str.length<password1.maxLength)
{
Calc.password.value += newValue;
}			
if(str.length<=password1.maxLength)
{
//password1.value=Calc.password.value;
password1.value+=newValue;
}
}
else
{
var str=Calc.password.value;
if(str.length<password1.maxLength)
{
Calc.password.value += newValue;
}
if(str.length<=password1.maxLength)
{
//password1.value=Calc.password.value;
password1.value+=newValue;
}
}
}

function setpassvalue()
{
//var longnum=Calc.password.value.length;
//var num
//num=Calc.password.value.substr(0,longnum-1);
//Calc.password.value=num;
//var str=Calc.password.value;
//password1.value=Calc.password.value;


var longnum=password1.value.length;
var num;
num=password1.value.substr(0,longnum-1);
password1.value=num;
}

function OverInput()
{
var str=Calc.password.value;
password1.value=Calc.password.value;
closekeyboard();
Calc.password.value="";
password1.readOnly=0;
}

function closekeyboard(theForm)
{
softkeyboard.style.display="none";
if(null!=unhideSelect){
unhideSelect();
}
}

function showkeyboard()
{
randomNumberButton();
var th = password1;
var ttop  = th.offsetTop;
var thei  = th.clientHeight;
var tleft = th.offsetLeft;
var ttyp  = th.type;
while (th = th.offsetParent){ttop+=th.offsetTop; tleft+=th.offsetLeft;}
softkeyboard.style.top  = ttop+thei+75;
softkeyboard.style.left = tleft-150;
softkeyboard.style.display="block";
password1.readOnly=1;
password1.blur();
document.all.useKey.focus();
if(null!=hideSelect){
hideSelect();
}
}

function setCapsLock()
{
if (CapsLockValue==0)
{
CapsLockValue=1
}
else 
{
CapsLockValue=0
}
}
function setCalcborder()
{
CalcTable.style.border="1px solid #999999"
}
function setHead()
{
CalcTable.cells[0].style.backgroundColor="#999999"	
}
function setCalcButtonBg()
{
for(var i=0;i<Calc.elements.length;i++)
{
if(Calc.elements[i].type=="button"&&Calc.elements[i].bgtype!="1")
{
if(Calc.elements[i].bgtype=="2"){
Calc.elements[i].className="btn_num1";
}else{
Calc.elements[i].className="btn_input1";
}
var str1=Calc.elements[i].value;
str1=str1.trim();
if(str1.length==1)
{
}
var thisButtonValue=Calc.elements[i].value;
thisButtonValue=thisButtonValue.trim();
if(thisButtonValue.length==1)
{
Calc.elements[i].onclick=
function ()
{
var thisButtonValue=this.value;
thisButtonValue=thisButtonValue.trim();
thisButtonValue=jiamiMimaKey(thisButtonValue);
addValue(thisButtonValue);
}
Calc.elements[i].ondblclick=
function ()
{
var thisButtonValue=this.value;
thisButtonValue=thisButtonValue.trim();
thisButtonValue=jiamiMimaKey(thisButtonValue);
addValue(thisButtonValue);
}
}
}
}
}
function initCalc()
{
setCalcborder();
setHead();
setCalcButtonBg();
}
String.prototype.trim = function()
{
return this.replace(/(^\s*)|(\s*$)/g, "");
}
var capsLockFlag;
capsLockFlag=true;
function capsLockText()
{
if(capsLockFlag)
{
for(var i=0;i<Calc.elements.length;i++)
{
var char=Calc.elements[i].value;
var char=char.trim()
if(Calc.elements[i].type=="button"&&char>="a"&&char<="z"&&char.length==1)
{
Calc.elements[i].value=" "+String.fromCharCode(char.charCodeAt(0)-32)+" "
}
}
}
else
{
for(var i=0;i<Calc.elements.length;i++)
{
var char=Calc.elements[i].value;
var char=char.trim()
if(Calc.elements[i].type=="button"&&char>="A"&&char<="Z"&&char.length==1)
{
Calc.elements[i].value=" "+String.fromCharCode(char.charCodeAt(0)+32)+" "
}
}
}
capsLockFlag=!capsLockFlag;
}

function randomNumberButton(){
var a = new Array(10);  
a[0]=0;a[1]=1;a[2]=2;a[3]=3;a[4]=4;a[5]=5;a[6]=6;a[7]=7;a[8]=8;a[9]=9;
var randomNum;
var times=10;
for(var i=0;i<10;i++){
randomNum = parseInt(Math.random()*10);
var tmp=a[0];
a[0]=a[randomNum];
a[randomNum]=tmp;
}
Calc.button_number0.value=" "+a[0]+" ";
Calc.button_number1.value=" "+a[1]+" ";
Calc.button_number2.value=" "+a[2]+" ";
Calc.button_number3.value=" "+a[3]+" ";
Calc.button_number4.value=" "+a[4]+" ";
Calc.button_number5.value=" "+a[5]+" ";
Calc.button_number6.value=" "+a[6]+" ";
Calc.button_number7.value=" "+a[7]+" ";
Calc.button_number8.value=" "+a[8]+" ";
Calc.button_number9.value=" "+a[9]+" ";

var b=new Array("~","!","@","#","$","%","^","&","*","(",")","_","+","|","`","-","=","\\","q","w","e","r","t","y","u","i","o","p","{","}","[","]","a","s","d","f","g","h","j","k","l",":","\"",";","\'","z","x","c","v","b","n","m","<",">","?",",",".","/"); 
for(var j=0;j<58;j++){
randomNum = parseInt(Math.random()*10);
var tmp=b[j];
b[j]=b[randomNum];
b[randomNum]=tmp;
}

var c=document.getElementsByName("randombutton");
for(var k=0;k<c.length;k++)
{
	c.item(k).value=b[k];
	
}


}

function hideSelect(){
var i=0;
while(i<document.getElementsByTagName("select").length){
document.getElementsByTagName("select")[i].style.visibility = "hidden";
i=i+1;
}
}

function unhideSelect(){
var i=0;
while(i<document.getElementsByTagName("select").length){
document.getElementsByTagName("select")[i].style.visibility = "visible";
i=i+1;
}
}


function  jiamiMimaKey(newValue) {
	if (typeof(b) == "undefined" || typeof(ifUseYinshe) == "undefined" || ifUseYinshe == 0) {return newValue;}
	var everyone = '';
	var afterPass = '';
	for (var i=0;i<newValue.length;i++ ) {
			everyone = newValue.charAt(i);
			for (var j =0;j<((b.length)/2);j++) {
					if (everyone == b[2*j]) {
							afterPass = afterPass + b[2*j+1];
							break;
					}
			}
		}
		newValue= afterPass;
		jiami = 1;
		return afterPass;
}

function see(inputvalue)
{
	if(inputposition==null)
	{
		inputposition=inputvalue;
	}
   if(softkeyboard.style.display=="none")
	{
		password1=inputposition;showkeyboard();inputposition.readOnly=0;Calc.password.value='';
		Calc.button12.onclick=closekeyboard;
	}
	else
	{
		password1.readOnly=0;password1.focus();closekeyboard();
		
	}
}

	function chooseposition(frm)
	{
		
		inputposition=document.activeElement;
		password1=inputposition;
		inputposition.readOnly=0;
		Calc.password.value='';
	}
	
	document.onclick = function()
	{
		if(document.activeElement.name==undefined||document.activeElement.name=="loginno"||document.activeElement.name=="checkcode"||document.activeElement.name=="Submit3"||document.activeElement.name=="Submit22"||document.activeElement.name=="UserName"||document.activeElement.name=="Password"||document.activeElement.name=="Submit1"||document.activeElement.name=="Submit2")
		{
			closekeyboard();
		}
	}

