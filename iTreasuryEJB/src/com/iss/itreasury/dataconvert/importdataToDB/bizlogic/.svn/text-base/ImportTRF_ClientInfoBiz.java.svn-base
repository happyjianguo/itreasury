/*
 * Created on 2006-3-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dataconvert.fieldgenerator.MaxFieldGenerator;
import com.iss.itreasury.dataconvert.importdataToDB.dao.ImportTRF_ClientInfoDao;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Client_ClientInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Client_CorporationInfo;
import com.iss.itreasury.dataconvert.importdataToDB.dataentity.Client_ExtendAttribute;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_ClientInfo;
import com.iss.itreasury.dataconvert.importdataToTRF.dataentity.TRF_CustomFieldSetting;
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
public class ImportTRF_ClientInfoBiz extends AbstractImportBiz {
    private Client_ClientInfo parseTRFInfoIntoClient_ClientInfo(
            TRF_ClientInfo info) {
        Client_ClientInfo result = new Client_ClientInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setOfficeId(NameRef
                .getOfficeIdByOfficeCode(info.getOfficeCode()));
        //客户基本类型 法人
        result.setClientBaseType("1");
        result.setCustomerManagerUserId(NameRef.getUserIdByUserName(info
                .getCustomerManagerUser()));
        result.setServiceLevel(TRF_Constant.ServiceLevel.getValue(info
                .getServiceLevel()));
        result.setInputUserId(NameRef.getUserIdByUserName(info.getInputUser()));
        result.setModifyUserId(NameRef
                .getUserIdByUserName(info.getModifyUser()));
        result.setStatusId(1);
        return result;
    }

    private Client_CorporationInfo parseTRFInfoIntoClient_CorporationInfo(
            TRF_ClientInfo info, Connection con) {
        Client_CorporationInfo result = new Client_CorporationInfo();
        TinyBeanUtil.copyFieldsValues(info, result);
        result.setIsPartner(TRF_Constant.YesOrNo.getValue(info.getIsPartner()));
        result.setIsMarkCompany(TRF_Constant.YesOrNo.getValue(info
                .getIsMarkCompany()));
        result.setBudgetParent(info.getBudgetParentCorp());
        //这里出现的数字对应我们的数据库数据定义1--6客户属性,11--18扩展属性
        if (!"".equals(info.getClientType1())) {
            result.setClientTypeId1(handleClientProperty(info.getClientType1(),
                    1, info, con));
        }
        if (!"".equals(info.getClientType2())) {
            result.setClientTypeId2(handleClientProperty(info.getClientType2(),
                    2, info, con));
        }
        if (!"".equals(info.getClientType3())) {
            result.setClientTypeId3(handleClientProperty(info.getClientType3(),
                    3, info, con));
        }
        if (!"".equals(info.getClientType4())) {
            result.setClientTypeId4(handleClientProperty(info.getClientType4(),
                    4, info, con));
        }
        if (!"".equals(info.getClientType5())) {
            result.setClientTypeId5(handleClientProperty(info.getClientType5(),
                    5, info, con));
        }
        if (!"".equals(info.getClientType6())) {
            result.setClientTypeId6(handleClientProperty(info.getClientType6(),
                    6, info, con));
        }
        if (!"".equals(info.getExtendAttribute1())) {
            result.setExtendAttribute1(handleClientProperty(info
                    .getExtendAttribute1(), 11, info, con));
        }
        if (!"".equals(info.getExtendAttribute2())) {
            result.setExtendAttribute2(handleClientProperty(info
                    .getExtendAttribute2(), 12, info, con));
        }
        if (!"".equals(info.getExtendAttribute3())) {
            result.setExtendAttribute3(handleClientProperty(info
                    .getExtendAttribute3(), 13, info, con));
        }
        if (!"".equals(info.getExtendAttribute4())) {
            result.setExtendAttribute4(handleClientProperty(info
                    .getExtendAttribute4(), 14, info, con));
        }
        if (!"".equals(info.getExtendAttribute5())) {
            result.setExtendAttribute5(handleClientProperty(info
                    .getExtendAttribute5(), 15, info, con));
        }
        if (!"".equals(info.getExtendAttribute6())) {
            result.setExtendAttribute6(handleClientProperty(info
                    .getExtendAttribute6(), 16, info, con));
        }
        if (!"".equals(info.getExtendAttribute7())) {
            result.setExtendAttribute7(handleClientProperty(info
                    .getExtendAttribute7(), 17, info, con));
        }
        if (!"".equals(info.getExtendAttribute8())) {
            result.setExtendAttribute8(handleClientProperty(info
                    .getExtendAttribute8(), 18, info, con));
        }
        return result;
    }

    //这里传入重复的参数是为了避免麻烦的反射调用
    private long handleClientProperty(String clientProperty, long propertyNo,
            TRF_ClientInfo clientInfo, Connection con) {
        ImportTRF_ClientInfoDao dao = new ImportTRF_ClientInfoDao();
        dao.setConnection(con);
        dao.setStrTableName("Client_ExtendAttribute");
        //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
        MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
        fieldGenerator.setConnection(con);
        dao.setFieldGenerator(fieldGenerator);
        Client_ExtendAttribute conditionInfo = new Client_ExtendAttribute();
        if ("".equals(clientProperty) || propertyNo == -1) {
            return -1;
        }
        conditionInfo.setName(clientProperty);
        conditionInfo.setAttributeId(propertyNo);
        conditionInfo.setStatusId(1);
        Collection c = dao.findByConditionOrderById(conditionInfo,null);
        //如果找到对应的属性id就返回
        //找不到就插入一条新的客户属性记录并返回id
        if (c.size() > 0) {
            return ((Client_ExtendAttribute) c.iterator().next()).getId();
        } else {
            Client_ExtendAttribute info = new Client_ExtendAttribute();
            info.setAttributeId(propertyNo);
            info.setCode(dao.generateNewCode());
            info.setName(clientProperty);
            info.setInputUserId(NameRef.getUserIdByUserName(clientInfo
                    .getInputUser()));
            info.setInputDate(clientInfo.getInputDate());
            //正确的逻辑先被屏蔽,这里先写死成1
            //info.setStatusId(TRF_Constant.RecordStatus.getValue(clientInfo.getStatus()));
            info.setStatusId(1);
            return dao.add(info,null, false);
        }
    }

    private void importCustomFieldSetting(Collection c) {
        DataTransplantBaseDao dao = new DataTransplantBaseDao();
        dao.setStrTableName("Client_CustomFieldSetting");
        for (Iterator i = c.iterator(); i.hasNext();) {
            try {
                TRF_CustomFieldSetting info = (TRF_CustomFieldSetting) i.next();
                dao.add(info,null, false);
            } catch (Exception e) {
                e.printStackTrace();
                //记录异常
            }
        }
    }

    private void importClient(Collection c) {
        ImportTRF_ClientInfoDao baseDao = new ImportTRF_ClientInfoDao();
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            //由于FieldGenerator必须运行在一个事物中,需要设置一个支持事务的FieldGenerator
            MaxFieldGenerator fieldGenerator = new MaxFieldGenerator();
            fieldGenerator.setConnection(con);
            baseDao.setConnection(con);
            baseDao.setFieldGenerator(fieldGenerator);
            for (Iterator i = c.iterator(); i.hasNext();) {
                TRF_ClientInfo info = (TRF_ClientInfo) i.next();
                baseDao.setStrTableName("client_ClientInfo");
                Client_ClientInfo clientInfo = parseTRFInfoIntoClient_ClientInfo(info);
                long clientId = baseDao.add(clientInfo,null, false);
                baseDao.setStrTableName("client_CorporationInfo");
                Client_CorporationInfo corporationInfo = parseTRFInfoIntoClient_CorporationInfo(
                        info, con);
                corporationInfo.setClientId(clientId);
                baseDao.add(corporationInfo,null, false);
            }
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            e.printStackTrace();
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }

    //这个过程必须在importClient之后执行
    private void updateParentCorpIdsForClient(Collection c) {
        ImportTRF_ClientInfoDao dao = new ImportTRF_ClientInfoDao();
        for (Iterator i = c.iterator(); i.hasNext();) {
            TRF_ClientInfo info = (TRF_ClientInfo) i.next();
            dao.updateParentCorpIdForOneRecord(info);
        }
    }

    private long searchForLevelId(long clientId) {
        long parentCorpId = NameRef.getParentCorpIdByClientTypeId(clientId);
        if (parentCorpId == -1) {
            return 1;
        } else {
            return 1 + searchForLevelId(parentCorpId);
        }
    }

    private String searchForLevelCode(long clientId, ImportTRF_ClientInfoDao dao) {
        long parentCorpId = NameRef.getParentCorpIdByClientTypeId(clientId);
        if (parentCorpId == -1) {
            return dao.generateNewLevelOneCode();
        }
        String parentCorpCode = dao.queryParentCorpLevelCode(clientId);
        if (parentCorpCode == null || "".equals(parentCorpCode)) {
            return searchForLevelCode(parentCorpId, dao) + "00001";
        }
        return parentCorpCode
                + dao.generateNewLevelCodeOfSpecifiedParentCorpId(parentCorpId);
    }

    //注意方法顺序关系
    private void updateLevelIdsForClient(Collection c) {
        ImportTRF_ClientInfoDao dao = new ImportTRF_ClientInfoDao();
        dao.setStrTableName("Client_ClientInfo");
        for (Iterator i = c.iterator(); i.hasNext();) {
            Client_ClientInfo infoFrom = (Client_ClientInfo) i.next();
            Client_ClientInfo infoTo = new Client_ClientInfo();
            infoTo.setId(infoFrom.getId());
            infoTo.setLevelId(searchForLevelId(infoFrom.getId()));
            dao.update(infoTo,null);
        }
    }

    //注意方法顺序关系
    private void updateLevelCodesForClient(Collection c) {
        ImportTRF_ClientInfoDao dao = new ImportTRF_ClientInfoDao();
        dao.setStrTableName("Client_ClientInfo");
        for (Iterator i = c.iterator(); i.hasNext();) {
            Client_ClientInfo infoFrom = (Client_ClientInfo) i.next();
            Client_ClientInfo infoTo = new Client_ClientInfo();
            infoTo.setId(infoFrom.getId());
            infoTo.setLevelCode(searchForLevelCode(infoFrom.getId(), dao));
            dao.update(infoTo,null);
        }
    }

    /**
     * 导入客户表数据,并把过程中发生的异常记录下来
     */
    public void importData() {
        Collection trfSettingInfos = this.readDataFromTRF(
                "TRF_CustomFieldSetting", TRF_CustomFieldSetting.class);
        importCustomFieldSetting(trfSettingInfos);
        Collection trfClientInfos = this.readDataFromTRF("TRF_Client",
                TRF_ClientInfo.class);
        importClient(trfClientInfos);
        updateParentCorpIdsForClient(trfClientInfos);
        Collection clientInfos = this.readDataFromTRF("Client_ClientInfo",
                Client_ClientInfo.class);
        updateLevelIdsForClient(clientInfos);
        updateLevelCodesForClient(clientInfos);
    }
}