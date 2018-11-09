/*
 * Created on 2004-8-2
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transabatement.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transabatement.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author gqzhang To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransAbatement extends javax.ejb.EJBObject
{

    /**
     * Method presave. 保存前的校验，1 重复交易
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long presave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method tempsave. 暂存业务
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long tempsave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method delete. 删除业务
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long delete(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method findAbatementInfoByID. 根据交易id查找交易信息
     * 
     * @param lTransID
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public TransAbatementInfo findAbatementInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method save.
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long save(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method findAbatementByQueryCondition. 根据组合条件查找交易信息
     * 
     * @param info
     * @return Vector
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public Vector findAbatementByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method check. 复核自动冲销
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long check(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method cancelCheck. 取消复核兼容业务
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long cancelCheck(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method match. 匹配自动冲销
     * 
     * @param info
     * @param TransAbatementInfo
     * @return TransCompatibilityInfo
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public TransAbatementInfo match(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method next. 自动冲销继续
     * 
     * @param info
     * @param TransAbatementInfo
     * @return TransAbatementInfo
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public TransAbatementInfo next(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;
    
    
    /**
     * 凭证下的票据查询操作
     * @param lTransDiscountCredenceID
     * @return
     * @throws java.rmi.RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID,String strOrder , boolean isdesc) throws java.rmi.RemoteException,IRollbackException, SettlementException;

    /**
     * 根据交易号查找自动冲销交易主键
     * @param strTransNo
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException,SettlementException;
}