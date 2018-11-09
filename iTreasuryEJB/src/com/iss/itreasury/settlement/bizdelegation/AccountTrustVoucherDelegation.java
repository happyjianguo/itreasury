/*
 * Created on 2004-3-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.consignvoucher.bizlogic.ConsignVoucher;
import com.iss.itreasury.settlement.consignvoucher.bizlogic.ConsignVoucherHome;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherConditionInfo;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccountTrustVoucherDelegation {
	private ConsignVoucher accountTrustVoucherFacade;
	public AccountTrustVoucherDelegation() throws RemoteException
	{
		try
		{
			ConsignVoucherHome home = (ConsignVoucherHome) EJBHomeFactory.getFactory().lookUpHome(ConsignVoucherHome.class);
			accountTrustVoucherFacade = (ConsignVoucher) home.create();
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
	 * 新增委托付款凭证 
	 * @param accountConditionInfo
	 * @return -1 新增不成功  >0 成功
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long add(AccountTrustVoucherConditionInfo accountConditionInfo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		lReturn = accountTrustVoucherFacade.save(accountConditionInfo);
		return lReturn;
	}

	/**
	 * 修改凭证状态 根据ID
	 */
	public long updateStatusByID(long lID,long lStatusID) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
		//填入需要update的值
		accountTrustVoucherInfo.setId(lID);
		accountTrustVoucherInfo.setStatusID(lStatusID);
		
		lReturn = accountTrustVoucherFacade.updateStatusByID(accountTrustVoucherInfo);
		return lReturn;
	}
	/**
	 * 用于取消复核、删除时调用
	 * 修改凭证状态 根据TransNo
	 * 返回：>0 成功 <0 失败
	 */
	public long updateStatusByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
		//填入需要update的值
		accountTrustVoucherInfo.setTransactionNo(strTransNo);
		
		lReturn = accountTrustVoucherFacade.updateStatusByTransNo(accountTrustVoucherInfo);
		return lReturn;
	}
	/**
	 * 使用凭证时调用 传入凭证号、密码
	 * @param strTransNo
	 * @param lStatusID
	 * @throws RemoteException
	 * @throws IRollbackException
	 * 返回：>0 成功 <0 失败
	 */
	public long updateStatusByUse(String strVoucherNo,String strPassword,long lAccountID,String strTransNo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
		//填入需要update的值
		accountTrustVoucherInfo.setVoucherNo(strVoucherNo);
		accountTrustVoucherInfo.setPassWord(strPassword);
		accountTrustVoucherInfo.setAccountID(lAccountID);
		accountTrustVoucherInfo.setTransactionNo(strTransNo);
		
		lReturn = accountTrustVoucherFacade.updateStatusByUse(accountTrustVoucherInfo);
		return lReturn;
	}

}
