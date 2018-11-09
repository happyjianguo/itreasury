/*
 * Created on 2006-4-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_AccountInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Sett_SubAccountInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_NotifyInfo;
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
public class ImportTRF_NotifyInfoBiz extends AbstractImportBiz {

    private Sett_AccountInfo parseSett_Account(TRF_NotifyInfo info) {
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

    private Sett_SubAccountInfo parseSett_SubAccount(TRF_NotifyInfo info) {
        Sett_SubAccountInfo result = new Sett_SubAccountInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setNStatusId(TRF_Constant.SubAccountStatus.getValue("未结清"));
        return result;
    }

    public void importData() {
        Collection c = this.readDataFromTRF("TRF_Notify", TRF_NotifyInfo.class);
        DataTransplantBaseDao baseDao = new DataTransplantBaseDao();
        Connection con = DataBaseUtil.getConnection();
        long accountId = -1;
        String sAccountNo = "";
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {

                TRF_NotifyInfo notifyInfo = (TRF_NotifyInfo) i.next();
                baseDao.setStrTableName("sett_Account");
                Sett_AccountInfo accountInfo = parseSett_Account(notifyInfo);
                if (!notifyInfo.getSAccountNo().equalsIgnoreCase(sAccountNo)) {
                    accountId = baseDao.add(accountInfo,null, false);
                }
                Sett_SubAccountInfo subAccountInfo = parseSett_SubAccount(notifyInfo);
                subAccountInfo.setNAccountId(accountId);
                baseDao.setStrTableName("sett_SubAccount");
                baseDao.add(subAccountInfo,null, false);
                sAccountNo = notifyInfo.getSAccountNo();
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