package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.setting.dao.Sett_GeneralLedgerQueryDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class Sett_GeneralLedgerQueryBiz {
	
	Sett_GeneralLedgerQueryDAO dao =new Sett_GeneralLedgerQueryDAO();
	
	public PagerInfo queryGeneralLedger(long lOfficeID, long lCurrencyID, String strStartCode, String strEndCode, String strName, String strSubject) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryGeneralLedgerSql(lOfficeID, lCurrencyID, strStartCode, strEndCode, strName, strSubject);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList =new ArrayList();
			PagerDepictBaseInfo baseInfo=null;
			
			//分录编码
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sGeneralLedgerCode");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.StrGeneralLedgerCode.class, "getName", new Class[]{String.class});
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sGeneralLedgerCode", "ID"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//分录名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
						
			//对应科目
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sSubjectCode");
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
