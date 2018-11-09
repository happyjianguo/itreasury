/*
	效验对象的值是否为指定的格式
	obj 需要效验的对象
	validateType 格式
		isEnValue  英文格式 允许下划线和点
		isTime24  24小时计时方式的格式 如23:22 0:12
*/
function validateObjValue(obj,validateType)
{
	var validateValue = obj.value.trim();
	if(validateType == 'isEnValue')
		return /^[a-zA-Z_.]+$/.test(validateValue);
	if(validateType == 'isTime24')
		return /^([0-9]|[0-1][0-9]|2[0-3]):[0-5]\d$/.test(validateValue);
	if(validateType == 'tlePhone')
		return /^([0-9]+-[0-9]+$)|(^[0-9]+$)|(^([0-9])[0-9]$)|(^013[0-9]+$)|(^13[0-9])+$/.test(validateValue);
	return false;
}
/*
	checkbox control
*/
function doSelectAllCheckBox(thisObj,from,checkBox)
{
	//如果多选框列表不为空
	if(typeof(checkBox)!= 'undefined')
	{
		//多选框元素个数大于1
		if(checkBox.length > 1)
		{
			for(var i = 0; i< checkBox.length; i++)
			{
				checkBox[i].checked = thisObj.checked;
			}
		}
		//多选框元素个数为1
		else
		{
			checkBox.checked = thisObj.checked;
		}
	}
	else
		return false;
}

/*
	checkbox control
*/
function doBoxControl(controlbox, sequencebox)
{
	var isControlChecked = controlbox.checked;
	if(sequencebox == null || sequencebox == 'null' || sequencebox == 'undefined')
		return;
	if(isNaN(sequencebox.length))
	{
		if (sequencebox.disabled == false)
			sequencebox.checked = isControlChecked;
	}
	else
	{
		for(var i = 0; i < sequencebox.length; i++)
		{
			if (sequencebox[i].disabled == false)
				sequencebox[i].checked = isControlChecked;
		}
	}
}
function doBoxChange(controlbox, sequencebox)
{
	var isControlChecked = true;
	if(controlbox == null || controlbox == 'null' || controlbox == 'undefined')
		return;
	if(isNaN(sequencebox.length))
	{
		if (sequencebox.checked == false)
			isControlChecked = false;
	}
	else
	{
		for(var i = 0; i < sequencebox.length; i++)
		{
			if (sequencebox[i].disabled == false && sequencebox[i].checked == false)
			{
				isControlChecked = false;
				break;
			}
		}
	}
	controlbox.checked = isControlChecked;
}

//用于多选CheckBox的选择,controlbox为父控件,sequencebox为子控件
//父控件和子控件均可为数组，且子控件数组根据父控件分组
//如controlbox 1 控制 sequencebox 1，sequencebox 2
//controlbox 2 控制 sequencebox 3，sequencebox 4，sequencebox 5的选择
//同组的controlbox和sequencebox设置相同的ID属性checkBoxID，如物资编码
//使用时控件必须为input对象，不能为struts的multibox标签，因为必须给定
//相同的ID属性，struts标签无该属性
function doMultiBoxControl(controlbox,sequencebox,checkBoxID)
{
	var isControlSelected;
	if(sequencebox==null)return;
	if(isNaN(controlbox.length))
	{
		isControlSelected=controlbox.checked;
		if(isNaN(sequencebox.length))
		{
			if (sequencebox.disabled==false)
				sequencebox.checked = isControlSelected;
		}
		else
		{
			for(var i=0;i<sequencebox.length;i++)
			{
				if (sequencebox[i].id==checkBoxID)
					if (sequencebox[i].disabled==false)
						sequencebox[i].checked = isControlSelected;
			}
		}
	}
	else
	{
		for(var i=0;i<controlbox.length;i++)
		{
			if (controlbox[i].id==checkBoxID)
				isControlSelected=controlbox[i].checked;
		}
		for(var i=0;i<sequencebox.length;i++)
		{
			if (sequencebox[i].id==checkBoxID)
				if (sequencebox[i].disabled==false)
					sequencebox[i].checked = isControlSelected;
		}
	}
}
//用于多选CheckBox的选择,controlbox为父控件,sequencebox为子控件
//父控件和子控件均可为数组，且子控件数组根据父控件分组
//如controlbox 1 控制 sequencebox 1，sequencebox 2
//controlbox 2 控制 sequencebox 3，sequencebox 4，sequencebox 5的选择
//同组的controlbox和sequencebox设置相同的ID属性checkBoxID，如物资编码
//使用时控件必须为input对象，不能为struts的multibox标签，因为必须给定
//相同的ID属性，struts标签无该属性
function doMultiBoxChange(controlbox,sequencebox,checkBoxID)
{
	var isSelected = true;
	var isChecked = true;
	var isReturnChecked = true;
	if(isNaN(sequencebox.length))
	{
		isSelected = sequencebox.checked;
		controlbox.checked = isSelected;
	}
	else
	{
		for(var i=0;i<sequencebox.length;i++)
		{
			isSelected = sequencebox[i].checked;
			isChecked = sequencebox[i].disabled;
			if (sequencebox[i].id==checkBoxID)
				if (isSelected==false && isChecked==false)
				{
					isReturnChecked = false;
					break;
				}
		}
	}
	if(isNaN(controlbox.length))
		controlbox = isReturnChecked;
	else
	{
		for(var i=0;i<controlbox.length;i++)
		{
			if (controlbox[i].id==checkBoxID)
			{
				controlbox[i].checked = isReturnChecked;
				break;
			}
		}
	}
}

function gourls(uri)
{
	var c_url = location.search;
	re = /definition=[\w\.]*&/;
	r = c_url.replace(re,"definition="+uri+"&");
	location.search=r;
}

/**
 * 判断radio是否有选中,如果选中返回选中的值
 *
 * @param radioObj radio对象
 */
	function getRadioCheckedValue(radioObj)
	{
		if(typeof(radioObj.length) == 'undefined')
		{
			return radioObj.value;
		}
		else
		{
			for(var i = 0; i< radioObj.length; i++)
			{
				if(radioObj[i].checked && radioObj[i].disabled==false)
				{
					return radioObj[i].value;
				}
			}
		}
		return '';
	}

/**
 * 判断radio是否有选中,或判断checkbox是否选中一个
 *
 * @param checkIdObj radio或checkbox对象
 */
function isRadioChecked(checkIdObj)
{
	//对象是否为空	
	if (checkIdObj == null) 
	{	
		return false;
	}
	//checkbox,并且列表对象有多个
	if(checkIdObj.length)
	{	
		var sum = 0;	
		for (i=0; i<checkIdObj.length; i++) 
		{	
			if(checkIdObj[i].checked  &&  checkIdObj[i].disabled==false)
			{	
				sum++;
			}	
		}	
		//checkbox中只有一个选中
		if (sum == 1)
		{
			return true;
		}		
	}	
	//radio,或checkbox列表对象只有一个	
	else if(checkIdObj.checked   &&  checkIdObj.disabled==false)
	{	
		return true;	
	}	
}

/**
 * 判断checkbox是否有选中
 *
 * @param checkIdObj checkbox对象
 */
function isBoxChecked(checkIdObj)
{
	//对象是否为空
	if(checkIdObj == null)
	{
		return false;
	}
	//checkbox列表对象有多个
	if(checkIdObj.length)
	{
		for(i = 0;i < checkIdObj.length;i++)
		{
			//checkbox有选中项
			if(checkIdObj[i].checked  &&  checkIdObj[i].disabled == false)
			{
				return true;
			}
		}
	}
	//checkbox列表对象只有一个
	else if(checkIdObj.checked  &&  checkIdObj.disabled == false)
	{
		return true;
	}	
}

/**
 * 判断checkbox是否有选中且只能选中一个
 *
 * @param checkIdObj checkbox对象
 */
function isBoxCheckedForOne(checkIdObj)
{
	var flag = 0;
	//对象是否为空
	if(checkIdObj == null)
	{
		return flag;
	}
	//checkbox列表对象有多个
	if(checkIdObj.length)
	{
		for(i = 0;i < checkIdObj.length;i++)
		{
			//checkbox有选中项
			if(checkIdObj[i].checked  &&  checkIdObj[i].disabled == false)
			{
				flag = flag + 1;
			}
		}
	}
	//checkbox列表对象只有一个
	else if(checkIdObj.checked  &&  checkIdObj.disabled == false)
	{
		flag = 1;
	}	
	return flag;
}
	 
  	 
	 /**
*将str中的第一个非空格字符前空格删除
*
*/	 
function Trim(str){
 if(str.charAt(0) == ' '){
  str = str.slice(1);
  str = Trim(str); 
 }
 return str;
}

