/*
 * Created on 2004-1-18
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;

import java.math.BigDecimal;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.iss.itreasury.dataentity.PageInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PageStatement
{

	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this);
	private String countSQL, querySQL;
	private long pageNo, pageSize, startIndex, totalCount;
	private javax.sql.RowSet rowSet;
	private List boundParams;
	private int boundParamCount = 0;
	private Connection psConn = null;

	private PageInfo pInfo = null;

	public static HashMap hmCount = new HashMap();
	public static HashMap hmPageInfo = new HashMap();
	
	public static String sKey = "";

	/**     
	 * 获取任一页第一条数据在数据库中的位置     
	 * @param pageNo 页号     
	 * @param pageSize 每页包含的记录数     
	 * @return 记录对应的rownum     
	 */
	public long getStartOfAnyPage(long pageNo, long pageSize)
	{
		long startIndex = 1;

		if (pageNo < 1)
		{
			pageNo = 1;
		}

		startIndex = (pageNo - 1) * pageSize + 1;
		if (startIndex < 1)
		{
			startIndex = 1;
			return startIndex;
		}
		return startIndex;
	}

	/**     
			*生成查询一页数据的sql语句     
			*@param sql 原查询语句     
			*/
	public String intiOrderSQL()
	{
		String orderSQL = "";
		if (pInfo.getOrderBy() != null && !pInfo.getOrderBy().equals(""))
		{
			orderSQL = " ORDER BY " + pInfo.getOrderBy();
			if (pInfo.getDesc()==Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				orderSQL += " DESC ";
			}
		}
		return orderSQL;
	}

	/**     
		*生成查询一页数据的sql语句     
		*@param sql 原查询语句     
		*/
	public String intiQuerySQL(String sql)
	{
		StringBuffer querySQL = new StringBuffer();

		querySQL.append("SELECT * FROM ");
		querySQL.append(" (SELECT my_table.*,rownum as my_rownum");
		querySQL.append(" FROM( ");
		querySQL.append(sql);
		querySQL.append(") my_table ");
		querySQL.append(") WHERE my_rownum BETWEEN ? AND ? ");
		querySQL.append(intiOrderSQL());//添加排序语句
		return querySQL.toString();
	}

	//	public String intiQuerySQL(int inResultSize)
	//	{
	//		String sQuerySQL = "";
	//		int index = this.querySQL.indexOf("#");
	//		String s1 = this.querySQL.substring(0, index);
	//		String s2 = this.querySQL.substring(index + 3);
	//		sQuerySQL = s1 + String.valueOf(inResultSize) + s2;
	//		return sQuerySQL;
	//	}

	/**     
	 * 构造一查询出当页数据的PageStatement，并指定每页显示记录条数  
	 * @param conn 数据库连接  
	 * @param sql query sql     
	 * @param pageNo 页码     
	 * @param pageSize 每页容量     
	 */
	public PageStatement(Connection conn, String sql)
	{
		this.psConn = conn;

		pInfo = (PageInfo) hmPageInfo.get(PageStatement.sKey);

		if (pInfo != null)
		{
			this.pageNo = pInfo.getPageNo();
			if (pInfo.getPageSize() > 0)
			{
				this.pageSize = pInfo.getPageSize();
			}
			else
			{
				this.pageSize = Constant.PageControl.CODE_PAGELINECOUNT;
			}

			this.startIndex = getStartOfAnyPage(this.pageNo, this.pageSize);
			this.boundParams = Collections.synchronizedList(new java.util.LinkedList());
			this.countSQL = " SELECT COUNT(*) FROM ( " + sql + ") ";
			this.querySQL = intiQuerySQL(sql);
		}
	}

	/**     
	 *使用给出的对象设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param obj 包含参数值的对象     
	 */
	public void setObject(int index, Object obj) throws SQLException
	{
		BoundParam bp = new BoundParam(index, obj);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的对象设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param obj 包含参数值的对象     
	 *@param targetSqlType 参数的数据库类型     
	 */
	public void setObject(int index, Object obj, int targetSqlType) throws SQLException
	{
		BoundParam bp = new BoundParam(index, obj, targetSqlType);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的对象设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param obj 包含参数值的对象     
	 *@param targetSqlType 参数的数据库类型(常量定义在java.sql.Types中)     
	 *@param scale 精度，小数点后的位数     
	 * （只对targetSqlType是Types.NUMBER或Types.DECIMAL有效，其它类型则忽略）     
	 */
	public void setObject(int index, Object obj, int targetSqlType, int scale) throws SQLException
	{
		BoundParam bp = new BoundParam(index, obj, targetSqlType, scale);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的字符串设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param str 包含参数值的字符串     
	 */
	public void setString(int index, String str) throws SQLException
	{
		BoundParam bp = new BoundParam(index, str);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的字符串设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param timestamp 包含参数值的时间戳     
	 */
	public void setTimestamp(int index, Timestamp timestamp) throws SQLException
	{
		BoundParam bp = new BoundParam(index, timestamp);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的整数设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param value 包含参数值的整数     
	 */
	public void setInt(int index, int value) throws SQLException
	{
		BoundParam bp = new BoundParam(index, new Integer(value));
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的长整数设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param value 包含参数值的长整数     
	 */
	public void setLong(int index, long value) throws SQLException
	{
		BoundParam bp = new BoundParam(index, new Long(value));
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的双精度浮点数设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param value 包含参数值的双精度浮点数     
	 */
	public void setDouble(int index, double value) throws SQLException
	{
		BoundParam bp = new BoundParam(index, new Double(value));
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	/**     
	 *使用给出的BigDecimal设置指定参数的值     
	 *@param index 第一个参数为1，第二个为2，。。。     
	 *@param bd 包含参数值的BigDecimal     
	 */
	public void setBigDecimal(int index, BigDecimal bd) throws SQLException
	{
		BoundParam bp = new BoundParam(index, bd);
		boundParams.remove(bp);
		boundParams.add(bp);
	}

	public void setParams(PreparedStatement pst) throws SQLException
	{
		if (pst == null || this.boundParams == null || this.boundParams.size() == 0)
			return;
		BoundParam param;
		Iterator itr = this.boundParams.iterator();
		boundParamCount = 0;
		while (itr.hasNext())
		{
			param = (BoundParam) itr.next();
			if (param == null)
				continue;
			boundParamCount++;

			if (param.sqlType == java.sql.Types.OTHER)
			{
				pst.setObject(param.index, param.value);
			}
			else
			{
				pst.setObject(param.index, param.value, param.sqlType, param.scale);
			}
		}
	}

	/**     
	 * 执行查询取得一页数据，执行结束后关闭数据库连接     
	 * @return RowSetPage     
	 * @throws SQLException     
	 */
	public ResultSet executeQuery() throws SQLException
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		long startIndex = 1;
		int inResultSize = 0;
		String strSQL = "";

		try
		{				
			if (pInfo.getChange())
			{
				pst = this.psConn.prepareStatement(this.countSQL);
				setParams(pst);
				rs = pst.executeQuery();

				if (rs.next())
				{
					inResultSize = rs.getInt(1);
				}
				hmCount.put(PageStatement.sKey,String.valueOf(inResultSize));
			}
			else
			{
				try
				{
					inResultSize = Integer.parseInt((String) hmCount.get(PageStatement.sKey));
				}
				catch (Exception e)
				{
					log4j.error(e.getMessage());
					inResultSize = 0;
				}
			}

			//strSQL = intiQuerySQL(inResultSize);
			strSQL = this.querySQL;
			log4j.info("strSQL:" + strSQL);
			log4j.info("Page No:" + this.startIndex + ":" + (this.startIndex + this.pageSize - 1));
			pst = this.psConn.prepareStatement(strSQL);
			setParams(pst);
			pst.setLong(boundParamCount + 1, this.startIndex);
			pst.setLong(boundParamCount + 2, (this.startIndex + this.pageSize - 1));
			rs = pst.executeQuery();
		}
		catch (SQLException sqle)
		{
			log4j.info("executeQuery SQLException");
			sqle.printStackTrace();
			throw sqle;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
		finally
		{
			//log4j.info("executeQuery finally");
		}

		log4j.info("executeQuery success");
		return rs;
	}

	/**     
	 *关闭数据库连接     
	 */
	public void close()
	{
		//因为数据库连接在查询结束或发生异常时即关闭，此处不做任何事情        
		//留待扩充。    
	}
	private class BoundParam
	{
		int index;
		Object value;
		int sqlType;
		int scale;
		public BoundParam(int index, Object value)
		{
			this(index, value, java.sql.Types.OTHER);
		}
		public BoundParam(int index, Object value, int sqlType)
		{
			this(index, value, sqlType, 0);
		}
		public BoundParam(int index, Object value, int sqlType, int scale)
		{
			this.index = index;
			this.value = value;
			this.sqlType = sqlType;
			this.scale = scale;
		}
		public boolean equals(Object obj)
		{
			if (obj != null && this.getClass().isInstance(obj))
			{
				BoundParam bp = (BoundParam) obj;
				if (this.index == bp.index)
					return true;
			}
			return false;
		}
	}
}
