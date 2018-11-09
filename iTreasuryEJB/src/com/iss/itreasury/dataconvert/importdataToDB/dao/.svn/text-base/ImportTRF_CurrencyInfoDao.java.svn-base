/*
 * Created on 2006-4-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dao;

import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_CurrencyInfoDao extends DataTransplantBaseDao {
    public long queryInterestRatePlanIdByInterestRate(double rate) {
        long result = -1;
        try {
            String strSql1 = "select id from sett_interestRate where fRate="
                    + rate + " \n";
            this.initDAO();
            this.prepareStatement(strSql1);
            this.executeQuery();
            long interestRateId = -1;
            if (this.resultSet.next()) {
                interestRateId = this.resultSet.getLong(1);
            }
            this.finalizeDAO();
            String strSql2 = "select id from sett_interestRatePlanItem where nInterestRateId="
                    + interestRateId + " \n";
            this.initDAO();
            this.prepareStatement(strSql2);
            this.executeQuery();
            if (this.resultSet.next()) {
                result = this.resultSet.getLong(1);
            }
        } catch (Exception e) {
            throw new TRF_Exception(
                    "error occurs when querying interestrateplanid", e);
        } finally {
            this.finalizeDAO();
        }
        return result;
    }
}