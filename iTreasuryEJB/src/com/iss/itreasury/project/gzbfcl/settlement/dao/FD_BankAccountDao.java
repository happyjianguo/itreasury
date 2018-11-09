package com.iss.itreasury.project.gzbfcl.settlement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_BankAccountInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FD_OrganizationInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

public class FD_BankAccountDao extends SettlementDAO{
	public FD_BankAccountDao()
	{
		super("FD_BankAccount");
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	
	 /*
	  * 对比，是否有重复记录（根据开户行账号-开户行名称，办事处）
	  * 
	  * 
	  * */
	
	public long findFD_BankAccountInfo(String BankAccountCode,String BankName, long OfficeID) throws Exception{
		
		
		long BankAccountID = -1;
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			strTmpSQL = "select ID from FD_BankAccount where StatusID = 1 and BankAccountCode =? and BankName =? and OfficeID = ?";
			transPS = prepareStatement(strTmpSQL);
			transPS.setString(1, BankAccountCode);
			transPS.setString(2, BankName);
			transPS.setLong(3, OfficeID);
			transRS = transPS.executeQuery();
			if (transRS != null && transRS.next())
				BankAccountID = transRS.getLong("ID");
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

		return BankAccountID;
		
	}
	
	
	 /*
	  * 开户行账号维护查询（模糊查询）
	  * 
	  * 
	  * */
	public Collection findBankAccountsbyCodition(FD_BankAccountInfo info) throws Exception{
		
		Vector v = new Vector();
		String strTmpSQL = "";
		try
		{
			initDAO();
			//
			StringBuffer strSQLBuffer = new StringBuffer();
			
			strSQLBuffer.append("select * from FD_BankAccount where StatusID = 1 and OfficeID = ? and CurrentID =? ");
			
			if(info.getBankAccountCode()!=null && !(info.getBankAccountCode().equals(""))){
				
				strSQLBuffer.append(" and BankAccountCode like '%"+info.getBankAccountCode()+"%'");
				
			}
			if(info.getBankName()!=null && !(info.getBankName().equals(""))){
				
				strSQLBuffer.append(" and BankName like '%"+info.getBankName()+"%'");
			}
			String strSQL = strSQLBuffer.toString();
			System.out.println(strSQL);
			transPS = prepareStatement(strSQL);
			transPS.setLong(1, info.getOfficeID());
			transPS.setLong(2, info.getCurrentID());
			transRS = transPS.executeQuery();
			while (transRS.next())
			{
				
				FD_BankAccountInfo binfo=new FD_BankAccountInfo();
				
				binfo.setId(transRS.getLong("ID"));
				binfo.setBankAccountCode(transRS.getString("BankAccountCode"));
				binfo.setBankName(transRS.getString("BankName"));
				binfo.setCurrentID(transRS.getLong("CurrentID"));
				binfo.setOfficeID(transRS.getLong("OfficeID"));
				binfo.setInputUserID(transRS.getLong("inputUserID"));
				binfo.setInputTime(transRS.getTimestamp("inputTime"));
				binfo.setModifyUserID(transRS.getLong("modifyUserID"));
				binfo.setModifyTime(transRS.getTimestamp("modifyTime"));
				
				v.addElement(binfo);
				
				
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
	
	
}
