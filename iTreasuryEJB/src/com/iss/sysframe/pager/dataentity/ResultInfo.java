package com.iss.sysframe.pager.dataentity;

import java.util.ArrayList;
import java.util.List;

public class ResultInfo
{
	private long total = 0;// 总行数
	private List resultList = new ArrayList();// 返回的结果集
	private Class resultClass = null;// 返回的结果集里的INFO类的CLASS

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public List getResultList()
	{
		return resultList;
	}

	public void setResultList(List resultList)
	{
		this.resultList = resultList;
	}

	public Class getResultClass()
	{
		return resultClass;
	}

	public void setResultClass(Class resultClass)
	{
		this.resultClass = resultClass;
	}
}
