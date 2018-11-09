/**
 文件名：check.js
 作者 ： 
 * 客户端校验 

* 目录创建日期：2005-9-7 by yuanxue
* 1. CompareDateString 比较两个日期大小
* 2. CompareDate 比较两个日期输入栏位日期大小
* 3. checkCommonTable,checkCommonTable1 检查填写数量的控件输入是否正确，包括单个控件和控件组
* 4. validpassword 检查两次密码输入是否一致
* 5. reverseFormatAmount1 反向格式化金额
* 6. checkAmount 校验金额
* 7. checkSubjectNo 校验科目放大镜非空
* 8. checkControl 校验放大镜非空
* 9. checkCashFlow 校验后缀 ID 的放大镜非空（不置焦点）
* 10.checkAccountClient，checkFixedDepositClient 校验账户和客户是否匹配
* 11.checkBankAccount 校验银行和账户是否匹配
* 12.checkPayGetAccount 校验付款收款账户
* 13.checkList 检查下拉列表选择非空
* 14.checkRate 校验利率
* 15.checkDate 校验日期
* 16.checkDateStart 检查输入日期必须在指定开始日期之后
* 17.checkDateEnd 检查输入日期必须在指定结束日期之前
* 18.checkString 检查字符串非空
* 19.checkInterestExecuteDate 检查起息日和执行日
* 20.checkInterestExecuteDate1 检查起息日和执行日(出错后，不指定页面焦点)
* 21.checkContractStartEndDate 检查合同日期起始和结束
* 22.InputValid 校验函数
* 23.isInt 判断是否整数
* 24.isFloat 判断是否浮点数
* 25.isDate 判断是否日期
* 26.isEmail 校验 email 格式
* 27.isFax 校验传真格式
* 28.MM_openBrWindow 打开新窗口
* 29.fillFormData 填写Form的数据(根据 Url 后面所带的参数填写 form 数据)
* 30.fillReturnFormData 填写Form的数据(只有一个表单域的情况)
* 31.checkMagnifier 放大镜校验
* 32.checkIsSelect 校验放大镜的数据是否是选择
* 33.trimFormValue 去除表单中文本框内的前后空格 
* 34.lValuelen 计算字符串长度（一个汉字占两个字符长度，一个英文占一个字符长度）
*/

/**
 * 如果 strBeginDate 晚于 strBeginDate，返回 false，否则返回 true
 *  strBeginDate 开始日期
 *  strEndDate 结束日期
 */
 function CompareDateString(strBeginDate,strEndDate)
{

	var temp,s;

	temp=new String(strBeginDate);

	if(chkdate(temp)==0)

	{

				//alert(strBegin); 

				return false;

	} 

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				//alert(strBegin); 

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtOne=new Date(s);

	if(dtOne.toString()=="NaN")

	{

		//alert(strBegin); 

		return false;

	}

	

	temp=new String(strEndDate);

	if(chkdate(temp)==0)

	{

				//alert(strEnd); 

				return false;

	}

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				//alert(strEnd); 

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtTwo=new Date(s);

	if(dtTwo.toString()=="NaN")

	{

		//alert(strEnd); 

		return false;

	}

	dtTwo=new Date(s);

	if(dtOne.valueOf()>dtTwo.valueOf())

	{

		//alert(str); 

		return false;

	}

	else

		return true;

}

/**
* dtBegin，dtEnd 都必输且格式正确
* 如果 dtBegin 晚于 dtEnd 返回 false，否则返回 true
* dtBegin 开始日期栏位名称
* dtEnd 结束日期栏位名称
* str 提示信息
*/
function CompareDate(dtBegin,dtEnd,str)

{

	var temp,s;

	temp=new String(dtBegin.value);

	if(chkdate(temp)==0)

	{

				alert("请输入正确的开始日期");

				dtBegin.focus();

				return false;

	} 

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

	if(chkdate(temp)==0)

	{

				alert("请输入正确的结束日期");

				dtEnd.focus();

				return false;

	}

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

				dtEnd.focus();

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



function CompareBeginLessTHanEnd(dtBegin,dtEnd,str)

{

	var temp,s;

	temp=new String(dtBegin.value);

	if(chkdate(temp)==0)

	{

				alert("请输入正确的开始日期");

				dtBegin.focus();

				return false;

	} 

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

	if(chkdate(temp)==0)

	{

				alert("请输入正确的结束日期");

				dtEnd.focus();

				return false;

	}

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

				dtEnd.focus();

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

		dtEnd.focus();

		return false;

	}

	else

		return true;

}

/**
* 检查填写数量的控件是否输入正确
* ctrlname 控件名称
* OnlyOne 1 为只有一个，其他为控件组
*/
function checkCommonTable(ctrlname, OnlyOne)

{

	if(OnlyOne==1)

	{

		if(ctrlname.value!="")

		{

			if( !isFloat( ctrlname.value ) )

			{

				alert("请填写正确的数量! ");

//				ctrlname.focus();

				return false;

			}

			else if(ctrlname.value<=0)

			{

				alert("数量必须大于零!");

//				ctrlname.focus();

				return false;

			}

				return true;

		}

		else

		{

			alert("请填写数量!");

//			ctrlname.focus();

			return false;

		}

	}

	else

	{

//		alert("do more");

		var bAllEmpty=true;

		for( tmp=0;tmp<ctrlname.length; tmp++)

		{

			if(ctrlname[tmp].value!="")

			{

				bAllEmpty=false;

				if( !isFloat( ctrlname[tmp].value ) )

				{

					alert("请填写正确的数量! ");

//					ctrlname[tmp].focus();

					return false;

				}

				else if(ctrlname[tmp].value<=0)

				{

					alert("数量必须大于零!");

//					ctrlname[tmp].focus();

					return false;

				}

			}

		}

		if(bAllEmpty==true)

		{

			alert("请填写数量!");

//			ctrlname[0].focus();

			return false;

		}

	}

	return true;

}

/**
* 检查填写数量的控件是否输入正确
* ctrlname 控件名称
* OnlyOne 1 为只有一个，其他为控件组
*/
function checkCommonTable1(ctrlname, OnlyOne)

{

	if(OnlyOne==1)

	{

		if(ctrlname.value!="")

		{

			if( !isFloat( ctrlname.value ) )

			{

				alert("请填写正确的数量! ");

//				ctrlname.focus();

				return false;

			}

			else if(ctrlname.value<0)

			{

				alert("数量不能小于零!");

//				ctrlname.focus();

				return false;

			}

				return true;

		}

		else

		{

			alert("请填写数量!");

//			ctrlname.focus();

			return false;

		}

	}

	else

	{

//		alert("do more");

		var bAllEmpty=true;

		for( tmp=0;tmp<ctrlname.length; tmp++)

		{

			if(ctrlname[tmp].value!="")

			{

				bAllEmpty=false;

				if( !isFloat( ctrlname[tmp].value ) )

				{

					alert("请填写正确的数量! ");

//					ctrlname[tmp].focus();

					return false;

				}

				else if(ctrlname[tmp].value<0)

				{

					alert("数量不能小于零!");

//					ctrlname[tmp].focus();

					return false;

				}

			}

		}

		if(bAllEmpty==true)

		{

			alert("请填写数量!");

//			ctrlname[0].focus();

			return false;

		}

	}

	return true;

}

