package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attornmentapply.dao.AttornmentApplyDao;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.securitiescontract.dao.ContractBondTypeDao;
import com.iss.itreasury.securities.securitiescontract.dao.SecuritiesContractInterestDao;
import com.iss.itreasury.securities.securitiescontract.dataentity.ContractBondTypeInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractInterestInfo;
import com.iss.itreasury.securities.securitiescontract.dataentity.SecuritiesContractQueryInfo;
import com.iss.itreasury.securities.securitiescontractextend.bizlogic.SecuritiesContractExtend;
import com.iss.itreasury.securities.securitiescontractextend.bizlogic.SecuritiesContractExtendHome;
import com.iss.itreasury.securities.securitiescontractextend.dataentity.SecuritiesContractExtendInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;

/**
 * @Name: ExtendContractDelegation.java @Description: @
 * 
 * @Author : gqfang @Create Date: 2005-4-20 To change the template for this
 * generated type comment go to Window - Preferences - Java - Code Generation -
 * Code and Comments
 */
public class ExtendContractDelegation
{
	//System.out.println("---------------Enter in ExtendContractDelegation");
	private SecuritiesContractExtend contractFacade = null;
	public ExtendContractDelegation() throws RemoteException
	{
		try
		{
			SecuritiesContractExtendHome home;
			try
			{
				home = (SecuritiesContractExtendHome) EJBHomeFactory.getFactory().lookUpHome(SecuritiesContractExtendHome.class);
			}
			catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory���Ӵ���", e);
			}
			contractFacade = (SecuritiesContractExtend) home.create();
		}
		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException", ce);
		}
	}
	/**
	 * ���ҵ�����ͬ��Ϣ
	 * 
	 * @param lID
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public SecuritiesContractExtendInfo findByID(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findByID(lID);
	}
	/**
	 * �����ͬ��Ϣ���������޸ĺ�ͬ
	 * 
	 * @param info
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long save(SecuritiesContractExtendInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.save(info);
	}
	/**
	 * ȡ����ͬ
	 * 
	 * @param lID
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public long cancel(long lID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.cancel(lID);
	}
	/**
	 * ���ҷ��������ĺ�ͬ��Ϣ�������޸Ĳ��Һ���˲���
	 * 
	 * @param qInfo
	 * @return @throws
	 * java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public java.util.Collection findByMultiOption(SecuritiesContractQueryInfo qInfo) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findByMultiOption(qInfo);
	}
	/**
	 * ������ͬ
	 * 
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
	 * 
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
	 * 
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void transferContractRight(long lID[], long lUserID) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.transferContractRight(lID, lUserID);
	}
	/**
	 * �ֶ�������ͬ��֧����������
	 * 
	 * @param lID
	 * @throws java.rmi.RemoteException
	 * @throws SecuritiesException
	 */
	public void endContract(long lID[]) throws java.rmi.RemoteException, SecuritiesException
	{
		contractFacade.endContract(lID);
	}
	/**
	 * ��ͬ�µ�ծȯ���ౣ�����
	 */
	public long saveBondType(ContractBondTypeInfo info) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.saveBondType(info);
	}
	/**
	 * ��ͬ�µ�ծȯ�����ѯ����
	 */
	public java.util.Collection findBondTypeByContractID(long lApplyID) throws java.rmi.RemoteException, SecuritiesException
	{
		return contractFacade.findBondTypeByContractID(lApplyID);
	}
	/**
	 * ��ͬ�µ�ծȯ����ɾ������
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
}