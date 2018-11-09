/*
 * Created on 2006-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.workingformsetting.dao;

import java.math.BigDecimal;

import com.iss.itreasury.dataconvert.util.DataTransplantBaseDao;
import com.iss.itreasury.dataconvert.workingformsetting.dataentity.TemplateSheetSetting;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WorkingFormSettingDao extends DataTransplantBaseDao{
    
    public WorkingFormSettingDao(){
        this.setStrTableName("sett_TemplateSheetSetting");
    }
    
    /**
     * 添加新工作表,code按取最大code加一的方式生成
     * @param entity
     */
    public void addFrom(TemplateSheetSetting entity){
        String newCode=((BigDecimal)fieldGenerator.generateValue("sett_TemplateSheetSetting","code")).toString();
        entity.setCode(newCode);
        add(entity,null,false);
    }
    
}