/**
* 检查两次输入的密码是否一致，如果密码非空并且两次输入一致返回 true，否则返回 false
* pass1 第一次输入的密码
* pass2 第二次输入的密码
*/
function validpassword( pass1, pass2 )

{

	var allValid = true;

	

		

	if( pass1=="" )

	{

		alert("请输入密码！");

		return false;

	}



	if( pass2=="" )

	{

		alert("请确认密码！");

		return false;

	} 

	

	if( pass1.length < 6 )

	{

		alert("密码长度至少6个字符！");

		return false;

	}



	if( pass1.length != pass2.length )

	{

		alert("两次输入的密码长度不一致！");

		return false;

	}

	for(i=0;i<pass1.length;i++)

	{

		if( pass1.charAt(i) != pass2.charAt(i) )

		{

			alert("两次输入的密码不一致!");

			allValid = false;

			break;

		}

	}

	return allValid;		

}



/**

 * 反向格式化金额

 * @param strData 需要格式化的数据

 * @return 返回反格式化的金额

 */ 

 function reverseFormatAmount1(strData)

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

 * 校验金额

 * @param d_input  需要校验的form中的域

 * @param d_notnull 如果不能为空，填1，否则为0

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkAmount(d_input,d_notnull,d_str)

{

		if ( !isFloat(reverseFormatAmount1(d_input.value)))

    {

		alert( d_str+"只能是数值" );

		d_input.focus();

		return (false);

    }

    
/*
    if(d_notnull!=2)//不判断最小值

    {

    	if  ( ((d_notnull==1) || (d_input.value!=""))  && reverseFormatAmount1(d_input.value) < 0.01 )

    	{

			alert(d_str+ "的值不能小于0.01。" );

			d_input.focus();

			return (false);

	}

   }	
*/
   if  ( ((d_input.value!="")) && reverseFormatAmount1(d_input.value) > 1000000000000 )

		{

			alert(d_str+ "的值不能大于1000000000000。" );

			d_input.focus();

			return (false);

		}

		return true;



//	return InputValid(d_input,d_notnull,"float",1,0.01,1000000000000,d_str);

}



/**

 *校验科目号放大镜非空

 * @param strFormName 域的名称

 * @param strCtrlName 控件名称

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkSubjectNo(strFormName,strCtrlName,d_str)

{

	if(eval(strFormName + "." + strCtrlName + ".value")=="")

	{

		alert("请选择一个" + d_str + "。");

		eval(strFormName + "." + strCtrlName + "Ctrl.focus()");

		return false;

	}	

	else

	{

		return true;

	}

}



/**

 * 校验标识放大镜非空

 * @param strFormName 域的名称

 * @param strCtrlName 控件名称

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkControl(strFormName,strCtrlName,d_str)

{

	if(eval(strFormName + "." + strCtrlName + ".value")<=0)

	{

		alert("请选择一个" + d_str + "。");

		eval(strFormName + "." + strCtrlName + "Ctrl.focus()");

		return false;

	}	

	else

	{

		return true;

	}

}



/**

 * 校验标识放大镜非空

 * @param strFormName 域的名称

 * @param strCtrlName 控件名称

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkCashFlow(strFormName,strCtrlName,d_str)

{

	if(eval(strFormName + "." + strCtrlName + "ID.value")<=0)

	{

		alert("请选择一个" + d_str + "。");

		//eval(strFormName + "." + strCtrlName + ".focus()");

		return false;

	}	

	else

	{

		return true;

	}

}



/**

 * 校验帐户、客户

 * @param strFormName form的名称

 * @param strAccountCtrlName 帐户控件名称

 * @param strAccountCtrlTitle 帐户控件Title

 * @param strClientCtrlName 客户控件名称

 * @param strClientCtrlTitle 客户控件Title

 */

function checkAccountClient(strFormName,strAccountCtrlName,strAccountCtrlTitle,strClientCtrlName,strClientCtrlTitle)

{

	//得到帐户标识，帐户对应客户标识，客户标识

	var nAccountID,nAccountClientID,nClientID,strClientAccountID;

	nAccountID=eval(strFormName + "." + strAccountCtrlName + ".value");

	nAccountClientID=eval(strFormName + "." + strAccountCtrlName + "ClientID.value");

	nClientID=eval(strFormName + "." + strClientCtrlName + ".value");

	strClientAccountID=eval(strFormName + "." + strClientCtrlName + "AccountID.value");

	if((nAccountID>0) && (nClientID>0))//只有帐户和客户都选择了才检查

	{

		if(nAccountClientID<0)//说明是从数据库里读出来的

		{

		  if(strClientAccountID=="-1")//说明从数据库里读出

		    return true;

		  else

		  {

		  	if(strClientAccountID=="")

		  	{

					alert(strAccountCtrlTitle + "和" + strClientCtrlTitle + "不匹配，请重新选择。");

		  		return false;

		  	}

		  	else

				{		  	

					var i,nStart,strTempAccountID;

					nStart=0;

					for (i = 0;  i < strClientAccountID.length;  i++)

					{

						var ch;

						ch = strClientAccountID.charAt(i);

						if ((ch=="/") )

						{

							strTempAccountID=strClientAccountID.substring(nStart,i);

							if(parseInt(strTempAccountID)==nAccountID)

							{

								return true;

							}

							nStart=i+1;

						}

					}

					if(i>0)

					{

						strTempAccountID=strClientAccountID.substring(nStart,i);

						if(parseInt(strTempAccountID)==nAccountID)

						{

							return true;

						}

						else

						{

							alert(strAccountCtrlTitle + "和" + strClientCtrlTitle + "不匹配，请重新选择。");

							return false;

						}

					}

				}

			}

			return true;

		}

		else

		{

			if(nAccountClientID!=nClientID)

			{

				alert(strAccountCtrlTitle + "和" + strClientCtrlTitle + "不匹配，请重新选择。");

				eval(strFormName + "." + strAccountCtrlName + "Ctrl.focus()");

				return false;

			}

			else

			{

				return true;

			}

		}

	}

	else

	{

		return true;

	}

}



