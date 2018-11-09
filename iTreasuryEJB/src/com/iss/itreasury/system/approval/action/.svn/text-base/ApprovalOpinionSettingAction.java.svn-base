package com.iss.itreasury.system.approval.action;

import java.util.Map;

import com.iss.itreasury.system.approval.bizlogic.ApprovalOpinionSettingBiz;
import com.iss.itreasury.system.approval.dataentity.ApprovalOpinionSettingInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class ApprovalOpinionSettingAction {
	
	ApprovalOpinionSettingBiz approvalOpinionSettingBiz = new ApprovalOpinionSettingBiz();
	
	/**
	 * 查询已设置审批流的操作类型action
	 * @author zk 2012-12-12
	 *
	 */
	public PagerInfo queryApprovalOpinionInfo(Map map) throws Exception {
		
		PagerInfo pagerInfo = null;
		try
		{
			ApprovalOpinionSettingInfo approvalOpinionSettingInfo = new ApprovalOpinionSettingInfo();
			approvalOpinionSettingInfo.convertMapToDataEntity(map);//将Map转化为INFO
			pagerInfo = approvalOpinionSettingBiz.queryApprovalOpinionInfo(approvalOpinionSettingInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage(),e);
		}
		return pagerInfo;
	}

}
