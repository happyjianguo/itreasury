/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.consignvoucher.bizlogic;
import java.rmi.RemoteException;

import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherConditionInfo;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherInfo;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface ConsignVoucher extends javax.ejb.EJBObject
{
	/**
	 * 生成委托付款凭证
	 * @param accountTrustVoucherInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(AccountTrustVoucherConditionInfo accountConditionInfo) throws RemoteException, IRollbackException;
	/**
	 * 更新委托付款凭证状态 ID
	 * @param accountTrustVoucherInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateStatusByID(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException;
	/**
	 * 更新委托付款凭证状态 TransNo
	 * @param accountTrustVoucherInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateStatusByTransNo(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException;
	/**
	 * 用于凭证的使用
	 * @param accountTrustVoucherInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long updateStatusByUse(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException;
}