/**

 * 校验帐户、客户

 * @param strFormName form的名称

 * @param strFixedDepositCtrlName 帐户控件名称

 * @param strFixedDepositCtrlTitle 帐户控件Title

 * @param strClientCtrlName 客户控件名称

 * @param strClientCtrlTitle 客户控件Title

 */

function checkFixedDepositClient(strFormName,strFixedDepositCtrlName,strFixedDepositCtrlTitle,strClientCtrlName,strClientCtrlTitle)

{

	

	//得到定期存单标识，定期存单对应客户标识，客户标识

	var nFixedDepositID,nFixedDepositClientID,nClientID;

	nFixedDepositID=eval(strFormName + "." + strFixedDepositCtrlName + ".value");

	nFixedDepositClientID=eval(strFormName + "." + strFixedDepositCtrlName + "ClientID.value");

	nClientID=eval(strFormName + "." + strClientCtrlName + ".value");

	

	if((nFixedDepositID>0) && (nClientID>0))//只有帐户和客户都选择了才检查

	{

		if(nFixedDepositClientID<0)//说明是从数据库里读出来的

		{

			return true;

		}

		else

		{

			if(nFixedDepositClientID!=nClientID)

			{

				alert(strFixedDepositCtrlTitle + "和" + strClientCtrlTitle + "不匹配，请重新选择。");

				eval(strFormName + "." + strFixedDepositCtrlName + "Ctrl.focus()");

				return false;

			}

			else

			{

				return true;

			}

		}

	}

	else

	{

		return true;

	}

}

/**

 * 校验银行、帐户

 * @param strFormName form的名称

 * @param strBankCtrlName 帐户控件名称

 * @param strBankCtrlTitle 帐户控件Title

 * @param strAccountCtrlName 客户控件名称

 * @param strAccountCtrlTitle 客户控件Title

 */

function checkBankAccount(strFormName,strBankCtrlName,strBankCtrlTitle,strAccountCtrlName,strAccountCtrlTitle)

{

	

	//得到帐户标识，帐户对应客户标识，客户标识

	var nBankID,nAccountID,strAccountBankID;

	nBankID=eval(strFormName + "." + strBankCtrlName + ".value");

	nAccountID=eval(strFormName + "." + strAccountCtrlName + ".value");

	strAccountBankID=eval(strFormName + "." + strAccountCtrlName + "BankID.value");

	if((nBankID>0) && (nAccountID>0))//只有都选择了才检查

	{

		if(strAccountBankID=="-1")//说明是从数据库里读出来的,初始化为这个数

		{

			return true;

		}

		else

		{

		  if(strAccountBankID=="")

		  {

				alert(strBankCtrlTitle + "和" + strAccountCtrlTitle + "不匹配，请重新选择。");

		    return false;

		  }

		  else

		  {

				var i,nStart,strTempBankID;

				nStart=0;

				for (i = 0;  i < strAccountBankID.length;  i++)

				{

					var ch;

					ch = strAccountBankID.charAt(i);

					if ((ch=="-") )

					{

						strTempBankID=strAccountBankID.substring(nStart,i);

						if(parseInt(strTempBankID)==nBankID)

						{

							return true;

						}

						nStart=i+1;

					}

				}

				if(i>0)

				{

					strTempBankID=strAccountBankID.substring(nStart,i);

					if(parseInt(strTempBankID)==nBankID)

					{

						return true;

					}

					else

					{

						alert(strBankCtrlTitle + "和" + strAccountCtrlTitle + "不匹配，请重新选择。");

						return false;

					}

				}

			}

		}

	}

	else

	{

		return true;

	}

}

/**

 * 校验帐户、客户

 * @param strFormName form的名称

 * @param lOfficeID 办事处标识

 * @param strPayAccountCtrlName 付款帐户控件名称

 * @param strPayAccountCtrlTitle 付款帐户控件Title

 * @param strGetAccountCtrlName 收款帐户控件名称

 * @param strGetAccountCtrlTitle 收款控件Title

 */

function checkPayGetAccount(strFormName,nOfficeID,strPayAccountCtrlName,strPayAccountCtrlTitle,strGetAccountCtrlName,strGetAccountCtrlTitle)

{

	

	//得到帐户标识，帐户对应办事处标识

	var nPayAccountID,nPayAccountOfficeID,nGetAccountID,nGetAccountOfficeID;

	nPayAccountID=eval(strFormName + "." + strPayAccountCtrlName + ".value");

	nPayAccountOfficeID=eval(strFormName + "." + strPayAccountCtrlName + "OfficeID.value");

	nGetAccountID=eval(strFormName + "." + strGetAccountCtrlName + ".value");

	nGetAccountOfficeID=eval(strFormName + "." + strGetAccountCtrlName + "OfficeID.value");

  

	if ((nPayAccountID>0) && (nGetAccountID>0))//只有两个帐户都选择了才做检查

	{

		if(nPayAccountID==nGetAccountID)

		{

			alert(strGetAccountCtrlTitle +"和" + strPayAccountCtrlTitle + "不能相同。");

			eval(strFormName + "." + strGetAccountCtrlName + "Ctrl.focus()");

			return false;

		}

		else

		{

			if((nPayAccountOfficeID<0) && (nGetAccountOfficeID<0))//说明是从数据库里读出来的

			{

				return true;

			}

			else

			{

				if(nPayAccountOfficeID<0)

				{

					if(nGetAccountOfficeID==nOfficeID)

					{

						return true;

					}

					else

					{

						alert("请重新选择" + strPayAccountCtrlTitle + "。");

						

						eval(strFormName + "." + strPayAccountCtrlName + "Ctrl.focus()");

						return false;

					}

					

				}

				else

				{

					if(nGetAccountOfficeID<0)

					{

						if(nPayAccountOfficeID==nOfficeID)

						{

							return true;

						}

						else

						{

							alert("请重新选择" + strGetAccountCtrlTitle + "。");

							

							eval(strFormName + "." + strGetAccountCtrlName + "Ctrl.focus()");

							return false;

						}

					}

					else

					{

						if((nGetAccountOfficeID==nOfficeID) || (nPayAccountOfficeID==nOfficeID))

						{

							return true;

						}

						else

						{

							alert(strGetAccountCtrlTitle + "和" + strPayAccountCtrlTitle + "不能都不属于本办事处，请重新选择。");

							eval(strFormName + "." + strPayAccountCtrlName + "Ctrl.focus()");

							return false;

						}

					}

				}

			}

		}

	}

	else

	{

		return true;

	}

}

