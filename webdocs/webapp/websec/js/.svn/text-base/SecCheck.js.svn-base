/**
 * 文件名：SecCheck.js
 * 客户端校验 
 * author:Forest
 * date:2003-09-17
 * 包含的校验函数：
 * 1,checkIsSelect 校验放大镜的数据是否是选择
 * 2,validateFields 校验页面所有元素（必输项是否输入，类型是否匹配）
 * 3,isMatch 校验放大镜是否匹配（比如，客户和帐户是否匹配）
 * 4,checkInterestExecuteDate 检查起息日和执行日
 * 5,compareDate 比较两个日期大小
 * 6,isInt 校验是否Int整型类型
 * 7,isDate 校验是否是日期
 * 8,isEmail 校验是否电邮地址
 * 9,isFloat 校验是否Float类型
 * 10,isFax 校验是否传真(电话)号码
 * 11,reverseFormatAmount 反向格式化金额(将所有的","去掉)
 * 12,submitForm 提交表单
 * 13,isPostalcode 校验是否邮政编码
 * 14,addDate 增加定期存款期限(定期开立专用)
 * 15,round	得到数值的指定小数位的四舍五入值
 * 16,openWindow 放大镜弹出方法
 * 17,addDate 得到以天为单位,n天后的日期
 * 18,公式校验结果(方法组)
 *			1).setPrecision(precis)		设置校验精度,由于javascript的精度问题,可以设置某一精度,只要校验结果在精度
 *										范围内,就认为校验通过,默认精度为 -0.01 ~ 0.01
 *
 * 			2).addFormula(key,formula) 	添加公式,key为公式代码,用来唯一标示公式,
 *										公式格式:"args"代表公式中的各个值,例如: "args*args+(args/args-args)"
 *										默认公式有加减乘除四项,分别由"+","-","*","/"作为代码.
 *
 *			3).alidateFormula() 		公式校验,第一个参数为公式代码,然后加入公式中的各个值,最后一个值为结果,
 *										如果按照公式计算出来的结果和传入的结果相等,返回true,否则返回false
 */


/**
 * 提交表单
 * @param form 表单域
 */
function submitForm(form)
{
	showsending();
	form.submit();
} 
/**
 * 增加定期存款期限(定期开立专用)
 * strInputDate 日期
 * lMonth 月数
 */
function addDate(strInputDate,lMonth)
{
	var temp,s;
		temp=new String(strInputDate);
		s=new String("");
	
		for(var i=0;i<=temp.length-1;i++)
		{
 			if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
		{
  				s=s+"/";
		}	
  		else
 			{
  				if(isNaN(Number(temp.charAt(i))))
  				{
   				alert("请输入正确的开始日期");
    			form.strStartDate.focus();
  					return false;
			}    
  				else
			{
				s=s+temp.charAt(i);
			}
		}	
 		}

	dtDate = new Date(s);
	var strDate;
	var dtDay;
	var dtMonth;
	var dtYear;
	var temp;
	
	if (parseInt(lMonth) < 10000)
	{						     
		dtMonth = parseInt(lMonth) + dtDate.getMonth();	
		var dtMonthTemp = parseInt(lMonth) + dtDate.getMonth()+1;	
		
		dtDay = dtDate.getDate();			
		if(dtMonthTemp == 4||dtMonthTemp ==6||dtMonthTemp ==9||dtMonthTemp==11)
		{
		   if(dtDay == 31)
		     dtDate.setDate(30);
		}
		else if(dtMonthTemp == 2)
		{
		  if(dtDay == 30||dtDay == 31 || dtDay == 29)
		  {
		     if(dtDate.getYear()%4 ==0)
			    dtDate.setDate(29);
			 else
			    dtDate.setDate(28);
		  }
		}	
		dtDate.setMonth(dtMonth);
	}
	else
	{		   
		lMonth = parseInt(lMonth) - 10000;
		dtDay = parseInt(lMonth) + dtDate.getDate();
		dtDate.setDate(dtDay);
		
		dtMonth = dtDate.getMonth() + 1;
		dtDate.setMonth(dtMonth);
		dtMonth = dtDate.getMonth();
		dtDay = dtDate.getDate();			
		if(dtMonth == 4||dtMonth ==6||dtMonth ==9||dtMonth==11)
		{
		   if(dtDay == 31)
		     dtDate.setDate(30);
		}
		if(dtMonth == 2)
		{
		  if(dtDay == 30||dtDay == 31 || dtDay ==29)
		  {
		     if(dtDate.getYear()%4 ==0)
			    dtDate.setDate(29);
			 else
			    dtDate.setDate(28);
		  }
		}			
	}
	
	dtDay = dtDate.getDate();
	dtMonth = dtDate.getMonth()+1;
	dtYear = dtDate.getYear();
	if (dtMonth == 0)
	{
		dtYear--;
		dtMonth = 12;
	}
	
	eval("strDate='"+ dtYear + "-" +dtMonth + "-" + dtDay + "'");
	return strDate;
}
/**
 * 反向格式化金额(将所有的","去掉)
 * @param strData 需要格式化的数据
 * @return 返回反格式化的金额
 */ 
function reverseFormatAmount(strData)
{
	var i,strTemp;
	strData = fntrimzero(strData);
	//去掉所有的"," 
	strTemp=new String(strData);
	strData="";
	for(i=0;i<strTemp.length;i++)
	{
		var cData;
		cData=strTemp.charAt(i);
		if (cData!=",")
		{
			strData=strData+cData;
		}
	}
	return strData;
}

