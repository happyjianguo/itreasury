/*
 * Created on 2006-4-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ExtendContractInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ExtendDetailInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ExtendFormInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ExtendDetailInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_ExtendDetailInfoBiz extends AbstractImportBiz {

    private Loan_ExtendFormInfo parseExtendFormInfo(TRF_ExtendDetailInfo info) {
        Loan_ExtendFormInfo result = new Loan_ExtendFormInfo();
        result.setNContractId(NameRef.getContractFormIdByContractCode(info
                .getLoanContractCode()));
        result.setMInterestRate(info.getExecuteInterestRate());
        result.setNStatusId(1);
        result.setNCurrencyId(1);
        result.setNOfficeId(1);
        result.setNPlanVersionId(1);
        return result;
    }

    private Loan_ExtendContractInfo parseExtendContractInfo(
            TRF_ExtendDetailInfo info) {
        Loan_ExtendContractInfo result = new Loan_ExtendContractInfo();
        result.setSCode(info.getExtendContractCode());
        result.setNStatusId(1);
        result.setNCurrencyId(1);
        result.setNOfficeId(1);
        return result;
    }

    private Loan_ExtendDetailInfo parseExtendDetailInfo(
            TRF_ExtendDetailInfo info) {
        Loan_ExtendDetailInfo result = new Loan_ExtendDetailInfo();
        result.setMExtendAmount(info.getAmount());
        result.setDtExtendBeginDate(info.getStartDate());
        result.setDtExtendEndDate(info.getEndDate());
        result.setNExtendIntervalnum(info.getExtendTerm());
        result.setNPlanId(1);
        return result;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_ExtendDetail",
                TRF_ExtendDetailInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_ExtendDetailInfo trfInfo = (TRF_ExtendDetailInfo) i.next();
                Loan_ExtendFormInfo extendFormInfo = parseExtendFormInfo(trfInfo);
                baseDao.setStrTableName("Loan_ExtendForm");
                long extendFormId = baseDao.add(extendFormInfo, null,false);
                Loan_ExtendContractInfo extendContractInfo = parseExtendContractInfo(trfInfo);
                baseDao.setStrTableName("Loan_ExtendContract");
                extendContractInfo.setNExtendId(extendFormId);
                baseDao.add(extendContractInfo,null, false);
                Loan_ExtendDetailInfo extendDetailInfo = parseExtendDetailInfo(trfInfo);
                extendDetailInfo.setNExtendFormId(extendFormId);
                baseDao.setStrTableName("Loan_ExtendDetail");
                baseDao.add(extendDetailInfo,null, false);
            }
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }
}