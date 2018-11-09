function SourceToTarget(source, target, hiddenTarget) 
{
	if ( move(source, target) )
	{
		for (var i=0;i < target.options.length;i++)
		{
			if (target.options[i].value == -1)
				target.options[i] = null;
		}
		setHiddenValue(target,hiddenTarget);
	}
	return false;
}
function TargetToSource(source,target,hiddenTarget,butt) 
{
    var str = butt.value;
    var tmpArray = str.split(",");
    for(var i=0;i<tmpArray.length;i++)
    {
       if(target.options.value == tmpArray[i])
       {
          alert("不能删除原有的币种");
          return false;
       }
       
    }
	if (move(target, source))
	{
		
		if (target.options.length == 0)
		{
			//填加全部
			target.options[0] = new Option("", "");
			target.options[0].text = "";
			target.options[0].value = -1;
		}
		setHiddenValue(target,hiddenTarget);
	}
	return false;
}
//两个Select互相转移的主程序
function move(source, target) 
{
	
	var checkValue = false;
	
	for (var i = 0; i < source.options.length; i++) 
	{
		if (source.options[i].selected) 
		{
			var len = target.options.length;
			
			var idValue = source.options[i].value;
			if (idValue == -1) continue;			

			var tmpNum = new Number(getOrder(idValue));
			for (var m = 0; m < target.options.length; m++) 
			{
				var tmpStr = target.options[m].value;
				var tmpNum2 = new Number(getOrder(tmpStr));
				
				if (tmpNum2 - tmpNum > 0)
				{
					break;
				}
			}
			
			//把要填加权限的地方后面的项依次置后
			for (var n = len; n > m; n--) {
				target.options[n] = new Option("", "");
				target.options[n].text = target.options[n-1].text;
				target.options[n].value = target.options[n-1].value;
			}
			//填加选中的权限
			target.options[m] = new Option("", "");
			target.options[m].text = source.options[i].text;
			target.options[m].value = source.options[i].value;
			//删除原权限
			//每删除一权限都要将i-1，因为会重新计算长度
			source.options[i] = null;
			i -= 1;
			checkValue = true;
			
		}
	}
  
	if (!checkValue) 
	{
	   alert("请选择!");
	   source.focus();
	}
	return checkValue;
}
//取出权限ID
function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}
//取出排序
function getOrder(str)
{
	var tmpArray = str.split("$");
	return tmpArray[1];
}
//设置隐藏hidden的值
function setHiddenValue(target,hiddenTarget)
{
	//先清空Hidden域
	hiddenTarget.value="";
	var strTmp = "";
	for(var i=0;i<target.options.length;i++)
	{
		var tmpValue = target.options[i].value;
		if (tmpValue == -1) continue;
		strTmp = strTmp + getId(tmpValue) + ",";
	}
	//去掉最后的逗号
	if (strTmp.length > 0)
		strTmp = strTmp.substring(0,strTmp.length-1);
	hiddenTarget.value = strTmp;
}
