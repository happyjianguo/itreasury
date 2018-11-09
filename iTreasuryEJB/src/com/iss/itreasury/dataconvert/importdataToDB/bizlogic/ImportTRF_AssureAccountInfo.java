/*
 * Created on 2006-4-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dao.Loan_AssureChargeFormInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_AssureChargeFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_AccountInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_AssureAccountInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TinyBeanUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_AssureAccountInfo extends AbstractImportBiz {

    //延迟天数
    private long delayDays = 1;

    public void setDelayDays(long delayDays) {
        this.delayDays = delayDays;
    }

    private Sett_AccountInfo parseSett_Account(TRF_AssureAccountInfo info) {
        Sett_AccountInfo result = new Sett_AccountInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNOfficeId(NameRef.getOfficeIdByOfficeCode(info
                .getOfficeCode()));
        result.setNCurrencyId(TRF_Constant.CurrencyType.getValue(info
                .getCurrency()));
        result.setNAccountTypeId(NameRef.getAccountTypeIdByAccountType(info
                .getAccountType()));
        result.setNClientId(NameRef.getClientIdByClientCode(info
                .getClientCode()));
        result.setSName(NameRef.getClientNameByClientId(result.getNClientId()));
        result
                .setNInputUserId(NameRef.getUserIdByUserName(info
                        .getInputUser()));
        result
                .setNCheckUserId(NameRef.getUserIdByUserName(info
                        .getCheckUser()));
        result.setNCheckStatusId(TRF_Constant.AccountCheckStatus.CHECK);
        result.setNStatusId(TRF_Constant.AccountStatus.getValue(info
                .getStatus()));
        return result;
    }

    private Sett_SubAccountInfo parseSett_SubAccount(
            TRF_AssureAccountInfo info, Loan_AssureChargeFormInfoDao dao) {
        Sett_SubAccountInfo result = new Sett_SubAccountInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNStatusId(TRF_Constant.SubAccountStatus.getValue("未结清"));
        Loan_AssureChargeFormInfo payFormInfo = dao.queryPayFormId(info
                .getPayFormCode(), info.getContractCode());
        //如果查到的结果为空，就用默认值
        if (payFormInfo == null) {
            payFormInfo = new Loan_AssureChargeFormInfo();
        }
        result.setAf_SDepositNo(info.getPayFormCode());
        result.setAf_NDepositTerm(payFormInfo.getIntervalNum());
        result.setAf_DtStart(payFormInfo.getStartDate());
        result.setAf_DtEnd(payFormInfo.getEndDate());
        result.setAf_DtPredraw(DataFormat.getDateTime("2005-12-21"));
        result.setAl_NLoanNoteId(payFormInfo.getId());
        result.setMOpenAmount(payFormInfo.getRecognizanceAmount());
        result.setMInterest(calcInterest(info));
        return result;
    }

    private double calcInterest(TRF_AssureAccountInfo info) {
        return (info.getStandBalance1() - info.getMBalance()) * info.getMRate()
                / 36000;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_AssureAccount",
                TRF_AssureAccountInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        Loan_AssureChargeFormInfoDao payFormDao = new Loan_AssureChargeFormInfoDao();
        long accountId = -1;
        String sAccountNo = "";
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator=new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_AssureAccountInfo assureInfo = (TRF_AssureAccountInfo) i
                        .next();
                Sett_AccountInfo accountInfo = parseSett_Account(assureInfo);
                baseDao.setStrTableName("sett_Account");
                if (!assureInfo.getSAccountNo().equalsIgnoreCase(sAccountNo)) {
                    accountId = baseDao.add(accountInfo, null,false);
                }
                Sett_SubAccountInfo subAccountInfo = parseSett_SubAccount(
                        assureInfo, payFormDao);
                subAccountInfo.setNAccountId(accountId);
                baseDao.setStrTableName("sett_SubAccount");
                baseDao.add(subAccountInfo,null, false);
                sAccountNo = assureInfo.getSAccountNo();
            }
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DataBaseUtil.rollBackConnection(con);
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }
}