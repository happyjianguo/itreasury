package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.setting.dao.Sett_SpecialOperationQueryDAO;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

/**
 * 特殊业务类型设置——新UI 
 * @author 宋雯霄
 */

public class Sett_SpecialOperationQueryBiz {
	
	Sett_SpecialOperationQueryDAO dao =new Sett_SpecialOperationQueryDAO();
	
	public PagerInfo querySpecialOperation(long lcurrencyID,long lOfficeID, long lStartID, long lEndID, String strContext) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.querySpecialOperationSql(lcurrencyID, lOfficeID, lStartID, lEndID, strContext);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList =new ArrayList();
			PagerDepictBaseInfo baseInfo=null;
			
			//特殊业务类型编码
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"ID", "ID", "payRelation", "gatheringRelation"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.LONG, PagerTypeConstant.LONG, PagerTypeConstant.LONG, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE+ "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//特殊业务类型名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
						
			//选项
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sContent");
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
