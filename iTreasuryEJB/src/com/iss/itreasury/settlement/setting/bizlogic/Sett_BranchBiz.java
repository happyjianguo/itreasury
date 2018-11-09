package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.setting.dao.Sett_BranchQueryDAO;
import com.iss.itreasury.settlement.setting.dataentity.QueryBranchInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class Sett_BranchBiz {
	
	Sett_BranchQueryDAO dao =new Sett_BranchQueryDAO();
	
	public PagerInfo queryBranchInfo(QueryBranchInfo qbInfo) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryBranchSql(qbInfo);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList =new ArrayList();
			PagerDepictBaseInfo baseInfo=null;
			
			//开户行代号 
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SCODE");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"SCODE", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//开户行名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SNAME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
						
			//对应科目号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SSUBJECTCODE");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//分行（省）
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SBRANCHPROVINCE");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//分行（市）
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SBRANCHCITY");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//开户行科目类型
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("BANKSUBJECTTYPE");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.BankSubjectType.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//银行账户编号 
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SBANKACCOUNTCODE");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false) == true)
			{	
				//账户名称
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SENTERPRISENAME");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
			}
			
			if (Config.getBoolean(
					ConfigConstant.SETTLEMENT_BRANCH_ISSINGLEACCOUNT, false)) 
			{
				//是否单边账
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NISSINGLE");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				//挂账贷方现金科目
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SCASHCREDITBOOKEDACCOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				//挂账借方现金科目
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("SCASHDEBITBOOKEDACCOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				//挂账贷方银行转账科目
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("STRANSFERCREDITBOOKEDACCOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				//挂账借方银行转账科目
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("STRANSFERDEBITBOOKEDACCOUNT");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
			}
			
			if (Config.getBoolean(Config.INTEGRATION_SERVICE_ISVALID, false) == true) 
			{
				//是否生成银行指令 
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NISAUTOVIREMENTBYBANK");
				baseInfo.setDisplayType(PagerTypeConstant.STRING);
				depictList.add(baseInfo);
				
				//银行类型 
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName("NBANKTYPE");
				baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
				baseInfo.setExtensionMothod(SETTConstant.BankType.class, "getName", new Class[]{long.class});
				depictList.add(baseInfo);
			}
			pagerInfo.setDepictList(depictList);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		
		return pagerInfo;
		
	}

}
