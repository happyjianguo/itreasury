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
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException �쳣˵����
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
	 * @exception java.rmi.RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
/**
 * ����ί�и���ƾ֤
 * @param accountTrustVoucherInfo
 * @throws Exception
 */
	public long save(AccountTrustVoucherConditionInfo accountConditionInfo) throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		long lExistVoucher = -1;// >0 ƾ֤�Ѿ����� <0 ���������µ�ƾ֤
		sett_AccountTrustVoucherDAO accuntTrustVoucherDAO = new sett_AccountTrustVoucherDAO();
		
		try 
		{
			Log.print("into save");
			long lVoucherStart = Long.valueOf(accountConditionInfo.getVoucherStart()).longValue();
			long lVoucherEnd = Long.valueOf(accountConditionInfo.getVoucherEnd()).longValue();
			//��ƾ֤ѭ���Ĳ������ݿ�
			for(long iVoucher = lVoucherStart ; iVoucher <= lVoucherEnd ; iVoucher++)
			{
				AccountTrustVoucherInfo accountTrustVoucherInfo = new AccountTrustVoucherInfo();
				accountTrustVoucherInfo.setAccountID(accountConditionInfo.getAccountID());
				accountTrustVoucherInfo.setVoucherNo(DataFormat.formatInt(iVoucher,accountConditionInfo.getVoucherNum()));//�õ�һ��λ����ƾ֤��
				accountTrustVoucherInfo.setPassWord(DataFormat.randomNumberPassword(accountConditionInfo.getPasswordNum()));//�õ�һ��λ��������
				accountTrustVoucherInfo.setStatusID(SETTConstant.ConsignVoucherStatus.NOTUSE);////û��ʹ��
				accountTrustVoucherInfo.setInputDate(Env.getSystemDate(accountConditionInfo.getOfficeID(),accountConditionInfo.getCurrencyID()));
				accuntTrustVoucherDAO.setUseMaxID();
				Log.print("----------------����ƾ֤�ţ�"+iVoucher+" ��ʼ ---------------");
				lReturn = accuntTrustVoucherDAO.add(accountTrustVoucherInfo);
				Log.print("----------------����ƾ֤�ţ�"+iVoucher+" ���� ---------------");
				
				if(lReturn < 0)
				{
					Log.print("����ʧ��");
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
 * �޸�ί�и���ƾ֤״̬ ����ID
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
	 * �޸�ί�и���ƾ֤״̬ ���� TransNo ���ڽ���ɾ��
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
		 * ����ƾ֤��ʹ��
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
