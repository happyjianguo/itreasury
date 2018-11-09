/**
*测试运行表单时要计算运行串,此函数只在djempty.htm一个地方调用了.
*@param sDesign 从文件中读出的整个设计串
*@date 2004-12-25
**/
function DesignStr_RunStr(sDesign){

	var pstrUserFunction="";
	var pstrAddHtml=""; //保存当前表单的附加页面元素的内容
	var pstrSplitAddHtml= "<p id=splitaddhtml />" ;
	var sHtm="";
	var sRet=sDesign ; //整个设计串
	var bRunDjFile = false ; //为true表示从djempty.htm中运行此函数
	//sRet=UnTransXml(sRet);
	var iEnd=sRet.indexOf("</scr"+"ipt>") ;
	if(iEnd==-1){
		pstrUserFunction="";
		sHtm=sRet;
	}else{
		var sHtm=sRet.substring(iEnd+9,sRet.length) ;
		if(isSpace(sHtm)){
			return "";
		} 
		pstrUserFunction=sRet.substring(8,iEnd) ;
		pstrUserFunction=UnTransXml(pstrUserFunction);
	}
	//求 附加页面串
	var ipos=sHtm.indexOf(pstrSplitAddHtml);
	if(ipos==-1){
		pstrAddHtml="";
		
	}else{
		pstrAddHtml=sHtm.substring(ipos+pstrSplitAddHtml.length,sHtm.length);	
		sHtm=sHtm.substring(0,ipos);
	}
	if(sHtm == "") return "";
	document.write("<div id=bigmain>" + sHtm + "</div>");
	
	if(oContXml == null){ //从djempty.htm中运行时
		bRunDjFile = true;
		SKbillsheet.contxml="";
		if(IsSpace(SKbillsheet.contxml)){
			TooContXml();
		}else{
			oContXml = SetDom(SKbillsheet.contxml);
		}
	}

	DesignStr_RunStr_Before(SKbillsheet);
	
	SKbillsheet.removeAttribute("contentEditable");
	SKbillsheet.removeAttribute("unselectable");
	
	//由设计转为运行串
	var sRun=SKbillsheet.outerHTML;

	sRun=DesignStr_RunStr_After(sRun);

	
	
	//运行串=函数串+处理后的设计串+附加页面串	
	sRun="<scr"+"ipt>"+pstrUserFunction+"</scr"+"ipt>"+sRun+pstrAddHtml;
	
	//清空
	bigmain.outerHTML="";
	document.write(sRun);
	return sRun;


}
//处理fcdbedit
function DesignStr_RunStr_Before0() {

	var oX = oContXml.documentElement.selectSingleNode("text");
	if(oX != null){
		var len = oX.childNodes.length ;
		for(var j=0;j<len;j++){
			try{
				var obj = eval(oX.childNodes(j).text) ;
			}catch(e){
				continue;
			}
			ClearCssPart(obj,"borderTop","Border-Top");
			ClearCssPart(obj,"borderBottom","border-Bottom");
			ClearCssPart(obj,"borderRight","border-Right");
			ClearCssPart(obj,"borderLeft","border-Left");
			switch(obj.txtStyle){
				case '1':{
					obj.className = "txtStyleDefulte" ;
					break;
				}
				case '2': {
					obj.className = "txtStyleBottom" ;
					break;
				}
				case '3':{
					obj.className = "txtStyleSolid" ;
					break;
				}
				case '4' :{
					obj.className = "txtStyledDotted" ;
					break;
				}
				case '5' : {
					obj.className = "txtStyleRidge" ;
					break;
				}
				case '6':{
					obj.className = "txtStyleoutset";
					break;
				}
			}
		}
	}
	
}
/**
*由设计串生成运行串时,在取 SKbillsheet.outerHTML 之前调用
**/
function DesignStr_RunStr_Before(SKbillsheet) {
	//处理href
	var o=window.document.all.tags("a");
	var l=o.length;
	for (var i=0;i<l;i++){
		if(isSpace(o[i].href1) == false){
			o[i].href=o[i].href1;
		}
	}			
	//处理img
		changesrc("dataset","../images/ef_designer_dataset.gif");
		changesrc("grid","../images/ef_designer_webgrid.gif");
		changesrc("chart","../images/ef_designer_graph.gif");
		changesrc("spin","../images/ef_designer_numedit.gif");		
		changesrc("dropdownlist","../images/ef_designer_fccode.gif");	
	//处理Shape
	var o=window.document.all.tags("line");
	var l=o.length;
	for (var i=0;i<l;i++){
		o[i].onresize = ""	;	
	}

	//button的处理
	/*var oNode = window.document.all.tags("Button") ;
	var len=oNode.length;
	for(var j=0;j<len;j++){
		//switch(oNode.cmdStyle){
		//	case '2':{
				//sAttach = "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/input4.css'  />" ;
				oNode[j].onmouseup="this.className='bnmouseup'" ;
				oNode[j].onmouseover="this.className='bnmoveover'" ;
		//		break;
		//	}
		//}	
	}
*/
	//修理button
	var oNode = oContXml.documentElement.selectSingleNode("button");
	if(oNode != null) {
		var l=oNode.childNodes.length ;
		for(var i=0; i<l; i++) {
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(e){
				continue ;
			}
			switch(obj.cmdStyle){
				case '2' :{
					obj.className = "cmd_style" ;
					obj.onmouseout="this.className='btn3_mouseout'" ;
					obj.onmouseover="this.className='btn3_mouseover'" ;
					break;
				}
			}
		}
	}
	//修理fcdbedit
	/*var oX = oContXml.documentElement.selectSingleNode("FCDBedit");
	if(oX != null){
		var len = oX.childNodes.length ;
		for(var j=0;j<len;j++){
			try{
				var obj = eval(oX.childNodes(j).text) ;
			}catch(e){
				continue;
			}
			ClearCssPart(obj,"borderTop","Border-Top")
			ClearCssPart(obj,"borderBottom","border-Bottom")
			ClearCssPart(obj,"borderRight","border-Right")
			ClearCssPart(obj,"borderLeft","border-Left")
			switch(obj.txtStyle){
				case '1':{
					obj.className = "txtStyleDefulte" ;
					break;
				}
				case '2': {
					obj.className = "txtStyleBottom" ;
					break;
				}
				case '3':{
					obj.className = "txtStyleSolid" ;
					break;
				}
				case '4' :{
					//var tmps = obj.outerHTML;

					obj.className = "txtStyledDotted" ;
					alert(obj.outerHTML)
					break;
				}
			}
		}
	}
	*/
	function changesrc(comType,srcPath) {
		var oNode = oContXml.documentElement.selectSingleNode(comType) ;
		if(oNode != null ){
			var l=oNode.childNodes.length;
			for(var i=0;i<l;i++){
				var obj = eval(oNode.childNodes(i).text);
				obj.src=srcPath;
			}
		}
	}
	

}
/**
*由设计串生成运行串时,在取 SKbillsheet.outerHTML 之后调用
* 保存到数据库时 arr 非空
**/
function DesignStr_RunStr_After(sRun,arr) {
	//计算基路径
	var basePath = ""; //fcpubdata.Path+"/fceform";
	var typePath = BillTypeNameToPath(SKbillsheet.type).path;
	typePath = Trim(typePath);
	typePath = typePath.toLowerCase();
	if(typePath.substring(0,1) == "/"){
		typePath = typePath.substring(1,typePath.length);
	}
	if(typePath.substring(typePath.length-1,typePath.length) != "/"){
		typePath = typePath + "/" ;
	}
	//系统表单
	if(typePath == "fceform/dj/"  ){
		basePath = ".." ;
	}else{
		var tag = 0;
		var tagbase = false;
		var arr = typePath.split("/");
		for(var i=0;i<arr.length-1;i++){
			if(arr[i] == "." || IsSpace(arr[i])){
				//当前路径,不用做事
			}else if(arr[i] == ".."){
				if(i == 0) {
					tagbase =true ;
				}else {
					tag++;
				}
			}else {
				basePath += "../"
			}
		}
		for(var i=0;i<tag;i++){
			if(basePath.length>3){
				basePath=basePath.substring(3,basePath.length);
			}
		}
		if(tagbase) basePath += fcpubdata.Path.substring(1,fcpubdata.Path.length)+"/" ;
		basePath += "fceform" ;
	}
	
	//模拟设计打开表单时做的数组赋值
	
	if(typeof arr == "undefined") {
		var ArrNum = new Array();
		ArrNum = OpenControlNo(SKbillsheet.controlno,ArrNum);
	} else {
		var ArrNum = arr;
	}
	//页签控件的转换
	var obj ;
	var l ;
	
	//for(var i=1;i<ArrNum["PageControl"]+1;i++){
	var oNode = oContXml.documentElement.selectSingleNode("tab") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
/*	
		try{
			obj=eval("PageControl"+i);
		}catch(E){
			continue;
		}
	var oNode = oContXml.documentElement.selectSingleNode("PageControl") ;
	if(oNode != null ){
		var l=oNode.childNodes.length
		for(var i=0;i<l;i++){
*/		
			var obj = eval(oNode.childNodes(i).text)
		
			var sHtm =""; // 

			sHtm += "<div class=\"tab-pane\" showtype=\"luna\" IsHideTitle="+obj.IsHideTitle+" id="+obj.id+" style=\"position:absolute;left:"+obj.style.left+";top:"+obj.style.top+";width:"+obj.style.width+";Height:"+obj.style.height+"\">" ;
			var sHeight=obj.style.posHeight-18 ;
			var l1=obj.childNodes(0).rows(0).cells.length ;
			for(var j=0;j<l1;j++){
				sHtm+="<div class=\"tab-page\" style=\"width:"+obj.style.width+";height:"+sHeight+"\" >";
				sHtm+="<h2 class=\"tab\">"+obj.childNodes(0).rows(0).cells(j).innerText+"</h2>";
				sHtm+=HideListBoxSave(obj.childNodes(j+1));
				sHtm+="</div>";
			}
			sHtm+="</div>";
			sHtm += "<script>";
			sHtm += "var " + obj.id  + " = new WebFXTabPane( document.getElementById( \"" + obj.id + "\" ) );" ;
			sHtm += "</scr" + "ipt>";
			
			var ilen=obj.outerHTML.length;
			var ipos=sRun.indexOf("<DIV id="+obj.id);
			sRun=sRun.substring(0,ipos)+sHtm+sRun.substring(ipos+ilen,sRun.length);
		}
	}
	
	//数据集转换
	/*
	var arrtmp=imgGrid_dsgrid(pubDsMain)
	sRun=sRun.substring(0,sRun.length-6)+arrtmp[0]+"</DIV>"
	for(var i=1;i<=ArrNum["SKBILLgrid"];i++){
		try{
			obj=eval("SKBILLgrid"+i);
		}catch(E){
			continue;
		}	
		arrtmp=imgGrid_dsgrid(obj)
		sRun=RepStr(sRun,obj.outerHTML,arrtmp[0]+arrtmp[1])
	}
	*/
	//新的数据集转换
	var oNode = oContXml.documentElement.selectSingleNode("dataset") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			var obj = eval(oNode.childNodes(i).text);
			var s1 = imgdataset_dataset(obj);
			sRun = RepStr(sRun,obj.outerHTML,s1);
		}
	}
	//新的webgrid转换
	var oNode = oContXml.documentElement.selectSingleNode("grid") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			var obj = eval(oNode.childNodes(i).text);
			var s1 = imgwebgrid_webgrid(obj);
			sRun = RepStr(sRun,obj.outerHTML,s1);
		}
	}
	//Numedit 控件转换
	var oNode = oContXml.documentElement.selectSingleNode("spin");
	if(oNode != null) {
		var l=oNode.childNodes.length ;
		for(var i=0; i<l; i++) {
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(e){
				continue ;
			}
			var sHtml = "" ;
			sHtml += "<div Class='xpSpin' DefaultValue=\""+obj.DeFaultValue+"\" id=\""+obj.id+"\" field=\""+obj.field+"\" dataset=\""+obj.dataset+"\"  Min=\""+obj.Min+"\" value\""+obj.value+"\" align=\""+obj.style.textalign;
			if(obj.parentNode.id == "fcpagesub" || obj.parentNode.controltype == "div" ){ 
			sHtml += " ParentPos='相对' " ;
			}
			sHtml += "\" style='align:"+obj.style.textalign+"; position:"+obj.style.position+"; left:"+obj.style.left+"; top:"+obj.style.top+"; width:"+obj.style.width+"; height:"+obj.style.height+";' NextNum=\""+obj.NextNum+"\" Max=\""+obj.Max+"\" fontsize=\""+obj.style.fontSize+"\" fontstyle=\""+obj.style.fontStyle+"\" fontfamily=\""+obj.style.fontFamily+"\" backgroundcolor=\""+obj.style.backgroundColor+"\" color=\""+obj.style.color+"\" fontweight=\""+obj.style.fontWeight+"\" enabled=\""+obj.enabled+"\" display=\""+obj.style.display+"\" left=\""+obj.style.left+"\"  top=\""+obj.style.top+"\" width=\""+obj.style.width+"\" height=\""+obj.style.height+"\"></div>";
			
			sRun = RepStr(sRun,obj.outerHTML,sHtml);
		}
	}
	//combobox 转换
	var oNode = oContXml.documentElement.selectSingleNode("combobox") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var s1 = obj.outerHTML ;
			var s2 = obj.sql ;
			var s3 = SqlPropTrans(s2) ;
			var s4 = RepStr(s1,' sql="'+s2+'"',' sqltrans="'+s3+'"');
			
			sRun = RepStr(sRun,s1,s4);
		}
	}
	//listbox 转换
	var oNode = oContXml.documentElement.selectSingleNode("listbox") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var s1 = obj.outerHTML ;
			var s2 = obj.sql ;
			var s3 = SqlPropTrans(s2) ;
			var s4 = RepStr(s1,' sql="'+s2+'"',' sqltrans="'+s3+'"');
			
			sRun = RepStr(sRun,s1,s4);
		}
	}	
	//checkboxlist 转换
	var oNode = oContXml.documentElement.selectSingleNode("checkboxlist") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var s1 = obj.outerHTML ;
			var s2 = obj.sql ;
			var s3 = SqlPropTrans(s2) ;
			var s4 = RepStr(s1,' sql="'+s2+'"',' sqltrans="'+s3+'"');
			
			sRun = RepStr(sRun,s1,s4);
		}
	}		
	//radiolist 转换
	var oNode = oContXml.documentElement.selectSingleNode("radiolist") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var s1 = obj.outerHTML ;
			var s2 = obj.sql ;
			var s3 = SqlPropTrans(s2) ;
			var s4 = RepStr(s1,' sql="'+s2+'"',' sqltrans="'+s3+'"');
			sRun = _RepStrEnter(sRun,s1,"<DIV id="+obj.id +" ",s4);
			//sRun = RepStr(sRun,s1,s4);
		}
	}			
	//fccode 转换
	//for(var i=1; i<ArrNum["fccode"]+1; i++){
	var oNode = oContXml.documentElement.selectSingleNode("dropdownlist") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var str="<fc:fc_code id=\""+obj.id+"\" addrow=\""+obj.addrow+"\"  multiselect=\""+obj.multiselect+"\"  blninput=\""+obj.blninput+"\"  enabled="+obj.disabled+" blnempty=\""+obj.blnempty+"\"  sqltrans=\""+SqlPropTrans(obj.sql1)+"\" sql2=\""+obj.sql2+"\"   format=\""+obj.format+"\" visible=\""+obj.visible+"\" xml=\""+obj.xml ;
			str += "\" dataset=\""+obj.dataset+ "\" align=\""+obj.style.textalign+"\" position=\""+obj.style.position+"\"   field=\""+obj.field;
			str += "\" left=\""+obj.style.pixelLeft+"\"  top=\""+obj.style.pixelTop+"\" width=\""+obj.style.width+"\" height=\""+obj.height+"\" fontsize=\""+obj.style.fontSize+"\" fontstyle=\""+obj.style.fontStyle+"\" fontfamily=\""+obj.style.fontFamily+"\" backgroundcolor=\""+obj.style.backgroundColor+"\" color=\""+obj.style.color+"\" fontweight=\""+obj.style.fontWeight+"\" height=\""+obj.style.pixelHeight+"\" " 	;	
			if(obj.parentNode.id == "fcpagesub"){ 
			str += " ParentPos='相对' " ;
			}
			var s1="" ;
			if(IsSpace(obj.fc_onclick)==false){
				s1=" onclickadd='"+quot_42(obj.fc_onclick)+"'";
			}
			str += s1 ;
			var s2="";
			if(IsSpace(obj.onkeydown)==false) {
				s2=" onkeydown='"+quot_42(obj.onkeydown)+"'";
			}
			str += s2 ;
			var s3="" ;
			if(IsSpace(obj.onclickopen)==false) {
				s3=" onclickopen='"+quot_42(obj.onclickopen)+"'" ;
			}
			str +=s3 ;
			var s4="" ;
			if(IsSpace(obj.oninterchange)==false) {
				s4=" oninterchange='"+quot_42(obj.oninterchange)+"'";
			}
			str +=s4 ;
			var s5 = "" ;
			if(IsSpace(obj.onchange)==false) {
				s5=" onchange='"+quot_42(obj.onchange)+"'";
			}
			str += s5 ;
			var s6 ="" ;
			if(IsSpace(obj.onselchange)==false) {
				s6=" onselchange='"+quot_42(obj.onselchange)+"'";
			}
			str += s6 ;
			str+= ">" ;
			str += "</fc:fc_code>";
			sRun=RepStr(sRun,obj.outerHTML,str);
		}
	}	
	
	//tree控件的转换
	//tree控件的转换
	var oNode = oContXml.documentElement.selectSingleNode("tree") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			try{
				var obj = eval(oNode.childNodes(i).text);
			}catch(E){
				continue;
			}
			var sStr='';
			//<div id="root1"></div>    <div id="test2"></div>
			//var tmproot=new xtreeItem(0,"Root1","","","","","http://localhost/tree/1.xml");tmproot.setup(document.getElementById("root1"));var root=new treeItem("Root","","","",icon.root.src,true,"");root.setup(document.getElementById("root"));
			sStr += '<div id="'+obj.id+'" '
			if(obj.disabled == true) {
				sStr += 'disabled '
			}

			sStr += 'style="OVERFLOW: auto;BORDER-STYLE:'+obj.style.borderStyle+';position:'+obj.style.position+';'
			sStr += 'border-width:'+obj.style.borderWidth+';'
			sStr += ' background-color:#ffffff; width:'+obj.style.width+'; height:'+obj.style.height+'; left:'+obj.style.left+'; top:'+obj.style.top+'; display:'+obj.style.display+'" ></div>';
			sStr += '<script language="JavaScript">'
				sStr += 'var '+obj.id+'=new xtreeItem('
				
				sStr += '"","'+obj.roottext+'","","'+""+'","",'
				if(obj.ischecked == true ) {
					sStr += 'icon.root.src,true'
				}else{
					sStr += '"",""'
				}
				sStr += ',"",'
				//if(obj.ischecked != true ) {
				sStr += '""'
				//}
				sStr += '); '+obj.id+'.setup(document.getElementById("'+obj.id+'"));';
			//}
			if(SKbillsheet.djsn != "tree") {
				var s="'";
				var itmp = parseInt(obj.sourcetype) ;
				switch(itmp){
					case 0 :
						if(IsSpace(obj.xml) == false ) {
							sStr += obj.id+'.loadXmlStr('+s+obj.xml+s+'); ';
						}
						break ;
					case 1 :
						if(IsSpace(obj.sql) == false){
							var s1 ="";
							if(IsSpace(obj.clicknode) == false) s1 = ' roottarget="'+obj.opentb+'" rootaction="' + escape(obj.clicknode) + '"' ;
							
							sStr += 'var tmp1 = SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath +"/uploaddoc"+fcpubdata.dotnetVersion+"?key=getTreeXml&sTablename="+RepXml(UnSqlPropTrans("'+SqlPropTrans(obj.sql)+'")),"") ;' ;
							var s2 = ".loadXmlStr('<TreeNode " + s1 +">'+tmp1+'</TreeNode>');" ;
							sStr += obj.id+s2;
							//sStr += obj.id+'.loadXmlStr("<TreeNode ' + s1 + ' >"+tmp1+"</TreeNode>"); ';
						}
						break ;	
					case 2 :
						if(IsSpace(obj.xmlpath) == false ) {
							sStr += obj.id+'.loadXmlFile('+s+ obj.xmlpath +s+'); ';
						}
						break ;
				}
				//var sql = 	;
				//

				if(obj.isAll == true){
					sStr += obj.id+'.expandAll();'   //'.collapseAll();'
				}else{
					sStr += obj.id+'.collapseAll();'
				}
			}
			sStr += '</'+'script>';
			sRun=RepStr(sRun,obj.outerHTML,sStr);
		}
	}
	
	//fcedit的转换
	sRun=RepStr(sRun," onkeyup=\"this.innerText=''\" "," ");
	sRun=RepStr(sRun,"<LABEL ","<INPUT type=text ");
	sRun=RepStr(sRun,"</LABEL>","</INPUT>");

	//var sTab = window.document.all.tags("table");
	//alert(sTab.className)
	//处理事件
	
	sRun=RepStr(sRun," fc_onclick="," onclick=");
	sRun=RepStr(sRun," fc_ondblclick="," ondblclick=");
	sRun=RepStr(sRun," fc_onfocus="," onfocus=");
	sRun=RepStr(sRun," fc_onblur="," onblur=");
	sRun=RepStr(sRun," fc_onkeydown="," onkeydown=");
	sRun=RepStr(sRun," onmove=move()","");
	sRun=RepStr(sRun," onresize=resize()","");
	sRun=RepStr(sRun," oncontrolselect=controlselect()","");
	sRun=RepStr(sRun," onresizeend=resizeEnd() onresizestart=resizeStart() onmoveend=moveEnd()","");
	
	//加上表单所引用的JS库
	var sAttach="";
	var sjslib = SKbillsheet.jslib;
	if(isSpace(sjslib)==false){
	var arr = sjslib.split("\r\n");
	var l = arr.length;
	for(var i=0;i<l;i++){
		if(isSpace(arr[i])) continue;
		if(arr[i] == "fcsavedj.js" || arr[i] == "selectdate.js"  || arr[i] == "fcother.js" ){
			sAttach += "<script defer src='"+basePath+"/js/" + arr[i] + "'></"+"script>";
		}else if(arr[i].substring(0,1) == "~"){   //用户自己的JS的引入
			sAttach += "<script src='"+basePath+"/../fceformext/js/" + arr[i].substring(1,arr[i].length) + "'></"+"script>";
		}else{
			sAttach += "<script src='"+basePath+"/js/" + arr[i] + "'></"+"script>";
		}
	}
	}
	sAttach += "<link href="+basePath+"/css/tabstyle.css type=text/css rel=stylesheet>";
	//附加上相应的引用
	var sCode = "<script >document.styleSheets[0].addRule(\"fc\\\\:fc_code\",\"behavior: url("+basePath+"/htc/fc_code.htc)\",0);</"+"script>";
	var l=oContXml.documentElement.childNodes.length;
	for(var i=0;i<l;i++){
		if(oContXml.documentElement.childNodes(i).childNodes.length > 0 ){
			var comtype=oContXml.documentElement.childNodes(i).nodeName;
			switch (comtype) {
				case "dropdownlist" :
					if( sAttach.indexOf(sCode) < 0 ) sAttach += sCode;
					break;
				case "spin" :
					sAttach += "<script >document.styleSheets[0].addRule(\".xpSpin\",\"behavior: url("+basePath+"/htc/NumEdit.htc)\",0);</"+"script>";
					break;
				case "tab" :
					sAttach += "<script src='"+basePath+"/js/webfxlayout.js'></"+"script><link id='luna-tab-style-sheet' type='text/css' rel='stylesheet' href='"+basePath+"/css/luna/tab.css'  />";
					break;				
				case "grid" :
					//sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/webgrid.css'  />";
					sAttach +="<script >document.styleSheets[0].addRule(\"fc\\\\:webgrid\",\"behavior: url("+basePath+"/htc/webgrid.htc)\",0);</"+"script>";
					sAttach +="<script src='"+basePath+"/js/fcwebgrid.js'></"+"script>";
					if( sAttach.indexOf(sCode) < 0 ) sAttach += sCode;
					
					break;
				case "dataset" :
					//sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/dataset.css'  />";
					sAttach +="<script >document.styleSheets[0].addRule(\"fc\\\\:dataset\",\"behavior: url("+basePath+"/htc/dataset.htc)\",0);</"+"script>";
					sAttach +="<script src='"+basePath+"/js/fcdataset.js'></"+"script>";
					break;
				case "shape" :
					sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/shape.css'  />";
					break;
				case "tree":
					sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/xtree.css'  />";
					sAttach +="<script src='"+basePath+"/js/xtree.js'></"+"script>";
					break;
				case "upload" :
					sAttach +="<script src='"+basePath+"/js/fcupload.js'></"+"script>";
					break;
				case "chart" :
					sAttach +="<script src='"+basePath+"/js/fcgraph.js'></"+"script>";
					break;
				case "SKDBTreeView" :
					sAttach +="<script src='"+basePath+"/tree/tree.js'></"+"script>";
					break;
				case "button":
					//if()
					sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/Button.css'/>"; 
					break;
				case "text":
					sAttach += "<link type='text/css' rel='stylesheet' href='"+basePath+"/css/TextStyle.css'/>"; 
					break;
				case "textarea" :
					sAttach += "<script >document.styleSheets[0].addRule(\".fcmask\",\"behavior: url("+basePath+"/htc/fcmask.htc)\",0);</"+"script>";
					sAttach +="<script language='vbscript' src='"+basePath+"/js/fcmask.vbs'></"+"script>";
					/*
					sAttach += '<script language="vbscript"> \n'
					sAttach += 'sub txtMaskEdit_OnIncomplete(Object) \n'
					sAttach += 'Object.focus \n'
					sAttach += 'Object.select \n'
					sAttach += 'alert "输入无效!" \n'
					sAttach += 'Object.Clear \n'
					sAttach += 'Object.focus \n'
					sAttach += 'Object.select \n'
					sAttach += 'end sub \n'
					sAttach += '</script> \n'
					*/
					break;



			}
		}
	}

	sRun = sAttach +sRun ;
	
	return sRun;
	/**
	* 因超长字符串替换时会出现自动转行现象,
	s = obj.outerHTML
	sFind ="<DIV id="+obj.id
	**/
	function _RepStrEnter(sRun,s,sFind,sRep){
		//var s = obj.outerHTML ;	
		var ilen=s.length;
		var ipos = sRun.indexOf(s) ;
		if(ipos > -1){
			sRun=sRun.substring(0,ipos)+sRep+sRun.substring(ipos+ilen,sRun.length);
		}else{
			ipos=sRun.indexOf(sFind);  //"<DIV id="+obj.id
			if(ipos > -1){
				sRun=sRun.substring(0,ipos)+sRep+sRun.substring(ipos+ilen,sRun.length);
			}
		} 
		return sRun ;
	}

}