/**

 * 检查下拉列表非空

 * @param d_input 控件

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkList(d_input,d_str)

{

	if(d_input.value<0)

	{

		alert("请选择一个" + d_str + "。");

		d_input.focus();

		return false;

	}	

	else

	{

		return true;

	}
}

/**

 * 校验利率

 * @param d_input  需要校验的form中的域

 * @param d_notnull 如果不能为空，填1，否则为0

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkRate(d_input,d_notnull,d_str)

{

	return InputValid(d_input,d_notnull,"float",1,0.0,100,d_str);

}

/**

 * 校验日期

 * @param d_input  需要校验的form中的域

 * @param d_notnull 如果不能为空，填1，否则为0

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkDate(d_input,d_notnull,d_str)

{

	if((d_input.value=="") && (d_notnull==0))

	{

		return true;

	}

	else

	{

		if(chkdate(d_input.value)==1)

			return true;

		else

		{

				alert("请输入正确的" + d_str + "。");

				d_input.focus();

				return false;

		}

	}		

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

		//alert("The Febryary never has this day!");

		return 0;

	}

	return 1;

}

/**

 * 检查时间期限

 * @param d_input 检查的输入框

 * @param strStartDate 起始日期

 * @param strInputName 输入框的名称

 */

function checkDateStart(d_input,strStartDate,strInputName)

{

	var temp,s;

	temp=new String(d_input.value);

	if(chkdate(temp)==0)

	{

		alert("请输入正确的" + strInputName + "。");

		d_input.focus();

		return false;

	}

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的" + strInputName + "。");

				d_input.focus();

				return false;

			}

			else

				s=s+temp.charAt(i);

		}

	}

	var dtDate;

	dtDate=new Date(s);

	if(dtDate.toString()=="NaN")

	{

		alert("请输入正确的" + strInputName + "。");

		d_input.focus();

		return false;

	}

	

	var dtStartDate;

	temp=new String(strStartDate);

	if(chkdate(temp)==0)

	{

		alert("请输入正确的开始日期。");

		d_input.focus();

		return false;

	}	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的开始日期。");

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtStartDate=new Date(s);

	if(dtStartDate.toString()=="NaN")

	{

		alert("请输入正确的开始日期。");

		return false;

	}

	if(dtDate.valueOf()<dtStartDate.valueOf())

	{

		alert(strInputName +"必须在" + strStartDate + "之后。");

		d_input.focus();

		return false;	

	}

	return true;

}

/**

 * 检查时间期限

 * @param d_input 检查的输入框

 * @param sttEndDate 结束日期

 * @param strInputName 输入框的名称

 */

function checkDateEnd(d_input,strEndDate,strInputName)

{

	var temp,s;

	temp=new String(d_input.value);

	if(chkdate(temp)==0)

	{

		alert("请输入正确的" + strInputName + "。");

		d_input.focus();

		return false;

	}

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的" + strInputName + "。");

				d_input.focus();

				return false;

			}

			else

				s=s+temp.charAt(i);

		}

	}

	var dtDate;

	dtDate=new Date(s);

	if(dtDate.toString()=="NaN")

	{

		alert("请输入正确的" + strInputName + "。");

		d_input.focus();

		return false;

	}

	var dtEndDate;

	temp=new String(strEndDate);

	if(chkdate(temp)==0)

	{

		alert("请输入正确的结束日期。");

		d_input.focus();

		return false;

	}

	s=new String("");

	for(var i=0;i<=temp.length-1;i++)

	{

		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

			s=s+"/";

		else

		{

			if(isNaN(Number(temp.charAt(i))))

			{

				alert("请输入正确的结束日期。");

				return false;

			}				

			else

				s=s+temp.charAt(i);

		}

	}

	dtEndDate=new Date(s);

	if(dtEndDate.toString()=="NaN")

	{

		alert("请输入正确的开始日期。");

		return false;

	}

	if(dtDate.valueOf()>dtEndDate.valueOf())

	{

		alert(strInputName +"必须在" + strEndDate + "之前。");

		d_input.focus();

		return false;	

	}

	return true;

}

/**

 * 检查字符非空

 * @param d_input  需要校验的form中的域

 * @param d_str  域的名称，比如“客户编号”，供提示信息使用

 */

function checkString(d_input,d_str)

{

	return InputValid(d_input,1,"str",0,0,0,d_str)

}

/**

 * 检查起息日和执行日

 * @param d_interest 起息日域

 * @param d_execute 执行日域

 * @param strSystemDate 系统日期

 */

function checkInterestExecuteDate(d_execute,strSystemDate)
{
	if(d_execute.value!="")
	{
		if(!isDate(d_execute.value))
		{
			alert("执行日格式不正确，请重新录入");
			d_execute.focus();
			return false;
		}
		else
		{
			
			var dtInterest,dtExecute,dtSystemDate;
			
			var temp,s;
			temp=new String(d_execute.value);
			if(chkdate(temp)==0)
			{
				alert("执行日格式不正确，请重新录入");
				d_execute.focus();
				return false;
			}
			s=new String("");
			for(var i=0;i<=temp.length-1;i++)
			{
				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
					s=s+"/";
				else
				{
					if(isNaN(Number(temp.charAt(i))))
					{
						alert("执行日格式不正确，请重新录入");
						d_execute.focus();
						return false;
					}				
					else
						s=s+temp.charAt(i);
				}
			}
			dtTwo=new Date(s);
			if(dtTwo.toString()=="NaN")
			{
				alert("执行日格式不正确，请重新录入");
				d_execute.focus();
				return false;
			}
			dtTwo=new Date(s);
			
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
					s=s+"/";
				else
				{
					if(isNaN(Number(temp.charAt(i))))
					{
						alert("请输入正确的系统日期。");
						return false;
					}				
					else
						s=s+temp.charAt(i);
				}
			}
			dtSystemDate=new Date(s);
			if(dtSystemDate.toString()=="NaN")
			{
				alert("请输入正确的系统日期。");
				return false;
			}
			dtSystemDate=new Date(s);
			
				if (dtSystemDate.valueOf()>dtTwo.valueOf())
				{
					alert("执行日不能早于今天");
					d_execute.focus();
					return false;
				}
		}
	}
	else
	{
		alert("执行日格式不正确，请重新录入");
		d_execute.focus();
		return false;
	}
	return true; 
}

/**

 * 检查起息日和执行日

 * @param d_interest 起息日域

 * @param d_execute 执行日域

 * @param strSystemDate 系统日期

 * 与checkInterestExecuteDate的区别：出错后，不指定页面焦点

 */

function checkInterestExecuteDate1(d_interest,d_execute,strSystemDate,nType,nDays)

