/*
 * Created on 2006-6-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.workinggroupsetting.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import com.iss.itreasury.dataconvert.util.DataTranserUtil;
import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.workingformsetting.dataentity.TemplateSheetSetting;
import com.iss.itreasury.dataconvert.workinggroupsetting.dataentity.WorkingGroupSetting;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WorkingGroupSettingDao extends DataTransplantBaseDao {
    
    public WorkingGroupSettingDao(){
        
    }
    
    public WorkingGroupSettingDao(String tableName){
        setStrTableName(tableName);
    }
    
    
    /**
     * 找到属于制定工作组的工作表
     * 
     * @param groupSettingId
     * @return
     */
    public Collection findWorkingFormSettingsBelongsToAWorkingGroupSetting(
            long groupSettingId) {
        String strSql = "select * from sett_TemplateSheetSetting where workingGroupId="
                + groupSettingId + " \n";
        //这样做是为了避免返回null,便于统一处理
        Collection result = new LinkedList();
        try {
            this.initDAO();
            this.prepareStatement(strSql);
            this.executeQuery();
            result = new DataTranserUtil().getDataEntitiesFromResultSet(
                    TemplateSheetSetting.class, this.resultSet);
        } finally {
            this.finalizeDAO();
        }
        return result;
    }
    
    /**
     * 添加新的工作组
     * code+1
     * @param entity
     */
    public long addGroup(WorkingGroupSetting entity){
        String newCode=((BigDecimal)fieldGenerator.generateValue("sett_TemplateGroupSetting","code")).toString();
        entity.setCode(newCode);
        return add(entity,null,false);
    }

}