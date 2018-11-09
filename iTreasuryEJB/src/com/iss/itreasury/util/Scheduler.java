package com.iss.itreasury.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Scheduler
{
	public static final long MILLISECONDS_OF_DAY = 24 * 60 * 60 * 1000;
	public static final long MS_OF_MINUTE = 60 * 1000; //每分钟的毫秒数

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	//private static Scheduler m_instance = null;
	private Scheduler m_instance = null;

	private Timer m_timer = null;

	/**
	 * Constructor for Scheduler.
	 */
	private Scheduler()
	{
		m_timer = new Timer();
	}

	/**
	 * 
	 */
	/*synchronized public static Scheduler getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new Scheduler();
		}
		return m_instance;
	}*/
	
	/**
	 * Modify by leiyang date 2007/07/04
	 */
	public static Scheduler getInstance()
	{
		return new Scheduler();
	}

	/**
	 * 
	 */
	public void addTask(Task task) throws Exception
	{
		try
		{
			if (task != null)
			{
				//获得当前时间
				Calendar calCurrent = this.getCurrentDate();
				calCurrent.setTime(Env.getSystemDateTime());
				long lDelay = 0;
				long lPeriod = 5000;

				if (task.getType().equalsIgnoreCase(Task.TASK_TYPE_ERVERDAY))
				{
					long lTemp = 0;

					lTemp = calCurrent.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000;

					lTemp += calCurrent.get(Calendar.MINUTE) * 60 * 1000;

					lTemp += calCurrent.get(Calendar.SECOND) * 1000;

					lDelay = task.getHour() * 60 * 60 * 1000;

					lDelay += task.getMinute() * 60 * 1000;

					lDelay += task.getSecond() * 1000;

					if (lDelay < lTemp)
					{
						lDelay = Scheduler.MILLISECONDS_OF_DAY-(lTemp - lDelay) ;
					}
					else
					{
						lDelay = lDelay - lTemp;
					}

					lPeriod = Scheduler.MILLISECONDS_OF_DAY;

					this.m_timer.schedule(task, lDelay, lPeriod);

					System.out.println(
						"AutoTask: "
							+ task.getName()
							+ "将于每天 "
							+ task.getHour()
							+ ":"
							+ task.getMinute()
							+ ":"
							+ task.getSecond()
							+ " 执行。lDelay=="+lDelay);
				}
				else if (task.getType().equalsIgnoreCase(Task.TASK_TYPE_INTERVAL))
				{
					lPeriod = task.getInterval();

					this.m_timer.schedule(task, lDelay, lPeriod);

					System.out.println(
						"AutoTask: " + task.getName() + "将于" + (lDelay / 1000) + "秒后每隔" + (lPeriod / 1000) + "秒执行。");
				}
				else if (task.getType().equalsIgnoreCase(Task.TASK_TYPE_ONCE))
				{
					Date dtTemp = task.getDate();

					if (dtTemp.before(calCurrent.getTime()))
						throw new Exception("任务计划执行时间已过");

					this.m_timer.schedule(task, dtTemp);

					System.out.println("AutoTask: " + task.getName() + "将于" + dtTemp + "执行。");
				}
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			throw new Exception("不能添加该任务计划！" + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void Pause()
	{
		if (m_timer != null)
		{
			m_timer.cancel();
		}
	}

	/**
	 * 获得系统当前日期时间
	 * @return Calendar
	 */
	public Calendar getCurrentDate()
	{
		//Timestamp dtTemp = Env.getSystemDate();

		Calendar calCurrent = Calendar.getInstance();

		//calCurrent.setTime(dtTemp);

		return calCurrent;
	}

}
