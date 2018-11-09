/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dao;

import java.math.BigDecimal;

import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.TRF_Exception;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_LoanContractFormInfoDao extends DataTransplantBaseDao{
    
    public long queryRateId(double rate){
        String strSql="select id from loan_InterestRate where mRate="+rate+" \n";
        this.initDAO();
        this.prepareStatement(strSql);
        this.executeQuery();
        long id=-1;
        try{
        if(this.resultSet.next()){
            id=this.resultSet.getLong(1);
        }
        }catch(Exception e){
            throw new TRF_Exception("error occurs when querying",e);
        }
        finally{
            this.finalizeDAO();
        }
        return id;
    }
    
    /**
     * 产生新的三位流水号
     * @return
     */
    public String generateNewCodeForRateType(String tableName){
        return DataFormat.formatInt(Long.parseLong(((BigDecimal)this.fieldGenerator.generateValue(tableName,"sInterestRateNo")).toString()),3);
    }

}