/**
 * 校验放大镜的数据是否是选择
 * @param form 表单域
 * @param sIDControl 放大镜隐藏的标识值对应的控件
 * @param sValueControl 放大镜主要控件的值对应的控件
 * @param sMagnifierDesc 放大镜描述
 * @param nIsNotNull 是否必输项：1，必输；0，非必输
 * Return: 是，返回true; 否，返回false.
 */
function checkIsSelect(form,sIDControl,sValueControl,sMagnifierDesc,nIsNotNull)
{
	var strFormName =  form.name;
	var nID;
	var sValue;
	eval("nID = " + strFormName + "." + sIDControl + ".value");
	eval("sValue = " + strFormName + "." + sValueControl + ".value");
	
	if (isInt(nID))
	{
		//alert("请输入有效的放大镜标识值");
		if (sValue != "" || sValue.length > 0)
		{
			if (nID <= 0)
			{
				alert(sMagnifierDesc+"必须从放大镜中选择！");
				return false;
			}
		}
		else if (nIsNotNull == 1)
		{
			if (nID <= 0)
			{
				alert(sMagnifierDesc+"必须从放大镜中选择！");
				return false;
			}
		}
	}
	else
	{
		if (sValue != "" || sValue.length > 0)
		{
			if (nID == "" || nID.length <= 0)
			{
				alert(sMagnifierDesc+"必须从放大镜中选择！");
				return false;
			}
		}
		else if (nIsNotNull == 1)
		{
			if (nID == "" || nID.length <= 0)
			{
				alert(sMagnifierDesc+"必须从放大镜中选择！");
				return false;
			}
		}
	}
	return true;
}

/**
 * 校验必输项是否输入,格式是否正确
 * 数据类型：
 *	date, 日期，格式2003-11-05
 *	money, 大于零的金额
 *	int, 
 *	string，
 *	magnifier，
 *	email，
 *	fax，
 *	phone,
 *	postalcode,
 *	list,
 *	rate,
 *	allmoney, 可以大于零，小于零，等于零的金额
 * 
 * allField格式：this.aa = new Array("txtDate","成交日期","date",1);
 * allFields参数："txtDate" -- 表单域名称
 *               "成交日期"-- 表单域描述
 *               "date"    -- 数据类型
 *               1         -- 是否必输项（1，是；0，否）
 * 例：
 *     
 * function allFields()
 * {
 *		this.aa = new Array("txtBankNo","银行编号","string",1);
 *		this.ab = new Array("txtDate","录入日期：","date",0);
 *		this.ac = new Array("txtAmount","金额","money",1);
 *		this.ad = new Array("txtNumber","数量","int",1);
 * }   
 */ 
