/*
 * Created on 2006-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;
import com.iss.itreasury.settlement.transnoteacceptance.bizlogic.*;
import com.iss.itreasury.settlement.transnoteacceptance.dataentity.*;
import com.iss.itreasury.util.*;

public class TransNoteAcceptanceDelegation
{
	private TransNoteAcceptance transNoteAcceptanceFacade;
	
	//���캯����ʼ����EJB
	public TransNoteAcceptanceDelegation() throws RemoteException,IRollbackException
	{
		try
		{
			TransNoteAcceptanceHome home = (TransNoteAcceptanceHome)EJBHomeFactory.getFactory().lookUpHome(TransNoteAcceptanceHome.class);
			transNoteAcceptanceFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	 
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׼����ķ�����
	 * @param lID long , ���׵�ID
	 * @return TransAcceptanceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceNext(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceNext(info);	
	}
	
	/**
	 * ���ݱ�ʶ��ѯ��ҵƱ�ݳж�--���ڳжҽ�����ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return TransAcceptanceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByID(lID);	
	}
	
	/**
	 * ���ݽ��׺Ų�ѯ��ҵƱ�ݳж�--���ڳжҽ�����ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return TransAcceptanceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAcceptanceNoteAcceptanceInfo acceptanceFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByTransNo(strTransNo);	
	}
	
	/**
	 * ���������տ����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����TransAcceptanceNoteAcceptanceInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection acceptanceFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceFindByStatus(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׵��ݴ淽����
	 * @param info, TransAcceptanceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long acceptanceTempSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceTempSave(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׵ı���
	 * @param Assemble, TransAcceptanceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long acceptanceSave(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceSave(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׵�ɾ��
	 * @param Assemble, TransAcceptanceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long acceptanceDelete(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceDelete(info);
	}

	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׸���ƥ��ķ�����
	 * @param info , TransAcceptanceNoteAcceptanceInfo��ҵƱ�ݳж�--���ڳжҽ���ʵ����
	 * @return Collection ,����TransAcceptanceNoteAcceptanceInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection acceptanceMatch(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceMatch(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׵ĸ��˷�����
	 * @param info, TransAcceptanceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵���ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long acceptanceCheck(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transNoteAcceptanceFacade.acceptanceCheck(info);
		return res;
	}
	
	/**
	 * ��ҵƱ�ݳж�--���ڳжҽ��׵�ȡ�����˷�����
	 * @param info, TransAcceptanceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵ı����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long acceptanceCancelCheck(TransAcceptanceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.acceptanceCancelCheck(info);	
	}
	
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׼����ķ�����
	 * @param lID long , ���׵�ID
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceNext(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceNext(info);	
	}
	
	/**
	 * ���ݱ�ʶ��ѯ��ҵƱ�ݳж�--�渶�ջؽ�����ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByID(lID);	
	}
	
	/**
	 * ���ݽ��׺Ų�ѯ��ҵƱ�ݳж�--�渶�ջؽ�����ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @Return TransAdvancedReceviceNoteAcceptanceInfo,����ʵ����
	 * @throws RemoteException
	 */
	public TransAdvancedReceviceNoteAcceptanceInfo advancedReceviceFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByTransNo(strTransNo);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׸���״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @Return Collection ,����TransAdvancedReceviceNoteAcceptanceInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection AdvancedReceviceFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceFindByStatus(info);	
	}
	
	/**
	 *��ҵƱ�ݳж�--�渶�ջؽ��׵��ݴ淽����
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long AdvancedReceviceTempSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceTempSave(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׵ı���
	 * @param Assemble, TransAdvancedReceviceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,��ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long AdvancedReceviceSave(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceSave(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׵�ɾ��
	 * @param Assemble, TransAdvancedReceviceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,��ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long advancedReceviceDelete(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceDelete(info);
	}

	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׸���ƥ��ķ�����
	 * @param info , TransAdvancedReceviceNoteAcceptanceInfo,���������տ�ʵ����
	 * @Return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection advancedReceviceMatch(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceMatch(info);	
	}
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׵ĸ��˷�����
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,�����˵���ҵƱ�ݳж�--���ڳжҽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long advancedReceviceCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		long res =  transNoteAcceptanceFacade.advancedReceviceCheck(info);
		return res;
	}
	
	/**
	 * ��ҵƱ�ݳж�--�渶�ջؽ��׵�ȡ�����˷�����
	 * @param info, TransAdvancedReceviceNoteAcceptanceInfo, ����ʵ���ࣨinfo��
	 * @Return long ,��ȡ�����˵ı����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long advancedReceviceCancelCheck(TransAdvancedReceviceNoteAcceptanceInfo info) throws RemoteException,IRollbackException
	{
		return transNoteAcceptanceFacade.advancedReceviceCancelCheck(info);	
	}
	
}
