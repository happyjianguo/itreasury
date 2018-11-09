package com.iss.itreasury.securities.mywork.bizlogic;

/*
 * Created on 2007-09-11
 *
 * Title:				iTreasury
 * @author             	杨垒、何小文
 * Company:             iSoftStone
 * @version				iTreasury4.0新增
 * Description:         产品化4.0在证券新增审批流,该功能实现查找我的任务
 */

import com.iss.itreasury.securities.mywork.dao.SecApplyActionDao;
import com.iss.itreasury.securities.mywork.dao.SecDeliveryActionDao;
import com.iss.itreasury.securities.mywork.dao.SecMyWorkDao;
import com.iss.itreasury.securities.mywork.dao.SecNoticeActionDao;
import com.iss.itreasury.securities.mywork.dataentity.SecMyWorkInfo;

public class SecMyWorkBiz implements java.io.Serializable {

	public Object querySecApplyActionWork(SecMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		SecMyWorkDao dao = new SecApplyActionDao();
		objReturn = dao.queryMyWork(qInfo);	
		return objReturn;
	}
	
	public Object querySecDeliveryActionWork(SecMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		SecMyWorkDao dao = new SecDeliveryActionDao();
		objReturn = dao.queryMyWork(qInfo);	
		return objReturn;
	}
	
	public Object querySecNoticeActionWork(SecMyWorkInfo qInfo) throws Exception {
		Object objReturn = null;
		SecMyWorkDao dao = new SecNoticeActionDao();
		objReturn = dao.queryMyWork(qInfo);	
		return objReturn;
	}
}
