/*
 * Created on 2004-10-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.settlement.setting.dataentity.GeneralLedgerInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_GeneralLedgerDAO {
	
	/**
	 * 得到最新的总账代码
	 */
	public String getNewGeneralLedgerCode(long lOfficeID,long lCurrencyID) 
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
			sb.append(" select nvl(min(i),1) ming from (select (to_number(sGenerallEdgerCode)+1) i from Sett_GeneralLedger b where (to_number(sGenerallEdgerCode)+1) not in (select to_number(a.sGenerallEdgerCode) from Sett_GeneralLedger a where a.nOfficeID=? and a.nCurrencyID=? and nStatusID>0) and b.nOfficeID=? and b.nCurrencyID=? and nStatusID>0)");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			rs = ps.executeQuery();
			Log.print(sb.toString());
			if (rs.next())
			{
				lResult = rs.getLong(1);
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
	 * 查询所有总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询所有总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedgerType
	 * <li>返回Collection，包含类GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllGeneralLedger(long lOfficeID, long lCurrencyID, String strStartCode, String strEndCode, String strName, String strSubject, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) 
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
			long lGeneralLedgerStartID = -1;
			long lGeneralLedgerEndID = -1;
			try
			{
				if (strStartCode != null && !strStartCode.equals("") && !strStartCode.equals("null"))
				{
					lGeneralLedgerStartID = Long.parseLong(strStartCode);
				}
			}
			catch (Exception ef)
			{
				lGeneralLedgerStartID = -1;
			}
			try
			{
				if (strEndCode != null && !strEndCode.equals("") && !strEndCode.equals("null"))
				{
					lGeneralLedgerEndID = Long.parseLong(strEndCode);
				}
			}
			catch (Exception ef)
			{
				lGeneralLedgerEndID = -1;
			}
			//返回需求的结果集
			strBuff = new StringBuffer();
			strBuff.append(" SELECT  ID ,sGeneralLedgerCode ,sName, sSubjectCode ,nStatusID,nofficeid,nCurrencyID ");
			strBuff.append(" FROM (");
			strBuff.append(" SELECT gl.ID ,gl.sGeneralLedgerCode, gl.sName ,gl.sSubjectCode sSubjectCode ,gl.nStatusID, gl.nofficeid,gl.nCurrencyID \n");
			strBuff.append(" FROM  Sett_GeneralLedger gl,Sett_currencysubject cs \n");
			strBuff.append(" where cs.ncurrencyid(+)=? and cs.stablename(+)='generalledger' and cs.nrecordid(+)=gl.id \n");
			strBuff.append("       and gl.nStatusID=? AND gl.NOFFICEID=? and gl.nCurrencyID=? \n");
			if (lGeneralLedgerStartID > 0)
			{
				strBuff.append(" and to_number(gl.sGeneralLedgerCode) >= " + lGeneralLedgerStartID);
			}
			if (lGeneralLedgerEndID > 0)
			{
				strBuff.append(" and to_number(gl.sGeneralLedgerCode) <= " + lGeneralLedgerEndID);
			}
			if (strName != null && !strName.equals("") && !strName.equals("null"))
			{
				strBuff.append(" and gl.sName  like  '%" + strName.trim() + "%' ");
			}
			if (strSubject != null && !strSubject.equals("") && !strSubject.equals("null"))
			{
				strBuff.append(" and gl.sSubjectCode  like  '%" + strSubject.trim() + "%' ");
			}
			switch ((int) lOrderParam)
			{
				case 1 :
					strBuff.append(" ORDER BY gl.ID");
					break;
				case 2 :
					strBuff.append(" ORDER BY to_number(gl.sGeneralLedgerCode)");
					break;
				case 3 :
					strBuff.append(" ORDER BY gl.sName");
					break;
				case 4 :
					strBuff.append(" ORDER BY gl.sSubjectCode");
					break;
				default :
					}
			if (lDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strBuff.append(" DESC ");
			}
			strBuff.append(" ) ");
			ps = con.prepareStatement(strBuff.toString());
			ps.setLong(1, lCurrencyID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			//
			rs = ps.executeQuery();
			Log.print("*********" + strBuff.toString());
			while (rs.next())
			{
				GeneralLedgerInfo generalLedgerInfo = new GeneralLedgerInfo();
				generalLedgerInfo.m_lID = rs.getLong("ID");
				generalLedgerInfo.m_strGeneralLedgerCode = rs.getString("sGeneralLedgerCode");
				generalLedgerInfo.m_strName = rs.getString("sName");
				System.out.println("----------ejb--------------");
				System.out.println("subjectcode:" + rs.getString("sSubjectCode"));
				System.out.println("subjectcode:" + rs.getString("sName"));
				System.out.println("----------ejb--------------");
				generalLedgerInfo.m_strSubjectCode = rs.getString("sSubjectCode");
				generalLedgerInfo.m_nStatusID = rs.getInt("nStatusID");
				generalLedgerInfo.m_lOfficeID = rs.getLong("nofficeid");
				generalLedgerInfo.m_lCurrencyID = rs.getLong("nCurrencyID");
				generalLedgerInfo.m_lPageCount = lPageCount;
				v.add(generalLedgerInfo);
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
	 * 根据标识查询总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>返回类GeneralLedgerInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return GeneralLedgerInfo
	 * @exception Exception
	 */
	public GeneralLedgerInfo findGeneralLedgerByID(long lID, long lCurrencyID)
	{
		GeneralLedgerInfo generalLedgerInfo = new GeneralLedgerInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			strBuff.append("SELECT ID ,sGeneralLedgerCode, sName ,sSubjectCode ,nStatusID FROM Sett_GeneralLedger ");
			strBuff.append(" WHERE ID = ?");
			ps = conn.prepareStatement(strBuff.toString());
			Log.print("sql =" + strBuff.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				generalLedgerInfo.m_lID = rs.getLong("ID");
				generalLedgerInfo.m_strGeneralLedgerCode = rs.getString("sGeneralLedgerCode");
				generalLedgerInfo.m_strName = rs.getString("sName");
				generalLedgerInfo.m_strSubjectCode = rs.getString("sSubjectCode");
				generalLedgerInfo.m_nStatusID = rs.getInt("nStatusID");
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
		return generalLedgerInfo;
	}
	
	/**
	 * 保存总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>如果lID<0，则在GeneralLedger表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lOfficeID
	 * @param strGeneralLedgerCode
	 * @param strName
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveGeneralLedger(long lID, long lOfficeID, String strGeneralLedgerCode, String strName, String strSubjectCode, long lCurrencyID) 
	{
		long lResult = -1;
		String strResult = "";
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = lID;
		try
		{
			if (strGeneralLedgerCode.equals(""))
			{
				strGeneralLedgerCode = getNewGeneralLedgerCode(lOfficeID,lCurrencyID);
			}
			conn = Database.getConnection();
			//判断科目号。必须是已有的。
			/*
			Log.print("判断科目号");
			String strForestSQL = "SELECT count(*) FROM SUBJECTNO WHERE sCode=? and nStatusid=1";
			ps = conn.prepareStatement(strForestSQL);
			ps.setString(1,strSubjectCode);
			rs = ps.executeQuery();
			if(rs.next())
			{
				if (rs.getLong(1) == 0)
				{
					Log.print("返回-11");
					return -11;
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			*/
			if (lID < 0) //增加记录
			{
				//判断编码不重复
				String strSQL = "SELECT ID FROM Sett_GeneralLedger WHERE sGeneralLedgerCode=? and nOfficeID=?  and nStatusid<>? and nCurrencyID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strGeneralLedgerCode);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, Constant.RecordStatus.INVALID);
				ps.setLong(4, lCurrencyID);
				
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = 0;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//判断名称不重复
				strSQL = "SELECT ID FROM Sett_GeneralLedger WHERE sname=? and nOfficeID=?  and nStatusid<>? and nCurrencyID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strName);
				ps.setLong(2, lOfficeID);
				ps.setLong(3, Constant.RecordStatus.INVALID);
				ps.setLong(4, lCurrencyID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = -1;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM Sett_GeneralLedger ");
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
				strBuff.append("INSERT INTO Sett_GeneralLedger(ID, sGeneralLedgerCode, sName ,sSubjectCode, nStatusID,nOfficeID,nCurrencyID)");
				strBuff.append(" VALUES(?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lMaxID);
				ps.setString(2, strGeneralLedgerCode);
				ps.setString(3, strName);
				ps.setString(4, strSubjectCode);
				ps.setLong(5, Constant.RecordStatus.VALID);
				ps.setLong(6, lOfficeID);
				ps.setLong(7, lCurrencyID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{
				//用来保证无重复的编码
				String strSQL = "select * from Sett_GeneralLedger where sGeneralLedgerCode=? and id<>? and nOfficeID=? and nStatusID<>? and nCurrencyID=?";
				Log.print("aastrSQL=" + strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strGeneralLedgerCode);
				ps.setLong(2, lID);
				ps.setLong(3, lOfficeID);
				ps.setLong(4, Constant.RecordStatus.INVALID);
				ps.setLong(5, lCurrencyID);
				
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//用来保证无重复的名称
				strSQL = "select * from Sett_GeneralLedger where sName=? and id<>? and nOfficeID=? and nstatusID<>? and nCurrencyID=?";
				Log.print("strSQL=" + strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strName);
				ps.setLong(2, lID);
				ps.setLong(3, lOfficeID);
				ps.setLong(4,Constant.RecordStatus.INVALID);
				ps.setLong(5, lCurrencyID);
				
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return -1;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_GeneralLedger SET sGeneralLedgerCode=?,  sName=?");
				strBuff.append(", sSubjectCode=?  WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				ps.setString(1, strGeneralLedgerCode);
				ps.setString(2, strName);
				ps.setString(3, strSubjectCode);
				ps.setLong(4, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}
			saveCurrencySubject("Sett_generalledger", lRecordID, lCurrencyID, strSubjectCode, lOfficeID);
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
	 * 删除总账类业务设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除总账类业务设置</b>
	 * <ul>
	 * <li>操作数据库表GeneralLedger
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void  sss
	 * @exception Exception
	 */
	public long deleteGeneralLedger(long lID)
	{
		long lResult = -1;
		String strSQL = "UPDATE  Sett_GeneralLedger SET nStatusID =? WHERE ID=?";
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
			deleteCurrencySubject("Sett_generalledger", lID);
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
