/**
 * created by kevin(刘连凯)2011-07-13
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
	private static  Object lockObj = new Object();  //静态
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
	 * 内部拆借业务-暂存
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
				log.print("====业务数据校验====");
				if (info.getId() > 0)
				{
					TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
					//被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
				}
				log.print("====保存业务数据====");
				infoId = this.partSave(info, dao);			
				log.print("====更新交易状态为：暂存====");
				long tempID=dao.updateStatus(infoId, SETTConstant.TransactionStatus.TEMPSAVE);
				if(tempID>0){
					lReturnID=infoId;
				}else{
					throw new IRollbackException(mySessionCtx, "暂存失败");
				}					
				log.print("====操作成功====");			
				
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
	 * 内部拆借业务-保存
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
				//对备付金账户加锁
				sessionID = utilOperation.waitUtilSuccessLock(info.getReserveAccountID());
				log.print("====校验该机构是否存在已保存的内部拆借业务====");
				String checkMsg=this.checkOthers(info, dao);
				if (checkMsg != null && !checkMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "保存失败："+checkMsg);
				}	
				String checkBalaceMsg=this.checkBalance(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,false);
				if (checkBalaceMsg != null && !checkBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "保存失败："+checkBalaceMsg);
				}	
				log.print("====开始保存====");
				String transNo = info.getTransNO();				
				if (transNo == null || transNo.equals(""))
				{
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransActionTypeID());
					info.setTransNO(transNo);
					log.print("====生成交易号为===="+transNo);
				}else{								
					log.print("====业务数据校验====");
					TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (this.isTouch(info, dao))
					{
						log.debug("====被非法修改过====");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
				}
				log.print("====保存业务数据====");
				lID = this.partSave(info, dao);
				info.setId(lID);
				log.debug("====保存账簿信息====");
				accountBookOperation.saveInternalLend(info);
				log.debug("====更新交易状态为：保存====");
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				if(tempID>0)
				{
					lReturnID=lID;
				}else
				{
					throw new IRollbackException(mySessionCtx, "保存失败");
				}
				log.debug("====保存操作完成====");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				
			}
			finally
			{
				//对备付金账户解锁
				try
				{
					if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 保存或修改信息操作
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
				log.print("======修改信息======");
				dao.update(info);
			}
			else
			{
				log.print("======增加信息======");
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
	 * 根据id查询内部拆借业务的详细信息
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
			log.print("======通过ID:"+id+"查询内部拆借业务的详细信息======");
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
			log.debug("======是否被修改？======");
			Timestamp lastTouchDate = dao.findByID(info.getId()).getModify();
			Timestamp currentTouchDate = info.getModify();
			Log.print("数据库的当前时间：" + lastTouchDate.toString());
			Log.print("操作对象的修改时间：" + currentTouchDate.toString());
			if (currentTouchDate == null || lastTouchDate == null || !lastTouchDate.toString().equals(currentTouchDate.toString()))
			{
				log.debug("======被修改======");
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
	 * 链接查找
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
	 * 删除操作
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
					throw new IRollbackException(mySessionCtx, "删除失败");
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
	 * 校验该机构是否存在处在保存状态下的业务数据
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
			log.print("======校验该机构是否存在其它未完成的此业务======");
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
						log.print("======已存在未完成的======");
						strMsg="该机构已存在未完成的"+SETTConstant.TransactionType.getName(info.getTransActionTypeID())+"业务，请处理";
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
	 * 校验账户余额与页面传递过来的余额是否一致
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
			log.debug("======校验账户余额是否发生变动======");
			double dNewAccBalance = DataFormat.formatDouble(dao.getBalanceByAccountID(lAccountID,isAvailable),2);		
			double dformatAccBalance=DataFormat.formatDouble(dAccBalance,2);
			String strAccountNo=NameRef.getAccountNoByID(lAccountID);			
			if(dformatAccBalance!=dNewAccBalance)
			{
				log.print("======账户 "+strAccountNo+" 的余额发生了变动======");
				log.print("======dNewAccBalance======"+dNewAccBalance);
				log.print("======dAccBalance======"+dformatAccBalance);				
				strMsg="账户 "+strAccountNo+" 的余额发生了变动，请核查";
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
	 * 内部拆借-匹配操作
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
			log.print("======内部拆借-匹配======");
			info = dao.match(conditonInfo);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}		
		return info;		
	}
	/**
	 * 内部拆借-复核操作
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
				log.print("====开始内部拆借-复核操作====");
				String checkBalaceMsg=this.checkBalance(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,false);
				if (checkBalaceMsg != null && !checkBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "复核失败："+checkBalaceMsg);
				}
				log.print("====业务数据校验====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}	
				this.partSave(info,dao);
				transInfo = dao.findByID(info.getId());		//查找数据库中的完整纪录
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====账簿处理====");
				accountBookOperation.checkInternalLend(transInfo);				
				log.print("====更新交易状态为：已复核====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());			
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "复核失败");
				}
				log.print("====结束内部拆借-复核操作====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
			
	}
	/**
	 * 内部拆借-取消复核
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
				log.print("====开始内部拆借-取消复核操作====");
				String checkLendBalaceMsg=this.isOver(info.getAmount(), info.getLendingAccountID(), dao,false);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "取消复核失败："+checkLendBalaceMsg);
				}		
				TransInternalLendInfo newInfo = this.FindTransInternalLendDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//查找数据库中的完整纪录
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====账簿处理====");
				accountBookOperation.cancelCheckInternalLend(transInfo);				
				log.print("====更新交易状态为：已保存====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "取消复核失败");
				}
				log.print("====结束内部拆借-取消复核操作====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}		
			
	}
	/**
	 * 内部拆借收回-暂存
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
				log.print("====业务数据校验====");
				if (info.getId() > 0)
				{
					TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
					//被修改过
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
				}
				log.print("====保存业务数据====");
				infoId = this.partSave(info, dao);			
				log.print("====更新交易状态为：暂存====");
				long tempID=dao.updateStatus(infoId, SETTConstant.TransactionStatus.TEMPSAVE);
				if(tempID>0){
					lReturnID=infoId;
				}else{
					throw new IRollbackException(mySessionCtx, "暂存失败");
				}					
				log.print("====操作成功====");			
				
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
	 * 内部拆借收回-保存
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
				//对备付金账户加锁
				sessionID = utilOperation.waitUtilSuccessLock(info.getReserveAccountID());
				log.print("====校验该机构是否存在已保存的内部拆借收回业务====");
				String checkMsg=this.checkOthers(info, dao);
				if (checkMsg != null && !checkMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "保存失败："+checkMsg);
				}	
				String checkLendBalaceMsg=this.isOver(info.getLendingAccountBalance(), info.getLendingAccountID(), dao,true);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "保存失败："+checkLendBalaceMsg);
				}
				String checkReserveBalaceMsg=this.isOver(info.getReserveAccountBalance(), info.getReserveAccountID(), dao,true);
				if (checkReserveBalaceMsg != null && !checkReserveBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "保存失败："+checkReserveBalaceMsg);
				}
				log.print("====开始保存====");
				String transNo = info.getTransNO();				
				if (transNo == null || transNo.equals(""))
				{
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransActionTypeID());
					info.setTransNO(transNo);
					log.print("====生成交易号为===="+transNo);
				}else{								
					log.print("====业务数据校验====");					
					TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
					if (errMsg != null && !errMsg.equals(""))
					{
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					if (this.isTouch(info, dao))
					{
						log.debug("====被非法修改过====");
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					}
					log.debug("====删除已有的账簿信息====");
					TransInternalLendInfo oldTransInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());;
					if (oldTransInfo == null)
					{
						log.debug("====无法找到交易对应的账户信息，交易失败====");
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					}
					accountBookOperation.deleteInternalLendRepayment(oldTransInfo);
				}
				log.print("====保存业务数据====");
				lID = this.partSave(info, dao);
				info.setId(lID);
				log.debug("====保存账簿信息====");
				accountBookOperation.saveInternalLendRepayment(info);
				log.debug("====更新交易状态为：保存====");
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				if(tempID>0)
				{
					lReturnID=lID;
				}else
				{
					throw new IRollbackException(mySessionCtx, "保存失败");
				}
				log.debug("====保存操作完成====");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
				
			}
			finally
			{
				//对备付金账户解锁
				try
				{
					if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 内部拆借收回业务处理/业务复核链接查找
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
	 * 通过id查询内部拆借收回详细信息
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
			log.print("======通过ID:"+id+"查询内部拆借收回业务的详细信息======");
			info = dao.findByID(id);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}
		return info;
	}
	/**
	 * 内部拆借收回-删除
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
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				if(info.getStatusID()== SETTConstant.TransactionStatus.SAVE)
				{
					repaymentInfo = dao.findByID(info.getId());		//查找数据库中的完整纪录
					accountBookOperation.deleteInternalLendRepayment(repaymentInfo);
				}
				long tempID=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.DELETED);
				if(tempID>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "删除失败");
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
	 * 内部拆借收回-匹配
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
			log.print("======内部拆借收回-匹配======");
			info = dao.match(conditonInfo);
		}
		catch (Exception e)
		{
			if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
		}		
		return info;
	}
	/**
	 * 内部拆借收回-复核
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
				log.print("====开始内部拆借收回-复核操作====");
				String checkLendBalaceMsg=this.isOver(info.getAmount(), info.getLendingAccountID(), dao,true);
				if (checkLendBalaceMsg != null && !checkLendBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "复核失败："+checkLendBalaceMsg);
				}		
				String checkReserveBalaceMsg=this.isOver(info.getAmount(), info.getReserveAccountID(), dao,true);
				if (checkReserveBalaceMsg != null && !checkReserveBalaceMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, "复核失败："+checkReserveBalaceMsg);
				}
				log.print("====业务数据校验====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//查找数据库中的完整纪录
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====账簿处理====");				
				accountBookOperation.checkInternalLendRepayment(transInfo);				
				log.print("====更新交易状态为：已复核====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK, info.getCheckAbstract(), info.getCheckUserID());			
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "复核失败");
				}
				log.print("====结束内部拆借收回-复核操作====");
			}
			catch (Exception e){
				e.printStackTrace();
				if(e instanceof IRollbackException){throw new IRollbackException(mySessionCtx,((IRollbackException)e).getMessage(),((IRollbackException)e).getMessageArgs());}else{throw new IRollbackException(mySessionCtx,e.getMessage());}
			}
			return lReturnID;
		}			
	}
	
	/**
	 * 内部拆借收回-取消复核
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
				log.print("====开始内部拆借收回-取消复核操作====");
				TransInternalLendInfo newInfo = this.FindTransInternalLendRepaymentDetailByID(info.getId());
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (this.isTouch(info, dao))
				{
					log.debug("====被非法修改过====");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				this.partSave(info, dao);
				transInfo = dao.findByID(info.getId());		//查找数据库中的完整纪录
				transInfo.setCheckAbstract(info.getCheckAbstract());
				transInfo.setCheckUserID(info.getCheckUserID());
				log.print("====账务处理====");
				accountBookOperation.cancelCheckInternalLendRepayment(transInfo);				
				log.print("====更新交易状态为：已保存====");
				long num=dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE, info.getCheckAbstract(), info.getCheckUserID());
				if(num>0){
					lReturnID=info.getId();
				}else{
					throw new IRollbackException(mySessionCtx, "取消复核失败");
				}
				log.print("====结束内部拆借收回-取消复核操作====");
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
			log.debug("======校验账户是否发生透支======");
			double dNewAccBalance = DataFormat.formatDouble(dao.getBalanceByAccountID(lAccountID,isAvailable),2);		
			double dformatAmount=DataFormat.formatDouble(dAmount,2);
			String strAccountNo=NameRef.getAccountNoByID(lAccountID);
			if(isAvailable){
				log.print("======账户 "+strAccountNo+"余额校验======");
				if(dNewAccBalance<0){
					log.print("======账户 "+strAccountNo+" 余额不足======");
					log.print("======dNewAccBalance======"+dNewAccBalance);
					log.print("======dAmount======"+dformatAmount);				
					strMsg="账户 "+strAccountNo+" 余额不足，请核查";
				}
			}else{
				if(dNewAccBalance-dformatAmount<0)
				{
					log.print("======账户 "+strAccountNo+" 余额不足======");
					log.print("======dNewAccBalance======"+dNewAccBalance);
					log.print("======dAmount======"+dformatAmount);				
					strMsg="账户 "+strAccountNo+" 余额不足，请核查";
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
