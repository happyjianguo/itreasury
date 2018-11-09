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
	 * �쳣˵����
	 */
	public void ejbActivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbCreate method comment
	 * 
	 * @exception javax.ejb.CreateException
	 * �쳣˵����
	 * @exception java.rmi.RemoteException
	 * �쳣˵����
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
			log.debug("---------�޷���ʹ��AccountBook���������ϵͳ������ʧ��-----------");
			throw new CreateException();
		}
	}
	/**
	 * ejbPassivate method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * �쳣˵����
	 */
	public void ejbPassivate() throws java.rmi.RemoteException
	{
	}
	/**
	 * ejbRemove method comment
	 * 
	 * @exception java.rmi.RemoteException
	 * �쳣˵����
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
	 * �쳣˵����
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) throws RemoteException
	{
		this.ctx = ctx;
	}
	/**
	 * Method saveCompatibilityAccountDetails. �������ҵ���˱���Ϣ
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveCompatibilityAccountDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------��ʼ������ݲ�����--------");
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
					//���������ί�и���ƾ֤���� ������ �����ʹ�õķ���
					if (detailInfo.getVoucher() != null && detailInfo.getVoucher().length() > 0 && detailInfo.getPassword() != null && detailInfo.getPassword().length() > 0)
					{
						log.print("����У��ί�и���ƾ֤�ţ�" + detailInfo.getVoucher() + "========================" + detailInfo.getPassword());
						AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
						long lVoucherReturn = atvDelegation.updateStatusByUse(detailInfo.getVoucher(), detailInfo.getPassword(), detailInfo.getAccountID(), transInfo.getTransNo());
						log.print("VoucherReturn=" + lVoucherReturn);
						if (lVoucherReturn <= 0)
						{
							log.print("----------ί�и���ƾ֤���治�ɹ�-----------");
							throw new IRollbackException(ctx, "Sett_E054", detailInfo.getVoucher());
						}
					}
					//����ý����и�����Ҹ��Ϊ�ڲ��˻�������á��ۼ�δ���˽��ķ���
					log.info("�˻�:" + detailInfo.getAccountID());
					if (detailInfo.getAccountID() > 0)
					{
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("�˻�:" + detailInfo.getAccountID() + "�Ǹ��");
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//�����˻�
									log.info("========�����˻�=========");
									accountOperation.addCurrentUncheckAmount(detailInfo.getAccountID(), -1, detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//�����˻�
									log.info("========�����˻�=========");
									accountOperation.addFixedUncheckAmount(detailInfo.getAccountID(), detailInfo.getDepositForm(), detailInfo.getAmount(),-1);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//�����˻�
									log.info("========�����˻�=========");
									accountOperation.addLoanUncheckAmount(detailInfo.getAccountID(), detailInfo.getDueBillID(), detailInfo.getAmount());
								}
							}
						}
						else
						{
							throw new IRollbackException(ctx, "�޷��˻���Ӧ�����˻���Ϣ������ʧ��");
						}
					}
				}
			}
		}
		log.info(" ------����������ݲ�����--------");
	}
	/**
	 * Method deleteTransCompatibility. ɾ������ҵ���˱���Ϣ
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
					//���������ί�и���ƾ֤���� ������ �����ʹ�õķ���
					if (detailInfo.getVoucher() != null && detailInfo.getVoucher().length() > 0 && detailInfo.getPassword() != null && detailInfo.getPassword().length() > 0)
					{
						log.print("ɾ��У��ί�и���ƾ֤�ţ�" + transInfo.getTransNo() + "========================");
						AccountTrustVoucherDelegation atvDelegation = new AccountTrustVoucherDelegation();
						long lVoucherReturn = atvDelegation.updateStatusByTransNo(transInfo.getTransNo());
						log.print("VoucherReturn=" + lVoucherReturn);
						if (lVoucherReturn <= 0)
						{
							log.print("----------ί�и���ƾ֤ɾ�����ɹ�-----------");
							throw new IRollbackException(ctx, "Sett_E054", detailInfo.getVoucher());
						}
					}
					//����ý����и�����Ҹ��Ϊ�ڲ��˻�������á��۳�δ���˽��ķ���
					log.info("�˻�:" + detailInfo.getAccountID());
					//�жϸ��˻��Ƿ��Ǹ����˻���ȡ�������㣺һ ���׷��� �� �˻�����
					if (detailInfo.getAccountID() > 0)
					{
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("�˻�:" + detailInfo.getAccountID() + "�Ǹ��");
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//�����˻�
									accountOperation.subtractCurrentUncheckAmount(detailInfo.getAccountID(), detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//�����˻�
									accountOperation.subtractFixedUncheckAmount(detailInfo.getAccountID(), detailInfo.getDepositForm(), detailInfo.getAmount());
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//�����˻�
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
	 * Method checkCompatibilityDetails. ���˼��ݽ���
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info("=============��ʼ���˼��ݽ���=======");
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		if (vctDetail != null && vctDetail.size() > 0)
		{
			log.info("=============���ݽ�����ϸ��Ϊ��=======");
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
					//1 ����ʽ���Դ���˻�
					if (detailInfo.getAccountID() > 0)
					{
						log.info("=============��ǰ���ݽ�����ϸ�ʽ���Դ���˻�=======��" + detailInfo.getAccountID());
						try
						{
							accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						log.info("=============��ѯ���˻���Ϣ֮��======");
						if (accountInfo != null)
						{
							log.info("=============���ڶ�Ӧ���˻���Ϣ=======");
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("=============��ǰ�˻�Ϊ���=======");
								//���Ϊ�����������δ���˽���
								tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 1);
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�������=======");
									lSubAccountID = accountOperation.withdrawCurrent(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�����=======");
									lSubAccountID = accountOperation.withdrawFix(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�������=======");
									lSubAccountID = accountOperation.repayLoan(tadi);
									//���Ʊ���Ƿ��Ѿ�����
									if (detailInfo.getContractID() > 0 && detailInfo.getDueBillID() > 0 && detailInfo.getBillID() > 0)
									{
										//�ж��Ƿ���ת������������ͺ�ͬ
										try
										{
											Loan_DAO loan_DAO = new Loan_DAO();
											//ά��Ʊ�ݳ���״̬
											log.print("----------�ж��Ƿ���ת������������ͺ�ͬ-----------");
											if (loan_DAO.IsReDiscountBuyBreakOutContract(detailInfo.getContractID()))
											{
												//��ȡƱ�ݵĳ���״̬,����Ѿ�������������쳣
												long lBillAbatementStatus = loan_DAO.getDiscountBillAbatementStatusIDByBillID(detailInfo.getBillID());
												log.print("----------Ʊ�ݳ���״̬-----------:" + lBillAbatementStatus);
												if (lBillAbatementStatus < 0)
												{
													throw new IRollbackException(ctx, "�޷����Ʊ��id��" + detailInfo.getBillID() + "��Ӧ��Ʊ����Ϣ������ʧ��");
												}
												else
													if (lBillAbatementStatus == SETTConstant.DiscountBillAbatementStatus.YES)
													{
														throw new IRollbackException(ctx, "Ʊ��id��" + detailInfo.getBillID() + "��Ӧ��Ʊ���Ѿ�����������ʧ��");
													}
												//�����޸�Ʊ��״̬Ϊ�Ѿ�����
												log.print("----------�޸�Ʊ��״̬Ϊ�Ѿ�����-----------");
												loan_DAO.changeIsabatementForBill(detailInfo.getBillID(), SETTConstant.DiscountBillAbatementStatus.YES);
											}
											//ά��Ʊ���Ƿ��������״̬
											log.print("----------�ж��Ƿ���ת������������ͺ�ͬ-----------");
											if (loan_DAO.IsReDiscountBuyBreakInContract(detailInfo.getContractID()))
											{//ת�������뵽���ջ�
												log.print("----------�޸�Ʊ��״̬Ϊ��������-----------");
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
									log.info("=============��ǰ�˻�Ϊ�տ=======");
									//���Ϊ�տ���˻��������
									tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 2);
									if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
											|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ����=======");
										lSubAccountID = accountOperation.depositCurrent(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
											||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ��=======");
										lSubAccountID = accountOperation.depositFix(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
											||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
											||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
											||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ����=======");
										lSubAccountID = accountOperation.grantLoan(tadi);
										//�������˻��������
										Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO();
										try
										{
											subAccountDao.UpdateOpenAmount(detailInfo.getSubAccountID(), detailInfo.getAmount());
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "�޸����˻�ID" + detailInfo.getSubAccountID() + "��Ӧ�����˻��������ʧ��");
										}
									}
								}
						}
						else
						{
							log.info("=============���˻���ϢΪ��=======");
							throw new IRollbackException(ctx, "�޷��ҵ��˻�id" + detailInfo.getAccountID() + "��Ӧ�����˻���Ϣ������ʧ��");
						}
						try
						{
							//ȡ�����Ŀ������
							//ȡ����ҵ���˻������еĿ�Ŀ-CPF��iTreasury��ͬ
							if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
							{
								log.info("=============ȡ����ҵ���˻������õĿ�Ŀ====");
								//getSubjectByCompatibilitySetting
								// �൱��ȡ���˻������õĿ�Ŀ��������������ȡ�ÿ�Ŀ
								strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
							}
							//��ȡ���˻������еĿ�Ŀ����ȡ�˻����������еĿ�Ŀ
							if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
							{
								log.info("=============ȡ�˻����������еĿ�Ŀ=======");
								//getSubjectBySubAccountID��ȡ��Ŀ�������ǣ�ȡ���˻���Ŀ��ȡ�˻����Ϳ�Ŀ
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
							log.info("=============��ǰ���ݽ�����ϸ�ʽ���Դ�ǿ�����=======");
							//2����ʽ���Դ�ǿ�����
							strSubjectCode = getSubjectByBankID(detailInfo.getBankID());
						}
						else
							if (detailInfo.getGLID() > 0)
							{
								log.info("=============��ǰ���ݽ�����ϸ�ʽ���Դ������=======");
								//3����ʽ���Դ������
								strSubjectCode = findGeneralLedgerSubjectCode(detailInfo.getGLID());
							}
					log.info("=============��ʼ���û������Ϣ=======");
					if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
					{
						log.info("=============��ĿΪ��=======");
						throw new IRollbackException(ctx, "�޷��ҵ���Ӧ��Ŀ������ʧ��");
					}
					log.info("=============��Ŀ:" + strSubjectCode);
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
					log.debug("======��Ʒ�¼����====");
					GLEntryInfo[] infos = { glEntryInfo };
					glopOperation.addGLEntries(infos);
				}
			}
		}
		log.info("=============�������˼��ݽ���=======");
	}
	/**
	 * Method cancelCheckCompatibilityDetails. ȡ�����˼��ݽ���
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckCompatibilityDetails(TransCompatibilityInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info("=============��ʼȡ�����˼��ݽ���=======");
		Vector vctDetail = null;
		TransCompatibilityDetailInfo detailInfo = null;
		vctDetail = transInfo.getTransCompatibilityDetailInfo();
		AccountInfo accountInfo = null;
		long lAccountTypeID = -1;
		long lAccountGroupID = -1;
		if (vctDetail != null && vctDetail.size() > 0)
		{
			log.info("=============���ݽ�����ϸ��Ϊ��=======");
			for (int i = 0; i < vctDetail.size(); i++)
			{
				detailInfo = (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
				if (detailInfo != null)
				{
					TransAccountDetailInfo tadi = null;
					GLEntryInfo glEntryInfo = new GLEntryInfo();
					String strSubjectCode = "";
					//1 ����ʽ���Դ���˻�
					if (detailInfo.getAccountID() > 0)
					{
						log.info("=============��ǰ���ݽ�����ϸ�ʽ���Դ���˻�=======");
						accountInfo = accountOperation.findAccountByID(detailInfo.getAccountID());
						if (accountInfo != null)
						{
							lAccountTypeID = accountInfo.getAccountTypeID();
							lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
							if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.PAY)
							{
								log.info("=============��ǰ�˻�Ϊ���=======");
								//���Ϊ�����������δ���˽���
								tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 1);
								if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
										|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT
)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�������=======");
									accountOperation.cancelWithdrawCurrent(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
										||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�����=======");
									accountOperation.cancelWithdrawFix(tadi);
								}
								if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
										||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
										||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
										||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
								{
									//�����˻�
									log.info("=============��ǰ�˻�Ϊ�������=======");
									accountOperation.cancelRepayLoan(tadi);
									//�ж��Ƿ���ת������������ͺ�ͬ
									try
									{
										if (detailInfo.getContractID() > 0 && detailInfo.getDueBillID() > 0 && detailInfo.getBillID() > 0)
										{
											Loan_DAO loan_DAO = new Loan_DAO();
											log.print("----------�ж��Ƿ���ת������������ͺ�ͬ-----------");
											if (loan_DAO.IsReDiscountBuyBreakOutContract(detailInfo.getContractID()))
											{
												//��ȡƱ�ݵ�״̬,���δ������������쳣
												long lBillAbatementStatus = loan_DAO.getDiscountBillAbatementStatusIDByBillID(detailInfo.getBillID());
												log.print("----------Ʊ�ݳ���״̬-----------:" + lBillAbatementStatus);
												if (lBillAbatementStatus < 0)
												{
													throw new IRollbackException(ctx, "�޷����Ʊ��id��" + detailInfo.getBillID() + "��Ӧ��Ʊ����Ϣ������ʧ��");
												}
												else
													if (lBillAbatementStatus == SETTConstant.DiscountBillAbatementStatus.NO)
													{
														throw new IRollbackException(ctx, "Ʊ��id��" + detailInfo.getBillID() + "��Ӧ��Ʊ��δ����������ʧ��");
													}
												//�����޸�Ʊ��״̬Ϊδ����
												log.print("----------�޸�Ʊ��״̬Ϊδ����-----------");
												loan_DAO.changeIsabatementForBill(detailInfo.getBillID(), SETTConstant.DiscountBillAbatementStatus.NO);
											}
											
											//ά��Ʊ���Ƿ��������״̬
											log.print("----------�ж��Ƿ���ת������������ͺ�ͬ-----------");
											if (loan_DAO.IsReDiscountBuyBreakInContract(detailInfo.getContractID()))
											{//ת�������뵽���ջ�
												log.print("----------�޸�Ʊ��״̬Ϊ������-----------");
												loan_DAO.updateDiscountBillSellStatusID(detailInfo.getBillID(),Constant.YesOrNo.YES);
											}
										}
									}
									catch (ITreasuryDAOException e)
									{
										throw new IRollbackException(ctx, "�жϺ�ͬ���Ͳ�������", e);
									}
								}
							}
							else
								if (receiveOrPay(detailInfo, accountInfo) == SETTConstant.ReceiveOrPay.RECEIVE)
								{
									log.info("=============��ǰ�˻�Ϊ�տ=======");
									//���Ϊ�տ���˻��������
									tadi = this.transferTransCompatibilityDetailInfoToTransAccountDetailInfo(transInfo, detailInfo, 2);
									log.info("�������ſ�֪ͨ��id��" + detailInfo.getDueBillID());
									if (lAccountGroupID == SETTConstant.AccountGroupType.CURRENT
											|| lAccountGroupID == SETTConstant.AccountGroupType.OTHERDEPOSIT
)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ����=======");
										accountOperation.cancelDepositCurrent(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.FIXED
											||lAccountGroupID == SETTConstant.AccountGroupType.NOTIFY
)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ��=======");
										accountOperation.cancelDepositFix(tadi);
									}
									if (lAccountGroupID == SETTConstant.AccountGroupType.TRUST
											||lAccountGroupID == SETTConstant.AccountGroupType.CONSIGN
											||lAccountGroupID == SETTConstant.AccountGroupType.DISCOUNT
											||lAccountGroupID == SETTConstant.AccountGroupType.OTHERLOAN)
									{
										//�����˻�
										log.info("=============��ǰ�˻�Ϊ�տ����=======");
										accountOperation.cancelGrantLoan(tadi);
										//���⴦��----�޸����˻���״̬
										Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO();
										try
										{
											log.info("=============��ʼ�޸�״̬=======");
											subAccountDao.updateFinishDateAndStatus(detailInfo.getSubAccountID(), SETTConstant.AccountStatus.NORMAL, null);
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "�޸����˻�ID" + detailInfo.getSubAccountID() + "��Ӧ�����˻�״̬��Ϣʧ��");
										}
										//���⴦��----�޸����˻���״̬
										//�������˻��Ŀ������
										try
										{
											subAccountDao.UpdateOpenAmount(detailInfo.getSubAccountID(), -detailInfo.getAmount());
										}
										catch (SQLException e)
										{
											throw new IRollbackException(ctx, "�޸����˻�ID" + detailInfo.getSubAccountID() + "��Ӧ�����˻��������ʧ��");
										}
									}
								}
						}
						else
						{
							log.info("=============���˻���ϢΪ��=======");
							throw new IRollbackException(ctx, "�޷��ҵ��˻�id" + detailInfo.getAccountID() + "��Ӧ�����˻���Ϣ������ʧ��");
						}
					}
					log.info("=============��ʼɾ���˱���Ϣ=======");
					accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
					log.info("=============��ʼɾ���������Ϣ=======");
					glopOperation.deleteGLEntry(transInfo.getTransNo());
				}
			}
		}
		log.info("=============����ȡ�����˼��ݽ���=======");
	}
	/**
	 * Method saveAbamentInfoAccountDetails.����ת���������Զ�����ҵ���˱���Ϣ
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void saveAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------��ʼ����ת���������Զ�����ҵ�������--------");
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
		log.info(" ------��������ת���������Զ�����ҵ�������--------");
	}
	/**
	 * Method deleteTransAbament. ɾ��ת���������Զ������˱���Ϣ
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void deleteTransAbament(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------��ʼɾ��ת���������Զ�����ҵ�������--------");
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
		log.info(" ------����ɾ��ת���������Զ�����ҵ�������--------");
	}
	/**
	 * Method checkAbamentInfoAccountDetails.����ת���������Զ�����ҵ��
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void checkAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------��ʼ����ת���������Զ�����ҵ�������--------");
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
					log.info("----��ʼ��¼�����˱���ϸ-----");
					//if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					//{
					//	log.info(" -----�跽:" + i);
					//	tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 1);
					//	lSubAccountID = accountOperation.grantLoan(tadi);
					//}
					//else
					//	if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					//	{
					tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 2);
					log.info(" -----�˻���ϸ��Ϣ----:");
					Log.print(tadi);
					lSubAccountID = accountOperation.repayLoan(tadi);
					//}
					log.info("----��ʼȡ��Ŀ-----");
					if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					{
						//����跽
						log.info("----ȡ�跽��Ŀ----");
						//ȡ�����Ŀ������
						//ȡ�������������õĶ�Ӧ��Ŀ
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----ȡת�������������ջس���ҵ�������õĽ跽��Ŀ----");
							strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getGLID());
						}
						//���ȡ�˻������еĿ�Ŀ
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----ȡ����ҵ���˻����������õĿ�Ŀ-----");
							try
							{
								strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
							}
							catch (SettlementException e)
							{
								throw new IRollbackException(ctx, e.getMessage(), e);
							}
						}
						//ȡ���˻����˻����������еĿ�Ŀ
						if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
						{
							log.info("----ȡ���˻����˻����������еĿ�Ŀ-----");
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
							//�������
							log.info("----ȡ������Ŀ----");
							//ȡ�����Ŀ������
							//ȡ�������������õĶ�Ӧ��Ŀ,��Ҫ���������ֺ�ͬ����ת���ֺ�ͬ
							if (transAbatementDetailInfo.getContractTypeID() == LOANConstant.LoanType.ZTX)
							{
								log.info("----ת���ֺ�ͬ----");
								//ת���ֺ�ͬ...
								if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
								{
									log.info("----ȡת�������������ջس���ҵ�������õĴ���ת���ֿ�Ŀ----");
									strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getReDiscountGLID());
								}
							}
							else
								if (transAbatementDetailInfo.getContractTypeID() == LOANConstant.LoanType.TX)
								{
									log.info("----���ֺ�ͬ----");
									log.info("----ȡת�������������ջس���ҵ�������õĴ������ֿ�Ŀ----");
									//���ֺ�ͬ...
									if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
									{
										strSubjectCode = findGeneralLedgerSubjectCode(transInfo.getDiscountGLID());
									}
								}
							//���ȡ�˻������еĿ�Ŀ
							if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
							{
								log.info("----ȡ����ҵ���˻����������õĿ�Ŀ-----");
								try
								{
									strSubjectCode = getSubjectByCompatibilitySetting(lSubAccountID);
								}
								catch (SettlementException e)
								{
									throw new IRollbackException(ctx, e.getMessage(), e);
								}
							}
							//ȡ���˻����˻����������еĿ�Ŀ
							if (strSubjectCode != null && strSubjectCode.trim().length() <= 0)
							{
								log.info("----ȡ���˻����˻����������еĿ�Ŀ-----");
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
					log.info("---------ȡ��Ŀ������" + strSubjectCode);
					if (strSubjectCode == null || strSubjectCode.trim().length() <= 0)
					{
						log.info("=============��ĿΪ��=======");
						throw new IRollbackException(ctx, "�޷��ҵ���Ӧ��Ŀ������ʧ��");
					}
					log.info("---------��ʼ���û������Ϣ------");
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
					log.debug("---------��ʼ��¼�����-------");
					GLEntryInfo[] infos = { glEntryInfo };
					glopOperation.addGLEntries(infos);
				}
			}
		}
		log.info(" ------��������ת���������Զ�����ҵ�������--------");
	}
	/**
	 * Method cancelCheckAbamentInfoAccountDetails.ȡ������ת���������Զ�����ҵ��
	 * 
	 * @param transInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public void cancelCheckAbamentInfoAccountDetails(TransAbatementInfo transInfo) throws RemoteException, IRollbackException
	{
		log.info(" ------��ʼȡ������ת���������Զ�����ҵ�������--------");
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
					log.info("----��ʼȡ����¼�����˱���ϸ-----");
					//if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					//{
					//	log.info(" -----�跽:" + i);
					//	tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 1);
					//	log.debug("-----------���˻�id:" + transAbatementDetailInfo.getSubAccountID());
					//	log.info("---------------�˻���ϸ" + tadi);
					//	lSubAccountID = accountOperation.cancelGrantLoan(tadi);
					//}
					//else
					//	if (transAbatementDetailInfo.getTransDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					//	{
					//		log.info(" -----����:" + i);
					tadi = transferTransAbamentDetailInfoToTransAccountDetailInfo(transInfo, transAbatementDetailInfo, 2);
					lSubAccountID = accountOperation.cancelRepayLoan(tadi);
					//}
				}
				log.info("=============��ʼɾ���˱���Ϣ=======");
				accountOperation.deleteTransAccountDetail(transInfo.getTransNo());
				log.info("=============��ʼɾ���������Ϣ=======");
				glopOperation.deleteGLEntry(transInfo.getTransNo());
			}
		}
		log.info(" ------����ȡ������ת���������Զ�����ҵ�������--------");
	}
	/**
	 * Method transferTransCompatibilityDetailInfoToTransAccountDetailInfo.
	 * �Ӽ���ҵ����ʵ�����еõ����ݽ��ײ�����ʵ����
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
			tadi.setId(info.getId()); //OTO:��ȷ��
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
			tadi.setGroup(-1); //TOTO:��ȷ��
			if (lTypeID == 1)
			{
				//����˻�����ʱʹ��
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(info.getAccountID());
				tadi.setTransSubAccountID(info.getSubAccountID()); //�������˻�������
			}
			else
				if (lTypeID == 2)
				{
					//�տ�˻�����ʱʹ��
					tadi.setTransDirection(SETTConstant.ReceiveOrPay.RECEIVE);
					tadi.setTransAccountID(info.getAccountID());
					tadi.setTransSubAccountID(info.getSubAccountID());
				}
		}
		return tadi;
	}
	/**
	 * Method receiveOrPay. �жϵ�ǰ�ļ��ݽ�����ϸ��Ӧ���˻������շ����Ǹ���
	 * 
	 * @param info
	 * @return long
	 */
	private long receiveOrPay(TransCompatibilityDetailInfo info, AccountInfo accountInfo) throws RemoteException, IRollbackException
	{
		long lReceiveOrPay = -1;
		long lAccountID = -1;
		long lAccountTypeID = -1; //�˻�����id
		long lTransDirection = -1; //���׷���
		long lAccountTypeBalanceDirection = -1; //�˻���������
		AccountOperation accountOperation = null;
		Sett_AccountTypeDAO sett_AccountTypeDAO = null;
		AccountTypeInfo accountTypeInfo = null;
		lAccountID = info.getAccountID();
		if (lAccountID > 0)
		{
			accountOperation = new AccountOperation();
			if (accountInfo == null)
			{
				throw new IRollbackException(ctx, "�޷��ҵ��˻�id" + lAccountID + "��Ӧ�����˻���Ϣ������ʧ��");
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
					throw new IRollbackException(ctx, "�����˻�����id" + lAccountTypeID + "��Ӧ���˻�������Ϣ�������󣬽���ʧ��");
				}
				if (accountTypeInfo == null)
				{
					throw new IRollbackException(ctx, "�޷��ҵ��˻�����id" + lAccountTypeID + "��Ӧ���˻�������Ϣ������ʧ��");
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
	 * ��ת������������ҵ����ʵ�����еõ�ת���������������ײ�����ʵ����
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
			tadi.setGroup(-1); //TOTO:��ȷ��
			if (lTypeID == 1)
			{
				//����˻�����ʱʹ��
				tadi.setTransDirection(SETTConstant.ReceiveOrPay.PAY);
				tadi.setTransAccountID(info.getAccountID());
				tadi.setTransSubAccountID(info.getSubAccountID()); //�������˻�������
			}
			else
				if (lTypeID == 2)
				{
					//�տ�˻�����ʱʹ��
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
		 * throw new IRollbackException(ctx, "�޷���ѯ��������ID" + generalLedgerID +
		 * "��Ӧ���������Ŀ,����ʧ��");
		 */
		return subjectCode;
	}
	/**
	 * Method getSubjectBySubAccountID. �������˻�ȥ��Ŀ
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
	 * Method getSubjectByBankID. ��������ȡ��Ŀ
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
	 * Method getSubjectByCompatibilitySetting. ȡ����ҵ���˻����õĿ�Ŀ
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