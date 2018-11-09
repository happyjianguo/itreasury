package com.iss.itreasury.fcinterface.extsystem.user.bizlogic;

import java.util.Collection;

import com.iss.itreasury.fcinterface.extsystem.user.dao.EP_UserDao;
import com.iss.itreasury.fcinterface.extsystem.user.dataentity.EP_UserInfo;

public class EP_UserBiz {
	/**
	 * 多条件查询
	 * @param infoCondition
	 * @return
	 * @throws Exception
	 */
	public Collection findByCondition(EP_UserInfo infoCondition) throws Exception
	{
		EP_UserDao dao=new EP_UserDao();
		return dao.findByCondition(infoCondition);
	}
	/**
	 * 新增方法
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long add(EP_UserInfo info)throws Exception
	{
		EP_UserDao dao=new EP_UserDao();
		return dao.add(info);
	}

}
