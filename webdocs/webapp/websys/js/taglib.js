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
//����Select����ת�Ƶ�������
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
			//���ѡ�е�Ȩ��
			target.options[m] = new Option("", "");
			target.options[m].text = source.options[i].text;
			target.options[m].value = source.options[i].value;
			//ɾ��ԭȨ��
			//ÿɾ��һȨ�޶�Ҫ��i-1����Ϊ�����¼��㳤��
			source.options[i] = null;
			i -= 1;
			checkValue = true;
		}
	}

	if (!checkValue) 
	{
	   alert("��ѡ��!");
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
			//���ѡ�е�Ȩ��
			target.options[m] = new Option("", "");
			target.options[m].text = source.options[i].text;
			target.options[m].value = source.options[i].value;
			//ɾ��ԭȨ��
			//ÿɾ��һȨ�޶�Ҫ��i-1����Ϊ�����¼��㳤��
			source.options[i] = null;
			i -= 1;
			checkValue = true;
	}

	if (!checkValue) 
	{
	   alert("��ѡ��!");
	   source.focus();
	}
	return checkValue;
}
//ȡ��Ȩ��ID
function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}

//��������hidden��ֵ
function setHiddenValue(target,hiddenTarget)
{
	//�����Hidden��
	hiddenTarget.value="";
	var strTmp = "";
	for(var i=0;i<target.options.length;i++)
	{
		var tmpValue = target.options[i].value;
		if (tmpValue == -1) continue;
		strTmp = strTmp + getId(tmpValue) + ",";
	}
	//ȥ�����Ķ���
	if (strTmp.length > 0)
		strTmp = strTmp.substring(0,strTmp.length-1);
	hiddenTarget.value = strTmp;
}