{

	if(!isDate(d_interest.value))

	{

		alert("请填写正确的起息日。");

		d_interest.focus();

		return false;

	}

	if(d_execute.value!="")

	{

		if(!isDate(d_execute.value))

		{

			alert("执行日格式不正确，请重新录入");

			//d_execute.focus();

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

				d_interest.focus();

				return false;

			}

			s=new String("");

			for(var i=0;i<=temp.length-1;i++)

			{

				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						alert("请输入正确的起息日。");

						d_interest.focus();

						return false;

					}				

					else

						s=s+temp.charAt(i);

				}

			}

			dtOne=new Date(s);

			if(dtOne.toString()=="NaN")

			{

				alert("请输入正确的起息日");

				d_interest.focus();

				return false;

			}

			temp=new String(d_execute.value);

			if(chkdate(temp)==0)

			{

				alert("执行日格式不正确，请重新录入");

				//d_execute.focus();

				return false;

			}

			s=new String("");

			for(var i=0;i<=temp.length-1;i++)

			{

				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						alert("执行日格式不正确，请重新录入");

						//d_execute.focus();

						return false;

					}				

					else

						s=s+temp.charAt(i);

				}

			}

			dtTwo=new Date(s);

			if(dtTwo.toString()=="NaN")

			{

				alert("执行日格式不正确，请重新录入");

				//d_execute.focus();

				return false;

			}

			dtTwo=new Date(s);

			if(dtOne.valueOf()>dtTwo.valueOf())

			{

				alert("起息日不能大于执行日。");

				//d_execute.focus();

				return false;

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

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						alert("请输入正确的系统日期。");

						return false;

					}				

					else

						s=s+temp.charAt(i);

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

					alert("执行日不能早于今天");

					//d_execute.focus();

					return false;

				}

			}

			else

			{

				if(dtSystemDate.valueOf()!=dtTwo.valueOf())

				{

					alert("执行日必须是今天才可以保存。");

					//d_execute.focus();

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

				    	//d_execute.focus();

				    	return false;

				    }

			   }

			}

		}

	}

	else

	{

		alert("执行日格式不正确，请重新录入");

		d_execute.focus();

		return false;

	}

	return true; 

}

 /**

 * 检查合同日期起始和结束

 * @param d_interest 合同起始日期  

 * @param d_execute 合同结束日期   strSystemDate

 */

function checkContractStartEndDate(d_interest,d_execute)

{

	if(!isDate(d_interest.value))

	{

		alert("请填写正确的起始日期。");

		d_interest.focus();

		return false;

	}

	if(d_execute.value!="")

	{

		if(!isDate(d_execute.value))

		{

			alert("请填写正确的结束日期");

			d_execute.focus();

			return false;

		}

		else

		{
			var dtInterest,dtExecute,dtSystemDate;
			var temp,s;

			temp=new String(d_interest.value);

			if(chkdate(temp)==0)

			{

				alert("请输入正确的起始日期。");

				d_interest.focus();

				return false;

			}

			s=new String("");

			for(var i=0;i<=temp.length-1;i++)

			{

				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						alert("请输入正确的起始日期。");

						d_interest.focus();

						return false;

					}				

					else

						s=s+temp.charAt(i);

				}

			}

			dtOne=new Date(s);

			if(dtOne.toString()=="NaN")

			{

				alert("请输入正确的起始日期");

				d_interest.focus();

				return false;

			}

			temp=new String(d_execute.value);

			if(chkdate(temp)==0)

			{

				alert("请输入正确的起始日期。");

				d_execute.focus();

				return false;

			}

			s=new String("");

			for(var i=0;i<=temp.length-1;i++)

			{

				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						alert("请输入正确的起始日期");

						d_execute.focus();

						return false;

					}				

					else

						s=s+temp.charAt(i);

				}

			}

			dtTwo=new Date(s);

			if(dtTwo.toString()=="NaN")

			{

				alert("请输入正确的结束日期。");

				d_execute.focus();

				return false;

			}

			dtTwo=new Date(s);

			if(dtOne.valueOf()>dtTwo.valueOf())

			{

				alert("起始日期不能大于结束日期。");

				d_execute.focus();

				return false;

			}

		}

	}

	else

	{

		alert("请填写正确的结束日期。");

		d_execute.focus();

		return false;

	}

	return true; 

}

/**

* 校验函数

* @param d_input 需要校验的form中的域

* @param d_notnull 如果不能为空，填1，否则为0

* @param d_type 数据类型 "int" "float" "date" "string" "email" "fax" "zip" "floatplus" "stringplus"

* @param d_limited 是否限制范围 1限制，0不限制

* @param d_low 限制的低值

* @param d_up 限制的高值

* @param d_str 域的名称，比如“客户编号”，供提示信息使用

* @return 如果通过验证，返回真，否则返回假

* @ floatplus类型支持如“+12.89”的格式

* @ stringplus类型支持如中英文混合输入的字符串的长度校验
*/

function InputValid(d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str)

{

  if ( d_input.length >1 )

  {

    var obj=d_input;

    var m;

    m=d_input.length;

	 m=m.toString();

	 for( var i=0; i<m ; i++ )

	 {

		if( !InputValid_A( obj[i],d_notnull, d_type,d_limited, d_low, d_up, "第"+(i+1)+"项："+d_str ) ) {

		   return (false);

		}

	 }

  }

  else

  {

      if ( !InputValid_A( d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str ) )

   	   return false;

  }

  return true;

}


function InputValid_A( d_input,d_notnull, d_type,d_limited, d_low, d_up,d_str )

