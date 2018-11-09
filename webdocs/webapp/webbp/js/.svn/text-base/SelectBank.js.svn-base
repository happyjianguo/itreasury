function showSupBranch()
{
    showSupBranch(-1)
}	

function showSupBranch(strSelectedId)
{
	var nSelectedId = -1;
	if(strSelectedId != null && !isNaN(strSelectedId))
	{
		nSelectedId = parseInt(strSelectedId);
	}

    for (m=supBranchSelect.options.length-1;m>=0;m--)
	{
  	    supBranchSelect.options.remove(m);
	}

	supBranchSelect.options[0] = new Option("--请选择--","-1");

	if(nSelectedId <= -1)
	{
		supBranchSelect.options[0].selected=true;
	}
	
    for (i = 1; i < supBranchGroup.length; i++)
    {
  	    supBranchSelect.options[i]=new Option(supBranchGroup[i].text,supBranchGroup[i].value);
		
		if(supBranchGroup[i].value == nSelectedId)
		{
			supBranchSelect.options[i].selected=true;
		}
  }
}


/**
*显示指定省份的城市
*param nStateId 省份的Id
*/
function showBranch(strStateId)
{
    showBranch(strStateId, -1);
}

/**
*显示指定省份的城市
*param nStateId 省份的Id
*/
function showBranch(strStateId, strSelectedId)
{
	var nStateId = -1;
	var nSelectedId = -1;
	if(strStateId != null && !isNaN(strStateId))
	{
		nStateId = parseInt(strStateId);
	}

	if(strSelectedId != null && !isNaN(strSelectedId))
	{
		nSelectedId = parseInt(strSelectedId);
	}

    for (m=branchSelect.options.length-1;m>=0;m--)
    {
  	    branchSelect.options.remove(m);
    }
    
    branchSelect.options[0] = new Option("--请选择--","-1");

    if(nStateId > 0)
	{
		for (i=0;i<branchGroup[nStateId].length;i++)
		{
			branchSelect.options[i+1]=new Option(branchGroup[nStateId][i].text,branchGroup[nStateId][i].value);

			if(branchGroup[nStateId][i].value == nSelectedId)
			{
				branchSelect.options[i+1].selected=true;
			}
		}
		if(nSelectedId <= -1 && branchSelect.options.length > 2)
		{
			branchSelect.options[0].selected=true;
		}
		else if (branchSelect.options.length = 2)
		{
			branchSelect.options[1].selected=true;
		}
	}
}