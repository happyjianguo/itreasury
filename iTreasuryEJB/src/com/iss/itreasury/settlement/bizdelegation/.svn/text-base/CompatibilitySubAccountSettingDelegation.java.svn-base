/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.settlement.compatibilitysetting.bizlogic.CompatibilitySubAccountSettingBean;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingInfo;
import com.iss.itreasury.settlement.compatibilitysetting.dataentity.CompatibilitySubAccountSettingWhereInfo;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CompatibilitySubAccountSettingDelegation {
	private CompatibilitySubAccountSettingBean subAccountBean = new CompatibilitySubAccountSettingBean();
	
	/*
	 * findById 方法
	 */
	public CompatibilitySubAccountSettingInfo findById(long lId) throws Exception
	{
		return subAccountBean.findById(lId);
	}
	/*
	 * 保存新增子账户的方法
	 */
	public long save(CompatibilitySubAccountSettingInfo info) throws Exception
	{
		return subAccountBean.save(info);
	}
	/*
	 * 根据ID 修改子账户信息
	 */
	public long update(CompatibilitySubAccountSettingInfo info) throws Exception
	{
		return subAccountBean.update(info);
	}
	/*
	 * 根据条件查找账户
	 */
	public Collection findByConditions(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findByConditions(conditionInfo);
	}
	/*
	 * 录入的链接查找
	 */
	public Collection findBySaveLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findBySaveLinkQuery(conditionInfo);
	}
	/*
	 * 录入复核的链接查找
	 */
	public Collection findByCheckLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findByCheckLinkQuery(conditionInfo);
	}
	
	/*
	 * 根据LoanNoteID取得合同ID
	 */
	public long findContractIDByLoanNoteID(long lLoanNoteID) throws Exception
	{
		return subAccountBean.findContractIDByLoanNoteID(lLoanNoteID);
	}

	/*
	 * 查找已有账户已经修改，并需要复核的账户
	 */
	public Collection findByOldCheckQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception 
	{
		return subAccountBean.findByOldCheckQuery(conditionInfo);
	}
	
}
