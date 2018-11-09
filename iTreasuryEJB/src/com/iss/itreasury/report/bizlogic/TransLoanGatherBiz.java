package com.iss.itreasury.report.bizlogic;

import java.util.Collection;

import com.iss.itreasury.report.dao.TransLoanGatherDAO;
import com.iss.itreasury.util.IException;

/**
 * 
 * @author Kloud Zhou
 * @date 2010-12-14
 * 贷款汇总帐查询Biz
 */
public class TransLoanGatherBiz {

	TransLoanGatherDAO gatherDAO = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Collection findTransLoanDetail(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception { 
		gatherDAO = new TransLoanGatherDAO();
		Collection coll = null;
		try {
			coll = gatherDAO.findTransLoanDetail(strStartDate, strEndDate, lLoanTypeId, strContractStatusId);
		} catch(IException ie) {
			throw ie;
		} catch(Exception e) {
			throw new IException("查询贷款明细交易失败，请检查");
		} 
		return coll;
	}
	
	public double getSumBalanceOfLoanGather(String strStartDate,String strEndDate,long lLoanTypeId,String strContractStatusId) throws Exception {
		gatherDAO = new TransLoanGatherDAO();
		double dbSumBalance = 0.00;
		try {
			dbSumBalance = gatherDAO.getSumBalanceOfLoanGather(strStartDate, strEndDate, lLoanTypeId, strContractStatusId);
		} catch(IException ie) {
			throw ie;
		} catch(Exception e) {
			throw new IException("查询贷款明细交易失败，请检查");
		} 
		return dbSumBalance;
	}
}
