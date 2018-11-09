package com.iss.itreasury.loan.obinterface.dao;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.obinterface.dataentity.OBBackInfo;
import com.iss.itreasury.loan.obinterface.dataentity.OBAheadInfo;
public class OBAheadDao
{
	private static Log4j log4j = null;
	public OBAheadDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	/**
	 * Method findByID.
	 * ����ID��ѯһ����ǰ����ָ�����Ϣ
	   ��ѯ��ob_aheadrepayform
	   ���أ�OBAheadInfo
	 * @param lID
	 * @return OBAheadInfo
	 * @throws Exception
	 */
	public OBAheadInfo findByID(long lID) throws Exception
	{
		OBAheadInfo resultInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			log4j.info("\n=======findByID start====");
			conn = Database.getConnection();
			//��ѯ��ob_extend
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from ob_aheadrepayform ");
			sbSQL.append(" where ID=? ");
			log4j.info("=========sbSQL start operate table:ob_aheadrepayform===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_aheadrepayform===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				resultInfo = new OBAheadInfo();
				resultInfo.setID(rs.getLong("ID"));
				resultInfo.setContratcID(rs.getLong("NCONTRACTID"));
				resultInfo.setLoanPayNoticeID(rs.getLong("NLOANPAYNOTICEID"));
				resultInfo.setAmount(rs.getDouble("MAMOUNT"));
				resultInfo.setInstructionNO(rs.getString("SINSTRUCTIONNO"));
				resultInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				resultInfo.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				resultInfo.setStatusID(rs.getLong("NSTATUSID"));
				Log.print("\n\n====״̬=======:"+resultInfo.getStatusID());
				resultInfo.setInID(rs.getLong("NINID"));
				resultInfo.setHandleUserID(rs.getLong("NHANDLEUSERID"));
				resultInfo.setIsAhead(rs.getLong("nisahead"));
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
			log4j.info("\n=======findByID end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return resultInfo;
	}
	/**
	 * Method updateOB.
	 * �޸�һ����ǰ��������ָ�����Ϣ
	   ������ob_aheadrepayform
	   ���أ�1�ɹ���0ʧ��
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateOB(OBBackInfo info) throws Exception
	{
		long lRtnResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		int nIndex = 1;
		try
		{
			log4j.info("\n=======updateOB start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_aheadrepayform set ");
			if (info.getInID() > 0)
			{
				sbSQL.append(" NINID=?, ");
			}
			sbSQL.append(" NSTATUSID=?, ");
			if (info.getUserID() > 0)
			{
				sbSQL.append(" NHANDLEUSERID=?, ");
			}
			sbSQL.append(" NINPUTUSERID=NINPUTUSERID ");
			sbSQL.append(" where ID=? ");
			log4j.info("=========sbSQL start operate table:ob_aheadrepayform===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_aheadrepayform===========");
			ps = conn.prepareStatement(sbSQL.toString());
			if (info.getInID() > 0)
			{
				ps.setLong(nIndex++, info.getInID());
			}
			ps.setLong(nIndex++, info.getStatusID());
			if (info.getUserID() > 0)
			{
				ps.setLong(nIndex++, info.getUserID());
			}
			ps.setLong(nIndex++, info.getID());
			if (ps.executeUpdate() > 0)
			{
				lRtnResult = 1;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			log4j.info("\n=======updateOB end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lRtnResult;
	}
	/**
	 * Method updateOBStatus.
	 * ����ָ�����ڲ�ϵͳ��ID�޸�һ����ǰ��������ָ���״̬
	   ������ob_aheadrepayform
	   ���أ�1�ɹ���0ʧ��
	 * @param lID
	 * @param lStatusID
	 * @return long
	 * @throws Exception
	 */
	public long updateOBStatus(long lID, long lStatusID) throws Exception
	{
		long lRtnResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			log4j.info("\n=======updateOBStatus start====");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_aheadrepayform set ");
			sbSQL.append(" NSTATUSID=? ");
			sbSQL.append(" where NINID=? ");
			log4j.info("=========sbSQL start operate table:ob_aheadrepayform===========\n");
			log4j.info(sbSQL.toString());
			log4j.info("\n=========sbSQL end operate table:ob_aheadrepayform===========");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lID);
			if (ps.executeUpdate() > 0)
			{
				lRtnResult = 1;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			log4j.info("\n=======updateOBStatus end====");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lRtnResult;
	}
	public static void main(String[] args)
	{
		try
		{
			OBAheadDao obAheadDao = new OBAheadDao();
			OBAheadInfo obAheadInfo = null;
			/*	obAheadInfo = obAheadDao.findByID(2);
				if (obAheadInfo != null)
				{
					Log.print("=====not null =========");
					Log.print("===="+obAheadInfo.getAmount());
					Log.print("===="+obAheadInfo.getInstructionNO());
					Log.print("===="+obAheadInfo.getContratcID());
					Log.print("===="+obAheadInfo.getLoanPayNoticeID());
				}
				*/
			/*OBBackInfo obBackInfo = new OBBackInfo();
			obBackInfo.setID(2);
			obBackInfo.setInID(1);
			obBackInfo.setStatusID(3);
			obBackInfo.setUserID(3);
			if (obAheadDao.updateOB(obBackInfo) > 0)
			{
				Log.print("success!");
			}
			*/
			/*	if(obAheadDao.updateOBStatus(1,1) > 0)
				{
					Log.print("success!");
				}
			*/
		}
		catch (Exception e)
		{
		}
	}
}