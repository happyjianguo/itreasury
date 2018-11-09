package com.iss.itreasury.report.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.report.dao.LoanInterestReportDAO;
import com.iss.itreasury.report.dateentity.LoanContractInfo;
import com.iss.itreasury.report.dateentity.LoanInterestReportInfo;
import com.iss.itreasury.report.dateentity.SettLoanInterestInfo;
import com.iss.itreasury.settlement.clearinterestplanSetting.dao.ClearinterestplanDao;
import com.iss.itreasury.settlement.clearinterestplanSetting.dataentity.ClearinterestplanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.opensymphony.util.Data;
/**
 * 报表利息收息情况报表Biz
 * @author yunzhou
 * @date 2010-01-10
 */
public class LoanInterestReportBiz {
	
	/**
	 * 生成报表利息收息情况表的数据
	 * @author yunzhou
	 * @date 2011-01-17
	 * @return
	 * @throws Exception
	 */
	public boolean CreateLoanInterestReportData() throws Exception{
		LoanInterestReportDAO reportDAO = new LoanInterestReportDAO();
		Connection conn = null;
		boolean isSuccess = false;
		
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			//创建报表数据之前，先删除所有的报表数据
			System.out.println("删除报表数据开始~~~~");
			conn = reportDAO.cleanLoanInterestReport(conn);
			System.out.println("删除报表数据结束~~~~");
			
			//获取所有的贷款合同集合
			Collection loanColl = null;
			Collection reportColl = null;
			loanColl = reportDAO.getLoanContractList();
			if(loanColl != null) {
				Iterator it = loanColl.iterator();
				LoanContractInfo contractInfo = null;
				long lLoanContractId = -1;
				long lLoanCleanType = -1;	//贷款结息方式 2 按季结息，3按月结息
				long lCleanDate = -1;		//贷款结息日
				String strStartDate = "2011-01-01";	//所有的报表数据从2011年1月1日开始,葛洲坝资金系统是2011年1月1日上线
				String strEndDate = DataFormat.formatDate(Env.getSystemDate(1,1));
				String[] strCleanMD = null;		//结息月和日
				long lYears = Long.parseLong(strEndDate.substring(0, 4)) -  Long.parseLong(strStartDate.substring(0, 4));
				long lStartYear = Long.parseLong(strEndDate.substring(0, 4));
				String strYear = "";
				String strCurrentCleanInterestDate = "";		//本期结息日
				String strLastCleanInterestDate = "";	//上期结息日 初始化为资金系统上线日期，2011年1月1日
				Collection settLoanInterestList = null;
				
				double dbSQQInterest = 0.00;	//收前期利息
				double dbSBQInterest = 0.00;	//收本期利息
				double dbYSInterest = 0.00;		//应收利息
				double dbSSInterest = 0.00;		//实收利息
				double dbWSInterest = 0.00;		//未收利息
				
				if(it != null) {
					while(it.hasNext()) {
						contractInfo = (LoanContractInfo)it.next();
						lLoanContractId = contractInfo.getLContractId();
						System.out.println("*********************************************************************合同ID为：" + lLoanContractId);
						lLoanCleanType = contractInfo.getLCleartype();
						lCleanDate = contractInfo.getLCleartime();
						if(lLoanCleanType == SETTConstant.ClearInterestPlayClearType.SEASON) {
							strCleanMD = new String[]{"03-"+lCleanDate,"06-"+lCleanDate,"09-"+lCleanDate,"12-"+lCleanDate};
						} else if(lLoanCleanType == SETTConstant.ClearInterestPlayClearType.MONTH) {
							strCleanMD = new String[]{"01-"+lCleanDate,"02-"+lCleanDate,"03-"+lCleanDate,"04-"+lCleanDate,"05-"+lCleanDate,"06-"+lCleanDate,
									"07-"+lCleanDate,"08-"+lCleanDate,"09-"+lCleanDate,"10-"+lCleanDate,"11-"+lCleanDate,"12-"+lCleanDate};
						}
						//System.out.println("strCleanMD = " + strCleanMD.toString());
						//System.out.println("strCleanMD.length = " + strCleanMD.length);
						strLastCleanInterestDate = strStartDate;
						for(int nYearIndex = 0;nYearIndex <= lYears;nYearIndex++) {
							strYear = "" + (lStartYear + nYearIndex);
							//SettLoanInterestList = new ArrayList();
							//SettLoanInterestList.clear();
							for(int nMonthIndex = 0;nMonthIndex < strCleanMD.length;nMonthIndex++) {
								strCurrentCleanInterestDate = strYear + "-" + strCleanMD[nMonthIndex];
								System.out.println("lLoanContractId = " + lLoanContractId);
								System.out.println("合同ID : " + lLoanContractId);
								System.out.println("上次结息日 ：" + strLastCleanInterestDate);
								System.out.println("本次结息日 ：" + strCurrentCleanInterestDate);
								settLoanInterestList = reportDAO.getTransLoanInterestList(strLastCleanInterestDate, strCurrentCleanInterestDate
										, lLoanContractId);
								
						//***********************************************************************************************
								System.out.println("生成应收应付利息开始");
								if(settLoanInterestList != null) {
									Iterator lIt = settLoanInterestList.iterator();
									if(lIt != null) {
										SettLoanInterestInfo settInfo = null;
										LoanInterestReportInfo reportInfo = null;
										while(lIt.hasNext()) {
											settInfo = (SettLoanInterestInfo)lIt.next();
											reportInfo =  new LoanInterestReportInfo();
											reportInfo.setLContractId(settInfo.getLContractId());
											reportInfo.setLLoanAccountId(settInfo.getLLoanAccountId());
											reportInfo.setLLoanReceivedAccountId(settInfo.getLReceiveAccountId());
											reportInfo.setLLoanClientId(settInfo.getLClientId());
											reportInfo.setStrDate(DataFormat.formatDate(settInfo.getTsExecuteDate()));
											reportInfo.setStrInterestDate(DataFormat.formatDate(settInfo.getTsInterestDate()));
											
											dbYSInterest = reportDAO.getLoanInterest(reportInfo.getStrInterestDate(), settInfo.getLContractId());
											dbSSInterest = settInfo.getDbInterest();
											dbWSInterest = dbYSInterest - dbSSInterest;
											if(strLastCleanInterestDate != null && strLastCleanInterestDate.equals(strStartDate)) {
												dbSQQInterest = 0.00;
											} else {
												dbSQQInterest = getSQQInterest(strLastCleanInterestDate,settInfo.getLContractId());
											}
											dbSBQInterest = dbSSInterest - dbSQQInterest;
											if(dbSBQInterest < 0) {
												dbSBQInterest = 0.00;
											}
											System.out.println("应收利息：" + dbYSInterest);
											System.out.println("实收利息：" + dbSSInterest);
											System.out.println("未收利息：" + dbWSInterest);
											System.out.println("收前期利息：" + dbSQQInterest);
											System.out.println("收本期利息：" + dbSBQInterest);
											
											reportInfo.setDbYSInterest(dbYSInterest);
											reportInfo.setDbSSInterest(dbSSInterest);
											reportInfo.setDbWSInterest(dbWSInterest);
											reportInfo.setDbBQQXInterest(dbWSInterest);
											reportInfo.setDbSBQInterest(dbSBQInterest);
											reportInfo.setDbSQQInterest(dbSQQInterest);
											conn = reportDAO.saveLoanInterestReport(reportInfo, conn);
										}
									}
								}
								System.out.println("生成应收应付利息结束");
								strLastCleanInterestDate = strCurrentCleanInterestDate;
						//***********************************************************************************************
							}
						}
						
						
					}
				}
			}
			conn.commit();
			isSuccess = true;
		} catch(IException ie) {
			conn.rollback();
			ie.printStackTrace();
			isSuccess = false;
			throw ie;
		} catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
			isSuccess = false;
			throw new IException("生成应收应付利息数据失败");
		} finally {
			if(conn != null)
				conn.close();
		}
		return isSuccess;
	}
	
	/**
	 * 获取收前期利息
	 * @author yunzhou
	 * @date 2011-01-25
	 * @param strDate
	 * @param lContractId
	 * @return
	 */
	public double getSQQInterest(String strDate,long lContractId) {
		double dbSQQInterest = 0.00;		//收前期利息
		double dbQQYSInterest = 0.00;		//前期应收利息
		double dbQQSSInterest = 0.00;		//前期实收利息
		Collection QQSSTransColl = null;	//前期实收利息交易
		LoanInterestReportDAO reportDAO = new LoanInterestReportDAO();
		
		try {
			QQSSTransColl = reportDAO.getTransLoanInterestList(strDate, strDate, lContractId);
			if(QQSSTransColl != null) {
				Iterator it = QQSSTransColl.iterator();
				SettLoanInterestInfo settInfo = null;
				while(it.hasNext()) {
					settInfo = (SettLoanInterestInfo)it.next();
					dbQQSSInterest += settInfo.getDbInterest();
				}
			}
			
			dbQQYSInterest = reportDAO.getLoanInterest(strDate, lContractId);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		dbSQQInterest = dbQQYSInterest - dbQQSSInterest;
		if(dbSQQInterest < 0) {
			dbSQQInterest = 0.00;
		}
		return dbSQQInterest;
	}
	
	
}
