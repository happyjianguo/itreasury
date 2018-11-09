/*
 * Created on 2006-6-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.workinggroupsetting.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.iss.itreasury.dataconvert.util.DataBaseUtil;
import com.iss.itreasury.dataconvert.util.TRF_Exception;
import com.iss.itreasury.dataconvert.workingformsetting.dataentity.TemplateSheetSetting;
import com.iss.itreasury.dataconvert.workinggroupsetting.dao.WorkingGroupSettingDao;
import com.iss.itreasury.dataconvert.workinggroupsetting.dataentity.WorkingGroupSetting;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WorkingGroupSettingBiz {

    /**
     * 增加工作组
     * 
     * @param groupSetting
     * @param workingFormIds
     */
    public void addWorkingGroup(WorkingGroupSetting groupSetting,
            String strIds) {
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            WorkingGroupSettingDao dao = new WorkingGroupSettingDao();
            dao.setStrTableName("sett_TemplateGroupSetting");
            dao.setConnection(con);
            long workingGroupSettingId=dao.addGroup(groupSetting);
            dao.setStrTableName("sett_TemplateSheetSetting");
            long[] workingFormIds=transferStringIdsToLongs(strIds);
            if(workingFormIds.length<1){
                throw new TRF_Exception("不能加入空用户组");
            }
            for (int i = 0; i < workingFormIds.length; i++) {
                TemplateSheetSetting each = (TemplateSheetSetting) dao
                        .findById(workingFormIds[i], TemplateSheetSetting.class);
                if (each != null) {
                    each.setWorkingGroupId(workingGroupSettingId);
                    dao.update(each, null);
                }
            }
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            throw new TRF_Exception("新增工作表时发生错误", e);
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }
    
    private long[] transferStringIdsToLongs(String strIds){
        if(strIds==null||"".equals(strIds)){
            return new long[]{};
        }
        String[] strings=strIds.split(",");
        long[] result=new long[strings.length];
        for(int i=0;i<result.length;i++){
            result[i]=Long.parseLong(strings[i]);
        }
        return result;
    }

    /**
     * 删除工作组
     * 
     * @param workingGroupId
     */
    public void deleteWorkingGroup(long workingGroupId) {
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            WorkingGroupSettingDao dao = new WorkingGroupSettingDao();
            dao.setStrTableName("sett_TemplateGroupSetting");
            dao.setConnection(con);
            dao.deletePhysically(workingGroupId);
            dao.setStrTableName("sett_TemplateSheetSetting");
            Collection formSettings = dao
                    .findWorkingFormSettingsBelongsToAWorkingGroupSetting(workingGroupId);
            TemplateSheetSetting baseLine = new TemplateSheetSetting();
            baseLine.setWorkingGroupId(-2);
            for (Iterator i = formSettings.iterator(); i.hasNext();) {
                TemplateSheetSetting each = (TemplateSheetSetting) i.next();
                each.setWorkingGroupId(-1);
                dao.update(each, baseLine);
            }
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            throw new TRF_Exception("删除工作表时发生错误", e);
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }

    /**
     * 修改工作组
     * 
     * @param groupSetting
     * @param workingFormIds
     */
    public void updateWorkingGroup(WorkingGroupSetting groupSetting,
            String strIds) {
        if(strIds.length()<1){
            throw new TRF_Exception("没有关联到组的表");
        }
        Connection con = DataBaseUtil.getConnection();
        try {
            con.setAutoCommit(false);
            WorkingGroupSettingDao dao = new WorkingGroupSettingDao();
            dao.setStrTableName("sett_TemplateGroupSetting");
            dao.setConnection(con);
            WorkingGroupSetting groupSettingBaseLine = new WorkingGroupSetting();
            groupSettingBaseLine.setName(null);
            dao.update(groupSetting, groupSettingBaseLine);
            dao.setStrTableName("sett_TemplateSheetSetting");
            long[] workingFormIds=transferStringIdsToLongs(strIds);
            Collection formSettings = dao
                    .findWorkingFormSettingsBelongsToAWorkingGroupSetting(groupSetting
                            .getId());
            //设置一个需要删除的表和一个需要添加的表,通过比较获取这两个表的值
            List idsToDelete = getIdsInFirstArrayAndNotInSecondArray(
                    getIdsFromWorkingFormCollection(formSettings),
                    workingFormIds);
            List idsToAdd = getIdsInFirstArrayAndNotInSecondArray(
                    workingFormIds,
                    getIdsFromWorkingFormCollection(formSettings));
            updateAList(idsToDelete, dao, -1);
            updateAList(idsToAdd, dao, groupSetting.getId());
            con.commit();
        } catch (Exception e) {
            DataBaseUtil.rollBackConnection(con);
            throw new TRF_Exception("修改工作表时发生错误", e);
        } finally {
            DataBaseUtil.closeDataBaseResource(con, null, null, null);
        }
    }

    private void updateAList(List ids, WorkingGroupSettingDao dao,
            long workingGroupId) {
        TemplateSheetSetting baseLine = new TemplateSheetSetting();
        baseLine.setWorkingGroupId(-2);
        for (Iterator i = ids.iterator(); i.hasNext();) {
            long each = ((Long) i.next()).longValue();
            TemplateSheetSetting setting = new TemplateSheetSetting();
            setting.setId(each);
            setting.setWorkingGroupId(workingGroupId);
            dao.update(setting, baseLine);
        }
    }

    private long[] getIdsFromWorkingFormCollection(Collection collection) {
        long[] result = new long[collection.size()];
        int counter = 0;
        for (Iterator i = collection.iterator(); i.hasNext();) {
            TemplateSheetSetting each = (TemplateSheetSetting) i.next();
            result[counter] = each.getId();
            counter++;
        }
        return result;
    }

    private List getIdsInFirstArrayAndNotInSecondArray(long[] firstArray,
            long[] secondArray) {
        List result = new LinkedList();
        for (int i = 0; i < firstArray.length; i++) {
            if (!isIdInAnArray(firstArray[i], secondArray)) {
                result.add(new Long(firstArray[i]));
            }
        }
        return result;
    }

    private boolean isIdInAnArray(long id, long[] array) {
        for (int i = 0; i < array.length; i++) {
            if (id == array[i]) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 查找工作组
     * @param queryObject
     * @return
     */
    public Collection findGroup(WorkingGroupSetting queryObject){
        return new WorkingGroupSettingDao("sett_TemplateGroupSetting").findByConditionOrderById(queryObject,null);
    }

}