/**
*将SKbillsheet.controlno中的值放入数组中
**/
function OpenControlNo(s,ArrNum) {
	var arr=s.split(";") ;
	var l=arr.length ;
	for(var i=0;i<l;i++){
		var arr1=arr[i].split(":");
		ArrNum[arr1[0]]=arr1[1];
	}
	return ArrNum;
}
/**
表单设计界面
作者：梅勇
日期：2004-07-21
**/

/**
将imgGrid控件转换成数据集控件串和表格控件串
 	<field>
		<fieldname> sitemcode</fieldname>
		<datatype>字符</datatype>
		<displaylabel>商品编号</displaylabel>
		<size>15</size>
		<precision>0</precision>
		<fieldkind>数据项</fieldkind>
		<defaultvalue></defaultvalue>
		<displayformat></displayformat>
		<isnull>否</isnull>
		<iskey>否</iskey>
		<valid>否</valid>
		<procvalid>否</procvalid>
		<link>否</link>
		<target>_blank</target>
		<href></href>
		<visible>是</visible>
		<primarykey>否</primarykey>
	</field> 
		
	<col>
		<fname>sitemcode</fname>
		<cname>商品编号</cname>
		<width>15</width>
		<dtype>字符</dtype>
		<readonly>否</readonly>
		<visible>是</visible>
		<unique>否</unique>
		<validate>否</validate>
		<sorted>否</sorted>
		<required>否</required>
		<columnwidth>80</columnwidth>
		<align>left</align> 
	</col>

由imgGrid对象的各个属性计算出数据集对象的字符串和表格对象的字符串，并以此作返回值。
保存表单时将此串插入到DIV中即可。imgGrid对象display=none,这样在保存表单时也不用去掉imgGrid对象了。
只是HTML串就加大了很多了。


*@param oimgGrid imgGrid控件对象
*@date 2004-07-21
**/
function imgGrid_dsgrid(oimgGrid){
	var sRetDs="";
	var sRetGrid="";
	var sFormat="";
	var sonSetText="";
	var sonGetText="";
	var sonValid="";
	var sGridFormat="";
	var sonclick="";
	var sondblclick="";
	var sonkeydown="";
	
	
	sRetDs="<fc:dataset";
	sRetDs+=" id="+oimgGrid.dsid;
	if(isSpace(oimgGrid.chnname)==false){
		sRetDs+=" chnname='"+oimgGrid.chnname+"'";
	}
	if(isSpace(oimgGrid.temptable)==false){
		sRetDs+=" temptable="+oimgGrid.temptable;
	}
	if(isSpace(oimgGrid.saveastable)==false){
		sRetDs+=" saveastable="+oimgGrid.saveastable;
	}
	if(isSpace(oimgGrid.BeforeOpen)==false){
		sRetDs+=" BeforeOpen=bill_dsevent(\"BeforeOpen\",\""+quot_42(oimgGrid.BeforeOpen)+"\")";
	}
	if(isSpace(oimgGrid.AfterOpen)==false){
		sRetDs+=" AfterOpen=bill_dsevent(\"AfterOpen\",\""+quot_42(oimgGrid.AfterOpen)+"\")";
	}
	if(isSpace(oimgGrid.BeforeInsert)==false){
		sRetDs+=" BeforeInsert=bill_dsevent(\"BeforeInsert\",\""+quot_42(oimgGrid.BeforeInsert)+"\")";
	}
	if(isSpace(oimgGrid.AfterInsert)==false){
		sRetDs+=" AfterInsert=bill_dsevent(\"AfterInsert\",\""+quot_42(oimgGrid.AfterInsert)+"\")";
	}
	if(isSpace(oimgGrid.BeforePost)==false){
		sRetDs+=" BeforePost=bill_dsevent(\"BeforePost\",\""+quot_42(oimgGrid.BeforePost)+"\")";
	}
	if(isSpace(oimgGrid.AfterPost)==false){
		sRetDs+=" AfterPost=bill_dsevent(\"AfterPost\",\""+quot_42(oimgGrid.AfterPost)+"\")";
	}
	if(isSpace(oimgGrid.BeforeDelete)==false){
		sRetDs+=" BeforeDelete=bill_dsevent(\"BeforeDelete\",\""+quot_42(oimgGrid.BeforeDelete)+"\")";
	}
	if(isSpace(oimgGrid.AfterDelete)==false){
		sRetDs+=" AfterDelete=bill_dsevent(\"AfterDelete\",\""+quot_42(oimgGrid.AfterDelete)+"\")";
	}
	if(isSpace(oimgGrid.BeforeClose)==false){
		sRetDs+=" BeforeClose=bill_dsevent(\"BeforeClose\",\""+quot_42(oimgGrid.BeforeClose)+"\")";
	}
	if(isSpace(oimgGrid.AfterClose)==false){
		sRetDs+=" AfterClose=bill_dsevent(\"AfterClose\",\""+quot_42(oimgGrid.AfterClose)+"\")";
	}
	if(isSpace(oimgGrid.BeforeEdit)==false){
		sRetDs+=" BeforeEdit=bill_dsevent(\"BeforeEdit\",\""+quot_42(oimgGrid.BeforeEdit)+"\")";
	}
	if(isSpace(oimgGrid.AfterEdit)==false){
		sRetDs+=" AfterEdit=bill_dsevent(\"AfterEdit\",\""+quot_42(oimgGrid.AfterEdit)+"\")";
	}
	if(isSpace(oimgGrid.BeforeCancel)==false){
		sRetDs+=" BeforeCancel=bill_dsevent(\"BeforeCancel\",\""+quot_42(oimgGrid.BeforeCancel)+"\")";
	}
	if(isSpace(oimgGrid.AfterCancel)==false){
		sRetDs+=" AfterCancel=bill_dsevent(\"AfterCancel\",\""+quot_42(oimgGrid.AfterCancel)+"\")";
	}
	if(isSpace(oimgGrid.BeforeScroll)==false){
		sRetDs+=" BeforeScroll=bill_dsevent(\"BeforeScroll\",\""+quot_42(oimgGrid.BeforeScroll)+"\")";
	}
	if(isSpace(oimgGrid.AfterScroll)==false){
		sRetDs+=" AfterScroll='bill_dsevent(\"AfterScroll\",\""+quot_42(oimgGrid.AfterScroll)+"\")'";
	}
	if(isSpace(oimgGrid.BeforeRefresh)==false){
		sRetDs+=" BeforeRefresh=bill_dsevent(\"BeforeRefresh\",\""+quot_42(oimgGrid.BeforeRefresh)+"\")";
	}
	if(isSpace(oimgGrid.AfterRefresh)==false){
		sRetDs+=" AfterRefresh=bill_dsevent(\"AfterRefresh\",\""+quot_42(oimgGrid.AfterRefresh)+"\")";
	}
	if(isSpace(oimgGrid.OnCalcFields)==false){
		sRetDs+=" OnCalcFields=bill_dsevent(\"OnCalcFields\",\""+quot_42(oimgGrid.OnCalcFields)+"\")";
	}
	if(isSpace(oimgGrid.OnDeleteError)==false){
		sRetDs+=" OnDeleteError=bill_dsevent(\"OnDeleteError\",\""+quot_42(oimgGrid.OnDeleteError)+"\")";
	}
	if(isSpace(oimgGrid.OnEditError)==false){
		sRetDs+=" OnEditError=bill_dsevent(\"OnEditError\",\""+quot_42(oimgGrid.OnEditError)+"\")";
	}
	if(isSpace(oimgGrid.OnFilterRecord)==false){
		sRetDs+=" OnFilterRecord=bill_dsevent(\"OnFilterRecord\",\""+quot_42(oimgGrid.OnFilterRecord)+"\")";
	}
	if(isSpace(oimgGrid.OnNewRecord)==false){
		sRetDs+=" OnNewRecord=bill_dsevent(\"OnNewRecord\",\""+quot_42(oimgGrid.OnNewRecord)+"\")";
	}
	if(isSpace(oimgGrid.OnPostError)==false){
		sRetDs+=" OnPostError=bill_dsevent(\"OnPostError\",\""+quot_42(oimgGrid.OnPostError)+"\")";
	}




	//记录中字段名的次序：西文字段名0 中文名称1 字段类型2 字段宽度3 字段精度4 类型5 默认值6
	//数据格式7 只读8 显示9 唯一10 不能为空11 常规校验12 过程校验13 主键14 超链接15 窗口16 地址17 对齐18 列宽19
	//表现数据20 回写数据21 数据验证22 按键事件23 鼠标单击24 鼠标双击25
	sFormat=" format=\"<fields>";
	sonSetText=" onSetText='bill_ondatasetsettext(\"<dsid>";
	sonGetText=" onGetText='bill_ondatasetgettext(\"<dsid>";
	sonValid=" onValid='bill_ondatasetvalid(\"<dsid>";
	if(oimgGrid.id != "pubDsMain"){
		sGridFormat=" format=\"<cols>";
		sonclick=" onclick='bill_ongridclick(\"<"+oimgGrid.gridid+">";
		sondblclick=" ondblclick='bill_ongriddblclick(\"<"+oimgGrid.gridid+">";
		sonkeydown=" onkeydown='bill_ongridkeydown(\"<"+oimgGrid.gridid+">";
	}
	if(isSpace(oimgGrid.formatxml)==false){
	var oXml=SetDom(oimgGrid.formatxml) ;//此DOM串的最后一个节点不是字段列表。
	var l=oXml.documentElement.childNodes.length;
	for(var i=0;i<l;i++){
		var fdname=oXml.documentElement.childNodes(i).childNodes(0).text;
		var datatype=oXml.documentElement.childNodes(i).childNodes(2).text;
		var displaylabel=oXml.documentElement.childNodes(i).childNodes(1).text;
		var size=oXml.documentElement.childNodes(i).childNodes(3).text;
		var precision=oXml.documentElement.childNodes(i).childNodes(4).text;
		sFormat+="<field>";
		sFormat+="<fieldname>"+fdname+"</fieldname>";
		sFormat+="<datatype>"+datatype+"</datatype>";
		sFormat+="<displaylabel>"+displaylabel+"</displaylabel>";
		sFormat+="<size>"+size+"</size>";
		sFormat+="<precision>"+precision+"</precision>";
		sFormat+="<fieldkind>"+oXml.documentElement.childNodes(i).childNodes(5).text+"</fieldkind>";
		sFormat+="<defaultvalue>"+oXml.documentElement.childNodes(i).childNodes(6).text+"</defaultvalue>";
		sFormat+="<displayformat>"+oXml.documentElement.childNodes(i).childNodes(7).text+"</displayformat>";
		sFormat+="<isnull>"+oXml.documentElement.childNodes(i).childNodes(11).text+"</isnull>";
		sFormat+="<iskey>"+oXml.documentElement.childNodes(i).childNodes(10).text+"</iskey>";
		sFormat+="<valid>"+oXml.documentElement.childNodes(i).childNodes(12).text+"</valid>";
		sFormat+="<procvalid>"+oXml.documentElement.childNodes(i).childNodes(13).text+"</procvalid>";
		sFormat+="<link>"+oXml.documentElement.childNodes(i).childNodes(15).text+"</link>";
		sFormat+="<target>"+oXml.documentElement.childNodes(i).childNodes(16).text+"</target>";
		sFormat+="<href>"+oXml.documentElement.childNodes(i).childNodes(17).text+"</href>";
		sFormat+="<visible>"+oXml.documentElement.childNodes(i).childNodes(9).text+"</visible>";
		sFormat+="<primarykey>"+oXml.documentElement.childNodes(i).childNodes(14).text+"</primarykey>";
		sFormat+="</field>";
		sonSetText+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(21).text)+"</"+fdname+">";	
		sonGetText+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(20).text)+"</"+fdname+">";		
		sonValid+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(22).text)+"</"+fdname+">";	
				
		if(oimgGrid.id != "pubDsMain"){
			if(oXml.documentElement.childNodes(i).childNodes(9).text=="是"){
			//表格
			sGridFormat+="<col>";
			sGridFormat+="<fname>"+fdname+"</fname>";
			sGridFormat+="<cname>"+displaylabel+"</cname>";
			sGridFormat+="<width>"+size+"</width>";
			sGridFormat+="<dtype>"+datatype+"</dtype>";
			sGridFormat+="<readonly>"+oXml.documentElement.childNodes(i).childNodes(8).text+"</readonly>";
			sGridFormat+="<visible>"+oXml.documentElement.childNodes(i).childNodes(9).text+"</visible>";
			sGridFormat+="<u></u><v></v><s></s><r></r>" ; //加上一些无用的节点用来占位，到时去掉

			sGridFormat+="<columnwidth>"+oXml.documentElement.childNodes(i).childNodes(19).text+"</columnwidth>";
			sGridFormat+="<align>"+oXml.documentElement.childNodes(i).childNodes(18).text+"</align>";
			sGridFormat+="</col>";
								
			sonclick+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(24).text)+"</col>";		
			sondblclick+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(25).text)+"</col>";		
			sonkeydown+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(23).text)+"</col>"	;	
			}
		}


	}
	} // end if	
		sFormat+="</fields>\"";
		sonSetText+="</dsid>\")' ";
		sonGetText+="</dsid>\")' ";
		sonValid+="</dsid>\")' ";
		if(oimgGrid.id != "pubDsMain"){
		
			sGridFormat+="</cols>\" ";
			sonclick+="</"+oimgGrid.gridid+">\")' ";
			sondblclick+="</"+oimgGrid.gridid+">\")' ";
			sonkeydown+="</"+oimgGrid.gridid+">\")' ";
		}
	sRetDs+=sFormat;
	if(isSpace(oimgGrid.opensql)==false){
		sRetDs+=" opensql=\""+oimgGrid.opensql+"\"";
	}
		//转换交叉表
		if(oimgGrid.iscrosstab=="是"){
			var s1="";
			s1="<sql>"+oimgGrid.crosstabsql+"</sql>";
			s1+="<no>"+oimgGrid.crosstabdatatype+"</no>";
			s1+="<no>"+oimgGrid.crosstabsumtype+"</no>";
			var i1=0;
			if(oimgGrid.rcount=="是") i1+=1;
			if(oimgGrid.rsum=="是") i1+=2;
			if(oimgGrid.rmin=="是") i1+=4;
			if(oimgGrid.rmax=="是") i1+=8;
			if(oimgGrid.ravg=="是") i1+=10;
			var i2=0;
			if(oimgGrid.ccount=="是") i2+=1;
			if(oimgGrid.csum=="是") i2+=2;
			if(oimgGrid.cmin=="是") i2+=4;
			if(oimgGrid.cmax=="是") i2+=8;
			if(oimgGrid.cavg=="是") i2+=10;
			s1+="<no>"+i1+"</no>";
			s1+="<no>"+i2+"</no>";
			s1+="<format>"+oimgGrid.crosstabformat+"</format>";
			s1+="<rowstr>"+oimgGrid.rowtitle+"</rowstr>"; //可能coltitle和rowtitle相反
			s1+="<colstr>"+oimgGrid.coltitle+"</colstr>";
			//alert(oimgGrid.crosstabsql)
			sRetDs+=" crossvalue=\""+s1+"\" ";
			//CopyToPub(s1)
		}

	
