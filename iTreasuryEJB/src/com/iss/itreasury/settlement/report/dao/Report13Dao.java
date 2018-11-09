package com.iss.itreasury.settlement.report.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.report.dataentity.ReportResultInfo;

public class Report13Dao extends ITreasuryDAO {

	public Collection querySettlementTotalBusiness(QueryFixedDepositInfo qInfo) throws ITreasuryDAOException {
		Collection results = new ArrayList();
		StringBuffer strSql = new StringBuffer();
		initDAO();
		strSql.append(" select months,count(1) as num,sum(nvl(receiveamount,0)) as sumAmount from( \n");
		strSql.append(" select v.receiveamount, to_char(v.execute,'mm') as months \n");
		strSql.append(" from sett_vtransaction v where v.statusid = 3 \n");
		strSql.append(" and to_char(v.execute,'yyyy') = '"+qInfo.getExtendAttribute5()+"' \n");
		strSql.append(" and to_char(v.execute,'mm') >= "+qInfo.getExtendAttribute1()+" \n");
		strSql.append(" and to_char(v.execute,'mm') <= "+qInfo.getExtendAttribute2()+" \n");
		strSql.append(" and v.officeid = "+qInfo.getOfficeID()+" \n");
		strSql.append(" and v.currencyid = "+qInfo.getCurrencyID()+" ) \n");
		strSql.append(" group by months order by months ");
		System.out.println("SQL=======>>"+strSql.toString());
		try {
			prepareStatement(strSql.toString());
			transRS = executeQuery();
			while(transRS.next()){
				ReportResultInfo report = new ReportResultInfo();
				report.setLongColumn1(transRS.getLong("months"));
				report.setLongColumn2(transRS.getLong("num"));
				report.setDoubleColumn1(transRS.getDouble("sumAmount"));
				results.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return results;
	}
	
	public Collection querySettlementTotalOnlineBank(QueryFixedDepositInfo qInfo) throws ITreasuryDAOException {
		Collection results = new ArrayList();
		StringBuffer strSql = new StringBuffer();
		initDAO();
		strSql.append(" select months,count(id) as num,sum(nvl(receiveamount,0)) as sumAmount from( \n");
		strSql.append(" select f.id, v.receiveamount, to_char(v.execute,'mm') as months \n");
		strSql.append(" from ob_financeinstr f, sett_vtransaction v \n");
		strSql.append(" where f.nstatus = 5 and f.cpf_stransno = v.transno \n");
		strSql.append(" and v.statusid = 3 and to_char(v.execute,'yyyy') = '"+qInfo.getExtendAttribute5()+"' \n");
		strSql.append(" and to_char(v.execute,'mm') >= "+qInfo.getExtendAttribute1()+" \n");
		strSql.append(" and to_char(v.execute,'mm') <= "+qInfo.getExtendAttribute2()+" \n");
		strSql.append(" and v.officeid = "+qInfo.getOfficeID()+" and v.currencyid = "+qInfo.getCurrencyID()+" ) group by months order by months \n");
		System.out.println("SQL=======>>"+strSql.toString());
		try {
			prepareStatement(strSql.toString());
			transRS = executeQuery();
			while(transRS.next()){
				ReportResultInfo report = new ReportResultInfo();
				report.setLongColumn1(transRS.getLong("months"));
				report.setLongColumn2(transRS.getLong("num"));
				report.setDoubleColumn1(transRS.getDouble("sumAmount"));
				results.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return results;
	}
	
	/**
	 * 获取当前年的前几年
	 * @param yearNum 年数
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getBeforeYear(long yearNum) throws ITreasuryDAOException {
		Collection results = new ArrayList();
		StringBuffer strSql = new StringBuffer();
		initDAO();
		strSql.append("select ((select to_char(sysdate,'yyyy') from dual) - rownum + 1) years from dual connect by rownum <= "+yearNum+" ");
		System.out.println("SQL=======>>"+strSql.toString());
		try {
			prepareStatement(strSql.toString());
			transRS = executeQuery();
			while(transRS.next()){
				ReportResultInfo report = new ReportResultInfo();
				report.setLongColumn1(transRS.getLong("years"));
				results.add(report);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("数据库异常发生", e);
		} finally{
			finalizeDAO();
		}
		return results;
	}

}
