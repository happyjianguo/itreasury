package com.iss.itreasury.settlement.report.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report13Dao;
import com.iss.itreasury.util.ITreasuryException;

public class Report13 {

	Report13Dao dao = new Report13Dao();
	
	/**
	 * 查询业务月度交易总量
	 * @param date
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection querySettlementTotalBusiness(QueryFixedDepositInfo qInfo)
			throws ITreasuryException {
		Collection results = dao.querySettlementTotalBusiness(qInfo);
		return results;
	}
	
	/**
	 * 查询网银月度交易总量
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection querySettlementTotalOnlineBank(QueryFixedDepositInfo qInfo) throws ITreasuryException {
		Collection results = dao.querySettlementTotalOnlineBank(qInfo);
		return results;
	}
	
	/**
	 * 获取当前年的前几年
	 * @param yearNum
	 * @return
	 * @throws ITreasuryException
	 */
	public Collection getBeforeYear(long yearNum) throws ITreasuryException {
		Collection results = dao.getBeforeYear(yearNum);
		return results;
	}
	
}