//	 onSetText='bill_ondatasetsettext("<dsid><sitemcode></sitemcode><sitemname></sitemname><iunits></iunits><sunit></sunit><dpacknum>uf_setpacknum()</dpacknum><dzeronum>uf_setzeronum()</dzeronum><dnum>uf_setnum()</dnum><dtaxprice>uf_setintaxprice()</dtaxprice><dprice>uf_setprice()</dprice><dmoney>uf_setmoney()</dmoney><ddiscount>uf_setdiscount()</ddiscount><dtax>uf_settax()</dtax><dtaxmoney></dtaxmoney><dintaxmoney>uf_setintaxmoney()</dintaxmoney><dretailprice></dretailprice><sitemid></sitemid><dretailmoney></dretailmoney><bz></bz><sdnumJe></sdnumJe><sumInTaxMoney></sumInTaxMoney><sumbhsje></sumbhsje><djbh></djbh><dj_sn></dj_sn><sumjetobig></sumjetobig><sumje></sumje><sdwid></sdwid></dsid>")' 
	sRetDs+=sonSetText;
	sRetDs+=sonGetText;
	sRetDs+=sonValid;
	
	sRetDs+=" ></fc:dataset>";
	
	if(oimgGrid.id != "pubDsMain"){
		//表格
		sRetGrid="<fc:webgrid visible='"+oimgGrid.visible+"' readonly='"+oimgGrid.readonly+"' autoappend='"+oimgGrid.autoappend+"' autowidth='"+oimgGrid.autowidth+"' autoheight='"+oimgGrid.autoheight+"' canselect='"+oimgGrid.canselect+"'";
		sRetGrid+=" id="+oimgGrid.gridid+" dataset="+oimgGrid.dsid+" ";
		//需要-17
		sRetGrid+=" left="+oimgGrid.style.posLeft+" top="+oimgGrid.style.posTop+" height="+(oimgGrid.style.posHeight-17)+" width="+(oimgGrid.style.posWidth-17);
		if(oimgGrid.usertitle == "是"){
			sRetGrid+=" multihead=\""+oimgGrid.usertitlehtml+"\" ";
			sRetGrid+=" headrows=\""+oimgGrid.titlerows+"\" ";
			
		}		
		sRetGrid+=sGridFormat;
		sRetGrid+=sonclick;
		sRetGrid+=sondblclick;
		sRetGrid+=sonkeydown;
		sRetGrid+=" >";
	    
	    var tabcss="BORDER-COLLAPSE:collapse;TABLE-LAYOUT:fixed;left:0px;POSITION:absolute;top:0px;BACKGROUND-COLOR:#FFFFFF;FONT-FAMILY:MS Sans Serif;FONT-SIZE:11px;color:#000000;";
		var trcss="BACKGROUND-COLOR:#C0C0C0 ;FONT-FAMILY:MS Sans Serif;FONT-SIZE:11px;color:#000000 ; height:30; ";
		var tmps=CssPart(oimgGrid.csstext1);
		if(isSpace(tmps)==false){
			tabcss="BORDER-COLLAPSE:collapse;TABLE-LAYOUT:fixed;left:0px;POSITION:absolute;top:0px;"+tmps;
		}
		var tmps=CssPart(oimgGrid.csstext2);
		var tmprowheight=oimgGrid.titlerowheight;
		if(isSpace(tmprowheight)) tmprowheight=30;
		if(isSpace(tmps)==false){
			trcss=tmps+";height:"+oimgGrid.titlerowheight;
		}


		sRetGrid+="<table id=t cellPadding=1 cellSpacing=0  frame=box "
	  		+" style=\""+tabcss+"\" >" 
	  		+"<tr style=\""+trcss+" \" ><td></td></tr>"
	  		+"</table></fc:webgrid>";
	}
	var arr=new Array();
	arr[0]=sRetDs ;
	arr[1]=sRetGrid ;
	return arr;
	
}
/**
从界面上得到可供选择的数据集字段信息，放入数组中
*@date 2004-07-27
**/
function SelFieldToArr(){
	var arr=new Array();
/*	
	var sOption="<option value='DsMain' selected >DsMain</option>"
	arr[0]=sOption
	arr['DsMain']=pubDsMain.formatxml
	var o=SKbillsheet.all.tags('img')
	var iLen=o.length;
	for(var i=0;i<iLen;i++){
		if(typeof o[i].dsid != "undefined"){
			sOption+="<option value='"+o[i].dsid+"'>"+o[i].dsid+"</option>"
			arr[o[i].dsid]=o[i].formatxml			
		}
	}
	arr[0]=sOption
	*/
	var oNode = oContXml.documentElement.selectSingleNode("dataset");
	if(oNode != null) {
		var l = oNode.childNodes.length;
		var sOption = "";
		for(var i=0;i<l;i++){
			var id = oNode.childNodes(i).text;
			var ods=eval(id);
			sOption += "<option value='"+id+"'>"+id+"</option>";
			arr[id]=ods.formatxml;
		}
		
		arr[0]=sOption;
	}
	return arr;

}
/**
得到要建的临时表的建表串
*@date 2004-07-30
**/
function GetTmpTableStr(){
	//return ""
	var sRet="";
	/*
	var oDsMain = GetDsMainObj()

	if(oDsMain.iscreatetable=="是"){
		sRet+=GetCreateTable(oDsMain.temptable,oDsMain.formatxml)+";" ;
	}
	var o=SKbillsheet.all.tags('img')
	var iLen=o.length;
	for(var i=0;i<iLen;i++){
		if(typeof o[i].dsid != "undefined"){
	*/
	var oNode = oContXml.documentElement.selectSingleNode("dataset");
	if(oNode != null) {
		var l = oNode.childNodes.length;
		for(var i=0;i<l;i++){
			var id = oNode.childNodes(i).text;
			var ods=eval(id);
			if(ods.iscreatetable=="是"){
				var sXml=ods.formatxml;
				//西文字段名0 中文名称1 字段类型2 字段宽度3 字段精度4 
				var stablename=ods.temptable;
				sRet+=GetCreateTable(stablename,sXml,true)+";" ;
				
			}			
		}
	}	
	if(sRet != "" ){
		sRet=sRet.substring(0,sRet.length-1);
	}
	return sRet;

}
/**
*由字段信息串生成建表语句
*@param stablename 表名
*@param sfieldxml 字段xml串
*@param bAddGzid = true 表示要加上一个挂帐ID字段
*@return 返回一个建表语句的字符串
*@date 2004-07-30
**/
function GetCreateTable(stablename,sfieldxml,bAddGzid){
	var sNewLine="\r\n"; //&#13;&#10;
	var sRet="";
	var oXml=SetDom(sfieldxml) ;
	//西文字段名0 中文名称1 字段类型2 字段宽度3 字段精度4 
	var l=oXml.documentElement.childNodes.length-1 ;
	var sF="";
	for(var j=0;j<l;j++){
		sF+=oXml.documentElement.childNodes(j).childNodes(0).text;
		
		var stype=oXml.documentElement.childNodes(j).childNodes(2).text;
		var slen=oXml.documentElement.childNodes(j).childNodes(3).text;
		var sDot=oXml.documentElement.childNodes(j).childNodes(4).text;
		if(stype=="整数"){
		     sF+=" integer null ,";
		     
		}else if(stype=="实数"){
		     sF+=" decimal("+slen+","+sDot+") null ," ;
		}else{
		     sF+=" varchar("+slen+") null ," ;
		}
	}
	if(bAddGzid) {
		sF += "GZID varchar(11) null " ;
	} else {		
		sF=sF.substring(0,sF.length-1)+sNewLine;
	}
	sRet+="if exists(select * from sysobjects where name='"+stablename+"')"+sNewLine ;
	sRet+=" drop table "+stablename+" "+sNewLine +";" ; 
	sRet+="create table "+stablename+" ("+sNewLine; 
	sRet+=sF+")"+sNewLine ;
	return sRet ;

}
/**
*将引号变成42
*@date 2004-12-03
**/
function quot_xml(s) {
	s=RepStr(s,"'","&amp;quot;");
	return s;

}
/**
*将引号变成42
*@date 2004-12-03
**/
function quot_42(s) {
	if(fcpubdata.databaseTypeName == "mysql"){
		var s1="\\";
	}else{
		var s1="";
	}
	s=RepStr(s,"'",s1+"\\42");
	return s;

}
/**
*纯数据集对象从设计串转换成运行串
*@param oimgGrid 设计时的数据集对象ID
*@date 2005-01-10
**/
function imgdataset_dataset(oimgGrid){
	var sRetDs="";
	var sRetGrid="";
	var sFormat="";
	var sonSetText="";
	var sonGetText="";
	var sonValid="";
	var sGridFormat="";
	var sonclick="";
	var sondblclick="";
	var sonkeydown="";
	
	
	sRetDs="<fc:dataset";
	sRetDs+=" id="+oimgGrid.id;
	sRetDs+=" opensortno='"+oimgGrid.opensortno+"'";
	if(isSpace(oimgGrid.chnname)==false){
		sRetDs+=" chnname='"+oimgGrid.chnname+"'";
	}
	if(isSpace(oimgGrid.temptable)==false){
		sRetDs+=" temptable="+oimgGrid.temptable;
	}
	if(isSpace(oimgGrid.saveastable)==false){
		sRetDs+=" saveastable="+oimgGrid.saveastable;
	}
	if(isSpace(oimgGrid.BeforeOpen)==false){
		sRetDs+=" BeforeOpen='bill_dsevent(\"BeforeOpen\",\""+quot_42(oimgGrid.BeforeOpen)+"\")'";
	}
	if(isSpace(oimgGrid.AfterOpen)==false){
		sRetDs+=" AfterOpen='bill_dsevent(\"AfterOpen\",\""+quot_42(oimgGrid.AfterOpen)+"\")'";
	}
	if(isSpace(oimgGrid.BeforePost)==false){
		sRetDs+=" BeforePost='bill_dsevent(\"BeforePost\",\""+quot_42(oimgGrid.BeforePost)+"\")'";
	}
	if(isSpace(oimgGrid.AfterPost)==false){
		sRetDs+=" AfterPost='bill_dsevent(\"AfterPost\",\""+quot_42(oimgGrid.AfterPost)+"\")'";
	}
	if(isSpace(oimgGrid.BeforeScroll)==false){
		sRetDs+=" BeforeScroll='bill_dsevent(\"BeforeScroll\",\""+quot_42(oimgGrid.BeforeScroll)+"\")'";
	}
	if(isSpace(oimgGrid.AfterScroll)==false){
		sRetDs+=" AfterScroll='bill_dsevent(\"AfterScroll\",\""+quot_42(oimgGrid.AfterScroll)+"\")'";
	}

	//记录中字段名的次序：西文字段名0 中文名称1 字段类型2 字段宽度3 字段精度4 类型5 默认值6
	//数据格式7 只读8 显示9 唯一10 不能为空11 常规校验12 过程校验13 主键14 超链接15 窗口16 地址17 对齐18 列宽19
	//表现数据20 回写数据21 数据验证22 按键事件23 鼠标单击24 鼠标双击25
	sFormat=" format=\"<fields>";
	sonSetText=" onSetText='bill_ondatasetsettext(\"<dsid>";
	sonGetText=" onGetText='bill_ondatasetgettext(\"<dsid>";
	sonValid=" onValid='bill_ondatasetvalid(\"<dsid>";

	if(isSpace(oimgGrid.formatxml)==false){
	var oXml=SetDom(oimgGrid.formatxml); //此DOM串的最后一个节点不是字段列表。
	var l=oXml.documentElement.childNodes.length;
	var bln = false;
	if(l>0){
		if(oXml.documentElement.childNodes(0).childNodes.length <= 26) bln = true;
		 
	}
	for(var i=0;i<l;i++){
		var fdname=oXml.documentElement.childNodes(i).childNodes(0).text;
		var datatype=oXml.documentElement.childNodes(i).childNodes(2).text;
		var displaylabel=oXml.documentElement.childNodes(i).childNodes(1).text;
		var size=oXml.documentElement.childNodes(i).childNodes(3).text;
		var precision=oXml.documentElement.childNodes(i).childNodes(4).text;
		sFormat+="<field>";
		sFormat+="<fieldname>"+fdname+"</fieldname>";
		sFormat+="<datatype>"+datatype+"</datatype>";
		sFormat+="<displaylabel>"+displaylabel+"</displaylabel>";
		sFormat+="<size>"+size+"</size>";
		sFormat+="<precision>"+precision+"</precision>";
		sFormat+="<fieldkind>"+oXml.documentElement.childNodes(i).childNodes(5).text+"</fieldkind>";
		sFormat+="<defaultvalue>"+oXml.documentElement.childNodes(i).childNodes(6).text+"</defaultvalue>";
		sFormat+="<displayformat>"+oXml.documentElement.childNodes(i).childNodes(7).text+"</displayformat>";
		sFormat+="<isnull>"+oXml.documentElement.childNodes(i).childNodes(11).text+"</isnull>";
		sFormat+="<iskey>"+oXml.documentElement.childNodes(i).childNodes(10).text+"</iskey>";
		sFormat+="<valid>"+oXml.documentElement.childNodes(i).childNodes(12).text+"</valid>";
		sFormat+="<procvalid>"+oXml.documentElement.childNodes(i).childNodes(13).text+"</procvalid>";
		sFormat+="<link>"+oXml.documentElement.childNodes(i).childNodes(15).text+"</link>";
		sFormat+="<target>"+oXml.documentElement.childNodes(i).childNodes(16).text+"</target>";
		sFormat+="<href>"+oXml.documentElement.childNodes(i).childNodes(17).text+"</href>";
		sFormat+="<visible>"+oXml.documentElement.childNodes(i).childNodes(9).text+"</visible>";
		sFormat+="<primarykey>"+oXml.documentElement.childNodes(i).childNodes(14).text+"</primarykey>";
		if(bln){
			sFormat+="<fieldvalid></fieldvalid>";
			sFormat+="<tag></tag>";
		}else{
			sFormat+="<fieldvalid>"+oXml.documentElement.childNodes(i).childNodes(26).text+"</fieldvalid>";
			sFormat+="<tag>"+oXml.documentElement.childNodes(i).childNodes(27).text+"</tag>";
		
		}
		sFormat+="</field>";
		sonSetText+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(21).text)+"</"+fdname+">";
		sonGetText+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(20).text)+"</"+fdname+">";		
		sonValid+="<"+fdname+">"
				+quot_xml(oXml.documentElement.childNodes(i).childNodes(22).text)+"</"+fdname+">";	
				


	} // end for
	} // end if	
		sFormat+="</fields>\"";
		sonSetText+="</dsid>\")' ";
		sonGetText+="</dsid>\")' ";
		sonValid+="</dsid>\")' ";
	sRetDs+=sFormat;
	if(isSpace(oimgGrid.opensql)==false){
		sRetDs+=" sqltrans=\""+SqlPropTrans(oimgGrid.opensql)+"\"";
	}
	//找到此数据集控件有没有对应的表格控件
	var oNode = oContXml.documentElement.selectSingleNode("grid") ;
	if(oNode != null ){
		var l=oNode.childNodes.length;
		for(var i=0;i<l;i++){
			var oimgGrid1 = eval(oNode.childNodes(i).text);
	
			//转换交叉表
			if(oimgGrid1.iscrosstab=="是"){
				var s1="";
				s1="<sql>"+SqlPropTrans(oimgGrid1.crosstabsql)+"</sql>";
				s1+="<no>"+oimgGrid1.crosstabdatatype+"</no>";
				s1+="<no>"+oimgGrid1.crosstabsumtype+"</no>";
				var i1=0;
				if(oimgGrid1.rcount=="是") i1+=1;
				if(oimgGrid1.rsum=="是") i1+=2;
				if(oimgGrid1.rmin=="是") i1+=4;
				if(oimgGrid1.rmax=="是") i1+=8;
				if(oimgGrid1.ravg=="是") i1+=10;
				var i2=0;
				if(oimgGrid1.ccount=="是") i2+=1;
				if(oimgGrid1.csum=="是") i2+=2;
				if(oimgGrid1.cmin=="是") i2+=4;
				if(oimgGrid1.cmax=="是") i2+=8;
				if(oimgGrid1.cavg=="是") i2+=10;
				s1+="<no>"+i1+"</no>";
				s1+="<no>"+i2+"</no>";
				s1+="<format>"+oimgGrid1.crosstabformat+"</format>";
				s1+="<rowstr>"+oimgGrid1.rowtitle+"</rowstr>" ;//可能coltitle和rowtitle相反
				s1+="<colstr>"+oimgGrid1.coltitle+"</colstr>";
				//alert(oimgGrid.crosstabsql)
				sRetDs+=" crossvalue=\""+s1+"\" ";
				//CopyToPub(s1)
			}
		}
	}
