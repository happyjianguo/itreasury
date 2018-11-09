package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.setting.dao.OfficeClearingAccountSetDao;
import com.iss.itreasury.settlement.setting.dataentity.OfficeClearingAccountInfo;

public class OfficeClearingAccountSetBiz {
	
	
	
	/**
	 * 查找机构间清算科目设置信息
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryOfficeClearingAccountSet(OfficeClearingAccountInfo info) throws Exception
	{
		Collection vReturn = new Vector();
		OfficeClearingAccountSetDao dao = new OfficeClearingAccountSetDao();
		if(!dao.isOfficeClearingAccountSetExist(info))
		{
			dao.addOfficeClearingAccountSet(info);
		}
		vReturn = dao.queryOfficeClearingAccountSet(info);
		
		return vReturn.size() > 0 ? vReturn : null;
	}
	
	
	/**
	 * 根据ID获得机构间清算设置信息
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws Exception
	 */
	public Collection findOfficeCliearingAccountSetByID(OfficeClearingAccountInfo clientInfo) throws Exception{
		
		Collection coll = null;
		OfficeClearingAccountSetDao dao = new OfficeClearingAccountSetDao();
		coll = dao.findOfficeCliearingAccountSetByID(clientInfo);
		return coll;
	}
	

	/**
	 * 更新机构间清算设置信息
	 * @param OfficeClearingAccountInfo
	 * @return
	 * @throws Exception
	 */
	public long updateExtClientInfo(OfficeClearingAccountInfo clientInfo) throws Exception{
		long id = -1;
		OfficeClearingAccountSetDao dao = new OfficeClearingAccountSetDao();
			id = dao.updateExtClientInfo(clientInfo);
		return id;
	}
}
