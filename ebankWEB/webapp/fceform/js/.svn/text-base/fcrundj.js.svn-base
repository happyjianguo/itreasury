
function SetPara(paraname,vValue){
SaveUserData("pub",paraname,vValue);
}
function GetPara(paraname){
return LoadUserData("pub",paraname ) ;
}
function SetParaPub(vValue){
SaveUserData("pub","pub",vValue) ;	
}
function GetParaPub(){
return LoadUserData("pub","pub" ) ;
}
function LoadMod(sKey1,sclass,sXml){
if(arguments.length==0 || isSpace(sKey1) ) return;
var sKey=trim(sKey1);
var blnRemove=false;  
if(sKey.length>2){
if(sKey.substring(sKey.length-2,sKey.length)=="()"){
sKey=sKey.substring(0,sKey.length-2);
blnRemove=true;
}
}
if(sclass=="click"){
var curO=event.srcElement;
}
if(sclass=="gridclick"){
var curO=event.srcElement;
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML (sXml);
var ogrid=eval(oXml.documentElement.tagName);
var curcol=ogrid.curTD.cellIndex  ;
if(curcol>0 && oXml.documentElement.childNodes.length>0) 
sKey=oXml.documentElement.childNodes(curcol-1).text;
else
return	;
}
if(sclass=="grid"){
var ogrid=eval(sXml);
var grid_ds=eval("window."+ogrid.dataset);
try {
var curO=event.srcElement;
}catch (e) {}
}
switch (sKey) {
case "mytest" :   
dssub1.MoveFirst();
break;
case "deltmptab" :   
DelTmpTab();
break;
case "exportto" :   
ExportToTxt();
break;
case "printdesign" :   
PrintDesign();
break;
case "printpreview" :   
PrintPreview();
break;
case "delrow" :   
DelRow();
break;
case "gridappend" :   
GridAppend();
break;
case "gridupdate" :   
GridUpdate();
break;
case "expandalltree" :   
var o=window.document.all.tags("div");
for (var i=0;i<o.length;i++){
if(isSpace(o[i].mode)==false){
expandall(o[i]);
}
}			
break;
case "closealltree" :   
var o=window.document.all.tags("div");
for (var i=0;i<o.length;i++){
if(isSpace(o[i].mode)==false){
closeall(o[i]);
}
}			
break;
case "savegridexit" :   
SaveGrid("","exit");
break;
case "checkdj" :   
SaveGrid("checkdj");
break;
case "djcheck" :   
SaveGrid("check");
break;
case "djcheck1" :   
SaveGrid("check1");
break;
case "savegrid" :   
SaveGrid();
break;
case "gridkey" :   
gridkey();
break;
case "refresh" :   
openselbill(pubdjbh);
break;
case "gridkey" :   
gridkey();
break;
case "clicksel" :   
clicksel();
break;
case "treefiltergrid" :
dssub1.Filter("F('bmid')=='"+curO.type+"'" );
break;  
case "openbill" :
break;  
case "maingridmovefirst" : 
if(pubdjbh!=""){
dssub1.MoveFirst();
}
break;
case "postmaingrid" :
dssub1.Update();
dssub2.Fields.Field["spid"].Value=dssub1.Fields.Field["spid"].Value;
dssub2.Fields.Field["dj_sn"].Value=dssub1.Fields.Field["dj_sn"].Value;
break;  
case "scrollsub" :
if (dssub2.Empty!="null"){
dssub2.Update();
dssub2.Filter("F('spid')=='"+dssub1.Fields.Field["spid"].Value+"'" );
}
break;  
case "submovefirst" :
dssub1.Filter("F('bmid')=='"+"BM"+"'" );
break;   	
case "movefirst" :
MoveDsMain(1);      
break;   	
case "moveprev" :
MoveDsMain(2);      
break;   	
case "movenext" :
MoveDsMain(3);      
break;   	
case "movelast" :
MoveDsMain(4);      
break;   	
case "selectdate" :
SelectDate();      
break;   	
case "gridaddline" :
try{
var sssnouse=grid_ds.id;
}catch (e) {
grid_ds=dssub1;
}
grid_ds.Append();
break;
case "griddelline" :
try{
var sssnouse=grid_ds.id;
}catch (e) {
grid_ds=dssub1;
}
grid_ds.Delete();
break;
case "savebilltmp" :   
SaveBill(4);
break;
case "savebillreturn" :   
SaveBill(2);
break;
case "savebillexit" :   
SaveBill(1);
break;
case "savebill" :   
SaveBill();
break;
case "delbill" :   
DelBill();
break;
case "addbill" :   
AddBill();
break;   
case "exitbill" :  
CloseBill();
break;   
default :
if(sKey.substring(0,10)=="dj_select_"){  
var fhNo=sKey.substring(10,sKey.length);
var sRet=zlSelect("dj_select_"+fhNo,"",1,zlSelectParaData());
if(sRet=="openbill"){
pubdjbh=DsMain.Fields.Field[SKbillsheet.keyfield].Value ;
openselbill(pubdjbh) ;
}
}else if(sKey.substring(0,10)=="zl_select_"){  
if(typeof ogrid=="undefined"){
if(curO.type == "button"){
var sRet=zlSelect(sKey,"",1);
}else {
var sRet=zlSelect(sKey,trim(curO.value),1);
}
}else{
var sRet=zlSelect(sKey,trim(curO.value),1,"",ogrid);
}
}else if(sKey.substring(0,8)=="openbill"){  
var iiPos1 = sKey.indexOf("_");
if(iiPos1<0) iiPos1=10;
var sDJNO=sKey.substring(iiPos1+1,sKey.length);
var sAdd=sKey.substring(8,9);  
var sRefresh=sKey.substring(9,10);	
if(sAdd=="_"){
var s1="";
var s2="";
try {
s2=curO.tagName;
}catch(E){}
if(s2=="TD")	{ 
if(curO.parentNode.rowIndex>0 && curO.cellIndex>0) {
s1=dssub1.Fields.Field[SKbillsheet.keyfield].Value;
}
}
OpenBill(sDJNO,s1,0); 
} else if(sAdd=="1"){
OpenBill(sDJNO,"",sAdd); 
if(sRefresh=="2"){
try{
openselbill(pubdjbh);
}catch(e){}
}
}else { 
if(curO.tagName=="TD")	{ 
if(curO.parentNode.rowIndex>0 && curO.cellIndex>0) {
var s1=dssub1.Fields.Field[SKbillsheet.keyfield].Value;
if(isSpace(s1)==false){
OpenBill(sDJNO,s1,2); 
if(sRefresh=="2"){
try{
openselbill(pubdjbh);
}catch(e){}
}
}
}
}
}
}else {  
if(isSpace(sKey)==false){
if(blnRemove)sKey=sKey+"()";
eval(sKey);
try {
}catch (e) {
alert(sKey+"函数运行出错，原因："+e.description);
}
}
}
} 
}
function clickmenu(sKey) {
LoadMod(sKey,"clickmenu");
}
function clickrightmenu(sKey,gridID) {
LoadMod(sKey,"grid",gridID);
}
function bill_blonopen(sKey) {
LoadMod(sKey,"clickmenu");
}
function bill_blonclose(sKey) {
LoadMod(sKey,"clickmenu");
}
function bill_onclick(sKey){
LoadMod(sKey,"click");
}
function bill_ondblclick(sKey,ogrid){
LoadMod(sKey,"click");
}
function bill_onenter(sKey){
LoadMod(sKey,"click");
}
function bill_onexit(sKey){
LoadMod(sKey,"click");
}
function bill_onkeydown(sKey){
LoadMod(sKey,"click");
}
function RunTabindex(){
var sXml=SKbillsheet.billtaborder;
if(sXml=="<root></root>")return ;
var ikeycode=event.keyCode ;
if( ikeycode==9){ 
var bRunUp=false;  
if(ikeycode==38) bRunUp=true;
curID=event.srcElement.id;
if(curID=="chk") curID=event.srcElement.parentNode.parentNode.id;
if(isSpace(curID))curID=event.srcElement.parentNode.id;
if(curID=="fc_txtName") curID=event.srcElement.parentNode.id;
if(curID=="Numedit") curID=event.srcElement.parentNode.id;
if(event.srcElement.id=="txtMyGrid")curID=event.srcElement.parentNode.parentNode.id;
if(event.srcElement.id=="t")curID=event.srcElement.parentNode.parentNode.id;
if(event.srcElement.tagName.toUpperCase()=="TD")curID=event.srcElement.parentNode.parentNode.parentNode.parentNode.parentNode.id;
var sXml=SKbillsheet.billtaborder;
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML (sXml);
var b1=false;
for(var i=0;i<oXml.documentElement.childNodes.length;i++){
if(oXml.documentElement.childNodes(i).text==curID){
b1=true;
break;
}
}
if(b1==false){
i=0;
}	   	
var iLoops=1;  
while(iLoops<20){
if(bRunUp){
if(i==0)
i=oXml.documentElement.childNodes.length-1;
else
i=i-1;
}else {
if(i==oXml.documentElement.childNodes.length-1)
i=0;
else
i=i+1;
}
var nextObj=eval(oXml.documentElement.childNodes(i).text);
var stagname1=nextObj.tagName.toUpperCase();
if(nextObj.disabled || nextObj.style.display=="none"){
iLoops++;
continue;			  	
}else{
if(stagname1=="FIELDSET"){
nextObj.childNodes(1).focus();
}else if(stagname1=="FC_CODE"){
nextObj.txt.focus() ;
}else if(stagname1=="DIV") {
try { nextObj.txt.focus() ;}catch(e){}
}else if(stagname1=="WEBGRID"){
if(nextObj.visible=="是"){
if(nextObj.tab.rows.length>=nextObj.FixRows){
nextObj.SetFocus(nextObj.FindFirstTD(nextObj.FixRows),"程序给焦点");
}else{
nextObj.SetFocus(null,"");
nextObj.curTD.focus();
}					
}else {
iLoops++;
continue;			  	
}
}else {
try{
nextObj.focus();
}catch(E){}
}
}
iLoops=21;
}
event.returnValue=false;
}
}
function FirstFocus(){
var sXml=SKbillsheet.billtaborder;
if(sXml=="<root></root>")return; 
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML (sXml);
var ll=oXml.documentElement.childNodes.length;
i=0;
while(i<20 && i<ll){
var nextObj=eval(oXml.documentElement.childNodes(i).text);
var stagname1=nextObj.tagName.toUpperCase();
if(nextObj.disabled || nextObj.style.display=="none"){
i++;
continue;			  	
}else{
if(stagname1=="FIELDSET"){
nextObj.childNodes(1).focus();
}else if(stagname1=="WEBGRID" ){
if(nextObj.visible=="是"){
if(nextObj.tab.rows.length>=nextObj.FixRows){
nextObj.SetFocus(nextObj.FindFirstTD(nextObj.FixRows),"程序给焦点");
}else{
nextObj.SetFocus(null,"");
nextObj.curTD.focus();
}
}else {
i++;
continue;			  	
}
}else {
try{
nextObj.focus();
}catch(E){}
}
}
i=21;
}
}
function openemptybill(){
var o=window.document.all.tags("dataset");
for(var ii=0;ii<o.length;ii++){
o[ii].PageSize=-1;
sErr=o[ii].OpenEmpty();
if(sErr!="") {
alert(sErr);
return;
}
}
var o=window.document.all.tags("webgrid");
for(var ii=0;ii<o.length;ii++){
o[ii].EndRowState="edit";
}	
}
function openselbill(djbh,gzid,noeditdjbh){
var sErr = "";
var oo=window.document.all.tags("dataset");
var l = oo.length;
var o = new Array(l);
if(l <= 0) return;
for(var ii=0;ii<l;ii++){
o[ii] = oo[ii];
}
if(isSpace(o[0].opensortno) == false){
o.sort(cmpdataset);
}
for(var ii=0;ii<l;ii++){
if(isSpace(gzid)){
if(isSpace(o[ii].crossvalue)){
var s1=o[ii].opensql;
if(typeof s1 =="undefined" ) s1="";
if(s1 == "") s1 = UnSqlPropTrans(o[ii].sqltrans) ;
if(s1.length>1){
if (s1.charCodeAt(s1.length-1)==10 && s1.charCodeAt(s1.length-2)==13 ){
s1=s1.substring(0,s1.length-2);
o[ii].opensql=s1;
}
}
if(isSpace(s1)==false){
if(isSpace(SKbillsheet.keyfield)==false){
sErr=o[ii].Open(fc_RepStr(s1,":"+SKbillsheet.keyfield,"'"+djbh+"'"));
}else{
sErr=o[ii].Open(s1);
}
}else{
o[ii].OpenEmpty();
}
}else { 
var s1= CrossTab(o[ii].crossvalue);
if(isSpace(s1)==false){
o[ii].OpenXml(s1);
sErr="";
}
}
}else{
sErr=o[ii].Open("select * from "+o[ii].temptable+" where gzid='"+gzid+"'");
}
if(isSpace(sErr) == false ) {
alert(sErr);
return;
}
}
if(noeditdjbh != "不要修改单据编号"){
pubdjbh=djbh;   
}
function cmpdataset(a,b) {
return parseInt(a.opensortno)-parseInt(b.opensortno);
}
}
function pub_djhtm() {
try{
fcpubvar.DsMain = GetDsMain(false) ;
}catch(e){}
if(typeof pubdjbh == "object"){
pubDataSet=pubdjbh;
pubdjbh="";
} 
}
function pub_window_onkeypress() {
pubEdit=true;
}
function pub_window_onbeforeunload() {
try{
eval(SKbillsheet.BLONclose);
}catch(e){}
if(parent.piAction != 3 && pubEdit){
event.returnValue="离开当前页面将导致当前输入的数据丢失!";
}
ajax_stop();
}
function pub_window_onresize() {
var o=window.document.all.tags("webgrid");
var winWidth=document.body.clientWidth   ;
var winHeight=document.body.clientHeight  ;
for(var ii=0;ii<o.length;ii++){
if(o[ii].childNodes(0).style.position != "absolute"){
winWidth = o[ii].parentNode.offsetWidth ;
winHeight = o[ii].parentNode.offsetHeight ;
}
if(o[ii].autowidth=="是"){
var tmpwidth = winWidth-parseInt(o[ii].left)-18;
if(tmpwidth < 0 ) tmpwidth = 0 ;
o[ii].width =tmpwidth;
}
if(o[ii].autoheight=="是"){
var tmpheight = winHeight-parseInt(o[ii].top)-18;
if(tmpheight < 0 ) tmpheight = 0 ;
o[ii].height =tmpheight;
}
}	
}