function validateFields(form) 
{
	var bValid = true;
	var focusField = null;
	var i = 0;
	var bValidOnce=false;
	var fields = new Array();
	oAllFields = new allFields();
	for (x in oAllFields) 
	{
		bValidOnce=false;
	 	var bIsFormControl = false;
		if (form[oAllFields[x][0]].type == 'text' ||
				form[oAllFields[x][0]].type == 'textarea' ||
				form[oAllFields[x][0]].type == 'select-one' ||
				form[oAllFields[x][0]].type == 'radio' ||
				form[oAllFields[x][0]].type == 'password' ||
				form[oAllFields[x][0]].type == 'hidden')
		{
			bIsFormControl = true;
		} 
		
		//alert (form[oAllFields[x][0]]);
		//alert (oAllFields[x][1]);
		//alert (oAllFields[x][2]);
		//alert (oAllFields[x][3]);
		String.prototype.trim= function()  
		{  
		    // 用正则表达式将前后空格  
		    // 用空字符串替代。  
		    return this.replace(/(^\s*)|(\s*$)/g, "");  
		}
		
		if (bIsFormControl = true){			
			//alert("-"+form[oAllFields[x][0]].value+"-");
			form[oAllFields[x][0]].value=form[oAllFields[x][0]].value.trim();
			//alert("-"+form[oAllFields[x][0]].value+"-");
		}
		
		//判断是否允许为空
		if ((bIsFormControl == true && oAllFields[x][3] == 1 && form[oAllFields[x][0]].value == '') ||
			(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "list" && form[oAllFields[x][0]].value <= 0))
		{
			//alert (oAllFields[x][3]);
			if (i == 0) 
			{
				focusField = form[oAllFields[x][0]];
			}
			fields[i++] = oAllFields[x][1]+"不能为空！";
			bValid = false;
			bValidOnce=true;
		}
		
		//如果是放大镜，则判断是否是从放大镜中选择的数据
		if (bIsFormControl == true && oAllFields[x][3] == 'magnifier' && bValidOnce==false) 
		{
			if (form[oAllFields[x][1]].value == '' || form[oAllFields[x][1]].value<0 || form[oAllFields[x][0]].value == ''){
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][2]+"的数据，请从放大镜中选择！";
				bValid = false;
			}
		}
		
		//如果是日期，则判断是否是正确的日期格式
		if (bIsFormControl == true && oAllFields[x][2] == 'date' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isDate(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"格式不正确，请重新录入！";
				bValid = false;			
			}
		}
		//如果是数字类型，则判断是否正确的数字类型;
		if (bIsFormControl == true && oAllFields[x][2] == 'int' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			
			//判断是否正确的数字类型
			if (!isInt(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为整数，请重新录入！";
				bValid = false;			
			}
			else
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				if(parseInt(form[oAllFields[x][0]].value)<=0){
					fields[i++] = oAllFields[x][1]+"必须大于0，请重新录入！";
					bValid = false;			
				}
				else if (parseInt(form[oAllFields[x][0]].value)>99999999999)
				{
					fields[i++] = oAllFields[x][1]+"值过大，请重新录入！";
					bValid = false;			
				}
			}
		}
		//如果是金额类型，则判断是否正确的金额类型
		if (bIsFormControl == true && oAllFields[x][2] == 'money'  && bValidOnce==false ) 
		{
			if (form[oAllFields[x][0]].value != '' && !isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为数字，请重新录入！";
				bValid = false;			
			}
			else
			{
				if(oAllFields[x][3] == 1)
				{
					if(form[oAllFields[x][0]].value <= 0)
					{
						if (i == 0) 
						{
							focusField = form[oAllFields[x][0]];
						}
						fields[i++] = oAllFields[x][1]+"不能小于等于零，请重新录入！";
						bValid = false;	
					}
				}
				else
				{	
					if(form[oAllFields[x][0]].value < 0)
					{
						if (i == 0) 
						{
							focusField = form[oAllFields[x][0]];
						}
						fields[i++] = oAllFields[x][1]+"不能小于零，请重新录入！";
						bValid = false;	
					}
				}

			}
		}
		//如果是金额类型，则判断是否正确的金额类型
		if (bIsFormControl == true && oAllFields[x][2] == 'allmoney' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为数字，请重新录入！";
				bValid = false;			
			}
		}		
		//如果是利率类型，则判断是否正确的利率类型
		if (bIsFormControl == true && oAllFields[x][2] == 'rate' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为数字，请重新录入！";
				bValid = false;			
			}
			else if(form[oAllFields[x][0]].value < 0)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"不能小于零，请重新录入！";
				bValid = false;		
			}
			else if(parseFloat(reverseFormatAmount(form[oAllFields[x][0]].value))>999)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"值过大，请重新录入！";
				bValid = false;	
			}	
		}			
		//如果是email类型，则判断是否正确的email类型
		if (bIsFormControl == true && oAllFields[x][2] == 'email' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isEmail(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为Email格式，请重新录入！";
				bValid = false;			
			}
		}
		//如果是fax类型，则判断是否正确的fax类型
		if (bIsFormControl == true && oAllFields[x][2] == 'fax' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为Fax格式，请重新录入！";
				bValid = false;			
			}
		}	
		//如果是phone类型，则判断是否正确的phone类型
		if (bIsFormControl == true && oAllFields[x][2] == 'phone' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为有效的电话号码，请重新录入！";
				bValid = false;			
			}
		}
		//如果是postalcode类型，则判断是否正确的邮政编码类型
		if (bIsFormControl == true && oAllFields[x][2] == 'postalcode' && form[oAllFields[x][0]].value != '' && bValidOnce==false) 
		{
			if (!isPostalcode(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为有效的邮政编码，请重新录入！";
				bValid = false;			
			}
		}
	}
	if (fields.length > 0) 
	{
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}


/**
 * 校验放大镜的数据是否匹配
 * @param nFirstID 第一个放大镜ID元素的名字
 * @param nSecondID 第二个放大镜ID元素的名字
 * @param sFirstDesc 第一个放大镜的描述
 * @param sSecondDesc 第二个放大镜的描述
 * Return: 是，返回true; 否，返回false.
 */
function isMatch(nFirstID,nSecondID,sFirstDesc,sSecondDesc)
{
	var isMatched 		= false;
	var firstValue 		= eval("document.all."+nFirstID+".value");
	var secondValue 	= eval("document.all."+nSecondID+".value");
	var checkValue		= null;					//校验值
	
	var checkElementA 	= eval("document.all."+nFirstID+nSecondID);
	var checkElementB 	= eval("document.all."+nSecondID+nFirstID);
	
	if (checkElementA == null && checkElementB == null) {
		alert("传入的校验域关联错误!");
		return false;
	}
	
	if (checkElementA != null){
		checkValue = checkElementA.value.split(",");
		
		for (var n=0;n<checkValue.length;n++){
			if (firstValue == checkValue[n]){
				isMatched = true;
				break;
			}
		}
		if (!isMatched){
			eval("document.all."+nSecondID+".focus()");
		}
	}
	else if (checkElementB != null){
		checkValue = checkElementB.value.split(",");
		for (var n=0;n<checkValue.length;n++){
			if (secondValue == checkValue[n]){
				isMatched = true;
				break;
			}
		}
		if (!isMatched){
			eval("document.all."+nFirstID+".focus()");
		}
	}
	
	if (!isMatched){
		alert(sFirstDesc+" 和 "+sSecondDesc+"放大镜 不匹配!");
	}
	
	return isMatched;
}


/**
 * 检查起息日和执行日
 * @param d_interest 起息日域
 * @param d_execute 执行日域
 * @param strSystemDate 系统日期
 * @param nType 类型：1，暂存时使用；其它，保存时使用。
 */

