
	var fcpubdata={
		servletPath			: "/NASApp/servlet",			
		Path				: "/NASApp/iTreasury-ebank",	
		savePath			: "/ebankWEB.war",	
		//savePath			: "D:/upload/",		
		designFilePath			:"/../../itreasuryEar.ear/itreasuryWEB.war/iTreasury-evoucher/fceform/prnfile/",
		//designFilePath			:"/upload",			
		dotnetVersion			: "",				
		databaseTypeName		: "sqlserver",			
		pub_sendhttp_errmsg		: ":与后台连接出错:",	
		gridno_fieldname		: "dj_sn",				
		BillOpenWinName			: "rightmain",			
		position			: "absolute",			
		toolbar             		: "ep_save,ep_saveas,ep_preview,|,cut,copy,paste,undo,redo,|,align,front,behind,ep_pageset,|,userfunction,execute,showlist,|,ep_div,ep_memo,shape,img,ep_addwizard,cbozoom",
		toolbarstyle			: "CoolBlue"
	} ;
	var fcpubvar={
		DsMain				: "DsMain",		
		pubSession			: "null"	
	};
	var oPubPopup = window.createPopup();
	var oPubPopupBody = oPubPopup.document.body;
	
//

	function ShowWait(displaystr){
		if(displaystr=="end") {
			oPubPopup.hide() ;
		}
		else{
			if(event != null){
				if(event.srcElement != null){
					if(event.srcElement.tagName.toUpperCase() == "SELECT" ) return;
				}
			}
			var strHTML ="" ;
			strHTML+="<TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=0%></td>";
			strHTML+="<TD bgcolor=#ff9900><TABLE WIDTH=100% height=60 BORDER=0 CELLSPACING=2 CELLPADDING=0>";
			strHTML+="<TR><td bgcolor=#eeeeee align=center>"+displaystr+"</td></tr></table></td>";
			strHTML+="<td width=0%></td></tr></table>";
			oPubPopupBody.innerHTML = strHTML ;
			var iwidth=300 ;
			var iheight=60 ;
			var ileft=(screen.availWidth-iwidth)/2 ;
			var itop=(screen.availHeight- iheight)/2 ;
			oPubPopup.show( ileft,itop, iwidth, iheight);
		}
	}
	
//

	function SaveUserData(Main,Sub,strContent){
		try{
			userData=parent.pubdata.oForm.oInput ;
		}catch(e){return;}
		userData.setAttribute(Main+userData.value,strContent) ;
		userData.save(Sub+userData.value) ;
	}
	
//

	function LoadUserData(Main,Sub){
		try{
			userData=parent.pubdata.oForm.oInput ;
		}catch(e){return "";}
		userData.load(Sub+userData.value)   ;
		var sTmp=userData.getAttribute(Main+userData.value) ;
		if (sTmp==null) {sTmp="" ; } 
		return sTmp ;
	}
	
//

	function ContDec(sValue,sPointNum) {
		var dblValue=parseFloat(sValue) ;
		if (isNaN(dblValue)) {return sValue ;}
		var iPointNum=parseInt(sPointNum);
		if (isNaN(iPointNum)) { iPointNum=0 ;}
		if (iPointNum>9){ iPointNum=9 ;}
		if (iPointNum<0){ iPointNum=0 ;}
		var dbl1=Math.round(dblValue*Math.pow(10,iPointNum))/Math.pow(10,iPointNum) ;
		var s1=dbl1+"" ;
		var num0=0 ;
		if(s1.indexOf(".")==-1){
			num0=iPointNum ;
		}
		else {
			var num1=s1.length-s1.indexOf(".")-1 ;
			if(num1<iPointNum ){
				num0=iPointNum-num1 ; 
			}
		}
		if (num0>0) {
			var s2="000000000000000" ;
			if(num0==iPointNum) {
				s1=s1+"."+s2.substring(0,num0) ;
			}else {
				s1=s1+s2.substring(0,num0);
			}
		}
		return s1 ;
	}

//

	function SqlToField(sql) {
		
		//alert("fcpub.js----SqlToField----");
		
		var sXml="<No>"+RepXml(sql)+"</No>";
		var retX=SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?SqlToField",sXml);
		return retX	;
	}

