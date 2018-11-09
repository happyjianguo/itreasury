/*
 * Created on 2005-1-17
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.setting.dataentity.ReleaseAmountlimitSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * @author ygzhao
 *  
 */
public class Sett_ReleaseAmountLimitSettingDAO extends ITreasuryDAO
{
    /**     
     */
    public Sett_ReleaseAmountLimitSettingDAO()
    {
        super("sett_ReleaseAmountLimitSetting");
    }

    /**
     * @param conn
     */
    public Sett_ReleaseAmountLimitSettingDAO(Connection conn)
    {
        super("sett_ReleaseAmountLimitSetting", conn);
    }

    /**
     * 保存
     * 
     * @param ReleaseAmountlimitSettingInfo
     *            info
     * @return long
     * @exception throws
     *                ITreasuryDAOException
     */
    public long add(ReleaseAmountlimitSettingInfo info)
            throws ITreasuryDAOException
    {
        long lReturn = -1;
        Collection collection = null;
        ReleaseAmountlimitSettingInfo tempInfo = new ReleaseAmountlimitSettingInfo();
        try
        {
            if (info.getClientSettingFlag() == 1)
            {
                //查找是否已有相同的 客户编号/分析项目/生效日期 组合
                tempInfo.setClientID(info.getClientID());
                tempInfo.setProjectID(info.getProjectID());
                tempInfo.setEfficientDate(info.getEfficientDate());
                tempInfo.setStatusID(Constant.RecordStatus.VALID);
                tempInfo.setClientSettingFlag(1);//客户编号设置
                collection = super.findByCondition(tempInfo);
                if (collection != null && collection.size() > 0)//重复增加
                {
                    lReturn = 1;
                    return lReturn;
                }

                collection = null;                
                ReleaseAmountlimitSettingInfo parameterInfo = new ReleaseAmountlimitSettingInfo();
                parameterInfo.setClientType(info.getClientType());
                parameterInfo.setProjectID(info.getProjectID());
                tempInfo.setEfficientDate(info.getEfficientDate());
                parameterInfo.setStatusID(Constant.RecordStatus.VALID);
                tempInfo.setClientSettingFlag(2);//客户类型设置
                collection = this.findByCondition(parameterInfo);

                if (collection == null || (collection!=null && collection.size()==0))//该客户所属的客户类型及相应的统计项目没有设置豁免额度
                    lReturn = 2;
                else
                {
                    ReleaseAmountlimitSettingInfo tempInfo2 = null;
                    tempInfo2 = (ReleaseAmountlimitSettingInfo) collection
                            .iterator().next();
                    if (tempInfo2 != null
                            && info.getReleaseAmountLimit() > tempInfo2
                                    .getReleaseAmountLimit())
                    {//该客户的豁免额度大于所属客户类型的豁免额度
                        lReturn = 3;
                        return lReturn;
                    }
                }

            } else if (info.getClientSettingFlag() == 2)
            {
                //查找是否已有相同的 客户类型/分析项目/生效日期 组合
                tempInfo.setClientType(info.getClientType());
                tempInfo.setProjectID(info.getProjectID());
                tempInfo.setEfficientDate(info.getEfficientDate());
                tempInfo.setStatusID(Constant.RecordStatus.VALID);
                tempInfo.setClientSettingFlag(2);//客户类型设置
                collection = super.findByCondition(tempInfo);
                if (collection != null && collection.size() > 0)//重复增加
                {
                    lReturn = 4;
                    return lReturn;
                }
            }
            this.setUseMaxID();
            super.add(info);
        } catch (ITreasuryDAOException e)
        {
            throw new ITreasuryDAOException("保存异常", e);
        }
        return lReturn;
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
     * @param ReleaseAmountlimitSettingInfo
     *            info
     * @return long
     * @exception throws
     *                ITreasuryDAOException
     */
    public long update(ReleaseAmountlimitSettingInfo info)
            throws ITreasuryDAOException
    {
        long lReturn = -1;
        if (info.getClientSettingFlag() == 1)
        {
            //该客户的豁免额度不能大于所属客户类型的豁免额度
            ReleaseAmountlimitSettingInfo tempInfo = null;
            tempInfo = this.findByID(info.getID());
            if (tempInfo != null
                    && info.getReleaseAmountLimit() > tempInfo
                            .getReleaseAmountLimit())
            {
                lReturn = 1;
                return lReturn;
            }
        }

        initDAO();

        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE " + strTableName + " SET \n");
        buffer.append(" efficientDate = to_date('" + DataFormat.getDateString(info.getEfficientDate()) + "','yyyy-mm-dd')");
        buffer.append(", releaseAmountLimit = " + info.getReleaseAmountLimit());
        buffer.append(", awokeRate = " + info.getAwokeRate());
        buffer.append(" WHERE ID = " + info.getID());

        String strSQL = buffer.toString();
        log.debug("update----" + strSQL);
        prepareStatement(strSQL);
        executeUpdate();

        finalizeDAO();
        return lReturn;
    }

    /**
     * 通过ID查找结果
     * 
     * @param long
     *            id
     * @return ReleaseAmountlimitSettingInfo
     * @exception throws
     *                ITreasuryDAOException
     */
    public ReleaseAmountlimitSettingInfo findByID(long id)
            throws ITreasuryDAOException
    {
        ReleaseAmountlimitSettingInfo info = null;
        try
        {
            info = (ReleaseAmountlimitSettingInfo) super.findByID(id,
                    ReleaseAmountlimitSettingInfo.class);
        } catch (ITreasuryDAOException ie)
        {

        }
        return info;
    }

    /**
     * 按客户设置标记查找 1、客户编号设置 2、客户类型设置
     * 
     * @param long
     *            info
     * @return void
     * @exception throws
     *                ITreasuryDAOException
     */
    public Collection findByFlag(long flag) throws ITreasuryDAOException
    {
        Collection collection = null;
        try
        {
            initDAO();
            StringBuffer buffer = new StringBuffer();
            buffer.append("SELECT * FROM \n");
            buffer.append(strTableName);
            buffer.append(" WHERE clientSettingFlag = " + flag);
            buffer.append(" and statusID = 1 ");
            String strSQL = buffer.toString();
            log.debug("按客户设置标记查找 sql = \n" + strSQL);
            prepareStatement(strSQL);
            executeQuery();
            collection = this
                    .getDataEntitiesFromResultSet(ReleaseAmountlimitSettingInfo.class);
            finalizeDAO();

        } catch (ITreasuryDAOException e)
        {

        }
        return collection;
    }
    
    /**
     * 查询某项目设置豁免额度列表
     * @param projectID
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findByProjectID(long projectID) throws ITreasuryDAOException
	{
    	Collection c = null;
    	
    	ReleaseAmountlimitSettingInfo info = new ReleaseAmountlimitSettingInfo();
    	info.setProjectID(projectID);
    	info.setStatusID(Constant.RecordStatus.VALID);
    	try
		{
			c = this.findByCondition(info);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();
			throw e;
		}
    	return c;
	}
}