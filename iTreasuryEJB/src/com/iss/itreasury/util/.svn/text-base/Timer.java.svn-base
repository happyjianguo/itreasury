package com.iss.itreasury.util;
import java.util.Date;
import java.util.TreeSet;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-3-6 15:14:02)
 * @author：Administrator
 */
public class Timer
{
	TreeSet tasks = new TreeSet(new TimerTaskComparator());
	TimerThread timer;
	/**
	 * Timer 构造子注解。
	 */
	public Timer()
	{
		this(false);
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:46:11)
	 * @param isDaemon boolean
	 */
	public Timer(boolean isDaemon)
	{
		timer = new TimerThread(isDaemon);
		timer.start();
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:46:56)
	 */
	public void cancel()
	{
		synchronized (tasks)
		{
			timer.pleaseStop();
			tasks.clear();
			tasks.notify();
		}
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:51:10)
	 * @param task com.iss.cpf.thread.TimerTask
	 */
	public void schedule(TimerTask task)
	{
		synchronized (tasks)
		{
			tasks.add(task);
			tasks.notify();
		}
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:48:03)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param delay long
	 */
	public void schedule(TimerTask task, long delay)
	{
		task.schedule(Env.getSystemDateTime().getTime() + delay, 0, false);
		schedule(task);
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:48:03)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param delay long
	 */
	public void schedule(TimerTask task, long delay, long period)
	{
		task.schedule(Env.getSystemDateTime().getTime() + delay, period, false);
		schedule(task);
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:49:04)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param time java.util.Date
	 */
	public void schedule(TimerTask task, Date time)
	{
		task.schedule(time.getTime(), 0, false);
		schedule(task);
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 18:50:08)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param firstTime java.util.Date
	 * @param period long
	 */
	public void schedule(TimerTask task, Date firstTime, long period)
	{
		task.schedule(firstTime.getTime(), period, false);
		schedule(task);
	}
	/////////////////////////////////////
	/**
	 * 此处插入类型说明。
	 * 创建日期：(2002-3-6 18:24:58)
	 * @author：Administrator
	 */
	class TimerThread extends Thread
	{
		volatile boolean stopped = false;
		/**
		 * 此处插入方法说明。
		 * 创建日期：(2002-3-6 18:26:25)
		 * @param isDaemon boolean
		 */
		public TimerThread(boolean isDaemon)
		{
			setDaemon(isDaemon);
		}
		/**
		 * 此处插入方法说明。
		 * 创建日期：(2002-3-6 18:28:26)
		 */
		public void pleaseStop()
		{
			stopped = true;
		}
		/**
		 * 此处插入方法说明。
		 * 创建日期：(2002-3-6 18:29:51)
		 */
		public void run()
		{
			TimerTask readyToRun = null;
			while (!stopped)
			{
				if (readyToRun != null)
				{
					if (readyToRun.cancelled)
					{
						readyToRun = null;
						continue;
					}
					readyToRun.run();
					if (readyToRun.reschedule())
						schedule(readyToRun);
					readyToRun = null;
					continue;
				}
				synchronized (tasks)
				{
					long timeout;
					if (tasks.isEmpty())
					{
						timeout = 0;
					}
					else
					{
						TimerTask t = (TimerTask) tasks.first();
						timeout = t.nextTime - Env.getSystemDateTime().getTime();
						if (timeout <= 0)
						{
							readyToRun = t;
							tasks.remove(t);
							continue;
						}
					}
					try
					{
						tasks.wait(timeout);
					}
					catch (InterruptedException e)
					{
					}
				}
			}
		}
	}
	///////////////////////////////////////////
	///////////////////////////////////////////
	static class TimerTaskComparator implements java.util.Comparator
	{
		public int compare(Object o1, Object o2)
		{
			TimerTask t1 = (TimerTask) o1;
			TimerTask t2 = (TimerTask) o2;
			long diff = t1.nextTime - t2.nextTime;
			if (diff < 0)
				return -1;
			else if (diff > 0)
				return 1;
			else
				return 0;
		}
		public boolean equals(Object o)
		{
			return this == o;
		}
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 19:21:27)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param delay long
	 * @param period long
	 */
	public void scheduleAtFixedRate(TimerTask task, long delay, long period)
	{
		task.schedule(Env.getSystemDateTime().getTime() + delay, period, true);
		schedule(task);
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-3-6 19:21:27)
	 * @param task com.iss.cpf.thread.TimerTask
	 * @param delay long
	 * @param period long
	 */
	public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period)
	{
		task.schedule(firstTime.getTime(), period, true);
		schedule(task);
	}
}