function pub_window_onload(){
var   d = new Date();
var   t = d.getTime() ;  
var obj=SKbillsheet;
if(isSpace(obj.posheight)==false && (obj.type != "LR" && obj.type != "PR") ){
window.dialogHeight=obj.posheight+"px";
window.dialogWidth=obj.poswidth+"px";
}
var iAction=parent.piAction;
if(typeof iAction =="undefined" || iAction==0 || iAction=="0"){
if(obj.entertype=="新增")
parent.piAction=1;
if(obj.entertype=="修改")
parent.piAction=2;
if(obj.entertype=="展现")
parent.piAction=3;
}else{
parent.piAction=iAction;
}
var sbar=obj.toolbar;
var htmfile="";
if(sbar=="带工具栏")
htmfile="toolbar.htm";
else if(sbar=="带工作流工具栏")
htmfile="toolbarwf.htm";
else if(sbar=="查询工具栏")
htmfile="toolbarfind.htm";
else if(sbar=="帮助工具栏")
htmfile="toolbarhelp.htm";
if(htmfile != ""){
parent.toolbar.location.assign(fcpubdata.Path+"/fceform/common/"+htmfile);
try{
parent.mainframeset.rows="31,90%,0";
}catch(E){}
}
ShowWait("正在打开表单......");
var o=window.document.all.tags("input");
var l=o.length;
for(var ii=0;ii<l;ii++){
if(o[ii].dropstyle=="是"){
SetButtonImage(o[ii],fcpubdata.Path+'/fceform/images/ef_run_downarrow.gif');
}
}	
var o=window.document.all.tags("button");
var l=o.length;
for(var ii=0;ii<l;ii++){
if(o[ii].dropstyle=="是"){
SetButtonImage(o[ii],fcpubdata.Path+'/fceform/images/ef_run_downarrow.gif');
}
}	
pub_window_onresize();
var o=window.document.all.tags("select");
var l=o.length;
for(var ii=0;ii<l;ii++){
if(isSpace(o[ii].sqltrans)==false){
var s1 = UnSqlPropTrans(o[ii].sqltrans) ;
if(isSpace(s1) == false){
fillcombox(s1,function (result) {
var s1 = result.value;
var obj = result.context ;
if(isSpace(s1)==false){
var s2=obj.outerHTML;
s2=s2.substring(0,s2.length-9)+s1+"</select>";
obj.outerHTML=s2;
}
},o[ii]) ;
}
}
}  
var arrTree=new Array();
var j=0;
var o=SKbillsheet.all.tags("div");			
var l=o.length;
for(var ii=0;ii<l;ii++){
if(isSpace(o[ii].sql)==false && o[ii].divtype=="tree"){	
arrTree[j]=o[ii];
j++;
}
}
l=arrTree.length;
for(var ii=0;ii<l;ii++){
var s=arrTree[ii].sql ;
s=RepOpenSql(s) ;
s=RepStr(s,"%","%25");
var bmultisel=false;
if(arrTree[ii].multisel =="是") bmultisel=true;
if(fcpubdata.dotnetVersion==""){
var s1=location.protocol+"//"+location.host+fcpubdata.servletPath+"/common.Tree?sql="+s ;
}else{
s=escape(s);
var s1=location.protocol+"//"+location.host+fcpubdata.servletPath+"/Tree.aspx?sql="+s ;
}
initialize(s1,bmultisel,arrTree[ii]);
}
if(pubDataSet != null ){
if(isSpace(SKbillsheet.keyfield)==false){
try {
pubdjbh=pubDataSet.Fields.Field[SKbillsheet.keyfield].Value;
}catch(E){}
}
}
var o=window.document.all.tags("dataset");
for(var ii=0;ii<o.length;ii++){
o[ii].InitLinkObj() ;
}
if(parent.piAction==1) 
openemptybill();
if(parent.piAction==2 || parent.piAction==3 ) 
openselbill(pubdjbh);
if(typeof upload1 != "undefined" ){
var tb=upload1.childNodes(0) ;
var ohref=tb.rows(tb.rows.length-1).cells(0).children[0];
ohref.onclick=function href_onclick() { if(pubEdit){pubEdit=false;} };
for(var i=0;i<tb.rows.length-1;i++){
tb.rows(i).cells(2).children[0].onclick=function href_onclick() { if(pubEdit){pubEdit=false;} } ;
}
}
var o=window.document.all.tags("div"); 
l=o.length;
for(var i=0;i<l;i++){
if(o[i].divtype != "divcheckbox") continue ;
DivCheckBoxInitLoad(o[i]) ;
}
var o=window.document.all.tags("div"); 
l=o.length;
for(var i=0;i<l;i++){
if(o[i].divtype != "divradio") continue ;
DivRadioInitLoad(o[i]) ;
}
eval(SKbillsheet.BLONopen);
contTermStyle();
var o=window.document.all.tags("div"); 
l=o.length;
for(var i=0;i<l;i++){
if(o[i].divtype != "checkbox") continue ;
var ds1=o[i].getAttribute("dataset");
if(isSpace(ds1)){
o[i].children[0].attachEvent("onclick", checkboxclick);    	
o[i].children[1].attachEvent("onclick", checkboxclick);    	
}
}
var o=window.document.all.tags("fieldset");
l=o.length;
for(var i=0;i<l;i++){
var ds1=o[i].getAttribute("dataset");
if(isSpace(ds1)){
for(var jj=1;jj<o[i].children.length;jj++){
if(o[i].children[jj].tagName=="INPUT"){
o[i].children[jj].attachEvent("onclick", radioclick1);
} else if (o[i].children[jj].tagName=="SPAN"){
o[i].children[jj].attachEvent("onclick", radioclick2);
}
}
}
}
ShowWait("end");
window.setTimeout("FirstFocus();", 30);	
d = new Date();
t1 = d.getTime() ;  
}



