/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.consignvoucher.bizlogic;
import java.rmi.RemoteException;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.consignvoucher.dao.sett_AccountTrustVoucherDAO;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherConditionInfo;
import com.iss.itreasury.settlement.consignvoucher.dataentity.AccountTrustVoucherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConsignVoucherEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * @param ctx javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
/**
 * 保存委托付款凭证
 * @param accountTrustVoucherInfo
 * @throws Exception
 */
	public long save(AccountTrustVoucherConditionInfo accountConditionInfo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long lExistVoucher = -1;// >0 凭证已经生成 <0 可以生成新的凭证
		sett_AccountTrustVoucherDAO accuntTrustVoucherDAO = new sett_AccountTrustVoucherDAO();
		
		try 
		{
			Log.print("into save");
			long lVoucherStart = Long.valueOf(accountConditionInfo.getVoucherStart()).longValue();
			long lVoucherEnd = Long.valueOf(accountConditionInfo.getVoucherEnd()).longValue();
			//将凭证循环的插入数据库
			for(long iVoucher = lVoucherStart ; iVoucher <= lVoucherEnd ; iVoucher++)
			{
				AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
				accountTrustVoucherInfo.setAccountID(accountConditionInfo.getAccountID());
				accountTrustVoucherInfo.setVoucherNo(DataFormat.formatInt(iVoucher,accountConditionInfo.getVoucherNum()));//得到一定位数的凭证号
				accountTrustVoucherInfo.setPassWord(DataFormat.randomNumberPassword(accountConditionInfo.getPasswordNum()));//得到一定位数的密码
				accountTrustVoucherInfo.setStatusID(SETTConstant.ConsignVoucherStatus.NOTUSE);////没有使用
				accountTrustVoucherInfo.setInputDate(Env.getSystemDate(accountConditionInfo.getOfficeID(),accountConditionInfo.getCurrencyID()));
				accuntTrustVoucherDAO.setUseMaxID();
				Log.print("----------------新增凭证号："+iVoucher+" 开始 ---------------");
				lReturn = accuntTrustVoucherDAO.add(accountTrustVoucherInfo);
				Log.print("----------------新增凭证号："+iVoucher+" 结束 ---------------");
				
				if(lReturn < 0)
				{
					Log.print("新增失败");
					break;
				}
			}
			Log.print("out save");
		}
		catch (IException e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
/**
 * 修改委托付款凭证状态 根据ID
 * @param accountTrustVoucherInfo
 * @throws Exception
 */
	public long updateStatusByID(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		sett_AccountTrustVoucherDAO accuntTrustVoucherDAO = new sett_AccountTrustVoucherDAO();
		
		try 
		{
			lReturn = accuntTrustVoucherDAO.updateStatusByID(accountTrustVoucherInfo.getId(),accountTrustVoucherInfo.getStatusID());
		}
		catch (IException e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}

	/**
	 * 修改委托付款凭证状态 根据 TransNo 用于交易删除
	 * @param accountTrustVoucherInfo
	 * @throws Exception
	 */
		public long updateStatusByTransNo(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException
		{
			long lReturn = -1;
			sett_AccountTrustVoucherDAO accuntTrustVoucherDAO = new sett_AccountTrustVoucherDAO();
			
			try 
			{
				lReturn = accuntTrustVoucherDAO.updateStatusByTransNo(accountTrustVoucherInfo.getTransactionNo());
			}
			catch (IException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
			return lReturn;
		}
		/**
		 * 用于凭证的使用
		 * @param accountTrustVoucherInfo
		 * @throws RemoteException
		 * @throws IRollbackException
		 */
		public long updateStatusByUse(AccountTrustVoucherInfo accountTrustVoucherInfo) throws RemoteException, IRollbackException
		{
			long lReturn = -1;
			sett_AccountTrustVoucherDAO accuntTrustVoucherDAO = new sett_AccountTrustVoucherDAO();
			
			try
			{
				lReturn = accuntTrustVoucherDAO.updateStatusByUse(accountTrustVoucherInfo.getVoucherNo(),accountTrustVoucherInfo.getPassWord(),accountTrustVoucherInfo.getAccountID(),accountTrustVoucherInfo.getTransactionNo());
			}
			catch (IException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
			return lReturn;
		}
	
}
