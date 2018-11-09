package com.iss.itreasury.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class Task extends TimerTask
{
	protected Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	public static final String TASK_TYPE_ERVERDAY = "everyday";
	public static final String TASK_TYPE_INTERVAL = "interval";
	public static final String TASK_TYPE_ONCE = "runonce";

	//任务名称
	protected String m_strName = null;
	//任务定时执行方式
	private String m_strType = null;

	private long m_lInterval = -1;
	private Calendar m_calCurrent = null;

	/**
	 * Constructor for BuildTask.
	 */
	public Task()
	{
		super();
		m_strName = null;
		m_strType = null;
		m_lInterval = -1;
		m_calCurrent = Calendar.getInstance();
	}

	/**
	 * 设置一次执行的日期时间
	 * @param time
	 */
	public final void setRunDateTime(Date time)
	{
		if (time != null)
		{
			this.m_strType = TASK_TYPE_ONCE;

			this.m_calCurrent.setTime(time);
		}

	}

	/**
	 * 设置每日执行的时间，传人的虽然是Date类型，但只有时间信息有效
	 * @param time
	 */
	public final void setEveryDayRunTime(Date time)
	{
		if (time != null)
		{
			this.m_strType = TASK_TYPE_ERVERDAY;

			this.m_calCurrent.setTime(time);
		}
	}

	/**
	 * 设置按固定间隔时间执行的间隔时间，间隔时间的单位是分钟，可以小于一分钟
	 * @param interval
	 */
	public final void setRunIntervalTime(float interval)
	{
		if (interval > 0)
		{
			this.m_strType = TASK_TYPE_INTERVAL;
			this.m_lInterval = Math.round(interval * Scheduler.MS_OF_MINUTE);
		}
	}

	public final Date getDate()
	{
		return this.m_calCurrent.getTime();
	}

	/**
	 * Returns the interval.
	 * @return int
	 */
	public final long getInterval()
	{
		return m_lInterval;
	}

	/**
	 * Returns the hour.
	 * @return int
	 */
	public final int getHour()
	{
		return m_calCurrent.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Returns the minute.
	 * @return int
	 */
	public final int getMinute()
	{
		return this.m_calCurrent.get(Calendar.MINUTE);
	}

	/**
	 * Returns the second.
	 * @return int
	 */
	public final int getSecond()
	{
		return this.m_calCurrent.get(Calendar.SECOND);
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public final String getName()
	{
		return m_strName;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public final void setName(String name)
	{
		m_strName = name;
	}

	/**
	 * Returns the tyep.
	 * @return String
	 */
	public final String getType()
	{
		return m_strType;
	}
}