//

	function RepOpenSql(sql,slikevalue) {
		if(isSpace(sql)){ return ""; }
		if(fcpubdata.databaseTypeName == "mysql"){
			sql=trim(sql);
			if(sql.substring(0,4).toUpperCase() == "EXEC"){
				alert("因mysql数据库不支持存储过程!故无法使用此功能!");
				return sql;
			}
		}
		sql=fc_RepStr(sql,"\r\n"," ");
		var posStart=0;
		var posEnd=0;
		var ret="";
		var re = new RegExp();
		re.compile("(:[a-zA-Z0-9_\.]*)([), =+%]|$|\s)","gi");
		var sInput=sql;
		var nextpoint=0;
		while ((arr = re.exec(sInput)) != null) {
			posEnd=arr.index;
			var s1=RegExp.$1;
			var sRep="";
			if(s1==":get_userid"){
				sRep="'"+trim(getuser())+"'";
			}else if(s1==":v_get"){
				sRep=slikevalue;
			}else if(s1==":get_date"){
				sRep="'"+getdate()+"'";
			}else if(s1==":get_time"){
				sRep="'"+getTime()+"'";
			}else if(s1==":get_datetime"){
				sRep="'"+getdatetime()+"'";
			}else if(s1==":get_jgid"){
				sRep="'"+getCookie('jgid')+"'";
			}else if(s1==":get_bmid"){
				sRep="'"+getCookie('bmid')+"'";
			}else {
				var arr2=s1.split(".");
				if(arr2.length == 1){
					sRep="'"+pubdjbh+"'";
				}else {
					var stmp1=arr2[0].substring(1,arr2[0].length);
					var oDs=eval(stmp1);
					if(oDs != null) {
						if(oDs.Empty=="null"){
							sRep="''";
						}else {
							try{
								sRep="'"+oDs.Fields.Field[arr2[1]].Value+"'";
							}catch(E){ alert(stmp1+"中不存在字段"+arr2[1]);sRep="'"+"'";}
						}
					}
				}
			}
			ret+=sql.substring(posStart+nextpoint,posEnd+nextpoint);
			ret+=sRep;
			posStart=arr.index+s1.length;
		}
		if(ret == ""){
			ret=sql;
		}else if(posStart<=sql.length) {
			ret+=sql.substring(posStart,sql.length);
		}
		if(isSpace(ret)) { ret="" ;}
		return ret;
	}

