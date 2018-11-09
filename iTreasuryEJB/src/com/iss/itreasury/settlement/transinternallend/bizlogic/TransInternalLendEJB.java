/**
 * created by kevin(������)2011-07-13
 */
package com.iss.itreasury.settlement.transinternallend.bizlogic;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;


import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.transinternallend.dao.Sett_TransInternalLendDAO;
import com.iss.itreasury.settlement.transinternallend.dataentity.QueryInternalLendConditionInfo;
import com.iss.itreasury.settlement.transinternallend.dataentity.TransInternalLendInfo;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.util.NameRef;


public class TransInternalLendEJB implements SessionBean {
	private javax.ejb.SessionContext mySessionCtx = null;
	private static  Object lockObj = new Object();  //��̬
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public void ejbActivate() throws EJBException, java.rmi.RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, java.rmi.RemoteException {
		// TODO Auto-generated method stub

	}
	
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{		
	}

	public void ejbRemove() throws EJBException, java.rmi.RemoteException{
		// TODO Auto-generated method stub

	}

	public void setSessionContext(SessionContext arg0) throws EJBException,
	java.rmi.RemoteException{
		// TODO Auto-generated method stub
		this.mySessionCtx =arg0;

	}
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * �ڲ����ҵ��-�ݴ�
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transTempSave(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			long infoId = -1;
			long lReturnID=-1;
			try
			{
				log.print("====ҵ������У��====");
				if (info.getId() > 0)
				{
					TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
					//���޸Ĺ�
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
				}
				log.print("====����ҵ������====");
				infoId = this.partSave(info, dao);			
				log.print("====���½���״̬Ϊ���ݴ�====");
				long tempID=dao.updateStatus(infoId, SETTConstant.TransactionStatus.TEMPSAVE);
				if(tempID>0){
					lReturnID=infoId;
				}else{
					throw new IRollbackException(mySessionCtx, "�ݴ�ʧ��");
				}					
				log.print("====�����ɹ�====");			
				
			}			
			catch (Exception e)
			{
				e.printStackTrace();				
				if(e instanceof IRollbackException){
					throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
				}else{
					throw new IRollbackException(mySessionCtx,e.getMessage());
				}
			}
		
			return lReturnID;
		}
	}	
	/**
	 * �ڲ����ҵ��-����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transSave(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
			long sessionID = -1;
			long lID = -1;
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			UtilOperation utilOperation = new UtilOperation();
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			try
			{
				//�Ա������˻�����
				sessionID = utilOperation.waitUtilSuccessLock(info.getReserveAccountID());
				log.print("====У��û����Ƿ�����ѱ�����ڲ����ҵ��====");
				String checkMsg=this.checkOthers(info, dao);
				if (checkMsg != null && !checkMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkMsg);
				}	
				String checkBalaceMsg=this.checkBalance(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,false);
				if (checkBalaceMsg != null && !checkBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkBalaceMsg);
				}	
				log.print("====��ʼ����====");
				String transNo = info.getTransNO();				
				if (transNo == null || transNo.equals(""))
				{
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransActionTypeID());
					info.setTransNO(transNo);
					log.print("====���ɽ��׺�Ϊ===="+transNo);
				}else{								
					log.print("====ҵ������У��====");
					TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (this.isTouch(info, dao))
					{
						log.debug("====���Ƿ��޸Ĺ�====");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
				}
				log.print("====����ҵ������====");
				lID = this.partSave(info, dao);
				info.setId(lID);
				log.debug("====�����˲���Ϣ====");
				accountBookOperation.saveInternalLend(info);
				log.debug("====���½���״̬Ϊ������====");
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				if(tempID>0)
				{
					lReturnID=lID;
				}else
				{
					throw new IRollbackException(mySessionCtx, "����ʧ��");
				}
				log.debug("====����������====");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				
			}
			finally
			{
				//�Ա������˻�����
				try
				{
					if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
						utilOperation.releaseLock(info.getReserveAccountID(), sessionID);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				}
			}
		
		 return lReturnID;
		}
	}
	/**
	 * ������޸���Ϣ����
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransInternalLendInfo info, Sett_TransInternalLendDAO dao) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		try
		{
			if (info.getId() > 0)
			{
				log.print("======�޸���Ϣ======");
				dao.update(info);
			}
			else
			{
				log.print("======������Ϣ======");
				dao.add(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info.getId();
		}
	}
	/**
	 * ����id��ѯ�ڲ����ҵ�����ϸ��Ϣ
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo FindTransInternalLendDetailByID(long id) throws RemoteException, IRollbackException
	{
		TransInternalLendInfo info = null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try
		{
			log.print("======ͨ��ID:"+id+"��ѯ�ڲ����ҵ�����ϸ��Ϣ======");
			info = dao.findByID(id);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	private boolean isTouch(TransInternalLendInfo info, Sett_TransInternalLendDAO dao) throws RemoteException, IRollbackException
	{
		try
		{
			log.debug("======�Ƿ��޸ģ�======");
			Timestamp lastTouchDate = dao.findByID(info.getId()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("���ݿ�ĵ�ǰʱ�䣺" + lastTouchDate.toString());
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
	 * ���Ӳ���
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try {
			coll = dao.findByStatus(queryInfo);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
		
	}
	/**
	 * ɾ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transDelete(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
			long lReturnID = -1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			try
			{
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.DELETED);
				if(tempID>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "ɾ��ʧ��");
				}
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
			
	}
	/**
	 * У��û����Ƿ���ڴ��ڱ���״̬�µ�ҵ������
	 * @param info
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private String checkOthers(TransInternalLendInfo info, Sett_TransInternalLendDAO dao) throws RemoteException, IRollbackException
	{
		Collection resultColl = null;
		Iterator itResult = null;
		String strMsg="";
		try
		{
			log.print("======У��û����Ƿ��������δ��ɵĴ�ҵ��======");
			resultColl = dao.findOtherByinfo(info);
			if(resultColl != null)
			{
				itResult = resultColl.iterator();
			}
			if(itResult != null && itResult.hasNext())
			{
				while(itResult.hasNext())
				{
					TransInternalLendInfo resultInfo = (TransInternalLendInfo)itResult.next();
					if(resultInfo.getStatusID()==SETTConstant.TransactionStatus.SAVE)
					{
						log.print("======�Ѵ���δ��ɵ�======");
						strMsg="�û����Ѵ���δ��ɵ�"+SETTConstant.TransactionType.getName(info.getTransActionTypeID())+"ҵ���봦��";
					}
				}
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return strMsg;
	}
	/**
	 * У���˻������ҳ�洫�ݹ���������Ƿ�һ��
	 * @param dAccBalance
	 * @param lAccountID
	 * @param dao
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	private String checkBalance(double dAccBalance,long lAccountID, Sett_TransInternalLendDAO dao,boolean isAvailable) throws RemoteException, IRollbackException
	{
		String strMsg="";		
		try
		{
			log.debug("======У���˻�����Ƿ����䶯======");
			double dNewAccBalance = DataFormat.formatDouble(dao.getBalanceByAccountID(lAccountID,isAvailable),2);		
			double dformatAccBalance=DataFormat.formatDouble(dAccBalance,2);
			String strAccountNo=NameRef.getAccountNoByID(lAccountID);			
			if(dformatAccBalance!=dNewAccBalance)
			{
				log.print("======�˻� "+strAccountNo+" �������˱䶯======");
				log.print("======dNewAccBalance======"+dNewAccBalance);
				log.print("======dAccBalance======"+dformatAccBalance);				
				strMsg="�˻� "+strAccountNo+" �������˱䶯����˲�";
			}						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return strMsg;
	}	
	/**
	 * �ڲ����-ƥ�����
	 * @param conditonInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransInternalLendInfo match(TransInternalLendInfo conditonInfo) throws RemoteException, IRollbackException
	{
		TransInternalLendInfo info=null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try
		{
			log.print("======�ڲ����-ƥ��======");
			info = dao.match(conditonInfo);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}		
		return info;		
	}
	/**
	 * �ڲ����-���˲���
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long check(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();			
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			TransInternalLendInfo transInfo=new TransInternalLendInfo();
			try
			{
				log.print("====��ʼ�ڲ����-���˲���====");
				String checkBalaceMsg=this.checkBalance(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,false);
				if (checkBalaceMsg != null && !checkBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkBalaceMsg);
				}
				log.print("====ҵ������У��====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}	
				this.partSave(info,dao);
				transInfo = dao.findByID(info.getId());		//�������ݿ��е�������¼
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====�˲�����====");
				accountBookOperation.checkInternalLend(transInfo);				
				log.print("====���½���״̬Ϊ���Ѹ���====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());			
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "����ʧ��");
				}
				log.print("====�����ڲ����-���˲���====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
			
	}
	/**
	 * �ڲ����-ȡ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long cancelCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();			
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			TransInternalLendInfo transInfo=new TransInternalLendInfo();
			try
			{
				log.print("====��ʼ�ڲ����-ȡ�����˲���====");
				String checkLendBalaceMsg=this.isOver(info.getAmount(), info.getLendingAccountID(), dao,false);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "ȡ������ʧ�ܣ�"+checkLendBalaceMsg);
				}		
				TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//�������ݿ��е�������¼
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====�˲�����====");
				accountBookOperation.cancelCheckInternalLend(transInfo);				
				log.print("====���½���״̬Ϊ���ѱ���====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "ȡ������ʧ��");
				}
				log.print("====�����ڲ����-ȡ�����˲���====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}		
			
	}
	/**
	 * �ڲ�����ջ�-�ݴ�
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentTempSave(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			long infoId = -1;
			long lReturnID=-1;
			try
			{
				log.print("====ҵ������У��====");
				if (info.getId() > 0)
				{
					TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
					//���޸Ĺ�
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
				}
				log.print("====����ҵ������====");
				infoId = this.partSave(info, dao);			
				log.print("====���½���״̬Ϊ���ݴ�====");
				long tempID=dao.updateStatus(infoId, SETTConstant.TransactionStatus.TEMPSAVE);
				if(tempID>0){
					lReturnID=infoId;
				}else{
					throw new IRollbackException(mySessionCtx, "�ݴ�ʧ��");
				}					
				log.print("====�����ɹ�====");			
				
			}			
			catch (Exception e)
			{
				e.printStackTrace();				
				if(e instanceof IRollbackException){
					throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());
				}else{
					throw new IRollbackException(mySessionCtx,e.getMessage());
				}
			}
		
			return lReturnID;
		}
	}
	/**
	 * �ڲ�����ջ�-����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long transRepaymentSave(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
			long sessionID = -1;
			long lID = -1;
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			UtilOperation utilOperation = new UtilOperation();
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			try
			{
				//�Ա������˻�����
				sessionID = utilOperation.waitUtilSuccessLock(info.getReserveAccountID());
				log.print("====У��û����Ƿ�����ѱ�����ڲ�����ջ�ҵ��====");
				String checkMsg=this.checkOthers(info, dao);
				if (checkMsg != null && !checkMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkMsg);
				}	
				String checkLendBalaceMsg=this.isOver(info.getLendingAccountBalance(), info.getLendingAccountID(), dao,true);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkLendBalaceMsg);
				}
				String checkReserveBalaceMsg=this.isOver(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,true);
				if (checkReserveBalaceMsg != null && !checkReserveBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkReserveBalaceMsg);
				}
				log.print("====��ʼ����====");
				String transNo = info.getTransNO();				
				if (transNo == null || transNo.equals(""))
				{
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransActionTypeID());
					info.setTransNO(transNo);
					log.print("====���ɽ��׺�Ϊ===="+transNo);
				}else{								
					log.print("====ҵ������У��====");					
					TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (this.isTouch(info, dao))
					{
						log.debug("====���Ƿ��޸Ĺ�====");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					log.debug("====ɾ�����е��˲���Ϣ====");
					TransInternalLendInfo oldTransInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());;
					if (oldTransInfo == null)
					{
						log.debug("====�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��====");
						throw new IRollbackException(mySessionCtx, "�޷��ҵ����׶�Ӧ���˻���Ϣ������ʧ��");
					}
					accountBookOperation.deleteInternalLendRepayment(oldTransInfo);
				}
				log.print("====����ҵ������====");
				lID = this.partSave(info, dao);
				info.setId(lID);
				log.debug("====�����˲���Ϣ====");
				accountBookOperation.saveInternalLendRepayment(info);
				log.debug("====���½���״̬Ϊ������====");
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				if(tempID>0)
				{
					lReturnID=lID;
				}else
				{
					throw new IRollbackException(mySessionCtx, "����ʧ��");
				}
				log.debug("====����������====");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				
			}
			finally
			{
				//�Ա������˻�����
				try
				{
					if (sessionID != -1) //��ʼֵ�Ѿ��ı䣬˵���Ѿ������������Ҫ����
						utilOperation.releaseLock(info.getReserveAccountID(), sessionID);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				}
			}
		
		 return lReturnID;
		}
	}
	/**
	 * �ڲ�����ջ�ҵ����/ҵ�񸴺����Ӳ���
	 * @param queryInfo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Collection transRepaymentFindByStatus(QueryInternalLendConditionInfo queryInfo) throws RemoteException, IRollbackException
	{
		Collection coll = null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try {
			coll = dao.findByStatus(queryInfo);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return coll;
	}
	/**
	 * ͨ��id��ѯ�ڲ�����ջ���ϸ��Ϣ
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	
	public TransInternalLendInfo FindTransInternalLendRepaymentDetailByID(long id) throws RemoteException, IRollbackException
	{
		TransInternalLendInfo info = null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try
		{
			log.print("======ͨ��ID:"+id+"��ѯ�ڲ�����ջ�ҵ�����ϸ��Ϣ======");
			info = dao.findByID(id);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	/**
	 * �ڲ�����ջ�-ɾ��
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */	
	public long transRepaymentDelete(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
			long lReturnID = -1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
			TransInternalLendInfo repaymentInfo=new TransInternalLendInfo();
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			try
			{
				TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				if(info.getStatusID()== SETTConstant.TransactionStatus.SAVE)
				{
					repaymentInfo = dao.findByID(info.getId());		//�������ݿ��е�������¼
					accountBookOperation.deleteInternalLendRepayment(repaymentInfo);
				}
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.DELETED);
				if(tempID>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "ɾ��ʧ��");
				}
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
			
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
		TransInternalLendInfo info=null;
		Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();
		try
		{
			log.print("======�ڲ�����ջ�-ƥ��======");
			info = dao.match(conditonInfo);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}		
		return info;
	}
	/**
	 * �ڲ�����ջ�-����
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();			
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			TransInternalLendInfo transInfo=new TransInternalLendInfo();
			try
			{
				log.print("====��ʼ�ڲ�����ջ�-���˲���====");
				String checkLendBalaceMsg=this.isOver(info.getAmount(), info.getLendingAccountID(), dao,true);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkLendBalaceMsg);
				}		
				String checkReserveBalaceMsg=this.isOver(info.getAmount(), info.getReserveAccountID(), dao,true);
				if (checkReserveBalaceMsg != null && !checkReserveBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "����ʧ�ܣ�"+checkReserveBalaceMsg);
				}
				log.print("====ҵ������У��====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//�������ݿ��е�������¼
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====�˲�����====");				
				accountBookOperation.checkInternalLendRepayment(transInfo);				
				log.print("====���½���״̬Ϊ���Ѹ���====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());			
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "����ʧ��");
				}
				log.print("====�����ڲ�����ջ�-���˲���====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
	}
	
	/**
	 * �ڲ�����ջ�-ȡ������
	 * @param info
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long repaymentCancelCheck(TransInternalLendInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){			
			long lReturnID=-1;
			Sett_TransInternalLendDAO dao = new Sett_TransInternalLendDAO();			
			AccountBookOperation accountBookOperation = new AccountBookOperation();
			TransInternalLendInfo transInfo=new TransInternalLendInfo();
			try
			{
				log.print("====��ʼ�ڲ�����ջ�-ȡ�����˲���====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====���Ƿ��޸Ĺ�====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//�������ݿ��е�������¼
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====������====");
				accountBookOperation.cancelCheckInternalLendRepayment(transInfo);				
				log.print("====���½���״̬Ϊ���ѱ���====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "ȡ������ʧ��");
				}
				log.print("====�����ڲ�����ջ�-ȡ�����˲���====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}		
	}
	private String isOver(double dAmount,long lAccountID, Sett_TransInternalLendDAO dao,boolean isAvailable) throws RemoteException, IRollbackException
	{
		String strMsg="";		
		try
		{
			log.debug("======У���˻��Ƿ���͸֧======");
			double dNewAccBalance = DataFormat.formatDouble(dao.getBalanceByAccountID(lAccountID,isAvailable),2);		
			double dformatAmount=DataFormat.formatDouble(dAmount,2);
			String strAccountNo=NameRef.getAccountNoByID(lAccountID);
			if(isAvailable){
				log.print("======�˻� "+strAccountNo+"���У��======");
				if(dNewAccBalance<0){
					log.print("======�˻� "+strAccountNo+" ����======");
					log.print("======dNewAccBalance======"+dNewAccBalance);
					log.print("======dAmount======"+dformatAmount);				
					strMsg="�˻� "+strAccountNo+" ���㣬��˲�";
				}
			}else{
				if(dNewAccBalance-dformatAmount<0)
				{
					log.print("======�˻� "+strAccountNo+" ����======");
					log.print("======dNewAccBalance======"+dNewAccBalance);
					log.print("======dAmount======"+dformatAmount);				
					strMsg="�˻� "+strAccountNo+" ���㣬��˲�";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return strMsg;
	}

}
