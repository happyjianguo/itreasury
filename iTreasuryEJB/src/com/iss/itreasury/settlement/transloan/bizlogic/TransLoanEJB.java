/*
 * Created on 2003-8-7
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transloan.bizlogic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.SessionBean;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.setting.bizlogic.SettTaxRatePlanSettingBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.dao.DAOHelper;
import com.iss.itreasury.settlement.transloan.dao.Sett_SyndicationLoanInterestDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.GrantConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.GrantTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.LoanPayFormDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentConsignLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentInterestConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.RepaymentTrustLoanConditionInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.settlement.transloan.dataentity.SyndicationLoanInterestInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransLoanEJB implements SessionBean
{
	private final long SETT_TRUST_LOAN = 1;			//����
	private final long SETT_CONSIGN_LOAN = 2;		//ί��
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private static  Object lockObj = new Object();  //��̬
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws java.rmi.RemoteException
	{
	}
	/**
	 * getSessionContext method comment
	 * 
	 * @return javax.ejb.SessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 *            javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************����ſ�ʼ*************************** ****************************************************************
	 */
	/**
	 * ����ǰ�Ĳ���
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 */
	public long preSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		return 1;
	}
	/**
	 * ���д�����ݴ����
	 * 
	 * @param info
	 *            TransGrantLoanInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		long infoId = -1;
		try
		{
			log.debug("----------У��-------------");
			validateTransGrantLoanInfo(info);
			log.debug("----------������Ϣ-------------");
			infoId = this.partSave(info, dao);
			log.debug("----------�޸�״̬-------------");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("----------�ɹ�-------------");
		}
		//modified by mzh_fu 2007/05/011
/*		catch (RemoteException e){
			throw e;
		}
		catch (IRollbackException e){
			throw e;
		}*/
		catch (Exception e)
		{
			e.printStackTrace();
			//Modified by zwsun, 2007/8/8
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,e.getMessage());
			}
		}
	
		return infoId;
		}
	}
	/**
	 *  ����ű���
	 * 
	 * @param info
	 * @param isTrustLoan
	 * @return TransGrantLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lID = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			log.debug("====��ʼ����====");
			//log.debug("====У��====");
			//validateTransGrantLoanInfo(info);//���������
			String transNo = info.getTransNo();
			boolean bNewTransNo = false;
			log.debug("====���׺�====" + transNo);
			if (transNo == null || transNo.equals(""))
			{
				log.debug("====���ɽ��׺�====");
				bNewTransNo = true;
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				Log.print("----��ʼУ��ſ�֪ͨ��״̬----");
				if (!dao.checkPayForm(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.CHECK))
				{
					Log.print("----�ſ�֪ͨ���Ѿ���ʹ��----");
					throw new IRollbackException(mySessionCtx, "�ſ�֪ͨ���Ѿ���ʹ��");
				}
				log.debug("====�ı�ſ�֪ͨ��״̬Ϊ��ʹ��====");
				dao.updateLoanPayFormStatus(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.USED);
				log.debug("====�ı����====");
			}
			else
			{
				synchronized(lockObj){
				//У��״̬
				TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
				log.print("====info.getStatusID()====" + info.getStatusID());
				log.print("====newInfo.getStatusID()====" + newInfo.getStatusID());
				log.print("====errMsg====" + errMsg);
				//���޸Ĺ�
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//ɾ���˲���Ϣ
					log.debug("====ɾ���˲���Ϣ====");
					TransGrantLoanInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
					{
						log.debug("====�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��====");
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					}
					accountBookOperation.deleteGrantLoan(oldTransInfo);
				}
				}
			}
			log.debug("====������Ϣ====");
			lID = this.partSave(info, dao);
			//�����˲���Ϣ
			info.setID(lID);
			//�����˲���Ϣ
			log.debug("====�����˲���Ϣ====");
			accountBookOperation.saveGrantLoan(info);
			//����״̬�������棨δ���ˣ�
			log.debug("====����״̬�������棨δ����====");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
		
			//Added by zwsun , 2007-06-20, ����������
			if(info.getInutParameterInfo()!=null)
			{
			log.debug("------�ύ����--------");
			//���÷��صĵ�ַ����(����idֻ���ڽ��ױ���֮�����,tempInfo.getUrl()�õ���urlû�о���Ľ���id)
			InutParameterInfo tempInfo = info.getInutParameterInfo();
			tempInfo.setUrl(tempInfo.getUrl()+lID);
			tempInfo.setTransID(transNo);//���ﱣ����ǽ��ױ��
			tempInfo.setDataEntity(info);
			
			//�ύ����
			FSWorkflowManager.initApproval(info.getInutParameterInfo());
			//����״̬��������
			dao.updateStatus(info.getID(),SETTConstant.TransactionStatus.APPROVALING);
			log.debug("------�ύ�����ɹ�--------");
			
			}
		}
		//modified by mzh_fu 2007/05/011
		/*		
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		*/
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			
		}
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		return lID;
		}
	}
	/**
	 * ����Ƿ����
	 * 
	 * @param info
	 * @param dao
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	private boolean isTouch(TransGrantLoanInfo info, Sett_TransGrantLoanDAO dao) throws RemoteException, IRollbackException
	{
		try
		{
			log.debug("======�Ƿ��޸ģ�======");
			Timestamp lastTouchDate = dao.findByID(info.getID()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("���ݿ⵱ǰʱ�䣺" + lastTouchDate.toString());
			Log.print("����������޸�ʱ�䣺" + currentTouchDate.toString());
			if (currentTouchDate == null || lastTouchDate == null || !lastTouchDate.toString().equals(currentTouchDate.toString()))
			{
				log.debug("======���޸�======");
				log.debug("======currentTouchDate======" + currentTouchDate);
				log.debug("======lastTouchDate======" + lastTouchDate);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}
	/**
	 * ����TransGrantLoanInfo
	 * 
	 * @param info
	 * @param dao
	 * @return TransGrantLoanInfo ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransGrantLoanInfo info, Sett_TransGrantLoanDAO dao) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try
		{
			if (info.getID() > 0)
			{
				log.debug("----------�޸���Ϣ-------------");
				dao.update(info);
			}
			else
			{
				log.debug("----------������Ϣ-------------");
				dao.add(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/**
	 * У��������Ϣ
	 * 
	 * @param info
	 *            TransGrantLoanInfo
	 * @param isNew
	 * @throws IRollbackException
	 */
	private void validateTransGrantLoanInfo(TransGrantLoanInfo info) throws IRollbackException, RemoteException
	{
		log.debug("----------У���С�����������-------------");
		if (info.getID() > 0)
		{
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
			//���޸Ĺ�
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
		}
	}
	/**
	 * ����id�������д������Ϣ
	 * 
	 * @param id
	 *            ���д������Ϣid
	 * @return TrustLoanInfoAssembler
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByID(long id) throws RemoteException, IRollbackException
	{
		TransGrantLoanInfo info = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			info = dao.findByID(id);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	
	/*
	 * ���ݷſ�֪ͨ��ID�������д������Ϣ
	 * 
	 * @param id ���д������Ϣ�ķſ�֪ͨ��ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo FindGrantDetailByLoanNoteID(long lLoanNoteID) throws RemoteException, IRollbackException
	{
		TransGrantLoanInfo info = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			Log.print("���ݷſ�֪ͨ��ID�������д������Ϣ1:" + lLoanNoteID);
			info = dao.findByLoanNoteID(lLoanNoteID);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	
	/**
	 * ���Ӳ�ѯ���д������Ϣ����
	 * 
	 * @param info
	 *            TrustLoanInfo
	 * @param orderType
	 * @param isDesc
	 * @return ���д������Ϣ����
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByCondition(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			collection = dao.findByCondition(info);
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return collection;
	}
	/**
	 * ƥ���ѯ���д������Ϣ
	 * 
	 * @param info
	 * @return ���д������Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		TransGrantLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			//Added by zwsun , 2007/6/28, ƥ��������
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));
					
			helper.setInfo(info);
			helper.setStrict(true);
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BREAK_INVESTADDITIONALRECORDINGGRANT)
			{
			helper.setNotMatch("getDepositAccountID");
			helper.setNotMatch("getBankID");
			}
			helper.setNotMatch("getInterestStart");
			helper.setNotMatch("getCashFlowID");
			helper.setNotMatch("getPaySuretyFeeAccountID");
			helper.setNotMatch("getReceiveSuretyFeeAccountID");
			helper.setNotMatch("getPayTypeID");
			//helper.setNotMatch("getInputUserID");
			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransGrantLoanInfo) iterator.next();
				break;
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
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * ƥ���ѯ���д������Ϣ
	 * 
	 * @param info
	 * @return ���д������Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransGrantLoanInfo match(GrantConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		TransGrantLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("����match..........");
			//Added by zwsun , 2007/6/28, ƥ��������
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
					info.getCurrencyID(),
					Constant.ModuleType.SETTLEMENT,
					info.getTransactionTypeID(),
					-1)));
			
			helper.setInfo(info);
			helper.setStrict(true);
			helper.setNotMatch("getPayTypeID"); // �ſʽ����Ϊ��ѯ����
			if (info.getPayInterestAccountID() < -1)
			{ //С��Ĭ��ֵ,����Ϊƥ������
				helper.setNotMatch("getPayInterestAccountID");
			}
			if (info.getReceiveInterestAccountID() < -1)
			{ //С��Ĭ��ֵ,����Ϊƥ������
				helper.setNotMatch("getReceiveInterestAccountID");
			}
			if (info.getPayCommisionAccountID() < -1)
			{ //С��Ĭ��ֵ,����Ϊƥ������
				helper.setNotMatch("getPayCommisionAccountID");
			}
			
			//if (info.getInterestTaxRate() < -1)
			//{ //С��Ĭ��ֵ,����Ϊƥ������
				helper.setNotMatch("getInterestTaxRate");
			//}
			
			//ƥ������֮ ��Ϣ˰���� �ĳ� ��Ϣ˰���ʱ��
			if(info.getInterestTaxPlanId() < -1)
			{
				helper.setNotMatch("getInterestTaxPlanId");
			}

			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				Log.print("�õ����");
				returninfo = (TransGrantLoanInfo) iterator.next();
				break;
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
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * �õ�����������Ϣ
	 * 
	 * @param info
	 *            LoanConditionInfo
	 * @return LoanConditionInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public LoanPayFormDetailInfo findLoanPayFormDetailByCondition(LoanPayFormDetailInfo info) throws RemoteException, IRollbackException
	{
		LoanPayFormDetailInfo returnInfo = null;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			returnInfo = dao.findPayFormDetailByCondition(info);
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
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returnInfo;
	}
	/**
	 * ɾ�������¼
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransGrantLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long id = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		
		try
		{
				//�Կͻ�����
				sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
	
				TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
				//���޸Ĺ�
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E131");
				}
				else
				{
					//ɾ�����׼�¼
					accountBookOperation.deleteGrantLoan(newInfo);
					//����ID
					if (info.getStatusID()== SETTConstant.TransactionStatus.SAVE){		//����Ǳ��棬���ķſ�֪ͨ��״̬,������ݴ治��
						if (!dao.checkPayForm(info.getLoanNoteID(),LOANConstant.LoanPayNoticeStatus.USED)){
							throw new IRollbackException(mySessionCtx,"�ſ�֪ͨ���Ѿ����޸�");
						}
						dao.updateLoanPayFormStatus(info.getLoanNoteID(), LOANConstant.LoanPayNoticeStatus.CHECK);
					}
	
					return dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		}
	}
	/**
	 * ���˻���ȡ������
	 * 
	 * @param info
	 * @param checkOrCancel
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransGrantLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		TransGrantLoanInfo transInfo = new TransGrantLoanInfo();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//�Կͻ�����
		try
		{
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		if (checkOrCancel)
		{
			//����
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
			//���޸Ĺ�
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E133");
			}
		}
		else
		{
			//ȡ������
			TransGrantLoanInfo newInfo = this.FindGrantDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
			//���޸Ĺ�
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
		}
		try
		{
			dao.update(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try{
			transInfo = dao.findByID(info.getID());		//�������ݿ��е�������¼
			transInfo.setCheckAbstract(info.getCheckAbstract());	//���˱�ע
			transInfo.setCheckUserID(info.getCheckUserID());		//������
			//*  modify by zcwang 2008-10-06 ���ſ�����ڴ������ȡ��ʽ�Ѿ��޸ģ�ԭ���߼�ע��
			if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANGRANT)
			{
				//��������ѯ
				ArrayList bankLoancol = null;
				BankLoanQuery bankLoanQuery =new BankLoanQuery();
				bankLoancol =(ArrayList)bankLoanQuery.findByFormID(transInfo.getLoanNoteID());
				transInfo.setSyndicationLoan(bankLoancol);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
		if (checkOrCancel)
		{
			//���˽��׼�¼
			accountBookOperation.checkGrantLoan(transInfo);
			try
			{
				dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
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
			
			try
			{
				if(bIsValid && bCreateInstruction) {
					Log.print("*******************��ʼ������Ӫ����Ż�ί�д���ŵȸ���ָ�������**************************");
					try {
						log.debug("------��ʼ��Ӫ����Ż�ί�д���ŵȸ���ָ������--------");
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
						
						log.debug("------������Ӫ����Ż�ί�д���ŵȸ���ָ��ɹ�--------");
						
					} catch (Throwable e) {
						log.error("������Ӫ����Ż�ί�д���ŵȸ���ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "������Ӫ����Ż�ί�д���ŵȸ���ָ��ʧ�ܣ�"+e.getMessage());
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
		else
		{
			//ȡ�����˽��׼�¼
			accountBookOperation.cancelCheckGrantLoan(transInfo);
			//����״̬����δ����
			try
			{
				//Added by zwsun , 2007/8/4, �ж��Ƿ����������
				InutParameterInfo pInfo=new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setOfficeID(info.getOfficeID());
				pInfo.setCurrencyID(info.getCurrencyID());
				pInfo.setTransTypeID(info.getTransactionTypeID());
				pInfo.setActionID(-1);
				if(FSWorkflowManager.isNeedApproval(pInfo)){
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED, info.getCheckAbstract(), info.getCheckUserID());					
				}else{
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());					
				}								
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		//����
		try
		{
			if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
				utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************����Ž���*************************** ****************************************************************
	 */
	/***************************************************************************************************************************************************************************************************
	 * ******************************�����ջؿ�ʼ*************************** ****************************************************************
	 */
	/**
	 * ����ǰ�Ĳ���
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 */
	public long preSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		return SETTConstant.PreSaveResult.NORMAL;
	}
	/**
	 * ���д����ջ��ݴ����
	 * 
	 * @param info
	 *            TransRepaymentLoanInfo
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long tempSave(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		long infoId = -1;
		try
		{
			log.debug("----------У��-------------");
			validateTransRepaymentLoanInfo(info);
			log.debug("----------������Ϣ-------------");
			infoId = this.partSave(info, dao);
			log.debug("----------�޸�״̬-------------");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("----------�ɹ�-------------");
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return infoId;
		}
	}
	/**
	 *  �����ջر���
	 * 
	 * @param info
	 * @return TransRepaymentLoanInfo id
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lID = -1;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			log.debug("====��ʼ����====");
			log.debug("====У��====");
			validateTransRepaymentLoanInfo(info);
			String transNo = "";
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("===��ǰ����Ϊ��ʴ����ջ�,ȡ����ʱ���׺�===");
				transNo = info.getTempTransNO();
			}
			else {
				Log.print("===��ǰ���ײ��Ƕ�ʴ����ջ�,ȡ�ý��׺�===");
				transNo = info.getTransNo();
			}
			/**
			 * 
			 */
			boolean bNewTransNo = false;
			log.debug("====���׺�====" + transNo);
			if (transNo == null || transNo.equals(""))
			{
				log.debug("====���ɽ��׺�====");
				bNewTransNo = true;
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
					Log.print("===�½���,��������ʱ���׺�===");
					info.setTempTransNO(transNo);
				}
				else{
					Log.print("===�½���,�����½��׺�===");
					info.setTransNo(transNo);
				}
				/**
				 * ��ʼ�ı��⻹֪ͨ������ǰ����֪ͨ����״̬
				 */
				if (info.getPreFormID()>0)
				{
					Log.print("===ʹ������ǰ����֪ͨ��===");
					Log.print("===��ʼ�����ǰ����֪ͨ��״̬===");
					if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
						throw new IRollbackException(mySessionCtx,"��ǰ����֪ͨ���Ѿ���ʹ��");
					}
					Log.print("===��ʼ�ı���ǰ����֪ͨ��״̬Ϊ��ʹ��===");
