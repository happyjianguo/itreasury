/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.bill.attach.dao;

import java.util.*;
import java.sql.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.bill.attach.dataentity.*;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachDao
{
	private static Log4j log4j = null;

	public AttachDao()
	{
		log4j = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	}

	/**
	* 得到文件信息
	* Create Date: 2003-10-15
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection query(long lAttachTypeID, long lParentID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.ID as docID,b.* ");
			sbSQL.append(" FROM BILL_DOCINFO a,FileInfo b");
			sbSQL.append(" WHERE a.FileID = b.ID ");
			sbSQL.append(" AND a.StatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.ParentTypeID = ?");
			sbSQL.append(" AND a.ParentID = ?");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAttachTypeID);
			ps.setLong(2, lParentID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表BILL_DOCINFO的ID)
				info.setFileID(rs.getLong("ID")); //文件ID（表fileinfo的ID）
				info.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				info.setRealName(rs.getString("sClientPath")); //在客户端的路径
				info.setMime(rs.getString("sFileMimeType")); //文件内容类型
				info.setContentType(rs.getString("sFileContentType")); //文件类型
				info.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				info.setParentID(lParentID);//所属对象的ID

				vResult.add(info);
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vResult.size() > 0 ? vResult : null;

	}

	/**
	* 逻辑删除文件信息
	* Create Date: 2003-10-15
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long delete(long lID) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE BILL_DOCINFO ");
			sbSQL.append(" SET StatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE ID = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	* 增加文件信息
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insert(AttachInfo info) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lID = -1;
		long lResult = -1;

		try
		{
			if (info != null)
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				
				sbSQL.append(" SELECT MAX(NVL(ID,0)+1) AS lID");
				sbSQL.append(" FROM BILL_DOCINFO");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO BILL_DOCINFO ");
				sbSQL.append(" (ID,FileID,");
				sbSQL.append(" TypeID,ParentTypeID,");
				sbSQL.append(" ParentID,StatusID)");
				sbSQL.append(" VALUES ");
				sbSQL.append(" (?,?,?,?,?,?)");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,lID);
				ps.setLong(2,info.getFileID());
				ps.setLong(3,info.getType());
				ps.setLong(4,info.getParentType());
				ps.setLong(5,info.getParentID());
				ps.setLong(6,info.getStatus());
				
				lResult = ps.executeUpdate();

				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

}
