/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.common.attach.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.common.attach.dataentity.AttachInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachDao extends ITreasuryDAO
{
	private static Log4j log4j = null;

	public AttachDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
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
			sbSQL.append(" SELECT a.id as docID,b.* ");
			sbSQL.append(" FROM loan_docInfo a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nParentTypeID = ?");
			sbSQL.append(" AND a.nParentID = ?");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAttachTypeID);
			ps.setLong(2, lParentID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表loan_docInfo的ID)
				info.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
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
	* 得到文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lAttachTypeID 上传的文档类型
	* @param lClientID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection queryclientcenter(long lAttachTypeID, long lClientID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,b.* ");
			sbSQL.append(" FROM CENTER_DOCINFO a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nParentTypeID = ?");
			sbSQL.append(" AND a.nCLIENTID = ?");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAttachTypeID);
			ps.setLong(2, lClientID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表center_docInfo的ID)
				info.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				info.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				info.setRealName(rs.getString("sClientPath")); //在客户端的路径
				info.setMime(rs.getString("sFileMimeType")); //文件内容类型
				info.setContentType(rs.getString("sFileContentType")); //文件类型
				info.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				info.setParentID(lClientID);//所属对象的ID

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
			sbSQL.append(" UPDATE loan_docInfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");

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
	* 逻辑删除客户中心文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteclientcenter(long lID) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE center_docInfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");

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
				
				sbSQL.append(" SELECT MAX(NVL(id,0)+1) AS lID");
				sbSQL.append(" FROM loan_docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO loan_docInfo ");
				sbSQL.append(" (ID,nFileID,");
				sbSQL.append(" nTypeID,nParentTypeID,");
				sbSQL.append(" nParentID,nStatusID)");
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
	/**
	* 增加文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insertclientcenter(AttachInfo info) throws Exception
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
				
				sbSQL.append(" SELECT MAX(NVL(id,0)+1) AS lID");
				sbSQL.append(" FROM center_docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO center_docInfo ");
				sbSQL.append(" (ID,nFileID,");
				sbSQL.append(" NFILETYPEID,nParentTypeID,");
				sbSQL.append(" NCLIENTID,nStatusID)");
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
	
	/**
	* 查询文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public Collection findByCondition(AttachInfo info) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,a.ntranscode,b.* ");
			sbSQL.append(" FROM docInfo a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nModuleID = ?");
			sbSQL.append(" AND a.nTransTypeID = ?");
			sbSQL.append(" AND a.nTransSubTypeID = ?");
			sbSQL.append(" AND a.nCurrencyID = ?");
			sbSQL.append(" AND a.nOfficeID = ?");
			//sbSQL.append(" AND a.nParentTypeID = ?");
			//sbSQL.append(" AND a.nParentID = ?");
			sbSQL.append(" AND a.ntranscode = ?");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getModuleID());
			ps.setLong(2, info.getTransTypeID());
			ps.setLong(3, info.getTransSubTypeID());
			ps.setLong(4, info.getCurrencyID());
			ps.setLong(5, info.getOfficeID());
			//ps.setLong(6, info.getParentType());
			//ps.setLong(7, info.getParentID());
			ps.setString(6, info.getTransCode());
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo aInfo = new AttachInfo();
				aInfo.setID(rs.getLong("docID")); //文件ID(表docInfo的ID)
				aInfo.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				aInfo.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				aInfo.setRealName(rs.getString("sClientPath")); //在客户端的路径
				aInfo.setMime(rs.getString("sFileMimeType")); //文件内容类型
				aInfo.setContentType(rs.getString("sFileContentType")); //文件类型
				aInfo.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				aInfo.setParentID(info.getParentID());//所属对象的ID
				aInfo.setModuleID(info.getModuleID());
				aInfo.setTransTypeID(info.getTransTypeID());
				aInfo.setTransSubTypeID(info.getTransSubTypeID());
				aInfo.setTransCode(rs.getString("ntranscode")); 

				vResult.add(aInfo);
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
	* 查询文件信息(网银)
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public Collection findByOBCondition(AttachInfo info) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,a.ntranscode,b.* ");
			sbSQL.append(" FROM ob_uploadinfo a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nModuleID = ?");
			sbSQL.append(" AND a.nTransTypeID = ?");
			sbSQL.append(" AND a.nTransSubTypeID = ?");
			sbSQL.append(" AND a.nCurrencyID = ?");
			sbSQL.append(" AND a.nOfficeID = ?");
			sbSQL.append(" AND a.nClientID = ?");
			//sbSQL.append(" AND a.nParentTypeID = ?");
			//sbSQL.append(" AND a.nParentID = ?");
			sbSQL.append(" AND a.ntranscode = ?");
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getModuleID());
			ps.setLong(2, info.getTransTypeID());
			ps.setLong(3, info.getTransSubTypeID());
			ps.setLong(4, info.getCurrencyID());
			ps.setLong(5, info.getOfficeID());
			ps.setLong(6, info.getNClientID());
			//ps.setLong(6, info.getParentType());
			//ps.setLong(7, info.getParentID());
			ps.setString(7, info.getTransCode());
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo aInfo = new AttachInfo();
				aInfo.setID(rs.getLong("docID")); //文件ID(表docInfo的ID)
				aInfo.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				aInfo.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				aInfo.setRealName(rs.getString("sClientPath")); //在客户端的路径
				aInfo.setMime(rs.getString("sFileMimeType")); //文件内容类型
				aInfo.setContentType(rs.getString("sFileContentType")); //文件类型
				aInfo.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				aInfo.setParentID(info.getParentID());//所属对象的ID
				aInfo.setModuleID(info.getModuleID());
				aInfo.setTransTypeID(info.getTransTypeID());
				aInfo.setTransSubTypeID(info.getTransSubTypeID());
				aInfo.setTransCode(rs.getString("ntranscode")); 

				vResult.add(aInfo);
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
	* 增加文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteDoc(long lID) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE docInfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");

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
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteOBDoc(long lID) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE ob_uploadinfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");

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
	public long insertDoc(AttachInfo info) throws Exception
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
				
				sbSQL.append(" SELECT MAX(NVL(id,0)+1) AS lID");
				sbSQL.append(" FROM docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO docInfo ");
				sbSQL.append(" (ID,nFileID,nModuleID,nTransTypeID,nTransSubTypeID,");
				sbSQL.append(" nCurrencyID,nOfficeID,nTypeID,nParentTypeID,");
				sbSQL.append(" nParentID,nStatusID,NTRANSCODE)");
				sbSQL.append(" VALUES ");
				sbSQL.append(" (?,?,?,?,?,?,?,?,?,?,?,?)");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,lID);
				ps.setLong(2,info.getFileID()); 
				ps.setLong(3,info.getModuleID());
				ps.setLong(4,info.getTransTypeID());
				ps.setLong(5,info.getTransSubTypeID());
				ps.setLong(6,info.getCurrencyID());
				ps.setLong(7,info.getOfficeID());
				ps.setLong(8,info.getType());
				ps.setLong(9,info.getParentType());
				ps.setLong(10,info.getParentID());
				ps.setLong(11,Constant.RecordStatus.VALID);
				ps.setString(12,info.getTransCode());
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
	/**
	* 增加文件信息（网银）
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insertOBDoc(AttachInfo info) throws Exception
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
				
				sbSQL.append(" SELECT MAX(NVL(id,0)+1) AS lID");
				sbSQL.append(" FROM ob_uploadinfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO ob_uploadinfo ");
				sbSQL.append(" (ID,nFileID,nModuleID,nTransTypeID,nTransSubTypeID,");
				sbSQL.append(" nCurrencyID,nOfficeID,nClientID,nTypeID,nParentTypeID,");
				sbSQL.append(" nParentID,nStatusID,NTRANSCODE)");
				sbSQL.append(" VALUES ");
				sbSQL.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?)");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,lID);
				ps.setLong(2,info.getFileID()); 
				ps.setLong(3,info.getModuleID());
				ps.setLong(4,info.getTransTypeID());
				ps.setLong(5,info.getTransSubTypeID());
				ps.setLong(6,info.getCurrencyID());
				ps.setLong(7,info.getOfficeID());
				ps.setLong(8,info.getNClientID());
				ps.setLong(9,info.getType());
				ps.setLong(10,info.getParentType());
				ps.setLong(11,info.getParentID());
				ps.setLong(12,Constant.RecordStatus.VALID);
				ps.setString(13,info.getTransCode());
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
	/**通过临时交易号,把正常的交易号与上传的文件做关联
	 * 
	 * @param tempTransCode 临时交易号
	 * @param transCode交易号
	 * @throws Exception
	 */
	public void updateTransCode(String tempTransCode,String transCode) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE docInfo ");
			sbSQL.append(" SET ntransCode = " + transCode);
			sbSQL.append(" WHERE ntransCode = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setString(1, tempTransCode);
			ps.executeUpdate();

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
		
	}
	
	/**通过临时交易号,把正常的交易号与上传的文件做关联（网银）
	 * 
	 * @param tempTransCode 临时交易号
	 * @param transCode交易号
	 * @throws Exception
	 */
	public void updateOBTransCode(String tempTransCode,String transCode) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE ob_uploadinfo ");
			sbSQL.append(" SET ntransCode = " + transCode);
			sbSQL.append(" WHERE ntransCode = ? ");

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setString(1, tempTransCode);
			ps.executeUpdate();

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
		
	}
	
	
}
