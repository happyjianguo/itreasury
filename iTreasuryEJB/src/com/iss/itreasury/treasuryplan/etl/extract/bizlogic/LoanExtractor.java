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

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.settlement.account.bizlogic.AccountOperation;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Loan_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Sett_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ForecastAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.loan.LoanContractPlanInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * @author yehuang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class LoanExtractor extends AbstractExtractor
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractActualBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception
	{

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#eextractActualAmount(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualAmount(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception
	{
		// TODO Auto-generated method stub
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractForcastAmount(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 *      
	 *      备注：结算的取数逻辑用的是账户类型，信贷的取数逻辑用的是账户组类型，矛盾。
	 *      处理方法：为了统一，在方法extractForcastAmount()中，把信贷取数部分的账户组类型换算成账户类型
	 *      涉及的方法：addLoanInfoToForcastData()
	 *      问题：用硬编码的方式换算，存在问题，待优化。
	 *      gqfang , 2005.12.29
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		log.debug("###--------从信贷模块抽取预测发生额 START.......................................");
		Loan_ExtractorUtilDAO loan_ExtractorUtilDAO = new Loan_ExtractorUtilDAO();
		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();

		Timestamp openDate = loan_ExtractorUtilDAO.getOfficeOpenDate(officeID, currencyID);
		
		/**
		 * 自营贷款发放本金相关数据的预测
		 */
		Connection tpConn = loan_ExtractorUtilDAO.getConnectionByModuleID(Constant.ModuleType.PLAN);
		tpConn.setAutoCommit(false);
		Trea_ForecastAmountDAO forecastAmountDAO = new Trea_ForecastAmountDAO(tpConn);
		forecastAmountDAO.deleteByTransactionDateAndModuleID(startDate, endDate, Constant.ModuleType.LOAN, officeID, currencyID);
		tpConn.commit();
		Collection c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate , endDate, TPConstant.TPTransaction.Loan_Transaction.ZYGrant);
		Iterator it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("111自营贷款发放本金----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("111自营贷款发放本金----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			
			log.debug("111自营贷款发放本金----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
		}

		log.debug("--------结束自营贷款发放本金相关数据的预测---------");
		/**
		 * 自营贷款收回本金相关数据的预测
		 */
		log.debug("--------开始自营贷款收回本金相关数据的预测---------");
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate , endDate , TPConstant.TPTransaction.Loan_Transaction.ZYRepayment);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("222自营贷款收回本金----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("222自营贷款收回本金----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("222自营贷款收回本金----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------结束自营贷款收回本金相关数据的预测---------");
		/**
		 * 委托贷款发放本金相关数据的预测
		 */
		log.debug("--------开始委托贷款发放本金相关数据的预测---------");
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate,endDate, TPConstant.TPTransaction.Loan_Transaction.WTGrant);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("333委托贷款发放本金----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333委托贷款发放本金----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CURRENT, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333委托贷款发放本金----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			//forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTGrant, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333委托贷款发放本金----4-----forecastAmountInfo3:" + forecastAmountInfo3);
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo3);
			
			ForecastAmountInfo forecastAmountInfo4 = new ForecastAmountInfo();
			//forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTGrant, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333委托贷款发放本金-----5----forecastAmountInfo4:" + forecastAmountInfo4);
			forecastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo4);
		}
		log.debug("--------结束委托贷款发放本金相关数据的预测---------");
		log.debug("--------开始委托贷款收回本金相关数据的预测---------");
		/**
		 * 委托贷款收回本金相关数据的预测
		 */
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate,endDate, TPConstant.TPTransaction.Loan_Transaction.WTRepayment);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("444委托贷款收回本金-----1----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444委托贷款收回本金-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CURRENT, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444委托贷款收回本金-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			//forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444委托贷款收回本金-----4----forecastAmountInfo3:" + forecastAmountInfo3);
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo3);
			
			ForecastAmountInfo forecastAmountInfo4 = new ForecastAmountInfo();
			//forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CONSIGN, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, 9 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444委托贷款收回本金-----5----forecastAmountInfo4:" + forecastAmountInfo4);
			forecastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo4);
		}
		log.debug("--------结束委托贷款收回本金相关数据的预测---------");
		log.debug("--------开始贴现贷款发放相关数据的预测---------");
		// 贴现贷款发放相关数据的预测
		c = loan_ExtractorUtilDAO.getDiscountGrantForecastInfoByCondtion(officeID, currencyID, startDate , endDate);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("555贴现贷款发放相关数据-----1----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			//forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXGrant, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getCheckAmount());
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXGrant, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getCheckAmount());
			log.debug("555贴现贷款发放相关数据-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXGrant, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getExamineAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXGrant, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getExamineAmount());
			log.debug("555贴现贷款发放相关数据-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------结束贴现贷款发放相关数据的预测---------");
		log.debug("--------开始贴现票据到期收回本金/利息相关数据的预测---------");
		// 贴现票据到期收回本金/利息相关数据的预测
		c = loan_ExtractorUtilDAO.getDiscountRepaymentForecastInfoByCondtion(officeID, currencyID , startDate , endDate);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("666 贴现票据到期收回本金/利息-----1----contractPlanInfo:" + contractPlanInfo);
			// ForecastAmountInfo forecastAmountInfo1 = new
			// ForecastAmountInfo();
			// forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo,
			// forecastAmountInfo1,
			// TPConstant.TPTransaction.Loan_Transaction.TXGrant,
			// SETTConstant.AccountType.CURRENTDEPOSIT,false,sett_ExtractorUtilDAO,officeID,currencyID,extractDate,
			// forcastDate,contractPlanInfo.getCheckAmount());
			// /forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			//forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount());
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount());
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			log.debug("666 贴现票据到期收回本金/利息-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount()- contractPlanInfo.getCheckAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount()- contractPlanInfo.getCheckAmount());
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountInfo2.setAccountTypeId(-1);
			log.debug("666 贴现票据到期收回本金/利息-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------结束贴现票据到期收回本金/利息相关数据的预测---------");
		log.debug("--------开始银团贷款发放本金相关数据的预测---------");
		// 银团贷款发放本金相关数据的预测
		c = loan_ExtractorUtilDAO.getYTLoanForcastInfoByCondition(officeID, currencyID,  startDate , endDate, 1);
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.YTGrant, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.YTGrant, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount()* contractPlanInfo.getRate());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.YTGrant, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount()* contractPlanInfo.getRate());
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			// 其它参与行承贷部份有关信息
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			forecastAmountInfo3.setClientCode(forecastAmountInfo2.getClientCode());
			forecastAmountInfo3.setContractCode(forecastAmountInfo2.getContractCode());
			forecastAmountInfo3.setTransactionName(TPConstant.TPTransaction.Loan_Transaction.getNameByID(TPConstant.TPTransaction.Loan_Transaction.YTGrant));
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountInfo3.setForecastAmount(contractPlanInfo.getAmount() * (1.0 - contractPlanInfo.getRate()));
			forecastAmountInfo3.setGlSubjectCode("001.000.2020120000.000.000000.0000.0000");
			forecastAmountInfo3.setRemark(forecastAmountInfo2.getRemark());
			forecastAmountDAO.add(forecastAmountInfo3);
		}
		log.debug("--------结束银团贷款发放本金相关数据的预测---------");
		log.debug("--------开始银团贷款发放本金相关数据的预测---------");
		// 银团贷款发放本金相关数据的预测
		c = loan_ExtractorUtilDAO.getYTLoanForcastInfoByCondition(officeID, currencyID, startDate , endDate , 2);
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			//forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.YTRepayment, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.YTRepayment, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.YTRepayment, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount()* contractPlanInfo.getRate());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.YTRepayment, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount()* contractPlanInfo.getRate());
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			// 其它参与行承贷部份有关信息
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			forecastAmountInfo3.setClientCode(forecastAmountInfo2.getClientCode());
			forecastAmountInfo3.setContractCode(forecastAmountInfo2.getContractCode());
			forecastAmountInfo3.setTransactionName(TPConstant.TPTransaction.Loan_Transaction.getNameByID(TPConstant.TPTransaction.Loan_Transaction.YTRepayment));
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountInfo3.setForecastAmount(contractPlanInfo.getAmount() * (1.0 - contractPlanInfo.getRate()));
			forecastAmountInfo3.setGlSubjectCode("001.000.2020120000.000.000000.0000.0000");
			forecastAmountInfo3.setRemark(forecastAmountInfo2.getRemark());
			forecastAmountDAO.add(forecastAmountInfo3);
		}
		log.debug("--------结束银团贷款发放本金相关数据的预测---------");
		

		Timestamp forcastDate = startDate;
		while (!forcastDate.after(endDate))
		{
			if (loan_ExtractorUtilDAO.isSettlemtInterestDate(forcastDate)) 
			{
				log.debug("----------今天" + forcastDate + "是结息日");
				Collection c1 = loan_ExtractorUtilDAO.getLoanContractPlanInfoForInterestSettlemenet(officeID, currencyID, 1);
				Iterator it1 = c1.iterator();
				while (it1.hasNext()) 
				{
					LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it1.next();
					log.debug("-------" + contractPlanInfo);
					Collection c2 = null;
					if (contractPlanInfo.getSource() == 1) 
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromLoanContractPlanForForecastInterest(contractPlanInfo.getId());
					}
					else 
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromContractFormLoanformPlanForForecastInterest(contractPlanInfo.getId());
					}
					// 计算利息
					Iterator it2 = c2.iterator();
					while (it2.hasNext()) 
					{
						LoanContractPlanInfo contractPlanInfo1 = (LoanContractPlanInfo) it2.next();
						/**
						 * d) 起息日=上季度未月的21号 终息日= TransactionDate e) 利率=结果集.
						 * Minterestrate f) 年日利率转换基数=360
						 * 
						 */
	
						double interest = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
						// 5) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
						ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
						ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountByClientIDToForecastAmountInfo(contractPlanInfo.getBorrowClientID(), SETTConstant.AccountGroupType.CURRENT);
						String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
						forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
						forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
						forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
						forcastAmountInfo1.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);
						forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
						forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// 自营贷款结息
						forcastAmountInfo1.setForecastAmount(interest);
						forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
						// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
						forecastAmountDAO.add(forcastAmountInfo1);
	
						// 6) 将贷款利息收入有关信息写到表Trea_ ForecastAmount中
						ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
						if (currencyID == 1)
						{
							forcastAmountInfo2.setGlSubjectCode("001.000.5010010000.000.000000.0000.0000");
						}
						else
						{
							forcastAmountInfo2.setGlSubjectCode("002.000.5010010000.000.000000.0000.0000");
						}
						// forcastAmountInfo2.setClientCode(tmpForcastAmountInfo.getClientCode());
						// forcastAmountInfo2.setAccountCode(tmpForcastAmountInfo.getAccountCode());
						// forcastAmountInfo2.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
						// forcastAmountInfo1.setContractCode()
						forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
						forcastAmountInfo2.setContractCode(contractPlanInfo.getContractCode());
						forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// 自营贷款结息
						forcastAmountInfo2.setForecastAmount(interest);
	
						// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
						forecastAmountDAO.add(forcastAmountInfo2);
					}
				}
	
				// 2. 如果是结息日，则进行委托贷款利息、手续费相关数据的预测
				Collection c3 = loan_ExtractorUtilDAO.getLoanContractPlanInfoForInterestSettlemenet(officeID, currencyID, 2);
				Iterator it3 = c3.iterator();
				while (it3.hasNext()) 
				{
					LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it3.next();
					Collection c2 = null;
					if (contractPlanInfo.getSource() == 1) 
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromLoanContractPlanForForecastInterest(contractPlanInfo.getId());
					}
					else 
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromContractFormLoanformPlanForForecastInterest(contractPlanInfo.getId());
					}
					// 计算利息
					Iterator it2 = c2.iterator();
					/**
					 * d) 起息日=上季度未月的21号 终息日= TransactionDate e) 利率结果集. minterestrate
					 * f) 手续费率=结果集. MChargeRate g) 年日利率转换基数=360
					 */
					log.debug("---------------------------------Start---------------");
					while (it2.hasNext()) 
					{
						LoanContractPlanInfo output = (LoanContractPlanInfo) it2.next();
						log.debug("-----LoanContractPlanInfo::PlanDate" + output.getPlanDate());
						log.debug("-----LoanContractPlanInfo::Amount" + output.getAmount());
					}
					log.debug("---------------------------------End---------------");
	
					double interest = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
					double commission = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getChargeRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
					// 6) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getBorrowClientID(), SETTConstant.AccountGroupType.CURRENT);
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));// 自营贷款结息
					forcastAmountInfo1.setForecastAmount(interest);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo1);
	
					// 6) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo1 = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getConsignClientID(), SETTConstant.AccountGroupType.CURRENT);
					glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo1.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo2.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo2.setClientCode(tmpForcastAmountInfo1.getClientCode());
					forcastAmountInfo2.setAccountCode(tmpForcastAmountInfo1.getAccountCode());
					forcastAmountInfo2.setAccountTypeId(tmpForcastAmountInfo1.getAccountTypeId());
					forcastAmountInfo2.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));// 委托贷款结息
					forcastAmountInfo2.setForecastAmount(interest * 0.95);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo2);
	
					// 8) 将委托贷款利息税费有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo3 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					forcastAmountInfo3.setGlSubjectCode("001.000.2020040000.000.000000.0000.0000");
					forcastAmountInfo3.setClientCode(tmpForcastAmountInfo1.getClientCode());
					forcastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo3.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));// 委托贷款结息利息税费
					forcastAmountInfo3.setForecastAmount(interest * 0.05);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo3);
	
					// 9) 将此贷款账户的手续费支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo4 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
	
					glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo4.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo4.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo4.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					forcastAmountInfo4.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					forcastAmountInfo4.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo4.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));//
					forcastAmountInfo4.setForecastAmount(commission);
					forcastAmountInfo4.setGlSubjectCode(glSubjectCode);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo4);
	
					// 10) 将手续费收入有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo5 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					forcastAmountInfo5.setGlSubjectCode("001.000.5020010000.000.000000.0000.0000");
					forcastAmountInfo5.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo5.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo5.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));// 委托贷款结息利息税费
					forcastAmountInfo5.setForecastAmount(commission);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo5);
	
				}
	
				// 进行银团贷款利息相关数据的预测
				Collection c4 = loan_ExtractorUtilDAO.getYTLoanContractPlanInfoForInterestSettlemenet(officeID, currencyID);
				Iterator it4 = c4.iterator();
				while (it4.hasNext()) 
				{
					LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it4.next();
					Collection c2 = null;
					if (contractPlanInfo.getSource() == 1)
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromLoanContractPlanForForecastInterest(contractPlanInfo.getId());
					}
					else 
					{
						c2 = loan_ExtractorUtilDAO.getInfoFromContractFormLoanformPlanForForecastInterest(contractPlanInfo.getId());
					}
	
					/**
					 * d) 起息日=上季度未月的21号 终息日= TransactionDate e) 利率=结果集.
					 * Minterestrate f) 年日利率转换基数=360
					 * 
					 */
	
					double interest = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
					// 5) 将此贷款账户的利息支取信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getConsignClientID(), SETTConstant.AccountGroupType.CURRENT);
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// 自营贷款结息
					forcastAmountInfo1.setForecastAmount(interest);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo1);
	
					// 6) 将贷款利息收入有关信息写到表Trea_ ForecastAmount中
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					if (currencyID == 1)
					{
						forcastAmountInfo2.setGlSubjectCode("001.000.5010010000.000.000000.0000.0000");
					}
					else
					{
						forcastAmountInfo2.setGlSubjectCode("002.000.5010010000.000.000000.0000.0000");
					}
					// forcastAmountInfo2.setClientCode(tmpForcastAmountInfo.getClientCode());
					// forcastAmountInfo2.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					// forcastAmountInfo2.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					// forcastAmountInfo1.setContractCode()
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo2.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// 自营贷款结息
					forcastAmountInfo2.setForecastAmount(interest);
	
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo2);
				}
			}
			forcastDate = DataFormat.getNextDate(forcastDate, 1);
		}
		
		tpConn.commit();
		if (tpConn != null)
		{
			tpConn.close();
		}
		loan_ExtractorUtilDAO.closeModuleConn();
		sett_ExtractorUtilDAO.closeModuleConn();
	}

	public final static long	GRANT	= 1;

	public final static long	REPAY	= 2;

	public final static long	ZY		= 1;	// 自营(include:自营短期贷款,自营中长期贷款,最高限额短期,最高限额中长期)

	public final static long	WT		= 2;	// 委托(inclue: 委托贷款,委托贷款—统借统还)


	private ForecastAmountInfo addLoanInterestInfoToForcastData(LoanContractPlanInfo contractPlanInfo, ForecastAmountInfo forecastAmountInfo, long loanTransTypeID, long accountTypeID, boolean isConsignClient, Sett_ExtractorUtilDAO sett_ExtractorUtilDAO, long officeID, long currencyID,
			Timestamp currencyDate, Timestamp forecastDate, double forcastAmount) throws Exception
	{

		forecastAmountInfo.setForecastAmount(forcastAmount);
		long clientID = -1;
		if ((loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTSettlementInterest || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTSettlementInterestTax) && isConsignClient)
		{
			clientID = contractPlanInfo.getConsignClientID();
		}
		else
		{
			clientID = contractPlanInfo.getBorrowClientID();
		}
		if (clientID > 0) 
		{
			forecastAmountInfo.setClientCode(NameRef.getClientCodeByID(clientID));
			forecastAmountInfo.setAccountCode(sett_ExtractorUtilDAO.getFirstAccountNoOfClient(clientID, accountTypeID));
		}
		forecastAmountInfo.setAccountTypeId(accountTypeID);
		return null;
	}


	private ForecastAmountInfo addLoanInfoToForcastData(LoanContractPlanInfo contractPlanInfo, ForecastAmountInfo forecastAmountInfo, long loanTransTypeID, long accountTypeID, boolean isConsignClient, Sett_ExtractorUtilDAO sett_ExtractorUtilDAO, long officeID, long currencyID,
			Timestamp currencyDate, Timestamp forecastDate, double forcastAmount) throws Exception
	{

		forecastAmountInfo.setForecastAmount(forcastAmount);
		long clientID = -1;
		if ((loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTGrant || loanTransTypeID == TPConstant.TPTransaction.Loan_Transaction.WTRepayment) && isConsignClient)
		{
			clientID = contractPlanInfo.getConsignClientID();
		}
		else
		{
			clientID = contractPlanInfo.getBorrowClientID();
		}
		if (clientID > 0) 
		{
			forecastAmountInfo.setClientCode(NameRef.getClientCodeByID(clientID));
			forecastAmountInfo.setAccountCode(sett_ExtractorUtilDAO.getFirstAccountNoOfClient(clientID, accountTypeID));
		}
		forecastAmountInfo.setAccountTypeId(accountTypeID);
		if (accountTypeID == SETTConstant.AccountGroupType.TRUST // 自营贷款
				|| accountTypeID == SETTConstant.AccountGroupType.CONSIGN || accountTypeID == SETTConstant.AccountGroupType.DISCOUNT) 
		{
			forecastAmountInfo.setContractCode(contractPlanInfo.getContractCode());
			forecastAmountInfo.setGlSubjectCode(sett_ExtractorUtilDAO.getLoanSubjectCodeLoanTypeID(contractPlanInfo.getCurrencyID(), contractPlanInfo.getTypeID(), accountTypeID));
		}
		else 
		{
			long subAccountID = sett_ExtractorUtilDAO.getFirstSubAccountIDByClientID(clientID, accountTypeID);
			if (subAccountID > 0)
			{
				forecastAmountInfo.setGlSubjectCode(sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(subAccountID, AccountOperation.SUBJECT_TYPE_ACCOUNT));
			}
		}
		forecastAmountInfo.setTransactionName(TPConstant.TPTransaction.Loan_Transaction.getNameByID(loanTransTypeID));
		forecastAmountInfo.setRemark(forecastAmountInfo.getClientCode() + contractPlanInfo.getContractCode());
		forecastAmountInfo.setCurrencyID(currencyID);
		forecastAmountInfo.setOfficeID(officeID);
		forecastAmountInfo.setExecuteDate(currencyDate);
		forecastAmountInfo.setTransactionDate(forecastDate);
		forecastAmountInfo.setModuleID(Constant.ModuleType.LOAN);
		return forecastAmountInfo;
	}


	static public void main(String[] args)
	{

		LoanExtractor extractor = new LoanExtractor();
		try 
		{
			if (!TPScheduler.isNeedExecute(null))
				return;
			extractor.startExtractData(args);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastBalance(long,
	 *      long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception
	{
		// TODO Auto-generated method stub
	}
}
