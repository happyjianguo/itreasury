/*
 * Created on 2005-1-31
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.closesystem;

import java.sql.Timestamp;

import com.iss.itreasury.bs.util.timer.Task;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**结算系统自动开关机自动任务
 * @author mxzhou
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AutoCloseOpenSystemTask extends Task
{
	/**
	 * 日志
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	/**
	 * 当前自动任务对应的开户行和币种
	 */
	private long officeID = -1;
	private long currencyID = -1;
	
	/**
	 * 构造方法
	 * @param officeID
	 * @param currencyID
	 */
	public AutoCloseOpenSystemTask(long officeID, long currencyID)
	{
		this.officeID = officeID;
		this.currencyID = currencyID;
	}

	/**
	 * 自动任务执行方法
	 */
	public void run() throws Exception
	{
		//执行关机操作
		//关机前先判断当前开机日和自然时间是否一致，如果不一致，不作关机操作
		Timestamp systemDate = null;//自然时间
		Timestamp openDate = null;//结算系统的开机时间
		
		//获取当前结算系统的开机时间
		try
		{
			systemDate = Env.getSystemDateTime();
			openDate = Env.getSystemDate(this.officeID, this.currencyID);
			if(systemDate == null || openDate == null)
			{
				throw new Exception();
			}
			Log.print("当前系统时间："+systemDate+" 结算系统的开机时间:"+openDate);
		}
		catch(Exception e)
		{
			Log.print("获取当前系统时间和结算系统的开机时间时出现异常，无法执行关机操作");
			e.printStackTrace();
			return;
		}
		//比较两时间是否相等
		if(IDate.compareDate(systemDate, openDate) != 0)
		{
			Log.print("当前系统时间和结算系统的开机时间不一致，无法执行关机操作");
			return;
		}
		//执行关机操作
		DealThread dealThread = new DealThread();
		boolean closeSuccess = false;
		try
		{
			closeSuccess = dealThread.closeSystemPublic(
					this.officeID, 
					this.currencyID, 
					Constant.ModuleType.SETTLEMENT,
					openDate,
					true);
		}
		catch(Throwable e)
		{
			Log.print("执行关机操作时出现异常");
			e.printStackTrace();
			return;
		}
		
		//关机成功时接着执行开机操作
		if(!closeSuccess)
		{
			Log.print("关机操作执行失败，无法执行开机操作");
			return;
		}
		Log.print("关机操作执行成功，执行开机操作");
		
		boolean openSuccess = false;
		try
		{
			openSuccess = dealThread.openSystemPublic(
					this.officeID, 
					this.currencyID, 
					Constant.ModuleType.SETTLEMENT,
					DataFormat.getNextDate(openDate));
		}
		catch(Throwable e)
		{
			Log.print("执行开机操作时出现异常");
			e.printStackTrace();
			return;
		}
		
		if(!openSuccess)
		{
			Log.print("开机操作执行失败");
			return;
		}
		Log.print("开机操作执行成功");
	}
}
