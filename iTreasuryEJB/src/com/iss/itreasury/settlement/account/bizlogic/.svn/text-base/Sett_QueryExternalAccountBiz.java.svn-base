package com.iss.itreasury.settlement.account.bizlogic;

import java.util.ArrayList;

import com.iss.itreasury.settlement.account.dao.Sett_ExternalAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.ExternalAccountInfo;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;

public class Sett_QueryExternalAccountBiz {
	
	Sett_ExternalAccountDAO dao =new Sett_ExternalAccountDAO();
	
	public PagerInfo queryExternalAccount(ExternalAccountInfo info) throws Exception{
		
		PagerInfo pagerInfo =null;
		String sql=null;
		
		try {
			
			pagerInfo=new PagerInfo();
			//得到查询的SQL
			sql=dao.queryPayeeSql(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList =new ArrayList();
			PagerDepictBaseInfo baseInfo=null;
			
			//id
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("ID");
			baseInfo.setDisplayType(PagerTypeConstant.LONG);
			depictList.add(baseInfo);
			
			//外部银行账户名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SEXTACCTNAME");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"SEXTACCTNAME", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			//外部银行账户号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SEXTACCTNO");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//汇入行名称
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SBANKNAME");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//汇入省
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SPROVINCE");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//汇入市 
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("SCITY");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//联行号 
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("spayeebankexchangeno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//CNAPS号 
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("spayeebankcnapsno");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			//机构号
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("spayeebankorgno");
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
