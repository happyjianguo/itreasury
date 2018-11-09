/**
 * 文件名：SettCheck.js
 * 客户端校验 
 * author:Forest
 * date:2003-09-17
 * 包含的校验函数：
 * 1,checkIsSelect 校验放大镜的数据是否是选择
 * 2,validateFields 校验页面所有元素（必输项是否输入，类型是否匹配）
 * 3,checkIsMatch 校验放大镜是否匹配（比如，客户和帐户是否匹配）
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
 * 14,checkClientAccount 校验客户和帐户是否匹配（传入页面名称与帐户客户放大镜域名及描述）
 * 15,checkClientContract 校验客户和合同是否匹配（传入页面名称与帐户客户放大镜域名及描述）
 * 16,checkContractPayForm 校验合同和放款通知单是否匹配（传入页面名称与帐户客户放大镜域名及描述）
 * 17,checkAccountDepositNo 校验帐户与存单放大镜的数据是否匹配（传入页面名称与帐户客户放大镜域名及描述）
 * 18,checkRequireClientBankBill 校验申领客户与银行票据的数据是否匹配（传入页面名称与帐户客户放大镜域名及描述）
 * 19,addDate 增加定期存款期限(定期开立专用)
 * 20,round	得到数值的指定小数位的四舍五入值
 * 21,checkFreeFormPayForm 校验免换通知单和放款通知单是否匹配（）
 * 22,checkAheadRepayFormPayForm 校验提前还款通知单和放款通知单是否匹配（）
 * 23,checkLineNo 检查项目编号的格式
 * 24,trimString 格式化字符串，去掉字符串首尾的空格
 * 25,checkParameter 校验行项目取数逻辑的'自定义参数'栏位
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
 * Return: 是，返回true; 否，返回false.
 */
