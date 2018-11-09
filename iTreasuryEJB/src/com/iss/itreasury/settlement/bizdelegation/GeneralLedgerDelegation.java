/*
 * Created on 2003-9-18
 * 
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedger;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerHome;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.system.dao.PageLoader;

/**
 * 
 * @author yqwu
 */
public class GeneralLedgerDelegation
{
    private GeneralLedger generalLedgerFacade;

    public GeneralLedgerDelegation() throws IException,IRollbackException
    {
        try
        {
            GeneralLedgerHome home =
                (GeneralLedgerHome) EJBHomeFactory.getFactory().lookUpHome(
                    GeneralLedgerHome.class);
            generalLedgerFacade = home.create();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "IException in TransCurrentDepositDelegation",
                e);
        }
        catch (CreateException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "CreateException in TransCurrentDepositDelegation",
                e);
        }
    }

    /**
     * �������˶����¼
     * @param info GLSubjectDefinitionInfo
     * @return ���˶����¼id
     * @throws IException
     */
    public long addGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.addGLSubjectDefinition(info);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * �޸Ŀ�Ŀ�ܱ��¼
     * @param info ��Ŀ�ܱ��¼
     * @return ��Ŀ�ܱ��¼id
     * @throws IException
     */
    public long updateGLSubjectDefinition(GLSubjectDefinitionInfo info)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.updateGLSubjectDefinition(info);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return ��Ŀ�ܱ��¼
     * @throws IException
     */
    public GLSubjectDefinitionInfo findGLSubjectDefinitionByID(long id)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findGLSubjectDefinitionByID(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * ��ѯ���п�Ŀ�ܱ��¼
     * @param includeDisable true-������Ч��false-��������Ч
     * @param officeID ����ID �����-1���Դ�����
     * @param CurrencyID ���� �����-1���Դ�����
     * @param subjectType ��Ŀ���� �����-1���Դ�����
     * @return ���п�Ŀ�ܱ��¼����
     * @throws IException
     */
    public Collection findAllGLSubjectDefinition(
        boolean includeDisable,
        long officeID,
        long CurrencyID,
        long subjectType)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findAllGLSubjectDefinition(
                includeDisable,
                officeID,
                CurrencyID,
				subjectType);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * �޸Ŀ�Ŀ�ܱ��¼״̬
     * @param id ��Ŀ�ܱ��¼id
     * @param status ��Ŀ�ܱ��¼״̬
     * @return ��Ŀ�ܱ��¼id
     * @throws IException
     */
    public long updateGLSubjectDefinitionStatus(long id, long status)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.updateGLSubjectDefinitionStatus(
                id,
                status);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * ������¼���ö����¼
     * @param info GLEntryDefinitionInfo
     * @return ��¼���ö����¼id
     * @throws IException
     */
    public long addGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IException,IRollbackException
    {
        try
        {
        	return this.generalLedgerFacade.addGLEntryDefinition(info);

        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

    /**
     * ������¼���ö����¼
     * @param info GLEntryDefinitionInfo
     * @return ��¼���ö����¼id
     * @throws IException
     */
    public long addGLEntryDefinitionTemp(GLEntryDefinitionTempInfo info)
        throws IException,IRollbackException
    {
        try
        {

        	return this.generalLedgerFacade.addGLEntryDefinitionTemp(info); 

        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
    /**
     * ���·�¼���ö����¼
     * @param info GLEntryDefinitionInfo
     * @return ��¼���ö����¼id
     * @throws IException
     */
    public long updateGLEntryDefinition(GLEntryDefinitionInfo info)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.updateGLEntryDefinition(info);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

	/**
	 * ���·�¼���ö����¼
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return ��¼���ö����¼id
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public long updateGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.updateGLEntryDefinitionTemp(tempInfo);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IException
     */
    public GLEntryDefinitionInfo findGLEntryDefinitionByID(long id)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findGLEntryDefinitionByID(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
    
    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws IException
     */
    public GLEntryDefinitionTempInfo findGLEntryDefinitionTempInfoByID(long id)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findGLEntryDefinitionTempInfoByID(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
	/**
	 * ����ɾ����Ʒ�¼����
	 * @param id ��Ʒ�¼����id
	 * @return ��Ʒ�¼����id
	 * @throws IException
	 */
	public long deleteGLEntryDefinition(long id) throws IException,IRollbackException
	{
		try
		{
			return this.generalLedgerFacade.deleteGLEntryDefinition(id);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();e.printStackTrace();throw new IException(
				"TransCurrentDepositDelegation RemoteException",
				e);
		}		
	}

	/**
	 * ����ɾ����Ʒ�¼����
	 * @param id ��Ʒ�¼����id
	 * @return ��Ʒ�¼����id
	 * @throws IException
	 */
	public long deleteGLEntryDefinitionTemp(GLEntryDefinitionTempInfo tempInfo) throws IException,IRollbackException
	{
		try
		{
			return this.generalLedgerFacade.deleteGLEntryDefinitionTemp(tempInfo);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();e.printStackTrace();throw new IException(
				"TransCurrentDepositDelegation RemoteException",
				e);
		}		
	}
	
	/**
	 * ���� ��Ʒ�¼����
	 * @param id ��Ʒ�¼����id
	 * @return ��Ʒ�¼����id
	 * @throws IException
	 */
	public long checkGLEntryDefinitionTemp(String strTransactionType,long checkUserID,long officeID, long currencyID) throws IException,IRollbackException
	{
		try
		{
			return this.generalLedgerFacade.checkGLEntryDefinitionTemp(strTransactionType,checkUserID,officeID,currencyID);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();e.printStackTrace();throw new IException(
				"checkGLEntryDefinition RemoteException",
				e);
		}		
	}
    /**
     * ��ѯ���з�¼���ö�����¼
     * @param officeID ����ID
     * @param currencyID ����
     * @param orderType
     * @return ���з�¼���ö�����¼����
     * @throws IException
     */
    public Collection findAllGLEntryDefinition(long officeID, long currencyID,long orderType)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findAllGLEntryDefinition(
                officeID,
                currencyID,orderType);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }

	/**
	 * ��ѯ����δ���˺�����Ч��¼���ö�����¼
	 * @param officeID ����ID
	 * @param currencyID ����
	 * @param orderType long
	 * @return ����δ���˺�����Ч��¼���ö�����¼
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUncheckAndUsedGLEntryDefinition(long officeID, long currencyID,long orderType)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findAllUncheckAndUsedGLEntryDefinition(
                officeID,
                currencyID,orderType);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
    
	/**
	 * ��ѯ�������»�Ʒ�¼���ö�����¼���� ���޸� ����ɾ���� ֻ�� �¼�¼ ���� �ϵ� ����Ч�ļ�¼��
	 * @param officeID ����ID
	 * @param currencyID ����
	 * @param orderType long
	 * @return �������»�Ʒ�¼���ö�����¼���� ���޸� ����ɾ���� ֻ�� �¼�¼ ���� �ϵ� ����Ч�ļ�¼��
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
    public Collection findAllUnUseAndUsedGLEntryDefinition(long officeID, long currencyID,long orderType)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findAllUnUseAndUsedGLEntryDefinition(
                officeID,
                currencyID,orderType);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();e.printStackTrace();throw new IException(
                "TransCurrentDepositDelegation RemoteException",
                e);
        }
    }
    /**
     * ��ѯ����δ���� �� �Ѹ��� ��¼���ö�����¼ 
     * @param officeID ����ID
     * @param currencyID ����
     * @param orderType
     * @return ���з�¼���ö�����¼����
     * @throws IException
     */
    public Collection findAllGLEntryDefinitionTemp(String strState,long officeID, long currencyID,long orderType)
        throws IException,IRollbackException
    {
        try
        {
            return this.generalLedgerFacade.findAllGLEntryDefinitionTemp(strState, officeID, currencyID,orderType);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new IException( "findAllGLEntryDefinitionTemp RemoteException", e);
        }
    }
    
    
    /**
     * ��ѯ����δ���� �� �Ѹ��� ��¼���ö�����¼ ��ҳ��ѯ
     * @param officeID ����ID
     * @param currencyID ����
     * @param orderType
     * @return ���з�¼���ö�����¼����
     * @throws IException
     */
    public PageLoader findAllGLEntryDefinitionPagerLoaderTemp(long nStatusID,long officeID, long currencyID,long orderType,String sort)
    throws IException
    {
        try
        {
            return this.generalLedgerFacade.findAllGLEntryDefinitionPagerLoaderTemp(nStatusID, officeID, currencyID,orderType,sort);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            throw new IException( "findAllGLEntryDefinitionPagerLoaderTemp RemoteException", e);
        }
    }
    
    public static void main(String[] args) throws Exception,IRollbackException
    {
        GeneralLedgerDelegation deligation = new GeneralLedgerDelegation();
    }



}