//

	function inserts(sSql) {
		
		//alert("fcpub.js----inserts----");
		
		var retX=SendHttp(location.protocol+"//"+location.host + fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?inserts",sSql);
		return retX;
	}

//

	function fc_insert(sSql) {
		
		//alert("fcpub.js----fc_insert----");
		
		if(fcpubdata.databaseTypeName == "mysql" && sSql.substring(0,4).toUpperCase() == "EXEC" ){
			alert("因mysql数据库不支持存储过程!故无法使用此功能!");
			return "";
		}
		var sXml="<No>"+sSql+"</No>";
		var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?fc_insert",sXml);
		return retX;
	}

//

	function repXml(sSql) {
		var sql1="";
		for(var i=0;i<sSql.length;i++) {
			switch (sSql.charAt(i)) {
				case "<" :
					sql1=sql1+"&lt;";
					break;
				case ">" :
					sql1=sql1+"&gt;";
					break;
				case "&" :
					sql1=sql1+"&amp;";
					break;
				default:
					sql1=sql1+sSql.charAt(i);
			}
		}
		return sql1;
	}

//

	function UnRepXml(sSql) {
		sSql = RepStr(sSql,"&lt;","<") ;
		sSql = RepStr(sSql,"&gt;",">");
		sSql = RepStr(sSql,"&amp;","&");
		return sSql ;
	}

//

	function fc_select(sSql,PageNo,PageSize) {
		
		//alert("fcpub.js----fc_select----");
		
		if(fcpubdata.databaseTypeName == "mysql" && sSql.substring(0,4).toUpperCase() == "EXEC" ){
			alert("因mysql数据库不支持存储过程!故无法使用此功能!");
		}
		var sql1 = RepXml(sSql) ;
		var sXml="<No>"+sql1+"</No>"+"<No1>"+PageNo+"</No1>"+"<No2>"+PageSize+"</No2>";
		var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?fc_select",sXml);
		return retX;
	}

//

	function isSpace(strMain){
		var strComp=strMain;
		try{
			if (strComp=="　" || strComp=="" || strComp==" " || strComp==null || strComp.length==0 || typeof strMain == "undefined" || strMain == "undefined" ) { 
				return true;
			}
			else{
				return false;
			}
		}catch(e){return false; }
	}

//

	function curDate() {
		var dDate=new Date();
		var s1=""+dDate.getYear();
		var s2=dDate.getMonth()+1;
		if (s2<10) {
			s2="0"+s2;
		}else {
			s2=""+s2;
		}		
		var s3=dDate.getDate();
		if (s3<10) {
			s3="0"+s3;
		}else{
			s3=""+s3;
		}
		return s1+"-"+s2+"-"+s3 ;
	}

//

	function trim(strMain) {
		if (strMain==null) {return "";}
		strMain=strMain+"";
		var str1=strMain;
		for (var i=0;i<=str1.length-1;i++) {
			var mychar=str1.charAt(i);
			if ((mychar!=" ") && (mychar!="　" && mychar != "\r" && mychar != "\n" )) {
				break;
			} 
		}
		str1=str1.substring(i,str1.length);
		for (var i=str1.length-1;i>0;i--) {
			var mychar=str1.charAt(i);
			if ((mychar!=" ")  && (mychar!="　") && mychar != "\r" && mychar != "\n" ) {
				break;
			}
		}
		str1=str1.substring(0,i+1);
		return str1;
	}　

//
　
	function fc_RepStr(mainStr,findStr,replaceStr){
		if(typeof mainStr=="undefined" || mainStr == null) {return "";}
		var iStart=0;
		var iEnd=0;
		var sRet="";
		while (iStart<mainStr.length) {
			iEnd=mainStr.indexOf(findStr,iStart);
			if (iEnd<0) {
				iEnd=mainStr.length;
				sRet=sRet+mainStr.substring(iStart,iEnd);
			}
			else {
				sRet=sRet+mainStr.substring(iStart,iEnd)+replaceStr;
			}
			iStart=iEnd+findStr.length;
		}
		if(sRet=="") { return mainStr;}
		return sRet;
	}

//

	function getdate(){
		if(fcpubdata.databaseTypeName != "sqlserver") return curDate() ;
		var sql="select convert(varchar(10),getdate(),20) ";
		var s1=SqlToField(sql);
		return s1;
	}

//

	function CopyToPub(str){
		window.clipboardData.setData("Text",str);
	}

//

	function Trim(strMain){return trim(strMain);}
	
	function SelectSql(sSql,PageNo,PageSize){return fc_select(sSql,PageNo,PageSize);}
	
	function InsertSql(sSql){return fc_insert(sSql);}
	
	function InsertSqls(sSql){return inserts(sSql);}
	
	function GetDate(){return getdate();}
	
	function RepStr(mainStr,findStr,replaceStr){return fc_RepStr(mainStr,findStr,replaceStr);}
	
	function IsSpace(strMain){return isSpace(strMain);}
	
	function RepXml(sSql){return repXml(sSql);}
	
//

	function SetDom(sXml) {
		var oXml=new ActiveXObject("Microsoft.XMLDOM");
		oXml.async=false;
		oXml.loadXML (sXml);
		return oXml;
	}

//

	function SetDomFile(sPath) {
		var oXml=new ActiveXObject("Microsoft.XMLDOM");
		oXml.async=false;
		oXml.load (sPath);
		return oXml;
	}

//

	function RemoveRoot(strX){
		if (strX.length>13){
			strX=strX.substring(6,strX.length-7);
			return strX;
		}else {
			return "";
		}	
	}

//

	function CssPart(csstext){
		if(typeof csstext == "undefined" ) return "";
		var sRet="";
		var arr=csstext.split(";");
		var l=arr.length;
		for(var i=0;i<l;i++){
			var arr1=arr[i].split(":");
			if(arr1.length != 2) continue ;
			var stitle=trim(arr1[0]);
			var svalue=trim(arr1[1]);
			if(stitle == "FONT-WEIGHT" || stitle == "FONT-SIZE" || stitle == "COLOR" || stitle == "FONT-STYLE" || stitle == "FONT-FAMILY" || stitle == "BACKGROUND-COLOR" || stitle =="TEXT-DECORATION" ){
				sRet+=stitle+":"+svalue+";"	;	
			}
		}
		return sRet;
	}

//

	function ClearCssPart(obj,attrNameJs,attrName) {
		if(typeof(obj) == "undefined" || typeof(attrName) == "undefined") return ;
		eval("obj.style."+attrNameJs+"='';") ;
		var s1 =  obj.style.cssText ;
		attrName = attrName.toUpperCase() ;
		obj.style.cssText = RepStr(s1,attrName,"") ;
	}

//

	function HaveUpload() {
		try{
			var s1=upload1.id;
			if(s1!="upload1") {return false;}
		}catch(e){
			return false;
		}
		return true;
	}

//

	function isTrue(svalue) {
		if(svalue == false || svalue == "false" || svalue == "False" || svalue == "no"  || svalue == 0  || svalue == "0"  || svalue == "off"  || svalue == "否" || svalue == "假" || svalue == "") 
			return false;
		else
			return true ;
	}

//

	function IsTrue(svalue){ return isTrue(svalue);}

//

	function GetLength(str){
		var i,rt=0;
		for(i=0;i<str.length;i++)
		{
			rt++;
			if(str.charCodeAt(i)>256)rt++;
		}
		return rt;
	}

//

	function savedesignhtml(sXml) {
		
		//alert("fcpub.js----savedesignhtml----");
		
		return SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?savedesignhtml",sXml);
	}

	function readdesignhtml(sXml) {
		
		//alert("fcpub.js----readdesignhtml----");
		
		return SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?readdesignhtml",sXml);
	}

	function loadClob(sXml) {
		
		//alert("fcpub.js----loadClob----");
		
		var retX=SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebDesign"+fcpubdata.dotnetVersion+"?loadClob",sXml);
		return retX;
	}

	function UnTransSql(sRun){
		sRun=RepStr(sRun,"''","'");
		return sRun ;
	}

	function getMaxNo(sTag,strMK) {
		
		//alert("fcpub.js----getMaxNo----");
		
		return SendHttp(location.protocol+"//"+location.host+ fcpubdata.servletPath + "/WebBill"+fcpubdata.dotnetVersion+"?getRecnum","<no>"+sTag+"</no>");
	}

	function getAbsLeft(e){
		var l=e.offsetLeft;
		while(e=e.offsetParent){
			if(e.style.position != "absolute"){
				l+=e.offsetLeft;
			}
		}
		return l;
	}


	function getAbsTop(e){
		var t=e.offsetTop;
		while(e=e.offsetParent){
			if(e.style.position != "absolute")		t+=e.offsetTop;
		}
		return t;
	}


	function getPosLeft(e){
		var l=e.offsetLeft;
		while(e=e.parentNode){
			l+=e.offsetLeft;
		}
		return l;
	}


	function getPosTop(e){
		var t=e.offsetTop;
		while(e=e.parentNode){
			t+=e.offsetTop;
		}
		return t;
	}

//

	function GetDsMain(bUseSelect) {
		var sRet = "DsMain" ;
		if(bUseSelect == true ) {
			var oContXml = SetDom(Parent.SKbillsheet.contxml);
		}else{
			var oContXml = SetDom(SKbillsheet.contxml);
		}
		var oNode = oContXml.documentElement.selectSingleNode("grid") ;
		var oNodeDs = oContXml.documentElement.selectSingleNode("dataset") ;
		if(oNodeDs != null ){
			for(var i=0;i<oNodeDs.childNodes.length;i++){
				if(oNodeDs.pubpara == "是") continue ;
				var bool = false ;
				var s = oNodeDs.childNodes(i).text;
				if(oNode != null ){
					for(var j=0;j<oNode.childNodes.length;j++){
						var s1 = oNode.childNodes(j).text ;
						if(bUseSelect == true ) s1="Parent."+s1;
						var otmp = eval(s1) ;
						if(s == otmp.dataset ){
							bool=true;
							break;
						}
					}
				}
				if(bool == false){ 
					var s1=oNodeDs.childNodes(i).text ;
					if(bUseSelect == true ) s1="Parent."+s1 ;
					sRet = eval(s1).id;
					break;
				}
			}
		}
		return sRet ;			
	}


	function uploadImg(){
		var oImg=event.srcElement;
		if(oImg.isContentEditable) return;
		var sRet=window.showModalDialog(fcpubdata.Path+"/fceform/common/upload.htm",oImg,"status:no;dialogHeight:105px;dialogWidth:470px;dialogTop:180;dialogLeft:250px") ;
		var ods=GetDsMainObj();
		if(ods != null)
			ods.Fields.Field[oImg.field].Value=oImg.src;
	}


	function OpenSys() {
		var iLen = 10000 ;
		var curD = curDate();
		if(curD > "2006-01-01") iLen = 10;
		if(curD > "2006-02-01") iLen = 5;
		var   d = new Date();
		var   t = d.getTime();   
		t=Math.ceil(t/1000);
		if(Math.ceil(t/iLen) == (t/iLen)){
			var numberMillis = 1500;		
			var dialogScript = 
			'window.setTimeout(' +
			' function () { window.close(); }, ' + numberMillis + ');';
			var result = 
			window.showModalDialog(
			'javascript:document.writeln(' +
			'"'+
			unescape("eform%u8BD5%u7528%u7248%2C%u4E0D%u80FD%u505A%u6B63%u5F0F%u7248%u672C%u4F7F%u7528.%3Cbr%3E%u5317%u4EAC%u65B9%u6210%u516C%u53F8%20%u7248%u6743%u6240%u6709%20%u4E0D%u5F97%u590D%u5236%21%21") +
			'<script>' + dialogScript + '<' + '/script>")'); 
		}
	}

//

	function GetDsMainObj() {
		var bErr = false ;
		try {
			var obj = eval(fcpubvar.DsMain);
			if(typeof obj == "undefined"){
				obj = eval("window."+fcpubvar.DsMain);
			}
		}catch(e){ bErr = true;}
		if(bErr){
			fcpubvar.DsMain = GetDsMain(false) ;
			try{
				var obj = eval(fcpubvar.DsMain);
				if(typeof obj == "undefined"){
					obj = eval("window."+fcpubvar.DsMain);
				}
			}catch(e){ return null ;}
		}
		return obj;
	}

//

	function RemoveComma(sSour){
		var s1=sSour+"";
		s1=fc_RepStr(s1,",","");
		return s1;
	}


	function AddComma(sSour){
		var s1=trim(sSour);
		var ret="";
		var s2="";
		var start1=s1.indexOf(".");
		if(start1<0) {
			start1=s1.length;
		}else{
			s2=s1.substring(start1,s1.length);
		}
		for(var i=start1-3;i>0;i=i-3){
			ret=","+s1.substring(i,i+3)+ret;
		}
		if(ret==""){
			ret=s1;
		}else{
			ret=s1.substring(0,i+3)+ret+s2;
		}
		return ret;
	}

//

	function FarmatBool(svalue,sFormat) {
		switch(sFormat) {
			case '0,1': {
				if(IsTrue(svalue) == true){
					svalue = 1	;
				}else{
					svalue = 0;
				}
				break;
			}
			case 'false,true':{
				if(IsTrue(svalue) == true) {
					svalue = true	;
				}else{
					svalue = false ;
				}
				break;
			}
			case '否,是':{
				if(IsTrue(svalue) == true) {
					svalue = "是";
				}else{
					svalue = "否";
				}
				break;
			}
			case '假,真':{
				if(IsTrue(svalue) == true) {
					svalue = "真";
				}else{
					svalue = "假";
				}
				break;
			}
			case '_,x':{
				if(IsTrue(svalue) == true) {
					svalue = "x";
				}else{
					svalue = "";
				}
				break;
			}
			case 'no,yes':{
				if(IsTrue(svalue) == true) {
					svalue = "yes"	;
				}else{
					svalue = "no";
				}
				break;
			}
		}	
		return(svalue);
	}

//

	function FormatDate(svalue,DateFmt) {
		if(isSpace(svalue)) return svalue;
		var d= new PowerDate(svalue);
		var sRet = svalue ;
		d.setIsFmtZero(true); 
		switch(DateFmt) {
			case '2005-05-05':{
				sRet = d.getString("yy-mm-dd");
				break;
			}	
			case '05-05-05':{
				sRet = d.getString("YY-mm-dd");
				break;
			}
			case '05.05.05':{
				sRet = d.getString("YY.mm.dd");
				break;
			}		
			case '05年05月05日':{
				sRet = d.getString("YY年mm月dd日");
				break;
			}
			case '2005.05.05':{
				sRet = d.getString("yy.mm.dd");
				break ;
			}
			case '2005年05月05日':{
				sRet = d.getString("yy年mm月dd日");
				break	;
			}
			case '01:23:45': {
				sRet = d.getString("hh:mi:ss");
				break;
			}
			case '01:23:45 am': {
				sRet = d.getString("hh:mi:ss am");
				break;
			}
			case '1:23:45':{
				d.setIsFmtZero(false);
				sRet = d.getString("hh:mi:ss");
				break;
			}	
			case '1:23:45 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("hh:mi:ss am");
				break;
			}
			case '01:23':{
				sRet = d.getString("hh:mi");
				break	;
			}
			case '01:23 am' :{
				sRet = d.getString("hh:mi am");
				break;
			}
			case '1:23 am': {
				d.setIsFmtZero(false);
				sRet = d.getString("hh:mi am");
				break;
			}
			case '1:23':{
				d.setIsFmtZero(false);
				sRet = d.getString("hh:mi");
				break;
			}
			case '2005-05-05 01:23:45':{
				sRet = d.getString("yy-mm-dd hh:mi:ss");
				break;
			}
			case '2005-05-05 01:23:45 am':{
				sRet = d.getString("yy-mm-dd hh:mi:ss am");
				break;
			}		
			case '2005-05-05 1:23:45':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy-mm-dd hh:mi:ss");
				break;
			}
			case '2005-05-05 1:23:45 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy-mm-dd hh:mi:ss am");
				break;
			}	
			case '2005.05.05 01:23:45':{
				sRet = d.getString("yy.mm.dd hh:mi:ss");
				break;
			}
			case '2005.05.05 01:23:45 am':{
				sRet = d.getString("yy.mm.dd hh:mi:ss am");
				break;
			}
			case '2005.05.05 1:23:45':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy.mm.dd hh:mi:ss");
				break;
			}
			case '2005.05.05 1:23:45 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy.mm.dd hh:mi:ss am");
				break;
			}
			case '05-05-05 01:23:45':{
				sRet = d.getString("YY.mm.dd hh:mi:ss");
				break
			}
			case '05-05-05 01:23:45 am':{
				sRet = d.getString("YY.mm.dd hh:mi:ss am");
				break;
			}
			case '05.05.05 1:23:45':{
				d.setIsFmtZero(false);		
				sRet = d.getString("YY.mm.dd hh:mi:ss");
				break;
			}
			case '05.05.05 1:23:45 am':{
				d.setIsFmtZero(false);		
				sRet = d.getString("YY.mm.dd hh:mi:ss am");
				break;
			}
			case '2005年05月05日 01:23:45':{
				sRet = d.getString("yy年mm月dd日 hh:mi:ss");
				break;
			}
			case '2005年05月05日 01:23:45 am':{
				sRet = d.getString("yy年mm月dd日 hh:mi:ss am");
				break;
			}
			case '2005年05月05日 1:23:45':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy年mm月dd日 hh:mi:ss");
				break;
			}
			case '2005年05月05日 1:23:45 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy年mm月dd日 hh:mi:ss am");
				break;
			}
			case '2005-05-05 01:23':{
				sRet = d.getString("yy-mm-dd hh:mi");
				break;
			}
			case '2005-05-05 01:23 am':{
				sRet = d.getString("yy-mm-dd hh:mi am");
				break;
			}		
			case '2005-05-05 1:23':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy-mm-dd hh:mi");
				break;
			}
			case '2005-05-05 1:23 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy-mm-dd hh:mi am");
				break;
			}	
			case '2005.05.05 01:23':{
				sRet = d.getString("yy.mm.dd hh:mi");
				break;
			}
			case '2005.05.05 01:23 am':{
				sRet = d.getString("yy.mm.dd hh:mi am");
				break;
			}
			case '2005.05.05 1:23':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy.mm.dd hh:mi");
				break;
			}
			case '2005.05.05 1:23 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy.mm.dd hh:mi am");
				break;
			}
			case '05-05-05 01:23':{
				sRet = d.getString("YY-mm-dd hh:mi");
				break;
			}
			case '05-05-05 01:23 am':{
				sRet = d.getString("YY-mm-dd hh:mi am");
				break;
			}
			case '05.05.05 1:23':{
				d.setIsFmtZero(false);
				sRet = d.getString("YY.mm.dd hh:mi");
				break;
			}
			case '05.05.05 1:23 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("YY.mm.dd hh:mi am");
				break;
			}
			case '2005年05月05日 01:23':{
				sRet = d.getString("yy年mm月dd日 hh:mi");
				break;
			}
			case '2005年05月05日 01:23 am':{
				sRet = d.getString("yy年mm月dd日 hh:mi am");
				break;
			}
			case '2005年05月05 1:23':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy年mm月dd日 hh:mi");
				break;
			}
			case '2005年05月05日 1:23 am':{
				d.setIsFmtZero(false);
				sRet = d.getString("yy年mm月dd日 hh:mi am");
				break;
			}
		}
		return sRet;
	}

