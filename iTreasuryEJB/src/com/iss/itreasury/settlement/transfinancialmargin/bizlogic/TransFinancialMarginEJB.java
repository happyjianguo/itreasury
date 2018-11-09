package com.iss.itreasury.settlement.transfinancialmargin.bizlogic;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transfinancialmargin.dao.TransfinancialMarginDao;
import com.iss.itreasury.settlement.transfinancialmargin.dataentity.TransFinancialMarginInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

public class TransFinancialMarginEJB implements SessionBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6436472385973646845L;
	private javax.ejb.SessionContext mySessionCtx = null;
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
	
	/**
	 * ��֤�𱣺��� �ݴ�
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawTempSave(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		
		try
		{
			if (info.getId() <= 0) //����
			{
				TransFinancialMarginInfo  rInfo = new TransFinancialMarginInfo(); 
				rInfo = dao.findIDByCondition(info.getNContractID(), info.getNAccountID(), info.getNLoanNoticeID());
				if(rInfo.getId() < 0 ){
					dao.setUseMaxID();
					info.setNStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
					//add by zwxiao 2010-08-20 ֮ǰû�б�����Ϣ��
					info.setDtInterestStartDate(info.getDtExecute());
					lReturn = dao.add(info);
				}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){
					info.setId(rInfo.getId());
					dao.update(info);
				}else{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			else //�޸��ݴ�
			{
				TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
				if(newInfo.getNStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){
					dao.update(info);
				}else{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
	}
	/**
	 * ��֤�𱣻����� ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawSave(TransFinancialMarginInfo info) throws IRollbackException,RemoteException{
		long lReturn = -1;
		long sessionID = -1;
		//���ݷ��ʶ���
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//�˻������ӿ���
		AccountOperation acctOperation = new AccountOperation();
		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			TransFinancialMarginInfo  rInfo = new TransFinancialMarginInfo(); 
			rInfo = dao.findIDByCondition(info.getNContractID(), info.getNAccountID(), info.getNLoanNoticeID());
			
			if(rInfo.getId() < 0 ){//���ݿ���û�� ��������
					//δ��������������½��׺�
					//ͨ�����߲����ӿ����ȡ�½��׺�					
					String transNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getTypeID());
					info.setSTransNo(transNo);
					//����					
					dao.setUseMaxID();
					//add by zwxiao 2010-08-20 ֮ǰû�б�����Ϣ��
					info.setDtInterestStartDate(info.getDtExecute());
					lReturn = dao.add(info);
					// ��3�����÷��������в�����
					accountBookOperation.saveWithFinancialMargin(info);
					if (!dao.checkIsUsed(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						throw new IRollbackException(mySessionCtx, "�������ޱ�֤�𱣺���֪ͨ�����Ѿ���ʹ��");
					}
					dao.updaterecognizancenoticeformStatus(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					//�޸Ľ��׵�״̬Ϊ���档
					lReturn = dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				
			}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){ //���ݿ����У��������ݴ�,���ݴ��Ϊ���� 
					
				    //�ж�״̬�Ƿ�Ϸ�
					long lNewStatusID = rInfo.getNStatusID();
					String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//���޸Ĺ�
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//δ�Ƿ��޸�
					
					//ͨ�����߲����ӿ����ȡ�½��׺�
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getTypeID());
					info.setSTransNo(transNo);
					info.setId(rInfo.getId());
					info.setNStatusID(SETTConstant.TransactionStatus.SAVE);
					
					if (!dao.checkIsUsed(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						throw new IRollbackException(mySessionCtx, "�������ޱ�֤�𱣺���֪ͨ�����Ѿ���ʹ��");
					}
					dao.updaterecognizancenoticeformStatus(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					//�����������˻����
					accountBookOperation.saveWithFinancialMargin(info);
					//�޸�
					dao.update(info);

			}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.SAVE)     //���ݿ����У��Ǳ���,���� �����ٱ���
			{                                                                    
					//�ж�״̬�Ƿ�Ϸ�
					long lNewStatusID = rInfo.getNStatusID();
					String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//���޸Ĺ�
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(rInfo.getId(),TransFinancialMarginInfo.class);							
					//������ɾ�����ݿ��е����еĴ浥
					accountBookOperation.deleteWithFinancialMargin(newInfo);
					
					info.setId(newInfo.getId());
					info.setSTransNo(newInfo.getSTransNo());
				    info.setNStatusID(SETTConstant.TransactionStatus.SAVE);
					dao.update(info);
					accountBookOperation.saveWithFinancialMargin(info);
				}else{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
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
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getNAccountID(), sessionID);
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
	 * ƥ��
	 * @param info
	 * @param typeFlag
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection drawMatch(TransFinancialMarginInfo info,long typeFlag) throws IRollbackException, RemoteException{
		
		Collection coll = null;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		try
		{
			coll = dao.match(info,typeFlag);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	
	/**
	 * ����ID����
	 * @param ID
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public TransFinancialMarginInfo drawFindByID(long ID) throws IRollbackException, RemoteException{
		TransFinancialMarginInfo info = new TransFinancialMarginInfo();
		
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		try
		{
			info = (TransFinancialMarginInfo)dao.findByID(ID,TransFinancialMarginInfo.class);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	
	/**
	 * ���ݽ��׺Ų���(TransNO)
	 * @param ID
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public TransFinancialMarginInfo findByTransNO(String strTransNO) throws IRollbackException, RemoteException{
		TransFinancialMarginInfo info = new TransFinancialMarginInfo();
		
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		try
		{
			info = dao.findByTransNO(strTransNO);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}
	/**
	 * ����
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCheck(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//����ʱ�ж�״̬IDΪδ���ˣ����Բ����жϣ���Ϊƥ���ʱ���Ѿ��ж��ˣ�
				//����������ſ��˻���
				accountBookOperation.checkWithFinancialMargin(info);
				newInfo.setNCheckUserID(info.getNCheckUserID());
				newInfo.setNStatusID(SETTConstant.TransactionStatus.CHECK);
				newInfo.setDtCheck(info.getDtCheck());
				newInfo.setSCheckAbstract(info.getSCheckAbstract());
				dao.update(newInfo);
		}
		catch (Exception e)
		{
					throw new IRollbackException(mySessionCtx, "��������ת��ָ�����" + e.getMessage());
		}
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getNAccountID(), sessionID);
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
	 * ȡ������
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCancelCheck(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//����ʱʹ��		
		long sessionID = -1;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//ȡ������
				accountBookOperation.cancelCheckWithFinancialMargin(info);
				//�޸�
				newInfo.setNStatusID(SETTConstant.TransactionStatus.SAVE);
				newInfo.setSCheckAbstract(info.getSCheckAbstract());
				dao.update(newInfo);
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
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
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getNAccountID(), sessionID);
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
	 * ҵ�񸴺ˣ���״̬��ѯ
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection drawFindByStatus4Check(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException
	{
		Collection coll = null;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();

		try
		{
			coll = dao.findByStatus4Check(info,lStatusIDs);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	/**
	 * ҵ������״̬��ѯ
	 * @param info
	 * @param lStatusIDs
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public Collection drawFindByStatus4Deal(TransFinancialMarginInfo info,long[] lStatusIDs) throws IRollbackException, RemoteException
	{
		Collection coll = null;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();

		try
		{
			coll = dao.findByStatus4Deal(info,lStatusIDs);
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	/**
	 * ȡ������
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCancel(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//����ʱʹ��		
		long sessionID = -1;

		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//�ж�״̬�Ƿ�Ϸ�
			log.debug("----------�ж�״̬�Ƿ񱻷Ƿ��޸Ĺ�-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();			
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//���޸Ĺ�
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//�ж��Ƿ��ݴ�
				if (info.getSTransNo() == null || info.getSTransNo().equals(""))
				{
					lReturn = dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.DELETED);
				}
				else
				{
					TransFinancialMarginInfo delInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
					accountBookOperation.deleteWithFinancialMargin(delInfo);
					lReturn = dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.DELETED);
				}
				if (info.getNStatusID()== SETTConstant.TransactionStatus.SAVE){		//����Ǳ��棬���ķſ�֪ͨ��״̬,������ݴ治��
					TransFinancialMarginInfo changeInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
					dao.updaterecognizancenoticeformStatus(changeInfo.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK);
				}
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IRollbackException e)
		{
			throw e;
		}
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
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getNAccountID(), sessionID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		return lReturn;
	}
}
