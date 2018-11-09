//

	function SavePrnFile(skey,sfilename,sXml) {
		return SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/editeprint"+fcpubdata.dotnetVersion+"?key=" + skey + "&filename="+sfilename+"&designpath=" +fcpubdata.designFilePath+"&sPathfceform="+fcpubdata.savePath,sXml)
	}
//	

	function getDataSet(skey,strSql) {
		var sXml = "<sql>" + strSql + "</sql>";
		return SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/DataSet"+fcpubdata.dotnetVersion+"?key=" + skey, sXml);
	}
//	

	function getDataSetColumns(skey,strSql,columns) {
		var sXml = "<sql>" + strSql + "</sql><columns>"+columns+"</columns>";
		return SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/DataSet"+fcpubdata.dotnetVersion+"?key=" + skey ,sXml);
	}
//	

	function Addeprint(name,bandtype){
		switch (name){
			case "ep_memo":{
				ArrNum[name]++;
				var sid = getNewContID(name,oContXml) ;
				var sHtml="<div controltype='" + name + "' style='border-left-style: solid; border-right-style: solid; border-top-style: solid; border-bottom-style: solid; border-width:1px; position:absolute;left:350px;top:150px;width:75px;height:25px; Font-Size:12px;overflow:hidden;' id="+sid+"></div>";
				htmltocont(sHtml,name);
				SelectObj(sid);
				break;
			}
			case "ep_band":{
				var sbandtype = "" ;
				var o = BandType(bandtype);
				if(typeof o != "undefined") sbandtype = o.type ;
				ArrNum[name]++;
				var sid = getNewContID(name,oContXml) ;
				var sinnerText = sid+" 类型:"+bandtype
				var sHtml="<div controltype='" + name + "' onmovestart='objstartTop()' onmoveend='objendTop()' onresizestart='objstartTop()' onresizeend='objendTop()' style='position:absolute;left:350px;top:0px;width:75px;height:25px; Font-Size:12px;overflow:hidden;background-color:lightgrey;border-top:1px solid blue;border-bottom:1px solid blue;' onmove=bandmove() onresize=bandmove() type="+bandtype+" id="+sid+">"+sinnerText+"</div>";
				htmltocont(sHtml,name);
				SelectObj(sid);
				break;
			}		
			case "ep_pageset":{	
				var o = DjOpen('ep_pageset',oPageSet,'修改',"有模式窗口","直接","页面设置")
				if(typeof o != "undefined") oPageSet = o ;
				initPageSet()
				break;
			}
			case "ep_addwizard":{	
				var arr=new Array()
				arr[2] = SKbillsheet
				arr[4] = Printer;
				var sHtml = DjOpen('ep_addwizard',arr,'修改',"有模式窗口","直接","页面设置") ;
				if(isSpace(sHtml) == false){
				SKbillsheet.innerHTML += sHtml ;
				TooContXml()
				openobjlist()			
				}
				break;
			}				
			case "saveprn":{	
				PrnSave() ;
				break;
			}
			case "saveasprn":{	
				var arr1 = new Array();
				arr1[0] = Printer.typeid;		
				var arr = DjOpen('ep_saveas',arr1,'修改',"有模式窗口","直接","另存打印模版") ;
				if(IsSpace(arr) == false ){
					curtempid = arr[0] ;
					oXmlTypeSub = null ;
					PrnSave() ;
				}
				break;
			}
			case "preview" :{
				Printer.operate = "preview";
				Printer.paperSize = null;
				Printer.pageWidth = null;
				Printer.pageHeigth = null;	
				_Preview(curtempid);
				break;
			}	
		}
	}
//

	function ZoomPrint(tempid){
		//alert("保存方法--ZoomPrint");
		Printer.tempid = tempid;
		var arr = DjOpen('ep_zoom',Printer,'修改',"有模式窗口","直接","缩放打印") ;
	}
//
		
	function TooContXml() {
		//alert("保存方法--TooContXml");
		var sRet = "<root>"
		var arrXml = new Array()
		var l=SKbillsheet.childNodes.length ;
		for(var i=0;i<l;i++){
			var sid = SKbillsheet.childNodes(i).id
			var scontroltype = SKbillsheet.childNodes(i).controltype
			if(typeof arrXml[scontroltype] == "undefined") arrXml[scontroltype] =""
			arrXml[scontroltype] += "<id>"+ sid +"</id>"
		}
			var l = ArrName.length ;
		for(var i=0;i<l;i++){
			if(isSpace(arrXml[ArrName[i]]) == false){
				sRet += "<"+ ArrName[i] +">" + arrXml[ArrName[i]]+"</"+ ArrName[i] +">"
			}
		}
		sRet += "</root>"
		oContXml = SetDom(sRet)
	}
//
		
