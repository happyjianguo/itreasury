/*
 * Created on 2004-7-30
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;
import javax.ejb.CreateException;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transabatement.bizlogic.TransAbatement;
import com.iss.itreasury.settlement.transabatement.bizlogic.TransAbatementHome;
import com.iss.itreasury.settlement.transabatement.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
/**
 * @author gqzhang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TransAbatementDelegation
{
	private TransAbatement transAbatement = null;
	public TransAbatementDelegation() throws RemoteException
	{
		try
		{
			TransAbatementHome home;
			try
			{
				home = (TransAbatementHome) EJBHomeFactory.getFactory().lookUpHome(TransAbatementHome.class);
			}
			catch (IException e)
			{
				throw new RemoteException("EJBHomeFactory���Ӵ���", e);
			}
			transAbatement = (TransAbatement) home.create();
		}
		catch (CreateException ce)
		{
			throw new RemoteException("����CreateException", ce);
		}
	}
	/**
	 * Method presave. ����ҵ��ǰ�ļ��
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long presave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.presave(info);
		//return -1;
	}
	/**
	 * Method tempsave. ����ǰ��У�飺�ظ�����
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long tempsave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.tempsave(info);
		//return -1;
	}
	/**
	 * Method save. ������ݽ���
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long save(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.save(info);
		//return -1;
	}
	/**
	 * Method delete. ɾ�����ݽ���
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long delete(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.delete(info);
		//return -1;
	}
	/**
	 * Method findCompatibilityByQueryCondition. ��������������Ҽ��ݽ���
	 * 
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Vector findAbatementByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.findAbatementByQueryCondition(info);
		//return null;
	}
	/**
	 * Method findCompatibilityInfoByID. �� �ݽ���id���ҽ�����Ϣ
	 * 
	 * @param lTransID
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransAbatementInfo findAbatementInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.findAbatementInfoByID(lTransID);
		//return null;
	}
	/**
	 * Method cancelCheck. ȡ������
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long cancelCheck(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.cancelCheck(info);
		//return -1;
	}
	/**
	 * Method check. ���˽���
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long check(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		long res = transAbatement.check(info);
////		��������ָ��
//		BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//		try
//		{
//			bankInstructDAO.sendBankInstruction(info.getTransNo());
//		}
//		catch (IException e)
//		{
//			Log.print("----------------------------��������ָ������쳣----------------");
//			throw new IRollbackException(null,e.getMessage(),e);
//		}
		return res;
		//return -1;
	}
	/**
	 * Method match.
	 * 
	 * @param info
	 * @param specialoperationid
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransAbatementInfo match(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.match(info);
		//return null;
	}
	/**
	 * Method next.
	 * 
	 * @param info
	 * @param TransAbatementInfo
	 * @return TransAbatementInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransAbatementInfo next(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.next(info);
		//return null;
	}
	/**
	 * 
	 * @param lTransDiscountCredenceID
	 * @return Collection
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID,String strOrder , boolean isdesc) throws java.rmi.RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.findBillByTransDiscountCredenceID(lTransDiscountCredenceID,strOrder,isdesc);
	}
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException, SettlementException
	{
		return transAbatement.getIDByTransNo(strTransNo);
	}
}