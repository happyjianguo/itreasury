/*
 * Created on 2006-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.bankinterface.dao;

import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountInfo;
import com.iss.itreasury.settlement.base.SettlementDAOException;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Bs_BankAccountInfoDAO extends SettlementDAO{
	//	取得内部账户关联的银行账号
	public Collection getBankAccountInfo(long AccountID,long InputOrOut) throws SettlementDAOException
	{
		Collection collection = null;
		BankAccountInfo info = new BankAccountInfo();
		try
		{
			collection = super.findByCondition(info);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}	

		return collection;
	}
	//	取得内部账户关联的银行账号
	public String getBankNo(long AccountID,long InputOrOut) throws SettlementDAOException
	{
		String bankno = "";
		
		try
		{
			if(AccountID == -1) return null;
			
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT s_accountno FROM bs_bankaccountinfo \n");
			buffer.append(" WHERE n_subjectid = "+AccountID);
			buffer.append(" AND n_inputoroutput in ( "+InputOrOut+",3)");
			
			log.debug("sql:\n"+buffer.toString());			
			transPS = prepareStatement(buffer.toString());		
			transRS = executeQuery();
			if (transRS != null)
			{
				if (transRS.next())
				{
					bankno = transRS.getString("s_accountno");
				}
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}	
		
		return bankno;
	}
	
	//根据账号取得账户名称
	public String getAcctNameByNo(String AccountNo) throws SettlementDAOException
	{
		String bankno = "";
		
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT s_accountname FROM bs_bankaccountinfo \n");
			buffer.append(" WHERE s_accountno = '"+AccountNo+"'");
			
			log.debug("sql:\n"+buffer.toString());			
			transPS = prepareStatement(buffer.toString());		
			transRS = executeQuery();
			if (transRS != null)
			{
				if (transRS.next())
				{
					bankno = transRS.getString("s_accountname");
				}
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}	
		
		return bankno;
	}
	//根据账号取得账户名称
	public String getBranchNameByNo(String AccountNo) throws SettlementDAOException
	{
		String branchName = "";
		
		try
		{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT S_BRANCHNAME FROM bs_bankaccountinfo \n");
			buffer.append(" WHERE s_accountno = '"+AccountNo+"'");
			
			log.debug("sql:\n"+buffer.toString());			
			transPS = prepareStatement(buffer.toString());		
			transRS = executeQuery();
			if (transRS != null)
			{
				if (transRS.next())
				{
					branchName = transRS.getString("S_BRANCHNAME");
				}
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}	
		
		return branchName;
	}
}
