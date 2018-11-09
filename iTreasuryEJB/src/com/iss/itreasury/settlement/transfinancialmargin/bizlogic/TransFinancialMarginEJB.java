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
	 * @exception RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception RemoteException 异常说明。
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
	 * @exception RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		mySessionCtx = ctx;
	}
	
	/**
	 * 保证金保后处理 暂存
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
			if (info.getId() <= 0) //新增
			{
				TransFinancialMarginInfo  rInfo = new TransFinancialMarginInfo(); 
				rInfo = dao.findIDByCondition(info.getNContractID(), info.getNAccountID(), info.getNLoanNoticeID());
				if(rInfo.getId() < 0 ){
					dao.setUseMaxID();
					info.setNStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
					//add by zwxiao 2010-08-20 之前没有保存起息日
					info.setDtInterestStartDate(info.getDtExecute());
					lReturn = dao.add(info);
				}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){
					info.setId(rInfo.getId());
					dao.update(info);
				}else{
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			else //修改暂存
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
	 * 保证金保护处理 保存
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawSave(TransFinancialMarginInfo info) throws IRollbackException,RemoteException{
		long lReturn = -1;
		long sessionID = -1;
		//数据访问对象
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			TransFinancialMarginInfo  rInfo = new TransFinancialMarginInfo(); 
			rInfo = dao.findIDByCondition(info.getNContractID(), info.getNAccountID(), info.getNLoanNoticeID());
			
			if(rInfo.getId() < 0 ){//数据库中没有 新增保存
					//未被保存过，生成新交易号
					//通过工具操作接口类获取新交易号					
					String transNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getTypeID());
					info.setSTransNo(transNo);
					//保存					
					dao.setUseMaxID();
					//add by zwxiao 2010-08-20 之前没有保存起息日
					info.setDtInterestStartDate(info.getDtExecute());
					lReturn = dao.add(info);
					// （3）调用方法：进行财务处理。
					accountBookOperation.saveWithFinancialMargin(info);
					if (!dao.checkIsUsed(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						throw new IRollbackException(mySessionCtx, "融资租赁保证金保后处理通知单号已经被使用");
					}
					dao.updaterecognizancenoticeformStatus(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					//修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				
			}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.TEMPSAVE){ //数据库中有，但是是暂存,将暂存改为保存 
					
				    //判断状态是否合法
					long lNewStatusID = rInfo.getNStatusID();
					String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					//未非法修改
					
					//通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getNOfficeID(), info.getNCurrencyID(),info.getTypeID());
					info.setSTransNo(transNo);
					info.setId(rInfo.getId());
					info.setNStatusID(SETTConstant.TransactionStatus.SAVE);
					
					if (!dao.checkIsUsed(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.CHECK))
					{
						throw new IRollbackException(mySessionCtx, "融资租赁保证金保后处理通知单号已经被使用");
					}
					dao.updaterecognizancenoticeformStatus(info.getNLoanNoticeID(), LOANConstant.LoanPayNoticeStatus.USED);
					//财务处理，设置账户余额
					accountBookOperation.saveWithFinancialMargin(info);
					//修改
					dao.update(info);

			}else if(rInfo.getNStatusID() == SETTConstant.TransactionStatus.SAVE)     //数据库中有，是保存,即是 保存再保存
			{                                                                    
					//判断状态是否合法
					long lNewStatusID = rInfo.getNStatusID();
					String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
					//被修改过
					if(errMsg !=null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx,errMsg);
					}
					TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(rInfo.getId(),TransFinancialMarginInfo.class);							
					//财务处理，删除数据库中的已有的存单
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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 匹配
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
	 * 根据ID查找
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
	 * 根据交易号查找(TransNO)
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
	 * 复核
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCheck(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				//财务操作，放开账户余额，
				accountBookOperation.checkWithFinancialMargin(info);
				newInfo.setNCheckUserID(info.getNCheckUserID());
				newInfo.setNStatusID(SETTConstant.TransactionStatus.CHECK);
				newInfo.setDtCheck(info.getDtCheck());
				newInfo.setSCheckAbstract(info.getSCheckAbstract());
				dao.update(newInfo);
		}
		catch (Exception e)
		{
					throw new IRollbackException(mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
		}
		finally
		{
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 取消复核
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCancelCheck(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;
		//加锁时使用		
		long sessionID = -1;
		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//取消复核
				accountBookOperation.cancelCheckWithFinancialMargin(info);
				//修改
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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 业务复核，按状态查询
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
	 * 业务处理，按状态查询
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
	 * 取消保存
	 * @param info
	 * @return
	 * @throws IRollbackException
	 * @throws RemoteException
	 */
	public long drawCancel(TransFinancialMarginInfo info) throws IRollbackException,RemoteException
	{
		long lReturn = -1;

		//加锁时使用		
		long sessionID = -1;

		TransfinancialMarginDao dao = new TransfinancialMarginDao();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getNAccountID());
			//判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFinancialMarginInfo newInfo = (TransFinancialMarginInfo)dao.findByID(info.getId(),TransFinancialMarginInfo.class);
			long lNewStatusID = newInfo.getNStatusID();			
			String errMsg =UtilOperation.checkStatus(info.getNStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			//被修改过
			if(errMsg !=null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx,errMsg);
			}
				//判断是否暂存
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
				if (info.getNStatusID()== SETTConstant.TransactionStatus.SAVE){		//如果是保存，更改放款通知单状态,如果是暂存不变
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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
