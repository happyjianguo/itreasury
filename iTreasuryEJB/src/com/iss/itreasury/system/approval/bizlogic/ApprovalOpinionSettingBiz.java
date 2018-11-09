package com.iss.itreasury.system.approval.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.approval.dao.ApprovalOpinionSettingDao;
import com.iss.itreasury.system.approval.dataentity.ApprovalOpinionSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class ApprovalOpinionSettingBiz {
	
	ApprovalOpinionSettingDao approvalOpinionSettingDao = new ApprovalOpinionSettingDao();
	
	/**
	 * 查询已设置审批流的操作类型biz
	 * @author zk 2012-12-12
	 *
	 */
   public PagerInfo queryApprovalOpinionInfo(ApprovalOpinionSettingInfo approvalOpinionSettingInfo) throws Exception{
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = approvalOpinionSettingDao.queryApprovalOpinionInfo(approvalOpinionSettingInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CODE");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"CODE", "ID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("MODULEID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(Constant.ModuleType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("DESCRIPTION");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("INPUTUSERID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getUserNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("INPUTDATE");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("CURRENCYID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(Constant.CurrencyType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		
		return pagerInfo;
	}

}
