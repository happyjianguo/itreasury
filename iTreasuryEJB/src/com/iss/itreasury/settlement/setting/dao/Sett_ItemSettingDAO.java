/*
 * Created on 2005-1-7
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dataentity.ItemSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;

/**
 * @author ygzhao
 *  
 */
public class Sett_ItemSettingDAO extends ITreasuryDAO
{
    /**     
     */
    public Sett_ItemSettingDAO()
    {
        super("sett_ItemSetting");
    }

    /**
     * @param conn
     */
    public Sett_ItemSettingDAO(Connection conn)
    {
        super("sett_ItemSetting", conn);
    }

    /**
     * 保存
     * 
     * @param ItemSettingInfo
     *            info
     * @return void
     * @exception throws
     *                ITreasuryDAOException
     */
    public void add(ItemSettingInfo info) throws ITreasuryDAOException
    {
        try
        {
            super.add(info);
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("保存异常", e);
        }
    }

    /**
     * 删除
     * 
     * @param long
     *            id
     * @return void
     * @exception throws
     *                ITreasuryDAOException
     */
    public void delete(long lID) throws ITreasuryDAOException
    {        
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE \n");
            buffer.append(strTableName);
            buffer.append(" SET STATUSID = " + Constant.RecordStatus.INVALID);
            String time = Env.getSystemDateTime().toString();
            time = time.substring(0, 19);
            buffer.append(" , MODIFYDATE = to_date('" + time
                    + "','YYYY-MM-DD-HH24:MI:SS')");//当前时间
            buffer.append("\n  WHERE ID = " + lID);
            String strSQL = buffer.toString();
            log.debug(strSQL);
            prepareStatement(strSQL);
            executeQuery();
            finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("状态更新异常", e);
        }
    }

    /**
     * 修改
     * 
     * @param ItemSettingInfo
     *            info
     * @return void
     * @exception throws
     *                ITreasuryDAOException
     */
    public void update(ItemSettingInfo info) throws ITreasuryDAOException
    {
        try
        {
            super.update(info);
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("修改异常", e);
        }
    }

    /**
     * 通过ID查找结果
     * 
     * @param long
     *            id
     * @return ItemSettingInfo
     * @exception throws
     *                ITreasuryDAOException
     */
    public ItemSettingInfo findByID(long id) throws ITreasuryDAOException
    {
        ItemSettingInfo info = null;
        try
        {
            info = (ItemSettingInfo) super.findByID(id, ItemSettingInfo.class);
        } catch (ITreasuryDAOException ie)
        {

        }
        return info;
    }

    /**
     * 查找 按项目类型 1、资产负债及表外项目设置 2、损益表项目设置
     * 
     * @param long
     *            info
     * @return void
     * @exception throws
     *                ITreasuryDAOException
     */
    public Collection findByItemType(long itemType)
            throws ITreasuryDAOException
    {
        Collection collection = null;
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("SELECT * FROM \n");
            buffer.append(strTableName);
            buffer.append("\n WHERE itemType = " + itemType);
            String strSQL = buffer.toString();
            log.debug("按项目类型查找 sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            collection = this
                    .getDataEntitiesFromResultSet(ItemSettingInfo.class);
            finalizeDAO();

        } catch (ITreasuryDAOException e)
        {

        }
        return collection;
    }
}