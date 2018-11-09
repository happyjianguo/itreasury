	/*
	 * 判断某一变量是否存在于表达式中
	 * @param strExpression 表达式字符串,例如:P_11+P_2+p_3
	 * @param strParamName 变量名,例如:P_11
	 * @return 存在,返回true,否者,返回false
	*/
	function isExistInExpression(strExpression, strParamName)
	{ 
		var strRegExp = new RegExp(strParamName + "([^0-9]|$)","");
		if(strExpression.search(strRegExp)>-1)
			return true;
			else return false;
	}	
	
	/*
	 * 该方法对资金计划某一项值改变后,和它相关的所有公式进行重新计算
	 * @param form名
	 * @param 值发生变化的变量名或行名,例如:P_11
	 * @param nType,需要计算的列	
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
	 		
	 		/*判断该项是否存在于公式中*/
	 		if(!isExistInExpression(strExp, strCtrlName))
	 		{
	 			continue;
	 		}
	 		
	 		/**存在于公式中,则重新计算该公式*/
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
	 * 计算行的金额
	 * @param strFormName
	 * @param strCtrlName 控件名称
	 * @param nType 类型1,format;2,reverseFormat
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
	 
	 //modified by ylguo,这个方法只适用与资金计划，不可用于周资金计划
	 function nextCtrlOnFocus(configId,coNext)
	 {
	 	var nextLineName = "P_" + ((configId*1)+1);// 下一行的名称
	 	var nextCtrlName = "";//下一个焦点文本框的名称
	 	var isThisLine = eval("document.getElementsByName('P_"+configId+"')[6].readOnly;");//本行可读写性
	 	if(typeof(eval("document.getElementsByName('"+nextLineName+"')[0]"))!="undefined"){
	 		var isNextLine = eval("document.getElementsByName('"+nextLineName+"')[6].readOnly;");//下一行可读写性
	 	}
	 	//如果本行是可读写的，并且下一行也是可读写的
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
		 				if(isReadOnly)//是不可读写的行，也就是统计行   
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