//	 onSetText='bill_ondatasetsettext("<dsid><sitemcode></sitemcode><sitemname></sitemname><iunits></iunits><sunit></sunit><dpacknum>uf_setpacknum()</dpacknum><dzeronum>uf_setzeronum()</dzeronum><dnum>uf_setnum()</dnum><dtaxprice>uf_setintaxprice()</dtaxprice><dprice>uf_setprice()</dprice><dmoney>uf_setmoney()</dmoney><ddiscount>uf_setdiscount()</ddiscount><dtax>uf_settax()</dtax><dtaxmoney></dtaxmoney><dintaxmoney>uf_setintaxmoney()</dintaxmoney><dretailprice></dretailprice><sitemid></sitemid><dretailmoney></dretailmoney><bz></bz><sdnumJe></sdnumJe><sumInTaxMoney></sumInTaxMoney><sumbhsje></sumbhsje><djbh></djbh><dj_sn></dj_sn><sumjetobig></sumjetobig><sumje></sumje><sdwid></sdwid></dsid>")' 
	sRetDs+=sonSetText;
	sRetDs+=sonGetText;
	sRetDs+=sonValid;
	
	sRetDs+=" ></fc:dataset>";

	return sRetDs;
	
	
}
/**
*纯表格对象从设计串转换成运行串
*@param oimgGrid 设计时的表格对象ID
*@date 2005-01-10
**/