function checkIsSelect(form,sIDControl,sValueControl,sMagnifierDesc)
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
	//alert ("tete");
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oAllFields = new allFields();
	for (x in oAllFields) 
	{
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
		
		//如果是放大镜类型，判断传入的控件是否是数值类型的。
		if (bIsFormControl == true && oAllFields[x][2] == "magnifier")
		{
			if (!isInt(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"放大镜输入的参数不正确，请修改程序！";
				bValid = false;	
			}
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
			fields[i++] = oAllFields[x][1]+"不能为空，请选择或录入！";
			bValid = false;
		}

		if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value == '')
		{
			if (i == 0) 
			{
				focusField = form[oAllFields[x][0]+'Ctrl'];
				
				//帐户号放大镜专用
				if(focusField.type == 'hidden')
				{
					focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
				}
			}			
			fields[i++] = oAllFields[x][1]+"不能为空，请从放大镜中选择！";
			bValid = false;
		}

		//如果是放大镜，则判断是否是从放大镜中选择的数据
		if (bIsFormControl == true && oAllFields[x][2] == 'magnifier' && form[oAllFields[x][0]+'Ctrl'].value != '') 
		{
			if (form[oAllFields[x][0]].value <= 0)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];

					//帐户号放大镜专用
					if(focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}
				fields[i++] = oAllFields[x][1]+"的数据，请从放大镜中选择！";
				bValid = false;			
			}
		}

		//如果是日期，则判断是否是正确的日期格式
		if (bIsFormControl == true && oAllFields[x][2] == 'date' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'int' && form[oAllFields[x][0]].value != '') 
		{
			//判断是否正确的数字类型
			if (!isInt(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"必须为数字，请重新录入！";
				bValid = false;			
			}
		}
		//如果是金额类型，则判断是否正确的金额类型
		if (bIsFormControl == true && oAllFields[x][2] == 'money' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'allmoney' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'rate' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'email' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'fax' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'phone' && form[oAllFields[x][0]].value != '') 
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
		if (bIsFormControl == true && oAllFields[x][2] == 'postalcode' && form[oAllFields[x][0]].value != '') 
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
 * @param nID 放大镜隐藏的标识值
 * @param sValue 放大镜主要控件的值
 * @param sMagnifierDesc 放大镜描述
 * Return: 是，返回true; 否，返回false.
 */
function checkIsMatch(nFirstID,nSecondID,sFirstDesc,sSecondDesc)
{
	if (isInt(nFirstID) && isInt(nSecondID))
	{
		//alert("请输入有效的放大镜标识值");
		if (nFirstID != nSecondID)
		{
			alert(sFirstDesc+"和"+sSecondDesc+"不匹配，请重新选择！");
			return false;
		}
	}
	else
	{
		alert ("请输入正确的标识！");
		return false;
	}
	return true;
}

/**
 * 校验客户与合同放大镜的数据是否匹配
 * @param form 页面名称
 * @param objClient 客户放大镜域名
 * @param objContract 合同放大镜域名
 * @param sClientDesc 客户描述
 * @param sContractDesc 合同描述
 * Return: 是，返回true; 否，返回false.
 */
function checkClientContract(form,objClient,objContract,sClientDesc,sContractDesc)
{
	var strFormName =  form.name;
	var nContractClientIDValue;
	var nClientIDValue;
	eval("nContractClientIDValue = " + strFormName + "." + objContract+ "ClientID.value");
	eval("nClientIDValue = " + strFormName + "." + objClient + ".value");
	return checkIsMatch(nClientIDValue,nContractClientIDValue,sClientDesc,sContractDesc);
}

/**
 * 校验合同与放款通知单大镜的数据是否匹配
 * @param form 页面名称
 * @param objContract 合同放大镜域名
 * @param objPayForm 放款通知单放大镜域名
 * @param sContractDesc 合同描述
 * @param sPayFormDesc 放款通知单描述
 * Return: 是，返回true; 否，返回false.
 */
function checkContractPayForm(form,objContract,objPayForm,sContractDesc,sPayFormDesc)
{
	var strFormName =  form.name;
	var nPayFormContractIDValue;
	var nContractIDValue;
	eval("nPayFormContractIDValue = " + strFormName + "." + objPayForm+ "ContractID.value");
	eval("nContractIDValue = " + strFormName + "." + objContract + ".value");
	return checkIsMatch(nContractIDValue,nPayFormContractIDValue,sContractDesc,sPayFormDesc);
}

/**
 * 校验合同与放款通知单大镜的数据是否匹配
 * @param form 页面名称
 * @param objFreeForm 免换通知单放大镜域名
 * @param objPayForm 放款通知单放大镜域名
 * @param sFreeFormDesc 免换通知单描述
 * @param sPayFormDesc 放款通知单描述
 * Return: 是，返回true; 否，返回false.
 */
function checkFreeFormPayForm(form,objFreeForm,objPayForm,sFreeFormDesc,sPayFormDesc)
{
	var strFormName =  form.name;
	var nFreeFormPayFormIDValue;
	var nPayFormIDValue;
	eval("nFreeFormPayFormIDValue = " + strFormName + "." + objFreeForm+ "PayFormID.value");
	eval("nPayFormIDValue = " + strFormName + "." + objPayForm + ".value");
	return checkIsMatch(nPayFormIDValue,nFreeFormPayFormIDValue,sPayFormDesc,sFreeFormDesc);
}

/**
 * 校验合同与放款通知单大镜的数据是否匹配
 * @param form 页面名称
 * @param objAheadRepayForm 提前还款通知单放大镜域名
 * @param objPayForm 放款通知单放大镜域名
 * @param sAheadRepayFormDesc 免换通知单描述
 * @param sPayFormDesc 提前还款通知单描述
 * Return: 是，返回true; 否，返回false.
 */
function checkAheadRepayFormPayForm(form,objAheadRepayForm,objPayForm,sAheadRepayFormDesc,sPayFormDesc)
{
	var strFormName =  form.name;
	var nAheadRepayFormPayFormIDValue;
	var nPayFormIDValue;
	eval("nAheadRepayFormPayFormIDValue = " + strFormName + "." + objAheadRepayForm+ "PayFormID.value");
	eval("nPayFormIDValue = " + strFormName + "." + objPayForm + ".value");
	return checkIsMatch(nPayFormIDValue,nAheadRepayFormPayFormIDValue,sPayFormDesc,sAheadRepayFormDesc);
}


/**
 * 校验申领客户与银行票据放大镜的数据是否匹配
 * @param form 页面名称
 * @param objAccount 申领客户放大镜域名
 * @param objDepositNo 银行票据放大镜域名
 * @param sAccountDesc 申领客户描述
 * @param sDepositNoDesc 银行票据描述
 * Return: 是，返回true; 否，返回false.
 */
function checkRequireClientBankBill(form,objRequireClient,objBankBill,RequireClientDesc,sBankBillDesc)
{
	var strFormName =  form.name;
	var nBankBillRequireClientValue;
	var nRequireClientValue;
	eval("nBankBillRequireClientValue = " + strFormName + "." + objBankBill+ "RequireClientID.value");
	eval("nRequireClientValue = " + strFormName + "." + objRequireClient + ".value");
	//alert(nBankBillRequireClientValue);
	//alert(nRequireClientValue);
	if (nBankBillRequireClientValue > 0)
	{
		return checkIsMatch(nRequireClientValue,nBankBillRequireClientValue,RequireClientDesc,sBankBillDesc);
	}
	else
	{
		return true;
	}
}


/**
 * 校验帐户与存单放大镜的数据是否匹配
 * @param form 页面名称
 * @param objAccount 帐户放大镜域名
 * @param objDepositNo 存单放大镜域名
 * @param sAccountDesc 帐户描述
 * @param sDepositNoDesc 存单描述
 * Return: 是，返回true; 否，返回false.
 */
function checkAccountDepositNo(form,objAccount,objDepositNo,sAccountDesc,sDepositNoDesc)
{
	var strFormName =  form.name;
	var nDepositNoAccountIDValue;
	var nAccountIDValue;
	eval("nDepositNoAccountIDValue = " + strFormName + "." + objDepositNo+ "AccountID.value");
	eval("nAccountIDValue = " + strFormName + "." + objAccount + ".value");
	return checkIsMatch(nAccountIDValue,nDepositNoAccountIDValue,sAccountDesc,sDepositNoDesc);
}

/**
 * 校验客户与帐户放大镜的数据是否匹配
 * @param form 页面名称
 * @param objAccount 帐户放大镜域名
 * @param objClient 客户放大镜域名
 * @param sClientDesc 客户描述
 * @param sAccountDesc 帐户描述
 * Return: 是，返回true; 否，返回false.
 */
function checkClientAccount(form,objClient,objAccount,sClientDesc,sAccountDesc)
{
	var strFormName =  form.name;
	var nAccountClientIDValue;
	var nClientIDValue;
	eval("nAccountClientIDValue = " + strFormName + "." + objAccount+ "ClientID.value");
	eval("nClientIDValue = " + strFormName + "." + objClient + ".value");
	return checkIsMatch(nClientIDValue,nAccountClientIDValue,sClientDesc,sAccountDesc);
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



/*检查项目编号的格式
 *项目编号的格式只能为"999_999_999_999"等 ，以下划线分段，每段3个数字的格式
 *编号的长度必须是4n-1(n>0),length + 1能被4整除
 */
function checkLineNo(frm,lineNo)
{
	
	var checkOK = "0123456789_";
	var checkStr = lineNo;
	var allValid = true;
	
	//step 1 只能录入数字和下划线
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
	}
	
	if( checkStr.charAt(0) == "_" || checkStr.charAt(checkStr.length-1) == "_" )
	{
		allValid = false;
	}
	
	//step 2长度 + 1
	varLength = checkStr.length + 1;
	
	if( varLength%4 != 0 )
	{
		allValid = false;
	}
	
	
	//step 3 检查每段3个数字
	var strNumber = "0123456789";
	var strFinalNo ="";//去除第4n位后的字符串不能再含有"_"
	if( checkStr.length == 3 ) //如果是一级行项目(不检查"_")
	{
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < strNumber.length;  j++)
			{
				if (ch == strNumber.charAt(j))
				{
					break;
				}
			}
			if (j == strNumber.length)
			{
				allValid = false;
				break;
			}
		}
		
	}
	else//不是一级行项目的情况(检查"_")
	{
		for( i = 0; i < checkStr.length;i++ )
		{
			if( i > 0)
			{
				j = i*4 - 1;
			}
			else
			{
				j = 0;
			}
			
			strUnderline = checkStr.charAt(j);
			
			if( j > 0 && j < checkStr.length && strUnderline != "_" )
			{
				allValid = false;
			}
			
			
			if( (i+1)%4 != 0 )
			{
				strFinalNo += checkStr.charAt(i);
			}
			
		}
		
	}
	
	

	for (i = 0;  i < strFinalNo.length;  i++)
	{
		ch = strFinalNo.charAt(i);
		for (j = 0;  j < strNumber.length;  j++)
		{
			if (ch == strNumber.charAt(j))
			{
				break;
			}
		}
		if (j == strNumber.length)
		{
			allValid = false;
			break;
		}
	}
		
	
	if( allValid == false )
	{
		alert("项目编号只能是以下划线分段，每段3个数字的形式，如：001_002_003。请重新录入！");
		frm.lineNo.focus();
		return false;
	}
	else
	{
		return true;
	}
	
}

