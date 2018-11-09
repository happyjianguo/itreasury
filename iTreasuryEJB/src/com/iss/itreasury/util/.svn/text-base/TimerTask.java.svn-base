package com.iss.itreasury.util;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-3-6 15:06:20)
 * @author：Administrator
 */
public abstract class TimerTask implements Runnable
{
	boolean cancelled = false;
	long nextTime = -1;
	long period;
	boolean fixedRate;
	protected TimerTask()
	{
	}
	public boolean cancel()
	{
		if (cancelled)
			return false;
		cancelled = true;
		if (nextTime == -1)
			return false;
		return true;
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 15:12:29)
	 * @return boolean
	 */
	boolean reschedule()
	{
		if (period == 0 || cancelled)
			return false;
		if (fixedRate)
			nextTime += period;
		else
			nextTime = System.currentTimeMillis() + period;
		return true;
	}
	public abstract void run();
	void schedule(long nextTime, long period, boolean fixedRate)
	{
		this.nextTime = nextTime;
		this.period = period;
		this.fixedRate = fixedRate;
	}
	public long scheduledExecutionTime()
	{
		return nextTime;
	}
}
