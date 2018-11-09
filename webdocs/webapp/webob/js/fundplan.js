	/*
	 * �ж�ĳһ�����Ƿ�����ڱ��ʽ��
	 * @param strExpression ���ʽ�ַ���,����:P_11+P_2+p_3
	 * @param strParamName ������,����:P_11
	 * @return ����,����true,����,����false
	*/
	function isExistInExpression(strExpression, strParamName)
	{ 
		var strRegExp = new RegExp(strParamName + "([^0-9]|$)","");
		if(strExpression.search(strRegExp)>-1)
			return true;
			else return false;
	}	
	
	/*
	 * �÷������ʽ�ƻ�ĳһ��ֵ�ı��,������ص����й�ʽ�������¼���
	 * @param form��
	 * @param ֵ�����仯�ı�����������,����:P_11
	 * @param nType,��Ҫ�������	
	*/
	function sumExpression(strFormName, strCtrlName, nType)
	{	
		var ctrlNames = new Array();
		var operators = new Array();
		
		for(var k = 0; k < ar.length; k++)
	 	{
	 		var curExpression = ar[k];
	 		var strExp= curExpression[1];
	 		var indexstart = 0;
	 		
	 		var tempStr="";
	 			 		
	 		var result = 0.00;
	 		var preOperator = '+';
	 		
	 		/*�жϸ����Ƿ�����ڹ�ʽ��*/
	 		if(!isExistInExpression(strExp, strCtrlName))
	 		{
	 			continue;
	 		}
	 		
	 		/**�����ڹ�ʽ��,�����¼���ù�ʽ*/
	 		while(indexstart < strExp.length)
	 		{
	 			if(strExp.charAt(indexstart)!='+' && strExp.charAt(indexstart)!='-')
	 			{
	 				tempStr = tempStr + strExp.charAt(indexstart);
	 			}else{
	 				var ctrlValue = eval(strFormName + "." + tempStr + "[" +nType+ "].value");
	 				// modified by ylguo
	 				//alert(ctrlValue);
	 				if(ctrlValue==""||ctrlValue==null){
	 					ctrlValue = 0.00;
	 					
					}
	 				if(preOperator=='+')
			 		{
			 			var _result = result*1 + parseFloat(reverseFormatAmount1(ctrlValue));
			 			result = parseFloat(_result).toFixed(2);
						
			 		}else{
			 			var _result = result - parseFloat(reverseFormatAmount1(ctrlValue));
			 			result = parseFloat(_result).toFixed(2);
			 		}
			 		preOperator = strExp.charAt(indexstart);
			 		tempStr="";
	 			}
	 			
	 			indexstart++;
	 		}
	 		
	 		if(tempStr!=null && tempStr!='') 
	 		{
	 			var ctrlValue = eval(strFormName + "." + tempStr + "[" +nType+ "].value");	 		
				// modified by ylguo
	 				//alert(ctrlValue);
	 				if(ctrlValue==""||ctrlValue==null){
	 					ctrlValue = 0.00;
	 					
					}
				if(preOperator=='+')
				{	// modified by ylguo
					var _result = result*1 + parseFloat(reverseFormatAmount1(ctrlValue));
			 			result = parseFloat(_result).toFixed(2);
				}else{
					var _result = result*1 - parseFloat(reverseFormatAmount1(ctrlValue));
			 		result = parseFloat(_result).toFixed(2);
				}
			}
			
			eval(strFormName + "." + curExpression[0] + "[" + nType+ "]").value= result;
			adjustAmount(strFormName, curExpression[0]+ '[' + nType+ ']', 1, "","");
			sumExpression(strFormName, curExpression[0], nType);
			totalupOneRow(strFormName, curExpression[0], "",1);
	 	}
	}
	
	/*
	 * �����еĽ��
	 * @param strFormName
	 * @param strCtrlName �ؼ�����
	 * @param nType ����1,format;2,reverseFormat
	 */
	 function totalupOneRow(strFormName,strCtrlName,strChineseCtrl,nCurrencyID)
	 {
		if (window.event.keyCode!=13)
		{
			var arr = new Array();
			var strValue=0.00;
			var strSumValue = 0.00;
			
	    	for(var i=1; i<=5; i++)
	    	{
	    		strvalue = eval(strFormName + "." + strCtrlName + "[" + i + "]").value;
	    		// modified by ylguo
	 				//alert(ctrlValue);
	 				if(strvalue==""||strvalue==null){
	 					strvalue = 0.00;
	 				}
	    		var _strSumValue = strSumValue*1 + parseFloat(reverseFormatAmount1(strvalue));
	    		strSumValue = parseFloat(_strSumValue).toFixed(2);
	    	
	    	}
	    	
	    	eval(strFormName+"."+strCtrlName+"[0]").value=strSumValue;
	    	adjustAmount(strFormName,strCtrlName+"[0]",1, strChineseCtrl,nCurrencyID);
		 }
	 }
	 
	 //modified by ylguo,�������ֻ�������ʽ�ƻ��������������ʽ�ƻ�
	 function nextCtrlOnFocus(configId,coNext)
	 {
	 	var nextLineName = "P_" + ((configId*1)+1);// ��һ�е�����
	 	var nextCtrlName = "";//��һ�������ı��������
	 	var isThisLine = eval("document.getElementsByName('P_"+configId+"')[6].readOnly;");//���пɶ�д��
	 	if(typeof(eval("document.getElementsByName('"+nextLineName+"')[0]"))!="undefined"){
	 		var isNextLine = eval("document.getElementsByName('"+nextLineName+"')[6].readOnly;");//��һ�пɶ�д��
	 	}
	 	//��������ǿɶ�д�ģ�������һ��Ҳ�ǿɶ�д��
	 	if((!isThisLine) )
	 	{
	 		nextCtrlName = "P_"+configId+"["+coNext+"]";
	 		return nextCtrlName;
	 	}
	 	var TempConfigId = configId*1;
	 	if(coNext ==1){
	 			nextLine = "P_"+new String(TempConfigId);
	 			while(true)
	 			{
		 			nextLine = "P_"+new String(TempConfigId);
		 			//alert(nextLine);
		 			if(typeof(eval("document.getElementsByName('"+nextLine+"')[0]"))!="undefined")
		 			{
		 				var isReadOnly = eval("document.getElementsByName('"+nextLine+"')[6].readOnly;");
		 				if(isReadOnly)//�ǲ��ɶ�д���У�Ҳ����ͳ����   
		 				{	TempConfigId = (TempConfigId*1)+1
		 					nextLine = "P_"+new String(TempConfigId);
		 					nextCtrlName = nextLine+"[1]";
		 				}else{
							break;
		 				}
		 			}
					else{
						break;
					}
					
				}
	 			return nextCtrlName;
	 	}
}