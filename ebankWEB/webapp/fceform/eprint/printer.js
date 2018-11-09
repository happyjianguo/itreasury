var Printer = {
typeid		: "fc001",
tempid		: "temp001",
tempname	: "tempname",
djpara		: "<root></root>" ,
datasets	: new Array() ,
arrDataSets	: new Array() ,
clear		: function() {this.datasets = new Array() ; this.arrDataSets=new Array();} ,
AddDataset	: function(id,chnname,xml) {
				var l = this.datasets.length ;
				var o = new ep_dataset() ;
				o.id = id ;
				o.chnname = chnname ;
				o.xml = xml ;
				this.datasets[l] = o ;
			} ,
inited		: false,	
oXmlTemp	: null,		
operate		: "preview",  
relations	: new Array(), 
AddRelation 	: function(strRelation){
				var l = this.relations.length;
				this.relations[l] = strRelation;
			},
Init		: function(){},	
Design		: function(tempid){
				this.tempid=tempid;
				if(this.inited == false ){ this.Init() ;_LoadDatasetsToDom();}
				_PrintDesignDirect(this.typeid,tempid) ;
			} ,
Preview		: function(tempid,zoomSize,zoomWidth,zoomHeight){
				
				this.tempid=tempid;	
				if(this.inited == false ){ this.Init() ; _LoadDatasetsToDom();}
				this.operate = "preview";
				if (typeof zoomSize=="undefined" || typeof zoomWidth=="undefined" || typeof zoomHeight=="undefined"){
					this.paperSize = null;
					this.pageWidth = null;
					this.pageHeight = null;
				}
				else{
					this.paperSize = zoomSize;
					this.pageWidth = zoomWidth;
					this.pageHeight = zoomHeight;
				}
				try{
					fc_eprint_before_preview();
				}catch(e){}
				_Preview(tempid) ;
				try{
					fc_eprint_after_preview();
				}catch(e){}
			},
Print		: function(tempid,bprompt,zoomSize,zoomWidth,zoomHeight){
				this.tempid=tempid;
				if (this.inited == false){ this.Init();_LoadDatasetsToDom();}
				this.operate="print";
				if (bprompt) this.operate = "printtrue";
				if (typeof zoomSize=="undefined" || typeof zoomWidth=="undefined" || typeof zoomHeight=="undefined"){
					this.paperSize = null;
					this.pageWidth = null;
					this.pageHeight = null;
				}
				else{
					this.paperSize = zoomSize;
					this.pageWidth = zoomWidth;
					this.pageHeight = zoomHeight;
				}
				_Preview(tempid,bprompt);
			},
LoadDatasetsToDom	:function(){_LoadDatasetsToDom();},		
paperSize	: null, 
pageWidth	: null,
pageHeight	: null, 
oBillXml	: null, 
oPrtPara 	: new Object()
};
function ep_dataset(id,chnname,xml) {
this.id = id
this.chnname = chnname
this.xml = xml
}
function objDataSet(oDom,arrField,curRow){
this.oDom = oDom;
this.arrField = arrField;
this.curRow = curRow;
}
function _LoadDatasetsToDom(){
Printer.arrDataSets = new Array();
for (var i=0;i<Printer.datasets.length;i++){
var oDom =SetDom(Printer.datasets[i].xml);
var arrField = new Array();
var fieldList = oDom.getElementsByTagName("field");
for (var j=0;j<fieldList.length;j++){
arrField[fieldList.item(j).childNodes(2).text] = j;
}
Printer.arrDataSets[Printer.datasets[i].chnname] = new objDataSet(oDom,arrField,0);
}
}
function _PrintDesignDirect(typeid,tempid) {
if(isSpace(tempid)){
var arr = new Array()
arr[0] = typeid
var arrRet = DjOpen("ep_openprn",arr,"展现","有模式窗口")
if(isSpace(arrRet) == false)
window.open(location.protocol+"//"+location.host+fcpubdata.Path + "/fceform/eprint/design.htm?typeid="+arr[0]+"&tempid="+arrRet[0]);
}else{
var sRet = window.open(location.protocol+"//"+location.host+fcpubdata.Path + "/fceform/eprint/design.htm?typeid="+typeid+"&tempid="+tempid) ;
}
}
function _Preview(tempid) {
	var tempfile = Printer.typeid + "_" + tempid + ".xml";
	
	//var oTemp =  SetDomFile(location.protocol+"//"+location.host+fcpubdata.Path + fcpubdata.designFilePath + tempfile);
	
	var oTemp =  SetDomFile(location.protocol+"//"+location.host+fcpubdata.Path + "/fceform/prnfile/" + tempfile);
	
	if (oTemp.documentElement==null){
		alert("模板文件：" + tempfile + "不存在，请先保存！");
		return;
	}
	Printer.oXmlTemp = oTemp;
	if (document.all("div_printPage")==null){
		document.body.insertAdjacentHTML("BeforeEnd", "<div id='div_printPage' style='position:absolute; left:100;  top:202; z-index:2;'></div>");
	}
	document.all("div_printPage").innerHTML="<iframe name='_print_iframe' src='"+location.protocol+"//"+location.host+fcpubdata.Path+"/fceform/eprint/printdata.htm' width='0' height='0' border=1 NORESIZE=NORESIZE SCROLLING=no MARGINWIDTH=0 MARGINHEIGHT=0 FRAMESPACING=0 FRAMEBORDER=0></iframe>";		
}