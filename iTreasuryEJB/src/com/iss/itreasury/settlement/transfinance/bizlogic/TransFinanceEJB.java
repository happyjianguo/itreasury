/*
 * Created on 2006-4-20
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfinance.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.leasehold.dao.LoanAssureChargeFormDao;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReturnFinanceDao;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReceiveFinanceDao;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReceiveFinanceInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
import org.json.*;
import com.iss.itreasury.util.DataFormat;
/**
 * @author feiye
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFinanceEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException �쳣˵����
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception RemoteException �쳣˵����
	 */
	public void ejbRemove() throws RemoteException
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
	 * @exception RemoteException �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}

	//******�û��Լ������*******//
	/**
	 * ���������տ�׵��ݴ淽����
	 * 
	 * 1��������
	 *    TransReceiveFinanceInfo,�տ� ����ʵ����
	 * 
	 * 2������ֵ��
	 *    long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����lID����-1�����÷���receiveCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ�
	 * ��
	 *             ���÷���Sett_TransReceiveFinanceDAO.update()���潻�׼�¼��Ϣ��
	 *             
	 * ���÷���Sett_TransReceiveFinanceDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 *   
	 * ��2�����lID��-1�����÷���Sett_TransReceiveFinanceDAO.add()���潻�׼�¼��Ϣ��
	 *            
	 * ���÷���Sett_TransReceiveFinanceDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long receiveTempSave(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//		�����������Ϣ
			if (info.getID() <= 0) //����
			{
				lReturn = dao.add(info);
				if (lReturn != -1)
				{
					//����״̬Ϊδ����
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
				}
			}
			else //�޸��ݴ�
				{
				//�ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//���޸Ĺ�
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("55555555555555555555555555555555555");
					
					Log.print("----�տ��ݴ淽���и��´����״̬----");
					Log.print("----��ʼУ��ſ�֪ͨ��״̬----");
					if (!dao.checkPayForm(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----�ſ�֪ͨ���Ѿ���ʹ��----");
						throw new IRollbackException(mySessionCtx, "�ſ�֪ͨ���Ѿ���ʹ��");
					}
//					log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
//					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
//					log.debug("====�ı����====");
					
					//�ݴ��ʱ�򣬲��ø��ķſ�֪ͨ����״̬��ȫ���޸� 2010-7-2
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//����״̬Ϊδ����
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	
	
	//����׵ļ���
	public TransReceiveFinanceInfo receiveNext(TransReceiveFinanceInfo info) throws IRollbackException, RemoteException
	{
		TransReceiveFinanceInfo receiveFinanceInfo = new TransReceiveFinanceInfo();
		
		//���ݷ��ʶ���
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		try {
			log.debug("---------��ʼreceiveNext---------------");
			receiveFinanceInfo=dao.next(info);
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//				throw e;
//		}
//		catch (IRollbackException e)
//		{
//				throw e;
//		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			
		}
		log.debug("---------����receiveSave---------------");
		
		return receiveFinanceInfo;
	}
	

	/**
	 * ���������տ� �տ�׵ı��淽����
	 * 
	 * 1�������� TransReceiveFinanceInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,���������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���TransReceiveFinanceInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��TransMarginOpenInfo ��
	 * ����ǿգ�˵�����޸ı���: ���÷���this.receiveCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * ���÷�����this.receiveFindByID(),�õ�����ԭ���Ľ���ʵ����TransReceiveFinanceInfo��
	 * 
	 * ���÷�����AccountDetail.deleteMarginDeposit()���ع�ԭ���Ĳ�����ע�������ԭ�� --kkf
	 * ��ʵ��TransReceiveFinanceInfo�� ��2�����÷�����Sett_TransReceiveFinanceDAO.add()
	 * ������Ϣ�� ��3�����÷�����AccountDetail.saveOpenMarginDeposit()�����в����� --kkf
	 * ��4�����÷�����Sett_TransReceiveFinanceDAO.updateStatus() ���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long receiveSave(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		
		//���ݷ��ʶ���
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//�˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		
		log.debug("---------��ʼreceiveSave---------------");
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//��õ�ǰ״̬
			long lStatus = info.getStatusID();
			//�ݴ����ݱ���
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				
				for(int i=0;i<50;i++)
					System.out.println("11111111111111111111111111111111");
				log.debug("----------��ǰ״̬Ϊ�ݴ�-------------");
				
				/*
				//				�жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//�ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//���޸Ĺ�
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//�ж��Ƿ񱻷Ƿ��޸Ĺ�										
				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.receiveCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//δ�Ƿ��޸�
					log.debug("----------��ʼ�����������޽�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));	//ֻ���ں�̨��������� 
					lReturn = dao.update(info);
					log.debug("----------���������������޽�����Ϣ-------------");
					//�����������˻����
					log.debug("----------��ʼaccountBookOperation::saveReceiveFinance-------------");
					accountBookOperation.saveReceiveFinance(info);
					log.debug("----------����accountBookOperation::saveReceiveFinance-------------");
					//ͨ�����߲����ӿ����ȡ�½��׺�
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					log.debug("----------�½��׺���: " + transNo + " -------------");
					info.setTransNo(transNo);
					//�޸�
					lReturn = dao.update(info);
					//�޸Ľ��׵�״̬Ϊ����
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
					log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====�ı����====");
				}
			}
			else //�����ݴ�
				{
				
				for(int i=0;i<50;i++)
					System.out.println("22222222222222222222222222222");
				log.debug("----------��ǰ״̬�����ݴ�-------------");
				
				/*
				//�жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info))
				{
					log.debug("----------�浥���ظ�������ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//��ȡ��ǰ���׺�
				String transNo = info.getTransNo();
				//��־λ���Ƿ����������׺�
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals(""))
				{
					for(int i=0;i<50;i++)
						System.out.println("33333333333333333333333333333333333");
					
					log.debug("----------����������-------------");
					//δ��������������½��׺�
					bNewTransNo = true;
					//ͨ�����߲����ӿ����ȡ�½��׺�					
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------�½��׺���: " + transNo + " -------------");
					//����					
					log.debug("----------��ʼ�������ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					
					// ��3�����÷��������в�����
					log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveReceiveFinance(info);
					log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					lReturn = dao.add(info);
					log.debug("----------�����������ڽ�����Ϣ-------------");
					/*
					//��д����ָ��
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
					*/
					Log.print("----�տ�淽���и��´����״̬----");
					Log.print("----��ʼУ��ſ�֪ͨ��״̬----");
					if (!dao.checkPayForm(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----�ſ�֪ͨ���Ѿ���ʹ��----");
						throw new IRollbackException(mySessionCtx, "�ſ�֪ͨ���Ѿ���ʹ��");
					}
					log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====�ı����====");
					
					//�޸Ľ��׵�״̬Ϊ���档
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("444444444444444444444444444444444444");
					
					//��������� �������ٱ���
					//�ж�״̬�Ƿ�Ϸ�
					log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
					TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					long lNewStatusID = newInfo.getStatusID();
					//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//���޸Ĺ�
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//�ж��Ƿ񱻷Ƿ��޸Ĺ�							
					log.debug("----------�Ǳ����ٱ���-------------");				
					if (this.receiveCheckIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//������ɾ�����ݿ��е����еĴ浥
						log.debug("-------ɾ���˲��оɵ���Ϣ--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						//ɾ���˲���Ϣ
						log.debug("------��ʼɾ���˲���Ϣ--------");					
						accountBookOperation.deleteReceiveFinance(newInfo);
						log.debug("------����ɾ���˲���Ϣ--------");
						
						//δ�Ƿ��޸�
						log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------�������¶��ڽ�����Ϣ-------------");
						//					�����������˻����
						log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveReceiveFinance(info);
						log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
						//�޸�
						lReturn = dao.update(info);
						//					�޸Ľ��׵�״̬Ϊ����
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
						dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.USED);
						log.debug("====�ı����====");
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����receiveSave---------------");
		return lReturn;
	}

	/**
	 * ���������տ� �տ�׵�ɾ��������
	 * 
	 * 1��������
	 *    TransMarginOpenInfo ����ʵ����
	 * 
	 * 2������ֵ��
	 *    long ,��ɾ�������������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   ��1�����÷���this.opencheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 *   ��2���жϲ���TransMarginOpenInfo �еı�����ʵ�����״̬��
	 *        ������ݴ棺
	 *            
	 * ���÷�����Sett_TransReceiveFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч
	 * ����
	 *        ����Ǳ��棺
	 *            
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransMarginOpenInfo
	 *            
	 * ���÷�����Sett_TransReceiveFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч
	 * ����
	 * @roseuid 3F73AE9E010B
	 */
	public long receiveDelete(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//����ʱʹ��		
		long sessionID = -1;
		
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------��ʼreceiveDelete---------------");
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			
			System.out.println("�õ�ҳ�洫�����ļ�¼״̬Ϊinfo.getStatusID():"+info.getStatusID());
			System.out.println("�ոմ����ݿ���������״̬ΪlNewStatusID:"+lNewStatusID);
			
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			
			
			//����Ƿ��޸Ĺ�
			boolean flag = this.receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//�ж��Ƿ��ݴ�
				if (info.getTransNo() == null || info.getTransNo().equals(""))
				{
					for(int i=0;i<50;i++)
						System.out.println("66666666666666666666666666666666666");
					
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
				else
				{
					for(int i=0;i<50;i++)
						System.out.println("77777777777777777777777777777777777777777");
					
					log.debug("---------��ʼaccountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransReceiveFinanceInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteReceiveFinance(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------����accountBookOperation::deleteOpenFixedDeposit---------------");
					
					/*
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
					*/
				//}
				
					//���´����տ��״̬
					//if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){	//����Ǳ��棬���ķſ�֪ͨ��״̬,������ݴ治��
				
						if (!dao.checkPayForm(info.getReceiveFormID(),LOANConstant.LoanPayNoticeStatus.USED)){
						throw new IRollbackException(mySessionCtx,"�ſ�֪ͨ���Ѿ����޸�");
						}
					dao.updateLoanReceiveFormStatus(info.getReceiveFormID(), LOANConstant.LoanPayNoticeStatus.CHECK);
					}  //ɾ���ݴ�Ļ��������ж�״̬��ȫ���޸ģ�20100702
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����receiveDelete---------------");
		return lReturn;
	}

	/**
	 * ���������տ���׵ĸ��˷�����
	 * 
	 * 1��������
	 *   TransMarginOpenInfo ����ʵ����
	 * 2������ֵ��
	 *    long ,�����˵����������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 *   ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 *   
	 * ��3�����÷�����Sett_TransReceiveFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * @roseuid 3F73AEAF02F9
	 */
	public long receiveCheck(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;

		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReceiveFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);

			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//����Ƿ��޸Ĺ�
			boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				System.out.println("info.getPayBailAccountID():"+info.getPayBailAccountID());
				System.out.println("info.getPayBailBankID():"+info.getPayBailBankID());
				System.out.println("info.getPayPoundageAccountID():"+info.getPayPoundageAccountID());
				System.out.println("info.getPayBailAccountID():"+info.getPayPoundageBankID());
				
				
				//����������ſ��˻����ȣ�
				accountBookOperation.checkReceiveFinance(info);
				log.debug("-------�տ�ˣ�����״̬���Ѹ���---------");
				/*
				 	if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/

				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				//�Ƿ�������ӿ�
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//�Ƿ���Ҫ��������ָ��
				boolean bCreateInstruction = true;
				/*long bankID = transInfo.getBankID();
				try {
					//���ô˷�����bankID��ֵ��Ϊ��������ID
					bCreateInstruction = this.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
					e1.printStackTrace();
				}
				*/
				if(bIsValid && bCreateInstruction) {//������ӿڲ�������Ҫ��������ָ��
					Log.print("*******************��ʼ���������������տ�ָ�������**************************");
					try {
						
						//�������
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(info.getPayBailBankID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//��������ָ�����
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------�������������տ�ָ�����--------");
						
					} catch (Throwable e) {
						log.error("�������������տ�ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "�������������տ�ָ��ʧ�ܣ�"+e.getMessage());
					}
				}
				else 
				{
					log.debug("û�����нӿڻ���Ҫ��������ָ�");
				}
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------�տ�ˣ�����---------");
		return lReturn;
	}

	/**
	 * ���������տ���׵�ȡ�����˷�����
	 * 
	 * 1��������
	 *   TransMarginOpenInfo ����ʵ����
	 * 2������ֵ��
	 *    long ,��ȡ�����˵����������տ�׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 *   
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ���
	 * ��
	 *   
	 * ��3�����÷�����Sett_TransReceiveFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * @roseuid 3F73AEB30222
	 */
	public long receiveCancelCheck(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		System.out.println("��ҳ�洫�����������˵�getInputUserIDΪ"+info.getInputUserID());
		System.out.println("��ҳ�洫�����ĸ����˵�getCheckUserIDΪ"+info.getCheckUserID());
		System.out.println("��ҳ�洫�����������˵�getStatusIDΪ"+info.getStatusID());
		
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;
		
		Sett_TransReceiveFinanceDao depositDao = new Sett_TransReceiveFinanceDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReceiveFinanceInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//����Ƿ��޸Ĺ�
			boolean flag = receiveCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				System.out.println("info.getPayBailAccountID():"+info.getPayBailAccountID());
				System.out.println("info.getPayBailBankID():"+info.getPayBailBankID());
				System.out.println("info.getPayPoundageAccountID():"+info.getPayPoundageAccountID());
				System.out.println("info.getPayBailAccountID():"+info.getPayPoundageBankID());
				
				//ȡ������
				accountBookOperation.cancelCheckReceiveFinance(info);
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/
				
				//�����Լ�ҵ���״̬
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}

			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		
		return lReturn;
	}

	/**
	 * ���ݱ�ʶ��ѯ���������տ� ������ϸ�ķ�����
	 * 
	 * 1��������
	 *    lID long , �տ�׵�ID
	 * 
	 * 2������ֵ��
	 *    TransReceiveFinanceInfo,���������տ��ʵ����
	 * 
	 * 3���߼�˵����
	 *    ��1�����÷�����Sett_TransReceiveFinanceDao.findByID() 
	 * �õ��տ�׵���ϸ��TransReceiveFinanceInfo
	 * @roseuid 3F73AEB8007A
	 */
	public TransReceiveFinanceInfo receiveFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			info = dao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * ���ݽ��׺Ų�ѯ���������տ����ϸ�ķ�����
	 * 
	 * 1��������
	 *    strTransNo , �տ�׺�
	 * 
	 * 2������ֵ��
	 *    TransMarginOpenInfo,�������� �տ��ʵ����
	 * 
	 * 3���߼�˵����
	 *    ��1�����÷�����Sett_TransReceiveFinanceDao.findByID() 
	 * �õ��տ�׵���ϸ��TransReceiveFinanceInfo��
	 * @roseuid 3F73AEB8007A
	 */
	public TransReceiveFinanceInfo receiveFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransReceiveFinanceInfo info = new TransReceiveFinanceInfo();
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			info = dao.findByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1��������
	 *    QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ��
	 *    Collection ,����TransReceiveFinanceInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵����
	 *    ����Sett_TransReceiveFinanceDao.findByStatus()������
	 * @roseuid 3F73AEBB0273
	 */
	public Collection receiveFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1��������
	 *    TransReceiveFinanceInfo,�������� �տ�ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ��
	 *    Collection ,����TransReceiveFinanceInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵����
	 *    ���÷�����Sett_TransReceiveFinanceDao.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection receiveMatch(TransReceiveFinanceInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
		
		try
		{
			//ƥ��
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return coll;
	}

	/**
	 * �жϽ��׼�¼�Ƿ��޸Ĺ���˽�з�����
	 * 
	 * 1��������
	 *    lID, ����ID��
	 *    long, ԭ����TouchTime��
	 * 
	 * 2������ֵ��
	 *    boolean, ������޸ģ�����true�����򣬷���false��
	 * 
	 * 3���߼�˵����
	 *   ��1�����÷�����Sett_TransReceiveFinanceDao.findByID,�õ����µĽ��ס�
	 *   
	 * ��2���жϴ����TouchTime�Ƿ����ѯ����һ�£������һ�£�����true�����򷵻�false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean receiveCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		
		try
		{
			Sett_TransReceiveFinanceDao dao = new Sett_TransReceiveFinanceDao();
			TransReceiveFinanceInfo info = dao.findByID(lID);
			//		�ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate1 = info.getModifyDate();
			
			System.out.println("�õ�ҳ�洫�����ļ�¼�޸�ʱ��ΪtsTouchTime:"+tsTouchTime);
			System.out.println("�õ�ҳ�洫�����ļ�¼�޸�ʱ��ΪlastTouchDate1:"+lastTouchDate1);

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	///////////////////////////////////////////////////////////////////
	/**
	 * �������޻���׵��ݴ淽����
	 * 
	 * 1��������
	 *    TransReturnFinanceInfo,���� ����ʵ����
	 * 
	 * 2������ֵ��
	 *    long ,�������޻���׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����lID����-1�����÷���ReturnCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ�
	 * ��
	 *             ���÷���Sett_TransReturnFinanceDAO.update()���潻�׼�¼��Ϣ��
	 *             
	 * ���÷���Sett_TransReturnFinanceDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 *   
	 * ��2�����lID��-1�����÷���Sett_TransReturnFinanceDAO.add()���潻�׼�¼��Ϣ��
	 *            
	 * ���÷���Sett_TransReturnFinanceDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long returnTempSave(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//		�����������Ϣ
			if (info.getID() <= 0) //����
			{
				lReturn = dao.add(info);
				if (lReturn != -1)
				{
					//����״̬Ϊδ����
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
				}
			}
			else //�޸��ݴ�
				{
				//�ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYTEMPSAVE);
				//���޸Ĺ�
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
				if (flag)
				{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					Log.print("----��ʼУ��ſ�֪ͨ��״̬----");
					if (!dao.checkPayForm(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----�ſ�֪ͨ���Ѿ���ʹ��----");
						throw new IRollbackException(mySessionCtx, "�ſ�֪ͨ���Ѿ���ʹ��");
					}
					log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
					dao.updateLoanReturnFormStatus(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====�ı����====");
					lReturn = dao.update(info);

					if (lReturn != -1)
					{
						//����״̬Ϊδ����
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	
	
	//����׵ļ���
	public TransReturnFinanceInfo returnNext(TransReturnFinanceInfo info) throws IRollbackException, RemoteException
	{
		TransReturnFinanceInfo ReturnFinanceInfo = new TransReturnFinanceInfo();
		
		//���ݷ��ʶ���
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		try {
			log.debug("---------��ʼReturnNext---------------");
			ReturnFinanceInfo=dao.next(info);
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//				throw e;
//		}
//		catch (IRollbackException e)
//		{
//				throw e;
//		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			
		}
		log.debug("---------����ReturnSave---------------");
		
		return ReturnFinanceInfo;
	}
	

	/**
	 * �������޻��� ����׵ı��淽����
	 * 
	 * 1�������� TransReturnFinanceInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,�������޻���׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���TransReturnFinanceInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��TransMarginOpenInfo ��
	 * ����ǿգ�˵�����޸ı���: ���÷���this.ReturnCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * ���÷�����this.ReturnFindByID(),�õ�����ԭ���Ľ���ʵ����TransReturnFinanceInfo��
	 * 
	 * ���÷�����AccountDetail.deleteMarginDeposit()���ع�ԭ���Ĳ�����ע�������ԭ�� --kkf
	 * ��ʵ��TransReturnFinanceInfo�� ��2�����÷�����Sett_TransReturnFinanceDAO.add()
	 * ������Ϣ�� ��3�����÷�����AccountDetail.saveOpenMarginDeposit()�����в����� --kkf
	 * ��4�����÷�����Sett_TransReturnFinanceDAO.updateStatus() ���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long returnSave(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		long sessionID = -1;
		
		//���ݷ��ʶ���
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//�˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		
		log.debug("---------��ʼReturnSave---------------");
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			//��õ�ǰ״̬
			long lStatus = info.getStatusID();
			//�ݴ����ݱ���
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE)
			{
				log.debug("----------��ǰ״̬Ϊ�ݴ�-------------");
				
				/*
				//				�жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//�ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
				//���޸Ĺ�
				if(errMsg !=null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx,errMsg);
				}
				//�ж��Ƿ񱻷Ƿ��޸Ĺ�										
				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.returnCheckIsTouched(info.getID(), info.getModifyDate()))
				{
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//δ�Ƿ��޸�
					log.debug("----------��ʼ�����������޽�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));	//ֻ���ں�̨��������� 
					lReturn = dao.update(info);
					log.debug("----------���������������޽�����Ϣ-------------");
					//�����������˻����
					log.debug("----------��ʼaccountBookOperation::saveReturnFinance-------------");
					accountBookOperation.saveReturnFinance(info);
					log.debug("----------����accountBookOperation::saveReturnFinance-------------");
					//ͨ�����߲����ӿ����ȡ�½��׺�
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					log.debug("----------�½��׺���: " + transNo + " -------------");
					info.setTransNo(transNo);
					//�޸�
					lReturn = dao.update(info);
					//�޸Ľ��׵�״̬Ϊ����
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
				}
			}
			else //�����ݴ�
				{
				log.debug("----------��ǰ״̬�����ݴ�-------------");
				
				/*
				//�жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info))
				{
					log.debug("----------�浥���ظ�������ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				*/
				
				//��ȡ��ǰ���׺�
				String transNo = info.getTransNo();
				//��־λ���Ƿ����������׺�
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals(""))
				{
					log.debug("----------����������-------------");
					//δ��������������½��׺�
					bNewTransNo = true;
					//ͨ�����߲����ӿ����ȡ�½��׺�					
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------�½��׺���: " + transNo + " -------------");
					//����					
					log.debug("----------��ʼ�������ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					
					// ��3�����÷��������в�����
					log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveReturnFinance(info);
					log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					//�ݴ�ļ�¼ֻ���£� Modified by zwsun, 2007/7/24
					if(info.getID()>0){
						lReturn = dao.update(info);
					}else{
						lReturn = dao.add(info);
					}
					log.debug("----------�����������ڽ�����Ϣ-------------");
					/*
					//��д����ָ��
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
					*/
					Log.print("----�տ�淽���и��´����״̬----");
					Log.print("----��ʼУ��ſ�֪ͨ��״̬----");
					if (!dao.checkPayForm(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						Log.print("----�ſ�֪ͨ���Ѿ���ʹ��----");
						throw new IRollbackException(mySessionCtx, "�ſ�֪ͨ���Ѿ���ʹ��");
					}
					log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
					dao.updateLoanReturnFormStatus(info.getReturnFormID(), LOANConstant.LoanPayNoticeStatus.USED);
					log.debug("====�ı����====");
					//�޸Ľ��׵�״̬Ϊ���档
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);					
					
				}
				else
				{
					//��������� �������ٱ���
					//�ж�״̬�Ƿ�Ϸ�
					log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
					TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					long lNewStatusID = newInfo.getStatusID();
					//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//���޸Ĺ�
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//�ж��Ƿ񱻷Ƿ��޸Ĺ�							
					log.debug("----------�Ǳ����ٱ���-------------");				
					if (this.returnCheckIsTouched(info.getID(), info.getModifyDate()))
					{
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					else
					{
						//������ɾ�����ݿ��е����еĴ浥
						log.debug("-------ɾ���˲��оɵ���Ϣ--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						//ɾ���˲���Ϣ
						log.debug("------��ʼɾ���˲���Ϣ--------");					
						accountBookOperation.deleteReturnFinance(newInfo);
						log.debug("------����ɾ���˲���Ϣ--------");
						
						//δ�Ƿ��޸�
						log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------�������¶��ڽ�����Ϣ-------------");
						//					�����������˻����
						log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveReturnFinance(info);
						log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
						//�޸�
						lReturn = dao.update(info);
						//					�޸Ľ��׵�״̬Ϊ����
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					}
				}
				//Added by zwsun , 2007-06-20, ����������
				if(info.getInutParameterInfo()!=null)
				{
					log.debug("------�ύ����--------");
					//���÷��صĵ�ַ����(����idֻ���ڽ��ױ���֮�����,tempInfo.getUrl()�õ���urlû�о���Ľ���id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl()+lReturn);
					tempInfo.setTransID(transNo);//���ﱣ����ǽ��ױ��
					tempInfo.setDataEntity(info);
					
					//�ύ����
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					//����״̬��������
					dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------�ύ�����ɹ�--------");
				
				}					
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����ReturnSave---------------");
		return lReturn;
	}
	/**
	 * ����������������(�������޻���)
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransReturnFinanceInfo info)throws RemoteException, IRollbackException
	{
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getID();
		try
		{
			TransReturnFinanceInfo loanInfo = new TransReturnFinanceInfo();
			loanInfo=dao.findByID(info.getID());
			inutParameterInfo.setDataEntity(loanInfo);
			//�ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			//��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if(returnInfo.isLastLevel())
			{	
				dao.updateStatus(
					info.getID(),
					SETTConstant.TransactionStatus.APPROVALED);
				//������Զ�����
				if(FSWorkflowManager.isAutoCheck())
				{
					TransReturnFinanceInfo loanInfo1 = new TransReturnFinanceInfo();
					loanInfo1=dao.findByID(info.getID());
					//����check����
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("����");
					loanInfo1.setCheckUserID(returnInfo.getUserID());	//���������Ϊ������				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					returnCheck(loanInfo1);
				}	
			}
			//	��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if(returnInfo.isRefuse())
			{
				dao.updateStatus(
						info.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return loanId;
	}	
	/**
	 * ��������ȡ�������������������޻����������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷���
	 * ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransReturnFinanceInfo loanInfo)throws RemoteException, IRollbackException
	{
		long lReturn = -1;
		Sett_TransReturnFinanceDao loanDao = new Sett_TransReturnFinanceDao();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//ȡ������
				this.returnCancelCheck(loanInfo);
				//ȡ������
				lReturn = loanDao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//ȡ������
				lReturn = loanDao.updateStatus(loanInfo.getID(), SETTConstant.TransactionStatus.SAVE);
			}
			
			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}	

	/**
	 * �������޻��� ����׵�ɾ��������
	 * 
	 * 1��������
	 *    TransMarginOpenInfo ����ʵ����
	 * 
	 * 2������ֵ��
	 *    long ,��ɾ�����������޻���׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   ��1�����÷���this.opencheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 *   ��2���жϲ���TransMarginOpenInfo �еı�����ʵ�����״̬��
	 *        ������ݴ棺
	 *            
	 * ���÷�����Sett_TransReturnFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч
	 * ����
	 *        ����Ǳ��棺
	 *            
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransMarginOpenInfo
	 *            
	 * ���÷�����Sett_TransReturnFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч
	 * ����
	 * @roseuid 3F73AE9E010B
	 */
	public long returnDelete(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//����ʱʹ��		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------��ʼReturnDelete---------------");
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.DELETE);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//����Ƿ��޸Ĺ�
			boolean flag = this.returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//�ж��Ƿ��ݴ�
				if (info.getTransNo() == null || info.getTransNo().equals(""))
				{
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
				}
				else
				{
					log.debug("---------��ʼaccountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransReturnFinanceInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteReturnFinance(delInfo);
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
					log.debug("---------����accountBookOperation::deleteOpenFixedDeposit---------------");
					
					/*
					if(delInfo.getInstructionNo()!=null && delInfo.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(delInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
					*/
					
					
						if (!dao.checkPayForm(info.getReturnFormID(),LOANConstant.LoanPayNoticeStatus.USED)){
						throw new IRollbackException(mySessionCtx,"�ſ�֪ͨ���Ѿ����޸�");
						}
						dao.updateLoanReturnFormStatus(info.getReturnFormID(),  LOANConstant.LoanPayNoticeStatus.CHECK);
					
				}
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����ReturnDelete---------------");
		return lReturn;
	}

	/**
	 * �������޻�����׵ĸ��˷�����
	 * 
	 * 1��������
	 *   TransReturnFinanceInfo ����ʵ����
	 * 2������ֵ��
	 *    long ,�����˵��������޻���׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 *   ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 *   
	 * ��3�����÷�����Sett_TransReturnFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * @roseuid 3F73AEAF02F9
	 */
	public long returnCheck(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReturnFinanceInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);

			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//����Ƿ��޸Ĺ�
			boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				//����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				
				//����������ſ��˻���
				accountBookOperation.checkReturnFinance(info);
				log.debug("-------����ˣ�����״̬���Ѹ���---------");
				
			 	//���������ָ��,��Ҫ����ָ����Ϣ
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				**/
					
				lReturn = dao.update(info);
				if(lReturn!=-1)
				{				
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK);
				}
				//added by xiong fei 2010-07-25�{���{��Ӌ���ķ���
				this.ajustPlan(info.getContractID(),Env.getSystemDate(info.getOfficeID(), info.getCurrencyID()));
				
				//�Ƿ�������ӿ�
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//�Ƿ���Ҫ��������ָ��
				boolean bCreateInstruction = true;
				/*long bankID = transInfo.getReturnCorpusBankID();
				try {
						//���ô˷�����bankID��ֵ��Ϊ��������ID
					bCreateInstruction = this.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
					e1.printStackTrace();
				}
				*/
				Log.print("**************************"+ bIsValid + bCreateInstruction);
				if(bIsValid && bCreateInstruction) {//������ӿڲ�������Ҫ��������ָ��
					Log.print("*******************��ʼ�������������޻���ָ�������**************************");
					try {
						
						//�������
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(info.getReturnCorpusBankID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//��������ָ�����
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------�����������޻���ָ�����--------");
						
					} catch (Throwable e) {
						log.error("�����������޻���ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "�����������޻���ָ��ʧ�ܣ�"+e.getMessage());
					}
				}
				else 
				{
					log.debug("û�����нӿڻ���Ҫ��������ָ�");
				}
				
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------����ˣ�����---------");
		return lReturn;
	}

	/**
	 * �������޻�����׵�ȡ�����˷�����
	 * 
	 * 1��������
	 *   TransReturnFinanceInfo ����ʵ����
	 * 2������ֵ��
	 *    long ,��ȡ�����˵��������޻���׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 *   
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 *   
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ���
	 * ��
	 *   
	 * ��3�����÷�����Sett_TransReturnFinanceDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * @roseuid 3F73AEB30222
	 */
	public long returnCancelCheck(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;
		
		Sett_TransReturnFinanceDao depositDao = new Sett_TransReturnFinanceDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			//sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransReturnFinanceInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			//this.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			String errMsg =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
			//����Ƿ��޸Ĺ�
			boolean flag = returnCheckIsTouched(info.getID(), info.getModifyDate());
			if (flag) //���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			}
			else
			{
				//ȡ������
				accountBookOperation.cancelCheckReturnFinance(info);
				
				//���������ָ��,��Ҫ����ָ����Ϣ
				/*
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null,financeInfo);
					}
				*/
				lReturn = depositDao.update(info);
				if(lReturn!=-1)
				{	
					//Modified by zwsun ,8/6,ȡ�������ж��Ƿ����������
					InutParameterInfo pInfo=new InutParameterInfo();
					pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					pInfo.setOfficeID(info.getOfficeID());
					pInfo.setCurrencyID(info.getCurrencyID());
					pInfo.setTransTypeID(info.getTransactionTypeID());
					pInfo.setActionID(-1);
					if(FSWorkflowManager.isNeedApproval(pInfo)){
						lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED);					
					}else{
						lReturn = depositDao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);					
					}					
				}
				this.deletePlan(info.getContractID());
			}
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		finally
		{
			//����
			try
			{
				/*
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
				*/
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		
		return lReturn;
	}

	/**
	 * ���ݱ�ʶ��ѯ�������ڻ��� ������ϸ�ķ�����
	 * 
	 * 1��������
	 *    lID long , ����׵�ID
	 * 
	 * 2������ֵ��
	 *    TransReturnFinanceInfo,�������ڻ����ʵ����
	 * 
	 * 3���߼�˵����
	 *    ��1�����÷�����Sett_TransReturnFinanceDao.findByID() 
	 * �õ�����׵���ϸ��TransReturnFinanceInfo
	 * @roseuid 3F73AEB8007A
	 */
	public TransReturnFinanceInfo returnFindByID(long lID) throws IRollbackException,RemoteException
	{
		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			info = dao.findByID(lID);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * ���ݽ��׺Ų�ѯ�������޻������ϸ�ķ�����
	 * 
	 * 1��������
	 *    strTransNo , ����׺�
	 * 
	 * 2������ֵ��
	 *    TransMarginOpenInfo,�������� �����ʵ����
	 * 
	 * 3���߼�˵����
	 *    ��1�����÷�����Sett_TransReturnFinanceDao.findByID() 
	 * �õ�����׵���ϸ��TransReturnFinanceInfo��
	 * @roseuid 3F73AEB8007A
	 */
	public TransReturnFinanceInfo returnFindByTransNo(String strTransNo) throws IRollbackException,RemoteException
	{
		TransReturnFinanceInfo info = new TransReturnFinanceInfo();
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			info = dao.findByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1��������
	 *    QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ��
	 *    Collection ,����TransReturnFinanceInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵����
	 *    ����Sett_TransReturnFinanceDao.findByStatus()������
	 * @roseuid 3F73AEBB0273
	 */
	public Collection returnFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//
			coll = dao.findByStatus(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1��������
	 *    TransReturnFinanceInfo,�������� ����ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ��
	 *    Collection ,����TransReturnFinanceInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵����
	 *    ���÷�����Sett_TransReturnFinanceDao.match()
	 * @roseuid 3F73AEC000C1
	 */
	public Collection returnMatch(TransReturnFinanceInfo info) throws IRollbackException,RemoteException
	{
		Collection coll = null;
		Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
		
		try
		{
			//Added by zwsun , 2007/6/28, ƥ��������
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));			
			//ƥ��
			coll = dao.match(info);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		return coll;
	}

	/**
	 * �жϽ��׼�¼�Ƿ��޸Ĺ���˽�з�����
	 * 
	 * 1��������
	 *    lID, ����ID��
	 *    long, ԭ����TouchTime��
	 * 
	 * 2������ֵ��
	 *    boolean, ������޸ģ�����true�����򣬷���false��
	 * 
	 * 3���߼�˵����
	 *   ��1�����÷�����Sett_TransReturnFinanceDao.findByID,�õ����µĽ��ס�
	 *   
	 * ��2���жϴ����TouchTime�Ƿ����ѯ����һ�£������һ�£�����true�����򷵻�false?
	 * @roseuid 3F73AEC40379
	 */
	private boolean returnCheckIsTouched(long lID, Timestamp tsTouchTime) throws IRollbackException,RemoteException
	{
		
		try
		{
			Sett_TransReturnFinanceDao dao = new Sett_TransReturnFinanceDao();
			TransReturnFinanceInfo info = dao.findByID(lID);
			//		�ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null || lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		//modified by mzh_fu 2007/05/011
//		catch (RemoteException e)
//		{
//			throw e;
//		}
//		catch (IRollbackException e)
//		{
//			throw e;
//		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
		
	}
	
	/**
	 * ��������----�������ݿ������Ϣ��¼
	 * @author afzhu
	 * @param trfi 
	 * @param balanceType ���㷽ʽ
	 * @throws Exception 
	 */
	public int quantityRepaymentBalance_createRecord(TransReturnFinanceNewInfo trfi,String balanceType) throws IRollbackException, RemoteException
	{
		System.out.println("========================�����������---�����뽻����ص���Ϣ��¼==========================");
		int isFail = 0;//ʧ�ܱ�ʶ; 0Ϊ�ɹ�,-1Ϊʧ��
		String transNo="";//���׺�
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		/*���ɽ��׺�*/
		String deleteParam = "";
		try{
			deleteParam = trfi.getAccountOperation().replaceAll(";","&");
			System.out.println("========================�����������---�����뽻����ص���Ϣ��¼JSONObject==========================begin��"+deleteParam);
			JSONObject jo=new JSONObject(deleteParam);
			System.out.println("========================�����������---�����뽻����ص���Ϣ��¼JSONObject==========================end");
			String detailOp[] = jo.getString("detail").split("&");
			double marginMoney = 0;//��֤���˻��ۿ��ܶ�
			double currentMoney = 0;//�����˻��ۿ��ܶ�
			/*if(detailOp.length>1)
			{
				for(int i=1;i<detailOp.length;i++)
				{
					marginMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
			}*/
			for(int i=0;i<detailOp.length;i++)
			{
				if(detailOp[i].split("-")[2].equals("2"))//Ϊ2�Ǳ�֤���˻��ۿ�,1�ǻ����˻��ۿ�
				{
					marginMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
				if(detailOp[i].split("-")[2].equals("1"))//Ϊ1�ǻ����˻��ۿ�
				{
					currentMoney+=Double.parseDouble(detailOp[i].split("-")[1]);
				}
			}
			transNo =utilOperation.getNewTransactionNo(
					trfi.getNofficeid(),
					trfi.getNcurrencyid(),
					SETTConstant.TransactionType.RETURNFINANCE);
			trfi.setTransno(transNo);//���ý��׺�
			trfi.setTransactionTypeID(SETTConstant.TransactionType.RETURNFINANCE);//���ý�������
			trfi.setMarginMoney(marginMoney);//��֤���˻��ۿ��ܶ�
			trfi.setCurrentMoney(currentMoney);//�����˻��ۿ��ܶ�
			/***************************************************/
			System.out.println("========================�����������---��ʼ�����˻���ϸ==========================");
			createAccountDetailInfo(trfi,balanceType);//�����˻���ϸ
			System.out.println("========================�����������---���������˻���ϸ==========================");
			System.out.println("========================�����������---��ʼ���ɻ�Ʒ�¼==========================");
			createGenerateGLEntryParam(trfi,balanceType);//���ɻ�Ʒ�¼
			System.out.println("========================�����������---�������ɻ�Ʒ�¼==========================");
			System.out.println("========================�����������---��ʼ���˻���Ǯ==========================");
			accountDeduckMoney(trfi);//�˻����� 
			System.out.println("========================�����������---�������˻���Ǯ==========================");
			System.out.println("========================�����������---��ʼ���ɽ����¼==========================");
			update_TransReturnFinance(trfi);//���ɽ����¼
			System.out.println("========================�����������---�������ɽ����¼==========================");
			System.out.println("========================�����������---��ʼ�������Ӳ�ѯ��¼==========================");
			createHrefFind(trfi);//�������Ӳ�ѯ��¼
			System.out.println("========================�����������---�����������Ӳ�ѯ��¼==========================");
			/***************************************************/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("========================�����������---�����뽻����ص���Ϣ��¼�����쳣==========================");
			isFail = -1;
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return isFail;
	}
	
	/**
	 *  ��������----�����˻���ϸ
	 * @author afzhu
	 * @param trfn
	 * @param balanceType
	 */
	public void createAccountDetailInfo(TransReturnFinanceNewInfo trfn,String balanceType) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		TransAccountDetailInfo debit_tadi = null;//�跽�˻���ϸʵ��
		List resultList = new ArrayList();
		String deleteParam = "";
		try
		{
		deleteParam = trfn.getAccountOperation().replaceAll(";","&");
		JSONObject jo=new JSONObject(deleteParam);
		String detailOp[] = jo.getString("detail").split("&");
		for(int i=0;i<jo.getInt("count");i++)
		{
			debit_tadi = new TransAccountDetailInfo();
			debit_tadi.setOfficeID(trfn.getNofficeid());//���´�ID
			debit_tadi.setCurrencyID(trfn.getNcurrencyid());//����
			debit_tadi.setTransactionTypeID(trfn.getTransactionTypeID());//��������
			debit_tadi.setDtExecute(DataFormat.getDateTime(trfn.getSysDateS()));//ִ����--������
			debit_tadi.setTransNo(trfn.getTransno());//���׺�
			debit_tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);//״̬
			if(LOANConstant.BalanceType.CURRENTACCOUNT == Integer.parseInt(balanceType))//������㷽ʽ�ǻ����˻�
			{
			debit_tadi.setTransAccountID(trfn.getCurrentAccountID());//�����˻�ID,�跽��ֵΪ�����˻�ID
			debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//�������˻�ID,�跽��ֵΪ�������˻�ID
			debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//���׷�����,Ҳ���Ǳ��ڻ�����
			}
			else if(LOANConstant.BalanceType.FIRST_CURRENTACCOUNT_LATE_RECOGNIZANCEACCOUNT == Integer.parseInt(balanceType))//������㷽ʽ���Ȼ��ں�֤��
			{
				//���i=0�Ļ�,֤�����ѭ���ǵ�һ�ο�Ǯ���˻�,Ҳ���ǻ���,�ڶ����Ժ���Ǳ�֤��,��Ϊֻ�б�֤���˻��ſ��Կ۶��
				if(i==0)
				{
					debit_tadi.setTransAccountID(trfn.getCurrentAccountID());//�����˻�ID,�跽��ֵΪ�����˻�ID
					debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//�������˻�ID,�跽��ֵΪ�������˻�ID
					debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//���׷�����,Ҳ���Ǳ��ڻ�����
				}
				else
				{
					debit_tadi.setTransAccountID(trfn.getMarginAccountID());//�����˻�ID,�跽��ֵΪ��֤���˻�ID
					debit_tadi.setTransSubAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//�������˻�ID,�跽��ֵΪ�������˻�ID
					debit_tadi.setAmount(Double.parseDouble(detailOp[i].split("-")[1]));//���׷�����,Ҳ���Ǳ��ڻ�����
				}
			}
			debit_tadi.setTransDirection(SETTConstant.ControlDirection.DEBIT);//���׷���(��)
			//debit_tadi.setOppAccountID(trfn.getOppAccountID());//�Է��˻�ID
			//debit_tadi.setOppAccountNo(trfn.getOppAccountNo());//�Է��˻���
			//debit_tadi.setOppSubAccountID(trfn.getOppSubAccountID());//�Է����˻�ID
			//debit_tadi.setOppAccountName(trfn.getOppClientName());//�Է��˻�����(Ҳ���ǿͻ�����)
			//System.out.println("===========��ʼ�����˻���ϸ===========");
			dao.saveAccountDetailInfo(debit_tadi);//����
			//System.out.println("===========���������˻���ϸ===========");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
	}
	/**
	 * ��������----���ɻ�Ʒ�¼
	 * ��Ʒ�¼Ҳ�����˻��������������,���ֻ�л����˻�����,�Ǿ���������,Ҫ�����л������б�֤��,�Ǿ���������,�ֱ��Ӧ���
	 * @author afzhu
	 * @param trfn
	 * @param balanceType
	 */
	public void createGenerateGLEntryParam(TransReturnFinanceNewInfo trfn,String balanceType) throws Exception
	{
		GenerateGLEntryParam param = null;//�����˻���Ʒ�¼
		GenerateGLEntryParam param1 = null;//��֤���˻���Ʒ�¼
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
			int type =-1;//0����ֻ�л��ڿۿ�,1����ֻ�б�֤��ۿ�,2������ںͱ�֤��ȫ�ۿ�
			String detailOp[] = jo.getString("detail").split("&");
			if(detailOp.length==1)
			{
				if(detailOp[0].split("-")[2].equals("2"))
				{
					type = 1;//ֻ�б�֤��ۿ�
				}
				if(detailOp[0].split("-")[2].equals("1"))
				{
					type = 0;//ֻ�л��ڿۿ�
				}
			}
			else
			{
				type = 1;//Ĭ��Ϊֻ�б�֤��ۿ�
				if(detailOp[0].split("-")[2].equals("2")){
					type = 1;//ֻ�б�֤��ۿ�
				}else{
					//for(int i=0;i<detailOp.length;i++)
					//{
					//	if(detailOp[i].split("-")[2].equals("1"))
					//	{
							type = 2;//���ںͱ�֤��ȫ�ۿ�
					//	}
					//}
				}
			}
			if(type == 2)//�����˻��ͱ�֤���˻��������ۿ�����
			{	
//				for(int i=0;i<detailOp.length;i++)
//				{
//					param = new GenerateGLEntryParam();//��Ʒ�¼
//					param.setOfficeID(trfn.getNofficeid());//���´�ID
//					param.setCurrencyID(trfn.getNcurrencyid());//����
//					param.setTransactionTypeID(trfn.getTransactionTypeID());//��������
//					param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//ִ����
//					param.setTransNo(trfn.getTransno());//���׺�
//					param.setInputUserID(trfn.getUserID());//¼����ID
//					param.setCheckUserID(trfn.getUserID());//������
//					param.setPrincipalType(SETTConstant.CapitalType.IRRESPECTIVE);// //�������򣬿ɿ�1�ڲ�ת��2����
//					//param.setInterestType(1);//��Ϣ���򣬿ɿ�1�ڲ�ת��2����
//					param.setEntryType(SETTConstant.EntryType.DIVIDE);//��¼����,�ɿ�0�޹�1�ϲ�2�ֲ�3����
//					//param.setReceiveAccountID(trfn.getOppAccountID());//�տ�˻�ID 
//					if(i==0)
//					{
//						param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//����˻�ID
//						param.setPrincipalOrTransAmount(Double.parseDouble(detailOp[0].split("-")[1]));//���׷�����(����)
//						param.setTotalPrincipalAndInterest(Double.parseDouble(detailOp[0].split("-")[1]));//��Ϣ�ϼ�
//					}
//					else
//					{
//						param.setPayFinanceAccountID(Integer.parseInt(detailOp[i].split("-")[0]));//��֤�𸶿��˻�
//						param.setPrincipalOrTransAmount(Double.parseDouble(detailOp[i].split("-")[1]));//���׷�����(����)
//						param.setFinanceAllAmount(Double.parseDouble(detailOp[i].split("-")[1]));//������Ϣ�ϼ�
//					}
//					boolean res = glopOperation.generateGLEntry(param);//���ɻ�Ʒ�¼
//					if (!res)
//					{
//						throw new IRollbackException(mySessionCtx, "������Ʒ�¼����2");
//					}
//				}
				param = new GenerateGLEntryParam();//��Ʒ�¼
				param.setOfficeID(trfn.getNofficeid());//���´�ID
				param.setCurrencyID(trfn.getNcurrencyid());//����
				param.setTransactionTypeID(trfn.getTransactionTypeID());//��������
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//ִ����
				param.setTransNo(trfn.getTransno());//���׺�
				param.setInputUserID(trfn.getUserID());//¼����ID
				param.setCheckUserID(trfn.getUserID());//������
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //�������򣬿ɿ�1�ڲ�ת��2����
				//param.setInterestType(1);//��Ϣ���򣬿ɿ�1�ڲ�ת��2����
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//��¼����,�ɿ�0�޹�1�ϲ�2�ֲ�3����
				//param.setReceiveAccountID(trfn.getOppAccountID());//�տ�˻�ID 
				param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//����˻�ID
				param.setTotalPrincipalAndInterest(trfn.getCurrentPaymentAmount());//��Ϣ�ϼ�
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//���ڱ�����Ϣ�ϼ�
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//���׷�����(����)
				param.setTotalInterest(trfn.getInterest());//��Ϣ�ϼ�
				param.setPayFinanceAccountID(Integer.parseInt(detailOp[1].split("-")[0]));//��֤�𸶿��˻�
				param.setFinanceAllAmount(trfn.getMarginMoney());//������Ϣ�ϼ�
				boolean res = glopOperation.generateGLEntry(param);//���ɻ�Ʒ�¼
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "������Ʒ�¼����2");
				}
			}
			else if(type == 0)//ֻ�л��ڿۿ�
			{
				param = new GenerateGLEntryParam();//��Ʒ�¼
				param.setOfficeID(trfn.getNofficeid());//���´�ID
				param.setCurrencyID(trfn.getNcurrencyid());//����
				param.setTransactionTypeID(trfn.getTransactionTypeID());//��������
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//ִ����
				param.setTransNo(trfn.getTransno());//���׺�
				param.setInputUserID(trfn.getUserID());//¼����ID
				param.setCheckUserID(trfn.getUserID());//������
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //�������򣬿ɿ�1�ڲ�ת��2����
				//param.setInterestType(1);//��Ϣ���򣬿ɿ�1�ڲ�ת��2����
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//��¼����,�ɿ�0�޹�1�ϲ�2�ֲ�3����
				//param.setReceiveAccountID(trfn.getOppAccountID());//�տ�˻�ID 
				param.setPayAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//����˻�ID
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//���׷�����(����)
				param.setTotalPrincipalAndInterest(trfn.getCurrentMoney());//��Ϣ�ϼ�
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//���ڱ�����Ϣ�ϼ�
				param.setTotalInterest(trfn.getInterest());//��Ϣ�ϼ�
				//param.setFinanceAllAmount(trfn.getMarginMoney());//������Ϣ�ϼ�
				boolean res = glopOperation.generateGLEntry(param);//���ɻ�Ʒ�¼
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "������Ʒ�¼����2");
				}
			}
			else//ֻ�б�֤���˻��ۿ�
			{
				param = new GenerateGLEntryParam();//��Ʒ�¼
				param.setOfficeID(trfn.getNofficeid());//���´�ID
				param.setCurrencyID(trfn.getNcurrencyid());//����
				param.setTransactionTypeID(trfn.getTransactionTypeID());//��������
				param.setExecuteDate(DataFormat.getDateTime(trfn.getSysDateS()));//ִ����
				param.setTransNo(trfn.getTransno());//���׺�
				param.setInputUserID(trfn.getUserID());//¼����ID
				param.setCheckUserID(trfn.getUserID());//������
				param.setPrincipalType(SETTConstant.CapitalType.INTERNAL);// //�������򣬿ɿ�1�ڲ�ת��2����
				//param.setInterestType(1);//��Ϣ���򣬿ɿ�1�ڲ�ת��2����
				param.setEntryType(SETTConstant.EntryType.DIVIDE);//��¼����,�ɿ�0�޹�1�ϲ�2�ֲ�3����
				//param.setReceiveAccountID(trfn.getOppAccountID());//�տ�˻�ID 
				//param.setPrincipalOrTransAmount(trfn.getPrincipal());//���׷�����(����)
				param.setCurrentPrincipalAndInterest(trfn.getCurrentMoney());//��Ϣ�ϼ�
				param.setTotalPrincipalAndInterest(trfn.getCurrentPaymentAmount());//��Ϣ�ϼ�
				param.setPayFinanceAccountID(Integer.parseInt(detailOp[0].split("-")[0]));//��֤�𸶿��˻�
				param.setTotalInterest(trfn.getInterest());//��Ϣ�ϼ�
				param.setFinanceAllAmount(trfn.getMarginMoney());//������Ϣ�ϼ�
				boolean res = glopOperation.generateGLEntry(param);//���ɻ�Ʒ�¼
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "������Ʒ�¼����2");
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		
	}
	
	/**
	 * ��������----�˻�����
	 * @author afzhu
	 * @param trfn
	 */
	public void accountDeduckMoney(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			String detailOp[] = jo.getString("detail").split("&");
			//String sk = trfn.getOppSubAccountID() + "-" + trfn.getCurrentPaymentAmount();//�տ�
			//dao.accountDeduckMoney(sk,1);//�տ����
			for(int i=0;i<detailOp.length;i++)
			{
				dao.accountDeduckMoney(detailOp[i],0);//�������
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * ��������---���ɽ����¼
	 * @author afzhu
	 * @param trfn
	 * @throws Exception
	 */
	public void update_TransReturnFinance(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try{
			dao.saveTransReturnFinance(trfn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * ��������---�������Ӳ�ѯ��¼
	 * @author afzhu
	 * @param trfn
	 * @throws Exception
	 */
	public void createHrefFind(TransReturnFinanceNewInfo trfn) throws Exception
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		String deleteParam="";
		try{
			deleteParam = trfn.getAccountOperation().replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			//String sk = trfn.getOppSubAccountID() + "-" + trfn.getCurrentPaymentAmount();//�տ�
			String detailOp =jo.getString("detail");
			dao.createHrefFind(trfn, detailOp);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	/**
	 * ��������---���Ӳ���---ɾ������
	 * @author afzhu
	 * @param deleteParam
	 * @throws Exception
	 */
	public void hrefFindDelete(String deleteParam) throws IRollbackException, RemoteException
	{
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try{
			deleteParam = deleteParam.replaceAll(";","&");
			JSONObject jo=new JSONObject(deleteParam);
			String detailOpp[] = jo.getString("detail").split("&");//������ϸ
			String transNo[] = jo.getString("transNo").split("&");//���׺�
			//�������˻��еĽ��
			for(int i=0;i<detailOpp.length;i++) 
			{
				dao.hrefFindDelete(detailOpp[i],0);
			}
			//���»�Ʒ�¼,�˻���ϸ,������еļ�¼
			for(int j=0;j<transNo.length;j++)
			{
				dao.hrefFindDelete(transNo[j],1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}
	
	/**
	 * @author yunchang
	 * @date 2010-07-02
	 * @function ��������-�տ�-���ձ�֤����
	 * @param long 
	 * @return double
	 * @throws IRollbackException RemoteException
	 */
	public double getMbailamount(long constractID) throws IRollbackException, RemoteException
	{
		double tempMbailamount = 0.0;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			tempMbailamount = dao.getMbailamount(constractID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return tempMbailamount;
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * ��ǰ����˳ɹ������°��Ż���ƻ�
	 * @param constractID
	 * @return long
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long ajustPlan(long contractID,Timestamp ts) throws IRollbackException, RemoteException
	{
		long returnRes = -1;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			returnRes = dao.ajustPlan(contractID,ts);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return returnRes;
	}
	
	/**
	 * added by xiong fei 2010-07-16
	 * ��ǰ����˳ɹ������°��Ż���ƻ�
	 * @param constractID
	 * @return long
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long deletePlan(long contractID) throws IRollbackException, RemoteException
	{
		long returnRes = -1;
		Sett_TransReturnFinanceDao dao=new Sett_TransReturnFinanceDao();
		try
		{
			returnRes = dao.deletePlan(contractID);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return returnRes;
	}
}