//

	function FormatNum(svalue,FormatNum,dotNum) {
		if(IsSpace(dotNum)){
			dotNum = 2 ;
		}
		switch(FormatNum){
			case '1234.5':{
				svalue = ContDec(svalue,1) ;
				break;
			}	
			case '1234.50':{
				svalue = ContDec(svalue,dotNum);
				break;	
			}
			case '1,234.5':{
				svalue = AddComma(svalue);
				break;
			}
			case '1,234.50':{
				svalue =  AddComma(ContDec(svalue,dotNum));
				break;
			}
			case '中文金额大写':{
				svalue = ChangeToBig(svalue);
				break;
			}
		}	
		return(svalue);
	}

//

	function ChangeToBig(value){
	var intFen,i;
	var strArr,strCheck,strFen,strDW,strNum,strBig,strNow;
	if(trim(value)==""){   
	return "零";
	}
	if (isNaN(value))   
	{
	strErr = "数据"+value+"非法！";
	alert(strErr);
	return "";
	}
	strCheck = value+".";
	strArr = strCheck.split(".");
	strCheck = strArr[0];
	var len = strCheck.length ;
	if(len > 12)   
	{
	strErr = "数据"+value+"过大，无法处理！";
	alert(strErr);
	return "";
	}
	try
	{
	i = 0;
	strBig = "";
	var s00="00";
	var svalue = value+"";
	var ipos = svalue.indexOf(".") ;
	var iiLen = svalue.length;
	if(ipos<0){  
	strFen = svalue+"00";
	}else if(ipos==iiLen-2){ 
	strFen = svalue.substring(0,iiLen-2)+svalue.substring(iiLen-1,iiLen)+"0";
	}else if(ipos==iiLen-3){ 
	strFen = svalue.substring(0,iiLen-3)+svalue.substring(iiLen-2,iiLen);
	}else{ 
	strFen = svalue.substring(0,ipos)+svalue.substring(ipos+1,ipos+3);
	}
	intFen = strFen.length;      
	strArr = strFen.split("");	
	while(intFen!=0)   
	{
	i = i+1;
	switch(i)              
	{
	case 1:strDW = "分";break;
	case 2:strDW = "角";break;
	case 3:strDW = "元";break;
	case 4:strDW = "拾";break;
	case 5:strDW = "佰";break;
	case 6:strDW = "仟";break;
	case 7:strDW = "万";break;
	case 8:strDW = "拾";break;
	case 9:strDW = "佰";break;
	case 10:strDW = "仟";break;
	case 11:strDW = "亿";break;
	case 12:strDW = "拾";break;
	case 13:strDW = "佰";break;
	case 14:strDW = "仟";break;
	}
	switch (strArr[intFen-1])              
	{
	case "1":strNum = "壹";break;
	case "2":strNum = "贰";break;
	case "3":strNum = "叁";break;
	case "4":strNum = "肆";break;
	case "5":strNum = "伍";break;
	case "6":strNum = "陆";break;
	case "7":strNum = "柒";break;
	case "8":strNum = "捌";break;
	case "9":strNum = "玖";break;
	case "0":strNum = "零";break;
	}
	strNow = strBig.split("");
	if((i==1)&&(strArr[intFen-1]=="0")){
	strBig = "整";
	}
	else if((i==2)&&(strArr[intFen-1]=="0"))
	{    
	if(strBig!="整")
	strBig = "零"+strBig;
	}
	else if((i==3)&&(strArr[intFen-1]=="0"))
	{
	strBig = "元"+strBig;
	}
	else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="元"))
	{
	strBig = "零"+strBig;
	}
	else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
	{} 
	else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="元"))
	{}
	else if((i==7)&&(strArr[intFen-1]=="0"))
	{
	strBig ="万"+strBig;
	}
	else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万"))
	{
	strBig = "零"+strBig;
	}
	else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万"))
	{}
	else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
	{}
	else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
	{
	strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);
	}
	else if(i==11)
	{
	if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
	{
	strBig ="亿"+"零"+strBig.substring(1,strBig.length);
	}
	else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="仟"))
	{
	strBig ="亿"+strBig.substring(1,strBig.length);
	}
	else if((strNow[0]=="万")&&(strNow[2]=="仟"))
	{
	strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);
	}
	else if((strNow[0]=="万")&&(strNow[2]!="仟"))
	{
	strBig = strNum+strDW+strBig.substring(1,strBig.length);	
	}
	else {
	strBig = strNum+strDW+strBig;
	}
	}
	else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿"))
	{
	strBig = "零"+strBig;
	}
	else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿"))
	{}
	else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
	{}
	else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="仟"))
	{
	strBig = strNum+strDW+strBig.substring(1,strBig.length);
	}
	else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="仟"))
	{
	strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);
	}else{
	strBig = strNum+strDW+strBig;
	}
	strFen = strFen.substring(0,intFen-1);
	intFen = strFen.length;
	strArr = strFen.split("");
	}
	if(strBig=="整") {strBig="零";}
	return strBig;
	}catch(err){
	return "";      
	}	
	}

