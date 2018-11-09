package com.iss.itreasury.craftbrother.query.bizlogic;

import java.util.Collection;

import com.iss.itreasury.craftbrother.query.dao.QueryContractDao;
import com.iss.itreasury.util.Log4j;



public class QueryContractPerformBiz
{
	private static Log4j log4j = null;

	

	/**
	 * 执行合同执行情况查询
	 * @param lID 合同标示
	 * @param lParam 排序标志
	 * @param lDesc 升降序标志
	 * @return 
	 * @throws Exception
	 */
	public Collection queryContractPerform(long lID, long lParam, long lDesc) throws Exception
	{
		Collection c = null;
		QueryContractDao dao = new QueryContractDao();

		try
		{
			c = dao.queryContractPerform(lID, lParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return c;
	}



        	
	public static void main(String[] args)
	{
		try
		{
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}