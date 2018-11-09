/**
 * @Author Lion Shooray 2007-7-24 iTruschina. Co.,Ltd
 * @Version 1.0
 * 修改:付明正 2007-09-05
 */

//global const 
var INPUT_BASE64 = 0x1;
var INPUT_HEX = 0x2;
var OUTPUT_BASE64 = 0x4;
var OUTPUT_HEX = 0x8;

String.prototype.trim = function() {
  return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
*与java 中的formatData方法配套
**/
function formatData(fNames,fValues){
	if (fNames.length == 0)
		return "";
				
	var returnValue = "";
	
	var returnValue = "<ORIGINALDATA>";
	returnValue += "<ITEMS>";

	for (var i = 0; i < fNames.length; i++) {
		var strTempName = fNames[i].trim().toUpperCase();
		var strTempValue = fValues[i].trim();

		returnValue += "<" + strTempName + ">";
		returnValue += strTempValue;
		returnValue += "</" + strTempName + ">";
	}
	returnValue += "</ITEMS>";
	returnValue += "</ORIGINALDATA>";

	return returnValue;	
}
/**
*与java 中的formatNetSign方法配套,用于将数组变成String
**/
function formatNetSign(fNames,fValues){
	if (fNames.length == 0)
		return "";
	var returnValue = fNames[0].trim()+"="+fValues[0].trim();
	for (var i = 1; i < fNames.length; i++) {
		var strTempName = fNames[i].trim();
		var strTempValue = fValues[i].trim();
		returnValue = returnValue+","+strTempName+"="+strTempValue;
	}
	return returnValue;
}

/**
 * JSDateAdd Javascript 计算给定日期+天数
 * @param theDate: 给定日期，Date类型
 * @param days: 整型
 * @return 计算结果，Date类型
 */
function JSDateAdd(theDate, days) {
	var dateValue = theDate.valueOf()
	dateValue += days * 1000 * 60 * 60 * 24;
	var newDate = new Date(dateValue);
	return newDate;
}
/**
 * JSDateDiffByDays Javascript 计算两个日期之间的间隔天数

 * @param date1: 给定日期1，Date类型
 * @param date2: 给定日期2，Date类型
 * @return 天数，整型

 */
function JSDateDiffByDays(date1, date2) {
	var mill = date1.valueOf() - date2.valueOf();
	var millStr = new String(mill / 1000 / 60 /60 / 24)
	return parseInt(millStr);
}

/**
 * filterCerts 根据所设置条件过滤证书
 * @param arrayIssuerDN(optional) Array() or string，缺省为""，证书的颁发者字符串和字符串数组，支持多个CA时使用字符串数组
 * @param dateFlag(optional) 缺省为0，0表示所有证书，1表示处于有效期内的证书，2表示待更新证书，3表示未生效或已过期证书
 * @param serialNumber(optional) 缺省为""，证书序列号（微软格式）
 * @return Array(), PTALib.Certificate
 */
function filterCerts(arrayIssuerDN, serialNumber,commonName,dateFlag) {
	var m_certs = new Array();
	var i = 0;
	if(arrayIssuerDN == null || typeof(arrayIssuerDN) == "undefined") {
		arrayIssuerDN = new Array("");
	} else if(typeof(arrayIssuerDN) == "string") {
		arrayIssuerDN = new Array(arrayIssuerDN);
	}
	if(typeof(serialNumber) == "undefined")
		serialNumber = "";

	for(i = 0; i < arrayIssuerDN.length; i++){
		var CertFilter = iTrusPTA.Filter;
		
		CertFilter.Clear();
		CertFilter.Issuer = arrayIssuerDN[i];
		CertFilter.SerialNumber = serialNumber;
		var t_Certs = iTrusPTA.MyCertificates; //临时变量

		var now = new Date();
		if(parseInt(t_Certs.Count) > 0) { //找到了证书
			for(var j = 1; j <= parseInt(t_Certs.Count) ; j++) {
				
				//如果CN不为空,那么作为条件进行过滤
				if(commonName != null && commonName != "" && t_Certs(j).CommonName != commonName.trim()){
					continue;
				}					
				var validFrom = new Date(t_Certs(j).ValidFrom);
				var validTo = new Date(t_Certs(j).validTo);
				
				switch (dateFlag){
   					case 0://所有证书
						m_certs.push(t_Certs(j));
						break;
					case 1://处于有效期内的证书
						//validFrom     validTo
						//          now
						if(validFrom < now && now < validTo)
							m_certs.push(t_Certs(j));
						break;
					case 2://待更新证书
						//validFrom     validTo-30     validTo
						//                         now
						if(JSDateAdd(validTo, -30) < now && now < validTo)
							m_certs.push(t_Certs(j));
						break;
					case 3://未生效或已过期证书
						//     validFrom     validTo
						// now                       now
						if(now < validFrom || validTo < now)
							m_certs.push(t_Certs(j));
						break;
					default://缺省当作所有证书处理
						m_certs.push(t_Certs(j));
						break;
				}
			}
		}
	}	
	return m_certs;
}

/**
 * ListAllCerts 列出所有证书

 * @param issuerDN: 用户证书的颁发者主题（微软格式）

 * @param serialNumber: 证书序列号（微软格式）

 * @param certList 证书列表<select>对象
 * @return Certificates()集合
 */
function ListAllCerts(arrayIssuerDN, serialNumber, commonName, certList) {
	var arrayCerts = filterCerts(arrayIssuerDN, serialNumber, 0);
	createCertList(certList,arrayCerts);
}

/**
 * ListWillOverdueCerts 列出需要更新的证书
 * @param issuerDN: 用户证书的颁发者主题（微软格式）

 * @param serialNumber: 证书序列号（微软格式）

 * @param certList 证书列表<select>对象
 * @return Certificates()集合
 */
function ListWillOverdueCerts(arrayIssuerDN, serialNumber, commonName, certList) {
	var arrayCerts = filterCerts(arrayIssuerDN, serialNumber, 2);
	createCertList(certList,arrayCerts);
}

/**
 * ListValidCerts 列出未过期的证书
 * @param issuerDN: 用户证书的颁发者主题（微软格式）

 * @param serialNumber: 证书序列号（微软格式）

 * @param certList 证书列表<select>对象
 * @return Certificates()集合
 */
function ListValidCerts(arrayIssuerDN, serialNumber, commonName, certList) {
	var arrayCerts = filterCerts(arrayIssuerDN, serialNumber, commonName, 1);
	createCertList(certList,arrayCerts);
}

/**
* certList   select对象
* arrayCerts 证书列表
*/
function createCertList(certList,arrayCerts){
	for(var i = certList.length - 1 ; i >= 0 ; i--)
		certList.options[i] = null;
			
	//把满足要求的证书加入CertList
	if(arrayCerts.length == 0) {
		var el = document.createElement("option");
		el.text = "没有找到数字证书";
		el.value = -1;
		certList.add(el);
	} else {
		for(var i = 0; i < arrayCerts.length ; i++) {
			var el = document.createElement("option");
			el.text = arrayCerts[i].CommonName;
			el.value = i;
			certList.add(el);
		}
	}
}

/**
 * DoSign 登陆签名
 * @param certList 证书列表<select>对象
 * @param originalData: 原始数据
 * @return 成功返回签名值，失败返回""
 */
function DoLogonSign (form,nameArray,valueArray) {	
	
	var CurCert = SelectSingleCertificate(form.certList);

	if(CurCert == null)
		return "";

	try {		
		var signature = CurCert.SignMessage(formatData(nameArray,valueArray), OUTPUT_BASE64);			
		form.safety_signature_session_certificateInfo_issuerDN.value=CurCert.Issuer;
		
		return signature;
					
	} catch (e) {
		if(e.number == -2147483135) {
			return "";
		}else	if(e.number == -2147469641) {
			alert("您取消了PTA签名，不签名将不能使用该系统。");
			return "";
		}else	if(e.number == -2147483648) {
			alert("您取消了PTA签名，不签名将不能使用该系统。");
			return "";
		}else if(e.number == -2146434962) {
			alert("您取消了PTA签名，不签名将不能使用该系统。");
			return "";
		}else if(e.number == -2146885621) {
			alert("您不拥有证书“" + CurCert.CommonName + "”的私钥，签名失败。");
			return "";
		}else{
			alert("PTA签名时发生错误\n错误号: " + e.number + "\n错误描述: " + e.description);
			return "";
		}
	}
}

/**
 * DoSign 签名
 * @param certList 证书列表<select>对象
 * @param originalData: 原始数据
 * @return 成功返回签名值，失败返回""
 */
function DoSign(form, nameArray,valueArray) {
	var CurCert = GetSingleCertificate(form.safety_signature_session_certificateInfo_issuerDN.value,form.safety_signature_session_certificateInfo_serialNumber.value);
	if(CurCert == null)
		return "";
		
	try {			
		var signature = CurCert.SignMessage(formatData(nameArray,valueArray), OUTPUT_BASE64);
		form.safety_signature_signatureValue.value = signature;
		
		return signature;
	} catch (e) {
		if(-2147483135 == e.number) {
			//用户取消签名
			alert("您取消了PTA签名，不签名将不能使用该系统");
			return "";
		}else if(e.number == -2146885621) {
			alert("您不拥有证书“" + CurCert.CommonName + "”的私钥，签名失败。");
			return "";
		}else{
			alert("PTA签名时发生错误\n错误号： " + e.number + "\n错误描述： " + e.description);
			return "";
		}
	}
//} else {
//	alert("没有找到可供操作的证书！");
	//return "";
//}
}

/**
 * 信安签名
 * @param form 提交的form
 * @param originalData: 原始数据
 * @return 成功返回签名值，失败返回""
 */
function DoSignNetSign(form, nameArray,valueArray) {
		try{
			iTrusPTA.NSSetPlainText(formatNetSign(nameArray,valueArray));
		}catch(ex){
			alert("签名异常，异常原因："+e.description);
		}	
		var signature;
		if(iTrusPTA.errorNum!=0)
		{
			alert("签名异常");
			return ;
		}
		
		//iTrusPTA.NSSetDigestArithmetic("1.2.840.113549.2.2");
		if(form.safety_signature_session_certificateInfo_issuerDN.value)
		{	
			try{
				signature = iTrusPTA.NSAttachedSign(defaultSignerDN);
			}catch(e){
				alert("签名异常，异常原因："+e.description);
			}
		}
		else
		{	
			try{
				signature = iTrusPTA.NSAttachedSign("");
			}catch(e){
				alert("签名异常，异常原因："+e.description);
			}
		}
		form.safety_signature_signatureValue.value = signature;
		if(iTrusPTA.errorNum!=0)
		{
			alert("返回值〔"+iTrusPTA.errorNum+"〕\n"+iTrusPTA.errMsg+"\n底层返回值("+iTrusPTA.errorCode+")");
			return "";
		}
	return signature;
}
/**
 * DoRenewCert 证书更新，与ListWillOverdueCerts函数匹配使用
 * @param certList 证书列表<select>对象
 * @param csrText: CSR证书注册请求（Base64格式字符串） 
 * @param pkcsInformation: 用待更新证书对CSR进行签名，<input>对象
 * @param origCertSerialNumber: 待更新证书序列号，<input>对象
 * @param origCert: 待更新Base64证书，<input>对象
 * @return 成功: true，失败: false
 */
function DoRenewCert(certList, csrText, pkcsInformation, origCertSerialNumber, origCert) {
	var Certs;
	try {
		if(certList.value == -1){
			alert("没有可以更新的证书！");
			return false;
		} else {
			Certs = iTrusPTA.MyCertificates;
			CurCert = Certs(certList.value);
			//对3个<input>对象pkcsInformation、origCertSerialNumber、origCert进行赋值

			pkcsInformation.value = CurCert.SignLogonData("LOGONDATA:" + csrText, OUTPUT_BASE64);
			origCertSerialNumber.value = CurCert.SerialNumber;
			origCert.value = CurCert.GetEncodedCert(2);
			return true;
		}
	} catch (e) {
		if(-2147483135 == e.number) {
			//用户取消签名
			alert("很抱歉，由于您拒绝使用证书对数据进行签名，我们无法确认您正在更新证书的有效性，操作已被取消！");
			return false;
		} else if(e.number == -2146885621) {
			alert("您不拥有证书“" + CurCert.CommonName + "”的私钥，签名失败。");
			return false;
		} else {
			alert("在更新证书过程中发生错误！错误原因：" + e.description + "，错误代码：" + e.number);
			return false;
		}
	}
}

/**
 * SelectSingleCertificate 从<select>中选择一张证书

 * @param certList 证书列表<select>对象
 * @return 成功: 返回PTA Certificate 对象，失败: null
 */
function SelectSingleCertificate(certList) {
	if(certList.value == -1){
		alert("没有找到任何有效的证书！");
		return null;
	} else {
		var Certs = iTrusPTA.MyCertificates;
		var certName = certList.options(certList.selectedIndex).innerText;

		var arrayCerts = filterCerts("", "",certName, 1);
		var CurCert = arrayCerts[0];
		
		return CurCert;
	}
}

/**
 * GetSingleCertificate 选择一张证书

 * @param issuerDN: 用户证书的颁发者主题（微软格式）

 * @param serialNumber: 证书序列号（微软格式）

 * @param certList 证书列表<select>对象
 * @return PTA Certificate 对象
 */
function GetSingleCertificate(issuerDN, serialNumber){
	var CertFilter = iTrusPTA.Filter;
	CertFilter.Clear();
	CertFilter.Issuer = issuerDN;
	CertFilter.SerialNumber = serialNumber;
	var Certs = iTrusPTA.MyCertificates;

	if(parseInt(Certs.Count) > 0){
		try{
			return Certs(0);
		}catch(e){
			alert("获得证书时发生错误，"+ "\n请检查您的key是否正确插入");
			return null;
		}
	}
	alert("没有找到有效的证书！");
	return null;
}

function DoCFCASign(CertCN,nameArray,valueArray)
{
	try
	{
		iTrusPTA.SelectSignCertificate(CertCN,'');
		var originalData = formatData(nameArray,valueArray);
	 	var signatureData = iTrusPTA.SignMessageDetached(originalData,'SHA-1');
	 	return signatureData;
 	}catch(e)
 	{
 		alert(e.number+":"+e.name);
 		return null;
 	}

	
}

function DoSDeanSign(cert,nameArray,valueArray)
{
	try
	{
		var signatureData = "";
		var originalData = formatData(nameArray,valueArray);
		if(cert.YwSign(originalData) == 0){
				signatureData = cert.signRet
				signatureData = signatureData.substring(0,signatureData.indexOf(","));	
		}
		return signatureData;
	}
	catch(e)
	{
		alert("客户端加签失败!  "+e.number+":"+e.name);
	}	
}
function clearSingleCertSession(){
	iTrusPTA = null;
}