function checkInterestExecuteDate(d_interest,d_execute,strSystemDate,nType)
{
	var nDays = 7;

	if(!isDate(d_interest.value))
	{
		alert("请填写正确的起息日。");
		try
		{
			d_interest.focus();
		}
		catch (e)
		{}
		return false;
	}
	if(d_execute.value!="")
	{
		if(!isDate(d_execute.value))
		{
			alert("请填写正确的执行日。");
			try
			{
				d_execute.focus();
			}
			catch (e)
			{}
			return false;
		}
		else
		{
			var dtInterest,dtExecute,dtSystemDate;
			var temp,s;
			temp=new String(d_interest.value);
			if(chkdate(temp)==0)
			{
				alert("请输入正确的起息日。");
				try
				{
					d_interest.focus();
				}
				catch (e)
				{}
				return false;
			}
			s=new String("");
			for(var i=0;i<=temp.length-1;i++)
			{
				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
				{
					s=s+"/";
				}
				else
				{
					if(isNaN(Number(temp.charAt(i))))
					{
						alert("请输入正确的起息日。");
						try
						{
							d_interest.focus();
						}
						catch (e)
						{}						
						return false;
					}				
					else
					{
						s=s+temp.charAt(i);
					}
				}
			}
			dtOne=new Date(s);
			if(dtOne.toString()=="NaN")
			{
				alert("请输入正确的起息日");
				try
				{
					d_interest.focus();
				}
				catch (e)
				{}		
				return false;
			}

			temp=new String(d_execute.value);
			if(chkdate(temp)==0)
			{
				alert("请输入正确的执行日。");
				try
				{
					d_execute.focus();
				}
				catch (e)
				{}
				return false;
			}
			s=new String("");
			for(var i=0;i<=temp.length-1;i++)
			{
				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
				{
					s=s+"/";
				}
				else
				{
					if(isNaN(Number(temp.charAt(i))))
					{
						alert("请输入正确的执行日");
						try
						{
							d_execute.focus();
						}
						catch (e)
						{}
						return false;
					}				
					else
					{
						s=s+temp.charAt(i);
					}
				}
			}
			dtTwo=new Date(s);
			if(dtTwo.toString()=="NaN")
			{
				alert("请输入正确的执行日。");
				try
				{
					d_execute.focus();
				}
				catch (e)
				{}
				return false;
			}
			dtTwo=new Date(s);
			
			if (nType==1)//暂存
			{
				if(dtOne.valueOf()>dtTwo.valueOf())
				{
				    if(!confirm("起息日大于执行日，是否继续？"))
				    {
						try
						{
							d_execute.focus();
						}
						catch (e)
						{}		
				    	return false;
				    }
				}
			}
			else
			{
				if(dtOne.valueOf()>dtTwo.valueOf())
				{
					alert("起息日不能大于执行日。");
					try
					{
						d_execute.focus();
					}
					catch (e)
					{}
					return false;
				}
			}
			temp=new String(strSystemDate);
			if(chkdate(temp)==0)
			{
				alert("请输入正确的系统日期。");
				return false;
			}
			s=new String("");
			for(var i=0;i<=temp.length-1;i++)
			{
				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
				{
					s=s+"/";
				}
				else
				{
					if(isNaN(Number(temp.charAt(i))))
					{
						alert("请输入正确的系统日期。");
						return false;
					}				
					else
					{
						s=s+temp.charAt(i);
					}
				}
			}
			dtSystemDate=new Date(s);
			if(dtSystemDate.toString()=="NaN")
			{
				alert("请输入正确的系统日期。");
				return false;
			}
			dtSystemDate=new Date(s);
			if (nType==1)//暂存
			{
				if (dtSystemDate.valueOf()>dtTwo.valueOf())
				{
					alert("执行日不能在今天之前。");
					try
					{
						d_execute.focus();
					}
					catch (e)
					{}
					return false;
				}
			}
			else
			{
				if(dtSystemDate.valueOf()!=dtTwo.valueOf())
				{
					alert("执行日必须是今天才可以保存。");
					try
					{
						d_execute.focus();
					}
					catch (e)
					{}		
					return false;	
				} 
			    if (nDays==null)
				 {
				    nDays=7;
				 }
				 else if(nDays=="")
				 {
				    nDays=7;

				 }	
			   if((dtTwo.valueOf()-dtOne.valueOf())/86400000>=nDays)
			   {
				    if(!confirm("起息日与执行日相差超过"+nDays+"天，是否继续？"))
				    {
						try
						{
							d_execute.focus();
						}
						catch (e)
						{}		
				    	return false;
				    }
			   }
			}
		}
	}
	else
	{
		alert("请填写正确的执行日。");
		try
		{
			d_execute.focus();
		}
		catch (e)
		{}		
		return false;
	}
	return true; 
}
/**
* 校验是否是日期
* 日期格式为“2003-09-15”
* Return true,false
*/
function isDate(d_date)
{		
	var temp,s;
	temp=d_date;
	s=new String("");
	for(var i=0;i<=temp.length-1;i++)
	{
		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
		{
			s=s+"/";
		}
		else
		{
			if(isNaN(Number(temp.charAt(i))))
			{
				return false;
			}				
			else
			{
				s=s+temp.charAt(i);
			}
		}
	}
	dtOne=new Date(s);
	if(dtOne.toString()=="NaN")
	{
		return false;
	}
	return true;
}

/**
* 校验是否电邮地址
* 
*/
function isEmail( d_email)
{		
	var checkStr = d_email;
	var emailtag = false;
	var emaildot=0
	var emailat=0
	if (checkStr.length<7) return (false);
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		if (ch=='@') emailat++;	
		if (ch=='.') emaildot++;	
	}				
	if (( emailat==1 ) ) 
	{
		emailtag = true;
	}
	return (emailtag);  	
}

