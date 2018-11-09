package com.iss.itreasury.project.gzbfcl.settlement.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_BankAccountInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_OrganizationInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Database;

public class FD_OrganizationDao extends SettlementDAO{

	public FD_OrganizationDao()
	{
		super("FD_Organization");
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	
	 /*
	  * 对比，是否有重复记录（单位名称，办事处）
	  * 
	  * 
	  * */
	public long findFD_OrganizationInfo(String OrganizationName,long OfficeID) throws Exception{
		
		
		long OrganizationID = -1;
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			strTmpSQL = "select ID from FD_Organization where StatusID = 1 and OrganizationName = ? and OfficeID = ?";
			transPS = prepareStatement(strTmpSQL);
			transPS.setString(1, OrganizationName);
			transPS.setLong(2, OfficeID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
				OrganizationID = transRS.getLong("ID");
			transRS.close();
			transRS = null;
			transPS.close();
			transPS = null;

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}

		return OrganizationID;
		
	}
	
	
	 /*
	  * 查询所有的单位名称（没用）
	  * 
	  * 
	  * */
	public Collection findAllOrganization(long OfficeID) throws Exception{
		
		Vector v = new Vector();
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			strTmpSQL = "select * from FD_Organization where StatusID = 1 and OfficeID = ?";
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, OfficeID);
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				
				FD_OrganizationInfo oinfo=new FD_OrganizationInfo();
				
				oinfo.setId(transRS.getLong("ID"));
				oinfo.setOrganizationName(transRS.getString("OrganizationName"));
				oinfo.setCurrentID(transRS.getLong("CurrentID"));
				oinfo.setOfficeID(transRS.getLong("OfficeID"));
				oinfo.setInputUserID(transRS.getLong("inputUserID"));
				oinfo.setInputTime(transRS.getTimestamp("inputTime"));
				oinfo.setModifyUserID(transRS.getLong("modifyUserID"));
				oinfo.setModifyTime(transRS.getTimestamp("modifyTime"));
				
				v.addElement(oinfo);
				
				
			}

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}

		return (v.size() > 0 ? v : null);
		
	}
	
	 /*
	  * 单位名称（模糊）查询
	  * 
	  * 
	  * */
	public Collection findOrganizationsbyCodition(FD_OrganizationInfo info) throws Exception{
		
		Vector v = new Vector();
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			StringBuffer strSQLBuffer = new StringBuffer();
			
			strSQLBuffer.append("select * from FD_Organization where StatusID = 1 and OfficeID = ? and CurrentID =? ");
			
			if(info.getOrganizationName()!=null && !(info.getOrganizationName().equals(""))){
				
				strSQLBuffer.append(" and OrganizationName like '%"+info.getOrganizationName()+"%'");
				
			}
			String strSQL = strSQLBuffer.toString();
			System.out.println(strSQL);
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, info.getOfficeID());
			transPS.setLong(2, info.getCurrentID());
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				
				FD_OrganizationInfo oinfo=new FD_OrganizationInfo();
				
				oinfo.setId(transRS.getLong("ID"));
				oinfo.setOrganizationName(transRS.getString("organizationName"));
				oinfo.setCurrentID(transRS.getLong("CurrentID"));
				oinfo.setOfficeID(transRS.getLong("OfficeID"));
				oinfo.setInputUserID(transRS.getLong("inputUserID"));
				oinfo.setInputTime(transRS.getTimestamp("inputTime"));
				oinfo.setModifyUserID(transRS.getLong("modifyUserID"));
				oinfo.setModifyTime(transRS.getTimestamp("modifyTime"));
				
				v.addElement(oinfo);
				
				
			}

			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}

		return (v.size() > 0 ? v : null);
		
	}
	
	 /*
	  * 批量删除（将要删除单位的id拼成一个字符串，传给 in （？））（没用）
	  * 
	  * 
	  * */
	public long updateOrganizationStatus(String id, long OfficeID,long StatusID) throws Exception{
		
		long lReturn = -1;
		String strTmpSQL = "";
		try
		{
			initDAO();
			
			strTmpSQL = "UPDATE FD_Organization SET StatusID = ? WHERE OfficeID = ? and ID in  ( ? )";
			System.out.println(strTmpSQL);
			transPS = prepareStatement(strTmpSQL);
			transPS.setLong(1, StatusID);
			transPS.setLong(2, OfficeID);
			transPS.setString(3, id);
			transPS.executeUpdate();

			lReturn = 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SettlementException();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
		}
		return lReturn;
		
	}
	
}
