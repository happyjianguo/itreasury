/*
 * Created on 2006-6-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.workingformsetting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.dataconvert.workingformsetting.dao.WorkingFormSettingDao;
import com.iss.itreasury.dataconvert.workingformsetting.dataentity.TemplateSheetSetting;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WorkingFormSettingBiz {
    
    private WorkingFormSettingDao workingFormSettingDao=new WorkingFormSettingDao();
    
    public void addForm(TemplateSheetSetting entity){
        workingFormSettingDao.addFrom(entity);
    }
    
    public void deleteForm(long id){
        workingFormSettingDao.deletePhysically(id);
    }
    
    public void updateForm(TemplateSheetSetting entity){
        TemplateSheetSetting baseLine=new TemplateSheetSetting();
        //比较基准实体把name设为不可达到的值null,说明要更新name字段
        baseLine.setName(null);
        //同上
        baseLine.setAliasNameInConfigFile(null);
        workingFormSettingDao.update(entity,baseLine);
    }
    
    public Collection findForm(TemplateSheetSetting queryObject){
        return workingFormSettingDao.findByConditionOrderById(queryObject,null);
    }

}
