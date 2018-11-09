/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcurrentdeposit.bizlogic;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.project.wisgfc.tran.trustcollection.dao.TrustCollectionDao;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.accountbook.bizlogic.AccountBookOperation;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.obinstruction.bizlogic.TransInfo;
import com.iss.itreasury.settlement.repaymentloancorresponding.bizlogic.LoanDetailBean;
import com.iss.itreasury.settlement.repaymentloancorresponding.bizlogic.RepaymentLoanCorrespondingBean;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDeposit;
import com.iss.itreasury.settlement.transfixeddeposit.bizlogic.TransFixedDepositHome;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedWithDrawDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedDrawInfo;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.TransFixedOpenInfo;
import com.iss.itreasury.settlement.transloan.bizlogic.TransLoan;
import com.iss.itreasury.settlement.transloan.bizlogic.TransLoanHome;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.transspecial.bizlogic.TransSpecial;
import com.iss.itreasury.settlement.transspecial.bizlogic.TransSpecialHome;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.transupsave.dao.Sett_TransUpRecordDAO;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
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
public class TransCurrentDepositEJB implements SessionBean
{
	private javax.ejb.SessionContext mySessionCtx = null;
	final static private int ACTION_CHECK = 0;
	final static private int ACTION_CANCEL_CHECK = 1;
	final static long serialVersionUID = 3206093459760846163L;
	
