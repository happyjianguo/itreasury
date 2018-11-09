package com.iss.itreasury.project.wisgfc.settlement.set.bizlogic;


import com.iss.itreasury.project.wisgfc.settlement.set.dao.QueryAccountRebateDao;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SetAcountRebateInfo;

public class QueryAccountRebateBiz {
	public void addSetAcountRebateInfo(SetAcountRebateInfo[] setAcountRebateInfos) throws Exception{
		QueryAccountRebateDao accountRebateDao = new QueryAccountRebateDao();
		accountRebateDao.save(setAcountRebateInfos);
	}
		
	public void updateSetAcountRebateInfo(SetAcountRebateInfo[] setAcountRebateInfos)throws Exception{
		QueryAccountRebateDao accountRebateDao = new QueryAccountRebateDao();
		accountRebateDao.update(setAcountRebateInfos);
	}
}