/**
*dateBegin 第一个需要比较的页面日期输入域
*dateEnd 第二个需要比较的页面日期输入域
*isFocus 在校验不通过的情况下是否需要设点焦点
*第一个参数不是日期类型时2000-01-01,提示信息
*第二个参数不是日期类型时2000-01-01,提示信息
*第二个日期大于第一个日期的时候提示信息
*/
function CompareDate(dateBegin,dateEnd,isFocus,str1,str2,str3)
{
	var tempBegin,sBegin;
	var tempEnd,sEnd
			tempBegin=eval("dateBegin.value");
			tempEnd=eval("dateEnd.value");
			sBegin=new String("");
			sEnd=new String("");
			for(var i=0;i<=tempBegin.length-1;i++)
			{
				if(tempBegin.charAt(i)=="-" || tempBegin.charAt(i)=="/")
					sBegin=sBegin+"/";
				else
				{
					if(isNaN(Number(tempBegin.charAt(i))))
					{
						alert(str1);
						if (isFocus)
							dateBegin.focus();
						return false;
					}				
					else
						sBegin=sBegin+tempBegin.charAt(i);
				}
			}
			for(var i=0;i<=tempEnd.length-1;i++)
			{
				if(tempEnd.charAt(i)=="-" || tempEnd.charAt(i)=="/")
					sEnd=sEnd+"/";
				else
				{
					if(isNaN(Number(tempEnd.charAt(i))))
					{
						alert(str2);
						if (isFocus)
							dateEnd.focus();
						return false;
					}				
					else
						sEnd=sEnd+tempEnd.charAt(i);
				}
			}
			dtOne=new Date(sBegin);
			dtTwo=new Date(sEnd);
			if(dtOne.toString()=="NaN")
			{
				dateBegin.focus();
				alert(str1);
				return false;
			}
			if(dtTwo.toString()=="NaN")
			{
				if (isFocus)
					dateEnd.focus();
				alert(str2);
				return false;
			}
			if(dtOne.valueOf()>dtTwo.valueOf())
			{
				if (isFocus)
					dateEnd.focus();
				alert(str3);
				return false;
			}
			else
			{
				return true;
			}
}

/**
 * 反向格式化金额,去掉逗号
 * 自动设置焦点
 * @param strData 需要格式化的数据
 * @return 返回反格式化的金额
 */
 function reverseFormatAmount(thisPoint,lIsMin)
 {
		var i,strTemp;
		var strData = thisPoint.value;
		//去掉所有的","
		strData = reverseFormatAmountString(strData);
		thisPoint.value=strData;
		thisPoint.select();
 }
 /**
 *strAmount 需要反向格式化的金额字符串
 *return 反向格式化好的金额字符串
 */
 function reverseFormatAmountString(strAmount)
 {
 		//去掉所有的","
		strTemp=new String(strAmount);
		strAmount="";
		for(var i=0;i<strTemp.length;i++)
		{
			var cData;
			cData=strTemp.charAt(i);
			if (cData!=",")
			{
				strAmount=strAmount+cData;
			}
		}
		return strAmount;
 }
 
 /**
 * 格式化金额，自动设置焦点
 * @param strData 需要格式化的数据控件指针
  * @param lIsMin 是否可以为负数，1表示为正，-1表示为负，0表示可以为正可以为负
 * @return 设置数据控件的值为格式化后的金额字符串
 */
 function formatAmount(thisPoint,lScale,lIsMin)
 {
 	var strData = thisPoint.value;
	strData = reverseFormatAmountString(strData);
	strData = parseFloat(strData);
	if (strData>999999999999.99)
		strData = 0.00;
	strData = formatAmountString(strData,lScale,lIsMin);
	thisPoint.value=strData;
 }
 /*
 *
 */
 function getRoundAmount(strData,lScale)
 {
 	var lRectify = 1;
	if (lScale==1)
		lRectify = 10;
	else if (lScale==2)
		lRectify = 100;
	else if (lScale==3)
		lRectify = 1000;
	else if (lScale==4)
		lRectify = 10000;
	else if (lScale==5)
		lRectify = 100000;
	return Math.round(parseFloat(strData)*lRectify)/lRectify;
	/*var strDataCopy = strData;
	strDataCopy = Math.abs(strData);
	var add = 0;
    var s,temp;
    var s1 = strDataCopy + "";
    var start = s1.indexOf(".");
    if(start > 0 && s1.substr(start+lScale+1,1)>=5)add=1;
    var temp = Math.pow(10,lScale);
    s = Math.floor(strDataCopy * temp) + add;
	if (strData >= 0)
    	return s/temp;
	else
		return -1 * s/temp;*/
 }
 /*
 *strData 
 * * @param lIsMin 是否可以为负数，1表示为正，-1表示为负，0表示可以为正可以为负
 */
 function formatAmountString(strData,lScale,lIsMin)
 {
	if(!isNaN(parseFloat(strData)))
 	{
		if(strData!=null)
 		{
			var i,strTemp;

			//去掉所有的","
			strTemp=new String(strData);
			strData="";
			var isValidZero = true;
			var isValidComma = true;
			for(i=0;i<strTemp.length;i++)
			{
				var cData;
				cData=strTemp.charAt(i);
				if(cData=="-" && i==0 && (lIsMin==null || lIsMin=="undefined" || lIsMin == 0 || lIsMin == -1))
				{
					strData = "-";
				}
				else 
				if (cData=="0")
				{
					if (isValidZero)
						strData = strData + cData;
				}
				else
				if (cData==".")
				{
					if (strData!="" && isValidComma)
					{
						strData = strData + cData; 
						isValidComma=true;
					}
				} 
				else
				if (cData!="," && cData!=" ")
				{
					if (!isNaN(cData) || cData==".")
					{
						strData=strData+cData;
						isValidZero = true;
					}
					else
					{
						strData="";
						i=10000;
					}
				}
			}
		}
		if(strData!="")
 		{
			var strRoundAmunt ;
			strRoundAmunt = getRoundAmount(strData,lScale);
			strData = "" + strRoundAmunt;
			//将小数点前和后的数据分别取出来
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//小数点前面的数据加","
			strTemp=new String(strFront);
			var bHaveMinus=false;
			if(strFront.substring(0,1)=="-")
			{
				bHaveMinus=true;
				strTemp=strTemp.substring(1,strTemp.length);
			}
			strFront="";
			var nNum;
			nNum=0;
			for(i=strTemp.length-1;i>=0;i--)
			{
				if(nNum==3)
				{
					strFront=","+strFront ;
					nNum=0;
				}
				nNum++;
				var cData;
				cData=strTemp.charAt(i);
				strFront=cData+strFront;
			}
			if(bHaveMinus)
			{
				strFront="-" + strFront;
			}

			//补或者截小数点后面的值，保持两位
	 		if(strEnd.length>lScale)
	 		{
	 			strEnd=strEnd.substring(0,lScale);
	 		}
	 		else
	 		{
	 			for (i=strEnd.length;i<lScale;i++)
					strEnd = strEnd + "0";
	 		}
			//如果需要小数位
			if (parseInt(lScale,10)> 0)
				{
			 		strData=strFront+"." + strEnd;
				}
			else
				{
					strData=strFront;
				}

 		}
		else
		{
			strData="";
		}
	}
	else
	{
		strData = "";
	}

	if (strData=="")
	{
		if (parseInt(lScale,10)> 0)
			{
				strData="0.";
				for (i=0;i<lScale;i++)
					{
						strData=strData+"0";
					}
			}
		else
			{
				strData="0";
			}
	}
	return strData;
 }
 
 
  
function formatAmountNoComma(thisPoint,lScale,lIsMin)
 {
 	var strData = thisPoint.value;
	strData = reverseFormatAmountString(strData);
	strData = parseFloat(strData);
	if (strData>999999999999.99)
		strData = 0.00;
	strData = formatAmountStringNoComma(strData,lScale,lIsMin);
	thisPoint.value=strData;
 }
function formatAmountStringNoComma(strData,lScale,lIsMin)
 {
	if(!isNaN(parseFloat(strData)))
 	{
		if(strData!=null)
 		{
			var i,strTemp;

			//去掉所有的","
			strTemp=new String(strData);
			strData="";
			var isValidZero = true;
			var isValidComma = true;
			for(i=0;i<strTemp.length;i++)
			{
				var cData;
				cData=strTemp.charAt(i);
				if(cData=="-" && i==0 && (lIsMin==null || lIsMin=="undefined" || lIsMin == 0 || lIsMin == -1))
				{
					strData = "-";
				}
				else 
				if (cData=="0")
				{
					if (isValidZero)
						strData = strData + cData;
				}
				else
				if (cData==".")
				{
					if (strData!="" && isValidComma)
					{
						strData = strData + cData; 
						isValidComma=true;
					}
				} 
				else
				if (cData!="," && cData!=" ")
				{
					if (!isNaN(cData) || cData==".")
					{
						strData=strData+cData;
						isValidZero = true;
					}
					else
					{
						strData="";
						i=10000;
					}
				}
			}
		}
		if(strData!="")
 		{
			var strRoundAmunt ;
			strRoundAmunt = getRoundAmount(strData,lScale);
			strData = "" + strRoundAmunt;
			//将小数点前和后的数据分别取出来
	 		var nPoint;
	 		nPoint=strData.indexOf(".");
	 		var strFront=strData,strEnd="";
	 		if(nPoint!=-1)
	 		{
	 			strFront=strData.substring(0,nPoint);
	 			strEnd=strData.substring(nPoint+1,strData.length);
	 		}

			//补或者截小数点后面的值，保持两位
	 		if(strEnd.length>lScale)
	 		{
	 			strEnd=strEnd.substring(0,lScale);
	 		}
	 		else
	 		{
	 			for (i=strEnd.length;i<lScale;i++)
					strEnd = strEnd + "0";
	 		}
			//如果需要小数位
			if (parseInt(lScale,10)> 0)
				{
			 		strData=strFront+"." + strEnd;
				}
			else
				{
					strData=strFront;
				}

 		}
		else
		{
			strData="";
		}
	}
	else
	{
		strData = "";
	}

	if (strData=="")
	{
		if (parseInt(lScale,10)> 0)
			{
				strData="0.";
				for (i=0;i<lScale;i++)
					{
						strData=strData+"0";
					}
			}
		else
			{
				strData="0";
			}
	}
	return strData;
 }

/**
 * 分配权限页面，选中checkbox后触发
 */