//检查自定义参数用
function checkLineNoForParameter(frm,lineNo)
{
	
	var checkOK = "0123456789_";
	var checkStr = lineNo;
	var allValid = true;
	
	//step 1 只能录入数字和下划线
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
	}
	
	if( checkStr.charAt(0) == "_" || checkStr.charAt(checkStr.length-1) == "_" )
	{
		allValid = false;
	}
	
	//step 2长度 + 1
	varLength = checkStr.length + 1;
	
	if( varLength%4 != 0 )
	{
		allValid = false;
	}
	
	
	//step 3 检查每段3个数字
	var strNumber = "0123456789";
	var strFinalNo ="";//去除第4n位后的字符串不能再含有"_"
	if( checkStr.length == 3 ) //如果是一级行项目(不检查"_")
	{
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < strNumber.length;  j++)
			{
				if (ch == strNumber.charAt(j))
				{
					break;
				}
			}
			if (j == strNumber.length)
			{
				allValid = false;
				break;
			}
		}
		
	}
	else//不是一级行项目的情况(检查"_")
	{
		for( i = 0; i < checkStr.length;i++ )
		{
			if( i > 0)
			{
				j = i*4 - 1;
			}
			else
			{
				j = 0;
			}
			
			strUnderline = checkStr.charAt(j);
			
			if( j > 0 && j < checkStr.length && strUnderline != "_" )
			{
				allValid = false;
			}
			
			
			if( (i+1)%4 != 0 )
			{
				strFinalNo += checkStr.charAt(i);
			}
			
		}
		
	}
	
	

	for (i = 0;  i < strFinalNo.length;  i++)
	{
		ch = strFinalNo.charAt(i);
		for (j = 0;  j < strNumber.length;  j++)
		{
			if (ch == strNumber.charAt(j))
			{
				break;
			}
		}
		if (j == strNumber.length)
		{
			allValid = false;
			break;
		}
	}
		
	
	if( allValid == false )
	{
		return false;
	}
	else
	{
		return true;
	}
	
}

