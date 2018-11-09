
	function OpenBillMain(tmpNo,djNo,iAction,oRequest,arrWorkFlow,ComputerName,modNo,openMode,sOpenCommand,sVersion,sTitle){			
		ShowWait("正在打开表单....");
		if(tmpNo == "ziliao_hp" || tmpNo == "jxc_hp" || tmpNo == "saleMain" || tmpNo == "stockMain"){
			ShowWait("end");
		}
		var sPathBase=location.protocol + "//"+location.host+fcpubdata.Path+"/fceform/";
		if(isSpace(sVersion)){			
			var s = location.href ;
			var ss = sPathBase + "common/djempty.htm" ;
			if(s==ss){
				sVersion="测试";
			}else{
				sVersion="直接";   
			}
		}
		if(tmpNo == "userfunction1" ) sVersion = "直接";
		if(typeof tmpNo != "undefined" ) tmpNo=trim(tmpNo);
		var htmfile="djframe.htm?isfile=release";
		var posstyle="";
		var arr=new Array();
		var curdjid="" ; 
		if(sVersion=="测试"){
			var sPos=fc_select("select djposition,dj_name,djid from FC_BILLZL where djsn='"+tmpNo+"'",1,1);
			var oXml=SetDom(sPos);
			sPos=oXml.documentElement.childNodes(0).xml;
			curdjid=oXml.documentElement.childNodes(0).childNodes(2).text;
			var oXml=SetDom(sPos);
			sPos=oXml.documentElement.childNodes(0).text;
			var arrPos=sPos.split(",");
			if(arrPos.length>=6){
				if(typeof iAction =="undefined" || iAction==0 ){
					if(arrPos[6]=="新增")
						iAction=1;
					if(arrPos[6]=="修改")
						iAction=2;
					if(arrPos[6]=="展现")
						iAction=3;
				}
				htmfile = "djframe.htm?isfile=test"	;
			}	
			if(arrPos.length>=4){
				var iheight=parseInt(arrPos[3])	;
				posstyle=";dialogWidth:"+(parseInt(arrPos[2]))+"px;dialogHeight:"+iheight+"px";
			}
			if(arrPos.length>=5){
				if(arrPos[4]=="居中")
					posstyle+=";center:yes;";
				else
					posstyle+=";dialogLeft:"+arrPos[0]+"px;dialogTop:"+arrPos[1]+"px;";
			}	
			if(fcpubdata.databaseTypeName == "oracle"){
				arr[0]=loadClob("<no>xmltext</no><no>"+curdjid+"</no>") ; 		
				arr[0]=UnTransSql(arr[0]);
			}else{
				arr[0]=selecttext("select xmltext from FC_BILLZL where djsn='"+tmpNo+"'",1,1);
			}
			if(isSpace(openMode)) {
				try{
					openMode = arrPos[7];
				}catch(e){		openMode="当前窗口";}
			}
			arr[7]=openMode;  
			arr[8]=sOpenCommand;
			if(isSpace(sTitle)){
				arr[9]=oXml.documentElement.childNodes(1).text+"["+tmpNo+"]";
			}else{
				arr[9]=sTitle;
			}
		}else if(sVersion == "测试文件") { 
			htmfile="djframe.htm?isfile=yes";
			arr[0]=readdesignhtml("<no>"+tmpNo+".dj</no><no></no>"+"<No>"+fcpubdata.Path+"</No>");
			if(isSpace(sTitle) == false){
				arr[9]=sTitle;
			}	
		} else { 
			arr[0]=tmpNo+".htm";
			if(isSpace(sTitle)){
				arr[9]=arr[0];
			}else{
				arr[9]=sTitle;
			}	
			if(posstyle == ""){
				var oXmlFile = SetDomFile(sPathBase+"billpos.xml") ;
				var oNode = oXmlFile.documentElement.selectSingleNode("//tr[td='" + tmpNo + "']") ;
				if(oNode != null) {
					var sPos = oNode.childNodes(1).text ;
					var arrPos=sPos.split(",");
					if(arrPos.length>=4){
						posstyle=";dialogWidth:"+(parseInt(arrPos[2]))+"px;dialogHeight:"+(parseInt(arrPos[3]))+"px";
					}
					if(arrPos.length>=5){
						if(arrPos[4]=="居中")
							posstyle+=";center:yes;";
						else
							posstyle+=";dialogLeft:"+arrPos[0]+"px;dialogTop:"+arrPos[1]+"px;";
					}	
				}
			}
			try{
				var sdjtype = parent.Request.QueryString("djtype").toString();
				if(sdjtype != "undefined"){
					htmfile=htmfile+"&djtype="+sdjtype ;
				}
			}catch(e){
			};
		}
		var sPath=sPathBase+"common/";
		if(isSpace(djNo) && typeof djNo != "object"){
			djNo="";
		}
		arr[1]=djNo;
		if(isSpace(iAction) || iAction == "0")iAction=0;
		if(iAction=="1")iAction=1;
		if(iAction=="2")iAction=2;
		if(iAction=="3")iAction=3;
		arr[2]=iAction;
		arr[3]=oRequest;
		arr[4]=arrWorkFlow;  
		arr[5]=ComputerName;  
		arr[6]=modNo;  
		htmfile = htmfile +"&djsn="+tmpNo;	
			
		if(openMode=="有模式窗口"){
			var sRet=window.showModalDialog(sPath+htmfile,arr,"resizable:yes;status:no;"+posstyle) ;
		}else if(openMode=="无模式窗口"){
			var sRet=window.showModelessDialog(sPath+htmfile,arr,"resizable:yes;status:no;"+posstyle); 
		}else { 
			var spathwin = parent.location.pathname;
			if(spathwin.indexOf('djframe.htm') >= 0){
				top.arr = arr;
			}else{
				parent.arr = arr;
			}
			window.open(sPath+htmfile,fcpubdata.BillOpenWinName);
		}
		return sRet;
	}