function rightBoxChange(thisObj, formName, checkboxName, propertyName)
{
	//form, checkbox, rightNo
	var formCtrl = eval("document." + formName);
	var checkboxCtrl = eval("document." + formName + ".elements['" + checkboxName + "']");
	var propertyCtrl = eval("document." + formName + ".elements['" + propertyName + "']");
	
	if (isNaN(checkboxCtrl.length))
		return;
	
	//nIndex
	var nIndex = 0;
	for (var i = 0; i < checkboxCtrl.length; i++)
	{
		if (checkboxCtrl[i] == thisObj)
		{
			nIndex = i;
			break;
		}
	}
	
	var isChecked = checkboxCtrl[nIndex].checked;
	var nLength = propertyCtrl[nIndex].value.length;
	var strFirst, strSecond, strThird, strFourth, strFifth;
	if (nLength == 2)
	{
		strFirst = propertyCtrl[nIndex].value;
		
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value.substring(0,2) == strFirst)
				checkboxCtrl[i].checked = isChecked;
		}
	}
	else if (nLength == 4)
	{
		strFirst = propertyCtrl[nIndex].value.substring(0,2);
		strSecond = propertyCtrl[nIndex].value;
		
		var bFlag = false;
		var nUpperIndex = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value.substring(0,4) == strSecond)
				checkboxCtrl[i].checked = isChecked;
				
			if (propertyCtrl[i].value == strFirst)
				nUpperIndex = i;
				
			if (propertyCtrl[i].value.length == 4
				&& propertyCtrl[i].value.substring(0,2) == strFirst
				&& checkboxCtrl[i].checked)
				bFlag = true;
		}
		checkboxCtrl[nUpperIndex].checked = bFlag;
	}
	else if (nLength == 6)
	{
		strFirst = propertyCtrl[nIndex].value.substring(0,2);
		strSecond = propertyCtrl[nIndex].value.substring(0,4);
		strThird = propertyCtrl[nIndex].value;
		
		var bFlag = false;
		var nUpperIndex = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value.substring(0,6) == strThird)
				checkboxCtrl[i].checked = isChecked;
				
			if (propertyCtrl[i].value == strSecond)
				nUpperIndex = i;
				
			if (propertyCtrl[i].value.length == 6
				&& propertyCtrl[i].value.substring(0,4) == strSecond
				&& checkboxCtrl[i].checked)
				bFlag = true;
		}
		checkboxCtrl[nUpperIndex].checked = bFlag;
		
		var bFlag2 = false;
		var nUpperIndex2 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strFirst)
				nUpperIndex2 = i;
				
			if (propertyCtrl[i].value.length == 4
				&& propertyCtrl[i].value.substring(0,2) == strFirst
				&& checkboxCtrl[i].checked)
				bFlag2 = true;
		}
		checkboxCtrl[nUpperIndex2].checked = bFlag2;
	}
	else if (nLength == 8)
	{
		strFirst = propertyCtrl[nIndex].value.substring(0,2);
		strSecond = propertyCtrl[nIndex].value.substring(0,4);
		strThird = propertyCtrl[nIndex].value.substring(0,6);
		strFourth = propertyCtrl[nIndex].value;
		
		var bFlag = false;
		var nUpperIndex = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value.substring(0,8) == strFourth)
				checkboxCtrl[i].checked = isChecked;
				
			if (propertyCtrl[i].value == strThird)
				nUpperIndex = i;
				
			if (propertyCtrl[i].value.length == 8
				&& propertyCtrl[i].value.substring(0,6) == strThird
				&& checkboxCtrl[i].checked)
				bFlag = true;
		}
		checkboxCtrl[nUpperIndex].checked = bFlag;
		
		var bFlag2 = false;
		var nUpperIndex2 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strSecond)
				nUpperIndex2 = i;
				
			if (propertyCtrl[i].value.length == 6
				&& propertyCtrl[i].value.substring(0,4) == strSecond
				&& checkboxCtrl[i].checked)
				bFlag2 = true;
		}
		checkboxCtrl[nUpperIndex2].checked = bFlag2;
		
		var bFlag3 = false;
		var nUpperIndex3 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strFirst)
				nUpperIndex3 = i;
				
			if (propertyCtrl[i].value.length == 4
				&& propertyCtrl[i].value.substring(0,2) == strFirst
				&& checkboxCtrl[i].checked)
				bFlag3 = true;
		}
		checkboxCtrl[nUpperIndex3].checked = bFlag3;
	}
	else if (nLength == 10)
	{
		strFirst = propertyCtrl[nIndex].value.substring(0,2);
		strSecond = propertyCtrl[nIndex].value.substring(0,4);
		strThird = propertyCtrl[nIndex].value.substring(0,6);
		strFourth = propertyCtrl[nIndex].value.substring(0,8);
		strFifth = propertyCtrl[nIndex].value;
		
		var bFlag = false;
		var nUpperIndex = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{/*
			if (propertyCtrl[i].value.substring(0,8) == strFourth)
				checkboxCtrl[i].checked = isChecked;
			*/
			if (propertyCtrl[i].value == strFourth)
				nUpperIndex = i;
				
			if (propertyCtrl[i].value.length == 10
				&& propertyCtrl[i].value.substring(0,8) == strFourth
				&& checkboxCtrl[i].checked)
				bFlag = true;
		}
		checkboxCtrl[nUpperIndex].checked = bFlag;
		
		var bFlag2 = false;
		var nUpperIndex2 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strThird)
				nUpperIndex2 = i;
				
			if (propertyCtrl[i].value.length == 8
				&& propertyCtrl[i].value.substring(0,6) == strThird
				&& checkboxCtrl[i].checked)
				bFlag2 = true;
		}
		checkboxCtrl[nUpperIndex2].checked = bFlag2;
		
		var bFlag3 = false;
		var nUpperIndex3 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strSecond)
				nUpperIndex3 = i;
				
			if (propertyCtrl[i].value.length == 6
				&& propertyCtrl[i].value.substring(0,4) == strSecond
				&& checkboxCtrl[i].checked)
				bFlag3 = true;
		}
		checkboxCtrl[nUpperIndex3].checked = bFlag3;
		
		var bFlag4 = false;
		var nUpperIndex4 = 0;
		for (var i = 0; i < propertyCtrl.length; i++)
		{
			if (propertyCtrl[i].value == strFirst)
				nUpperIndex4 = i;
				
			if (propertyCtrl[i].value.length == 4
				&& propertyCtrl[i].value.substring(0,2) == strFirst
				&& checkboxCtrl[i].checked)
				bFlag4 = true;
		}
		checkboxCtrl[nUpperIndex4].checked = bFlag4;
	}
}
	
/**
 * 判断物资编码是否合法
 * @param strNO 物资编码
 * @return true(合法);false(不合法)
 */
function isValidProductNO(strNO)
{
	//不能为空,长度必须为16位
	if((Trim(strNO) == '') || (strNO.length != 16))	return false;
	//必须是数字
	else if(isNaN(strNO)) return false;
	else
	{
		//大类
		var strLarger = strNO.substring(0,2);
		var nLarger = parseInt(strLarger,10);
		if((nLarger <= 0) || (nLarger> 56)) return false;
		//中类
		var strMiddle = strNO.substring(2,4);
		var nMiddle = parseInt(strMiddle,10);
		if((nMiddle % 2 != 1) && (nMiddle != 0)) return false;
		//小类
		var strSmall = strNO.substring(4,6);
		var nSmall = parseInt(strSmall,10);
		if((nSmall % 2 != 0) && (nSmall != 99)) return false;
		//特征1
		var strSpec1 = strNO.substring(6,9);
		var nSpec1 = parseInt(strSpec1,10);
		if((nSpec1 % 2 != 1) && (nSpec1 != 0)) return false;
		//特征2
		var strSpec2 = strNO.substring(9,13);
		var nSpec2 = parseInt(strSpec2,10);
		if((nSpec2 % 2 != 0) && (nSpec2 != 9999)) return false;
		//特征3
		var strSpec3 = strNO.substring(13,16);
		var nSpec3 = parseInt(strSpec3,10);
		if((nSpec3 % 2 != 1) && (nSpec3 != 0)) return false;
		
		return true;
	}
}
		
	/**
	* @param procuctNO 物资编码对象
	* @param ltype 物资编码类型
	* @return true(符合物资编码规范) false(反之)
	*/	
 	function checkProductNO(procuctNO,ntype)
	{
		var strProductNO = procuctNO.value;
		//输入项可以为空
		if (Trim(strProductNO) == '') return true;
		//输入项必须是数字
		else if (isNaN(strProductNO)) return false;
		else
			{
				switch(parseInt(ntype,10))
					{
						case 1:
						{
							//大类必须是两位而且不大于56
							if (strProductNO.length == 2 && parseInt(strProductNO,10) <= 56)
							return true;
						}
						break;
						case 2:
						case 3:
						{
							//中类必须是两位,小类必须是两位
							if (strProductNO.length == 2)
							return true;
						}
						break;
						case 4:
						case 6:
						{
							//特征1必须是3位,特征3必须是3位
							if (strProductNO.length == 3)
							return true;
						}
						break;
						case 5:
						{
							//特征2必须是4位
							if (strProductNO.length == 4)
							return true;
						}
						break;
					}
			}
	}
	/**
	 *物资编码自动补0或9
	 *@param formName
	 *@param strCtrlName
	 *@param index 数组下标
	 *@param beginOrEnd 0表示补0，9表示补9
	 */
	function formatProductNO(formName,strCtrlName,index,beginOrEnd)
		{
			var strProductNO ; 
			if (eval("document."+formName+".elements[\""+strCtrlName+"\"]").length)
				{	
					eval("strProductNO = document."+formName+".elements[\""+strCtrlName+"\"]["+index+"].value");
					if (Trim(strProductNO) == "")
				 		return;
					else
						{
							while(strProductNO.length < 16)
								{
									strProductNO += beginOrEnd;
								}
						}
					eval("document."+formName+".elements[\""+strCtrlName+"\"]["+index+"].value = strProductNO");

				}
			else
				{
					eval("strProductNO = document."+formName+".elements[\""+strCtrlName+"\"].value");
					if (Trim(strProductNO) == "")
				 		return;
					else
						{
							while(strProductNO.length < 16)
								{
									strProductNO += beginOrEnd;
								}
						}
					eval("document."+formName+".elements[\""+strCtrlName+"\"].value = strProductNO");
				}
				
		}