function CloseBill(){
pubEdit=false;
history.go(-2);
parent.close();
try{
parent.parent.execScript('try{ CloseWin();}catch(e){} ');
}catch(e){}
}
function checkboxclick(){
var obj=event.srcElement ;
if(obj.tagName.toUpperCase() == "SPAN"){
if(obj.parentNode.children[0].checked == true)
obj.parentNode.children[0].checked = false;
else
obj.parentNode.children[0].checked = true;
var bool = obj.parentNode.children[0].checked;
}else{
var bool = obj.checked;
}
obj=obj.parentNode;
if(bool){
obj.value=obj.truevalue;
}else{
obj.value=obj.falsevalue ;
}
}
function radioclick1() {
var o=event.srcElement ;
o.parentNode.value=o.value ;
}
function radioclick2() {
var o=event.srcElement ;
o.parentNode.value=o.previousSibling.value ;
}
function contTermStyle(){
var o=window.document.all.tags("button");
var l=o.length;
for(var ii=0;ii<l;ii++){
if(IsSpace(o[ii].termStyle)==false){
var oXml = SetDom(o[ii].termStyle) ;
var slen=oXml.documentElement.childNodes.length ;
for(var i=0;i<slen;i++){
var str1=oXml.documentElement.childNodes(i).childNodes(4).text;
var str2=oXml.documentElement.childNodes(i).childNodes(5).text;
if(eval(str1)==true){
str2=RepStr(str2,"curObjID",o[ii].id);
eval(str2);
}
}
}
}
var cont=window.document.all.tags("input");
var sl=cont.length;
for(var m=0;m<sl;m++){
if(IsSpace(cont[m].termStyle)==false){
var oXml = SetDom(cont[m].termStyle) ;
var slen=oXml.documentElement.childNodes.length ;
for(var l=0;l<slen;l++){
var str1=oXml.documentElement.childNodes(l).childNodes(4).text;
var str2=oXml.documentElement.childNodes(l).childNodes(5).text;
if(eval(str1)==true){
str2=RepStr(str2,"curObjID",cont[m].id);
eval(str2);
}
}
}
}
var oCont=window.document.all.tags("span");
var s=oCont.length;
for(var j=0;j<s;j++){
if(IsSpace(oCont[j].termStyle)==false){
var oXml = SetDom(oCont[j].termStyle) ;
var slen=oXml.documentElement.childNodes.length ;
for(var n=0;n<slen;n++){
var str1=oXml.documentElement.childNodes(n).childNodes(4).text;
var str2=oXml.documentElement.childNodes(n).childNodes(5).text;
if(eval(str1)==true){
str2=RepStr(str2,"curObjID",oCont[j].id);
eval(str2);
}
}
}
}
}
function GetConts(comType) {
var oContXml = SetDom(SKbillsheet.contxml);
var oNode = oContXml.documentElement.selectSingleNode(comType) ;
return oNode;
}
function SetButtonImage(sbutton,spathgif) {
var obutton=eval(sbutton);
obutton.style.backgroundImage="url("+spathgif+")" ;
obutton.style.cursor="hand" ;
obutton.onmouseout=function func_onmouseout() { this.style.color="black";};
obutton.onmouseover=function func_onmouseover() {	this.style.color="red";};
}
function parseMemo() {
var sInput=txtValue.value ;
var re = new RegExp() ;
re.compile("[\[]([^]]+)[]]","gi") ; 
var resub = new RegExp() ;
resub.compile("[<]([^>]+)[>]","gi") ; 
var posStart = 0;
var posEnd = 0;
var sR =""; 
var arr,arr1 ;
var bool=false; 
while ((arr = re.exec(sInput)) != null) {
posEnd = arr.index;
var sInput1=RegExp.$1;
var posStart1=0;
var posEnd1=0;
var sRetExp = "" ;
var ret = "" ;  
var bool1=false; 
while ((arr1 = resub.exec(sInput1)) != null) {
posEnd1 = arr1.index;
var s1=RegExp.$1 ;
sRetExp = GetPubVarValue(s1);
ret += sInput1.substring(posStart1,posEnd1);
ret += sRetExp;
posStart1=arr1.index+s1.length+2 ;
bool1 = true;
}
if(bool1 == false ){ 
if( bIsVar(sInput1)){
ret = GetPubVarValue(sInput1);
}else{
ret = sInput1;
}
}else if(posStart1 <= sInput1.length) {
ret += sInput1.substring(posStart1,sInput1.length);
}
if(isSpace(ret)) { ret="" ; }		
var sret = eval(ret);
sR += sInput.substring(posStart,posEnd);
sR += sret;
posStart=arr.index+sInput1.length+2 ;
bool = true;
}
if(bool == false){
sR = sInput;
}else if(posStart <= sInput.length) {
sR += sInput.substring(posStart,sInput.length);
}
if(isSpace(sR)) { sR="" ;}		
txtRet.value = sR ;
return {
bsuccess : true,
msg		 : sR
}	;
function GetPubVarValue(name){
var sRetExp ="";
if(name == "customer.name"){
sRetExp = "'方成'";
}
if(name == "customer.id"){
sRetExp = "'a01'";
}
return sRetExp;
}
function bIsVar(name) {
if( name == "customer.name" || name == "customer.id" ) return true;
return false;
}
}
function SqlPropTrans(sql) {
var s = Trim(sql) ;
if(IsSpace(s)) return "" ;
s = escape(s) ;
var sRet = "" ;
var l = s.length ;
for(var i=0;i<l;i++){
var c = 2 * (s.charCodeAt(i) + 7) ; 
sRet += String.fromCharCode(c);
}
sRet = escape(sRet);
return sRet ;
}
function UnSqlPropTrans(s1) {
var s = Trim(s1) ;
if(IsSpace(s)) return "" ;
s = unescape(s) ;
var sRet = "" ;
var l = s.length ;
for(var i=0;i<l;i++){
var c = (s.charCodeAt(i)/2) - 7 ; 
sRet += String.fromCharCode(c);
}
sRet = unescape(sRet);
return sRet ;
}