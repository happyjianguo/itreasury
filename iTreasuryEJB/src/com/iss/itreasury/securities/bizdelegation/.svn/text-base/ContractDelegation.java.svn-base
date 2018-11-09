/*
 * Created on 2004-4-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.util.*;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.securitiescontract.bizlogic.*;
import com.iss.itreasury.securities.securitiescontract.dataentity.*;
import com.iss.itreasury.securities.securitiescontract.dao.*;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.system.approval.dataentity.*;
import com.iss.itreasury.loan.attornmentapply.dao.*;
import com.iss.itreasury.loan.attornmentapply.dataentity.*;
import com.iss.itreasury.loan.contract.dao.ContractDao;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractDelegation
{
	private SecuritiesContract contractFacade = null;

	public ContractDelegation() throws RemoteException
	{
		try
		{
			SecuritiesContractHome home;
			try
			{
				home = (SecuritiesContractHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesContractHome.class);
			}
			catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory连接错误", e);
			}
			contractFacade = (SecuritiesContract) home.create();
		}
		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException", ce);
		}

	}

	/**
	 * 查找单条合同信息
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findByID(lID);
	}

	/**
	 * 保存合同信息，新增或修改合同
	 * @param info
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.save(info);
	}

	/**
	 * 取消合同
	 * @param lID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.cancel(lID);
	}

	/**
	 * 查找符合条件的合同信息，用于修改查找和审核查找
	 * @param qInfo
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findByMultiOption(qInfo);
	}

	/**
	 * 审批合同
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.check(atInfo);
	}

	/**
	 * 激活合同，支持批量激活
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.activateContract(lID);
	}

	/**
	* 转移合同管理人权限，支持批量转移
	* @param lID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferContractRight(long lID[],long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.transferContractRight(lID,lUserID);
	}

	/**
	 * 手动结束合同，支持批量结束
	 * @param lID 
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.endContract(lID);
	}

	/**
	 *合同下的债券种类保存操作
	*/
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.saveBondType(info);
	}

	/**
	 *合同下的债券种类查询操作
	*/
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findBondTypeByContractID(lApplyID);
	}

	/**
	 *合同下的债券种类删除操作
	*/
	public void deleteBondType(long[] lIDList) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.deleteBondType(lIDList);
	}

	public ContractBondTypeInfo findBondTypeByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		ContractBondTypeDao dao = new ContractBondTypeDao();
		ContractBondTypeInfo info = null;

		try
		{
			info = (ContractBondTypeInfo) dao.findByID(lID, ContractBondTypeInfo.class);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return info;

	}

	public Collection findAttornmentByApplyID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		Collection c = null;

		try
		{
			c = dao.findContractByRepurchaseID(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return c;

	}

	public AttornmentApplyInfo findAttornmentInfoByApplyID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		AttornmentApplyDao dao = new AttornmentApplyDao();
		AttornmentApplyInfo c = null;

		try
		{
			c = dao.findAttornmentByRepurchaseID(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return c;

	}
	
	public long countSecuritiesContractInterest(SecuritiesContractInterestInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
	    long lResult = -1;
	    SecuritiesContractInterestDao dao = new SecuritiesContractInterestDao();
	    
		try
		{
		    lResult = dao.countSecuritiesContractInterest(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return lResult;

	}
	
	public boolean isNull_leftoversattornmentamount(long  lContractID) throws java.rmi.RemoteException, SecuritiesException
	{
	    boolean lResult = false;
	    ContractDao contractDao = new ContractDao();
	    
		try
		{
		    lResult = contractDao.isNull_leftoversattornmentamount(lContractID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new SecuritiesException();
		}

		return lResult;

	}
}
