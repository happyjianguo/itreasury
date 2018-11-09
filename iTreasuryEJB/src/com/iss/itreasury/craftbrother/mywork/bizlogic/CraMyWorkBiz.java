/*
 * Created on 2007-06-21
 * 
 *
 * Title:				iTreasury
 * @author             	付明正 
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在信贷新增审批流,该功能实现查找我的任务
 */
package com.iss.itreasury.craftbrother.mywork.bizlogic;

import com.iss.itreasury.craftbrother.mywork.dataentity.CraMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dataentity.CreditSettingMyWorkInfo;
import com.iss.itreasury.craftbrother.mywork.dao.CraTransActionDao;
import com.iss.itreasury.craftbrother.mywork.dao.CraMyWorkDao;
import com.iss.itreasury.craftbrother.mywork.dao.CreditSettingMyWorkDao;


public class CraMyWorkBiz implements java.io.Serializable {

	public Object queryCraTransActionWork(CraMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		CraMyWorkDao dao = new CraTransActionDao();
		objReturn = dao.queryMyWork(qInfo);
		return objReturn;
	}
	
	public Object queryCreditSettingWork(CreditSettingMyWorkInfo qInfo) throws Exception{
		Object objReturn = null;
		CraMyWorkDao dao = new CreditSettingMyWorkDao();
		objReturn = dao.queryMyWork(qInfo);
		return objReturn;
	}
}