//					�����жϣ�����黹�����ڻ���֪ͨ�����ı�֪ͨ��״̬Ϊ��ʹ�ã����򲻸ı���״̬�������¼ӣ�һ������
					//֪ͨ�����Զ�λ��塣
					double  AllAmount=  DataFormat.formatDouble(dao.getTotalMountByPReformid(info.getPreFormID(),info.getID()));//��ø�֪ͨ�����л�����
		            double  noticeAmout = DataFormat.formatDouble(dao.getAMountByPReformid (info.getPreFormID()));  //��û���֪ͨ�����         
				    System.out.println("-----AllAmount-----===="+AllAmount+"=====noticeAmout====="+noticeAmout);
					AllAmount+=info.getAmount();
					if(DataFormat.formatDouble(AllAmount)==DataFormat.formatDouble(noticeAmout)){
					dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
					}
				}
				//����޸ĵ�ʱ����ʹ����ǰ����֪ͨ������Ҫ������֪ͨ����״̬�ı�.
				TransRepaymentLoanInfo oldInfo = dao.findByID(info.getID());
				if(oldInfo!=null&&oldInfo.getPreFormID()>0&&info.getPreFormID()<0){
					dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
				}
				if (info.getFreeFormID()>0)
				{
					Log.print("===ʹ�����⻹֪ͨ��===");
					Log.print("===��ʼ����⻹֪ͨ����״̬===");
					if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK)){
						throw new IRollbackException(mySessionCtx,"�⻹֪ͨ���Ѿ���ʹ��");
					}
					Log.print("===��ʼ�ı��⻹֪ͨ����״̬Ϊ��ʹ��");
					dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED);
				}				
			}
			else
			{
				synchronized(lockObj){
					TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
	
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
	
					if (this.isTouch(info, dao))
					{
						log.debug("====���Ƿ��޸Ĺ�====");
						throw new IRollbackException(mySessionCtx, "@TBD:���Ƿ��޸Ĺ�");
					}
	
					if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						if (newInfo.getPreFormID() <= 0 && info.getPreFormID() > 0)
						{
							Log.print("===ʹ������ǰ����֪ͨ��===");
							Log.print("===��ʼ�����ǰ����֪ͨ��״̬===");
							if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"��ǰ����֪ͨ���Ѿ���ʹ��");
							}
							Log.print("===��ʼ�ı���ǰ����֪ͨ��״̬Ϊ��ʹ��===");
							dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
						}
						if (newInfo.getFreeFormID() <= 0 && info.getFreeFormID() > 0)
						{
							Log.print("===ʹ�����⻹֪ͨ��===");
							Log.print("===��ʼ����⻹֪ͨ����״̬===");
							if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"�⻹֪ͨ���Ѿ���ʹ��");
							}
							Log.print("===��ʼ�ı��⻹֪ͨ����״̬Ϊ��ʹ��");
							dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED);
						}
					}
					/**
					 * add by kevin(������)2012-06-25
					 * �������֪ͨ���ɣ���-��-��ʱ֪ͨ��״̬û�б仯������
					 * ��ʼ�ı��⻹֪ͨ������ǰ����֪ͨ����״̬
					 */
					if (info.getPreFormID()>0)
					{
						Log.print("===ʹ������ǰ����֪ͨ��===");
						if(newInfo.getPreFormID()<0){
							Log.print("===��ʼ�����ǰ����֪ͨ��״̬===");
							if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK)){
								throw new IRollbackException(mySessionCtx,"��ǰ����֪ͨ���Ѿ���ʹ��");
							}
						}
						
						Log.print("===��ʼ�ı���ǰ����֪ͨ��״̬Ϊ��ʹ��===");
						//�����жϣ�����黹�����ڻ���֪ͨ�����ı�֪ͨ��״̬Ϊ��ʹ�ã����򲻸ı���״̬�������¼ӣ�һ������
						//֪ͨ�����Զ�λ��塣
						double  AllAmount=  DataFormat.formatDouble(dao.getTotalMountByPReformid(info.getPreFormID(),info.getID()));//��ø�֪ͨ�����л�����
			            double  noticeAmout = DataFormat.formatDouble(dao.getAMountByPReformid (info.getPreFormID()));  //��û���֪ͨ�����         
					    System.out.println("-----AllAmount-----===="+AllAmount+"=====noticeAmout====="+noticeAmout);
						AllAmount+=info.getAmount();
						if(DataFormat.formatDouble(AllAmount)==DataFormat.formatDouble(noticeAmout)){
							dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED);
						}else{
							if(newInfo.getPreFormID()>0){
								dao.updatePreFormStatus(newInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
							}
						}
					}
					//����޸ĵ�ʱ����ʹ����ǰ����֪ͨ������Ҫ������֪ͨ����״̬�ı�.
					if(newInfo!=null&&newInfo.getPreFormID()>0&&info.getPreFormID()<0){
						dao.updatePreFormStatus(newInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
					}			
					
					//ɾ���˲���Ϣ
					log.debug("====ɾ���˲���Ϣ====");
					TransRepaymentLoanInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
					{
						log.debug("====�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��====");
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					}
					/**
					 * ɾ���˲���Ϣ��Ҫ�жϽ�������.
					 */
					if (oldTransInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
					{
						accountBookOperation.deleteMultiLoanReceive(oldTransInfo);
					}
					else 
					{
						accountBookOperation.deleteRepaymentLoan(oldTransInfo);
					}
				}
			}
			log.debug("====������Ϣ====");
			lID = this.partSave(info, dao);
			//�����˲���Ϣ
			info.setID(lID);
			//�����˲���Ϣ
			log.debug("====�����˲���Ϣ====");
			/**
			 * �����˲���Ϣ,��Ҫ�жϽ�������
			 */
			if (info.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				accountBookOperation.saveMultiLoanReceive(info);
			}
			else {
				accountBookOperation.saveRepaymentLoan(info);
			}
			//����״̬�������棨δ���ˣ�
			log.debug("====����״̬�������棨δ����====");
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		return lID;
		}
	}
	/**
	 * ����Ƿ����
	 * 
	 * @param info
	 * @param dao
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	private boolean isTouch(TransRepaymentLoanInfo info, Sett_TransRepaymentLoanDAO dao) throws RemoteException, IRollbackException
	{
		try
		{
			log.debug("======�Ƿ��޸ģ�======");
			Timestamp lastTouchDate = dao.findByID(info.getID()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("���ݿ�ʱ�䣺" + lastTouchDate.toString());
			Log.print("��ǰ��¼ʱ�䣺" + currentTouchDate.toString());
			if (currentTouchDate == null || lastTouchDate == null || !lastTouchDate.toString().equals(currentTouchDate.toString()))
			{
				log.debug("======���޸�======");
				log.debug("======currentTouchDate======" + currentTouchDate);
				log.debug("======lastTouchDate======" + lastTouchDate);
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
	}
	/**
	 * �������Ŵ�����Ϣ��Ϣ
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private void saveSyndicationLoan(long lReceiveLoanID,ArrayList list) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			if(list!=null && list.size()>0)
			{
				Iterator it =null;
				it=list.iterator();
				deletePhysicsSyndicationLoan(lReceiveLoanID);
				long id = dao.getMaxID();
				while(it!=null && it.hasNext())
				{					
					SyndicationLoanInterestInfo info =(SyndicationLoanInterestInfo)it.next();
//					if(info.getSyndicationLoanReceiveID()>0)
//					{
//						 dao.update(info);
//					}
//					else
//					{		
						
						info.setID(id);			
						info.setSyndicationLoanReceiveID(lReceiveLoanID);
						info.setStatusID(SETTConstant.TransactionStatus.SAVE);
						dao.add(info);
						id++;
					//}
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * ����ID�������Ŵ�����Ϣ����
	 * @param lReceiveLoanID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private ArrayList findSyndicationByReceiveLoanID(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		ArrayList list = null;
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			list = (ArrayList)dao.findBySyndicationLoanReceiveID(lReceiveLoanID);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
	    return list;
	}
	/**
	 * ɾ�����Ŵ�����Ϣ����
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	private void deleteSyndicationLoan(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			dao.updateStatus(lReceiveLoanID,SETTConstant.TransactionStatus.DELETED);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * @param lReceiveLoanID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private void deletePhysicsSyndicationLoan(long lReceiveLoanID) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try 
		{
			Sett_SyndicationLoanInterestDAO dao = new Sett_SyndicationLoanInterestDAO();
			dao.deletePhysicsSyndicationLoan(lReceiveLoanID);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		}
		
	}
	/**
	 * ����TransRepaymentLoanInfo
	 * 
	 * @param info
	 * @param dao
	 * @return TransRepaymentLoanInfo ID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransRepaymentLoanInfo info, Sett_TransRepaymentLoanDAO dao) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try
		{
			if (info.getID() > 0)
			{
				log.debug("----------�޸���Ϣ-------------");
				dao.update(info);
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{				
					log.info("------------�������Ŵ�����Ϣ----------");
					saveSyndicationLoan(info.getID(),info.getSyndicationLoanInterest());
				}
			}
			else
			{
				log.debug("----------������Ϣ-------------");
				long id = dao.add(info);
				
				if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{				
					log.info("------------�������Ŵ�����Ϣ----------");
					saveSyndicationLoan(id,info.getSyndicationLoanInterest());
				}
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	/**
	 * У������ջ���Ϣ
	 * 
	 * @param info
	 *            TransRepaymentLoanInfo
	 * @throws IRollbackException
	 */
	private void validateTransRepaymentLoanInfo(TransRepaymentLoanInfo info) throws IRollbackException
	{
		log.debug("----------У���С�����������-------------");
		if (info.getID() > 0)
		{
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), info.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
			//���޸Ĺ�
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
		}
	}
	/**
	 * ����id�������д����ջ���Ϣ
	 * 
	 * @param id
	 *            ���д����ջ���Ϣid
	 * @return TransRepaymentLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo FindRepaymentDetailByID(long id) throws RemoteException, IRollbackException
	{
		Log.print("����FindRepaymentDetailByID............");
		TransRepaymentLoanInfo info = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			Log.print("ID:" + id);
			info = dao.findByID(id);
			log.info("----------�ж��Ƿ����Ŵ���----------------");
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				list = findSyndicationByReceiveLoanID(id);
				info.setSyndicationLoanInterest(list);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	/**
	 * ���Ӳ�ѯ���д����ջ���Ϣ����
	 * 
	 * @param info
	 *            TrustLoanInfo
	 * @param orderType
	 * @param isDesc
	 * @return ���д������Ϣ����
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findByCondition(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		Collection collection = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			collection = dao.findByCondition(info);
		}
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return collection;
	}
	/**
	 * ƥ���ѯ���д����ջ���Ϣ
	 * 
	 * @param info
	 * @return ���д����ջ���Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentTrustLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			helper.setInfo(info);
			helper.setStrict(true);
			if (info.getPreFormID() < -1)
			{
				helper.setNotMatch("getPreFormID");
			}
			if (info.getPayInterestAccountID() < -1)
			{
				helper.setNotMatch("getPayInterestAccountID");
			}
			if (info.getInterestBankID() < -1)
			{
				helper.setNotMatch("getInterestBankID");
			}
			if (info.getPaySuertyFeeAccountID() < -1)
			{
				helper.setNotMatch("getPaySuertyFeeAccountID");
			}
			if (info.getSuertyFeeBankID() < -1)
			{
				helper.setNotMatch("getSuertyFeeBankID");
			}
			if (info.getReceiveSuertyFeeAccountID() < -1)
			{
				helper.setNotMatch("getReceiveSuertyFeeAccountID");
			}

			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				log.info("----------�ж��Ƿ����Ŵ���----------------");
				if(returninfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
				{
					ArrayList list = null;
					list = findSyndicationByReceiveLoanID(returninfo.getID());
					returninfo.setSyndicationLoanInterest(list);
				}
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * ƥ���ѯ���д����ջ���Ϣ
	 * 
	 * @param info
	 * @return ���д����ջ���Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentConsignLoanConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("����match..........");
			helper.setInfo(info);
			helper.setHelpType(DAOHelper.HELPER_SELECT);
			helper.setStrict(true);
			helper.setNotEquals("getInputUserID");
			if (info.getPreFormID() < -1)
			{ //�����ǰ����֪ͨ������Ϊƥ������
				helper.setNotMatch("getPreFormID");
			}
			if (info.getFreeFormID() < -1)
			{ //����⻹֪ͨ������Ϊƥ������
				helper.setNotMatch("getFreeFormID");
			}
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * ƥ���ѯ��Ϣ�ջ���Ϣ
	 * 
	 * @param info
	 * @return ��Ϣ�ջ���Ϣ
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo match(RepaymentInterestConditionInfo info) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo returninfo = null;
		DAOHelper helper = new DAOHelper();
		try
		{
			Log.print("����match..........");
			helper.setInfo(info);
			helper.setStrict(true);
			helper.setNotEquals("getInputUserID");
			for (Iterator iterator = dao.match(helper).iterator(); iterator.hasNext();)
			{
				returninfo = (TransRepaymentLoanInfo) iterator.next();
				break;
			}
		}
/*		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}*/
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return returninfo;
	}
	/**
	 * ͨ�����׺Ų��ҽ���ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long grantGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}
	/**
	 * ͨ�����׺Ų��ҽ���ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentGetIDByTransNo(String strTransNo) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			return dao.getIDByTransNo(strTransNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}
	/**
	 * ͨ�����׺Ų��ҽ���ID
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentGetIDByTransNoAndSerialNo(String strTransNo,long lSerialNo) throws RemoteException, IRollbackException
	{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try
		{
			return dao.getIDByTransNoAndSerialNo(strTransNo,lSerialNo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
	}

	/**
	 * �õ�����������Ϣ
	 * 
	 * @param info
	 *            SubLoanAccountDetailInfo
	 * @return SubLoanAccountDetailInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{

		
		SubLoanAccountDetailInfo returnInfo = null;
		SubAccountLoanInfo subAccInfo = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		InterestOperation io = null;
		try
		{
			returnInfo = dao.findSubLoanAccountDetailByCondition(info);
			Log.print("----�Ѿ��õ����˻���ϸ��Ϣ----");
			Log.print("��ǰ�˻��������:"+(returnInfo.isAccountClear()?"�Ѿ�����":"û�н���"));
			
			if (returnInfo.isAccountClear()){
				Log.print("ִ����:"+returnInfo.getExecute());
				Log.print("���´�:"+returnInfo.getOfficeID());
				Log.print("���˻�:"+returnInfo.getLoanAccountID());
				Log.print("���˻�:"+returnInfo.getSubAccountID());
				Log.print("��Ϣ��:"+returnInfo.getInterestStart());

				Log.print("----��ʼ��Ϣ����----");
				sett_TransAccountDetailDAO tadiDAO = new sett_TransAccountDetailDAO();
				Collection col = tadiDAO.findByIsBackward(returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
							returnInfo.getOfficeID(),
								returnInfo.getCurrencyID(),
									returnInfo.getExecute());
				Iterator iter = col.iterator();
				while(iter.hasNext()){
					TransAccountDetailInfo tadInfo = (TransAccountDetailInfo)iter.next();
					InterestBatch interestBatch = new InterestBatch();				
					
					//modified by mzh_fu 2007/12/14
/*					interestBatch.accountInterestCalculationBackward(
							returnInfo.getLoanAccountID(),
								returnInfo.getSubAccountID(),
									returnInfo.getInterestStart(),
										returnInfo.getAmount(),
											returnInfo.getOfficeID(),
												returnInfo.getCurrencyID(),
													returnInfo.getExecute(),
														SETTConstant.BooleanValue.ISFALSE);*/
					
					//added by mzh_fu 2007/12/14 begin
					long tmpTransAccountID = -1; // �����˻�
					long tmpSubAccountID = -1; // �������˻�
					Timestamp interestStartDate = null; // ��Ϣ��
					double backAmount = 0.0;

					tmpTransAccountID = tadInfo.getTransAccountID();
					tmpSubAccountID = tadInfo.getTransSubAccountID();
					interestStartDate = tadInfo.getDtInterestStart();
					backAmount = tadInfo.getAmount();

					interestBatch.accountInterestCalculationBackward(
							tmpTransAccountID, tmpSubAccountID,
							interestStartDate, backAmount, info.getOfficeID(),
							info.getCurrencyID(), info.getExecute(),
							SETTConstant.BooleanValue.ISFALSE);
					//added by mzh_fu 2007/12/14 end
					
				}
				Log.print("----��Ϣ�������----");
				
				//add by bingliu 20120307 �����������
				/*��Ӫ���ί�д����ͬ�����ʵ���������ʷ��������Ϣ���ػ����ٴν�Ϣ��ϵͳ�������Ϣû�м�ȥ�ϴ��Ѿ��������Ϣ��
				 *��Ϣ֮ǰû�е�����Ϣ���ᵼ���������⣺1.�����Ϣ���ԣ�2.�ػ�ʱ�ᵹ����Ϣ������֮�����Ϣ������ȷ��û�м�ȥ�ϴν����Ϣ��
				 *����취���ڽ�Ϣ֮ǰ�ļ��㷽���м������ʵ��㷽����
				 */
				log.info("-------------�ж��Ƿ��е������ʵ���---------");
				Collection coll2 = null;
				coll2 = sett_SubAccountDAO.findAccountLoanBankInterestAdjust(info.getOfficeID(), info.getCurrencyID(), 
						returnInfo.getExecute(),info.getSubAccountID());
				Iterator itResult2 = null;
				if (coll2 != null && coll2.size() > 0)
				{
					InterestBatch ib = new InterestBatch();
					itResult2 = coll2.iterator();
					if (itResult2 != null && itResult2.hasNext())
					{
						while (itResult2.hasNext())
						{
							BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
							log.info("-------------��ʼ�������ʵ���---------");
							long flag = ib.accountInterestCalculationBackward(backinfo.getAccountID(),
									backinfo.getSubAccountID(), backinfo.getBackDate(), 0, 
									info.getOfficeID(), info.getCurrencyID(),
									Env.getSystemDate(info.getOfficeID(), info
											.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
							long lUpdateReturn = sett_SubAccountDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
							   log.debug("�������ʵ�����ϸ��Ϣ���ı�־lUpdateReturn = "+lUpdateReturn);
							if (flag < 0||lUpdateReturn < 0)
							{
								throw new IException("�������ʵ���ʧ��");
							}
							log.info("-------------�������ʵ������---------");
						}
					}
				}
				log.info("-------------�ж��Ƿ��е������ʵ������---------");
				
				Log.print("----�жϴ�������----");
				long lContractType = returnInfo.getContractType(); //��ͬ����
				long lLoanType = -1;								//��������
				if (lContractType==LOANConstant.LoanType.YT
					||lContractType==LOANConstant.LoanType.ZGXE
					||lContractType==LOANConstant.LoanType.ZY){//���������
					lLoanType = SETT_TRUST_LOAN;
					Log.print("��ǰ����Ϊ��Ӫ�����ջ�");
				}
				else if(lContractType==LOANConstant.LoanType.WT ){//�����ί��
					lLoanType = SETT_CONSIGN_LOAN;
					Log.print("��ǰ����Ϊί�д����ջ�");
				}
				
				Log.print("----������Ϣ������----");
				io = new InterestOperation();
				
				/**
				 * �õ���ǰϵͳʱ��
				 */
				Timestamp tsSys = Env.getSystemDate(returnInfo.getOfficeID(),returnInfo.getCurrencyID());
				
				//������Ϣ
				Log.print("----��ʼ������Ϣ----");
				
				Log.print("���´�:"+returnInfo.getOfficeID());
				Log.print("����:"+returnInfo.getCurrencyID());
				Log.print("���˻�:"+returnInfo.getLoanAccountID());
				Log.print("���˻�:"+returnInfo.getSubAccountID());
				Log.print("��Ϣ��:"+returnInfo.getInterestStart());
				Log.print("ִ����:"+returnInfo.getExecute());
				Log.print("ϵͳʱ��:"+tsSys);
				
				LoanAccountInterestInfo interest = io.GetLoanAccountInterest(
						returnInfo.getOfficeID(),
						returnInfo.getCurrencyID(),
						returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
						returnInfo.getInterestStart(),
						tsSys);
				returnInfo.setInterest(UtilOperation.Arith.round(interest.getInterest(), 2));
				
				//��һ����Ϣ�մ����˻��л�ȡ Boxu Update 2008��3��25��
				subAccInfo = sett_SubAccountDAO.findByID(returnInfo.getSubAccountID()).getSubAccountLoanInfo();
				returnInfo.setLatestClearInterest(subAccInfo.getClearInterestDate());
				//returnInfo.setLatestClearInterest(interest.getSDate());  //�ϴν�Ϣ��
				
				double dInterestRate = UtilOperation.Arith.round(UtilOperation.Arith.div(interest.getRate(),100),6);
				returnInfo.setLoanRepaymentRate(dInterestRate);				//����
				//������Ϣ
				Log.print("----��ʼ���������Ϣ----");
							
				AccountOperation ao = new AccountOperation();
				long lAccountType = ao.getAccountTypeBySubAccountID(returnInfo.getSubAccountID());
				SubAccountPredrawInterestInfo interestPredraw = io.getLoanAccountPredrawInterest(
						returnInfo.getLoanAccountID(),
						returnInfo.getSubAccountID(),
						lAccountType,
						returnInfo.getInterestStart());
				returnInfo.setInterestReceiveAble(interestPredraw.getPredrawInterest());
				Log.print("������Ϣ:"+interestPredraw.getPredrawInterest());
							
				//��Ϣ
				Log.print("----��ʼ���㱾����Ϣ----");
				double dInterestIncome = UtilOperation.Arith.sub(UtilOperation.Arith.round(returnInfo.getInterest(),2),UtilOperation.Arith.round(returnInfo.getInterestReceiveAble(),2));
				returnInfo.setInterestIncome(dInterestIncome);
				Log.print("������Ϣ:"+returnInfo.getInterestIncome());
					//�����й�����������ڸ�����Ϣ���㷽�� Boxu 2008-10-06
					//���ڸ���
					Log.print("----��ʼ���㸴��----");
					LoanAccountInterestInfo compoundInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);		//���һ����������
					returnInfo.setCompoundInterest(UtilOperation.Arith.round(compoundInterest.getInterest(),2));		//�õ�����
					returnInfo.setCompoundInterestStart(compoundInterest.getSDate());	//�õ�������Ϣ��
					returnInfo.setCompoundAmount(compoundInterest.getBalance());		//�õ���������
					double dCompoundRate = UtilOperation.Arith.round(UtilOperation.Arith.div(compoundInterest.getRate(),100),6);
					returnInfo.setCompoundRate(dCompoundRate);				//�õ�����������
					Log.print("����:"+compoundInterest.getInterest());
								
					//���ڷ�Ϣ
					Log.print("----��ʼ�������ڷ�Ϣ----");
					LoanAccountInterestInfo overDueInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.FORFEITINTEREST);//���һ����������
					returnInfo.setOverDueInterest(UtilOperation.Arith.round(overDueInterest.getInterest(),2));		//�õ����ڷ�Ϣ
					returnInfo.setOverDueStart(overDueInterest.getSDate());				//�õ����ڷ�Ϣ��Ϣ��
					returnInfo.setOverDueAmount(overDueInterest.getBalance());			//�õ����ڷ�Ϣ����
					double dOverDueRate = UtilOperation.Arith.round(UtilOperation.Arith.div(overDueInterest.getRate(),100),6);
					returnInfo.setOverDueRate(dOverDueRate);							//�õ����ڷ�Ϣ����
					Log.print("���ڷ�Ϣ:"+returnInfo.getOverDueInterest());			
				/*}
				else
				{
					//����
					Log.print("----��ʼ���㸴��----");
					LoanAccountInterestInfo compoundInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMPOUNDINTEREST);		//���һ����������
					returnInfo.setCompoundInterest(compoundInterest.getInterest());		//�õ�����
					returnInfo.setCompoundInterestStart(compoundInterest.getSDate());	//�õ�������Ϣ��
					returnInfo.setCompoundAmount(compoundInterest.getBalance());		//�õ���������
					double dCompoundRate = UtilOperation.Arith.round(UtilOperation.Arith.div(compoundInterest.getRate(),100),6);
					returnInfo.setCompoundRate(dCompoundRate);				//�õ�����������
					Log.print("����:"+returnInfo.getCompoundInterest());
								
					//���ڷ�Ϣ
					Log.print("----��ʼ�������ڷ�Ϣ----");
					LoanAccountInterestInfo overDueInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.FORFEITINTEREST);//���һ����������
					returnInfo.setOverDueInterest(overDueInterest.getInterest());		//�õ����ڷ�Ϣ
					returnInfo.setOverDueStart(overDueInterest.getSDate());				//�õ����ڷ�Ϣ��Ϣ��
					returnInfo.setOverDueAmount(overDueInterest.getBalance());			//�õ����ڷ�Ϣ����
					double dOverDueRate = UtilOperation.Arith.round(UtilOperation.Arith.div(overDueInterest.getRate(),100),6);
					returnInfo.setOverDueRate(dOverDueRate);							//�õ����ڷ�Ϣ����
					Log.print("���ڷ�Ϣ:"+returnInfo.getOverDueInterest());
				}*/
				
				if (lLoanType == SETT_TRUST_LOAN){				//���м��㵣����
					//������
					Log.print("----��ʼ���㵣����----");
					LoanAccountInterestInfo suretyInterest = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.ASSURE);//���һ����������
					returnInfo.setSuretyFee(UtilOperation.Arith.round(suretyInterest.getInterest(),2));
					returnInfo.setSuretyFeeStart(suretyInterest.getSDate());
					double dSuretyFeeRate = UtilOperation.Arith.round(UtilOperation.Arith.div(suretyInterest.getRate(),100),6);
					returnInfo.setSuretyFeeRate(dSuretyFeeRate);
					Log.print("������:"+returnInfo.getSuretyFee());
				}
				if (lLoanType == SETT_CONSIGN_LOAN){		//ί�вż��������Ѻ���Ϣ˰
					//������
				
					LoanAccountInterestInfo commission = io.getLoanAccountFee(
							returnInfo.getOfficeID(),
							returnInfo.getCurrencyID(),
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							returnInfo.getInterestStart(),
							tsSys,
							SETTConstant.InterestFeeType.COMMISION);		//���һ����������
					returnInfo.setCommisson(UtilOperation.Arith.round(commission.getInterest(),2));
					returnInfo.setCommissionStart(commission.getSDate());
					double dCommissionRate = UtilOperation.Arith.round(UtilOperation.Arith.div(commission.getRate(),100),6);
					returnInfo.setCommissionRate(dCommissionRate);
					Log.print("������:"+returnInfo.getCommisson());
								
					//��Ϣ˰��
					Log.print("----��ʼ������Ϣ˰��----");
					double dInterestShouldTax = 
						UtilOperation.Arith.add(
							returnInfo.getOverDueInterest(),
							UtilOperation.Arith.add(returnInfo.getInterest(),
								returnInfo.getCompoundInterest()));  //Ӧ��˰��Ϣ
					//modify by zcwang 2007-3-31 �·��������ʼƻ���ȡ��Ϣ����
					InterestTaxInfo tax = io.getInterestTaxByPlan(
							returnInfo.getLoanAccountID(),
							returnInfo.getSubAccountID(),
							dInterestShouldTax
							);
							//	
					returnInfo.setInterestPlanId(tax.getInterestTaxPlanId());
					SettTaxRatePlanSettingBiz planBiz = new SettTaxRatePlanSettingBiz();
					String allRateBuffer[] = planBiz.findAllInterestTaxRate(tax.getInterestTaxPlanId()).split(",");
					double interestTaxRateMain = 0.0;
					double interestTaxRate = 0.0;
					for(int i=0;i<allRateBuffer.length;i++)
					{
						if(i==0)
						{
							interestTaxRateMain=Double.valueOf(allRateBuffer[i]).doubleValue();
						}
						else 
						{
							interestTaxRate+=interestTaxRateMain*Double.valueOf(allRateBuffer[i]).doubleValue()/100;
						}
					}
					returnInfo.setInterestTaxRate(interestTaxRateMain+interestTaxRate);
					Log.print("��Ϣ˰����:"+returnInfo.getInterestTaxRate());
					returnInfo.setInterestTax(UtilOperation.Arith.round(tax.getInterestTax(),2));
					Log.print("��Ϣ˰��:"+returnInfo.getInterestTax());
				}
			}