function imgwebgrid_webgrid(oimgGrid){
	var sRetDs="";
	var sRetGrid="";
	var sFormat="";
	var sonSetText="";
	var sonGetText="";
	var sonValid="";
	var sGridFormat="";
	var sonclick="";
	var sondblclick="";
	var sonkeydown="";
	
	//记录中字段名的次序：西文字段名0 中文名称1 字段类型2 字段宽度3 字段精度4 类型5 默认值6
	//数据格式7 只读8 显示9 唯一10 不能为空11 常规校验12 过程校验13 主键14 超链接15 窗口16 地址17 对齐18 列宽19
	//表现数据20 回写数据21 数据验证22 按键事件23 鼠标单击24 鼠标双击25
		sGridFormat=" format=\"<cols>";
		sonclick=" onclick='bill_ongridclick(\"<"+oimgGrid.id+">";
		sondblclick=" ondblclick='bill_ongriddblclick(\"<"+oimgGrid.id+">";
		sonkeydown=" onkeydown='bill_ongridkeydown(\"<"+oimgGrid.id+">";
	if(isSpace(oimgGrid.dataset)==false){
		var ods_formatxml=eval(oimgGrid.dataset); //找到表格对应的ds对象
		if(isSpace(ods_formatxml.formatxml)==false){
			var oXml=SetDom(ods_formatxml.formatxml); //此DOM串的最后一个节点不是字段列表。
			var l=oXml.documentElement.childNodes.length;
			for(var i=0;i<l;i++){
				var fdname=oXml.documentElement.childNodes(i).childNodes(0).text;
				var datatype=oXml.documentElement.childNodes(i).childNodes(2).text;
				var displaylabel=oXml.documentElement.childNodes(i).childNodes(1).text;
				var size=oXml.documentElement.childNodes(i).childNodes(3).text;
				var precision=oXml.documentElement.childNodes(i).childNodes(4).text;
						
					if(oXml.documentElement.childNodes(i).childNodes(9).text=="是"){
					//表格
					sGridFormat+="<col>";
					sGridFormat+="<fname>"+fdname+"</fname>";
					sGridFormat+="<cname>"+displaylabel+"</cname>";
					sGridFormat+="<width>"+size+"</width>";
					sGridFormat+="<dtype>"+datatype+"</dtype>";
					sGridFormat+="<readonly>"+oXml.documentElement.childNodes(i).childNodes(8).text+"</readonly>";
					sGridFormat+="<visible>"+oXml.documentElement.childNodes(i).childNodes(9).text+"</visible>";
					sGridFormat+="<u></u><v></v><s></s><r></r>" ; //加上一些无用的节点用来占位，到时去掉

					sGridFormat+="<columnwidth>"+oXml.documentElement.childNodes(i).childNodes(19).text+"</columnwidth>";
					sGridFormat+="<align>"+oXml.documentElement.childNodes(i).childNodes(18).text+"</align>";
					sGridFormat+="</col>";
										
					sonclick+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(24).text)+"</col>";		
					sondblclick+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(25).text)+"</col>";		
					sonkeydown+="<col>"+quot_xml(oXml.documentElement.childNodes(i).childNodes(23).text)+"</col>";		
					}


			}	// end for
		} // end if	
	}
		
			sGridFormat+="</cols>\" ";
			sonclick+="</"+oimgGrid.id+">\")' ";
			sondblclick+="</"+oimgGrid.id+">\")' ";
			sonkeydown+="</"+oimgGrid.id+">\")' ";

	
	
		//表格
		sRetGrid="<fc:webgrid visible='"+oimgGrid.visible+"' readonly='"+oimgGrid.readonly+"' autoappend='"+oimgGrid.autoappend+"' autowidth='"+oimgGrid.autowidth+"' autoheight='"+oimgGrid.autoheight+"' canselect='"+oimgGrid.canselect+"'";
		sRetGrid+=" id="+oimgGrid.id+" dataset="+oimgGrid.dataset+" ";
		try{
		if(oimgGrid.parentNode.id == "fcpagesub" ){ //|| oimgGrid.parentNode.tagName =="TD"
			sRetGrid += " ParentPos='相对' " ;
		}
		}catch(e){}
		//需要-17
		sRetGrid+=" left="+oimgGrid.style.posLeft+" top="+oimgGrid.style.posTop+" height="+(oimgGrid.style.posHeight-17)+" width="+(oimgGrid.style.posWidth-17);
		if(oimgGrid.usertitle == "是"){
			sRetGrid+=" multihead=\""+oimgGrid.usertitlehtml+"\" ";
			sRetGrid+=" headrows=\""+oimgGrid.titlerows+"\" ";
			
		}		
		sRetGrid+=sGridFormat;
		sRetGrid+=sonclick;
		sRetGrid+=sondblclick;
		sRetGrid+=sonkeydown;
		sRetGrid+=" >";
	    
	    var tabcss="BORDER-COLLAPSE:collapse;TABLE-LAYOUT:fixed;left:0px;POSITION:absolute;top:0px;BACKGROUND-COLOR:#FFFFFF;FONT-FAMILY:MS Sans Serif;FONT-SIZE:11px;color:#000000;";
		var trcss="BACKGROUND-COLOR:#C0C0C0 ;FONT-FAMILY:MS Sans Serif;FONT-SIZE:11px;color:#000000 ; height:30; ";
		var tmps=CssPart(oimgGrid.csstext1);
		if(isSpace(tmps)==false){
			tabcss="BORDER-COLLAPSE:collapse;TABLE-LAYOUT:fixed;left:0px;POSITION:absolute;top:0px;"+tmps;
		}
		var tmps=CssPart(oimgGrid.csstext2);
		var tmprowheight=oimgGrid.titlerowheight;
		if(isSpace(tmprowheight)) tmprowheight=30;
		if(isSpace(tmps)==false){
			trcss=tmps+";height:"+oimgGrid.titlerowheight;
		}


		sRetGrid+="<table id=t cellPadding=1 cellSpacing=0  frame=box "
	  		+" style=\""+tabcss+"\" >" 
	  		+"<tr style=\""+trcss+" \" ><td></td></tr>"
	  		+"</table></fc:webgrid>";
	return sRetGrid ;
	
}

