/**
 * �ļ�����SettCheck.js
 * �ͻ��� У�� 
 * author:Forest
 * date:2003-09-17
 * ������У�麯����
 * 1,checkIsSelect У��Ŵ󾵵������Ƿ���ѡ��
 * 2,validateFields У��ҳ������Ԫ�أ��������Ƿ����룬�����Ƿ�ƥ�䣩
 * 2-1,validateFieldsRate У��ҳ������Ԫ�أ��������Ƿ����룬�����Ƿ�ƥ�䣬�˷����ɶ������浥��ʹ�ã�
 * 3,checkIsMatch У��Ŵ��Ƿ�ƥ�䣨���磬�ͻ����ʻ��Ƿ�ƥ�䣩
 * 4,checkInterestExecuteDate �����Ϣ�պ�ִ����
 * 5,compareDate �Ƚ��������ڴ�С
 * 6,isInt У���Ƿ�Int��������
 * 6-1,isDouble У���Ƿ�Double���ͣ��˷����ɶ������浥��ʹ�ã�
 * 7,isDate У���Ƿ�������
 * 8,isEmail У���Ƿ���ʵ�ַ
 * 9,isFloat У���Ƿ�Float����
 * 10,isFax У���Ƿ���(�绰)����
 * 11,reverseFormatAmount �����ʽ�����(�����е�","ȥ��)
 * 12,submitForm �ύ����
 * 13,isPostalcode У���Ƿ���������
 * 14,checkClientAccount У��ͻ����ʻ��Ƿ�ƥ�䣨����ҳ���������ʻ��ͻ��Ŵ�������������
 * 15,checkClientContract У��ͻ��ͺ�ͬ�Ƿ�ƥ�䣨����ҳ���������ʻ��ͻ��Ŵ�������������
 * 16,checkContractPayForm У���ͬ�ͷſ�֪ͨ���Ƿ�ƥ�䣨����ҳ���������ʻ��ͻ��Ŵ�������������
 * 17,checkAccountDepositNo У���ʻ���浥�Ŵ󾵵������Ƿ�ƥ�䣨����ҳ���������ʻ��ͻ��Ŵ�������������
 * 18,checkRequireClientBankBill У������ͻ�������Ʊ�ݵ������Ƿ�ƥ�䣨����ҳ���������ʻ��ͻ��Ŵ�������������
 * 19,addDate ���Ӷ��ڴ������(���ڿ���ר��)
 * 20,round	�õ���ֵ��ָ��С��λ����������ֵ
 * 21,checkFreeFormPayForm У���⻻֪ͨ���ͷſ�֪ͨ���Ƿ�ƥ�䣨��
 * 22,checkAheadRepayFormPayForm У����ǰ����֪ͨ���ͷſ�֪ͨ���Ƿ�ƥ�䣨��
 * 23,checkMagnifierInput У��Ŵ�������Ƿ��������ַ�
 * 24,isNotSpecialString У��Ŵ�������Ƿ��������ַ�
 * 25,formatDateString ��ʽ������(xxxx-xx-xx)
 * 26,checkSpanMonth ����������Ƿ����
 * 27,chkdate ����Ƿ�Ϊ����
 * 28,LTrim ȥ����ߵĿո�
 * 29,RTrim ȥ���ұߵĿո�
 * 30,Trim ȥ���������ߵĿո�
 */


/**
 * �ύ����
 * @param form ������
 */
function submitForm(form)
{
	showsending();
	form.submit();
} 
/**
 * ���Ӷ��ڴ������(���ڿ���ר��)
 * strInputDate ����
 * lMonth ����
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
   				alert("��������ȷ�Ŀ�ʼ����");
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
 * �����ʽ�����(�����е�","ȥ��)
 * @param strData ��Ҫ��ʽ��������
 * @return ���ط���ʽ���Ľ��
 */ 
function reverseFormatAmount(strData)
{
	var i,strTemp;
	//ȥ�����е�"," 
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
 * У��Ŵ󾵵������Ƿ���ѡ��
 * @param form ������
 * @param sIDControl �Ŵ����صı�ʶֵ��Ӧ�Ŀؼ�
 * @param sValueControl �Ŵ���Ҫ�ؼ���ֵ��Ӧ�Ŀؼ�
 * @param sMagnifierDesc �Ŵ�����
 * Return: �ǣ�����true; �񣬷���false.
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
		//alert("��������Ч�ķŴ󾵱�ʶֵ");
		if (sValue != "" || sValue.length > 0)
		{
			if (nID <= 0)
			{
				alert(sMagnifierDesc+"����ӷŴ���ѡ��");
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
				alert(sMagnifierDesc+"����ӷŴ���ѡ��");
				return false;
			}
		}		
	}
	return true;
}

