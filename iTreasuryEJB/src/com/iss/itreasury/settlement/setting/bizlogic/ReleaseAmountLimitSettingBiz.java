/*
 * Created on 2005-1-17
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dao.Sett_ReleaseAmountLimitSettingDAO;
import com.iss.itreasury.settlement.setting.dataentity.ReleaseAmountlimitSettingInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

/**
 * @author ygzhao
 *  
 */
public class ReleaseAmountLimitSettingBiz
{
    private Connection conn = null;

    private Sett_ReleaseAmountLimitSettingDAO releaseAmountLimitSettingDAO = null;

    /**
     *  
     */
    public ReleaseAmountLimitSettingBiz()
    {
        try
        {
            conn = Database.getConnection();
            releaseAmountLimitSettingDAO = new Sett_ReleaseAmountLimitSettingDAO(
                    conn);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ReleaseAmountlimitSettingInfo findByID(long lID) throws IException
    {
        ReleaseAmountlimitSettingInfo info = null;
        info = releaseAmountLimitSettingDAO.findByID(lID);
        return info;
    }

    public Collection findByCondition(ReleaseAmountlimitSettingInfo info)
            throws IException
    {
        Collection collection = null;
        collection = releaseAmountLimitSettingDAO.findByCondition(info);
        return collection;
    }

    public long save(ReleaseAmountlimitSettingInfo info) throws IException
    {
        return releaseAmountLimitSettingDAO.add(info);
    }

    public void delete(long lID) throws IException
    {
        releaseAmountLimitSettingDAO.delete(lID);
    }

    public long update(ReleaseAmountlimitSettingInfo info)
    {
        long lReturn = -1;
        try
        {
            lReturn = releaseAmountLimitSettingDAO.update(info);            
        } catch (ITreasuryDAOException e)
        {
        }
        return lReturn;
    }

    public Collection findByFlag(long flag)
    {
        Collection collection = null;
        try
        {
            collection = releaseAmountLimitSettingDAO.findByFlag(flag);
        } catch (ITreasuryDAOException e)
        {
        }
        return collection;
    }

}