var istarttop; 
var istartheight; 
var oBandChild;
//
  
	function objstartTop() {
		//alert("保存方法--objstartTop");
		var o = event.srcElement;
		istarttop = ToFloat(o.style.top);
		istartheight = ToFloat(o.style.height);
		oBandChild = parseBand(o,'nouse');
	}
//

	function objendTop() {
		//alert("保存方法--objendTop");
		var o = event.srcElement;
		var iendtop=ToFloat(o.style.top);
		var ioffsettop = iendtop - istarttop; 
		var sRet = band_not_intersect() ;
		if(sRet != ""){
			alert(sRet);
			o.style.top = istarttop
			o.style.height = istartheight
			return
		}
		var l=oBandChild.oSubObj.length;
		for(var i=0; i<l; i++) {
			var inewtop = ToFloat(oBandChild.oSubObj[i].style.top) 
			inewtop += ioffsettop ;
			oBandChild.oSubObj[i].style.top = inewtop ;
		}	
	}
//
	
	function bandmove() {
		var o=event.srcElement
		o.style.left = 0
		o.style.width = SKbillsheet.offsetWidth
		blnChange = true
	}
//
	
	function initPageSet() {
		//alert("保存方法--initPageSet");
		var dw = "mm"
		if (oPageSet.orientation=="1"){
			middlediv.style.width = oPageSet.width + dw
			middlediv.style.height = oPageSet.height + dw
		}
		else{
			middlediv.style.width = oPageSet.height+ dw
			middlediv.style.height = oPageSet.width + dw	
		}
		middlediv.style.paddingTop = oPageSet.top + dw
		middlediv.style.paddingBottom = oPageSet.bottom + dw
		middlediv.style.paddingLeft = oPageSet.left + dw
		middlediv.style.paddingRight = oPageSet.right + dw
		var elems = oContXml.documentElement.selectNodes("ep_band/id") ;
		var len = elems.length;
		for(var i=0; i<len; i++) {
			var obj = eval(elems.item(i).text);
			obj.style.left = 0
			obj.style.width = SKbillsheet.offsetWidth
		}
	}
//
	
	function tempid_name(typeid,tempid) {
	}
	
//
	
	function bBandIntersect(obj1,obj2) {
		//alert("保存方法--bBandIntersect");
		var iTop1 = ToFloat(obj1.style.top);
		var iHeight1 = ToFloat(obj1.style.height);
		var iLine1 = ToFloat(obj1.style.borderBottomWidth);
		var iTop2 = ToFloat(obj2.style.top);
		var iHeight2 = ToFloat(obj2.style.height);
		var iLine2 = ToFloat(obj2.style.borderBottomWidth);
		if(iTop1 < iHeight2 + iTop2 - iLine2 && iTop2 < iTop1 + iHeight1 - iLine1) {
			bool = true
		}else{
			bool = false
		}
		return bool
	}
//

	function bChildBandIntersect(ochild,oband) {
		//alert("保存方法--bChildBandIntersect");
		var bool=true;
		objLeft = ToFloat(ochild.style.left);
		objTop =  ToFloat(ochild.style.top);
		objRight = objLeft + ToFloat(ochild.style.width);
		objBottom = objTop + ToFloat(ochild.style.height);
		bandLeft =  ToFloat(oband.style.left);
		bandTop =  ToFloat(oband.style.top);
		bandRight = bandLeft+ToFloat(oband.style.width);
		bandBottom = bandTop+ToFloat(oband.style.height);
		if ((objBottom>bandBottom && objTop<bandBottom-1)||(objTop<bandTop && objBottom>bandTop+1)) 
		{
			bool=true;
		}
		else{
			bool=false;
		}
		return bool
	}
//

	function BandType(sType) {
		//alert("保存方法--BandType");
		var elems = oContXml.documentElement.selectNodes("ep_band/id") ;
		var len = elems.length;
		for(var i=0; i<len; i++) {
			var obj = eval(elems.item(i).text);
			if(obj.type == sType ) {
				return obj
			}
		}
	} 
//

	function band_not_intersect() {
		//alert("保存方法--band_not_intersect");
		var elems = oContXml.documentElement.selectNodes("ep_band/id") ;
		var len = elems.length;
		var arr1 = new Array(len);
		for(var o=0; o<len; o++) {
			arr1[o] = eval(elems.item(o).text);
		}
		arr1.sort(cmp);
		for(var n=0;n<len-1;n++) {
			if(bBandIntersect(arr1[n],arr1[n+1])) return(arr1[n+1].id+"控件和"+arr1[n].id+"控件不能相交") ;
		}
		return ""
	}