/**
* 校验是否传真(或电话)号码
*
*/
function isFax( d_int)
{
	var checkOK = "0123456789 -()";
	var checkStr = d_int;
	var allValid = true;
	var decPoints = 0;
	var allNum = "";
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		for (j = 0;  j < checkOK.length;  j++)
		if (ch == checkOK.charAt(j))
		break;
		if (j == checkOK.length)
		{
			allValid = false;
			break;
		}
		if (ch != ",")
		{
			allNum += ch;
		}
	}
	return (allValid)
}

/**
* 校验是否邮政编号
*
*/
function isPostalcode( d_int)
{
	var checkOK = "0123456789";
	var checkStr = d_int;
	var allValid = true;
	var decPoints = 0;
	var allNum = "";
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		for (j = 0;  j < checkOK.length;  j++)
		if (ch == checkOK.charAt(j))
		break;
		if (j == checkOK.length)
		{
			allValid = false;
			break;
		}
		if (ch != ",")
		{
			allNum += ch;
		}
	}
	if (checkStr.length != 6)
	{
		allValid = false;
	}
	return (allValid)
}

/**
* 校验是否Float类型
* 
*/
function isFloat( d_float)
{
	var checkOK = "0123456789,.-";
	var checkStr = d_float;
	var allValid = true;
	var decPoints = 0;
	var allNum = "";
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		for (j = 0;  j < checkOK.length;  j++)
		if (ch == checkOK.charAt(j))
		{
			break;
		}
		if (j == checkOK.length)
		{
			allValid = false;
			break;
		}
		if ( (ch == '-') && (i!=0) )			
		{
			allValid = false;
			break;
		}			
		if (ch != ",")
		{
			allNum += ch;				
		}
		if (ch == ".")
		{
			decPoints += 1;				
		}	
	}			
	if ( decPoints > 1 )
	{
		allValid = false;
	}
	return (allValid)
}

/**
* 校验是否Int整型类型
*
*/
function isInt( d_int)
{
	var checkOK = "0123456789,-";
	var checkStr = d_int;
	var allValid = true;
	var decPoints = 0;
	var allNum = "";
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		for (j = 0;  j < checkOK.length;  j++)
		{
			if (ch == checkOK.charAt(j))
			{
				break;
			}
		}
		if (j == checkOK.length)
		{
			allValid = false;
			break;
		}
		if (ch != ",")
		{
			allNum += ch;
		}
	}
	return (allValid)
}

/**
 * 比较两个日期大小
 * 日期格式：2003-09-18 或 2003/09/18
 * 如果dtStart>dtEnd，返回false,否则返回true
 */
function compareDate(dtStart,dtEnd)
{
	var temp,s;
	temp=new String(dtStart);
	s=new String("");
	for(var i=0;i<=temp.length-1;i++)
	{
		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
		{
			s=s+"/";
		}
		else
		{
			s=s+temp.charAt(i);
		}
	}
	dtOne=new Date(s);
	temp=new String(dtEnd);
	s=new String("");
	for(var i=0;i<=temp.length-1;i++)
	{
		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
		{
			s=s+"/";
		}
		else
		{
			s=s+temp.charAt(i);
		}
	}
	dtTwo=new Date(s);
	if(dtOne.valueOf()>dtTwo.valueOf())
	{
		return false;
	}
	return true;	
}

//函数名：chkdate

//功能介绍：检查是否为日期

//参数说明：要检查的字符串

//返回值：0：不是日期  1：是日期

function chkdate(datestr)

{

	var lthdatestr

	if (datestr != "")

		lthdatestr= datestr.length ;

	else

		lthdatestr=0;

		

	var tmpy="";

	var tmpm="";

	var tmpd="";

	//var datestr;

	var status;

	status=0;

	if ( lthdatestr== 0)

		return 0



	

	for (i=0;i<lthdatestr;i++)

	{	if (datestr.charAt(i)== '-')

		{

			status++;

		}

		if (status>2)

		{

			//alert("Invalid format of date!");

			return 0;

		}

		if ((status==0) && (datestr.charAt(i)!='-'))

		{

			tmpy=tmpy+datestr.charAt(i)

		}

		if ((status==1) && (datestr.charAt(i)!='-'))

		{

			tmpm=tmpm+datestr.charAt(i)

		}

		if ((status==2) && (datestr.charAt(i)!='-'))

		{

			tmpd=tmpd+datestr.charAt(i)

		}



	}

	year=new String (tmpy);

	month=new String (tmpm);

	day=new String (tmpd)

//	alert(year);

//	alert(month);

//	alert(day);

	//tempdate= new String (year+month+day);

	//alert(tempdate);

	if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2))

	{

		//alert("Invalid format of date!");

		return 0;

	}

	if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )

	{

		//alert ("Invalid month or day!");

		return 0;

	}

	if (!((year % 4)==0) && (month==2) && (day==29))

	{

		//alert ("This is not a leap year!");

		return 0;

	}

	if ((month<=7) && ((month % 2)==0) && (day>=31))

	{

		//alert ("This month is a small month!");

		return 0;

	

	}

	if ((month>=8) && ((month % 2)==1) && (day>=31))

	{

		//alert ("This month is a small month!");

		return 0;

	}

	if ((month==2) && (day==30))

	{

		alert("The Febryary never has this day!");

		return 0;

	}

	

	return 1;

}

/**
 *对数字的小数位进行四舍五入
 *param number 	需要做四舍五入的数字
 *param deci	需要保留的小数位数,小数位数不能多于6位,大于六位精度有时候会出现问题
 *return 		四舍五入后的数字,如果数字没有小数位,数字精度低于于要求的精度则返回原值,如果输入不是数字型返回0
 */
