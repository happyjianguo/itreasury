package com.iss.itreasury.codingrule.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.codingrule.dao.EncodingRulesDao;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class EncodingRulesBiz {

	EncodingRulesDao encodingRulesDao = new EncodingRulesDao();
	/**
	 * 编码规则设置查询类
	 * add by liaiyi 2012-12-10
	 */
   public PagerInfo queryCodingRuleDetailInfo(long officeid,long statusID) throws Exception {
		
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = encodingRulesDao.queryCodingRuleDetailSQL(officeid,statusID);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("id");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("name");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"name","id","name"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG, PagerTypeConstant.STRING});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("statusid");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SYSConstant.CodingRuleStatus.class, "getName", new Class[]{long.class});
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