//

	function child_in_band() {
		//alert("保存方法--child_in_band");
		var bands = oContXml.documentElement.selectNodes("ep_band/id") ;
		var len = bands.length;
		var oList = oContXml.documentElement.selectNodes("//id") ;
		var l = oList.length;
		var smsg="";
		for(var j=0; j<l;j++) {
			var oChild = eval(oList.item(j).text);
			if(oChild.controltype != "ep_band" && oChild.isBackground!="1"){
				smsg = "";
				for(var k=0; k<len;k++) {
				var oBand = eval(bands.item(k).text);
				var bool = bChildBandIntersect(oChild,oBand)
					if (bool) {
						smsg+=oChild.innerText + "控件id="+oChild.id + "必须在其所在的div容器内，不能超出！\r" ;
						break;
					}
				}
				if(smsg!=""){
					return smsg;
				}	
			}
		}
		return ""
	}
//

	function cmp(a,b){
		//alert("保存方法--cmp");
		return ToFloat(a.style.top) - ToFloat(b.style.top);
	}
//

	function objBand(type,oband,dataset,subOuterHTML,oSubObj){
		//alert("保存方法--objBand");
		
		this.type			= type;
		this.oband			= oband; 
		this.dataset		= dataset;
		this.subOuterHTML	= subOuterHTML;
		this.oSubObj		= oSubObj;
	}
//

	function parseBand(oband,type){
		//alert("保存方法--parseBand");
		var oSubObj = new Array();
		var strOuterHTML="";
		var dataset = oband.getAttribute("dataset");
		var elems = SKbillsheet.childNodes 
		var len = elems.length;
		var obj,controltype;
		var objLeft,objWidth,objTop,objHeight,objBRight,objBBottom,objBLeft,objBTop;
		var bandLeft,bandWidth,bandTop,bandHeight;
		var iElement = 0;
		for (var i=0;i<len;i++){
			obj = elems(i);
			if (typeof(obj.id)=="undefined") continue;
			controltype = obj.getAttribute("controltype");
			if (controltype=="ep_memo" || controltype=="img" || controltype=="shape"){
				objLeft = ToFloat(obj.style.left);
				objWidth =  ToFloat(obj.style.width);
				objTop =  ToFloat(obj.style.top);
				objHeight =  ToFloat(obj.style.height);	
				objBRight =  ToFloat(obj.style.borderRightWidth);
				objBBottom =  ToFloat(obj.style.borderBottomWidth);
				objBLeft =  ToFloat(obj.style.borderLeftWidth);
				objBTop =  ToFloat(obj.style.borderTopWidth);	
				bandLeft =  ToFloat(oband.style.left);
				bandWidth =  ToFloat(oband.style.width);
				bandTop =  ToFloat(oband.style.top);
				bandHeight =  ToFloat(oband.style.height);	
				if (  (objLeft + objWidth - objBRight <= bandLeft)
				|| (objTop + objHeight - objBBottom <= bandTop)
				|| (objLeft + objBLeft >= bandLeft + bandWidth)
				|| (objTop + objBTop >= bandTop + bandHeight)
				){	
				}
				else{	
					if (obj.isBackground!="1"){		 
						oSubObj[iElement] = obj;
						iElement++;
						strOuterHTML += obj.outerHTML;
					}
				}
			}		
		}
		return new objBand(type,oband,dataset,strOuterHTML,oSubObj);
	}
//构建xml信息并保存

	function PrnSave(){
		//alert("保存方法--PrnSave");
		
		var sMsg = band_not_intersect();
		if (sMsg!=""){
			alert(sMsg);
			return;
		} 
		sMsg = child_in_band();
		if (sMsg!=""){
			alert(sMsg);
			return;
		} 	
		sMsg =saveDataCheck();
		if (sMsg!="")
		{
			alert(sMsg);
			return;
		}
		var scontrolno=SaveControlNo()
		SKbillsheet.controlno=scontrolno
		SKbillsheet.contxml=oContXml.documentElement.xml	
		var curtypeid = Printer.typeid;
		var sXmlFile = "";
		sXmlFile += "<page>";
		sXmlFile += "<papersize>"+ oPageSet.size + "</papersize>";
		sXmlFile += "<pagewidth>" + oPageSet.width + "</pagewidth>";
		sXmlFile += "<pageheight>" + oPageSet.height + "</pageheight>";
		sXmlFile += "<orientation>" + oPageSet.orientation + "</orientation>";
		sXmlFile += "<margintop>" + oPageSet.top + "</margintop>";
		sXmlFile += "<marginleft>" + oPageSet.left + "</marginleft>";
		sXmlFile += "<marginright>" + oPageSet.right + "</marginright>";
		sXmlFile += "<marginbottom>" + oPageSet.bottom + "</marginbottom>";
		sXmlFile += "<header>" + escape(oPageSet.header) + "</header>";
		sXmlFile += "<footer>" + escape(oPageSet.footer) + "</footer>";
		sXmlFile += "</page>";
		sXmlFile +="<content>";
		sXmlFile +="<fcbillsheet><![CDATA[ ";
		sXmlFile += SKbillsheet.outerHTML
		sXmlFile +=" ]]></fcbillsheet>";
		sXmlFile +="</content>";
		if (isSpace(pstrUserFunction)==false){
			sXmlFile +="<script><![CDATA[";
			sXmlFile += pstrUserFunction;
			sXmlFile +=" ]]></script>";
		}
		var sname=curtypeid + "_" + curtempid ;
		sRet = SavePrnFile("writefile",sname+".xml",sXmlFile)
		if (sRet == "ok"){
			alert(curtypeid+"_"+curtempid+".xml文件保存成功!")
			blnChange = false
		}else{
			alert(sRet)	;
		}
	}
