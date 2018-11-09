
function SetRadioValue(r1,sValue){
r1.value=sValue;
for(var i=1;i<r1.childNodes.length;i++){
if(typeof r1.childNodes(i).tagName!="undefined"){
if(r1.childNodes(i).tagName.toUpperCase()=="INPUT"){
if(r1.childNodes(i).value==r1.value){
r1.childNodes(i).checked=true;
break;
}
}
}
}
}
function SetCheckBoxValue(obj,sValue){
if(obj.truevalue == sValue){
obj.children[0].checked=true;
}else{
obj.children[0].checked=false;
}
obj.value=sValue;
}
function fillcombox(sSql,callback,context) {
if(typeof sSql == "undefined"  || sSql == "undefined" ) { return "" ;}
sSql=RepOpenSql(sSql);
var sql1="";
for(var i=0;i<sSql.length;i++) {
switch (sSql.charAt(i)) {
case "<" :
sql1=sql1+"&lt;";
break;
case ">" :
sql1=sql1+"&gt;";
break;
default:
sql1=sql1+sSql.charAt(i);
}
}
var sXml="<No>"+sql1+"</No>";

var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?fillcombox",sXml,callback,context);
return retX;
}
function SelectAddOption(obj,sHtml) {
var ss=obj.outerHTML;
return ss.substring(0,ss.length-9)+sHtml+"</select>";
}
function sqlcombox(obj,sql){
if(typeof obj != "object") obj=eval(obj);
if(isSpace(sql)==false)obj.sql=sql;
if(isSpace(obj.sql)==false){
var s1 =fillcombox(obj.sql);
if(isSpace(s1)==false){
obj.outerHTML=SelectAddOption(obj,s1);
}
}
}
function SqlCombo(obj,sql){sqlcombox(obj,sql);}
function SetComboText(obj,sText) {
l=obj.options.length ;
for(var i=0;i<l;i++){
if(obj.options(i).text == sText){
obj.selectedIndex = i;
return
}
}
}
function DivRadioInitLoad(obj){
var strsql=UnSqlPropTrans(obj.sqltrans) ;
var strReturn=SelectSql(strsql,1,100);
var sfont=obj.style.fontSize ;
var sfontstyle=obj.style.fontStyle;
var sfontweight=obj.style.fontWeight;
var sColor=obj.style.color;
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML (strReturn);
var sLen=oXml.documentElement.childNodes.length;
if (sLen>0){
var strX="<table border=0 width='100%' style='font-size:"+sfont+"; font-Style:"+sfontstyle+"; font-Weight:"+sfontweight+"; color:"+sColor+"'><tr>";
var j=0;
var s=obj.rows-1;
for (var i=0;i<sLen-1;i++){
sdwid=oXml.documentElement.childNodes(i).childNodes(0).text;
sdwname=oXml.documentElement.childNodes(i).childNodes(1).text;
strX=strX+"<td><input type='radio' name='radio' value='"+sdwid+"' text='"+sdwname+"'>"+sdwname+"</td>";
j=j+1;
if(j>s){
strX=strX+"</tr><tr>";
j=0;
}
}
strX=strX+"</tr></table>";
}
obj.innerHTML=strX;
}
function DivCheckBoxInitLoad(obj){
var strsql=UnSqlPropTrans(obj.sqltrans) ;
var strReturn=SelectSql(strsql,1,100);
var sfontsize=obj.style.fontSize ;
var sfontstyle=obj.style.fontStyle ;
var sfontweight=obj.style.fontWeight;
var sColor=obj.style.color; 
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML (strReturn);
var sLen=oXml.documentElement.childNodes.length;
if (sLen>0){
var strX="<table border=0 width='100%' style='font-size:"+sfontsize+"; font-style:"+sfontstyle+" ; font-weight:"+sfontweight+"; color:"+sColor+"'><tr>";
var j=0;
var s=obj.rows-1;
for (var i=0;i<sLen-1;i++){
sdwid=oXml.documentElement.childNodes(i).childNodes(0).text;
sdwname=oXml.documentElement.childNodes(i).childNodes(1).text;
strX=strX+"<td><input type='checkbox' id='check' value='"+sdwid+"' text='"+sdwname+"'>"+sdwname+"</td>";
j=j+1;
if(j>s){
strX=strX+"</tr><tr>";
j=0;
}
}
strX=strX+"</tr></table>";
}
obj.innerHTML=strX;
}
function GetDivRadioValue(obj) {
var t=obj.children[0];
for (var i=0;i<t.rows.length;i++){
for (var j=0;j<t.rows(i).cells.length;j++){
if (t.rows(i).cells(j).childNodes(0).checked){
return t.rows(i).cells(j).childNodes(0).text ;
}
}
}
return "";
}
function GetDivCheckBoxValue(obj) {
var strX="";
var t=obj.children[0] ;     
for (var i=0;i<t.rows.length;i++){         
for (var j=0;j<t.rows(i).cells.length;j++){             
if (t.rows(i).cells(j).childNodes(0).checked){             
strX= strX + t.rows(i).cells(j).childNodes(0).text +"," ;
}		 
}     
}     
var ll=strX.length ;
strX=strX.substring(0,ll-1);
return strX;
}
function SetDivRadioValue(obj,vValue) {
var strX="";
var t=obj.children[0] ;     
for (var i=0;i<t.rows.length;i++){         
for (var j=0;j<t.rows(i).cells.length;j++){             
if(t.rows(i).cells(j).childNodes(0).text==vValue){
t.rows(i).cells(j).childNodes(0).checked=true ;			
}
}     
}
}
function SetDivCheckBoxValue(obj,strX) {
if(typeof strX == "undefined" || strX == "") return ;
var arr = strX.split(",");
var t=obj.children[0] ;     
for (var i=0;i<t.rows.length;i++){         
for (var j=0;j<t.rows(i).cells.length;j++){ 
for (var k=0; k<arr.length; k++){
if(t.rows(i).cells(j).childNodes(0).text==arr[k]){
t.rows(i).cells(j).childNodes(0).checked=true ;	
break ;	
}
}
}     
}
}