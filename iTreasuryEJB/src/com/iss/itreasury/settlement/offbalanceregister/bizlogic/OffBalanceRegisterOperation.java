/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalanceregister.bizlogic;

import java.util.Collection;

import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.offbalanceregister.bizlogic.OffBalanceRegister;
import com.iss.itreasury.settlement.offbalanceregister.bizlogic.OffBalanceRegisterHome;
import com.iss.itreasury.settlement.offbalanceregister.dataentity.OffBalanceRegisterInfo;
import com.iss.itreasury.settlement.offbalanceregister.exception.OffBalanceRegisterException;

/**
 * Title:        		iTreasury
 * Description:         OffBalanceRegisterOperation
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceRegisterOperation {
	private OffBalanceRegister offBalanceRegister = null;

	/**
	 * 构造函数
	 * @param  无
	 * @return 无
	 * @exception java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public OffBalanceRegisterOperation() throws java.rmi.RemoteException, OffBalanceRegisterException {
		try {
			OffBalanceRegisterHome offBalanceRegisterHome = (OffBalanceRegisterHome) EJBHomeFactory.getFactory().lookUpHome(OffBalanceRegisterHome.class);
			offBalanceRegister = (OffBalanceRegister) offBalanceRegisterHome.create();
		} catch (Exception e) {
			throw new java.rmi.RemoteException("System Error:" + e.getMessage(), e);
		}
	}

	/**
	 * 通过ID查找结果集
	 * @param  long id
	 * @return OffBalanceRegisterInfo
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public OffBalanceRegisterInfo findByID(long id) throws java.rmi.RemoteException, OffBalanceRegisterException {
		return this.offBalanceRegister.findByID(id);
	}

	/**
	 * 通过条件查找结果集
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return Collection
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public Collection findByCondition(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException {
		return this.offBalanceRegister.findByCondition(offBalanceRegisterInfo);
	}

	/**
	 * 保存
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void save(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException {
		this.offBalanceRegister.save(offBalanceRegisterInfo);
	}

	/**
	 * 修改
	 * @param  OffBalanceRegisterInfo offBalanceRegisterInfo
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void update(OffBalanceRegisterInfo offBalanceRegisterInfo) throws java.rmi.RemoteException, OffBalanceRegisterException {
		this.offBalanceRegister.update(offBalanceRegisterInfo);
	}

	/**
	 * 修改状态
	 * @param  long statusID, String sTransNo
	 * @return void
	 * @exception throws OffBalanceRegisterException
	 */
	public void updateByTransNo(long statusID, String sTransNo) throws java.rmi.RemoteException, OffBalanceRegisterException {
		this.offBalanceRegister.updateByTransNo(statusID, sTransNo);
	}

	/**
	 * 修改业务方向
	 * @param  String sTransNo, long direction
	 * @return void
	 * @exception throws OffBalanceRegisterException
	 */
	public void updateByTransNo(String sTransNo, long direction,long transactionType) throws java.rmi.RemoteException, OffBalanceRegisterException {
		this.offBalanceRegister.updateByTransNo(sTransNo, direction,transactionType);
	}

	/**
	 * 删除
	 * @param  long id
	 * @return void
	 * @exception throws java.rmi.RemoteException, OffBalanceRegisterException
	 */
	public void delete(long id) throws java.rmi.RemoteException, OffBalanceRegisterException {
		this.offBalanceRegister.delete(id);			
	}
}