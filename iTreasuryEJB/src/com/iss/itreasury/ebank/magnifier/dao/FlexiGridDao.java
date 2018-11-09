
package com.iss.itreasury.ebank.magnifier.dao;


import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.ebank.magnifier.bizlogic.PagerTools;
import com.iss.itreasury.ebank.magnifier.dataentity.PagerInfo;

public class FlexiGridDao extends ITreasuryDAO
{
	private long countNum = 0;
	
	public long getCountNum()
	{
		return countNum;
	}
	//public List getResultList(long startNum, long finishNum, String sql, String order, String orderMark, PagerInfo pagerInfo) throws Exception
	public List getResultList(PagerInfo pagerInfo,Map paramMap) throws Exception
	{
		StringBuffer strSQL = null;
		List returnList = null;

		
		try
		{
			//获取查询相关信息
			String sql = pagerInfo.getSqlString();
			long page = Integer.parseInt((String)paramMap.get("page"));
			long lRp = Integer.parseInt((String)paramMap.get("lRp"));
			String order = (String)paramMap.get("order");
			String orderMark = (String)paramMap.get("orderMark");	
			long startNum = (page-1)*lRp;// 计算查询开始数据下标
			long finishNum = startNum + lRp;
			
			/*----------------- init DAO --------------------*/
			try
			{
				initDAO();
			}catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append(" select count(*) countNum from ("+ sql +")");
				System.out.println("放大镜查询sql="+strSQL.toString());
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
				while (transRS.next())
				{
					countNum = transRS.getLong(1);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}
			
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append("select * from (");
				strSQL.append("  select t.*, rownum as rownum1 from (");
				strSQL.append("    select * from (" + sql + ")");
				if(order != null && !order.equals("")){
					strSQL.append("    order by "+ order + " " + orderMark);
				}
				strSQL.append("  ) t where rownum<= " + finishNum);
				strSQL.append(") where rownum1 > " + startNum);
				System.out.println("放大镜查询sql="+strSQL.toString());
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	returnList = PagerTools.convertResultSetToJSONListEx(transRS, pagerInfo);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}

			 /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		finally {
			finalizeDAO();
		}
		return returnList;
	}
	
}
