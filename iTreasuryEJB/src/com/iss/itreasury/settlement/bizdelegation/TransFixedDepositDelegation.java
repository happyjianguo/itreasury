/*
 * Created on 2003-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;

//import com.iss.itreasury.settlement.bankinstruction.dao.BankInstructionDAO;
//import com.iss.itreasury.settlement.bankinterface.bizlogic.BankInstruction;
//import com.iss.itreasury.settlement.bankinterface.dataentity.BankInstructionInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.*;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.*;
/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedDepositDelegation
{
	//private com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositEJB ejb;
	private TransFixedDeposit transFixedDepositFacade;
		
	public TransFixedDepositDelegation() throws RemoteException,IRollbackException
	{
		
		try
		{
			TransFixedDepositHome home = (TransFixedDepositHome)EJBHomeFactory.getFactory().lookUpHome(TransFixedDepositHome.class);
			transFixedDepositFacade = home.create();
		}
		catch(Exception e){
			throw new RemoteException("CreateException in TransFixedDepositDelegation", e);			
		}
	}	 
	  
	/**
	 * ���ڣ�֪ͨ���������׵��ݴ淽����
	 * @param info, TransFixedOpenInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long openTempSave(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openTempSave(info);	
	}
	/**
	 * �������ڴ浥���׵��ݴ淽����
	 * @param info, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long changeTempSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeTempSave(info);	
	}
	/**
	 * �������ڴ浥���׵��ݴ淽����
	 * @param info, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,�������ڴ浥���׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long openChangeSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeTempSave(info);	
	}
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵��ݴ淽����
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long drawTempSave(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawTempSave(info);	
	}
	/**
	 * ��������ת�潻�׵��ݴ淽����
	 * @param info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */
	public long continueTempSave(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueTempSave(info);			
	}
	
	/**
	 * �������׵ı���
	 * @param Assemble, TransFixedOpenInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ���������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long openSave(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openSave(info);	
	}
	/**
	 * �������ڴ浥���׵ı���
	 * @param Assemble, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,�������ڴ浥���׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long changeSave(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeSave(info);	
	}
	
	/**
	 * ֧ȡ���׵ı���
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,���ڣ�֪ͨ��֧ȡ���׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long drawSave(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawSave(info);	
	}	
	/**
	 * ����ת�潻�׵ı���
	 * @param info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @return long ,���׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws java.rmi.RemoteException
	 */	
	public long continueSave(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueSave(info);			
	}
	/**
	 * ���ڣ�֪ͨ���������׵�ɾ��������
	 * @param info, TransFixedOpenInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ɾ���Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long openDelete(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openDelete(info);
	}
	/**
	 * �������ڴ浥���׵�ɾ��������
	 * @param info, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ɾ���Ļ������ڴ浥���׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long changeDelete(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeDelete(info);
	}
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵�ɾ��������
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ɾ���Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long drawDelete(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawDelete(info);			
	}
	/**
	 * ��������ת�潻�׵�ɾ��������
	 * @param info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ɾ���Ķ��ڣ����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long continueDelete(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueDelete(info);			
	}

	/**
	 * ���ڣ�֪ͨ���������׵ĸ��˷�����
	 * @param info, TransFixedOpenInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵Ķ��ڣ�֪ͨ���������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long openCheck(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFixedDepositFacade.openCheck(info);
//		//��������ָ��
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
		
	}
	/**
	 * �������ڴ浥���׵ĸ��˷�����
	 * @param info, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵Ķ��ڣ�֪ͨ���������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long changeCheck(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		long res =  transFixedDepositFacade.changeCheck(info);
		return res;
		
	}
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵ĸ��˷�����
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long drawCheck(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		long res = transFixedDepositFacade.drawCheck(info);
//		//��������ָ��
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
	}
	
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵ĸ��˷���ȡ�ò���֧ȡ�������´浥�ţ�
	 * @author ���ָ�
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return TransFixedOpenInfo ,�����˵Ķ��ڣ�֪ͨ�����׵��´浥����
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo getNewDepositNo(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		TransFixedOpenInfo nInfo = null;
		if(null != info){
			String OldDepositNo=info.getDepositNo();
			nInfo = transFixedDepositFacade.openFindByOldDepositNo(OldDepositNo);
		}
		return nInfo;
	}
	
	/**
	 * ��������ת�潻�׵ĸ��˷�����
	 * @param info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @return long ,�����˵Ķ��ڣ����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long continueCheck(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
			long res = transFixedDepositFacade.continueCheck(info);	
//			//��������ָ��
//			BankInstructionDAO bankInstructDAO = new BankInstructionDAO();
//			try
//			{
//				bankInstructDAO.sendBankInstruction(info.getTransNo());
//			}
//			catch (IException e)
//			{
//				Log.print("----------------------------��������ָ������쳣----------------");
//				throw new IRollbackException(null,e.getMessage(),e);
//			}
			return res;
	}
	/**
	 * ���ڣ�֪ͨ�����׵�ȡ�����˷�����
	 * @param info, TransFixedOpenInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵Ķ��ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long openCancelCheck(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openCancelCheck(info);	
	}
	/**
	 * �������ڴ浥���׵�ȡ�����˷�����
	 * @param info, TransFixedChangeInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵Ķ��ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long changeCancelCheck(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeCancelCheck(info);	
	}
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵�ȡ�����˷�����
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long drawCancelCheck(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawCancelCheck(info);			
	}
	/**
	 * ��������ת�潻�׵�ȡ�����˷�����
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return long ,��ȡ�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * @throws RemoteException
	 */
	public long continueCancelCheck(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueCancelCheck(info);
			
	}
	/**
	 * ���ڣ�֪ͨ��֧ȡ���׵ļ����ķ�����
	 * @param info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @return info, TransFixedDrawInfo, ����ʵ���ࣨinfo��
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawNext(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawNext(info);	
	}
	/**
	 * ��������ת�潻�׵ļ����ķ�����
	 * @param info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @return info, TransFixedContinueInfo, ����ʵ���ࣨinfo��
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueNext(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueNext(info);			
	}

	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ������������ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return FixedDepositAssemble,���ڣ�֪ͨ������ʵ����
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo openFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByID(lID);	
	}
	/**
	 * ���ݱ�ʶ��ѯ�������ڴ浥������ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return TransFixedChangeInfo,���ڣ�֪ͨ������ʵ����
	 * @throws RemoteException
	 */
	public TransFixedChangeInfo changeFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeFindByID(lID);	
	}
	/**
	 * ���ݽ��׺Ų�ѯ���ڣ�֪ͨ������������ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return FixedDepositAssemble,���ڣ�֪ͨ������ʵ����
	 * @throws RemoteException
	 */
	public TransFixedOpenInfo openFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByTransNo(strTransNo);	
	}
	/**
	 * ���ݽ��׺Ų�ѯ�������ڴ浥������ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return TransFixedChangeInfo,���ڣ�֪ͨ������ʵ����
	 * @throws RemoteException
	 */
	public TransFixedChangeInfo changeFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeFindByTransNo(strTransNo);	
	}
	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ��֧ȡ������ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return FixedDepositAssemble,���ڣ�֪ͨ������ʵ����
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawFindByID(long lID) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawFindByID(lID);	
	}
	/**
	 * ���ݱ�ʶ��ѯ���ڣ�����ת�潻����ϸ�ķ�����
	 * @param lID long , ���׵�ID
	 * @return FixedDepositAssemble,���ڽ���ʵ����
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueFindByID(long lID) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByID(lID);
			
	}
	/**
	 * ���ݽ��׺Ų�ѯ���ڣ�����ת�潻����ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return FixedDepositAssemble,���ڽ���ʵ����
	 * @throws RemoteException
	 */
	public TransFixedContinueInfo continueFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByTransNo(strTransNo);
			
	}
	//	added by qhzhou 2007.6.26
	//�������׵����Ӳ���,�������ڶ���֧ȡ���ɵĿ����浥

	/**
	 * ��������״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info,boolean isFilt) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByStatus(info,isFilt);	
	}
	/**
	 * ��������״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openFindByStatus(info);	
	}
	
	/**
	 * �������ڴ浥����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection changeFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		System.out.println("�������ڴ浥(findByStatus):----------(��ʼDelegation)");
		Collection coll=transFixedDepositFacade.changeFindByStatus(info);
		System.out.println("�������ڴ浥(findByStatus):----------(����Delegation)");
		return coll;
		
	}
	/**
	 * ����ת�����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection continueFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.continueFindByStatus(info);
			
	}
	/**
	 * ֧ȡ����״̬��ѯ�ķ�����
	 * @param QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * @return Collection ,���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawFindByStatus(info);	
	}

	/**
	 * ���ݽ��׺Ų�ѯ����֧ȡ������ϸ�ķ�����
	 * @param strTransNo String , ���׺�
	 * @return FixedDepositAssemble,���ڽ���ʵ����
	 * @throws RemoteException
	 */
	public TransFixedDrawInfo drawFindByTransNo(String strTransNo) throws RemoteException,IRollbackException
	{
		
		return transFixedDepositFacade.drawFindByTransNo(strTransNo);
			
	}
	/**
	 * ��������ƥ��ķ�����
	 * @param info , TransFixedOpenInfo,���ڣ�֪ͨ������ʵ����
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection openMatch(TransFixedOpenInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.openMatch(info);	
	}
	/**
	 * �������ڴ浥����ƥ��ķ�����
	 * @param info , TransFixedChangeInfo,�������ڴ浥ʵ����
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection changeMatch(TransFixedChangeInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.changeMatch(info);	
	}
	/**
	 * ֧ȡ����ƥ��ķ�����
	 * @param info , TransFixedDrawInfo,���ڣ�֪ͨ��֧ȡʵ����
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection drawMatch(TransFixedDrawInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.drawMatch(info);	
	}
	/**
	 * ����ת�渴��ƥ��ķ�����
	 * @param info , TransFixedContinueInfo,����ת��ʵ����
	 * @return Collection ,����FixedDepositResultInfo��ѯ���ʵ����ļ�¼��
	 * @throws RemoteException
	 */
	public Collection continueMatch(TransFixedContinueInfo info) throws RemoteException,IRollbackException
	{
		return transFixedDepositFacade.continueMatch(info);			
	}
	/**
	 * ����˵������������(����)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	/**
	 * ����˵������������(֧ȡ)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedDrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	/**
	 * ����˵������������(ת��)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long doApproval ( TransFixedContinueInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.doApproval(info);
	}
	
	/**
	 * ����˵����ȡ����������(���ڿ�����֪ͨ����)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedOpenInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
	/**
	 * ����˵����ȡ����������(����֧ȡ��֪֧ͨȡ)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedDrawInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
	/**
	 * ����˵����ȡ����������(ת��)
	 *  Method  doApproval.
	 * @param TransFixedOpenInfo info
	 * @return long transid
	 */
	public long cancelApproval ( TransFixedContinueInfo info)
		throws RemoteException, IRollbackException
	{
		return transFixedDepositFacade.cancelApproval(info);
	}
}