/**
功能：校验表单金额数组
使用范围：表单中有批量录入、修改数据，并且通过选择每条数
		  据前的checkbox或者radio来进行操作的，ehckbox和radio
		  的值为数组下标
参数：1、selectedObj    checkbox 或者 raido 的对象，如nameForm.checkBoxName
	  2、checkObj  要校验的金额数组的对象   如 nameForm.amountName
	  3、focusObj  校验如果没有通过焦点的位置，如果focusObj没有定义或者为""，
	  则焦点被设置到checkObj的相应位置上
return：通过校验返回  true ;否则返回false
*/		
function checkMutiAmount(selectedObj,checkObj,focusObj)
{
	var returnCehckResult = true;
	if (selectedObj=="")
	{
		if (isNaN(checkObj.length))
		{
			if (parseFloat(reverseFormatAmountString(checkObj.value))<=0)
			{
				if (focusObj==null || focusObj=="")
				{
					checkObj.focus();
					checkObj.select();
				}
				else
				{
					focusObj.focus();
					focusObj.select();
				}
				returnCehckResult = false;
			}	
		}
		else
		{
			for (var i=0;i<checkObj.length;i++)
			{
				if (parseFloat(reverseFormatAmountString(checkObj[i].value))<=0)
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						focusObj[i].focus();
						focusObj[i].select();
					}
					returnCehckResult = false;
				}
			}
		}
	}
	else if (isNaN(selectedObj.length))
	 {
	 	if (parseFloat(reverseFormatAmountString(checkObj.value))<=0)
		{
			if (focusObj==null || focusObj=="")
			{
				checkObj.focus();
				checkObj.select();
			}
			else
			{
				focusObj.focus();
				focusObj.select();
			}
			returnCehckResult = false;
		}
		else
		{
			return true;
		}
	 }
	 else
	 {
	 	for (var i=0;i<selectedObj.length;i++)
		{
			if (selectedObj[i].checked)
			{
				if (parseFloat(reverseFormatAmountString(checkObj[i].value))<=0)
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						focusObj[i].focus();
						focusObj[i].select();
					}
					returnCehckResult = false;
				}
			}
		}
	 }
	 return returnCehckResult;
}

/**
校验字符串是否为日期  即满足  yyyy-MM-DD 格式
是：返回 true
否：返回 false

*/
function checkDate(strDate)
{
	var strCheckDate = "";
	if(strDate.length != 10)
	{
		return false;
	}
	for(var i=0;i<=strDate.length-1;i++)
		{
			if(strDate.charAt(i)=="-" || strDate.charAt(i)=="/")
			{
				strCheckDate=strCheckDate+"/";
			}
			else
			{
				if(isNaN(Number(strDate.charAt(i))))
				{
					return false;
				}				
				else
				{
					strCheckDate=strCheckDate+strDate.charAt(i);
				}
			}
		}
	dtOne=new Date(strCheckDate);
	if(dtOne.toString()=="NaN")
	{
		return false;
	}
	var y=dtOne.getFullYear();
    var m=dtOne.getMonth()+1;
    var d=dtOne.getDate();
	if (m<10) m="0"+m;
	if (d<10) d="0"+d;
	var myday=y + "-" + m + "-" + d;
	if (myday!=strDate)
	{
		return false;
	}
	return true;
}


/**
add by jianwu
校验字符串是否为日期  即满足  yyyy-MM-DD HH:MM:SS 格式
是：返回 true
否：返回 false

*/
function checkDateHHMMSS(strDate)
{
	var strCheckDate = "";
	if(strDate.length != 19)
	{
		return false;
	}	
	for(var i=0;i<=strDate.length-1;i++)
		{
			if(strDate.charAt(i)=="-" || strDate.charAt(i)=="/")
			{
				strCheckDate=strCheckDate+"/";
			}
			else
			{
					strCheckDate=strCheckDate+strDate.charAt(i);	
			}
		}		
	dtOne=new Date(strCheckDate);
	if(dtOne.toString()=="NaN")
	{
		return false;
	}
	var y=dtOne.getFullYear();
    var m=dtOne.getMonth()+1;
    var d=dtOne.getDate();
    var h=dtOne.getHours();
    var mi=dtOne.getMinutes();
    var s=dtOne.getSeconds();		
	if (m<10) m="0"+m;
	if (d<10) d="0"+d;
	if(h<10) h ="0"+h;
	if(mi<10) mi="0"+mi;
	if(s<10) s="0"+s;
	var myday=y + "-" + m + "-" + d +" "+h+":"+mi+":"+s;
	if (myday!=strDate)
	{
		return false;
	}
	return true;
}

/**
比较日期 ，返回 。。。天。。。小时。。。分。。。秒
asStartDate：起始日期
asEndDate：结束日期
diffDate: 预设施检差
author:jianwu
*/
function DateCompare(asStartDate,asEndDate,diffDate){
  	var miStart=Date.parse(asStartDate.replace(/\-/g,'/'));
  	var miEnd=Date.parse(asEndDate.replace(/\-/g,'/'));
	if(miEnd-miStart-diffDate < 0)
	{
		return "已过结束时间";
	}
	var day = parseInt((miEnd-miStart-diffDate)/(1000*24*3600));
	var ho = parseInt((miEnd-miStart-diffDate - (1000*24*3600*day))/(1000*3600));
	var mi = parseInt((miEnd-miStart-diffDate - (1000*24*3600*day)-(1000*3600*ho))/(1000*60));	
	var se = parseInt((miEnd-miStart-diffDate - (1000*24*3600*day)-(1000*3600*ho)-(1000*60*mi))/(1000));		
  	return parseInt((miEnd-miStart-diffDate)/(1000*24*3600))+"天"+ho+"小时"+mi+"分"+se+"秒";
}
/**
比较日期 ，返回毫秒数
asStartDate：起始日期
asEndDate：结束日期
author:jianwu
*/
function DateDiff(asStartDate,asEndDate){
  	var miStart=Date.parse(asStartDate.replace(/\-/g,'/'));
  	var miEnd=Date.parse(asEndDate.replace(/\-/g,'/'));
  	return (miEnd-miStart);
}
/**
将日期类型格式化为yyyy-mm-dd hh:mm:ss
add by jianwu
*/
function formatDateHHMMSS(dtDate)
{
	var y=dtDate.getFullYear();
    var m=dtDate.getMonth()+1;
    var d=dtDate.getDate();
    var h=dtDate.getHours();
    var mi=dtDate.getMinutes();
    var s=dtDate.getSeconds();		
	if (m<10) m="0"+m;
	if (d<10) d="0"+d;
	if(h<10) h ="0"+h;
	if(mi<10) mi="0"+mi;
	if(s<10) s="0"+s;
	var myday=y + "-" + m + "-" + d +" "+h+":"+mi+":"+s;
	return myday;
}
/**
功能：校验表单日期数组
使用范围：表单中有批量录入、修改数据，并且通过选择每条数
		  据前的checkbox或者radio来进行操作的，ehckbox和radio
		  的值为数组下标
参数：1、selectedObj    checkbox 或者 raido 的对象，如nameForm.checkBoxName
	  2、checkObj  要校验的日期数组的对象   如 nameForm.amountName
	  3、focusObj  校验如果没有通过焦点的位置，如果focusObj没有定义或者为""，
	  则焦点被设置到checkObj的相应位置上
return：通过校验返回  true ;否则返回false
*/		
function checkMutiDate(selectedObj,checkObj,focusObj)
{
	var returnCehckResult = true;
	if (selectedObj=="")
	{
		if (isNaN(checkObj.length))
		{
			if (checkObj.value!="" && !checkDate(checkObj.value))
			{
				if (focusObj==null || focusObj=="")
				{
					checkObj.focus();
					checkObj.select();
				}
				else
				{
					focusObj.focus();
					focusObj.select();
				}
				returnCehckResult = false;
			}	
		}
		else
		{
			for (var i=0;i<checkObj.length;i++)
			{
				if (checkObj[i].value!="" && !checkDate(checkObj[i].value))
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						focusObj[i].focus();
						focusObj[i].select();
					}
					returnCehckResult = false;
				}
			}
		}
	}
	else if (isNaN(selectedObj.length))
	 {
	 	if (checkObj.value!="" && !checkDate(checkObj.value))
		{
			checkObj.value="";
			if (focusObj==null || focusObj=="")
			{
				checkObj.focus();
				checkObj.select();
			}
			else
			{
				focusObj.focus();
				focusObj.select();
			}
			returnCehckResult = false;
		}
		else
		{
			return true;
		}
	 }
	 else
	 {
	 	for (var i=0;i<selectedObj.length;i++)
		{
			if (selectedObj[i].checked)
			{
				if (checkObj[i].value!="" &&  !checkDate(checkObj[i].value))
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						if (isNaN(focusObj.length))
						{
							focusObj.focus();
							focusObj.select();
						}
						else
						{
							focusObj.focus();
							focusObj.select();
						}
					}
					returnCehckResult = false;
				}
			}
		}
	 }
	 return returnCehckResult;
}