/**
 * У��������Ƿ�����,��ʽ�Ƿ���ȷ
 * �������ͣ�
 *	date, ���ڣ���ʽ2003-11-05
 *	money, ������Ľ��
 *	int, 
 *	string��
 *	magnifier��
 *	email��
 *	fax��
 *	phone,
 *	postalcode,
 *	list,
 *	rate,
 *	allmoney, ���Դ����㣬С���㣬������Ľ��
 * 
 * allField��ʽ��this.aa = new Array("txtDate","�ɽ�����","date",1);
 * allFields������"txtDate" -- ����������
 *               "�ɽ�����"-- ����������
 *               "date"    -- ��������
 *               1         -- �Ƿ�����1���ǣ�0����
 * ����
 *     
 * function allFields()
 * {
 *		this.aa = new Array("txtBankNo","���б��","string",1);
 *		this.ab = new Array("txtDate","¼�����ڣ�","date",0);
 *		this.ac = new Array("txtAmount","���","money",1);
 *		this.ad = new Array("txtNumber","����","int",1);
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
		
		//����ǷŴ����ͣ��жϴ���Ŀؼ��Ƿ�����ֵ���͵ġ�
		if (bIsFormControl == true && oAllFields[x][2] == "magnifier")
		{	
				var next =false;  
			if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value == '')
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];
				
					//�ʻ��ŷŴ�ר��
					if(focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}			
				fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ӷŴ���ѡ��";
				bValid = false;
				next=true;
			}
			//modify by wjliu �������ڲ�Ʒ��3.2�еķŴ���λ����ֵ,��ӷŴ󾵵ĵڶ��������ʼ�ж� 14:12 2007-3-13
			if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value != '')
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];
				
					//�ʻ��ŷŴ�ר��
					if(form[oAllFields[x][0]+'CtrlCtrl2'] && focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}			
				fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ӷŴ���ѡ��";
				bValid = false;
				next=true;
			}
			
			if(next==false)
			{
				if (!isInt(form[oAllFields[x][0]].value) )
				{	
					if (i == 0) 
					{
						focusField = form[oAllFields[x][0]];
					}
					fields[i++] = oAllFields[x][1]+"�����ݲ���ȷ����ӷŴ���ѡ��";
					bValid = false;	
				}
			}
		}

		//�ж��Ƿ�����Ϊ��
		if ((bIsFormControl == true && oAllFields[x][3] == 1 && Trim(form[oAllFields[x][0]].value) == '') ||
			(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "list" && form[oAllFields[x][0]].value <= 0))
		{
			//alert (oAllFields[x][3]);
			if (i == 0) 
			{
				focusField = form[oAllFields[x][0]];
			}
			fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ѡ���¼�룡";
			bValid = false;
		}

	/*
		//����ǷŴ󾵣����ж��Ƿ��ǴӷŴ���ѡ�������
		if (bIsFormControl == true && oAllFields[x][2] == 'magnifier' && form[oAllFields[x][0]+'Ctrl'].value != '') 
		{
			if (form[oAllFields[x][0]].value <= 0)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];

					//�ʻ��ŷŴ�ר��
					if(focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}
				fields[i++] = oAllFields[x][1]+"�����ݣ���ӷŴ���ѡ��";
				bValid = false;			
			}
		}
	*/
		//��������ڣ����ж��Ƿ�����ȷ�����ڸ�ʽ
		if (bIsFormControl == true && oAllFields[x][2] == 'date' && form[oAllFields[x][0]].value != '') 
		{
			if (!isDate(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"��ʽ����ȷ��������¼�룡";
				bValid = false;			
			}
		}
		//������������ͣ����ж��Ƿ���ȷ����������;
		if (bIsFormControl == true && oAllFields[x][2] == 'int' && form[oAllFields[x][0]].value != '') 
		{
			//�ж��Ƿ���ȷ����������
			if (!isInt(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
		}
		//����ǽ�����ͣ����ж��Ƿ���ȷ�Ľ������
		if (bIsFormControl == true && oAllFields[x][2] == 'money' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
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
						fields[i++] = oAllFields[x][1]+"����С�ڵ����㣬������¼�룡";
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
						fields[i++] = oAllFields[x][1]+"����С���㣬������¼�룡";
						bValid = false;	
					}
				}

			}
		}
		//����ǽ�����ͣ����ж��Ƿ���ȷ�Ľ������
		if (bIsFormControl == true && oAllFields[x][2] == 'allmoney' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
		}		
		//������������ͣ����ж��Ƿ���ȷ����������
		if (bIsFormControl == true && oAllFields[x][2] == 'rate' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
			else if(form[oAllFields[x][0]].value < 0)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����С���㣬������¼�룡";
				bValid = false;		
			}
			else if(parseFloat(reverseFormatAmount(form[oAllFields[x][0]].value))>999)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"ֵ����������¼�룡";
				bValid = false;	
			}	
		}			
		//�����email���ͣ����ж��Ƿ���ȷ��email����
		if (bIsFormControl == true && oAllFields[x][2] == 'email' && form[oAllFields[x][0]].value != '') 
		{
			if (!isEmail(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����ΪEmail��ʽ��������¼�룡";
				bValid = false;			
			}
		}
		//�����fax���ͣ����ж��Ƿ���ȷ��fax����
		if (bIsFormControl == true && oAllFields[x][2] == 'fax' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����ΪFax��ʽ��������¼�룡";
				bValid = false;			
			}
		}	
		//�����phone���ͣ����ж��Ƿ���ȷ��phone����
		if (bIsFormControl == true && oAllFields[x][2] == 'phone' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ��Ч�ĵ绰���룬������¼�룡";
				bValid = false;			
			}
		}
		//�����postalcode���ͣ����ж��Ƿ���ȷ��������������
		if (bIsFormControl == true && oAllFields[x][2] == 'postalcode' && form[oAllFields[x][0]].value != '') 
		{
			if (!isPostalcode(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ��Ч���������룬������¼�룡";
				bValid = false;			
			}
		}
		//�����textarea���ͣ����ж��ַ��ĳ����Ƿ�С�ڵ���100bytes
		if (bIsFormControl == true && oAllFields[x][2] == 'textMaxlength' && form[oAllFields[x][0]].value != '') {
			var strValue = form[oAllFields[x][0]].value;
			//strValue = strValue.replace(/(<.;,*?!>)/ig,'1');
			strValue = strValue.replace(/([\u0391-\uFFE5])/ig,'11');
			if (strValue.length>80) {
				if (i == 0) {
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"���ܶ���40�����Ļ�80��Ӣ�ģ�������¼�룡";
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

function validateFieldsRate(form) 
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
		
		//����ǷŴ����ͣ��жϴ���Ŀؼ��Ƿ�����ֵ���͵ġ�
		if (bIsFormControl == true && oAllFields[x][2] == "magnifier")
		{	
				var next =false;  
			if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value == '')
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];
				
					//�ʻ��ŷŴ�ר��
					if(focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}			
				fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ӷŴ���ѡ��";
				bValid = false;
				next=true;
			}
			//modify by wjliu �������ڲ�Ʒ��3.2�еķŴ���λ����ֵ,��ӷŴ󾵵ĵڶ��������ʼ�ж� 14:12 2007-3-13
			if(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "magnifier" && form[oAllFields[x][0]].value <= 0 && form[oAllFields[x][0]+'Ctrl'].value != '')
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]+'Ctrl'];
				
					//�ʻ��ŷŴ�ר��
					if(form[oAllFields[x][0]+'CtrlCtrl2'] && focusField.type == 'hidden')
					{
						focusField = form[oAllFields[x][0]+'CtrlCtrl3'];
					}
				}			
				fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ӷŴ���ѡ��";
				bValid = false;
				next=true;
			}
			
			if(next==false)
			{
				if (!isDouble(form[oAllFields[x][0]].value) )
				{	
					if (i == 0) 
					{
						focusField = form[oAllFields[x][0]];
					}
					fields[i++] = oAllFields[x][1]+"�����ݲ���ȷ����ӷŴ���ѡ��";
					bValid = false;	
				}
			}
		}

		//�ж��Ƿ�����Ϊ��
		if ((bIsFormControl == true && oAllFields[x][3] == 1 && Trim(form[oAllFields[x][0]].value) == '') ||
			(bIsFormControl == true && oAllFields[x][3] == 1 && oAllFields[x][2] == "list" && form[oAllFields[x][0]].value <= 0))
		{
			//alert (oAllFields[x][3]);
			if (i == 0) 
			{
				focusField = form[oAllFields[x][0]];
			}
			fields[i++] = oAllFields[x][1]+"����Ϊ�գ���ѡ���¼�룡";
			bValid = false;
		}

		//��������ڣ����ж��Ƿ�����ȷ�����ڸ�ʽ
		if (bIsFormControl == true && oAllFields[x][2] == 'date' && form[oAllFields[x][0]].value != '') 
		{
			if (!isDate(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"��ʽ����ȷ��������¼�룡";
				bValid = false;			
			}
		}
		//������������ͣ����ж��Ƿ���ȷ����������;
		if (bIsFormControl == true && oAllFields[x][2] == 'int' && form[oAllFields[x][0]].value != '') 
		{
			//�ж��Ƿ���ȷ����������
			if (!isInt(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
		}
		//����ǽ�����ͣ����ж��Ƿ���ȷ�Ľ������
		if (bIsFormControl == true && oAllFields[x][2] == 'money' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
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
						fields[i++] = oAllFields[x][1]+"����С�ڵ����㣬������¼�룡";
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
						fields[i++] = oAllFields[x][1]+"����С���㣬������¼�룡";
						bValid = false;	
					}
				}

			}
		}
		//����ǽ�����ͣ����ж��Ƿ���ȷ�Ľ������
		if (bIsFormControl == true && oAllFields[x][2] == 'allmoney' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
		}		
		//������������ͣ����ж��Ƿ���ȷ����������
		if (bIsFormControl == true && oAllFields[x][2] == 'rate' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFloat(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ���֣�������¼�룡";
				bValid = false;			
			}
			else if(form[oAllFields[x][0]].value < 0)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����С���㣬������¼�룡";
				bValid = false;		
			}
			else if(parseFloat(reverseFormatAmount(form[oAllFields[x][0]].value))>999)
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"ֵ����������¼�룡";
				bValid = false;	
			}	
		}			
		//�����email���ͣ����ж��Ƿ���ȷ��email����
		if (bIsFormControl == true && oAllFields[x][2] == 'email' && form[oAllFields[x][0]].value != '') 
		{
			if (!isEmail(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����ΪEmail��ʽ��������¼�룡";
				bValid = false;			
			}
		}
		//�����fax���ͣ����ж��Ƿ���ȷ��fax����
		if (bIsFormControl == true && oAllFields[x][2] == 'fax' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����ΪFax��ʽ��������¼�룡";
				bValid = false;			
			}
		}	
		//�����phone���ͣ����ж��Ƿ���ȷ��phone����
		if (bIsFormControl == true && oAllFields[x][2] == 'phone' && form[oAllFields[x][0]].value != '') 
		{
			if (!isFax(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ��Ч�ĵ绰���룬������¼�룡";
				bValid = false;			
			}
		}
		//�����postalcode���ͣ����ж��Ƿ���ȷ��������������
		if (bIsFormControl == true && oAllFields[x][2] == 'postalcode' && form[oAllFields[x][0]].value != '') 
		{
			if (!isPostalcode(form[oAllFields[x][0]].value))
			{
				if (i == 0) 
				{
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"����Ϊ��Ч���������룬������¼�룡";
				bValid = false;			
			}
		}
		//�����textarea���ͣ����ж��ַ��ĳ����Ƿ�С�ڵ���100bytes
		if (bIsFormControl == true && oAllFields[x][2] == 'textMaxlength' && form[oAllFields[x][0]].value != '') {
			var strValue = form[oAllFields[x][0]].value;
			//strValue = strValue.replace(/(<.;,*?!>)/ig,'1');
			strValue = strValue.replace(/([\u0391-\uFFE5])/ig,'11');
			if (strValue.length>80) {
				if (i == 0) {
					focusField = form[oAllFields[x][0]];
				}
				fields[i++] = oAllFields[x][1]+"���ܶ���40�����Ļ�80��Ӣ�ģ�������¼�룡";
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
 * У��Ŵ󾵵������Ƿ�ƥ��
 * @param nID �Ŵ����صı�ʶֵ
 * @param sValue �Ŵ���Ҫ�ؼ���ֵ
 * @param sMagnifierDesc �Ŵ�����
 * Return: �ǣ�����true; �񣬷���false.
 */
function checkIsMatch(nFirstID,nSecondID,sFirstDesc,sSecondDesc)
{
	if (isInt(nFirstID) && isInt(nSecondID))
	{
		//alert("��������Ч�ķŴ󾵱�ʶֵ");
		if (nFirstID != nSecondID)
		{
			alert(sFirstDesc+"��"+sSecondDesc+"��ƥ�䣬������ѡ��");
			return false;
		}
	}
	else
	{
		alert ("��������ȷ�ı�ʶ��");
		return false;
	}
	return true;
}

/**
 * У��ͻ����ͬ�Ŵ󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objClient �ͻ��Ŵ�����
 * @param objContract ��ͬ�Ŵ�����
 * @param sClientDesc �ͻ�����
 * @param sContractDesc ��ͬ����
 * Return: �ǣ�����true; �񣬷���false.
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
 * У���ͬ��ſ�֪ͨ���󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objContract ��ͬ�Ŵ�����
 * @param objPayForm �ſ�֪ͨ���Ŵ�����
 * @param sContractDesc ��ͬ����
 * @param sPayFormDesc �ſ�֪ͨ������
 * Return: �ǣ�����true; �񣬷���false.
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
 * У���ͬ��ſ�֪ͨ���󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objFreeForm �⻻֪ͨ���Ŵ�����
 * @param objPayForm �ſ�֪ͨ���Ŵ�����
 * @param sFreeFormDesc �⻻֪ͨ������
 * @param sPayFormDesc �ſ�֪ͨ������
 * Return: �ǣ�����true; �񣬷���false.
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
 * У���ͬ��ſ�֪ͨ���󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objAheadRepayForm ��ǰ����֪ͨ���Ŵ�����
 * @param objPayForm �ſ�֪ͨ���Ŵ�����
 * @param sAheadRepayFormDesc �⻻֪ͨ������
 * @param sPayFormDesc ��ǰ����֪ͨ������
 * Return: �ǣ�����true; �񣬷���false.
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
 * У������ͻ�������Ʊ�ݷŴ󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objAccount ����ͻ��Ŵ�����
 * @param objDepositNo ����Ʊ�ݷŴ�����
 * @param sAccountDesc ����ͻ�����
 * @param sDepositNoDesc ����Ʊ������
 * Return: �ǣ�����true; �񣬷���false.
 */
function checkRequireClientBankBill(form,objRequireClient,objBankBill,RequireClientDesc,sBankBillDesc)
{
	var strFormName =  form.name;
	var nBankBillRequireClientValue;
	var nRequireClientValue;
	eval("nBankBillRequireClientValue = " + strFormName + "." + objBankBill+ "RequireClientID.value");
	eval("nRequireClientValue = " + strFormName + "." + objRequireClient + ".value");
	return checkIsMatch(nRequireClientValue,nBankBillRequireClientValue,RequireClientDesc,sBankBillDesc);
}


/**
 * У���ʻ���浥�Ŵ󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objAccount �ʻ��Ŵ�����
 * @param objDepositNo �浥�Ŵ�����
 * @param sAccountDesc �ʻ�����
 * @param sDepositNoDesc �浥����
 * Return: �ǣ�����true; �񣬷���false.
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
 * У��ͻ����ʻ��Ŵ󾵵������Ƿ�ƥ��
 * @param form ҳ������
 * @param objAccount �ʻ��Ŵ�����
 * @param objClient �ͻ��Ŵ�����
 * @param sClientDesc �ͻ�����
 * @param sAccountDesc �ʻ�����
 * Return: �ǣ�����true; �񣬷���false.
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
 * �����Ϣ�պ�ִ����
 * @param d_interest ��Ϣ����
 * @param d_execute ִ������
 * @param strSystemDate ϵͳ����
 * @param nType ���ͣ�1���ݴ�ʱʹ�ã�����������ʱʹ�á�
 */

function checkInterestExecuteDate(d_interest,d_execute,strSystemDate,nType)
{
	var nDays = 7;

	if(!isDate(d_interest.value))
	{
		alert("����д��ȷ����Ϣ�ա�");
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
			alert("����д��ȷ��ִ���ա�");
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
				alert("��������ȷ����Ϣ�ա�");
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
						alert("��������ȷ����Ϣ�ա�");
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
				alert("��������ȷ����Ϣ��");
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
				alert("��������ȷ��ִ���ա�");
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
						alert("��������ȷ��ִ����");
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
				alert("��������ȷ��ִ���ա�");
				try
				{
					d_execute.focus();
				}
				catch (e)
				{}
				return false;
			}
			dtTwo=new Date(s);
			
			if (nType==1)//�ݴ�
			{
				if(dtOne.valueOf()>dtTwo.valueOf())
				{
				    if(!confirm("��Ϣ�մ���ִ���գ��Ƿ������"))
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
					alert("��Ϣ�ղ��ܴ���ִ���ա�");
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
				alert("��������ȷ��ϵͳ���ڡ�");
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
						alert("��������ȷ��ϵͳ���ڡ�");
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
				alert("��������ȷ��ϵͳ���ڡ�");
				return false;
			}
			dtSystemDate=new Date(s);
			if (nType==1)//�ݴ�
			{
				if (dtSystemDate.valueOf()>dtTwo.valueOf())
				{
					alert("ִ���ղ����ڽ���֮ǰ��");
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
					alert("ִ���ձ����ǽ���ſ��Ա��档");
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
				    if(!confirm("��Ϣ����ִ��������"+nDays+"�죬�Ƿ������"))
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
		alert("����д��ȷ��ִ���ա�");
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
* У���Ƿ�������
* ���ڸ�ʽΪ��2003-09-15��
* Return true,false
*/
/*
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
*/
function isDate(sDate)
{ 
	var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	var iaDate = new Array(3);
	var year, month, day;
    //
	var lthdatestr
	if (sDate != "")
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

/**
* У���Ƿ���ʵ�ַ
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
* У���Ƿ���(��绰)����
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
* У���Ƿ��������
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
* У���Ƿ�Float����
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
* У���Ƿ�Int��������
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
* У���Ƿ�Double���ͣ��˷���Ŀǰ�ɶ������浥��ʹ��
*
*/
function isDouble( d_int)
{
	var checkOK = "0123456789,.-";
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
 * �Ƚ��������ڴ�С
 * ���ڸ�ʽ��2003-09-18 �� 2003/09/18
 * ���dtStart>dtEnd������false,���򷵻�true
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

//��������chkdate

//���ܽ��ܣ�����Ƿ�Ϊ����

//����˵����Ҫ�����ַ���

//����ֵ��0����������  1��������

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
 *�����ֵ�С��λ������������
 *param number 	��Ҫ���������������
 *param deci	��Ҫ������С��λ��,С��λ�����ܶ���6λ,������λ������ʱ����������
 *return 		��������������,�������û��С��λ,���־��ȵ�����Ҫ��ľ����򷵻�ԭֵ,������벻�������ͷ���0
 */
function round(num,deci){
	if (isNaN(num)) return 0;			//�����������,����0
	
	var tmp			= "1";						//�м����
	var returnValue = new String(num);					//����ֵ
	var index 		= returnValue.indexOf(".");	//С����λ��
	var len 		= index + deci + 1;			//������ֵ�ĳ���
	var precision	= 0.0;						//��Ҫ�ľ���,��������׼ȷ�ķ���ֵ
	
	for (var n=0;n<deci;n++){					//ȡ����Ҫ��λ��
		tmp += "0";	
	}
	precision = parseFloat(tmp); 				//�����ֵ
	
	if (index != -1 && len<returnValue.length){
		var nextNum = returnValue.charAt(len);
		returnValue = returnValue.substring(0,len);
		if (parseInt(nextNum)>=5) returnValue = (parseFloat(returnValue)*precision+1)/precision;
	}

	return parseFloat(returnValue);
}
/*
==================================================================

�ַ�������

Trim(string):ȥ���ַ������ߵĿո�

==================================================================

*/

 

/*

==================================================================

LTrim(string):ȥ����ߵĿո�

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

RTrim(string):ȥ���ұߵĿո�

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

Trim(string):ȥ��ǰ��ո�

==================================================================

*/

function Trim(str)

{

    return RTrim(LTrim(str));

}


/**
 * 
 * У��Ŵ�������Ƿ��������ַ�
 * @param d_int
 * @param d_str ҪУ��������ַ�
 * 
 */
function checkMagnifierInput(d_input,d_str)
{
	gnIsSelectCtrl=1;
	if (!isNotSpecialString(d_input.value,d_str))
	{
		alert("�Ŵ������ַ����ܺ��������ַ�");
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

/**
 * ��ʽ������(xxxx-xx-xx)
 * @param strDate ��Ҫ��ʽ��������
 * @return ���ظ�ʽ��������
 */ 
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

//�������� checkSpanMonth
//���ܽ��ܣ�����������Ƿ����
//����˵����Ҫ�����ַ���
//����ֵ��0������  1������  -1�����ڲ�ͬ
function checkSpanMonth(dtBegin,dtEnd)
{
	var strBegin=new String(dtBegin.value);
	var strEnd=new String(dtEnd.value);
	if (strBegin != "")
	{
		if(chkdate(strBegin) == 0)
		{
			alert("��������ȷ�Ŀ�ʼ����");
			dtBegin.focus();
			return false;
		}
	}
    if (strEnd != "")
	{
		if(chkdate(strEnd) == 0)
		{
			alert("��������ȷ�Ľ�������");
			dtEnd.focus();
			return false;
		}
	}
	var lBegin,lEnd;
	if(strBegin!="")
		lBegin = strBegin.length;
	else lBegin=0;
	if(strEnd!="")
		lEnd = strEnd.length;
	else lEnd=0;

	var bYear="";
	var bMonth="";
	var bDay="";
	var eYear="";
	var eMonth="";
	var eDay="";
	var status;
	status=0;
	
	for (i=0;i<lBegin;i++)
	{	if (strBegin.charAt(i)== '-')
		{
			status++;
		}		
		if ((status==0) && (strBegin.charAt(i)!='-'))
		{
			bYear=bYear+strBegin.charAt(i)
		}
		if ((status==1) && (strBegin.charAt(i)!='-'))
		{
			bMonth=bMonth+strBegin.charAt(i)
		}
		if ((status==2) && (strBegin.charAt(i)!='-'))
		{
			bDay=bDay+strBegin.charAt(i)
		}
	}
	status=0;
	for (i=0;i<lEnd;i++)
	{	if (strEnd.charAt(i)== '-')
		{
			status++;
		}		
		if ((status==0) && (strEnd.charAt(i)!='-'))
		{
			eYear=eYear+strEnd.charAt(i)
		}
		if ((status==1) && (strEnd.charAt(i)!='-'))
		{
			eMonth=eMonth+strEnd.charAt(i)
		}
		if ((status==2) && (strEnd.charAt(i)!='-'))
		{
			eDay=eDay+strEnd.charAt(i)
		}
	}	
	var lbyear=new String (bYear);
	var lbmonth=new String (bMonth);
	var lbday=new String (bDay);
	var leyear=new String (eYear);
	var lemonth=new String (eMonth);
	var leday=new String (eDay);
	if(lbyear > leyear || lbyear < leyear)
		return 0;
	else if(lbmonth > lemonth || lbmonth < lemonth)
		return 1;
	else if(lbday > leday || lbday < leday)
		return -1;
	return -1;
}
/*
==================================================================
LTrim(string):ȥ����ߵĿո�
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
RTrim(string):ȥ���ұߵĿո�
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
Trim(string):ȥ��ǰ��ո�
==================================================================
*/
function Trim(str)
{
    return RTrim(LTrim(str));
}


/*
==================================================================
У������
==================================================================
*/
function checkInt(num)

	{
		if(isNaN(num))

		{
			return false;
		}
		if(num.indexOf(".") > 0)
		{	
			return false;
		}		
	
		return true;
		
	}
	
	/*
==================================================================
У��Ϊ�յ���λ
==================================================================
*inputName �ؼ�����
*str ��ʾ����λ���ƣ��磧��˾���ƣ�
*
*
*/
function checkBlankInput(inputName,str)
{
	if(Trim(inputName.value) == "")
	{
		alert(str + "����Ϊ��");
		inputName.focus();
		return false;
	}
}



/** 
 *�����ı���(TextArea)�����ַ��� 
 *objΪ�ı��������
 *lenΪ��Ҫ���Ƶĳ���
 *�÷�������ʵ�ֶ���Ӣ�ʵĻ�ϴ���
 *ʹ��ʱ���ı�����ʹ�� onPropertyChange="checkMaxInput(this,5)"�¼�
 *2009.3.18
 */

function getStringLength(str)
{
    return str.replace(/[^\x00-\xff]/g,"**").length;
}

function checkMaxInput(obj,len) {
	  
	 
    var m = getStringLength(obj.value);
    if (m > len) {
    for(i = obj.value.length; i > 0; i--) {
        if (getStringLength(obj.value.substring(0,i)) <= len) {
            break;
        }
    }
    obj.value = obj.value.substring(0,i);
    }
}