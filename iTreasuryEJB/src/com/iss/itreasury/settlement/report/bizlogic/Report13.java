package com.iss.itreasury.settlement.report.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dao.Report13Dao;
import com.iss.itreasury.util.ITreasuryException;

public class Report13 {

	Report13Dao dao = new Report13Dao();
	
	/**
	 * ��ѯҵ���¶Ƚ�������
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
	 * ��ѯ�����¶Ƚ�������
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection querySettlementTotalOnlineBank(QueryFixedDepositInfo qInfo) throws ITreasuryException {
		Collection results = dao.querySettlementTotalOnlineBank(qInfo);
		return results;
	}
	
	/**
	 * ��ȡ��ǰ���ǰ����
	 * @param yearNum
	 * @return
	 * @throws ITreasuryException
	 */
	public Collection getBeforeYear(long yearNum) throws ITreasuryException {
		Collection results = dao.getBeforeYear(yearNum);
		return results;
	}
	
}
