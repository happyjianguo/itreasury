/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ContractFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_DiscountContractBillInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_DiscountCredenceInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanTypeSettingInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.UserInfoInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanDiscountContractFormInfo;
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
public class ImportTRF_LoanDiscountContractFormInfoBiz extends
        AbstractImportBiz {

    private Loan_ContractFormInfo parseContractFormInfo(
            TRF_LoanDiscountContractFormInfo info, DataTransplantBaseDao dao) {
        Loan_ContractFormInfo result = new Loan_ContractFormInfo();
        result.setSContractCode(info.getSContractCode());
        result.setMDiscountRate(info.getMInterestRate());
        result.setDtDiscountDate(info.getDtDiscountDate());
        result.setNStatusId(5);
        result.setNInputUserId(handleUserName(info.getContractManagerPerson(),
                dao));
        result.setNBorrowClientId(NameRef.getClientIdByClientCode(info
                .getBorrowClientCode()));
        result.setMLoanAmount(info.getMLoanAmount());
        result.setMCheckAmount(info.getMCheckAmount());
        result.setNBankAcceptPo(info.getNBankAcceptPo());
        result.setNBizAcceptPo(info.getNBizAcceptPo());
        result.setNCurrencyId(1);
        result.setNOfficeId(1);
        result.setNTypeId(TRF_Constant.LoanType.TX);
        result.setNSubTypeId(handleLoanSubType(dao));
        result.setNInterestTypeId(1);
        result.setMExamineAmount(info.getMLoanAmount());
        //nBankInterestId
        //statusId 现在写死了,待以后更正
        result.setIsCanModify(1);
        return result;
    }

    private Loan_DiscountCredenceInfo parseDiscountCredenceInfo(
            TRF_LoanDiscountContractFormInfo info) {
        Loan_DiscountCredenceInfo result = new Loan_DiscountCredenceInfo();
        result.setSDraftCode(info.getSContractCode());
        result.setMAmount(info.getMLoanAmount());
        result.setMRate(info.getMInterestRate());
        result.setMInterest(info.getMInterest());
        result.setNOfficeId(1);
        result.setNCurrencyId(1);
        result.setNStatusId(1);
        return result;
    }

    private Loan_DiscountContractBillInfo parseDiscountContractBillInfo(
            TRF_LoanDiscountContractFormInfo info) {
        Loan_DiscountContractBillInfo result = new Loan_DiscountContractBillInfo();
        result.setNSerialNo(Long.parseLong(info.getBill_Serialno()));
        result.setNAcceptPoTypeId(TRF_Constant.DraftType.getValue(info
                .getBill_AcceptPoType()));
        result.setSUserName(info.getBill_SUserName());
        result.setDtCreate(info.getBill_DtCreate());
        result.setDtEnd(info.getBill_DtEnd());
        result.setSCode(info.getBill_SCode());
        result.setSBank(info.getBill_SBank());
        result.setMAmount(info.getBill_MAmount());
        result.setMCheckAmount(info.getBill_MRealAmount());
        result.setNOfficeId(1);
        result.setNCurrencyId(1);
        result.setNStatusId(1);
        return result;
    }

    private long handleUserName(String name, DataTransplantBaseDao dao) {
        long result = -1;
        result = NameRef.getUserIdByUserName(name);
        if (result < 0) {
            UserInfoInfo userInfo = new UserInfoInfo();
            //这里币种和单位先写死，以后修改
            userInfo.setNCurrencyId(1);
            userInfo.setNOfficeId(1);
            userInfo.setSName(name);
            dao.setStrTableName("userInfo");
            result = dao.add(userInfo,null,false);
        }
        return result;
    }

    private long handleLoanSubType(DataTransplantBaseDao dao) {
        long result = -1;
        result = NameRef.getSubTypeIdBySubLoanType(TRF_Constant.LoanType.TX,
                "贴现");
        if (result != -1) {
            return result;
        }
        Loan_LoanTypeSettingInfo settingInfo = new Loan_LoanTypeSettingInfo();
        settingInfo.setLoanTypeId(TRF_Constant.LoanType.TX);
        settingInfo.setName("贴现");
        settingInfo.setStatusId(1);
        dao.setStrTableName("loan_LoanTypeSetting");
        result = dao.add(settingInfo,null,false);
        return result;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_LoanDiscountContractForm",
                TRF_LoanDiscountContractFormInfo.class);
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
                TRF_LoanDiscountContractFormInfo trfInfo = (TRF_LoanDiscountContractFormInfo) i
                        .next();
                Loan_ContractFormInfo contractFormInfo = parseContractFormInfo(
                        trfInfo, baseDao);
                baseDao.setStrTableName("loan_ContractForm");
                long contractId = baseDao.add(contractFormInfo, null,false);
                Loan_DiscountCredenceInfo credenceInfo = parseDiscountCredenceInfo(trfInfo);
                credenceInfo.setNContractId(contractId);
                baseDao.setStrTableName("Loan_DiscountCredence");
                long credenceId = baseDao.add(credenceInfo,null, false);
                Loan_DiscountContractBillInfo billInfo = parseDiscountContractBillInfo(trfInfo);
                billInfo.setNContractId(contractId);
                billInfo.setNDiscountCredenceId(credenceId);
                baseDao.setStrTableName("Loan_DiscountContractBill");
                baseDao.add(billInfo,null, false);
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