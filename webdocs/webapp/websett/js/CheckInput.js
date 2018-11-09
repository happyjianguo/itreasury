/**
* File Name: CheckInput.js
* Author: Wu Jin
* Date: 2000-8-21
* Reversion:
* Copyright (c) 2000 by AsiaEC.com, Inc. All Rights Reserved.
* Ŀ¼�������ڣ�2005-7-12 by ̷־��
* 1. CheckNumber У�����ڸ�ʽ
* 2. CheckDate �Ƚ���������������λ���ڴ�С
* 3. CompareDate У�鿪ʼ���ڱ����ڽ�������֮ǰ
* 4. CompareMonth У�鿪ʼ���ڱ���Ƚ������������ɸ���
* 5. CheckLength У�������ַ����ĳ���
* 6. CheckEmail У�� email ��ʽ
* 7. CheckPhoneNum У��绰�����ʽ
* 8. CheckNull �ǿ�У��
* 9. CheckValue(�����������б�һ��) ����ֵ��ȡֵ����У��
* 10.CheckSelect ����������ѡ��
* 11.isEmpty У������Ĳ����Ƿ�ΪNULL
* 12.isPosInteger ����0������
* 13.isNature ��Ȼ���������������
* 14.isNumberOrNull У������Ϊ��ֵ��Ϊ��
* 15.isInteger ����0������
* 16.isNumber ������
* 17.isSKU ��ȷ��SKU��ţ������в�һ������)
* 18.isArabic ��������ɵ��ִ�
* 19.isPosNumber ��Ϊ���ĸ�����,����0
* 
*/


/**
* У���������Ϊ��ֵ
* frmName ��������ڵ�Form
* txtName ������ name
* txtLab �����ı�ǩ
*/
function CheckNumber(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("����" + txtLab +"����������!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp= Math.abs(temp);

	if(temp.toString()=="NaN")

	{

		alert("����" + txtLab +"����������!");

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

			alert("��" + txtLab +"����������ֻ����С�������λ!");

			frmTemp.elements[txtName].focus();

			return false;

		}   

	}

	return true;

}

/**
* У�����ڸ�ʽ
* frmName ��������ڵ�Form
* txtName ������ name
* txtLab �����ı�ǩ
*/
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

				alert("��" + txtLab +"����������ڸ�ʽ����");

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	d=new Date(s);

	if(d.toString()=="NaN")

	{

		alert("��" + txtLab +"����������ڸ�ʽ����")

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true

}

/**
* У�鿪ʼ���ڱ����ڽ�������֮ǰ�������ʼ�������ڽ������ڣ����� false�����򷵻� true
* dtBegin ��ʼ�������������
* dtEnd �����������������
* str ��ʾ��Ϣ
*/
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

				alert("��������ȷ�Ŀ�ʼ����");

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

		alert("��������ȷ�Ŀ�ʼ����");

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

				alert("��������ȷ�Ľ�������");

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

		alert("��������ȷ�Ľ�������");

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

/**
* У�鿪ʼ���ڱ���Ƚ������������ɸ���
* dtBegin ��ʼ�������������
* dtEnd �����������������
* minus ָ��Ҫ�������
*/
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

				alert("��������ȷ�Ŀ�ʼ����");

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

		alert("��������ȷ�Ŀ�ʼ����");

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

				alert("��������ȷ�Ľ�������");

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

		alert("��������ȷ�Ľ�������");

		dtEnd.focus();

		return false;

	}

	dtTwo=new Date(s);

	if(((dtTwo.getYear()*12+dtTwo.getMonth())-(dtOne.getYear()*12+dtOne.getMonth()))>=minus)

		return true;

	else

	{

		alert("��ʼ����Ӧ�Ƚ��������� " + minus + "��");

		dtBegin.focus();

		return false;

	}

}

