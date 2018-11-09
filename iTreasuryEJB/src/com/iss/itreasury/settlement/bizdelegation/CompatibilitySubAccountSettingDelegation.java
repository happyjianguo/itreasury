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
	 * findById ����
	 */
	public CompatibilitySubAccountSettingInfo findById(long lId) throws Exception
	{
		return subAccountBean.findById(lId);
	}
	/*
	 * �����������˻��ķ���
	 */
	public long save(CompatibilitySubAccountSettingInfo info) throws Exception
	{
		return subAccountBean.save(info);
	}
	/*
	 * ����ID �޸����˻���Ϣ
	 */
	public long update(CompatibilitySubAccountSettingInfo info) throws Exception
	{
		return subAccountBean.update(info);
	}
	/*
	 * �������������˻�
	 */
	public Collection findByConditions(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findByConditions(conditionInfo);
	}
	/*
	 * ¼������Ӳ���
	 */
	public Collection findBySaveLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findBySaveLinkQuery(conditionInfo);
	}
	/*
	 * ¼�븴�˵����Ӳ���
	 */
	public Collection findByCheckLinkQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception
	{
		return subAccountBean.findByCheckLinkQuery(conditionInfo);
	}
	
	/*
	 * ����LoanNoteIDȡ�ú�ͬID
	 */
	public long findContractIDByLoanNoteID(long lLoanNoteID) throws Exception
	{
		return subAccountBean.findContractIDByLoanNoteID(lLoanNoteID);
	}

	/*
	 * ���������˻��Ѿ��޸ģ�����Ҫ���˵��˻�
	 */
	public Collection findByOldCheckQuery(CompatibilitySubAccountSettingWhereInfo conditionInfo) throws Exception 
	{
		return subAccountBean.findByOldCheckQuery(conditionInfo);
	}
	
}
