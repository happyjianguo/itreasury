/*
 * Created on 2006-4-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanContractPlanDetailInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanContractPlanInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanContractPlanInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Constant;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_LoanContractPlanBiz extends AbstractImportBiz {

    private Loan_LoanContractPlanInfo parseLoan_ContractPlanInfo(
            TRF_LoanContractPlanInfo info) {
        Loan_LoanContractPlanInfo result = new Loan_LoanContractPlanInfo();
        result.setNContractId(NameRef.getContractFormIdByContractCode(info
                .getSContractCode()));
        result.setNPlanVersion(1);
        result.setNStatusId(1);
        return result;
    }

    private Loan_LoanContractPlanDetailInfo parseLoan_ContractPlanDetailInfo(
            TRF_LoanContractPlanInfo info) {
        Loan_LoanContractPlanDetailInfo result = new Loan_LoanContractPlanDetailInfo();
        result.setDtPlanDate(info.getDtPlanDate());
        result.setNPayTypeId(TRF_Constant.PayOrReturn.getValue(info
                .getPayType()));
        result.setMAmount(info.getMAmount());
        result.setSType("本金");
        return result;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_LoanContractPlan",
                TRF_LoanContractPlanInfo.class);
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

                TRF_LoanContractPlanInfo trfInfo = (TRF_LoanContractPlanInfo) i
                        .next();
                baseDao.setStrTableName("Loan_LoanContractPlan");
                Loan_LoanContractPlanInfo planInfo = parseLoan_ContractPlanInfo(trfInfo);
                long planId = baseDao.add(planInfo, null,false);
                Loan_LoanContractPlanDetailInfo planDetailInfo = parseLoan_ContractPlanDetailInfo(trfInfo);
                planDetailInfo.setNContractPlanId(planId);
                baseDao.setStrTableName("Loan_LoanContractPlanDetail");
                baseDao.add(planDetailInfo,null, false);
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