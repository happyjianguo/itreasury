/*
 * Created on 2004-8-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.compatibilitysetting.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.compatibilitysetting.dao.Sett_CompatibilityTypeSettingDao;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingWhereInfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CompatibilityTypeSettingBean {
	private Sett_CompatibilityTypeSettingDao typeSettingDAO = new Sett_CompatibilityTypeSettingDao();
	
	/**
	 * 根据ID查询所有信息
	 * @param lId
	 * @return
	 * @throws SettlementException
	 */
	public CompatibilityTypeSettingInfo findById(long lId) throws SettlementException
	{
		CompatibilityTypeSettingInfo info = new CompatibilityTypeSettingInfo();
		try
		{
			info = (CompatibilityTypeSettingInfo)typeSettingDAO.findByID(lId,info.getClass());
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		return info;
	}
	/**
	 * 根据ID查询所有信息
	 * @param lId
	 * @return
	 * @throws SettlementException
	 */
	public CompatibilityTypeSettingInfo findByName(String TypeName) throws SettlementException
	{
		CompatibilityTypeSettingInfo info = new CompatibilityTypeSettingInfo();
		info = (CompatibilityTypeSettingInfo)typeSettingDAO.findTypeSettingByName(TypeName);
		return info;
	}
	
	/**
	 * 新增记录的方法
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public long save(CompatibilityTypeSettingInfo info) throws SettlementException
	{
		long lReturn = -1;
		try
		{
			typeSettingDAO.setUseMaxID();
			lReturn = typeSettingDAO.add(info);
		} catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		}
		return lReturn;
	}

	/**
	 * 根据ID，修改改条记录的方法
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long update(CompatibilityTypeSettingInfo info) throws Exception
	{
		long lReturn = -1;
		try
		{
			typeSettingDAO.update(info);
		}
		catch (ITreasuryDAOException e)
		{
			e.printStackTrace();throw new SettlementDAOException(e);
		} 
		return lReturn;
	}

	/**
	 * 根据办事处和币种 查询所有设置的记录
	 * @param conditionInfo
	 * @return
	 * @throws Exception
	 */
	public Vector findAll(CompatibilityTypeSettingWhereInfo conditionInfo) throws Exception
	{
		return typeSettingDAO.findAllTypeSetting(conditionInfo.getOfficeId(),conditionInfo.getCurrencyId());
	}

	public Vector findByConditionInfo(CompatibilityTypeSettingWhereInfo conditionInfo) throws SettlementDAOException
	{
	    return typeSettingDAO.findByConditionInfo(conditionInfo);
	}
}