function round(num,deci){
	if (isNaN(num)) return 0;			//如果不是数字,返回0
	
	var tmp			= "1";						//中间变量
	var returnValue = new String(num);					//返回值
	var index 		= returnValue.indexOf(".");	//小数点位置
	var len 		= index + deci + 1;			//返回数值的长度
	var precision	= 0.0;						//需要的精度,用来计算准确的返回值
	
	for (var n=0;n<deci;n++){					//取得需要的位数
		tmp += "0";	
	}
	precision = parseFloat(tmp); 				//获得数值
	
	if (index != -1 && len<returnValue.length){
		var nextNum = returnValue.charAt(len);
		returnValue = returnValue.substring(0,len);
		if (parseInt(nextNum)>=5) returnValue = (parseFloat(returnValue)*precision+1)/precision;
	}

	return parseFloat(returnValue);
}



/*********************放大镜方法组************************/

/**
 * 放大镜弹出方法
 * added by ligangwang
 * 2004-03-02
 */
function openWindow(elementNames,
					elementFields,
					ctrlElementNames,
					ctrlElementFields,
					ctrlValues,
					ctrlFields,
					ctrlOperators,
					ctrlLogicOperators,
					listTitleDisplayNames,
					listTitleDisplayFields,
					windowTitle,
					anchorPosition,
					sqlNum,
					tName,
					nextFocusElementNames){
						
	
	gnIsSelectCtrl=1;			//防止窗口弹出两次
	
	/**
	 * 定义传递的参数
	 */
	var vParams = "";
	/**
	 * 添加返回值元素名称
	 */
	for (var n=0;n<elementNames.length;n++){
		if (n==0){
			vParams+="?elementNames="+elementNames[n];
		}
		else{
			vParams+="&elementNames="+elementNames[n];
		}
	}
	
	/**
	 * 确定初始的控制域是否是放大镜显示元素本身,如果是则循环添加校验域时从1开始,如果不是从0开始
	 * 因为自己不加校验域
	 */
	var startNum = (ctrlElementNames[0] == elementNames[0])?1:0;
	/**
	 * 添加校验域
	 */
	for (var n=startNum;n<ctrlElementNames.length;n++){
		if (ctrlElementNames[n]!="" && eval("document.all."+ctrlElementNames[n]) !=null){					//如果控制元素存在,则添加这个控制条件
			if (ctrlElementNames[n]!=null && ctrlElementNames[n]!=""){
				vParams+="&elementNames="+ctrlElementNames[n]+elementNames[0];
			}
		}
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 添加返回值的数据库字段名称
	 */
	for (var n=0;n<elementFields.length;n++){
		vParams+="&elementFields="+elementFields[n];
	}
	
	
	for (var n=startNum;n<ctrlElementFields.length;n++){
		if (ctrlElementNames[n]!="" && eval("document.all."+ctrlElementNames[n]) != null){				//如果页面存在这个控制元素,则添加这个控制条件
			var temp = "";
			if (ctrlElementFields[n].indexOf("_")==0){
				temp = ctrlElementFields[n].substring(1);
			}
			else{
				temp = ctrlElementFields[n];
			}
			vParams+="&elementFields="+temp;
		}
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 弹出页面显示的字段描述
	 */
	for (var n=0;n<listTitleDisplayNames.length;n++){
		vParams+="&listTitleDisplayNames="+listTitleDisplayNames[n];
	}
	
	/**
	 * 弹出页面显示对应数据库字段名
	 */
	for (var n=0;n<listTitleDisplayFields.length;n++){
		vParams+="&listTitleDisplayFields="+listTitleDisplayFields[n];
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 添加约束值，第一个添加的是本放大镜显示元素的内容,作为模糊查询条件
	 */
	vParams+="&ctrlValues="+eval("document.all."+elementNames[0]+".value");
	for (var n=0;n<ctrlElementNames.length;n++){
		if (ctrlElementNames[n]!="" && eval("document.all."+ctrlElementNames[n]) != null){				//如果页面存在这个控制元素,则添加这个控制条件
			var obj = eval("document.all." + ctrlElementNames[n]);			//控制对象

			vParams+="&ctrlValues=" + obj.value;
		} //end if
	} // end for n
	
	for (var n=0;n<ctrlValues.length;n++){
		vParams+="&ctrlValues=" + ctrlValues[n];
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 添加约束值对应数据库的字段，第一个添加的是本放大镜显示元素的对应字段
	 */
	vParams+="&ctrlFields="+elementFields[0];
	for (var n=0;n<ctrlElementFields.length;n++){
		if (ctrlElementNames[n]!="" && eval("document.all." + ctrlElementNames[n]) != null){
			vParams+="&ctrlFields=" + ctrlElementFields[n];
		}
	}
	for (var n=0;n<ctrlFields.length;n++){
		vParams+="&ctrlFields=" + ctrlFields[n];
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 添加约束操作符
	 */
	for (var n=0;n<ctrlOperators.length;n++){
		if (n<=ctrlElementNames.length){
			if (n == 0){
				vParams+="&ctrlOperators="+ctrlOperators[n];
			}
			else{
				if (ctrlElementNames[n-1]!="" && eval("document.all." + ctrlElementNames[n-1]) != null){
					vParams+="&ctrlOperators="+ctrlOperators[n];
				}
			}
		}
		else{
			vParams+="&ctrlOperators="+ctrlOperators[n];
		}
	}
	//--------------------------------------------------------------------------------
	
	/**
	 * 添加约束的逻辑操作符
	 */
	var operatorOutter = "";
	for (var n=0;n<ctrlLogicOperators.length;n++){
		if (n<ctrlElementNames.length){
			var operatorInner = ctrlLogicOperators[n];
			if (ctrlElementNames[n]!="" && eval("document.all." + ctrlElementNames[n]) != null){
				if (operatorInner == "and"){
					vParams+="&ctrlLogicOperators="+ctrlLogicOperators[n];
				}
				else if (operatorInner == "or") {
					if (operatorInner == operatorOutter || operatorOutter == ""){
						vParams+="&ctrlLogicOperators="+operatorInner;
					}
					else{
						vParams+="&ctrlLogicOperators="+operatorOutter;
					}
				}
			}
			operatorOuter = ctrlLogicOperators[n];
		}
		else{
			vParams+="&ctrlLogicOperators="+ctrlLogicOperators[n];
		}
	}

	vParams+="&windowTitle="+windowTitle;						//添加弹出窗口的名称
	vParams+="&anchorPosition="+anchorPosition;					//添加锚点所在位置
	vParams+="&sqlNum="+sqlNum;									//添加sql方法的代码
	vParams+="&tName="+tName;
	
	for (var n=0;n<nextFocusElementNames.length;n++){
		vParams+="&nextFocusElementNames="+nextFocusElementNames[n];		//添加下一个焦点位置
	}
	
	//--------------------------------------------------------------------------------
	
	//alert(vParams);
	var isMagnifierDisabled = eval("document.all."+elementNames[0]+".disabled");
	if (!isMagnifierDisabled){			//如果当前没有被disabled,弹出窗口

	
		window.open("/NASApp/iTreasury-securities/magnifier/magnifier.jsp"+vParams,"selectAnything","toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,left=300,top=100,scrollbars=yes");
	}
	
}
/**
 * 多文本框放大镜,如帐户放大镜,当按下回车时跳到下一个文本框,当按下back space时删除当前文本框内容,并跳到上一个文本框
 */
function handleKeyEvent(magName,num){
	/*if (event.keyCode == 13){
		var obj = eval("document.all." + magName + "[" + (num+1) + "]");
		if (obj != null){
			
			obj.focus();
			}
		}*/
	if (event.keyCode == 8){
		var currentObj = eval("document.all." + magName + "[" + (num) + "]");
		var lastObj = eval("document.all." + magName + "[" + (num-1) + "]");
		if (currentObj.value != ""){
			currentObj.value = "";
			}
		else{
			if (lastObj != null){
				lastObj.value = "";
				lastObj.focus();
				}
			}
		}
	}
/**
 * 将一个文本框里的内容,按照分隔符切分,并分到几个文本框
 */
function splitAccount(delims,obj,accountObjName){
	if (obj.value != null && obj.value.length>0){
		var accounts = obj.value.split(delims);
		var textNum = eval("document.all."+accountObjName+".length");
		
		for (var n=0;n<textNum;n++) eval("document.all."+accountObjName+"["+n+"].value = ''");
		
		var len = textNum>accounts.length?accounts.length:textNum;
		for (var n=0;n<len;n++){
			var tmp = accounts[n] == null || accounts[n] == ""?"''":"'"+accounts[n]+"'";
			//alert("document.all."+accountObjName + "[" + n + "].value = " + tmp);
			eval("document.all."+accountObjName + "[" + n + "].value = " + tmp);
			}
		}
	}
/**
 * 拼多文本框模糊查询条件
 */
function changeAccountNo(delims,accountNoObj,accountObjName){
	var subAccountObj = eval("document.all."+accountObjName);
	var accountNo = "";
	var isEmpty = false;
	for (var n=0;n<subAccountObj.length;n++){
		if (subAccountObj[n].value!=null && subAccountObj[n].value.length>0){
			accountNo += subAccountObj[n].value;
			if (n<subAccountObj.length-1 && subAccountObj[n+1].value!=null && subAccountObj[n+1].value != ""){
				accountNo += delims;
				}
			else{
				if (isEmpty) {
					accountNo = "";
					break;
					}
				isEmpty = true;
				}
				
			}
		}
	accountNoObj.value = accountNo;
	}
	
	
/*********************放大镜方法组************************/

//-------------------------------------------
function addDate(startDate,dayNum){
	
	if (chkdate(startDate)==0){			//检查日期格式是否正确
		return;
	}
	if (isNaN(dayNum)){					//检查增加天数是否是数字
		return;
	}
	
	var dtDate = new Date();
	var splitDate = startDate.split("-");
	
	dtDate.setYear(splitDate[0]);
	dtDate.setMonth(splitDate[1]);
	dtDate.setDate(splitDate[2]);
	
	dtDate.setDate(dtDate.getDate()+parseInt(dayNum));
	
	return dtDate.getYear() + "-" + dtDate.getMonth() + "-" + dtDate.getDate();
}

function formatDateString(strDate){
	
	var splitDate,strYear,strMonth,strDay;
	
	splitDate = strDate.split(" ");
	
	strDate = splitDate[0];

	splitDate = strDate.split("-");
	
	strYear = splitDate[0];
	if (strYear.length < 2 )
	{
		strYear = "0" + strYear;
	}
	strMonth = splitDate[1];
	if (strMonth.length < 2 )
	{
		strMonth = "0" + strMonth;
	}
	strDay = splitDate[2];
		
	return strYear + "-" + strMonth + "-" + strDay;
}

/**
 * 用公式校验是否结果复核公式方法组
 */
{//start validate formula
	var formulas = new Array();			//计算公式
	var keys  = new Array();			//公式的代码
	var precision = 0.01;				//精度,由于javascript的精度问题,只要结果的差距在某一个精度范围内就认为相等
	
	
	//-----------------------添加默认公式"+","-","*","/"
	addFormula("+","args+args");
	addFormula("-","args-args");
	addFormula("*","args*args");
	addFormula("/","args/args");
	//-----------------------
	
	/**
	 *	添加公式
	 */
	function addFormula(key,formula){
		keys[keys.length+1] = key;
		formulas[formulas.length+1] = formula;
		}
	/**
	 * 通过公式代码获取公式
	 */
	function getFormula(key){
		var formula = "";
		for (var n=0;n<keys.length;n++){
			if (keys[n] == key){
				formula = formulas[n];
				break;
				}
			}
		return formula;
		}
	/**
	 * 设置精度
	 */
	function setPrecision(precis){
		if (!isNaN(precis))	precision = precis;
		}
	/**
	 * 使用公式
	 * 参数解释:
	 *		如果公式中有三个数字,那么传入五个参数,
	 *		第一个是公式代码,
	 * 		第二个到第四个是公式中的参数,第五个是结果,公式计算如果和结果相等返回true,不相等返回false
	 */
	function validateFormula(){
		if (arguments.length<4){
			alert("传入公式参数错误");
			return;
			} 
		
		var formula = getFormula(arguments[0]);			//得到需要校验的公式
		
		if (formula.length == 0){
			alert("传入的公式代码错误");
			return;
			}
		
		/**
		 * 计算公式中含有参数的数量
		 */
		var argsNum = count(formula,"args");
		if (argsNum!=(arguments.length-2)){
			alert("传入参数和公式需要参数数量不匹配");
			return;
			}
		
		/**
		 * 开始按照公式校验结果是否正确
		 */
		for (var n=0;n<argsNum;n++){
			formula=formula.replace("args",""+arguments[n+1]);
			}
		var result = eval(formula);					//计算结果
		/**
		 * 校验,默认差值在小于0.01大于-0.01,则认为合法
		 */
		var difference = arguments[arguments.length-1] - result;
		if(difference <= precision && difference >= -precision){
			return true;
			}
		else{
			return false;
			}
		
		}
	/**
	 * 获得一个字符串中含有的另一个字符串的数量
	 * @param str 要计算含有数量的字符串
	 * @param deli 分隔符,即需要计算数量的子串
	 */
	function count(str,deli){
		var tmpStr = str;
		var deliNum = 0;
		while (tmpStr.indexOf(deli)>=0){
			tmpStr=tmpStr.substring(tmpStr.indexOf(deli)+deli.length);
			deliNum++;
			}
		return deliNum;
		}
	//添加普通运算公式
}//end validate formula

  /**
   * 将放大镜中的内容选择到多选框
   * add lhj 2004-04-07
   */	
function addCode(txt,hid,selt)
{
    if(hid.value == "-1"){
	   alert("请从放大镜中选择!");
	   return;
	}
	
	if ((txt.value != "") && (hid.value != "") )
    {
        for (i = 0; i < selt.options.length; i++)
        {
            if (selt.options[i].value == hid.value)
            {
                alert("重复选择!");
                return;
            }
        }
		var opt = new Option(txt.value,hid.value);
		selt.options.add(opt);
	}
	txt.value = "";
}
/**
   * 将多选框的内容删除之
   * add lhj 2004-04-07
   */	
function removeCode(hid,selt)
{
	if (selt.selectedIndex > -1 )
	{
		hid.value = "";
		selt.options[selt.selectedIndex] = null;
		selt.focus();
	} else {
		alert("请选择!")
	}
}


// add by chenlei 2003-5-20  start
function addSelCode(sel,selt)
{
    if(sel.value == "-1"){
	   alert("请从下拉框中选择!");
	   return;
	}
	
	if ((sel.options[sel.selectedIndex].text != "") && (sel.options[sel.selectedIndex].value != "") )
    {
        for (i = 0; i < selt.options.length; i++)
        {
            if (selt.options[i].value == sel.options[sel.selectedIndex].value)
            {
                alert("重复选择!");
                return;
            }
        }
		var opt = new Option(sel.options[sel.selectedIndex].text,sel.options[sel.selectedIndex].value);
		selt.options.add(opt);
	}
}

/**
 * 删除select中的值
 */
function removeSelCode(selt)
{
	if (selt.selectedIndex > -1 )
	{
		//list.value = "";
		selt.options[selt.selectedIndex] = null;
		selt.focus();
	} else {
		alert("请选择!")
	}
}

/**
*对放大镜值进行判断，如果有值则必须是从列表选择的，不允许手工输入
*/
function validateMagnifier(form){
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oAllFields = new allMagnifierFields();

	for (x in oAllFields) 
	{
	 	
		//如果是放大镜，则判断是否是从放大镜中选择的数据，如果是则返回true，如果是手工输入则返回false
		if (oAllFields[x][3] == 'magnifier') 
		{
						
			if ((form[oAllFields[x][1]].value<0 || form[oAllFields[x][1]].value == '') && form[oAllFields[x][0]].value!=''){
				
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][2]+"的数据，请从放大镜中选择！";
				bValid = false;
			}
		}
	}

	if (fields.length > 0) 
	{
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}