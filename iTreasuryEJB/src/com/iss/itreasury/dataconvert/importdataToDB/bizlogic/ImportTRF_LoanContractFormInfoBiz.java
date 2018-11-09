/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_LoanContractFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dao.ImportTRF_LoanContractFormInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_ContractFormInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_InterestRateInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_InterestRateTypeInfoInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanContractAssureInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_LoanTypeSettingInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Loan_RateAdjustContractDetailInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.UserInfoInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Constant;
import com.iss.itreasury.dataconvert.util.TinyBeanUtil;
import com.iss.itreasury.util.Env;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_LoanContractFormInfoBiz extends AbstractImportBiz {

    private Loan_ContractFormInfo parseContractFormInfo(
            TRF_LoanContractFormInfo info, DataTransplantBaseDao dao) {
        Loan_ContractFormInfo result = new Loan_ContractFormInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNCurrencyId(TRF_Constant.CurrencyType.getValue(info
                .getCurrency()));
        result.setNOfficeId(1);
        long loanTypeId = TRF_Constant.LoanType.getValue(info.getLoanType());
        if (loanTypeId == TRF_Constant.LoanType.WT) {
            result.setMInterestRate(info.getLoanContractInerestRate());
        }
        result.setNTypeId(loanTypeId);
        result.setNSubTypeId(handleLoanSubType(info, dao));
        result.setNConsignClientId(NameRef.getClientIdByClientCode(info
                .getConsignClientCode()));
        result.setNBorrowClientId(NameRef.getClientIdByClientCode(info
                .getBorrowClientCode()));
        result.setNIsCredit(info.getCreditLoanAmount() > 0 ? 1 : -1);
        result.setNIsAssure(info.getAssureAmount() > 0 ? 1 : -1);
        result.setNIsImpawn(info.getImpawAmount() > 0 ? 1 : -1);
        result.setNIsPledge(info.getPledgeAmount() > 0 ? 1 : -1);
        result.setNInterestTypeId(1);
        result.setMExamineAmount(info.getMLoanAmount());
        //nBankInterestId
        result.setNInputUserId(handleContractManagerPerson(info, dao));
        //statusId 现在写死了,待以后更正
        result.setNStatusId(5);
        result.setIsCanModify(1);
        result.setNChargeRateTypeId(TRF_Constant.ChargeRatePayType
                .getValue(info.getChargeAssureType()));
        result.setNRiskLevel(TRF_Constant.VentureLevel.getValue(info
                .getRiskLevel()));
        return result;
    }

    private long handleContractManagerPerson(TRF_LoanContractFormInfo trfInfo,
            DataTransplantBaseDao dao) {
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

    private long handleLoanSubType(TRF_LoanContractFormInfo trfInfo,
            DataTransplantBaseDao dao) {
        long result = -1;
        result = NameRef.getSubTypeIdBySubLoanType(TRF_Constant.LoanType
                .getValue(trfInfo.getLoanType()), trfInfo.getSubLoanType());
        if (result != -1) {
            return result;
        }
        Loan_LoanTypeSettingInfo settingInfo = new Loan_LoanTypeSettingInfo();
        settingInfo.setLoanTypeId(TRF_Constant.LoanType.getValue(trfInfo
                .getLoanType()));
        settingInfo.setName(trfInfo.getSubLoanType());
        settingInfo.setStatusId(1);
        dao.setStrTableName("loan_LoanTypeSetting");
        result = dao.add(settingInfo,null, false);
        return result;
    }

    private Loan_InterestRateInfo parseInterestRateInfo(
            TRF_LoanContractFormInfo info, double rate) {
        Loan_InterestRateInfo result = new Loan_InterestRateInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setMRate(rate);
        result.setDtValiDate(DataFormat.getDateTime("2005-12-31"));
        result.setNCurrencyId(1);
        result.setNOfficeId(1);
        result.setDtInput(Env.getSystemDateTime());
        return result;
    }

    private Loan_InterestRateTypeInfoInfo parseInterestRateTypeInfo(
            TRF_LoanContractFormInfo info) {
        Loan_InterestRateTypeInfoInfo result = new Loan_InterestRateTypeInfoInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        ImportTRF_LoanContractFormInfoDao dao = new ImportTRF_LoanContractFormInfoDao();
        result.setSInterestRateNo(dao
                .generateNewCodeForRateType("Loan_InterestRateTypeInfo"));
        StringBuffer sInterestRateName = new StringBuffer();
        sInterestRateName.append("数据转移-");
        sInterestRateName.append(info.getCurrency() + "利率-");
        result.setSInterestRateName(sInterestRateName.toString());
        result.setNCurrencyId(TRF_Constant.CurrencyType.getValue(info
                .getCurrency()));
        result.setNOfficeId(1);
        return result;
    }

    private Loan_RateAdjustContractDetailInfo parseAdjustContractDetialInfo(
            TRF_LoanContractFormInfo info) {
        Loan_RateAdjustContractDetailInfo result = new Loan_RateAdjustContractDetailInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNAdjustSerial(1);
        result.setDtStartDate(DataFormat.getDateTime("2005-12-31"));
        result.setDtEndDate(null);
        return result;
    }

    private Loan_LoanContractAssureInfo createSharedAssureInfo(
            TRF_LoanContractFormInfo paraTrfInfo, long contractFormId) {
        Loan_LoanContractAssureInfo result = new Loan_LoanContractAssureInfo();
        result.setNContractId(contractFormId);
        result.setNAssureTypeId(2);
        result.setMAmount(paraTrfInfo.getAssureAmount());
        result.setNStatusId(1);
        return result;
    }

    private void importAssureInfo(TRF_LoanContractFormInfo paraTrfInfo,
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
                assureDao.add(info,null, false);
            }
            if (!"".equalsIgnoreCase(paraTrfInfo.getAssureClientCode3())) {
                Loan_LoanContractAssureInfo info = createSharedAssureInfo(
                        paraTrfInfo, contractFormId);
                info.setNClientId(NameRef.getClientIdByClientCode(paraTrfInfo
                        .getAssureClientCode3()));
                assureDao.add(info,null, false);
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
                info.setSImpawName(paraTrfInfo.getSImpawName());
                info.setSImpawQuality(paraTrfInfo.getSImpawQuality());
                info.setSImpawStatus(paraTrfInfo.getSImpawStatus());
                assureDao.add(info,null, false);
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
                info.setMPledgeAmount(paraTrfInfo.getMPledgeAmount());
                info.setMPledgeRate(paraTrfInfo.getMPledgeRate());
                assureDao.add(info,null, false);
            }
        }
    }

    private long importInterestRate(double rate,
            TRF_LoanContractFormInfo trfInfo, DataTransplantBaseDao dao) {
        long bankInerestId = -1;
        Loan_InterestRateTypeInfoInfo rateTypeInfo = parseInterestRateTypeInfo(trfInfo);
        dao.setStrTableName("loan_InterestRateTypeInfo");
        long bankInterestTypeId = dao.add(rateTypeInfo,null, false);
        Loan_InterestRateInfo rateInfo = parseInterestRateInfo(trfInfo, rate);
        rateInfo.setNBankInterestTypeId(bankInterestTypeId);
        dao.setStrTableName("loan_InterestRate");
        bankInerestId = dao.add(rateInfo, null,false);
        return bankInerestId;
    }

    private long importContractForm(long bankInterestId,
            TRF_LoanContractFormInfo trfInfo, DataTransplantBaseDao dao) {
        long contractId = -1;
        Loan_ContractFormInfo contractFormInfo = parseContractFormInfo(trfInfo,
                dao);
        contractFormInfo.setNBankInterestId(bankInterestId);
        dao.setStrTableName("loan_ContractForm");
        contractId = dao.add(contractFormInfo,null, false);
        return contractId;
    }

    private void importRateAdjustContract(long contractId, long bankInterestId,
            TRF_LoanContractFormInfo trfInfo, DataTransplantBaseDao dao) {
        Loan_RateAdjustContractDetailInfo info = parseAdjustContractDetialInfo(trfInfo);
        info.setNContractId(contractId);
        info.setNBankInterestId(bankInterestId);
        info.setMRate(trfInfo.getLoanExecuteInterestRate());
        dao.setStrTableName("Loan_RateAdjustContractDetail");
        dao.add(info,null, false);
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_LoanContractForm",
                TRF_LoanContractFormInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        ImportTRF_LoanContractFormInfoDao importTRF_LoanContractFormInfoDao = new ImportTRF_LoanContractFormInfoDao();
        Connection con = DataBaseUtil.getConnection();
        try {
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            con.setAutoCommit(false);
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_LoanContractFormInfo trfInfo = (TRF_LoanContractFormInfo) i
                        .next();
                //先插入合同有关的信息
                long bankInterestId = importTRF_LoanContractFormInfoDao
                        .queryRateId(trfInfo.getLoanExecuteInterestRate());
                long contractId = -1;
                if (bankInterestId > 0) {
                    contractId = importContractForm(bankInterestId, trfInfo,
                            baseDao);
                } else {
                    long newBankInterestId = importInterestRate(trfInfo
                            .getLoanExecuteInterestRate(), trfInfo, baseDao);
                    contractId = importContractForm(newBankInterestId, trfInfo,
                            baseDao);
                }
                //如果有调整利率，就处理调整利率有关的信息
                if (trfInfo.getLoanExecuteInterestRate() != trfInfo
                        .getLoanContractInerestRate()) {
                    bankInterestId = importTRF_LoanContractFormInfoDao
                            .queryRateId(trfInfo.getLoanContractInerestRate());
                    if (bankInterestId < 0) {
                        bankInterestId = importInterestRate(trfInfo
                                .getLoanContractInerestRate(), trfInfo, baseDao);
                    }
                    importRateAdjustContract(contractId, bankInterestId,
                            trfInfo, baseDao);
                }
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