package com.iss.itreasury.settlement.craftbrother.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.SessionBean;
import com.iss.itreasury.craftbrother.notice.dao.SEC_NoticeDAO;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountcontract.dataentity.TransDiscountContractBillInfo;
import com.iss.itreasury.loan.transdiscountcredence.bizlogic.TransDiscountCredenceBiz;
import com.iss.itreasury.loan.transdiscountcredence.dao.TransDiscountCredenceDAO;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.craftbrother.dao.TransCraInterestPreDrawDAO;
import com.iss.itreasury.settlement.craftbrother.dao.TransCraftbrotherDAO;
import com.iss.itreasury.settlement.craftbrother.dataentity.CalcNoticeInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.CraInterestCalcInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.DraftAmortizationInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraInterestPreDrawInfo;
import com.iss.itreasury.settlement.craftbrother.dataentity.TransCraftbrotherInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author qhzhou
 *
 */
public class TransCraftbrotherEJB implements SessionBean {

	private javax.ejb.SessionContext mySessionCtx = null;
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private static final long serialVersionUID = 4084208537156611761L;

	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                �쳣˵����
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbCreate() throws javax.ejb.CreateException,
			java.rmi.RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void ejbRemove() throws java.rmi.RemoteException {
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
	 * @exception java.rmi.RemoteException
	 *                �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	/** �����ݴ���޸��ݴ�ӿ� */
	public long tempSave(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		
		long lReturn = -1;
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//�˲������ӿ��� 
			AccountOperation accountOperation = new AccountOperation();
			if(info.getId() < 0){
				info.setNstatusId(SETTConstant.TransactionStatus.TEMPSAVE);
				lReturn = transDao.add(info);
			}else{
				lReturn = info.getId();
				//1.�ж�״̬�Ƿ��޸�
				if(isTouch(info, transDao)){
					throw new IRollbackException(mySessionCtx, "Sett_E153");
				}
				//2.�ж�״̬�Ƿ�Ϸ�
				String errMsg = checkStatus(SETTConstant.Actions.MODIFYTEMPSAVE, info, transDao);
				if (errMsg != null && !errMsg.equals("")){
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//�޸��ݴ�
				info.setDtModify(Env.getSystemDateTime());//�޸�ʱ��
				transDao.update(info);
			}
			accountOperation.saveExternalAccount(info.getExternalAccountInfo());
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** �����������޸ı���ӿ� */
	public long save(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = -1;
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//���幤����
			UtilOperation utilOperation = new UtilOperation();
			//�˲������ӿ��� 
			AccountOperation accountOperation = new AccountOperation();
			String stransNo = info.getStransNo();
			//��־λ���Ƿ����������׺�
			boolean bNewTransNo = false;
			if (stransNo == null || stransNo.equalsIgnoreCase(""))
			{ //δ��������������½��׺�
				bNewTransNo = true;
				//ͨ�����߲����ӿ����ȡ�½��׺�
				log.debug("------��ʼ��ȡ�½��׺�--------");
				stransNo = utilOperation.getNewTransactionNo(
								info.getNofficeId(),
								info.getNcurrencyId(),
								info.getNtransactionTypeId());
				info.setStransNo(stransNo);
				Log.print("----��ʼУ��֪ͨ��(ƾ֤)״̬----");
				if(info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE)
				{
					if (transDao.checkTransactionRecord(info.getNnoticeId(),SETTConstant.TransactionStatus.SAVE))
					{
						Log.print("----�ú�ͬ��ƾ֤�Ѿ����ڽ��׼�¼----");
						throw new IRollbackException(mySessionCtx, "��ƾ֤�Ѿ����ڽ��׼�¼");
					}
					
				}else{
					if (!transDao.checkPayForm(info.getNnoticeId(),info.getNcraBusinessTypeId()))
					{
						Log.print("----֪ͨ����ƾ֤���Ѿ���ʹ��----");
						throw new IRollbackException(mySessionCtx, "֪ͨ����ƾ֤���Ѿ���ʹ��");
					}
				}
			}
					
			if(info.getId() < 0){
				info.setNstatusId(SETTConstant.TransactionStatus.SAVE);
				lReturn = transDao.add(info);
			}else{
				lReturn = info.getId();
				info.setNstatusId(SETTConstant.TransactionStatus.SAVE);
				//1.�ж�״̬�Ƿ��޸�
				if(isTouch(info, transDao)){
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				//2.�ж�״̬�Ƿ�Ϸ�
				String errMsg = checkStatus(SETTConstant.Actions.MODIFYSAVE, info, transDao);
				if (errMsg != null && !errMsg.equals("")){
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				//�޸ı���
				info.setDtModify(Env.getSystemDateTime());//�޸�ʱ��
				transDao.update(info);
			}
			accountOperation.saveExternalAccount(info.getExternalAccountInfo());
			if(lReturn > 0){
				if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.DISCOUNT){//ת����ҵ��
					//����ƾ֤��״̬Ϊ����ʹ�á�
					TransDiscountCredenceDAO dao=new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
					dao.update(info.getNnoticeId(),info.getNinputUserId(),LOANConstant.DiscountCredenceStatus.USED);
				}else if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.FUND_ATTORN    
						|| info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS){//�ʽ���//�ʲ�ת��
				    SEC_NoticeDAO nDao = new SEC_NoticeDAO();
				    nDao.updateStatusID(info.getNnoticeId(),SECConstant.NoticeFormStatus.USED);
				}else{
					throw new IRollbackException(mySessionCtx, "ͬҵҵ�����ʹ��󣬸���֪ͨ��״̬ʧ��!");
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ����ɾ���ӿ� */
	public long delete(TransCraftbrotherInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = info.getId();
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			//1.�ж�״̬�Ƿ��޸�
			if(isTouch(info, transDao)){
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			//2.�ж�״̬�Ƿ�Ϸ�
			String errMsg = checkStatus(SETTConstant.Actions.DELETE, info, transDao);
			if (errMsg != null && !errMsg.equals("")){
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			TransCraftbrotherInfo tmpinfo = new TransCraftbrotherInfo();
			tmpinfo.setId(lReturn);
			tmpinfo.setNstatusId(SETTConstant.TransactionStatus.DELETED);
			transDao.update(tmpinfo);
			if(lReturn > 0){
				if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.DISCOUNT){//ת����ҵ��
					//����ƾ֤��״̬Ϊ����������
					TransDiscountCredenceDAO dao=new TransDiscountCredenceDAO("LOAN_DISCOUNTCREDENCE");
					if(info.getNtransactionTypeId() != SETTConstant.TransactionType.TRANS_DISCOUNT_RECEIVE)
						dao.update(info.getNnoticeId(),info.getNinputUserId(),LOANConstant.DiscountCredenceStatus.CHECK);
				}else if(info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.FUND_ATTORN    
						|| info.getNcraBusinessTypeId() == CRAconstant.SameBusinessAttribute.SAME_BUSINESS){//�ʽ���//�ʲ�ת��
				    SEC_NoticeDAO nDao = new SEC_NoticeDAO();
				    nDao.updateStatusID(info.getNnoticeId(),SECConstant.NoticeFormStatus.CHECKED);
				}else{
					throw new IRollbackException(mySessionCtx, "ͬҵҵ�����ʹ��󣬸���֪ͨ��״̬ʧ��!");
				}
			}
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ID���ҽ��� */
	public TransCraftbrotherInfo findByID(long lId) throws RemoteException {
		TransCraftbrotherInfo rInfo = null;
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			rInfo = (TransCraftbrotherInfo)transDao.findByID(lId, TransCraftbrotherInfo.class);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return rInfo;
	}
	/** ���׺��������ҽ��� */
	public TransCraftbrotherInfo findByTransNo(String sTransNo) throws RemoteException {
		TransCraftbrotherInfo retInfo = null;
		try{
			
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			retInfo = transDao.findByTransNo(sTransNo);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return retInfo;
	}
	/** ƥ����ҽ��� */
	public TransCraftbrotherInfo match(TransCraftbrotherInfo qInfo)throws RemoteException {
		TransCraftbrotherInfo rInfo = null;
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			rInfo = (TransCraftbrotherInfo)transDao.match(qInfo);
		}catch(Exception e){
			throw new RemoteException("Gen_E001",e);
		}
		return rInfo;
	}

	/**������˵�������Ӳ��ҽ���
	 * ����˵����
	 * @param lQueryPurpose 1:�޸����Ӳ��� 2:�������Ӳ���
	 * @param lStatusId ��ѯ״̬
	 * @param lUserId   ��ǰ��ѯ�û�
	 * @param nOrderByCode �����ֶ�����
	 * @param lIsDesc �Ƿ���
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection linkSearch(long lQueryPurpose,long lTransactionTypeId,long lStatusId, long lUserId,
			int nOrderIndex, boolean lIsDesc) throws RemoteException {
		
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			return transDao.linkSearch(lQueryPurpose,lTransactionTypeId, lStatusId, lUserId, nOrderIndex, lIsDesc);
		}catch(Exception e){
			throw new RemoteException(e.getMessage());
		}
	}

	/** ���׸��˽ӿ� */
	public long check(TransCraftbrotherInfo info) throws RemoteException,IRollbackException {
		long lReturn = -1;
		try{
			lReturn = this.check(SETTConstant.Actions.CHECK, info);
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ����ȡ�����˽ӿ� */
	public long cancelCheck(TransCraftbrotherInfo info) throws RemoteException,IRollbackException {
		long lReturn = -1;
		try{
			lReturn = this.check(SETTConstant.Actions.CANCELCHECK, info);
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ͬҵ���ύ����������ӿ� */
	public long saveInterestPreDraw(TransCraInterestPreDrawInfo info)
			throws RemoteException, IRollbackException {

		long lReturn = -1;
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		List draftList = null;
		DraftAmortizationInfo DraftInfo = null;
		try{
			//�������ݲ���DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			TransCraftbrotherDAO draftDao = new TransCraftbrotherDAO("DraftAmortization");
			draftDao.setUseMaxID();
			//���幤����
			UtilOperation utilOperation = new UtilOperation();
			String stransNo = info.getStransno();
			//��־λ���Ƿ����������׺�
			boolean bNewTransNo = false;
			if (stransNo == null || stransNo.equalsIgnoreCase(""))
			{ //δ��������������½��׺�
				bNewTransNo = true;
				//ͨ�����߲����ӿ����ȡ�½��׺�
				log.debug("------��ʼ��ȡ�½��׺�--------");
				stransNo = utilOperation.getNewTransactionNo(
								info.getNofficeid(),
								info.getNcurrencyid(),
								info.getNtransactiontypeid());
				info.setStransno(stransNo);
			}
			info.setNstatusid(SETTConstant.TransactionStatus.CHECK);
			//��һ�������ɼ��ύ��
			lReturn = transDao.add(info);
			//�ڶ���������Ʊ��̯����Ϣ
			
			draftList = info.getDraftList();
			if(draftList!=null)
			{
				Iterator it = draftList.iterator();
				while(it.hasNext())
				{
					DraftInfo = (DraftAmortizationInfo)it.next();
					DraftInfo.setAmortizationID(lReturn);
					DraftInfo.setNstatus(SETTConstant.TransactionStatus.SAVE);
					draftDao.add(DraftInfo);
				}
			}
			
			//�����������ɼ����Ʒ�¼
			if(lReturn > 0){
				/**
				 * ������Ʒ�¼:��¼����lEntryType =0 �޹أ�lAccountID1=�տ�˻���lAccountID2=����˻��� dAmount1=������
				 */
				GenerateGLEntryParam param = new GenerateGLEntryParam();
				/**
				 * �����/���������˻�ID>0���򱾽� ����lPrincipalType =2���У����򱾽�����lPrincipalType =1 �ڲ�ת��
				 */
				long lPrincipalType = -1;
				long lBankId = -1;
				
				//���������� "�޹�"
				lPrincipalType = SETTConstant.CapitalType.IRRESPECTIVE;
				
				//��¼���� �޹�
				long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
				//�տ�˻�
				long receiveAccountID = -1;
				//����˻�
				long payAccountID = -1;
				//���𿪻���ID
				long principalBankID = lBankId;
				//������
				double dAmount = info.getMpredrawinterest();
				param.setOfficeID(info.getNofficeid());
				param.setCurrencyID(info.getNcurrencyid());
				param.setTransactionTypeID(info.getNtransactiontypeid());
				param.setSubTransactionType(info.getNsubtransactiontypeid());
				//param.setExecuteDate(info.getDtinterestend());
				param.setExecuteDate(Env.getSystemDate(info.getNofficeid(),info.getNcurrencyid()));//ִ���գ����㿪����
				param.setInterestStartDate(DataFormat.getNextDate(info.getDtinterestend()));
				param.setTransNo(info.getStransno());
				param.setAbstractStr(info.getSabstract());
				param.setInputUserID(info.getNmakeuserid());
				param.setCheckUserID(info.getNmakeuserid());
				param.setPrincipalType(lPrincipalType);
				param.setEntryType(lEntryType);
				param.setReceiveAccountID(receiveAccountID);
				param.setPayAccountID(payAccountID);
				param.setPrincipalOrTransAmount(0.00);//����/���׽��
				param.setTotalInterest(info.getMpredrawinterest());//��Ϣ�ϼ�
				param.setTotalPrincipalAndInterest(dAmount);//��Ϣ�ϼ�
				param.setPrincipalBankID(principalBankID);
				
				param.setCraBusinessType(info.getNcrabusinesstypeid());
				param.setCounterpartId(info.getNcounterpartid());
				log.print("----------ACCOUNTBOOKEJB:checkCurrentAccountDetails�Ƿ���������ָ��:false"+"-----------");
				param.setAutoCreateBankInstruction(false);
				boolean res = glopOperation.generateGLEntry(param);
				if (!res)
				{
					throw new IRollbackException(mySessionCtx, "������Ʒ�¼����");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}

	/** ͬҵ���ύ��ɾ���ӿ� */
	public long deleteInterestPreDraw(TransCraInterestPreDrawInfo info) throws RemoteException,
			IRollbackException {
		long lReturn = info.getId();
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		String preTransNo = "";
		try{
			//�������ݲ���DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			//��һ����У��ҵ���߼�
			preTransNo = transDao.validateTransaction(info);
			if(!preTransNo.equals(""))
			{
				throw new Exception("����ɾ�����׺�Ϊ"+preTransNo+"�ļ����¼!");
			}
			//�ڶ�����ɾ�����ύ��
			TransCraInterestPreDrawInfo tmpinfo = new TransCraInterestPreDrawInfo();
			tmpinfo.setId(lReturn);
			tmpinfo.setNstatusid(SETTConstant.TransactionStatus.DELETED);
			transDao.update(tmpinfo);
			//��������ɾ��Ʊ��̯����¼
			transDao.updateDraftAmortization(lReturn);
			
			//���Ĳ���ɾ�������Ʒ�¼
			if(lReturn > 0){
				TransCraInterestPreDrawInfo infotmp = (TransCraInterestPreDrawInfo)transDao.findByID(lReturn, TransCraInterestPreDrawInfo.class);
				glopOperation.deleteGLEntry(infotmp.getStransno());
			}
				
		}catch(Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage(),e);
		}
		return lReturn;
	}


	/** �жϽ����Ƿ񱻷Ƿ��޸� */
	private boolean isTouch(TransCraftbrotherInfo info, TransCraftbrotherDAO dao)
			throws IException {
		boolean bReturn = false;
		try
		{
			//�ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate;
			TransCraftbrotherInfo lastinfo = null;
			lastinfo =(TransCraftbrotherInfo)dao.findByID(info.getId(), TransCraftbrotherInfo.class);
			lastTouchDate = lastinfo.getDtModify();
			Timestamp curTouchDate = info.getDtModify();
			if (curTouchDate == null || curTouchDate.compareTo(lastTouchDate) != 0)
				bReturn = true;
			else
				bReturn = false;
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001",e);
		}
		return bReturn;
	}

	/** �жϽ���״̬���ڵ�ǰ�����Ƿ�Ϸ� */
	private String checkStatus(long lActionID, TransCraftbrotherInfo info,TransCraftbrotherDAO dao) 
	       	throws IException {
		String errMsg = "";
		try
		{
			TransCraftbrotherInfo newInfo =(TransCraftbrotherInfo)dao.findByID(info.getId(), TransCraftbrotherInfo.class);
			
			errMsg =
				UtilOperation.checkStatus(
						info.getNstatusId(),
						newInfo.getNstatusId(),
						lActionID);
		}
		catch (Exception e)
		{
			throw new IException(errMsg,e);
		}
		return errMsg;
	}
	/**
	 * Method check
	 * @descriptin  �ڲ����������˺�ȡ������
	 * @param  checkOrCancelCheck ���˻���ȡ������
	 * @return long 
	 * @throws RemoteException��IException
	 */
	private long check(long checkOrCancelCheck,TransCraftbrotherInfo info)
										throws RemoteException, IRollbackException
	{
		long lReturn = info.getId();
		GeneralLedgerOperation glopOperation = new GeneralLedgerOperation();
		try{
			//�������ݲ���DAO
			TransCraftbrotherDAO transDao = new TransCraftbrotherDAO();
			
			//��һ������齻��״̬�Ƿ�Ϸ�����ʼ-----------------------------------------------------------------
			String errMsg = checkStatus(checkOrCancelCheck, info, transDao);
			if (errMsg != null && !errMsg.equals("")){
				throw new IException(errMsg);
			}
			//��һ��������-------------------------------------------------------------------------------------
			
			
			//�ڶ�������齻���Ƿ񱻷Ƿ��޸ģ���ʼ--------------------------------------------------------------
			if(isTouch(info, transDao)){
				if(checkOrCancelCheck == SETTConstant.Actions.CHECK){
					throw new IException("Sett_E020");
				}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
					throw new IException("Sett_E024");
				}
			}
			//�ڶ���������------------------------------------------------------------------------------------
			
			
			//���������˲�����Ʒ�¼����ʼ---------------------------------------------------------------------------
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){				
				
				accountBookOperation.checkTransCraftbrother(info);
				
			}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
				//ȡ�����ˣ�ɾ����Ʒ�¼
				//ɾ����Ʒ�¼
				//glopOperation.deleteGLEntry(info.getStransNo());
				accountBookOperation.cancelCheckTransCraftbrother(info);
			}
			//������������------------------------------------------------------------------------------------
			
			
			//���Ĳ������׸���:��ʼ----------------------------------------------------------------------------
			TransCraftbrotherInfo tmpInfo = new TransCraftbrotherInfo();
			tmpInfo.setId(lReturn);
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){
				//���ˣ����½���״̬���Ѹ��ˡ���д�븴����ID
				tmpInfo.setNcheckUserId(info.getNcheckUserId());
				tmpInfo.setDtModify(Env.getSystemDate());
				tmpInfo.setNstatusId(SETTConstant.TransactionStatus.CHECK);
				
			}else if(checkOrCancelCheck == SETTConstant.Actions.CANCELCHECK){
				//ȡ�����ˣ����½���״̬���ѱ��桱����ո�����ID
				tmpInfo.setNcheckUserId(-1);
				tmpInfo.setDtModify(Env.getSystemDateTime());
				tmpInfo.setNstatusId(SETTConstant.TransactionStatus.SAVE);
			}
			transDao.update(tmpInfo);
			//���Ĳ�������------------------------------------------------------------------------------------
			
			
			//���岽����������ָ���ʼ----------------------------------------------------------------
			if(checkOrCancelCheck == SETTConstant.Actions.CHECK){				
				if((info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_GRANT&&(info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.BREAK_INVEST||info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_INVEST))
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.TRANS_DISCOUNT_REPURCHASE&&info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.REPURCHASE_NOTIFY)
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN && info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_OUT)
						||(info.getNtransactionTypeId()==SETTConstant.TransactionType.FUND_ATTORN_REPAY&&info.getNsubTransactionTypeId()== SETTConstant.SubTransactionType.CAPITAL_IN)
						||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_GRANT&&(info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.BREAK_INVEST || info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_INVEST ))
						||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_RECEIVE&&info.getNsubTransactionTypeId() == SETTConstant.SubTransactionType.REPURCHASE_NOTIFY)
				        ||(info.getNtransactionTypeId() == SETTConstant.TransactionType.SAME_BUSINESS_INTERESTPROCESS))
				{
				log.info("--------------��ʼ��������ָ��--------------");
				
				/***********�������и���ָ��**********/
				//�Ƿ�������ӿ�
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//�Ƿ���Ҫ��������ָ��
				boolean bCreateInstruction = false;
				long bankID = info.getNbankId();
				try {
					//���ô˷�����bankID��ֵ��Ϊ��������ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("�жϴ��������ID�Ƿ���Ҫ��������ָ�����");
					e1.printStackTrace();
				}
				
				if(bIsValid && bCreateInstruction) {//������ӿڲ�������Ҫ��������ָ��
					Log.print("*******************��ʼ���������տ�ָ�������**************************");
					try {
						//�������
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getNtransactionTypeId());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getNofficeId());
						instructionParam.setCurrencyID(info.getNcurrencyId());
						instructionParam.setCheckUserID(info.getNcheckUserId());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getNinputUserId());
						//��������ָ�����
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------�������л���ָ�����--------");
						
					} catch (Throwable e) {
						log.error("�������и���ָ��ʧ��");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "�������и���ָ��ʧ�ܣ�"+e.getMessage());
					}
				}
				else {
					Log.print("û�����нӿڻ���Ҫ��������ָ�");
				}
				log.info("--------------������������ָ��--------------");
			}
			}
			
			//���岽������------------------------------------------------------------------------------------
		}catch(ITreasuryDAOException e){
			
			throw new IRollbackException(mySessionCtx,
					"SQLException in TransCraftbrotherEJB",e);
		}
		catch (Exception e)
		{
			throw new IRollbackException( mySessionCtx, e.getMessage(),e);
		}
		return lReturn;
	}
	/**ͬҵ��Ϣ����ӿ�*/
	public Collection calcInterest(CraInterestCalcInfo calcInfo) 
			throws RemoteException, IRollbackException{
		Collection coll = null;
		try{
			//�������ݲ���DAO
			TransCraInterestPreDrawDAO dao = new TransCraInterestPreDrawDAO();
			coll = dao.findCredencesByCondition(calcInfo);
			if(coll != null && coll.size() > 0){
				//��ʼ��Լ������ÿ��ƾ֤������Ϣ
				Iterator it = coll.iterator();
				//������ʱ����
				Timestamp startInterestDate = null;//���μ�����Ϣ��
				Timestamp endInterestDate = null;//���μ����Ϣ��
				Timestamp endCredenceDate = null;//ƾ֤������
				Timestamp repurchaseDate  = null;//��ͬ�ع�����
				Timestamp discountDate  = null;//ת��������
				boolean isLastAmortize = false;  //�Ƿ����һ��̯��
				boolean isNext = false;  //�������Ƿ�ָ����һ��
				
				double interest = 0.00;//����̯����Ϣ
				long days = 0;//����ʵ�ʼ�Ϣ����
			//	int i=0;
				while(it.hasNext()){
					isNext = true;

					isLastAmortize = false;
					CalcNoticeInfo info = (CalcNoticeInfo)it.next();
		        	
		        	if(info.getInorout() == LOANConstant.TransDiscountInOrOut.IN && info.getDiscountType() == LOANConstant.TransDiscountType.BUYBREAK){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_INVEST_BREAK);//ת�������루�ع���
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.IN && info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE);//ת�������루��ϣ�
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.OUT && info.getDiscountType() == LOANConstant.TransDiscountType.BUYBREAK){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_AVERAGE_BREAK);//ת������������ϣ�
		        	}else if(info.getInorout() == LOANConstant.TransDiscountInOrOut.OUT && info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){
		        		info.setCraTransTypeId(CRAconstant.TransactionType.ZTX_AVERAGE_REPURCHASE);//ת�����������ع���
		        	}
		        	startInterestDate = info.getInterestStartDate();//���μ�����Ϣ��
		        	endInterestDate = info.getInterestEndDate();//���μ����Ϣ��
		        	discountDate = info.getTransDate();//ת��������
		        	if(endInterestDate.compareTo(startInterestDate) < 0){//��ʼ���ڴ��ڽ������ڣ������¸�ƾ֤
	        			//interest = 0.00;
	        			//days = 0;
		        		if(isNext)
		        		{
		        			isNext = false;
		        			it.remove();
		        			continue;//������һ��ƾ֤
		        		}
	        		}
		        	
		        	if(info.getDiscountType() == LOANConstant.TransDiscountType.REPURCHASE){//���ع���ʽ
		        		repurchaseDate = info.getRepurchaseDate();
		        		repurchaseDate = DataFormat.getNextDate(repurchaseDate,-1);
		        		if(repurchaseDate.compareTo(startInterestDate) < 0){//�ع������ڱ���̯������֮ǰ
		        			//interest = 0.00;
		        			//days = 0;
		        			if(isNext)
		        			{
		        				isNext = false;
			        			it.remove();
			        			continue;//������һ��ƾ֤
		        			}
		        		}
		        		
		        		if(endInterestDate.compareTo(repurchaseDate) >= 0){//������ν�Ϣ��>=�ع����ڣ���Ϣ�ս�ֹΪ�ع�����
		        			endInterestDate = repurchaseDate;
		        			//���һ��̯��
		        			isLastAmortize = true;
		        		}
		        		//����ʵ��̯������
		        		days = DataFormat.getIntervalDays(startInterestDate, endInterestDate);
	        			//��ʼ����ƾ֤Ʊ��
	        			
	        			
	        			//long draftInterestDay = 0;  //ʵ�ʼ�Ϣ����
	        			long remainDay = 0;  //ʣ������
	        			long additionalDay = 0; //�����������ڼ���+�Ǳ��أ�
	        			double sumBillInterest = 0.00;  //̯������Ϣ����Ʊ��̯����Ϣ�ͣ�
	        			double draftInterest = 0.00;  //Ʊ����Ϣ
	        			double draftAmortizeInterest = 0.00;  //����Ʊ��̯����Ϣ
	        			double restAmortizeInterest = 0.00; //ʣ���̯�����
	        			double totalDraftInterest = 0.00;  //��Ϣ�ܶ�
	        			double sumDraftAmortizeInterest = 0.00;  //Ʊ����̯���ܶ�
	        			double sumNotDraftAmortizeInterest = 0.00;  //Ʊ��δ̯����Ϣ�ܶ�
	        			String draftMessage = "";  //Ʊ����Ϣ
		        		TransDiscountCredenceBiz crebiz = new TransDiscountCredenceBiz();
		        		Collection bills = crebiz.findBillByTransDiscountCredenceID(info.getId());
		        		if(bills !=null && bills.size() > 0){
		        			Iterator bIt = bills.iterator();
		        			while(bIt.hasNext()){
		        				additionalDay = 0;
		        				TransDiscountContractBillInfo bInfo = (TransDiscountContractBillInfo)bIt.next();
		        				additionalDay += bInfo.getAddDays();//+�ڼ�����������
		        				// �ع�ʽ�Ĳ��ӷǱ�������
		        				/*if(bInfo.getIsLocal()==Constant.YesOrNo.NO){//�Ǳ���+3��
		        					additionalDay += 3;//+�Ǳ���3
		        				}*/
		        				//����Ʊ�ݼ�Ϣ����
		        				//draftInterestDay = additionalDay + DataFormat.getIntervalDays(discountDate,repurchaseDate);
		        				
		        				//���㵥��Ʊ����Ϣ
		        				draftInterest = bInfo.getAccrual();
		        				//����ʣ���̯�����
		        				restAmortizeInterest = UtilOperation.Arith.sub(draftInterest, bInfo.getDraftAmortizeInterest());
		        				if(isLastAmortize)
		        				{
		        					draftAmortizeInterest = restAmortizeInterest;
		        					
		        				}else
		        				{
			        				//����̯��ʣ������
			        				remainDay = DataFormat.getIntervalDays(startInterestDate,repurchaseDate) + additionalDay;

			        				//���㵥��Ʊ��̯����Ϣ
			        				draftAmortizeInterest = restAmortizeInterest * days / remainDay;	
			        				//draftAmortizeInterest = DataFormat.roundDouble(draftAmortizeInterest, 2);
		        				}
		        				if(draftAmortizeInterest!=0.00)
		        				{
		        					draftMessage = draftMessage + bInfo.getId() + "@@" + String.valueOf(draftAmortizeInterest) + "&&";
		        				}
		        				//�����̯����Ϣ
		        				sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        				//������Ϣ�ܶ�
		        				totalDraftInterest = UtilOperation.Arith.add(totalDraftInterest,draftInterest);
		        				//������̯����Ϣ
		        				sumDraftAmortizeInterest = UtilOperation.Arith.add(sumDraftAmortizeInterest,bInfo.getDraftAmortizeInterest());
		        			}
		        		}
		        		
		        		interest = sumBillInterest;
		        		//����δ̯����Ϣ�ܶ�
		        		sumNotDraftAmortizeInterest = UtilOperation.Arith.sub(UtilOperation.Arith.sub(totalDraftInterest, sumDraftAmortizeInterest),interest);
		        		
		        		info.setDays(days);
		        		//����̯����Ϣ
		        		info.setInterest(interest);
		        		//��Ϣ�ܶ�
		        		info.setTotalInterest(totalDraftInterest);
		        		//��̯����Ϣ�ܶ�
		        		info.setSumAmortizeInterest(sumDraftAmortizeInterest);
		        		//δ̯����Ϣ�ܶ�
		        		info.setSumNotAmortizeInterest(sumNotDraftAmortizeInterest);
		        		
		        		info.setInterestEndDate(endInterestDate);
		        		if(draftMessage.length()>2)
		        		{
		        			info.setDraftMessage(draftMessage.substring(0, draftMessage.length()-2));
		        		}
		        	}else{//����ϣ�ʽ
		        		//��ʼ����ƾ֤Ʊ��

	        			double sumBillInterest = 0.00;
	        			Timestamp billEndDate = null;//Ʊ�ݵ�����
	        			long additionalDay = 0; //�����������ڼ���+�Ǳ��أ�
	        			//long draftInterestDay = 0; //ʵ�ʼ�Ϣ����
	        			long remainDay = 0;  //ʣ������
	        			
	        			double draftInterest = 0.00;  //Ʊ����Ϣ
	        			double restAmortizeInterest = 0.00; //ʣ���̯�����
	        			double draftAmortizeInterest = 0.00;  //����Ʊ��̯����Ϣ
	        			double totalDraftInterest = 0.00;  //��Ϣ�ܶ�
	        			double sumDraftAmortizeInterest = 0.00;  //��̯����Ϣ�ܶ�
	        			double sumNotDraftAmortizeInterest = 0.00; //δ̯����Ϣ�ܶ�
	        			//���ʽȡ�������
	        			Timestamp maxDate = null; //ȡ����ֹ��
	        			long maxDay = 0;  //ȡ�������
	        			boolean billNext = false;
	        			String draftMessage = "";
		        		TransDiscountCredenceBiz crebiz = new TransDiscountCredenceBiz();
		        		Collection bills = crebiz.findBillByTransDiscountCredenceID(info.getId());
		        		if(bills !=null && bills.size() > 0){
		        			Iterator bIt = bills.iterator();
		        			while(bIt.hasNext()){
		        				isLastAmortize = false;
		        				billNext = true;
		        				endInterestDate = info.getInterestEndDate();
		        				TransDiscountContractBillInfo bInfo = (TransDiscountContractBillInfo)bIt.next();
		        				additionalDay = 0;
		        				billEndDate = bInfo.getEnd();
		        				billEndDate = DataFormat.getNextDate(billEndDate,-1);
		        				
				        		if(endInterestDate.compareTo(billEndDate) >=0){//������ν�Ϣ��>=Ʊ�ݵ����գ���Ϣ�ս�ֹΪƱ�ݵ�����
				        			endInterestDate = billEndDate;
				        			//���һ��̯��
				        			isLastAmortize = true;				        			
				        		}
				        		//��ʼ����ʵ��̯������
				        		days = DataFormat.getIntervalDays(startInterestDate, endInterestDate);
				        		
				        		//�����������
			        			additionalDay += bInfo.getAddDays();//+�ڼ�����������
		        				if(bInfo.getIsLocal()==Constant.YesOrNo.NO){//�Ǳ���+3��
		        					additionalDay += 3;//+�Ǳ���3
		        				}

		        				//����Ʊ�ݼ�Ϣ����
		        				//draftInterestDay = additionalDay + DataFormat.getIntervalDays(discountDate,billEndDate);
			        				
		        				//���㵥��Ʊ����Ϣ
		        				//draftInterest = bInfo.getAmount()*info.getRate()*draftInterestDay/36000.00;
		        				if(info.getInorout()==LOANConstant.TransDiscountInOrOut.OUT)
		        				{
		        					draftInterest = bInfo.getBreaknotifyAccrual();
		        				}
		        				else
		        				{
		        					draftInterest = bInfo.getAccrual();
		        				}
		        				//����ʣ���̯�����
		        				restAmortizeInterest = UtilOperation.Arith.sub(draftInterest, bInfo.getDraftAmortizeInterest());
		        				if(isLastAmortize)
		        				{
		        					draftAmortizeInterest = restAmortizeInterest;
		        				}else
		        				{
			        				//����̯��ʣ������
			        				remainDay = DataFormat.getIntervalDays(startInterestDate,billEndDate) + additionalDay;		        
			        				
			        				//���㵥��Ʊ��̯����Ϣ
			        				draftAmortizeInterest = restAmortizeInterest * days / remainDay;	
			        				//draftAmortizeInterest = DataFormat.roundDouble(draftAmortizeInterest, 2);
			        				
		        				}
	       
		        				//sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        				totalDraftInterest = UtilOperation.Arith.add(totalDraftInterest,draftInterest);
		        				//������̯����Ϣ
		        				sumDraftAmortizeInterest = UtilOperation.Arith.add(sumDraftAmortizeInterest,bInfo.getDraftAmortizeInterest());		
		        				
		        				//ȡ����Ʊ���������
		        				maxDay = Math.max(days, maxDay);
		        				if(maxDate==null)
		        				{
		        					maxDate = endInterestDate;
		        				}
		        				else
		        				{
		        					if(maxDate.compareTo(endInterestDate)<0)
		        					{
		        						maxDate = endInterestDate;
		        					}
		        				}
		        				
		        				if(billEndDate.compareTo(startInterestDate) < 0){//Ʊ�ݵ��������ڱ��μ�����Ϣ��֮ǰ
				        			//interest = 0.00;
				        			//days = 0;
		        					if(billNext)
		        					{
		        						billNext = false;
		        						bIt.remove();
					        			continue;//��������Ʊ
		        					}
				        		}		  
		        				if(draftAmortizeInterest>0.00)
		        				{
		        					draftMessage = draftMessage + bInfo.getId() + "@@" + String.valueOf(draftAmortizeInterest) + "&&";
		        				}		        				
		        				sumBillInterest = UtilOperation.Arith.add(sumBillInterest, draftAmortizeInterest);
		        			}
		        			
		        			interest = sumBillInterest;//��Ʊ��̯����Ϣ֮��

		        		}
		        		
        				if(maxDate.compareTo(startInterestDate) < 0){//Ʊ�ݵ�������������ڱ��μ�����Ϣ��֮ǰ
		        			//interest = 0.00;
		        			//days = 0;
        					if(isNext)
        					{
        						isNext = false;
        						it.remove();
			        			continue;//������һ��ƾ֤
        					}
		        		}		        		
		        		
		        		//����δ̯����Ϣ�ܶ�
		        		sumNotDraftAmortizeInterest = UtilOperation.Arith.sub(UtilOperation.Arith.sub(totalDraftInterest, sumDraftAmortizeInterest),interest);
		        		
		        		info.setDays(maxDay);
		        		//����̯����Ϣ
		        		info.setInterest(interest);
		        		//��Ϣ�ܶ�
		        		info.setTotalInterest(totalDraftInterest);
		        		//��¼��̯����Ϣ�ܶ�
		        		info.setSumAmortizeInterest(sumDraftAmortizeInterest);
		        		
		        		info.setSumNotAmortizeInterest(sumNotDraftAmortizeInterest);
		        		
		        		info.setInterestEndDate(maxDate);
		        		if(draftMessage.length()>2)
		        		{
		        			info.setDraftMessage(draftMessage.substring(0, draftMessage.length()-2));
		        		}
		        	}

				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx,"Gen_E001",e);
		}
		return coll;
	}

	/** ����ͬҵ��Ϣ���ύ�ײ��ҽӿ� */
	public PageLoader searchTransInterestPerDraw(
			TransCraInterestPreDrawInfo info) throws RemoteException{
		try{
			//�������ݲ���DAO
			TransCraInterestPreDrawDAO transDao = new TransCraInterestPreDrawDAO();
			return transDao.searchTransInterestPerDraw(info);
		}catch(Exception e){
			throw new RemoteException(e.getMessage());
		}
	}
}
