package com.iss.itreasury.craftbrother.transferloancontract.transfercontract.bizlogic;

import java.sql.Connection;

import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.TransferAccountDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dataentity.TransferAccountInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;

public class TransferAccountBiz 
{
	public void add(TransferAccountInfo transferAccountInfo) throws ITreasuryDAOException,IException
	{
		try {
			Connection  conn = Database.getConnection();
			try
			{
				TransferAccountDao transferAccountDao=new TransferAccountDao("SETT_TRANSFERACCOUNT","SEQ_SETT_TRANSFERACCOUNT",conn);
				transferAccountDao.save(transferAccountInfo);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
	}
}