//校验文件名

	function CheckFileName(sName){
		//alert("保存方法--CheckFileName");
		
		var bRet = true;
		for (var i=0;i<sName.length;i++){
			var inx=sName.charCodeAt(i);
			if ((inx>96 && inx<123) || (inx>64 && inx<91) ||(inx>47 && inx<58) ||(inx==95) || (inx==45))
				continue;
			else{
				bRet = false;
				alert("模板ID只能是字母、数字、下划线或短线！")
				break;
			}						
		}
		return bRet;
	}
//新开窗口读取模版

	function design_window_onload() {
		//alert("保存方法--design_window_onload");
		
		if(isSpace(Printer.typeid) == false && isSpace(top.toptempid) == false){
			//alert("---1---");
			curtempid = top.toptempid
			//alert("---2---");
			//alert(location.protocol+"//"+location.host+fcpubdata.Path+fcpubdata.designFilePath + Printer.typeid + "_" + top.toptempid + ".xml" );
			//var oXmlRet = SetDomFile("D:/upload/" + Printer.typeid + "_" + top.toptempid + ".xml" );		
			//alert("---3---");
			//var oXmlRet = SetDomFile(location.protocol+"//"+location.host+fcpubdata.Path+fcpubdata.designFilePath + Printer.typeid + "_" + top.toptempid + ".xml" )	
		
			var oXmlRet=new ActiveXObject("Microsoft.XMLDOM");
			oXmlRet.async=false;
			var oTemp = SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/editeprint"+fcpubdata.dotnetVersion+"?key=readxmlfile&filename="+Printer.typeid + "_" + top.toptempid + ".xml");
			oXmlRet.loadXML (oTemp);				
		
			if(oXmlRet.documentElement != null){			
				oPageSet.size = oXmlRet.getElementsByTagName("papersize").item(0).text;
				oPageSet.width = oXmlRet.getElementsByTagName("pagewidth").item(0).text;
				oPageSet.height = oXmlRet.getElementsByTagName("pageheight").item(0).text;
				oPageSet.orientation = oXmlRet.getElementsByTagName("orientation").item(0).text;
				oPageSet.top = oXmlRet.getElementsByTagName("margintop").item(0).text;
				oPageSet.left = oXmlRet.getElementsByTagName("marginleft").item(0).text;
				oPageSet.right = oXmlRet.getElementsByTagName("marginright").item(0).text;
				oPageSet.bottom = oXmlRet.getElementsByTagName("marginbottom").item(0).text;
				oPageSet.header = unescape(oXmlRet.getElementsByTagName("header").item(0).text);
				oPageSet.footer = unescape(oXmlRet.getElementsByTagName("footer").item(0).text);
				SKbillsheet.outerHTML = oXmlRet.getElementsByTagName("fcbillsheet").item(0).childNodes(0).nodeValue;
				if (oXmlRet.getElementsByTagName("script").length>0){
					pstrUserFunction =oXmlRet.getElementsByTagName("script").item(0).childNodes(0).nodeValue
				}
				if(isSpace(SKbillsheet.controlno)) return ""
				ArrNum = OpenControlNo(SKbillsheet.controlno,ArrNum)
				var scontxml = SKbillsheet.contxml
				if(isSpace(scontxml)  ) {
					scontxml = "<root></root>"
				}
				oContXml=SetDom(scontxml)
				initPageSet();
				window.setTimeout("openobjlist();blnChange = false;",500);
			}
		}
	}
//

	function ToFloat(str1){		
		var s1=trim(str1)
		if(isSpace(s1)) {return 0}
		var f1=parseFloat(s1)
		if(isNaN(f1)) {return 0}
		return f1
	}
//

	function DelPrintTemp(typeid,tempid) {
		//alert("保存方法--DelPrintTemp");
		
		sRet = SavePrnFile("delfile",typeid+"_"+tempid + ".xml")		
		if (sRet != "ok" ) return sRet;
		return ""
	}