{

// not null

   if ( d_notnull==1 && d_input.value.length ==0 ) 

    {

       alert("请输入" + d_str );

       d_input.focus();

       return (false);

    }

    

// "int"

	if (d_type=="int")

	{

		if ( !isInt(d_input.value))

	    {

			alert( d_str+ "只能是整数");

			d_input.focus();

			return (false);

	    }

		if  ( d_input.value.length > 0 && d_limited==1 && !(d_low<=d_input.value && d_input.value <= d_up))

		{

			alert(d_str+ "的值必须在"+ d_low + "到"+ d_up +"之间。");

			d_input.focus();

			return (false);

		}

		return true;

	} 



// "float"

	if (d_type=="float")

	{

		if ( !isFloat(d_input.value))

	    {

			alert( d_str+"只能是数值" );

			d_input.focus();

			return (false);

	    }

	    if  ( d_limited==1 && d_input.value < d_low )

		{

			alert(d_str+ "的值不能小于"+ d_low );

			d_input.focus();

			return (false);

		}

	    if  ( d_limited==1 && d_input.value > d_up )

		{

			alert(d_str+ "的值不能大于"+ d_up );

			d_input.focus();

			return (false);

		}

		return true;

	}

// "floatplus"

	if (d_type=="floatplus")

	{

		var nData555;

		nData555 = d_input.value;

		if ( nData555.substring(0,1) == "+")

		{

			nData555 = nData555.substring(1);

		}	

		if ( !isFloat(nData555))

	    {

			alert( d_str+"只能是数值" );

			d_input.focus();

			return (false);

	    }

	    if  ( d_limited==1 && nData555 < d_low )

		{

			alert(d_str+ "的值不能小于"+ d_low );

			d_input.focus();

			return (false);

		}

	    if  ( d_limited==1 && nData555 > d_up )

		{

			alert(d_str+ "的值不能大于"+ d_up );

			d_input.focus();

			return (false);

		}

		return true;

	}

// "string"

	if (d_type=="string")

	{

		if  (d_limited==1 && !(d_low<=d_input.value.length && d_input.value.length <= d_up))

		{

			alert(d_str+ " 的长度必须在"+ d_low + "到"+ d_up +"个字符之间。");

         d_input.focus();

			return (false);

		}

		if  (d_input.value.indexOf("'")>0)

		{

			alert(d_str+ "中不能包含非法字符！");

         d_input.focus();

			return (false);

		}

		return (true);

	}    

//"stringplus"  可处理中英文混合的字符串的长度校验

	if(d_type=="stringplus")
	
	{

	d_input.value=Trim(d_input.value);

	var yylength=strlen(d_input.value);


	if  (d_limited==1 && !(d_low<=yylength && yylength <= d_up))

		{       
            		if  (d_low!=d_up)	{

				if(d_low!=0)	{
                        			alert("您所输入的"+d_str+"长为："+yylength+"个字符，但长度应该限制在"+ d_low + "到"+ d_up +"个字符之间。");

									d_input.focus();

									return false;
						}
				else		{
			
									alert("您所输入的"+d_str+"长为："+yylength+"个字符，但长度应该限制在"+ d_up +"个字符以内。");
				
									d_input.focus();

									return false;

						}
				
			}
			else {
					alert("您所输入的"+d_str+"长为："+yylength+"个字符，但长度应该限制为"+d_up+"个字符!");

					d_input.focus();

					return false;
                 	}

		}
       
		return (true);
	}
	
	
	
// "date"

	if (d_type=="date")

	{

	    if ( (!isDate(d_input.value)) || (d_input.value.length != 10) )

	    {

			alert("请在"+d_str+"处输入如下的日期形式：2000-08-08");

			d_input.focus();

			return (false);

	    }	

	    return (true);

	}



// "email"

	if (d_type=="email")

	{

	   if (d_notnull==0 && d_input.value.length==0) return (true);

       if ( !isEmail(d_input.value))

	    {

			alert("请在 "+d_str+"处输入正确的Email地址。");

			d_input.focus();

			return (false);

	    }	

		return (true);

	}



// "fax"

	if (d_type=="fax")

	{

		//is int

	    if ( !isFax(d_input.value))

	    {

			alert(d_str+" 只能输入数字和'- '");

			d_input.focus();

			return (false);

	    }

		//limit

		if  ( d_limited==1 && !(d_low<=d_input.value.length && d_input.value.length <= d_up))

		{

			alert(d_str+ "的长度只能在 "+ d_low + " 和 "+ d_up +" 之间.");

         d_input.focus();

			return (false);

		}

		return true;  

	}



     // auto

	if (d_type=="auto")

	{

		//limit

		if  ( d_input.value==0 )

		{

			alert( "请输入 " + d_str );

			return (false);

		}

		return true;  

	} 

	

// "zip"

	if (d_type=="zip")

	{

	    if ( !isInt(d_input.value) )

	    {

			alert(d_str+"只能是整数");

			d_input.focus();

			return (false);

	    }

	    else

	    {

	    	if(parseInt(d_input.value)<0)

	    	{

	    		alert(d_str + "不能是负数");

	    		d_input.focus();

	    		return (false);

	    		

	    	}

	    }

		if  ( d_limited==1 ){

			if ( (d_low == d_up)&& (d_input.value.length != d_low) && (d_input.value.length > 0) ) {

				alert( d_str+ "的长度只能是 "+ d_low +" 位." );

         	d_input.focus();

				return (false);

			}

			else {

				if ( (d_low < d_input.value.length && d_input.value.length < d_up))

				{

					alert(d_str+ "的长度只能在 "+ d_up +" 位以内.");

         		d_input.focus();

					return (false);

				}

			}

		}

		return true;  

	}

	return (true);

}

/**

* 计算长度函数:计算字符串长度（字符的个数）

* @param str 需要计算长度的字符串

* @param d_hzsize 一个汉字占两个字符数，英文占一个字符

* @return len 字符串长度

*/
function strlen(str) 
{ 
	var i; 
	var len; 
	str=Trim(str);
	len = 0; 
	for(i=0;i<str.length;i++)
	{ 
		if (str.charCodeAt(i)>255) len+=2; else len++; 
	} 
	return len; 
} 

/**
* 校验是否Int整型类型
*
*/
function isInt( d_int)
{
//modify by wjliu --begin 2007/2/8 整数不应该包括","-"
	//var checkOK = "0123456789,-";
	var checkOK = "0123456789";
	// -- end
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

//modify by wjliu -- 去掉checkOK中的-和,
function isFloat( d_float)
{
		//modified by mzh_fu 2007/03/23 去掉逗号(如果是金额,在传入前进行反格式化,不应该有逗号)
		
		//modified by mzh_fu 2007/03/28 加上逗号(由于校验金额与校验普通浮点数都使用了这个方法,
		//且很多地方错误的采用InputValid()方法来校验金额,在传入金额前没有对金额进行反格式化)
		
	//	var checkOK = "0123456789-.";
		var checkOK = "0123456789,-.";
		//var checkOK = "0123456789.";
		
		var checkStr = Trim(d_float);


		var allValid = true;

		var decPoints = 0;

		var allNum = "";
		
		//added by mzh_fu 2007/03/22
		//modified by mzh_fu 2007/03/28 加上逗号的校验
		//|| checkStr==","  || checkStr=="-," || checkStr==",-" 
		if(checkStr=="." || checkStr=="-"  ||  checkStr.substring(0,2)=="-." ||  checkStr.substring(0,2)==".-" || checkStr==","  || checkStr=="-," || checkStr==",-" || checkStr==".," || checkStr==",." ){
			return false;
		}

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

			if ( (ch == '-') && (i!=0) )			

			{

				allValid = false;

				break;

			}			

			if (ch != ",")

				allNum += ch;				

			if (ch == ".")

				decPoints += 1;				

		}				

		if ( decPoints > 1 )

		{

			allValid = false;

		}

		return (allValid)

}

/**

* 校验日期 --
*/

function isDate( d_date)