/**
功能：校验表单字符串数组不能为空
使用范围：表单中有批量录入、修改数据，并且通过选择每条数
		  据前的checkbox或者radio来进行操作的，ehckbox和radio
		  的值为数组下标
参数：1、selectedObj    checkbox 或者 raido 的对象，如nameForm.checkBoxName
	  2、checkObj  要校验的字符串数组的对象   如 nameForm.amountName
	  3、focusObj  校验如果没有通过焦点的位置，如果focusObj没有定义或者为""，
	  则焦点被设置到checkObj的相应位置上
return：通过校验返回  true ;否则返回false
*/		
function checkMutiRequire(selectedObj,checkObj,focusObj)
{
	var returnCehckResult = true;
		if (selectedObj=="")
	{
		if (isNaN(checkObj.length))
		{
			if (Trim(checkObj.value)=="")
			{
				if (focusObj==null || focusObj=="")
				{
					checkObj.focus();
					checkObj.select();
				}
				else
				{
					focusObj.focus();
					focusObj.select();
				}
				returnCehckResult = false;
			}	
		}
		else
		{
			for (var i=0;i<checkObj.length;i++)
			{
				if (Trim(checkObj[i].value)=="")
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						focusObj[i].focus();
						focusObj[i].select();
					}
					returnCehckResult = false;
				}
			}
		}
	}
	else if (isNaN(selectedObj.length))
	 {
	 	if (Trim(checkObj.value)=="")
		{
			if (focusObj==null || focusObj=="")
			{
				checkObj.focus();
				checkObj.select();
			}
			else
			{
						if (isNaN(focusObj.length))
						{
							focusObj.focus();
							focusObj.select();
						}
						else
						{
							focusObj.focus();
							focusObj.select();
						}
			}
			returnCehckResult = false;
		}
		else
		{
			return true;
		}
	 }
	 else
	 {
	 	for (var i=0;i<selectedObj.length;i++)
		{
			if (selectedObj[i].checked)
			{
				if (Trim(checkObj[i].value)=="")
				{
					if (focusObj==null || focusObj=="")
					{
						checkObj[i].focus();
						checkObj[i].select();
					}
					else
					{
						if (isNaN(focusObj.length))
						{
							focusObj.focus();
							focusObj.select();
						}
						else
						{
							focusObj.focus();
							focusObj.select();
						}
					}
					returnCehckResult = false;
				}
			}
		}
	 }
	 return returnCehckResult;
}

/**返回文本字符长度
  *@param checkObj 页面上的元素
  *@param lLength 能输入文本的最大长度
  *@param promptInfo 要显示的提示信息
  */
  function checkTextLength(checkObj,lLength,promptInfo)
  	{
		if (checkObj.value.length> parseInt(lLength,10))
			{
				alert(" \""+promptInfo+"\" " + "只能输入 " + lLength + " 个字符,你现在已经输入" + checkObj.value.length + " 个字符! ");
				checkObj.focus();
				return false;
			}
		else
			return true;
	}
/*
*设置Form中checkBox或者radio被选中
*用于页面中明细信息发生变化时自动选中当前记录
*用法：在input 的 onChange事件中调用此方法
*@param thisObj 当前input的指针，固定传入 this
*@param thisObjs 当前 input 的名称，如：form.userName
*@param checkBoxOjb heckBox或者radio对象，如：form.selectNo
*/
function returnSelectedRowNumber(thisObj,thisObjs,checkBoxObjs)
{
	if (isNaN(thisObjs.length))
	{
        return 0;
    }
	else
	{
    	for (var i=0;i<thisObjs.length;i++)
		{
			if (thisObj==thisObjs[i])
			{
                return i;
			}
		}
	}
}
	
/*
*设置Form中checkBox或者radio被选中
*用于页面中明细信息发生变化时自动选中当前记录
*用法：在input 的 onChange事件中调用此方法
*@param thisObj 当前input的指针，固定传入 this
*@param thisObjs 当前 input 的名称，如：form.userName
*@param checkBoxOjb heckBox或者radio对象，如：form.selectNo
*/
function autoSelectChecked(thisObj,thisObjs,checkBoxObjs)
{
	if (isNaN(checkBoxObjs.length))
	{
		if (checkBoxObjs!=null && checkBoxObjs!="")
			if(checkBoxObjs.disabled != true)
				checkBoxObjs.checked = true;
	}
	else
	{
		for (var i=0;i<thisObjs.length;i++)
		{
			if (thisObj==thisObjs[i])
			{
				if (checkBoxObjs!=null && checkBoxObjs!="")
                {
					if(checkBoxObjs[i].disabled != true)
        	            checkBoxObjs[i].checked = true;
                    break;
                }
			}
		}
	}
}

function autoSelectCheckedByValue(thisObj,thisObjs,thisObjValues,checkBoxObjs)
{
	var compValue = "";
	if (isNaN(checkBoxObjs.length))
	{
		if (checkBoxObjs!=null && checkBoxObjs!="")
			checkBoxObjs.checked = true;
	}
	else
	{
		for (var i=0;i<thisObjValues.length;i++)
		{
			if (thisObj == thisObjs[i])
				compValue = thisObjValues[i].value
		}
		for (var i=0;i<thisObjValues.length;i++)
		{
			if (thisObjValues[i].value == compValue)
			{
				if (checkBoxObjs!=null && checkBoxObjs!="")
					checkBoxObjs[i].checked = true;
			}
		}
	}
}

function setButtonDisableByExecPrice(checkObj,priceObj,execPriceObj)
{
	if (priceObj==null)
	{
		return;
	}
	else if (isNaN(priceObj.length))
	{
		if (parseFloat(reverseFormatAmountString(priceObj.value)) != parseFloat(reverseFormatAmountString(execPriceObj.value)))
		{
			checkObj.disabled=true;
		}
	}
	else
	{
		for (var i =0 ;i<priceObj.length;i++)
		{
			if (parseFloat(reverseFormatAmountString(priceObj[i].value)) != parseFloat(reverseFormatAmountString(execPriceObj[i].value)))
			{
				checkObj[i].disabled=true;
			}
		}
	}
}

function filterByCompare(compareObj,compareObjs,checkObjs)
{
	if (compareObjs == null)
	{
		return;
	}
	if (isNaN(compareObjs.length)) 
	{
		if (compareObj.value != "")
		{
			if (compareObjs.value != compareObj.value)
			{
				checkObjs.disabled = true;
			}
		}
	}
	else
	{
		for (var i=0;i<compareObjs.length;i++)
		{
			if (compareObj.value != "")
			{
				if (compareObjs[i].value != compareObj.value)
				{
					checkObjs[i].disabled = true;
				}
			}
		}
	}
}
/**判断相同物资编码，批次的物资是否重复，对重复物资disable*/
	function setProdcutButtonDisableByBatchIsExist(checkBoxObj,productObj,strArgObj1)
	{
		if (productObj==null)
		{
			return;
		}
		else if (isNaN(productObj.length))
		{
		  	var strArg1;
	
		  	if (strArgObj1!=null && strArgObj1!="")
		  		strArg1=strArgObj1.value;
		  	else 
		  		strArg1="";			  	
		  	
			if (checkIsExistProductNo(productObj.value,strArg1))
			{
				checkBoxObj.disabled=true;					
			}

		}
		else
		{
			for (var i =0 ;i<productObj.length;i++)
			{
			  	var strArg1;			  	
			  	if (strArgObj1!=null && strArgObj1!="")
			  		strArg1=strArgObj1[i].value;
			  	else 
			  		strArg1="";			  				  	
				if (checkIsExistProductNo(productObj[i].value,strArg1))
				{
					checkBoxObj[i].disabled=true;							
				}
			}			
		}		
	}

/**判断相同物资编码，批次的价格是否不同，对不同价格物资disable*/
	function setProdcutButtonDisableByPriceIsSame(checkBoxObj,productObj,strArgObj1)
	{
		if (productObj==null)
		{
			return;
		}
		else if (isNaN(productObj.length))
		{
		  	var strArg1;
	
		  	if (strArgObj1!=null && strArgObj1!="")
		  		strArg1=strArgObj1.value;
		  	else 
		  		strArg1="";			  	
		  	
			if(checkIsExistProductNo(productObj.value))
			{
				if(!checkIsExistProductNo(productObj.value,strArg1,""))
				{
					checkBoxObj.disabled=true;					
				}
			} 
		}
		else
		{
			for (var i =0 ;i<productObj.length;i++)
			{
			  	var strArg1;			  	
			  	if (strArgObj1!=null && strArgObj1!="")
			  		strArg1=strArgObj1[i].value;
			  	else 
			  		strArg1="";			  				  				  		
				if(checkIsExistProductNo(productObj[i].value))
				{
				   if(!checkIsExistProductNo(productObj[i].value,strArg1,""))
					{
							checkBoxObj[i].disabled=true;							
					}
				}
			}
		}			
				
	}


/**
 * 设置 Select 的缺省值
 * 
 * @param selectObj     Select控件
 * @param strValue      缺省值
 * 
 * @modify:     bingli@isoftstone.com
 * @date:       2004-11-26
 */
function selectOption(selectObj, strValue)
{
    if(selectObj != null && selectObj != 'undefined')
    {
        if(isNaN(selectObj.length))
        {
            if(selectObj.value == strValue)
            {
                selectObj.selected = true;
            }
        }
        else
        {
        	for (i = 0; i < selectObj.length; i++)
        	{
                if(selectObj[i].value == strValue)
                {
                    selectObj[i].selected = true;
                    break;
                }
        	}
        }
    }
}

