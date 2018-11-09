/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.process;

import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Task;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RemindTask extends Task
{
	/**
	 * Constructor for AutoInterestSettlementTask.
	 */
	public RemindTask()
	{
		super();
		super.m_strName = "业务提醒后台处理";
		super.setRunIntervalTime(1);
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		Log.print("--业务提醒后台处理---");
		RemindProcess process = new RemindProcess();
		try
		{
			process.StartRemindProcess();	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Log.print(ex.toString());
		}
	}
}
