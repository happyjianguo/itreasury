package com.iss.itreasury.system.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.system.setting.dao.DepartmentDAO;
import com.iss.itreasury.system.setting.dataentity.DepartmentInfo;
import com.iss.itreasury.system.util.SYSConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class DepartmentBiz {

	DepartmentDAO departmentDao = new DepartmentDAO();
	
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
			sql = departmentDao.queryCodingRuleDetailSQL(officeid,statusID);
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
/**
	 * 部门设置查询类
	 * add by liaiyi 2012-12-10
	 */
public PagerInfo querydepartmentInfo(DepartmentInfo departmentInfo ) throws Exception {
	
	PagerInfo pagerInfo = null;
	String sql = null;
	try
	{
		pagerInfo = new PagerInfo();
		sql = departmentDao.querydepartmentSQL(departmentInfo);
		pagerInfo.setSqlString(sql);
		
		ArrayList depictList = new ArrayList();
		PagerDepictBaseInfo baseInfo = null;
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("departmentCode");
//		baseInfo.setDisplayType(PagerTypeConstant.STRING);
//		depictList.add(baseInfo);
		baseInfo.setExtension(true);
		baseInfo.setExtensionName(new String[]{"departmentCode","ID","departmentCode"});
		baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.STRING, PagerTypeConstant.STRING});
		baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE );
		depictList.add(baseInfo);
		
		baseInfo = new PagerDepictBaseInfo();
		baseInfo.setDisplayName("departmentName");
		baseInfo.setDisplayType(PagerTypeConstant.STRING);
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
