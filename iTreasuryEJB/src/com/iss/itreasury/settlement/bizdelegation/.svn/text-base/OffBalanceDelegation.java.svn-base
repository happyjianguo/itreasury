/*
 * Created on 2003-9-16
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.CreateException;

import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.offbalance.bizlogic.OffBalance;
import com.iss.itreasury.settlement.offbalance.bizlogic.OffBalanceHome;
import com.iss.itreasury.settlement.offbalance.dataentity.OffBalanceInfo;
import com.iss.itreasury.settlement.offbalance.dataentity.OffBalanceParam;
import com.iss.itreasury.settlement.offbalance.exception.OffBalanceException;

/**
 * Title:        		iTreasury
 * Description:         OffBalanceDelegation
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              wlming 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceDelegation {
	private OffBalance offBalance = null;

	public OffBalanceDelegation() throws java.rmi.RemoteException {
		try {
			OffBalanceHome offBalanceHome = (OffBalanceHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceHome.class);
			offBalance = (OffBalance) offBalanceHome.create();
		} catch (Exception re) {
			re.printStackTrace();
		}
	}

	 /**
     * 保存记录
     * @param  OffBalanceParam parameter
     * @return void
     * @exception throws java.rmi.RemoteException, OffBalanceException
     */
    public void save(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
    	offBalance.save(parameter);
    }

    /**
     * 修改记录
     * @param  OffBalanceParam parameter
     * @return void
     * @exception throws java.rmi.RemoteException, OffBalanceException
     */
    public void update(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
    	offBalance.update(parameter);
    }

    /**
     * 删除记录
     * @param  long id
     * @return void
     * @exception throws java.rmi.RemoteException, OffBalanceException
     */
    public void delete(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
    	offBalance.delete(parameter);
    }

    /**
     * 通过ID查找结果集
     * @param  long id
     * @return OffBalanceInfo
     * @exception throws java.rmi.RemoteException, OffBalanceException
     */
    public OffBalanceInfo findByID(long id) throws java.rmi.RemoteException, OffBalanceException {
		return offBalance.findByID(id);
    }

    /**
     * 通过条件查找结果集
     * @param  OffBalanceInfo info, String stringOrder
     * @return Collection
     * @exception throws java.rmi.RemoteException, OffBalanceException
     */
    public Collection findByCondition(OffBalanceInfo info, String stringOrder) throws java.rmi.RemoteException, OffBalanceException {
		return offBalance.findByCondition(info, stringOrder);
    }

	/**
	 * 复核
	 * @param  OffBalanceParam parameter
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceException
	 */
	public void check(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
		offBalance.check(parameter);
	}

	/**
	 * 取消复核
	 * @param OffBalanceParam parameter
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceException
	 */
	public void cancleCheck(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
		offBalance.cancleCheck(parameter);
	}

	/**
	 * 表外业务明细账查询
	 * @param  OffBalanceParam parameter
	 * @return Collection
	 * @exception throws java.rmi.RemoteException, OffBalanceException
	 */
	public Collection findTableOutsideDetail(OffBalanceParam parameter,String orderByString) throws java.rmi.RemoteException, OffBalanceException {
		return offBalance.findTableOutsideDetail(parameter,orderByString);
    }

	/**
	 * 表外业务汇总查询
	 * @param  OffBalanceParam parameter
	 * @return Collection
	 * @exception throws java.rmi.RemoteException, OffBalanceException
	 */
	public Collection findTableOutsideCollect(OffBalanceParam parameter) throws java.rmi.RemoteException, OffBalanceException {
		return offBalance.findTableOutsideCollect(parameter);
    }
}