/*
 * Created on 2004-8-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.util.Vector;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.compatibilitysetting.bizlogic.CompatibilityTypeSettingBean;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilityTypeSettingWhereInfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CompatibilityTypeSettingDelegation {
	private CompatibilityTypeSettingBean settingBean = new CompatibilityTypeSettingBean();
	
	/**
	 * 根据ID查询所有信息
	 * @param lId
	 * @return
	 * @throws SettlementException
	 */
	public CompatibilityTypeSettingInfo findById(long lId) throws Exception
	{
		return settingBean.findById(lId);
	}
	/**
	 * 根据name查询信息
	 * @param TypeName
	 * @return
	 * @throws Exception
	 */
	public CompatibilityTypeSettingInfo findByName(String TypeName) throws Exception
	{
		return settingBean.findByName(TypeName);
	}
	/**
	 * 新增记录的方法
	 * @param info
	 * @return
	 * @throws SettlementException
	 */
	public long save(CompatibilityTypeSettingInfo info) throws Exception
	{
		return settingBean.save(info);
	}

	/**
	 * 根据ID，修改改条记录的方法
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long update(CompatibilityTypeSettingInfo info) throws Exception
	{
		return settingBean.update(info);
	}

	/**
	 * 根据办事处和币种 查询所有设置的记录
	 * @param conditionInfo
	 * @return
	 * @throws Exception
	 */
	public Vector findAll(CompatibilityTypeSettingWhereInfo conditionInfo) throws Exception
	{
		return settingBean.findAll(conditionInfo);
	}
	
	/**
	 * 根据设置条件进行兼容业务类型查询
	 * @throws SettlementDAOException
	 * @author ygniu
	 *	
	 */
	public Vector findByConditionInfo(CompatibilityTypeSettingWhereInfo conditionInfo) throws SettlementDAOException
	{
	    return settingBean.findByConditionInfo(conditionInfo);
	}
	
}
