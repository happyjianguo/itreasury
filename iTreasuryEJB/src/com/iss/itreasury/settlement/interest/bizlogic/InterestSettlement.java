/*
 * Created on 2003-10-28
 * 
 * InterestSettlement.java
 */

package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.bankinstruction.BankInstructionFactory;
import com.iss.itreasury.settlement.bankinstruction.IBankInstruction;
import com.iss.itreasury.settlement.bankinstruction.entity.CreateInstructionParam;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.interest.dao.Sett_CompoundInterestSettingDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_InterestFeeSettingDetailDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.BankInterestAdjustInfo;
import com.iss.itreasury.settlement.interest.dataentity.ClearLoanAccountInterestConditionInfo;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestAccountIDInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.transloan.bizlogic.BankLoanQuery;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * @author Allan Liu
 * 
 * ��Ϣ���ý����Ϣ����ҵ�������Ҫʵ�ֵĹ��ܰ�����
 * 
 * 1.��ѯ��Ϣ��¼�� 2.����������Ϣ�� 3.��������������Ϣ�� 4.����������Ϣ��
 * 
 * ע������:Ϊ��ʹ�����ݷ��ʶ��������������ͷ������������ṩ֧�֣�
 * 
 */
public class InterestSettlement
{

	/**
	 * ����Ǹ���ķ�������ά������Ӧ�ô�����ȡ�����ݿ����ӻ���ֱ��ͨ��JDBC���ʣ�ȱʡΪ������ȡ�á�
	 */
	private boolean	bConnectionFromContainer	= true;

	private Log4j	log							= new Log4j(Constant.ModuleType.SETTLEMENT, this);

	/**
	 * ��������ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 * 
	 * @return ���ݿ����ӡ�
	 */
	private Connection getConnection() throws Exception
	{

		Connection con = null;
		if (bConnectionFromContainer)
		{
			con = Database.getConnection();
		}
		else
		{
			con = Database.get_jdbc_connection();
		}
		return con;
	}

	/**
	 * �������ݿ����ӵ���Դ��
	 * 
	 * @param bConnectionFromContainer true - ������ȡ�����ݿ����ӡ� false - ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 */
	public void setConnectionFromContainer(boolean bConnectionFromContainer)
	{

		this.bConnectionFromContainer = bConnectionFromContainer;
	}

	/**
	 * ȡ�����ݿ����ӵ���Դ��
	 * 
	 * @return ���ݿ����ӵ���Դ�� true - ������ȡ�����ݿ����ӡ� false - ֱ��ͨ��JDBCȡ�����ݿ����ӡ�
	 */
	public boolean getConnectionFromContainer()
	{

		return bConnectionFromContainer;
	}

	/**
	 * �����������н�Ϣ��¼�Ĳ�ѯ��Ϊ������Ϣ������������Ϣ�ͽ��㹦���ṩ֧�֡�
	 * 
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ����� ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector findSettlementRecords(Connection con, InterestQueryInfo queryInfo) throws Exception
	{

		Vector resultVec = new Vector(); // ����ֵ

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
		}
		try
		{
			// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			// �����÷�������������һ����ɲ��֡�

			// ������Ϣ���ý����Ϣ�������ݷ��ʶ���ʵ��
			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			// �ж��Ƿ�������
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
			// �ж��Ƿ����ʵ���
			Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);
			// ��ѯ��Ϣ��¼��
			// �ݲ������ҳ����ע��
			/*
			 * if(queryInfo.needFreshQuiry()){ long lTotalRecord = dao.getRecordCount(conInternal, queryInfo);
			 * queryInfo.initPageInfo(lTotalRecord); }
			 */

