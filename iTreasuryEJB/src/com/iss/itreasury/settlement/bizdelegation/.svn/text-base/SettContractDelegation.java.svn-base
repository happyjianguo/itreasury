/*
 * 创建日期 2004-9-10
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Collection;

import com.iss.itreasury.project.wisgfc.settlement.postsupervise.checkaccounts.dataentity.CheckAccountInfo;
import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.settlement.settcontract.bizlogic.SettContract;
import com.iss.itreasury.settlement.settcontract.bizlogic.SettContractHome;
import com.iss.itreasury.settlement.settcontract.dataentity.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.system.dao.PageLoader;
/**
 * 结算合同
 * @author yfan
 */
public class SettContractDelegation
{
	private SettContract settContractFacade = null;
	public SettContractDelegation() throws RemoteException
	{
		try
		{
		    SettContractHome home = (SettContractHome) EJBHomeFactory.getFactory().lookUpHome(SettContractHome.class);
		    settContractFacade = home.create();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage());
		}
	}
	
    /**
     *结算合同的保存操作
    */
    public long save(SettContractInfo info) throws java.rmi.RemoteException, SettlementException
    {        
        try 
        {
            return settContractFacade.save(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (SettlementException e) 
        {
            throw new SettlementDAOException(e);
        }
    }
    
    /**
     *结算合同的删除操作
    */
    public void delete(long id) throws java.rmi.RemoteException, SettlementException
    {
        try 
        {
            settContractFacade.delete(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (SettlementException e) 
        {
            throw new SettlementDAOException(e);
        }
    }

    /**
     *结算合同的审核操作
    */
    /*public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, SettlementException
    {
        try 
        {
            settContractFacade.check(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (SettlementException e) 
        {
            throw new SettlementDAOException(e);
        }
    }*/

    /**
     *结算合同的取消操作
    */
    public void cancel(long id) throws java.rmi.RemoteException, SettlementException
    {
        try 
        {
            settContractFacade.cancel(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (SettlementException e) 
        {
            throw new SettlementDAOException(e);
        }
    }
    
    /**
     *单笔查询操作
    */
    public SettContractInfo findByID(long id) throws java.rmi.RemoteException,SettlementException
    {
        try 
        {
            return (SettContractInfo)settContractFacade.findByID(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (SettlementException e) 
        {
            throw new SettlementDAOException(e);
        }
    }

    /**
     *结算合同的多笔查询操作
     */
    public Collection findByMultiOption(SettContractQueryInfo qInfo) throws java.rmi.RemoteException, SettlementException
    {
		Collection c = null;
	    try 
	    {
	        c = settContractFacade.findByMultiOption(qInfo);            
	    } 
	    catch (RemoteException re) 
	    {
	        throw re;
	    }
	    catch (SettlementException e) 
	    {
	        throw new SettlementDAOException(e);
	    }   
	    return c;
    }
    
    /**
	 *  合同分页记录
	 * @param CheckAccountInfo conditionInfo
	 * @return PageLoader
	 */
	public PageLoader getMultiOptin(SettContractQueryInfo conditionInfo)throws java.rmi.RemoteException, SettlementException{
		PageLoader pageLoader=settContractFacade.getMultiOptin(conditionInfo);		   
		return pageLoader;
	}
    

}
