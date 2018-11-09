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
	 *      ��ע�������ȡ���߼��õ����˻����ͣ��Ŵ���ȡ���߼��õ����˻������ͣ�ì�ܡ�
	 *      ��������Ϊ��ͳһ���ڷ���extractForcastAmount()�У����Ŵ�ȡ�����ֵ��˻������ͻ�����˻�����
	 *      �漰�ķ�����addLoanInfoToForcastData()
	 *      ���⣺��Ӳ����ķ�ʽ���㣬�������⣬���Ż���
	 *      gqfang , 2005.12.29
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception
	{

		log.debug("###--------���Ŵ�ģ���ȡԤ�ⷢ���� START.......................................");
		Loan_ExtractorUtilDAO loan_ExtractorUtilDAO = new Loan_ExtractorUtilDAO();
		Sett_ExtractorUtilDAO sett_ExtractorUtilDAO = new Sett_ExtractorUtilDAO();

		Timestamp openDate = loan_ExtractorUtilDAO.getOfficeOpenDate(officeID, currencyID);
		
		/**
		 * ��Ӫ����ű���������ݵ�Ԥ��
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
			log.debug("111��Ӫ����ű���----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("111��Ӫ����ű���----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYGrant, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			
			log.debug("111��Ӫ����ű���----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
		}

		log.debug("--------������Ӫ����ű���������ݵ�Ԥ��---------");
		/**
		 * ��Ӫ�����ջر���������ݵ�Ԥ��
		 */
		log.debug("--------��ʼ��Ӫ�����ջر���������ݵ�Ԥ��---------");
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate , endDate , TPConstant.TPTransaction.Loan_Transaction.ZYRepayment);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("222��Ӫ�����ջر���----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("222��Ӫ�����ջر���----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, SETTConstant.AccountGroupType.TRUST, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.ZYRepayment, 8 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("222��Ӫ�����ջر���----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------������Ӫ�����ջر���������ݵ�Ԥ��---------");
		/**
		 * ί�д���ű���������ݵ�Ԥ��
		 */
		log.debug("--------��ʼί�д���ű���������ݵ�Ԥ��---------");
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate,endDate, TPConstant.TPTransaction.Loan_Transaction.WTGrant);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("333ί�д���ű���----1-----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333ί�д���ű���----2-----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CURRENT, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333ί�д���ű���----3-----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			//forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTGrant, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333ί�д���ű���----4-----forecastAmountInfo3:" + forecastAmountInfo3);
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo3);
			
			ForecastAmountInfo forecastAmountInfo4 = new ForecastAmountInfo();
			//forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTGrant, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTGrant, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("333ί�д���ű���-----5----forecastAmountInfo4:" + forecastAmountInfo4);
			forecastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo4);
		}
		log.debug("--------����ί�д���ű���������ݵ�Ԥ��---------");
		log.debug("--------��ʼί�д����ջر���������ݵ�Ԥ��---------");
		/**
		 * ί�д����ջر���������ݵ�Ԥ��
		 */
		c = loan_ExtractorUtilDAO.getLoanForcastInfoByCondition(officeID, currencyID, startDate,endDate, TPConstant.TPTransaction.Loan_Transaction.WTRepayment);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("444ί�д����ջر���-----1----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CURRENT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444ί�д����ջر���-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CURRENT, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444ί�д����ջر���-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
			
			ForecastAmountInfo forecastAmountInfo3 = new ForecastAmountInfo();
			//forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CONSIGN, true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo3 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo3, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, 9 , true, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444ί�д����ջر���-----4----forecastAmountInfo3:" + forecastAmountInfo3);
			forecastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo3);
			
			ForecastAmountInfo forecastAmountInfo4 = new ForecastAmountInfo();
			//forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, SETTConstant.AccountGroupType.CONSIGN, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			forecastAmountInfo4 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo4, TPConstant.TPTransaction.Loan_Transaction.WTRepayment, 9 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(),  contractPlanInfo.getPlanDate(), contractPlanInfo.getAmount());
			log.debug("444ί�д����ջر���-----5----forecastAmountInfo4:" + forecastAmountInfo4);
			forecastAmountInfo4.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo4);
		}
		log.debug("--------����ί�д����ջر���������ݵ�Ԥ��---------");
		log.debug("--------��ʼ���ִ����������ݵ�Ԥ��---------");
		// ���ִ����������ݵ�Ԥ��
		c = loan_ExtractorUtilDAO.getDiscountGrantForecastInfoByCondtion(officeID, currencyID, startDate , endDate);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("555���ִ�����������-----1----contractPlanInfo:" + contractPlanInfo);
			ForecastAmountInfo forecastAmountInfo1 = new ForecastAmountInfo();
			//forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXGrant, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getCheckAmount());
			forecastAmountInfo1 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo1, TPConstant.TPTransaction.Loan_Transaction.TXGrant, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getCheckAmount());
			log.debug("555���ִ�����������-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXGrant, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getExamineAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXGrant, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID, Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getExamineAmount());
			log.debug("555���ִ�����������-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------�������ִ����������ݵ�Ԥ��---------");
		log.debug("--------��ʼ����Ʊ�ݵ����ջر���/��Ϣ������ݵ�Ԥ��---------");
		// ����Ʊ�ݵ����ջر���/��Ϣ������ݵ�Ԥ��
		c = loan_ExtractorUtilDAO.getDiscountRepaymentForecastInfoByCondtion(officeID, currencyID , startDate , endDate);
		it = c.iterator();
		while (it.hasNext()) 
		{
			LoanContractPlanInfo contractPlanInfo = (LoanContractPlanInfo) it.next();
			log.debug("666 ����Ʊ�ݵ����ջر���/��Ϣ-----1----contractPlanInfo:" + contractPlanInfo);
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
			log.debug("666 ����Ʊ�ݵ����ջر���/��Ϣ-----2----forecastAmountInfo1:" + forecastAmountInfo1);
			forecastAmountDAO.add(forecastAmountInfo1);
			
			ForecastAmountInfo forecastAmountInfo2 = new ForecastAmountInfo();
			//forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, SETTConstant.AccountGroupType.DISCOUNT, false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount()- contractPlanInfo.getCheckAmount());
			forecastAmountInfo2 = addLoanInfoToForcastData(contractPlanInfo, forecastAmountInfo2, TPConstant.TPTransaction.Loan_Transaction.TXRepayment, 10 , false, sett_ExtractorUtilDAO, officeID, currencyID,  Env.getSystemDate(), contractPlanInfo.getDiscountDate(), contractPlanInfo.getAmount()- contractPlanInfo.getCheckAmount());
			forecastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
			forecastAmountInfo2.setAccountTypeId(-1);
			log.debug("666 ����Ʊ�ݵ����ջر���/��Ϣ-----3----forecastAmountInfo2:" + forecastAmountInfo2);
			forecastAmountDAO.add(forecastAmountInfo2);
		}
		log.debug("--------��������Ʊ�ݵ����ջر���/��Ϣ������ݵ�Ԥ��---------");
		log.debug("--------��ʼ���Ŵ���ű���������ݵ�Ԥ��---------");
		// ���Ŵ���ű���������ݵ�Ԥ��
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
			
			// ���������гд������й���Ϣ
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
		log.debug("--------�������Ŵ���ű���������ݵ�Ԥ��---------");
		log.debug("--------��ʼ���Ŵ���ű���������ݵ�Ԥ��---------");
		// ���Ŵ���ű���������ݵ�Ԥ��
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
			
			// ���������гд������й���Ϣ
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
		log.debug("--------�������Ŵ���ű���������ݵ�Ԥ��---------");
		

		Timestamp forcastDate = startDate;
		while (!forcastDate.after(endDate))
		{
			if (loan_ExtractorUtilDAO.isSettlemtInterestDate(forcastDate)) 
			{
				log.debug("----------����" + forcastDate + "�ǽ�Ϣ��");
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
					// ������Ϣ
					Iterator it2 = c2.iterator();
					while (it2.hasNext()) 
					{
						LoanContractPlanInfo contractPlanInfo1 = (LoanContractPlanInfo) it2.next();
						/**
						 * d) ��Ϣ��=�ϼ���δ�µ�21�� ��Ϣ��= TransactionDate e) ����=�����.
						 * Minterestrate f) ��������ת������=360
						 * 
						 */
	
						double interest = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
						// 5) ���˴����˻�����Ϣ֧ȡ��Ϣд����Trea_ ForecastAmount��
						ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
						ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountByClientIDToForecastAmountInfo(contractPlanInfo.getBorrowClientID(), SETTConstant.AccountGroupType.CURRENT);
						String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
						forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
						forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
						forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
						forcastAmountInfo1.setAccountTypeId(SETTConstant.AccountGroupType.CURRENT);
						forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
						forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
						forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// ��Ӫ�����Ϣ
						forcastAmountInfo1.setForecastAmount(interest);
						forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
						// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
						forecastAmountDAO.add(forcastAmountInfo1);
	
						// 6) ��������Ϣ�����й���Ϣд����Trea_ ForecastAmount��
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
						forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// ��Ӫ�����Ϣ
						forcastAmountInfo2.setForecastAmount(interest);
	
						// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
						forecastAmountDAO.add(forcastAmountInfo2);
					}
				}
	
				// 2. ����ǽ�Ϣ�գ������ί�д�����Ϣ��������������ݵ�Ԥ��
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
					// ������Ϣ
					Iterator it2 = c2.iterator();
					/**
					 * d) ��Ϣ��=�ϼ���δ�µ�21�� ��Ϣ��= TransactionDate e) ���ʽ����. minterestrate
					 * f) ��������=�����. MChargeRate g) ��������ת������=360
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
	
					// 6) ���˴����˻�����Ϣ֧ȡ��Ϣд����Trea_ ForecastAmount��
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getBorrowClientID(), SETTConstant.AccountGroupType.CURRENT);
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));// ��Ӫ�����Ϣ
					forcastAmountInfo1.setForecastAmount(interest);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo1);
	
					// 6) ���˴����˻�����Ϣ֧ȡ��Ϣд����Trea_ ForecastAmount��
					ForecastAmountInfo forcastAmountInfo2 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo1 = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getConsignClientID(), SETTConstant.AccountGroupType.CURRENT);
					glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo1.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo2.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo2.setClientCode(tmpForcastAmountInfo1.getClientCode());
					forcastAmountInfo2.setAccountCode(tmpForcastAmountInfo1.getAccountCode());
					forcastAmountInfo2.setAccountTypeId(tmpForcastAmountInfo1.getAccountTypeId());
					forcastAmountInfo2.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo2.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlement));// ί�д����Ϣ
					forcastAmountInfo2.setForecastAmount(interest * 0.95);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo2);
	
					// 8) ��ί�д�����Ϣ˰���й���Ϣд����Trea_ ForecastAmount��
					ForecastAmountInfo forcastAmountInfo3 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					forcastAmountInfo3.setGlSubjectCode("001.000.2020040000.000.000000.0000.0000");
					forcastAmountInfo3.setClientCode(tmpForcastAmountInfo1.getClientCode());
					forcastAmountInfo3.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo3.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));// ί�д����Ϣ��Ϣ˰��
					forcastAmountInfo3.setForecastAmount(interest * 0.05);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo3);
	
					// 9) ���˴����˻���������֧ȡ��Ϣд����Trea_ ForecastAmount��
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
	
					// 10) �������������й���Ϣд����Trea_ ForecastAmount��
					ForecastAmountInfo forcastAmountInfo5 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					forcastAmountInfo5.setGlSubjectCode("001.000.5020010000.000.000000.0000.0000");
					forcastAmountInfo5.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo5.setAmountDirection(TPConstant.AmountDirection.PLUS);
					forcastAmountInfo5.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.ConsignLoanSettlementCommission));// ί�д����Ϣ��Ϣ˰��
					forcastAmountInfo5.setForecastAmount(commission);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo5);
	
				}
	
				// �������Ŵ�����Ϣ������ݵ�Ԥ��
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
					 * d) ��Ϣ��=�ϼ���δ�µ�21�� ��Ϣ��= TransactionDate e) ����=�����.
					 * Minterestrate f) ��������ת������=360
					 * 
					 */
	
					double interest = caculateInterest(forcastDate, openDate, DataFormat.getNextMonth(forcastDate, -3), forcastDate, contractPlanInfo.getRate(), SETTConstant.InterestRateDaysFlag.DAYS_360, (ArrayList) c2, 0.0);
	
					// 5) ���˴����˻�����Ϣ֧ȡ��Ϣд����Trea_ ForecastAmount��
					ForecastAmountInfo forcastAmountInfo1 = new ForecastAmountInfo(officeID, currencyID, Env.getSystemDate(), forcastDate, Constant.ModuleType.LOAN);
					ForecastAmountInfo tmpForcastAmountInfo = sett_ExtractorUtilDAO.getFirstSubAccountIDUnderSameClientBySubAccountID(contractPlanInfo.getConsignClientID(), SETTConstant.AccountGroupType.CURRENT);
					String glSubjectCode = sett_ExtractorUtilDAO.getSubjectCodeBySubAccountID(tmpForcastAmountInfo.getSubAccountID(), AccountOperation.SUBJECT_TYPE_ACCOUNT);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					forcastAmountInfo1.setClientCode(tmpForcastAmountInfo.getClientCode());
					forcastAmountInfo1.setAccountCode(tmpForcastAmountInfo.getAccountCode());
					forcastAmountInfo1.setAccountTypeId(tmpForcastAmountInfo.getAccountTypeId());
					forcastAmountInfo1.setContractCode(contractPlanInfo.getContractCode());
					forcastAmountInfo1.setAmountDirection(TPConstant.AmountDirection.SUBTRACT);
					forcastAmountInfo1.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// ��Ӫ�����Ϣ
					forcastAmountInfo1.setForecastAmount(interest);
					forcastAmountInfo1.setGlSubjectCode(glSubjectCode);
					// forcastAmountInfo1.setRemark(forcastAmountInfo1.getAccountCode()+fixedDepositInfo.getContractCode());
					forecastAmountDAO.add(forcastAmountInfo1);
	
					// 6) ��������Ϣ�����й���Ϣд����Trea_ ForecastAmount��
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
					forcastAmountInfo2.setTransactionName(TPConstant.TPTransaction.Interest_Transaction.getNameByID(TPConstant.TPTransaction.Interest_Transaction.TrustLoanSettlement));// ��Ӫ�����Ϣ
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

	public final static long	ZY		= 1;	// ��Ӫ(include:��Ӫ���ڴ���,��Ӫ�г��ڴ���,����޶����,����޶��г���)

	public final static long	WT		= 2;	// ί��(inclue: ί�д���,ί�д��ͳ��ͳ��)


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
		if (accountTypeID == SETTConstant.AccountGroupType.TRUST // ��Ӫ����
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
