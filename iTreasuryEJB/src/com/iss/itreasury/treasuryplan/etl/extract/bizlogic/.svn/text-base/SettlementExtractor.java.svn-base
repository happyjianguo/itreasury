/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-7
 */

package com.iss.itreasury.treasuryplan.etl.extract.bizlogic;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastBalanceInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.Log4j;

/**
 * @author yehuang w 结算数据抽取类
 */
public class SettlementExtractor extends AbstractExtractor
{

	private Log4j	log	= new Log4j(Constant.ModuleType.PLAN, this);


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractActualBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */

	/**
	 * 抽取每个账户的余额
	 */
	public void extractActualBalance(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();
		Connection tpConn = sett_ExtractorUtilDAO.getConnectionByModuleID(Constant.ModuleType.PLAN);
		Trea_ActualBalanceDAO balanceDao = new Trea_ActualBalanceDAO(tpConn);
		balanceDao.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SETTLEMENT, officeID, currencyID);
		sett_ExtractorUtilDAO.extractDataFromActualBalanceToActualBalance(officeID, currencyID, startDate, endDate);
	}


	/**
	 * 实际发生额--抽取的数据包括：账户交易明细、分录明细。
	 */
	public void extractActualAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();
		Connection tpConn = sett_ExtractorUtilDAO.getConnectionByModuleID(Constant.ModuleType.PLAN);
		Trea_ActualAmountDAO amountDao = new Trea_ActualAmountDAO(tpConn);
		amountDao.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SETTLEMENT, officeID, currencyID);
		sett_ExtractorUtilDAO.extractDataFromTransAccountDetailToActualAmount(officeID, currencyID, startDate, endDate);
	}


	/**
	 * 抽取预测发生额
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{
		System.out.println("-----------settlement: extractForcastAmount----------");
		
		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();
		Connection tpConn = sett_ExtractorUtilDAO.getConnectionByModuleID(SETTConstant.ModuleType.PLAN);
		tpConn.setAutoCommit(false);
		
		Connection settConn = sett_ExtractorUtilDAO.getConnectionByModuleID(TPConstant.ModuleType.SETTLEMENT);
		Collection c = sett_ExtractorUtilDAO.getFixedDepositThatEndAtForcastDay(officeID, currencyID, startDate, endDate);
		
		Iterator it = c.iterator();
		InterestCalculation interestCalculation = new InterestCalculation(settConn);
		Trea_ForecastAmountDAO forecastAmountDAO = new Trea_ForecastAmountDAO(tpConn);
		forecastAmountDAO.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SETTLEMENT, officeID, currencyID);

		while (it.hasNext()) 
		{
			ForecastAmountInfo fixedDepositInfo = (ForecastAmountInfo) it.next();

			/**
			 * 调用结算模块的计息方法GetFixedDepositAccountInterest，匡算出此子账户的利息。 注意：结息日=
			 * TransactionDate，执行日=结算日期+1
			 */

			/**
			 * ，匡算出此定期子账户的利息
			 */
			InterestsInfo interestsInfo = new InterestsInfo();
			try 
			{
				interestsInfo = interestCalculation.calculateFixedDepositAccountInterest(fixedDepositInfo.getSubAccountID(), fixedDepositInfo.getForecastAmount(), fixedDepositInfo.getAf_dtend());
			}
			catch (IException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("^^^^^^^^^^^^  IN SettlementExtractor   extractForcastAmount   fixedDepositInfo.getAf_dtend() = " + fixedDepositInfo.getAf_dtend());
			// 4) 将此定期子账户的到期支取相关信息写到表Trea_ ForecastAmount中
			ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), fixedDepositInfo.getAf_dtend(), Constant.ModuleType.SETTLEMENT);

			String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(fixedDepositInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
			forcastAmountInfo1.setClientCode(fixedDepositInfo.getClientCode());
			forcastAmountInfo1.setAccountCode(fixedDepositInfo.getAccountCode());
			forcastAmountInfo1.setAccountTypeId(fixedDepositInfo.getAccountTypeId());
			forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.TransactionName.getNameByID(TPConstant.TPTransaction.TransactionName.FixedMaturity));// 定期到期支取
			forcastAmountInfo1.setForecastAmount(fixedDepositInfo.getForecastAmount());
			forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
			forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode() + fixedDepositInfo.getContractCode());
			forecastAmountDAO.add(forcastAmountInfo1);
			// 5) 将此定期子账户的本金到期转活期相关信息写到表Trea_ ForecastAmount中
			ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), fixedDepositInfo.getAf_dtend(), Constant.ModuleType.SETTLEMENT);
			ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(fixedDepositInfo.getClientID(), SETTConstant.AccountGroupType.CURRENT);
			forcastAmountInfo2.setAccountCode(tmpForcastAmountInfo.getAccountCode());
			forcastAmountInfo2.setClientCode(fixedDepositInfo.getClientCode());
			forcastAmountInfo2.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
			forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.TransactionName.getNameByID(TPConstant.TPTransaction.TransactionName.FixedMaturityToCurrent));
			forcastAmountInfo2.setForecastAmount(fixedDepositInfo.getForecastAmount() + interestsInfo.getTotalInterest());
			glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
			forcastAmountInfo2.setGlSubjectCode(glSubjectCode);
			forcastAmountInfo2.setRemark(forcastAmountInfo2.getAccountCode() + fixedDepositInfo.getContractCode());

			forecastAmountDAO.add(forcastAmountInfo2);
			// 6) 将定期利息支出有关信息写到表Trea_ ForecastAmount中
			ForecastAmountInfo forcastAmountInfo3 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), fixedDepositInfo.getAf_dtend(), Constant.ModuleType.SETTLEMENT);
			forcastAmountInfo3.setTransactionName(TPConstant.TPTransaction.TransactionName.getNameByID(TPConstant.TPTransaction.TransactionName.FixedMaturityInterest));
			forcastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forcastAmountInfo3.setForecastAmount(interestsInfo.getTotalInterest());
			glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(fixedDepositInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_INTEREST);
			forcastAmountInfo3.setGlSubjectCode(glSubjectCode);
			forcastAmountInfo3.setRemark(forcastAmountInfo2.getAccountCode() + fixedDepositInfo.getContractCode());

			forecastAmountDAO.add(forcastAmountInfo3);
		}
		tpConn.commit();
		// 3. 如果是结息日，则进行活期存款利息相关数据的预测
		Timestamp forcastDate = startDate;
		System.out.println("=========================== 开始循环处理利息数据............................................. ");
		System.out.println("=========================== in settlementExtractor   forcastDate = " + forcastDate);
		while (!forcastDate.after(endDate)) 
		{
			System.out.println("=========================== 在循环体内   forcastDate = " + forcastDate);
			if (TPConstant.InterestSettlementDate.isSettlementDate(forcastDate)) 
			{
				System.out.println("-------1");
				Collection c1 = sett_ExtractorUtilDAO.getAllCurrentAccount(officeID, currencyID);
				System.out.println("-------2");
				Iterator it1 = c1.iterator();
				while (it1.hasNext()) 
				{
					ForecastAmountInfo currentDepositAccount = (ForecastAmountInfo) it1.next();
					Timestamp openDate = sett_ExtractorUtilDAO.getOfficeOpenDate(officeID, currencyID);
					// a) 如果是预测本季度利息，即TransactionDate是结算日期所在季度的21号
					CurrentDepositAccountInterestInfo currentDepositAccountInterestInfo = new CurrentDepositAccountInterestInfo();
					try
					{
						//currentDepositAccountInterestInfo = interestCalculation.getCurrentDepositAccountInterest(tpConn, officeID, currencyID, currentDepositAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1));
						currentDepositAccountInterestInfo = interestCalculation.getCurrentDepositAccountInterest(tpConn, officeID, currencyID, currentDepositAccount.getSubAccountID(), forcastDate,openDate); //modify by jason 2005.12.19
					}
					catch (IException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double interest = currentDepositAccountInterestInfo.getNormalInterest();
					double negotiationInterest = currentDepositAccountInterestInfo.getNegotiationInterest();
					if (!TPConstant.InterestSettlementDate.isCurrentQuarterSettlementInterestDate(forcastDate))
					{
						CurrentDepositAccountInterestInfo lastQuarterDepositAccountInterestInfo = new CurrentDepositAccountInterestInfo();
						try 
						{
							//lastQuarterDepositAccountInterestInfo = interestCalculation.getCurrentDepositAccountInterest(tpConn, officeID, currencyID, currentDepositAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1));
							lastQuarterDepositAccountInterestInfo = interestCalculation.getCurrentDepositAccountInterest(tpConn, officeID, currencyID, currentDepositAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), openDate);//modify by jason 2005.12.19
						}
						catch (IException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 利息=预测日利息-预测日上季度利息
						interest -= lastQuarterDepositAccountInterestInfo.getNormalInterest();
						negotiationInterest -= lastQuarterDepositAccountInterestInfo.getNegotiationInterest();
					}
					// 4) 将此账户的利息收入信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					forcastAmountInfo1.setClientCode(currentDepositAccount.getClientCode());
					forcastAmountInfo1.setAccountCode(currentDepositAccount.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(currentDepositAccount.getAccountTypeId());
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(currentDepositAccount.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.TransactionName.getNameByID(TPConstant.TPTransaction.TransactionName.CurrentInterestSettlement));
					forcastAmountInfo1.setForecastAmount(interest + negotiationInterest);
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo1.setRemark(currentDepositAccount.getAccountCode());
					System.out.println("----------------ad1" + forcastAmountInfo1);
					forecastAmountDAO.add(forcastAmountInfo1);
					// 5) 将活期利息支出有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					if (currencyID == 1) 
					{
						forcastAmountInfo2.setGlSubjectCode("001.000.5210040000.000.000000.0000.0000");
					}
					else 
					{
						forcastAmountInfo2.setGlSubjectCode("002.000.5210040000.000.000000.0000.0000");
					}
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.TransactionName.getNameByID(TPConstant.TPTransaction.TransactionName.CurrentInterestSettlement));
					forcastAmountInfo2.setForecastAmount(interest + negotiationInterest);
					// forcastAmountInfo2.setAccountTypeId(currentDepositAccount.getAccountTypeId());
					forcastAmountInfo2.setRemark(currentDepositAccount.getAccountCode());
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					// forcastAmountInfo2.setClientCode(currentDepositAccount.getClientCode());
					// forcastAmountInfo2.setAccountCode(currentDepositAccount.getAccountCode());
					System.out.println("----------------ad2" + forcastAmountInfo2);
					forecastAmountDAO.add(forcastAmountInfo2);
				}

				tpConn.commit();
				// 则进行自营贷款利息相关数据的预测。已放款部份的在结算预测利息费用。
				Collection c2 = sett_ExtractorUtilDAO.getAlTrustLoanAccount(officeID, currencyID, forcastDate);
				Iterator it2 = c2.iterator();
				while (it2.hasNext()) 
				{
					ForecastAmountInfo trustLoanAccount = (ForecastAmountInfo) it2.next();
					Timestamp openDate = sett_ExtractorUtilDAO.getOfficeOpenDate(officeID, currencyID);
					// a) 如果是预测本季度利息，即TransactionDate是结算日期所在季度的21号
					LoanAccountInterestInfo loanAccountInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						loanAccountInterestInfo = interestCalculation.getLoanAccountInterest(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1));
					}
					catch (IException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("-------loanAccountInterestInfo" + UtilOperation.dataentityToString(loanAccountInterestInfo));
					LoanAccountInterestInfo compoundInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						compoundInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					}
					catch (IException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("-------compoundInterestInfo" + UtilOperation.dataentityToString(compoundInterestInfo));
					LoanAccountInterestInfo forfeitInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.FORFEITINTEREST);
					}
					catch (IException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("-------forfeitInterestInfo" + UtilOperation.dataentityToString(forfeitInterestInfo));
					double interest = loanAccountInterestInfo.getInterest();
					double compoundInterest = compoundInterestInfo.getInterest();
					double forfeitInterest = forfeitInterestInfo.getInterest();

					if (!TPConstant.InterestSettlementDate.isCurrentQuarterSettlementInterestDate(forcastDate)) 
					{

						LoanAccountInterestInfo lastQuarterLoanAccountInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							lastQuarterLoanAccountInterestInfo = interestCalculation.getLoanAccountInterest(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1));
						}
						catch (IException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("-------lastQuarterLoanAccountInterestInfo" + UtilOperation.dataentityToString(lastQuarterLoanAccountInterestInfo));
						LoanAccountInterestInfo lastQuarterCompoundInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							lastQuarterCompoundInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1),
									SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						}
						catch (IException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("-------lastQuarterCompoundInterestInfo" + UtilOperation.dataentityToString(lastQuarterCompoundInterestInfo));
						LoanAccountInterestInfo lastQuarterForfeitInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							lastQuarterForfeitInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1),
									SETTConstant.InterestFeeType.FORFEITINTEREST);
						}
						catch (IException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("-------lastQuarterForfeitInterestInfo" + UtilOperation.dataentityToString(lastQuarterForfeitInterestInfo));
						// 利息=预测日利息-预测日上季度利息
						interest -= lastQuarterLoanAccountInterestInfo.getInterest();
						compoundInterest -= lastQuarterCompoundInterestInfo.getInterest();
						forfeitInterest -= lastQuarterForfeitInterestInfo.getInterest();

					}
					// 5) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					long paySubAccountID = sett_ExtractorUtilDAO.getFirstSubAccountIDByClientID(trustLoanAccount.getClientID(), SETTConstant.AccountGroupType.CURRENT);
					String payCurrentSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(paySubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setClientCode(trustLoanAccount.getClientCode());
					forcastAmountInfo1.setAccountCode(sett_ExtractorUtilDAO.getAccountCodeBySubAccountID(paySubAccountID));
					forcastAmountInfo1.setAccountTypeId(1);
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo1.setGlSubjectCode(payCurrentSubjectCode);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));
					forcastAmountInfo1.setForecastAmount(interest + compoundInterest + forfeitInterest);
					forcastAmountInfo1.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					forcastAmountInfo1.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forecastAmountDAO.add(forcastAmountInfo1);
					// 6) 将贷款利息收入有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					// forcastAmountInfo2.setClientCode(trustLoanAccount.getClientCode());
					// forcastAmountInfo2.setAccountCode(trustLoanAccount.getAccountCode());
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(trustLoanAccount.getSubAccountID(), AccountOperation.SUBJECT_TYPE_INTEREST);
					forcastAmountInfo2.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));
					forcastAmountInfo2.setForecastAmount(interest + compoundInterest + forfeitInterest);
					forcastAmountInfo2.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					// forcastAmountInfo2.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forecastAmountDAO.add(forcastAmountInfo2);

				}

				tpConn.commit();
				// 进行委托贷款利息、手续费相关数据的预测。已放款部份的在结算预测利息费用。
				Collection c3 = sett_ExtractorUtilDAO.getAlConsignLoanAccount(officeID, currencyID, forcastDate);
				Iterator it3 = c3.iterator();
				while (it3.hasNext()) 
				{
					ForecastAmountInfo trustLoanAccount = (ForecastAmountInfo) it3.next();
					Timestamp openDate = sett_ExtractorUtilDAO.getOfficeOpenDate(officeID, currencyID);
					// a) 如果是预测本季度利息，即TransactionDate是结算日期所在季度的21号

					LoanAccountInterestInfo loanAccountInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						loanAccountInterestInfo = interestCalculation.getLoanAccountInterest(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1));
					}
					catch (IException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LoanAccountInterestInfo compoundInterestInfo = new LoanAccountInterestInfo();
					try
					{
						compoundInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.COMPOUNDINTEREST);
					}
					catch (IException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LoanAccountInterestInfo forfeitInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						forfeitInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.FORFEITINTEREST);
					}
					catch (IException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					LoanAccountInterestInfo comissionInterestInfo = new LoanAccountInterestInfo();
					try 
					{
						comissionInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), forcastDate, DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.COMMISION);
					}
					catch (IException e) 
					
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double interest = loanAccountInterestInfo.getInterest();
					double compoundInterest = compoundInterestInfo.getInterest();
					double forfeitInterest = forfeitInterestInfo.getInterest();
					double comissionInterest = comissionInterestInfo.getInterest();

					if (!TPConstant.InterestSettlementDate.isCurrentQuarterSettlementInterestDate(forcastDate)) {
						LoanAccountInterestInfo lastQuarterLoanAccountInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							interestCalculation.getLoanAccountInterest(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1));
						}
						catch (IException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LoanAccountInterestInfo lastQuarterCompoundInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.COMPOUNDINTEREST);
						}
						catch (IException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LoanAccountInterestInfo lastQuarterForfeitInterestInfo = new LoanAccountInterestInfo();
						try 
						{
							interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1), SETTConstant.InterestFeeType.FORFEITINTEREST);
						}
						catch (IException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LoanAccountInterestInfo lastQuarterComissionInterestInfo = new LoanAccountInterestInfo();
						try
						{
							lastQuarterComissionInterestInfo = interestCalculation.getLoanAccountFee(tpConn, officeID, currencyID, trustLoanAccount.getAccountID(), trustLoanAccount.getSubAccountID(), DataFormat.getNextMonth(forcastDate, -3), DataFormat.getNextDate(openDate, 1),
									SETTConstant.InterestFeeType.COMMISION);
						}
						catch (IException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 利息=预测日利息-预测日上季度利息
						interest -= lastQuarterLoanAccountInterestInfo.getInterest();
						compoundInterest -= lastQuarterCompoundInterestInfo.getInterest();
						forfeitInterest -= lastQuarterForfeitInterestInfo.getInterest();
						comissionInterest -= lastQuarterComissionInterestInfo.getInterest();

					}
					// 5) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					forcastAmountInfo1.setClientCode(trustLoanAccount.getClientCode());
					forcastAmountInfo1.setAccountCode(trustLoanAccount.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					String glSubjectCode = "";
					try 
					{
						glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(trustLoanAccount.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					}
					catch (RemoteException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IRollbackException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));
					forcastAmountInfo1.setForecastAmount(interest + compoundInterest + forfeitInterest);
					// 6) 将贷款利息收入有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					long paySubAccountID = sett_ExtractorUtilDAO.getFirstSubAccountIDByClientID(trustLoanAccount.getClientID(), SETTConstant.AccountGroupType.CURRENT);
					String payCurrentSubjectCode = "";
					try 
					{
						payCurrentSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(paySubAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT);
					}
					catch (RemoteException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					catch (IRollbackException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					catch (IException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					forcastAmountInfo2.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo2.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forcastAmountInfo2.setClientCode(trustLoanAccount.getClientCode());
					forcastAmountInfo2.setAccountCode(trustLoanAccount.getAccountCode());
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));
					forcastAmountInfo2.setForecastAmount((interest + compoundInterest + forfeitInterest) * 0.95);
					forcastAmountInfo2.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo2);
					// 7) 将委托贷款利息税费有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo3 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					glSubjectCode = "";
					try
					{
						glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(trustLoanAccount.getSubAccountID(), AccountOperation.SUBJECT_TYPE_INTEREST);
					}
					catch (RemoteException e2) 
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					catch (IRollbackException e2) 
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					catch (IException e2)
					{
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					forcastAmountInfo3.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo3.setClientCode(trustLoanAccount.getClientCode());
					forcastAmountInfo3.setAccountCode(trustLoanAccount.getAccountCode());
					forcastAmountInfo3.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forcastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo3.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));
					forcastAmountInfo3.setForecastAmount((interest + compoundInterest + forfeitInterest) * 0.05);
					forcastAmountInfo3.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo3);

					// 8) 将此贷款账户的手续费支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo4 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);
					forcastAmountInfo4.setGlSubjectCode(payCurrentSubjectCode);
					forcastAmountInfo4.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forcastAmountInfo4.setClientCode(trustLoanAccount.getClientCode());
					forcastAmountInfo4.setAccountCode(trustLoanAccount.getAccountCode());
					forcastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo4.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));
					forcastAmountInfo4.setForecastAmount((interest + compoundInterest + forfeitInterest) * 0.95);
					forcastAmountInfo4.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo4);

					// 9) 将此贷款账户的手续费支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo5 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.SETTLEMENT);

					if (currencyID == 1) 
					{
						forcastAmountInfo5.setGlSubjectCode("001.000.5020010000.000.000000.0000.0000");
					}
					forcastAmountInfo5.setAmountDirection(TPConstant.AmountDirection.PLUS);
					// forcastAmountInfo5.setClientCode(trustLoanAccount.getClientCode());
					// forcastAmountInfo5.setAccountCode(trustLoanAccount.getAccountCode());
					// forcastAmountInfo5.setAccountTypeId(trustLoanAccount.getAccountTypeId());
					forcastAmountInfo5.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));
					forcastAmountInfo5.setForecastAmount(comissionInterest);
					forcastAmountInfo5.setRemark(trustLoanAccount.getAccountCode() + trustLoanAccount.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo5);
				}
			}

			forcastDate = DataFormat.getNextDate(forcastDate, 1);
		}
		tpConn.commit();
		if (tpConn != null)
		{
			tpConn.close();
		}
		Connection sett_Conn = sett_ExtractorUtilDAO.getConnectionByModuleID(Constant.ModuleType.SETTLEMENT);
		sett_Conn.close();

	}


	static public void main(String[] args)
	{

		SettlementExtractor extractor = new SettlementExtractor();
		try {
			// Timestamp today = Timestamp.valueOf("2004-08-25
			// 00:00:00.000000000");
			// Timestamp opendate = Timestamp.valueOf("2004-08-20
			// 00:00:00.000000000");
			if (!TPScheduler.isNeedExecute(null))
				return;
			// for(int i= 0;i<30;i++){
			// extractor.extractForcastBalance(1,1,today,opendate);;
			// opendate = DataFormat.getNextDate(opendate, 1);
			// }
			extractor.startExtractData(args);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * 从结算抽取预测余额
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastBalance(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{
		System.out.println("-----------settlement: extractForcastBalance----------");
		
		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();
		Connection tpConn = sett_ExtractorUtilDAO.getConnectionByModuleID(SETTConstant.ModuleType.PLAN);
		tpConn.setAutoCommit(false);
		
		Connection settConn = sett_ExtractorUtilDAO.getConnectionByModuleID(TPConstant.ModuleType.SETTLEMENT);
		Trea_ForecastBalanceDAO forecastBalanceDAO = new Trea_ForecastBalanceDAO(tpConn);
		Trea_ActualBalanceDAO actualBalanceDAO = new Trea_ActualBalanceDAO(tpConn);
		
		forecastBalanceDAO.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.SETTLEMENT, officeID, currencyID);
		tpConn.commit();
		Collection c = null;
		
		Timestamp transDate = startDate;
		while (transDate != null && !transDate.after(endDate))
		{
			System.out.println("Extract...从结算抽取预测余额  transDate : "+transDate);
			if (forecastBalanceDAO.countLastTransactionDateRecordByModuleID(TPConstant.ModuleType.SETTLEMENT, transDate, officeID, currencyID) > 0) 
			{
				c = forecastBalanceDAO.caculateForecastBalance(transDate, TPConstant.ModuleType.SETTLEMENT, officeID, currencyID);
			}
			else 
			{
				c = actualBalanceDAO.caculateForecastBalance(transDate, TPConstant.ModuleType.SETTLEMENT, officeID, currencyID);
			}
			
			if( c != null )
			{
				Iterator it = c.iterator();
				System.out.println("11111111111111111  Collection的长度是 : "+c.size());
				
				while (it != null && it.hasNext()) 
				{
					ForecastBalanceInfo forecastBalanceInfo = (ForecastBalanceInfo) it.next();
					
					
					System.out.println("往预测余额表中插入一条记录  forecastBalanceInfo.getClass() ："+forecastBalanceInfo.getClass());
					System.out.println("往预测余额表中插入一条记录  forecastBalanceInfo.toString() ："+forecastBalanceInfo.toString());
					forecastBalanceDAO.add(forecastBalanceInfo);
				}
			}
			
			transDate =  DataFormat.getNextDate(transDate, 1);
			tpConn.commit();
			
		}
		sett_ExtractorUtilDAO.closeModuleConn(SETTConstant.ModuleType.PLAN);
		sett_ExtractorUtilDAO.closeModuleConn(TPConstant.ModuleType.SETTLEMENT);
	}

}
