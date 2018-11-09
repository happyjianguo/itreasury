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
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Gl_ExtractorUtilDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ActualBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualAmountInfo;
import com.iss.itreasury.treasuryplan.etl.extract.dataentity.ActualBalanceInfo;
import com.iss.itreasury.treasuryplan.etl.scheduler.bizlogic.TPScheduler;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GeneralLedgerExtractor extends AbstractExtractor {

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractActualBalance(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractActualBalance(long officeID, long currencyID, Timestamp startDate, Timestamp endDate) throws Exception {	
			
		Gl_ExtractorUtilDAO extractorUtilDAO = new Gl_ExtractorUtilDAO();
		
		Collection c = extractorUtilDAO.GetGlBalanceInfo(officeID, currencyID, startDate, endDate);	
		Iterator it = c.iterator();		
		Connection tpConn = extractorUtilDAO.getConnectionByModuleID(Constant.ModuleType.PLAN);	
		tpConn.setAutoCommit(false);	
		Trea_ActualBalanceDAO balanceDao = new Trea_ActualBalanceDAO(tpConn);
		balanceDao.deleteByTransactionDateAndModuleID(startDate,endDate,Constant.ModuleType.GENERALLEDGER,officeID, currencyID);
		
		Trea_ActualAmountDAO amountDao = new Trea_ActualAmountDAO(tpConn);
		amountDao.deleteByTransactionDateAndModuleID(startDate,endDate,Constant.ModuleType.GENERALLEDGER,officeID, currencyID);
		while(it.hasNext()){			
			GLBalanceInfo tmp = (GLBalanceInfo) it.next();

			//余额抽取 如果没有发生额，也要取得余额
			ActualBalanceInfo balanceInfo = new ActualBalanceInfo();
			balanceInfo.setGlSubjectCode(tmp.getGLSubjectCode());
			balanceInfo.setActualBalance(tmp.getDebitBalance()+tmp.getCreditBalance());
			balanceInfo.setOfficeID(officeID);
			balanceInfo.setCurrencyID(currencyID);
			balanceInfo.setModuleID(TPConstant.ModuleType.GENERALLEDGER);
			//总账交易日取交易日
			balanceInfo.setTransactionDate(tmp.getGLDate());
			balanceInfo.setExecuteDate(Env.getSystemDate());
			balanceDao.add(balanceInfo);

			//发生额的抽取
			if(tmp.getDebitAmount() > 0 || tmp.getCreditAmount() > 0){
				
			}else
				continue;

			ActualAmountInfo dAmountInfo = new ActualAmountInfo();	
			ActualAmountInfo cAmountInfo = new ActualAmountInfo();			
				if(tmp.getBalanceDirection() == SETTConstant.DebitOrCredit.DEBIT){
					dAmountInfo.setGlSubjectCode(tmp.getGLSubjectCode());			
					dAmountInfo.setActualAmount(tmp.getDebitAmount());
					dAmountInfo.setOfficeID(officeID);
					dAmountInfo.setCurrencyID(currencyID);
					dAmountInfo.setModuleID(TPConstant.ModuleType.GENERALLEDGER);
					dAmountInfo.setTransactionDate(tmp.getGLDate());
					dAmountInfo.setExecuteDate(Env.getSystemDate());
					dAmountInfo.setAmountDirection(1);
					
					cAmountInfo.setGlSubjectCode(tmp.getGLSubjectCode());			
					cAmountInfo.setActualAmount(tmp.getCreditAmount());
					cAmountInfo.setOfficeID(officeID);
					cAmountInfo.setCurrencyID(currencyID);
					cAmountInfo.setModuleID(TPConstant.ModuleType.GENERALLEDGER);
					cAmountInfo.setTransactionDate(tmp.getGLDate());
					cAmountInfo.setExecuteDate(Env.getSystemDate());						
					cAmountInfo.setAmountDirection(2);
				}else{
					dAmountInfo.setGlSubjectCode(tmp.getGLSubjectCode());			
					dAmountInfo.setActualAmount(tmp.getDebitAmount());
					dAmountInfo.setOfficeID(officeID);
					dAmountInfo.setCurrencyID(currencyID);
					dAmountInfo.setModuleID(TPConstant.ModuleType.GENERALLEDGER);
					dAmountInfo.setTransactionDate(tmp.getGLDate());
					dAmountInfo.setExecuteDate(Env.getSystemDate());
					dAmountInfo.setAmountDirection(2);
					
					cAmountInfo.setGlSubjectCode(tmp.getGLSubjectCode());			
					cAmountInfo.setActualAmount(tmp.getCreditAmount());
					cAmountInfo.setOfficeID(officeID);
					cAmountInfo.setCurrencyID(currencyID);
					cAmountInfo.setModuleID(TPConstant.ModuleType.GENERALLEDGER);
					cAmountInfo.setTransactionDate(tmp.getGLDate());
					cAmountInfo.setExecuteDate(Env.getSystemDate());						
					cAmountInfo.setAmountDirection(1);					
				}
			amountDao.add(dAmountInfo);
			amountDao.add(cAmountInfo);							
			
		}
		tpConn.commit();
		balanceDao.closeModuleConn(TPConstant.ModuleType.PLAN);
		extractorUtilDAO.closeModuleConn();
//		log.debug("-----end extractActualBalance------");
	}

	/* 
	 * 为了提高效率，实际余额和发生额都在发生额抽取此进行处理
	 */
	public void extractActualAmount(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception {
//		log.debug("-----start extractActualAmount------");
//		log.debug("-----nothing to do------");
		}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbastractExtractor#extractForcastAmount(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastAmount(long officeID, long currencyID, Timestamp extractDate, Timestamp forcastDate) throws Exception {
//		log.debug("-----start extractForcastAmount------");
//		log.debug("-----nothing to do------");
		
	}
	
	static public void main(String[] args){
		GeneralLedgerExtractor extractor = new GeneralLedgerExtractor();
		try {
			if(!TPScheduler.isNeedExecute(null))
				return;
			extractor.startExtractData(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.treasuryplan.etl.extract.bizlogic.AbstractExtractor#extractForcastBalance(long, long, java.sql.Timestamp, java.sql.Timestamp)
	 */
	public void extractForcastBalance(long officeID, long currencyID, Timestamp currentDate, Timestamp openDate) throws Exception {
		// TODO Auto-generated method stub
		
	} 

}