//

	function ChangeWinTitle(sTitle){
		document.title=sTitle;
		parent.document.title=sTitle;
		parent.parent.document.title=sTitle;
	}

	function DjOpenMenu(tmpNo,sTitle){
		DjOpen(tmpNo,"","","当前窗口","直接",sTitle);
	}

	function DjOpenTest(tmpNo){
		DjOpen(tmpNo,"","","","测试","",parent.Request);
	}

	function DjOpenTestFile(tmpNo){
		DjOpen(tmpNo,"","","","测试文件","",parent.Request);
	}

//

	function selecttext(sSql,PageNo,PageSize) {
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
		var sXml="<No>"+sql1+"</No>"+"<No1>"+PageNo+"</No1>"+"<No2>"+PageSize+"</No2>";
		var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?selecttext",sXml);
		return retX;
	}

//

	function OpenBill(tmpNo,djNo,iAction,UserID){
		var suser="";
		try{suser=getuser();}catch(E){}
		var sRet= OpenBillMain(tmpNo,djNo,iAction,suser,"","","EE","有模式窗口","");
		return sRet;
	}

	function OpenBillMenu(tmpNo){
		window.open(fcpubdata.Path+"/fceform/common/djframe.htm?djtype=LH&djsn="+tmpNo,fcpubdata.BillOpenWinName);
	}
	
//

	function DjOpen(djsn,updataset,opentype,sModal,sVersion,sTitle,oRequest){
		var sAction = "";
		try {
			if(IsSpace(opentype)) sAction = oRequest.QueryString("opentype").toString() ; 
		}catch(e){}
		try {
			if(IsSpace(updataset)) updataset = oRequest.QueryString("para").toString() ; 
		}catch(e){}
		var iAction=0;
		if(opentype == "新增" || sAction == "1") {
			iAction=1;
			updataset="";
		}
		if(opentype == "修改"  || sAction == "2" ) iAction=2;
		if(opentype == "展现"  || sAction == "3" ) iAction=3;
		if(typeof sModal == "undefined"){
			return OpenBillMain(djsn,updataset,iAction,oRequest,"","","","有模式窗口","",sVersion);
		}else{
			return OpenBillMain(djsn,updataset,iAction,oRequest,"","","",sModal,"",sVersion,sTitle);
		}
	}