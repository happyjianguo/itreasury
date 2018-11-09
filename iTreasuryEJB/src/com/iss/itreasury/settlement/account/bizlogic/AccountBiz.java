package com.iss.itreasury.settlement.account.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.account.dao.Sett_QueryAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class AccountBiz {
	
	Sett_QueryAccountDAO dao=new Sett_QueryAccountDAO();
	public PagerInfo findByConditions( QueryAccountConditionInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//账户编号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sAccountNo");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sAccountNo", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//账户名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
				
			//复核状态
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nCheckStatusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.AccountCheckStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//录入人
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nInputUserID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getUserNameByID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//录入日期
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("dtInput");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			//账户状态
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nStatusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.AccountStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//利率计划
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getInterestRatePlanNameByAccountID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		return pagerInfo;
		
		
		
	}
		
	public PagerInfo findByConditionsModify( QueryAccountConditionInfo info) throws Exception
	{
		
		PagerInfo pagerInfo=null;
		String sql=null;
		try
		{
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.getSQL(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			//账户编号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sAccountNo");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"sAccountNo", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//账户名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("sName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
				
			//账户状态
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nStatusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.AccountStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//利率计划
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(NameRef.class, "getInterestRatePlanNameByAccountID", new Class[]{long.class});
			depictList.add(baseInfo);
			
			//复核状态
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("nCheckStatusID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(SETTConstant.AccountCheckStatus.class, "getName", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常:"+e.getMessage());
		}
		return pagerInfo;

}
}
