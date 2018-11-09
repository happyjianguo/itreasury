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
	private static  Object lockObj = new Object();  //静态
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
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
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
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
			//更新状态到：暂存
			info.setStatusID(SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("------开始　tempSave--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------结束　tempSave--------");			
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
		//Sett_TransGeneralLedger数据访问对象
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());
			//获取当前的交易号
			String transNo = info.getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{
				//被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");

				//校验状态是否正确
				TransFeeInfo newInfo = this.findByID(info.getID());
				String errMsg =
					UtilOperation.checkStatus(
						info.getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, dao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//删除账簿信息
					log.debug("------开始删除账簿信息--------");
					TransFeeInfo oldTransInfo = dao.findByID(info.getID());
					if (oldTransInfo == null)
						throw new IRollbackException(mySessionCtx, "无法找到交易对应的账户信息，交易失败");
					log.debug(oldTransInfo.toString());
					log.debug(newInfo.toString());
					accountBookOperation.deleteTransFee(oldTransInfo);
					log.debug("------结束删除账簿信息--------");
				}
			}
			log.debug("------开始　Save--------");
			if (info.getID() == -1)
				depositId = dao.add(info);
			else
				depositId = dao.update(info);
			log.debug("------结束　Save--------");
			info.setID(depositId);

			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			accountBookOperation.saveTransFee(info);
			log.debug("------结束保存账簿信息并开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * @return long 删除的记录ID
	 * @throws RemoteException
	 */
	public long delete(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//校验状态是否正确
			TransFeeInfo newInfo = this.findByID(info.getID());
			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改过			
			if (isTouch(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//删除交易记录
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					accountBookOperation.deleteTransFee(newInfo);
				}
			}
			//返回ID
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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * @return long 复核的记录ID
	 * @throws RemoteException
	 */
	public long check(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//校验状态是否正确
			TransFeeInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//判断是否被修改
			if (isTouch(info, dao))
				throw new IRollbackException(mySessionCtx, "Sett_E020");

			dao.update(info);

			//复核交易记录
			log.info("--------------开始AccountBook复核交易记录--------------");
			
			info.setStatusID(SETTConstant.TransactionStatus.CHECK);
			
			//开始更新表sett_glentry
			log.info("=========开始更新表sett_glEntry");
			accountBookOperation.checkTransFee(info);

			//info.setStatusID(SETTConstant.TransactionStatus.CHECK);
			
			//开始更新表SETT_TRANSFEE
			log.info("=========开始更新表SETT_TRANSFEE");
			dao.update(info);
			log.info("--------------结束AccountBook复核交易记录--------------");

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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long sessionID = -1;
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();

		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getRelatedAccountID());

			//校验状态是否正确
			TransFeeInfo newInfo = this.findByID(info.getID());

			String errMsg =
				UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);

			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			//判断是否被修改
			if (isTouch(info, dao))
				throw new IRollbackException(mySessionCtx, "Sett_E024");

			//取消复核交易记录
			log.info("--------------开始取消AccountBook复核交易记录--------------");
			accountBookOperation.cancelCheckTransFee(info);

			//更新状态到：未复核
			info.setStatusID(SETTConstant.TransactionStatus.SAVE);
			dao.update(info);

			log.info("--------------结束取消AccountBook复核交易记录--------------");

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
			//解锁
			try
			{
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
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
		//Sett_TransGeneralLedger数据访问对象
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
	* 方法说明：根据查询条件匹配
	*  Method  match.
	* @param Sett_TransCurrentDepositInfo info
	* @return Sett_TransCurrentDepositInfo
	*/
	public TransFeeInfo match(TransFeeInfo info) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger数据访问对象
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
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransFeeInfo
	 * @throws IRollbackException
	 */
	public TransFeeInfo findByID(long lID) throws RemoteException, IRollbackException
	{
		//Sett_TransGeneralLedger数据访问对象
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
			//判断是否被非法修改过
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
