/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_OperationReminderDAO;
import com.iss.itreasury.settlement.setting.dataentity.OperationReminderInfo;

/**
 * @author weilu
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationReminderBiz
{
	Sett_OperationReminderDAO dao = null;
	public OperationReminderBiz()
	{
		dao = new Sett_OperationReminderDAO();
		// TODO Auto-generated constructor stub
	}
	public OperationReminderInfo findOperationReminder(long lOfficeID,long lCurrencyID) throws SettlementException
	{
		return dao.findOperationReminder(lOfficeID,lCurrencyID);
	}
	public long saveOperationReminder(OperationReminderInfo settingInfo) throws SettlementException
	{
		return dao.saveOperationReminder(settingInfo);
	}
}
