
function Addobj(name){
if(bigmain.style.display == "none"){
alert("只有在设计状态下才有此功能!请点下面的设计按钮切换到设计状态后再试!");
return;
}
switch (name){
case "button":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<button controltype='" + name + "' style='position:" + fcpubdata.position + ";left:0px;top:0px;width:75px;height:25px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() id="+sid+">"+sid+"</button>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "spin":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+" Min='1' Max='32000' NextNum='1' style=' Font-Size:12px; position:" + fcpubdata.position + ";Left:0;Top:0; Height:22; Width:70;' src='../images/ef_designer_numedit.gif' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() />";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "SKButton":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<input controltype='" + name + "' type=button style='position:" + fcpubdata.position + ";left:0px;top:0px;width:75px;height:25px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() value="+sid+" id="+sid+"></input>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "label":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<div controltype='" + name + "' style='position:" + fcpubdata.position + ";left:0px;top:0px;width:65px;height:15px; Font-Size:12px; ' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() nowrap value="+sid+" id="+sid+">"+sid+"</div>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "hr":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<HR controltype='" + name + "' id="+sid+" width='95' color='silver' style='position:" + fcpubdata.position + ";left:0px;top:0px;width:165px;height:2px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect()>";
htmltocont(sHtml,name);
break;
}
case "a":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<a controltype='" + name + "' style='position:" + fcpubdata.position + "; left:0px; Top:0px; height:15px; Width:80px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() id="+sid+" >超级连接</a>";  
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "SKDBedit":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<input controltype='" + name + "' type='text' style='position:" + fcpubdata.position + ";left:0px;top:0px;width:110px;height:20px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() value="+sid+" id="+sid+"></input>";
htmltocont(sHtml,name);
eval(sid).setActive();
break;
}
case "text":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<label controltype='" + name + "'  CanSelect=false style='position:" + fcpubdata.position + ";left:0px;top:0px;width:110px;height:20px; Font-Size:12px;BORDER-RIGHT: silver 1px solid; BORDER-TOP: dimgray 2px solid; BORDER-LEFT: dimgray 2px solid; BORDER-BOTTOM: silver 1px solid' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect()  id="+sid+" ></label>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "SKDBTreeView":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<DIV controltype='" + name + "' id="+sid+" divtype=tree style='font-Size:12px;overflow:auto;position:" + fcpubdata.position + ";left:0;top:0;width:200;height:100;border: solid 1 black;' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() ></Div>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "checkboxlist":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<DIV controltype='" + name + "' id="+sid+" divtype='divcheckbox' style='background-color:#d4d0c8; overflow:auto;position:" + fcpubdata.position + ";left:0;top:0;width:300;height:200;border font-size:12px: solid 1 black; FONT-SIZE:12px ;' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() ></DIV>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break;
}
case "radiolist":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<DIV controltype='" + name + "' id="+sid+" divtype='divradio' style='background-color:#d4d0c8; overflow:auto;position:" + fcpubdata.position + ";left:0;top:0;width:300;height:200;border: solid 1 black; FONT-SIZE:12px;' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() ></DIV>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break;
}
case "chart":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+" type=graph style='position:" + fcpubdata.position + ";Left:0;Top:0; Height:188; Width:326;' src='../images/ef_designer_graph.gif' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() />";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "dropdownlist": {
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' format xml sql1  id="+sid+" multiselect='否' addrow='否'  blnempty='否'  blninput='是' visible='是' type=divbox dataset='' style='FONT-size:12px; position:" + fcpubdata.position + ";left:0; top:0; width:100; height:20 ;' src='"+fcpubdata.Path+"/fceform/images/ef_designer_fccode.gif' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() />";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "checkbox":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<div controltype='" + name + "' nowrap id="+sid+" divtype='checkbox'  truevalue='是' falsevalue='否' value='否' style='position:" + fcpubdata.position + "; left:0px; top:0px; width:80; height:20px;FONT-FAMILY:宋体;FONT-SIZE:12px;'onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect()><input type=checkbox oncontrolselect=controlselectcancel()><span>复选框</span></div>";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "radio":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<fieldset controltype='" + name + "' id="+sid+" contentEditable=false value=-1  style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:96;Width:152;FONT-FAMILY:宋体;FONT-SIZE:12px;color:#000000 ; display:block ' dataset='' field='' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() ><legend>单选表</legend></fieldset>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "listbox":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<select controltype='" + name + "' size=8  style='position:" + fcpubdata.position + "; left:0px; top:0px; width:100px; height:80px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() id="+sid+"></select>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break;
}	
case "textarea":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<textarea controltype='" + name + "' style='position:" + fcpubdata.position + "; left:0px; top:0px; width:100px; height:85px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() value="+sid+" id="+sid+"></textarea>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "combobox":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<select controltype='" + name + "' style='position:" + fcpubdata.position + "; left:0px; top:0px; width:120px; height:25px; Font-Size:12px;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect() id="+sid+"></select>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break;
}
case "SKDBtext":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<input controltype='" + name + "' type=text  id="+sid+" value="+sid+"  style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:20;Width:106;BACKGROUND-COLOR:#D4D0C8 ;FONT-FAMILY:宋体;FONT-SIZE:12px;color:#000000 ; display:block ; text-align:left;' dataset='' field='' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "upload":{
try{
obj=upload1.id;
alert("每个表单上只能有一个File Field控件！");
return;
}catch(e){
ArrNum[name]++;
var sHtml="<div controltype='" + name + "' id='upload1' style='BORDER-BOTTOM: 1px solid; BORDER-LEFT: 1px solid; FONT-SIZE:12px;  BORDER-RIGHT: 1px solid;BORDER-TOP: 1px solid;overflow: auto;position:" + fcpubdata.position + ";Left:0;Top:0;Height:48;Width:152; display:block ' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() ><table  border=0 cellPadding=0 cellSpacing=0  frame=box style='font-size:12px ;table-layout:fixed;BORDER-COLLAPSE: collapse;'> <tr><td colspan=5  >&nbsp;&nbsp;<a href='javascript:uploadAddFile()'>增加附件</a></td></tr>  </table>  </div> ";
}
htmltocont(sHtml,name);
SelectObj(upload1);
break ;
}
case "dbimg":{   
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+"  alt='用鼠标双击此可选择图形' ondblclick=uploadImg() style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:105;Width:105;BACKGROUND-COLOR:#FFFFFF ;FONT-FAMILY:宋体;FONT-SIZE:12px;color:#000000 ;' dataset='' field='' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "img":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+" style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:48;Width:56;' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "tree": {
ArrNum[name]++;
var sid = getNewContID(name,oContXml);
var sHtml = "<img controltype='" + name + "' id="+sid+" isAll='true' xml ischecked='false'  src='../images/ef_designer_Tree.gif' style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:205;Width:187;' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";
htmltocont(sHtml,name) ;
SelectObj(sid);
break;
}
case "SKBILLgrid":{
var sidtype=name;
var no=1;
try{
var obj=eval(SKBILLgrid1);
ArrNum[sidtype]++;
no=ArrNum[sidtype];
}catch(e){
if(ArrNum[sidtype]==0)ArrNum[sidtype]++;
}
var sHtml="<img controltype='" + name + "' id="+sidtype+no+" dsid=dssub"+no+" gridid=SKBILLgrid"+no+" style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:140;Width:300;' src='image/newgrid.jpg' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";
htmltocont(sHtml,name) ;
SelectObj(sidtype+no);
break ;
}
case "tab":{
var sidtype=name;
ArrNum[sidtype]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<div id="+sid+" controltype='" + name + "' onresize=page_onresize() onresizestart=resizeStart() onresizeend=resizeEnd() onmovestart=moveStart() onmoveend=moveEnd() style='position:" + fcpubdata.position + ";top:0px;left:0px;width:402px;height:320px'>"
+"<table id=fcpagesubtable cursor:hand bgcolor=white onmousedown=pageonclick() onresizestart=CancelEvent()><tbody><tr contentEditable=false><td style='background-color:white;border-left:1px solid #8BA7B6;border-top:1px solid #8BA7B6;border-right:1px solid #8BA7B6;color:red;' width=80px height=20px align=center><font size=2>页签1</font></td><td style='background-color:white;border-left:1px solid #8BA7B6;border-right:1px solid #8BA7B6;border-top:1px solid #8BA7B6;' width=80px height=20px align=center><font size=2>页签2</font></td></tr></tbody></table>"
+"<div id=fcpagesub style='z-index:1;background-color:white;position:" + fcpubdata.position + ";top:22px;height:250px;border-left:1px solid #8BA7B6;border-bottom:1px solid #8BA7B6;border-right:1px solid #8BA7B6;border-top:1px solid #8BA7B6;' onmovestart=CancelEvent() onresizestart=CancelEvent() oncontrolselect=controlselect()></div>"
+"<div id=fcpagesub style='background-color:white;position:" + fcpubdata.position + ";top:22px;height:250px;border-left:1px solid #8BA7B6;border-bottom:1px solid #8BA7B6;border-right:1px solid #8BA7B6;border-top:1px solid #8BA7B6;' onmovestart=CancelEvent() onresizestart=CancelEvent() oncontrolselect=controlselect()></div>"
+"</div>";	
htmltocont(sHtml,name) ;			
SelectObj(sid);
break ;
}
case "shape":{
ArrNum[name]++;
var sid = getNewContID(name,oContXml) ;
sidtype="Shape";
var sHtml="<v:Rect controltype='" + name + "' id="+sid+" style='position:" + fcpubdata.position + ";Left:0;Top:0;width:100px;height:100px;' StrokeColor='#000000'  fillcolor='#ffffff' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() > "
+" <v:stroke dashstyle='Soild' /> "    
+"</v:Rect> ";
htmltocont(sHtml,name);
SelectObj(sid);
break;
}
case "grid":{
var sidtype=name;
ArrNum[sidtype]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+" style='position:" + fcpubdata.position + ";Left:0;Top:0;Height:140;Width:300;' src='../images/ef_designer_webgrid.gif' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";	
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "dataset":{   
var no=1;
var sidtype=name;
ArrNum[sidtype]++;
var sid = getNewContID(name,oContXml) ;
var sHtml="<img controltype='" + name + "' id="+sid+" opensortno="+no+" style='position:absolute;Left:5;Top:5;Height:47;Width:39;' src='../images/ef_designer_dataset.gif' onmovestart=moveStart() onmoveend=moveEnd() onresizestart=resizeStart() onresizeend=resizeEnd() >";	
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "div": {
ArrNum[name]++ ;
var sid = getNewContID(name,oContXml) ;
var sHtml="<div controltype='" + name + "' id="+sid+" style='BORDER-BOTTOM: 1px solid black; BORDER-LEFT: 1px solid black; FONT-SIZE:12px;  BORDER-RIGHT: 1px solid black; BORDER-TOP: 1px solid black;overflow: auto; background-color:#ffffff; position:" + fcpubdata.position + "; left:0; Top:0; width:280; height:200;' onresize=resize() onresizestart=resizeStart() onresizeend=resizeEnd() onmove=move() onmovestart=moveStart() onmoveend=moveEnd() oncontrolselect=controlselect()></div>";
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}
case "excel": {
ArrNum[name]++ ;
var sid = getNewContID(name,oContXml) ;
var sHtml="<object controltype='" + name + "' classid='clsid:0002E510-0000-0000-C000-000000000046' id="+sid+" style='position:" + fcpubdata.position + "; left:0; Top:0; width:80%; height:200;'><param name='HTMLURL' value><param name='HTMLData' value='&lt;html xmlns:x=&quot;urn:schemas-microsoft-com:office:excel&quot;xmlns=&quot;http://www.w3.org/TR/REC-html40&quot;&gt;&lt;head&gt;&lt;style type=&quot;text/css&quot;&gt;&lt;!--tr{mso-height-source:auto;}td{black-space:nowrap;}.wc4590F88{black-space:nowrap;font-family:宋体;mso-number-format:General;font-size:auto;font-weight:auto;font-style:auto;text-decoration:auto;mso-background-source:auto;mso-pattern:auto;mso-color-source:auto;text-align:general;vertical-align:bottom;border-top:none;border-left:none;border-right:none;border-bottom:none;mso-protection:locked;}--&gt;&lt;/style&gt;&lt;/head&gt;&lt;body&gt;&lt;!--[if gte mso 9]&gt;&lt;xml&gt;&lt;x:ExcelWorkbook&gt;&lt;x:ExcelWorksheets&gt;&lt;x:ExcelWorksheet&gt;&lt;x:OWCVersion&gt;9.0.0.2710&lt;/x:OWCVersion&gt;&lt;x:Label Style='border-top:solid .5pt silver;border-left:solid .5pt silver;border-right:solid .5pt silver;border-bottom:solid .5pt silver'&gt;&lt;x:Caption&gt;Microsoft Office Spreadsheet&lt;/x:Caption&gt; &lt;/x:Label&gt;&lt;x:Name&gt;Sheet1&lt;/x:Name&gt;&lt;x:WorksheetOptions&gt;&lt;x:Selected/&gt;&lt;x:Height&gt;7620&lt;/x:Height&gt;&lt;x:Width&gt;15240&lt;/x:Width&gt;&lt;x:TopRowVisible&gt;0&lt;/x:TopRowVisible&gt;&lt;x:LeftColumnVisible&gt;0&lt;/x:LeftColumnVisible&gt; &lt;x:ProtectContents&gt;False&lt;/x:ProtectContents&gt; &lt;x:DefaultRowHeight&gt;210&lt;/x:DefaultRowHeight&gt; &lt;x:StandardWidth&gt;2389&lt;/x:StandardWidth&gt; &lt;/x:WorksheetOptions&gt; &lt;/x:ExcelWorksheet&gt;&lt;/x:ExcelWorksheets&gt; &lt;x:MaxHeight&gt;80%&lt;/x:MaxHeight&gt;&lt;x:MaxWidth&gt;80%&lt;/x:MaxWidth&gt;&lt;/x:ExcelWorkbook&gt;&lt;/xml&gt;&lt;![endif]--&gt;&lt;table class=wc4590F88 x:str&gt;&lt;col width=&quot;56&quot;&gt;&lt;tr height=&quot;14&quot;&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;&lt;/body&gt;&lt;/html&gt;'> <param name='DataType' value='HTMLDATA'> <param name='AutoFit' value='0'><param name='DisplayColHeaders' value='-1'><param name='DisplayGridlines' value='-1'><param name='DisplayHorizontalScrollBar' value='-1'><param name='DisplayRowHeaders' value='-1'><param name='DisplayTitleBar' value='0'><param name='DisplayToolbar' value='-1'><param name='DisplayVerticalScrollBar' value='-1'> <param name='EnableAutoCalculate' value='-1'> <param name='EnableEvents' value='-1'><param name='MoveAfterReturn' value='-1'><param name='MoveAfterReturnDirection' value='0'><param name='RightToLeft' value='0'><param name='ViewableRange' value='1:65536'></object>" ;
htmltocont(sHtml,name) ;
SelectObj(sid);
break ;
}		
case "focus":{   
var sXml=TaborderXml();
var oX = SetDom(sXml) ;
var sX = oX.childNodes(0).childNodes.length ;
if (RemoveRoot(sXml)!="" && sX >= 2){
SaveoUndoOneRecord();
var sRet=DjOpen('SKDBfocus',sXml,'展现',"有模式窗口","直接","焦点次序");
SKbillsheet.billtaborder=sRet;
SaveoRedoOneRecord();
}else{
alert("有两个或以上控件才能使用此控制！");
}
break;
}
case "align":{   
sArray=CurrSel();
if(sArray.length<2){
alert("选中两个或以上控件才能使用此控制");
break;
}
if(sArray !=""  ){
SaveoUndoOneRecord();
var sRet=DjOpen('SKDBalign',sArray,'展现',"有模式窗口","直接","对齐面板");
SaveoRedoOneRecord();
}    
break;
}
case "menu":{
var str = DjOpen('menu','','展现',"无模式窗口","直接","");
break;
}
case "cut":	
if(CopyCont()){ 
main_onkeydown(46);
main_exec('Delete');
main_onkeyup(46);
}
break;
case "copy":	
if(CopyCont() == false){
document.execCommand("Copy") ;
}
break;
case "paste":	
if(PasteCont() == false){
document.execCommand("Paste") ;
}
break;
case "front":{	
AdjustPositionBefore("是");
break;
}
case "behind":{	
AdjustPositionBefore("否");
break;
}
case "dsmain":{   
arrtmp1[0]=pubDsMain ;
arrtmp1[2]=SKbillsheet ;
var sRet=DjOpen('SKBILLgrid',arrtmp1,'展现',"有模式窗口","直接","主数据集属性") ;
blnChange=true ;
break;
}
case "formatTab":{ 
var Htm = DjOpen('fcs_NewFormatTab','SKbillsheet','展现',"有模式窗口","直接","新建版式表格") ;
if(typeof Htm == 'undefined') {
SKbillsheet.innerHTML += "";
}else{
htmltocont(Htm);
}
blnChange=true ;
break;
}
case "HtmlTab":{  
var sHTab = DjOpen('fcs_NewHtmlTab',fcpubdata,'展现',"有模式窗口","直接","新建表格") ;
if(typeof sHTab == 'undefined') {
SKbillsheet.innerHTML += "";
}else{
try{
htmltocont(sHTab) ;
}catch(e){}
}
blnChange=true;
break ;
}
case "form":{   
var sRet=DjOpen('form',SKbillsheet,'展现',"有模式窗口","直接","表单属性");
if(sRet == "ok"){
var iwidth=0;
if(isSpace(SKbillsheet.poswidth) == false ){
iwidth = parseInt(SKbillsheet.poswidth);
if(bigmain.offsetWidth<iwidth){
bigmain.style.width = iwidth;
}
}
var iheight=0;
if(isSpace(SKbillsheet.posheight) == false ) {
iheight = parseInt(SKbillsheet.posheight);
if(bigmain.offsetHeight<iheight){
bigmain.style.height = iheight;
}
}
blnChange=true ;
}
break;
}
case "userfunction":{   
var stmp=DjOpen('userfunction',pstrUserFunction,'展现',"有模式窗口","直接","自定义函数");
if(typeof stmp != "undefined") pstrUserFunction=stmp;
blnChange=true ;
break;
}
case "userfunction1":{   
try{
var l=new ActiveXObject("CodeMax.Language.3");
}catch(e){
if(window.confirm("没有安装codemax或是IE的安全设置属性框中对没有标记为安全的activex控件进行初始化和脚本运行的选项没为启动；是否下载安装codemax!")==true){
window.open("download.htm","","height=250,width=300,left=300,top=150,resizabel=no,menubar=no") ;
}
break;
}
var stmp=DjOpen('userfunction1',pstrUserFunction,'展现',"有模式窗口","直接","带颜色的自定义函数");
if(typeof stmp != "undefined") pstrUserFunction=stmp;
blnChange=true ;
break;
}
case "addhtml":{   
var stmp=DjOpen('addhtml',pstrAddHtml,'展现',"有模式窗口","直接","附加页面");
if(typeof stmp != "undefined") pstrAddHtml=stmp;
blnChange=true ;
break;
}
case "opendj":{   
var sRet=DjOpen('opendj','','修改',"有模式窗口","直接","选择从数据库中打开的表单");
if(isSpace(sRet)==false){
DesignDjOpen(sRet);
}
break;
}
case "opendjfile":{   
var sRet=DjOpen('opendjfile','','修改',"有模式窗口","直接","选择从文件中打开的表单");
if(isSpace(sRet)==false){
var shtm=readdesignhtml("<no>"+sRet+"</no><no></no>"+"<No>"+fcpubdata.Path+"</No>");
DesignDjOpenSub(shtm,0);
}
break;
}
case "save":{   
blnChange=false;
break;
}
case "saveas":{   
DesignDjSaveAs() ;
blnChange=false;
break;
}
case "new":{   
parent.objlist.select1.options.length = 0;
DesignDjNew("是") ;
break;
}
case "newempty":{   
parent.objlist.select1.options.length = 0;
DesignDjNew() ;
break;
}
case "djpreview":{   
var djsn=SKbillsheet.dj_sn;
if(typeof djsn == "undefined"){
alert("请进入表单属性窗口输入了单据sn后才能预览!");
return
}
if(blnChange){
var stmp = DesignDjSave("不提示") ;
if(IsSpace(stmp) == false) {
return ;
}
}
var sUrl=location.protocol + "//"+location.host+fcpubdata.Path+"/fceform/design/opendj.htm?djsn="+djsn ;
var isfile=SKbillsheet.isfile;
if(isfile == "是"){
sUrl += "&isfile=yes" ;
} else {
sUrl += "&isfile=test" ;
}
open(sUrl);
break;
}
case "billtype":{   
var sRet=DjOpen('billtypefile','','展现',"有模式窗口","直接","表单分类维护");
break;
}
case "createhtmfile":{   
if(isSpace(SKbillsheet.dj_sn)){
var s1 = '表单sn不能为空!请进入表单属性窗口输入表单SN.';
alert(s1);
return ;
}
DesignStr_RunStr_Before(SKbillsheet);
SKbillsheet.removeAttribute("contentEditable");
SKbillsheet.removeAttribute("unselectable");
var sRun=SKbillsheet.outerHTML;
sRun=DesignStr_RunStr_After(sRun);
sRun="<scr"+"ipt>"+pstrUserFunction+"</scr"+"ipt>"+sRun+pstrAddHtml	;
sRun="<![CDATA["+sRun+"]]>";
var sFile = trim(SKbillsheet.dj_sn) + ".htm";
var sPath = "dj/" + sFile; 
var sXml = "<no>djsn</no><no>"+sRun+"</no><no>" + sPath + "</no>"+"<No>"+fcpubdata.Path+"</No>";
var ret=savedesignhtml(sXml);
if(ret == "") {
alert(sFile+"文件保存成功!");
return "";
}else{
alert(ret);
return ret;
}					
break;
}
case "undo" : { cmdUndo() ; break ;}
case "redo" : { cmdRedo() ; break ;}
}
function AdjustPositionBefore(yes) {
var slen=oContXml.selectNodes("//id").length;
if(slen<2){
alert("单个控件不能设置此控制");
return;
}
var sArray=CurrSel();
ilen=sArray.length;
if (ilen>0){
SaveoUndoOneRecord();
var ss=sArray[0].parentNode.innerHTML;
var parentid=sArray[0].parentNode.id;
var strHtm="";
for(var i=0;i<ilen;i++){
upid=sArray[i].parentNode.id;
if(upid==parentid){
stmp=Trim(sArray[i].outerHTML);
ss=RepStr(ss,stmp,"");
strHtm=strHtm+stmp;
}
}
if(yes == "是"){
sArray[0].parentNode.innerHTML=ss+strHtm ;
}else {
sArray[0].parentNode.innerHTML=strHtm+ss ;
}
blnChange=true ;
}
SaveoRedoOneRecord();
}
}
function designdjsave(sXml) {	
var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebDesign"+fcpubdata.dotnetVersion+"?designdjsave",sXml);
return retX;
}
function SavePubData(Sub,strContent,userData){
var Main="pubdata";
if(typeof userData == "undefined")
userData=parent.menu.oForm.oInput ;
userData.setAttribute(Main+userData.value,strContent) ;
userData.save(Sub+userData.value) ;
}
function LoadPubData(Sub,userData){
var Main="pubdata";
if(typeof userData == "undefined")
userData=parent.menu.oForm.oInput ;
userData.load(Sub+userData.value) ;
var sTmp=userData.getAttribute(Main+userData.value) ;
if (sTmp==null) {sTmp="" ; } 
return sTmp ;
}
function getNewContID(comType,oContXml){
var sRet = "id1" ;
var curNo = 1 ;
if(isNaN(parseInt(ArrNum[comType])) == false){ ; 
curNo = parseInt(ArrNum[comType]) ;
}
for(var i=curNo;i<curNo+30;i++){
var sid = comType+i ;
var oList = oContXml.documentElement.selectNodes("//id[. ='"+sid+"']") ;
if(oList.length == 0 ) {
ArrNum[comType] = i ;
return sid ;
}
}
alert(sid + "控件名称重复!请立即修改!");
return sid;
}
function main_ondblclick(obj) {
var arr=new Array();
if(typeof obj == "undefined"){
try {
var obj=event.srcElement;
}catch(e){
obj=curSelElement;
}
}else{
try { 
parent.topic.focus();
}catch(e) {}
}
if(obj == null) {
Addobj('form');
return;
}
if(typeof obj.tagName=="undefined"){
obj=curSelElement;
}
var strid=obj.id;
if(isSpace(strid)){
try {
var stmp1 = obj.parentNode.divtype;
if( stmp1 =="checkbox"){
obj=obj.parentNode;
strid=obj.id;
}
} catch(e) {}
}
arr[0]=obj;
arr[1]=SelFieldToArr();
arr[2]=SKbillsheet;
arr[3]=oContXml;  
try{
arr[4] = Printer;	
}catch(e){}
SaveoUndoOneRecord();
var arrCur = CurrSel();
if(arrCur.length>1){
arr[0]=arrCur;
var sRet=DjOpen('pubAttr',arr,'展现','有模式窗口','直接','设置多个控件的属性');
}else{
if(isSpace(obj.controltype) == false ){
var sRet=DjOpen(obj.controltype,arr,'展现','有模式窗口','直接',obj.controltype+'属性');
}else {	
var bool = false;
var ArrNameNew=new Array();
ArrNameNew["SKButton"]="SKButton";
ArrNameNew["SKDBedit"]="SKDBedit";
ArrNameNew["SKDBcheckbox"]="checkbox";
ArrNameNew["Label"]="label";
ArrNameNew["SKDBRadioGroup"]="radio";
ArrNameNew["SKDBListBox"]="listbox";
ArrNameNew["SKDBMemo"]="textarea";
ArrNameNew["SKDBcombobox"]="combobox";
ArrNameNew["password"]="password";
ArrNameNew["SKuploadfile"]="upload";
ArrNameNew["SKDBtext"]="SKDBtext";
ArrNameNew["SKDBchart"]="chart";
ArrNameNew["SKDBImage"]="dbimg";
ArrNameNew["Image"]="img";
ArrNameNew["SKBILLgrid"]="SKBILLgrid";
ArrNameNew["Shape"]="shape";
ArrNameNew["PageControl"]="tab";
ArrNameNew["FCDiv"]="div";
ArrNameNew["SKDBTreeView"]="SKDBTreeView";
ArrNameNew["SKDBLike"]="a";
ArrNameNew["FCButton"]="button";
ArrNameNew["FCDBedit"]="text";
ArrNameNew["HR"]="hr";
ArrNameNew["divcheckbox"]="checkboxlist";
ArrNameNew["divradio"]="radiolist";
ArrNameNew["fccode"]="textarea";
ArrNameNew["imgwebgrid"]="grid";
ArrNameNew["imgdataset"]="dataset";
ArrNameNew["NumEdit"]="spin";
ArrNameNew["excel"]="excel";
ArrNameNew["Tree"]="tree";
var ArrName1=new Array();
ArrName1[0]="SKButton";
ArrName1[1]="SKDBedit";
ArrName1[2]="SKDBcheckbox";
ArrName1[3]="Label";
ArrName1[4]="SKDBRadioGroup" ;
ArrName1[5]="SKDBListBox"
ArrName1[6]="SKDBMemo"
ArrName1[7]="SKDBcombobox"
ArrName1[8]="password" ;
ArrName1[9]="SKuploadfile" ;
ArrName1[10]="SKDBtext";
ArrName1[11]="SKDBchart";
ArrName1[12]="SKDBImage";
ArrName1[13]="Image";
ArrName1[14]="SKBILLgrid";
ArrName1[15]="Shape";
ArrName1[16]="PageControl";
ArrName1[17]="FCDiv";
ArrName1[18]="SKDBTreeView";
ArrName1[19]="SKDBLike" ;
ArrName1[20]="FCButton" ;
ArrName1[21]="FCDBedit" 
ArrName1[22]="HR" ;
ArrName1[23]="divcheckbox" ;
ArrName1[24]="divradio" ;
ArrName1[25]="fccode" ;
ArrName1[26]="imgwebgrid" ;
ArrName1[27]="imgdataset" ;
ArrName1[28]="NumEdit" ;
ArrName1[29]="excel"	;	
ArrName1[30]="Tree"	;		
var l=ArrName1.length;
for (var i=0;i<l;i++){
if (strid.indexOf(ArrName1[i])==0){
var sRet=DjOpen(ArrNameNew[ArrName1[i]],arr,'展现','有模式窗口','直接',ArrNameNew[ArrName1[i]]+'属性');
bool = true;
break;
}
}
if(bool == false ) {
var sTag = obj.tagName;
if(isSpace(sTag) == false ) {
sTag = sTag.toUpperCase() ;
if(sTag == "INPUT" || sTag == "SELECT" || sTag == "TEXTAREA" || sTag == "BUTTON" || sTag == "A" || sTag == "IMG") {
var sRet=DjOpen('SetCtrlType',arr,'展现','有模式窗口','直接','属性');
}
}
}
}
SaveoRedoOneRecord();
return;
}
}
function initoUndooRedo(){
lngUndo=-1;
lngRedo=-1;
oUndo.loadXML("<root></root>");
oRedo.loadXML("<root></root>");
}
function SaveoRedoOneRecord(){
var root = oRedo.documentElement;	
var newNode = oRedo.createNode (1, "record", "");
root.appendChild(newNode);
var newElem = oRedo.createElement("contxml");
newNode.appendChild(newElem);
newNode.lastChild.text = escape(oContXml.documentElement.xml);
var newElem = oUndo.createElement("SKbillsheet");
newNode.appendChild(newElem);
newNode.lastChild.text = escape(SKbillsheet.outerHTML);
if (oRedo.documentElement.childNodes.length >8)	{
DeleteoRedoOneRecord(0);
}	
}
function SaveoUndoOneRecord(){
var root = oUndo.documentElement	;
var newNode = oUndo.createNode (1, "record", "");
root.appendChild(newNode);
var newElem = oUndo.createElement("contxml") ;
newNode.appendChild(newElem);
newNode.lastChild.text = escape(oContXml.documentElement.xml);
var newElem = oUndo.createElement("SKbillsheet"); 
newNode.appendChild(newElem);
newNode.lastChild.text = escape(SKbillsheet.outerHTML);
lngUndo=lngUndo+1;	
if (oUndo.documentElement.childNodes.length >8)	{
DeleteoUndoOneRecord(0);
lngUndo=7;
}	
}
function ReadoRedoOneRecord(lngKey){
oContXml=SetDom(unescape(oRedo.documentElement.childNodes(lngKey).childNodes(0).childNodes(0).xml));
SKbillsheet.outerHTML=unescape(oRedo.documentElement.childNodes(lngKey).childNodes(1).childNodes(0).xml);
openobjlist();
}
function ReadoUndoOneRecord(lngKey){
oContXml=SetDom(unescape(oUndo.documentElement.childNodes(lngKey).childNodes(0).childNodes(0).xml));
SKbillsheet.outerHTML=unescape(oUndo.documentElement.childNodes(lngKey).childNodes(1).childNodes(0).xml);
openobjlist();
}
function DeleteoRedoOneRecord(intR){
oRedo.documentElement.removeChild(oRedo.documentElement.childNodes.item(intR));
}
function DeleteoUndoOneRecord(intR){
oUndo.documentElement.removeChild(oUndo.documentElement.childNodes.item(intR));	
}
function cmdRedo(){	
var intMaxR=oRedo.documentElement.childNodes.length - 1;
if (lngRedo>=0 && lngRedo<=intMaxR){
ReadoRedoOneRecord(lngRedo);
lngUndo=lngRedo;
lngRedo=lngRedo+1;
}
window.focus();
window.SKbillsheet.focus();
}
function cmdUndo(){	
var intMaxR=oUndo.documentElement.childNodes.length - 1;
if (lngUndo>=0 && lngUndo<=intMaxR){
ReadoUndoOneRecord(lngUndo);	
lngRedo=lngUndo;
lngUndo=lngUndo - 1;		
}
window.focus();
window.SKbillsheet.focus();
}
function resizeStart(){
SaveoUndoOneRecord();
}
function resizeEnd(){
SaveoRedoOneRecord();
}
function moveStart(){
SaveoUndoOneRecord();
}
function moveEnd(){
SaveoRedoOneRecord();
}
function IsDivCont() {
if(curSelElement == null) return false;
if (curSelElement.id == "fcpagesub"  || curSelElement.controltype == "div" || curSelElement.controltype == "tab") return true;
return false;
}
function htmltocont(sHtml,comType,NotOne){
SaveoUndoOneRecord();
if(curSelElement==null ){
SKbillsheet.innerHTML+=sHtml;
}else if(curSelElement.id=="fcpagesub" ){
curSelElement.innerHTML+=sHtml;
}else if(curSelElement.controltype =="tab"){
var l=curSelElement.childNodes.length;
for(var i=1;i<l;i++){
if(curSelElement.childNodes(i).style.zIndex==1){
curSelElement.childNodes(i).innerHTML+=sHtml;
break;
}
}
}else if ((curSelElement.controltype == "div" && comType != "div") || curSelElement.tagName == "TD") {
curSelElement.innerHTML += sHtml;
}else {
SKbillsheet.innerHTML+=sHtml;
}	
if(NotOne == "是"){
}else {
try{
AddContXml(comType);
}catch(e){}
}
blnChange = true;
SaveoRedoOneRecord();
}
function AddContXml(comType,contID) {
if(isSpace(contID)) contID = comType + ArrNum[comType];
var oNode = oContXml.documentElement.selectSingleNode("//id[. ='"+contID+"']") ;
if(oNode != null) return;
var oNode = oContXml.documentElement.selectSingleNode(comType) ;
if(oNode == null) {
var sxml = "<"+comType+"><id>" + contID + "</id></"+comType+">";
var oX = SetDom(sxml);
oContXml.documentElement.appendChild(oX.documentElement);
}else {
var sxml = "<id>" + contID + "</id>";
var oX = SetDom(sxml);
oNode.appendChild(oX.documentElement);
}
parent.objlist.execScript("objlist_add('"+contID+"','"+comType+"')");
}
function CopyCont() {
var sAll="";
var stype="";
if(document.selection.type=="Control"){
var o=document.selection.createRange();
strXml="";
for(var i=0;i<o.length;i++){
var sid=o(i).id;
var svalue=o(i).value;
if(isSpace(o(i).controltype)){
for (var j=0;j<ArrName.length;j++){
if (sid.indexOf(ArrName[j])>=0){
stype=ArrName[j];
break;
}
}
}else {
stype = o(i).controltype;
}
if(isSpace(stype)) continue ;
strXml=strXml+"<Node><id>"+sid+"</id>";
strXml=strXml+"<type>"+stype+"</type></Node>";
var strHtm=o(i).outerHTML;
sAll+=strHtm;
}
}
if(sAll !=""){
sAll=trim(sAll);
CopyToPub(sAll);
SavePubData("stmpid",strXml);
return true ;
}else{
return false ;
}
}
function PasteCont() {
var s1=window.clipboardData.getData("Text") ;
if (isSpace(s1)) return false;
if(s1.substring(0,1)!="<") return false;
sXml=LoadPubData("stmpid");
if(isSpace(sXml)) return false;
var oXml=new ActiveXObject("Microsoft.XMLDOM");
oXml.async=false;
oXml.loadXML ("<root>"+sXml+"</root>");
var newid = "" ;
var sLen=oXml.documentElement.childNodes.length;
var arrNewId = new Array() ; 
for(var i=0;i<sLen;i++){
sid=oXml.documentElement.childNodes(i).childNodes(0).text;
stype=oXml.documentElement.childNodes(i).childNodes(1).text;
newid = sid;
for(j=0;j<ArrName.length;j++){
if(stype==ArrName[j]){
if(ObjIsHave(newid)) {
newid = getNewContID(stype,oContXml,ArrNum[stype]);
}
searchStr="id="+sid+" ";
replaceStr="id="+newid+" ";
svalue="value="+sid+" ";
repvalue="value="+newid+" ";
s1=RepStr(s1,searchStr,replaceStr);
s1=RepStr(s1,svalue,repvalue);
AddContXml(stype,newid);
arrNewId[i] = newid;
break;
}
}
}
htmltocont(s1,"","是");
var oControlRange = document.body.createControlRange();
for(var i=0;i<sLen;i++){
try { 
oControlRange.add(eval(arrNewId[i]));
}catch(e){}
}	
try { 
oControlRange.select();
}catch(e){}
if(IsDivCont() == false) curSelElement = null ;
return true;
} 
function ObjIsHave(sid){
try{
var obj = eval(sid) ;
return true;
}catch(E){
return false;
}
}
function CurrSel(){
var sArray=new Array();
if(document.selection.type=="Control"){
var ii = 0 ; 
var o=document.selection.createRange();
for(var i=0;i<o.length;i++){
var sid=o(i).id;
if(isSpace(sid) == false  ) {
sArray[ii]=eval(sid);
ii++ ;
}
}
}
return sArray;
}
function main_onkeyup(skeycode){
if(typeof(skeycode)=="undefined"){
var scode=event.keyCode;
}else{
var scode = skeycode;
}
if(scode == 46){
SaveoRedoOneRecord();
}
}
function main_onkeydown(skeycode,sshift,sctrl){
var sArray=CurrSel();
if(arguments.length == 0){
var scode=event.keyCode;
var skey=event.shiftKey;
var sctrlkey=event.ctrlKey;
}else{
var scode = skeycode;
var skey = sshift;
var sctrlkey = sctrl;
}
if(scode == 46){  
var ltmp = sArray.length ;
for(var jj=0;jj<ltmp;jj++){
if(isSpace(sArray[jj].controltype)) {
try{
event.returnValue=false;
}catch(e){}
return ;
}
}
SaveoUndoOneRecord();
var oXml = null ;
var sxml = SKbillsheet.billtaborder;
if(isSpace(sxml) == false ){
oXml = SetDom(sxml);
}
del_cont(sArray,oContXml,oXml);
if(oXml != null){
SKbillsheet.billtaborder = oXml.documentElement.xml;
}
curSelElement = null ;
return;
}
function del_cont(arr,oContXml,oXml) {
for(var i=0; i<arr.length; i++ ){
var sid = arr[i].id;
if(isSpace(sid)) continue;
var comType = arr[i].controltype;
if(isSpace(comType)){
var l=ArrName.length ;
for(var ii=0;ii<l;ii++){
if(sid.indexOf(ArrName[ii]) >= 0) {
comType = ArrName[ii];
break;
}
}
}
if(isSpace(comType)) continue;
var oNode = oContXml.documentElement.selectSingleNode(comType) ;
if(oNode != null ){
var oNodeSub = oNode.selectSingleNode("//id[. ='"+sid+"']");
if(oNodeSub != null){
oNode.removeChild(oNodeSub);
parent.objlist.execScript("objlist_del('"+sid+"')");
}					
}
if(oXml != null){
var oNode = oXml.documentElement.selectSingleNode("//taborder[. ='"+sid+"']") ;
if(oNode != null ) {
oXml.documentElement.removeChild(oNode);
}
}
if(comType == "div") {
del_cont(arr[i].all,oContXml,oXml);
}else if (comType == "tab") {
for(var jj=1;jj<arr[i].childNodes.length;jj++){
del_cont(arr[i].childNodes(jj).all,oContXml,oXml);
}
}
}
}
if (sctrlkey==true){	
switch(scode){
case 40:{
for (var s=0;s<sArray.length; s++){
sArray[s].style.top=parseInt(sArray[s].style.top)+1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 38:{
for(var M=0;M<sArray.length; M++){		
sArray[M].style.top=parseInt(sArray[M].style.top)-1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 39:{
for(var t=0; t<sArray.length; t++){		
sArray[t].style.left=parseInt(sArray[t].style.left)+1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 37:{
for(var N=0 ; N<sArray.length; N++){		 
sArray[N].style.left=parseInt(sArray[N].style.left)-1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 67:{  
if(CopyCont()){
event.returnValue=false;
}
break;
}
case 86:{	
if(PasteCont()){
event.returnValue=false;
}
break;
}
case 88:{	
if(CopyCont()){ 
main_onkeydown(46);
main_exec('Delete');
main_onkeyup(46);
event.returnValue=false ;
}
break;
}
case 90:{	
cmdUndo();
break;
}
case 89:{	
cmdRedo();
break;
}
case 83:{	
Addobj('save');
event.returnValue=false;
break;
} 
case 65:{	
if (sArray.length==1){
try{
if (sArray[0].controltype=="ep_band")
BandSelectAll(sArray[0]);
}
catch(e){};
}	
event.returnValue=false;
break;
} 
}
}
if (skey==true){
switch (scode){
case 40:{
for (var Lent=0;Lent<sArray.length;Lent++){
sArray[Lent].style.height=parseInt(sArray[Lent].style.height)+1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 38:{
for (var d=0;d<sArray.length;d++){	   
sArray[d].style.height=parseInt(sArray[d].style.height)-1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 39:{
for (var k=0;k<sArray.length;k++){    
sArray[k].style.width=parseInt(sArray[k].style.width)+1;
}
blnChange = true ;
event.returnValue=false;
break;
}
case 37:{
for (var j=0;j<sArray.length;j++){	   
sArray[j].style.width=parseInt(sArray[j].style.width)-1;
}
blnChange = true ;
event.returnValue=false;
break;
}
}	
}
}
function SelectObj(sid,noadd_objlist){
blnChange=true ;
if(typeof noadd_objlist == "undefined"){
if(curSelElement != null){
if (IsDivCont() ) return;
}
}
try { 
var oControlRange = document.body.createControlRange();
oControlRange.add(eval(sid));
oControlRange.select();
}catch(e){}
}
function openobjlist() {
try{
parent.objlist.select1.options.length = 0;
}catch(e){
Pause(100);
}
parent.objlist.select1.options.length = 0;
var sOpt ="";
var l=oContXml.documentElement.childNodes.length;
for(var i=0;i<l;i++){
var l1 = oContXml.documentElement.childNodes(i).childNodes.length;
var comtype =oContXml.documentElement.childNodes(i).tagName;
for(var j=0;j<l1;j++){
sOpt += "<option controltype='"+ comtype +"'>"+oContXml.documentElement.childNodes(i).childNodes(j).text+"</option>";
}
}
var objList = parent.objlist.select1;
objList.outerHTML = SelectAddOption(objList,sOpt);
}