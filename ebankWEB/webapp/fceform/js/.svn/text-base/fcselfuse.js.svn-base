function RepHtml(sour,sfind,srep,endstr){
var findlen=sfind.length;
var ipos=sour.indexOf(sfind);
if(ipos>=0){
var ipos1=sour.indexOf(endstr,ipos);
sour=sour.substring(0,ipos)+srep+sour.substring(ipos1+endstr.length,sour.length);
}
return sour;
}
function GetAllDjFileName(sPath) {
if(typeof sPath == "undefined") sPath = "" ;

return SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/PathFile"+fcpubdata.dotnetVersion+"?GetAllDjFileName","<vpath>"+fcpubdata.Path+"</vpath>"+"<subpath>"+sPath+"</subpath>");
}
function GetBillType() {
var oXml = SetDomFile(fcpubdata.Path+"/fceform/billtype.xml");
var sRet = "" ;
if(oXml.documentElement != null) {
var l = oXml.documentElement.childNodes.length-1;
for(var i=0;i<l ; i++) {
var stext = oXml.documentElement.childNodes(i).childNodes(1).text;
var svalue = oXml.documentElement.childNodes(i).childNodes(2).text;
var spath = oXml.documentElement.childNodes(i).childNodes(3).text;
var sextname = oXml.documentElement.childNodes(i).childNodes(4).text;
sRet += "<option value=\""+svalue+"\" extname=\""+sextname+"\" path=\""+spath+"\">"+stext+"</option>";
}
}	
return sRet;
}
function BillTypeNameToPath(name) {
var oXml = SetDomFile(fcpubdata.Path+"/fceform/billtype.xml");
var oRet = Object() ;
if(oXml.documentElement != null) {
var l = oXml.documentElement.childNodes.length-1;
for(var i=0;i<l ; i++) {
var svalue = oXml.documentElement.childNodes(i).childNodes(2).text;
var spath = oXml.documentElement.childNodes(i).childNodes(3).text;
var extname = oXml.documentElement.childNodes(i).childNodes(4).text;
if(svalue == name) {
oRet.path = spath ;
oRet.extname = extname ;
break;
}
}
}	
return oRet;
}
function contselfield(arr) {
if(arr.length == 0 ){
alert("请增加了数据集后再试!") ;
return ;
}
var sRet=DjOpen('fieldsel',arr,'展现','有模式窗口','直接','选择字段');
if(typeof sRet!="undefined"){
var arr1=sRet.split(",");
return arr1;
}
}
function SelColor(){
var sInitColor = displayfont.style.backgroundColor;
var sColor;
if (isSpace(sInitColor ))
sColor = dlgHelper.ChooseColorDlg();
else
sColor = dlgHelper.ChooseColorDlg(sInitColor);
sColor = sColor.toString(16);
if (sColor.length < 6) {
var sTempString = "000000".substring(0,6-sColor.length);
sColor = sTempString.concat(sColor);
}
displayfont.style.backgroundColor=sColor;
}
function SelFgColor() {
var sInitColor = displayfont.style.color;
var sColor;
if (isSpace(sInitColor ))
sColor = dlgHelper.ChooseColorDlg();
else
sColor = dlgHelper.ChooseColorDlg(sInitColor);
sColor = sColor.toString(16);
if (sColor.length < 6) {
var sTempString = "000000".substring(0,6-sColor.length);
sColor = sTempString.concat(sColor);
}
displayfont.style.color=sColor;
}
function SelFunction(obj){
DjOpen('selfunc',obj,'展现','有模式窗口','直接','选择函数');
}
function GridChangeRow(up,ogrid1){
var ogrid;
if(typeof ogrid1 == "undefined"){
ogrid=SKBILLgrid1;
}else{
ogrid=ogrid1;
}
var ods=eval(ogrid.dataset);
if ((up && ods.RecNo==0) || (up==false && ods.RecNo>=ods.RecordCount-1)) return;
var oP=ods.oDom.documentElement;
var oNode,oNode1;
if(up){
if(ogrid.TopRow>0 && ogrid.Row==ogrid.TopRow+1){
ogrid.VscrollTo(ogrid.TopRow-1);
}
oNode=oP.childNodes(ods.RecNo).cloneNode(true);
oNode1=oP.childNodes(ods.RecNo-1).cloneNode(true);
oP.replaceChild(oNode1,oP.childNodes(ods.RecNo));
oP.replaceChild(oNode,oP.childNodes(ods.RecNo-1));
}else{
if(ogrid.Vmax>0 && ogrid.TopRow<ogrid.Vmax && ogrid.Row>ogrid.TopRow+1){
ogrid.VscrollTo(ogrid.TopRow+1);
}
oNode=oP.childNodes(ods.RecNo).cloneNode(true);
oNode1=oP.childNodes(ods.RecNo+1).cloneNode(true);
oP.replaceChild(oNode1,oP.childNodes(ods.RecNo));
oP.replaceChild(oNode,oP.childNodes(ods.RecNo+1));
}
if(up){
ogrid.tab.rows(ods.RecNo+1).swapNode(ogrid.tab.rows(ods.RecNo))	;
ods.RecNo--;
}else{
ogrid.tab.rows(ods.RecNo+1).swapNode(ogrid.tab.rows(ods.RecNo+2));	
ods.RecNo++;
}
}
function BillEventHeadOpen(svalue,obj){
var s=svalue ;
var ilen=14 ;
if(typeof s == "function"){
var s=s.toString();
ilen=BillEventHeadOpenTmp(s);
obj.value=s.substring(ilen+23,s.length-4);
}else{
if(IsSpace(s)){
obj.value="";
}else{
ilen=BillEventHeadOpenTmp(s);
obj.value=s.substring(ilen,s.length-2) ;
}
}
}
function BillEventHeadOpenTmp(s) {
var iRet=14;
if(s.indexOf("bill_ondblclick")>=0){
iRet=17;
}else if (s.indexOf("bill_onexit")>=0){
iRet=13;
}else if (s.indexOf("bill_onkeydown")>=0){
iRet=16;
}	
return iRet;
}
function SetPasswordEdit(SKDBedit2) {
var s1=SKDBedit2.outerHTML;
var sRet=fc_RepStr(s1,'<INPUT ','<INPUT type=password ');
SKDBedit2.outerHTML=sRet;
}
function SaveFile(sFile,sHtml){
try{
var fso = new ActiveXObject("Scripting.FileSystemObject");
}catch(e){
alert("因当前IE禁止运行ActiveX控件,请调低IE的安全属性后再运行此功能!");
return;
}
var char1=unescape("%5C"); 
var sFile1=fc_RepStr(sFile,char1,char1+char1);
try{
var a = fso.CreateTextFile(sFile1, false);
}catch(e){
alert("文件"+sFile+"已存在.");
return;
}
var s1="" ;
if(typeof sHtml == "undefined" ){
s1=t.outerHTML;
}else {
s1=sHtml;
}
a.WriteLine(s1);
a.Close();
alert("文件成功保存到: "+sFile);
}
function loadhtml(sql,djsn,typePath,extname,callback,context) {
var sXml="<No>"+RepXml(sql)+"</No>"+"<No>"+djsn+"</No>"+"<No>"+fcpubdata.databaseTypeName+"</No>"+"<No>"+fcpubdata.Path+"</No>"+"<typepath>"+typePath+"</typepath>"+"<extname>"+extname+"</extname>";

return SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?loadhtml",sXml,callback,context);
}
function CheckContSameName(oContXml,sid,obj,SKbillsheet) {
var oldid = obj.id;
sid = trim(sid);
var re = /\W/g ; 
if( re.test(sid) ) {
sRet = "控件名称只能为a-z或A-Z或0-9或_";
return sRet;
}
var sRet = "" ;
var oList = oContXml.documentElement.selectNodes("//id[. ='"+sid+"']") ;
if(oList.length > 0 ) {
sRet = sid+ "控件名称重复! 请另外输入一个名称! " ;
} else if (oList.length <= 0 ){		
var oNode = oContXml.documentElement.selectSingleNode("//id[. ='"+oldid+"']") ;
if(oNode != null) {
oNode.text = sid ;
obj.id = sid ;
SKbillsheet.ownerDocument.parentWindow.parent.objlist.execScript("objlist_edit('"+oldid+"','"+sid+"')");
}
var sxml = SKbillsheet.billtaborder;
if(isSpace(sxml) == false ){
var oXml = SetDom(sxml);
var oNode = oXml.documentElement.selectSingleNode("//taborder[. ='"+oldid+"']") ;
if( oNode  != null ) { 
oNode.text = sid ;
SKbillsheet.billtaborder = oXml.documentElement.xml;
}
}
}
return sRet;
}
function SetTextValue(oP,oCont){
if(IsSpace(oP)){
oCont.value="";
}else{
oCont.value=oP;
}
}
function SetCheckBoxPutValue(oP,oCont){
if(IsSpace(oP)){
SetCheckBoxValue(oCont,"否");
}else{
SetCheckBoxValue(oCont,oP);
}
}
function SetPosOnChange(oCont,pos) {
var obj = event.srcElement;
if(obj.value == txtLeft.value) {
oCont.style.left=obj.value ;
}
if(obj.value == txtTop.value) {
oCont.style.top=obj.value;
}
if(obj.value == txtWidth.value) {
oCont.style.width=obj.value ;
}
if(obj.value == txtHeight.value ) {
oCont.style.height=obj.value ;
}
}
function RepDqMarks(obj) {
if(obj.value.indexOf('"') != -1)
return false;
}
function IsCheckDataField(obj1,obj2) {
if(IsSpace(obj1.value) == false && IsSpace(obj2.value) == true || IsSpace(obj1.value) == true && IsSpace(obj2.value) == false) 
return false;
}