//

	function saveDataCheck(){
		//alert("保存方法--saveDataCheck");
		
		var arrTitle = new Array();
		var arrMainHeader = new Array();
		var arrMainList = new Array();
		var arrDetailList = new Array();
		var arrMainFooter = new Array();
		var iT = 0;
		var iH = 0;
		var iL = 0;
		var iD = 0;
		var iF = 0;	
		var elems = document.all.tags("DIV");
		var len = elems.length;	
		var sMsg = "";
		var sExp ="";
		for (var i=0;i<len;i++){
			if (elems[i].getAttribute("controltype") == "ep_band"){
				switch (elems[i].getAttribute("type")){
					case "mainlist":{
						arrMainList[iL] = parseBand(elems[i],"mainlist");
						iL++;
						break;			
					}			
					case "detaillist":{
						arrDetailList[iD] = parseBand(elems[i],"detaillist");				
						iD++;
						break;			
					}			
				}					
			}
		}
		if (arrDetailList.length>0)
		{
			if (arrMainList.length<=0) {
				if (sMsg!="") sMsg +="\n";
					sMsg +="您没有设置主数据div，如果不是主从表式的报表，请将明细数据div改成主数据div后保存！";
			}
			else{
				var pre_dataset=arrMainList[0].dataset;
				for (var i=0;i<arrMainList.length;i++){
					if (arrMainList[i].dataset!=pre_dataset)
					{
						if (sMsg!="")
						sMsg +="\n";
						sMsg += "将所有主数据div的数据集设置成同一个！";
						break;
					}
					pre_dataset = arrMainList[i].dataset;
				}
			}
		}
		return sMsg;
	}
//

	function checkTheSameDataset(oband){
		//alert("保存方法--checkTheSameDataset");
		
		var errExpress = "";
		var datasetname = oband.dataset;	
		if (isSpace(datasetname) || datasetname==null || datasetname=="undefined") return errExpress;
		for (var i=0;i<oband.oSubObj.length;i++){
			if (oband.oSubObj[i].controltype =="ep_memo"){
				var express = oband.oSubObj[i].innerText;
				if (!parseMemoDataset(express,datasetname)){
					errExpress += express + " ";
				}
			}
		}
		return errExpress;
	}
//

	function parseMemoDataset(strExpress,dataset) {
		//alert("保存方法--parseMemoDataset");
		
		var sInput = strExpress;
		var re = new RegExp() ;
		re.compile("[\[]([^]]+)[]]","gi") ; 
		var resub = new RegExp() ;
		resub.compile("[<]([^>]+)[>]","gi") ; 
		var posStart = 0
		var posEnd = 0
		var bR = true;
		var arr,arr1 ;
		var bool=false; 
		while ((arr = re.exec(sInput)) != null) {
			posEnd = arr.index;
			var sInput1=RegExp.$1
			var posStart1=0
			var posEnd1=0
			var bool1=false; 
			while ((arr1 = resub.exec(sInput1)) != null) {
				posEnd1 = arr1.index;
				var s1=RegExp.$1 ;
				if (!bIsSame(s1,dataset)){
					bR = false;				
					break;
				}
				posStart1=arr1.index+s1.length+2 ;
				bool1 = true
			}
			if (!bR) break;
			if(bool1 == false ){ 
				if(!bIsSame(sInput1,dataset)){
					bR = false;
					break;
				}				
			}
			posStart=arr.index+sInput1.length+2 ;
			bool = true
		}
		return bR;
//内嵌
		function bIsSame(name,dataset){
			var bRet = true;
			var chndataset = "";
			for (var i=0;i<Printer.datasets.length;i++){
				if (dataset == Printer.datasets[i].id)
				{
					chndataset = Printer.datasets[i].chnname;
					break;
				}
			}
				var arrName = name.split(".");
				if (arrName.length>1){
				if (arrName[0]!=chndataset) 
				bRet = false;
				}
			return  bRet;
		}	
	}
//

	function BandSelectAll(oband){
		//alert("保存方法--BandSelectAll");
		
		var oControlRange = document.body.createControlRange();
		var objband = parseBand(oband,"");
		for (var i=0;i<objband.oSubObj.length;i++){
			oControlRange.add(objband.oSubObj[i]);
		}
		oControlRange.select();
	}