{		



			var temp,s;

			temp=d_date;

			s=new String("");

			for(var i=0;i<=temp.length-1;i++)

			{

				if(temp.charAt(i)=="-" || temp.charAt(i)=="/")

					s=s+"/";

				else

				{

					if(isNaN(Number(temp.charAt(i))))

					{

						return false;

					}				

					else

						s=s+temp.charAt(i);

				}

			}

			dtOne=new Date(s);

			if(dtOne.toString()=="NaN")

			{

				return false;

			}

		return true;

}
/*
function isDate(sDate)
{ 
	
	var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	var iaDate = new Array(3);
	var year, month, day;
    //
	var lthdatestr
	if (sDate != "" && !sDate)
		lthdatestr= sDate.length ;
	else
		lthdatestr=0;
		
	var tmpy="";
	var tmpm="";
	var tmpd="";
	var status;
	status=0;
	if ( lthdatestr== 0)
		return 0

	
	for (i=0;i<lthdatestr;i++)
	{	if (sDate.charAt(i)== '-')
		{
			status++;
		}
		if (status>2)
		{
			//alert("Invalid format of date!");
			return 0;
		}
		if ((status==0) && (sDate.charAt(i)!='-'))
		{
			tmpy=tmpy+sDate.charAt(i)
		}
		if ((status==1) && (sDate.charAt(i)!='-'))
		{
			tmpm=tmpm+sDate.charAt(i)
		}
		if ((status==2) && (sDate.charAt(i)!='-'))
		{
			tmpd=tmpd+sDate.charAt(i)
		}
	}
	if(tmpy=="") return false;
	if(tmpm=="") return false;
	if(tmpd=="") return false;
    //
	if (arguments.length != 1) return false;
	iaDate = sDate.toString().split("-");
	if (iaDate.length != 3) return false;
	if (iaDate[0].length > 4 || iaDate[1].length > 2 || iaDate[2].length > 2) return false;
	if (iaDate[0] == "" || iaDate[1] == "" || iaDate[2] == "") return false;

	year = parseFloat(iaDate[0]);
	month = parseFloat(iaDate[1]);
	day=parseFloat(iaDate[2]);

	if (year < 1900 || year > 2100) return false;
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1]=29;;
	if (month < 1 || month > 12) return false;
	if (day < 1 || day > iaMonthDays[month - 1]) return false;
	return true;
} 
*/
/**
* 校验Email
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
* 校验传真
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

			allNum += ch;

		}

		return (allValid)

}

/**
* 打开新窗口
* theURL 新窗口地址
* winName 新窗口名称
* features 新窗口属性
*/
function MM_openBrWindow(theURL,winName,features) 

{ 

  window.open(theURL,winName,features);

}

/**

 * 填写Form的数据

 * @param formname

 * @param strMsg 

 */

function fillFormData(formname,strMsg)

{

	var nPoint=-1;

	nPoint=strMsg.indexOf("&");

	while(nPoint>=0)

	{

		var strNameData=strMsg.substring(0,nPoint);

		var nEqual=strNameData.indexOf("=");

		if(nEqual>=0)

		{

			var strName=strNameData.substring(0,nEqual);

			var strData=strNameData.substring(nEqual+1,strNameData.length);

			var sType=typeof(eval(formname + "." + strName ));

			if(sType=="object")

			{

				eval(formname+"." + strName + ".value='" + strData +"'");

			}

		}

		strMsg=strMsg.substring(nPoint+1,strMsg.length);

		nPoint=strMsg.indexOf("&");

	}

	if(strMsg!="")

	{

		var strNameData=strMsg;

		var nEqual=strNameData.indexOf("=");

		if(nEqual>=0)

		{

			var strName=strNameData.substring(0,nEqual);

			var strData=strNameData.substring(nEqual+1,strNameData.length);

			var sType=typeof(eval(formname + "." + strName ));

			if(sType=="object")

			{

				eval(formname+"." + strName + ".value='" + strData +"'");

			}

		}

	}

}

/**

 * 填写Form的数据

 * @param formname

 * @param strMsg 

 */

function fillReturnFormData(formname,strMsg)

{

	var nPoint=-1;

	nPoint=strMsg.indexOf("&");

	var strNameData=strMsg;

	var nEqual=strNameData.indexOf("=");

	if(nEqual>=0)

	{

		var strName=strNameData.substring(0,nEqual);

		var strData=strNameData.substring(nEqual+1,strNameData.length);

		eval(formname+"." + strName + ".value='" + strData +"'");

	}

}
/*
==================================================================
LTrim(string):去除左边的空格
==================================================================
*/
function LTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
   if (whitespace.indexOf(s.charAt(0)) != -1)
    {
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}

/*
==================================================================
RTrim(string):去除右边的空格
==================================================================
*/
function RTrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
    {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
        {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}
/*
==================================================================
Trim(string):去除前后空格
==================================================================
*/
function Trim(str)
{
    return RTrim(LTrim(str));
}
/*
==================================================================
去除表单中文本框内的前后空格 
==================================================================
*/
function trimFormValue()
{
	for( i= 0; i < document.all.length; i++)
	{
		if (document.all[i].tagName == "INPUT" && document.all[i].type == "text")
		{
			document.all[i].value = Trim(document.all[i].value);
		}
	}
}
/**
 * 放大镜校验
 * @param strFormName 框架名称
 * @param lMagnifierID 放大镜隐藏ID(hiddenID)比较的是隐藏ID
 * @param returnFocus 焦点返回的控件名称
 * @param strMassage 输入错误的提示信息
 * @return true/false
 */
function checkMagnifier(strFormName,hdnMagnifier,returnFocus,strMassage)
{
	var lMagnifierID = eval(strFormName + "." + hdnMagnifier + ".value");
	if(lMagnifierID <= 0 ) 
	{
		alert("请输入正确的 "+ strMassage +"");
		eval(strFormName+"."+returnFocus+"."+"focus();");
		return false;  
	}
	else
	{
		return true;
	}
}



function checkMagnifierInput(d_input,d_str)
{
	gnIsSelectCtrl=1;
	if (!isNotSpecialString(d_input.value,d_str))
	{
		alert("放大镜输入字符不能含有特殊字符");
		d_input.focus();
		return (false);
	}
	return true;
}
function isNotSpecialString(d_int,d_str)
{
		var checkOK = "'\"";
		var checkStr = d_int;
		var allValid =  true;
		var decPoints = 0;
		var allNum = "";

		if(d_str.length > 0){ checkOK = d_str; }

		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			{
				if (ch == checkOK.charAt(j))	break;
			}
			if (j < checkOK.length)
			{
				allValid = false;
				break;
			}
			if (ch != ",")
			allNum += ch;
		}
		return (allValid);
}

//格式化日期
function formatedate(datestr)
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
	if(month.length == 1)
	{
		month = "0" + month;
	}
	if(day.length == 1)
	{
		day = "0" + day;
	}
	datestr = year+"-"+month+"-"+day;
	//alert("datestr----"+datestr);
	return datestr;
 }
 
 // 验证输入消息的字数