/**
* У�������ַ����ĳ���
* frmName ������
* txtName ���������
* txtLab �����ı�ǩ��
* minLen ��С����
* maxLen ��󳤶�
*/
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

		alert("������"+txtLab);

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(lCount<minLen)

	{

		alert(txtLab +"������Ҫ����"+minLen+"���ַ�");

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(lCount>maxLen)

	{

		alert(txtLab +"��������ɾ��");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

/**
* У�� email ��ʽ
* frmName ������
* txtName ���������
* txtLab ������ǩ��
*/
function CheckEmail(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("����" + txtLab +"��������ȷ��e-Mail��ַ!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	var i = temp.indexOf("@"); 

	var j = temp.indexOf(".");

	if(parseInt(i)>1 )

		return true;

	else

	{

		alert("����" + txtLab +"��������ȷ��e-Mail��ַ!")

		frmTemp.elements[txtName].focus();

		return false;

	}

}

/**
* У��绰�����ʽ
* frmName ������
* txtName ���������
* txtLab ������ǩ��
*/
function CheckPhoneNum(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("����" + txtLab +"����������!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	

	var re = /[^1234567890()-]/i;

	if(!temp.search(re))

	{

		alert("����" + txtLab +"��������ȷ�ĺ���!")

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

/**
* �ǿ�У��
* frmName ������
* txtName ���������
* txtLab ������ǩ��
*/
function CheckNull(frmName,txtName,txtLab)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	

	if(temp=="")

	{

		alert("������" + txtLab + "��");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

/**
* ����ֵ��ȡֵ����У�飬�� minValue �� maxValue ֮�䷵�� true�����򷵻� false
* frmName ������
* txtName ���������
* txtLab ������ǩ��
* minValue ��Сֵ
* maxValue ���ֵ
*/
function CheckValue(frmName,txtName,txtLab,minValue,maxValue)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("����" + txtLab +"����������!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp=Number(temp);;

	if(isNaN(temp))

	{

		alert("����" + txtLab +"����������!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	else

	{

		if(temp>=minValue && temp<=maxValue)

			return true;

		else

		{

			alert("����" + txtLab +"��������ȷֵ������"+minValue+" �� "+maxValue+" ֮��!");

			frmTemp.elements[txtName].focus();

			return false;

		}

	}

}

/**
* ����ֵ��ȡֵ����У�飬�� minValue �� maxValue ֮�䷵�� true�����򷵻� false
* obj �����
* txtLab ������ǩ��
* minValue ��Сֵ
* maxValue ���ֵ
*/
function CheckValue(obj,txtLab,minValue,maxValue)

{

	var temp;

	temp=obj.value;

	if (temp=="")

	{

		alert("����" + txtLab +"����������!");

		obj.focus();

		return false;

	}

	temp=Number(temp);

	if(isNaN(temp))

	{

		alert("����" + txtLab +"����������!");

		obj.focus();

		return false;

	}

	else

	{

		if(temp>=minValue && temp<=maxValue)

			return true;

		else

		{

			alert("����" + txtLab +"��������ȷֵ������"+minValue+" �� "+maxValue+" ֮��!");

			obj.focus();

			return false;

		}

	}

}

/**
* ����������ѡ��
* frmName ������
* txtName ���������
* txtLab ������ǩ����
* intIllegue ���Ϸ�ֵ
*/
function CheckSelect(frmName,txtName,txtLab,intIllegue)

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp==intIllegue)

	{

		alert("����" + txtLab +"��ѡ��");

		frmTemp.elements[txtName].focus();

		return false;

	}

	return true;

}

//isEmpty     : У������Ĳ����Ƿ�ΪNULL

//isPosInteger: ����0������

//isNature    : ��Ȼ���������������

//isInteger   : ����0������

//isNumber    : ������

//isSKU       : ��ȷ��SKU��ţ������в�һ������)

//isArabic    : ��������ɵ��ִ�

//isPosNumber : ��Ϊ���ĸ�����,����0



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

		alert ("������˰!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && oneChar == "+")

			if (inputVal.length == 1 )

			{

				alert ("��������������!");

				input.focus();

				return false;

			}

			else continue;

		if (oneChar < "0" || oneChar > "9")

		{

			alert ("��������!");

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

//isNumberOrNull �������Ϊ��ֵ����Ϊ��
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

/**
* isNumberOrNull �������Ϊ��ֵ����Ϊ��
* frmName ������
* txtName ���������
* txtLab ������ǩ��
*/
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

		alert("������������!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 )

			{

				alert("������������!");

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

			alert("������������!");

			input.focus();

		 	return false;

		}

	}

	return true;

}

/**
* isInteger �������Ϊ����
* frmName ������
* txtName ���������
* txtLab ������ǩ��
*/
function isInteger1(frmName,txtName,txtLab) 

{

	var frmTemp,temp;

	frmTemp=document.forms[frmName];

	temp=frmTemp.elements[txtName].value;

	if (temp=="")

	{

		alert("����" + txtLab +"����������!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	temp= Math.abs(temp);

	if(temp.toString()=="NaN")

	{

		alert("����" + txtLab +"����������!");

		frmTemp.elements[txtName].focus();

		return false;

	}

	if(parseInt(temp)<0)

	{

		alert("��" + txtLab +"������������");

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

		alert("��" + txtLab +"������������");

		frmTemp.elements[txtName].focus();

		return false; 

	}

	return true;

}

//isPosNumber : ��Ϊ���ĸ�����,����0
function isPosNumber(input) {

	oneDecimal = false;

	inputVal = input.value;

	if (isEmpty(inputVal)) 

	{

		alert("������������!");

		input.focus();

		return false;

	}

	for (var i = 0; i < inputVal.length; i ++ ) {

		var oneChar = inputVal.charAt(i);

		if (i == 0 && (oneChar == "+" || oneChar == "-") )

			if (inputVal.length == 1 )

			{

				alert("������������!");

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

			alert("������������!");

			input.focus();

		 	return false;

		}

	}

	if (parseFloat(inputVal) < 0)

	{

		alert("����������!");

		input.focus();

	 	return false;

	}

	return true;

}

//isArabic : ��������ɵ��ִ�
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

//isSKU : ��ȷ��SKU��ţ������в�һ������)
function CheckSKU(input) {

	var checkOK = "0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ";

	var checkStr = input.value;

	if (isEmpty(checkStr)) 

	{

		alert("������SKU!");

		input.focus();

		return false;

	}

	if (checkStr.length != 14)

	{

		alert("������14λSKU!");

		input.focus();

		return false;

	}

	for (i = 0;  i < checkStr.length;  i++){

		ch = checkStr.charAt(i);

		if (checkOK.indexOf(ch) == -1)

		{

			alert("SKU�а����Ƿ��ַ�");

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

			alert("����ѡ��ֿ�!");

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

			alert("����ѡ��ֿ�!");

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

			alert("����ѡ��ֿ�!");

			return false;

		}

		if(parseInt(lProductID) == -1)

		{

			alert("����ѡ������!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("û�����κ��ı��������");

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

			alert("����ѡ��ֿ�!");

			return false;

		}

		if(lProductID==null || lProductID=="")

		{

			alert("����ѡ������!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("û�����κ��ı��������");

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

			alert("����ѡ��ֿ�!");

			return false;

		}

		if(parseInt(lProjectID) == -1)

		{

			alert("����ѡ����Ŀ!");

			return false;

		}

		if(parseInt(lProductID) == -1)

		{

			alert("����ѡ������!");

			return false;

		}

		if(objBatchNo == "")

		{

			alert("û�����κ��ı��������");

			return false;

		}

		if(objQty == "")

		{

			alert("û�п������ʾ������");

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


 /*
	��ȡ���
	*/
 function getCursorPos(obj){
	obj.focus();
	var currentRange=document.selection.createRange();
	var workRange=currentRange.duplicate();
	obj.select();
	var allRange=document.selection.createRange();
	var pos=0;
	while(workRange.compareEndPoints("StartToStart",allRange)>0)
	{
	 workRange.moveStart("character",-1);
	 pos++;
	}
	currentRange.select();
	return pos;
}
	
/*
	��λ���
	*/
 function setCursorPos(obj,pos){
	var rng =obj.createTextRange();
	rng.moveStart('character',pos);
	rng.collapse(true);
	rng.select();
}

/* ˵��: ���������ı������İ�Ǳ�����תΪȫ�Ǳ��
  * obj:Ҫת���Ķ��� 
  * attribute: �ؼ�����
  */
 function halfTurnFull(obj,attribute) {
	var lValue = obj.value;
	var pos=getCursorPos(obj);//����ԭʼ���λ��
	if(lValue.indexOf("'")!=-1){lValue=lValue.replace("'","��");}//39
	if(lValue.indexOf("\"")!=-1){lValue=lValue.replace("\"","��");}//34
	if(lValue.indexOf("<")!=-1){lValue=lValue.replace("<","��");}//60
	if(lValue.indexOf(">")!=-1){lValue=lValue.replace(">","��");}//62
	if(lValue.indexOf("\\")!=-1){lValue=lValue.replace("\\","��");}//92
	if(lValue.indexOf("/")!=-1){lValue=lValue.replace("/","��");}//47
	document.getElementById(attribute).value = lValue;
	setCursorPos(obj,pos);//���ù��
	return; 
} 