/**
 * 设置 Radio 的缺省值
 * 
 * @param radioObj      Radio控件
 * @param strValue      缺省值
 *
 * 设置 Checkbox 的缺省值时，可以通过多次调用 radioOption() 来实现：
 * radioOption(document.formName.elements['selectIndex'],"0");
 * radioOption(document.formName.elements['selectIndex'],"1");
 * radioOption(document.formName.elements['selectIndex'],"2");
 * 
 * @modify:     bingli@isoftstone.com
 * @date:       2004-11-26
 */
function radioOption(radioObj, strValue)
{
    if(radioObj != null && radioObj != 'undefined')
    {
        if(isNaN(radioObj.length))
        {
            if(radioObj.value == strValue)
            {
                radioObj.checked = true;
            }
        }
        else
        {
        	for (i = 0; i < radioObj.length; i++)
        	{
                if(radioObj[i].value == strValue)
                {
                    radioObj[i].checked = true;
                    //break;
                }
        	}
        }
    }
}

/**验证大类的范围
 * @param targetProductNo 要验证的物资编码
 * @param srcProductNo1s  IC卡的物资编码范围(split with comma)
 */
function validCatalog(targetProductNo, srcProductNo1s)
	{
		if (srcProductNo1s == null || srcProductNo1s == "")
			{
				return false;
			}
		var lTargetProductNo = parseInt(Trim(targetProductNo), 10);
		if (Trim(srcProductNo1s) == "")
			{
				return false;
			}
		var srcProductNos = srcProductNo1s.split(",");
		 for (var i = 0;i < srcProductNos.length;i++)
		 	{
				var lSrcProductNo = parseInt(Trim(srcProductNos[i]), 10);
				if (lTargetProductNo == lSrcProductNo)
					{
						return true;
					}
			}
		return false;
	}
	
/**制作领料申请刷卡验证身份
 @ param ipsGydw 供应单位
 @ param totalMoney 验证金额 is a object,need to convert to double
 @ param catalogNO 物资大类
 @ param strOutboundID 领料申请编号
 */
function brushCardForApply(ipsGydw, catalogNO, totalMoney, strOutboundID)
	{
		var ipsFile;//卡机配置文件
		ipsFile = "wsjs.ini";
		//提示用户输入密码
		if (!confirm("请确认已输入密码，并且卡已放好！"))
			return false;
		//读卡
		var nResult;
		nResult = parseInt(window.SlofIC.ReadIc(ipsFile, ipsGydw, "1", strDllVesion), 10);//标志1表示在科室刷卡
		//如果读卡失败
		if (nResult < 0)
			{
				window.SlofIC.FinishCycle();
				return false;
			}
		//取IC卡号，日志编号以及物资权限
		var strIcNo;
		var strLogNo;
		var strWzNo;
		var dMoney;
		strIcNo = window.SlofIC.Icbh();
		strLogNo = window.SlofIC.Iclog();
		strWzNo = window.SlofIC.Wzqx();
		if (strIcNo == "")
			{
				alert("读取IC卡信息失败，请重新刷卡！");
				window.SlofIC.FinishCycle();
				return false;
			}		
		strIcNoForSave = strIcNo;
		dMoney = parseFloat(reverseFormatAmountString(totalMoney));
		if (!validCatalog(catalogNO, strWzNo))
			{
				alert("发料单物资大类不在IC卡所有大类之中！");
				//写日志
				window.SlofIC.WriteIcLog(strIcNo, strLogNo, dMoney, "发料单物资大类不在IC卡所有大类之中，放弃制领料申请单", '');
				window.SlofIC.FinishCycle();
				return false;
			}
		//写日志
		//window.SlofIC.WriteIcLog(strIcNo, strLogNo, dMoney, "制发料单。编号="+strOutboundID, '');
		window.SlofIC.FinishCycle();
		return true;
	}	
	
/**科室刷卡验证身份和金额
 @ param ipsGydw 供应单位
 @ param totalMoney 验证金额 is a object,need to convert to double
 @ param catalogNO 物资大类
 @ param strOutboundID 发料单ID
 */
function brushCardForDept(ipsGydw, catalogNO, totalMoney, strOutboundID)
	{
		var ipsFile;//卡机配置文件
		ipsFile = "wsjs.ini";
		//提示用户输入密码
		if (!confirm("请确认已输入密码，并且卡已放好！"))
			return false;
		//读卡
		var nResult;
		nResult = parseInt(window.SlofIC.ReadIc(ipsFile, ipsGydw, "1", strDllVesion), 10);//标志1表示在科室刷卡
		//如果读卡失败
		if (nResult < 0)
			{
				window.SlofIC.FinishCycle();
				return false;
			}
		//取IC卡号，日志编号以及物资权限
		var strIcNo;
		var strLogNo;
		var strWzNo;
		var dMoney;
		strIcNo = window.SlofIC.Icbh();
		strLogNo = window.SlofIC.Iclog();
		strWzNo = window.SlofIC.Wzqx();
		if (strIcNo == "")
			{
				alert("读取IC卡信息失败，请重新刷卡！");
				window.SlofIC.FinishCycle();
				return false;
			}		
		strIcNoForSave = strIcNo;
		dMoney = parseFloat(reverseFormatAmountString(totalMoney));
		if (!validCatalog(catalogNO, strWzNo))
			{
				alert("发料单物资大类不在IC卡所有大类之中！");
				//写日志
				window.SlofIC.WriteIcLog(strIcNo, strLogNo, dMoney, "发料单物资大类不在IC卡所有大类之中，放弃制发料单", '');
				window.SlofIC.FinishCycle();
				return false;
			}
		if (window.SlofIC.BalanceValidate("1", strIcNo, dMoney) < 0)
			{
				alert("金额验证不通过！");
				//写日志
				window.SlofIC.WriteIcLog(strIcNo, strLogNo, dMoney, "金额验证不通过，放弃制发料单", '');
				window.SlofIC.FinishCycle();
				return false;
			}
		//写日志
		window.SlofIC.WriteIcLog(strIcNo, strLogNo, dMoney, "发料单号:"+strOutboundID, strOutboundID);
		window.SlofIC.FinishCycle();
		return true;
	}
	
/**库房刷卡验证身份和金额
 @ param ipsGydw 供应单位
 @ param totalMoney 验证金额 is a object,need to convert to double
 @ param catalogNO 物资大类
 @ param strOutboundNO 发料单编号
 @ param strReceiveDate 发料日期
 @ param items 料单明细
 @ param oldIcNo 科室刷卡制单时的IC卡号，如果是直达，则为空
 @ param oriTime 跳转到页面的时间
 */
var ifSucessSettle = -1;
var strIcNoForSave = "";
var isBrushCard = 0; //控制在一次刷卡未结束（成功或失败）时不能再次刷卡
var strDllVesion = "1.0"; //dll的最新版本号，更新dll时要更新此版本号
function brushCardForDepot(ipsGydw, catalogNO, totalMoney, stroutboundNo, strReceiveDate, items, oldIcNo, oriTime)
	{
		isBrushCard++;
		//不能在一次刷卡未结束时再次刷卡
		if (isBrushCard > 1)
			{
				return false;
			}
		var ipsFile;//卡机配置文件
		ipsFile = "wsjs.ini";
		var currentTime;
		currentTime = new Date();
		if ((currentTime - oriTime)/(1000 * 60) > 5)//如果5分钟内没有操作页面，提示重新登陆
			{
				alert("与服务器会话超时，请重新登陆刷卡！");
				isBrushCard = 0;
				return false;
			}
		
		if (ipsGydw == "")
			{
				alert("单位编号不能为空！");
				isBrushCard = 0;
				return false;
			}
		if (stroutboundNo == "")
			{	
				alert("发料单号不能为空！");
				isBrushCard = 0;
				return false;
			}
		//提示用户输入密码
		if (!confirm("请确认已输入密码，并且卡已放好！"))
			{
				isBrushCard = 0;
				return false;
			}
		//读卡
		var nResult;
		nResult = parseInt(window.SlofIC.ReadIc(ipsFile, ipsGydw, "2", strDllVesion), 10);//标志2表示在库房刷卡
		//如果读卡失败
		if (nResult < 0)
			{
				window.SlofIC.FinishCycle();
				alert("读取IC卡信息失败，请重新刷卡！");
				isBrushCard = 0;
				return false;
			}
		//取IC卡号，日志编号以及物资权限
		var strIcNo;
		var strLogNo;
		var strWzNo;
		var dMoney;
		strIcNo = window.SlofIC.Icbh();
		strLogNo = window.SlofIC.Iclog();
		strWzNo = window.SlofIC.Wzqx();
		if (strIcNo == "")
			{
				alert("读取IC卡信息失败，请重新刷卡！");
				window.SlofIC.FinishCycle();
				isBrushCard = 0;
				return false;
			}
		
		//同科室刷卡时的IC卡号比较，必须是同一张卡
		if (oldIcNo != null && oldIcNo != 'null' && oldIcNo != '' && oldIcNo != 'undefined')
			{
				if (oldIcNo != strIcNo)
					{
						alert("该单据此前刷卡使用的IC卡卡号为" + oldIcNo + "，当前刷卡的卡号为" + strIcNo + "，必须使用同一张IC卡刷卡结算");
						window.SlofIC.FinishCycle();
						isBrushCard = 0;
						return false;
					}
			}
		strIcNoForSave = strIcNo;
		dMoney = parseFloat(reverseFormatAmountString(totalMoney));
		if (!validCatalog(catalogNO, strWzNo))
			{
				alert("发料单物资大类不在IC卡所有大类之中！");
				window.SlofIC.FinishCycle();
				isBrushCard = 0;
				return false;
			}
		if (window.SlofIC.BalanceValidate("2", strIcNo, dMoney) < 0)
			{
				alert("金额验证不通过！");
				window.SlofIC.FinishCycle();
				isBrushCard = 0;
				return false;
			}
				
		//开始事务
		window.SlofIC.BeginTrans();
		//保存料单信息
		if (window.SlofIC.SaveLlxx(stroutboundNo, ipsGydw, strReceiveDate, strIcNo, dMoney) < 0)
			{
				window.SlofIC.Rollback();
				window.SlofIC.FinishCycle();
				alert("保存料单信息失败！");
				isBrushCard = 0;
				return false;
			}		
		//保存明细
		var i;
		if (items != null)
			{
				for (i = 0;i < items.length;i++)
					{
						if (window.SlofIC.SaveLlmx(items[i]) < 0)
							{
								window.SlofIC.Rollback();
								window.SlofIC.FinishCycle();
								alert("保存料单明细失败");
								isBrushCard = 0;
								return false;
							}
					}
			}		
		//提交
	 	window.SlofIC.Commit();
		window.SlofIC.FinishCycle();
		//设置结算成功标志
		ifSucessSettle = 1;
		isBrushCard = 0;
		return true;
	}	
    