function checkStr(wordnumber, thename)
{
	var message = document.getElementById(thename).value;
	var wordNum = wordnumber;
	var spareStr = wordNum - getCharLength(message);
	if(getCharLength(message) > wordNum)
	{
	      if(navigator.userAgent.indexOf("Firefox") > 0)
	      {
	        document.getElementById(thename+"textAreaShow").textContent = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），<font color='red'>已经超出 <b>"+(getCharLength(message) - wordNum)+"</b> 个字符</font>";
	      }
	      else
	      {
			document.getElementById(thename+"textAreaShow").innerHTML = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），<font color='red'>已经超出 <b>"+(getCharLength(message) - wordNum)+"</b> 个字符</font>"; 
	      }
	}
	else if(getCharLength(message) >= 1)
	{
	    if(navigator.userAgent.indexOf("Firefox") > 0)
	      {
	        document.getElementById(thename+"textAreaShow").textContent = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+spareStr+"</b> 个字符";
	      }
	      else
	      {
	        document.getElementById(thename+"textAreaShow").innerHTML = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+spareStr+"</b> 个字符";
	      }
	}
	else if(getCharLength(message) == 0)
	{
	    if(navigator.userAgent.indexOf("Firefox") > 0)
	      {
	        document.getElementById(thename+"textAreaShow").textContent = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+ wordNum +"</b> 个字符";
	      }
	      else
	      {
	        document.getElementById(thename+"textAreaShow").innerHTML = "最多<b>"+wordNum+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+ wordNum +"</b> 个字符";
	      }
	}
}

function checkStrMessage(wordnumber, thename){

	var message = document.getElementById(thename).value;
	if(getCharLength(message)>wordnumber){
		document.getElementById(thename).focus();
		alert("最大输入" + wordnumber + "个字节（相当于"+wordnumber /2+"个汉字）！");
		document.getElementById(thename).focus();
	}

}

function checkStrEnter(wordnumber, thename){

	var e = window.event;
	var message = document.getElementById(thename).value;
	if(getCharLength(message)>wordnumber){
		if(e.keyCode==13) e.keyCode=9;
		document.getElementById(thename).focus();
	}

}
 
 //检验并限制输入的字符数（一个字母为一个字节，一个汉字为两个字节）
 function limitLength(value, maxLength, title, attribute) {
	var currentStr="";
    for(var i=0,len=value.length;i<len;i++) {
         currentStr+=value.charAt(i);
         if(getCharLength(currentStr)>maxLength){
            value=value.substr(0,i);
     		document.getElementById(attribute).value = value;
	 		alert(title + "最大输入" + maxLength + "个字节（相当于"+maxLength /2+"个汉字）！");
	 		document.getElementById(attribute).focus();
            return;
         }else{
	         document.getElementById(attribute+"textAreaShow").innerHTML=maxLength-getCharLength(currentStr);
         }
     }
     if(value.length == 0){
     	document.getElementById(attribute+"textAreaShow").innerHTML=maxLength;
     }
 }
 
 //得到字符串的字节数
 function getCharLength(str){
     var charLen=0;
	  for(var i=0,len=str.length;i<len;i++){
	       if(str.charCodeAt(i)>255){
	        charLen+=2;
	    }else{
	        charLen+=1;
	    }  
	  }
  return charLen;
}

 /*
	获取光标
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
	定位光标
	*/
 function setCursorPos(obj,pos){
	var rng =obj.createTextRange();
	rng.moveStart('character',pos);
	rng.collapse(true);
	rng.select();
}

/* 说明: 部分引起文本域错误的半角标点符号转为全角标点
  * obj:要转换的对象 
  * attribute: 控件名称
  */
 function halfTurnFull(obj,attribute) {
	var lValue = obj.value;
	var pos=getCursorPos(obj);//保存原始光标位置
	if(lValue.indexOf("'")!=-1){lValue=lValue.replace(/'/g,"＇");}//39
	if(lValue.indexOf("\"")!=-1){lValue=lValue.replace(/\"/g,"＂");}//34
	if(lValue.indexOf("<")!=-1){lValue=lValue.replace(/</g,"＜");}//60
	if(lValue.indexOf(">")!=-1){lValue=lValue.replace(/>/g,"＞");}//62
	if(lValue.indexOf("\\")!=-1){lValue=lValue.replace(/\\/g,"＼");}//92
	if(lValue.indexOf("/")!=-1){lValue=lValue.replace(/\//g,"／");}//47
	document.getElementById(attribute).value = lValue;
	setCursorPos(obj,pos);//设置光标
	return; 
} 

//add by xlchang 2010-11-29
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
				var next =false;  
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
				next=true;
			}
			//modify by wjliu 由于现在产品化3.2中的放大镜首位均有值,便从放大镜的第二个输入框开始判断 14:12 2007-3-13
			if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value != '')
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];
				
					//帐户号放大镜专用
					if(form[oAllFields[x][0]+'CtrlCtrl2'] && focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}			
				fields[i++] = oAllFields[x][1]+"不能为空，请从放大镜中选择！";
				bValid = false;
				next=true;
			}
			
			if(next==false)
			{
				if (!isInt1(form[oAllFields[x][0]].value) )
				{	
					if (i == 0) 
					{
						focusField = form[oAllFields[x][0]];
					}
					fields[i++] = oAllFields[x][1]+"的数据不正确，请从放大镜中选择！";
					bValid = false;	
				}
			}
		}

		//判断是否允许为空
		if ((bIsFormControl == true && oAllFields[x][3] == 1 && Trim(form[oAllFields[x][0]].value) == '') ||
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

	/*
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
	*/
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
			if (!isInt1(form[oAllFields[x][0]].value))
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
					if(reverseFormatAmount(form[oAllFields[x][0]].value) <= 0)
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
					if(reverseFormatAmount(form[oAllFields[x][0]].value) < 0)
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
		//如果是textarea类型，则判断字符的长度是否小于等于100bytes
		if (bIsFormControl == true && oAllFields[x][2] == 'textMaxlength' && form[oAllFields[x][0]].value != '') {
			var strValue = form[oAllFields[x][0]].value;
			//strValue = strValue.replace(/(<.;,*?!>)/ig,'1');
			strValue = strValue.replace(/([\u0391-\uFFE5])/ig,'11');
			if (strValue.length>80) {
				if (i == 0) {
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"不能多于40个中文或80个英文，请重新录入！";
				bValid = false;			
			}
		}
	}
	if (fields.length > 0) 
	{  
	    if(focusField.type!='hidden'){
		    //added by mzh_fu 2007/08/31
		    var vDisabled = focusField.getAttribute("disabled");
		    if(vDisabled==false)
				focusField.focus();
		}
		alert(fields.join('\n'));
	}
	return bValid;
}

/**
* 校验是否Int整型类型
* add by xlchang 2010-11-29 拷贝至isInt,但是包括负号
*/
function isInt1( d_int)
{
	var checkOK = "0123456789-";
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