//

	function markModelFile(curtypeid,curtempid,sTitle,sDataSet){
		//alert("保存方法--markModelFile");
		
		var pagewidth=210;
		var pageheight=297;	
		var leftmargin = 8;
		var rightmargin = 8;
		var oDom=Printer.arrDataSets[sDataSet].oDom;
		var spage="<page>";
		spage+="<papersize>A4</papersize>";
		spage+="<pagewidth>"+pagewidth+"</pagewidth>";
		spage+="<pageheight>"+pageheight+"</pageheight>";
		spage+="<orientation>1</orientation>";
		spage+="<margintop>8</margintop>";
		spage+="<marginleft>" + leftmargin + "</marginleft>";
		spage+="<marginright>"+ rightmargin + "</marginright>";
		spage+="<marginbottom>8</marginbottom>";
		spage+="<header></header>";
		spage+="<footer></footer>";
		spage+="</page>";
		var datasetid;
		for(var j=0;j<Printer.datasets.length;j++){
			if(Printer.datasets[j].chnname == sDataSet){
				datasetid = Printer.datasets[j].id;
				break;
			}
		}
		var datasetchn =sDataSet;
		var sBandTitle='<DIV onmove=bandmove() id=ep_band1 onresizeend=objendTop() onresizestart=objstartTop() onmoveend=objendTop() onresize=bandmove() style="BORDER-TOP: blue 1px solid; FONT-SIZE: 12px; LEFT: 0px; OVERFLOW: hidden; WIDTH: 728px; BORDER-BOTTOM: blue 1px solid; POSITION: absolute; TOP: -3px; HEIGHT: 50px; BACKGROUND-COLOR: lightgrey" onmovestart=objstartTop() type="title" controltype="ep_band">ep_band1 类型:title</DIV> '
		var sBandHeader='<DIV onmove=bandmove() id=ep_band2 onresizeend=objendTop() onresizestart=objstartTop() onmoveend=objendTop() onresize=bandmove() style="BORDER-TOP: blue 1px solid; FONT-SIZE: 12px; LEFT: 0px; OVERFLOW: hidden; WIDTH: 728px; BORDER-BOTTOM: blue 1px solid; POSITION: absolute; TOP: 63px; HEIGHT: 42px; BACKGROUND-COLOR: lightgrey" onmovestart=objstartTop() type="mainheader" controltype="ep_band" dataset rowsperpage needblankrow="0"></DIV>'
		var sBandList='<DIV onmove=bandmove() id=ep_band3 onresizeend=objendTop() onresizestart=objstartTop() onmoveend=objendTop() onresize=bandmove() style="BORDER-TOP: blue 1px solid; FONT-SIZE: 12px; LEFT: 0px; OVERFLOW: hidden; WIDTH: 728px; BORDER-BOTTOM: blue 1px solid; POSITION: absolute; TOP: 104px; HEIGHT: 30px; BACKGROUND-COLOR: lightgrey" onmovestart=objstartTop() type="mainlist" controltype="ep_band" dataset="'+datasetid+'" rowsperpage needblankrow="0" needblandrow="1"></DIV>'
		var sMemo='<DIV id=ep_memo1 title='+sTitle+' style="BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; FONT-WEIGHT: bold; FONT-SIZE: 18px; LEFT: 200px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: medium none; WIDTH: 401px; PADDING-TOP: 0px; BORDER-BOTTOM: medium none; FONT-FAMILY: 宋体; POSITION: absolute; TOP: 19px; HEIGHT: 25px; TEXT-ALIGN: center" controltype="ep_memo">'+sTitle+'</DIV>'
		var sHeaderMemo="";
		var sListMemo="";
		var fieldList = oDom.getElementsByTagName("field");
		var arrLabel=new Array();
		var arrLabelId= new Array();
		var arrLabelSize = new Array();
		var arrLabelSizeSum=0;
		var LabelWidth=new Array();
		var LabelLeft=new Array();
		var len=fieldList.length-14;
		for (var i=0;i<len;i++){
			arrLabel[i]=fieldList.item(i).childNodes(2).text;	
			arrLabelId[i]=fieldList.item(i).childNodes(0).text;	
			arrLabelSize[i]=ToFloat(fieldList.item(i).childNodes(3).text);
			arrLabelSizeSum+=arrLabelSize[i];
		}
		var divOuterHTML = '<DIV oncontrolselect=controlselect() id=SKbillsheet contentEditable=true unselectable="on" controlno="ep_memo:'+len*2+1+';ep_band:3;Shape:0;Image:0;ep_subreport:0" ';
		divOuterHTML+='contxml="<root><ep_band><id>ep_band1</id><id>ep_band2</id><id>ep_band3</id></ep_band>';
		divOuterHTML+='<ep_memo>';
		for (var i=0;i<len;i++){
			if(i==0){
				LabelLeft[i]=3;
				LabelWidth[i]=Math.ceil((pagewidth-leftmargin-rightmargin -LabelLeft[i] )*3.78*(arrLabelSize[i]/arrLabelSizeSum));
			}
			else
			{
				LabelLeft[i]=Math.ceil(LabelLeft[i-1]+LabelWidth[i-1]-1);
				LabelWidth[i]=Math.ceil((pagewidth-19)*3.78*(arrLabelSize[i]/arrLabelSizeSum));
			}
			sHeaderMemo+='<DIV id='+arrLabelId[i] +' style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; LEFT:'+LabelLeft[i]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[i]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: 75px; HEIGHT: 30px; TEXT-ALIGN: center" controltype="ep_memo">'+arrLabel[i]+'</DIV>'
			sListMemo+='<DIV id=txt'+arrLabelId[i] +' title="[<'+datasetchn+'.'+arrLabel[i]+'>]" style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; LEFT: '+LabelLeft[i]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[i]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: 104px; HEIGHT: 30px; TEXT-ALIGN: center" controltype="ep_memo">[<'+datasetchn+'.'+arrLabel[i]+'>]</DIV>'
			divOuterHTML+='<id>'+arrLabelId[i]+'</id>';
			divOuterHTML+='<id>txt'+arrLabelId[i]+'</id>';
		}
		divOuterHTML+='</ep_memo><Shape/></root>">';	
		var sXmlFile=spage;
		sXmlFile +="<content>";
		sXmlFile +="<fcbillsheet><![CDATA[ ";
		sXmlFile += divOuterHTML+sBandTitle+sBandHeader+sBandList+sMemo+sHeaderMemo+sListMemo+"</DIV>";
		sXmlFile +=" ]]></fcbillsheet>";
		sXmlFile +="</content>";
		sXmlFile = escape(sXmlFile);
		sRet = SavePrnFile("writefile",curtypeid+"_"+curtempid + ".xml",sXmlFile)
		if (sRet=="ok"){
			alert(curtypeid+"_"+curtempid+".xml文件生成成功!")
			blnChange = false
		}else{
			alert(sRet)	;
		}
	}