//			else{
//				//add by zhouxiang 20111026
//				//���㲿�ֻ���ʱ��Ӧ����Ϣ
//				PayNoticeOperation payOperation = new PayNoticeOperation();
//				double payInterest = payOperation.findRepayInterestByID(
//						info.getAmount(),
//						info.getPayFormID(),
//						info.getContractID(),
//						info.getInterestStart(),
//						info.getOfficeID(),
//						info.getCurrencyID()
//						);
//				returnInfo.setInterest(payInterest);
//				returnInfo.setInterestIncome(payInterest);
//				//add end
//			}
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
		catch (IException e){
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
			}
		}
		finally{
			if(io!=null){
				io.closeConnection();
			}
		}
		
		return returnInfo;
	}
	/**
	 * ɾ�������¼
	 * 
	 * @param info
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransRepaymentLoanInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long id = -1;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		UtilOperation utilOperation = new UtilOperation();
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			
			//�Կͻ�����
			Log.print("��ʼɾ������......");
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
			TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			Log.print("errMsgLength:" + errMsg.length() + " errMsgSpace:" + (errMsg == ""));
			//���޸Ĺ�
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//ɾ�����׼�¼
				Log.print("׼������ɾ�����˲�����");
				TransRepaymentLoanInfo repaymentInfo = dao.findByID(info.getID());
				accountBookOperation.deleteRepaymentLoan(repaymentInfo);
				Log.print("�ɹ�����ɾ�����˲�����");
				
				//�޸�����ָ��״̬
				if(repaymentInfo.getInstructionNo()!=null && repaymentInfo.getInstructionNo().length()>0)
					{
						log.info("---------������ָ��----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(repaymentInfo.getInstructionNo()).longValue(),OBConstant.SettInstrStatus.REFUSE);
					}
				/**
				 * ��ʼ�ı��⻹֪ͨ������ǰ����֪ͨ����״̬
				 */
				if (info.getStatusID()==SETTConstant.TransactionStatus.SAVE){
					if (info.getPreFormID()>0){
						Log.print("===ʹ������ǰ����֪ͨ��===");
						Log.print("===��ʼ�����ǰ����֪ͨ��״̬===");
						/* ����һ������֪ͨ����Ҫ����ջأ��Ի���׽��ж��ɾ����ʱ�򣬵�һ��ɾ������׻���֪ͨ��״̬�ı�Ϊ
						 * ��������֮���ɾ��������޷�ɾ������ע�͵��˷��� add by wjyang3 2008-4-22
						if (!dao.isPreFormStatusCorrect(info.getPreFormID(),LOANConstant.AheadRepayStatus.USED)){
							throw new IRollbackException(mySessionCtx,"��ǰ����֪ͨ���Ѿ����޸�");
						}
						*/
						Log.print("===��ʼ�ı���ǰ����֪ͨ��״̬Ϊ�Ѹ���===");
						dao.updatePreFormStatus(info.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
					}
					
					if (info.getFreeFormID()>0){
						Log.print("===ʹ�����⻹֪ͨ��===");
						Log.print("===��ʼ����⻹֪ͨ����״̬===");
						if (!dao.isFreeFormStatusCorrect(info.getFreeFormID(),LOANConstant.FreeApplyStatus.USED)){
							throw new IRollbackException(mySessionCtx,"�⻹֪ͨ���Ѿ����޸�");
						}
						Log.print("===��ʼ�ı��⻹֪ͨ����״̬Ϊ�Ѹ���");
						dao.updateFreeFormStatus(info.getFreeFormID(),LOANConstant.FreeApplyStatus.CHECK);
					}
				}
				//����޸ĵ�ʱ����ʹ����ǰ����֪ͨ������Ҫ������֪ͨ����״̬�ı�.
				TransRepaymentLoanInfo oldInfo = dao.findByID(info.getID());
				if(oldInfo!=null&&oldInfo.getPreFormID()>0&&info.getPreFormID()<0){
					dao.updatePreFormStatus(oldInfo.getPreFormID(),LOANConstant.AheadRepayStatus.CHECK);
				}
				
			}
			//����ID
			Log.print("�����߼�ɾ����");
			log.info("------------�ж��Ƿ����Ŵ���--------------");
			if(info.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				deleteSyndicationLoan(info.getID());
			}
			return dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.DELETED);
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
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
		}
		}
	}
	/**
	 * ���˻���ȡ������
	 * 
	 * @param info
	 * @param checkOrCancel
	 * @return @throws
	 *         RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransRepaymentLoanInfo info, boolean checkOrCancel) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long lStatus = -1; //��ǰ�����ı���״̬,���ݵ�ǰ�����checkOrCancel�ж�
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		TransRepaymentLoanInfo transInfo = new TransRepaymentLoanInfo();
		UtilOperation utilOperation = new UtilOperation();
		SubAccountLoanInfo subAccountLoan = new SubAccountLoanInfo();
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		//�Կͻ�����
		try
		{
			sessionID = utilOperation.waitUtilSuccessLock(info.getLoanAccountID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		Log.print("����״̬ǰ");
		if (checkOrCancel)
		{ //������Ѿ����ϵ�,�����Ƿ�ȡ������,���δ����,���鸴��
			lStatus = SETTConstant.Actions.CHECK;
		}
		else
		{
			lStatus = SETTConstant.Actions.CANCELCHECK;
		}
		TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(info.getID());
		String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), lStatus);
		Log.print("����״̬��" + errMsg);
		//���޸Ĺ�
		if (errMsg != null && !errMsg.equals(""))
		{
			throw new IRollbackException(mySessionCtx, errMsg);
		}
		if (isTouch(info, dao))
		{
			throw new IRollbackException(mySessionCtx, "Sett_E133");
		}
		try
		{
			//dao.update(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		try{
			transInfo = dao.findByID(info.getID());  //�����ݿ��в����¼
			transInfo.setCheckAbstract(info.getCheckAbstract());
			transInfo.setCheckUserID(info.getCheckUserID());
			
			log.info("----------�ж��Ƿ����Ŵ���----------------");
			if(transInfo.getTransactionTypeID()==SETTConstant.TransactionType.BANKGROUPLOANRECEIVE)
			{
				ArrayList list = null;
				list = findSyndicationByReceiveLoanID(transInfo.getID());
				transInfo.setSyndicationLoanInterest(list);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		
		if (checkOrCancel)
		{
			/**
			 * ���˽��׼�¼,��Ҫ�жϽ������� 
			 */
			try
			{
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("====���ö�ʴ����ջظ��˷���====");
				accountBookOperation.checkMultiLoanReceive(transInfo);
			}
			else {
				accountBookOperation.checkRepaymentLoan(transInfo);
			}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),e);}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			try
			{
				dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.FINISH);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				Log.print("��ʼ���˵�ǰ���");
				dao.updateCurrentBalance(info.getID(),info.getSubAccountID());
				
				//Modify by leiyang  date 2007-6-14
				/*subAccountLoan = dao.getSubAccountLoanInfoByID(info.getSubAccountID());
				
				//�Ƿ��⻹ʣ�������Ϣ
				if(info.getIsRemitInterest() == 1)
				{
					subAccountLoan.setInterest(0.0);
					
					subAccountLoan.setPreDrawInterest(0.0);
				}
				else 
				{
					if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
					{
						subAccountLoan.setInterest(info.getInterest() - info.getRealInterest());
						
						//������ʵ��֧ȡ�����ۼ�������Ϣ Boxu Add 2008��1��30��
						if(info.getInterestReceiveAble() > 0)
						{
							subAccountLoan.setPreDrawInterest(UtilOperation.Arith.sub(info.getInterestReceiveAble(), info.getRealInterestReceiveAble()));
						}
					}
				}
					
				//�Ƿ��⻹ʣ�ิ��
				if(info.getIsRemitCompoundInterest() == 1) {
					subAccountLoan.setCompoundInterest(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
					{
						subAccountLoan.setCompoundInterest(info.getCompoundInterest() - info.getRealCompoundInterest());
					}
				}
				//�Ƿ��⻹ʣ�����ڷ�Ϣ
				if(info.getIsRemitOverDueInterest() == 1) {
					subAccountLoan.setOverDueInterest(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
					{
						subAccountLoan.setOverDueInterest(info.getOverDueInterest() - info.getRealOverDueInterest());
					}
				}
				//�Ƿ��⻹ʣ�ൣ����
				if(info.getIsRemitSuretyFee() == 1) {
					subAccountLoan.setSuretyFee(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
					{
						subAccountLoan.setSuretyFee(info.getSuretyFee() - info.getRealSuretyFee());
					}
				}
				//�Ƿ��⻹ʣ��������
				if(info.getIsRemitCommission() == 1) {
					subAccountLoan.setCommission(0.0);
				}
				else {
					if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
					{
						subAccountLoan.setCommission(info.getCommission() - info.getRealCommission());
					}
				}
				//��Ϣ˰��
				if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
				{
					subAccountLoan.setInterestTax(info.getInterestTax() - info.getRealInterestTax());
				}
				if(subAccountLoan.getBalance()==0.0) //�����ջ�,���������н��ȫ������ʱ,������Ϣ��,�����˻������Ϣ�ֶ�����.
				{
					dao.updateSubAccountLoanInfo(subAccountLoan);
				}*/
				
				//modify by zcwang 2007-6-1 ���ô����޸�
				//��һ�ʴ����ڱ����Ѿ����壬������ҵ��ʱ����Ϣ�����⻹���������Ƚ�Ϣʱ��Ȼ�ܹ��������ʱ�����⻹����Ϣ���
				//if(info.getIsRemitInterest() == 1)
				//	dao.updateCurrentBalance2(info.getSubAccountID());
				
			}
			//modified by mzh_fu 2007/05/011
			/*catch (RemoteException e)
			{
				throw e;
			}
			catch (IRollbackException e)
			{
				throw e;
			}*/
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			//�Ƿ�������ӿ�
			boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
			
			try
			{
				if(bIsValid) {
					Log.print("*******************��ʼ���������ջ�ָ�������**************************");
					try {
						log.debug("------��ʼ�����ջ�--------");
						//�������
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setInputUserID(info.getInputUserID());
						
						//��������ָ�����
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------���ɴ����ջ�ָ��ɹ�--------");
						
					} catch (Throwable e) {
						log.error("���ɴ����ջ�ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "���ɴ����ջ�ָ��ʧ�ܣ�"+e.getMessage());
					}
				}	
				else {
					Log.print("û�����нӿڻ���Ҫ��������ָ�");
				}
			}
			catch (Exception e)
			{
				log.debug("-----��������ջ�ָ��ָ��ʧ��");
				throw new IRollbackException(mySessionCtx, "��������ջ�ָ�����" + e.getMessage());
			}
		
		}
		else
		{
			/**
			 * ȡ�����˽��׼�¼,��Ҫ�жϽ������� 
			 */
			try
			{
			if (transInfo.getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE){
				Log.print("====���ö�ʴ����ջ�ȡ�����˷���====");
				accountBookOperation.cancelCheckMultiLoanReceive(transInfo);
			}
			else {
				accountBookOperation.cancelCheckRepaymentLoan(transInfo);
			}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),e);}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			//����״̬����δ����
			try
			{
				//Added by zwsun , 2007/8/4, �ж��Ƿ����������
				InutParameterInfo pInfo=new InutParameterInfo();
				pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				pInfo.setOfficeID(info.getOfficeID());
				pInfo.setCurrencyID(info.getCurrencyID());
				pInfo.setTransTypeID(info.getTransactionTypeID());
				pInfo.setActionID(-1);
				if(FSWorkflowManager.isNeedApproval(pInfo)){
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.APPROVALED, info.getCheckAbstract(), info.getCheckUserID());					
				}else{
					dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());					
				}
										
				if(info.getInstructionNo()!=null && info.getInstructionNo().length()>0)
				{
					log.info("---------������ָ��----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
					
					financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					financeInfo.setDealUserID(info.getInputUserID());
					financeInfo.setConfirmDate(info.getExecute());
					financeInfo.setFinishDate(info.getExecute());
					financeInfo.setTransNo(info.getTransNo());
					financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					financeDao.updateStatusAndTransNo(null,financeInfo);
				}
				Log.print("��ʼ���˵�ǰ���");
				dao.updateCurrentBalance(info.getID(),info.getSubAccountID());
				
				//Boxu Update 2008��3��28�� �����յ�����Ϣ����֧��ȡ������û������,���ǽ�Ϣ����ǰ,���˻���Ϣ�������
				//Boxu Update 2008��4��9�� �����ڻ�д�������ѵ�ʱ������ķ����������
				/*if(info.getInterestClear().compareTo(Env.getSystemDate(info.getOfficeID(), info.getCurrencyID())) == 0)
				{
					//Modify by leiyang  date 2007-6-14
					subAccountLoan = dao.getSubAccountLoanInfoByID(info.getSubAccountID());
					//������Ϣ
					if(info.getInterest()!=0.0)
					{
						subAccountLoan.setInterest(info.getInterest());
					}
					//����
					if(info.getCompoundInterest()!=0.0)
					{
						subAccountLoan.setCompoundInterest(info.getCompoundInterest());
					}
					//���ڷ�Ϣ
					if(info.getOverDueInterest()!=0.0)
					{
						subAccountLoan.setOverDueInterest(info.getOverDueInterest());
					}
					//������
					if(info.getSuretyFee()!=0.0)
					{
						subAccountLoan.setSuretyFee(info.getSuretyFee());
					}
					//������
					if(info.getCommission()!=0.0)
					{
						subAccountLoan.setCommission(info.getCommission());
					}
					//��Ϣ˰��
					if(info.getInterestTax()!=0.0)
					{
						subAccountLoan.setInterestTax(info.getInterestTax());
					}
					//������Ϣ
					if(info.getInterestReceiveAble()!=0.0)
					{
						subAccountLoan.setPreDrawInterest(info.getInterestReceiveAble());
					}
					dao.updateSubAccountLoanInfo(subAccountLoan);
				}*/
			}
			catch (Exception e)
			{
				if(e instanceof IRollbackException){
					throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
				}else{
					throw new IRollbackException(mySessionCtx, e.getMessage(), e);
				}
			}
		}
		//����
		try
		{
			if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
				utilOperation.releaseLock(info.getLoanAccountID(), sessionID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getID();
		}
	}
	
	public boolean squareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Log.print("====��ʼ���빴�˷���====");
		long[] sessionIDs = null;
		//DAO
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//���˲����ӿ���
		GeneralLedgerOperation glOperation = new GeneralLedgerOperation();

		try
		{
			Log.print("----��ʼ�Կͻ�����----");
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] = utilOperation.waitUtilSuccessLock(infos[i].getLoanAccountID());
			}
			
			//У��״̬�Ƿ���ȷ
			for (int i = 0; i < infos.length; i++)
			{
				TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(infos[i].getID());

				String errMsg = "";

				errMsg =
					UtilOperation.checkStatus(
						infos[i].getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.SQUAREUP);

				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}

				//�ж��Ƿ��޸�
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E047");
			}
			
			//�����ո�ƽ��
			double dPayAmount = 0.0;
			double dReceiveAmount = 0.0;
			for (int i = 0; i < infos.length; i++)
			{
				
				if(infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.PAYMENT)
				{
					dPayAmount += infos[i].getAmount();
				}
				else if(infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.TRUSTLOAN ||
						infos[i].getTransDirectionID() == SETTConstant.MultiLoanType.CONSIGNLOAN)
				{
					dReceiveAmount += infos[i].getAmount();
				}
			}
			
			if(dPayAmount != dReceiveAmount)
			{
				throw new IRollbackException(mySessionCtx, "�ո���ƽ�⣬����ʧ��");
			}
			Log.print("----У���ո�ƽ�����----");
			//�����ʽ���׺�
			log.debug("------��ʼ��ȡ��ʽ���׺�--------");
			String transNo = utilOperation.getNewTransactionNo(infos[0].getOfficeID(), infos[0].getCurrencyID(),infos[0].getTransactionTypeID());
			log.debug("------��ʽ���׺���:" + transNo + "--------");

			for (int i = 0; i < infos.length; i++)
			{
				infos[i].setTransNo(transNo);
				infos[i].setSerialNo(i+1);
				
				dao.updateTransNo(infos[i].getID(),transNo,i+1);
			}			

			//���˽��׼�¼
			log.info("--------------��ʼAccountBook���˽��׼�¼--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info("--------------��ʼ���˽��׼�¼:" + infos[i].getID() + "--------------");
				accountBookOperation.squareMultiLoanReceive(infos[i]);

				dao.updateStatus(infos[i].getID(), SETTConstant.TransactionStatus.CIRCLE);
				log.info("--------------�������˽��׼�¼:" + infos[i].getID() + "--------------");

			}

			//��������ָ��(���SEFC)
			if (infos[0].getTransactionTypeID() == SETTConstant.TransactionType.MULTILOANRECEIVE)
			{
				//�ж��Ƿ���������ӿ�
				//if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)) {
					//�������
				  if(false){//�н���Ŀ����Ҫ������Ϣָ��
					CreateInstructionParam instructionParam = new CreateInstructionParam();
					instructionParam.setTransactionTypeID(infos[0].getTransactionTypeID());						
					instructionParam.setTransNo(infos[0].getTransNo());
					instructionParam.setOfficeID(infos[0].getOfficeID());
					instructionParam.setCurrencyID(infos[0].getCurrencyID());
					instructionParam.setCheckUserID(infos[0].getCheckUserID());
					instructionParam.setInputUserID(infos[0].getInputUserID());
					
					//��������ָ�����
					IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
					bankInstruction.createBankInstructionFromTransDetail(instructionParam);
						
				}
				else {
					log.info("��ǰϵͳû���ṩ����ӿڷ���");
				}
			}

			log.info("--------------����AccountBook���˽��׼�¼--------------");
			
			log.debug("-----��鱾���׺Ų����ķ�¼�Ƿ���ƽ��-----");
			boolean checkRes = glOperation.checkTransDCBalance(transNo);
			if (!checkRes)
			{
				log.debug("-----���ƽ�ⲻƽ�⣬��¼����ʧ��-------");
				throw new IRollbackException(mySessionCtx, "���ƽ�ⲻƽ�⣬��¼����ʧ��");
			}	
		}
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
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		finally
		{
			//����
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
						try
						{
							utilOperation.releaseLock(infos[i].getLoanAccountID(), sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(mySessionCtx, e.getMessage(), e);
						}
					}
				}
			}
		}

		return true;
		}
	}

	public boolean cancelSquareUp(TransRepaymentLoanInfo[] infos) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long[] sessionIDs = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//�Կͻ�����
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] = utilOperation.waitUtilSuccessLock(infos[i].getLoanAccountID());
			}

			//У��״̬�Ƿ���ȷ
			for (int i = 0; i < infos.length; i++)
			{
				TransRepaymentLoanInfo newInfo = this.FindRepaymentDetailByID(infos[i].getID());

				String errMsg = "";

				errMsg =
					UtilOperation.checkStatus(
						infos[i].getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CANCELSQUAREUP);

				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}

				//�ж��Ƿ��޸�
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E051");
			}

			//ɾ����ʽ���׺�
			for (int i = 0; i < infos.length; i++)
			{				
				dao.updateTransNo(infos[i].getID(),null,-1);
			}			

			//ȡ�����˽��׼�¼
			log.info("--------------��ʼAccountBookȡ�����˽��׼�¼--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info("--------------��ʼȡ�����˽��׼�¼:" + infos[i].getID() + "--------------");
				accountBookOperation.cancelSquareMultiLoanReceive(infos[i]);

				dao.updateStatus(infos[i].getID(), SETTConstant.TransactionStatus.CHECK);
				log.info("--------------����ȡ�����˽��׼�¼:" + infos[i].getID() + "--------------");

			} 
			log.info("--------------����AccountBookȡ�����˽��׼�¼--------------");
		}
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
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		finally
		{
			//����
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
						try
						{
							utilOperation.releaseLock(infos[i].getLoanAccountID(), sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(mySessionCtx, e.getMessage(), e);
						}
					}
				}
			}
		}

		return true;
		}
	}
	/**
	 * ����������������(��Ӫ/ί��)
	 * Added by zwsun, 2007-06-21
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransGrantLoanInfo info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long loanId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		Sett_TransGrantLoanDAO dao = new Sett_TransGrantLoanDAO();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		loanId =info.getID();
		try
		{
			TransGrantLoanInfo loanInfo = new TransGrantLoanInfo();
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
					TransGrantLoanInfo loanInfo1 = new TransGrantLoanInfo();
					loanInfo1=dao.findByID(info.getID());
					//����check����
					//TransFixedOpenInfo depositInfo = new TransFixedOpenInfo();
					//depositInfo = this.openFindByID(info.getID());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo1.setAbstract("����");
					loanInfo1.setCheckUserID(returnInfo.getUserID());	//���������Ϊ������				
					
					//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					check(loanInfo1,true);
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
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return loanId;
		}
	}	
	/**
	 * ��������ȡ��������������Ӫ/ί������������Զ����ˣ���ȡ������֮ǰ������ȡ�����ˣ�������ֶ����ˣ���ֻ��ȡ������
	 * ȡ�����ˣ�����ejb��ȡ�����˷���
	 * ȡ����������ҵ���¼״̬��Ϊ�ѱ��漴��
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransGrantLoanInfo loanInfo)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransGrantLoanDAO loanDao = new Sett_TransGrantLoanDAO();		
		InutParameterInfo inutParameterInfo = loanInfo.getInutParameterInfo();	
		
		try
		{
			//���ϵͳ���趨Ϊ�Զ������ˣ�����Ҫ��ȡ�����ˣ�Ȼ��ȡ������
			if(FSWorkflowManager.isAutoCheck() && loanInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//ȡ������
				this.check(loanInfo,false);
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
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
		}
	}	
	
	
	/**
	 * ��ʴ����ջع��˲�ѯ
	 * @param condiInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection findSquareUpRecordsByCondition(TransRepaymentLoanInfo condiInfo)throws RemoteException,IRollbackException{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Collection coll = null;
		try{
			coll = dao.findSquareUpRecordsByCondition(condiInfo);
		}
		catch (IException e){
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		catch (Exception e){
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return coll;
	}
	
	/**
	 * ͨ�����׺Ų�ѯ��ʴ����ջصĽ����
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection getRepaymentCollectionByTransNo(String strTransNo)throws RemoteException,IRollbackException{
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Log.print("----��ʼ����getRepaymentCollectionByTransNo----");
		Log.print("----����Ľ��׺���:"+ strTransNo + "----");
		Collection coll = null;
		try{
			coll = dao.getRepaymentCollectionByTransNo(strTransNo);
		}catch (IException e){
			if(e instanceof IRollbackException){
				throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
			}else{
				throw new IRollbackException(mySessionCtx,e.getMessage(),e);
			}
		}catch (Exception e){
			throw new IRollbackException(mySessionCtx,"Gen_E001",e.getMessage());
		}
		return coll;
	}
	/***************************************************************************************************************************************************************************************************
	 * ******************************�����ջؽ���*************************** ****************************************************************
	 */
	/*
	 * ���ݷſ�֪ͨ��ID�������д����ջ���Ϣ
	 * 
	 * @param id ���д������Ϣ�ķſ�֪ͨ��ID
	 * @return TransGrantLoanInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection grantFindInterestByLoanNoteID(long lLoanNoteID,long nOfficeID,long nCurrencyID,long lLoanAccountID,long lContractID,long lSubAccountID) throws RemoteException, IRollbackException
	{
		Collection c = null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO();
		AccountInfo accountInfo = null;
       //����SubAccountID���Ҷ�Ӧ�����˻���¼
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountLoanInfo resultInfo = new SubAccountLoanInfo();
		try
		{
			try
			{
				accountInfo = sett_AccountDAO.findByID(lLoanAccountID);
				if (accountInfo == null)
				{
					throw new IException(true, "���˻�����û�ж�Ӧ��¼����ѯʧ��", null);
				}
			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			try
			{
				subAccountAssemblerInfo = sett_SubAccountDAO.findByID(lSubAccountID);
				resultInfo.setID(lSubAccountID);
				resultInfo = sett_SubAccountDAO.querySubAccountInfo(resultInfo);
				if (subAccountAssemblerInfo == null)
				{
					throw new IException(true, "���˻�����û�ж�Ӧ��¼����ѯʧ��", null);
				}

			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw ie;
			}
			Timestamp tsLastInterestDate = resultInfo.getClearInterestDate();//ͨ�����˻���ȡ��һ����Ϣ��
					
			Log.print("���ݷſ�֪ͨ��ID�������д������Ϣ1:" + lLoanNoteID);
			c = dao.findInterestByNoteID(lLoanNoteID,nOfficeID,nCurrencyID,resultInfo.getClearInterestDate());
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return c;
	}
	
	/**
	 * ���ݺ�ͬ�ͷſ�֪ͨ��ID��ѯʵ��֧������Ϣ�������ѵȻ�����Ϣ
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransRepaymentLoanInfo grantFindInterestByCondition(SubLoanAccountDetailInfo info) throws RemoteException, IRollbackException
	{
		TransRepaymentLoanInfo reInfo=null;
		Sett_TransRepaymentLoanDAO dao = new Sett_TransRepaymentLoanDAO();
		try{
			reInfo=dao.grantFindInterestByCondition(info);
			
		}catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return reInfo;
	}
}
