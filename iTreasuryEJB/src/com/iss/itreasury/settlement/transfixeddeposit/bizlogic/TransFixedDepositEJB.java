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
	
	private final static  Object lockObj = new Object();  //静态

	/**
	 * ejbActivate method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbActivate() throws RemoteException {
	}

	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 *                异常说明。
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException {
	}

	/**
	 * ejbPassivate method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
	 */
	public void ejbPassivate() throws RemoteException {
	}

	/**
	 * ejbRemove method comment
	 * 
	 * @exception RemoteException
	 *                异常说明。
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
	 *                异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
			throws RemoteException {
		mySessionCtx = ctx;
	}

	// ******用户自加入代码*******//
	/**
	 * 定期（通知）存款开立交易的暂存方法：
	 * 
	 * 1、参* 3、逻辑说明：
	 * 
	 * （1）如果lID不是-1，调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过 。
	 * 调用方法Sett_TransOpenFixedDepositDAO.update()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransOpenFixedDepositDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * （2）如果lID是-1，调用方法Sett_TransOpenFixedDepositDAO.add()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransOpenFixedDepositDAO.updateStatus()更改记录的状态为未保存。
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

			// 判断存单号是否重复
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// 新增或更新信息
			if (info.getID() <= 0) // 新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// 更新状态为未保存
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // 修改暂存
			{
				// 判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransFixedOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYTEMPSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYTEMPSAVE);
				// 被修改过
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
						// 更新状态为未保存
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
	 * 定期（通知）开立交易保存前检测是否重复的方法：
	 * 
	 * 1、参数： FixdOpenInfo 交易实体类
	 * 
	 * 2、返回值： String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明： （1）判断参数FixdOpenInfo,中的交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 用方法：Sett_TransOpenFixedDeposit.checkIsDuplicate()判断是否重复。
	 * 
	 * @roseuid 3F73AE9300E8
	 */

	/**
	 * 定期（通知）开立交易的保存方法：
	 * 
	 * 1、参数： FixdOpenInfo, 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数FixdOpenInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixdOpenInfo 。 如果非空，说明是修改保存:
	 * 调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.openFindByID(),得到包含原来的交易实体类FixdOpenInfo。
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo。 （2）调用方法：Sett_TransOpenFixedDepositDAO.add() 保存信息。
	 * （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 * （4）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus() 。修改交易的状态为保存。
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
		// 数据访问对象
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// 账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		String transNo = null;
		log.debug("---------开始openSave---------------");
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			// 获得当前状态
			long lStatus = info.getStatusID();
			// 暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) {
				log.debug("----------当前状态为暂存-------------");
				// modify by xwhe date:2007-08-03
				String DepositNo = utilOperation
						.getOpenDepositNoBackGround(info.getAccountID());
				info.setDepositNo(DepositNo);
				// 判断存单号是否重复
				if (!dao.checkDepositNo(info)) {
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				// 判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransFixedOpenInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// 被修改过
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// 判断是否被非法修改过
				log.debug("----------判断是否被非法修改过-------------");
				if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// 未非法修改
					log.debug("----------开始更新定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新定期交易信息-------------");
					// 财务处理，设置账户余额
					log
							.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveOpenFixedDeposit(info);
					log
							.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					// 通过工具操作接口类获取新交易号
					// String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					// 修改
					lReturn = dao.update(info);
					// 修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);
				}
			} else // 不是暂存
			{
				log.debug("----------当前状态不是暂存-------------");
				// modify by xwhe date:2007-08-03
				String DepositNo = info.getDepositNo();
				if (DepositNo == null || DepositNo.equals("")) {
					DepositNo = utilOperation.getOpenDepositNoBackGround(info
							.getAccountID());
					info.setDepositNo(DepositNo);
				}
				// 判断存单号是否重复
				if (!dao.checkDepositNo(info)) {
					log.debug("----------存单号重复，交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E132");
				}
				// 获取当前交易号
				transNo = info.getTransNo();
				// 标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals("")) {
					log.debug("----------是新增交易-------------");
					// 未被保存过，生成新交易号
					bNewTransNo = true;
					// 通过工具操作接口类获取新交易号
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					// 保存
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增定期交易信息-------------");
					// （3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveOpenFixedDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					
					//回写网银指令
					if (info.getInstructionNo() != null && info.getInstructionNo().length() > 0) {
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						
						//回写定期开立定期利率 Boxu Add 2008年5月7日
						financeInfo.setDepositRate(info.getRate());
						
						financeDao.updateStatusAndTransNo(null, financeInfo);
					}
					
					// Modifyed by qhzhou 2007.6.21
					if (info.getStatusID() != SETTConstant.TransactionStatus.WAITAPPROVAL) {
						// 修改交易的状态为保存。
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
					}
				} else {
					// 被保存过， 即保存再保存
					// 判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransFixedOpenInfo newInfo = dao.findByID(info.getID());
					if (newInfo == null)
						throw new IRollbackException(mySessionCtx,
								"无法找到交易对应的账户信息，交易失败");
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info
							.getStatusID(), lNewStatusID,
							SETTConstant.Actions.MODIFYSAVE);
					// 被修改过
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// 判断是否被非法修改过
					log.debug("----------是保存再保存-------------");
					if (this.openCheckIsTouched(info.getID(), info
							.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {
						// 财务处理，删除数据库中的已有的存单
						log.debug("-------删除账簿中旧的信息--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						// 删除账簿信息
						log.debug("------开始删除账簿信息--------");
						accountBookOperation.deleteOpenFixedDeposit(newInfo);
						log.debug("------结束删除账簿信息--------");

						// 未非法修改
						log.debug("----------开始更新定期交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新定期交易信息-------------");
						// 财务处理，设置账户余额
						log
								.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveOpenFixedDeposit(info);
						log
								.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						// 修改
						lReturn = dao.update(info);
						// 修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.SAVE);
						
						//回写网银指令
						if (info.getInstructionNo() != null && info.getInstructionNo().length() > 0) {
							log.info("---------是网银指令----------");
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

							financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
							financeInfo.setDealUserID(info.getInputUserID());
							financeInfo.setConfirmDate(info.getExecuteDate());
							financeInfo.setFinishDate(info.getExecuteDate());
							financeInfo.setTransNo(info.getTransNo());
							financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
							
							//回写定期开立定期利率 Boxu Add 2008年5月7日
							financeInfo.setDepositRate(info.getRate());
							
							financeDao.updateStatusAndTransNo(null, financeInfo);
						}						

					}
				}
			}
			/**
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */
			if (info.getInutParameterInfo() != null) {
				log.debug("------提交审批--------");
				// 设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl() + lReturn);
				tempInfo.setTransID(transNo);// 这里保存的是交易编号
				tempInfo.setDataEntity(info);

				// 提交审批
				FSWorkflowManager.initApproval(info.getInutParameterInfo());
				// 更新状态到审批中
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALING);
				log.debug("------提交审批成功--------");

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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束openSave---------------");
		return lReturn;
		}
	}

	/**
	 * 定期（通知）开立交易的删除方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类
	 * 
	 * 2、返回值： long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）调用方法this.opencheckIsTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数TransFixedOpenInfo 中的本金交易实体类的状态， 如果是暂存：
	 * 
	 * 调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为删除（无效 ）。 如果是保存：
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo
	 * 
	 * 调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为删除（无效 ）。
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long openDelete(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;

		// 加锁时使用
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();

		log.debug("---------开始openDelete---------------");
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.DELETE);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// 判断是否暂存
				if (info.getTransNo() == null || info.getTransNo().equals("")) {
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
				} else {
					log
							.debug("---------开始accountBookOperation::deleteOpenFixedDeposit---------------");
					log.debug(UtilOperation.dataentityToString(info));
					TransFixedOpenInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteOpenFixedDeposit(delInfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);
					log
							.debug("---------结束accountBookOperation::deleteOpenFixedDeposit---------------");

					if (delInfo.getInstructionNo() != null
							&& delInfo.getInstructionNo().length() > 0) {
						log.info("---------是网银指令----------");
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("---------结束openDelete---------------");
		return lReturn;
		}
	}

	/**
	 * 定期（通知）存款交易的复核方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类 2、返回值： long ,被复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常? 您要复核的单据已被修改，请检查！”。
	 * （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 * 
	 * （3）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为复核?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long openCheck(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();

		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// 复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）

				// 财务操作，放开账户余额，
				accountBookOperation.checkOpenFixedDeposit(info);
				log.debug("-------开立复核：更新状态到已复核---------");
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				/***********构造银行付款指令**********/
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getBankID();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {				
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				
				if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
					Log.print("*******************开始产生定期开立和通知开立指令，并保存**************************");
					try {
						log.debug("------开始银行定期开立和通知立指令生成--------");
						//构造参数
						CreateInstructionParam instructionParam = new CreateInstructionParam();
						instructionParam.setTransactionTypeID(info.getTransactionTypeID());
						instructionParam.setObjInfo(info);
						instructionParam.setOfficeID(info.getOfficeID());
						instructionParam.setCurrencyID(info.getCurrencyID());
						instructionParam.setCheckUserID(info.getCheckUserID());
						instructionParam.setBankType(bankID);
						instructionParam.setInputUserID(info.getInputUserID());
						
						//生成银行指令并保存
						IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
						bankInstruction.createBankInstruction(instructionParam);
						
						log.debug("------生成银行定期开立和通知开立指令结束--------");
						
					} catch (Throwable e) {
						log.error("生成银行定期开立和通知立指令失败");
						e.printStackTrace();
						throw new IRollbackException(mySessionCtx, "生成银行定期开立和通知开立指令失败："+e.getMessage());
					}
				}
				else {
					Log.print("没有银行接口或不需要生成银行指令！");
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------开立复核：结束---------");
		return lReturn;
		}
	}
	
	/**
	 * 定期（通知）存款交易的复核方法(不发银行指令，定期部分支取专用，在定期部分支取中统一发指令)：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类 2、返回值： long ,被复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常? 您要复核的单据已被修改，请检查！”。
	 * （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 * 
	 * （3）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为复核?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long openCheckForPartDraw(TransFixedOpenInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();

		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedOpenInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// 复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）

				// 财务操作，放开账户余额，
				accountBookOperation.checkOpenFixedDeposit(info);
				log.debug("-------开立复核：更新状态到已复核---------");
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		log.debug("-------开立复核：结束---------");
		return lReturn;
		}
	}


	/**
	 * 定期（通知）存款交易的取消复核方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类 2、返回值： long ,被取消复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 * 
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处 理。
	 * 
	 * （3）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为保存?
	 * 
	 * @roseuid 3F73AEB30222
	 */
	public long openCancelCheck(TransFixedOpenInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransOpenFixedDepositDAO depositDao = new Sett_TransOpenFixedDepositDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedOpenInfo newInfo = depositDao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				// 取消复核
				accountBookOperation.cancelCheckOpenFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				// 更新状态到：未复核或已审批(根据该业务是否有审批流判断)
				long lCancelCheckStatus = FSWorkflowManager
						.getSettCheckStatus(new InutParameterInfo(info
								.getOfficeID(), info.getCurrencyID(),
								Constant.ModuleType.SETTLEMENT, info
										.getTransactionTypeID(), -1));
				info.setStatusID(lCancelCheckStatus);
				// //added by qhzhou 2007-07-25 取消复核时应清空复核备注，复核人
				//info.setCheckAbstract("");Modified by ylguo(郭英亮)at 2009-02-16取消复核的时候，备注不能清空，因为在查询的时候要看的到，在复核或取消复核的时候
				//备注的内容应该由页面来控制，就是说查询的时候，页面上能看到。复核和取消复核的是却不能看到内容
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 根据标识查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： lID long , 开立交易的ID
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	 * 根据交易号查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： strTransNo , 开立交易号
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	 * 根据存单编号查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： DepositNo , 存单编号
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByDepositNo()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	 * 根据存单编号查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： DepositNo , 存单编号
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByDepositNo()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	// 开立交易的链接查找,过滤由于定期支取生成的开立存单
	/**
	 * 根据状态查询的方法：
	 * 
	 * 1、参数： QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值： Collection ,包含TransFixedOpenInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用Sett_TransOpenFixedDepositDAO.findByStatus()方法。
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
	 * 根据状态查询的方法：
	 * 
	 * 1、参数： QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值： Collection ,包含TransFixedOpenInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用Sett_TransOpenFixedDepositDAO.findByStatus()方法。
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
	 * 复核匹配的方法：
	 * 
	 * 1、参数： TransFixedOpenInfo,定期（通知）开立交易查询条件实体类
	 * 
	 * 2、返回值： Collection ,包含TransFixedOpenInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用方法：Sett_TransOpenFixedDepositDAO.match()
	 * 
	 * @roseuid 3F73AEC000C1
	 */
	public Collection openMatch(TransFixedOpenInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		try {
			// 控制匹配复核状态
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
	 * 判断交易记录是否被修改过的暂存方法：
	 * 
	 * 1、参数： lID, 交易ID。 long, 原来的TouchTime。
	 * 
	 * 2、返回值： boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID,得到最新的交易。
	 * 
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * 
	 * @roseuid 3F73AEC40379
	 */
	private boolean openCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
			TransFixedOpenInfo info = dao.findByID(lID);
			// 判断是否被非法修改过
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
	 * 判断状态是否正确
	 * 
	 * @param lStatusID
	 *            页面传过来的状态
	 * @param lNewStatusID
	 *            后台取到的状态
	 * @param lActionID
	 *            页面操作类型
	 * @return void 状态不匹配
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	/*
	 * private void checkStatus(long lStatusID, long lNewStatusID, long
	 * lActionID) throws IRollbackException,RemoteException { try { //修改保存 if
	 * (lActionID == SETTConstant.Actions.MODIFYSAVE) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.SAVE && lStatusID !=
	 * SETTConstant.TransactionStatus.TEMPSAVE) { //已被复核 if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //已被删除 if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } } else { //已被复核 if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //已被删除 if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } } } //修改暂存 if (lActionID ==
	 * SETTConstant.Actions.MODIFYTEMPSAVE) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.TEMPSAVE) { //已被复核 if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } //已被删除 if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } //已被保存 if (lStatusID ==
	 * SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E130"); } } else { if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E016"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E017"); } if (lNewStatusID ==
	 * SETTConstant.TransactionStatus.SAVE) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E130"); } } } //删除 if (lActionID ==
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
	 * IRollbackException(mySessionCtx, "Sett_E019"); } } } //复核 if (lActionID ==
	 * SETTConstant.Actions.CHECK) { if (lStatusID !=
	 * SETTConstant.TransactionStatus.SAVE) { if (lStatusID ==
	 * SETTConstant.TransactionStatus.DELETED) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E022"); } if (lStatusID ==
	 * SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E021"); }
	 *  } else { if (lNewStatusID == SETTConstant.TransactionStatus.DELETED) {
	 * throw new IRollbackException(mySessionCtx, "Sett_E022"); } if
	 * (lNewStatusID == SETTConstant.TransactionStatus.CHECK) { throw new
	 * IRollbackException(mySessionCtx, "Sett_E021"); } } } //取消复核 if (lActionID ==
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
	 * 定期（通知）存款（转活期/支取）交易的暂存方法：
	 * 
	 * 1、参数： FixedDrawInfo,开立 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）如果lID不是-1，调用方法this.DrawCheckIsTouched,判断要暂存的记录是否被修改过 。
	 * 调用方法Sett_TransFixedWithDrawDAO.update()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransFixedWithDrawDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransFixedWithDrawDAO.add()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransFixedWithDrawDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * @roseuid 3F73AECB006C
	 */
	public long drawTempSave(TransFixedDrawInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			// 新增或更新活期信息
			if (info.getID() <= 0) // 新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// 更新状态为未保存
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // 修改暂存
			{
				boolean flag = drawCheckIsTouched(info.getID(), info
						.getModifyDate());
				if (flag) {
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					lReturn = dao.update(info);

					if (lReturn != -1) {
						// 更新状态为未保存
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
	 * 定期（通知）（转活期/支取）交易的保存方法：
	 * 
	 * 1、参数： FixedDrawInfo, 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数FixedDrawInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixedDrawInfo 。 如果非空，说明是修改保存:
	 * 调用方法this.drawCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.drawFindByID(),得到包含原来的交易实体类FixedDrawInfo。
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体FixedDrawInfo。 （2）调用方法：Sett_TransFixedWithDrawDAO.add() 保存信息。
	 * （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 * （4）调用方法：Sett_TransFixedWithDrawDAO.updateStatus() 。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AECF0111
	 */
	public long drawSave(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		long sessionID = -1;
		// 数据访问对象
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// 账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		// 开立数据访问对象
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

			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 获得当前状态
			long lStatus = info.getStatusID();
			// 暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) 
			{
				log.debug("----------当前状态为暂存-------------");

				// 判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransFixedDrawInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// 被修改过
				if (errMsg != null && !errMsg.equals("")) 
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// 判断是否被非法修改过
				log.debug("----------判断是否被非法修改过-------------");
				if (this.drawCheckIsTouched(info.getID(), info.getModifyDate())) 
				{
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} 
				else 
				{
					// 未非法修改
					log.debug("----------开始更新定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新定期交易信息-------------");
					// 财务处理，设置账户余额
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveWithdrawFixedDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					// 通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					// 修改
					lReturn = dao.update(info);
					// 修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					lReturn = biz.insertTransNo(info.getTransNo(), info.getNotifyId());

					// added by qhzhou 2007-07-23
					// Modified by qhzhou 2007.07.26 定期提前部分支取业务
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
						if (endDate != null && drawDate != null) {
							// added by qhzhou
							boolean b = (drawDate.compareTo(endDate) < 0)
									&& (remainAmount > 0);
							if (b&&(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))) {
								// TransFixedOpenInfo
								// n_tfoInfo=this.openFindByDepositNo(info.getDepositNo().trim());//新建开立信息

								TransFixedOpenInfo n_tfoInfo = new TransFixedOpenInfo();
								n_tfoInfo.setTransNo(""); // 置空交易号使用系统新生成的交易号
								n_tfoInfo.setDepositNo(info.getNewDepositNo()); // 设置新的存单号
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

								n_tfoInfo.setAmount(0.0);// 开立是暂不存款，到该定期支取复核后再将本金为原（本金-支取金额）
								n_tfoInfo.setExecuteDate(info.getExecuteDate()); // 设置新开立存单的执行日为定期支取执行日
//								n_tfoInfo.setInterestStartDate(info
//										.getExecuteDate());// 设置新开立存单的起息日为定期支取执行日
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

								n_tfoInfo.setBillNo(dpNo); // 保存原定期开立单据号
								n_tfoInfo
										.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// 新增开立数据状态，重新定义的状态
																									// ，“待处理状态”
								// add by zcwang 2007-11-08
								// 部分提取时将支取的摘要作为新开立存单的摘要
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
			else  //不是暂存，保存/保存再保存
			{
				//TransFixedOpenInfo n_tfoInfo=null;
				//如果是定期提前部分支取，则开立新的定期开立帐户

				log.debug("----------当前状态不是暂存-------------");
				//获取当前交易号
				String transNo = info.getTransNo();
				//标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals("")) 
				{
					log.debug("----------是新增交易-------------");
					//未被保存过，生成新交易号
					bNewTransNo = true;
					//通过工具操作接口类获取新交易号
					transNo = utilOperation.getNewTransactionNo(info.getOfficeID(), info.getCurrencyID(), info.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					//保存
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增定期交易信息-------------");
					//（3）调用方法：进行财务处理。
					log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveWithdrawFixedDeposit(info);
					log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					
					//回写网银指令
					if(info.getInstructionNo() != null && info.getInstructionNo().length() > 0) 
					{
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
						financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
						financeInfo.setDealUserID(info.getInputUserID());
						financeInfo.setConfirmDate(info.getExecuteDate());
						financeInfo.setFinishDate(info.getExecuteDate());
						financeInfo.setTransNo(info.getTransNo());
						financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
						
						//Boxu Add 2008年4月2日 如果定期支取是提前或逾期则可能存在活期利率,网银中没有活期利率字段,暂时存在"贷款利率"
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

						//在通知支取指令的记录表当中，将状态改为已使用,并在该条指令表当中添加交易号
						minfo.setID(info.getNotifyId());
						minfo.setDepositNo(info.getDepositNo());
						minfo.setStatusID(SETTConstant.NotifyInformStatus.USED);
						lReturn = biz.insertTransNo(info.getTransNo(), info.getNotifyId());
						lReturn = biz.modify(minfo);
						//修改交易的状态为保存。
						lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);

						//Modified by qhzhou 2007.07.26 定期提前部分支取业务
						if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
							if (endDate != null && drawDate != null) {
								// added by qhzhou
								boolean b = (drawDate.compareTo(endDate) < 0)
										&& (remainAmount > 0);
								if (b&&(Config.getBoolean(ConfigConstant.SETT_FIXEDDRAW_CREATENEWBOOK,true))) {
									// TransFixedOpenInfo
									// n_tfoInfo=this.openFindByDepositNo(info.getDepositNo().trim());//新建开立信息

									TransFixedOpenInfo n_tfoInfo = new TransFixedOpenInfo();
									n_tfoInfo.setTransNo(""); // 置空交易号使用系统新生成的交易号
									n_tfoInfo.setDepositNo(info.getNewDepositNo()); // 设置新的存单号
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

									n_tfoInfo.setAmount(0.0);// 开立是暂不存款，到该定期支取复核后再将本金为原（本金-支取金额）
									n_tfoInfo.setExecuteDate(info.getExecuteDate()); // 设置新开立存单的执行日为定期支取执行日
									//n_tfoInfo.setInterestStartDate(info.getExecuteDate());// 设置新开立存单的起息日为定期支取执行日
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

									n_tfoInfo.setBillNo(dpNo); // 保存原定期开立单据号
									n_tfoInfo.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// 新增开立数据状态，重新定义的状态
																										// ，“待处理状态”
									// add by zcwang 2007-11-08
									// 部分提取时将支取的摘要作为新开立存单的摘要
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
					// 被保存过， 即保存再保存
					// 判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransFixedDrawInfo newInfo = dao.findByID(info.getID());
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info.getStatusID(), lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
					// 被修改过
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// 判断是否被非法修改过
					log.debug("----------是保存再保存-------------");
					if (this.drawCheckIsTouched(info.getID(), info.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {

						// 财务处理，删除数据库中的已有的存单
						log.debug("-------删除账簿中旧的信息--------");
						log.debug(UtilOperation.dataentityToString(newInfo));
						accountBookOperation.deleteWithdrawFixedDeposit(newInfo);
						// 未非法修改
						log.debug("----------开始更新定期交易信息-------------");
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
										// 部分提取时将支取的摘要作为新开立存单的摘要
										n_tfoInfo.setAbstract(info.getAbstract());
										//
										openDao.update(n_tfoInfo);
									} 
									else {
										throw new IRollbackException(mySessionCtx, "新开立存单不存在，操作失败");
									}
								}
							}
						}
						log.debug("----------结束更新定期交易信息-------------");
						// 财务处理，设置账户余额
						log.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveWithdrawFixedDeposit(info);
						log.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");

						// 在通知支取指令的记录表当中，将状态改为已使用
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
							// 修改
							lReturn = dao.update(info);
							// 修改交易的状态为保存
							lReturn = dao.updateStatus(info.getID(), SETTConstant.TransactionStatus.SAVE);
						}

					}
				}
				/**
				 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				 */
				if (info.getInutParameterInfo() != null) {
					log.debug("------提交审批--------");
					// 设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(transNo);// 这里保存的是交易编号
					tempInfo.setDataEntity(info);
					// 提交审批
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					// 更新状态到审批中
					dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------提交审批成功--------");

				}
				// liuguang 增加外部账户处理
				// 账簿操作接口类
				AccountOperation accountOperation = new AccountOperation();
				
				ExternalAccountInfo extAccountInfo = new ExternalAccountInfo();
				extAccountInfo.setOfficeID(info.getOfficeID());
				extAccountInfo.setNcurrencyID(info.getCurrencyID());
				//增加本金收款外部账户
				extAccountInfo.setExtAcctNo(info.getExtAcctNo());
				extAccountInfo.setExtAcctName(info.getExtClientName());
				extAccountInfo.setBankName(info.getRemitInBank());
				extAccountInfo.setProvince(info.getRemitInProvince());
				extAccountInfo.setCity(info.getRemitInCity());
				accountOperation.saveExternalAccount(extAccountInfo);
				
				//增加利息收款外部账户
				extAccountInfo.setExtAcctNo(info.getInterestExtAcctNo());
				extAccountInfo.setExtAcctName(info.getInterestExtClientName());
				extAccountInfo.setBankName(info.getInterestRemitInBank());
				extAccountInfo.setProvince(info.getInterestRemitInProvince());
				extAccountInfo.setCity(info.getInterestRemitInCity());
				accountOperation.saveExternalAccount(extAccountInfo);
				//结束

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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期（通知）支取/转活期交易的删除方法：
	 * 
	 * 1、参数： FixedDrawInfo 交易实体类
	 * 
	 * 2、返回值： long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）调用方法this.drawCheckIsTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数FixedDrawInfo 中的本金交易实体类的状态， 如果是暂存：
	 * 
	 * 调用方法：Sett_TransFixedWithDrawDAO.updateStatus。修改交易的状态为删除（无效）?
	 * 
	 * 如果是保存：
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo
	 * 
	 * 调用方法：Sett_TransFixedWithDrawDAO.updateStatus。修改交易的状态为删除（无效）?
	 * 
	 * @roseuid 3F73AED3008A
	 */
	public long drawDelete(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;

		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// 开立数据访问对象
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// 判断是否暂存
				if (info.getTransNo() == null || info.getTransNo().equals("")) {
					// 在通知支取指令的记录表当中，将状态改为保存
					System.out
							.println("******************开始修改通知指令表状态***************");
					System.out.println("********info.getDepositNo()="
							+ info.getDepositNo());
					NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					minfo.setDepositNo(info.getDepositNo());
					minfo.setStatusID(SETTConstant.NotifyInformStatus.SAVE);
					minfo.setIsDele(1);// 设置是通知指令做删除时候标志
					minfo.setStransno(info.getTransNo());// 存交易号
					lReturn = biz.modify(minfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);

				} else {
					TransFixedDrawInfo delInfo = dao.findByID(info.getID());
					accountBookOperation.deleteWithdrawFixedDeposit(delInfo);
					// 删除网银指令
					if (delInfo.getInstructionNo() != null
							&& delInfo.getInstructionNo().length() > 0) {
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(Long.valueOf(
								delInfo.getInstructionNo()).longValue(),
								OBConstant.SettInstrStatus.REFUSE);
					}
					// 在通知支取指令的记录表当中，将状态改为保存
					System.out
							.println("******************开始修改通知指令表状态***************");
					System.out.println("********info.getDepositNo()="
							+ info.getDepositNo());
					NotifyDepositInformInfo minfo = new NotifyDepositInformInfo();
					TransNotifyDepositBiz biz = new TransNotifyDepositBiz();
					minfo.setDepositNo(info.getDepositNo());
					minfo.setStatusID(SETTConstant.NotifyInformStatus.SAVE);
					minfo.setIsDele(1);// 设置是通知指令做删除时候标志
					minfo.setStransno(info.getTransNo());// 存交易号
					lReturn = biz.modify(minfo);
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.DELETED);

					// Modified by qhzhou 2007.07.26 定期提前部分支取业务
					if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
						// added by qhzhou 2007.6.21
						// long CurrentAccountID=info.getCurrentAccountID();
						String oldOpenDepositNo = info.getDepositNo();
						// Modifyed by qhzhou 2007.6.23
						// 根据该定期开立存单号，查找其是否开立了新的定期开立帐户，如果没有开立记录，说明该定期支渠不是提前部分支取
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期（通知）存款交易的取消复核方法：
	 * 
	 * 1、参数： FixedDrawInfo 交易实体类 2、返回值： long ,被取消复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.drawcheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 * 
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处 理。
	 * （3）调用方法：Sett_TransFixedWithDrawDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AED60143
	 */
	public long drawCancelCheck(TransFixedDrawInfo info)throws IRollbackException, RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// 开立数据访问对象
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {

				// added by qhzhou 2007.6.21 定期支取业务
				if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
					// 先对系统自动开立的新定期存款记录取消复核操作
					// long CurrentAccountID=info.getCurrentAccountID();
					String oldOpenDepositNo = info.getDepositNo();
					// Modifyed by qhzhou 2007.6.23
					// 根据该定期开立存单号，查找其是否开立了新的定期开立帐户，如果没有开立记录，说明该定期支渠不是提前部分支取
					TransFixedOpenInfo n_tfoInfo = this
							.openFindByOldDepositNo(oldOpenDepositNo);
					if (n_tfoInfo != null) {

						this.openCancelCheck(n_tfoInfo);// 取消新定期开立复核状态
						n_tfoInfo = this
								.openFindByOldDepositNo(oldOpenDepositNo);
						if (n_tfoInfo != null) {
							n_tfoInfo
									.setStatusID(SETTConstant.TransactionStatus.WAITAPPROVAL);// 将新定期开立状态打回“待审批”
							n_tfoInfo.setAmount(0.0);
							openDao.update(n_tfoInfo);
							// 取消复核不增加未复核金额 add by zcwang 2007-8-14
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
				// 取消复核
				accountBookOperation.cancelCheckWithdrawFixedDeposit(info);

				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				// 更新状态到：未复核或已审批(根据该业务是否有审批流判断)
				long lCancelCheckStatus = FSWorkflowManager
						.getSettCheckStatus(new InutParameterInfo(info
								.getOfficeID(), info.getCurrencyID(),
								Constant.ModuleType.SETTLEMENT, info
										.getTransactionTypeID(), -1));
				info.setStatusID(lCancelCheckStatus);
//				 //added by qhzhou 2007-07-25 取消复核时应清空复核备注，复核人
				//info.setCheckAbstract("");Modified by ylguo(郭英亮)at 2009-02-16取消复核的时候，备注不能清空，因为在查询的时候要看的到，在复核或取消复核的时候
				//备注的内容应该由页面来控制，就是说查询的时候，页面上能看到。复核和取消复核的是却不能看到内容
				//info.setCheckUserID(-1);
				//info.setCheckUserName("");
				//Modifying ended at 2009-02-16
				lReturn = dao.update(info);
				// if(lReturn!=-1)
				// {
				// lReturn = dao.updateStatus(info.getID(),
				// SETTConstant.TransactionStatus.SAVE);
				// }

				// added by qhzhou 2007.08.07 查找子帐户
				//if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
				//	AccountBean accBean = new AccountBean();
				//	Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
				//	long subAccountId = accBean
				//			.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(
				//					info.getAccountID(), info.getDepositNo());
					// **************开始恢复冲销计提利息
					// 2007-08-07*******************************************
					// //SubAccountFixedInfo
					// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);
					//				
				//	saDao.updateLoanPredrawInterestByID(subAccountId, info
				//			.getStrikePreDrawInterest());
					// *************************************************************************************
				//}
				
				//Add Boxu 2008年1月29日 还原计提利息
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期（通知）存款交易的复核方法：
	 * 
	 * 1、参数： FixedDrawInfo 交易实体类 2、返回值： long ,被复核的定期（通知）本金交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.drawcheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常? 您要复核的单据已被修改，请检查！”。
	 * （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 * （3）调用方法：Sett_TransFixedWithDrawDAO.updateStatus。修改交易的状态为复核。
	 * 
	 * @roseuid 3F73AEDA0102
	 */
	public long drawCheck(TransFixedDrawInfo info) throws IRollbackException,RemoteException 
	{
		synchronized(lockObj){
			
		
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();

		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		// 开立数据访问对象
		// added by qhzhou
		Sett_TransOpenFixedDepositDAO openDao = new Sett_TransOpenFixedDepositDAO();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedDrawInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = drawCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// added by qhzhou 2007.07.25 查找子帐户

				AccountBean accBean = new AccountBean();
				Sett_SubAccountDAO saDao = new Sett_SubAccountDAO();
				long subAccountId = accBean
						.getFixedSubAccoutIDByAccoutIDAndFixedDepositNo(info
								.getAccountID(), info.getDepositNo());
				// SubAccountFixedInfo
				// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);
				// sInfo.setPreDrawInterest(0.00);
				// saDao.updateSubAccountFix(sInfo);

				// 复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
				// 财务操作，放开账户余额，
				accountBookOperation.checkWithdrawFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				
				// added by qhzhou 2007.6.21 定期提前部分支取业务
				//if (info.getTransactionTypeID() == SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) {
					
					// 开始清零冲销计提利息 2007-08-07 这样处理不对
					//SubAccountFixedInfo sInfo = saDao.findFixedSubAccountInfoByID(subAccountId);
					//saDao.updateLoanPredrawInterestByID(sInfo.getID(), 0.00);

					// long CurrentAccountID=info.getCurrentAccountID();
				String oldOpenDepositNo = info.getDepositNo();
				// Modifyed by qhzhou 2007.6.23
				// 根据该定期开立存单号，查找其是否开立了新的定期开立帐户，如果没有开立记录，说明该定期支取不是提前部分支取
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
						// 保存没增加未复核金额,复核不扣除未复核金额,做反向操作 add by zcwang 2007-8-14

						if (n_tfoInfo.getCurrentAccountID() > 0) {
							AccountEJB accountejb = new AccountEJB();
							Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
							subAccountDAO.updateUncheckPaymentAmount(accountejb
									.getCurrentSubAccoutIDByAccoutID(n_tfoInfo
											.getCurrentAccountID()), info
									.getAmount()
									- info.getDrawAmount());
						}
						

						//是否需要生成银行指令
						boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
						boolean bCreateInstruction = false;	
						boolean bCreateInstruction1 = false;	
						long bankID = info.getBankID();
						long bankID1 = n_tfoInfo1.getBankID();
						try {
							//调用此方法后，bankID的值变为银行类型ID
							bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
							bCreateInstruction1 = accountBookOperation.isCreateInstruction(bankID1);
						} catch (Exception e1) {
							log.error("判断传入的银行ID是否需要生成银行指令出错！");
							e1.printStackTrace();
						}
						
						try
						{
							if(bIsValid && bCreateInstruction && bCreateInstruction1) {
								Log.print("*******************开始产生定期部分支取交易指令，并保存**************************");
								try {
									log.debug("------开始定期支取、通知存款支取交易付款指令生成--------");
									Collection coll = new ArrayList();
									//构造参数
									//支取INFO
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									instructionParam.setTransactionTypeID(info.getTransactionTypeID());
									instructionParam.setObjInfo(info);
									instructionParam.setOfficeID(info.getOfficeID());
									instructionParam.setCurrencyID(info.getCurrencyID());
									instructionParam.setCheckUserID(info.getCheckUserID());
									instructionParam.setBankType(bankID);
									instructionParam.setInputUserID(info.getInputUserID());
									coll.add(instructionParam);
									/*开立INFO 部分开立不发指令
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
									//生成银行指令并保存
									IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
									bankInstruction.createSpecialBankInstruction(coll);
									
									log.debug("------生成定期部分支取交易付款指令成功--------");
									
								} catch (Throwable e) {
									log.error("生成定期部分支取交易付款指令失败");
									e.printStackTrace();
									throw new IRollbackException(mySessionCtx, "生成定期部分支取交易付款指令失败："+e.getMessage());
								}
							}
							else {
								Log.print("没有银行接口或不需要生成银行指令！");
							}
						}
						catch (Exception e)
						{
							throw new IRollbackException(mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
						}
						// 开始清零冲销计提利息
						// SubAccountFixedInfo
						// sInfo=saDao.findFixedSubAccountInfoByID(subAccountId);

						// saDao.updateLoanPredrawInterestByID(sInfo.getID(),0.00);
					//}
				}
				else {
						
						//是否需要生成银行指令
						boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
						boolean bCreateInstruction = false;			
						long bankID = info.getBankID();
						try {
							//调用此方法后，bankID的值变为银行类型ID
							bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
						} catch (Exception e1) {
							log.error("判断传入的银行ID是否需要生成银行指令出错！");
							e1.printStackTrace();
						}
						
						try
						{
							if(bIsValid && bCreateInstruction) {
								Log.print("*******************开始产生定期支取、通知存款支取交易指令，并保存**************************");
								try {
									log.debug("------开始定期支取、通知存款支取交易付款指令生成--------");
									//构造参数
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									instructionParam.setTransactionTypeID(info.getTransactionTypeID());
									instructionParam.setObjInfo(info);
									instructionParam.setOfficeID(info.getOfficeID());
									instructionParam.setCurrencyID(info.getCurrencyID());
									instructionParam.setCheckUserID(info.getCheckUserID());
									instructionParam.setBankType(bankID);
									instructionParam.setInputUserID(info.getInputUserID());
									
									//生成银行指令并保存
									IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
									bankInstruction.createBankInstruction(instructionParam);
									
									log.debug("------生成定期支取、通知存款支取交易付款指令成功--------");
									
								} catch (Throwable e) {
									log.error("生成定期支取、通知存款支取交易付款指令失败");
									e.printStackTrace();
									throw new IRollbackException(mySessionCtx, "生成定期支取、通知存款支取交易付款指令失败："+e.getMessage());
								}
							}
							else {
								Log.print("没有银行接口或不需要生成银行指令！");
							}
						}
						catch (Exception e)
						{
							throw new IRollbackException(mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 根据标识查询定期（通知）支取/转活期交易明细的方法：
	 * 
	 * 1、参数： lID long , 交易的ID
	 * 
	 * 2、返回值： FixedDrawInfo,定期（通知）交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedWithDrawtDAO.findByID()
	 * 得到开立交易的明细类FixedDrawInfo。
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
	 * 根据状态查询的方法：
	 * 
	 * 1、参数： QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值： Collection ,包含FixedDrawInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用Sett_TransFixedWithDrawDAO.findByStatus()方法。
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
	 * 根据交易号查询定期支取交易明细的方法：
	 * 
	 * 1、参数： strTransNo , 开立交易号
	 * 
	 * 2、返回值： FixedDrawInfo,定期交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedContinueDAO.findByID()
	 * 得到开立交易的明细类FixedContinueInfo。
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
			//info.setIsAutoContinue(sub_accountinfo.getSubAccountFixedInfo().getIsAutoContinue());//bug 20370 代码错误 定期部分支取，新存单选择不到期续存，交易查询中显示成到期续存 
			info.setAutocontinuetype(sub_accountinfo.getSubAccountFixedInfo().getAutoContinueType());
			info.setAutocontinueaccountid(sub_accountinfo.getSubAccountFixedInfo().getInterestAccountID());

		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return info;
	}

	/**
	 * 复核匹配的方法：
	 * 
	 * 1、参数： FixedDrawInfo,定期（通知）交易查询条件实体类
	 * 
	 * 2、返回值： Collection ,包含FixedDrawInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用方法：Sett_TransFixedWithDrawDAO.match()
	 * 
	 * @roseuid 3F73AEE4034A
	 */
	public Collection drawMatch(TransFixedDrawInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
		try {
			// 控制匹配复核状态
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
	 * 判断交易记录是否被修改过的暂存方法：
	 * 
	 * 1、参数： lID, 交易ID。 long, 原来的TouchTime。
	 * 
	 * 2、返回值： boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedWithDrawDAO.findByID,得到最新的交易。
	 * 
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * 
	 * @roseuid 3F73AEE702CC
	 */
	private boolean drawCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransFixedWithDrawDAO dao = new Sett_TransFixedWithDrawDAO();
			TransFixedDrawInfo info = dao.findByID(lID);
			// 判断是否被非法修改过
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
	 * 定期（通知）（支取/转活期）交易的继续的方法：
	 * 
	 * 1、参数： FixedDrawInfo 交易实体类
	 * 
	 * 2、返回值： TransFixedDrawInfo 交易实体类
	 * 
	 * 
	 * 3、逻辑说明： （1）调用方法：XXX.findByID(),得到定期子账户的信息。(从子账户表中取值)
	 * （2）将定期子账户的信息回写到交易实体TransFixedDrawInfo ，并返回。
	 * 
	 * @roseuid 3F73AEEA01B8
	 */
	public TransFixedDrawInfo drawNext(TransFixedDrawInfo info)
			throws IRollbackException, RemoteException {
		TransFixedDrawInfo infoReturn = new TransFixedDrawInfo();
		// 账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		log.debug("----------定期转活期继续-------------");
		SubAccountAssemblerInfo fixedInfo = acctOperation
				.findSubAccountByID(info.getSubAccountID());
		AccountInfo clientInfo = acctOperation.findAccountByID(info
				.getAccountID());
		log.debug("----------定期转活期继续-------------");
		
		// 基本信息
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
		infoReturn.setPreDrawInterest(fixedInfo.getSubAccountFixedInfo().getPreDrawInterest());  //计提利息
		infoReturn.setIsAutoContinue(fixedInfo.getSubAccountFixedInfo().getIsAutoContinue());//是否自动续存
		infoReturn.setAutocontinuetype(fixedInfo.getSubAccountFixedInfo().getAutoContinueType());//自动续存方式
		infoReturn.setAutocontinueaccountid(fixedInfo.getSubAccountFixedInfo().getInterestAccountID());//收息账户id
		// 通知与定期支取利率处理方式不同
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

		// 通知与定期利息处理也不同
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
				log.debug("提前支取利率是：" + info.getAdvanceRate());
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
		log.debug("----------定期转活期成功-------------");
		return infoReturn;
	}

	/**
	 * 定期（通知）存款续期转存交易的暂存方法：
	 * 
	 * 1、参数： FixedContinueInfo,开立 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）本金交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）如果lID不是-1，调用方法this.continueCheckIsTouched,判断要暂存的记录是否被修 改过。
	 * 调用方法Sett_TransFixedContinueDAO.update()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransFixedContinueDAO.updateStatus()更改记录的状态为未保存。
	 * （2）如果lID是-1，调用方法Sett_TransFixedContinueDAO.add()保存交易记录信息。
	 * 调用方法Sett_TransFixedContinueDAO.update()更改记录的状态为未保存。
	 * 
	 * @roseuid 3F73AEEC028D
	 */
	public long continueTempSave(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			// 判断存单号是否重复
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// 新增或更新活期信息
			if (info.getID() <= 0) // 新增
			{
				lReturn = dao.add(info);
				if (lReturn != -1) {
					// 更新状态为未保存
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.TEMPSAVE);
				}
			} else // 修改暂存
			{
				boolean flag = continueCheckIsTouched(info.getID(), info
						.getModifyDate());
				if (flag) {
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					lReturn = dao.update(info);

					if (lReturn != -1) {
						// 更新状态为未保存
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
	 * 定期续期转存交易的保存方法：
	 * 
	 * 1、参数： FixedContinueInfo, 交易实体类
	 * 
	 * 2、返回值： long ,定期交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数FixedContinueInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixedContinueInfo 。 如果非空，说明是修改保存:
	 * 调用方法this.continueCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 
	 * 调用方法：this.continueFindByID(),得到包含原来的交易实体类FixedContinueInfo。
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体FixedContinueInfo。 （2）调用方法：Sett_TransFixedContinueDAO.add() 保存信息。
	 * （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 * （4）调用方法：Sett_TransFixedContinueDAO.update()。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AEEE038A
	 */
	public long continueSave(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		long sessionID = -1;
		// 数据访问对象
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 判断存单号是否重复
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());

			// 获得当前状态
			long lStatus = info.getStatusID();
			// 暂存数据保存
			if (lStatus == SETTConstant.TransactionStatus.TEMPSAVE) {
				log.debug("----------当前状态为暂存-------------");

				// 判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransFixedContinueInfo newInfo = dao.findByID(info.getID());
				long lNewStatusID = newInfo.getStatusID();
				// this.checkStatus(info.getStatusID(), lNewStatusID,
				// SETTConstant.Actions.MODIFYSAVE);
				String errMsg = UtilOperation.checkStatus(info.getStatusID(),
						lNewStatusID, SETTConstant.Actions.MODIFYSAVE);
				// 被修改过
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				// 判断是否被非法修改过
				log.debug("----------判断是否被非法修改过-------------");
				if (this.continueCheckIsTouched(info.getID(), info
						.getModifyDate())) {
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// 未非法修改
					log.debug("----------开始更新定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.update(info);
					log.debug("----------结束更新定期交易信息-------------");
					// 财务处理，设置账户余额
					log
							.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveContinueFixedDeposit(info);
					log
							.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					// 通过工具操作接口类获取新交易号
					String transNo = null;
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					log.debug("----------新交易号是: " + transNo + " -------------");
					info.setTransNo(transNo);
					// 修改
					lReturn = dao.update(info);
					// 修改交易的状态为保存
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);
				}
			} else // 不是暂存
			{
				log.debug("----------当前状态不是暂存-------------");
				// 获取当前交易号
				String transNo = info.getTransNo();
				// 标志位：是否是新增交易号
				boolean bNewTransNo = false;
				if (transNo == null || transNo.equals("")) {
					log.debug("----------是新增交易-------------");
					// 未被保存过，生成新交易号
					bNewTransNo = true;
					// 通过工具操作接口类获取新交易号
					transNo = utilOperation.getNewTransactionNo(info
							.getOfficeID(), info.getCurrencyID(), info
							.getTransactionTypeID());
					info.setTransNo(transNo);
					log.debug("----------新交易号是: " + transNo + " -------------");
					// 保存
					log.debug("----------开始新增定期交易信息-------------");
					log.debug(UtilOperation.dataentityToString(info));
					lReturn = dao.add(info);
					log.debug("----------结束新增定期交易信息-------------");
					// （3）调用方法：进行财务处理。
					log
							.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
					accountBookOperation.saveContinueFixedDeposit(info);
					log
							.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
					// 回写网银指令
					if (info.getInstructionNo() != null
							&& info.getInstructionNo().length() > 0) {
						log.info("---------是网银指令----------");
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
					// 修改交易的状态为保存。
					lReturn = dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.SAVE);

				} else {
					// 被保存过， 即保存再保存
					// 判断状态是否合法
					log.debug("----------判断状态是否被非法修改过-------------");
					TransFixedContinueInfo newInfo = dao.findByID(info.getID());
					long lNewStatusID = newInfo.getStatusID();
					// this.checkStatus(info.getStatusID(), lNewStatusID,
					// SETTConstant.Actions.MODIFYSAVE);
					String errMsg = UtilOperation.checkStatus(info
							.getStatusID(), lNewStatusID,
							SETTConstant.Actions.MODIFYSAVE);
					// 被修改过
					if (errMsg != null && !errMsg.equals("")) {
						throw new IRollbackException(mySessionCtx, errMsg);
					}
					// 判断是否被非法修改过
					log.debug("----------是保存再保存-------------");
					if (this.continueCheckIsTouched(info.getID(), info
							.getModifyDate())) {
						throw new IRollbackException(mySessionCtx, "Sett_E130");
					} else {
						// 财务处理，删除数据库中的已有的存单
						log.debug("------开始删除账簿信息--------");
						accountBookOperation
								.deleteContinueFixedDeposit(newInfo);
						log.debug("------结束删除账簿信息--------");
						// 未非法修改
						log.debug("----------开始更新定期交易信息-------------");
						log.debug(UtilOperation.dataentityToString(info));
						lReturn = dao.update(info);
						log.debug("----------结束更新定期交易信息-------------");
						// 财务处理，设置账户余额
						log
								.debug("----------开始accountBookOperation::saveOpenFixedDeposit-------------");
						accountBookOperation.saveContinueFixedDeposit(info);
						log
								.debug("----------结束accountBookOperation::saveOpenFixedDeposit-------------");
						// 修改
						lReturn = dao.update(info);
						// 修改交易的状态为保存
						lReturn = dao.updateStatus(info.getID(),
								SETTConstant.TransactionStatus.SAVE);
					}
				}
				/**
				 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
				 */
				if (info.getInutParameterInfo() != null) {
					log.debug("------提交审批--------");
					// 设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
					InutParameterInfo tempInfo = info.getInutParameterInfo();
					tempInfo.setUrl(tempInfo.getUrl() + lReturn);
					tempInfo.setTransID(transNo);// 这里保存的是交易编号
					tempInfo.setDataEntity(info);
					// 提交审批
					FSWorkflowManager.initApproval(info.getInutParameterInfo());
					// 更新状态到审批中
					dao.updateStatus(info.getID(),
							SETTConstant.TransactionStatus.APPROVALING);
					log.debug("------提交审批成功--------");

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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期续期转存交易的删除方法：
	 * 
	 * 1、参数： FixedContinueInfo 交易实体类
	 * 
	 * 2、返回值： long ,被删除的定期交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）调用方法this.continueCheckIsTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数FixedContinueInfo 中的交易实体类的状态， 如果是暂存：
	 * 
	 * 调用方法：Sett_TransFixedContinueDAO.updateStatus。修改交易的状态为删除（无效）?
	 * 
	 * 如果是保存：
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体FixedContinueInfo
	 * 
	 * 调用方法：Sett_TransFixedContinueDAO.updateStatus。修改交易的状态为删除（无效）?
	 * 
	 * @roseuid 3F73AEF1017B
	 */
	public long continueDelete(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.DELETE);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				// 判断是否暂存
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
						log.info("---------是网银指令----------");
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期续期转存存款交易的复核方法：
	 * 
	 * 1、参数： FixedContinueInfo 交易实体类 2、返回值： long ,被复核的定期（通知）本金交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.continueCheckIsTouched,判断要复核的记录是否被修改过，否则抛出?
	 * 常“您要复核的单据已被修改，请检查！”。
	 * （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 * （3）调用方法：Sett_TransFixedContinueDAO.updateStatus。修改交易的状态为复核。
	 * 
	 * @roseuid 3F73AEF40035
	 */
	public long continueCheck(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();

		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				// 复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）

				// 财务操作，放开账户余额，
				accountBookOperation.checkContinueFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				//是否有银企接口
				boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
				//是否需要生成银行指令
				boolean bCreateInstruction = false;
				long bankID = info.getInterestBankID();
				try {
					//调用此方法后，bankID的值变为银行类型ID
					bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
				} catch (Exception e1) {
					log.error("判断传入的银行ID是否需要生成银行指令出错！");
					e1.printStackTrace();
				}
				
				try
				{
					if(bIsValid && bCreateInstruction) {
						log.debug("------开始定期续期转存交易指令生成，利息--------");
						try {
							log.debug("------开始定期续期转存交易指令生成，利息--------");
							//构造参数
							CreateInstructionParam instructionParam = new CreateInstructionParam();
							instructionParam.setTransactionTypeID(info.getTransactionTypeID());
							instructionParam.setObjInfo(info);
							instructionParam.setOfficeID(info.getOfficeID());
							instructionParam.setCurrencyID(info.getCurrencyID());
							instructionParam.setCheckUserID(info.getCheckUserID());
							instructionParam.setBankType(bankID);
							instructionParam.setInputUserID(info.getInputUserID());
							
							//生成银行指令并保存
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createBankInstruction(instructionParam);
							
							log.debug("------生成定期续期转存交易指令成功，利息--------");
							
						} catch (Throwable e) {
							log.error("生成定期续期转存交易指令失败");
							e.printStackTrace();
							throw new IRollbackException(mySessionCtx, "生成定期续期转存交易指令失败："+e.getMessage());
						}
						log.debug("------结束定期续期转存交易指令生成，利息--------");
					}
//					else {
//						Log.print("没有银行接口或不需要生成银行指令！");
//					}
				}
				catch (Exception e)
				{
					throw new IRollbackException(mySessionCtx, "保存银行转账指令出错！" + e.getMessage());
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 定期续期转存交易的取消复核方法：
	 * 
	 * 1、参数： FixedContinueInfo 交易实体类 2、返回值： long ,被取消复核的定期交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.continueCheckIsTouched,判断要复核的记录是否被修改过，否则抛出?
	 * 常“您要取消复核的单据已被修改，请检查！”。
	 * 
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处 理。
	 * （3）调用方法：Sett_TransFixedContinueDAO.updateStatus。修改交易的状态为保存。
	 * 
	 * @roseuid 3F73AEF60312
	 */
	public long continueCancelCheck(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;
		// 加锁时使用
		long sessionID = -1;

		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		// 工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		// 账簿操作接口类
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try {
			// 对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedContinueInfo newInfo = dao.findByID(info.getID());
			long lNewStatusID = newInfo.getStatusID();
			// this.checkStatus(info.getStatusID(), lNewStatusID,
			// SETTConstant.Actions.CANCELCHECK);
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			// 检测是否被修改过
			boolean flag = continueCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				// 取消复核
				accountBookOperation.cancelCheckContinueFixedDeposit(info);
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令----------");
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
				// 更新状态到：未复核或已审批(根据该业务是否有审批流判断)
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
			// 解锁
			try {
				if (sessionID != -1) // 初始值已经改变，说明已经加锁，因此需要解锁
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
	 * 根据标识查询定期续期转存交易明细的方法：
	 * 
	 * 1、参数： lID long , 开立交易的ID
	 * 
	 * 2、返回值： FixedContinueInfo,定期交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedContinueDAO.findByID()
	 * 得到开立交易的明细类FixedContinueInfo。
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
	 * 根据交易号查询定期续期转存交易明细的方法：
	 * 
	 * 1、参数： strTransNo , 开立交易号
	 * 
	 * 2、返回值： FixedContinueInfo,定期交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedContinueDAO.findByID()
	 * 得到开立交易的明细类FixedContinueInfo。
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
	 * 根据状态查询的方法：
	 * 
	 * 1、参数： QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值： Collection ,包含FixedContinueInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用Sett_TransFixedContinueDAO.findByStatus()方法。
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
	 * 复核匹配的方法：
	 * 
	 * 1、参数： FixedContinueInfo,定期交易查询条件实体类
	 * 
	 * 2、返回值： Collection ,包含FixedContinueInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用方法：Sett_TransFixedContinueDAO.match()
	 * 
	 * @roseuid 3F73AEFF00BC
	 */
	public Collection continueMatch(TransFixedContinueInfo info)
			throws IRollbackException, RemoteException {
		Collection coll = null;
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		try {
			// 控制匹配复核状态
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
	 * 定期续期转存交易的继续的方法：
	 * 
	 * 1、参数： FixedContinueInfo 交易实体类
	 * 
	 * 2、返回值： FixedContinueInfo 交易实体类
	 * 
	 * 
	 * 3、逻辑说明： （1）调用方法：XXX.findByID(),得到定期子账户的信息。(从子账户表中取值)
	 * （2）将定期子账户的信息回写到交易实体FixedContinueInfo ，并返回。
	 * 
	 * @roseuid 3F73AF010141
	 */
	public TransFixedContinueInfo continueNext(TransFixedContinueInfo info) throws IRollbackException, RemoteException {
		TransFixedContinueInfo infoReturn = new TransFixedContinueInfo();
		//账户操作接口类
		AccountOperation acctOperation = new AccountOperation();
		log.debug("----------定期转存继续-------------");

		SubAccountAssemblerInfo fixedInfo = acctOperation.findSubAccountByID(info.getSubAccountID());
		log.debug("----------定期转存继续-------------");
		//基本信息
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

		//临时处理
		//infoReturn.setPreDrawInterest(50.00);
		//infoReturn.setPayableInterest(10.00);
		//利息信息
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
		
		//added by mzh_fu 2008/02/22 解决内存泄露问题
		io.closeConnection();
		
		log.debug("----------定期转存成功-------------");
		return infoReturn;
	}

	/**
	 * 判断交易记录是否被修改过的暂存方法：
	 * 
	 * 1、参数： lID, 交易ID。 long, 原来的TouchTime。
	 * 
	 * 2、返回值： boolean, 如果被修改，返回true；否则，返回false。
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransFixedContinueDAO.findByID,得到最新的交易。
	 * 
	 * （2）判断传入的TouchTime是否与查询出的一致，如果不一致，返回true；否则返回false?
	 * 
	 * @roseuid 3F73AF0302AC
	 */
	private boolean continueCheckIsTouched(long lID, Timestamp tsTouchTime)
			throws IRollbackException, RemoteException {
		try {

			Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
			TransFixedContinueInfo info = dao.findByID(lID);
			// 判断是否被非法修改过
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
	 * 定期（通知）支取/转活期交易保存前检测是否重复的方法：
	 * 
	 * 1、参数： TransFixedDrawInfo 交易实体类
	 * 
	 * 2、返回值： String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明： （1）判断参数TransFixedDrawInfo,中的交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 用方法：Sett_TransFixedWithDraw.checkIsDuplicate()判断是否重复。
	 * 
	 * @roseuid 3F73AF06006B
	 */

	/**
	 * 定期续期转存交易保存前检测是否重复的方法：
	 * 
	 * 1、参数： FixedContinueInfo 交易实体类
	 * 
	 * 2、返回值： String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明： （1）判断参数FixedContinueInfo,中的交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 用方法：Sett_TransFixedContinueDAO.checkIsDuplicate()判断是否重复。
	 * 
	 * @roseuid 3F73AF080349
	 */

	/**
	 * 定期（通知）存款开立交易的暂存方法：
	 * 
	 * 1、参数： FixdOpenInfo,开立 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）如果lID不是-1，调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过 。
	 * 调用方法Sett_TransOpenFixedDepositDAO.update()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransOpenFixedDepositDAO.updateStatus()更改记录的状态为未保存。
	 * 
	 * （2）如果lID是-1，调用方法Sett_TransOpenFixedDepositDAO.add()保存交易记录信息。
	 * 
	 * 调用方法Sett_TransOpenFixedDepositDAO.updateStatus()更改记录的状态为未保存。
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
			System.out.println("-------------后台EJB:  开始暂存!1111111111111");
			// 判断存单号是否重复
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			System.out.println("-------------后台EJB:  开始暂存!2222222222222222");
			// 新增或更新信息
			if (info.getID() <= 0) // 表示页面传进来的值是错误的
			{
				// 更新状态为未保存
				lReturn = -1;
				System.out
						.println("-------------后台EJB:  开始暂存!3333333333333333");
			} else // 修改暂存
			{
				System.out
						.println("-------------后台EJB:  开始暂存!444444444444444444444443");

				// 判断状态是否合法
				log.debug("----------判断状态是否被非法修改过-------------");
				TransFixedChangeInfo newInfo = dao.findByID(info.getID());

				long lNewStatusID = newInfo.getDepositBillStatusID();
				System.out.println("原来的换开定期存单的状态:"
						+ info.getDepositBillStatusID());
				System.out.println("刚刚从数据库中查到的的换开定期存单的状态:" + lNewStatusID);
				String errMsg = UtilOperation.checkStatus(info
						.getDepositBillStatusID(), lNewStatusID,
						SETTConstant.Actions.MODIFYSAVE);
				// 被修改过
				if (errMsg != null && !errMsg.equals("")) {
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				System.out.println("原来的换开定期存单的修改时间:" + info.getModifyDate());
				System.out.println("刚刚从数据库中查到的的换开定期存单的修改时间:"
						+ newInfo.getModifyDate());
				// 判断是否被非法修改过

				log.debug("----------判断是否被非法修改过-------------");
				if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
					log.debug("----------被非法修改过,交易失败-------------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				} else {
					// 将数据更新 (相当于添加和修改功能均在此)
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
	 * 定期（通知）开立交易保存前检测是否重复的方法：
	 * 
	 * 1、参数： FixdOpenInfo 交易实体类
	 * 
	 * 2、返回值： String , 重复时的提示信息；如果不重复，返回null。
	 * 
	 * 3、逻辑说明： （1）判断参数FixdOpenInfo,中的交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 用方法：Sett_TransOpenFixedDeposit.checkIsDuplicate()判断是否重复。
	 * 
	 * @roseuid 3F73AE9300E8
	 */

	/**
	 * 定期（通知）开立交易的保存方法：
	 * 
	 * 1、参数： FixdOpenInfo, 交易实体类
	 * 
	 * 2、返回值： long ,定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）判断参数FixdOpenInfo,中的本金交易实体类的交易编号是否为空。 如果是空，说明是新增保存：
	 * 
	 * 调用方法：XXX.getTransactionNo()得到一个交易号，并将其写入FixdOpenInfo 。 如果非空，说明是修改保存:
	 * 调用方法this.openCheckIsTouched,判断要暂存的记录是否被修改过。
	 * 调用方法：this.openFindByID(),得到包含原来的交易实体类FixdOpenInfo。
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo。 （2）调用方法：Sett_TransOpenFixedDepositDAO.add() 保存信息。
	 * （3）调用方法：AccountDetail.saveOpenFixedDeposit()。进行财务处理。
	 * （4）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus() 。修改交易的状态为保存。
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
		// 数据访问对象
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		log.debug("---------开始changeSave---------------");
		try {
			// 获得当前状态
			// long lStatus = info.getDepositBillStatusID();

			// 判断存单号是否重复
			if (!dao.checkDepositNo(info)) {
				throw new IRollbackException(mySessionCtx, "Sett_E132");
			}
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());

			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.MODIFYSAVE);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			System.out.println("原来的换开定期存单的状态:" + info.getDepositBillStatusID());
			System.out.println("刚刚从数据库中查到的的换开定期存单的状态:" + lNewStatusID);
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.MODIFYSAVE);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			System.out.println("原来的换开定期存单的修改时间:" + info.getModifyDate());
			System.out.println("刚刚从数据库中查到的的换开定期存单的修改时间:"
					+ newInfo.getModifyDate());
			// 判断是否被非法修改过

			log.debug("----------判断是否被非法修改过-------------");
			if (this.openCheckIsTouched(info.getID(), info.getModifyDate())) {
				log.debug("----------被非法修改过,交易失败-------------");
				throw new IRollbackException(mySessionCtx, "Sett_E130");
			} else {
				
				// 回写网银指令() 换开存单 网银状态不做处理
				
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令---------（换开定期存单）----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					  financeInfo.setID(Long.valueOf(info.getInstructionNo()).longValue());
					  financeInfo.setDealUserID(info.getInputUserID());
					  financeInfo.setConfirmDate(info.getExecuteDate());
					  financeInfo.setFinishDate(info.getExecuteDate());
					  financeInfo.setTransNo(info.getTransNo());
					  financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
					  //处理中
					  financeDao.updateStatusAndTransNo(null,financeInfo);
					 
				
					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // 网银的指令号ID
					 financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					 //输入人ID
					 financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					 //输入日期
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.DEAL); // 处理中
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}

				// 将数据更新 (相当于添加和修改功能均在此)
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
		log.debug("---------结束changeSave---------------");
		return lReturn;
		}
	}

	/**
	 * 定期（通知）开立交易的删除方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类
	 * 
	 * 2、返回值： long ,被删除的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明： （1）调用方法this.opencheckIsTouched,判断要删除的记录是否被修改过。
	 * （2）判断参数TransFixedOpenInfo 中的本金交易实体类的状态， 如果是暂存：
	 * 
	 * 调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为删除（无效 ）。 如果是保存：
	 * 
	 * 调用方法：AccountDetail.deleteFixedDeposit()。回滚原来的财务处理。注意参数是原来
	 * 的实体TransFixedOpenInfo
	 * 
	 * 调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为删除（无效 ）。
	 * 
	 * @roseuid 3F73AE9E010B
	 */
	public long changeDelete(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		// 加锁时使用
		long sessionID = -1;

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		log.debug("---------开始changeDelete---------------");
		try {
			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.DELETE);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.DELETE);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			} else {
				System.out.println("得到定期指令号info:" + info.getInstructionNo());
				System.out.println("得到定期指令号newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());
				/* 换开存单 不更改网银数据状态
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令   (换开定期存单删除)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // 网银的指令号ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //输入人ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //输入日期
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.REFUSE); // 拒绝
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// 只更新它的状态为删除即可，其它的数据可以留着(但需不需要留着呢考虑一下，到时候微调一下)
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
		log.debug("---------结束changeDelete---------------");
		return lReturn;
		}
	}

	/**
	 * 定期（通知）存款交易的复核方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类 2、返回值： long ,被复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常? 您要复核的单据已被修改，请检查！”。
	 * （2）调用方法：AccountDetail.checkOpenFixedDeposit()。进行复核的财务处理。
	 * 
	 * （3）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为复核?
	 * 
	 * @roseuid 3F73AEAF02F9
	 */
	public long changeCheck(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();

		try {
			System.out.println("---换开定期存单复核--------开始(EJB)");

			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedChangeInfo newInfo = dao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CHECK);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info
					.getDepositBillStatusID(), lNewStatusID,
					SETTConstant.Actions.CHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E020");
			} else {
				System.out.println("得到定期指令号info:" + info.getInstructionNo());
				System.out.println("得到定期指令号newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());

				/*  换开存单 不更新网银状态
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令   (换开定期存单复核)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // 网银的指令号ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //输入人ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //输入日期
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.FINISH); // 网银指令完成
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// 复核时判断状态ID为未复核（可以不用判断，因为匹配的时候已经判断了）
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
		System.out.println("---换开定期存单复核--------结束(EJB)");
		return lReturn;
		}
	}

	/**
	 * 定期（通知）存款交易的取消复核方法：
	 * 
	 * 1、参数： TransFixedOpenInfo 交易实体类 2、返回值： long ,被取消复核的定期（通知）交易的标识，如果失败，返回-1
	 * 
	 * 3、逻辑说明：
	 * 
	 * （1）调用方法this.opencheckIsTouched,判断要复核的记录是否被修改过，否则抛出异常?
	 * 您要取消复核的单据已被修改，请检查！”。
	 * 
	 * （2）调用方法：AccountDetail.cancelCheckOpenFixedDeposit()。进行取消复核的财务处 理。
	 * 
	 * （3）调用方法：Sett_TransOpenFixedDepositDAO.updateStatus。修改交易的状态为保存?
	 * 
	 * @roseuid 3F73AEB30222
	 */
	public long changeCancelCheck(TransFixedChangeInfo info)
			throws IRollbackException, RemoteException {
		synchronized(lockObj){
		long lReturn = -1;

		Sett_TransChangeFixedDepositDAO depositDao = new Sett_TransChangeFixedDepositDAO();
		try {
			System.out.println("---换开定期存单取消复核--------开始(EJB)");

			// 判断状态是否合法
			log.debug("----------判断状态是否被非法修改过-------------");
			TransFixedChangeInfo newInfo = depositDao.findByID(info.getID());
			// long lNewStatusID = newInfo.getStatusID();
			// String errMsg
			// =UtilOperation.checkStatus(info.getStatusID(),lNewStatusID,SETTConstant.Actions.CANCELCHECK);
			long lNewStatusID = newInfo.getDepositBillStatusID();
			String errMsg = UtilOperation.checkStatus(info.getStatusID(),
					lNewStatusID, SETTConstant.Actions.CANCELCHECK);
			// 被修改过
			if (errMsg != null && !errMsg.equals("")) {
				throw new IRollbackException(mySessionCtx, errMsg);
			}

			// 检测是否被修改过
			boolean flag = openCheckIsTouched(info.getID(), info
					.getModifyDate());
			if (flag) // 被修改过
			{
				throw new IRollbackException(mySessionCtx, "Sett_E022");
			} else {
				System.out.println("得到定期指令号info:" + info.getInstructionNo());
				System.out.println("得到定期指令号newInfo:"
						+ newInfo.getInstructionNo());
				info.setInstructionNo(newInfo.getInstructionNo());
				
				/* 换开存单 不更改网银数据状态
				if (info.getInstructionNo() != null
						&& info.getInstructionNo().length() > 0) {
					log.info("---------是网银指令   (换开定期存单取消复核)----------");
					FinanceInfo financeInfo = new FinanceInfo();
					OBFinanceInstrDao financeDao = new OBFinanceInstrDao();

					financeInfo.setID(Long.valueOf(info.getInstructionNo())
							.longValue()); // 网银的指令号ID
					// financeInfo.setNDepositBillInputuserId(info.getDepositBillInputUserID());
					// //输入人ID
					// financeInfo.setDtDepositBillInputdate(info.getDepositBillInputDate());
					// //输入日期
					financeInfo
							.setNDepositBillStatusId(OBConstant.SettInstrStatus.DEAL); // 网银指令处理中
					financeDao.TransOpenFixdDePositUpdtae(financeInfo);
				}
				*/
				// 取消复核
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
		System.out.println("---换开定期存单取消复核--------结束(EJB)");
		return lReturn;
		}
	}

	/**
	 * 根据标识查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： lID long , 开立交易的ID
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	 * 根据交易号查询定期（通知） 开立交易明细的方法：
	 * 
	 * 1、参数： strTransNo , 开立交易号
	 * 
	 * 2、返回值： TransFixedOpenInfo,定期（通知）开立交易实体类
	 * 
	 * 3、逻辑说明： （1）调用方法：Sett_TransOpenFixedDepositDAO.findByID()
	 * 得到开立交易的明细类TransFixedOpenInfo。
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
	 * 根据状态查询的方法：
	 * 
	 * 1、参数： QueryByStatusConditionInfo, 按状态查询的查询条件实体类。
	 * 
	 * 2、返回值： Collection ,包含TransFixedOpenInfo查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用Sett_TransOpenFixedDepositDAO.findByStatus()方法。
	 * 
	 * @roseuid 3F73AEBB0273
	 */
	public Collection changeFindByStatus(QueryByStatusConditionInfo info)
			throws IRollbackException, RemoteException {
		System.out.println("换开定期存单(changeFindByStatus):----------(开始EJB)");
		Collection coll = null;
		Sett_TransChangeFixedDepositDAO dao = new Sett_TransChangeFixedDepositDAO();
		try {
			coll = dao.findByStatus(info);
		} catch (Exception e) {
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		System.out.println("换开定期存单(changeFindByStatus):----------(结束EJB)");
		return coll;

	}

	/**
	 * 复核匹配的方法：
	 * 
	 * 1、参数： TransFixedOpenInfo,定期（通知）开立交易查询条件实体类
	 * 
	 * 2、返回值： Collection ,包含TransFixedOpenInfo,查询结果实体类的记录集
	 * 
	 * 3、逻辑说明： 调用方法：Sett_TransOpenFixedDepositDAO.match()
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
	 * 审批方法（开立）。
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
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// 如果是自动复核
				if (FSWorkflowManager.isAutoCheck()) {
					TransFixedOpenInfo depositInfo1 = new TransFixedOpenInfo();
					depositInfo1 = this.openFindByID(info.getID());
					// 构造check参数
					// TransFixedOpenInfo depositInfo = new
					// TransFixedOpenInfo();
					// depositInfo = this.openFindByID(info.getID());
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("机核");
					depositInfo1.setCheckUserID(returnInfo.getUserID()); // 最后审批人为复核人

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// 调用openCheck方法
					this.openCheck(depositInfo1);
				}
			}
			// 如果是最后一级,且为审批拒绝,更新状态为已保存
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
	 * 审批方法（支取）。
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
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// 如果是自动复核
				if (FSWorkflowManager.isAutoCheck()) {
					TransFixedDrawInfo depositInfo1 = new TransFixedDrawInfo();
					depositInfo1 = this.drawFindByID(info.getID());
					// 构造check参数
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("机核");
					depositInfo1.setCheckUserID(returnInfo.getUserID());

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// 调用openCheck方法
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
	 * 审批方法（转存）。
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
			// 提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			// 如果是最后一级,且为审批通过,更新状态为已审批
			if (returnInfo.isLastLevel()) {
				dao.updateStatus(info.getID(),
						SETTConstant.TransactionStatus.APPROVALED);
				// 如果是自动复核
				if (FSWorkflowManager.isAutoCheck()) {
					// 构造check参数
					TransFixedContinueInfo depositInfo1 = new TransFixedContinueInfo();
					depositInfo1 = this.continueFindByID(info.getID());
					// depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());
					// depositInfo1.setAbstract("机核");
					depositInfo1.setCheckUserID(returnInfo.getUserID());

					// TransCurrentDepositAssembler dataEntity = new
					// TransCurrentDepositAssembler(depositInfo);

					// 调用openCheck方法
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
	 * 取消审批方法（定期开立，通知开立）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法 取消审批：将业务记录状态置为已保存即可
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
			// 如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if (FSWorkflowManager.isAutoCheck()
					&& openInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				// 取消复核
				this.openCancelCheck(openInfo);
				// 取消审批
				lReturn = fixedDao.updateStatus(openInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& openInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				// 取消审批
				lReturn = fixedDao.updateStatus(openInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			// 将审批记录表内的该交易的审批记录状态置为无效
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
	 * 取消审批方法（定期支取，通知支取）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法 取消审批：将业务记录状态置为已保存即可
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
			// 如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if (FSWorkflowManager.isAutoCheck()
					&& drawInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				// 取消复核
				this.drawCancelCheck(drawInfo);
				// 取消审批
				lReturn = drawDao.updateStatus(drawInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& drawInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				// 取消审批
				lReturn = drawDao.updateStatus(drawInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			// 将审批记录表内的该交易的审批记录状态置为无效
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
	 * 取消审批方法（定期续存）。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
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
			// 如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if (FSWorkflowManager.isAutoCheck()
					&& continueInfo.getStatusID() == SETTConstant.TransactionStatus.CHECK) {
				//取消复核
				this.continueCancelCheck(continueInfo);
				//取消审批
				lReturn = continueDao.updateStatus(continueInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			} else if (!FSWorkflowManager.isAutoCheck()
					&& continueInfo.getStatusID() == SETTConstant.TransactionStatus.APPROVALED) {
				//取消审批
				lReturn = continueDao.updateStatus(continueInfo.getID(),
						SETTConstant.TransactionStatus.SAVE);
			}

			//将审批记录表内的该交易的审批记录状态置为无效
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
