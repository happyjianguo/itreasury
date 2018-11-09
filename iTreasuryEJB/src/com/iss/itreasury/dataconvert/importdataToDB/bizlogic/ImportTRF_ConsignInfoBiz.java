/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dao.Loan_PayFormInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_PayFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_AccountInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ConsignLoanInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TinyBeanUtil;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_ConsignInfoBiz extends AbstractImportBiz {
    //延迟天数
    private long delayDays = 1;

    public void setDelayDays(long delayDays) {
        this.delayDays = delayDays;
    }

    private double calcInterest(TRF_ConsignLoanInfo consignInfo,
            Loan_PayFormInfo payInfo) {
        return (consignInfo.getStandBalance1() - delayDays
                * consignInfo.getMBalance())
                * payInfo.getMInterestRate() / 100 / 360;
    }

    private Sett_AccountInfo parseSett_Account(TRF_ConsignLoanInfo info) {
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

    private Sett_SubAccountInfo parseSett_SubAccount(TRF_ConsignLoanInfo info,
            Loan_PayFormInfoDao dao) {
        Sett_SubAccountInfo result = new Sett_SubAccountInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNStatusId(TRF_Constant.SubAccountStatus.getValue("未结清"));
        Loan_PayFormInfo payFormInfo = dao.queryPayFormId(
                info.getPayFormCode(), info.getContractCode());
        //如果查到的结果为空，就用默认值
        if (payFormInfo == null) {
            payFormInfo = new Loan_PayFormInfo();
        }
        result.setAl_MInterestTaxRate(result.getAl_MInterestTaxRate() / 100);
        result.setAl_NLoanNoteId(payFormInfo.getId());
        result.setAl_NIsCycLoan(TRF_Constant.YesOrNo.getValue(info
                .getIsCircleLoan()));
        result.setAl_NPayInterestAccountId(NameRef
                .getAccountIdByAccountCode(info.getPayInterestAccountCode()));
        result
                .setAl_NReceiveInterestAccountId(NameRef
                        .getAccountIdByAccountCode(info
                                .getReceiveInterestAccountCode()));
        result.setAl_NConsignAccountId(NameRef.getAccountIdByAccountCode(info
                .getConsignAccountCode()));
        result.setAl_NCommissionAccountId(NameRef
                .getAccountIdByAccountCode(info.getCommissionAccountCode()));
        result.setMOpenAmount(payFormInfo.getMAmount());
        result.setMInterest(calcInterest(info, payFormInfo));
        return result;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_ConsignLoan",
                TRF_ConsignLoanInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        Loan_PayFormInfoDao payFormDao = new Loan_PayFormInfoDao();
        long accountId = -1;
        String sAccountNo = "";
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_ConsignLoanInfo trustInfo = (TRF_ConsignLoanInfo) i.next();
                baseDao.setStrTableName("sett_Account");
                Sett_AccountInfo accountInfo = parseSett_Account(trustInfo);
                if (!trustInfo.getSAccountNo().equalsIgnoreCase(sAccountNo)) {
                    accountId = baseDao.add(accountInfo,null, false);
                }
                Sett_SubAccountInfo subAccountInfo = parseSett_SubAccount(
                        trustInfo, payFormDao);
                subAccountInfo.setNAccountId(accountId);
                baseDao.setStrTableName("sett_SubAccount");
                baseDao.add(subAccountInfo, null,false);
                sAccountNo = trustInfo.getSAccountNo();
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