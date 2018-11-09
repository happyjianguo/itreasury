package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.setting.dao.Sett_OfficeQueryDAO;
import com.iss.itreasury.settlement.setting.dataentity.OfficeInfo;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class Sett_OfficeBiz {
	
	Sett_OfficeQueryDAO dao =new Sett_OfficeQueryDAO();
	
	public PagerInfo queryOfficefindByConditions(OfficeInfo office) throws Exception {
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try {
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryOfficeSql();
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//机构编号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sCode");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sCode", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//机构名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
						
			//是否总部
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ORGLEVEL");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
			
		}
		
		return pagerInfo;
		
	}

}
