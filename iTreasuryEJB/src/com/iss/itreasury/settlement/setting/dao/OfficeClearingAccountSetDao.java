package com.iss.itreasury.settlement.setting.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.settlement.setting.dataentity.OfficeClearingAccountInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;

public class OfficeClearingAccountSetDao extends ITreasuryDAO {
	
	
	/**
	 * ���һ����������Ŀ������Ϣ
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws IException
	 */
	public Collection queryOfficeClearingAccountSet(OfficeClearingAccountInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT s.*,o.sname officename,o.scode officecode FROM sett_officeSubjectSetting s,office o");
			sbSQL.append(" WHERE  s.officeid=o.id(+) ");
			if(info.getStatus()!=-1)
			{
				sbSQL.append(" and s.statusid =" + info.getStatus());
			}
			if(info.getOfficeid()!=-1)
			{
				sbSQL.append(" and s.officeid =" + info.getOfficeid());
			}	
			if(info.getCurrencyid()!=-1)
			{
				sbSQL.append(" and s.currencyid =" + info.getCurrencyid());
			}	
			sbSQL.append(" order by s.subjectcode");
			System.out.print(sbSQL);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				OfficeClearingAccountInfo resultInfo = new OfficeClearingAccountInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeid(rs.getLong("OFFICEID"));
				resultInfo.setSubjectcode(rs.getString("SUBJECTCODE"));
				resultInfo.setOfficename(rs.getString("OFFICENAME"));
				resultInfo.setOfficecode(rs.getString("OFFICECODE"));
				resultInfo.setStatus(rs.getLong("STATUSID"));
				vReturn.add(resultInfo);
				resultInfo = null;
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
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	/**
	 * ����ID��û���������������Ϣ
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws IException
	 */
	public Collection findOfficeCliearingAccountSetByID(OfficeClearingAccountInfo clientInfo) throws Exception {
       
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT s.*,o.sname officename,o.scode officecode FROM sett_officeSubjectSetting s,office o");
			sbSQL.append(" WHERE  s.officeid=o.id(+) ");
			sbSQL.append(" and  s.id="+clientInfo.getId());
			System.out.print(sbSQL);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			while (rs.next())
			{
				OfficeClearingAccountInfo resultInfo = new OfficeClearingAccountInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeid(rs.getLong("OFFICEID"));
				resultInfo.setSubjectcode(rs.getString("SUBJECTCODE"));
				resultInfo.setOfficename(rs.getString("OFFICENAME"));
				resultInfo.setOfficecode(rs.getString("OFFICECODE"));
				resultInfo.setStatus(rs.getLong("STATUSID"));
				vReturn.add(resultInfo);
				resultInfo = null;
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
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
    }
	/**
	 * ���»���������������Ϣ
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExtClientInfo(OfficeClearingAccountInfo clientInfo) throws Exception {
		
		long myid = -1;
		try {
			super.strTableName ="sett_officeSubjectSetting";
			super.update(clientInfo);
			myid = 1;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		
		return myid;
	}
	
	/**
	 * ����һ������������������Ϣ,��������Ŀ
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws Exception
	 */
	public long addOfficeClearingAccountSet(OfficeClearingAccountInfo clientInfo) throws Exception {
		
		long myid = -1;
		try {
			super.setUseMaxID();
			super.strTableName ="sett_officeSubjectSetting";
			super.add(clientInfo);
			myid = 1;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		
		return myid;
	}
	
	
	/**
	 * �жϵ�ǰ������ǰ�����Ƿ��������˻����������Ŀ
	 * 
	 * @return true(�ظ��� or false(���ظ���
	 * @throws ITreasuryException
	 */
	public boolean isOfficeClearingAccountSetExist(OfficeClearingAccountInfo clientInfo) throws ITreasuryException {
		
		boolean exists = false;
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select id");
			sb.append(" from sett_officeSubjectSetting ");
			sb.append(" where statusid = "+Constant.RecordStatus.VALID+" and  officeid = '"+ clientInfo.getOfficeid()+"' and currencyid="+clientInfo.getCurrencyid());
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				exists = true;
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new ITreasuryException();
		}
		finally
		{
			finalizeDAO();
		}

		return exists;
	}
	
	/**
	 * ���ݻ���ID������IDȡ�������Ŀ
	 * 
	 * @return String Subject
	 * @throws ITreasuryException
	 */
	public String getSubjectByOfficeAndCurrency(long lofficeid,long lcurrencyid) throws ITreasuryException,RemoteException {
		
		String subject = "";
		try {
			initDAO();
			StringBuffer sb = new StringBuffer();
			sb.append(" select subjectcode");
			sb.append(" from sett_officeSubjectSetting ");
			sb.append(" where statusid = "+Constant.RecordStatus.VALID+" and  officeid = '"+ lofficeid+"' and currencyid="+lcurrencyid);
			transPS = transConn.prepareStatement(sb.toString());
			transRS = transPS.executeQuery();
			if (transRS.next()) {
				subject = transRS.getString("subjectcode");
			}
			if(subject == null || subject.trim().length()<=0)
			{
				throw new IException("�Ҳ�������'"+NameRef.getOfficeNameByID(lofficeid) +"'��Ӧ�������Ŀ���,�������ɻ�Ʒ�¼ʧ��");
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			finalizeDAO();
		}
		return subject;
	}

}
