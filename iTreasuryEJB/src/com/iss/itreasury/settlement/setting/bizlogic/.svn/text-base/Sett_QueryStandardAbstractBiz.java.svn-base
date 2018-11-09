package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.setting.dao.Sett_StandardAbstractDAO;
import com.iss.itreasury.settlement.setting.dataentity.StandardAbstractInfo;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class Sett_QueryStandardAbstractBiz {
	
	Sett_StandardAbstractDAO dao =new Sett_StandardAbstractDAO();
	
	public PagerInfo queryStandardAbastract(StandardAbstractInfo info) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryStandardAbstractSql(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList =new ArrayList();
			PagerDepictBaseInfo baseInfo=null;
			
			//摘要代号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SCODE");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"SCODE", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//摘要描述
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SDESC");
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
