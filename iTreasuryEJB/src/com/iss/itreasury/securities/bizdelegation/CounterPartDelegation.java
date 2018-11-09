/*
 * Created on 2004-3-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.securities.setting.bizlogic.CounterPartBean;
import com.iss.itreasury.securities.setting.dataentity.CounterPartBankAccountInfo;
import com.iss.itreasury.securities.setting.dataentity.CounterPartInfo;

import com.iss.itreasury.util.*;

/**
 * @author hjliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CounterPartDelegation
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.SECURITIES, this);
	private CounterPartBean counterPartBeanFacade = null;

	public CounterPartDelegation() throws RemoteException
	{
		counterPartBeanFacade = new CounterPartBean();
	
	}

	/**
	 * ������޸Ľ��׶�������
	 * @param counterPartInfo  ���׶�������
	 * @throws SecuritiesException
	 */
	public void saveCounterPart(CounterPartInfo counterPartInfo) throws SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::saveCounterPart start");
		counterPartBeanFacade.saveCounterPart(counterPartInfo);
		log4j.debug("lhj debug info :: CounterPartDelegation::saveCounterPart start");
	}
	/**
	 * ������߽��׶��ֿ���������
	 * @param counterPartBankAccountInfo ���׶��ֿ���������
	 * @throws SecuritiesException
	 */
	public void saveCounterPartBankAccount(CounterPartBankAccountInfo counterPartBankAccountInfo) throws SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::saveCounterPartBankAccount start");
		counterPartBeanFacade.saveCounterPartBankAccount(counterPartBankAccountInfo);
		log4j.debug("lhj debug info :: CounterPartDelegation::saveCounterPartBankAccount end");
	}
  
    /**
     * ����һ�����׶�������
     * @param counterPartId ���׶�������ID
     * @return ���׶�������CounterPartInfo
     * @throws SecuritiesException
     */	
	public CounterPartInfo findCounterPartById(long counterPartId) throws SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::findCounterPartById start");
		CounterPartInfo counterPartInfo = (CounterPartInfo)counterPartBeanFacade.findCounterPartById(counterPartId);		
		log4j.debug("lhj debug info :: CounterPartDelegation::findCounterPartById end");
        return counterPartInfo;
	}

	/**
	 * ����һ�����׶��ֿ���������
	 * @param counterPartBankAccountId ���׶��ֿ���������ID
	 * @return ���׶��ֿ���������CounterPartBankAccountInfo
	 * @throws SecuritiesException
	 */
	public CounterPartBankAccountInfo findCounterPartBankAccountById(long counterPartBankAccountId) throws  SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::findCounterPartBankAccountById start");
		CounterPartBankAccountInfo bankInfo =  (CounterPartBankAccountInfo)counterPartBeanFacade.findCounterPartBankAccountById(counterPartBankAccountId);
		log4j.debug("lhj debug info :: CounterPartDelegation::findCounterPartBankAccountById end");
	    return bankInfo;
	}
    
    /**
     * �߼�ɾ��һ�����׶�������
     * @param counterPartId Ҫɾ�����׶������ϵģɣ�  
     * @throws SecuritiesException
     */
	public void deleteCounterPart(long counterPartId) throws  SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::deleteCounterPart start");
		counterPartBeanFacade.deleteCounterPart(counterPartId);
		log4j.debug("lhj debug info :: CounterPartDelegation::deleteCounterPart end");
	}
    /**
     * �߼�ɾ��һ�����׶��ֿ���������
     * @param counterPartBankAccountIdҪɾ�����׶��ֿ��������ϵģɣ� 
     * @throws SecuritiesException
     */
	public void deleteCounterPartBankAccount(long counterPartBankAccountId) throws  SecuritiesException
	{
		log4j.debug("lhj debug info :: CounterPartDelegation::deleteCounterPartBankAccount start");
		counterPartBeanFacade.deleteCounterPart(counterPartBankAccountId);
		log4j.debug("lhj debug info :: CounterPartDelegation::deleteCounterPartBankAccount end");
	}
	
	/**
	 * ���Ӳ���
	 * @param counterPartId
	 * @return
	 * @throws SecuritiesException
	 */
	public Collection findByLinkSearch(long counterPartId) throws SecuritiesException{
		log4j.debug("lhj debug info :: CounterPartDelegation::findByLinkSearch start");
		Collection counterPartCollection = (Collection)counterPartBeanFacade.findByLinkSearch(counterPartId);		
		log4j.debug("lhj debug info :: CounterPartDelegation::findByLinkSearch end");
		return counterPartCollection;
	}
	
	public Collection findByConditions(String conditions) throws SecuritiesException{
		Collection counterPartCollection = (Collection)counterPartBeanFacade.findByConditions(conditions);		
		return counterPartCollection;
	}

}