/**查询物资：默认选中所有明细
 *@param checkObj  复选框name
 *@Outhor  李应洪
 */    
function setButtonChecked(checkObj)
    {                                 
        if (checkObj == null)
        {
            return;
        }    
        if (isNaN(checkObj.length) && !checkObj.disabled)
        {
            checkObj.checked = true;
        }
        else
        {
            for (var i = 0; i  < checkObj.length; i++)
            {   
                if(!checkObj[i].disabled)
                {
                    checkObj[i].checked = true;
                }
            }
        }
    }      

/**系统中所有的审核功能点，将“待审核”的所有单据直接查询出俩，并第一项默认选中，审核后返回列表页面将该页面刷新，第一项仍默认选中
 *@param checkObj  复选框name
 *@Outhor  李应洪
 */    
function setRadioChecked(checkObj)
    {                                 
        if (checkObj == null)
        {
            return;
        }    
        if (isNaN(checkObj.length) && !checkObj.disabled)
        {
            checkObj.checked = true;
        }
        else
        {
            for (var i = 0; i  < checkObj.length; i++)
            {   
                if(!checkObj[i].disabled)
                {
                    checkObj[i].checked = true;
					break;
                }
            }
        }
    }   
/*
 *当鼠标指到某条数据时，高亮显示当前数据
 */
 var  initObjectBackground;
 function changeBackground(obj)
 {
 	//initObjectBackground=obj.style.background;
	obj.style.background='#ACD5FD'; 
    //#3A6EA5桌面颜色
    //#ffff66
 }
 //当鼠标离开某条数据时，恢复显示当前数据背景
 function reChangeBackground(obj)
 {
 	//obj.style.background=initObjectBackground;
	obj.style.background='#ffffff';
 }
   
 /*
  *add by jianwu
  *当定义了分层以后，决定是否显示分层内容
  *@param div1 div ID
  *@param relateDiv 相应需要调整大小的div ID
  *@param hiddenHeight 相应需要调整大小的div 隐藏div1时的高度
  *@param viewHeight 相应需要调整大小的div  不隐藏div1时的高度
  */ 
 function expands(div1,relateDiv,hiddenHeight,viewHeight) 
{
		var whichEl1=eval(div1);
		var relateDiv1=eval(relateDiv);
		if (whichEl1.style.display=="none")
		{	
			whichEl1.style.display="block";
			document.images["view"].src = ROOT_PATH+"/images/up.gif";
			if(relateDiv != "")
			relateDiv1.style.height=validWorkAreaHeight*hiddenHeight;
			getTheFirstFocus();
		}
		else{
			whichEl1.style.display="none";
			document.images["view"].src = ROOT_PATH+"/images/down.gif";
			if(relateDiv != "")
			relateDiv1.style.height=validWorkAreaHeight*viewHeight;
		}		
}	

   
 /*
  *add by jianwu
  *通过事件决定是否显示分层内容,与图片无关
  *@param div1 div ID
  *@param relateDiv 相应需要调整大小的div ID
  *@param hiddenHeight 相应需要调整大小的div 隐藏div1时的高度
  *@param viewHeight 相应需要调整大小的div  不隐藏div1时的高度
  */ 
 function hiddenDiv(div1,relateDiv,hiddenHeight,viewHeight) 
{
		var whichEl1=eval(div1);
		var relateDiv1=eval(relateDiv);
		if (whichEl1.style.display=="none")
		{	
			whichEl1.style.display="block";
			if(relateDiv != "")
			relateDiv1.style.height=validWorkAreaHeight*hiddenHeight;
		}
		else{
			whichEl1.style.display="none";			
			if(relateDiv != "")
			relateDiv1.style.height=validWorkAreaHeight*viewHeight;
		}
}	

/**
* add by jianwu 
*改变焦点所在行的背景色
*@ param obj <tr>处输入this
*@ param formName form名称 
*@ param trNum <tr>所在行数，如nColorIndex
*/
var lastNum = -1;
var lastObject = new Object();
var lastColor = "";
function keyboardChangeBackground(obj,trNum)
{
	if(lastNum == -1)
	{
		lastNum = trNum;
		lastObject = obj;	
		obj.style.background='#E7ECFA';			
	}  
	else
	{
		lastObject.style.background="#F8F8F8";			
		obj.style.background='#E7ECFA';				
		lastNum = trNum;
		lastObject = obj;		 
	}			
}	
	/**
	* add by jianwu 
	*拉动滚动条始终显示表头和栏目
	*@ param theParrentObj  分层id
	*@ param tblID  主表id	
	*@ param yID  栏目分层id
	*@ param xHeaderNum 表头行数
	*@ param yTdNum 栏目分层总ｔｄ数
	*/
var yDivMark = 0;	
function setPosition(theParrentObj,tblID,yID,xHeaderNum)
{		
		var yDivObj=eval(yID);
		var yTRs = yDivObj.getElementsByTagName('TR');	
		var parrentTable =eval(tblID);	
		var	 xTRs = parrentTable.getElementsByTagName('TR');
		var yTdNum;
	
		//显示列
	
		if(yDivMark == 0)
		{				
				//如果类信息为空，不再执行	
				
				if(yTRs.length  == 0)	
				{
					
					return;	
				}
				
				    yTdNum = yTRs[0].cells.length;
				
				//设置层高
				yDivObj.style.pixelHeight = parseFloat(parrentTable.offsetHeight) - parseFloat(yDivObj.style.top);	
								
				if(yDivObj.style.display=="none")
				{									
					yDivObj.style.display="block";			
					if(yTdNum > 0)			
					{
						//设置层宽
						var temp = 0;
						for(var k = 0; k < yTdNum; k++)
						{							
								 temp += parseFloat(xTRs[0].cells[k].offsetWidth);
						}											
						yDivObj.style.pixelWidth  = temp;						
						
						//设置单元格宽度
						for(var k = 0; k < yTdNum; k++)
						{			
									yTRs[0].cells[k].style.pixelWidth = xTRs[0].cells[k].offsetWidth;	
						}
						
						//设置单元格高度
						for(var m = 0; m < yTRs.length; m++)
						{				
							yTRs[m].style.pixelHeight= xTRs[m+xHeaderNum].offsetHeight;
						}													
											
					}												
				}						
				yDivObj.innerHTML += "<iframe src=\"javascript:false\" style=\"position:absolute;top:0px; left:0px; width:100%; height:"+yDivObj.style.pixelHeight+"; z-index:0; filter='progid:DXImageTransform.Microsoft.Alpha(opacity=0)';\"></iframe>";	
				yDivMark = 1;							
		}	
		else
		{
			if(yDivObj.style.display=="none")
			{
				yDivObj.style.display ="block";	
			}	
		}
		if(yDivObj.style.display=="block")
		{
			if(theParrentObj.scrollLeft <= 25)
			{
				yDivObj.style.display ="none";	
			}
			else
			{
				yDivObj.style.left =theParrentObj.scrollLeft;	
			}
		}
		
	}
	
/**
* Reference: Sandeep V. Tamhankar (stamhankar@hotmail.com),
* http://javascript.internet.com
*/
	function checkEmail(emailStr) 
	{
		if (emailStr.length == 0) 
		{
			return true;
		}
		var emailPat=/^(.+)@(.+)$/;
		var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
		var validChars="\[^\\s" + specialChars + "\]";
		var quotedUser="(\"[^\"]*\")";
		var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
		var atom=validChars + '+';
		var word="(" + atom + "|" + quotedUser + ")";
		var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
		var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
		var matchArray=emailStr.match(emailPat);
		if (matchArray == null) 
		{
			return false;
		}
		var user=matchArray[1];
		var domain=matchArray[2];
		if (user.match(userPat) == null)
		{
			return false;
		}
		var IPArray = domain.match(ipDomainPat);
		if (IPArray != null) 
		{
			for (var i = 1; i <= 4; i++) 
			{
				if (IPArray[i] > 255) 
				{
					return false;
				}
			}
			return true;
		}
		var domainArray=domain.match(domainPat);
		if (domainArray == null) 
		{
			return false;
		}
		var atomPat=new RegExp(atom,"g");
		var domArr=domain.match(atomPat);
		var len=domArr.length;
		if ((domArr[domArr.length-1].length < 2) ||(domArr[domArr.length-1].length > 3)) 
		{
			return false;
		}
		if (len < 2) 
		{
			return false;
		}
		return true;
	}
//trim	
String.prototype.trim = function()
{
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}	

//对字符串进行处理
//oralString 传入的字符串
function dealString(oralString)
{
	return oralString.replace("\"","\\\"");
}


