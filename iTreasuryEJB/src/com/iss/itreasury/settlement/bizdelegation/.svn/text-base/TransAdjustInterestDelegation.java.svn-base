/*
 * Created on 2005-9-6
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transadjustinterest.bizlogic.*;
import com.iss.itreasury.settlement.transadjustinterest.dataentity.*;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author feiye
 * 
 * 累计费用及利息调整功能代理类
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class TransAdjustInterestDelegation
{
	
	private TransAdjustInterest adjustFacade;
	public TransAdjustInterestDelegation() throws RemoteException
	{
		try
		{
			TransAdjustInterestHome home = (TransAdjustInterestHome) EJBHomeFactory.getFactory().lookUpHome(TransAdjustInterestHome.class);
			adjustFacade = (TransAdjustInterest) home.create();
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
		}
		catch (CreateException ce)
		{
			ce.printStackTrace();
		}
		catch (IException ie)
		{
			ie.printStackTrace();
		}
	}	
	/**
     * 结算模块--查找(累计费用及利息调整)查找当前信息 
     * 如果起始、终止日都是今天，则不查历史信息
     */
    public Collection findForm(AccumulateFeeInfoQuery queryCondition) {
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findForm(queryCondition);
    }
    /**
     * 结算模块--查找(累计费用及利息调整)历史记录 
     */
    public Collection findFormHistory(AccumulateFeeInfoQuery queryCondition) {
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findFormHistory(queryCondition);
    }


    /**
     * 结算模块--根据ID查找记录(累计费用及利息调整)
     */
    public AdjustInterestInfo findAdjustByID(long lID) throws SettlementException{
    	//null:失败  其它:AdjustInterestInfo
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findAdjustByID(lID);
    }
    
    /**
     * 结算模块--根据条件查找(累计费用及利息调整)
     */
    public Collection findAdjustByCondition(AdjustInterestInfoQuery info) throws SettlementException{
    	//null:失败  其它:把回的记录集
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.findAdjustByCondition(info);
    }
   
    /**
     * 结算模块--添加(累计费用及利息调整)
     */
    public long addAdjust(AdjustInterestInfo info) throws SettlementException, IRollbackException, RemoteException{
    	//-1:失败  其它:添加的记录ID
    	//Modified by zwsun, 2007/7/9, 应用ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.addAdjust(info);   	
    }
    
    /**
     * 结算模块--删除(累计费用及利息调整)
     */
    public long delAdjust(long lID) throws SettlementException, IRollbackException, RemoteException{
    	//-1:失败 其它：删除的ID号
    	//Modified by zwsun, 2007/7/9, 应用ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.delAdjust(lID);
    }
    
    /**
     * 结算模块--值调整(累计费用及利息调整)
     */
    public long valueAdjust(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	//0:正常 -1:失败
    	//Modified by zwsun, 2007/7/9, 应用ejb
    	//TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();

    	return adjustFacade.valueAdjust(adjustInfo);
    }
    
    /**
     * 结算模块--复核(累计费用及利息调整)
     * 不要再用这个方法，现在改成下面的check(), zwsun
     */
    public long checkAdjust(AdjustInterestInfo adjustInfo) throws SettlementException{
    	//0:正常 -1:失败
    	TransAdjustInterestBiz adjustBiz=new TransAdjustInterestBiz();
    	return adjustBiz.checkAdjust(adjustInfo);
    }
	
    /**
     * 审批， Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @return
     * @throws SettlementException
     */
    public long doApproval(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.doApproval(adjustInfo);    	
    }
    
    /**
     * 取消审批，　Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @return
     * @throws SettlementException
     */
    public long cancelApproval(AdjustInterestInfo adjustInfo) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.cancelApproval(adjustInfo);    	
    }
    
    /**
     * 复核，　Added by zwsun, 2007/7/6
     * @param adjustInfo
     * @param isCancel　true表示取消复核
     * @return
     * @throws SettlementException
     */
    public long check(AdjustInterestInfo adjustInfo, boolean isCancel) throws SettlementException, IRollbackException, RemoteException{
    	return adjustFacade.check(adjustInfo, isCancel);     	
    }
}