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
function SourceToTargetAll(source,target,hiddenTarget)
{
	if ( moveAll(source, target) )
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
function TargetToSource(source, target,hiddenTarget) 
{
	if ( move(target, source) )
	{
		setHiddenValue(target,hiddenTarget);
	}
	return false;
}
function TargetToSourceAll(source, target,hiddenTarget) 
{
	if ( moveAll(target, source) )
	{
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
			var idValue = source.options[i].value;
			if (idValue == -1) continue;			
			var m = target.options.length;
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

function moveAll(source, target) 
{
	var checkValue = false;
	for (var i = 0; i < source.options.length; i++) 
	{
			var idValue = source.options[i].value;
			if (idValue == -1) continue;			
			var m = target.options.length;
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