/**************************************************************************************
 * 供求关系视图页面专用js
 * 
 * @param thisObj
 * @param strFormName
 * @param strControlBoxName
 * @param strSequenceBoxName
 * 
 * @Author  bingli@isoftstone.com
 * @Date    2004-12-20
 */
function doViewBoxControl(thisObj, strFormName, strControlBoxName, strSequenceBoxName)
{
    eval("var controlBox = document." + strFormName + ".elements['" + strControlBoxName + "'];");
    eval("var sequenceBox = document." + strFormName + ".elements['" + strSequenceBoxName + "'];");
    
    var strThisValue = thisObj.value;
    var bIsChecked = thisObj.checked;
    if(isNaN(sequenceBox.length))
    {
        var strSequenceValue = sequenceBox.value;
        var strPrefix = strSequenceValue.split('_')[0];
        if(strPrefix == strThisValue)
        {
            sequenceBox.checked = bIsChecked;
        }
    }
    else
    {
        for(var i = 0; i < sequenceBox.length; i++)
        {
            var strSequenceValue = sequenceBox[i].value;
            var strPrefix = strSequenceValue.split('_')[0];
            if(strPrefix == strThisValue)
            {
                sequenceBox[i].checked = bIsChecked;
            }
        }
    }
}
function viewBoxChange(thisObj, strFormName, strControlBoxName, strSequenceBoxName)
{
    eval("var controlBox = document." + strFormName + ".elements['" + strControlBoxName + "'];");
    eval("var sequenceBox = document." + strFormName + ".elements['" + strSequenceBoxName + "'];");
    
    var strThisValue = thisObj.value;
    var strPrefix = strThisValue.split('_')[0];
    var bIsChecked = thisObj.checked;
    if(bIsChecked)
    {
        if(isNaN(controlBox.length))
        {
            if(controlBox.value == strPrefix)
            {
                controlBox.checked = true;
            }
        }
        else
        {
            for(var i = 0; i < controlBox.length; i++)
            {
                if(controlBox[i].value == strPrefix)
                {
                    controlBox[i].checked = true;
                }
            }
        }
    }
    else
    {
        var bIsControlChecked = false;
        if(isNaN(sequenceBox.length))
        {
            bIsControlChecked = false;
        }
        else
        {
            for(var i = 0; i < sequenceBox.length; i++)
            {
                if(sequenceBox[i].value.split('_')[0] == strPrefix && sequenceBox[i].checked)
                {
                    bIsControlChecked = true;
                    break;
                }
            }
        }
        if(isNaN(controlBox.length))
        {
            if(controlBox.value == strPrefix)
            {
                controlBox.checked = bIsControlChecked;
            }
        }
        else
        {
            for(var i = 0; i < controlBox.length; i++)
            {
                if(controlBox[i].value == strPrefix)
                {
                    controlBox[i].checked = bIsControlChecked;
                }
            }
        }
    }
}

function imgChange(imgObj)
{
	
	var theRate = imgObj.height/imgObj.width;
	if(theRate>1)
	{
		alert(1);
		imgObj.height = 450;
		imgObj.width = imgObj.width/theRate;
	}
	else
	{
		imgObj.width = 450;
		imgObj.height = imgObj.height*theRate;
	}

}


/**设置快捷键*/
var keyArray = new Array();
var objArray = new Array();
function addArray(key,objName)
{
	 var nLength = keyArray.length;
	 keyArray[nLength] = key;
	 objArray[nLength] = objName;
}


function trigEasyCode()
{		
	if(keyArray == null || objArray == null || keyArray.length !=objArray.length)
	{
			return;
	}
	for(var i = 0; i < keyArray.length; i++)
	{
		if(event.keyCode==keyArray[i]&&event.altKey)
		{
			if(isNaN(eval("document.forms[0].elements[\'"+objArray[i]+"\']").length)||eval("document.forms[0].elements[\'"+objArray[i]+"\']").type=="select-one")
			{
                if(eval("document.forms[0].elements[\'"+objArray[i]+"\']") != null
                && eval("document.forms[0].elements[\'"+objArray[i]+"\']") != undefined)
                {
				    eval("document.forms[0].elements[\'"+objArray[i]+"\']").focus();
			    }
            }
			else
			{
                if(eval("document.forms[0].elements[\'"+objArray[i]+"\']") != null
                && eval("document.forms[0].elements[\'"+objArray[i]+"\']") != undefined)
                {
					eval("document.forms[0].elements[\'"+objArray[i]+"\']")[0].focus();
                }
			}
			return;	
		}
	}	
}  

function escOut()
{
	if(event.keyCode == 27)	
		window.close();
}

	//add:feiluo
	//按整数位算出最大的值
	function getIntDigit(intDigit)
	{
		var intValue=0;
		if(intDigit==5)
		{
			intValue=99999;
		}
		if(intDigit==6)
		{
			intValue=999999;
		}
		if(intDigit==7)
		{
			intValue=9999999;
		}
		if(intDigit==8)
		{
			intValue=99999999;
		}
		if(intDigit==9)
		{
			intValue=999999999;
		}
		if(intDigit==10)
		{
			intValue=9999999999;
		}		
		return intValue;		
	}
	
/*create:feiluo 2005-10-24
*校验是否是整数
*intDigit：整数位数*
*/
	function IsNumeric(obj,intDigit)
	{
			var str="0123456789";
			var maxNum=getIntDigit(intDigit);			
			var txt=obj.value;
			if(txt==null||txt=="")
			{
				obj.value="0";
				return obj.value;
			}
			if(txt<=0)
			{
				obj.value="0";
				return obj.value;
			}
	 		//if(txt!=""&&txt!=null)
	 		else
	 		{  		
		 		for(i=0;i<txt.length;i++)
		 		{
		 			var strdata=txt.charAt(i);
		 			if(str.indexOf(strdata)==-1)
		 			{	 		
		 				obj.value="0";
		 				return obj.value;
		 			}
		 		}
		 		//如果整数前面有0存在
		 		if(txt.charAt(0)<=0)
	 			{	 				
		 			for(k=0;k<txt.length;k++)
		 			{		 					 				
			 			if(txt.charAt(k)<=0)
			 			{
			 				newtxt1=txt.substring(k+1,txt.length);			 				
			 			}
			 			else
			 			{
			 				obj.value=newtxt1;			 				
			 				break;
			 			}
			 		}
			 	}
			 	else
			 	{
			 		obj.value=txt;
			 	}		 		
	 			if(obj.value>maxNum)
	 			{
	 				obj.value="0";	 				
	 			}	 			
	 			return obj.value;
	 		}
		 return obj.value;
	}
 /*create:feiluo 2005-10-24
 *intDigit:整数位数
 *pointDight:小数位数
 *校验是否是小数，如果是整数则保留两位小数
 *如果是不合法的字符则为0.00
 */
 function isNumericVSPoint(obj,intDigit)
 { 		
 		var str="0123456789.";
 		var maxNum=getIntDigit(intDigit)+".99"; 		
 		var txt=obj.value; 		
 		if(txt<=0)
		{
				obj.value="0.00";
				return obj.value;
		}
 		if(txt!=""&&txt!=null)
 		{  		
	 		for(i=0;i<txt.length;i++)
	 		{
	 			var strdata=txt.charAt(i);
	 			if(str.indexOf(strdata)==-1)
	 			{	 		
	 				obj.value="0.00";
	 				return obj.value;
	 			}
	 		}
	 		//是否是小数
	 		if(txt.indexOf(".")>-1)
	 		{	 			
	 			if(txt.indexOf(".")==0||(txt.lastIndexOf(".")>txt.indexOf(".")))
	 			{ 				
	 				obj.value="0.00";
	 				return obj.value;
	 			}
	 			inx=txt.indexOf(".");
	 			if((txt.length-1)==inx)
	 			{
	 				txt=txt+"00";
	 			}	 			
	 			//小数点前整数
	 			inttxt=txt.substring(0,inx);	 		
	 			//小数点后整数	 		
	 			pointtxt=txt.substring(inx+1,txt.length);	
	 			//判断小数点前整数是否为合法数.比如00123等
	 			if(inttxt.charAt(0)<=0)
	 			{
	 				var newinttxt="";
	 				for(j=0;j<inttxt.length;j++)
		 			{		 						 				
		 				if(inttxt.charAt(j)<=0)
		 				{
		 					newinttxt=inttxt.substring(j+1,inttxt.length);
		 				}
		 				else
		 				{
		 					inttxt=newinttxt;
		 					break;
		 				}
		 			}
	 			}
	 			if(pointtxt.length==1)
	 			{	 			
	 				pointtxt=pointtxt+"0";
	 			}
	 			obj.value=inttxt+"."+pointtxt;
	 			//四舍五入保留两位
		 		obj.value=getRoundAmount(obj.value,2);		 		
	 			if(parseFloat(obj.value,2)>maxNum)
	 			{ 				 				
	 					obj.value="0.00";
	 					return obj.value;
	 			}
	 		}
	 		else
	 		{	 		
	 			var newtxt1="";	 		
	 			if(txt.charAt(0)<=0)
	 			{	 				
		 			for(k=0;k<txt.length;k++)
		 			{		 					 				
			 			if(txt.charAt(k)<=0)
			 			{
			 				newtxt1=txt.substring(k+1,txt.length);			 				
			 			}
			 			else
			 			{
			 				obj.value=newtxt1+".00";
			 				break;
			 			}
			 		}
			 	}
			 	else
			 	{
			 		obj.value=txt+".00";
			 	}			 
	 			if(parseFloat(obj.value)>maxNum)
	 			{
	 				obj.value="0.00";	 				
	 			}	 			
	 			return obj.value;
	 		}
	 	}
	 	else
	 	{	 		
	 		obj.value="0.00";
	 	}	 		 	
 		return obj.value;
}