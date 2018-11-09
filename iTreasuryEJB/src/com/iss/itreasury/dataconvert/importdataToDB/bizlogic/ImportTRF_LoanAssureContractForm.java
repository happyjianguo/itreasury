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
import com.iss.itreasury.dataconvert.importdataToDB.dao.ImportTRF_LoanContractFormInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ContractFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanContractAssureInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanTypeSettingInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.UserInfoInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanAssureContractFormInfo;
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
public class ImportTRF_LoanAssureContractForm extends AbstractImportBiz {

    private Loan_ContractFormInfo parseContractFormInfo(
            TRF_LoanAssureContractFormInfo info, DataTransplantBaseDao dao) {
        Loan_ContractFormInfo result = new Loan_ContractFormInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNCurrencyId(TRF_Constant.CurrencyType.getValue(info
                .getCurrency()));
        result.setNOfficeId(1);
        result.setNTypeId(TRF_Constant.LoanType.DB);
        result.setNSubTypeId(handleLoanSubType(dao));
        result.setNBorrowClientId(NameRef.getClientIdByClientCode(info
                .getAssuredClientCode()));
        result.setNInputUserId(handleContractManagerPerson(info, dao));
        result.setNIsAssure(info.getAssureAmount() > 0 ? 1 : -1);
        result.setNIsImpawn(info.getImpawAmount() > 0 ? 1 : -1);
        result.setNIsPledge(info.getPledgeAmount() > 0 ? 1 : -1);
        result.setNInterestTypeId(1);
        result.setMExamineAmount(info.getMLoanAmount());
        //nBankInterestId
        //statusId 现在写死了,待以后更正
        result.setNStatusId(5);
        result.setIsCanModify(1);
        result.setNChargeRateTypeId(TRF_Constant.ChargeRatePayType
                .getValue(info.getChargeAssureType()));
        result.setNRiskLevel(TRF_Constant.VentureLevel.getValue(info
                .getRiskLevel()));
        result.setAssureTypeId1(TRF_Constant.AssureType1.getValue(info
                .getAssureType1()));
        result.setAssureTypeId2(TRF_Constant.AssureType2.getValue(info
                .getAssureType2()));
        return result;
    }

    private long handleContractManagerPerson(
            TRF_LoanAssureContractFormInfo trfInfo, DataTransplantBaseDao dao) {
        long result = -1;
        result = NameRef
                .getUserIdByUserName(trfInfo.getContractManagerPerson());
        if (result < 0) {
            UserInfoInfo userInfo = new UserInfoInfo();
            //这里币种和单位先写死，以后修改
            userInfo.setNCurrencyId(1);
            userInfo.setNOfficeId(1);
            userInfo.setSName(trfInfo.getContractManagerPerson());
            dao.setStrTableName("userInfo");
            result = dao.add(userInfo,null, false);
        }
        return result;
    }

    private long handleLoanSubType(DataTransplantBaseDao dao) {
        long result = -1;
        result = NameRef.getSubTypeIdBySubLoanType(TRF_Constant.LoanType.DB,
                "担保");
        if (result != -1) {
            return result;
        }
        Loan_LoanTypeSettingInfo settingInfo = new Loan_LoanTypeSettingInfo();
        settingInfo.setLoanTypeId(TRF_Constant.LoanType.DB);
        settingInfo.setName("担保");
        settingInfo.setStatusId(1);
        dao.setStrTableName("loan_LoanTypeSetting");
        result = dao.add(settingInfo,null, false);
        return result;
    }

    private Loan_LoanContractAssureInfo createSharedAssureInfo(
            TRF_LoanAssureContractFormInfo paraTrfInfo, long contractFormId) {
        Loan_LoanContractAssureInfo result = new Loan_LoanContractAssureInfo();
        result.setNContractId(contractFormId);
        result.setNAssureTypeId(2);
        result.setMAmount(paraTrfInfo.getAssureAmount());
        result.setNStatusId(1);
        return result;
    }

    private void importAssureInfo(TRF_LoanAssureContractFormInfo paraTrfInfo,
            long contractFormId, DataTransplantBaseDao assureDao) {
        assureDao.setStrTableName("loan_LoanContractAssure");
        if (paraTrfInfo.getAssureAmount() > 0) {
            if (!"".equalsIgnoreCase(paraTrfInfo.getAssureClientCode1())) {
                Loan_LoanContractAssureInfo info = createSharedAssureInfo(
                        paraTrfInfo, contractFormId);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getAssureClientCode1()));
                assureDao.add(info,null, false);
            }
            if (!"".equalsIgnoreCase(paraTrfInfo.getAssureClientCode2())) {
                Loan_LoanContractAssureInfo info = createSharedAssureInfo(
                        paraTrfInfo, contractFormId);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getAssureClientCode2()));
                assureDao.add(info, null,false);
            }
            if (!"".equalsIgnoreCase(paraTrfInfo.getAssureClientCode3())) {
                Loan_LoanContractAssureInfo info = createSharedAssureInfo(
                        paraTrfInfo, contractFormId);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getAssureClientCode3()));
                assureDao.add(info, null, false);
            }
        }
        if (paraTrfInfo.getImpawAmount() > 0) {
            if (!"".equalsIgnoreCase(paraTrfInfo.getImpawClientCode())) {
                Loan_LoanContractAssureInfo info = new Loan_LoanContractAssureInfo();
                info.setNContractId(contractFormId);
                info.setNAssureTypeId(4);
                info.setMAmount(paraTrfInfo.getImpawAmount());
                info.setNStatusId(1);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getImpawClientCode()));
                assureDao.add(info, null,false);
            }
        }
        if (paraTrfInfo.getPledgeAmount() > 0) {
            if (!"".equalsIgnoreCase(paraTrfInfo.getPledgeClientCode())) {
                Loan_LoanContractAssureInfo info = new Loan_LoanContractAssureInfo();
                info.setNContractId(contractFormId);
                info.setNAssureTypeId(3);
                info.setMAmount(paraTrfInfo.getPledgeAmount());
                info.setNStatusId(1);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getPledgeClientCode()));
                assureDao.add(info,null, false);
            }
        }
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_LoanAssureContractForm",
                TRF_LoanAssureContractFormInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        ImportTRF_LoanContractFormInfoDao importTRF_LoanContractFormInfoDao = new ImportTRF_LoanContractFormInfoDao();
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_LoanAssureContractFormInfo trfInfo = (TRF_LoanAssureContractFormInfo) i
                        .next();
                Loan_ContractFormInfo contractFormInfo = parseContractFormInfo(
                        trfInfo, baseDao);
                baseDao.setStrTableName("loan_ContractForm");
                long contractId = baseDao.add(contractFormInfo,null,false);
                importAssureInfo(trfInfo, contractId, baseDao);
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