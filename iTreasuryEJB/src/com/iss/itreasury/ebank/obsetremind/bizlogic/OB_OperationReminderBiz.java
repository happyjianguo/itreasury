/*
 * Credate date 2007/06/21
 */
package com.iss.itreasury.ebank.obsetremind.bizlogic;

import java.util.Collection;

import com.iss.itreasury.ebank.obawake.dataentity.OBAwakeCondition;
import com.iss.itreasury.ebank.obsetremind.dao.OB_OperationReminderDAO;
import com.iss.itreasury.ebank.obsetremind.dataentity.OB_OperationReminderInfo;
import com.iss.itreasury.util.IException;

/**
 * author leiyang
 * Modify by leiyang date 2007/06/21
 */
public class OB_OperationReminderBiz {
	
	private OB_OperationReminderDAO orDao = null;
	
	public OB_OperationReminderBiz() {
		orDao = new OB_OperationReminderDAO();
	}
	
	public void addOperationReminder(OB_OperationReminderInfo info) throws IException {
		orDao.addOperationReminder(info);
	}
	
	public OB_OperationReminderInfo findOperationReminder(OB_OperationReminderInfo info) throws IException {
		return orDao.findOperationReminder(info);
	}
	
	public Collection findOperationReminder(OBAwakeCondition awake) throws IException {
		return orDao.findOperationReminder(awake);
	}
	
	public void update(OB_OperationReminderInfo info) throws IException {
		orDao.update(info);
	}
}
