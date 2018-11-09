/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.settlement.account.bizlogic.Account;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountEJB;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransChangeFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedChangeInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedContinueInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFixedDepositEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;

	final static long serialVersionUID = 3206093459760846163L;

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	private final static  Object lockObj = new Object();  //��̬

	/**
	 * ejbActivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws RemoteException {
	}

	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	// ******�û��Լ������*******//
	/**
	 * ���ڣ�֪ͨ���������׵��ݴ淽����
	 * 
	 * 1����* 3���߼�˵����
	 * 
	 * ��1�����lID����-1�����÷���this.openCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ� ��
	 * ���÷���Sett_TransOpenFixedDepositDAO.update()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransOpenFixedDepositDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * 
	 * ��2�����lID��-1�����÷���Sett_TransOpenFixedDepositDAO.add()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransOpenFixedDepositDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * 
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long openTempSave(TransFixedOpenInfo info) throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
		
		long lReturn = -1;
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();

		try {

			// �жϴ浥���Ƿ��ظ�
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// �����������Ϣ
			if (info.getID() <= 0) // ����
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// ����״̬Ϊδ����
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // �޸��ݴ�
			{
				// �ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransFixedOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYTEMPSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYTEMPSAVE);
				// ���޸Ĺ�
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				boolean flag = this.openCheckIsTouched(info.getID(), info
						.getModifyDate());
				if (flag) {
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					lReturn = dao.update(info);

					if (lReturn != -1) {
						// ����״̬Ϊδ����
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		// catch (RemoteException e)
		// {
		// throw e;
		// }
		// catch (IRollbackException e)
		// {
		// throw e;
		// }
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ���������ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1�������� FixdOpenInfo ����ʵ����
	 * 
	 * 2������ֵ�� String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixdOpenInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * �÷�����Sett_TransOpenFixedDeposit.checkIsDuplicate()�ж��Ƿ��ظ���
	 * 
	 * @roseuid 3F73AE9300E8
	 */

	/**
	 * ���ڣ�֪ͨ���������׵ı��淽����
	 * 
	 * 1�������� FixdOpenInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixdOpenInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��FixdOpenInfo �� ����ǿգ�˵�����޸ı���:
	 * ���÷���this.openCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * ���÷�����this.openFindByID(),�õ�����ԭ���Ľ���ʵ����FixdOpenInfo��
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo�� ��2�����÷�����Sett_TransOpenFixedDepositDAO.add() ������Ϣ��
	 * ��3�����÷�����AccountDetail.saveOpenFixedDeposit()�����в�����
	 * ��4�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus() ���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long openSave(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		long lReturn = -1;
		long sessionID = -1;
		// ���ݷ��ʶ���
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// �˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		String transNo = null;
		log.debug("---------��ʼopenSave---------------");
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			// ��õ�ǰ״̬
			long lStatus = info.getStatusID();
			// �ݴ����ݱ���
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) {
				log.debug("----------��ǰ״̬Ϊ�ݴ�-------------");
				// modify by xwhe date:2007-08-03
				String DepositNo = utilOperation
						.getOpenDepositNoBackGround(info.getAccountID());
				info.setDepositNo(DepositNo);
				// �жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info)) {
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				// �ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransFixedOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// ���޸Ĺ�
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// �ж��Ƿ񱻷Ƿ��޸Ĺ�
				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// δ�Ƿ��޸�
					log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------�������¶��ڽ�����Ϣ-------------");
					// �����������˻����
					log
							.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveOpenFixedDeposit(info);
					log
							.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					// ͨ�����߲����ӿ����ȡ�½��׺�
					// String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					log.debug("----------�½��׺���: " + transNo + " -------------");
					info.setTransNo(transNo);
					// �޸�
					lReturn = dao.update(info);
					// �޸Ľ��׵�״̬Ϊ����
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);
				}
			} else // �����ݴ�
			{
				log.debug("----------��ǰ״̬�����ݴ�-------------");
				// modify by xwhe date:2007-08-03
				String DepositNo = info.getDepositNo();
				if (DepositNo == null || DepositNo.equals("")) {
					DepositNo = utilOperation.getOpenDepositNoBackGround(info
							.getAccountID());
					info.setDepositNo(DepositNo);
				}
				// �жϴ浥���Ƿ��ظ�
				if (!dao.checkDepositNo(info)) {
					log.debug("----------�浥���ظ�������ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				// ��ȡ��ǰ���׺�
				transNo = info.getTransNo();
				// ��־λ���Ƿ����������׺�
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals("")) {
					log.debug("----------����������-------------");
					// δ��������������½��׺�
					bNewTransNo = true;
					// ͨ�����߲����ӿ����ȡ�½��׺�
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------�½��׺���: " + transNo + " -------------");
					// ����
					log.debug("----------��ʼ�������ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------�����������ڽ�����Ϣ-------------");
					// ��3�����÷��������в�����
					log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveOpenFixedDeposit(info);
					log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					
					//��д����ָ��
					if (info.getInstructionNo() != null && info.getInstructionNo().length() > 0) {
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						
						//��д���ڿ����������� Boxu Add 2008��5��7��
						financeInfo.setDepositRate(info.getRate());
						
						financeDao.updateStatusAndTransNo(null, financeInfo);
					}
					
					// Modifyed by qhzhou 2007.6.21
					if (info.getStatusID() != SETTConstant.TransactionStatus.WAITAPPROVAL) {
						// �޸Ľ��׵�״̬Ϊ���档
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					}
				} else {
					// ��������� �������ٱ���
					// �ж�״̬�Ƿ�Ϸ�
					log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
					TransFixedOpenInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx,
								"�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info
							.getStatusID(), lNewStatusID,
							SETTConstant.Actions.MODIFYSAVE);
					// ���޸Ĺ�
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// �ж��Ƿ񱻷Ƿ��޸Ĺ�
					log.debug("----------�Ǳ����ٱ���-------------");
					if (this.openCheckIsTouched(info.getID(), info
							.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {
						// ������ɾ�����ݿ��е����еĴ浥
						log.debug("-------ɾ���˲��оɵ���Ϣ--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						// ɾ���˲���Ϣ
						log.debug("------��ʼɾ���˲���Ϣ--------");
						accountBookOperation.deleteOpenFixedDeposit(newInfo);
						log.debug("------����ɾ���˲���Ϣ--------");

						// δ�Ƿ��޸�
						log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------�������¶��ڽ�����Ϣ-------------");
						// �����������˻����
						log
								.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveOpenFixedDeposit(info);
						log
								.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
						// �޸�
						lReturn = dao.update(info);
						// �޸Ľ��׵�״̬Ϊ����
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.SAVE);
						
						//��д����ָ��
						if (info.getInstructionNo() != null && info.getInstructionNo().length() > 0) {
							log.info("---------������ָ��----------");
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

							financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
							financeInfo.setDealUserID(info.getInputUserID());
							financeInfo.setConfirmDate(info.getExecuteDate());
							financeInfo.setFinishDate(info.getExecuteDate());
							financeInfo.setTransNo(info.getTransNo());
							financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
							
							//��д���ڿ����������� Boxu Add 2008��5��7��
							financeInfo.setDepositRate(info.getRate());
							
							financeDao.updateStatusAndTransNo(null, financeInfo);
						}						

					}
				}
			}
			/**
			 * ���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by ���� 2007-04-17
			 */
			if (info.getInutParameterInfo() != null) {
				log.debug("------�ύ����--------");
				// ���÷��صĵ�ַ����(����idֻ���ڽ��ױ���֮�����,tempInfo.getUrl()�õ���urlû�о���Ľ���id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(transNo);// ���ﱣ����ǽ��ױ��
				tempInfo.setDataEntity(info);

				// �ύ����
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				// ����״̬��������
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALING);
				log.debug("------�ύ�����ɹ�--------");

			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����openSave---------------");
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ���������׵�ɾ��������
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ����
	 * 
	 * 2������ֵ�� long ,��ɾ���Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1�����÷���this.opencheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 * ��2���жϲ���TransFixedOpenInfo �еı�����ʵ�����״̬�� ������ݴ棺
	 * 
	 * ���÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч ���� ����Ǳ��棺
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo
	 * 
	 * ���÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч ����
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long openDelete(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;

		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------��ʼopenDelete---------------");
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.DELETE);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// �ж��Ƿ��ݴ�
				if (info.getTransNo() == null || info.getTransNo().equals("")) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
				} else {
					log
							.debug("---------��ʼaccountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransFixedOpenInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteOpenFixedDeposit(delInfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
					log
							.debug("---------����accountBookOperation::deleteOpenFixedDeposit---------------");

					if (delInfo.getInstructionNo() != null
							&& delInfo.getInstructionNo().length() > 0) {
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(
								delInfo.getInstructionNo()).longValue(),
								OBConstant.SettInstrStatus.REFUSE);
					}
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------����openDelete---------------");
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ�����׵ĸ��˷�����
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ���� 2������ֵ�� long ,�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣? ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 * ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 * 
	 * ��3�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long openCheck(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();

		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// ����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�

				// ����������ſ��˻���
				accountBookOperation.checkOpenFixedDeposit(info);
				log.debug("-------�������ˣ�����״̬���Ѹ���---------");
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				lReturn = dao.update(info);
				if (lReturn != -1) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.CHECK);
				}
				/***********�������и���ָ��**********/
				//�Ƿ�������ӿ�
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//�Ƿ���Ҫ��������ָ��
				boolean bCreateInstruction = false;
				long bankID = info.getBankID();
				try {
					//���ô˷�����bankID��ֵ��Ϊ��������ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
					e1.printStackTrace();
				}
				
				if(bIsValid && bCreateInstruction) {//������ӿڲ�������Ҫ��������ָ��
					Log.print("*******************��ʼ�������ڿ�����֪ͨ����ָ�������**************************");
					try {
						log.debug("------��ʼ���ж��ڿ�����֪ͨ��ָ������--------");
						//�������
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getInputUserID());
						
						//��������ָ�����
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------�������ж��ڿ�����֪ͨ����ָ�����--------");
						
					} catch (Throwable e) {
						log.error("�������ж��ڿ�����֪ͨ��ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "�������ж��ڿ�����֪ͨ����ָ��ʧ�ܣ�"+e.getMessage());
					}
				}
				else {
					Log.print("û�����нӿڻ���Ҫ��������ָ�");
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------�������ˣ�����---------");
		return lReturn;
		}
	}
	
	/**
	 * ���ڣ�֪ͨ�����׵ĸ��˷���(��������ָ����ڲ���֧ȡר�ã��ڶ��ڲ���֧ȡ��ͳһ��ָ��)��
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ���� 2������ֵ�� long ,�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣? ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 * ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 * 
	 * ��3�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long openCheckForPartDraw(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();

		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// ����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�

				// ����������ſ��˻���
				accountBookOperation.checkOpenFixedDeposit(info);
				log.debug("-------�������ˣ�����״̬���Ѹ���---------");
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				lReturn = dao.update(info);
				if (lReturn != -1) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.CHECK);
				}
				
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------�������ˣ�����---------");
		return lReturn;
		}
	}


	/**
	 * ���ڣ�֪ͨ�����׵�ȡ�����˷�����
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ���� 2������ֵ�� long ,��ȡ�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 * 
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ��� ��
	 * 
	 * ��3�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * 
	 * @roseuid 3F73AEB30222
	 */
	public long openCancelCheck(TransFixedOpenInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO depositDao = new Sett_TransOpenFixedDepositDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedOpenInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				// ȡ������
				accountBookOperation.cancelCheckOpenFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				// ����״̬����δ���˻�������(���ݸ�ҵ���Ƿ����������ж�)
				long lCancelCheckStatus = FSWorkflowManager
						.getSettCheckStatus(new InutParameterInfo(info
								.getOfficeID(), info.getCurrencyID(),
								Constant.ModuleType.SETTLEMENT, info
										.getTransactionTypeID(), -1));
				info.setStatusID(lCancelCheckStatus);
				// //added by qhzhou 2007-07-25 ȡ������ʱӦ��ո��˱�ע��������
				//info.setCheckAbstract("");Modified by ylguo(��Ӣ��)at 2009-02-16ȡ�����˵�ʱ�򣬱�ע������գ���Ϊ�ڲ�ѯ��ʱ��Ҫ���ĵ����ڸ��˻�ȡ�����˵�ʱ��
				//��ע������Ӧ����ҳ�������ƣ�����˵��ѯ��ʱ��ҳ�����ܿ��������˺�ȡ�����˵���ȴ���ܿ�������
				//info.setCheckUserID(-1);
				//info.setCheckUserName("");
				//Modifying ended at 2009-02-16
				lReturn = depositDao.update(info);
				// if(lReturn!=-1)
				// {
				// lReturn = depositDao.updateStatus(info.getID(),
				// SETTConstant.TransactionStatus.SAVE);
				// }
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� lID long , �������׵�ID
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByID()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedOpenInfo openFindByID(long lID) throws IRollbackException,RemoteException 
	{
		TransFixedOpenInfo info = new TransFixedOpenInfo();

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			info = dao.findByID(lID);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ���ݽ��׺Ų�ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� strTransNo , �������׺�
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByID()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedOpenInfo openFindByTransNo(String strTransNo)throws IRollbackException, RemoteException 
	{
		TransFixedOpenInfo info = new TransFixedOpenInfo();

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			info = dao.findByTransNo(strTransNo);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	// added by qhzhou 2007.6.20
	/**
	 * ���ݴ浥��Ų�ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� DepositNo , �浥���
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByDepositNo()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedOpenInfo openFindByDepositNo(String DepositNo)
			throws IRollbackException, RemoteException {
		TransFixedOpenInfo info = new TransFixedOpenInfo();

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			info = dao.findByDepositNo(DepositNo);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	// added by qhzhou 2007.6.20
	/**
	 * ���ݴ浥��Ų�ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� DepositNo , �浥���
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByDepositNo()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedOpenInfo openFindByOldDepositNo(String oldDepositNo)
			throws IRollbackException, RemoteException {
		TransFixedOpenInfo info = new TransFixedOpenInfo();

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			info = dao.findByOldDepositNo(oldDepositNo);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	// added by qhzhou 2007.6.26
	// �������׵����Ӳ���,�������ڶ���֧ȡ���ɵĿ����浥
	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1�������� QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ�� Collection ,����TransFixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ����Sett_TransOpenFixedDepositDAO.findByStatus()������
	 * 
	 * @roseuid 3F73AEBB0273
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info,
			boolean isFilt) throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			coll = dao.findByStatus(info, isFilt);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1�������� QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ�� Collection ,����TransFixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ����Sett_TransOpenFixedDepositDAO.findByStatus()������
	 * 
	 * @roseuid 3F73AEBB0273
	 */
	public Collection openFindByStatus(QueryByStatusConditionInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			coll = dao.findByStatus(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1�������� TransFixedOpenInfo,���ڣ�֪ͨ���������ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ�� Collection ,����TransFixedOpenInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ���÷�����Sett_TransOpenFixedDepositDAO.match()
	 * 
	 * @roseuid 3F73AEC000C1
	 */
	public Collection openMatch(TransFixedOpenInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			// ����ƥ�临��״̬
			info.setStatusID(FSWorkflowManager
					.getSettCheckStatus(new InutParameterInfo(info
							.getOfficeID(), info.getCurrencyID(),
							Constant.ModuleType.SETTLEMENT, info
									.getTransactionTypeID(), -1)));
			coll = dao.match(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * �жϽ��׼�¼�Ƿ��޸Ĺ����ݴ淽����
	 * 
	 * 1�������� lID, ����ID�� long, ԭ����TouchTime��
	 * 
	 * 2������ֵ�� boolean, ������޸ģ�����true�����򣬷���false��
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByID,�õ����µĽ��ס�
	 * 
	 * ��2���жϴ����TouchTime�Ƿ����ѯ����һ�£������һ�£�����true�����򷵻�false?
	 * 
	 * @roseuid 3F73AEC40379
	 */
	private boolean openCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
			TransFixedOpenInfo info = dao.findByID(lID);
			// �ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null
					|| lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * �ж�״̬�Ƿ���ȷ
	 * 
	 * @param lStatusID
	 *            ҳ�洫������״̬
	 * @param lNewStatusID
	 *            ��̨ȡ����״̬
	 * @param lActionID
	 *            ҳ���������
	 * @return void ״̬��ƥ��
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	/*
	 * private void checkStatus(long lStatusID, long lNewStatusID, long
	 * lActionID) throws IRollbackException,RemoteException { try { //�޸ı��� if
	 * (lActionID == SETTConstant.Actions.MODIFYSAVE) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.SAVE && lStatusID !=
	 * SETTConstant.TransactionStatus.TEMPSAVE) { //�ѱ����� if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //�ѱ�ɾ�� if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } } else { //�ѱ����� if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //�ѱ�ɾ�� if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } } } //�޸��ݴ� if (lActionID ==
	 * SETTConstant.Actions.MODIFYTEMPSAVE) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.TEMPSAVE) { //�ѱ����� if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //�ѱ�ɾ�� if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } //�ѱ����� if (lStatusID ==
	 * SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E130"); } } else { if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E130"); } } } //ɾ�� if (lActionID ==
	 * SETTConstant.Actions.DELETE) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.SAVE && lStatusID !=
	 * SETTConstant.TransactionStatus.TEMPSAVE) { if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E018"); } if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E019"); } } else { if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E018"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E019"); } } } //���� if (lActionID ==
	 * SETTConstant.Actions.CHECK) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.SAVE) { if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E022"); } if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E021"); }
	 *  } else { if (lNewStatusID == SETTConstant.TransactionStatus.DELETED) {
	 * throw new IRollbackException(mySessionCtx, "Sett_E022"); } if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E021"); } } } //ȡ������ if (lActionID ==
	 * SETTConstant.Actions.CANCELCHECK) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.CHECK) { if (lStatusID ==
	 * SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E023"); } if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E025"); } } else { if
	 * (lNewStatusID == SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E023"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E025"); } } } } catch
	 * (IRollbackException e) { throw e; } catch (Exception e) { throw new
	 * IRollbackException(mySessionCtx, e.getMessage(), e); } }
	 */

	/**
	 * ���ڣ�֪ͨ����ת����/֧ȡ�����׵��ݴ淽����
	 * 
	 * 1�������� FixedDrawInfo,���� ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����lID����-1�����÷���this.DrawCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ� ��
	 * ���÷���Sett_TransFixedWithDrawDAO.update()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransFixedWithDrawDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * ��2�����lID��-1�����÷���Sett_TransFixedWithDrawDAO.add()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransFixedWithDrawDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * 
	 * @roseuid 3F73AECB006C
	 */
	public long drawTempSave(TransFixedDrawInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			// ��������»�����Ϣ
			if (info.getID() <= 0) // ����
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// ����״̬Ϊδ����
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // �޸��ݴ�
			{
				boolean flag = drawCheckIsTouched(info.getID(), info
						.getModifyDate());
				if (flag) {
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					lReturn = dao.update(info);

					if (lReturn != -1) {
						// ����״̬Ϊδ����
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ����ת����/֧ȡ�����׵ı��淽����
	 * 
	 * 1�������� FixedDrawInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixedDrawInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��FixedDrawInfo �� ����ǿգ�˵�����޸ı���:
	 * ���÷���this.drawCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * ���÷�����this.drawFindByID(),�õ�����ԭ���Ľ���ʵ����FixedDrawInfo��
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��FixedDrawInfo�� ��2�����÷�����Sett_TransFixedWithDrawDAO.add() ������Ϣ��
	 * ��3�����÷�����AccountDetail.saveOpenFixedDeposit()�����в�����
	 * ��4�����÷�����Sett_TransFixedWithDrawDAO.updateStatus() ���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AECF0111
	 */
	public long drawSave(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		long sessionID = -1;
		// ���ݷ��ʶ���
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// �˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		// �������ݷ��ʶ���
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// add by qhzhou 2007.6.19
			Timestamp endDate = info.getEndDate();
			Timestamp drawDate = info.getInterestStartDate();
			double remainAmount = info.getRemainAmount();
			String dpNo = info.getDepositNo().trim();
			long lCurrentAccountID = info.getCurrentAccountID();
			String lCurrentAccountNo = info.getCurrentAccountNo();
			String lCurrentAccountClientName = info.getCurrentAccountClientName();

			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// ��õ�ǰ״̬
			long lStatus = info.getStatusID();
			// �ݴ����ݱ���
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) 
			{
				log.debug("----------��ǰ״̬Ϊ�ݴ�-------------");

				// �ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransFixedDrawInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// ���޸Ĺ�
				if (errMsg != null && !errMsg.equals("")) 
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// �ж��Ƿ񱻷Ƿ��޸Ĺ�
				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.drawCheckIsTouched(info.getID(), info.getModifyDate())) 
				{
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} 
				else 
				{
					// δ�Ƿ��޸�
					log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------�������¶��ڽ�����Ϣ-------------");
					// �����������˻����
					log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveWithdrawFixedDeposit(info);
					log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					// ͨ�����߲����ӿ����ȡ�½��׺�
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					log.debug("----------�½��׺���: " + transNo + " -------------");
					info.setTransNo(transNo);
					// �޸�
					lReturn = dao.update(info);
					// �޸Ľ��׵�״̬Ϊ����
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					lReturn = biz.insertTransNo(info.getTransNo(), info.getNotifyId());

					// added by qhzhou 2007-07-23
					// Modified by qhzhou 2007.07.26 ������ǰ����֧ȡҵ��
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
						if (endDate != null && drawDate != null) {
							// added by qhzhou
							boolean b = (drawDate.compareTo(endDate) < 0)
									&& (remainAmount > 0);
							if (b&&(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))) {
								// TransFixedOpenInfo
								// n_tfoInfo=this.openFindByDepositNo(info.getDepositNo().trim());//�½�������Ϣ

								TransFixedOpenInfo n_tfoInfo = new TransFixedOpenInfo();
								n_tfoInfo.setTransNo(""); // �ÿս��׺�ʹ��ϵͳ�����ɵĽ��׺�
								n_tfoInfo.setDepositNo(info.getNewDepositNo()); // �����µĴ浥��
								if (lCurrentAccountID < 0) {
									n_tfoInfo.setBankID(info.getBankID());
									n_tfoInfo.setCurrentAccountID(-1);
									n_tfoInfo.setCurrentAccountNo("");
								} else {
									n_tfoInfo
											.setCurrentAccountID(lCurrentAccountID);
									n_tfoInfo
											.setCurrentAccountNo(lCurrentAccountNo);
									n_tfoInfo.setBankID(-1);
								}
								n_tfoInfo.setOfficeID(info.getOfficeID());
								n_tfoInfo.setCurrencyID(info.getCurrencyID());
								n_tfoInfo.setClientID(info.getClientID());
								n_tfoInfo.setAccountID(info.getAccountID());
								n_tfoInfo.setRate(info.getRate());
								n_tfoInfo.setStartDate(info.getStartDate());
								n_tfoInfo.setEndDate(info.getEndDate());
								n_tfoInfo.setDepositTerm(info.getDepositTerm());
								n_tfoInfo.setInterestPlanID(info
										.getInterestPlanID());

								n_tfoInfo.setAmount(0.0);// �������ݲ������ö���֧ȡ���˺��ٽ�����Ϊԭ������-֧ȡ��
								n_tfoInfo.setExecuteDate(info.getExecuteDate()); // �����¿����浥��ִ����Ϊ����֧ȡִ����
//								n_tfoInfo.setInterestStartDate(info
//										.getExecuteDate());// �����¿����浥����Ϣ��Ϊ����֧ȡִ����
								Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
								Timestamp interestStartDate = objSubAccountDAO.findByID(info.getSubAccountID()).getSubAccountFixedInfo().getStartDate();
								n_tfoInfo.setInterestStartDate(interestStartDate);
								n_tfoInfo.setInputDate(info.getInputDate());
								n_tfoInfo.setInputUserID(info.getInputUserID());// ???????????????

								n_tfoInfo
										.setTransactionTypeID(SETTConstant.TransactionType.OPENFIXEDDEPOSIT);

								n_tfoInfo
										.setCurrentAccountClientName(lCurrentAccountClientName);
								n_tfoInfo.setInputUserName(info
										.getInputUserName());
								n_tfoInfo.setCheckUserID(-1);
								n_tfoInfo.setCheckUserName("");

								n_tfoInfo.setBillNo(dpNo); // ����ԭ���ڿ������ݺ�
								n_tfoInfo
										.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// ������������״̬�����¶����״̬
																									// ����������״̬��
								// add by zcwang 2007-11-08
								// ������ȡʱ��֧ȡ��ժҪ��Ϊ�¿����浥��ժҪ
								n_tfoInfo.setAbstract(info.getAbstract());
								n_tfoInfo.setIsAutoContinue(info.getIsAutoContinue());
								n_tfoInfo.setAutocontinuetype(info.getAutocontinuetype());
								n_tfoInfo.setAutocontinueaccountid(info.getAutocontinueaccountid());
								//
								this.openSave(n_tfoInfo);
							}
						}
					}
					lReturn = newInfo.getID();
				}
			} 
			else  //�����ݴ棬����/�����ٱ���
			{
				//TransFixedOpenInfo n_tfoInfo=null;
				//����Ƕ�����ǰ����֧ȡ�������µĶ��ڿ����ʻ�

				log.debug("----------��ǰ״̬�����ݴ�-------------");
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
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------�½��׺���: " + transNo + " -------------");
					//����
					log.debug("----------��ʼ�������ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------�����������ڽ�����Ϣ-------------");
					//��3�����÷��������в�����
					log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveWithdrawFixedDeposit(info);
					log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					
					//��д����ָ��
					if(info.getInstructionNo() != null && info.getInstructionNo().length() > 0) 
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
						
						//Boxu Add 2008��4��2�� �������֧ȡ����ǰ����������ܴ��ڻ�������,������û�л��������ֶ�,��ʱ����"��������"
						if(info.getAdvanceRate() > 0.0)
						{
							financeInfo.setInterestRate(info.getAdvanceRate());
						}
						if(info.getRate()>0)
						{
							financeInfo.setInterestRate(info.getRate());
						}
						financeDao.updateStatusAndTransNo(null, financeInfo);
					}
					
					NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					long notifyId = -1;
					notifyId = biz.getStatus(info.getNotifyId());
					if (notifyId == 0) {
						//System.out.println("**********************dd1******************notifyId="+notifyId);
						throw new IRollbackException(mySessionCtx, "Sett_E150");
					} 
					else if (notifyId == 2) {
						//System.out.println("**********************dd2******************notifyId="+notifyId);
						throw new IRollbackException(mySessionCtx, "Sett_E152");
					} 
					else 
					{
						//System.out.println("***********info.getNotifyId()="+info.getNotifyId());
						//System.out.println("**********************dd3******************notifyId="+notifyId);

						//��֪֧ͨȡָ��ļ�¼���У���״̬��Ϊ��ʹ��,���ڸ���ָ�������ӽ��׺�
						minfo.setID(info.getNotifyId());
						minfo.setDepositNo(info.getDepositNo());
						minfo.setStatusID(SETTConstant.NotifyInformStatus.USED);
						lReturn = biz.insertTransNo(info.getTransNo(), info.getNotifyId());
						lReturn = biz.modify(minfo);
						//�޸Ľ��׵�״̬Ϊ���档
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

						//Modified by qhzhou 2007.07.26 ������ǰ����֧ȡҵ��
						if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
							if (endDate != null && drawDate != null) {
								// added by qhzhou
								boolean b = (drawDate.compareTo(endDate) < 0)
										&& (remainAmount > 0);
								if (b&&(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))) {
									// TransFixedOpenInfo
									// n_tfoInfo=this.openFindByDepositNo(info.getDepositNo().trim());//�½�������Ϣ

									TransFixedOpenInfo n_tfoInfo = new TransFixedOpenInfo();
									n_tfoInfo.setTransNo(""); // �ÿս��׺�ʹ��ϵͳ�����ɵĽ��׺�
									n_tfoInfo.setDepositNo(info.getNewDepositNo()); // �����µĴ浥��
									if (lCurrentAccountID < 0) {
										n_tfoInfo.setBankID(info.getBankID());
										n_tfoInfo.setCurrentAccountID(-1);
										n_tfoInfo.setCurrentAccountNo("");
									} else {
										n_tfoInfo.setCurrentAccountID(lCurrentAccountID);
										n_tfoInfo.setCurrentAccountNo(lCurrentAccountNo);
										n_tfoInfo.setBankID(-1);
									}
									n_tfoInfo.setOfficeID(info.getOfficeID());
									n_tfoInfo.setCurrencyID(info.getCurrencyID());
									n_tfoInfo.setClientID(info.getClientID());
									n_tfoInfo.setAccountID(info.getAccountID());
									n_tfoInfo.setRate(info.getRate());
									n_tfoInfo.setStartDate(info.getStartDate());
									n_tfoInfo.setEndDate(info.getEndDate());
									n_tfoInfo.setDepositTerm(info.getDepositTerm());
									n_tfoInfo.setInterestPlanID(info.getInterestPlanID());

									n_tfoInfo.setAmount(0.0);// �������ݲ������ö���֧ȡ���˺��ٽ�����Ϊԭ������-֧ȡ��
									n_tfoInfo.setExecuteDate(info.getExecuteDate()); // �����¿����浥��ִ����Ϊ����֧ȡִ����
									//n_tfoInfo.setInterestStartDate(info.getExecuteDate());// �����¿����浥����Ϣ��Ϊ����֧ȡִ����
									Sett_SubAccountDAO objSubAccountDAO = new Sett_SubAccountDAO();
									Timestamp interestStartDate = objSubAccountDAO.findByID(info.getSubAccountID()).getSubAccountFixedInfo().getStartDate();
									n_tfoInfo.setInterestStartDate(interestStartDate);
									n_tfoInfo.setInputDate(info.getInputDate());
									n_tfoInfo.setInputUserID(info.getInputUserID());// ???????????????

									n_tfoInfo.setTransactionTypeID(SETTConstant.TransactionType.OPENFIXEDDEPOSIT);

									n_tfoInfo.setCurrentAccountClientName(lCurrentAccountClientName);
									n_tfoInfo.setInputUserName(info.getInputUserName());
									n_tfoInfo.setCheckUserID(-1);
									n_tfoInfo.setCheckUserName("");

									n_tfoInfo.setBillNo(dpNo); // ����ԭ���ڿ������ݺ�
									n_tfoInfo.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// ������������״̬�����¶����״̬
																										// ����������״̬��
									// add by zcwang 2007-11-08
									// ������ȡʱ��֧ȡ��ժҪ��Ϊ�¿����浥��ժҪ
									n_tfoInfo.setAbstract(info.getAbstract());
									n_tfoInfo.setIsAutoContinue(info.getIsAutoContinue());
									n_tfoInfo.setAutocontinuetype(info.getAutocontinuetype());
									n_tfoInfo.setAutocontinueaccountid(info.getAutocontinueaccountid());
									//
									this.openSave(n_tfoInfo);
								}
							}
						}
					}
				} else {
					// ��������� �������ٱ���
					// �ж�״̬�Ƿ�Ϸ�
					log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
					TransFixedDrawInfo newInfo = dao.findByID(info.getID());
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					// ���޸Ĺ�
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// �ж��Ƿ񱻷Ƿ��޸Ĺ�
					log.debug("----------�Ǳ����ٱ���-------------");
					if (this.drawCheckIsTouched(info.getID(), info.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {

						// ������ɾ�����ݿ��е����еĴ浥
						log.debug("-------ɾ���˲��оɵ���Ϣ--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						accountBookOperation.deleteWithdrawFixedDeposit(newInfo);
						// δ�Ƿ��޸�
						log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						// deleteWithdrawFixedDeposit

						// added by qhzhou 2007.6.21
						// long lCurrentAccountID=info.getCurrentAccountID();
						// String lCurrentAccountNo=info.getCurrentAccountNo();
						// String
						// lCurrentAccountClientName=info.getCurrentAccountClientName();
						if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
							if (endDate != null && drawDate != null) {
								// added by qhzhou
								boolean b = (drawDate.compareTo(endDate) < 0)
										&& (remainAmount > 0);
								if (b&&(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))) {
									String oldOpenDepositNo = info.getDepositNo();

									TransFixedOpenInfo n_tfoInfo = this.openFindByOldDepositNo(oldOpenDepositNo);
									long oldCurrentAccountID = n_tfoInfo.getCurrentAccountID();
									if (n_tfoInfo != null) {
										if (lCurrentAccountID < 0) {
											n_tfoInfo.setBankID(info
													.getBankID());
											n_tfoInfo.setCurrentAccountID(-1);
											n_tfoInfo.setCurrentAccountNo("");
										} else {
											n_tfoInfo.setCurrentAccountID(lCurrentAccountID);
											n_tfoInfo.setCurrentAccountNo(lCurrentAccountNo);
											n_tfoInfo.setBankID(-1);
										}

										n_tfoInfo.setCurrentAccountClientName(lCurrentAccountClientName);
										// add by zcwang 2007-11-08
										// ������ȡʱ��֧ȡ��ժҪ��Ϊ�¿����浥��ժҪ
										n_tfoInfo.setAbstract(info.getAbstract());
										//
										openDao.update(n_tfoInfo);
									} 
									else {
										throw new IRollbackException(mySessionCtx, "�¿����浥�����ڣ�����ʧ��");
									}
								}
							}
						}
						log.debug("----------�������¶��ڽ�����Ϣ-------------");
						// �����������˻����
						log.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveWithdrawFixedDeposit(info);
						log.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");

						// ��֪֧ͨȡָ��ļ�¼���У���״̬��Ϊ��ʹ��
						NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
						TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
						long notifyId = -1;
						notifyId = biz.getStatus(info.getNotifyId());
						if (notifyId == 0) {
							// System.out.println("**********************dd4******************notifyId="+notifyId);
							throw new IRollbackException(mySessionCtx,
									"Sett_E150");
						} else if (notifyId == 2) {
							// System.out.println("**********************dd5******************notifyId="+notifyId);
							throw new IRollbackException(mySessionCtx,
									"Sett_E152");
						} else {// System.out.println("**********************dd6******************notifyId="+notifyId);

							minfo.setID(info.getNotifyId());
							minfo.setDepositNo(info.getDepositNo());
							minfo.setStatusID(SETTConstant.NotifyInformStatus.USED);
							lReturn = biz.insertTransNo(info.getTransNo(), info.getNotifyId());
							lReturn = biz.modify(minfo);
							// �޸�
							lReturn = dao.update(info);
							// �޸Ľ��׵�״̬Ϊ����
							lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						}

					}
				}
				/**
				 * ���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by ���� 2007-04-17
				 */
				if (info.getInutParameterInfo() != null) {
					log.debug("------�ύ����--------");
					// ���÷��صĵ�ַ����(����idֻ���ڽ��ױ���֮�����,tempInfo.getUrl()�õ���urlû�о���Ľ���id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(transNo);// ���ﱣ����ǽ��ױ��
					tempInfo.setDataEntity(info);
					// �ύ����
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					// ����״̬��������
					dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------�ύ�����ɹ�--------");

				}
				// liuguang �����ⲿ�˻�����
				// �˲������ӿ���
				AccountOperation accountOperation = new AccountOperation();
				
				ExternalAccountInfo extAccountInfo = new ExternalAccountInfo();
				extAccountInfo.setOfficeID(info.getOfficeID());
				extAccountInfo.setNcurrencyID(info.getCurrencyID());
				//���ӱ����տ��ⲿ�˻�
				extAccountInfo.setExtAcctNo(info.getExtAcctNo());
				extAccountInfo.setExtAcctName(info.getExtClientName());
				extAccountInfo.setBankName(info.getRemitInBank());
				extAccountInfo.setProvince(info.getRemitInProvince());
				extAccountInfo.setCity(info.getRemitInCity());
				accountOperation.saveExternalAccount(extAccountInfo);
				
				//������Ϣ�տ��ⲿ�˻�
				extAccountInfo.setExtAcctNo(info.getInterestExtAcctNo());
				extAccountInfo.setExtAcctName(info.getInterestExtClientName());
				extAccountInfo.setBankName(info.getInterestRemitInBank());
				extAccountInfo.setProvince(info.getInterestRemitInProvince());
				extAccountInfo.setCity(info.getInterestRemitInCity());
				accountOperation.saveExternalAccount(extAccountInfo);
				//����

			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}

	}

	/**
	 * ���ڣ�֪ͨ��֧ȡ/ת���ڽ��׵�ɾ��������
	 * 
	 * 1�������� FixedDrawInfo ����ʵ����
	 * 
	 * 2������ֵ�� long ,��ɾ���Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1�����÷���this.drawCheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 * ��2���жϲ���FixedDrawInfo �еı�����ʵ�����״̬�� ������ݴ棺
	 * 
	 * ���÷�����Sett_TransFixedWithDrawDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч��?
	 * 
	 * ����Ǳ��棺
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo
	 * 
	 * ���÷�����Sett_TransFixedWithDrawDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч��?
	 * 
	 * @roseuid 3F73AED3008A
	 */
	public long drawDelete(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;

		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// �������ݷ��ʶ���
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// �ж��Ƿ��ݴ�
				if (info.getTransNo() == null || info.getTransNo().equals("")) {
					// ��֪֧ͨȡָ��ļ�¼���У���״̬��Ϊ����
					System.out
							.println("******************��ʼ�޸�ָ֪ͨ���״̬***************");
					System.out.println("********info.getDepositNo()="
							+ info.getDepositNo());
					NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					minfo.setDepositNo(info.getDepositNo());
					minfo.setStatusID(SETTConstant.NotifyInformStatus.SAVE);
					minfo.setIsDele(1);// ������ָ֪ͨ����ɾ��ʱ���־
					minfo.setStransno(info.getTransNo());// �潻�׺�
					lReturn = biz.modify(minfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);

				} else {
					TransFixedDrawInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteWithdrawFixedDeposit(delInfo);
					// ɾ������ָ��
					if (delInfo.getInstructionNo() != null
							&& delInfo.getInstructionNo().length() > 0) {
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(
								delInfo.getInstructionNo()).longValue(),
								OBConstant.SettInstrStatus.REFUSE);
					}
					// ��֪֧ͨȡָ��ļ�¼���У���״̬��Ϊ����
					System.out
							.println("******************��ʼ�޸�ָ֪ͨ���״̬***************");
					System.out.println("********info.getDepositNo()="
							+ info.getDepositNo());
					NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					minfo.setDepositNo(info.getDepositNo());
					minfo.setStatusID(SETTConstant.NotifyInformStatus.SAVE);
					minfo.setIsDele(1);// ������ָ֪ͨ����ɾ��ʱ���־
					minfo.setStransno(info.getTransNo());// �潻�׺�
					lReturn = biz.modify(minfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);

					// Modified by qhzhou 2007.07.26 ������ǰ����֧ȡҵ��
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
						// added by qhzhou 2007.6.21
						// long CurrentAccountID=info.getCurrentAccountID();
						String oldOpenDepositNo = info.getDepositNo();
						// Modifyed by qhzhou 2007.6.23
						// ���ݸö��ڿ����浥�ţ��������Ƿ������µĶ��ڿ����ʻ������û�п�����¼��˵���ö���֧��������ǰ����֧ȡ
						TransFixedOpenInfo n_tfoInfo = this
								.openFindByOldDepositNo(oldOpenDepositNo);
						if (n_tfoInfo != null && n_tfoInfo.getID() > 0) {
							this.openDelete(n_tfoInfo);
						}
					}
				}
				//		
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ�����׵�ȡ�����˷�����
	 * 
	 * 1�������� FixedDrawInfo ����ʵ���� 2������ֵ�� long ,��ȡ�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.drawcheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 * 
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ��� ��
	 * ��3�����÷�����Sett_TransFixedWithDrawDAO.updateStatus���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AED60143
	 */
	public long drawCancelCheck(TransFixedDrawInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// �������ݷ��ʶ���
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {

				// added by qhzhou 2007.6.21 ����֧ȡҵ��
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
					// �ȶ�ϵͳ�Զ��������¶��ڴ���¼ȡ�����˲���
					// long CurrentAccountID=info.getCurrentAccountID();
					String oldOpenDepositNo = info.getDepositNo();
					// Modifyed by qhzhou 2007.6.23
					// ���ݸö��ڿ����浥�ţ��������Ƿ������µĶ��ڿ����ʻ������û�п�����¼��˵���ö���֧��������ǰ����֧ȡ
					TransFixedOpenInfo n_tfoInfo = this
							.openFindByOldDepositNo(oldOpenDepositNo);
					if (n_tfoInfo != null) {

						this.openCancelCheck(n_tfoInfo);// ȡ���¶��ڿ�������״̬
						n_tfoInfo = this
								.openFindByOldDepositNo(oldOpenDepositNo);
						if (n_tfoInfo != null) {
							n_tfoInfo
									.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// ���¶��ڿ���״̬��ء���������
							n_tfoInfo.setAmount(0.0);
							openDao.update(n_tfoInfo);
							// ȡ�����˲�����δ���˽�� add by zcwang 2007-8-14
							if (n_tfoInfo.getCurrentAccountID() > 0) {
								AccountEJB accountejb = new AccountEJB();
								Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
								subAccountDAO
										.updateUncheckPaymentAmount(
												accountejb
														.getCurrentSubAccoutIDByAccoutID(n_tfoInfo
																.getCurrentAccountID()),
												-(info.getAmount() - info
														.getDrawAmount()));
							}
							//
						}
					}
				}
				// ȡ������
				accountBookOperation.cancelCheckWithdrawFixedDeposit(info);

				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				// ����״̬����δ���˻�������(���ݸ�ҵ���Ƿ����������ж�)
				long lCancelCheckStatus = FSWorkflowManager
						.getSettCheckStatus(new InutParameterInfo(info
								.getOfficeID(), info.getCurrencyID(),
								Constant.ModuleType.SETTLEMENT, info
										.getTransactionTypeID(), -1));
				info.setStatusID(lCancelCheckStatus);
//				 //added by qhzhou 2007-07-25 ȡ������ʱӦ��ո��˱�ע��������
				//info.setCheckAbstract("");Modified by ylguo(��Ӣ��)at 2009-02-16ȡ�����˵�ʱ�򣬱�ע������գ���Ϊ�ڲ�ѯ��ʱ��Ҫ���ĵ����ڸ��˻�ȡ�����˵�ʱ��
				//��ע������Ӧ����ҳ�������ƣ�����˵��ѯ��ʱ��ҳ�����ܿ��������˺�ȡ�����˵���ȴ���ܿ�������
				//info.setCheckUserID(-1);
				//info.setCheckUserName("");
				//Modifying ended at 2009-02-16
				lReturn = dao.update(info);
				// if(lReturn!=-1)
				// {
				// lReturn = dao.updateStatus(info.getID(),
				// SETTConstant.TransactionStatus.SAVE);
				// }

				// added by qhzhou 2007.08.07 �������ʻ�
				//if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
				//	AccountBean accBean = new AccountBean();
				//	Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
				//	long subAccountId = accBean
				//			.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(
				//					info.getAccountID(), info.getDepositNo());
					// **************��ʼ�ָ�����������Ϣ
					// 2007-08-07*******************************************
					// //SubAccountFixedInfo
					// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);
					//				
				//	saDao.updateLoanPredrawInterestByID(subAccountId, info
				//			.getStrikePreDrawInterest());
					// *************************************************************************************
				//}
				
				//Add Boxu 2008��1��29�� ��ԭ������Ϣ
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) 
				{
					Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
					AccountBean accBean = new AccountBean();
					long subAccountId = accBean.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(info.getAccountID(), info.getDepositNo());
					saDao.RollbackDrawInterest(subAccountId);
				}
				
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ�����׵ĸ��˷�����
	 * 
	 * 1�������� FixedDrawInfo ����ʵ���� 2������ֵ�� long ,�����˵Ķ��ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.drawcheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣? ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 * ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 * ��3�����÷�����Sett_TransFixedWithDrawDAO.updateStatus���޸Ľ��׵�״̬Ϊ���ˡ�
	 * 
	 * @roseuid 3F73AEDA0102
	 */
	public long drawCheck(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();

		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// �������ݷ��ʶ���
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// added by qhzhou 2007.07.25 �������ʻ�

				AccountBean accBean = new AccountBean();
				Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
				long subAccountId = accBean
						.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(info
								.getAccountID(), info.getDepositNo());
				// SubAccountFixedInfo
				// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);
				// sInfo.setPreDrawInterest(0.00);
				// saDao.updateSubAccountFix(sInfo);

				// ����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				// ����������ſ��˻���
				accountBookOperation.checkWithdrawFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}

				lReturn = dao.update(info);
				if (lReturn != -1) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.CHECK);
				}
				
				// added by qhzhou 2007.6.21 ������ǰ����֧ȡҵ��
				//if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
					
					// ��ʼ�������������Ϣ 2007-08-07 ����������
					//SubAccountFixedInfo sInfo = saDao.findFixedSubAccountInfoByID(subAccountId);
					//saDao.updateLoanPredrawInterestByID(sInfo.getID(), 0.00);

					// long CurrentAccountID=info.getCurrentAccountID();
				String oldOpenDepositNo = info.getDepositNo();
				// Modifyed by qhzhou 2007.6.23
				// ���ݸö��ڿ����浥�ţ��������Ƿ������µĶ��ڿ����ʻ������û�п�����¼��˵���ö���֧ȡ������ǰ����֧ȡ
				TransFixedOpenInfo n_tfoInfo = null;
				if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
				{
					 n_tfoInfo = this.openFindByOldDepositNo(oldOpenDepositNo);
				}
					if (n_tfoInfo != null && n_tfoInfo.getID() > 0 && info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) 
					{
						
						n_tfoInfo.setCheckUserID(info.getCheckUserID());
						//2011-03-29 by jiangqi 
						n_tfoInfo.setIsAutoContinue(info.getIsAutoContinue());
						n_tfoInfo.setAutocontinuetype(info.getAutocontinuetype());
						n_tfoInfo.setAutocontinueaccountid(info.getAutocontinueaccountid());
						
						n_tfoInfo.setAmount(info.getAmount()
								- info.getDrawAmount());
						// this.openSave(n_tfoInfo);
						openDao.update(n_tfoInfo);
						TransFixedOpenInfo n_tfoInfo1 = this
								.openFindByOldDepositNo(oldOpenDepositNo);

						this.openCheckForPartDraw(n_tfoInfo1);
						TransFixedOpenInfo n_tfoInfo2 = this
								.openFindByOldDepositNo(oldOpenDepositNo);

						n_tfoInfo.setInputUserID(info.getInputUserID());
						n_tfoInfo.setInputUserName(info.getInputUserName());
						n_tfoInfo.setCheckUserID(info.getCheckUserID());
						n_tfoInfo.setCheckUserName(info.getCheckUserName());
						n_tfoInfo.setModifyDate(n_tfoInfo2.getModifyDate());
						n_tfoInfo
								.setStatusID(SETTConstant.TransactionStatus.CHECK);
						

						
						openDao.update(n_tfoInfo);
						// ����û����δ���˽��,���˲��۳�δ���˽��,��������� add by zcwang 2007-8-14

						if (n_tfoInfo.getCurrentAccountID() > 0) {
							AccountEJB accountejb = new AccountEJB();
							Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
							subAccountDAO.updateUncheckPaymentAmount(accountejb
									.getCurrentSubAccoutIDByAccoutID(n_tfoInfo
											.getCurrentAccountID()), info
									.getAmount()
									- info.getDrawAmount());
						}
						

						//�Ƿ���Ҫ��������ָ��
						boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
						boolean bCreateInstruction = false;	
						boolean bCreateInstruction1 = false;	
						long bankID = info.getBankID();
						long bankID1 = n_tfoInfo1.getBankID();
						try {
							//���ô˷�����bankID��ֵ��Ϊ��������ID
							bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
							bCreateInstruction1 = accountBookOperation.isCreateInstruction(bankID1);
						} catch (Exception e1) {
							log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
							e1.printStackTrace();
						}
						
						try
						{
							if(bIsValid && bCreateInstruction && bCreateInstruction1) {
								Log.print("*******************��ʼ�������ڲ���֧ȡ����ָ�������**************************");
								try {
									log.debug("------��ʼ����֧ȡ��֪ͨ���֧ȡ���׸���ָ������--------");
									Collection coll = new ArrayList();
									//�������
									//֧ȡINFO
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									instructionParam.setTransactionTypeID(info.getTransactionTypeID());
									instructionParam.setObjInfo(info);
									instructionParam.setOfficeID(info.getOfficeID());
									instructionParam.setCurrencyID(info.getCurrencyID());
									instructionParam.setCheckUserID(info.getCheckUserID());
									instructionParam.setBankType(bankID);
									instructionParam.setInputUserID(info.getInputUserID());
									coll.add(instructionParam);
									/*����INFO ���ֿ�������ָ��
									instructionParam = new CreateInstructionParam();
									instructionParam.setTransactionTypeID(n_tfoInfo1.getTransactionTypeID());
									instructionParam.setObjInfo(n_tfoInfo1);
									instructionParam.setOfficeID(n_tfoInfo1.getOfficeID());
									instructionParam.setCurrencyID(n_tfoInfo1.getCurrencyID());
									instructionParam.setCheckUserID(n_tfoInfo1.getCheckUserID());
									instructionParam.setBankType(bankID1);
									instructionParam.setInputUserID(n_tfoInfo1.getInputUserID());
									coll.add(instructionParam);
									*/
									//��������ָ�����
									IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
									bankInstruction.createSpecialBankInstruction(coll);
									
									log.debug("------���ɶ��ڲ���֧ȡ���׸���ָ��ɹ�--------");
									
								} catch (Throwable e) {
									log.error("���ɶ��ڲ���֧ȡ���׸���ָ��ʧ��");
									e.printStackTrace();
									throw new IRollbackException(mySessionCtx, "���ɶ��ڲ���֧ȡ���׸���ָ��ʧ�ܣ�"+e.getMessage());
								}
							}
							else {
								Log.print("û�����нӿڻ���Ҫ��������ָ�");
							}
						}
						catch (Exception e)
						{
							throw new IRollbackException(mySessionCtx, "��������ת��ָ�����" + e.getMessage());
						}
						// ��ʼ�������������Ϣ
						// SubAccountFixedInfo
						// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);

						// saDao.updateLoanPredrawInterestByID(sInfo.getID(),0.00);
					//}
				}
				else {
						
						//�Ƿ���Ҫ��������ָ��
						boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
						boolean bCreateInstruction = false;			
						long bankID = info.getBankID();
						try {
							//���ô˷�����bankID��ֵ��Ϊ��������ID
							bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
						} catch (Exception e1) {
							log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
							e1.printStackTrace();
						}
						
						try
						{
							if(bIsValid && bCreateInstruction) {
								Log.print("*******************��ʼ��������֧ȡ��֪ͨ���֧ȡ����ָ�������**************************");
								try {
									log.debug("------��ʼ����֧ȡ��֪ͨ���֧ȡ���׸���ָ������--------");
									//�������
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									instructionParam.setTransactionTypeID(info.getTransactionTypeID());
									instructionParam.setObjInfo(info);
									instructionParam.setOfficeID(info.getOfficeID());
									instructionParam.setCurrencyID(info.getCurrencyID());
									instructionParam.setCheckUserID(info.getCheckUserID());
									instructionParam.setBankType(bankID);
									instructionParam.setInputUserID(info.getInputUserID());
									
									//��������ָ�����
									IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
									bankInstruction.createBankInstruction(instructionParam);
									
									log.debug("------���ɶ���֧ȡ��֪ͨ���֧ȡ���׸���ָ��ɹ�--------");
									
								} catch (Throwable e) {
									log.error("���ɶ���֧ȡ��֪ͨ���֧ȡ���׸���ָ��ʧ��");
									e.printStackTrace();
									throw new IRollbackException(mySessionCtx, "���ɶ���֧ȡ��֪ͨ���֧ȡ���׸���ָ��ʧ�ܣ�"+e.getMessage());
								}
							}
							else {
								Log.print("û�����нӿڻ���Ҫ��������ָ�");
							}
						}
						catch (Exception e)
						{
							throw new IRollbackException(mySessionCtx, "��������ת��ָ�����" + e.getMessage());
						}
					}
				
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ��֧ȡ/ת���ڽ�����ϸ�ķ�����
	 * 
	 * 1�������� lID long , ���׵�ID
	 * 
	 * 2������ֵ�� FixedDrawInfo,���ڣ�֪ͨ������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedWithDrawtDAO.findByID()
	 * �õ��������׵���ϸ��FixedDrawInfo��
	 * 
	 * @roseuid 3F73AEDD03A5
	 */
	public TransFixedDrawInfo drawFindByID(long lID) throws IRollbackException,
			RemoteException {
		TransFixedDrawInfo info = new TransFixedDrawInfo();
		AccountOperation acctOperation = new AccountOperation();
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			info = dao.findByID(lID);
			AccountInfo clientInfo = acctOperation.findAccountByID(info
					.getAccountID());
			//SubAccountAssemblerInfo sub_accountinfo = acctOperation.findSubAccountByID(info.getSubAccountID());
			info.setAccountNo(clientInfo.getAccountNo());
			info.setAccountName(clientInfo.getAccountName());
			info.setMinSingleAmount(clientInfo.getMinSinglePayAmount());
			//info.setIsAutoContinue(sub_accountinfo.getSubAccountFixedInfo().getIsAutoContinue());
			//info.setAutocontinuetype(sub_accountinfo.getSubAccountFixedInfo().getAutoContinueType());
			//info.setAutocontinueaccountid(sub_accountinfo.getSubAccountFixedInfo().getInterestAccountID());
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1�������� QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ�� Collection ,����FixedDrawInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ����Sett_TransFixedWithDrawDAO.findByStatus()������
	 * 
	 * @roseuid 3F73AEE1021A
	 */
	public Collection drawFindByStatus(QueryByStatusConditionInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();

		try {
			coll = dao.findByStatus(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ���ݽ��׺Ų�ѯ����֧ȡ������ϸ�ķ�����
	 * 
	 * 1�������� strTransNo , �������׺�
	 * 
	 * 2������ֵ�� FixedDrawInfo,���ڽ���ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedContinueDAO.findByID()
	 * �õ��������׵���ϸ��FixedContinueInfo��
	 * 
	 * @roseuid 3F73AEF900AA
	 */
	public TransFixedDrawInfo drawFindByTransNo(String strTransNo)
			throws IRollbackException, RemoteException {
		TransFixedDrawInfo info = new TransFixedDrawInfo();
		AccountOperation acctOperation = new AccountOperation();
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			info = dao.findByTransNo(strTransNo);
			AccountInfo clientInfo = acctOperation.findAccountByID(info
					.getAccountID());
			SubAccountAssemblerInfo sub_accountinfo = acctOperation.findSubAccountByID(info.getSubAccountID());
			info.setAccountNo(clientInfo.getAccountNo());
			info.setAccountName(clientInfo.getAccountName());
			info.setMinSingleAmount(clientInfo.getMinSinglePayAmount());
			//info.setIsAutoContinue(sub_accountinfo.getSubAccountFixedInfo().getIsAutoContinue());//bug 20370 ������� ���ڲ���֧ȡ���´浥ѡ�񲻵������棬���ײ�ѯ����ʾ�ɵ������� 
			info.setAutocontinuetype(sub_accountinfo.getSubAccountFixedInfo().getAutoContinueType());
			info.setAutocontinueaccountid(sub_accountinfo.getSubAccountFixedInfo().getInterestAccountID());

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1�������� FixedDrawInfo,���ڣ�֪ͨ�����ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ�� Collection ,����FixedDrawInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ���÷�����Sett_TransFixedWithDrawDAO.match()
	 * 
	 * @roseuid 3F73AEE4034A
	 */
	public Collection drawMatch(TransFixedDrawInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			// ����ƥ�临��״̬
			info.setStatusID(FSWorkflowManager
					.getSettCheckStatus(new InutParameterInfo(info
							.getOfficeID(), info.getCurrencyID(),
							Constant.ModuleType.SETTLEMENT, info
									.getTransactionTypeID(), -1)));
			coll = dao.match(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * �жϽ��׼�¼�Ƿ��޸Ĺ����ݴ淽����
	 * 
	 * 1�������� lID, ����ID�� long, ԭ����TouchTime��
	 * 
	 * 2������ֵ�� boolean, ������޸ģ�����true�����򣬷���false��
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedWithDrawDAO.findByID,�õ����µĽ��ס�
	 * 
	 * ��2���жϴ����TouchTime�Ƿ����ѯ����һ�£������һ�£�����true�����򷵻�false?
	 * 
	 * @roseuid 3F73AEE702CC
	 */
	private boolean drawCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			TransFixedDrawInfo info = dao.findByID(lID);
			// �ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null
					|| lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * ���ڣ�֪ͨ����֧ȡ/ת���ڣ����׵ļ����ķ�����
	 * 
	 * 1�������� FixedDrawInfo ����ʵ����
	 * 
	 * 2������ֵ�� TransFixedDrawInfo ����ʵ����
	 * 
	 * 
	 * 3���߼�˵���� ��1�����÷�����XXX.findByID(),�õ��������˻�����Ϣ��(�����˻�����ȡֵ)
	 * ��2�����������˻�����Ϣ��д������ʵ��TransFixedDrawInfo �������ء�
	 * 
	 * @roseuid 3F73AEEA01B8
	 */
	public TransFixedDrawInfo drawNext(TransFixedDrawInfo info)
			throws IRollbackException, RemoteException {
		TransFixedDrawInfo infoReturn = new TransFixedDrawInfo();
		// �˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		log.debug("----------����ת���ڼ���-------------");
		SubAccountAssemblerInfo fixedInfo = acctOperation
				.findSubAccountByID(info.getSubAccountID());
		AccountInfo clientInfo = acctOperation.findAccountByID(info
				.getAccountID());
		log.debug("----------����ת���ڼ���-------------");
		
		// ������Ϣ
		// infoReturn.setExecuteDate(info.getExecuteDate());
		infoReturn.setMinSingleAmount(clientInfo.getMinSinglePayAmount());
		infoReturn.setInterestStartDate(info.getInterestStartDate());
		infoReturn.setSubAccountID(info.getSubAccountID());
		infoReturn.setAccountID(fixedInfo.getSubAccountFixedInfo().getAccountID());
		infoReturn.setAccountNo(NameRef.getAccountNoByID(infoReturn.getAccountID()));
		infoReturn.setClientID(NameRef.getClientIDByAccountID(infoReturn.getAccountID()));
		infoReturn.setClientName(NameRef.getClientNameByAccountID(infoReturn.getAccountID()));
		infoReturn.setStartDate(fixedInfo.getSubAccountFixedInfo().getStartDate());
		infoReturn.setEndDate(fixedInfo.getSubAccountFixedInfo().getEndDate());
		infoReturn.setDepositTerm(fixedInfo.getSubAccountFixedInfo().getDepositTerm());
		infoReturn.setNoticeDay(fixedInfo.getSubAccountFixedInfo().getNoticeDay());
		infoReturn.setPreDrawInterest(fixedInfo.getSubAccountFixedInfo().getPreDrawInterest());  //������Ϣ
		infoReturn.setIsAutoContinue(fixedInfo.getSubAccountFixedInfo().getIsAutoContinue());//�Ƿ��Զ�����
		infoReturn.setAutocontinuetype(fixedInfo.getSubAccountFixedInfo().getAutoContinueType());//�Զ����淽ʽ
		infoReturn.setAutocontinueaccountid(fixedInfo.getSubAccountFixedInfo().getInterestAccountID());//��Ϣ�˻�id
		// ֪ͨ�붨��֧ȡ���ʴ���ʽ��ͬ
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
			infoReturn.setRate(fixedInfo.getSubAccountFixedInfo().getRate());
		} else if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) {
			infoReturn.setRate(info.getRate());
			infoReturn.setDepositBalance(fixedInfo.getSubAccountFixedInfo()
					.getBalance()
					- info.getDrawAmount());
		}

		infoReturn.setAmount(fixedInfo.getSubAccountFixedInfo().getOpenAmount());
		infoReturn.setDrawAmount(info.getDrawAmount());

		InterestOperation io = new InterestOperation();
		InterestsInfo ioInfo = new InterestsInfo();

		// ֪ͨ�붨����Ϣ����Ҳ��ͬ
		if (info.getTransactionTypeID() == SETTConstant.TransactionType.NOTIFYDEPOSITDRAW) {
			double interest = 0.0;
			try {
				interest = io.calculateNoticeDepositAccountInterest(info
						.getCurrencyID(), info.getRate(),
						SETTConstant.InterestRateTypeFlag.YEAR, info
								.getDrawAmount(), infoReturn.getStartDate(),
						infoReturn.getInterestStartDate());
			} catch (IException e) {
				throw new IRollbackException(mySessionCtx, e.getErrorCode());

			}
			infoReturn.setDrawInterest(UtilOperation.Arith.round(interest, 2));
		} else if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
			try {
				double baseAmount = 0.0;
				if (info.getDrawAmount() == 0)
					baseAmount = info.getAmount();
				else
					baseAmount = info.getDrawAmount();
				log.debug("��ǰ֧ȡ�����ǣ�" + info.getAdvanceRate());
				// ioInfo =
				// io.calculateFixedDepositAccountInterest(info.getSubAccountID(),baseAmount,info.getExecuteDate());
				ioInfo = io.calculateFixedDepositAccountInterest(info
						.getSubAccountID(), baseAmount, info.getExecuteDate(),
						info.getAdvanceRate());
			} catch (IException e) {
				throw new IRollbackException(mySessionCtx, e.getErrorCode());

			}
			infoReturn.setPreDrawInterest(ioInfo.getPreDrawInterest());
			if(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))
			{
				infoReturn.setStrikePreDrawInterest(ioInfo
						.getStrikePreDrawInterest());
			}
			else
			{
				if(fixedInfo.getSubAccountFixedInfo().getBalance()!=info.getDrawAmount()&&!Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_STRIKEALLPREDRAWINTEREST,true))
				{
					infoReturn.setStrikePreDrawInterest(info.getDrawAmount()*ioInfo
							.getStrikePreDrawInterest()/fixedInfo.getSubAccountFixedInfo().getBalance());
				}
				else
				{
					infoReturn.setStrikePreDrawInterest(ioInfo.getStrikePreDrawInterest());
				}
			}
			infoReturn.setCurrentInterest(ioInfo.getCurrentInterest());
			infoReturn.setPayableInterest(ioInfo.getInterestPayment());
		}
		// infoReturn.setTotalInterest(ioInfo.getTotalInterest());
		io.closeConnection();
		log.debug("----------����ת���ڳɹ�-------------");
		return infoReturn;
	}

	/**
	 * ���ڣ�֪ͨ���������ת�潻�׵��ݴ淽����
	 * 
	 * 1�������� FixedContinueInfo,���� ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����lID����-1�����÷���this.continueCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��� �Ĺ���
	 * ���÷���Sett_TransFixedContinueDAO.update()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransFixedContinueDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * ��2�����lID��-1�����÷���Sett_TransFixedContinueDAO.add()���潻�׼�¼��Ϣ��
	 * ���÷���Sett_TransFixedContinueDAO.update()���ļ�¼��״̬Ϊδ���档
	 * 
	 * @roseuid 3F73AEEC028D
	 */
	public long continueTempSave(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			// �жϴ浥���Ƿ��ظ�
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// ��������»�����Ϣ
			if (info.getID() <= 0) // ����
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// ����״̬Ϊδ����
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // �޸��ݴ�
			{
				boolean flag = continueCheckIsTouched(info.getID(), info
						.getModifyDate());
				if (flag) {
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					lReturn = dao.update(info);

					if (lReturn != -1) {
						// ����״̬Ϊδ����
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.TEMPSAVE);
					}
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ��������ת�潻�׵ı��淽����
	 * 
	 * 1�������� FixedContinueInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixedContinueInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��FixedContinueInfo �� ����ǿգ�˵�����޸ı���:
	 * ���÷���this.continueCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * 
	 * ���÷�����this.continueFindByID(),�õ�����ԭ���Ľ���ʵ����FixedContinueInfo��
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��FixedContinueInfo�� ��2�����÷�����Sett_TransFixedContinueDAO.add() ������Ϣ��
	 * ��3�����÷�����AccountDetail.saveOpenFixedDeposit()�����в�����
	 * ��4�����÷�����Sett_TransFixedContinueDAO.update()���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AEEE038A
	 */
	public long continueSave(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		// ���ݷ��ʶ���
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �жϴ浥���Ƿ��ظ�
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			// ��õ�ǰ״̬
			long lStatus = info.getStatusID();
			// �ݴ����ݱ���
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) {
				log.debug("----------��ǰ״̬Ϊ�ݴ�-------------");

				// �ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransFixedContinueInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// ���޸Ĺ�
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// �ж��Ƿ񱻷Ƿ��޸Ĺ�
				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.continueCheckIsTouched(info.getID(), info
						.getModifyDate())) {
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// δ�Ƿ��޸�
					log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------�������¶��ڽ�����Ϣ-------------");
					// �����������˻����
					log
							.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveContinueFixedDeposit(info);
					log
							.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					// ͨ�����߲����ӿ����ȡ�½��׺�
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					log.debug("----------�½��׺���: " + transNo + " -------------");
					info.setTransNo(transNo);
					// �޸�
					lReturn = dao.update(info);
					// �޸Ľ��׵�״̬Ϊ����
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);
				}
			} else // �����ݴ�
			{
				log.debug("----------��ǰ״̬�����ݴ�-------------");
				// ��ȡ��ǰ���׺�
				String transNo = info.getTransNo();
				// ��־λ���Ƿ����������׺�
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals("")) {
					log.debug("----------����������-------------");
					// δ��������������½��׺�
					bNewTransNo = true;
					// ͨ�����߲����ӿ����ȡ�½��׺�
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------�½��׺���: " + transNo + " -------------");
					// ����
					log.debug("----------��ʼ�������ڽ�����Ϣ-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------�����������ڽ�����Ϣ-------------");
					// ��3�����÷��������в�����
					log
							.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveContinueFixedDeposit(info);
					log
							.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
					// ��д����ָ��
					if (info.getInstructionNo() != null
							&& info.getInstructionNo().length() > 0) {
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

						financeInfo.setID(Long.valueOf(info.getInstructionNo())
								.longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						financeDao.updateStatusAndTransNo(null, financeInfo);
					}
					// �޸Ľ��׵�״̬Ϊ���档
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);

				} else {
					// ��������� �������ٱ���
					// �ж�״̬�Ƿ�Ϸ�
					log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
					TransFixedContinueInfo newInfo = dao.findByID(info.getID());
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info
							.getStatusID(), lNewStatusID,
							SETTConstant.Actions.MODIFYSAVE);
					// ���޸Ĺ�
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// �ж��Ƿ񱻷Ƿ��޸Ĺ�
					log.debug("----------�Ǳ����ٱ���-------------");
					if (this.continueCheckIsTouched(info.getID(), info
							.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {
						// ������ɾ�����ݿ��е����еĴ浥
						log.debug("------��ʼɾ���˲���Ϣ--------");
						accountBookOperation
								.deleteContinueFixedDeposit(newInfo);
						log.debug("------����ɾ���˲���Ϣ--------");
						// δ�Ƿ��޸�
						log.debug("----------��ʼ���¶��ڽ�����Ϣ-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------�������¶��ڽ�����Ϣ-------------");
						// �����������˻����
						log
								.debug("----------��ʼaccountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveContinueFixedDeposit(info);
						log
								.debug("----------����accountBookOperation::saveOpenFixedDeposit-------------");
						// �޸�
						lReturn = dao.update(info);
						// �޸Ľ��׵�״̬Ϊ����
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.SAVE);
					}
				}
				/**
				 * ���Info�е�InutParameterInfo��Ϊ��,����Ҫ�ύ���� add by ���� 2007-04-17
				 */
				if (info.getInutParameterInfo() != null) {
					log.debug("------�ύ����--------");
					// ���÷��صĵ�ַ����(����idֻ���ڽ��ױ���֮�����,tempInfo.getUrl()�õ���urlû�о���Ľ���id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(transNo);// ���ﱣ����ǽ��ױ��
					tempInfo.setDataEntity(info);
					// �ύ����
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					// ����״̬��������
					dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------�ύ�����ɹ�--------");

				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}

	}

	/**
	 * ��������ת�潻�׵�ɾ��������
	 * 
	 * 1�������� FixedContinueInfo ����ʵ����
	 * 
	 * 2������ֵ�� long ,��ɾ���Ķ��ڽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1�����÷���this.continueCheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 * ��2���жϲ���FixedContinueInfo �еĽ���ʵ�����״̬�� ������ݴ棺
	 * 
	 * ���÷�����Sett_TransFixedContinueDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч��?
	 * 
	 * ����Ǳ��棺
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��FixedContinueInfo
	 * 
	 * ���÷�����Sett_TransFixedContinueDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч��?
	 * 
	 * @roseuid 3F73AEF1017B
	 */
	public long continueDelete(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// �ж��Ƿ��ݴ�
				if (info.getTransNo() == null || info.getTransNo().equals("")) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
				} else {
					TransFixedContinueInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteContinueFixedDeposit(delInfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
					if (delInfo.getInstructionNo() != null
							&& delInfo.getInstructionNo().length() > 0) {
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(
								delInfo.getInstructionNo()).longValue(),
								OBConstant.SettInstrStatus.REFUSE);
					}
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ��������ת����׵ĸ��˷�����
	 * 
	 * 1�������� FixedContinueInfo ����ʵ���� 2������ֵ�� long ,�����˵Ķ��ڣ�֪ͨ�������׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.continueCheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳�?
	 * ������Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 * ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 * ��3�����÷�����Sett_TransFixedContinueDAO.updateStatus���޸Ľ��׵�״̬Ϊ���ˡ�
	 * 
	 * @roseuid 3F73AEF40035
	 */
	public long continueCheck(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();

		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// ����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�

				// ����������ſ��˻���
				accountBookOperation.checkContinueFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				lReturn = dao.update(info);
				if (lReturn != -1) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.CHECK);
				}
				//�Ƿ�������ӿ�
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//�Ƿ���Ҫ��������ָ��
				boolean bCreateInstruction = false;
				long bankID = info.getInterestBankID();
				try {
					//���ô˷�����bankID��ֵ��Ϊ��������ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {
					log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
					e1.printStackTrace();
				}
				
				try
				{
					if(bIsValid && bCreateInstruction) {
						log.debug("------��ʼ��������ת�潻��ָ�����ɣ���Ϣ--------");
						try {
							log.debug("------��ʼ��������ת�潻��ָ�����ɣ���Ϣ--------");
							//�������
							CreateInstructionParam instructionParam = new CreateInstructionParam();
							instructionParam.setTransactionTypeID(info.getTransactionTypeID());
							instructionParam.setObjInfo(info);
							instructionParam.setOfficeID(info.getOfficeID());
							instructionParam.setCurrencyID(info.getCurrencyID());
							instructionParam.setCheckUserID(info.getCheckUserID());
							instructionParam.setBankType(bankID);
							instructionParam.setInputUserID(info.getInputUserID());
							
							//��������ָ�����
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createBankInstruction(instructionParam);
							
							log.debug("------���ɶ�������ת�潻��ָ��ɹ�����Ϣ--------");
							
						} catch (Throwable e) {
							log.error("���ɶ�������ת�潻��ָ��ʧ��");
							e.printStackTrace();
							throw new IRollbackException(mySessionCtx, "���ɶ�������ת�潻��ָ��ʧ�ܣ�"+e.getMessage());
						}
						log.debug("------������������ת�潻��ָ�����ɣ���Ϣ--------");
					}
//					else {
//						Log.print("û�����нӿڻ���Ҫ��������ָ�");
//					}
				}
				catch (Exception e)
				{
					throw new IRollbackException(mySessionCtx, "��������ת��ָ�����" + e.getMessage());
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ��������ת�潻�׵�ȡ�����˷�����
	 * 
	 * 1�������� FixedContinueInfo ����ʵ���� 2������ֵ�� long ,��ȡ�����˵Ķ��ڽ��׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.continueCheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳�?
	 * ������Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 * 
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ��� ��
	 * ��3�����÷�����Sett_TransFixedContinueDAO.updateStatus���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AEF60312
	 */
	public long continueCancelCheck(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// ���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		// �˲������ӿ���
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// �Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// ����Ƿ��޸Ĺ�
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				// ȡ������
				accountBookOperation.cancelCheckContinueFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecuteDate());
					financeInfo.setFinishDate(info.getExecuteDate());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null, financeInfo);
				}
				// ����״̬����δ���˻�������(���ݸ�ҵ���Ƿ����������ж�)
				long lCancelCheckStatus = FSWorkflowManager
						.getSettCheckStatus(new InutParameterInfo(info
								.getOfficeID(), info.getCurrencyID(),
								Constant.ModuleType.SETTLEMENT, info
										.getTransactionTypeID(), -1));
				info.setStatusID(lCancelCheckStatus);
				lReturn = dao.update(info);
				// if(lReturn!=-1)
				// {
				// lReturn = dao.updateStatus(info.getID(),
				// SETTConstant.TransactionStatus.SAVE);
				// }

			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
			// ����
			try {
				if (sessionID != -1) // ��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}

	/**
	 * ���ݱ�ʶ��ѯ��������ת�潻����ϸ�ķ�����
	 * 
	 * 1�������� lID long , �������׵�ID
	 * 
	 * 2������ֵ�� FixedContinueInfo,���ڽ���ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedContinueDAO.findByID()
	 * �õ��������׵���ϸ��FixedContinueInfo��
	 * 
	 * @roseuid 3F73AEF900AA
	 */
	public TransFixedContinueInfo continueFindByID(long lID)
			throws IRollbackException, RemoteException {
		TransFixedContinueInfo info = new TransFixedContinueInfo();

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			info = dao.findByID(lID);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ���ݽ��׺Ų�ѯ��������ת�潻����ϸ�ķ�����
	 * 
	 * 1�������� strTransNo , �������׺�
	 * 
	 * 2������ֵ�� FixedContinueInfo,���ڽ���ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedContinueDAO.findByID()
	 * �õ��������׵���ϸ��FixedContinueInfo��
	 * 
	 * @roseuid 3F73AEF900AA
	 */
	public TransFixedContinueInfo continueFindByTransNo(String strTransNo)
			throws IRollbackException, RemoteException {
		TransFixedContinueInfo info = new TransFixedContinueInfo();

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			info = dao.findByTransNo(strTransNo);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1�������� QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ�� Collection ,����FixedContinueInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ����Sett_TransFixedContinueDAO.findByStatus()������
	 * 
	 * @roseuid 3F73AEFC0393
	 */
	public Collection continueFindByStatus(QueryByStatusConditionInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			coll = dao.findByStatus(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1�������� FixedContinueInfo,���ڽ��ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ�� Collection ,����FixedContinueInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ���÷�����Sett_TransFixedContinueDAO.match()
	 * 
	 * @roseuid 3F73AEFF00BC
	 */
	public Collection continueMatch(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			// ����ƥ�临��״̬
			info.setStatusID(FSWorkflowManager
					.getSettCheckStatus(new InutParameterInfo(info
							.getOfficeID(), info.getCurrencyID(),
							Constant.ModuleType.SETTLEMENT, info
									.getTransactionTypeID(), -1)));
			coll = dao.match(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ��������ת�潻�׵ļ����ķ�����
	 * 
	 * 1�������� FixedContinueInfo ����ʵ����
	 * 
	 * 2������ֵ�� FixedContinueInfo ����ʵ����
	 * 
	 * 
	 * 3���߼�˵���� ��1�����÷�����XXX.findByID(),�õ��������˻�����Ϣ��(�����˻�����ȡֵ)
	 * ��2�����������˻�����Ϣ��д������ʵ��FixedContinueInfo �������ء�
	 * 
	 * @roseuid 3F73AF010141
	 */
	public TransFixedContinueInfo continueNext(TransFixedContinueInfo info) throws IRollbackException, RemoteException {
		TransFixedContinueInfo infoReturn = new TransFixedContinueInfo();
		//�˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		log.debug("----------����ת�����-------------");

		SubAccountAssemblerInfo fixedInfo = acctOperation.findSubAccountByID(info.getSubAccountID());
		log.debug("----------����ת�����-------------");
		//������Ϣ
		infoReturn.setExecuteDate(info.getExecuteDate());
		infoReturn.setSubAccountID(info.getSubAccountID());
		infoReturn.setAccountID(fixedInfo.getSubAccountFixedInfo().getAccountID());
		infoReturn.setAccountNo(NameRef.getAccountNoByID(infoReturn.getAccountID()));
		infoReturn.setClientID(NameRef.getClientIDByAccountID(infoReturn.getAccountID()));
		infoReturn.setClientName(NameRef.getClientNameByAccountID(infoReturn.getAccountID()));
		infoReturn.setStartDate(fixedInfo.getSubAccountFixedInfo().getStartDate());
		infoReturn.setEndDate(fixedInfo.getSubAccountFixedInfo().getEndDate());
		infoReturn.setDepositTerm(fixedInfo.getSubAccountFixedInfo().getDepositTerm());
		infoReturn.setRate(fixedInfo.getSubAccountFixedInfo().getRate());
		infoReturn.setAmount(fixedInfo.getSubAccountFixedInfo().getBalance());
		infoReturn.setIsAutoContinue(fixedInfo.getSubAccountFixedInfo().getIsAutoContinue());
		infoReturn.setAutocontinuetype(fixedInfo.getSubAccountFixedInfo().getAutoContinueType());
		infoReturn.setAutocontinueaccountid(fixedInfo.getSubAccountFixedInfo().getInterestAccountID());

		UtilOperation uo = new UtilOperation();
		try 
		{
			String strNewDepositNo = uo.getOpenDepositNoBackGround(infoReturn.getAccountID());
			infoReturn.setNewDepositNo(strNewDepositNo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}

		//��ʱ����
		//infoReturn.setPreDrawInterest(50.00);
		//infoReturn.setPayableInterest(10.00);
		//��Ϣ��Ϣ
		InterestOperation io = new InterestOperation();
		InterestsInfo ioInfo = new InterestsInfo();
		
		try
		{
			double baseAmount = 0.0;
			baseAmount = info.getAmount();
			ioInfo = io.calculateFixedDepositAccountInterest(info.getSubAccountID(), baseAmount, info.getExecuteDate());
		}
		catch (IException e)
		{
			throw new IRollbackException(mySessionCtx, e.getErrorCode());
		}
		
		infoReturn.setPreDrawInterest(ioInfo.getPreDrawInterest());
		infoReturn.setPayableInterest(ioInfo.getInterestPayment());
		
		//added by mzh_fu 2008/02/22 ����ڴ�й¶����
		io.closeConnection();
		
		log.debug("----------����ת��ɹ�-------------");
		return infoReturn;
	}

	/**
	 * �жϽ��׼�¼�Ƿ��޸Ĺ����ݴ淽����
	 * 
	 * 1�������� lID, ����ID�� long, ԭ����TouchTime��
	 * 
	 * 2������ֵ�� boolean, ������޸ģ�����true�����򣬷���false��
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransFixedContinueDAO.findByID,�õ����µĽ��ס�
	 * 
	 * ��2���жϴ����TouchTime�Ƿ����ѯ����һ�£������һ�£�����true�����򷵻�false?
	 * 
	 * @roseuid 3F73AF0302AC
	 */
	private boolean continueCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
			TransFixedContinueInfo info = dao.findByID(lID);
			// �ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate1 = info.getModifyDate();

			if (tsTouchTime == null
					|| lastTouchDate1.compareTo(tsTouchTime) != 0)
				return true;
			else
				return false;
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
	}

	/**
	 * ���ڣ�֪ͨ��֧ȡ/ת���ڽ��ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1�������� TransFixedDrawInfo ����ʵ����
	 * 
	 * 2������ֵ�� String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵���� ��1���жϲ���TransFixedDrawInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * �÷�����Sett_TransFixedWithDraw.checkIsDuplicate()�ж��Ƿ��ظ���
	 * 
	 * @roseuid 3F73AF06006B
	 */

	/**
	 * ��������ת�潻�ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1�������� FixedContinueInfo ����ʵ����
	 * 
	 * 2������ֵ�� String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixedContinueInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * �÷�����Sett_TransFixedContinueDAO.checkIsDuplicate()�ж��Ƿ��ظ���
	 * 
	 * @roseuid 3F73AF080349
	 */

	/**
	 * ���ڣ�֪ͨ���������׵��ݴ淽����
	 * 
	 * 1�������� FixdOpenInfo,���� ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����lID����-1�����÷���this.openCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ� ��
	 * ���÷���Sett_TransOpenFixedDepositDAO.update()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransOpenFixedDepositDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * 
	 * ��2�����lID��-1�����÷���Sett_TransOpenFixedDepositDAO.add()���潻�׼�¼��Ϣ��
	 * 
	 * ���÷���Sett_TransOpenFixedDepositDAO.updateStatus()���ļ�¼��״̬Ϊδ���档
	 * 
	 * @roseuid 3F73AE8A0136
	 * @throws RemoteException,IRollbackException
	 */
	public long changeTempSave(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			System.out.println("-------------��̨EJB:  ��ʼ�ݴ�!1111111111111");
			// �жϴ浥���Ƿ��ظ�
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			System.out.println("-------------��̨EJB:  ��ʼ�ݴ�!2222222222222222");
			// �����������Ϣ
			if (info.getID() <= 0) // ��ʾҳ�洫������ֵ�Ǵ����
			{
				// ����״̬Ϊδ����
				lReturn = -1;
				System.out
						.println("-------------��̨EJB:  ��ʼ�ݴ�!3333333333333333");
			} else // �޸��ݴ�
			{
				System.out
						.println("-------------��̨EJB:  ��ʼ�ݴ�!444444444444444444444443");

				// �ж�״̬�Ƿ�Ϸ�
				log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
				TransFixedChangeInfo newInfo = dao.findByID(info.getID());

				long lNewStatusID = newInfo.getDepositBillStatusID();
				System.out.println("ԭ���Ļ������ڴ浥��״̬:"
						+ info.getDepositBillStatusID());
				System.out.println("�ոմ����ݿ��в鵽�ĵĻ������ڴ浥��״̬:" + lNewStatusID);
				String errMsg = UtilOperation.checkStatus(info
						.getDepositBillStatusID(), lNewStatusID,
						SETTConstant.Actions.MODIFYSAVE);
				// ���޸Ĺ�
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				System.out.println("ԭ���Ļ������ڴ浥���޸�ʱ��:" + info.getModifyDate());
				System.out.println("�ոմ����ݿ��в鵽�ĵĻ������ڴ浥���޸�ʱ��:"
						+ newInfo.getModifyDate());
				// �ж��Ƿ񱻷Ƿ��޸Ĺ�

				log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
				if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
					log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// �����ݸ��� (�൱����Ӻ��޸Ĺ��ܾ��ڴ�)
					info
							.setDepositBillStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
					lReturn = dao.update(info);
				}
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ���������ױ���ǰ����Ƿ��ظ��ķ�����
	 * 
	 * 1�������� FixdOpenInfo ����ʵ����
	 * 
	 * 2������ֵ�� String , �ظ�ʱ����ʾ��Ϣ��������ظ�������null��
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixdOpenInfo,�еĽ���ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * �÷�����Sett_TransOpenFixedDeposit.checkIsDuplicate()�ж��Ƿ��ظ���
	 * 
	 * @roseuid 3F73AE9300E8
	 */

	/**
	 * ���ڣ�֪ͨ���������׵ı��淽����
	 * 
	 * 1�������� FixdOpenInfo, ����ʵ����
	 * 
	 * 2������ֵ�� long ,���ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1���жϲ���FixdOpenInfo,�еı�����ʵ����Ľ��ױ���Ƿ�Ϊ�ա� ����ǿգ�˵�����������棺
	 * 
	 * ���÷�����XXX.getTransactionNo()�õ�һ�����׺ţ�������д��FixdOpenInfo �� ����ǿգ�˵�����޸ı���:
	 * ���÷���this.openCheckIsTouched,�ж�Ҫ�ݴ�ļ�¼�Ƿ��޸Ĺ���
	 * ���÷�����this.openFindByID(),�õ�����ԭ���Ľ���ʵ����FixdOpenInfo��
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo�� ��2�����÷�����Sett_TransOpenFixedDepositDAO.add() ������Ϣ��
	 * ��3�����÷�����AccountDetail.saveOpenFixedDeposit()�����в�����
	 * ��4�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus() ���޸Ľ��׵�״̬Ϊ���档
	 * 
	 * @roseuid 3F73AE99038F
	 * 
	 * @throws RemoteException,IRollbackException
	 */
	public long changeSave(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		// ���ݷ��ʶ���
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		log.debug("---------��ʼchangeSave---------------");
		try {
			// ��õ�ǰ״̬
			// long lStatus = info.getDepositBillStatusID();

			// �жϴ浥���Ƿ��ظ�
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());

			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			System.out.println("ԭ���Ļ������ڴ浥��״̬:" + info.getDepositBillStatusID());
			System.out.println("�ոմ����ݿ��в鵽�ĵĻ������ڴ浥��״̬:" + lNewStatusID);
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.MODIFYSAVE);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			System.out.println("ԭ���Ļ������ڴ浥���޸�ʱ��:" + info.getModifyDate());
			System.out.println("�ոմ����ݿ��в鵽�ĵĻ������ڴ浥���޸�ʱ��:"
					+ newInfo.getModifyDate());
			// �ж��Ƿ񱻷Ƿ��޸Ĺ�

			log.debug("----------�ж��Ƿ񱻷Ƿ��޸Ĺ�-------------");
			if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
				log.debug("----------���Ƿ��޸Ĺ�,����ʧ��-------------");
				throw new IRollbackException(mySessionCtx, "Sett_E130");
			} else {
				
				// ��д����ָ��() �����浥 ����״̬��������
				
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��---------���������ڴ浥��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					  financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					  financeInfo.setDealUserID(info.getInputUserID());
					  financeInfo.setConfirmDate(info.getExecuteDate());
					  financeInfo.setFinishDate(info.getExecuteDate());
					  financeInfo.setTransNo(info.getTransNo());
					  financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					  //������
					  financeDao.updateStatusAndTransNo(null,financeInfo);
					 
				
					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // ������ָ���ID
					 financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					 //������ID
					 financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					 //��������
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.DEAL); // ������
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}

				// �����ݸ��� (�൱����Ӻ��޸Ĺ��ܾ��ڴ�)
				info
						.setDepositBillStatusID(SETTConstant.TransactionStatus.SAVE);
				lReturn = dao.update(info);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
		}
		log.debug("---------����changeSave---------------");
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ���������׵�ɾ��������
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ����
	 * 
	 * 2������ֵ�� long ,��ɾ���Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵���� ��1�����÷���this.opencheckIsTouched,�ж�Ҫɾ���ļ�¼�Ƿ��޸Ĺ���
	 * ��2���жϲ���TransFixedOpenInfo �еı�����ʵ�����״̬�� ������ݴ棺
	 * 
	 * ���÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч ���� ����Ǳ��棺
	 * 
	 * ���÷�����AccountDetail.deleteFixedDeposit()���ع�ԭ���Ĳ�����ע�������ԭ��
	 * ��ʵ��TransFixedOpenInfo
	 * 
	 * ���÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊɾ������Ч ����
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long changeDelete(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		// ����ʱʹ��
		long sessionID = -1;

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		log.debug("---------��ʼchangeDelete---------------");
		try {
			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.DELETE);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				System.out.println("�õ�����ָ���info:" + info.getInstructionNo());
				System.out.println("�õ�����ָ���newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());
				/* �����浥 ��������������״̬
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��   (�������ڴ浥ɾ��)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // ������ָ���ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //������ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //��������
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.REFUSE); // �ܾ�
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// ֻ��������״̬Ϊɾ�����ɣ����������ݿ�������(���費��Ҫ�����ؿ���һ�£���ʱ��΢��һ��)
				lReturn = dao.updateStatus(info.getID(), -1);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
		}
		log.debug("---------����changeDelete---------------");
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ�����׵ĸ��˷�����
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ���� 2������ֵ�� long ,�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣? ��Ҫ���˵ĵ����ѱ��޸ģ����飡����
	 * ��2�����÷�����AccountDetail.checkOpenFixedDeposit()�����и��˵Ĳ�����
	 * 
	 * ��3�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long changeCheck(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		try {
			System.out.println("---�������ڴ浥����--------��ʼ(EJB)");

			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.CHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				System.out.println("�õ�����ָ���info:" + info.getInstructionNo());
				System.out.println("�õ�����ָ���newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());

				/*  �����浥 ����������״̬
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��   (�������ڴ浥����)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // ������ָ���ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //������ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //��������
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.FINISH); // ����ָ�����
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// ����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				lReturn = dao.updateForCheck(info);

				info.setDepositBillNO("");
				info
						.setDepositBillStatusID(SETTConstant.TransactionStatus.CHECK);
				lReturn = dao.update(info);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
		}
		System.out.println("---�������ڴ浥����--------����(EJB)");
		return lReturn;
		}
	}

	/**
	 * ���ڣ�֪ͨ�����׵�ȡ�����˷�����
	 * 
	 * 1�������� TransFixedOpenInfo ����ʵ���� 2������ֵ�� long ,��ȡ�����˵Ķ��ڣ�֪ͨ�����׵ı�ʶ�����ʧ�ܣ�����-1
	 * 
	 * 3���߼�˵����
	 * 
	 * ��1�����÷���this.opencheckIsTouched,�ж�Ҫ���˵ļ�¼�Ƿ��޸Ĺ��������׳��쳣?
	 * ��Ҫȡ�����˵ĵ����ѱ��޸ģ����飡����
	 * 
	 * ��2�����÷�����AccountDetail.cancelCheckOpenFixedDeposit()������ȡ�����˵Ĳ��� ��
	 * 
	 * ��3�����÷�����Sett_TransOpenFixedDepositDAO.updateStatus���޸Ľ��׵�״̬Ϊ����?
	 * 
	 * @roseuid 3F73AEB30222
	 */
	public long changeCancelCheck(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		Sett_TransChangeFixedDepositDAO depositDao = new Sett_TransChangeFixedDepositDAO();
		try {
			System.out.println("---�������ڴ浥ȡ������--------��ʼ(EJB)");

			// �ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFixedChangeInfo newInfo = depositDao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// ���޸Ĺ�
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// ����Ƿ��޸Ĺ�
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // ���޸Ĺ�
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				System.out.println("�õ�����ָ���info:" + info.getInstructionNo());
				System.out.println("�õ�����ָ���newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());
				
				/* �����浥 ��������������״̬
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------������ָ��   (�������ڴ浥ȡ������)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // ������ָ���ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //������ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //��������
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.DEAL); // ����ָ�����
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// ȡ������
				info.setDepositBillNO("");
				lReturn = depositDao.updateForCheck(info);

				info
						.setDepositBillStatusID(SETTConstant.TransactionStatus.SAVE);
				lReturn = depositDao.update(info);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		} finally {
		}
		System.out.println("---�������ڴ浥ȡ������--------����(EJB)");
		return lReturn;
		}
	}

	/**
	 * ���ݱ�ʶ��ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� lID long , �������׵�ID
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByID()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedChangeInfo changeFindByID(long lID)
			throws IRollbackException, RemoteException {
		TransFixedChangeInfo info = new TransFixedChangeInfo();

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			info = dao.findByID(lID);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ���ݽ��׺Ų�ѯ���ڣ�֪ͨ�� ����������ϸ�ķ�����
	 * 
	 * 1�������� strTransNo , �������׺�
	 * 
	 * 2������ֵ�� TransFixedOpenInfo,���ڣ�֪ͨ����������ʵ����
	 * 
	 * 3���߼�˵���� ��1�����÷�����Sett_TransOpenFixedDepositDAO.findByID()
	 * �õ��������׵���ϸ��TransFixedOpenInfo��
	 * 
	 * @roseuid 3F73AEB8007A
	 */
	public TransFixedChangeInfo changeFindByTransNo(String strTransNo)
			throws IRollbackException, RemoteException {
		TransFixedChangeInfo info = new TransFixedChangeInfo();

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			info = dao.findByTransNo(strTransNo);

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * ����״̬��ѯ�ķ�����
	 * 
	 * 1�������� QueryByStatusConditionInfo, ��״̬��ѯ�Ĳ�ѯ����ʵ���ࡣ
	 * 
	 * 2������ֵ�� Collection ,����TransFixedOpenInfo��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ����Sett_TransOpenFixedDepositDAO.findByStatus()������
	 * 
	 * @roseuid 3F73AEBB0273
	 */
	public Collection changeFindByStatus(QueryByStatusConditionInfo info)
			throws IRollbackException, RemoteException {
		System.out.println("�������ڴ浥(changeFindByStatus):----------(��ʼEJB)");
		Collection coll = null;
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			coll = dao.findByStatus(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		System.out.println("�������ڴ浥(changeFindByStatus):----------(����EJB)");
		return coll;

	}

	/**
	 * ����ƥ��ķ�����
	 * 
	 * 1�������� TransFixedOpenInfo,���ڣ�֪ͨ���������ײ�ѯ����ʵ����
	 * 
	 * 2������ֵ�� Collection ,����TransFixedOpenInfo,��ѯ���ʵ����ļ�¼��
	 * 
	 * 3���߼�˵���� ���÷�����Sett_TransOpenFixedDepositDAO.match()
	 * 
	 * @roseuid 3F73AEC000C1
	 */
	public Collection changeMatch(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			coll = dao.match(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}

	/**
	 * ������������������
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransFixedOpenInfo info) throws RemoteException,
			IRollbackException {
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId = info.getID();
		try {
			TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
			depositInfo = this.openFindByID(info.getID());
			inutParameterInfo.setDataEntity(depositInfo);
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// ������Զ�����
				if (FSWorkflowManager.isAutoCheck()) {
					TransFixedOpenInfo depositInfo1 = new TransFixedOpenInfo();
					depositInfo1 = this.openFindByID(info.getID());
					// ����check����
					// TransFixedOpenInfo depositInfo = new
					// TransFixedOpenInfo();
					// depositInfo = this.openFindByID(info.getID());
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("����");
					depositInfo1.setCheckUserID(returnInfo.getUserID()); // ���������Ϊ������

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// ����openCheck����
					this.openCheck(depositInfo1);
				}
			}
			// ��������һ��,��Ϊ�����ܾ�,����״̬Ϊ�ѱ���
			else if (returnInfo.isRefuse()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
		}
	}

	/**
	 * ����������֧ȡ����
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransFixedDrawInfo info) throws RemoteException,
			IRollbackException {
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId = info.getID();
		try {
			TransFixedDrawInfo depositInfo = new TransFixedDrawInfo();
			depositInfo = this.drawFindByID(info.getID());
			inutParameterInfo.setDataEntity(depositInfo);
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// ������Զ�����
				if (FSWorkflowManager.isAutoCheck()) {
					TransFixedDrawInfo depositInfo1 = new TransFixedDrawInfo();
					depositInfo1 = this.drawFindByID(info.getID());
					// ����check����
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("����");
					depositInfo1.setCheckUserID(returnInfo.getUserID());

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// ����openCheck����
					this.drawCheck(depositInfo1);
				}
			} else if (returnInfo.isRefuse()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
		}
	}

	/**
	 * ����������ת�棩��
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransFixedContinueInfo info) throws RemoteException,
			IRollbackException {
		synchronized(lockObj){
		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId = info.getID();
		try {
			TransFixedContinueInfo depositInfo = new TransFixedContinueInfo();
			depositInfo = this.continueFindByID(info.getID());
			inutParameterInfo.setDataEntity(depositInfo);
			// �ύ����
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// ��������һ��,��Ϊ����ͨ��,����״̬Ϊ������
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// ������Զ�����
				if (FSWorkflowManager.isAutoCheck()) {
					// ����check����
					TransFixedContinueInfo depositInfo1 = new TransFixedContinueInfo();
					depositInfo1 = this.continueFindByID(info.getID());
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("����");
					depositInfo1.setCheckUserID(returnInfo.getUserID());

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// ����openCheck����
					this.continueCheck(depositInfo1);
				}
			} else if (returnInfo.isRefuse()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}
		}
		// modified by mzh_fu 2007/05/011
		/*
		 * catch (RemoteException e) { throw e; } catch (IRollbackException e) {
		 * throw e; }
		 */
		catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return depositId;
		}
	}

	/**
	 * ȡ���������������ڿ�����֪ͨ��������������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷��� ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransFixedOpenInfo openInfo)
			throws RemoteException, IRollbackException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransOpenFixedDepositDAO fixedDao = new Sett_TransOpenFixedDepositDAO();
		InutParameterInfo inutParameterInfo = openInfo.getInutParameterInfo();

		try {
			// ���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if (FSWorkflowManager.isAutoCheck()
					&& openInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				// ȡ������
				this.openCancelCheck(openInfo);
				// ȡ������
				lReturn = fixedDao.updateStatus(openInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& openInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				// ȡ������
				lReturn = fixedDao.updateStatus(openInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			// ��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ȡ����������������֧ȡ��֪֧ͨȡ����������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷��� ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransFixedDrawInfo drawInfo)
			throws RemoteException, IRollbackException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransFixedWithDrawDAO drawDao = new Sett_TransFixedWithDrawDAO();
		InutParameterInfo inutParameterInfo = drawInfo.getInutParameterInfo();

		try {
			// ���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if (FSWorkflowManager.isAutoCheck()
					&& drawInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				// ȡ������
				this.drawCancelCheck(drawInfo);
				// ȡ������
				lReturn = drawDao.updateStatus(drawInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& drawInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				// ȡ������
				lReturn = drawDao.updateStatus(drawInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			// ��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}

	/**
	 * ȡ�������������������棩��������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������ ȡ�����ˣ�����ejb��ȡ�����˷���
	 * ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * 
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransFixedContinueInfo continueInfo)
			throws RemoteException, IRollbackException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransFixedContinueDAO continueDao = new Sett_TransFixedContinueDAO();
		InutParameterInfo inutParameterInfo = continueInfo
				.getInutParameterInfo();

		try {
			// ���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if (FSWorkflowManager.isAutoCheck()
					&& continueInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				//ȡ������
				this.continueCancelCheck(continueInfo);
				//ȡ������
				lReturn = continueDao.updateStatus(continueInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& continueInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				//ȡ������
				lReturn = continueDao.updateStatus(continueInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			//��������¼���ڵĸý��׵�������¼״̬��Ϊ��Ч
			if (inutParameterInfo.getApprovalEntryID() > 0) {
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}
}
