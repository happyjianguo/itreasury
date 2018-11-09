/*
 * Created on 2004-8-2
 * 
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.compatibilityaccountbook.bizlogic;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.bizdelegation.AccountTrustVoucherDelegation;
import com.iss.itreasury.settlement.compatibilitysetting.bizlogic.CompatibilitySubAccountSettingBean;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dao.Sett_GeneralLedgerDAO;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo;
import com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.settlement.transloan.dao.Loan_DAO;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
/**
 * @author gqzhang To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CompatibilityAccountBookEJB implements SessionBean
{
	private javax.ejb.SessionContext ctx = null;
	final static long serialVersionUID = 3206093459760846163L;
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	private AccountOperation accountOperation = null;
	private GeneralLedgerOperation glopOperation = null;
	/**
	 * ejbActivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 * 异常说明。
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbCreate() throws javax.ejb.CreateException, RemoteException
	{
		try
		{
			accountOperation = new AccountOperation();
			glopOperation = new GeneralLedgerOperation();
		}
		catch (Exception e)
		{
			log.debug("---------无法初使化AccountBook及其相关子系统，交易失败-----------");
			throw new CreateException();
		}
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * 异常说明。
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
		return ctx;
	}
	/**
	 * setSessionContext method comment
	 * 
	 * @param ctx
	 * javax.ejb.SessionContext
	 * @exception java.rmi.RemoteException
	 * 异常说明。
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	/**
	 * Method saveCompatibilityAccountDetails. 保存兼容业务账薄信息
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveCompatibilityAccountDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始保存兼容财务交易--------");
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		long lAccountTypeID = -1;
		long lAccountGroupID = -1;
		if (vctDetail != null && vctDetail.size() > 0)
		{
			for (int i = 0; i < vctDetail.size(); i++)
			{
				detailInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
				if (detailInfo != null)
				{
					//如果填入了委托付款凭证号码 和密码 则调用使用的方法
					if (detailInfo.getVoucher() != null && detailInfo.getVoucher().length() > 0 && detailInfo.getPassword() != null && detailInfo.getPassword().length() > 0)
					{
						log.print("保存校验委托付款凭证号＝" + detailInfo.getVoucher() + "========================" + detailInfo.getPassword());
						AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
						long lVoucherReturn = atvDelegation.updateStatusByUse(detailInfo.getVoucher(), detailInfo.getPassword(), detailInfo.getAccountID(), transInfo.getTransNo());
						log.print("VoucherReturn=" + lVoucherReturn);
						if (lVoucherReturn <= 0)
						{
							log.print("----------委托付款凭证保存不成功-----------");
							throw new IRollbackException(ctx, "Sett_E054", detailInfo.getVoucher());
						}
					}
					//如果该交易有付款方，且付款方为内部账户，则调用“累计未复核金额”的方法
					log.info("账户:" + detailInfo.getAccountID());
					if (detailInfo.getAccountID() > 0)
					{
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("账户:" + detailInfo.getAccountID() + "是付款方");
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//活期账户
									log.info("========活期账户=========");
									accountOperation.addCurrentUncheckAmount(detailInfo.getAccountID(), -1, detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//定期账户
									log.info("========定期账户=========");
									accountOperation.addFixedUncheckAmount(detailInfo.getAccountID(), detailInfo.getDepositForm(), detailInfo.getAmount(),-1);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//贷款账户
									log.info("========贷款账户=========");
									accountOperation.addLoanUncheckAmount(detailInfo.getAccountID(), detailInfo.getDueBillID(), detailInfo.getAmount());
								}
							}
						}
						else
						{
							throw new IRollbackException(ctx, "无法账户对应的主账户信息，交易失败");
						}
					}
				}
			}
		}
		log.info(" ------结束保存兼容财务交易--------");
	}
	/**
	 * Method deleteTransCompatibility. 删除兼容业务账薄信息
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteTransCompatibility(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		long lAccountTypeID = -1;
		long lAccountGroupID = -1;
		Loan_DAO loan_DAO = new Loan_DAO();
		if (vctDetail != null && vctDetail.size() > 0)
		{
			for (int i = 0; i < vctDetail.size(); i++)
			{
				detailInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
				if (detailInfo != null)
				{
					//如果填入了委托付款凭证号码 和密码 则调用使用的方法
					if (detailInfo.getVoucher() != null && detailInfo.getVoucher().length() > 0 && detailInfo.getPassword() != null && detailInfo.getPassword().length() > 0)
					{
						log.print("删除校验委托付款凭证号＝" + transInfo.getTransNo() + "========================");
						AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
						long lVoucherReturn = atvDelegation.updateStatusByTransNo(transInfo.getTransNo());
						log.print("VoucherReturn=" + lVoucherReturn);
						if (lVoucherReturn <= 0)
						{
							log.print("----------委托付款凭证删除不成功-----------");
							throw new IRollbackException(ctx, "Sett_E054", detailInfo.getVoucher());
						}
					}
					//如果该交易有付款方，且付款方为内部账户，则调用“扣除未复核金额”的方法
					log.info("账户:" + detailInfo.getAccountID());
					//判断该账户是否是付款账户，取决于两点：一 交易方向 二 账户余额方向
					if (detailInfo.getAccountID() > 0)
					{
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("账户:" + detailInfo.getAccountID() + "是付款方");
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//活期账户
									accountOperation.subtractCurrentUncheckAmount(detailInfo.getAccountID(), detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//定期账户
									accountOperation.subtractFixedUncheckAmount(detailInfo.getAccountID(), detailInfo.getDepositForm(), detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//贷款账户
									accountOperation.subtractLoanUncheckAmount(detailInfo.getAccountID(), detailInfo.getDueBillID(), detailInfo.getAmount());
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Method checkCompatibilityDetails. 复核兼容交易
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info("=============开始复核兼容交易=======");
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		if (vctDetail != null && vctDetail.size() > 0)
		{
			log.info("=============兼容交易明细不为空=======");
			for (int i = 0; i < vctDetail.size(); i++)
			{
				detailInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
				if (detailInfo != null)
				{
					TransAccountDetailInfo tadi = null;
					long lSubAccountID = -1;
					long lAccountTypeID = -1;
					long lAccountGroupID = -1;
					GLEntryInfo glEntryInfo = new GLEntryInfo();
					String strSubjectCode = "";
					//1 如果资金来源是账户
					if (detailInfo.getAccountID() > 0)
					{
						log.info("=============当前兼容交易明细资金来源是账户=======：" + detailInfo.getAccountID());
						try
						{
							accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						log.info("=============查询主账户信息之后======");
						if (accountInfo != null)
						{
							log.info("=============存在对应的账户信息=======");
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("=============当前账户为付款方=======");
								//如果为付款方，将本次未复核金额付清
								tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 1);
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//活期账户
									log.info("=============当前账户为付款活期=======");
									lSubAccountID = accountOperation.withdrawCurrent(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//定期账户
									log.info("=============当前账户为付款定期=======");
									lSubAccountID = accountOperation.withdrawFix(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//贷款账户
									log.info("=============当前账户为付款贷款=======");
									lSubAccountID = accountOperation.repayLoan(tadi);
									//检查票据是否已经冲销
									if (detailInfo.getContractID() > 0 && detailInfo.getDueBillID() > 0 && detailInfo.getBillID() > 0)
									{
										//判断是否是转贴现卖出买断型合同
										try
										{
											Loan_DAO loan_DAO = new Loan_DAO();
											//维护票据冲销状态
											log.print("----------判断是否是转贴现卖出买断型合同-----------");
											if (loan_DAO.IsReDiscountBuyBreakOutContract(detailInfo.getContractID()))
											{
												//获取票据的冲销状态,如果已经冲销，则产生异常
												long lBillAbatementStatus = loan_DAO.getDiscountBillAbatementStatusIDByBillID(detailInfo.getBillID());
												log.print("----------票据冲销状态-----------:" + lBillAbatementStatus);
												if (lBillAbatementStatus < 0)
												{
													throw new IRollbackException(ctx, "无法获得票据id：" + detailInfo.getBillID() + "对应的票据信息，交易失败");
												}
												else
													if (lBillAbatementStatus == SETTConstant.DiscountBillAbatementStatus.YES)
													{
														throw new IRollbackException(ctx, "票据id：" + detailInfo.getBillID() + "对应的票据已经冲销，交易失败");
													}
												//否则修改票据状态为已经冲销
												log.print("----------修改票据状态为已经冲销-----------");
												loan_DAO.changeIsabatementForBill(detailInfo.getBillID(), SETTConstant.DiscountBillAbatementStatus.YES);
											}
											//维护票据是否可以卖出状态
											log.print("----------判断是否是转贴现买入买断型合同-----------");
											if (loan_DAO.IsReDiscountBuyBreakInContract(detailInfo.getContractID()))
											{//转贴现买入到期收回
												log.print("----------修改票据状态为不可卖出-----------");
												loan_DAO.updateDiscountBillSellStatusID(detailInfo.getBillID(),Constant.YesOrNo.NO);
											}
										}
										catch (ITreasuryDAOException e)
										{
											throw new IRollbackException(ctx, e.getMessage(), e.getOriginalException());
										}
									}
								}
							}
							else
								if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.RECEIVE)
								{
									log.info("=============当前账户为收款方=======");
									//如果为收款方，账户金额增加
									tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 2);
									if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
											|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
									{
										//活期账户
										log.info("=============当前账户为收款活期=======");
										lSubAccountID = accountOperation.depositCurrent(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
											||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
									{
										//定期账户
										log.info("=============当前账户为收款定期=======");
										lSubAccountID = accountOperation.depositFix(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
											||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
											||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
											||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
									{
										//贷款账户
										log.info("=============当前账户为收款贷款=======");
										lSubAccountID = accountOperation.grantLoan(tadi);
										//增加子账户开立金额
										Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO();
										try
										{
											subAccountDao.UpdateOpenAmount(detailInfo.getSubAccountID(), detailInfo.getAmount());
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "修改子账户ID" + detailInfo.getSubAccountID() + "对应的子账户开户金额失败");
										}
									}
								}
						}
						else
						{
							log.info("=============主账户信息为空=======");
							throw new IRollbackException(ctx, "无法找到账户id" + detailInfo.getAccountID() + "对应的主账户信息，交易失败");
						}
						try
						{
							//取自设科目待增加
							//取兼容业务账户设置中的科目-CPF于iTreasury相同
							if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
							{
								log.info("=============取兼容业务账户中设置的科目====");
								//getSubjectByCompatibilitySetting
								// 相当于取子账户中设置的科目，但是由于优先取该科目
								strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
							}
							//先取主账户设置中的科目，后取账户类型设置中的科目
							if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
							{
								log.info("=============取账户类型设置中的科目=======");
								//getSubjectBySubAccountID中取科目的流程是：取主账户科目，取账户类型科目
								strSubjectCode = getSubjectBySubAccountID(lSubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
							}
						}
						catch (IException e)
						{
							throw new IRollbackException(ctx, e.getMessage(), e);
						}
					}
					else
						if (detailInfo.getBankID() > 0)
						{
							log.info("=============当前兼容交易明细资金来源是开户行=======");
							//2如果资金来源是开户行
							strSubjectCode = getSubjectByBankID(detailInfo.getBankID());
						}
						else
							if (detailInfo.getGLID() > 0)
							{
								log.info("=============当前兼容交易明细资金来源是总账=======");
								//3如果资金来源是总账
								strSubjectCode = findGeneralLedgerSubjectCode(detailInfo.getGLID());
							}
					log.info("=============开始设置会计账信息=======");
					if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
					{
						log.info("=============科目为空=======");
						throw new IRollbackException(ctx, "无法找到对应科目，交易失败");
					}
					log.info("=============科目:" + strSubjectCode);
					glEntryInfo.setSubjectCode(strSubjectCode);
					glEntryInfo.setTransDirection(detailInfo.getTransDirectionID());
					glEntryInfo.setAmount(detailInfo.getAmount());
					glEntryInfo.setAbstract(transInfo.getAbstract());
					glEntryInfo.setCheckUserID(transInfo.getCheckUserID());
					glEntryInfo.setCurrencyID(transInfo.getCurrencyID());
					glEntryInfo.setExecute(transInfo.getExecuteDate());
					glEntryInfo.setInputUserID(transInfo.getInputUserID());
					glEntryInfo.setInterestStart(transInfo.getInterestStartDate());
					glEntryInfo.setOfficeID(transInfo.getOfficeID());
					glEntryInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
					glEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
					glEntryInfo.setTransNo(transInfo.getTransNo());
					log.debug("======会计分录内容====");
					GLEntryInfo[] infos = { glEntryInfo };
					glopOperation.addGLEntries(infos);
				}
			}
		}
		log.info("=============结束复核兼容交易=======");
	}
	/**
	 * Method cancelCheckCompatibilityDetails. 取消复核兼容交易
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info("=============开始取消复核兼容交易=======");
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		long lAccountTypeID = -1;
		long lAccountGroupID = -1;
		if (vctDetail != null && vctDetail.size() > 0)
		{
			log.info("=============兼容交易明细不为空=======");
			for (int i = 0; i < vctDetail.size(); i++)
			{
				detailInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
				if (detailInfo != null)
				{
					TransAccountDetailInfo tadi = null;
					GLEntryInfo glEntryInfo = new GLEntryInfo();
					String strSubjectCode = "";
					//1 如果资金来源是账户
					if (detailInfo.getAccountID() > 0)
					{
						log.info("=============当前兼容交易明细资金来源是账户=======");
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("=============当前账户为付款方=======");
								//如果为付款方，将本次未复核金额付清
								tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 1);
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT
)
								{
									//活期账户
									log.info("=============当前账户为付款活期=======");
									accountOperation.cancelWithdrawCurrent(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//定期账户
									log.info("=============当前账户为付款定期=======");
									accountOperation.cancelWithdrawFix(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//贷款账户
									log.info("=============当前账户为付款贷款=======");
									accountOperation.cancelRepayLoan(tadi);
									//判断是否是转贴现卖出买断型合同
									try
									{
										if (detailInfo.getContractID() > 0 && detailInfo.getDueBillID() > 0 && detailInfo.getBillID() > 0)
										{
											Loan_DAO loan_DAO = new Loan_DAO();
											log.print("----------判断是否是转贴现卖出买断型合同-----------");
											if (loan_DAO.IsReDiscountBuyBreakOutContract(detailInfo.getContractID()))
											{
												//获取票据的状态,如果未冲销，则产生异常
												long lBillAbatementStatus = loan_DAO.getDiscountBillAbatementStatusIDByBillID(detailInfo.getBillID());
												log.print("----------票据冲销状态-----------:" + lBillAbatementStatus);
												if (lBillAbatementStatus < 0)
												{
													throw new IRollbackException(ctx, "无法获得票据id：" + detailInfo.getBillID() + "对应的票据信息，交易失败");
												}
												else
													if (lBillAbatementStatus == SETTConstant.DiscountBillAbatementStatus.NO)
													{
														throw new IRollbackException(ctx, "票据id：" + detailInfo.getBillID() + "对应的票据未冲销，交易失败");
													}
												//否则修改票据状态为未冲销
												log.print("----------修改票据状态为未冲销-----------");
												loan_DAO.changeIsabatementForBill(detailInfo.getBillID(), SETTConstant.DiscountBillAbatementStatus.NO);
											}
											
											//维护票据是否可以卖出状态
											log.print("----------判断是否是转贴现买入买断型合同-----------");
											if (loan_DAO.IsReDiscountBuyBreakInContract(detailInfo.getContractID()))
											{//转贴现买入到期收回
												log.print("----------修改票据状态为可卖出-----------");
												loan_DAO.updateDiscountBillSellStatusID(detailInfo.getBillID(),Constant.YesOrNo.YES);
											}
										}
									}
									catch (ITreasuryDAOException e)
									{
										throw new IRollbackException(ctx, "判断合同类型产生错误", e);
									}
								}
							}
							else
								if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.RECEIVE)
								{
									log.info("=============当前账户为收款方=======");
									//如果为收款方，账户金额增加
									tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 2);
									log.info("＝＝＝放款通知单id：" + detailInfo.getDueBillID());
									if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
											|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT
)
									{
										//活期账户
										log.info("=============当前账户为收款活期=======");
										accountOperation.cancelDepositCurrent(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
											||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY
)
									{
										//定期账户
										log.info("=============当前账户为收款定期=======");
										accountOperation.cancelDepositFix(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
											||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
											||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
											||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
									{
										//贷款账户
										log.info("=============当前账户为收款贷款=======");
										accountOperation.cancelGrantLoan(tadi);
										//特殊处理----修改子账户的状态
										Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO();
										try
										{
											log.info("=============开始修改状态=======");
											subAccountDao.updateFinishDateAndStatus(detailInfo.getSubAccountID(), SETTConstant.AccountStatus.NORMAL, null);
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "修改子账户ID" + detailInfo.getSubAccountID() + "对应的子账户状态信息失败");
										}
										//特殊处理----修改子账户的状态
										//减少子账户的开户金额
										try
										{
											subAccountDao.UpdateOpenAmount(detailInfo.getSubAccountID(), -detailInfo.getAmount());
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "修改子账户ID" + detailInfo.getSubAccountID() + "对应的子账户开户金额失败");
										}
									}
								}
						}
						else
						{
							log.info("=============主账户信息为空=======");
							throw new IRollbackException(ctx, "无法找到账户id" + detailInfo.getAccountID() + "对应的主账户信息，交易失败");
						}
					}
					log.info("=============开始删除账薄信息=======");
					accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
					log.info("=============开始删除会计账信息=======");
					glopOperation.deleteGLEntry(transInfo.getTransNo());
				}
			}
		}
		log.info("=============结束取消复核兼容交易=======");
	}
	/**
	 * Method saveAbamentInfoAccountDetails.保存转贴现卖出自动冲销业务账薄信息
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始保存转贴现卖出自动冲销业务财务交易--------");
		Vector vctAbmentDetailInfo = transInfo.getTransAbatementDetailInfo();
		TransAbatementDetailInfo transAbatementDetailInfo = null;
		if (vctAbmentDetailInfo != null && vctAbmentDetailInfo.size() > 0)
		{
			for (int i = 0; i < vctAbmentDetailInfo.size(); i++)
			{
				transAbatementDetailInfo = (TransAbatementDetailInfo) vctAbmentDetailInfo.elementAt(i);
				if (transAbatementDetailInfo != null)
				{
					//if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					//{
					accountOperation.addLoanUncheckAmount(transAbatementDetailInfo.getAccountID(), transAbatementDetailInfo.getCredenceID(), transAbatementDetailInfo.getAmount());
					//}
				}
			}
		}
		log.info(" ------结束保存转贴现卖出自动冲销业务财务交易--------");
	}
	/**
	 * Method deleteTransAbament. 删除转贴现卖出自动冲销账薄信息
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteTransAbament(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始删除转贴现卖出自动冲销业务财务交易--------");
		Vector vctAbmentDetailInfo = transInfo.getTransAbatementDetailInfo();
		TransAbatementDetailInfo transAbatementDetailInfo = null;
		if (vctAbmentDetailInfo != null && vctAbmentDetailInfo.size() > 0)
		{
			for (int i = 0; i < vctAbmentDetailInfo.size(); i++)
			{
				transAbatementDetailInfo = (TransAbatementDetailInfo) vctAbmentDetailInfo.elementAt(i);
				if (transAbatementDetailInfo != null)
				{
					if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					{
						accountOperation.subtractLoanUncheckAmount(
							transAbatementDetailInfo.getAccountID(),
							transAbatementDetailInfo.getCredenceID(),
							transAbatementDetailInfo.getAmount());
					}
				}
			}
		}
		log.info(" ------结束删除转贴现卖出自动冲销业务财务交易--------");
	}
	/**
	 * Method checkAbamentInfoAccountDetails.复核转贴现卖出自动冲销业务
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始复核转贴现卖出自动冲销业务财务交易--------");
		Vector vctAbmentDetailInfo = transInfo.getTransAbatementDetailInfo();
		TransAbatementDetailInfo transAbatementDetailInfo = null;
		long lSubAccountID = -1;
		if (vctAbmentDetailInfo != null && vctAbmentDetailInfo.size() > 0)
		{
			for (int i = 0; i < vctAbmentDetailInfo.size(); i++)
			{
				TransAccountDetailInfo tadi = null;
				GLEntryInfo glEntryInfo = new GLEntryInfo();
				String strSubjectCode = "";
				transAbatementDetailInfo = (TransAbatementDetailInfo) vctAbmentDetailInfo.elementAt(i);
				if (transAbatementDetailInfo != null)
				{
					log.info("----开始记录交易账薄明细-----");
					//if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					//{
					//	log.info(" -----借方:" + i);
					//	tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 1);
					//	lSubAccountID = accountOperation.grantLoan(tadi);
					//}
					//else
					//	if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					//	{
					tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 2);
					log.info(" -----账户明细信息----:");
					Log.print(tadi);
					lSubAccountID = accountOperation.repayLoan(tadi);
					//}
					log.info("----开始取科目-----");
					if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					{
						//如果借方
						log.info("----取借方科目----");
						//取自设科目待增加
						//取冲销交易中设置的对应科目
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----取转贴现卖出到期收回冲销业务中设置的借方科目----");
							strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getGLID());
						}
						//其次取账户设置中的科目
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----取兼容业务账户设置中设置的科目-----");
							try
							{
								strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
							}
							catch (SettlementException e)
							{
								throw new IRollbackException(ctx, e.getMessage(), e);
							}
						}
						//取主账户和账户类型设置中的科目
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----取主账户和账户类型设置中的科目-----");
							try
							{
								strSubjectCode = getSubjectBySubAccountID(lSubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
							}
							catch (IException e)
							{
								throw new IRollbackException(ctx, e.getMessage(), e);
							}
						}
					}
					else
						if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
						{
							//如果贷方
							log.info("----取贷方科目----");
							//取自设科目待增加
							//取冲销交易中设置的对应科目,需要区分是贴现合同还是转贴现合同
							if (transAbatementDetailInfo.getContractTypeID() == LOANConstant.LoanType.ZTX)
							{
								log.info("----转贴现合同----");
								//转贴现合同...
								if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
								{
									log.info("----取转贴现卖出到期收回冲销业务中设置的贷方转贴现科目----");
									strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getReDiscountGLID());
								}
							}
							else
								if (transAbatementDetailInfo.getContractTypeID() == LOANConstant.LoanType.TX)
								{
									log.info("----贴现合同----");
									log.info("----取转贴现卖出到期收回冲销业务中设置的贷方贴现科目----");
									//贴现合同...
									if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
									{
										strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getDiscountGLID());
									}
								}
							//其次取账户设置中的科目
							if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
							{
								log.info("----取兼容业务账户设置中设置的科目-----");
								try
								{
									strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
								}
								catch (SettlementException e)
								{
									throw new IRollbackException(ctx, e.getMessage(), e);
								}
							}
							//取主账户和账户类型设置中的科目
							if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
							{
								log.info("----取主账户和账户类型设置中的科目-----");
								try
								{
									strSubjectCode = getSubjectBySubAccountID(lSubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
								}
								catch (IException e)
								{
									throw new IRollbackException(ctx, e.getMessage(), e);
								}
							}
						}
					log.info("---------取科目结束：" + strSubjectCode);
					if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
					{
						log.info("=============科目为空=======");
						throw new IRollbackException(ctx, "无法找到对应科目，交易失败");
					}
					log.info("---------开始设置会计账信息------");
					glEntryInfo.setSubjectCode(strSubjectCode);
					glEntryInfo.setTransDirection(transAbatementDetailInfo.getTransDirectionID());
					glEntryInfo.setAmount(transAbatementDetailInfo.getAmount());
					glEntryInfo.setAbstract(transInfo.getAbstract());
					glEntryInfo.setCheckUserID(transInfo.getCheckUserID());
					glEntryInfo.setCurrencyID(transInfo.getCurrencyID());
					glEntryInfo.setExecute(transInfo.getExecuteDate());
					glEntryInfo.setInputUserID(transInfo.getInputUserID());
					glEntryInfo.setInterestStart(transInfo.getInterestStartDate());
					glEntryInfo.setOfficeID(transInfo.getOfficeID());
					glEntryInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
					glEntryInfo.setTransactionTypeID(transInfo.getTransactionTypeID());
					glEntryInfo.setTransNo(transInfo.getTransNo());
					log.debug("---------开始记录会计账-------");
					GLEntryInfo[] infos = { glEntryInfo };
					glopOperation.addGLEntries(infos);
				}
			}
		}
		log.info(" ------结束复核转贴现卖出自动冲销业务财务交易--------");
	}
	/**
	 * Method cancelCheckAbamentInfoAccountDetails.取消复核转贴现卖出自动冲销业务
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------开始取消复核转贴现卖出自动冲销业务财务交易--------");
		Vector vctAbmentDetailInfo = transInfo.getTransAbatementDetailInfo();
		TransAbatementDetailInfo transAbatementDetailInfo = null;
		long lSubAccountID = -1;
		if (vctAbmentDetailInfo != null && vctAbmentDetailInfo.size() > 0)
		{
			for (int i = 0; i < vctAbmentDetailInfo.size(); i++)
			{
				TransAccountDetailInfo tadi = null;
				transAbatementDetailInfo = (TransAbatementDetailInfo) vctAbmentDetailInfo.elementAt(i);
				if (transAbatementDetailInfo != null)
				{
					log.info("----开始取消记录交易账薄明细-----");
					//if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					//{
					//	log.info(" -----借方:" + i);
					//	tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 1);
					//	log.debug("-----------子账户id:" + transAbatementDetailInfo.getSubAccountID());
					//	log.info("---------------账户明细" + tadi);
					//	lSubAccountID = accountOperation.cancelGrantLoan(tadi);
					//}
					//else
					//	if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					//	{
					//		log.info(" -----贷方:" + i);
					tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 2);
					lSubAccountID = accountOperation.cancelRepayLoan(tadi);
					//}
				}
				log.info("=============开始删除账薄信息=======");
				accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
				log.info("=============开始删除会计账信息=======");
				glopOperation.deleteGLEntry(transInfo.getTransNo());
			}
		}
		log.info(" ------结束取消复核转贴现卖出自动冲销业务财务交易--------");
	}
	/**
	 * Method transferTransCompatibilityDetailInfoToTransAccountDetailInfo.
	 * 从兼容业务交易实体类中得到兼容交易财务处理实体类
	 * 
	 * @param info
	 * @param lTypeID
	 * @return TransAccountDetailInfo
	 */
	private TransAccountDetailInfo transferTransCompatibilityDetailInfoToTransAccountDetailInfo(
		TransCompatibilityInfo transMainInfo,
		TransCompatibilityDetailInfo info,
		long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (transMainInfo != null && info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setId(info.getId()); //OTO:待确定
			tadi.setAbstractStr(transMainInfo.getAbstract());
			tadi.setLoanNoteID(info.getDueBillID());
			tadi.setAmount(info.getAmount());
			tadi.setBankChequeNo(info.getBankCheckNo());
			tadi.setCurrencyID(transMainInfo.getCurrencyID());
			tadi.setDtExecute(transMainInfo.getExecuteDate());
			tadi.setDtInterestStart(transMainInfo.getInterestStartDate());
			tadi.setOfficeID(transMainInfo.getOfficeID());
			tadi.setTransNo(transMainInfo.getTransNo());
			tadi.setTransactionTypeID(transMainInfo.getTransactionTypeID());
			tadi.setStatusID(transMainInfo.getStatusID());
			tadi.setTransDirection(info.getTransDirectionID());
			tadi.setGroup(-1); //TOTO:待确定
			if (lTypeID == 1)
			{
				//付款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(info.getAccountID());
				tadi.setTransSubAccountID(info.getSubAccountID()); //活期子账户不用设
			}
			else
				if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getAccountID());
					tadi.setTransSubAccountID(info.getSubAccountID());
				}
		}
		return tadi;
	}
	/**
	 * Method receiveOrPay. 判断当前的兼容交易明细对应的账户号是收方还是付方
	 * 
	 * @param info
	 * @return long
	 */
	private long receiveOrPay(TransCompatibilityDetailInfo info, AccountInfo accountInfo) throws RemoteException, IRollbackException
	{
		long lReceiveOrPay = -1;
		long lAccountID = -1;
		long lAccountTypeID = -1; //账户类型id
		long lTransDirection = -1; //交易方向
		long lAccountTypeBalanceDirection = -1; //账户类型余额方向
		AccountOperation accountOperation = null;
		Sett_AccountTypeDAO sett_AccountTypeDAO = null;
		AccountTypeInfo accountTypeInfo = null;
		lAccountID = info.getAccountID();
		if (lAccountID > 0)
		{
			accountOperation = new AccountOperation();
			if (accountInfo == null)
			{
				throw new IRollbackException(ctx, "无法找到账户id" + lAccountID + "对应的主账户信息，交易失败");
			}
			else
			{
				lAccountTypeID = accountInfo.getAccountTypeID();
				sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				try
				{
					accountTypeInfo = sett_AccountTypeDAO.findByID(lAccountTypeID);
				}
				catch (SQLException e)
				{
					throw new IRollbackException(ctx, "查找账户类型id" + lAccountTypeID + "对应的账户类型信息产生错误，交易失败");
				}
				if (accountTypeInfo == null)
				{
					throw new IRollbackException(ctx, "无法找到账户类型id" + lAccountTypeID + "对应的账户类型信息，交易失败");
				}
				else
				{
					lAccountTypeBalanceDirection = accountTypeInfo.getBalanceDirection();
				}
				lTransDirection = info.getTransDirectionID();
				if (lAccountTypeBalanceDirection > 0 && lTransDirection > 0 && lAccountTypeBalanceDirection != lTransDirection)
				{
					lReceiveOrPay = SETTConstant.ReceiveOrPay.PAY;
				}
				else
					if (lAccountTypeBalanceDirection > 0 && lTransDirection > 0 && lAccountTypeBalanceDirection == lTransDirection)
					{
						lReceiveOrPay = SETTConstant.ReceiveOrPay.RECEIVE;
					}
			}
		}
		return lReceiveOrPay;
	}
	/**
	 * Method transferTransAbamentDetailInfoToTransAccountDetailInfo.
	 * 从转贴现卖出冲销业务交易实体类中得到转贴现卖出冲销交易财务处理实体类
	 * 
	 * @param transMainInfo
	 * @param info
	 * @param lTypeID
	 * @return TransAccountDetailInfo
	 */
	private TransAccountDetailInfo transferTransAbamentDetailInfoToTransAccountDetailInfo(TransAbatementInfo transMainInfo, TransAbatementDetailInfo info, long lTypeID)
	{
		TransAccountDetailInfo tadi = null;
		if (transMainInfo != null && info != null)
		{
			tadi = new TransAccountDetailInfo();
			tadi.setAbstractStr(transMainInfo.getAbstract());
			tadi.setLoanNoteID(info.getCredenceID());
			tadi.setAmount(info.getAmount());
			tadi.setCurrencyID(transMainInfo.getCurrencyID());
			tadi.setDtExecute(transMainInfo.getExecuteDate());
			tadi.setDtInterestStart(transMainInfo.getInterestStartDate());
			tadi.setOfficeID(transMainInfo.getOfficeID());
			tadi.setTransNo(transMainInfo.getTransNo());
			tadi.setTransactionTypeID(transMainInfo.getTransactionTypeID());
			tadi.setStatusID(transMainInfo.getStatusID());
			tadi.setTransDirection(info.getTransDirectionID());
			tadi.setDiscountBillID(info.getBillID());
			tadi.setGroup(-1); //TOTO:待确定
			if (lTypeID == 1)
			{
				//付款方账户操作时使用
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(info.getAccountID());
				tadi.setTransSubAccountID(info.getSubAccountID()); //活期子账户不用设
			}
			else
				if (lTypeID == 2)
				{
					//收款方账户操作时使用
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getAccountID());
					tadi.setTransSubAccountID(info.getSubAccountID());
				}
		}
		return tadi;
	}
	/**
	 * Method findGeneralLedgerSubjectCode.
	 * 
	 * @param generalLedgerID
	 * @return String
	 * @throws IRollbackException
	 */
	public String findGeneralLedgerSubjectCode(long generalLedgerID) throws IRollbackException
	{
		Sett_GeneralLedgerDAO glDAO = new Sett_GeneralLedgerDAO();
		String subjectCode;
		try
		{
			subjectCode = glDAO.findSubjectCodeByID(generalLedgerID);
		}
		catch (SQLException e)
		{
			throw new IRollbackException(ctx, e.getMessage(), e);
		}
		/*
		 * if (subjectCode == null || subjectCode.compareToIgnoreCase("") == 0)
		 * throw new IRollbackException(ctx, "无法查询到总账类ID" + generalLedgerID +
		 * "对应的总账类科目,交易失败");
		 */
		return subjectCode;
	}
	/**
	 * Method getSubjectBySubAccountID. 根据子账户去科目
	 * 
	 * @param lSubAccountID
	 * @param subjectType
	 * @return String
	 * @throws RemoteException
	 * @throws IRollbackException
	 * @throws IException
	 */
	public String getSubjectBySubAccountID(long lSubAccountID, int subjectType) throws RemoteException, IRollbackException, IException
	{
		return accountOperation.getSubjectBySubAccountID(lSubAccountID, subjectType);
	}
	/**
	 * Method getSubjectByBankID. 根据银行取科目
	 * 
	 * @param lBankID
	 * @return String
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public String getSubjectByBankID(long lBankID) throws RemoteException, IRollbackException
	{
		return accountOperation.getSubjectByBankID(lBankID);
	}
	/**
	 * Method getSubjectByCompatibilitySetting. 取兼容业务账户设置的科目
	 * 
	 * @param lSubAccountID
	 * @return String
	 * @throws SettlementException
	 */
	public String getSubjectByCompatibilitySetting(long lSubAccountID) throws SettlementException
	{
		String strSubjectCode = "";
		CompatibilitySubAccountSettingInfo compatibilitySubAccountSettingInfo = null;
		CompatibilitySubAccountSettingBean compatibilitySubAccountSettingBean = new CompatibilitySubAccountSettingBean();
		compatibilitySubAccountSettingInfo = compatibilitySubAccountSettingBean.findById(lSubAccountID);
		if (compatibilitySubAccountSettingInfo != null)
		{
			strSubjectCode = compatibilitySubAccountSettingInfo.getSubject();
		}
		return strSubjectCode;
	}
}