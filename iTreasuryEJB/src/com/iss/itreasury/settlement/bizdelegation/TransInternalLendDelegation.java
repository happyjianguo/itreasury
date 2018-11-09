/**
 * Creat by kevin(������)
 * 2011-07-13
 * �ڲ����ҵ��
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;


import com.iss.itreasury.settlement.transinternallend.bizlogic.TransInternalLend;
import com.iss.itreasury.settlement.transinternallend.bizlogic.TransInternalLendHome;
import com.iss.itreasury.settlement.transinternallend.dataentity.QueryInternalLendConditionInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class TransInternalLendDelegation {
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private TransInternalLend transInternalLendFacade;
	public TransInternalLendDelegation() throws RemoteException
	{
		try
		{
			TransInternalLendHome home = (TransInternalLendHome) EJBHomeFactory.getFactory().lookUpHome(TransInternalLendHome.class);
			transInternalLendFacade = (TransInternalLend) home.create();
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CreateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("InternalLendDelegation::Constructor 11!!!~");
	}
	/**
	 * �ڲ����ҵ��-�ݴ�
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transTempSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transTempSave(info);	
	}
	/**
	 * �ڲ����ҵ��-����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transSave(info);	
	}
	/**
	 * ҵ����/ҵ�񸴺����Ӳ���
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public Collection transFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transFindByStatus(queryInfo);	
	}
	/**
	 * ͨ��id��ѯ��ϸ��Ϣ
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo findByID(long lID) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.FindTransInternalLendDetailByID(lID);	
	}
	/**
	 * ɾ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transDelete(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transDelete(info);	
	}
	/**
	 * �ڲ����-ƥ�����
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo match(TransInternalLendInfo conditonInfo) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.match(conditonInfo);	
	}
	/**
	 * �ڲ����-���˲���
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.check(info);	
	}
	/**
	 * �ڲ����-ȡ�����˲���
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.cancelCheck(info);	
	}
	/**
	 * �ڲ�����ջ�-�ݴ����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentTempSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentTempSave(info);	
	}
	/**
	 * �ڲ�����ջ�-�������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentSave(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentSave(info);	
	}
	/**
	 * ͨ��id��ѯ��ϸ��Ϣ
	 * @param lID
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo repaymentFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.FindTransInternalLendRepaymentDetailByID(lID);	
	}
	/**
	 * �ڲ�����ջ�-���Ӳ���
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transRepaymentFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException
	{
		return transInternalLendFacade.transRepaymentFindByStatus(queryInfo);
	}
	/**
	 * �ڲ�����ջ�-ɾ��
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public long transRepaymentDelete(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.transRepaymentDelete(info);	
	}
	/**
	 * �ڲ�����ջ�-ƥ��
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo repaymentMatch(TransInternalLendInfo conditonInfo) throws RemoteException, IRollbackException
	{
		return transInternalLendFacade.repaymentMatch(conditonInfo);	
	}
	/**
	 * �ڲ�����ջ�-����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.repaymentCheck(info);	
	}
	/**
	 * �ڲ�����ջ�-ȡ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransInternalLendInfo info) throws RemoteException,IRollbackException
	{
		return transInternalLendFacade.repaymentCancelCheck(info);	
	}
	
	

}
