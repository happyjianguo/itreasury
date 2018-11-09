/**
 * 
 */
package com.iss.itreasury.settlement.bankinstruction.instructionbean;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.bankinstruction.IImportAccount;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.setting.dao.Sett_BranchDAO;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_SpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationAssembler;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.integration.client.info.ImpAccountResultItem;
import com.iss.itreasury.settlement.integration.client.constant.ResultStatus;
import com.iss.itreasury.settlement.integration.client.info.AccountTransInfo;

/**
 * @author qijiang 南航项目结算入账操作实现类
 */
public class ImportAccountBean_Product implements IImportAccount {

	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * 
	 */
	public ImportAccountBean_Product() {
		super();
	}

	/**
	 * 接受银行交易信息，进行结算入账处理
	 * 
	 * @param param
	 * @throws IException
	 */
	public ImpAccountResultItem save(String accountNO, String bankAccountCode,
			AccountTransInfo bankAccountTransInfo) {

		log
				.info("Enter into ImportAccountBean_SouthAir.save(ReceiveInstructionParam param)!!");

		if (bankAccountTransInfo == null) {
			log.error("交易信息为空,无法执行结算入账处理");
			return new ImpAccountResultItem(ResultStatus.FAIL,
					"交易信息为空,无法执行结算入账处理", null, null);
		}

		/*
		 * try { Sett_AccountDAO accountDao = new Sett_AccountDAO(); AccountInfo
		 * accountInfo = accountDao.findByAccountNO(accountNO); Timestamp
		 * ts=EndDayProcess.getSystemDate(accountInfo.getOfficeID(),accountInfo.getCurrencyID());
		 * if(bankAccountTransInfo.getExecuteDate().after(ts)){
		 * log.error("交易日期大于开机日期，不形成结算交易"); log.info("开机日："+ts.toString());
		 * log.info("执行日/交易日："+bankAccountTransInfo.getExecuteDate().toString());
		 * return new
		 * ImpAccountResultItem(ResultStatus.FAIL,"交易日期大于开机日期，不形成结算交易",null); } }
		 * catch (Exception e1) { e1.printStackTrace(); return new
		 * ImpAccountResultItem(ResultStatus.FAIL,"日期判断出错，不形成结算交易",null); }
		 */

		log.info("入账的信息："
				+ UtilOperation.dataentityToString(bankAccountTransInfo));

		// Date date = null; //交易处理日期

		if (bankAccountTransInfo.getTypeId() == SETTConstant.GlobalBusinessTypeId.CONVERGE) {// 归集业务
			TransSpecialOperationInfo transInfo = null;
			try {

				// 判断借贷方向为“贷”，即收款交易
				if (bankAccountTransInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT) {
					transInfo = this.transBankAccountToTransSpecialOperation(
							accountNO, bankAccountCode, bankAccountTransInfo,
							SETTConstant.TransactionType.SPECIALOPERATION);
					
				} else {// 借贷方向为“借”，即付款交易，这里仅为成员单位的被动支付
					transInfo = this.transBankAccountToTransSpecialOperation(
							accountNO, bankAccountCode, bankAccountTransInfo,
							SETTConstant.TransactionType.SPECIALOPERATION);
				}
				log.info("转换后得信息为 "
						+ UtilOperation.dataentityToString(transInfo));

				Timestamp systemDate = Env.getSystemDate(transInfo
						.getNofficeid(), transInfo.getNcurrencyid());

				// 判断是否已经关机
				if (EndDayProcess.getSystemStatusID(transInfo.getNofficeid(),
						transInfo.getNcurrencyid()) != Constant.SystemStatus.OPEN) {
					log.info("系统关机，办事处编号:" + transInfo.getNofficeid() + "币种："
							+ transInfo.getNcurrencyid());
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"系统已经关机", transInfo.getStransno(), systemDate);
				}
				// 判断是否正在关机
				if (CloseSystemMain.getDealStatusID(transInfo.getNofficeid(),
						transInfo.getNcurrencyid(),
						Constant.ModuleType.SETTLEMENT) == Constant.ShutDownStatus.DOING) {
					log.info("系统正在关机，办事处编号:" + transInfo.getNofficeid() + "币种："
							+ transInfo.getNcurrencyid());
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"系统正在关机", transInfo.getStransno(), systemDate);
				}

				// 校验特殊业务类型设置--归集业务编号与配置项是否一致
				boolean blnHasSetSpecialOperationSetting = false;
				long sett_specialOperation_converge_number = Config.getLong(
						"sett_specialOperation_converge_number", 1003);
				try {
					blnHasSetSpecialOperationSetting = findSett_SpecialOperationSettingById(sett_specialOperation_converge_number);
				} catch (Exception e) {
					log.info("查询特殊业务类型设置失败:" + transInfo.getNofficeid() + "币种："
							+ transInfo.getNcurrencyid());
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"查询特殊业务类型设置", transInfo.getStransno(), systemDate);
				}
				if (!blnHasSetSpecialOperationSetting) {
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"特殊业务类型设置--归集业务编号与配置项不一致或未进行设置", transInfo
									.getStransno(), systemDate);
				}
				transInfo
						.setNoperationtypeid(sett_specialOperation_converge_number);

				// 自动保存复核
				log.info("---------开始自动保存复核操作------------");
				try {
					transInfo.setAutocreatebankinstruction(false); //不自动发送指令
					TransSpecialOperationAssembler assembler = new TransSpecialOperationAssembler(
							transInfo);					
							
					TransSpecialOperationAssembler newAssembler = new TransSpecialOperationDelegation()
							.saveAndCheckAutomatically(assembler);
					// long depositId =
					// currentDepositDelegation.save(assembler);

				} catch (RemoteException e) {
					log.error("自动保存复核失败！");
					e.printStackTrace();
					return new ImpAccountResultItem(ResultStatus.FAIL, e
							.getMessage(), transInfo.getStransno(), systemDate);
				} catch (IRollbackException e) {
					log.error("自动保存复核失败，回滚失败");
					e.printStackTrace();
					return new ImpAccountResultItem(ResultStatus.FAIL, e
							.getMessage(), transInfo.getStransno(), systemDate);
				}
				log.info("---------自动保存复核操作结束------------");

				// 入账成功
				log.info("监控自动入账成功： " + transInfo.getStransno());
				log.info("---------更改交易指令状态为结算复核成功  完成------------");
				return new ImpAccountResultItem(ResultStatus.SUCCESS, "",
						transInfo.getStransno(), systemDate);

			} catch (Exception e) {
				e.printStackTrace();
				Log.print("---------处理异常更改交易状态为结算复核失败 "
						+ transInfo.getStransno() + "------------");
				String strError = "无法捕捉的异常";
				if (e instanceof IException) {
					if (IExceptionMessage.getExceptionMessage(((IException) e)
							.getErrorCode()) != null) {
						strError = IExceptionMessage.getExceptionMessage(e
								.getMessage());
					}
				}
				return new ImpAccountResultItem(ResultStatus.FAIL, strError,
						transInfo.getStransno(), null);
			}
		} else {
			// 正常业务
			TransCurrentDepositInfo transInfo = null;
			try {
				// 判断本方账户是成员单位账户，则为成员单位收款业务；若本方账户是财务公司账户，则为财务公司总账户的收款业务
				// String accountNo = bankAccountTransInfo.getAccountNo();
				// long bankType = bankAccountTransInfo.getBankType();
				// FilialeAccountInfo filialeAccountInfo = null;
				// Sett_FilialeAccountSetDAO filialeDAO = new
				// Sett_FilialeAccountSetDAO();
				// filialeAccountInfo = filialeDAO.findByAccountNo(accountNo,
				// bankType);

				// 判断对方账户是否是财务公司总账户
				/*
				 * BranchInfo branchInfo = new BranchInfo(); Sett_BranchDAO
				 * branchDAO = new Sett_BranchDAO(); Collection coll = null;
				 * boolean bOppAccountIsBranch = false;
				 * branchInfo.setBankAccountCode(bankAccountTransInfo.getOppositeAccountNo());
				 * coll = branchDAO.findByConditions(branchInfo,1,false);
				 * if(coll!=null && coll.size()>0) { bOppAccountIsBranch = true; }
				 * else { log.info("交易的对方账户不是总账户！"); }
				 */

				// 判断借贷方向为“贷”，即收款交易
				if (bankAccountTransInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT) {
					transInfo = this.transBankAccountToCurrentDeposit(
							accountNO, bankAccountCode, bankAccountTransInfo,
							SETTConstant.TransactionType.BANKRECEIVE);
				} else {
					transInfo = this.transBankAccountToCurrentDeposit(
							accountNO, bankAccountCode, bankAccountTransInfo,
							SETTConstant.TransactionType.BANKPAY);
				}

				log.info("转换后得信息为 "
						+ UtilOperation.dataentityToString(transInfo));
				Timestamp systemDate = Env.getSystemDate(transInfo
						.getOfficeID(), transInfo.getCurrencyID());
				// 判断是否已经关机
				if (EndDayProcess.getSystemStatusID(transInfo.getOfficeID(),
						transInfo.getCurrencyID()) != Constant.SystemStatus.OPEN) {
					log.info("系统关机，办事处编号:" + transInfo.getOfficeID() + "币种："
							+ transInfo.getCurrencyID());
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"系统已经关机", transInfo.getTransNo(), systemDate);
				}
				// 判断是否正在关机
				if (CloseSystemMain.getDealStatusID(transInfo.getOfficeID(),
						transInfo.getCurrencyID(),
						Constant.ModuleType.SETTLEMENT) == Constant.ShutDownStatus.DOING) {
					log.info("系统正在关机，办事处编号:" + transInfo.getOfficeID() + "币种："
							+ transInfo.getCurrencyID());
					return new ImpAccountResultItem(ResultStatus.FAIL,
							"系统正在关机", transInfo.getTransNo(), systemDate);
				}

				// 自动保存复核
				log.info("---------开始自动保存复核操作------------");
				TransCurrentDepositDelegation currentDepositDelegation;
				try {
					currentDepositDelegation = new TransCurrentDepositDelegation();

					TransCurrentDepositAssembler assembler = new TransCurrentDepositAssembler(
							transInfo);

					log.info("-------------是否生成银行指令-------"
							+ assembler.getSett_TransCurrentDepositInfo()
									.isAutoCreateBankInstruction());
					TransCurrentDepositAssembler newAssembler = currentDepositDelegation
							.saveAndCheckAutomatically(assembler);
					// long depositId =
					// currentDepositDelegation.save(assembler);

				} catch (RemoteException e) {
					log.error("自动保存复核失败！");
					e.printStackTrace();
					return new ImpAccountResultItem(ResultStatus.FAIL, e
							.getMessage(), transInfo.getTransNo(), systemDate);
				} catch (IRollbackException e) {
					log.error("自动保存复核失败，回滚失败");
					e.printStackTrace();
					return new ImpAccountResultItem(ResultStatus.FAIL, e
							.getMessage(), transInfo.getTransNo(), systemDate);
				}
				log.info("---------自动保存复核操作结束------------");

				// 入账成功
				log.info("监控自动入账成功： " + transInfo.getTransNo());
				log.info("---------更改交易指令状态为结算复核成功  完成------------");
				return new ImpAccountResultItem(ResultStatus.SUCCESS, "",
						transInfo.getTransNo(), systemDate);

			} catch (Exception e) {
				e.printStackTrace();
				Log.print("---------处理异常更改交易状态为结算复核失败 "
						+ transInfo.getTransNo() + "------------");
				String strError = "无法捕捉的异常";
				if (e instanceof IException) {
					if (IExceptionMessage.getExceptionMessage(((IException) e)
							.getErrorCode()) != null) {
						strError = IExceptionMessage.getExceptionMessage(e
								.getMessage());
					}
				}
				return new ImpAccountResultItem(ResultStatus.FAIL, strError,
						transInfo.getTransNo(), null);
			}
		}
	}

	private boolean findSett_SpecialOperationSettingById(long lID)
			throws Exception {
		boolean blnReturn = false;

		SpecialOperationInfo specialOperationInfo = new Sett_SpecialOperationDAO()
				.findByID(lID);

		if (null != specialOperationInfo)
			blnReturn = true;

		return blnReturn;
	}

	private TransCurrentDepositInfo transBankAccountToCurrentDeposit(
			String accountNO, String bankAccountCode,
			AccountTransInfo accountTransInfo, long transType) throws Exception {
		// 先把账户信息查出来
		// Sett_FilialeAccountSetDAO filialeAccountSetDAO = new
		// Sett_FilialeAccountSetDAO();
		Sett_AccountDAO accountDao = new Sett_AccountDAO();

//		QueryAccountConditionInfo queryInfo = new QueryAccountConditionInfo();
		
		//modified by mzh_fu 2008/04/02 取正常状态的的账户
		//AccountInfo accountInfo = accountDao.findByAccountNO(accountNO);// 账户信息
		AccountInfo accountInfo = accountDao.findNormalByAccountNO(accountNO);// 账户信息

		if(null == accountInfo)
			throw new IException("账户 " + accountNO + " 状态无效");
		

		TransCurrentDepositInfo transInfo = new TransCurrentDepositInfo();

		// 交易方向为“贷”时，表示本方账户为收款
		if (accountTransInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT) {
			transInfo.setReceiveAccountID(accountInfo.getAccountID());
			transInfo.setReceiveClientID(accountInfo.getClientID());
		}
		// 交易方向为“借”时，表示本方账户为付款
		else if (accountTransInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT) {
			transInfo.setPayAccountID(accountInfo.getAccountID());
			transInfo.setPayClientID(accountInfo.getClientID());
		}

		transInfo.setTransactionTypeID(transType);
		transInfo.setExtAccountNo(accountTransInfo.getOppAccountNO());// 非财务公司账户号
		transInfo.setExtClientName(accountTransInfo.getOppAccountName());// 非财务公司客户名称
		transInfo.setRemitInBank(accountTransInfo.getOppBranchName());// 非财务公司汇入银行名称
		transInfo.setOfficeID(accountInfo.getOfficeID());
		transInfo.setCurrencyID(accountInfo.getCurrencyID());

		// 根据开户行账户找到开户行ID
		Sett_BranchDAO branchDao = new Sett_BranchDAO();
		BranchInfo queryBranchInfo = new BranchInfo();
		BranchInfo branchInfo = new BranchInfo();
		queryBranchInfo.setBankAccountCode(bankAccountCode);

		// added by mzh_fu 2007/04/19 开户行科目类型
		long lBankSubjectType = Config.getLong(
				Config.SETT_BRANCH_BANKSUBJECTTYPE,
				SETTConstant.BankSubjectType.NORMAL);
		
		queryBranchInfo.setBankSubjectType(lBankSubjectType);

		Collection col = branchDao.findByConditions(queryBranchInfo,
				Sett_BranchDAO.ORDERBY_ID, true);
		if (col.iterator().hasNext())
			branchInfo = (BranchInfo) col.iterator().next();

		transInfo.setBankID(branchInfo.getID());
		transInfo.setAmount(accountTransInfo.getAmount());
		transInfo.setInputDate(DataToTimestamp(accountTransInfo
				.getExecuteDate()));
		
		// 起息日设为执行日
		transInfo.setInterestStartDate(DataToTimestamp(accountTransInfo
				.getExecuteDate()));
		
		// 执行日设为开机日
		transInfo.setExecuteDate(EndDayProcess.getSystemDate(accountInfo
				.getOfficeID(), accountInfo.getCurrencyID()));
		transInfo.setAbstractStr(accountTransInfo.getAbstract());
		transInfo.setConsignVoucherNo(accountTransInfo.getCheckNO());// 委托付款凭证号
		transInfo.setInstructionNo(accountTransInfo.getTransNoOfBank());
		transInfo.setAutoCreateBankInstruction(false);
		return transInfo;
	}

	private TransSpecialOperationInfo transBankAccountToTransSpecialOperation(
			String accountNO, String bankAccountCode,
			AccountTransInfo accountTransInfo, long transType) throws Exception {
		Sett_AccountDAO accountDao = new Sett_AccountDAO();

//		QueryAccountConditionInfo queryInfo = new QueryAccountConditionInfo();

		//modified by mzh_fu 2008/04/02 取正常状态的的账户
		//AccountInfo accountInfo = accountDao.findByAccountNO(accountNO);// 账户信息
		AccountInfo accountInfo = accountDao.findNormalByAccountNO(accountNO);// 账户信息

		if(null == accountInfo)
			throw new IException("账户 " + accountNO + " 状态无效");
		
		
		TransSpecialOperationInfo transInfo = new TransSpecialOperationInfo();

		transInfo.setNtransactiontypeid(transType);
		transInfo.setSextaccountno(accountTransInfo.getOppAccountNO());// 非财务公司账户号
		transInfo.setSextclientname(accountTransInfo.getOppAccountName());// 非财务公司客户名称
		transInfo.setSremitinbank(accountTransInfo.getOppBranchName());// 非财务公司汇入银行名称
		transInfo.setNofficeid(accountInfo.getOfficeID());
		transInfo.setNcurrencyid(accountInfo.getCurrencyID());

		// 根据开户行账户找到开户行ID
		Sett_BranchDAO branchDao = new Sett_BranchDAO();
		BranchInfo queryBranchInfo = new BranchInfo();
		BranchInfo branchInfo = new BranchInfo();
		queryBranchInfo.setBankAccountCode(bankAccountCode);
		
		// 取得正常科目下的开户行ID
		queryBranchInfo.setBankSubjectType(SETTConstant.BankSubjectType.NORMAL);
		
		Collection col = branchDao.findByConditions(queryBranchInfo,
				Sett_BranchDAO.ORDERBY_ID, true);		
		if (col.iterator().hasNext())
			branchInfo = (BranchInfo) col.iterator().next();
		
		long normalBankId = branchInfo.getID();
		
		// 取得过度科目下的开户行ID
		queryBranchInfo
				.setBankSubjectType(SETTConstant.BankSubjectType.TRANSITION);
		
		col = branchDao.findByConditions(queryBranchInfo,
				Sett_BranchDAO.ORDERBY_ID, true);
		if (col.iterator().hasNext())
			branchInfo = (BranchInfo) col.iterator().next();
		
		long transtionBankId = branchInfo.getID();

		// 交易方向为“贷”时，表示本方账户为收款
		if (accountTransInfo.getDirection() == SETTConstant.DebitOrCredit.CREDIT) {
			transInfo.setNreceivebankid(normalBankId);
			transInfo.setNpaybankid(transtionBankId);
		}
		// 交易方向为“借”时，表示本方账户为付款
		else if (accountTransInfo.getDirection() == SETTConstant.DebitOrCredit.DEBIT) {
			transInfo.setNreceivebankid(transtionBankId);
			transInfo.setNpaybankid(normalBankId);
		}
		
		transInfo.setNreceivedirection(SETTConstant.DebitOrCredit.DEBIT);
		transInfo.setNpaydirection(SETTConstant.DebitOrCredit.CREDIT);
		
		transInfo.setMpayamount(accountTransInfo.getAmount());
		transInfo.setMreceiveamount(accountTransInfo.getAmount());
		transInfo
				.setDtinput(DataToTimestamp(accountTransInfo.getExecuteDate()));
		// 起息日设为执行日
		transInfo.setDtintereststart(DataToTimestamp(accountTransInfo
				.getExecuteDate()));
		// 执行日设为开机日
		transInfo.setDtexecute(EndDayProcess.getSystemDate(accountInfo
				.getOfficeID(), accountInfo.getCurrencyID()));
		transInfo.setSabstract(accountTransInfo.getAbstract());
		transInfo.setInstructionno(accountTransInfo.getTransNoOfBank());
		transInfo.setAutocreatebankinstruction(false);
		transInfo.setNstatusid(SETTConstant.TransactionStatus.SAVE);

		return transInfo;
	}

	private Timestamp DataToTimestamp(Date dtDate) {
		if (dtDate == null)
			return null;
		Timestamp tsDate = new Timestamp(dtDate.getYear(), dtDate.getMonth(),
				dtDate.getDate(), dtDate.getHours(), dtDate.getMinutes(),
				dtDate.getSeconds(), 0);
		return tsDate;
	}
}
