package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.setting.dataentity.TransFeeTypeSetInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_TransactionFeeTypeDAO extends SettlementDAO
{

	/**
	 * Constructor for Sett_TransactionFeeTypeDAO.
	 */
	public Sett_TransactionFeeTypeDAO()
	{
		super();
		this.strTableName = "sett_transactionfeetype";
	}

	/**
	 * Constructor for Sett_TransactionFeeTypeDAO.
	 * @param conn
	 */
	public Sett_TransactionFeeTypeDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_transactionfeetype";
	}

	public TransFeeTypeSetInfo findByID(long id) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransFeeTypeSetInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs);
			
			if(collTemp != null)
			{
				Iterator itTemp = collTemp.iterator();
				
				if(itTemp != null && itTemp.hasNext())
				{
					result = (TransFeeTypeSetInfo)itTemp.next();
				}
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}
	public Collection findByConditions(TransFeeTypeSetInfo conditionInfo, int orderByType, boolean isDesc)
		throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer(256);

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");

			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;

			StringBuffer strWhereSQLBuffer = new StringBuffer(128);

			if (conditionInfo.getID() != -1)
			{
				strWhereSQLBuffer.append(" AND ID = " + conditionInfo.getID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getOfficeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getCurrencyID() != -1)
			{
				strWhereSQLBuffer.append(" AND nCurrencyID = " + conditionInfo.getCurrencyID() + " \n");
				isNeedWhere = true;
			}
			
			if (conditionInfo.getName() != null && conditionInfo.getName().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SNAME = '" + conditionInfo.getName() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCode() != null && conditionInfo.getCode().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCODE = '" + conditionInfo.getCode() + "' \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getSubjectCode() != null && conditionInfo.getSubjectCode().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SSUBJECTCODE = '" + conditionInfo.getSubjectCode() + "' \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getIsHaveBank() != -1)
			{
				strWhereSQLBuffer.append(" AND NISHAVEBANK = " + conditionInfo.getIsHaveBank() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getStatusID() != -1)
			{
				strWhereSQLBuffer.append(" AND NSTATUSID = " + conditionInfo.getStatusID() + " \n");
				isNeedWhere = true;
			}

			if (isNeedWhere)
			{
				strSQLBuffer.append(" WHERE");
				//Cut first "AND"
				strSQLBuffer.append(strWhereSQLBuffer.substring(4));
			}

			boolean isNeedOrderBy = true;
			switch (orderByType)
			{

				case 1 :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
					}
					break;
				default :
					{
						isNeedOrderBy = false;
					}
					break;
			}

			if (isNeedOrderBy)
			{
				if (isDesc)
					strSQLBuffer.append(" DESC \n");
				else
					strSQLBuffer.append(" ASC \n");
			}

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			result = this.getInfoFromResultSet(rs);
		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransFeeTypeSetInfo info = new TransFeeTypeSetInfo();

			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setName(rs.getString("SNAME"));
			info.setCode(rs.getString("SCODE"));
			info.setSubjectCode(rs.getString("SSUBJECTCODE"));
			info.setIsHaveBank(rs.getLong("NISHAVEBANK"));
			info.setStatusID(rs.getLong("NSTATUSID"));

			list.add(info);
		}
		return list;
	}
	
	/**
	 * 根据标识查询交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回类TransactionFeeTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransactionFeeTypeInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID)
	{
		TransFeeTypeSetInfo Info = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			strBuff.append("SELECT ID,SNAME,SCODE,SSUBJECTCODE,NSTATUSID FROM Sett_TransactionFeeType ");
			strBuff.append(" WHERE ID = ?");
			ps = conn.prepareStatement(strBuff.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Info = new TransFeeTypeSetInfo();
				
				Info.setID( rs.getLong(1));
				Info.setName(rs.getString(2));
				Info.setCode(rs.getString(3));
				Info.setSubjectCode( rs.getString(4));
				Info.setStatusID( rs.getInt(5));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return Info;
	}
	
	/**
	 * 根据标识查询交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回类TransFeeTypeSetInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return TransFeeTypeSetInfo
	 * @exception Exception
	 */
	public TransFeeTypeSetInfo findTransactionFeeTypeByID(long lID, long lCurrencyID) 
	{
		TransFeeTypeSetInfo Info = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			strBuff.append("SELECT ID,SNAME,SCODE,SSUBJECTCODE,NSTATUSID FROM Sett_TransactionFeeType ");
			strBuff.append(" WHERE ID = ?");
			ps = conn.prepareStatement(strBuff.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				Info = new TransFeeTypeSetInfo();
				Info.setID( rs.getLong(1));
				Info.setName(rs.getString(2));
				Info.setCode(rs.getString(3));
				Info.setSubjectCode( rs.getString(4));
				Info.setStatusID( rs.getInt(5));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return Info;
	}

	/**
	 * 查询所有交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询所有交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>返回Collection，包含类TransFeeTypeSetInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lOfficeID 办事处标识
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllTransactionFeeType(long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		try
		{
			con = Database.getConnection();
			//返回需求的结果集
			strBuff = new StringBuffer();
			strBuff.append(" SELECT  ID, SNAME, SCODE, SSUBJECTCODE, NSTATUSID,  NISHAVEBANK  FROM ");
			strBuff.append(" ( ");
			strBuff.append(" SELECT tf.ID, tf.SNAME, tf.SCODE, tf.SSUBJECTCODE SSUBJECTCODE, tf.NSTATUSID, tf.NOFFICEID, tf.NISHAVEBANK,tf.nCurrencyID \n");
			strBuff.append(" FROM  Sett_TransactionFeeType tf,Sett_currencysubject cs \n");
			strBuff.append(" WHERE cs.ncurrencyid(+)=? and cs.stablename(+)='transactionfeetype' and cs.nrecordid(+)=tf.id \n");
			strBuff.append(" and tf.nStatusID=? and tf.nOfficeID=? and tf.nCurrencyID=? \n");
			switch ((int) lOrderParam)
			{
				case 1 :
					strBuff.append(" ORDER BY tf.ID");
					break;
				case 2 :
					strBuff.append(" ORDER BY tf.SNAME");
					break;
				case 3 :
					strBuff.append(" ORDER BY to_number(tf.SCODE)");
					break;
				case 4 :
					strBuff.append(" ORDER BY tf.SSUBJECTCODE");
					break;
				case 5 :
					strBuff.append(" ORDER BY tf.nishavebank");
			}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strBuff.append(" DESC ");
			}
			strBuff.append(" ) ");
			Log.print("strSQL=" + strBuff.toString());
			ps = con.prepareStatement(strBuff.toString());
			ps.setLong(1, lCurrencyID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				TransFeeTypeSetInfo Info = new TransFeeTypeSetInfo();
				Info.setID( rs.getLong(1));
				Info.setName(rs.getString(2));
				Info.setCode(rs.getString(3));
				Info.setSubjectCode( rs.getString(4));
				Info.setStatusID( rs.getInt(5));
				Info.setIsHaveBank(rs.getLong(6));
				Info.setM_lPageCount(lPageCount);
				v.add(Info);
			}
			//关闭资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return (v.size() > 0 ? v : null);
	}	
	
	/**
	 * 得到最新的交易费用类型代码
	 * @param lOfficeID 办事处标识
	 * @return
	 */
	public String getNewTransactionFeeTypeCode(long lOfficeID,long lCurrencyID)
	{
		String strResult = "";
		StringBuffer sb = new StringBuffer();
		long lResult = 0;
		//String strSQL = "select (max(to_number(sgeneralledgercode))+1) as nCode from GeneralLedger" ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sb.append(" select nvl(min(id),0) maxcode \n");
			sb.append(" from (select id from Sett_transactionfeetype  where nofficeid=? and nCurrencyID=? and nStatusID>0 \n");
			sb.append("       minus \n");
			sb.append("       select to_number(scode) scode from Sett_transactionfeetype  where nofficeid=? and nCurrencyID=? and nStatusID>0 \n");
			sb.append("       ) \n");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			// 没有跳过的scode,通过下面sql获取最大的scode。
			if (lResult == 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);
				sb.append(" select nvl(max(to_number(scode))+1,1) as scode from Sett_transactionfeetype where nofficeid=? and nCurrencyID=? and nStatusID>0 ");
				ps = conn.prepareStatement(sb.toString());
				ps.setLong(1, lOfficeID);
				ps.setLong(2, lCurrencyID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lResult = rs.getLong(1);
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult + "";
	}

	
	/**
	 * 保存交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>如果lID<0，则在TransactionFeeType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strName
	 * @param strCode
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveTransactionFeeType(long lID, long lOfficeID, long lCurrencyID, String strName, String strCode, String strSubjectCode, long lIsHaveBank)
	{
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = lID;
		try
		{
			if (strCode.equals("-1"))
			{
				strCode = getNewTransactionFeeTypeCode(lOfficeID,lCurrencyID);
				Log.print("enter t():" + strCode);
			}
			else
			{
			}
			conn = Database.getConnection();
			if (lID < 0) //增加记录
			{
				Log.print("33333");
				String strSQL = "SELECT COUNT(ID) ID FROM Sett_TransactionFeeType WHERE nStatusID>0 and SCODE=? AND nOfficeID=? and nCurrencyID=? "; //用来检查是否重复输入相同的编码
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strCode);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, lCurrencyID);
				rs = ps.executeQuery();
				rs.next();
				if (rs.getLong(1) > 0)
				{
					lResult = 0; //有重复的选项
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//确定交易类型名称
				strSQL = "SELECT COUNT(ID) ID FROM Sett_TransactionFeeType WHERE nStatusID>0 and SNAME=? AND nOfficeID=? and nCurrencyID=?"; //用来检查是否重复输入相同的编码
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, lCurrencyID);
				rs = ps.executeQuery();
				rs.next();
				if (rs.getLong(1) > 0)
				{
					lResult = -1; //有重复的交易类型名称
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM Sett_TransactionFeeType ");
				Log.print("strBuff=" + strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_TransactionFeeType(ID,SNAME,SCODE,SSUBJECTCODE,NSTATUSID,nOfficeID,nishavebank,nCurrencyID)");
				strBuff.append("VALUES(?,?,?,?,?,?,?,?)");
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lMaxID);
				ps.setString(2, strName);
				ps.setString(3, strCode);
				ps.setString(4, strSubjectCode);
				ps.setLong(5, Constant.RecordStatus.VALID);
				ps.setLong(6, lOfficeID);
				ps.setLong(7, lIsHaveBank);
				ps.setLong(8, lCurrencyID);
				
				lResult = ps.executeUpdate();
				Log.print("come here **********************");
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{
				Log.print("come here *****&&&&&&&&*****************");
				String strSQL = "SELECT COUNT(ID) ID FROM Sett_TransactionFeeType WHERE nStatusID>0 and SCODE=? AND nOfficeID=? AND ID<>? and nCurrencyID=?"; //用来检查是否重复输入相同的编码
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strCode);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, lID);
				ps.setLong(4, lCurrencyID);
				rs = ps.executeQuery();
				rs.next();
				if (rs.getLong(1) > 0)
				{
					lResult = 0; //有重复的交易类型编码
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = "SELECT COUNT(ID) ID FROM Sett_TransactionFeeType WHERE nStatusID>0 and SNAME=? AND nOfficeID=? AND ID<>? and nCurrencyID=?"; //用来检查是否重复输入相同的编码
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, lID);
				ps.setLong(4, lCurrencyID);
				rs = ps.executeQuery();
				rs.next();
				if (rs.getLong(1) > 0)
				{
					lResult = -1; //有重复的交易类型名称
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_TransactionFeeType SET SCODE=?,SNAME=?,SSUBJECTCODE=?, nishavebank=? WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				ps.setString(1, strCode);
				ps.setString(2, strName);
				ps.setString(3, strSubjectCode);
				ps.setLong(4, lIsHaveBank);
				ps.setLong(5, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}
			saveCurrencySubject("Sett_transactionfeetype", lRecordID, lCurrencyID, strSubjectCode, lOfficeID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (lResult > 9999)
		{
			lResult = 9999;
		}
		return lResult;
	}
	
	
	
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	private long saveCurrencySubject(String strTableName, long lRecordID, long lCurrencyID, String strSubject, long lOfficeID)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		int nIndex = 1;
		boolean bIsNew = true;
		try
		{
			conn = Database.getConnection();
			// sett_currencysubject( STABLENAME, NRECORDID, NCURRENCYID, SSUBJECT )
			strSQL = "select STABLENAME from Sett_CURRENCYSUBJECT where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and NBACKOFFICEID = ? ";
			ps = conn.prepareStatement(strSQL);
			nIndex = 1;
			ps.setString(nIndex++, strTableName);
			ps.setLong(nIndex++, lRecordID);
			ps.setLong(nIndex++, lCurrencyID);
			ps.setLong(nIndex++, lOfficeID);
			rs = ps.executeQuery();
			if (rs.next())
				bIsNew = false;
			else
				bIsNew = true;
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//
			if (bIsNew)
			{
				strSQL = "insert into Sett_CURRENCYSUBJECT( STABLENAME, NRECORDID, NCURRENCYID, SSUBJECT,NBACKOFFICEID ) values(?,?,?,?,?)";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;
				ps.setString(nIndex++, strTableName);
				ps.setLong(nIndex++, lRecordID);
				ps.setLong(nIndex++, lCurrencyID);
				ps.setString(nIndex++, strSubject);
				ps.setLong(nIndex++, lOfficeID);
				lResult = ps.executeUpdate();
			}
			else
			{
				strSQL = "update Sett_CURRENCYSUBJECT set SSUBJECT=? where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and NBACKOFFICEID=? ";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;
				ps.setString(nIndex++, strSubject);
				ps.setString(nIndex++, strTableName);
				ps.setLong(nIndex++, lRecordID);
				ps.setLong(nIndex++, lCurrencyID);
				ps.setLong(nIndex++, lOfficeID);
				lResult = ps.executeUpdate();
			}
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}

	
	/**
	 * 删除交易费用设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除交易费用设置</b>
	 * <ul>
	 * <li>操作数据库表TransactionFeeType
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long deleteTransactionFeeType(long lID)
	{
		long lResult = -1;
		String strSQL = "UPDATE Sett_TransactionFeeType SET  nStatusID = ? WHERE ID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareCall(strSQL);
			ps.setLong(1, Constant.RecordStatus.INVALID);
			ps.setLong(2, lID);
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			deleteCurrencySubject("Sett_TransactionFeeType", lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}

	
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	private long deleteCurrencySubject(String strTableName, long lRecordID)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		int nIndex = 1;
		try
		{
			conn = Database.getConnection();
			strSQL = "delete Sett_CURRENCYSUBJECT where STABLENAME=? and NRECORDID=?  ";
			ps = conn.prepareStatement(strSQL);
			nIndex = 1;
			ps.setString(nIndex++, strTableName);
			ps.setLong(nIndex++, lRecordID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}
}
