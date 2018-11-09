/*

File Name: CheckInput.js

Author: Wu Jin

Date: 2000-8-21

Reversion:

Copyright (c) 2000 by AsiaEC.com, Inc. All Rights Reserved.

*/



//输入参数frmName是输入框所在的Form,txtName是输入框的name,txtLab 是输入框的标签

function CheckNumber(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp= Math.abs(temp);

	if(temp.toString()=="NaN")

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	var re,p;

	re = /\./i;  

	temp=temp.toString();

	p=temp.search(re);

	if(p==-1)

		return true;

	else

	{

		temp=temp.substr(p+1);

		if(temp.length>4)

		{

			alert("在" + txtLab +"中输入数字只允许小数点后四位!");

			frmTemp.elements[txtName].focus();

			return false;

		}   

	}

	return true;

}



function CheckDate(frmName,txtName,txtLab)

{

	var frmTemp,temp,s;

	

	frmTemp=document.forms[frmName];

	temp=new String(frmTemp.elements[txtName].value);

	s=new String("");

	

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("在" + txtLab +"中输入的日期格式不对");

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	d=new Date(s);

	if(d.toString()=="NaN")

	{

		alert("在" + txtLab +"中输入的日期格式不对")

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true

}



function CompareDate(dtBegin,dtEnd,str)

{

	var temp,s;

	temp=new String(dtBegin.value);

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的开始日期");

				dtBegin.focus();

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtOne=new Date(s);

	if(dtOne.toString()=="NaN")

	{

		alert("请输入正确的开始日期");

		dtBegin.focus();

		return false;

	}

	

	temp=new String(dtEnd.value);

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的结束日期");

				dtBegin.focus();

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtTwo=new Date(s);

	if(dtTwo.toString()=="NaN")

	{

		alert("请输入正确的结束日期");

		dtEnd.focus();

		return false;

	}

	dtTwo=new Date(s);

	if(dtOne.valueOf()>dtTwo.valueOf())

	{

		alert(str);

		dtBegin.focus();

		return false;

	}

	else

		return true;

}



function CompareMonth(dtBegin,dtEnd,minus)

{

	var temp,s;

	temp=new String(dtBegin.value);

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的开始日期");

				dtBegin.focus();

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtOne=new Date(s);

	if(dtOne.toString()=="NaN")

	{

		alert("请输入正确的开始日期");

		dtBegin.focus();

		return false;

	}

	

	temp=new String(dtEnd.value);

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的结束日期");

				dtBegin.focus();

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtTwo=new Date(s);

	if(dtTwo.toString()=="NaN")

	{

		alert("请输入正确的结束日期");

		dtEnd.focus();

		return false;

	}

	dtTwo=new Date(s);

	if(((dtTwo.getYear()*12+dtTwo.getMonth())-(dtOne.getYear()*12+dtOne.getMonth()))>=minus)

		return true;

	else

	{

		alert("开始日期应比结束日期早 " + minus + "月");

		dtBegin.focus();

		return false;

	}

}

function CheckLength(frmName,txtName,txtLab,minLen,maxLen)

{

	var temp,lCount=0;

	frmTemp=document.forms[frmName];

	temp=new String(frmTemp.elements[txtName].value);

	for(var i =0;i<temp.length;i++)

	{

		if(temp.charCodeAt(i)>255)

			lCount +=2;

		else

			lCount +=1;

	}

	if(minLen>0 && lCount==0)

	{

		alert("请输入"+txtLab);

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(lCount<minLen)

	{

		alert(txtLab +"至少需要输入"+minLen+"个字符");

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(lCount>maxLen)

	{

		alert(txtLab +"过长，请删减");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

//检测email

function CheckEmail(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("请在" + txtLab +"中输入正确的e-Mail地址!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	var i = temp.indexOf("@"); 

	var j = temp.indexOf(".");

	if(parseInt(i)>1 )

		return true;

	else

	{

		alert("请在" + txtLab +"中输入正确的e-Mail地址!")

		frmTemp.elements[txtName].focus();

		return false;

	}

}



function CheckPhoneNum(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("请在" + txtLab +"中输入数据!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	

	var re = /[^1234567890()-]/i;

	if(!temp.search(re))

	{

		alert("请在" + txtLab +"中输入正确的号码!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

function CheckNull(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("请输入" + txtLab + "！");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

function CheckValue(frmName,txtName,txtLab,minValue,maxValue)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp=Number(temp);;

	if(isNaN(temp))

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	else

	{

		if(temp>=minValue && temp<=maxValue)

			return true;

		else

		{

			alert("请在" + txtLab +"中输入正确值：介于"+minValue+" 与 "+maxValue+" 之间!");

			frmTemp.elements[txtName].focus();

			return false;

		}

	}

}

function CheckValue(obj,txtLab,minValue,maxValue)

{

	var temp;

	temp=obj.value;

	if (temp=="")

	{

		alert("请在" + txtLab +"中输入数字!");

		obj.focus();

		return false;

	}

	temp=Number(temp);

	if(isNaN(temp))

	{

		alert("请在" + txtLab +"中输入数字!");

		obj.focus();

		return false;

	}

	else

	{

		if(temp>=minValue && temp<=maxValue)

			return true;

		else

		{

			alert("请在" + txtLab +"中输入正确值：介于"+minValue+" 与 "+maxValue+" 之间!");

			obj.focus();

			return false;

		}

	}

}

function CheckSelect(frmName,txtName,txtLab,intIllegue)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp==intIllegue)

	{

		alert("请在" + txtLab +"中选择");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

//isEmpty     : 校验输入的参数是否为NULL

//isPosInteger: 包含0的整数

//isNature    : 自然数：大于零的整数

//isInteger   : 包括0的整数

//isNumber    : 浮点数

//isSKU       : 正确的SKU编号（但库中不一定存在)

//isArabic    : 由数字组成的字串

//isPosNumber : 不为负的浮点数,包括0



function isEmpty(inputStr) {

	if (inputStr == null || inputStr == '') return true;

	return false;

}



function isInteger(inputVal) {

	inputStr = inputVal.toString();

	if (isEmpty(inputStr)) return false;

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 ) 	return false;

			else continue;

		if (oneChar < "0" || oneChar > "9")

			return false;

	}

	return true;

}



function isPosInteger(input) {

	inputVal = input.value;

	if (isEmpty(inputVal)) 

	{

		alert ("请输入税!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && oneChar == "+")

			if (inputVal.length == 1 )

			{

				alert ("请输入正的整数!");

				input.focus();

				return false;

			}

			else continue;

		if (oneChar < "0" || oneChar > "9")

		{

			alert ("请输整数!");

			input.focus();

			return false;

		}

	}

	return true;

}



function isNature(inputVal) {

	if (isInteger(inputVal)) {

		if (parseInt(inputVal.toString()) < 1 ) return false;

	}

	else	return false;

	return true;

}



function isNumberOrNull(inputVal) {

	oneDecimal = false;

	inputStr = inputVal.toString();

	if (isEmpty(inputStr)) return true;

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 ) 	return false;

			else continue;

		if (oneChar == "." && !oneDecimal) {

			oneDecimal = true;

			continue;

		}

		if (oneChar < "0" || oneChar > "9")

			return false;

	}

	return true;

}



function isNumberOrNull(frmName,txtName,txtLab) 

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if (isEmpty(temp)) return true;

	return CheckNumber(frmName,txtName,txtLab);

}



function isNumber(input) {

	oneDecimal = false;

	inputVal = input.value;

	if (isEmpty(inputVal)) 

	{

		alert("请输入正整数!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 )

			{

				alert("请输入正整数!");

				input.focus();

			 	return false;

			}

			else continue;

		if (oneChar == "." && !oneDecimal) {

			oneDecimal = true;

			continue;

		}

		if (oneChar < "0" || oneChar > "9")

		{

			alert("请输入正整数!");

			input.focus();

		 	return false;

		}

	}

	return true;

}

function isInteger(frmName,txtName,txtLab) 

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp= Math.abs(temp);

	if(temp.toString()=="NaN")

	{

		alert("请在" + txtLab +"中输入数字!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(parseInt(temp)<0)

	{

		alert("在" + txtLab +"中输入正整数");

		frmTemp.elements[txtName].focus();

		return false; 

	}

	var re,p;

	re = /\./i;  

	temp=temp.toString();

	p=temp.search(re);

	if(p==-1)

		return true;

	else

	{

		alert("在" + txtLab +"中输入正整数");

		frmTemp.elements[txtName].focus();

		return false; 

	}

	return true;

}

function isPosNumber(input) {

	oneDecimal = false;

	inputVal = input.value;

	if (isEmpty(inputVal)) 

	{

		alert("请输入正整数!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 )

			{

				alert("请输入正整数!");

				input.focus();

			 	return false;

			}

			else continue;

		if (oneChar == "." && !oneDecimal) {

			oneDecimal = true;

			continue;

		}

		if (oneChar < "0" || oneChar > "9")

		{

			alert("请输入正整数!");

			input.focus();

		 	return false;

		}

	}

	if (parseFloat(inputVal) < 0)

	{

		alert("请输入正数!");

		input.focus();

	 	return false;

	}

	return true;

}



function isArabic(inputVal) {

	var checkOK = "0123456789";

	var checkStr = inputVal.toString();

	if (isEmpty(checkStr)) return false;

	for (i = 0;  i < checkStr.length;  i++){

		ch = checkStr.charAt(i);

		if (checkOK.indexOf(ch) == -1)

			return (false);

	}

	return true;

}



function CheckSKU(input) {

	var checkOK = "0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ";

	var checkStr = input.value;

	if (isEmpty(checkStr)) 

	{

		alert("请输入SKU!");

		input.focus();

		return false;

	}

	if (checkStr.length != 14)

	{

		alert("请输入14位SKU!");

		input.focus();

		return false;

	}

	for (i = 0;  i < checkStr.length;  i++){

		ch = checkStr.charAt(i);

		if (checkOK.indexOf(ch) == -1)

		{

			alert("SKU中包含非法字符");

			input.focus();

			return (false);

		}

	}

	return true;

}

	function selectShelf(lStockID,obj,objView)

	{

		if(lStockID==null || lStockID=="" || parseInt(lStockID)==-1 )

		{

			alert("请先选择仓库!");

			return false;

		}

		var strTemp="/inventory/ShelfControlA.jsp?lStockID=" + lStockID;

		var vReturnValue = window.showModalDialog(strTemp, "shelf","dialogWidth:650px;dialogHeight:400px;center:true;help:no;resizable:no;status:no");

		if (vReturnValue == -1 || vReturnValue== null)

		{	

			obj.value ="";

			objView.value ="";

		}

		else

		{	

			var i=vReturnValue.indexOf(";")

			obj.value = vReturnValue.substr(0,i);

			objView.value = vReturnValue.substr(i+1);

		}

	}

	function selectShelfB(lStockID,obj,objView,objQty,lProjectID,lProductID)

	{

		if(lStockID==null || lStockID=="" || parseInt(lStockID)==-1 )

		{

			alert("请先选择仓库!");

			return false;

		}

		

		var strTemp="/inventory/ShelfControlA.jsp?lStockID=" + lStockID+"&lProjectID=" + lProjectID + "&lProductID=" + lProductID;

		var vReturnValue = window.showModalDialog(strTemp, "shelf","dialogWidth:650px;dialogHeight:400px;center:true;help:no;resizable:no;status:no");

		if (vReturnValue == -1 || vReturnValue== null)

		{	

			obj.value ="";

			objView.value ="";

			objQty.value="0";

			document.all.item(obj.name + lProductID).innerHTML = "0";

		}

		else

		{	

			var i=vReturnValue.indexOf(";")

			obj.value = vReturnValue.substr(0,i);

			var j=vReturnValue.indexOf("#")

			objView.value = vReturnValue.substr(i+1,j-i-1);

			objQty.value=vReturnValue.substr(j+1);

			document.all.item(obj.name + lProductID).innerHTML = vReturnValue.substr(j+1);



		}

	}

	

	function selectBatchNo(lStockID,lProductID,objBatchNo,objShelfID,bSingle)

	{

		if(parseInt(lStockID) == -1)

		{

			alert("请先选择仓库!");

			return false;

		}

		if(parseInt(lProductID) == -1)

		{

			alert("请先选择物资!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("没有批次号文本框的名称");

			return false;

		}

		var strTemp="/inventory/BatchNoControlA.jsp?lStockID=" + lStockID +"&lProductID="+lProductID + "&bSingle="+bSingle;

		var vReturnValue = window.showModalDialog(strTemp, "","dialogWidth:650px;dialogHeight:400px;center:true;help:no;resizable:no;status:no");

		if (vReturnValue == -1 || vReturnValue== null)

		{

			return true;

		}

		else

		{

			var iPos = 0;

			var iNum = 0;

			var strHtml = "";

			while (iPos < vReturnValue.length)

			{

				iNum = vReturnValue.indexOf(";",iPos);

				strHtml = strHtml + "<input type=\"Text\" readonly class=\"f9\" name=\""+objBatchNo+"\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

				iPos = iNum + 1;

				if(objShelfID !=null && objShelfID!="")

				{

					iNum = vReturnValue.indexOf("|",iPos);

					strHtml = strHtml + "<input type=\"hidden\" class=\"f9\"  name=\""+objShelfID+"\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

					iPos = iNum + 1;

					iNum = vReturnValue.indexOf("#",iPos);

					strHtml = strHtml + "<input type=\"Text\" readonly class=\"f9\"  name=\""+objShelfID+"View\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

					iPos = iNum + 1;

				}

				else

				{

					iNum = vReturnValue.indexOf("|",iPos);

					iPos = iNum + 1;

					iNum = vReturnValue.indexOf("#",iPos);

					iPos = iNum + 1;

				}

				strHtml = strHtml + "<br>"

			}

			document.all.item("P"+lProductID).innerHTML = strHtml;

		}

	}



	

	function selectBatchNoB(lStockID,lProductID,objBatchNo,bSingle)

	{

		if(parseInt(lStockID) == -1)

		{

			alert("请先选择仓库!");

			return false;

		}

		if(lProductID==null || lProductID=="")

		{

			alert("请先选择物资!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("没有批次号文本框的名称");

			return false;

		}

		var strTemp="/inventory/BatchNoControlA.jsp?lStockID=" + lStockID +"&lProductID="+lProductID + "&bSingle="+bSingle;

		var vReturnValue = window.showModalDialog(strTemp, "","dialogWidth:650px;dialogHeight:400px;center:true;help:no;resizable:no;status:no");

		if (vReturnValue == -1 || vReturnValue== null)

		{	

			return true;

		}

		else

		{	

			var iNum = vReturnValue.indexOf(";",0);

			objBatchNo.value=vReturnValue.substr(0,iNum);

		}

	}



	function selectBatchNoC(lStockID,lProjectID,lProductID,objBatchNo,objShelfID,objQty,bSingle)

	{

		if(parseInt(lStockID) == -1)

		{

			alert("请先选择仓库!");

			return false;

		}

		if(parseInt(lProjectID) == -1)

		{

			alert("请先选择项目!");

			return false;

		}

		if(parseInt(lProductID) == -1)

		{

			alert("请先选择物资!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("没有批次号文本框的名称");

			return false;

		}

		if(objQty == "")

		{

			alert("没有库存量显示的名称");

			return false;

		}

		var strTemp="/inventory/BatchNoControlC.jsp?lStockID=" + lStockID +"&lProjectID="+lProjectID +"&lProductID="+lProductID;

		var vReturnValue = window.showModalDialog(strTemp, "","dialogWidth:650px;dialogHeight:400px;center:true;help:no;resizable:no;status:no");

		if (vReturnValue == -1 || vReturnValue== null)

		{	

			return true;

		}

		else

		{	

			var iPos = 0;

			var iNum = 0;

			var strHtml = "";

			while (iPos < vReturnValue.length)

			{

				iNum = vReturnValue.indexOf(";",iPos);

				document.all.item(objBatchNo).value = vReturnValue.substr(iPos,iNum-iPos);

				//strHtml = strHtml + "<input type=\"Text\" readonly class=\"f9\" name=\""+objBatchNo+"\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

				iPos = iNum + 1;

				if(objShelfID !=null && objShelfID!="")

				{

					iNum = vReturnValue.indexOf("|",iPos);

					document.all.item(objShelfID).value = vReturnValue.substr(iPos,iNum-iPos);

					//objShelfID.value = vReturnValue.substr(iPos,iNum-iPos);

					//strHtml = strHtml + "<input type=\"hidden\" class=\"f9\"  name=\""+objShelfID+"\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

					iPos = iNum + 1;

					iNum = vReturnValue.indexOf("#",iPos);

					//strHtml = strHtml + "<br><input type=\"Text\" readonly class=\"f9\"  name=\""+objShelfID+"View\" size=\"12\" value=\""+vReturnValue.substr(iPos,iNum-iPos)+"\">";

					iPos = iNum + 1;

				}

				else

				{

					iNum = vReturnValue.indexOf("|",iPos);

					iPos = iNum + 1;

					iNum = vReturnValue.indexOf("#",iPos);

					iPos = iNum + 1;

				}

				iNum = vReturnValue.indexOf("*",iPos);

				//objQty.value = vReturnValue.substr(iPos,iNum-iPos);

				document.all.item(objQty).innerHTML = vReturnValue.substr(iPos,iNum-iPos);

				document.all.item(objShelfID+"Qty").value = vReturnValue.substr(iPos,iNum-iPos);

				iPos = iNum + 1;



				iNum = vReturnValue.indexOf("%",iPos);

				document.all.item("dRMBPrice"+lProductID).value = vReturnValue.substr(iPos,iNum-iPos);

				iPos = iNum + 1;

				iNum = vReturnValue.indexOf("$",iPos);

				document.all.item("dUSDPrice"+lProductID).value = vReturnValue.substr(iPos,iNum-iPos);

				iPos = iNum + 1;

			}

			document.all.item("P"+lProductID).innerHTML = strHtml;

		}

	}

