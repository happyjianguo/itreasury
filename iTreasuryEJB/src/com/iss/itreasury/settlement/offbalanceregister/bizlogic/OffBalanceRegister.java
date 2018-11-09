package com.iss.itreasury.settlement.offbalanceregister.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterException;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterDAOException;

/**
 * Created 2004-9-24 23:29:07
 * Code generated by the Sun ONE Studio EJB Builder
 * @author yiwang
 */
public interface OffBalanceRegister extends javax.ejb.EJBObject {
	/**
	 * 通过ID查找结果集
	 * @param  long id
	 * @return OffBalanceRegisterInfo
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public OffBalanceRegisterInfo findByID(long id) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 通过条件查找结果集
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return Collection
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public Collection findByCondition(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 保存
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void save(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 修改
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void update(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 修改状态
	 * @param  long statusID, String sTransNo
	 * @return void
	 * @exception throws OffBalanceRegisterException
	 */
	public void updateByTransNo(long statusID, String sTransNo) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 修改业务方向
	 * @param  String sTransNo, long direction
	 * @return void
	 * @exception throws OffBalanceRegisterException
	 */
	public void updateByTransNo(String sTransNo, long direction,long transactionType) throws java.rmi.RemoteException, OffBalanceRegisterException;

	/**
	 * 删除
	 * @param  long id
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void delete(long id) throws java.rmi.RemoteException, OffBalanceRegisterException;
}