//

	function markCardFile(curtypeid,curtempid,sTitle,sDataSet){
		//alert("保存方法--markCardFile");
		
		var pagewidth=210;
		var pageheight=297;	
		var leftmargin = 8;
		var rightmargin = 8;
		var zoomwidth = 10;
		var bandwidth = pagewidth -leftmargin-rightmargin-zoomwidth;
		var LabelWidth=new Array();
		var LabelLeft=new Array();
		LabelWidth[0] =Math.ceil(bandwidth*0.1*3.78);
		LabelWidth[1] =Math.ceil(bandwidth*0.4*3.78);
		LabelLeft[0] =Math.ceil((zoomwidth/2)*3.78);
		LabelLeft[1] =LabelLeft[0]+LabelWidth[0]-1;
		LabelLeft[2] =LabelLeft[1] + LabelWidth[1]-1;
		LabelLeft[3] =LabelLeft[2] + LabelWidth[0]-1;
		var oDom=Printer.arrDataSets[sDataSet].oDom;
		var spage="<page>";
		spage+="<papersize>A4</papersize>";
		spage+="<pagewidth>"+pagewidth+"</pagewidth>";
		spage+="<pageheight>"+pageheight+"</pageheight>";
		spage+="<orientation>1</orientation>";
		spage+="<margintop>8</margintop>";
		spage+="<marginleft>" + leftmargin + "</marginleft>";
		spage+="<marginright>"+ rightmargin + "</marginright>";
		spage+="<marginbottom>8</marginbottom>";
		spage+="<header></header>";
		spage+="<footer></footer>";
		spage+="</page>";
		var datasetid;
		for(var j=0;j<Printer.datasets.length;j++){
			if(Printer.datasets[j].chnname == sDataSet){
				datasetid = Printer.datasets[j].id;
				break;
			}
		}
		var datasetchn =sDataSet;
		var sCardMemo="";
		var fieldList = oDom.getElementsByTagName("field");
		var arrLabel=new Array();
		var arrLabelId= new Array();
		var arrLabelSize = new Array();
		var arrLabelTop = new Array();
		var arrLabelSizeSum=0;
		var len=fieldList.length;
		for (var i=0;i<len;i++){
			arrLabel[i]=fieldList.item(i).childNodes(2).text;	
			arrLabelId[i]=fieldList.item(i).childNodes(0).text;	
			arrLabelSize[i]=ToFloat(fieldList.item(i).childNodes(3).text);
			arrLabelSizeSum+=arrLabelSize[i];
			if(i==0){
				arrLabelTop[0]=83;
			}
			else
			{
				if(Math.ceil(i/2) ==i/2){
					arrLabelTop[i]=arrLabelTop[i-1]+30-1;
				}
				else{
					arrLabelTop[i]=arrLabelTop[i-1];
				}
			}
		}
		var memoHeight = len * 15+40;
		var sBandTitle='<DIV onmove=bandmove() id=ep_band1 onresizeend=objendTop() onresizestart=objstartTop() onmoveend=objendTop() onresize=bandmove() style="BORDER-TOP: blue 1px solid; FONT-SIZE: 12px; LEFT: 0px; OVERFLOW: hidden; WIDTH: 728px; BORDER-BOTTOM: blue 1px solid; POSITION: absolute; TOP: -3px; HEIGHT: 50px; BACKGROUND-COLOR: lightgrey" onmovestart=objstartTop() type="title" controltype="ep_band">ep_band1 类型:title</DIV> '
		var sBandList='<DIV onmove=bandmove() id=ep_band2 onresizeend=objendTop() onresizestart=objstartTop() onmoveend=objendTop() onresize=bandmove() style="BORDER-TOP: blue 1px solid; FONT-SIZE: 12px; LEFT: 0px; OVERFLOW: hidden; WIDTH: 728px; BORDER-BOTTOM: blue 1px solid; POSITION: absolute; TOP: 53px; HEIGHT:'+memoHeight+'px; BACKGROUND-COLOR: lightgrey" onmovestart=objstartTop() type="mainlist" controltype="ep_band" rowsperpage dataset="item" needblankrow="0">ep_band2 类型:mainlist 数据集:item</DIV>'
		var sMemo='<DIV id=ep_memo1 title='+sTitle+' style="BORDER-RIGHT: medium none; PADDING-RIGHT: 0px; BORDER-TOP: medium none; PADDING-LEFT: 0px; FONT-WEIGHT: bold; FONT-SIZE: 18px; LEFT: 200px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: medium none; WIDTH: 401px; PADDING-TOP: 0px; BORDER-BOTTOM: medium none; FONT-FAMILY: 宋体; POSITION: absolute; TOP: 19px; HEIGHT: 25px; TEXT-ALIGN: center" controltype="ep_memo">'+sTitle+'</DIV>'
		var divOuterHTML = '<DIV oncontrolselect=controlselect() id=SKbillsheet contentEditable=true unselectable="on" controlno="ep_memo:'+len*2+1+';ep_band:2;Shape:0;Image:0;ep_subreport:0" ';
		divOuterHTML+='contxml="<root><ep_band><id>ep_band1</id><id>ep_band2</id></ep_band>';
		divOuterHTML+='<ep_memo>';
		for (var i=0;i<len;i++){
			if(Math.ceil(i/2) ==i/2){
				sCardMemo+='<DIV id='+arrLabelId[i] +' style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; LEFT: '+LabelLeft[0]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[0]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: '+arrLabelTop[i]+'px; HEIGHT: 30px; TEXT-ALIGN: right" controltype="ep_memo">'+arrLabel[i]+'</DIV>'
				sCardMemo+='<DIV id=txt'+arrLabelId[i] +' title="[<'+datasetchn+'.'+arrLabel[i]+'>]" style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 5px; FONT-SIZE: 12px; LEFT: '+LabelLeft[1]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[1]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: '+arrLabelTop[i]+'px; HEIGHT: 30px; TEXT-ALIGN: left" controltype="ep_memo">[<'+datasetchn+'.'+arrLabel[i]+'>]</DIV>'
			}
			else
			{
				sCardMemo+='<DIV id='+arrLabelId[i] +' style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 0px; FONT-SIZE: 12px; LEFT: '+LabelLeft[2]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[0]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: '+arrLabelTop[i]+'px; HEIGHT: 30px; TEXT-ALIGN: right" controltype="ep_memo">'+arrLabel[i]+'</DIV>'
				sCardMemo+='<DIV id=txt'+arrLabelId[i] +' title="[<'+datasetchn+'.'+arrLabel[i]+'>]" style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 0px; BORDER-TOP: 1px solid; PADDING-LEFT: 5px; FONT-SIZE: 12px; LEFT: '+LabelLeft[3]+'px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: '+LabelWidth[1]+'px; PADDING-TOP: 8px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: ; POSITION: absolute; TOP: '+arrLabelTop[i]+'px; HEIGHT: 30px; TEXT-ALIGN: left" controltype="ep_memo">[<'+datasetchn+'.'+arrLabel[i]+'>]</DIV>'
			}
			divOuterHTML+='<id>'+arrLabelId[i]+'</id>';
			divOuterHTML+='<id>txt'+arrLabelId[i]+'</id>';
		}
		divOuterHTML+='</ep_memo><Shape/></root>">';	
		var sXmlFile=spage;
		sXmlFile +="<content>";
		sXmlFile +="<fcbillsheet><![CDATA[ ";
		sXmlFile += divOuterHTML+sBandTitle+sBandList+sMemo+sCardMemo+"</DIV>";
		sXmlFile +=" ]]></fcbillsheet>";
		sXmlFile +="</content>";
		sXmlFile = escape(sXmlFile);
		sRet = SavePrnFile("writefile",curtypeid+"_"+curtempid + ".xml",sXmlFile)
		if (sRet=="ok"){
			alert(curtypeid+"_"+curtempid+".xml文件生成成功!")
			blnChange = false
		}else{
			alert(sRet)	;
		}
	}