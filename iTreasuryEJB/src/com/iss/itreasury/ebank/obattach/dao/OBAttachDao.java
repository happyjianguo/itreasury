/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obattach.dao;

import java.util.*;
import java.sql.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.obattach.dataentity.*;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBAttachDao
{

	private static Log4j log4j = null;

	public OBAttachDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	* �õ��ļ���Ϣ
	* Create Date: 2003-10-15
	* @param lAttachTypeID �ϴ����ĵ�����
	* @param lParentID �ĵ��������������ID
	* @return Collection �ļ���Ϣ
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
			sbSQL.append(" FROM ob_docInfo a,fileInfo b");
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
				OBAttachInfo info = new OBAttachInfo();
				info.setID(rs.getLong("docID")); //�ļ�ID(��loan_docInfo��ID)
				info.setFileID(rs.getLong("id")); //�ļ�ID����fileinfo��ID��
				info.setShowName(rs.getString("sClientFileName")); //�ڿͻ��˵�����
				info.setRealName(rs.getString("sClientPath")); //�ڿͻ��˵�·��
				info.setMime(rs.getString("sFileMimeType")); //�ļ���������
				info.setContentType(rs.getString("sFileContentType")); //�ļ�����
				info.setServerPath(rs.getString("sServerPath")); //�ڷ������ϵ�·��
				info.setParentID(lParentID);//���������ID

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
	* �߼�ɾ���ļ���Ϣ
	* Create Date: 2003-10-15
	* @param lID �ĵ�ID
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
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
			sbSQL.append(" UPDATE ob_docInfo ");
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
	* �����ļ���Ϣ
	* Create Date: 2003-10-15
	* @param AttachInfo �ĵ���Ϣ
	* @return long ����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long insert(OBAttachInfo info) throws Exception
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
				sbSQL.append(" FROM ob_docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}
								
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO ob_docInfo ");
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

}
