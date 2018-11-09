package com.iss.itreasury.settlement.upgathering.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringDetailInfo;
import com.iss.itreasury.util.Constant;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class Sett_UpGatheringDetailDAO extends SettlementDAO
{
	
	public Sett_UpGatheringDetailDAO()
	{
		super("Sett_UpGatheringDetail");		
	}
	
	public void updateForDelete(long lPolicyID) throws SettlementDAOException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("update Sett_UpGatheringDetail set StatusId = ? where PolicyId = ? \n");
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1, Constant.RecordStatus.INVALID);
			transPS.setLong(2, lPolicyID);
			transPS.executeUpdate();			
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.updateForDelete() ITreasuryDAOException 异常发生",e);
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.updateForDelete() SQLException 异常发生",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断某一账户是否已经充当付款方账户
	 * @param lAccountID
	 * @param lDetailID
	 * @return
	 * @throws SettlementDAOException
	 */
	public boolean isPayerAccountRole(long lAccountID,long lDetailID) throws SettlementDAOException
	{
		boolean bReturn = false;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select id from Sett_UpGatheringDetail where StatusId = ? and PayerAccountId = ? ");
		if(lDetailID > 0)
			buffer.append("and id != ? ");
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1, Constant.RecordStatus.VALID);
			transPS.setLong(2, lAccountID);
			if(lDetailID > 0)
				transPS.setLong(3, lDetailID);
			executeQuery();
			if(transRS.next())
			{
				bReturn = true;
			}
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.isPayeeAccountRole() ITreasuryDAOException 异常发生",e);
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.isPayeeAccountRole() SQLException 异常发生",e);
		}
		finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
			}
		}
		return bReturn;
	}
	
	/**
	 * 查找某策略下的上收策略明细(资金上收用)
	 * @param lPolicyID
	 * @return
	 * @throws SettlementDAOException
	 */
	public Collection findPayerAccountsByIDForExcute(long lPolicyID) throws SettlementDAOException
	{		
		Collection c = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select Id,CurrencyID,OfficeID,PayerClientId,PayerAccountId,Limit,Unit,MaxAmount,SerialNo,AccountBalance, \n");
		buffer.append("		(case when (AccountBalance - Limit) > 0 And (AccountBalance - Limit) > MaxAmount then Unit*floor(MaxAmount/Unit) \n");
		buffer.append("			  when (AccountBalance - Limit) > 0 And (AccountBalance - Limit) <= MaxAmount then Unit*floor((AccountBalance-Limit)/Unit) \n");
		buffer.append("			  when (AccountBalance - Limit) <= 0 then 0.0 \n");
		buffer.append("		 end) UpGatcheringAmount \n");
		buffer.append("from ( \n");
		buffer.append("select d.Id,d.CurrencyID,d.OfficeID,d.PayerClientId,d.PayerAccountId,d.Limit,d.Unit,d.MaxAmount,d.SerialNo,\n");
		//参考账户余额查询中的可用余额
		buffer.append("round(nvl(decode(sa.nstatusid,1,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,5,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,sa.mbalance-sa.AC_MCAPITALLIMITAMOUNT-sa.MUNCHECKPAYMENTAMOUNT,0),0),2) AccountBalance \n");
		buffer.append("from Sett_UpGatheringDetail d,Sett_SubAccount sa \n");
		buffer.append("where d.StatusId = 1 and d.PayerAccountId = sa.nAccountID \n");
		buffer.append("and d.PolicyId = ? \n");
		buffer.append(")\n");
		System.out.println("findPayerAccountsByIDForExcute ----------------\n" + buffer.toString());
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1, lPolicyID);
			executeQuery();
			c = getDataEntitiesFromResultSet(UpGatheringDetailInfo.class);
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.findPayerAccountsByIDForExcute() ITreasuryDAOException 异常发生",e);
	    }
		catch(SQLException e)
	    {
	    	e.printStackTrace();
	    	e.printStackTrace();throw new SettlementDAOException("Sett_UpGatheringDetailDAO.findPayerAccountsByIDForExcute() ITreasuryDAOException 异常发生",e);
	    }
		finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e)
			{
				e.printStackTrace();
			}
		}
		return c;
	}
}
