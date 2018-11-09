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
     * Method presave. ����ǰ��У�飬1 �ظ�����
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long presave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method tempsave. �ݴ�ҵ��
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long tempsave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method delete. ɾ��ҵ��
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long delete(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method findAbatementInfoByID. ���ݽ���id���ҽ�����Ϣ
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
     * Method findAbatementByQueryCondition. ��������������ҽ�����Ϣ
     * 
     * @param info
     * @return Vector
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public Vector findAbatementByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method check. �����Զ�����
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long check(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method cancelCheck. ȡ�����˼���ҵ��
     * 
     * @param info
     * @return long
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long cancelCheck(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException;

    /**
     * Method match. ƥ���Զ�����
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
     * Method next. �Զ���������
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
     * ƾ֤�µ�Ʊ�ݲ�ѯ����
     * @param lTransDiscountCredenceID
     * @return
     * @throws java.rmi.RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID,String strOrder , boolean isdesc) throws java.rmi.RemoteException,IRollbackException, SettlementException;

    /**
     * ���ݽ��׺Ų����Զ�������������
     * @param strTransNo
     * @return
     * @throws RemoteException
     * @throws IRollbackException
     * @throws SettlementException
     */
    public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException,SettlementException;
}