//

	function selList(obj) {
		var l = obj.options.length ;
		var sHtml = "";
		var oCount = 0;
		for(var i=0;i<l;i++) {
			var sid = "ep_memo"+i ;
			var len = SKDBListBox2.options(i).length;
			alert(len);
			if(i == 0 ) {
				var iLeft = 5;
			}else{
				iLeft += len; 
			}
			sHtml += "<DIV id="+ sid +" style='BORDER-RIGHT: 1px solid; BORDER-TOP: 1px solid; FONT-SIZE: 12px; LEFT:";
			sHtml += iLeft +"; OVERFLOW: hidden; BORDER-LEFT: 1px solid; WIDTH: 75px; BORDER-BOTTOM: 1px solid; POSITION: absolute; TOP:";
			sHtml += "20px; HEIGHT: 25px' controltype='ep_memo'>"+sid+"</DIV>";
		}
		window.returnValue = sHtml;
		window.close();
	}

//

	function Pause(numberMillis) {
		var dialogScript = 
		'window.setTimeout(' +
		' function () { window.close(); }, ' + numberMillis + ');';
		var result = 
		window.showModalDialog(
		'javascript:document.writeln(' +
		'"<script>' + dialogScript + '<' + '/script>")'); 
	}


	function SetSession(strQueryString){
		if ( document.all("ifrSession") == null ){
			document.body.insertAdjacentHTML("BeforeEnd", "<IFRAME id=ifrSession name=ifrSession src='' width=0 height=0></IFRAME>");
		}
		document.all.ifrSession.src=location.protocol+"//"+location.host+ fcpubdata.servletPath + "/setSession"+fcpubdata.dotnetVersion + "?"+strQueryString;
	}


	function GetSession(strQueryString){
		if ( document.all("ifrSession") == null ){
			document.body.insertAdjacentHTML("BeforeEnd", "<IFRAME id=ifrSession name=ifrSession src='' width=0 height=0></IFRAME>");
		}
		document.all.ifrSession.src=location.protocol+"//"+location.host+ fcpubdata.servletPath + "/getSession"+fcpubdata.dotnetVersion + "?"+strQueryString;
		var i=0 ; 
		while (fcpubvar.pubSession == "null" && i<100){ 
			Pause(200);
			i++ ;
		}
		var arrRet=new Array();
		var arr=fcpubvar.pubSession.split("&");
		var ilen=arr.length;
		for(i=0;i<ilen;i++){
			var arr1=arr[i].split("=");
			arrRet[arr1[0]]=arr1[1];	
		}
		return arrRet;
	}

