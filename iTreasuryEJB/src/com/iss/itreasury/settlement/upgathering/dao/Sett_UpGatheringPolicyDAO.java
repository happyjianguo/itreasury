package com.iss.itreasury.settlement.upgathering.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.upgathering.dataentity.UpGatheringPolicyInfo;
import com.iss.itreasury.util.Constant;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class Sett_UpGatheringPolicyDAO extends SettlementDAO
{

	public Sett_UpGatheringPolicyDAO()
	{
		super("Sett_UpGatheringPolicy");
	}
	
	/**
	 * 得到下一个上收策略的编号
	 * @return
	 * @throws SettlementDAOException
	 */
	public String getNextPolicyCode() throws SettlementDAOException
	{
		String strNextCode = "001";
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT lpad(to_number(nvl(max(code),0))+1,3,'0') nextcode FROM Sett_UpGatheringPolicy \n");
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			executeQuery();
			if(transRS.next())
				strNextCode = transRS.getString("nextcode");
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	throw new SettlementDAOException("Sett_UpGatheringPolicyDAO.getNextPolicyCode() ITreasuryDAOException 异常发生",e);
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
	    	throw new SettlementDAOException("Sett_UpGatheringPolicyDAO.getNextPolicyCode() SQLException 异常发生",e);
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
		return strNextCode;
	}
	
	/**
	 * 查询所有的上收策略
	 * @param info
	 * @param lOrderBy
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementDAOException
	 */
	public Collection findAllPolicies(UpGatheringPolicyInfo info,long lOrderBy,long lAscOrDesc) throws SettlementDAOException
	{
		String orderByString = null;
		switch((int)lOrderBy)
		{
			case 1:
				orderByString = "order by Code";break;
			case 2:
				orderByString = "order by UpOrder";break;
			case 3:
				orderByString = "order by UpType";break;
			default:
				orderByString = "order by Code";
		}
		if(lAscOrDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			orderByString += " desc ";
		else
			orderByString += " asc ";
		Collection c = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select p.Id,p.Code,p.Name,p.UpClientId,p.PayeeAccountId,p.UpOrder,p.UpType,p.InputUserId,p.ModifyUserId,count(d.Id) PayerAccounts \n");
		buffer.append("from Sett_UpGatheringPolicy p, (select * from Sett_UpGatheringDetail where StatusId = 1 ) d \n");
		buffer.append("where p.Id = d.policyId(+) and p.OfficeId = ? and p.CurrencyId = ? \n");
		buffer.append("and p.StatusId = ? ");
		if(info.getId()>0)
			buffer.append("and p.Id = ? ");
		buffer.append("group by p.Id,p.Code,p.Name,p.UpClientId,p.PayeeAccountId,p.UpOrder,p.UpType,p.InputUserId,p.ModifyUserId \n");
		buffer.append(orderByString);
		System.out.println("findAllPolicies-------------------------\n" + buffer.toString());
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			transPS.setLong(1, info.getOfficeId());
			transPS.setLong(2, info.getCurrencyId());
			transPS.setLong(3, Constant.RecordStatus.VALID);
			if(info.getId()>0)
				transPS.setLong(4, info.getId());
			executeQuery();
			c = getDataEntitiesFromResultSet(info.getClass());
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	throw new SettlementDAOException("Sett_UpGatheringPolicyDAO.findAllPolicies() ITreasuryDAOException 异常发生",e);
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
	    	throw new SettlementDAOException("Sett_UpGatheringPolicyDAO.findAllPolicies() SQLException 异常发生",e);
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
	
	/**
	 * 查询所有的上收策略(资金上收用)
	 * @param lAscOrDesc
	 * @return
	 * @throws SettlementDAOException
	 */
	public Collection findAllPoliciesForExcute(long lAscOrDesc) throws SettlementDAOException
	{		
		Collection c = null;
		StringBuffer buffer = new StringBuffer();
		/*
		buffer.append("select p.ID,p.Code,p.Name,p.UpOrder,p.UpClientId,p.PayeeAccountId,count(d.ID) PayerAccounts,\n");
		//参考账户余额查询中的可用余额
		buffer.append("sum(round(nvl(decode(sa.nstatusid,1,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,5,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,sa.mbalance-sa.AC_MCAPITALLIMITAMOUNT-sa.MUNCHECKPAYMENTAMOUNT,0),0),2)) UpGatcheringAmount \n");
		buffer.append("from Sett_UpGatheringPolicy p,(select * from Sett_UpGatheringDetail where StatusId = 1) d,Sett_SubAccount sa \n");
		buffer.append("where p.StatusId = 1 and p.ID = d.PolicyId(+) and d.PayerAccountId = sa.nAccountID(+) \n");
		buffer.append("group by p.ID,p.Code,p.Name,p.UpOrder,p.UpClientId,p.PayeeAccountId \n");
		*/
		buffer.append("select p.ID,p.Code,p.Name,p.UpOrder,p.UpClientId,p.PayeeAccountId,count(PolicyId) PayerAccounts, \n");
		buffer.append("		  p.CurrencyID,p.OfficeID, \n");
		buffer.append("		  sum(UpGatcheringAmount) UpGatcheringAmount \n");
		buffer.append("from \n");
		buffer.append("( \n");
		
		buffer.append("select PolicyId,PayerClientId,PayerAccountId,Limit,Unit,MaxAmount,SerialNo, \n");
		buffer.append("		(case when (AccountBalance - Limit) > 0 And (AccountBalance - Limit) > MaxAmount then Unit*floor(MaxAmount/Unit) \n");
		buffer.append("			  when (AccountBalance - Limit) > 0 And (AccountBalance - Limit) <= MaxAmount then Unit*floor((AccountBalance-Limit)/Unit) \n");
		buffer.append("			  when (AccountBalance - Limit) <= 0 then 0.0 \n");
		buffer.append("		end) UpGatcheringAmount \n");
		buffer.append("from ( \n");
		buffer.append(" select d.PolicyId,d.PayerClientId,d.PayerAccountId,d.Limit,d.Unit,d.MaxAmount,d.SerialNo,\n");
		buffer.append(" round(nvl(decode(sa.nstatusid,1,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,5,sa.mbalance-sa.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,sa.mbalance-sa.AC_MCAPITALLIMITAMOUNT-sa.MUNCHECKPAYMENTAMOUNT,0),0),2) AccountBalance\n");
		buffer.append(" from Sett_UpGatheringDetail d,Sett_SubAccount sa \n");
		buffer.append(" where d.StatusId = 1 and d.PayerAccountId = sa.nAccountID \n");
		buffer.append("		) \n");
		
		buffer.append("	) a,  Sett_UpGatheringPolicy p\n");
		buffer.append("where a.PolicyId(+) = p.ID and p.StatusId = 1 \n");
		buffer.append("group by p.ID,p.Code,p.Name,p.UpOrder,p.UpClientId,p.PayeeAccountId,p.CurrencyID,p.OfficeID \n");
		
		if(lAscOrDesc == Constant.PageControl.CODE_ASCORDESC_DESC)
			buffer.append("order by p.Code desc");
		else
			buffer.append("order by p.Code asc");
		System.out.println("findAllPoliciesForExcute------------------- \n" + buffer.toString());
		try
		{
			initDAO();			
			transPS = prepareStatement(buffer.toString());
			executeQuery();
			c = getDataEntitiesFromResultSet(UpGatheringPolicyInfo.class);
			finalizeDAO();
		}
		catch(ITreasuryDAOException e)
	    {
	    	e.printStackTrace();
	    	throw new SettlementDAOException("Sett_UpGatheringPolicyDAO.findAllPoliciesForExcute() ITreasuryDAOException 异常发生",e);
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
