/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */

package com.iss.itreasury.loan.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.bill.util.BILLConstant;
import com.iss.itreasury.bill.util.BillException;
import com.iss.itreasury.loan.base.LoanException;
import com.iss.itreasury.loan.transdiscountapply.bizlogic.TransDiscountApply;
import com.iss.itreasury.loan.transdiscountapply.bizlogic.TransDiscountApplyHome;
import com.iss.itreasury.loan.transdiscountapply.dao.TransDiscountApplyDAO;
import com.iss.itreasury.loan.transdiscountapply.dataentity.SelectBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyBillInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyQueryInfo;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class TransDiscountApplyDelegation {
	
	private TransDiscountApply transDiscountApplyFacade = null;
	
	//private LoanCommonSetting loanCommonSettingFacade = null;
	
	public TransDiscountApplyDelegation() throws RemoteException{
		try
		{
		    TransDiscountApplyHome home;
			try {
				home =
					(TransDiscountApplyHome) EJBHomeFactory.getFactory().lookUpHome(
					        TransDiscountApplyHome.class);
			} catch (IException e) {
				throw new RemoteException("EJBHomeFactory连接错误",e);
			}
			transDiscountApplyFacade = (TransDiscountApply) home.create();			
		}

		catch (CreateException ce)
		{
			throw new RemoteException("发生CreateException",ce);
		}

	}

    /**
     *申请书的保存操作
    */
    public long save(TransDiscountApplyInfo info) throws java.rmi.RemoteException, LoanException
    {        
        try 
        {
            return transDiscountApplyFacade.save(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }
    
    /**
     *申请书的删除操作
    */
    public void delete(long id) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            transDiscountApplyFacade.delete(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

    /**
     *申请书的审核操作
    */
    public void check(ApprovalTracingInfo info) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            
			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
            
//        	if(Env.getProjectName().equals(Constant.ProjectName.CPF))
//			{
//				transDiscountApplyFacade.cpfCheck(info);
//			} 
//			else transDiscountApplyFacade.check(info);
			
			transDiscountApplyFacade.check(info);
			
			/*  TOCONFIG—END  */
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

    /**
     *申请书的取消操作
    */
    public void cancel(long id) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            transDiscountApplyFacade.cancel(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }
    
    /**
     *单笔查询操作
    */
    public TransDiscountApplyInfo findById(long id) throws java.rmi.RemoteException,LoanException
    {
        try 
        {
            return (TransDiscountApplyInfo)transDiscountApplyFacade.findByID(id);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }

    /**
     *申请书的多笔查询操作
     */
    public Collection findByMultiOption(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
    {
		Collection c = null;
	    try 
	    {
            
			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */
			 
//	    	if(Env.getProjectName().equals(Constant.ProjectName.CPF))
//			{
//	    		c = transDiscountApplyFacade.cpfFindByMultiOption(qInfo);
//			}
//	    	else {
//	    		c = transDiscountApplyFacade.findByMultiOption(qInfo);  
//	    	}
	    	
			c = transDiscountApplyFacade.findByMultiOption(qInfo); 
			
			/*  TOCONFIG—END  */
	    } 
	    catch (RemoteException re) 
	    {
	        throw re;
	    }
	    catch (LoanException e) 
	    {
	        throw e;
	    }   
	    return c;
    }


    /**
     *申请书下的票据保存操作
     */
    public long saveBill(TransDiscountApplyBillInfo info) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            return transDiscountApplyFacade.saveBill(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }


    /**
     *申请书下的票据查询操作
     */
    public Collection findBillByTransDiscountApplyID(long lTransDiscountID, long lInOrOut) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            return transDiscountApplyFacade.findBillByTransDiscountApplyID(lTransDiscountID, lInOrOut);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }


    /**
     *申请书下的票据删除操作
     */
    public void deleteBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            transDiscountApplyFacade.deleteBill(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }


    /**
     *票据查询操作
     */
    public Collection findTransDiscountApplyBill(TransDiscountApplyQueryInfo qInfo) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            return transDiscountApplyFacade.findTransDiscountApplyBill(qInfo);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }


    /**
     *票据选择操作
     */
    public void saveTransDiscountApplyBill(SelectBillInfo info) throws java.rmi.RemoteException, LoanException
    {
        try 
        {
            transDiscountApplyFacade.saveTransDiscountApplyBill(info);            
        } 
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
    }
    
    /**
     * 计算转贴现申请下的贴现票据利息，
     * 操作Loan_DiscountFormBill表
     * @param lDiscountApplyID 贴现标识
     * @param  dRate        贴现利率
     * @param  tsDate       贴现日
     * @return 返回贴现票据的列表
     */
     public Collection calculateTransDiscountBillInterest(TransDiscountApplyQueryInfo qInfo)
     throws java.rmi.RemoteException, LoanException
     {
         Collection c = null;
         try 
         {
             c = transDiscountApplyFacade.calculateTransDiscountBillInterest(qInfo);            
         } 
         catch (RemoteException re) 
         {
             throw re;
         }
         catch (LoanException e) 
         {
             throw e;
         }
         return c;
     }

	/**
     *申请书的单笔查询操作
    */
    public TransDiscountApplyBillInfo findBillByID(long lID) throws java.rmi.RemoteException, LoanException
    {
         TransDiscountApplyBillInfo info = new TransDiscountApplyBillInfo();
         try 
         {
             info = transDiscountApplyFacade.findBillByID(lID);            
         } 
         catch (RemoteException re) 
         {
             throw re;
         }
         catch (LoanException e) 
         {
             throw e;
         }
         return info;
     }
	/**
	 * 申请书票据审批动作
	 */
	/**
 * added by qhzhou 2007/09/22
 * @param info
 * @return
 * @throws RemoteException
 * @throws IRollbackException
 */
    public long doApprovalApplyBillInit(TransDiscountApplyInfo nInfo) throws RemoteException,
	IRollbackException,IException , BillException{
		try {
			
			 return transDiscountApplyFacade.doApprovalApplyBillInit(nInfo);
		}  
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
	}
   	/**
     * added by qhzhou 2007/09/22
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long doCancleApprovalApplyBillInit(TransDiscountApplyInfo nInfo) throws RemoteException,
	IRollbackException,IException{
		try {
			
			 return transDiscountApplyFacade.doCancleApprovalApplyBillInit(nInfo);
		}  
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (LoanException e) 
        {
            throw e;
        }
	}
   	/**
     * added by qhzhou 2007/09/22
     * @param info
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     */
    public long submitApplyAndApprovalBillInit(TransDiscountApplyInfo nInfo) throws RemoteException,
	IRollbackException,IException{
		try {
			
			 return transDiscountApplyFacade.submitApplyAndApprovalBillInit(nInfo);
		}  
        catch (RemoteException re) 
        {
            throw re;
        }
        catch (IException e) 
        {
            throw e;
        }
	}

}