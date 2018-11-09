package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.setting.dao.Sett_ExtSysReceiveModeDao;
import com.iss.itreasury.settlement.setting.dataentity.ExtSysReceiveModeInfo;


/**
 * 外部系统指令接收方式
 * @author xiangzhou
 *	2011-03-23
 */
public class ExtSysReceiveMode {
	
	/**
	 * 获取外部系统接收设置
	 * @return
	 * @throws Exception
	 */
	public Collection getExtSysReceiveMode() throws Exception{
		Sett_ExtSysReceiveModeDao dao = new Sett_ExtSysReceiveModeDao();
		return dao.getExtSysReceiveMode();
	}
	
	/**
	 * 设置接收方式
	 * @param info
	 * @throws Exception
	 */
	public void setExtSysReceiveMode(ExtSysReceiveModeInfo info) throws Exception{
		Sett_ExtSysReceiveModeDao dao = new Sett_ExtSysReceiveModeDao();
		dao.setExtSysReceiveMode(info);
	}

}
