/*
 * Created on 2004-8-2
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transabatement.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilityaccountbook.bizlogic.CompatibilityAccountBookOperation;
import com.iss.itreasury.settlement.transabatement.dao.Sett_TransAbatementDAO;
import com.iss.itreasury.settlement.transabatement.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.settlement.transloan.dao.Loan_DAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;
/**
 * SessionBean
 * 
 * @author gqzhang
 */
public class TransAbatementEJB implements SessionBean
{
	final static private int ACTION_CHECK = 0;
	final static private int ACTION_CANCEL_CHECK = 1;
	private javax.ejb.SessionContext mySessionCtx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 *                异常说明。
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
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException
	{
		mySessionCtx = ctx;
	}
	/**
	 * Method findCompatibilityInfoByID. 根据交易id查找转贴现卖出自动冲销交易信息，包括账户处理明细
	 * 
	 * @param lTransID
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransAbatementInfo findAbatementInfoByID(long lTransID) throws RemoteException, IRollbackException, SettlementException
	{
		log.print("====TransAbatementEJB：进入查找转贴现卖出自动冲销交易信息===");
		log.print("====TransAbatementEJB：lID:" + lTransID);
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		TransAbatementInfo rtnInfo = null;
		rtnInfo = dao.findByID(lTransID);
		log.print("====TransCompatibilityEJB：完成查找转贴现卖出自动冲销交易信息===");
		return rtnInfo;
	}
	/**
	 * Method tempsave. 暂存转贴现卖出自动冲销交易信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long tempsave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		//Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
		long depositId = -1;

		log.debug("=========开始暂存转贴现卖出自动冲销交易信息======");
		/*
		Vector detail = info.getTransAbatementDetailInfo();
		if(!dao.checkDebitAndCredit(detail)){
			throw new SettlementException("借贷方金额不相等",new Exception());
		}*/
		