			Vector rstVec = new Vector();
			rstVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.INTEREST);
			if (rstVec.size() == 0)
			{
				log.info("rstVec.size()���㣡��������������");
				return resultVec;
			}
			log.info("-------��ʼ�����ѯ����--------");
			InterestOperation io = new InterestOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			for (int i = 0; i < rstVec.size(); i++)
			{
				log.info("lhj debug info ===����ѭ����Ϣ=======");
				InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
				resultInfo = (InterestQueryResultInfo) rstVec.elementAt(i);
		        log.debug("---------�ж��˻�����------------");
				long accountTypeID = resultInfo.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        try {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (accountTypeInfo != null) {
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || 
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
					{
						log.info("lhj debug info ===���붨����Ϣ=======");
						FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
						if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							log.info("lhj debug info ===���붨�ڼ�����Ϣ=======");
							// ִ�ж��ڼ���ļ��㣬�����µļ�¼����
							SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
							subInterestInfo = io.getFixedAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getAccountTypeID(), queryInfo.getClearInterest());
							fixedInterestInfo.setBalance(subInterestInfo.getBalance());
							fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							// lhj
							// modify===���漸�����Ժ���û�б�Ҫ��ʾ����------2003-11-26-----------------------
							fixedInterestInfo.setDays(subInterestInfo.getDays());
							fixedInterestInfo.setEDate(subInterestInfo.getEDate());
							fixedInterestInfo.setRate(subInterestInfo.getRate());
							fixedInterestInfo.setSDate(subInterestInfo.getSDate());
							// lhj
							// modify==---------------------------------------2003-11-26-------------
							InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
							// ������Ϣ
							newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							resultVec.addElement(newResultInfo);
							log.info("lhj debug info ===�������ڼ�����Ϣ=======");
						}
						else if (queryInfo.isBInterest())
						{
							log.info("--------------��ʼ������Ϣ------------");
							fixedInterestInfo = io.getFixedDepositAccountPayableInterest(resultInfo.getAccountID(),
									resultInfo.getSubAccountID(), queryInfo.getClearInterest());
							log.info("--------------����������Ϣ------------");
							InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							newResultInfo.setDrawingResult(true);
							resultVec.addElement(newResultInfo);
						}
						log.info("lhj debug info ===����������Ϣ=======");
					}
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||//������
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||//׼����
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||//���
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT) // ����
					{
						// ��Ϣ
						if (queryInfo.isInterest())
						{
							log.info("lhj debug info ===���������Ϣ=======");
							CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
	
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ������Ϣ����---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							log.info("-------------�ж��Ƿ���Ҫ�����������---------");
							log.info("-------------��Ϣ��ʼ---------");
							log.info("lhj debug info == ����getCurrentDepositAccountInterest.......");
							currInterestInfo = io.getCurrentDepositAccountInterest(queryInfo.getOfficeID(), queryInfo
									.getCurrencyID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(), Env
									.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()));
	
							resultInfo.setDays(currInterestInfo.getIntervalDays());
							resultInfo.setEndDate(currInterestInfo.getEDate());
							resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNegotiationBalance(), currInterestInfo.getAverageNormalBalance()));
							resultInfo.setInterest(currInterestInfo.getNormalInterest());
							resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
							resultInfo.setStartDate(currInterestInfo.getSDate());
	
							// //Э����Ϣ
							resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
							resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
							resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
							// ��Ϣ����
							resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// ��Ϣ��
							resultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("lhj debug info ==������ " + currInterestInfo.getIntervalDays());
							log.info("lhj debug info ==��ʼ���ڣ� " + currInterestInfo.getSDate());
							log.info("lhj debug info ==�������ڣ� " + currInterestInfo.getEDate());
							log.info("lhj debug info ==������ " + currInterestInfo.getNormalBalance());
							log.info("lhj debug info ==������Ϣ�� " + currInterestInfo.getNormalInterest());
							log.info("lhj debug info ==���ʣ� " + currInterestInfo.getNormalInterestRate());
							log.info("lhj debug info ==Э����Ϣ�� " + currInterestInfo.getNegotiationInterest());
							log.info("lhj debug info ==Э���� " + currInterestInfo.getNegotiationBalance());
							log.info("lhj debug info ==Э�����ʣ� " + currInterestInfo.getNegotiationInterestRate());
							log.info("lhj debug info ==�������ͣ� " + SETTConstant.InterestFeeType.INTEREST);
							log.info("lhj debug info ==��Ϣ���ڣ� " + queryInfo.getClearInterest());
							log.info("-------------��Ϣ����---------");
	
							// �õ���Ϣ�˻����븶Ϣ�˻���
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��ſ�ʼ---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getInterestType());
							resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��Ž���---------");
							resultVec.addElement(resultInfo);
							log.info("lhj debug info ===����������Ϣ=======");
						}
	
					}
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) // ����
					{
						log.info("lhj debug info ===���������Ϣ=======");
						LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
	
						if (queryInfo.isInterest())
						{
							log.info("��interestSettlement 346��lhj debug settlement info >>>>>>>>>>>�����˻���Ҫ��Ϣ! ");
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ��������---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							log.info("-------------�ж��Ƿ���Ҫ�����������---------");
							
							//add by bingliu 20120307 �����������
							/*��Ӫ���ί�д����ͬ�����ʵ���������ʷ��������Ϣ���ػ����ٴν�Ϣ��ϵͳ�������Ϣû�м�ȥ�ϴ��Ѿ��������Ϣ��
							 *��Ϣ֮ǰû�е�����Ϣ���ᵼ���������⣺1.�����Ϣ���ԣ�2.�ػ�ʱ�ᵹ����Ϣ������֮�����Ϣ������ȷ��û�м�ȥ�ϴν����Ϣ��
							 *����취���ڽ�Ϣ֮ǰ�ļ��㷽���м������ʵ��㷽����
							 */
							log.info("-------------�ж��Ƿ��е������ʵ���---------");
							Collection coll2 = null;
							coll2 = subaccDAO.findAccountLoanBankInterestAdjust(queryInfo.getOfficeID(), queryInfo.getCurrencyID(), 
									queryInfo.getClearInterest(),resultInfo.getSubAccountID());
							Iterator itResult2 = null;
							if (coll2 != null && coll2.size() > 0)
							{
								itResult2 = coll2.iterator();
								if (itResult2 != null && itResult2.hasNext())
								{
									while (itResult2.hasNext())
									{
										BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
										log.info("-------------��ʼ�������ʵ���---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), backinfo.getBackDate(), 0, 
												queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										long lUpdateReturn = subaccDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
										   log.debug("�������ʵ�����ϸ��Ϣ���ı�־lUpdateReturn = "+lUpdateReturn);
										if (flag < 0||lUpdateReturn < 0)
										{
											throw new IException("�������ʵ���ʧ��");
										}
										log.info("-------------�������ʵ������---------");
									}
								}
							}
							log.info("-------------�ж��Ƿ��е������ʵ������---------");
							
	
							// ��Ϣ
							log.info("-------------��Ϣ��ʼ---------");
							loanInterestInfo = io.GetLoanAccountInterest(queryInfo.getOfficeID(),
									queryInfo.getCurrencyID(), resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getClearInterest(), Env.getSystemDate(conInternal, queryInfo.getOfficeID(),
											queryInfo.getCurrencyID()));
							log.info("----------��ӡ������Ϣ-------------");
							log.debug(UtilOperation.dataentityToString(loanInterestInfo));
							log.info("-------------��Ϣ����---------");
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ��Ϣ
							newResultInfo.setInterest(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							newResultInfo.setDays(loanInterestInfo.getDays());
							// ���Լ���
							newResultInfo.setDrawingResult(true);
							log.info("��interestSettlement 415��lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID�ǣ� "
											+ newResultInfo.getAccountTypeID());
					        log.debug("---------�ж��˻�����------------");
					        AccountTypeInfo newAccountTypeInfo = null;
					        try {
					        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
							} catch (SQLException e) {
								e.printStackTrace();
							}
							if (newAccountTypeInfo != null) {
								// ί�д����˻�
								if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
								{
									InterestTaxInfo taxInfo = new InterestTaxInfo();
		
									/**
									 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(),
									 * newResultInfo.getSubAccountID(), newResultInfo.getInterest());
									 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
									 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
									 */
		
									// ��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
									taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(), newResultInfo
											.getSubAccountID(), newResultInfo.getInterest());
									newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
									newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
									newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
		
								}
		
								// �õ���Ϣ�˻����븶Ϣ�˻���
								InterestAccountIDInfo interestAccountIDInfo = null;
								interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), newResultInfo.getInterestType());
								newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
								newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
								resultVec.addElement(newResultInfo);
							}
						}
						if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							log.info("��interestSettlement 445��lhj debug settlement info >>>>>>>>>>>�����˻�������Ϣ-------");
	
							// ִ�д��������Ϣ�ļ��㣬�����µļ�¼����
							SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
							subInterestInfo = io.getLoanAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), resultInfo.getAccountTypeID(), queryInfo.getClearInterest());
							loanInterestInfo.setBalance(subInterestInfo.getBalance());
							loanInterestInfo.setDays(subInterestInfo.getDays());
							loanInterestInfo.setEDate(subInterestInfo.getEDate());
							loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
							loanInterestInfo.setRate(subInterestInfo.getRate());
							loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ������Ϣ
							newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							resultVec.addElement(newResultInfo);
						}
						else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
						{
							log.info("��interestSettlement 475��lhj debug settlement info >>>>>>>>>>>�����˻�������-------");
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ��������---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.COMMISION);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ������
							newResultInfo.setHandlingCharge(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
	
						}
						else if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
						{
							log.info("��interestSettlement 551��lhj debug settlement info >>>>>>>>�����˻�������-------");
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ������Ϣ����---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.ASSURE);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ������
							newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
							// ��Ϣ˰��
							/*
							 * InterestTaxInfo taxInfo = new InterestTaxInfo(); log.info("��interestSettlement 611��������Ϣ˰��");
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getAssuranceCharge());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 */
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
	
						}
						if (queryInfo.isCompoundInterest())
						{
							log.info("��interestSettlement 635��lhj debug info>>>>>�����.....");
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ������Ϣ����---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							// ����
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ����
							newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMPOUNDINTEREST);
							// ��Ϣ˰��
							InterestTaxInfo taxInfo = new InterestTaxInfo();
	
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getCompoundInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
							// ��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(),
									newResultInfo.getSubAccountID(), newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
	
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
						}
						if (queryInfo.isForfeitInterest())
						{
							log.info("��interestSettlement 721��lhj debug info>>>>>���Ϣ.....");
	
							Collection coll = null;
							log.info("-------------�ж��Ƿ���Ҫ��������---------");
							coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
									queryInfo.getCurrencyID(), queryInfo.getOfficeID(), queryInfo.getClearInterest());
							Iterator itResult = null;
							if (coll != null && coll.size() > 0)
							{
								itResult = coll.iterator();
								if (itResult != null && itResult.hasNext())
								{
									while (itResult.hasNext())
									{
										TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
										log.info("-------------��ʼ������Ϣ����---------");
										long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
												resultInfo.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo
														.getAmount(), queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
												Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo
														.getCurrencyID()), SETTConstant.BooleanValue.ISFALSE);
										if (flag < 0)
										{
											throw new IException("��������ʧ��");
										}
										log.info("-------------�����������---------");
									}
								}
							}
							// ��Ϣ
							loanInterestInfo = io.getLoanAccountFee(queryInfo.getOfficeID(), queryInfo.getCurrencyID(),
									resultInfo.getAccountID(), resultInfo.getSubAccountID(), queryInfo.getClearInterest(),
									Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
									SETTConstant.InterestFeeType.FORFEITINTEREST);
							InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
							// ���ڷ�Ϣ
							newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
							// ��Ϣ����
							newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);
							// ��Ϣ˰��
							InterestTaxInfo taxInfo = new InterestTaxInfo();
	
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getForfeitInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
	
							// ��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(),
									newResultInfo.getSubAccountID(), newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
	
							// ��Ϣ��
							newResultInfo.setCreateDate(queryInfo.getClearInterest());
							log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
							InterestAccountIDInfo interestAccountIDInfo = null;
							interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo
									.getSubAccountID(), newResultInfo.getInterestType());
							newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
							newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
							resultVec.addElement(newResultInfo);
						}
						log.info("lhj debug info ===����������Ϣ=======");
					}
				}
			}
			log.info("-------�����ѯ���ݽ���--------");
			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new IException("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new IException("��������쳣");
				}
			}
			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return resultVec;
	}

	/**
	 * �����������н�Ϣ��¼�Ĳ�ѯ��Ϊ������Ϣ������������Ϣ�ͽ��㹦���ṩ֧�֡�
	 * 
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ����� ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param queryInfo ������ѯ������ʵ�壬never null.
	 * @return InterestQueryResultInfo[] ���������Ľ�Ϣ��¼������Ϣ���飬������Ϣ����Ϣ ��Ҫ���ü�Ϣ�ӿڼ���ó������û�з��������Ľ�Ϣ��¼����ô���� null.
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector selectSettlementRecords(Connection con, InterestQueryInfo queryInfo) throws Exception
	{

		Vector resultVec = new Vector(); // ����ֵ

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
		}

		try
		{
			// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			// �����÷�������������һ����ɲ��֡�
			// ������Ϣ���ý����Ϣ�������ݷ��ʶ���ʵ��

			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			// �ж��Ƿ�������
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
			
			// �ж��Ƿ����ʵ���
			Sett_SubAccountDAO subaccDAO = new Sett_SubAccountDAO(conInternal);

			InterestOperation io = new InterestOperation(conInternal);

			InterestBatch ib = new InterestBatch(conInternal);

			// ��ѯ��Ϣ��¼��
			Vector settlmentVec = null;
			Vector assuerVec = null;
			Vector commissionVec = null;
			Vector compoundVec = null;
			Vector forfeitVec = null;
			Vector preDrawVec = null;

			/**
			 * ���� info.setInterest(Interest); --��Ϣ true
			 */

			if (queryInfo.isBInterest()) // �Ƿ������Ϣ
			{
				settlmentVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.INTEREST);

				if (settlmentVec != null && settlmentVec.size() > 0)
				{
					log.info("-------��ʼ������Ϣ��ѯ����--------");

					for (int i = 0; i < settlmentVec.size(); i++)
					{

						log.info("lhj debug info ===����ѭ����Ϣ=======");

						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) settlmentVec.elementAt(i);

				        log.debug("---------�ж��˻�����------------");
						long accountTypeID = resultInfo.getAccountTypeID();
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        
				        try 
				        {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} 
				        catch (SQLException e) 	
				        {
							e.printStackTrace();
						}
				        
						if (accountTypeInfo != null) 
						{
							/**
							 * ����������ʻ������������ �������� info.getInterestRate() ����ʱ�� resultInfo.getEndDate()
							 */
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								resultInfo = this.getDiscountInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, resultInfo.getInterestRate() // ��ͬ�������
										, resultInfo.getEndDate()
										, resultInfo.getContractID()
										, resultInfo.getDiscreID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										, queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal , queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO );
							}
							// �Ƿ�����˻�������
							// �Ƿ�֤���˻�������
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
								   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN )
							{
								resultInfo = this.getInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										,  queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO );
							}
							else
							{
								//add by zcwang 2007-6-23
								long payFormID=-1;
								payFormID=resultInfo.getPayFormID();

								resultInfo = this.getInterestInfo( resultInfo.getFixedDepositNo()
										, resultInfo.getLoanPreDrawInterest()
										, resultInfo.getContractNo()
										, resultInfo.getPayFormNo()
										, resultInfo.getSubAccountID()
										, resultInfo.getAccountID()
										, resultInfo.getAccountTypeID()
										, queryInfo.getOfficeID()
										, queryInfo.getCurrencyID()
										, queryInfo.getClearInterest()
										, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
										, SETTConstant.InterestFeeType.INTEREST
										, io
										, ib
										, transDetailDAO
										, subaccDAO);
								
								//add by zcwang 2007-6-23
								resultInfo.setPayFormID(payFormID);
							}
	
							// ������Ϣֵ
							resultInfo.setInterest(UtilOperation.Arith.round(resultInfo.getInterest(), 2));
	
							settlmentVec.setElementAt(resultInfo, i);
	
							// ���뷵�ؽ��
							resultVec.addElement(resultInfo);
						}
					}

					log.info("-------������Ϣ��ѯ���ݽ���--------");
				}

			}

			if (queryInfo.isBCompoundInterest())
			{
				compoundVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.COMPOUNDINTEREST);

				if (compoundVec != null && compoundVec.size() > 0)
				{
					log.info("-------��ʼ����������--------");
					for (int i = 0; i < compoundVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) compoundVec.elementAt(i);
						//add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.COMPOUNDINTEREST, io, ib, transDetailDAO);
						//add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						compoundVec.setElementAt(resultInfo, i);

						//���뷵�ؽ��
						resultVec.addElement(resultInfo);
					}
					log.info("-------���������ݽ���--------");
				}
			}
			
			if (queryInfo.isBForfeitInterest())
			{
				forfeitVec = dao.selectSettlementRecords(conInternal, queryInfo,
						SETTConstant.InterestFeeType.FORFEITINTEREST);
				if (forfeitVec != null && forfeitVec.size() > 0)
				{
					log.info("-------��ʼ����Ϣ����--------");
					for (int i = 0; i < forfeitVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) forfeitVec.elementAt(i);
						//		add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();
						//
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.FORFEITINTEREST, io, ib, transDetailDAO);
					    //		add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						//

						forfeitVec.setElementAt(resultInfo, i);

						// ���뷵�ؽ��
						resultVec.addElement(resultInfo);
					}
					log.info("-------����Ϣ���ݽ���--------");
				}

			}
			
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.COMMISION)
			{
				commissionVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.COMMISION);
				if (commissionVec != null && commissionVec.size() > 0)
				{
					log.info("-------��ʼ��������������--------");
					for (int i = 0; i < commissionVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) commissionVec.elementAt(i);
						resultInfo = this.getInterestInfo(
								  resultInfo.getFixedDepositNo()
								, resultInfo.getLoanPreDrawInterest()
								, resultInfo.getContractNo()
								, resultInfo.getPayFormNo()
								, resultInfo.getSubAccountID()
								, resultInfo.getAccountID()
								, resultInfo.getAccountTypeID()
								, queryInfo.getOfficeID()
								, queryInfo.getCurrencyID()
								, queryInfo.getClearInterest()
								, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
								, SETTConstant.InterestFeeType.COMMISION
								, io
								, ib
								, transDetailDAO);
						
						commissionVec.setElementAt(resultInfo, i);

						// ���뷵�ؽ��
						resultVec.addElement(resultInfo);
					}
					log.info("-------�������������ݽ���--------");
				}
			}
			
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.ASSURE)
			{
				assuerVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.ASSURE);
				if (assuerVec != null && assuerVec.size() > 0)
				{
					log.info("-------��ʼ������������--------");
					for (int i = 0; i < assuerVec.size(); i++)
					{
						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) assuerVec.elementAt(i);
						resultInfo = this.getInterestInfo(resultInfo.getFixedDepositNo(), resultInfo
								.getLoanPreDrawInterest(), resultInfo.getContractNo(), resultInfo.getPayFormNo(),
								resultInfo.getSubAccountID(), resultInfo.getAccountID(), resultInfo.getAccountTypeID(),
								queryInfo.getOfficeID(), queryInfo.getCurrencyID(), queryInfo.getClearInterest(),
								Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID()),
								SETTConstant.InterestFeeType.ASSURE, io, ib, transDetailDAO);
						assuerVec.setElementAt(resultInfo, i);

						// ���뷵�ؽ��
						resultVec.addElement(resultInfo);
					}
					log.info("-------�����������ݽ���--------");
				}
			}

			/**
			 * ���� info.setFeeType(lFeeType); --������Ϣ 6
			 */

			// ������Ϣ
			if (queryInfo.getFeeType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{

				preDrawVec = dao.selectSettlementRecords(conInternal, queryInfo, SETTConstant.InterestFeeType.PREDRAWINTEREST);

				if (preDrawVec != null && preDrawVec.size() > 0)
				{
					log.info("-------��ʼ���������Ϣ����--------");

					for (int i = 0; i < preDrawVec.size(); i++)
					{

						InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();
						resultInfo = (InterestQueryResultInfo) preDrawVec.elementAt(i);
						//add by zcwang 2007-6-23
						long payFormID=-1;
						payFormID=resultInfo.getPayFormID();

						resultInfo = this.getInterestInfo(
								resultInfo.getFixedDepositNo()
								, resultInfo.getLoanPreDrawInterest()
								, resultInfo.getContractNo()
								, resultInfo.getPayFormNo()
								, resultInfo.getSubAccountID()
								, resultInfo.getAccountID()
								, resultInfo.getAccountTypeID()
								, queryInfo.getOfficeID()
								, queryInfo.getCurrencyID()
								, queryInfo.getClearInterest()
								, Env.getSystemDate(conInternal, queryInfo.getOfficeID(), queryInfo.getCurrencyID())
								, SETTConstant.InterestFeeType.PREDRAWINTEREST  //6
								, io
								, ib
								, transDetailDAO );
						
						//add by zcwang 2007-6-23
						resultInfo.setPayFormID(payFormID);
						
						preDrawVec.setElementAt(resultInfo, i);

						//���뷵�ؽ��
						resultVec.addElement(resultInfo);
					}

					log.info("-------���������Ϣ���ݽ���--------");

				}

			}

			log.info("-------��ʼ�����ѯ����--------");
			log.info("-------�����ѯ���ݽ���--------");

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
				}
				catch (Exception eCommit)
				{
					throw new IException("�����ύ�쳣");
				}
			}

		}

		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new IException("��������쳣");
				}
			}
			throw e;
		}

		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return resultVec;
	}

	/**
	 * ������Ϣ(ֻ������ʹ��)
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getDiscountInterestInfo(String strFixDepsitNo, double mloanPredrawInterest,
			String strContractNo, String strPayFormNo, long nSubAccountID, long nAccountID, long nAccountTYpe,
			double dInterestRate, Timestamp enddate, long contractID, long discreID, long nOfficeID, long nCurrencyID,
			Timestamp dtClearInterest, Timestamp dtSysdate, long nInterestType // 1
			, InterestOperation io, InterestBatch ib, sett_TransAccountDetailDAO transDetailDAO) throws Exception
	{

		InterestQueryResultInfo interestInfo = new InterestQueryResultInfo();
		// ���÷��ؽ���ĳ�ʼ��Ϣ
		InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();

		resultInfo.setAccountID(nAccountID);
		resultInfo.setAccountNo(NameRef.getAccountNoByID(nAccountID));
		resultInfo.setSubAccountID(nSubAccountID);
		resultInfo.setAccountTypeID(nAccountTYpe);
		resultInfo.setContractNo(strContractNo);
		resultInfo.setPayFormNo(strPayFormNo);
		resultInfo.setFixedDepositNo(strFixDepsitNo);
		resultInfo.setLoanPreDrawInterest(mloanPredrawInterest);

        log.debug("---------�ж��˻�����------------");
		long accountTypeID = resultInfo.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        try {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (accountTypeInfo != null) {
			// �Ƿ����ִ����˻�������
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
			{
				log.info("lhj debug info ===�������ִ�����Ϣ=======");
	
				LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
				FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
				// ��Ϣ
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
	
					log.info("�����˻���Ҫ��Ϣ! ");
					/*
					 * 2011-03-18 ���ָ� ע�ͣ������˻�����Ϣ����������Ϣ
					 *
					Collection coll = null;
	
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
	
					Iterator itResult = null;
	
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------��ʼ���ֵ�����Ϣ����---------");
	
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
	
								if (flag < 0)
								{
									throw new IException("���ֵ�������ʧ��");
								}
	
								log.info("-------------���ֵ����������---------");
	
							}
						}
					}
	
					log.info("-------------�ж��Ƿ���Ҫ���ֵ����������---------");
	
					// ��Ϣ
	
					// loanInterestInfo = io.GetDiscountAccountInterest(
					// nOfficeID
					// , nCurrencyID
					// , resultInfo.getAccountID()
					// , resultInfo.getSubAccountID()
					// , dtClearInterest
					// , dtSysdate
					// , dInterestRate
					// );
					 * 
					 */
	
					/**
					 * �޸ĵĵط�
					 */
					log.info("-------------������Ϣ��ʼ---------");
					// ������ϢȻ�󷵻�
					fixedInterestInfo = io.getDiscountAccountPayableInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest // ҳ��¼��"��Ϣ��"
							, dInterestRate // ���ֺ�ͬ�е�����
							, enddate, contractID, discreID);
	
					log.info("-------------������Ϣ����---------");
	
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					// ��Ϣ
					// sett_DailyAccountBalance���ֶ��ۼ���Ϣ/�ۼ�������ϢMINTEREST
					// �����ʱ���õ��Ǵ�����
					// �ۼ���Ϣ - ���ʻ��еļ�����Ϣ
					newResultInfo.setInterest(fixedInterestInfo.getInterest());
	
					/**
					 * ������Ҫ�޸ĵĵط� ���ί����μ��� ������Ϣ = �ۼ���Ϣ - ���ʻ����������Ϣ
					 */
					// if ( SETTConstant.AccountType.isConsignAccountType(
					// resultInfo.getAccountTypeID() ) )
					// {
					// ���˻�
					// Sett_SubAccountDAO sett_SubAccountDAO = new
					// Sett_SubAccountDAO();
					// SubAccountLoanInfo subAccInfo = null;
					// subAccInfo =
					// sett_SubAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					// newResultInfo.setDrawingInterest(loanInterestInfo.getInterest()
					// - subAccInfo.getPreDrawInterest());
					// }
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
					// newResultInfo.setDays(loanInterestInfo.getDays());
	
					// ���Լ���
					newResultInfo.setDrawingResult(true);
	
					// ������Ϣ
					newResultInfo.setDrawingInterest(fixedInterestInfo.getDrawinterest());
	
					log.info("����newResultInfo.getAccountTypeID�ǣ� " + newResultInfo.getAccountTypeID());
	
					// �õ���Ϣ�˻����븶Ϣ�˻���
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
	
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
	
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				// ������Ϣ 6
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
	
					log.info("���ִ����˻�������Ϣ-------");
	
					// ִ�д��������Ϣ�ļ��㣬�����µļ�¼����
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getLoanAccountPredrawInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), resultInfo.getAccountTypeID(), dtClearInterest);
	
					// loanInterestInfo.setBalance(subInterestInfo.getBalance());
					/**
					 * �޸ĵĵط�
					 */
					// ������ϢȻ�󷵻�
					fixedInterestInfo = io.getDiscountAccountPayableInterest(resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest // ҳ��¼��"��Ϣ��"
							, dInterestRate // ���ֺ�ͬ�е�����
							, enddate, contractID, discreID);
					// ��ȡ���ʻ��е�����ֶ�,ȡû�е���Ʊ���ܽ��
					loanInterestInfo.setBalance(fixedInterestInfo.getBalance());
	
					loanInterestInfo.setDays(subInterestInfo.getDays());
					loanInterestInfo.setEDate(subInterestInfo.getEDate());
	
					loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest()); // ���ʻ����еļ�����Ϣ
	
					loanInterestInfo.setRate(subInterestInfo.getRate());
					loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					// ������Ϣ
					// �ӱ�sett_SubAccount�����ǰ��AF_MPREDRAWINTEREST������Ϣ�ֶε�ֵ
					newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
	
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST); // ������Ϣ
	
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest); // ҳ��¼���Ϣ��
	
					return newResultInfo;
				}
				else if (nInterestType == SETTConstant.InterestFeeType.COMMISION)
				{
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.ASSURE)
				{
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
				}
				log.info("lhj debug info ===�������ִ�����Ϣ=======");
			}
		}
		return interestInfo;
	}
	
	/**
	 * ���ݲ�ѯ��¼�����Ѿ�����Ϣ�ļ�¼
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getInterestInfo( String strFixDepsitNo
			, double mloanPredrawInterest
			, String strContractNo
			, String strPayFormNo
			, long nSubAccountID
			, long nAccountID
			, long nAccountTYpe
			, long nOfficeID
			, long nCurrencyID
			, Timestamp dtClearInterest
			, Timestamp dtSysdate
			, long nInterestType // 1
			, InterestOperation io
			, InterestBatch ib
			, sett_TransAccountDetailDAO transDetailDAO) throws Exception
	{
		return this.getInterestInfo(strFixDepsitNo
				, mloanPredrawInterest
				, strContractNo
				, strPayFormNo
				, nSubAccountID
				, nAccountID
				, nAccountTYpe
				, nOfficeID
				, nCurrencyID
				, dtClearInterest
				, dtSysdate
				, nInterestType // 1
				, io
				, ib
				, transDetailDAO
				, null);
	}

	/**
	 * ���ݲ�ѯ��¼�����Ѿ�����Ϣ�ļ�¼
	 * 
	 * @param resultInfo
	 * @param fixedInterestInfo
	 * @return
	 */
	public InterestQueryResultInfo getInterestInfo( String strFixDepsitNo
			, double mloanPredrawInterest
			, String strContractNo
			, String strPayFormNo
			, long nSubAccountID
			, long nAccountID
			, long nAccountTYpe
			, long nOfficeID
			, long nCurrencyID
			, Timestamp dtClearInterest
			, Timestamp dtSysdate
			, long nInterestType // 1
			, InterestOperation io
			, InterestBatch ib
			, sett_TransAccountDetailDAO transDetailDAO
			, Sett_SubAccountDAO subaccDAO) throws Exception
	{

		InterestQueryResultInfo interestInfo = new InterestQueryResultInfo();
		// ���÷��ؽ���ĳ�ʼ��Ϣ
		InterestQueryResultInfo resultInfo = new InterestQueryResultInfo();

		resultInfo.setAccountID(nAccountID);
		resultInfo.setAccountNo(NameRef.getAccountNoByID(nAccountID));
		resultInfo.setSubAccountID(nSubAccountID);
		resultInfo.setAccountTypeID(nAccountTYpe);
		resultInfo.setContractNo(strContractNo);
		resultInfo.setPayFormNo(strPayFormNo);
		resultInfo.setFixedDepositNo(strFixDepsitNo);
		resultInfo.setLoanPreDrawInterest(mloanPredrawInterest);

        log.debug("---------�ж��˻�����------------");
        
		long accountTypeID = resultInfo.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        
        try 
        {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		
		if (accountTypeInfo != null) 
		{
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || // �Ƿ����˻�������
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) // �Ƿ�֪ͨ�˻�������
			{
				log.info("lhj debug info ===���붨����Ϣ=======");
	
				FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST) // ��Ϣ
				{
					log.info("--------------��ʼ������Ϣ------------");
	
					//������ϢȻ�󷵻�
					fixedInterestInfo = io.getNewDepositAccountInterest(
							resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest // ҳ��¼��"��Ϣ��"
							);
	
					log.info("--------------����������Ϣ------------");
	
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					//��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST); // ����Ϊ��Ϣ
	
					//��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
	
					//�Ƿ����ɹ���־���Զ��ڴ���˻���Ч��һ���ɹ���
					newResultInfo.setDrawingResult(true);
	
					//������Ϣ
					newResultInfo.setDrawingInterest(fixedInterestInfo.getDrawinterest());
	
					log.info("lhj debug info ===����������Ϣ=======");
	
					return newResultInfo;
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST) // ������Ϣ
				{
	
					log.info("lhj debug info ===���붨�ڼ�����Ϣ=======");
	
					//ִ�ж��ڼ���ļ��㣬�����µļ�¼����
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getFixedAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					fixedInterestInfo.setBalance(subInterestInfo.getBalance());
					fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
					//lhj
					//modify===���漸�����Ժ���û�б�Ҫ��ʾ����------2003-11-26-----------------------
					fixedInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008��2��25�� ��������Ӧ��ȡ�������� Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					fixedInterestInfo.setEDate(subInterestInfo.getPredrawDate());
					
					fixedInterestInfo.setRate(subInterestInfo.getRate());
					fixedInterestInfo.setSDate(subInterestInfo.getSDate());
					//lhj
					//modify==---------------------------------------2003-11-26-------------
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
					//������Ϣ
					newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
					newResultInfo.setInterest(0);
					//��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
					//��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest); // ҳ��¼��"��Ϣ��"
	
					log.info("lhj debug info ===�������ڼ�����Ϣ=======");
	
					return newResultInfo;
				}
			}
	
			//�Ƿ���������˻�������
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
			{
				//��Ϣ
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("lhj debug info ===������ڣ���֤�𣩴����Ϣ======= ������");
	
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					Collection coll = null;
	
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
	
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------��ʼ������Ϣ����---------");
	
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
	
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
	
								log.info("-------------�����������---------");
							}
						}
					}
	
					log.info("-------------�ж��Ƿ���Ҫ�����������---------");
					log.info("-------------��Ϣ��ʼ---------");
					log.info("lhj debug info == ����getCurrentDepositAccountInterest.......");
	
					currInterestInfo = io.getCurrentDepositAccountInterest(nOfficeID, nCurrencyID, resultInfo
							.getSubAccountID(), dtClearInterest, dtSysdate);
	
					resultInfo.setDays(currInterestInfo.getIntervalDays());
					resultInfo.setEndDate(currInterestInfo.getEDate());
					// �Ĵ�ƽ�����
					// resultInfo.setBalance(currInterestInfo.getNormalBalance());
					// resultInfo.setBalance(currInterestInfo.getAverageNormalBalance());
	
					// �ô�ƽ�����+ƽ��Э�����
					resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
	
					/**
					 * ������Ϣ
					 */
					// ������ʻ������е��ۼ���Ϣ����
					// �Ƿ�����˻�������
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)
					{
						resultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(currInterestInfo.getNormalInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
					}
					else
					{
						resultInfo.setInterest(currInterestInfo.getNormalInterest());
					}
					
					resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
	
					//ҳ����ʾ"��ʼ����"
					resultInfo.setStartDate(currInterestInfo.getSDate());
	
					//Э����Ϣ
					resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
					
					//Boxu Add 2008��2��25�� ��������Ϣ��ɺ���ʾ��ϢӦ��Ϊ"0"
					if(currInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//
					}
					else
					{
						resultInfo.setInterest(0.0);
						
						//Э����Ϣ
						resultInfo.setNegotiateInterest(0.0);
					}
					
					//�Ĵ�ƽ��Э�����
					//resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
					resultInfo.setNegotiateBalance(currInterestInfo.getAverageNegotiationBalance());
					resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
	
					//��Ϣ����
					resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//��Ϣ��
					resultInfo.setCreateDate(dtClearInterest);
	
					log.info("-------------��Ϣ����---------");
	
					//�õ���Ϣ�˻����븶Ϣ�˻���
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��ſ�ʼ---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
	
					resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��Ž���---------");
					log.info("lhj debug info ===�������ڣ���֤����Ϣ=======");
	
					return resultInfo;
				}
			}
			
			//�����ӻ��ڼ��� �����ӱ�֤�����
			else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || // �Ƿ�����˻�������
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK || // �Ƿ񱸸����˻�������
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE || // �Ƿ�׼�����˻�������
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING || // �Ƿ����˻�������
					 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) // �Ƿ�֤���˻�������
			{
				//��Ϣ
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("lhj debug info ===������ڣ���֤�𣩴����Ϣ=======");
	
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					Collection coll = null;
	
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
	
					//coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nCurrencyID, nOfficeID, dtClearInterest);
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nOfficeID, nCurrencyID, dtClearInterest);
					
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------��ʼ������Ϣ����---------");
	
								long flag = ib.accountInterestCalculationBackward( resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE );
	
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
	
								log.info("-------------�����������---------");
							}
						}
					}
	
					log.info("-------------�ж��Ƿ���Ҫ�����������---------");
					log.info("-------------��Ϣ��ʼ---------");
					log.info("lhj debug info == ����getCurrentDepositAccountInterest.......");
	
					currInterestInfo = io.getCurrentDepositAccountInterest( nOfficeID
																		  , nCurrencyID
																		  , resultInfo.getSubAccountID()
																		  , dtClearInterest
																		  , dtSysdate );
	
					resultInfo.setDays(currInterestInfo.getIntervalDays());
					resultInfo.setEndDate(currInterestInfo.getEDate());
					
					//�Ĵ�ƽ�����
					//resultInfo.setBalance(currInterestInfo.getNormalBalance());
					//resultInfo.setBalance(currInterestInfo.getAverageNormalBalance());
	
					//�Ĵ�ƽ����� + ƽ��Э�����
					resultInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
					
					/**
					 * ������Ϣ
					 */
					// ������ʻ������е��ۼ���Ϣ����
					// �Ƿ�����˻�������
					// if (
					// SETTConstant.AccountType.isCurrentAccountType(resultInfo.getAccountTypeID())
					// )
					// {
					
					//Boxu Add 2008��2��25�� ���ڽ�Ϣ��ɺ���ʾ��ϢӦ��Ϊ"0"
					if(currInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//modify by xwhe 2008-11-07 ���ۼƼ�����Ϣ������ʱ�����Ǵ�sett_dailyaccountbalance��ȡ�������յ�ǰһ����ۼ�
						//������Ϣ���ۼ�Э��������Ϣ
						if(mloanPredrawInterest > 0.01)
						{
							Connection conInternal = null;
							Sett_DailyAccountBalanceDAO dailyDAO = new Sett_DailyAccountBalanceDAO(conInternal);
							Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conInternal);
							InterestCalculation inter_cal = new InterestCalculation();
							SubAccountCurrentInfo subAccInfo = null;
							
							subAccInfo = sett_SubAccountDAO.findByID(nSubAccountID).getSubAccountCurrenctInfo();
							Timestamp dtLastDate = inter_cal.getNextNDay(subAccInfo.getPreDrawDate(), -1);
							Timestamp dtLastDate1 = inter_cal.getNextNDay(dtClearInterest, -1);
							
							DailyAccountBalanceInfo balanceInfo = dailyDAO.findAllBySubAccountIDAndDate(
									  nSubAccountID
									, nOfficeID
									, nCurrencyID
									, dtLastDate);
							DailyAccountBalanceInfo balanceInfo1 = dailyDAO.findAllBySubAccountIDAndDate(
									  nSubAccountID
									, nOfficeID
									, nCurrencyID
									, dtLastDate1);			
							/** ���»�ȡ��Ϣ **/	
							resultInfo.setInterest(UtilOperation.Arith.sub(balanceInfo1.getInterest(),balanceInfo.getInterest()));
						
							resultInfo.setNegotiateInterest(UtilOperation.Arith.sub(balanceInfo1.getAc_mNegotiateInterest(), balanceInfo.getAc_mNegotiateInterest()));
						}
						else
						{
							resultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(currInterestInfo.getNormalInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
							
							//Э����Ϣ
							resultInfo.setNegotiateInterest(currInterestInfo.getNegotiationInterest());
						}
					}
					else
					{
						resultInfo.setInterest(0.0);
						
						//Э����Ϣ
						resultInfo.setNegotiateInterest(0.0);
					}
					
					// }
					// else
					// {
					// resultInfo.setInterest(currInterestInfo.getNormalInterest());
					// }
	
					resultInfo.setInterestRate(currInterestInfo.getNormalInterestRate());
	
					//ҳ����ʾ"��ʼ����"
					resultInfo.setStartDate(currInterestInfo.getSDate());
					
					//�Ĵ�ƽ��Э�����
					//resultInfo.setNegotiateBalance(currInterestInfo.getNegotiationBalance());
					resultInfo.setNegotiateBalance(currInterestInfo.getAverageNegotiationBalance());
					resultInfo.setNegotiateInterestRate(currInterestInfo.getNegotiationInterestRate());
	
					//��Ϣ����
					resultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//��Ϣ��
					resultInfo.setCreateDate(dtClearInterest);
	
					log.info("-------------��Ϣ����---------");
	
					//�õ���Ϣ�˻����븶Ϣ�˻���
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��ſ�ʼ---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
	
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
	
					resultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					resultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻��Ž���---------");
					log.info("lhj debug info ===�������ڣ���֤����Ϣ=======");
	
					//���������Ϣ
					resultInfo.setDrawingInterest(currInterestInfo.getDrawInterest());
	
					return resultInfo;
				}
	
				//������Ϣ(���ö��ڼ�����Ϣ����)
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
	
					log.info("lhj debug info ===������ڼ�����Ϣ=======");
	
					FixedDepositAccountPayableInterestInfo fixedInterestInfo = new FixedDepositAccountPayableInterestInfo();
	
					//ִ�ж��ڼ���ļ��㣬�����µļ�¼����
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getFixedAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					/**
					 * �޸ļ�����ʾ���,��ʾΪƽ�����
					 */
					CurrentDepositAccountInterestInfo currInterestInfo = new CurrentDepositAccountInterestInfo();
					currInterestInfo = io.getCurrentDepositAccountInterest(
							  nOfficeID
							, nCurrencyID
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate);
					//δ����ֱ��ȡ���ݿ�����ֶ�
					//fixedInterestInfo.setBalance(subInterestInfo.getBalance());
					fixedInterestInfo.setBalance(UtilOperation.Arith.add(currInterestInfo.getAverageNormalBalance(), currInterestInfo.getAverageNegotiationBalance()));
	
					//������Ϣ
					fixedInterestInfo.setInterest(subInterestInfo.getPredrawInterest());
	
					//lhj
					//modify===���漸�����Ժ���û�б�Ҫ��ʾ����------2003-11-26-----------------------
					fixedInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008��2��25�� ��������Ӧ��ȡ�������� Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					fixedInterestInfo.setEDate(subInterestInfo.getPredrawDate());
					
					fixedInterestInfo.setRate(subInterestInfo.getRate());
					fixedInterestInfo.setSDate(subInterestInfo.getSDate());
					//lhj
					//modify==---------------------------------------2003-11-26-------------
					InterestQueryResultInfo newResultInfo = getFixedInfo(resultInfo, fixedInterestInfo);
	
					//������Ϣ
					newResultInfo.setDrawingInterest(fixedInterestInfo.getInterest());
	
					newResultInfo.setInterest(0);
					//��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
					//��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
	
					log.info("lhj debug info ===�������ڼ�����Ϣ=======");
	
					return newResultInfo;
	
				}
	
			}
			//�Ƿ�����˻�������
			else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT 
				   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT )
			{
				log.info("lhj debug info ===���������Ϣ=======");
	
				LoanAccountInterestInfo loanInterestInfo = new LoanAccountInterestInfo();
	
				//��Ϣ
				if (nInterestType == SETTConstant.InterestFeeType.INTEREST)
				{
					log.info("��interestSettlement 346��lhj debug settlement info >>>>>>>>>>>�����˻���Ҫ��Ϣ! ");
	
					Collection coll = null;
	
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
	
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest );
	
					Iterator itResult = null;
	
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
	
								log.info("-------------��ʼ������Ϣ����---------");
	
								long flag = ib.accountInterestCalculationBackward( resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE );
	
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
	
								log.info("-------------�����������---------");
	
							}
						}
					}
					
					// �ж��Ƿ����ʵ���
					if(subaccDAO!=null)
					{
						log.info("-------------�ж��Ƿ���Ҫ�����������---------");
						
						//add by bingliu 20120307 �����������
						/*��Ӫ���ί�д����ͬ�����ʵ���������ʷ��������Ϣ���ػ����ٴν�Ϣ��ϵͳ�������Ϣû�м�ȥ�ϴ��Ѿ��������Ϣ��
						 *��Ϣ֮ǰû�е�����Ϣ���ᵼ���������⣺1.�����Ϣ���ԣ�2.�ػ�ʱ�ᵹ����Ϣ������֮�����Ϣ������ȷ��û�м�ȥ�ϴν����Ϣ��
						 *����취���ڽ�Ϣ֮ǰ�ļ��㷽���м������ʵ��㷽����
						 */
						log.info("-------------�ж��Ƿ��е������ʵ���---------");
						Collection coll2 = null;
						coll2 = subaccDAO.findAccountLoanBankInterestAdjust(nOfficeID, nCurrencyID, 
								dtClearInterest,resultInfo.getSubAccountID());
						Iterator itResult2 = null;
						if (coll2 != null && coll2.size() > 0)
						{
							itResult2 = coll2.iterator();
							if (itResult2 != null && itResult2.hasNext())
							{
								while (itResult2.hasNext())
								{
									BankInterestAdjustInfo backinfo = (BankInterestAdjustInfo) itResult2.next();
									log.info("-------------��ʼ�������ʵ���---------");
									long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(),
											resultInfo.getSubAccountID(), backinfo.getBackDate(), 0, 
											nOfficeID, nCurrencyID,
											dtSysdate, SETTConstant.BooleanValue.ISFALSE);
									long lUpdateReturn = subaccDAO.updateLoanRateAdjustPayDetail(backinfo.getLoanRateAdjustPayDetailID());
									   log.debug("�������ʵ�����ϸ��Ϣ���ı�־lUpdateReturn = "+lUpdateReturn);
									if (flag < 0||lUpdateReturn < 0)
									{
										throw new IException("�������ʵ���ʧ��");
									}
									log.info("-------------�������ʵ������---------");
								}
							}
						}
						log.info("-------------�ж��Ƿ��е������ʵ������---------");
					}
	
					//��Ϣ
					log.info("-------------��Ϣ��ʼ---------");
	
					loanInterestInfo = io.GetLoanAccountInterest( nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate );
	
					log.info("----------��ӡ������Ϣ-------------");
	
					log.debug(UtilOperation.dataentityToString(loanInterestInfo));
	
					log.info("-------------��Ϣ����---------");
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					//��Ϣ
					//sett_DailyAccountBalance���ֶ��ۼ���Ϣ/�ۼ�������ϢMINTEREST
					//�����ʱ���õ��Ǵ�����
					//�ۼ���Ϣ - ���ʻ��еļ�����Ϣ
					/**** 
					 * Boxu Add 2008��2��22�� �������:����֮��ѡ�������֮ǰ������,������ָ���Ϣ������
					 * ��/iTreasuryEJB/src/com/iss/itreasury/settlement/interest/bizlogic/InterestCalculation.java 1944-1949�� 
					 * *****/
					//if(UtilOperation.Arith.sub(loanInterestInfo.getInterest(), mloanPredrawInterest) < 0)
					//{
					//	newResultInfo.setInterest(0.0);
					//}
					//else
					//{
						newResultInfo.setInterest(UtilOperation.Arith.sub(UtilOperation.Arith.round(loanInterestInfo.getInterest(),2), UtilOperation.Arith.round(mloanPredrawInterest,2)));
					//}
					
					//Boxu Add 2008��2��25�� ��������Ϣ��ɺ���ʾ��ϢӦ��Ϊ"0"
					if(loanInterestInfo.getSDate().compareTo(dtClearInterest)!=0)
					{
						//
					}
					else
					{
						newResultInfo.setInterest(0.0);
					}
						
					/**
					 * ������Ҫ�޸ĵĵط� ���ί����μ��� ������Ϣ = �ۼ���Ϣ - ���ʻ����������Ϣ
					 */
					// if ( SETTConstant.AccountType.isConsignAccountType(
					// resultInfo.getAccountTypeID() ) )
					// {
					// ���˻�
					// Sett_SubAccountDAO sett_SubAccountDAO = new
					// Sett_SubAccountDAO();
					// SubAccountLoanInfo subAccInfo = null;
					// subAccInfo =
					// sett_SubAccountDAO.findByID(resultInfo.getSubAccountID()).getSubAccountLoanInfo();
					// newResultInfo.setDrawingInterest(loanInterestInfo.getInterest()
					// - subAccInfo.getPreDrawInterest());
					// }
						
					//��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
	
					//��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
					newResultInfo.setDays(loanInterestInfo.getDays());
	
					//�Լ���
					newResultInfo.setDrawingResult(true);
	
					//���������Ϣ
					newResultInfo.setDrawingInterest(loanInterestInfo.getDrawInterest());
	
					log.info("��interestSettlement 415��lhj debug settlement info >>>>>>>>>>newResultInfo.getAccountTypeID�ǣ� " + newResultInfo.getAccountTypeID());

			        log.debug("---------�ж��˻�����------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        
			        try 
			        {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} 
			        catch (SQLException e) 
					{
						e.printStackTrace();
					}
			        
					if (newAccountTypeInfo != null) 
					{
						//ί�д����˻�
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
		
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getInterest()); newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							//��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
							taxInfo = io.getInterestTaxByPlan( newResultInfo.getAccountID()
									, newResultInfo.getSubAccountID()
									//�޸ļ�����Ϣ˰�Ѵ������Ϣֵ,ȡ�˻������е���Ϣ
									//, newResultInfo.getInterest() );
									, loanInterestInfo.getInterest() );
		
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
		
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
		
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
		
						}
		
						//�õ���Ϣ�˻����븶Ϣ�˻���
						InterestAccountIDInfo interestAccountIDInfo = null;
		
						interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
		
						newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
		
						newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
		
						return newResultInfo;
		
					}
				}
	
				//������Ϣ
				if (nInterestType == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
					log.info("��interestSettlement 445��lhj debug settlement info >>>>>>>>>>>�����˻�������Ϣ-------");
	
					//ִ�д��������Ϣ�ļ��㣬�����µļ�¼����
					SubAccountPredrawInterestInfo subInterestInfo = new SubAccountPredrawInterestInfo();
	
					subInterestInfo = io.getLoanAccountPredrawInterest(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, resultInfo.getAccountTypeID()
							, dtClearInterest);
	
					loanInterestInfo.setBalance(subInterestInfo.getBalance());
					loanInterestInfo.setDays(subInterestInfo.getDays());
					
					//2008��2��25�� ��������Ӧ��ȡ�������� Boxu Add
					//fixedInterestInfo.setEDate(subInterestInfo.getEDate());
					loanInterestInfo.setEDate(subInterestInfo.getPredrawDate());
	
					loanInterestInfo.setInterest(subInterestInfo.getPredrawInterest()); // ���ʻ����еļ�����Ϣ
	
					loanInterestInfo.setRate(subInterestInfo.getRate());
					loanInterestInfo.setSDate(subInterestInfo.getSDate());
	
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
	
					// ������Ϣ
					// �ӱ�sett_SubAccount�����ǰ��AF_MPREDRAWINTEREST������Ϣ�ֶε�ֵ
					newResultInfo.setDrawingInterest(loanInterestInfo.getInterest());
	
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST); // ������Ϣ
	
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest); // ҳ��¼���Ϣ��
	
					return newResultInfo;
	
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.COMMISION)
				{
					log.info("��interestSettlement 475��lhj debug settlement info >>>>>>>>>>>�����˻�������-------");
					Collection coll = null;
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
					coll = transDetailDAO.findByIsBackward(
							  resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------��ʼ������Ϣ����---------");
								long flag = ib.accountInterestCalculationBackward(
										  resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
							}
						}
					}
					loanInterestInfo = io.getLoanAccountFee(
							  nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate
							, SETTConstant.InterestFeeType.COMMISION);
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// ������
					newResultInfo.setHandlingCharge(loanInterestInfo.getInterest());
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMMISION);
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(), resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				else if (nInterestType == SETTConstant.InterestFeeType.ASSURE)
				{
					log.info("��interestSettlement 551��lhj debug settlement info >>>>>>>>�����˻�������-------");
					Collection coll = null;
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------��ʼ��������---------");
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
							}
						}
					}
					loanInterestInfo = io.getLoanAccountFee(nOfficeID, nCurrencyID, resultInfo.getAccountID(), resultInfo
							.getSubAccountID(), dtClearInterest, dtSysdate, SETTConstant.InterestFeeType.ASSURE);
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// ������
					newResultInfo.setAssuranceCharge(loanInterestInfo.getInterest());
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.ASSURE);
	
					// ��Ϣ˰�ѣ�������û����Ϣ˰�ѣ�
					/*
					 * InterestTaxInfo taxInfo = new InterestTaxInfo(); log.info("��interestSettlement 611��������Ϣ˰��"); taxInfo =
					 * io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
					 * newResultInfo.getAssuranceCharge()); newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
					 */
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
	
					return newResultInfo;
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					log.info("��interestSettlement 635��lhj debug info>>>>>�����.....");
					Collection coll = null;
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
					coll = transDetailDAO.findByIsBackward(
							resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, nCurrencyID
							, nOfficeID
							, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------��ʼ������Ϣ����---------");
								long flag = ib.accountInterestCalculationBackward(
										resultInfo.getAccountID()
										, resultInfo.getSubAccountID()
										, detailInfo.getDtInterestStart()
										, detailInfo.getAmount()
										, nOfficeID
										, nCurrencyID
										, dtSysdate
										, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
							}
						}
					}
					
					//����
					loanInterestInfo = io.getLoanAccountFee(
							nOfficeID
							, nCurrencyID
							, resultInfo.getAccountID()
							, resultInfo.getSubAccountID()
							, dtClearInterest
							, dtSysdate
							, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					//Boxu Update 2008��11��10�� �й��������������ڸ�������
//					loanInterestInfo = io.getLoanGuoDianAccountFee(
//							nOfficeID
//							, nCurrencyID
//							, resultInfo.getAccountID()
//							, resultInfo.getSubAccountID()
//							, dtClearInterest
//							, dtSysdate
//							, SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// ����
					newResultInfo.setCompoundInterest(loanInterestInfo.getInterest());
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.COMPOUNDINTEREST);
			        log.debug("---------�ж��˻�����------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        
			        try 
			        {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} 
			        catch (SQLException e) 
					{
						e.printStackTrace();
					}
					
					if (newAccountTypeInfo != null) 
					{
						// ��Ϣ˰�ѣ�ֻ��ί�����У�
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
		
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getCompoundInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							// ��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
							taxInfo = io.getInterestTaxByPlan(
									newResultInfo.getAccountID()
									, newResultInfo.getSubAccountID()
									, newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						}
						// ��Ϣ��
						newResultInfo.setCreateDate(dtClearInterest);
						log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
						InterestAccountIDInfo interestAccountIDInfo = null;
						interestAccountIDInfo = ib.getInterestAccountID(
								resultInfo.getAccountID()
								, resultInfo.getSubAccountID()
								, nInterestType);
						newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
						newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
		
						return newResultInfo;
					}
				}
	
				if (nInterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					log.info("��interestSettlement 721��lhj debug info>>>>>���Ϣ.....");
	
					Collection coll = null;
					log.info("-------------�ж��Ƿ���Ҫ��������---------");
					coll = transDetailDAO.findByIsBackward(resultInfo.getAccountID(), resultInfo.getSubAccountID(),
							nCurrencyID, nOfficeID, dtClearInterest);
					Iterator itResult = null;
					if (coll != null && coll.size() > 0)
					{
						itResult = coll.iterator();
						if (itResult != null && itResult.hasNext())
						{
							while (itResult.hasNext())
							{
								TransAccountDetailInfo detailInfo = (TransAccountDetailInfo) itResult.next();
								log.info("-------------��ʼ������Ϣ����---------");
								long flag = ib.accountInterestCalculationBackward(resultInfo.getAccountID(), resultInfo
										.getSubAccountID(), detailInfo.getDtInterestStart(), detailInfo.getAmount(),
										nOfficeID, nCurrencyID, dtSysdate, SETTConstant.BooleanValue.ISFALSE);
								if (flag < 0)
								{
									throw new IException("��������ʧ��");
								}
								log.info("-------------�����������---------");
							}
						}
					}
					
					//��Ϣ
					loanInterestInfo = io.getLoanAccountFee(
							nOfficeID, 
							nCurrencyID, 
							resultInfo.getAccountID(), 
							resultInfo.getSubAccountID(), 
							dtClearInterest, 
							dtSysdate, 
							SETTConstant.InterestFeeType.FORFEITINTEREST);
					//Boxu Update 2008��11��10�� �й��������������ڷ�Ϣ����
//					loanInterestInfo = io.getLoanGuoDianAccountFee(
//							nOfficeID, 
//							nCurrencyID, 
//							resultInfo.getAccountID(), 
//							resultInfo.getSubAccountID(), 
//							dtClearInterest, 
//							dtSysdate, 
//							SETTConstant.InterestFeeType.FORFEITINTEREST);
					
					InterestQueryResultInfo newResultInfo = getLoanInfo(resultInfo, loanInterestInfo);
					// ���ڷ�Ϣ
					newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
					// ��Ϣ����
					newResultInfo.setInterestType(SETTConstant.InterestFeeType.FORFEITINTEREST);

			        log.debug("---------�ж��˻�����------------");
			        AccountTypeInfo newAccountTypeInfo = null;
			        try {
			        	newAccountTypeInfo = sett_AccountTypeDAO.findByID(newResultInfo.getAccountTypeID());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (newAccountTypeInfo != null) {
						// ��Ϣ˰�ѣ�ֻ��ί�����У�
						if (newAccountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
						{
							InterestTaxInfo taxInfo = new InterestTaxInfo();
							/**
							 * taxInfo = io.getInterestTax( newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
							 * newResultInfo.getForfeitInterest());
							 * newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							 * newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							 */
		
							// ��ȡ��Ϣ˰�ѵķ�ʽ����������ĳɴ���Ϣ˰���ʼƻ��л�ȡ
							taxInfo = io.getInterestTaxByPlan(newResultInfo.getAccountID(), newResultInfo.getSubAccountID(),
									newResultInfo.getInterest());
							newResultInfo.setInterestTaxCharge(taxInfo.getInterestTax());
							newResultInfo.setInterestTaxRate(taxInfo.getInterestTaxRate());
							newResultInfo.setInterestTaxRatePlan(taxInfo.getInterestTaxPlanId());
						}
					}
					// ��Ϣ��
					newResultInfo.setCreateDate(dtClearInterest);
					log.info("-------------�õ���Ϣ�˻����븶Ϣ�˻���---------");
					InterestAccountIDInfo interestAccountIDInfo = null;
					interestAccountIDInfo = ib.getInterestAccountID(resultInfo.getAccountID(),
							resultInfo.getSubAccountID(), nInterestType);
					newResultInfo.setRecieveInterestAccountID(interestAccountIDInfo.getReceiveInterestAccountID());
					newResultInfo.setPayInterestAccountID(interestAccountIDInfo.getPayInterestAccountID());
					return newResultInfo;
				}

				log.info("lhj debug info ===����������Ϣ=======");
			}
		}
		
		return interestInfo;
	}

	private InterestQueryResultInfo getFixedInfo(InterestQueryResultInfo resultInfo,
			FixedDepositAccountPayableInterestInfo fixedInterestInfo)
	{

		// �����µļ�¼���뵽Vector
		InterestQueryResultInfo newResultInfo = new InterestQueryResultInfo();
		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());
		newResultInfo.setStartDate(fixedInterestInfo.getSDate());
		newResultInfo.setEndDate(fixedInterestInfo.getEDate());

		newResultInfo.setBalance(fixedInterestInfo.getBalance());

		newResultInfo.setInterest(fixedInterestInfo.getInterest());
		newResultInfo.setInterestRate(fixedInterestInfo.getRate());
		newResultInfo.setDays(fixedInterestInfo.getDays());
		return newResultInfo;
	}

	private InterestQueryResultInfo getLoanInfo(InterestQueryResultInfo resultInfo,
			LoanAccountInterestInfo loanInterestInfo)
	{

		InterestQueryResultInfo newResultInfo = new InterestQueryResultInfo();

		newResultInfo.setAccountNo(resultInfo.getAccountNo());
		newResultInfo.setAccountTypeID(resultInfo.getAccountTypeID());
		newResultInfo.setAccountID(resultInfo.getAccountID());
		newResultInfo.setSubAccountID(resultInfo.getSubAccountID());
		newResultInfo.setFixedDepositNo(resultInfo.getFixedDepositNo());
		newResultInfo.setContractNo(resultInfo.getContractNo());
		newResultInfo.setPayFormNo(resultInfo.getPayFormNo());

		// subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, "); //���������Ϣ
		// �ӱ�sett_subaccount���
		newResultInfo.setLoanPreDrawInterest(resultInfo.getLoanPreDrawInterest()); // ���������Ϣ

		newResultInfo.setStartDate(loanInterestInfo.getSDate());
		newResultInfo.setEndDate(loanInterestInfo.getEDate());
		newResultInfo.setBalance(loanInterestInfo.getBalance());

		// ��Ϣ
		// newResultInfo.setForfeitInterest(loanInterestInfo.getInterest());
		newResultInfo.setInterestRate(loanInterestInfo.getRate());
		newResultInfo.setDays(loanInterestInfo.getDays());

		return newResultInfo;

	}

	private int	INTEREST_CURRENT_WITHDRAW	= 0;

	private int	INTEREST_CURRENT_DEPOSIT	= 1;

	private int	INTEREST_LOAN_WITHDRAW		= 2;

	private int	INTEREST_LOAN_DEPOSIT		= 3;

	private int	INTEREST_MARGIN_DEPOSIT		= 4;

	/** ת����Ϣ����������˻��������� */
	private TransAccountDetailInfo transferInterestQueryResultInfoToTransAccountDetailInfo(InterestSettmentInfo isInfo,
			InterestQueryResultInfo info, String transNo, int transType)
	{

		TransAccountDetailInfo tadi = null;

		tadi = new TransAccountDetailInfo();
		tadi.setAbstractStr(isInfo.getAbstract());
		// tadi.setBankChequeNo(info.getb);
		// tadi.setBillTypeID(info.getBillTypeID());
		// tadi.setBillNo(info.getBillNo());
		tadi.setCurrencyID(isInfo.getCurrencyID());
		tadi.setDtExecute(isInfo.getExecuteDate());
		tadi.setDtInterestStart(info.getCreateDate());
		tadi.setOfficeID(isInfo.getOfficeID());
		tadi.setTransNo(transNo);
		// tadi.setTransactionTypeID(info.getTransactionTypeID());
		tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
		tadi.setGroup(-1);
		if (transType == INTEREST_CURRENT_WITHDRAW)
		{
			tadi.setTransAccountID(isInfo.getPayInterestAccountID());
			tadi.setOppAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_CURRENT_DEPOSIT)
		{
			tadi.setTransAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setOppAccountID(isInfo.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_MARGIN_DEPOSIT)
		{
			tadi.setTransAccountID(isInfo.getReceiveInterestAccountID());
			tadi.setOppAccountID(isInfo.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(isInfo.getInterest(), isInfo.getNegotiateInterest()), 2));
		}
		else if (transType == INTEREST_LOAN_DEPOSIT)
		{
			tadi.setTransAccountID(info.getRecieveInterestAccountID());
			tadi.setOppAccountID(info.getPayInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(UtilOperation.Arith.sub(isInfo.getInterest(), isInfo.getInterestTax()), 2));
		}
		else if (transType == INTEREST_LOAN_WITHDRAW)
		{
			tadi.setTransAccountID(info.getPayInterestAccountID());
			tadi.setOppAccountID(info.getRecieveInterestAccountID());
			tadi.setAmount(UtilOperation.Arith.round(isInfo.getInterest(), 2));
		}

		return tadi;
	}

	/**
	 * �������㡣
	 * 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultVec ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @param isSave �Ƿ񱣴�
	 * @param settmentInfo ��Ź�����Ϣ �Ƿ���ˣ��Ƿ񱣴��
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public Vector balanceInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		Vector result = new Vector ();
		int nResult = 0; // ����ֵ
		long lsubReceiveInterestID = -1;
		long lsubPayInterestID = -1;
		String[] sTransNo = new String[resultVec.size()];
		//��������ָ��
		boolean bIsValid = Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false);
		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{

			conInternal = con;
			conInternal.setAutoCommit(false);
		}
		try
		{
			try
			{
				// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
				// �����÷�������������һ����ɲ��֡�
				InterestBatch ib = new InterestBatch(conInternal);
				AccountOperation aco = new AccountOperation(conInternal);
				GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
				UtilOperation uo = new UtilOperation(conInternal);

				log.info("-------------��ʼ��Ϣ---------");

				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo info = new InterestQueryResultInfo();
					info = (InterestQueryResultInfo) resultVec.elementAt(i);
					
					/*ȡ������δ������Ϣ�Լ�Э��δ������Ϣ
					 * */
					double unpredrawInterest = info.getInterest();
					double unpredrawNegotiateInterest = info.getNegotiateInterest();
                  
					try
						{
						/** ���»�ȡ��Ϣ **/
						//��ԭ��Ϣ = δ������Ϣ + ������Ϣ
						//info.setInterest(UtilOperation.Arith.add(info.getInterest(), info.getDrawingInterest()));
						Sett_DailyAccountBalanceDAO dailyDAO = new Sett_DailyAccountBalanceDAO(conInternal);
						DailyAccountBalanceInfo balanceInfo = dailyDAO.findAllBySubAccountIDAndDate(
								  info.getSubAccountID()
								, settmentInfo.getOfficeID()
								, settmentInfo.getCurrencyID()
								, info.getEndDate());
					
						/* if(info.getBalance()<0){
							 throw new IException("��Ϣ����Ϊ��ֵ");
						 }*/
						/** ���»�ȡ��Ϣ **/
						if(balanceInfo!=null)
						{
							info.setInterest(balanceInfo.getInterest());
							info.setNegotiateInterest(balanceInfo.getAc_mNegotiateInterest());
						}
						
						//ȡ��Ϣ���� DTCLEARINTEREST
						Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO();
						SubAccountAssemblerInfo  AssemblerInfo =null;
						SubAccountCurrentInfo subAccInfo = null;
						SubAccountLoanInfo    subLoanInfo = null;
						AssemblerInfo = subAccountDAO.findByID(info.getSubAccountID());
						subAccInfo = AssemblerInfo.getSubAccountCurrenctInfo();
						subLoanInfo = AssemblerInfo.getSubAccountLoanInfo();
						if(info.getInterestType()==SETTConstant.InterestFeeType.INTEREST)
						{
							info.setStartDate(subAccInfo.getClearInterestDate());
						}
						else if(info.getInterestType()==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							Timestamp time =uo.getStartCompoundinterestDay(info.getSubAccountID());
							if(time!=null&&!(subLoanInfo.getClearCompoundDate().before(time)&&settmentInfo.getExecuteDate().before(time)))
							{
								info.setStartDate(time);
							}
							else
							{
								info.setStartDate(subLoanInfo.getClearCompoundDate());
							}
						}
						else if(info.getInterestType()==SETTConstant.InterestFeeType.FORFEITINTEREST)
						{
							info.setStartDate(subLoanInfo.getClearOverDueDate());
						}
						else
						{
							info.setStartDate(subAccInfo.getClearInterestDate());
						}
						
						//���¼���ʱ������
						InterestCalculation interestCalculation = new InterestCalculation();
						long intervalDays = interestCalculation.getIntervalDays(info.getStartDate(), interestCalculation.getNextNDay(info.getEndDate(), 1), InterestCalculation.INTERVALDAYSFLAG_FACTDAY);
						info.setDays(intervalDays);
						
				        log.debug("---------�ж��˻�����------------");
						long accountTypeID = info.getAccountTypeID();
				        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
				        AccountTypeInfo accountTypeInfo = null;
				        
				        try 
				        {
							accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
						} 
				        catch (SQLException e) 
				        {
							e.printStackTrace();
						}
				        
						if (accountTypeInfo != null)
						{
							//�ж�����������˻�,�����˻�,֪ͨ�˻�������"��Ϣ"
							if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT )
							{
								throw new IException(" �����˻�["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"���ܽ�Ϣ ");
							}
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED )
							{
								throw new IException(" �����˻�["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"���ܽ�Ϣ ");
							}
							else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY )
							{
								throw new IException(" ֪ͨ�˻�["+NameRef.getAccountNameByID(info.getAccountID())+"]"+ info.getAccountNo() +"���ܽ�Ϣ ");
							}
							
							//�Ƿ�����˻�������
							//�Ƿ���������˻�������
							//�Ƿ�֤���˻�������
							if (  accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN )
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								
								if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
								{
									throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
								}
								
								//�жϽ�Ϣ�պ͵�ǰҳ��¼������
								if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
								{
									throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
								}
								
								log.info("------------- ����/��֤�� ��Ϣ��ʼ��Ϣ---------");
		
								/**
								 * ��֤���˻���Ϣ ע�⣺��֤���˻��ڿ���ʱ�������ˡ���Ϣ�˻���Ϊ�����˻���������ϢӦ�ô���û����˻� ����ҵ���Ϣ�˻������Ϣ���Ҳ��������쳣
								 */
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
								{
									long marginReceiveInterestCurrentAccountID = -1; // ��֤���˻���Ӧ����Ϣ�����˻�
		
									log.info("----------��Ϣ��֤���˻� --------- info.getAccountID() = " + info.getAccountID());
		
									//�õ���֤����Ϣ�˻�
									Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
		
									marginReceiveInterestCurrentAccountID = sett_SubAccountDAO.getReceiveInterestAccountByMarginAccountID(info.getAccountID());
		
									log.info("----------��֤���˻���Ӧ����Ϣ�����˻� --------- marginReceiveInterestCurrentAccountID = " + marginReceiveInterestCurrentAccountID);
		
									info.setRecieveInterestAccountID(marginReceiveInterestCurrentAccountID);
								}
		
								//��Ϣ����
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									log.info("----------�ж���Ϣ�Ƿ�Ϊ0---------");
		
									//��Ϣ��0�������� �޸�Ϊ���ԽḺ����Ϣ
									if (UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()) != 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------����/Э���˻���Ϣ��ʼ---------");
											long flag = ib.clearCurrentDepositAccountInterest( info.getAccountID()
																							 , info.getSubAccountID()
																							 , info.getCreateDate()
																							 , info.getInterest()
																							 , info.getNegotiateInterest() );
		
											if (flag < 0)
											{
												throw new IException("����/Э���˻���Ϣʧ��");
											}
		
											log.info("-------------����/Э���˻���Ϣ����---------");
		
											//�����棬�ҽ�Ϣ���� < ִ�����ڣ�����õ�����Ϣ�����
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------��ʼ��������---------");
		
												long flag1 = ib.accountInterestSettlelmentBackward( info.getAccountID()
																								  , info.getSubAccountID()
																								  , info.getCreateDate()
																								  , UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest())
																								  , settmentInfo.getOfficeID()
																								  , settmentInfo.getCurrencyID()
																								  , Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) 
																								  ,SETTConstant.InterestFeeType.INTEREST);
		
												if (flag1 < 0)
												{
													throw new IException("��������ʧ��");
												}
												log.info("-------------��ʼ��������---------");
											}
											// �����Ϣ��¼
											log.info("-------------�����Ϣ��¼��ʼ---------");
		
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
											}
											//modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal ,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
		
											//�޸Ļ��ڽ�Ϣ�ļ�Ϣ���(���ϸ���Ϣ�յ����ڵ�ƽ���������) boxu 2007-8-18
											//Boxu Add 2008��2��28�� ����ֻ���������,Ӧ�����Э�����
											if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT )
											{
												Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(con);
												//�������
												double sumInterestBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//Э�����
												double sumNormalBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//ƽ�����
												double averageNormalBalance = 0.0;
												
												if(sumInterestBalance == 0 && sumNormalBalance == 0)
												{
													averageNormalBalance = 0.0;
												}
												else if(intervalDays == 0)
												{
													averageNormalBalance = UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance);
												}
												else
												{
													//ƽ���������
													averageNormalBalance = UtilOperation.Arith.div(
															UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance)
															, intervalDays);
												}
												
												saveInfo.setBaseBalance( averageNormalBalance );
											}
											
											//�����Ϣ��¼
											long l = dao.add(conInternal, saveInfo);
		
											if (l < 0)
											{
												throw new IException("��Ϣʧ�ܣ���");
											}
		
											log.info("-------------�����Ϣ��¼����---------");
											log.info("-------------�����Ϣ��¼���˿�ʼ............");
		
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)  // ����
											{
												System.out.println("***************** ��Ϣ�˻��� is " + info.getRecieveInterestAccountID());
												
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
												{
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------��֤�� ���뿪ʼ---------");
		
														// ���ڴ���
														// TransAccountDetailInfo
														// accountDetailInfo = new
														// TransAccountDetailInfo();
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
																									  saveInfo
																									, info
																									, saveInfo.getTransNo()
																									, INTEREST_MARGIN_DEPOSIT );
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
		
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("��֤�����ʧ��");
														}
														log.info("-------------��֤��������---------");
													}
		
													log.info("----��֤��--------���ɻ�Ʒ�¼��ʼ--------------");
		
													GenerateGLEntryParam param = new GenerateGLEntryParam();
		
													if (lsubReceiveInterestID < 0)
													{
														lsubReceiveInterestID = aco.getCurrentSubAccoutIDByAccoutID(saveInfo.getReceiveInterestAccountID());
													}
		
													// �޸���Ϣ�����˻�������ȡֵΪ��֤���˻������˻�
													param.setReceiveAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getNegotiateInterest(),2)), 2));
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2), UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));  //������Ϣ/������Ϣ
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));  //ʵ����Ϣ/˰����Ϣ/Э����Ϣ
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													// param.setCheckUserID(settmentInfo.getc);
													// param.setPrincipalType(lPrincipalType);
													// param.setInterestType(lInterestType);
													// param.setCommisionType(lCommissionType);
													// param.setEntryType(lEntryType);
		
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//δ������Ϣ���ɿ�
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] =param.getTransNo();
		
													log.info("------��֤��------���ɻ�Ʒ�¼����--------------");
												}
												else
												{
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------���ڴ��뿪ʼ---------");
														// ���ڴ���
														// TransAccountDetailInfo
														// accountDetailInfo = new
														// TransAccountDetailInfo();
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_DEPOSIT);
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("���ڴ���ʧ�ܣ�");
														}
														log.info("-------------���ڴ������---------");
													}
													log.info("----����-------���ɻ�Ʒ�¼��ʼ--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													if (lsubReceiveInterestID < 0)
													{
														lsubReceiveInterestID = aco.getCurrentSubAccoutIDByAccoutID(saveInfo.getReceiveInterestAccountID());
													}
													
													log.info("----����-------lsubReceiveInterestID--------------" + lsubReceiveInterestID);
													param.setReceiveAccountID(lsubReceiveInterestID);
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//��Ϣ�ϼ�
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													System.out.println("===param.setTotalInterest()=== "+param.getTotalInterest());
													//������Ϣ/������Ϣ
													//param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//ʵ����Ϣ/˰����Ϣ/Э����Ϣ
													//param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													/*modifyed by bingliu  20120604 
													 * δ������ϢҪ����������Ϣ��Э����Ϣ
													 * ����ȡҳ���ϴ�������Э����Ϣ��������Ϣ����������Ϣ���Ǽ����˼���Ĳ��֡�
													 * */
													//������Ϣ/������Ϣ
													 if(subAccInfo.getIsNegotiate()!=SETTConstant.BooleanValue.ISTRUE)
														{
															param.setRemissionInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(
																	UtilOperation.Arith.round(param.getTotalInterest(),2),
																	UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)),2));
														}
														//ֻ��ΪЭ������ʱ��������Ĵ���
														else{
														param.setRemissionInterest(UtilOperation.Arith.round(unpredrawInterest, 2));
														System.out.println("===param.setRemissionInterest()=== "+param.getRemissionInterest());
														
														//ʵ����Ϣ/˰����Ϣ/Э����Ϣ
														/*//�ɿ�(����Ϣ��ȥ������Ϣ���ټ�ȥ����δ������Ϣ)*/
														param.setReallyReceiveInterest(UtilOperation.Arith.round(
																						UtilOperation.Arith.sub(
																								UtilOperation.Arith.sub(
																										UtilOperation.Arith.round(param.getTotalInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2))
																											, UtilOperation.Arith.round(param.getRemissionInterest(),2)), 2));
													    }

													System.out.println("===param.getReallyReceiveInterest()=== "+param.getReallyReceiveInterest());
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													// param.setCheckUserID(settmentInfo.getc);
													// param.setPrincipalType(lPrincipalType);
													// param.setInterestType(lInterestType);
													// param.setCommisionType(lCommissionType);
													// param.setEntryType(lEntryType);
													
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													System.out.println("===param.setPreDrawInterest()=== "+param.getPreDrawInterest());
													
													/*//δ������Ϣ���ɿ�(������Ϣ����Э����Ϣ��ȥ������Ϣ���)*/
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																							UtilOperation.Arith.add(
																									UtilOperation.Arith.round(info.getInterest(),2),
																									UtilOperation.Arith.round(info.getNegotiateInterest(),2))
																										, UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------����------���ɻ�Ʒ�¼����--------------");
												}
		
											}
										}
										log.info("-------------����/��֤����Ϣ��Ϣ����---------");
									}
								}
							}
							
							//�Ƿ񱸸����˻�������
							//�Ƿ�׼�����˻�������
							//�Ƿ����˻�������
							if (  accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK 
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								
								if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
								{
									throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
								}
								
								//�жϽ�Ϣ�պ͵�ǰҳ��¼������
								if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
								{
									throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
								}
								
								log.info("------------- ��Ϣ��ʼ��Ϣ---------");
		
		
								//��Ϣ����
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									log.info("----------�ж���Ϣ�Ƿ�Ϊ0---------");
		
									//��Ϣ��0�������� �޸�Ϊ���ԽḺ����Ϣ
									if (UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()) != 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------������׼�����˻���Ϣ��ʼ---------");
											long flag = ib.clearCurrentDepositAccountInterest( info.getAccountID()
																							 , info.getSubAccountID()
																							 , info.getCreateDate()
																							 , info.getInterest()
																							 , info.getNegotiateInterest() );
		
											if (flag < 0)
											{
												throw new IException("������׼�����˻���Ϣʧ��");
											}
		
											log.info("-------------������׼�����˻���Ϣ����---------");
		
											//�����棬�ҽ�Ϣ���� < ִ�����ڣ�����õ�����Ϣ�����
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------��ʼ��������---------");
		
												long flag1 = ib.accountInterestSettlelmentBackward( info.getAccountID()
																								  , info.getSubAccountID()
																								  , info.getCreateDate()
																								  , UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest())
																								  , settmentInfo.getOfficeID()
																								  , settmentInfo.getCurrencyID()
																								  , Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) 
																								  ,SETTConstant.InterestFeeType.INTEREST);
		
												if (flag1 < 0)
												{
													throw new IException("��������ʧ��");
												}
												log.info("-------------��ʼ��������---------");
											}
											// �����Ϣ��¼
											log.info("-------------�����Ϣ��¼��ʼ---------");
		
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
											}
											//modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal ,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
		
											//�޸Ļ��ڽ�Ϣ�ļ�Ϣ���(���ϸ���Ϣ�յ����ڵ�ƽ���������) boxu 2007-8-18
											//Boxu Add 2008��2��28�� ����ֻ���������,Ӧ�����Э�����
											if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT )
											{
												Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(con);
												//�������
												double sumInterestBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//Э�����
												double sumNormalBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(info.getSubAccountID(), info.getStartDate(), info.getEndDate());
												
												//ƽ�����
												double averageNormalBalance = 0.0;
												
												if(sumInterestBalance == 0 && sumNormalBalance == 0)
												{
													averageNormalBalance = 0.0;
												}
												else if(intervalDays == 0)
												{
													averageNormalBalance = UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance);
												}
												else
												{
													//ƽ���������
													averageNormalBalance = UtilOperation.Arith.div(
															UtilOperation.Arith.add(sumInterestBalance ,sumNormalBalance)
															, intervalDays);
												}
												
												saveInfo.setBaseBalance( averageNormalBalance );
											}
											
											//�����Ϣ��¼
											long l = dao.add(conInternal, saveInfo);
		
											if (l < 0)
											{
												throw new IException("��Ϣʧ�ܣ���");
											}
		
											log.info("-------------�����Ϣ��¼����---------");
											log.info("-------------�����Ϣ��¼���˿�ʼ............");
		
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)  // ����
											{
												
												
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
												{ 
													System.out.println("***************** ��Ϣ�˻��� is " + info.getPayInterestAccountID());
													if (info.getPayInterestAccountID() > 0)
													{//�Բ���˻���˵�Ǹ�Ϣ�˻�
														
														log.info("-------------��踶Ϣ�˻�֧ȡ��ʼ---------");
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_WITHDRAW);
														
														tadi.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
														//��Ϣʹ�ã����ۼ��ۼ�δ���˽��
														tadi.setCommonOperation(false);
														lsubPayInterestID = aco.withdrawCurrent(tadi, conInternal);
														log.info("----��踶Ϣ���˻�-------lsubPayInterestID--------------" + lsubPayInterestID);
														if (lsubPayInterestID < 0)
														{
															throw new IException("��踶Ϣ�˻�֧ȡʧ�ܣ�");
														}
														log.info("-------------��踶Ϣ�˻�֧ȡ����---------");
													}
													log.info("----����˻���Ϣ-------���ɻ�Ʒ�¼��ʼ--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayInterestAccountID(lsubPayInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//��Ϣ�ϼ�
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													
													//������Ϣ/������Ϣ
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//ʵ����Ϣ/˰����Ϣ/Э����Ϣ
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_INTEREST);
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//δ������Ϣ���ɿ�(������Ϣ��ȥ������Ϣ���)
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																									UtilOperation.Arith.round(info.getInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------���------���ɻ�Ʒ�¼����--------------");
												
												}
												else
												{
													System.out.println("***************** ��Ϣ�˻��� is " + info.getRecieveInterestAccountID());
													if (info.getRecieveInterestAccountID() > 0)
													{
														log.info("-------------������׼������뿪ʼ---------");
														TransAccountDetailInfo tadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_CURRENT_DEPOSIT);
														
														if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
														{
															tadi.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
														}
														else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
														{
															tadi.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
														}
														
														lsubReceiveInterestID = aco.depositCurrent(tadi, conInternal);
														log.info("----������׼������Ϣ���˻�-------lsubReceiveInterestID--------------" + lsubReceiveInterestID);
														if (lsubReceiveInterestID < 0)
														{
															throw new IException("������׼�������ʧ�ܣ�");
														}
														log.info("-------------������׼����������---------");
													}
													log.info("----������׼�����Ϣ-------���ɻ�Ʒ�¼��ʼ--------------");
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPrincipalOrTransAmount(UtilOperation.Arith.round(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()), 2));
													
													//��Ϣ�ϼ�
													param.setTotalInterest(UtilOperation.Arith.round(UtilOperation.Arith.add(UtilOperation.Arith.round(info.getInterest(), 2),UtilOperation.Arith.round(info.getNegotiateInterest(), 2)), 2));
													
													//������Ϣ/������Ϣ
													param.setRemissionInterest(UtilOperation.Arith.round(info.getInterest(), 2));
													
													//ʵ����Ϣ/˰����Ϣ/Э����Ϣ
													param.setReallyReceiveInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
													
													param.setOfficeID(settmentInfo.getOfficeID());
													param.setCurrencyID(settmentInfo.getCurrencyID());
													if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_INTEREST);
													}
													param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//δ������Ϣ���ɿ�(������Ϣ��ȥ������Ϣ���)
													param.setUnPreDrawInterest(UtilOperation.Arith.round(
																					UtilOperation.Arith.sub(
																									UtilOperation.Arith.round(info.getInterest(),2),
																										UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
													
													log.info("------������׼����------���ɻ�Ʒ�¼����--------------");
												}
		
											}
										}
										log.info("-------------��Ϣ��Ϣ����---------");
									}
								}
							}
							
							//1.���д��� 2.ί�д��� 3.�������� 4.���Ŵ���
							if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
								preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
								//��ѯ�������
								double availableBalance = aco.findAvailableBalance(info.getPayInterestAccountID(),settmentInfo.getOfficeID(), settmentInfo.getCurrencyID());
								log.info("-------------���ʼ��Ϣ---------");
								//��Ϣ
								if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
								{
									if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									{
										throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									}
									
									//�жϽ�Ϣ�պ͵�ǰҳ��¼������
									if (preDrawInterestInfo.getEDate().compareTo(settmentInfo.getInputClearInterest()) == 0)
									{
										throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									}
									
									log.info("----------�ж���Ϣ�Ƿ����0------------");
									if (info.getInterest() > 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE)  //����
										{
											log.info("-------------������Ϣ��ʼ��Ϣ---------");
											
											//ȡ���������Ϣ
											SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
											preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
											
											log.info("-------------���������Ϣ��ʼ---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											
											//Ӧ��������Ϣ
											clearLoanInfo.setInterestReceivable(preDrawInfo.getPredrawInterest());
											
											
											//Ӧ����Ϣ
											clearLoanInfo.setInterest(info.getInterest());
											
											
											if(availableBalance>0&&availableBalance<info.getInterest()&&settmentInfo.isClearPartInterest())
											{
												//ʵ����Ϣ
												clearLoanInfo.setRealInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												//ʵ����Ϣ
												clearLoanInfo.setRealInterest(UtilOperation.Arith.round(info.getInterest(), 2));
											}
											if(availableBalance>0&&availableBalance<preDrawInfo.getPredrawInterest()&&settmentInfo.isClearPartInterest())
											{
												//ʵ��������Ϣ
												clearLoanInfo.setRealInterestReceivable(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												//ʵ��������Ϣ
												clearLoanInfo.setRealInterestReceivable(UtilOperation.Arith.round(preDrawInfo.getPredrawInterest(), 2));
											}
											
											//Ӧ����Ϣ
											//clearLoanInfo.setInterest(info.getInterest() + clearLoanInfo.getRealInterestReceivable() );
											
											//ʵ����Ϣ
											//clearLoanInfo.setRealInterest(UtilOperation.Arith.round(info.getInterest() + clearLoanInfo.getRealInterestReceivable(), 2) );
											info.setRealInterest(clearLoanInfo.getRealInterest());
											clearLoanInfo.setClearInterest(true);
											//�����棬�ҽ�Ϣ����<ִ�����ڣ�����õ�����Ϣ�����
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------��ʼ��������---------");
												long flag =-1;
												if(info.getInterestType()==SETTConstant.InterestFeeType.INTEREST)
												{
													 flag = ib.accountInterestSettlelmentBackward( info.getAccountID()
															, info.getSubAccountID()
															, info.getCreateDate()
															, UtilOperation.Arith.add(info.getRealInterest(), info.getNegotiateInterest())
															, settmentInfo.getOfficeID()
															, settmentInfo.getCurrencyID()
															, Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())
															,SETTConstant.InterestFeeType.INTEREST);
												}
												if (flag < 0)
												{
													throw new IException("��������ʧ��");
												}
												log.info("-------------�����������---------");
											}
											long flagClear = ib.clearLoanAccountInterest(clearLoanInfo);
											
											if (flagClear < 0)
											{
												throw new IException("������Ϣ��Ϣʧ��");
											}
											
											log.info("-------------���������Ϣ����---------");
		
											log.info("----------���濪ʼ---------");
											//�����Ϣ��¼
											
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)
											{
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
												}
												else
												{
													settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
											}
											//modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("��Ϣ����ʧ�ܣ���");
											}
											log.info("----------�������---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
											{
												//ί�д���
												log.info("----------�ж�ί�д����Ƿ����ո�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û�и�Ϣ�˻�");
													}
													if (info.getRecieveInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û����Ϣ�˻�");
													}
												}
												log.info("----------�ж���Ӫ�����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(��Ӫ����)û�и�Ϣ�˻�");
													}
												}
												
												//WZC ADD
												log.info("----------�ж����Ŵ����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) 
												{
													if (info.getPayInterestAccountID() <= 0) 
													{
														throw new IException("�˻�" + info.getAccountNo() + "(���Ŵ���)û�и�Ϣ�˻�");
													}
												}
												
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------����֧ȡ��ʼ---------");
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													
													//WZC ADD
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													
													else
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													//��Ϣ����
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("����֧ȡʧ�ܣ�");
													}
													log.info("-------------����֧ȡ����---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------���ڴ��뿪ʼ---------");
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													
													//WZC ADD
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
		
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("���ڴ���ʧ�ܣ�");
													}
													log.info("-------------���ڴ������---------");
												}
		
												log.info("------------���ɴ����Ʒ�¼��ʼ--------------");
		
												// ���Ҵ��������Ϣ
												// ǰ���Ѿ�ȡ���ˣ����ҽ����ˣ�������ȡ�Ļ�Ϊ0
												/*
												 * SubAccountPredrawInterestInfo preDrawInfo = new
												 * SubAccountPredrawInterestInfo(); preDrawInfo =
												 * uo.getPredrawInterestBySubAccountIDAndAccountType( info.getSubAccountID(),
												 * info.getAccountTypeID());
												 */
												
												//modify by zcwang 2007-6-23 ���Ŵ������ͨ�����Ʒ�¼��ͬ
												if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPayInterestAccountID(lsubPayInterestID);
													
													//����/���׽��
													param.setPrincipalOrTransAmount(saveInfo.getInterest());
													
													param.setTotalInterest(saveInfo.getInterest());
													
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//δ������Ϣ���ɿ�
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(saveInfo.getInterest(),2),UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)),2 ));
													
													param.setOfficeID(saveInfo.getOfficeID());
													param.setCurrencyID(saveInfo.getCurrencyID());
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													param.setExecuteDate(saveInfo.getExecuteDate());
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													param.setInterestTaxFee(info.getInterestTaxCharge());
													param.setReallyReceiveInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getInterestTaxCharge(),2)), 2));
													param.setList(list);
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													//sTransNo[i] = param.getTransNo();
		
													log.info("------------�������Ŵ����Ʒ�¼����--------------");
												}
												else
												{
													GenerateGLEntryParam param = new GenerateGLEntryParam();
													
													param.setReceiveAccountID(info.getSubAccountID());
													param.setPayAccountID(info.getSubAccountID());
													param.setReceiveInterestAccountID(lsubReceiveInterestID);
													param.setPayInterestAccountID(lsubPayInterestID);
													
													//����/���׽��
													param.setPrincipalOrTransAmount(saveInfo.getInterest());
													
													//��Ϣ�ϼƣ��ɿ�
													param.setTotalInterest(saveInfo.getInterest());
													
													//������Ϣ���ɿ�
													param.setPreDrawInterest(preDrawInterestInfo.getPredrawInterest());
													
													//δ������Ϣ���ɿ�
													param.setUnPreDrawInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(saveInfo.getInterest(),2), UtilOperation.Arith.round(preDrawInterestInfo.getPredrawInterest(),2)), 2));
													
													param.setOfficeID(saveInfo.getOfficeID());
													param.setCurrencyID(saveInfo.getCurrencyID());
													
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													param.setExecuteDate(saveInfo.getExecuteDate());
													param.setInterestStartDate(saveInfo.getInterestSettlementDate());
													param.setTransNo(saveInfo.getTransNo());
													param.setAbstractStr(saveInfo.getAbstract());
													param.setInputUserID(saveInfo.getInputUserID());
													param.setInterestTaxFee(info.getInterestTaxCharge());
													
													//ʵ����Ϣ/˰����Ϣ���ɿ�
													//��Ϣ˰��  info.getInterestTaxCharge()
													param.setReallyReceiveInterest(UtilOperation.Arith.round(UtilOperation.Arith.sub(UtilOperation.Arith.round(info.getInterest(),2), UtilOperation.Arith.round(info.getInterestTaxCharge(),2)), 2));
													
													boolean bFlag = glo.generateGLEntry(param, conInternal);
													
													//sTransNo[i] = param.getTransNo();
													
													log.info("------------���ɴ����Ʒ�¼����--------------");
												}
											}
											else
											{
												log.info("--------���ɺ��ֳ�����Ʒ�¼��ʼ---------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												//param.setRemissionInterest(saveInfo.getInterest());
												
												param.setPreDrawInterest((-1) * preDrawInfo.getPredrawInterest());
												param.setPrincipalOrTransAmount((-1) * preDrawInfo.getPredrawInterest());
												param.setTotalInterest((-1) * preDrawInfo.getPredrawInterest());
												
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
												param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),settmentInfo.getCurrencyID()));
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
		
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("--------���ɺ��ֳ�����Ʒ�¼����---------");
											}
		
										}
										/*
										 * else { InterestSettmentInfo saveInfo = getSaveInfo(settmentInfo, info);
										 * Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO(); long l =
										 * dao.add(conInternal, saveInfo); if (l < 0) { throw new IException("��Ϣ����ʧ�ܣ�"); } }
										 */
										log.info("-------------������Ϣ��Ϣ����---------");
		
									}
								}
								
								//������
								if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									//}
									
									log.info("----------�жϵ������Ƿ����0-----------");
									if (info.getAssuranceCharge() > 0)
									{
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------������㵣���ѿ�ʼ---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setSuretyFee(info.getAssuranceCharge());
											clearLoanInfo.setRealSuretyFee(UtilOperation.Arith.round(info.getAssuranceCharge(),
													2));
		
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("������ѽ�Ϣʧ��");
											}
											log.info("-------------������㵣���ѽ���---------");
											log.info("-------------���濪ʼ--------");
											//�����Ϣ��¼
											
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
	                                        //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("��Ϣ����ʧ�ܣ���");
											}
											log.info("-------------�������---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
											{
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------����֧ȡ��ʼ---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
													//��Ϣ����
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("����֧ȡʧ�ܣ�");
													}
													log.info("-------------����֧ȡ����---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------���ڴ��뿪ʼ---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("���ڴ���ʧ�ܣ�");
													}
													log.info("-------------���ڴ������---------");
												}
												log.info("------------���ɵ����ѻ�Ʒ�¼��ʼ--------------");
												
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												// param.setReceiveInterestAccountID(lsubReceiveInterestID);
												// param.setPayInterestAccountID(lsubPayInterestID);
												param.setPaySuertyFeeAccountID(lsubPayInterestID);
												param.setReceieveSuertyFeeAccountID(lsubReceiveInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setSuretyFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_SURETY_FEE);
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												
												log.info("------------���ɵ����ѻ�Ʒ�¼����--------------");
											}
										}
										/*
										 * else { InterestSettmentInfo saveInfo = getSaveInfo(settmentInfo, info);
										 * Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO(); long l =
										 * dao.add(conInternal, saveInfo); if (l < 0) { throw new IException("��Ϣ����ʧ�ܣ�"); } }
										 */
										log.info("-------------������ѽ�Ϣ����---------");
		
									}
								}
		
								//������
								if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									//}
									
									log.info("----------�ж��������Ƿ�>0----------------");
									if (info.getHandlingCharge() > 0)
									{
										log.info("-------------���������ѿ�ʼ��Ϣ---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------������������ѿ�ʼ---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setCommision(info.getHandlingCharge());
											clearLoanInfo.setRealCommission(UtilOperation.Arith.round(info.getHandlingCharge(),
													2));
		
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("�����ѽ�Ϣʧ��");
											}
											log.info("-------------������������ѽ���---------");
											log.info("-------------���濪ʼ---------");
											// �����Ϣ��¼
											
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
	                                        //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("��Ϣ����ʧ�ܣ���");
											}
											log.info("-------------�������---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
											{
												// ί�д���
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û�и��������˻�");
													}
												}
												
												/*
												//���Ŵ���
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) {
													if (info.getPayInterestAccountID() < 0) {
														throw new IException("�˻�"
																+ info.getAccountNo()
																+ "(���Ŵ���)û�и��������˻�");
													}
												}
												*/
												
												if (info.getPayInterestAccountID() > 0)
												{
													if (info.getPayInterestAccountID() > 0)
													{
														log.info("-------------����֧ȡ��ʼ---------");
		
														// accountDetailInfo.setAmount(info.getInterest());
														// accountDetailInfo.setTransAccountID(info.getAccountID());
														TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
		
														//withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
														
														//ί�д���
														if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
														{
															withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
														}
														/*
														//���Ŵ���
														else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
															{
																withdrawTadi
																	.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE);
															}
														*/
														
														// ��Ϣ����
														withdrawTadi.setCommonOperation(false);
														lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
														if (lsubPayInterestID < 0)
														{
															throw new IException("����֧ȡʧ�ܣ�");
														}
														log.info("-------------����֧ȡ����---------");
													}
												}
												log.info("------------���������ѻ�Ʒ�¼��ʼ--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												//param.setPayInterestAccountID(lsubPayInterestID);
												param.setPayCommissionAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setCommissionFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												
												//param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
												
												//ί�д���
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_COMMISION_FEE);
												}
												/*
													else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_COMMISION_FEE);
													}
												*/
												
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												
												log.info("------------���������ѻ�Ʒ�¼����--------------");
											}
										}
										log.info("-------------���������ѽ�Ϣ����---------");
									}
								}
		
								//����
								if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									//}
									
									log.info("-----------�жϸ����Ƿ����0------------------");
									if (info.getCompoundInterest() > 0)
									{
										log.info("-------------�������ʼ��Ϣ---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------������㸴����ʼ---------");
											long flag = -1;
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setCompoundInterest(info.getCompoundInterest());
											
											if(availableBalance>0&&availableBalance<info.getCompoundInterest()&&settmentInfo.isClearPartInterest())
											{
												clearLoanInfo.setRealCompoundInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												clearLoanInfo.setRealCompoundInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));	
											}
											info.setRealInterest(clearLoanInfo.getRealCompoundInterest());
											clearLoanInfo.setClearInterest(true);
											//�����棬�ҽ�Ϣ����<ִ�����ڣ�����õ�����Ϣ�����
											if (Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getCreateDate()))
											{
												log.info("-------------��ʼ��������---------");
												if(info.getInterestType()==SETTConstant.InterestFeeType.COMPOUNDINTEREST)
												{
													 flag = ib.accountInterestSettlelmentBackward( info.getAccountID()
															, info.getSubAccountID()
															, info.getCreateDate()
															, info.getRealInterest()
															, settmentInfo.getOfficeID()
															, settmentInfo.getCurrencyID()
															, Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())
															,SETTConstant.InterestFeeType.COMPOUNDINTEREST);
												}
												if (flag < 0)
												{
													throw new IException("��������ʧ��");
												}
												log.info("-------------�����������---------");
											}
											flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("������Ϣʧ��");
											}
											log.info("-------------������㸴������---------");
											log.info("-------------���濪ʼ---------");
											//�����Ϣ��¼
											
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
											}
											else 
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
											}
	                                        //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)										
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("��Ϣ����ʧ�ܣ���");
											}
											log.info("-------------�������---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
											{
												//ί�д���
												log.info("----------�ж�ί�д����Ƿ����ո�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û�и�Ϣ�˻�");
													}
													if (info.getRecieveInterestAccountID() < 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û����Ϣ�˻�");
													}
												}
												log.info("----------�ж���Ӫ�����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(��Ӫ����)û�и�Ϣ�˻�");
													}
												}
		
												log.info("----------�ж����Ŵ����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) 
												{
													if (info.getPayInterestAccountID() <= 0) 
													{
														throw new IException("�˻�" + info.getAccountNo() + "(���Ŵ���)û�и�Ϣ�˻�");
													}
												}
												
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------����֧ȡ��ʼ---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
		
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else 
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													//��Ϣ����
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("����֧ȡʧ�ܣ�");
													}
													log.info("-------------����֧ȡ����---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------���ڴ��뿪ʼ---------");
		
													//accountDetailInfo.setAmount(info.getInterest());
													//accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("���ڴ���ʧ�ܣ�");
													}
													log.info("-------------���ڴ������---------");
												}
		
												log.info("------------���ɸ�����Ʒ�¼��ʼ--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(saveInfo.getSubAccountID());
												param.setPayAccountID(saveInfo.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												param.setPayInterestAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setCompoundInterest(saveInfo.getInterest());
												param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getInterest(),2) - UtilOperation.Arith.round(saveInfo.getInterestTax(),2));
												param.setOfficeID(saveInfo.getOfficeID());
												param.setCurrencyID(saveInfo.getCurrencyID());
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													param.setList(list);
												}
												else
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("------------���ɸ�����Ʒ�¼����--------------");
											}
										}
										log.info("-------------�������Ϣ����---------");
		
									}
								}
		
								//��Ϣ
								if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
								{
									//if (preDrawInterestInfo.getEDate().compareTo(Env.getSystemDate(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID())) == 0)
									//{
									//		throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
									//		+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ���Ϣ");
									//}
		
									log.info("---------�жϷ�Ϣ�Ƿ�>0------------------");
									if (info.getForfeitInterest() > 0)
									{
										log.info("-------------���Ϣ��ʼ��Ϣ---------");
										if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
										{
											log.info("-------------������㷣Ϣ��ʼ---------");
											ClearLoanAccountInterestConditionInfo clearLoanInfo = new ClearLoanAccountInterestConditionInfo();
											clearLoanInfo.setAccountID(info.getAccountID());
											clearLoanInfo.setSubAccountID(info.getSubAccountID());
											clearLoanInfo.setInterestDate(info.getCreateDate());
											clearLoanInfo.setOverDueInterest(info.getForfeitInterest());
											clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
		
											if(availableBalance>0&&availableBalance<info.getForfeitInterest()&&settmentInfo.isClearPartInterest())
											{
												clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(availableBalance, 2));
											}
											else
											{
												clearLoanInfo.setRealOverDueInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));	
											}
											info.setRealInterest(clearLoanInfo.getRealOverDueInterest());
											clearLoanInfo.setClearInterest(true);
											
											long flag = ib.clearLoanAccountInterest(clearLoanInfo);
											if (flag < 0)
											{
												throw new IException("��Ϣ��Ϣʧ��");
											}
											log.info("-------------������㷣Ϣ����---------");
											log.info("-------------���濪ʼ---------");
											// �����Ϣ��¼
											
											/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
											if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
											}
											else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
											}
											else
											{
												settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
											}
	                                       //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
											InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
											//�������ѡ�����ɻ�Ʒ�¼��Ҳ��Ҫ��������ָ�� add 2009-03-24
											sTransNo[i] = saveInfo.getTransNo();
											Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
											long l = dao.add(conInternal, saveInfo);
											if (l < 0)
											{
												throw new IException("��Ϣ����ʧ�ܣ���");
											}
											log.info("-------------�������---------");
											if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
											{
												// ί�д���
												log.info("----------�ж�ί�д����Ƿ����ո�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
												{
													if (info.getPayInterestAccountID() < 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û�и�Ϣ�˻�");
													}
													if (info.getRecieveInterestAccountID() < 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(ί�д���)û����Ϣ�˻�");
													}
												}
												log.info("----------�ж���Ӫ�����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(��Ӫ����)û�и�Ϣ�˻�");
													}
												}
												log.info("----------�ж����Ŵ����Ƿ��и�Ϣ�˻�------------");
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													if (info.getPayInterestAccountID() <= 0)
													{
														throw new IException("�˻�" + info.getAccountNo() + "(���Ŵ���)û�и�Ϣ�˻�");
													}
												}
												if (info.getPayInterestAccountID() > 0)
												{
													log.info("-------------����֧ȡ��ʼ---------");
		
													// accountDetailInfo.setAmount(info.getInterest());
													// accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo withdrawTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
															saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_WITHDRAW);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														withdrawTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													
													// ��Ϣ����
													withdrawTadi.setCommonOperation(false);
													lsubPayInterestID = aco.withdrawCurrent(withdrawTadi, conInternal);
													if (lsubPayInterestID < 0)
													{
														throw new IException("����֧ȡʧ�ܣ�");
													}
													log.info("-------------����֧ȡ����---------");
												}
												if (info.getRecieveInterestAccountID() > 0)
												{
													log.info("-------------���ڴ��뿪ʼ---------");
		
													// accountDetailInfo.setAmount(info.getInterest());
													// accountDetailInfo.setTransAccountID(info.getAccountID());
													TransAccountDetailInfo depositTadi = transferInterestQueryResultInfoToTransAccountDetailInfo(
															saveInfo, info, saveInfo.getTransNo(), INTEREST_LOAN_DEPOSIT);
													if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
													}
													else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													}
													else
													{
														depositTadi.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
													}
													lsubReceiveInterestID = aco.depositCurrent(depositTadi, conInternal);
													if (lsubReceiveInterestID < 0)
													{
														throw new IException("���ڴ���ʧ�ܣ�");
													}
													log.info("-------------���ڴ������---------");
												}
												log.info("------------���ɷ�Ϣ��Ʒ�¼��ʼ--------------");
												GenerateGLEntryParam param = new GenerateGLEntryParam();
												param.setReceiveAccountID(info.getSubAccountID());
												param.setPayAccountID(info.getSubAccountID());
												param.setReceiveInterestAccountID(lsubReceiveInterestID);
												param.setPayInterestAccountID(lsubPayInterestID);
												param.setPrincipalOrTransAmount(saveInfo.getInterest());
												param.setTotalInterest(saveInfo.getInterest());
												param.setOverFee(saveInfo.getInterest());
												param.setOfficeID(settmentInfo.getOfficeID());
												param.setCurrencyID(settmentInfo.getCurrencyID());
												param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getInterest(),2) - UtilOperation.Arith.round(saveInfo.getInterestTax(),2));
												if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);
												}
												else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_INTEREST);
													BankLoanQuery bankloanquery = new BankLoanQuery();
													ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
													param.setList(list);
												}
												else
												{
													param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);
												}
												param.setExecuteDate(saveInfo.getExecuteDate());
												param.setInterestStartDate(saveInfo.getInterestSettlementDate());
												param.setTransNo(saveInfo.getTransNo());
												param.setAbstractStr(saveInfo.getAbstract());
												param.setInputUserID(saveInfo.getInputUserID());
												boolean bFlag = glo.generateGLEntry(param, conInternal);
												//sTransNo[i] = param.getTransNo();
												log.info("------------���ɷ�Ϣ��Ʒ�¼����--------------");
											}
										}
										log.info("-------------���Ϣ��Ϣ����---------");
									}
								}
							}
						}
						if(bIsValid)
						{
							Log.print("*******************��ʼ�������л�����Ϣָ�������**************************");

							// �������
							Collection coll = new ArrayList();
								try
								{
									CreateInstructionParam instructionParam = new CreateInstructionParam();
									
									if (accountTypeInfo != null) 
									{
										/**
										 * �޷����ί�滧���˻�����
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)
										{
											// ί�滧������ָ��
											continue;
										}
										*/
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT)//����
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);// ����
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)//��֤��
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_MARGIN);// ��֤��
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)//��Ӫ
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);// ��Ӫ
										}
										//WZC ADD
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)//����
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_INTEREST);// ����
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)//ί��
										{
											instructionParam.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_INTEREST);// ί��
										}
										
										instructionParam.setObjInfo((InterestQueryResultInfo) resultVec.elementAt(i));
										instructionParam.setOfficeID(settmentInfo.getOfficeID());
										instructionParam.setCurrencyID(settmentInfo.getCurrencyID());
										instructionParam.setCheckUserID(settmentInfo.getInputUserID());
										instructionParam.setTransNo(sTransNo[i]);
										//instructionParam.setBankType(((InterestQueryResultInfo)resultVec.elementAt(i)).bankID);
										instructionParam.setInputUserID(settmentInfo.getInputUserID());
										coll.add(instructionParam);
										log.debug("------�������л�����Ϣָ�����--------" + sTransNo[i]);
									}
								}
								catch (Exception e)
								{
									log.print("----------------------------��������ָ������쳣:sTransNo=" + sTransNo[i] + "----------------");
									log.print("----------------------------������������ָ��!----------------");
									throw e;
								}
							// ��������ָ�����
							IBankInstruction bankInstruction = BankInstructionFactory.getInstance();
							bankInstruction.createSpecialBankInstruction(coll);
						
						}
						
						info.setSuccess(true);
						result.addElement(info);
						conInternal.commit();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					//modify by kevin(������)2011-10-20
					log.error("�˻�"+info.getAccountNo()+"��Ϣ�����쳣��ԭ��"+IExceptionMessage.getExceptionMSG(e)) ;
					info.setSuccess(false);
				info.setStrPromptMessage(IExceptionMessage.getExceptionMSG(e));//��ʾ��Ϣ						
					result.addElement(info);
					conInternal.rollback();					
				}
				}
				
				log.info("-------------��Ϣ����---------");

				// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
				if (con == null)
				{
					try
					{
						conInternal.commit();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eCommit)
					{
						throw new Exception("�����ύ�쳣");
					}
				}
			}
			catch (Exception e)
			{
				// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
				// �׳�����֪ͨ�������֯�ߡ�
				e.printStackTrace();
				if (con == null)
				{
					try
					{
						log.info("****���ӻع�******");
						conInternal.rollback();
						conInternal.setAutoCommit(true);
					}
					catch (Exception eRollback)
					{
						throw new IException("��������쳣");
					}
				}
				throw e;
			}

		}
		catch (Exception eRollback)
		{
			//2009��2��20�� Boxu ���쳣������
			//throw new IException(eRollback.getMessage());
			throw new IException(eRollback.getMessage(), eRollback);
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					log.info("****�ر�����******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new IException("�ر�����ʧ�ܣ�");
				}
			}
		}

		return result;
	}

	private InterestSettmentInfo getSaveInfo(InterestSettmentInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// ��Ϣ��
		InterestSettmentInfo saveInfo = new InterestSettmentInfo();

		// ���׺�
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(settmentInfo.getOfficeID(), settmentInfo.getCurrencyID(),settmentInfo.getTransactionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("û��ȡ�����׺ţ�");
		}

		saveInfo.setTransNo(strTransNo);
		// ������Ϣ
		saveInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		saveInfo.setCurrencyID(settmentInfo.getCurrencyID());
		saveInfo.setOfficeID(settmentInfo.getOfficeID());
		// saveInfo.setTransactionTypeID(settmentInfo.getTransactionTypeID());
		saveInfo.setInputUserID(settmentInfo.getInputUserID());
		saveInfo.setAbstract(settmentInfo.getAbstract());
		saveInfo.setCheckAbstract(settmentInfo.getCheckAbstract());
		saveInfo.setIsSave(settmentInfo.getIsSave());
		saveInfo.setIsKeepAccount(settmentInfo.getIsKeepAccount());
		saveInfo.setIsSuccess(settmentInfo.getIsSuccess());
		saveInfo.setFaultReason(settmentInfo.getFaultReason());
		saveInfo.setTransInterestFeeID(settmentInfo.getTransInterestFeeID());
		// saveInfo.setExecuteDate(
		// Env.getSystemDate(settmentInfo.getOfficeID(),
		// settmentInfo.getCurrencyID()));
		saveInfo.setExecuteDate(settmentInfo.getExecuteDate());

		// ������Ϣ
		saveInfo.setAccountID(info.getAccountID());
		saveInfo.setAccountTypeID(info.getAccountTypeID());
		saveInfo.setSubAccountID(info.getSubAccountID());
		saveInfo.setInterestSettlementDate(info.getCreateDate());
		saveInfo.setInterestStartDate(info.getStartDate());
		saveInfo.setInterestEndDate(info.getEndDate());
		saveInfo.setInterestDays(info.getDays());
		saveInfo.setBaseBalance(info.getBalance());
		saveInfo.setRate(info.getInterestRate());
		saveInfo.setInterestType(info.getInterestType());

		// �������
		saveInfo.setNegotiateBalance(info.getNegotiateBalance());
		saveInfo.setNegotiateInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
		saveInfo.setNegotiateRate(info.getNegotiateInterestRate());
		saveInfo.setNegotiatePecisionInterest(info.getNegotiateInterest());

		// ��Ϣ˰��
		saveInfo.setInterestTax(info.getInterestTaxCharge());
		saveInfo.setInterestTaxRate(info.getInterestTaxRate());

		// ������Ϣû���ո�Ϣ�˻���
		// ������Ϣ
		if (settmentInfo.getOperationType() != SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			// �ո�Ϣ�˻���
			saveInfo.setReceiveInterestAccountID(info.getRecieveInterestAccountID());
			saveInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		}

		// ���⴦��
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.INTEREST)
			{
				throw new IException("ѡ���˻����ܼ��ᣡ");
			}
		}

		// ����������Ϣ
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				throw new IException("ѡ���˻����ܳ������ᣡ");
			}
		}

		// ������
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getAssuranceCharge(), 2));
			saveInfo.setPecisionInterest(info.getAssuranceCharge());
		}

		// ������
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getHandlingCharge(), 2));
			saveInfo.setPecisionInterest(info.getHandlingCharge());
		}

		// ����
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getCompoundInterest());
		}

		// ��Ϣ
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getForfeitInterest());
		}

		// ��Ϣ
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getInterest());
		}

		// ������Ϣ
		// ��Ϣ����
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
		{
			// info.getDrawingInterest() ��ż�����Ϣ

			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getDrawingInterest(), 2));
			saveInfo.setPecisionInterest(info.getDrawingInterest());
		}

		// ��������
		// ���ݡ��˻����͡�������Ϣ���ü���ķ������͡��͡���Ϣ���ý���Ĳ������͡������Ϣ��������
		saveInfo.setTransactionTypeID(SETTConstant.TransactionType.getTransactionType(saveInfo.getAccountTypeID(), saveInfo.getInterestType(), saveInfo.getOperationType()));

		saveInfo.setAbstract(getAbstract(saveInfo, info));

		return saveInfo;
	}
     
	private InterestSettmentInfo getSaveInfo(Connection con,InterestSettmentInfo settmentInfo, InterestQueryResultInfo info) throws IException, Exception
	{
		// ��Ϣ��
		InterestSettmentInfo saveInfo = new InterestSettmentInfo();

		// ���׺�
		UtilOperation uo = new UtilOperation();

		String strTransNo = "";
		try
		{
			strTransNo = uo.getNewTransactionNo(con,settmentInfo.getOfficeID(), settmentInfo.getCurrencyID(),settmentInfo.getTransactionTypeID());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("û��ȡ�����׺ţ�");
		}

		saveInfo.setTransNo(strTransNo);
		// ������Ϣ
		saveInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
		saveInfo.setCurrencyID(settmentInfo.getCurrencyID());
		saveInfo.setOfficeID(settmentInfo.getOfficeID());
		// saveInfo.setTransactionTypeID(settmentInfo.getTransactionTypeID());
		saveInfo.setInputUserID(settmentInfo.getInputUserID());
		saveInfo.setAbstract(settmentInfo.getAbstract());
		saveInfo.setCheckAbstract(settmentInfo.getCheckAbstract());
		saveInfo.setIsSave(settmentInfo.getIsSave());
		saveInfo.setIsKeepAccount(settmentInfo.getIsKeepAccount());
		saveInfo.setIsSuccess(settmentInfo.getIsSuccess());
		saveInfo.setFaultReason(settmentInfo.getFaultReason());
		saveInfo.setTransInterestFeeID(settmentInfo.getTransInterestFeeID());
		// saveInfo.setExecuteDate(
		// Env.getSystemDate(settmentInfo.getOfficeID(),
		// settmentInfo.getCurrencyID()));
		saveInfo.setExecuteDate(settmentInfo.getExecuteDate());

		// ������Ϣ
		saveInfo.setAccountID(info.getAccountID());
		saveInfo.setAccountTypeID(info.getAccountTypeID());
		saveInfo.setSubAccountID(info.getSubAccountID());
		saveInfo.setInterestSettlementDate(info.getCreateDate());
		saveInfo.setInterestStartDate(info.getStartDate());
		saveInfo.setInterestEndDate(info.getEndDate());
		saveInfo.setInterestDays(info.getDays());
		saveInfo.setBaseBalance(info.getBalance());
		saveInfo.setRate(info.getInterestRate());
		saveInfo.setInterestType(info.getInterestType());

		// �������
		saveInfo.setNegotiateBalance(info.getNegotiateBalance());
		saveInfo.setNegotiateInterest(UtilOperation.Arith.round(info.getNegotiateInterest(), 2));
		saveInfo.setNegotiateRate(info.getNegotiateInterestRate());
		saveInfo.setNegotiatePecisionInterest(info.getNegotiateInterest());

		// ��Ϣ˰��
		saveInfo.setInterestTax(info.getInterestTaxCharge());
		saveInfo.setInterestTaxRate(info.getInterestTaxRate());

		// ������Ϣû���ո�Ϣ�˻���
		// ������Ϣ
		if (settmentInfo.getOperationType() != SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			// �ո�Ϣ�˻���
			saveInfo.setReceiveInterestAccountID(info.getRecieveInterestAccountID());
			saveInfo.setPayInterestAccountID(info.getPayInterestAccountID());
		}

		// ���⴦��
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.INTEREST)
			{
				throw new IException("ѡ���˻����ܼ��ᣡ");
			}
		}

		// ����������Ϣ
		if (settmentInfo.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
		{
			if (saveInfo.getInterestType() != SETTConstant.InterestFeeType.PREDRAWINTEREST)
			{
				throw new IException("ѡ���˻����ܳ������ᣡ");
			}
		}

		// ������
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getAssuranceCharge(), 2));
			saveInfo.setPecisionInterest(info.getAssuranceCharge());
		}

		// ������
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getHandlingCharge(), 2));
			saveInfo.setPecisionInterest(info.getHandlingCharge());
		}

		// ����
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getCompoundInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getCompoundInterest());
		}

		// ��Ϣ
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getForfeitInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getForfeitInterest());
		}

		// ��Ϣ
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
		{
			saveInfo.setOperationType(settmentInfo.getOperationType());
			if(info.getRealInterest()>0)
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getRealInterest(), 2));
			}
			else
			{
				saveInfo.setInterest(UtilOperation.Arith.round(info.getInterest(), 2));
			}
			saveInfo.setPecisionInterest(info.getInterest());
		}

		// ������Ϣ
		// ��Ϣ����
		if (saveInfo.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
		{
			// info.getDrawingInterest() ��ż�����Ϣ

			saveInfo.setOperationType(settmentInfo.getOperationType());
			saveInfo.setInterest(UtilOperation.Arith.round(info.getDrawingInterest(), 2));
			saveInfo.setPecisionInterest(info.getDrawingInterest());
		}

		// ��������
		// ���ݡ��˻����͡�������Ϣ���ü���ķ������͡��͡���Ϣ���ý���Ĳ������͡������Ϣ��������
		saveInfo.setTransactionTypeID(SETTConstant.TransactionType.getTransactionType(saveInfo.getAccountTypeID(), saveInfo.getInterestType(), saveInfo.getOperationType()));

		saveInfo.setAbstract(getAbstract(saveInfo, info));

		return saveInfo;
	}
	/*
	 * �õ�ժҪ
	 */
	private String getAbstract(InterestSettmentInfo info, InterestQueryResultInfo qRInfo)
	{

		String strAbstract = "";
        log.debug("---------�ж��˻�����------------");
		long accountTypeID = info.getAccountTypeID();
        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
        AccountTypeInfo accountTypeInfo = null;
        
        try 
        {
			accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
		} 
        catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if (accountTypeInfo != null) 
		{
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT || 
				 accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "���ڴ���Ϣ-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "���ڴ�����-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "���ڴ���������-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK )
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						strAbstract = "�������˻���Ϣ-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						strAbstract = "�������˻�����-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						strAbstract = "�������˻���������-" + DataFormat.formatDate(info.getExecuteDate());
						return strAbstract;
					}
				}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "׼�����˻���Ϣ-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "׼�����˻�����-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "׼�����˻���������-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "����˻���Ϣ-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "����˻�����-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "����˻���������-" + DataFormat.formatDate(info.getExecuteDate());
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "���ڴ�����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "���ڴ���������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY )
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "֪ͨ������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "֪ͨ����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
			{
				if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
				{
					strAbstract = "��֤�����Ϣ-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
				{
					strAbstract = "��֤�����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
				if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
				{
					strAbstract = "��֤���������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getFixedDepositNo();
					return strAbstract;
				}
			}
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
				accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
			{
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "ί�д���ᵣ����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "ί�д����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "ί�д����Ϣ-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "ί�д������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "ί�д���Ḵ��-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "ί�д���ᷣϢ" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "ί�д����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "��Ӫ����ᵣ����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "��Ӫ�����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "��Ӫ�����Ϣ-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "��Ӫ�������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "��Ӫ����Ḵ��-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "��Ӫ����ᷣϢ" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "��Ӫ�����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
				{
					if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
					{
						strAbstract = "���Ŵ���ᵣ����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
					{
						strAbstract = "���Ŵ����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
						{
							strAbstract = "���Ŵ����Ϣ-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
						if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
						{
							strAbstract = "���Ŵ������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
					{
						strAbstract = "���Ŵ���Ḵ��-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
					{
						strAbstract = "���Ŵ���ᷣϢ" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
						{
							strAbstract = "���Ŵ����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
							return strAbstract;
						}
					}
				}
			}
			
			//�������ּ���ժҪ
			if(accountTypeInfo.getAccountGroupID()==SETTConstant.AccountGroupType.DISCOUNT)
			{
				if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
				{
					strAbstract = "���ִ���ᵣ����-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
				{
					strAbstract = "���ִ����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						strAbstract = "���ִ����Ϣ-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
					if (info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						strAbstract = "���ִ������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
				{
					strAbstract = "���ִ���Ḵ��-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
				{
					strAbstract = "���ִ���ᷣϢ" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
					return strAbstract;
				}
				if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
				{
					if (info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						strAbstract = "���ִ����������-" + DataFormat.formatDate(info.getExecuteDate()) + "-" + qRInfo.getContractNo() + "(" + qRInfo.getPayFormNo() + ")";
						return strAbstract;
					}
				}
			}
		}

		return strAbstract;
	}

	/**
	 * ��������Ϣ(ɾ��)��
	 * 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultInfo ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int delInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		log.info("-------------���뷴��Ϣģ��---------");
		int nResult = 0; // ����ֵ
		long subAccountStatus = -1;

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
			conInternal.setAutoCommit(false);
		}

		try
		{
			// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			// �����÷�������������һ����ɲ��֡�
			AccountOperation aco = new AccountOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);
			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			UtilOperation uo = new UtilOperation(conInternal);

			log.info("-------------��ʼɾ��---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestSettmentInfo infoResult = new InterestSettmentInfo();
				infoResult = (InterestSettmentInfo) resultVec.elementAt(i);

				InterestSettmentInfo info = new InterestSettmentInfo();

				info = dao.findByID(conInternal, infoResult.getID());
				
				//������˻�״̬
				subAccountStatus = subAccountDao.findStatusByID(info.getSubAccountID());
				if(subAccountStatus == SETTConstant.SubAccountStatus.FINISH)
				{
					throw new IException("�˻�״̬Ϊ"+SETTConstant.SubAccountStatus.getName(subAccountStatus)+", ���ܽ���ɾ������");
				}
				System.out.println("ִ���գ�"+info.getExecuteDate());
				System.out.println("�����գ�"+Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
				if ( info.getExecuteDate().after( Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()) )
				  || Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()).after(info.getExecuteDate()) )
				{
					throw new IException("ֻ��ɾ��ϵͳ�����յ���ļ�¼");
				}

				//Ч��ɾ��˳��,���밴����˳�����ɾ��,����ɾ����һ�β���
				InterestSettmentInfo interestInfo = new InterestSettmentInfo();
				interestInfo = dao.findOperation(conInternal, info);
				if(interestInfo.getTransNo().length()>0 && interestInfo.getOperationType()!=-1)
				{
					throw new IException("����ɾ�����׺�Ϊ "+interestInfo.getTransNo()+" �� "+SETTConstant.InterestOperateType.getName(interestInfo.getOperationType())+" ����");
				}
					
				// InterestAccountIDInfo interestAccountIDInfo = null;
				// interestAccountIDInfo =
				// ib.getInterestAccountID(info.getAccountID(),info.getSubAccountID(),info.getInterestType());
				log.debug(UtilOperation.dataentityToString(info));

		        log.debug("---------�ж��˻�����------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        
		        try 
		        {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} 
		        catch (SQLException e) 
		        {
					e.printStackTrace();
				}
		        
				if (accountTypeInfo != null) 
				{
					// ����֮���л��ڴ�����֧ȡ
					if (info.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE)
					{
						// �����Ϣ�˻��Ŵ���0
						if (info.getPayInterestAccountID() > 0)
						{
							log.info("-------------����ȡ��֧ȡ��ʼ------");
	
							// �Ƿ�����˻�������
							// �Ƿ���������˻�������
							TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()));
							}
	
							// �Ƿ�����˻�������
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								accountDetailInfo.setAmount(info.getInterest());
							}
	
							accountDetailInfo.setTransAccountID(info.getPayInterestAccountID());
							// ��Ϣʹ��
							accountDetailInfo.setCommonOperation(false);
							aco.cancelWithdrawCurrent(accountDetailInfo, conInternal);
	
							log.info("-------------����ȡ��֧ȡ����---------");
						}
	
						// �����Ϣ�˻��Ŵ���0
						if (info.getReceiveInterestAccountID() > 0)
						{
							log.info("-------------����ȡ�����뿪ʼ---------");
							// ���ڴ���
							TransAccountDetailInfo accountDetailInfo = new TransAccountDetailInfo();
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
								||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
								||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
									)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.add(info.getInterest(), info.getNegotiateInterest()));
							}
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								accountDetailInfo.setAmount(UtilOperation.Arith.sub(info.getInterest(), info.getInterestTax()));
							}
	
							accountDetailInfo.setTransAccountID(info.getReceiveInterestAccountID());
							aco.cancelDepositCurrent(accountDetailInfo, conInternal);
	
							log.info("-------------����ȡ���������---------");
						}
					}
	
					// ������Ϣ����
					if (info.getExecuteDate().after(info.getInterestSettlementDate()))
					{
						if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
						{
							log.info("-------------ɾ��ʱ�������ʼ------------");
	
							ib.accountInterestSettlelmentBackward(info.getAccountID(), info.getSubAccountID(), info
									.getInterestSettlementDate(), (-1)
									* (UtilOperation.Arith.add(info.getInterest(), info.getNegotiatePecisionInterest())), info
									.getOfficeID(), info.getCurrencyID(), info.getExecuteDate(),SETTConstant.InterestFeeType.INTEREST);
	
							log.info("-------------ɾ��ʱ�����������------------");
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							log.info("-------------ɾ��ʱ�������ʼ------------");
	
							ib.accountInterestSettlelmentBackward(info.getAccountID(), info.getSubAccountID(), info
									.getInterestSettlementDate(), (-1)
									*info.getInterest(), info
									.getOfficeID(), info.getCurrencyID(), info.getExecuteDate(),SETTConstant.InterestFeeType.COMPOUNDINTEREST);
	
							log.info("-------------ɾ��ʱ�����������------------");
						}
					}
	
					// ���һ����֤������ add by wjliu --2007-4-9
					// �Ƿ�����˻�������
					// �Ƿ�֤���˻�������
					// ��ӱ�����׼���𡢲���˻��飬����ͬ����
					if ( (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							|| accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING
					   || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) 
					   && info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT )
					{
						log.info("------------���ڷ���Ϣ��ʼ----------");
	
						ib.clearCurrentDepositAccountInterestReverse(
								  info.getAccountID()
								, info.getSubAccountID()
								, info.getInterestStartDate()
								, info.getPecisionInterest()
								, info.getNegotiatePecisionInterest() );
	
						log.info("------------���ڷ���Ϣ����----------");
					}
					
					//�Ƿ�����˻�������
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.INTERESTSETTLEMENT)
					{
						log.info("------------�����Ϣ��ʼ----------");
	
						ClearLoanAccountInterestConditionInfo loanInfo = new ClearLoanAccountInterestConditionInfo();
						loanInfo.setAccountID(info.getAccountID());
	
						if (info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
						{
							loanInfo.setInterest(info.getPecisionInterest());
							loanInfo.setRealInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMMISION)
						{
							loanInfo.setCommision(info.getPecisionInterest());
							loanInfo.setRealCommission(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
						{
							loanInfo.setCompoundInterest(info.getPecisionInterest());
							loanInfo.setRealCompoundInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.FORFEITINTEREST)
						{
							loanInfo.setOverDueInterest(info.getPecisionInterest());
							loanInfo.setRealOverDueInterest(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.ASSURE)
						{
							loanInfo.setSuretyFee(info.getPecisionInterest());
							loanInfo.setRealSuretyFee(info.getInterest());
						}
						if (info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
						{
							loanInfo.setInterestReceivable(info.getPecisionInterest());
							loanInfo.setRealInterestReceivable(info.getInterest());
						}
	
						loanInfo.setInterestDate(info.getInterestStartDate());
						loanInfo.setClearInterestDate(info.getInterestSettlementDate());
	
						loanInfo.setSubAccountID(info.getSubAccountID());
	
						long lFlag = ib.clearLoanAccountInterestReverse(loanInfo);
	
						if (lFlag < 0)
						{
							throw new IException("�����˻�����Ϣʧ��");
						}
	
						log.info("------------�����Ϣ����----------");
					}
					
					// ���������Ϣɾ��
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------������Ὺʼ----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------����������----------");
					}
					//�����������ɾ��
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) && 
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------����������Ὺʼ----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, -info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								
								, info.getAccountTypeID());
	
						log.info("------------��������������----------");
					}
					// �Ƿ����˻�������
					// �Ƿ�֪ͨ�˻�������
					// �Ƿ�֤���˻�������
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) &&
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------���ڷ����Ὺʼ----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------���ڷ��������----------");
					}
					// �Ƿ����˻�������
					// �Ƿ�֪ͨ�˻�������
					// �Ƿ�֤���˻�������
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN) &&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------���ڷ��������Ὺʼ----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								, info.getAccountTypeID());
	
						log.info("------------���ڷ������������----------");
					}
	
					// ���ڷ�����
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							&& 
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------���ڷ����Ὺʼ----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								
								//, preDrawInfo.getPredrawInterest() - info.getInterest()
								, UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
										UtilOperation.Arith.add(
												UtilOperation.Arith.round( info.getInterest(), 2), UtilOperation.Arith.round( info.getNegotiateInterest(), 2)
												)
										)
								
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------���ڷ��������----------");
					}
	
					// ���ڷ�����
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING)
							&&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------���ڷ��������Ὺʼ----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								
								//2008��2��29�� Add Boxu ����Э����Ϣ
								//, UtilOperation.Arith.add(preDrawInfo.getPredrawInterest(), info.getInterest())
								//, UtilOperation.Arith.add(preDrawInfo.getPredrawInterest(),
								//		UtilOperation.Arith.add(
								//				info.getInterest(), info.getNegotiateInterest()
								//				)
								//		)
								, info.getInterest()
								
								//ʱ��Ӧ��ȡ
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								, info.getInterestEndDate()
								
								, info.getAccountTypeID());
	
						log.info("------------���ڷ������������----------");
					}
	
					// ���ַ�����
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT &&
						info.getOperationType() == SETTConstant.InterestOperateType.PREDRAWINTEREST)
					{
						log.info("------------���ַ����Ὺʼ----------");
	
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
	
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, preDrawInfo.getPredrawInterest() - UtilOperation.Arith.round( info.getInterest(), 2)
								, info.getInterestStartDate()
								, info.getAccountTypeID());
	
						log.info("------------���ַ��������----------");
					}
	
					// ���ַ�����
					if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT &&
						info.getOperationType() == SETTConstant.InterestOperateType.CLEANPREDRAWINTEREST)
					{
						log.info("------------���ַ��������Ὺʼ----------");
						SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
						preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
						
						// �޸����˻���Ϣ
						log.info("-------------�޸����˻���Ϣ---------");
						subAccountDao.updatePreDrawInterestAndDateReverse(
								info.getSubAccountID()
								, -info.getInterest()
								
								, info.getInterestEndDate()
								//, Env.getSystemDate(conInternal, info.getOfficeID(), info.getCurrencyID())
								
								, info.getAccountTypeID());
	
						log.info("------------���ַ������������----------");
					}
	
					log.info("-----------ɾ����ϸ�˿�ʼ--------");
					aco.deleteTransAccountDetail(info.getTransNo(), conInternal);
					log.info("-----------ɾ����ϸ�˽���--------");
	
					log.info("-----------ɾ����Ʒ�¼��ʼ--------");
					glo.deleteGLEntry(info.getTransNo(), conInternal);
					log.info("-----------ɾ����Ʒ�¼����--------");
	
					log.info("-----------ɾ����Ϣ��¼��ʼ--------");
					long l = dao.updateStatus(conInternal, info.getID(), SETTConstant.TransactionStatus.DELETED);
	
					if (l < 0)
					{
						throw new IException("ɾ����Ϣ��¼ʧ��");
					}
	
					// ɾ���ɹ�������Ϣ����������ϸ��Ϣ��ִ��״̬
					if (info.getTransInterestFeeID() > -1)
					{
						Sett_InterestFeeSettingDetailDAO settingDetailDAO = new Sett_InterestFeeSettingDetailDAO(conInternal);
	
						settingDetailDAO.updateIsSuccessStatus(info.getTransInterestFeeID(), SETTConstant.BooleanValue.ISFALSE);
					}
	
					log.info("-----------ɾ����Ϣ��¼����--------");
	
				}
			}
			log.info("-------------ɾ������---------");
			// ���н�Ϣ������
			// result = dao.selectSettlementRecords(conInternal, queryInfo);
			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯��
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("----------����ع�------------");
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("��������쳣");
				}
			}
			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return nResult;
	}

	/**
	 * ����������Ϣ�� ����ֻ�����Ϣ,�����ͷ�Ϣ���������ᡣ ���ȣ������˻����е�ÿ���˻��Ŷ�������м��ᣨ��ǰ���ҵ���н��й��жϣ��� ��Σ����˻����е�ÿ����Ӫ�����˻������м����������¼�����Ƿ�ɹ��ı�־��
	 * �ٴΣ����˻����е�ÿ�����ڴ���˻���ֱ�ӽ��м��������
	 * 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultInfo ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int drawingInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo) throws Exception
	{

		int nResult = 0; // ����ֵ

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
			conInternal.setAutoCommit(false);
		}

		try
		{
			// InterestCalculation interestCalculation = new
			// InterestCalculation();

			// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			// �����÷�������������һ����ɲ��֡�
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);

			log.info("-------------��ʼ����---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();

				info = (InterestQueryResultInfo) resultVec.elementAt(i);
				//����ʱʵ����Ϣ����Ӧ����Ϣ
				info.setRealInterest(info.getInterest());
		        log.debug("---------�ж��˻�����------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        
		        try 
		        {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} 
		        catch (SQLException e) 
		        {
					e.printStackTrace();
				}
		        
				if (accountTypeInfo != null) 
				{
					/**
					 * ˵�� �Ƿ�����˻������� �����ʻ�����Ϊ: 1.��Ӫ����
					 * 2.ί�д��� 3.���ִ��� 4.��������
					 */
					// ί�д����˻������Ϳ��Լ���
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST // ��Ϣ
					)
					{
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						log.info("---------------�ж���Ϣ�Ƿ����0-----------------");
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
										+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							// ��Ӫ����ֻ�������һ��
							log.info("---------------�ж���Ϣ�Ƿ����0-----------------");
							/**
							 * ��Ҫ�޸ĵ�˵��: ������Զ�μ��� ������������һ����ֻ�������һ�� preDrawInfo1.getPredrawDate() �������� info.getCreateDate()
							 * ҳ��¼��"��Ϣ��"
							 */

							// �����Ϣ��¼
							log.info("----------���濪ʼ---------");
	
							// info.getInterestType() ==
							// SETTConstant.InterestFeeType.INTEREST //��Ϣ
							
							/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
							settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
							}
                            //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)

							InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);

							// ������Ϣ
							// ��Ϣ���ü���ķ�������
							saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
							// ��Ӫ�������Ӧ����Ϣ����������
							saveInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
							{
								// ���ִ������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
							{
								// ί�д������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
							}
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								// ���Ŵ������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
							}
	
							log.debug(UtilOperation.dataentityToString(saveInfo));
	
							// ��������Ϣ��sett_TransInterestSettlement
							long l = interestDao.add(conInternal, saveInfo);
	
							if (l < 0)
							{
								throw new IException("��Ϣ����ʧ��");
							}
	
							log.info("----------�������---------");
							log.info("-------------������Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								// AL_MPREDRAWINTEREST sett_SubAccount�� ������Ϣ
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								// �޸����˻���Ϣ
								log.info("-------------�޸����˻���Ϣ---------");
	
								// �޸�sett_SubAccount���еļ�����Ϣ AF_mPreDrawInterest
								// ���ļ�����Ϣ���������
								// preDrawInfo.getPredrawInterest()
								// ��sett_SubAccount�е��ֶ�AL_MPREDRAWINTEREST������Ϣ
								// saveInfo.getInterest()
								// ��sett_DailyAccountBalance�е��ֶ�MINTEREST�ۼ���Ϣ/�ۼ�������Ϣ
								subAccountDao.updatePreDrawInterestAndDate( info.getSubAccountID()
										, UtilOperation.Arith.round(UtilOperation.Arith.add(saveInfo.getInterest()
										, preDrawInfo.getPredrawInterest()), 2)
										, info.getCreateDate(), info.getAccountTypeID() );
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
	
									// ������Ϣ���ɿ�
									param.setPreDrawInterest(saveInfo.getInterest());
	
									// ����/���׽��
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									// ��Ϣ�ϼƣ��ɿ�
									param.setTotalInterest(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// ��Ӫ�������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										// ���ִ������Ӧ����Ϣ����������
										param.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										// ί�д������Ӧ����Ϣ����������
										param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										// ���Ŵ������Ӧ����Ϣ����������
										param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
										
										BankLoanQuery bankloanquery = new BankLoanQuery();
										ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
										param.setList(list);
									}
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
	
							log.info("-------------������Ϣ�������---------");
						}
					}
					// �Ƿ����˻�������
					// �Ƿ�֪ͨ�˻�������
					else if (( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY ) 
							&& info.getInterestType() == SETTConstant.InterestFeeType.INTEREST )
					{
						log.info("---------�ж���Ϣ�Ƿ����0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0 && preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------������Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ��Ϣ����Ϊ: ������Ϣ
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// ���ڴ�����Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									// ֪ͨ������Ӧ����Ϣ����������
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��Ϣ����ʧ��");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �޸����˻���Ϣ
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
	
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
	
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setTotalInterest(UtilOperation.Arith.round(
											UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
	
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// ���ڴ�����Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
									{
										// ֪ͨ������Ӧ����Ϣ����������
										param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
	
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									param.setInputUserID(saveInfo.getInputUserID());
									param.setCheckUserID(saveInfo.getInputUserID());
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------������Ϣ�������---------");
						}
					}
	
					// �Ƿ�֤���˻�������
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------�ж���Ϣ�Ƿ����0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
										+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------��֤����Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ������Ϣ
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// ��֤�������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��֤���Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �޸����˻���Ϣ
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("-----��֤��---���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// ��֤�������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("------------��֤����Ϣ�������---------");
						}
					}
					
					// �Ƿ񱸸���׼�����˻�������
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK 
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)&&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------�ж���Ϣ�Ƿ����0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
										+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else //if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------������׼������Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ������Ϣ
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								//�����˻����������ý�������
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("������׼������ᱣ��ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �޸����˻���Ϣ
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("-----������׼�������---���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									//�����˻����������ý�������
									if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("------------��֤����Ϣ�������---------");
						}
					}
					
					// �Ƿ����˻�������
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING &&
						info.getInterestType() == SETTConstant.InterestFeeType.INTEREST)
					{
						log.info("---------�ж���Ϣ�Ƿ����0-------------");
	
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(),
								info.getAccountTypeID());
	
						if (DataFormat.formatDouble(info.getInterest()) == 0)
						{
							// ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0
									&& preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�"
										+ NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						else if (DataFormat.formatDouble(info.getInterest()) > 0)
						{
							log.info("-------------�����Ϣ���Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ������Ϣ
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								// ��֤�������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("�����ᱣ��ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �޸����˻���Ϣ
								subAccountDao.updatePreDrawInterestAndDate(info.getSubAccountID(), UtilOperation.Arith
										.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2), info
										.getCreateDate(), info.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("-----������---���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest(saveInfo.getInterest());
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setTotalInterest(UtilOperation.Arith.round(
										UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2));
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// ������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(),
											settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									// param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("------------��֤����Ϣ�������---------");
						}
					}
	
					/**
					 * ������ڼ��� �Ƿ�����˻�������
					 */
					else if ( accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
						   && info.getInterestType() == SETTConstant.InterestFeeType.INTEREST )
					{
						log.info("---------�ж���Ϣ�Ƿ����0-------------");
	
						/**
						 * ȡ������
						 */
						//info.setInterest(info.getDrawingInterest());
						//���ύ�׼�¼��ʼ����ȡ���Ὺʼ����
						//info.setStartDate(info.getDrawStartDate());
						//info.setDays(interestCalculation.getIntervalDays(
						//info.getStartDate(),
						//interestCalculation.getNextNDay(info.getEndDate(),1), 1)
						//);
						//����
						//long days = getIntervalDays(sDate, getNextNDay(eDate, 1),
						//INTERVALDAYSFLAG_FACTDAY);
						
						SubAccountPredrawInterestInfo preDrawInterestInfo = new SubAccountPredrawInterestInfo();
						preDrawInterestInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(info.getSubAccountID(), info.getAccountTypeID());
	
						if (UtilOperation.Arith.round(UtilOperation.Arith.add(
									UtilOperation.Arith.round(info.getInterest(),2),
									UtilOperation.Arith.round(info.getNegotiateInterest(),2)
								),2) == 0)
						{
							//ȡsett_SubAccount��ļ�����Ϣ�ֶ�
							if (preDrawInterestInfo.getPredrawInterest() > 0 && preDrawInterestInfo.getPredrawDate().equals(info.getCreateDate()))
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " �Ѿ�����");
							}
							else
							{
								throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ��ϢΪ�ղ��ܼ���");
							}
						}
						/*if (UtilOperation.Arith.round(UtilOperation.Arith.add(
								UtilOperation.Arith.round(info.getInterest(),2),
								UtilOperation.Arith.round(info.getNegotiateInterest(),2)
							),2) > 0)*/
						else
						{
							log.info("-------------������Ϣ���Ὺʼ---------");
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								//�����Ϣ��¼
								log.info("----------���濪ʼ---------");
								
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								saveInfo.setInterestType(SETTConstant.InterestFeeType.PREDRAWINTEREST);
	
								//���ڴ�����Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								//�޸����ʻ���Ϣ
								subAccountDao.updatePreDrawInterestAndDate(
										  info.getSubAccountID()
										
										/** Boxu Add 2008��2��28�� ������Ϣ=������Ϣ+Э����Ϣ **/
										//, UtilOperation.Arith.round(UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest()), 2)
										, UtilOperation.Arith.round(
												UtilOperation.Arith.add(
														UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2), preDrawInfo.getPredrawInterest())
														, UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2))
												, 2)
										  
										, info.getCreateDate()  //ҳ��¼���Ϣ��
	
										//�޸Ķ����ȥ�����ʱ��
										//, DataFormat.getNextDate(info.getEndDate())
	
										, info.getAccountTypeID() );
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									
									/** ������Ϣ=������Ϣ+Э����Ϣ **/
									param.setPreDrawInterest(UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2),UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2) ));
									param.setPrincipalOrTransAmount(UtilOperation.Arith.round(
											UtilOperation.Arith.add(
													UtilOperation.Arith.add(saveInfo.getInterest(), preDrawInfo.getPredrawInterest())
													, saveInfo.getNegotiateInterest())
											, 2));
									param.setTotalInterest(UtilOperation.Arith.round(
												UtilOperation.Arith.add(
													UtilOperation.Arith.add(UtilOperation.Arith.round(saveInfo.getInterest(), 2), preDrawInfo.getPredrawInterest())
													, UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2))
												, 2));
									
									param.setOfficeID(settmentInfo.getOfficeID());
									param.setCurrencyID(settmentInfo.getCurrencyID());
	
									// ���ڴ�����Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, settmentInfo.getOfficeID(), settmentInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr(saveInfo.getAbstract());
									param.setInputUserID(saveInfo.getInputUserID());
									// param.setCheckUserID(settmentInfo.getc);
									// param.setPrincipalType(lPrincipalType);
									// param.setInterestType(lInterestType);
									// param.setCommisionType(lCommissionType);
									// param.setEntryType(lEntryType);
									//modify by kevin(������)2012-04-01
									//��ҩ��ĿҪ��ѡ�Э����Ϣ����������
									param.setRemissionInterest(UtilOperation.Arith.round(saveInfo.getInterest(), 2));
									param.setReallyReceiveInterest(UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2));
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------������Ϣ�������---------");
						}
					}
					else
					{
						throw new IException("�˻���"
								+ info.getAccountNo()
								// + "�ſ:"
								+ (info.getPayFormNo() != null && info.getPayFormNo().length() > 0 ? " �ſ��"
										+ info.getPayFormNo() : "") + " �˻����ͣ�"
								+ SETTConstant.AccountType.getName(info.getAccountTypeID()) + " �޷�����");
					}
				}
			}

			log.info("-------------�������---------");

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					log.info("*****�ύ******");
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("*****�ع�******");
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("��������쳣");
				}
			}

			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					log.info("****�ر�����******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new Exception("�����쳣");
				}
			}
		}

		return nResult;
	}

	/**
	 * ����������Ϣ(����-��ͨ��Ŀ)�� ������Ի��ڴ���˻��� 1���жϽ�Ϣ���뵱ǰ�����Ƿ���ͬ�������ͬ�������²����� 2����ѯ���е�Ҫ������Ϣ�Ļ����˻��� 3��������Ϣ��
	 * 
	 * @param con ���ݿ����ӣ����ⲿ���룬never null.
	 * @param resultInfo ����Ҫ������˻������Ϣ��ʵ�壬never null.
	 * @return int value:
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int drawingInterestForICBC(Connection con, long lOfficeID, long lCurrencyID, Timestamp dtCreateDate)
			throws Exception
	{

		int nResult = 0; // ����ֵ
		Vector resultVec = null;
		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
			conInternal.setAutoCommit(false);
		}

		try
		{
			// ҵ���߼������磺����������ҵ�񷽷������ݷ��ʷ�����һ��Ҫ�� conInternal ��Ϊ��������Ҫ�����õķ�����
			// �����÷�������������һ����ɲ��֡�
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAccountDao = new Sett_SubAccountDAO(conInternal);

			Sett_CompoundInterestSettingDAO compoundinterestsettingDAO = new Sett_CompoundInterestSettingDAO(
					conInternal);
			long lIsSettlement = compoundinterestsettingDAO.findByCompoundInterestDate(lOfficeID, lCurrencyID,
					dtCreateDate);

			// ����������ǽ�Ϣ�գ�������Ϣ���ᴦ��
			if (lIsSettlement == 1)
			{
				// ��ѯ����Ҫ����Ļ�����Ϣ
				resultVec = (Vector) subAccountDao.findAllInterestForICBC(lOfficeID, lCurrencyID);

				log.info("-------------������ͨ��Ŀ--��ʼ����/��Ϣ---------");
				for (int i = 0; i < resultVec.size(); i++)
				{
					InterestQueryResultInfo info = new InterestQueryResultInfo();
					info = (InterestQueryResultInfo) resultVec.elementAt(i);

			        log.debug("---------�ж��˻�����------------");
					long accountTypeID = info.getAccountTypeID();
			        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
			        AccountTypeInfo accountTypeInfo = null;
			        try {
						accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (accountTypeInfo != null) {
						if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
							accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT)
						{
							if (info.getInterest() <= 0)
							{
								log.info("-------------������Ϣ���Ὺʼ---------");
								// �޸����˻���Ϣ
								subAccountDao.updatePreDrawInterestAndDateForICBC(info.getSubAccountID(), info
										.getInterest(), dtCreateDate, info.getAccountTypeID());
								log.info("-------------������Ϣ�������---------");
							}
							else
							{
								log.info("-------------�����Ϣ��Ϣ��ʼ---------");
								// �����˻����
	
								// ���ɽ�Ϣ��¼
								this.drawDepositInterestForICBC(conInternal, lOfficeID, lCurrencyID, info.getAccountID(),
										info.getSubAccountID(), info.getInterest(), dtCreateDate);
								// �޸����˻���Ϣ
								subAccountDao.updateDepositInterestForICBC(info.getSubAccountID(), dtCreateDate, info
										.getAccountTypeID());
								log.info("-------------�����Ϣ��Ϣ����---------");
							}
						}
						else
						{
							throw new IException("�˻�:" + info.getAccountNo() + "�˻����ʹ���");
						}
					}
				}
				log.info("-------------������ͨ��Ŀ--�������/��Ϣ---------");
			}

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					log.info("*****�ύ******");
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					log.info("*****�ع�******");
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("��������쳣");
				}
			}
			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					log.info("****�ر�����******");
					conInternal.close();
				}
				catch (Exception eClose)
				{
					throw new Exception("�����쳣");
				}
			}
		}

		return nResult;
	}

	/**
	 * ����������Ϣ��
	 * 
	 * @param con ���ݿ����ӣ��������ⲿ���룬���Ϊnull����ô�ڱ������ڲ����� ���ӣ����Ҹ÷���Ӧ�ø��������ά��������ֱ��ʹ�ü��ɡ�
	 * @param resultInfo[] ����Ҫ����������˻������Ϣ��ʵ���б�.
	 * @return int value
	 * @throws Excetion �κ�ϵͳ�쳣����ʱ��
	 */
	public int clearDrawingInterest(Connection con, Vector resultVec, InterestSettmentInfo settmentInfo)
			throws Exception
	{

		int nResult = 0; // ����ֵ

		// �ж��Ƿ�Ӧ���ڸ÷����ڲ���������������Ӳ���Ϊ NULL ���ͱ�ʾ�÷�������ά����������
		Connection conInternal = null;
		if (con == null)
		{
			try
			{
				conInternal = getConnection();
				conInternal.setAutoCommit(false);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("�޷�ȡ�����ݿ�����");
			}
		}
		else
		{
			conInternal = con;
			conInternal.setAutoCommit(false);
		}

		try
		{
			GeneralLedgerOperation glo = new GeneralLedgerOperation(conInternal);
			UtilOperation uo = new UtilOperation(conInternal);
			Sett_TransInterestSettlementDAO interestDao = new Sett_TransInterestSettlementDAO();
			Sett_SubAccountDAO subAcctontDao = new Sett_SubAccountDAO(conInternal);
			SubAccountAssemblerInfo saai = null;
			log.info("-------------��ʼ��������---------");

			for (int i = 0; i < resultVec.size(); i++)
			{
				InterestQueryResultInfo info = new InterestQueryResultInfo();
				info = (InterestQueryResultInfo) resultVec.elementAt(i);

		        log.debug("---------�ж��˻�����------------");
				long accountTypeID = info.getAccountTypeID();
		        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
		        AccountTypeInfo accountTypeInfo = null;
		        try {
					accountTypeInfo = sett_AccountTypeDAO.findByID(accountTypeID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (accountTypeInfo != null) 
				{
					// �Ƿ�����˻�������
					if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0---------------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------������Ϣ�������Ὺʼ---------");
	
							// �Ƿ�����˻�������
							if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN ||
								accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
							{
								if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									// �����Ϣ��¼
									log.info("----------���濪ʼ---------");
	
									/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
									}
                                    //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
									InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
									// ��Ӫ�������Ӧ����Ϣ����������
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
									{
										// ���ִ������Ӧ����Ϣ����������
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
									{
										// ί�д������Ӧ����Ϣ����������
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
									}
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
									{
										// ���Ŵ������Ӧ����Ϣ����������
										saveInfo.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
									}
	
									saveInfo.setInterest((-1)*saveInfo.getInterest());//���뽻�׼�¼Ϊ��
									long l = interestDao.add(conInternal, saveInfo);
									saveInfo.setInterest((-1)*saveInfo.getInterest());
	
									if (l < 0)
									{
										throw new IException("��Ϣ����ʧ�ܣ���");
									}
	
									log.info("----------�������---------");
	
									SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
	
									preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
									Timestamp dtInterestStart = null;
	
									dtInterestStart = interestDao.findInterestStartDate( conInternal
											, info.getSubAccountID()
											, SETTConstant.InterestFeeType.PREDRAWINTEREST
											, SETTConstant.InterestOperateType.PREDRAWINTEREST );
	
									if (dtInterestStart == null)
									{
										throw new IException("û��������������޷��������ᣡ");
									}
	
									//ȡ���˻��Ľ�Ϣ��
									saai = new SubAccountAssemblerInfo();
									saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
									
									// �޸����˻���Ϣ
									// preDrawInfo.getPredrawInterest() ���ʻ��еļ�����Ϣ
									// saveInfo.getInterest() ҳ����ʾ������Ϣ
									subAcctontDao.updatePreDrawInterestAndDateReverse( saveInfo.getSubAccountID()
											, UtilOperation.Arith.round(preDrawInfo.getPredrawInterest() -UtilOperation.Arith.round( saveInfo.getInterest(), 2), 2)
											
											, saai.getSubAccountCurrenctInfo().getClearInterestDate()
											
											, saveInfo.getAccountTypeID() );
	
									if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
									{
										log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
										GenerateGLEntryParam param = new GenerateGLEntryParam();
										param.setReceiveAccountID(info.getSubAccountID());
										param.setPayAccountID(info.getSubAccountID());
										// param.setRemissionInterest(saveInfo.getInterest());
	
										// ������Ϣ���ɿ�
										param.setPreDrawInterest((-1) * saveInfo.getInterest());
	
										// ����/���׽��
										param.setPrincipalOrTransAmount((-1)
												* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo
														.getInterest(), 2)), 2));
	
										// ��Ϣ�ϼƣ��ɿ�
										param.setTotalInterest((-1)
												* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
	
										param.setOfficeID(saveInfo.getOfficeID());
										param.setCurrencyID(saveInfo.getCurrencyID());
	
										// ��Ӫ�������Ӧ����Ϣ����������
										param.setTransactionTypeID(SETTConstant.TransactionType.TRUST_LOAN_PREDRAW_INTEREST);
	
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT)
										{
											// ���ִ������Ӧ����Ϣ����������
											param.setTransactionTypeID(SETTConstant.TransactionType.DISCOUNTACCRUAL);
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN)
										{
											// ί�д������Ӧ����Ϣ����������
											param.setTransactionTypeID(SETTConstant.TransactionType.CONSIGN_LOAN_PREDRAW_INTEREST);
										}
										if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT)
										{
											// ���Ŵ������Ӧ����Ϣ����������
											param.setTransactionTypeID(SETTConstant.TransactionType.YT_LOAN_PREDRAW_INTEREST);
											
											BankLoanQuery bankloanquery = new BankLoanQuery();
											ArrayList list =(ArrayList)bankloanquery.findByFormID(info.getPayFormID());
											param.setList(list);
										}
	
										param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(),
												saveInfo.getCurrencyID()));
										param.setInterestStartDate(saveInfo.getInterestSettlementDate());
										param.setTransNo(saveInfo.getTransNo());
										param.setAbstractStr(saveInfo.getAbstract());
	
										boolean bFlag = glo.generateGLEntry(param, conInternal);
	
										log.info("--------���ɻ�Ʒ�¼����---------");
									}
								}
							}
							log.info("-------------������Ϣ�����������---------");
						}
					}
					// �Ƿ����˻�������
					// �Ƿ�֪ͨ�˻�������
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED ||
						accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------������Ϣ�������Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ���ڴ�����Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
								if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
								{
									// ֪ͨ������Ӧ����Ϣ����������
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �õ��ϴν�Ϣ����
								Timestamp dtInterestStart = null;
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("û��������������޷��������ᣡ");
								}
	
								//ȡ���˻��Ľ�Ϣ��
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
								
								// �޸����˻���Ϣ
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// ���ڴ�����Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.FIXED_DEPOSIT_PREDRAW_INTEREST);
	
									if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY)
									{
										// ֪ͨ������Ӧ����Ϣ����������
										//modified by qhzhou 2008-02-20
										param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
										//saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_NOTIFY_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("��������");
									
									//added by qhzhou 2008-02-20
									param.setInputUserID(saveInfo.getInputUserID());
									param.setCheckUserID(saveInfo.getInputUserID());
									
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------������Ϣ�����������---------");
						}
					}
					// �Ƿ�֤���˻�������
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------��֤����Ϣ�������Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
								
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ��֤�������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��֤���Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �õ��ϴν�Ϣ����
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("û��������������޷��������ᣡ");
								}
								
								//ȡ���˻��Ľ�Ϣ��
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// �޸����˻���Ϣ
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("---��֤��-----���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// ��֤�������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.MARGIN_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("��������");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----��֤��---���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------��֤����Ϣ�����������---------");
						}
					}
					// �Ƿ񱸸���׼�����˻�������
					else if ((accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK
							||accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE) &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------������׼������Ϣ�������Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
								
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								//�����˻����������ý�������
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								//�����˻����������ý�������
								if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
								}
								else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
								{
									saveInfo.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
								}
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��֤���Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �õ��ϴν�Ϣ����
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("û��������������޷��������ᣡ");
								}
								
								//ȡ���˻��Ľ�Ϣ��
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// �޸����˻���Ϣ
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("---������׼����-----���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									//�����˻����������ý�������
									if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.BAK_DEPOSIT_PREDRAW_INTEREST);
									}
									else if(accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE)
									{
										param.setTransactionTypeID(SETTConstant.TransactionType.RESERVE_DEPOSIT_PREDRAW_INTEREST);
									}
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("��������");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----��֤��---���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------��֤����Ϣ�����������---------");
						}
					}
					// �Ƿ����˻�������
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING &&
						info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else //if (info.getDrawingInterest() > 0)
						{
							log.info("-------------�����Ϣ�������Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
								
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								// ������Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("���������ᱣ��ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(),
										saveInfo.getAccountTypeID());
	
								// �õ��ϴν�Ϣ����
								Timestamp dtInterestStart = null;
	
								dtInterestStart = interestDao.findInterestStartDate(conInternal, info.getSubAccountID(),
										SETTConstant.InterestFeeType.PREDRAWINTEREST,
										SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("û��������������޷��������ᣡ");
								}
								
								//ȡ���˻��Ľ�Ϣ��
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
	
								// �޸����˻���Ϣ
								subAcctontDao.updatePreDrawInterestAndDateReverse(saveInfo.getSubAccountID(),
										UtilOperation.Arith.round(
												UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2)
										
										, saai.getSubAccountCurrenctInfo().getClearInterestDate()
										
										, saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("---����������-----���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									param.setPreDrawInterest((-1) * saveInfo.getInterest());
									param.setPrincipalOrTransAmount((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setTotalInterest((-1)
											* UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), UtilOperation.Arith.round( saveInfo.getInterest(), 2)), 2));
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// ��֤�������Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.LENDING_LOAN_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo
											.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("��������");
	
									// param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("-----��֤��---���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------��֤����Ϣ�����������---------");
						}
					}
					//�Ƿ�����˻�������
					else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT 
						  && info.getInterestType() == SETTConstant.InterestFeeType.PREDRAWINTEREST)
					{
						log.info("---------�жϼ�����Ϣ�Ƿ����0-----------");
	
						if (DataFormat.formatDouble(info.getDrawingInterest()) == 0)
						{
							throw new IException("�˻���" + info.getAccountNo() + " �˻����ƣ�" + NameRef.getAccountNameByID(info.getAccountID()) + " ������ϢΪ�ղ��ܳ�������");
						}
						else// if (info.getDrawingInterest() > 0)
						{
							log.info("-------------������Ϣ�������Ὺʼ---------");
	
							if (settmentInfo.getIsSave() == SETTConstant.BooleanValue.ISTRUE) // ����
							{
								// �����Ϣ��¼
								log.info("----------���濪ʼ---------");
	
								/*****�ڵ��ø÷���ǰ���뽻������(�����ɽ��ױ��ʹ��)*****/
								settmentInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
                                //modify by xwhe 2009-03-06 (���ػ�ȡ���׺ŵķ�������conInternal��Ϊ����ά������)
								InterestSettmentInfo saveInfo = getSaveInfo(conInternal,settmentInfo, info);
	
								//���ڴ�����Ӧ����Ϣ����������
								saveInfo.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
								long l = interestDao.add(conInternal, saveInfo);
	
								if (l < 0)
								{
									throw new IException("��Ϣ����ʧ�ܣ���");
								}
	
								log.info("----------�������---------");
	
								SubAccountPredrawInterestInfo preDrawInfo = new SubAccountPredrawInterestInfo();
								preDrawInfo = uo.getPredrawInterestBySubAccountIDAndAccountType(saveInfo.getSubAccountID(), saveInfo.getAccountTypeID());
	
								// �õ��ϴν�Ϣ����
								Timestamp dtInterestStart = null;
								dtInterestStart = interestDao.findInterestStartDate(conInternal
										, info.getSubAccountID()
										, SETTConstant.InterestFeeType.PREDRAWINTEREST
										, SETTConstant.InterestOperateType.PREDRAWINTEREST);
	
								if (dtInterestStart == null)
								{
									throw new IException("û��������������޷��������ᣡ");
								}
								
								//ȡ���˻��Ľ�Ϣ��
								saai = new SubAccountAssemblerInfo();
								saai = subAcctontDao.findByID(saveInfo.getSubAccountID());
								
								//�޸����˻���Ϣ
								subAcctontDao.updatePreDrawInterestAndDateReverse(
										  saveInfo.getSubAccountID()
										  
										  //Boxu Add 2008��2��28�� ԭ��Ϣ=������Ϣ-(������Ϣ+Э����Ϣ)
										  //, UtilOperation.Arith.round(UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(), saveInfo.getInterest()), 2)
										  , UtilOperation.Arith.round(
												  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
														  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
														  )
												  , 2)
										  
										  //ȡ���˻��Ľ�Ϣ��
										  //, dtInterestStart
										  , saai.getSubAccountCurrenctInfo().getClearInterestDate()
										  
										  , saveInfo.getAccountTypeID());
	
								if (settmentInfo.getIsKeepAccount() == SETTConstant.BooleanValue.ISTRUE) // ����
								{
									log.info("--------���ɻ�Ʒ�¼��ʼ---------");
	
									GenerateGLEntryParam param = new GenerateGLEntryParam();
	
									param.setReceiveAccountID(saveInfo.getSubAccountID());
									param.setPayAccountID(saveInfo.getSubAccountID());
									
									//ԭ��Ϣ=������Ϣ-(������Ϣ+Э����Ϣ)
									param.setPreDrawInterest((-1) * UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2)));
									param.setPrincipalOrTransAmount((-1) * 
																		  UtilOperation.Arith.round(
																		  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
																				  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
																				  )
																		  , 2)
																		  );
									param.setTotalInterest((-1) * 
																		  UtilOperation.Arith.round(
																		  UtilOperation.Arith.sub(preDrawInfo.getPredrawInterest(),
																				  UtilOperation.Arith.add(UtilOperation.Arith.round( saveInfo.getInterest(), 2), UtilOperation.Arith.round( saveInfo.getNegotiateInterest(), 2))
																				  )
																		  , 2)
																		  );
									
									param.setOfficeID(saveInfo.getOfficeID());
									param.setCurrencyID(saveInfo.getCurrencyID());
	
									// ���ڴ�����Ӧ����Ϣ����������
									param.setTransactionTypeID(SETTConstant.TransactionType.CURRENT_DEPOSIT_PREDRAW_INTEREST);
	
									param.setExecuteDate(Env.getSystemDate(conInternal, saveInfo.getOfficeID(), saveInfo.getCurrencyID()));
									param.setInterestStartDate(saveInfo.getInterestSettlementDate());
									param.setTransNo(saveInfo.getTransNo());
									param.setAbstractStr("��������");
									//modify by kevin(������)2012-04-01
									//��ҩ��ĿҪ��ѡ�Э����Ϣ����������
									param.setRemissionInterest((-1)*UtilOperation.Arith.round(saveInfo.getInterest(), 2));
									param.setReallyReceiveInterest((-1)*UtilOperation.Arith.round(saveInfo.getNegotiateInterest(), 2));
	
	
									//param.setEntryType(lEntryType);
	
									boolean bFlag = glo.generateGLEntry(param, conInternal);
	
									log.info("--------���ɻ�Ʒ�¼����---------");
								}
							}
							log.info("-------------������Ϣ�����������---------");
						}
					}
					else
					{
						// throw new IException("�˻�:"
						// + info.getAccountNo()
						// + "�ſ:"
						// + info.getPayFormNo() + "�޷���������");
	
						throw new IException("�˻���"
								+ info.getAccountNo()
								// + "�ſ:"
								+ (info.getPayFormNo() != null && info.getPayFormNo().length() > 0 ? " �ſ��"
										+ info.getPayFormNo() : "") + " �˻����ͣ�"
								+ SETTConstant.AccountType.getName(info.getAccountTypeID()) + " �޷���������");
					}
				}
			}
			log.info("-------------�����������---------");

			// �������������ά������һ�ж�˳����ɣ��ύ�������������ҵ���߼���
			if (con == null)
			{
				try
				{
					conInternal.commit();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eCommit)
				{
					throw new Exception("�����ύ�쳣");
				}
			}
		}
		catch (Exception e)
		{
			// ִ�й����г������쳣������������ڸ÷����ڲ������ģ���ô�÷���Ҫ��������Ļ��ˣ�����ֻ�轫�쳣�ٴ�
			// �׳�����֪ͨ�������֯�ߡ�
			e.printStackTrace();
			if (con == null)
			{
				try
				{
					conInternal.rollback();
					conInternal.setAutoCommit(true);
				}
				catch (Exception eRollback)
				{
					throw new Exception("��������쳣");
				}
			}
			throw e;
		}
		finally
		{
			// �����ǳɹ��������÷������������������Ӧ�ý���������Դ���ͷţ������ٴ��׳��ر����ӵ��쳣��
			if (con == null)
			{
				try
				{
					conInternal.close();
				}
				catch (Exception eClose)
				{
					;
				}
			}
		}

		return nResult;

	}

	/**
	 * ��������Ϣ������-��ͨ��Ŀ��
	 * 
	 * @param officeID
	 * @param currencyID
	 * @param accountID
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	private void drawDepositInterestForICBC(Connection conn, long officeID, long currencyID, long accountID,
			long subAccountID, double dInterest, Timestamp dtSettlementDate) throws Exception
	{

		// ��ȡ���˻���Ϣ
		SubAccountAssemblerInfo subAccountAssemblerInfo = null;
		SubAccountCurrentInfo subAccountCurrentInfo = null;
		Sett_SubAccountDAO subAccountDAO = new Sett_SubAccountDAO(conn);
		AccountBean accountbean = new AccountBean(conn);

		try
		{
			subAccountAssemblerInfo = subAccountDAO.findByAccountID(accountID);
			if (subAccountAssemblerInfo == null)
			{
				throw new Exception("�����˻�ID��" + accountID + "�Ҳ������˻���Ϣ");
			}
			subAccountCurrentInfo = subAccountAssemblerInfo.getSubAccountCurrenctInfo();

			Timestamp lastclearInterestDate = subAccountCurrentInfo.getClearInterestDate();// ��Ϣ����
			long interestRatePlanID = subAccountCurrentInfo.getInterestRatePlanID();

			// ����Ϣ����0(�����Ϣ)����ִ��ִ�н�Ϣ����
			if (dInterest > 0)
			{
				UtilOperation utilOperation = new UtilOperation();
				String strNewTransNo = utilOperation.getNewTransactionNo(officeID, currencyID,SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				strNewTransNo = DataFormat.formatDate(dtSettlementDate).replaceAll("-", "")
						+ strNewTransNo.substring(8);

				// �����Ϣ��¼
				Sett_TransInterestSettlementDAO transInterestSettlementDAO = new Sett_TransInterestSettlementDAO();
				InterestSettmentInfo interestSettmentInfo = new InterestSettmentInfo();
				interestSettmentInfo.setOfficeID(officeID);
				interestSettmentInfo.setCurrencyID(currencyID);
				interestSettmentInfo.setTransNo(strNewTransNo);
				interestSettmentInfo.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				interestSettmentInfo.setAccountID(accountID);
				interestSettmentInfo.setSubAccountID(subAccountID);
				interestSettmentInfo.setAccountTypeID(NameRef.getAccountTypeByID(accountID));
				interestSettmentInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
				interestSettmentInfo.setOperationType(SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
				interestSettmentInfo.setInterestSettlementDate(dtSettlementDate);
				interestSettmentInfo.setInterestStartDate(lastclearInterestDate);
				interestSettmentInfo.setInterestEndDate(new Timestamp(IDate.before(dtSettlementDate, -1).getTime()));
				interestSettmentInfo.setInterestDays(IDate.intervalDays(lastclearInterestDate, interestSettmentInfo
						.getInterestEndDate()));
				interestSettmentInfo.setRate(BaseQueryObject.getCurrentInterestRate(dtSettlementDate,
						interestSettmentInfo.getInterestEndDate(), interestRatePlanID, 0));
				interestSettmentInfo.setReceiveInterestAccountID(accountID);
				interestSettmentInfo.setReceiveInterestAccountID(accountID);
				interestSettmentInfo.setExecuteDate(Env.getSystemDate(officeID, currencyID));
				interestSettmentInfo.setInputUserID(Constant.MachineUser.InputUser);
				interestSettmentInfo.setStatusID(SETTConstant.TransactionStatus.CHECK);
				interestSettmentInfo.setIsKeepAccount(Constant.TRUE);
				interestSettmentInfo.setPecisionInterest(dInterest);
				interestSettmentInfo.setInterest(dInterest);
				transInterestSettlementDAO.add(conn, interestSettmentInfo);

				// ���ӻ����˻�
				TransAccountDetailInfo tadi = new TransAccountDetailInfo();
				tadi.setAmount(dInterest);
				tadi.setCurrencyID(currencyID);
				tadi.setDtExecute(dtSettlementDate);
				tadi.setDtInterestStart(dtSettlementDate);
				tadi.setOfficeID(officeID);
				tadi.setStatusID(SETTConstant.TransactionStatus.CHECK);
				tadi.setTransAccountID(accountID);
				tadi.setTransDirection(SETTConstant.DebitOrCredit.CREDIT);
				tadi.setTransSubAccountID(subAccountID);
				tadi.setTransNo(interestSettmentInfo.getTransNo());
				tadi.setTransactionTypeID(SETTConstant.TransactionType.INTEREST_FEE_PAY_CURRENT);
				accountbean.depositCurrent(tadi);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}
