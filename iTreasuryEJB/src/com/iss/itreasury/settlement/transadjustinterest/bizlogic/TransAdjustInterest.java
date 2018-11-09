package com.iss.itreasury.settlement.transadjustinterest.bizlogic;
/**
 * @author zwsun, 2007/7/9
 */
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.iss.itreasury.settlement.transadjustinterest.dataentity.AdjustInterestInfo;
import com.iss.itreasury.util.IRollbackException;

public interface TransAdjustInterest extends EJBObject{
	/**
	 * 增加利息
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
	public long addAdjust(AdjustInterestInfo info) throws IRollbackException, RemoteException;
	/**
	 * 值调整
	 * @param adjustInfo
	 * @return
	 * @throws IRollbackException
	 */
    public long valueAdjust(AdjustInterestInfo adjustInfo) throws IRollbackException, RemoteException;	
	/**
	 * 删除利息
	 * @param lID
	 * @return
	 * @throws IRollbackException
	 */
    public long delAdjust(long lID) throws IRollbackException, RemoteException;
    /**
     * 审批
     * @param info
     * @return
     */
	public long doApproval(AdjustInterestInfo info) throws IRollbackException, RemoteException;
	/**
	 * 取消审批
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
	public long cancelApproval(AdjustInterestInfo info) throws IRollbackException, RemoteException;
	/**
	 * 复核
	 * @param info
	 * @return
	 * @throws IRollbackException
	 */
    public long check(AdjustInterestInfo info, boolean isCancel) throws IRollbackException, RemoteException;	
}
