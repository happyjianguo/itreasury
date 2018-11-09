/*
 * Created on 2004-8-2
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.bizlogic;
import java.rmi.RemoteException;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author gqzhang
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransCompatibility extends javax.ejb.EJBObject
{
	
	/**
	 * Method findAllTransTypeSetting.
	 * 查询所有有效的兼容业务类型
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Vector findAllTransTypeSetting(long lOfficeID, long lCurrencyID) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method findTypeSettingDetailByID.
	 * 查找交易类型设置信息
	 * @param lSettingID
	 * @return CompatibilityTypeSettingInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public CompatibilityTypeSettingInfo findTypeSettingDetailByID(long lSettingID) throws RemoteException, IRollbackException, SettlementException;
	
	/**
	 * Method presave.
	 * 保存前的校验，1 重复交易
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long presave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method tempsave.
	 * 暂存业务
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long tempsave(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method delete.
	 * 删除业务
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long delete(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method findCompatibilityInfoByID.
	 * 根据交易id查找交易信息
	 * @param lTransID
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransCompatibilityInfo findCompatibilityInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long save(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method findCompatibilityByQueryCondition.
	 * 根据组合条件查找交易信息
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Vector findCompatibilityByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method check.
	 * 复核兼容业务
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long check(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method cancelCheck.
	 * 取消复核兼容业务
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long cancelCheck(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	/**
	 * Method match.
	 * 匹配兼容业务
	 * @param info
	 * @param specialoperationinfoid
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransCompatibilityInfo match(TransCompatibilityInfo info) throws RemoteException, IRollbackException, SettlementException;
	
	
	/**
	 * Method getIDByTransNo.
	 * 根据交易号查找交易id
	 * @param strTransNo
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException,SettlementException;
}