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
				throw new RemoteException("EJBHomeFactory���Ӵ���", e);
			}
			contractFacade = (SecuritiesContract) home.create();
		}
		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException", ce);
		}

	}

	/**
	 * ���ҵ�����ͬ��Ϣ
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
	 * �����ͬ��Ϣ���������޸ĺ�ͬ
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
	 * ȡ����ͬ
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
	 * ���ҷ��������ĺ�ͬ��Ϣ�������޸Ĳ��Һ���˲���
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
	 * ������ͬ
	 * @param atInfo
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void check(ApprovalTracingInfo atInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.check(atInfo);
	}

	/**
	 * �����ͬ��֧����������
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void activateContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.activateContract(lID);
	}

	/**
	* ת�ƺ�ͬ������Ȩ�ޣ�֧������ת��
	* @param lID
	* @throws java.rmi.RemoteException
	* @throws SecuritiesException
	*/
	public void transferContractRight(long lID[],long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.transferContractRight(lID,lUserID);
	}

	/**
	 * �ֶ�������ͬ��֧����������
	 * @param lID 
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.endContract(lID);
	}

	/**
	 *��ͬ�µ�ծȯ���ౣ�����
	*/
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.saveBondType(info);
	}

	/**
	 *��ͬ�µ�ծȯ�����ѯ����
	*/
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findBondTypeByContractID(lApplyID);
	}

	/**
	 *��ͬ�µ�ծȯ����ɾ������
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
