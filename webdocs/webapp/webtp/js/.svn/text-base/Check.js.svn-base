/**

 * 客户端校验 

 * 

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

	if(dtOne.valueOf()>=dtTwo.valueOf())

	{

		//alert(str); 

		return false;

	}

	else

		return true;

}

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

    

    if(d_notnull!=2)//不判断最小值

    {

    	if  ( ((d_notnull==1) || (d_input.value!=""))  && reverseFormatAmount1(d_input.value) < 0.01 )

    	{

			alert(d_str+ "的值不能小于0.01。" );

			d_input.focus();

			return (false);

	}

   }	

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

	return InputValid(d_input,d_notnull,"float",1,0.0,200,d_str);

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

		alert("The Febryary never has this day!");

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

function checkInterestExecuteDate(d_interest,d_execute,strSystemDate,nType,nDays)

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

			alert("请填写正确的执行日。");

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

				alert("请输入正确的执行日。");

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

						alert("请输入正确的执行日");

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

				alert("请输入正确的执行日。");

				d_execute.focus();

				return false;

			}

			dtTwo=new Date(s);

			if(dtOne.valueOf()>dtTwo.valueOf())

			{

				alert("起息日不能大于执行日。");

				d_execute.focus();

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

					alert("执行日不能在今天之前。");

					d_execute.focus();

					return false;

				}

			}

			else

			{

				if(dtSystemDate.valueOf()!=dtTwo.valueOf())

				{

					alert("执行日必须是今天才可以保存。");

					d_execute.focus();

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

				    	d_execute.focus();

				    	return false;

				    }

			   }

			}

		}

	}

	else

	{

		alert("请填写正确的执行日。");

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

			alert("请填写正确的执行日。");

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

				alert("请输入正确的执行日。");

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

						alert("请输入正确的执行日");

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

				alert("请输入正确的执行日。");

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

					alert("执行日不能在今天之前。");

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

		alert("请填写正确的执行日。");

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

 *

 */





/**

* 校验函数

* @param d_input 需要校验的form中的域

* @param d_notnull 如果不能为空，填1，否则为0

* @param d_type 数据类型 "int" "float" "date" "string" "email" "fax" "zip" "floatplus"

* @param d_limited 是否限制范围 1限制，0不限制

* @param d_low 限制的低值

* @param d_up 限制的高值

* @param d_str 域的名称，比如“客户编号”，供提示信息使用

* @return 如果通过验证，返回真，否则返回假

* @ floatplus类型支持如“+12.89”的格式

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

			alert( d_str+ "只能是数字");

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

			alert(d_str+ " 的长度必须在"+ d_low + "和"+ d_up +"之间。");

         d_input.focus();

			return (false);

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

			alert(d_str+"只能是数字");

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



function isFloat( d_float)

{

		var checkOK = "0123456789-,.";

		var checkStr = d_float;

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