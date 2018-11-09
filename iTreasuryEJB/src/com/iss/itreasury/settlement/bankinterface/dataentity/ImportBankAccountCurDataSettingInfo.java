package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.sql.Timestamp;

/**
 * @author mxzhou
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ImportBankAccountCurDataSettingInfo 
{
	private long id	= -1;//	ID
	private long bankType = -1;//	导入指定银行	Type
	private Timestamp beginTime	= null;//导入执行的时间范围(起始)	Date
	private Timestamp endTime = null;//	导入执行的时间范围(截止)	Date
	private long interval = -1;//	执行频率	Integer
	private long taskStatus = -1;//	任务状态	Type
	private long status = -1;//	记录状态	Type
	private Timestamp modifyTime = null;//	记录修改时间	

	/**
	 * @return Returns the bankType.
	 */
	public long getBankType()
	{
		return bankType;
	}
	/**
	 * @param bankType The bankType to set.
	 */
	public void setBankType(long bankType)
	{
		this.bankType = bankType;
	}
	/**
	 * @return Returns the beginTime.
	 */
	public Timestamp getBeginTime()
	{
		return beginTime;
	}
	/**
	 * @param beginTime The beginTime to set.
	 */
	public void setBeginTime(Timestamp beginTime)
	{
		this.beginTime = beginTime;
	}
	/**
	 * @return Returns the endTime.
	 */
	public Timestamp getEndTime()
	{
		return endTime;
	}
	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @return Returns the interval.
	 */
	public long getInterval()
	{
		return interval;
	}
	/**
	 * @param interval The interval to set.
	 */
	public void setInterval(long interval)
	{
		this.interval = interval;
	}
	/**
	 * @return Returns the rdModifyTime.
	 */
	public Timestamp getModifyTime()
	{
		return modifyTime;
	}
	/**
	 * @param rdModifyTime The rdModifyTime to set.
	 */
	public void setModifyTime(Timestamp rdModifyTime)
	{
		this.modifyTime = rdModifyTime;
	}
	/**
	 * @return Returns the rdStatus.
	 */
	public long getStatus()
	{
		return status;
	}
	/**
	 * @param rdStatus The rdStatus to set.
	 */
	public void setStatus(long rdStatus)
	{
		this.status = rdStatus;
	}
	/**
	 * @return Returns the taskStatus.
	 */
	public long getTaskStatus()
	{
		return taskStatus;
	}
	/**
	 * @param taskStatus The taskStatus to set.
	 */
	public void setTaskStatus(long taskStatus)
	{
		this.taskStatus = taskStatus;
	}


}