	private final static  Object lockObj = new Object();  //静态
	
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * ejbActivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbActivate() throws RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * @exception javax.ejb.CreateException 异常说明。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
	}
	/**
	 * ejbPassivate method comment
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void ejbPassivate() throws RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * @exception java.rmi.RemoteException 异常说明。
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
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
		throws RemoteException
	{
		mySessionCtx = ctx;
	}
	/**
	 * 
	 * Method isTouch
	 * @descriptin  判断是否被非法修改过
	 * @return long 根据条件查找
	 * @throws RemoteException
	 */
	private boolean isTouch(
		TransCurrentDepositAssembler info,
		Sett_TransCurrentDepositDAO currentDepositDao)
		throws RemoteException, IRollbackException
	{
		
		try
		{
			//判断是否被非法修改过
			Timestamp lastTouchDate;
			lastTouchDate =
				currentDepositDao.findTouchDate(
					info.getSett_TransCurrentDepositInfo().getId());
			//@TBD: get touch date from info class
			Timestamp curTouchDate =
				info.getSett_TransCurrentDepositInfo().getModifyTime();
			if (curTouchDate == null
				|| lastTouchDate.compareTo(curTouchDate) != 0)
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
			throw new IRollbackException(
				mySessionCtx,
				"SQLException in TransCurrentDepositEJB",
				e);
		}
	}
	private boolean isTouch(
		TransOnePayMultiReceiveInfo info,
		Sett_TransOnePayMultiReceiveDAO dao)
		throws RemoteException, IRollbackException
	{
		try
		{
			//判断是否被非法修改过
			Timestamp lastTouchDate;
			lastTouchDate = dao.findTouchDate(info.getId());
			//@TBD: get touch date from info class
			Timestamp curTouchDate = info.getModifyDate();
			if (curTouchDate == null
				|| lastTouchDate.compareTo(curTouchDate) != 0)
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
			throw new IRollbackException(
				mySessionCtx,
				"SQLException in TransCurrentDepositEJB",
				e);
		}
	}
	/**
	 * 
	 * Method check
	 * @descriptin  内部方法处理复核和取消复核
	 * @param  checkOrCancelCheck 复核还是取消复核
	 * @return long 
	 * @throws RemoteException，IException
	 */
	private long check(
		int checkOrCancelCheck,
		TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();
		TransCurrentDepositInfo transCurrentDepositInfo =
			info.getSett_TransCurrentDepositInfo();
		try
		{
			//对客户加锁
			sessionID =
				utilOperation.waitUtilSuccessLock(
					info.getSett_TransCurrentDepositInfo().getPayAccountID());
			//校验是否落地处理交易：如果产生指令的交易并且摘要为落地处理的交易在复核时，抛出异常。
			if(info.getSett_TransCurrentDepositInfo().isAutoCreateBankInstruction()&& (checkOrCancelCheck == ACTION_CHECK))
			{
				if(info.getSett_TransCurrentDepositInfo().getAbstractStr() != null && info.getSett_TransCurrentDepositInfo().getAbstractStr().startsWith("落地处理"))
				{
					throw new IRollbackException(mySessionCtx, "Sett_E058");
				}
			}
			//校验状态是否正确
			TransCurrentDepositInfo newInfo =
				this.findBySett_TransCurrentDepositID(
					transCurrentDepositInfo.getId());
			String errMsg = "";
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getSett_TransCurrentDepositInfo().getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CHECK);
			}
			else
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getSett_TransCurrentDepositInfo().getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CANCELCHECK);
			}
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				if (isTouch(info, currentDepositDao))
					throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				if (isTouch(info, currentDepositDao))
					throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			currentDepositDao.update(info.getSett_TransCurrentDepositInfo());
			
			//通存通兑业务校验
			if(newInfo.getTransactionTypeID()==SETTConstant.TransactionType.INTERNALVIREMENT)
			{//如果是内部转账业务（目前只支持内部转账业务20110714）
				if(newInfo.getIsDifOffice()==Constant.TRUE)
				{
					//如果通存通兑业务
					if(com.iss.itreasury.util.Env.getHQOFFICEID()<=0)
					{
						throw new IRollbackException(
								mySessionCtx,
								"本业务属于通存通兑业务，但是没有设置总部，交易失败，请检查总部设置");
					}
					this.DifOfficeDeal(newInfo);
				}
			}
			
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				System.out.println("新test&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				//复核交易记录
				log.info("--------------开始AccountBook复核交易记录--------------");
				log.info(
					"--------transCurrentDepositInfo---------------"
						+ transCurrentDepositInfo.isAutoCreateBankInstruction());
				accountBookOperation.checkCurrentAccountDetails(
					transCurrentDepositInfo);
				System.out.println("新test************************************");
				currentDepositDao.updateStatus(
					info.getSett_TransCurrentDepositInfo().getId(),
					SETTConstant.TransactionStatus.CHECK);
				log.info("--------------结束AccountBook复核交易记录--------------");
				String instructionNo = info.getSett_TransCurrentDepositInfo().getInstructionNo();
				if (instructionNo!= null && instructionNo.length()> 0)
				{
					if (instructionNo.indexOf("T")<0) {
					log.info("---------是网银指令----------");
						if (info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT
							||
							info
								.getSett_TransCurrentDepositInfo()
								.getTransactionTypeID()
								== SETTConstant.TransactionType.INTERNALVIREMENT
							|| info
								.getSett_TransCurrentDepositInfo()
								.getTransactionTypeID()
								== SETTConstant.TransactionType.SUBCLIENT_BANKPAY
							|| info
								.getSett_TransCurrentDepositInfo()
								.getTransactionTypeID()
								== SETTConstant.TransactionType.FUND_REQUEST)
						{
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						
							financeInfo.setID(
								Long
									.valueOf(
										info
											.getSett_TransCurrentDepositInfo()
											.getInstructionNo())
									.longValue());
							financeInfo.setDealUserID(
								info
									.getSett_TransCurrentDepositInfo()
									.getInputUserID());
							financeInfo.setConfirmDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setFinishDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setTransNo(
								info
									.getSett_TransCurrentDepositInfo()
									.getTransNo());
							financeInfo.setStatus(
								OBConstant.SettInstrStatus.FINISH);
							//modify by zcwang 2007-3-29
							currentDepositDao.updateStatusAndTransNo(financeInfo);
						}
					}
				}
				
				log.info("--------------开始生成银行指令--------------");
				//判断交易是否需要生成指令，主要用于对落地处理的判断，落地处理，不需要指令生成
				if(transCurrentDepositInfo.isAutoCreateBankInstruction()) {
					//if(false) {  //由于中交项目不需要生成银行指令所以把判断条件改为false	

					/***********构造银行付款指令**********/
					//是否有银企接口
					boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
					//是否需要生成银行指令
					boolean bCreateInstruction = false;
					long bankID = transCurrentDepositInfo.getBankID();
					try {
						//调用此方法后，bankID的值变为银行类型ID
						bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
					} catch (Exception e1) {				
						log.error("判断传入的银行ID是否需要生成银行指令出错！");
						e1.printStackTrace();
					}
					
					if(bIsValid && bCreateInstruction) {//有银企接口并且是需要生成银行指令
						Log.print("*******************开始产生银行收款指令，并保存**************************");
						try {
							//构造参数
							CreateInstructionParam instructionParam = new CreateInstructionParam();
							instructionParam.setTransactionTypeID(transCurrentDepositInfo.getTransactionTypeID());
							instructionParam.setObjInfo(transCurrentDepositInfo);
							instructionParam.setOfficeID(transCurrentDepositInfo.getOfficeID());
							instructionParam.setCurrencyID(transCurrentDepositInfo.getCurrencyID());
							instructionParam.setCheckUserID(transCurrentDepositInfo.getCheckUserID());
							instructionParam.setBankType(bankID);
							instructionParam.setInputUserID(transCurrentDepositInfo.getInputUserID());
							//生成银行指令并保存
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createBankInstruction(instructionParam);
							
							log.debug("------生成银行活期指令结束--------");
							
						} catch (Throwable e) {
							log.error("生成银行付款指令失败");
							e.printStackTrace();
							throw new IRollbackException(mySessionCtx, "生成银行付款指令失败："+e.getMessage());
						}
					}
					else {
						Log.print("没有银行接口或不需要生成银行指令！");
					}
				}
				else {
					log.info("落地处理不需要指令形成！");
				}
				log.info("--------------结束生成银行指令--------------");
			}
			else
			{
				//取消复核交易记录
				log.info("--------------开始取消AccountBook复核交易记录--------------");
				accountBookOperation.unCheckCurrentAccountDetails(
					transCurrentDepositInfo);
				//					更新状态到：未复核或已审批(根据该业务是否有审批流判断)
				long lCancelCheckStatus = FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(transCurrentDepositInfo.getOfficeID(),
						transCurrentDepositInfo.getCurrencyID(),
						Constant.ModuleType.SETTLEMENT,
						transCurrentDepositInfo.getTransactionTypeID(),
						-1));
				currentDepositDao.updateStatus(
					info.getSett_TransCurrentDepositInfo().getId(),
					lCancelCheckStatus);
				//清空复核人
				currentDepositDao.updateCheckUser(
					info.getSett_TransCurrentDepositInfo().getId(), 
					-1);
				
				log.info("--------------结束取消AccountBook复核交易记录--------------");
				String instructionNo = info.getSett_TransCurrentDepositInfo().getInstructionNo();
				if (instructionNo != null && instructionNo.length()> 0)
				{
					log.info("---------是网银指令----------");
					if (instructionNo.indexOf("T")<0) {
						if (info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY
							||
							info
							.getSett_TransCurrentDepositInfo()
							.getTransactionTypeID()
							== SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT
							|| info
								.getSett_TransCurrentDepositInfo()
								.getTransactionTypeID()
								== SETTConstant.TransactionType.INTERNALVIREMENT
							|| info
								.getSett_TransCurrentDepositInfo()
								.getTransactionTypeID()
								== SETTConstant.TransactionType.SUBCLIENT_BANKPAY)
						{
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
							financeInfo.setID(
								Long
									.valueOf(
										info
											.getSett_TransCurrentDepositInfo()
											.getInstructionNo())
									.longValue());
							financeInfo.setDealUserID(
								info
									.getSett_TransCurrentDepositInfo()
									.getInputUserID());
							financeInfo.setConfirmDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setFinishDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setTransNo(
								info
									.getSett_TransCurrentDepositInfo()
									.getTransNo());
							financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
							//financeDao.updateStatusAndTransNo(null, financeInfo);
							//modify by xwhe 2008-11-18 网银银行付款指令自动复核交易取消复核时自动修改网银指令状态
							boolean bIsValid = Config.getBoolean(Config.SETT_CANCELCHECKBANKPAYOPTION, false);
							if(bIsValid &&
									info
									.getSett_TransCurrentDepositInfo()
									.getTransactionTypeID()
									== SETTConstant.TransactionType.BANKPAY &&
									info.getSett_TransCurrentDepositInfo().getInputUserID()==info.getSett_TransCurrentDepositInfo().getCheckUserID())
							{
								boolean autoDelBankInstruction = true;
								info.getSett_TransCurrentDepositInfo().setAutoDelBankInstruction(autoDelBankInstruction);
								TransCurrentDepositDelegation delegation = new TransCurrentDepositDelegation();		
								TransCurrentDepositAssembler  delInfo = new TransCurrentDepositAssembler( delegation.findByTransNo(info.getSett_TransCurrentDepositInfo().getTransNo()));
								long return1 = delegation.delete(delInfo);
							     if(return1>0)
							     {
							     Sett_TransUpRecordDAO stdao=new Sett_TransUpRecordDAO();
						         stdao.del(info.getSett_TransCurrentDepositInfo().getId());
							     }	
							     //将银行付款交易银行返回的错误信息回填至网银指令的备注栏
							     financeInfo.setReject(info
											.getSett_TransCurrentDepositInfo().getCheckAbstractStr());
							     financeInfo.setStatus(OBConstant.SettInstrStatus.REFUSE);
							}
							
							//modify by zcwang 2007-3-29
							currentDepositDao.updateStatusAndTransNo(financeInfo);
						}
					}
				}
			}
			//解锁
			if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				utilOperation.releaseLock(
					info.getSett_TransCurrentDepositInfo().getPayAccountID(),
					sessionID);
		}
		//modified by mzh_fu 2007/05/06		
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
			e.printStackTrace();
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return transCurrentDepositInfo.getId();
		}
	}
	private long check(
		int checkOrCancelCheck,
		TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation;
		accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//校验状态是否正确
			TransOnePayMultiReceiveInfo newInfo =
				this.findBySett_TransOnePayMultiReceiveID(info.getId());
			String errMsg = "";
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CHECK);
			}
			else
			{
				errMsg =
					UtilOperation.checkStatus(
						info.getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.CANCELCHECK);
			}
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			//判断是否被修改
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				if (isTouch(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E020");
			}
			else
			{
				if (isTouch(info, dao))
					throw new IRollbackException(mySessionCtx, "Sett_E024");
			}
			dao.update(info);
			if (checkOrCancelCheck == ACTION_CHECK)
			{
				System.out.println("曲来安测试列名无效test=====================");
				//复核交易记录
				log.info("--------------开始AccountBook复核交易记录--------------");
				accountBookOperation.checkOnePayMultiReceive(info);
				dao.updateStatus(
					info.getId(),
					SETTConstant.TransactionStatus.CHECK);
				log.info("--------------结束AccountBook复核交易记录--------------");
			}
			else
			{
				//取消复核交易记录
				log.info("--------------开始取消AccountBook复核交易记录--------------");
				accountBookOperation.cancelCheckOnePayMultiReceive(info);
				//更新状态到：未复核
				dao.updateStatus(
					info.getId(),
					SETTConstant.TransactionStatus.SAVE);
				log.info("--------------结束取消AccountBook复核交易记录--------------");
			}
			//解锁
			if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
				utilOperation.releaseLock(info.getAccountID(), sessionID);
		}
		//modified by mzh_fu 2007/05/06	
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
		return info.getId();
		}
	}
	/**
	 *Save part of information during transaction
	 */
	private long partSave(
		TransCurrentDepositAssembler info,
		Sett_TransCurrentDepositDAO currentDepositDao)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		long partSave_ID = -1;
		//账簿操作接口类 
		AccountOperation accountOperation = new AccountOperation();
		try
		{
			TransCurrentDepositInfo transCurrentDepositInfo =
				info.getSett_TransCurrentDepositInfo();
			if (currentDepositDao.findByID(transCurrentDepositInfo.getId())
				== null)
			{
				//新增或更新活期信息
				partSave_ID = currentDepositDao.add(info.getSett_TransCurrentDepositInfo());
				String instructionNo = info.getSett_TransCurrentDepositInfo().getInstructionNo();
				if (instructionNo!= null && instructionNo.length() > 0)
				{
					if (instructionNo.indexOf("T")<0) {
						log.info("---------是网银指令----------");
						if (info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.BANKPAY
							||info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER
							||info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY
							||info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT
							||info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.INTERNALVIREMENT
							|| info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.SUBCLIENT_BANKPAY
							|| info.getSett_TransCurrentDepositInfo().getTransactionTypeID()== SETTConstant.TransactionType.FUND_REQUEST)
						{
							FinanceInfo financeInfo = new FinanceInfo();
							OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
							financeInfo.setID(
								Long
									.valueOf(
										info
											.getSett_TransCurrentDepositInfo()
											.getInstructionNo())
									.longValue());
							financeInfo.setDealUserID(
								info
									.getSett_TransCurrentDepositInfo()
									.getInputUserID());
							financeInfo.setConfirmDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setFinishDate(
								info
									.getSett_TransCurrentDepositInfo()
									.getExecuteDate());
							financeInfo.setTransNo(
								info
									.getSett_TransCurrentDepositInfo()
									.getTransNo());
							
							//modify by xwhe 2009-05-07					
							//currentDepositDao.findstatusAndTransNO(financeInfo);
							
							financeInfo.setStatus(OBConstant.SettInstrStatus.DEAL);
							
							//financeDao.updateStatusAndTransNo(null, financeInfo);
							//modify by zcwang 2007-3-29
							currentDepositDao.updateStatusAndTransNo(financeInfo);
						}
					}
				}
			}
			else
				//更新
				{
				partSave_ID = currentDepositDao.update(
					info.getSett_TransCurrentDepositInfo());
			}
			//外部账户处理
			if(info.getSett_TransCurrentDepositInfo().getTransactionTypeID() != SETTConstant.TransactionType.INTERNALVIREMENT)
			{
				accountOperation.saveExternalAccount(
				info
					.getSett_TransCurrentDepositInfo()
					.getExternalAccountInfo());
			}	
			info.getSett_TransCurrentDepositInfo().setId(partSave_ID);
		}
		//modified by mzh_fu 2007/05/06			
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
		return info.getSett_TransCurrentDepositInfo().getId();
		}
	}
	private long partSave(
		TransOnePayMultiReceiveInfo info,
		Sett_TransOnePayMultiReceiveDAO dao)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		//账簿操作接口类 
		AccountOperation accountOperation = new AccountOperation();
		try
		{
			if (dao.findByID(info.getId()) == null)
				//新增或更新活期信息
				dao.add(info);
			else
				//更新
				dao.update(info);
			//外部账户处理
			//accountOperation.saveExternalAccount(info.getExternalAccountInfo());
		}
		//modified by mzh_fu 2007/05/06	
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
		return info.getId();
		}
	}
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		TransCurrentDepositInfo queryTransCurrentDepInfo =
			info.getSett_TransCurrentDepositInfo().getQeureyInfo();
		TransCurrentDepositAssembler depositAssemblerInfo =
			new TransCurrentDepositAssembler(queryTransCurrentDepInfo);

		Collection c = this.findByConditions(depositAssemblerInfo, -1, true);
		if (c != null && c.size() > 0)
		{
			if (c.size() == 1)
			{
				TransCurrentDepositInfo tmpInfo =
					(TransCurrentDepositInfo) ((ArrayList) c).get(0);
				if (tmpInfo.getId()
					== info.getSett_TransCurrentDepositInfo().getId())
					return SETTConstant.PreSaveResult.NORMAL;
			}
			return SETTConstant.PreSaveResult.REPEATED;
		}
		return SETTConstant.PreSaveResult.NORMAL;
		}
	}
	/**
	 * Method preSave.
	 * @param info
	 * @return int 0: 正常 1: 委付凭证不可用--提示输入 2: 票据不可用 3: 重复交易记录 4: 账户透支
	 * @throws RemoteException
	 */
	public long preSave(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		TransOnePayMultiReceiveInfo queryInfo = info.getQeureyInfo();
		Collection c = this.findByConditions(queryInfo, -1, false);
		if (c != null && c.size() > 0)
		{
			if (c.size() == 1)
			{
				TransOnePayMultiReceiveInfo tmpInfo =
					(TransOnePayMultiReceiveInfo) ((ArrayList) c).get(0);
				if (tmpInfo.getId() == info.getId())
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
	public long tempSave(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		synchronized(lockObj){

		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		long depositId = partSave(info, currentDepositDao);
		//更新状态到：暂存
		try
		{
			currentDepositDao.updateStatus(
				info.getSett_TransCurrentDepositInfo().getId(),
				SETTConstant.TransactionStatus.TEMPSAVE);
		}
		//modified by mzh_fu 2007/05/06	
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
	public long tempSave(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		long depositId = partSave(info, dao);
		//更新状态到：暂存
		try
		{
			dao.updateStatus(
				info.getId(),
				SETTConstant.TransactionStatus.TEMPSAVE);
		}	
		//modified by mzh_fu 2007/05/06			
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
	public long save(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		long depositId = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		TransCurrentDepositInfo transCurrentDepositInfo =
			info.getSett_TransCurrentDepositInfo();
		try
		{
			//		    /**
			//		     * 为银企接口添加，为保证提交到银企接口地交易数据有效，在此进行交易，各个银行地差异都在
			//		     * CheckExternalAccountInfo.check()内部处理。
			//		     */
			//		    //银企接口外部账户数据检查开始
			//		    if(Env.BANK_SERVICE_ISVALID && transCurrentDepositInfo.getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY)
			//		    {
			//	            Sett_BranchDAO branchDAO = new Sett_BranchDAO();
			//	            BranchInfo bankInfo = branchDAO.findByID(transCurrentDepositInfo.getBankID());	
			//	            //交易是否有有效地开户行设置（设置了银行类型、账户号），可以执行银企接口转账操作
			//	            if(bankInfo != null && bankInfo.getBankTypeID() != -1 && bankInfo.getBankAccountCode() != null)
			//	            {
			//				    String strErrorMsg = CheckExternalAccountInfoUtil.check(
			//			    									                  transCurrentDepositInfo.getExtAccountNo(),
			//			    									                  transCurrentDepositInfo.getExtClientName(),
			//			    									                  transCurrentDepositInfo.getRemitInProvince(),
			//			    									                  transCurrentDepositInfo.getRemitInCity(),
			//			    									                  transCurrentDepositInfo.getRemitInBank());
			//				    if(strErrorMsg != null)
			//				    {
			//		                throw new IRollbackException(mySessionCtx, new IException("校验收款方信息失败，" + strErrorMsg));
			//				    }
			//	            }
			//		    }
			//			//银企接口外部账户数据检查结束
			//对客户加锁			
			sessionID =
				utilOperation.waitUtilSuccessLock(
					info.getSett_TransCurrentDepositInfo().getPayAccountID());
			
			//通存通兑业务特殊处理
			//如果是内部转账业务（目前只支持内部转账业务20110714）
			if(transCurrentDepositInfo.getTransactionTypeID()==SETTConstant.TransactionType.INTERNALVIREMENT)
			{
				Sett_AccountDAO sett_accountdao = new Sett_AccountDAO();
				AccountInfo payaccinfo = sett_accountdao.findByID(transCurrentDepositInfo.getPayAccountID());
				AccountInfo receiveaccinfo = sett_accountdao.findByID(transCurrentDepositInfo.getReceiveAccountID());
				if(payaccinfo!=null&&receiveaccinfo!=null&&payaccinfo.getOfficeID()!=receiveaccinfo.getOfficeID())
				{
					//如果是内部转账的通存通兑业务（收付款方办事处不一致）
					transCurrentDepositInfo.setIsDifOffice(Constant.TRUE);
					transCurrentDepositInfo.setPayOfficeID(payaccinfo.getOfficeID());
					transCurrentDepositInfo.setReceiveOfficeID(receiveaccinfo.getOfficeID());
					if(com.iss.itreasury.util.Env.getHQOFFICEID()<=0)
					{
						throw new IRollbackException(
								mySessionCtx,
								"本业务属于通存通兑业务，但是没有设置总部，交易失败，请检查总部设置");
					}
					transCurrentDepositInfo.setParentOfficeID(com.iss.itreasury.util.Env.getHQOFFICEID() );
					log.debug("====isdif======= "+info.getSett_TransCurrentDepositInfo().getIsDifOffice());
					log.debug("====parent======= "+info.getSett_TransCurrentDepositInfo().getParentOfficeID());
					this.DifOfficeDeal(transCurrentDepositInfo);
				}
			}
			
			//获取当前交易号
			String transNo =
				info.getSett_TransCurrentDepositInfo().getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo =
					utilOperation.getNewTransactionNo(
						info.getSett_TransCurrentDepositInfo().getOfficeID(),
						info.getSett_TransCurrentDepositInfo().getCurrencyID(),
						info.getSett_TransCurrentDepositInfo().getTransactionTypeID());
				info.getSett_TransCurrentDepositInfo().setTransNo(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			else
			{ //被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");
				//校验状态是否正确
				TransCurrentDepositInfo newInfo =
					this.findBySett_TransCurrentDepositID(
						transCurrentDepositInfo.getId());
				if (newInfo == null)
				{
					throw new IRollbackException(
						mySessionCtx,
						"无法找到交易ID对应的旧交易信息，交易失败");
				}
				String errMsg =
					UtilOperation.checkStatus(
						info.getSett_TransCurrentDepositInfo().getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.MODIFYSAVE);
				//被修改过
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				if (isTouch(info, currentDepositDao))
				{
					log.debug("------被非法修改过--------");
					throw new IRollbackException(mySessionCtx, "Sett_E130");
				}
				else
				{ //删除账簿信息
					log.debug("------开始删除账簿信息--------");
					/*
					 //2004-04-01，Forest注释，原因是与上面的重复了。 
					 TransCurrentDepositInfo oldTransInfo = currentDepositDao.findByID(transCurrentDepositInfo.getId());
					 if (oldTransInfo == null)
					 throw new IRollbackException(mySessionCtx, "无法找到交易ID对应的旧交易信息，交易失败");
					 log.debug(UtilOperation.dataentityToString(newInfo));
					 log.debug(UtilOperation.dataentityToString(transCurrentDepositInfo));
					 */
					accountBookOperation.deleteCurrentAccountDetails(newInfo);
					log.debug("------结束删除账簿信息--------");
				}
			}
			log.debug("------开始　PartSave--------");
			depositId = partSave(info, currentDepositDao);
			log.debug("------结束　PartSave--------");
			info.getSett_TransCurrentDepositInfo().setId(depositId);
			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			log.debug(UtilOperation.dataentityToString(transCurrentDepositInfo));
			accountBookOperation.saveCurrentAccountDetails(transCurrentDepositInfo);
			log.debug("------结束保存账簿信息--------");
			log.debug("------开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			currentDepositDao.updateStatus(info.getSett_TransCurrentDepositInfo().getId(),SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
			
			/**
			 * 如果Info中的InutParameterInfo不为空,则需要提交审批 add by 刘琰 2007-04-17
			 */
			if(info.getInutParameterInfo()!=null)
			{
				log.debug("------提交审批--------");
				//设置返回的地址链接(交易id只能在交易保存之后加上,tempInfo.getUrl()得到的url没有具体的交易id)
				InutParameterInfo tempInfo = info.getInutParameterInfo();
				tempInfo.setUrl(tempInfo.getUrl()+depositId);
				tempInfo.setTransID(transNo);//这里保存的是交易编号
				tempInfo.setDataEntity(info.getSett_TransCurrentDepositInfo());
				
				//提交审批
				FSWorkflowManager.initApproval(tempInfo);
				//更新状态到审批中
				currentDepositDao.updateStatus(info.getSett_TransCurrentDepositInfo().getId(),SETTConstant.TransactionStatus.APPROVALING);
				log.debug("------提交审批成功--------");
			}	
			
		}
		//modified by mzh_fu 2007/05/06			
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
					utilOperation.releaseLock(
						info
							.getSett_TransCurrentDepositInfo()
							.getPayAccountID(),
						sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(
					mySessionCtx,
					"@TBD:Error Code--",
					e);
			}
		}
		return depositId;
		}
	}
	/**
	 * 资金集中管理保存
	 * @param currentVec
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveDebitInterest(Vector currentVec)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		try
		{
			Sett_SubAccountDAO dao = new Sett_SubAccountDAO();
			if (currentVec != null && currentVec.size() > 0)
			{
				for (int i = 0; i < currentVec.size(); i++)
				{
					TransCurrentDepositAssembler info =
						(TransCurrentDepositAssembler) currentVec.elementAt(i);
					if (dao
						.findByAccountID(
							info
								.getSett_TransCurrentDepositInfo()
								.getPayAccountID())
						.getSubAccountCurrenctInfo()
						.getDailyUncheckAmount()
						> 0)
					{
						throw new IRollbackException(
							mySessionCtx,
							"账户"
								+ NameRef.getAccountNoByID(
									info
										.getSett_TransCurrentDepositInfo()
										.getPayAccountID())
								+ "存在未复核金额,不能处理！");
					}
					long temp = saveDebitInterestInfo(info);
				}
			}
		}
		//modified by mzh_fu 2007/05/06	
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
		}
	}
	/**
	 * Method save.
	 * @param info
	 * @return long
	 * @throws RemoteException
	 */
	public long saveDebitInterestInfo(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		long depositId = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		TransCurrentDepositInfo transCurrentDepositInfo =
			info.getSett_TransCurrentDepositInfo();
		try
		{
			//对客户加锁			
			sessionID =
				utilOperation.waitUtilSuccessLock(
					info.getSett_TransCurrentDepositInfo().getPayAccountID());
			//获取当前交易号
			String transNo =
				info.getSett_TransCurrentDepositInfo().getTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (transNo == null || transNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新交易号--------");
				transNo =
					utilOperation.getNewTransactionNo(
						info.getSett_TransCurrentDepositInfo().getOfficeID(),
						info.getSett_TransCurrentDepositInfo().getCurrencyID(),info.getSett_TransCurrentDepositInfo().getTransactionTypeID());
				info.getSett_TransCurrentDepositInfo().setTransNo(transNo);
				log.debug("------新交易号是:" + transNo + "--------");
			}
			log.debug("------开始　PartSave--------");
			depositId = partSave(info, currentDepositDao);
			log.debug("------结束　PartSave--------");
			info.getSett_TransCurrentDepositInfo().setId(depositId);
			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			log.debug(
				UtilOperation.dataentityToString(transCurrentDepositInfo));
			accountBookOperation.saveCurrentAccountDetails(
				transCurrentDepositInfo);
			log.debug("------结束保存账簿信息--------");
			//复核交易记录
			log.info("--------------开始AccountBook复核交易记录--------------");
			accountBookOperation.checkCurrentAccountDetails(
				transCurrentDepositInfo);
			currentDepositDao.updateStatus(
				info.getSett_TransCurrentDepositInfo().getId(),
				SETTConstant.TransactionStatus.CHECK);
			log.info("--------------结束AccountBook复核交易记录--------------");
		}
		//modified by mzh_fu 2007/05/06			
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
					utilOperation.releaseLock(
						info
							.getSett_TransCurrentDepositInfo()
							.getPayAccountID(),
						sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(
					mySessionCtx,
					"@TBD:Error Code--",
					e);
			}
		}
		return depositId;
		}
	}
	public long save(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		long depositId = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//获取当前的临时交易号
			String emptyTransNo = info.getEmptyTransNo();
			//标志位：是否是新增交易号
			boolean bNewTransNo = false;
			if (emptyTransNo == null || emptyTransNo.equalsIgnoreCase(""))
			{ //未被保存过，生成新交易号
				bNewTransNo = true;
				//通过工具操作接口类获取新交易号
				log.debug("------开始获取新的临时交易号--------");
				emptyTransNo =
					utilOperation.getNewTransactionNo(
						info.getOfficeID(),
						info.getCurrencyID(),info.getTransactionTypeID());
				info.setEmptyTransNo(emptyTransNo);
				log.debug("------新的临时交易号是:" + emptyTransNo + "--------");
			}
			else
			{ //被保存过， 即保存再保存
				//判断是否被非法修改过
				log.debug("------开始判断是否被非法修改过--------");
				//校验状态是否正确
				TransOnePayMultiReceiveInfo newInfo =
					this.findBySett_TransOnePayMultiReceiveID(info.getId());
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
				{ //删除账簿信息
					log.debug("------开始删除账簿信息--------");
					TransOnePayMultiReceiveInfo oldTransInfo =
						dao.findByID(info.getId());
					if (oldTransInfo == null)
						throw new IRollbackException(
							mySessionCtx,
							"无法找到交易对应的账户信息，交易失败");
					log.debug(UtilOperation.dataentityToString(oldTransInfo));
					log.debug(UtilOperation.dataentityToString(newInfo));
					accountBookOperation.deleteOnePayMultiReceive(oldTransInfo);
					log.debug("------结束删除账簿信息--------");
				}
			}
			log.debug("------开始　PartSave--------");
			depositId = partSave(info, dao);
			log.debug("------结束　PartSave--------");
			info.setId(depositId);
			//保存账簿信息	
			log.debug("------开始保存账簿信息--------");
			accountBookOperation.saveOnePayMultiReceive(info);
			log.debug("------结束保存账簿信息并开始更新状态到未复核--------");
			//更新状态到：2保存（未复核）
			dao.updateStatus(info.getId(), SETTConstant.TransactionStatus.SAVE);
			log.debug("------更新状态到未复核成功--------");
		}
		//modified by mzh_fu 2007/05/06			
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
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(
					mySessionCtx,
					"@TBD:Error Code--",
					e);
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
	public long delete(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			TransCurrentDepositInfo transCurrentDepositInfo =
				info.getSett_TransCurrentDepositInfo();
			//对客户加锁			
			sessionID =
				utilOperation.waitUtilSuccessLock(
					info.getSett_TransCurrentDepositInfo().getPayAccountID());
			//校验状态是否正确
			TransCurrentDepositInfo newInfo =
				this.findBySett_TransCurrentDepositID(
					transCurrentDepositInfo.getId());
			//网银提交过来的银行付款取消复核校验状态 modify by xwhe 2008-11-25
			String errMsg = "";
			if(info.getSett_TransCurrentDepositInfo().isAutoDelBankInstruction())
			{
			errMsg =
					UtilOperation.checkStatusForAutoDel(
						info.getSett_TransCurrentDepositInfo().getStatusID(),
						newInfo.getStatusID(),
						SETTConstant.Actions.DELETE);
			}
			else
			{
			errMsg =
				UtilOperation.checkStatus(
					info.getSett_TransCurrentDepositInfo().getStatusID(),
					newInfo.getStatusID(),
					SETTConstant.Actions.DELETE);
            //判断是否被修改过			
			if (isTouch(info, currentDepositDao))
			{
				throw new IRollbackException(mySessionCtx, "Sett_E131");
			}
			}
			if (errMsg != null && !errMsg.equals(""))
			{
				throw new IRollbackException(mySessionCtx, errMsg);
			}
			
			else
			{
				
				//删除交易记录
				if (transCurrentDepositInfo.getStatusID()
					== SETTConstant.TransactionStatus.SAVE)
					accountBookOperation.deleteCurrentAccountDetails(newInfo);
				//删除网银指令	
				String instructionNo = newInfo.getInstructionNo();
				if (instructionNo != null && instructionNo.length() > 0)
				{
					if (instructionNo.indexOf("T")<0) {
						log.info("---------是网银指令----------");
						FinanceInfo financeInfo = new FinanceInfo();
						OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
						financeDao.updateStatus(
							Long.valueOf(newInfo.getInstructionNo()).longValue(),
							OBConstant.SettInstrStatus.REFUSE);
					}
				}
			}
			//返回ID
			return currentDepositDao.delete(
				info.getSett_TransCurrentDepositInfo().getId());
			
		}
		//modified by mzh_fu 2007/05/06	
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
					utilOperation.releaseLock(
						info
							.getSett_TransCurrentDepositInfo()
							.getPayAccountID(),
						sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		}
	}
	public long delete(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long sessionID = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁			
			sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
			//校验状态是否正确
			TransOnePayMultiReceiveInfo newInfo =
				this.findBySett_TransOnePayMultiReceiveID(info.getId());
			String errMsg =
				UtilOperation.checkStatus(
					info.getStatusID(),
					newInfo.getStatusID(),
					SETTConstant.Actions.DELETE);
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
				log.info("TransCurrentDepositEJB删除交易记录");
				//删除交易记录
				if (info.getStatusID() == SETTConstant.TransactionStatus.SAVE)
				{
					accountBookOperation.deleteOnePayMultiReceive(newInfo);
				}
			}
			//返回ID
			log.info("TransCurrentDepositEJB:dao.delete删除交易记录");
			
			return dao.delete(info.getId());
			
		}	
		//modified by mzh_fu 2007/05/06			
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
					utilOperation.releaseLock(info.getAccountID(), sessionID);
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
	public long check(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		return check(ACTION_CHECK, info);
		}
	}
	public long check(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		return check(ACTION_CHECK, info);
		}
	}
	/**
	 * Method delete.
	 * @param info
	 * @return long 取消复核的记录ID
	 * @throws RemoteException
	 */
	public long cancelCheck(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		return check(ACTION_CANCEL_CHECK, info);
		}
	}
	public long cancelCheck(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){
		return check(ACTION_CANCEL_CHECK, info);
		}
	}
	public Collection findByConditions(
		TransCurrentDepositAssembler info,
		int orderByType,
		boolean isDesc)
		throws RemoteException, IRollbackException
	{
        //Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		try
		{
			return currentDepositDao.findByConditions(
				info.getSett_TransCurrentDepositInfo(),
				orderByType,
				isDesc);
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
	public Collection findByConditions(
		TransOnePayMultiReceiveInfo info,
		int orderByType,
		boolean isDesc)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
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
	public Collection findByConditionsForSquareUp(
		TransOnePayMultiReceiveInfo info,
		int orderByType,
		boolean isDesc)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		try
		{
			return dao.findByConditionsForSquareUp(info, orderByType, isDesc);
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
	public TransCurrentDepositInfo match(
		long transactionType,
		TransCurrentDepositInfo info)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		
		
		try
		{
			//控制匹配复核状态
			info.setStatusID(FSWorkflowManager.getSettCheckStatus(new InutParameterInfo(info.getOfficeID(),
																		info.getCurrencyID(),
																		Constant.ModuleType.SETTLEMENT,
																		info.getTransactionTypeID(),
																		-1)));
			return currentDepositDao.match(transactionType, info);
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
	public TransOnePayMultiReceiveInfo match(TransOnePayMultiReceiveInfo info)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
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
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransCurrentDepositInfo findBySett_TransCurrentDepositID(long transCurrentDepositID)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao =
			new Sett_TransCurrentDepositDAO();
		try
		{
			return currentDepositDao.findByID(transCurrentDepositID);
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
	public TransOnePayMultiReceiveInfo findBySett_TransOnePayMultiReceiveID(long lId)
		throws RemoteException, IRollbackException
	{
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		try
		{
			return dao.findByID(lId);
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
	public boolean squareUp(TransOnePayMultiReceiveInfo[] infos)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long[] sessionIDs = null;
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		//总账操作接口类
		GeneralLedgerOperation glOperation = new GeneralLedgerOperation();
		try
		{
			//对客户加锁
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] =
					utilOperation.waitUtilSuccessLock(infos[i].getAccountID());
			}
			//校验状态是否正确
			for (int i = 0; i < infos.length; i++)
			{
				TransOnePayMultiReceiveInfo newInfo =
					this.findBySett_TransOnePayMultiReceiveID(infos[i].getId());
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
				//判断是否被修改
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E047");
			}
			//交易收付平衡
			double dPayAmount = 0.0;
			double dReceiveAmount = 0.0;
			for (int i = 0; i < infos.length; i++)
			{
				if (infos[i].getTypeID() == SETTConstant.ReceiveOrPay.PAY)
				{
					dPayAmount += infos[i].getAmount();
					dPayAmount = DataFormat.formatDouble(dPayAmount);
				}
				else if (
					infos[i].getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE)
				{
					dReceiveAmount += infos[i].getAmount();
					dReceiveAmount = DataFormat.formatDouble(dReceiveAmount);
				}
			}
			log.info("一付多收收方金额：" + dReceiveAmount + "付方金额：" + dPayAmount);
			if (Math.abs(dPayAmount - dReceiveAmount) > 0.0001)
			{
				throw new IRollbackException(mySessionCtx, "收付金额不平衡，勾账失败");
			}
			//获得正式交易号
			log.debug("------开始获取正式交易号--------");
			String transNo =
				utilOperation.getNewTransactionNo(
					infos[0].getOfficeID(),
					infos[0].getCurrencyID(),infos[0].getTransactionTypeID());
			log.debug("------正式交易号是:" + transNo + "--------");
			for (int i = 0; i < infos.length; i++)
			{
				infos[i].setTransNo(transNo);
				infos[i].setSerialNo(i + 1);
				dao.update(infos[i]);
			}
			//勾账交易记录
			log.info("--------------开始AccountBook勾账交易记录--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info(
					"--------------开始勾账交易记录:"
						+ infos[i].getId()
						+ "--------------");
				accountBookOperation.squareOnePayMultiReceive(infos[i]);
				dao.updateStatus(
					infos[i].getId(),
					SETTConstant.TransactionStatus.CIRCLE);
				log.info(
					"--------------结束勾账交易记录:"
						+ infos[i].getId()
						+ "--------------");
			}
			log.info("--------------结束AccountBook勾账交易记录--------------");
			log.debug("-----检查本交易号产生的分录是否借贷平衡-----");
			boolean checkRes = glOperation.checkTransDCBalance(transNo);
			if (!checkRes)
			{
				log.debug("-----借贷平衡不平衡，分录产生失败-------");
				throw new IRollbackException(mySessionCtx, "借贷平衡不平衡，分录产生失败");
			}		
			/*
			//判断是否启用银企接口
			if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID,false)) {
				//构造参数
				CreateInstructionParam instructionParam = new CreateInstructionParam();
				instructionParam.setTransactionTypeID(infos[0].getTransactionTypeID());
				instructionParam.setTransNo(infos[0].getTransNo());
				instructionParam.setOfficeID(infos[0].getOfficeID());
				instructionParam.setCurrencyID(infos[0].getCurrencyID());
				instructionParam.setCheckUserID(infos[0].getCheckUserID());
				instructionParam.setInputUserID(infos[0].getInputUserID());
				
				//生成银行指令并保存
				IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
				bankInstruction.createBankInstructionFromTransDetail(instructionParam);
			}
			else {
				log.info("当前系统没有提供银企接口服务！");
			}
			*/
			//modify by xlchang 2010-11-25 多借多贷业务不生成指令
			if (infos[0].isCreateInstruction()) {			
			
			//生成银行一付多收指令
			Collection coll = new ArrayList();
			for (int i = 0; i < infos.length; i++)
			{
					//是否有银企接口
					boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
					//是否需要生成银行指令
					boolean bCreateInstruction = false;
					long bankID = infos[i].getBankID();
					try {
						//调用此方法后，bankID的值变为银行类型ID
						bCreateInstruction = accountBookOperation.isCreateInstruction(bankID);
					} catch (Exception e1) {				
						log.error("判断传入的银行ID是否需要生成银行指令出错！");
						e1.printStackTrace();
					}
					try
					{
						if(bIsValid&&bCreateInstruction) {
							Log.print("*******************开始产生一付多收业务指令，并保存**************************");
							try {
								//构造参数
								CreateInstructionParam instructionParam = new CreateInstructionParam();
								instructionParam.setTransactionTypeID(infos[i].getTransactionTypeID());
								instructionParam.setObjInfo(infos[i]);
								instructionParam.setOfficeID(infos[i].getOfficeID());
								instructionParam.setCurrencyID(infos[i].getCurrencyID());
								instructionParam.setCheckUserID(infos[i].getCheckUserID());
								instructionParam.setBankType(bankID);
								instructionParam.setInputUserID(infos[i].getInputUserID());
								
								coll.add(instructionParam);
								
							} catch (Throwable e) {
								log.error("生成一付多收业务失败");
								e.printStackTrace();
								throw new IRollbackException(mySessionCtx, "生成一付多收业务指令失败："+e.getMessage());
							}
						}	
						else {
							Log.print("没有银行接口或不需要生成银行指令！");
						}
					}
					catch (Exception e)
					{
						log.debug("-----保存一付多收业务失败");
						throw new IRollbackException(mySessionCtx, "保存一付多收业务出错！" + e.getMessage());
					}
			}
			//生成银行指令并保存
			IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
			bankInstruction.createSpecialBankInstruction(coll);
			}
		}
		//modified by mzh_fu 2007/05/06			
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
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//初始值已经改变，说明已经加锁，因此需要解锁
						try
						{
							utilOperation.releaseLock(
								infos[i].getAccountID(),
								sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(
								mySessionCtx,
								e.getMessage(),
								e);
						}
					}
				}
			}
		}
		return true;
		}
	}
	public boolean cancelSquareUp(TransOnePayMultiReceiveInfo[] infos)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long[] sessionIDs = null;
		Sett_TransOnePayMultiReceiveDAO dao =
			new Sett_TransOnePayMultiReceiveDAO();
		//工具操作接口类
		UtilOperation utilOperation = new UtilOperation();
		//账簿操作接口类 
		AccountBookOperation accountBookOperation = new AccountBookOperation();
		try
		{
			//对客户加锁
			sessionIDs = new long[infos.length];
			for (int i = 0; i < infos.length; i++)
			{
				sessionIDs[i] =
					utilOperation.waitUtilSuccessLock(infos[i].getAccountID());
			}
			//校验状态是否正确
			for (int i = 0; i < infos.length; i++)
			{
				TransOnePayMultiReceiveInfo newInfo =
					this.findBySett_TransOnePayMultiReceiveID(infos[i].getId());
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
				//判断是否被修改
				if (isTouch(infos[i], dao))
					throw new IRollbackException(mySessionCtx, "Sett_E051");
			}
			//删除正式交易号
			for (int i = 0; i < infos.length; i++)
			{
				dao.updateTransNo(infos[i].getId(), "");
			}
			//取消勾账交易记录
			log.info("--------------开始AccountBook取消勾账交易记录--------------");
			for (int i = 0; i < infos.length; i++)
			{
				log.info(
					"--------------开始取消勾账交易记录:"
						+ infos[i].getId()
						+ "--------------");
				accountBookOperation.cancelsquareCheckOnePayMultiReceive(
					infos[i]);
				dao.updateStatus(
					infos[i].getId(),
					SETTConstant.TransactionStatus.CHECK);
				log.info(
					"--------------结束取消勾账交易记录:"
						+ infos[i].getId()
						+ "--------------");
			}
			log.info("--------------结束AccountBook取消勾账交易记录--------------");
		}
		//modified by mzh_fu 2007/05/06			
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
			if (sessionIDs != null)
			{
				for (int i = 0; i < sessionIDs.length; i++)
				{
					if (sessionIDs[i] != -1)
					{
						//初始值已经改变，说明已经加锁，因此需要解锁
						try
						{
							utilOperation.releaseLock(
								infos[i].getAccountID(),
								sessionIDs[i]);
						}
						catch (SQLException e)
						{
							throw new IRollbackException(
								mySessionCtx,
								e.getMessage(),
								e);
						}
					}
				}
			}
		}
		return true;
		}
	}
	/**
	 * 自动保存和复核
	 * Modify by Forest
	 * 1、调用活期保存的方法
	 * 2、调用活期复核的方法
	 * 3、更新连通的贷款表
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomatically(TransCurrentDepositAssembler assemble)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		log.debug("===== start saveAndCheckAutomatically");
        
        log.debug("===== start save");
        boolean AutoCreateBankInstruction = assemble.getSett_TransCurrentDepositInfo().isAutoCreateBankInstruction();
        this.save(assemble);
        log.debug("===== end save");
        
        assemble = new TransCurrentDepositAssembler(this.findBySett_TransCurrentDepositID(assemble.getSett_TransCurrentDepositInfo().getId()));
        //update by xfma 2008-12-30 添加录入人：机核
        assemble.getSett_TransCurrentDepositInfo().setInputUserID(Constant.MachineUser.InputUser);
        
        assemble.getSett_TransCurrentDepositInfo().setCheckAbstractStr("机核");
        assemble.getSett_TransCurrentDepositInfo().setCheckUserID(Constant.MachineUser.CheckUser);
        
        assemble.getSett_TransCurrentDepositInfo().setAutoCreateBankInstruction(AutoCreateBankInstruction);
       
        log.debug("===== start check");
        long res = this.check(assemble);
        log.debug("===== end check");
        
        log.debug("===== end saveAndCheckAutomatically");
        
        return assemble;
		}

		/*		
         * //收款
		if (assemble.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY)
		{
			LoanDetailBean bean = new LoanDetailBean();
			try
			{
				bean.addLoanDetail(assemble.getSett_TransCurrentDepositInfo());
			}
			catch (SettlementException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		//付款
		else if (assemble.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE)
		{
			RepaymentLoanCorrespondingBean bean = new RepaymentLoanCorrespondingBean();
			try
			{
				bean.corresponding(assemble.getSett_TransCurrentDepositInfo());
			}
			catch (SettlementException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}*/

	}
	
	/**
	 * Added by leiyang3
	 * 
	 * 是 saveAndCheckAutomatically 的改进，CheckUserID 就是 InputUserID
	 * 
	 * @param assemble
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public TransCurrentDepositAssembler saveAndCheckNew(TransCurrentDepositAssembler assemble)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		log.debug("===== start saveAndCheckNew");
        boolean AutoCreateBankInstruction = assemble.getSett_TransCurrentDepositInfo().isAutoCreateBankInstruction();
        long nCheckUserId = assemble.getSett_TransCurrentDepositInfo().getInputUserID();
        
        log.debug("===== start save");
        this.save(assemble);
        log.debug("===== end save");
        
        
        assemble = new TransCurrentDepositAssembler(this.findBySett_TransCurrentDepositID(assemble.getSett_TransCurrentDepositInfo().getId()));
        assemble.getSett_TransCurrentDepositInfo().setAutoCreateBankInstruction(AutoCreateBankInstruction);
        assemble.getSett_TransCurrentDepositInfo().setCheckUserID(nCheckUserId);
        log.debug("===== start check");
        long res = this.check(assemble);
        log.debug("===== end check");
        
        log.debug("===== end saveAndCheckNew");
        return assemble;
		}
	}
	
	/**
	 * 自动保存和复核(网银)
	 * Modify by Forest
	 * 1、调用活期保存的方法
	 * 2、调用活期复核的方法
	 * 3、更新连通的贷款表
	 * */
	public TransCurrentDepositAssembler saveAndCheckAutomaticallyforEbank(TransCurrentDepositAssembler assemble)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		log.debug("===== start saveAndCheckAutomatically");
        try
        {
        log.debug("===== start save");
        boolean AutoCreateBankInstruction = assemble.getSett_TransCurrentDepositInfo().isAutoCreateBankInstruction();
        this.save(assemble);
        log.debug("===== end save");
        
        assemble = new TransCurrentDepositAssembler(this.findBySett_TransCurrentDepositID(assemble.getSett_TransCurrentDepositInfo().getId()));
        
//      assemble.getSett_TransCurrentDepositInfo().setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getCheckUserID());
        assemble.getSett_TransCurrentDepositInfo().setCheckUserID(Constant.MachineUser.CheckUser);
        assemble.getSett_TransCurrentDepositInfo().setCheckAbstractStr("结算接收网银自动复核");
        assemble.getSett_TransCurrentDepositInfo().setAutoCreateBankInstruction(AutoCreateBankInstruction);
       
        log.debug("===== start check");
        long res = this.check(assemble);
        log.debug("===== end check");
        
        log.debug("===== end saveAndCheckAutomatically");
        }
        catch (Exception e1)
		{
			throw new IRollbackException(mySessionCtx, e1.getMessage(), e1);
		}
        return assemble;
		}
	}
	
	/**
	 *取消复核与删除交易
	 * */
	public void cancelSaveAndCheckAutomatically(TransCurrentDepositAssembler info)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		Sett_TransCurrentDepositDAO transCurrentDepositDAO = new Sett_TransCurrentDepositDAO();

		TransCurrentDepositInfo transCurrentDepositInfo = null;
		try
		{
			transCurrentDepositInfo = transCurrentDepositDAO.findByTransNo(
					info.getSett_TransCurrentDepositInfo().getTransNo());
			info = new TransCurrentDepositAssembler(transCurrentDepositInfo);
		}
		catch (Exception e1)
		{
			throw new IRollbackException(mySessionCtx, e1.getMessage(), e1);
		}

		this.cancelCheck(info);
		
		try
		{
			transCurrentDepositInfo = transCurrentDepositDAO.findByTransNo(
					info.getSett_TransCurrentDepositInfo().getTransNo());
			info = new TransCurrentDepositAssembler(transCurrentDepositInfo);
		}
		catch (Exception e1)
		{
			throw new IRollbackException(mySessionCtx, e1.getMessage(), e1);
		}
		
		this.delete(info);
		
		//收款
		if ( info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY ||
			 info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER||
             info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY ||
             info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT
           )
		{
			LoanDetailBean bean = new LoanDetailBean();
			try
			{
				bean.deleteLoanDetail(info.getSett_TransCurrentDepositInfo());
			}
			catch (SettlementException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		//付款
		else if (
			info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE ||
            info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE_GATHERING ||
            info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE_SUBCLIENT ||
            info.getSett_TransCurrentDepositInfo().getTransactionTypeID() == SETTConstant.TransactionType.BANKRECEIVE_TOSUBCLIENT
            )
		{
			RepaymentLoanCorrespondingBean bean =
				new RepaymentLoanCorrespondingBean();
			try
			{
				bean.cancelCorresponding(
					info.getSett_TransCurrentDepositInfo());
			}
			catch (SettlementException e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		}
	}
	/**
	 * 自动保存复核,用于接受网银指令
	 * @return 返回交易号
	 * @param info
	 * @param userID
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public String saveAndCheckAutomatically(FinanceInfo info, long userID)
		throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		TransInfo transInfo = new TransInfo();
		long lID = -1;
		String sTransNo = ""; //要返回的交易编号

		if (info.getDefaultTransType()
			== SETTConstant.TransactionType.OPENFIXEDDEPOSIT) //定期开立
		{
			TransFixedDeposit transFixedDepositFacade = null;
			try
			{
				TransFixedDepositHome home =
					(TransFixedDepositHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						TransFixedDepositHome.class);
				transFixedDepositFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(
					"CreateException in TransFixedDepositDelegation",
					e);
			}

			TransFixedOpenInfo fixedOpenInfo = new TransFixedOpenInfo();
			try
			{
				fixedOpenInfo = transInfo.transFixedOpen(info, userID);
				fixedOpenInfo.setTransactionTypeID(
					SETTConstant.TransactionType.OPENFIXEDDEPOSIT);

				log.print("=====start save=======");
				lID = transFixedDepositFacade.openSave(fixedOpenInfo);

				Sett_TransOpenFixedDepositDAO dao =
					new Sett_TransOpenFixedDepositDAO();
				fixedOpenInfo = dao.findByID(lID);
				sTransNo = fixedOpenInfo.getTransNo();

				log.print("=====end save=======");
				log.print("=====start check=======");
				transFixedDepositFacade.openCheck(fixedOpenInfo);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.OPENNOTIFYACCOUNT)
			//通知开立
		{
			TransFixedDeposit transFixedDepositFacade = null;
			try
			{
				TransFixedDepositHome home =
					(TransFixedDepositHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						TransFixedDepositHome.class);
				transFixedDepositFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(
					"CreateException in TransFixedDepositDelegation",
					e);
			}

			TransFixedOpenInfo fixedOpenInfo = new TransFixedOpenInfo();

			try
			{
				fixedOpenInfo = transInfo.transFixedOpen(info, userID);
				fixedOpenInfo.setTransactionTypeID(
					SETTConstant.TransactionType.OPENNOTIFYACCOUNT);
				log.print("=====start save=======");
				lID = transFixedDepositFacade.openSave(fixedOpenInfo);

				Sett_TransOpenFixedDepositDAO dao =
					new Sett_TransOpenFixedDepositDAO();
				fixedOpenInfo = dao.findByID(lID);
				sTransNo = fixedOpenInfo.getTransNo();

				log.print("=====end save=======");
				log.print("=====start check=======");
				transFixedDepositFacade.openCheck(fixedOpenInfo);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}

		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER) //定期支取
		{
			TransFixedDeposit transFixedDepositFacade = null;
			try
			{
				TransFixedDepositHome home =
					(TransFixedDepositHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						TransFixedDepositHome.class);
				transFixedDepositFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(
					"CreateException in TransFixedDepositDelegation",
					e);
			}
			TransFixedDrawInfo fixedDrawInfo = new TransFixedDrawInfo();
			try
			{
				fixedDrawInfo = transInfo.transFixedDraw(info, userID);
				fixedDrawInfo.setTransactionTypeID(
					SETTConstant.TransactionType.FIXEDTOCURRENTTRANSFER);
				log.print("=====start save=======");
				lID = transFixedDepositFacade.drawSave(fixedDrawInfo);

				Sett_TransFixedWithDrawDAO dao =
					new Sett_TransFixedWithDrawDAO();
				fixedDrawInfo = dao.findByID(lID);
				sTransNo = fixedDrawInfo.getTransNo();

				log.print("=====end save=======");
				log.print("=====start check=======");
				transFixedDepositFacade.drawCheck(fixedDrawInfo);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.NOTIFYDEPOSITDRAW)
			//通知支取
		{
			TransFixedDeposit transFixedDepositFacade = null;
			try
			{
				TransFixedDepositHome home =
					(TransFixedDepositHome) EJBHomeFactory
						.getFactory()
						.lookUpHome(
						TransFixedDepositHome.class);
				transFixedDepositFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(
					"CreateException in TransFixedDepositDelegation",
					e);
			}
			TransFixedDrawInfo fixedDrawInfo = new TransFixedDrawInfo();
			try
			{
				fixedDrawInfo = transInfo.transFixedDraw(info, userID);
				fixedDrawInfo.setTransactionTypeID(
					SETTConstant.TransactionType.NOTIFYDEPOSITDRAW);

				log.print("=====start save=======");
				lID = transFixedDepositFacade.drawSave(fixedDrawInfo);
				log.print("=====end save=======");

				Sett_TransFixedWithDrawDAO dao =
					new Sett_TransFixedWithDrawDAO();
				fixedDrawInfo = dao.findByID(lID);
				sTransNo = fixedDrawInfo.getTransNo();

				log.print("=====start check=======");
				transFixedDepositFacade.drawCheck(fixedDrawInfo);
				log.print("=====end check=======");

			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.TRUSTLOANRECEIVE)
			//自营收回
		{
			TransLoan transLoanFacade = null;
			try
			{
				TransLoanHome home =
					(TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(
						TransLoanHome.class);
				transLoanFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage());
			}
			TransRepaymentLoanInfo transRepaymentLoanInfo =
				new TransRepaymentLoanInfo();
			try
			{
				transRepaymentLoanInfo = transInfo.transTrustLoan(info, userID);
				transRepaymentLoanInfo.setTransactionTypeID(
					SETTConstant.TransactionType.TRUSTLOANRECEIVE);
				log.print("=====start save=======");
				lID = transLoanFacade.save(transRepaymentLoanInfo);
				log.print("=====end save=======");

				Sett_TransRepaymentLoanDAO dao =
					new Sett_TransRepaymentLoanDAO();
				transRepaymentLoanInfo = dao.findByID(lID);
				sTransNo = transRepaymentLoanInfo.getTransNo();

				log.print("=====start check=======");
				transLoanFacade.check(transRepaymentLoanInfo, true);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.BANKGROUPLOANRECEIVE) //银团收回
		{
			TransLoan transLoanFacade = null;
			try
			{
				TransLoanHome home =
					(TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(
						TransLoanHome.class);
				transLoanFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage());
			}
			TransRepaymentLoanInfo transRepaymentLoanInfo =
				new TransRepaymentLoanInfo();
			try
			{
				transRepaymentLoanInfo =
					transInfo.transBankGroupLoan(info, userID);
				transRepaymentLoanInfo.setTransactionTypeID(
					SETTConstant.TransactionType.BANKGROUPLOANRECEIVE);
				log.print("=====start save=======");
				lID = transLoanFacade.save(transRepaymentLoanInfo);
				log.print("=====end save=======");

				Sett_TransRepaymentLoanDAO dao =
					new Sett_TransRepaymentLoanDAO();
				transRepaymentLoanInfo = dao.findByID(lID);
				sTransNo = transRepaymentLoanInfo.getTransNo();

				log.print("=====start check=======");
				transLoanFacade.check(transRepaymentLoanInfo, true);
				log.print("=====end check=======");

			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
			//委贷收回
		{
			TransLoan transLoanFacade = null;
			try
			{
				TransLoanHome home =
					(TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(
						TransLoanHome.class);
				transLoanFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage());
			}
			TransRepaymentLoanInfo transRepaymentLoanInfo =
				new TransRepaymentLoanInfo();
			try
			{
				transRepaymentLoanInfo =
					transInfo.transBankGroupLoan(info, userID);
				transRepaymentLoanInfo.setTransactionTypeID(
					SETTConstant.TransactionType.CONSIGNLOANRECEIVE);
				log.print("=====start save=======");
				lID = transLoanFacade.save(transRepaymentLoanInfo);
				log.print("=====end save=======");

				Sett_TransRepaymentLoanDAO dao =
					new Sett_TransRepaymentLoanDAO();
				transRepaymentLoanInfo = dao.findByID(lID);
				sTransNo = transRepaymentLoanInfo.getTransNo();

				log.print("=====start check=======");
				transLoanFacade.check(transRepaymentLoanInfo, true);
				log.print("=====end check=======");

			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.INTERESTFEEPAYMENT)
			//利息费用支付
		{
			TransLoan transLoanFacade = null;
			try
			{
				TransLoanHome home =
					(TransLoanHome) EJBHomeFactory.getFactory().lookUpHome(
						TransLoanHome.class);
				transLoanFacade = home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage());
			}
			TransRepaymentLoanInfo transRepaymentLoanInfo =
				new TransRepaymentLoanInfo();
			try
			{
				transRepaymentLoanInfo =
					transInfo.transBankGroupLoan(info, userID);
				transRepaymentLoanInfo.setTransactionTypeID(
					SETTConstant.TransactionType.INTERESTFEEPAYMENT);
				log.print("=====start save=======");
				lID = transLoanFacade.save(transRepaymentLoanInfo);
				log.print("=====end save=======");

				Sett_TransRepaymentLoanDAO dao =
					new Sett_TransRepaymentLoanDAO();
				transRepaymentLoanInfo = dao.findByID(lID);
				sTransNo = transRepaymentLoanInfo.getTransNo();

				log.print("=====start check=======");
				transLoanFacade.check(transRepaymentLoanInfo, true);
				log.print("=====end check=======");

			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.SPECIALOPERATION)
			//特殊交易
		{
			TransSpecial transspecialejb = null;
			try
			{
				TransSpecialHome home =
					(TransSpecialHome) EJBHomeFactory.getFactory().lookUpHome(
						TransSpecialHome.class);
				transspecialejb = (TransSpecial) home.create();
			}
			catch (Exception e)
			{
				throw new RemoteException(e.getMessage());
			}
			TransSpecialOperationInfo specialInfo =
				new TransSpecialOperationInfo();
			try
			{
				specialInfo = transInfo.transSpecial(info, userID);
				specialInfo.setNtransactiontypeid(
					SETTConstant.TransactionType.SPECIALOPERATION);
				log.print("=====start save=======");
				lID = transspecialejb.save(specialInfo);
				log.print("=====end save=======");

				Sett_TransSpecialOperationDAO dao =
					new Sett_TransSpecialOperationDAO();
				specialInfo = dao.findByID(lID);
				sTransNo = specialInfo.getStransno();

				log.print("=====start check=======");
				transspecialejb.check(specialInfo);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.INTERNALVIREMENT)
			//内部转账
		{
			TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
			try
			{
				currInfo = transInfo.transCurrent(info, userID);
				currInfo.setTransactionTypeID(
					SETTConstant.TransactionType.INTERNALVIREMENT);
				TransCurrentDepositAssembler data =
					new TransCurrentDepositAssembler(currInfo);
				log.print("=====start save=======");
				lID = this.save(data);
				log.print("=====end save=======");

				Sett_TransCurrentDepositDAO dao =
					new Sett_TransCurrentDepositDAO();
				data = new TransCurrentDepositAssembler(dao.findByID(lID));
				sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

				log.print("=====start check=======");
				this.check(data);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if ( info.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY)
			//银行付款
		{
			TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
			try
			{
				currInfo = transInfo.transCurrent(info, userID);

				/*
				 * 因为从网银接受过来的没有选择开户行,这里取活期户对应的二级户所属的开户行
				 * 如果找不到二级户,此指令无法接收
				 */
				if (currInfo.getBankID() == -1)
				{
					Sett_FilialeAccountSetDAO filialeAccountSetDao =
						new Sett_FilialeAccountSetDAO();
					FilialeAccountInfo[] filialAccountInfo = null;
					try
					{ //根据活期户ID得到银行账户信息
						filialAccountInfo =
							filialeAccountSetDao
								.findRefFilialeAccountInfoBySettAccountId(
								currInfo.getPayAccountID());
					}
					catch (Exception e1)
					{
						log.print("ERROR:根据活期账户查询二级户时出现异常!");
						throw new SettlementException("Sett_E302", e1);
					}
					if (filialAccountInfo == null
						|| filialAccountInfo.length == 0)
					{
						log.print("ERROR:找不到此账户对应的二级账户!");
						throw new SettlementException("Sett_E302", null);
					}
					else
					{
						if (filialAccountInfo.length > 1)
						{
							log.print("ERROR:此账户找到多条二级账户");
							throw new SettlementException("Sett_E304", null);
						}
						else
						{
							currInfo.setBankID(
								filialAccountInfo[0].getUpBankAccountID());
						}
					}
				}
				currInfo.setTransactionTypeID( SETTConstant.TransactionType.BANKPAY);
				TransCurrentDepositAssembler data =
					new TransCurrentDepositAssembler(currInfo);
				log.print("=====start save=======");
				lID = this.save(data);
				log.print("=====end save=======");

				Sett_TransCurrentDepositDAO dao =
					new Sett_TransCurrentDepositDAO();
				data = new TransCurrentDepositAssembler(dao.findByID(lID));
				sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

				log.print("=====start check=======");
				this.check(data);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		//中交，资金划拨，下划
		else if ( info.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER)
			//银行付款
		{
			TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
			try
			{
				currInfo = transInfo.transCurrent(info, userID);

				/*
				 * 因为从网银接受过来的没有选择开户行,这里取活期户对应的二级户所属的开户行
				 * 如果找不到二级户,此指令无法接收
				 */
				if (currInfo.getBankID() == -1)
				{
					Sett_FilialeAccountSetDAO filialeAccountSetDao =
						new Sett_FilialeAccountSetDAO();
					FilialeAccountInfo[] filialAccountInfo = null;
					try
					{ //根据活期户ID得到银行账户信息
						filialAccountInfo =
							filialeAccountSetDao
								.findRefFilialeAccountInfoBySettAccountId(
								currInfo.getPayAccountID());
					}
					catch (Exception e1)
					{
						log.print("ERROR:根据活期账户查询二级户时出现异常!");
						throw new SettlementException("Sett_E302", e1);
					}
					if (filialAccountInfo == null
						|| filialAccountInfo.length == 0)
					{
						log.print("ERROR:找不到此账户对应的二级账户!");
						throw new SettlementException("Sett_E302", null);
					}
					else
					{
						if (filialAccountInfo.length > 1)
						{
							log.print("ERROR:此账户找到多条二级账户");
							throw new SettlementException("Sett_E304", null);
						}
						else
						{
							currInfo.setBankID(
								filialAccountInfo[0].getUpBankAccountID());
						}
			 		}
				}
				currInfo.setTransactionTypeID( SETTConstant.TransactionType.BANKPAY_DOWNTRANSFER);
				TransCurrentDepositAssembler data =
					new TransCurrentDepositAssembler(currInfo);
				log.print("=====start save=======");
				lID = this.save(data);
				log.print("=====end save=======");

				Sett_TransCurrentDepositDAO dao =
					new Sett_TransCurrentDepositDAO();
				data = new TransCurrentDepositAssembler(dao.findByID(lID));
				sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

				log.print("=====start check=======");
				this.check(data);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
        else if (
                info.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY)
                //银行付款-财司代收
            {
                TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
                try
                {
                    currInfo = transInfo.transCurrent(info, userID);

                    /*
                     * 因为从网银接受过来的没有选择开户行,这里取活期户对应的二级户所属的开户行
                     * 如果找不到二级户,此指令无法接收
                     */
                    if (currInfo.getBankID() == -1)
                    {
                        Sett_FilialeAccountSetDAO filialeAccountSetDao =
                            new Sett_FilialeAccountSetDAO();
                        FilialeAccountInfo[] filialAccountInfo = null;
                        try
                        { //根据活期户ID得到银行账户信息
                            filialAccountInfo =
                                filialeAccountSetDao
                                    .findRefFilialeAccountInfoBySettAccountId(
                                    currInfo.getPayAccountID());
                        }
                        catch (Exception e1)
                        {
                            log.print("ERROR:根据活期账户查询二级户时出现异常!");
                            throw new SettlementException("Sett_E302", e1);
                        }
                        if (filialAccountInfo == null
                            || filialAccountInfo.length == 0)
                        {
                            log.print("ERROR:找不到此账户对应的二级账户!");
                            throw new SettlementException("Sett_E302", null);
                        }
                        else
                        {
                            if (filialAccountInfo.length > 1)
                            {
                                log.print("ERROR:此账户找到多条二级账户");
                                throw new SettlementException("Sett_E304", null);
                            }
                            else
                            {
                                currInfo.setBankID(
                                    filialAccountInfo[0].getUpBankAccountID());
                            }
                        }
                    }
                    currInfo.setTransactionTypeID(
                        SETTConstant.TransactionType.BANKPAY_FINCOMPANYPAY);
                    TransCurrentDepositAssembler data =
                        new TransCurrentDepositAssembler(currInfo);
                    log.print("=====start save=======");
                    lID = this.save(data);
                    log.print("=====end save=======");

                    Sett_TransCurrentDepositDAO dao =
                        new Sett_TransCurrentDepositDAO();
                    data = new TransCurrentDepositAssembler(dao.findByID(lID));
                    sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

                    log.print("=====start check=======");
                    this.check(data);
                    log.print("=====end check=======");
                }
                catch (Exception e)
                {
                    throw new IRollbackException(mySessionCtx, e.getMessage(), e);
                }
            }
        else if (
                info.getDefaultTransType() == SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT)
                //银行付款-拨子账户
            {
                TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
                try
                {
                    currInfo = transInfo.transCurrent(info, userID);

                    /*
                     * 因为从网银接受过来的没有选择开户行,这里取活期户对应的二级户所属的开户行
                     * 如果找不到二级户,此指令无法接收
                     */
                    if (currInfo.getBankID() == -1)
                    {
                        Sett_FilialeAccountSetDAO filialeAccountSetDao =
                            new Sett_FilialeAccountSetDAO();
                        FilialeAccountInfo[] filialAccountInfo = null;
                        try
                        { //根据活期户ID得到银行账户信息
                            filialAccountInfo =
                                filialeAccountSetDao
                                    .findRefFilialeAccountInfoBySettAccountId(
                                    currInfo.getPayAccountID());
                        }
                        catch (Exception e1)
                        {
                            log.print("ERROR:根据活期账户查询二级户时出现异常!");
                            throw new SettlementException("Sett_E302", e1);
                        }
                        if (filialAccountInfo == null
                            || filialAccountInfo.length == 0)
                        {
                            log.print("ERROR:找不到此账户对应的二级账户!");
                            throw new SettlementException("Sett_E302", null);
                        }
                        else
                        {
                            if (filialAccountInfo.length > 1)
                            {
                                log.print("ERROR:此账户找到多条二级账户");
                                throw new SettlementException("Sett_E304", null);
                            }
                            else
                            {
                                currInfo.setBankID(
                                    filialAccountInfo[0].getUpBankAccountID());
                            }
                        }
                    }
                    currInfo.setTransactionTypeID(
                        SETTConstant.TransactionType.BANKPAY_PAYSUBACCOUNT);
                    TransCurrentDepositAssembler data =
                        new TransCurrentDepositAssembler(currInfo);
                    log.print("=====start save=======");
                    lID = this.save(data);
                    log.print("=====end save=======");

                    Sett_TransCurrentDepositDAO dao =
                        new Sett_TransCurrentDepositDAO();
                    data = new TransCurrentDepositAssembler(dao.findByID(lID));
                    sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

                    log.print("=====start check=======");
                    this.check(data);
                    log.print("=====end check=======");
                }
                catch (Exception e)
                {
                    throw new IRollbackException(mySessionCtx, e.getMessage(), e);
                }
            }
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.SUBCLIENT_BANKPAY)
			//下属单位银行付款
		{
			TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
			try
			{
				currInfo = transInfo.transCurrent(info, userID);
				currInfo.setTransactionTypeID(
					SETTConstant.TransactionType.SUBCLIENT_BANKPAY);
				TransCurrentDepositAssembler data =
					new TransCurrentDepositAssembler(currInfo);
				log.print("=====start save=======");
				lID = this.save(data);
				log.print("=====end save=======");

				Sett_TransCurrentDepositDAO dao =
					new Sett_TransCurrentDepositDAO();
				data = new TransCurrentDepositAssembler(dao.findByID(lID));
				sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

				log.print("=====start check=======");
				this.check(data);
				log.print("=====end check=======");
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
		}
		else if (
			info.getDefaultTransType()
				== SETTConstant.TransactionType.FUND_REQUEST)
			//资金申领
		{
			sTransNo = this.dealFinanceInfo_For_FUND_REQUEST(info, userID);
		}
		else
		{
			log.print("---------错误交易类型--------");
		}
		return sTransNo;
		}
	}
	
	/**
	 * 处理网银提交的资金申领业务指令，完成保存和复合操作。
	 * @param info
	 * @param userID
	 * @return String
	 * @throws IRollbackException
	 */
	private String dealFinanceInfo_For_FUND_REQUEST(
		FinanceInfo info,
		long userID)
		throws IRollbackException
	{
		synchronized(lockObj){

		TransInfo transInfo = new TransInfo();
		String sTransNo = null;
		long lID = -1;
		TransCurrentDepositInfo currInfo = new TransCurrentDepositInfo();
		try
		{
			currInfo = transInfo.transCurrent(info, userID);

			//对提交的账户相关的设置项进行校验
			Sett_FilialeAccountSetDAO filialeAccountSetDao =
				new Sett_FilialeAccountSetDAO();
			FilialeAccountInfo[] filialAccountInfo = null;
			try
			{ //根据活期户ID得到银行账户信息
				filialAccountInfo =
					filialeAccountSetDao
						.findRefFilialeAccountInfoBySettAccountId(
						currInfo.getPayAccountID());
			}
			catch (Exception e1)
			{
				log.print("ERROR:根据活期账户查询银行账户时出现异常!");
				throw new SettlementException("Sett_E302", e1);
			}
			
			if (filialAccountInfo == null || filialAccountInfo.length == 0)
			{
				log.print("ERROR:找不到此账户对应的银行账户!");
				throw new SettlementException("Sett_E302", null);
			}
			else
			{
				if (filialAccountInfo.length > 1)
				{
					log.print("ERROR:此账户找到多条银行账户");
					throw new SettlementException("Sett_E304", null);
				}
				else
				{
					if(filialAccountInfo[0].getUpBankAccountID() <= 0)
					{
						log.print("ERROR:此账户找到的银行账户设置中未指定关联开户行");
						throw new SettlementException("Sett_E311", null);
					}
					else
					{
						currInfo.setBankID(filialAccountInfo[0].getUpBankAccountID());
					}
				}
			}

			TransCurrentDepositAssembler data =
				new TransCurrentDepositAssembler(currInfo);
			log.print("=====start save=======");
			lID = this.save(data);
			log.print("=====end save=======");

			Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
			data = new TransCurrentDepositAssembler(dao.findByID(lID));
			sTransNo = data.getSett_TransCurrentDepositInfo().getTransNo();

			log.print("=====start check=======");
			this.check(data);
			log.print("=====end check=======");
			
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return sTransNo;
		}
	}
	
	/**
	 * 提交审批方法。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long initApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long depositId = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao = new Sett_TransCurrentDepositDAO();
		TransCurrentDepositInfo transCurrentDepositInfo = info.getSett_TransCurrentDepositInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId = transCurrentDepositInfo.getId();
		try
		{
			//提交审批
			FSWorkflowManager.initApproval(inutParameterInfo);
			//更新状态
			currentDepositDao.updateStatus(
					transCurrentDepositInfo.getId(),
					SETTConstant.TransactionStatus.APPROVALING);
		}
		//modified by mzh_fu 2007/05/06			
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
	 * 审批方法。
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long doApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long depositId = -1;
		InutParameterInfo returnInfo = new InutParameterInfo();
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao = new Sett_TransCurrentDepositDAO();
		TransCurrentDepositInfo transCurrentDepositInfo = info.getSett_TransCurrentDepositInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();
		depositId = transCurrentDepositInfo.getId();
		
		
		try
		{
			//将业务记录置入pinfo,转换成标准map传递到审批流引擎
			transCurrentDepositInfo = this.findBySett_TransCurrentDepositID(depositId);	
			inutParameterInfo.setDataEntity(transCurrentDepositInfo);
			
			//提交审批
			returnInfo = FSWorkflowManager.doApproval(inutParameterInfo);
			
			//如果是最后一级,且为审批通过,更新状态为已审批
			if(returnInfo.isLastLevel())
			{	
				currentDepositDao.updateStatus(
					transCurrentDepositInfo.getId(),
					SETTConstant.TransactionStatus.APPROVALED);
				//如果是自动复核
				if(FSWorkflowManager.isAutoCheck())
				{
					//构造check参数
					TransCurrentDepositInfo depositInfo = new TransCurrentDepositInfo();
					depositInfo = this.findBySett_TransCurrentDepositID(info.getSett_TransCurrentDepositInfo().getId());
					//depositInfo.setCheckUserID(assemble.getSett_TransCurrentDepositInfo().getInputUserID());			        
					//depositInfo.setCheckAbstractStr("机核");
					depositInfo.setCheckUserID(returnInfo.getUserID());					
					
					TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfo);
					
					//调用check方法
					this.check(dataEntity);
				}	
			}
			//如果是最后一级,且为审批拒绝,更新状态为已保存
			else if(returnInfo.isRefuse())
			{
				currentDepositDao.updateStatus(
						transCurrentDepositInfo.getId(),
						SETTConstant.TransactionStatus.SAVE);
			}	
		}
		//modified by mzh_fu 2007/05/06			
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
	 * 取消审批方法。如果是自动复核，则取消审批之前必须先取消复核，如果是手动复核，则只需取消审批
	 * 取消复核：调用ejb的取消复核方法
	 * 取消审批：将业务记录状态置为已保存即可
	 * @param info
	 * @return long
	 * @throws IRollbackException
	 */
	public long cancelApproval(TransCurrentDepositAssembler info)throws RemoteException, IRollbackException
	{
		synchronized(lockObj){

		long lReturn = -1;
		//Sett_TransCurrentDeposit数据访问对象
		Sett_TransCurrentDepositDAO currentDepositDao = new Sett_TransCurrentDepositDAO();
		TransCurrentDepositInfo transCurrentDepositInfo = info.getSett_TransCurrentDepositInfo();
		InutParameterInfo inutParameterInfo = info.getInutParameterInfo();	
		
		try
		{
			//如果系统内设定为自动动复核，则需要先取消复核，然后取消审批
			if(FSWorkflowManager.isAutoCheck() && transCurrentDepositInfo.getStatusID()==SETTConstant.TransactionStatus.CHECK)
			{
				//取消复核
				this.check(ACTION_CANCEL_CHECK, info);
				//取消审批
				lReturn = currentDepositDao.updateStatus(transCurrentDepositInfo.getId(), SETTConstant.TransactionStatus.SAVE);
			}
			else if( !FSWorkflowManager.isAutoCheck() && transCurrentDepositInfo.getStatusID()==SETTConstant.TransactionStatus.APPROVALED)
			{
				//取消审批
				lReturn = currentDepositDao.updateStatus(transCurrentDepositInfo.getId(), SETTConstant.TransactionStatus.SAVE);
			}
			
			//将审批记录表内的该交易的审批记录状态置为无效
			if(inutParameterInfo.getApprovalEntryID()>0)
			{
				FSWorkflowManager.cancelApprovalRecord(inutParameterInfo);
			}							
		}
		catch (Exception e)
		{
			throw new IRollbackException(mySessionCtx, e.getMessage(), e);
		}
		return lReturn;
		}
	}
	
	/**
	 * 从excel 导入到多借多贷
	 * @param transOnePayMultiReceiveInfos
	 * @return
	 * @throws Exception 
	 */
	public long saveAndCheck(
			TransOnePayMultiReceiveInfo[] transOnePayMultiReceiveInfos,long batchEntity)throws RemoteException, IRollbackException{
		long lRtn = -1;;
		for(int i=0;i<transOnePayMultiReceiveInfos.length;i++){
			TransOnePayMultiReceiveInfo info = transOnePayMultiReceiveInfos[i];
			long lId = this.save(info);
			System.out.println(lId);
			info = this.findBySett_TransOnePayMultiReceiveID(lId);
			checkForImport(info);
		}
		TrustCollectionDao dao = new TrustCollectionDao();
		try{
		lRtn = dao.deleteTrustCollection(batchEntity);
		}catch (Exception e){
			throw new IRollbackException(mySessionCtx,e.getMessage());
		}
		return lRtn;
	}
	private long checkForImport(
			TransOnePayMultiReceiveInfo info)
			throws RemoteException, IRollbackException
		{
			synchronized(lockObj){

			long sessionID = -1;
			Sett_TransOnePayMultiReceiveDAO dao =
				new Sett_TransOnePayMultiReceiveDAO();
			//工具操作接口类
			UtilOperation utilOperation = new UtilOperation();
			//账簿操作接口类 
			AccountBookOperation accountBookOperation;
			accountBookOperation = new AccountBookOperation();
			try
			{
				//对客户加锁
				sessionID = utilOperation.waitUtilSuccessLock(info.getAccountID());
				//校验状态是否正确
				TransOnePayMultiReceiveInfo newInfo =
					this.findBySett_TransOnePayMultiReceiveID(info.getId());
				String errMsg = "";
				
					errMsg =
						UtilOperation.checkStatus(
							info.getStatusID(),
							newInfo.getStatusID(),
							SETTConstant.Actions.CHECK);
				if (errMsg != null && !errMsg.equals(""))
				{
					throw new IRollbackException(mySessionCtx, errMsg);
				}
				dao.update(info);
				
					System.out.println("曲来安测试列名无效test=====================");
					//复核交易记录
					log.info("--------------开始AccountBook复核交易记录--------------");
					accountBookOperation.checkOnePayMultiReceive(info);
					dao.updateStatus(
						info.getId(),
						SETTConstant.TransactionStatus.CHECK);
					log.info("--------------结束AccountBook复核交易记录--------------");
				
				if (sessionID != -1) //初始值已经改变，说明已经加锁，因此需要解锁
					utilOperation.releaseLock(info.getAccountID(), sessionID);
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
			return info.getId();
			}
		}
		/**
		 * 判断是否通存通兑交易，如果是通存通兑，则需要进行一些校验
		 * @param transCurrentDepositInfo
		 */
		private void DifOfficeDeal(TransCurrentDepositInfo transCurrentDepositInfo) throws IRollbackException
		{
			try
			{
				//如果是通存通兑业务，增加校验
				if(transCurrentDepositInfo.getIsDifOffice()==Constant.TRUE)
				{
					if(transCurrentDepositInfo.getPayOfficeID()==transCurrentDepositInfo.getParentOfficeID()
						||transCurrentDepositInfo.getReceiveOfficeID()==transCurrentDepositInfo.getParentOfficeID())
					{//如果总部是参与方，只判断付方和收方
						
						if(Env.getSystemStatusID(transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.SystemStatus.CLOSE)
						{
							throw new IRollbackException(
									mySessionCtx,
									"付款方办事处已关机，交易失败");
						}
						if (Env.getDealStatusID ( transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID() ) == Constant.ShutDownStatus.DOING)
						{
							throw new IRollbackException ( 
									mySessionCtx,
									"付款方办事处正在关机，交易失败。") ;
						}
						if(Env.getSystemStatusID(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.SystemStatus.CLOSE)
						{
							throw new IRollbackException(
									mySessionCtx,
									"收款方办事处已关机，交易失败");
						}
						if(Env.getDealStatusID(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.ShutDownStatus.DOING)
						{
							throw new IRollbackException(
									mySessionCtx,
									"收款方办事处正在关机，交易失败");
						}
						if(!Env.getSystemDate(transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID())
								.equals(Env.getSystemDate(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())))
						{
							throw new IRollbackException(
									mySessionCtx,
									"业务双方开机日不一致，交易失败");
						}
					}
					else
					{//如果总部是协调方，则还需要判断总部信息
						if(Env.getSystemStatusID(transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.SystemStatus.CLOSE)
						{
							throw new IRollbackException(
									mySessionCtx,
									"付款方办事处已关机，交易失败");
						}
						if(Env.getDealStatusID(transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.ShutDownStatus.DOING)
						{
							throw new IRollbackException(
									mySessionCtx,
									"付款方办事处正在关机，交易失败");
						}
						if(Env.getSystemStatusID(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.SystemStatus.CLOSE)
						{
							throw new IRollbackException(
									mySessionCtx,
									"收款方办事处已关机，交易失败");
						}
						if(Env.getDealStatusID(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.ShutDownStatus.DOING)
						{
							throw new IRollbackException(
									mySessionCtx,
									"收款方办事处正在关机，交易失败");
						}
						if(Env.getSystemStatusID(transCurrentDepositInfo.getParentOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.SystemStatus.CLOSE)
						{
							throw new IRollbackException(
									mySessionCtx,
									"总部已关机，交易失败");
						}
						if(Env.getDealStatusID(transCurrentDepositInfo.getParentOfficeID(), transCurrentDepositInfo.getCurrencyID())==Constant.ShutDownStatus.DOING)
						{
							throw new IRollbackException(
									mySessionCtx,
									"总部正在关机，交易失败");
						}
						
						if(!Env.getSystemDate(transCurrentDepositInfo.getPayOfficeID(), transCurrentDepositInfo.getCurrencyID())
								.equals(Env.getSystemDate(transCurrentDepositInfo.getParentOfficeID(), transCurrentDepositInfo.getCurrencyID())))
						{
							throw new IRollbackException(
									mySessionCtx,
									"付方与总部开机日不一致，交易失败");
						}
						
						if(!Env.getSystemDate(transCurrentDepositInfo.getReceiveOfficeID(), transCurrentDepositInfo.getCurrencyID())
								.equals(Env.getSystemDate(transCurrentDepositInfo.getParentOfficeID(), transCurrentDepositInfo.getCurrencyID())))
						{
							throw new IRollbackException(
									mySessionCtx,
									"收方与总部开机日不一致，交易失败");
						}
					}
				}		
			}
			catch (Exception e)
			{
				throw new IRollbackException(mySessionCtx, e.getMessage(), e);
			}
			
		}
}
