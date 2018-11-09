/*
 * Created on 2006-3-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dao.ImportTRF_CurrencyInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_AccountInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_CurrencyInfo;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.DataFormat;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.util.NameRef;
import com.iss.itreasury.dataconvert.util.TRF_Constant;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ImportTRF_CurrencyInfoBiz extends AbstractImportBiz {

    //�ӳ�����
    private long delayDays = 1;

    public void setDelayDays(long delayDays) {
        this.delayDays = delayDays;
    }

    private double calcInterest(TRF_CurrencyInfo info) {
        //Э���˻����>0��ʱ��Ϣ��ʽ��=����������+0��*0.72/100/360 + ��Э������+���-0��*1.44/100/360
        //Э���˻����>0��ʱ��Ϣ��ʽ���ܻ���-Э������-3*0��*0.72/100/360 +
        // ��Э������-�����-0��*3��*1.44/100/360
        if (TRF_Constant.YesOrNo.getValue(info.getIsNegotiate()) == TRF_Constant.YesOrNo.YES) {
            return (info.getStandBalance1() - info.getStandBalance2() - delayDays
                    * info.getCapitalLimitAmount())
                    * 0.72
                    / 100
                    / 360
                    + (info.getStandBalance2() - (info.getAccountBalance() - info
                            .getCapitalLimitAmount())
                            * delayDays) * 1.44 / 100 / 360;
        }
        //Э���˻����<0��ʱ��Ϣ��ʽ��=����������+��*0.72/100/360 + Э������*1.44/100/360
        //Э���˻����<=0��ʱ��Ϣ��ʽ��=���ܻ���-3*��*0.72/100/360 + ��Э��������*1.44/100/360
        else {
            return (info.getStandBalance1() - delayDays
                    * info.getAccountBalance())
                    * 0.72
                    / 100
                    / 360
                    + info.getStandBalance2()
                    * 1.44
                    / 100
                    / 360;
        }
    }

    private Sett_AccountInfo parseSett_Account(TRF_CurrencyInfo info) {
        Sett_AccountInfo result = new Sett_AccountInfo();
        result.setSAccountNo(info.getAccountNo());
        result.setNOfficeId(NameRef.getOfficeIdByOfficeCode(info
                .getOfficeCode()));
        result.setNCurrencyId(TRF_Constant.CurrencyType.getValue(info
                .getCurrency()));
        result.setNAccountTypeId(NameRef.getAccountTypeIdByAccountType(info
                .getAccountType()));
        result.setNClientId(NameRef.getClientIdByClientCode(info
                .getClientCode()));
        if (info.getName() != null && !info.getName().equalsIgnoreCase("")) {
            result.setSName(info.getName());
        } else {
            result.setSName(NameRef.getClientNameByClientId(result
                    .getNClientId()));
        }
        result.setDtOpen(info.getOpenDate());
        result
                .setNInputUserId(NameRef.getUserIdByUserName(info
                        .getInputUser()));
        result.setDtInput(info.getInputDate());
        result
                .setNCheckUserId(NameRef.getUserIdByUserName(info
                        .getCheckUser()));
        result.setDtCheck(info.getCheckDate());
        result.setNCheckStatusId(TRF_Constant.AccountCheckStatus.CHECK);
        result.setNStatusId(TRF_Constant.AccountStatus.getValue(info
                .getStatus()));
        result.setDtFinish(info.getFinishDate());
        result.setSAbstract(info.getAbstract());
        result.setMMaxSinglePayAmount(0.0);
        result.setMMinSinglePayAmount(0.0);
        result.setNBatchUpdateId(-1);
        result.setNBatchUpdateTypeId(-1);
        return result;
    }

    private Sett_SubAccountInfo parseSett_SubAccount(TRF_CurrencyInfo info) {
        Sett_SubAccountInfo result = new Sett_SubAccountInfo();
        result.setMInterest(calcInterest(info));
        result.setMBalance(info.getAccountBalance());
        result.setMOpenAmount(0);
        result.setDtOpen(info.getOpenDate());
        result.setDtFinish(info.getFinishDate());
        if (TRF_Constant.YesOrNo.getValue(info.getIsNegotiate()) == TRF_Constant.YesOrNo.YES) {
            result.setNIsInterest(1);
        } else {
            result.setNIsInterest(-1);
        }
        result.setDtClearInterest(info.getFinishDate());
        result.setNStatusId(TRF_Constant.SubAccountStatus.getValue("δ����"));
        result.setMUncheckPaymentAmount(0);
        result.setAc_MMonthLimitAmount(info.getAnyBusinessLimitAmount());
        result.setAc_NInterestAccountId(-1);
        result.setAc_NIsOverDraft(TRF_Constant.YesOrNo.getValue(info
                .getIsOverDraft()));
        result.setAc_NFirstLimitTypeId(TRF_Constant.AccountOverDraftType
                .getValue(info.getFirstLimitType()));
        result.setAc_MFirstLimitAmount(info.getFirstLimitAmount());
        result.setAc_NSecondLimitTypeId(TRF_Constant.AccountOverDraftType
                .getValue(info.getSecondLimitType()));
        result.setAc_MSecondLimitAmount(info.getSecondLimitAmount());
        result.setAc_NThirdLimitTypeId(TRF_Constant.AccountOverDraftType
                .getValue(info.getThirdLimitType()));
        result.setAc_MThirdLimitAmount(info.getThirdLimitAmount());
        if (info.getRatePlan() != null && !"".equals(info.getRatePlan())) {
            result.setAc_NInterestRatePlanId(new ImportTRF_CurrencyInfoDao()
                    .queryInterestRatePlanIdByInterestRate(100 * Double
                            .parseDouble(info.getRatePlan())));
        }
        result.setAc_DtInterestRatePlan(info.getOpenDate());
        result.setAc_NIsNegotiate(TRF_Constant.YesOrNo.getValue(info
                .getIsNegotiate()));
        //Э�����ĸ����߼�
        if (TRF_Constant.YesOrNo.getValue(info.getIsNegotiate()) == TRF_Constant.YesOrNo.YES) {
            result.setAc_MNegotiateAmount(info.getCapitalLimitAmount());
            result.setAc_MNegotiateRate(info.getNegotiateRate());
            result.setAc_MNegotiateUnit(info.getNegotiateUnit());
            result.setAc_DtNegotiationEndDate(DataFormat
                    .getDateTime("2006-12-21"));
        }
        result.setAc_MNegotiateAmount(info.getCapitalLimitAmount());
        result.setAc_MNegotiateUnit(info.getNegotiateUnit());
        result.setAc_MNegotiateRate(info.getNegotiateRate());
        result.setAc_DtNegotiateRate(null);
        result.setAc_MNegotiateInterest(0.0);
        return result;
    }

    /**
     * ��������
     */
    public void importData() {
        Collection c = this.readDataFromTRF("TRF_Currency",
                TRF_CurrencyInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //����FieldGenerator����������һ��������,��Ҫ����һ��֧�������FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_CurrencyInfo currencyInfo = (TRF_CurrencyInfo) i.next();
                baseDao.setStrTableName("sett_Account");
                Sett_AccountInfo accountInfo = parseSett_Account(currencyInfo);
                long accountId = baseDao.add(accountInfo, null,false);
                Sett_SubAccountInfo subAccountInfo = parseSett_SubAccount(currencyInfo);
                subAccountInfo.setNAccountId(accountId);
                baseDao.setStrTableName("sett_SubAccount");
                baseDao.add(subAccountInfo,null, false);
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