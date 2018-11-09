package com.iss.itreasury.craftbrother.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.clientcenter.client.dataentity.ClientInfo;
import com.iss.itreasury.util.Database;

public class CRANameRef {
	/**
	 * ��ȡ����˾��Ϣ����Ϊclient��
	 * @param officeid
	 * @return
	 * @throws SQLException 
	 */
    public static ClientInfo  getPartenerInfo(long officeid) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ClientInfo clientinfo=new ClientInfo();
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select id, scode,sname from client where nispartner=1 and clientbasetype=1 and nstatusid=1");
			sb.append(" and NOFFICEID ="+officeid);
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				clientinfo.setClientID(rs.getLong("id"));
				clientinfo.setCode(rs.getString("scode"));
				clientinfo.setName(rs.getString("sname"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}	
		return clientinfo;
	}
    /**
     * ͨ�����ױ�Ų�ѯ��������
     * @param applyID
     * @return
     * @throws SQLException
     */
    public static long  getTransactionTypeByApplyID(long applyID) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		long lReturn = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select TRANSACTIONTYPEID from sec_applyform where id="+applyID);
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getLong("TRANSACTIONTYPEID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    /**
     * ͨ��ת�ú�ͬID��ѯת�ú�ͬ���
     * @param cracontractID
     * @return
     * @throws SQLException
     */
    public static String  getTransferContractCodeByID(long cracontractID) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String lReturn = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select CONTRACTCODE from CRA_TRANSFERCONTRACTFORM where id="+cracontractID);
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getString("CONTRACTCODE");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    /**
     * ͨ�����׶���ID��ѯ���׶��ֱ��
     * @param counterPartId
     * @return
     * @throws SQLException
     * @author liangli
     */
    public static String  getCounterPartCodeByID(long counterPartId) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String lReturn = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select COUNTERPARTCODE from CRA_COUNTERPART where id="+counterPartId);
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getString("COUNTERPARTCODE");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    /**
     * ͨ�����׶���ID��ѯ���׶�������
     * @param counterPartId
     * @return
     * @throws SQLException
     * @author liangli
     */
    public static String  getCounterPartNameByID(long counterPartId) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String lReturn = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select COUNTERPARTNAME from CRA_COUNTERPART where id="+counterPartId);
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getString("COUNTERPARTNAME");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    /**
     * ͨ��¼����ID���¼��������
     * @param inputUserId
     * @return
     * @throws SQLException
     * @author liangli
     */
    public static String  getInputUserNameByID(long inputUserId) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String lReturn = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select sname from userinfo where id='"+inputUserId+"'");
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getString("sname");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    
    /**
     * ͨ�����׶��ֿ�����ID��ÿ������˺�
     * @param inputUserId
     * @return
     * @throws SQLException
     * @author liangli
     */
    public static String  getCounterPartBankNoById(long inputUserId) throws SQLException
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String lReturn = "";
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select counterpartbankno from cra_counterpartbank where id='"+inputUserId+"'");
			ps = con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lReturn=rs.getString("counterpartbankno");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(con!=null)
			{
				con.close();
				con = null;
			}
		}
		return lReturn;
	}
    public static void main(String[] args) throws SQLException
    {
    	long a=getTransactionTypeByApplyID(141);
    	System.out.println(a);
    }

}


