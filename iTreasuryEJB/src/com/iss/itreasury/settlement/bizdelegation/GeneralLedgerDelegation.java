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
     * 新增总账定义记录
     * @param info GLSubjectDefinitionInfo
     * @return 总账定义记录id
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
     * 修改科目总表记录
     * @param info 科目总表记录
     * @return 科目总表记录id
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
     * 通过标识查询
     * @param id long
     * @return 科目总表记录
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
     * 查询所有科目总表记录
     * @param includeDisable true-包括无效，false-不包括无效
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param CurrencyID 币种 如果是-1忽略此条件
     * @param subjectType 科目属性 如果是-1忽略此条件
     * @return 所有科目总表记录集合
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
     * 修改科目总表记录状态
     * @param id 科目总表记录id
     * @param status 科目总表记录状态
     * @return 科目总表记录id
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
     * 新增分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
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
     * 新增分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
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
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
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
	 * 更新分录设置定义记录
	 * @param tempInfo GLEntryDefinitionTempInfo
	 * @return 分录设置定义记录id
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
     * 通过标识查询
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
     * 通过标识查询
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
	 * 物理删除会计分录定义
	 * @param id 会计分录定义id
	 * @return 会计分录定义id
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
	 * 物理删除会计分录定义
	 * @param id 会计分录定义id
	 * @return 会计分录定义id
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
	 * 复核 会计分录定义
	 * @param id 会计分录定义id
	 * @return 会计分录定义id
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
     * 查询所有分录设置定义表记录
     * @param officeID 机构ID
     * @param currencyID 币种
     * @param orderType
     * @return 所有分录设置定义表记录集合
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
	 * 查询所有未复核和已生效分录设置定义表记录
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有未复核和已生效分录设置定义表记录
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
	 * 查询所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
	 * @param officeID 机构ID
	 * @param currencyID 币种
	 * @param orderType long
	 * @return 所有最新会计分录设置定义表记录（已 做修改 或者删除的 只查 新纪录 不查 老的 已生效的记录）
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
     * 查询所有未复核 和 已复核 分录设置定义表记录 
     * @param officeID 机构ID
     * @param currencyID 币种
     * @param orderType
     * @return 所有分录设置定义表记录集合
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
     * 查询所有未复核 和 已复核 分录设置定义表记录 分页查询
     * @param officeID 机构ID
     * @param currencyID 币种
     * @param orderType
     * @return 所有分录设置定义表记录集合
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
