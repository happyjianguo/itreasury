package com.iss.itreasury.settlement.transferinterest.bizlogic;

import java.sql.Connection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.transferinterest.dataentity.TransferInterestRecordInfo;
import com.iss.itreasury.settlement.transferinterest.dao.TransferInterestRecordDao;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class TransferInterestRecordBiz {
	
	private Log4j log = new Log4j(Constant.ModuleType.CRAFTBROTHER, this);
	
	//翻页查询
	public PageLoader queryTransferInterestRecordInfo(TransferInterestRecordInfo transferInterestRecordInfo) throws Exception
	{
		PageLoader pageLoader = null;
		TransferInterestRecordDao transferInterestRecordDao = new TransferInterestRecordDao();
		pageLoader = transferInterestRecordDao.queryTransferInterestRecordInfo(transferInterestRecordInfo);
		return pageLoader;
	}
	
	public long deleteRecord(Vector resultVec) throws Exception
	{
		long lReturn = -1; 
		Connection con = null;
		try
		{
			con = Database.getConnection();
			con.setAutoCommit(false);
			try
			{
				TransferInterestRecordDao transferInterestRecordDao = new TransferInterestRecordDao(con);		
				GeneralLedgerOperation glo = new GeneralLedgerOperation(con);
				log.info("-------------开始删除---------");
				for(int i = 0; i < resultVec.size(); i++)
				{
					TransferInterestRecordInfo infoResult = new TransferInterestRecordInfo();
					infoResult = (TransferInterestRecordInfo)resultVec.elementAt(i);
					if(!infoResult.getStransno().equals("") && infoResult.getId()>0)
					{
						lReturn = transferInterestRecordDao.deleteRecord(infoResult.getId());
						log.info("-----------删除会计分录开始--------");
						glo.deleteGLEntry(infoResult.getStransno(),con);
						log.info("-----------删除会计分录结束--------");						
					}				
				}
				con.commit();
				log.info("-------------删除结束---------");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				con.rollback();
				throw new IException(e.getMessage());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}	
		finally
		{
			try
			{
				if (con != null ) {
					con.close();
					con = null;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return lReturn;
	}

}
