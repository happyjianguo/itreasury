/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfee.bizlogic;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.SessionBean;

import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transfee.dao.Sett_TransFeeDAO;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransFeeEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private static  Object lockObj = new Object();  //��̬
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
	 * Method preSave.
	 * @param info
	 * @return int 0: ���� 1: ί��ƾ֤������--��ʾ���� 2: Ʊ�ݲ����� 3: �ظ����׼�¼ 4: �˻�͸֧
	 * @throws RemoteException
	 */
	public long preSave(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		TransFeeInfo queryInfo = info.getQeureyInfo();

		Collection c = this.findByConditions(queryInfo, -1, false);
		if (c != null && c.size() > 0)
		{
			if (c.size() == 1)
			{
				TransFeeInfo tmpInfo = (TransFeeInfo) ((ArrayList) c).get(0);
				if (tmpInfo.getID() == info.getID())
					return SETTConstant.PreSaveResult.NORMAL;
			}
			return SETTConstant.PreSaveResult.REPEATED;
		}

		return SETTConstant.PreSaveResult.NORMAL;
		}
	}

	/**
	 * Method tempSave.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long tempSave(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		long depositId = -1;

		try
		{
			//����״̬�����ݴ�
			info.setStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("------��ʼ��tempSave--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------������tempSave--------");			
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
		return depositId;
		}
	}

	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long save(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		long depositId = -1;
		//Sett_TransGeneralLedger���ݷ��ʶ���
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());
			//��ȡ��ǰ�Ľ��׺�
			String transNo = info.getTransNo();
			//��־λ���Ƿ����������׺�
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{ //δ��������������½��׺�
				bNewTransNo = true;
				//ͨ�����߲����ӿ����ȡ�½��׺�
				log.debug("------��ʼ��ȡ�½��׺�--------");
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				log.debug("------�½��׺���:" + transNo + "--------");
			}
			else
			{
				//��������� �������ٱ���
				//�ж��Ƿ񱻷Ƿ��޸Ĺ�
				log.debug("------��ʼ�ж��Ƿ񱻷Ƿ��޸Ĺ�--------");

				//У��״̬�Ƿ���ȷ
				TransFeeInfo newInfo = this.findByID(info.getID());
				String errMsg =
					UtilOperation.checkStatus(
						info.getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.MODIFYSAVE);
				//���޸Ĺ�
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					log.debug("------���Ƿ��޸Ĺ�--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//ɾ���˲���Ϣ
					log.debug("------��ʼɾ���˲���Ϣ--------");
					TransFeeInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					log.debug(oldTransInfo.toString());
					log.debug(newInfo.toString());
					accountBookOperation.deleteTransFee(oldTransInfo);
					log.debug("------����ɾ���˲���Ϣ--------");
				}
			}
			log.debug("------��ʼ��Save--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------������Save--------");
			info.setID(depositId);

			//�����˲���Ϣ	
			log.debug("------��ʼ�����˲���Ϣ--------");
			accountBookOperation.saveTransFee(info);
			log.debug("------���������˲���Ϣ����ʼ����״̬��δ����--------");
			//����״̬����2���棨δ���ˣ�
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------����״̬��δ���˳ɹ�--------");
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
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getRelatedAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}

		return depositId;
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException
	 */
	public long delete(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		//Sett_TransCurrentDeposit���ݷ��ʶ���
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//�Կͻ�����			
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//У��״̬�Ƿ���ȷ
			TransFeeInfo newInfo = this.findByID(info.getID());
			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//�ж��Ƿ��޸Ĺ�			
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//ɾ�����׼�¼
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					accountBookOperation.deleteTransFee(newInfo);
				}
			}
			//����ID
			return dao.delete(info.getID());
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
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getRelatedAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long check(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//У��״̬�Ƿ���ȷ
			TransFeeInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//�ж��Ƿ��޸�
			if (isTouch(info, dao))
				throw new IRollbackException(mySessionCtx, "Sett_E020");

			dao.update(info);

			//���˽��׼�¼
			log.info("--------------��ʼAccountBook���˽��׼�¼--------------");
			
			info.setStatusID(SETTConstant.TransactionStatus.CHECK);
			
			//��ʼ���±�sett_glentry
			log.info("=========��ʼ���±�sett_glEntry");
			accountBookOperation.checkTransFee(info);

			//info.setStatusID(SETTConstant.TransactionStatus.CHECK);
			
			//��ʼ���±�SETT_TRANSFEE
			log.info("=========��ʼ���±�SETT_TRANSFEE");
			dao.update(info);
			log.info("--------------����AccountBook���˽��׼�¼--------------");

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
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getRelatedAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}

		return info.getID();
		}
	}

	/**
	 * Method delete.
	 * @param info
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//���߲����ӿ���
		UtilOperation utilOperation = new UtilOperation();
		//�˲������ӿ��� 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();

		try
		{
			//�Կͻ�����
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//У��״̬�Ƿ���ȷ
			TransFeeInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//�ж��Ƿ��޸�
			if (isTouch(info, dao))
				throw new IRollbackException(mySessionCtx, "Sett_E024");

			//ȡ�����˽��׼�¼
			log.info("--------------��ʼȡ��AccountBook���˽��׼�¼--------------");
			accountBookOperation.cancelCheckTransFee(info);

			//����״̬����δ����
			info.setStatusID(SETTConstant.TransactionStatus.SAVE);
			dao.update(info);

			log.info("--------------����ȡ��AccountBook���˽��׼�¼--------------");

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
		finally
		{
			//����
			try
			{
				if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
					utilOperation.releaseLock(info.getRelatedAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, "@TBD:Error Code--", e);
			}
		}
		return info.getID();
		}
	}

	public Collection findByConditions(TransFeeInfo info, int orderByType, boolean isDesc)
		throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger���ݷ��ʶ���
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		try
		{
			return dao.findByConditions(info, orderByType, isDesc);
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
	}

	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransFeeInfo match(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger���ݷ��ʶ���
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		try
		{
			return dao.match(info);
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
	}

	/**
	 * ����˵���������˻�ID���õ��˻���Ϣ
	 * @param transCurrentDepositID
	 * @return Sett_TransFeeInfo
	 * @throws IRollbackException
	 */
	public TransFeeInfo findByID(long lID) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger���ݷ��ʶ���
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		try
		{
			return dao.findByID(lID);
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
	}

	private boolean isTouch(TransFeeInfo info, Sett_TransFeeDAO dao)
		throws RemoteException, IRollbackException
	{
		try
		{
			//�ж��Ƿ񱻷Ƿ��޸Ĺ�
			Timestamp lastTouchDate;
			lastTouchDate = dao.findTouchDate(info.getID());

			//@TBD: get touch date from info class
			Timestamp curTouchDate = info.getModifyDate();
			if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) != 0)
				return true;
			else
				return false;
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
			throw new IRollbackException(mySessionCtx, "SQLException in TransCurrentDepositEJB", e);
		}

	}
}
