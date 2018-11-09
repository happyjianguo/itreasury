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
          alert("����ɾ��ԭ�еı���");
          return false;
       }
       
    }
	if (move(target, source))
	{
		
		if (target.options.length == 0)
		{
			//���ȫ��
			target.options[0] = new Option("", "");
			target.options[0].text = "";
			target.options[0].value = -1;
		}
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
			
			//��Ҫ���Ȩ�޵ĵط�������������ú�
			for (var n = len; n > m; n--) {
				target.options[n] = new Option("", "");
				target.options[n].text = target.options[n-1].text;
				target.options[n].value = target.options[n-1].value;
			}
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
//ȡ��Ȩ��ID
function getId(str)
{
	var tmpArray = str.split("$");
	return tmpArray[0];
}
//ȡ������
function getOrder(str)
{
	var tmpArray = str.split("$");
	return tmpArray[1];
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
