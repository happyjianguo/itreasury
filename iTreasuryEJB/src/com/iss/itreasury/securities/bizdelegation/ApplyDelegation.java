package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.util.*;

import com.iss.itreasury.securities.apply.bizlogic.Apply;
import com.iss.itreasury.securities.apply.bizlogic.ApplyHome;
import com.iss.itreasury.securities.apply.dataentity.*;
import com.iss.itreasury.securities.apply.dao.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.system.approval.dataentity.*;

public class ApplyDelegation
{

	private Apply applyFacade = null;

	public ApplyDelegation() throws RemoteException
	{
		try
		{
			ApplyHome home;
			try
			{
				home = (ApplyHome) EJBHomeFactory.getFactory().lookUpHome(ApplyHome.class);
			}
			catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory连接错误", e);
			}
			applyFacade = (Apply) home.create();
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException", ce);
		}

	}

	/**
	 *申请书的保存操作
	*/
	public long save(ApplyInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.save(info);
	}

	/**
	 *申请书的删除操作
	*/
	public void delete(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.delete(lID);
	}

	/**
	 *申请书的审核操作
	*/
	public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.check(info);
	}

	/**
	 *申请书的取消操作
	*/
	public void cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.delete(lID);
	}
	/**
	 *申请书的审批操作
	*/
	public long doApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.doApproval(info, approvalInfo);
	}
	/**
	 *申请书的取消审批操作
	*/
	public long cancelApproval(ApplyInfo info, ApprovalTracingInfo approvalInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.cancelApproval(info, approvalInfo);
	}	
	/**
	 *申请书的单笔查询操作
	*/
	public ApplyInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.findByID(lID);
	}

	/**
	 *申请书的多笔查询操作
	*/
	public Collection findByMultiOption(ApplyQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.findByMultiOption(qInfo);
	}

	/**
	 *申请书下的投标区间保存操作
	*/
	public long saveBidRange(BidRangeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.saveBidRange(info);
	}

	/**
	 *申请书下的投标区间查询操作
	*/
	public Collection findBidRangeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.findBidRangeByApplyID(lApplyID);
	}

	/**
	 *申请书下的投标区间删除操作
	*/
	public void deleteBidRange(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.deleteBidRange(lIDList);
	}

	/**
	 *申请书下的债券种类保存操作
	*/
	public long saveBondType(BondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.saveBondType(info);
	}

	/**
	 *申请书下的债券种类查询操作
	*/
	public Collection findBondTypeByApplyID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		return applyFacade.findBondTypeByApplyID(lApplyID);
	}

	/**
	 *申请书下的债券种类删除操作
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.deleteBondType(lIDList);
	}

	public void transferApplyRight(long[] lID,long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		applyFacade.transferApplyRight(lID,lUserID);
	}

	public BondTypeInfo findBondTypeByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		SEC_BondTypeDAO dao = new SEC_BondTypeDAO();
		BondTypeInfo info = null;

		try
		{
			info = (BondTypeInfo) dao.findByID(lID, BondTypeInfo.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return info;

	}

	/**
	 * 检查资金拆借授信额度
	 * @param counterpartID	交易对手
	 * @param transactionTypeID 交易类型
	 * @param newPledgeSecuritiesAmount 拆借金额
	 * @param lApplyID 申请书标示
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public boolean checkTransactionTypeID(long lTransactionTypeID) throws java.rmi.RemoteException, SecuritiesException
	{
		boolean bResult = false;
		SEC_ApplyDAO dao = new SEC_ApplyDAO();

		try
		{
			bResult = dao.checkTransactionTypeID(lTransactionTypeID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}

		return bResult;
	}
}