		try
		{
			//检验记录是否被修改
			if (info.getId() > 0)
			{
				//修改暂存再进行暂存
				TransAbatementInfo newInfo = this.findAbatementInfoByID(info.getId());
				if (newInfo == null)
				{
					log.debug("=========无法找到交易ID对应的旧转贴现卖出自动冲销交易信息，交易失败======");
					throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧转贴现卖出自动冲销交易信息，交易失败");
				}
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYTEMPSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equalsIgnoreCase(""))
				{
					log.debug("=========交易被修改过======");
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (checkIsTouched(info, dao))
				{
					log.debug("=========被非法修改过=========");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
			}
			info.setTransNo("");
			depositId = partSave(info, dao);
			
			info.setId(depositId);
			log.debug("====depositId==="+depositId +"==="+info.getId());
			//更新状态到：暂存
			dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.TEMPSAVE);
			log.debug("=========结束暂存转贴现卖出自动冲销交易信息======");
		}
		//modified by mzh_fu 2007/05/011
		catch (Exception e)
		{
			throw new IRollbackException(
					mySessionCtx,
					"暂存转贴现卖出自动冲销交易信息产生错误",
					e);
		}
		return depositId;
	}
	/**
	 * Method save. 保存转贴现卖出自动冲销交易信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long save(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("=========开始保存转贴现卖出自动冲销交易信息======");
		long sessionID = -1;
		//TODO 
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		UtilOperation utilOperation = new UtilOperation();
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		/*
		Vector detail = info.getTransAbatementDetailInfo();
		if(!dao.checkDebitAndCredit(detail)){
			throw new SettlementException("借贷方金额不相等",new Exception());
		}
		*/
		long lTransId = -1;
		try
		{
			//对付款方客户加锁
			log.debug("===========开始对付款方客户进行加锁");
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获取当前记录交易号
			String transNo = info.getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.trim().length() <= 0)
			{
				//未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("==========开始获取新交易号===========");
				transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(),info.getTransactionTypeID());
				info.setTransNo(transNo);
				log.debug("=========新交易号是:" + transNo + "=========");
			}
			else
			{
				log.debug("=========已经生成交易号：" + info.getTransNo());
				//被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("=========开始判断是否被非法修改过======");
				//校验状态是否正确
				log.debug("==========主交易信息的id:" + info.getId());
				TransAbatementInfo newInfo = this.findAbatementInfoByID(info.getId());
				if (newInfo == null)
				{
					log.debug("=========无法找到交易ID对应的旧交易信息，交易失败======");
					throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equalsIgnoreCase(""))
				{
					log.debug("=========交易被修改过======");
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (checkIsTouched(info, dao))
				{
					log.debug("=========被非法修改过=========");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{
					//删除账簿信息
					log.debug("=========开始删除账簿信息=========");
					//TODO 2
					accountBookOperation.deleteTransAbament(newInfo);
					log.debug("=========结束删除账簿信息=========");
				}
			}
			log.debug("============开始PartSave===========");
			//调用保存方法
			lTransId = partSave(info, dao);
			log.debug("============结束PartSave===========");
			//组装完整的交易信息，将主交易信息的id置入明细中
			log.debug("------开始组织交易信息--------");
			info.setId(lTransId);
			//保存账簿信息
			log.debug("------开始保存账簿信息--------");
			log.debug(UtilOperation.dataentityToString(info));
            //TODO 3
			accountBookOperation.saveAbamentInfoAccountDetails(info);
			log.debug("------结束保存账簿信息--------");
			log.debug("------开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
			log.debug("=========结束保存转贴现卖出自动冲销交易信息======");
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"保存转贴现卖出自动冲销交易信息产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("保存转贴现卖出自动冲销交易信息产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("保存转贴现卖出自动冲销交易信息产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (SQLException e)
			{
				throw new IRollbackException(
						mySessionCtx,
						"保存转贴现卖出自动冲销交易信息解锁产生错误",
						e);
			//	throw new SettlementException("保存转贴现卖出自动冲销交易信息解锁产生错误", e);
			}
		}
		return lTransId;
	}
	/**
	 * Method delete.删除转贴现卖出自动冲销交易信息
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long delete(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始删除转贴现卖出自动冲销交易信息======");
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类
		//TODO 4
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		long sessionID = -1;
		//long[] lPayAccountIDs = null;
		long lReturnID = info.getId();
		try
		{
			//对客户加锁
			log.debug("===========开始对付款方客户进行加锁");
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			log.debug("===========结束对付款方客户进行加锁============");
			//校验状态是否正确
			TransAbatementInfo newInfo = this.findAbatementInfoByID(info.getId());
			log.debug("===========记录状态：" + info.getStatusID());
			String errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.DELETE);
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改过
			if (checkIsTouched(info, dao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			else
			{
				//删除交易记录
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
                    //TODO 5
					accountBookOperation.deleteTransAbament(newInfo);
				}
			}
			dao.delete(info.getId());
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"删除转贴现卖出自动冲销交易信息产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("删除转贴现卖出自动冲销交易信息产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("删除转贴现卖出自动冲销交易信息产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (SQLException e)
			{
				throw new IRollbackException(
						mySessionCtx,
						"删除转贴现卖出自动冲销交易信息解锁产生错误",
						e);
//				throw new SettlementException("删除转贴现卖出自动冲销交易信息解锁产生错误", e);
			}
		}
		log.debug("===========结束删除转贴现卖出自动冲销交易信息======");
		return lReturnID;
	}
	/**
	 * Method findCompatibilityByQueryCondition. 链接查找
	 * 
	 * @param info
	 * @return Vector
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public Vector findAbatementByQueryCondition(QueryByStatusConditionInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始按组合条件查找======");
		Vector vctResult = null;
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		vctResult = dao.findAbatementByQueryCondition(info);
		log.debug("===========结束按组合条件查找======");
		return vctResult;
	}
	/**
	 * Method presave.检验交易是否重复保存 如果返回结果大于零，则交易重复
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long presave(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		//暂时保留接口
		log.debug("===========开始保存前检查======");
		log.debug("===========结束保存前检查======");
		return -1;
	}
	/**
	 * Method check. 复核转贴现卖出自动冲销交易
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long check(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return check(ACTION_CHECK, info);
	}
	/**
	 * Method cancelCheck. 取消复核转贴现卖出自动冲销交易
	 * 
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long cancelCheck(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		return check(ACTION_CANCEL_CHECK, info);
	}
	/**
	 * Method match. 匹配
	 * 
	 * @param info
	 * @param specialoperationinfoid
	 * @return TransCompatibilityInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransAbatementInfo match(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("===========开始匹配转贴现卖出自动冲销交易======");
		TransAbatementInfo returnInfo = null;
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		returnInfo = dao.match(info);
		log.debug("===========结束匹配转贴现卖出自动冲销交易======");
		return returnInfo;
	}
	/**
	 * Method check. 内部方法处理复核和取消复核
	 * 
	 * @param checkOrCancelCheck
	 * @param info
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long check(int checkOrCancelCheck, TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		log.debug("=========开始复核（取消复核）兼容业务======");
		long sessionID = -1;
//		TODO 6
		CompatibilityAccountBookOperation accountBookOperation = new CompatibilityAccountBookOperation();
		UtilOperation utilOperation = new UtilOperation();
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		/*
		Vector detail1 = info.getTransAbatementDetailInfo();
		if(!dao.checkDebitAndCredit(detail1)){
			throw new SettlementException("借贷方金额不相等",new Exception());
		}*/
		
		try
		{
			//对付款方客户进行加锁
			log.debug("===========开始对付款方客户进行加锁");
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//检验状态是否正确
			TransAbatementInfo newInfo = this.findAbatementInfoByID(info.getId());
			if (newInfo == null)
			{
				log.debug("=========无法找到交易ID对应的旧交易信息，交易失败======");
				throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
			}
			String errMsg = "";
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CHECK);
			}
			else
			{
				errMsg = UtilOperation.checkStatus(info.getStatusID(), newInfo.getStatusID(), SETTConstant.Actions.CANCELCHECK);
			}
			//被修改过
			if (errMsg != null && !errMsg.equalsIgnoreCase(""))
			{
				log.debug("=========交易被修改过======");
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				if (checkIsTouched(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				if (checkIsTouched(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			dao.update(info);
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				log.debug("=========开始复核转贴现卖出自动冲销交易======");
				//处理账薄和会计账
				//TODO 7
				accountBookOperation.checkAbamentInfoAccountDetails(info);
				//更新交易状态到复核
				dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.CHECK);
			}
			else
			{
				log.debug("=========开始取消复核转贴现卖出自动冲销交易======");
				//取消处理账薄和会计账
				//TODO 8
				accountBookOperation.cancelCheckAbamentInfoAccountDetails(info);
				//更新交易状态到未复核即保存状态
				TransAbatementInfo uInfo = dao.findByID(info.getId());
				uInfo.setStatusID(SETTConstant.TransactionStatus.SAVE);
				uInfo.setCheckUserID(-1);
				uInfo.setCheckDate(null);
				dao.update(uInfo);
				//dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
				
			}
			
			//更新票据状态
			Loan_DAO loadDao = new Loan_DAO();
			Vector detail = info.getTransAbatementDetailInfo();
			
			if(detail!=null && detail.size()>1){
				Vector bill = new Vector();
				Vector credence = new Vector();
				
				for(int i =1;i<detail.size();i++){
					
					TransAbatementDetailInfo detailInfo =(TransAbatementDetailInfo)detail.get(i);
					if(detailInfo.getBillID()>0){
						bill.add(Long.toString(detailInfo.getId()));
					}else{
						credence.add(Long.toString(detailInfo.getId()));
					}
				}
				
				if(checkOrCancelCheck == ACTION_CHECK){
					loadDao.changeIsabatementForBill(bill,credence,SETTConstant.DiscountBillAbatementStatus.YES);
				}else{
					loadDao.changeIsabatementForBill(bill,credence,SETTConstant.DiscountBillAbatementStatus.NO);
				}
			}
			
			
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"复核（取消复核）转贴现卖出自动冲销交易产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("复核（取消复核）转贴现卖出自动冲销交易产生错误", e);
//		}
//		catch (SQLException e)
//		{
//			throw new SettlementException("复核（取消复核）转贴现卖出自动冲销交易产生错误", e);
//		}
		finally
		{
			//解锁
			try
			{
				utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (SQLException e)
			{
				throw new IRollbackException(
						mySessionCtx,
						"复核（取消复核）转贴现卖出自动冲销交易解锁产生错误",
						e);
//				throw new SettlementException("复核（取消复核）转贴现卖出自动冲销交易解锁产生错误", e);
			}
		}
		log.debug("=========结束复核（取消复核）转贴现卖出自动冲销交易======");
		return info.getId();
	}
	/**
	 * Method partSave.
	 * 
	 * @param info
	 * @param dao
	 * @return long
	 * @throws IRollbackException 
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	private long partSave(TransAbatementInfo info, Sett_TransAbatementDAO dao) throws SettlementException, IRollbackException
	{
		try
		{
			log.info("================进入方法partSave===========");
			if (dao.findByID(info.getId()) == null )
			{
				log.info("================新增===========");
				info.setModifyDate(Env.getSystemDateTime());
				long lID = dao.add(info);
				info.setId(lID);
				log.info("================交易ID===========:" + lID);
			}
			else
			{
				log.info("================更新===========");
				info.setModifyDate(Env.getSystemDateTime());
				dao.update(info);
			}
			log.info("================退出方法partSave===========");
		}
		//modified by mzh_fu 2007/05/011
		catch(Exception e){
			throw new IRollbackException(
					mySessionCtx,
					"部分保存转贴现卖出自动冲销交易信息产生错误",
					e);
		}
//		catch (ITreasuryDAOException e)
//		{
//			throw new SettlementException("部分保存转贴现卖出自动冲销交易信息产生错误", e);
//		}
//		catch (SettlementException e)
//		{
//			throw new SettlementException("部分保存转贴现卖出自动冲销交易信息产生错误", e);
//		}
		return info.getId();
	}
	/**
	 * Method checkIsTouched. 检验记录是否被非法修改过
	 * 
	 * @param info
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkIsTouched(TransAbatementInfo info, Sett_TransAbatementDAO dao) throws SettlementException
	{
		//判断是否被非法修改过
		Timestamp lastTouchDate;
		log.debug("=====开始查找修改时间:");
		lastTouchDate = dao.findTouchDate(info.getId());
		log.debug("=====最新修改时间:" + lastTouchDate);
		//@TBD: get touch date from info class
		Timestamp curTouchDate = info.getModifyDate();
		log.debug("=====当前修改时间:" + curTouchDate);
		/*
		 * if (curTouchDate == null || lastTouchDate.compareTo(curTouchDate) !=
		 * 0) { log.debug("=====true"); return true; }
		 */
		if (curTouchDate == null)
		{
			log.debug("=====true1");
			return true;
		}
		else
			if (!lastTouchDate.equals(curTouchDate))
			{
				log.debug("=====true2");
				return true;
			}
			else
			{
				log.debug("=====false");
				return false;
			}
	}
	/**
	 * Method findCompatibilityInfoByID. 查找兼容业务的完整信息，包括主要信息和明细信息
	 * 
	 * @param lTransID
	 * @param dao
	 * @param detailDao
	 * @return TransCompatibilityInfo
	 */
	/*
	private TransAbatementInfo findAbatementInfoByID(long lTransID, Sett_TransAbatementDAO dao) throws SettlementException
	{
		return null;
	}
	*/
	/**
	 * Method findPayAccountIDByTransInfo ，此方法需区分中油还是国机 根 据兼容交易的信息查找对应的付款方账户id集
	 * 合
	 * 此类中不使用些方法 by ycliu
	 * @param info
	 * @return long[]
	 * @throws Exception
	 */
	/*
	private long[] findPayAccountIDByTransInfo(TransAbatementInfo info) throws SettlementException
	{
		
		log.debug("===========开始查找与交易相关的付款方账户Id集合");
		Vector vctTemp = null;
		TransCompatibilityDetailInfo detailInfo = null;
		//vctTemp = info.getTransCompatibilityDetailInfo();
		long lAccountID = -1;
		long lTransDirection = -1; //交易方向
		long lAccountBalanceDirection = -1; //账户余额方向
		long[] lReturnIDs = null;
		try
		{
			AccountOperation accountOperation = new AccountOperation();
			AccountInfo accountInfo = null;
			if (vctTemp != null && vctTemp.size() > 0)
			{
				lReturnIDs = new long[vctTemp.size()];
				for (int i = 0; i < vctTemp.size(); i++)
				{
					detailInfo = (TransCompatibilityDetailInfo) vctTemp.elementAt(i);
					if (detailInfo != null)
					{
						lAccountID = detailInfo.getAccountID();
						lTransDirection = detailInfo.getTransDirectionID();
						log.debug("=========8===============:" + lAccountID);
						accountInfo = accountOperation.findAccountByID(lAccountID);
						if (accountInfo != null)
						{
							//log.debug("=========accountInfo.getBalanceDirection():" + accountInfo.getBalanceDirection());
							//lAccountBalanceDirection = accountInfo.getBalanceDirection();
							log.debug("=========9================");
							if (lTransDirection > 0 && lAccountBalanceDirection > 0 && lTransDirection != lAccountBalanceDirection)
							{
								lReturnIDs[i] = lAccountID;
							}
							else
							{
								lReturnIDs[i] = -1;
							}
						}
					}
				}
			}
		}
		catch (RemoteException e)
		{
			throw new SettlementException("查找与交易相关的付款方账户Id集合产生错误", e);
		}
		catch (IRollbackException e)
		{
			throw new SettlementException("查找与交易相关的付款方账户Id集合产生错误", e);
		}
		log.debug("===========结束查找与交易相关的付款方账户Id集合");
		return lReturnIDs;
	}
	*/
	/**
	 * Method next. 自动冲销继续
	 * 
	 * @param info
	 * @param TransAbatementInfo
	 * @return TransAbatementInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public TransAbatementInfo next(TransAbatementInfo info) throws RemoteException, IRollbackException, SettlementException
	{
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		return dao.next(info);
	}
	/**
	 * 凭证下的票据查询操作
	 * @param lTransDiscountCredenceID
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public Collection findBillByTransDiscountCredenceID(long lTransDiscountCredenceID ,String strOrder , boolean isdesc) throws java.rmi.RemoteException, IRollbackException, SettlementException
	{
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
	    return (Collection)dao.getReDiscountContractBill(lTransDiscountCredenceID ,strOrder , isdesc);
	}
	
	/**
	 * 根据交易号查找自动冲销交易主键
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws SettlementException
	 */
	public long getIDByTransNo(String strTransNo) throws RemoteException, IRollbackException, SettlementException
	{
		log.info("========根据交易号查找自动冲销交易主键=========");
		long lID = -1;
		Sett_TransAbatementDAO dao = new Sett_TransAbatementDAO();
		lID = dao.getIDByTransNo(strTransNo);
		return lID;
	}
}