//格式化字符串，去掉字符串首尾的空格
function trimString(stringValue)
{
	var s = stringValue;
	while (s.substring(0,1) == ' ') 
	{
    	s = s.substring(1,s.length);
 	}
    while (s.substring(s.length-1,s.length) == ' ') 
	{
    	s = s.substring(0,s.length-1);
    }
    return s;
}

/**
 * 校验行项目取数逻辑的'自定义参数'栏位
 * 自定义参数只能是下列格式中的一种
 * 1.可以为空
 * 2.#开头
 * 3.@reserve
 * 4.@paymentrate
 * 5.数字
 *
 */
function checkParameter(frm,strParameter)
{
	var flag = false;
	var trimParameter = trimString(strParameter);
	
	// 1.为空
	if( trimParameter.length == 0 )
	{
		//alert("1.为空");
		return true;
	}
	
	
	var firstChar ="";
	if( strParameter != "" && strParameter != null )
	{
		firstChar = strParameter.substring(0,1);
		//alert("第一个字符是:"+firstChar);
	}
	
	//2.以#开头
	if( firstChar == "#" && strParameter.length > 1 )
	{
		//alert("2.以#开头");
		//检查#号后面的字符串是否符合行项目的格式
		var sharpString = strParameter.substring(1,strParameter.length);
		//alert(sharpString);
		if( checkLineNoForParameter(frm,sharpString) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//3.@reserve
	if( strParameter == "@reserve" )
	{
		//alert("3.@reserve");
		return true;
	}
	
	//4.@paymentrate
	if( strParameter == "@paymentrate" )
	{
		//alert("4.@paymentrate");
		return true;
	}
	
	//5.数字
	if( isFloat(strParameter) == true )
	{
		//alert("5.数字");
		return true;	
	}
	
	return flag;
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
		window.open("/NASApp/iTreasury-securities/iTreasury-securities/magnifier/magnifier.jsp"+vParams,"selectAnything","toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,left=300,top=100,scrollbars=yes");
	}
	
}