//

	function ShowHelp(htmlfile) {
		window.open(fcpubdata.Path+"/eformhelp/" + htmlfile + ".htm","_blank","top=0,left=0,height=400,width=300,status=no,toolbar=yes,menubar=no,location=no,resizable=yes,scrollbars=yes")	
	}

	function ShowFlash(htmlfile) {
		window.open(fcpubdata.Path+"/flash/" + htmlfile + ".htm","_blank","top=0,left=0,height=700,width=900,status=no,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes")	
	}

	function getuser() {
		return "fc";
	}

	function getusername() {
		return "fc";
	}

//

	function SendHttp(url, data, callback, context){
		//alert(url);
		//alert("--------------------------");
		return ajax_request(url, data, callback, context);
	}
	
//
	
	var requests = new Array();
	if(typeof(XMLHttpRequest) == 'undefined')
	var XMLHttpRequest = function()
	{
		var request = null;
		try
		{
			request = new ActiveXObject('Msxml2.XMLHTTP');
			request.setTimeouts(20000, 20000, 50000,100000);  
		}
		catch(e)
		{
			try
			{
				request = new ActiveXObject('Microsoft.XMLHTTP');
			}
			catch(ee)
			{}
		}
		return request;
	}

//

	function ajax_stop()
	{
		for(var i=0; i<requests.length; i++)
		{
			if(requests[i] != null){
				requests[i].obj.abort();
			}
		}
	}


	function ajax_create_request(context)
	{
		for(var i=0; i<requests.length; i++)
		{
			if(requests[i].readyState == 4)
			{
				requests[i].abort();
				requests[i].context = null;
				return requests[i];
			}
		}
		var pos = requests.length;
		requests[pos] = Object();
		requests[pos].obj = new XMLHttpRequest();
		requests[pos].context = context;
		return requests[pos];
	}


	function ajax_request(url, data, callback, context)
	{
		var request = ajax_create_request(context);
		var async = typeof(callback) == 'function';
		if(async) request.obj.onreadystatechange = function()
		{
			if(request.obj.readyState == 4)
			callback(new ajax_response(request));
		}
		request.obj.open('POST', url, async);
		request.obj.send("<root>"+data+"</root>");
		if(!async){
			var o = new ajax_response(request);
			return o.value ;
		}
	}


	function ajax_response(request)
	{
		this.request = request.obj;
		this.error = null;
		this.value = null;
		this.context = request.context;
		if(request.obj.status == 200)
		{
			try
			{
				this.value = object_from_json(request);
				if(this.value && this.value.error)
				{
					this.error = this.value.error;
					this.value = null;
				}
			}
			catch(e)
			{
				this.error = new ajax_error(e.name, e.description, e.number);
			}
		}
		else
		{
			this.error = new ajax_error('HTTP request failed with status: ' + request.obj.status, request.obj.status);
		}
		return this;
	}


	function enc(s)
	{
		return s.toString().replace(/\%/g, "%26").replace(/=/g, "%3D");
	}


	function object_from_json(request)
	{
		if(request.obj.responseXML != null && request.obj.responseXML.xml != null && request.obj.responseXML.xml != '')
			return request.obj.responseXML;
		return request.obj.responseText ;
	}


	function ajax_error(name, description, number)
	{
		this.name = name;
		this.description = description;
		this.number = number;
		return this;
	}
	
	
	
	ajax_error.prototype.toString = function()
	{
		return this.name + " " + this.description;
	}



	function json_from_object(o)
	{
		if(o == null)
		return 'null';
		switch(typeof(o))
		{
			case 'object':
				if(o.constructor == Array)		
				{
					var s = '';
					for(var i=0; i<o.length; ++i)
					{
						s += json_from_object(o[i]);
						if(i < o.length -1)
							s += ',';
					}
					return '[' + s + ']';
				}
				break;
			case 'string':
				return '"' + o.replace(/(["\\])/g, '\\$1') + '"';
			default:
				return String(o);
		}
	}
	
	var ajaxVersion = '5.6.3.4'
//-----------------------------------------------------------------------------------------------


	function getXmlNodeValueByName(nodeName){		
		var sRetExp = "";
		//从单据变量中获得值
		if (Printer.oBillXml!=null && Printer.oBillXml.documentElement!=null){
			try{	
				var valueList = Printer.oBillXml.getElementsByTagName(nodeName);
				if (valueList.length>0)
				{
					sRetExp = valueList.item(0).text ;
				}
			}catch(e){}					
		}
		return sRetExp;
	}