//shape控件的处理
function fc_shaperesize() {
	var o=event.srcElement ;
	if(o.id1==3 || o.id1==7 || o.id1==8) {
		o.to=""+o.style.pixelWidth+",0";
	}
	if(o.id1==2 || o.id1==5 || o.id1==6) {
		o.to="0,"+o.style.pixelHeight+"";
	}// 
	if(o.id1==1 || o.id1==10 || o.id1==11) {
		o.from ="0,"+o.style.pixelHeight;
		o.to=""+o.style.pixelWidth+",0";
	}
	if(o.id1==4 || o.id1==9 || o.id1==12) {
		o.to=" "+o.style.pixelWidth+","+o.style.pixelHeight+" " ;
	}
}
function TransXml(sRun){
	sRun=RepStr(sRun,"&","&amp;");
	sRun=RepStr(sRun,">","&gt;");
	//SQL Server库用两个'表示一个'
	//sRun=RepStr(sRun,"'","&apos;&apos;");
	if(fcpubdata.databaseTypeName == "sqlserver") sRun=RepStr(sRun,"'","&apos;&apos;");
	
	sRun=RepStr(sRun,"<","&lt;");
	sRun=RepStr(sRun,"\r\n","&#13;&#10;");
	return sRun ;

}
function UnTransXml(sRun){
	sRun=RepStr(sRun,"&amp;","&");
	sRun=RepStr(sRun,"&gt;",">");
	sRun=RepStr(sRun,"&apos;","'");
	
	sRun=RepStr(sRun,"&lt;","<");
	sRun=RepStr(sRun,"&#13;&#10;","\r\n");
	
	return sRun ;

}
function TransSql(sRun){
	//SQL Server库用两个'表示一个'
	sRun=RepStr(sRun,"'","''");
	//sRun=RepStr(sRun,"\r\n","&#13;&#10;");	
	return sRun ;

}

/**
表单保存时用它 
*@date 2004-09-23
**/
function HideListBoxSave(oPage){
	var sTag1
	var oList=oPage.all.tags("select")
	var sRet
	var l=oList.length
		for(var i=0;i<l;i++){
			if(oList(i).style.posWidth>0){
				return oPage.innerHTML;
			}else{
				oList(i).style.width=oList(i).backwidth
				oList(i).style.height=oList(i).backheight
			}
		}
		sRet=oPage.innerHTML;
		//恢复
		for(var i=0;i<l;i++){
			oList(i).style.posWidth=0
			oList(i).style.posHeight=0
		}
		return sRet
		
}
